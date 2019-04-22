package com.depas98.assignment.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ActionStat {

    private Action action;
    private double avg;
    private int count;

    public ActionStat(){
        super();
    }

    public ActionStat(Action action, double avg){
        if (action == null){
            throw new IllegalArgumentException("The parameter action can't be null");
        }

        this.action = action;
        this.avg = avg;
        count = 1;  // initial to one since when we create this there is no one record
    }

    public void setAction(Action action) {
        if (action == null){
            throw new IllegalArgumentException("The parameter action can't be null");
        }
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getAvg() {
        return avg;
    }

    /**
     * Returns Count of the number of Action items added used to calculate the average
     * @return action item count
     */
    @JsonIgnore
    public int getCount() {
        return count;
    }

    /**
     * Set the number of Action items added
     * @param  count action item count
     */
    @JsonIgnore
    public void setCount(int count) {
        this.count = count;
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
