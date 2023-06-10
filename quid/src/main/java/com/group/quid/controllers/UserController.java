package com.group.quid.controllers;

import com.group.quid.entity.User;
import com.group.quid.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "The field " + err.getField()+ " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        User userCreated = userService.saveUser(user);
        //TODO generar metodo para convertir entity to DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
