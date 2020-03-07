package com.ankitakhurana.validations;

import java.util.List;
import com.ankitakhurana.services.UserService;

public class LoginValidation {
	public static int validateUser(String userName, String password) {
		List<Integer> res = UserService.GetUser(userName, password);
		int userId;
		if (res.isEmpty())
			userId = 0;
		else
			userId = res.get(0);
		return userId;

	}
}
