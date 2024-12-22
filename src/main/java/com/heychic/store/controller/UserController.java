package com.heychic.store.controller;

import com.heychic.store.domain.User;
import com.heychic.store.repository.OrderRepository;
import com.heychic.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    // List all users
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> employees = userService.findAllByRoleId(3);
        Map<Long, Double> employeeRevenue = new HashMap<>();
        double totalRevenue = 0.0;

        for (User employee : employees) {
            Double revenue = orderRepository.calculateRevenueByEmployee(employee.getId());
            revenue = (revenue != null) ? revenue : 0.0;
            employeeRevenue.put(employee.getId(), revenue);
            totalRevenue += revenue; // Add revenue to total
        }

        model.addAttribute("employeeRevenue", employeeRevenue);
        model.addAttribute("totalRevenue", totalRevenue); // Pass total revenue to the view
        model.addAttribute("employees", employees);
        return "admin/userList";
    }

    // Show form to add a new user
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/addUser";
    }

    // Handle adding a new user
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin/user/list";
    }

    // Show form to edit an existing user
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID: " + id);
        }
        model.addAttribute("user", user);
        return "editUser";
    }

    // Handle editing an existing user
    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin/user/list";
    }

    // Handle deleting a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/user/list";
    }
}
