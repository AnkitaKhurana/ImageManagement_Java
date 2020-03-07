package com.ankitakhurana.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ankitakhurana.models.Image;
import com.ankitakhurana.utils.HibernateUtil;
import com.ankitakhurana.validations.ImageValidation;

public class DatabaseService {

	public static List<Integer> GetUser(String username, String password) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String hql = "select U.userId from User U where U.name = :name and U.password = :password";
		Query query = session.createQuery(hql);
		query.setParameter("name", username);
		query.setParameter("password", password);
		@SuppressWarnings("unchecked")
		List<Integer> res = query.getResultList();
		session.getTransaction().commit();
		session.close();
		return res;
	}

	public static Image saveImage(Image image) {
		Session session = HibernateUtil.getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(image);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return image;
	}

	public static String editImage(Image image, long previousImageId) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String hql;
		Query query;
		String errorMessage = null;
		if (image.getImage().length == 0 && image.getName().length() == 0)
			return errorMessage;
		errorMessage = ImageValidation.validateSize(image.getSize());
		if (errorMessage != null)
			return errorMessage;
		if (image.getImage().length != 0 && image.getName().length() != 0) {
			hql = "Update Image I set I.name = :name,I.image = :image,I.size =:size  where I.id = :id and I.user= :user";
			query = session.createQuery(hql);
			query.setParameter("name", image.getName() + ".jpg");
			query.setParameter("image", image.getImage());
			query.setParameter("id", image.getId());
			query.setParameter("user", image.getUser());
			query.setParameter("size", image.getSize());
			int res = query.executeUpdate();
			session.getTransaction().commit();
		} else if (image.getImage().length == 0) {
			hql = "Update Image I set I.name = :name where I.id = :id and I.user = :user";
			query = session.createQuery(hql);
			query.setParameter("name", image.getName() + ".jpg");
			query.setParameter("id", image.getId());
			query.setParameter("user", image.getUser());
			int res = query.executeUpdate();
			session.getTransaction().commit();
		} else if (image.getName().length() == 0) {
			hql = "Update Image I set I.image = :image,I.size =:size where I.id = :id and I.user = :user";
			query = session.createQuery(hql);
			query.setParameter("image", image.getImage());
			query.setParameter("id", image.getId());
			query.setParameter("user", image.getUser());
			query.setParameter("size", image.getSize());
			int res = query.executeUpdate();
			session.getTransaction().commit();
		}
		return errorMessage;
	}
}
