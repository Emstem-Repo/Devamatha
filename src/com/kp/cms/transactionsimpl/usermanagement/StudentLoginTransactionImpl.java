package com.kp.cms.transactionsimpl.usermanagement;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetail;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.SemesterWiseCourseBO;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TTClassesHistory;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.bo.exam.InternalExamGrievanceBo;
import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.bo.sap.UploadSAPMarksBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningActivityBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.fee.FeePaymentHandler;
import com.kp.cms.handlers.usermanagement.StudentLoginHandler;
import com.kp.cms.transactions.usermanagement.IStudentLoginTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
public class StudentLoginTransactionImpl implements IStudentLoginTransaction{
	private static final Log log = LogFactory.getLog(StudentLoginTransactionImpl.class);
	private static volatile StudentLoginTransactionImpl studentLoginTransactionImpl = null;
	public static StudentLoginTransactionImpl getInstance(){
		if(studentLoginTransactionImpl == null){
			studentLoginTransactionImpl = new StudentLoginTransactionImpl();
			return studentLoginTransactionImpl;
		}
		return studentLoginTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentLoginTransaction#saveMobileNo(com.kp.cms.bo.admin.PersonalData)
	 */
	@Override
	public boolean saveMobileNo(PersonalData personalData) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isAdded=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(personalData);
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentLoginTransaction#getStudentPersonalData(int)
	 */
	@Override
	public PersonalData getStudentPersonalData(int personalId) throws Exception {
		Session session=null;
		PersonalData data=null;
		try{
			session= HibernateUtil.getSession();
			String str="from PersonalData p where p.id =" +personalId;
			Query query = session.createQuery(str);
			data= (PersonalData) query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return data;
	}
	/* (non-Javadoc)
	 * Querying the database to find whether student has any fee payment through smart card mode in the last one month
	 * @see com.kp.cms.transactions.usermanagement.IStudentLoginTransaction#getStudentPaymentMode(int)
	 */
	@Override
	public List<FeePayment> getStudentPaymentMode(int id) throws Exception {
		Session session=null;
		List<FeePayment> feeList=null;
		try{
			session= HibernateUtil.getSession();
			String currentDate=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), "dd-MMM-yyyy","yyyy-MM-dd");
			String previousMonthDate=CommonUtil.ConvertStringToDateFormat(CommonUtil.getPreviousMonthDate(), "dd-MM-yyyy","yyyy-MM-dd");
			String str="from FeePayment f where f.student.id=" +id+
					" and f.feePaymentMode.id=" +CMSConstants.smartCardPaymentMode+
					" and f.isCancelChallan=0 and f.challenPrintedDate between '" +previousMonthDate+
					"' and '"+currentDate+"'" ;
			Query query = session.createQuery(str);
			feeList=  query.list();

			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return feeList;
	}
	/* (non-Javadoc)
	 * getting the FeePayment BO to print challan
	 * @see com.kp.cms.transactions.usermanagement.IStudentLoginTransaction#getFeePaymentDetailsForEdit(int, int)
	 */
	@Override
	public FeePayment getFeePaymentDetailsForEdit(int parseInt,
			int financialYear) throws Exception {
		Session session = null;
		FeePayment feePayment = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select feePayment" +
					" from FeePayment feePayment" +
					" join feePayment.feePaymentDetails paymentDetails" +
					" where feePayment.billNo = :billNo" +
					" and paymentDetails.feeFinancialYear.id = :FinYearId and feePayment.isCancelChallan = 0" +
					" group by feePayment.id");
			query.setInteger("billNo", parseInt); 
			query.setInteger("FinYearId", financialYear);

			if(query.list() != null && !query.list().isEmpty()){
				feePayment = (FeePayment) query.list().get(0);
			}
			return feePayment;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentLoginTransaction#isFacultyFeedbackAvailable(int)
	 */
	@Override
	public EvaStudentFeedbackOpenConnection isFacultyFeedbackAvailable(int id,Map<Integer,String> specializationIds) throws Exception {
		Session session =null;
		Object object = null;
		EvaStudentFeedbackOpenConnection connection = null;
		try{

			session = HibernateUtil.getSession();
			Query query  = session.createQuery("from EvaStudentFeedbackOpenConnection conn where conn.isActive=1 and conn.classesId.id="+id+" and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between conn.startDate and conn.endDate");
			//connection = (EvaStudentFeedbackOpenConnection) query.uniqueResult();
			//newly modified
			List<EvaStudentFeedbackOpenConnection> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<EvaStudentFeedbackOpenConnection> iterator = list.iterator();
				while (iterator.hasNext()) {
					EvaStudentFeedbackOpenConnection connectionBo = (EvaStudentFeedbackOpenConnection) iterator .next();
					if(connectionBo.getExamSpecializationBO()!=null && !connectionBo.getExamSpecializationBO().toString().isEmpty()){
						if(specializationIds.containsKey(connectionBo.getExamSpecializationBO().getId())){
							connection = new EvaStudentFeedbackOpenConnection();
							EvaluationStudentFeedbackSession feedbackSession = new EvaluationStudentFeedbackSession();
							Classes classes = new Classes();
							ExamSpecializationBO specializationBo = new ExamSpecializationBO();
							feedbackSession.setId(connectionBo.getFeedbackSession().getId());
							classes.setId(connectionBo.getClassesId().getId());
							specializationBo.setId(connectionBo.getExamSpecializationBO().getId());
							connection.setFeedbackSession(feedbackSession);
							connection.setClassesId(classes);
							connection.setExamSpecializationBO(specializationBo);
							connection.setIsExitEval(connectionBo.getIsExitEval());
						}
					}else{
						connection = new EvaStudentFeedbackOpenConnection();
						EvaluationStudentFeedbackSession feedbackSession = new EvaluationStudentFeedbackSession();
						Classes classes = new Classes();
						feedbackSession.setId(connectionBo.getFeedbackSession().getId());
						classes.setId(connectionBo.getClassesId().getId());
						connection.setFeedbackSession(feedbackSession);
						connection.setClassesId(classes);
						connection.setIsExitEval(connectionBo.getIsExitEval());

					}
				}
			}
			//
		}catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return connection;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentLoginTransaction#getSpecializationIds(int)
	 */
	@Override
	public Map<Integer,String> getSpecializationIds(int studentId) throws Exception {
		Session session = null;
		Map<Integer,String> map = new HashMap<Integer,String>();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamStudentBioDataBO examStudentBioData where examStudentBioData.studentId="+studentId);
			List<ExamStudentBioDataBO> listBos = query.list();
			if(listBos!=null && !listBos.isEmpty()){
				Iterator<ExamStudentBioDataBO> iterator = listBos.iterator();
				while (iterator.hasNext()) {
					ExamStudentBioDataBO examStudentBioDataBO = (ExamStudentBioDataBO) iterator .next();
					if(examStudentBioDataBO.getSpecializationId()!=0){
						map.put(examStudentBioDataBO.getSpecializationId(), examStudentBioDataBO.getExamSpecializationBO().getName());
					}
				}
			}
		}catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentLoginTransaction#checkHonoursCourse(int)
	 */
	@Override
	public boolean checkHonoursCourse(int studentId,int courseId) throws Exception {
		Session session = null;
		boolean displayLink = false;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HonoursCourse h where h.isActive=1 and h.eligibleCourse.id="+courseId);
			if(query.list() != null && !query.list().isEmpty()){
				displayLink=true;
			}
			return displayLink;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentLoginTransaction#checkMandatoryCertificateCourse(com.kp.cms.bo.admin.AdmAppln)
	 */
	@Override
	public boolean checkMandatoryCertificateCourse(AdmAppln admAppln) throws Exception {
		boolean isCheckMandatory = false;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AssignCertificateCourse certificateCourse where certificateCourse.isActive =1 " +
					" and certificateCourse.course.id="+admAppln.getCourseBySelectedCourseId().getId()+
					" and certificateCourse.academicYear>="+admAppln.getAppliedYear());
			if(query.list()!=null && !query.list().isEmpty()){
				isCheckMandatory = true;
			}
		}catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isCheckMandatory;
	}
	public boolean checkingStudentIsAppliedForSAPExam(int studentId) throws Exception {
		Session session = null;
		boolean displayLink = false;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from UploadSAPMarksBo bo where bo.isActive=1 " +
					" and bo.studentId.id="+studentId);
			if(query.list() != null && !query.list().isEmpty()){
				displayLink=true;
			}
			return displayLink;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	public UploadSAPMarksBo getSAPExamResults(String studentId) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select bo from UploadSAPMarksBo bo"+
					" where bo.isActive=1  and bo.studentId.id="+studentId+
					" order by date desc ");

			query.setMaxResults(1);
			UploadSAPMarksBo bo=(UploadSAPMarksBo)query.uniqueResult();

			return bo;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public Map<Integer, String> getSubjectsListForStudent(LoginForm loginForm) throws Exception {
		Session session = null;
		List<Object[]> subject = null;
		List<Subject> currentClassSubject = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String sqlQuery =   " select subject.id, subject.code, subject.name, classes.id, classes.name, classes.term_number, student.id, curriculum_scheme_duration.academic_year from student " +
					" inner join adm_appln ON student.adm_appln_id = adm_appln.id " +
					" inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id " +
					" inner join classes ON class_schemewise.class_id = classes.id " +
					" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
					" inner join applicant_subject_group on applicant_subject_group.adm_appln_id = adm_appln.id " +
					" inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id and subject_group.is_active=1 " +
					" inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id and subject_group_subjects.is_active=1 " +
					" inner join subject ON subject_group_subjects.subject_id = subject.id and subject.is_active=1 " +
					" where student.is_admitted=1 and adm_appln.is_cancelled=0 " +
					" and student.id=:studentId" +
					" and classes.term_number=:termNo"+
					" group by student.id, subject.id, classes.id " +
					" union all " +
					" select subject.id, subject.code, subject.name, classes.id, classes.name, classes.term_number, student.id, curriculum_scheme_duration.academic_year " +
					" from student " +
					" inner join adm_appln ON student.adm_appln_id = adm_appln.id " +
					" inner join EXAM_student_previous_class_details on EXAM_student_previous_class_details.student_id = student.id " +
					" inner join classes on EXAM_student_previous_class_details.class_id = classes.id and EXAM_student_previous_class_details.scheme_no = classes.term_number " +
					" inner join class_schemewise ON class_schemewise.class_id = classes.id " +
					" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id and EXAM_student_previous_class_details.academic_year = curriculum_scheme_duration.academic_year " +
					" inner join EXAM_student_sub_grp_history on EXAM_student_sub_grp_history.student_id = student.id and EXAM_student_sub_grp_history.scheme_no = classes.term_number " +
					" inner join subject_group on EXAM_student_sub_grp_history.subject_group_id = subject_group.id and subject_group.is_active=1 " +
					" inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id and subject_group_subjects.is_active=1 " +
					" inner join subject ON subject_group_subjects.subject_id = subject.id and subject.is_active=1 " +
					" where student.is_admitted=1 and adm_appln.is_cancelled=0 " +
					" and student.id =:studentId " +
					" and classes.term_number = :termNo"+
					" group by student.id, subject.id, classes.id";
			Query query=session.createSQLQuery(sqlQuery);
			query.setInteger("studentId", loginForm.getStudentId());
			query.setInteger("termNo", Integer.parseInt(loginForm.getSchemeNo()));
			subject= query.list();
			if(subject.size()!=0){
				Iterator<Object[]> itr = subject.iterator();
				while(itr.hasNext()){
					Object[] sub = itr.next();
					subjectMap.put(Integer.parseInt(sub[0].toString()), sub[1].toString()+" - "+sub[2].toString());
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return subjectMap;
	}

	public String getQueryForStudentInternalMarksStudentLogin(LoginForm form, int academicYearOfSemester) throws Exception{
		String query = " SELECT student.id,student.register_no," +
				" EXAM_definition.name as name_3,       " +
				" EXAM_internal_exam_type.id,	  " +
				" EXAM_internal_exam_type.name as name_5,		  " +
				" subject.id,      " +
				" subject.name as name,			" +
				" subject.code,			" +
				" subject.is_theory_practical,			" +
				" subject.is_second_language,      " +
				" if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks) as Theory_Marks_Add,			" +
				" if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks) as Pra_add_Mrks,			" +
				" EXAM_marks_entry_details.theory_marks,	   	" +
				" EXAM_marks_entry_details.practical_marks,			" +
				" upper(personal_data.first_name) as first_name,			" +
				" classes.id,      " +
				" classes.name as name_17,			 " +
				" classes.term_number,      " +
				" EXAM_subject_rule_settings_sub_internal.maximum_mark as maximum_mark,      " +
				" EXAM_definition.month, " +
				" EXAM_sub_definition_coursewise.subject_order, " +
				" EXAM_student_overall_internal_mark_details.theory_total_attendance_mark, " +
				" EXAM_student_overall_internal_mark_details.practical_total_attendance_mark, " +
				" EXAM_student_overall_internal_mark_details.theory_total_mark, " +
				" EXAM_student_overall_internal_mark_details.practical_total_mark," +
				" EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark," +
				" EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark," +
				" EXAM_subject_rule_settings.final_theory_internal_maximum_mark," +
				" if(EXAM_internal_exam_type.id in (3,6,4,5),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks))," +
				" (((if(EXAM_marks_entry_details.theory_marks='AB' || " +
				" EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0," +
				" EXAM_marks_entry_details.theory_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))+((if(EXAM_marks_entry_details.practical_marks='AB' ||      " +
				" EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,     " +
				" EXAM_marks_entry_details.practical_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0)))) as total," +
				" if(EXAM_internal_exam_type.name='Attendance',1,if(EXAM_internal_exam_type.name='Assignment',2,if(EXAM_internal_exam_type.name='Seminar',3,if(if(EXAM_internal_exam_type.name in ('Internal_I','Internal_II'),'CIA TEST', EXAM_internal_exam_type.name)='CIA TEST',4,5)))) as intexam_order,	" +
				" if(EXAM_internal_exam_type.name in ('Internal_I','Internal_II'),'CIA TEST', EXAM_internal_exam_type.name) as int_exam_name,	 " +
				" if((classes.term_number=1 && EXAM_internal_exam_type.name='Attendance'),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),ifnull(theory_total_attendance_mark,practical_total_attendance_mark)) as attendnc,			" +
				" if(EXAM_internal_exam_type.name='Assignment',(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),0) as asgnmnt,			" +
				" if(EXAM_internal_exam_type.name='Seminar',(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),0) as seminar,			" +
				" if(EXAM_internal_exam_type.name='Internal_I',(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),0) as int_1,			" +
				" if(EXAM_internal_exam_type.name='Internal_I',if(EXAM_internal_exam_type.id in (3,6,4,5),(((if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))+((if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks))),0) as int_1_conv,			 " +
				" if(EXAM_internal_exam_type.name='Internal_II',(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),0) as int_2,			 " +
				" if(EXAM_internal_exam_type.name='Internal_II',if(EXAM_internal_exam_type.id in (3,6,4,5),(((if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))+((if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks))),0) as int_2_conv,			" +
				" if(EXAM_internal_exam_type.id in (3,6,4,5),(((if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))+((if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks))) as con_tot,			" +
				" ifnull(EXAM_student_overall_internal_mark_details.theory_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark) as overall_tot,			" +
				" (SELECT   sum(if(attendance_stu.is_present=1 || attendance_stu.is_cocurricular_leave=1,if(atten.hours_held is not null ,1,0),0)) " +
				" from attendance_class attendance_cla              " +
				" INNER JOIN                " +
				" class_schemewise class_schem               " +
				" ON (attendance_cla.class_schemewise_id = class_schem.id)          " +
				" INNER JOIN             " +
				" attendance atten         " +
				" ON (attendance_cla.attendance_id = atten.id)       " +
				" INNER JOIN          " +
				" attendance_student attendance_stu       " +
				" ON (attendance_stu.attendance_id = atten.id)       			 " +
				" INNER JOIN       " +
				" curriculum_scheme_duration curriculum_scheme_d      " +
				" ON (class_schem.curriculum_scheme_duration_id =             " +
				" curriculum_scheme_d.id)               " +
				" where attendance_stu.student_id=student.id			" +
				" and class_schem.class_id=classes.id			 	" +
				" and class_schem.id=class_schemewise.id			 		" +
				" and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year			" +
				" and atten.subject_id=subject.id			" +
				" and atten.is_canceled=0 			 " +
				" group by attendance_stu.student_id,atten.subject_id) as HA,			 " +
				" (SELECT sum(if(atten.hours_held is not null ,1,0))  " +
				" from attendance_class attendance_cla              " +
				" INNER JOIN                  " +
				" class_schemewise class_schem              " +
				" ON (attendance_cla.class_schemewise_id = class_schem.id)          " +
				" INNER JOIN             " +
				" attendance atten          " +
				" ON (attendance_cla.attendance_id = atten.id)      " +
				" INNER JOIN         " +
				" attendance_student attendance_stu      " +
				" ON (attendance_stu.attendance_id = atten.id)       			 " +
				" INNER JOIN       " +
				" curriculum_scheme_duration curriculum_scheme_d      " +
				" ON (class_schem.curriculum_scheme_duration_id = curriculum_scheme_d.id)               " +
				" where attendance_stu.student_id=student.id			" +
				" and class_schem.class_id=classes.id			 	 " +
				" and class_schem.id=class_schemewise.id			 		 " +
				" and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year			" +
				" and atten.subject_id=subject.id			 " +
				" and atten.is_canceled=0 			" +
				" group by attendance_stu.student_id,atten.subject_id) as hh,			";

		/**
		 * Modified by Arun Sudhakaran
		 * Conditions added for 
		 * 		1) Till ODD sem 2017
		 * 		2) From Even sem 2017
		 * There has been a modification in finding hours held for these two conditions
		 * In first condition previous query was used where hours held will be always considered as 1 even if multiple periods were conducted one day
		 * In second condition it has been changed to get all the period counts 
		 */
		if(academicYearOfSemester > 2017 || (academicYearOfSemester == 2017 && (Integer.parseInt(form.getSchemeNo())%2 == 0))) {	//	for even sem
			query += "round(((SELECT   sum(if(attendance_stu.is_present=1 || attendance_stu.is_cocurricular_leave=1,ifnull(atten.hours_held,0),0)) " +
					"from attendance_class attendance_cla " +
					"INNER JOIN class_schemewise class_schem ON (attendance_cla.class_schemewise_id = class_schem.id) " +
					"INNER JOIN attendance atten ON (attendance_cla.attendance_id = atten.id) " +
					"INNER JOIN attendance_student attendance_stu ON (attendance_stu.attendance_id = atten.id) " +
					"INNER JOIN curriculum_scheme_duration curriculum_scheme_d ON (class_schem.curriculum_scheme_duration_id = curriculum_scheme_d.id) " +
					"where attendance_stu.student_id=student.id " +
					"	and class_schem.class_id=classes.id " +
					"	and class_schem.id=class_schemewise.id " +
					"	and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year " +
					"	and atten.subject_id=subject.id " +
					"	and atten.is_canceled=0 " +
					"	group by attendance_stu.student_id,atten.subject_id)" +
					"/" +
					"(SELECT sum(ifnull(atten.hours_held,0)) " +
					"	 from attendance_class attendance_cla " +
					"INNER JOIN class_schemewise class_schem ON (attendance_cla.class_schemewise_id = class_schem.id) " +
					"INNER JOIN attendance atten ON (attendance_cla.attendance_id = atten.id) " +                     
					"INNER JOIN attendance_student attendance_stu ON (attendance_stu.attendance_id = atten.id) " +                     
					"INNER JOIN curriculum_scheme_duration curriculum_scheme_d ON (class_schem.curriculum_scheme_duration_id = curriculum_scheme_d.id) " +
					"where attendance_stu.student_id=student.id " +
					"	and class_schem.class_id=classes.id " +
					"	and class_schem.id=class_schemewise.id " +
					"	and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year " +
					"	and atten.subject_id=subject.id " +
					"	and atten.is_canceled=0 " +
					"	group by attendance_stu.student_id,atten.subject_id))*100,2) as per_att, ";
		}
		else if(academicYearOfSemester < 2017 || (academicYearOfSemester == 2017 && (Integer.parseInt(form.getSchemeNo())%2 != 0))) {
			query += "round(((SELECT   sum(if(attendance_stu.is_present=1 || attendance_stu.is_cocurricular_leave=1,if(atten.hours_held is not null ,1,0),0)) " +
					"from attendance_class attendance_cla " +
					"INNER JOIN class_schemewise class_schem ON (attendance_cla.class_schemewise_id = class_schem.id) " +
					"INNER JOIN attendance atten ON (attendance_cla.attendance_id = atten.id) " +
					"INNER JOIN attendance_student attendance_stu ON (attendance_stu.attendance_id = atten.id) " +
					"INNER JOIN curriculum_scheme_duration curriculum_scheme_d ON (class_schem.curriculum_scheme_duration_id = curriculum_scheme_d.id) " +
					"where attendance_stu.student_id=student.id " +
					"	and class_schem.class_id=classes.id " +
					"	and class_schem.id=class_schemewise.id " +
					"	and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year " +
					"	and atten.subject_id=subject.id " +
					"	and atten.is_canceled=0 " +
					"	group by attendance_stu.student_id,atten.subject_id)" +
					"/" +
					"(SELECT   sum(if(atten.hours_held is not null ,1,0)) " +
					"from attendance_class attendance_cla " +
					"INNER JOIN class_schemewise class_schem ON (attendance_cla.class_schemewise_id = class_schem.id) " +
					"INNER JOIN attendance atten ON (attendance_cla.attendance_id = atten.id) " +
					"INNER JOIN attendance_student attendance_stu ON (attendance_stu.attendance_id = atten.id) " +
					"INNER JOIN curriculum_scheme_duration curriculum_scheme_d ON (class_schem.curriculum_scheme_duration_id = curriculum_scheme_d.id) " +
					"where attendance_stu.student_id=student.id " +
					"	and class_schem.class_id=classes.id " +
					"	and class_schem.id=class_schemewise.id " +
					"	and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year " +
					"	and atten.subject_id=subject.id " +
					"	and atten.is_canceled=0 " +
					"	group by attendance_stu.student_id,atten.subject_id))*100,2) as per_att, ";
		}

		/*
		" round(((SELECT   sum(if(attendance_stu.is_present=1 || attendance_stu.is_cocurricular_leave=1,if(atten.hours_held is not null ,1,0),0)) "+
		" from attendance_class attendance_cla  "+            
		" INNER JOIN  "+              
		" class_schemewise class_schem "+              
		" ON (attendance_cla.class_schemewise_id = class_schem.id)   "+       
		" INNER JOIN   "+          
		" attendance atten    "+     
		" ON (attendance_cla.attendance_id = atten.id) "+       
		" INNER JOIN "+        
		" attendance_student attendance_stu   "+    
		" ON (attendance_stu.attendance_id = atten.id) "+      			 
		" INNER JOIN   "+    
		" curriculum_scheme_duration curriculum_scheme_d "+     
		" ON (class_schem.curriculum_scheme_duration_id =   "+          
		" curriculum_scheme_d.id)   "+            
	//inner join period period ON (period.class_schemewise_id =  class_schem.id)			 
	//INNER JOIN attendance_period attendance_period ON (attendance_period.period_id = period.id)													
	//and (attendance_period.attendance_id = atten.id )             			
		" where attendance_stu.student_id=student.id "+			
		 " and class_schem.class_id=classes.id	"+		 	
		" and class_schem.id=class_schemewise.id	"+		 		
		" and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year	"+		
		" and atten.subject_id=subject.id	"+		
		" and atten.is_canceled=0 "+			 
		" group by attendance_stu.student_id,atten.subject_id)/(SELECT sum(if(atten.hours_held is not null ,1,0))  "+
		" from attendance_class attendance_cla    "+          
		" INNER JOIN   "+               
		" class_schemewise class_schem   "+           
		" ON (attendance_cla.class_schemewise_id = class_schem.id) "+         
		" INNER JOIN    "+         
		" attendance atten   "+       
		" ON (attendance_cla.attendance_id = atten.id)  "+    
		" INNER JOIN    "+     
		" attendance_student attendance_stu   "+   
		" ON (attendance_stu.attendance_id = atten.id)  "+     			 
		" INNER JOIN    "+   
		" curriculum_scheme_duration curriculum_scheme_d    "+  
		" ON (class_schem.curriculum_scheme_duration_id = curriculum_scheme_d.id)       "+        
		//inner join  period period ON (period.class_schemewise_id =  class_schem.id)			
		// INNER JOIN  attendance_period attendance_period ON (attendance_period.period_id = period.id)				 
		// and (attendance_period.attendance_id = atten.id )             			
		" where attendance_stu.student_id=student.id	"+		
		" and class_schem.class_id=classes.id	"+		 	 
		" and class_schem.id=class_schemewise.id	"+		 		 
		" and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year	"+		
		" and atten.subject_id=subject.id	"+		 
		" and atten.is_canceled=0 			"+
		" group by attendance_stu.student_id,atten.subject_id))*100,2) as per_att,";
		 */

		query += " EXAM_subject_rule_settings_sub_internal.maximum_mark as con_tot_max,        " +
				" EXAM_subject_rule_settings_sub_internal.entered_max_mark as entered_maximum_mark, " +
				" EXAM_subject_rule_settings.final_practical_internal_maximum_mark," +
				" EXAM_subject_rule_settings_att.id as eXAM_subject_rule_settings_att_id"+
				" FROM class_schemewise class_schemewise                                                  " +
				" INNER JOIN                                                     " +
				" classes classes                                                  " +
				" ON (class_schemewise.class_id = classes.id)                                              " +
				" INNER JOIN                                                 " +
				" EXAM_marks_entry EXAM_marks_entry                                              " +
				" ON (EXAM_marks_entry.class_id =                                                     " +
				" classes.id)                                           " +
				" INNER JOIN                                             " +
				" EXAM_marks_entry_details EXAM_marks_entry_details                                          " +
				" ON (EXAM_marks_entry_details.marks_entry_id =                                                 " +
				" EXAM_marks_entry.id)                                                  " +
				" INNER JOIN                                        " +
				" EXAM_definition EXAM_definition                                      " +
				" ON (EXAM_marks_entry.exam_id = EXAM_definition.id)                                  " +
				" INNER JOIN                                     " +
				" EXAM_internal_exam_type EXAM_internal_exam_type                                  " +
				" ON (EXAM_definition.internal_exam_type_id =                                         " +
				" EXAM_internal_exam_type.id)                      " +
				" INNER JOIN                          " +
				" subject subject                      " +
				" ON (EXAM_marks_entry_details.subject_id = subject.id                      " +
				" and subject.is_active=1)" +
				" INNER JOIN                     " +
				" student student ON (EXAM_marks_entry.student_id = student.id) and student.is_admitted=1 " +
				" INNER JOIN                         " +
				" EXAM_subject_rule_settings_sub_internal EXAM_subject_rule_settings_sub_internal " +
				" ON (EXAM_subject_rule_settings_sub_internal.internal_exam_type_id = EXAM_internal_exam_type.id)                   " +
				" INNER JOIN                             " +
				" EXAM_subject_rule_settings EXAM_subject_rule_settings                          " +
				" ON (EXAM_subject_rule_settings_sub_internal.subject_rule_settings_id = EXAM_subject_rule_settings.id)                             " +
				" and (EXAM_subject_rule_settings.scheme_no=classes.term_number) and EXAM_subject_rule_settings.course_id = classes.course_id 																	" +
				" and EXAM_subject_rule_settings.subject_id = subject.id and EXAM_subject_rule_settings.academic_year = EXAM_definition.academic_year              " +

		 " left join EXAM_subject_rule_settings_attendance EXAM_subject_rule_settings_att "+
		 " on EXAM_subject_rule_settings_att.subject_rule_settings_id = EXAM_subject_rule_settings.id "+

		" INNER JOIN                 " +
		" adm_appln adm_appln              " +
		" ON (student.adm_appln_id = adm_appln.id) and adm_appln.is_cancelled=0          " +
		" INNER JOIN             " +
		" personal_data personal_data          " +
		" ON (adm_appln.personal_data_id = personal_data.id)					" +
		" LEFT JOIN EXAM_student_overall_internal_mark_details EXAM_student_overall_internal_mark_details					" +
		" ON(EXAM_student_overall_internal_mark_details.class_id=classes.id)					" +
		" AND EXAM_student_overall_internal_mark_details.subject_id=subject.id					 " +
		" and EXAM_student_overall_internal_mark_details.student_id=student.id					        " +
		" INNER JOIN         " +
		" curriculum_scheme_duration curriculum_scheme_duration      " +
		" ON (class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id)              " +
		" left JOIN             " +
		" EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise         " +
		" ON (EXAM_sub_definition_coursewise.subject_id = subject.id)      " +
		" left JOIN      " +
		" EXAM_subject_sections EXAM_subject_sections      " +
		" ON (EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id)						" +
		" where  student.id not in(select student_id from EXAM_student_detention_rejoin_details where (detain=1 || discontinued=1)and rejoin is null and scheme_no=classes.term_number)				" +
		" and student.id=" + form .getStudentId()+
		" and subject.id=" + form.getSubjectId()+
		" and classes.term_number=" +form.getSchemeNo()+
		" group by student.id,subject.id, EXAM_internal_exam_type.id,EXAM_definition.id";

		return query;
	}
	public String getQueryForStudentInternalMarksStudentLoginGrievance(LoginForm form,String classid) throws Exception{
		String query = " SELECT student.id,student.register_no," +
				" EXAM_definition.name as name_3,       " +
				" EXAM_internal_exam_type.id,	  " +
				" EXAM_internal_exam_type.name as name_5,		  " +
				" subject.id,      " +
				" subject.name as name,			" +
				" subject.code,			" +
				" subject.is_theory_practical,			" +
				" subject.is_second_language,      " +
				" if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks) as Theory_Marks_Add,			" +
				" if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks) as Pra_add_Mrks,			" +
				" EXAM_marks_entry_details.theory_marks,	   	" +
				" EXAM_marks_entry_details.practical_marks,			" +
				" upper(personal_data.first_name) as first_name,			" +
				" classes.id,      " +
				" classes.name as name_17,			 " +
				" classes.term_number,      " +
				" EXAM_subject_rule_settings_sub_internal.maximum_mark as maximum_mark,      " +
				" EXAM_definition.month, " +
				" EXAM_sub_definition_coursewise.subject_order, " +
				" EXAM_student_overall_internal_mark_details.theory_total_attendance_mark, " +
				" EXAM_student_overall_internal_mark_details.practical_total_attendance_mark, " +
				" EXAM_student_overall_internal_mark_details.theory_total_mark, " +
				" EXAM_student_overall_internal_mark_details.practical_total_mark," +
				" EXAM_student_overall_internal_mark_details.practical_total_sub_internal_mark," +
				" EXAM_student_overall_internal_mark_details.theory_total_sub_internal_mark," +
				" EXAM_subject_rule_settings.final_theory_internal_maximum_mark," +
				" if(EXAM_internal_exam_type.id in (3,6,4,5),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks))," +
				" (((if(EXAM_marks_entry_details.theory_marks='AB' || " +
				" EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0," +
				" EXAM_marks_entry_details.theory_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))+((if(EXAM_marks_entry_details.practical_marks='AB' ||      " +
				" EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,     " +
				" EXAM_marks_entry_details.practical_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0)))) as total," +
				" if(EXAM_internal_exam_type.name='Attendance',1,if(EXAM_internal_exam_type.name='Assignment',2,if(EXAM_internal_exam_type.name='Seminar',3,if(if(EXAM_internal_exam_type.name in ('Internal_I','Internal_II'),'CIA TEST', EXAM_internal_exam_type.name)='CIA TEST',4,5)))) as intexam_order,	" +
				" if(EXAM_internal_exam_type.name in ('Internal_I','Internal_II'),'CIA TEST', EXAM_internal_exam_type.name) as int_exam_name,	 " +
				" if((classes.term_number=1 && EXAM_internal_exam_type.name='Attendance'),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),ifnull(theory_total_attendance_mark,practical_total_attendance_mark)) as attendnc,			" +
				" if(EXAM_internal_exam_type.name='Assignment',(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),0) as asgnmnt,			" +
				" if(EXAM_internal_exam_type.name='Seminar',(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),0) as seminar,			" +
				" if(EXAM_internal_exam_type.name='Internal_I',(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),0) as int_1,			" +
				" if(EXAM_internal_exam_type.name='Internal_I',if(EXAM_internal_exam_type.id in (3,6,4,5),(((if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))+((if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks))),0) as int_1_conv,			 " +
				" if(EXAM_internal_exam_type.name='Internal_II',(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)),0) as int_2,			 " +
				" if(EXAM_internal_exam_type.name='Internal_II',if(EXAM_internal_exam_type.id in (3,6,4,5),(((if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))+((if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks))),0) as int_2_conv,			" +
				" if(EXAM_internal_exam_type.id in (3,6,4,5),(((if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))+((if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks)*ifnull(EXAM_subject_rule_settings_sub_internal.maximum_mark,0))/			" +
				" ifnull(EXAM_subject_rule_settings_sub_internal.entered_max_mark,0))),(if(EXAM_marks_entry_details.theory_marks='AB' || EXAM_marks_entry_details.theory_marks='NP' || EXAM_marks_entry_details.theory_marks is null || EXAM_marks_entry_details.theory_marks='',0,EXAM_marks_entry_details.theory_marks)+if(EXAM_marks_entry_details.practical_marks='AB' || EXAM_marks_entry_details.practical_marks='NP' || EXAM_marks_entry_details.practical_marks is null || EXAM_marks_entry_details.practical_marks='',0,EXAM_marks_entry_details.practical_marks))) as con_tot,			" +
				" ifnull(EXAM_student_overall_internal_mark_details.theory_total_mark,EXAM_student_overall_internal_mark_details.practical_total_mark) as overall_tot,			" +
				" (SELECT   sum(if(attendance_stu.is_present=1 || attendance_stu.is_cocurricular_leave=1,if(atten.hours_held is not null ,1,0),0)) " +
				" from attendance_class attendance_cla              " +
				" INNER JOIN                " +
				" class_schemewise class_schem               " +
				" ON (attendance_cla.class_schemewise_id = class_schem.id)          " +
				" INNER JOIN             " +
				" attendance atten         " +
				" ON (attendance_cla.attendance_id = atten.id)       " +
				" INNER JOIN          " +
				" attendance_student attendance_stu       " +
				" ON (attendance_stu.attendance_id = atten.id)       			 " +
				" INNER JOIN       " +
				" curriculum_scheme_duration curriculum_scheme_d      " +
				" ON (class_schem.curriculum_scheme_duration_id =             " +
				" curriculum_scheme_d.id)               " +
				//" inner join period period ON (period.class_schemewise_id =  class_schem.id)			 " +
				//" INNER JOIN attendance_period attendance_period ON (attendance_period.period_id = period.id)													" +
				//" and (attendance_period.attendance_id = atten.id )             			" +
				" where attendance_stu.student_id=student.id			" +
				" and class_schem.class_id=classes.id			 	" +
				" and class_schem.id=class_schemewise.id			 		" +
				" and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year			" +
				" and atten.subject_id=subject.id			" +
				" and atten.is_canceled=0 			 " +
				" group by attendance_stu.student_id,atten.subject_id) as HA,			 " +
				" (SELECT sum(if(atten.hours_held is not null ,1,0))  " +
				" from attendance_class attendance_cla              " +
				" INNER JOIN                  " +
				" class_schemewise class_schem              " +
				" ON (attendance_cla.class_schemewise_id = class_schem.id)          " +
				" INNER JOIN             " +
				" attendance atten          " +
				" ON (attendance_cla.attendance_id = atten.id)      " +
				" INNER JOIN         " +
				" attendance_student attendance_stu      " +
				" ON (attendance_stu.attendance_id = atten.id)       			 " +
				" INNER JOIN       " +
				" curriculum_scheme_duration curriculum_scheme_d      " +
				" ON (class_schem.curriculum_scheme_duration_id = curriculum_scheme_d.id)               " +
				//" inner join  period period ON (period.class_schemewise_id =  class_schem.id)			" +
				//" INNER JOIN  attendance_period attendance_period ON (attendance_period.period_id = period.id)				 " +
				//" and (attendance_period.attendance_id = atten.id )             			" +
				" where attendance_stu.student_id=student.id			" +
				" and class_schem.class_id=classes.id			 	 " +
				" and class_schem.id=class_schemewise.id			 		 " +
				" and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year			" +
				" and atten.subject_id=subject.id			 " +
				" and atten.is_canceled=0 			" +
				" group by attendance_stu.student_id,atten.subject_id) as hh,			" +

		" round(((SELECT   sum(if(attendance_stu.is_present=1 || attendance_stu.is_cocurricular_leave=1,if(atten.hours_held is not null ,1,0),0)) "+
		" from attendance_class attendance_cla  "+            
		" INNER JOIN  "+              
		" class_schemewise class_schem "+              
		" ON (attendance_cla.class_schemewise_id = class_schem.id)   "+       
		" INNER JOIN   "+          
		" attendance atten    "+     
		" ON (attendance_cla.attendance_id = atten.id) "+       
		" INNER JOIN "+        
		" attendance_student attendance_stu   "+    
		" ON (attendance_stu.attendance_id = atten.id) "+      			 
		" INNER JOIN   "+    
		" curriculum_scheme_duration curriculum_scheme_d "+     
		" ON (class_schem.curriculum_scheme_duration_id =   "+          
		" curriculum_scheme_d.id)   "+            
		//inner join period period ON (period.class_schemewise_id =  class_schem.id)			 
		//INNER JOIN attendance_period attendance_period ON (attendance_period.period_id = period.id)													
		//and (attendance_period.attendance_id = atten.id )             			
		" where attendance_stu.student_id=student.id "+			
		" and class_schem.class_id=classes.id	"+		 	
		" and class_schem.id=class_schemewise.id	"+		 		
		" and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year	"+		
		" and atten.subject_id=subject.id	"+		
		" and atten.is_canceled=0 "+			 
		" group by attendance_stu.student_id,atten.subject_id)/(SELECT sum(if(atten.hours_held is not null ,1,0))  "+
		" from attendance_class attendance_cla    "+          
		" INNER JOIN   "+               
		" class_schemewise class_schem   "+           
		" ON (attendance_cla.class_schemewise_id = class_schem.id) "+         
		" INNER JOIN    "+         
		" attendance atten   "+       
		" ON (attendance_cla.attendance_id = atten.id)  "+    
		" INNER JOIN    "+     
		" attendance_student attendance_stu   "+   
		" ON (attendance_stu.attendance_id = atten.id)  "+     			 
		" INNER JOIN    "+   
		" curriculum_scheme_duration curriculum_scheme_d    "+  
		" ON (class_schem.curriculum_scheme_duration_id = curriculum_scheme_d.id)       "+        
		//inner join  period period ON (period.class_schemewise_id =  class_schem.id)			
		// INNER JOIN  attendance_period attendance_period ON (attendance_period.period_id = period.id)				 
		// and (attendance_period.attendance_id = atten.id )             			
		" where attendance_stu.student_id=student.id	"+		
		" and class_schem.class_id=classes.id	"+		 	 
		" and class_schem.id=class_schemewise.id	"+		 		 
		" and curriculum_scheme_d.academic_year=curriculum_scheme_duration.academic_year	"+		
		" and atten.subject_id=subject.id	"+		 
		" and atten.is_canceled=0 			"+
		" group by attendance_stu.student_id,atten.subject_id))*100,2) as per_att,"+

		" EXAM_subject_rule_settings_sub_internal.maximum_mark as con_tot_max,        " +
		" EXAM_subject_rule_settings_sub_internal.entered_max_mark as entered_maximum_mark, " +
		" EXAM_subject_rule_settings.final_practical_internal_maximum_mark," +
		" EXAM_subject_rule_settings_att.id as eXAM_subject_rule_settings_att_id,"+
		" CAST(adm_appln.selected_course_id AS UNSIGNED INTEGER) AS course_id "+
		" FROM class_schemewise class_schemewise                                                  " +
		" INNER JOIN                                                     " +
		" classes classes                                                  " +
		" ON (class_schemewise.class_id = classes.id)                                              " +
		" INNER JOIN                                                 " +
		" EXAM_marks_entry EXAM_marks_entry                                              " +
		" ON (EXAM_marks_entry.class_id =                                                     " +
		" classes.id)                                           " +
		" INNER JOIN                                             " +
		" EXAM_marks_entry_details EXAM_marks_entry_details                                          " +
		" ON (EXAM_marks_entry_details.marks_entry_id =                                                 " +
		" EXAM_marks_entry.id)                                                  " +
		" INNER JOIN                                        " +
		" EXAM_definition EXAM_definition                                      " +
		" ON (EXAM_marks_entry.exam_id = EXAM_definition.id)                                  " +
		" INNER JOIN                                     " +
		" EXAM_internal_exam_type EXAM_internal_exam_type                                  " +
		" ON (EXAM_definition.internal_exam_type_id =                                         " +
		" EXAM_internal_exam_type.id)                      " +
		" INNER JOIN                          " +
		" subject subject                      " +
		" ON (EXAM_marks_entry_details.subject_id = subject.id                      " +
		" and subject.is_active=1)" +
		" INNER JOIN                     " +
		" student student ON (EXAM_marks_entry.student_id = student.id) and student.is_admitted=1 " +
		" INNER JOIN                         " +
		" EXAM_subject_rule_settings_sub_internal EXAM_subject_rule_settings_sub_internal " +
		" ON (EXAM_subject_rule_settings_sub_internal.internal_exam_type_id = EXAM_internal_exam_type.id)                   " +
		" INNER JOIN                             " +
		" EXAM_subject_rule_settings EXAM_subject_rule_settings                          " +
		" ON (EXAM_subject_rule_settings_sub_internal.subject_rule_settings_id = EXAM_subject_rule_settings.id)                             " +
		" and (EXAM_subject_rule_settings.scheme_no=classes.term_number) and EXAM_subject_rule_settings.course_id = classes.course_id 																	" +
		" and EXAM_subject_rule_settings.subject_id = subject.id and EXAM_subject_rule_settings.academic_year = EXAM_definition.academic_year              " +

		 " left join EXAM_subject_rule_settings_attendance EXAM_subject_rule_settings_att "+
		 " on EXAM_subject_rule_settings_att.subject_rule_settings_id = EXAM_subject_rule_settings.id "+

		" INNER JOIN                 " +
		" adm_appln adm_appln              " +
		" ON (student.adm_appln_id = adm_appln.id) and adm_appln.is_cancelled=0          " +
		" INNER JOIN             " +
		" personal_data personal_data          " +
		" ON (adm_appln.personal_data_id = personal_data.id)					" +
		" LEFT JOIN EXAM_student_overall_internal_mark_details EXAM_student_overall_internal_mark_details					" +
		" ON(EXAM_student_overall_internal_mark_details.class_id=classes.id)					" +
		" AND EXAM_student_overall_internal_mark_details.subject_id=subject.id					 " +
		" and EXAM_student_overall_internal_mark_details.student_id=student.id					        " +
		" INNER JOIN         " +
		" curriculum_scheme_duration curriculum_scheme_duration      " +
		" ON (class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id)              " +
		" left JOIN             " +
		" EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise         " +
		" ON (EXAM_sub_definition_coursewise.subject_id = subject.id)      " +
		" left JOIN      " +
		" EXAM_subject_sections EXAM_subject_sections      " +
		" ON (EXAM_sub_definition_coursewise.subject_section_id = EXAM_subject_sections.id)						" +
		" where  student.id not in(select student_id from EXAM_student_detention_rejoin_details where (detain=1 || discontinued=1)and rejoin is null and scheme_no=classes.term_number)				" +
		" and student.id=" + form .getStudentId()+
		//" and subject.id=" + form.getSubjectId()+
		//" and classes.term_number=" +form.getSchemeNo()+
		" and classes.id=" +Integer.parseInt(classid)+
		//" and EXAM_definition.id=" +Integer.parseInt(form.getInternalExamId())+
		" group by student.id,subject.id, EXAM_internal_exam_type.id,EXAM_definition.id";

		return query;
	}



	public List<Object[]> getDataForStudentInternalMarksStudentLogin(String queryForMarks) throws Exception {
		Session session = null;
		List<Object[]> internalMarksCardData = null;
		try {			
			session = HibernateUtil.getSession();
			internalMarksCardData=session.createSQLQuery(queryForMarks).list();
			return internalMarksCardData;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public boolean saveGrievance(List<GrievanceRemarkBo> grievanceRemarkBoList) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isAdded=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			Iterator<GrievanceRemarkBo> itr = grievanceRemarkBoList.iterator();
			while(itr.hasNext()){
				GrievanceRemarkBo GrievanceRemarkBo = itr.next();
				session.save(GrievanceRemarkBo);
			}


			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	public Integer getHodIdBySubjectId(Integer subjectId) throws Exception {
		Session session = null;
		Integer hodId=0;
		try {
			session = HibernateUtil.getSession();
			String str = "select users.id as user_id "+
					"from users "+
					"   inner join employee ON users.employee_id = employee.id"+
					"   inner join roles ON users.roles_id = roles.id"+
					"  where employee.department_id = (select subject.department_id from subject where subject.id = "+subjectId+")"+
					" and roles.id = 51 ";
			hodId=(Integer) session.createSQLQuery(str).uniqueResult();
			/* Query query = session.createQuery("select u.id from Users u"+
                           "where u.employee.department.id=(select s.department.id from Subject s where s.id=1)"+
                           "and u.roles.id=51"); */
			//hard coded role id for hod
			// hodId = (String) query.uniqueResult();

			if(hodId!=null)	  
				return hodId;
		} catch (Exception e) { 
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return null;
	}
	public GrievanceRemarkBo checkDuplicates(int examId,int studentId,int subjectId) throws Exception {
		Session session = null;
		GrievanceRemarkBo dupGrievanceRemarkBo;
		try {
			session = HibernateUtil.getSession();
			String str = "from GrievanceRemarkBo g where g.student.id="+studentId+"  and g.examDefinition.id="+examId+" and g.subject.id="+subjectId;
			dupGrievanceRemarkBo=(GrievanceRemarkBo) session.createQuery(str).uniqueResult();

			return dupGrievanceRemarkBo;
		} catch (Exception e) { 
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}
	public GrievanceNumber getGrievancePrifixByYear(String year) throws Exception {
		Session session = null;
		GrievanceNumber grievanceNumber;
		try {
			session = HibernateUtil.getSession();
			String str = "from GrievanceNumber gn where gn.year="+year;
			grievanceNumber=(GrievanceNumber) session.createQuery(str).uniqueResult();

			return grievanceNumber;
		} catch (Exception e) { 
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public boolean updateGrievanceMaster(GrievanceNumber grievanceNumber,Integer curentNo) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isUpdated=false;
		GrievanceNumber grievanceNumberr;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			grievanceNumberr =(GrievanceNumber) session.get(GrievanceNumber.class,grievanceNumber.getId() );
			if(grievanceNumberr!=null)
				grievanceNumberr.setCurrentNo(curentNo+1);
			session.update(grievanceNumberr);
			tx.commit();
			session.flush();
			session.close();
			isUpdated = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isUpdated;
	}

	public List<GrievanceRemarkBo> getGrievanceList(LoginForm loginForm )throws Exception {
		Session session=null;
		List<GrievanceRemarkBo> grievanceRemarkBoList=null;
		try{
			session= HibernateUtil.getSession();
			String str="from GrievanceRemarkBo bo where bo.student.id=" +loginForm.getStudentId();
			Query query = session.createQuery(str);
			grievanceRemarkBoList=  query.list();

			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return grievanceRemarkBoList;
	}

	public GrievanceRemarkBo getGrievanceNoByStuentId(int studentId) throws Exception {
		Session session = null;
		GrievanceRemarkBo dupGrievanceRemarkBo;
		try {
			session = HibernateUtil.getSession();
			String str = "select distinct g from GrievanceRemarkBo g where g.student.id="+studentId+" group by g.student.id";
			dupGrievanceRemarkBo=(GrievanceRemarkBo) session.createQuery(str).uniqueResult();

			return dupGrievanceRemarkBo;
		} catch (Exception e) { 
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public boolean saveInternalGrievance(List<InternalExamGrievanceBo> grievanceRemarkBoList) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isAdded=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			Iterator<InternalExamGrievanceBo> itr = grievanceRemarkBoList.iterator();
			while(itr.hasNext()){
				InternalExamGrievanceBo internalExamGrievanceBo = itr.next();
				session.save(internalExamGrievanceBo);
			}
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	public boolean saveServiceLearningActivity(ServiceLearningActivityBo serviceLearningActivityBo) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isAdded=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(serviceLearningActivityBo);
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	public boolean isDupServiceLearningActivity(ServiceLearningActivityBo serviceLearningActivityBo) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isDuplicate=false;
		ServiceLearningActivityBo serviceLearningActivityBo1;
		try{

			session= HibernateUtil.getSession();
			String str="from ServiceLearningActivityBo bo where bo.programmeEntryBo.id=" +serviceLearningActivityBo.getProgrammeEntryBo().getId()+" and bo.student.id="+serviceLearningActivityBo.getStudent().getId()+
					"and bo.startDate = '"+serviceLearningActivityBo.getStartDate()+"' and bo.endDate = '"+serviceLearningActivityBo.getEndDate()+"'";
			Query query = session.createQuery(str);
			serviceLearningActivityBo1 =  (ServiceLearningActivityBo) query.uniqueResult();
			session.flush();
			session.close();
			if(serviceLearningActivityBo1!=null){
				isDuplicate = true;
			}else{
				isDuplicate = false;
			}

		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isDuplicate;
	}

	public List<ServiceLearningActivityBo> getServiceLearningActivity(LoginForm loginForm )throws Exception {
		Session session=null;
		List<ServiceLearningActivityBo> serviceLearningActivityBoList=null;
		try{
			session= HibernateUtil.getSession();
			String str="from ServiceLearningActivityBo bo where bo.student.id=" +loginForm.getStudentId();
			Query query = session.createQuery(str);
			serviceLearningActivityBoList=  query.list();

			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return serviceLearningActivityBoList;
	}
	@Override
	public List getDataForQuery(String listQuery)
			throws Exception {
		Session session=null;
		List<Period> list=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery(listQuery);
			list=query.list();
			return list;
		}catch (Exception e) {
			log.error("error while retriving Periods.."+e);
			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}
	@Override
	public boolean checkTimeTableHistory(String classId) throws Exception {
		boolean historyExists=false;
		Session session = null;
		List<TTClassesHistory> ttClassHistoryList = null;
		try {
			session = HibernateUtil.getSession();
			ttClassHistoryList = session.createQuery("from TTClassesHistory tc where tc.classSchemewise.id=:classSchemeId").setInteger("classSchemeId",
					Integer.parseInt(classId)).list();
			if(ttClassHistoryList!=null && !ttClassHistoryList.isEmpty())
				historyExists=true;
			return historyExists;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public List<Object[]> getPaymentDetails(int categoryId,int courseId,int termNo,Integer academicYear) throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String s = "select fee_account_assignment.amount,fee_account_assignment.fee_heading_id,fee_account_assignment.fee_category_id  from fee_account_assignment " +
					" inner join fee ON fee.id = fee_account_assignment.fee_id  " +
					" inner join fee_category ON fee_category.id = fee_account_assignment.fee_category_id  " +
					" inner join fee_heading ON fee_heading.id = fee_account_assignment.fee_heading_id " +
					" where fee_category.id = :categoryId  " +
					" and fee.course_id = :courseId" +
					" and fee.semester_no = :termNo" +
					" and fee.academic_year = :academicYear";
			Query query = session.createSQLQuery(s)
					.setInteger("categoryId", categoryId)
					.setInteger("courseId", courseId)
					.setInteger("termNo", termNo)
					.setInteger("academicYear", academicYear);
			list = query.list();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public FeeCategory getDetails(int feeCatId) throws Exception {
		FeeCategory cate = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			cate = (FeeCategory) session.get(FeeCategory.class, feeCatId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return cate;
	}
	@Override
	public FeeHeading getHeadingStu(int feeheading) throws Exception {
		FeeHeading head = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			head = (FeeHeading) session.get(FeeHeading.class, feeheading);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return head;
	}
	@Override
	public Student getStudentDetails(String studentid) throws Exception {
		Session session = null;
		Student student = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from Student s where s.id = :studentid";
			Query query = session.createQuery(s)
					.setString("studentid", studentid);
			student = (Student)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return student;
	}
	@Override
	public String generateCandidateRefNoOnline(CandidatePGIDetails bo,LoginForm loginForm)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		String candidateRefNo="";
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(bo!=null){
				session.save(bo);
			}
			transaction.commit();
			//			session.flush();
			int savedId=bo.getId();
			if(savedId>0){
				transaction=session.beginTransaction();
				if(!loginForm.getCourseId().equalsIgnoreCase("21")){
					candidateRefNo="BMC"+String.valueOf(savedId);
				}else {
					candidateRefNo="BMCMBA"+String.valueOf(savedId);
				}
				System.out.println("$#$#$#$#$#CANDIDATE REFERENCE NUMBER CHANGED$#$#$#$#$#");
				//				CandidatePGIDetails savedBo=(CandidatePGIDetails)session.get(CandidatePGIDetails.class, savedId);
				bo.setCandidateRefNo(candidateRefNo);
				if(!loginForm.getCourseId().equalsIgnoreCase("21")){
					bo.setTxnRefNo(CMSConstants.PGI_MERCHANT_ID_FEDERAL+candidateRefNo);
				}else {
					bo.setTxnRefNo(CMSConstants.PGI_MERCHANT_ID_FEDERAL_MBA+candidateRefNo);
				}
				//				session.save(savedBo);
				session.update(bo);
				transaction.commit();
				//				session.flush();
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e);
			throw  new ApplicationException(e);
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return candidateRefNo;
	}
	@Override
	public boolean updateReceivedStatusStudentLogin(CandidatePGIDetails bo,
			LoginForm loginForm,Student student) throws Exception {
		log.info("Entered into generateCandidateRefNo-AdmissionFormTransactionImpl");
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		loginForm.setIsTnxStatusSuccess(false);
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(bo!=null){
				String query=" from CandidatePGIDetails c where c.txnRefNo='"+bo.getTxnRefNo()
				+"' and c.txnAmount="+bo.getTxnAmount()+" and c.txnStatus='Pending'";
				System.out.println("=====================candidaterefno=="+bo.getTxnRefNo()+"===============");
				System.out.println("=====================applnamount=="+bo.getTxnAmount()+"===============");
				CandidatePGIDetails candidatePgiBo=(CandidatePGIDetails)session.createQuery(query).uniqueResult();
				if(candidatePgiBo!=null){
					candidatePgiBo.setTxnRefNo(bo.getTxnRefNo());
					candidatePgiBo.setTxnDate(bo.getTxnDate());
					candidatePgiBo.setTxnStatus(bo.getTxnStatus());
					candidatePgiBo.setTxnAmount(bo.getTxnAmount());
					candidatePgiBo.setMode("Online");

					AdmAppln admAppln=new AdmAppln();
					admAppln.setId(student.getAdmAppln().getId());
					candidatePgiBo.setAdmAppln(admAppln);
					if(loginForm.getAdmApplnId()!=null && !loginForm.getAdmApplnId().equalsIgnoreCase("")){
						admAppln.setId(Integer.parseInt(loginForm.getAdmApplnId()));

						if(bo.getTxnStatus()!=null && bo.getTxnStatus().equalsIgnoreCase("S")){
							candidatePgiBo.setAdmAppln(admAppln);
						}
					}
					if(bo.getTxnStatus()!=null && bo.getTxnStatus().equalsIgnoreCase("SUCCESS")){
						loginForm.setIsTnxStatusSuccess(true);
						loginForm.setPaymentSuccess(true);
					}
					loginForm.setCandidateRefNo(candidatePgiBo.getCandidateRefNo());
					loginForm.setTransactionRefNO(bo.getTxnRefNo());
					session.update(candidatePgiBo);
					isUpdated=true;
				}
			}
			transaction.commit();
			//session.flush();
			//session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error during .................................updateReceivedStatus.........."+ e.getCause().toString());
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				//session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e.getCause().toString());
			throw  new ApplicationException(e);
		}

		finally{

			//session.flush();
			session.close();
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return isUpdated;

	}
	@Override
	public boolean addFeePaymentDetail(FeePayment feePayment) throws Exception {
		log.info("Inside updateFeePaymentDetailAmounts TransactionImpl");
		Transaction tx=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(feePayment);
			tx.commit();
			return true;
		} catch (Exception e) {
			if(tx !=null){
				tx.rollback();
			}
			log.error("Error occured in updateFeePaymentDetailAmounts");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
			log.info("End of updateFeePaymentDetailAmounts in TransactionImpl");
		}
	}
	@Override
	public Object checkCourseAidedOrNotStudent(String courseId)
			throws Exception {
		Session session = null;
		boolean isAided = false;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select course.isAided from Course course where course.isActive = 1 and course.id = :courseId").setInteger("courseId", Integer.parseInt(courseId));
			isAided = (Boolean) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		} 
		return isAided;
	}
	@Override
	public FeeBillNumber getFeeBillNoYear1(int id,
			Object checkCourseAidedOrNotStudent) throws Exception {
		log.info("Inside of getFeeBillNoYear of FeeBillNumberTransactionImpl");
		Session session = null;
		FeeBillNumber number = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from FeeBillNumber number where number.feeFinancialYear.id = :row and number.isAided = :isAided");
			//query.setInteger("row", year);
			//query.setBoolean("isAided", checkCourseAidedOrNotStudent);
			number = (FeeBillNumber)query.uniqueResult();
		} catch (Exception e) {			
			log.warn("Exception occured while getting the row based on the year in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getFeeBillNoYear of FeeBillNumberTransactionImpl");
		return number;
	}
	@Override
	public boolean updateFeeBillNumberStudent1(FeeBillNumber billNumber)
			throws Exception {

		log.info("Inside of updateFeeBillNumber of FeeBillNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(billNumber);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating FeeBillNumber in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
			log.info("End of updateFeeBillNumber of FeeBillNumberTransactionImpl");
		}

	}
	@Override
	public boolean getFeePaymentDetailsOfStudent(LoginForm loginForm,
			HttpServletRequest request) throws Exception {
		Session session1 = null;
		Transaction transaction = null;
		boolean flag = false;
		try{
			session1 = HibernateUtil.getSession();
			transaction = session1.beginTransaction();
			transaction.begin();
			int count = 0;
			FeePayment feePayment = new FeePayment();
			HttpSession session = request.getSession();
			String studentid = (String) session.getAttribute("studentid");
			List<FeePayment> feePaymentList = getList(studentid,loginForm.getClassId());
			if(feePaymentList == null ||feePaymentList.size()==0 ){
				Student student = StudentLoginHandler.getInstance().getStudentById(studentid);
				Student student1 = new Student();
				student1.setId(loginForm.getStudentId());
				feePayment.setStudent(student1);

				FeeFinancialYear feeFinancialYear = new FeeFinancialYear();
				feeFinancialYear.setId(FeePaymentHandler.getInstance().getFinancialYearId());
				feePayment.setAmountFinancialYear(feeFinancialYear);
				String billno = getBillNo(feeFinancialYear);
				feePayment.setBillNo(billno);
				Course course = new Course();
				course.setId(Integer.parseInt(loginForm.getCourseId()));
				feePayment.setCourse(course);

				Classes classes = new Classes();
				classes.setId(Integer.parseInt(loginForm.getClassId()));
				feePayment.setClasses(classes);

				feePayment.setApplicationNo(String.valueOf(student.getAdmAppln().getApplnNo()));
				feePayment.setRegistrationNo(loginForm.getRegNo());
				feePayment.setRollNo(student.getRollNo());
				feePayment.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
				feePayment.setAcademicYear(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
				feePayment.setChallanCreatedTime(new Date());
				feePayment.setIsCancelChallan(false);
				feePayment.setIsFeePaid(true);
				feePayment.setPaymentVerified(true);
				feePayment.setFeePaidDate(new Date());
				feePayment.setCreatedBy(loginForm.getUserId());
				feePayment.setCreatedDate(new Date());

				int count1 = 0;
				int count2 = 0;
				boolean billNoGenerate = false; 

				Set<FeePaymentDetail> feePaymentDetails = new HashSet<FeePaymentDetail>();
				List<Object[]> list= (List<Object[]>)session.getAttribute("paymentList"); 
				Iterator<Object[]> keyValItr = list.iterator();
				double totalPaid = 0.0;
				double totalFine = 0.0;
				double totalAmount = 0.0;
				double balanceAmount = 0.0;
				double feepaid=0.0;
				String assignedAmt=null;
				while(keyValItr.hasNext()) {
					Object[] obj = keyValItr.next();
					FeePaymentDetail feePaymentDetail = new FeePaymentDetail();			
					feePaymentDetail.setSemesterNo(loginForm.getTermNo());
					feePaymentDetail.setPaidDate(CommonUtil.ConvertStringToSQLDate(loginForm.getTxnDate()));
					FeeHeading feeHeading = new FeeHeading();
					feeHeading.setId(Integer.parseInt(obj[1].toString()));
					feePaymentDetail.setFeeHeading(feeHeading);
					feepaid = Double.parseDouble(loginForm.getAmount())/100;
					feePaymentDetail.setAmountPaid(new BigDecimal(feepaid));
					assignedAmt = (String) session.getAttribute("assignedAmount");
					if(balanceAmount == 0.0){
						balanceAmount = Double.parseDouble(assignedAmt) - feepaid;
					}else {
						balanceAmount = balanceAmount - feepaid;
					}
					feePaymentDetail.setFeeFinancialYear(feeFinancialYear);
					feePaymentDetail.setAmountBalance(new BigDecimal(balanceAmount));
					feePaymentDetail.setTotalAmount(new BigDecimal(assignedAmt));


					totalPaid += feepaid;
					feePayment.setTotalAmount(new BigDecimal(assignedAmt));
					feePayment.setTotalFeePaid(new BigDecimal(totalPaid));
					feePayment.setTotalBalanceAmount(new BigDecimal(Double.parseDouble(assignedAmt) -totalPaid ));
					feePayment.setIsCompletlyPaid(true);
					feePaymentDetail.setFeePayment(feePayment);
					feePaymentDetails.add(feePaymentDetail);
					feePayment.setFeePaymentDetails(feePaymentDetails);
					session1.save(feePayment);
					transaction.commit();
					session1.flush();
					flag = true;
					return flag;
				}
			}else {
				boolean isObjectPresent;
				Student student = StudentLoginHandler.getInstance().getStudentById(studentid);
				Student student1 = new Student();
				student1.setId(loginForm.getStudentId());
				feePayment.setStudent(student1);
				List<FeePayment> feePaymentList1 = getList(studentid,loginForm.getClassId());
				FeeFinancialYear feeFinancialYear = new FeeFinancialYear();
				feeFinancialYear.setId(FeePaymentHandler.getInstance().getFinancialYearId());
				feePayment.setAmountFinancialYear(feeFinancialYear);
				String billno = getBillNo(feeFinancialYear);
				if(feePaymentList1 != null && feePaymentList1.size() != 0){
					feePayment.setBillNo(billno);
				}else {
					feePayment.setBillNo(billno +1);
				}
				Course course = new Course();
				course.setId(Integer.parseInt(loginForm.getCourseId()));
				feePayment.setCourse(course);

				Classes classes = new Classes();
				classes.setId(Integer.parseInt(loginForm.getClassId()));
				feePayment.setClasses(classes);

				feePayment.setApplicationNo(String.valueOf(student.getAdmAppln().getApplnNo()));
				feePayment.setRegistrationNo(loginForm.getRegNo());
				feePayment.setRollNo(student.getRollNo());
				feePayment.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
				feePayment.setAcademicYear(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
				feePayment.setChallanCreatedTime(new Date());
				feePayment.setIsCancelChallan(false);
				feePayment.setIsFeePaid(true);
				feePayment.setFeePaidDate(new Date());
				feePayment.setCreatedBy(loginForm.getUserId());
				feePayment.setCreatedDate(new Date());

				Set<FeePaymentDetail> feePaymentDetails = new HashSet<FeePaymentDetail>();
				List<Object[]> list= (List<Object[]>)session.getAttribute("paymentList"); 
				Iterator<Object[]> keyValItr = list.iterator();
				double totalPaid = 0.0;
				double totalFine = 0.0;
				double totalAmount = 0.0;
				double balanceAmount = 0.0;
				double balAmt = 0.0;

				while(keyValItr.hasNext()) {
					Object[] obj = keyValItr.next();
					Iterator<FeePayment> iterator = feePaymentList.iterator();
					while (iterator.hasNext()){
						FeePaymentDetail feePaymentDetail = new FeePaymentDetail();			
						feePaymentDetail.setSemesterNo(loginForm.getTermNo());
						feePaymentDetail.setPaidDate(CommonUtil.ConvertStringToSQLDate(loginForm.getTxnDate()));
						FeePayment feePayment2 = iterator.next();
						Set<FeePaymentDetail> feePaymentDetails2 = feePayment2.getFeePaymentDetails();
						Iterator<FeePaymentDetail> iterator3 = feePaymentDetails2.iterator();
						while(iterator3.hasNext()){
							FeePaymentDetail feeDetail = iterator3.next();
							balAmt = feeDetail.getAmountBalance().doubleValue();
							//						FeeAccount feeAccount = new FeeAccount();
							//						feeAccount.setId(to.getFeeAccountId());
							//						feePaymentDetail.setFeeAccount(feeAccount);
							FeeHeading feeHeading = new FeeHeading();
							feeHeading.setId(Integer.parseInt(obj[1].toString()));
							feePaymentDetail.setFeeHeading(feeHeading);
							double feepaid = Double.parseDouble(loginForm.getAmount())/100;
							feePaymentDetail.setAmountPaid(new BigDecimal(feepaid));
							String assignedAmt = (String) session.getAttribute("assignedAmount");

							if(balAmt == 0.0){
								balanceAmount = Double.parseDouble(assignedAmt) - feepaid;
							}else {
								balanceAmount = balAmt - feepaid;
							}
							feePaymentDetail.setFeeFinancialYear(feeFinancialYear);
							feePaymentDetail.setAmountBalance(new BigDecimal(balanceAmount));
							feePaymentDetail.setTotalAmount(new BigDecimal(assignedAmt));
							feePayment.setId(feePayment2.getId());
							feePaymentDetail.setFeePayment(feePayment);

							feePaymentDetails.add(feePaymentDetail);
							feePayment.setFeePaymentDetails(feePaymentDetails);
							totalPaid = feePayment2.getTotalFeePaid().doubleValue();
							totalPaid += feepaid;
							feePayment.setTotalAmount(new BigDecimal(assignedAmt));
							feePayment.setTotalFeePaid(new BigDecimal(totalPaid));
							feePayment.setTotalBalanceAmount(new BigDecimal(Double.parseDouble(assignedAmt) -totalPaid ));
							feePayment.setIsCompletlyPaid(true);
							session1.save(feePaymentDetail);
							session1.merge(feePayment);
							if(++count % 20 == 0){
								session1.flush();
								session1.clear();
							}
							transaction.commit();;

							session1.flush();
							flag = true;
							return flag;
						}
					}
				}

			}
		}catch (ConstraintViolationException e) {
			transaction.rollback();
			session1.close();
			throw e;				 
		} catch (Exception e) {
			transaction.rollback();
			session1.close();
			throw e;				 
		} 
		return flag;
	}

	private List<FeePayment> getList(String studentid, String classId)  throws Exception{
		Session session = null;
		List<FeePayment> list = new ArrayList<FeePayment>();
		try{
			session = HibernateUtil.getSession();
			String s = "from FeePayment f where f.student.id = :studId and f.classes.id = :classId";
			Query query = session.createQuery(s)
					.setString("studId", studentid)
					.setString("classId", classId);
			list = query.list();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return list;
	}
	@Override
	public List<FeePayment> getPaymentList(String studentid, String classId)
			throws Exception {
		Session session = null;
		List<FeePayment> list = new ArrayList<FeePayment>();
		try{
			session = HibernateUtil.getSession();
			String s = "from FeePayment f where f.student.id = :studId and f.classes.id = :classId";
			Query query = session.createQuery(s)
					.setString("studId", studentid)
					.setString("classId", classId);
			list = query.list();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return list;
	}
	private String getBillNo(FeeFinancialYear feeFinancialYear) throws Exception {
		Session session = null;
		String billNo = null;
		try{
			session = HibernateUtil.getSession();
			String s = "select f.currentBillNo from FeeBillNumber f where f.feeFinancialYear.id = :financialYearId";
			Query query = session.createQuery(s)
					.setInteger("financialYearId", feeFinancialYear.getId());
			billNo = (String)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return billNo;
	}
	@Override
	public FeePayment getDetailsOfstudent(String studentid, String classId)
			throws Exception {
		Session session = null;
		FeePayment feePayment = new FeePayment();
		try{
			session = HibernateUtil.getSession();
			String s = "from FeePayment f where f.student.id = :studId and f.classes.id = :classId";
			Query query = session.createQuery(s)
					.setString("studId", studentid)
					.setString("classId", classId);
			feePayment = (FeePayment)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return feePayment;
	}
	@Override
	public CandidatePGIDetails getStatus(String studentName,String status,String mode) throws Exception {
		Session session = null;
		CandidatePGIDetails details = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from CandidatePGIDetails cg where cg.candidateName = :studentName  and cg.txnStatus = :status  and cg.mode = :mode";
			Query query = session.createQuery(s)
					.setString("studentName", studentName)
					.setString("status", status)
					.setString("mode", mode);
			details = (CandidatePGIDetails) query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return details;
	}
	@Override
	public FeeBillNumber getlBillNo(FeeFinancialYear feeFinancialYear)
			throws Exception {
		Session session = null;
		FeeBillNumber billNo = null;
		try{
			session = HibernateUtil.getSession();
			String s = " from FeeBillNumber f where f.feeFinancialYear.id = :financialYearId";
			Query query = session.createQuery(s)
					.setInteger("financialYearId", feeFinancialYear.getId());
			billNo = (FeeBillNumber)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return billNo;
	}
	@Override
	public boolean updateFeeBillNo(FeeBillNumber bo) throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(bo);
			tx.commit();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			if(tx!=null)
				tx.rollback();
			throw new ApplicationException(ex);
		}
	}
	@Override
	public SemesterWiseCourseBO isSemesterWise(int courseId) throws Exception {
		Session session = null;
		SemesterWiseCourseBO bo = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from SemesterWiseCourseBO b  " +
					"where b.course.id = :courseId  " +
					"and b.isSemesterWise = 1";
			Query query = session.createQuery(s)
					.setInteger("courseId", courseId);
			bo = (SemesterWiseCourseBO)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return bo;
	}
	@Override
	public List<Object[]> getPaymentDetailsSemesterWise(int categoryId,
			int courseId, int year,int semNo, Integer academicYear) throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String s = "select fee_account_assignment.amount,fee_account_assignment.fee_heading_id,fee_account_assignment.fee_category_id  from fee_account_assignment " +
					" inner join fee ON fee.id = fee_account_assignment.fee_id  " +
					" inner join fee_category ON fee_category.id = fee_account_assignment.fee_category_id  " +
					" inner join fee_heading ON fee_heading.id = fee_account_assignment.fee_heading_id " +
					" where fee_category.id = :categoryId  " +
					" and fee.course_id = :courseId" +
					" and fee.semester_no = :termNo" +
					" and fee.course_sem_no = :semNo" +
					" and fee.academic_year = :academicYear";
			Query query = session.createSQLQuery(s)
					.setInteger("categoryId", categoryId)
					.setInteger("courseId", courseId)
					.setInteger("termNo", year)
					.setInteger("semNo", semNo)
					.setInteger("academicYear", academicYear);
			list = query.list();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public CandidatePGIDetails getDataTxn(String admAppln) throws Exception {
		Session session = null;
		CandidatePGIDetails pgiDetails = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from CandidatePGIDetails cd " +
					"where cd.admAppln.id = :admAppln " +
					"and cd.txnStatus = 'Success' " +
					"and cd.mode = 'Online'";
			Query query = session.createQuery(s)
					.setString("admAppln", admAppln);
			pgiDetails = (CandidatePGIDetails)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return pgiDetails;
	}
	@Override
	public FeePayment getRecord(int appnId) throws Exception {
		Session session = null;
		FeePayment payment = null;
		try{
			session = HibernateUtil.getSession();
			String s = "from FeePayment f where f.applicationNo = :applnNo  and f.paymentVerified = 1";
			Query query = session.createQuery(s)
					.setInteger("applnNo", appnId);
			payment = (FeePayment) query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return payment;
	}
	@Override
	public List<FeePaymentDetail> getDataForStudent(String studentid, String classId)throws Exception {
		Session session = null;
		List<FeePaymentDetail> feePayment = new ArrayList<FeePaymentDetail>();
		try{
			session = HibernateUtil.getSession();
			String s = "from FeePaymentDetail f where f.feePayment.student.id= :studId and f.feePayment.classes.id= :classId order by f.id";
			Query query = session.createQuery(s)
					.setString("studId", studentid)
					.setString("classId", classId);
			feePayment = query.list();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return feePayment;
	}	
	@Override
	public List<DocChecklist> getExamtypes(int courseId, int year)
			throws Exception {
		List<DocChecklist> docchecklist = new ArrayList<DocChecklist>();
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocChecklist d where d.course.id = :courseId and d.year=:year and d.isActive=1 and d.docType.isActive=1 and d.docType.id not in(2)");
			query.setInteger("courseId", courseId);
			query.setInteger("year", year);
			List<DocChecklist> listofdocs = query.list();
			if (listofdocs != null && !listofdocs.isEmpty()) {
				Iterator<DocChecklist> itr = listofdocs.iterator();
				DocChecklist checkdocs;
				while (itr.hasNext()) {
					checkdocs = (DocChecklist) itr.next();
					if (checkdocs != null && checkdocs.getIsMarksCard()) {
						docchecklist.add(checkdocs);

					}
				}

			}

		} catch (Exception e) {

			if (session != null) {
				//session.flush();
			}

			log.error("Error during getting doc checklists...", e);
			throw new ApplicationException(e);
		}

		//		session.close();
		return docchecklist;
	}
	@Override
	public boolean saveAppBO(AdmAppln appBO) throws ApplicationException, BusinessException {
		boolean result = false;
		Session session = null;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			session.saveOrUpdate(appBO);

			txn.commit();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()){
				if(txn!=null)
					txn.rollback();

				session.close();
			}
			log.error("Error during updating complete student data...", e);
			System.out.println("Error during .................................createNewApplicant.........."+ e.getCause().toString());
			throw new BusinessException(e);
		} catch (Exception e) {
			//raghu
			if (session.isOpen()){
				if(txn!=null)
					txn.rollback();

			}
			log.error("Error during updating complete student data...", e);
			System.out.println("Error during .................................createNewApplicant.........."+ e.getCause().toString());
			throw new ApplicationException(e);
		}
		finally{
			session.close();
		}
		return result;
	}
}

