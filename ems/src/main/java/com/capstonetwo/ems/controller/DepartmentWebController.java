package com.capstonetwo.ems.controller;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.service.DepartmentService;

//import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.web.csrf.CsrfToken;

@Controller
@RequestMapping("/departments")  // e.g. "/departments"
public class DepartmentWebController {

    @Autowired
    private DepartmentService departmentService;
    
    @Value("${department.web.base}")
    private String departmentWebBase;

    // Show list of departments
    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("departmentWebBase", departmentWebBase);  
        return "department/list";  
    }
    
//    @GetMapping("/manage")
//    public String manage(Model model, HttpServletRequest request) {
//    	CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
//    	model.addAttribute("_csrf", csrfToken);
//    	model.addAttribute("departments", departmentService.getAll());
//    	return "admin/departments/manage";  // maps to manage.html
//    }

    // Show form to create a new department
    @GetMapping("/new")
    public String createDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("departmentWebBase", departmentWebBase);  
        return "department/form";  
    }

    // Save a new department
    @PostMapping
    public String saveDepartment(@Valid @ModelAttribute Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departmentWebBase", departmentWebBase);
            return "department/form";  // return with validation errors
        }
        departmentService.save(department);
        return "redirect:" + departmentWebBase;
    }

    // Show form to edit an existing department
    @GetMapping("/{id}/edit")
    public String editDepartmentForm(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getById(id));
        model.addAttribute("departmentWebBase", departmentWebBase);  
        return "department/form";
    }

    // Update an existing department
    @PostMapping("/{id}")
    public String updateDepartment(@PathVariable Long id, @Valid @ModelAttribute Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departmentWebBase", departmentWebBase);
            return "department/form";  // return with validation errors
        }
        departmentService.update(id, department);
        return "redirect:" + departmentWebBase;
    }

//     Delete a department
//    @PostMapping("/{id}/delete")
//    public String deleteDepartment(@PathVariable Long id) {
//        departmentService.delete(id);
//        return "redirect:" + departmentWebBase;
//    }
    
    @PostMapping("/delete")
    public String deleteDepartment(@RequestParam("id") Long id) {
        departmentService.delete(id);
        return "redirect:/admin/departments/manage";
    }

}
