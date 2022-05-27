package com.yashika.demo.services;

import com.yashika.demo.entity.Users;
import com.yashika.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UsersServiceInterface {
    @Autowired
    private ModelMapper modelMapper;

    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Users> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUserById(int id) {
        Optional<Users> result = Optional.of(userRepository.getById(id));

        return result.get();
    }

    @Override
    public Users getUserByUsername(String username) {
        Optional<Users> result = Optional.of(userRepository.getUserByUsername(username));

        return result.get();
    }

    @Override
    public Users saveUser(Users user) {

        userRepository.save(user);

        return user;
    }

    @Override
    public void deleteUser(int id) {

        userRepository.deleteById(id);
    }
}
