package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamExamResultsForm;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamResultsImpl extends ExamGenImpl {

	public void delete_ExamResults(int id) throws Exception {
		Session session = null;
		try{
			String HQL_QUERY = "delete from ExamPublishExamResultsBO e"
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

	public ExamPublishExamResultsBO select_ExamResults(int id) throws Exception {
		Session session = null;
		ExamPublishExamResultsBO eBO = null;
		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamPublishExamResultsBO.class);
			crit.add(Restrictions.eq("id", id));
			crit.setMaxResults(1);

			List<ExamPublishExamResultsBO> list = crit.list();

			if (list.size() > 0) {
				eBO = list.get(0);
			}
			session.flush();
			//session.close();

		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw e;
		}
		return eBO;

	}

	public List select_ExamResults(ExamExamResultsForm objform) throws Exception {
		Session session = null;
		List list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = (Query) session.createQuery(" select e.id, e.classUtilBO.name, e.examDefinitionBO.name, e.publishDate,"
							   + " e.isPublishOverallInternalCompOnly,sets.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.year  from ExamPublishExamResultsBO e inner join e.classUtilBO cls inner join cls.classSchemewiseUtilBOSet sets "
							   + " where e.examDefinitionBO.academicYear="+objform.getYear()+" and e.examDefinitionBO.id="+objform.getExamName()+" and e.examDefinitionBO.examTypeID="+objform.getExtype());

			list = query.list();

			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList();
		}
		return list;

	}

	public void isDuplicated_ExamResults(int id, int examId, int classId,
			Date date, int publishComponents) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(ExamPublishExamResultsBO.class);
		crit.add(Restrictions.eq("examId", examId));
		crit.add(Restrictions.eq("classId", classId));
		crit.add(Restrictions.eq("isPublishOverallInternalCompOnly",
				publishComponents));
		List list = crit.list();
		if (list.size() > 0) {

			Iterator itr = list.iterator();
			ExamPublishExamResultsBO objbo = null;
			while (itr.hasNext()) {
				objbo = (ExamPublishExamResultsBO) itr.next();
				if (id != objbo.getId()) {
					throw new DuplicateException("Duplicate");
				}
			}

		}
		}catch (Exception e) {
			if(e.getMessage()!=null && e.getMessage().equalsIgnoreCase("Duplicate") )
				throw new DuplicateException();
			else
				throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public void update_ExamResults(int id, int examId, Date date, int classid,
			int publishComponents, String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ExamPublishExamResultsBO objBO = (ExamPublishExamResultsBO) session
					.get(ExamPublishExamResultsBO.class, id);
			objBO.setExamId(examId);
			objBO.setPublishDate(date);
			objBO.setClassId(classid);
			objBO.setIsPublishOverallInternalCompOnly(publishComponents);
			objBO.setLastModifiedDate(new Date());
			objBO.setModifiedBy(userId);
			session.update(objBO);
			transaction.commit();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public int selectInternalComponents(int examId) {
		int id = 0;
		Session session = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query query = (Query) session
				.createQuery("select a.internalExamNameId from ExamInternalExamDetailsBO a where a.examId = :examId");
		query.setParameter("examId", examId);
		List list = query.list();
		session.flush();
		if (list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object row = (Object) itr.next();

				id = Integer.parseInt(row.toString());
			}
		}
		return id;

	}

	public boolean isDateIsValid(int examId, String date, String maxDate) throws Exception {
		boolean flag = false;
		List list;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String SQL_QUERY = "Select count(*) from EXAM_time_table join  "
				+ "EXAM_exam_course_scheme_details ON EXAM_time_table.exam_id = "
				+ "EXAM_exam_course_scheme_details.id  where :maxDate <= :dateStarttime and "
				+ "EXAM_exam_course_scheme_details.exam_course_scheme_id= :examId";
		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("dateStarttime", date);
		query.setParameter("maxDate", maxDate);

		list = query.list();
		int id = 0;
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object row = (Object) itr.next();
			id = Integer.parseInt(row.toString());
			if (id != 0) {
				flag = true;
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

		return flag;

	}

	public String getMaxdate(int examId) throws Exception {
		String id = "";
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String SQL_QUERY = "select max(t.date_endtime) from EXAM_time_table t join  "
				+ "EXAM_exam_course_scheme_details ON "
				+ "t.exam_course_scheme_id = EXAM_exam_course_scheme_details.id where  "
				+ "EXAM_exam_course_scheme_details.exam_id= :examId ";
		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("examId", examId);
		List list = query.list();
		session.flush();
		if (list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object row = (Object) itr.next();
				if (row != null) {
					id = row.toString();
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
		return id;
	}

	public int getcurrentExamID() throws Exception {
		int id = 0;
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query query = (Query) session
				.createQuery("select exam.id from ExamDefinitionBO exam where exam.isCurrent=true");
		List list = query.list();
		session.flush();
		if (list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object row = (Object) itr.next();

				id = Integer.parseInt(row.toString());
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
		return id;
	}
}
