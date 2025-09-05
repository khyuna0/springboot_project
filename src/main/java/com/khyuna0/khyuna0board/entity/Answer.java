package com.khyuna0.khyuna0board.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 아이디 (Pk, 자동 증가)
	
	@Column (columnDefinition = "TEXT") // 글자 수 제한 없이 긴 글도 가능 (255자 이상)
	private String content; // 답변 게시판 내용
	
	@CreatedDate
	private LocalDateTime createDate; // 질문 게시판에 글 작성한 시간
	
	// n : 1 관계 -> 답변 다수 : 질문 하나
	@ManyToOne
	private Question question;
	
	
}
