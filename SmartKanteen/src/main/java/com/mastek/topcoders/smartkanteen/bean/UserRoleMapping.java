package com.mastek.topcoders.smartkanteen.bean;

public class UserRoleMapping
{
	private int userRoleMappingId;
	private User user;
	private Role role;

	public int getUserRoleMappingId()
	{
		return userRoleMappingId;
	}

	public void setUserRoleMappingId(int userRoleMappingId)
	{
		this.userRoleMappingId = userRoleMappingId;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	@Override
	public String toString()
	{
		return "UserRoleMapping [userRoleMappingId=" + userRoleMappingId + ", role=" + role + "]";
	}
}
