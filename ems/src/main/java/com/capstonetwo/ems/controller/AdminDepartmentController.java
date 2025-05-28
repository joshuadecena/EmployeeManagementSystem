package com.capstonetwo.ems.controller;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.service.DepartmentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/departments")
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Show all departments and the create form
    @GetMapping({"", "/manage"})
    public String manageDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("department", new Department());
        return "admin/departments/manage"; // 
    }

    // Show form to edit existing department (populate the form with data)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("department", departmentService.getById(id));
        return "admin/departments/manage"; //
    }

    // Process create form submission
    @PostMapping
    public String createDepartment(@Valid @ModelAttribute("department") Department department,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAll());
            return "admin/departments/manage"; // 
        }
        departmentService.save(department);
        return "redirect:/admin/departments/manage";
    }

    // Process edit form submission
    @PostMapping("/{id}")
    public String updateDepartment(@PathVariable Long id,
                                   @Valid @ModelAttribute("department") Department department,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAll());
            return "admin/departments/manage"; //
        }
        departmentService.update(id, department);
        return "redirect:/admin/departments/manage";
    }
    
    // Process delete request
    @PostMapping("/delete")
    public String deleteDepartment(@RequestParam("id") Long id) {
        departmentService.delete(id);
        return "redirect:/admin/departments/manage";
    }

}
