package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.TeacherToGroup;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.InternalMarksEntryForm;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.IInternalMarksEntryImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class InternalMarksEntryImpl implements IInternalMarksEntryImpl {
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	public static volatile InternalMarksEntryImpl impl = null;
	private static final Log log = LogFactory.getLog(InternalMarksEntryImpl.class);
	
	public static InternalMarksEntryImpl getInstance(){
		if(impl == null){
			impl = new InternalMarksEntryImpl();
		}
		return impl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IInternalMarksEntryImpl#getCurrentExamDetails(com.kp.cms.forms.exam.InternalMarksEntryForm)
	 */
	@Override
	public List<Object[]> getCurrentExamDetails(InternalMarksEntryForm objform) throws Exception{
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			session=HibernateUtil.getSession();
			/*String hqlQuery = "select ed.id as examId,cl.id as classId,cl.name as className,subject.id as subId," +
			" subject.name as subName,subject.code as subCode,ed.name as examName,subject.is_theory_practical as theroy," +
			" ecsd.course_id as courseId,ecsd.scheme_no as scheme,program_type.id as ptype,open.is_theory_practical," +
			" DATE_FORMAT(open.end_date,'%d/%m/%Y') as date " +
			" from EXAM_definition ed " +
			" join EXAM_exam_course_scheme_details ecsd  on ecsd.exam_id = ed.id " +
			" join classes cl on cl.course_id=ecsd.course_id and cl.term_number=ecsd.scheme_no " +
			" join course cou on cl.course_id = cou.id"+
			" join program ON cou.program_id = program.id"+
			" join program_type ON program.program_type_id = program_type.id"+
			" join class_schemewise cls on cls.class_id = cl.id " +
			" join open_internal_marks_entry open on open.program_type_id = program_type.id and open.exam_id = ed.id and open.is_active=1 "+
			" join open_internal_exam_classes opencls on open.id = opencls.open_exam_id and opencls.classes_id = cl.id and opencls.is_active=1"+
			" join curriculum_scheme_duration csd on cls.curriculum_scheme_duration_id = csd.id " +
			" join teacher_class_subject tcs on tcs.class_schemewise_id = cls.id " +
			" join subject ON  tcs.subject_id = subject.id and subject.is_active=1 " +
			" join users u on tcs.teacher_id = u.id " +
			" where u.id= " +objform.getUserId()+
			" and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()),InternalMarksEntryImpl.SQL_DATEFORMAT,InternalMarksEntryImpl.FROM_DATEFORMAT))+"' between  date(csd.start_date) and date(csd.end_date) " +
			" and cl.is_active=1 and tcs.is_active=1 and ed.del_is_active=1 " +
			" and subject.name != 'HOLISTIC EDUCATION'" +
			" and ecsd.is_active=1 and u.active=1 order by ed.name,cl.name,subject.name";*/
			/*code modified by sudhir */
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
			  " where curdate() between curriculum_scheme_duration.start_date and curriculum_scheme_duration.end_date and tt_users.user_id=" +objform.getUserId()+
			  " group by tt_users.user_id, batch.id, tt_subject_batch.subject_id" +
			  " ) as tt on u.id=tt.ttuser and tt.ttsubject=subject.id " +
//			  " and tt.ttclassid=cl.id" +
			  " where u.id="+objform.getUserId()+
			  " and curdate() between  date(csd.start_date) and date(csd.end_date)" +
			  " and cl.is_active=1 and tcs.is_active=1 and ed.del_is_active=1" +
			  " and (subject.name != 'HOLISTIC EDUCATION' and subject.code != 'ENVS') and ecsd.is_active=1 and u.active=1" +
			  " order by ed.name,cl.name,subject.name";
			/*--------------------------------*/
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
	
	/**
	 * @param objform
	 * @param practicalexamIds 
	 * @param praProgramTypeList 
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getPracticalCurrentExamDetails(
			InternalMarksEntryForm objform) throws Exception{
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "select ed.id as examId,cl.id as classId,cl.name as className,subject.id as subId," +
			" subject.name as subName,subject.code as subCode,ed.name as examName,subject.is_theory_practical as theroy,ecsd.course_id as courseId,ecsd.scheme_no as scheme,program_type.id as ptype" +
			" from EXAM_definition ed " +
			" join EXAM_exam_course_scheme_details ecsd  on ecsd.exam_id = ed.id " +
			" join classes cl on cl.course_id=ecsd.course_id and cl.term_number=ecsd.scheme_no " +
			" join course cou on cl.course_id = cou.id"+
			" join program ON cou.program_id = program.id"+
			" join program_type ON program.program_type_id = program_type.id"+
			" join class_schemewise cls on cls.class_id = cl.id " +
			" join open_internal_marks_entry open on open.program_type_id = program_type.id and open.exam_id = ed.id and open.is_active=1 and (open.is_theory_practical='P' or open.is_theory_practical='B')"+
			" join open_internal_exam_classes opencls on open.id = opencls.open_exam_id and opencls.classes_id = cl.id and opencls.is_active=1"+
			" join curriculum_scheme_duration csd on cls.curriculum_scheme_duration_id = csd.id " +
			" join teacher_class_subject tcs on tcs.class_schemewise_id = cls.id " +
			" join subject ON  tcs.subject_id = subject.id and subject.is_active=1 " +
			" join users u on tcs.teacher_id = u.id " +
			" where u.id= " +objform.getUserId()+
			" and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()),InternalMarksEntryImpl.SQL_DATEFORMAT,InternalMarksEntryImpl.FROM_DATEFORMAT))+"' between  date(csd.start_date) and date(csd.end_date) " +
			" and cl.is_active=1 and tcs.is_active=1 and ed.del_is_active=1 " +
			" and (subject.is_theory_practical ='P' or subject.is_theory_practical='B') and subject.name != 'HOLISTIC EDUCATION'" +
			" and ecsd.is_active=1 and u.active=1 order by ed.name,cl.name,subject.name";
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
	 * @see com.kp.cms.transactions.exam.IInternalMarksEntryImpl#saveMarks(com.kp.cms.forms.exam.InternalMarksEntryForm)
	 */
	@Override
	public boolean saveMarks(InternalMarksEntryForm objform) throws Exception {
		log.debug("inside addTermsConditionCheckList");
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
							//code added by chandra
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
								//code added by chandra
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

	/**
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	@Override
	public Double getMaxMarkOfSubject(InternalMarksEntryForm objform) throws Exception {
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
				String query="select internal.enteredMaxMark," +
				" s.theoryEseEnteredMaxMark, s.practicalEseEnteredMaxMark, ansScript.value from SubjectRuleSettings s" +
				" left join s.examSubjectRuleSettingsSubInternals internal" +
				" left join s.examSubjectRuleSettingsMulEvaluators eval" +
				" left join s.examSubjectRuleSettingsMulAnsScripts ansScript " +
				" where s.course.id="+objform.getCourseId()+
				" and s.schemeNo=" +objform.getSchemeNo()+
				" and s.subject.id=" +objform.getSubjectId()+
				" and s.academicYear = " +definition.getAcademicYear();
				if(definition.getInternalExamType() != null)
					query = query + " and internal.internalExamTypeId= " +definition.getInternalExamType().getId();
				query = query + " and internal.isTheoryPractical='"+subType+"'"+
				" and s.isActive=1 group by s.id";
				List<Object[]> list=session.createQuery(query).list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null)
							maxMarks=new Double(objects[0].toString());
					}
				}
			}
		} catch (Exception e) {
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
	 * @see com.kp.cms.transactions.exam.IInternalMarksEntryImpl#getOpendExamDetails()
	 */
	public List<OpenInternalExamForClasses> getOpendExamDetails() throws Exception{
		Session session = null;
		List<OpenInternalExamForClasses> list = new ArrayList<OpenInternalExamForClasses>();
		try {
			session=HibernateUtil.getSession();
			String str = "from OpenInternalExamForClasses cls where cls.isActive=1 and cls.openExam.isActive=1"; 
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

	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getTeachersMap(String userId) throws Exception{
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			session=HibernateUtil.getSession();
			String str = "select t from TeacherToGroup t join t.rolesId.userses u where t.isActive=1 and u.id="+userId +" order by t.usersId.userName "; 
			Query query1 = session.createQuery(str);
			List<TeacherToGroup> list = query1.list();
			if(list != null && !list.isEmpty()){
				Iterator<TeacherToGroup> iterator = list.iterator();
				while (iterator.hasNext()) {
					TeacherToGroup teacherToGroup = (TeacherToGroup) iterator.next();
					if(teacherToGroup.getUsersId() != null && teacherToGroup.getUsersId().getId() != 0 && teacherToGroup.getUsersId().getUserName() != null){
						map.put(teacherToGroup.getUsersId().getId(), teacherToGroup.getUsersId().getUserName());
					}
				}
			}
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	/**
	 * @param objform
	 * @param theoryexamIds
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getCurrentExamDetailsForOtherTeacher(
			InternalMarksEntryForm objform, String marksFor) throws Exception{
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			session=HibernateUtil.getSession();
			/*String hqlQuery = "select ed.id as examId,cl.id as classId,cl.name as className,subject.id as subId," +
			" subject.name as subName,subject.code as subCode,ed.name as examName,subject.is_theory_practical as theroy," +
			" ecsd.course_id as courseId,ecsd.scheme_no as scheme,program_type.id as ptype,open.is_theory_practical," +
			" DATE_FORMAT(open.end_date,'%d/%m/%Y') as date " +
			" from EXAM_definition ed " +
			" join EXAM_exam_course_scheme_details ecsd  on ecsd.exam_id = ed.id " +
			" join classes cl on cl.course_id=ecsd.course_id and cl.term_number=ecsd.scheme_no " +
			" join course cou on cl.course_id = cou.id"+
			" join program ON cou.program_id = program.id"+
			" join program_type ON program.program_type_id = program_type.id"+
			" join class_schemewise cls on cls.class_id = cl.id " +
			" join open_internal_marks_entry open on open.program_type_id = program_type.id and open.exam_id = ed.id and open.is_active=1 "+
			" join open_internal_exam_classes opencls on open.id = opencls.open_exam_id and opencls.classes_id = cl.id and opencls.is_active=1"+
			" join curriculum_scheme_duration csd on cls.curriculum_scheme_duration_id = csd.id " +
			" join teacher_class_subject tcs on tcs.class_schemewise_id = cls.id " +
			" join subject ON  tcs.subject_id = subject.id and subject.is_active=1 " +
			" join users u on tcs.teacher_id = u.id ";
			if(marksFor.equalsIgnoreCase("HOD")){
				hqlQuery = hqlQuery + " where u.id= " +objform.getEmpId();
			}else{
				hqlQuery = hqlQuery +" where u.id= " +objform.getTeacherId();
			}
			hqlQuery = hqlQuery + 
			
			" and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()),InternalMarksEntryImpl.SQL_DATEFORMAT,InternalMarksEntryImpl.FROM_DATEFORMAT))+"' between  date(csd.start_date) and date(csd.end_date) " +
			" and cl.is_active=1 and tcs.is_active=1 and ed.del_is_active=1 " +
			" and subject.name != 'HOLISTIC EDUCATION'" +
			" and ecsd.is_active=1 and u.active=1 order by ed.name,cl.name,subject.name";*/
			
			/*code modified by sudhir */
			/* Query brings the records  batchwise, if the subject has batch ,otherwise based on classes*/
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
			  
			  " left join (select tt_users.user_id as ttuser, batch.id as ttbatchid, tt_subject_batch.subject_id as ttsubject, batch.batch_name as ttbatchname,tt_subject_batch.attendance_type_id as attendaceTypeId" +
			  " from tt_users" +
			  " inner join tt_subject_batch ON tt_users.tt_subject_id = tt_subject_batch.id and tt_subject_batch.is_active = 1" +
			  " inner join tt_period_week ON tt_subject_batch.tt_period_id = tt_period_week.id and tt_period_week.is_active = 1" +
			  " inner join tt_class ON tt_period_week.tt_class_id = tt_class.id and tt_class.is_active = 1" +
			  " inner join class_schemewise ON tt_class.class_schemewise_id = class_schemewise.id" +
			  " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
			  " inner join classes ON class_schemewise.class_id = classes.id and classes.is_active = 1" +
			  " inner join batch ON tt_subject_batch.batch_id = batch.id and batch.is_active = 1" +
			  " where curdate() between curriculum_scheme_duration.start_date and curriculum_scheme_duration.end_date and " ;
			  if(marksFor.equalsIgnoreCase("HOD")){
					hqlQuery = hqlQuery +  " tt_users.user_id=" +objform.getEmpId();
				}else{
					hqlQuery = hqlQuery + " tt_users.user_id=" +objform.getTeacherId();
				}
			 // " tt_users.user_id=" +objform.getUserId()+
			  		hqlQuery = hqlQuery + 
				  " group by tt_users.user_id, batch.id, tt_subject_batch.subject_id" +
				  " ) as tt on u.id=tt.ttuser and tt.ttsubject=subject.id" ;
			  if(marksFor.equalsIgnoreCase("HOD")){
				  	hqlQuery = hqlQuery + " where u.id= " +objform.getEmpId();
			  }else{
				  	hqlQuery = hqlQuery +" where u.id= " +objform.getTeacherId();
			  }
			  //" where u.id="+objform.getUserId()+
					hqlQuery = hqlQuery + 
			  " and curdate() between  date(csd.start_date) and date(csd.end_date)" +
			  " and cl.is_active=1 and tcs.is_active=1 and ed.del_is_active=1" +
			  " and subject.name != 'HOLISTIC EDUCATION' and ecsd.is_active=1 and u.active=1" +
			  " order by ed.name,cl.name,subject.name";
			/*--------------------------------*/
			Query query=session.createSQLQuery(hqlQuery);
			list = query.list();
			if(marksFor.equalsIgnoreCase("HOD")){
				objform.setEmpType("HOD");
			}
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}

	/**
	 * @param objform
	 * @param practicalexamIds
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getPracticalCurrentExamDetailsForOtherTeacher(
			InternalMarksEntryForm objform) throws Exception{
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "select ed.id as examId,cl.id as classId,cl.name as className,subject.id as subId," +
			" subject.name as subName,subject.code as subCode,ed.name as examName,subject.is_theory_practical as theroy,ecsd.course_id as courseId,ecsd.scheme_no as scheme,program_type.id as ptype " +
			" from EXAM_definition ed " +
			" join EXAM_exam_course_scheme_details ecsd  on ecsd.exam_id = ed.id " +
			" join classes cl on cl.course_id=ecsd.course_id and cl.term_number=ecsd.scheme_no " +
			" join course cou on cl.course_id = cou.id"+
			" join program ON cou.program_id = program.id"+
			" join program_type ON program.program_type_id = program_type.id"+
			" join class_schemewise cls on cls.class_id = cl.id " +
			" join open_internal_marks_entry open on open.program_type_id = program_type.id and open.exam_id = ed.id and open.is_active=1 and (open.is_theory_practical='P' or open.is_theory_practical='B')"+
			" join open_internal_exam_classes opencls on open.id = opencls.open_exam_id and opencls.classes_id = cl.id and opencls.is_active=1"+
			" join curriculum_scheme_duration csd on cls.curriculum_scheme_duration_id = csd.id " +
			" join teacher_class_subject tcs on tcs.class_schemewise_id = cls.id " +
			" join subject ON  tcs.subject_id = subject.id and subject.is_active=1 " +
			" join users u on tcs.teacher_id = u.id " +
			" where u.id= " +objform.getTeacherId()+
			" and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()),InternalMarksEntryImpl.SQL_DATEFORMAT,InternalMarksEntryImpl.FROM_DATEFORMAT))+"' between  date(csd.start_date) and date(csd.end_date) " +
			" and cl.is_active=1 and tcs.is_active=1 and ed.del_is_active=1 " +
			" and (subject.is_theory_practical ='P' or subject.is_theory_practical='B') and subject.name != 'HOLISTIC EDUCATION'" +
			" and ecsd.is_active=1 and u.active=1 order by ed.name,cl.name,subject.name";
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

	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getEmployees(String userId) throws Exception{
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			
			session=HibernateUtil.getSession();
			List<Integer> departmentList = session.createQuery("select t.departmentId.id  from TeacherDepartment t where t.teacherId.id="+userId).list();
			List<Integer> teacherIds1=new ArrayList<Integer>();
			List<Integer> teacherIds2=new ArrayList<Integer>();
			if(departmentList != null && !departmentList.isEmpty()){
				Query query =session.createQuery("select u.id from Users u "+
						 							" where u.isActive=1 and u.active=1 and u.department.id in (:departmentList)");
				Query query1= session.createQuery("select u.id from Users  u"+
													" where u.employee.isActive=1 and u.employee.active=1 and u.employee.department.id in(:departmentList)");
				 query.setParameterList("departmentList", departmentList);
				 query1.setParameterList("departmentList", departmentList);
				 teacherIds1=query.list();
				 teacherIds2=query1.list();
				}
			teacherIds1.addAll(teacherIds2);
			if(teacherIds1 != null && !teacherIds1.isEmpty()){
				String str = "select t from TeacherClassSubject t " +
				" where t.teacherId.id in " +
				" (:Teachers) " +
				" and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()),InternalMarksEntryImpl.SQL_DATEFORMAT,InternalMarksEntryImpl.FROM_DATEFORMAT))+ 
				"' between t.classId.curriculumSchemeDuration.startDate " +
				" and t.classId.curriculumSchemeDuration.endDate group by t.teacherId.id "; 
				Query query1 = session.createQuery(str).setParameterList("Teachers", teacherIds1);
				List<TeacherClassSubject> list = query1.list();
				if(list != null && !list.isEmpty()){
					Iterator<TeacherClassSubject> iterator = list.iterator();
					while (iterator.hasNext()) {
						TeacherClassSubject teacherToGroup = (TeacherClassSubject) iterator.next();
						if(teacherToGroup.getTeacherId() != null && teacherToGroup.getTeacherId().getId() != 0 && teacherToGroup.getTeacherId().getUserName() != null){
							map.put(teacherToGroup.getTeacherId().getId(), teacherToGroup.getTeacherId().getUserName());
						}
					}
				}
			}
			/*String query = "select u from Users u " +
					" where u.employee.department.id = " +
					" (select us.employee.department.id from Users us where us.id="+userId+") " +
					" and u.guest.id is not null";
			List<Users> users = session.createQuery(query).list();
			if(users != null){
				Iterator<Users> iterator = users.iterator();
				while (iterator.hasNext()) {
					Users user = (Users) iterator.next();
					if(user.getId() != 0){
						map.put(user.getId(),user.getUserName());
					}
				}
			}*/
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		map= CommonUtil.sortMapByValue(map);
		return map;
	}
	/**
	 * @param subId
	 * @param examId
	 * @param classesId
	 * @param subjectType
	 * @param currentStudentList
	 * @return
	 */
	public List getAlreadyEnteredMarksForBatchStudents(int subId, int examId, String classesId, String subjectType, List currentStudentList) {
		Session session =null;
		List list = null;
		try{
			if(currentStudentList!=null && !currentStudentList.isEmpty()){
				List<Integer> studentIds = new ArrayList<Integer>();
				//List<Integer> classIds = new ArrayList<Integer>();
				Iterator<Object[]> iterator = currentStudentList.iterator();
				while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					studentIds .add(Integer.parseInt(objects[1].toString()));
				}
				/*String[] classId = classesId;
				classIds*/
				session = HibernateUtil.getSession();
				String hqlQuery = "from MarksEntryDetails m" +
				" where m.subject.id=" +subId+
				//" and m.marksEntry.classes.id in (classesId)"+
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
				//query.setParameter("", arg1)
//			query=query+" and m.theoryMarks is not null ";
//			query=query+" and m.practicalMarks is not null ";
//			query=query+" and m.theoryMarks is not null and m.practicalMarks is not null ";
			}
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;
	}

	/**
	 * @param subId
	 * @param classesList
	 * @param batchId
	 * @return
	 * @throws Exception
	 */
	public List getQueryForCurrentBatchStudents(int subId, List<Integer> classesList, int batchId)throws Exception {
		Session session =null;
		List list =null;
		try{
			session =HibernateUtil.getSession();
			String str = "select bs.student,bs.student.id,bs.classSchemewise.classes.id from Batch batch join batch.batchStudents bs " +
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
	
}
