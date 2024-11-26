package com.pokejava.pokejava.repository;
import com.pokejava.pokejava.entity.JavaQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface JavaQuestionRepository extends CrudRepository<JavaQuestion, Long> {
    @Query(value = "SELECT * FROM java_question ORDER BY RAND() LIMIT 1", nativeQuery = true)
    JavaQuestion findRandomQuestion();
}