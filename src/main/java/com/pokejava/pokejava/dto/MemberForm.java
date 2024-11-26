package com.pokejava.pokejava.dto;

import com.pokejava.pokejava.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberForm {

    private String memberEmail;
    private String memberPassword;

    public Member toEntity() {
        return new Member(memberEmail, memberPassword);
    }
}
