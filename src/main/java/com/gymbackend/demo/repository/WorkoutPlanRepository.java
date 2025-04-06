package com.gymbackend.demo.repository;

import com.gymbackend.demo.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByMemberId(Long memberId);
}
