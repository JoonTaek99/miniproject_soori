package com.board.dtos;

import org.apache.ibatis.type.Alias;

@Alias(value = "userDto")
public class UserDto {

	private String id;
	private String name;
	private String password;
	private String address;
	private String role;
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDto(String id, String name, String password, String address, String role) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.address = address;
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", password=" + password + ", address=" + address + ", role="
				+ role + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
