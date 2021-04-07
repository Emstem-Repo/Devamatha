package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.bo.exam.ExamUpdateExcludeWithheldBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * Dec 26, 2009 Created By 9Elements Team
 */
public class ExamUpdateExcludeWithheldImpl extends ExamGenImpl {

	private static final Log log = LogFactory
			.getLog(ExamUpdateExcludeWithheldImpl.class);

	// To get the courseId for the particular examId
	public ArrayList<Integer> select_course(int examId) {
		ArrayList<Integer> listCourseIds = new ArrayList<Integer>();
		String SQL_QUERY = "Select ec.courseID, ec.courseName from ExamCourseUtilBO ec , ExamExamCourseSchemeDetailsBO ecs"
				+ " where ec.courseId = ecs.courseId and ec.isActive = 1 and ecs.examId = :examId";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createQuery(SQL_QUERY);
		Iterator it = query.iterate();
		while (it.hasNext()) {
			Object[] row = (Object[]) it.next();
			listCourseIds.add(((Integer) row[0]).intValue());
		}
		return listCourseIds;
	}

	public ArrayList<ExamExamCourseSchemeDetailsBO> select_CourseName(int examId) {
		Session session = null;
		ArrayList<ExamExamCourseSchemeDetailsBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamExamCourseSchemeDetailsBO.class);

			//		
			crit.add(Restrictions.eq("examId", examId));

			// list = new ArrayList(crit.list());

			// crit.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("courseId"),
			// "courseId")));
			list = new ArrayList(crit.list());

			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamExamCourseSchemeDetailsBO>();

		}
		return list;

	}

	public ArrayList<ExamExamCourseSchemeDetailsBO> select_SchemeList(
			String courseId) {
		Session session = null;
		ArrayList<ExamExamCourseSchemeDetailsBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamExamCourseSchemeDetailsBO.class);
			crit.add(Restrictions.eq("courseId", courseId));

			list = new ArrayList(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamExamCourseSchemeDetailsBO>();

		}
		return list;

	}

	public ArrayList<StudentUtilBO> select_student(int courseId, int year,
			int schemeId, Integer academicYear) {
		Session session = null;
		ArrayList<StudentUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL_QUERY = "from StudentUtilBO s "
					+ " where s.admApplnUtilBO.isCancelled=0 and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId =:courseId "
					// +
					// " and  s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.year =:year"
					//+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId =:schemeId ";
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo =:schemeId "
					+ " order by s.registerNo";
			Query query = session.createQuery(HQL_QUERY);
			// query.setParameter("year", year);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			list = new ArrayList(query.list());
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<StudentUtilBO>();

		}

		return list;
	}

	public int select_year(int examId) {
		int year = 0;
		// Session session = null;
		// try {
		// SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		// session = sessionFactory.openSession();
		// String HQL_QUERY = "from ExamDefinitionBO e where e.id =:id ";
		// Query query = session.createQuery(HQL_QUERY);
		// query.setParameter("id", examId);
		// Iterator i = query.list().iterator();
		// while (i.hasNext()) {
		// ExamDefinitionBO e = (ExamDefinitionBO) i.next();
		// year = e.getYear();
		// }
		// session.flush();
		// } catch (Exception e) {
		// if (session != null) {
		// session.flush();
		// session.close();
		// }
		// }
		return year;
	}

	public void update(ArrayList<Integer> listExcludeStudents,
			ArrayList<Integer> listWithheldStudents, int examId, int courseId,
			int schemeNo, String userId) {

		ArrayList<ExamUpdateExcludeWithheldBO> listBO;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			// get all transactions for given courseid and examid
			Criteria crit = session
					.createCriteria(ExamUpdateExcludeWithheldBO.class);
			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("examId", examId));
			crit.add(Restrictions.eq("schemeNo", schemeNo));
			listBO = new ArrayList(crit.list());
			Integer studentId;
			// Update the respective flags
			for (ExamUpdateExcludeWithheldBO e : listBO) {
				studentId = e.getStudentId();
				e.reset(userId);
				if (listExcludeStudents.contains(studentId)) {
					e.setExcludeFromResults(true);
					listExcludeStudents.remove(studentId);
					e.setSchemeNo(schemeNo);
				}
				if (listWithheldStudents.contains(studentId)) {
					e.setWithheld(true);
					listWithheldStudents.remove(studentId);
					e.setSchemeNo(schemeNo);					
				}
				session.update(e);

			}
			tx.commit();
			session.flush();
			// session.close();

			// till this line of code only students that have been added
			// previously will be updated

			// For NEW STUDENTS- get list of students not in tables / never
			// withheld or excluded
			ArrayList<Integer> listStudentsNotPresent = new ArrayList<Integer>(
					listExcludeStudents);

			for (Integer i : listWithheldStudents) {
				if (!listStudentsNotPresent.contains(i)) {
					listStudentsNotPresent.add(i);
				}
			}
			// create the BOs for new students
			ArrayList<ExamUpdateExcludeWithheldBO> listNewStudents = new ArrayList<ExamUpdateExcludeWithheldBO>();
			for (Integer stuId : listStudentsNotPresent) {
				listNewStudents.add(new ExamUpdateExcludeWithheldBO(courseId,
						examId, stuId, schemeNo, userId));
			}
			// check for each new student what to flag and save
			Integer studentIdNew;
			for (ExamUpdateExcludeWithheldBO eNS : listNewStudents) {
				studentIdNew = eNS.getStudentId();
				if (listExcludeStudents.contains(studentIdNew)) {
					eNS.setExcludeFromResults(true);
				}
				if (listWithheldStudents.contains(studentIdNew)) {
					eNS.setWithheld(true);
				}

			}
			insert_List(listNewStudents);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Integer getAcademicYearForExam(int examId)throws Exception
	{
		log.info("Start of getAcademicYearForExam");
		Session session = null;
		ExamDefinitionBO examDefinitionBO;
		Integer year = 0;
		try {
			session = InitSessionFactory.getInstance().openSession();
			year = (Integer) session.createQuery("select academicYear from ExamDefinitionBO e where e.id = " + examId +
					" and e.isActive = 1").uniqueResult();
			} catch (Exception e) {
			log.error("Error in getActiveCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				session.close();
				}
			}
			log.info("End of getActiveCourses of CourseTransactionImpl");
			return year;
	}
}
