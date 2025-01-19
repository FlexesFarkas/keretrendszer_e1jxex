package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @Autowired
    private final EntityManager entityManager;



    @Override
    public Role findByName(String roleName) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class);
        query.setParameter("roleName", roleName);
        return query.getSingleResult();
    }
    @Autowired
    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Optional<Role> findById(int id) {
        Role role = entityManager.find(Role.class, id);
        return Optional.ofNullable(role);
    }

    @Override
    public List<Role> findAll() {
        TypedQuery<Role> query = entityManager.createQuery("FROM Role", Role.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Role role) {
        if (role.getId() != null && entityManager.find(Role.class, role.getId()) != null) {
            entityManager.merge(role);
        } else {
            throw new IllegalArgumentException("Role with ID " + role.getId() + " does not exist.");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            entityManager.remove(role);
        } else {
            throw new IllegalArgumentException("Role with ID " + id + " does not exist.");
        }
    }
}
