	package com.kp.cms.actions.employee;
	
	import java.util.List;
	
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.DuplicateException1;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.exceptions.ReActivateException1;
	import com.kp.cms.forms.employee.InterviewRatingFactorForm;
	import com.kp.cms.handlers.employee.InterviewRatingFactorHandler;
import com.kp.cms.to.employee.InterviewRatingFactorTO;
	
	public class InterviewRatingFactorAction extends BaseDispatchAction{
		private static final Log log = LogFactory.getLog(InterviewRatingFactorAction.class);
		private static final String INTERVIEW_RATING_FACTOR = "InterviewRatingFactorOperation";
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInterviewRatingFactor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewRatingFactorForm interviewRatingForm=(InterviewRatingFactorForm) form;
		try{
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setInterviewRatingFactorListToRequest(request);
			setUserId(request, interviewRatingForm);
			initFields(interviewRatingForm);
		
		}catch (Exception e) {
			log.error("error initEducationMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewRatingForm.setErrorMessage(msg);
				interviewRatingForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
	}
	
	/**
	 * @param request
	 * @throws Exception 
	 */
	private void setInterviewRatingFactorListToRequest(HttpServletRequest request) throws Exception {
		List<InterviewRatingFactorTO> interviewRatingFactorList=InterviewRatingFactorHandler.getInstance().getInterviewRatingList();
		request.setAttribute("InterviewRatingFactorList", interviewRatingFactorList);
	}
	
	/**
	 * @param interviewRatingForm
	 */
	private void initFields(InterviewRatingFactorForm interviewRatingForm) {
		interviewRatingForm.setRatingFactor(null);
		interviewRatingForm.setTeaching(true);
		interviewRatingForm.setMaxScore(null);
		interviewRatingForm.setDisplayOrder(null);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInterviewRatingFactor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewRatingFactorForm interviewRatingForm=(InterviewRatingFactorForm) form;
		setUserId(request, interviewRatingForm);
		HttpSession session=request.getSession();
		ActionErrors errors=new ActionErrors();
		ActionMessages messages =new ActionMessages();
		boolean isAdded=false;
		boolean isDuplicated=false;
		try{
			if(errors !=null && !errors.isEmpty()){
				saveErrors(request, errors);
				setInterviewRatingFactorListToRequest(request);
				return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
			}
			if(interviewRatingForm.getRatingFactor() ==null || interviewRatingForm.getRatingFactor().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.employee.interview.rating.factor.level.required"));
				saveErrors(request, errors);
				setInterviewRatingFactorListToRequest(request);
				initFields(interviewRatingForm);
				
			}
			if(interviewRatingForm.getMaxScore() == null || interviewRatingForm.getMaxScore() ==0){
				errors.add("error", new ActionError("knowledgepro.employee.interview.max.score.required"));
				saveErrors(request, errors);
				setInterviewRatingFactorListToRequest(request);
				initFields(interviewRatingForm);
				
			}
			if(interviewRatingForm.getDisplayOrder() == null || interviewRatingForm.getDisplayOrder()==0){
				errors.add("error", new ActionError("knowledgepro.employee.interview.display.order.required"));
				saveErrors(request, errors);
				setInterviewRatingFactorListToRequest(request);
				initFields(interviewRatingForm);
				return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
			}
			
			isAdded=InterviewRatingFactorHandler.getInstance().addInterviewRatingFactor(interviewRatingForm, "Add");
			setInterviewRatingFactorListToRequest(request);
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.interview.rating.factor.name.exists"));
			saveErrors(request, errors);
			setInterviewRatingFactorListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
		} catch (ReActivateException e2) {
			errors.add("error", new ActionError(CMSConstants.INTERVIEW_RATING_FACTOR_REACTIVATE));
			saveErrors(request, errors);
			setInterviewRatingFactorListToRequest(request);
			session.setAttribute("REACTIVATEID", interviewRatingForm.getDuplId());
			session.setAttribute("NAME", interviewRatingForm.getRatingFactor());
			session.setAttribute("MAXSCORE", interviewRatingForm.getMaxScore());
			session.setAttribute("DISPLAYORDER", interviewRatingForm.getDisplayOrder());
			session.setAttribute("TEACHING", interviewRatingForm.getTeaching());
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
		}catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewRatingForm.setErrorMessage(msg);
				interviewRatingForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(isAdded){
			ActionMessage message=new ActionMessage("knowledgepro.employee.interview.rating.factor.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(interviewRatingForm);
		}else {
			errors.add("error", new ActionError("knowledgepro.employee.interview.rating.factor.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addInterviewRatingFactor Action");
		return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editInterviewRatingFactor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewRatingFactorForm interviewRatingForm=(InterviewRatingFactorForm) form;
		setUserId(request, interviewRatingForm);
		ActionMessages messages =new ActionMessages();
		try{
			if(interviewRatingForm.getId() !=0){
				int id=interviewRatingForm.getId();
				InterviewRatingFactorTO factorTO=InterviewRatingFactorHandler.getInstance().editInterviewRatingFactor(id);
				interviewRatingForm.setRatingFactor(factorTO.getRatingFactor());
				interviewRatingForm.setMaxScore(factorTO.getMaxScore());
				interviewRatingForm.setDisplayOrder(factorTO.getDisplayOrder());
				if(factorTO.getTeaching().equalsIgnoreCase("Yes")){
					interviewRatingForm.setTeaching(true);
					interviewRatingForm.setOrgTeaching(true);
				}else if(factorTO.getTeaching().equalsIgnoreCase("No")){
					interviewRatingForm.setTeaching(false);
					interviewRatingForm.setOrgTeaching(false);
				}
				interviewRatingForm.setOrgRatingFactor(factorTO.getRatingFactor());
				interviewRatingForm.setOrgMaxScore(factorTO.getMaxScore());
				interviewRatingForm.setOrgDisplayOrder(factorTO.getDisplayOrder());
			}
			setInterviewRatingFactorListToRequest(request);
		}catch (Exception e) {
			log.error("error in final submit of interview rating factor...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewRatingForm.setErrorMessage(msg);
				interviewRatingForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		request.setAttribute(INTERVIEW_RATING_FACTOR,
				CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateInterviewRatingFactor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InterviewRatingFactorForm interviewRatingFactorForm = (InterviewRatingFactorForm)form;
		setUserId(request, interviewRatingFactorForm);
		ActionErrors errors=new ActionErrors();
		ActionMessages messages =new ActionMessages();
		boolean isUpdated=false;
		try{
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setInterviewRatingFactorListToRequest(request);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
			}
			if(interviewRatingFactorForm.getRatingFactor() ==null || interviewRatingFactorForm.getRatingFactor().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.employee.interview.rating.factor.level.required"));
				saveErrors(request, errors);
				setInterviewRatingFactorListToRequest(request);
				//initFields(interviewRatingFactorForm);
				request.setAttribute(INTERVIEW_RATING_FACTOR,
						CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
			}
			if(interviewRatingFactorForm.getMaxScore() == 0){
				errors.add("error", new ActionError("knowledgepro.employee.interview.max.score.required"));
				saveErrors(request, errors);
				setInterviewRatingFactorListToRequest(request);
				//initFields(interviewRatingFactorForm);
				request.setAttribute(INTERVIEW_RATING_FACTOR,
						CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);	
			}
			if(interviewRatingFactorForm.getDisplayOrder() == 0){
				errors.add("error", new ActionError("knowledgepro.employee.interview.display.order.required"));
				saveErrors(request, errors);
				setInterviewRatingFactorListToRequest(request);
				//initFields(interviewRatingFactorForm);
				request.setAttribute(INTERVIEW_RATING_FACTOR,
						CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
			}
			isUpdated=InterviewRatingFactorHandler.getInstance().addInterviewRatingFactor(interviewRatingFactorForm, "Edit");
			setInterviewRatingFactorListToRequest(request);
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.employee.interview.rating.factor.name.exists"));
			saveErrors(request, errors);
			setInterviewRatingFactorListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			request.setAttribute(INTERVIEW_RATING_FACTOR,
					CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.INTERVIEW_RATING_FACTOR_REACTIVATE));
			saveErrors(request, errors);
			setInterviewRatingFactorListToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
		} catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewRatingFactorForm.setErrorMessage(msg);
				interviewRatingFactorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(isUpdated){
			ActionMessage message=new ActionMessage("Knowledgepro.employee.interview.rating.factor.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(interviewRatingFactorForm);
		}else{
			errors.add("error", new ActionError("Knowledgepro.employee.interview.rating.factor.updatefailure"));
			saveErrors(request, errors);
		}
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("Leaving updateInterviewRatingFactor Action");
		return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
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
	public ActionForward deleteInterviewRatingFactor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewRatingFactorForm ratingFactorForm = (InterviewRatingFactorForm) form;
		setUserId(request, ratingFactorForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isDeleted = false;
		try {
			if (ratingFactorForm.getId() != 0) {
				int id = ratingFactorForm.getId();
				isDeleted = InterviewRatingFactorHandler.getInstance()
						.deleteInterviewRatingFactor(id, false,
								ratingFactorForm);
			}
			setInterviewRatingFactorListToRequest(request);
		} catch (Exception e) {
			log.error("error in deleteEducation...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				ratingFactorForm.setErrorMessage(msg);
				ratingFactorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isDeleted) {
			ActionMessage message = new ActionMessage(
					"Knowledgepro.employee.interview.rating.factor.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(ratingFactorForm);
		} else {
			errors.add("error",new ActionError("Knowledgepro.employee.interview.rating.factor.deletefailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateInterviewRatingFactor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 InterviewRatingFactorForm ratingFactorForm=(InterviewRatingFactorForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		boolean isActivated = false;
		try{
			int id=(Integer) session.getAttribute("REACTIVATEID");
			isActivated=InterviewRatingFactorHandler.getInstance().deleteInterviewRatingFactor(id, true, ratingFactorForm);
		}catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.INTERVIEW_RATING_FACTOR_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		 setInterviewRatingFactorListToRequest(request);
		 if (isActivated) {
				ActionMessage message = new ActionMessage(CMSConstants.INTERVIEW_RATING_FACTOR_ACTIVATE);
				messages.add("messages", message);
				saveMessages(request, messages);
				initFields(ratingFactorForm);
			}
		return mapping.findForward(CMSConstants.INTERVIEW_RATING_FACTOR);
	}
	}
