package com.gymbackend.demo.repository;

import com.gymbackend.demo.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
	@Query("SELECT d FROM DietPlan d WHERE d.member.id = :memberId")
    List<DietPlan> findByMemberId(@Param("memberId") Long memberId);
    
}
