package com.gymbackend.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "diet_plans")
public class DietPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob  // <--- This ensures large text storage
    @Column(name = "diet_plan", columnDefinition = "TEXT")
    private String dietPlan;
    
    
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    public DietPlan() {}

    public DietPlan(String dietPlan, Member member) {
        this.dietPlan = dietPlan;
        this.member = member;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDietPlan() { return dietPlan; }
    public void setDietPlan(String dietPlan) { this.dietPlan = dietPlan; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
}
