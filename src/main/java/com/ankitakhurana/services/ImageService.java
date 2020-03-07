package com.ankitakhurana.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ankitakhurana.constants.ImageConstants;
import com.ankitakhurana.models.Image;
import com.ankitakhurana.models.User;
import com.ankitakhurana.utils.HibernateUtil;
import com.ankitakhurana.validations.ImageValidation;

public class ImageService {

	private static String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public static String saveImage(Part filePart, User user) {
		InputStream inputStream = null;
		byte[] imageByte = null;
		Image image = new Image();
		String fileName = null;
		double fileSize = 0;
		String errorMessgae;
		try {
			if (filePart != null) {
				inputStream = filePart.getInputStream();
				imageByte = IOUtils.toByteArray(inputStream);
				fileName = getFileName(filePart);
				if (fileName.contains("\\"))
					fileName = fileName.substring(fileName.lastIndexOf("\\"));
				fileSize = ((imageByte.length) / ImageConstants.convertToMb);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (imageByte == null || imageByte.length == 0)
			errorMessgae = "Image size should be between (0 - 10 MB)";
		else
			errorMessgae = ImageValidation.validateSize(fileSize);
		if (errorMessgae == null) {
			image.setSize(fileSize);
			image.setUser(user);
			image.setName(fileName);
			image.setImage(imageByte);
			DatabaseService.saveImage(image);
			errorMessgae = "Successful";
		}

		return errorMessgae;

	}

	@SuppressWarnings("unchecked")
	public static List<Image> getImages(User user) {
		final String hql = "SELECT I FROM Image I where I.user = :user";
		@SuppressWarnings("rawtypes")
		final Query query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("user", user);
		return query.getResultList();
	}

	public static void deleteImage(Long numberToDelete, User user) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String hql = "Delete from Image I where I.id = :id and I.user = :user";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(hql);
		query.setParameter("id", numberToDelete);
		query.setParameter("user", user);
		query.executeUpdate();
		session.getTransaction().commit();

	}

	public static String editImage(Part filePart, User user, long previousImageId, String imageName) throws IOException {
		byte[] image_type = null;
		InputStream inputStream = null;
		byte[] imageByte;
		Image image = new Image();
		try {
			inputStream = filePart.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageByte = IOUtils.toByteArray(inputStream);
		image.setSize((imageByte.length) / ImageConstants.convertToMb);
		image.setName(imageName);
		image.setImage(imageByte);
		image.setId(previousImageId);
		image.setUser(user);
		return DatabaseService.editImage(image, previousImageId);
	}

}
