package com.board.command;


public class UserUpdateCommand {

	private String id;
	
	private String name;
	
	private String role;
	
	private String address;
		
	public UserUpdateCommand() {
		super();
	}

	public UserUpdateCommand(String id, String name, String role, String address) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserUpdateCommand [id=" + id + ", name=" + name + ", role=" + role + ", address=" + address + "]";
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
