package com.group.quid.services;

import com.group.quid.entity.User;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long user_id);
    User saveUser(User user);
    void deleteUser(Long user_id);

}
