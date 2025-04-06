package com.gymbackend.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String contact;

    
    private int membership_duration;

    private double paidAmount;
    private double dueAmount;

    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @ElementCollection
    @CollectionTable(name = "membership_history", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "renewal_date")
    private List<LocalDate> renewalDates = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private MembershipStatus status = MembershipStatus.ACTIVE;

    public MembershipStatus getStatus() {
        return status;
    }

    public void setStatus(MembershipStatus status) {
        this.status = status;
    }

    public enum MembershipStatus {
        ACTIVE, EXPIRED, BLOCKED
    }

    @Transient
    private String imageBase64;

    public Member() {}

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
    public List<LocalDate> getRenewalDates() {
        return renewalDates;
    }

    public void setRenewalDates(List<LocalDate> renewalDates) {
        this.renewalDates = renewalDates;
    }

    // Add a new renewal date to the list
    public void addRenewalDate(LocalDate renewalDate) {
        this.renewalDates.add(renewalDate);
    }

    public Member(String name, String email, String contact, int duration,
                  double paidAmount, double dueAmount, LocalDate membershipStartDate,
                  LocalDate membershipEndDate, byte[] image) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.membership_duration = duration;
        this.paidAmount = paidAmount;
        this.dueAmount = dueAmount;
        this.membershipStartDate = membershipStartDate;
        this.membershipEndDate = membershipEndDate;
        this.image = image;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }

    public double getDueAmount() { return dueAmount; }
    public void setDueAmount(double dueAmount) { this.dueAmount = dueAmount; }

    public LocalDate getMembershipStartDate() { return membershipStartDate; }
    public void setMembershipStartDate(LocalDate membershipStartDate) { this.membershipStartDate = membershipStartDate; }

    public LocalDate getMembershipEndDate() { return membershipEndDate; }
    public void setMembershipEndDate(LocalDate newEndDate) { this.membershipEndDate = newEndDate; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

	public int getMembership_duration() {
		return membership_duration;
	}

	public void setMembership_duration(int membership_duration) {
		this.membership_duration = membership_duration;
	}
}

   