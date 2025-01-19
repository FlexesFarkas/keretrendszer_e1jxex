package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Purchase;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;

import java.util.List;
import java.util.Optional;

public interface PurchaseDAO {
    void save(Purchase purchase);
    Optional<Purchase> findById(int id);
    public List<Purchase> findByUser(User user);
    List<Purchase> findAll();
    void update(Purchase purchase);
    void delete(int id);
    public Optional<Purchase> findByUserAndGame(User user, Game game);

}
