package com.kp.cms.transactionsimpl.exam;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.utilities.HibernateUtil;

public class ExamOptionalSubjectAssignmentToStudentImpl {

	/**
	 * Feb 16, 2010
	 * Created By 9Elements Team
	 */
	
	//Not Used
	public List selectClasesByAcademicYear(int academicYear) {
		List list;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
// get classes for 
		String SQL_QUERY = "select classId";
		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("year", academicYear);
		query.setParameter("active", 1);
		list = query.list();
		session.flush();
		session.close();
		return list;
	}
}
