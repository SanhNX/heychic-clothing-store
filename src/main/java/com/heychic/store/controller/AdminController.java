package com.heychic.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminHome() {
        return "admin/index"; // Render admin home page
    }

    @GetMapping("/admin/manage-users")
    public String manageUsers(Model model) {
        // Add logic to fetch and display users
        model.addAttribute("users", getAllUsers());
        return "admin/manage-users"; // Render manage users page
    }

    @GetMapping("/admin/reports")
    public String adminReports() {
        return "admin/reports"; // Render admin reports page
    }

    // Mock method to fetch users (Replace with actual service call)
    private Object getAllUsers() {
        return null; // Replace with user fetching logic
    }
}


