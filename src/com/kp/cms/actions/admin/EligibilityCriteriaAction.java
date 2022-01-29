package com.kp.cms.actions.admin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.EligibilityCriteriaForm;
import com.kp.cms.handlers.admin.EligibilityCriteriaHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.EligibilityCriteriaTO;
import com.kp.cms.to.admin.EligibleSubjectsTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * Date Created : 12 feb 2009 This action class used for
 *         configuring Eligibility Criteria
 *         
 * 
 */
@SuppressWarnings("deprecation")
public class EligibilityCriteriaAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EligibilityCriteriaAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to criteriaEntry
	 * @throws Exception
	 */

	public ActionForward initCriteria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		EligibilityCriteriaForm eligibilityCriteriaForm = (EligibilityCriteriaForm)form; 
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setCriteriaToRequest(request, eligibilityCriteriaForm); //setting criteriaList to request for UI display
			setUserId(request, eligibilityCriteriaForm); //setting user Id to form for updating last changed details
		} catch (Exception e) {
			log.error("error in initCriteria method...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				eligibilityCriteriaForm.setErrorMessage(msg);
				eligibilityCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				eligibilityCriteriaForm.setErrorMessage(msg);
				eligibilityCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}

		return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);

	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method inserts a record into eligibility criteria table. this
	 *         method will be called while submitting the form
	 * @throws Exception
	 */

	public ActionForward addCriteria(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws Exception {

		EligibilityCriteriaForm eligibilityCriteriaForm = (EligibilityCriteriaForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = eligibilityCriteriaForm.validate(mapping, request);
		boolean isAdded;
		try {
			
			if(errors.isEmpty()){
				validateSubjectAndPercentage(eligibilityCriteriaForm, errors);  //validation for subject & percentage
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setCriteriaToRequest(request, eligibilityCriteriaForm);
				setSubjectMapToForm(eligibilityCriteriaForm.getCourseId(), eligibilityCriteriaForm);
				return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);
			}
			isAdded = EligibilityCriteriaHandler.getInstance().addEligibilityCriteria(eligibilityCriteriaForm, "Add");

			setCriteriaToRequest(request, eligibilityCriteriaForm);

		} catch (Exception e) {
			log.error("error in final submit of ELigibility criteria page...", e);
			
			if (e instanceof DuplicateException) {
				errors.add("error", new ActionError("knowledgepro.admin.course.year.addexist"));
				saveErrors(request, errors); 
				setCriteriaToRequest(request, eligibilityCriteriaForm);
				setSubjectMapToForm(eligibilityCriteriaForm.getCourseId(), eligibilityCriteriaForm);
				return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);
			} else if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				eligibilityCriteriaForm.setErrorMessage(msg);
				eligibilityCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				eligibilityCriteriaForm.setErrorMessage(msg);
				eligibilityCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.criteria.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			eligibilityCriteriaForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.criteria.addfailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);

	}
	
	/**
	 * getting all the records from table and setting to request for UI display
	 * @param request
	 * @param eligibilityCriteriaForm
	 * @throws Exception 
	 * @throws Exception
	 */
	public void setCriteriaToRequest(HttpServletRequest request, EligibilityCriteriaForm eligibilityCriteriaForm) throws Exception{
		List<EligibilityCriteriaTO> criteriaList = EligibilityCriteriaHandler.getInstance().getEligibilityCriteria(0, eligibilityCriteriaForm, true);
		request.setAttribute("criteriaList", criteriaList);
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response  This will load a particular record for edit.
	 * @return to mapping
	 * @throws Exception
	 */
	public ActionForward loadCriteria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {

		EligibilityCriteriaForm criteriaForm = (EligibilityCriteriaForm) form;
		try{
			setRequiredDataToForm(criteriaForm, request);
			setCriteriaToRequest(request, criteriaForm);
			setprogramMapToRequest(request, criteriaForm);
			setCourseMapToRequest(request, criteriaForm);
		}
		catch (Exception e) {
			log.error("Error occured in loadCriteria of EligibilityCriteria Action");
		}
		request.setAttribute("criteriaOperation", "edit");
		return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    This will set all the required data for edit.
	 * @return to mapping
	 * @throws Exception
	 */

	public void setRequiredDataToForm(EligibilityCriteriaForm eligibilityCriteriaForm, HttpServletRequest request) throws Exception {
		eligibilityCriteriaForm.setSelectedSubjects(null);
		
		int criteriaId = Integer.parseInt(request.getParameter("id"));
		List<EligibilityCriteriaTO> criteriaList = EligibilityCriteriaHandler.getInstance().getEligibilityCriteria(criteriaId, eligibilityCriteriaForm, false); 
		Iterator<EligibilityCriteriaTO> criteriaIt = criteriaList.iterator();
		
		while (criteriaIt.hasNext()) {
			EligibilityCriteriaTO criteriaTO = criteriaIt.next();
			if(criteriaTO.getTotalPercentage() != null){
				eligibilityCriteriaForm.setTotalPercentage(criteriaTO.getTotalPercentage().toString());
			}
			if(criteriaTO.getPercentageWithoutLanguage() != null){
				eligibilityCriteriaForm.setPercentageWithoutLanguage(criteriaTO.getPercentageWithoutLanguage().toString());
			}
			eligibilityCriteriaForm.setCourseId(Integer.toString(criteriaTO.getCourseTO().getId()));
			eligibilityCriteriaForm.setProgramTypeId(Integer.toString(criteriaTO.getCourseTO().getProgramTo().getProgramTypeTo().getProgramTypeId()));
			eligibilityCriteriaForm.setProgramId(Integer.toString(criteriaTO.getCourseTO().getProgramTo().getId()));
			eligibilityCriteriaForm.setPerWithoutMandatory("no");  //setting for showing mandatory sign based on the condition
			if(criteriaTO.getYear()!= 0){
				eligibilityCriteriaForm.setYear(Integer.toString(criteriaTO.getYear()));
			}
			int subjectArrayLen = criteriaTO.getEligibleSubjectsTOList().size(); 
			String subjectIdarray[] = new String[subjectArrayLen];
			Iterator<EligibleSubjectsTO> itr = criteriaTO.getEligibleSubjectsTOList().iterator();
			int count = 0;
			while (itr.hasNext()){
				EligibleSubjectsTO eligibleSubjectsTO = itr.next();
				if(eligibleSubjectsTO.getIsActive()){
					subjectIdarray[count] = Integer.toString(eligibleSubjectsTO.getDetailedSubjectsTO().getId());
					eligibilityCriteriaForm.setPerWithoutMandatory("yes"); //setting for showing mandatory sign based on the condition
				}
				count = count + 1;
			}
			
			setSubjectMapToForm(Integer.toString(criteriaTO.getCourseTO().getId()), eligibilityCriteriaForm);
			
			eligibilityCriteriaForm.setSelectedSubjects(subjectIdarray);
			eligibilityCriteriaForm.setEligibilityCriteriaTO(criteriaTO);
		}

	}


	/**
	 * this method used to update criteria to table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward updateCriteria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		EligibilityCriteriaForm eligibilityCriteriaForm = (EligibilityCriteriaForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = eligibilityCriteriaForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			//setting data to form while clicking on reset button in update
			if(isCancelled(request)){
				setRequiredDataToForm(eligibilityCriteriaForm, request);
				setCriteriaToRequest(request, eligibilityCriteriaForm);
				setprogramMapToRequest(request, eligibilityCriteriaForm);
				setCourseMapToRequest(request, eligibilityCriteriaForm);
				request.setAttribute("criteriaOperation", "edit");
				return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);
			}
			if(errors.isEmpty()){
				validateSubjectAndPercentage(eligibilityCriteriaForm, errors);  //validation for percentage & subjects
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setSubjectMapToForm(eligibilityCriteriaForm.getCourseId(), eligibilityCriteriaForm);
				setCriteriaToRequest(request, eligibilityCriteriaForm);
				setprogramMapToRequest(request, eligibilityCriteriaForm);
				setCourseMapToRequest(request, eligibilityCriteriaForm);
				request.setAttribute("criteriaOperation", "edit");
				return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);
			}
			isUpdated = EligibilityCriteriaHandler.getInstance().addEligibilityCriteria(eligibilityCriteriaForm, "Edit");

			setCriteriaToRequest(request, eligibilityCriteriaForm);
			setprogramMapToRequest(request, eligibilityCriteriaForm);
			setCourseMapToRequest(request, eligibilityCriteriaForm);

		} catch (Exception e) {
			log.error("error in final submit updateCriteria...", e);
			if (e instanceof DuplicateException) {
				errors.add("error", new ActionError("knowledgepro.admin.course.year.addexist"));
				saveErrors(request, errors);
				setprogramMapToRequest(request, eligibilityCriteriaForm);
				setCourseMapToRequest(request, eligibilityCriteriaForm);
				setSubjectMapToForm(eligibilityCriteriaForm.getCourseId(), eligibilityCriteriaForm);
				setCriteriaToRequest(request, eligibilityCriteriaForm);
				request.setAttribute("criteriaOperation", "edit");
				return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);
			} else if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				eligibilityCriteriaForm.setErrorMessage(msg);
				eligibilityCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				eligibilityCriteriaForm.setErrorMessage(msg);
				eligibilityCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}

		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.criteria.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			eligibilityCriteriaForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.criteria.updatefailure"));
			saveErrors(request, errors);
		}
		request.setAttribute("criteriaOperation", "add");
		return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);

	}
	
	
	
	/**
	 * setting programMap to request to populating in UI 
	 * @param request
	 * @param eligibilityCriteriaForm
	 */

	public void setprogramMapToRequest(HttpServletRequest request, EligibilityCriteriaForm eligibilityCriteriaForm) {
		if (eligibilityCriteriaForm.getProgramTypeId() != null && !eligibilityCriteriaForm.getProgramTypeId().isEmpty()) {
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(eligibilityCriteriaForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
	}

	/**
	 * setting courseMap to request to populating in UI 
	 * @param request
	 * @param eligibilityCriteriaForm
	 */
	
	public void setCourseMapToRequest(HttpServletRequest request, EligibilityCriteriaForm eligibilityCriteriaForm) {
		if (eligibilityCriteriaForm.getProgramId() != null && !eligibilityCriteriaForm.getProgramId().isEmpty()) {
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(eligibilityCriteriaForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
	}
	
	/**
	 * setting subjectMap to request to populating in UI 
	 * @param courseId
	 * @param eligibilityCriteriaForm
	 */
	public void setSubjectMapToForm(String courseId, EligibilityCriteriaForm eligibilityCriteriaForm) {
		Map<Integer, String> subjectMap = null;
		if(courseId != null && !courseId.isEmpty()){
			subjectMap = CommonAjaxHandler.getInstance().getSubjectDetailsByCourse(Integer.parseInt(courseId));
		}
		eligibilityCriteriaForm.setSubjectMap(subjectMap);
	}

	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing criteria based on the ID
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteCriteria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {

		EligibilityCriteriaForm eligibilityCriteriaForm = (EligibilityCriteriaForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (eligibilityCriteriaForm.getId() != 0) {
				int criteriaId = eligibilityCriteriaForm.getId();   //setting id for delete
				isDeleted = EligibilityCriteriaHandler.getInstance().deleteCriteria(criteriaId,false,eligibilityCriteriaForm);
			}
		} catch (Exception e) {
			log.error("error in submit of delete page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				eligibilityCriteriaForm.setErrorMessage(msg);
				eligibilityCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				eligibilityCriteriaForm.setErrorMessage(msg);
				eligibilityCriteriaForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}

		setprogramMapToRequest(request, eligibilityCriteriaForm);
		setCourseMapToRequest(request, eligibilityCriteriaForm);
		setSubjectMapToForm(eligibilityCriteriaForm.getCourseId(), eligibilityCriteriaForm);
		setCriteriaToRequest(request, eligibilityCriteriaForm);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.criteria.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			eligibilityCriteriaForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError(
					"knowledgepro.admin.criteria.deletefailure"));
			saveErrors(request, errors);
		}
		eligibilityCriteriaForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.ELIGIBILITY_CRITERIA);
	}
	/**
	 * validate method for percentage & subject
	 * @param eligibilityCriteriaForm
	 * @param errors
	 */

	private void validateSubjectAndPercentage(EligibilityCriteriaForm eligibilityCriteriaForm, ActionErrors errors){
//		if (errors == null){
//			errors = new ActionErrors();}
		
		// setting mandatory according to the condition
		if(eligibilityCriteriaForm.getSelectedSubjects() != null && eligibilityCriteriaForm.getSelectedSubjects().length > 0){
			eligibilityCriteriaForm.setPerWithoutMandatory("yes"); //setting for showing mandatory sign based on the condition
		}
		
		if(!CommonUtil.checkForEmpty(eligibilityCriteriaForm.getTotalPercentage()) )
		{
			if(eligibilityCriteriaForm.getSelectedSubjects() == null && !CommonUtil.checkForEmpty(eligibilityCriteriaForm.getPercentageWithoutLanguage())){
				errors.add("knowledgepro.admin.criteria.percent.or.subject.required",new ActionError("knowledgepro.admin.criteria.percent.or.subject.required"));
				return;
			}
				
		}
		if(!CommonUtil.checkForEmpty(eligibilityCriteriaForm.getPercentageWithoutLanguage()) ){
			if(eligibilityCriteriaForm.getSelectedSubjects() != null && eligibilityCriteriaForm.getSelectedSubjects().length > 0){
				errors.add("knowledgepro.admin.valid.criteria.per.required",new ActionError("knowledgepro.admin.valid.criteria.per.required"));
				return;
			}
		}
		
		if(!CommonUtil.checkForEmpty(eligibilityCriteriaForm.getPercentageWithoutLanguage()) && !CommonUtil.checkForEmpty(eligibilityCriteriaForm.getTotalPercentage()) ){
			if(eligibilityCriteriaForm.getSelectedSubjects() != null && eligibilityCriteriaForm.getSelectedSubjects().length > 0){
				errors.add("knowledgepro.admin.valid.criteria.per.required",new ActionError("knowledgepro.admin.valid.criteria.per.required"));
				return;
			}
		}	

		if(CommonUtil.checkForEmpty(eligibilityCriteriaForm.getPercentageWithoutLanguage()) && CommonUtil.checkForEmpty(eligibilityCriteriaForm.getTotalPercentage()) ){
			errors.add("knowledgepro.admin.valid.criteria.per.same",new ActionError("knowledgepro.admin.valid.criteria.per.same"));
			return;
		}	
		
		
		
	}
}
