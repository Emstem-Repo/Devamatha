package com.kp.cms.actions.admission;

import java.util.Iterator;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.GroupEntryForm;
import com.kp.cms.handlers.admission.GroupEntryHandler;
import com.kp.cms.to.admission.CCGroupTo;

public class GroupEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(GroupEntryAction.class);
	
	
	/**
	 * setting the required data to the request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGetGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupEntryForm groupEntryForm=(GroupEntryForm)form;
		log.info("Entering into initGetDocumentExams in GroupEntryAction");
		try{
			groupEntryForm.resetFields();
			setRequestedDataToForm(groupEntryForm);
			setUserId(request, groupEntryForm);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured in GroupEntryAction", e);
			String msg = super.handleApplicationException(e);
			groupEntryForm.setErrorMessage(msg);
			groupEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		log.info("Exit from initGetDocumentExams in GroupEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_GROUPS);
	}


	/**
	 * @param groupEntryForm
	 */
	private void setRequestedDataToForm(GroupEntryForm groupEntryForm) throws Exception {
		List<CCGroupTo> groupList=GroupEntryHandler.getInstance().getGroupList();
		groupEntryForm.setCcGroupTos(groupList);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addOrUpdateGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into addGroup in GroupEntryAction");
		GroupEntryForm groupEntryForm=(GroupEntryForm)form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = groupEntryForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try{
				boolean isGroupAdded= GroupEntryHandler.getInstance().addOrUpdateGroup(groupEntryForm,groupEntryForm.getMode());
				if(isGroupAdded){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.CCGroup.addsuccess",groupEntryForm.getName()));
					saveMessages(request, messages);
					groupEntryForm.resetFields();
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.CCGroup.addfailure",groupEntryForm.getName()));
					addErrors(request, errors);
				}
			
			}catch (Exception e) {
				log.error("Error occured in Group Entry Action", e);
				if(e instanceof DuplicateException){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.CCGroup.addexist",groupEntryForm.getName()));
					addErrors(request, errors);
				}else if(e instanceof ReActivateException){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.CCGroup.addfailure.alreadyexist.reactivate",groupEntryForm.getName()));
					addErrors(request, errors);
				}else{
					String msg = super.handleApplicationException(e);
					groupEntryForm.setErrorMessage(msg);
					groupEntryForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
		} else {
			saveErrors(request, errors);
		}
		setRequestedDataToForm(groupEntryForm);
		log.info("Exit from addGroup in GroupEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_GROUPS); 
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteOrReactivateGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into addGroup in GroupEntryAction");
		GroupEntryForm groupEntryForm=(GroupEntryForm)form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = groupEntryForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try{
				boolean isGroupAdded= GroupEntryHandler.getInstance().deleteOrReactivateGroup(groupEntryForm,groupEntryForm.getMode());
				if(isGroupAdded){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.CCGroup.addsuccess",groupEntryForm.getName()));
					saveMessages(request, messages);
					groupEntryForm.resetFields();
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.CCGroup.addfailure",groupEntryForm.getName()));
					addErrors(request, errors);
				}
			
			}catch (Exception e) {
				log.error("Error occured in Group Entry Action", e);
				if(e instanceof DuplicateException){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.CCGroup.addexist",groupEntryForm.getName()));
					addErrors(request, errors);
				}else if(e instanceof ReActivateException){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.CCGroup.addfailure.alreadyexist.reactivate",groupEntryForm.getName()));
					addErrors(request, errors);
				}else{
					String msg = super.handleApplicationException(e);
					groupEntryForm.setErrorMessage(msg);
					groupEntryForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
		} else {
			saveErrors(request, errors);
		}
		setRequestedDataToForm(groupEntryForm);
		log.info("Exit from addGroup in GroupEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_GROUPS); 
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupEntryForm groupEntryForm=(GroupEntryForm)form;
		log.info("Entering into initGetDocumentExams in GroupEntryAction");
		try{
			List<CCGroupTo> toList=groupEntryForm.getCcGroupTos();
			Iterator<CCGroupTo> itr=toList.iterator();
			while (itr.hasNext()) {
				CCGroupTo to = (CCGroupTo) itr.next();
				if(to.getId()==groupEntryForm.getId()){
					groupEntryForm.setName(to.getName());
					groupEntryForm.setStartDate(to.getStartDate());
					groupEntryForm.setEndDate(to.getEndDate());
					groupEntryForm.setMode("update");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured in GroupEntryAction", e);
			String msg = super.handleApplicationException(e);
			groupEntryForm.setErrorMessage(msg);
			groupEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		log.info("Exit from initGetDocumentExams in GroupEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_GROUPS);
	}
}
