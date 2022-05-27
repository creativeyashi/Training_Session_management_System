package com.yashika.demo.dto;

import com.yashika.demo.entity.Events;
import com.yashika.demo.entity.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UsersDTO {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String branch;
    private List<Events> eventsList;
    private Set<Roles> roles;
}
