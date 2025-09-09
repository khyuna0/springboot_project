package com.khyuna0.khyuna0board.user;

import java.time.LocalDateTime;

import com.khyuna0.khyuna0board.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table (name = "siteuser")
@SequenceGenerator (
		name= "USER_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName ="USER_SEQ", // 실제 DB에 있는 시퀀스 이름 
		initialValue = 1, // 시퀀스의 시작 값
		allocationSize = 1 // 시퀀스의 증가치 
		)
public class SiteUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
	private Long id; // 유저 번호 기본키
	
	@Column(unique = true) // 중복 불가
	private String username; // 유저 아이디
	
	@Column
	private String password; // 유저 비밀번호
	
	@Column(unique = true) // 중복 불가
	private String email; // 유저 이메일
}
