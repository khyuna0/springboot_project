package com.khyuna0.khyuna0board.answer;

import java.time.LocalDateTime;

import com.khyuna0.khyuna0board.question.Question;
import com.khyuna0.khyuna0board.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "answer")
@SequenceGenerator (
		name= "ANSWER_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName ="ANSWER_SEQ", // 실제 DB에 있는 시퀀스 이름 
		initialValue = 1, // 시퀀스의 시작 값
		allocationSize = 1 // 시퀀스의 증가치 (생략하면 1)
		)
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANSWER_SEQ_GENERATOR")
	private Integer id;// 아이디 (Pk, 자동 증가)
	
	@Column (length = 500) 
	private String content; // 답변 게시판 내용
	
	private LocalDateTime createdate; // 질문 게시판에 글 작성한 시간
	
	// n : 1 관계 -> 답변 다수 : 질문 하나
	@ManyToOne
	private Question question;
	
	// n : 1 관계
		@ManyToOne
		private SiteUser author; // 답변
		
		private LocalDateTime modifydate; // 답 글 수정 일시
}	
