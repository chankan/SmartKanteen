package com.mastek.topcoders.smartkanteen.bean;

// Generated Dec 24, 2014 2:31:35 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User generated by hbm2java
 */
@XmlRootElement
public class User implements java.io.Serializable
{

	private Integer userId;
	private String loginId;
	private String password;
	private String emailId;
	private Date accountCreationDate;
	private UserDetails userDetails;
	private Set<UserRoleMapping> userRoleMappingSet;

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public String getLoginId()
	{
		return loginId;
	}

	public void setLoginId(String loginId)
	{
		this.loginId = loginId;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public Date getAccountCreationDate()
	{
		return accountCreationDate;
	}

	public void setAccountCreationDate(Date accountCreationDate)
	{
		this.accountCreationDate = accountCreationDate;
	}

	public UserDetails getUserDetails()
	{
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails)
	{
		this.userDetails = userDetails;
	}

	public Set<UserRoleMapping> getUserRoleMappingSet()
	{
		return userRoleMappingSet;
	}

	public void setUserRoleMappingSet(Set<UserRoleMapping> userRoleMappingSet)
	{
		this.userRoleMappingSet = userRoleMappingSet;
	}

	@Override
	public String toString()
	{
		return "User [userId=" + userId + ", loginId=" + loginId + ", password=" + password + ", emailId=" + emailId
				+ ", accountCreationDate=" + accountCreationDate + ", userDetails=" + userDetails
				+ ", userRoleMappingSet=" + userRoleMappingSet + "]";
	}
}
