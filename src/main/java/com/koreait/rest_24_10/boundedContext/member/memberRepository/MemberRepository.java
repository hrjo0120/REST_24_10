package com.koreait.rest_24_10.boundedContext.member.memberRepository;

import com.koreait.rest_24_10.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
