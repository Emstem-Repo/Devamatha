package com.kp.cms.transactionsimpl.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactionsimpl.reports.ScoreSheetTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

public class ExamAssignExaminerImpl extends ExamGenImpl {
	private static final Log log = LogFactory.getLog(ExamAssignExaminerImpl.class);

	@SuppressWarnings("unchecked")
	public List getEmployeeDetails(int examId) throws Exception {
		List list;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String SQL_QUERY = "select distinct employee.id, employee.first_name, employee.last_name, "
				+ "department.id, department.name, EXAM_assign_examiner_to_exam.employee_id from users u "
				+ "join employee ON u.employee_id = employee.id join department "
				+ "ON employee.department_id = department.id left outer join EXAM_assign_examiner_to_exam on"
				+ " (EXAM_assign_examiner_to_exam.employee_id = employee.id "
				+ "AND EXAM_assign_examiner_to_exam.exam_id=:examId) where u.is_teaching_staff =:is_teaching_staff and u.is_active=1";

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("is_teaching_staff", 1);
		list = query.list();
		return list;
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public void delete_ExaminerToExam(int examId, int employeeId) {

		try {
			String SQL_QUERY = "delete from ExamAssignExaminerToExamBO e"
					+ " where  e.examId = :examId and e.employeeId = :employeeId";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("employeeId", employeeId);
			query.executeUpdate();
			session.flush();
			session.close();

		} catch (HibernateException e) {
		}
	}

	public void delete_Exam(int examId) {

		try {
			String SQL_QUERY = "delete from ExamAssignExaminerToExamBO e"
					+ " where  e.examId = :examId";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			org.hibernate.Transaction tx = session.beginTransaction();
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("examId", examId);
			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();

		} catch (HibernateException e) {
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isDeletePossible(int employeeId, int examId) {
		List list;
		boolean retVar = false;
		try {
			String SQL_QUERY = "select d.id from ExamAssignExaminerDutiesBO d where d.examAssignExaminerToExamBO.examId = :examId"
					+ " and d.examAssignExaminerToExamBO.employeeId = :employeeId";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("employeeId", employeeId);
			list = query.list();

			session.flush();
			session.close();
			if (list.size() > 0) {
				retVar = false;
			} else {
				retVar = true;
			}

		} catch (HibernateException e) {
		}

		return retVar;
	}

	public void delete_from_duties(int examId, List<Integer> employeeId) {

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

			Session session = sessionFactory.openSession();
			org.hibernate.Transaction tx = session.beginTransaction();
			String SQL_QUERY = "delete  from ExamAssignExaminerDutiesBO d where "
					+ " d.assignExaminerExamId in"
					+ " (select e.id from ExamAssignExaminerToExamBO e where e.examId = :examId and e.employeeId not in (:employeeId))";

			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameterList("employeeId", employeeId);
			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
		}
	}

	public void delete_from_assign(int examId, List<Integer> employeeId) {

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			String SQL_QUERY = "delete from ExamAssignExaminerToExamBO e"
					+ " where  e.examId = :examId and e.employeeId not in (:employeeId)";
			org.hibernate.Transaction tx = session.beginTransaction();
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameterList("employeeId", employeeId);
			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unchecked")
	public boolean isDuplicate(int employeeId, int examId) {
		boolean retVar = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			List list;

			String SQL_QUERY = "select e.id from ExamAssignExaminerToExamBO e"
					+ " where  e.examId = :examId and e.employeeId = :employeeId";
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("employeeId", employeeId);

			list = query.list();
			session.flush();
			session.close();
			if (list.size() > 0) {
				retVar = true;
			} else {
				retVar = false;
			}

		} catch (HibernateException e) {
		}

		return retVar;
	}

	@SuppressWarnings("unchecked")
	public boolean isDeletePossible(int examId) {
		List list;
		boolean retVar = false;
		try {
			String SQL_QUERY = "select d.id from ExamAssignExaminerDutiesBO d "
					+ "where d.examAssignExaminerToExamBO.examId = :examId";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("examId", examId);
			list = query.list();
			session.flush();
			session.close();
			if (list.size() > 0) {
				retVar = false;
			} else {
				retVar = true;
			}

		} catch (HibernateException e) {
		}

		return retVar;
	}

}
