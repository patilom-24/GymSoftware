package com.gymbackend.demo.service;

import com.gymbackend.demo.model.WorkoutPlan;
import com.gymbackend.demo.model.Member;
import com.gymbackend.demo.repository.WorkoutPlanRepository;
import com.gymbackend.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final MemberRepository memberRepository;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository, MemberRepository memberRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.memberRepository = memberRepository;
    }

    public List<WorkoutPlan> getWorkoutPlansByMember(Long memberId) {
        return workoutPlanRepository.findByMemberId(memberId);
    }

    public List<WorkoutPlan> saveOrUpdateWorkoutPlan(Long memberId, String workoutPlanDetails) {
        // Find existing workout plans for the member
        List<WorkoutPlan> existingPlans = workoutPlanRepository.findByMemberId(memberId);

        if (!existingPlans.isEmpty()) {
            // Update all existing workout plans
            for (WorkoutPlan plan : existingPlans) {
                plan.setWorkoutPlan(workoutPlanDetails);
            }
            return workoutPlanRepository.saveAll(existingPlans);
        } else {
            // Create a new workout plan
            WorkoutPlan newWorkoutPlan = new WorkoutPlan();
            newWorkoutPlan.setWorkoutPlan(workoutPlanDetails);
            newWorkoutPlan.setMember(memberRepository.findById(memberId).orElseThrow());
            return List.of(workoutPlanRepository.save(newWorkoutPlan)); // Returning as List
        }
    }
}
