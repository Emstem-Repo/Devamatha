package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelLeaveApprovalForm;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelLeaveApprovalHandler;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;
import com.kp.cms.utilities.CommonUtil;

public class HostelLeaveApprovalAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelLeaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveApprovalForm objForm = (HostelLeaveApprovalForm)form;
		objForm.reset();
		HttpSession session = request.getSession(false);
		try{
			session.setAttribute("blockMap", null);
			session.setAttribute("unitMap", null);
			setRequiredDataToForm(objForm);
		}catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setRequiredDataToForm(HostelLeaveApprovalForm objForm) throws Exception{
		Map<String,String> hostelMap = AvailableSeatsHandler.getInstance().getHostelMap();
		Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
		objForm.setHostelMap(hostelMap);
		objForm.setCourseMap(courseMap);
		objForm.setFromDate(CommonUtil.getTodayDate());
		objForm.setToDate(CommonUtil.getTodayDate());
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHostelLeaveApprovalDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HostelLeaveApprovalForm objForm = (HostelLeaveApprovalForm)form;
		objForm.clearFields();
		ActionErrors errors = objForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		session.setAttribute("blockMap", null);
		session.setAttribute("unitMap", null);
		try{
			if(errors.isEmpty()){
				List<HostelLeaveApprovalTo> hostelLeaveApprovalToList = HostelLeaveApprovalHandler.getInstance().getLeaveApprovalDetails(objForm);
				 if(hostelLeaveApprovalToList!=null && !hostelLeaveApprovalToList.isEmpty()){
					 objForm.setHostelLeaveApprovalTo(hostelLeaveApprovalToList);
				 }else {
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
					//return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
				 }
			}else{
				saveErrors(request, errors);
				//return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
			}
		}catch(Exception exception){
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		Map<Integer,String> blockMap = HostelEntryHandler.getInstance().getBlocks(objForm.getHostelId());
		Map<Integer,String> unitMap = HostelEntryHandler.getInstance().getUnits(objForm.getBlockId());
		session.setAttribute("blockMap", blockMap);
		session.setAttribute("unitMap", unitMap);
		return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetailsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HostelLeaveApprovalForm objForm = (HostelLeaveApprovalForm)form;
		/*objForm.setStudentName(null);
		objForm.setClassName(null);*/
		objForm.setApprovalTosList(null);
		try{
				Map<String,List<HostelLeaveApprovalTo>> leaveApprovalMapDetails = objForm.getLeaveApprovalMap();
				if(leaveApprovalMapDetails!=null && !leaveApprovalMapDetails.isEmpty()){
					List<HostelLeaveApprovalTo> leaveApplicationList = null;
					if(request.getParameter("propertyName").equalsIgnoreCase("NoofApplication")){
						leaveApplicationList =  HostelLeaveApprovalHandler.getInstance().getDetailsList(leaveApprovalMapDetails,objForm,"NoofApplication");
					}else if(request.getParameter("propertyName").equalsIgnoreCase("Approved")){
						leaveApplicationList =  HostelLeaveApprovalHandler.getInstance().getDetailsList(leaveApprovalMapDetails,objForm,"Approved");
					}else if(request.getParameter("propertyName").equalsIgnoreCase("Rejected")){
						leaveApplicationList =  HostelLeaveApprovalHandler.getInstance().getDetailsList(leaveApprovalMapDetails,objForm,"Rejected");
					}else if(request.getParameter("propertyName").equalsIgnoreCase("Cancelled")){
						leaveApplicationList =  HostelLeaveApprovalHandler.getInstance().getDetailsList(leaveApprovalMapDetails,objForm,"Cancelled");
					}
					if(leaveApplicationList!=null && !leaveApplicationList.isEmpty()){
						objForm.setApprovalTosList(leaveApplicationList);
					}
				}
		}catch(Exception exception){
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_APPROVAL_LEAVE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitApproveLeaveDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveApprovalForm objForm = (HostelLeaveApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		setUserId(request, objForm);
		try{
			objForm.setNotSelectAtLeastOne(false);
			boolean isSent = HostelLeaveApprovalHandler.getInstance().sendSMSAndEmailToStudents(objForm,"Approve");
			if(isSent){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.mail.confirm.label"));
				saveMessages(request, messages);
			}else{
				if(objForm.isNotSelectAtLeastOne()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.select.atleastone"));
					saveErrors(request, errors);
					getUnCheckedDetails(objForm);
					objForm.setFlag(false);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.sending.mail.fail"));
					saveErrors(request, errors);
					getUnCheckedDetails(objForm);
					objForm.setFlag(false);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
				}
			}
			getUnCheckedDetails(objForm);
		}catch(Exception exception){
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void getUnCheckedDetails(HostelLeaveApprovalForm objForm) throws Exception{
		List<HostelLeaveApprovalTo> hostelLeaveApprovalToList = HostelLeaveApprovalHandler.getInstance().getLeaveApprovalDetails(objForm);
		if(hostelLeaveApprovalToList!=null && !hostelLeaveApprovalToList.isEmpty()){
			objForm.setHostelLeaveApprovalTo(hostelLeaveApprovalToList);
		}else {
			objForm.setHostelLeaveApprovalTo(null);
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
	public ActionForward submitRejectLeaveDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelLeaveApprovalForm objForm = (HostelLeaveApprovalForm)form;
		setUserId(request, objForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
				objForm.setFlag(false);
			if(objForm.getRejectReason()==null || objForm.getRejectReason().isEmpty()){
				objForm.setFlag(true);
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.reason.required"));
				saveErrors(request, errors);
				getUnCheckedDetails(objForm);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
			}
			boolean isSent = HostelLeaveApprovalHandler.getInstance().sendSMSAndEmailToStudents(objForm,"Reject");
			if(isSent){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.employee.mail.confirm.label"));
				saveMessages(request, messages);
			}else{
				if(objForm.isNotSelectAtLeastOne()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.select.atleastone"));
					saveErrors(request, errors);
					getUnCheckedDetails(objForm);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.sending.mail.fail"));
					saveErrors(request, errors);
					getUnCheckedDetails(objForm);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
				}
			}
			getUnCheckedDetails(objForm);
			objForm.setRejectReason(null);
		}catch(Exception exception){
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_LEAVE_APPROVAL);
	}
}
