package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamSubjectSectionMasterBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;

/**
 * Dec 14, 2009 Created By 9Elements
 */
@SuppressWarnings("unchecked")
public class ExamSubjectSectionImpl extends ExamGenImpl {

	/**
	 * duplication checking && Reactivation
	 */

	
	public boolean isDuplicated(int id, String name) throws Exception {
		Session session = null;
		boolean duplicate = false;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session
				.createCriteria(ExamSubjectSectionMasterBO.class);
		crit.add(Restrictions.eq("name", name));

		List<ExamSubjectSectionMasterBO> list = crit.list();
		// It is duplicate/ needs reactivation if there is atleast 1 object with
		// same name and different id
		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamSubjectSectionMasterBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamSubjectSectionMasterBO) it.next();

				if (id != objbo.getId()) {
					if (objbo.getIsActive()) {
						throw new DuplicateException();
					} else {
						throw new ReActivateException();
					}
				}
			}

		}
		session.flush();
		session.close();
		return duplicate;
	}

	public boolean reActivateSS(String name, String userId) throws Exception {

		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session1 = sessionFactory.openSession();

			Criteria crit = session1
					.createCriteria(ExamSubjectSectionMasterBO.class);
			crit.add(Restrictions.eq("name", name));

			List<ExamSubjectSectionMasterBO> list = crit.list();

			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();

			if (list.size() > 0) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					ExamSubjectSectionMasterBO objbo = (ExamSubjectSectionMasterBO) it
							.next();
					objbo.setLastModifiedDate(new Date());
					objbo.setModifiedBy(userId);
					objbo.setIsActive(true);
					session.update(objbo);
				}
			}
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

	public ExamSubjectSectionMasterBO loadSSMaster(int id) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			ExamSubjectSectionMasterBO objBO = (ExamSubjectSectionMasterBO) session
					.get(ExamSubjectSectionMasterBO.class, id);
			session.flush();
			session.close();
			return objBO != null ? objBO : null;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
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

			ExamSubjectSectionMasterBO objBO = (ExamSubjectSectionMasterBO) session
					.get(ExamSubjectSectionMasterBO.class, id);
			objBO.setLastModifiedDate(new Date());
			objBO.setIsActive(false);
			objBO.setModifiedBy(userId);

			session.update(objBO);
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

	public boolean update(int id, String name, int isinitialise, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamSubjectSectionMasterBO objSSBO = (ExamSubjectSectionMasterBO) session
					.get(ExamSubjectSectionMasterBO.class, id);
			objSSBO.setLastModifiedDate(new Date());
			objSSBO.setName(name);
			objSSBO.setIsinitialise(isinitialise);
			objSSBO.setModifiedBy(userId);
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
}
