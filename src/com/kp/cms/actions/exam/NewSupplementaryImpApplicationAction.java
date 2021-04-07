package com.kp.cms.actions.exam;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.NewStudentCertificateCourseAction;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamRegularApplication;
import com.kp.cms.bo.exam.ExamRevaluationApp;
import com.kp.cms.bo.exam.ExamRevaluationApplicationNew;
import com.kp.cms.bo.exam.SupplementaryFees;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.NewSupplementaryImpApplicationForm;
import com.kp.cms.handlers.admission.NewStudentCertificateCourseHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSupplementaryImpAppHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSupplementaryImpApplicationHandler;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationSubjectTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.to.exam.SupplementaryAppExamTo;
import com.kp.cms.to.exam.SupplementaryApplicationClassTo;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewSupplementaryImpApplicationTransaction;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.ClassEntryTransImpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewSupplementaryImpApplicationTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.PropertyUtil;

public class NewSupplementaryImpApplicationAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(NewSupplementaryImpApplicationAction.class);
	ExamSupplementaryImpAppHandler handler = new ExamSupplementaryImpAppHandler();
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSupplementaryImpApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info("Entered initSupplementaryImpApplication");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
		newSupplementaryImpApplicationForm.resetFields();
		setRequiredDatatoForm(newSupplementaryImpApplicationForm);
		log.info("Exit initSupplementaryImpApplication");
		
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP);
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception{
		/*List<KeyValueTO> examList=handler.getExamNameList_SI();
		newSupplementaryImpApplicationForm.setExamList(examList);*/
		//added by Smitha for academic year and exam type parameters
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(newSupplementaryImpApplicationForm.getYear()!=null && !newSupplementaryImpApplicationForm.getYear().isEmpty()){
			year=Integer.parseInt(newSupplementaryImpApplicationForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		if(newSupplementaryImpApplicationForm.getExamType()==null || newSupplementaryImpApplicationForm.getExamType().trim().isEmpty())
			newSupplementaryImpApplicationForm.setExamType("Suppl");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(newSupplementaryImpApplicationForm.getExamType(),year); 
		newSupplementaryImpApplicationForm.setExamNameMap(examMap);
		
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(newSupplementaryImpApplicationForm.getExamType());
		if((newSupplementaryImpApplicationForm.getExamId()==null || newSupplementaryImpApplicationForm.getExamId().trim().isEmpty()) && currentExam!=null){
			newSupplementaryImpApplicationForm.setExamId(currentExam);
		}
		//ends
		if (newSupplementaryImpApplicationForm.getExamId() != null
				&& newSupplementaryImpApplicationForm.getExamId().length() > 0) {
			newSupplementaryImpApplicationForm.setCourseList(handler.getCourseByExamNameRegNoRollNo(
					Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()), newSupplementaryImpApplicationForm
							.getRegisterNo(), newSupplementaryImpApplicationForm.getRollNo()));
		}else{
			newSupplementaryImpApplicationForm.setCourseList(null);
		}
		if (newSupplementaryImpApplicationForm.getCourseId() != null && newSupplementaryImpApplicationForm.getCourseId().length() > 0) {
			newSupplementaryImpApplicationForm.setSchemeList(handler.getSchemeNoByCourse(newSupplementaryImpApplicationForm.getCourseId()));
		}else{
			newSupplementaryImpApplicationForm.setSchemeList(null);
		}
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		 ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		validateCandidate(newSupplementaryImpApplicationForm,errors);
		if (errors.isEmpty()) {
			try {
				Map<String,ExamSupplementaryImpApplicationTO> toMap=NewSupplementaryImpApplicationHandler.getInstance().getStudentListForInput(newSupplementaryImpApplicationForm);
				if (toMap.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
					return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP);
				}
				newSupplementaryImpApplicationForm.setToMap(toMap);
				setNameToForm(newSupplementaryImpApplicationForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
			log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_RESULT);
	}
	
	/**
	 * @param newSupplementaryImpApplicationForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateCandidate( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm, ActionErrors errors) throws Exception {
		if((newSupplementaryImpApplicationForm.getRegisterNo()!=null && !newSupplementaryImpApplicationForm.getRegisterNo().isEmpty()) || (newSupplementaryImpApplicationForm.getRollNo()!=null && !newSupplementaryImpApplicationForm.getRollNo().isEmpty()) ){
			if (newSupplementaryImpApplicationForm.getSchemeNo() == null || newSupplementaryImpApplicationForm.getSchemeNo().trim().isEmpty()) {
				errors.add("error", new ActionError( "knowledgepro.fee.semister.required"));
			}
		}
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @throws Exception
	 */
	private void setNameToForm( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception {
		newSupplementaryImpApplicationForm.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()),"ExamDefinitionBO",true,"name"));
		if(newSupplementaryImpApplicationForm.getCourseId()!=null && !newSupplementaryImpApplicationForm.getCourseId().isEmpty())
			newSupplementaryImpApplicationForm.setCourseName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newSupplementaryImpApplicationForm.getCourseId()),"Course",true,"name"));
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		 ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				Map<String,ExamSupplementaryImpApplicationTO> stuMap=newSupplementaryImpApplicationForm.getToMap();
				ExamSupplementaryImpApplicationTO to=stuMap.get(newSupplementaryImpApplicationForm.getStudent());
				NewSupplementaryImpApplicationHandler.getInstance().setDatatoTo(to);
				newSupplementaryImpApplicationForm.setSuppTo(to);
				newSupplementaryImpApplicationForm.setAddOrEdit("edit");
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
			log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_RESULT);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT);
	}
	
	
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSupplementaryApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		setUserId(request,newSupplementaryImpApplicationForm);
		if (errors.isEmpty()) {
			try {
				boolean isSaved=NewSupplementaryImpApplicationHandler.getInstance().saveSupplementaryApplication(newSupplementaryImpApplicationForm);
				if(isSaved){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.updatesuccess","Student Supplementary Application"));
					saveMessages(request, messages);
					newSupplementaryImpApplicationForm.resetFields();
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Student Supplementary Application"));
					saveErrors(request, errors);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT);
		}
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		if(newSupplementaryImpApplicationForm.getAddOrEdit().equals("add")){
			newSupplementaryImpApplicationForm.setAddOrEdit(null);
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP);
		}else{
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_RESULT);
		}
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSupplementaryImpApp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		
		if (errors.isEmpty()) {
			try {
				Map<String,ExamSupplementaryImpApplicationTO> stuMap=newSupplementaryImpApplicationForm.getToMap();
				ExamSupplementaryImpApplicationTO to=stuMap.get(newSupplementaryImpApplicationForm.getStudent());
				boolean isDelete=NewSupplementaryImpApplicationHandler.getInstance().deleteSupplementaryImpApp(to);
				if(isDelete){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.deletesuccess","Student Supplementary Application"));
					saveMessages(request, messages);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Student Supplementary Application"));
					saveErrors(request, errors);
				}
				Map<String,ExamSupplementaryImpApplicationTO> toMap=NewSupplementaryImpApplicationHandler.getInstance().getStudentListForInput(newSupplementaryImpApplicationForm);
				newSupplementaryImpApplicationForm.setToMap(toMap);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
			log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_RESULT);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_RESULT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		 ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		validateStudentInformation(newSupplementaryImpApplicationForm,errors);
		if (errors.isEmpty()) {
			try {
				String validMsg=NewSupplementaryImpApplicationHandler.getInstance().checkValid(newSupplementaryImpApplicationForm);
				if(validMsg.isEmpty()){
					ExamSupplementaryImpApplicationTO to=NewSupplementaryImpApplicationHandler.getInstance().getStudentDetails(newSupplementaryImpApplicationForm);
					newSupplementaryImpApplicationForm.setSuppTo(to);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.supplementary.message",validMsg));
					addErrors(request, errors);
					setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
					log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
					return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP);
				}
				newSupplementaryImpApplicationForm.setAddOrEdit("add");
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
			log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT);
	}

	/**
	 * @param newSupplementaryImpApplicationForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateStudentInformation( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm, ActionErrors errors) throws Exception {
		String regNo = newSupplementaryImpApplicationForm.getRegisterNo();
		String rollNo = newSupplementaryImpApplicationForm.getRollNo();
		if ((regNo == null || regNo.trim().equals("")) && (rollNo == null || rollNo.trim().equals(""))) {
			errors.add("error", new ActionError( "knowledgepro.exam.ExamInternalMark.regNoOrrollNumber"));
		}
		if (newSupplementaryImpApplicationForm.getSchemeNo() == null || newSupplementaryImpApplicationForm.getSchemeNo().trim().isEmpty()) {
			errors.add("error", new ActionError( "knowledgepro.fee.semister.required"));
		}
	}
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelSupplementaryImpApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_RESULT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkSupplementaryImpApplicationForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		newSupplementaryImpApplicationForm.setIsSupp(true);
		String examId=newSupplementaryImpApplicationForm.getExamId();
		newSupplementaryImpApplicationForm.resetFields();
		newSupplementaryImpApplicationForm.setExamId(examId);
		/*Properties prop = new Properties();
		InputStream inStr = CommonUtil.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(inStr);
		newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(prop.getProperty(CMSConstants.LINK_FOR_FEE_FINE)));*/
		try{
			setDataToForm(newSupplementaryImpApplicationForm,request);
		}catch (Exception e) {
			newSupplementaryImpApplicationForm.setSuppImpAppAvailable(false);
			e.printStackTrace();
		}
		
		
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_RESULT);
	}
	
	/**
	 * @param newSupplementaryImpApplicationForm
	 * @param request
	 * @throws Exception
	 */
	private void setDataToForm( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm, HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession(true);
		newSupplementaryImpApplicationForm.setStudentId(Integer.parseInt(session.getAttribute("studentId").toString()));
		
		//rgahu write newly like regular app
		ISingleFieldMasterTransaction txn1=SingleFieldMasterTransactionImpl.getInstance();
		Student student=(Student) txn1.getMasterEntryDataById(Student.class,newSupplementaryImpApplicationForm.getStudentId());
		newSupplementaryImpApplicationForm.setStudentObj(student);
		
		
		Boolean extendedTrue=NewSupplementaryImpApplicationHandler.getInstance().checkSuppDateExtended(newSupplementaryImpApplicationForm.getStudentId(), newSupplementaryImpApplicationForm);
		List<Integer> examIds=NewSupplementaryImpApplicationHandler.getInstance().checkSuppImpAppAvailable(newSupplementaryImpApplicationForm.getStudentId());
		
		//raghu added for improvement
		
		if(examIds.isEmpty()){
			examIds=NewSupplementaryImpApplicationHandler.getInstance().checkSuppImpAppAvailable1(newSupplementaryImpApplicationForm.getStudentId());
				
		}
		
		//raghu added from mounts
		
		// set app no and date
		NewSupplementaryImpApplicationHandler.getInstance().setAppNoAndDate(newSupplementaryImpApplicationForm); 
	
		if(examIds.isEmpty() || examIds!=null && !examIds.contains(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()))){
			newSupplementaryImpApplicationForm.setSuppImpAppAvailable(false);
		}
		else{
			
			newSupplementaryImpApplicationForm.setSuppImpAppAvailable(true);
			NewSupplementaryImpApplicationHandler.getInstance().getApplicationForms(extendedTrue,examIds,newSupplementaryImpApplicationForm);
			
			if(student!=null){
				
				//raghu write newly like regular app
				newSupplementaryImpApplicationForm.setNameOfStudent(student.getAdmAppln().getPersonalData().getFirstName()+(student.getAdmAppln().getPersonalData().getMiddleName()!=null?student.getAdmAppln().getPersonalData().getMiddleName():"")+(student.getAdmAppln().getPersonalData().getLastName()!=null?student.getAdmAppln().getPersonalData().getLastName():""));
				newSupplementaryImpApplicationForm.setClassName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getName():"");
				newSupplementaryImpApplicationForm.setRegNo(student.getRegisterNo());
				newSupplementaryImpApplicationForm.setDob(null);
				newSupplementaryImpApplicationForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
				newSupplementaryImpApplicationForm.setAddress("");
				newSupplementaryImpApplicationForm.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
				newSupplementaryImpApplicationForm.setSchemeNo(""+student.getClassSchemewise().getClasses().getTermNumber());
				newSupplementaryImpApplicationForm.setEmail(student.getAdmAppln().getPersonalData().getEmail());
				newSupplementaryImpApplicationForm.setMobileNo(student.getAdmAppln().getPersonalData().getMobileNo2());
				newSupplementaryImpApplicationForm.setCourseId(""+student.getClassSchemewise().getClasses().getCourse().getId());
				
				String address="";
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine1()+",";
				}
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine2()+",";
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId().getName();
				}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";
					
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()+",";
				}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";
					
				}
				
				newSupplementaryImpApplicationForm.setAddress(address);
								
				//getting instructions
				String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(student.getClassSchemewise().getClasses().getId(),"Classes",true,"course.program.programType.id");
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
		}
		newSupplementaryImpApplicationForm.setChalanNo("SU"+newSupplementaryImpApplicationForm.getRegNo()+newSupplementaryImpApplicationForm.getExamId());
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward calculateAmount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		newSupplementaryImpApplicationForm.setIsSupp(true);
		 ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		setUserId(request,newSupplementaryImpApplicationForm);
		boolean isChecked=false;
		if (errors.isEmpty()) {
			double amount=0;
			try {
				List<SupplementaryAppExamTo> examList=newSupplementaryImpApplicationForm.getMainList();
				for (SupplementaryAppExamTo suppTo : examList) {
					List<SupplementaryApplicationClassTo> classTos=suppTo.getExamList();
					for (SupplementaryApplicationClassTo cto: classTos) {
						List<ExamSupplementaryImpApplicationSubjectTO> listSubject = cto.getToList();
						for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
							if(!to.isTempChecked())
								if(to.getIsAppearedTheory()){
									amount=amount+newSupplementaryImpApplicationForm.getCvCampFees()+newSupplementaryImpApplicationForm.getPaperFees();	
									isChecked=true;
								}
							if(!to.isTempPracticalChecked())
								if(to.getIsAppearedPractical()){
									amount=amount+newSupplementaryImpApplicationForm.getCvCampFees()+newSupplementaryImpApplicationForm.getPaperFees();
									isChecked=true;
								}
						}
						}
					}
				
				if(amount==0 && !isChecked){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.supplementary.message","Please Select atleast one subject"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_RESULT);
				}/*else
				{
					if(newSupplementaryImpApplicationForm.isExtended())
					{
						Properties prop = new Properties();
						InputStream inStr = CommonUtil.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
						prop.load(inStr);
						newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(prop.getProperty(CMSConstants.LINK_FOR_FEE_FINE)));
						amount=amount+newSupplementaryImpApplicationForm.getFineFees();
					}
				}*/
				amount+=newSupplementaryImpApplicationForm.getApplicationFees()+newSupplementaryImpApplicationForm.getRegistrationFees()+newSupplementaryImpApplicationForm.getMarksListFees();
				newSupplementaryImpApplicationForm.setTotalFees(amount);	
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		//raghu added from mounts
		//return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_VEIRFY_SMART);
		
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_VEIRFY_SMART_2);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verifyStudentSmartCardForStudentLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionErrors errors = new ActionErrors();
		setUserId(request,newSupplementaryImpApplicationForm);//setting the userId to Form
		try {
			boolean isValidSmartCard=NewSupplementaryImpApplicationHandler.getInstance().verifySmartCard(newSupplementaryImpApplicationForm.getSmartCardNo(),newSupplementaryImpApplicationForm.getStudentId());
			if(!isValidSmartCard){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter the valid last 5 digits of your smart card number"));
			}
			if(newSupplementaryImpApplicationForm.getDob()!=null){
				if(!newSupplementaryImpApplicationForm.getDob().equalsIgnoreCase(newSupplementaryImpApplicationForm.getOriginalDob()))
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter Valid Date Of Birth"));
			}
			
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_VEIRFY_SMART);
			}
			
		}catch (ReActivateException e) {
			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_VEIRFY_SMART);
		} catch (Exception e) {
			log.error("Error in getCertificateCourses"+e.getMessage());
			String msg = super.handleApplicationException(e);
			newSupplementaryImpApplicationForm.setErrorMessage(msg);
			newSupplementaryImpApplicationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_VEIRFY_SMART_1);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSupplementaryApplicationForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		setUserId(request,newSupplementaryImpApplicationForm);
		if (errors.isEmpty()) {
			try {
				newSupplementaryImpApplicationForm.setPrintSupplementary(false);
				String msg="";
				boolean isSaved=NewSupplementaryImpApplicationHandler.getInstance().saveSupplementaryApplicationForStudentLogin(newSupplementaryImpApplicationForm);
				msg=newSupplementaryImpApplicationForm.getMsg();
				if(isSaved){
					//raghu
					//NewStudentCertificateCourseHandler.getInstance().sendSMSToStudent(newSupplementaryImpApplicationForm.getStudentId(),CMSConstants.SUPPLEMENTARY_APPLICATION_SMS_TEMPLATE);
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.certificate.added.success.online"));
					saveMessages(request, messages);
					
					//raghu added from mounts
					//set supply details to tos for printing application
					boolean extendedTrue =NewSupplementaryImpApplicationHandler.getInstance().checkSuppDateExtended(newSupplementaryImpApplicationForm.getStudentId(), newSupplementaryImpApplicationForm);
					List<Integer> examIds=NewSupplementaryImpApplicationHandler.getInstance().checkSuppImpAppAvailable(newSupplementaryImpApplicationForm.getStudentId());
					NewSupplementaryImpApplicationHandler.getInstance().getApplicationForms(extendedTrue, examIds, newSupplementaryImpApplicationForm);
					
					
					
					
					
					String printData=NewSupplementaryImpApplicationHandler.getInstance().getPrintData(newSupplementaryImpApplicationForm.getOnlinePaymentId(),request);
					// resetting the fields
					String examId=newSupplementaryImpApplicationForm.getExamId();
					newSupplementaryImpApplicationForm.resetFields();
					/*Properties prop = new Properties();
					InputStream inStr = CommonUtil.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
					prop.load(inStr);
					newSupplementaryImpApplicationForm.setFineFees(Double.parseDouble(prop.getProperty(CMSConstants.LINK_FOR_FEE_FINE)));*/
					newSupplementaryImpApplicationForm.setExamId(examId);
					//setting data for print
					newSupplementaryImpApplicationForm.setPrintData(printData);
					newSupplementaryImpApplicationForm.setPrintSupplementary(true);
					setDataToForm(newSupplementaryImpApplicationForm,request);
				}else{
					if(msg==null || msg.isEmpty()){
						msg="Payment Failed";
						}
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Supplementary/Improvement submission was not successfull, Reason:"+msg));
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Kindly rectify the errors mentioned and re-submit the application" ));
					saveErrors(request, errors);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_STUDENT_RESULT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPrintDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			//raghu added from mounts
			NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
		
			if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getId()==1){
					return mapping.findForward("printDetailsUG");
			}else{
				return mapping.findForward("printDetailsPG");
			}
			
	}
	
	
	//raghu added from mounts
	
	public ActionForward showPrintDetailsSubjectList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		   NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		   ActionMessages messages=new ActionMessages();
		      
			return mapping.findForward("printDetails");
	}
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSupplementaryImpApplicationForAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info("Entered initSupplementaryImpApplication");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
		newSupplementaryImpApplicationForm.resetFields();
		setRequiredDatatoForm(newSupplementaryImpApplicationForm);
		log.info("Exit initSupplementaryImpApplication");
		
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_ALL);
	}

	
	//raghu write for getting all improvement student
	public ActionForward checkUpdateProcessForSupp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewSupplementaryImpApplicationAction - checkUpdateProcessForSupp");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);

		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		String query="from StudentSupplementaryImprovementApplication s where  s.classes.course.id="+newSupplementaryImpApplicationForm.getCourseId()+" and s.schemeNo="+newSupplementaryImpApplicationForm.getSchemeNo()+" and s.examDefinition.id="+newSupplementaryImpApplicationForm.getExamId();


		List previousList=transaction.getDataForQuery(query);
		if(previousList.size()!=0){
			
			getAllStudents(mapping, newSupplementaryImpApplicationForm, request, response);
			newSupplementaryImpApplicationForm.setAdd(true);
			newSupplementaryImpApplicationForm.setContinue1(false);
			
			log.info("Entered NewSupplementaryImpApplicationAction - checkUpdateProcessForSupp");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_ALL);
			
		}else{
			newSupplementaryImpApplicationForm.setAdd(false);
			newSupplementaryImpApplicationForm.setContinue1(true);
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.supp.updateprocess.check"));
			saveErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);
			log.info("Entered NewSupplementaryImpApplicationAction - checkUpdateProcessForSupp");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_ALL);
		}
		
		
		
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//raghu write for getting all improvement student
	public ActionForward getAllStudents(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		
		//for check updateprocess
		newSupplementaryImpApplicationForm.setAdd(true);
		newSupplementaryImpApplicationForm.setContinue1(false);
		
		if (errors.isEmpty()) {
			try {
				String validMsg="";
				//raghu
				//String validMsg=NewSupplementaryImpApplicationHandler.getInstance().checkValidAll(newSupplementaryImpApplicationForm);
				String validMsg1=NewSupplementaryImpApplicationHandler.getInstance().checkImpValidAll(newSupplementaryImpApplicationForm);
				
				if(validMsg.isEmpty() && validMsg1.isEmpty()){
					
					List<ExamSupplementaryImpApplicationTO> suppToList=NewSupplementaryImpApplicationHandler.getInstance().getStudentDetailsAllSupply(newSupplementaryImpApplicationForm);
					newSupplementaryImpApplicationForm.setSuppToList(suppToList);
					
					boolean isSaved=NewSupplementaryImpApplicationHandler.getInstance().saveSupplementaryApplicationAll(newSupplementaryImpApplicationForm);
					
					List<ExamSupplementaryImpApplicationTO> impToList=NewSupplementaryImpApplicationHandler.getInstance().getStudentDetailsAll(newSupplementaryImpApplicationForm);
					newSupplementaryImpApplicationForm.setSuppToList(impToList);
					
					isSaved=NewSupplementaryImpApplicationHandler.getInstance().saveSupplementaryApplicationAll(newSupplementaryImpApplicationForm);
					
					if(isSaved){
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.updatesuccess","Student Improvement Application"));
						saveMessages(request, messages);
						newSupplementaryImpApplicationForm.resetFields();
					}else{
						errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Student Improvement Application"));
						saveErrors(request, errors);
					}
					
					
				}else{
					
					if(!validMsg.isEmpty()){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.supplementary.message",validMsg));
					}else{
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.supplementary.message",validMsg1));
						
					}
					addErrors(request, errors);
					setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
					log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
					return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_ALL);
				}
				newSupplementaryImpApplicationForm.setAddOrEdit("add");
			}  catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
			log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_ALL);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_ALL);
	}
	
	
	//raghu for regular exam application
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkRegularApplicationForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		newSupplementaryImpApplicationForm.setIsSupp(false);
		newSupplementaryImpApplicationForm.resetFields();
		setDataToFormForRegular(newSupplementaryImpApplicationForm,request);
		
		if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getId()==1){
			return mapping.findForward(CMSConstants.INIT_REGULAR_APP_STUDENT_RESULTUG);
		}else{
			return mapping.findForward(CMSConstants.INIT_REGULAR_APP_STUDENT_RESULTPG);
		}
		
	}
	
	
	/**
	 * @param newSupplementaryImpApplicationForm
	 * @param request
	 * @throws Exception
	 */
	private void setDataToFormForRegular( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm, HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession(true);
		newSupplementaryImpApplicationForm.setStudentId(Integer.parseInt(session.getAttribute("studentId").toString()));
		ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
		Student student=(Student) txn.getMasterEntryDataById(Student.class,newSupplementaryImpApplicationForm.getStudentId());
		newSupplementaryImpApplicationForm.setStudentObj(student);
		boolean extendedTrue=NewSupplementaryImpApplicationHandler.getInstance().extendedDateForRegular(student.getClassSchemewise().getClasses().getId(), newSupplementaryImpApplicationForm);
		if(extendedTrue){
			newSupplementaryImpApplicationForm.setExtended(true);
		}
		List<Integer> examIds=NewSupplementaryImpApplicationHandler.getInstance().checkRegularAppAvailable(student.getClassSchemewise().getClasses().getId(),extendedTrue);
		IDownloadHallTicketTransaction txn1=DownloadHallTicketTransactionImpl.getInstance();
		INewSupplementaryImpApplicationTransaction supTxn=NewSupplementaryImpApplicationTransactionImpl.getInstance();
		boolean isSubmitted=supTxn.checkDuplication(newSupplementaryImpApplicationForm);
		
		if(isSubmitted)
		{
			List examRegularAppList=supTxn.getRegularApp(newSupplementaryImpApplicationForm);
			if(!examRegularAppList.isEmpty()){
				ExamRegularApplication examReglarApplication=(ExamRegularApplication)examRegularAppList.get(0);
				if(examReglarApplication.getChallanNo()!=null)
				newSupplementaryImpApplicationForm.setChalanNo(examReglarApplication.getChallanNo());
			}
			newSupplementaryImpApplicationForm.setIsSubmitted(true);
		}
		else
		{
			newSupplementaryImpApplicationForm.setIsSubmitted(false);
		}
		if(examIds.isEmpty()){
		newSupplementaryImpApplicationForm.setRegularAppAvailable(false);
		}
		
		else{
			ExamBlockUnblockHallTicketBO h=null;
			for (Integer examId: examIds) {
				h=txn1.isHallTicketBlockedStudent(student.getId(), student.getClassSchemewise().getClasses().getId(), examId,"A");
			}
			if(h!=null){
				newSupplementaryImpApplicationForm.setRegularAppAvailable(false);
			}else{
				
			
			
			newSupplementaryImpApplicationForm.setRegularAppAvailable(true);
			NewSupplementaryImpApplicationHandler.getInstance().getApplicationFormsForRegular(examIds,newSupplementaryImpApplicationForm);
			
			
			if(student!=null){
				newSupplementaryImpApplicationForm.setNameOfStudent(student.getAdmAppln().getPersonalData().getFirstName()+(student.getAdmAppln().getPersonalData().getMiddleName()!=null?student.getAdmAppln().getPersonalData().getMiddleName():"")+(student.getAdmAppln().getPersonalData().getLastName()!=null?student.getAdmAppln().getPersonalData().getLastName():""));
				newSupplementaryImpApplicationForm.setClassName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getName():"");
				newSupplementaryImpApplicationForm.setRegNo(student.getRegisterNo());
				newSupplementaryImpApplicationForm.setRollNo(student.getRollNo());
				newSupplementaryImpApplicationForm.setDob(null);
				newSupplementaryImpApplicationForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
				newSupplementaryImpApplicationForm.setAddress("");
				newSupplementaryImpApplicationForm.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
				newSupplementaryImpApplicationForm.setSchemeNo(""+student.getClassSchemewise().getClasses().getTermNumber());
				newSupplementaryImpApplicationForm.setEmail(student.getAdmAppln().getPersonalData().getEmail());
				newSupplementaryImpApplicationForm.setMobileNo(student.getAdmAppln().getPersonalData().getMobileNo2());
				newSupplementaryImpApplicationForm.setCourseId(""+student.getClassSchemewise().getClasses().getCourse().getId());
				newSupplementaryImpApplicationForm.setDate(CommonUtil.getTodayDate());
				String address="";
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine1()+",";
				}
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine2()+",";
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId().getName()+",";
				}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";
					
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()+",";
				}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";
					
				}
				/*String chalanNo="";
				if(newSupplementaryImpApplicationForm.getProgramTypeId()!=null && newSupplementaryImpApplicationForm.getProgramTypeId().equals("1")){
					chalanNo="RG"+student.getRegisterNo()+student.getId();
				}
				else
				if(newSupplementaryImpApplicationForm.getExamId()!=null && student.getRegisterNo()!=null){
					chalanNo="RG"+student.getRegisterNo()+newSupplementaryImpApplicationForm.getExamId();
					newSupplementaryImpApplicationForm.setChalanNo(chalanNo);
				}*/
				
				newSupplementaryImpApplicationForm.setAddress(address);
			}
			
		}
			
		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitRegularApplication(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			boolean dup1=false;
			try{
			NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
			newSupplementaryImpApplicationForm.setIsSupp(false);
			
			boolean dup=NewSupplementaryImpApplicationHandler.getInstance().checkDuplication(newSupplementaryImpApplicationForm);
			if(!dup){
				dup1=NewSupplementaryImpApplicationHandler.getInstance().addAppliedStudent(newSupplementaryImpApplicationForm);
			}else{
				if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getId()==1){
						//return mapping.findForward("printDetailsforRegularUG");
					return mapping.findForward(CMSConstants.INIT_REGULAR_APP_STUDENT_RESULTUG);
				}else{
					//return mapping.findForward("printDetailsforRegularPG");
					return mapping.findForward(CMSConstants.INIT_REGULAR_APP_STUDENT_RESULTPG);
				}
			}
			if(dup1){
				newSupplementaryImpApplicationForm.setIsSubmitted(true);
				if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getId()==1){
					//return mapping.findForward("printDetailsforRegularUG");
					return mapping.findForward(CMSConstants.INIT_REGULAR_APP_STUDENT_RESULTUG);
				}else{
					//return mapping.findForward("printDetailsforRegularPG");
					return mapping.findForward(CMSConstants.INIT_REGULAR_APP_STUDENT_RESULTPG);
				}
			}else{
				return mapping.findForward("printDetailsforRegularFail");
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
			
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPrintChallanForRegular(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
		return mapping.findForward("printDetailsChallanRegular");
			
			
			
	}
	
	public ActionForward showPrintDetailsForRegular(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			boolean dup1=false;
			NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
			
			boolean dup=NewSupplementaryImpApplicationHandler.getInstance().checkDuplication(newSupplementaryImpApplicationForm);
			if(!dup){
				dup1=NewSupplementaryImpApplicationHandler.getInstance().addAppliedStudent(newSupplementaryImpApplicationForm);
			}else{
				if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getId()==1){
						return mapping.findForward("printDetailsforRegularUG");
					
				}else{
					return mapping.findForward("printDetailsforRegularPG");
					
				}
			}
			if(dup1){
				newSupplementaryImpApplicationForm.setIsSubmitted(true);
				if(newSupplementaryImpApplicationForm.getStudentObj().getAdmAppln().getCourseBySelectedCourseId().getProgram().getProgramType().getId()==1){
					return mapping.findForward("printDetailsforRegularUG");
					
				}else{
					return mapping.findForward("printDetailsforRegularPG");
				}
			}else{
				return mapping.findForward("printDetailsforRegularFail");
			}
			
			
	}
	
	public ActionForward checkRevaluationApplicationForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		newSupplementaryImpApplicationForm.resetFields();
		newSupplementaryImpApplicationForm.resetFieldsForFees();
		HttpSession session=request.getSession(true);
		newSupplementaryImpApplicationForm.setProgramTypeName((String)session.getAttribute("programType").toString());
		newSupplementaryImpApplicationForm.setRevclassid(Integer.parseInt((String)session.getAttribute("revclassId").toString()));
		newSupplementaryImpApplicationForm.setRevExamId((String)session.getAttribute("revAppExamId").toString());
		newSupplementaryImpApplicationForm.setPrevSemNo(Integer.parseInt((String)session.getAttribute("prevSemNo").toString()));
		newSupplementaryImpApplicationForm.setPrintSupplementary(false);
		setDataToFormForRevaluation(newSupplementaryImpApplicationForm,request);
		
		SimpleDateFormat df = new SimpleDateFormat();
		newSupplementaryImpApplicationForm.setDateOfApplication(df.format(new Date()));
        
		return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT_RESULT_FIRST_PAGE);
	}
	
	
private void setDataToFormForRevaluation( NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm, HttpServletRequest request) throws Exception {
		
		try {
			
		
		HttpSession session=request.getSession(false);
		newSupplementaryImpApplicationForm.setStudentId(Integer.parseInt(session.getAttribute("studentId").toString()));
		newSupplementaryImpApplicationForm.setRevclassid(Integer.parseInt(session.getAttribute("revclassId").toString()));
		ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
		Student student=(Student) txn.getMasterEntryDataById(Student.class,newSupplementaryImpApplicationForm.getStudentId());
		newSupplementaryImpApplicationForm.setStudentObj(student);
		
		//boolean extendedTrue=NewSupplementaryImpApplicationHandler.getInstance().extendedDateForRevaluation(student.getClassSchemewise().getClasses().getId(), newSupplementaryImpApplicationForm);
		boolean extendedTrue=NewSupplementaryImpApplicationHandler.getInstance().extendedDateForRevaluation(newSupplementaryImpApplicationForm.getRevclassid(), newSupplementaryImpApplicationForm);
		
		if(extendedTrue){
			newSupplementaryImpApplicationForm.setExtended(true);
		}
		
		List<Integer> examIds=NewSupplementaryImpApplicationHandler.getInstance().checkRevaluationAppAvailable(newSupplementaryImpApplicationForm.getRevclassid(), newSupplementaryImpApplicationForm.isExtended(),newSupplementaryImpApplicationForm.isSupplementary());
		newSupplementaryImpApplicationForm.setCourseId(student.getAdmAppln().getCourseBySelectedCourseId().getId()+"");
		if(examIds.isEmpty()){
			newSupplementaryImpApplicationForm.setRevAppAvailable(false);
		}
		
		else{

			newSupplementaryImpApplicationForm.setRevAppAvailable(true);
			NewSupplementaryImpApplicationHandler.getInstance().getApplicationFormsForRevaluation(examIds,newSupplementaryImpApplicationForm);
			if(student!=null){								
				
				//get class based on exam
				ClassSchemewise classes=ClassEntryTransImpl.getInstance().getClassById(newSupplementaryImpApplicationForm.getRevclassid());
				
				//raghu write newly like regular app
				newSupplementaryImpApplicationForm.setNameOfStudent(student.getAdmAppln().getPersonalData().getFirstName()+(student.getAdmAppln().getPersonalData().getMiddleName()!=null?student.getAdmAppln().getPersonalData().getMiddleName():"")+(student.getAdmAppln().getPersonalData().getLastName()!=null?student.getAdmAppln().getPersonalData().getLastName():""));
				newSupplementaryImpApplicationForm.setClassName(classes.getClasses().getName());
				newSupplementaryImpApplicationForm.setRegisterNo(student.getRegisterNo());
				newSupplementaryImpApplicationForm.setRollNo(student.getRollNo());
				newSupplementaryImpApplicationForm.setDob(null);
				newSupplementaryImpApplicationForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
				newSupplementaryImpApplicationForm.setAddress("");
				newSupplementaryImpApplicationForm.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
				newSupplementaryImpApplicationForm.setSchemeNo(""+student.getClassSchemewise().getClasses().getTermNumber());
				newSupplementaryImpApplicationForm.setEmail(student.getAdmAppln().getPersonalData().getEmail());
				newSupplementaryImpApplicationForm.setMobileNo(student.getAdmAppln().getPersonalData().getMobileNo2());
				newSupplementaryImpApplicationForm.setCourseId(""+student.getClassSchemewise().getClasses().getCourse().getId());
				
				String address="";
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine1()+",";
				}
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine2()+",";
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId().getName();
				}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";
					
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()+",";
				}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";
					
				}
				
				newSupplementaryImpApplicationForm.setAddress(address);
			}
			
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/**
	 * @param newSupplementaryImpApplicationForm
	 * @param request
	 * @throws Exception
	 */
	
	public ActionForward getDataToFormForRevaluationChoose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form

		try {
	
		HttpSession session=request.getSession(false);
		newSupplementaryImpApplicationForm.resetFieldsForFees();
		newSupplementaryImpApplicationForm.setStudentId(Integer.parseInt(session.getAttribute("studentId").toString()));
		newSupplementaryImpApplicationForm.setRevclassid(Integer.parseInt(session.getAttribute("revclassId").toString()));
		
		ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
		Student student=(Student) txn.getMasterEntryDataById(Student.class,newSupplementaryImpApplicationForm.getStudentId());
		newSupplementaryImpApplicationForm.setStudentObj(student);
		
		boolean extendedTrue=NewSupplementaryImpApplicationHandler.getInstance().extendedDateForRevaluation(student.getClassSchemewise().getClasses().getId(), newSupplementaryImpApplicationForm);
		if(extendedTrue){
			newSupplementaryImpApplicationForm.setExtended(true);
		}
		
		List<Integer> examIds=NewSupplementaryImpApplicationHandler.getInstance().checkRevaluationAppAvailable(newSupplementaryImpApplicationForm.getRevclassid(), newSupplementaryImpApplicationForm.isExtended(),newSupplementaryImpApplicationForm.isSupplementary());
		newSupplementaryImpApplicationForm.setCourseId(student.getAdmAppln().getCourseBySelectedCourseId().getId()+"");
		
		if(examIds.isEmpty()){
		newSupplementaryImpApplicationForm.setRevAppAvailable(false);
		}
		
		else{

			newSupplementaryImpApplicationForm.setRevAppAvailable(true);
			
			INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
			String query="from ExamRevaluationApplicationNew er where er.classes.id="+newSupplementaryImpApplicationForm.getRevclassid()+
			" and er.exam.id="+examIds.get(0)+" and er.student.id= "+newSupplementaryImpApplicationForm.getStudentId();
			
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

			if(boList.size()!=0){
				NewSupplementaryImpApplicationHandler.getInstance().getApplicationFormsForRevaluationChooseType(examIds,newSupplementaryImpApplicationForm);
				
			}else{
				NewSupplementaryImpApplicationHandler.getInstance().getApplicationFormsForRevaluation(examIds,newSupplementaryImpApplicationForm);
				
			}
			
			if(student!=null){
				//get class based on exam
				ClassSchemewise classes=ClassEntryTransImpl.getInstance().getClassById(newSupplementaryImpApplicationForm.getRevclassid());
				
				//setting data for print
				
		/*		if(NewSupplementaryImpApplicationHandler.getInstance().checkOnlinePayment(newSupplementaryImpApplicationForm)){
					newSupplementaryImpApplicationForm.setPrintSupplementary(true);
					
				}else{
					newSupplementaryImpApplicationForm.setPrintSupplementary(false);
					
				}*/
				
				
				
				//raghu write newly like regular app
				newSupplementaryImpApplicationForm.setNameOfStudent(student.getAdmAppln().getPersonalData().getFirstName()+(student.getAdmAppln().getPersonalData().getMiddleName()!=null?student.getAdmAppln().getPersonalData().getMiddleName():"")+(student.getAdmAppln().getPersonalData().getLastName()!=null?student.getAdmAppln().getPersonalData().getLastName():""));
				newSupplementaryImpApplicationForm.setClassName(classes.getClasses().getName());
				newSupplementaryImpApplicationForm.setRegisterNo(student.getRegisterNo());
				newSupplementaryImpApplicationForm.setRollNo(student.getRollNo());
				newSupplementaryImpApplicationForm.setDob(null);
				newSupplementaryImpApplicationForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
				newSupplementaryImpApplicationForm.setAddress("");
				newSupplementaryImpApplicationForm.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
				newSupplementaryImpApplicationForm.setSchemeNo(""+student.getClassSchemewise().getClasses().getTermNumber());
				newSupplementaryImpApplicationForm.setEmail(student.getAdmAppln().getPersonalData().getEmail());
				newSupplementaryImpApplicationForm.setMobileNo(student.getAdmAppln().getPersonalData().getMobileNo2());
				newSupplementaryImpApplicationForm.setCourseId(""+student.getClassSchemewise().getClasses().getCourse().getId());
				
				String address="";
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine1()+",";
				}
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine2()+",";
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId().getName();
				}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";
					
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()+",";
				}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";
					
				}
				
				newSupplementaryImpApplicationForm.setAddress(address);
			}
			
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(newSupplementaryImpApplicationForm.getAppliedAlready() && !newSupplementaryImpApplicationForm.isPrintSupplementary()){
			ActionMessages messages = new ActionMessages();
			messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.revaluationApplication.appliedalready"));
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT_RESULT_FIRST_PAGE);
		}

	

		return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT_RESULT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addRevaluationApplicationForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewSupplementaryImpApplicationAction - saveMarks");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		setUserId(request,newSupplementaryImpApplicationForm);
		if (errors.isEmpty()) {
			try {
				
				//checl atleast one subject select or not
				int select=0;
				List<SupplementaryAppExamTo> examList=newSupplementaryImpApplicationForm.getMainList();
				for (SupplementaryAppExamTo suppTo : examList) {
					List<SupplementaryApplicationClassTo> classTos=suppTo.getExamList();
					for (SupplementaryApplicationClassTo cto: classTos) {
						List<ExamSupplementaryImpApplicationSubjectTO> listSubject = cto.getToList();
						for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
							if(!to.isTempChecked())
								if(to.getIsApplied())
									select++;	

						}
					}
				}
				
				if(select==0){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.supplementary.message","Please Select atleast one apply exam type "));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT_RESULT);
				}
				else{
					double totalTheoryFees =0;
					if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){
						 totalTheoryFees = newSupplementaryImpApplicationForm.getRevaluationFees()*select +newSupplementaryImpApplicationForm.getApplicationFees();
						newSupplementaryImpApplicationForm.setApplicationAmount(String.valueOf(totalTheoryFees));
						newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalTheoryFees));

					}
					if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
						 totalTheoryFees = newSupplementaryImpApplicationForm.getScrutinyFees()*select +newSupplementaryImpApplicationForm.getApplicationFees();
						newSupplementaryImpApplicationForm.setApplicationAmount(String.valueOf(totalTheoryFees));
						newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalTheoryFees));

					}
					if(newSupplementaryImpApplicationForm.getProgramTypeName().equalsIgnoreCase("PG")){
						 totalTheoryFees = newSupplementaryImpApplicationForm.getChallengeRevaluationFees()*select +newSupplementaryImpApplicationForm.getApplicationFees();
						newSupplementaryImpApplicationForm.setApplicationAmount(String.valueOf(totalTheoryFees));
						newSupplementaryImpApplicationForm.setTotalFeesInWords(CommonUtil.numberToWord((int)totalTheoryFees));

					}
					newSupplementaryImpApplicationForm.setTotalFees(totalTheoryFees-newSupplementaryImpApplicationForm.getApplicationFees());


					
				}
				
				if(select==0){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.supplementary.message","Please Select atleast one subject"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT_RESULT);
				}
				
				//checl atleast one revaluation select or not
				select=0;
				if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation")){
					select++;	
				}
				if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
					select++;	
				}
				if(newSupplementaryImpApplicationForm.getProgramTypeName().equalsIgnoreCase("PG")){
					select++;	
				}
			
			
				
				
				newSupplementaryImpApplicationForm.setPrintSupplementary(false);
				String msg="";
				boolean isSavedChallanNo=false;
				if(newSupplementaryImpApplicationForm.getStudentObj().getRegisterNo()!=null){
					if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("revaluation"))

					newSupplementaryImpApplicationForm.setChallanNo("REV"+newSupplementaryImpApplicationForm.getStudentObj().getRegisterNo()+newSupplementaryImpApplicationForm.getRevExamId());
					else if(newSupplementaryImpApplicationForm.getIsRevaluation()!=null && newSupplementaryImpApplicationForm.getIsRevaluation().equalsIgnoreCase("scrutiny")){
						newSupplementaryImpApplicationForm.setChallanNo("SCR"+newSupplementaryImpApplicationForm.getStudentObj().getRegisterNo()+newSupplementaryImpApplicationForm.getRevExamId());

					}
					else
						newSupplementaryImpApplicationForm.setChallanNo("CHV"+newSupplementaryImpApplicationForm.getStudentObj().getRegisterNo()+newSupplementaryImpApplicationForm.getRevExamId());


				}
				boolean isSaved=NewSupplementaryImpApplicationHandler.getInstance().saveRevaluationApplicationForStudentLogin(newSupplementaryImpApplicationForm);
				if(isSaved){
					isSavedChallanNo = NewSupplementaryImpApplicationHandler.getInstance().saveRevaluationAppWithChallanNos(newSupplementaryImpApplicationForm);
				}
				msg=newSupplementaryImpApplicationForm.getMsg();
				if(isSaved && isSavedChallanNo){
					
					//newSupplementaryImpApplicationForm.resetFields();
					
					getDataToFormForRevaluationChoose(mapping, newSupplementaryImpApplicationForm, request, response);
					
					newSupplementaryImpApplicationForm.setDisplayButton(false);
					newSupplementaryImpApplicationForm.setPrintSupplementary(false);
					newSupplementaryImpApplicationForm.setChallanButton(true);
				
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.revaluation.added.success"));
					
					saveMessages(request, messages);
					
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Supplementary/Improvement submission was not successfull, Reason:"+msg));
					saveErrors(request, errors);
				}
			}  catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit NewSupplementaryImpApplicationAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT_RESULT);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - saveMarks");
		return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT_RESULT);
	}
	
	
	public ActionForward showPrintDetailsForRevalution(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
			newSupplementaryImpApplicationForm.setPrintSupplementary(false);
			
			return mapping.findForward("printDetailsforRevaluation");
			
	}
	

	public ActionForward showPrintChallanForRevaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
		
				return mapping.findForward("printDetailsChallanRevaluation");
				
	}
	
	public ActionForward initRevaluationApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			log.info("Entered initSupplementaryImpApplication");
			NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
			newSupplementaryImpApplicationForm.resetFields();
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);
			log.info("Exit initSupplementaryImpApplication");
			
			return mapping.findForward(CMSConstants.INIT_REVALUATION_APP);
		}
	
	public ActionForward getCandidatesForRevaluation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		 ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		validateCandidate(newSupplementaryImpApplicationForm,errors);
		
		if (errors.isEmpty()) {
			try {
				String query = "from ExamDefinitionBO e where e.id="+newSupplementaryImpApplicationForm.getExamId();
				ExamDefinitionBO def = (ExamDefinitionBO) PropertyUtil.getDataForUniqueObject(query);
				newSupplementaryImpApplicationForm.setAcademicYear(String.valueOf(def.getAcademicYear()));
				
				Map<String,ExamSupplementaryImpApplicationTO> toMap=NewSupplementaryImpApplicationHandler.getInstance().getStudentListForRevaluation(newSupplementaryImpApplicationForm);
				if (toMap.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
					return mapping.findForward(CMSConstants.INIT_REVALUATION_APP);
				}
				newSupplementaryImpApplicationForm.setToMap(toMap);
				setNameToForm(newSupplementaryImpApplicationForm);
			}  catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
			log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_REVALUATION_APP);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_RESULT);
	}
	
	// for revaluation
	
	public ActionForward editStudentForRevaluation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		 ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				Map<String,ExamSupplementaryImpApplicationTO> stuMap=newSupplementaryImpApplicationForm.getToMap();
				ExamSupplementaryImpApplicationTO to=stuMap.get(newSupplementaryImpApplicationForm.getStudent());
				newSupplementaryImpApplicationForm.setRevclassid(to.getClassId());
				newSupplementaryImpApplicationForm.setStudentId(to.getStudentId());
				NewSupplementaryImpApplicationHandler.getInstance().setDatatoToForRevaluation(to);
				newSupplementaryImpApplicationForm.setSuppTo(to);
				newSupplementaryImpApplicationForm.setAddOrEdit("edit");
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
			log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_RESULT);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT);
	}
	
	
	public ActionForward addRevaluationApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.info("Entered NewExamMarksEntryAction - saveMarks");
	
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		setUserId(request,newSupplementaryImpApplicationForm);
		if (errors.isEmpty()) {
			try {
				boolean isSaved=NewSupplementaryImpApplicationHandler.getInstance().saveRevaluationApplication(newSupplementaryImpApplicationForm);
				if(isSaved){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.updatesuccess","Student Revaluation Application"));
					saveMessages(request, messages);
					newSupplementaryImpApplicationForm.resetFields();
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Student Revaluation Application"));
					saveErrors(request, errors);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT);
		}
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		if(newSupplementaryImpApplicationForm.getAddOrEdit().equals("add")){
			newSupplementaryImpApplicationForm.setAddOrEdit(null);
			return mapping.findForward(CMSConstants.INIT_REVALUATION_APP);
		}else{
			return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_RESULT);
		}
	}
	
	public ActionForward cancelRevaluationApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_RESULT);
	}
	
	public ActionForward deleteRevaluationApp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		
		if (errors.isEmpty()) {
			try {
				Map<String,ExamSupplementaryImpApplicationTO> stuMap=newSupplementaryImpApplicationForm.getToMap();
				ExamSupplementaryImpApplicationTO to=stuMap.get(newSupplementaryImpApplicationForm.getStudent());
				boolean isDelete=NewSupplementaryImpApplicationHandler.getInstance().deleteRevaluationApp(to,newSupplementaryImpApplicationForm);
				if(isDelete){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.deletesuccess","Student Revaluation Application"));
					saveMessages(request, messages);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("kknowledgepro.admin.addfailure","Student Revaluation Application"));
					saveErrors(request, errors);
				}
				Map<String,ExamSupplementaryImpApplicationTO> toMap=NewSupplementaryImpApplicationHandler.getInstance().getStudentListForRevaluation(newSupplementaryImpApplicationForm);
				newSupplementaryImpApplicationForm.setToMap(toMap);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newSupplementaryImpApplicationForm);			
			log.info("Exit NewSupplementaryImpApplicationAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_RESULT);
		}
		log.info("Entered NewSupplementaryImpApplicationAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_SUPPL_IMP_APP_RESULT);
	}
	public ActionForward checkRevaluationApplicationForSuppl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;// Type casting the Action form to Required Form
		newSupplementaryImpApplicationForm.resetFields();
		newSupplementaryImpApplicationForm.resetFieldsForFees();
		HttpSession session=request.getSession(true);
		newSupplementaryImpApplicationForm.setProgramTypeName((String)session.getAttribute("programType").toString());
		newSupplementaryImpApplicationForm.setRevclassid(Integer.parseInt(newSupplementaryImpApplicationForm.getSuppClassId().trim()));
		newSupplementaryImpApplicationForm.setRevExamId(newSupplementaryImpApplicationForm.getSuppExamId());
		newSupplementaryImpApplicationForm.setPrintSupplementary(false);
		newSupplementaryImpApplicationForm.setSupplementary(true);
		session.setAttribute("revclassId", newSupplementaryImpApplicationForm.getSuppClassId().trim());
		newSupplementaryImpApplicationForm.setExamId(newSupplementaryImpApplicationForm.getSuppExamId());
		setDataToFormForRevaluation(newSupplementaryImpApplicationForm,request);
		
		SimpleDateFormat df = new SimpleDateFormat();
		newSupplementaryImpApplicationForm.setDateOfApplication(df.format(new Date()));
	    
		return mapping.findForward(CMSConstants.INIT_REVALUATION_APP_STUDENT_RESULT_FIRST_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * @author Arun Sudhakaran
	 */
	public ActionForward checkInternalRedoImpApplicationForStudentLogin(ActionMapping mapping, 
																		ActionForm form, 
																		HttpServletRequest request, 
																		HttpServletResponse response) throws Exception {
		
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
		newSupplementaryImpApplicationForm.setIsSupp(true);
		String examId=newSupplementaryImpApplicationForm.getExamId();
		newSupplementaryImpApplicationForm.resetFields();
		newSupplementaryImpApplicationForm.setExamId(examId);

		try{
			setDataToFormForInternalRedo(newSupplementaryImpApplicationForm,request);
		}
		catch (Exception e) {
			newSupplementaryImpApplicationForm.setIntRedoAppAvailable(false);
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.STUDENT_INTERNAL_REDO_APP_PAGE);
	}
	
	/**
	 * @param newSupplementaryImpApplicationForm
	 * @param request
	 * @throws Exception
	 * @author Arun Sudhakaran
	 */
	private void setDataToFormForInternalRedo(NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm, 
											  HttpServletRequest request) throws Exception {
		
		HttpSession session=request.getSession(true);
		newSupplementaryImpApplicationForm.setStudentId(Integer.parseInt(session.getAttribute("studentId").toString()));
		
		ISingleFieldMasterTransaction txn1=SingleFieldMasterTransactionImpl.getInstance();
		Student student=(Student) txn1.getMasterEntryDataById(Student.class,newSupplementaryImpApplicationForm.getStudentId());
		newSupplementaryImpApplicationForm.setStudentObj(student);
		
		
		Boolean extendedTrue=NewSupplementaryImpApplicationHandler.getInstance().checkInternalRedooDateExtended(newSupplementaryImpApplicationForm.getStudentId(), newSupplementaryImpApplicationForm);
		
		List<Integer> examIds = (List<Integer>)session.getAttribute("internalRedoExamIds");
		if(examIds == null || examIds.isEmpty())
			examIds = NewSupplementaryImpApplicationHandler.getInstance().checkInternalRedoAppAvailable(newSupplementaryImpApplicationForm.getStudentId());
		
		// set app no and date
		NewSupplementaryImpApplicationHandler.getInstance().setAppNoAndDate(newSupplementaryImpApplicationForm); 
	
		if(examIds.isEmpty() || examIds!=null && !examIds.contains(Integer.parseInt(newSupplementaryImpApplicationForm.getExamId()))){
			newSupplementaryImpApplicationForm.setIntRedoAppAvailable(false);
		}
		else{
			
			newSupplementaryImpApplicationForm.setIntRedoAppAvailable(true);
			NewSupplementaryImpApplicationHandler.getInstance().getApplicationFormsForInternalRedo(extendedTrue,examIds,newSupplementaryImpApplicationForm);
			
			if(student!=null){
				
				newSupplementaryImpApplicationForm.setNameOfStudent(student.getAdmAppln().getPersonalData().getFirstName()+(student.getAdmAppln().getPersonalData().getMiddleName()!=null?student.getAdmAppln().getPersonalData().getMiddleName():"")+(student.getAdmAppln().getPersonalData().getLastName()!=null?student.getAdmAppln().getPersonalData().getLastName():""));
				newSupplementaryImpApplicationForm.setClassName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getName():"");
				newSupplementaryImpApplicationForm.setRegNo(student.getRegisterNo());
				newSupplementaryImpApplicationForm.setDob(null);
				newSupplementaryImpApplicationForm.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
				newSupplementaryImpApplicationForm.setAddress("");
				newSupplementaryImpApplicationForm.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
				newSupplementaryImpApplicationForm.setSchemeNo(""+student.getClassSchemewise().getClasses().getTermNumber());
				newSupplementaryImpApplicationForm.setEmail(student.getAdmAppln().getPersonalData().getEmail());
				newSupplementaryImpApplicationForm.setMobileNo(student.getAdmAppln().getPersonalData().getMobileNo2());
				newSupplementaryImpApplicationForm.setCourseId(""+student.getClassSchemewise().getClasses().getCourse().getId());
				
				String address="";
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine1()+",";
				}
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressLine2()+",";
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressDistrictId().getName();
				}
				else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";					
				}
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null ){
					address=address+""+student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()+",";
				}
				else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers().equalsIgnoreCase("")){
					address=address+""+student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()+",";					
				}
				
				newSupplementaryImpApplicationForm.setAddress(address);
								
				//getting instructions
				String programTypeId=	NewSecuredMarksEntryHandler.getInstance().getPropertyValue(student.getClassSchemewise().getClasses().getId(),"Classes",true,"course.program.programType.id");
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
			
			newSupplementaryImpApplicationForm.setChalanNo("IRE"+newSupplementaryImpApplicationForm.getRegNo()+newSupplementaryImpApplicationForm.getExamId());
		}		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Arun Sudhakaran
	 */
	public ActionForward calculateAmountForInternalRedo(ActionMapping mapping, 
														ActionForm form, 
														HttpServletRequest request,
														HttpServletResponse response) throws Exception {
		
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
		newSupplementaryImpApplicationForm.setIsSupp(true);
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		setUserId(request,newSupplementaryImpApplicationForm);
		boolean isChecked=false;
		
		if (errors.isEmpty()) {
			double amount=0;
			try {
				
				List<SupplementaryAppExamTo> examList=newSupplementaryImpApplicationForm.getMainList();
				for (SupplementaryAppExamTo suppTo : examList) {
					List<SupplementaryApplicationClassTo> classTos=suppTo.getExamList();
					for (SupplementaryApplicationClassTo cto: classTos) {
						List<ExamSupplementaryImpApplicationSubjectTO> listSubject = cto.getToList();
						for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
							if(!to.isTempChecked()) {
								if(to.getIsCIAAppearedTheory()){
									amount += newSupplementaryImpApplicationForm.getCvCampFees() +
											  newSupplementaryImpApplicationForm.getPaperFees();	
									isChecked=true;
								}
							}
							if(!to.isTempPracticalChecked()) {
								if(to.getIsCIAAppearedPractical()){
									amount += newSupplementaryImpApplicationForm.getCvCampFees() +
											  newSupplementaryImpApplicationForm.getPaperFees();
									isChecked=true;
								}
							}
						}
					}
				}
				
				if(amount==0 && !isChecked) {
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.supplementary.message","Please Select atleast one subject"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.STUDENT_INTERNAL_REDO_APP_PAGE);
				}
				
				amount += newSupplementaryImpApplicationForm.getApplicationFees() +
						  newSupplementaryImpApplicationForm.getRegistrationFees() +
						  newSupplementaryImpApplicationForm.getMarksListFees();
				
				newSupplementaryImpApplicationForm.setTotalFees(amount);	
			}
			catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.STUDENT_INTERNAL_REDO_APP_PAGE);
		}
		
		return mapping.findForward(CMSConstants.STUDENT_INTERNAL_REDO_SUBMIT);
	}
	
	public ActionForward addInternalRedoApplicationForStudentLogin(ActionMapping mapping, 
																   ActionForm form, 
																   HttpServletRequest request,
																   HttpServletResponse response) throws Exception {
		
		NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm=(NewSupplementaryImpApplicationForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newSupplementaryImpApplicationForm.validate(mapping, request);
		setUserId(request,newSupplementaryImpApplicationForm);
		
		if (errors.isEmpty()) {
			try {
				
				HttpSession session=request.getSession(true);
				newSupplementaryImpApplicationForm.setPrintSupplementary(false);
				String msg="";
				
				boolean isSaved=NewSupplementaryImpApplicationHandler.getInstance()
																	 .saveInternalRedoApplicationForStudentLogin(newSupplementaryImpApplicationForm);
				msg=newSupplementaryImpApplicationForm.getMsg();
				if(isSaved){
					
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.certificate.added.success.online"));
					saveMessages(request, messages);
					
					boolean extendedTrue=NewSupplementaryImpApplicationHandler.getInstance().checkInternalRedooDateExtended(newSupplementaryImpApplicationForm.getStudentId(), newSupplementaryImpApplicationForm);
					List<Integer> examIds = (List<Integer>)session.getAttribute("internalRedoExamIds");
					if(examIds == null || examIds.isEmpty())
						examIds = NewSupplementaryImpApplicationHandler.getInstance().checkInternalRedoAppAvailable(newSupplementaryImpApplicationForm.getStudentId());
					NewSupplementaryImpApplicationHandler.getInstance().getApplicationFormsForInternalRedo(extendedTrue, examIds, newSupplementaryImpApplicationForm);
					
					String printData=NewSupplementaryImpApplicationHandler.getInstance().getPrintData(newSupplementaryImpApplicationForm.getOnlinePaymentId(),request);
					String examId=newSupplementaryImpApplicationForm.getExamId();
					
					newSupplementaryImpApplicationForm.resetFields();					
					newSupplementaryImpApplicationForm.setExamId(examId);
					newSupplementaryImpApplicationForm.setPrintData(printData);
					newSupplementaryImpApplicationForm.setPrintSupplementary(true);
					
					setDataToFormForInternalRedo(newSupplementaryImpApplicationForm,request);
				}
				else{
					if(msg==null || msg.isEmpty()){
						msg="Payment Failed";
					}
					
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Inernal Redo submission was not successfull, Reason:"+msg));
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Kindly rectify the errors mentioned and re-submit the application" ));
					saveErrors(request, errors);
				}
			}  
			catch (Exception exception) {
				exception.printStackTrace();
				String msg = super.handleApplicationException(exception);
				newSupplementaryImpApplicationForm.setErrorMessage(msg);
				newSupplementaryImpApplicationForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.STUDENT_INTERNAL_REDO_APP_PAGE);
		}
		return mapping.findForward(CMSConstants.STUDENT_INTERNAL_REDO_APP_PAGE);
	}

}