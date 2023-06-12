package com.group.quid.services.servicesImpl;

import com.group.quid.entity.User;
import com.group.quid.repositories.UserRepository;
import com.group.quid.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long user_id) {
        return userRepository.findById(user_id);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        String pass = user.getPassword();
        String passEncode = passwordEncoder.encode(pass);
        user.setPassword(passEncode);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}
