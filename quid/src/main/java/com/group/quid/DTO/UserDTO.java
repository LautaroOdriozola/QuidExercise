package com.group.quid.DTO;

import com.group.quid.entity.Task;

import java.util.List;

public class UserDTO {

    Long user_id;
    String name;
    String surname;
    String email;
    String password;
    List<TaskDTO> tasksCompleted;
    List<TaskDTO> tasksPending;

    public UserDTO() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TaskDTO> getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(List<TaskDTO> tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }

    public List<TaskDTO> getTasksPending() {
        return tasksPending;
    }

    public void setTasksPending(List<TaskDTO> tasksPending) {
        this.tasksPending = tasksPending;
    }
}
