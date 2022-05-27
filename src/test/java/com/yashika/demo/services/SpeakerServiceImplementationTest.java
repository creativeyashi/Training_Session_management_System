package com.yashika.demo.services;


import com.yashika.demo.entity.Speakers;
import com.yashika.demo.repository.SpeakersRepository;
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
class SpeakerServiceImplementationTest {
    @Autowired
    private SpeakerService speakerService;

    @MockBean
    private SpeakersRepository speakersRepository;

    @Test
    void listSpeakers() {
        when(speakersRepository.findAll()).thenReturn(Stream
                .of(new Speakers("XYZ", "Metaverse expert")).collect(Collectors.toList()));

        assertEquals(1, speakerService.listSpeakers().size());
    }

    @Test
    void getSpeakerById(){
        Speakers speakers = new Speakers("XYZ", "Hacker");
        when(speakersRepository.getById(1)).thenReturn(speakers);

        Speakers speaker = speakerService.getSpeakerById(1);
        assertEquals("XYZ", speaker.getSpeakerName());
        assertEquals("Hacker", speaker.getSpeakerDesignation());
    }

    @Test
    void saveSpeaker() {
        Speakers speakers = new Speakers("XYZ", "Metaverse expert");
        when(speakersRepository.save(speakers)).thenReturn(speakers);

        assertEquals(speakers, speakerService.saveSpeaker(speakers));
    }

    @Test
    void deleteSpeaker() {
        Speakers speakers = new Speakers("XYZ", "Metaverse expert");

        speakerService.deleteSpeaker(speakers.getId());
        verify(speakersRepository, times(1)).deleteById(speakers.getId());
    }
}