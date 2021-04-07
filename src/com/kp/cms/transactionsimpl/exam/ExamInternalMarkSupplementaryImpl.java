package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryBO;
import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.transactions.exam.IExamInternalMarkSupplementaryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

@SuppressWarnings("unchecked")
public class ExamInternalMarkSupplementaryImpl extends ExamGenImpl implements IExamInternalMarkSupplementaryTransaction {

	// To get the supplementary exame name list

	public List getSupplementaryExamName(int year) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String SQL_QUERY = " select e.id, e.name from ExamDefinitionBO e where e.examTypeID in (select id from ExamTypeUtilBO a where a.name like '%Supplementary%') " +
				" and e.academicYear="+year;

		Query query = session.createQuery(SQL_QUERY);

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

	

	public Integer getStudentId(String rollNo, String regNo) throws Exception {

		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String HQL = null;
		Query query;
		Integer stuId = 0;
		if ((rollNo != null && rollNo.length() > 0)
				&& (regNo != null && regNo.length() > 0)) {
			HQL = "select s.id from StudentUtilBO s where s.rollNo =:rollNo and s.registerNo =:regNo ";

		}
		if ((regNo != null && regNo.length() > 0)
				&& (rollNo == null || rollNo.length() == 0)) {
			HQL = "select s.id from StudentUtilBO s where  s.registerNo =:regNo ";
		}
		if ((rollNo != null && rollNo.length() > 0)
				&& (regNo == null || regNo.length() == 0)) {
			HQL = "select s.id from StudentUtilBO s where  s.rollNo = :rollNo ";
		}

		query = session.createQuery(HQL);
		if (rollNo != null && rollNo.length() > 0) {
			query.setParameter("rollNo", rollNo);
		}
		if (regNo != null && regNo.length() > 0) {
			query.setParameter("regNo", regNo);
		}
		session.flush();
		List l = query.list();
		if (l != null && l.size() > 0) {
			stuId = (Integer) l.get(0);
		}
		return stuId;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	public List<ExamInternalMarkSupplementaryDetailsBO> select_InternalSuppMarksToDisplay(int courseId, String rollNo,
			String regNo, int schemeNo, int examId) throws Exception {
		Integer studentId = getStudentId(rollNo, regNo);
		if (studentId == 0) {
			studentId = null;
		}
		Session session=null;
		try {
			String QUERY="from ExamInternalMarkSupplementaryDetailsBO e where e.student.id=:studentId and e.student.admAppln.courseBySelectedCourseId.id=:courseId and e.classes.termNumber=:schemeNo";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query = session.createQuery(QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			//query.setParameter("examId", examId);
			query.setParameter("studentId", studentId);
			List<ExamInternalMarkSupplementaryDetailsBO> examInternalMarkSupplementaryDetailsBOs = query.list();
			return examInternalMarkSupplementaryDetailsBOs;
			}catch (Exception e) {
				throw  new ApplicationException(e);
			}finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
	}
	
	public List<ExamStudentOverallInternalMarkDetailsBO> select_OverAllInternalMarksToDisplay(int courseId, String rollNo,
			String regNo, int schemeNo, int examId) throws Exception {
		Integer studentId = getStudentId(rollNo, regNo);
		if (studentId == 0) {
			studentId = null;
		}
		Session session=null;
		try {
			String QUERY="from ExamStudentOverallInternalMarkDetailsBO e where e.student.id=:studentId and e.student.admAppln.courseBySelectedCourseId.id=:courseId and e.classes.termNumber=:schemeNo";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query = session.createQuery(QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			//query.setParameter("examId", examId);
			query.setParameter("studentId", studentId);
			List<ExamStudentOverallInternalMarkDetailsBO> examStudentOverallInternalMarkDetailsBOs = query.list();
			return examStudentOverallInternalMarkDetailsBOs;
			}catch (Exception e) {
				throw  new ApplicationException(e);
			}finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
	}
	
	public void saveExamInternalMarkDetails(List<ExamInternalMarkSupplementaryDetailsBO> examInternalMarkSupplementaryDetailsBOs) throws Exception{
		Session session=null;
		Transaction tx=null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			// session =InitSessionFactory.getInstance().openSession();
			session=sessionFactory.openSession();
			 tx = session.beginTransaction();
			Iterator<ExamInternalMarkSupplementaryDetailsBO> iterator = examInternalMarkSupplementaryDetailsBOs.iterator();
				while (iterator.hasNext()) {
					ExamInternalMarkSupplementaryDetailsBO examInternalMarkSupplementaryDetailsBO = (ExamInternalMarkSupplementaryDetailsBO) iterator.next();
					if(examInternalMarkSupplementaryDetailsBO.getId() > 0)
						session.merge(examInternalMarkSupplementaryDetailsBO);
					else
						session.save(examInternalMarkSupplementaryDetailsBO);
				}
					tx.commit();
					 session.flush();
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
	// To get subjects and marks for a student
	/*public List<Object[]> select_InternalSuppMarks(int courseId, String rollNo,
			String regNo, int schemeNo, int examId) throws Exception {

		Integer studentId = getStudentId(rollNo, regNo);
		if (studentId == 0) {
			studentId = null;
		}
		Session session = null;
		try{
		String SQL_QUERY = " SELECT im.id AS iid," + " subject.id AS subid,"
				+ " subject.name," + " subject.code,"
				+ " ifnull(im.theory_marks, es.theory_total_mark),"
				+ " ifnull(im.practical_marks, es.practical_total_mark),"
				+ " subject.is_theory_practical" + " FROM subject"
				+ " LEFT JOIN" + " EXAM_internal_mark_supplementary im"
				+ " ON im.subject_id = subject.id"
				+ " and im.student_id = :studentId"
				+ " left join EXAM_student_overall_internal_mark_details es"
				+ " on es.subject_id = subject.id "
				+ " and es.student_id = :studentId" + " WHERE subject.id IN"
				+ " (SELECT sgs.subject_id"
				+ " FROM subject_group_subjects sgs"
				+ " WHERE sgs.subject_group_id IN"
				+ " (SELECT css.subject_group_id"
				+ " FROM curriculum_scheme_subject css"
				+ " WHERE css.curriculum_scheme_duration_id IN"
				+ " (SELECT csd.id" + " FROM curriculum_scheme_duration csd"
				+ " WHERE csd.curriculum_scheme_id IN"
				+ " (SELECT cs.id FROM curriculum_scheme cs"
				+ " WHERE cs.course_id = :courseId"
				+ " AND cs.course_scheme_id IN"
				+ " (SELECT ec.course_scheme_id"
				+ " FROM EXAM_exam_course_scheme_details ec"
				+ " WHERE ec.exam_id = :examId AND ec.course_id = :courseId"
				+ " AND ec.scheme_no = :schemeNo)"
				+ " AND csd.semester_year_no = :schemeNo"
				+ " AND csd.academic_year IN" + " (SELECT e.academic_year"
				+ " FROM EXAM_definition e" + " WHERE e.id = :examId)))))";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("examId", examId);
		query.setParameter("studentId", studentId);

		return query.list();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}*/

	/*public void isDuplicated(Integer studentId, Integer subjectId,
			BigDecimal theoryMarks, BigDecimal practicalMarks) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamInternalMarkSupplementaryBO.class);
			crit.add(Restrictions.eq("studentId", studentId));
			crit.add(Restrictions.eq("subjectId", subjectId));
			crit.add(Restrictions.eq("theoryMarks", theoryMarks));
			crit.add(Restrictions.eq("practicalMarks", practicalMarks));

			List<ExamInternalMarkSupplementaryBO> list = crit.list();

			if (list.size() > 0) {
				Iterator it = list.iterator();
				ExamInternalMarkSupplementaryBO objbo = null;
				while (it.hasNext()) {
					objbo = (ExamInternalMarkSupplementaryBO) it.next();

					if (objbo.getIsActive() == true) {
						throw new DuplicateException();
					} else {
						throw new ReActivateException(objbo.getId());
					}
				}
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

	}*/

	// To update
	/*public void updateIntMarks(Integer id, String theoryMarks,
			String practicalMarks) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String HQL_QUERY = "update ExamInternalMarkSupplementaryBO e set e.theoryMarks = :theoryMarks,"
				+ " e.practicalMarks = :practicalMarks where e.id = :id";
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.setParameter("theoryMarks", theoryMarks);
		query.setParameter("practicalMarks", practicalMarks);
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
		

	}*/
}
