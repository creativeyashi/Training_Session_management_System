package com.yashika.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    @Column(name = "event_name")
    @NotNull(message="is required")
    @Size(min=5, message="should have 5 or more characters")
    private String eventName;

    @Column(name = "event_venue")
    @NotNull(message="is required")
    @Size(min=3, message="should have 3 or more characters")
    private String eventVenue;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> usersList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "event_speaker",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id")
    )
    private List<Speakers> speakersList;

    public Events(String eventName, String eventVenue) {
        this.eventName = eventName;
        this.eventVenue = eventVenue;
    }

    public void addUser(Users tempUser){
        if(this.usersList == null){
            this.usersList = new ArrayList<>();
        }

        this.usersList.add(tempUser);
    }

    public void addSpeaker(Speakers speaker){
        if(this.speakersList == null){
            this.speakersList = new ArrayList<>();
        }

        this.speakersList.add(speaker);
    }
}
