package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void save(User user);
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    void update(User user);
    void delete(int id);
    List<String> findRolesByUserId(int userId);
}
