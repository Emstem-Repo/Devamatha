package com.kp.cms.actions.attendance;

import java.util.Calendar;
import java.util.HashMap;
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.ClassEntryForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ClassEntryHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * An action class for Class Entry
 */
@SuppressWarnings("deprecation")
public class ClassEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ClassEntryAction.class);
	private static final String CLASS_ENTRY_OPERATION = "ClassEntryOperation";

	/**
	 * Method to initialize Class Entry with isActive=1 to display in UI
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initClassEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entering initClassEntry");
		ClassEntryForm classEntryForm = (ClassEntryForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			classEntryForm.setYear(null);
			setUserId(request, classEntryForm);
			classEntryForm.clear();
			assignListToForm(classEntryForm);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			classEntryForm.setErrorMessage(msg);
			classEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving initClassEntry");
		return mapping.findForward(CMSConstants.CLASSES_ENTRY);
	}

	/**
	 * Method to initialize Class Entry wrt Academic year and isActive=1 to
	 * display in UI
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setClassEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entering setClassEntry");
		ClassEntryForm classEntryForm = (ClassEntryForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, classEntryForm);
			assignListToForm(classEntryForm);
			setOptionsDataInEditMode(classEntryForm, request);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			classEntryForm.setErrorMessage(msg);
			classEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving setClassEntry");
		return mapping.findForward(CMSConstants.CLASSES_ENTRY);
	}

	/**
	 * Method to Add a classEntry entry for a particular course, year and
	 * semester
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addClassEntryEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering addClassEntryEntry");
		ClassEntryForm classEntryForm = (ClassEntryForm) form;
		 ActionErrors errors = classEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if (errors.isEmpty()) {
				ClassEntryHandler.getInstance().checkForDuplicate(
						classEntryForm);
				ClassEntryHandler.getInstance().addClassEntry(classEntryForm);
				ActionMessage message = new ActionMessage(
						CMSConstants.ATTEDANCE_CLASSES_ADD_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				classEntryForm.clear();
				assignListToForm(classEntryForm);
			} else {
				setOptionsDataInEditMode(classEntryForm, request);
				saveErrors(request, errors);
			}
		} catch (DuplicateException e1) {
			if (e1.getMessage().equals("class")) {
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.ATTEDANCE_CLASSES_DUPLICATE));
			} else if (e1.getMessage().equals("combinations")) {
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.ATTEDANCE_CLASSES_FIELD1_DUPLICATE,
						classEntryForm.getSectionName()));
			}
			setOptionsDataInEditMode(classEntryForm, request);
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ATTEDANCE_CLASSES_ACTIVATE_REACTIVATE));
			setOptionsDataInEditMode(classEntryForm, request);
			saveErrors(request, errors);
		} catch (Exception e) {
			log.debug("Action :addclassEntryEntry exception occured");
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ATTEDANCE_CLASSES_ADD_FAILURE));
			saveErrors(request, errors);
		}
		log.info("Leaving addClassEntryEntry");
		return mapping.findForward(CMSConstants.CLASSES_ENTRY);
	}

	/**
	 * Method to make the Class Entry inactive
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteClassEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering deleteclassEntry");
		ClassEntryForm ClassEntryForm = (ClassEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if (ClassEntryForm.getId() != null) {
				ClassEntryHandler.getInstance()
						.deleteClassEntry(ClassEntryForm);
				ActionMessage message = new ActionMessage(
						CMSConstants.ATTEDANCE_CLASSES_DELETE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				ClassEntryForm.clear();
				assignListToForm(ClassEntryForm);
			}
		} catch (Exception e) {
			log.debug("Action :deleteclassEntry exception occured");
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ATTEDANCE_CLASSES_DELETE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug(":Leaving deleteclassEntry");
		return mapping.findForward(CMSConstants.CLASSES_ENTRY);
	}

	/**
	 * Method to update the Class Entry
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateClassEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering updateclassEntryEntry");
		ClassEntryForm classEntryForm = (ClassEntryForm) form;
		 ActionErrors errors = classEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if (errors.isEmpty()) {
				if(classEntryForm.getYear().equals(classEntryForm.getOriginalYear()) && classEntryForm.getTermNo().equals(classEntryForm.getOriginalTermNo()) &&
						classEntryForm.getClassName().equalsIgnoreCase(classEntryForm.getOriginalClassName()) && classEntryForm.getSectionName().equalsIgnoreCase(classEntryForm.getOriginalSectionName())
						&& classEntryForm.getClassSchemewiseId().equals(classEntryForm.getOriginalClassSchemewiseId()) && classEntryForm.getCourseId().equalsIgnoreCase(classEntryForm.getOriginalCourseId())
						){
					ClassEntryHandler.getInstance().updateClassEntry(classEntryForm);
					ActionMessage message = new ActionMessage(
							CMSConstants.ATTEDANCE_CLASSES_UPDATE_SUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					classEntryForm.clear();
					assignListToForm(classEntryForm);
				}else{
					boolean classNameCheckrequired = ClassEntryHandler
					.getInstance().isDuplicateClassCheckRequired(
							classEntryForm);
					
					if (classNameCheckrequired) {
						ClassEntryHandler.getInstance().checkDuplicateFields(
								classEntryForm);
					}
					
					boolean isCheckrequired = ClassEntryHandler.getInstance()
					.isDuplicateCheckRequired(classEntryForm);
					if (isCheckrequired) {
						ClassEntryHandler.getInstance().checkForDuplicate(
								classEntryForm);
					}
					ClassEntryHandler.getInstance()
					.updateClassEntry(classEntryForm);
					ActionMessage message = new ActionMessage(
							CMSConstants.ATTEDANCE_CLASSES_UPDATE_SUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					classEntryForm.clear();
					assignListToForm(classEntryForm);
				}
			} else {
				log.info("Leaving  updateClassEntryEntry");
				request.setAttribute(CLASS_ENTRY_OPERATION,
						CMSConstants.EDIT_OPERATION);
				setOptionsDataInEditMode(classEntryForm, request);
				saveErrors(request, errors);
			}
		} catch (DuplicateException e1) {
			request.setAttribute(CLASS_ENTRY_OPERATION,
					CMSConstants.EDIT_OPERATION);
			if (e1.getMessage().equals("class")) {
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.ATTEDANCE_CLASSES_DUPLICATE));
			} else if (e1.getMessage().equals("combinations")) {
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.ATTEDANCE_CLASSES_FIELD1_DUPLICATE,
						classEntryForm.getSectionName()));
			}
			setOptionsDataInEditMode(classEntryForm, request);
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			request.setAttribute(CLASS_ENTRY_OPERATION,
					CMSConstants.EDIT_OPERATION);
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ATTEDANCE_CLASSES_ACTIVATE_REACTIVATE));
			saveErrors(request, errors);
			setOptionsDataInEditMode(classEntryForm, request);
		} catch (Exception e) {
			log.debug("Action :updateclassEntryEntry exception occured");
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ATTEDANCE_CLASSES_UPDATE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("Action :Leaving updateclassEntryEntry");
		return mapping.findForward(CMSConstants.CLASSES_ENTRY);
	}

	/**
	 * Method to activate the inactive classEntry.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateClassEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Action :Entering activateclassEntry");
		ClassEntryForm ClassEntryForm = (ClassEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if (ClassEntryForm.getActivateId() != null) {
				ClassEntryHandler.getInstance().activateClassEntry(
						ClassEntryForm);
				ActionMessage message = new ActionMessage(
						CMSConstants.ATTEDANCE_CLASSES_ACTIVATE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				ClassEntryForm.clear();
				assignListToForm(ClassEntryForm);
			}
		} catch (Exception e) {
			log.debug("Action :activateclassEntry with exception occured");
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ATTEDANCE_CLASSES_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		log.debug("Action :exiting activateclassEntry");
		return mapping.findForward(CMSConstants.CLASSES_ENTRY);
	}

	/**
	 * Method to set data to the form when the edit button is clicked
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editClassEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Action :Entering editclassEntry");
		ClassEntryForm classEntryForm = (ClassEntryForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			ClassEntryHandler.getInstance().getClassdetails(classEntryForm);
			setOptionsDataInEditMode(classEntryForm, request);
			request.setAttribute(CLASS_ENTRY_OPERATION,
					CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			classEntryForm.clear();
			log.debug("Action :editclassEntry exception occured");
			errors.add(CMSConstants.ERROR, new ActionError(
					CMSConstants.ATTEDANCE_CLASSES_LOADERROR));
			saveErrors(request, errors);
		}
		log.debug("Action :Leaving editclassEntry");
		return mapping.findForward(CMSConstants.CLASSES_ENTRY);
	}

	/**
	 * Method to set the required data to the form to display in UI
	 * 
	 * @param classEntryForm
	 * @throws Exception
	 */
	public void assignListToForm(ClassEntryForm classEntryForm)
			throws Exception {
		log.info("Entering assignListToForm");
		List<ClassesTO> classEntryLists = null;
		ExamGenHandler e = new ExamGenHandler();

		classEntryForm.setCourseGroupCodeList(e.getCourseGroupCodeList());
		if (classEntryForm.getYear() == null
				|| classEntryForm.getYear().isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}// end
			classEntryLists = ClassEntryHandler.getInstance().getAllClasses(
					currentYear);
		} else {
			int year = Integer.parseInt(classEntryForm.getYear().trim());
			classEntryLists = ClassEntryHandler.getInstance().getAllClasses(
					year);
		}
		if (classEntryLists != null) {
			classEntryForm.setClassesList(classEntryLists);

		}
		List<CourseTO> courseList = CourseHandler.getInstance()
				.getCourses();
		classEntryForm.setCourseList(courseList);

		log.info("Leaving assignListToForm");
	}

	/**
	 * 
	 * @param classEntryForm
	 * @param request
	 * @throws Exception
	 */
	public void setOptionsDataInEditMode(ClassEntryForm classEntryForm,
			HttpServletRequest request) throws Exception {
		if (classEntryForm.getCourseId().length() != 0
				&& classEntryForm.getYear().length() != 0) {
			Map<Integer, Integer> semesterMap = CommonAjaxHandler.getInstance()
					.getSemistersByYearAndCourseScheme(
							Integer.parseInt(classEntryForm.getYear()),
							Integer.parseInt(classEntryForm.getCourseId()));
			request.setAttribute("semisterMap", semesterMap);
		}
	}
}