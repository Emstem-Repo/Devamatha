package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryCorrectionDetailsBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentMarksCorrectionBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamStudentMarksCorrectionImpl extends ExamGenImpl {
	public String markType1 = null;

	public List selectMarksDetail_AllSubForOneStudent(int studentId,
			ArrayList<Integer> listSubjectIds, int examId, Integer ansScriptId,
			Integer evaluatorId, int secured) throws DataNotFoundException {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String SQL1 = null, SQL2 = null, SQL3 = null, SQL4 = null;

		if (evaluatorId == null) {
			SQL1 = " AND me.evaluator_type_id IS NULL ";
			SQL3 = " AND me1.evaluator_type_id IS NULL ";

		} else {
			SQL1 = " AND me.evaluator_type_id =:evaluatorId";
			SQL3 = " AND me1.evaluator_type_id =:evaluatorId";
		}

		if (ansScriptId == null) {
			SQL2 = " AND me.answer_script_type IS NULL ";
			SQL4 = " AND me1.answer_script_type IS NULL ";

		} else {
			SQL2 = " AND me.answer_script_type = :ansScriptId";
			SQL4 = " AND me1.answer_script_type = :ansScriptId";

		}

		ArrayList<Object[]> l = null;
		
		/*String HQL_QUERY = " SELECT me.id AS meid"
		+ "  FROM EXAM_marks_entry me LEFT JOIN EXAM_marks_entry_details med"
		+ "  ON me.id = med.marks_entry_id INNER JOIN"
		+ "  (SELECT me2.subject_id, MAX(me2.id) AS md, count(me2.id) AS cou"
		+ "  FROM EXAM_marks_entry_details me2 JOIN"
		+ "  EXAM_marks_entry me1"
		+ "  ON me1.id = me2.marks_entry_id"
		+ "  WHERE me1.exam_id = :examId"
		+ "  AND me1.is_secured = :secured"
		+ "  AND me1.student_id = :studentId"
		+ SQL3
		+ SQL4

		+ "  GROUP BY subject_id) s1"
		+ "  ON s1.subject_id = med.subject_id AND med.id = s1.md"
		+ "  LEFT JOIN  subject s"
		+ "  ON med.subject_id = s.id"
		+ "  WHERE s.id IN (:subjectId) AND me.exam_id =  :examId"
		+ "  AND me.is_secured = :secured AND me.student_id = :studentId"
		+ SQL1 + SQL2;*/
		
		
		String HQL_QUERY = " SELECT me.id AS meid,"
				+ "  med.id AS medid,"
				+ "  me.marks_card_no,"
				+ "  me.marks_card_date,"
				+ "  s.id AS sid,"
				+ "  s.name,"
				+ "  med.theory_marks,"
				+ "  med.practical_marks,"
				+ "  med.is_retest,"
				+ "  med.is_mistake,"
				+ "  med.comments,"
				+ "  s.is_theory_practical,"
				+ "  s1.cou, med.previous_evaluator_theory_marks, med.previous_evaluator_practical_marks," 
				+ "  med.created_by, med.created_date, med.modified_by, med.last_modified_date, s.code " 
				+ "  FROM EXAM_marks_entry me LEFT JOIN EXAM_marks_entry_details med"
				+ "  ON me.id = med.marks_entry_id INNER JOIN"
				+ "  (SELECT me2.subject_id, MAX(me2.id) AS md, count(me2.id) AS cou"
				+ "  FROM EXAM_marks_entry_details me2 JOIN"
				+ "  EXAM_marks_entry me1"
				+ "  ON me1.id = me2.marks_entry_id"
				+ "  WHERE me1.exam_id = :examId"
				+ "  AND me1.is_secured = :secured"
				+ "  AND me1.student_id = :studentId"
				+ SQL3
				+ SQL4

				+ "  GROUP BY subject_id) s1"
				+ "  ON s1.subject_id = med.subject_id AND med.id = s1.md"
				+ "  LEFT JOIN  subject s"
				+ "  ON med.subject_id = s.id"
				+ "  WHERE s.id IN (:subjectId) AND me.exam_id =  :examId"
				+ "  AND me.is_secured = :secured AND me.student_id = :studentId"
				+ SQL1 + SQL2 + " GROUP BY sid";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("studentId", studentId);
		if (evaluatorId != null) {
			query.setParameter("evaluatorId", evaluatorId);
		}
		if (ansScriptId != null) {
			query.setParameter("ansScriptId", ansScriptId);
		}
		query.setParameter("secured", secured);
		query.setParameterList("subjectId", listSubjectIds);
		l = (ArrayList<Object[]>) query.list();
		if (l != null && l.size() > 0) {

			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	// To get the id of first table (Exam_students_marks_correction)
	public int insert_returnId(ExamStudentMarksCorrectionBO objBO) {

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

	public void insert_MarksCorrection(ExamMarksEntryBO marksMaster,
			ArrayList<ExamMarksEntryDetailsBO> marksDetailsList, int studentId, Integer evaluatorId, int examId, boolean secured, Integer answerScriptTypeId) {
		try {
			int masterID = marksMaster.getId();
			boolean newMaster = false;

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			String HQL_QUERY = "update ExamMarksEntryBO e set e.marksCardNo = :marksCardNo"
					+ " where e.id = :masterId";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("marksCardNo", marksMaster.getMarksCardNo());
			query.setParameter("masterId", masterID);
			query.executeUpdate();
			tx.commit();
			session.close();

			insert_MarksCorrectionDetails(marksDetailsList, newMaster, masterID, studentId, evaluatorId, examId, secured, answerScriptTypeId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert_MarksCorrectionDetails(
			ArrayList<ExamMarksEntryDetailsBO> marksDetailsList,
			boolean newMaster, Integer masterID, int studentId, Integer evaluatorId, int examId, boolean secured, Integer answerScriptTypeId) throws ApplicationException {
		ArrayList<ExamMarksEntryDetailsBO> updatedMarksDetailsList = new ArrayList<ExamMarksEntryDetailsBO>();
		ArrayList<ExamMarksEntryDetailsBO> updatedMarksDetailsTempList = new ArrayList<ExamMarksEntryDetailsBO>();
		ExamMarksEntryDetailsBO detTempBO;
	    HashMap<Integer, Integer> idMap = getMarkEntryIdForSubject(studentId, evaluatorId, examId, secured, answerScriptTypeId);
		for (ExamMarksEntryDetailsBO detBO : marksDetailsList) {
			if (chkIfCorrectionDone(detBO.getTheoryMarks(), detBO
					.getPracticalMarks(), /*masterID*/idMap.get(detBO.getSubjectId()), detBO.getSubjectId())) {
				masterID = idMap.get(detBO.getSubjectId());
				detTempBO = new ExamMarksEntryDetailsBO(); 
				
				detTempBO.setComments(detBO.getComments());
				detTempBO.setCreatedBy(detBO.getCreatedBy());
				detTempBO.setCreatedDate(detBO.getCreatedDate());
				detTempBO.setExamMarksEntryBO(detBO.getExamMarksEntryBO());
				detTempBO.setId(detBO.getId());
				detTempBO.setIsActive(detBO.getIsActive());
				detTempBO.setIsMistake(detBO.getIsMistake());
				detTempBO.setIsRetest(detBO.getIsRetest());
				detTempBO.setLastModifiedDate(detBO.getLastModifiedDate());
				detTempBO.setMarksEntryId(detBO.getMarksEntryId());
				detTempBO.setModifiedBy(detBO.getModifiedBy());
				detTempBO.setName(detBO.getName());
				detTempBO.setPracticalMarks(detBO.getPracticalMarks());
				detTempBO.setPreviousEvaluatorPracticalMarks(detBO.getPreviousEvaluatorPracticalMarks());
				detTempBO.setPreviousEvaluatorTheoryMarks(detBO.getPreviousEvaluatorTheoryMarks());
				detTempBO.setSubjectId(detBO.getSubjectId());
				detTempBO.setTheoryMarks(detBO.getTheoryMarks());
				
				detTempBO.setMarksEntryId(masterID);
				detBO.setMarksEntryId(masterID);
				updatedMarksDetailsTempList.add(detTempBO);
				detBO.setComments(null);
				updatedMarksDetailsList.add(detBO);
			}

		}
		if(updatedMarksDetailsTempList!= null && updatedMarksDetailsTempList.size() > 0 ){
			if(updateentryCorrectionDetailsBO(updatedMarksDetailsTempList)){
				deleteExamMarkEntryDetailsBO(updatedMarksDetailsTempList);
			}
		}
		if(updatedMarksDetailsList!= null && updatedMarksDetailsList.size() > 0){
			insert_List(updatedMarksDetailsList);
		}
	}

	public boolean chkIfCorrectionDone(String theoryMarks,
			String practicalMarks, Integer masterID, int subjectId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		String HQL_QUERY = " SELECT med.theory_marks, med.practical_marks"
				+ "   FROM       EXAM_marks_entry me"
				+ "           LEFT JOIN"
				+ "              EXAM_marks_entry_details med"
				+ "           ON me.id = med.marks_entry_id"
				+ "        INNER JOIN"
				+ "           (SELECT me1.student_id, MAX(me2.id) AS md, count(me2.id) AS cou"
				+ "              FROM    EXAM_marks_entry_details me2"
				+ "                   JOIN"
				+ "                      EXAM_marks_entry me1"
				+ "                   ON me1.id = me2.marks_entry_id"
				+ "             WHERE me1.id = :marksEntryId AND me2.subject_id = :subjectId"
				+ "            GROUP BY me1.student_id) s1"
				+ "        ON s1.student_id = me.student_id AND med.id = s1.md"
				+ "  WHERE me.id = :marksEntryId AND med.subject_id = :subjectId";

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("marksEntryId", masterID);

		Iterator<Object[]> itr = query.list().iterator();
		
		String oldTheoryMark = "";
		String oldPracticalMark = "";
		
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if(theoryMarks == null){
				theoryMarks = "";
			}
			if(practicalMarks == null){
				practicalMarks = "";
			}
			if(row[0] == null){
				oldTheoryMark = "";
			}
			else{
				oldTheoryMark = row[0].toString();
			}
			if(row[1] == null){
				oldPracticalMark = "";
			}
			else{
				oldPracticalMark = row[1].toString();
			}
			if (!(theoryMarks.equals(oldTheoryMark))) {
				return true;
			} else if (!(practicalMarks.equals(oldPracticalMark))) {
				return true;
			}
			else if (oldTheoryMark.isEmpty() && theoryMarks!= null && !theoryMarks.trim().isEmpty()){
				return true;
			}
			else if (oldPracticalMark.isEmpty() && practicalMarks!= null && !practicalMarks.trim().isEmpty()){
				return true;
			}

		}

		return false;
	}

	public void delete_MarksCorrectionDetails(int id) {

		String HQL_QUERY = "delete from ExamStudentMarksCorrectionDetailsBO e"
				+ " where e.studentsMarksCorrectionId = :id";

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

	public void update_details(Integer studentsMarksCorrectionId,
			String theoryMarks, String practicalMarks, String comments) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "update ExamStudentMarksCorrectionDetailsBO e set e.theoryMarks = :theoryMarks,"
				+ " e.practicalMarks = :practicalMarks, e.comments = :comments"
				+ " where e.id = :studentsMarksCorrectionId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentsMarksCorrectionId",
				studentsMarksCorrectionId);
		query.setParameter("theoryMarks", theoryMarks);
		query.setParameter("practicalMarks", practicalMarks);
		query.setParameter("comments", comments);
		query.executeUpdate();
		tx.commit();
		session.close();

	}

	public List select_allStudentsForOneSubject(int courseId, int subjectId,
			int examId, Integer ansScriptId, Integer evaluatorId, int secured,
			String markType) {

		String SQL1 = null, SQL2 = null, SQL3 = null, SQL4 = null, SQL5 = null, SQL6 = null;

		if (evaluatorId == null) {
			SQL1 = " AND me.evaluator_type_id IS NULL ";
			SQL3 = " AND me1.evaluator_type_id IS NULL ";

		} else {
			SQL1 = " AND me.evaluator_type_id =:evaluatorId";
			SQL3 = " AND me1.evaluator_type_id =:evaluatorId";
		}

		if (ansScriptId == null) {
			SQL2 = " AND me.answer_script_type IS NULL ";
			SQL4 = " AND me1.answer_script_type IS NULL ";

		} else {
			SQL2 = " AND me.answer_script_type = :ansScriptId";
			SQL4 = " AND me1.answer_script_type = :ansScriptId";

		}
		if (markType1 == null) {
			SQL5 = " AND me.mark_type IS NULL";
			SQL6 = " AND me1.mark_type IS NULL";
		} else {
			SQL5 = " AND me.mark_type = :markType";
			SQL6 = " AND me1.mark_type = :markType";
		}

		
		String SQL_QUERY = "SELECT st.roll_no,"
			+ " st.register_no,"
			+ " st.id AS stuid,"
			+ " pd.first_name,"
			+ " pd.last_name,"
			+ " me.id AS meid,"
			+ " med.id AS medid,"
			+ " med.theory_marks,"
			+ " med.practical_marks,"
			+ " med.is_mistake,"
			+ " med.is_retest,"
			+ " med.comments,"
			+ " s1.cou, med.previous_evaluator_theory_marks,"
				+ " 	   med.previous_evaluator_practical_marks, "
				+ " 	   med.created_by, "
				+ "		   med.created_date, "
				+"		   med.modified_by, "
				+" 		   med.last_modified_date, med.subject_id"
			+ " FROM EXAM_marks_entry me"
			+ " LEFT JOIN"
			+ " EXAM_marks_entry_details med"
			+ " ON me.id = med.marks_entry_id"
			+ " INNER JOIN"
			+ " (SELECT me1.student_id,"
			+ " MAX(me2.id) AS md,"
			+ " count(me2.id) AS cou"
			+ " FROM  EXAM_marks_entry_details me2"
			+ " JOIN"
			+ " EXAM_marks_entry me1"
			+ " ON me1.id = me2.marks_entry_id"
			+ " WHERE me1.exam_id = :examId"
			//+ " AND me1.is_secured = :secured"
			+ " AND me2.subject_id = :subjectId"
			+ SQL3
			+ SQL4
			+ SQL6
			+ " GROUP BY me1.student_id) s1"
			+ " ON s1.student_id = me.student_id AND med.id = s1.md"
			+ " LEFT JOIN student st"
			+ " ON me.student_id = st.id "
			+ " JOIN class_schemewise csc on st.class_schemewise_id = csc.id"
			+ " JOIN curriculum_scheme_duration cscd on "
			+ " csc.curriculum_scheme_duration_id = cscd.id"
			+ " JOIN curriculum_scheme cs on cscd.curriculum_scheme_id = cs.id"
			+ " JOIN adm_appln ON st.adm_appln_id = adm_appln.id"
			+ " JOIN personal_data pd"
			+ " ON adm_appln.personal_data_id = pd.id"
			+ " WHERE med.subject_id = :subjectId"
			+ " AND me.exam_id = :examId"
		//	+ " AND me.is_secured = :secured"
			+
					" AND cs.course_id = :courseId"
			+ SQL1 + SQL2 + SQL5 + " GROUP BY stuid;";
		

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
	//	query.setParameter("secured", secured);
		query.setParameter("courseId", courseId);
		if (ansScriptId != null) {
			query.setParameter("ansScriptId", ansScriptId);
		}
		if (evaluatorId != null) {
			query.setParameter("evaluatorId", evaluatorId);
		}
		if (markType1 != null) {
			query.setParameter("markType", markType);
		}
		return query.list();
	}

	public int insert_returnId(ExamMarksEntryBO objBO) {
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

	public String getCurrentExamName(String examTypeName) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String id = null;

		String HQL_QUERY = null;
		if (examTypeName.contains("Reg")) {
			HQL_QUERY = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.name like 'Reg%' and d.delIsActive = 1 and d.isActive=1";
		} else if (examTypeName.contains("Suppl")) {
			HQL_QUERY = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.name like 'Suppl%' and d.delIsActive = 1 and d.isActive=1";
		} else if (examTypeName.contains("Int")) {
			HQL_QUERY = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.name like 'Int%' and d.delIsActive = 1 and d.isActive=1";
		}

		Query query = session.createQuery(HQL_QUERY);

		List list = query.list();

		if (list != null && list.size() > 0) {

			id = list.get(0).toString();
		}
		return id;

	}

	public List<Integer> getEvaluator_list(Integer courseId, Integer schemeNo,
			Integer subjectId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List l = null;
		String HQL_QUERY = "";
		if (subjectId != null) {

			HQL_QUERY = "select esre.evaluatorId"
					+ " from ExamSubjecRuleSettingsMultipleEvaluatorBO esre"
					+ " join esre.examSubjectRuleSettingsBO es"
					+ " where es.courseId = :courseId and es.schemeNo = :schemeNo and es.subjectId = :subjectId";
		} else {
			HQL_QUERY = "select esre.evaluatorId"
					+ " from ExamSubjecRuleSettingsMultipleEvaluatorBO esre"
					+ " join esre.examSubjectRuleSettingsBO es"
					+ " where es.courseId = :courseId and es.schemeNo = :schemeNo";
		}
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		if (subjectId != null) {
			query.setParameter("subjectId", subjectId);
		}

		l = query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Integer>();
		}

	}

	public Integer getanswerScriptId_fromRuleSettings(Integer courseId,
			Integer schemeNo, Integer subjectId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer id = null;

		String HQL_QUERY = null;

		if (subjectId != null) {
			HQL_QUERY = "select es.id"
					+ " from ExamSubjectRuleSettingsBO es"
					+ " where es.courseId = :courseId and es.schemeNo = :schemeNo and es.subjectId = :subjectId and "
					+ " (es.theoryEseIsMultipleAnswerScript = 1 or es.practicalEseIsMultipleAnswerScript = 1)";
		} else {
			HQL_QUERY = "select es.id"
					+ " from ExamSubjectRuleSettingsBO es"
					+ " where es.courseId = :courseId and es.schemeNo = :schemeNo and "
					+ " (es.theoryEseIsMultipleAnswerScript = 1 or es.practicalEseIsMultipleAnswerScript = 1)";
		}

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		if (subjectId != null) {
			query.setParameter("subjectId", subjectId);
		}
		List list = query.list();

		if (list != null && list.size() > 0) {

			id = (Integer) list.get(0);
		}
		return id;
	}

	// To get student's old marks
	public ArrayList<Object[]> getPrevMarks(int examId, int studentId,
			Integer evaluatorTypeId, Integer answerScriptTypeId, int subjectId,
			int secured, String markType) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Object[]> l = null;

		String SQL1 = null, SQL2 = null, SQL3 = null, SQL4 = null, SQL5 = null, SQL6 = null;

		if (evaluatorTypeId == null || evaluatorTypeId == 0) {
			SQL1 = " AND me.evaluator_type_id IS NULL ";
			SQL3 = " AND me1.evaluator_type_id IS NULL ";

		} else {
			SQL1 = " AND me.evaluator_type_id =:evaluatorId";
			SQL3 = " AND me1.evaluator_type_id =:evaluatorId";
		}

		if (answerScriptTypeId == null || answerScriptTypeId == 0) {
			SQL2 = " AND me.answer_script_type IS NULL ";
			SQL4 = " AND me1.answer_script_type IS NULL ";

		} else {
			SQL2 = " AND me.answer_script_type = :ansScriptId";
			SQL4 = " AND me1.answer_script_type = :ansScriptId";

		}
		if (markType1 == null) {
			SQL5 = " AND me.mark_type IS NULL";
			SQL6 = " AND me1.mark_type IS NULL";
		} else {
			SQL5 = " AND me.mark_type = :markType";
			SQL6 = " AND me1.mark_type = :markType";
		}
		String HQL_QUERY = "";

		HQL_QUERY = " SELECT  s.name,"
				+ " med.theory_marks,"
				+ " med.practical_marks,"
				+ " med.comments, med.last_modified_date"
				+ " FROM  EXAM_marks_entry me LEFT JOIN"
				+ " EXAM_marks_entry_details med"
				+ "  ON me.id = med.marks_entry_id INNER JOIN"
				+ "  (SELECT me2.subject_id, MAX(me2.id) AS md, count(me2.id) AS cou"
				+ "  FROM EXAM_marks_entry_details me2 JOIN"
				+ "  EXAM_marks_entry me1" + "  ON me1.id = me2.marks_entry_id"
				+ "  WHERE me1.exam_id = :examId"
				+ "  AND me1.is_secured = :secured"
				+ " AND me1.student_id = :studentId" + SQL3 + SQL4 + SQL6
				+ "  GROUP BY subject_id) s1"
				+ " ON s1.subject_id = med.subject_id" + " and med.id != s1.md"
				+ " LEFT JOIN subject s" + " ON med.subject_id = s.id"
				+ " WHERE s.id = :subjectId AND me.exam_id = :examId"
				+ " AND me.is_secured = :secured AND me.student_id =:studentId"
				+ SQL1 + SQL2 + SQL5;

		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("studentId", studentId);
		if (evaluatorTypeId != null && evaluatorTypeId != 0) {
			query.setParameter("evaluatorId", evaluatorTypeId);
		}
		if (answerScriptTypeId != null && answerScriptTypeId != 0) {
			query.setParameter("ansScriptId", answerScriptTypeId);
		}
		if (markType1 != null) {
			query.setParameter("markType", markType);
		}
		query.setParameter("secured", secured);
		query.setParameter("subjectId", subjectId);

		l = (ArrayList<Object[]>) query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}

	}

	public List select_allStudentsForOneSubjectForInternalOverall(int courseId,
			int subjectId, int examId) {

		String SQL_QUERY = "SELECT st.roll_no, st.register_no, st.id AS stuid, pd.first_name, pd.last_name,"
				+ " imd.id as id, "
				+ " theory_total_mark AS tmm,"
				+ " practical_total_mark AS pmm,"
				+ " imd.comments, s1.cou,  imd.subject_id"
				+ " FROM  EXAM_student_overall_internal_mark_details imd"
				+ " INNER JOIN (SELECT imd1.student_id, MAX(imd1.id) AS md, count(imd1.id) AS cou"
				+ " FROM EXAM_student_overall_internal_mark_details imd1"
				+ " WHERE imd1.exam_id = :examId AND imd1.subject_id = :subjectId  GROUP BY imd1.student_id) s1"
				+ " ON s1.student_id = imd.student_id AND imd.id = s1.md"
				+ " LEFT JOIN  student st ON imd.student_id = st.id"
				+ " JOIN class_schemewise csc on st.class_schemewise_id = csc.id"
				+ " JOIN curriculum_scheme_duration cscd on "
				+ " csc.curriculum_scheme_duration_id = cscd.id"
				+ " JOIN curriculum_scheme cs on cscd.curriculum_scheme_id = cs.id"
				+ " JOIN adm_appln ON st.adm_appln_id = adm_appln.id"
				+ " JOIN personal_data pd ON adm_appln.personal_data_id = pd.id"
				+ " WHERE imd.subject_id = :subjectId AND imd.exam_id = :examId AND cs.course_id = :courseId GROUP BY stuid;";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);
		// query.setParameter("thr", dtv.getDisplay());
		// query.setParameter("pra", dtv.getValue());

		return query.list();
	}

	public List select_allStudentsForOneSubjectForRegularOverAll(int courseId,
			int subjectId, int examId) {

		String SQL_QUERY = "SELECT st.roll_no, st.register_no, st.id AS stuid, pd.first_name, pd.last_name, fmd.id AS id,"
				+ " student_theory_marks as tmm, student_practical_marks as pmm, fmd.comments, s1.cou, fmd.subject_id"
				+ " FROM EXAM_student_final_mark_details fmd INNER JOIN (SELECT fmd1.student_id,"
				+ " MAX(fmd1.id) AS md, count(fmd1.id) AS cou FROM EXAM_student_final_mark_details fmd1"
				+ " WHERE fmd1.exam_id = :examId AND fmd1.subject_id = :subjectId GROUP BY fmd1.student_id) s1"
				+ " ON s1.student_id = fmd.student_id AND fmd.id = s1.md LEFT JOIN student st"
				+ " ON fmd.student_id = st.id "
				+ " JOIN class_schemewise csc on st.class_schemewise_id = csc.id"
				+ " JOIN curriculum_scheme_duration cscd on "
				+ " csc.curriculum_scheme_duration_id = cscd.id"
				+ " JOIN curriculum_scheme cs on cscd.curriculum_scheme_id = cs.id"
				+ " JOIN adm_appln ON st.adm_appln_id = adm_appln.id"
				+ " JOIN personal_data pd ON adm_appln.personal_data_id = pd.id"
				+ " WHERE fmd.subject_id = :subjectId AND fmd.exam_id = :examId  AND cs.course_id = :courseId GROUP BY stuid;";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("courseId", courseId);
		return query.list();
	}

	public ArrayList<Object[]> getPrevMarksInternal(int courseId,
			int subjectId, int examId, int studentId) {
		ArrayList<Object[]> l = null;

		String SQL_QUERY = "SELECT s.name,"
				+ " imd.theory_total_mark AS tmm,"
				+ " imd.practical_total_mark AS pmm,"
				+ "  imd.comments, imd.last_modified_date "
				+ "  FROM  EXAM_student_overall_internal_mark_details imd"
				+ "  INNER JOIN (SELECT imd1.student_id, MAX(imd1.id) AS md, count(imd1.id) AS cou"
				+ "  FROM EXAM_student_overall_internal_mark_details imd1"
				+ "  WHERE imd1.exam_id = :examId AND imd1.subject_id = :subjectId  GROUP BY imd1.student_id) s1"
				+ "  ON s1.student_id = imd.student_id AND imd.id != s1.md"
				+ "  LEFT JOIN  student st ON imd.student_id = st.id"
				+ "	left join subject s on imd.subject_id = s.id"
				+ "  WHERE imd.subject_id = :subjectId AND imd.exam_id = :examId AND imd.student_id=:studentId ;";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		query.setParameter("examId", examId);
		l = (ArrayList<Object[]>) query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	public ArrayList<Object[]> getPrevMarksRegular(int courseId, int subjectId,
			int examId, int studentId) {
		ArrayList<Object[]> l = null;

		String SQL_QUERY = " SELECT s.name, "
				+ " student_theory_marks as tmm, student_practical_marks as pmm, fmd.comments,"
				+ " fmd.last_modified_date"
				+ " FROM EXAM_student_final_mark_details fmd INNER JOIN (SELECT fmd1.student_id,"
				+ " MAX(fmd1.id) AS md, count(fmd1.id) AS cou FROM EXAM_student_final_mark_details fmd1"
				+ " WHERE fmd1.exam_id = :examId AND fmd1.subject_id = :subjectId GROUP BY fmd1.student_id) s1"
				+ " ON s1.student_id = fmd.student_id AND fmd.id != s1.md LEFT JOIN student st"
				+ " ON fmd.student_id = st.id LEFT JOIN subject s on fmd.subject_id = s.id"
				+ " WHERE fmd.subject_id = :subjectId AND fmd.exam_id = :examId and fmd.student_id=:studentId ;";
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		query.setParameter("examId", examId);
		l = (ArrayList<Object[]>) query.list();
		if (l != null && l.size() > 0) {
			return l;
		} else {
			return new ArrayList<Object[]>();
		}
	}

	public List selectMarksDetail_AllSubForOneStudentInternalOverall(
			int studentId, int subjectId, int examId) {

		String SQL_QUERY = "SELECT imd.id as id, s.id AS sid,  s.name,"
				+ " theory_total_sub_internal_mark AS tmm,"
				+ " practical_total_sub_internal_mark AS pmm,"
				+ " imd.comments,s.is_theory_practical, s1.cou, s.code"
				+ " FROM  EXAM_student_overall_internal_mark_details imd"
				+ " INNER JOIN (SELECT imd1.student_id, MAX(imd1.id) AS md, count(imd1.id) AS cou"
				+ " FROM EXAM_student_overall_internal_mark_details imd1"
				+ " WHERE imd1.exam_id = :examId AND imd1.subject_id = :subjectId  GROUP BY imd1.student_id) s1"
				+ " ON s1.student_id = imd.student_id AND imd.id = s1.md"
				+ " LEFT JOIN  student st ON imd.student_id = st.id"
				+ " LEFT JOIN  subject s ON imd.subject_id = s.id"
				+ " WHERE imd.subject_id = :subjectId AND imd.student_id=:studentId AND imd.exam_id = :examId GROUP BY sid;";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		query.setParameter("examId", examId);

		return query.list();
	}

	public void chkNInsertInternalOverall(String subInttheoryMarks,
			String subIntpracticalMarks, Integer masterID, int subjectId,
			String comments) {
		ExamStudentOverallInternalMarkDetailsBO bo;
		ExamMarksEntryCorrectionDetailsBO oldBo;
		int examId = 0, studentId = 0, classId = 0, subjectId1 = 0;
		String theoryTotalSubInternalMarks = null, theoryTotalAttendenceMarks = null, theoryTotalAssignmentMarks = null;
		String practicalTotalSubInternalMarks = null, practicalTotalAttendenceMarks = null, practicalTotalAssignmentMarks = null, passOrFail = null;
		String oldTheoryMarks = null, oldPracticalMarks = null;
		
		boolean insertFlag = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		String HQL_QUERY = " SELECT * FROM  EXAM_student_overall_internal_mark_details imd"
				+ "  INNER JOIN (SELECT imd1.student_id, MAX(imd1.id) AS md, count(imd1.id) AS cou"
				+ "  FROM EXAM_student_overall_internal_mark_details imd1"
				+ "  WHERE imd1.id = :id AND imd1.subject_id = :subjectId  GROUP BY imd1.student_id) s1"
				+ " ON s1.student_id = imd.student_id AND imd.id = s1.md"
				+ " WHERE imd.subject_id = :subjectId AND imd.id = :id ;";
		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("id", masterID);

		Iterator<Object[]> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[5] != null && !(subInttheoryMarks.equals(row[5].toString()))) {
				insertFlag = true;
			} else if (row[8] != null
					&& !(subIntpracticalMarks.equals(row[8].toString()))) {
				insertFlag = true;
			}

			if (insertFlag) {
				if (row[1] != null)
					examId = (Integer) row[1];
				if (row[2] != null)
					studentId = (Integer) row[2];
				if (row[3] != null)
					classId = (Integer) row[3];
				if (row[4] != null)
					subjectId1 = (Integer) row[4];
				if (row[5] != null)
					theoryTotalSubInternalMarks = (String) row[5];
				if (row[6] != null)
					theoryTotalAttendenceMarks = (String) row[6];
				if (row[7] != null)
					theoryTotalAssignmentMarks = (String) row[7];

				if (row[8] != null)
					practicalTotalSubInternalMarks = (String) row[8];
				if (row[9] != null)
					practicalTotalAttendenceMarks = (String) row[9];
				if (row[10] != null)
					practicalTotalAssignmentMarks = (String) row[10];

				if (row[11] != null)
					passOrFail = (String) row[11];
				
				if (row[14] != null)
					oldTheoryMarks = (String) row[14];
				if (row[15] != null)
					oldPracticalMarks = (String) row[15];


			}

		}
		if (insertFlag) {
			//ExamStudentOverallInternalMarkDetailsBO examStudentOverallInternalMarkDetailsBO = new ExamStudentOverallInternalMarkDetailsBO();
			//examStudentOverallInternalMarkDetailsBO.setId(masterID);
			
			oldBo = new ExamMarksEntryCorrectionDetailsBO(null,
					subjectId, theoryTotalSubInternalMarks, practicalTotalSubInternalMarks,
					null,
					null, 0,
					0, null,
					null, comments, masterID, null);
			insert(oldBo);
			bo = new ExamStudentOverallInternalMarkDetailsBO(examId, studentId,
					classId, subjectId1, subInttheoryMarks,
					theoryTotalAttendenceMarks, theoryTotalAssignmentMarks,
					oldTheoryMarks, subIntpracticalMarks,
					practicalTotalAttendenceMarks,
					practicalTotalAssignmentMarks, oldPracticalMarks, passOrFail,
					null, masterID);
			
			//insert(bo);
			update(bo);
		}

	}

	public void chkNInsertRegularOverall(String theoryMarks,
			String practicalMarks, Integer masterID, int subjectId,
			String comments) {
		ExamStudentFinalMarkDetailsBO bo;
		ExamMarksEntryCorrectionDetailsBO oldBO;

		int examId = 0, studentId = 0, classId = 0, subjectId1 = 0, id =0;
		String subjectTheoryMark = null, subjectPracticalMark = null, passOrFail = null;
		boolean insertFlag = false;
		String studentPracticalMark = null, studentTheoryMark = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		String HQL_QUERY = " SELECT * FROM  EXAM_student_final_mark_details imd"
				+ "  INNER JOIN (SELECT imd1.student_id, MAX(imd1.id) AS md, count(imd1.id) AS cou"
				+ "  FROM EXAM_student_final_mark_details imd1"
				+ "  WHERE imd1.id = :id GROUP BY imd1.student_id) s1"
				+ " ON s1.student_id = imd.student_id AND imd.id = s1.md"
				+ " WHERE imd.subject_id = :subjectId AND imd.id = :id ;";
		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("id", masterID);

		Iterator<Object[]> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[7] != null && !(theoryMarks.equals(row[7].toString()))) {
				insertFlag = true;
			} else if (row[8] != null
					&& !(practicalMarks.equals(row[8].toString()))) {
				insertFlag = true;
			}
			if (insertFlag) {
				if (row[0] != null)
					id = (Integer) row[0];
				if (row[1] != null)
					examId = (Integer) row[1];
				if (row[2] != null)
					studentId = (Integer) row[2];
				if (row[3] != null)
					classId = (Integer) row[3];
				if (row[4] != null)
					subjectId1 = (Integer) row[4];
				if (row[5] != null)
					subjectTheoryMark = (String) row[5];
				if (row[6] != null)
					subjectPracticalMark = (String) row[6];

				if (row[9] != null)
					passOrFail = (String) row[9];
				if (row[14] != null)
					comments = (String) row[14];

				if (row[7] != null)
					studentTheoryMark = (String) row[7];
				if (row[8] != null)
					studentPracticalMark = (String) row[8];
			}

		}
		if (insertFlag) {
			//ExamStudentFinalMarkDetailsBO examStudentFinalMarkDetailsBO = new ExamStudentFinalMarkDetailsBO();
			//examStudentFinalMarkDetailsBO.setId(id);
			oldBO = new ExamMarksEntryCorrectionDetailsBO(null,
					subjectId1, studentTheoryMark, studentPracticalMark,
					null,
					null, null,
					null, null,
					null, comments,
					null,
					id);
			insert(oldBO);
			bo = new ExamStudentFinalMarkDetailsBO(examId, studentId, classId,
					subjectId1, subjectTheoryMark, subjectPracticalMark,
					theoryMarks, practicalMarks, passOrFail, null, id);
			update(bo);
		}

	}

	public List selectMarksDetail_AllSubForOneStudentRegularOverall(
			int studentId, ArrayList<Integer> subjectId, int examId) {

		String SQL_QUERY = "SELECT fmd.id AS id, s.id AS sid,  s.name,"
				+ " student_theory_marks as tmm, student_practical_marks as pmm, fmd.comments,s.is_theory_practical, s1.cou, s.code"
				+ " FROM EXAM_student_final_mark_details fmd INNER JOIN (SELECT fmd1.subject_id,"
				+ " MAX(fmd1.id) AS md, count(fmd1.id) AS cou FROM EXAM_student_final_mark_details fmd1"
				+ " WHERE fmd1.exam_id = :examId AND fmd1.subject_id in (:subjectId) AND fmd1.student_id =:studentId GROUP BY fmd1.subject_id) s1"
				+ " ON s1.subject_id = fmd.subject_id AND fmd.id = s1.md LEFT JOIN student st"
				+ " ON fmd.student_id = st.id "
				+ " LEFT JOIN  subject s ON fmd.subject_id = s.id"
				+ " WHERE fmd.subject_id in (:subjectId) AND fmd.student_id =:studentId AND fmd.exam_id = :examId GROUP BY sid;";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameterList("subjectId", subjectId);
		query.setParameter("examId", examId);
		query.setParameter("studentId", studentId);
		return query.list();
	}

	public boolean chkIfCorrectionDoneRegularOverall(String theoryMarks,
			String practicalMarks, Integer id, int subjectId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		String HQL_QUERY = " SELECT student_theory_marks AS tmm,"
				+ " student_practical_marks AS pmm,"
				+ " FROM  EXAM_student_final_mark_details imd"
				+ " INNER JOIN (SELECT imd1.student_id, MAX(imd1.id) AS md"
				+ " FROM EXAM_student_final_mark_details imd1"
				+ " WHERE imd1.id = id AND imd1.subject_id = :subjectId  GROUP BY imd1.student_id) s1"
				+ " ON s1.student_id = imd.student_id AND imd.id = s1.md"
				+ " WHERE imd.subject_id = :subjectId AND imd.id = :id ;";
		Query query = session.createSQLQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("id", id);

		Iterator<Object[]> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			if (row[0] != null && !(theoryMarks.equals(row[0].toString()))) {
				return true;
			} else if (row[1] != null
					&& !(practicalMarks.equals(row[1].toString()))) {
				return true;
			}

		}

		return false;
	}
	
	/**
	 * 
	 * @param updatedMarksDetailsList
	 * @return
	 */
	public boolean updateentryCorrectionDetailsBO(List<ExamMarksEntryDetailsBO> updatedMarksDetailsList) {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Iterator<ExamMarksEntryDetailsBO> itr = updatedMarksDetailsList.iterator();
		Transaction tx = null;
		try {
			while (itr.hasNext()) {
				ExamMarksEntryDetailsBO detBO = (ExamMarksEntryDetailsBO) itr
						.next();
				tx= session.beginTransaction();
				
				String HQL_QUERY = "from ExamMarksEntryDetailsBO where marksEntryId = " 
				+ detBO.getMarksEntryId() + " and subjectId = " + detBO.getSubjectId(); 
			
				
			/*	String HQL_QUERY = "INSERT INTO EXAM_marks_entry_correction_details "+
						" SELECT * from  EXAM_marks_entry_details where marks_entry_id = " 
						+ detBO.getMarksEntryId() + " and subject_id = " + detBO.getSubjectId(); */
					
				List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = session.createQuery(HQL_QUERY).list();
				ExamMarksEntryCorrectionDetailsBO examMarksEntryBO;
				if(examMarksEntryDetailsBOList!= null && examMarksEntryDetailsBOList.size() > 0){
					Iterator<ExamMarksEntryDetailsBO> itr1 = examMarksEntryDetailsBOList.iterator();
					while (itr1.hasNext()) {
						ExamMarksEntryDetailsBO examMarksEntryDetailsBO = (ExamMarksEntryDetailsBO) itr1
								.next();
						
						examMarksEntryBO =	new ExamMarksEntryCorrectionDetailsBO(examMarksEntryDetailsBO.getMarksEntryId(),
								examMarksEntryDetailsBO.getSubjectId(), examMarksEntryDetailsBO.getTheoryMarks(), examMarksEntryDetailsBO.getPracticalMarks(),
								examMarksEntryDetailsBO.getPreviousEvaluatorTheoryMarks(),
								examMarksEntryDetailsBO.getPreviousEvaluatorPracticalMarks(), examMarksEntryDetailsBO.getIsMistake(),
								examMarksEntryDetailsBO.getIsRetest(), null,
								null, detBO.getComments(), null, null);
						session.save(examMarksEntryBO);
						
					}
				}
				
				
			}
			tx.commit();
			return true;
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
			return false;
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
			return false;
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		
	}
	/**
	 * 
	 * @param updatedMarksDetailsList
	 * @return
	 */
	public boolean deleteExamMarkEntryDetailsBO(List<ExamMarksEntryDetailsBO> updatedMarksDetailsList) {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Iterator<ExamMarksEntryDetailsBO> itr = updatedMarksDetailsList.iterator();
		Transaction tx = null;
		try {
			tx= session.beginTransaction();
			while (itr.hasNext()) {
				ExamMarksEntryDetailsBO detBO = (ExamMarksEntryDetailsBO) itr
						.next();
				String HQL_QUERY = "DELETE FROM EXAM_marks_entry_details "+
				" where marks_entry_id = " 
				+ detBO.getMarksEntryId() + " and subject_id = " + detBO.getSubjectId(); 
			
				Query query = session.createSQLQuery(HQL_QUERY);
				query.executeUpdate();
			}
			tx.commit();
			return true;
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
			return false;
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
			return false;
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	
	}
	/**
	 * 
	 * @param detBO
	 * @return
	 */
	public boolean saveHistoryToTable(ExamMarksEntryDetailsBO detBO) {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		Transaction tx = null;
		try {
			tx= session.beginTransaction();
			String HQL_QUERY = "INSERT INTO EXAM_marks_entry_correction_details "+
					" SELECT * from  EXAM_marks_entry_details where marks_entry_id = " 
					+ detBO.getMarksEntryId() + " and subject_id = " + detBO.getSubjectId(); 
				
			Query query = session.createSQLQuery(HQL_QUERY);
			query.executeUpdate();
			tx.commit();
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
			return false;
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
			return false;
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param detBO
	 * @return
	 */
	public boolean deleteExamMarkEntryDetailsBOForAllStudents(ExamMarksEntryDetailsBO detBO) {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx= session.beginTransaction();
			String HQL_QUERY = "DELETE FROM EXAM_marks_entry_details "+
			" where marks_entry_id = " 
			+ detBO.getMarksEntryId() + " and subject_id = " + detBO.getSubjectId(); 
		
			Query query = session.createSQLQuery(HQL_QUERY);
			query.executeUpdate();
			tx.commit();
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
			return false;
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
			return false;
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ArrayList<Object[]> getoldMarkDetails(int id, String subjectId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ArrayList<Object[]> list = null;
		try {
			tx= session.beginTransaction();
			
			String sqlQUERY = "SELECT  s.name,"
			+ " med.theory_marks,"
			+ " med.practical_marks,"
			+ " med.comments, med.last_modified_date"
			+ " FROM EXAM_marks_entry_correction_details med"
			+ " LEFT JOIN subject s" + " ON med.subject_id = s.id where med.marks_entry_id = " + id + " and "
			+ " med.subject_id = " + subjectId;
			
			Query query = session.createSQLQuery(sqlQUERY);
			list = (ArrayList<Object[]>) query.list();
		
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;

	}
	
	/**
	 * 
	 * @return
	 */
	
	public Map<Integer, List<Integer>> getoldMarkPresent() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Object[]> list = null;
		Map<Integer, List<Integer>> subjectMarkList = new HashMap<Integer, List<Integer>>();
		List<Integer> subjectIdList = new ArrayList<Integer>();
		try {
			tx= session.beginTransaction();
			
			String sqlQUERY = "select e.marksEntryId, e.subjectId FROM ExamMarksEntryCorrectionDetailsBO e where e.marksEntryId IS NOT NULL";
			
			Query query = session.createQuery(sqlQUERY);
			list = (List<Object[]>) query.list();
			if(list!= null && list.size() > 0){
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if(obj!= null){
						if(subjectMarkList.containsKey((Integer.parseInt(obj[0].toString())))){
							subjectIdList = subjectMarkList.get(Integer.parseInt(obj[0].toString()));
							subjectIdList.add(Integer.parseInt(obj[1].toString()));
							subjectMarkList.put(Integer.parseInt(obj[0].toString()), subjectIdList);
						}
						else{
							subjectIdList = new ArrayList<Integer>();
							subjectIdList.add(Integer.parseInt(obj[1].toString()));
							subjectMarkList.put(Integer.parseInt(obj[0].toString()), subjectIdList);
						}
					}
				}
			}
			tx.commit();
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return subjectMarkList;

	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ArrayList<Object[]> getoldMarkDetailsInternal(int id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ArrayList<Object[]> list = null;
		try {
			tx= session.beginTransaction();
			
			String sqlQUERY = "SELECT  s.name,"
			+ " med.theory_marks,"
			+ " med.practical_marks,"
			+ " med.comments, med.last_modified_date"
			+ " FROM EXAM_marks_entry_correction_details med"
			+ " LEFT JOIN subject s" + " ON med.subject_id = s.id where med.overall_id = " + id;
			
			Query query = session.createSQLQuery(sqlQUERY);
			list = (ArrayList<Object[]>) query.list();
		
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;

	}
	
	/**
	 * 
	 * @return
	 */
	
	public Map<Integer, List<Integer>> getoldMarkPresentForInternalOverAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Object[]> list = null;
		Map<Integer, List<Integer>> subjectMarkList = new HashMap<Integer, List<Integer>>();
		List<Integer> subjectIdList = new ArrayList<Integer>();
		try {
			tx= session.beginTransaction();
			
			String sqlQUERY = "select e.overAllId, e.subjectId FROM ExamMarksEntryCorrectionDetailsBO e where e.overAllId IS NOT NULL";
			
			Query query = session.createQuery(sqlQUERY);
			list = (List<Object[]>) query.list();
			if(list!= null && list.size() > 0){
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if(obj!= null){
						if(subjectMarkList.containsKey((Integer.parseInt(obj[0].toString())))){
							subjectIdList = subjectMarkList.get(Integer.parseInt(obj[0].toString()));
							subjectIdList.add(Integer.parseInt(obj[1].toString()));
							subjectMarkList.put(Integer.parseInt(obj[0].toString()), subjectIdList);
						}
						else{
							subjectIdList = new ArrayList<Integer>();
							subjectIdList.add(Integer.parseInt(obj[1].toString()));
							subjectMarkList.put(Integer.parseInt(obj[0].toString()), subjectIdList);
						}
					}
				}
			}
			tx.commit();
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return subjectMarkList;

	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ArrayList<Object[]> getoldMarkDetailsInternalRegular(int id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ArrayList<Object[]> list = null;
		try {
			tx= session.beginTransaction();
			
			String sqlQUERY = "SELECT  s.name,"
			+ " med.theory_marks,"
			+ " med.practical_marks,"
			+ " med.comments, med.last_modified_date"
			+ " FROM EXAM_marks_entry_correction_details med"
			+ " LEFT JOIN subject s" + " ON med.subject_id = s.id where med.int_final_mark_id = " + id;
			
			Query query = session.createSQLQuery(sqlQUERY);
			list = (ArrayList<Object[]>) query.list();
		
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;

	}

	public Map<Integer, List<Integer>> getoldMarkPresentForRegularOverAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Object[]> list = null;
		Map<Integer, List<Integer>> subjectMarkList = new HashMap<Integer, List<Integer>>();
		List<Integer> subjectIdList = new ArrayList<Integer>();
		try {
			tx= session.beginTransaction();
			
			String sqlQUERY = "select e.finalMarkId, e.subjectId FROM ExamMarksEntryCorrectionDetailsBO e where e.finalMarkId IS NOT NULL";
			
			Query query = session.createQuery(sqlQUERY);
			list = (List<Object[]>) query.list();
			if(list!= null && list.size() > 0){
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if(obj!= null){
						if(subjectMarkList.containsKey((Integer.parseInt(obj[0].toString())))){
							subjectIdList = subjectMarkList.get(Integer.parseInt(obj[0].toString()));
							subjectIdList.add(Integer.parseInt(obj[1].toString()));
							subjectMarkList.put(Integer.parseInt(obj[0].toString()), subjectIdList);
						}
						else{
							subjectIdList = new ArrayList<Integer>();
							subjectIdList.add(Integer.parseInt(obj[1].toString()));
							subjectMarkList.put(Integer.parseInt(obj[0].toString()), subjectIdList);
						}
					}
				}
			}
			tx.commit();
		} catch (ConstraintViolationException e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}catch (Exception e) {
			if(session.isOpen()){	
				tx.rollback();
			}
		}finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return subjectMarkList;

	}
	
	/**
	 * 
	 * @param studentId
	 * @param subjectId
	 * @param evaluatorId
	 * @return
	 * @throws ApplicationException
	 */
	public HashMap<Integer, Integer> getMarkEntryIdForSubject(int studentId, Integer evaluatorId, int examId, boolean secured, Integer answerScriptTypeId) throws ApplicationException {
		Session session = null;
		HashMap<Integer, Integer> idMap = new HashMap<Integer, Integer>();
		int intIsSecured = 0;
		if(secured){
			intIsSecured = 1;
		}
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select det.subjectId, det.marksEntryId from ExamMarksEntryBO e inner join e.examMarksEntryDetailsBOset det " +
					" where e.studentId = " + studentId + 
					" and e.evaluatorTypeId = " + evaluatorId + " and e.examId = " + examId +  " and e.isSecured = " + intIsSecured + " and e.answerScriptTypeId = " + answerScriptTypeId);
			List<Object[]> list = query.list();
			session.flush();
			Iterator<Object[]> itr = list.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				idMap.put((Integer)row[0], (Integer)row[1]);
			}
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return idMap;
	}

}
