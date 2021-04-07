package com.kp.cms.transactionsimpl.exam;

/**
 * Dec 25, 2009 Created By 9Elements Team
 */
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamSettingsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;

public class ExamSettingsImpl extends ExamGenImpl {

	public boolean isDuplicated(int id, String absentCodeMarkEntry,
			String notProcedCodeMarkEntry, String withHeldCodeMarkEntry,String securedMarkEntryBy,
			BigDecimal maxAllwdDiffPrcntgMultiEvaluator,
			BigDecimal gradePointForFail, String gradeForFail)
			throws DuplicateException, ReActivateException, ApplicationException {

		boolean duplicate = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		Criteria crit = session.createCriteria(ExamSettingsBO.class);
		// crit.add(Restrictions.eq("absentCodeMarkEntry",
		// absentCodeMarkEntry));
		// crit.add(Restrictions.eq("notProcedCodeMarkEntry",
		// notProcedCodeMarkEntry));
		// crit.add(Restrictions.eq("securedMarkEntryBy", securedMarkEntryBy));
		// crit.add(Restrictions.eq("maxAllwdDiffPrcntgMultiEvaluator",
		// maxAllwdDiffPrcntgMultiEvaluator));
		// crit.add(Restrictions.eq("gradePointForFail", gradePointForFail));
		// crit.add(Restrictions.eq("gradeForFail", gradeForFail));
		crit.setMaxResults(1);
		List<ExamSettingsBO> list = crit.list();

		if (list.size() > 0) {
			throw new DuplicateException();
			// Iterator it = list.iterator();
			// ExamSettingsBO objbo = null;
			// while (it.hasNext()) {
			// objbo = (ExamSettingsBO) it.next();
			// if (id == 0 || id != objbo.getId()) {
			// if (objbo.getIsActive() == true) {
			// throw new DuplicateException();
			// } else {
			// throw new ReActivateException(objbo.getId());
			// }
			// }
			// }
		}
		}catch (DuplicateException e) {
			throw  new DuplicateException(e);
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return duplicate;
	}

	public boolean update(int id, String absentCodeMarkEntry,
			String notProcedCodeMarkEntry,String withHeldCodeMarkEntry, String securedMarkEntryBy,
			BigDecimal maxAllwdDiffPrcntgMultiEvaluator,
			BigDecimal gradePointForFail, String gradeForFail, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();

			ExamSettingsBO objbo = (ExamSettingsBO) session.get(
					ExamSettingsBO.class, id);
			objbo.setAbsentCodeMarkEntry(absentCodeMarkEntry);
			objbo.setNotProcedCodeMarkEntry(notProcedCodeMarkEntry);
			objbo.setWithHeldCodeMarkEntry(withHeldCodeMarkEntry);
			objbo.setSecuredMarkEntryBy(securedMarkEntryBy);
			objbo
					.setMaxAllwdDiffPrcntgMultiEvaluator(maxAllwdDiffPrcntgMultiEvaluator);
			objbo.setGradeForFail(gradeForFail);
			objbo.setGradePointForFail(gradePointForFail);

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

	public void deleteExamSettings() throws Exception {
		

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		try{
		Transaction tx = session.beginTransaction();
		String HQL_QUERY = "delete from ExamSettingsBO ";
		Query query = session.createQuery(HQL_QUERY);

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
}
