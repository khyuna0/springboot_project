package com.khyuna0.khyuna0board.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity // DB 와 매핑할 entity 클래스로 설정
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 질문게시판 글번호 (Pk, 자동 증가)
	
	@Column (length = 200) // 200자 까지 가능
	private String subject; // 질문 게시판 제목
	
	@Column (columnDefinition = "TEXT") // 글자 수 제한 없이 긴 글도 가능 (255자 이상)
	private String content; // 질문 게시판 내용
	
	@CreatedDate
	private LocalDateTime createDate; // 질문 게시판에 글 작성한 시간
	
	// 1 : n 관계 , 질문 글 하나 : 답변 여러개  
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)  // CascadeType -> 질문 글 삭제될 경우 해당 질문 글의 답변 글도 같이 삭제됨
	private List<Answer> answerList;
	
	
}
