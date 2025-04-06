package com.gymbackend.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "workout_plans")
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob  // <--- This ensures large text storage
    @Column(name = "workout_plan", columnDefinition = "TEXT")
    private String workoutPlan;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWorkoutPlan() { return workoutPlan; }
    public void setWorkoutPlan(String workoutPlan) { this.workoutPlan = workoutPlan; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
}
