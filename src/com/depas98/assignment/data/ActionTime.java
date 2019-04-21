package com.depas98.assignment.data;

public class ActionTime {
    private Action action;
    private int time;

    public ActionTime(){
        super();
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

    public void setTime(int time) {
        // don't accept negative time
        if (time < 0){
            throw new IllegalArgumentException("The parameter time needs to be zero or greater");
        }
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName())
                .append(" [action=").append(action)
                .append(", time=").append(time)
                .append("]");
        return sb.toString();
    }
}
