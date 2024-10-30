package com.koreait.rest_24_10.boundedContext.member.service;

import com.koreait.rest_24_10.boundedContext.member.entity.Member;
import com.koreait.rest_24_10.boundedContext.member.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
        memberRepository.save(member);
        return member;
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
