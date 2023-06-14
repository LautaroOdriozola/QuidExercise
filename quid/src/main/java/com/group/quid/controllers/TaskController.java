package com.group.quid.controllers;

import com.group.quid.DTO.AuthResponse;
import com.group.quid.DTO.TaskDTO;
import com.group.quid.DTO.UserDTO;
import com.group.quid.DTO.enums.State;
import com.group.quid.entity.Task;
import com.group.quid.entity.User;
import com.group.quid.jwt.JwtTokenUtil;
import com.group.quid.services.TaskService;
import com.group.quid.services.UserService;
import com.group.quid.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;


    @PostMapping("/create")
    public ResponseEntity<?> saveTask(Authentication authentication,
                                      @RequestBody TaskDTO taskDTO,
                                      BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "The field " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<User> userByEmail = userService.getUserByEmail(authentication.getName());
        taskDTO.setState(State.PENDING.getState());
        if(taskDTO.getUser_id() == null){
            taskDTO.setUser_id(userByEmail.get().getUser_id());
        } else{
            Optional<User> userById = userService.getUserById(taskDTO.getUser_id());
            if(userById.isPresent()){
                taskDTO.setUser_id(userById.get().getUser_id());
            } else{
                Map<String, String> errors = new HashMap<>();
                errors.put("error", "There is no user id passed by request.");
                return ResponseEntity.badRequest().body(errors);
            }
        }

        Task task = modelMapper.map(taskDTO, Task.class);
        Task taskCreated = taskService.saveTask(task);
        TaskDTO taskDtoReturn = Utils.getInstance().convertTaskToDto(taskCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDtoReturn);

    }

    @PutMapping("/setTaskComplete/{id}")
    public ResponseEntity<?> setTaskComplete(@PathVariable Long id){
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setState(State.COMPLETE.getState());
            task = taskService.saveTask(task);
            return ResponseEntity.ok(Utils.getInstance().convertTaskToDto(task));
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "The indicated task does not exist in the database.");
            return ResponseEntity.badRequest().body(errors);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks(){
        List<TaskDTO> tasks = taskService.getAll()
                .stream()
                .map(task -> Utils.getInstance().convertTaskToDto(task))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        Optional<Task> optionalTask = taskService.getTaskById(id);

        if(optionalTask.isPresent()){
            taskService.deleteTaskById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
