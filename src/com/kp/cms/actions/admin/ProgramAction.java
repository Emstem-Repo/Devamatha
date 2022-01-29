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
import com.kp.cms.forms.admin.ProgramForm;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;


@SuppressWarnings("deprecation")
public class ProgramAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ProgramAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set programList, ProgramTypeList to display in the UI
	 * @throws Exception
	 */
	public ActionForward initProgram(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering initProgram : Action");
		ProgramForm programForm = (ProgramForm) form;
		try {
			String formName = mapping.getName();
			programForm.setYear(null);
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setProgramListToRequest(request);  //setting program type list to request for UI display
			setProgramTypeListToRequest(request);  //setting programType list to request for populating in combo
		} catch (Exception e) {
			log.error("error submit program page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programForm.setErrorMessage(msg);
				programForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initProgram: Action");

		return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response  This method will add a new Program
	 * @return forward to program entry
	 * @throws Exception
	 */
	public ActionForward addprogram(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addprogram :Action");
		ProgramForm programForm = (ProgramForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = programForm.validate(mapping, request);
		boolean isAdded = false;
		request.getParameterValues("mytext");
		try {
			if(programForm.getYear() == null || programForm.getYear().trim().isEmpty()){
				errors.add("error",  new ActionError("knowledgepro.admission.year.required"));
			}
			if (!errors.isEmpty()) {
				setProgramListToRequest(request);
				setProgramTypeListToRequest(request);
				saveErrors(request, errors);
				//space should not allow in table. so if space entered then assigning null
				if(programForm.getProgramCode().trim().isEmpty()){
					programForm.setProgramCode(null);
				}
				if(programForm.getName() != null && programForm.getName().trim().isEmpty()){
					programForm.setName(null);
				}
				return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
			}
			setUserId(request, programForm);  //setting userId for updating last changed details
			isAdded = ProgramHandler.getInstance().addprogram(programForm, "Add");  //Add parameter is using to identify add/edit
			setProgramListToRequest(request);
			setProgramTypeListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.program.code.name.exists", programForm.getProgramCode(), 
												programForm.getProgramName()));
			saveErrors(request, errors);
			setProgramListToRequest(request);
			setProgramTypeListToRequest(request);
			return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.PROGRAM_ENTRY_REACTIVATE));
			saveErrors(request, errors);
			setProgramListToRequest(request);
			setProgramTypeListToRequest(request);
			return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programForm.setErrorMessage(msg);
				programForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.program.addsuccess", programForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			programForm.reset(mapping, request);
			programForm.setYear(null);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.admin.program.addfailure", programForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addprogram Action");
		return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
	}

	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set programList, ProgramTypeList
	 * @throws Exception
	 */
	public ActionForward initEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering initProgram ");
		ProgramForm programForm = (ProgramForm) form;
		try {
			setProgramListToRequest(request);
			setProgramTypeListToRequest(request);
			ProgramHandler.getInstance().getProgramDetails(programForm);
			request.setAttribute("programOperation", "edit");
		} catch (Exception e) {
			log.error("error submit program page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programForm.setErrorMessage(msg);
				programForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initProgram ");

		return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set programList, ProgramTypeList
	 * @throws Exception
	 */
	public ActionForward initView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering initView ");
		ProgramForm programForm = (ProgramForm) form;
		try {
			ProgramHandler.getInstance().getProgramDetailsForView(programForm);
		} catch (Exception e) {
			log.error("error submit program page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programForm.setErrorMessage(msg);
				programForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initView ");
		return mapping.findForward(CMSConstants.VIEW_PROGRAM);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will edit the existing Program
	 * @return to mapping Program entry
	 * @throws Exception
	 */
	public ActionForward editProgram(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside editProgram Action");
		ProgramForm programForm = (ProgramForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = programForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if(programForm.getYear() == null || programForm.getYear().trim().isEmpty()){
				errors.add("error",  new ActionError("knowledgepro.admission.year.required"));
			}
			if (!errors.isEmpty()) {
				setProgramListToRequest(request);
				setProgramTypeListToRequest(request);
				saveErrors(request, errors);
				if(programForm.getProgramCode().trim().isEmpty()){
					programForm.setProgramCode(null);
				}
				if(programForm.getName() != null && programForm.getName().trim().isEmpty()){
					programForm.setName(null);
				}
				request.setAttribute("programOperation", "edit");
				return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
			}
			setUserId(request, programForm);
			isAdded = ProgramHandler.getInstance().addprogram(programForm, "Edit");
			setProgramListToRequest(request);
			setProgramTypeListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.program.code.name.exists", programForm.getProgramCode(), 
									programForm.getProgramName()));
			saveErrors(request, errors);
			setProgramListToRequest(request);
			setProgramTypeListToRequest(request);
			request.setAttribute("programOperation", "edit");
			return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					CMSConstants.PROGRAM_ENTRY_REACTIVATE));
			saveErrors(request, errors);
			setProgramListToRequest(request);
			setProgramTypeListToRequest(request);
			request.setAttribute("programOperation", "edit");
			return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programForm.setErrorMessage(msg);
				programForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.program.updatesuccess", programForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			programForm.reset(mapping, request);
			programForm.setYear(null);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.program.updatefailure", programForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving editProgram Action");
		return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing program
	 * @return ActionForward This action method will called when particular
	 *         Program need to be deleted based on the program id.
	 * @throws Exception
	 */
	public ActionForward deleteProgram(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		log.debug("inside Delete program Action");
		ProgramForm programForm = (ProgramForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (programForm.getProgramId() != null) {
				int progId = Integer.parseInt(programForm.getProgramId());
				setUserId(request, programForm);  //setting user id to update last changed information
				isDeleted = ProgramHandler.getInstance().deleteProgram(progId, false, programForm);
			}
		} catch (Exception e) {
			log.error("error in delete of program page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programForm.setErrorMessage(msg);
				programForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		setProgramListToRequest(request);
		setProgramTypeListToRequest(request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.program.deletesuccess", programForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			programForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError(
					"knowledgepro.admin.program.deletefailure", programForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("inside Delete program Action");
		return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
	}

	/**
	 * this method sets program List to request to show in the UI
	 * @param request
	 * @throws Exception
	 */
	
	public void setProgramListToRequest(HttpServletRequest request)	throws Exception {
		log.debug("inside setProgramListToRequest");
		List<ProgramTO> programList = ProgramHandler.getInstance().getProgram();
		request.setAttribute("programList", programList);
	}

	/**
	 * this method sets program Type List to request to show in the UI 
	 * @param request
	 * @throws Exception
	 */
	
	public void setProgramTypeListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
	}

	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this method will activate the program
	 * @return ActionForward This action method will called when particular
	 *         Program need to be recovered based on the program.
	 * @throws Exception
	 */
	public ActionForward activateProgram(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {

		log.debug("Entering ActivateProgram");
		ProgramForm programForm = (ProgramForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (programForm.getDuplPgmId() != 0) {
				int duplPgmId = programForm.getDuplPgmId();
				setUserId(request, programForm); //setting user id to update last changed details
				isActivated = ProgramHandler.getInstance().deleteProgram(duplPgmId, true, programForm); 
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.PROGRAM_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setProgramListToRequest(request);
		setProgramTypeListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.PROGRAM_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving ActivateProgram");
		request.setAttribute("programOperation", "add");
		return mapping.findForward(CMSConstants.PROGRAM_ENTRY);
	}

	
}
