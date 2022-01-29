package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

@SuppressWarnings("unchecked")
public class ExamAssignOverallMarksImpl extends ExamGenImpl {
	private static Logger log = Logger
			.getLogger(ExamAssignOverallMarksImpl.class);

	// To get students for course, scheme & subject
	public List<Object[]> select_Students_course_Scheme_Subj(int courseId,
			int schemeId, int subjectId, int subjectTypeId, int schemeNO,
			String assignMentOverall, Integer examId,
			Integer overallAssignmentId, Integer academicYear, String isPreviousExam, String examType) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String isTheoryPractical = "";
		String SQL_QUERY = "";
		String origSubType = selectSubjectsTypeBySubjectId(subjectId);
		if (subjectTypeId == 1) {
			isTheoryPractical = "T";
		} else if (subjectTypeId == 0) {
			isTheoryPractical = "P";
		} else {
			isTheoryPractical = "B";
		}

		if (origSubType.equalsIgnoreCase("B")) {
			isTheoryPractical = "B";
		}
		String SQL1 = "";
		if (overallAssignmentId == null || overallAssignmentId == 0) {
			SQL1 = "is null";

		}
		if (overallAssignmentId != null && overallAssignmentId > 0) {
			//SQL1 = "=:overallAssignmentId";
			SQL1 = "=" + overallAssignmentId;

		}

		String SQL2 = "is null";
		if (examId != null && examId > 0) {
			SQL2 = "=:examId";
		}
		String supQuery = "";
		if(examType.equalsIgnoreCase("supyes")){
			if(!isPreviousExam.equalsIgnoreCase("true")){
				supQuery = supQuery + " and csd.academic_year = (select ed.exam_for_joining_batch from EXAM_definition ed where ed.id = :examId) and stu.id in ";
			}else{
				supQuery = " (select ed.exam_for_joining_batch from  EXAM_definition ed where ed.id = :examId) and stu.student_id in ";
			}
			
			supQuery = supQuery + "  ( select sia.student_id from EXAM_supplementary_improvement_application "
				+ " sia where sia.exam_id=:examId and sia.scheme_no=:schemeNO and sia.subject_id=:subjectId and "
				+ " sia.is_supplementary=1 ";
			if(isTheoryPractical.equalsIgnoreCase("T")){
				supQuery = supQuery + " and sia.is_appeared_theory = 1)";
			}
			else if(isTheoryPractical.equalsIgnoreCase("P")){
				supQuery = supQuery + " and sia.is_appeared_practical = 1)";
			}
			else if(isTheoryPractical == "B"){
				if(subjectTypeId == 1){
					supQuery = supQuery + " and sia.is_appeared_theory = 1)";
				}
				else if(subjectTypeId == 0){
					supQuery = supQuery + " and sia.is_appeared_practical = 1)";
				}
			}
			
			
		}
		else{
			if(isPreviousExam.equalsIgnoreCase("true")){
				supQuery = " (select ed.academic_year from  EXAM_definition ed where ed.id = :examId)";
			}
		}
		if (assignMentOverall != null
				&& assignMentOverall.equalsIgnoreCase("overall")) {
			if (overallAssignmentId != null && overallAssignmentId > 0) {
				if(!isPreviousExam.equalsIgnoreCase("true")){
					String newQueryString = "";
					String newSelectQuery = "";
					if (overallAssignmentId == 1) {
						newSelectQuery = " fm.theory_total_sub_internal_mark,  fm.practical_total_sub_internal_mark, ";	
						newQueryString = " LEFT JOIN EXAM_student_overall_internal_mark_details fm";
					}
					else{
						newSelectQuery = " fm.student_theory_marks,  fm.student_practical_marks, ";	
						newQueryString = " LEFT JOIN EXAM_student_final_mark_details fm";
					}
					SQL_QUERY = "SELECT  DISTINCT stu.register_no, "
						+ " stu.roll_no, "
						+ " personal_data.first_name, " +
						//+ " ifnull(eaom.theory_marks, fm.theory_total_mark),"
						//+ " ifnull(eaom.practical_marks, practical_total_mark),"
						newSelectQuery								
						//+ " eaom.id, "
						+ " fm.id, "
						+ " stu.id AS stuId, classes.id as classId"
						+ " FROM student stu " +
						//+ " LEFT JOIN EXAM_assign_overall_marks eaom "
						//+ " ON eaom.student_id = stu.id "
						//+ " AND eaom.is_overall_assignment_name = :assignMentOverall "
						//+ " AND eaom.overall_assignment_id "
						//+ SQL1
						//+ " AND eaom.exam_id "
						//+ SQL2
						//+ " AND eaom.subject_id = :subjectId "
						//+ " LEFT JOIN EXAM_student_overall_internal_mark_details fm"
						newQueryString 
						+ "  ON fm.student_id = stu.id 	and fm.subject_id = :subjectId "
						+ " LEFT JOIN adm_appln "
						+ " ON stu.adm_appln_id = adm_appln.id "
						+ " LEFT JOIN personal_data "
						+ " ON adm_appln.personal_data_id = personal_data.id "
						+ " LEFT JOIN applicant_subject_group asg "
						+ " ON asg.adm_appln_id = stu.adm_appln_id "
						+ " LEFT JOIN subject_group_subjects sgs "
						+ " ON asg.subject_group_id = sgs.subject_group_id "
						+ " LEFT JOIN subject_group sg "
						+ " ON sgs.subject_group_id = sg.id"
						+ " LEFT JOIN subject s "
						+ " ON sgs.subject_id = s.id "
						+ " LEFT JOIN class_schemewise "
						+ " ON stu.class_schemewise_id = class_schemewise.id "
						+ " LEFT JOIN curriculum_scheme_duration csd on class_schemewise.curriculum_scheme_duration_id = csd.id"
						+ " AND csd.semester_year_no = :schemeNO"
						+ " LEFT JOIN curriculum_scheme cs on csd.curriculum_scheme_id = cs.id"
						+ " AND cs.course_scheme_id = :schemeId"
						+ " LEFT JOIN classes "
						+ " ON class_schemewise.class_id = classes.id "
						+ " WHERE sgs.subject_id = :subjectId AND s.is_theory_practical = :isTheoryPractical and adm_appln.is_cancelled = 0  AND cs.course_id = :courseId  " +
						" and fm.exam_id and stu.is_active = 1"
						+ SQL2 +
						supQuery;
				} else{
					
						String newQueryString = "";
						String newSelectQuery = "";
						if (overallAssignmentId == 1) {
							newSelectQuery = " fm.theory_total_sub_internal_mark,  fm.practical_total_sub_internal_mark, ";	
							newQueryString = " LEFT JOIN EXAM_student_overall_internal_mark_details fm";
						}
						else{
							newSelectQuery = " fm.student_theory_marks,  fm.student_practical_marks, ";	
							newQueryString = " LEFT JOIN EXAM_student_final_mark_details fm";
						}
						 
						SQL_QUERY = " SELECT  DISTINCT curstu.register_no, "+
						  " curstu.roll_no, " + 
						  " personal_data.first_name, " + 
						  newSelectQuery +
						  " fm.id, " +
						  " stu.student_id AS stuId, stu.class_id" +
						  " FROM EXAM_student_previous_class_details stu" +
						  " LEFT JOIN student curstu on stu.student_id = curstu.id" +
						  newQueryString +
						  " ON fm.student_id = stu.student_id and fm.subject_id = :subjectId " +
						  " LEFT JOIN adm_appln " +
						  " ON curstu.adm_appln_id = adm_appln.id" + 
						  " LEFT JOIN personal_data " +
						  " ON adm_appln.personal_data_id = personal_data.id" + 
						  " LEFT JOIN EXAM_student_sub_grp_history asg " +
						  " ON asg.student_id = stu.student_id and asg.scheme_no = :schemeNO" +
						  " LEFT JOIN subject_group_subjects sgs " +
						  " ON asg.subject_group_id = sgs.subject_group_id" + 
						  " LEFT JOIN subject_group sg " +
						  " ON sgs.subject_group_id = sg.id" +
						  " LEFT JOIN subject s " +
						  " ON sgs.subject_id = s.id" + 
						  " LEFT JOIN classes cls" +
						  " ON stu.class_id = cls.id" + 
						  " WHERE sgs.subject_id = :subjectId AND s.is_theory_practical = :isTheoryPractical" + 
						  " and adm_appln.is_cancelled = 0  AND cls.course_id = :courseId and stu.academic_year >=" + supQuery +
						  "  and stu.scheme_no = :schemeNO " +
						  " and fm.exam_id = :examId and curstu.is_active = 1";
				}
			}
		} else {
			isPreviousExam = "false";
			SQL_QUERY = " SELECT DISTINCT"
					+ " stu.register_no,"
					+ " stu.roll_no,"
					+ " personal_data.first_name,"
					+ " eaom.theory_marks,"
					+ " eaom.practical_marks,"
					+ " eaom.id as eid,"
					+ " stu.id AS stuId"
					+ " FROM  student stu  LEFT JOIN"
					+ " EXAM_assign_overall_marks eaom"
					+ " ON eaom.student_id = stu.id"
					//+ " AND eaom.is_overall_assignment_name = :assignMentOverall "
					+ " AND eaom.overall_assignment_id "
					+ SQL1
					+ " AND eaom.subject_id = :subjectId"
					+ " LEFT JOIN adm_appln"
					+ " ON stu.adm_appln_id = adm_appln.id"
					+ " LEFT JOIN personal_data"
					+ " ON adm_appln.personal_data_id = personal_data.id"
					+ " LEFT JOIN applicant_subject_group asg"
					+ " ON asg.adm_appln_id = stu.adm_appln_id"
					+ " LEFT JOIN subject_group_subjects sgs"
					+ " ON asg.subject_group_id = sgs.subject_group_id"
					+ " LEFT JOIN subject_group sg"
					+ " ON sgs.subject_group_id = sg.id"
					+ " LEFT JOIN subject s"
					+ " ON sgs.subject_id = s.id"
					+ " LEFT JOIN class_schemewise"
					+ " ON stu.class_schemewise_id = class_schemewise.id"
					+ " LEFT JOIN curriculum_scheme_duration csd"
					+ " ON class_schemewise.curriculum_scheme_duration_id = csd.id"
					+ " AND csd.semester_year_no = :schemeNO"
					+ " LEFT JOIN curriculum_scheme cs"
					+ " ON csd.curriculum_scheme_id = cs.id AND cs.course_scheme_id = :schemeId"
					+ " WHERE sgs.subject_id = :subjectId"
					+ " AND s.is_theory_practical = :isTheoryPractical"
					+ " AND adm_appln.is_cancelled = 0"
					+ " AND cs.course_id = :courseId and stu.is_active = 1";
		}
		if(!isPreviousExam.equalsIgnoreCase("true")){
			if(!examType.equalsIgnoreCase("supyes")){
				if (examId != null && examId > 0) {
					SQL_QUERY = SQL_QUERY
							+ " AND csd.academic_year IN (SELECT ed.academic_year FROM EXAM_definition ed WHERE ed.id = :examId) group by stuId";
				} else {
					SQL_QUERY = SQL_QUERY
							+ " AND cs.year = :academicYear  group by stuId";
				}
			}
		}
		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("subjectId", subjectId);
		query.setParameter("schemeNO", schemeNO);
		query.setParameter("isTheoryPractical", isTheoryPractical);
		
		if(!isPreviousExam.equalsIgnoreCase("true")){
			query.setParameter("schemeId", schemeId);
			/*query.setParameter("assignMentOverall", assignMentOverall);
			if (overallAssignmentId != null && overallAssignmentId > 0) {
				query.setParameter("overallAssignmentId", overallAssignmentId);
			}*/
		}
		if (examId != null && examId > 0) {
			query.setParameter("examId", examId);
		} else {
			query.setParameter("academicYear", academicYear);
		}
		
		return query.list();

	}

	// On - SUBMIT(Update)
	public void update_OverallMarks(int id, BigDecimal theoryMark,
			BigDecimal practicalMarks, String userId) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String StringTheoryMark = null;
		if(theoryMark!= null){
			StringTheoryMark = theoryMark.toString();
		}
		String StringPracticalMark = null;
		if(practicalMarks!= null){
			StringPracticalMark =  practicalMarks.toString();
		}
		try {
			/*String HQL_QUERY = "update ExamAssignOverallMarksBO e set e.theoryMarks = :theoryMarks, e.practicalMarks= :practicalMarks"
					+ " where e.id = :id";*/
			String HQL_QUERY = "update ExamStudentOverallInternalMarkDetailsBO e set e.theoryTotalSubInternalMarks = :theoryMarks, e.practicalTotalSubInternalMarks= :practicalMarks"
			+ " where e.id = :id";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", id);
			query.setParameter("theoryMarks", StringTheoryMark);
			query.setParameter("practicalMarks", StringPracticalMark);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	// On - SUBMIT(Update)
	public void updateOverallAssignmentMarks(int id, BigDecimal theoryMark,
			BigDecimal practicalMarks, String userId) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String HQL_QUERY = "update ExamAssignOverallMarksBO e set e.theoryMarks = :theoryMarks, e.practicalMarks= :practicalMarks"
					+ " where e.id = :id";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", id);
			query.setParameter("theoryMarks", theoryMark);
			query.setParameter("practicalMarks", practicalMarks);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	// On - SUBMIT(Update)
	public void updateStudentFinalMarks(int id, BigDecimal theoryMark,
			BigDecimal practicalMarks, String userId) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String StringTheoryMark = null;
		if(theoryMark!= null){
			StringTheoryMark = theoryMark.toString();
		}
		String StringPracticalMark = null;
		if(practicalMarks!= null){
			StringPracticalMark =  practicalMarks.toString();
		}
		try {
			String HQL_QUERY = "update ExamStudentFinalMarkDetailsBO e set e.studentTheoryMarks = :theoryMarks, e.studentPracticalMarks= :practicalMarks"
			+ " where e.id = :id";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("id", id);
			query.setParameter("theoryMarks", StringTheoryMark);
			query.setParameter("practicalMarks", StringPracticalMark);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	// To get schemeNo for a course & exam
	public ArrayList<ExamExamCourseSchemeDetailsBO> select_SchemeNoBy_ExamId_CourseId(
			int examId, int courseId) {
		Session session = null;
		ArrayList list;
		try {
      //raghu added isactive=1
			String hql = "select e.courseSchemeId, e.schemeNo, e.courseSchemeUtilBO.name"
					+ " from ExamExamCourseSchemeDetailsBO e where e.examId = :examId and e.courseId = :courseId and e.isActive=1";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
			query.setParameter("examId", examId);
			query.setParameter("courseId", courseId);

			list = new ArrayList<ExamExamCourseSchemeDetailsBO>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." + e);
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamExamCourseSchemeDetailsBO>();
		}
		return list;
	}

	// To get subject type by subjectId
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

	public int getcurrentExamId() throws Exception {
		int id = 0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "select d.id from ExamDefinitionBO d where d.isCurrent=1";

		Query query = session.createQuery(HQL);

		List list = null;
		try {
			list = query.list();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." + e);
			throw new ApplicationException(e);
		}
		Iterator<Object[]> itr = list.iterator();
		while (itr.hasNext()) {
			Object row = (Object) itr.next();
			if (row != null) {
				id = Integer.parseInt(row.toString());
			}

		}

		return id;
	}

	public List<Object[]> select_Subjects(int courseId, int schemeId,
			int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String SQL_QUERY = " SELECT sub.id as subid,"
				+ "        sub.name,"
				+ "        sub.code,"
				+ "        et.date_endtime,"
				+ "        et.date_starttime,"
				+ "        et.id"
				+ "   FROM subject sub"
				+ "   LEFT JOIN EXAM_time_table et ON et.subject_id = sub.id"
				+ "   AND et.exam_course_scheme_id IN"
				+ "               (SELECT ecs.id"
				+ "                  FROM EXAM_exam_course_scheme_details ecs"
				+ "                 WHERE     ecs.course_id = :courseId"
				+ "                       AND ecs.course_scheme_id = :schemeId"
				+ "                       AND ecs.scheme_no = :schemeNo)"
				+ "  WHERE sub.id IN"
				+ "           (SELECT DISTINCT sgs.subject_id"
				+ "              FROM subject_group_subjects sgs"
				+ "             WHERE sgs.subject_group_id IN"
				+ "                      (SELECT css.subject_group_id"
				+ "                         FROM curriculum_scheme_subject css"
				+ "                        WHERE css.curriculum_scheme_duration_id IN"
				+ "                                 (SELECT csd.id"
				+ "                                    FROM curriculum_scheme_duration csd"
				+ "                                   WHERE csd.curriculum_scheme_id IN"
				+ "                                            (SELECT cs.id"
				+ "                                               FROM curriculum_scheme cs"
				+ "                                              WHERE cs.course_id = :courseId"
				+ "                                                    AND cs.course_scheme_id ="
				+ "                                                           :schemeId)"
				+ "                                         AND csd.semester_year_no = :schemeNo)))";

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeId", schemeId);
		query.setParameter("schemeNo", schemeNo);
		return query.list();

	}

	public List<Object[]> getSubjectInternalDetails(int courseId,
			int subjectId, String subjectType, int schemeNo, Integer examId)
			throws Exception {
		log
				.info("In getSubjectInternalDetails() method of ExamAssignOverallMarksImpl class");
		Session session = null;
		List<Object[]> objectsList = null;
		StringBuffer split_query = null;
		try {

			String hql = " from ExamSubjectRuleSettingsBO bo where bo.subjectId= "+subjectId+" and bo.courseId= "+courseId
					+ " and bo.schemeNo= "+schemeNo+" and bo.academicYear IN (select e.academicYear from ExamDefinitionBO e where e.id = "+examId+")";

			if (subjectType != null && subjectType.trim().length() > 0) {
				split_query = new StringBuffer();
				split_query.append("select ");
				int flag = 0;
				if (subjectType.equalsIgnoreCase("Theory")
						|| subjectType.equalsIgnoreCase("Theory and Practical")) {
					split_query
							.append("bo.finalTheoryInternalIsAssignment,bo.finalTheoryInternalIsAttendance,bo.finalTheoryInternalIsSubInternal");
					flag = 1;

				}
				if (subjectType.equalsIgnoreCase("Practical")
						|| subjectType.equalsIgnoreCase("Theory and Practical")) {
					if (flag == 1) {
						split_query.append(",");
					}
					split_query
							.append("bo.finalPracticalInternalIsAssignment,bo.finalPracticalInternalIsAttendance,bo.finalPracticalInternalIsSubInternal");
				}

			} else {
				split_query = new StringBuffer();
			}
			session = InitSessionFactory.getInstance().openSession();
			String strQuery=split_query.append(hql).toString();
			Query query = session.createQuery(strQuery);
			objectsList = query.list();
			split_query = null;
		} catch (Exception e) {
			log.error("Exception in getSubjectInternalDetails() method");
			if (session != null) {
				session.flush();
				session.close();
			}
			split_query = null;
			objectsList = new ArrayList<Object[]>();
		}

		return objectsList;
	}

	public Integer getClassId(int studentId, String isPreviousExam, String schmeNo) throws Exception {
		log.debug("inside getClassId");
		Session session = null;
		Integer classId = null;
		try {
			session = HibernateUtil.getSession();
			String strQuery="";
			if(isPreviousExam.equalsIgnoreCase("true"))
				strQuery="select e.classId  from ExamStudentPreviousClassDetailsBO e " +
					"where e.studentUtilBO.id="+studentId+" and e.schemeNo="+schmeNo;
			else
				strQuery="select s.classSchemewise.classes.id from Student s where s.id="
				+ studentId;
			
			Query query = session
					.createQuery(strQuery);
			Object obj = query.uniqueResult();

			if (obj != null)
				classId = (Integer) obj;
			session.flush();
			log.debug("leaving getClassId");
		} catch (Exception e) {
			log.error("Error in get ClassId checking...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return classId;

	}

	public List<Object> getInternalMarcks(int examId, int studentId,
			Integer classId, int subjectId) throws Exception {
		log.debug("inside isUserNameDuplcated");
		Session session = null;
		List<Object> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select so.theoryTotalSubInternalMarks,so.theoryTotalAttendenceMarks, "
							+ "so.theoryTotalAssignmentMarks, so.practicalTotalSubInternalMarks,"
							+ "so.practicalTotalAttendenceMarks,so.practicalTotalAssignmentMarks from "
							+ "ExamStudentOverallInternalMarkDetailsBO so where so.examDefinitionBO.id="
							+ examId
							+ "and so.studentUtilBO.id="
							+ studentId
							+ " and so.classUtilBO.id="
							+ classId
							+ " and so.subjectUtilBO.id=" + subjectId);
			list = query.list();

			session.flush();
			log.debug("leaving isUserNameDuplcated");
		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		return list;

	}

	public Integer getAccodamicYear(int studentId) {
		log.debug("inside getAccodamicYear");
		Session session = null;
		Integer classId = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select s.classSchemewise.curriculumSchemeDuration.academicYear from Student s where s.id="
							+ studentId);
			Object obj = query.uniqueResult();

			if (obj != null)
				classId = (Integer) obj;
			session.flush();
			log.debug("leaving getAccodamicYear");
		} catch (Exception e) {
			log.error("Error in get Accodamic Year checking...", e);
			session.flush();
			e.printStackTrace();
		}
		return classId;
		// return null;
	}

	public List<Object[]> getMaxMarcks(Integer accyear, int courseId,
			int schemeNo, int subjectId, String assignmentOverall,
			int assignTypeId, String type) {
		log.debug("inside getMaxMarcks");
		Session session = null;
		List<Object[]> list = null;
		Query query = null;
		try {
			session = HibernateUtil.getSession();
			if (assignmentOverall.equalsIgnoreCase("overall")) {
				if (type != null && type.contains("1")) {

					query = session
							.createQuery("select srs.theoryIntEntryMaxMarksTotal, srs.practicalIntEntryMaxMarksTotal"
									+ " from ExamSubjectRuleSettingsBO srs where srs.academicYear= ? and "
									+ " srs.courseId= ? and srs.schemeNo= ?  and srs.subjectId= ? ");
					query.setInteger(0, accyear);
					query.setInteger(1, courseId);
					query.setInteger(2, schemeNo);
					query.setInteger(3, subjectId);
				} else {
					query = session
							.createQuery("select srs.theoryEseEnteredMaxMark, srs.practicalEseEnteredMaxMark "
									+ "from ExamSubjectRuleSettingsBO srs where   "
									+ "srs.courseId= ? and srs.schemeNo= ?  and srs.subjectId= ? ");
					query.setInteger(0, courseId);
					query.setInteger(1, schemeNo);
					query.setInteger(2, subjectId);
				}

			} else if (assignmentOverall.equalsIgnoreCase("Assignment")
					&& assignTypeId > 0) {
				String HQL = "SELECT sa.maximum_mark, p.praMax FROM  EXAM_subject_rule_settings ss"
						+ " LEFT JOIN EXAM_subject_rule_settings_assignment sa ON sa.subject_rule_settings_id = ss.id"
						+ " INNER JOIN (SELECT sa.maximum_mark praMax FROM    EXAM_subject_rule_settings ss"
						+ " LEFT JOIN EXAM_subject_rule_settings_assignment sa ON sa.subject_rule_settings_id = ss.id"
						+ " WHERE sa.is_theory_practical = 'p' AND ss.course_id = :courseId AND ss.subject_id = :subjectId"
						+ " AND ss.academic_year=:academicYear AND ss.scheme_no=:schemeNo AND sa.assignment_type_id = :assTypeId) AS p WHERE sa.is_theory_practical = 't'"
						+ " AND ss.course_id = :courseId AND ss.subject_id = :subjectId AND ss.scheme_no=:schemeNo AND ss.academic_year=:academicYear AND sa.assignment_type_id = :assTypeId ";
				query = session.createSQLQuery(HQL);

				query.setParameter("courseId", courseId);
				query.setParameter("schemeNo", schemeNo);
				query.setParameter("academicYear", accyear);
				query.setParameter("subjectId", subjectId);
				query.setParameter("assTypeId", assignTypeId);

			}

			list = query.list();

			session.flush();
			log.debug("leaving isUserNameDuplcated");
		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();

		}
		return list;
	}

	public List<Object[]> getCourseByAcademicYear(int year) throws Exception {
		log.debug("inside isUserNameDuplcated");
		Session session = null;
		List<Object[]> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select distinct csd.curriculumSchemeUtilBO.courseId, "
							+ "csd.curriculumSchemeUtilBO.examCourseUtilBO.courseName from "
							+ "CurriculumSchemeDurationUtilBO csd where csd.academicYear = ? and "
							+ "csd.curriculumSchemeUtilBO.examCourseUtilBO.isActive = 1 ");

			query.setInteger(0, year);

			list = query.list();

			session.flush();
			log.debug("leaving isUserNameDuplcated");
		} catch (Exception e) {
			log.error("Error in duplication checking...", e);
			session.flush();

		}
		return list;

	}

	public Integer getExamAcademicYear(Integer examId) throws Exception {
		Integer academicYear = 1;

		Session session = HibernateUtil.getSession();
		try {
			Query query = session
					.createQuery("select bo.academicYear from ExamDefinitionBO bo where bo.id= :examId");
			query.setParameter("examId", examId);
			List l = query.list();
			if (l != null && l.size() > 0 && l.get(0) != null) {
				academicYear = Integer.parseInt(l.get(0).toString());
			}
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return academicYear;
	}

	public ArrayList<Object[]> select_ActiveOnly_ExamNameRRS() throws Exception {
		Session session = null;
		ArrayList<Object[]> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

		/*	String HQL = " select ed.id,ed.name"
					+ " from ExamDefinitionBO ed where ed.delIsActive=1 and ed.isActive=1 and"
					+ " ed.examTypeUtilBO.name in ('Regular','Regular & Supplementary') order by"
					+ " ed.isCurrent , ed.year, ed.month desc";*/
			String HQL = " select ed.id,ed.name"
				+ " from ExamDefinitionBO ed where ed.delIsActive=1 and ed.isActive=1 and"
				+ " ed.examTypeUtilBO.id != 4 and ed.examTypeUtilBO.id != 5 order by"
				+ " ed.isCurrent , ed.year, ed.month desc";

			Query query = session.createQuery(HQL);
			list = (ArrayList<Object[]>) query.list();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object[]>();
		}
		return list;

	}
	// To get schemeNo for a course & exam
	public ArrayList<ExamExamCourseSchemeDetailsBO> select_SchemeNoBy_ExamId(
			int examId) {
		Session session = null;
		ArrayList list;
		try {
      //raghu added isactive=1
			String hql = "select e.courseSchemeId, e.schemeNo, e.courseSchemeUtilBO.name"
					+ " from ExamExamCourseSchemeDetailsBO e where e.examId = :examId  and e.isActive=1 group by e.schemeNo";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
			query.setParameter("examId", examId);
			

			list = new ArrayList<ExamExamCourseSchemeDetailsBO>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." + e);
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamExamCourseSchemeDetailsBO>();
		}
		return list;
	}

}
