package com.depas98.assignment.data;

public class ActionStat {

    private Action action;
    private double avg = 0.0;

    // The count of the number of Action items added and is used to calculate the average
    private int count = 0;

    public ActionStat(Action action) {
        if (action == null){
            throw new IllegalArgumentException("The parameter action can't be null");
        }

        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public double getAvg() {
        return avg;
    }

    public void addTime(int time){
        // don't accept negative time
        if (time < 0){
            throw new IllegalArgumentException("The value of time needs to be zero or greater, time = " + time);
        }

        // calculate and set the average and the new item count
        avg = ((avg * count) + time) / (count + 1);
        count++;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName())
                .append(" [action=").append(action)
                .append(", avg=").append(avg)
                .append(", count=").append(count)
                .append("]");
            return sb.toString();
    }
}
