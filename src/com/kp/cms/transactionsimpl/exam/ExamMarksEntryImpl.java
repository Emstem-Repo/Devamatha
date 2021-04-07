package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamMarksEntryImpl extends ExamGenImpl {

	public List<Object[]> selectMarksDetail_AllSubForOneStudent(int studentId,
			ArrayList<Integer> listSubjectIds, int examId, Integer ansScriptId,
			Integer evaluatorId, String examType) throws DataNotFoundException {
		Session session = null;
		try{
		List returnList = null;
		
		if (listSubjectIds.size() == 0) {
			throw new DataNotFoundException();
		}

		String SQL1 = null;
		String SQL2 = null;
		if (evaluatorId == null) {
			SQL1 = " and me.evaluator_type_id is null ";
		} else {
			SQL1 = " and me.evaluator_type_id = :evaluatorId";
		}

		if (ansScriptId == null) {
			SQL2 = " and me.answer_script_type is null ";
		} else {
			SQL2 = " and me.answer_script_type = :ansScriptId";
		}
		Query query = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String suppQuery = "";
		if (examType.equalsIgnoreCase("Supplementary")) {
			suppQuery = "select sia.subject_id from EXAM_supplementary_improvement_application sia"
					+ " where sia.exam_id=:examId and sia.student_id = :studentId and sia.subject_id in"
					+ " (:listSubjectIds) and sia.is_supplementary=1 and sia";
		} else {
			suppQuery = ":listSubjectIds";
		}
		String SQL = "select me.id AS meid, me.marks_card_no, me.marks_card_date,"
				+ " MAX(med.id) AS medid,"
				+ " s.id AS sid, s.name,"
				+ " MAX(med.theory_marks),"
				+ " MAX(med.practical_marks),s.is_theory_practical"
				+ " from subject s"
				+ " left outer join EXAM_marks_entry me on me.student_id = :studentId and me.exam_id = :examId"
				+ SQL1
				+ SQL2
				+ " and me.is_secured = 0"
				+ " left outer join EXAM_marks_entry_details med on "
				+ " med.subject_id = s.id and med.marks_entry_id = me.id "
				+ " where s.id in (" + suppQuery + ") group by sid";

		query = session.createSQLQuery(SQL);
		query.setParameter("studentId", studentId);
		query.setParameterList("listSubjectIds", listSubjectIds);
		query.setParameter("examId", examId);
		if (ansScriptId != null && ansScriptId > 0) {
			query.setParameter("ansScriptId", ansScriptId);
		}
		if (evaluatorId != null) {
			query.setParameter("evaluatorId", evaluatorId);
		}
		returnList = query.list();

		return returnList;
		}catch (Exception e) {
			throw  new DataNotFoundException();
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public Integer insert_MarksEntry(ExamMarksEntryBO marksMaster,
			ArrayList<ExamMarksEntryDetailsBO> marksDetailsList) throws Exception {
		try {
			Integer masterID;
			boolean newMaster = false;
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			if (marksMaster.getId() != null && marksMaster.getId() > 0) {
				// update the existing row
				Transaction tx = session.beginTransaction();
				session.merge(marksMaster);
				tx.commit();

				masterID = marksMaster.getId();
				session.flush();
			} else {
				// insert it
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
			e.printStackTrace();
			return 0;
		}
	}

	public void insert_MarksEntryDetails(
			ArrayList<ExamMarksEntryDetailsBO> marksDetailsList,
			boolean newMaster, Integer masterID) throws Exception {
		if (!newMaster) {
			delete_MarksEntryDetails(masterID);
		}
		for (ExamMarksEntryDetailsBO detBO : marksDetailsList) {
			detBO.setMarksEntryId(masterID);
		}
		insert_List(marksDetailsList);
	}


	// To fetch all students for a single subject (AS - SS)
	public List<Object[]> select_allStudentsForOneSubject(Integer courseId,
			int subjectId, int subjectTypeId, String orderRollNo, int examId,
			Integer ansScriptId, Integer evaluatorId, int schemeNo,
			String originalSubjectType, String examType, int classId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String isTheoryPractical = "";

		if (subjectTypeId == 1) {
			isTheoryPractical = "T";
		} else if (subjectTypeId == 0) {
			isTheoryPractical = "P";
		} else {
			isTheoryPractical = "B";
		}
		if (originalSubjectType != null) {
			if (originalSubjectType.equalsIgnoreCase("b")) {
				isTheoryPractical = "B";
			} else if (!isTheoryPractical.equalsIgnoreCase(originalSubjectType)) {
				return new ArrayList<Object[]>();
			}
		}
		String SQL1 = null;
		String SQL2 = null;
		String SQL3 = "";

		String suppQuery = "";
		if (examType.equalsIgnoreCase("Supplementary")) {
			suppQuery = " and  st.id  in ( select sia.student_id from EXAM_supplementary_improvement_application"
					+ " sia where sia.exam_id=:examId and sia.scheme_no=:schemeNo and sia.subject_id=:subjectId and"
					+ " sia.is_supplementary=1 ";
			if(isTheoryPractical.equalsIgnoreCase("T")){
				suppQuery = suppQuery + " and sia.is_appeared_theory = 1)";
			}
			else if(isTheoryPractical.equalsIgnoreCase("P")){
				suppQuery = suppQuery + " and sia.is_appeared_practical = 1)";
			}
			else if(isTheoryPractical == "B"){
				if(subjectTypeId == 1){
					suppQuery = suppQuery + " and sia.is_appeared_theory = 1)";
				}
				else if(subjectTypeId == 0){
					suppQuery = suppQuery + " and sia.is_appeared_practical = 1)";
				}
			}
		}
		String yearString = "";
		String yearString1 = "";
		if (examType.equalsIgnoreCase("Supplementary")) {
			
			yearString = " AND csd.academic_year >= (SELECT "  +
						 " ed.exam_for_joining_batch FROM EXAM_definition ed "
						 + " WHERE ed.id = :examId) " ;
			
			
			yearString1 =  " AND esp.academic_year >= (select " +
			 " e.exam_for_joining_batch from EXAM_definition e where e.id = :examId)";
			
		}
		else
		{
			yearString = " AND csd.academic_year = (SELECT "  +
			 " ed.academic_year FROM EXAM_definition ed "
			 + " WHERE ed.id = :examId) " ;

			yearString1 = " AND esp.academic_year IN (select " +
			"  e.academic_year from EXAM_definition e where e.id = :examId)";
		}
		if (evaluatorId == null) {
			SQL1 = " AND me.evaluator_type_id is null ";
		} else {
			SQL1 = " AND me.evaluator_type_id = :evaluatorId";
		}

		if (ansScriptId == null) {
			SQL2 = " AND me.answer_script_type  is null ";
		} else {
			SQL2 = " AND me.answer_script_type = :ansScriptId";
		}

		if (orderRollNo != null && orderRollNo.equalsIgnoreCase("register")) {
			SQL3 = " Group by st.register_no order by st.register_no)";
		} else if (orderRollNo != null && orderRollNo.equalsIgnoreCase("roll")) {

			SQL3 = " Group by st.roll_no order by st.roll_no)";
		}
		
		String classIdSql = "";
		if(classId > 0){
			classIdSql = " and classes.id = :classId";
		}
		
		String SQL_QUERY = "";

		SQL_QUERY = " (SELECT st.roll_no," + " st.register_no,"
				+ " st.id AS sti," + " personal_data.first_name,"
				+ " personal_data.last_name," + " me.id AS meid,"
				+ " MAX(med.id) AS medid," + " MAX(med.theory_marks),"
				+ " MAX(med.practical_marks)," + " me.marks_card_date"
				+ " FROM student st LEFT JOIN" + " EXAM_marks_entry me"
				+ " ON me.student_id = st.id AND me.exam_id = :examId"
				+ " AND me.is_secured = 0 "
				+ SQL1
				+ SQL2
				+ " LEFT JOIN EXAM_marks_entry_details med ON med.marks_entry_id = me.id"
				+ " AND med.subject_id = :subjectId LEFT JOIN"
				+ " applicant_subject_group asg ON asg.adm_appln_id = st.adm_appln_id"
				+ " LEFT JOIN subject_group_subjects sgs"
				+ " ON asg.subject_group_id = sgs.subject_group_id  and sgs.is_active = 1"
				+ " JOIN subject_group ON sgs.subject_group_id = subject_group.id"
				+ " AND subject_group.course_id = :courseId  and subject_group.is_active = 1 LEFT JOIN subject s"
				+ " ON sgs.subject_id = s.id"
				+ " LEFT JOIN class_schemewise"
				+ " ON st.class_schemewise_id = class_schemewise.id"
				+ " LEFT JOIN curriculum_scheme_duration csd"
				+ " ON class_schemewise.curriculum_scheme_duration_id = csd.id"
				+ "  LEFT JOIN curriculum_scheme"
				+ " ON curriculum_scheme.id = csd.curriculum_scheme_id"
				+ " AND curriculum_scheme.course_id = :courseId"
				+ " LEFT JOIN classes ON class_schemewise.class_id = classes.id and classes.is_active = 1"
				+ " LEFT JOIN adm_appln"
				+ " ON st.adm_appln_id = adm_appln.id"
				+ " LEFT JOIN personal_data"
				+ " ON adm_appln.personal_data_id = personal_data.id"
				+ " WHERE sgs.subject_id = :subjectId"
				+ " AND s.is_theory_practical = :isTheoryPractical"
				+  yearString + " AND csd.semester_year_no = :schemeNo"
				+ " AND adm_appln.is_cancelled = 0 "
				+ classIdSql 
				+ suppQuery
				+ SQL3
				+ " UNION"
				+ " (SELECT st.roll_no,"
				+ " st.register_no,"
				+ " st.id AS sti,"
				+ " personal_data.first_name,"
				+ " personal_data.last_name,"
				+ " me.id AS meid,"
				+ " MAX(med.id) AS medid,"
				+ " MAX(med.theory_marks),"
				+ " MAX(med.practical_marks),"
				+ " me.marks_card_date"
				+ " FROM  student st LEFT JOIN"
				+ " EXAM_marks_entry me"
				+ " ON me.student_id = st.id"
				+ " AND me.exam_id = :examId"
				+ " AND me.is_secured = 0"
				+ SQL1
				+ SQL2
				+ " LEFT JOIN  EXAM_student_previous_class_details esp"
				+ " ON esp.student_id = st.id"
				+ " LEFT JOIN EXAM_marks_entry_details med"
				+ " ON med.marks_entry_id = me.id AND med.subject_id = :subjectId  "
				+ "  JOIN   classes"
				+ "  ON classes.id = esp.class_id AND classes.course_id = :courseId LEFT JOIN"
				+ " adm_appln ON st.adm_appln_id = adm_appln.id"
				+ " LEFT JOIN personal_data"
				+ " ON adm_appln.personal_data_id = personal_data.id JOIN"
				+ " EXAM_student_sub_grp_history sh"
				+ " ON sh.student_id = st.id"
				+ " JOIN subject_group_subjects sgs"
				+ " ON sgs.subject_group_id = sh.subject_group_id"
				+ " AND sgs.is_active = 1"
				+ " JOIN subject sub"
				+ " ON sgs.subject_id = sub.id"
				+ " WHERE sub.id = :subjectId"
				+ " AND sub.is_theory_practical = :isTheoryPractical"
				+ " AND esp.scheme_no = :schemeNo"
				+	yearString1 
				+ " AND adm_appln.is_cancelled = 0" + classIdSql  + suppQuery + SQL3;

		try {
			Query query = session.createSQLQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);

			query.setParameter("subjectId", subjectId);
			query.setParameter("examId", examId);
			query.setParameter("isTheoryPractical", isTheoryPractical);
			if (ansScriptId != null) {
				query.setParameter("ansScriptId", ansScriptId);
			}
			if (evaluatorId != null) {
				query.setParameter("evaluatorId", evaluatorId);
			}
			if(classId > 0){
				query.setParameter("classId", classId);
			}
			List l1 = query.list();
			if (l1 != null && l1.size() > 0) {
				return l1;
			} else {
				return new ArrayList();
			}
		} catch (Exception e) {
			return new ArrayList();
		}

	}

	public int insert_returnId(ExamMarksEntryBO objBO) throws Exception {
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

	public void delete_MarksEntryDetails(int id) throws Exception {
		Session session = null;
		try{
		String HQL_QUERY = "delete from ExamMarksEntryDetailsBO e"
				+ " where e.marksEntryId = :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
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
	}

	// UPDATE DETAILS - based on subject type
	public void update_details(Integer id, String theoryMarks,
			String practicalMarks, boolean theoryPractical, boolean theory) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "";

		if (theoryPractical) {
			if (theory) {
				HQL_QUERY = "update ExamMarksEntryDetailsBO eb set eb.theoryMarks = :theoryMarks"
						+ " where eb.id = :id";
			} else {

				HQL_QUERY = "update ExamMarksEntryDetailsBO eb set eb.theoryMarks = :theoryMarks, eb.practicalMarks = :practicalMarks"
						+ " where eb.id = :id";
			}
		} else {
			HQL_QUERY = "update ExamMarksEntryDetailsBO eb set eb.practicalMarks = :practicalMarks"
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
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	// To get Class Code for Roll/RegNo
	public String getClassCodeforRegRoll(String rollNo, String registerNo,
			boolean useRollNo) throws Exception {
		Session session = null;
		String classCode = "";
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL = "from ClassUtilBO c where c.id in "
					+ "(select cs.classId from ClassSchemewiseUtilBO cs where cs.id in"
					+ "(select s.classSchemewiseId from StudentUtilBO s";
			if (useRollNo) {
				HQL = HQL + " where s.rollNo = :rollNo))";
			} else {
				HQL = HQL + " where s.registerNo = :registerNo))";
			}

			Query query = session.createQuery(HQL);
			if (useRollNo) {
				query.setParameter("rollNo", rollNo);
			} else {
				query.setParameter("registerNo", registerNo);
			}

			Iterator<ClassUtilBO> itr = query.iterate();

			while (itr.hasNext()) {

				ClassUtilBO bO = (ClassUtilBO) itr.next();
				classCode = bO.getName() + "-" + bO.getSectionName();
			}

			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				// session.flush();
				session.close();
			}
		}

		return classCode;
	}

	public ArrayList<ExamExamCourseSchemeDetailsBO> select_SchemeNoBy_ExamId_CourseId(
			int examId, int courseId) throws Exception {
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

	// Validation for Multiple Answer Scripts
	public Integer select_answerScriptFrom_ruleSettings(Integer courseId,
			Integer schemeNo, Integer subjectId, Integer subjectTypeId,
			Integer examId) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String isTheoryPractical = "";
		Integer subRuleId = 0;
		List l = null;

		if (subjectTypeId != null) {
			if (subjectTypeId == 1) {
				isTheoryPractical = "t";
			} else if (subjectTypeId == 0) {
				isTheoryPractical = "p";
			} else {
				isTheoryPractical = "b";
			}
		}
		Integer examTypeId=(Integer)session.createQuery("select ed.examTypeID from ExamDefinitionBO ed where ed.id ="+examId).uniqueResult();
		String HQL_QUERY ="";
		if(examTypeId!=null && (examTypeId==6)){
			 HQL_QUERY = "select esrm.id"
					+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO esrm"
					+ " join esrm.examSubjectRuleSettingsBO esr"
					+ " where esr.schemeNo = :schemeNo"
					+ " and esr.academicYear >= (select ed.examForJoiningBatch from ExamDefinitionBO ed where ed.id = :examId) and esr.isActive = 1";
		}else{
		 HQL_QUERY = "select esrm.id"
				+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO esrm"
				+ " join esrm.examSubjectRuleSettingsBO esr"
				+ " where esr.schemeNo = :schemeNo"
				+ " and esr.academicYear = (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId) and esr.isActive = 1";
		}
		if(courseId!= null && courseId > 0){
			HQL_QUERY = HQL_QUERY  + " and esr.courseId = :courseId ";
		}
		if (subjectId != null) {
			HQL_QUERY = HQL_QUERY + " and esr.subjectId = :subjectId";
		}
		if (subjectTypeId != null) {
			if (subjectTypeId == 1) {
				HQL_QUERY = HQL_QUERY
						+ " and esrm.isTheoryPractical = :isTheoryPractical and esr.theoryEseIsMultipleAnswerScript = 1 ";

			} else if (subjectTypeId == 0) {
				HQL_QUERY = HQL_QUERY
						+ " and esrm.isTheoryPractical = :isTheoryPractical"
						+ " and esr.practicalEseIsMultipleAnswerScript = 1 ";
			} else {
				HQL_QUERY = HQL_QUERY
						+ "  and esrm.isTheoryPractical = :isTheoryPractical"
						+ " and esr.theoryEseIsMultipleAnswerScript = 1 and esr.practicalEseIsMultipleAnswerScript = 1 ";
			}
		} else {
			HQL_QUERY = HQL_QUERY
					+ " and (esr.theoryEseIsMultipleAnswerScript = 1 or esr.practicalEseIsMultipleAnswerScript = 1)";
		}
		Query query = session.createQuery(HQL_QUERY);
		if(courseId!= null && courseId > 0){
			query.setParameter("courseId", courseId);
		}
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("examId", examId);
		if (subjectId != null) {
			query.setParameter("subjectId", subjectId);
		}

		if (!isTheoryPractical.equalsIgnoreCase("")) {
			query.setParameter("isTheoryPractical", isTheoryPractical);
		}

		l = query.list();

		if (l != null && l.size() > 0) {

			subRuleId = (Integer) l.get(0);

		}
		return subRuleId;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	// To FETCH Evaluator List
	public List<Integer> select_evaluatorList_ruleSettings(Integer courseId,
			Integer schemeNo, Integer subjectId, Integer subjectTypeId,
			Integer examId) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String isTheoryPractical = "";
		List l = null;
		if (subjectTypeId != null) {
			if (subjectTypeId == 1) {
				isTheoryPractical = "t";
			} else if (subjectTypeId == 0) {
				isTheoryPractical = "p";
			} else {
				isTheoryPractical = "b";
			}
		}
		Integer examTypeId=(Integer)session.createQuery("select ed.examTypeID from ExamDefinitionBO ed where ed.id ="+examId).uniqueResult();
		String HQL_QUERY ="";
		if(examTypeId!=null && (examTypeId==6 || examTypeId==3)){
			HQL_QUERY= "select distinct esv.evaluatorId"
				+ " from ExamSubjecRuleSettingsMultipleEvaluatorBO esv"
				+ " join esv.examSubjectRuleSettingsBO esvs"
				+ " where esvs.schemeNo = :schemeNo "
				+ " and esvs.academicYear = (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId) and esvs.isActive = 1";
		}else{
		HQL_QUERY= "select distinct esv.evaluatorId"
				+ " from ExamSubjecRuleSettingsMultipleEvaluatorBO esv"
				+ " join esv.examSubjectRuleSettingsBO esvs"
				+ " where esvs.schemeNo = :schemeNo "
				+ " and esvs.academicYear = (select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId) and esvs.isActive = 1";
		}
		if(courseId!= null && courseId > 0){
				HQL_QUERY = HQL_QUERY + " and esvs.courseId = :courseId ";  
		}
		
		if (subjectId != null && subjectTypeId != null) {
			if (subjectTypeId == 1) {
				HQL_QUERY = HQL_QUERY
						+ " and esvs.subjectId = :subjectId and esv.isTheoryPractical = :isTheoryPractical"
						+ " and esvs.theoryEseIsMultipleEvaluator = 1";

			} else if (subjectTypeId == 0) {
				HQL_QUERY = HQL_QUERY
						+ " and esvs.subjectId = :subjectId and esv.isTheoryPractical = :isTheoryPractical"
						+ " and esvs.practicalEseIsMultipleEvaluator = 1";
			} else {
				HQL_QUERY = HQL_QUERY
						+ " and esvs.subjectId = :subjectId and esv.isTheoryPractical in ('t', 'p')"
						+ " and esvs.theoryEseIsMultipleEvaluator = 1 and esvs.practicalEseIsMultipleEvaluator = 1";
			}
		} else {

			HQL_QUERY = HQL_QUERY
					+ " and (esvs.theoryEseIsMultipleEvaluator = 1 or esvs.practicalEseIsMultipleEvaluator = 1)";

		}
		Query query = session.createQuery(HQL_QUERY);
		if(courseId!= null && courseId > 0){
			query.setParameter("courseId", courseId);
		}
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("examId", examId);
		if (subjectId != null) {
			query.setParameter("subjectId", subjectId);
		}
		if (!isTheoryPractical.equalsIgnoreCase("")
				&& !isTheoryPractical.equalsIgnoreCase("b")) {
			query.setParameter("isTheoryPractical", isTheoryPractical);
		}

		l = query.list();
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

	public String getCurrentExamName(String examTypeName) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String id = null;

		String HQL_QUERY = null;
		if(StringUtils.isNumeric(examTypeName)){
			HQL_QUERY = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.id=" +examTypeName+
					" and d.delIsActive = 1 and d.isActive=1";
		}
		else {
			if (examTypeName.contains("Reg")) {
				HQL_QUERY = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.name like 'Reg%' and d.delIsActive = 1 and d.isActive=1";
			} else if (examTypeName.contains("Suppl")) {
				HQL_QUERY = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.name like 'Suppl%' and d.delIsActive = 1 and d.isActive=1";
			} else if (examTypeName.contains("Int")) {
				HQL_QUERY = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.name like 'Int%' and d.delIsActive = 1 and d.isActive=1";
			}
		}
		Query query = session.createQuery(HQL_QUERY);

		List list = query.list();

		if (list != null && list.size() > 0) {

			id = list.get(0).toString();
		}
		return id;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	// Get Absent code from Exam Settings
	public String getCodeForAbsentEntry() throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String name = null;

		String HQL_QUERY = "select es.absentCodeMarkEntry"
				+ " from ExamSettingsBO es";

		Query query = session.createQuery(HQL_QUERY);

		List list = query.list();

		if (list != null && list.size() > 0) {

			name = (String) list.get(0);
		}
		return name;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// Get Not Process code from Exam Settings
	public String getCodeForNotprocessEntry() throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String name = null;

		String HQL_QUERY = "select es.notProcedCodeMarkEntry"
				+ " from ExamSettingsBO es";

		Query query = session.createQuery(HQL_QUERY);

		List list = query.list();

		if (list != null && list.size() > 0) {

			name = (String) list.get(0);
		}
		return name;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// Marks Validation for theory
	public BigDecimal getTheoryMaxMarkForSubject(String examType,
			Integer courseId, Integer schemeNo, Integer subjectId,
			Integer subjectTypeId, Integer examId, Integer ansScriptId,
			Integer multipleEvaluator, Integer academicYear) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		BigDecimal maxTheoryMarks = null;
		Integer intType = 0;
		List l = null;

		String HQL_QUERY = "";
		String academicString = "";
		if(examType.equalsIgnoreCase("Supplementary") && academicYear != null){
			academicString = "  and srs.academicYear = " + academicYear;
		}else{
			academicString = "  and srs.academicYear IN (select e.academicYear from ExamDefinitionBO e where e.id = " + examId + ")";
		}
		
		if (examType != null
				&& (examType.equalsIgnoreCase("Regular") || examType
						.equalsIgnoreCase("Supplementary"))) {
			if (subjectId != null && subjectTypeId != null) {

				if ((multipleEvaluator != null && (ansScriptId == null || ansScriptId == 0))
						|| ((ansScriptId == null || ansScriptId == 0) && (multipleEvaluator == null || multipleEvaluator == 0))) {

					HQL_QUERY = "select srs.theoryEseEnteredMaxMark"
							+ " from ExamSubjectRuleSettingsBO srs"
							+ " where srs.courseId = :courseId and srs.schemeNo = :schemeNo"
							+ " and srs.subjectId = :subjectId"
							+ academicString
							+ " and srs.isActive = 1";

				} else if ((ansScriptId != null && (multipleEvaluator == null || multipleEvaluator == 0))
						|| (ansScriptId != null && multipleEvaluator != null)) {

					HQL_QUERY = "select ans.value"
							+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO ans"
							+ " join ans.examSubjectRuleSettingsBO s"
							+ " where s.courseId = :courseId and s.schemeNo = :schemeNo and s.subjectId = :subjectId"
							+ academicString
							+ "  and s.isActive = 1 "
							+ " and ans.isTheoryPractical = 't' and ans.multipleAnswerScriptId = :ansScriptId";
				}

			}
			Query query = session.createQuery(HQL_QUERY);

			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			//query.setParameter("examId", examId);
			if ((ansScriptId != null && ansScriptId != 0)) {
				query.setParameter("ansScriptId", ansScriptId);
			}

			if (subjectId != null) {
				query.setParameter("subjectId", subjectId);
			}

			l = query.list();

			if (l != null && l.size() > 0) {

				maxTheoryMarks = (BigDecimal) l.get(0);

			}

		}

		else if (examType != null && examType.equalsIgnoreCase("Internal")) {

			if (subjectId != null && subjectTypeId != null) {
				intType = getIntType(examId);

				if (intType != null && intType > 0) {
					HQL_QUERY = "select e.enteredMaxMark"
							+ " from ExamSubjectRuleSettingsSubInternalBO e"
							+ " join e.examSubjectRuleSettingsBO srs"
							+ " where srs.courseId = :courseId and srs.schemeNo = :schemeNo and srs.academicYear IN (select d.academicYear from ExamDefinitionBO d where d.id = :examId) and srs.isActive = 1"
							+ " and e.internalExamTypeId IN (:intType) and e.isTheoryPractical = 't' and srs.subjectId= :subjectId";

					Query query = session.createQuery(HQL_QUERY);
					query.setParameter("courseId", courseId);
					query.setParameter("schemeNo", schemeNo);
					query.setParameter("examId", examId);
					query.setParameter("intType", intType);
					query.setParameter("subjectId", subjectId);
					maxTheoryMarks = getMarks(query.list());
				}
			}
		}

		return maxTheoryMarks;
	}catch (Exception e) {
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
	}

	// Marks Validation for practical
	public BigDecimal getPracticalMaxMarkForSubject(String examType,
			Integer courseId, Integer schemeNo, Integer subjectId,
			Integer subjectTypeId, Integer examId, Integer ansScriptId,
			Integer multipleEvaluator, Integer academicYear) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		BigDecimal maxPracticalMarks = null;
		Integer intType = 0;
		List l = null;

		String HQL_QUERY = "";
		String academicString = "";
		if(examType.equalsIgnoreCase("Supplementary") && academicYear != null){
			academicString = "  and srs.academicYear = " + academicYear;
		}else{
			academicString = "  and srs.academicYear IN (select e.academicYear from ExamDefinitionBO e where e.id = " + examId + ")";
		}
		if (examType != null
				&& (examType.equalsIgnoreCase("Regular") || examType
						.equalsIgnoreCase("Supplementary"))) {
			if (subjectId != null && subjectTypeId != null) {

				if ((multipleEvaluator != null && (ansScriptId == null || ansScriptId == 0))
						|| ((ansScriptId == null || ansScriptId == 0) && (multipleEvaluator == null || multipleEvaluator == 0))) {

					HQL_QUERY = "select srs.practicalEseEnteredMaxMark"
							+ " from ExamSubjectRuleSettingsBO srs"
							+ " where srs.courseId = :courseId and srs.schemeNo = :schemeNo"
							+ " and srs.subjectId = :subjectId  and srs.isActive = 1 " + academicString;

				} else if ((ansScriptId != null && (multipleEvaluator == null || multipleEvaluator == 0))
						|| (ansScriptId != null && multipleEvaluator != null)) {

					HQL_QUERY = "select ans.value"
							+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO ans"
							+ " join ans.examSubjectRuleSettingsBO s"
							+ " where s.courseId = :courseId and s.schemeNo = :schemeNo and s.subjectId = :subjectId"
							+ academicString
							+ " and s.isActive = 1"
							+ " and ans.multipleAnswerScriptId = :ansScriptId and ans.isTheoryPractical = 'p'";
				}
			}
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			//query.setParameter("examId", examId);
			if (ansScriptId != null && ansScriptId > 0) {
				query.setParameter("ansScriptId", ansScriptId);
			}
			if (subjectId != null && subjectId > 0) {
				query.setParameter("subjectId", subjectId);
			}

			l = query.list();

			if (l != null && l.size() > 0) {

				maxPracticalMarks = (BigDecimal) l.get(0);

			}
		}

		else if (examType != null && examType.equalsIgnoreCase("Internal")) {

			if (subjectId != null && subjectTypeId != null) {

				intType = getIntType(examId);

				if (intType != null && intType > 0) {
					HQL_QUERY = "select e.enteredMaxMark"
							+ " from ExamSubjectRuleSettingsSubInternalBO e"
							+ " join e.examSubjectRuleSettingsBO srs"
							+ " where srs.courseId = :courseId and srs.schemeNo = :schemeNo and srs.academicYear IN (select d.academicYear from ExamDefinitionBO d where d.id = :examId) and srs.isActive = 1"
							+ " and e.internalExamTypeId IN (:intType) and e.isTheoryPractical = 'p' and srs.subjectId= :subjectId";

					Query query = session.createQuery(HQL_QUERY);
					query.setParameter("courseId", courseId);
					query.setParameter("schemeNo", schemeNo);
					query.setParameter("examId", examId);
					query.setParameter("intType", intType);
					query.setParameter("subjectId", subjectId);
					maxPracticalMarks = getMarks(query.list());
				}
			}
		}

		return maxPracticalMarks;
	}catch (Exception e) {
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
	}

	private BigDecimal getMarks(List list) throws Exception {
		if (list != null && list.size() > 0 && list.get(0) != null) {
			return (BigDecimal) list.get(0);
		}
		return new BigDecimal(0.0);
	}

	private Integer getIntType(Integer examId) throws Exception {
		Session session = null;
		Integer intTypeid = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String HQL_QUERY = "";

		HQL_QUERY = "SELECT ed.internal_exam_type_id FROM EXAM_definition ed WHERE  ed.id = :examId";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		List list = query.list();

		if (list != null && list.size() > 0) {
			intTypeid = (Integer) list.get(0);

		}
		return intTypeid;
		}catch (Exception e) {;
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		}

	public List<Integer> getListInternalExam(Integer examId) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		List<Integer> l = null;

		String HQL_QUERY = "SELECT ed.id FROM EXAM_definition ed WHERE  ed.id in ("
				+ " select eint.internal_exam_name_id from EXAM_definition e"
				+ " join EXAM_exam_internal_exam_details eint on eint.exam_id = e.id"
				+ " where eint.exam_id = :examId)";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		l = (List<Integer>) query.list();

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

	// Get admn Id by rollNo
	public Integer getadmIdByRollNo(String rollNo) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.admApplnId" + " from StudentUtilBO s"
				+ " where s.rollNo = :rollNo";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("rollNo", rollNo);
		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// Get admn Id by regNo
	public Integer getadmIdByRegNo(String regNo) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Integer classSchemewiseId = null;
		String HQL_QUERY = "";

		HQL_QUERY = "select s.admApplnId" + " from StudentUtilBO s"
				+ " where s.registerNo = :regNo";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("regNo", regNo);
		List list = query.list();

		if (list != null && list.size() > 0) {
			classSchemewiseId = (Integer) list.get(0);

		}

		return classSchemewiseId;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// Get subject group list by appln Id
	public ArrayList<Integer> getSubGrpList(Integer admAppId) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		ArrayList<Integer> l = null;

		String HQL_QUERY = "select a.subjectGroupId"
				+ " from ApplicantSubjectGroupUtilBO a"
				+ " where a.admnApplnId = :admAppId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("admAppId", admAppId);
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

	// To get student's subjects
	public ArrayList<Integer> getSubjectIdListForStudent(
			ArrayList<Integer> subjGrpList) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		ArrayList<Integer> l = null;

		String HQL_QUERY = "select s.subjectUtilBO.id"
				+ " from SubjectGroupSubjectsUtilBO s"
				+ " where s.subjectGroupUtilBO.id in (:subjGrpList) and s.isActive = 1";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameterList("subjGrpList", subjGrpList);
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

	// Get student's current scheme
	public int getCurrentSchemeByRollNoRegNo(String rollNo, boolean isRoll) throws Exception {
		Session session  = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		int scheme = 0;

		String HQL_QUERY = "select st.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
				+ " from StudentUtilBO st ";
		if (isRoll) {
			HQL_QUERY = HQL_QUERY + "where st.rollNo=:rollNo";
		} else {
			HQL_QUERY = HQL_QUERY + "where st.registerNo=:rollNo";
		}

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("rollNo", rollNo);

		List list = query.list();

		if (list != null && list.size() > 0) {

			scheme = (Integer) list.get(0);
		}
		return scheme;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	// Get student's subject groups from history table
	public ArrayList<Integer> getSubGrpHistory(String rollNo, int schemeNo,
			boolean isRoll) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		ArrayList<Integer> subGrpList = new ArrayList<Integer>();

		String HQL_QUERY = "select distinct s.subjectUtilBO.id	from SubjectGroupSubjectsUtilBO s"
				+ " where s.subjectGroupUtilBO.id in ( "
				+ " select ss.subjectGroupId"
				+ " from ExamStudentSubGrpHistoryBO ss"
				+ " join ss.subjectGroupUtilBO"
				+ " where ss.schemeNo=:schemeNo and s.isActive = 1";

		if (isRoll) {
			HQL_QUERY = HQL_QUERY + " and ss.studentUtilBO.rollNo=:rollNo)";
		} else {
			HQL_QUERY = HQL_QUERY + " and ss.studentUtilBO.registerNo=:rollNo)";
		}

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("rollNo", rollNo);
		query.setParameter("schemeNo", schemeNo);

		List list = query.list();

		if (list != null && list.size() > 0) {

			subGrpList.add((Integer) list.get(0));
		}
		return subGrpList;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	public List<Object[]> validateCourseScheme(String rollNo,
			boolean useRollNo, int examId) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		String HQL_QUERY = "select st.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId,"
				+ " st.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
				+ " from StudentUtilBO st where";
		if (useRollNo) {
			HQL_QUERY = HQL_QUERY + " st.rollNo= :rollNo  ";
		} else {
			HQL_QUERY = HQL_QUERY + " st.registerNo= :rollNo  ";
		}
		HQL_QUERY = HQL_QUERY
				+ " and st.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear"
				+ " =(select ed.academicYear from ExamDefinitionBO ed where ed.id = :examId)";

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("rollNo", rollNo);
		query.setParameter("examId", examId);
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

	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception
	{
		List<Integer> studentList=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			/*	String query="select s.student.id from ExamStudentDetentionRejoinDetails s " +
							"where s.id=(select max(id) from ExamStudentDetentionRejoinDetails b " +
							"where b.student.id=s.student.id) and (s.detain=1 or s.discontinued=1)";
			*/
			String query="select s.student.id from ExamStudentDetentionRejoinDetails s " +
			"where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)";
			studentList=session.createQuery(query).list();
		}
		catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
		
		return studentList;
	}
	public List<Integer> getStudentsHallTicketBlocked(int studentId, int classId, int examId) throws Exception
	{
		List<Integer> studentList=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="select s.studentId from ExamBlockUnblockHallTicketBO s "+
						 "where s.classId="+ classId +" and s.studentId="+ studentId +" and "+
						 		"s.examId="+ examId +" and s.hallTktOrMarksCard='H' ";
			studentList=session.createQuery(query).list();
		}
		catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
		
		return studentList;
	}
	public String getDetainedDiscontinuedStudent(int studentId, int classId) throws Exception
	{
		String studentList="";
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			
			String query="select s.student.id from ExamStudentDetentionRejoinDetails s " +
			"inner join s.classSchemewise cs "+
			"where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null) " +
			"and and cs.classes.id="+ classId +" and s.student.id="+ studentId ;
			studentList=(String) session.createQuery(query).uniqueResult();
		}
		catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
		
		return studentList;
	}
	
	public List<ExamMarksEntryDetailsBO> getMarksEntryByExamAndClass(int examId, int classId) throws Exception {
		
		List<ExamMarksEntryDetailsBO> marksEntryList = new ArrayList<ExamMarksEntryDetailsBO>();
		Session session=null;
		
		try {
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamMarksEntryDetailsBO mkDetails where mkDetails.examMarksEntryBO.examDefinitionBO.id = :examId " +
																	   "and mkDetails.examMarksEntryBO.classes.id = :classId";
			Query query = session.createQuery(hqlQuery)
								 .setInteger("examId", examId)
								 .setInteger("classId", classId);
			marksEntryList = query.list();
			session.flush();
			return marksEntryList;
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(ex);
		}
	}
	
	public List<Integer> getStudentsHallTicketBlockedForPromotion(int studentId) throws Exception
	{
		List<Integer> studentList=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="select regApp.student.id from ExamRegularApplication regApp "+
						 "where regApp.student.id not in"+
						"(select blockStud.studentUtilBO.id from ExamBlockUnblockHallTicketBO blockStud "+
						 " where blockStud.hallTktOrMarksCard = 'H')"+
						 "and regApp.exam.examTypeUtilBO.id = 1"+
						 "and regApp.student.classSchemewise.classes.termNumber in"+
						 "(select cosScheme.schemeNo from ExamExamCourseSchemeDetailsBO cosScheme where cosScheme.examDefinitionBO.id = regApp.id)"+
						 "and regApp.student.id = "+studentId;
			studentList=session.createQuery(query).list();
		}
		catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
		
		return studentList;
	}

}
