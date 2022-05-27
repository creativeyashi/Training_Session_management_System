package com.yashika.demo.services;

import com.yashika.demo.entity.Speakers;

import java.util.List;

public interface SpeakerService {
    List<Speakers> listSpeakers();
    Speakers getSpeakerById(int id);
    Speakers saveSpeaker(Speakers speaker);
    void deleteSpeaker(int id);
}
