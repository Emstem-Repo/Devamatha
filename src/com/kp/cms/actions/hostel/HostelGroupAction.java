package com.kp.cms.actions.hostel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.hostel.HostelGroupForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelGroupHandler;
import com.kp.cms.to.hostel.ApplicationFormTO;
import com.kp.cms.to.hostel.HostelGroupTO;
import com.kp.cms.to.hostel.HostelTO;

@SuppressWarnings("deprecation")
public class HostelGroupAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(HostelGroupAction.class);
	
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("inside getHostelDeatils");
		HostelGroupForm hForm = (HostelGroupForm) form;
		resetFields(hForm);
		try {
			setHostelEntryDetailsToRequest(hForm,request);
			setUserId(request, hForm);
		} catch (Exception e) {
			log.error("error in getHostelDeatils...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hForm.setErrorMessage(msg);
				hForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_GROUP);
	}
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHostelDeatils(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("inside getHostelDeatils");
		HostelGroupForm hForm = (HostelGroupForm) form;
		ActionErrors errors = hForm.validate(mapping, request);
		try {
			if(errors.isEmpty()){
				setHostelEntryDetailsToRequest(hForm,request);
				assignStudentListToRequest(hForm);
				assignHostelGroupListToRequest(hForm,request);
				setUserId(request, hForm);
				if(hForm.getStudList()==null || hForm.getStudList().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_GROUP);
				}
			}else{
				saveErrors(request, errors);
				setHostelEntryDetailsToRequest(hForm,request);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_GROUP);
			}
		} catch (Exception e) {
			log.error("error in getHostelDeatils...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hForm.setErrorMessage(msg);
				hForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		return mapping.findForward(CMSConstants.HOSTEL_GROUP);
	}	

	/**
	 * setting hostel list to request
	 * @param request
	 * @throws Exception 
	 */
	public void setHostelEntryDetailsToRequest(HostelGroupForm hForm,HttpServletRequest request) throws Exception{
		log.debug("start setHostelEntryDetailsToRequest");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
		if(hForm.getHostelId()!=null && !hForm.getHostelId().isEmpty()){
			Map<Integer, String> floorMap=CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(hForm.getHostelId()));
			request.setAttribute("floorMap", floorMap);
		}
		log.debug("exit setHostelEntryDetailsToRequest");
	}
	/**
	 * setting student list to form
	 * @param request
	 * @throws Exception 
	 */
	public void assignStudentListToRequest(HostelGroupForm hForm) throws Exception{
		log.debug("start assignStudentListToRequest");
		List<ApplicationFormTO> studList = HostelGroupHandler.getInstance().getStudentDetails(hForm);
		hForm.setStudList(studList);
		log.debug("exit assignStudentListToRequest");
	}
	/**
	 * save method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveHostelGroup(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside saveHostelGroup");
		HostelGroupForm hlForm = (HostelGroupForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hlForm.validate(mapping, request);
		hlForm.setId(0);
		Boolean isAdded;
		try {
			Iterator<ApplicationFormTO> studItr = hlForm.getStudList().iterator();
			ApplicationFormTO appFormTO;
			Boolean isSelected = false;
			while (studItr.hasNext()){
				appFormTO = studItr.next();
				if(appFormTO.isSelected()){
					isSelected = true;
				}
			}
			if(!isSelected){
				errors.add("error", new ActionError("knowledgepro.hostel.group.student.selection"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setHostelEntryDetailsToRequest(hlForm,request);
				assignHostelGroupListToRequest(hlForm,request);
				resetStudentList(hlForm);
				return mapping.findForward(CMSConstants.HOSTEL_GROUP);
			}
			
						
			
			isAdded = HostelGroupHandler.getInstance().saveHostelGroup(hlForm, "add"); 
			setHostelEntryDetailsToRequest(hlForm,request);
			assignStudentListToRequest(hlForm);
			assignHostelGroupListToRequest(hlForm,request);
		} catch (DuplicateException e1) {
			if(hlForm.getStudentDuplicated()){
				errors.add("error", new ActionError("knowledgepro.hostel.group.student.exist"));
			}
			else{
				errors.add("error", new ActionError("knowledgepro.hostel.group.name.exist"));
			}
				
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(hlForm,request);
			assignHostelGroupListToRequest(hlForm,request);
			resetStudentList(hlForm);
			return mapping.findForward(CMSConstants.HOSTEL_GROUP);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.HOSTEL_GROUP_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(hlForm,request);
			assignStudentListToRequest(hlForm);
			assignHostelGroupListToRequest(hlForm,request);
			return mapping.findForward(CMSConstants.HOSTEL_GROUP);		
		} catch (Exception e) {
			log.error("error in saveHostelGroup...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hlForm.setErrorMessage(msg);
				hlForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.group.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			resetFields(hlForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.group.addfailure"));
			saveErrors(request, errors);
		}
		
		log.debug("leaving saveRooms");
		return mapping.findForward(CMSConstants.HOSTEL_GROUP);
	}	
	/**
	 * reset functionality
	 * @param hForm
	 */
	public void resetFields(HostelGroupForm hForm){
		hForm.setFloorNo(null);
		hForm.setHostelId(null);
		hForm.setGroupName(null);
		
	}
	/**
	 * setting hlGroupList to request
	 * @param request
	 * @throws Exception 
	 */
	public void assignHostelGroupListToRequest(HostelGroupForm hForm,HttpServletRequest request) throws Exception {
		log.debug("start assignHostelGroupListToRequest");
		List<HostelGroupTO> hlGroupList = HostelGroupHandler.getInstance().getHostelGroup(hForm);
		request.setAttribute("hlGroupList", hlGroupList);
		log.debug("exit assignHostelGroupListToRequest");
	}
	
	/**
	 * Used when edit button is clicked
	 */

	public ActionForward editHostelGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into editHostelGroup of RoomType Action");
		HostelGroupForm hostelGroupForm = (HostelGroupForm)form;
		try {
			HostelGroupHandler.getInstance().getHostelGroupById(hostelGroupForm.getId(), hostelGroupForm);
			setHostelEntryDetailsToRequest(hostelGroupForm,request);
			assignHostelGroupListToRequest(hostelGroupForm,request);
			setFloorMapToRequest(request, hostelGroupForm);			
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in editRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			hostelGroupForm.setErrorMessage(msg);
			hostelGroupForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving editHostelGroup");
		return  mapping.findForward(CMSConstants.HOSTEL_GROUP);
	}
	/**
	 * setting floor map to request
	 * @param request
	 * @param hForm
	 */
	public void setFloorMapToRequest(HttpServletRequest request, HostelGroupForm hForm) {
		if (hForm.getHostelId() != null
				&& !(hForm.getHostelId().isEmpty())) {
			Map<Integer, String> floorMap = CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(hForm.getHostelId()));
			request.setAttribute("floorMap", floorMap);
		}
	}

	/**
	 * update hostel group
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHostelGroup(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside saveHostelGroup");
		HostelGroupForm hlForm = (HostelGroupForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hlForm.validate(mapping, request);
		Boolean isAdded;
		try {
			if(isCancelled(request)){
				HostelGroupHandler.getInstance().getHostelGroupById(hlForm.getId(), hlForm);
				setHostelEntryDetailsToRequest(hlForm,request);
				assignHostelGroupListToRequest(hlForm,request);
				setFloorMapToRequest(request, hlForm);			
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.HOSTEL_GROUP);
			}
			Iterator<ApplicationFormTO> studItr = hlForm.getStudList().iterator();
			ApplicationFormTO appFormTO;
			Boolean isSelected = false;
			while (studItr.hasNext()){
				appFormTO = studItr.next();
				if(appFormTO.isSelected()){
					isSelected = true;
				}
			}
			if(!isSelected){
				errors.add("error", new ActionError("knowledgepro.hostel.group.student.selection"));
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setHostelEntryDetailsToRequest(hlForm,request);
				assignHostelGroupListToRequest(hlForm,request);
				resetStudentList(hlForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.HOSTEL_GROUP);
			}
			
			isAdded = HostelGroupHandler.getInstance().saveHostelGroup(hlForm, "edit"); 
			setHostelEntryDetailsToRequest(hlForm,request);
			assignStudentListToRequest(hlForm);
			assignHostelGroupListToRequest(hlForm,request);
		} catch (DuplicateException e1) {
			if(hlForm.getStudentDuplicated()){
				errors.add("error", new ActionError("knowledgepro.hostel.group.student.exist"));
			}
			else{
				errors.add("error", new ActionError("knowledgepro.hostel.group.name.exist"));
			}
			saveErrors(request, errors);
			resetStudentList(hlForm);
			setHostelEntryDetailsToRequest(hlForm,request);
			assignHostelGroupListToRequest(hlForm,request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.HOSTEL_GROUP);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.HOSTEL_GROUP_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setHostelEntryDetailsToRequest(hlForm,request);
			assignStudentListToRequest(hlForm);
			assignHostelGroupListToRequest(hlForm,request);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.HOSTEL_GROUP);			
		} catch (Exception e) {
			log.error("error in updateHostelGroup...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hlForm.setErrorMessage(msg);
				hlForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.group.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			resetFields(hlForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.group.addfailure"));
			saveErrors(request, errors);
		}
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("leaving saveRooms");
		return mapping.findForward(CMSConstants.HOSTEL_GROUP);
	}		

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the Hostel Group
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHostelGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteHostelGroup");
		HostelGroupForm hForm = (HostelGroupForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (hForm.getId() != 0) {
				int hId = hForm.getId();
				isDeleted = HostelGroupHandler.getInstance().deleteHostelGroup(hId, false, hForm); 
			}
		} catch (Exception e) {
			log.error("error in deleteHostelEntry...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hForm.setErrorMessage(msg);
				hForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setHostelEntryDetailsToRequest(hForm,request);
		assignStudentListToRequest(hForm);
		assignHostelGroupListToRequest(hForm,request);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.hostel.hlgroup.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			resetFields(hForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.hostel.hlgroup.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteHostelGroup");
		return mapping.findForward(CMSConstants.HOSTEL_GROUP);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the hostel group
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateHostelGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateHostelGroup");
		HostelGroupForm hlForm = (HostelGroupForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (hlForm.getDuplId() != 0) {
				int id = hlForm.getDuplId();  //setting id for activate
				isActivated = HostelGroupHandler.getInstance().deleteHostelGroup(id, true, hlForm);
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.HOSTEL_GROUP_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setHostelEntryDetailsToRequest(hlForm,request);
		assignStudentListToRequest(hlForm);
		assignHostelGroupListToRequest(hlForm,request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_GROUP_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateHostelGroup");
		return mapping.findForward(CMSConstants.HOSTEL_GROUP);
	}

	/**  reset functionality
	 */
	public void resetStudentList(HostelGroupForm hlForm){
		log.info("Entering into resetStudentList");
		if(hlForm.getStudList()!=null && !hlForm.getStudList().isEmpty()){
			Iterator<ApplicationFormTO> it = hlForm.getStudList().iterator();
			while (it.hasNext()) {
				ApplicationFormTO applicationFormTO = (ApplicationFormTO) it.next();
				if(applicationFormTO.isSelected()){
					applicationFormTO.setDummySelected(true);
					applicationFormTO.setSelected(false);
				}
				else if(!applicationFormTO.isSelected()){
					applicationFormTO.setDummySelected(false);
				}
			}
		}
		log.info("exit resetStudentList");
	}
		
}
