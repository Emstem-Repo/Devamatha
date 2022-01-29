package com.kp.cms.handlers.exam;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.ExamRegularApplication;
import com.kp.cms.bo.exam.ExamRevaluationApp;
import com.kp.cms.bo.exam.ExamRevaluationApplicationNew;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamSubjectSectionMasterBO;
import com.kp.cms.bo.exam.InternalRedoFees;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.bo.exam.RegularExamFees;
import com.kp.cms.bo.exam.RevaluationExamFees;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.bo.exam.SupplementaryFees;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.NewSupplementaryImpApplicationForm;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.attendance.StudentAttendanceSummaryHandler;
import com.kp.cms.helpers.exam.NewSupplementaryImpApplicationHelper;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationSubjectTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.to.exam.SupplementaryAppExamTo;
import com.kp.cms.to.exam.SupplementaryApplicationClassTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewStudentMarksCorrectionTransaction;
import com.kp.cms.transactions.exam.INewSupplementaryImpApplicationTransaction;
import com.kp.cms.transactions.exam.INewUpdateProccessTransaction;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.admission.DisciplinaryDetailsImpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewStudentMarksCorrectionTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewSupplementaryImpApplicationTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewUpdateProccessTransactionImpl;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.OnlinePaymentUtils;
import com.kp.cms.utilities.PropertyUtil;

public class NewSupplementaryImpApplicationHandler {
	/**
	 * Singleton object of NewSupplementaryImpApplicationHandler
	 */
	private static volatile NewSupplementaryImpApplicationHandler newSupplementaryImpApplicationHandler = null;
	private static final Log log = LogFactory.getLog(NewSupplementaryImpApplicationHandler.class);

	private NewSupplementaryImpApplicationHandler() {

	}

	/**
	 * return singleton object of NewSupplementaryImpApplicationHandler.
	 * 
	 * @return
	 */
	public static NewSupplementaryImpApplicationHandler getInstance() {
		if (newSupplementaryImpApplicationHandler == null) {
			newSupplementaryImpApplicationHandler = new NewSupplementaryImpApplicationHandler();
		}
		return newSupplementaryImpApplicationHandler;
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	public Map<String, ExamSupplementaryImpApplicationTO> getStudentListForInput(
			NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm)
			throws Exception {
		Integer studentId = getStudentId(newSupplementaryImpApplicationForm);
		String query = NewSupplementaryImpApplicationHelper.getInstance()
		.getStudentListQuery(newSupplementaryImpApplicationForm,
				studentId);
		boolean isRegOrRoll=false;
		boolean checkAll=true;
		if((newSupplementaryImpApplicationForm.getRegisterNo()!=null && !newSupplementaryImpApplicationForm.getRegisterNo().isEmpty()) || (newSupplementaryImpApplicationForm.getRollNo()!=null && !newSupplementaryImpApplicationForm.getRollNo().isEmpty())){
			isRegOrRoll=true;
		}
		if(isRegOrRoll && studentId==null)
			checkAll=false;
		if(checkAll){
			INewExamMarksEntryTransaction transaction = NewExamMarksEntryTransactionImpl
			.getInstance();
			List boList = transaction.getDataForQuery(query);
			return NewSupplementaryImpApplicationHelper.getInstance()
			.convertBotoToList(boList, newSupplementaryImpApplicationForm);
		}else
			return new HashMap<String, ExamSupplementaryImpApplicationTO>();
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	private Integer getStudentId(
			NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm)
	throws Exception {
		Integer studentId = null;
		if ((newSupplementaryImpApplicationForm.getRegisterNo() != null && !newSupplementaryImpApplicationForm
				.getRegisterNo().isEmpty())
				|| (newSupplementaryImpApplicationForm.getRollNo() != null && !newSupplementaryImpApplicationForm
						.getRollNo().isEmpty())) {
			if (newSupplementaryImpApplicationForm.getSchemeNo() != null
					&& !newSupplementaryImpApplicationForm.getSchemeNo()
					.isEmpty()) {
				INewStudentMarksCorrectionTransaction transaction1 = NewStudentMarksCorrectionTransactionImpl .getInstance();
				studentId = transaction1.getStudentId( newSupplementaryImpApplicationForm.getRegisterNo(), newSupplementaryImpApplicationForm.getSchemeNo(), newSupplementaryImpApplicationForm.getRollNo());
			}
		}
		return studentId;
	}

	/**
	 * @param to
	 * @throws Exception
	 */
	public void setDatatoTo(ExamSupplementaryImpApplicationTO to)
	throws Exception {
		String query = NewSupplementaryImpApplicationHelper.getInstance().getQueryForEdit(to);
		INewExamMarksEntryTransaction transaction = NewExamMarksEntryTransactionImpl.getInstance();
		List<StudentSupplementaryImprovementApplication> boList = transaction .getDataForSQLQuery(query);
		NewSupplementaryImpApplicationHelper.getInstance().convertBotoToList(boList, to);
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveSupplementaryApplication( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		List<StudentSupplementaryImprovementApplication> boList=new ArrayList<StudentSupplementaryImprovementApplication>();
		ExamSupplementaryImpApplicationTO suppTo = newSupplementaryImpApplicationForm .getSuppTo();
		ExamDefinition examDefinition = new ExamDefinition();
		examDefinition.setId(suppTo.getExamId());
		Student student = new Student();
		student.setId(suppTo.getStudentId());
		Classes classes = new Classes();
		classes.setId(suppTo.getClassId());
		List<ExamSupplementaryImpApplicationSubjectTO> listSubject = suppTo .getSubjectList();
		for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
			StudentSupplementaryImprovementApplication objBO = new StudentSupplementaryImprovementApplication();
			if(to.getId()!=null)
				objBO.setId(to.getId());
			objBO.setCreatedBy(newSupplementaryImpApplicationForm.getUserId());
			objBO.setCreatedDate(new Date());
			objBO.setLastModifiedDate(new Date());
			objBO.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
			objBO.setStudent(student);
			objBO.setExamDefinition(examDefinition);
			Subject subject = new Subject();
			subject.setId(to.getSubjectId());
			objBO.setSubject(subject);
			if (to.getFailedTheory() != null) {
				objBO.setIsFailedTheory(true);
			} else {
				objBO .setIsFailedTheory((to.getIsFailedTheory()) ? true : false);
			}
			if (to.getFailedPractical() != null) {
				objBO.setIsFailedPractical(true);
			} else {
				objBO.setIsFailedPractical((to.getIsFailedPractical()) ? true : false);
			}
			objBO.setIsAppearedTheory(to.getIsAppearedTheory());
			objBO.setIsAppearedPractical(to.getIsAppearedPractical());
			objBO.setFees(to.getFees());
			objBO.setSchemeNo(suppTo.getSemester_year_no());
			objBO.setChance(to.getChance());
			if(to.getIsOverallTheoryFailed()!=null)
				objBO.setIsTheoryOverallFailed(to.getIsOverallTheoryFailed());
			else
				objBO.setIsTheoryOverallFailed(false);
			if(to.getIsOverallPracticalFailed()!=null)
				objBO.setIsPracticalOverallFailed(to.getIsOverallPracticalFailed());
			else
				objBO.setIsPracticalOverallFailed(false);
			objBO.setClasses(classes);
			if (newSupplementaryImpApplicationForm .getSupplementaryImprovement() != null && newSupplementaryImpApplicationForm .getSupplementaryImprovement().trim().length() > 0) {
				if (newSupplementaryImpApplicationForm .getSupplementaryImprovement().equalsIgnoreCase("Supplementary")) {

					//raghu write this one
					//objBO.setIsSupplementary(true);
					//objBO.setIsImprovement(false);

					//raghu added newly
					if(to.getIsImprovement()||to.getIsSupplementary()){
						objBO.setIsImprovement(to.getIsImprovement());
						objBO.setIsSupplementary(to.getIsSupplementary());
					}else{
						objBO.setIsSupplementary(true);
						objBO.setIsImprovement(false);

					}



				} else if (newSupplementaryImpApplicationForm.getSupplementaryImprovement().equalsIgnoreCase("Improvement")) {

					//raghu write this one
					//objBO.setIsImprovement(true);
					//objBO.setIsSupplementary(false);

					//raghu added newly
					if(to.getIsImprovement()||to.getIsSupplementary()){
						objBO.setIsImprovement(to.getIsImprovement());
						objBO.setIsSupplementary(to.getIsSupplementary());
					}else{
						objBO.setIsImprovement(true);
						objBO.setIsSupplementary(false);


					}
				}
			}
			objBO.setIsOnline(false);




			//raghu added from mounts

			objBO.setIsAppearedTheory(to.getIsAppearedTheory());
			objBO.setIsAppearedPractical(to.getIsAppearedPractical());


			// new modification


			if(to.isCiaExam())
			{
				if(to.getIsAppearedTheory())
				{
					objBO.setIsAppearedTheory(false);
					objBO.setIsAppearedPractical(false);
					objBO.setIsFailedPractical(false);
					objBO.setIsFailedTheory(false);

					// cia
					objBO.setIsCIAAppearedTheory(to.getIsAppearedTheory());
					objBO.setIsCIAFailedTheory(true);
					objBO.setIsCIAFailedPractical(false);
					objBO.setIsCIAAppearedPractical(false);
					objBO.setCiaExam(true);
				}
				if(to.getIsAppearedPractical())
				{
					objBO.setIsAppearedTheory(false);
					objBO.setIsAppearedPractical(false);
					objBO.setIsFailedPractical(false);
					objBO.setIsFailedTheory(false);

					// cia
					objBO.setIsCIAAppearedTheory(false);
					objBO.setIsCIAFailedTheory(true);
					objBO.setIsCIAFailedPractical(true);
					objBO.setIsCIAAppearedPractical(to.getIsAppearedPractical());
					objBO.setCiaExam(true);
				}
			}
			else
			{
				objBO.setIsAppearedTheory(to.getIsAppearedTheory());
				objBO.setIsAppearedPractical(to.getIsAppearedPractical());
				objBO.setIsFailedPractical(to.getIsFailedPractical());
				objBO.setIsFailedTheory(to.getIsFailedTheory());

				objBO.setIsCIAAppearedTheory(false);
				objBO.setIsCIAFailedTheory(false);
				objBO.setIsCIAFailedPractical(false);
				objBO.setIsCIAAppearedPractical(false);
				objBO.setCiaExam(false);
			}


			boList.add(objBO);
		}
		return transaction.saveSupplementarys(boList);
	}

	/**
	 * @param to
	 * @throws Exception
	 */
	public boolean deleteSupplementaryImpApp(ExamSupplementaryImpApplicationTO to) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		String query = NewSupplementaryImpApplicationHelper.getInstance().getQueryForEdit(to);
		return transaction.deleteSupplementaryImpApp(query); 
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	public ExamSupplementaryImpApplicationTO getStudentDetails(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Object[]> studentDetails=new ArrayList<Object[]>();
		List<Subject> subList=new ArrayList<Subject>();
		String previousQuery="select s.student,s.classes from StudentPreviousClassHistory s " +
		" where s.schemeNo=" +newSupplementaryImpApplicationForm.getSchemeNo()+
		" and s.student.id="+newSupplementaryImpApplicationForm.getStudentId();
		List previousList=transaction.getDataForQuery(previousQuery);
		if(previousList.isEmpty()){
			String currentQuery="select s,s.classSchemewise.classes from Student s " +
			"where s.id=" +newSupplementaryImpApplicationForm.getStudentId()+
			" and s.classSchemewise.classes.termNumber="+newSupplementaryImpApplicationForm.getSchemeNo();
			List currentList=transaction.getDataForQuery(currentQuery);
			studentDetails.addAll(currentList);
		}else{
			studentDetails.addAll(previousList);
		}
		//String subPreQuery="select subGrp.subject from StudentSubjectGroupHistory s join s.subjectGroup.subjectGroupSubjectses subGrp" +
		//	" where s.schemeNo=" +newSupplementaryImpApplicationForm.getSchemeNo()+
		//	" and s.student.id="+newSupplementaryImpApplicationForm.getStudentId();

		String subPreQuery="select  m.subject from MarksEntryDetails m where m.marksEntry.student.id="+newSupplementaryImpApplicationForm.getStudentId()+" and m.marksEntry.exam.examTypeUtilBO.name='Regular' and  m.marksEntry.classes.termNumber=" +newSupplementaryImpApplicationForm.getSchemeNo()+" group by m.subject.id";
		// raghu change query bcz they enterd wrong data in student subject group


		List<Subject> preList=transaction.getDataForQuery(subPreQuery);
		if(!preList.isEmpty()){
			subList.addAll(preList);
		}
		String subQuery="select subGrp.subject from Student s join s.admAppln.applicantSubjectGroups appSub join appSub.subjectGroup.subjectGroupSubjectses subGrp" +
		" where s.id=" +newSupplementaryImpApplicationForm.getStudentId()+
		" and subGrp.isActive=1 and s.classSchemewise.classes.termNumber="+newSupplementaryImpApplicationForm.getSchemeNo();
		List<Subject> suList=transaction.getDataForQuery(subQuery);
		subList.addAll(suList);
		return NewSupplementaryImpApplicationHelper.getInstance().convertBoListsToTo(subList,studentDetails,newSupplementaryImpApplicationForm);
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 */
	public String checkValid(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewStudentMarksCorrectionTransaction transaction1 = NewStudentMarksCorrectionTransactionImpl .getInstance();
		Integer studentId = transaction1.getStudentId( newSupplementaryImpApplicationForm.getRegisterNo(), newSupplementaryImpApplicationForm.getSchemeNo(), newSupplementaryImpApplicationForm.getRollNo());
		String msg="";
		if(studentId==null || studentId==0){
			msg="Invalid Student";
		}else{
			String query="from StudentSupplementaryImprovementApplication s where s.student.id="+studentId+" and s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+
			" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId();
			List<StudentSupplementaryImprovementApplication> boList=transaction.getDataForQuery(query);
			if(boList!=null && !boList.isEmpty()){
				msg="Details Already Exists ";
			}else{
				newSupplementaryImpApplicationForm.setStudentId(studentId);
			}
		}
		return msg;
	}


	public Boolean checkSuppDateExtended(int studentId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		Boolean isExtended=false;
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		isExtended=transaction.getExtendedDate(studentId, newSupplementaryImpApplicationForm);
		return isExtended;

	}
	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	/*public List<Integer> checkSuppImpAppAvailable(int studentId) throws Exception {
		String query="select p.exam.id from PublishSupplementaryImpApplication p " +
				" where p.isActive=1 and p.exam.delIsActive=1 and p.exam.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
				" where s.student.id= " +studentId+
				" and ((s.isFailedPractical=1 or s.isFailedTheory=1) and (s.isSupplementary=1 or s.isImprovement=1))and s.classes.id in(p.classCode.id)) " +
		" and ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.startDate and p.endDate or '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.startDate and p.extendedDate) group by p.exam.id "; 
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}*/

	public List<Integer> checkSuppImpAppAvailable(int studentId) throws Exception {
		String query="select p.exam.id from PublishSupplementaryImpApplication p " +
		" where p.isActive=1 and p.exam.delIsActive=1 and p.exam.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
		" where s.student.id= " +studentId+
		//raghu added from mounts
		//" and (((s.isFailedPractical=1 or s.isFailedTheory=1) or (s.isTheoryOverallFailed=1 or s.isPracticalOverallFailed=1)) and (s.isSupplementary=1 or s.isImprovement=1))and s.classes.id in(p.classCode.id)) " +
		" and (((s.isFailedPractical=1 or s.isFailedTheory=1 or s.isCIAFailedPractical=1 or s.isCIAFailedTheory=1) and s.isSupplementary=1 ) or s.isImprovement=1)and s.classes.id in(p.classCode.id)) " +


		/*"and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between (p.startDate and p.endDate)  group by p.exam.id ";*/
		" and (('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.startDate and p.endDate) or ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.startDate and p.extendedDate)) group by p.exam.id "; 
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}

	/**
	 * @param examIds
	 * @param newSupplementaryImpApplicationForm
	 * @throws Exception
	 */
	public void getApplicationForms(Boolean extendedTrue, List<Integer> examIds, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewSupplementaryImpApplicationTransaction transaction1=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		List<SupplementaryAppExamTo> examList=new ArrayList<SupplementaryAppExamTo>();
		SupplementaryAppExamTo eto=null;
		int count=0;
		boolean buttonDisplay=false;
		boolean challanDisplay=false;
		boolean isTotal=false;
		boolean isChecked=false;
		boolean isScSupply=false;
		List<Integer> classNotRequiredList=new ArrayList<Integer>();


		if(newSupplementaryImpApplicationForm.getExamId()!=null){
			eto=new SupplementaryAppExamTo();
			eto.setExamId(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()));
			eto.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()),"ExamDefinition",true,"name"));
			String endDateQuery="select concat(s.endDate,'') from PublishSupplementaryImpApplication s where s.exam.id="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
			"and s.classCode.id in (select supp.classes.id from StudentSupplementaryImprovementApplication supp where supp.examDefinition.id="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+" and supp.student.id="+newSupplementaryImpApplicationForm.getStudentId()+") and s.isActive=1";
			List dateList = transaction.getDataForQuery(endDateQuery);
			//			String endDate=PropertyUtil.getDataForUnique(endDateQuery); // commented by nagarjuna because this query return multiple records from multiple classes.
			String endDate=""; 
			if(dateList != null && !dateList.isEmpty())
				endDate = (String) dateList.get(0);
			eto.setExamDate(CommonUtil.formatSqlDate1(endDate));
			if(extendedTrue){
				String extendedDateQuery="select concat(s.extendedDate,'') from PublishSupplementaryImpApplication s where s.exam.id="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
				"and s.classCode.id in (select supp.classes.id from StudentSupplementaryImprovementApplication supp where supp.examDefinition.id="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+" and supp.student.id="+newSupplementaryImpApplicationForm.getStudentId()+") and s.isActive=1";
				List extendedDateList = transaction.getDataForQuery(extendedDateQuery);
				//				String endDate=PropertyUtil.getDataForUnique(endDateQuery); // commented by nagarjuna because this query return multiple records from multiple classes.
				String extendedEndDate=""; 
				if(extendedDateList != null && !extendedDateList.isEmpty())
					extendedEndDate = (String) extendedDateList.get(0);
				if(extendedEndDate!=null)
					eto.setExtendedDate(CommonUtil.formatSqlDate1(extendedEndDate));
			}


			//rgahu write newly like regular app
			String query1 = "from ExamDefinition e where e.id ="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId());
			List<ExamDefinition> examList1=transaction.getDataForQuery(query1);
			for (ExamDefinition bo : examList1) {
				newSupplementaryImpApplicationForm.setExamId(""+bo.getId());
				newSupplementaryImpApplicationForm.setExamName(bo.getName());
				newSupplementaryImpApplicationForm.setMonth(CommonUtil.getMonthForNumber(Integer.parseInt(bo.getMonth())));
				newSupplementaryImpApplicationForm.setYear(""+bo.getYear());
				newSupplementaryImpApplicationForm.setAcademicYear(bo.getAcademicYear()+"");
			}

			query1 = "from CourseToDepartment c where c.course.id ="+newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getId();
			List<CourseToDepartment> courseToDepartmentList=transaction.getDataForQuery(query1);
			String dep="";

			for (CourseToDepartment bo : courseToDepartmentList) {
				if(courseToDepartmentList.size()!=0 && courseToDepartmentList.size()==1){
					dep=dep+bo.getDepartment().getName();
				}else if(courseToDepartmentList.size()!=0 && courseToDepartmentList.size()>1){
					if(count==0){
						dep=dep+bo.getDepartment().getName();
					}else{
						dep=dep+","+bo.getDepartment().getName();
					}

					count++;
				}

				newSupplementaryImpApplicationForm.setCourseDep(dep);

			}





			/*String query="from StudentSupplementaryImprovementApplication s" +
				" where s.student.id= " +newSupplementaryImpApplicationForm.getStudentId()+
				//raghu added from mounts
				//" and (((s.isFailedPractical=1 or s.isFailedTheory=1) and s.isSupplementary=1) or s.isImprovement=1) and s.examDefinition.id="+examId;
				" and (((s.isFailedPractical=1 or s.isFailedTheory=1 or s.isCIAFailedPractical=1 or s.isCIAFailedTheory=1) and s.isSupplementary=1) or s.isImprovement=1) and s.examDefinition.id="+examId;

			 */	

			String query="SELECT  *  FROM    EXAM_supplementary_improvement_application EXAM_supplementary_improvement_application" +
			" INNER JOIN EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise " +
			" ON (EXAM_supplementary_improvement_application.subject_id =  EXAM_sub_definition_coursewise.subject_id)" +
			" where EXAM_supplementary_improvement_application.student_id="+newSupplementaryImpApplicationForm.getStudentId()+
			" and EXAM_supplementary_improvement_application.exam_id=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
			" and (((EXAM_supplementary_improvement_application.is_failed_practical=1 or EXAM_supplementary_improvement_application.is_failed_theory=1 or EXAM_supplementary_improvement_application.cia_is_failed_practical=1 or EXAM_supplementary_improvement_application.cia_is_failed_theory=1)" +
			" and EXAM_supplementary_improvement_application.is_supplementary=1) or EXAM_supplementary_improvement_application.is_improvement=1) "+
			" group by EXAM_supplementary_improvement_application.subject_id order by EXAM_sub_definition_coursewise.subject_order";


			List<StudentSupplementaryImprovementApplication> boList= transaction.getDataForSQLQuery(query);
			if(boList.size()>0){
				Map<Integer,Boolean> subIdMap = new HashMap<Integer, Boolean>();
				Iterator<StudentSupplementaryImprovementApplication> itr = boList.iterator();
				while(itr.hasNext()){
					StudentSupplementaryImprovementApplication bo = itr.next();
					if(bo.getIsImprovement()){
						String queryCount ="from StudentSupplementaryImprovementApplication supp" +
						" where supp.subject.id="+bo.getSubject().getId()+" and supp.student.id="+newSupplementaryImpApplicationForm.getStudentId()+
						" group by supp.examDefinition.id,supp.subject.id";
						List subCount= PropertyUtil.getInstance().getListOfData(queryCount);
						if(subCount.size()>1){
							subIdMap.put(bo.getSubject().getId(), true);
						}
					}
				}


	

				List<SupplementaryApplicationClassTo> classList=new ArrayList<SupplementaryApplicationClassTo>();
				SupplementaryApplicationClassTo to=null;
				boolean isSupplementary=false;
				//Basim did for Exam Fee Calculation
				double applicationFee=0;
				double paperFee=0;
				double RegistrationFee=0;
				double cvCampFee=0;
				double marksListFee=0;
				double totalfee=0;
				String programType =null;
				List<Integer> selffinanceCourseId=CMSConstants.SELF_FINANCE_COURSE;
				int secId=0;
				for (StudentSupplementaryImprovementApplication bo : boList) {


					if (!classNotRequiredList.contains(bo.getClasses().getId())) {

						query="from SupplementaryFees r where r.academicYear="+newSupplementaryImpApplicationForm.getAcademicYear()+" and r.classes.id="+bo.getClasses().getId();
						if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection()!=null){
							secId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection().getId();
							if(secId==6||secId==7||secId==8||secId==9||secId==10||secId==11)
								secId=2;
							query+=" and r.religionSection.id="+secId;
						}else if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough()!=null){
							int admId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough().getId();
							secId=0;
							if(admId==2){
								secId=3;
							}else if(admId==18){
								secId=4;
							}else{
								secId=2;
							}
							query+=" and r.religionSection.id="+secId;
						}
						SupplementaryFees supplementaryFees=null;
						List<SupplementaryFees> regularExamFeeList=transaction.getDataForQuery(query);



						if(regularExamFeeList==null || regularExamFeeList.isEmpty() )
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						if(!regularExamFeeList.isEmpty()){
							supplementaryFees=regularExamFeeList.get(0);
							if(supplementaryFees.getApplicationFees()!=null){
								applicationFee=supplementaryFees.getApplicationFees().doubleValue();
								newSupplementaryImpApplicationForm.setApplicationFees(applicationFee);
							}
							if(supplementaryFees.getMarksListFees()!=null){
								marksListFee=supplementaryFees.getMarksListFees().doubleValue();
								newSupplementaryImpApplicationForm.setMarksListFees(marksListFee);
							}

							if(supplementaryFees.getRegistrationFees()!=null){
								RegistrationFee=supplementaryFees.getRegistrationFees().doubleValue();
								newSupplementaryImpApplicationForm.setRegistrationFees(RegistrationFee);
							}
							if(supplementaryFees.getPaperFees()!=null){
								double PaperFee=supplementaryFees.getPaperFees().doubleValue();
								newSupplementaryImpApplicationForm.setPaperFees(PaperFee);
							}
							if(supplementaryFees.getCvCampFees()!=null){
								double cvFee=supplementaryFees.getCvCampFees().doubleValue();
								newSupplementaryImpApplicationForm.setCvCampFees(cvFee);
							}
						}
						to = new SupplementaryApplicationClassTo();
						to.setClassId(bo.getClasses().getId());
						to.setClassName(bo.getClasses().getName());
						List<ExamSupplementaryImpApplicationSubjectTO> subjectList = null;
						if (classList.contains(to)) {
							to = classList.remove(classList.indexOf(to));
							subjectList = to.getToList();
							if (bo.getIsSupplementary() != null) {
								if (to.isSupplementary() != bo.getIsSupplementary()){
									//seniors wrote this condition to check supply student have improvement, if he has he will not get receipt, but raghu put cooment
									//classNotRequiredList.add(bo.getClasses() .getId());
								}
							}
							if (bo.getIsImprovement() != null) {
								if (to.isImprovement() != bo.getIsImprovement()){
									//seniors wrote this condition to check supply student have improvement, if he has he will not get receipt, but raghu put cooment
									//classNotRequiredList.add(bo.getClasses() .getId());
								}
							}
						} else {
							subjectList = new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
							to.setCourseName(bo.getClasses().getCourse().getName());
							to.setRegisterNo(bo.getStudent().getRegisterNo());
							to.setRollNo(bo.getStudent().getRollNo());
							to.setSemNo(bo.getClasses().getTermNumber().toString());
							to.setStudentName(bo.getStudent().getAdmAppln() .getPersonalData().getFirstName());
							to.setClassName(bo.getClasses().getName());
							if (bo.getIsImprovement() != null)
								to.setImprovement(bo.getIsImprovement());
							if (bo.getIsSupplementary() != null)
								to.setSupplementary(bo.getIsSupplementary());
							if (count == 0) {
								newSupplementaryImpApplicationForm.setCourseName(bo .getClasses().getCourse().getName());
								newSupplementaryImpApplicationForm.setRegisterNo(bo .getStudent().getRegisterNo());
								newSupplementaryImpApplicationForm .setNameOfStudent(bo.getStudent() .getAdmAppln().getPersonalData() .getFirstName());
								count++;
							}
							newSupplementaryImpApplicationForm.setProgramId(String .valueOf(bo.getStudent().getAdmAppln() .getCourseBySelectedCourseId() .getProgram().getId()));
							String prgrmTypeQry= "select p.programType.name from Program p where p.id="+newSupplementaryImpApplicationForm.getProgramId();
							programType = PropertyUtil.getDataForUnique(prgrmTypeQry);
						}
						if (!classNotRequiredList.contains(bo.getClasses().getId())) {
							ExamSupplementaryImpApplicationSubjectTO sto = new ExamSupplementaryImpApplicationSubjectTO();
							sto.setId(bo.getId());
							sto.setSubjectId(bo.getSubject().getId());
							sto.setSubjectCode(bo.getSubject().getCode());
							sto.setSubjectName(bo.getSubject().getName());
							if (bo.getIsAppearedPractical() != null && bo.getIsAppearedPractical() || bo.getIsAppearedTheory()!=null && bo.getIsAppearedTheory()){
								if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && (secId==3 || secId==4)&& bo.getChance()>1){
									if(supplementaryFees!=null && supplementaryFees.getPaperFees()!=null)
										paperFee+=supplementaryFees.getPaperFees().doubleValue();
									if(supplementaryFees!=null && supplementaryFees.getCvCampFees()!=null)
										cvCampFee+=supplementaryFees.getCvCampFees().doubleValue();
									isTotal=true;
								}
								else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && (secId!=3 && secId!=4)){
									if(supplementaryFees!=null && supplementaryFees.getPaperFees()!=null)
										paperFee+=supplementaryFees.getPaperFees().doubleValue();
									if(supplementaryFees!=null && supplementaryFees.getCvCampFees()!=null)
										cvCampFee+=supplementaryFees.getCvCampFees().doubleValue();
									isTotal=true;
								}
								//Basim did this condition for self financing
								else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && selffinanceCourseId.contains(bo.getClasses().getCourse().getId())){
									if(supplementaryFees!=null && supplementaryFees.getPaperFees()!=null)
										paperFee+=supplementaryFees.getPaperFees().doubleValue();
									if(supplementaryFees!=null && supplementaryFees.getCvCampFees()!=null)
										cvCampFee+=supplementaryFees.getCvCampFees().doubleValue();
									isTotal=true;
								}
								else if(bo.getIsImprovement()!=null && bo.getIsImprovement()==true){
									if(supplementaryFees!=null && supplementaryFees.getPaperFees()!=null)
										paperFee+=supplementaryFees.getPaperFees().doubleValue();
									if(supplementaryFees!=null && supplementaryFees.getCvCampFees()!=null)
										cvCampFee+=supplementaryFees.getCvCampFees().doubleValue();
									isTotal=true;

								}
								//Basim did for SC/ST conditions
								else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && (secId==3 || secId==4) && bo.getChance()<=1){
									isScSupply=true;
								}
								challanDisplay=true;
								isChecked=true;
							}
							//raghu new 
							sto.setSubjectType(bo.getSubject().getIsTheoryPractical());

							if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getName().equalsIgnoreCase("UG")){

								String subQuery =   "select esb.examSubjectSectionMasterBO.name from ExamSubDefinitionCourseWiseBO esb where   esb.subjectUtilBO.id = "+bo.getSubject().getId();
								List sList= transaction.getDataForQuery(subQuery);
								for (Object s : sList) {
									sto.setSectionName(s.toString());
								}

							}

							if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("t") || bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B"))
								sto.setTheory(true);
							if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("p") || bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B"))
								sto.setPractical(true);

							if (!bo.getIsFailedTheory() && !bo.getIsFailedPractical()) {
								sto.setIsOverallTheoryFailed(bo .getIsTheoryOverallFailed());
								sto.setIsOverallPracticalFailed(bo .getIsPracticalOverallFailed());
							} else {
								sto.setIsOverallTheoryFailed(false);
								sto.setIsOverallPracticalFailed(false);
							}





							sto.setChance(bo.getChance());
							// sto.setFees(bo.getFees());
							if (bo.getFees() != null && !bo.getFees().isEmpty())
								sto.setPreviousFees(Double .parseDouble(bo.getFees()));
							sto.setIsFailedTheory(bo.getIsFailedTheory());
							sto.setIsFailedPractical(bo.getIsFailedPractical());
							if (bo.getIsAppearedTheory() != null){
								sto.setTempChecked(bo.getIsAppearedTheory());
								sto.setCommonChecked(bo.getIsAppearedTheory());
							}

							else
								sto.setTempChecked(false);
							if (bo.getIsAppearedPractical() != null){
								sto.setTempPracticalChecked(bo .getIsAppearedPractical());
								sto.setCommonChecked(bo.getIsAppearedPractical());
							}

							else
								sto.setTempPracticalChecked(false);
							boolean controlDisable = false;
							if ((bo.getIsFailedPractical() == true) || (bo.getIsFailedTheory() == true)) {
								controlDisable = true;
							}
							sto.setControlDisable(controlDisable);
							sto.setClassId(bo.getClasses().getId());
							sto.setSchemeNo(bo.getSchemeNo());
							if(bo.getIsOnline()!=null)
								sto.setOnline(bo.getIsOnline());
							else
								sto.setOnline(false);


							//raghu added newly setting supply/improve into sub list
							if(bo.getIsSupplementary()!=null){
								sto.setIsSupplementary(bo.getIsSupplementary());
								isSupplementary=true;
							}

							//raghu added newly setting supply/improve into sub list

							boolean isPg=false;

							boolean isFinalYear=false;
							if(bo.getIsImprovement()!=null){
								sto.setIsImprovement(bo.getIsImprovement());
								if(bo.getIsImprovement() && (to.getSemNo().equalsIgnoreCase("5") || to.getSemNo().equalsIgnoreCase("5") )){
									isFinalYear=true;
								}
								if(bo.getIsImprovement()){
									if(programType.equalsIgnoreCase("PG"))
										isPg=true;
								}

							}

							newSupplementaryImpApplicationForm.setExamType("Supplementary/Improvement");

							boolean isSecondImp = false;
							if(subIdMap.containsKey(bo.getSubject().getId())){
								isSecondImp = subIdMap.get(bo.getSubject().getId());
							}
							if(!isSecondImp && !isFinalYear && !isPg){

								subjectList.add(sto);
								to.setToList(subjectList);
							}
							if(to.isSupplementary())
								if ((sto.isTempChecked() != sto.getIsFailedTheory()) || (sto.isTempPracticalChecked() != sto .getIsFailedPractical())){
									buttonDisplay = true;
								}
							if(to.isImprovement())
								if ((sto.isTheory() && !sto.isTempChecked()) || (!sto.isTempPracticalChecked() && sto.isPractical())){
									buttonDisplay = true;
								}

							//raghu added from mounts


							if(bo.getCiaExam()!= null)
							{
								if(bo.getCiaExam())

								{

									sto.setCiaExam(true);
									sto.setIsFailedTheory(bo.getIsCIAFailedTheory());
									sto.setIsFailedPractical(bo.getIsCIAFailedPractical());
									if (bo.getIsCIAAppearedTheory() != null)
									{
										sto.setTempChecked(bo.getIsCIAAppearedTheory());
										if(bo.getIsCIAAppearedTheory())
										{
											sto.setTempCIAExamChecked(true);
										}
										else
										{
											sto.setTempCIAExamChecked(false);
										}
									}
									else
									{
										sto.setTempChecked(false);
									}
									if (bo.getIsCIAAppearedPractical() != null)
									{
										sto.setTempPracticalChecked(bo .getIsCIAAppearedPractical());
										if(bo.getIsCIAAppearedPractical())
										{
											sto.setTempCIAExamChecked(true);
										}
										else
										{
											sto.setTempCIAExamChecked(false);
										}
									}
									else
									{
										sto.setTempPracticalChecked(false);
									}
									if(bo.getIsCIAAppearedPractical() || bo.getIsCIAAppearedTheory() )
									{
										sto.setTempCIAExamChecked(true);
									}
									if(bo.getIsCIAAppearedPractical()!=null)
									{
										sto.setIsCIAAppearedPractical(bo.getIsCIAAppearedPractical());
									}
									else
									{
										sto.setIsCIAAppearedPractical(false);
									}
									if(bo.getIsCIAAppearedTheory()!=null)
									{
										sto.setIsCIAAppearedTheory(bo.getIsCIAAppearedTheory());
									}
									else
									{
										sto.setIsCIAAppearedTheory(false);
									}
									if(bo.getIsCIAFailedPractical()!=null)
									{
										sto.setIsCIAFailedPractical(bo.getIsCIAFailedPractical());
									}
									else
									{
										sto.setIsCIAFailedPractical(false);
									}
									if(bo.getIsCIAFailedTheory()!=null)
									{
										sto.setIsCIAFailedTheory(bo.getIsCIAFailedTheory());
									}
									else
									{
										sto.setIsCIAFailedTheory(false);
									}
								}
								else
								{
									sto.setTempCIAExamChecked(false);
									sto.setIsCIAAppearedPractical(false);
									sto.setIsCIAAppearedTheory(false);
									sto.setIsCIAFailedPractical(false);
									sto.setIsCIAFailedTheory(false);
									sto.setCiaExam(false);
								}
							}
							//
							else
							{
								sto.setTempCIAExamChecked(false);
								sto.setIsCIAAppearedPractical(false);
								sto.setIsCIAAppearedTheory(false);
								sto.setIsCIAFailedPractical(false);
								sto.setIsCIAFailedTheory(false);
								sto.setCiaExam(false);
							}
							if(bo.getCiaExam()!=null)
							{
								if(bo.getCiaExam())
								{
									sto.setIsESE(false);
								}
								else 
								{
									if(bo.getIsAppearedPractical()|| bo.getIsAppearedTheory() )
									{
										sto.setIsESE(true);
									}
									else
									{
										sto.setIsESE(false);
									}
								}

							}
							else 
							{
								if(bo.getIsAppearedPractical()|| bo.getIsAppearedTheory() )
								{
									sto.setIsESE(true);
								}
								else
								{
									sto.setIsESE(false);
								}
							}

							newSupplementaryImpApplicationForm.setPrevClassId(bo.getClasses().getId()+"");

							classList.add(to);
						}

					}
				}
				//Basim did for different fine fees(Fine,Super Fine)
				query="from PublishSupplementaryImpApplication p"+
				" where p.classCode.id="+newSupplementaryImpApplicationForm.getPrevClassId()+
				" and p.exam.id=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId());
				List<PublishSupplementaryImpApplication> fineDetails=transaction.getDataForQuery(query);
				PublishSupplementaryImpApplication supplFees=null;
				if(fineDetails!=null && fineDetails.size()>0){
					supplFees=fineDetails.get(0);
				}
				if(isTotal==false && isChecked==true){
					cvCampFee=0;
					paperFee=0;
					applicationFee=0;
					marksListFee=0;
					RegistrationFee=CMSConstants.SC_REGULAR_SUPPLY_FEE;
				}
				if(isTotal==true && isChecked==true && isScSupply==true){
					double regForSC=CMSConstants.SC_REGULAR_SUPPLY_FEE;
					RegistrationFee+=regForSC;
				}
				totalfee=cvCampFee+RegistrationFee+paperFee+applicationFee+marksListFee;
				if(newSupplementaryImpApplicationForm.isExtended())
					if(transaction1.checkSubmitSuppApp(newSupplementaryImpApplicationForm))
					{
						newSupplementaryImpApplicationForm.setExtended(false);
					}
				if(newSupplementaryImpApplicationForm.isExtended())
				{
					double fine=0.0;
					if(newSupplementaryImpApplicationForm.getIsFine()){
						fine=Double.parseDouble(supplFees.getFineAmount());
						newSupplementaryImpApplicationForm.setFineFees(fine);
					}
					else if(newSupplementaryImpApplicationForm.getIsSuperFine()){
						fine=Double.parseDouble(supplFees.getSuperFineAmount());
						newSupplementaryImpApplicationForm.setFineFees(fine);

					}

					totalfee+=fine;
				}
				else
				{
					newSupplementaryImpApplicationForm.setFineFees(0);
				}
				//Basim did for adding amount in Exam Reg App Table
				boolean isAmountAddedRegApp=transaction1.addAmountToExamRegApp(newSupplementaryImpApplicationForm, totalfee);
				newSupplementaryImpApplicationForm.setRegistrationFees(RegistrationFee);
				newSupplementaryImpApplicationForm.setTotalFees(totalfee);
				newSupplementaryImpApplicationForm.setTotalCvCampFees(cvCampFee);
				newSupplementaryImpApplicationForm.setTotalPaperFees(paperFee);
				newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalfee));
				if(isTotal==false && isChecked==true){
					newSupplementaryImpApplicationForm.setPaperFees(0);
					newSupplementaryImpApplicationForm.setCvCampFees(0);
					//newSupplementaryImpApplicationForm.setRegistrationFees(0);
					newSupplementaryImpApplicationForm.setApplicationFees(0);
					newSupplementaryImpApplicationForm.setMarksListFees(0);
					newSupplementaryImpApplicationForm.setTotalCvCampFees(0);
					//newSupplementaryImpApplicationForm.setTotalFees(0);
					newSupplementaryImpApplicationForm.setTotalPaperFees(0);
					//newSupplementaryImpApplicationForm.setTotalFeesInWords("Zero");
				}
				newSupplementaryImpApplicationForm.setIsSupplPaper(isSupplementary);
				eto.setExamList(classList);
				examList.add(eto);
			}
			else
				newSupplementaryImpApplicationForm.setSuppImpAppAvailable(false);
		}
		newSupplementaryImpApplicationForm.setDisplayButton(buttonDisplay);
		newSupplementaryImpApplicationForm.setMainList(examList);
		newSupplementaryImpApplicationForm.setChallanDisplay(challanDisplay);
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 */
	public boolean saveSupplementaryApplicationForStudentLogin( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		
		/*OnlinePaymentReciepts onlinePaymentReciepts=new OnlinePaymentReciepts();
		PcFinancialYear pcFinancialYear=new PcFinancialYear();
		pcFinancialYear.setId(newSupplementaryImpApplicationForm.getFinId());
		onlinePaymentReciepts.setPcFinancialYear(pcFinancialYear);
		 */
		
		ExamRegularApplication examRegApp=null;
		Student student = new Student();
		student.setId(newSupplementaryImpApplicationForm.getStudentId());

		/*
		onlinePaymentReciepts.setStudent(student);
		onlinePaymentReciepts.setTotalAmount(new BigDecimal(newSupplementaryImpApplicationForm.getTotalFees()));
		onlinePaymentReciepts.setIsActive(true);
		onlinePaymentReciepts.setCreatedBy(newSupplementaryImpApplicationForm.getUserId());
		onlinePaymentReciepts.setCreatedDate(new Date());
		onlinePaymentReciepts.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
		onlinePaymentReciepts.setLastModifiedDate(new Date());
		onlinePaymentReciepts.setApplicationFor("Supplementary Application");
		//raghu  added from mounts
		onlinePaymentReciepts.setIsPaymentFailed(Boolean.FALSE);

		PropertyUtil.getInstance().save(onlinePaymentReciepts);
		 */
		//raghu added from mounts
		//boolean isPaymentSuccess=false;
		boolean isPaymentSuccess=true;

		/*
		if(onlinePaymentReciepts.getId()>0){
			newSupplementaryImpApplicationForm.setOnlinePaymentId(onlinePaymentReciepts.getId());
			String registerNo=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(newSupplementaryImpApplicationForm.getStudentId(),"Student",true,"registerNo");
			//raghu added from mounts
			//OnlinePaymentUtils.dedactAmountFromAccount(registerNo,onlinePaymentReciepts.getId(), newSupplementaryImpApplicationForm.getTotalFees(), onlinePaymentReciepts);

			if(!onlinePaymentReciepts.getIsPaymentFailed()){
				isPaymentSuccess=true;
				transaction.updateAndGenerateRecieptNoOnlinePaymentReciept(onlinePaymentReciepts);
			}else
				newSupplementaryImpApplicationForm.setMsg(onlinePaymentReciepts.getStatus());
		}*/
		if(isPaymentSuccess){
			
			List<StudentSupplementaryImprovementApplication> boList=new ArrayList<StudentSupplementaryImprovementApplication>();
			List<SupplementaryAppExamTo> examList=newSupplementaryImpApplicationForm.getMainList();

			for (SupplementaryAppExamTo suppTo : examList) {
				ExamDefinition examDefinition = new ExamDefinition();
				examDefinition.setId(suppTo.getExamId());
				//Adding Supplementary Details to Regular App
				ExamDefinitionBO examDefinitionBo = new ExamDefinitionBO();
				examDefinitionBo.setId(suppTo.getExamId());
				List<SupplementaryApplicationClassTo> classTos=suppTo.getExamList();
				for (SupplementaryApplicationClassTo cto: classTos) {
					Classes classes = new Classes();
					classes.setId(cto.getClassId());
					newSupplementaryImpApplicationForm.setPrevClassId(cto.getClassId()+"");
					List<ExamSupplementaryImpApplicationSubjectTO> listSubject = cto.getToList();
					for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
						StudentSupplementaryImprovementApplication objBO = new StudentSupplementaryImprovementApplication();
						if(examRegApp==null){
							examRegApp=new ExamRegularApplication();
							examRegApp.setClasses(classes);
							examRegApp.setExam(examDefinitionBo);
							if(newSupplementaryImpApplicationForm.getRegNo()!=null){
								examRegApp.setChallanNo("SU"+newSupplementaryImpApplicationForm.getRegNo()+newSupplementaryImpApplicationForm.getExamId());
								newSupplementaryImpApplicationForm.setChalanNo(examRegApp.getChallanNo());
							}
							examRegApp.setStudent(student);
							examRegApp.setCreatedBy(newSupplementaryImpApplicationForm.getUserId());
							examRegApp.setCreatedDate(new Date());
							examRegApp.setLastModifiedDate(new Date());
							examRegApp.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
							examRegApp.setIsApplied(true);

						}

						if(to.getId()!=null)
							objBO.setId(to.getId());
						objBO.setCreatedBy(newSupplementaryImpApplicationForm.getUserId());
						objBO.setCreatedDate(new Date());
						objBO.setLastModifiedDate(new Date());
						objBO.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
						objBO.setStudent(student);
						objBO.setExamDefinition(examDefinition);
						Subject subject = new Subject();
						subject.setId(to.getSubjectId());
						objBO.setSubject(subject);

						//raghu change code for both imp/sup
						//if(cto.isSupplementary()){
						if(to.getIsSupplementary()){


							//raghu added from mounts
							// new modification
							// if cia selected then all the property in cse is 0 and property in cia get fille

							if(to.isCiaExam())
							{
								if (to.getFailedTheory() != null) {
									objBO.setIsFailedTheory(false);
								} else {
									objBO .setIsFailedTheory(false);
								}
								if (to.getFailedPractical() != null) {
									objBO.setIsFailedPractical(false);
								} else {
									objBO.setIsFailedPractical(false);
								}
								double fees=0;
								if(to.getPreviousFees()>0)
									fees=fees+to.getPreviousFees();
								boolean isEntered=false;
								if(!to.isTempChecked()){
									objBO.setIsAppearedTheory(false);
									if(to.getIsAppearedTheory()){
										fees=fees+newSupplementaryImpApplicationForm.getTheoryFees();
										objBO.setIsOnline(true);
										isEntered=true;
									}else
										objBO.setIsOnline(to.isOnline());
								}else{
									objBO.setIsAppearedTheory(false);
									objBO.setIsOnline(to.isOnline());
								}
								if(!to.isTempPracticalChecked()){
									objBO.setIsAppearedPractical(false);
									if(to.getIsAppearedPractical()){
										fees=fees+newSupplementaryImpApplicationForm.getPracticalFees();
										if(!isEntered){
											objBO.setIsOnline(true);
											isEntered=true;
										}
									}else{
										if(!isEntered)
											objBO.setIsOnline(to.isOnline());
									}
								}else{
									objBO.setIsAppearedPractical(false);
									if(!isEntered)
										objBO.setIsOnline(to.isOnline());
								}
								objBO.setFees(String.valueOf(fees));
								// new feilds
								if (to.getFailedTheory() != null) {
									objBO.setIsCIAFailedTheory(true);
								} else {
									objBO.setIsCIAFailedTheory((to.getIsCIAFailedTheory()) ? true : false);
								}
								if (to.getFailedPractical() != null) {
									objBO.setIsCIAFailedPractical(true);
								} else {
									objBO.setIsCIAFailedPractical((to.getIsCIAFailedPractical()) ? true : false);
								}
								if(!to.isTempChecked()){
									objBO.setIsCIAAppearedTheory(to.getIsCIAAppearedTheory());
									if(to.getIsCIAAppearedTheory()){
										fees=fees+newSupplementaryImpApplicationForm.getTheoryFees();
										objBO.setIsOnline(true);
										isEntered=true;
									}else
										objBO.setIsOnline(to.isOnline());
								}else{
									objBO.setIsCIAAppearedTheory(true);
									objBO.setIsOnline(to.isOnline());
								}
								if(!to.isTempPracticalChecked()){
									objBO.setIsCIAAppearedPractical(to.getIsCIAAppearedPractical());
									if(to.getIsCIAAppearedPractical()){
										fees=fees+newSupplementaryImpApplicationForm.getPracticalFees();
										if(!isEntered){
											objBO.setIsOnline(true);
											isEntered=true;
										}
									}else{
										if(!isEntered)
											objBO.setIsOnline(to.isOnline());
									}
								}else{
									objBO.setIsCIAAppearedPractical(true);
									if(!isEntered)
										objBO.setIsOnline(to.isOnline());
								}
								if(to.getIsAppearedTheory() || to.isTempChecked())
								{
									objBO.setIsCIAAppearedTheory(true);
									objBO.setIsCIAFailedTheory(true);
									objBO.setIsCIAFailedPractical(false);
									objBO.setIsCIAAppearedPractical(false);
									objBO.setCiaExam(true);

								}
								else if(to.getIsAppearedPractical() || to.isTempPracticalChecked())
								{
									objBO.setIsCIAAppearedTheory(false);
									objBO.setIsCIAFailedTheory(false);
									objBO.setIsCIAFailedPractical(true);
									objBO.setIsCIAAppearedPractical(true);
									objBO.setCiaExam(true);
								}
								else
								{
									objBO.setIsCIAAppearedTheory(false);
									objBO.setIsCIAFailedTheory(false);
									objBO.setIsCIAFailedPractical(false);
									objBO.setIsCIAAppearedPractical(false);
									objBO.setCiaExam(false);

									objBO.setIsFailedPractical(to.getIsFailedPractical());
									objBO.setIsAppearedPractical(false);
									objBO.setIsAppearedTheory(false);
									objBO.setIsFailedTheory(to.getIsFailedTheory());
								}

							}
							else // not cia
							{



								if (to.getFailedTheory() != null) {
									objBO.setIsFailedTheory(true);
								} else {
									objBO .setIsFailedTheory((to.getIsFailedTheory()) ? true : false);
								}
								if (to.getFailedPractical() != null) {
									objBO.setIsFailedPractical(true);
								} else {
									objBO.setIsFailedPractical((to.getIsFailedPractical()) ? true : false);
								}
								double fees=0;
								if(to.getPreviousFees()>0)
									fees=fees+to.getPreviousFees();
								boolean isEntered=false;
								if(!to.isTempChecked()){
									objBO.setIsAppearedTheory(to.getIsAppearedTheory());
									if(to.getIsAppearedTheory()){
										fees=fees+newSupplementaryImpApplicationForm.getTheoryFees();
										objBO.setIsOnline(true);
										isEntered=true;
									}else
										objBO.setIsOnline(to.isOnline());
								}else{
									objBO.setIsAppearedTheory(true);
									objBO.setIsOnline(to.isOnline());
								}
								if(!to.isTempPracticalChecked()){
									objBO.setIsAppearedPractical(to.getIsAppearedPractical());
									if(to.getIsAppearedPractical()){
										fees=fees+newSupplementaryImpApplicationForm.getPracticalFees();
										if(!isEntered){
											objBO.setIsOnline(true);
											isEntered=true;
										}
									}else{
										if(!isEntered)
											objBO.setIsOnline(to.isOnline());
									}
								}else{
									objBO.setIsAppearedPractical(true);
									if(!isEntered)
										objBO.setIsOnline(to.isOnline());
								}
								objBO.setFees(String.valueOf(fees));



								//raghu added from mounts

								// nee field
								objBO.setIsCIAAppearedPractical(false);
								objBO.setIsCIAAppearedTheory(false);
								objBO.setIsCIAFailedPractical(false);
								objBO.setIsCIAFailedTheory(false);
								objBO.setCiaExam(false);

							}



						}





						//raghu change code for both imp/sup
						//if(cto.isImprovement()){
						if(to.getIsImprovement()){

							double fees=0;
							if(to.getPreviousFees()>0)
								fees=fees+to.getPreviousFees();
							boolean isEntered=false;
							if(!to.isTempChecked()){
								objBO.setIsAppearedTheory(to.getIsAppearedTheory());
								//objBO.setIsFailedTheory(to.getIsAppearedTheory());
								//raghu put for improvement
								objBO.setIsFailedTheory(to.getIsFailedTheory());

								if(to.getIsAppearedTheory()){
									fees=fees+newSupplementaryImpApplicationForm.getTheoryFees();
									objBO.setIsOnline(true);
									isEntered=true;
								}else
									objBO.setIsOnline(to.isOnline());
							}else{
								objBO.setIsAppearedTheory(true);
								objBO.setIsFailedTheory(true);
								objBO.setIsOnline(to.isOnline());
							}
							if(!to.isTempPracticalChecked()){
								objBO.setIsAppearedPractical(to.getIsAppearedPractical());
								//objBO.setIsFailedPractical(to.getIsAppearedPractical());
								//raghu put for improvement
								objBO.setIsFailedPractical(to.getIsFailedPractical());

								if(to.getIsAppearedPractical()){
									fees=fees+newSupplementaryImpApplicationForm.getPracticalFees();
									if(!isEntered){
										objBO.setIsOnline(true);
										isEntered=true;
									}
								}else{
									if(!isEntered)
										objBO.setIsOnline(to.isOnline());
								}
							}else{
								objBO.setIsAppearedPractical(true);
								objBO.setIsFailedPractical(true);
								if(!isEntered)
									objBO.setIsOnline(to.isOnline());
							}
							objBO.setFees(String.valueOf(fees));
						}
						objBO.setSchemeNo(to.getSchemeNo());
						objBO.setChance(to.getChance());
						if(to.getIsOverallTheoryFailed()!=null)
							objBO.setIsTheoryOverallFailed(to.getIsOverallTheoryFailed());
						else
							objBO.setIsTheoryOverallFailed(false);
						if(to.getIsOverallPracticalFailed()!=null)
							objBO.setIsPracticalOverallFailed(to.getIsOverallPracticalFailed());
						else
							objBO.setIsPracticalOverallFailed(false);
						objBO.setClasses(classes);
						/*objBO.setIsSupplementary(true);
					objBO.setIsImprovement(false);*/
						objBO.setLastModifiedDate(new Date());

						//raghu change code for both imp/sup
						//if (cto.isSupplementary()) {
						if (to.getIsSupplementary()) {
							objBO.setIsSupplementary(true);
							objBO.setIsImprovement(false);
						} 

						//raghu change code for both imp/sup
						//else if (cto.isImprovement()) {
						else if (to.getIsImprovement()) {
							objBO.setIsImprovement(true);
							objBO.setIsSupplementary(false);
						}
						boList.add(objBO);
					}
				}

			}
			boolean isDuplicate=transaction.checkDuplicationRegularExamApp(newSupplementaryImpApplicationForm);
			boolean isAdded=false;
			if(!isDuplicate){
				isAdded=transaction.addAppliedStudent(examRegApp);
				return transaction.saveSupplementarys(boList) && isAdded;
			}
			else
				return transaction.saveSupplementarys(boList);
		}else
			return false;
	}

	/**
	 * @param smartCardNo
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public boolean verifySmartCard(String smartCardNo, int studentId) throws Exception {
		String query="select s.smartCardNo from Student s where s.id="+studentId+" and s.smartCardNo like '%"+smartCardNo+"'";
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List list=txn.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @param onlinePaymentId
	 * @return
	 * @throws Exception
	 */
	public String getPrintData(int onlinePaymentId,HttpServletRequest request) throws Exception {
		ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
		OnlinePaymentReciepts bo=(OnlinePaymentReciepts) txn.getMasterEntryDataById(OnlinePaymentReciepts.class,onlinePaymentId);
		List<GroupTemplate> list= TemplateHandler.getInstance().getDuplicateCheckList(0,CMSConstants.TEMPLATE_SUPPLEMENTARY_APPLICATION_PRINT);
		return NewSupplementaryImpApplicationHelper.getInstance().getPrintData(bo,list,request);
	}



	//raghu added from mounts

	public void setAppNoAndDate(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		log.debug("call of setAppNoAndDate mehtod in  newSupplementaryImpApplicationHandler.class");
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		newSupplementaryImpApplicationForm.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
		String appNo = txn.getApplicationNumber(newSupplementaryImpApplicationForm.getStudentId());
		newSupplementaryImpApplicationForm.setApplicationNumber(appNo);
		log.debug("end of setAppNoAndDate mehtod in  newSupplementaryImpApplicationHandler.class");

		// TODO Auto-generated method stub

	}

	//raghu added for improvement
	public List<Integer> checkSuppImpAppAvailable1(int studentId) throws Exception {
		String query="select p.exam.id from PublishSupplementaryImpApplication p " +
		" where p.isActive=1 and p.exam.delIsActive=1 and p.exam.id in ( select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
		" where s.student.id= " +studentId+
		//raghu added from mounts
		//" and (((s.isFailedPractical=1 or s.isFailedTheory=1) or (s.isTheoryOverallFailed=1 or s.isPracticalOverallFailed=1)) and (s.isSupplementary=1 or s.isImprovement=1))and s.classes.id in(p.classCode.id)) " +
		//" and (((s.isFailedPractical=1 or s.isFailedTheory=1 or s.isCIAFailedPractical=1 or s.isCIAFailedTheory=1) or (s.isTheoryOverallFailed=1 or s.isPracticalOverallFailed=1)) and (s.isSupplementary=1 or s.isImprovement=1))and s.classes.id in(p.classCode.id)) " +

		" and (((s.isFailedPractical=1 or s.isFailedTheory=1 or s.isCIAFailedPractical=1 or s.isCIAFailedTheory=1) and s.isSupplementary=1) or s.isImprovement=1) and s.classes.id in(p.classCode.id)) " +


		/*"and '"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between (p.startDate and p.endDate)  group by p.exam.id ";*/
		" and (('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.startDate and p.endDate) or ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.startDate and p.extendedDate)) group by p.exam.id "; 
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}




	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 */
	//raghu check updateprocess has done/ not for supply
	public String checkValidAll(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewStudentMarksCorrectionTransaction transaction1 = NewStudentMarksCorrectionTransactionImpl .getInstance();
		String msg="";

		String query="from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+
		" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId();
		List<StudentSupplementaryImprovementApplication> boList=transaction.getDataForQuery(query);
		if( boList.isEmpty() && boList.size()==0){
			msg="Do UpdateProcess For Supplementary";
		}

		return msg;
	}




	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 */
	//raghu check updateprocess has done/ not for supply
	public String checkImpValidAll(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewStudentMarksCorrectionTransaction transaction1 = NewStudentMarksCorrectionTransactionImpl .getInstance();
		String msg="";

		String query="from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+
		" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+" and s.isImprovement=1";
		List<StudentSupplementaryImprovementApplication> boList=transaction.getDataForQuery(query);
		if(!boList.isEmpty() && boList.size()!=0){
			msg="Already Improvement Has Done";
		}

		return msg;
	}





	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	//raghu
	public List<ExamSupplementaryImpApplicationTO> getStudentDetailsAll(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<ExamSupplementaryImpApplicationTO> list= new LinkedList<ExamSupplementaryImpApplicationTO>();
		List<Object[]> studentDetails=new ArrayList<Object[]>();

		String previousQuery="select s.student,s.classes from StudentPreviousClassHistory s " +
		" where s.schemeNo=" +newSupplementaryImpApplicationForm.getSchemeNo()+
		//" and s.student.id="+newSupplementaryImpApplicationForm.getStudentId();
		//" and s.classes.id in(select s.classes.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+")"+
		" and s.student.isAdmitted=1 and s.student.admAppln.isCancelled=0	and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+newSupplementaryImpApplicationForm.getExamId()+")"+
		" and s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+
		" and s.student.id not in(select s.student.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+")";


		List previousList=transaction.getDataForQuery(previousQuery);
		if(previousList.isEmpty()){
			String currentQuery="select s,s.classSchemewise.classes from Student s " +
			//"where s.id=" +newSupplementaryImpApplicationForm.getStudentId()+
			" where s.classSchemewise.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+
			" and s.classSchemewise.classes.termNumber="+newSupplementaryImpApplicationForm.getSchemeNo()+
			" and s.isAdmitted=1 and s.admAppln.isCancelled=0	 and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+newSupplementaryImpApplicationForm.getExamId()+")"+
			" and s.id not in(select s.student.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+")";
			//and s.classSchemewise.classes.id in(select s.classes.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+")";

			List currentList=transaction.getDataForQuery(currentQuery);
			studentDetails.addAll(currentList);
		}else{
			studentDetails.addAll(previousList);
		}













		ExamSupplementaryImpApplicationTO to =null;
		if(!studentDetails.isEmpty()){
			Iterator<Object[]> itr=studentDetails.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				Student student=(Student)objects[0];
				Classes classes=(Classes)objects[1];
				List<Subject> subList=new ArrayList<Subject>();



				//String subPreQuery="select subGrp.subject from StudentSubjectGroupHistory s join s.subjectGroup.subjectGroupSubjectses subGrp" +
				//" where s.schemeNo=" +newSupplementaryImpApplicationForm.getSchemeNo()+
				//" and s.student.id="+student.getId();

				String subPreQuery="select  distinct m.subject from MarksEntryDetails m where m.marksEntry.student.id="+student.getId()+" and m.marksEntry.exam.examTypeUtilBO.name='Regular' and  m.marksEntry.classes.termNumber=" +newSupplementaryImpApplicationForm.getSchemeNo()+" and m.marksEntry.exam.academicYear="+newSupplementaryImpApplicationForm.getYear()+" group by m.subject.id";
				// raghu change query bcz they enterd wrong data in student subject group

				List<Subject> preList=transaction.getDataForQuery(subPreQuery);
				if(!preList.isEmpty()){
					subList.addAll(preList);
				}

				String subQuery="select subGrp.subject from Student s join s.admAppln.applicantSubjectGroups appSub join appSub.subjectGroup.subjectGroupSubjectses subGrp" +
				" where s.id=" +student.getId()+" and subGrp.isActive=1"+
				" and s.classSchemewise.classes.termNumber="+newSupplementaryImpApplicationForm.getSchemeNo();

				List<Subject> suList=transaction.getDataForQuery(subQuery);
				if(subList.isEmpty())
					subList.addAll(suList);











				to=new ExamSupplementaryImpApplicationTO();
				to.setClassName(classes.getName());
				to.setCourseId(classes.getCourse().getId());
				to.setCourseName(classes.getCourse().getName());
				to.setClassId(classes.getId());
				to.setSection(classes.getSectionName());
				to.setStudentId(student.getId());
				StringBuffer stuName=new StringBuffer();
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getFirstName());
				if(student.getAdmAppln().getPersonalData().getMiddleName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getMiddleName());
				if(student.getAdmAppln().getPersonalData().getLastName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getLastName());

				to.setStudentName(stuName.toString());
				to.setRegNumber(student.getRegisterNo());
				to.setRollNumber(student.getRollNo());
				to.setSemester_year_no(classes.getTermNumber());
				to.setExamId(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()));
				List<ExamSupplementaryImpApplicationSubjectTO> subjectList=new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
				Iterator<Subject> subitr=subList.iterator();
				while (subitr.hasNext()) {
					Subject bo = subitr .next();
					ExamSupplementaryImpApplicationSubjectTO sto=new ExamSupplementaryImpApplicationSubjectTO();
					sto.setSubjectId(bo.getId());
					sto.setSubjectCode(bo.getCode());
					sto.setSubjectName(bo.getName());
					sto.setIsOverallPracticalFailed(false);
					sto.setIsOverallTheoryFailed(false);
					sto.setChance(0);
					sto.setIsFailedTheory(false);
					sto.setIsFailedPractical(false);
					sto.setTempChecked(false);
					sto.setTempPracticalChecked(false);
					sto.setControlDisable(false);
					sto.setClassId(to.getClassId());
					subjectList.add(sto);
				}// sub while close

				to.setSubjectList(subjectList);
				list.add(to);

			}// student while close
		}// empty close





		return list;
	}









	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	//raghu
	public List<ExamSupplementaryImpApplicationTO> getStudentDetailsAllSupply(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<ExamSupplementaryImpApplicationTO> list= new LinkedList<ExamSupplementaryImpApplicationTO>();
		List<Object[]> studentDetails=new ArrayList<Object[]>();

		String previousQuery="select s.student,s.classes from StudentPreviousClassHistory s " +
		" where s.schemeNo=" +newSupplementaryImpApplicationForm.getSchemeNo()+
		//" and s.student.id="+newSupplementaryImpApplicationForm.getStudentId();
		" and s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+
		" and s.student.id in(select s.student.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+")";
		//and s.classes.id in(select s.classes.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+")";

		List previousList=transaction.getDataForQuery(previousQuery);
		if(previousList.isEmpty()){
			String currentQuery="select s,s.classSchemewise.classes from Student s " +
			//"where s.id=" +newSupplementaryImpApplicationForm.getStudentId()+
			" where s.classSchemewise.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+
			" and s.classSchemewise.classes.termNumber="+newSupplementaryImpApplicationForm.getSchemeNo()+
			" and s.id in(select s.student.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+")";
			//and s.classSchemewise.classes.id in(select s.classes.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+")";

			List currentList=transaction.getDataForQuery(currentQuery);
			studentDetails.addAll(currentList);
		}else{
			studentDetails.addAll(previousList);
		}













		ExamSupplementaryImpApplicationTO to =null;
		if(!studentDetails.isEmpty()){
			Iterator<Object[]> itr=studentDetails.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				Student student=(Student)objects[0];
				Classes classes=(Classes)objects[1];
				List<Subject> subList=new ArrayList<Subject>();




				//String subPreQuery="select subGrp.subject from StudentSubjectGroupHistory s join s.subjectGroup.subjectGroupSubjectses subGrp" +
				//" where s.schemeNo=" +newSupplementaryImpApplicationForm.getSchemeNo()+
				//" and s.student.id="+student.getId()+
				//" and subGrp.subject.id not in(select s.subject.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+" and s.student.id="+student.getId()+")";


				String subPreQuery="select distinct m.subject from MarksEntryDetails m where m.marksEntry.student.id="+student.getId()+" and m.marksEntry.exam.examTypeUtilBO.name='Regular' and  m.marksEntry.classes.termNumber=" +newSupplementaryImpApplicationForm.getSchemeNo()+" and m.marksEntry.exam.academicYear="+newSupplementaryImpApplicationForm.getYear()+
				" and m.subject.id not in(select s.subject.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+" and s.student.id="+student.getId()+")   group by m.subject.id";

				// raghu change query bcz they enterd wrong data in student subject group

				List<Subject> preList=transaction.getDataForQuery(subPreQuery);
				if(!preList.isEmpty()){
					subList.addAll(preList);
				}

				String subQuery="select distinct subGrp.subject from Student s join s.admAppln.applicantSubjectGroups appSub join appSub.subjectGroup.subjectGroupSubjectses subGrp" +
				" where s.id=" +student.getId()+" and subGrp.isActive=1"+
				" and s.classSchemewise.classes.termNumber="+newSupplementaryImpApplicationForm.getSchemeNo()+
				" and subGrp.subject.id not in(select s.subject.id from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+" and s.student.id="+student.getId()+")";

				List<Subject> suList=transaction.getDataForQuery(subQuery);
				if(subList.isEmpty())
					subList.addAll(suList);








				to=new ExamSupplementaryImpApplicationTO();
				to.setClassName(classes.getName());
				to.setCourseId(classes.getCourse().getId());
				to.setCourseName(classes.getCourse().getName());
				to.setClassId(classes.getId());
				to.setSection(classes.getSectionName());
				to.setStudentId(student.getId());
				StringBuffer stuName=new StringBuffer();
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getFirstName());
				if(student.getAdmAppln().getPersonalData().getMiddleName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getMiddleName());
				if(student.getAdmAppln().getPersonalData().getLastName()!=null)
					stuName.append(student.getAdmAppln().getPersonalData().getLastName());

				to.setStudentName(stuName.toString());
				to.setRegNumber(student.getRegisterNo());
				to.setRollNumber(student.getRollNo());
				to.setSemester_year_no(classes.getTermNumber());
				to.setExamId(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()));
				List<ExamSupplementaryImpApplicationSubjectTO> subjectList=new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
				Iterator<Subject> subitr=subList.iterator();
				while (subitr.hasNext()) {
					Subject bo = subitr .next();
					ExamSupplementaryImpApplicationSubjectTO sto=new ExamSupplementaryImpApplicationSubjectTO();
					sto.setSubjectId(bo.getId());
					sto.setSubjectCode(bo.getCode());
					sto.setSubjectName(bo.getName());
					sto.setIsOverallPracticalFailed(false);
					sto.setIsOverallTheoryFailed(false);
					sto.setChance(0);
					sto.setIsFailedTheory(false);
					sto.setIsFailedPractical(false);
					sto.setTempChecked(false);
					sto.setTempPracticalChecked(false);
					sto.setControlDisable(false);
					sto.setClassId(to.getClassId());
					subjectList.add(sto);
				}// sub while close

				to.setSubjectList(subjectList);
				list.add(to);

			}// student while close
		}// empty close




		return list;
	}








	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveSupplementaryApplicationAll( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {


		boolean isAdded=false;

		try{


			INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
			List<StudentSupplementaryImprovementApplication> boList=new ArrayList<StudentSupplementaryImprovementApplication>();
			List<ExamSupplementaryImpApplicationTO> suppToList = newSupplementaryImpApplicationForm .getSuppToList();
			Iterator<ExamSupplementaryImpApplicationTO> iterator=suppToList.iterator();

			while(iterator.hasNext()){

				ExamSupplementaryImpApplicationTO suppTo=iterator.next();
				//ExamSupplementaryImpApplicationTO suppTo = newSupplementaryImpApplicationForm .getSuppTo();

				ExamDefinition examDefinition = new ExamDefinition();
				examDefinition.setId(suppTo.getExamId());
				Student student = new Student();
				student.setId(suppTo.getStudentId());
				Classes classes = new Classes();
				classes.setId(suppTo.getClassId());
				List<ExamSupplementaryImpApplicationSubjectTO> listSubject = suppTo .getSubjectList();
				for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
					StudentSupplementaryImprovementApplication objBO = new StudentSupplementaryImprovementApplication();
					if(to.getId()!=null)
						objBO.setId(to.getId());
					objBO.setCreatedBy(newSupplementaryImpApplicationForm.getUserId());
					objBO.setCreatedDate(new Date());
					objBO.setLastModifiedDate(new Date());
					objBO.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
					objBO.setStudent(student);
					objBO.setExamDefinition(examDefinition);
					Subject subject = new Subject();
					subject.setId(to.getSubjectId());
					objBO.setSubject(subject);
					if (to.getFailedTheory() != null) {
						objBO.setIsFailedTheory(true);
					} else {
						objBO .setIsFailedTheory((to.getIsFailedTheory()) ? true : false);
					}
					if (to.getFailedPractical() != null) {
						objBO.setIsFailedPractical(true);
					} else {
						objBO.setIsFailedPractical((to.getIsFailedPractical()) ? true : false);
					}
					objBO.setIsAppearedTheory(to.getIsAppearedTheory());
					objBO.setIsAppearedPractical(to.getIsAppearedPractical());
					objBO.setFees(to.getFees());
					objBO.setSchemeNo(suppTo.getSemester_year_no());
					objBO.setChance(to.getChance());
					if(to.getIsOverallTheoryFailed()!=null)
						objBO.setIsTheoryOverallFailed(to.getIsOverallTheoryFailed());
					else
						objBO.setIsTheoryOverallFailed(false);
					if(to.getIsOverallPracticalFailed()!=null)
						objBO.setIsPracticalOverallFailed(to.getIsOverallPracticalFailed());
					else
						objBO.setIsPracticalOverallFailed(false);
					objBO.setClasses(classes);
					if (newSupplementaryImpApplicationForm .getSupplementaryImprovement() != null && newSupplementaryImpApplicationForm .getSupplementaryImprovement().trim().length() > 0) {
						if (newSupplementaryImpApplicationForm .getSupplementaryImprovement().equalsIgnoreCase("Supplementary")) {
							objBO.setIsSupplementary(true);
							objBO.setIsImprovement(false);
						} else if (newSupplementaryImpApplicationForm
								.getSupplementaryImprovement().equalsIgnoreCase("Improvement")) {
							objBO.setIsImprovement(true);
							objBO.setIsSupplementary(false);
						}
					}
					objBO.setIsOnline(false);




					//raghu added from mounts

					objBO.setIsAppearedTheory(to.getIsAppearedTheory());
					objBO.setIsAppearedPractical(to.getIsAppearedPractical());


					// new modification


					if(to.isCiaExam())
					{
						if(to.getIsAppearedTheory())
						{
							objBO.setIsAppearedTheory(false);
							objBO.setIsAppearedPractical(false);
							objBO.setIsFailedPractical(false);
							objBO.setIsFailedTheory(false);

							// cia
							objBO.setIsCIAAppearedTheory(to.getIsAppearedTheory());
							objBO.setIsCIAFailedTheory(true);
							objBO.setIsCIAFailedPractical(false);
							objBO.setIsCIAAppearedPractical(false);
							objBO.setCiaExam(true);
						}
						if(to.getIsAppearedPractical())
						{
							objBO.setIsAppearedTheory(false);
							objBO.setIsAppearedPractical(false);
							objBO.setIsFailedPractical(false);
							objBO.setIsFailedTheory(false);

							// cia
							objBO.setIsCIAAppearedTheory(false);
							objBO.setIsCIAFailedTheory(true);
							objBO.setIsCIAFailedPractical(true);
							objBO.setIsCIAAppearedPractical(to.getIsAppearedPractical());
							objBO.setCiaExam(true);
						}
					}
					else
					{
						objBO.setIsAppearedTheory(to.getIsAppearedTheory());
						objBO.setIsAppearedPractical(to.getIsAppearedPractical());
						objBO.setIsFailedPractical(to.getIsFailedPractical());
						objBO.setIsFailedTheory(to.getIsFailedTheory());

						objBO.setIsCIAAppearedTheory(false);
						objBO.setIsCIAFailedTheory(false);
						objBO.setIsCIAFailedPractical(false);
						objBO.setIsCIAAppearedPractical(false);
						objBO.setCiaExam(false);
					}


					boList.add(objBO);
				}//for loop

				isAdded=transaction.saveSupplementarys(boList);
			}//while loop



		}catch (Exception e) {


			Session session = HibernateUtil.getSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			String query="delete from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+
			" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId()+" and s.isImprovement=1";

			Query query1=session.createQuery(query);
			//query.setString("current_date1",date);
			query1.executeUpdate();
			transaction.commit();
			log.error("#############################  error in   newSupplementaryImpApplicationHandler.class while adding all improvement application ################################"+e);
		}


		return isAdded;	
	}


	//raghu for regular exam application

	public List<Integer> checkRegularAppAvailable(int classId,boolean isExtended) throws Exception {
		String query="select eb.examId from ExamPublishHallTicketMarksCardBO eb where eb.isActive=1 and eb.publishFor='Regular Application' and eb.examDefinitionBO.examTypeUtilBO.name='Regular' and eb.classes.id=" +classId+
		" and (('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between eb.downloadStartDate and eb.downloadEndDate)  "; 
		if(isExtended){
			query+=" or ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between eb.downloadEndDate and eb.extendedDate))  "; 
		}else{
			query+=")";
		}
		query+=" group by eb.examId";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}


	/**
	 * @param examIds
	 * @param newSupplementaryImpApplicationForm
	 * @throws Exception
	 */
	public void getApplicationFormsForRegular(List<Integer> examIds, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewSupplementaryImpApplicationTransaction transaction1=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		List<ExamSupplementaryImpApplicationSubjectTO> subjectList = new LinkedList<ExamSupplementaryImpApplicationSubjectTO>();
		SupplementaryAppExamTo eto=null;
		int count=0;

		for (Integer examId: examIds) {

			String query="";
			query = "from ExamDefinition e where e.id ="+examId;
			List<ExamDefinition> examList=transaction.getDataForQuery(query);
			for (ExamDefinition bo : examList) {
				newSupplementaryImpApplicationForm.setAcademicYear(String.valueOf(bo.getAcademicYear()));
				newSupplementaryImpApplicationForm.setExamId(""+bo.getId());
				newSupplementaryImpApplicationForm.setExamName(bo.getName());
				newSupplementaryImpApplicationForm.setMonth(CommonUtil.getMonthForNumber(Integer.parseInt(bo.getMonth())));
				newSupplementaryImpApplicationForm.setYear(""+bo.getYear());
			}

			//Basim did for different fine fees(Fine,Super Fine)
			query="from ExamPublishHallTicketMarksCardBO p"+
			" where p.classes.id="+newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId()+
			" and p.examId=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+" and p.publishFor='Regular Application'";
			List<ExamPublishHallTicketMarksCardBO> fineDetails=transaction.getDataForQuery(query);
			ExamPublishHallTicketMarksCardBO regFineFees=null;
			if(fineDetails!=null){
				regFineFees=fineDetails.get(0);
			}

			if(newSupplementaryImpApplicationForm.isExtended())
				if(transaction1.checkSubmitRegApp(newSupplementaryImpApplicationForm)){
					newSupplementaryImpApplicationForm.setExtended(false);
				}
			if(newSupplementaryImpApplicationForm.isExtended()){

			}

			query = "from CourseToDepartment c where c.course.id ="+newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getId();
			List<CourseToDepartment> courseToDepartmentList=transaction.getDataForQuery(query);
			String dep="";
			//Basim did for Exam Fee Calculation
			double applicationFee=0;
			double theoryFee=0;
			double practicalFee=0;
			double cvCampFee=0;
			double marksListFee=0;
			double onlineServiceChargeFee=0;
			double examFee=0;
			double totalfee=0;
			query="from RegularExamFees r where r.academicYear="+newSupplementaryImpApplicationForm.getAcademicYear()+" and r.classes.id="+newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId();
			if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection()!=null){
				int secId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection().getId();
				if(secId==6||secId==7||secId==8||secId==9||secId==10||secId==11)
					secId=2;
				query+=" and r.religionSection.id="+secId;
			}else if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough()!=null){
				int admId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough().getId();
				int secId=0;
				if(admId==2){
					secId=3;
				}else if(admId==18){
					secId=4;
				}else{
					secId=2;
				}



				query+=" and r.religionSection.id="+secId;
			}
			List<RegularExamFees> regularExamFeeList=transaction.getDataForQuery(query);
			if(!regularExamFeeList.isEmpty()){
				RegularExamFees regularExamFees=regularExamFeeList.get(0);
				if(regularExamFees.getApplicationFees()!=null)
					applicationFee=regularExamFees.getApplicationFees().doubleValue();
				if(regularExamFees.getCvCampFees()!=null)
					cvCampFee=regularExamFees.getCvCampFees().doubleValue();
				if(regularExamFees.getMarksListFees()!=null)
					marksListFee=regularExamFees.getMarksListFees().doubleValue();
				if(regularExamFees.getOnlineServiceChargeFees()!=null)
					onlineServiceChargeFee=regularExamFees.getOnlineServiceChargeFees().doubleValue();
				if(regularExamFees.getTheoryFees()!=null)
					theoryFee=regularExamFees.getTheoryFees().doubleValue();
				if(regularExamFees.getPracticalFees()!=null)
					practicalFee=regularExamFees.getPracticalFees().doubleValue();
				if(regularExamFees.getExamFees()!=null)
					examFee=regularExamFees.getExamFees().doubleValue();


			}
			for (CourseToDepartment bo : courseToDepartmentList) {
				if(courseToDepartmentList.size()!=0 && courseToDepartmentList.size()==1){
					dep=dep+bo.getDepartment().getName();
				}else if(courseToDepartmentList.size()!=0 && courseToDepartmentList.size()>1){
					if(count==0){
						dep=dep+bo.getDepartment().getName();
					}else{
						dep=dep+","+bo.getDepartment().getName();
					}

					count++;
				}

				newSupplementaryImpApplicationForm.setCourseDep(dep);

			}

			List boList=transaction1.getSubjectsListForStudent(newSupplementaryImpApplicationForm.getStudentObj(),Integer.parseInt(newSupplementaryImpApplicationForm.getAcademicYear()));
			Iterator i=boList.iterator();
			while(i.hasNext()) {
				ExamSupplementaryImpApplicationSubjectTO sto = new ExamSupplementaryImpApplicationSubjectTO();
				Object obj[]=(Object[])i.next();
				SubjectUtilBO bo=(SubjectUtilBO)obj[0];
				sto.setSubjectCode(bo.getCode());
				sto.setSubjectName(bo.getName());
				sto.setSubjectType(bo.getIsTheoryPractical());
				//Basim did for exam fee calculation
				if(bo.getIsTheoryPractical().equalsIgnoreCase("T")){
					//theoryFee+=regularExamFees.getTheoryFees().doubleValue();
				}
				else if(bo.getIsTheoryPractical().equalsIgnoreCase("P")){
					//practicalFee+=regularExamFees.getPracticalFees().doubleValue();
				}

				if(obj[1]!=null){
					sto.setSectionName(obj[1].toString());
				}else{
					sto.setSectionName("");
				}

				subjectList.add(sto);

			}
			totalfee=theoryFee+practicalFee+cvCampFee+onlineServiceChargeFee+marksListFee+applicationFee+examFee;
			newSupplementaryImpApplicationForm.setExamFees(examFee);
			Properties prop = new Properties();
			InputStream inStr = CommonUtil.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
			if(newSupplementaryImpApplicationForm.isExtended()){
				if(newSupplementaryImpApplicationForm.getIsFine()){
					newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(regFineFees.getFineAmount()));
				}
				else if(newSupplementaryImpApplicationForm.getIsSuperFine()){
					newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(regFineFees.getSuperFineAmount()));
				}
				totalfee+=newSupplementaryImpApplicationForm.getFineFees();
			}
			newSupplementaryImpApplicationForm.setTotalFees(totalfee);

			newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalfee));

			newSupplementaryImpApplicationForm.setSupSubjectList(subjectList);

		}//main for

		newSupplementaryImpApplicationForm.setExamType("Regular");

		//raghu for attenance session
		/*DisciplinaryDetailsImpl impl = new DisciplinaryDetailsImpl();
		int classId = newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId();
		List studateList=impl.getDateList(newSupplementaryImpApplicationForm.getStudentObj().getId(), classId);
		List clsdateList=impl.getDateList(0, classId);
		List sessionAttendanceList=impl.getSessionAttendanceList(newSupplementaryImpApplicationForm.getStudentObj().getId(), classId);
		float percentage=NewSupplementaryImpApplicationHelper.getInstance().getAttendancePercentage(studateList, sessionAttendanceList, clsdateList);
		 */

		StudentWiseAttendanceSummaryForm form=new StudentWiseAttendanceSummaryForm();
		form.setClassesId(newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId()+"");
		//periodw wise
		float percentage =StudentAttendanceSummaryHandler.getInstance().getStudentWiseSubjectsAttendancePercentage(newSupplementaryImpApplicationForm.getStudentObj().getId(), form, newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getCourse().getId()+"");
		form=null;
		boolean ava=checkAttendanceAvailability(newSupplementaryImpApplicationForm.getStudentObj().getId(),newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId());
		//String reqPercentage=CMSConstants.ATTENDANCE_PERCENTAGE;
		//getting instructions
		String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId(),"Classes",true,"course.program.programType.id");
		IDownloadHallTicketTransaction txn= new DownloadHallTicketTransactionImpl();
		List<ExamFooterAgreementBO> footer=txn.getFooterDetails(programTypeId,"E",newSupplementaryImpApplicationForm.getClassId());
		if(footer!=null && !footer.isEmpty()){
			ExamFooterAgreementBO obj=footer.get(0);
			if(obj.getDescription()!=null)
				newSupplementaryImpApplicationForm.setDescription(obj.getDescription());
		}else{
			newSupplementaryImpApplicationForm.setDescription(null);
		}




	}

	public boolean checkAttendanceAvailability(int studentId, int classId) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		boolean dup=false;
		dup=transaction.checkAttendanceAvailability(studentId,classId);
		return dup;	
	}
	public boolean checkCondonationAvailability(int studentId, int classId) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		boolean ava=false;
		ava=transaction.checkCondonationAvailability(studentId,classId);
		return ava;
	}

	public boolean checkDuplication(NewSupplementaryImpApplicationForm form) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		boolean dup=false;
		dup=transaction.checkDuplication(form);
		return dup;
	}

	public boolean addAppliedStudent(NewSupplementaryImpApplicationForm form) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		boolean add=false;
		ExamRegularApplication er=new ExamRegularApplication();

		Classes c=new Classes();
		c.setId(form.getStudentObj().getClassSchemewise().getClasses().getId());
		System.out.println(form.getStudentObj().getClassSchemewise().getClasses().getId());
		er.setClasses(c);

		ExamDefinitionBO e=new ExamDefinitionBO();
		e.setId(Integer.parseInt(form.getExamId()));
		System.out.println(form.getExamId());
		er.setExam(e);

		Student s=new Student();
		s.setId(form.getStudentObj().getId());
		System.out.println(form.getStudentObj().getId());
		er.setStudent(s);

		er.setIsApplied(true);
		er.setCreatedBy(form.getUserId());
		er.setCreatedDate(new Date());
		er.setModifiedBy(form.getUserId());
		er.setLastModifiedDate(new Date());
		if(form.getRegNo()!=null){
			er.setChallanNo("RG"+form.getRegNo()+form.getExamId());
			form.setChalanNo(er.getChallanNo());
			System.out.println(er.getChallanNo());
		}
		add=transaction.addAppliedStudent(er);

		return add;
	}
	public List<Integer> getPreviousClasses(int studentId) throws Exception {

		String query="select s.classes.id from StudentPreviousClassHistory s where s.student.id="+studentId ; 
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}

	public boolean extendedDateForRegular(int classId,NewSupplementaryImpApplicationForm suppForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getExtendedDateForRegular(classId, suppForm);
	}
	
	//Temporary Purpose
	public Double getSupplementaryFeeForStudent(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewSupplementaryImpApplicationTransaction transaction1=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		//List<SupplementaryAppExamTo> examList=new ArrayList<SupplementaryAppExamTo>();
		SupplementaryAppExamTo eto=null;
		//	int count=0;

		boolean isTotal=false;
		boolean isChecked=false;
		boolean isScSupply=false;
		List<Integer> classNotRequiredList=new ArrayList<Integer>();


		if(newSupplementaryImpApplicationForm.getExamId()!=null){
			eto=new SupplementaryAppExamTo();
			eto.setExamId(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()));








			String query="SELECT  *  FROM    EXAM_supplementary_improvement_application EXAM_supplementary_improvement_application" +
			" INNER JOIN EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise " +
			" ON (EXAM_supplementary_improvement_application.subject_id =  EXAM_sub_definition_coursewise.subject_id)" +
			" where EXAM_supplementary_improvement_application.student_id="+newSupplementaryImpApplicationForm.getStudentObj().getId()+
			" and EXAM_supplementary_improvement_application.exam_id=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
			" and (EXAM_supplementary_improvement_application.is_appeared_theory=1  or EXAM_supplementary_improvement_application.is_appeared_practical=1 ) " +
			//" and (((EXAM_supplementary_improvement_application.is_failed_practical=1 or EXAM_supplementary_improvement_application.is_failed_theory=1 or EXAM_supplementary_improvement_application.cia_is_failed_practical=1 or EXAM_supplementary_improvement_application.cia_is_failed_theory=1)" +
			//" and EXAM_supplementary_improvement_application.is_supplementary=1) or EXAM_supplementary_improvement_application.is_improvement=1) "+
			" group by EXAM_supplementary_improvement_application.subject_id order by EXAM_sub_definition_coursewise.subject_order";


			List<StudentSupplementaryImprovementApplication> boList= transaction.getDataForSQLQuery(query);

			List<SupplementaryApplicationClassTo> classList=new ArrayList<SupplementaryApplicationClassTo>();
			SupplementaryApplicationClassTo to=null;
			boolean isSupplementary=false;
			//Basim did for Exam Fee Calculation
			double applicationFee=0;
			double paperFee=0;
			double RegistrationFee=0;
			double cvCampFee=0;
			double marksListFee=0;
			double totalfee=0;
			ClassSchemewise cl=null;
			List<Integer> selffinanceCourseId=CMSConstants.SELF_FINANCE_COURSE;
			int secId=0;
			for (StudentSupplementaryImprovementApplication bo : boList) {
				query="from ClassSchemewise c where classes.id="+bo.getClasses().getId();
				List<ClassSchemewise> classScheme=transaction.getDataForQuery(query);
				if(!classScheme.isEmpty()){
					cl=classScheme.get(0);

				}
				if (!classNotRequiredList.contains(bo.getClasses().getId())) {

					query="from SupplementaryFees r where r.academicYear="+cl.getCurriculumSchemeDuration().getAcademicYear()+" and r.classes.id="+bo.getClasses().getId();
					if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection()!=null){
						secId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection().getId();
						if(secId==6||secId==7||secId==8||secId==9||secId==10||secId==11)
							secId=2;
						query+=" and r.religionSection.id="+secId;
					}else if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough()!=null){
						int admId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough().getId();
						secId=0;
						if(admId==2){
							secId=3;
						}else if(admId==18){
							secId=4;
						}else{
							secId=2;
						}
						query+=" and r.religionSection.id="+secId;
					}
					SupplementaryFees supplementaryFees=null;
					List<SupplementaryFees> regularExamFeeList=transaction.getDataForQuery(query);



					if(regularExamFeeList==null || regularExamFeeList.isEmpty() )
						newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
					if(!regularExamFeeList.isEmpty()){
						supplementaryFees=regularExamFeeList.get(0);
						if(supplementaryFees.getApplicationFees()!=null){
							applicationFee=supplementaryFees.getApplicationFees().doubleValue();
							newSupplementaryImpApplicationForm.setApplicationFees(applicationFee);
						}
						if(supplementaryFees.getMarksListFees()!=null){
							marksListFee=supplementaryFees.getMarksListFees().doubleValue();
							newSupplementaryImpApplicationForm.setMarksListFees(marksListFee);
						}

						if(supplementaryFees.getRegistrationFees()!=null){
							RegistrationFee=supplementaryFees.getRegistrationFees().doubleValue();
							newSupplementaryImpApplicationForm.setRegistrationFees(RegistrationFee);
						}
						if(supplementaryFees.getPaperFees()!=null){
							double PaperFee=supplementaryFees.getPaperFees().doubleValue();
							newSupplementaryImpApplicationForm.setPaperFees(PaperFee);
						}
						if(supplementaryFees.getCvCampFees()!=null){
							double cvFee=supplementaryFees.getCvCampFees().doubleValue();
							newSupplementaryImpApplicationForm.setCvCampFees(cvFee);
						}
					}
					to = new SupplementaryApplicationClassTo();
					to.setClassId(bo.getClasses().getId());
					to.setClassName(bo.getClasses().getName());
					List<ExamSupplementaryImpApplicationSubjectTO> subjectList = null;
					if (classList.contains(to)) {
						to = classList.remove(classList.indexOf(to));
						subjectList = to.getToList();
						if (bo.getIsSupplementary() != null) {
							if (to.isSupplementary() != bo.getIsSupplementary()){
								//seniors wrote this condition to check supply student have improvement, if he has he will not get receipt, but raghu put cooment
								//classNotRequiredList.add(bo.getClasses() .getId());
							}
						}
						if (bo.getIsImprovement() != null) {
							if (to.isImprovement() != bo.getIsImprovement()){
								//seniors wrote this condition to check supply student have improvement, if he has he will not get receipt, but raghu put cooment
								//classNotRequiredList.add(bo.getClasses() .getId());
							}
						}
					} else {
						/*subjectList = new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
						to.setCourseName(bo.getClasses().getCourse().getName());
						to.setRegisterNo(bo.getStudent().getRegisterNo());
						to.setRollNo(bo.getStudent().getRollNo());
						to.setSemNo(bo.getClasses().getTermNumber().toString());
						to.setStudentName(bo.getStudent().getAdmAppln() .getPersonalData().getFirstName());
						to.setClassName(bo.getClasses().getName());*/
						/*if (bo.getIsImprovement() != null)
							to.setImprovement(bo.getIsImprovement());
						if (bo.getIsSupplementary() != null)
							to.setSupplementary(bo.getIsSupplementary());
						if (count == 0) {
							newSupplementaryImpApplicationForm.setCourseName(bo .getClasses().getCourse().getName());
							newSupplementaryImpApplicationForm.setRegisterNo(bo .getStudent().getRegisterNo());
							newSupplementaryImpApplicationForm .setNameOfStudent(bo.getStudent() .getAdmAppln().getPersonalData() .getFirstName());
							count++;
						}*/
						newSupplementaryImpApplicationForm.setProgramId(String .valueOf(bo.getStudent().getAdmAppln() .getCourseBySelectedCourseId() .getProgram().getId()));
					}
					if (!classNotRequiredList.contains(bo.getClasses().getId())) {

						if (bo.getIsAppearedPractical() != null && bo.getIsAppearedPractical() || bo.getIsAppearedTheory()!=null && bo.getIsAppearedTheory()){
							if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && (secId==3 || secId==4)&& bo.getChance()>1){
								if(supplementaryFees!=null && supplementaryFees.getPaperFees()!=null)
									paperFee+=supplementaryFees.getPaperFees().doubleValue();
								if(supplementaryFees!=null && supplementaryFees.getCvCampFees()!=null)
									cvCampFee+=supplementaryFees.getCvCampFees().doubleValue();
								isTotal=true;
							}
							else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && (secId!=3 && secId!=4)){
								if(supplementaryFees!=null && supplementaryFees.getPaperFees()!=null)
									paperFee+=supplementaryFees.getPaperFees().doubleValue();
								if(supplementaryFees!=null && supplementaryFees.getCvCampFees()!=null)
									cvCampFee+=supplementaryFees.getCvCampFees().doubleValue();
								isTotal=true;
							}
							//Basim did this condition for self financing
							else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && selffinanceCourseId.contains(bo.getClasses().getCourse().getId())){
								if(supplementaryFees!=null && supplementaryFees.getPaperFees()!=null)
									paperFee+=supplementaryFees.getPaperFees().doubleValue();
								if(supplementaryFees!=null && supplementaryFees.getCvCampFees()!=null)
									cvCampFee+=supplementaryFees.getCvCampFees().doubleValue();
								isTotal=true;
							}
							else if(bo.getIsImprovement()!=null && bo.getIsImprovement()==true){
								if(supplementaryFees!=null && supplementaryFees.getPaperFees()!=null)
									paperFee+=supplementaryFees.getPaperFees().doubleValue();
								if(supplementaryFees!=null && supplementaryFees.getCvCampFees()!=null)
									cvCampFee+=supplementaryFees.getCvCampFees().doubleValue();
								isTotal=true;

							}
							//Basim did for SC/ST conditions
							else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && secId==3 || secId==4 && bo.getChance()<=1){
								isScSupply=true;
							}

							isChecked=true;
						}
						//raghu new 













						newSupplementaryImpApplicationForm.setPrevClassId(bo.getClasses().getId()+"");


						classList.add(to);
					}

				}
			}
			//Basim did for different fine fees(Fine,Super Fine)
			query="from PublishSupplementaryImpApplication p"+
			" where p.classCode.id="+newSupplementaryImpApplicationForm.getPrevClassId()+
			" and p.exam.id=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId());
			List<PublishSupplementaryImpApplication> fineDetails=transaction.getDataForQuery(query);
			PublishSupplementaryImpApplication supplFees=null;
			if(fineDetails!=null){
				supplFees=fineDetails.get(0);
			}
			if(isTotal==false && isChecked==true){
				cvCampFee=0;
				paperFee=0;
				applicationFee=0;
				marksListFee=0;
				RegistrationFee=CMSConstants.SC_REGULAR_SUPPLY_FEE;
			}
			if(isTotal==true && isChecked==true && isScSupply==true){
				double regForSC=CMSConstants.SC_REGULAR_SUPPLY_FEE;
				RegistrationFee+=regForSC;
			}
			totalfee=cvCampFee+RegistrationFee+paperFee+applicationFee+marksListFee;
			if(transaction1.checkSubmitSuppApp(newSupplementaryImpApplicationForm))
			{
				newSupplementaryImpApplicationForm.setExtended(false);
			}
			if(newSupplementaryImpApplicationForm.isExtended())
			{
				double fine=0.0;
				if(newSupplementaryImpApplicationForm.getIsFine()){
					fine=Double.parseDouble(supplFees.getFineAmount());
					newSupplementaryImpApplicationForm.setFineFees(fine);
				}
				else if(newSupplementaryImpApplicationForm.getIsSuperFine()){
					fine=Double.parseDouble(supplFees.getSuperFineAmount());
					newSupplementaryImpApplicationForm.setFineFees(fine);

				}

				totalfee+=fine;
			}
			else
			{
				newSupplementaryImpApplicationForm.setFineFees(0);
			}
			//newSupplementaryImpApplicationForm.setRegistrationFees(RegistrationFee);
			newSupplementaryImpApplicationForm.setTotalFees(totalfee);
			//newSupplementaryImpApplicationForm.setTotalCvCampFees(cvCampFee);
			//newSupplementaryImpApplicationForm.setTotalPaperFees(paperFee);
			//newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalfee));
			/*if(isTotal==false && isChecked==true){
				newSupplementaryImpApplicationForm.setPaperFees(0);
				newSupplementaryImpApplicationForm.setCvCampFees(0);
				//newSupplementaryImpApplicationForm.setRegistrationFees(0);
				newSupplementaryImpApplicationForm.setApplicationFees(0);
				newSupplementaryImpApplicationForm.setMarksListFees(0);
				newSupplementaryImpApplicationForm.setTotalCvCampFees(0);
				//newSupplementaryImpApplicationForm.setTotalFees(0);
				newSupplementaryImpApplicationForm.setTotalPaperFees(0);
				//newSupplementaryImpApplicationForm.setTotalFeesInWords("Zero");
			}*/
			newSupplementaryImpApplicationForm.setIsSupplPaper(isSupplementary);
			//eto.setExamList(classList);
			//examList.add(eto);
		}

		return newSupplementaryImpApplicationForm.getTotalFees();
	}

	public Double getRegularFeeForStudent( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewSupplementaryImpApplicationTransaction transaction1=NewSupplementaryImpApplicationTransactionImpl.getInstance();


		if(newSupplementaryImpApplicationForm.getExamId()!=null) {

			String query="";


			//Basim did for different fine fees(Fine,Super Fine)
			query="from ExamPublishHallTicketMarksCardBO p"+
			" where p.classes.id="+newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId()+
			" and p.examId=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+" and p.publishFor='Regular Application'";
			List<ExamPublishHallTicketMarksCardBO> fineDetails=transaction.getDataForQuery(query);
			ExamPublishHallTicketMarksCardBO regFineFees=null;
			if(fineDetails!=null && !fineDetails.isEmpty()){
				regFineFees=fineDetails.get(0);
			}
			newSupplementaryImpApplicationForm.setStudentId(newSupplementaryImpApplicationForm.getStudentObj().getId());

			if(transaction1.checkSubmitRegApp(newSupplementaryImpApplicationForm)){
				newSupplementaryImpApplicationForm.setExtended(false);
			}



			//Basim did for Exam Fee Calculation
			double applicationFee=0;
			double theoryFee=0;
			double practicalFee=0;
			double cvCampFee=0;
			double marksListFee=0;
			double onlineServiceChargeFee=0;
			double examFee=0;
			double totalfee=0;
			query="from RegularExamFees r where r.academicYear="+newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()+" and r.classes.id="+newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId();
			if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection()!=null){
				int secId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection().getId();
				if(secId==6||secId==7||secId==8||secId==9||secId==10||secId==11)
					secId=2;
				query+=" and r.religionSection.id="+secId;
			}else if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough()!=null){
				int admId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough().getId();
				int secId=0;
				if(admId==2){
					secId=3;
				}else if(admId==18){
					secId=4;
				}else{
					secId=2;
				}



				query+=" and r.religionSection.id="+secId;
			}
			List<RegularExamFees> regularExamFeeList=transaction.getDataForQuery(query);
			if(!regularExamFeeList.isEmpty()){
				RegularExamFees regularExamFees=regularExamFeeList.get(0);
				if(regularExamFees.getApplicationFees()!=null)
					applicationFee=regularExamFees.getApplicationFees().doubleValue();
				if(regularExamFees.getCvCampFees()!=null)
					cvCampFee=regularExamFees.getCvCampFees().doubleValue();
				if(regularExamFees.getMarksListFees()!=null)
					marksListFee=regularExamFees.getMarksListFees().doubleValue();
				if(regularExamFees.getOnlineServiceChargeFees()!=null)
					onlineServiceChargeFee=regularExamFees.getOnlineServiceChargeFees().doubleValue();
				if(regularExamFees.getTheoryFees()!=null)
					theoryFee=regularExamFees.getTheoryFees().doubleValue();
				if(regularExamFees.getPracticalFees()!=null)
					practicalFee=regularExamFees.getPracticalFees().doubleValue();
				if(regularExamFees.getExamFees()!=null)
					examFee=regularExamFees.getExamFees().doubleValue();


			}

			totalfee=theoryFee+practicalFee+cvCampFee+onlineServiceChargeFee+marksListFee+applicationFee+examFee;
			newSupplementaryImpApplicationForm.setExamFees(examFee);
			Properties prop = new Properties();
			InputStream inStr = CommonUtil.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
			if(newSupplementaryImpApplicationForm.isExtended()){
				if(newSupplementaryImpApplicationForm.getIsFine()){
					newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(regFineFees.getFineAmount()));
				}
				else if(newSupplementaryImpApplicationForm.getIsSuperFine()){
					newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(regFineFees.getSuperFineAmount()));
				}
				totalfee+=newSupplementaryImpApplicationForm.getFineFees();
			}
			newSupplementaryImpApplicationForm.setTotalFees(totalfee);

			newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalfee));



		}//main for




		return newSupplementaryImpApplicationForm.getTotalFees();


	}
	// for revaluation
	public boolean extendedDateForRevaluation(int classId,NewSupplementaryImpApplicationForm suppForm) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getExtendedDateForRevaluation(classId, suppForm);
	}
	public List<Integer> checkRevaluationAppAvailable(int classId, boolean isExtended,boolean isSupplementary) throws Exception {
		String query="select eb.examId from ExamPublishHallTicketMarksCardBO eb " +
				"where eb.isActive=1 and eb.publishFor='Revaluation/Scrutiny' and " +
				" eb.classes.id=" +classId ;
		if(!isSupplementary)
			query+=" and eb.examDefinitionBO.examTypeUtilBO.name='Regular' " ;
		else
			query+=" and eb.examDefinitionBO.examTypeUtilBO.name='Supplementary' " ;
		
		query+=" and (('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between eb.downloadStartDate and eb.downloadEndDate)  "; 
		
		if(isExtended){
			query+=" or ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between eb.downloadEndDate and eb.extendedDate))  "; 
		}else{
			query+=")";
		}
		query+=" group by eb.examId"; 
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}
	
	// for revaluation added by Ashwini
	
	public void getApplicationFormsForRevaluation(List<Integer> examIds, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<SupplementaryAppExamTo> examList=new ArrayList<SupplementaryAppExamTo>();
		SupplementaryAppExamTo eto=null;
		int count=0;
		boolean buttonDisplay=true;
		List<Integer> classNotRequiredList=new ArrayList<Integer>();
		INewSupplementaryImpApplicationTransaction transaction1=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		newSupplementaryImpApplicationForm.setChallanButton(false);

		for (Integer examId: examIds) {
			eto=new SupplementaryAppExamTo();
			eto.setExamId(examId);
			eto.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId,"ExamDefinition",true,"name"));
			newSupplementaryImpApplicationForm.setExamid(examId);
			//rgahu write newly like regular app
			String query1 = "from ExamDefinition e where e.id ="+examId;
			List<ExamDefinition> examList1=transaction.getDataForQuery(query1);
			for (ExamDefinition bo : examList1) {
				newSupplementaryImpApplicationForm.setAcademicYear(String.valueOf(bo.getAcademicYear()));
				newSupplementaryImpApplicationForm.setExamId(""+bo.getId());
				newSupplementaryImpApplicationForm.setExamName(bo.getName());
				newSupplementaryImpApplicationForm.setMonth(CommonUtil.getMonthForNumber(Integer.parseInt(bo.getMonth())));
				newSupplementaryImpApplicationForm.setYear(""+bo.getYear());
			}

			query1="from ExamPublishHallTicketMarksCardBO p"+
			" where p.classes.id="+newSupplementaryImpApplicationForm.getRevclassid()+
			" and p.examId=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+" and p.publishFor='Revaluation/Scrutiny'";
			List<ExamPublishHallTicketMarksCardBO> fineDetails=transaction.getDataForQuery(query1);
			ExamPublishHallTicketMarksCardBO regFineFees=null;
			if(fineDetails!=null && !fineDetails.isEmpty()){
				regFineFees=fineDetails.get(0);
			}

			List boList1 = new ArrayList();
			if(!newSupplementaryImpApplicationForm.isSupplementary())
				boList1=transaction1.getSubjectsListForRevaluation(newSupplementaryImpApplicationForm.getStudentObj(),examId,newSupplementaryImpApplicationForm.getRevclassid());
			else
				boList1 =transaction1.getSupplSubjectsListForRevaluation(newSupplementaryImpApplicationForm.getStudentObj(),examId,newSupplementaryImpApplicationForm.getRevclassid());
			
			List<SupplementaryApplicationClassTo> classList=new ArrayList<SupplementaryApplicationClassTo>();
			SupplementaryApplicationClassTo to=null;
            if(boList1.size()==0)
            	newSupplementaryImpApplicationForm.setRevAppAvailable(false);
			Iterator i=boList1.iterator();
			while(i.hasNext()) {

				Object obj[]=(Object[])i.next();
				to = new SupplementaryApplicationClassTo();
				to.setClassId(newSupplementaryImpApplicationForm.getRevclassid());

				List<ExamSupplementaryImpApplicationSubjectTO> subjectList = null;
				if (classList.contains(to)) {
					to = classList.remove(classList.indexOf(to));
					subjectList = to.getToList();
					//classNotRequiredList.add(newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses() .getId());
				}else{
					subjectList = new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
					//to.setClassName(newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getName());
					to.setCourseName(newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getCourse().getName());
					if(obj[26]!=null)
					newSupplementaryImpApplicationForm.setPrevSemNo(Integer.parseInt(obj[26].toString()));
					to.setRegisterNo(newSupplementaryImpApplicationForm.getStudentObj().getRegisterNo());
					to.setRollNo(newSupplementaryImpApplicationForm.getStudentObj().getRollNo());
					//to.setSemNo(newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getTermNumber().toString());
					to.setStudentName(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln() .getPersonalData().getFirstName());
					if (count == 0) {
						newSupplementaryImpApplicationForm.setCourseName(newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise() .getClasses().getCourse().getName());
						newSupplementaryImpApplicationForm.setRegisterNo(newSupplementaryImpApplicationForm.getStudentObj().getRegisterNo());
						newSupplementaryImpApplicationForm .setNameOfStudent(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData() .getFirstName());
						count++;
					}
					newSupplementaryImpApplicationForm.setProgramId(String .valueOf(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln() .getCourseBySelectedCourseId() .getProgram().getId()));

				}

				if (!classNotRequiredList.contains(newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId())) {
					ExamSupplementaryImpApplicationSubjectTO sto = new ExamSupplementaryImpApplicationSubjectTO();
					//sto.setId(bo.getId());
					if(obj[10]!=null)
						sto.setSubjectId(Integer.parseInt(obj[10].toString()));
					if(obj[11]!=null)
						sto.setSubjectCode(obj[11].toString());
					if(obj[12]!=null)
						sto.setSubjectName(obj[12].toString());
					if(obj[13]!=null){
						sto.setSubjectType(obj[13].toString());
						if(obj[13].toString().equalsIgnoreCase("t") || obj[13].toString().equalsIgnoreCase("B"))
							sto.setTheory(true);
						if(obj[13].toString().equalsIgnoreCase("p") || obj[13].toString().equalsIgnoreCase("B"))
							sto.setPractical(true);
					}
					//if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getName().equalsIgnoreCase("UG")){
					if(obj[14]!=null)
						sto.setSectionName(obj[14].toString());
					sto.setCommonChecked(false);
					sto.setTempChecked(false);
					sto.setControlDisable(true);
					sto.setClassId(newSupplementaryImpApplicationForm.getRevclassid());

					if(obj[18]!=null || obj[19]!=null){
						boolean isAbsent=false;
						float theroy=0;
						float practial=0;
						if(obj[18]!=null && CommonUtil.isValidDecimal(obj[18].toString()))
							theroy=Float.parseFloat(obj[18].toString());
						else{
							isAbsent=true;
							sto.setMarks("AB");
							sto.setDisableCheckBox(true);
						}
						if(obj[19]!=null && CommonUtil.isValidDecimal(obj[19].toString()))	
							practial=Float.parseFloat(obj[19].toString());
						else{
							isAbsent=true;
							sto.setMarks("AB");
							sto.setDisableCheckBox(true);
						}
                        if(!isAbsent)
						sto.setMarks(theroy+practial+"");}

					subjectList.add(sto);
					to.setToList(subjectList);
					classList.add(to);
				}		

			}//close for loop subject list while

			if(newSupplementaryImpApplicationForm.getCourseId()!=null){

				String query="from RevaluationExamFees r where r.academicYear = "+newSupplementaryImpApplicationForm.getAcademicYear()+" and r.course.id="+newSupplementaryImpApplicationForm.getCourseId();
				RevaluationExamFees bo=(RevaluationExamFees)PropertyUtil.getDataForUniqueObject(query);
				if(bo==null){
					newSupplementaryImpApplicationForm.setRevAppAvailable(false);
				}else{

					if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){

						if(bo.getRevaluationFees()!=null){
							newSupplementaryImpApplicationForm.setRevaluationFees(bo.getRevaluationFees().doubleValue());

						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}
						
						if(bo.getApplicationFees()!=null){
							newSupplementaryImpApplicationForm.setApplicationFees(bo.getApplicationFees().doubleValue());


						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}
					}	

					else if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){

						if(bo.getScrutinyFees()!=null){
							newSupplementaryImpApplicationForm.setScrutinyFees(bo.getScrutinyFees().doubleValue());


						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}
						
						if(bo.getApplicationFees()!=null){
							newSupplementaryImpApplicationForm.setApplicationFees(bo.getApplicationFees().doubleValue());


						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}
					}
					
					else{
						if(bo.getChallengeValuationFees()!=null){
							newSupplementaryImpApplicationForm.setChallengeRevaluationFees(bo.getChallengeValuationFees().doubleValue());


						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}
						
						if(bo.getApplicationFees()!=null){
							newSupplementaryImpApplicationForm.setApplicationFees(bo.getApplicationFees().doubleValue());


						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}
					}

					double totalTheoryFees = 0;
					if(newSupplementaryImpApplicationForm.isExtended()){
						if(newSupplementaryImpApplicationForm.getIsFine()){
							newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(regFineFees.getFineAmount()));
						}
						else if(newSupplementaryImpApplicationForm.getIsSuperFine()){
							newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(regFineFees.getSuperFineAmount()));
						}						
					}

					if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){
						totalTheoryFees = newSupplementaryImpApplicationForm.getFineFees() +
						newSupplementaryImpApplicationForm.getRevaluationFees() +
						newSupplementaryImpApplicationForm.getApplicationFees();
					}
					else if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
						totalTheoryFees = newSupplementaryImpApplicationForm.getFineFees() +
						newSupplementaryImpApplicationForm.getScrutinyFees() +
						newSupplementaryImpApplicationForm.getApplicationFees();
						
					}
					else{
						totalTheoryFees = newSupplementaryImpApplicationForm.getFineFees() +
						newSupplementaryImpApplicationForm.getChallengeRevaluationFees() +
						newSupplementaryImpApplicationForm.getApplicationFees();
					}
					newSupplementaryImpApplicationForm.setApplicationAmount(String.valueOf(totalTheoryFees));
					newSupplementaryImpApplicationForm.setTotalFees(totalTheoryFees-newSupplementaryImpApplicationForm.getApplicationFees());
					

				}
			
			}

			eto.setExamList(classList);
			examList.add(eto);
		}//close main for loop




		newSupplementaryImpApplicationForm.setDisplayButton(buttonDisplay);
		newSupplementaryImpApplicationForm.setMainList(examList);
	}




	/**
	 * @param examIds
	 * @param newSupplementaryImpApplicationForm
	 * @throws Exception
	 */
	public void getApplicationFormsForRevaluationChooseType(List<Integer> examIds, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<SupplementaryAppExamTo> examList=new ArrayList<SupplementaryAppExamTo>();
		SupplementaryAppExamTo eto=null;
		int count=0;
		boolean buttonDisplay=false;
		boolean displayButton=false;
		List<Integer> classNotRequiredList=new ArrayList<Integer>();
		INewSupplementaryImpApplicationTransaction transaction1=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		newSupplementaryImpApplicationForm.setChallanButton(false);

		for (Integer examId: examIds) {
			eto=new SupplementaryAppExamTo();
			eto.setExamId(examId);
			eto.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(examId,"ExamDefinition",true,"name"));
			newSupplementaryImpApplicationForm.setExamid(examId);
			//rgahu write newly like regular app
			String query1 = "from ExamDefinition e where e.id ="+examId;
			List<ExamDefinition> examList1=transaction.getDataForQuery(query1);
			for (ExamDefinition bo : examList1) {
				newSupplementaryImpApplicationForm.setAcademicYear(String.valueOf(bo.getAcademicYear()));
				newSupplementaryImpApplicationForm.setExamId(""+bo.getId());
				newSupplementaryImpApplicationForm.setExamName(bo.getName());
				newSupplementaryImpApplicationForm.setMonth(CommonUtil.getMonthForNumber(Integer.parseInt(bo.getMonth())));
				newSupplementaryImpApplicationForm.setYear(""+bo.getYear());
			}

			query1="from ExamPublishHallTicketMarksCardBO p"+
			" where p.classes.id="+newSupplementaryImpApplicationForm.getRevclassid()+
			" and p.examId=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+" and p.publishFor='Revaluation/Scrutiny'";
			List<ExamPublishHallTicketMarksCardBO> fineDetails=transaction.getDataForQuery(query1);
			ExamPublishHallTicketMarksCardBO regFineFees=null;
			if(fineDetails!=null && !fineDetails.isEmpty()){
				regFineFees=fineDetails.get(0);
			}
			
			// checking records exist or what
			ExamRevaluationApp examRevaluationApp = NewSupplementaryImpApplicationHandler.getInstance().checkDuplicationForRevaluation(newSupplementaryImpApplicationForm);
			if(examRevaluationApp!=null){
				newSupplementaryImpApplicationForm.setDisplayButton(false);
				
				if(examRevaluationApp.getIsChallanVerified()){
					newSupplementaryImpApplicationForm.setAppliedAlready(true);

					newSupplementaryImpApplicationForm.setChallanButton(false);
					newSupplementaryImpApplicationForm.setPrintSupplementary(true);
					newSupplementaryImpApplicationForm.setApplicationAmount(String.valueOf(examRevaluationApp.getAmount()));

				}
				else{
				// this code is meant for students applying through admin login & laterAppliedFees will be having value when student applies 
				//through admin login and got challan verified also later he wants some other subjects to apply
					if(examRevaluationApp.getLaterAppliedFees()!=null && Double.parseDouble(examRevaluationApp.getLaterAppliedFees())>0){
						newSupplementaryImpApplicationForm.setApplicationAmount(String.valueOf(examRevaluationApp.getLaterAppliedFees()));
						int amount = (int) Math.round(Double.parseDouble(newSupplementaryImpApplicationForm.getApplicationAmount()));
						newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord(amount));
						newSupplementaryImpApplicationForm.setTotalFees(Double.parseDouble(newSupplementaryImpApplicationForm.getApplicationAmount()));
						newSupplementaryImpApplicationForm.setAppliedThroughAdmin(true);
					}
					else
						newSupplementaryImpApplicationForm.setApplicationAmount(String.valueOf(examRevaluationApp.getAmount()));

					newSupplementaryImpApplicationForm.setChallanButton(true);
				}
				newSupplementaryImpApplicationForm.setChallanNo(examRevaluationApp.getChallanNo());

			}

			String query="from ExamRevaluationApplicationNew er where er.classes.id="+newSupplementaryImpApplicationForm.getRevclassid()+
			" and er.exam.id="+examId+" and er.student.id= "+newSupplementaryImpApplicationForm.getStudentId();

			if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){
				query=query+"  and er.isRevaluation=1" ;
			}
			if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
				query=query+"  and er.isScrutiny=1 " ;
			}
			if(newSupplementaryImpApplicationForm.getProgramTypeName().equalsIgnoreCase("PG")){
				query=query+" and er.isChallengeValuation=1 " ;
			}

			List<ExamRevaluationApplicationNew> boList= transaction.getDataForQuery(query);
			List<SupplementaryApplicationClassTo> classList=new ArrayList<SupplementaryApplicationClassTo>();
			SupplementaryApplicationClassTo to=null;

			int revaluationcount=0;
			int scrutinycount=0;
			int challengeValuationCount=0;

			for (ExamRevaluationApplicationNew bo : boList) {
				if (!classNotRequiredList.contains(bo.getClasses().getId())) {
					to = new SupplementaryApplicationClassTo();
					to.setClassId(bo.getClasses().getId());
					to.setClassName(bo.getClasses().getName());
					List<ExamSupplementaryImpApplicationSubjectTO> subjectList = null;
					if (classList.contains(to)) {
						to = classList.remove(classList.indexOf(to));
						subjectList = to.getToList();

						if (to.isRevaluation() != bo.getIsRevaluation()){
							//seniors wrote this condition to check supply student have improvement, if he has he will not get receipt, but raghu put cooment
							//classNotRequiredList.add(bo.getClasses() .getId());
						}else if (to.isScrutiny() != bo.getIsScrutiny()){
							//seniors wrote this condition to check supply student have improvement, if he has he will not get receipt, but raghu put cooment
							//classNotRequiredList.add(bo.getClasses() .getId());
						}

					} else {
						subjectList = new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
						to.setCourseName(bo.getClasses().getCourse().getName());
						to.setRegisterNo(bo.getStudent().getRegisterNo());
						to.setRollNo(bo.getStudent().getRollNo());
						to.setSemNo(bo.getClasses().getTermNumber().toString());
						to.setStudentName(bo.getStudent().getAdmAppln() .getPersonalData().getFirstName());
						to.setClassName(bo.getClasses().getName());

						if (bo.getIsRevaluation())
							to.setRevaluation(true);
						if (bo.getIsScrutiny())
							to.setScrutiny(true);
						if (bo.getIsChallengeValuation())
                             to.setIsChallengeValuation(true);
				

						if (count == 0) {
							newSupplementaryImpApplicationForm.setCourseName(bo .getClasses().getCourse().getName());
							newSupplementaryImpApplicationForm.setRegisterNo(bo .getStudent().getRegisterNo());
							newSupplementaryImpApplicationForm .setNameOfStudent(bo.getStudent() .getAdmAppln().getPersonalData() .getFirstName());
							count++;
						}
						newSupplementaryImpApplicationForm.setProgramId(String .valueOf(bo.getStudent().getAdmAppln() .getCourseBySelectedCourseId() .getProgram().getId()));
					}
					if (!classNotRequiredList.contains(bo.getClasses().getId())) {
						ExamSupplementaryImpApplicationSubjectTO sto = new ExamSupplementaryImpApplicationSubjectTO();
						sto.setId(bo.getId());
						sto.setSubjectId(bo.getSubject().getId());
						sto.setSubjectCode(bo.getSubject().getCode());
						sto.setSubjectName(bo.getSubject().getName());
						//raghu new 
						sto.setSubjectType(bo.getSubject().getIsTheoryPractical());

						if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getName().equalsIgnoreCase("UG")){

							String subQuery =   "select esb.examSubjectSectionMasterBO.name from ExamSubDefinitionCourseWiseBO esb where   esb.subjectUtilBO.id = "+bo.getSubject().getId();
							List sList= transaction.getDataForQuery(subQuery);
							for (Object s : sList) {
								sto.setSectionName(s.toString());
							}

						}

						if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("t") || bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B"))
							sto.setTheory(true);
						if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("p") || bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B"))
							sto.setPractical(true);

						if (bo.getIsRevaluation()){
							sto.setRevaluation(true);
						}else if (bo.getIsScrutiny()){
							sto.setScrutiny(true);
						}else if (bo.getIsChallengeValuation()){
							sto.setIsChallengeRevaluation(true);
						}


						if (bo.getIsApplied()){
							sto.setIsApplied(true);
							sto.setCommonChecked(true);
							sto.setTempChecked(true);

						}

						boolean controlDisable = false;

						if ((bo.getIsRevaluation() == true) || (bo.getIsScrutiny() == true || bo.getIsChallengeValuation() == true )) {
							controlDisable = true;
						}

						sto.setControlDisable(controlDisable);
						sto.setClassId(bo.getClasses().getId());
						sto.setSchemeNo(bo.getClasses().getTermNumber());
						sto.setMarks(bo.getMarks());

						subjectList.add(sto);
						to.setToList(subjectList);

						if(to.isRevaluation())
							if (bo.getIsApplied() != bo.getIsRevaluation()){
								buttonDisplay = true;
								//newSupplementaryImpApplicationForm.setChallanButton(true);

							}
						if(to.isScrutiny())
							if (bo.getIsApplied() != bo.getIsScrutiny()){
								buttonDisplay = true;
								//newSupplementaryImpApplicationForm.setChallanButton(true);

							}
						if(to.getIsChallengeValuation())
							if (bo.getIsApplied() != bo.getIsChallengeValuation()){
								buttonDisplay = true;
								//newSupplementaryImpApplicationForm.setChallanButton(true);

							}


						if(to.isRevaluation())
							if (bo.getIsApplied() && bo.getIsRevaluation()){
								displayButton =false;
								//newSupplementaryImpApplicationForm.setChallanButton(true);
								revaluationcount++;
							}

						if(to.isScrutiny())
							if (bo.getIsApplied() && bo.getIsScrutiny()){
								displayButton =false;
								//newSupplementaryImpApplicationForm.setChallanButton(true);
								scrutinycount++;
							}

						if(to.getIsChallengeValuation())
							if (bo.getIsApplied() && bo.getIsChallengeValuation()){
								displayButton =false;
								//newSupplementaryImpApplicationForm.setChallanButton(true);
								challengeValuationCount++;
							}

						classList.add(to);
					}
				}
			}//close for loop subject list



			double toralamount=0;
			if(newSupplementaryImpApplicationForm.getCourseId()!=null){

				query="from RevaluationExamFees r where r.academicYear = "+newSupplementaryImpApplicationForm.getAcademicYear()+" and r.course.id="+newSupplementaryImpApplicationForm.getCourseId();
				RevaluationExamFees bo=(RevaluationExamFees)PropertyUtil.getDataForUniqueObject(query);

				if(bo!=null){

					if(bo.getApplicationFees()!=null){
						newSupplementaryImpApplicationForm.setApplicationFees(bo.getApplicationFees().doubleValue());
						toralamount=toralamount+(bo.getApplicationFees().doubleValue());
					}else{
						newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
					}

					if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){

						if(bo.getRevaluationFees()!=null){
							newSupplementaryImpApplicationForm.setRevaluationFees(bo.getRevaluationFees().doubleValue());
							toralamount=toralamount+(bo.getRevaluationFees().doubleValue()*revaluationcount);
						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}
					}
					if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){

						if(bo.getScrutinyFees()!=null){
							newSupplementaryImpApplicationForm.setScrutinyFees(bo.getScrutinyFees().doubleValue());
							toralamount=toralamount+(bo.getScrutinyFees().doubleValue()*scrutinycount);

						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}


					}

					else if(newSupplementaryImpApplicationForm.getProgramTypeName().equalsIgnoreCase("PG")){
						if(bo.getChallengeValuationFees()!=null){
							newSupplementaryImpApplicationForm.setChallengeRevaluationFees(bo.getChallengeValuationFees().doubleValue());
							toralamount=toralamount+(bo.getChallengeValuationFees().doubleValue()*challengeValuationCount);

						}else{
							newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
						}


					}
					if(newSupplementaryImpApplicationForm.isExtended()){
						if(newSupplementaryImpApplicationForm.getIsFine()){
							newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(regFineFees.getFineAmount()));
						}
						else if(newSupplementaryImpApplicationForm.getIsSuperFine()){
							newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(regFineFees.getSuperFineAmount()));
						}						
					}

					toralamount += newSupplementaryImpApplicationForm.getFineFees();

					if(!newSupplementaryImpApplicationForm.getAppliedThroughAdmin()){
						newSupplementaryImpApplicationForm.setApplicationAmount(String.valueOf(toralamount));
						newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)toralamount));
						newSupplementaryImpApplicationForm.setTotalFees(toralamount-newSupplementaryImpApplicationForm.getApplicationFees());
						
					}
					else
						newSupplementaryImpApplicationForm.setApplicationFees(0);


				}else{
					newSupplementaryImpApplicationForm.setRevAppAvailable(false);
				}
                if(!newSupplementaryImpApplicationForm.getAppliedThroughAdmin())
				newSupplementaryImpApplicationForm.setApplicationAmount(toralamount+"");

			}





			eto.setExamList(classList);
			examList.add(eto);
		}//close main for loop

		newSupplementaryImpApplicationForm.setDisplayButton(displayButton);
		newSupplementaryImpApplicationForm.setMainList(examList);
	}



	/**
	 * @param newSupplementaryImpApplicationForm
	 * @return
	 */
	public boolean saveRevaluationApplicationForStudentLogin( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();

		Student student = new Student();
		student.setId(newSupplementaryImpApplicationForm.getStudentId());
		List<ExamRevaluationApplicationNew> boList=new ArrayList<ExamRevaluationApplicationNew>();
		List<SupplementaryAppExamTo> examList=newSupplementaryImpApplicationForm.getMainList();
		for (SupplementaryAppExamTo suppTo : examList) {
			ExamDefinitionBO examDefinition = new ExamDefinitionBO();
			examDefinition.setId(suppTo.getExamId());
			List<SupplementaryApplicationClassTo> classTos=suppTo.getExamList();
			for (SupplementaryApplicationClassTo cto: classTos) {
				Classes classes = new Classes();
				classes.setId(cto.getClassId());
				List<ExamSupplementaryImpApplicationSubjectTO> listSubject = cto.getToList();
				for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {


					ExamRevaluationApplicationNew objBO = new ExamRevaluationApplicationNew();
					if(to.getId()!=null)
						objBO.setId(to.getId());
					objBO.setCreatedBy(newSupplementaryImpApplicationForm.getUserId());
					objBO.setCreatedDate(new Date());
					objBO.setLastModifiedDate(new Date());
					objBO.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
					objBO.setStudent(student);
					objBO.setExam(examDefinition);
					Subject subject = new Subject();
					subject.setId(to.getSubjectId());
					objBO.setSubject(subject);
					objBO.setClasses(classes);
					objBO.setMarks(to.getMarks());

					//set new one
					if(!to.isTempChecked()){
						if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){
							objBO.setIsRevaluation(true);
							objBO.setIsScrutiny(false);
							objBO.setIsApplied(to.getIsApplied());
						}

						else if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null &&  newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
							objBO.setIsRevaluation(false);
							objBO.setIsScrutiny(true);
							objBO.setIsApplied(to.getIsApplied());
						}
						else{
							objBO.setIsRevaluation(false);
							objBO.setIsScrutiny(false);
							objBO.setIsChallengeValuation(true);
							objBO.setIsApplied(to.getIsApplied());
						}

					}//close checked or not
					//set old one
					else{
						objBO.setIsRevaluation(to.isRevaluation());
						objBO.setIsScrutiny(to.isScrutiny());
						objBO.setIsChallengeValuation(to.getIsChallengeRevaluation());
						objBO.setIsApplied(to.getIsApplied());


					}

					boList.add(objBO);


				}//close sub loop	

			}//close class loop

		}//close exam loop

		return transaction.saveRevaluationApps(boList);

	}
	
	public boolean saveRevaluationAppWithChallanNos( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();

		Student student = new Student();
		student.setId(newSupplementaryImpApplicationForm.getStudentId());
		Classes classes = new Classes();
		classes.setId(newSupplementaryImpApplicationForm.getRevclassid());

		ExamRevaluationApp objBO = new ExamRevaluationApp();
		ExamDefinitionBO def = new ExamDefinitionBO();
		def.setId(Integer.parseInt(newSupplementaryImpApplicationForm.getRevExamId()));
		objBO.setCreatedBy(newSupplementaryImpApplicationForm.getUserId());
		objBO.setCreatedDate(new Date());
		objBO.setLastModifiedDate(new Date());
		objBO.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
		objBO.setStudent(student);
		objBO.setExam(def);
		objBO.setChallanNo(newSupplementaryImpApplicationForm.getChallanNo());
		objBO.setAmount(new BigDecimal(newSupplementaryImpApplicationForm.getApplicationAmount()));
		objBO.setIsChallanVerified(false);
		objBO.setClasses(classes);
		
		if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){
			objBO.setIsRevaluation(true);
			objBO.setIsScrutiny(false);
			objBO.setIsChallengeValuation(false);

		}

		else if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null &&  newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
			objBO.setIsRevaluation(false);
			objBO.setIsScrutiny(true);
			objBO.setIsChallengeValuation(false);

		}
		else{ 
			objBO.setIsRevaluation(false);
			objBO.setIsScrutiny(false);
			objBO.setIsChallengeValuation(true);
		}
		
		return transaction.saveRevaluationAppWithChallanNos(objBO);

	}
	public ExamRevaluationApp checkDuplicationForRevaluation(NewSupplementaryImpApplicationForm form) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		ExamRevaluationApp bo =transaction.checkDuplicationForRevaluation(form);
		return bo;
	}
	
	public Double getRevaluationFeeForStudent( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {

		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewSupplementaryImpApplicationTransaction transaction1=NewSupplementaryImpApplicationTransactionImpl.getInstance();


		if(newSupplementaryImpApplicationForm.getExamId()!=null) {

			String query="";


			//Basim did for different fine fees(Fine,Super Fine)
			query="from ExamPublishHallTicketMarksCardBO p"+
			" where p.classes.id="+newSupplementaryImpApplicationForm.getStudentObj().getClassSchemewise().getClasses().getId()+
			" and p.examId=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+" and p.publishFor='Revaluation/Scrutiny'";
			List<ExamPublishHallTicketMarksCardBO> fineDetails=transaction.getDataForQuery(query);
			ExamPublishHallTicketMarksCardBO regFineFees=null;
			if(fineDetails!=null && !fineDetails.isEmpty()){
				regFineFees=fineDetails.get(0);
			}
			newSupplementaryImpApplicationForm.setStudentId(newSupplementaryImpApplicationForm.getStudentObj().getId());

			if(transaction1.checkSubmitRegApp(newSupplementaryImpApplicationForm)){
				newSupplementaryImpApplicationForm.setExtended(false);
			}



		//	newSupplementaryImpApplicationForm.setTotalFees(totalfee);

			//newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalfee));



		}//main for

		return newSupplementaryImpApplicationForm.getTotalFees();


	}
	
	public Map<String, ExamSupplementaryImpApplicationTO> getStudentListForRevaluation(
			NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm)
			throws Exception {
		Integer studentId = getStudentId(newSupplementaryImpApplicationForm);
		String query = NewSupplementaryImpApplicationHelper.getInstance()
		.getStudentsForRevaluationEdit(newSupplementaryImpApplicationForm,
				studentId);
		boolean isRegOrRoll=false;
		boolean checkAll=true;
		if((newSupplementaryImpApplicationForm.getRegisterNo()!=null && !newSupplementaryImpApplicationForm.getRegisterNo().isEmpty()) || (newSupplementaryImpApplicationForm.getRollNo()!=null && !newSupplementaryImpApplicationForm.getRollNo().isEmpty())){
			isRegOrRoll=true;
		}
		if(isRegOrRoll && studentId==null)
			checkAll=false;
		if(checkAll){
			INewExamMarksEntryTransaction transaction = NewExamMarksEntryTransactionImpl
			.getInstance();
			List boList = transaction.getDataForQuery(query);
			return NewSupplementaryImpApplicationHelper.getInstance()
			.convertBotoToList(boList, newSupplementaryImpApplicationForm);
		}else
			return new HashMap<String, ExamSupplementaryImpApplicationTO>();
	}
	
	public List<Integer> getCurrentClassId(int studentId) throws Exception {

		String query="select s.classSchemewise.id from Student s s.student.id="+studentId ; 
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}
	
	public void setDatatoToForRevaluation(ExamSupplementaryImpApplicationTO to)
	throws Exception {
		String query = NewSupplementaryImpApplicationHelper.getInstance().getQueryForEditRevaluation(to);
		INewExamMarksEntryTransaction transaction = NewExamMarksEntryTransactionImpl.getInstance();
		List<ExamRevaluationApplicationNew> boList = transaction .getDataForForRevaluationEdit(query);
		NewSupplementaryImpApplicationHelper.getInstance().convertBotoToListForRevaluationEdit(boList, to);
	}
	
	public boolean saveRevaluationApplication( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm)throws Exception {

		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		List<ExamRevaluationApplicationNew> boList=new ArrayList<ExamRevaluationApplicationNew>();
		ExamSupplementaryImpApplicationTO suppTo = newSupplementaryImpApplicationForm .getSuppTo();
		ExamDefinitionBO examDefinition = new ExamDefinitionBO();
		examDefinition.setId(suppTo.getExamId());
		Student student = new Student();
		student.setId(suppTo.getStudentId());
		Classes classes = new Classes();
		classes.setId(suppTo.getClassId());
		
		double toralamount=0;
		int revaluationCount=0;
		int scrutinyCount =0;
		int challengeValuationCount=0;
		boolean isChallanVerifiedAlready =false;
		
		ExamRevaluationApp examRevaluationApp = NewSupplementaryImpApplicationHandler.getInstance().checkDuplicationForRevaluation(newSupplementaryImpApplicationForm);
		List<ExamSupplementaryImpApplicationSubjectTO> listSubject = suppTo .getSubjectList();
		for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
			ExamRevaluationApplicationNew objBO = new ExamRevaluationApplicationNew();
			if(to.getId()!=null)
				objBO.setId(to.getId());
			objBO.setCreatedBy(newSupplementaryImpApplicationForm.getUserId());
			objBO.setCreatedDate(new Date());
			objBO.setLastModifiedDate(new Date());
			objBO.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
			objBO.setStudent(student);
			objBO.setExam(examDefinition);
			Subject subject = new Subject();
			subject.setId(to.getSubjectId());
			objBO.setSubject(subject);
			objBO.setIsApplied(to.getIsAppearedTheory());
			if(to.getMarks()!=null)
			objBO.setMarks(to.getMarks());
			

			objBO.setClasses(classes);
			if (newSupplementaryImpApplicationForm .getIsRevaluation() != null && newSupplementaryImpApplicationForm .getIsRevaluation().trim().length() > 0) {
				if (newSupplementaryImpApplicationForm .getIsRevaluation().equalsIgnoreCase("revaluation")) {
					objBO.setIsRevaluation(true);
					if(to.getIsAppearedTheory())
						revaluationCount++;
			
				} else if (newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")) {
					objBO.setIsScrutiny(true);
					if(to.getIsAppearedTheory())
						scrutinyCount++;

				}				
				else{
					objBO.setIsChallengeValuation(true);
					if(to.getIsAppearedTheory())
						challengeValuationCount++;

				}
			}
			boList.add(objBO);
		}
		
		if(newSupplementaryImpApplicationForm.getCourseId()!=null){

			String query="from RevaluationExamFees r where r.academicYear = "+newSupplementaryImpApplicationForm.getAcademicYear()+" and r.course.id="+newSupplementaryImpApplicationForm.getCourseId();
			RevaluationExamFees bo=(RevaluationExamFees)PropertyUtil.getDataForUniqueObject(query);

			if(bo!=null){
				if(bo.getApplicationFees()!=null){
					toralamount=toralamount+(bo.getApplicationFees().doubleValue());
				}
				if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){
					if(bo.getRevaluationFees()!=null){
						toralamount=toralamount+(bo.getRevaluationFees().doubleValue()*revaluationCount);
					}
				}
				else if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
					if(bo.getScrutinyFees()!=null){
						toralamount=toralamount+(bo.getScrutinyFees().doubleValue()*scrutinyCount);
					}
				}

				else{
					if(bo.getChallengeValuationFees()!=null){
						toralamount=toralamount+(bo.getChallengeValuationFees().doubleValue()*challengeValuationCount);
					}
				}
			}
		}
		
		if(examRevaluationApp.getIsChallanVerified()){
			if(examRevaluationApp.getAmount()!=null && toralamount>0){
				if(toralamount-examRevaluationApp.getAmount().doubleValue()>0){
					examRevaluationApp.setLaterAppliedFees(String.valueOf(toralamount-examRevaluationApp.getAmount().doubleValue()));
					examRevaluationApp.setIsChallanVerified(false);
				}
			}
		}
		else
			examRevaluationApp.setAmount(new BigDecimal(toralamount));
		return transaction.saveRevaluationRecords(boList) && transaction.saveRevaluationAppRecords(examRevaluationApp);
	}
	
	public boolean deleteRevaluationApp(ExamSupplementaryImpApplicationTO to,  NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		String query = NewSupplementaryImpApplicationHelper.getInstance().getQueryForDeletingRevaluationRecords(to,newSupplementaryImpApplicationForm);
		String queryForRevApp = NewSupplementaryImpApplicationHelper.getInstance().getQueryForDeletingRevaluationAppRecords(to, newSupplementaryImpApplicationForm);
		
		return transaction.deleteRevaluationApp(query) && transaction.deleteRevaluationApp(queryForRevApp); 
	}

	public List<Integer> checkInternalRedoAppAvailable(int studentId) throws Exception {
		
		String query="select p.examDefinitionBO.id from ExamPublishHallTicketMarksCardBO p" +
					 " where p.isActive=1" +
					 "   and p.examDefinitionBO.delIsActive=1" +
					 "   and p.examDefinitionBO.id in (select s.examDefinition.id from StudentSupplementaryImprovementApplication s" +
					 " 					    where s.student.id= " +studentId+
					 " 					      and ((s.isCIAFailedPractical=1 or s.isCIAFailedTheory=1)" +
					 "						  		and (s.isSupplementary=1))" +
					 "					      and s.classes.id in(p.classes.id))" +
					 " and (('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.downloadStartDate and p.downloadEndDate) or ('"+CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate())+"' between p.downloadStartDate and p.extendedDate)) " +
					 "group by p.examDefinitionBO.id "; 
		
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}
	
	public Boolean checkInternalRedooDateExtended(int studentId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		
		Boolean isExtended=false;
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		isExtended=transaction.getExtendedDateInternalRedo(studentId, newSupplementaryImpApplicationForm);
		return isExtended;
	}
	
	public void getApplicationFormsForInternalRedo(Boolean extendedTrue, List<Integer> examIds, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		INewSupplementaryImpApplicationTransaction transaction1=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		List<SupplementaryAppExamTo> examList=new ArrayList<SupplementaryAppExamTo>();
		SupplementaryAppExamTo eto=null;
		int count=0;
		boolean buttonDisplay=false;
		boolean challanDisplay=false;
		boolean isTotal=false;
		boolean isChecked=false;
		boolean isScSupply=false;
		List<Integer> classNotRequiredList=new ArrayList<Integer>();

		if(newSupplementaryImpApplicationForm.getExamId()!=null){
			
			eto=new SupplementaryAppExamTo();
			eto.setExamId(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()));
			eto.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()),"ExamDefinition",true,"name"));
			
			String endDateQuery="select concat(s.downloadEndDate,'') from ExamPublishHallTicketMarksCardBO s " +
								 "where s.examDefinitionBO.id="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
								  " and s.classes.id in (select supp.classes.id from StudentSupplementaryImprovementApplication supp " +
								  						   "where supp.examDefinition.id="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
								  						   "  and supp.student.id="+newSupplementaryImpApplicationForm.getStudentId()+") " +
								  						   "  and s.isActive=1";
			
			List dateList = transaction.getDataForQuery(endDateQuery);

			String endDate=""; 
			
			if(dateList != null && !dateList.isEmpty())
				endDate = (String) dateList.get(0);
			eto.setExamDate(CommonUtil.formatSqlDate1(endDate));
			
			if(extendedTrue){
				String extendedDateQuery="select concat(s.extendedDate,'') " +
										 "  from ExamPublishHallTicketMarksCardBO s " +
										 " where s.examDefinitionBO.id="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
										 "   and s.classes.id in (select supp.classes.id from StudentSupplementaryImprovementApplication supp " +
										 						  "where supp.examDefinition.id="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
										 						  "  and supp.student.id="+newSupplementaryImpApplicationForm.getStudentId()+") " +
										 "   and s.isActive=1";
				List extendedDateList = transaction.getDataForQuery(extendedDateQuery);

				String extendedEndDate=""; 
				if(extendedDateList != null && !extendedDateList.isEmpty())
					extendedEndDate = (String) extendedDateList.get(0);
				if(extendedEndDate!=null)
					eto.setExtendedDate(CommonUtil.formatSqlDate1(extendedEndDate));
			}

			//rgahu write newly like regular app
			String query1 = "from ExamDefinition e where e.id ="+Integer.parseInt(newSupplementaryImpApplicationForm.getExamId());
			List<ExamDefinition> examList1=transaction.getDataForQuery(query1);
			for (ExamDefinition bo : examList1) {
				newSupplementaryImpApplicationForm.setExamId(String.valueOf(bo.getId()));
				newSupplementaryImpApplicationForm.setExamName(bo.getName());
				newSupplementaryImpApplicationForm.setMonth(CommonUtil.getMonthForNumber(Integer.parseInt(bo.getMonth())));
				newSupplementaryImpApplicationForm.setYear(String.valueOf(bo.getYear()));
				newSupplementaryImpApplicationForm.setAcademicYear(String.valueOf(bo.getAcademicYear()));
			}

			query1 = "from CourseToDepartment c where c.course.id ="+newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln()
																													   .getCourseBySelectedCourseId()
																													   .getId();
			List<CourseToDepartment> courseToDepartmentList=transaction.getDataForQuery(query1);
			String dep="";

			for (CourseToDepartment bo : courseToDepartmentList) {
				
				if(courseToDepartmentList.size()!=0 && courseToDepartmentList.size()==1){
					dep=dep+bo.getDepartment().getName();
				}
				else if(courseToDepartmentList.size()!=0 && courseToDepartmentList.size()>1){
					if(count==0){
						dep=dep+bo.getDepartment().getName();
					}
					else{
						dep=dep+","+bo.getDepartment().getName();
					}
					count++;
				}
				newSupplementaryImpApplicationForm.setCourseDep(dep);
			}

			String query="SELECT * FROM EXAM_supplementary_improvement_application EXAM_supplementary_improvement_application" +
						 " INNER JOIN EXAM_sub_definition_coursewise EXAM_sub_definition_coursewise ON (EXAM_supplementary_improvement_application.subject_id =  EXAM_sub_definition_coursewise.subject_id)" +
						 " where EXAM_supplementary_improvement_application.student_id="+newSupplementaryImpApplicationForm.getStudentId()+
						 " and EXAM_supplementary_improvement_application.exam_id=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId())+
						 " and ((EXAM_supplementary_improvement_application.cia_is_failed_practical=1 or " +
						 "		 EXAM_supplementary_improvement_application.cia_is_failed_theory=1)" +
						 " 		 and " +
						 "		 EXAM_supplementary_improvement_application.is_supplementary=1) "+
						 " group by EXAM_supplementary_improvement_application.subject_id " +
						 " order by EXAM_sub_definition_coursewise.subject_order";

			List<StudentSupplementaryImprovementApplication> boList= transaction.getDataForSQLQuery(query);
			List<SupplementaryApplicationClassTo> classList=new ArrayList<SupplementaryApplicationClassTo>();
			SupplementaryApplicationClassTo to=null;
			
			boolean isSupplementary=false;
			double applicationFee=0;
			double paperFee=0;
			double RegistrationFee=0;
			double cvCampFee=0;
			double marksListFee=0;
			double totalfee=0;
			
			Map<Integer, StudentOverallInternalMarkDetails> subjectMarkMap = null;
			List<Integer> selffinanceCourseId=CMSConstants.SELF_FINANCE_COURSE;
			int secId=0;
			for (StudentSupplementaryImprovementApplication bo : boList) {
				if (!classNotRequiredList.contains(bo.getClasses().getId())) {

					query="from InternalRedoFees r where r.academicYear="+newSupplementaryImpApplicationForm.getAcademicYear()+
						  " and r.classes.id="+bo.getClasses().getId();
					if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection()!=null){
						secId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getPersonalData().getReligionSection().getId();
						if(secId==6||secId==7||secId==8||secId==9||secId==10||secId==11)
							secId=2;
						query+=" and r.religionSection.id="+secId;
					}
					else if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough()!=null){
						int admId=newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getAdmittedThrough().getId();
						secId=0;
						if(admId==2){
							secId=3;
						}else if(admId==18){
							secId=4;
						}else{
							secId=2;
						}
						query+=" and r.religionSection.id="+secId;
					}
					InternalRedoFees internalRedoFees=null;
					List<InternalRedoFees> internalRedoFeeList=transaction.getDataForQuery(query);

					if(internalRedoFeeList==null || internalRedoFeeList.isEmpty() )
						newSupplementaryImpApplicationForm.setFeesNotConfigured(true);
					if(!internalRedoFeeList.isEmpty()){
						internalRedoFees=internalRedoFeeList.get(0);
						if(internalRedoFees.getApplicationFees()!=null){
							applicationFee=internalRedoFees.getApplicationFees().doubleValue();
							newSupplementaryImpApplicationForm.setApplicationFees(applicationFee);
						}
						if(internalRedoFees.getMarksListFees()!=null){
							marksListFee=internalRedoFees.getMarksListFees().doubleValue();
							newSupplementaryImpApplicationForm.setMarksListFees(marksListFee);
						}

						if(internalRedoFees.getRegistrationFees()!=null){
							RegistrationFee=internalRedoFees.getRegistrationFees().doubleValue();
							newSupplementaryImpApplicationForm.setRegistrationFees(RegistrationFee);
						}
						if(internalRedoFees.getPaperFees()!=null){
							double PaperFee=internalRedoFees.getPaperFees().doubleValue();
							newSupplementaryImpApplicationForm.setPaperFees(PaperFee);
						}
						if(internalRedoFees.getCvCampFees()!=null){
							double cvFee=internalRedoFees.getCvCampFees().doubleValue();
							newSupplementaryImpApplicationForm.setCvCampFees(cvFee);
						}
					}
					
					to = new SupplementaryApplicationClassTo();
					to.setClassId(bo.getClasses().getId());
					to.setClassName(bo.getClasses().getName());
					
					if(subjectMarkMap == null || subjectMarkMap.isEmpty()) {
						
						subjectMarkMap = getCIAMarksMapForSubjects(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()),
																   to.getClassId(),
																   newSupplementaryImpApplicationForm.getStudentId());
					}
					
					List<ExamSupplementaryImpApplicationSubjectTO> subjectList = null;
					
					if (classList.contains(to)) {
						
						to = classList.remove(classList.indexOf(to));
						subjectList = to.getToList();
					}
					else {
						
						subjectList = new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
						to.setCourseName(bo.getClasses().getCourse().getName());
						to.setRegisterNo(bo.getStudent().getRegisterNo());
						to.setRollNo(bo.getStudent().getRollNo());
						to.setSemNo(bo.getClasses().getTermNumber().toString());
						to.setStudentName(bo.getStudent().getAdmAppln() .getPersonalData().getFirstName());
						to.setClassName(bo.getClasses().getName());
						if (bo.getIsImprovement() != null)
							to.setImprovement(bo.getIsImprovement());
						if (bo.getIsSupplementary() != null)
							to.setSupplementary(bo.getIsSupplementary());
						if (count == 0) {
							newSupplementaryImpApplicationForm.setCourseName(bo.getClasses().getCourse().getName());
							newSupplementaryImpApplicationForm.setRegisterNo(bo.getStudent().getRegisterNo());
							newSupplementaryImpApplicationForm .setNameOfStudent(bo.getStudent() .getAdmAppln().getPersonalData() .getFirstName());
							count++;
						}
						newSupplementaryImpApplicationForm.setProgramId(String.valueOf(bo.getStudent().getAdmAppln() .getCourseBySelectedCourseId() .getProgram().getId()));
					}
					if (!classNotRequiredList.contains(bo.getClasses().getId())) {
						
						ExamSupplementaryImpApplicationSubjectTO sto = new ExamSupplementaryImpApplicationSubjectTO();
						sto.setId(bo.getId());
						sto.setSubjectId(bo.getSubject().getId());
						sto.setSubjectCode(bo.getSubject().getCode());
						sto.setSubjectName(bo.getSubject().getName());
						
						if((bo.getIsCIAAppearedPractical() != null && bo.getIsCIAAppearedPractical()) || (bo.getIsCIAAppearedTheory() != null && bo.getIsCIAAppearedTheory())){
							if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && (secId==3 || secId==4)&& bo.getChance()>1){
								if(internalRedoFees!=null && internalRedoFees.getPaperFees()!=null)
									paperFee+=internalRedoFees.getPaperFees().doubleValue();
								if(internalRedoFees!=null && internalRedoFees.getCvCampFees()!=null)
									cvCampFee+=internalRedoFees.getCvCampFees().doubleValue();
								isTotal=true;
							}
							else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && (secId!=3 && secId!=4)){
								if(internalRedoFees!=null && internalRedoFees.getPaperFees()!=null)
									paperFee+=internalRedoFees.getPaperFees().doubleValue();
								if(internalRedoFees!=null && internalRedoFees.getCvCampFees()!=null)
									cvCampFee+=internalRedoFees.getCvCampFees().doubleValue();
								isTotal=true;
							}
							else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && selffinanceCourseId.contains(bo.getClasses().getCourse().getId())){
								if(internalRedoFees!=null && internalRedoFees.getPaperFees()!=null)
									paperFee+=internalRedoFees.getPaperFees().doubleValue();
								if(internalRedoFees!=null && internalRedoFees.getCvCampFees()!=null)
									cvCampFee+=internalRedoFees.getCvCampFees().doubleValue();
								isTotal=true;
							}
							else if(bo.getIsSupplementary()!=null && bo.getIsSupplementary()==true && secId==3 || secId==4 && bo.getChance()<=1){
								isScSupply=true;
							}
							challanDisplay=true;
							isChecked=true;
						}

						sto.setSubjectType(bo.getSubject().getIsTheoryPractical());

						if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getName().equalsIgnoreCase("UG")){

							String subQuery =   "select esb.examSubjectSectionMasterBO.name from ExamSubDefinitionCourseWiseBO esb where   esb.subjectUtilBO.id = "+bo.getSubject().getId();
							List sList= transaction.getDataForQuery(subQuery);
							for (Object s : sList) {
								sto.setSectionName(s.toString());
							}

						}

						if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("t") || bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B"))
							sto.setTheory(true);
						if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("p") || bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B"))
							sto.setPractical(true);

						if (!bo.getIsFailedTheory() && !bo.getIsFailedPractical()) {
							sto.setIsOverallTheoryFailed(bo .getIsTheoryOverallFailed());
							sto.setIsOverallPracticalFailed(bo .getIsPracticalOverallFailed());
						} else {
							sto.setIsOverallTheoryFailed(false);
							sto.setIsOverallPracticalFailed(false);
						}

						sto.setChance(bo.getChance());

						if (bo.getFees() != null && !bo.getFees().isEmpty())
							sto.setPreviousFees(Double .parseDouble(bo.getFees()));
						
						sto.setIsFailedTheory(bo.getIsFailedTheory());
						sto.setIsFailedPractical(bo.getIsFailedPractical());
						
						if(bo.getIsCIAFailedTheory() != null)
							sto.setIsCIAFailedTheory(bo.getIsCIAFailedTheory());
						else
							sto.setIsCIAFailedTheory(false);
						
						if(bo.getIsCIAFailedPractical() != null)
							sto.setIsCIAFailedPractical(bo.getIsCIAFailedPractical());
						else
							sto.setIsCIAFailedPractical(false);
						
						if (bo.getIsAppearedTheory() != null){
							sto.setTempChecked(bo.getIsAppearedTheory());
							sto.setCommonChecked(bo.getIsAppearedTheory());
						}
						else
							sto.setTempChecked(false);
						
						if (bo.getIsAppearedPractical() != null){
							sto.setTempPracticalChecked(bo .getIsAppearedPractical());
							sto.setCommonChecked(bo.getIsAppearedPractical());
						}
						else
							sto.setTempPracticalChecked(false);
						
						boolean controlDisable = false;
						if ((bo.getIsFailedPractical() == true) || (bo.getIsFailedTheory() == true)) {
							controlDisable = true;
						}
						
						sto.setControlDisable(controlDisable);
						sto.setClassId(bo.getClasses().getId());
						sto.setSchemeNo(bo.getSchemeNo());
						
						if(bo.getIsOnline()!=null)
							sto.setOnline(bo.getIsOnline());
						else
							sto.setOnline(false);

						if(bo.getIsSupplementary()!=null){
							sto.setIsSupplementary(bo.getIsSupplementary());
							isSupplementary=true;
						}
						
						if(bo.getIsImprovement()!=null)
							sto.setIsImprovement(bo.getIsImprovement());
						
						StudentOverallInternalMarkDetails ciaMark = subjectMarkMap.get(sto.getSubjectId());
						if(sto.isTheory())
							sto.setCiaMark(ciaMark.getTheoryTotalMarks());
						else if(sto.isPractical())
							sto.setCiaMark(ciaMark.getPracticalTotalMarks());
						else if(sto.isTheory() || sto.isPractical()){
							
							double theoryCIAMark = Double.parseDouble(ciaMark.getTheoryTotalMarks());
							double practicalCIAMark = Double.parseDouble(ciaMark.getPracticalTotalMarks());
							sto.setCiaMark(String.valueOf(theoryCIAMark + practicalCIAMark));
						}
						
						newSupplementaryImpApplicationForm.setExamType("Internal Redo");

						subjectList.add(sto);
						to.setToList(subjectList);
						if(to.isSupplementary())
							if ((sto.isTempChecked() != sto.getIsCIAFailedTheory()) || (sto.isTempPracticalChecked() != sto.getIsCIAFailedPractical())){
								buttonDisplay = true;
							}
						
						if(to.isImprovement())
							if ((sto.isTheory() && !sto.isTempChecked()) || (!sto.isTempPracticalChecked() && sto.isPractical())){
								buttonDisplay = true;
							}

						if(bo.getCiaExam()!= null) {
							
							if(bo.getCiaExam()) {

								sto.setCiaExam(true);
								sto.setIsFailedTheory(bo.getIsCIAFailedTheory());
								sto.setIsFailedPractical(bo.getIsCIAFailedPractical());
								
								if (bo.getIsCIAAppearedTheory() != null) {
									sto.setTempChecked(bo.getIsCIAAppearedTheory());
									if(bo.getIsCIAAppearedTheory())
										sto.setTempCIAExamChecked(true);
									else
										sto.setTempCIAExamChecked(false);
								}
								else {
									sto.setTempChecked(false);
								}
								if (bo.getIsCIAAppearedPractical() != null) {
									
									sto.setTempPracticalChecked(bo .getIsCIAAppearedPractical());
									if(bo.getIsCIAAppearedPractical()) {
										sto.setTempCIAExamChecked(true);
									}
									else {
										sto.setTempCIAExamChecked(false);
									}
								}
								else {
									sto.setTempPracticalChecked(false);
								}
								if(bo.getIsCIAAppearedPractical() || bo.getIsCIAAppearedTheory()) {
									sto.setTempCIAExamChecked(true);
								}
								if(bo.getIsCIAAppearedPractical()!=null) {
									sto.setIsCIAAppearedPractical(bo.getIsCIAAppearedPractical());
								}
								else {
									sto.setIsCIAAppearedPractical(false);
								}
								if(bo.getIsCIAAppearedTheory()!=null) {
									sto.setIsCIAAppearedTheory(bo.getIsCIAAppearedTheory());
								}
								else {
									sto.setIsCIAAppearedTheory(false);
								}
								if(bo.getIsCIAFailedPractical()!=null) {
									sto.setIsCIAFailedPractical(bo.getIsCIAFailedPractical());
								}
								else {
									sto.setIsCIAFailedPractical(false);
								}
								if(bo.getIsCIAFailedTheory()!=null) {
									sto.setIsCIAFailedTheory(bo.getIsCIAFailedTheory());
								}
								else {
									sto.setIsCIAFailedTheory(false);
								}
							}
							else {
								
								sto.setTempCIAExamChecked(false);
								sto.setIsCIAAppearedPractical(false);
								sto.setIsCIAAppearedTheory(false);
								sto.setIsCIAFailedPractical(false);
								sto.setIsCIAFailedTheory(false);
								sto.setCiaExam(false);
							}
						}
						else {
							
							sto.setTempCIAExamChecked(false);
							sto.setIsCIAAppearedPractical(false);
							sto.setIsCIAAppearedTheory(false);
							sto.setIsCIAFailedPractical(false);
							sto.setIsCIAFailedTheory(false);
							sto.setCiaExam(false);
						}
						if(bo.getCiaExam()!=null) {
							
							if(bo.getCiaExam()) {
								sto.setIsESE(false);
							}
							else {
								if(bo.getIsAppearedPractical()|| bo.getIsAppearedTheory()) {
									sto.setIsESE(true);
								}
								else {
									sto.setIsESE(false);
								}
							}
						}
						else {
							if(bo.getIsAppearedPractical()|| bo.getIsAppearedTheory()) {
								sto.setIsESE(true);
							}
							else {
								sto.setIsESE(false);
							}
						}
						newSupplementaryImpApplicationForm.setPrevClassId(bo.getClasses().getId()+"");						
						classList.add(to);
					}
				}
			}
			
			query="from ExamPublishHallTicketMarksCardBO p"+
				  " where p.classes.id="+newSupplementaryImpApplicationForm.getPrevClassId()+
				  " and p.examDefinitionBO.id=" +Integer.parseInt(newSupplementaryImpApplicationForm.getExamId());
			List<ExamPublishHallTicketMarksCardBO> fineDetails=transaction.getDataForQuery(query);
			ExamPublishHallTicketMarksCardBO internalRedoFees=null;
			if(fineDetails!=null) {
				internalRedoFees=fineDetails.get(0);
			}
			if(isTotal==false && isChecked==true) {
				cvCampFee=0;
				paperFee=0;
				applicationFee=0;
				marksListFee=0;
				RegistrationFee=CMSConstants.SC_REGULAR_SUPPLY_FEE;
			}
			if(isTotal==true && isChecked==true && isScSupply==true) {
				double regForSC=CMSConstants.SC_REGULAR_SUPPLY_FEE;
				RegistrationFee+=regForSC;
			}
			totalfee=cvCampFee+RegistrationFee+paperFee+applicationFee+marksListFee;
			if(newSupplementaryImpApplicationForm.isExtended())
				if(transaction1.checkSubmitSuppApp(newSupplementaryImpApplicationForm)) {
					newSupplementaryImpApplicationForm.setExtended(false);
				}
			if(newSupplementaryImpApplicationForm.isExtended())
			{
				double fine=0.0;
				if(newSupplementaryImpApplicationForm.getIsFine()){
					fine=Double.parseDouble(internalRedoFees.getFineAmount());
					newSupplementaryImpApplicationForm.setFineFees(fine);
				}
				else if(newSupplementaryImpApplicationForm.getIsSuperFine()){
					fine=Double.parseDouble(internalRedoFees.getSuperFineAmount());
					newSupplementaryImpApplicationForm.setFineFees(fine);

				}
				totalfee+=fine;
			}
			else {
				newSupplementaryImpApplicationForm.setFineFees(0);
			}

			boolean isAmountAddedRegApp=transaction1.addAmountToExamRegApp(newSupplementaryImpApplicationForm, totalfee);
			newSupplementaryImpApplicationForm.setRegistrationFees(RegistrationFee);
			newSupplementaryImpApplicationForm.setTotalFees(totalfee);
			newSupplementaryImpApplicationForm.setTotalCvCampFees(cvCampFee);
			newSupplementaryImpApplicationForm.setTotalPaperFees(paperFee);
			newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalfee));
			if(isTotal==false && isChecked==true){
				newSupplementaryImpApplicationForm.setPaperFees(0);
				newSupplementaryImpApplicationForm.setCvCampFees(0);
				newSupplementaryImpApplicationForm.setApplicationFees(0);
				newSupplementaryImpApplicationForm.setMarksListFees(0);
				newSupplementaryImpApplicationForm.setTotalCvCampFees(0);
				newSupplementaryImpApplicationForm.setTotalPaperFees(0);
			}
			newSupplementaryImpApplicationForm.setIsSupplPaper(isSupplementary);
			eto.setExamList(classList);
			examList.add(eto);
		}
		newSupplementaryImpApplicationForm.setDisplayButton(buttonDisplay);
		newSupplementaryImpApplicationForm.setMainList(examList);
		newSupplementaryImpApplicationForm.setChallanDisplay(challanDisplay);
	}
	
	/**
	 * @param to
	 * @param examId
	 * @throws Exception
	 * @author Arun Sudhakaran
	 * 
	 * Purpose : 
	 * 			This method was developed because the cia marks for respective subjects needed to be displayed, which wasn't available in the
	 * SupplementaryImprovement table so, marks are set to subject TOs by fetching data from OverAllInternal table.
	 */
	private Map<Integer, StudentOverallInternalMarkDetails> getCIAMarksMapForSubjects(int examId, int classId, int studentId) throws Exception {
		
		INewUpdateProccessTransaction tx = NewUpdateProccessTransactionImpl.getInstance();
		Map<Integer, StudentOverallInternalMarkDetails> subjectMarksMap = new HashMap<Integer, StudentOverallInternalMarkDetails>();
		
		Iterator<StudentOverallInternalMarkDetails> ciaMarksItr = tx.getInternalMarksForGivenExamClassAndStudent(examId, classId, studentId).iterator();
		while(ciaMarksItr.hasNext()) {
			
			StudentOverallInternalMarkDetails internalFinalMark = ciaMarksItr.next();
			subjectMarksMap.put(internalFinalMark.getSubject().getId(), internalFinalMark);
		}
		return subjectMarksMap;
	}
	
	public boolean saveInternalRedoApplicationForStudentLogin( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		
		INewSupplementaryImpApplicationTransaction transaction=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		
		ExamRegularApplication examRegApp=null;
		Student student = new Student();
		student.setId(newSupplementaryImpApplicationForm.getStudentId());

		boolean isPaymentSuccess=true;

		if(isPaymentSuccess){
			
			List<StudentSupplementaryImprovementApplication> boList=new ArrayList<StudentSupplementaryImprovementApplication>();
			List<SupplementaryAppExamTo> examList=newSupplementaryImpApplicationForm.getMainList();

			for (SupplementaryAppExamTo suppTo : examList) {
				
				ExamDefinition examDefinition = new ExamDefinition();
				examDefinition.setId(suppTo.getExamId());
				ExamDefinitionBO examDefinitionBo = new ExamDefinitionBO();
				examDefinitionBo.setId(suppTo.getExamId());
				List<SupplementaryApplicationClassTo> classTos=suppTo.getExamList();
				
				for (SupplementaryApplicationClassTo cto: classTos) {
					
					Classes classes = new Classes();
					classes.setId(cto.getClassId());
					newSupplementaryImpApplicationForm.setPrevClassId(cto.getClassId()+"");
					List<ExamSupplementaryImpApplicationSubjectTO> listSubject = cto.getToList();
					
					for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
						
						StudentSupplementaryImprovementApplication objBO = new StudentSupplementaryImprovementApplication();
						
						if(examRegApp==null) {
							
							examRegApp=new ExamRegularApplication();
							examRegApp.setClasses(classes);
							examRegApp.setExam(examDefinitionBo);
							
							if(newSupplementaryImpApplicationForm.getRegNo()!=null){
								examRegApp.setChallanNo("IRE"+newSupplementaryImpApplicationForm.getRegNo()+newSupplementaryImpApplicationForm.getExamId());
								newSupplementaryImpApplicationForm.setChalanNo(examRegApp.getChallanNo());
							}
							examRegApp.setStudent(student);
							examRegApp.setLastModifiedDate(new Date());
							examRegApp.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
							examRegApp.setIsApplied(true);
						}

						if(to.getId()!=null)
							objBO.setId(to.getId());

						objBO.setLastModifiedDate(new Date());
						objBO.setModifiedBy(newSupplementaryImpApplicationForm.getUserId());
						objBO.setStudent(student);
						objBO.setExamDefinition(examDefinition);
						Subject subject = new Subject();
						subject.setId(to.getSubjectId());
						objBO.setSubject(subject);
						
						double fees=0;					
						
						boolean isEntered=false;
						if(!to.isTempChecked()){
																
							if(to.getIsCIAAppearedTheory()){
								
								objBO.setIsOnline(true);
								isEntered=true;
							}
							else
								objBO.setIsOnline(to.isOnline());
							
							objBO.setIsCIAAppearedTheory(to.getIsCIAAppearedTheory());
						}
						else{
							objBO.setIsOnline(to.isOnline());
						}
						if(!to.isTempPracticalChecked()){
																
							if(to.getIsCIAAppearedPractical()){
								
								if(!isEntered){
									objBO.setIsOnline(true);
									isEntered=true;
								}
							}else{
								if(!isEntered)
									objBO.setIsOnline(to.isOnline());
							}
							
							objBO.setIsCIAAppearedPractical(to.getIsCIAAppearedPractical());
						}
						else{
							if(!isEntered)
								objBO.setIsOnline(to.isOnline());
						}
						objBO.setFees(String.valueOf(fees));
						
						objBO.setIsCIAFailedTheory(to.isTheory());
						objBO.setIsCIAFailedPractical(to.isPractical());
						
						objBO.setIsFailedTheory(false);
						objBO.setIsFailedPractical(false);
						objBO.setIsAppearedTheory(false);
						objBO.setIsAppearedPractical(false);
						objBO.setSchemeNo(to.getSchemeNo());
						objBO.setChance(to.getChance());
						objBO.setCiaExam(true);
						
						if(to.getIsOverallTheoryFailed()!=null)
							objBO.setIsTheoryOverallFailed(to.getIsOverallTheoryFailed());
						else
							objBO.setIsTheoryOverallFailed(false);
						if(to.getIsOverallPracticalFailed()!=null)
							objBO.setIsPracticalOverallFailed(to.getIsOverallPracticalFailed());
						else
							objBO.setIsPracticalOverallFailed(false);
						objBO.setClasses(classes);

						objBO.setIsSupplementary(true);
						objBO.setIsImprovement(false);
						
						boList.add(objBO);
					}
				}

			}
			boolean isDuplicate=transaction.checkDuplicationRegularExamApp(newSupplementaryImpApplicationForm);
			boolean isAdded=false;
			if(!isDuplicate){
				isAdded=transaction.addAppliedStudent(examRegApp);
				return transaction.saveSupplementarys(boList) && isAdded;
			}
			else
				return transaction.saveSupplementarys(boList);
		}else
			return false;
	}
}
