package com.depas98.assignment.data;

public class ActionStat {

    private Action action;
    private double avg;

    public ActionStat(){
        super();
    }

    public ActionStat(Action action, double avg){
        if (action == null){
            throw new IllegalArgumentException("The parameter action can't be null");
        }

        this.action = action;
        this.avg = avg;
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

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public double getAvg() {
        return avg;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName())
                .append(" [action=").append(action)
                .append(", avg=").append(avg)
                .append("]");
            return sb.toString();
    }
}
