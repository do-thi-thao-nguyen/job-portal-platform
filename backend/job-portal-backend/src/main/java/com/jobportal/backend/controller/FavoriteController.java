package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.FavoriteJob;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.FavoriteJobRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteJobRepository favRepo;
    private final UserRepository userRepo;
    private final JobRepository jobRepo;

    public FavoriteController(FavoriteJobRepository f, UserRepository u, JobRepository j) {
        this.favRepo = f;
        this.userRepo = u;
        this.jobRepo = j;
    }

    // ADD FAVORITE
    @PostMapping("/{jobId}")
    public FavoriteJob add(@PathVariable Long jobId, Authentication auth) {

        User user = userRepo.findByEmail(auth.getName())
                .orElseThrow();

        if (favRepo.existsByUserIdAndJobId(user.getId(), jobId)) {
            throw new RuntimeException("Already saved");
        }

        Job job = jobRepo.findById(jobId).orElseThrow();

        FavoriteJob fav = new FavoriteJob();
        fav.setUser(user);
        fav.setJob(job);

        return favRepo.save(fav);
    }

    // GET MY FAVORITES
    @GetMapping
    public List<FavoriteJob> list(Authentication auth) {

        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
        return favRepo.findByUserId(user.getId());
    }

    // REMOVE
    @DeleteMapping("/{jobId}")
    public String remove(@PathVariable Long jobId, Authentication auth) {

        User user = userRepo.findByEmail(auth.getName()).orElseThrow();

        favRepo.deleteByUserIdAndJobId(user.getId(), jobId);

        return "Removed";
    }
}
