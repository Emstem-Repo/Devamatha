package com.kp.cms.transactionsimpl.exam;

/**
 * Dec 29, 2009 Created By 9Elements Team
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamGradeDefinitionImpl extends ExamGenImpl {

	public ExamGradeDefinitionBO loadExamGradeClassDefinition(int id)
			throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			ExamGradeDefinitionBO objEGCDO = (ExamGradeDefinitionBO) session
					.get(ExamGradeDefinitionBO.class, id);
			session.flush();
			//session.close();
			return objEGCDO != null ? objEGCDO : null;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	public boolean isDuplicated(int id, int courseId,
			BigDecimal startPercentage, BigDecimal endPercentage, String grade)
			throws Exception {
		boolean duplicate = false;
		Session session = null;
		try{
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session=HibernateUtil.getSession();
		Criteria crit = session.createCriteria(ExamGradeDefinitionBO.class);
		crit.add(Restrictions.eq("courseId", courseId));

		List<ExamGradeDefinitionBO> list = crit.list();

		if (list.size() > 0) {
			Iterator it = list.iterator();
			ExamGradeDefinitionBO objbo = null;
			while (it.hasNext()) {
				objbo = (ExamGradeDefinitionBO) it.next();
				BigDecimal lowestPerc = objbo.getStartPercentage();
				BigDecimal highestPerc = objbo.getEndPercentage();

				if (grade != null && grade.equalsIgnoreCase(objbo.getGrade())) {
					if (id != objbo.getId()) {
						if (objbo.getIsActive() == true) {
							throw new DuplicateException();
						} else {
							throw new ReActivateException(objbo.getId());
						}
					}
				}
				if ((lowestPerc.doubleValue() <= startPercentage.doubleValue() && startPercentage
						.doubleValue() <= highestPerc.doubleValue())
						|| (lowestPerc.doubleValue() <= endPercentage
								.doubleValue() && endPercentage.doubleValue() <= highestPerc
								.doubleValue())) {

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
		session.flush();
		//session.close();
		}catch(DuplicateException e){
			throw new DuplicateException();
		}catch(ReActivateException e1){
			throw new ReActivateException();
		}
		catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return duplicate;
	}

	public ArrayList<ExamGradeDefinitionBO> select_GradeDefinition(
			ArrayList<Integer> listCourseId) throws Exception  {
		Session session = null;
		ArrayList<ExamGradeDefinitionBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamGradeDefinitionBO.class);
			crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.in("courseId", listCourseId));

			list = new ArrayList<ExamGradeDefinitionBO>(crit.list());
			session.flush();
			//session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamGradeDefinitionBO>();

		}
		return list;

	}

	public boolean update(int id, int courseId, BigDecimal startPercentage,
			BigDecimal endPercentage, String grade, String interpretation,
			String resultClass, BigDecimal gradePoint, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamGradeDefinitionBO objbo = (ExamGradeDefinitionBO) session.get(
					ExamGradeDefinitionBO.class, id);
			objbo.setStartPercentage(startPercentage);
			objbo.setEndPercentage(endPercentage);
			objbo.setGrade(grade);
			objbo.setInterpretation(interpretation);
			objbo.setResultClass(resultClass);
			objbo.setGradePoint(gradePoint);
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

	public List<ExamGradeDefinitionBO> select() throws Exception  {
		{
			Session session = null;
			ArrayList<ExamGradeDefinitionBO> list = null;
			try {
				SessionFactory sessionFactory = HibernateUtil
						.getSessionFactory();
				session = sessionFactory.openSession();

				Criteria crit = session
						.createCriteria(ExamGradeDefinitionBO.class);
				crit.add(Restrictions.eq("isActive", true));

				list = (ArrayList<ExamGradeDefinitionBO>) crit.list();
				session.flush();
				//session.close();
			} catch (Exception e) {
				if (session != null) {
					session.flush();
					session.close();
				}
				list = new ArrayList<ExamGradeDefinitionBO>();

			}
			return list;
		}
	}

	
	public void delete_courseID(int courseId, String userId) throws Exception  {
		ArrayList<ExamGradeDefinitionBO> list = null;
		Session session = null;
		Transaction transaction = null;
		SessionFactory sessionFactory = null;
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(ExamGradeDefinitionBO.class);
			crit.add(Restrictions.eq("courseId", courseId));
			list = (ArrayList<ExamGradeDefinitionBO>) crit.list();
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamGradeDefinitionBO>();
		}

		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();

			for (ExamGradeDefinitionBO eBO : list) {
				eBO.setLastModifiedDate(new Date());
				eBO.setIsActive(false);
				eBO.setModifiedBy(userId);
				session.update(eBO);
			}

			transaction.commit();
			session.flush();
			session.close();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
}
