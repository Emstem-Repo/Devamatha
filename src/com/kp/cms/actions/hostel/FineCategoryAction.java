package com.kp.cms.actions.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.forms.hostel.FineCategoryForm;
import com.kp.cms.handlers.hostel.FineCategoryHandler;
import com.kp.cms.to.hostel.FineCategoryTo;

public class FineCategoryAction extends BaseDispatchAction{
	private static final Log log = LogFactory
	.getLog(FineCategoryAction.class);
	/**
	 * init method
	 */
	public ActionForward initFineCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FineCategoryForm fineCategoryForm=(FineCategoryForm)form;
		fineCategoryForm.reset();
		setFineCategoryListToForm(fineCategoryForm);
		return mapping.findForward("fineCategory");
	}
	/**
	 * set required data to from
	 */
	public void setFineCategoryListToForm(FineCategoryForm fineCategoryForm)throws Exception{
		List<FineCategoryTo> list=FineCategoryHandler.getInstance().getFineCategoryList(fineCategoryForm);
		fineCategoryForm.setFineCategoryTosList(list);
	}
	/**
	 * add fine Category
	 */
	public ActionForward addFineCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering initSingleFieldMaster");
		FineCategoryForm fineCategoryForm=(FineCategoryForm)form;
		 ActionErrors errors = fineCategoryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		boolean isAdded = false;
		try {
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				saveErrors(request, errors);
				setFineCategoryListToForm(fineCategoryForm);
				return mapping.findForward("fineCategory");
				
			}
			setUserId(request, fineCategoryForm); // setting user is for
			// updating last changed
			// details
			isAdded = FineCategoryHandler.getInstance().addFineCategory(fineCategoryForm, "Add");
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.FineCategory.name.exists"));
			saveErrors(request, errors);
			setFineCategoryListToForm(fineCategoryForm);
				return mapping.findForward("fineCategory");
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.FineCategory.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setFineCategoryListToForm(fineCategoryForm);
			return mapping.findForward("fineCategory");
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				fineCategoryForm.setErrorMessage(msg);
				fineCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.FineCategory.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			fineCategoryForm.setMainPage(null);
			fineCategoryForm.setSuperMainPage(null);
			fineCategoryForm.reset();
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.FineCategory.addfailure"));
			saveErrors(request, errors);
		}
		setFineCategoryListToForm(fineCategoryForm);
		return mapping.findForward("fineCategory");
	}
	/**
	 * delete Fine Category
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFineCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FineCategoryForm fineCategoryForm=(FineCategoryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (fineCategoryForm.getId() != 0) {
				int masterId = fineCategoryForm.getId();
				setUserId(request, fineCategoryForm);
				isDeleted = FineCategoryHandler.getInstance().deleteFineCategory(masterId, false,fineCategoryForm);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				fineCategoryForm.setErrorMessage(msg);
				fineCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.admin.FineCategory.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			fineCategoryForm.reset();
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.FineCategory.deletefail"));
			saveErrors(request, errors);
		}
		setFineCategoryListToForm(fineCategoryForm);
		return mapping.findForward("fineCategory");
	}
	/**
	 * reactivate fine category
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateFineCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering activateAdmittedThrough");
		FineCategoryForm fineCategoryForm=(FineCategoryForm)form;
//		String activateMessage = "knowledgepro.admin." + boName + ".activate";
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (fineCategoryForm.getReactivateid() != 0) {
				int masterId = fineCategoryForm.getReactivateid();
				setUserId(request, fineCategoryForm);
				isActivated = FineCategoryHandler.getInstance().deleteFineCategory(masterId, true,fineCategoryForm);
			}
		} catch (Exception e) {
			errors.add("error", new ActionError("knowledgepro.admin.fineCategory.activatefail"));
			saveErrors(request, errors);
			fineCategoryForm.reset();
		}
		if (isActivated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.fineCategory.activate");
			messages.add("messages", message);
			saveMessages(request, messages);
			fineCategoryForm.reset();
		}
		log.debug("leaving activateSingleFieldMaster");
		setFineCategoryListToForm(fineCategoryForm);
		return mapping.findForward("fineCategory");
	}
	/**
	 * update fine Category
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateFineCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("inside SingleFieldMaster Action");
		FineCategoryForm fineCategoryForm=(FineCategoryForm)form;
		ActionErrors errors = new ActionErrors();
		//String name = fineCategoryForm.getName();
		ActionMessages messages = new ActionMessages();
		if (fineCategoryForm.getName() == null
				|| StringUtils.isEmpty(fineCategoryForm.getName().trim())) {
			errors.add("error", new ActionError("knowledgepro.admin.FineCategory.required"));
			saveErrors(request, errors);
			request.setAttribute("admOperation", "edit");
			setFineCategoryListToForm(fineCategoryForm);
			return mapping.findForward("fineCategory");
		}
		if (fineCategoryForm.getAmount() == null
				|| StringUtils.isEmpty(fineCategoryForm.getAmount().trim())) {
			errors.add("error", new ActionError("knowledgepro.admin.FineCategoryamount.required"));
			saveErrors(request, errors);
			request.setAttribute("admOperation", "edit");
			setFineCategoryListToForm(fineCategoryForm);
			return mapping.findForward("fineCategory");
		}
		boolean isUpdated = false;
		try {
			setUserId(request, fineCategoryForm);
			isUpdated = FineCategoryHandler.getInstance().addFineCategory(fineCategoryForm, "Edit");
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.FineCategory.name.exists"));
			saveErrors(request, errors);
			setFineCategoryListToForm(fineCategoryForm);
			request.setAttribute("admOperation", "edit");
			return mapping.findForward("fineCategory");
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.FineCategory.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setFineCategoryListToForm(fineCategoryForm);
			request.setAttribute("admOperation", "edit");
			return mapping.findForward("fineCategory");
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				fineCategoryForm.setErrorMessage(msg);
				fineCategoryForm.setErrorStack(e.getMessage());
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.fineCategory.update.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			fineCategoryForm.reset();
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.fineCategory.update.fail"));
			saveErrors(request, errors);

		}
		setFineCategoryListToForm(fineCategoryForm);
		return mapping.findForward("fineCategory");
	}
	/**
	 * delete Fine Category
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editFineCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FineCategoryForm fineCategoryForm=(FineCategoryForm)form;
		ActionMessages messages = new ActionMessages();
		boolean isDeleted = false;
		try {
			if (fineCategoryForm.getId() != 0) {
				int masterId = fineCategoryForm.getId();
				setUserId(request, fineCategoryForm);
				FineCategoryHandler.getInstance().editFineCategory(masterId,fineCategoryForm);
				request.setAttribute("admOperation", "edit");
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				fineCategoryForm.setErrorMessage(msg);
				fineCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		setFineCategoryListToForm(fineCategoryForm);
		return mapping.findForward("fineCategory");
	}
}
