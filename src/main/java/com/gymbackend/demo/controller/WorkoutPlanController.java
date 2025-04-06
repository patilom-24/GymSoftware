package com.gymbackend.demo.controller;

import com.gymbackend.demo.model.WorkoutPlan;
import com.gymbackend.demo.service.WorkoutPlanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workout")
@CrossOrigin("*")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping("/{memberId}")
    public List<WorkoutPlan> getWorkoutPlansByMember(@PathVariable Long memberId) {
        return workoutPlanService.getWorkoutPlansByMember(memberId);
    }

    @PostMapping("/{memberId}")
    public List<WorkoutPlan> saveOrUpdateWorkoutPlan(@PathVariable Long memberId, @RequestBody String workoutPlanText) {
        return workoutPlanService.saveOrUpdateWorkoutPlan(memberId, workoutPlanText);
    }

    
    @PostMapping("/save")
    public ResponseEntity<String> saveWorkoutPlan(@RequestBody Map<String, Object> request) {
        Long memberId = Long.valueOf(request.get("memberId").toString());
        String workoutPlanDetails = request.get("workoutPlan").toString();

        workoutPlanService.saveOrUpdateWorkoutPlan(memberId, workoutPlanDetails);
        return ResponseEntity.ok("Workout saved");
    }

}
