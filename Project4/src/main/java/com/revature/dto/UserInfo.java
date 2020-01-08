package com.revature.dto;

public class UserInfo {
	private int id;
	private String username;
	private int role;
	private int group;
	public UserInfo() {
		// TODO Auto-generated constructor stub
	}
	public UserInfo(int id, String username, int role, int group) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
		this.group = group;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + group;
		result = prime * result + id;
		result = prime * result + role;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (group != other.group)
			return false;
		if (id != other.id)
			return false;
		if (role != other.role)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", role=" + role + ", group=" + group + "]";
	}
	
	

}
