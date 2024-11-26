package com.pokejava.pokejava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JavaQuestion {
    @Id
    @GeneratedValue
    @Column
    private Long javaQuestionId;

    @Column
    private String title;

    @Column
    private String answer;

    @Column
    private LocalDateTime regDate;

    @Column
    private char isUse = 'Y';

    public JavaQuestion(String title, String answer) {
        this.title = title;
        this.answer = answer;
        this.regDate = LocalDateTime.now(); // regDate는 자동으로 현재 시간으로 설정
    }

}
