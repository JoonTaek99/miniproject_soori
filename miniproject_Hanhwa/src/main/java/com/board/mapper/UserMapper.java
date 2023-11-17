package com.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.board.dtos.UserDto;

@Mapper
public interface UserMapper {

	public boolean addUser(UserDto dto);
	
	public String idChk(String id);
	
	public UserDto loginUser(String id);
}
