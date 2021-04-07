package com.kp.cms.transactionsimpl.exam;

/**
 * Mar 2, 2010 Created By 9Elements Team
 */
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamInternalRetestApplicationBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamInternalRetestApplicationImpl extends ExamGenImpl {

	// On SEARCH to get the student details

	public List<Object[]> select_student_details(Integer examId,
			Integer classId, String rollNo, String regNo,
			boolean rollNoPresent, boolean regNoPresent) throws BusinessException {
		Session session=null;
		
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String HQL_QUERY = "select e.studentUtilBO.classSchemewiseUtilBO.classUtilBO.name, e.studentUtilBO.registerNo,"
				+ " e.studentUtilBO.rollNo, e.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName, e.id"
				+ " from ExamInternalRetestApplicationBO e"
				+ " where e.examId = :examId";

		if (classId != null && classId.intValue() != 0) {
			HQL_QUERY = HQL_QUERY + " and e.classId = :classId";

		}
		if (rollNoPresent) {
			HQL_QUERY = HQL_QUERY + " and e.studentUtilBO.rollNo = :rollNo";

		} else if (regNoPresent) {

			HQL_QUERY = HQL_QUERY + " and e.studentUtilBO.registerNo = :regNo";

		}

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("examId", examId);
		if (classId != null && classId.intValue() != 0) {
			query.setParameter("classId", classId);
		}
		if (rollNoPresent) {
			query.setParameter("rollNo", rollNo);
		} else if (regNoPresent) {
			query.setParameter("regNo", regNo);
		}
		return query.list();
		
		}catch (Exception e) {
			throw  new BusinessException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	// ADD - to get the exam details for a particular student
	public List<Object[]> get_examDetails(Integer examId, Integer classId,
			String rollNo, String regNo) throws BusinessException {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		boolean rollNoPresent = false;
		boolean regNOPresent = false;
		List<Object[]> list = null;

		if (rollNo != null && rollNo.length() > 0) {
			rollNoPresent = true;
		}
		if (regNo != null && regNo.length() > 0) {
			regNOPresent = true;
		}

		String SQL1 = "";
		if (classId != null && classId.intValue() != 0) {
			SQL1 = " and ira.class_id = :classId";

		}
		String SQL_QUERY = "SELECT a.chance, classes.name, personal_data.first_name, stu.register_no, stu.roll_no, stu.id as stuid, a.id, classes.id as clsId"
				+ "		  FROM    student stu "
				+ "		       LEFT JOIN "
				+ "		          (SELECT ira.student_id, ira.id, ira.chance, ira.class_id "
				+ "		             FROM EXAM_internal_retest_application ira "
				+ "		            WHERE ira.exam_id = :examId  "
				+ SQL1
				+ "								) a "
				+ "		       ON a.student_id = stu.id "
				+ "					 left join class_schemewise ON stu.class_schemewise_id = class_schemewise.id "
				+ "					 left join classes ON class_schemewise.class_id = classes.id "
				+ "					 left join adm_appln ON stu.adm_appln_id = adm_appln.id "
				+ "					 left join personal_data ON adm_appln.personal_data_id = personal_data.id ";

		if (rollNoPresent) {
			SQL_QUERY = SQL_QUERY + " Where stu.roll_no = :rollNo";

		} else if (regNOPresent) {

			SQL_QUERY = SQL_QUERY + " Where stu.register_no = :regNo";

		}

		Query query = session.createSQLQuery(SQL_QUERY);
		if (classId != null && classId.intValue() != 0) {
			query.setParameter("classId", classId);
		}
		if (rollNoPresent) {
			query.setParameter("rollNo", rollNo);
		} else if (regNOPresent) {
			query.setParameter("regNo", regNo);
		}
		query.setParameter("examId", examId);
		list = query.list();
		return list;
		}catch (Exception e) {
			throw  new BusinessException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	// ADD - to get the subjects for a particular student
	public List<Object[]> get_subjectsList(Integer examId, String rollNo,
			String regNo) throws BusinessException {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		boolean rollNoPresent = false;
		boolean regNOPresent = false;

		if (rollNo != null && rollNo.length() > 0) {
			rollNoPresent = true;
		}
		if (regNo != null && regNo.length() > 0) {
			regNOPresent = true;
		}

		String SQL1 = null;
		String SQL2 = null;
		if (rollNoPresent && !regNOPresent) {

			SQL1 = " and (student.roll_no =  '" + rollNo + "'))";
			SQL2 = " where student.roll_no = '" + rollNo + "')";
		} else if (!rollNoPresent && regNOPresent) {

			SQL1 = " and ( student.register_no = '" + regNo + "'))";
			SQL2 = " where student.register_no = '" + regNo + "')";
		} else if (rollNoPresent && regNOPresent) {

			SQL1 = " and (student.roll_no = '" + rollNo
					+ "' and student.register_no = '" + regNo + "'))";
			SQL2 = " where student.roll_no  = '" + rollNo
					+ "' and student.register_no = '" + regNo + "')";
		}

		String SQL_QUERY = " SELECT sub.code, sub.name, a.fees, a.is_theory, a.is_practical, sub.id "
				+ "   FROM subject sub "
				+ "  LEFT JOIN "
				+ " (select isub.subject_id,isub.fees, isub.is_theory, isub.is_practical  from EXAM_internal_retest_application_subjects isub"
				+ " join EXAM_internal_retest_application ON isub.exam_internal_retest_application_id = EXAM_internal_retest_application.id"
				+ " and EXAM_internal_retest_application.exam_id = :examId"
				+ " join student ON EXAM_internal_retest_application.student_id = student.id"
				+ SQL1
				+ "  a"
				+ " ON a.subject_id = sub.id "
				+ " where sub.id IN "
				+ " (SELECT sgs.subject_id "
				+ " FROM subject_group_subjects sgs "
				+ " JOIN "
				+ " curriculum_scheme_subject css "
				+ " ON sgs.subject_group_id = css.subject_group_id "
				+ " JOIN "
				+ " curriculum_scheme_duration csd "
				+ " ON css.curriculum_scheme_duration_id = csd.id "
				+ " JOIN "
				+ " class_schemewise cs "
				+ " ON cs.curriculum_scheme_duration_id = csd.id "
				+ " JOIN "
				+ " student "
				+ " ON student.class_schemewise_id = cs.id "
				+ SQL2;

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("examId", examId);

		return query.list();
		}catch (Exception e) {
			throw  new BusinessException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	// On EDIT - to get student details
	public List<Object[]> get_subjectsList(int id) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String HQL_QUERY = "select e.subjectUtilBO.code, e.subjectUtilBO.name, "
				+ " e.fees, e.isTheory, e.isPractical, e.subjectUtilBO.id"
				+ " from ExamInternalRetestApplicationSubjectsBO e where e.examInternalRetestApplicationId = :id";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);

		return query.list();
		
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// On EDIT - to get exam details
	public List<Object[]> get_examDetails(int id) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String HQL_QUERY = "select e.chance, e.studentUtilBO.classSchemewiseUtilBO.classUtilBO.name,"
				+ " e.studentUtilBO.admApplnUtilBO.personalDataUtilBO.firstName, "
				+ " e.studentUtilBO.registerNo, e.studentUtilBO.rollNo,"
				+ " e.studentUtilBO.id, e.id, e.studentUtilBO.classSchemewiseUtilBO.classUtilBO.id from ExamInternalRetestApplicationBO e where e.id = :id";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);

		return query.list();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// To update
	public void updateSubjectDetails(int id, int subjectId, String fees,
			int theory, int practical, Integer chance) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "update ExamInternalRetestApplicationSubjectsBO e set e.fees = :fees,"
				+ " e.isTheory = :theory, e.isPractical = :practical"
				+ " where e.examInternalRetestApplicationId = :id and e.subjectId = :subjectId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.setParameter("subjectId", subjectId);
		query.setParameter("fees", fees);
		query.setParameter("theory", theory);
		query.setParameter("practical", practical);
		query.executeUpdate();

		String HQL_QUERY1 = "update ExamInternalRetestApplicationBO e set e.chance = :chance"
				+ " where e.id = :id";

		Query query1 = session.createQuery(HQL_QUERY1);
		query1.setParameter("id", id);
		query1.setParameter("chance", chance);
		query1.executeUpdate();
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

	// To DELETE
	public void deleteIntAppl(int id) throws Exception {

		Session session = null;
		try{
		String HQL_QUERY = "delete from ExamInternalRetestApplicationSubjectsBO e"
				+ " where e.examInternalRetestApplicationId = :id ";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);

		query.executeUpdate();

		String HQL_QUERY1 = "delete from ExamInternalRetestApplicationBO e"
				+ " where e.id = :id ";

		Query query1 = session.createQuery(HQL_QUERY1);
		query1.setParameter("id", id);
		query1.executeUpdate();
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

	// To get the id of first table(EXAM_internal_retest_application)
	public int insert_returnId(ExamInternalRetestApplicationBO objBO) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {

			session.save(objBO);

			id = objBO.getId();

			tx.commit();
			session.flush();
			session.close();
			return id;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

		}
		return id;
	}

	// To get class name based on examId & roll/register no
	public List getClassByExamNameRegNoOnly(int examId, String rollNo,
			String regNo, boolean rollNoPresent, boolean regNOPresent) {
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String SQL_QUERY = "select distinct classes.id, classes.name from classes join class_schemewise on"
				+ " class_schemewise.class_id = classes.id"
				+ " join curriculum_scheme_duration on"
				+ " class_schemewise.curriculum_scheme_duration_id ="
				+ " curriculum_scheme_duration.id"
				+ " join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id"
				+ " = curriculum_scheme.id"
				+ " join student on student.class_schemewise_id = class_schemewise.id join"
				+ " EXAM_exam_course_scheme_details on"
				+ " EXAM_exam_course_scheme_details.course_id = classes.course_id"
				+ " and EXAM_exam_course_scheme_details.exam_id = :examId";

		if (rollNoPresent) {
			SQL_QUERY = SQL_QUERY + " where (student.roll_no = '" + rollNo
					+ "')";

		} else if (regNOPresent) {
			SQL_QUERY = SQL_QUERY + " where (student.register_no = '" + regNo
					+ "' )";

		}

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("examId", examId);
		return query.list();
		}
	}
