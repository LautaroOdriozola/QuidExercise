package com.group.quid.utils;

import com.group.quid.DTO.TaskDTO;
import com.group.quid.DTO.UserDTO;
import com.group.quid.DTO.enums.State;
import com.group.quid.entity.Task;
import com.group.quid.entity.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {


    private static Utils INSTANCE;

    private Utils(){
    }

    public static Utils getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new Utils();
        }

        return INSTANCE;
    }

    public UserDTO convertUserToDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUser_id());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        List<Task> tasks = user.getTasks();

        if(tasks != null){
            List<TaskDTO> tasksCompleted = tasks.stream()
                    .map(this::convertTaskToDto)
                    .filter(taskDTO -> Objects.equals(taskDTO.getState(), State.COMPLETE.getState()))
                    .collect(Collectors.toList());

            userDTO.setTasksCompleted(tasksCompleted);

            List<TaskDTO> tasksPending = tasks.stream()
                    .map(this::convertTaskToDto)
                    .filter(taskDTO -> Objects.equals(taskDTO.getState(), State.PENDING.getState()))
                    .collect(Collectors.toList());

            userDTO.setTasksPending(tasksPending);
        }

        return userDTO;
    }

    public TaskDTO convertTaskToDto(Task task){
        TaskDTO taskDTO = new TaskDTO();

        if(task.getId() != null){
            taskDTO.setId(task.getId());
        }

        taskDTO.setState(task.getState());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());

        if(task.getUser() != null){
            taskDTO.setUser_id(task.getUser().getUser_id());
        }

        String stateString = "";

        if(task.getState() == 1){ stateString = "COMPLETE";}
        if(task.getState() == 2){ stateString = "PENDING";}

        taskDTO.setStateString(stateString);

        return taskDTO;
    }

}
