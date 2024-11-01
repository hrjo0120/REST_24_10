package com.koreait.rest_24_10.boundedContext.article.entity;

import com.koreait.rest_24_10.base.entity.BaseEntity;
import com.koreait.rest_24_10.boundedContext.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
    @ManyToOne
    private Member author;
    private String subject;
    private String content;
}
