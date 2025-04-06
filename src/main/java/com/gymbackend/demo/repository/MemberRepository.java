package com.gymbackend.demo.repository;

import com.gymbackend.demo.model.Member;
import com.gymbackend.demo.model.Member.MembershipStatus;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findByStatus(MembershipStatus status);
    List<Member> findByNameContainingOrEmailContainingOrContactContaining(String name, String email, String mobile);
    
    @Query("SELECT m FROM Member m WHERE m.membershipEndDate = :date")
    List<Member> findMembersExpiringOn(LocalDate date);
    
    @Query("SELECT m FROM Member m WHERE FUNCTION('MONTH', m.membershipEndDate) = :month " +
            "AND FUNCTION('YEAR', m.membershipEndDate) = :year")
     List<Member> findExpiringMembers(@Param("month") int month, @Param("year") int year);

}
