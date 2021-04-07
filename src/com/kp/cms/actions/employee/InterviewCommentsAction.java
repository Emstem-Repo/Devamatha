package com.kp.cms.actions.employee;

import java.util.List;

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
import com.kp.cms.forms.employee.InterviewCommentsForm;
import com.kp.cms.handlers.employee.InterviewCommentsHandler;
import com.kp.cms.to.employee.InterviewCommentsTO;

public class InterviewCommentsAction extends BaseDispatchAction {
	private static final Log log = LogFactory
			.getLog(InterviewCommentsAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInterviewComments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initInterviewComments in InterviewCommentsAction");
		InterviewCommentsForm objform = (InterviewCommentsForm) form;
		objform.resetFields();
		log.info("Exit from the initInterviewComments in InterviewCommentsAction");
		return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the getDetails in InterviewCommentsAction");
		InterviewCommentsForm objform = (InterviewCommentsForm) form;
		ActionErrors errors=objform.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<InterviewCommentsTO> listOfDetails=InterviewCommentsHandler.getInstance().getDetails(objform.getName());
				if (listOfDetails.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS);
				} 
				objform.setListOfDetails(listOfDetails);
				return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS_DETAILS);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}else{
			saveErrors(request, errors);
		}
		log.info("Exit from the getDetails in InterviewCommentsAction");
		return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into the getDetails in InterviewCommentsAction");
		InterviewCommentsForm objform = (InterviewCommentsForm) form;
		try {
			objform = InterviewCommentsHandler.getInstance().getComments(objform);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Exit from the getDetails in InterviewCommentsAction");
		return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS_DETAILS_ENTRY);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInterviedetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the getDetails in InterviewCommentsAction");
		InterviewCommentsForm objform = (InterviewCommentsForm) form;
		try {
			 objform =InterviewCommentsHandler.getInstance().getResumeDetails(objform);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Exit from the getDetails in InterviewCommentsAction");
		return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS_DISPLAY);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveInterviewComments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the getDetails in InterviewCommentsAction");
		InterviewCommentsForm objform = (InterviewCommentsForm) form;
		ActionMessages errorsmsages = objform.validate(mapping, request);
		if (errorsmsages.isEmpty()) {
			try {
				setUserId(request, objform);
				boolean flag = InterviewCommentsHandler.getInstance().saveInterviewComments(objform);
				if(flag){
					objform.clear();
					List<InterviewCommentsTO> listOfDetails=InterviewCommentsHandler.getInstance().getDetails(objform.getName());
					ActionMessages messages = new ActionMessages();
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess", "Interview Comments");
					messages.add("messages", message);
					objform.setListOfDetails(listOfDetails);
					return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS_DETAILS);
				}
				
			} catch (Exception e) {

				e.printStackTrace();

			}
		}
		log.info("Exit from the getDetails in InterviewCommentsAction");
		return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS_DETAILS);

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStatus (ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InterviewCommentsForm objform = (InterviewCommentsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			boolean result=InterviewCommentsHandler.getInstance().updateStaus(objform);
			if (result) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.admin.interviewComments.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.resetFields();
			} else {
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.interviewComments.updatefailure"));
				saveErrors(request, errors);
			}
			}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("error while updating status in InterviewCommentsAction");
		}
		
		return mapping.findForward(CMSConstants.INTERVIEW_COMMENTS);
	}

}
