package com.kp.cms.transactionsimpl.exam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamAssignExaminerDutiesBO;
import com.kp.cms.bo.exam.ExamAssignStudentsRoomBO;
import com.kp.cms.bo.exam.ExamAssignStudentsRoomStudentListBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactionsimpl.reports.ScoreSheetTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamAssignStudentsToRoomImpl extends ExamGenImpl {
	private static final Log log = LogFactory.getLog(ScoreSheetTransactionImpl.class);  

	public List selectRoomDetails(int roomNo, int examId, Date examDate) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = null;
		HQL = "select distinct abo.examRoomMasterBO.id, abo.examDefinitionBO.name, "
				+ "abo.examRoomMasterBO.roomNo, abo.examRoomMasterBO.examCapacity, "
				+ "count(es.studentId), abo.examRoomMasterBO.internalExamCapacity,abo.dateTime from "
				+ "ExamAssignStudentsRoomBO abo join abo.examAssignStudentsRoomStudentListBOset es "
				+ "where abo.examId= :examId and abo.dateTime= :examDate ";

		if (roomNo > 0) {
			HQL = HQL + " and abo.roomId= :roomNo ";
		}
		HQL = HQL + " group by abo.roomId ";
		Query query = session.createQuery(HQL);
		query.setParameter("examId", examId);
		query.setTimestamp("examDate", examDate);
		if (roomNo > 0) {
			query.setParameter("roomNo", roomNo);
		}

		return query.list();
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

	public List selectExaminars(int examId, String roomNo, Date date) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "select e.id, e.employeeUtilBO.firstName, e.employeeUtilBO.lastName "
				+ "from ExamAssignExaminerDutiesBO eb join "
				+ "eb.examAssignExaminerToExamBO e  where e.examId= :examId and "
				+ "(eb.roomNos LIKE '%,"
				+ roomNo
				+ " ,%' OR eb.roomNos LIKE '"
				+ roomNo
				+ ",%' OR "
				+ "eb.roomNos LIKE '%,"
				+ roomNo
				+ "' OR eb.roomNos LIKE '"
				+ roomNo
				+ "')"
				+ " and eb.dateTime =:date ";
		Query query = session.createQuery(HQL);
		query.setParameter("examId", examId);
		query.setTimestamp("date", date);
		return query.list();
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

	public List getRommDetails(int roomNo) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = null;
		HQL = "select rbo.examCapacity, rbo.alloted, rbo.internalExamCapacity, rbo.comments from "
				+ "ExamRoomMasterBO rbo where rbo.id=:roomTypeId";
		Query query = session.createQuery(HQL);
		query.setParameter("roomTypeId", roomNo);

		return query.list();
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

	public void deleteExaminars(int id) throws Exception {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
		String HQL_QUERY = "delete from ExamAssignExaminerToExamBO e"
			+ " where e.id = :id ";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);

		query.executeUpdate();
		tx.commit();
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

	public void delete_AssignExaminer(Integer id) throws Exception {

	
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();
		try{
		String HQL_QUERY = "delete from ExamAssignExaminerDutiesBO e"
			+ " where  e.assignExaminerExamId = :id";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.executeUpdate();
		tx.commit();
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

	public void deleteStudents(int id) throws Exception {
		

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
		String HQL_QUERY = "delete from ExamAssignStudentsRoomStudentListBO e"
			+ " where e.id = :id ";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);

		query.executeUpdate();
		tx.commit();
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

	public List getSubjects(int examId, ArrayList<Integer> listClassId,
			Date formatDate) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = "select distinct sgs.subjectUtilBO.id, sgs.subjectUtilBO.name, sgs.subjectUtilBO.code from "
				+ "SubjectGroupSubjectsUtilBO sgs where sgs.subjectGroupUtilBO.id in "
				+ "(select css.subjectGroupUtilBO.id from CurriculumSchemeSubjectUtilBO css "
				+ "left join css.curriculumSchemeDurationUtilBO.classSchemewiseUtilBOSet csuSet where "
				+ "csuSet.classId in (:classId) ) and "
				+ "sgs.subjectUtilBO.id in (select et.subjectId from ExamTimeTableBO et where "
				+ "et.examExamCourseSchemeDetailsBO.examId = :examId  " +
				" and et.examExamCourseSchemeDetailsBO.courseId in ( select c.course.id " +
				" from Classes c" +
				" where c.isActive=1 and c.id in (:classId)) and et.examExamCourseSchemeDetailsBO.schemeNo in ( select c.termNumber" +
				" from Classes c where c.isActive=1 and c.id in (:classId))"
				+ " and et.dateStarttime= :date)";

		Query query = session.createQuery(HQL);
		query.setParameterList("classId", listClassId);
		query.setParameter("examId", examId);
		query.setTimestamp("date", formatDate);

		return query.list();

	}

	public List getStudentList(int examId, Date date, String displayorder,
			int roomId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		try{
		HQL = "select distinct aa.id, ar.classUtilBO.name, ar.subjectUtilBO.name, aa.studentUtilBO.registerNo, "
				+ "aa.studentUtilBO.rollNo, aa.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName, aa.isPresent,ar.dateTime,ar.classUtilBO.termNumber,aa.studentUtilBO.id from "
				+ "ExamAssignStudentsRoomBO ar join ar.examAssignStudentsRoomStudentListBOset aa where "
				+ "ar.examId = :examId and ar.roomId= :roomId "
				+ " and ar.dateTime = :date";

		if (displayorder != null && displayorder.length() > 0) {
			if (displayorder.contains("regNo")) {
				HQL = HQL + "  ORDER BY aa.studentUtilBO.registerNo ";
			}
			if (displayorder.contains("rollNo")) {
				HQL = HQL + "  ORDER BY aa.studentUtilBO.rollNo ";
			}

		}
		Query query = session.createQuery(HQL);
		query.setParameter("examId", examId);
		query.setParameter("roomId", roomId);
		query.setTimestamp("date", date);
		return query.list();
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

	public List getDateRoomList(List list, int position, String examDate) throws Exception {

		Iterator itr = list.iterator();
		ArrayList<Object> listOfRoom = new ArrayList<Object>();
		while (itr.hasNext()) {
			Object object[] = (Object[]) itr.next();
			if (object[position] != null) {
				String date = object[position].toString();

				String[] v1 = date.split(" ");
				String d1 = null;
				String time = null;
				for (int i = 0; i < v1.length; i = i + 5) {
					d1 = v1[i];
					time = v1[i + 1];
				}
				String[] v2 = time.split(":");
				String t1 = null;
				String t2 = null;
				String t3 = null;
				for (int j = 0; j < v2.length; j = j + 3) {
					t1 = v2[j];
					t2 = v2[j + 1];
					t3 = v2[j + 2];
				}
				if (t1.equals("00")) {
					t1 = "12";
				}
				String fDate = d1 + " " + t1 + ":" + t2 + ":" + t3;
				if (examDate.equalsIgnoreCase(fDate)) {
					listOfRoom.add(object);
				}
			}
		}

		return listOfRoom;
	}

	public List getInvigilatorList(int examid, Date examDate, String roomNo) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		try{
		HQL = "select a.id, a.examAssignExaminerToExamBO.employeeId,"
				+ " a.examAssignExaminerToExamBO.employeeUtilBO.firstName, a.isPresent,a.dateTime from "
				+ "ExamAssignExaminerDutiesBO a where a.examAssignExaminerToExamBO.examId = :examId and "
				+ "(a.roomNos LIKE '%," + roomNo + " ,%' OR a.roomNos LIKE '"
				+ roomNo + ",%' OR " + "a.roomNos LIKE '%," + roomNo
				+ "' OR a.roomNos LIKE '" + roomNo + "')"
				+ " and a.dateTime =:examDate ";
		;
		Query query = session.createQuery(HQL);
		query.setParameter("examId", examid);
		query.setTimestamp("examDate", examDate);

		return query.list();
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

	
	public List getStudentList_forSubjects(ArrayList<Integer> listSubjects,
			String nonElegibleStudent, ArrayList<Integer> classIds,
			String displayorder, Integer examId, Timestamp timestamp) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "SELECT distinct classes.name as cname, "
				+ " s.id as sid, "
				+ " s.name as sname, st.id as stId, "
				+ " st.register_no, "
				+ " st.roll_no, "
				+ " personal_data.first_name ,classes.id as cid,classes.term_number"
				+ " FROM student st "
				+ " LEFT JOIN applicant_subject_group asg "
				+ " ON asg.adm_appln_id = st.adm_appln_id "
				+ " LEFT JOIN subject_group_subjects sgs "
				+ " ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active =1 "
				+ " LEFT JOIN subject s "
				+ " ON sgs.subject_id = s.id and s.is_active =1 "
				+ " LEFT JOIN class_schemewise "
				+ " ON st.class_schemewise_id = class_schemewise.id "
				+ " LEFT JOIN curriculum_scheme_duration csd on class_schemewise.curriculum_scheme_duration_id = csd.id"
				+ " LEFT JOIN classes "
				+ " ON class_schemewise.class_id = classes.id and classes.is_active =1"
				+ " LEFT JOIN adm_appln "
				+ " ON st.adm_appln_id = adm_appln.id"
				+ " LEFT JOIN personal_data "
				+ " ON adm_appln.personal_data_id = personal_data.id "
				+ " LEFT JOIN EXAM_student_eligibility_check ec "
				+ " ON ec.student_id = st.id "
				+ " WHERE sgs.subject_id IN ( :listSubjects) AND classes.id IN (:classIds) AND adm_appln.is_cancelled = 0"
				+ " AND st.id NOT IN (SELECT r.student_id "
				+ " from EXAM_assign_students_room_studentlist r"
				+ " left join EXAM_assign_students_room e on r.assign_stu_room_id = e.id"
				+ " where e.exam_id =:examId  and e.date_time= :dateTime ) and csd.academic_year = (select e.academic_year from EXAM_definition e where e.id = :examId)";

		if (nonElegibleStudent.equals("off")) {
			HQL = HQL + " AND ec.is_exam_eligible = 1";

		}
		if (displayorder != null && displayorder.length() > 0) {
			if (displayorder.contains("regNo")) {
				HQL = HQL + "  ORDER BY st.register_no ";
			}
			if (displayorder.contains("rollNo")) {
				HQL = HQL + "  ORDER BY st.roll_no ";
			}

		}

		Query query = session.createSQLQuery(HQL);
		query.setParameterList("listSubjects", listSubjects);
		query.setTimestamp("dateTime", timestamp);
		query.setParameterList("classIds", classIds);
		query.setParameter("examId", examId);
		return query.list();
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

	public List getStudentList(int examId, String examDate,
			ArrayList<Integer> listClass, String nonElegibleStudent) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = " SELECT stu.id, classes.id AS classid, classes.NAME, stu.register_no, stu.roll_no,"
				+ " personal_data.first_name, asg.date_time FROM  student stu"
				+ " JOIN adm_appln"
				+ " ON stu.adm_appln_id = adm_appln.id JOIN personal_data"
				+ " ON adm_appln.personal_data_id = personal_data.id"
				+ " LEFT JOIN class_schemewise"
				+ " ON stu.class_schemewise_id = class_schemewise.id"
				+ " JOIN classes ON class_schemewise.class_id = classes.id"
				+ " LEFT JOIN EXAM_assign_students_room_studentlist e"
				+ " ON e.student_id = stu.id"
				+ " LEFT JOIN EXAM_assign_students_room asg"
				+ " ON e.assign_stu_room_id = asg.id AND asg.exam_id = :examId"
				+ " LEFT JOIN EXAM_student_eligibility_check ec"
				+ " ON ec.student_id = stu.id"
				+ " WHERE ec.class_id IN (:listClass)"
				+ " AND stu.id NOT IN (SELECT g.student_id"
				+ " FROM EXAM_assign_students_room_studentlist g)";

		if (nonElegibleStudent.equals("off")) {
			HQL = HQL + " AND ec.is_exam_eligible = 1";
		}

		Query query = session.createSQLQuery(HQL);

		query.setParameter("examId", examId);
		query.setParameterList("listClass", listClass);
		return getDateStudentList(query.list(), 6, examDate);
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

	public List getDateStudentList(List list, int position, String examDate) throws Exception {

		Iterator itr = list.iterator();
		ArrayList<Object> listOfRoom = new ArrayList<Object>();
		while (itr.hasNext()) {
			Object object[] = (Object[]) itr.next();
			if (object[position] == null) {
				listOfRoom.add(object);

			} else {

				if (examDate.equalsIgnoreCase(object[position].toString())) {
					listOfRoom.add(object);
				}
			}
		}

		return listOfRoom;
	}

	public void updateInvigilator(
			ArrayList<ExamAssignExaminerDutiesBO> listInvgilator) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		for (ExamAssignExaminerDutiesBO objBo : listInvgilator) {
			String HQL_QUERY = "update ExamAssignExaminerDutiesBO e set e.isPresent= :isPresent where e.id = :id ";

			Query query = session.createQuery(HQL_QUERY);

			query.setParameter("isPresent", objBo.getIsPresent());
			query.setParameter("id", objBo.getId());

			try {
				query.executeUpdate();
			} catch (HibernateException e) {
			}

		}
		tx.commit();
		session.flush();
		session.close();

	}

	public void updateStudent(
			ArrayList<ExamAssignStudentsRoomStudentListBO> listStudent) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
		for (ExamAssignStudentsRoomStudentListBO objBo : listStudent) {
			String HQL_QUERY = "update ExamAssignStudentsRoomStudentListBO e set e.isPresent= :isPresent where e.id = :id ";

			Query query = session.createQuery(HQL_QUERY);

			query.setParameter("isPresent", objBo.getIsPresent());
			query.setParameter("id", objBo.getId());

			query.executeUpdate();

		}
		tx.commit();
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

	public int isDuplicated(int examId, int subjectId, int classId, int roomId,
			String date) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		Integer id = null;
		try{
		HQL = "select abo.id from ExamAssignStudentsRoomBO abo where abo.examId= :examId and abo.classId= :classId"
				+ " and abo.roomId= :roomId and abo.subjectId= :subjectId";

		Query query = session.createQuery(HQL);
		query.setParameter("examId", examId);
		query.setParameter("classId", classId);
		query.setParameter("roomId", roomId);
		query.setParameter("subjectId", subjectId);

		query.list();
		if (query.list().size() > 0) {

			id = (Integer) query.list().get(0);

		} else {
			id = 0;
		}
		return id;
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

	public int insert(ExamAssignStudentsRoomBO obj) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {
			session.save(obj);
			tx.commit();
			id = obj.getId();
			// session.flush();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return id;
	}

	public List getRoomIds(int roomId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		try{
		HQL = "select abo.id from ExamAssignStudentsRoomBO abo where abo.roomId= :roomId";

		Query query = session.createQuery(HQL);
		query.setParameter("roomId", roomId);

		return query.list();
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

	public void deleteStudents(ArrayList<Integer> list) throws Exception {

		try {
			String HQL_QUERY = "delete from ExamAssignStudentsRoomStudentListBO e"
					+ " where  e.assignStuRoomId IN ( :list)";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(HQL_QUERY);
			query.setParameterList("list", list);
			query.executeUpdate();
			tx.commit();
			session.close();

		} catch (HibernateException e) {
		}

	}

	public void deleteRooms(int id) throws Exception {
		try {
			String HQL_QUERY = "delete from ExamAssignStudentsRoomBO e"
					+ " where  e.roomId IN ( :id)";
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

	public String getDateTimeByExamId(int examId) {
		String date = "";
		Session session = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String HQL = "select min(et.dateStarttime) from ExamTimeTableBO et "
				+ "join et.examExamCourseSchemeDetailsBO ecs where "
				+ "ecs.examId = :examId and ecs.isActive =1 ";
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

		if (date.isEmpty()) {
			date = "0";
		}
		return date;
	}

	public String getRoomNoByRoomId(int roomNoId) throws Exception {

		String roomNo = "";
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String HQL = "select erBo.roomNo from ExamRoomMasterBO erBo "
				+ "where erBo.id= :roomNoId ";
		Query query = session.createQuery(HQL);
		query.setParameter("roomNoId", roomNoId);
		List list = query.list();
		session.flush();
		if (list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object row = (Object) itr.next();
				if (row != null) {
					roomNo = row.toString();
				}

			}
		}

		return roomNo;
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

	public void deleteAssinedExaminars(String roomNo) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();
		try{
		String HQL_QUERY = "DELETE FROM EXAM_examiner_duties  WHERE  "
				+ "room_nos LIKE '%," + roomNo + " ,%' OR room_nos LIKE '"
				+ roomNo + ",%' OR " + "room_nos LIKE '%," + roomNo
				+ "' OR room_nos LIKE '" + roomNo + "'";
		

		Query query = session.createSQLQuery(HQL_QUERY);
		query.executeUpdate();
		tx.commit();
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}	}

	public boolean isDateTimeValid(Date dateStarttime, int examId) throws Exception {
		boolean flag = false;
		List list;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "select max(et.dateStarttime), min(et.dateEndtime) from ExamTimeTableBO et "
				+ "where et.examExamCourseSchemeDetailsBO.examId= :examId"
				+ " and et.dateStarttime= :dateStarttime";
		Query query = session.createQuery(HQL);
		query.setParameter("examId", examId);
		query.setTimestamp("dateStarttime", dateStarttime);
		list = query.list();

		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object[] object = (Object[]) itr.next();
			if (object[0] != null) {
				flag = true;
			}
		}
		return flag;
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

	public ArrayList<ExamDefinitionBO> select_ExamName(int examTypeID) throws Exception {
		Session session = null;
		ArrayList<ExamDefinitionBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamDefinitionBO.class);
			crit.addOrder(Order.desc("isCurrent"));
			crit.add(Restrictions.eq("examTypeID", examTypeID));
			crit.add(Restrictions.eq("delIsActive", true));
			crit.add(Restrictions.eq("isActive", true));
			list = new ArrayList(crit.list());
			session.flush();
			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamDefinitionBO>();

		}
		return list;

	}

	public List getExamNameById(int examTypeId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		try{
		HQL = "select d.id, d.name from ExamDefinitionBO d where "
				+ "d.examTypeID = :examTypeId and d.delIsActive = 1 and  d.isActive = 1 order by d.isCurrent desc";

		Query query = session.createQuery(HQL);
		query.setParameter("examTypeId", examTypeId);

		return query.list();
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

	public Map<Integer, String> getExamNameById1(int examTypeId) throws Exception {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "select d.id, d.name from ExamDefinitionBO d where "
				+ "d.examTypeID = :examTypeId and d.delIsActive = 1 and  d.isActive = 1 order by d.isCurrent desc";

		Query query = session.createQuery(HQL);
		query.setParameter("examTypeId", examTypeId);
		Iterator<Object[]> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			map.put(Integer.parseInt(row[0].toString()), row[1].toString());

		}
		return map;
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

	public String getCurrentExam(int examTypeId){
		String id = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeID= :examTypeID";

		Query query = session.createQuery(HQL);

		query.setParameter("examTypeID", examTypeId);

		List list = null;
		try {
			list = query.list();
		} catch (HibernateException e) {

		}
		Iterator<Object[]> itr = list.iterator();
		while (itr.hasNext()) {
			Object row = (Object) itr.next();
			id = row.toString();

		}
		if (id == null) {
			id = "0";
		}
		return id;
	}

	public int getRoomAlloted(int examId, int roomId, Date examDate) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		try{
		HQL = "select count(es.studentId) from ExamAssignStudentsRoomBO ear join "
				+ "ear.examAssignStudentsRoomStudentListBOset es where ear.roomId = :roomId "
				+ "and ear.examId = :examId and  ear.dateTime= :examDate group by ear.dateTime";

		Query query = session.createQuery(HQL);

		query.setParameter("examId", examId);
		query.setParameter("roomId", roomId);
		query.setTimestamp("examDate", examDate);
		Iterator itr = query.list().iterator();
		int count = 0;
		while (itr.hasNext()) {
			count = Integer.parseInt(itr.next().toString());

		}
		return count;
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

	public List getStudentListForExamSupplementary(
			ArrayList<Integer> listSubjects, String nonEligible,
			ArrayList<Integer> listClass, String displayOrder, int examId,
			Timestamp timestamp,String type) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "select bo.classes.name," +
				"bo.subjectUtilBO.id,bo.subjectUtilBO.name,bo.studentUtilBO.id," +
				"bo.studentUtilBO.registerNo,bo.studentUtilBO.rollNo," +
				"bo.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName,bo.classes.id,bo.classes.termNumber" +
				" from ExamSupplementaryImprovementApplicationBO bo" +
				" join bo.classes.classSchemewises cs" +
				" where bo.classes.id in (:classIds) and bo.subjectId in (:listSubjects)" +
				" and bo.examDefinitionBO.id=:examId" +
				" and (bo.isSupplementary=1 or bo.isImprovement=1)" +
				" and bo.studentId not in (select r.studentId from ExamAssignStudentsRoomStudentListBO r" +
				" left join r.examAssignStudentsRoomBO e where e.examId=:examId and e.dateTime=:dateTime)" +
				" and cs.curriculumSchemeDuration.academicYear >= (select ed.examForJoiningBatch from ExamDefinitionBO ed" +
				" where ed.id=:examId and ed.isActive=1 )";
		if(type!=null && type.length()>0){
			if(type.equals("1")){
				HQL=HQL+" and bo.isAppearedTheory=1";
			}else if(type.equals("0")){
				HQL=HQL+" and bo.isAppearedPractical=1";
			}else{
				HQL=HQL+" and bo.isAppearedTheory=1 and bo.isAppearedPractical=1";
			}
		}
		if (displayOrder != null && displayOrder.length() > 0) {
			if (displayOrder.contains("regNo")) {
				HQL = HQL + "  order by bo.studentUtilBO.registerNo ";
			}
			if (displayOrder.contains("rollNo")) {
				HQL = HQL + "  order by bo.studentUtilBO.rollNo ";
			}

		}

		Query query = session.createQuery(HQL);
		query.setParameterList("listSubjects", listSubjects);
		query.setTimestamp("dateTime", timestamp);
		query.setParameterList("classIds", listClass);
		query.setParameter("examId", examId);
		return query.list();
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

}
