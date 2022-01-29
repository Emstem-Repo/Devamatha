package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamStudentEligibilityCheckBO;
import com.kp.cms.bo.exam.ExamStudentEligibilityEntryBO;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamStudentEligibilityEntryImpl extends ExamGenImpl {
	private static final Log log = LogFactory
			.getLog(ExamStudentEligibilityEntryImpl.class);

	public List select_StudentEli(ArrayList<Integer> listClassIds,
			int eligibilityCriteria, String academicYear) throws Exception {
		Session session = null;
		List list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL = " select s.id as studentId ,"
					+ " s.roll_no, s.register_no, "
					+ " personal_data.first_name,"
					+ " personal_data.last_name,"
					+ " ee.is_eligibile, ee.id as eligibilityId,  "
					+ " ee.class_id,"
					+ " ee.eligibility_criteria_id, classes.id  as classid"
					+ " from student s"
					+ " left join adm_appln ON s.adm_appln_id = adm_appln.id"
					+ " left join personal_data ON adm_appln.personal_data_id = personal_data.id"
					+ " left join class_schemewise ON s.class_schemewise_id = class_schemewise.id"
					+ " LEFT JOIN"
					+ " curriculum_scheme_duration"
					+ " ON class_schemewise.curriculum_scheme_duration_id ="
					+ " curriculum_scheme_duration.id"
					+ " left join classes ON class_schemewise.class_id = classes.id"
					+ " left join EXAM_student_eligibility ee"
					+ " on ee.student_id = s.id"
					+ " and ee.eligibility_criteria_id = :eligibilityCriteria"
					+ " where classes.id in (:listClassIds) AND curriculum_scheme_duration.academic_year = :academicYear";

			Query query = (Query) session.createSQLQuery(HQL);
			query.setParameterList("listClassIds", listClassIds);
			query.setParameter("eligibilityCriteria", eligibilityCriteria);
			query.setParameter("academicYear", academicYear);
			list = query.list();

			session.flush();
			// session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}

	public void delete(ArrayList<Integer> listClassId, int eligibilityCriteriaId) throws Exception {

		Session session = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String HQL = "delete from ExamStudentEligibilityEntryBO e where e.classId in (:listClassId) "
					+ "and e.eligibilityCriteriaId = :eligibilityCriteriaId  ";
			Query query = session.createQuery(HQL);
			query.setParameterList("listClassId", listClassId);
			query.setParameter("eligibilityCriteriaId", eligibilityCriteriaId);

			query.executeUpdate();
			tx.commit();
			session.flush();
			// session.close();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}

	}

	public boolean getValidStudentForClass(int studentId, int classId) throws Exception {
		boolean flag = false;
		Session session = null;
		ArrayList<Integer> list;
		try {
			String HQL = "select distinct stu.classSchemewiseUtilBO.classUtilBO.id from StudentUtilBO stu where stu.id = :studentId and "
					+ " stu.classSchemewiseId in (select cs.id from ClassSchemewiseUtilBO cs where cs.classId = :classId )";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query q = session.createQuery(HQL);
			q.setParameter("studentId", studentId);
			q.setParameter("classId", classId);
			list = new ArrayList<Integer>(q.list());
			session.flush();
			// session.close();
			if (list.size() > 0) {
				flag = true;
			}
			flag = false;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			flag = false;
		}
		return flag;
	}

	public void insert_List_Values(ArrayList<ExamStudentEligibilityEntryBO> list) throws Exception {

		for (ExamStudentEligibilityEntryBO objBo : list) {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			try {

				session.save(objBo);
				tx.commit();
				session.flush();
			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
				if (session != null) {
					// session.flush();
					session.close();
				}
			}
		}

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
}
