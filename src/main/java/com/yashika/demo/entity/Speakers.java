package com.yashika.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "speakers")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Speakers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speaker_id")
    private int id;

    @Column(name = "speaker_name")
    @NotNull(message="is required")
    @Size(min=3, message="should have 3 or more characters")
    private String speakerName;

    @Column(name = "speaker_designation")
    @NotNull(message="is required")
    @Size(min=10, message="should have 10 or more characters")
    private String speakerDesignation;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "event_speaker",
            joinColumns = @JoinColumn(name = "speaker_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Events> eventsList;

    public Speakers(String speakerName, String speakerDesignation) {
        this.speakerName = speakerName;
        this.speakerDesignation = speakerDesignation;
    }
}
