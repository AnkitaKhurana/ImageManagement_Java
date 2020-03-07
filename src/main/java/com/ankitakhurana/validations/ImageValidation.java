package com.ankitakhurana.validations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ankitakhurana.utils.HibernateUtil;

public class ImageValidation {

	public static String validateSize(double fileSize) {
		String errorMessage = null;
		double totalSize = 0;
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		String hql = "Select sum(I.size) from Image I";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Double> res = query.getResultList();
		session.getTransaction().commit();
		session.close();
		if (res.get(0) != null)
			totalSize = res.get(0);
		if (fileSize > 1) {
			errorMessage = "Image size should be smaller than 1 MB";
		} else if ((totalSize + fileSize) > 10) {
			errorMessage = "Total size of images uploaded should be less than 10 MB";
		}
		return errorMessage;
	}
}
