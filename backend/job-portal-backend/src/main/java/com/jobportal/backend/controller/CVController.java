package com.jobportal.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.backend.entity.CV;
import com.jobportal.backend.service.CVService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cv")
@RequiredArgsConstructor
public class CVController {

    private final CVService cvService;

    @PostMapping("/upload")
    public CV uploadCV(
            @RequestParam Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam String title
    ) throws Exception {
        return cvService.uploadCV(userId, file, title);
    }
}