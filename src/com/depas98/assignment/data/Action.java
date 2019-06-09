package com.depas98.assignment.data;

public enum Action {
    jump("jump"),
    run("run"),
    unknown("unknown");

    private String name;

    Action(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static Action getActionByName(String name){
        if (name==null){
            throw new IllegalArgumentException("Name can't be null");
        }

        if (jump.getName().equalsIgnoreCase(name)){
                return jump;
        }
        if (run.getName().equalsIgnoreCase(name)){
            return run;
        }
        else{
            return unknown;
        }

    }
}
