package com.kp.cms.transactionsimpl.exam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.exam.ExamGracingSubjectMarksBo;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalRedoMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.bo.exam.SubjectRuleSettingsAttendance;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulAnsScript;
import com.kp.cms.bo.exam.SubjectRuleSettingsMulEvaluator;
import com.kp.cms.bo.exam.SubjectRuleSettingsSubInternal;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.NewUpdateProccessForm;
import com.kp.cms.helpers.exam.NewUpdateProccessHelper;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.exam.INewUpdateProccessTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
@SuppressWarnings("unchecked")
public class NewUpdateProccessTransactionImpl implements
		INewUpdateProccessTransaction {
	static DecimalFormat df = new DecimalFormat("#.##");
	/**
	 * Singleton object of NewUpdateProccessTransactionImpl
	 */
	private static volatile NewUpdateProccessTransactionImpl newUpdateProccessTransactionImpl = null;
	private static final Log log = LogFactory.getLog(NewUpdateProccessTransactionImpl.class);
	private NewUpdateProccessTransactionImpl() {
		
	}
	/**
	 * return singleton object of NewUpdateProccessTransactionImpl.
	 * @return
	 */
	public static NewUpdateProccessTransactionImpl getInstance() {
		if (newUpdateProccessTransactionImpl == null) {
			newUpdateProccessTransactionImpl = new NewUpdateProccessTransactionImpl();
		}
		return newUpdateProccessTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getDataByQuery(java.lang.String)
	 */
	public List getDataByQuery(String query) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#saveUpdateProcess(java.util.List)
	 */
	public boolean saveUpdateProcess(List<StudentSupplementaryImprovementApplication> boList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(boList!=null){
				Iterator<StudentSupplementaryImprovementApplication> tcIterator = boList.iterator();
				int count = 0;
				while(tcIterator.hasNext()){
					StudentSupplementaryImprovementApplication bo = tcIterator.next();
					int chance=0;
					Integer chan=(Integer)session.createQuery("select max(s.chance)" +
							" from StudentSupplementaryImprovementApplication s where s.student.id="+bo.getStudent().getId()+
							" and s.classes.id="+bo.getClasses().getId()+" and s.examDefinition.id!="+bo.getExamDefinition().getId()).uniqueResult();
					if(chan!=null){
						chance=chan;
					}
					chance+=1;
					bo.setChance(chance);
					session.save(bo);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getDataByStudentAndClassId(int, int)
	 */
	public List getDataByStudentAndClassId(int studentId, int classId,List<Integer> subList,int appliedYear) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		//studentId=2052;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createSQLQuery(" select EXAM_internal_mark_supplementary_details.id, classes.name as className, classes.id as classId, student.register_no, student.id StudentID, EXAM_definition.id ExamDefId, " +
					" EXAM_definition.year, EXAM_definition.month, EXAM_definition.name as examName, subject.id SubjectID, subject.name as subName, " +
					" EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.theory_total_attendance_mark, " +
					" EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark, EXAM_student_overall_internal_mark_details.practical_total_attendance_mark, " +
					" EXAM_internal_mark_supplementary_details.theory_total_sub_internal_mark as intTheorymark, EXAM_internal_mark_supplementary_details.theory_total_attendance_mark as intTheoryAttMark, " +
					" EXAM_internal_mark_supplementary_details.practical_total_sub_internal_mark  as intPracticalMark, EXAM_internal_mark_supplementary_details.practical_total_attendance_mark as intPracticalAttMark, " +
					" EXAM_student_final_mark_details.student_theory_marks as finalStudentTheoryMarks,  " +
					" EXAM_student_final_mark_details.student_practical_marks as finalStudentPracticalMarks, " +
					" subject_type.name as subTypeName, subject.is_theory_practical,EXAM_supplementary_improvement_application.is_improvement " +
					" from EXAM_student_overall_internal_mark_details " +
					" inner join student ON EXAM_student_overall_internal_mark_details.student_id = student.id " +
					" inner join classes ON EXAM_student_overall_internal_mark_details.class_id = classes.id " +
					" inner join subject ON EXAM_student_overall_internal_mark_details.subject_id = subject.id " +
					" inner join subject_type ON subject.subject_type_id = subject_type.id " +
					" left join EXAM_student_final_mark_details on EXAM_student_final_mark_details.student_id = student.id " +
					" and EXAM_student_final_mark_details.class_id = classes.id  " +
					" and EXAM_student_final_mark_details.subject_id = subject.id " +
					" and ((EXAM_student_final_mark_details.student_theory_marks is not null) or (EXAM_student_final_mark_details.student_practical_marks is not null)) " +
					" left join EXAM_internal_mark_supplementary_details on EXAM_internal_mark_supplementary_details.class_id = classes.id " +
					" and EXAM_internal_mark_supplementary_details.student_id = student.id " +
					" and EXAM_internal_mark_supplementary_details.subject_id = subject.id" +
					" and if(classes.course_id=18 and  EXAM_student_final_mark_details.exam_id is not null, EXAM_internal_mark_supplementary_details.exam_id = EXAM_student_final_mark_details.exam_id, true)"+
					/*last condition added for supli exams which will have both cia and ese always eg: bba, we cannot check it for other courses as there will not be always cia and ese for supli*/
					" left join EXAM_definition ON EXAM_definition.id = if(EXAM_student_final_mark_details.exam_id is null, " +
					" (if(EXAM_internal_mark_supplementary_details.exam_id is null, EXAM_student_overall_internal_mark_details.exam_id, EXAM_internal_mark_supplementary_details.exam_id)),  " +
					"                                                     EXAM_student_final_mark_details.exam_id) left join EXAM_supplementary_improvement_application on EXAM_supplementary_improvement_application.student_id=student.id" +
					" and EXAM_supplementary_improvement_application.exam_id=EXAM_definition.id" +
					" and EXAM_supplementary_improvement_application.subject_id=subject.id" +
					" and EXAM_supplementary_improvement_application.class_id=classes.id" +
					" and (EXAM_supplementary_improvement_application.is_appeared_theory=1 or EXAM_supplementary_improvement_application.is_appeared_practical=1)" +
					" where student.id = :studentId" +
					" and classes.id =:classId " +(appliedYear>=2010?"and subject.id in (:subList)":"")+
					" order by student.id, subject.id, EXAM_definition.year DESC, EXAM_definition.month DESC  ").setInteger("studentId",studentId).setInteger("classId",classId);
			if(appliedYear>=2010){
				if(subList.size()==0)
					subList.add(0);	
				
			selectedCandidatesQuery.setParameterList("subList", subList);
			}
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getMinMarksMap(com.kp.cms.to.attendance.ClassesTO)
	 */
	public Map<String, SubjectMarksTO> getMinMarksMap(ClassesTO to) throws Exception {
		Map<String, SubjectMarksTO> map=new HashMap<String, SubjectMarksTO>();

		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			String query="select s.subject.id," +
							   " s.theoryEseMinimumMark," +
							   " s.practicalEseMinimumMark," +
							   " s.finalTheoryInternalMinimumMark, " +
							   " s.finalPracticalInternalMinimumMark, " +
							   " s.theoryEseTheoryFinalMinimumMark, " +
							   " s.practicalEseTheoryFinalMinimumMark," +
							   " s.theoryEseTheoryFinalMaximumMark, " +
							   " s.practicalEseTheoryFinalMaximumMark" +
						" from SubjectRuleSettings s" +
						" where s.isActive=1" +
						" and s.schemeNo="+to.getTermNo()+
						" and s.course.id="+to.getCourseId()+
						" and s.academicYear="+to.getYear();

			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] obj= (Object[]) itr.next();
					SubjectMarksTO sto=new SubjectMarksTO();
					if(obj[0]!=null)
						sto.setSubjectId(Integer.parseInt(obj[0].toString()));
					if(!map.containsKey(sto.getSubjectId())){
						if(obj[1]!=null)
							sto.setTheoryRegMin(obj[1].toString());
						if(obj[2]!=null)
							sto.setPracticalRegMin(obj[2].toString());
						if(obj[3]!=null)
							sto.setTheoryIntMin(obj[3].toString());
						if(obj[4]!=null)
							sto.setPracticalIntMin(obj[4].toString());
						if(obj[5]!=null)
							sto.setFinalTheoryMin(obj[5].toString());
						if(obj[6]!=null)
							sto.setFinalPracticalMin(obj[6].toString());
						if(obj[7]!=null)
							sto.setFinalTheoryMarks(obj[7].toString());
						if(obj[8]!=null)
							sto.setFinalPracticalMarks(obj[8].toString());
						map.put(String.valueOf(sto.getSubjectId()),sto);
					}
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#deleteOldRecords(int, java.lang.String)
	 */
	public boolean deleteOldRecords(int id, String examId) throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
		    tx= session.beginTransaction();
			Query query = session.createQuery(" delete from StudentSupplementaryImprovementApplication s where s.examDefinition.id = " + examId + " and s.classes.id = " + id);
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getSubjectRuleSettings(int, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<Integer, SubjectRuleSettingsTO> getSubjectRuleSettings(
			int courseId, Integer year, Integer termNo) throws Exception {
		Map<Integer, SubjectRuleSettingsTO> map=new HashMap<Integer, SubjectRuleSettingsTO>();

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<SubjectRuleSettings> list =session.createQuery("from SubjectRuleSettings s where s.isActive=1" +
																" and s.academicYear=" +year+
																" and s.course.id=" +courseId+
																" and s.schemeNo=" +termNo+
																" and s.subject.isActive=1 and s.subject.coCurricularSubject=0").list();
			if(list!=null && !list.isEmpty()){
				Iterator<SubjectRuleSettings> itr=list.iterator();
				while (itr.hasNext()) {
					SubjectRuleSettings bo=itr.next();
					SubjectRuleSettingsTO sto=new SubjectRuleSettingsTO();
					sto.setSubjectId(bo.getSubject().getId());
					sto.setIsTheoryPractical(bo.getSubject().getIsTheoryPractical());
					
					if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")){
						if(bo.getFinalTheoryInternalIsAttendance()!=null){
							sto.setTheoryAttendanceCheck(bo.getFinalTheoryInternalIsAttendance());
						}
						if(bo.getFinalTheoryInternalIsAssignment()!=null)
							sto.setTheoryAssignmentCheck(bo.getFinalTheoryInternalIsAssignment());
						if(bo.getFinalTheoryInternalIsSubInternal()!=null)
							sto.setTheoryInteralCheck(bo.getFinalTheoryInternalIsSubInternal());
						if(bo.getSelectBestOfTheoryInternal()!=null)
							sto.setTheoryBest(bo.getSelectBestOfTheoryInternal());
					}
					else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("P")){
						if(bo.getFinalPracticalInternalIsAttendance()!=null){
							sto.setPracticalAttendanceCheck(bo.getFinalPracticalInternalIsAttendance());
						}
						if(bo.getFinalPracticalInternalIsAssignment()!=null)
							sto.setPracticalAssigntmentCheck(bo.getFinalPracticalInternalIsAssignment());
						if(bo.getFinalPracticalInternalIsSubInternal()!=null)
							sto.setPracticalInternalCheck(bo.getFinalPracticalInternalIsSubInternal());
						if(bo.getSelectBestOfPracticalInternal()!=null)
							sto.setPracticalBest(bo.getSelectBestOfPracticalInternal());
					}
					else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
						if(bo.getFinalTheoryInternalIsAttendance()!=null){
							sto.setTheoryAttendanceCheck(bo.getFinalTheoryInternalIsAttendance());
						}
						if(bo.getFinalTheoryInternalIsAssignment()!=null)
							sto.setTheoryAssignmentCheck(bo.getFinalTheoryInternalIsAssignment());
						if(bo.getSelectBestOfTheoryInternal()!=null)
							sto.setTheoryBest(bo.getSelectBestOfTheoryInternal());
						if(bo.getSelectBestOfPracticalInternal()!=null)
							sto.setPracticalBest(bo.getSelectBestOfPracticalInternal());
						
						if(bo.getFinalPracticalInternalIsAttendance()!=null){
							sto.setPracticalAttendanceCheck(bo.getFinalPracticalInternalIsAttendance());
						}
						if(bo.getFinalPracticalInternalIsAssignment()!=null)
							sto.setPracticalAssigntmentCheck(bo.getFinalPracticalInternalIsAssignment());
						if(bo.getFinalTheoryInternalIsSubInternal()!=null)
							sto.setTheoryInteralCheck(bo.getFinalTheoryInternalIsSubInternal());
						if(bo.getFinalPracticalInternalIsSubInternal()!=null)
							sto.setPracticalInternalCheck(bo.getFinalPracticalInternalIsSubInternal());
					}
					if(bo.getFinalTheoryInternalMinimumMark() != null) {
						sto.setTheoryCiaMinMark(bo.getFinalTheoryInternalMinimumMark().doubleValue());
					}
					if(bo.getFinalPracticalInternalMinimumMark() != null) {
						sto.setPracticalCiaMinMark(bo.getFinalPracticalInternalMinimumMark().doubleValue());
					}
					Set<SubjectRuleSettingsSubInternal> subSet=bo.getExamSubjectRuleSettingsSubInternals();
					if(subSet!=null && !subSet.isEmpty()){
						Iterator<SubjectRuleSettingsSubInternal> subItr=subSet.iterator();
						while (subItr.hasNext()) {
							SubjectRuleSettingsSubInternal subBo = (SubjectRuleSettingsSubInternal) subItr.next();
							if(subBo.getMaximumMark()!=null){
								String subType=subBo.getIsTheoryPractical().toString();
								if(subType.equalsIgnoreCase("t")){
									sto.setTheoryIndCheck(true);
								}
								if(subType.equalsIgnoreCase("p")){
									sto.setPracticalIndCheck(true);
								}
							}
						}
					}
					Set<SubjectRuleSettingsAttendance> attSet=bo.getExamSubjectRuleSettingsAttendances();
					if(attSet!=null && !attSet.isEmpty()){
						Iterator<SubjectRuleSettingsAttendance> attItr=attSet.iterator();
						while (attItr.hasNext()) {
							SubjectRuleSettingsAttendance attBo = attItr.next();
							String subType=attBo.getIsTheoryPractical().toString();
							if(subType.equalsIgnoreCase("t")){
								if(attBo.getIsCoCurricular()!=null)
									sto.setTheoryCoLeaveCheck(attBo.getIsCoCurricular());
								if(attBo.getIsLeave()!=null)
									sto.setTheoryLeaveCheck(attBo.getIsLeave());
								if(attBo.getAttendanceTypeId()!=null)
									sto.setTheoryAttTypeId(attBo.getAttendanceTypeId());
							}else if(subType.equalsIgnoreCase("p")){
								if(attBo.getIsCoCurricular()!=null)
									sto.setPracticalCoLeaveCheck(attBo.getIsCoCurricular());
								if(attBo.getIsLeave()!=null)
									sto.setTheoryLeaveCheck(attBo.getIsLeave());
								if(attBo.getAttendanceTypeId()!=null)
									sto.setPracticalAttTypeId(attBo.getAttendanceTypeId());
							}
						}
					}
					
					map.put(bo.getSubject().getId(),sto);
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	/**
	 * @param classId
	 * @param studentId
	 * @return
	 */
	@Override
	public Map<String, StudentAttendanceTO> getAttendanceForStudent(int classId, int studentId,List<Integer> subIdList , String batchYear,int termNo, String academicYear) throws Exception {
		Map<String, StudentAttendanceTO> map=new HashMap<String, StudentAttendanceTO>();

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			/*			List<Object[]> list =session.createQuery("select ast.attendance.subject.id,ast.attendance.attendanceType.id," +
					" sum(ast.attendance.hoursHeld), " +
					" sum(case when (ast.isPresent=1) then ast.attendance.hoursHeld else 0 end)," +
					" sum(case when (ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end), " +
					" sum(case when (ast.isOnLeave=1) then ast.attendance.hoursHeld else 0 end)" +
					" from AttendanceStudent ast" +
					" join ast.attendance.attendanceClasses ac" +
					" where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
					" and ac.classSchemewise.classes.id=" +classId+
					" and ast.attendance.subject.id in (:subList)"+
					" group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();
			 */			List<Object[]> list =null;
			 if(!batchYear.equalsIgnoreCase("2014")){
				 if((batchYear.equalsIgnoreCase("2015") && (termNo==1|| termNo==2 || termNo==3 ||termNo == 6) )|| (batchYear.equalsIgnoreCase("2016") && termNo==1 ) ){
						
					 list =session.createQuery("select ast.attendance.subject.id," +
							 " case when (ast.attendance.subject.isTheoryPractical='T' or ast.attendance.subject.isTheoryPractical='B') then 3 else 2 end," +
							 " sum(ast.attendance.hoursHeld), " +
							 " sum(case when (ast.isPresent=1) then ast.attendance.hoursHeld else 0 end)," +
							 " sum(case when (ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end), " +
							 " sum(case when (ast.isOnLeave=1) then ast.attendance.hoursHeld else 0 end)" +
							 " from AttendanceStudent ast" +
							 " join ast.attendance.attendanceClasses ac" +

							 " where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
							 " and ac.classSchemewise.classes.id=" +classId+
							 " and ast.attendance.subject.id in (:subList)"+
					 " group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();
				 }				 
				 else if(Integer.parseInt(academicYear)>2017){
						 list =session.createQuery("select ast.attendance.subject.id," +
								 " case when (ast.attendance.subject.isTheoryPractical='T' or ast.attendance.subject.isTheoryPractical='B') then 3 else 2 end," +
								 " sum(ast.attendance.hoursHeld)," +
								 " sum(case when (ast.isPresent = 1) then (ast.attendance.hoursHeld) else 0 end)," +
								 " sum(case when (ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end), " +
								 " sum(case when (ast.isOnLeave=1) then 1 else 0 end)" +
								 " from AttendanceStudent ast" +
								 " join ast.attendance.attendanceClasses ac" +

								 " where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
								 " and ac.classSchemewise.classes.id=" +classId+
								 " and ast.attendance.subject.id in (:subList)"+
						 " group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();
				 }
				 else if(Integer.parseInt(academicYear)==2017 && (termNo!=1 && termNo!=3 && termNo!=5)){
					 list =session.createQuery("select ast.attendance.subject.id," +
							 " case when (ast.attendance.subject.isTheoryPractical='T' or ast.attendance.subject.isTheoryPractical='B') then 3 else 2 end," +
							 " sum(ast.attendance.hoursHeld)," +
							 " sum(case when (ast.isPresent = 1) then (ast.attendance.hoursHeld) else 0 end)," +
							 " sum(case when (ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end), " +
							 " sum(case when (ast.isOnLeave=1) then 1 else 0 end)" +
							 " from AttendanceStudent ast" +
							 " join ast.attendance.attendanceClasses ac" +

							 " where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
							 " and ac.classSchemewise.classes.id=" +classId+
							 " and ast.attendance.subject.id in (:subList)"+
					 " group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();
				 }
				 else{
					 list =session.createQuery("select ast.attendance.subject.id," +
							 " case when (ast.attendance.subject.isTheoryPractical='T' or ast.attendance.subject.isTheoryPractical='B') then 3 else 2 end," +
							 " sum( case when (ast.attendance.hoursHeld is not null) then 1 else 0 end), " +
							 " sum(case when (ast.isPresent=1) then 1 else 0 end)," +
							 " sum(case when (ast.isCoCurricularLeave=1) then 1 else 0 end), " +
							 " sum(case when (ast.isOnLeave=1) then 1 else 0 end)" +
							 " from AttendanceStudent ast" +
							 " join ast.attendance.attendanceClasses ac" +

							 " where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
							 " and ac.classSchemewise.classes.id=" +classId+
							 " and ast.attendance.subject.id in (:subList)"+
					 " group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();
				 
				 }
			 }
			 else{
				 if(Integer.parseInt(academicYear)>2017){
						 list =session.createQuery("select ast.attendance.subject.id," +
								 " case when (ast.attendance.subject.isTheoryPractical='T' or ast.attendance.subject.isTheoryPractical='B') then 3 else 2 end," +
								 " sum(ast.attendance.hoursHeld)," +
								 " sum(case when (ast.isPresent = 1) then (ast.attendance.hoursHeld) else 0 end),"+
								 " sum(case when (ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end), " +
								 " sum(case when (ast.isOnLeave=1) then ast.attendance.hoursHeld else 0 end)" +
								 " from AttendanceStudent ast" +
								 " join ast.attendance.attendanceClasses ac" +

								 " where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
								 " and ac.classSchemewise.classes.id=" +classId+
								 " and ast.attendance.subject.id in (:subList)"+
						 " group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();
				 }
				 else if(Integer.parseInt(academicYear)==2017 && (termNo!=1 && termNo!=3 && termNo!=5)){
					 list =session.createQuery("select ast.attendance.subject.id," +
							 " case when (ast.attendance.subject.isTheoryPractical='T' or ast.attendance.subject.isTheoryPractical='B') then 3 else 2 end," +
							 " sum(ast.attendance.hoursHeld)," +
							 " sum(case when (ast.isPresent = 1) then (ast.attendance.hoursHeld) else 0 end),"+
							 " sum(case when (ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end), " +
							 " sum(case when (ast.isOnLeave=1) then ast.attendance.hoursHeld else 0 end)" +
							 " from AttendanceStudent ast" +
							 " join ast.attendance.attendanceClasses ac" +

							 " where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
							 " and ac.classSchemewise.classes.id=" +classId+
							 " and ast.attendance.subject.id in (:subList)"+
					 " group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();
				 }
				 else{
					 list =session.createQuery("select ast.attendance.subject.id," +
							 " case when (ast.attendance.subject.isTheoryPractical='T' or ast.attendance.subject.isTheoryPractical='B') then 3 else 2 end," +
							 " sum(ast.attendance.hoursHeld), " +
							 " sum(case when (ast.isPresent=1) then ast.attendance.hoursHeld else 0 end)," +
							 " sum(case when (ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end), " +
							 " sum(case when (ast.isOnLeave=1) then ast.attendance.hoursHeld else 0 end)" +
							 " from AttendanceStudent ast" +
							 " join ast.attendance.attendanceClasses ac" +
	
							 " where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
							 " and ac.classSchemewise.classes.id=" +classId+
							 " and ast.attendance.subject.id in (:subList)"+
					 " group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();
				 }
				
			 }
			 if(list!=null && !list.isEmpty()){
				 Iterator<Object[]> itr=list.iterator();
				 while (itr.hasNext()) {
					 Object[] bo=itr.next();
					 StudentAttendanceTO sto=new StudentAttendanceTO();
					 if(bo[0]!=null)
						 sto.setSubjectId(Integer.parseInt(bo[0].toString()));
					 if(bo[1]!=null)
						 sto.setAttTypeId(Integer.parseInt(bo[1].toString()));
					 if(bo[2]!=null)
						 sto.setSubjectHoursHeld(Integer.parseInt(bo[2].toString()));
					 if(bo[3]!=null)
						 sto.setPresentHoursAtt(Integer.parseInt(bo[3].toString()));
					 if(bo[4]!=null)
						 sto.setCoLeaveHoursAtt(Integer.parseInt(bo[4].toString()));
					 if(bo[5]!=null)
						 sto.setLeaveHoursAtt(Integer.parseInt(bo[5].toString()));

					 map.put(sto.getSubjectId()+"_"+sto.getAttTypeId(),sto);
				 }
			 }
			 return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public double getAttendanceMarksForPercentage(int courseId, Integer subId,
			float percentage) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		double marks = 0;
		List l=session.createQuery("from ExamSubCoursewiseAttendanceMarksBO e where e.subjectId = "+subId+" and e.courseId="+courseId).list();
		Query query=null;
		if(l!=null && !l.isEmpty()){
			query=session.createQuery( "select e.attendanceMarks"
					+ " from ExamSubCoursewiseAttendanceMarksBO e"
					+ " where e.subjectId = :subId and e.courseId=:courseId"
					+ " and e.fromPrcntgAttndnc <= :totalCalculation"
					+ " and e.toPrcntgAttndnc >= :totalCalculation").setParameter("subId", subId).setParameter("courseId", courseId).setParameter("totalCalculation",new BigDecimal(percentage));
		}else{
			query=session.createQuery("select a.marks"
					+ " from ExamAttendanceMarksBO a"
					+ " where a.courseId = :courseId and a.fromPercentage <= :totalCalculation"
					+ " and a.toPercentage >= :totalCalculation").setParameter("courseId", courseId).setParameter("totalCalculation",new BigDecimal(percentage));
		}

		List list = query.list();
		if (list != null && list.size() > 0) {
			marks = new Double(list.get(0).toString());
		}
		return Double.parseDouble(new DecimalFormat("##.##").format(marks));
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getAssignmentMarksForStudent(int, int)
	 */
	@Override
	public Map<Integer, StudentAttendanceTO> getAssignmentMarksForStudent(
			int courseId, int studentId,List<Integer> subIdList) throws Exception {
		Map<Integer, StudentAttendanceTO> map=new HashMap<Integer, StudentAttendanceTO>();

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<Object[]> list =session.createQuery("select e.subjectId,sum(e.theoryMarks), sum(e.practicalMarks)" +
					" from ExamAssignOverallMarksBO e where e.courseId=" +courseId+
					" and e.studentId=" +studentId+" and e.subjectId in (:subList)"+
					" group by e.subjectId").setParameterList("subList", subIdList).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] bo=itr.next();
					StudentAttendanceTO sto=new StudentAttendanceTO();
					if(bo[0]!=null)
					sto.setSubjectId(Integer.parseInt(bo[0].toString()));
					if(bo[1]!=null)
					sto.setTheoryAssMarks(Integer.parseInt(bo[1].toString()));
					if(bo[2]!=null)
					sto.setPracticalAssMarks(Integer.parseInt(bo[2].toString()));
					
					map.put(sto.getSubjectId(),sto);
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getInternalExamId(int)
	 */
	public List<Integer> getInternalExamId(int examId) throws Exception {
		List<Integer> list=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			list=session.createQuery("select e.id from ExamDefinition e where e.id in ( select e.internalExamNameId from ExamInternalExamDetailsBO e where e.examId=:examId )").setParameter("examId",examId).list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getStudentMarksForSubject(java.lang.Integer, int, java.util.List, com.kp.cms.to.attendance.ClassesTO, java.lang.String, int, boolean)
	 */
	public double getStudentMarksForSubject(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm)
			throws Exception {
		List<Object[]> list=null;
		double marks=0;
		Session session = null;
		boolean isGracing=false;
		try {
			session = HibernateUtil.getSession();
			String query="from MarksEntryDetails md, SubjectRuleSettings s " +
					" left join s.examSubjectRuleSettingsSubInternals subInt" +
					" where md.subject.id=" +subId+
					" and md.marksEntry.student.id="+studentId+" and md.marksEntry.exam.id in (:examId)" +
					" and md.subject.id=s.subject.id and s.course.id="+to.getCourseId()+"" +
					" and s.academicYear="+to.getYear()+" and s.schemeNo="+to.getTermNo()+
					" and s.isActive=1 and subInt.isActive=1 and subInt.isTheoryPractical='" +subType+
					"' and subInt.internalExamTypeId= md.marksEntry.exam.internalExamTypeBO.id" +
					" group by md.marksEntry.exam.id,s.subject.id";
			if(subType.equalsIgnoreCase("t")){
				query="select md.marksEntry.exam.id, cast(max(md.theoryMarks) as string) as marks," +
					  " subInt.enteredMaxMark as subIntEntMaxMarks," +
					  " subInt.maximumMark as subIntMaxMarks, s.theoryIntEntryMaxMarksTotal, s.theoryIntMaxMarksTotal," +
					  " (cast(max(md.theoryMarks) as string)/ subInt.enteredMaxMark)*100 as percentage, md.isGracing "+query+
					  " order by (cast(max(md.theoryMarks) as string)/ subInt.enteredMaxMark)*100 DESC";
			}else if(subType.equalsIgnoreCase("p")){
				query="select md.marksEntry.exam.id," +
					  " cast(max(md.practicalMarks) as string) as marks," +
					  " subInt.enteredMaxMark as subIntEntMaxMarks, subInt.maximumMark as subIntMaxMarks," +
					  " s.practicalIntEntryMaxMarksTotal, s.practicalIntMaxMarksTotal," +
					  " (cast(max(md.practicalMarks) as string)/ subInt.enteredMaxMark)*100 as percentage, md.isGracing "+query+
					  " order by (cast(max(md.practicalMarks) as string)/ subInt.enteredMaxMark)*100 DESC";
				
			}
			
			list=session.createQuery(query).setParameterList("examId",intExamId).list();
			if(list!=null && !list.isEmpty()){
				if(limit==0)
					limit=list.size();
				if(isInd){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						Object[] obj = (Object[]) iterator.next();
						double totalMarks=0;
						double enterdMaxMarks=0;
						double maxMarks=0;
						@SuppressWarnings("unused")
						String totmarks="";
						if(obj[1]!=null && !obj[1].toString().trim().isEmpty() && !obj[1].toString().equalsIgnoreCase("AB")&& !obj[1].toString().equalsIgnoreCase("WithHeld"))
							totalMarks=Double.parseDouble(obj[1].toString());
						if(obj[1].toString().equalsIgnoreCase("WithHeld"))
							totmarks=obj[1].toString();
						if(obj[2]!=null && !obj[2].toString().trim().isEmpty())
							enterdMaxMarks=Double.parseDouble(obj[2].toString());
						if(obj[3]!=null && !obj[3].toString().trim().isEmpty())
							maxMarks=Double.parseDouble(obj[3].toString());
						//marks=marks+((totalMarks/enterdMaxMarks)*maxMarks);
						if (enterdMaxMarks == 80.0) {
                            if (totalMarks >= 1.0 && totalMarks <= 7.0) {
                                marks=marks+ 0.5;
                            }
                            else if (totalMarks >= 8.0 && totalMarks <= 15.0) {
                            	marks=marks+1;
                            }
                            else if (totalMarks >= 16.0 && totalMarks <= 23.0) {
                            	marks=marks+ 1.5;
                            }
                            else if (totalMarks >= 24.0 && totalMarks <= 31.0) {
                            	marks=marks+ 2.0;
                            }
                            else if (totalMarks >= 32.0 && totalMarks <= 39.0) {
                            	marks=marks+ 2.5;
                            }
                            else if (totalMarks >= 40.0 && totalMarks <= 47.0) {
                            	marks=marks+ 3.0;
                            }
                            else if (totalMarks >= 48.0 && totalMarks <= 55.0) {
                            	marks=marks+ 3.5;
                            }
                            else if (totalMarks >= 56.0 && totalMarks <= 63.0) {
                                marks += 4.0;
                            }
                            else if (totalMarks >= 64.0 && totalMarks <= 71.0) {
                            	marks=marks+ 4.5;
                            }
                            else if (totalMarks >= 72.0 && totalMarks <= 80.0) {
                            	marks=marks+ 5.0;
                            }
                        }
                        else if (enterdMaxMarks == 60.0) {
                            if (totalMarks >= 1.0 && totalMarks <= 7.0) {
                                marks += 0.5;
                            }
                            else if (totalMarks >= 8.0 && totalMarks <= 14.0) {
                                ++marks;
                            }
                            else if (totalMarks >= 15.0 && totalMarks <= 22.0) {
                                marks += 1.5;
                            }
                            else if (totalMarks >= 23.0 && totalMarks <= 29.0) {
                                marks += 2.0;
                            }
                            else if (totalMarks >= 30.0 && totalMarks <= 37.0) {
                                marks += 2.5;
                            }
                            else if (totalMarks >= 38.0 && totalMarks <= 44.0) {
                                marks += 3.0;
                            }
                            else if (totalMarks >= 45.0 && totalMarks <= 52.0) {
                                marks += 3.5;
                            }
                            else if (totalMarks >= 53.0 && totalMarks <= 60.0) {
                            	marks=marks+ 4.0;
                            }
                        }
                        else {
                            marks += totalMarks / enterdMaxMarks * maxMarks;
                        }
						// /*code added by chandra
						if(obj[7]!=null && !obj[7].toString().trim().isEmpty()){
							if(obj[7].toString().trim().equalsIgnoreCase("true")){
								isGracing=true;
							}else if(obj[7].toString().trim().equalsIgnoreCase("false")){
								if(isGracing){
									isGracing=true;
								}else{
									isGracing=false;
								}
							}
						}
						// */
							
					}
					Math.round(marks);
					if(!NewUpdateProccessHelper.avoidExamIds.contains(examId))
					{
						//Math.round(Double.parseDouble(df.format(marks)));
						
					}
					else
					{
						//Math.round(marks);
					}
					
				}else{
					double totalMarks=0;
					double enterdMaxMarks=0;
					double maxMarks=0;
					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						Object[] obj = (Object[]) iterator.next();
						if(obj[1]!=null && !obj[1].toString().trim().isEmpty()&& !obj[1].toString().equalsIgnoreCase("AB") && !obj[1].toString().equalsIgnoreCase("WithHeld"))
							totalMarks=totalMarks+Double.parseDouble(obj[1].toString());
						if(obj[1].toString().equalsIgnoreCase("WithHeld"))
							totalMarks=Double.parseDouble(obj[1].toString());
						if(obj[4]!=null && !obj[4].toString().trim().isEmpty())
							enterdMaxMarks=Double.parseDouble(obj[4].toString());
						if(obj[5]!=null && !obj[5].toString().trim().isEmpty())
							maxMarks=Double.parseDouble(obj[5].toString());
						// /*code added by chandra
						if(obj[7]!=null && !obj[7].toString().trim().isEmpty()){
							if(obj[7].toString().trim().equalsIgnoreCase("true")){
								isGracing=true;
							}else if(obj[7].toString().trim().equalsIgnoreCase("false")){
								if(isGracing){
									isGracing=true;
								}else{
									isGracing=false;
								}
							}
						}
						// */
					}
					if (enterdMaxMarks == 80.0) {
                        if (totalMarks >= 1.0 && totalMarks <= 7.0) {
                        	marks=marks+ 0.5;
                        }
                        else if (totalMarks >= 8.0 && totalMarks <= 15.0) {
                        	marks=marks+1;
                        }
                        else if (totalMarks >= 16.0 && totalMarks <= 23.0) {
                        	marks=marks+ 1.5;
                        }
                        else if (totalMarks >= 24.0 && totalMarks <= 31.0) {
                        	marks=marks+ 2.0;
                        }
                        else if (totalMarks >= 32.0 && totalMarks <= 39.0) {
                        	marks=marks+ 2.5;
                        }
                        else if (totalMarks >= 40.0 && totalMarks <= 47.0) {
                        	marks=marks+ 3.0;
                        }
                        else if (totalMarks >= 48.0 && totalMarks <= 55.0) {
                        	marks=marks+ 3.5;
                        }
                        else if (totalMarks >= 56.0 && totalMarks <= 63.0) {
                        	marks=marks+ 4.0;
                        }
                        else if (totalMarks >= 64.0 && totalMarks <= 71.0) {
                        	marks=marks+ 4.5;
                        }
                        else if (totalMarks >= 72.0 && totalMarks <= 80.0) {
                        	marks=marks+ 5.0;
                        }
                    }
                    else if (enterdMaxMarks == 60.0) {
                        if (totalMarks >= 1.0 && totalMarks <= 7.0) {
                        	marks=marks+ 0.5;
                        }
                        else if (totalMarks >= 8.0 && totalMarks <= 14.0) {
                        	marks=marks+1;
                        }
                        else if (totalMarks >= 15.0 && totalMarks <= 22.0) {
                        	marks=marks+ 1.5;
                        }
                        else if (totalMarks >= 23.0 && totalMarks <= 29.0) {
                        	marks=marks+ 2.0;
                        }
                        else if (totalMarks >= 30.0 && totalMarks <= 37.0) {
                        	marks=marks+ 2.5;
                        }
                        else if (totalMarks >= 38.0 && totalMarks <= 44.0) {
                        	marks=marks+ 3.0;
                        }
                        else if (totalMarks >= 45.0 && totalMarks <= 52.0) {
                        	marks=marks+ 3.5;
                        }
                        else if (totalMarks >= 53.0 && totalMarks <= 60.0) {
                        	marks=marks+ 4.0;
                        }
                    }
                    else {
                        marks += totalMarks / enterdMaxMarks * maxMarks;
                    }
					marks=(totalMarks/enterdMaxMarks)*maxMarks;
					Math.round(marks);
					if(!NewUpdateProccessHelper.avoidExamIds.contains(examId))
					{
						//Math.round(Double.parseDouble(df.format(marks)));
						Double.parseDouble(new DecimalFormat("##.##").format(marks));
					}
					else
					{
						//Math.round(marks);
						Double.parseDouble(new DecimalFormat("##.##").format(marks));
					}
					
				}
			}
			newUpdateProccessForm.setIsGracing(isGracing);
			if(!NewUpdateProccessHelper.avoidExamIds.contains(examId))
				//return marks;//Math.round(Double.parseDouble(df.format(marks)));
			    return Double.parseDouble(new DecimalFormat("##.##").format(marks));
			else
				//return marks;//Math.round(marks);
			    return Double.parseDouble(new DecimalFormat("##.##").format(marks));
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	
	public List<MarksEntryDetails> getStudentMarksForSubjectNew(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm)
	throws Exception {
		List<MarksEntryDetails> list=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from MarksEntryDetails md, SubjectRuleSettings s " +
					" left join s.examSubjectRuleSettingsSubInternals subInt" +
					" where md.subject.id=" +subId+
					" and md.marksEntry.student.id="+studentId+" and md.marksEntry.exam.id in (:examId)" +
					" and md.subject.id=s.subject.id and s.course.id="+to.getCourseId()+"" +
					" and s.academicYear="+to.getYear()+" and s.schemeNo="+to.getTermNo()+
					" and s.isActive=1 and subInt.isActive=1 and subInt.isTheoryPractical='" +subType+
					"' and subInt.internalExamTypeId= md.marksEntry.exam.internalExamTypeBO.id" +
					" group by md.marksEntry.exam.id,s.subject.id";
			if(subType.equalsIgnoreCase("t")){
				query="select md.marksEntry.exam.id, cast(max(md.theoryMarks) as string) as marks," +
					  " subInt.enteredMaxMark as subIntEntMaxMarks," +
					  " subInt.maximumMark as subIntMaxMarks, s.theoryIntEntryMaxMarksTotal, s.theoryIntMaxMarksTotal," +
					  " (cast(max(md.theoryMarks) as string)/ subInt.enteredMaxMark)*100 as percentage, md.isGracing "+query+
					  " order by (cast(max(md.theoryMarks) as string)/ subInt.enteredMaxMark)*100 DESC";
			}else if(subType.equalsIgnoreCase("p")){
				query="select md.marksEntry.exam.id," +
					  " cast(max(md.practicalMarks) as string) as marks," +
					  " subInt.enteredMaxMark as subIntEntMaxMarks, subInt.maximumMark as subIntMaxMarks," +
					  " s.practicalIntEntryMaxMarksTotal, s.practicalIntMaxMarksTotal," +
					  " (cast(max(md.practicalMarks) as string)/ subInt.enteredMaxMark)*100 as percentage, md.isGracing "+query+
					  " order by (cast(max(md.practicalMarks) as string)/ subInt.enteredMaxMark)*100 DESC";
				
			}
			
			list=session.createQuery(query).setParameterList("examId",intExamId).list();
							
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#saveInternalOverAll(java.util.List)
	 */
	@Override
	public boolean saveInternalOverAll( List<StudentOverallInternalMarkDetails> boList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		StudentOverallInternalMarkDetails bo;
		try {
			//session = HibernateUtil.getSession();
			session = HibernateUtil.getSession();
			session.close();
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<StudentOverallInternalMarkDetails> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();
				StudentOverallInternalMarkDetails bo1=(StudentOverallInternalMarkDetails)session.createQuery("from StudentOverallInternalMarkDetails s" +
						" where s.classes.id="+bo.getClasses().getId()+" and s.student.id="+bo.getStudent().getId()+
						" and s.exam.id="+bo.getExam().getId()+" and s.subject.id="+bo.getSubject().getId()).uniqueResult();
				if(bo1!=null){
					bo1.setTheoryTotalAssignmentMarks(bo.getTheoryTotalAssignmentMarks());
					bo1.setTheoryTotalAttendenceMarks(bo.getTheoryTotalAttendenceMarks());
					bo1.setTheoryTotalSubInternalMarks(bo.getTheoryTotalSubInternalMarks());
					bo1.setTheoryBeforeGracing(bo.getTheoryBeforeGracing());
					bo1.setTheoryTotalMarks(bo.getTheoryTotalMarks());
					bo1.setPracticalTotalAssignmentMarks(bo.getPracticalTotalAssignmentMarks());
					bo1.setPracticalTotalAttendenceMarks(bo.getPracticalTotalAttendenceMarks());
					bo1.setPracticalTotalSubInternalMarks(bo.getPracticalTotalSubInternalMarks());
					bo1.setPracticalTotalMarks(bo.getPracticalTotalMarks());
					bo1.setLastModifiedDate(new Date());
					bo1.setIsGracing(bo.getIsGracing());
					session.merge(bo1);
				}else{
					session.save(bo);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getSubjectRuleSettingsForRegularOverAll(int, java.lang.Integer, java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getSubjectRuleSettingsForRegularOverAll(int, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<Integer, SubjectRuleSettingsTO> getSubjectRuleSettingsForRegularOverAll(
			int courseId, Integer year, Integer termNo) throws Exception {
		Map<Integer, SubjectRuleSettingsTO> map=new HashMap<Integer, SubjectRuleSettingsTO>();

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<SubjectRuleSettings> list =session.createQuery("from SubjectRuleSettings s where s.isActive=1" +
																" and s.academicYear=" +year+
																" and s.course.id=" +courseId+
																" and s.schemeNo=" +termNo+
																//" and s.subject.id=987"+
																" and s.subject.isActive=1").list();
			if(list!=null && !list.isEmpty()){
				Iterator<SubjectRuleSettings> itr=list.iterator();
				while (itr.hasNext()) {
					SubjectRuleSettings bo=itr.next();
					SubjectRuleSettingsTO sto=new SubjectRuleSettingsTO();
					sto.setSubjectId(bo.getSubject().getId());
					sto.setSubjectName(bo.getSubject().getCode());
					sto.setIsTheoryPractical(bo.getSubject().getIsTheoryPractical());
					if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")){
						if(bo.getTheoryEseIsRegular()!=null)
							sto.setTheoryRegularCheck(bo.getTheoryEseIsRegular());
						if(bo.getTheoryEseIsMultipleEvaluator()!=null)
							sto.setTheoryEvalCheck(bo.getTheoryEseIsMultipleEvaluator());
						if(bo.getTheoryEseIsMultipleAnswerScript()!=null)
							sto.setTheoryAnsCheck(bo.getTheoryEseIsMultipleAnswerScript());
						if(bo.getTheoryEseEnteredMaxMark()!=null)
							sto.setTheoryEseEnteredMaxMarks(bo.getTheoryEseEnteredMaxMark().doubleValue());
						if(bo.getTheoryEseMaximumMark()!=null)
							sto.setTheoryEseMaxMarks(bo.getTheoryEseMaximumMark().doubleValue());
						if(bo.getTheoryEseMinimumMark()!=null)
							sto.setTheoryEseMinMarks(bo.getTheoryEseMinimumMark().doubleValue());
						
					}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("P")){
						if(bo.getPracticalEseIsRegular()!=null)
							sto.setPracticalRegularCheck(bo.getPracticalEseIsRegular());
						if(bo.getPracticalEseIsMultipleEvaluator()!=null)
							sto.setPracticalEvalCheck(bo.getPracticalEseIsMultipleEvaluator());
						if(bo.getPracticalEseIsMultipleAnswerScript()!=null)
							sto.setPracticalAnsCheck(bo.getPracticalEseIsMultipleAnswerScript());
						
						if(bo.getPracticalEseEnteredMaxMark()!=null)
							sto.setPracticalEseEnteredMaxMarks(bo.getPracticalEseEnteredMaxMark().doubleValue());
						if(bo.getPracticalEseMaximumMark()!=null)
							sto.setPracticalEseMaxMarks(bo.getPracticalEseMaximumMark().doubleValue());
						if(bo.getPracticalEseMinimumMark()!=null)
							sto.setPracticalEseMinMarks(bo.getPracticalEseMinimumMark().doubleValue());
						
					}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
						if(bo.getTheoryEseIsRegular()!=null)
							sto.setTheoryRegularCheck(bo.getTheoryEseIsRegular());
						if(bo.getTheoryEseIsMultipleEvaluator()!=null)
							sto.setTheoryEvalCheck(bo.getTheoryEseIsMultipleEvaluator());
						if(bo.getTheoryEseIsMultipleAnswerScript()!=null)
							sto.setTheoryAnsCheck(bo.getTheoryEseIsMultipleAnswerScript());
						if(bo.getPracticalEseIsRegular()!=null)
							sto.setPracticalRegularCheck(bo.getPracticalEseIsRegular());
						if(bo.getPracticalEseIsMultipleEvaluator()!=null)
							sto.setPracticalEvalCheck(bo.getPracticalEseIsMultipleEvaluator());
						if(bo.getPracticalEseIsMultipleAnswerScript()!=null)
							sto.setPracticalAnsCheck(bo.getPracticalEseIsMultipleAnswerScript());
						
						if(bo.getTheoryEseEnteredMaxMark()!=null)
							sto.setTheoryEseEnteredMaxMarks(bo.getTheoryEseEnteredMaxMark().doubleValue());
						if(bo.getTheoryEseMaximumMark()!=null)
							sto.setTheoryEseMaxMarks(bo.getTheoryEseMaximumMark().doubleValue());
						if(bo.getTheoryEseMinimumMark()!=null)
							sto.setTheoryEseMinMarks(bo.getTheoryEseMinimumMark().doubleValue());
						if(bo.getPracticalEseEnteredMaxMark()!=null)
							sto.setPracticalEseEnteredMaxMarks(bo.getPracticalEseEnteredMaxMark().doubleValue());
						if(bo.getPracticalEseMaximumMark()!=null)
							sto.setPracticalEseMaxMarks(bo.getPracticalEseMaximumMark().doubleValue());
						if(bo.getPracticalEseMinimumMark()!=null)
							sto.setPracticalEseMinMarks(bo.getPracticalEseMinimumMark().doubleValue());
						
					}
					Set<SubjectRuleSettingsMulEvaluator> evalSet=bo.getExamSubjectRuleSettingsMulEvaluators();
					if(evalSet!=null && !evalSet.isEmpty()){
						Iterator<SubjectRuleSettingsMulEvaluator> evalItr=evalSet.iterator();
						while (evalItr.hasNext()) {
							SubjectRuleSettingsMulEvaluator eval = evalItr .next();
							if(eval.getIsActive()){
								if(eval.getIsTheoryPractical().equals('t')){
									sto.setTheoryTypeOfEval(eval.getTypeOfEvaluation());
									if(eval.getNoOfEvaluations()!=null)
										sto.setTheoryNoOfEval(eval.getNoOfEvaluations());
								}
								if(eval.getIsTheoryPractical().equals('p')){
									sto.setPracticalTypeOfEval(eval.getTypeOfEvaluation());
									if(eval.getNoOfEvaluations()!=null)
										sto.setPracticalNoOfEval(eval.getNoOfEvaluations());
								}
								if(eval.getIsReviewer()!= null && eval.getIsReviewer()){
									sto.setReviewerId(eval.getEvaluatorId());
								}
							}
						}
					}
					Set<SubjectRuleSettingsMulAnsScript> ansScripts=bo.getExamSubjectRuleSettingsMulAnsScripts();
					if(ansScripts!=null && !ansScripts.isEmpty()){
						Iterator<SubjectRuleSettingsMulAnsScript> ansItr=ansScripts.iterator();
						while (ansItr.hasNext()) {
							SubjectRuleSettingsMulAnsScript ans = ansItr .next();
							if(ans.getIsActive()){
								if(ans.getIsTheoryPractical().equals('t')){
									sto.setTheoryNoOfAns(sto.getTheoryNoOfAns()+1);
								}
								if(ans.getIsTheoryPractical().equals('p')){
									sto.setPracticalNoOfAns(sto.getPracticalNoOfAns()+1);
								}
							}
							
						}
					}
					map.put(bo.getSubject().getId(),sto);
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#saveRegularOverAll(java.util.List)
	 */
	@Override
	public boolean saveRegularOverAll(List<StudentFinalMarkDetails> boList)
			throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		StudentFinalMarkDetails bo;
		try {
			session = HibernateUtil.getSession();
			session.close();
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<StudentFinalMarkDetails> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();
				StudentFinalMarkDetails bo1=(StudentFinalMarkDetails)session.createQuery("from StudentFinalMarkDetails s where s.student.id=" +bo.getStudent().getId()+
						" and s.classes.id=" +bo.getClasses().getId()+
						" and s.exam.id= " +bo.getExam().getId()+
						"and s.subject.id="+bo.getSubject().getId()).uniqueResult();
				if(bo1!=null){
					bo1.setLastModifiedDate(new Date());
					bo1.setModifiedBy(bo.getModifiedBy());
					bo1.setStudentTheoryMarks(bo.getStudentTheoryMarks());
					bo1.setStudentTheoryMarksBeforeGrace(bo.getStudentTheoryMarksBeforeGrace());
					bo1.setStudentPracticalMarks(bo.getStudentPracticalMarks());
					bo1.setPassOrFail(bo.getPassOrFail());
					bo1.setSubjectPracticalMark(bo.getSubjectPracticalMark());
					bo1.setSubjectTheoryMark(bo.getSubjectTheoryMark());
					bo1.setIsGracing(bo.getIsGracing());
					bo1.setIsRevaluationModeration(bo.getIsRevaluationModeration());
					session.update(bo1);
				}else{
					session.save(bo);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getStudentRegularMarks(int, java.util.List, int)	 
	 */
	public Map<Integer, List<StudentMarksTO>> getStudentRegularMarks( int studentId, List<Integer> subjectIdList, int examId, 
			int classId, NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		Map<Integer,List<StudentMarksTO>> stuMarksMap=new HashMap<Integer, List<StudentMarksTO>>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			
			StringBuilder sql = new StringBuilder("select subject.id,max(ed.theory_marks),max(ed.practical_marks), ");
			sql.append(" entry.evaluator_type_id,entry.answer_script_type,ed.is_theory_secured,");
			sql.append(" verification.vmarks,subject.name,student.register_no,subject.code,EXAM_definition.academic_year as year,ed.graced_mark");
			sql.append(" from EXAM_marks_entry_details ed ");
			sql.append(" JOIN EXAM_marks_entry entry ON ed.marks_entry_id = entry.id ");
			sql.append(" JOIN classes ON entry.class_id = classes.id ");
			sql.append(" JOIN student ON entry.student_id = student.id ");
			sql.append(" JOIN subject ON ed.subject_id = subject.id ");
			sql.append(" JOIN EXAM_definition ON entry.exam_id = EXAM_definition.id ");
			sql.append(" LEFT JOIN EXAM_marks_verification_details verification ON ");			
			sql.append(" (verification.exam_id = EXAM_definition.id AND verification.student_id = student.id AND verification.subject_id = subject.id ");
			sql.append(" AND if(verification.evaluator_type_id is null,0,verification.evaluator_type_id) = if(entry.evaluator_type_id is null,0,entry.evaluator_type_id) ");
			sql.append(" AND if(verification.answer_script_type is null,0,verification.answer_script_type) = if(entry.answer_script_type is null,0,entry.answer_script_type)) ");
			sql.append(" WHERE student.id=:studentId");
			sql.append(" AND subject.id in(:subList) ");
			sql.append(" AND EXAM_definition.id=:examId");
			sql.append(" AND (classes.id is null OR classes.id =:classId)");
			sql.append(" GROUP BY entry.evaluator_type_id,entry.answer_script_type,subject.id ");
			sql.append(" ORDER BY subject.id");
			
		
			List<Object[]> list = session.createSQLQuery(sql.toString())
										 .setInteger("studentId", studentId)
										 .setInteger("examId", examId)
										 .setInteger("classId", classId)
										 .setParameterList("subList",subjectIdList).list();			
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				String errMsg=newUpdateProccessForm.getErrorMessage();
				while (itr.hasNext()) {
					Object[] bo =itr .next();
					List<StudentMarksTO> markList=null;
					if(stuMarksMap.containsKey(Integer.parseInt(bo[0].toString()))){
						markList=stuMarksMap.remove(Integer.parseInt(bo[0].toString()));
					}else{
						markList=new ArrayList<StudentMarksTO>();
					}
					StudentMarksTO to=new StudentMarksTO();
					if(bo[1]!=null && !bo[1].toString().trim().isEmpty()){
						to.setTheoryMarks(bo[1].toString().trim());
					}
					if(bo[2]!=null && !bo[2].toString().trim().isEmpty())
						to.setPracticalMarks(bo[2].toString().trim());
					if(bo[3]!=null)
						to.setEvaId(bo[3].toString());
					if(bo[4]!=null)
						to.setAnsId(bo[4].toString());
					if(bo[1]!=null && !bo[1].toString().trim().isEmpty() && !bo[1].toString().equalsIgnoreCase("AA") && !bo[1].toString().equalsIgnoreCase("NP")){
						if(bo[10] != null && !bo[10].toString().isEmpty() && Integer.parseInt(bo[10].toString())>=2012){
							to.setTheoryMarks(bo[1].toString().trim());
							if(bo[2]!=null && !bo[2].toString().trim().isEmpty())
								to.setPracticalMarks(bo[2].toString().trim());
							if(bo[3]!=null)
								to.setEvaId(bo[3].toString());
							if(bo[4]!=null)
								to.setAnsId(bo[4].toString());
							if(bo[5]!=null && !bo[5].toString().trim().isEmpty()){
								if(bo[5].toString().equalsIgnoreCase("true")){
									if(bo[6]!=null && !bo[6].toString().trim().isEmpty()){
										if(!to.getTheoryMarks().equalsIgnoreCase(bo[6].toString().trim())){
											if(errMsg.isEmpty()){
												errMsg = errMsg + "There are Marks Mismatch for the student(s) ";
											}
											errMsg = errMsg + ", " +bo[8].toString() + " - "+bo[7].toString()+" ("+bo[9].toString()+")";
										}
									}else if(to.getTheoryMarks() != null && bo[6] == null){
										if(errMsg.isEmpty()){
											errMsg = errMsg + "There are Marks Mismatch for the student(s) ";
										}
										errMsg = errMsg + ", " +bo[8].toString() + " - "+bo[7].toString()+" ("+bo[9].toString()+")";
									}
								}
							}
						}
						
					}
					// /*code added by chandra
					if(bo[11]!=null && !bo[11].toString().trim().isEmpty()){
						if(bo[11].toString().trim().equalsIgnoreCase("true")){
							to.setIsGracing(true);
						}else if(bo[11].toString().trim().equalsIgnoreCase("false")){
							to.setIsGracing(false);
						}
					}
					// */
					markList.add(to);
					stuMarksMap.put(Integer.parseInt(bo[0].toString()),markList);
				}
				//newUpdateProccessForm.setErrorMessage(errMsg);
			}
			return stuMarksMap;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/**
	 * 
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public boolean isPgStudent(int courseId) throws Exception {
		List<Course> list=null;
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			list=session.createQuery("FROM Course c where c.id = :courseId  and c.isActive = 1)").setParameter("courseId",courseId).list();
			if(list!= null && list.size() > 0){
				Course course = list.get(0);
				if(course.getProgram().getProgramType().getName().equalsIgnoreCase("PG")){
					result = true;
				}
			}
			return result;
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public List<Integer> getStudentIds(int id, int examId) throws ApplicationException {
		List<Integer> list=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query q=session.createQuery("select bo.studentId from ModerationMarksEntryBo bo where bo.classId="+id+" and bo.examId="+examId+" group by bo.studentId");
			list = q.list();
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	@Override
	public List<Integer> getSubjectIds(int id, int examId, Integer  stdnt) throws ApplicationException {
		List<Integer> list=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query q=session.createQuery("select bo.subjectId from ModerationMarksEntryBo bo where bo.classId="+id+" and bo.examId="+examId+" and bo.studentId="+stdnt+" group by bo.subjectId");
			list = q.list();
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	@Override
	public Map<Integer, StudentFinalMarkDetails> getgetFinalMarkMap(int id,
			int examId, Integer stdnt) throws ApplicationException {
		Map<Integer, StudentFinalMarkDetails> map = new HashMap<Integer, StudentFinalMarkDetails>();
		List list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String s = " from StudentFinalMarkDetails sfm"+
			" where sfm.exam.id="+examId+
			" and sfm.classes.id="+ id+" and sfm.student.id="+stdnt;
			Query q  = session.createQuery(s);
			list = q.list();
			if(list!=null && list.size()!=0){
				Iterator<StudentFinalMarkDetails> itr = list.iterator();
				while (itr.hasNext()) {
					StudentFinalMarkDetails sfm = (StudentFinalMarkDetails) itr.next();
					map.put(sfm.getSubject().getId(), sfm);
				}
				
			}
			
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return map;
	}
	@Override
	public List getMaxModeration(int id, int examId, Integer stdnt,
			Integer subId) throws ApplicationException {
		List list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String s = " select max(m.theoryMarks),max(m.practicalMarks) from ModerationMarksEntryBo m"+
			" where m.examId="+examId+
			" and m.classId="+ id+" and m.studentId="+stdnt+" and m.subjectId="+subId;
			Query q  = session.createQuery(s);
			list = q.list();
			
			
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	@Override
	public List<Integer> getStudentIdsForGracing(int id, int examId) throws ApplicationException {
		List<Integer> list=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query q=session.createQuery("select bo.student.id from ExamGracingSubjectMarksBo bo where bo.classes.id="+id+" group by bo.student.id");
			list = q.list();
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	@Override
	public List<Integer> getSubjectIdsForGracing(int id, int examId, Integer stdnt, int batchYear, int programTypeId) throws Exception {
		List<Integer> list=null;
		Session session = null;
		boolean isNotOldBatchReg = true;
		try {
			session = HibernateUtil.getSession();
			Integer suplyId = 0;
			List<Integer> subIds = new ArrayList<Integer>();
			String s1 = "";
			StringBuilder sql = new StringBuilder();
			Query q2 = session.createQuery("select min(m.examDefinitionBO.id) from ExamStudentFinalMarkDetailsBO m where m.classUtilBO.id="+id+" and m.examDefinitionBO.examTypeUtilBO.id=6");
			suplyId =  (Integer) q2.uniqueResult();
			if(suplyId!=null && examId==suplyId){				
				if((programTypeId == 1 && batchYear > 2014) || (programTypeId == 2 && batchYear > 2015)) {
					sql = sql.append("select EXAM_student_final_mark_details.subject_id as subject_id ");				
					sql.append("  		from EXAM_student_final_mark_details ");
					sql.append("  	   inner join EXAM_student_overall_internal_mark_details ");
					sql.append("          	   on EXAM_student_final_mark_details.class_id = EXAM_student_overall_internal_mark_details.class_id ");
					sql.append("         	  and EXAM_student_final_mark_details.exam_id = EXAM_student_overall_internal_mark_details.exam_id ");
					sql.append("         	  and EXAM_student_final_mark_details.student_id = EXAM_student_overall_internal_mark_details.student_id ");
					sql.append("         	  and EXAM_student_final_mark_details.subject_id = EXAM_student_overall_internal_mark_details.subject_id ");
					sql.append("  	   inner join classes ON classes.id = EXAM_student_final_mark_details.class_id ");
					sql.append("  	   inner join EXAM_definition ON EXAM_definition.id = EXAM_student_final_mark_details.exam_id ");
					sql.append("  	   inner join EXAM_subject_rule_settings ");
					sql.append("          	   on EXAM_student_final_mark_details.subject_id = EXAM_subject_rule_settings.subject_id ");
					sql.append("         	  and classes.course_id = EXAM_subject_rule_settings.course_id ");
					sql.append("         	  and classes.term_number = EXAM_subject_rule_settings.scheme_no ");
					sql.append("         	  and EXAM_subject_rule_settings.academic_year = EXAM_definition.academic_year ");
					sql.append("       where EXAM_student_final_mark_details.student_id = :studentId");
					sql.append("         and EXAM_student_final_mark_details.class_id = :classId");
					sql.append("         and EXAM_definition.exam_type_id = 1 ");
					sql.append("         and EXAM_subject_rule_settings.is_active = 1 ");
					sql.append("         and EXAM_definition.is_active = 1 ");
					sql.append("         and classes.is_active = 1 ");
					sql.append("         and (if(EXAM_student_final_mark_details.student_theory_marks is not null, EXAM_student_final_mark_details.student_theory_marks, 0) + ");
					sql.append("  	          if(EXAM_student_final_mark_details.student_practical_marks is not null, EXAM_student_final_mark_details.student_practical_marks, 0) + ");
					sql.append("       		  if(EXAM_student_overall_internal_mark_details.theory_total_mark is not null, EXAM_student_overall_internal_mark_details.theory_total_mark , 0) + ");
					sql.append("       		  if(EXAM_student_overall_internal_mark_details.practical_total_mark is not null, EXAM_student_overall_internal_mark_details.practical_total_mark , 0)) < subject_final_minimum");
					isNotOldBatchReg = false;
				}
				else {
					s1="select m.subjectUtilBO.id from ExamStudentFinalMarkDetailsBO m where m.studentUtilBO.id="+stdnt+" and m.classUtilBO.id="+id+" and m.examDefinitionBO.examTypeUtilBO.id=1 and m.passOrFail='fail'";
				}
			}else if(suplyId==null || examId<suplyId){
				s1="select m.subjectUtilBO.id from ExamStudentFinalMarkDetailsBO m where m.studentUtilBO.id="+stdnt+" and m.classUtilBO.id="+id+" and m.examDefinitionBO.examTypeUtilBO.id=1 and m.examDefinitionBO.id="+examId;
			}
			if(!s1.isEmpty() || sql.length() != 0){
				if(isNotOldBatchReg) {
					Query q1 = session.createQuery(s1);
					subIds =  q1.list();
				}
				else {
					Query q1 = session.createSQLQuery(sql.toString())
									  .setInteger("studentId", stdnt)
									  .setInteger("classId", id);
					subIds =  q1.list();					
				}
			}
			subIds.add(0);
			String s = "select bo.subject.id from ExamGracingSubjectMarksBo bo where bo.classes.id="+id+"  and bo.isGraced=1 and bo.student.id="+stdnt+" and bo.subject.id in (:subList) "
			  		+"group by bo.subject.id";
			Query q=session.createQuery(s);
			q.setParameterList("subList",subIds );
			list = q.list();
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	@Override
	public ExamGracingSubjectMarksBo getGraceMarks(int id, int examId,
			Integer stdnt, Integer subId) throws Exception {
		ExamGracingSubjectMarksBo bo = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String s = " from ExamGracingSubjectMarksBo m"+
			" where m.classes.id="+ id+" and m.student.id="+stdnt+" and m.subject.id="+subId;
			Query q  = session.createQuery(s);
			bo = (ExamGracingSubjectMarksBo) q.uniqueResult();
			
			
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return bo;
	}
	@Override
	public Map<Integer, StudentOverallInternalMarkDetails> getFinalInternalMarkMap(
			int id, int examId, Integer stdnt) throws ApplicationException {
		Map<Integer, StudentOverallInternalMarkDetails> map = new HashMap<Integer, StudentOverallInternalMarkDetails>();
		List list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String s = " from StudentOverallInternalMarkDetails sfm where"+
			"  sfm.classes.id="+ id+" and sfm.student.id="+stdnt+" and sfm.exam.id="+examId;
			Query q  = session.createQuery(s);
			list = q.list();
			if(list!=null && list.size()!=0){
				Iterator<StudentOverallInternalMarkDetails> itr = list.iterator();
				while (itr.hasNext()) {
					StudentOverallInternalMarkDetails sfm = (StudentOverallInternalMarkDetails) itr.next();
					map.put(sfm.getSubject().getId(), sfm);
				} 
				
			}
			
		} catch (Exception e) {
			log.error("error" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return map;
	}
	public Map<String, Boolean> getSuppImpExamDetails(int classId) throws Exception{

		String query ="SELECT exam_id,student_id, subject_id, is_supplementary, is_improvement FROM EXAM_supplementary_improvement_application "+
		" where class_id="+classId+" and exam_id in (SELECT max(exam_id) FROM EXAM_supplementary_improvement_application where class_id="+classId+") "+
		" group by student_id,subject_id ";

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Session session = null;
		Boolean isImprovement = false;
		try {
			session = HibernateUtil.getSession();
			List<Object[]> list = session.createSQLQuery(query).list();
			if(list!=null){
				Iterator itr = list.iterator();
				while(itr.hasNext()){
					Object[] obj =(Object[]) itr.next();
					if(obj[4]!=null ){
						isImprovement =Boolean.parseBoolean(obj[4].toString());
					}
					if(obj[1]!=null && obj[2]!=null){
						if(isImprovement)
						map.put(obj[1]+"_"+obj[2], isImprovement);
					}
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public boolean deleteOldInternalOverAllMarks(int classId, int examId) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
		    tx= session.beginTransaction();
		    StringBuilder sb = new StringBuilder("delete from StudentOverallInternalMarkDetails sfm where sfm.classes.id= :classId and sfm.exam.id= :examId");
			Query query = session.createQuery(sb.toString())
								 .setInteger("classId", classId)
								 .setInteger("examId", examId);
   		    query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
	}
	
	public StudentOverallInternalMarkDetails getInternalMarksForGivenExamAndClass(
			int examId, int classId, int studentId, int subId) throws Exception {
		
		Session session = null;
		StudentOverallInternalMarkDetails internalMarkList = null;
		try {
			
			session = HibernateUtil.getSession();
			String hqlQuery = "from StudentOverallInternalMarkDetails overallInternal " +
							  "where overallInternal.exam.id in (select internalExamDetail.examId " + 
							   												  "from ExamInternalExamDetailsBO internalExamDetail " +
							   												 "where internalExamDetail.internalExamNameId in (select internalExamDetailInn.internalExamNameId " + 
											   													 							   "from ExamInternalExamDetailsBO internalExamDetailInn " +
											   													 							   "where internalExamDetailInn.examId = :examId)) " +
								"and overallInternal.classes.id = :classId "+
								" and overallInternal.student.id= :studentId"+	
								" and overallInternal.subject.id= :subId" ;
		 							   
			Query query = session.createQuery(hqlQuery)
								 .setInteger("examId", examId)
								 .setInteger("classId", classId)
								 .setInteger("studentId", studentId)
								 .setInteger("subId", subId);
			internalMarkList = (StudentOverallInternalMarkDetails) query.uniqueResult();
			session.flush();
			return internalMarkList;
		}
		catch (ConstraintViolationException e) {
			log.error("Error while fetching internal exam"	, e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		catch (Exception e) {
			log.error("Error while fetching internal exam" + e);
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

	public List<StudentOverallInternalMarkDetails> getInternalMarksForGivenExamClassAndStudent(int examId, int classId, int studentId) throws Exception {
		
		Session session = null;
		List<StudentOverallInternalMarkDetails> internalMarkList = null;
		try {
			
			session = HibernateUtil.getSession();
			String hqlQuery = "from StudentOverallInternalMarkDetails overallInternal " +
							  "where overallInternal.exam.id in (select internalExamDetail.examId " + 
							   												  "from ExamInternalExamDetailsBO internalExamDetail " +
							   												 "where internalExamDetail.internalExamNameId in (select internalExamDetailInn.internalExamNameId " + 
											   													 							   "from ExamInternalExamDetailsBO internalExamDetailInn " +
											   													 							   "where internalExamDetailInn.examId = :examId)) " +
								"and overallInternal.classes.id = :classId " +
								"and overallInternal.student.id = :studentId";
			Query query = session.createQuery(hqlQuery)
								 .setInteger("examId", examId)
								 .setInteger("classId", classId)
								 .setInteger("studentId", studentId);
			internalMarkList = query.list();
			session.flush();
			return internalMarkList;
		}
		catch (ConstraintViolationException e) {
			log.error("Error while fetching internal exam"	, e);
			e.printStackTrace();
			throw new BusinessException(e);
		}
		catch (Exception e) {
			log.error("Error while fetching internal exam" + e);
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
	
	public boolean updateMarksForInternalRedo(List<StudentOverallInternalRedoMarkDetails> boList) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
		StudentOverallInternalRedoMarkDetails bo;

		try {
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();
			transaction.begin();
			
			String deleteQuery="";
			Iterator<StudentOverallInternalRedoMarkDetails> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				
				bo = tcIterator.next();
				deleteQuery="delete from EXAM_student_overall_redo_internal_mark_details " +
						" where internal_marks_details_id= "+bo.getStudentOverallInternalMarkDetails().getId()+
						" and redo_exam_id="+bo.getExam().getId();
				Query query = session.createSQLQuery(deleteQuery);
				query.executeUpdate();
				transaction.commit();
				session.flush();
				session.close();
				
				session = HibernateUtil.getSession();
				transaction= session.beginTransaction();
				session.saveOrUpdate(bo);
				
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			if(transaction != null)
				transaction.rollback();
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}
	
	public boolean updateMarksForInternalRedoDetails(List<StudentOverallInternalRedoMarkDetails> boList) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
		StudentOverallInternalRedoMarkDetails bo;

		try {
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();
			transaction.begin();
			
			String deleteQuery="";
			Iterator<StudentOverallInternalRedoMarkDetails> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				
				bo = tcIterator.next();
				deleteQuery="delete * from EXAM_student_overall_redo_internal_mark_details e " +
						" where e.internal_marks_details_id= "+bo.getStudentOverallInternalMarkDetails().getId()+
						" e.exam.id="+bo.getExam().getId();
				Query query = session.createSQLQuery(deleteQuery);
				query.executeUpdate();
				transaction.commit();
				session.flush();
				session.close();
				
				session = HibernateUtil.getSession();
				transaction= session.beginTransaction();
				session.saveOrUpdate(bo);
				
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			if(transaction != null)
				transaction.rollback();
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}
	
	public List<MarksEntryDetails> getStudentMarksForSubjectNewForRedo(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm)
	throws Exception {
		List<MarksEntryDetails> list=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from MarksEntryDetails md, SubjectRuleSettings s " +
			" left join s.examSubjectRuleSettingsSubInternals subInt" +
			" where md.subject.id=" +subId+
			" and md.marksEntry.student.id="+studentId+" and md.marksEntry.exam.id in (:examId)" +
			" and md.subject.id=s.subject.id and s.course.id="+to.getCourseId()+"" +
			" and s.academicYear="+to.getYear()+" and s.schemeNo="+to.getTermNo()+
			" and s.isActive=1 and subInt.isActive=1 and subInt.isTheoryPractical='" +subType+ 
			"' group by md.marksEntry.exam.id,s.subject.id";
			if(subType.equalsIgnoreCase("t")){
				query="select md.marksEntry.exam.id, cast(max(md.theoryMarks) as string) as marks," +
				" subInt.enteredMaxMark as subIntEntMaxMarks," +
				" subInt.maximumMark as subIntMaxMarks, s.theoryIntEntryMaxMarksTotal, s.theoryIntMaxMarksTotal," +
				" (cast(max(md.theoryMarks) as string)/ subInt.enteredMaxMark)*100 as percentage, md.isGracing "+query+
				" order by (cast(max(md.theoryMarks) as string)/ subInt.enteredMaxMark)*100 DESC";
			}else if(subType.equalsIgnoreCase("p")){
				query="select md.marksEntry.exam.id," +
				" cast(max(md.practicalMarks) as string) as marks," +
				" subInt.enteredMaxMark as subIntEntMaxMarks, subInt.maximumMark as subIntMaxMarks," +
				" s.practicalIntEntryMaxMarksTotal, s.practicalIntMaxMarksTotal," +
				" (cast(max(md.practicalMarks) as string)/ subInt.enteredMaxMark)*100 as percentage, md.isGracing "+query+
				" order by (cast(max(md.practicalMarks) as string)/ subInt.enteredMaxMark)*100 DESC";

			}

			list=session.createQuery(query).setParameterList("examId",intExamId).list();



		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
}
	
	public double getStudentMarksForSubjectForRedo(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm)
	throws Exception {
		List<Object[]> list=null;
		double marks=0;
		Session session = null;
		boolean isGracing=false;
		try {
			session = HibernateUtil.getSession();
			String query="from MarksEntryDetails md, SubjectRuleSettings s " +
			" left join s.examSubjectRuleSettingsSubInternals subInt" +
			" where md.subject.id=" +subId+
			" and md.marksEntry.student.id="+studentId+" and md.marksEntry.exam.id in (:examId)" +
			" and md.subject.id=s.subject.id and s.course.id="+to.getCourseId()+"" +
			" and s.academicYear="+to.getYear()+" and s.schemeNo="+to.getTermNo()+
			" and s.isActive=1 and subInt.isActive=1 and subInt.isTheoryPractical='" +subType+
			"' group by md.marksEntry.exam.id,s.subject.id";
			if(subType.equalsIgnoreCase("t")){
				query="select md.marksEntry.exam.id, cast(max(md.theoryMarks) as string) as marks," +
				" subInt.enteredMaxMark as subIntEntMaxMarks," +
				" subInt.maximumMark as subIntMaxMarks, s.theoryIntEntryMaxMarksTotal, s.theoryIntMaxMarksTotal," +
				" (cast(max(md.theoryMarks) as string)/ subInt.enteredMaxMark)*100 as percentage, md.isGracing "+query+
				" order by (cast(max(md.theoryMarks) as string)/ subInt.enteredMaxMark)*100 DESC";
			}else if(subType.equalsIgnoreCase("p")){
				query="select md.marksEntry.exam.id," +
				" cast(max(md.practicalMarks) as string) as marks," +
				" subInt.enteredMaxMark as subIntEntMaxMarks, subInt.maximumMark as subIntMaxMarks," +
				" s.practicalIntEntryMaxMarksTotal, s.practicalIntMaxMarksTotal," +
				" (cast(max(md.practicalMarks) as string)/ subInt.enteredMaxMark)*100 as percentage, md.isGracing "+query+
				" order by (cast(max(md.practicalMarks) as string)/ subInt.enteredMaxMark)*100 DESC";

			}

			list=session.createQuery(query).setParameterList("examId",intExamId).list();
			if(list!=null && !list.isEmpty()){
				if(limit==0)
					limit=list.size();
				if(isInd){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						Object[] obj = (Object[]) iterator.next();
						double totalMarks=0;
						double enterdMaxMarks=0;
						double maxMarks=0;
						@SuppressWarnings("unused")
						String totmarks="";
						if(obj[1]!=null && !obj[1].toString().trim().isEmpty() && !obj[1].toString().equalsIgnoreCase("AB")&& !obj[1].toString().equalsIgnoreCase("WithHeld"))
							totalMarks=Double.parseDouble(obj[1].toString());
						if(obj[1].toString().equalsIgnoreCase("WithHeld"))
							totmarks=obj[1].toString();
						if(obj[2]!=null && !obj[2].toString().trim().isEmpty())
							enterdMaxMarks=Double.parseDouble(obj[2].toString());
						if(obj[3]!=null && !obj[3].toString().trim().isEmpty())
							maxMarks=Double.parseDouble(obj[3].toString());
						marks=marks+((totalMarks/enterdMaxMarks)*maxMarks);
						// /*code added by chandra
						if(obj[7]!=null && !obj[7].toString().trim().isEmpty()){
							if(obj[7].toString().trim().equalsIgnoreCase("true")){
								isGracing=true;
							}else if(obj[7].toString().trim().equalsIgnoreCase("false")){
								if(isGracing){
									isGracing=true;
								}else{
									isGracing=false;
								}
							}
						}
						// */

					}
					//			Math.round(marks);
					if(!NewUpdateProccessHelper.avoidExamIds.contains(examId))
					{
						//Math.round(Double.parseDouble(df.format(marks)));

					}
					else
					{
						//Math.round(marks);
					}

				}else{
					double totalMarks=0;
					double enterdMaxMarks=0;
					double maxMarks=0;
					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						Object[] obj = (Object[]) iterator.next();
						if(obj[1]!=null && !obj[1].toString().trim().isEmpty()&& !obj[1].toString().equalsIgnoreCase("AB") && !obj[1].toString().equalsIgnoreCase("WithHeld"))
							totalMarks=totalMarks+Double.parseDouble(obj[1].toString());
						if(obj[1].toString().equalsIgnoreCase("WithHeld"))
							totalMarks=Double.parseDouble(obj[1].toString());
						if(obj[4]!=null && !obj[4].toString().trim().isEmpty())
							enterdMaxMarks=Double.parseDouble(obj[4].toString());
						if(obj[5]!=null && !obj[5].toString().trim().isEmpty())
							maxMarks=Double.parseDouble(obj[5].toString());
						// /*code added by chandra
						if(obj[7]!=null && !obj[7].toString().trim().isEmpty()){
							if(obj[7].toString().trim().equalsIgnoreCase("true")){
								isGracing=true;
							}else if(obj[7].toString().trim().equalsIgnoreCase("false")){
								if(isGracing){
									isGracing=true;
								}else{
									isGracing=false;
								}
							}
						}
						// */
					}
					marks=(totalMarks/enterdMaxMarks)*maxMarks;
					//			Math.round(marks);
					if(!NewUpdateProccessHelper.avoidExamIds.contains(examId))
					{
						//Math.round(Double.parseDouble(df.format(marks)));
						Double.parseDouble(new DecimalFormat("##.##").format(marks));
					}
					else
					{
						//Math.round(marks);
						Double.parseDouble(new DecimalFormat("##.##").format(marks));
					}

				}
			}
			newUpdateProccessForm.setIsGracing(isGracing);
			if(!NewUpdateProccessHelper.avoidExamIds.contains(examId))
				//return marks;//Math.round(Double.parseDouble(df.format(marks)));
				return Double.parseDouble(new DecimalFormat("##.##").format(marks));
			else
				//return marks;//Math.round(marks);
				return Double.parseDouble(new DecimalFormat("##.##").format(marks));
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
}
	@Override
	public List<StudentOverallInternalMarkDetails> getInternalMarksForGivenExamAndClass(int examId, int classId) throws Exception {
		Session session = null;
		List<StudentOverallInternalMarkDetails> internalMarks = null;
		try {
			session= HibernateUtil.getSession();
			String hql = " from StudentOverallInternalMarkDetails ovrInt where ovrInt.classes.id = :classId and ovrInt.exam.id = (select def.regularExamId from ExamDefinitionBO def where def.id = :examId)";
			Query query = session.createQuery(hql)
								 .setInteger("classId", classId)
								 .setInteger("examId", examId);
			internalMarks = query.list();
			return internalMarks;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
	}
	@Override
	public Map<Integer, List<StudentOverallInternalMarkDetails>> getStudentOverallMarks(
			Map<Integer, List<Integer>> stuMapWithSubjects, int classId) throws Exception {

		Session session = null;
		List subList =null;
		List<StudentOverallInternalMarkDetails> studentOverallInternalMarkDetailsList =null;
		Map<Integer, List<StudentOverallInternalMarkDetails>> studentOverallInternalMarkDetailsMap=new HashMap<Integer, List<StudentOverallInternalMarkDetails>>();
		try {
			session= HibernateUtil.getSession();
			for(int studentId :stuMapWithSubjects.keySet()){
				subList =stuMapWithSubjects.get(studentId);
				String query="from StudentOverallInternalMarkDetails s where s.student.id in(:stuId)" +
				" and s.classes.id in(:classId) and s.subject.id in(:subList)";
				studentOverallInternalMarkDetailsList = (List<StudentOverallInternalMarkDetails>) session.createQuery(query).
				setParameter("stuId", studentId).setParameter("classId", classId).setParameterList("subList", subList).list();
				
				studentOverallInternalMarkDetailsMap.put(studentId, studentOverallInternalMarkDetailsList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return studentOverallInternalMarkDetailsMap;
	}
	
	public boolean updateOverAllInternalmarksDetails(List<StudentOverallInternalMarkDetails> boList) throws Exception {
		
		Session session = null;
		Transaction transaction = null;
		StudentOverallInternalMarkDetails bo;
		
		try {
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<StudentOverallInternalMarkDetails> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				
				bo = tcIterator.next();
				session.saveOrUpdate(bo);
				
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			if(transaction != null)
				transaction.rollback();
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}


	@Override
	public boolean saveRegularOverAllAfterRevaluation(List<StudentFinalMarkDetails> boList) throws Exception {	
		Session session = null;
		Transaction transaction = null;
		StudentFinalMarkDetails bo;
		try {
			session = HibernateUtil.getSession();	
			transaction = session.beginTransaction();
			Iterator<StudentFinalMarkDetails> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();				
				
				session.saveOrUpdate(bo);
				
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			if(transaction != null) {
				transaction.rollback();
			}
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			e.printStackTrace();
			if(transaction != null) {
				transaction.rollback();
			}
			if(session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}
	
	public StudentFinalMarkDetails getRegularMarksForComparison(int classId, int studentId, int subjectId) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from StudentFinalMarkDetails sfm where sfm.classes.id = :classId and sfm.student.id = :studentId and sfm.subject.id = :subjectId and sfm.exam.examType.id = 1")
								 .setInteger("classId", classId)
								 .setInteger("studentId", studentId)
								 .setInteger("subjectId", subjectId);
			StudentFinalMarkDetails sfm = (StudentFinalMarkDetails) query.uniqueResult();
			return sfm;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new ApplicationException();
		}
	}
}
