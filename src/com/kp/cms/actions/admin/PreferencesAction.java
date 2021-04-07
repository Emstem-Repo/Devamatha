package com.kp.cms.actions.admin;

import java.util.ArrayList;
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
import com.kp.cms.forms.admin.PreferencesForm;
import com.kp.cms.handlers.admin.PreferencesHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.admin.PreferencesHelper;
import com.kp.cms.to.admin.PreferencesTO;
import com.kp.cms.to.admin.ProgramTypeTO;

/**
 * @author
 * 
 */
@SuppressWarnings("deprecation")
public class PreferencesAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(PreferencesAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPreferencesMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		PreferencesForm preferencesForm = (PreferencesForm) form;
		preferencesForm.firstFromreset(mapping, request);  //resetting first form values
		setProgramtypelist(request);  //setting program type list to request for program type population
		setPreferencesToRequest(request, preferencesForm, true);  //setting preferences to request for UI display
		setUserId(request, preferencesForm);  //setting userId to update last changed details
		return mapping.findForward(CMSConstants.INIT_PREFERENCES);

	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to second page
	 * @throws Exception
	 */
	public ActionForward showPreferencesOnCourse(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		PreferencesForm preferencesForm = (PreferencesForm) form;

		ActionErrors errors = preferencesForm.validate(mapping, request);

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			setProgramtypelist(request);
			setPreferencesToRequest(request, preferencesForm, true);
			return mapping.findForward(CMSConstants.INIT_PREFERENCES);
		}

		setprogramMapToRequest(request, preferencesForm);
		setPrefprogramMapToRequest(request, preferencesForm);
		setPrefCourseMapToRequest(request, preferencesForm);
		setPreferencesToRequest(request, preferencesForm, false);

		return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);

	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response this method will add new record in preferences table
	 * @return forward to second page
	 * @throws Exception
	 */
	public ActionForward savePreferencesOnCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		PreferencesForm preferencesForm = (PreferencesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = preferencesForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setPreferencesToRequest(request, preferencesForm, false);
				setprogramMapToRequest(request, preferencesForm);
				setCourseMapToRequest(request, preferencesForm);
				setPrefprogramMapToRequest(request, preferencesForm);
				setPrefCourseMapToRequest(request, preferencesForm);
				return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
			}

			if (preferencesForm.getCourseId().equals(preferencesForm.getPrefCourseId())) {
				errors.add("error", new ActionError("knowledgepro.admin.preference.valid.course"));
				saveErrors(request, errors);
				setPreferencesToRequest(request, preferencesForm, false);
				setprogramMapToRequest(request, preferencesForm);
				setCourseMapToRequest(request, preferencesForm);
				setPrefprogramMapToRequest(request, preferencesForm);
				setPrefCourseMapToRequest(request, preferencesForm);
				return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
			}
			isUpdated = PreferencesHandler.getInstance().addPreferences(preferencesForm, "Add");

			setPreferencesToRequest(request, preferencesForm, false);
			setProgramtypelist(request);
			setprogramMapToRequest(request, preferencesForm);
			setCourseMapToRequest(request, preferencesForm);
			setPrefprogramMapToRequest(request, preferencesForm);
			setPrefCourseMapToRequest(request, preferencesForm);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.preference.exists", preferencesForm.getCourseName()));
			saveErrors(request, errors);
			setPreferencesToRequest(request, preferencesForm, false);
			setprogramMapToRequest(request, preferencesForm);
			setCourseMapToRequest(request, preferencesForm);
			setPrefprogramMapToRequest(request, preferencesForm);
			setPrefCourseMapToRequest(request, preferencesForm);
			return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
		} catch (Exception e) {
			log.error("error in final submit of preference page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				preferencesForm.setErrorMessage(msg);
				preferencesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.preferences.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
//			preferencesForm.reset(mapping, request);
			resetFields(preferencesForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.preferences.addfailure"));
			saveErrors(request, errors);
		}

		return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response loading preferences based on the id
	 * @return forward to seconds page
	 * @throws Exception
	 */

	public ActionForward editPreferences(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {

		PreferencesForm preferencesForm = (PreferencesForm) form;
		setPreferencesToRequest(request, preferencesForm, false);
		setProgramtypelist(request);
		setprogramMapToRequest(request, preferencesForm);
		setCourseMapToRequest(request, preferencesForm);
		setPrefprogramMapToRequest(request, preferencesForm);
		setPrefCourseMapToRequest(request, preferencesForm);
		request.setAttribute("prefOperation", "edit");
		request.setAttribute("prefOperationfirst", "editfirstpage");
		return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response load items in second page in edit
	 * @return forward to second page
	 * @throws Exception
	 */

	public ActionForward editPreferencesForCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {

		PreferencesForm preferencesForm = (PreferencesForm) form;
		setPreferencesToRequestForUpdate(request, preferencesForm, false);
		setProgramtypelist(request);
		setprogramMapToRequest(request, preferencesForm);
		setCourseMapToRequest(request, preferencesForm);
		setPrefprogramMapToRequest(request, preferencesForm);
		setPrefCourseMapToRequest(request, preferencesForm);

		request.setAttribute("prefOperation", "edit");
		request.setAttribute("prefOperationfirst", "editsecondpage");
		return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response update preferences
	 * @return forward to first page
	 * @throws Exception
	 */
	public ActionForward updatePreferencesOnCourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {

		PreferencesForm preferencesForm = (PreferencesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
//		errors = preferencesForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if(preferencesForm.getProgramTypeId() == null || preferencesForm.getProgramTypeId().trim().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.admission.programtype.required"));
			}
			if(preferencesForm.getProgramId() == null || preferencesForm.getProgramId().trim().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.admission.program.required"));
			}
			if(preferencesForm.getCourseId() == null || preferencesForm.getCourseId().trim().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.admission.course.required"));
			}
			if(preferencesForm.getPrefProgramId() == null || preferencesForm.getPrefProgramId().trim().isEmpty() ||
					preferencesForm.getPrefCourseId() == null || preferencesForm.getPrefCourseId().trim().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.admission.pre.click.edit.button"));
			}			
				
			if (isCancelled(request)) {
				setRequiredDataToForm(preferencesForm, request);
				setPreferencesToRequestForUpdate(request, preferencesForm, false);
				setprogramMapToRequest(request, preferencesForm);
				setCourseMapToRequest(request, preferencesForm);
				setPrefprogramMapToRequest(request, preferencesForm);
				setPrefCourseMapToRequest(request, preferencesForm);
				setProgramtypelist(request);
				request.setAttribute("prefOperation", "edit");
				return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setPreferencesToRequestForUpdate(request, preferencesForm, false);
				setprogramMapToRequest(request, preferencesForm);
				setCourseMapToRequest(request, preferencesForm);
				setPrefprogramMapToRequest(request, preferencesForm);
				setPrefCourseMapToRequest(request, preferencesForm);
				setProgramtypelist(request);
//				request.setAttribute("prefOperationfirst", "editfirstpage");
				request.setAttribute("prefOperation", "edit");
				return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
			}

			if (preferencesForm.getCourseId().equals(preferencesForm.getPrefCourseId())) {
				errors.add("error", new ActionError("knowledgepro.admin.preference.valid.course"));
				saveErrors(request, errors);
				setPreferencesToRequestForUpdate(request, preferencesForm, false);
				setprogramMapToRequest(request, preferencesForm);
				setCourseMapToRequest(request, preferencesForm);
				setPrefprogramMapToRequest(request, preferencesForm);
				setPrefCourseMapToRequest(request, preferencesForm);
				setProgramtypelist(request);
				request.setAttribute("prefOperation", "edit");
				return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
			}
			isUpdated = PreferencesHandler.getInstance().addPreferences(preferencesForm, "Edit");

			setPreferencesToRequestForUpdate(request, preferencesForm, false);
			setProgramtypelist(request);
			setprogramMapToRequest(request, preferencesForm);
			setCourseMapToRequest(request, preferencesForm);
			setPrefprogramMapToRequest(request, preferencesForm);
			setPrefCourseMapToRequest(request, preferencesForm);

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.preference.update.exists"));
			saveErrors(request, errors);
			setPreferencesToRequest(request, preferencesForm, false);
			setprogramMapToRequest(request, preferencesForm);
			setCourseMapToRequest(request, preferencesForm);
			setPrefprogramMapToRequest(request, preferencesForm);
			setPrefCourseMapToRequest(request, preferencesForm);
			setProgramtypelist(request);
			request.setAttribute("prefOperation", "edit");
			return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
		} catch (Exception e) {
			log.error("error in final submit of preference page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				preferencesForm.setErrorMessage(msg);
				preferencesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.preferences.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
//			preferencesForm.reset(mapping, request);
			resetFields(preferencesForm);
			preferencesForm.firstFromreset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.preferences.updatefailure"));
			saveErrors(request, errors);
		}
		setProgramtypelist(request);
		setPreferencesToRequest(request, preferencesForm, true);
		request.setAttribute("prefOperation", "add");
		return mapping.findForward(CMSConstants.INIT_PREFERENCES);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing Preference for the selected
	 *            course
	 * @return ActionForward This action method will called when particular
	 *         preference need to be deleted based on the id
	 * @throws Exception
	 */
	public ActionForward deletePreferenceForCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside delete Preference For Course Action");
		PreferencesForm preferencesForm = (PreferencesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (preferencesForm.getPrefId() != null) {
				int prefId = Integer.parseInt(preferencesForm.getPrefId());
				isDeleted = PreferencesHandler.getInstance().deletePreferences(prefId, false, preferencesForm);
			}
		} catch (Exception e) {
			log.error("error in deletePreferenceForCourse...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				preferencesForm.setErrorMessage(msg);
				preferencesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setPreferencesToRequest(request, preferencesForm, false);
		setProgramtypelist(request);
		setprogramMapToRequest(request, preferencesForm);
		setCourseMapToRequest(request, preferencesForm);
		setPrefprogramMapToRequest(request, preferencesForm);
		setPrefCourseMapToRequest(request, preferencesForm);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.preferences.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			resetFields(preferencesForm);
//			preferencesForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.prefernces.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("inside Delete Preferences Action");
		return mapping.findForward(CMSConstants.SHOW_PREFERENCES_ON_COURSE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing Preference for the selected
	 *            course
	 * @return ActionForward This action method will called when particular
	 *         preference need to be deleted based on the id
	 * @throws Exception
	 */
	public ActionForward deleteAllPreferenceForACourse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
														HttpServletResponse response) throws Exception {

		log.debug("inside delete All Preference For Course Action");
		PreferencesForm preferencesForm = (PreferencesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (preferencesForm.getCourseId() != null) {
				int courseId = Integer.parseInt(preferencesForm.getCourseId());
				isDeleted = PreferencesHandler.getInstance().deleteAllPreferences(courseId, preferencesForm);
			}
		} catch (Exception e) {
			log.error("error in delete Prefernces page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				preferencesForm.setErrorMessage(msg);
				preferencesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setPreferencesToRequest(request, preferencesForm, true);
		setProgramtypelist(request);
		setprogramMapToRequest(request, preferencesForm);
		setCourseMapToRequest(request, preferencesForm);
		setPrefprogramMapToRequest(request, preferencesForm);
		setPrefCourseMapToRequest(request, preferencesForm);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.preferences.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
//			preferencesForm.reset(mapping, request);
			resetFields(preferencesForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.prefernces.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("inside Delete Preferences Action");
		return mapping.findForward(CMSConstants.INIT_PREFERENCES);
	}
	/**
	 * loading in program type combo.
	 * @param request
	 * @throws Exception
	 */
	public void setProgramtypelist(HttpServletRequest request) throws Exception {
		log.debug("inside setProgramtypelist");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();

		if (!programTypeList.isEmpty()) {
			request.setAttribute("programTypeList", programTypeList);

		} else {
			log.error("No records found :: List is empty");
		}
		log.debug("leaving setProgramtypelist");
	}

	/**
	 * 
	 * @param request
	 * @param preferencesForm
	 */
	public void setprogramMapToRequest(HttpServletRequest request, PreferencesForm preferencesForm) {
		if (preferencesForm.getProgramTypeId() != null && (!preferencesForm.getProgramTypeId().isEmpty())) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(preferencesForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
		log.debug("leaving setprogramMapToRequest");
	}

	/**
	 * 
	 * @param request
	 * @param preferencesForm
	 * @throws Exception
	 */
	public void setPreferenceListToForm(HttpServletRequest request,	PreferencesForm preferencesForm) throws Exception {
		List<PreferencesTO> assignedPrefList = preferencesForm.getPrefList();
		if (assignedPrefList == null) {
			assignedPrefList = new ArrayList<PreferencesTO>();
		}
		PreferencesTO preferencesTO = PreferencesHelper.getInstance().assignListFromForm(preferencesForm);
		assignedPrefList.add(preferencesTO);
		preferencesForm.setPrefList(assignedPrefList);
	}
	/**
	 * 
	 * @param request
	 * @param preferencesForm
	 * @param isFirstPage
	 * @throws Exception
	 */
	public void setPreferencesToRequest(HttpServletRequest request, PreferencesForm preferencesForm, Boolean isFirstPage)
										throws Exception {
		List<PreferencesTO> preferencesList = PreferencesHandler.getInstance().getPreferences(preferencesForm, isFirstPage);
		request.setAttribute("preferencesList", preferencesList);
	}

	/**
	 * 
	 * @return This method sets course map to request for setting in edit option
	 * @throws Exception
	 */

	public void setCourseMapToRequest(HttpServletRequest request, PreferencesForm preferencesForm) {
		if (preferencesForm.getProgramId() != null	&& (!preferencesForm.getProgramId().isEmpty())) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(preferencesForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
	}

	/**
	 * 
	 * @return This method sets course map to request for setting in edit option
	 * @throws Exception
	 */

	public void setPrefCourseMapToRequest(HttpServletRequest request,PreferencesForm preferencesForm) {
		if (preferencesForm.getPrefProgramId() != null	&& (!preferencesForm.getPrefProgramId().isEmpty())) {
			Map<Integer, String> prefCourseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(preferencesForm
											.getPrefProgramId()));
			request.setAttribute("prefCourseMap", prefCourseMap);
		}
	}
	/**
	 * method used for populating in combo
	 * @param request
	 * @param preferencesForm
	 */
	public void setPrefprogramMapToRequest(HttpServletRequest request,	PreferencesForm preferencesForm) {
		if (preferencesForm.getProgramTypeId() != null	&& (!preferencesForm.getProgramTypeId().isEmpty())) {
			Map<Integer, String> PrefProgramMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(preferencesForm.getProgramTypeId()));
			request.setAttribute("PrefProgramMap", PrefProgramMap);
		}
	}

	/**
	 * setting data to form for edit
	 * @param preferencesForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(PreferencesForm preferencesForm, HttpServletRequest request) throws Exception {
		int prefId = Integer.parseInt(request.getParameter("prefId"));
		List<PreferencesTO> prefList = PreferencesHandler.getInstance().getPreferenceswithIdForForm(prefId);
		Iterator<PreferencesTO> prefIt = prefList.iterator();
		PreferencesTO preferencesTO;

		while (prefIt.hasNext()) {
			preferencesTO = (PreferencesTO) prefIt.next();
			int progTypeId = preferencesTO.getCourseTO().getProgramTo().getProgramTypeTo().getProgramTypeId();
			preferencesForm.setProgramTypeId(Integer.toString(progTypeId));
			preferencesForm.setProgramId(Integer.toString(preferencesTO.getCourseTO().getProgramTo().getId()));
			preferencesForm.setCourseId(Integer.toString(preferencesTO.getCourseTO().getId()));
			if ((preferencesForm.getPrefCourseId() != null)	&& (!preferencesForm.getPrefCourseId().isEmpty())) {
				preferencesForm.setPrefCourseId(Integer.toString(preferencesTO.getPrefCourseTO().getId()));
			}
			if ((preferencesForm.getPrefProgramId() != null)&& (!preferencesForm.getPrefProgramId().isEmpty())) {
				preferencesForm.setPrefProgramId(Integer.toString(preferencesTO.getPrefCourseTO().getProgramTo().getId()));
			}
		}
		setPreferencesToRequest(request, preferencesForm, false);
		setProgramtypelist(request);
		setprogramMapToRequest(request, preferencesForm);
		setCourseMapToRequest(request, preferencesForm);
		setPrefprogramMapToRequest(request, preferencesForm);
		setPrefCourseMapToRequest(request, preferencesForm);
		request.setAttribute("conditionsOperation", "edit");
	}

	public void resetFields(PreferencesForm preferencesForm) {
		preferencesForm.setPrefCourseId(null);
		preferencesForm.setPrefProgName(null);
		preferencesForm.setPrefCourseName(null);
		preferencesForm.setPrefProgramId(null);
		
	}

	/**
	 * 
	 * @param request
	 * @param preferencesForm
	 * @param isFirstPage
	 * @throws Exception
	 */
	public void setPreferencesToRequestForUpdate(HttpServletRequest request, PreferencesForm preferencesForm, Boolean isFirstPage)
										throws Exception {
		List<PreferencesTO> preferencesList = PreferencesHandler.getInstance().getPreferencesForUpdate(preferencesForm, isFirstPage);
		request.setAttribute("preferencesList", preferencesList);
	}
	
}
