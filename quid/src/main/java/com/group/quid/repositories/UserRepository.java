package com.group.quid.repositories;

import com.group.quid.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users_quid.users WHERE email = :email", nativeQuery = true)
    Optional<User> getUserByEmail(@Param("email") String email);

}
