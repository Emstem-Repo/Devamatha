package com.kp.cms.actions.hostel;



import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.forms.hostel.DisciplinaryTypeForm;
import com.kp.cms.forms.hostel.RoomTypeForm;
import com.kp.cms.handlers.hostel.DisciplinaryTypeHandler;
import com.kp.cms.to.hostel.DisciplinaryTypeTO;

@SuppressWarnings("deprecation")
public class DisciplinaryTypeAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(DisciplinaryTypeAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set gradesList having disciplinaryTo objects to request, forward to
	 *         DISCIPLINARY_TYPE_ENTRY
	 * @throws Exception
	 */
	public ActionForward initDisciplinaryType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("Entering setDisciplinaryTypeListToRequest ");
		DisciplinaryTypeForm disForm = (DisciplinaryTypeForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setDisciplinaryTypeListToRequest(request);
			setUserId(request, disForm);
		} catch (Exception e) {
			log.error("error initDisciplinaryType...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				disForm.setErrorMessage(msg);
				disForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initDisciplinaryType ");

		return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Disciplinary record
	 * @return to mapping DISCIPLINARY_TYPE_ENTRY
	 * @throws Exception
	 */
	public ActionForward addDisciplinaryType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addDisciplinaryType Action");
		DisciplinaryTypeForm disciplinaryTypeForm = (DisciplinaryTypeForm) form;
		disciplinaryTypeForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = disciplinaryTypeForm.validate(mapping, request);
		validateFormSpecialCharacter(disciplinaryTypeForm, errors, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setDisciplinaryTypeListToRequest(request);
				//space should not get added in the table
				if(disciplinaryTypeForm.getName().trim().isEmpty()){
					disciplinaryTypeForm.setName(null);
				}
					
				return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
			}
			isAdded = DisciplinaryTypeHandler.getInstance().addDisciplinaryType(disciplinaryTypeForm, "add");
			setDisciplinaryTypeListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.hostel.discipline.type.exists", disciplinaryTypeForm.getName()));
			saveErrors(request, errors);
			setDisciplinaryTypeListToRequest(request);
			return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.DISCIPLINARY_TYPE_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setDisciplinaryTypeListToRequest(request);
			return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of disciplinary type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				disciplinaryTypeForm.setErrorMessage(msg);
				disciplinaryTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.discipline.type.addsuccess", disciplinaryTypeForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			disciplinaryTypeForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.discipline.type.addfailure", disciplinaryTypeForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addDisciplinaryType Action");
		return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
	}


	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new DisciplinaryType record
	 * @return to mapping DISCIPLINARY_TYPE_ENTRY
	 * @throws Exception
	 */
	public ActionForward updateDisciplinaryType(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addDisciplinaryType Action");
		DisciplinaryTypeForm disciplinaryTypeForm = (DisciplinaryTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = disciplinaryTypeForm.validate(mapping, request);
		validateFormSpecialCharacter(disciplinaryTypeForm, errors, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setDisciplinaryTypeListToRequest(request);
				//space should not get added in the table
				if(disciplinaryTypeForm.getName().trim().isEmpty()){
					disciplinaryTypeForm.setName(null);
				}
				request.setAttribute("disciplineOperation", "edit");
				return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
			}
			isUpdated = DisciplinaryTypeHandler.getInstance().addDisciplinaryType(disciplinaryTypeForm, "edit");
			setDisciplinaryTypeListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.hostel.discipline.type.exists", disciplinaryTypeForm.getName()));
			saveErrors(request, errors);
			setDisciplinaryTypeListToRequest(request);
			request.setAttribute("disciplineOperation", "edit");
			return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.DISCIPLINARY_TYPE_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setDisciplinaryTypeListToRequest(request);
			request.setAttribute("disciplineOperation", "edit");
			return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of disciplinary type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				disciplinaryTypeForm.setErrorMessage(msg);
				disciplinaryTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.discipline.type.updatesuccess", disciplinaryTypeForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			disciplinaryTypeForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.discipline.type.addfailure", disciplinaryTypeForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addDisciplinaryType Action");
		request.setAttribute("disciplineOperation", "add");
		return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
	}



	/**
	 * 
	 * @param request
	 *            This method sets the disList to Request used to display
	 *            disList record on UI.
	 * @throws Exception
	 */
	public void setDisciplinaryTypeListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setDisciplinaryTypeListToRequest");
		List<DisciplinaryTypeTO> disList = DisciplinaryTypeHandler.getInstance().getDisplinaryTypes();
		request.setAttribute("disList", disList);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the Disciplinary type
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteDisciplinaryType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteDisciplinaryType");
		DisciplinaryTypeForm disciplinaryTypeForm = (DisciplinaryTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (disciplinaryTypeForm.getId() != 0) {
				int disId = disciplinaryTypeForm.getId();
				isDeleted = DisciplinaryTypeHandler.getInstance().deleteDisciplinaryType(disId, false, disciplinaryTypeForm);
			}
		} catch (Exception e) {
			log.error("error in deleteDisciplinaryType...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				disciplinaryTypeForm.setErrorMessage(msg);
				disciplinaryTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setDisciplinaryTypeListToRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.hostel.discipline.deletesuccess", disciplinaryTypeForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			disciplinaryTypeForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.hostel.discipline.deletefailure", disciplinaryTypeForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteDisciplinaryType");
		return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the Disciplinary type
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateDisciplinaryType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateDisciplinaryType");
		DisciplinaryTypeForm disciplinaryTypeForm = (DisciplinaryTypeForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (disciplinaryTypeForm.getDuplId() != 0) {
				int id = disciplinaryTypeForm.getDuplId();  //setting id for activate
				isActivated = DisciplinaryTypeHandler.getInstance().deleteDisciplinaryType(id, true, disciplinaryTypeForm); 
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.DISCIPLINARY_TYPE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setDisciplinaryTypeListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.DISCIPLINARY_TYPE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateDisciplinaryType");
		return mapping.findForward(CMSConstants.DISCIPLINARY_TYPE_ENTRY);
	}
	
	/**
	 * special character validation
	 * 
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name) {
		boolean result = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \\. \\s \t \\/ \\( \\) ]+");

		Matcher matcher = pattern.matcher(name);
		result = matcher.find();
		return result;

	}
	
	private void validateFormSpecialCharacter(DisciplinaryTypeForm disciplinaryTypeForm, ActionErrors errors, HttpServletRequest request)throws Exception
	{
		if(disciplinaryTypeForm.getName()!= null && !disciplinaryTypeForm.getName().isEmpty() && nameValidate(disciplinaryTypeForm.getName()))
		{
			errors.add("error", new ActionError("knowledgepro.hostel.vistorinfo.specialCharacter","Disciplinary Type"));
		}
	}
	

}
