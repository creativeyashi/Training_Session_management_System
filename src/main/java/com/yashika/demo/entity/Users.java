package com.yashika.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "username")
    @NotNull(message="is required")
    private String username;

    @Column(name = "password")
    @NotNull(message="is required")
    @Size(min=6, message="should have 6 or more characters")
    private String password;

    @Column(name = "first_name")
    @NotNull(message="is required")
    @Size(min=3, message="should have 3 or more characters")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message="is required")
    @Size(min=3, message="should have 3 or more characters")
    private String lastName;

    @Column(name = "age")
    @NotNull(message="is required")
    @Min(value=0, message = "Must be greater than or equal to 0")
    @Max(value=130, message = "Must not be greater than 130")
    private int age;

    @Column(name = "gender")
    @NotNull(message="is required")
    @Size(max=1, message="write M for male and F for female")
    private String gender;

    @Column(name = "branch")
    @NotNull(message="is required")
    @Size(min=2, max = 5, message="should have 2-5 characters")
    private String branch;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Events> eventsList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    public Users(String username, String password, String firstName, String lastName, int age, String gender, String branch) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.branch = branch;
    }

    public void addEvent(Events tempEvent){
        if(this.eventsList == null){
            this.eventsList = new ArrayList<>();
        }

        this.eventsList.add(tempEvent);
    }

    public void addRole(Roles role){
        if(this.roles == null){
            this.roles = new HashSet<>();
        }

        this.roles.add(role);
    }
}
