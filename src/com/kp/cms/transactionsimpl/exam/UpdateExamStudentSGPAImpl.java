package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamStudentCCPA;
import com.kp.cms.bo.exam.ExamStudentSgpa;
import com.kp.cms.bo.exam.GradePointRangeBO;
import com.kp.cms.bo.exam.SemesterWiseExamResults;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.exam.IUpdateStudentSGPATxn;
import com.kp.cms.utilities.HibernateUtil;

public class UpdateExamStudentSGPAImpl implements IUpdateStudentSGPATxn {
	private static final Log log = LogFactory.getLog(UpdateExamStudentSGPAImpl.class);
	public static volatile UpdateExamStudentSGPAImpl updateExamStudentSGPAImpl = null;
	
	public static UpdateExamStudentSGPAImpl getInstance() {
	if (updateExamStudentSGPAImpl == null) {
		updateExamStudentSGPAImpl = new UpdateExamStudentSGPAImpl();
		return updateExamStudentSGPAImpl;
	}
	return updateExamStudentSGPAImpl;
	}
	/**
	 * 
	 * @param courseId
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getClassIdsByCourseAndScheme(int courseId, int schemeNo, Integer academicYear) throws Exception {
		Session session = null;
		try {
			List<Integer> classIdList;
			session = HibernateUtil.getSession();

			classIdList = session.createQuery(" select cs.classes.id from ClassSchemewise cs " +
					" where cs.classes.isActive = 1  " +
					" and cs.classes.course.id = " + courseId + 
					" and cs.classes.termNumber =  " + schemeNo +
					" and cs.curriculumSchemeDuration.academicYear = " + academicYear).list();
			session.flush();
			return new ArrayList(classIdList);
			
		} catch (Exception e) {
			log.error("Error in getClassIdsByCourseAndScheme..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public Integer getStudentsTheoryMarkForSubj(Integer subjectId,
			Integer studentId, int classId) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer marks = null;
		String HQL_QUERY = "";
		try {
			HQL_QUERY = "select max(e.studentTheoryMarks)"
					+ " from ExamStudentFinalMarkDetailsBO e"
					+ " where e.studentId = :studentId and e.subjectId = :subjectId and classId = :classId";
		
	
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", subjectId);
			query.setParameter("studentId", studentId);
			query.setParameter("classId", classId);
			
			List list = query.list();
	
			if (list != null && list.size() > 0 && list.get(0) != null) {
				String str = list.get(0).toString();
				if (str != null && str.trim().length() > 0 && !StringUtils.isAlpha(str))
					marks = new Double(str).intValue();
	
			}
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error("Error in getClassIdsByCourseAndScheme..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return marks;
	}
	
	
	// Get student's practical marks for each subject
	public Integer getStudentsPracticalMarkForSubj(Integer subjectId,
			Integer studentId, int classId) throws Exception { 

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer marks = null;
		String HQL_QUERY = "";
		try {
		HQL_QUERY = "select max(e.studentPracticalMarks)"
				+ " from ExamStudentFinalMarkDetailsBO e"
				+ " where e.studentId = :studentId and e.subjectId = :subjectId and classId = :classId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("studentId", studentId);
		query.setParameter("classId", classId);
		List list = query.list();

		if (list != null && list.size() > 0 && list.get(0) != null) {
			String str = list.get(0).toString();
			if (str != null && str.trim().length() > 0 && !StringUtils.isAlpha(str))
				marks = new Double(str).intValue();

		}
		session.flush();
		session.close();
		} catch (Exception e) {
			log.error("Error in getClassIdsByCourseAndScheme..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return marks;
	}

	// Get student's internal marks for each subject
	public List<Object[]> getStudentsInternalMarkForSubj(Integer subjectId, Integer studentId, int classId) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object[]> l = null;
		String HQL_QUERY = "";
		try {
			 HQL_QUERY = "select e.theoryTotalSubInternalMarks, e.theoryTotalAssignmentMarks, e.theoryTotalAttendenceMarks,"
					+ " e.practicalTotalSubInternalMarks, e.practicalTotalAssignmentMarks, e.practicalTotalAttendenceMarks"
					+ " from ExamStudentOverallInternalMarkDetailsBO e"
					+ " where e.studentId = :studentId and e.subjectId = :subjectId and classId = :classId";
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", subjectId);
			query.setParameter("studentId", studentId);
			query.setParameter("classId", classId);
			l = (List<Object[]>) query.list();
			session.flush();
			if (l != null && l.size() > 0) {
				return l;
			} else {
				return new ArrayList<Object[]>();
			}
		} catch (Exception e) {
			log.error("Error in getClassIdsByCourseAndScheme..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * 
	 */
	public Double getGradePointForSubject(Integer subjectId, int courseId, Double per) throws Exception { 

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Double gradePoint = null;
		String HQL_QUERY = "";
		try {
			HQL_QUERY = "select gradePoint"
					+ " from ExamSubCoursewiseGradeDefnBO e"
					+ " where e.subjectId = :subjectId and e.courseId = :courseId and " +
					per +  " >= startPrcntgGrade and " +
					per + " <= endPrcntgGrade ";
	
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			List list = query.list();
	
			if (list != null && list.size() > 0 && list.get(0) != null) {
				String str = list.get(0).toString();
				if (str != null && str.trim().length() > 0 && !StringUtils.isAlpha(str))
					gradePoint = new Double(str);
	
			}
			else{
				
				list = session.createQuery("select gradePoint"
					+ " from ExamGradeDefinitionBO e" 
					+ " where e.courseId = " + courseId + " and isActive = 1 and " +
					per +  " >= startPercentage and " +
					per + " <= endPercentage "	).list();
				if (list != null && list.size() > 0 && list.get(0) != null) {
					String str = list.get(0).toString();
					if (str != null && str.trim().length() > 0 && !StringUtils.isAlpha(str))
						gradePoint = new Double(str);
		
				}
			}
		} catch (Exception e) {
			log.error("Error in getGradePointForSubject..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return gradePoint;
	}
	
	/**
	 * 
	 * @param subjectId
	 * @param courseId
	 * @param schemeNo
	 * @param subjectType
	 * @return
	 * @throws Exception
	 */
	public Integer getCreditForSubject(Integer subjectId, int courseId, int schemeNo, String subjectType, Double per, Integer academicYear) throws Exception { 

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Integer credit = null;
		String HQL_QUERY = "";
		try {
			
			if(subjectType.equalsIgnoreCase("T")){
				HQL_QUERY = "select theoryCredit ";
			}
			else if(subjectType.equalsIgnoreCase("P")){
				HQL_QUERY = "select practicalCredit ";
			}
			else if(subjectType.equalsIgnoreCase("B")){
				HQL_QUERY = "select (theoryCredit + practicalCredit) ";	
			}
			
			HQL_QUERY = HQL_QUERY + " from ExamSubDefinitionCourseWiseBO e"
					+ " where e.subjectId = :subjectId and e.courseId = :courseId and e.schemeNo = " + schemeNo + " and academicYear = " + academicYear;
	
			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			List list = query.list();
	
			if (list != null && list.size() > 0 && list.get(0) != null) {
				String str = list.get(0).toString();
				if (str != null && str.trim().length() > 0 && !StringUtils.isAlpha(str))
					credit = new Integer(str);
	
			}
		
		} catch (Exception e) {
			log.error("Error in getCreditForSubject..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return credit;
	}
	/**
	 * 
	 * @param sgpaList
	 * @return
	 * @throws Exception
	 */
	public boolean updateSgpa(List<ExamStudentSgpa> sgpaList) throws Exception {
		log.info("Inside updateSgpa");
		ExamStudentSgpa sgpa;
		Transaction tx=null;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Iterator<ExamStudentSgpa> itr = sgpaList.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				sgpa = itr.next();
				session.save(sgpa);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			log.info("End of updateSgpa");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			session.flush();
			session.clear();
			log.error("Error occured in updateSgpa");
			throw new ApplicationException(e);
		}
	}
	
	public Map<Integer, Double> getSubjectMark(ArrayList<Integer> subjectList,
			Integer academicYear, Integer courseId, int schemeNo) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Map<Integer, Double> subjectMarkMap = new HashMap<Integer, Double>();
		try {
			List l = null;
			String HQL_QUERY = " SELECT subject_id, theory_ese_theory_final_maximum_mark, practical_ese_theory_final_maximum_mark from "+
						        " EXAM_subject_rule_settings where subject_id in " +
						        "(:subjectList) and course_id = :courseId and academic_year = :academicYear" +
						        " and scheme_no = " + schemeNo + " and is_active = 1" +
						        " group by subject_id ";
					 
			Query query = session.createSQLQuery(HQL_QUERY);
			query.setParameter("academicYear", academicYear);
			query.setParameterList("subjectList", subjectList);
			query.setParameter("courseId", courseId);
	
			l = query.list();
			Double sum = 0.0;
			if (l != null && l.size() > 0) {
				Iterator<Object[]> itr = l.iterator();
				while (itr.hasNext()) {
					Object[] row = (Object[]) itr.next();
					sum = 0.0;
					if (row[1] != null) {
						sum = sum + new Double(row[1].toString());
					}
					if (row[2] != null) {
						sum = sum + new Double( row[2].toString());
					}
					subjectMarkMap.put(Integer.parseInt(row[0].toString()), sum);
				}
				
	
			}
		} catch (Exception e) {
			session.flush();
			session.clear();
			log.error("Error occured in updateSgpa");
			throw new ApplicationException(e);
		}
		return subjectMarkMap;
	}
	/**
	 * 
	 * @param courseId
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public boolean deleleAlreadyExistedRecords(int classId, int schemeNo)throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx= session.beginTransaction();
			String sqlQuey = " delete from EXAM_student_sgpa " +
			" where class_id = "  + classId +  " and scheme_no =  " + schemeNo ;
			Query query = session.createSQLQuery(sqlQuey);

			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();

			// for deleting semester results existing records..
			session = HibernateUtil.getSession();
			tx= session.beginTransaction();
			sqlQuey = " delete from semester_wise_result " +
			" where class_id = "  + classId +  " and scheme_no =  " + schemeNo ;
			query = session.createSQLQuery(sqlQuey);

			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleleAlreadyExistedRecords");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error while deleting"	, e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error while deleting" + e);
			throw new ApplicationException(e);
		}
	}
	
	public ArrayList<Integer> getSubjectsForStudentWithPrevExam(
			Integer studentId, String isPrevExam, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<Integer> l = new ArrayList<Integer>();
		String HQL = "";
		if (isPrevExam.equalsIgnoreCase("true")) {
			HQL = "  SELECT s.id  FROM   EXAM_student_previous_class_details st"
					+ "  LEFT JOIN"
					+ "  EXAM_student_sub_grp_history asg"
					+ "  ON asg.student_id = st.student_id"
					+ "  LEFT JOIN"
					+ "  subject_group_subjects sgs"
					+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "  LEFT JOIN"
					+ "  subject s"
					+ "  ON sgs.subject_id = s.id"
					+ "  WHERE st.student_id = :studentId"
					+ " and asg.scheme_no = "
					+ schemeNo
					+ " and st.scheme_no = " + schemeNo;

		} else {
			HQL = "  SELECT s.id  FROM   student st"
					+ "  LEFT JOIN"
					+ "  applicant_subject_group asg"
					+ "  ON asg.adm_appln_id = st.adm_appln_id"
					+ "  LEFT JOIN"
					+ "  subject_group_subjects sgs"
					+ "  ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1"
					+ "  LEFT JOIN" + "  subject s"
					+ "  ON sgs.subject_id = s.id"
					+ "  WHERE st.id = :studentId";
		}
		Query query = session.createSQLQuery(HQL);
		query.setParameter("studentId", studentId);
		Iterator<Object> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			if (obj != null) {
				l.add((Integer) obj);
			}

		}
		session.flush();
		session.close();
		return l;

	}

	/**
	 * @param sid 
	 * 
	 */
	public String getResultClass (int courseId, Double per, int appliedYear, int sid) throws Exception { 

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String result = null;
		String HQL_QUERY = "";
		try {
			if(appliedYear == 0){
				appliedYear = (Integer)session.createQuery("select s.admAppln.appliedYear from Student s where id = "+sid).uniqueResult();
			}
			List list = session.createQuery("select resultClass"
					+ " from GradeDefinitionBatchWiseBO e" 
					+ " where e.courseId = " + courseId + " and isActive = 1 and " +
					per +  " >= startPercentage and " +
					per + " <= endPercentage  and e.fromBatch<="+appliedYear ).list();
			if(list == null || list.isEmpty()){
				list = session.createQuery("select resultClass"
						+ " from ExamGradeDefinitionBO e" 
						+ " where e.courseId = " + courseId + " and isActive = 1 and " +
						per +  " >= startPercentage and " +
						per + " <= endPercentage "	).list();
			}
			if (list != null && list.size() > 0 && list.get(0) != null) {
				String str = list.get(0).toString();
				if (str != null && str.trim().length() > 0 )
					result = str;
	
			}

		} catch (Exception e) {
			log.error("Error in getGradePointForSubject..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return result;
	}	
	
	public String getResultGrade (int courseId, String per, int appliedYear, int sid) throws Exception { 

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		double perc=((Double.parseDouble(per))*10);
		//per=String.valueOf(perc);
		String result = null;
		String HQL_QUERY = "";
		try {
			if(appliedYear == 0){
				appliedYear = (Integer)session.createQuery("select s.admAppln.appliedYear from Student s where id = "+sid).uniqueResult();
			}
			List list = session.createQuery("select grade"
					+ " from ExamGradeDefinitionBO e" 
					+ " where e.courseId = " + courseId + " and e.isActive = 1 and " +
					per +  " >= startPercentage and " +
					per + " <= endPercentage" ).list();
			/*if(list == null || list.isEmpty()){
				list = session.createQuery("select grade"
						+ " from ExamGradeDefinitionBO e" 
						+ " where e.courseId = " + courseId + " and isActive = 1 and " +
						per +  " >= startPercentage and " +
						per + " <= endPercentage "	).list();
			}*/
			if (list != null && list.size() > 0 && list.get(0) != null) {
				String str = list.get(0).toString();
				if (str != null && str.trim().length() > 0 )
					result = str;
	
			}

		} catch (Exception e) {
			log.error("Error in getGradePointForSubject..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return result;
	}	
	public ArrayList<ExamStudentSgpa> getStudentSemesterResults(int courseId) throws Exception {
		Session session = null;
		try {
			List<ExamStudentSgpa> resultList;
			session = HibernateUtil.getSession();

			resultList = session.createQuery("from ExamStudentSgpa e " +
					"where e.course.id = "+courseId+" ").list();
			
			session.flush();
			return new ArrayList(resultList);

		} catch (Exception e) {
			log.error("Error in getStudentSemesterResults..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	public ArrayList<Integer> getClassIdsByCourse(int courseId,Integer academicYear) throws Exception {
		Session session = null;
		try {
			List<Integer> classIdList;
			session = HibernateUtil.getSession();

			classIdList = session.createQuery(" select cs.classes.id from ClassSchemewise cs " +
					" where cs.classes.isActive = 1  " +
					" and cs.classes.course.id = " + courseId + 
					" and cs.curriculumSchemeDuration.academicYear = " + academicYear).list();
			session.flush();
			return new ArrayList(classIdList);

		} catch (Exception e) {
			log.error("Error in getClassIdsByCourseAndScheme..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	public boolean updateCCPA(List<ExamStudentCCPA> ccpaList,boolean isRankGeneration) throws Exception {
		log.info("Inside updateCCPA");
		ExamStudentCCPA ccpa=new ExamStudentCCPA();
		Transaction tx=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Iterator<ExamStudentCCPA> itr = ccpaList.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				ccpa = itr.next();
				if(!isRankGeneration)
				session.save(ccpa);
				else
					session.update(ccpa);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			session.flush();
			session.close();
			log.info("End of updateCCPA");
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
				return false;
			}
			session.flush();
			session.clear();
		
			log.error("Error occured in updateCCPA");
			throw new ApplicationException(e);
			
		}
	}
	
	public ArrayList<ExamStudentSgpa> getStudentSemesterCount(int courseId) throws Exception {
		Session session = null;
		try {
			List<ExamStudentSgpa> resultList;
			session = HibernateUtil.getSession();

			resultList = session.createQuery("select count(e.student.id),e.student.id from ExamStudentSgpa e " +
					"where e.course.id = "+courseId+" group by e.student.id ").list();
			
			session.flush();
			return new ArrayList(resultList);

		} catch (Exception e) {
			log.error("Error in getStudentSemesterResults..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public ArrayList<ExamStudentSgpa> getStudentResultDetails(int stuId, int appliedYear) throws Exception {
		Session session = null;
		try {
			List<ExamStudentSgpa> resultList;
			session = HibernateUtil.getSession();

			resultList = session.createQuery("from ExamStudentSgpa e " +
					"where e.student.id = "+stuId+" and e.appliedYear = "+appliedYear+" group by e.student.id,e.classes.id ").list();
			
			session.flush();
			return new ArrayList(resultList);

		} catch (Exception e) {
			log.error("Error in getStudentSemesterResults..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public boolean deleleAlreadyExistingCCPARecords(int courseId,int batchYear)throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx= session.beginTransaction();
			String sqlQuey = " delete from coursewise_semester_result " +
			" where course_id = "  + courseId +" and batch_year = "+ batchYear;
			Query query = session.createSQLQuery(sqlQuey);

			query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();

			log.debug("leaving deleleAlreadyExistedRecords");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error while deleting"	, e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error while deleting" + e);
			throw new ApplicationException(e);
		}
	}
	public boolean saveSemesterWiseMarksDetails(List<SemesterWiseExamResults> boList) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			SemesterWiseExamResults bo = null;
			Iterator<SemesterWiseExamResults> itr = boList.iterator();
			int count = 0;
			while (itr.hasNext()) {
				bo = itr.next();
				session.save(bo);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public List<GradePointRangeBO>  getCoursewiseGrades (int courseId,int appliedYear,int sid) throws Exception { 

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String result = null;
		String HQL_QUERY = "";
		List<GradePointRangeBO> list = new ArrayList<GradePointRangeBO>();
		try {
			if(appliedYear==0)
				appliedYear = (Integer)session.createQuery("select s.admAppln.appliedYear from Student s where id = "+sid).uniqueResult();
			 list = (List<GradePointRangeBO>)session.createQuery(
					 " from GradePointRangeBO e" +
			 " where e.courseId = " + courseId + " and e.appliedYear="+appliedYear+" and e.isActive = 1 " ).list();
			
			if (list != null && list.size() > 0 && list.get(0) != null) {
				String str = list.get(0).toString();
				if (str != null && str.trim().length() > 0 )
					result = str;
	
			}

		} catch (Exception e) {
			log.error("Error in getGradePointForSubject..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
		return list;
	}
	
	public ArrayList<ExamStudentCCPA> getStudentCCPAForRank(int appliedYear,int courseId) throws Exception {
		Session session = null;
		try {
			List<ExamStudentSgpa> resultList;
			session = HibernateUtil.getSession();
            String result="passed";
			resultList = session.createQuery("from ExamStudentCCPA e " +
					"where e.appliedYear = "+appliedYear+" and e.course.id="+courseId+" and e.result='passed' order by e.ccpa desc ").list();
			
			session.flush();
			return new ArrayList(resultList);

		} catch (Exception e) {
			log.error("Error in getStudentSemesterResults..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	@Override
	public Map<Integer, String> getExtraLearning(Integer stuId, int courseId)
			throws Exception {
		Session session = null;
		Map<Integer, String> learningMap = new HashMap<Integer, String>();
		try{
			List toMap;
			List extraCreditMap;
			session=HibernateUtil.getSession();
			String str= "select pe.extraCreditActivityType.id,sl.mark from ServiceLearningMarksEntryBo sl join sl.programmeEntryBo pe join sl.student.admAppln.course c where sl.student.id= :studentId and c.id= :courseId";
			Query query = session.createQuery(str)
								 .setInteger("studentId", stuId)
								 .setInteger("courseId", courseId);
			toMap= query.list();
			String string = "from ExtraCreditActivityType r where r.isActive=1";
			Query query2= session.createQuery(string);
			extraCreditMap=query2.list();
			Double sum=0.0;
			Object[] row=null;
			for(int i=1;i<=extraCreditMap.size();i++){
				sum =0.0;
				if (toMap != null && toMap.size() > 0) {
					Iterator<Object[]> itr = toMap.iterator();
					while (itr.hasNext()) {
						row = (Object[]) itr.next();
						if((Integer)row[0]==i){
							sum = sum + new Double(row[1].toString());
						}
					}
				}
				learningMap.put(i,Double.toString(sum));

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return learningMap;
	}
	@Override
	public Map<Integer, String> getminMarks(int courseId,String year) 
											throws Exception {
		Session session = null;
		Map<Integer, String> minMarksMap = new HashMap<Integer, String>();
		try{
			List list = null;
			session=HibernateUtil.getSession();
			String str = "select a.extraCreditActivityType.id,a.minMark from ActivityLearning a where a.appliedYear= :appliedYear and a.course.id=:courseId";
			Query query = session.createQuery(str)
								  .setInteger("courseId", courseId)
								  .setString("appliedYear", year);
			list=query.list();
			if(list!=null && list.size()>0){
				Iterator<Object[]> iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					minMarksMap.put((Integer)objects[0], (String)objects[1]);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return minMarksMap;
	}
}
