package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamStudentEligibilityCheckBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamStudentEligibilityCheckImpl extends ExamGenImpl {
	private static final Log log = LogFactory
			.getLog(ExamStudentEligibilityCheckImpl.class);

	public List select_StudentEli(ArrayList<Integer> listClassIds,
			Integer eligibilityCriteria) throws Exception {

		String HQL = "select s.id, s.rollNo, s.registerNo, s.admApplnUtilBO.personalDataUtilBO.firstName,"
				+ " s.admApplnUtilBO.personalDataUtilBO.lastName,  e.isEligibile, e.id, e.classId,  e.examId, , e.eligibilityCriteriaId   "
				+ " from  StudentUtilBO s "
				+ " left outer join  s.examStudentEligibilityCheckBOSet e  with e.eligibilityCriteriaId = :eligibilityCriteria "
				+ " where  s.classSchemewiseUtilBO.classId in (:listClassIds)";

		Session session = null;
		List list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = (Query) session.createQuery(HQL);
			query.setParameter("listClassIds", listClassIds);
			query.setParameter("eligibilityCriteria", eligibilityCriteria);

			list = query.list();

			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}

	public void delete(ArrayList<Integer> listClassId, int eligibilityCriteriaId) throws Exception {

		String HQL = "delete from ExamStudentEligibilityCheckBO e where e.classId in (:listClassId) "
				+ "and e.eligibilityCriteriaId = :eligibilityCriteriaId  ";

		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = (Query) session.createQuery(HQL);
			query.setParameter("listClassId", listClassId);
			query.setParameter("eligibilityCriteriaId", eligibilityCriteriaId);

			query.executeUpdate();

			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}

	}

	public ArrayList<ExamTypeUtilBO> getListExamType() throws Exception {
		ArrayList<ExamTypeUtilBO> listBO = new ArrayList(
				select_All(ExamTypeUtilBO.class));
		return listBO;
	}

	// To FETCH students based on display(Eligible,Non - Eligible,Both)
	public List<Object[]> select_students_elig_nonElig(
			ArrayList<Integer> listClass, int examTypeId, String display) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";
		try{
		HQL_QUERY = "select"
				+ " s.studentUtilBO.rollNo,"
				+ " s.studentUtilBO.classSchemewiseUtilBO.classUtilBO.name,"
				+ " s.studentUtilBO.registerNo,"
				+ " s.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName "
				+ " from" + " ExamStudentEligibilityEntryBO s " + " where"
				+ " s.eligibilityCriteriaId in (" + " select"
				+ " e.additionalEligibilityCriteriaId " + " from"
				+ " ExamEligibilitySetupAdditionalEligibilityBO e " + " where"
				+ " e.examEligibilitySetupBO.examTypeId = :examTypeId "
				+ " and e.examEligibilitySetupBO.classId in (:listClass ))";

		if (display.equalsIgnoreCase("E")) {

			HQL_QUERY = HQL_QUERY + " and s.isEligibile = 1";

		} else if (display.equalsIgnoreCase("N")) {

			HQL_QUERY = HQL_QUERY + " and s.isEligibile = 0";

		} else if (display.equalsIgnoreCase("B")) {

			HQL_QUERY = HQL_QUERY + " and s.isEligibile in (1, 0)";

		}
		Query query = session.createQuery(HQL_QUERY);
		query.setParameterList("listClass", listClass);
		query.setParameter("examTypeId", examTypeId);
		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// To get course for a class
	public Integer get_course_for_class(int classId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List l = null;
		Integer courseIds = null;
		try{
		String HQL_QUERY = "select c.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId"
				+ " from ClassSchemewiseUtilBO c"
				+ " where c.classId = :classId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);

		l = query.list();

		if (l != null && l.size() > 0) {

			courseIds = (Integer) l.get(0);
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return courseIds;

	}

	// To get academic year for a class
	public Integer get_academicYear_for_class(int classId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List l = null;
		Integer courseIds = null;
		try{
		String HQL_QUERY = "select c.curriculumSchemeDurationUtilBO.academicYear"
				+ " from ClassSchemewiseUtilBO c"
				+ " where c.classId = :classId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);

		l = query.list();

		if (l != null && l.size() > 0) {

			courseIds = (Integer) l.get(0);
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return courseIds;

	}

	// To get students for a class
	public ArrayList get_students_for_class(int classId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList l = null;
		try{
		String HQL_QUERY = "select  s.id" + " from ClassSchemewiseUtilBO c"
				+ " join c.studentUtilBOSet s" + " where c.classId = :classId";
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		l = new ArrayList<Integer>(query.list());
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Integer>();
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// To FETCH the students and fee payment status
	public List<Object[]> get_eligCheck_details(Integer course,
			Integer academicYear, List<Integer> students) throws Exception {
		if (students != null && students.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			List<Object[]> l = null;
			String HQL_QUERY = "";
			try{
			HQL_QUERY = " SELECT distinct student.id, ifnull(ec.is_course_fees_paid, ifnull(f.is_fee_paid,0))"
					+ " FROM student"
					+ " LEFT JOIN fee_payment f"
					+ " ON f.student_id = student.id"
					+ " AND f.course_id = :course"
					+ " AND f.academic_year = :academicYear"
					+ " LEFT JOIN EXAM_student_eligibility_check ec"
					+ " ON ec.student_id = student.id"
					+ " LEFT JOIN EXAM_student_eligibility_check_criteria ecc"
					+ " ON ecc.stu_eligibility_chk_id = ec.id"
					+ " WHERE student.id IN (:students)";

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("course", course);
			query.setParameter("academicYear", academicYear);
			query.setParameterList("students", students);

			l = query.list();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		}
			
		return new ArrayList<Object[]>();
	}

	// To FETCH the students and fee payment status
	public List<Object[]> get_studentDetails_courseFees(Integer course,
			Integer academicYear, List<Integer> students) throws Exception {
		if (students != null && students.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			List<Object[]> l = null;
			String HQL_QUERY = "";
			try{
			HQL_QUERY = " SELECT distinct student.id,"
					+ " student.roll_no,"
					+ " classes.name,"
					+ " student.register_no,"
					+ " personal_data.first_name,"
					+ " esec.id as esecid,"
					+ " esecc.id as eseccid,"
					+ " esec.remarks,"
					+ " ifnull(esec.is_exam_eligible,0)"
					+ " FROM student"
					+ " left join class_schemewise ON student.class_schemewise_id = class_schemewise.id"
					+ " left join classes ON class_schemewise.class_id = classes.id"
					+ " LEFT JOIN adm_appln"
					+ " ON student.adm_appln_id = adm_appln.id"
					+ " LEFT JOIN personal_data"
					+ " ON adm_appln.personal_data_id = personal_data.id"
					+ " LEFT JOIN fee_payment f"
					+ " ON f.student_id = student.id"
					+ " AND f.course_id = :course"
					+ " AND f.academic_year = :academicYear"
					+ " left join EXAM_student_eligibility_check esec on esec.student_id = student.id"
					+ " left join EXAM_student_eligibility_check_criteria esecc on esecc.stu_eligibility_chk_id = esec.id"
					+ "  WHERE student.id IN (:students)";

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("course", course);
			query.setParameter("academicYear", academicYear);
			query.setParameterList("students", students);

			l = query.list();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		}
		return new ArrayList<Object[]>();
	}

	public int insert_returnId(ExamStudentEligibilityCheckBO objBO) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int mid = 0;
		try {
			session.save(objBO);
			tx.commit();
			mid = objBO.getId();
			session.flush();
			// session.close();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}

		return mid;
	}

	// To FETCH additional eligibility list for a student
	public List<Object[]> getListAdditionalEligibility_list(Integer classId,
			Integer examTypeId, ArrayList<Integer> studentId) throws Exception {

		if (studentId != null && studentId.size() > 0) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			List<Object[]> l = null;
			String HQL_QUERY = "";
			try{
			HQL_QUERY = "SELECT distinct"
					+ " student.id,"
					+ " b.additional_eligibility_criteria_id,"
					+ " ifnull(esecc.is_eligibile, ifnull(ese.is_eligibile,0)),"
					+ " esecc.id as detailId"
					+ " FROM student"
					+ " JOIN (SELECT a.additional_eligibility_criteria_id"
					+ " FROM EXAM_eligibility_setup_additional_eligibility a"
					+ " JOIN"
					+ " EXAM_eligibility_setup ees"
					+ " ON a.eligibility_setup_id = ees.id"
					+ " WHERE ees.class_id = :classId and ees.exam_type_id = :examTypeId) b"
					+ " LEFT JOIN EXAM_student_eligibility ese"
					+ " ON ese.student_id = student.id and ese.eligibility_criteria_id = b.additional_eligibility_criteria_id"
					+ " LEFT JOIN EXAM_student_eligibility_check esec"
					+ " ON esec.student_id = student.id"
					+ " LEFT JOIN EXAM_student_eligibility_check_criteria esecc"
					+ "  ON esecc.stu_eligibility_chk_id = esec.id"
					+ "  and esecc.eligibility_criteria_id =  b.additional_eligibility_criteria_id"
					+ " WHERE student.id in (:studentId)"
					+ " order by student.id,b.additional_eligibility_criteria_id  asc";

			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("classId", classId);
			query.setParameter("examTypeId", examTypeId);
			query.setParameterList("studentId", studentId);

			l = query.list();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
			}catch (Exception e) {
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}

		}
		return new ArrayList<Object[]>();
	}

	// To FETCH additional eligibility list for class
	public List<Object> getAdditionalEgibility_listFor_class(
			List<Integer> classId, Integer examTypeId) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Object> l = null;
		String HQL_QUERY = "";
		try{
		HQL_QUERY = "select distinct EXAM_eligibility_criteria.name,EXAM_eligibility_criteria.id FROM EXAM_eligibility_setup ees"
				+ " left join EXAM_eligibility_setup_additional_eligibility ad on ad.eligibility_setup_id = ees.id"
				+ " left join EXAM_eligibility_criteria ON ad.additional_eligibility_criteria_id = EXAM_eligibility_criteria.id"
				+ " where ees.class_id in (:classId)"
				+ " and ees.exam_type_id = :examTypeId";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameterList("classId", classId);
		query.setParameter("examTypeId", examTypeId);

		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object>();
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	// To get Exam Fees Status for a class
	public boolean getExamFeesChecked_forClass(Integer classId) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		try{
		String HQL_QUERY = "select ees.isExamFeesChecked"
				+ " from ExamEligibilitySetupBO ees"
				+ " where ees.classId = :classId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		list = query.list();
		if (list.size() > 0 && list.get(0) > 0) {
			return false;
		} else {
			return true;
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// To get Course Fees Status for a class
	public boolean getCourseFeesChecked_forClass(Integer classId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		try{
		String HQL_QUERY = "select ees.isCourseFeesChecked"
				+ " from ExamEligibilitySetupBO ees"
				+ " where ees.classId = :classId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		list = query.list();
		if (list.size() > 0 && list.get(0) > 0) {
			return false;
		} else {
			return true;
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// To get Attendance Status for a class
	public boolean getAttendanceChecked_forClass(Integer classId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Integer> list = null;
		try{
		String HQL_QUERY = "select ees.isAttendanceChecked"
				+ " from ExamEligibilitySetupBO ees"
				+ " where ees.classId = :classId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);
		list = query.list();
		if (list.size() > 0 && list.get(0) > 0) {
			return false;
		} else {
			return true;
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	public String getCurrentExamName() throws Exception {
		String id = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "select d.id from ExamDefinitionBO d where d.isCurrent=1";

		Query query = session.createQuery(HQL);
		Iterator<Object[]> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object row = (Object) itr.next();
			id = row.toString();

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

	public HashMap<Integer, Integer> getAttendanceMapForStudents(Integer classId) throws Exception {
		ArrayList<Integer> studList = get_students_for_class(classId);
		HashMap<Integer, Integer> attendenceMap = new HashMap<Integer, Integer>();
		if (studList != null) {
			for (Iterator iterator = studList.iterator(); iterator.hasNext();) {
				Integer studId = (Integer) iterator.next();
				if (getSubjectCountForStudent(studId) == getAttendenceCountForStudent(studId)) {
					attendenceMap.put(studId, 1);
				} else {
					attendenceMap.put(studId, 0);
				}
			}

		}
		return attendenceMap;
	}

	private int getSubjectCountForStudent(Integer studId) throws Exception {
		int id = 0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "select  count(distinct sub.id)"
				+ " from CurriculumSchemeSubjectUtilBO css"
				+ " left join css.curriculumSchemeDurationUtilBO.classSchemewiseUtilBOSet  cs "
				+ " left join css.subjectGroupSubjectsUtilBO.subjectUtilBO sub"
				+ " where cs.id in (select stu.classSchemewiseId"
				+ " from StudentUtilBO stu" + " where stu.id = :studId)";

		Query query = session.createQuery(HQL);
		query.setParameter("studId", studId);
		List l = query.list();
		if (l != null && l.size() > 0) {
			id = Integer.parseInt(l.get(0).toString());
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

	private int getAttendenceCountForStudent(Integer studId) throws Exception {
		int id = 0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL = "select count(*) as count"
				+ " from AttendanceStudent ast"
				+ " where ast.attendance.subject.id in ("
				+ " select distinct sub.id"
				+ " from CurriculumSchemeSubjectUtilBO css"
				+ " left join css.curriculumSchemeDurationUtilBO.classSchemewiseUtilBOSet  cs "
				+ " left join css.subjectGroupSubjectsUtilBO.subjectUtilBO sub"
				+ " where cs.id in (select stu.classSchemewiseId"
				+ " from StudentUtilBO stu" + " where stu.id  = :studId))"
				+ " and ast.student.id = :studId" + " and ast.isPresent = 1";

		Query query = session.createQuery(HQL);
		query.setParameter("studId", studId);
		List l = query.list();
		if (l != null && l.size() > 0) {
			id = Integer.parseInt(l.get(0).toString());
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
