package com.jobportal.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.Profile;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.ProfileRepository;
import com.jobportal.backend.repository.UserRepository;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileRepository profileRepo;
    private final UserRepository userRepo;

    public ProfileController(ProfileRepository p, UserRepository u) {
        this.profileRepo = p;
        this.userRepo = u;
    }

    @GetMapping("/me")
    public Profile get(Authentication auth) {
        String email = (String) auth.getPrincipal();
        User user = userRepo.findByEmail(email).orElseThrow();

        Profile p = profileRepo.findByUserId(user.getId());
        return p != null ? p : new Profile();
    }

    @PostMapping
    public Profile save(@RequestBody Profile profile, Authentication auth) {
        String email = (String) auth.getPrincipal();
        User user = userRepo.findByEmail(email).orElseThrow();

        Profile existing = profileRepo.findByUserId(user.getId());
        if (existing != null) profile.setId(existing.getId());

        profile.setUser(user);
        return profileRepo.save(profile);
    }
}