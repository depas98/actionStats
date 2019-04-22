package com.depas98.assignment.data;

public class ActionTime {
    private Action action;
    private int time;

    public Action getAction() {
        return action;
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
