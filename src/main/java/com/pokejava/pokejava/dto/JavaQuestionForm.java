package com.pokejava.pokejava.dto;

import com.pokejava.pokejava.entity.JavaQuestion;
import com.pokejava.pokejava.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JavaQuestionForm {
    private String title;
    private String answer;

    public JavaQuestion toEntity() {
        return new JavaQuestion(title, answer);
    }
}
