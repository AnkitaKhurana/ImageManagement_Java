package com.ankitakhurana.services;

import java.util.List;

public class UserService {

	public static List<Integer> GetUser(String username, String password) {

		try {

			return DatabaseService.GetUser(username, password);
		} catch (Exception e) {
			return null;
		}

	}

}
