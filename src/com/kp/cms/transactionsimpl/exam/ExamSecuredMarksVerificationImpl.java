package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.bo.exam.ExamSecuredMarkVerificationBO;
import com.kp.cms.bo.exam.ExamSecuredMarksVerificationDetailsBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExamSecuredMarksVerificationForm;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamSecuredMarksVerificationImpl extends ExamGenImpl {

	public SubjectUtilBO get_subjectCode_BySubjName(int id) {

		Session session = null;
		SubjectUtilBO s = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(SubjectUtilBO.class);
			crit.add(Restrictions.eq("id", id));
			s = (SubjectUtilBO) crit.uniqueResult();
			session.flush();
			// session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return s;
	}

	public ArrayList<SubjectUtilBO> get_subjectList() {

		Session session = null;
		ArrayList<SubjectUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session
					.createQuery("select sub.id, sub.name , sub.code from SubjectUtilBO sub	where sub.isActive = 1");
			list = new ArrayList<SubjectUtilBO>(query.list());

			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<SubjectUtilBO>();
		}
		return list;
	}

	// To get the rollNo/regNo from exam settings
	public boolean getsecured_marks_type() {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList list = null;
		String HQL = null;
		boolean regOrRoll = true;

		HQL = "select es.securedMarkEntryBy from ExamSettingsBO es";

		Query query = session.createQuery(HQL);

		list = new ArrayList(query.list());
		if (list != null && list.size() > 0) {
			return (list.get(0).toString().equalsIgnoreCase("Register No")) ? true
					: false;
		}
		return regOrRoll;
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
		if (list != null && list.size() > 0) {
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

	// To get the id of ExamSecuredMarkVerificationBO
	public int insert_returnId(ExamSecuredMarkVerificationBO objBO) {

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

	// To validate students' subjects
	public boolean get_subjectsList(int subjectId, String rollRegNo,
			boolean rollOrReg) {
		List list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			String HQL_QUERY = " SELECT st.id" + "   FROM  student st"
					+ "  LEFT JOIN" + " applicant_subject_group asg"
					+ " ON asg.adm_appln_id = st.adm_appln_id"
					+ " LEFT JOIN subject_group_subjects sgs"
					+ " ON asg.subject_group_id = sgs.subject_group_id"
					+ "	LEFT JOIN subject s" + " ON sgs.subject_id = s.id"
					+ " LEFT JOIN class_schemewise"
					+ " ON st.class_schemewise_id = class_schemewise.id"
					+ "  WHERE sgs.subject_id = :subjectId ";
			// "select stu.id"
			// +
			// " from StudentUtilBO stu where stu.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.id in ("
			// + " select css.curriculumSchemeDurationId"
			// + " from CurriculumSchemeSubjectUtilBO css"
			// + " where css.subjectGroupId in ("
			// + " select sgs.subjectGroupUtilBO.id"
			// + " from SubjectGroupSubjectsUtilBO sgs"
			// + " where sgs.subjectUtilBO.id = :subjectId))";

			if (rollOrReg) {
				HQL_QUERY = HQL_QUERY + " and st.registerNo = :rollRegNo";

			} else {

				HQL_QUERY = HQL_QUERY + " and st.rollNo = :rollRegNo";
			}

			Query query = session.createSQLQuery(HQL_QUERY);

			query.setParameter("subjectId", subjectId);
			query.setParameter("rollRegNo", rollRegNo);
			list = query.list();

		} catch (Exception e) {

		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// To get studentId by roll/register no depending on exam - settings
	public Integer select_Student_id_rollReg(String rollRegNo, boolean rollOrReg) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer id = null;
		String HQL_QUERY = "select s.id from StudentUtilBO s";

		if (rollOrReg) {
			HQL_QUERY = HQL_QUERY + " where s.registerNo = :rollRegNo";

		} else {

			HQL_QUERY = HQL_QUERY + " where s.rollNo = :rollRegNo";
		}

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("rollRegNo", rollRegNo);
		if (query.list().size() > 0) {
			id = (Integer) query.list().get(0);

		} else {
			id = Integer.valueOf(0);
		}
		return id;
	}

	// Duplicate Check ExamSecuredMarksVerificationDetailsBO
	public boolean isDuplicated_examSecuredMarksVerificationDetails(
			Integer examSecuredMarkVerificationId, Integer studentId) {
		Session session = null;
		boolean var = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamSecuredMarksVerificationDetailsBO.class);
			if (examSecuredMarkVerificationId != null) {
				crit.add(Restrictions.eq("examSecuredMarkVerificationId",
						examSecuredMarkVerificationId));
			}
			if (studentId != null) {
				crit.add(Restrictions.eq("studentId", studentId));
			}
			List<ExamSecuredMarksVerificationDetailsBO> list = crit.list();

			if (list.size() > 0) {
				var = true;
			}
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return var;

	}

	// To UPDATE
	public int update_mistake_retest(int id, int mistake, int retest) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String HQL_QUERY = "update ExamSecuredMarksVerificationDetailsBO e set e.isMistake = :mistake, e.isRetest = :retest"
				+ " where e.id = :id";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.setParameter("mistake", mistake);
		query.setParameter("retest", retest);
		int flag = query.executeUpdate();

		tx.commit();
		session.close();
		return flag;
	}

	// To get the mark details(GRID)
	public ArrayList<Object[]> select_secured_markdet_stu(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			int studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String SQL1 = null;
		String SQL2 = null;
		String SQL3 = null;
		String SQL4 = null;

		if (evaluatorId == null) {
			SQL1 = " and esm.evaluator_type_id is null ";
		} else {
			SQL1 = " and esm.evaluator_type_id = :evaluatorId";
		}

		if (ansScriptId == null) {
			SQL2 = " and esm.answer_script_id is null ";
		} else {
			SQL2 = " and esm.answer_script_id = :ansScriptId";
		}

		if (evaluatorId == null) {
			SQL3 = " and eme.evaluator_type_id is null ";
		} else {
			SQL3 = " and eme.evaluator_type_id = :evaluatorId";
		}

		if (ansScriptId == null) {
			SQL4 = " and eme.answer_script_type is null ";
		} else {
			SQL4 = " and eme.answer_script_type = :ansScriptId";
		}
		ArrayList list = null;
		String SQL_QUERY = " SELECT student.register_no,"
				+ "        student.roll_no,"
				+ "        ifnull(esmd.theory_marks, emed.theory_marks),"
				+ "        ifnull(esmd.practical_marks, emed.practical_marks),"
				+ "        esmd.is_mistake,"
				+ "        esmd.is_retest,"
				+ "        personal_data.first_name,"
				+ "        eme.id AS emeid,"
				+ "        esm.id AS esmid,"
				+ "        esmd.id AS esmdid"
				+ "   FROM EXAM_marks_entry eme"
				+ "        JOIN EXAM_marks_entry_details emed"
				+ "           ON emed.marks_entry_id = eme.id AND emed.subject_id = :subjectId"
				+ "        JOIN student"
				+ "           ON eme.student_id = student.id"
				+ "        JOIN adm_appln"
				+ "           ON student.adm_appln_id = adm_appln.id"
				+ "        JOIN personal_data"
				+ "           ON adm_appln.personal_data_id = personal_data.id"
				+ "        LEFT JOIN EXAM_secured_mark_verification esm"
				+ "           ON esm.subject_id = emed.subject_id AND esm.exam_id = :examId"
				+ SQL1
				+ SQL2
				+ "        LEFT JOIN EXAM_secured_mark_verification_details esmd"
				+ "           ON esmd.exam_secured_mark_verification_id = esm.id"
				+ "  WHERE eme.student_id = :studentId AND eme.is_secured = 1 "
				+ SQL3 + SQL4 + "        AND eme.exam_id = :examId";

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("subjectId", subjectId);
		if (evaluatorId != null) {
			query.setParameter("evaluatorId", evaluatorId);
		}

		if (ansScriptId != null) {
			query.setParameter("ansScriptId", ansScriptId);
		}

		query.setParameter("studentId", studentId);

		list = (ArrayList<Object[]>) query.list();
		return list;

	}
	
	// To get the mark details(GRID)
	public ArrayList<Object[]> select_secured_markdet_stu1(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			int studentId, String subjectType) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String SQL1 = null;
		String SQL2 = null;
		String SQL3 = null;
		String SQL4 = null;

		if (evaluatorId == null) {
			SQL1 = " and esm.evaluator_type_id is null ";
		} else {
			SQL1 = " and esm.evaluator_type_id = :evaluatorId";
		}

		if (ansScriptId == null) {
			SQL2 = " and esm.answer_script_id is null ";
		} else {
			SQL2 = " and esm.answer_script_id = :ansScriptId";
		}

		if (evaluatorId == null) {
			SQL3 = " and eme.evaluator_type_id is null ";
		} else {
			SQL3 = " and eme.evaluator_type_id = :evaluatorId";
		}

		if (ansScriptId == null) {
			SQL4 = " and eme.answer_script_type is null ";
		} else {
			SQL4 = " and eme.answer_script_type = :ansScriptId";
		}
		

		ArrayList list = null;
		
		String SQL_QUERY = " SELECT student.register_no,"
			+ "        student.roll_no,"
			+ "        personal_data.first_name,personal_data.middle_name,personal_data.last_name,eme.class_id"
			+ "   FROM EXAM_marks_entry eme"
			+ "        JOIN EXAM_marks_entry_details emed"
			+ "           ON emed.marks_entry_id = eme.id AND emed.subject_id = :subjectId"
			+ "        JOIN student"
			+ "           ON eme.student_id = student.id"
			+ "        JOIN adm_appln"
			+ "           ON student.adm_appln_id = adm_appln.id"
			+ "        JOIN personal_data"
			+ "           ON adm_appln.personal_data_id = personal_data.id"
			+ "        LEFT JOIN EXAM_secured_mark_verification esm"
			+ "           ON esm.subject_id = emed.subject_id AND esm.exam_id = :examId"
			+ SQL1
			+ SQL2
			+ "        LEFT JOIN EXAM_secured_mark_verification_details esmd"
			+ "           ON esmd.exam_secured_mark_verification_id = esm.id"
			+ "  WHERE eme.student_id = :studentId "
			+ SQL3 + SQL4 + "        AND eme.exam_id = :examId";
		if(subjectType != null && !subjectType.trim().isEmpty()){
			if(subjectType.equalsIgnoreCase("Theory"))
				SQL_QUERY = SQL_QUERY + " AND (emed.theory_marks is not null AND emed.theory_marks !='')";
			else
				SQL_QUERY = SQL_QUERY + " AND (emed.practical_marks is not null AND emed.practical_marks !='')";
		}
		
		String SQL_QUERY1 = " SELECT student.register_no,"
				+ "        student.roll_no,"
				+ "        personal_data.first_name,"
				+ "        student.id"
				+ "   FROM student"
				+ "        JOIN adm_appln"
				+ "           ON student.adm_appln_id = adm_appln.id"
				+ "        JOIN personal_data"
				+ "           ON adm_appln.personal_data_id = personal_data.id"
				+ " where student.register_no = :studentId";

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("subjectId", subjectId);
		if (evaluatorId != null) {
			query.setParameter("evaluatorId", evaluatorId);
		}

		if (ansScriptId != null) {
			query.setParameter("ansScriptId", ansScriptId);
		}

		query.setParameter("studentId", studentId);

		list = (ArrayList<Object[]>) query.list();
		return list;

	}
	public ArrayList<Object[]> select_secured_markdet_stu3(int studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		

		ArrayList list = null;
		String SQL_QUERY = " SELECT student.register_no,"
				+ "        student.roll_no,"
				+ "        personal_data.first_name,"
				+ "        personal_data.middle_name,"
				+ "        personal_data.last_name,"
				+ "        student.id"
				+ "   FROM student"
				+ "        JOIN adm_appln"
				+ "           ON student.adm_appln_id = adm_appln.id"
				+ "        JOIN personal_data"
				+ "           ON adm_appln.personal_data_id = personal_data.id"
				+ " where student.id = :studentId";

		Query query = session.createSQLQuery(SQL_QUERY);
		
		query.setParameter("studentId", studentId);

		list = (ArrayList<Object[]>) query.list();
		return list;

	}
	
	// To Check the Student Id
	public ArrayList<Object[]> checkStudentId(int examId,
			int subjectId, Integer evaluatorId, Integer ansScriptId,
			int studentId){

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		//String SQL1 = null;
		//String SQL2 = null;
		String SQL3 = null;
		//String SQL4 = null;

		/*if (evaluatorId == null) {
			SQL1 = " and emvd.evaluator_type_id is null ";
		} else {
			SQL1 = " and emvd.evaluator_type_id = :evaluatorId";
		}

		if (ansScriptId == null) {
			SQL2 = " and emvd.answer_script_id is null ";
		} else {
			SQL2 = " and emvd.answer_script_id = :ansScriptId";
		}*/

		if (evaluatorId == null) {
			SQL3 = " and emvd.evaluator_type_id is null ";
		} else {
			SQL3 = " and emvd.evaluator_type_id = :evaluatorId";
		}

		/*if (ansScriptId == null) {
			SQL4 = " and emvd.answer_script_type is null ";
		} else {
			SQL4 = " and emvd.answer_script_type = :ansScriptId";
		}*/
		ArrayList list = null;
		String SQL_QUERY = " SELECT emvd.student_id,"
			+ "        emvd.exam_id"
			+ "   FROM EXAM_marks_verification_details emvd"
				+ "  WHERE emvd.student_id = :studentId "
				+ SQL3 + "        AND emvd.exam_id = :examId"
				+ "				  AND emvd.subject_id = :subjectId";

		Query query = session.createSQLQuery(SQL_QUERY);
		query.setParameter("examId", examId);
		query.setParameter("subjectId", subjectId);
		if (evaluatorId != null) {
			query.setParameter("evaluatorId", evaluatorId);
		}

		/*if (ansScriptId != null) {
			query.setParameter("ansScriptId", ansScriptId);
		}*/

		query.setParameter("studentId", studentId);

		list = (ArrayList<Object[]>) query.list();
		if(list.size() != 0){
		}
		return list;

	}
	// To get id of first table based on the given conditions
	public Integer get_smeid(int examId, int subjectId, Integer evaluatorType,
			Integer answerScriptId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String SQL1 = null;
		String SQL2 = null;
		if (evaluatorType == null) {
			SQL1 = " esmv.evaluatorTypeId is null ";
		} else {
			SQL1 = " esmv.evaluatorTypeId = :evaluatorType";
		}

		if (answerScriptId == null) {
			SQL2 = " and esmv.answerScriptId is null ";
		} else {
			SQL2 = " and esmv.answerScriptId = :answerScriptId";
		}
		String HQL_QUERY = "select esmv.id"
				+ " from ExamSecuredMarkVerificationBO esmv where" + SQL1
				+ SQL2
				+ " and esmv.examId = :examId and esmv.subjectId = :subjectId ";

		Query query = session.createQuery(HQL_QUERY);

		query.setParameter("examId", examId);
		query.setParameter("subjectId", subjectId);
		if (evaluatorType != null) {
			query.setParameter("evaluatorType", evaluatorType);
		}
		if (answerScriptId != null) {
			query.setParameter("answerScriptId", answerScriptId);
		}

		Integer id = (Integer) query.list().get(0);

		return id;
	}

	// Duplicate Check ExamSecuredMarksVerification
	public int isDuplicated_examSecuredMarksVerification(int examId,
			int subjectId, Integer evaluatorTypeId, Integer answerScriptId) {
		Session session = null;
		int id = 0;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamSecuredMarkVerificationBO.class);
			crit.add(Restrictions.eq("examId", examId));
			crit.add(Restrictions.eq("subjectId", subjectId));
			if (evaluatorTypeId != null) {
				crit.add(Restrictions.eq("evaluatorTypeId", evaluatorTypeId));
			}
			if (answerScriptId != null) {
				crit.add(Restrictions.eq("answerScriptId", answerScriptId));
			}
			List<ExamSecuredMarkVerificationBO> list = crit.list();

			Iterator<ExamSecuredMarkVerificationBO> it = list.iterator();
			while (it.hasNext()) {
				ExamSecuredMarkVerificationBO bo = it.next();
				id = bo.getId();

			}
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return id;
	}

	// Validation for Multiple Answer Scripts
	public Integer select_answerScriptFrom_ruleSettings(Integer courseId,
			Integer schemeNo, Integer subjectId, Integer subjectTypeId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

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
		String HQL_QUERY = "select esrm.id"
				+ " from ExamSubjectRuleSettingsMultipleAnsScriptBO esrm"
				+ " join esrm.examSubjectRuleSettingsBO esr"
				+ " where esr.courseId = :courseId and esr.schemeNo = :schemeNo";

		if (subjectId != null && subjectTypeId != null) {
			if (subjectTypeId == 1) {
				HQL_QUERY = HQL_QUERY
						+ " and esr.subjectId = :subjectId and esrm.isTheoryPractical = :isTheoryPractical and esr.theoryEseIsMultipleAnswerScript = 1 ";

			} else if (subjectTypeId == 0) {
				HQL_QUERY = HQL_QUERY
						+ " and esr.subjectId = :subjectId and esrm.isTheoryPractical = :isTheoryPractical"
						+ " and esr.practicalEseIsMultipleAnswerScript = 1 ";
			} else {
				HQL_QUERY = HQL_QUERY
						+ " and esr.subjectId = :subjectId and esrm.isTheoryPractical = :isTheoryPractical"
						+ " and esr.theoryEseIsMultipleAnswerScript = 1 and esr.practicalEseIsMultipleAnswerScript = 1 ";
			}
		} else {
			HQL_QUERY = HQL_QUERY
					+ " and (esr.theoryEseIsMultipleAnswerScript = 1 or esr.practicalEseIsMultipleAnswerScript = 1)";
		}
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
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

	}
	
	public Integer insert_returnId1(ExamMarksVerificationEntryBO vmasterBO) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {
			String query = "from ExamMarksVerificationEntryBO e where e.examId= " +vmasterBO.getExamId()+
							" and e.studentId= " +vmasterBO.getStudentId()+
							" and e.subjectId= " + vmasterBO.getSubjectId();
			if(vmasterBO.getEvaluatorTypeId() != null && vmasterBO.getEvaluatorTypeId() !=0){
				query = query + " and e.evaluatorTypeId ="+vmasterBO.getEvaluatorTypeId();
			}
			ExamMarksVerificationEntryBO bo = (ExamMarksVerificationEntryBO)session.createQuery(query).uniqueResult();
			if(bo == null){
				session.save(vmasterBO);
			}
			if(vmasterBO.getId() != null)
				id = vmasterBO.getId();
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

	/**
	 * @param objform
	 * @param regNo 
	 * @return
	 * @throws Exception
	 */
	public Double getMaxMarkOfSubject(ExamSecuredMarksVerificationForm objform, String regNo) throws Exception{
		Session session = null;
		Double maxMarks=null;
		try{
			session = HibernateUtil.getSession();
			String subjectType="";
			if(objform.getSubjectType().equalsIgnoreCase("Theory")){
				subjectType = "T";
			}else if(objform.getSubjectType().equalsIgnoreCase("Practical")){
				subjectType = "P";
			}
			String query="select internal.enteredMaxMark," +
					" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark,ansScript.value from SubjectRuleSettings s" +
					" left join s.examSubjectRuleSettingsSubInternals internal" +
					" left join s.examSubjectRuleSettingsMulEvaluators eval" +
					" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " ;
			Student student = (Student)session.createQuery("from Student s where s.registerNo='"+regNo+"'").uniqueResult();
			
			if(student==null){
				student = (Student)session.createQuery("select s.student from StudentOldRegisterNumber s where s.registerNo='"+regNo+"'").uniqueResult();
			}
			Classes cls = null;
			int year = 0;
			objform.setStudentId(String.valueOf(student.getId()));
			if(!objform.getExamType().equalsIgnoreCase("Supplementary")){
				if(objform.getIsPreviousExam().equalsIgnoreCase("false")){
					query = query + " where s.course.id=" +student.getClassSchemewise().getClasses().getCourse().getId()+
					" and s.schemeNo=" +student.getClassSchemewise().getClasses().getTermNumber();
					year = student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear();
				}else{
					String clsQuery = "select classHis.classes,cd.academicYear from Student s " +
					" join s.studentPreviousClassesHistory classHis " +
					" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd" +
					" join s.studentSubjectGroupHistory subjHist" +
					" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
					" where s.id="+student.getId()+
					" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
					" from ExamDefinitionBO e where e.id="+objform.getExamId()+")" +	
					" and subSet.subject.id="+objform.getSubject()+" and classHis.schemeNo=subjHist.schemeNo" +
					" group by s.id";
					Object[] data = (Object[])session.createQuery(clsQuery).uniqueResult();
					if(data != null){
						cls = (Classes)data[0];
						year = Integer.parseInt(data[1].toString());
						query = query + " where s.course.id=" +cls.getCourse().getId()+
						" and s.schemeNo=" +cls.getTermNumber();
					}else{
						query = query + " where s.course.id=0" +
						" and s.schemeNo=0";
					}
				}
			}else{
				String clsQuery = "select bo.classes,cd.academicYear from ExamSupplementaryImprovementApplicationBO bo " +
								  " join bo.classes.classSchemewises cls " +
								  " join cls.curriculumSchemeDuration cd " +
								  " where bo.examId= " +objform.getExamId()+
								  " and bo.studentId= " +student.getId()+
								  " and bo.subjectId= " +objform.getSubject()+
								  " and bo.classes.termNumber= "+objform.getSchemeNo();
				if(subjectType!=null && !subjectType.isEmpty()){
					if(subjectType.equalsIgnoreCase("t")){
						clsQuery=clsQuery+" and bo.isAppearedTheory=1";
					}else if(subjectType.equalsIgnoreCase("p")){
						clsQuery=clsQuery+" and bo.isAppearedPractical=1";
					}else{
						clsQuery=clsQuery+" and bo.isAppearedTheory=1 and bo.isAppearedPractical=1";
					}
				}
				Object[] data = (Object[])session.createQuery(clsQuery).uniqueResult();
				if(data != null){
					cls = (Classes)data[0];
					year = Integer.parseInt(data[1].toString());
					query = query + " where s.course.id=" +cls.getCourse().getId()+
					" and s.schemeNo=" +objform.getSchemeNo();
				}else{
					query = query + " where and s.schemeNo=" +objform.getSchemeNo();
				}
			}
			query = query + " and s.academicYear="+year+
			" and s.subject.id=" +objform.getSubject();
			
			if(objform.getEvaluatorType()!=null && !objform.getEvaluatorType().isEmpty()){
				query=query+" and eval.evaluatorId="+objform.getEvaluatorType()+
				" and eval.isTheoryPractical='"+subjectType+"'";
			}
			if(objform.getAnswerScriptType()!=null && !objform.getAnswerScriptType().isEmpty()){
				query=query+" and ansScript.multipleAnswerScriptId="+objform.getAnswerScriptType()+
				" and ansScript.isTheoryPractical='"+subjectType+"'";
			}	
			if(objform.getExamType().equals("Internal")){
				query=query+" and internal.internalExamTypeId=(select e.internalExamType.id" +
						" from ExamDefinition e where e.id="+objform.getExamId()+
				") and internal.isTheoryPractical='"+subjectType+"'";
			}
			
			query=query+" and s.isActive=1 group by s.id";
			
			List<Object[]> list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objform.getExamType().equals("Internal")){
						if(objects[0]!=null)
							maxMarks=new Double(objects[0].toString());
					}else{
						if(objform.getAnswerScriptType()!=null && !objform.getAnswerScriptType().isEmpty()){
							if(objects[3]!=null)
								maxMarks=new Double(objects[3].toString());
						}else{
							if(objform.getSubjectType().equalsIgnoreCase("Theory") && objects[1]!=null)
								maxMarks=new Double(objects[1].toString());
							if(objform.getSubjectType().equalsIgnoreCase("Practical") && objects[2]!=null)
								maxMarks=new Double(objects[2].toString());
						}
					}
				}
			}
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
		return maxMarks;
	}
	
	
	/**
	 * @param classId
	 * @return
	 */
	public String getClassNameByClassId(int classId) {

		String className = "";
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query query = (Query) session
				.createQuery("select c.name from Classes c where c.isActive=1  and c.id= :classId");
		query.setParameter("classId", classId);
		className = (String)query.uniqueResult();
		}catch (Exception e) {
			// TODO: handle exception
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return className;
	}

	
}
