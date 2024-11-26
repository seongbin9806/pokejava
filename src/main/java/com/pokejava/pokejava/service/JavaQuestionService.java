package com.pokejava.pokejava.service;


import com.pokejava.pokejava.dto.JavaQuestionForm;
import com.pokejava.pokejava.dto.ResponseDTO;
import com.pokejava.pokejava.entity.JavaQuestion;
import com.pokejava.pokejava.entity.MyPokemon;
import com.pokejava.pokejava.repository.JavaQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class JavaQuestionService {
    private final JavaQuestionRepository javaQuestionRepository;

    @Autowired
    public JavaQuestionService(JavaQuestionRepository JavaQuesitonRepository) {
        this.javaQuestionRepository = JavaQuesitonRepository;
    }

    @Transactional
    public ResponseDTO createJavaQuestion(JavaQuestionForm form) {
        try{
            JavaQuestion javaQuestion = form.toEntity(); // DTO -> Entity 변환
            javaQuestionRepository.save(javaQuestion);

            return new ResponseDTO(true, "자바 퀴즈 등록 성공", javaQuestion);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(false, "자바 퀴즈 등록 실패");
        }
    }

    @Transactional
    public ResponseDTO deleteJavaQuestion(Long javaQuestionId) {
        try{
            javaQuestionRepository.deleteById(javaQuestionId);

            return new ResponseDTO(true, "자바 퀴즈 삭제 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(false, "자바 퀴즈 삭제 실패");
        }
    }

    @Transactional
    public ResponseDTO getRandomJavaQuestion() {
        JavaQuestion javaQuestion =  javaQuestionRepository.findRandomQuestion();

        return new ResponseDTO(true, "자바 퀴즈 가져오기 성공", javaQuestion);
    }

    @Transactional
    public ResponseDTO getAllJavaQuestion() {
        List<JavaQuestion> javaQuestionList = (List<JavaQuestion>) javaQuestionRepository.findAll(Sort.by(Sort.Order.desc("javaQuestionId")));

        return new ResponseDTO(true, "자바 퀴즈 가져오기 성공", javaQuestionList);
    }
}
