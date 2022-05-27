package com.yashika.demo.services;

import com.yashika.demo.entity.Events;

import java.util.List;

public interface EventsService {
    List<Events> listEvents();
    Events getEventById(int id);
    Events saveEvent(Events event);
    void deleteEvent(int id);
}
