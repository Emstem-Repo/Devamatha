package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * Dec 14, 2009 Created By 9Elements
 */
public class ExamSpecializationImpl extends ExamGenImpl {

	public boolean isSSDuplicated(int id, String name, int courseID)
			throws Exception {
		boolean notDuplicate = false;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session.createCriteria(ExamSpecializationBO.class);
		crit.add(Restrictions.eq("name", name)); // Like
		crit.add(Restrictions.eq("courseId", courseID)); // Like
		// condition
		List<ExamSpecializationBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				ExamSpecializationBO objbO = (ExamSpecializationBO) it.next();
				if (id != objbO.getId()) {
					if (objbO.getIsActive()) {
						throw new DuplicateException();
					} else {
						throw new ReActivateException(objbO.getId());
					}
				}

			}

		}

		session.flush();
		session.close();
		return notDuplicate;
	}

	/**
	 * 
	 * For Update to the Table
	 */
	public boolean updateSpecilizaction(int id, String name, int courseID,
			String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamSpecializationBO objSSBO = (ExamSpecializationBO) session.get(
					ExamSpecializationBO.class, id);
			objSSBO.setName(name);
			objSSBO.setCourseId(courseID);
			objSSBO.setModifiedBy(userId);
			objSSBO.setLastModifiedDate(new Date());
			session.update(objSSBO);

			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			return false;
		}
	}

	public boolean delete(int id, String userId) throws Exception {

		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamSpecializationBO objSSBO = (ExamSpecializationBO) session.get(
					ExamSpecializationBO.class, id);
			objSSBO.setLastModifiedDate(new Date());
			objSSBO.setIsActive(false);
			objSSBO.setModifiedBy(userId);
			session.update(objSSBO);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return false;
		}

	}

//	public boolean reactivateSpecialization(String specialization,
//			int courseId, String userId) throws Exception {
//
//		Session session = null;
//		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			Session session1 = sessionFactory.openSession();
//
//			Criteria crit = session1.createCriteria(ExamSpecializationBO.class);
//			crit.add(Restrictions.eq("name", specialization));
//			crit.add(Restrictions.eq("courseId", courseId));
//			crit.setMaxResults(1);
//			List<ExamSpecializationBO> list = crit.list();
//
//			session = sessionFactory.openSession();
//			Transaction transaction = session.beginTransaction();
//			transaction.begin();
//
//			if (list.size() > 0) {
//				Iterator it = list.iterator();
//				while (it.hasNext()) {
//					ExamSpecializationBO objbo = (ExamSpecializationBO) it
//							.next();
//					objbo.setLastModifiedDate(new Date());
//					objbo.setModifiedBy(userId);
//					objbo.setIsActive(true);
//					session.update(objbo);
//				}
//			}
//			transaction.commit();
//			session.flush();
//			session.close();
//			return true;
//		} catch (Exception e) {
//			if (session != null) {
//				session.flush();
//				session.close();
//			}
//			return false;
//		}
//	}

	public ExamSpecializationBO loadSSMaster(int id) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			ExamSpecializationBO objBO = (ExamSpecializationBO) session.get(
					ExamSpecializationBO.class, id);
			session.flush();
			session.close();
			return objBO != null ? objBO : null;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

}
