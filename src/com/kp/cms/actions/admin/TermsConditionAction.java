package com.kp.cms.actions.admin;

import java.util.ArrayList;
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
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.TermsConditionForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.DocumentExamEntryHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.TermsConditionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.admin.TermsConditionTO;

/**
 * 
 * @author
 *        
 */

@SuppressWarnings("deprecation")
public class TermsConditionAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(TermsConditionAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will get all the details from  termsandcondition table 
	 * @return
	 * @throws Exception
	 */

	public ActionForward initTermsCondition(ActionMapping mapping, ActionForm form, 
										HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside initTermsCondition");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		initFields(termsConditionForm);

		try {
			final String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setTermsAndConditionsToRequest(request);  //setting terms&condition list to request for UI display
			setUserId(request, termsConditionForm);  //setting userID for updating last changed details
		} catch (Exception e) {
			log.error("error submit Terms And Conditions page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("leaving initTermsCondition");
		return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);

	}
	/**
	 *setting programtype list to request for selecting program type from combo 
	 * @param request
	 * @throws Exception
	 */
	
	public void setProgramtypelist(HttpServletRequest request) throws Exception {
		log.debug("inside setProgramtypelist");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		if (programTypeList != null) {
			request.setAttribute("programTypeList", programTypeList);

		} else {
			log.error("No records found :: List is empty");
		}
		log.debug("leaving setProgramtypelist");
	}

	/**
	 * setting terms&condition list to request for UI display 
	 * @param request
	 * @throws Exception
	 */
	public void setTermsAndConditionsToRequest(HttpServletRequest request) throws Exception {
		List<TermsConditionTO> termsConditionList = TermsConditionHandler.getInstance().getTermsConditionWithId(0);
		request.setAttribute("termsConditionList", termsConditionList);
	}

	/**
	 * method used for adding terms&condition data to table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward addTermsCondition(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside addTermsCondition Action");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = termsConditionForm.validate(mapping, request);
		boolean isAdded = false;
		try {

			if (!errors.isEmpty()) {
				setProgramtypelist(request);
				setTermsAndConditionsToRequest(request);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
			}
			if (termsConditionForm.getDescription() != null && termsConditionForm.getDescription().length() > 2000) {
				errors.add("error", new ActionError("knowledgepro.admin.tc.descriptionfail"));
				setProgramtypelist(request);
				setTermsAndConditionsToRequest(request);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
			}

			isAdded = TermsConditionHandler.getInstance().addTermsCondition(termsConditionForm, "Add");

			setProgramtypelist(request);
			setTermsAndConditionsToRequest(request);

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.addexist", termsConditionForm.getCourseName(), 
												termsConditionForm.getYears()));
			saveErrors(request, errors);
			setProgramtypelist(request);
			setTermsAndConditionsToRequest(request);
			return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.TERMS_CONDITIONS_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setProgramtypelist(request);
			setTermsAndConditionsToRequest(request);
			return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
		} catch (Exception e) {
			log.error("error in update terms and conditions page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.termsandconditions.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(termsConditionForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addTermsCondition Action");
		return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method update termscondition table based on the courseId.
	 * @throws Exception
	 */

	public ActionForward updateTermsCondition(ActionMapping mapping, ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		log.debug("inside updateTermsCondition Action");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = termsConditionForm.validate(mapping, request);
		boolean isAdded = false;

		try {

			if (isCancelled(request)) {
				setRequiredDataToForm(termsConditionForm, request);
				setProgramtypelist(request);
				setprogramMapToRequest(request, termsConditionForm);
				setTermsAndConditionsToRequest(request);
				setpCourseMapToRequest(request, termsConditionForm);
				request.setAttribute("conditionsOperation", "edit");
				return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
			}

			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramtypelist(request);
				setprogramMapToRequest(request, termsConditionForm);
				setTermsAndConditionsToRequest(request);
				setpCourseMapToRequest(request, termsConditionForm);
				request.setAttribute("conditionsOperation", "edit");
				return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
			}
			if (termsConditionForm.getDescription() != null && termsConditionForm.getDescription().length() > 2000) {
				errors.add("error", new ActionError(
						"knowledgepro.admin.tc.descriptionfail"));
				setProgramtypelist(request);
				setprogramMapToRequest(request, termsConditionForm);
				setTermsAndConditionsToRequest(request);
				setpCourseMapToRequest(request, termsConditionForm);
				request.setAttribute("conditionsOperation", "edit");
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
			}

			isAdded = TermsConditionHandler.getInstance().addTermsCondition(termsConditionForm, "Edit");

			setProgramtypelist(request);
			setTermsAndConditionsToRequest(request);
			setprogramMapToRequest(request, termsConditionForm);
			setpCourseMapToRequest(request, termsConditionForm);

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.addexist", termsConditionForm.getCourseName(), 
													termsConditionForm.getYears()));
			saveErrors(request, errors);
			setProgramtypelist(request);
			setTermsAndConditionsToRequest(request);
			setprogramMapToRequest(request, termsConditionForm);
			setpCourseMapToRequest(request, termsConditionForm);
			request.setAttribute("conditionsOperation", "edit");
			return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.TERMS_CONDITIONS_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setProgramtypelist(request);
			setTermsAndConditionsToRequest(request);
			setprogramMapToRequest(request, termsConditionForm);
			setpCourseMapToRequest(request, termsConditionForm);
			request.setAttribute("conditionsOperation", "edit");
			return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
		} catch (Exception e) {
			log.error("error in update terms and conditions page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.termsandconditions.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(termsConditionForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.updatefailure"));
			saveErrors(request, errors);
		}
		request.setAttribute("conditionsOperation", "add");
		log.debug("Leaving updateTermsCondition Action");
		return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing Terms And Conditions
	 * @return ActionForward This action method will called when particular
	 *         Terms And Conditions according to Course need to be deleted based
	 *         on the Terms & Conditions id.
	 * @throws Exception
	 */
	public ActionForward deleteTermsCondition(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside delete Terms Condition Action");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (termsConditionForm.getId() != 0) {
				int conditionId = termsConditionForm.getId();
				isDeleted = TermsConditionHandler.getInstance().deleteTermsCondition(conditionId, false, termsConditionForm.getUserId());
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
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setProgramtypelist(request);
		setTermsAndConditionsToRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.termsandconditions.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(termsConditionForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("inside delete Terms Condition Action");
		return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will edit Terms And Conditions
	 * @return to mapping
	 * @throws Exception
	 */
	public ActionForward editTermsAndConditions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside editTermsAndConditions");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		setRequiredDataToForm(termsConditionForm, request);
		request.setAttribute("conditionsOperation", "edit");
		return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
	}

	public void setRequiredDataToForm(TermsConditionForm termsConditionForm, HttpServletRequest request) throws Exception {
		log.debug("inside setRequiredDataToForm");
		int conditionId = Integer.parseInt(request.getParameter("id"));
		List<TermsConditionTO> termsAndConditionsList = TermsConditionHandler.getInstance().getTermsConditionWithId(conditionId);
		Iterator<TermsConditionTO> conditionIt = termsAndConditionsList.iterator();

		setProgramtypelist(request);
		setTermsAndConditionsToRequest(request);

		while (conditionIt.hasNext()) {
			TermsConditionTO termsConditionTO = (TermsConditionTO) conditionIt.next();
			int progTypeId = termsConditionTO.getCourseTo().getProgramTo().getProgramTypeTo().getProgramTypeId();
			termsConditionForm.setProgramTypeId(Integer.toString(progTypeId));
			termsConditionForm.setProgramId(Integer.toString(termsConditionTO.getCourseTo().getProgramTo().getId()));
			termsConditionForm.setCourseId(Integer.toString(termsConditionTO.getCourseTo().getId()));
			// setting to check the duplication
			termsConditionForm.setOrigCourseId(termsConditionTO.getCourseTo().getId());
			termsConditionForm.setOrigYear(termsConditionTO.getYear());
			termsConditionForm.setYears(Integer.toString(termsConditionTO.getYear()));
			termsConditionForm.setDescription(termsConditionTO.getDescription());
		}

		Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(termsConditionForm.getProgramTypeId()));
		Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(termsConditionForm.getProgramId()));
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseMap", courseMap);
		log.debug("leaving setRequiredDataToForm");
		request.setAttribute("conditionsOperation", "edit");
	}

	/**
	 * 
	 * @return This method sets propgram map to request for setting in edit
	 *         option
	 * @throws Exception
	 */

	public void setprogramMapToRequest(HttpServletRequest request, TermsConditionForm termsConditionForm) {
		log.debug("inside setprogramMapToRequest");
		if (termsConditionForm.getProgramTypeId() != null && (!termsConditionForm.getProgramTypeId().isEmpty())) {
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(termsConditionForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
		log.debug("leaving setprogramMapToRequest");
	}

	/**
	 * 
	 * @param request
	 * @param termsConditionForm
	 */
	
	public void setpCourseMapToRequest(HttpServletRequest request, TermsConditionForm termsConditionForm) {
		log.debug("inside setpCourseMapToRequest");
		if (termsConditionForm.getProgramId() != null && (!termsConditionForm.getProgramId().isEmpty())) {
			 Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(termsConditionForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
		log.debug("leaving setpCourseMapToRequest");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This action method Reactivate terms & conditions.
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward activateTermsAndConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {

		log.debug("Entering activateTermsAndConditions");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (termsConditionForm.getDuplId() != 0) {
				int duplId = termsConditionForm.getDuplId();
				isActivated = TermsConditionHandler.getInstance().deleteTermsCondition(duplId, true, termsConditionForm.getUserId());
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.TERMS_CONDITIONS_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setProgramtypelist(request);
		setTermsAndConditionsToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.TERMS_CONDITIONS_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateTermsAndConditions");
		return mapping.findForward(CMSConstants.TERMS_AND_CONDITIONS_ENTRY);
	}

	/***
	 * this method used to view the terms&conditions in a seperate link
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTermsAndConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request,
		    									HttpServletResponse response) throws Exception{
		log.debug("inside viewTermsAndConditions");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		int viewId = termsConditionForm.getId();
		List<TermsConditionTO> termsAndConditionsList = TermsConditionHandler.getInstance().getTermsConditionWithId(viewId);
		Iterator<TermsConditionTO> conditionItr = termsAndConditionsList.iterator();

		while (conditionItr.hasNext()) {
			TermsConditionTO termsConditionTO = (TermsConditionTO) conditionItr.next();
			termsConditionForm.setViewDesc(termsConditionTO.getDescription());
		}
		log.debug("leaving viewTermsAndConditions");
		return mapping.findForward(CMSConstants.VIEW_TERMS_CONDITIONS);
	}	
	
	/**
	 * forward to detailed t & c page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detailedTC(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside initTermsCondition");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		initFields(termsConditionForm);

		try {
			final String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setTermsAndConditionsCheckListToRequest(request);  //setting terms&condition list to request for UI display
			setUserId(request, termsConditionForm);  //setting userID for updating last changed details
			termsConditionForm.setTermsConditionList(null);
		} catch (Exception e) {
			log.error("error in detailedTC...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("leaving detailedTC");
		return mapping.findForward(CMSConstants.DETAILED_TC);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return submit detailed t & c page
	 * @throws Exception
	 */
	public ActionForward submitDetailedTC(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside submitDetailedTC");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		 ActionErrors errors = termsConditionForm.validate(mapping, request);

		try {
			if (!errors.isEmpty()) {
				setProgramtypelist(request);
				setTermsAndConditionsCheckListToRequest(request);  //setting terms&condition list to request for UI display
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.DETAILED_TC);
			}
			
			if(termsConditionForm.getNoOfDesc() == null || termsConditionForm.getNoOfDesc().trim().isEmpty() || termsConditionForm.getNoOfDesc().equalsIgnoreCase("0") || Integer.parseInt(termsConditionForm.getNoOfDesc()) <= 0){
				errors.add("error", new ActionError("knowledgepro.admin.tc.noofdesc.required"));
			}
			if (!errors.isEmpty()) {
				setProgramtypelist(request);
				setTermsAndConditionsCheckListToRequest(request);  //setting terms&condition list to request for UI display
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.DETAILED_TC);
			}
			
			Integer noOfDesc = 0;
			
			if(termsConditionForm.getNoOfDesc()!= null && !termsConditionForm.getNoOfDesc().trim().isEmpty()){
				noOfDesc = Integer.parseInt(termsConditionForm.getNoOfDesc());
			}
			List<TermsConditionChecklistTO> tcList = new ArrayList<TermsConditionChecklistTO>(); 
			for(int j = 1; j <= noOfDesc; j++){
				TermsConditionChecklistTO tcListTO = new TermsConditionChecklistTO(); 
				tcListTO.setChecklistDescription("");
				tcListTO.setMandatory(false);
				tcList.add(tcListTO);
			}
			termsConditionForm.setTermsConditionList(tcList);
		} catch (Exception e) {
			log.error("error in detailedTC...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("leaving detailedTC");
		return mapping.findForward(CMSConstants.DETAILED_TC_DESC);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return save detailed t & c
	 * @throws Exception
	 */
	public ActionForward saveDetailedTC(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside saveDetailedTC");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		Iterator<TermsConditionChecklistTO> tcItr = termsConditionForm.getTermsConditionList().iterator();
		Boolean isAdded;
		try {
			if(termsConditionForm.getNoOfDesc()!= null && !termsConditionForm.getNoOfDesc().trim().isEmpty() && Integer.parseInt(termsConditionForm.getNoOfDesc()) <= 0){
				errors.add("error", new ActionError("knowledgepro.admin.tc.noofdesc.with.col"));
			}
			while(tcItr.hasNext()){
				TermsConditionChecklistTO tcCheckListTO = tcItr.next();
				if(tcCheckListTO.getChecklistDescription() == null || tcCheckListTO.getChecklistDescription().trim().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.admin.tc.dec.required"));
				}
				break;
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.DETAILED_TC_DESC);
			}
			
			isAdded = TermsConditionHandler.getInstance().saveTermsConditionDeatils(termsConditionForm);
			setProgramtypelist(request);
			setTermsAndConditionsCheckListToRequest(request);  //setting terms&condition list to request for UI display
			
		} catch (Exception e) {
			log.error("error in detailedTC...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.termsandconditions.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(termsConditionForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.addfailure"));
			saveErrors(request, errors);
		}
		
		log.debug("leaving detailedTC");
		return mapping.findForward(CMSConstants.DETAILED_TC);	
	}	
	/**
	 * initialize 
	 * @param tcForm
	 */
	public void initFields(TermsConditionForm tcForm) {
	
		tcForm.setCourseId(null);
		tcForm.setProgramId(null);
		tcForm.setProgramTypeId(null);
		tcForm.setDescription(null);
		//tcForm.setYears(null);
		tcForm.setNoOfDesc(null);
		tcForm.setTermsConditionList(null);
	}
	/**
	 * setting termsConditionCheckList list to request for UI display 
	 * @param request
	 * @throws Exception
	 */
	public void setTermsAndConditionsCheckListToRequest(HttpServletRequest request) throws Exception {
		List<TermsConditionChecklistTO> termsConditionCheckList = TermsConditionHandler.getInstance().getTermsConditionCheckList();
		request.setAttribute("termsConditionCheckList", termsConditionCheckList);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return view t & c
	 * @throws Exception
	 */
	public ActionForward viewTermsAndConditionsCheckList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.debug("inside viewTermsAndConditions");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		int viewId = 0;
		if(termsConditionForm.getCourseId()!= null && !termsConditionForm.getCourseId().trim().isEmpty()){
			viewId = Integer.parseInt(termsConditionForm.getCourseId());
		}
		/*Integer year =0;
		if(termsConditionForm.getYears()!= null && !termsConditionForm.getYears().trim().isEmpty()){
			year = Integer.parseInt(termsConditionForm.getYears());
		}*/
		List<TermsConditionChecklistTO> termsAndConditionsCheckList = TermsConditionHandler.getInstance().getTermsConditionCheckListWithCourseId(viewId/*, year*/);
		request.setAttribute("viewTermsConditionCheckList", termsAndConditionsCheckList);
		log.debug("leaving viewTermsAndConditionsCheckList");
		return mapping.findForward(CMSConstants.VIEW_TERMS_CONDITIONS_CHECK_LIST);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTermsAndCondtionCheckList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("inside deleteTermsAndCondtionCheckList Action");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			int courseId = 0;
			//int year = 0;
			if(termsConditionForm.getCourseId()!= null && !termsConditionForm.getCourseId().trim().isEmpty()){
				courseId = Integer.parseInt(termsConditionForm.getCourseId());
			}
			/*if(termsConditionForm.getYears()!= null && !termsConditionForm.getYears().trim().isEmpty()){
				year = Integer.parseInt(termsConditionForm.getYears());
			}*/
			isDeleted = TermsConditionHandler.getInstance().deleteTermsConditionCheckList(courseId/*, year*/);
		} catch (Exception e) {
			log.error("error in submit of delete page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setProgramtypelist(request);
		setTermsAndConditionsCheckListToRequest(request); 
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.termsandconditions.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(termsConditionForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("exit deleteTermsAndCondtionCheckList :Action");
		return mapping.findForward(CMSConstants.DETAILED_TC);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return view t & c
	 * @throws Exception
	 */
	public ActionForward editTermsAndConditionsCheckList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.debug("inside editTermsAndConditions");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		int viewId = 0;
		if(termsConditionForm.getCourseId()!= null && !termsConditionForm.getCourseId().trim().isEmpty()){
			viewId = Integer.parseInt(termsConditionForm.getCourseId());
		}
		List<TermsConditionChecklistTO> termsAndConditionsCheckList = TermsConditionHandler.getInstance().getTermsConditionCheckListWithCourseId(viewId/*, year*/);
		termsConditionForm.setTermsConditionList(termsAndConditionsCheckList);
		log.debug("leaving editTermsAndConditionsCheckList");
		return mapping.findForward(CMSConstants.EDIT_TERMS_CONDITIONS_CHECK_LIST);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return view t & c
	 * @throws Exception
	 */
	public ActionForward deleteTermsAndConditionsCheckList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.debug("inside editTermsAndConditions");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if(termsConditionForm.getId()>0){
				isDeleted=TermsConditionHandler.getInstance().deleteTermsConditionCheckListById(termsConditionForm.getId());
			}
			
			if (isDeleted) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.termsandconditions.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.deletefailure"));
				saveErrors(request, errors);
			}
			int viewId = 0;
			if(termsConditionForm.getCourseId()!= null && !termsConditionForm.getCourseId().trim().isEmpty()){
				viewId = Integer.parseInt(termsConditionForm.getCourseId());
			}
			List<TermsConditionChecklistTO> termsAndConditionsCheckList = TermsConditionHandler.getInstance().getTermsConditionCheckListWithCourseId(viewId/*, year*/);
			termsConditionForm.setTermsConditionList(termsAndConditionsCheckList);
		} catch (Exception e) {
			log.error("error in submit of delete page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("leaving editTermsAndConditionsCheckList");
		return mapping.findForward(CMSConstants.EDIT_TERMS_CONDITIONS_CHECK_LIST);
	}
	
	public ActionForward updateTermsAndConditionsCheckList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.debug("inside editTermsAndConditions");
		TermsConditionForm termsConditionForm = (TermsConditionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isUpdated = false;
		setUserId(request, termsConditionForm);
		try {
			//This condition works when reset button will click in update mode
			if(isCancelled(request)){
				int viewId = 0;
				if(termsConditionForm.getCourseId()!= null && !termsConditionForm.getCourseId().trim().isEmpty()){
					viewId = Integer.parseInt(termsConditionForm.getCourseId());
				}
				List<TermsConditionChecklistTO> termsAndConditionsCheckList = TermsConditionHandler.getInstance().getTermsConditionCheckListWithCourseId(viewId/*, year*/);
				termsConditionForm.setTermsConditionList(termsAndConditionsCheckList);
				return mapping.findForward(CMSConstants.EDIT_TERMS_CONDITIONS_CHECK_LIST);
			}
			Iterator<TermsConditionChecklistTO> tcItr = termsConditionForm.getTermsConditionList().iterator();
			while(tcItr.hasNext()){
				TermsConditionChecklistTO tcCheckListTO = tcItr.next();
				if(tcCheckListTO.getChecklistDescription() == null || tcCheckListTO.getChecklistDescription().trim().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.admin.tc.dec.required"));
				}
				break;
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EDIT_TERMS_CONDITIONS_CHECK_LIST);
			}
			
			isUpdated=TermsConditionHandler.getInstance().updateTermsConditionList(termsConditionForm.getTermsConditionList(),termsConditionForm.getUserId());
			
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.termsandconditions.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.termsandconditions.updatefailure"));
				saveErrors(request, errors);
			}
			setProgramtypelist(request);  //setting programtype list to request for selecting program type from combo
			setTermsAndConditionsCheckListToRequest(request);  //setting terms&condition list to request for UI display
			setUserId(request, termsConditionForm);  //setting userID for updating last changed details
			termsConditionForm.setTermsConditionList(null);
		} catch (Exception e) {
			log.error("error in submit of delete page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				termsConditionForm.setErrorMessage(msg);
				termsConditionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("leaving editTermsAndConditionsCheckList");
		return mapping.findForward(CMSConstants.DETAILED_TC);
	}
}
