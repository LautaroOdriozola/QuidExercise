package com.group.quid.DTO.enums;

import java.util.Objects;

public enum State {

    COMPLETE(1, "COMPLETE"),
    PENDING(2, "PENDING");
    private Integer state;
    private String stateString;

    private State(Integer state, String stateString){
        this.state = state;
        this.stateString = stateString;
    }

    public Integer getState() {
        return state;
    }

    public String getStateString(){
        return stateString;
    }

    public String getStateString(Integer i){
        return null;
    }

}
