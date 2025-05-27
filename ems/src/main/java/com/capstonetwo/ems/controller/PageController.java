package com.capstonetwo.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/home")
    public String home() {
        return "home";  // maps to home.html
    }
    
    @GetMapping("/add")
    public String add() {
        return "add";  // maps to add.html
    }
    
    @GetMapping("/search")
    public String search() {
        return "search";  // maps to search.html
    }

    @GetMapping("/update")
    public String update() {
        return "update";  // maps to update.html
    }
    
    @GetMapping("/delete")
    public String delete() {
        return "delete";  // maps to delete.html
    }
    
    @GetMapping("/form")
    public String form() {
        return "form"; // maps to form.html
    }
    
    @GetMapping("/list")
    public String list() {
        return "list"; // maps to list.html
    }
}

