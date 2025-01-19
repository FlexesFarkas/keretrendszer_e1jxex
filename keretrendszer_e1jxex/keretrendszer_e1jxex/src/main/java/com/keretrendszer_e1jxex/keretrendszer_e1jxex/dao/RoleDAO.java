package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDAO {
    void save(Role role);
    Optional<Role> findById(int id);
    List<Role> findAll();
    void update(Role role);
    void delete(int id);

    Role findByName(String role);
}
