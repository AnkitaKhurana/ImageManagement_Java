package com.ankitakhurana.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.ankitakhurana.dtos.ImageDTO;
import com.ankitakhurana.models.User;
import com.ankitakhurana.services.ImageService;

/**
 * Servlet implementation class ImageController
 */

@MultipartConfig
@WebServlet("/Image")
public class ImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if ("put".equals(request.getParameter("action"))) {
			doPut(request, response);
		} else {
			HttpSession httpSession = request.getSession();
			if (httpSession.getAttribute("user") == null) {

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login");
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
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/image.jsp");
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if ("delete".equals(request.getParameter("action"))) {
			doDelete(request, response);
		} else {

			String redirect, errorMessage;
			User user = new User();
			HttpSession httpSession = request.getSession();
			user = (User) httpSession.getAttribute("user");
			Part filePart = request.getPart("pic");
			if (request.getParameter("content") == null) {
				errorMessage = ImageService.saveImage(filePart, user);
			} else {

				errorMessage = ImageService.editImage(filePart, user, Long.parseLong(request.getParameter("content")),
						request.getParameter("image_name"));
			}
			if (errorMessage.equals("Successful")) {
				doGet(request, response);
			} else {
				redirect = "/image.jsp";
				request.setAttribute("errorMessage", errorMessage);
				RequestDispatcher rd = getServletContext().getRequestDispatcher(redirect);
				rd.forward(request, response);
			}
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = getServletContext()
				.getRequestDispatcher("/upload.jsp?number=" + request.getParameter("content"));
		rd.forward(request, response);

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long numberToDelete = Long.parseLong(request.getParameter("content"));
		HttpSession httpSession = request.getSession();
		User user = new User();
		user = (User) httpSession.getAttribute("user");
		ImageService.deleteImage(numberToDelete, user);
		doGet(request, response);

	}

}
