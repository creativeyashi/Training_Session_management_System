package com.yashika.demo.controller;

import com.yashika.demo.entity.Events;
import com.yashika.demo.entity.Speakers;
import com.yashika.demo.services.EventsService;
import com.yashika.demo.services.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/speakers")
public class SpeakerController {
    private SpeakerService speakerService;
    private EventsService eventsService;

    @Autowired
    public SpeakerController(SpeakerService speakerService, EventsService eventsService){

        this.speakerService = speakerService;
        this.eventsService = eventsService;
    }

    @GetMapping("/list")
    public String listSpeakers(Model model){
        List<Speakers> speakers = speakerService.listSpeakers();

        int eventId = EventsController.passEventIdToSpeaker();

        List<Speakers> newSpeakersList = new ArrayList<>();

        if(speakers != null){
            for(Speakers speaker: speakers){
                List<Events> eventSpeakers = speaker.getEventsList();
                for(Events eventSpeaker: eventSpeakers){
                    if(eventSpeaker.getId() == eventId){
                        newSpeakersList.add(speaker);
                    }
                }
            }
        }


        model.addAttribute("speaker", newSpeakersList);

        return "list-speakers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Speakers speaker = new Speakers();

        model.addAttribute("speaker", speaker);

        return "speaker-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("speaker") Speakers speaker, Model theModel,
                               BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "speaker-form";
        }

        int eventId = EventsController.passEventIdToSpeaker();

        List<Events> eventsList = speaker.getEventsList();

        if(eventsList == null){
            eventsList = new ArrayList<>();
            Events events = eventsService.getEventById(eventId);
            eventsList.add(events);

            speaker.setEventsList(eventsList);
            speakerService.saveSpeaker(speaker);

            theModel.addAttribute("speaker", speaker);

            return "redirect:/api/speakers/list";
        }

        boolean flag = false;

        for(Events event: eventsList){
            if(event.getId() == eventId){
                flag = true;
                break;
            }
        }

        if(!flag){
            Events events = eventsService.getEventById(eventId);
            eventsList.add(events);

            speaker.setEventsList(eventsList);
            speakerService.saveSpeaker(speaker);
        }
        else{
            String alreadyRegistered = speaker.getSpeakerName() + " has already registered to this event";

            theModel.addAttribute("speaker", alreadyRegistered);

            return "already-register-speaker";
        }

        theModel.addAttribute("speaker", speaker);

        return "redirect:/api/speakers/list";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("speakerId") int theId,
                                    Model theModel) {

        Speakers theSpeaker = speakerService.getSpeakerById(theId);

        theModel.addAttribute("speaker", theSpeaker);

        return "speaker-form";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("speakerId") int theId) {
        speakerService.deleteSpeaker(theId);

        return "redirect:/api/speakers/list";

    }
}
