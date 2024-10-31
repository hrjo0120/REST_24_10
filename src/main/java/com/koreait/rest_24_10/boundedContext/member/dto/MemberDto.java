package com.koreait.rest_24_10.boundedContext.member.dto;

import com.koreait.rest_24_10.boundedContext.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private LocalDateTime regDate;
    private String userName;

    //외부에 노출용 데이터를 만드는 것
    // 내가 작업을 했는데 외부에 노출이 되지않도록 한 겹 더 감싸는 것?
    // 이 양식은 변하지 않음.
    private MemberDto(Member member) {
        this.id = member.getId();
        this.userName = member.getUsername();
        this.regDate = member.getCreateDate();
    }

    public static MemberDto of(Member member) {
        return new MemberDto(member);
    }

}


