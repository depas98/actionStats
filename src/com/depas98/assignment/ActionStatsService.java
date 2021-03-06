package com.depas98.assignment;

import com.depas98.assignment.data.Action;
import com.depas98.assignment.data.ActionStat;
import com.depas98.assignment.data.ActionTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ActionStatsService {

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    // this holds the data for a list of ActionStat objects grouped by Action
    private final Map<Action, ActionStat> actionStatMap = new HashMap<>();

    /**
     *  Given a JSON string that represents an ActionTime object this will
     *  unmarshal the string to the object calculate the running average for the Action
     *  and save to the actionStatMap
     *
     *  The JSON string format should be (and is case sensitive) the following:
     *
     *  {"action":"jump", "time":75}
     *
     *  The "action" field is the Action enum and can only be one of the values in the enum (ex. "jump" or "run").
     *  Also case matters, "JUMP" will not work.
     *
     *  If null or empty string is passed as an argument then this method will throw an IllegalArgumentException
     *
     * @param actionTimeJson  The JSON string representation of the ActionTime object
     * @throws IOException, IllegalArgumentException
     * Unchecked Exceptions JsonParseException, JsonMappingException, InvalidFormatException
     */
    public void addAction(final String actionTimeJson) throws IOException, IllegalArgumentException {

        // TODO For now just doing a little sanity check, but in the future could do more validation
        // of the JSON string to give the user a better error message
        if (actionTimeJson == null || actionTimeJson.isEmpty()){
            throw new IllegalArgumentException("The actionTimeJson parameter can't be null or empty.");
        }

        // TODO handle error better, need to log error to file with the stack trace and rethrow the error
        // For now we are just throwing the IOException and letting the caller handle it
        // Unmarshal JSON string to ActionTime object
        ActionTime actionTimeEntry = new ObjectMapper().readValue(actionTimeJson, ActionTime.class);

        writeLock.lock();
        try {
            // add the new time to the ActionStat object in the map, create one if doesn't exist amd add to map
            actionStatMap.computeIfAbsent(actionTimeEntry.getAction(), ActionStat::new)
                    .addTime(actionTimeEntry.getTime());
        }
        finally {
            writeLock.unlock();
        }

    }

    /**
     *  This will process the actionStatMap creating an ActionStat list.
     *  Then it will marshall the list to a JSON string and return it.
     *  The JSON string format will be the following:
     *
     *  [{"action":"jump", "avg":10.0}, {"action":"run", "avg":15.0}]
     *
     * @return A JSON string representing a list of ActionStat objects
     * @throws JsonProcessingException
     */
    public String getStats() throws JsonProcessingException{

        readLock.lock();
        try {
            // Get the list of actionStats from the map
            final List<ActionStat> actionStats = new ArrayList<>(actionStatMap.values());

            // TODO handle error better, would need to log error to file with the stack trace and rethrow the error
            // For now we are just throwing the JsonProcessingException and letting the caller handle it
            return new ObjectMapper().writeValueAsString(actionStats);  // marshal object to json string
        }
        finally {
            readLock.unlock();
        }

    }

    public static void main(String[] args){

        // To test concurrency each one of these JASON entries will be run on a separate thread
        List<String> actionTimeJsonList = List.of(
                "{\"action\":\"run\", \"time\":200}",
                "{\"action\":\"jump\", \"time\":100}",
                "{\"action\":\"run\", \"time\":150}",
                "{\"action\":\"jump\", \"time\":150}");

        // create the service
        ActionStatsService actionService = new ActionStatsService();

        // create a list of threads so we can join them and wait till they are completed
        List<Thread> threads = new ArrayList<>();

        // create a new thread for each item in the JSON list and call addAction with the JSON string
        for (String actionTimeJson : actionTimeJsonList) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.println("Adding action: " + actionTimeJson);
                    actionService.addAction(actionTimeJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
            thread.start();
        }

        // join created threads and wait till they are done
        for (Thread thread : threads) {
            try {
                thread.join(2000);   // waiting up to 2 secs for thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Get the Action Stats JSON string and print it out
        // Note: a gotcha if a thread takes longer than 2 seconds then the stats output will
        // not have all the records added because they haven't finished yet.
        // This shouldn't happen unless the computer is really slow
        try {
            System.out.println();
            System.out.println("Stats: ");
            System.out.println(actionService.getStats());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
