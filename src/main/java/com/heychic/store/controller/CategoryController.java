package com.heychic.store.controller;

import com.heychic.store.domain.Category;
import com.heychic.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/categoryList";
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/addCategory";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/category/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/editCategory";
    }

    @PostMapping("/edit")
    public String editCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/admin/category/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/category/list";
    }
}
