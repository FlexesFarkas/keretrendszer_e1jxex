package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;


    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Username and password must not be null.");
        }
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public Optional<User> findById(int id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        query.setFlushMode(FlushModeType.COMMIT);
        List<User> results = query.getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.get(0));
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(User user) {
        if (user.getId() != null && entityManager.find(User.class, user.getId()) != null) {
            entityManager.merge(user);
            entityManager.flush();
        } else {
            throw new IllegalArgumentException("User with ID " + user.getId() + " does not exist.");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
            entityManager.flush();
        } else {
            throw new IllegalArgumentException("User with ID " + id + " does not exist.");
        }
    }

    @Override
    public List<String> findRolesByUserId(int userId) {
        String query = "SELECT r.name FROM Role r " +
                "JOIN r.users u " +
                "WHERE u.id = :userId";

        return entityManager.createQuery(query, String.class)
                .setParameter("userId", userId)
                .getResultList();
    }


}
