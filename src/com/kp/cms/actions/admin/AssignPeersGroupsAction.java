package com.kp.cms.actions.admin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AssignPeersGroupsForm;
import com.kp.cms.handlers.admin.AssignPeersGroupsHandlers;
import com.kp.cms.handlers.admin.PeersEvaluationGroupsHandler;
import com.kp.cms.handlers.admin.PeersEvaluationOpenSessionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.AssignPeersGroupsTo;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.PeersEvaluationGroupsTO;

public class AssignPeersGroupsAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAssignPeersGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssignPeersGroupsForm assignPeersGroupsForm =(AssignPeersGroupsForm)form;
		setUserId(request, assignPeersGroupsForm);
		assignPeersGroupsForm.clear(mapping, request);
		try{
			assignPeersGroupsForm.setDisplayErrorMsg(null);
			setDepartmentList(assignPeersGroupsForm);
			setPeersGroupsList(assignPeersGroupsForm);
			setAssignPeersToGroups(assignPeersGroupsForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			assignPeersGroupsForm.setErrorMessage(msg);
			assignPeersGroupsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ASSIGN_PEERS_GROUPS);
	}

	/**
	 * @param assignPeersGroupsForm
	 * @throws Exception
	 */
	private void setAssignPeersToGroups( AssignPeersGroupsForm assignPeersGroupsForm) throws Exception{
		List<AssignPeersGroupsTo> list = AssignPeersGroupsHandlers.getInstance().getAssignPeersToGroups();
		assignPeersGroupsForm.setPeersGroupsTos(list);
	}

	/**
	 * @param assignPeersGroupsForm
	 * @throws Exception 
	 */
	private void setPeersGroupsList(AssignPeersGroupsForm assignPeersGroupsForm) throws Exception {
		List<PeersEvaluationGroupsTO> groupsListTO = PeersEvaluationGroupsHandler.getInstance().getPeersEvaluationGroupsList();
		assignPeersGroupsForm.setGroupsTOs(groupsListTO);
	}

	/**
	 * @param assignPeersGroupsForm
	 * @throws Exception
	 */
	private void setDepartmentList( AssignPeersGroupsForm assignPeersGroupsForm) throws Exception{
		List<DepartmentEntryTO> departmentTO= PeersEvaluationOpenSessionHandler.getInstance().getDepartmentList();
		Collections.sort(departmentTO);
		assignPeersGroupsForm.setDepartmentTo(departmentTO);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFacultyByDepartmentToGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssignPeersGroupsForm assignPeersGroupsForm =(AssignPeersGroupsForm)form;
		setUserId(request, assignPeersGroupsForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignPeersGroupsForm.validate(mapping, request);
		boolean isAdded = false;
		HttpSession session = request.getSession();
		try{
			assignPeersGroupsForm.setDisplayErrorMsg(null);
			if (errors.isEmpty()) {
			boolean isDuplicate = AssignPeersGroupsHandlers.getInstance().checkFacultyDuplicatesForAdd(assignPeersGroupsForm);
			if(!isDuplicate){
					isAdded = AssignPeersGroupsHandlers.getInstance().addFacultyByDepartmentToGroups(assignPeersGroupsForm,session);
					if(isAdded){
						ActionMessage message = new ActionError( "knowledgepro.admin.assign.peers.addsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						
					}else{
						errors .add( "error", new ActionError( "knowledgepro.admin.assign.peers.addfailure"));
						saveErrors(request, errors);
					}
			}else{
				List<String> duplicateNames = assignPeersGroupsForm.getDupFacultyIds();
				Iterator<String> iterator = duplicateNames.iterator();
				StringBuilder s = new StringBuilder();
				while (iterator.hasNext()) {
					String dupName = (String) iterator.next();
					s.append(dupName).append(",");
				}
				assignPeersGroupsForm.setDisplayErrorMsg(s+ "\n are already assigned for Same Group");
			}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_PEERS_GROUPS);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			assignPeersGroupsForm.setErrorMessage(msg);
			assignPeersGroupsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setAssignPeersToGroups(assignPeersGroupsForm);
		assignPeersGroupsForm.clear(mapping, request);
		/*setDepartmentList(assignPeersGroupsForm);
		setPeersGroupsList(assignPeersGroupsForm);*/
		return mapping.findForward(CMSConstants.INIT_ASSIGN_PEERS_GROUPS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editAssignPeersGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssignPeersGroupsForm assignPeersGroupsForm =(AssignPeersGroupsForm)form;
		setUserId(request, assignPeersGroupsForm);
		HttpSession session = request.getSession();
		try{
			assignPeersGroupsForm.setDisplayErrorMsg(null);
			AssignPeersGroupsHandlers.getInstance().editAssignPeersGroups(assignPeersGroupsForm,session);
			request.setAttribute("assignPeers", "edit");
			/*setDepartmentList(assignPeersGroupsForm);
			setPeersGroupsList(assignPeersGroupsForm);*/
			setAssignPeersToGroups(assignPeersGroupsForm);
			setFacultyListByDepartment(assignPeersGroupsForm,request);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			assignPeersGroupsForm.setErrorMessage(msg);
			assignPeersGroupsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_ASSIGN_PEERS_GROUPS);
	}

	/**
	 * @param assignPeersGroupsForm
	 * @throws Exception
	 */
	private void setFacultyListByDepartment( AssignPeersGroupsForm assignPeersGroupsForm,HttpServletRequest request) throws Exception{
		int departmentId = Integer.parseInt(assignPeersGroupsForm.getDepartmentId().trim());
		// The below map contains key as userid and value as name of Employee.
		Map<Integer,String> teachersMap = CommonAjaxHandler.getInstance().getFacultyByDepartment(departmentId);
		request.setAttribute(CMSConstants.OPTION_MAP, teachersMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAssignPeersToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssignPeersGroupsForm assignPeersGroupsForm =(AssignPeersGroupsForm)form;
		setUserId(request, assignPeersGroupsForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = assignPeersGroupsForm.validate(mapping, request);
		boolean isAdded = false;
		HttpSession session = request.getSession();
		try{
			assignPeersGroupsForm.setDisplayErrorMsg(null);
			if (errors.isEmpty()) {
			boolean isDuplicate = AssignPeersGroupsHandlers.getInstance().checkFacultyDuplicatesForUpdate(assignPeersGroupsForm);
			if(!isDuplicate){
					isAdded = AssignPeersGroupsHandlers.getInstance().updateFacultyByDepartmentToGroups(assignPeersGroupsForm,session);
					if(isAdded){
						ActionMessage message = new ActionError( "knowledgepro.admin.assign.peers.updatesuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						
					}else{
						errors .add( "error", new ActionError( "knowledgepro.admin.assign.peers.updatefailure"));
						saveErrors(request, errors);
					}
			}else{
				List<String> duplicateNames = assignPeersGroupsForm.getDupFacultyIds();
				Iterator<String> iterator = duplicateNames.iterator();
				StringBuilder s = new StringBuilder();
				while (iterator.hasNext()) {
					String dupName = (String) iterator.next();
					s.append(dupName).append(",");
				}
				assignPeersGroupsForm.setDisplayErrorMsg(s+ "\n are already assigned for some other Group");
			}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_PEERS_GROUPS);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			assignPeersGroupsForm.setErrorMessage(msg);
			assignPeersGroupsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setAssignPeersToGroups(assignPeersGroupsForm);
		assignPeersGroupsForm.clear(mapping, request);
		/*setDepartmentList(assignPeersGroupsForm);
		setPeersGroupsList(assignPeersGroupsForm);*/
		return mapping.findForward(CMSConstants.INIT_ASSIGN_PEERS_GROUPS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAssignPeersToGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssignPeersGroupsForm assignPeersGroupsForm =(AssignPeersGroupsForm)form;
		setUserId(request, assignPeersGroupsForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			assignPeersGroupsForm.setDisplayErrorMsg(null);
			boolean isdelete = AssignPeersGroupsHandlers.getInstance().deleteAssignPeersToGroups(assignPeersGroupsForm);
			if(isdelete){
				ActionMessage message = new ActionError( "knowledgepro.admin.assign.peers.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				assignPeersGroupsForm.clear(mapping, request);
			}else{
				errors .add( "error", new ActionError( "knowledgepro.admin.assign.peers.deletefailure"));
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			assignPeersGroupsForm.setErrorMessage(msg);
			assignPeersGroupsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setAssignPeersToGroups(assignPeersGroupsForm);
		return mapping.findForward(CMSConstants.INIT_ASSIGN_PEERS_GROUPS);
	}
}
