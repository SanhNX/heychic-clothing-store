package com.heychic.store.controller;

import com.heychic.store.domain.User;
import com.heychic.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/admin")
    public String adminHome(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Double revenue = orderRepository.calculateRevenueByEmployee(user.getId());
        model.addAttribute("revenue", revenue); // Pass total revenue to the view
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


