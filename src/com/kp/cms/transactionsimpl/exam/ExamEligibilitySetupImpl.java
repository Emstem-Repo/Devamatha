package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamEligibilitySetupBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamEligibilitySetupImpl extends ExamGenImpl {

	public boolean update(int id, int classId, int examTypeId,
			int isAttendanceChecked, int isCourseFeesChecked,
			int isExamFeesChecked, int isNoEligibilityChecked, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamEligibilitySetupBO objbo = (ExamEligibilitySetupBO) session
					.get(ExamEligibilitySetupBO.class, id);
			objbo.setClassId(classId);
			objbo.setExamTypeId(examTypeId);
			objbo.setIsAttendanceChecked(isAttendanceChecked);
			objbo.setIsCourseFeesChecked(isCourseFeesChecked);
			objbo.setIsExamFeesChecked(isExamFeesChecked);
			objbo.setIsNoEligibilityChecked(isNoEligibilityChecked);
			objbo.setLastModifiedDate(new Date());
			objbo.setModifiedBy(userId);
			session.update(objbo);
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

	@SuppressWarnings("unchecked")
	public void isDuplicated(int classId, int examTypeId) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamEligibilitySetupBO.class);
			crit.add(Restrictions.eq("classId", classId));
			crit.add(Restrictions.eq("examTypeId", examTypeId));

			List<ExamEligibilitySetupBO> list = crit.list();

			if (list.size() > 0) {
				Iterator it = list.iterator();
				ExamEligibilitySetupBO objbo = null;
				while (it.hasNext()) {
					objbo = (ExamEligibilitySetupBO) it.next();

					if (objbo.getIsActive() == true) {
						throw new DuplicateException();
					} else {
						throw new ReActivateException(objbo.getId());
					}
				}
			}
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw e;
		}

	}

	public int insert_returnId(ExamEligibilitySetupBO objBO) throws Exception {
		SessionFactory sessionFactory = InitSessionFactory.getInstance();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {
			session.save(objBO);
			tx.commit();
			id = objBO.getId();
			session.flush();
			session.close();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return id;
	}

	public void delete_AdditionalEligibilitySetUp(int id) throws Exception {
			Session session = null;
			try{
			String HQL_QUERY = "delete from ExamEligibilitySetupAdditionalEligibilityBO e"
					+ " where e.eligibilitySetupId = :id";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", id);
			query.executeUpdate();
			tx.commit();
			}catch (Exception e) {
			
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		


	}

	public void delete_EligibilitySetUp(int id) throws Exception {
			Session session = null;
			
			try{
			String HQL_QUERY = "delete from ExamEligibilitySetupBO e"
					+ " where e.id = :id";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", id);
			query.executeUpdate();
			tx.commit();
			}catch (Exception e) {
			throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}

	}

	public void updateEligibilitySetup_impl(int eligibilitySetupId,
			int isNoEligibilityChecked, int isExamFeesChecked,
			int isAttendanceChecked, int isCourseFeesChecked, String userId,
			Date date) throws Exception {
		Session session = null;
		try{
		String HQL_QUERY = "update ExamEligibilitySetupBO e set e.isAttendanceChecked = :isAttendanceChecked, "
				+ " e.isCourseFeesChecked = :isCourseFeesChecked,"
				+ " e.isExamFeesChecked = :isExamFeesChecked,"
				+ " e.isNoEligibilityChecked = :isNoEligibilityChecked, e.lastModifiedDate = :date,"
				+ " e.modifiedBy = :userId where e.id = :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", eligibilitySetupId);
		query.setParameter("isAttendanceChecked", isAttendanceChecked);
		query.setParameter("isCourseFeesChecked", isCourseFeesChecked);
		query.setParameter("isExamFeesChecked", isExamFeesChecked);
		query.setParameter("isNoEligibilityChecked", isNoEligibilityChecked);
		query.setParameter("userId", userId);
		query.setParameter("date", date);

		query.executeUpdate();
		tx.commit();
		}catch (Exception e) {
		
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public ExamEligibilitySetupBO select_ExamEligibilty(int classId,
			int examTypeId) throws Exception {
		Session session = null;
		ExamEligibilitySetupBO eBO = null;
		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamEligibilitySetupBO.class);
			crit.add(Restrictions.eq("classId", classId));
			crit.add(Restrictions.eq("examTypeId", examTypeId));
			crit.setMaxResults(1);

			List<ExamEligibilitySetupBO> list = crit.list();

			if (list.size() > 0) {
				eBO = list.get(0);
			}
			session.flush();
			session.close();

		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw e;
		}
		return eBO;
	}

	@SuppressWarnings("unchecked")
	public List getAdditionalEligibilitySetUpToEdit(int rowId) throws Exception {
		List list;
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String SQL_QUERY = "select EXAM_eligibility_criteria.id, EXAM_eligibility_criteria.name, "
				+ "EXAM_eligibility_setup_additional_eligibility.additional_eligibility_criteria_id from "
				+ "EXAM_eligibility_criteria left outer join EXAM_eligibility_setup_additional_eligibility on "
				+ "(EXAM_eligibility_criteria.id=EXAM_eligibility_setup_additional_eligibility.additional_eligibility_criteria_id "
				+ " and EXAM_eligibility_setup_additional_eligibility.eligibility_setup_id= :rowId) where "
				+ "EXAM_eligibility_criteria.is_active= :active";

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("rowId", rowId);
		query.setParameter("active", 1);
		list = query.list();
		}
		catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;

	}

	public int getClassId(int id) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String HQL = null;
		int idValue = 0;
		HQL = "select ebo.classId from ExamEligibilitySetupBO ebo where ebo.id= :id";

		Query query = session.createQuery(HQL);
		query.setParameter("id", id);

		query.list();
		if (query.list().size() > 0) {

			idValue = (Integer) query.list().get(0);

		}
		return idValue;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

}
