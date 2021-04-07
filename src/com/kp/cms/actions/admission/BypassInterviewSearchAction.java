package com.kp.cms.actions.admission;

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
import com.kp.cms.forms.admission.StudentSearchForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.StudentSearchHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.StudentSearchTO;

/**
 * Performs bypass interview action.
 */
public class BypassInterviewSearchAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(BypassInterviewSearchAction.class);
	
	/**
	 * Initializes the search action
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward initBypassInterviewSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initBypassInterviewSearch of BypassInterviewSearchAction class.");
		StudentSearchForm studentSearchForm = (StudentSearchForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","bypass_interview");
		
		studentSearchForm.resetFields();
		setRequiredDatatoForm(request);
		log.info("exit of initBypassInterviewSearch of BypassInterviewSearchAction class.");
		return mapping
				.findForward(CMSConstants.INIT_BYPASS_INTERVIEW_STUDENT_SEARCH);
	}

	/**
	 * Performs the cancellation action of the search.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward cancelBypassInterviewSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into cancelBypassInterviewSearch of BypassInterviewSearchAction class.");
		StudentSearchForm studentSearchForm = (StudentSearchForm) form;
		studentSearchForm.resetFields();
		setRequiredDatatoForm(request);
		log.info("exit of cancelBypassInterviewSearch of BypassInterviewSearchAction class.");
		return mapping
				.findForward(CMSConstants.INIT_BYPASS_INTERVIEW_STUDENT_SEARCH);
	}

	/**
	 * Get the bypass interview search results.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward getBypassInterviewSearchResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getBypassInterviewSearchResults of BypassInterviewSearchAction class.");
		StudentSearchForm studentSearchForm = ((StudentSearchForm) form);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = studentSearchForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<StudentSearchTO> searchTO = StudentSearchHandler
						.getInstance().getBypassInterviewSearchResults(
								studentSearchForm, false);
				studentSearchForm.setStudentSearch(searchTO);
				if (searchTO.isEmpty() ) {
					message = new ActionMessage(
							"knowledgepro.admission.noresultsfound");
					messages.add(CMSConstants.MESSAGES, message);
					addErrors(request, messages);
				}
				log.info("exit of getBypassInterviewSearchResults of BypassInterviewSearchAction class.");
				return mapping
						.findForward(CMSConstants.GET_BYPASS_INTERVIEW_STUDENT_SEARCH);
			} catch (Exception e) {
				log.error("Error while getting search results"+e.getMessage());
				String msg = super.handleApplicationException(e);
				studentSearchForm.setErrorMessage(msg);
				studentSearchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(request);
			return mapping
					.findForward(CMSConstants.INIT_BYPASS_INTERVIEW_STUDENT_SEARCH);
		}
	}

	/**
	 * selects the candidates for bypass interview.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward submitSelectedBypassInterviewList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitSelectedBypassInterviewList of BypassInterviewSearchAction class.");
		StudentSearchForm studentSearchForm = ((StudentSearchForm) form);

		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		setUserId(request, studentSearchForm);
		if (studentSearchForm.getSelectedCandidates() == null) {
			message = new ActionMessage(
					"knowledgepro.admission.selectcandidate");
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);

		} else {
			try {
				List<OrganizationTO> tos=OrganizationHandler.getInstance().getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					studentSearchForm.setNeedApproval(orgTO.isFinalMeritListApproval());
				}
				StudentSearchHandler.getInstance()
						.updateSgetBypassInterviewCandidates(studentSearchForm,
								true);
				List<StudentSearchTO> searchTO = StudentSearchHandler
						.getInstance().getBypassInterviewSearchResults(
								studentSearchForm, false);
				studentSearchForm.setStudentSearch(searchTO);
				message = new ActionMessage("knowledgepro.admission.addtolist");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error("Error while submitting selected candidates"+e.getMessage());
				String msg = super.handleApplicationException(e);
				studentSearchForm.setErrorMessage(msg);
				studentSearchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

			}

		}

		studentSearchForm.setSelectedCandidates(null);
		log.info("exit of submitSelectedBypassInterviewList of BypassInterviewSearchAction class.");
		return mapping
				.findForward(CMSConstants.GET_BYPASS_INTERVIEW_STUDENT_SEARCH);

	}

	/**
	 * get the selected candidates list.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward getSelectedBypassInterviewList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getSelectedBypassInterviewList of BypassInterviewSearchAction class.");
		StudentSearchForm studentSearchForm = ((StudentSearchForm) form);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		ActionErrors errors = studentSearchForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<StudentSearchTO> searchTO = StudentSearchHandler
						.getInstance().getBypassInterviewSearchResults(
								studentSearchForm, true);
				studentSearchForm.setStudentSearch(searchTO);
				if (searchTO.isEmpty()) {
					message = new ActionMessage(
							"knowledgepro.admission.noresultsfound");
					messages.add(CMSConstants.MESSAGES, message);
					addErrors(request, messages);
				}
				log.info("exit of getSelectedBypassInterviewList of BypassInterviewSearchAction class.");
				return mapping
						.findForward(CMSConstants.GET_SELECTED_BYPASS_INTERVIEW_STUDENT_SEARCH);
			} catch (Exception e) {
				log.error("Error while getting selected results"+e.getMessage());
				String msg = super.handleApplicationException(e);
				studentSearchForm.setErrorMessage(msg);
				studentSearchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(request);
			return mapping
					.findForward(CMSConstants.GET_BYPASS_INTERVIEW_STUDENT_SEARCH);
		}
	}

	/**
	 * Removes the selected candidates from the list.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward removeSelectedBypassInterviewList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into removeSelectedBypassInterviewList of BypassInterviewSearchAction class.");
		StudentSearchForm studentSearchForm = ((StudentSearchForm) form);

		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		if (studentSearchForm.getSelectedCandidates() == null) {
			message = new ActionMessage(
					"knowledgepro.admission.selectcandidate");
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);

		} else {

			try {
				StudentSearchHandler.getInstance()
						.updateSgetBypassInterviewCandidates(studentSearchForm,
								false);
				List<StudentSearchTO> searchTO = StudentSearchHandler
						.getInstance().getBypassInterviewSearchResults(
								studentSearchForm, true);
				studentSearchForm.setStudentSearch(searchTO);
				message = new ActionMessage(
						"knowledgepro.admission.addremovefromlist");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error("Error while removing selected candidates"+e.getMessage());
				String msg = super.handleApplicationException(e);
				studentSearchForm.setErrorMessage(msg);
				studentSearchForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

			}

		}
		studentSearchForm.setSelectedCandidates(null);
		log.info("exit of removeSelectedBypassInterviewList of BypassInterviewSearchAction class.");
		return mapping
				.findForward(CMSConstants.GET_SELECTED_BYPASS_INTERVIEW_STUDENT_SEARCH);
	}

	/**
	 * Set the required data to the form.
	 * 
	 * @param studentSearchForm
	 *            - Represents the student search form.
	 * @param request
	 *            - The HTTP request we are processing
	 */
	private void setRequiredDatatoForm(
			HttpServletRequest request) throws Exception {
		log.info("entering into setRequiredDatatoForm of BypassInterviewSearchAction class.");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
				.getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.info("exit of setRequiredDatatoForm of BypassInterviewSearchAction class.");
	}
}
