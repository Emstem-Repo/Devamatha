package com.kp.cms.actions.smartcard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.smartcard.ScLostCorrectionForm;
import com.kp.cms.handlers.smartcard.ScLostCorrectionHandler;
import com.kp.cms.to.smartcard.ScLostCorrectionTo;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(ScLostCorrectionAction.class);
	private static final String PHOTOBYTES = "DisplinaryPhotoBytes";
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initScLostCorrection(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initScLostCorrection method in ScLostCorrectionAction class.");
		ScLostCorrectionForm scForm=(ScLostCorrectionForm) form;
		scForm.clearAll();
		cleanupEditSessionData(request);
		
		log.debug("Leaving initScLostCorrection");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION);
	}
	
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(ScLostCorrectionAction.PHOTOBYTES) != null)
				session.removeAttribute(ScLostCorrectionAction.PHOTOBYTES);
		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responce
	 * @return
	 * @throws Exception
	 */
	public ActionForward ScLostCorrectionSearch(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse responce) throws Exception{
		
		ScLostCorrectionForm scForm=(ScLostCorrectionForm) form;
		ActionErrors errors = new ActionErrors();
		/*if(scForm.getRegNo()==null || scForm.getRegNo().isEmpty())
		{
			errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.regno.employeeid.required"));
			addErrors(request, errors);
			scForm.clearAll();
			return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION);
		}
		else{*/
			try{
				if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
					Student student = ScLostCorrectionHandler.getInstance().verifyRegisterNumberAndGetStudentDetails(scForm);
					if(student == null){
						errors.add("error", new ActionError("knowledgepro.norecords"));
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION);
					}else{
						ScLostCorrection scLostCorrection=ScLostCorrectionHandler.getInstance().checkForDuplicate(scForm);
						if(scLostCorrection!=null ){
							String dDate = CommonUtil.ConvertStringToSQLDate2(scLostCorrection.getDateOfSubmission().toString());
							errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.already.applied", scLostCorrection.getCardReason(),  dDate.substring(0, 10)));
							addErrors(request, errors);
							return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION);
						}
					
						setStudentDetailsDataToForm(scForm, student, request);
						setSmartCardDetails(scForm, request);
						return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_DETAILS);
					}
				}
				else{
						Employee employee = (Employee) ScLostCorrectionHandler.getInstance().verifyEmployeeIdAndGetEmployeeDetails(scForm);
						if(employee == null){
							scForm.clearAll();
							errors.add("error", new ActionError("knowledgepro.norecords"));
							addErrors(request, errors);
							return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION);
						}
						else{
							ScLostCorrection scLostCorrection=ScLostCorrectionHandler.getInstance().checkForDuplicate(scForm);
							if(scLostCorrection!=null ){
								String dDate = CommonUtil.ConvertStringToSQLDate2(scLostCorrection.getDateOfSubmission().toString());
								errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.already.applied.employee", scLostCorrection.getCardReason(),  dDate.substring(0, 10)));
								addErrors(request, errors);
								scForm.clearAll();
								return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION);
							}
					
							setEmployeeDetailsDataToForm(scForm, employee, request);
							setSmartCardDetails(scForm, request);
							return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_DETAILS);
						}
				}
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}
		//}
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION);
		
	}
	
	/**
	 * @param scForm
	 * @param student
	 * @param request
	 * @throws Exception
	 */
	private void setStudentDetailsDataToForm(ScLostCorrectionForm scForm, Student student, HttpServletRequest request) throws Exception{
		
		try{			
			//scForm.setId(student.getId());
			scForm.setStuId(String.valueOf(student.getId()));
			if(student.getAdmAppln()!=null
					&& student.getAdmAppln().getPersonalData()!=null
					&& student.getAdmAppln().getPersonalData().getFirstName()!=null)
				scForm.setName(student.getAdmAppln().getPersonalData().getFirstName());
			if(student.getClassSchemewise()!=null
					&& student.getClassSchemewise().getClasses()!=null
					&& student.getClassSchemewise().getClasses().getName()!=null)
				scForm.setClassName(student.getClassSchemewise().getClasses().getName());
			scForm.setOldSmartCardNo(student.getSmartCardNo());
			scForm.setRegNo(student.getRegisterNo());
			scForm.setAccountNo(student.getBankAccNo());
			
			String currentAddress = null;
			if(student.getAdmAppln().getPersonalData()!=null){
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().isEmpty())
					currentAddress = student.getAdmAppln().getPersonalData().getCurrentAddressLine1()+", ";
				if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().isEmpty())
					currentAddress = currentAddress + student.getAdmAppln().getPersonalData().getCurrentAddressLine2()+", ";
				if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null 
						&& student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()!=null && !student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName().isEmpty())
					currentAddress = currentAddress + student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()+", ";
				if(student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!=null
						&& student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName()!=null && !student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName().isEmpty())
					currentAddress = currentAddress + student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName()+", ";
				if(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressZipCode().isEmpty())
					currentAddress = currentAddress + student.getAdmAppln().getPersonalData().getCurrentAddressZipCode();
			}
			scForm.setCurrentAddress(currentAddress);
			
			Set<ApplnDoc> docSet = student.getAdmAppln().getApplnDocs();
			Iterator<ApplnDoc> itr = docSet.iterator();
			while (itr.hasNext()) {
				ApplnDoc applnDoc = (ApplnDoc) itr.next();
				if (applnDoc.getIsPhoto() != null)
					if (applnDoc.getIsPhoto() && applnDoc.getDocument() != null) {
						request.getSession().setAttribute("DisplinaryPhotoBytes", applnDoc.getDocument());
					}
			}
			
			int courseComYear=0;
			if(student.getAdmAppln().getCourseBySelectedCourseId().getCurriculumSchemes()!=null){
				Iterator<CurriculumScheme> currSchemeItr=student.getAdmAppln().getCourseBySelectedCourseId().getCurriculumSchemes().iterator();
				while (currSchemeItr.hasNext()) {
					CurriculumScheme curriculumScheme = (CurriculumScheme) currSchemeItr.next();
					if(curriculumScheme.getYear().intValue()==student.getAdmAppln().getAppliedYear().intValue()){
					if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("year")){
						courseComYear= student.getAdmAppln().getAppliedYear()+(curriculumScheme.getNoScheme());
						}
					else if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("semester")){
					courseComYear= student.getAdmAppln().getAppliedYear()+((curriculumScheme.getNoScheme())/2);
					}
					else if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("Trimester")){
						courseComYear= student.getAdmAppln().getAppliedYear()+((curriculumScheme.getNoScheme())/3);
					}
				}
				}
			}
			scForm.setStudentCourseDuration("31-05-"+String.valueOf(courseComYear));
			
			
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	/**
	 * @param scForm
	 * @param employee
	 * @param request
	 * @throws Exception
	 */
	private void setEmployeeDetailsDataToForm(ScLostCorrectionForm scForm, Employee employee, HttpServletRequest request) throws Exception{
		
		try{			
			//scForm.setId(employee.getId());
			scForm.setEmpId(String.valueOf(employee.getId()));
			scForm.setEmpFingerPrintId(employee.getFingerPrintId());
			scForm.setName(employee.getFirstName());
			scForm.setEmpDepartment(employee.getDepartment()!=null?employee.getDepartment().getName():"");
			scForm.setOldSmartCardNo(employee.getSmartCardNo());
			scForm.setAccountNo(employee.getBankAccNo());
			scForm.setEmpDesignation(employee.getDesignation()!=null?employee.getDesignation().getName():"");
			
			String currentAddress = null;
			if(employee.getCommunicationAddressLine1()!=null && !employee.getCommunicationAddressLine1().isEmpty()){
				currentAddress = employee.getCommunicationAddressLine1()+", ";
			if(employee.getCommunicationAddressLine2()!=null && !employee.getCommunicationAddressLine2().isEmpty())
				currentAddress = currentAddress + employee.getCommunicationAddressLine2()+", ";
			if(employee.getCommunicationAddressCity()!=null && !employee.getCommunicationAddressCity().isEmpty())
				currentAddress = currentAddress + employee.getCommunicationAddressCity()+", ";
			if(employee.getCommunicationAddressStateOthers()!=null && !employee.getCommunicationAddressStateOthers().isEmpty())
				currentAddress = currentAddress + employee.getCommunicationAddressStateOthers()+", ";
			if(employee.getCommunicationAddressZip()!=null && !employee.getCommunicationAddressZip().isEmpty())
				currentAddress = currentAddress + employee.getCommunicationAddressZip();
			}
			scForm.setCurrentAddress(currentAddress);
			
			if(employee.getEmpImages()!=null && !employee.getEmpImages().isEmpty()){
				Iterator<EmpImages> itr=employee.getEmpImages().iterator();
				while (itr.hasNext()) {
					EmpImages empImg = itr.next();
					if(empImg.getEmpPhoto()!=null)
						request.getSession().setAttribute("DisplinaryPhotoBytes", empImg.getEmpPhoto());
						//scForm.setPhotoBytes(bo.getEmpPhoto());
				}
			
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
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
	public ActionForward addScLostCorrection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- ScLostCorrectionAction --- addScLostCorrection");
		ScLostCorrectionForm scForm=(ScLostCorrectionForm)form;
		ActionErrors errors=new ActionErrors();
//		errors = scForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			if(isCancelled(request))
			{
				scForm.clearAll();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
			if(errors.isEmpty())
			{
				
			setUserId(request, scForm);
			boolean isScLtCrn;
			isScLtCrn = ScLostCorrectionHandler.getInstance().addScLostCorrection(scForm);
			//If add operation is success then display the success message.
			if(isScLtCrn)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.SC_LOST_CORRECTION_ADD_SUCCESS));
				saveMessages(request, messages);
				scForm.clearAll();
				return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.SC_LOST_CORRECTION_ADD_FAIL));
				saveErrors(request, errors);
				}
			}
		}catch (Exception e) {
				log.error("Error in adding addScLostCorrection in ScLostCorrection Action",e);
				String msg = super.handleApplicationException(e);
				scForm.setErrorMessage(msg);
				scForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- ScLostCorrectionAction --- addScLostCorrection");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_DETAILS);
	}	
	
	/**
	 * @param scForm
	 * @param request
	 * @throws Exception
	 */
	private void setSmartCardDetails(ScLostCorrectionForm scForm, HttpServletRequest request) throws Exception{
		
		try{
			List<ScLostCorrectionTo> scHistory = ScLostCorrectionHandler.getInstance().getScHistory(scForm);
			if(scHistory!=null && !scHistory.isEmpty()){
				scForm.setDisplayHistory("YES");
				scForm.setScHistory(scHistory);
				
			}
		}catch (Exception e) {
			log.debug(e.getMessage());
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
	public ActionForward initScLostCorrectionCancel(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initScLostCorrectionCancel method in ScLostCorrectionAction class.");
		ScLostCorrectionForm scForm=(ScLostCorrectionForm) form;
		scForm.clearAll();
		log.debug("Leaving initScLostCorrectionCancel");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responce
	 * @return
	 * @throws Exception
	 */
	public ActionForward ScLostCorrectionCancelSearch(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse responce) throws Exception{
		
		log.info("start of ScLostCorrectionCancelSearch method in ScLostCorrectionAction class.");
		ScLostCorrectionForm scForm=(ScLostCorrectionForm) form;
		ActionErrors errors = new ActionErrors();
		/*if(scForm.getRegNo()==null || scForm.getRegNo().isEmpty())
		{
			errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.regno.employeeid.required"));
			addErrors(request, errors);
			
			return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
		}
		else{*/
			
			try{
				if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
				Student student = ScLostCorrectionHandler.getInstance().verifyRegisterNumberAndGetStudentDetails(scForm);
				if(student==null){
					errors.add("error", new ActionError("knowledgepro.norecords"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
				}else{

					ScLostCorrection scLostCorrection=ScLostCorrectionHandler.getInstance().checkForPresent(scForm);
					if(scLostCorrection!=null){
						if(!scLostCorrection.getStatus().equalsIgnoreCase("Applied")){
							if(scLostCorrection.getStatus().equalsIgnoreCase("Cancelled")){
								errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.cancel.already"));
								addErrors(request, errors);
								return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
							}else{
							errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.cancel.request.notavailable", scLostCorrection.getStatus()));
							addErrors(request, errors);
							return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
							}
						}
					}
					if(scLostCorrection==null){
						errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.cancel.request.notapplied"));
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
					}
					
					scForm.setId(scLostCorrection.getId());
					scForm.setCardType(scLostCorrection.getCardReason());
					scForm.setAppliedDate(CommonUtil.formatDates(scLostCorrection.getDateOfSubmission()));
					scForm.setStatus(scLostCorrection.getStatus());
					if(scLostCorrection.getRemarks()!=null && !scLostCorrection.getRemarks().isEmpty())
						scForm.setRemarks(scLostCorrection.getRemarks());
					scForm.setName(scLostCorrection.getStudentId().getAdmAppln().getPersonalData().getFirstName());
					scForm.setRegNo(scLostCorrection.getStudentId().getRegisterNo());
					scForm.setClassName(scLostCorrection.getStudentId().getClassSchemewise().getClasses().getName());
										
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL_DETAILS);
					}
				}
				else{
					Employee employee = ScLostCorrectionHandler.getInstance().verifyEmployeeIdAndGetEmployeeDetails(scForm);
					if(employee==null){
						errors.add("error", new ActionError("knowledgepro.norecords"));
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
					}else{

						ScLostCorrection scLostCorrection=ScLostCorrectionHandler.getInstance().checkForPresent(scForm);
						if(scLostCorrection!=null){
							if(!scLostCorrection.getStatus().equalsIgnoreCase("Applied")){
								if(scLostCorrection.getStatus().equalsIgnoreCase("Cancelled")){
									errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.cancel.already"));
									addErrors(request, errors);
									return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
								}else{
									scForm.clearAll();
									errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.cancel.request.notavailable", scLostCorrection.getStatus()));
									addErrors(request, errors);
									return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
								}
							}
						}
						if(scLostCorrection==null){
							scForm.clearAll();
							errors.add("error", new ActionError("knowledgepro.smartcard.lostcorrection.cancel.request.notapplied.employee"));
							addErrors(request, errors);
							return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
						}
						
						scForm.setId(scLostCorrection.getId());
						scForm.setCardType(scLostCorrection.getCardReason());
						scForm.setAppliedDate(CommonUtil.formatDates(scLostCorrection.getDateOfSubmission()));
						scForm.setStatus(scLostCorrection.getStatus());
						if(scLostCorrection.getRemarks()!=null && !scLostCorrection.getRemarks().isEmpty())
							scForm.setRemarks(scLostCorrection.getRemarks());
						scForm.setName(scLostCorrection.getEmployeeId().getFirstName());
						scForm.setEmpId(scLostCorrection.getEmployeeId().getFingerPrintId());
						scForm.setEmpDepartment(scLostCorrection.getEmployeeId().getDepartment().getName());
						
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL_DETAILS);
					}
					
					
				}
			}catch (Exception e) {
				log.debug(e.getMessage());
			}
		//}
		addErrors(request, errors);
		log.info("end of ScLostCorrectionCancelSearch method in ScLostCorrectionAction class.");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
		
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelScLostCorrection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- ScLostCorrectionAction --- addScLostCorrection");
		ScLostCorrectionForm scForm=(ScLostCorrectionForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		try {
			if(isCancelled(request))
			{
				scForm.clearAll();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
							
			setUserId(request, scForm);
			
			String userId = scForm.getUserId();
			int id = scForm.getId();
			boolean isScLtCrn = ScLostCorrectionHandler.getInstance().cancelScLostCorrection(id, userId);
			//If add operation is success then display the success message.
			if(isScLtCrn)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.SC_LOST_CORRECTION_CANCEL_SUCCESS));
				saveMessages(request, messages);
				scForm.clearAll();
				return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.SC_LOST_CORRECTION_CANCEL_FAIL));
				saveErrors(request, errors);
				}
			
		}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				scForm.setErrorMessage(msg);
				scForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- ScLostCorrectionAction --- addScLostCorrection");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_CANCEL);
	}
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initScViewStudentDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initScViewStudentDetails method in ScLostCorrectionAction class.");
		ScLostCorrectionForm scForm=(ScLostCorrectionForm) form;
		scForm.clearAll();
		
		log.debug("Leaving initScViewStudentDetails");
		return mapping.findForward(CMSConstants.INIT_SC_VIEW_STUDENT_DETAILS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responce
	 * @return
	 * @throws Exception
	 */
	public ActionForward ScViewStudentDetailsSearch(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse responce) throws Exception{
		
		ScLostCorrectionForm scForm=(ScLostCorrectionForm) form;
		ActionErrors errors = new ActionErrors();
		if((scForm.getRegNo()==null || scForm.getRegNo().isEmpty()) 
				&& (scForm.getLastFiveDigitAccNo().isEmpty() || scForm.getLastFiveDigitAccNo()==null))
		{
			errors.add("error", new ActionError("knowledgepro.anyonefield.required"));
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_SC_VIEW_STUDENT_DETAILS);
		}
		else{
			
			try{
				Student student = ScLostCorrectionHandler.getInstance().verifyRegisterNumberAndGetStudentDetailsAcc(scForm);
					if(student!=null){
						
						scForm.setId(student.getId());
						if(student.getAdmAppln()!=null
								&& student.getAdmAppln().getPersonalData()!=null
								&& student.getAdmAppln().getPersonalData().getFirstName()!=null)
						scForm.setName(student.getAdmAppln().getPersonalData().getFirstName());
						if(student.getClassSchemewise()!=null
								&& student.getClassSchemewise().getClasses()!=null
								&& student.getClassSchemewise().getClasses().getName()!=null)
						scForm.setClassName(student.getClassSchemewise().getClasses().getName());
						scForm.setStuId(String.valueOf(student.getId()));
						scForm.setOldSmartCardNo(student.getSmartCardNo());
						scForm.setRegNo(student.getRegisterNo());
						scForm.setAccountNo(student.getBankAccNo());
						
						String currentAddress = null;
						if(student.getAdmAppln().getPersonalData()!=null){
						if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().isEmpty())
							currentAddress = student.getAdmAppln().getPersonalData().getCurrentAddressLine1()+", ";
						if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().isEmpty())
							currentAddress = currentAddress + student.getAdmAppln().getPersonalData().getCurrentAddressLine2()+", ";
						if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!=null 
								&& student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()!=null && !student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName().isEmpty())
							currentAddress = currentAddress + student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()+", ";
						if(student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!=null
								&& student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName()!=null && !student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName().isEmpty())
							currentAddress = currentAddress + student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName()+", ";
						if(student.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null && !student.getAdmAppln().getPersonalData().getCurrentAddressZipCode().isEmpty())
							currentAddress = currentAddress + student.getAdmAppln().getPersonalData().getCurrentAddressZipCode();
						}
						scForm.setCurrentAddress(currentAddress);
						
						Set<ApplnDoc> docSet = student.getAdmAppln().getApplnDocs();
						Iterator<ApplnDoc> itr = docSet.iterator();
						while (itr.hasNext()) {
							ApplnDoc applnDoc = (ApplnDoc) itr.next();
							if (applnDoc.getIsPhoto() != null)
								if (applnDoc.getIsPhoto() && applnDoc.getDocument() != null) {

									request.getSession().setAttribute("DisplinaryPhotoBytes", applnDoc.getDocument());
									
								}
						}
						
						int courseComYear=0;
						if(student.getAdmAppln().getCourseBySelectedCourseId().getCurriculumSchemes()!=null){
							Iterator<CurriculumScheme> currSchemeItr=student.getAdmAppln().getCourseBySelectedCourseId().getCurriculumSchemes().iterator();
							while (currSchemeItr.hasNext()) {
								CurriculumScheme curriculumScheme = (CurriculumScheme) currSchemeItr.next();
								if(curriculumScheme.getYear().intValue()==student.getAdmAppln().getAppliedYear().intValue()){
								if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("year")){
									courseComYear= student.getAdmAppln().getAppliedYear()+(curriculumScheme.getNoScheme());
									}
								else if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("semester")){
								courseComYear= student.getAdmAppln().getAppliedYear()+((curriculumScheme.getNoScheme())/2);
								}
								else if(curriculumScheme.getCourseScheme().getName().equalsIgnoreCase("Trimester")){
									courseComYear= student.getAdmAppln().getAppliedYear()+((curriculumScheme.getNoScheme())/3);
								}
							}
							}
						}
						scForm.setStudentCourseDuration("31-05-"+String.valueOf(courseComYear));
						
					}else{
						errors.add("error", new ActionError("knowledgepro.norecords"));
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_SC_VIEW_STUDENT_DETAILS);
					}
					
				
				return mapping.findForward(CMSConstants.SC_VIEW_STUDENT_DETAILS);
				
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_VIEW_STUDENT_DETAILS);
		
	}
	
	

}