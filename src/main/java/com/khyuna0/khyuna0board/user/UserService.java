package com.khyuna0.khyuna0board.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 유저 등록 서비스 (회원가입)
	public SiteUser create(String username, String password, String email) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		String cryptPassword = passwordEncoder.encode(password); // 암호화 
		user.setPassword(cryptPassword); // 암호화한 비밀번호를 DB에 입력
		user.setEmail(email);
		userRepository.save(user);
		return user;
		
	}
	
	
}
