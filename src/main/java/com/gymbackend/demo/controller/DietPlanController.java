package com.gymbackend.demo.controller;

import com.gymbackend.demo.model.DietPlan;
import com.gymbackend.demo.service.DietPlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diet")
@CrossOrigin("*")  // Adjust if needed
public class DietPlanController {

    private final DietPlanService dietPlanService;

    public DietPlanController(DietPlanService dietPlanService) {
        this.dietPlanService = dietPlanService;
    }

    @GetMapping
    public List<DietPlan> getAllDietPlans() {
        return dietPlanService.getAllDietPlans();
    }

    @GetMapping("/{memberId}")
    public List<DietPlan> getDietPlansByMember(@PathVariable Long memberId) {
        return dietPlanService.getDietPlansByMember(memberId);
    }

    @PostMapping("/{memberId}")
    public DietPlan addOrUpdateDietPlan(@PathVariable Long memberId, @RequestBody String dietPlanText) {
        return dietPlanService.addOrUpdateDietPlan(memberId, dietPlanText);  // ✅ Corrected method name
    }
    
    @PostMapping("/save")
    public DietPlan addOrUpdateDietPlan(@RequestBody Map<String, Object> requestData) {
        Long memberId = Long.valueOf(requestData.get("memberId").toString());
        String dietPlanText = requestData.get("dietPlan").toString();

        return dietPlanService.addOrUpdateDietPlan(memberId, dietPlanText);  // ✅ Corrected method name
    }
}
