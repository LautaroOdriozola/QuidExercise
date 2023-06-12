package com.group.quid.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TaskDTO {

    public Long id;
    public Integer state;
    public String stateString;
    @NotBlank
    @NotNull
    public String title;
    @NotBlank
    @NotNull
    public String description;
    public Long user_id;

    public TaskDTO() {
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getStateString() {
        return stateString;
    }

    public void setStateString(String stateString) {
        this.stateString = stateString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
