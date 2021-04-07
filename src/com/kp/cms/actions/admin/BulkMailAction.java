package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.BulkMailForm;
import com.kp.cms.handlers.admin.BulkMailHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.StudentSearchTO;

public class BulkMailAction extends BaseDispatchAction
 {

	private static final Log log = LogFactory.getLog(BulkMailAction.class);
	
	
	/**
	 * Performs the student search action for mail.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initStudentSearch in BulkMailAction class.");
		BulkMailForm bulkMailForm = (BulkMailForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Bulk Mail");
		bulkMailForm.resetFields();
		setRequiredDatatoForm(request);
		log.info("exit of initStudentSearch in BulkMailAction class.");
		return mapping.findForward(CMSConstants.BULK_MAIL_SEARCH);
	}

	/**
	 * Performs cancel student search action.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelStudentSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into cancelStudentSearch in BulkMailAction class.");
		BulkMailForm bulkMailForm = (BulkMailForm) form;
		bulkMailForm.resetFields();
		setRequiredDatatoForm(request);
		log.info("exit of cancelStudentSearch in BulkMailAction class.");
		return mapping.findForward(CMSConstants.BULK_MAIL_SEARCH);
	}

	/**
	 * Get the student search results for mail.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentSearchResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getStudentSearchResults in BulkMailAction class.");
		BulkMailForm bulkMailForm = (BulkMailForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = bulkMailForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<StudentSearchTO> searchTO = BulkMailHandler.getInstance()
						.getStudentSearchResults((BulkMailForm) form);
				bulkMailForm.setStudentSearch(searchTO);
				if (searchTO.isEmpty()) {
					message = new ActionMessage(
							"knowledgepro.admission.noresultsfound");
					messages.add("messages", message);
					saveMessages(request, messages);
					setRequiredDatatoForm( request);
					return mapping.findForward(CMSConstants.BULK_MAIL_SEARCH);
				}
				log.info("exit of getStudentSearchResults in BulkMailAction class.");
				return mapping.findForward(CMSConstants.BULK_MAIL_SEARCH_RESULTS);
			} catch (Exception e) {
				log.error("Error while getting search results"+e.getMessage());
				String msg = super.handleApplicationException(e);
				bulkMailForm.setErrorMessage(msg);
				bulkMailForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm( request);
			return mapping.findForward(CMSConstants.BULK_MAIL_SEARCH);
		}
	}

	/**
	 * Set the required data to form.
	 * @param studentSearchForm
	 * @param request
	 */
	private void setRequiredDatatoForm(	HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
				.getProgramType();
		request.setAttribute("programTypeList", programTypeList);
	}

	/**
	 * Sends mail to selected students.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward bulkMailMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into bulkMailMethod in BulkMailAction class.");
		BulkMailForm bulkMailForm = ((BulkMailForm) form);
		bulkMailForm.setSubject(null);
		bulkMailForm.setDesc(null);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		String[] emails = bulkMailForm.getEmailAddresses();
		if (emails == null || (emails != null  && emails.length ==0 )) {
			message = new ActionMessage(
					"knowledgepro.admin.bulkmail.atleastonemail");
			messages.add("messages", message);
			addErrors(request, messages);

			return mapping.findForward(CMSConstants.BULK_MAIL_SUBMITTEDSTUDENTS);
		} else {
			bulkMailForm.setEmailAddresses(emails);
			log.info("exit of bulkMailMethod in BulkMailAction class.");
			return mapping.findForward(CMSConstants.BULK_MAIL_MAIL_DESCRIPTION);
		}
		
	}

	/**
	 *  Sends mail to selected students.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendMailMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into sendMailMethod in BulkMailAction class.");
		BulkMailForm bulkMailForm = ((BulkMailForm) form);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		if (isCancelled(request)) {
			return mapping.findForward(CMSConstants.BULK_MAIL_SUBMITTEDSTUDENTS);
		}

		try {
			BulkMailHandler.getInstance().sendMails(bulkMailForm);
			setRequiredDatatoForm( request);
			message = new ActionMessage("knowledgepro.admin.bulkmail.sent");
			messages.add("messages", message);
			addMessages(request, messages);
			bulkMailForm.resetFields();
		} catch (Exception e) {

			log.error("Error while sending the mail"+e.getMessage());
			String msg = super.handleApplicationException(e);
			bulkMailForm.setErrorMessage(msg);
			bulkMailForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("exit of sendMailMethod in BulkMailAction class.");
		return mapping.findForward(CMSConstants.BULK_MAIL_SEARCH);
	}

}
