package com.mastek.topcoders.smartkanteen.service;

import com.mastek.topcoders.smartkanteen.bean.User;

public interface UserService
{
	User getUserById(int userId);

	Boolean registerUser(User user);

	Boolean updateUser(Integer userId, String loginId, String firstName, String lastName, String password,
			String emailId);

	Boolean authenicateUser(String loginId, String password);

	Boolean deleteUser(User user);
}
