package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamInternalCalculationMarksBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;

public class ExamInternalCalMarksImpl extends ExamGenImpl {

	public boolean update(int id, BigDecimal startPercBD, BigDecimal endPercBD,
			BigDecimal marksBD, String theoryPractical, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamInternalCalculationMarksBO objbo = (ExamInternalCalculationMarksBO) session
					.get(ExamInternalCalculationMarksBO.class, id);
			objbo.setStartPercentage(startPercBD);
			objbo.setEndPercentage(endPercBD);
			objbo.setMarks(marksBD);
			if (theoryPractical.equalsIgnoreCase("Theory")) {
				objbo.setTheory(1);
				objbo.setPractical(0);
			} else if (theoryPractical.equalsIgnoreCase("Practical")) {
				objbo.setTheory(0);
				objbo.setPractical(1);
			} else {
				objbo.setTheory(1);
				objbo.setPractical(1);
			}
			objbo.setTheoryPractical(theoryPractical);
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

	public boolean isDuplicated2(int id, int courseId,
			BigDecimal startPercInput, BigDecimal endPercInput,
			BigDecimal marksBD, String theoryPractical) throws Exception {
		boolean duplicate = false;
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		BigDecimal toDB = new BigDecimal(0);
		Criteria crit = session
				.createCriteria(ExamInternalCalculationMarksBO.class);
		crit.add(Restrictions.eq("courseId", courseId));
		crit.add(Restrictions.eq("theoryPractical", theoryPractical));

		List<ExamInternalCalculationMarksBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamInternalCalculationMarksBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamInternalCalculationMarksBO) it.next();
				BigDecimal fromDB = objbo.getStartPercentage();
				toDB = objbo.getEndPercentage();

				if ((fromDB.doubleValue() <= startPercInput.doubleValue() && startPercInput
						.doubleValue() <= toDB.doubleValue())
						|| (fromDB.doubleValue() <= endPercInput.doubleValue() && (endPercInput
								.doubleValue() <= toDB.doubleValue()))) {
					if (id != objbo.getId()) {
						if (objbo.getIsActive() == true) {
							throw new DuplicateException();
						} else {
							throw new ReActivateException(objbo.getId());
						}
					}
				}
			}

		}
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
//	public static void main(String[] args) {
//		try {
//			new ExamInternalCalMarksImpl().isDuplicated(0, 167, new BigDecimal("23"), "Theory");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public boolean isDuplicated(int id, int courseId,
			BigDecimal startPercInput, String theoryPractical) throws Exception {
		boolean duplicate = false;
		Session session = null;
		int tp = 0;
		try{
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		if (theoryPractical.equals("Theory")) {
			tp = 1;
		} else if (theoryPractical.equals("Theory and Practical")) {
			tp = 11;
		}

		Criteria crit = session
				.createCriteria(ExamInternalCalculationMarksBO.class);
		crit.add(Restrictions.eq("courseId", courseId));
		//crit.add(Restrictions.eq("theoryPractical", theoryPractical));
		crit.add(Restrictions.eq("startPercentage", startPercInput));

		List<ExamInternalCalculationMarksBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamInternalCalculationMarksBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamInternalCalculationMarksBO) it.next();
			if (id != objbo.getId()) {

					if (objbo.getTheoryPractical().equals(
							"Theory and Practical")
							&& objbo.getIsActive() == true) {
						throw new DuplicateException();
					}

					else if (objbo.getTheoryPractical().equals("Theory")
							&& objbo.getIsActive() == true
							&& (tp == 1 || tp == 11)) {
						throw new DuplicateException();
					} else if (objbo.getTheoryPractical().equals("Practical")
							&& objbo.getIsActive() == true
							&& (tp == 0 || tp == 11)) {
						throw new DuplicateException();
					}

					else if (objbo.getTheoryPractical().equals(
							"Theory and Practical")
							&& objbo.getIsActive() == false) {
						throw new ReActivateException(objbo.getId());
					}

					else if (objbo.getTheoryPractical().equals("Theory")
							&& objbo.getIsActive() == false
							&& (tp == 1 || tp == 11)) {
						throw new ReActivateException(objbo.getId());
					} else if (objbo.getTheoryPractical().equals("Practical")
							&& objbo.getIsActive() == false
							&& (tp == 0 || tp == 11)) {
						throw new ReActivateException(objbo.getId());
					}

			}
			}
		}
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

}
