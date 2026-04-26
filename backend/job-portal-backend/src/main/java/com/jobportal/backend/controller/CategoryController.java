package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Category;
import com.jobportal.backend.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:3000") // 👈 thêm cho chắc
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // 🔥 API frontend đang gọi
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}