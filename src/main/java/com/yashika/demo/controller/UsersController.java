package com.yashika.demo.controller;


import com.yashika.demo.entity.Roles;
import com.yashika.demo.entity.Users;
import com.yashika.demo.services.RoleService;
import com.yashika.demo.services.UsersServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UsersController {
    private UsersServiceInterface usersService;

    @Autowired
    private RoleService roleService;

    // logger added
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public UsersController(UsersServiceInterface usersService){

        this.usersService = usersService;
    }

    @GetMapping("/list")
    public String listUsers(Model model){
        List<Users> users = usersService.listUsers();

        model.addAttribute("user", users);

        return "list-users";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Users user = new Users();

        model.addAttribute("user", user);

        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") Users users,
                               BindingResult bindingResult, RedirectAttributes redirAttrs){

        if(bindingResult.hasErrors()){
            return "user-form";
        }

        List<Users> allUsers = usersService.listUsers();

        boolean flag = false;

        for(Users user: allUsers){
            if(user.getUsername().equals(users.getUsername())){
                flag = true;
                break;
            }
        }

        if(flag){
            logger.warn(">>>>>> user already registered in the database!!");

            redirAttrs.addFlashAttribute("error", users.getUsername() + " has already registered");
            return "redirect:/api/users/showFormForAdd";
        }

        Roles userRole = roleService.getRoleById(2);
        users.addRole(userRole);

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        users.setPassword(bcrypt.encode(users.getPassword()));

        usersService.saveUser(users);

        logger.debug(">>>>>>> user registered in the database");

        redirAttrs.addFlashAttribute("success", "User Registered");

        return "redirect:/api/users/list";
    }

    @PostMapping("/saveUpdated")
    public String saveUpdatedUser(@Valid @ModelAttribute("user") Users users,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "user-form-update";
        }

        Roles userRole = roleService.getRoleById(2);
        users.addRole(userRole);

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        users.setPassword(bcrypt.encode(users.getPassword()));

        usersService.saveUser(users);

        return "redirect:/api/users/list";

    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("userId") int theId,
                                    Model theModel) {

        Users theUser = usersService.getUserById(theId);

        theModel.addAttribute("user", theUser);

        return "user-form-update";
    }

    @PostMapping ("/delete")
    public String delete(@RequestParam("userId") int theId) {

        Users user = usersService.getUserById(theId);
        user.setRoles(null);

        usersService.deleteUser(theId);

        return "redirect:/api/users/list";

    }
}
