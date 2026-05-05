package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Category;
import com.jobportal.backend.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // CREATE
    @PostMapping
    public Category create(@RequestBody Category category) {

        // check trùng tên
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }

        return categoryRepository.save(category);
    }

    // GET ALL
    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    // GET BY ID (THÊM MỚI)
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category updated) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(updated.getName());

        return categoryRepository.save(category);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);

        return "Deleted category " + id;
    }
}