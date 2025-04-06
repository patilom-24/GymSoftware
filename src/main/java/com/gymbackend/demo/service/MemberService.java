package com.gymbackend.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymbackend.demo.model.Member;
import com.gymbackend.demo.model.Member.MembershipStatus;
import com.gymbackend.demo.model.MembershipType;
import com.gymbackend.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberService {
    
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    public List<Member> getExpiringMembers(int days) {
        LocalDate targetDate = LocalDate.now().plusDays(days);
        return memberRepository.findMembersExpiringOn(targetDate);
    }

    public List<LocalDate> getRenewalHistoryByMemberId(Long id) {
        return memberRepository.findById(id)
                .map(Member::getRenewalDates)
                .orElse(new ArrayList<>());
    }
    
    @Transactional
    public Member saveMember(Member member, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            member.setImage(imageFile.getBytes()); // Convert image to byte array
        }

        Member savedMember = memberRepository.save(member);
        System.out.println("Saved Member: " + savedMember); // ✅ Debugging Log
        return savedMember;
    }
    
    @Transactional
    public List<Member> getExpiringMembers(int month, int year) {
        return memberRepository.findExpiringMembers(month, year);
    }
    
    
    @Transactional
    public String updateMember(Long id, String memberData, MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Member updatedMember = objectMapper.readValue(memberData, Member.class);

        // Fetch existing member from database
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Update basic details
        existingMember.setName(updatedMember.getName());
        existingMember.setEmail(updatedMember.getEmail());
        existingMember.setContact(updatedMember.getContact());
        existingMember.setMembership_duration(updatedMember.getMembership_duration());
        existingMember.setPaidAmount(updatedMember.getPaidAmount());
        existingMember.setDueAmount(updatedMember.getDueAmount());

        // Calculate new membership end date
        if (updatedMember.getMembership_duration() > 0) {
            existingMember.setMembershipEndDate(LocalDate.now().plusMonths(updatedMember.getMembership_duration()));
        }

        // If a new image is uploaded, update it; otherwise, keep the existing image
        if (image != null && !image.isEmpty()) {
            existingMember.setImage(image.getBytes());
        }

        // Save the updated member
        memberRepository.save(existingMember);
        return "Member updated successfully";
    }
    public List<Member> getMembersByStatus(MembershipStatus status) {
        return memberRepository.findByStatus(status);
    }
    public List<Member> getActiveMembers() {
        return memberRepository.findByStatus(Member.MembershipStatus.ACTIVE);
    }

    public List<Member> getBlockedMembers() {
        return memberRepository.findByStatus(Member.MembershipStatus.BLOCKED );
    }
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
    public List<Member> getExpiredMembers() {
        return memberRepository.findByStatus(Member.MembershipStatus.EXPIRED);
    }
    
    public String renewMembership(Long id, Map<String, Object> request) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (!memberOpt.isPresent()) {
            return "Member not found";
        }

        Member member = memberOpt.get();

        try {
        	int membershipDuration = Integer.parseInt(request.get("membership_duration").toString());
            LocalDate startDate = LocalDate.parse((String) request.get("membershipStartDate"));
            LocalDate endDate = LocalDate.parse((String) request.get("membershipEndDate"));

          
            // Update membership details
            member.setMembership_duration(membershipDuration);
            member.setMembershipStartDate(startDate);
            member.setMembershipEndDate(endDate);
            member.setStatus(Member.MembershipStatus.ACTIVE);

            // Store renewal date in history
            member.addRenewalDate(LocalDate.now());

            // Save the updated member
            memberRepository.save(member);

            return "Membership renewed successfully";
        } catch (Exception e) {
            return "Error renewing membership: " + e.getMessage();
        }
    }
    // Block a member
    public void blockMember(Long memberId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        memberOpt.ifPresent(member -> {
            member.setStatus(MembershipStatus.BLOCKED);
            memberRepository.save(member);
        });
    }

        public List<Member> searchMembers(String query) {
            return memberRepository.findByNameContainingOrEmailContainingOrContactContaining(query, query, query);
        }
   
    // Unblock a member
    public void unblockMember(Long memberId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        memberOpt.ifPresent(member -> {
            member.setStatus(MembershipStatus.ACTIVE);
            memberRepository.save(member);
        });
    }

    public Map<String, Long> getMemberCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("active", memberRepository.findByStatus(MembershipStatus.ACTIVE).stream().count());
        counts.put("expired", memberRepository.findByStatus(MembershipStatus.EXPIRED).stream().count());
        counts.put("blocked", memberRepository.findByStatus(MembershipStatus.BLOCKED).stream().count());
        return counts;
    }
    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // Fetch all members
    }
}
