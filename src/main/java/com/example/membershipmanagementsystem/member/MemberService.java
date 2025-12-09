package com.example.membershipmanagementsystem.member;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void addMember(Member member) {
        memberRepository.save(member);
    }

    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    public List<Member> searchMember(Long memberID) {
        Member member = memberRepository.findById(memberID).orElse(null);
        return member != null ? Collections.singletonList(member) : Collections.emptyList();
    }

    public List<Member> filterMember(String category) {
        List<Member> showAllMembers = memberRepository.findAll();
        return switch (category) {
            case "Active" -> showAllMembers.stream().filter(m -> "Active".equalsIgnoreCase(m.getMembershipStatus())).collect(Collectors.toList());
            case "Inactive" -> showAllMembers.stream().filter(m -> "Inactive".equalsIgnoreCase(m.getMembershipStatus())).collect(Collectors.toList());
            case "Expired" -> showAllMembers.stream().filter(m -> "Expired".equalsIgnoreCase(m.getMembershipStatus())).collect(Collectors.toList());
            case "Basic" -> showAllMembers.stream().filter(m -> "Basic".equalsIgnoreCase(m.getMembershipType())).collect(Collectors.toList());
            case "Premium" -> showAllMembers.stream().filter(m -> "Premium".equalsIgnoreCase(m.getMembershipType())).collect(Collectors.toList());
            default -> showAllMembers;
        };
    }

    public long countMembersActive() {
        return memberRepository.countMembersByMembershipStatus("active");
    }

    public long countMembersInactive() {
        return memberRepository.countMembersByMembershipStatus("inactive");
    }

    public long countMembersExpired() {
        return memberRepository.countMembersByMembershipStatus("expired");
    }
}
