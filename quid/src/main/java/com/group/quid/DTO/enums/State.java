package com.group.quid.DTO.enums;

public enum State {

    COMPLETE(1),
    PENDING(2);
    private Integer state;
    private State(Integer state){
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
