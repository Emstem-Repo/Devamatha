package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.NewInternalMarksEntryForm;
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class NewInternalMarksEntryTransactionImpl implements INewInternalMarksEntryTransaction{
	private static volatile NewInternalMarksEntryTransactionImpl transactionImpl = null;
	private static final Log log = LogFactory.getLog(NewInternalMarksEntryTransactionImpl.class);
	public static NewInternalMarksEntryTransactionImpl getInstance(){
		if(transactionImpl == null){
			transactionImpl = new NewInternalMarksEntryTransactionImpl();
		}
		return transactionImpl;
	}
	private NewInternalMarksEntryTransactionImpl (){
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction#openInternalExamClasses()
	 */
	@Override
	public List<OpenInternalExamForClasses> openInternalExamClasses() throws Exception {
		Session session = null;
		List<OpenInternalExamForClasses> list = new ArrayList<OpenInternalExamForClasses>();
		try {
			session=HibernateUtil.getSession();
			String str = "from OpenInternalExamForClasses cls where cls.isActive=1 and cls.openExam.isActive=1" +
					" order by cls.openExam.endDate , cls.openExam.endDate  ASC"; 
			Query query1 = session.createQuery(str);
			list = query1.list();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}
	/* This method contains the Query to get the current Internal Exams and its details.
	 * @see com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction#getCurrentExamDetails(com.kp.cms.forms.exam.NewInternalMarksEntryForm)
	 */
	@Override
	public List<Object[]> getCurrentInternalExamDetails( NewInternalMarksEntryForm internalMarksEntryForm) throws Exception {
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "select ed.id as examId,cl.id as classId,cl.name as className," +
			  " subject.id as subId, subject.name as subName,subject.code as subCode," +
			  " ed.name as examName,subject.is_theory_practical as theroy, " +
			  " ecsd.course_id as courseId,ecsd.scheme_no as scheme," +
			  " program_type.id as ptype,open.is_theory_practical, " +
			  " DATE_FORMAT(open.end_date,'%d/%m/%Y') as date ," +
			  " ttbatchid, ttbatchname,attendaceTypeId" +
			  " from EXAM_definition ed" +
			  " join EXAM_exam_course_scheme_details ecsd  on ecsd.exam_id = ed.id" +
			  " join classes cl on cl.course_id=ecsd.course_id and cl.term_number=ecsd.scheme_no" +
			  " join course cou on cl.course_id = cou.id join program ON cou.program_id = program.id" +
			  " join program_type ON program.program_type_id = program_type.id" +
			  " join class_schemewise cls on cls.class_id = cl.id" +
			  " join open_internal_marks_entry open on open.program_type_id = program_type.id and open.exam_id = ed.id and open.is_active=1" +
			  " join open_internal_exam_classes opencls on open.id = opencls.open_exam_id and opencls.classes_id = cl.id and opencls.is_active=1" +
			  " join curriculum_scheme_duration csd on cls.curriculum_scheme_duration_id = csd.id" +
			  " join teacher_class_subject tcs on tcs.class_schemewise_id = cls.id" +
			  " join subject ON  tcs.subject_id = subject.id and subject.is_active=1" +
			  " join users u on tcs.teacher_id = u.id" +
			  
			  " left join (select tt_users.user_id as ttuser, batch.id as ttbatchid, tt_subject_batch.subject_id as ttsubject, batch.batch_name as ttbatchname,tt_subject_batch.attendance_type_id as attendaceTypeId,classes.id as ttclassid" +
			  " from tt_users" +
			  " inner join tt_subject_batch ON tt_users.tt_subject_id = tt_subject_batch.id and tt_subject_batch.is_active = 1" +
			  " inner join tt_period_week ON tt_subject_batch.tt_period_id = tt_period_week.id and tt_period_week.is_active = 1" +
			  " inner join tt_class ON tt_period_week.tt_class_id = tt_class.id and tt_class.is_active = 1" +
			  " inner join class_schemewise ON tt_class.class_schemewise_id = class_schemewise.id" +
			  " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
			  " inner join classes ON class_schemewise.class_id = classes.id and classes.is_active = 1" +
			  " inner join batch ON tt_subject_batch.batch_id = batch.id and batch.is_active = 1" +
			  " where curdate() between curriculum_scheme_duration.start_date and curriculum_scheme_duration.end_date and tt_users.user_id=" +internalMarksEntryForm.getUserId()+
			  " group by tt_users.user_id, batch.id, tt_subject_batch.subject_id,ttclassid" +
			  " ) as tt on u.id=tt.ttuser and tt.ttsubject=subject.id " +
			  " and tt.ttclassid=cl.id" +
			  " where u.id="+internalMarksEntryForm.getUserId()+
			  " and curdate() between  date(csd.start_date) and date(csd.end_date)" +
			  " and cl.is_active=1 and tcs.is_active=1 and ed.del_is_active=1" +
			  " and (subject.name != 'HOLISTIC EDUCATION' and subject.code != 'ENVS') and ecsd.is_active=1 and u.active=1" +
			  " order by ed.name,cl.name,subject.name";
			Query query=session.createSQLQuery(hqlQuery);
			list = query.list();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction#getAlreadyEnteredMarksForBatchStudents(int, int, java.lang.String, java.util.List)
	 */
	@Override
	public List getAlreadyEnteredMarksForBatchStudents(int subId, int examId,  String subjectType, List currentStudentList)
			throws Exception {
		Session session =null;
		List list = null;
		System.out.println("hi");
		try{
			if(currentStudentList!=null && !currentStudentList.isEmpty()){
				List<Integer> studentIds = new ArrayList<Integer>();
				Iterator<Object[]> iterator = currentStudentList.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					Student student = (Student) objects[0];
					studentIds .add(student.getId());
				}
				session = HibernateUtil.getSession();
				String hqlQuery = "from MarksEntryDetails m" +
				" where m.subject.id=" +subId+
				" and m.marksEntry.exam.id="+examId+
				" and m.marksEntry.student.id in (:studentIds)";
				if(subjectType.equalsIgnoreCase("B")){
					hqlQuery=hqlQuery+" and (m.theoryMarks is not null or m.practicalMarks is not null) ";
				}else if(subjectType.equalsIgnoreCase("T") || subjectType.equalsIgnoreCase("B")){
					hqlQuery=hqlQuery+" and m.theoryMarks is not null ";
				}else if(subjectType.equalsIgnoreCase("P") || subjectType.equalsIgnoreCase("B")){
					hqlQuery=hqlQuery+" and m.practicalMarks is not null ";
				}
				hqlQuery = hqlQuery + " and m.marksEntry.student.admAppln.isCancelled=0" +
				" and (m.marksEntry.student.isHide=0 or m.marksEntry.student.isHide is null) " +
				" and m.marksEntry.student.isActive=1";
				Query query = session.createQuery(hqlQuery);
				query.setParameterList("studentIds", studentIds);
				list = query.list();
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}
	@Override
	public List getQueryForCurrentBatchStudents(int subId, List<Integer> classesList, int batchId) throws Exception {
		Session session =null;
		List list =null;
		try{
			session =HibernateUtil.getSession();
			String str = "select bs.student,bs.classSchemewise.classes.id from Batch batch join batch.batchStudents bs " +
			 " where batch.isActive = 1 and bs.student.isAdmitted=1 and bs.student.admAppln.isCancelled=0 and batch.id = "+batchId+
			 " and batch.subject.id ="+subId+
			 " and bs.classSchemewise in (select cls.id from ClassSchemewise cls where cls.classes.isActive = 1 and cls.classes.id in(:classesList)) and bs.isActive = 1" +
			 " and (bs.student.isHide=0 or bs.student.isHide is null)";
			Query query = session.createQuery(str);
			query.setParameterList("classesList", classesList);
			list = query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction#getDataForQuery(java.lang.String)
	 */
	@Override
	public List getDataForQuery(String query) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} 
		/*finally {
			if (session != null) {
				session.flush();
			}
		}*/
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction#getMaxMarksOfSubject(com.kp.cms.forms.exam.NewInternalMarksEntryForm)
	 */
	@Override
	public Double getMaxMarksOfSubject(NewInternalMarksEntryForm objform) throws Exception {
		Session session = null;
		Double maxMarks=null;
		String subType="";
		if(objform.getSubjectType()!=null){
			if(objform.getSubjectType().equalsIgnoreCase("1")){
				subType="t";
			}else if(objform.getSubjectType().equalsIgnoreCase("0")){
				subType="p";
			}else if(objform.getSubjectType().equalsIgnoreCase("11")){
				subType="t";// Still its not completed
			}
		}
		try{
			session = HibernateUtil.getSession();
			ExamDefinition definition = (ExamDefinition)session.createQuery("from ExamDefinition e where e.id="+objform.getExamId()).uniqueResult();
			if(definition != null){
				String query="select internal.enteredMaxMark" +
//				" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark, ansScript.value " +
				" from SubjectRuleSettings s" +
				" left join s.examSubjectRuleSettingsSubInternals internal" +
//				" left join s.examSubjectRuleSettingsMulEvaluators eval" +
//				" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
				" where s.course.id="+objform.getCourseId()+
				" and s.schemeNo=" +objform.getSchemeNo()+
				" and s.subject.id=" +objform.getSubjectId()+
				" and s.academicYear = " +definition.getAcademicYear();
				if(definition.getInternalExamType() != null)
					query = query + " and internal.internalExamTypeId= " +definition.getInternalExamType().getId();
				query = query + " and internal.isTheoryPractical='"+subType+"'"+
				" and s.isActive=1 group by s.id";
				List<Object> list=session.createQuery(query).list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object> itr=list.iterator();
					while (itr.hasNext()) {
						Object objects = (Object) itr.next();
						if(objects!=null)
							maxMarks=new Double(objects.toString());
					}
				}
			}
		}catch (Exception e) {
			log.error("Error while retrieving ExamAbscentCode.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return maxMarks;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction#saveMarks(com.kp.cms.forms.exam.NewInternalMarksEntryForm)
	 */
	@Override
	public boolean saveMarks(NewInternalMarksEntryForm objform) throws Exception {
		log.debug("inside saveMarks method in NewInternalMarksEntry");
		Session session = null;
		Transaction transaction = null;
		List<StudentMarksTO> list=objform.getStudentList();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<StudentMarksTO> itr=list.iterator();
			while (itr.hasNext()) {
				StudentMarksTO to = (StudentMarksTO) itr.next();
				if(to.getId()>0){
					if((to.getIsPractical() && to.getPracticalMarks() != null && !to.getPracticalMarks().equals(to.getOldPracticalMarks())) || (to.getIsTheory() && to.getTheoryMarks() != null && !to.getTheoryMarks().equals(to.getOldTheoryMarks()))){
						MarksEntryDetails detailBo=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getId());
						if(to.getIsPractical()){
							detailBo.setPracticalMarks(to.getPracticalMarks());
						}
						if(to.getIsTheory()){
							detailBo.setTheoryMarks(to.getTheoryMarks());
						}
						detailBo.setModifiedBy(objform.getUserId());
						detailBo.setLastModifiedDate(new Date());
						session.update(detailBo);
					}
				}else{
					if((to.getIsTheory() && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()) || (to.getIsPractical() && to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty())){
						MarksEntry marksEntry=null;
						String query="from MarksEntry m where m.exam.id="+objform.getExamId()+" and m.student.id="+to.getStudentId()+" and m.classes.id="+to.getClassId();
						if(objform.getEvaluatorType()!=null && !objform.getEvaluatorType().isEmpty())
							query=query+" and m.evaluatorType="+objform.getEvaluatorType();
						else
							query=query+" and m.evaluatorType is null";
						if(objform.getAnswerScriptType()!=null && !objform.getAnswerScriptType().isEmpty())
							query=query+" and m.answerScript="+objform.getAnswerScriptType();
						else
							query=query+" and m.answerScript is null";
						List<MarksEntry> marksEntrys=session.createQuery(query).list();
						if(marksEntrys==null || marksEntrys.isEmpty()){
							marksEntry=new MarksEntry();
							Student student=new Student();
							student.setId(to.getStudentId());
							marksEntry.setStudent(student);
							ExamDefinitionBO exam=new ExamDefinitionBO();
							exam.setId(Integer.parseInt(objform.getExamId()));
							marksEntry.setExam(exam);
							if(objform.getEvaluatorType()!=null && !objform.getEvaluatorType().isEmpty()){
								marksEntry.setEvaluatorType(Integer.parseInt(objform.getEvaluatorType()));
							}
							if(objform.getAnswerScriptType()!=null && !objform.getAnswerScriptType().isEmpty()){
								marksEntry.setAnswerScript(Integer.parseInt(objform.getAnswerScriptType()));
							}
							Classes classes=new Classes();
							classes.setId(to.getClassId());
							marksEntry.setClasses(classes);
							marksEntry.setCreatedBy(objform.getUserId());
							marksEntry.setCreatedDate(new Date());
							marksEntry.setModifiedBy(objform.getUserId());
							marksEntry.setLastModifiedDate(new Date());
							Set<MarksEntryDetails> marksEntryDetails=new HashSet<MarksEntryDetails>();
							
							MarksEntryDetails detail=new MarksEntryDetails();
							Subject subject=new Subject();
							subject.setId(Integer.parseInt(objform.getSubjectId()));
							detail.setSubject(subject);
							if(to.getIsTheory()){
								detail.setTheoryMarks(to.getTheoryMarks());
								detail.setIsTheorySecured(false);
							}
							if(to.getIsPractical()){
								detail.setPracticalMarks(to.getPracticalMarks());
								detail.setIsPracticalSecured(false);
							}
							detail.setCreatedBy(objform.getUserId());
							detail.setCreatedDate(new Date());
							detail.setModifiedBy(objform.getUserId());
							detail.setLastModifiedDate(new Date());
							detail.setIsGracing(false);
							marksEntryDetails.add(detail);
							marksEntry.setMarksDetails(marksEntryDetails);
							session.save(marksEntry);
						}else{
							Iterator<MarksEntry> marksitr=marksEntrys.iterator();
							if (marksitr.hasNext()) {
								 marksEntry = (MarksEntry) marksitr.next();
							}
							MarksEntryDetails detail=(MarksEntryDetails)session.createQuery("from MarksEntryDetails m where m.marksEntry.id="+marksEntry.getId()+"" +
									" and m.subject.id="+objform.getSubjectId()).uniqueResult();
							if(detail==null){
								detail=new MarksEntryDetails();
								Subject subject=new Subject();
								subject.setId(Integer.parseInt(objform.getSubjectId()));
								detail.setSubject(subject);
								detail.setCreatedBy(objform.getUserId());
								detail.setCreatedDate(new Date());
								detail.setIsGracing(false);
							}
							detail.setMarksEntry(marksEntry);
							if(to.getIsTheory()){
								detail.setTheoryMarks(to.getTheoryMarks());
								detail.setIsTheorySecured(false);
							}
							if(to.getIsPractical()){
								detail.setPracticalMarks(to.getPracticalMarks());
								detail.setIsPracticalSecured(false);
							}
							detail.setModifiedBy(objform.getUserId());
							detail.setLastModifiedDate(new Date());
							marksEntry.setModifiedBy(objform.getUserId());
							marksEntry.setLastModifiedDate(new Date());
							session.saveOrUpdate(detail);
							session.update(marksEntry);
						}
					}
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			log.debug("leaving saveMarks method in NewInternalMarksEntry impl");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in saveMarks method in NewInternalMarksEntry impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in saveMarks method in NewInternalMarksEntry impl...", e);
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction#getMobileNoByUserId(java.lang.String)
	 */
	@Override
	public Users getMobileNoByUserId(String userId) throws Exception {
		Session session =null;
		Users users =null;
		try{
			session =HibernateUtil.getSession();
			String str = "from Users user where user.id='"+userId+"'";
			Query query = session.createQuery(str);
			users = (Users) query.uniqueResult();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return users;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction#getMaxMarksDefineForSubject(com.kp.cms.to.exam.InternalMarksEntryTO)
	 */
	@Override
	public Double getMaxMarksDefineForSubject( InternalMarksEntryTO internalMarksEntryTO) throws Exception {
		Session session = null;
		Double maxMarks=null;
		String subType="";
		if(internalMarksEntryTO.getTheoryPractical()!=null){
			if(internalMarksEntryTO.getTheoryPractical().equalsIgnoreCase("T")){
				subType="t";
			}else if(internalMarksEntryTO.getTheoryPractical().equalsIgnoreCase("P")){
				subType="p";
			}
		}
		try{
			session = HibernateUtil.getSession();
			ExamDefinition definition = (ExamDefinition)session.createQuery("from ExamDefinition e where e.id="+internalMarksEntryTO.getExamId()).uniqueResult();
			if(definition != null){
				String query="select internal.enteredMaxMark" +
				" from SubjectRuleSettings s" +
				" left join s.examSubjectRuleSettingsSubInternals internal" +
				" where s.course.id="+internalMarksEntryTO.getCourseId()+
				" and s.schemeNo=" +internalMarksEntryTO.getSchemeNo()+
				" and s.subject.id=" +internalMarksEntryTO.getSubId()+
				" and s.academicYear = " +definition.getAcademicYear();
				if(definition.getInternalExamType() != null)
					query = query + " and internal.internalExamTypeId= " +definition.getInternalExamType().getId();
				query = query + " and internal.isTheoryPractical='"+subType+"'"+
				" and s.isActive=1 group by s.id";
				List<Object> list=session.createQuery(query).list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object> itr=list.iterator();
					while (itr.hasNext()) {
						Object objects = (Object) itr.next();
						if(objects!=null)
							maxMarks=new Double(objects.toString());
					}
				}
			}
		}catch (Exception e) {
			log.error("Error while retrieving getMaxMarksDefineForSubject.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return maxMarks;
	}
}
