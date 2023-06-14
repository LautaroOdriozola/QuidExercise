package com.group.quid.services;

import com.group.quid.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getAll();
    Task saveTask(Task task);
    Optional<Task> getTaskById(Long id);
    void deleteTaskById(Long id);
}
