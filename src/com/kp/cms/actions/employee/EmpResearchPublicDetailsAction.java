package com.kp.cms.actions.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.forms.employee.EmpResPubPendApprovalForm;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.handlers.employee.EmpResPubDetailsHandler;
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
import com.kp.cms.transactionsimpl.employee.EmpResPubDetailsImpl;

public class EmpResearchPublicDetailsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EmpResearchPublicDetailsAction.class);
	private static final String MESSAGE_KEY = "messages";
	private static final String PHOTOBYTES = "PhotoBytes";
	private static final String RESEARCHPHOTOBYTES = "researchPhotoBytes";
	
	IEmpResPubDetailsTransaction empTransaction=EmpResPubDetailsImpl.getInstance();
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	
	public ActionForward initResearchPublicDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
		ActionErrors errors = new ActionErrors();
		cleanUpPageData(empResPubForm);
		setUserId(request,empResPubForm);
		
		Employee emp=empTransaction.getEmployeeIdFromUserId(empResPubForm);
		if(emp!=null && emp.getId()>0)
		{
		empResPubForm.setEmployeeId(String.valueOf(emp.getId()));
		empResPubForm.setEmployeeName(emp.getFirstName());
		clear(empResPubForm);
		setDataToForm(empResPubForm);
		empResPubForm.setIsRejectScreen("false");
		empResPubForm.setIsAdminScreen("false");
		//EmpResPubDetailsHandler.getInstance().getEmployeeResearchDetails(empResPubForm);
		}else
		{
			errors.add("error", new ActionError("knowledgepro.employee.no.data"));
			addErrors(request, errors);	
		}
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
	}
	
	
	
	public ActionForward initResearchPublicRejectedDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
		ActionErrors errors = new ActionErrors();
		cleanUpPageData(empResPubForm);
		setUserId(request,empResPubForm);
		Employee emp=empTransaction.getEmployeeIdFromUserId(empResPubForm);
		if(emp!=null && emp.getId()>0)
		{
		empResPubForm.setEmployeeId(String.valueOf(emp.getId()));
		empResPubForm.setEmployeeName(emp.getFirstName());
		setDataToForm(empResPubForm);
		empResPubForm.setIsRejectScreen("true");
		EmpResPubDetailsHandler.getInstance().getEmployeeResearchDetailsRej(empResPubForm);
		}else
		{
			errors.add("error", new ActionError("knowledgepro.employee.no.data"));
			addErrors(request, errors);	
		}
		return mapping.findForward(CMSConstants.EMP_RES_PUB_REJECTED);
	}
	
	/**
	 * @param empResPubForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(EmpResPubDetailsForm empResPubForm) throws Exception {
		// 1. Set the ResPubMaster list
		List<EmpResPublicMasterTO> resPubMasterToList=EmpResPubDetailsHandler.getInstance().getEmpResPublicList();
		empResPubForm.setResPubMasterToList(resPubMasterToList);
	}
		/**
	 * @param empResPubForm
	 * @throws Exception
	 */
	public  void setDataToForm(EmpResPubDetailsForm empResPubForm)throws Exception {
		 //Initialising data 
		initializeResearchProject(empResPubForm);
		initializeBookMonographs(empResPubForm);
		initializeArticleJournals(empResPubForm);
		initializeChapArticlBookTO(empResPubForm);
		initializeBlogTO(empResPubForm);
		initializeFilmVideosDoc(empResPubForm);
		initializeArticlInPeriodicals(empResPubForm);
		initializeConfPresent(empResPubForm); 
		initializeInvitedTalk(empResPubForm); 
		initializeCasesNotesWorking(empResPubForm);
		initializeSeminarsOrganised(empResPubForm);
		initializePhdMPhilThesisGuided(empResPubForm);
		initializeOwnPhdMPilThesis(empResPubForm);
		initializeSeminarsAttended(empResPubForm);
		initializeWorkshopsAttended(empResPubForm);
		initializeAwardsAchievements(empResPubForm);
}
	/**
	 * @param empResPubForm
	 */
	private void cleanUpPageData(EmpResPubDetailsForm empResPubForm) {
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
			empResPubForm.setFingerPrintId(null);
			empResPubForm.setVerifyFlag("false");
			empResPubForm.setSelectedCategory(false);
			empResPubForm.setErrorMessage(null);
			empResPubForm.setErrMsg(null);
			empResPubForm.setIsEdit("false");
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
	
	@SuppressWarnings("deprecation")
	public ActionForward saveEmpResPubDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmpResPubDetailsForm empResPubForm =(EmpResPubDetailsForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=empResPubForm.validate(mapping, request);
		validateData(empResPubForm,errors);
		boolean flag=false;
		if(errors.isEmpty()){
			try {
				flag=EmpResPubDetailsHandler.getInstance().saveEmpResPub(empResPubForm,errors);
					empResPubForm.setVerifyFlag("false");
				if(flag){
				//Temp Comment	
					EmpResPubDetailsHandler.getInstance().sendMailToApprover(empResPubForm);
				//Temp Comment	
					EmpResPubDetailsHandler.getInstance().sendMailToEmployee(empResPubForm);
					ActionMessage message=new ActionMessage(CMSConstants.EMP_RESEARCH_SUBMIT_CONFIRM);
					messages.add(CMSConstants.MESSAGES,message);
					saveMessages(request, messages);
					cleanUpPageData(empResPubForm);
					return mapping.findForward(CMSConstants.EMP_RESEARCH_SUBMIT_CONFIRM);
				}else{
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
					saveMessages(request, messages);
					if(empResPubForm.getIsRejectScreen().equalsIgnoreCase("true")){
						cleanUpPageData(empResPubForm);
						return mapping.findForward(CMSConstants.EMP_RES_PUB_REJECTED);
						
					}else
					{
						cleanUpPageData(empResPubForm);
						return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
					}
					
				}
				
			}
			catch (BusinessException e) {
				errors.add("error", new ActionError("knowledgepro.research.approvernotAssigned"));
				
			}
			catch(ApplicationException e){
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
			}
			catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					empResPubForm.setErrorMessage(msg);
					empResPubForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
		}else{
			saveErrors(request, errors);
			if(empResPubForm.getIsRejectScreen().equalsIgnoreCase("true")){
				return mapping.findForward(CMSConstants.EMP_RES_PUB_REJECTED);
			}else
			{
			return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
			}
		}
	}
/**
 * @param empResPubForm
 */
private void initializeResearchProject(EmpResPubDetailsForm empResPubForm) {
		
		List<EmpResProjectTO> list=new ArrayList<EmpResProjectTO>();
		EmpResProjectTO empPreviousOrgTo=new EmpResProjectTO();
		empPreviousOrgTo.setAbstractObjectives("");
		empPreviousOrgTo.setInvestigators("");
		empPreviousOrgTo.setSponsors("");
		empPreviousOrgTo.setTitle("");
		empPreviousOrgTo.setIsApproved(false);
		empPreviousOrgTo.setIsRejected(false);
		empPreviousOrgTo.setIsResearchProject(false);
		empResPubForm.setEmpResearchProjectLength(String.valueOf(list.size()));
		list.add(empPreviousOrgTo);
		empResPubForm.setEmpResearchProjectTO(list);
	}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetResearchProject(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpResearchProjectTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpResProjectTO> list=empResPubForm.getEmpResearchProjectTO();
			EmpResProjectTO empPreviousOrgTo=new EmpResProjectTO();
			empPreviousOrgTo.setAbstractObjectives("");
			empPreviousOrgTo.setInvestigators("");
			empPreviousOrgTo.setSponsors("");
			empPreviousOrgTo.setTitle("");
			empPreviousOrgTo.setIsApproved(false);
			empPreviousOrgTo.setIsRejected(false);
			empPreviousOrgTo.setIsResearchProject(false);
			empResPubForm.setEmpResearchProjectLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("Research");
		}
	}
	log.info("End of resetExperienceInfo of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeResearchProject(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeExperienceInfo of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpResProjectTO> list=null;
	if(empResPubForm.getEmpResearchProjectTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpResearchProjectTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpResearchProjectLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("Research");
		}
	}
	log.info("End of removeExperienceInfo of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}
/**
 * @param empResPubForm
 */
private void initializeBookMonographs(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsBookMonographs(false);
	empResPubForm.setEmpBooksMonographsLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpBooksMonographsTO(list);
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetBookMonographs(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpBooksMonographsTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpBooksMonographsTO> list=empResPubForm.getEmpBooksMonographsTO();
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
			empPreviousOrgTo.setIsBookMonographs(false);
			empResPubForm.setEmpBooksMonographsLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("BookMonographs");
		}
	}
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeBookMonographs(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeExperienceInfo of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpBooksMonographsTO> list=null;
	if(empResPubForm.getEmpBooksMonographsTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpBooksMonographsTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpBooksMonographsLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("BookMonographs");
		}
	}
	log.info("End of removeExperienceInfo of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}
/**
 * @param empResPubForm
 */
private void initializeArticleJournals(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsArticleJournal(false);
	empResPubForm.setEmpArticleJournalsLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpArticleJournalsTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetArticleJournals(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpArticleJournalsTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpArticleJournalsTO> list=empResPubForm.getEmpArticleJournalsTO();
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
			empPreviousOrgTo.setIsArticleJournal(false);
			empResPubForm.setEmpArticleJournalsLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("ArticleJournals");
		}
	}
	log.info("End of resetExperienceInfo of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeArticleJournals(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeExperienceInfo of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpArticleJournalsTO> list=null;
	if(empResPubForm.getEmpArticleJournalsTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpArticleJournalsTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpArticleJournalsLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("ArticleJournals");
		}
	}
	log.info("End of removeExperienceInfo of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}
/**
 * @param empResPubForm
 */
private void initializeChapArticlBookTO(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsChapterArticleBook(false);
	empResPubForm.setEmpChapterArticlBookLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpChapterArticlBookTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetChapArticlBook(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpChapterArticlBookTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpChapterArticlBookTO> list=empResPubForm.getEmpChapterArticlBookTO();
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
			empPreviousOrgTo.setIsChapterArticleBook(false);
			empResPubForm.setEmpChapterArticlBookLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("ChapArticlBook");
		}
	}
	log.info("End of resetExperienceInfo of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeChapArticlBook(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeExperienceInfo of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpChapterArticlBookTO> list=null;
	if(empResPubForm.getEmpChapterArticlBookTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpChapterArticlBookTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpChapterArticlBookLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("ChapArticlBook");
		}
	}
	log.info("End of removeExperienceInfo of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}

/**
 * @param empResPubForm
 */
private void initializeBlogTO(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsBlog(false);
	empResPubForm.setEmpBlogLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpBlogTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetBlog(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpBlogTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpBlogTO> list=empResPubForm.getEmpBlogTO();
			EmpBlogTO empPreviousOrgTo=new EmpBlogTO();
			empPreviousOrgTo.setLanguage("");
			empPreviousOrgTo.setMonthYear("");
			empPreviousOrgTo.setNumberOfComments("");
			empPreviousOrgTo.setSubject("");
			empPreviousOrgTo.setUrl("");
			empPreviousOrgTo.setTitle("");
			empPreviousOrgTo.setIsApproved(false);
			empPreviousOrgTo.setIsRejected(false);
			empPreviousOrgTo.setIsBlog(false);
			empResPubForm.setEmpBlogLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("Blog");
		}
	}
	log.info("End of resetExperienceInfo of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeBlog(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeExperienceInfo of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpBlogTO> list=null;
	if(empResPubForm.getEmpBlogTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpBlogTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpBlogLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("Blog");
		}
	}
	log.info("End of removeExperienceInfo of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}

/**
 * @param empResPubForm
 */
private void initializeFilmVideosDoc(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsFilmVideoDoc(false);
	empResPubForm.setEmpFilmVideosDocLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpFilmVideosDocTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetFilmVideosDoc(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpFilmVideosDocTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpFilmVideosDocTO> list=empResPubForm.getEmpFilmVideosDocTO();
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
			empPreviousOrgTo.setIsFilmVideoDoc(false);
			empResPubForm.setEmpFilmVideosDocLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("FilmVideosDoc");
		}
	}
	log.info("End of resetFilmVideosDoc of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeFilmVideosDoc(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeFilmVideosDoc of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpFilmVideosDocTO> list=null;
	if(empResPubForm.getEmpFilmVideosDocTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpFilmVideosDocTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpFilmVideosDocLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("FilmVideosDoc");
		}
	}
	log.info("End of removeFilmVideosDoc of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}

/**
 * @param empResPubForm
 */
private void initializeArticlInPeriodicals(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsArticleInPeriodicals(false);
	empResPubForm.setEmpArticlPeriodicalsLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpArticlInPeriodicalsTO(list);
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetArticlInPeriodicals(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpArticlInPeriodicalsTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpArticlInPeriodicalsTO> list=empResPubForm.getEmpArticlInPeriodicalsTO();
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
			empPreviousOrgTo.setIsArticleInPeriodicals(false);
			empResPubForm.setEmpArticlPeriodicalsLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("ArticlInPeriodicals");
		}
	}
	log.info("End of resetArticlInPeriodicals of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeArticlInPeriodicals(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeArticlInPeriodicals of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpArticlInPeriodicalsTO> list=null;
	if(empResPubForm.getEmpArticlInPeriodicalsTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpArticlInPeriodicalsTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpArticlPeriodicalsLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("ArticlInPeriodicals");
		}
	}
	log.info("End of removeArticlInPeriodicals of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}
/**
 * @param empResPubForm
 */
private void initializeConfPresent(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsConferencePresentation(false);
	empResPubForm.setEmpConfPresentLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpConferencePresentationTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetConfPresent(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpConferencePresentationTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpConferencePresentationTO> list=empResPubForm.getEmpConferencePresentationTO();
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
			empPreviousOrgTo.setIsConferencePresentation(false);
			empResPubForm.setEmpConfPresentLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("ConfPresent");
		}
	}
	log.info("End of resetConfPresent of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeConfPresent(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeConfPresent of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpConferencePresentationTO> list=null;
	if(empResPubForm.getEmpConferencePresentationTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpConferencePresentationTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpConfPresentLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("ConfPresent");
		}
	}
	log.info("End of removeConfPresent of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}

/**
 * @param empResPubForm
 */
private void initializeInvitedTalk(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsInvitedTalk(false);
	empResPubForm.setEmpInvitedTalkLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpInvitedTalkTO(list);
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetInvitedTalk(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpInvitedTalkTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpInvitedTalkTO> list=empResPubForm.getEmpInvitedTalkTO();
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
			empPreviousOrgTo.setIsInvitedTalk(false);
			empResPubForm.setEmpInvitedTalkLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("InvitedTalk");
		}
	}
	log.info("End of resetInvitedTalk of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeInvitedTalk(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeInvitedTalk of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpInvitedTalkTO> list=null;
	if(empResPubForm.getEmpInvitedTalkTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpInvitedTalkTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpInvitedTalkLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("InvitedTalk");
		}
	}
	log.info("End of removeInvitedTalk of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}


/**
 * @param empResPubForm
 */
private void initializeCasesNotesWorking(EmpResPubDetailsForm empResPubForm) {
	
	List<EmpCasesNotesWorkingTO> list=new ArrayList<EmpCasesNotesWorkingTO>();
	EmpCasesNotesWorkingTO empPreviousOrgTo=new EmpCasesNotesWorkingTO();
	empPreviousOrgTo.setAuthorName("");
	empPreviousOrgTo.setTitle("");
	empPreviousOrgTo.setAbstractObjectives("");
	empPreviousOrgTo.setSponsors("");
	empPreviousOrgTo.setCaseNoteWorkPaper("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empPreviousOrgTo.setIsCasesNoteWorking(false);
	empResPubForm.setEmpCasesNotesWorkingLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpCasesNotesWorkingTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetCasesNotesWorking(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpCasesNotesWorkingTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpCasesNotesWorkingTO> list=empResPubForm.getEmpCasesNotesWorkingTO();
			EmpCasesNotesWorkingTO empPreviousOrgTo=new EmpCasesNotesWorkingTO();
			empPreviousOrgTo.setAuthorName("");
			empPreviousOrgTo.setTitle("");
			empPreviousOrgTo.setAbstractObjectives("");
			empPreviousOrgTo.setSponsors("");
			empPreviousOrgTo.setCaseNoteWorkPaper("");
			empPreviousOrgTo.setIsApproved(false);
			empPreviousOrgTo.setIsRejected(false);
			empPreviousOrgTo.setIsCasesNoteWorking(false);
			empResPubForm.setEmpCasesNotesWorkingLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("CasesNotesWorking");
		}
	}
	log.info("End of resetCasesNotesWorking of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeCasesNotesWorking(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeCasesNotesWorking of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpCasesNotesWorkingTO> list=null;
	if(empResPubForm.getEmpCasesNotesWorkingTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpCasesNotesWorkingTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpCasesNotesWorkingLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("CasesNotesWorking");
		}
	}
	log.info("End of removeCasesNotesWorking of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}
/**
 * @param empResPubForm
 */
private void initializeSeminarsOrganised(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsSeminarOrganized(false);
	empResPubForm.setEmpSeminarsOrganizedLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpSeminarsOrganizedTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetSeminarsOrganised(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpSeminarsOrganizedTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpSeminarsOrganizedTO> list=empResPubForm.getEmpSeminarsOrganizedTO();
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
			empPreviousOrgTo.setIsSeminarOrganized(false);
			empResPubForm.setEmpSeminarsOrganizedLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("SeminarsOrganised");
		}
	}
	log.info("End of resetSeminarsOrganised of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeSeminarsOrganised(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeSeminarsOrganised of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpSeminarsOrganizedTO> list=null;
	if(empResPubForm.getEmpSeminarsOrganizedTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpSeminarsOrganizedTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpSeminarsOrganizedLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("SeminarsOrganised");
		}
	}
	log.info("End of removeSeminarsOrganised of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}
/**
 * @param empResPubForm
 */
private void initializePhdMPhilThesisGuided(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsPhdMPhilThesisGuided(false);
	empResPubForm.setEmpPhdThesisGuidedLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpPhdMPhilThesisGuidedTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetPhdMPhilThesisGuided(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpPhdMPhilThesisGuidedTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpPhdMPhilThesisGuidedTO> list=empResPubForm.getEmpPhdMPhilThesisGuidedTO();
			EmpPhdMPhilThesisGuidedTO empPreviousOrgTo=new EmpPhdMPhilThesisGuidedTO();
			empPreviousOrgTo.setTitle("");
			empPreviousOrgTo.setSubject("");
			empPreviousOrgTo.setNameStudent("");
			empPreviousOrgTo.setCompanyInstitution("");
			empPreviousOrgTo.setPlace("");
			empPreviousOrgTo.setMonthYear("");
			empPreviousOrgTo.setIsApproved(false);
			empPreviousOrgTo.setIsRejected(false);
			empPreviousOrgTo.setIsPhdMPhilThesisGuided(false);
			empResPubForm.setEmpPhdThesisGuidedLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("PhdMPhilThesisGuided");
		}
	}
	log.info("End of resetPhdMPhilThesisGuided of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removePhdMPhilThesisGuided(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removePhdMPhilThesisGuided of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpPhdMPhilThesisGuidedTO> list=null;
	if(empResPubForm.getEmpPhdMPhilThesisGuidedTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpPhdMPhilThesisGuidedTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpPhdThesisGuidedLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("PhdMPhilThesisGuided");
		}
	}
	log.info("End of removePhdMPhilThesisGuided of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}
/**
 * @param empResPubForm
 */
private void initializeOwnPhdMPilThesis(EmpResPubDetailsForm empResPubForm) {
	
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
	empPreviousOrgTo.setIsOwnPhdMphilThesis(false);
	empResPubForm.setEmpOwnPhdThesisLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpOwnPhdMPilThesisTO(list);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward resetOwnPhdMPilThesis(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	if(empResPubForm.getEmpOwnPhdMPilThesisTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<EmpOwnPhdMPilThesisTO> list=empResPubForm.getEmpOwnPhdMPilThesisTO();
			EmpOwnPhdMPilThesisTO empPreviousOrgTo=new EmpOwnPhdMPilThesisTO();
			empPreviousOrgTo.setTitle("");
			empPreviousOrgTo.setSubject("");
			empPreviousOrgTo.setNameGuide("");
			empPreviousOrgTo.setCompanyInstitution("");
			empPreviousOrgTo.setPlace("");
			empPreviousOrgTo.setMonthYear("");
			empPreviousOrgTo.setIsApproved(false);
			empPreviousOrgTo.setIsRejected(false);
			empPreviousOrgTo.setIsOwnPhdMphilThesis(false);
			empResPubForm.setEmpOwnPhdThesisLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			empResPubForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			empResPubForm.setFocusValue("OwnPhdMPilThesis");
		}
	}
	log.info("End of resetOwnPhdMPilThesis of empResPubForm");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
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
public ActionForward removeOwnPhdMPilThesis(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeOwnPhdMPilThesis of INIT_EMP_RES_PUB");
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	List<EmpOwnPhdMPilThesisTO> list=null;
	if(empResPubForm.getEmpOwnPhdMPilThesisTO()!=null)
	if(empResPubForm.getMode()!=null){
		if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=empResPubForm.getEmpOwnPhdMPilThesisTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			empResPubForm.setEmpOwnPhdThesisLength(String.valueOf(list.size()-1));
			empResPubForm.setFocusValue("OwnPhdMPilThesis");
		}
	}
	log.info("End of removeOwnPhdMPilThesis of INIT_EMP_RES_PUB");
	if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	else
		return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);

}

private void initializeSeminarsAttended(EmpResPubDetailsForm empResPubForm) {
	
	List<EmpConferenceSeminarsAttendedTO> list=new ArrayList<EmpConferenceSeminarsAttendedTO>();
	EmpConferenceSeminarsAttendedTO empPreviousOrgTo=new EmpConferenceSeminarsAttendedTO();
	empPreviousOrgTo.setNameOfPgm("");
	empPreviousOrgTo.setDateOfPgm("");
	empPreviousOrgTo.setDuration("");
	empPreviousOrgTo.setOrganisedBy("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empPreviousOrgTo.setIsSeminarAttended(false);
	empResPubForm.setEmpConfSeminarsAttendedLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpConferenceSeminarsAttendedTO(list);
}

/**
* @param mapping
* @param form
* @param request
* @param response
* @return
* @throws Exception
*/
public ActionForward resetSeminarsAttended(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
if(empResPubForm.getEmpResearchProjectTO()!=null)
if(empResPubForm.getMode()!=null){
	if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		List<EmpConferenceSeminarsAttendedTO> list=empResPubForm.getEmpConferenceSeminarsAttendedTO();
		EmpConferenceSeminarsAttendedTO empPreviousOrgTo=new EmpConferenceSeminarsAttendedTO();
		empPreviousOrgTo.setNameOfPgm("");
		empPreviousOrgTo.setDateOfPgm("");
		empPreviousOrgTo.setDuration("");
		empPreviousOrgTo.setOrganisedBy("");
		empPreviousOrgTo.setIsApproved(false);
		empPreviousOrgTo.setIsRejected(false);
		empPreviousOrgTo.setIsSeminarAttended(false);
		empResPubForm.setEmpConfSeminarsAttendedLength(String.valueOf(list.size()));
		list.add(empPreviousOrgTo);
		empResPubForm.setMode(null);
		//String size=String.valueOf(list.size()-1);
		empResPubForm.setFocusValue("SeminarsAttended");
	}
}
log.info("End of resetExperienceInfo of empResPubForm");
if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
	return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
else
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
public ActionForward removeSeminarsAttended(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
log.info("Befinning of removeSeminarsAttended of INIT_EMP_RES_PUB");
EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
List<EmpConferenceSeminarsAttendedTO> list=null;
if(empResPubForm.getEmpConferenceSeminarsAttendedTO()!=null)
if(empResPubForm.getMode()!=null){
	if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		list=empResPubForm.getEmpConferenceSeminarsAttendedTO();
		if(list.size()>0)
		list.remove(list.size()-1);
		empResPubForm.setEmpConfSeminarsAttendedLength(String.valueOf(list.size()-1));
		empResPubForm.setFocusValue("SeminarsAttended");
	}
}
log.info("End of removeSeminarsAttended of INIT_EMP_RES_PUB");
if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
	return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
else
	return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}

private void initializeWorkshopsAttended(EmpResPubDetailsForm empResPubForm) {
	
	List<EmpWorkshopsFdpTrainingTO> list=new ArrayList<EmpWorkshopsFdpTrainingTO>();
	EmpWorkshopsFdpTrainingTO empPreviousOrgTo=new EmpWorkshopsFdpTrainingTO();
	empPreviousOrgTo.setNameOfPgm("");
	empPreviousOrgTo.setDateOfPgm("");
	empPreviousOrgTo.setDuration("");
	empPreviousOrgTo.setOrganisedBy("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empPreviousOrgTo.setIsWorkshopTraining(false);
	empResPubForm.setEmpWorkshopsAttendedLength(String.valueOf(list.size()));
	list.add(empPreviousOrgTo);
	empResPubForm.setEmpWorkshopsTO(list);
}

/**
* @param mapping
* @param form
* @param request
* @param response
* @return
* @throws Exception
*/
public ActionForward resetWorkshopsAttended(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
if(empResPubForm.getEmpWorkshopsTO()!=null)
if(empResPubForm.getMode()!=null){
	if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		List<EmpWorkshopsFdpTrainingTO> list=empResPubForm.getEmpWorkshopsTO();
		EmpWorkshopsFdpTrainingTO empPreviousOrgTo=new EmpWorkshopsFdpTrainingTO();
		empPreviousOrgTo.setNameOfPgm("");
		empPreviousOrgTo.setDateOfPgm("");
		empPreviousOrgTo.setDuration("");
		empPreviousOrgTo.setOrganisedBy("");
		empPreviousOrgTo.setIsApproved(false);
		empPreviousOrgTo.setIsRejected(false);
		empPreviousOrgTo.setIsWorkshopTraining(false);
		empResPubForm.setEmpWorkshopsAttendedLength(String.valueOf(list.size()));
		list.add(empPreviousOrgTo);
		empResPubForm.setMode(null);
		//String size=String.valueOf(list.size()-1);
		empResPubForm.setFocusValue("WorkshopsAttended");
	}
}
log.info("End of resetExperienceInfo of empResPubForm");
if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
	return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
else
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
public ActionForward removeWorkshopsAttended(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
log.info("Befinning of removeWorkshopsAttended of INIT_EMP_RES_PUB");
EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
List<EmpWorkshopsFdpTrainingTO> list=null;
if(empResPubForm.getEmpWorkshopsTO()!=null)
if(empResPubForm.getMode()!=null){
	if (empResPubForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		list=empResPubForm.getEmpWorkshopsTO();
		if(list.size()>0)
		list.remove(list.size()-1);
		empResPubForm.setEmpWorkshopsAttendedLength(String.valueOf(list.size()-1));
		empResPubForm.setFocusValue("WorkshopsAttended");
	}
}
log.info("End of removeWorkshopsAttended of INIT_EMP_RES_PUB");
if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
	return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
else
	return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}







private void initializeAwardsAchievements(EmpResPubDetailsForm empResPubForm) {
	
	List<EmpAwardsAchievementsOthersTO> list=new ArrayList<EmpAwardsAchievementsOthersTO>();
	EmpAwardsAchievementsOthersTO empPreviousOrgTo=new EmpAwardsAchievementsOthersTO();
	empPreviousOrgTo.setName("");
	empPreviousOrgTo.setDescription("");
	empPreviousOrgTo.setPlace("");
	empPreviousOrgTo.setMonthYear("");
	empPreviousOrgTo.setIsApproved(false);
	empPreviousOrgTo.setIsRejected(false);
	empPreviousOrgTo.setIsAwardsAchievementsOthers(false);
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
EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
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
		empPreviousOrgTo.setIsAwardsAchievementsOthers(false);
		empResPubForm.setEmpAwardsLength(String.valueOf(list.size()));
		list.add(empPreviousOrgTo);
		empResPubForm.setMode(null);
		//String size=String.valueOf(list.size()-1);
		empResPubForm.setFocusValue("AwardsAchievements");
	}
}
log.info("End of resetExperienceInfo of empResPubForm");
if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
	return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
else
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
EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
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
if(empResPubForm.getIsAdminScreen().equalsIgnoreCase("true"))
	return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
else
	return mapping.findForward(CMSConstants.INIT_EMP_RES_PUB);
}


private boolean splCharValidation(String name, String splChar) {
	boolean haveSplChar = false;
	Pattern pattern = Pattern.compile("[^A-Za-z0-9.?!@#$%&*()_-+={}[]:;<>,./\\\b  \t  \n  \f  \r  \"  \'  \\]+");
	Matcher matcher = pattern.matcher(name);
	haveSplChar = matcher.find();
	return haveSplChar;
}

@SuppressWarnings("deprecation")
private void validateData(EmpResPubDetailsForm empResPubForm,ActionErrors errors) {
	
	if (empResPubForm.getSubmitName().equalsIgnoreCase("ArticleJournals")) {
		 if (empResPubForm.getEmpArticleJournalsTO() != null && !empResPubForm.getEmpArticleJournalsTO().isEmpty()) {
			Iterator<EmpArticleJournalsTO> itr = empResPubForm.getEmpArticleJournalsTO().iterator();
			while (itr.hasNext()) {
				EmpArticleJournalsTO to = (EmpArticleJournalsTO) itr.next();
				if(to.getType()==null || to.getType().isEmpty()){
					if (errors.get(CMSConstants.EMP_JOURNAL_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_JOURNAL_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_JOURNAL_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_JOURNAL_TYPE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("ArticlInPeriodicals")) {
		if (empResPubForm.getEmpArticlInPeriodicalsTO() != null	&& !empResPubForm.getEmpArticlInPeriodicalsTO().isEmpty()) {
			Iterator<EmpArticlInPeriodicalsTO> itr = empResPubForm.getEmpArticlInPeriodicalsTO().iterator();
			while (itr.hasNext()) {
				EmpArticlInPeriodicalsTO to = (EmpArticlInPeriodicalsTO) itr.next();
				if(to.getType()==null || to.getType().isEmpty()){
					if (errors.get(CMSConstants.EMP_PERIODICALS_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_PERIODICALS_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_PERIODICALS_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_PERIODICALS_TYPE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("BooksMonographs")) {
		if (empResPubForm.getEmpBooksMonographsTO() != null && !empResPubForm.getEmpBooksMonographsTO().isEmpty()) {
			Iterator<EmpBooksMonographsTO> itr = empResPubForm.getEmpBooksMonographsTO().iterator();
			while (itr.hasNext()) {
				EmpBooksMonographsTO to = (EmpBooksMonographsTO) itr.next();
				if(to.getCoAuthored()==null || to.getCoAuthored().isEmpty()){
					if (errors.get(CMSConstants.EMP_BOOKSMONOGRAPHS_COAUTHORED_REQUIRED) != null && !errors.get(CMSConstants.EMP_BOOKSMONOGRAPHS_COAUTHORED_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_BOOKSMONOGRAPHS_COAUTHORED_REQUIRED, new ActionError(CMSConstants.EMP_BOOKSMONOGRAPHS_COAUTHORED_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("ChapterArticlBook")) {
		if (empResPubForm.getEmpChapterArticlBookTO() != null && !empResPubForm.getEmpChapterArticlBookTO().isEmpty()) {
			Iterator<EmpChapterArticlBookTO> itr = empResPubForm.getEmpChapterArticlBookTO().iterator();
			while (itr.hasNext()) {
				EmpChapterArticlBookTO to = (EmpChapterArticlBookTO) itr.next();
				if(to.getCoAuthored()==null || to.getCoAuthored().isEmpty()){
					if (errors.get(CMSConstants.EMP_CHAPTERS_COAUTHORED_REQUIRED) != null && !errors.get(CMSConstants.EMP_CHAPTERS_COAUTHORED_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_CHAPTERS_COAUTHORED_REQUIRED, new ActionError(CMSConstants.EMP_CHAPTERS_COAUTHORED_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("ConferencePresentation")) {
		if (empResPubForm.getEmpConferencePresentationTO() != null	&& !empResPubForm.getEmpConferencePresentationTO().isEmpty()) {
			Iterator<EmpConferencePresentationTO> itr = empResPubForm.getEmpConferencePresentationTO().iterator();
			while (itr.hasNext()) {
				EmpConferencePresentationTO to = (EmpConferencePresentationTO) itr.next();
				if(to.getType()==null || to.getType().isEmpty()){
					if (errors.get(CMSConstants.EMP_CONFERENCE_PRESENTATION_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_CONFERENCE_PRESENTATION_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_CONFERENCE_PRESENTATION_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_CONFERENCE_PRESENTATION_TYPE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
				if(to.getTypeOfPgm()==null || to.getTypeOfPgm().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_TYPEOFPGM_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_TYPEOFPGM_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_TYPEOFPGM_REQUIRED, new ActionError(CMSConstants.EMP_RES_TYPEOFPGM_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("InvitedTalk")) { 
		if (empResPubForm.getEmpInvitedTalkTO() != null	&& !empResPubForm.getEmpInvitedTalkTO().isEmpty()) {
			Iterator<EmpInvitedTalkTO> itr = empResPubForm.getEmpInvitedTalkTO().iterator();
			while (itr.hasNext()) {
				EmpInvitedTalkTO to = (EmpInvitedTalkTO) itr.next();
				if(to.getType()==null || to.getType().isEmpty()){
					if (errors.get(CMSConstants.EMP_INVITEDTALK_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_INVITEDTALK_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_INVITEDTALK_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_INVITEDTALK_TYPE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("ResearchProject")) {
		if (empResPubForm.getEmpResearchProjectTO() != null	&& !empResPubForm.getEmpResearchProjectTO().isEmpty()) {
			Iterator<EmpResProjectTO> itr = empResPubForm.getEmpResearchProjectTO().iterator();
			while (itr.hasNext()) {
				EmpResProjectTO to = (EmpResProjectTO) itr.next();
				if(to.getAmountGranted()==null || to.getAmountGranted().isEmpty()){
					if (errors.get(CMSConstants.EMP_RESEARCH_AMOUNTGRANTED_REQUIRED) != null && !errors.get(CMSConstants.EMP_RESEARCH_AMOUNTGRANTED_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RESEARCH_AMOUNTGRANTED_REQUIRED, new ActionError(CMSConstants.EMP_RESEARCH_AMOUNTGRANTED_REQUIRED));
					}	
				}
				if(to.getInternalExternal()==null || to.getInternalExternal().isEmpty()){
					if (errors.get(CMSConstants.EMP_RESEARCH_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_RESEARCH_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RESEARCH_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_RESEARCH_TYPE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("OwnPhdMPilThesis")) {
		if (empResPubForm.getEmpOwnPhdMPilThesisTO() != null && !empResPubForm.getEmpOwnPhdMPilThesisTO().isEmpty()) {
			Iterator<EmpOwnPhdMPilThesisTO> itr = empResPubForm.getEmpOwnPhdMPilThesisTO().iterator();
			while (itr.hasNext()) {
				EmpOwnPhdMPilThesisTO to = (EmpOwnPhdMPilThesisTO) itr.next();
				if(to.getType()==null || to.getType().isEmpty()){
					if (errors.get(CMSConstants.EMP_OWN_PHD_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_OWN_PHD_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_OWN_PHD_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_OWN_PHD_TYPE_REQUIRED));
					}	
				}
				if(to.getSubmissionDate()==null){
					if (errors.get(CMSConstants.EMP_OWN_PHD_SUBMITTED_DATE_REQUIRED) != null && !errors.get(CMSConstants.EMP_OWN_PHD_SUBMITTED_DATE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_OWN_PHD_SUBMITTED_DATE_REQUIRED, new ActionError(CMSConstants.EMP_OWN_PHD_SUBMITTED_DATE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("PhdMPhilThesisGuided")) {
		if (empResPubForm.getEmpPhdMPhilThesisGuidedTO() != null && !empResPubForm.getEmpPhdMPhilThesisGuidedTO().isEmpty()) {
			Iterator<EmpPhdMPhilThesisGuidedTO> itr = empResPubForm.getEmpPhdMPhilThesisGuidedTO().iterator();
			while (itr.hasNext()) {
				EmpPhdMPhilThesisGuidedTO to = (EmpPhdMPhilThesisGuidedTO) itr.next();
				/*if(to.getGuidedAbudjicated()==null || to.getGuidedAbudjicated()=="" || to.getGuidedAbudjicated().isEmpty()){
					if (errors.get(CMSConstants.EMP_PHD_GUIDED_REQUIRED) != null && !errors.get(CMSConstants.EMP_PHD_GUIDED_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_PHD_GUIDED_REQUIRED, new ActionError(CMSConstants.EMP_PHD_GUIDED_REQUIRED));
					}	
				}*/
				if(to.getType()==null || to.getType().isEmpty()){
					if (errors.get(CMSConstants.EMP_PHD_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_PHD_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_PHD_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_PHD_TYPE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("SeminarsAttended")) {
		if (empResPubForm.getEmpConferenceSeminarsAttendedTO() != null && !empResPubForm.getEmpConferenceSeminarsAttendedTO().isEmpty()) {
			Iterator<EmpConferenceSeminarsAttendedTO> itr = empResPubForm.getEmpConferenceSeminarsAttendedTO().iterator();
			while (itr.hasNext()) {
				EmpConferenceSeminarsAttendedTO to = (EmpConferenceSeminarsAttendedTO) itr.next();
				if(to.getType()==null || to.getType().isEmpty()){
					if (errors.get(CMSConstants.EMP_SEMINARS_ATTENDED_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_SEMINARS_ATTENDED_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_SEMINARS_ATTENDED_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_SEMINARS_ATTENDED_TYPE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("CasesNotesWorking")) {
		if (empResPubForm.getEmpCasesNotesWorkingTO() != null && !empResPubForm.getEmpCasesNotesWorkingTO().isEmpty()) {
			Iterator<EmpCasesNotesWorkingTO> itr = empResPubForm.getEmpCasesNotesWorkingTO().iterator();
			while (itr.hasNext()) {
				EmpCasesNotesWorkingTO to = (EmpCasesNotesWorkingTO) itr.next();
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("Blog")) {
		if (empResPubForm.getEmpBlogTO() != null && !empResPubForm.getEmpBlogTO().isEmpty()) {
			Iterator<EmpBlogTO> itr = empResPubForm.getEmpBlogTO().iterator();
			while (itr.hasNext()) {
				EmpBlogTO to = (EmpBlogTO) itr.next();
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("FilmVideosDoc")) {
		if (empResPubForm.getEmpFilmVideosDocTO() != null && !empResPubForm.getEmpFilmVideosDocTO().isEmpty()) {
			Iterator<EmpFilmVideosDocTO> itr = empResPubForm.getEmpFilmVideosDocTO().iterator();
			while (itr.hasNext()) {
				EmpFilmVideosDocTO to = (EmpFilmVideosDocTO) itr.next();
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("SeminarsOrganized")) {
		if (empResPubForm.getEmpSeminarsOrganizedTO() != null && !empResPubForm.getEmpSeminarsOrganizedTO().isEmpty()) {
			Iterator<EmpSeminarsOrganizedTO> itr = empResPubForm.getEmpSeminarsOrganizedTO().iterator();
			while (itr.hasNext()) {
				EmpSeminarsOrganizedTO to = (EmpSeminarsOrganizedTO) itr.next();
				
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty() || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
				
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("WorkshopsAttended")) {
		if (empResPubForm.getEmpWorkshopsTO() != null && !empResPubForm.getEmpWorkshopsTO().isEmpty()) {
			Iterator<EmpWorkshopsFdpTrainingTO> itr = empResPubForm.getEmpWorkshopsTO().iterator();
			while (itr.hasNext()) {
				EmpWorkshopsFdpTrainingTO to = (EmpWorkshopsFdpTrainingTO) itr.next();
				if(to.getType()==null || to.getType().isEmpty()){
					if (errors.get(CMSConstants.EMP_WORKSHOPS_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMP_WORKSHOPS_TYPE_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_WORKSHOPS_TYPE_REQUIRED, new ActionError(CMSConstants.EMP_WORKSHOPS_TYPE_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
				
			}
		}
	}
	if (empResPubForm.getSubmitName().equalsIgnoreCase("AwardsAchievements")) {
		if (empResPubForm.getEmpAwardsAchievementsOthersTO() != null && !empResPubForm.getEmpAwardsAchievementsOthersTO().isEmpty()) {
			Iterator<EmpAwardsAchievementsOthersTO> itr = empResPubForm.getEmpAwardsAchievementsOthersTO().iterator();
			while (itr.hasNext()) {
				EmpAwardsAchievementsOthersTO to = (EmpAwardsAchievementsOthersTO) itr.next();
				if(to.getOrganisationAwarded()==null || to.getOrganisationAwarded().isEmpty()){
					if (errors.get(CMSConstants.EMP_AWARDS_ORGANISATION_AWARDED_REQUIRED) != null && !errors.get(CMSConstants.EMP_AWARDS_ORGANISATION_AWARDED_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_AWARDS_ORGANISATION_AWARDED_REQUIRED, new ActionError(CMSConstants.EMP_AWARDS_ORGANISATION_AWARDED_REQUIRED));
					}	
				}
				if(to.getAcademicYear()==null || to.getAcademicYear().isEmpty()){
					if (errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED) != null && !errors.get(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED).hasNext()) {
						errors.add(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED, new ActionError(CMSConstants.EMP_RES_ACADEMIC_YEAR_REQUIRED));
					}	
				}
				
			}
		}
	}
	
}


//-------------------------------Code for Admin Screen-------------------------------------------------------------------

public ActionForward LoadResearchPublicAdmin(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	EmpResPubDetailsForm empResPubForm=(EmpResPubDetailsForm)form;
	boolean flag=false;
	ActionErrors errors = new ActionErrors();
	cleanUpPageData(empResPubForm);
	setUserId(request,empResPubForm);
	HttpSession session = request.getSession();
	
	session.removeAttribute(EmpResearchPublicDetailsAction.RESEARCHPHOTOBYTES);
	empResPubForm.setIsAdminScreen("true");
	/*Employee emp=empTransaction.getEmployeeIdFromUserId(empResPubForm);
	if(emp!=null && emp.getId()>0)
	{*/
//  empResPubForm.setEmployeeId(String.valueOf(emp.getId()));
//	empResPubForm.setEmployeeName(emp.getFirstName());
  setDataToForm(empResPubForm);
	flag=EmpResPubDetailsHandler.getInstance().getEmpResDetailsAdminEdit(empResPubForm);
	if(!flag){
		errors.add("error", new ActionError("knowledgepro.employee.no.data"));
		addErrors(request, errors);	
	}
	if (empResPubForm.getResearchPhotoBytes()!= null)
	{
	
		if (session != null) {
			session.setAttribute(EmpResearchPublicDetailsAction.RESEARCHPHOTOBYTES, empResPubForm.getResearchPhotoBytes());
		}	
	}
	return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
}

public ActionForward initResearch(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	EmpResPubDetailsForm stForm = (EmpResPubDetailsForm) form;
	cleanupEditSessionData(request);
	return mapping.findForward(CMSConstants.INIT_RESEARCH_DETAILS);
}


public ActionForward initResearchPublicAdmin(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	EmpResPubDetailsForm objform = (EmpResPubDetailsForm) form;
	cleanupEditSessionData(request);
	objform.setIsAdminScreen("true");
	objform.setTempEmployeeId(null);
	objform.setTempFingerPrintId(null);
	objform.setTempName(null);
	objform.setTempDesignationPfId(null);
	objform.setTempDepartmentId(null);
	objform.setTempActive("1");
	objform.setTempStreamId(null);
	objform.setTempTeachingStaff("1");
	objform.setTempEmptypeId(null);
	
	setDataToInitForm(objform);
	return mapping.findForward(CMSConstants.INIT_RESEARCH_DETAILS);
}
public  void setDataToInitForm(EmpResPubDetailsForm employeeInfoEditForm)throws Exception {
	EmpResPubDetailsHandler.getInstance().getInitialPageData(employeeInfoEditForm);
	employeeInfoEditForm.setTempActive("1");
	employeeInfoEditForm.setIsEmployee("true");
}

private void cleanupEditSessionData(HttpServletRequest request) {
	log.info("enter cleanupEditSessionData...");
	HttpSession session = request.getSession(false);
	if (session == null) {
		return;
	} else {
		if (session.getAttribute(EmpResearchPublicDetailsAction.RESEARCHPHOTOBYTES) != null)
			session.removeAttribute(EmpResearchPublicDetailsAction.RESEARCHPHOTOBYTES);
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
public ActionForward getSearchedEmployee(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	EmpResPubDetailsForm stForm = (EmpResPubDetailsForm) form;
	cleanupEditSessionData(request);
	ActionMessages errors = stForm.validate(mapping, request);
try {
	if (errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping
					.findForward(CMSConstants.INIT_RESEARCH_DETAILS);
		}
		List<EmployeeTO> employeeToList = EmpResPubDetailsHandler.getInstance().getSearchedEmployee(stForm);
		if (employeeToList == null || employeeToList.isEmpty()) {
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			message = new ActionMessage(
					CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			messages.add(EmpResearchPublicDetailsAction.MESSAGE_KEY, message);
			saveMessages(request, messages);
			return mapping
					.findForward(CMSConstants.INIT_RESEARCH_DETAILS);
		}
		stForm.setEmployeeToList(employeeToList);
	} catch (ApplicationException e) {
		log.error("error in getSearchedStudents...", e);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		message = new ActionMessage(
				CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
		messages.add(EmpResearchPublicDetailsAction.MESSAGE_KEY, message);
		saveMessages(request, messages);

		return mapping
				.findForward(CMSConstants.INIT_RESEARCH_DETAILS);

	} catch (Exception e) {
		log.error("error in getSearchedStudents...", e);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		message = new ActionMessage(
				CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
		messages.add(EmpResearchPublicDetailsAction.MESSAGE_KEY, message);
		saveMessages(request, messages);

		return mapping
				.findForward(CMSConstants.INIT_RESEARCH_DETAILS);

	}
	log.info("exit getSearchedStudents..");
	return mapping.findForward(CMSConstants.RESEARCH_EMPLOYEE_EDITLISTPAGE);
}
public ActionForward saveEmpResAdminEditAdmin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
	EmpResPubDetailsForm empResPubForm =(EmpResPubDetailsForm)form;
	ActionMessages messages=new ActionMessages();
	ActionErrors errors=empResPubForm.validate(mapping, request);
	validateData(empResPubForm,errors);
	boolean flag=false;
	if(errors.isEmpty()){
		try {
			flag=EmpResPubDetailsHandler.getInstance().saveEmpResPubAdmin(empResPubForm,errors);
				empResPubForm.setVerifyFlag("false");
			if(flag){
				empResPubForm.setIsEdit("true");
				ActionMessage message=new ActionMessage(CMSConstants.EMP_RESEARCH_SUBMIT_CONFIRM);
				messages.add(CMSConstants.MESSAGES,message);
				saveMessages(request, messages);
				cleanUpPageData(empResPubForm);
				empResPubForm.setIsEdit("true");
				return mapping.findForward(CMSConstants.EMP_RESEARCH_SUBMIT_CONFIRM);
			}else{
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
			}
			
		}
		catch (BusinessException e) {
			errors.add("error", new ActionError("knowledgepro.research.approvernotAssigned"));
			
		}
		catch(ApplicationException e){
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
		}
		catch (Exception exception) {
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				empResPubForm.setErrorMessage(msg);
				empResPubForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else
			throw exception;
		}
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	}else{
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.LOAD_RESEARCH_DETAILS_ADMIN_EDIT);
	}
}

public void clear(EmpResPubDetailsForm objform){
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
