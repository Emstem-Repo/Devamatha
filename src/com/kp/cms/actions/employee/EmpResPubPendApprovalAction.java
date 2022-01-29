package com.kp.cms.actions.employee;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.kp.cms.actions.admission.DisciplinaryDetailsAction;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.forms.employee.EmpResPubPendApprovalForm;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.handlers.employee.EmpResPubDetailsHandler;
import com.kp.cms.handlers.employee.EmpResPubPendApprovalHandler;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpArticlInPeriodicalsTO;
import com.kp.cms.to.employee.EmpArticleJournalsTO;
import com.kp.cms.to.employee.EmpAwardsAchievementsOthersTO;
import com.kp.cms.to.employee.EmpBlogTO;
import com.kp.cms.to.employee.EmpBooksMonographsTO;
import com.kp.cms.to.employee.EmpCasesNotesWorkingTO;
import com.kp.cms.to.employee.EmpChapterArticlBookTO;
import com.kp.cms.to.employee.EmpConferencePresentationTO;
import com.kp.cms.to.employee.EmpConferenceSeminarsAttendedTO;
import com.kp.cms.to.employee.EmpFilmVideosDocTO;
import com.kp.cms.to.employee.EmpInvitedTalkTO;
import com.kp.cms.to.employee.EmpOwnPhdMPilThesisTO;
import com.kp.cms.to.employee.EmpPhdMPhilThesisGuidedTO;
import com.kp.cms.to.employee.EmpResProjectTO;
import com.kp.cms.to.employee.EmpResPublicMasterTO;
import com.kp.cms.to.employee.EmpSeminarsOrganizedTO;
import com.kp.cms.to.employee.EmpWorkshopsFdpTrainingTO;
import com.kp.cms.transactions.employee.IEmpResPubDetailsTransaction;
import com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction;
import com.kp.cms.transactionsimpl.employee.EmpResPubDetailsImpl;
import com.kp.cms.transactionsimpl.employee.EmpResPubPendApprovalImpl;

public class EmpResPubPendApprovalAction  extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EmpResPubPendApprovalAction.class);
	
	private static final String MESSAGE_KEY = "messages";
	private static final String RESEARCHPHOTOBYTES = "researchPhotoBytes";
	private static final String TO_DATEFORMAT = "MM/dd/yyyy";
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private static final String selectedCategory = "selectedCategory";
	private static final String selectedEmpoloyeeId="selectedEmpoloyeeId";
	IEmpResPubPendApprovalTransaction empTransaction=new EmpResPubPendApprovalImpl();
	EmpResPubPendApprovalHandler handler = EmpResPubPendApprovalHandler.getInstance();
	
public ActionForward initEmpResPendList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
{
	ActionErrors errors = new ActionErrors();
	EmpResPubPendApprovalForm objform = (EmpResPubPendApprovalForm) form;
	try {
		cleanUpPageData(objform);
		setUserId(request,objform);
		objform.setPendingList(true);
		setDataToFormForSearch(objform);
		Employee emp=empTransaction.getEmployeeIdFromUserIdEmp(objform);
		if(emp==null && emp.getId()<=0)
		{
			errors.add("error", new ActionError("knowledgepro.employee.no.data"));
			addErrors(request, errors);	
		}else if(emp!=null && emp.getId()>0)
		{
		List<EmployeeTO> employeeToList=null;
	//	setDataToForm(objform);
		employeeToList = handler.getSearchedEmployeePending(emp.getId());
		if (employeeToList == null || employeeToList.isEmpty()) {
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		message = new ActionMessage(
				CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
		messages.add(EmpResPubPendApprovalAction.MESSAGE_KEY, message);
		saveMessages(request, messages);
		return mapping
				.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);
		
			}
			objform.setEmployeeToList(employeeToList);
		}
	} catch (Exception e) {
		log.error("error in EmpResPubPendApprovalAction...", e);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		message = new ActionMessage(
				CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
		messages.add(EmpResPubPendApprovalAction.MESSAGE_KEY, message);
		saveMessages(request, messages);

		return mapping
				.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);

	}
	log.info("exit EmpResPubPendApprovalAction..");
	
	return mapping.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);
}


public ActionForward initEmpResApprovedList(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
{
	EmpResPubPendApprovalForm objform = (EmpResPubPendApprovalForm) form;
	ActionErrors errors = new ActionErrors();
	try {
	cleanUpPageData(objform);
	setUserId(request,objform);
	objform.setApprovedList(true);
	setDataToFormForSearch(objform);
	Employee emp=empTransaction.getEmployeeIdFromUserIdEmp(objform);
	if(emp==null && emp.getId()<=0)
	{
		errors.add("error", new ActionError("knowledgepro.employee.no.data"));
		addErrors(request, errors);	
	}else if(emp!=null && emp.getId()>0)
	{
		List<EmployeeTO> employeeToList=null;
		employeeToList = handler.getSearchedEmployeeApproved(emp.getId());
				if (employeeToList == null || employeeToList.isEmpty()) {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(EmpResPubPendApprovalAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
			}
	   objform.setEmployeeToList(employeeToList);
	}
} catch (Exception e) {
	log.error("error in EmpResPubPendApprovalAction...", e);
	ActionMessages messages = new ActionMessages();
	ActionMessage message = null;
	message = new ActionMessage(
			CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
	messages.add(EmpResPubPendApprovalAction.MESSAGE_KEY, message);
	saveMessages(request, messages);

	return mapping
			.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);

}
log.info("exit EmpResPubPendApprovalAction..");

return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
}
	

public ActionForward getSearchedEmployee(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	EmpResPubPendApprovalForm stForm = (EmpResPubPendApprovalForm) form;
	ActionMessages errors = stForm.validate(mapping, request);
try {
	if (errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			if(stForm.isApprovedList())
			{
				return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
			}
			else
			{
				return mapping.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);
			}
		}
		
		List<EmployeeTO> employeeToList = handler.getSearchedEmployee(stForm);
		if (employeeToList == null || employeeToList.isEmpty()) {
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			message = new ActionMessage(
					CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			messages.add(EmpResPubPendApprovalAction.MESSAGE_KEY, message);
			saveMessages(request, messages);
			
			
		}
		stForm.setEmployeeToList(employeeToList);
		
		
	} catch (ApplicationException e) {
		log.error("error in getSearchedStudents...", e);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		message = new ActionMessage(
				CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
		messages.add(EmpResPubPendApprovalAction.MESSAGE_KEY, message);
		saveMessages(request, messages);

		if(stForm.isApprovedList())
		{
			return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
		}
		else
		{
			return mapping.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);
		}

	} catch (Exception e) {
		log.error("error in getSearchedStudents...", e);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		message = new ActionMessage(
				CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
		messages.add(EmpResPubPendApprovalAction.MESSAGE_KEY, message);
		saveMessages(request, messages);

		if(stForm.isApprovedList())
		{
			return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
		}
		else
		{
			return mapping.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);
		}

	}
	log.info("exit getSearchedStudents..");
	if(stForm.isApprovedList())
	{
		return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
	}
	else
	{
		return mapping.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);
	}
}


public  void setDataToForm(EmpResPubPendApprovalForm empResPubForm)throws Exception {
	 //Initialising data 
	initializeResearchProject(empResPubForm);
	initializeBookMonographs(empResPubForm);
	initializeArticleJournals(empResPubForm);
	initializeChapArticlBookTO(empResPubForm);
	initializeBlogTO(empResPubForm);
	initializeFilmVideosDoc(empResPubForm);
	initializeArticlInPeriodicals(empResPubForm);
	initializeConfPresent(empResPubForm); 
	initializeCasesNotesWorking(empResPubForm);
	initializeSeminarsOrganised(empResPubForm);
	initializePhdMPhilThesisGuided(empResPubForm);
	initializeOwnPhdMPilThesis(empResPubForm);
	initializeInvitedTalk(empResPubForm); 
	initializeAwardsAchievements(empResPubForm);
	
	
}

public  void setDataToFormForSearch(EmpResPubPendApprovalForm empResPubForm)throws Exception {
	 Map<String,String> departmentMap=empTransaction.getDepartmentMap();
	 if(departmentMap!=null)
	 {
		 empResPubForm.setTempDepartmentMap(departmentMap);
     }

}

	public ActionForward getEmpResEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmpResPubPendApprovalForm objform = (EmpResPubPendApprovalForm) form;
		HttpSession session = request.getSession();
			ActionMessages errors = objform.validate(mapping, request);
			try {
				
				if (session.getAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES) != null)
					session.removeAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES);
				
				if (errors != null && !errors.isEmpty()) {
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);
					}
				clear(objform);
				handler.getEmployeeResearchDetails(objform);
				if (objform.getResearchPhotoBytes()!= null)
				{
				
					if (session != null) {
						session.setAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES, objform.getResearchPhotoBytes());
					}	
				}
			
			return mapping.findForward(CMSConstants.EMP_RES_PUB_DETAILS_APPROVAL);
		}catch (Exception exception) {
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
}
	
	
	public ActionForward getEmpResApprovedEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmpResPubPendApprovalForm objform = (EmpResPubPendApprovalForm) form;
		
			ActionMessages errors = objform.validate(mapping, request);
			try {
				HttpSession session = request.getSession();
				if (session.getAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES) != null)
					session.removeAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES);
				if (errors != null && !errors.isEmpty()) {
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
					}
				clear(objform);
				handler.getEmployeeResearchDetailsApproved(objform);
				if (objform.getResearchPhotoBytes()!= null)
				{
					if (session != null) {
						session.setAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES, objform.getResearchPhotoBytes());
					}	
				}
				
			return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED);
		}catch (Exception exception) {
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
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
	public ActionForward saveEmpResPubDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmpResPubPendApprovalForm objform = (EmpResPubPendApprovalForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=objform.validate(mapping, request);
			
		boolean flag=false;
		if(errors.isEmpty()){
			try {
				flag=handler.saveEmpResPub(objform);
				if(flag){
					if(objform.isApproveFlag()){
				//Temp Comment	
						handler.sendMailToEmployee(objform);
					}
					ActionMessage message=new ActionMessage(CMSConstants.EMP_RES_APPROVAL_CONFIRM);
					messages.add(CMSConstants.MESSAGES,message);
					saveMessages(request, messages);
					cleanUpPageData(objform);
					return mapping.findForward(CMSConstants.EMP_RES_APPROVAL_CONFIRM);
				}else{
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMP_RES_PUB_PENDING_LIST);
				}
			} catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					objform.setErrorMessage(msg);
					objform.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
				
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EMP_RES_PUB_DETAILS_APPROVAL);
		}
	}
	
	public ActionForward saveResPubApproved(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmpResPubPendApprovalForm objform = (EmpResPubPendApprovalForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=objform.validate(mapping, request);
			
		boolean flag=false;
		if(errors.isEmpty()){
			try {
				flag=handler.saveResPubApproved(objform);
				if(flag){
				//	EmpResPubDetailsHandler.getInstance().sendMailToApprover(objform);
					ActionMessage message=new ActionMessage(CMSConstants.EMP_RESEARCH_APPROVED_SUBMIT);
					messages.add(CMSConstants.MESSAGES,message);
					saveMessages(request, messages);
					cleanUpPageData(objform);
					return mapping.findForward(CMSConstants.EMP_RESEARCH_APPROVED_SUBMIT);
				}else{
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
				}
			} catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					objform.setErrorMessage(msg);
					objform.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
				
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED);
		}
	}
	
	/**
	 * @param empResPubForm
	 */
	private void cleanUpPageData(EmpResPubPendApprovalForm empResPubForm) {
		log.info("enter cleanUpPageData..");
		if (empResPubForm != null) {
			empResPubForm.setApprovedDate(null);
			empResPubForm.setApproverComment(null);
			empResPubForm.setApproverId(null);
			empResPubForm.setEmployeeId(null);
			empResPubForm.setEmployeeName(null);
			empResPubForm.setEntryCreatedate(null);
			empResPubForm.setFocusValue(null);
			empResPubForm.setOrigEmpResPubName(null);
			empResPubForm.setEmpArticleJournalsTO(null);
			empResPubForm.setEmpArticlInPeriodicalsTO(null);
			empResPubForm.setEmpBlogTO(null);
			empResPubForm.setEmpBooksMonographsTO(null);
			empResPubForm.setEmpCasesNotesWorkingTO(null);
			empResPubForm.setEmpChapterArticlBookTO(null);
			empResPubForm.setEmpConferencePresentationTO(null);
			empResPubForm.setEmpFilmVideosDocTO(null);
			empResPubForm.setEmpOwnPhdMPilThesisTO(null);
			empResPubForm.setEmpPhdMPhilThesisGuidedTO(null);
			empResPubForm.setEmpResearchProjectTO(null);
			empResPubForm.setEmpSeminarsOrganizedTO(null);
			empResPubForm.setEmployeeToList(null);
			empResPubForm.setTempActive("1");
			empResPubForm.setTempDepartmentId(null);
			empResPubForm.setTempDepartmentName(null);
			empResPubForm.setTempFingerPrintId(null);
			empResPubForm.setTempName(null);
			empResPubForm.setPendingList(false);
			empResPubForm.setApprovedList(false);
			empResPubForm.setEmpName(null);
			empResPubForm.setEmpDepartment(null);
			empResPubForm.setFingerprintId(null);
			empResPubForm.setSelectedCategory(false);
		
			}
		}
	
private void initializeResearchProject(EmpResPubPendApprovalForm empResPubForm) {
		
		List<EmpResProjectTO> list=new ArrayList<EmpResProjectTO>();
		EmpResProjectTO empPreviousOrgTo=new EmpResProjectTO();
		empPreviousOrgTo.setAbstractObjectives("");
		empPreviousOrgTo.setInvestigators("");
		empPreviousOrgTo.setSponsors("");
		empPreviousOrgTo.setTitle("");
		empPreviousOrgTo.setIsApproved(false);
		empPreviousOrgTo.setIsRejected(false);
		empResPubForm.setEmpResearchProjectLength(String.valueOf(list.size()));
		list.add(empPreviousOrgTo);
		empResPubForm.setEmpResearchProjectTO(list);
	}
/**
 * @param empResPubForm
 */
private void initializeBookMonographs(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpBooksMonographsTO> list=new ArrayList<EmpBooksMonographsTO>();
	EmpBooksMonographsTO empPreviousOrgTo=new EmpBooksMonographsTO();
	empPreviousOrgTo.setCompanyInstitution("");
	empPreviousOrgTo.setAuthorName("");
	empPreviousOrgTo.setIsbn("");
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setPlacePublication("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setTotalPages("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpBooksMonographsLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpBooksMonographsTO(list);
}
/**
 * @param empResPubForm
 */
private void initializeArticleJournals(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpArticleJournalsTO> list=new ArrayList<EmpArticleJournalsTO>();
	EmpArticleJournalsTO empPreviousOrgTo=new EmpArticleJournalsTO();
	empPreviousOrgTo.setNameJournal("");
	empPreviousOrgTo.setAuthorName("");
	empPreviousOrgTo.setDoi("");
	empPreviousOrgTo.setIsbn("");
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setIssueNumber("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setPagesFrom("");
	empPreviousOrgTo.setPagesTo("");
	empPreviousOrgTo.setVolumeNumber("");
	empPreviousOrgTo.setDepartmentInstitution("");
	empPreviousOrgTo.setCompanyInstitution("");
	empPreviousOrgTo.setPlacePublication("");
	empPreviousOrgTo.setUrl("");
	empPreviousOrgTo.setImpactFactor("");
	empPreviousOrgTo.setDateAccepted("");
	empPreviousOrgTo.setDateSent("");
	empPreviousOrgTo.setDatePublished("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpArticleJournalsLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpArticleJournalsTO(list);
}
/**
 * @param empResPubForm
 */
private void initializeChapArticlBookTO(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpChapterArticlBookTO> list=new ArrayList<EmpChapterArticlBookTO>();
	EmpChapterArticlBookTO empPreviousOrgTo=new EmpChapterArticlBookTO();
	empPreviousOrgTo.setEditorsName("");
	empPreviousOrgTo.setAuthorName("");
	empPreviousOrgTo.setDoi("");
	empPreviousOrgTo.setIsbn("");
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setPlacePublication("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setPagesFrom("");
	empPreviousOrgTo.setPagesTo("");
	empPreviousOrgTo.setCompanyInstitution("");
	empPreviousOrgTo.setTotalPages("");
	empPreviousOrgTo.setTitleChapterArticle("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpChapterArticlBookLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpChapterArticlBookTO(list);
}

/**
 * @param empResPubForm
 */
private void initializeBlogTO(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpBlogTO> list=new ArrayList<EmpBlogTO>();
	EmpBlogTO empPreviousOrgTo=new EmpBlogTO();
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setNumberOfComments("");
	empPreviousOrgTo.setSubject("");
	empPreviousOrgTo.setUrl("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpBlogLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpBlogTO(list);
}
/**
 * @param empResPubForm
 */
private void initializeFilmVideosDoc(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpFilmVideosDocTO> list=new ArrayList<EmpFilmVideosDocTO>();
	EmpFilmVideosDocTO empPreviousOrgTo=new EmpFilmVideosDocTO();
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setSubtitles("");
	empPreviousOrgTo.setGenre("");
	empPreviousOrgTo.setCredits("");
	empPreviousOrgTo.setRunningTime("");
	empPreviousOrgTo.setAspectRatio("");
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setDiscFormat("");
	empPreviousOrgTo.setTechnicalFormat("");
	empPreviousOrgTo.setAudioFormat("");
	empPreviousOrgTo.setProducer("");
	empPreviousOrgTo.setCopyrights("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpFilmVideosDocLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpFilmVideosDocTO(list);
}
/**
 * @param empResPubForm
 */
private void initializeArticlInPeriodicals(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpArticlInPeriodicalsTO> list=new ArrayList<EmpArticlInPeriodicalsTO>();
	EmpArticlInPeriodicalsTO empPreviousOrgTo=new EmpArticlInPeriodicalsTO();
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setAuthorName("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setDateMonthYear("");
	empPreviousOrgTo.setNamePeriodical("");
	empPreviousOrgTo.setVolumeNumber("");
	empPreviousOrgTo.setIssueNumber("");
	empPreviousOrgTo.setPagesFrom("");
	empPreviousOrgTo.setPagesTo("");
	empPreviousOrgTo.setIsbn("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpArticlPeriodicalsLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpArticlInPeriodicalsTO(list);
}
/**
 * @param empResPubForm
 */
private void initializeConfPresent(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpConferencePresentationTO> list=new ArrayList<EmpConferencePresentationTO>();
	EmpConferencePresentationTO empPreviousOrgTo=new EmpConferencePresentationTO();
	empPreviousOrgTo.setNameTalksPresentation("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setNameConferencesSeminar("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setCompanyInstitution("");
	empPreviousOrgTo.setPlacePublication("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpConfPresentLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpConferencePresentationTO(list);
}

/**
 * @param empResPubForm
 */
private void initializeInvitedTalk(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpInvitedTalkTO> list=new ArrayList<EmpInvitedTalkTO>();
	EmpInvitedTalkTO empPreviousOrgTo=new EmpInvitedTalkTO();
	empPreviousOrgTo.setNameTalksPresentation("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setNameConferencesSeminar("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setCompanyInstitution("");
	empPreviousOrgTo.setPlacePublication("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpInvitedTalkLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpInvitedTalkTO(list);
}



/**
 * @param empResPubForm
 */
private void initializeCasesNotesWorking(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpCasesNotesWorkingTO> list=new ArrayList<EmpCasesNotesWorkingTO>();
	EmpCasesNotesWorkingTO empPreviousOrgTo=new EmpCasesNotesWorkingTO();
	empPreviousOrgTo.setAuthorName("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setAbstractObjectives("");
	empPreviousOrgTo.setSponsors("");
	empPreviousOrgTo.setCaseNoteWorkPaper("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpCasesNotesWorkingLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpCasesNotesWorkingTO(list);
}

/**
 * @param empResPubForm
 */
private void initializeSeminarsOrganised(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpSeminarsOrganizedTO> list=new ArrayList<EmpSeminarsOrganizedTO>();
	EmpSeminarsOrganizedTO empPreviousOrgTo=new EmpSeminarsOrganizedTO();
	empPreviousOrgTo.setNameOrganisers("");
	empPreviousOrgTo.setNameConferencesSeminar("");
	empPreviousOrgTo.setResoursePerson("");
	empPreviousOrgTo.setPlace("");
	empPreviousOrgTo.setDateMonthYear("");
	empPreviousOrgTo.setLanguage("");
	empPreviousOrgTo.setSponsors("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpSeminarsOrganizedLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpSeminarsOrganizedTO(list);
}

/**
 * @param empResPubForm
 */
private void initializePhdMPhilThesisGuided(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpPhdMPhilThesisGuidedTO> list=new ArrayList<EmpPhdMPhilThesisGuidedTO>();
	EmpPhdMPhilThesisGuidedTO empPreviousOrgTo=new EmpPhdMPhilThesisGuidedTO();
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setSubject("");
	empPreviousOrgTo.setNameStudent("");
	empPreviousOrgTo.setCompanyInstitution("");
	empPreviousOrgTo.setPlace("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpPhdThesisGuidedLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpPhdMPhilThesisGuidedTO(list);
}
/**
 * @param empResPubForm
 */
private void initializeOwnPhdMPilThesis(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpOwnPhdMPilThesisTO> list=new ArrayList<EmpOwnPhdMPilThesisTO>();
	EmpOwnPhdMPilThesisTO empPreviousOrgTo=new EmpOwnPhdMPilThesisTO();
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setSubject("");
	empPreviousOrgTo.setNameGuide("");
	empPreviousOrgTo.setCompanyInstitution("");
	empPreviousOrgTo.setPlace("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpOwnPhdThesisLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpOwnPhdMPilThesisTO(list);
}


private void initializeAwardsAchievements(EmpResPubPendApprovalForm empResPubForm) {
	
	List<EmpAwardsAchievementsOthersTO> list=new ArrayList<EmpAwardsAchievementsOthersTO>();
	EmpAwardsAchievementsOthersTO empPreviousOrgTo=new EmpAwardsAchievementsOthersTO();
	empPreviousOrgTo.setName("");
	empPreviousOrgTo.setDescription("");
	empPreviousOrgTo.setPlace("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empResPubForm.setEmpAwardsLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpAwardsAchievementsOthersTO(list);
}

/**
* @param mapping
* @param form
* @param request
* @param response
* @return
* @throws Exception
*/
public ActionForward resetAwardsAchievements(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	EmpResPubPendApprovalForm empResPubForm=(EmpResPubPendApprovalForm)form;
if(empResPubForm.getEmpWorkshopsTO()!=null)
if(empResPubForm.getMode()!=null){
	if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		List<EmpAwardsAchievementsOthersTO> list=empResPubForm.getEmpAwardsAchievementsOthersTO();
		EmpAwardsAchievementsOthersTO empPreviousOrgTo=new EmpAwardsAchievementsOthersTO();
		empPreviousOrgTo.setName("");
		empPreviousOrgTo.setDescription("");
		empPreviousOrgTo.setPlace("");
		empPreviousOrgTo.setMonthYear("");
		empPreviousOrgTo.setIsApproved(false);
		empPreviousOrgTo.setIsRejected(false);
		empResPubForm.setEmpAwardsLength(String.valueOf(list.size()));
		list.add(empPreviousOrgTo);
		empResPubForm.setMode(null);
		//String size=String.valueOf(list.size()-1);
		empResPubForm.setFocusValue("AwardsAchievements");
	}
}
log.info("End of resetExperienceInfo of empResPubForm");
return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}
/**
* @param mapping
* @param form
* @param request
* @param response
* @return
* @throws Exception
*/
public ActionForward removeAwardsAchievements(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
log.info("Befinning of removeWorkshopsAttended of INIT_EMP_RES_PUB");
EmpResPubPendApprovalForm empResPubForm=(EmpResPubPendApprovalForm)form;
List<EmpAwardsAchievementsOthersTO> list=null;
if(empResPubForm.getEmpWorkshopsTO()!=null)
if(empResPubForm.getMode()!=null){
	if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		list=empResPubForm.getEmpAwardsAchievementsOthersTO();
		if(list.size()>0)
		list.remove(list.size()-1);
		empResPubForm.setEmpAwardsLength(String.valueOf(list.size()-1));
		empResPubForm.setFocusValue("AwardsAchievements");
	}
}
log.info("End of removeWorkshopsAttended of INIT_EMP_RES_PUB");
return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}

/*public ActionForward LoadApprovedDetailsBySelection(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Beginning of LoadApprovedDetailsBySelection ");
	EmpResPubPendApprovalForm empResPubForm=(EmpResPubPendApprovalForm)form;
	ActionMessages messages=new ActionMessages();
	ActionMessages errors = empResPubForm.validate(mapping, request);
	try {
		HttpSession session = request.getSession();
		if (session.getAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES) != null)
			session.removeAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES);
		if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.EMP_RES_PUB_APPROVED_LIST);
			}
		handler.getEmployeeResearchDetails(empResPubForm);
		if (empResPubForm.getResearchPhotoBytes()!= null)
		{
		
			if (session != null) {
				session.setAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES, empResPubForm.getResearchPhotoBytes());
			}	
		}
		
	return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED);
}catch (Exception exception) {
	if (exception instanceof ApplicationException) {
		String msg = super.handleApplicationException(exception);
		empResPubForm.setErrorMessage(msg);
		empResPubForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}else
		throw exception;
	}
}*/
	
public ActionForward seeApprovedData(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	EmpResPubPendApprovalForm objform = (EmpResPubPendApprovalForm) form;
	
		EmpResPubPendApprovalHandler handler = EmpResPubPendApprovalHandler.getInstance();
		try {
			HttpSession session = request.getSession();
			String Category = request.getParameter(EmpResPubPendApprovalAction.selectedCategory);
			
			if (Category != null) {
			boolean cat=false;
				if(Category.equalsIgnoreCase("true"))
					cat=true;
				session.setAttribute(EmpResPubPendApprovalAction.selectedCategory, Category);
				objform.setSelectedCategory(cat);
			}
			
			String empId = request .getParameter(EmpResPubPendApprovalAction.selectedEmpoloyeeId);
			if (empId != null) {
				session.setAttribute(EmpResPubPendApprovalAction.selectedEmpoloyeeId, Category);
				objform.setSelectedEmployeeId(empId);
			}
			if (session.getAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES) != null)
				session.removeAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES);
			clear(objform);
			handler.getEmployeeResearchDetailsByEmployeeId(objform);
			if (objform.getResearchPhotoBytes()!= null)
			{
				if (session != null) {
					session.setAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES, objform.getResearchPhotoBytes());
				}	
			}
		return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVED_NEW);
	}catch (Exception exception) {
		if (exception instanceof ApplicationException) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else
			throw exception;
		}
}

public ActionForward seeApprovalPendingData(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	EmpResPubPendApprovalForm objform = (EmpResPubPendApprovalForm) form;
	
		EmpResPubPendApprovalHandler handler = EmpResPubPendApprovalHandler.getInstance();
		try {
			HttpSession session = request.getSession();
			String Category = request.getParameter(EmpResPubPendApprovalAction.selectedCategory);
			
			if (Category != null) {
			boolean cat=false;
				if(Category.equalsIgnoreCase("true"))
					cat=true;
				session.setAttribute(EmpResPubPendApprovalAction.selectedCategory, Category);
				objform.setSelectedCategory(cat);
			}
			
			String empId = request .getParameter(EmpResPubPendApprovalAction.selectedEmpoloyeeId);
			if (empId != null) {
				session.setAttribute(EmpResPubPendApprovalAction.selectedEmpoloyeeId, Category);
				objform.setSelectedEmployeeId(empId);
			}
			if (session.getAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES) != null)
				session.removeAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES);
			clear(objform);
			handler.getEmployeeApprovalPendingByEmployeeId(objform);
			if (objform.getResearchPhotoBytes()!= null)
			{
				if (session != null) {
					session.setAttribute(EmpResPubPendApprovalAction.RESEARCHPHOTOBYTES, objform.getResearchPhotoBytes());
				}	
			}
		return mapping.findForward(CMSConstants.EMP_RES_PUB_APPROVAL_PENDING_NEW);
	}catch (Exception exception) {
		if (exception instanceof ApplicationException) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else
			throw exception;
		}
}


public void clear(EmpResPubPendApprovalForm objform){
	List<EmpArticlInPeriodicalsTO> empArticlInPeriodicalsTO = new ArrayList<EmpArticlInPeriodicalsTO>();
	List<EmpArticleJournalsTO> empArticleJournalsTO = new ArrayList<EmpArticleJournalsTO>();
	List<EmpBlogTO> empBlogTO= new ArrayList<EmpBlogTO>();
	List<EmpBooksMonographsTO> empBooksMonographsTO= new ArrayList<EmpBooksMonographsTO>();
	List<EmpCasesNotesWorkingTO> empCasesNotesWorkingTO= new ArrayList<EmpCasesNotesWorkingTO>();
	List<EmpChapterArticlBookTO> empChapterArticlBookTO= new ArrayList<EmpChapterArticlBookTO>();
	List<EmpConferencePresentationTO> empConferencePresentationTO= new ArrayList<EmpConferencePresentationTO>();
	List<EmpFilmVideosDocTO> empFilmVideosDocTO= new ArrayList<EmpFilmVideosDocTO>();
	List<EmpOwnPhdMPilThesisTO> empOwnPhdMPilThesisTO= new ArrayList<EmpOwnPhdMPilThesisTO>();
	List<EmpPhdMPhilThesisGuidedTO> empPhdMPhilThesisGuidedTO= new ArrayList<EmpPhdMPhilThesisGuidedTO>();
	List<EmpSeminarsOrganizedTO> empSeminarsOrganizedTO= new ArrayList<EmpSeminarsOrganizedTO>();
	List<EmpResProjectTO> empResProjectTO= new ArrayList<EmpResProjectTO>();
	List<EmpInvitedTalkTO> empInvitedTalkTO= new ArrayList<EmpInvitedTalkTO>();
	List<EmpConferenceSeminarsAttendedTO> empSeminarsAttendedTO= new ArrayList<EmpConferenceSeminarsAttendedTO>();
	List<EmpWorkshopsFdpTrainingTO> empWorkshopsTrainingTOs= new ArrayList<EmpWorkshopsFdpTrainingTO>();
	List<EmpAwardsAchievementsOthersTO> empAwardsAchievementsTO= new ArrayList<EmpAwardsAchievementsOthersTO>();
	
	
	objform.setEmpArticlInPeriodicalsTO(empArticlInPeriodicalsTO);
	objform.setEmpArticleJournalsTO(empArticleJournalsTO);
	objform.setEmpAwardsAchievementsOthersTO(empAwardsAchievementsTO);
	objform.setEmpBlogTO(empBlogTO);
	objform.setEmpBooksMonographsTO(empBooksMonographsTO);
	objform.setEmpCasesNotesWorkingTO(empCasesNotesWorkingTO);
	objform.setEmpChapterArticlBookTO(empChapterArticlBookTO);
	objform.setEmpConferencePresentationTO(empConferencePresentationTO);
	objform.setEmpFilmVideosDocTO(empFilmVideosDocTO);
	objform.setEmpInvitedTalkTO(empInvitedTalkTO);
	objform.setEmpOwnPhdMPilThesisTO(empOwnPhdMPilThesisTO);
	objform.setEmpPhdMPhilThesisGuidedTO(empPhdMPhilThesisGuidedTO);
	objform.setEmpResearchProjectTO(empResProjectTO);
	objform.setEmpSeminarsOrganizedTO(empSeminarsOrganizedTO);
	objform.setEmpWorkshopsTO(empWorkshopsTrainingTOs);
	objform.setEmpConferenceSeminarsAttendedTO(empSeminarsAttendedTO);
}

}

