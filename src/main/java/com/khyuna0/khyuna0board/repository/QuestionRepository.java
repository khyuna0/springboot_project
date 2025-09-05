package com.khyuna0.khyuna0board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khyuna0.khyuna0board.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
