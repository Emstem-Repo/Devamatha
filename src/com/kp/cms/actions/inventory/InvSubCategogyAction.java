package com.kp.cms.actions.inventory;

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
import com.kp.cms.bo.admin.InvSubCategoryBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ValuatorChargesForm;
import com.kp.cms.forms.inventory.InvSubCategoryForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.ValuatorChargesHandler;
import com.kp.cms.handlers.inventory.InvSubCategoryHandler;
import com.kp.cms.to.inventory.InvCategoryTo;
import com.kp.cms.to.inventory.InvSubCategoryTo;

public class InvSubCategogyAction extends BaseDispatchAction{
	private static final Log log=LogFactory.getLog(InvSubCategogyAction.class);

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSubCategory(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initSubCategory method in InvSubCategogyAction class.");
		InvSubCategoryForm invSubCategoryForm=(InvSubCategoryForm) form;
		HttpSession session=request.getSession(false);
		String formName=mapping.getName();
		request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
		setItemCategoryToRequest(request);
		invSubCategoryForm.reset();
		setRequestedDateToForm(invSubCategoryForm);
		if(session.getAttribute("newEntryName")!=null)
			session.removeAttribute("newEntryName");
		if(request.getParameter("mainPageExists")==null)
			invSubCategoryForm.setMainPage(null);
		log.debug("Leaving initSubCategory");
		return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY);
	}
	
	/**
	 * @param request
	 * @throws Exception
	 */
	public void setItemCategoryToRequest(HttpServletRequest request)throws Exception
	{
		log.debug("inside setItemCategoryToRequest");
		List <InvCategoryTo> categoryList=InvSubCategoryHandler.getInstance().getCategory();
		request.getSession().setAttribute("categoryList", categoryList);
		log.debug("leaving setItemCategoryToRequest");
	}
	/**
	 * @param invSubCategoryForm
	 */
	public void setRequestedDateToForm(InvSubCategoryForm invSubCategoryForm) throws Exception{
		List<InvSubCategoryTo> subCategoryList=InvSubCategoryHandler.getInstance().getSubCategoryList();
		invSubCategoryForm.setSubCategoryList(subCategoryList);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward addSubCategory(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		InvSubCategoryForm invSubCategoryForm = (InvSubCategoryForm) form;
		setUserId(request,invSubCategoryForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = invSubCategoryForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=InvSubCategoryHandler.getInstance().duplicateCheck(invSubCategoryForm,errors,session);
				if(!isDuplicate){
				isAdded = InvSubCategoryHandler.getInstance().addSubCategory(invSubCategoryForm,"Add");
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					if(invSubCategoryForm.getMainPage()!=null && !invSubCategoryForm.getMainPage().isEmpty()){
						session.setAttribute("newEntryName",invSubCategoryForm.getSubCategoryName());
						session.setAttribute("itemCategoryId", invSubCategoryForm.getInvItemCategory());
					}
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.inventory.subcategory.addsuccess"));
					saveMessages(request, messages);
					setRequestedDateToForm(invSubCategoryForm);
					invSubCategoryForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.inventory.subcategory.addfailure"));
					addErrors(request, errors);
					invSubCategoryForm.reset();
				}}
				else{
					addErrors(request, errors);
				}			
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				invSubCategoryForm.setErrorMessage(msg);
				invSubCategoryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setRequestedDateToForm(invSubCategoryForm);
			return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY);
		}
		
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editSubCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvSubCategoryForm invSubCategoryForm = (InvSubCategoryForm) form;
		log.debug("Entering ValuatorCharges ");
		try {
			InvSubCategoryHandler.getInstance().editSubCategory(invSubCategoryForm);
			request.setAttribute("invSubCategory", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			invSubCategoryForm.setErrorMessage(msg);
			invSubCategoryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSubCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatevaluatorCharges Action");
		InvSubCategoryForm invSubCategoryForm = (InvSubCategoryForm) form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = invSubCategoryForm.validate(mapping, request);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				invSubCategoryForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				InvSubCategoryHandler.getInstance().editSubCategory(invSubCategoryForm);
				request.setAttribute("invSubCategory", "edit");
				return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY);
			}
			setUserId(request, invSubCategoryForm); // setting user id to update
			boolean isDuplicate=InvSubCategoryHandler.getInstance().duplicateCheck(invSubCategoryForm,errors,session);
			if(!isDuplicate){
				isUpdated = InvSubCategoryHandler.getInstance().updateSubCategory(invSubCategoryForm,"Edit");
			if (isUpdated) {
				if(invSubCategoryForm.getMainPage()!=null && !invSubCategoryForm.getMainPage().isEmpty()){
					session.setAttribute("newEntryName",invSubCategoryForm.getSubCategoryName());
					session.setAttribute("itemCategoryId", invSubCategoryForm.getInvItemCategory());
				}
				ActionMessage message = new ActionMessage("knowledgepro.inventory.subcategory.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				invSubCategoryForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.inventory.subcategory.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				invSubCategoryForm.reset();
			}}
			else{
				request.setAttribute("invSubCategory", "edit");
				addErrors(request, errors);
				//invSubCategoryForm.reset();
			}
		} catch (Exception e) {
			log.error("Error occured in edit valuatorcharges", e);
			String msg = super.handleApplicationException(e);
			invSubCategoryForm.setErrorMessage(msg);
			invSubCategoryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDateToForm(invSubCategoryForm);
			request.setAttribute("invSubCategory", "edit");
			return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY);
		}
        setRequestedDateToForm(invSubCategoryForm);
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSubCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		InvSubCategoryForm invSubCategoryForm = (InvSubCategoryForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted =InvSubCategoryHandler.getInstance().deleteSubCategory(invSubCategoryForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage("knowledgepro.inventory.subcategory.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage("knowledgepro.inventory.subcategory.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			invSubCategoryForm.reset();
			setRequestedDateToForm(invSubCategoryForm);
		} catch (Exception e) {
			log.error("error submit valuatorCharges...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				invSubCategoryForm.setErrorMessage(msg);
				invSubCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				invSubCategoryForm.setErrorMessage(msg);
				invSubCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete valuatorCharges ");
		return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateSubCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivateValuatorCharges Action");
		InvSubCategoryForm invSubCategoryForm = (InvSubCategoryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, invSubCategoryForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = invSubCategoryForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			invSubCategoryForm.setId(Integer.parseInt(duplicateId));
			isReactivate =  InvSubCategoryHandler.getInstance().reactivateSubCategory(invSubCategoryForm,userId);
			if(isReactivate){
				if(invSubCategoryForm.getMainPage()!=null && !invSubCategoryForm.getMainPage().isEmpty()){
					String reactivatedName=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(invSubCategoryForm.getId(), "InvSubCategoryBo", true, "subCategoryName");
					String itemCategoryId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(invSubCategoryForm.getId(), "InvSubCategoryBo", true, "invItemCategory.id");
					session.setAttribute("newEntryName",reactivatedName);
					session.setAttribute("itemCategoryId", itemCategoryId);
				}
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.inventory.subcategory.activate"));
				setRequestedDateToForm(invSubCategoryForm);
				invSubCategoryForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDateToForm(invSubCategoryForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.inventory.subcategory.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivatevaluatorCharges of ValuatorChargesAction", e);
			String msg = super.handleApplicationException(e);
			invSubCategoryForm.setErrorMessage(msg);
			invSubCategoryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivatevaluatorCharges of ValuatorChargesAction");
		return mapping.findForward(CMSConstants.INVENTORY_SUB_CATEGORY); 
	}

}
