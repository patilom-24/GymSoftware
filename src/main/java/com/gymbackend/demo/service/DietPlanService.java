package com.gymbackend.demo.service;

import com.gymbackend.demo.model.DietPlan;
import com.gymbackend.demo.model.Member;
import com.gymbackend.demo.repository.DietPlanRepository;
import com.gymbackend.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DietPlanService {

    private final DietPlanRepository dietPlanRepository;
    private final MemberRepository memberRepository;

    public DietPlanService(DietPlanRepository dietPlanRepository, MemberRepository memberRepository) {
        this.dietPlanRepository = dietPlanRepository;
        this.memberRepository = memberRepository;
    }
     
    public List<DietPlan> getAllDietPlans() {
        return dietPlanRepository.findAll();
    }
    public List<DietPlan> getDietPlansByMember(Long memberId) {
        return dietPlanRepository.findByMemberId(memberId);
    }


    @Transactional
    public DietPlan addOrUpdateDietPlan(Long memberId, String dietPlanDetails) {
        // Find existing diet plans for the member
        List<DietPlan> existingDietPlans = dietPlanRepository.findByMemberId(memberId);

        if (!existingDietPlans.isEmpty()) {
            // Update first existing diet plan (assuming one per member)
            DietPlan dietPlan = existingDietPlans.get(0);
            dietPlan.setDietPlan(dietPlanDetails);
            return dietPlanRepository.save(dietPlan);
        } else {
            // Create a new diet plan if no existing plan is found
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

            DietPlan newDietPlan = new DietPlan();
            newDietPlan.setDietPlan(dietPlanDetails);
            newDietPlan.setMember(member);
            return dietPlanRepository.save(newDietPlan);
        }
    }
}
