package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.attandance.ICopyPeriodsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CopyPeriodsTxnImpl implements ICopyPeriodsTransaction {
	private static volatile CopyPeriodsTxnImpl copyPeriodsTxnImpl = null;

	public static CopyPeriodsTxnImpl getInstance() {
		if (copyPeriodsTxnImpl == null) {
			copyPeriodsTxnImpl = new CopyPeriodsTxnImpl();
			return copyPeriodsTxnImpl;
		}
		return copyPeriodsTxnImpl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.attandance.ICopyPeriodsTransaction#getClassesByYear
	 * (int)
	 */
	@Override
	public List<ClassSchemewise> getClassesByYear(int fromYear)
			throws Exception {
		Session session = null;
		List<ClassSchemewise> classSchemewises = null;
		try {
			session = HibernateUtil.getSession();
			String str = "from ClassSchemewise cs where cs.classes.isActive =1 and cs.classes.course.isActive = 1 and cs.curriculumSchemeDuration.academicYear="
					+ fromYear;
			Query query = session.createQuery(str);
			classSchemewises = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return classSchemewises;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.attandance.ICopyPeriodsTransaction#getClassesByToYear
	 * (int)
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	@Override
	public Map<String, ClassSchemewise> getClassesByToYear(int toYear)
			throws Exception {
		Session session = null;
		Map<String, ClassSchemewise> map = new HashMap<String, ClassSchemewise>();
		try {
			session = HibernateUtil.getSession();
			String str = "from ClassSchemewise cs where cs.classes.isActive =1 and cs.classes.course.isActive = 1 and cs.curriculumSchemeDuration.academicYear="
					+ toYear;
			Query query = session.createQuery(str);
			List<ClassSchemewise> list = query.list();
			if (list.size() != 0) {
				Iterator<ClassSchemewise> iterator = list.iterator();
				while (iterator.hasNext()) {
					ClassSchemewise csw = (ClassSchemewise) iterator.next();
					map.put(csw.getClasses().getName(), csw);
				}
			}
			return map;
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

	/**
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicate(String query) throws Exception {
		Session session = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			List<ClassSchemewise> classesBO = session.createQuery(query).list();
			if (classesBO != null && !classesBO.isEmpty()) {
				return true;
			} else
				return false;

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.attandance.ICopyPeriodsTransaction#savePeriods
	 * (java.util.List)
	 */
	@Override
	public boolean savePeriods(List<Period> periods) throws Exception {
		boolean isCopied = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			Iterator<Period> iterator = periods.iterator();
			Period period = null;
			while (iterator.hasNext()) {
				period = (Period) iterator.next();
				session.save(period);
			}
			transaction.commit();
			session.flush();
			isCopied = true;
			return isCopied;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
}
