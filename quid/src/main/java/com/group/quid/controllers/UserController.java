package com.group.quid.controllers;

import com.group.quid.DTO.AuthRequest;
import com.group.quid.DTO.AuthResponse;
import com.group.quid.DTO.UserDTO;
import com.group.quid.entity.User;
import com.group.quid.jwt.JwtTokenUtil;
import com.group.quid.services.UserService;
import com.group.quid.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "The field " + err.getField()+ " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<User> userFound = userService.getUserByEmail(user.getEmail());
        if(userFound.isPresent()){
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "There is already a registered user with that email.");
            return ResponseEntity.badRequest().body(errors);
        }

        User userCreated = userService.saveUser(user);
        UserDTO userDTO = Utils.getInstance().convertUserToDto(userCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/id/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable Long user_id){
        Optional<User> user = userService.getUserById(user_id);
        if(user.isPresent()){
            return ResponseEntity.ok(Utils.getInstance().convertUserToDto(user.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        Optional<User> user = userService.getUserByEmail(email);
        if(user.isPresent()){
            return ResponseEntity.ok(Utils.getInstance().convertUserToDto(user.get()));
        }
        return ResponseEntity.notFound().build();
    }


}
