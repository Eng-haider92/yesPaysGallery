package com.example.yespaysgallery.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.yespaysgallery.model.User;
import com.example.yespaysgallery.services.UserService;

@Controller
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping(value="/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("user", new User());
        
        return "login";
    }

    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public String getLoggingPage(Model model){
        model.addAttribute("user", new User());

        return "login";
    }
    

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redAttr){
        User existUser = userService.findUserByUsername(user.getUsername());
        if(existUser != null){
            model.addAttribute("existUser", "User is already exist!..Please login!");
            return "login";
        }
        
        else{
            if(bindingResult.hasErrors()){
            return "login";
            }
            else{
                redAttr.addFlashAttribute("successMessage", "Nice....User has been registered successfully, You can login now!!");
                userService.saveUser(user);
                return "redirect:/login";
            }
        }
    }




}

