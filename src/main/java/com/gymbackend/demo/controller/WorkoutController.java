package com.gymbackend.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymbackend.demo.model.Workout;
import com.gymbackend.demo.repository.WorkoutRepository;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addWorkout(@RequestBody Workout workout) {
        workoutRepository.save(workout);
        return ResponseEntity.ok("Workout added successfully!");
    }
}

