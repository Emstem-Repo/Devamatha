package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.forms.admission.DDStatusForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.ApplicationEditHandler;
import com.kp.cms.handlers.admission.DDStatusHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentCourseAllotmentTo;
import com.kp.cms.to.admission.DDStatusTO;
import com.kp.cms.transactions.admission.IDDStatusTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class DDStatusAction extends BaseDispatchAction {
	private static final String TO_DATEFORMAT="MM/dd/yyyy";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";	
	private static final Log log = LogFactory.getLog(DDStatusAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDDStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initDDStatus");
		DDStatusForm dDStatusForm = (DDStatusForm) form;
		dDStatusForm.resetFields();
		log.info("Exit initDDStatus");
		
		return mapping.findForward(CMSConstants.INIT_DD_STATUS);
	}
	
	/**
	 * Performs the add Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward updateDDStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DDStatusForm dDStatusForm = (DDStatusForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = dDStatusForm.validate(mapping, request);
		if(dDStatusForm.getRecievedDDDate()!=null && !dDStatusForm.getRecievedDDDate().isEmpty()){
		if(CommonUtil.isValidDate(dDStatusForm.getRecievedDDDate())){
			boolean	isValid = validatefutureDate(dDStatusForm.getRecievedDDDate());
			if(!isValid){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.dd.status.recievedDate.large"));
			}
		}
		}
		boolean isAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, dDStatusForm);
			boolean checkStudent=DDStatusHandler.getInstance().checkStudent(dDStatusForm);
			if(!checkStudent){
				errors.add("error", new ActionError("knowledgepro.admission.ddStatus.student.notExists"));
			}
			else{
				boolean isAlreadyEnterd=DDStatusHandler.getInstance().getAlreadyEntered(dDStatusForm);
				if(isAlreadyEnterd){
					errors.add("error", new ActionError("knowledgepro.admission.ddStatus.student.exists"));
				}
			}
			boolean checkDDExists=DDStatusHandler.getInstance().checkDDExists(dDStatusForm);
			if(checkDDExists){
				errors.add("error", new ActionError("knowledgepro.admission.ddStatus.student.ddExists"));
			}
			if(errors.isEmpty()){
				isAdded = DDStatusHandler.getInstance().updateDDStatus(dDStatusForm);
				if (isAdded) {
					ActionMessage message = new ActionMessage("knowledgepro.admissionForm.update.dd.status.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					dDStatusForm.resetFields();
				}else{
					// failed
					errors.add("error", new ActionError("knowledgepro.admissionForm.update.dd.status.failure"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_DD_STATUS);
			}
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				dDStatusForm.setErrorMessage(msg);
				dDStatusForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_DD_STATUS);
		}
		return mapping.findForward(CMSConstants.INIT_DD_STATUS);
	}
	/**
	 * future date validation
	 * @param dateString
	 * @return
	 */
	private boolean validatefutureDate(String dateString) {
		log.info("enter validatefutureDate..");
			String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, DDStatusAction.FROM_DATEFORMAT,DDStatusAction.TO_DATEFORMAT);
			Date date = new Date(formattedString);
			Date curdate = new Date();
			Calendar cal= Calendar.getInstance();
			cal.setTime(curdate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date origdate=cal.getTime();
			log.info("exit validatefutureDate..");
			return !(date.compareTo(origdate) == 1);
	}
	
	
	//raghu
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initChallanStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initDDStatus");
		DDStatusForm dDStatusForm = (DDStatusForm) form;
		dDStatusForm.resetFields();
		log.info("Exit initDDStatus");
		
		return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS);
	}
	
	
	
	/**
	 * Performs the add Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward updateChallanStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DDStatusForm dDStatusForm = (DDStatusForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = dDStatusForm.validate(mapping, request);
		if(dDStatusForm.getRecievedChallanDate()!=null && !dDStatusForm.getRecievedChallanDate().isEmpty()){
		if(CommonUtil.isValidDate(dDStatusForm.getRecievedChallanDate())){
			boolean	isValid = validatefutureDate(dDStatusForm.getRecievedChallanDate());
			if(!isValid){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.challan.status.recievedDate.large"));
			}
		}
		}
		boolean isAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, dDStatusForm);
			boolean checkStudent=DDStatusHandler.getInstance().checkStudent(dDStatusForm);
			if(!checkStudent){
				errors.add("error", new ActionError("knowledgepro.admission.challanStatus.student.notExists"));
			}
			else{
				boolean isAlreadyEnterd=DDStatusHandler.getInstance().getAlreadyEntered(dDStatusForm);
				if(isAlreadyEnterd){
					errors.add("error", new ActionError("knowledgepro.admission.challanStatus.student.exists"));
				}
			}
			boolean checkChallanExists=DDStatusHandler.getInstance().checkChallanExists(dDStatusForm);
			if(checkChallanExists){
				errors.add("error", new ActionError("knowledgepro.admission.challanStatus.student.challanExists"));
			}
			if(errors.isEmpty()){
				isAdded = DDStatusHandler.getInstance().updateChallanStatus(dDStatusForm);
				if (isAdded) {
					ActionMessage message = new ActionMessage("knowledgepro.admissionForm.update.challan.status.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					dDStatusForm.resetFields();
				}else{
					// failed
					errors.add("error", new ActionError("knowledgepro.admissionForm.update.challan.status.failure"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS);
			}
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				dDStatusForm.setErrorMessage(msg);
				dDStatusForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS);
		}
		return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS);
	}
	
	
	
	
	
	
	//at
	public ActionForward initChallanStatusOnCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DDStatusForm ddForm=(DDStatusForm) form;
		ddForm.setProgramId("");
		ddForm.setProgramTypeId("");
		ddForm.setCourseId("");
		ddForm.setDdStatusList(null);
		try {
			setUserId(request, ddForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			ddForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE);
	}
	

	
	
	// getting students for challan
	public ActionForward getStudentsChallanStatusOnCourse(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
		DDStatusForm ddForm = (DDStatusForm)form;
		ActionMessages errors =new ActionErrors();
		if (ddForm.getRecievedChallanDate()!=null && !ddForm.getRecievedChallanDate().isEmpty() ) {
			errors=ddForm.validate(mapping, request);
		}
		
		if (errors.isEmpty()) {
		
		try 
		{
			ddForm.setDdStatusList(null);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE);
			}
			
			
			List<DDStatusTO> studentList=new ArrayList();	
		if (ddForm.getProgramTypeId()!=null && !ddForm.getProgramTypeId().isEmpty()) {
			 studentList=DDStatusHandler.getInstance().getStudentsChallanStatusOnCourse(ddForm);
		}	
		
		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			ddForm.setProgramTypeList(programTypeList);
			return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE);
		}
		
		ddForm.setDdStatusList(studentList);
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		}
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE);
	}
	
	
	

	
	//at
	public ActionForward updateChallanStatusOnCourse(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		
	
		DDStatusForm ddForm=(DDStatusForm) form;
		
		
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		errors=ddForm.validate(mapping, request);
		validateChallanStatus(ddForm,errors);
		if (errors.isEmpty()) {
				
			try{
				
				
				//setting user id to form.
				setUserId(request, ddForm);				
				boolean isAdded=DDStatusHandler.getInstance().updateChallanStatusOnCourse(ddForm);
				
				if(!isAdded){
					errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE);
				}
				
			}catch (Exception e) {
				System.out.println("action  ======================================"+e);
				errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE);
			}
				
			
		} else{
			
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE);
		
		}
		
		
		
		return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_CONFIRM_PAGE_NEW);
	}
	
	
	private void validateChallanStatus(DDStatusForm admForm, ActionErrors errors) throws Exception{
		boolean isChecked=false;
		List<DDStatusTO> list = admForm.getDdStatusList();
		
		if (list != null && !list.isEmpty()) {
			
				Iterator<DDStatusTO> itr = list.iterator();
			
				int count = 0;
				
				while (itr.hasNext()) {
					
					DDStatusTO  allotmentTo = (DDStatusTO) itr.next();
				
				if (allotmentTo.getChecked()!=null && allotmentTo.getChecked().equalsIgnoreCase("on")) {
					isChecked=true;
					break;
				}
				
				}
		
				if(!isChecked){
					errors.add(CMSConstants.ERROR,new ActionError("interviewProcessForm.checkbox.select"));
				}
				
		}
		
	}
	
	

	
	
	private void validateChallanStatusOnCourse(DDStatusForm dDStatusForm, ActionErrors errors) throws Exception{
		boolean isChecked=false;
		List<DDStatusTO> list = dDStatusForm.getDdStatusList();
		
		if (list != null && !list.isEmpty()) {
			
				Iterator<DDStatusTO> itr = list.iterator();
			
				int count = 0;
				
				while (itr.hasNext()) {
					
					DDStatusTO  ddTo = (DDStatusTO) itr.next();
				
					if((ddTo.getRecievedChallanNo()!=null && !ddTo.getRecievedChallanNo().equalsIgnoreCase("")) || (ddTo.getRecievedChallanDate()!=null && !ddTo.getRecievedChallanDate().equalsIgnoreCase(""))){
											
					
					if(ddTo.getRecievedChallanDate()!=null && !ddTo.getRecievedChallanDate().isEmpty()){
					if(CommonUtil.isValidDate(ddTo.getRecievedChallanDate())){
						boolean	isValid = validatefutureDate(ddTo.getRecievedChallanDate());
						if(!isValid){
								errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.challan.status.course.recievedDate.large",ddTo.getStudentName()));
						}
					}
					}else{
								errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.challan.status.recievedDate.course",ddTo.getStudentName()));
						
					}
				
											
						
						/*boolean checkStudent=DDStatusHandler.getInstance().checkStudent(dDStatusForm);
						if(!checkStudent){
							errors.add("error", new ActionError("knowledgepro.admission.challanStatus.student.notExists"));
						}
						else{
							boolean isAlreadyEnterd=DDStatusHandler.getInstance().getAlreadyEntered(dDStatusForm);
							if(isAlreadyEnterd){
								errors.add("error", new ActionError("knowledgepro.admission.challanStatus.student.exists"));
							}
						}*/
					
						if(ddTo.getRecievedChallanNo()!=null && !ddTo.getRecievedChallanNo().isEmpty()){
						}else{
							errors.add("error", new ActionError("knowledgepro.admission.challan.status.recievedChallan.course",ddTo.getStudentName()));
							
						}
						
						dDStatusForm.setRecievedChallanNo(ddTo.getRecievedChallanNo());
						boolean checkChallanExists=DDStatusHandler.getInstance().checkChallanExists(dDStatusForm);
						if(checkChallanExists){
							errors.add("error", new ActionError("knowledgepro.admission.challanStatus.student.course.challanExists",ddTo.getStudentName()));
						}
						dDStatusForm.setRecievedChallanNo("");
					
						
					
					}//main if
				
				}
		
				
		}
		
	}
	
	
	//at
	public ActionForward initDDStatusOnCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DDStatusForm ddForm=(DDStatusForm) form;
		ddForm.setProgramId("");
		ddForm.setProgramTypeId("");
		ddForm.setCourseId("");
		ddForm.setDdStatusList(null);
		try {
			setUserId(request, ddForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			ddForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.INIT_DD_STATUS_ONCOURSE);
	}
	

	
	
	// getting students for DD
	public ActionForward getStudentsDDStatusOnCourse(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
		DDStatusForm ddForm = (DDStatusForm)form;
 	  
		ActionMessages errors=ddForm.validate(mapping, request);
		if (errors.isEmpty()) {
		
		try 
		{
			ddForm.setDdStatusList(null);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.INIT_DD_STATUS_ONCOURSE);
			}
			
			
			
			
		List<DDStatusTO> studentList=DDStatusHandler.getInstance().getStudentsDDStatusOnCourse(ddForm);
		
		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			ddForm.setProgramTypeList(programTypeList);
			return mapping.findForward(CMSConstants.INIT_DD_STATUS_ONCOURSE);
		}
		
		ddForm.setDdStatusList(studentList);
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		}
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_DD_STATUS_ONCOURSE);
	}
	
	
	

	
	//at
	public ActionForward updateDDStatusOnCourse(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		
	
		DDStatusForm ddForm=(DDStatusForm) form;
		
		
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		
		errors = ddForm.validate(mapping, request);
		validateChallanStatus(ddForm,errors);
		
		if (errors.isEmpty()) {
				
			try{
				
				
				//setting user id to form.
				setUserId(request, ddForm);				
				boolean isAdded=DDStatusHandler.getInstance().updateDDStatusOnCourse(ddForm);
				
				if(!isAdded){
					errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_DD_STATUS_ONCOURSE);
				}
				
			}catch (Exception e) {
				System.out.println("action  ======================================"+e);
				errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_DD_STATUS_ONCOURSE);
			}
				
			
		} else{
			
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_DD_STATUS_ONCOURSE);
		
		}
		
		
		
		return mapping.findForward(CMSConstants.INIT_DD_STATUS_ONCOURSE_CONFIRM_PAGE_NEW);
	}
	
	

	//at
	public ActionForward initChallanUploadProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DDStatusForm ddForm=(DDStatusForm) form;
		
		try {
			setUserId(request, ddForm);
			
		}  catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		ddForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS);
	}
	
	
	
	// getting students for DD
	public ActionForward updateChallanUploadProcess(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
		DDStatusForm ddForm = (DDStatusForm)form;
 	  
		ActionMessages errors=ddForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		if (errors.isEmpty()) {
		
		try 
		{
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS);
			}
			ddForm.setChalanNotVerified(null);
			ddForm.setChalanVerifiedCount(null);
		List<DDStatusTO> studentList=DDStatusHandler.getInstance().getStudentsChallanDtailsOnDate(ddForm);
		
		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS);
		}else{
			ddForm.setDdStatusList(studentList);
			boolean isAdded=DDStatusHandler.getInstance().updateChallanUploadProcess(ddForm);
			if(!isAdded){
				errors.add("admissionFormForm.faield",new ActionError("knowledgepro.admin.updateFailure"));
				
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS);
			}
			else
			{
				chalanRecivedNotRecievedCount(request, ddForm);
			}
		}
		
		
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		}
		
		ActionMessage message = new ActionMessage("knowledgepro.admin.updateSuccess");
		messages.add("messages", message);
		saveMessages(request, messages);
		ddForm.setRecievedChallanDate("");
		ddForm.setDdStatusList(null);
		ddForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS);
	}
	
	public void chalanRecivedNotRecievedCount(HttpServletRequest request,DDStatusForm ddform) throws Exception
	{
		Integer chalvercount=DDStatusHandler.getInstance().ChallanVerifiedCount(ddform);
		ddform.setChalanVerifiedCount(chalvercount.toString());
		Integer chalnotvercount=DDStatusHandler.getInstance().ChallanNotVerifiedCount(ddform);
		ddform.setChalanNotVerified(chalnotvercount.toString());
	}
	
	public ActionForward initChallanStatusOnCourseForExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DDStatusForm ddForm=(DDStatusForm) form;
		ddForm.setProgramId("");
		ddForm.setProgramTypeId("");
		ddForm.setCourseId("");
		ddForm.setDdStatusList(null);
		ddForm.resetFields();
		try {
			setUserId(request, ddForm);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			Map<Integer, String> examMap = new HashMap<Integer, String>();;
			ddForm.setProgramTypeList(programTypeList);
			int year=0;
			year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(ddForm.getYear()!=null && !ddForm.getYear().isEmpty()){
				year=Integer.parseInt(ddForm.getYear());
			}
			
			if(year==0){
				Calendar calendar = Calendar.getInstance();
				year = calendar.get(Calendar.YEAR);
			}
			examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(ddForm.getExamType(),year);
			ddForm.setExamMap(examMap);
			
			
			
		} catch (ApplicationException e) {
			log.error("error in initStudentEdit...", e);
			String msg = super.handleApplicationException(e);
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_FOR_EXAM);
	}
	

	
	
	// getting students for challan for exam
	public ActionForward getStudentsChallanStatusOnCourseForExam(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
		DDStatusForm ddForm = (DDStatusForm)form;
 	  
		ActionMessages errors=ddForm.validate(mapping, request);
		if (errors.isEmpty()) {
		
		try 
		{
			ddForm.setDdStatusList(null);
			if(ddForm.getExamid()==0){
				errors.add("error", new ActionError("admissionFormForm.education.exam.required"));
				saveErrors(request, errors);
				}
			if(ddForm.getClasses()==null || ddForm.getClasses().equals("")){
				errors.add("error", new ActionError("knowledgepro.attendance.activityattendence.class.required"));
				saveErrors(request, errors);
				}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_FOR_EXAM);
			}
			
			
			List<DDStatusTO> studentList = null;
		if(ddForm.getFlagType()!=null && (ddForm.getFlagType().equalsIgnoreCase("isRevaluation")|| 
				ddForm.getFlagType().equalsIgnoreCase("isScrutiny")|| ddForm.getFlagType().equalsIgnoreCase("isChallengeValuation")) ){	
		    studentList=DDStatusHandler.getInstance().getStudentsChallanStatusOnCourseForRevaluation(ddForm);
		    
		}
		else
		    studentList=DDStatusHandler.getInstance().getStudentsChallanStatusOnCourseForExam(ddForm);

		
		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			ddForm.setProgramTypeList(programTypeList);
			return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_FOR_EXAM);
		}
		
		ddForm.setDdStatusList(studentList);
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		}
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_FOR_EXAM);
	}
	
	public ActionForward updateChallanStatusOnCourseForExam(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
			
				DDStatusForm ddForm=(DDStatusForm) form;
				
				
				ActionErrors errors = new ActionErrors();
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				
				errors = ddForm.validate(mapping, request);
				validateChallanStatus(ddForm,errors);
				HttpSession session = request.getSession(false);
				if (errors.isEmpty()) {
						
					try{
						
						
						//setting user id to form.
						setUserId(request, ddForm);		
						boolean isAdded = false;
						if(ddForm.getFlagType()!=null && (ddForm.getFlagType().equalsIgnoreCase("isRevaluation")|| 
								ddForm.getFlagType().equalsIgnoreCase("isScrutiny")|| ddForm.getFlagType().equalsIgnoreCase("isChallengeValuation")) ){	
						    isAdded=DDStatusHandler.getInstance().updateChallanStatusOnCourseForRevaluation(ddForm);
						    if(ddForm.getMobNoList()!=null && ddForm.getMobNoList().size()>0){
						    	DDStatusHandler.getInstance().sendSMSToStudents(ddForm,session);
						    }
						}
						else
						    isAdded=DDStatusHandler.getInstance().updateChallanStatusOnCourseForExam(ddForm);

						
						if(!isAdded){
							errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
							
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_FOR_EXAM);
						}
						
					}catch (Exception e) {
						System.out.println("action  ======================================"+e);
						errors.add("admissionFormForm.faield",new ActionError("admissionFormForm.faield"));
						
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_FOR_EXAM);
					}
						
					
				} else{
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_FOR_EXAM);
				
				}
				
				
				
				return mapping.findForward(CMSConstants.INIT_CHALLAN_STATUS_ONCOURSE_CONFIRM_PAGE_NEW_FOR_EXAM);
			}
	
	public ActionForward initChallanUploadProcessForExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DDStatusForm ddForm=(DDStatusForm) form;
		
		try {
			setUserId(request, ddForm);
			
		}  catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		ddForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_EXAM);
	}
	
	
	
	// getting students for DD
	public ActionForward updateChallanUploadProcessForExam(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
		DDStatusForm ddForm = (DDStatusForm)form;
 	  
		ActionMessages errors=ddForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		
		try 
		{
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
			
				return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_EXAM);
			}
			ddForm.setChalanNotVerified(null);
			ddForm.setChalanVerifiedCount(null);
			List<DDStatusTO> studentList =null;
			if(ddForm.getFlagType()!=null && (ddForm.getFlagType().equalsIgnoreCase("isRevaluation")|| 
					ddForm.getFlagType().equalsIgnoreCase("isScrutiny")|| ddForm.getFlagType().equalsIgnoreCase("isChallengeValuation")) ){	
				studentList=DDStatusHandler.getInstance().getStudentsChallanDtailsOnDateWiseForRevaluation(ddForm);
			}
			else
				studentList=DDStatusHandler.getInstance().getStudentsChallanDtailsOnDateForExam(ddForm);

		System.out.println(studentList.size());
		if(studentList.size()==0 || studentList.isEmpty()){
		
			errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_EXAM);
		}else{
			boolean isAdded=false;
			ddForm.setDdStatusList(studentList);
			if(ddForm.getFlagType()!=null && (ddForm.getFlagType().equalsIgnoreCase("isRevaluation")|| 
					ddForm.getFlagType().equalsIgnoreCase("isScrutiny")|| ddForm.getFlagType().equalsIgnoreCase("isChallengeValuation")) ){
				isAdded=DDStatusHandler.getInstance().updateChallanUploadProcessForRevaluation(ddForm);

			}
			else{

				isAdded=DDStatusHandler.getInstance().updateChallanUploadProcessForExam(ddForm);
			}

			if(!isAdded){
				errors.add("admissionFormForm.faield",new ActionError("knowledgepro.admin.updateFailure"));

				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_EXAM);
			}

		}
		
		
		
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			exception.printStackTrace();
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		
		ActionMessage message = new ActionMessage("knowledgepro.admin.updateSuccess");
		messages.add("messages", message);
		saveMessages(request, messages);
		ddForm.setRecievedChallanDate("");
		ddForm.setDdStatusList(null);
		ddForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_EXAM);
	}
	
	/*public void chalanRecivedNotRecievedCountForExam(HttpServletRequest request,DDStatusForm ddform) throws Exception
	{
		Integer chalvercount=DDStatusHandler.getInstance().ChallanVerifiedCount(ddform);
		ddform.setChalanVerifiedCount(chalvercount.toString());
		Integer chalnotvercount=DDStatusHandler.getInstance().ChallanNotVerifiedCount(ddform);
		ddform.setChalanNotVerified(chalnotvercount.toString());
	}*/
	
	public ActionForward initChallanUploadProcessForFee(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DDStatusForm ddForm=(DDStatusForm) form;
		
		try {
			setUserId(request, ddForm);
			
		}  catch (Exception e) {
			log.error("error in initStudentEdit...", e);
			throw e;
		}
		ddForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_FEE);
	}
	
	
	
	// getting students for DD
	public ActionForward updateChallanUploadProcessForFee(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	 
		DDStatusForm ddForm = (DDStatusForm)form;
 	  
		ActionMessages errors=ddForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try 
		{	
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);			
				return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_FEE);
			}
			ddForm.setChalanNotVerified(null);
			ddForm.setChalanVerifiedCount(null);
			List<DDStatusTO> studentList =null;
			
			studentList=DDStatusHandler.getInstance().getStudentsChallanDtailsOnDateForFee(ddForm);

			System.out.println(studentList.size());
			if(studentList.size()==0 || studentList.isEmpty()){		
				errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_FEE);
			}else{
			boolean isAdded=false;
			ddForm.setDdStatusList(studentList);

			isAdded=DDStatusHandler.getInstance().updateChallanUploadProcessForFee(ddForm);

			if(!isAdded){
				errors.add("admissionFormForm.faield",new ActionError("knowledgepro.admin.updateFailure"));

				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_FEE);
			}
		}
		}
		catch(Exception exception)
		{
			String msg = super.handleApplicationException(exception);
			exception.printStackTrace();
			ddForm.setErrorMessage(msg);
			ddForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		ActionMessage message = new ActionMessage("knowledgepro.admin.updateSuccess");
		messages.add("messages", message);
		saveMessages(request, messages);
		ddForm.setRecievedChallanDate("");
		ddForm.setDdStatusList(null);
		ddForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_CHALLAN_UPLOAD_PROCESS_FOR_FEE);
	}
}
