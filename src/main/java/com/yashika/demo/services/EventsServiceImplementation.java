package com.yashika.demo.services;

import com.yashika.demo.entity.Events;
import com.yashika.demo.repository.EventsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventsServiceImplementation implements EventsService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventsRepository eventsRepository;

    @Override
    public List<Events> listEvents() {

        return eventsRepository.findAll();
    }

    @Override
    public Events getEventById(int id) {
        Optional<Events> result = Optional.of(eventsRepository.getById(id));

        return result.get();
    }

    @Override
    public Events saveEvent(Events event) {

        eventsRepository.save(event);

        return event;
    }

    @Override
    public void deleteEvent(int id) {

        eventsRepository.deleteById(id);
    }
}
