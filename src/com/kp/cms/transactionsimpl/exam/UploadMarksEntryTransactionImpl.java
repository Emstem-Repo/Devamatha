package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMarksEntryUtilBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.forms.exam.UploadMarksEntryForm;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.IUploadMarksEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadMarksEntryTransactionImpl implements IUploadMarksEntryTransaction {

	@Override
	public Map<String, Integer> getStudentMap(Integer programId,Integer courseId,Integer semister,Integer subjectId,String isPrevious,int examId,String examType,String subType,StringBuffer query) throws Exception {
		Map<String,Integer> studentMap=new HashMap<String, Integer>();
		Session session = null;
		List<Object[]> student = null;
		try {
			session = HibernateUtil.getSession();
			if (isPrevious.equalsIgnoreCase("yes")) 
			{
				if(!examType.equalsIgnoreCase("Supplementary")){
					query =query.append(" from Student s" +
										" join s.studentPreviousClassesHistory classHis" +
										" join classHis.classes.classSchemewises classSchemewise" +
										" join s.studentSubjectGroupHistory subjHist" +
										" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
										" where s.admAppln.isCancelled=0" +
										" and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
										" from ExamDefinitionBO e where e.id="+examId+")" +
										" and classSchemewise.curriculumSchemeDuration.semesterYearNo=" +semister+
										" and classSchemewise.classes.course.id="+courseId+
										" and subSet.subject.id="+subjectId +
										" and subjHist.schemeNo="+semister);
				}else{
					query =query.append("  from Student s" +
										" join s.studentPreviousClassesHistory classHis" +
										" join classHis.classes.classSchemewises classSchemewise" +
										" join s.studentSubjectGroupHistory subjHist" +
										" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
										" join s.studentSupplementaryImprovements supImp with supImp.subject.id=" +subjectId+
										" and supImp.examDefinition.id=" +examId+
										" where s.admAppln.isCancelled=0" +
										" and classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
										" from ExamDefinitionBO e" +
										" where e.id="+examId+") and classSchemewise.curriculumSchemeDuration.semesterYearNo="+semister+
										" and classSchemewise.classes.course.id="+courseId+
										" and subSet.subject.id="+subjectId+
										" and supImp.classes.course.id=" +courseId);
					if(subType!=null && !subType.isEmpty()){
						if(subType.equals("T")){
							query =query.append(" and supImp.isAppearedTheory=1");
						}else if(subType.equals("P")){
							query =query.append(" and supImp.isAppearedPractical=1");
						}else{
							query =query.append(" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1");
						}
					}
				}
				// Code commented By Balaji
//				query = query.append("select s.register_no,s.id from EXAM_student_sub_grp_history asg " +
//						"LEFT JOIN subject_group_subjects sgs ON asg.subject_group_id = sgs.subject_group_id and sgs.is_active = 1 " +
//						"LEFT JOIN student s ON  asg.student_id = s.id " +
//						"left join adm_appln app on s.adm_appln_id = app.id " +
//						"left join course ON app.selected_course_id = course.id " +
//						"where sgs.subject_id="+subjectId+
//						" and asg.scheme_no="+semister+
//						" and course.id=" +courseId+
//						" and app.is_approved=1" +
//						" and app.is_cancelled=0" +
//						" and app.is_selected=1" +
//						" and s.register_no is not null ");
				student = session.createQuery(query.toString()).list();
			}
			else
			{	
				if(!examType.equalsIgnoreCase("Supplementary")){
					query =query.append("  from Student s" +
										" join s.admAppln.applicantSubjectGroups appSub" +
										" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
										" where s.admAppln.isCancelled=0" +
										" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
										" from ExamDefinitionBO e where e.id="+examId+") " +
										" and subGroup.subject.id=" +subjectId+
										" and s.admAppln.courseBySelectedCourseId.id=" +courseId+
										" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+semister);
				}else{
					query =query.append("  from Student s" +
										" join s.classSchemewise.classes c" +
										" join s.admAppln.applicantSubjectGroups appSub" +
										" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
										" join s.studentSupplementaryImprovements supImp with supImp.subject.id="+subjectId+
										" and supImp.examDefinition.id="+courseId+
										" where s.admAppln.isCancelled=0" +
										" and s.classSchemewise.curriculumSchemeDuration.academicYear>=(select e.examForJoiningBatch" +
										" from ExamDefinitionBO e where e.id="+examId+")" +
										" and s.admAppln.courseBySelectedCourseId.id="+courseId+
										" and supImp.classes.course.id="+courseId+
										" and subGroup.subject.id="+subjectId+
										" and s.classSchemewise.curriculumSchemeDuration.semesterYearNo="+semister);
					if(subType!=null && !subType.isEmpty()){
						if(subType.equals("T")){
							query =query.append(" and supImp.isAppearedTheory=1");
						}else if(subType.equals("P")){
							query =query.append(" and supImp.isAppearedPractical=1");
						}else{
							query =query.append(" and supImp.isAppearedTheory=1 and supImp.isAppearedPractical=1");
						}
					}
				}
				// COmmented Code By Balaji
//				query = query.append("select st.registerNo,st.id from Student st inner join st.admAppln.applicantSubjectGroups asg " +
//									"inner join asg.subjectGroup.subjectGroupSubjectses sgs " +
//									"where st.isAdmitted=1 and st.isActive = 1 " +
//									"and st.admAppln.isSelected=1 " +
//									"and st.admAppln.isCancelled=0  " +
//									"and st.admAppln.isApproved=1 ");
//				query = query.append(" and st.admAppln.courseBySelectedCourseId.program.id="+ programId);
//				query = query.append(" and st.admAppln.courseBySelectedCourseId.id="+ courseId);
//				query = query.append(" and st.classSchemewise.curriculumSchemeDuration.semesterYearNo="+ semister);
//				query = query.append(" and sgs.subject.id="+subjectId);
				student = session.createQuery(query.toString()).list();
			}	
			
			
			if(student!=null && !student.isEmpty()){
				Iterator<Object[]> itr=student.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null){
						studentMap.put(objects[0].toString(),Integer.parseInt(objects[1].toString()));
					}
				}
			}
			
			return studentMap;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public boolean addMarks(List<ExamMarksEntryUtilBO> evaluator1Marks,List<ExamMarksEntryUtilBO> evaluator2Marks,Integer subjectId,String subjectType)throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			int count=0;
			for(ExamMarksEntryUtilBO bo:evaluator1Marks)
			{
				if(duplicateCheck(bo, subjectId,subjectType))
				{
					session.persist(bo);
					if(++count%20==0)
					{
						session.flush();
						session.clear();
					}
				}
				
			}
			count=0;
			session.flush();
			session.clear();
			for(ExamMarksEntryUtilBO bo:evaluator2Marks)
			{
				if(duplicateCheck(bo, subjectId,subjectType))
				{
					session.persist(bo);
					if(++count%20==0)
					{
						session.flush();
						session.clear();
					}
				}
			}
			transaction.commit();
			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
			throw new BusinessException(e.getMessage());
		}
	}
	
	private boolean duplicateCheck(ExamMarksEntryUtilBO marksEntryUtilBO,Integer subjectId,String subjectType) throws Exception
	{
		boolean canBeAdded=false;
		try
		{
			Session session=HibernateUtil.getSession();
			String Query=	"from ExamMarksEntryUtilBO mE"
							+" inner join mE.examMarksEntryDetailsBOset mED" 
							+" where mE.studentUtilBO.id="+marksEntryUtilBO.getStudentUtilBO().getId()
							+" and mE.examDefinitionBO.id="+marksEntryUtilBO.getExamDefinitionBO().getId()  
							+" and mE.evaluatorTypeId="+marksEntryUtilBO.getEvaluatorTypeId()
							+" and mED.subjectUtilBO.id="+subjectId;
			if(subjectType.equalsIgnoreCase("T"))
				Query=Query+" and mED.theoryMarks is not null ";
			else
				Query=Query+" and mED.practicalMarks is not null ";
			if(session.createQuery(Query).list().size()==0)
				canBeAdded=true;
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
			
		}
		return canBeAdded;
	}
	
	public List<Integer> getEvaluatorIds(int subjectId,int courseId,int schemeNo,int academicYear)throws Exception
	{
		List<Integer>evaluatorIds=null;
		try
		{
			Session session=HibernateUtil.getSession();
			String Query="select e.evaluatorId from ExamSubjecRuleSettingsMultipleEvaluatorBO e " +
						"where e.evaluatorId is not null and e.examSubjectRuleSettingsBO.subjectUtilBO.id="+subjectId
						+" and e.examSubjectRuleSettingsBO.academicYear="+ academicYear +" and e.examSubjectRuleSettingsBO.courseId="+courseId
						+" and e.examSubjectRuleSettingsBO.schemeNo="+schemeNo +" order by e.evaluatorId asc";
			evaluatorIds=session.createQuery(Query).list();
			return evaluatorIds;
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	public ExamMarksEntryUtilBO getMarkEntryDetails(String examId, String studentId,Integer evaluatorId)throws Exception
	{
		ExamMarksEntryUtilBO bo=null;
		try
		{
			Session session=HibernateUtil.getSession();
			String Query="from ExamMarksEntryUtilBO marks where marks.examDefinitionBO.id="+examId+" and marks.isSecured=0 " +
					"and marks.studentUtilBO.id="+studentId+" and marks.evaluatorTypeId="+evaluatorId;
			List<ExamMarksEntryUtilBO> boList=session.createQuery(Query).list();
			if(boList!=null && boList.size()!=0)
				bo=boList.get(0);
			return bo;
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new BusinessException(e.getMessage());
		}
	}
	
	public boolean checkStudentCourse(String regNo, String courseId)throws Exception
	{
		boolean sameCourse=false;
		try
		{
			Session session=HibernateUtil.getSession();
			String Query="from Student s where s.registerNo='"+regNo+"' and s.admAppln.courseBySelectedCourseId.id="+courseId;
			if(session.createQuery(Query).uniqueResult()!=null)
				sameCourse=true;
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
			
		}
		return sameCourse;
	}

	public boolean isDuplicateForMarkDetails(Integer marksId, Integer subjectId)throws Exception
	{
		boolean duplicate=false;
		try
		{
			Session session=HibernateUtil.getSession();
			String Query="from ExamMarksEntryDetailUtilBo e where e.examMarksEntryBO.id="+marksId+" and e.subjectUtilBO.id="+subjectId;
			if(session.createQuery(Query).list().size()>0)
				duplicate=true;
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
			
		}
		return duplicate;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IUploadMarksEntryTransaction#saveMarks(java.util.List)
	 */
	@Override
	public boolean saveMarks(List<StudentMarksTO> finalList,UploadMarksEntryForm marksEntryForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<StudentMarksTO> itr=finalList.iterator();
			while (itr.hasNext()) {
				StudentMarksTO to = (StudentMarksTO) itr.next();

				if((marksEntryForm.getSubjectType().equalsIgnoreCase("t") && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()) || (marksEntryForm.getSubjectType().equalsIgnoreCase("p") && to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty())){
					MarksEntry marksEntry=null;
					String query="from MarksEntry m where m.exam.id="+marksEntryForm.getExamId()+" and m.student.id="+to.getStudentId()+" and m.classes.id="+to.getClassId();
					if(to.getEvaId()!=null && !to.getEvaId().isEmpty())
						query=query+" and m.evaluatorType="+to.getEvaId();
					else
						query=query+" and m.evaluatorType is null";
					if(to.getAnsId()!=null && !to.getAnsId().isEmpty())
						query=query+" and m.answerScript="+to.getAnsId();
					else
						query=query+" and m.answerScript is null";
					List<MarksEntry> marksEntrys=session.createQuery(query).list();
					if(marksEntrys==null || marksEntrys.isEmpty()){
						marksEntry=new MarksEntry();
						Student student=new Student();
						student.setId(to.getStudentId());
						marksEntry.setStudent(student);
						ExamDefinitionBO exam=new ExamDefinitionBO();
						exam.setId(Integer.parseInt(marksEntryForm.getExamId()));
						marksEntry.setExam(exam);
						if(to.getEvaId()!=null && !to.getEvaId().isEmpty()){
							marksEntry.setEvaluatorType(Integer.parseInt(to.getEvaId()));
						}
						if(to.getAnsId()!=null && !to.getAnsId().isEmpty()){
							marksEntry.setAnswerScript(Integer.parseInt(to.getAnsId()));
						}
						Classes classes=new Classes();
						classes.setId(to.getClassId());
						marksEntry.setClasses(classes);
						marksEntry.setCreatedBy(marksEntryForm.getUserId());
						marksEntry.setCreatedDate(new Date());
						marksEntry.setModifiedBy(marksEntryForm.getUserId());
						marksEntry.setLastModifiedDate(new Date());
						Set<MarksEntryDetails> marksEntryDetails=new HashSet<MarksEntryDetails>();
						MarksEntryDetails detail=new MarksEntryDetails();
						Subject subject=new Subject();
						subject.setId(Integer.parseInt(marksEntryForm.getSubjectId()));
						detail.setSubject(subject);
						detail.setTheoryMarks(to.getTheoryMarks());
						detail.setPracticalMarks(to.getPracticalMarks());
						detail.setCreatedBy(marksEntryForm.getUserId());
						detail.setCreatedDate(new Date());
						detail.setModifiedBy(marksEntryForm.getUserId());
						detail.setLastModifiedDate(new Date());
						if(marksEntryForm.getSubjectType().equalsIgnoreCase("t")){
							detail.setTheoryMarks(to.getTheoryMarks());
							detail.setIsTheorySecured(false);
						}
						if(marksEntryForm.getSubjectType().equalsIgnoreCase("p")){
							detail.setPracticalMarks(to.getPracticalMarks());
							detail.setIsPracticalSecured(false);
						}
						
						marksEntryDetails.add(detail);
						marksEntry.setMarksDetails(marksEntryDetails);
						session.save(marksEntry);
					}else{
						Iterator<MarksEntry> marksitr=marksEntrys.iterator();
						if (marksitr.hasNext()) {
							 marksEntry = (MarksEntry) marksitr.next();
						}
						MarksEntryDetails detail=(MarksEntryDetails)session.createQuery("from MarksEntryDetails m where m.marksEntry.id="+marksEntry.getId()+"" +
								" and m.subject.id="+marksEntryForm.getSubjectId()).uniqueResult();
						if(detail==null){
							detail=new MarksEntryDetails();
							Subject subject=new Subject();
							subject.setId(Integer.parseInt(marksEntryForm.getSubjectId()));
							detail.setSubject(subject);
							detail.setCreatedBy(marksEntryForm.getUserId());
							detail.setCreatedDate(new Date());
						}
						detail.setMarksEntry(marksEntry);
						
						if(marksEntryForm.getSubjectType().equalsIgnoreCase("t")){
							detail.setTheoryMarks(to.getTheoryMarks());
							detail.setIsTheorySecured(false);
						}
						if(marksEntryForm.getSubjectType().equalsIgnoreCase("p")){
							detail.setPracticalMarks(to.getPracticalMarks());
							detail.setIsPracticalSecured(false);
						}
						detail.setModifiedBy(marksEntryForm.getUserId());
						detail.setLastModifiedDate(new Date());
						marksEntry.setModifiedBy(marksEntryForm.getUserId());
						marksEntry.setLastModifiedDate(new Date());
						session.saveOrUpdate(detail);
						session.update(marksEntry);
					}
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IUploadMarksEntryTransaction#checkMarksAlreadyExist(java.util.List, com.kp.cms.forms.exam.UploadMarksEntryForm)
	 */
	@Override
	public boolean checkMarksAlreadyExist(List<Integer> studentIdList, UploadMarksEntryForm marksEntryForm) throws Exception {
		String query="from MarksEntryDetails md where md.subject.id= " +marksEntryForm.getSubjectId()+
				" and md.marksEntry.exam.id= " +marksEntryForm.getExamId()+
				" and md.marksEntry.classes.course.id= " +marksEntryForm.getCourseId()+
				" and md.marksEntry.student.id in (:studentList)";
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query).setParameterList("studentList",studentIdList);
			list = selectedCandidatesQuery.list();
			if(list!=null && !list.isEmpty())
				return true;
			else
				return false;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	
	
	public Double getMaxMarkOfSubject(UploadMarksEntryForm newExamMarksEntryForm) throws Exception {
		Session session = null;
		Double maxMarks=null;
		String subType="";
		if(newExamMarksEntryForm.getSubjectType()!=null){
			if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("1")){
				subType="t";
			}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("0")){
				subType="p";
			}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("11")){
				subType="t";// Still its not completed
			}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("T")){
				subType="t";
			}else if(newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("P")){
				subType="p";
			}
		}
		try{
			session = HibernateUtil.getSession();
			String query="select internal.enteredMaxMark," +
					" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark, ansScript.value from SubjectRuleSettings s" +
					" left join s.examSubjectRuleSettingsSubInternals internal" +
					" left join s.examSubjectRuleSettingsMulEvaluators eval" +
					" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
					" where s.course.id="+newExamMarksEntryForm.getCourseId()+
					" and s.schemeNo=" +newExamMarksEntryForm.getSchemeId()+
					" and s.subject.id=" +newExamMarksEntryForm.getSubjectId();
			if(newExamMarksEntryForm.getEvaluatorType()!=null && !newExamMarksEntryForm.getEvaluatorType().isEmpty()){
				query=query+" and eval.evaluatorId="+newExamMarksEntryForm.getEvaluatorType()+
				" and eval.isTheoryPractical='"+subType+"'";
			}
			if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
				query=query+" and ansScript.multipleAnswerScriptId="+newExamMarksEntryForm.getAnswerScriptType()+
				" and ansScript.isTheoryPractical='"+subType+"'";
			}	
			if(newExamMarksEntryForm.getExamType().equals("Internal")){
				query=query+" and internal.internalExamTypeId=(select e.internalExamType.id" +
						" from ExamDefinition e where e.id="+newExamMarksEntryForm.getExamId()+
				") and internal.isTheoryPractical='"+subType+"'";
			}
			if(newExamMarksEntryForm.getExamType().equals("Supplementary")){
				query=query+" and s.academicYear>=(select e.examForJoiningBatch from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") ";
			}else{
				query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+newExamMarksEntryForm.getExamId()+") ";
			}
			query=query+" and s.isActive=1 group by s.id";
			List<Object[]> list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(newExamMarksEntryForm.getExamType().equals("Internal")){
						if(objects[0]!=null)
							maxMarks=new Double(objects[0].toString());
					}else{
						if(newExamMarksEntryForm.getAnswerScriptType()!=null && !newExamMarksEntryForm.getAnswerScriptType().isEmpty()){
							if(objects[3]!=null)
								maxMarks=new Double(objects[3].toString());
						}else{
							if(newExamMarksEntryForm.getSubjectType().equals("1") || newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
								if(objects[1]!=null)
								maxMarks=new Double(objects[1].toString());
							}else if(newExamMarksEntryForm.getSubjectType().equals("0") || newExamMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
								if(objects[2]!=null)
								maxMarks=new Double(objects[2].toString());
							}else{
								if(objects[1]!=null)
									maxMarks=new Double(objects[1].toString());
							}
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

}
