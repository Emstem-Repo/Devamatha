
package com.kp.cms.actions.reportusermanagement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.reportusermanagement.ReportModuleForm;
import com.kp.cms.handlers.reportusermanagement.ReportModuleHandler;
import com.kp.cms.handlers.usermanagement.ModuleHandler;
import com.kp.cms.to.usermanagement.ModuleTO;
/**
 * 
 * @author
 *
 */
@SuppressWarnings("deprecation")
public class ReportModuleAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ReportModuleAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set moduleList having moduleTo objects to request, forward to
	 *         moduleEntry
	 * @throws Exception
	 */
	public ActionForward initModule(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("Entering initModule");
		ReportModuleForm moduleForm = (ReportModuleForm) form;
		try {
			final String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setModuleListToRequest(request);
		} catch (Exception e) {
			log.error("error submit module page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				moduleForm.setErrorMessage(msg);
				moduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initModule ");

		return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new Module
	 * @return to mapping Module entry
	 * @throws Exception
	 */
	public ActionForward addModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside addModule Action");
		ReportModuleForm moduleForm = (ReportModuleForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = moduleForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if(moduleForm.getName() == null || moduleForm.getName().isEmpty() ){
				errors.add("error", new ActionError("knowledgepro.report.management.module.name"));
			}
			if(moduleForm.getPosition() == null || moduleForm.getPosition().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.report.management.module.position"));
			}
			if (errors != null && !errors.isEmpty()) {
				setModuleListToRequest(request);
				saveErrors(request, errors);
				if((moduleForm.getName().trim()).isEmpty()){
					moduleForm.setName(null);
				}
				return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
			}
			setUserId(request, moduleForm);
			isAdded = ReportModuleHandler.getInstance().addModule(moduleForm, "Add");
			setModuleListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.usermanagement.module.name.exists", moduleForm.getName()));
			saveErrors(request, errors);
			setModuleListToRequest(request);
			return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.MODULE_ENTRY_REACTIVATE));
			saveErrors(request, errors);
			setModuleListToRequest(request);
			return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of module page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				moduleForm.setErrorMessage(msg);
				moduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.usermanagement.module.addsuccess", moduleForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			moduleForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.usermanagement.module.addfailure", moduleForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addModule Action");
		return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will edit the existing Module
	 * @return to mapping Module entry
	 * @throws Exception
	 */
	public ActionForward editModules(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {

		log.debug("inside editModules Action");
		ReportModuleForm moduleForm = (ReportModuleForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = moduleForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if(moduleForm.getName() == null || moduleForm.getName().isEmpty() ){
				errors.add("error", new ActionError("knowledgepro.report.management.module.name"));
			}
			if(moduleForm.getPosition() == null || moduleForm.getPosition().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.report.management.module.position"));
			}
			if (errors != null && !errors.isEmpty()) {
				setModuleListToRequest(request);
				saveErrors(request, errors);
				if(moduleForm.getName().trim().isEmpty()){
					moduleForm.setName(null);
				}
				request.setAttribute("moduleOperation", "edit");
				return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
			}
			setUserId(request, moduleForm);
			isAdded = ReportModuleHandler.getInstance().addModule(moduleForm,	"Edit");
			setModuleListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.usermanagement.module.name.exists", moduleForm.getName()));
			saveErrors(request, errors);
			setModuleListToRequest(request);
			request.setAttribute("moduleOperation", "edit");
			return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.MODULE_ENTRY_REACTIVATE));
			saveErrors(request, errors);
			setModuleListToRequest(request);
			request.setAttribute("moduleOperation", "edit");
			return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of module page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				moduleForm.setErrorMessage(msg);
				moduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.usermanagement.module.updatesuccess", moduleForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			moduleForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.usermanagement.module.updatefailure", moduleForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving editModules Action");
		return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing module
	 * @return ActionForward This action method will called when particular
	 *         module need to be deleted based on the module id.
	 * @throws Exception
	 */
	public ActionForward deleteModule(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
										HttpServletResponse response) throws Exception {

		log.debug("inside Delete module Action");
		ReportModuleForm moduleForm = (ReportModuleForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (moduleForm.getId() != null) {
				int modId = Integer.parseInt(moduleForm.getId());
				setUserId(request, moduleForm);
				isDeleted = ReportModuleHandler.getInstance().deleteModule(modId, moduleForm,false) ;
			}
		} catch (Exception e) {
			log.error("error in delete of module page...", e);
    		errors.add("error", new ActionError(CMSConstants.MODULE_DELETE_FAILURE, moduleForm.getName()));
    		saveErrors(request,errors);
		}

		setModuleListToRequest(request);

		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.usermanagement.module.deletesuccess", moduleForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			moduleForm.reset(mapping, request);
		}
		log.debug("inside Delete module Action");
		return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will activate the deactivated Module
	 * @return ActionForward This action method will called when particular
	 *         Module need to be recovered based on the module.
	 * @throws Exception
	 */
	public ActionForward ActivateModule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering ActivateModule");
		ReportModuleForm moduleForm = (ReportModuleForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (moduleForm.getDuplId() != 0) {
				int duplId = moduleForm.getDuplId();
				setUserId(request, moduleForm);
				isActivated = ReportModuleHandler.getInstance().ReactivateModule(duplId, moduleForm, true);
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(
					CMSConstants.MODULE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
			setModuleListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(
					CMSConstants.MODULE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			setModuleListToRequest(request);
		}
		log.debug("leaving ActivateModule");
		request.setAttribute("moduleOperation", "add");
		return mapping.findForward(CMSConstants.REPORT_MODULE_ENTRY);
	}

	

	/**
	 * 
	 * @param request
	 *            This method for showing all the Modules in the UI.
	 * @throws Exception
	 */
	public void setModuleListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setModuleListToRequest");
		List<ModuleTO> moduleList = ReportModuleHandler.getInstance().getModule();
		request.setAttribute("moduleList", moduleList);
	}

}

