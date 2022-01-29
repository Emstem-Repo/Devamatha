package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.bo.exam.ExamRevaluationBO;
import com.kp.cms.bo.exam.ExamRevaluationDetailsBO;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamRevaluationImpl extends ExamGenImpl {

	// To get the subjectIDs from revaluation application
	public ArrayList<Integer> select_subjectIdForRevaluation(int courseId,
			String rollNo, String regNo, boolean useRollNo, int schemeNo,
			int examId, int revaluationTypeId) {

		String SQL_QUERY = "";
		if (useRollNo) {

			SQL_QUERY = "select eras.subjectId "
					+ " from ExamRevaluationApplicationSubjectBO eras "
					+ " where eras.revaluationAppId in ( " + " select er.id "
					+ " from ExamRevaluationApplicationBO er "
					+ " where er.examId = :examId "
					+ " and er.studentUtilBO.rollNo = :rollNo "
					+ " and er.courseId = :courseId "
					+ " and er.schemeNo = :schemeNo "
					+ " and er.revaluationTypeId  = :revaluationTypeId ) ";

		} else {
			SQL_QUERY = "select eras.subjectId "
					+ " from ExamRevaluationApplicationSubjectBO eras "
					+ " where eras.revaluationAppId in ( " + " select er.id "
					+ " from ExamRevaluationApplicationBO er "
					+ " where er.examId = :examId "
					+ " and er.studentUtilBO.registerNo = :regNo "
					+ " and er.courseId = :courseId "
					+ " and er.schemeNo = :schemeNo "
					+ " and er.revaluationTypeId  = :revaluationTypeId) ";
		}

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createQuery(SQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("examId", examId);
		query.setParameter("revaluationTypeId", revaluationTypeId);
		if (useRollNo) {
			query.setParameter("rollNo", rollNo);
		} else {
			query.setParameter("regNo", regNo);
		}
		
		ArrayList<Integer> listStuId = new ArrayList<Integer>();
		Iterator itr = query.list().iterator();
		while (itr.hasNext()) {
			listStuId.add((Integer) itr.next());

		}
		session.flush();
		// session.close();
		return listStuId;
	}

	// To FETCH SS - AS based on the rules
	// specified in SUBJECT - RULE - SETTINGS
	public List selectMarksDetail_AllSubForOneStudent(int studentId,
			ArrayList<Integer> listSubjectIds, int examId, int revaluationId)
			throws DataNotFoundException {

		List returnList = null;

		if (listSubjectIds.size() == 0) {
			throw new DataNotFoundException();
		}

		Query query = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String SQL = " SELECT er.id,  "
				+ " er.old_marks_card_no,  "
				+ " er.old_marks_card_date,  "
				+ " erd.id,  "
				+ " sub.id AS subjectId,  "
				+ " sub.name,         "
				+ " if(erd.id IS NULL, b.maxthoery , erd.previous_theory_marks) max_previous_theory_marks,  "
				+ " if(erd.id IS NULL, b.maxpractical, erd.previous_practical_marks) max_previous_practical_marks,  "
				+ " sub.is_theory_practical,  "
				+ " erd.current_theory_marks,  "
				+ " erd.current_practical_marks,  "
				+ " er.new_marks_card_no,  "
				+ " er.new_marks_card_date,  "
				+ " if(erd.id IS NULL, b.minthoery, erd.previous_practical_marks) min_previous_theory_marks,"
				+ " if(erd.id IS NULL, b.minpractical, erd.previous_practical_marks) min_previous_practical_marks,"
				+ " if(erd.id IS NULL, b.avgthoery, erd.previous_theory_marks) avg_previous_theory_marks,"
				+ " if(erd.id IS NULL, b.avgpractical, erd.previous_practical_marks) avg_previous_practical_marks"
				+ " FROM subject sub  "
				+ " left join (SELECT a.exam_id,  "
				+ " a.student_id,  "
				+ " a.subject_id,  "
				+ " (max(a.finalthoerymark)) AS maxthoery,  "
				+ " (max(a.finalpracticalmark)) AS maxpractical,  "
				+ " (sum(a.finalthoerymark) / a.total) AS avgthoery,  "
				+ " (sum(a.finalpracticalmark) / a.total)  "
				+ " AS avgpractical,  "
				+ " (min(a.finalthoerymark)) AS minthoery,  "
				+ " (min(a.finalpracticalmark)) AS minpractical  "
				+ " FROM (SELECT me.exam_id,  "
				+ " me.student_id,  "
				+ " med.subject_id,  "
				+ " me.evaluator_type_id,  "
				+ " sum(med.theory_marks) AS finalthoerymark,  "
				+ " sum(med.practical_marks)  "
				+ " AS finalpracticalmark,  "
				+ " (SELECT COUNT(*)"
				+ "  FROM  EXAM_marks_entry me"
				+ "  LEFT JOIN EXAM_marks_entry_details med"
				+ " ON med.marks_entry_id = me.id"
				+ "  WHERE me.exam_id = :examId"
				+ "      AND me.student_id = :studentId"
				+ ")	AS total  "
				+ " FROM EXAM_marks_entry me  LEFT JOIN  "
				+ " EXAM_marks_entry_details med  "
				+ " ON med.marks_entry_id = me.id  "
				+ " WHERE     me.exam_id = :examId  "
				+ " AND me.student_id = :studentId  "
				+ " GROUP BY me.exam_id,  "
				+ " me.student_id, med.subject_id, me.evaluator_type_id) a  "
				+ " GROUP BY a.student_id, a.subject_id) AS b  "
				+ " ON b.subject_id = sub.id  "
				+ " AND b.exam_id = :examId  "
				+ " left JOIN EXAM_revaluation er  "
				+ " ON er.student_id = b.student_id  AND er.exam_id = b.exam_id  "
				+ " AND er.revaluation_type_id =:revaluationId  "
				+ " LEFT JOIN EXAM_revaluation_details erd  "
				+ " ON erd.exam_revaluation_id = er.id AND erd.subject_id = sub.id  "
				+ " WHERE sub.id IN (:listSubjectIds)  ";

		query = session.createSQLQuery(SQL);
		query.setParameter("studentId", studentId);
		query.setParameterList("listSubjectIds", listSubjectIds);
		query.setParameter("examId", examId);
		query.setParameter("revaluationId", revaluationId);
		returnList = query.list();
		session.flush();
		session.close();
		if (returnList != null) {
			return returnList;
		} else {
			return new ArrayList();
		}

	}

	// To INSERT & UPDATE SS - AS(first table)
	public Integer insert_MarksEntry(ExamRevaluationBO marksMaster,
			ArrayList<ExamRevaluationDetailsBO> marksDetailsList) {
		try {
			Integer masterID;
			boolean newMaster = false;
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			if (marksMaster.getId() != null && marksMaster.getId() > 0) {
				Transaction tx = session.beginTransaction();
				session.merge(marksMaster);
				tx.commit();

				masterID = marksMaster.getId();
				session.flush();
			} else {
				Transaction tx = session.beginTransaction();
				session.save(marksMaster);
				tx.commit();
				masterID = marksMaster.getId();
				newMaster = true;
			}
			session.flush();
			// session.close();
			insert_MarksEntryDetails(marksDetailsList, newMaster, masterID);
			return masterID;
		} catch (Exception e) {
			return 0;
		}
	}

	// To INSERT SS - AS(second table)
	public void insert_MarksEntryDetails(
			ArrayList<ExamRevaluationDetailsBO> marksDetailsList,
			boolean newMaster, Integer masterID) {
		if (!newMaster) {
			delete_MarksEntryDetails(masterID);
		}
		for (ExamRevaluationDetailsBO detBO : marksDetailsList) {
			detBO.setExamRevaluationId(masterID);
		}
		insert_List(marksDetailsList);
	}

	// To FETCH subject type & evaluation type from SUBJECT - RULE - SETTINGS
	public ArrayList<DisplayValueTO> get_type_of_evaluation(int subjectId,
			int courseId, int schemeNo) {

		String SQL_QUERY = "select distinct me.is_theory_practical, me.type_of_evaluation from EXAM_subject_rule_settings_mul_evaluator me, EXAM_subject_rule_settings srs"
				+ " where srs.id = me.subject_rule_settings_id"
				+ " and srs.subject_id = :subjectId"
				+ " and srs.course_id = :courseId"
				+ " and srs.scheme_no = :schemeNo" + " and srs.is_active = 1";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList<DisplayValueTO> list = new ArrayList<DisplayValueTO>();
		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		Iterator itr = query.list().iterator();
		while (itr.hasNext()) {
			Object object[] = (Object[]) itr.next();
			list.add(new DisplayValueTO(object[0].toString(), object[1]
					.toString()));
		}
		return list;
	}

	// To FETCH AS - SS based on the rules
	// specified in SUBJECT - RULE - SETTINGS
	public List select_allStudentsForOneSubject(int courseId, int subjectId,
			int subjectTypeId, boolean orderRollNo, int examId,
			int revaluationId, int schemeNo) {

		String isTheoryPractical = "";

		if (subjectTypeId == 1) {
			isTheoryPractical = "T";
		} else if (subjectTypeId == 0) {
			isTheoryPractical = "P";
		} else {
			isTheoryPractical = "B";
		}

		String HQL_String = " SELECT student.roll_no,"
				+ " student.register_no,  "
				+ " student.id,"
				+ " personal_data.first_name,"
				+ " personal_data.last_name,  "
				+ " er.id AS erid,"
				+ " erd.id AS erdid, "
				+ " if(erd.id IS NULL, b.maxthoery , erd.previous_theory_marks) max_previous_theory_marks,  "
				+ " if(erd.id IS NULL, b.maxpractical, erd.previous_practical_marks) max_previous_practical_marks,  "
				+ "  erd.current_theory_marks,"
				+ "  erd.current_practical_marks,"
				+ " if(erd.id IS NULL, b.minthoery, erd.previous_practical_marks) min_previous_theory_marks,"
				+ " if(erd.id IS NULL, b.minpractical, erd.previous_practical_marks) min_previous_practical_marks,"
				+ " if(erd.id IS NULL, b.avgthoery, erd.previous_theory_marks) avg_previous_theory_marks,"
				+ " if(erd.id IS NULL, b.avgpractical, erd.previous_practical_marks) avg_previous_practical_marks"
				+ "   FROM student"
				+ "  LEFT JOIN EXAM_revaluation er"
				+ "  ON  er.student_id = student.id"
				+ "  AND er.exam_id = :examId"
				+ "              AND er.revaluation_type_id = :revaluationId"
				+ "        JOIN adm_appln"
				+ "           ON student.adm_appln_id = adm_appln.id"
				+ "        JOIN personal_data"
				+ "           ON adm_appln.personal_data_id = personal_data.id"
				+ "        LEFT JOIN EXAM_revaluation_details erd"
				+ "           ON erd.exam_revaluation_id = er.id AND erd.subject_id = :subjectId"
				+ "        LEFT JOIN (SELECT a.exam_id,"
				+ "                          a.student_id,"
				+ "                          a.subject_id,"
				+ "                          (max(a.finalthoerymark)) AS maxthoery,"
				+ "                          (max(a.finalpracticalmark)) AS maxpractical,"
				+ "                          (sum(a.finalthoerymark) / a.total) AS avgthoery,"
				+ "                          (sum(a.finalpracticalmark) / a.total)"
				+ "                             AS avgpractical,"
				+ "                          (min(a.finalthoerymark)) AS minthoery,"
				+ "                          (min(a.finalpracticalmark)) AS minpractical"
				+ "                     FROM (SELECT me.exam_id,"
				+ "                                  me.student_id,"
				+ "                                  med.subject_id,"
				+ "                                  me.evaluator_type_id,"
				+ "                                  sum(med.theory_marks) AS finalthoerymark,"
				+ "                                  sum(med.practical_marks)"
				+ "                                     AS finalpracticalmark,"
				+ "                                  (SELECT COUNT(*)"
				+ "   FROM    EXAM_marks_entry me"
				+ "   LEFT JOIN"
				+ "   EXAM_marks_entry_details med"
				+ "  ON med.marks_entry_id = me.id"
				+ "   WHERE     me.exam_id = :examId"
				+ "   AND med.subject_id = :subjectId) AS total"
				+ "                             FROM    EXAM_marks_entry me"
				+ "                                  LEFT JOIN"
				+ "                                     EXAM_marks_entry_details med"
				+ "                                  ON med.marks_entry_id = me.id"
				+ "                            WHERE     me.exam_id = :examId"
				+ "                                  AND med.subject_id = :subjectId"
				+ "                           GROUP BY me.exam_id,"
				+ "                                    me.student_id,"
				+ "                                    med.subject_id,"
				+ "                                    me.evaluator_type_id) a"
				+ "                   GROUP BY a.student_id, a.subject_id) AS b"
				+ "           ON b.exam_id = :examId AND b.student_id = student.id"
				+ "  WHERE student.id IN"
				+ "           (SELECT era.student_id"
				+ "              FROM EXAM_re_valuation_application era"
				+ "                   JOIN EXAM_re_valuation_application_subject eras"
				+ "                      ON eras.revaluation_app_id = era.id"
				+ "                         AND eras.subject_id = :subjectId"
				+ "                   JOIN subject"
				+ "                      ON eras.subject_id = subject.id"
				+ "                         AND subject.is_theory_practical = :isTheoryPractical"
				+ "             WHERE     era.exam_id = :examId"
				+ "                   AND era.course_id = :courseId"
				+ "                   AND era.scheme_no = :schemeNo"
				+ "                   AND era.revaluation_type_id = :revaluationId"
				+ "                   AND era.is_active = 1)";

		if (orderRollNo == true) {

			HQL_String = HQL_String + " order by student.roll_no";

		} else {

			HQL_String = HQL_String + " order by student.register_no";
		}

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(HQL_String);
		query.setParameter("courseId", courseId);
		query.setParameter("subjectId", subjectId);
		query.setParameter("isTheoryPractical", isTheoryPractical);
		query.setParameter("examId", examId);
		query.setParameter("revaluationId", revaluationId);
		query.setParameter("schemeNo", schemeNo);
		return query.list();

	}

	// To get the ID of the first table
	public int insert_returnId(ExamRevaluationBO objBO) {
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
				session.flush();
				session.close();
			}
		}

		return mid;
	}

	// To delete from ExamRevaluationDetailsBO considering examRevaluationID
	public void delete_MarksEntryDetails(int id) {

		String HQL_QUERY = "delete from ExamRevaluationDetailsBO e"
				+ " where e.examRevaluationId = :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.executeUpdate();

		tx.commit();
		session.flush();
		session.close();
	}

	// TO UPDATE Details Table
	public void update_details(Integer id, BigDecimal theoryMarks,
			BigDecimal practicalMarks, boolean theoryPractical, boolean theory) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "";

		if (theoryPractical) {
			if (theory) {
				HQL_QUERY = "update ExamRevaluationDetailsBO eb set eb.previousTheoryMarks = :theoryMarks"
						+ " where eb.id = :id";
			} else {
				HQL_QUERY = "update ExamRevaluationDetailsBO eb set eb.previousTheoryMarks = :theoryMarks, eb.previousPracticalMarks = :practicalMarks"
						+ " where eb.id = :id";
			}
		} else {
			HQL_QUERY = "update ExamRevaluationDetailsBO eb set eb.previousPracticalMarks = :practicalMarks"
					+ " where eb.id = :id";
		}
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);

		if (theoryPractical) {
			if (theory) {
				query.setParameter("theoryMarks", theoryMarks);
			} else {
				query.setParameter("theoryMarks", theoryMarks);
				query.setParameter("practicalMarks", practicalMarks);
			}
		} else {
			query.setParameter("practicalMarks", practicalMarks);
		}

		query.executeUpdate();
		tx.commit();
		session.close();

	}

	// To get schemeNo for a course & exam
	public ArrayList<ExamExamCourseSchemeDetailsBO> select_SchemeNoBy_ExamId_CourseId(
			int examId, int courseId) {
		Session session = null;
		ArrayList list;
		try {

			String hql = "select e.courseSchemeId, e.schemeNo, e.courseSchemeUtilBO.name"
					+ " from ExamExamCourseSchemeDetailsBO e where e.examId = :examId and e.courseId = :courseId";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
			query.setParameter("examId", examId);
			query.setParameter("courseId", courseId);

			list = new ArrayList<ExamExamCourseSchemeDetailsBO>(query.list());
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

	// To get subjectType by subjectID
	public String selectSubjectsTypeBySubjectId(int subjectId) {
		String subjectType = "";
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query query = (Query) session
				.createQuery("select s.isTheoryPractical from Subject s where s.id= :subjectId");
		query.setParameter("subjectId", subjectId);
		List list = query.list();
		session.flush();
		if (list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object row = (Object) itr.next();
				if (row != null) {
					subjectType = row.toString();
				}
			}
		}
		return subjectType;

	}

}
