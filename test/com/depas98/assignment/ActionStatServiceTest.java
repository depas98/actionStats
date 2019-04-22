package com.depas98.assignment;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActionStatServiceTest {

    @Test (expected = IllegalArgumentException.class)
    public void addActionNullArgTest(){
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test the null argument case
        try {
            actionStatsService.addAction(null);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {}
    }

    @Test (expected = IllegalArgumentException.class)
    public void addActionEmptyArgTest(){
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test the empty argument case
        try {
            actionStatsService.addAction("");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {}
    }

    @Test (expected = JsonParseException.class)
    public void addActionInvalidJsonArgTest() throws JsonParseException {
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test bad JSON string case
        try {
            actionStatsService.addAction("{bogus}");
        }
        catch (JsonParseException e) {
            throw e;
        }
        catch (IOException e) {}
    }

    @Test(expected = JsonMappingException.class)
    public void addActionInvalidActionValue() throws JsonMappingException {
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test JSON string bad Action value case
        try {
            actionStatsService.addAction("{\"action\":\"JUMP\", \"time\":100}");
        }
        catch (JsonMappingException e) {
            throw e;
        }
        catch (IOException e) {}
    }

    @Test(expected = JsonMappingException.class)
    public void addActionInvalidActionNameTest() throws JsonMappingException {
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test JSON string bad Action value case
        try {
            actionStatsService.addAction("{\"ACTION\":\"jump\", \"time\":100}");
        }
        catch (JsonMappingException e) {
            throw e;
        }
        catch (IOException e) {}
    }

    @Test (expected = InvalidFormatException.class)
    public void addActionInvalidTimeValue() throws InvalidFormatException {
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test JSON string bad Time value case
        try {
            actionStatsService.addAction("{\"action\":\"jump\", \"time\":\"bogus\"}");
        }
        catch (InvalidFormatException e) {
            throw e;
        }
        catch (IOException e) {}
    }

    @Test(expected = JsonMappingException.class)
    public void addActionInvalidTimeNameTest() throws JsonMappingException {
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test JSON string bad Action value case
        try {
            actionStatsService.addAction("{\"action\":\"jump\", \"TIME\":100}");
        }
        catch (JsonMappingException e) {
            throw e;
        }
        catch (IOException e) {}
    }

    @Test(expected = JsonMappingException.class)
    public void addActionTimeTooLargeTest() throws JsonMappingException {
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test JSON string bad Action value case
        try {
            actionStatsService.addAction("{\"action\":\"jump\", \"time\":" + Integer.MAX_VALUE + 1 + "}");
        }
        catch (JsonMappingException e) {
            throw e;
        }
        catch (IOException e) {}
    }

    @Test(expected = IllegalArgumentException.class)
    public void addActionNegativeTimeTest() throws IllegalArgumentException {
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test JSON string bad Action value case
        try {
            actionStatsService.addAction("{\"action\":\"jump\", \"time\":-10}");
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (IOException e) {}
    }

    @Test
    public void addActionGetStatsTest() {
        ActionStatsService actionStatsService = new ActionStatsService();

        // Test the happy path of adding Action objects and getting the stats
        try {
            // test one entry
            actionStatsService.addAction("{\"action\":\"jump\", \"time\":100}");
            String stats = actionStatsService.getStats();
            assertEquals("[{\"action\":\"jump\",\"avg\":100.0}]", stats);

            // test one group two entries
            actionStatsService.addAction("{\"action\":\"jump\", \"time\":150}");
            stats = actionStatsService.getStats();
            assertEquals("[{\"action\":\"jump\",\"avg\":125.0}]", stats);

            // test multiple groups
            actionStatsService.addAction("{\"action\":\"run\", \"time\":150}");
            stats = actionStatsService.getStats();

            // can't rely on order of the action types so need to parse and figure out the order
            String[] statsArr = stats.split(",");
            assertEquals(4, statsArr.length);
            if (statsArr[0].contains("jump")){
                assertEquals("[{\"action\":\"jump\",\"avg\":125.0},{\"action\":\"run\",\"avg\":150.0}]", stats);
            }
            else{
                assertEquals("[{\"action\":\"run\",\"avg\":150.0},{\"action\":\"jump\",\"avg\":125.0}]", stats);
            }

            // Test zero time
            actionStatsService = new ActionStatsService();
            actionStatsService.addAction("{\"action\":\"jump\", \"time\":0}");
            stats = actionStatsService.getStats();
            assertEquals("[{\"action\":\"jump\",\"avg\":0.0}]", stats);
        }
     catch (JsonMappingException e) {
         System.out.println(e);; }
        catch (IOException e) {}
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void getEmptyActionStatsTest() {
        ActionStatsService actionStatsService = new ActionStatsService();

        try {
            String stats = actionStatsService.getStats();
            assertEquals("[]", stats);
        } catch (JsonProcessingException e) {}

    }

    @Test
    public void actionStatsServiceConcurrencyTest()  {
        // To test concurrency each one of these JASON entries will be run on a separate thread
        List<String> actionTimeJsonList = List.of(
                "{\"action\":\"run\", \"time\":250}",
                "{\"action\":\"jump\", \"time\":50}",
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
                    actionService.addAction(actionTimeJson);
                } catch (IOException e) {}
            });
            threads.add(thread);
            thread.start();
        }

        // join created threads and wait till they are done
        for (Thread thread : threads) {
            try {
                thread.join(500);   // waiting .5 secs
            } catch (InterruptedException e) {}
        }

        // get the Action Stats JSON string and print it out
        try {
            String stats = actionService.getStats();
            // can't rely on order of the action types so need to parse and figure out the order
            String[] statsArr = stats.split(",");
            assertEquals(4, statsArr.length);
            if (statsArr[0].contains("jump")){
                assertEquals("[{\"action\":\"jump\",\"avg\":100.0},{\"action\":\"run\",\"avg\":200.0}]", stats);
            }
            else{
                assertEquals("[{\"action\":\"run\",\"avg\":200.0},{\"action\":\"jump\",\"avg\":100.0}]", stats);
            }
        } catch (JsonProcessingException e) {}
    }

}
