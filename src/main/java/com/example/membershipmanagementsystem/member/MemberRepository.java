package com.example.membershipmanagementsystem.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    long countMembersByMembershipStatus(String status);
}
