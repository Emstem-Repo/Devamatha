package com.kp.cms.actions.admission;

import java.util.Iterator;
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
import com.kp.cms.bo.admin.Weightage;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.WeightageDefenitionForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.WeightageDefenitionHandler;
import com.kp.cms.helpers.admission.WeightageDefenitionHelper;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.CoursePrerequisiteWeightageTO;

/**
 * Performs actions related to Weightage definition
 */
public class WeightageDefenitionAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(WeightageDefenitionAction.class);
	
	
	/**
	 * 
	 * Initializes the weightage entry screen.
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
	public ActionForward initWeightageEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initWeightageEntry of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","weightage_definition");
		
		weightageDefenitionForm.resetWeightageEntry();
		setRequiredDatatoForm(request);
		log.info("exit of initWeightageEntry of WeightageDefenitionAction class.");
		return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_ENTRY);
	}
	
	/**
	 * 
	 * Cancels the weightage entry screen.
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
	public ActionForward cancelWeightageEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into cancelWeightageEntry of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		weightageDefenitionForm.resetWeightageEntry();
		setRequiredDatatoForm(request);
		log.info("exit of cancelWeightageEntry of WeightageDefenitionAction class.");
		return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_ENTRY);
	}

	/**
	 * 
	 * Initializes weightage definition screen.
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
	public ActionForward initWeightageDefenition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		weightageDefenitionForm.reset();
		 ActionErrors errors = weightageDefenitionForm.validate(mapping, request);
		if (errors.isEmpty()) {
			WeightageDefenitionHandler.getInstance()
					.setWeightageDefenitionEntry(weightageDefenitionForm);
			log.info("exit of initWeightageDefenition of WeightageDefenitionAction class.");
			return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_DEFENITION);
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(request);
			return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_ENTRY);
		}
	}

	/**
	 * persists weightage definition to the database.
	 * 
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
	public ActionForward submitWeightageDefenition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;

		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		setUserId(request, weightageDefenitionForm);
		try {

			if (Float.valueOf(weightageDefenitionForm.getTotalWeightage()) > 100) {
				message = new ActionMessage(
						"knowledgepro.admission.weightagedefenition.total.error");
				messages.add(CMSConstants.MESSAGES, message);
				addErrors(request, messages);

			} else {

				WeightageDefenitionHandler.getInstance()
						.submitWeightageDefenition(weightageDefenitionForm);
				WeightageDefenitionHandler.getInstance()
				.setWeightageDefenitionEntry(weightageDefenitionForm);
				message = new ActionMessage(CMSConstants.WEIGHTAGE_DEFINITION_UPDATE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			}

		} catch (Exception e) {
			log.error("Error while submitting weightage defenition "+e.getMessage());
			String msg = super.handleApplicationException(e);
			weightageDefenitionForm.setErrorMessage(msg);
			weightageDefenitionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of submitWeightageDefenition of WeightageDefenitionAction class.");
		return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_DEFENITION);

	}

	/**
	 * Initializes education weightage definition screen.
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
	public ActionForward initEducationWeightageDefenition(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initEducationWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		Weightage weightage = WeightageDefenitionHandler.getInstance()
				.getweightageDefenition(weightageDefenitionForm);

		if (weightage == null) {
			message = new ActionMessage(CMSConstants.WEIGHTAGE_DEFENITION_REQUIRED);
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);

			return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_DEFENITION);
		} else {
			try {
				weightageDefenitionForm.setWeightageId(weightage.getId());
				String courseId = weightageDefenitionForm.getCourseId();
				String year = weightageDefenitionForm.getYear();

				weightageDefenitionForm
						.setEducationWeightageList(WeightageDefenitionHandler
								.getInstance()
								.getEducationalWeightageDefenitionList(
										Integer.valueOf(courseId),
										Integer.valueOf(year)));

			} catch (Exception exception) {
				log.error("Error while initializing education weightage defenition "+exception.getMessage());
				String msg = super.handleApplicationException(exception);
				weightageDefenitionForm.setErrorMessage(msg);
				weightageDefenitionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("exit of initEducationWeightageDefenition of WeightageDefenitionAction class.");
			return mapping
					.findForward(CMSConstants.INIT_EDUCATION_WEIGHTAGE_DEFENITION);
		}

	}

	/**
	 * Persists education weightage to the database.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitEducationWeightageDefenition(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initWeightageEntry of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		setUserId(request, weightageDefenitionForm);
		try {
			if (Float.valueOf(weightageDefenitionForm
					.getTotalEducationWeightage()) > 100
					|| Float.valueOf(weightageDefenitionForm
							.getTotalEducationWeightage()) < 100) {
				message = new ActionMessage(
						"knowledgepro.admission.weightagedefenition.totalequal.error");
				messages.add(CMSConstants.MESSAGES, message);
				addErrors(request, messages);

			} else {
				WeightageDefenitionHandler.getInstance()
						.submitEducationWeightageDefenition(
								weightageDefenitionForm);
				String courseId = weightageDefenitionForm.getCourseId();
				String year = weightageDefenitionForm.getYear();

				weightageDefenitionForm
						.setEducationWeightageList(WeightageDefenitionHandler
								.getInstance()
								.getEducationalWeightageDefenitionList(
										Integer.valueOf(courseId),
										Integer.valueOf(year)));
				message = new ActionMessage(
						CMSConstants.WEIGHTAGE_DEFINITION_UPDATE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			}

		} catch (Exception e) {
			log.error("Error while submitting education weightage defenition "+e.getMessage());
			String msg = super.handleApplicationException(e);
			weightageDefenitionForm.setErrorMessage(msg);
			weightageDefenitionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of initWeightageEntry of WeightageDefenitionAction class.");
		return mapping
				.findForward(CMSConstants.INIT_EDUCATION_WEIGHTAGE_DEFENITION);
	}

	/**
	 * 
	 * Initializes interview weightage definition screen.
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
	public ActionForward initInterviewWeightageDefenition(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initInterviewWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		Weightage weightage = WeightageDefenitionHandler.getInstance()
				.getweightageDefenition(weightageDefenitionForm);

		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		if (weightage == null) {

			message = new ActionMessage(CMSConstants.WEIGHTAGE_DEFENITION_REQUIRED);
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);
			return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_DEFENITION);
		} else {
			weightageDefenitionForm.setWeightageId(weightage.getId());
			try {
				String courseId = weightageDefenitionForm.getCourseId();
				String year = weightageDefenitionForm.getYear();
				weightageDefenitionForm
						.setInterviewWeightageList(WeightageDefenitionHandler
								.getInstance()
								.getInterviewWeightageDefenitionList(
										Integer.valueOf(courseId),
										Integer.valueOf(year)));
			} catch (Exception exception) {
				log.error("Error while initializing interview weightage defenition "+exception.getMessage());
				String msg = super.handleApplicationException(exception);
				weightageDefenitionForm.setErrorMessage(msg);
				weightageDefenitionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("exit of initInterviewWeightageDefenition of WeightageDefenitionAction class.");
			return mapping
					.findForward(CMSConstants.INIT_INTERVIEW_WEIGHTAGE_DEFENITION);
		}

	}

	/**
	 * Persists interview weightage to the database.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInterviewWeightageDefenition(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitInterviewWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		setUserId(request, weightageDefenitionForm);
		try {

			if (Float.valueOf(weightageDefenitionForm
					.getTotalInterviewWeightage()) > 100
					|| Float.valueOf(weightageDefenitionForm
							.getTotalInterviewWeightage()) < 100) {
				message = new ActionMessage(
						"knowledgepro.admission.weightagedefenition.totalequal.error");
				messages.add(CMSConstants.MESSAGES, message);
				addErrors(request, messages);
			} else {
				WeightageDefenitionHandler.getInstance()
						.submitInterviewWeightageDefenition(
								weightageDefenitionForm);
				String courseId = weightageDefenitionForm.getCourseId();
				String year = weightageDefenitionForm.getYear();
				weightageDefenitionForm
						.setInterviewWeightageList(WeightageDefenitionHandler
								.getInstance()
								.getInterviewWeightageDefenitionList(
										Integer.valueOf(courseId),
										Integer.valueOf(year)));
				message = new ActionMessage(
						CMSConstants.WEIGHTAGE_DEFINITION_UPDATE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			}

		} catch (Exception e) {
			log.error("Error while submitting interview weightage defenition "+e.getMessage());
			String msg = super.handleApplicationException(e);
			weightageDefenitionForm.setErrorMessage(msg);
			weightageDefenitionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of submitInterviewWeightageDefenition of WeightageDefenitionAction class.");
		return mapping
				.findForward(CMSConstants.INIT_INTERVIEW_WEIGHTAGE_DEFENITION);

	}

	/**
	 * 
	 * Initializes prerequisite weightage screen.
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
	public ActionForward initPrerequisiteWeightageDefenition(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initPrerequisiteWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		Weightage weightage = WeightageDefenitionHandler.getInstance()
				.getweightageDefenition(weightageDefenitionForm);

		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		if (weightage == null) {
			message = new ActionMessage(
					CMSConstants.WEIGHTAGE_DEFENITION_REQUIRED);
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);

			return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_DEFENITION);
		} else {
			weightageDefenitionForm.setWeightageId(weightage.getId());
			try {
				String courseId = weightageDefenitionForm.getCourseId();
				String year = weightageDefenitionForm.getYear();
				weightageDefenitionForm
						.setPrerequisiteWeightageList(WeightageDefenitionHandler
								.getInstance()
								.getPrerequisiteWeightageDefenitionList(
										Integer.valueOf(courseId),
										Integer.valueOf(year)));
			} catch (Exception exception) {
				log.error("Error while initializing prerequisite weightage defenition "+exception.getMessage());				
				String msg = super.handleApplicationException(exception);
				weightageDefenitionForm.setErrorMessage(msg);
				weightageDefenitionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("exit of initPrerequisiteWeightageDefenition of WeightageDefenitionAction class.");
			return mapping
					.findForward(CMSConstants.INIT_PREREQUISITE_WEIGHTAGE_DEFENITION);
		}

	}

	/**
	 * Persists pre-requisite weightage definition to the database.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitPrerequisiteWeightageDefenition(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitPrerequisiteWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		setUserId(request, weightageDefenitionForm);
		try {

			StringBuffer isValied = isValidPrerequisite(weightageDefenitionForm
					.getPrerequisiteWeightageList());

			if (isValied.length() > 0) {
				message = new ActionMessage(
						"knowledgepro.admission.weightagedefenition.prerequisite.error",
						isValied);
				messages.add(CMSConstants.MESSAGES, message);
				addErrors(request, messages);

			} else {
				WeightageDefenitionHandler.getInstance()
						.submitPrerequisiteWeightageDefenition(
								weightageDefenitionForm);
				String courseId = weightageDefenitionForm.getCourseId();
				String year = weightageDefenitionForm.getYear();
				weightageDefenitionForm
						.setPrerequisiteWeightageList(WeightageDefenitionHandler
								.getInstance()
								.getPrerequisiteWeightageDefenitionList(
										Integer.valueOf(courseId),
										Integer.valueOf(year)));
				message = new ActionMessage(
						CMSConstants.WEIGHTAGE_DEFINITION_UPDATE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			}

		} catch (Exception e) {
			log.error("Error while submitting prerequisite weightage defenition "+e.getMessage());
			String msg = super.handleApplicationException(e);
			weightageDefenitionForm.setErrorMessage(msg);
			weightageDefenitionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of submitPrerequisiteWeightageDefenition of WeightageDefenitionAction class.");
		return mapping
				.findForward(CMSConstants.INIT_PREREQUISITE_WEIGHTAGE_DEFENITION);

	}

	/**
	 * Returns pre-requisite weightage names if the pre- requisite values
	 * greater then 100, other wise returns empty string.
	 * 
	 * @param prerequisiteWeightageList
	 * @return
	 */
	private StringBuffer isValidPrerequisite(
			List<CoursePrerequisiteWeightageTO> prerequisiteWeightageList) {
		log.info("entering into isValidPrerequisite of WeightageDefenitionAction class.");
		StringBuffer isValied = new StringBuffer();
		Iterator<CoursePrerequisiteWeightageTO> preRequisiteIterator = prerequisiteWeightageList
				.iterator();
		while (preRequisiteIterator.hasNext()) {
			CoursePrerequisiteWeightageTO coursePrerequisiteWeightageTO = (CoursePrerequisiteWeightageTO) preRequisiteIterator
					.next();
			if ((Double.valueOf(coursePrerequisiteWeightageTO
					.getWeightagePercentage())) > 100) {
				if (isValied.length() > 0) {
					isValied = isValied.append(" and ");
				}
				isValied = isValied.append(coursePrerequisiteWeightageTO.getPrerequisiteName());
			}
		}
		log.info("exit of isValidPrerequisite of WeightageDefenitionAction class.");
		return isValied;
	}

	/**
	 * Initializes general weightage definition screen.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGeneralWeightageDefenition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initGeneralWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		weightageDefenitionForm.setSelectionType(null);
		Weightage weightage = WeightageDefenitionHandler.getInstance()
				.getweightageDefenition(weightageDefenitionForm);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null; 
		if (weightage == null) {
			message = new ActionMessage(
					CMSConstants.WEIGHTAGE_DEFENITION_REQUIRED);
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);
			return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_DEFENITION);
		} else {
			weightageDefenitionForm
					.setWeightageTypeMap(WeightageDefenitionHelper
							.getInstance().getWeightageType());
			weightageDefenitionForm.setWeightageId(weightage.getId());
			log.info("exit of initGeneralWeightageDefenition of WeightageDefenitionAction class.");
			return mapping
					.findForward(CMSConstants.INIT_GENERAL_WEIGHTAGE_DEFENITION);
		}

	}

	/**
	 * selects general weightage types bases on selected weightage type
	 * 
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
	public ActionForward selectGeneralWeightageDefenition(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into selectGeneralWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		Weightage weightage = WeightageDefenitionHandler.getInstance()
				.getweightageDefenition(weightageDefenitionForm);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		if (weightage == null) {

			message = new ActionMessage(
					CMSConstants.WEIGHTAGE_DEFENITION_REQUIRED);
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);
			return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_DEFENITION);
		} else {
			try {

				weightageDefenitionForm.setWeightageId(weightage.getId());
				String courseId = weightageDefenitionForm.getCourseId();
				String year = weightageDefenitionForm.getYear();
				setWeightageType(weightageDefenitionForm, courseId, year);

			} catch (Exception exception) {
				log.error("Error while selecting general weightage defenition "+exception.getMessage());
				String msg = super.handleApplicationException(exception);
				weightageDefenitionForm.setErrorMessage(msg);
				weightageDefenitionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("exit of selectGeneralWeightageDefenition of WeightageDefenitionAction class.");
			return mapping
					.findForward(CMSConstants.INIT_GENERAL_WEIGHTAGE_DEFENITION);
		}

	}

	/**
	 * fetches general weightage type list based on selection.
	 * 
	 * @param weightageDefenitionForm
	 * @param courseId
	 * @param year
	 * @param selectionType
	 * @throws ApplicationException
	 */
	private void setWeightageType(
			WeightageDefenitionForm weightageDefenitionForm, String courseId,
			String year) throws ApplicationException {
		log.info("entering into setWeightageType of WeightageDefenitionAction class.");

		if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase("1")) {
			weightageDefenitionForm
					.setRuralUrbanWeightageList(WeightageDefenitionHandler
							.getInstance().getRuralUrbanWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"2")) {
			weightageDefenitionForm
					.setCasteWeightageList(WeightageDefenitionHandler
							.getInstance().getCasteWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));

		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"3")) {
			weightageDefenitionForm
					.setReligionSectionWeightageList(WeightageDefenitionHandler
							.getInstance().getReligionSectionWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));

		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"4")) {
			weightageDefenitionForm
					.setCountryWeightageList(WeightageDefenitionHandler
							.getInstance().getCountryWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"5")) {
			weightageDefenitionForm
					.setGenderWeightageList(WeightageDefenitionHandler
							.getInstance().getGenderWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"6")) {
			weightageDefenitionForm
					.setInstituteWeightageList(WeightageDefenitionHandler
							.getInstance().getInstituteWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));

		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"7")) {
			weightageDefenitionForm
					.setNationalityWeightageList(WeightageDefenitionHandler
							.getInstance().getNationalityWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"8")) {
			weightageDefenitionForm
					.setReligionWeightageList(WeightageDefenitionHandler
							.getInstance().getReligionWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"9")) {
			weightageDefenitionForm
					.setResidentCategoryWeightageList(WeightageDefenitionHandler
							.getInstance().getResidentCategoryWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));
		} else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
				"10")) {
			weightageDefenitionForm
					.setUniversityWeightageList(WeightageDefenitionHandler
							.getInstance().getUnbiversityWeightageList(
									Integer.valueOf(courseId),
									Integer.valueOf(year)));
		}
		else if (weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
		         "11")) {
	         weightageDefenitionForm
			    .setPreviousQualificationWeightageList(WeightageDefenitionHandler
					.getInstance().getPreviousQualificationWeightageList(
							Integer.valueOf(courseId),
							Integer.valueOf(year)));
	    }else if(weightageDefenitionForm.getSelectionType().equalsIgnoreCase(
			"12")){
		weightageDefenitionForm.setWorkExperienceWeightageList(WeightageDefenitionHandler
				.getInstance().getWorkExperienceWeightageList(
						Integer.valueOf(courseId),
						Integer.valueOf(year)));
			     
	    }
		log.info("exit of setWeightageType of WeightageDefenitionAction class.");
	}

	/**
	 * Persists general weighhtage types to the database.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitGeneralWeightageDefenition(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitGeneralWeightageDefenition of WeightageDefenitionAction class.");
		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		try {
			setUserId(request, weightageDefenitionForm);
			WeightageDefenitionHandler.getInstance().updateGeneralWeightage(
					weightageDefenitionForm);
			
			String courseId = weightageDefenitionForm.getCourseId();
			String year = weightageDefenitionForm.getYear();

			setWeightageType(weightageDefenitionForm, courseId, year);
			message = new ActionMessage(
					CMSConstants.WEIGHTAGE_DEFINITION_UPDATE_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);

		} catch (Exception exception) {
			log.error("Error while submitting general weightage defenition "+exception.getMessage());
			String msg = super.handleApplicationException(exception);
			weightageDefenitionForm.setErrorMessage(msg);
			weightageDefenitionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of submitGeneralWeightageDefenition of WeightageDefenitionAction class.");
		return mapping
				.findForward(CMSConstants.INIT_GENERAL_WEIGHTAGE_DEFENITION);
	}

	/**
	 * updates weightage for all the candidates in the selected course
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateWeightageDefenition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into updateWeightageDefenition of WeightageDefenitionAction class.");

		WeightageDefenitionForm weightageDefenitionForm = (WeightageDefenitionForm) form;
		try {
			ActionErrors errors = weightageDefenitionForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		setUserId(request, weightageDefenitionForm);
		if (errors.isEmpty()) {
			Weightage weightage = WeightageDefenitionHandler.getInstance()
					.getweightageDefenition(weightageDefenitionForm);
			if (weightage == null) {

				message = new ActionMessage(
						CMSConstants.WEIGHTAGE_DEFENITION_REQUIRED);
				messages.add(CMSConstants.MESSAGES, message);
				addErrors(request, messages);
			} else {
				// updates educational weightage marks
				WeightageDefenitionHandler.getInstance()
						.updateIndividualEducationalWeightageAdjustedMarks(
								weightageDefenitionForm);
				
				// // updates interview weightage marks
				WeightageDefenitionHandler.getInstance()
						.updateIndividualInterviewWeightageAdjustedMarks(
								weightageDefenitionForm,weightage);
				
				 // updates general weightage marks
				WeightageDefenitionHandler.getInstance()
						.updateIndividualGeneralWeightageAdjustedMarks(
								weightageDefenitionForm);

				// updates individualPrerequisiteWeightage
				WeightageDefenitionHandler.getInstance()
						.updateIndividualPrerequisiteWeightageAdjustedMarks(
								weightageDefenitionForm);

				 // updates total weightage marks
				WeightageDefenitionHandler.getInstance()
						.updateIndividualTotalWeightageAdjustedMarks(
								weightageDefenitionForm);

				message = new ActionMessage(
						CMSConstants.WEIGHTAGE_DEFINITION_UPDATE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				weightageDefenitionForm.setProgramTypeId(null);
			}

		} else {
			addErrors(request, errors);
			weightageDefenitionForm.setProgramTypeId(null);
		}

		
		} catch (Exception exception) {
			setRequiredDatatoForm(request);
			log.error("Error while submitting general weightage defenition "+exception.getMessage());
			String msg = super.handleApplicationException(exception);
			weightageDefenitionForm.setErrorMessage(msg);
			weightageDefenitionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequiredDatatoForm(request);
		log.info("exit of updateWeightageDefenition of WeightageDefenitionAction class.");
		return mapping.findForward(CMSConstants.INIT_WEIGHTAGE_ENTRY);

	}

	/**
	 * Set the required data to the request scope of the form.
	 * 
	 * @param weightageDefenitionForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(
			HttpServletRequest request) throws Exception {
		log.info("entering into setRequiredDatatoForm of WeightageDefenitionAction class.");
		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
				.getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.info("exit of setRequiredDatatoForm of WeightageDefenitionAction class.");

	}

}
