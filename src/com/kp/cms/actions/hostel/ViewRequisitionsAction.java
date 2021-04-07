package com.kp.cms.actions.hostel;
		
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.hostel.ViewRequisitionsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.RequisitionHandler;
import com.kp.cms.helpers.hostel.RequisitionHelper;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RequisitionsTo;
import com.kp.cms.to.hostel.VRequisitionsTO;
		
public class ViewRequisitionsAction extends BaseDispatchAction {
	private static final Log log = LogFactory
					.getLog(ViewRequisitionsAction.class);
			// private static final String ADDRESS = "address";
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewRequisitions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initViewRequisitions. of ViewRequisitionsAction");
		ViewRequisitionsForm viewRequisitionsForm = (ViewRequisitionsForm) form;
		try {
			HttpSession session = request.getSession(false);
			session.removeAttribute("viewRequisition");
			viewRequisitionsForm.resetFields( mapping,request);
			setHostelListToRequest(request,viewRequisitionsForm);
			setUserId(request, viewRequisitionsForm);
		} catch (Exception e) {
			log.error("Error in initViewRequisitions in ViewRequisitionsAction",e);
			String msg = super.handleApplicationException(e);
			viewRequisitionsForm.setErrorMessage(msg);
			viewRequisitionsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("leaving into initViewRequisitions. of ViewRequisitionsAction");
		return mapping.findForward(CMSConstants.VIEW_REQUISITIONS);
	}
	
	
	
	/**
	 * @param request
	 * @param viewRequisitionsForm
	 * @throws Exception
	 */
	public void setHostelListToRequest(HttpServletRequest request,ViewRequisitionsForm viewRequisitionsForm) throws Exception {
		log.debug("inside setHostelListToRequest");
		 List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		viewRequisitionsForm.setHostelList(hostelList);
		if(viewRequisitionsForm.getHostelId1()!=null && viewRequisitionsForm.getHostelId1().length()!=0)
		{
			Map<Integer,String>roomTypeMap=CommonAjaxHandler.getInstance().getRoomTypesByHostel(Integer.parseInt(viewRequisitionsForm.getHostelId1()));
			request.setAttribute("roomTypeMap", roomTypeMap);
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitViewRequisitions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitViewRequisitions. of ViewRequisitionsAction");	
	ViewRequisitionsForm viewRequisitionsForm = (ViewRequisitionsForm)form;
	HttpSession session = request.getSession(false);
	 ActionMessages errors = viewRequisitionsForm.validate(mapping, request);
	if(session.getAttribute("address")==null){
		try {
			if(errors.isEmpty()){
				List<VRequisitionsTO> list = RequisitionHandler.getInstance().getRequisitionDetails(viewRequisitionsForm);
				if(list==null ||list.isEmpty() ){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					viewRequisitionsForm.resetFields(mapping, request);
					setHostelListToRequest(request,viewRequisitionsForm);
					return mapping.findForward(CMSConstants.VIEW_REQUISITIONS);
				}
					viewRequisitionsForm.setRequisitionsList(list);
			}
			else{
				addErrors(request, errors);
				//Sets programType and program to form bean
				setHostelListToRequest(request, viewRequisitionsForm);
				return mapping.findForward(CMSConstants.VIEW_REQUISITIONS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in showRequisitions in ViewRequisitionsAction",e);
			String msg = super.handleApplicationException(e);
			viewRequisitionsForm.setErrorMessage(msg);
			viewRequisitionsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}	
	log.info("Leaving into submitViewRequisitions. of ViewRequisitionsAction");
		return mapping.findForward(CMSConstants.SHOW_REQUISITIONS);
	}		
		
	
	// approve method 
	public ActionForward approveRequisitions(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {			
		log.debug("Entering approveRequisitions ");
	//	ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessages errors = new ActionErrors();
		ViewRequisitionsForm viewRequisitionsForm = (ViewRequisitionsForm)form;
		viewRequisitionsForm.resetFields( mapping,request);
		List<VRequisitionsTO> vRequisitionsTO =RequisitionHelper.getapprvieIds(viewRequisitionsForm);
		if(vRequisitionsTO.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.SELECT_ATLEAST_ONE));
			saveErrors(request, errors);
		return mapping.findForward(CMSConstants.SHOW_REQUISITIONS);
		}
	
		if(errors.isEmpty()){
			try {
		
		boolean suceess=RequisitionHandler.getInstance().getapprvieIds(viewRequisitionsForm);
		if(suceess)
		{
			ActionMessage message = new ActionMessage(CMSConstants.UPDATED_SUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
			viewRequisitionsForm.clear();
			setHostelListToRequest(request,viewRequisitionsForm);
		}
		
		} catch (Exception e) {
		log.debug(e.getMessage());
		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_APPROVE__FAILURE));
		saveErrors(request,errors);
		return mapping.findForward(CMSConstants.SHOW_REQUISITIONS);
		}
		}
		log.debug("Leaving markAsCancel ");
		return mapping.findForward(CMSConstants.VIEW_REQUISITIONS);
		}
	
	
	// to approve for particular Room status from view Details 
	public ActionForward approveRequisitionsStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {			
	log.debug("Entering markAsCancel ");
	ActionMessages messages = new ActionMessages();
	
	ViewRequisitionsForm viewRequisitionsForm = (ViewRequisitionsForm)form;
	ActionErrors errors = viewRequisitionsForm.validate(mapping, request);
	if(errors.isEmpty()){
	try {
		
	boolean suceess=RequisitionHandler.getInstance().getapprvieIdforSingle(viewRequisitionsForm);
	if(suceess)
	  {	ActionMessage message = new ActionMessage(CMSConstants.UPDATED_SUCCESS);
		messages.add(CMSConstants.MESSAGES, message);
		saveMessages(request, messages);
		viewRequisitionsForm.clear();
		setHostelListToRequest(request,viewRequisitionsForm);
				}
	} catch (Exception e) {
	log.debug(e.getMessage());
	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_APPROVE__FAILURE));
	saveErrors(request,errors);
	return mapping.findForward(CMSConstants.SHOW_REQUISITIONS);
	}
	}else{
		addErrors(request, errors);
		//Sets programType and program to form bean
		setHostelListToRequest(request, viewRequisitionsForm);
		return mapping.findForward(CMSConstants.REQUISITION_POPUP_RESULT);
		
	}
	request.getSession().removeAttribute("viewRequisition");
	viewRequisitionsForm.resetFields( mapping,request);
	setHostelListToRequest(request,viewRequisitionsForm);
	log.debug("Leaving markAsCancel ");
	return mapping.findForward(CMSConstants.VIEW_REQUISITIONS);
	}
	// to pop the window when user click on view
	public ActionForward getrequisitionDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			ViewRequisitionsForm viewRequisitionsForm = (ViewRequisitionsForm) form;
			try
			{
				request.removeAttribute("studentDetails");
				int hlAppId=viewRequisitionsForm.getHlAppId();
				RequisitionsTo studentDetails = RequisitionHandler.getInstance().getRequisitionDetailsToShow(hlAppId,viewRequisitionsForm,request);
				if(studentDetails!=null)
				{
					viewRequisitionsForm.setRto(studentDetails);
					viewRequisitionsForm.setStatus1(studentDetails.getStatus());
					viewRequisitionsForm.setHostelId1(Integer.toString(studentDetails.getHostelId()));
					viewRequisitionsForm.setRoomtype(Integer.toString(studentDetails.getRoomTypeId()));
					setHostelListToRequest(request, viewRequisitionsForm);
				}
			}
			catch (Exception e) 
			{
				if (e instanceof ApplicationException) 
				{
					String msg = super.handleApplicationException(e);
					viewRequisitionsForm.setErrorMessage(msg);
					viewRequisitionsForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				else
				{
					throw e;
				}
			}
			return mapping.findForward(CMSConstants.REQUISITION_POPUP_RESULT);
		}

}
