package com.khyuna0.khyuna0board.question;

import java.time.LocalDateTime;
import java.util.List;

import com.khyuna0.khyuna0board.answer.Answer;
import com.khyuna0.khyuna0board.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity // DB 와 매핑할 entity 클래스로 설정
@Table (name = "question")
@SequenceGenerator (
		name= "QUESTION_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName ="QUESTION_SEQ", // 실제 DB에 있는 시퀀스 이름 
		initialValue = 1, // 시퀀스의 시작 값
		allocationSize = 1 // 시퀀스의 증가치 (생략하면 1)
		)

public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_SEQ_GENERATOR")
	private Integer id;// 질문게시판 글번호 (Pk, 자동 증가)
	
	@Column (length = 200) // 200자 까지 가능
	private String subject; // 질문 게시판 제목
	
	@Column (length = 500) 
	private String content; // 질문 게시판 내용
	
	private LocalDateTime createdate; // 질문 게시판에 글 작성한 시간
	
	// 1 : n 관계 , 질문 글 하나 : 답변 여러개  
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // CascadeType -> 질문 글 삭제될 경우 해당 질문 글의 답변 글도 같이 삭제됨
	private List<Answer> answerlist;
	
	// n : 1 관계
	@ManyToOne
	private SiteUser author; // 글쓴이
	
	private LocalDateTime modifydate; // 질문 글 수정 일시
	
}
