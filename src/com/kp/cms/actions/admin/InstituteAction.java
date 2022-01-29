package com.kp.cms.actions.admin;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.InstituteForm;
import com.kp.cms.handlers.admin.InstituteHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.UniversityTO;

@SuppressWarnings("deprecation")
public class InstituteAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(InstituteAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set instituteList having collegeTo objects to request, forward to
	 *         instituteEntry
	 * @throws Exception
	 */
	public ActionForward initInstitute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InstituteForm instituteForm = (InstituteForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setUniversityListToRequest(request);  //setting university list to request for populating in university selection
			setInstituteListToRequest(request);   //setting instituteList to request for display in the UI
		} catch (Exception e) {
			log.error("error init institute page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				instituteForm.setErrorMessage(msg);
				instituteForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Institute
	 * @return to mapping instituteEntry
	 * @throws Exception
	 */
	public ActionForward addInstitute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		InstituteForm instituteForm = (InstituteForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = instituteForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setUniversityListToRequest(request);
				setInstituteListToRequest(request);
				//no need to add blank space to table
				if(instituteForm.getName().trim().isEmpty()){
					instituteForm.setName(null);
				}
				return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
			}
			setUserId(request, instituteForm);   // setting to update lastchanged details
			isAdded = InstituteHandler.getInstance().addInstitute(instituteForm);
			setUniversityListToRequest(request);
			setInstituteListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.institute.name.exists", instituteForm.getName()));
			saveErrors(request, errors);
			setUniversityListToRequest(request);
			setInstituteListToRequest(request);
			return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.INSTITUTE_ENTRY_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setUniversityListToRequest(request);
			setInstituteListToRequest(request);
			return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of institute page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				instituteForm.setErrorMessage(msg);
				instituteForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.institute.addsuccess", instituteForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			instituteForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.institute.addfailure", instituteForm
							.getName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward This action method will called when particular
	 *         Institute name need to be deleted based on the id.
	 * @throws Exception
	 */
	
	public ActionForward deleteInstitute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		InstituteForm instituteForm = (InstituteForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (instituteForm.getId() != null) {
				int instId = Integer.parseInt(instituteForm.getId());
				setUserId(request, instituteForm);
				isDeleted = InstituteHandler.getInstance().deleteInstitute(instId, false, instituteForm.getUserId()); 
			}
			setUniversityListToRequest(request);
			setInstituteListToRequest(request);
		} catch (Exception e) {
			log.error("error in delete Institute page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				instituteForm.setErrorMessage(msg);
				instituteForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.institute.deletesuccess", instituteForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			instituteForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.institute.deletefailure", instituteForm.getName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This action method will called when particular Institute need to be
	 *         updated based on the id.
	 * @throws Exception
	 */
	public ActionForward updateInstitute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		InstituteForm instituteForm = (InstituteForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = instituteForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setUniversityListToRequest(request);
				setInstituteListToRequest(request);
				//no need to add blank space to the table
				if(instituteForm.getName().trim().isEmpty()){
					instituteForm.setName(null);
				}
				request.setAttribute("instituteOperation", "edit");
				return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
			}
			if (instituteForm.getId() != null) {
				setUserId(request, instituteForm);
				isUpdated = InstituteHandler.getInstance().updateInstitute(instituteForm); 
			}
			setUniversityListToRequest(request);
			setInstituteListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.institute.name.exists"));
			saveErrors(request, errors);
			setUniversityListToRequest(request);
			setInstituteListToRequest(request);
			request.setAttribute("instituteOperation", "edit");
			return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.STATE_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setUniversityListToRequest(request);
			setInstituteListToRequest(request);
			request.setAttribute("instituteOperation", "edit");
			return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
		} catch (Exception e) {
			log.error("error in update state page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("instituteOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				instituteForm.setErrorMessage(msg);
				instituteForm.setErrorStack(e.getMessage());
				request.setAttribute("instituteOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		if (isUpdated) {
			// successfully deleted.
			ActionMessage message = new ActionMessage("knowledgepro.admin.institute.updatesuccess",	instituteForm.getName());
			messages.add("messages", message);
			instituteForm.reset(mapping, request);
			saveMessages(request, messages);
			instituteForm.reset(mapping, request);
		} else {
			// failed to update.
			errors.add("error", new ActionError("knowledgepro.admin.institute.updatefailure", instituteForm.getName()));
			saveErrors(request, errors);
			request.setAttribute("instituteOperation", "edit");
			return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
		}
		request.setAttribute("instituteOperation", "add");
		return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
	}

	/**
	 * 
	 * @param request
	 *            This method sets the  university list to Request useful in
	 *            populating in university selection.
	 */
	public void setUniversityListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setUniversityListToRequest");
		List<UniversityTO> universityList = UniversityHandler.getInstance().getUniversity(); 
		request.setAttribute("universityList", universityList);
	}

	/**
	 * 
	 * @param request
	 *            This method sets the instituteList to Request used to display
	 *            Institute record on UI.
	 * @throws Exception
	 */
	public void setInstituteListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setInstituteListToRequest");
		List<CollegeTO> instituteList = InstituteHandler.getInstance().getInstitute();
		request.setAttribute("instituteList", instituteList);
	}
	
	/**
	 * use to activate institute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateInstitute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
											HttpServletResponse response) throws Exception {

		log.debug("inside activateInstitute");
		InstituteForm instituteForm = (InstituteForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (instituteForm.getDuplInstId() != 0) {
				int instId = instituteForm.getDuplInstId();
				setUserId(request, instituteForm);
				isActivated = InstituteHandler.getInstance().deleteInstitute(instId, true, instituteForm.getUserId()); 
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.INSTITUTE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setUniversityListToRequest(request);
		setInstituteListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.INSTITUTE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.INSTITUTE_ENTRY);
	}

}
