package com.kp.cms.transactionsimpl.exam;

/**
 * Dec 31, 2009 Created By 9Elements Team
 */

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ApplicantSubjectGroupUtilBO;
import com.kp.cms.bo.exam.ClassSchemewiseUtilBO;
import com.kp.cms.utilities.HibernateUtil;

public class ExamUpdateCommonSubjectGroupImpl extends ExamGenImpl {

	public ArrayList<ClassSchemewiseUtilBO> select_SubjectGroups(
			ArrayList<Integer> listClassId) {
		Session session = null;
		ArrayList<ClassSchemewiseUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ClassSchemewiseUtilBO.class);
			crit.add(Restrictions.in("classId", listClassId));

			list = new ArrayList<ClassSchemewiseUtilBO>(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ClassSchemewiseUtilBO>();
		}
		return list;
	}

	// Duplicate Check
	public boolean isDuplicated(Integer admnApplnId, Integer courseSubGroupId) {
		Session session = null;
		boolean var = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ApplicantSubjectGroupUtilBO.class);

			crit.add(Restrictions.eq("admnApplnId", admnApplnId));
			crit.add(Restrictions.eq("subjectGroupId", courseSubGroupId));

			List<ApplicantSubjectGroupUtilBO> list = crit.list();

			if (list.size() > 0) {
				var = true;
			}
			session.flush();
			// session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return var;

	}

	// To get student's admission application id
	public Integer getStudentAdmnApplnId(Integer studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.admApplnId" + " from StudentUtilBO s"
				+ " where s.id = :studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;

	}

	public void delPrevSubGrp(Integer applnId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = "delete from ApplicantSubjectGroup a"
				+ " where a.admAppln.id = :applnId";

		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("applnId", applnId);
		query.executeUpdate();

		tx.commit();
		session.flush();
		session.close();

	}

	private static Integer getStuId(Integer admAppId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.id" + " from StudentUtilBO s"
				+ " where s.admApplnUtilBO.id = :admAppId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("admAppId", admAppId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;
	}

	public String getSecondLanguageHistory(Integer stuId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String secondLanguage = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.admApplnUtilBO.personalDataUtilBO.secondLanguage"
				+ " from StudentUtilBO s" + " where s.id = :stuId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("stuId", stuId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				secondLanguage = (list.get(0).toString());

		}

		return secondLanguage;
	}

	public Integer getsubGrpBySecondLanguage(Integer secondLanguage,
			ArrayList<Integer> listCourseSubGroupStudent) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer subGrpId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select sub.id from SubjectGroupUtilBO sub where sub.secondLanguageId = :secondLanguage"
				+ " and id in (:listCourseSubGroupStudent)";
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("secondLanguage", secondLanguage);
		query.setParameterList("listCourseSubGroupStudent",
				listCourseSubGroupStudent);

		List list = query.list();

		if (list != null && list.size() > 0) {
			if (list.get(0) != null)
				subGrpId = (Integer.parseInt(list.get(0).toString()));

		}

		return subGrpId;
	}
}
