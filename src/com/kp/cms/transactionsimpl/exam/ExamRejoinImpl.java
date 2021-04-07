package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamEligibilitySetupBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.utilities.HibernateUtil;

/**
 * Dec 28, 2009 Created By 9Elements Team
 */
public class ExamRejoinImpl extends ExamGenImpl {

	public List selectClassesByJoingBatch(int joiningBatch) {
		List list;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String SQL_QUERY = "select classes.id,classes.name from classes left join course on(classes.course_id=course.id) "
				+ "left join program on course.program_id = program.id left join EXAM_definition on "
				+ "(EXAM_definition.program_id = program.id) where EXAM_definition.academic_year= :year and EXAM_definition.is_active= :active";
		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("year", joiningBatch);
		query.setParameter("active", 1);
		list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public void isDuplicated(String newRegNumber, String newRollNumber,
			String rollNo, String regNo) throws Exception {

		Session session = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String SQL = "SELECT student.id, EXAM_student_rejoin.id rejoinId "
					+ "FROM student LEFT JOIN EXAM_student_rejoin ON student.id = EXAM_student_rejoin.student_id WHERE "
					+ " (student.register_no = :newRegNumber OR student.roll_no = :newRollNumber)  "
					+ " OR(EXAM_student_rejoin.new_register_no = :newRegNumber  OR EXAM_student_rejoin.new_roll_no = :newRollNumber) ";
			

			Query query = session.createSQLQuery(SQL);

//			query.setParameter("rollNo", rollNo);
//			query.setParameter("regNo", regNo);
			query.setParameter("newRegNumber", newRegNumber);
			query.setParameter("newRollNumber", newRollNumber);

			List list=query.list();

			if (list.size()>0)  {
				throw new DuplicateException();
			}
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			throw e;
		}

	}

}
