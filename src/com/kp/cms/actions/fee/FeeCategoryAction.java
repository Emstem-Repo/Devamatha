package com.kp.cms.actions.fee;

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
import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeCategoryForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.fee.FeeCategoryHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.fee.FeeCategoryTo;

public class FeeCategoryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(FeeCategoryAction.class);

	public ActionForward initFeeCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	
		FeeCategoryForm feeCategoryForm = (FeeCategoryForm) form;	
		feeCategoryForm.reset(mapping, request);
		List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
		feeCategoryForm.setFeeCategoryList(feeCategoryList);
		return mapping.findForward(CMSConstants.FEE_CATEGORY);
	}
	
	
	public ActionForward addFeeCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		FeeCategoryForm feeCategoryForm = (FeeCategoryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = feeCategoryForm.validate(mapping, request);
		boolean isFeeCategoryAdded = false;
		if (errors.isEmpty()) {
			try{
				setUserId(request, feeCategoryForm);
				isFeeCategoryAdded = FeeCategoryHandler.getInstance().addFeeCategory(feeCategoryForm,request);
			}
			catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.feeCategory.name.exists",feeCategoryForm.getFeeCategoryName()));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.FEE_CATEGORY);
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.feeCategory.addfailure.alreadyexist.reactivate",feeCategoryForm.getFeeCategoryName()));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.FEE_CATEGORY);
				}
				log.error("Error occured in FeeCategory Action", e);
				String msg = super.handleApplicationException(e);
				feeCategoryForm.setErrorMessage(msg);
				feeCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			if (isFeeCategoryAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.feeCategory.addsuccess", feeCategoryForm.getFeeCategoryName());
				messages.add("messages", message);
				saveMessages(request, messages);
				feeCategoryForm.reset(mapping, request);
				List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
				feeCategoryForm.setFeeCategoryList(feeCategoryList);
			}
		}
		else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.FEE_CATEGORY);
		}
		return mapping.findForward(CMSConstants.FEE_CATEGORY);
	}
	
	public ActionForward deleteFeeCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeCategoryForm feeCategoryForm = (FeeCategoryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int feeCategoryId = feeCategoryForm.getFeeCategoryId();
		String name=feeCategoryForm.getFeeCategoryName();
		boolean isfeeCategoryDeleted=false;
		try{
			setUserId(request, feeCategoryForm);
			isfeeCategoryDeleted = FeeCategoryHandler.getInstance().deleteFeeCategory(feeCategoryId,feeCategoryForm.getUserId());
		}
		catch (Exception e) {
			log.error("Error occured in FeeCategory Action", e);
			String msg = super.handleApplicationException(e);
			feeCategoryForm.setErrorMessage(msg);
			feeCategoryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);			
		}
		if(isfeeCategoryDeleted){
			ActionMessage message = new ActionMessage("knowledgepro.admin.feeCategory.deletesuccess", name);
			messages.add("messages", message);
			saveMessages(request, messages);
			feeCategoryForm.reset(mapping, request);
			List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
			feeCategoryForm.setFeeCategoryList(feeCategoryList);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.feeCategory.deletefailure",name));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.FEE_CATEGORY);
	}
	
	public ActionForward reActivateFeeCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeCategoryForm feeCategoryForm = (FeeCategoryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		FeeCategory feeCategory =(FeeCategory)request.getSession().getAttribute("FeeCategory");
		boolean isFeeCategoryReActivate=false;
		try{
			setUserId(request, feeCategoryForm);
			isFeeCategoryReActivate = FeeCategoryHandler.getInstance().reActivateFeeCategory(feeCategory,feeCategoryForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in FeeCategory Action", e);
			String msg = super.handleApplicationException(e);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		if (isFeeCategoryReActivate) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.feeCategory.activate");
			messages.add("messages", message);
			saveMessages(request, messages);
			feeCategoryForm.reset(mapping, request);
			List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
			feeCategoryForm.setFeeCategoryList(feeCategoryList);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.feeCategory.activatefailure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.FEE_CATEGORY);
	}	
	
	public ActionForward updateFeeCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FeeCategoryForm feeCategoryForm = (FeeCategoryForm) form;
		ActionErrors errors = feeCategoryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		String name=feeCategoryForm.getFeeCategoryName();
		boolean isFeeCategoryEdited = false;
		if (errors.isEmpty()) {
			try{
				setUserId(request, feeCategoryForm);
				isFeeCategoryEdited = FeeCategoryHandler.getInstance().updateFeeCategory(feeCategoryForm,request);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.feeCategory.name.exists",name));
					saveErrors(request, errors);
					feeCategoryForm.reset(mapping, request);
					List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
					feeCategoryForm.setFeeCategoryList(feeCategoryList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.FEE_CATEGORY);
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.admin.feeCategory.addfailure.alreadyexist.reactivate",name));
					saveErrors(request, errors);
					feeCategoryForm.reset(mapping, request);
					List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
					feeCategoryForm.setFeeCategoryList(feeCategoryList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.FEE_CATEGORY);
				}
				log.error("Error occured in FeeCategory Entry Action", e);
				String msg = super.handleApplicationException(e);
				feeCategoryForm.setErrorMessage(msg);
				feeCategoryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			if (isFeeCategoryEdited) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.feeCategory.updatesuccess", feeCategoryForm.getFeeCategoryName());
				messages.add("messages", message);
				saveMessages(request, messages);
				feeCategoryForm.reset(mapping, request);
				List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
				feeCategoryForm.setFeeCategoryList(feeCategoryList);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.feeCategory.updatefailure",  feeCategoryForm.getFeeCategoryName()));
				saveErrors(request, errors);
				feeCategoryForm.reset(mapping, request);
				List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
				feeCategoryForm.setFeeCategoryList(feeCategoryList);
			}
		} else {
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");
			feeCategoryForm.reset(mapping, request);
			List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
			feeCategoryForm.setFeeCategoryList(feeCategoryList);
			return mapping.findForward(CMSConstants.FEE_CATEGORY);
		}
			
		return mapping.findForward(CMSConstants.FEE_CATEGORY);
	}
}
