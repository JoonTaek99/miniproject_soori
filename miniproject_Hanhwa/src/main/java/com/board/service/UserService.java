package com.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.board.command.AddUserCommand;
import com.board.command.LoginCommand;
import com.board.dtos.UserDto;
import com.board.mapper.UserMapper;
import com.board.status.RoleStatus;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean addUser(AddUserCommand addUserCommand) {
		
		UserDto mdto = new UserDto();
		mdto.setId(addUserCommand.getId());
		mdto.setName(addUserCommand.getName());
		mdto.setPassword(passwordEncoder.encode(addUserCommand.getPassword()));
		mdto.setAddress(addUserCommand.getAddress());
		mdto.setRole(RoleStatus.EGG+"");
		
		return userMapper.addUser(mdto);
	}
	public String idChk(String id) {
		return userMapper.idChk(id);
	}
	
	public String login(LoginCommand loginCommand
	           ,HttpServletRequest request
	           ,Model model) {
		UserDto dto = userMapper.loginUser(loginCommand.getId());
		String path="user/main";
		if(dto!=null) {
			//로그인 폼에서 입력받은 패스워드값과 DB에 암호화된 패스워드 비교
			if(passwordEncoder.matches(loginCommand.getPassword()
					                  , dto.getPassword())) {
				System.out.println("패스워드 같음: 회원이 맞음");
				//session객체에 로그인 정보 저장
				request.getSession().setAttribute("mdto", dto);
				return path;
			}else {
				System.out.println("패스워드 틀림");
				model.addAttribute("msg", "패스워드를 확인하세요");
				path="home";
			}
		}else {
			System.out.println("회원이 아닙니다. ");
			model.addAttribute("msg", "아이디를 확인하세요");
			path="home";
		}
		
		return path;
		}
	
}
























