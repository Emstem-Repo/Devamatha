package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamAssignExaminerDutiesBO;
import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.bo.exam.ExamTimeTableBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.reports.ScoreSheetTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamAssignExaminerDutiesImpl extends ExamGenImpl {
	
	private static final Log log = LogFactory.getLog(ExamAssignExaminerDutiesImpl.class);

	public String getDateTimeByExamId(int examId) {
		String date = "";
		Session session = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		try{
		String HQL = "select min(et.dateStarttime) from ExamTimeTableBO et "
				+ "join et.examExamCourseSchemeDetailsBO ecs where "
				+ "ecs.examId = :examId and et.isActive=1";

		Query query = session.createQuery(HQL);
		query.setParameter("examId", examId);
		List list = query.list();
		session.flush();
		if (list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object row = (Object) itr.next();
				if (row != null) {
					date = row.toString();
				}

			}
		}
		if (date.length() == 0) {
			date = "0";
		}
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return date;
	}


	public List getEmployeeDetails(int examId, Date dateTime,
			ArrayList<KeyValueTO> invList) throws Exception {
		List list = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			String SQL_QUERY = "SELECT DISTINCT EXAM_examiner_duties.id duty,"
					+ " EXAM_assign_examiner_to_exam.id did,EXAM_assign_examiner_to_exam.employee_id,"
					+ " EXAM_assign_examiner_to_exam.exam_id, employee.first_name,employee.last_name,"
					+ " invDuty.name dutyName,room_nos,remarks,"
					+ " EXAM_examiner_duties.invigilator_duty_type_id,"
					+ " EXAM_examiner_duties.id,d.name departName, ";

			int count = invList.size();
			for (KeyValueTO to : invList) {
				SQL_QUERY = SQL_QUERY + " if(invDuty.id ="
						+ to.getId() + ", 1, 0)";
				count--;

				if (count > 0) {
					SQL_QUERY = SQL_QUERY + ",";
				}
			}

			SQL_QUERY = SQL_QUERY
					+ " FROM EXAM_assign_examiner_to_exam LEFT JOIN EXAM_examiner_duties"
					+ " ON EXAM_examiner_duties.assign_examiner_exam_id = EXAM_assign_examiner_to_exam.id"
					+ " and EXAM_examiner_duties.date_time= :dateTime"
					+ " LEFT JOIN EXAM_invigilator_duty_type invDuty ON "
					+ "(invDuty.id = EXAM_examiner_duties.invigilator_duty_type_id)"
					+ " LEFT JOIN employee ON EXAM_assign_examiner_to_exam.employee_id = employee.id LEFT JOIN department d ON  employee.department_id=d.id"
					+ " WHERE EXAM_assign_examiner_to_exam.exam_id = :examId order by d.name,employee.first_name";
			// + "and EXAM_examiner_duties.date_time= :dateTime";
			Query query = session.createSQLQuery(SQL_QUERY);
			query.setParameter("examId", examId);

			query.setParameter("dateTime", dateTime);
			list = query.list();
		} catch (HibernateException e) {

		}
		session.flush();
		session.close();
		return list;

	}

	public boolean isDateTimeValid(Date dateStarttime, int examId) throws Exception {
		boolean flag = false;
		List list;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		Query query = session
				.createQuery("select max(et.dateStarttime), min(et.dateEndtime) from ExamTimeTableBO et "
						+ "where et.examExamCourseSchemeDetailsBO.examId="
						+ examId + " and et.dateStarttime= :dateStarttime");
		query.setTimestamp("dateStarttime", dateStarttime);
		list = query.list();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object[] object = (Object[]) itr.next();
			if (object[0] != null) {
				flag = true;
			}
		}
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return flag;
	}

	/*
	 * Update a hibernate object into respective table
	 */
	public void updateAssignExaminer_List(
			ArrayList<ExamAssignExaminerDutiesBO> objList) throws Exception {
		SessionFactory sessionFactory = InitSessionFactory.getInstance();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (ExamAssignExaminerDutiesBO obj : objList) {

				ExamAssignExaminerDutiesBO objBO = (ExamAssignExaminerDutiesBO) session
						.get(ExamAssignExaminerDutiesBO.class, obj.getId());

				objBO.setInvigilatorDutyTypeId(obj.getInvigilatorDutyTypeId());
				objBO.setRemarks(obj.getRemarks());
				objBO.setRoomNos(obj.getRoomNos());
				session.update(objBO);

			}
			tx.commit();
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
	}

	public List getListAssignExaminer(int examId, Date dateTime) throws Exception {
		List list = null;
		SessionFactory sessionFactory = InitSessionFactory.getInstance();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "select distinct e.id, e.examAssignExaminerToExamBO.employeeUtilBO.firstName,"
				+ " e.examAssignExaminerToExamBO.employeeUtilBO.lastName, "
				+ "e.examInvigilationDutyBO.name, e.roomNos, e.remarks," +
				" e.examAssignExaminerToExamBO.employeeUtilBO.departmentUtilBO.name from ExamAssignExaminerDutiesBO e"
				+ " where e.examAssignExaminerToExamBO.examId= :examId " +
				" and e.dateTime= :dateTime order by e.examAssignExaminerToExamBO.employeeUtilBO.departmentUtilBO.name,e.examAssignExaminerToExamBO.employeeUtilBO.firstName";

		Query query = session.createQuery(HQL);
		query.setParameter("examId", examId);
		query.setTimestamp("dateTime", dateTime);
		list = query.list();
		}
		catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;

	}

	public void delete_AssignExaminer(Integer id) throws Exception {

		try {
			String HQL_QUERY = "delete from ExamAssignExaminerDutiesBO e"
					+ " where  e.id = :id";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", id);
			query.executeUpdate();
			tx.commit();
			session.close();

		} catch (HibernateException e) {

		}
	}

	public boolean validateRoom(String roomNo) throws Exception {
		
		SessionFactory sessionFactory = InitSessionFactory.getInstance();
		Session session = sessionFactory.openSession();
		try{
		List<ExamTimeTableBO> list;

		Criteria crit = session.createCriteria(ExamRoomMasterBO.class);
		crit.add(Restrictions.eq("roomNo", roomNo));
		list = crit.list();
		if (list.size() > 0) {
			return true;
		}
		return false;
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

	public List<Object[]> select_getExamName() throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select ed.id, ed.name ,ed.year, ed.month, ed.isCurrent "
							+ "from ExamDefinitionBO ed where ed.delIsActive=1 and ed.isActive=1 order by "
							+ "ed.isCurrent desc, ed.year desc, ed.month desc");

			list = query.list();
			session.flush();
		} catch (ApplicationException e) {
			session.close();
			session.flush();
			e.printStackTrace();
		}

		return list;
	}

}
