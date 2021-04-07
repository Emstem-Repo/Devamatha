package com.kp.cms.actions.exam;

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
import com.kp.cms.actions.reports.ScoreSheetAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.UpdateExamHallTicketForm;
import com.kp.cms.handlers.exam.UpdateExamHallTicketHandler;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;

public class UpdateExamHallTicketAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ScoreSheetAction.class);
	
	/**
	 * Method to set the required data to the form to display it in UpdateExamHallTicket.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUpdateExamPublishHallTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initUpdateExamPublishHallTicket input");
		UpdateExamHallTicketForm updateExamHallTicketForm = (UpdateExamHallTicketForm) form;
		updateExamHallTicketForm.resetFields();
		log.info("Exit initUpdateExamPublishHallTicket input");
		
		return mapping.findForward(CMSConstants.UPDATE_PUBLISH_HALL_TICKET);
	}
	
	/**
	 * Method to select the candidates for UpdateExamHallTicket.jsp based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPublishHallTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		UpdateExamHallTicketForm updateExamHallTicketForm = (UpdateExamHallTicketForm) form;
		 ActionErrors errors = updateExamHallTicketForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				UpdateExamHallTicketHandler updateExamHallTicketHandler=UpdateExamHallTicketHandler.getInstance();
				List<ExamPublishHallTicketTO> selectedCandidates = updateExamHallTicketHandler.getListOfCandidates(updateExamHallTicketForm);
				if (selectedCandidates.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					log.info("Exit getPublishHallTicket Result - getPublishHallTicket size 0");
					return mapping.findForward(CMSConstants.UPDATE_PUBLISH_HALL_TICKET);
				} 
				updateExamHallTicketForm.setListTo(selectedCandidates);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				updateExamHallTicketForm.setErrorMessage(msg);
				updateExamHallTicketForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit getPublishHallTicket Result - getSelectedCandidates errors not empty ");
		}
		log.info("Entered UpdateExamHallTicketAction - getPublishHallTicket");
		return mapping.findForward(CMSConstants.UPDATE_PUBLISH_HALL_TICKET);
	}
	
	/**
	 * Method to select the candidates for UpdateExamHallTicket.jsp based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePublishHallTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		UpdateExamHallTicketForm updateExamHallTicketForm = (UpdateExamHallTicketForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = updateExamHallTicketForm.validate(mapping, request);
		setUserId(request, updateExamHallTicketForm);
		if (errors.isEmpty()) {
			try {
				UpdateExamHallTicketHandler updateExamHallTicketHandler=UpdateExamHallTicketHandler.getInstance();
				List<ExamPublishHallTicketTO> selectedCandidates = updateExamHallTicketForm.getListTo();
				boolean isUpdated=updateExamHallTicketHandler.updatePublishHallTicket(selectedCandidates,updateExamHallTicketForm);
				if (isUpdated) {
					ActionMessage message = new ActionMessage("knowledgepro.exam.publishHallTicket.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					updateExamHallTicketForm.resetFields();
					return mapping.findForward(CMSConstants.UPDATE_PUBLISH_HALL_TICKET);
				} else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.publishHallTicket.update.failure"));
					saveErrors(request, errors);
				}
				updateExamHallTicketForm.setListTo(selectedCandidates);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				updateExamHallTicketForm.setErrorMessage(msg);
				updateExamHallTicketForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit getPublishHallTicket Result - getSelectedCandidates errors not empty ");
		}
		log.info("Entered UpdateExamHallTicketAction - getPublishHallTicket");
		return mapping.findForward(CMSConstants.UPDATE_PUBLISH_HALL_TICKET);
	}
}
