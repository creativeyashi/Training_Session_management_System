package com.yashika.demo.controller;


import com.yashika.demo.entity.Events;
import com.yashika.demo.entity.Users;
import com.yashika.demo.repository.MyUserDetails;
import com.yashika.demo.services.EventsService;
import com.yashika.demo.services.UsersServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/events")
public class EventsController {
    private EventsService eventsService;
    private UsersServiceInterface usersService;

    // logger added
    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    private static int eventIdToSpeaker;

    @Autowired
    public EventsController(EventsService eventsService,
                            UsersServiceInterface usersService){

        this.eventsService = eventsService;
        this.usersService = usersService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor trimSpaces = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, trimSpaces);
    }

    @GetMapping("/list")
    public String listEvents(Model model){
        List<Events> events = eventsService.listEvents();

        model.addAttribute("event", events);

        return "list-events";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Events event = new Events();

        model.addAttribute("event", event);

        return "event-form";
    }

    @PostMapping("/save")
    public String saveEvent(@Valid @ModelAttribute("event") Events event,
                               BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "event-form";
        }

        eventsService.saveEvent(event);

        return "redirect:/api/events/list";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("eventId") int theId,
                                    Model theModel) {

        Events theEvent = eventsService.getEventById(theId);

        theModel.addAttribute("event", theEvent);

        return "event-form";
    }

    @PostMapping("/register")
    public String showFormRegister(@RequestParam("eventId") int theId,
                                   Model theModel, RedirectAttributes redirAttrs) {

        Events theEvent = eventsService.getEventById(theId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof MyUserDetails){
            String username = ((MyUserDetails)principal).getUsername();
            List<Users> usersList = theEvent.getUsersList();

            boolean flag = false;

            for(Users user: usersList){
                if(user.getUsername().equals(username)){
                    flag = true;
                    break;
                }
            }

            if(!flag){
                // add user as it has not enrolled in the course
                Users users = usersService.getUserByUsername(username);
                usersList.add(users);
                theEvent.setUsersList(usersList);
                eventsService.saveEvent(theEvent);
            }
            else{
                logger.warn(">>>>>> event already registered in the database!!");

                redirAttrs.addFlashAttribute("error", username + " has already registered to this event");
                return "redirect:/api/events/list";
            }
        }

        logger.debug(">>>>>>> event registered in the database");

        theModel.addAttribute("event", theEvent);

        redirAttrs.addFlashAttribute("success", "Registered for the event");

        return "redirect:/api/events/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("eventId") int theId) {
        eventsService.deleteEvent(theId);

        return "redirect:/api/events/list";

    }

    @PostMapping("/eventDetails")
    public String eventDetails(@RequestParam("eventId") int theId,
                               Model theModel){

        Events theEvent = eventsService.getEventById(theId);

        eventIdToSpeaker = theId;

        theModel.addAttribute("event", theEvent);
        return "event-details";
    }

    public static int passEventIdToSpeaker(){
        return eventIdToSpeaker;
    }
}
