package com.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.command.AddUserCommand;
import com.board.command.LoginCommand;
import com.board.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/addUser")
	public String addUserForm(Model model) {
		System.out.println("회원가입폼으로 이동");
		model.addAttribute("addUserCommand", new AddUserCommand());
		
		return "user/addUserForm";
	}
	
	@PostMapping(value = "/addUser")
	public String addUser(@Validated AddUserCommand addUserCommand,
								BindingResult result, Model model) {
		System.out.println("회원가입하기");
		
		if(result.hasErrors()) {
			System.out.println("회원가입 유효값 오류");
			return "user/addUserForm";
		}
		
		try {
			userService.addUser(addUserCommand);
			System.out.println("회원가입 성공ㅋ");
			return "redirect:/";
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("회원가입 실패ㅋ");
			e.printStackTrace();
			return "redirect:addUser";
		}
	}
	
	@ResponseBody
	@GetMapping(value = "/idChk")
	public Map<String, String> idChk(String id){
		System.out.println("ID중복체크");
		
		String resultId = userService.idChk(id);
		
		// json 객체로 보내기 위해 Map에 담아서 응답
		// text라면 그냥 String 으로 보내도 됨
		Map<String, String> map = new HashMap<>();
		map.put("id", resultId);
		
		return map;
	}
	
	
	@PostMapping(value = "/login")
	public String login(@Validated LoginCommand loginCommand, BindingResult result, Model model, HttpServletRequest request) {
		if(result.hasErrors()) {
			System.out.println("로그인 유효값 오류");
			return "redirect:/home";
		}
		
		
		
		String path = userService.login(loginCommand, request, model);
		
		return path;
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		System.out.println("로그아웃ㅂㅂ");
		request.getSession().invalidate();
		return "redirect:/";
	}
	
}
