package com.heychic.store.controller;

import com.heychic.store.domain.Brand;
import com.heychic.store.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public String listBrands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "admin/brandList";
    }

    @GetMapping("/add")
    public String showAddBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/addBrand";
    }

    @PostMapping("/add")
    public String addBrand(@ModelAttribute Brand brand) {
        brandService.save(brand);
        return "redirect:/admin/brand/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditBrandForm(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        return "admin/editBrand";
    }

    @PostMapping("/edit")
    public String editBrand(@ModelAttribute Brand brand) {
        brandService.save(brand);
        return "redirect:/admin/brand/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable Long id) {
        brandService.deleteById(id);
        return "redirect:/admin/brand/list";
    }
}
