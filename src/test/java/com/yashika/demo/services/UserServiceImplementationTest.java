package com.yashika.demo.services;


import com.yashika.demo.entity.Events;
import com.yashika.demo.entity.Roles;
import com.yashika.demo.entity.Users;
import com.yashika.demo.repository.RoleRepository;
import com.yashika.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplementationTest {
    @Autowired
    private UsersServiceInterface usersService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    void listUsers() {
        when(userRepository.findAll()).thenReturn(Stream
                .of(new Users("jack","test123","micheal","jackson",22,"M","IT")).collect(Collectors.toList()));

        assertEquals(1, usersService.listUsers().size());
    }

    @Test
    void getUserById(){
        Users users = new Users("jack","test123","Micheal","Jackson", 22, "M", "IT");
        users.addRole(roleRepository.getById(2));
        users.addEvent(new Events("python language", "XYZ"));

        when(userRepository.getById(1)).thenReturn(users);

        Users user = usersService.getUserById(1);
        assertEquals("jack", user.getUsername());
        assertEquals("Micheal", user.getFirstName());
        assertEquals("Jackson", user.getLastName());
        assertEquals(22, user.getAge());
        assertEquals("M", user.getGender());
        assertEquals("IT", user.getBranch());
        assertEquals(1, user.getEventsList().size());
    }

    @Test
    void getUserByUsername(){
        Users users = new Users("jack","test123","Micheal","Jackson", 22, "M", "IT");
        users.addRole(roleRepository.getById(2));
        users.addEvent(new Events("python language", "XYZ"));
        users.addEvent(new Events("java language", "ABC"));

        when(userRepository.getUserByUsername("jack")).thenReturn(users);

        Users user = usersService.getUserByUsername("jack");
        assertEquals(users.getId(), user.getId());
        assertEquals("Micheal", user.getFirstName());
        assertEquals("Jackson", user.getLastName());
        assertEquals(22, user.getAge());
        assertEquals("M", user.getGender());
        assertEquals("IT", user.getBranch());
        assertEquals(2, user.getEventsList().size());
    }

    @Test
    void saveUser() {
        Users users = new Users("jack","test123","micheal","jackson",22,"M","IT");
        Roles role = roleRepository.getById(2);
        users.addRole(role);

        when(userRepository.save(users)).thenReturn(users);

        assertEquals(users, usersService.saveUser(users));
    }

    @Test
    void deleteUser() {
        Users users = new Users("jack","test123","micheal","jackson",22,"M","IT");
        Roles role = roleRepository.getById(2);
        users.addRole(role);

        usersService.deleteUser(users.getId());
        verify(userRepository, times(1)).deleteById(users.getId());
    }
}