package com.pokejava.pokejava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
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
}
