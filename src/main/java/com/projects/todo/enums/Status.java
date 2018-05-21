package com.projects.todo.enums;

public enum Status {
    DONE("done"),
    IN_PROGRESS("in progress"),
    TO_DO("to do"),
    ON_HOLD("on hold");

    private final String displayName;

    Status(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
