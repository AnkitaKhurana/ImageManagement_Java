package com.ankitakhurana.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ankitakhurana.dtos.ImageDTO;
import com.ankitakhurana.models.User;
import com.ankitakhurana.validations.LoginValidation;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/Login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("user") == null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		} else {
			User user = (User) httpSession.getAttribute("user");
			ImageDTO imageDTO = new ImageDTO();
			List<String> listOFImages = new ArrayList<String>();
			List<String> listOFNames = new ArrayList<String>();
			List<Long> listOFNumber = new ArrayList<Long>();
			List<Double> listOfSize = new ArrayList<Double>();
			listOFImages = imageDTO.getImageList(user);
			listOFNames = imageDTO.getImageName(user);
			listOFNumber = imageDTO.getImageNumber(user);
			listOfSize = imageDTO.getImageSize(user);
			request.setAttribute("pictureList", listOFImages);
			request.setAttribute("nameList", listOFNames);
			request.setAttribute("numberList", listOFNumber);
			request.setAttribute("sizeList", listOfSize);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Image");
			rd.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String redirect;
		int valid = LoginValidation.validateUser(userName, password);
		if (valid != 0) {
			User user = new User();
			user.setUserId(valid);
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("user", user);
			doGet(request, response);
		} else {
			String errorMessage = "Invalid Credentials";
			request.setAttribute("errorMessage", errorMessage);
			redirect = "/login.jsp";
			RequestDispatcher rd = getServletContext().getRequestDispatcher(redirect);
			rd.forward(request, response);
		}

	}

}
