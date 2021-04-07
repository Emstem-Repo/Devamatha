package com.kp.cms.actions.supportrequest;

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
import com.kp.cms.forms.supportrequest.CategoryForm;
import com.kp.cms.handlers.supportrequest.CategoryHandler;
import com.kp.cms.utilities.CommonUtil;

public class CategoryAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(CategoryAction.class);
	CategoryHandler categoryHandler=CategoryHandler.getInstance();
	public ActionForward initCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Disciplinary Details action");
		CategoryForm categoryForm = (CategoryForm) form;
		reset(categoryForm);
		setRequiredData(categoryForm);
		setCategoryListToForm(categoryForm);
		return mapping.findForward(CMSConstants.INIT_CATEGORY);
	}

	private void setCategoryListToForm(CategoryForm categoryForm)throws Exception {
		categoryHandler.getCategory(categoryForm);
	}

	private void setRequiredData(CategoryForm categoryForm)throws Exception {
		categoryHandler.getDepartmentMap(categoryForm);
	}
	/**
	 * method used to add  fine entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside action class. addFineEntry Action");
		CategoryForm categoryForm=(CategoryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = categoryForm.validate(mapping, request);
		boolean isAdded=false;
		try {
			if(errors.isEmpty()){
				setUserId(request, categoryForm);
				categoryForm.setMode("Add");
				boolean duplicate=categoryHandler.checkDuplicate(categoryForm);
				if(duplicate){
					//duplicate
					errors.add("error", new ActionError("knowledgepro.admin.FineCategory.name.exists"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CATEGORY);
				}
				if(categoryForm.getEmail()!=null && !categoryForm.getEmail().isEmpty()){
					boolean flag=CommonUtil.validateEmailID(categoryForm.getEmail());
					if(!flag){
						errors.add("error", new ActionError("knowledgepro.supportrequest.mail.notvalid"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CATEGORY);
					}
				}
				isAdded=categoryHandler.addCategory(categoryForm);
				if(isAdded){
					// success .
					ActionMessage message = new ActionMessage("knowledgepro.admin.FineCategory.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					reset(categoryForm);
				
				}else{
					// failed
					errors.add("error", new ActionError("knowledgepro.admin.FineCategory.addfailure"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
				saveErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				categoryForm.setErrorMessage(msg);
				categoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("Leaving action class addFineEntry");
		setCategoryListToForm(categoryForm);
		return mapping.findForward(CMSConstants.INIT_CATEGORY);
	}

	private void reset(CategoryForm categoryForm) {
		categoryForm.setDepartmentId(null);
		categoryForm.setName(null);
		categoryForm.setCategoryFor(null);
		categoryForm.setMode(null);
		categoryForm.setEmail(null);
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
	public ActionForward deleteCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CategoryForm categoryForm=(CategoryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (categoryForm.getId() != 0) {
				setUserId(request, categoryForm);
				isDeleted = categoryHandler.deleteCategory(categoryForm.getId(), false,categoryForm);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				categoryForm.setErrorMessage(msg);
				categoryForm.setErrorStack(e.getMessage());
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
			reset(categoryForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.admin.FineCategory.deletefail"));
			saveErrors(request, errors);
		}
		setCategoryListToForm(categoryForm);
		return mapping.findForward(CMSConstants.INIT_CATEGORY);
	}
	/**
	 * editing the Fine Entry details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CategoryForm categoryForm=(CategoryForm)form;
		reset(categoryForm);
		categoryForm.setCategoryList(null);
		log.debug("Entering editFineEntry ");
		try{
			categoryHandler.editFineEntry(categoryForm);
			request.setAttribute("admOperation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editVisitorsInfo ");
		}catch (Exception e) {
			log.error("error in editing editFineEntry...", e);
			String msg = super.handleApplicationException(e);
			categoryForm.setErrorMessage(msg);
			categoryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequiredData(categoryForm);
		setCategoryListToForm(categoryForm);
		return mapping.findForward(CMSConstants.INIT_CATEGORY);
	}
	/**
	 * method used to add  fine entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside action class. addFineEntry Action");
		CategoryForm categoryForm=(CategoryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = categoryForm.validate(mapping, request);
		boolean isUpdate=false;
		try {
			if(errors.isEmpty()){
				setUserId(request, categoryForm);
				categoryForm.setMode("Update");
				boolean duplicate=categoryHandler.checkDuplicate(categoryForm);
				if(duplicate){
					//duplicate
					request.setAttribute("admOperation", "edit");
					errors.add("error", new ActionError("knowledgepro.admin.FineCategory.name.exists"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CATEGORY);
				}
				if(categoryForm.getEmail()!=null && !categoryForm.getEmail().isEmpty()){
					boolean flag=CommonUtil.validateEmailID(categoryForm.getEmail());
					if(!flag){
						errors.add("error", new ActionError("knowledgepro.supportrequest.mail.notvalid"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CATEGORY);
					}
				}
				isUpdate=categoryHandler.updateCategory(categoryForm);
				if(isUpdate){
					// success .
					ActionMessage message = new ActionMessage("knowledgepro.admin.category.updatesuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					reset(categoryForm);
				
				}else{
					// failed
					errors.add("error", new ActionError( "knowledgepro.hostel.category.updatefial"));
					saveErrors(request, errors);
				}
			}else{
				request.setAttribute("admOperation", "edit");
				saveErrors(request, errors);
				saveErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				categoryForm.setErrorMessage(msg);
				categoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("Leaving action class addFineEntry");
		setCategoryListToForm(categoryForm);
		return mapping.findForward(CMSConstants.INIT_CATEGORY);
	}
}
