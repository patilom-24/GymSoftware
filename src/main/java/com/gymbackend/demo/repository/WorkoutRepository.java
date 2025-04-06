package com.gymbackend.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gymbackend.demo.model.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}

