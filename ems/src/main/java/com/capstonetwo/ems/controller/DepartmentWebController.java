package com.capstonetwo.ems.controller;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.service.DepartmentService;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("${department.web.base}")
public class DepartmentWebController {

    @Autowired
    private DepartmentService departmentService;
    
    @Value("${department.web.base}")
    private String departmentWebBase;

    // Show list of departments
    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("departmentWebBase", departmentWebBase);  // add here
        return "department/list";
    }

    // Show form to create a new department
    @GetMapping("/new")
    public String createDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("departmentWebBase", departmentWebBase);  // add here
        return "department/form";
    }

    // Save a new department
    @PostMapping
    public String saveDepartment(@Valid @ModelAttribute Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departmentWebBase", departmentWebBase);
            return "department/form";  // Return form with error messages
        }
        departmentService.save(department);
        return "redirect:" + departmentWebBase;
    }

    // Show form to edit an existing department
    @GetMapping("/{id}/edit")
    public String editDepartmentForm(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getById(id));
        model.addAttribute("departmentWebBase", departmentWebBase);  // add here
        return "department/form";
    }

    // Update an existing department
    @PostMapping("/{id}")
    public String updateDepartment(@PathVariable Long id, @Valid @ModelAttribute Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departmentWebBase", departmentWebBase);
            return "department/form";  // Return form with error messages
        }
        departmentService.update(id, department);
        return "redirect:" + departmentWebBase;
    }

    // Delete a department
    @GetMapping("/{id}/delete")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.delete(id);
        return "redirect:" + departmentWebBase;
    }
}
