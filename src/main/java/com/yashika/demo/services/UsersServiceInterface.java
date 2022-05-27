package com.yashika.demo.services;

import com.yashika.demo.entity.Users;

import java.util.List;

public interface UsersServiceInterface {
    List<Users> listUsers();
    Users getUserById(int id);
    Users saveUser(Users user);

    void deleteUser(int id);
    Users getUserByUsername(String username);
}
