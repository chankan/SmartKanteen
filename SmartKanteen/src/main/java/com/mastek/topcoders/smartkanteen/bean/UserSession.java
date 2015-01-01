package com.mastek.topcoders.smartkanteen.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserSession {
	private String sessionId;
	private User user;
	private int role;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}
