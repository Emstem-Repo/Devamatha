	package com.kp.cms.actions.employee;
	
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.NewInterviewCommentsForm;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.handlers.employee.NewInterviewCommentsHandler;
import com.kp.cms.handlers.employee.TelephoneDirectoryHandler;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.employee.EmpEducationalDetailsTO;
import com.kp.cms.to.employee.EmpOnlineResumeTO;
import com.kp.cms.to.employee.InterviewRatingFactorTO;
import com.kp.cms.to.employee.NewInterviewCommentsDetailsTo;
import com.kp.cms.to.employee.NewInterviewCommentsTO;
	
	public class NewInterviewCommentsAction extends BaseDispatchAction{
		private static final Log log = LogFactory
		.getLog(NewInterviewCommentsAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInterviewComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the initInterviewComments in NewInterviewCommentsAction");
		NewInterviewCommentsForm interviewCommentsForm = (NewInterviewCommentsForm) form;
		try{
			setUserId(request, interviewCommentsForm);
			initFields(interviewCommentsForm);
		}catch (Exception e) {
			log.error("error initInterviewComments...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewCommentsForm.setErrorMessage(msg);
				interviewCommentsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("Leaving into the initInterviewComments in NewInterviewCommentsAction");
		return mapping.findForward(CMSConstants.INIT_EMP_INTERVIEW_COMMENTS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the searchDetails in NewInterviewCommentsAction");
		NewInterviewCommentsForm interviewCommentsForm =(NewInterviewCommentsForm)form;
		setUserId(request, interviewCommentsForm);
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		try{
		if(interviewCommentsForm.getName()!= null && !interviewCommentsForm.getName().isEmpty()){
			List<NewInterviewCommentsTO> commentsTO=NewInterviewCommentsHandler.getInstance().getSearchList1(interviewCommentsForm);
			if(commentsTO == null || commentsTO.isEmpty()){
				
				//failed
				errors.add("error", new ActionError("knowledgepro.employee.interview.comments.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EMP_INTERVIEW_COMMENTS);
			}
			interviewCommentsForm.setNewInterviewCommentsList(commentsTO);
		}else if(interviewCommentsForm.getApplicationNo()!= null && !interviewCommentsForm.getApplicationNo().isEmpty()){
			List<NewInterviewCommentsTO> list=NewInterviewCommentsHandler.getInstance().getSearchList1(interviewCommentsForm);
			if(list == null || list.isEmpty()){
				//failed
				errors.add("error", new ActionError("knowledgepro.employee.interview.comments.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EMP_INTERVIEW_COMMENTS);
			}
			interviewCommentsForm.setNewInterviewCommentsList(list);
		}else{
			List<NewInterviewCommentsTO> commentsList=NewInterviewCommentsHandler.getInstance().getSearchList1(interviewCommentsForm);
			if(commentsList == null || commentsList.isEmpty()){
				//failed
				errors.add("error", new ActionError("knowledgepro.employee.interview.comments.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EMP_INTERVIEW_COMMENTS);
			}
			interviewCommentsForm.setNewInterviewCommentsList(commentsList);
		}
			
			
		}catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewCommentsForm.setErrorMessage(msg);
				interviewCommentsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_EMP_INTERVIEW_COMMENTS_SEARCH);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInterviewComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewInterviewCommentsForm interviewCommentsForm =(NewInterviewCommentsForm)form;
		setUserId(request, interviewCommentsForm);
		ActionErrors errors=new ActionErrors();
		ActionMessages messages= new ActionMessages();
		boolean isAdded=false;
		try{
			/*if(interviewCommentsForm.getNoOfExternalInterviewers() ==0){
				errors.add("error", new ActionError("knowledgepro.employee.interview.comments.noofexternalinterviewers"));
				saveErrors(request, errors);
			}
			if(interviewCommentsForm.getNoOfInternalInterviewers() ==0){
				errors.add("error", new ActionError("knowledgepro.employee.interview.comments.noofinternalinterviewers"));
				saveErrors(request, errors);
			}*/
			isAdded=NewInterviewCommentsHandler.getInstance().addInterviewComments(interviewCommentsForm);
			interviewCommentsForm =NewInterviewCommentsHandler.getInstance().getInterviewCommentDetails(interviewCommentsForm);
			List<InterviewRatingFactorTO> ratingFactorTOs = NewInterviewCommentsHandler.getInstance().getRatingFactorDetails(interviewCommentsForm);
			interviewCommentsForm.setInterviewRatingFactorTOs(ratingFactorTOs);
			//initFields(interviewCommentsForm);
			List<NewInterviewCommentsTO> list = NewInterviewCommentsHandler.getInstance().getEmpDetails(interviewCommentsForm);
			interviewCommentsForm.setNewInterviewCommentsList(list);
		} catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewCommentsForm.setErrorMessage(msg);
				interviewCommentsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
			if(isAdded){
				//success
				ActionMessage message=new ActionMessage("knowledgepro.employee.interview.comments.addsuccess");
				messages.add("messages",message);
				saveMessages(request, messages);
			}else {
				//failed
				errors.add("error", new ActionError("knowledgepro.employee.interview.comments.addfailure"));
				saveErrors(request, errors);
			}
			log.debug("Leaving addInterviewComments Action");
		return mapping.findForward(CMSConstants.INIT_EMP_INTERVIEW_COMMENTS_ADD);
	}
	/**
	 * @param interviewCommentsForm
	 */
	private void initFields(NewInterviewCommentsForm interviewCommentsForm) {
		interviewCommentsForm.setComments(null);
		interviewCommentsForm.setNameOfExternalInterviewer1(null);
		interviewCommentsForm.setNameOfExternalInterviewer2(null);
		interviewCommentsForm.setNameOfExternalInterviewer3(null);
		interviewCommentsForm.setNameOfInternalInterviewer1(null);
		interviewCommentsForm.setNameOfInternalInterviewer2(null);
		interviewCommentsForm.setNameOfInternalInterviewer3(null);
		interviewCommentsForm.setNoOfExternalInterviewers(null);
		interviewCommentsForm.setNoOfInternalInterviewers(null);
		interviewCommentsForm.setNameOfExternalInterviewer4(null);
		interviewCommentsForm.setNameOfExternalInterviewer5(null);
		interviewCommentsForm.setNameOfExternalInterviewer6(null);
		interviewCommentsForm.setNameOfInternalInterviewer4(null);
		interviewCommentsForm.setNameOfInternalInterviewer5(null);
		interviewCommentsForm.setNameOfInternalInterviewer6(null);
		interviewCommentsForm.setJoiningTime(null);
		interviewCommentsForm.setDepartment(null);
		interviewCommentsForm.setDepartmentId(null);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewInterviewComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewInterviewCommentsForm interviewCommentsForm=(NewInterviewCommentsForm)form;
		setUserId(request, interviewCommentsForm);
		ActionMessages messages=new ActionMessages();
		try{
			List<EmpOnlineResumeTO> empOnlineResumeToList = NewInterviewCommentsHandler.getInstance().getViewEmpInfoDetails(interviewCommentsForm);
			interviewCommentsForm.setEmpOnlineResumeList(empOnlineResumeToList);
			List<EmpEducationalDetailsTO> list=NewInterviewCommentsHandler.getInstance().getEmpEducationalDetails(interviewCommentsForm);
			interviewCommentsForm.setEducationalDetailsList(list);
			List<EmpAcheivementTO> acheivementTOs=NewInterviewCommentsHandler.getInstance().getEmpAcheivementDetails(interviewCommentsForm);
			interviewCommentsForm.setAcheivementSet(acheivementTOs);
		}catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewCommentsForm.setErrorMessage(msg);
				interviewCommentsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_EMP_INTERVIEW_COMMENTS_VIEW);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editInterviewCommentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewInterviewCommentsForm interviewCommentsForm =(NewInterviewCommentsForm)form;
		setUserId(request, interviewCommentsForm);
		initFields(interviewCommentsForm);
		ActionMessages messages=new ActionMessages();
		HttpSession session = request.getSession();
		try{
			interviewCommentsForm.setInterviewCommentId(0);
			interviewCommentsForm =NewInterviewCommentsHandler.getInstance().getInterviewCommentDetails(interviewCommentsForm);
			Map<Integer,Integer> map= NewInterviewCommentsHandler.getInstance().getNoOfInterviewers(interviewCommentsForm);
			interviewCommentsForm.setMap(map);
			List<NewInterviewCommentsTO> list = NewInterviewCommentsHandler.getInstance().getEmpDetails(interviewCommentsForm);
			interviewCommentsForm.setNewInterviewCommentsList(list);
			List<InterviewRatingFactorTO> ratingFactorTOs = NewInterviewCommentsHandler.getInstance().getRatingFactorDetails(interviewCommentsForm);
			interviewCommentsForm.setInterviewRatingFactorTOs(ratingFactorTOs);
			session.setAttribute("Internal", interviewCommentsForm.getNoOfInternalInterviewers());
			session.setAttribute("External", interviewCommentsForm.getNoOfExternalInterviewers());
			List<DepartmentEntryTO> newList = TelephoneDirectoryHandler.getInstance().getDepartmentList();
			interviewCommentsForm.setDepartmentMap(newList);
		}catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewCommentsForm.setErrorMessage(msg);
				interviewCommentsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("Leaving editInterviewCommentDetails Action");
		return mapping.findForward(CMSConstants.INIT_EMP_INTERVIEW_COMMENTS_ADD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printInterviewComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewInterviewCommentsForm interviewCommentsForm =(NewInterviewCommentsForm)form;
		ActionMessages messages=new ActionMessages();
		HttpSession session = request.getSession();
		try{
			interviewCommentsForm = NewInterviewCommentsHandler.getInstance().getLogo(interviewCommentsForm,request);
			List<NewInterviewCommentsTO> commentsTOs=NewInterviewCommentsHandler.getInstance().getPrintDetailsList(interviewCommentsForm,request);
			interviewCommentsForm.setNewInterviewCommentsList(commentsTOs);
			List<EmpEducationalDetailsTO> empDetailsTo=NewInterviewCommentsHandler.getInstance().getPrintEmpEduDetails(interviewCommentsForm);
			interviewCommentsForm.setEducationalDetailsList(empDetailsTo);
			List<NewInterviewCommentsDetailsTo> detailsTos = NewInterviewCommentsHandler.getInstance().getPrintCommentsDetails(interviewCommentsForm);
			interviewCommentsForm.setNewInterviewCommentsDetailsList(detailsTos);
			List<EmpOnlineResumeTO> empOnlineResumeTOs=NewInterviewCommentsHandler.getInstance().getEmpInfo(interviewCommentsForm);
			interviewCommentsForm.setEmpOnlineResumeList(empOnlineResumeTOs);
			NewInterviewCommentsHandler.getInstance().AgeCalculation(interviewCommentsForm,empOnlineResumeTOs);
			if(interviewCommentsForm.getNoOfExternalInterviewers()==null){
				interviewCommentsForm.setNoOfExternalInterviewers(0);
				session.setAttribute("External", interviewCommentsForm.getNoOfExternalInterviewers());
			}else{
				session.setAttribute("External", interviewCommentsForm.getNoOfExternalInterviewers());
			}
			if(interviewCommentsForm.getNoOfInternalInterviewers()==null){
				interviewCommentsForm.setNoOfInternalInterviewers(0);
				session.setAttribute("Internal", interviewCommentsForm.getNoOfInternalInterviewers());
			}
			else{
				session.setAttribute("Internal", interviewCommentsForm.getNoOfInternalInterviewers());
			}
		}catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewCommentsForm.setErrorMessage(msg);
				interviewCommentsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.SHOW_INTERVIEW_COMMENTS);
	}
}
