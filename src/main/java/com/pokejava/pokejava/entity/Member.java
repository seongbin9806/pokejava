package com.pokejava.pokejava.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {

    @Id
    @GeneratedValue
    @Column
    private Long memberId;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private LocalDateTime regDate;

    @Column
    private char isUse = 'Y';

    public Member(String memberEmail, String memberPassword) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.regDate = LocalDateTime.now(); // regDate는 자동으로 현재 시간으로 설정
    }
}
