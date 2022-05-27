package com.yashika.demo.services;


import static org.mockito.Mockito.verify;


import com.yashika.demo.entity.Events;
import com.yashika.demo.entity.Speakers;
import com.yashika.demo.repository.EventsRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class EventsServiceImplementationTest {
    @Autowired
    private EventsService eventsService;

    @MockBean
    private EventsRepository eventsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void listEvents() {
        when(eventsRepository.findAll()).thenReturn(Stream
                .of(new Events("Introduction to programming", "Lab 2")).collect(Collectors.toList()));

        assertEquals(1, eventsService.listEvents().size());
        ;
    }

    @Test
    void getEventById(){
        Events events = new Events("python language", "XYZ");
        events.addSpeaker(new Speakers("jack","sr. dev"));
        when(eventsRepository.getById(1)).thenReturn(events);

        Events event = eventsService.getEventById(1);
        assertEquals("python language", event.getEventName());
        assertEquals("XYZ", event.getEventVenue());

    }

    @Test
    void saveEvent() {
        Events event = new Events("Introduction to programming", "Lab 2");
        event.addSpeaker(new Speakers("XYXZ", "Metaverse expert"));

        when(eventsRepository.save(event)).thenReturn(event);
        assertEquals(event, eventsService.saveEvent(event));
    }

    @Test
    void deleteEvent() {
        Events event = new Events("Introduction to programming", "Lab 2");
        eventsService.deleteEvent(event.getId());
        verify(eventsRepository, times(1)).deleteById(event.getId());
    }
}