package com.gymbackend.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymbackend.demo.model.Member;
import com.gymbackend.demo.model.Member.MembershipStatus;
import com.gymbackend.demo.model.MembershipType;
import com.gymbackend.demo.repository.MemberRepository;
import com.gymbackend.demo.service.MemberService;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/members")
@CrossOrigin("*")
public class MemberController {
    
    private final MemberService memberService;
    
    @Autowired
    MemberRepository memberRepository;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    

    @PostMapping("/add")
    public ResponseEntity<String> saveMember(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("contact") String contact,
            @RequestParam("membership_duration") int membership_duration,
            @RequestParam("paidAmount") double paidAmount,
            @RequestParam("dueAmount") double dueAmount,
            @RequestParam("membershipStartDate") String membershipStartDate,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            // ✅ Create Member Object
            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setContact(contact);
            member.setMembership_duration(membership_duration);
            member.setPaidAmount(paidAmount);
            member.setDueAmount(dueAmount);

            // ✅ Parse & Set Membership Dates
            LocalDate startDate = LocalDate.parse(membershipStartDate);
            member.setMembershipStartDate(startDate);
            member.setMembershipEndDate(startDate.plusMonths(membership_duration));
            member.addRenewalDate(startDate);
            // ✅ Set Status as ACTIVE
            member.setStatus(Member.MembershipStatus.ACTIVE);

            // ✅ Log Before Saving
            System.out.println("Saving Member: " + member);

            // ✅ Save Member
            Member savedMember = memberService.saveMember(member, imageFile);

            // ✅ Validate If Member Is Saved
            if (savedMember == null) {
                System.err.println("Error: Member could not be saved.");
                return ResponseEntity.internalServerError().body("Error: Member could not be saved.");
            }

            // ✅ Log After Saving
            System.out.println("Saved Member ID: " + savedMember.getId());

            return ResponseEntity.ok("Member saved successfully. Membership valid until: " + savedMember.getMembershipEndDate());

        } catch (IOException e) {
            System.err.println("File Upload Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error uploading image: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Unexpected error occurred: " + e.getMessage());
        }
    }
   
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMember(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("contact") String contact,
            @RequestParam("duration") int membershipDuration,
            @RequestParam("paidamount") double paidAmount,
            @RequestParam("dueamount") double dueAmount,
            @RequestParam("startdate") String startDateStr,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            // ✅ Fetch existing member
            Member existingMember = memberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            // ✅ Update fields
            existingMember.setName(name);
            existingMember.setEmail(email);
            existingMember.setContact(contact);
            existingMember.setMembership_duration(membershipDuration);
            existingMember.setPaidAmount(paidAmount);
            existingMember.setDueAmount(dueAmount);
            
            // ✅ Parse start date and calculate end date
            LocalDate startDate = LocalDate.parse(startDateStr);
            existingMember.setMembershipStartDate(startDate);
            existingMember.setMembershipEndDate(startDate.plusMonths(membershipDuration));

            // ✅ Prevent Overwriting Image (if no new image uploaded)
            if (imageFile != null && !imageFile.isEmpty()) {
                existingMember.setImage(imageFile.getBytes());
            }

            // ✅ Save Updated Member
            memberRepository.save(existingMember);

            return ResponseEntity.ok("Member updated successfully!");

        } catch (IOException e) {
            System.err.println("File Upload Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error uploading image: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Unexpected error occurred: " + e.getMessage());
        }
    }



    @GetMapping("/expiring")
    public ResponseEntity<List<Member>> getExpiringMembers(
            @RequestParam int month, 
            @RequestParam int year) {
        
        List<Member> members = memberService.getExpiringMembers(month, year);
        return ResponseEntity.ok(members);
    } 
    
    @GetMapping("/active")
    public List<Member> getActiveMembers() {
        return memberService.getActiveMembers();
    }

    @GetMapping("/blocked")
    public List<Member> getBlockedMembers() {
        return memberService.getBlockedMembers();
    }

    @GetMapping("/expired")
    public List<Member> getExpiredMembers() {
        return memberService.getExpiredMembers();
    }
   
    @PostMapping("/block/{id}")
    public void blockMember(@PathVariable Long id) {
        memberService.blockMember(id);
    }

    @PostMapping("/unblock/{id}")
    public void unblockMember(@PathVariable Long id) {
    	System.out.println(id);
        memberService.unblockMember(id);
    }

    @PostMapping("/renew/{id}") 
    public ResponseEntity<String> renewMembership(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        String response = memberService.renewMembership(id, request);
        if (response.equals("Member not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else if (response.startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/history/{id}")
    public ResponseEntity<List<LocalDate>> getRenewalHistory(@PathVariable Long id) {
        List<LocalDate> history = memberService.getRenewalHistoryByMemberId(id);
        return ResponseEntity.ok(history);
    }
   
    @GetMapping("/expiring/2")
    public List<Member> getExpiringInTwoDays() {
        return memberService.getExpiringMembers(2);
    }

    // Get members whose membership ends in 1 day
    @GetMapping("/expiring/1")
    public List<Member> getExpiringInOneDay() {
        return memberService.getExpiringMembers(1);
    }
    
   

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        return member != null ? ResponseEntity.ok(member) : ResponseEntity.notFound().build();
    }
    @GetMapping("/counts")
    public Map<String, Long> getMemberCounts() {
        return memberService.getMemberCounts();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }
    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam String query) {
        List<Member> members = memberService.searchMembers(query);
        return ResponseEntity.ok(members);
    }
}
