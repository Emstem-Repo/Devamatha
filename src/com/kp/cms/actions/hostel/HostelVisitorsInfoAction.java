package com.kp.cms.actions.hostel;

import java.util.Date;
import java.util.HashMap;
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
import org.eclipse.birt.report.soapengine.api.Data;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.HostelVisitorsInfoForm;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.handlers.hostel.HostelVisitorsInfoHandler;
import com.kp.cms.helpers.hostel.FineEntryHelper;
import com.kp.cms.helpers.hostel.HostelVisitorsInfoHelper;
import com.kp.cms.to.hostel.HostelVisitorsInfoTo;
import com.kp.cms.transactions.hostel.IHostelVisitorsInfoTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelVisitorsInfoTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelVisitorsInfoAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AvailableSeatsAction.class);
	/**
	 * initAvailableSeats() to set required fields to form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelVisitorsInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HostelVisitorsInfoForm hostelVisitorsInfoForm=(HostelVisitorsInfoForm)form;
		reset(hostelVisitorsInfoForm);
		setRequiredDatatoForm(hostelVisitorsInfoForm, request);
		//setVisitorsListList(hostelVisitorsInfoForm);
		// /* code added by chandra
		if (hostelVisitorsInfoForm.getRgNoFromHlTransaction() != null && !hostelVisitorsInfoForm.getRgNoFromHlTransaction().isEmpty()) {
			hostelVisitorsInfoForm.setRegNo(hostelVisitorsInfoForm.getRgNoFromHlTransaction());
			hostelVisitorsInfoForm.setAcademicYear(hostelVisitorsInfoForm.getYear());
			if(hostelVisitorsInfoForm.getRgNoFromHlTransaction() != null && !hostelVisitorsInfoForm.getRgNoFromHlTransaction().isEmpty()
					&& (hostelVisitorsInfoForm.getYear()!=null && !hostelVisitorsInfoForm.getYear().isEmpty())){
				String regNo=hostelVisitorsInfoForm.getRgNoFromHlTransaction();
				String academicYear=hostelVisitorsInfoForm.getYear();
				HostelVisitorsInfoHelper.getInstance().getStudentDetails(hostelVisitorsInfoForm, regNo, academicYear);
				request.setAttribute("admOperation", "add");
			}
		}
		// */ code added by chandra
		return mapping.findForward(CMSConstants.INIT_HOSTEL_VISITORS_INFO);
	}
	/**
	 * set required data to form
	 * @param holidaysForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(HostelVisitorsInfoForm hostelVisitorsInfoForm,HttpServletRequest request) throws Exception{
		Date currentDate=new Date();
		hostelVisitorsInfoForm.setDate(CommonUtil.formatDates(currentDate));
		 Map<String, String> hostelMap = AvailableSeatsHandler.getInstance().getHostelMap();
		hostelVisitorsInfoForm.setHostelMap(hostelMap);
		Map<String,String> hoursMap=AvailableSeatsHandler.getInstance().getHoursMap();
		hostelVisitorsInfoForm.setHourMap(hoursMap);
		Map<String,String> minMap=AvailableSeatsHandler.getInstance().getMinMap();
		hostelVisitorsInfoForm.setMinMap(minMap);
	}
	/**
	 * method used to add  visitors information
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addVisitorsInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside action class. addPeriod Action");
		HostelVisitorsInfoForm hostelVisitorsInfoForm=(HostelVisitorsInfoForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hostelVisitorsInfoForm.validate(mapping, request);
		boolean isAdded=false;
		try {
			//mandatory message for start & end time
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setRequiredDatatoForm(hostelVisitorsInfoForm, request);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_VISITORS_INFO);
				
			}
			if(hostelVisitorsInfoForm.getInHours().trim().equals("00") || hostelVisitorsInfoForm.getInHours().trim().equals("0") || hostelVisitorsInfoForm.getInHours().trim().equals("")){
				errors.add("knowledgepro.attn.visitors.startHours",new ActionError("knowledgepro.attn.visitors.startHours"));
			}
			
			if(hostelVisitorsInfoForm.getOutHours().trim().equals("00") || hostelVisitorsInfoForm.getOutHours().trim().equals("0") || hostelVisitorsInfoForm.getOutHours().trim().equals("")){
				errors.add("knowledgepro.attn.visitors.endHours",new ActionError("knowledgepro.attn.visitors.endHours"));
			}
			if(errors!=null && !errors.isEmpty()){
				saveErrors(request, errors);
				setRequiredDatatoForm(hostelVisitorsInfoForm, request);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_VISITORS_INFO);
				
			}
			//time validation
			if(errors.isEmpty()){
				validateTime(hostelVisitorsInfoForm, errors);
			}	
			setUserId(request, hostelVisitorsInfoForm);  //setting user id to update last changed details
			IHostelVisitorsInfoTransaction transaction=HostelVisitorsInfoTransactionImpl.getInstance();
			String hlAdmissionId=transaction.getHlAdmissionId(hostelVisitorsInfoForm.getAcademicYear(),hostelVisitorsInfoForm.getRegNo());
			if(hlAdmissionId!=null && !hlAdmissionId.isEmpty()){
				isAdded = HostelVisitorsInfoHandler.getInstance().addVisitorsInformation(hostelVisitorsInfoForm,hlAdmissionId);
				if (isAdded) {
					// success .
					ActionMessage message = new ActionMessage("knowledgepro.attn.visitors.info.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					reset(hostelVisitorsInfoForm);
					
				} else {
					// failed
					errors.add("error", new ActionError("knowledgepro.attn.visitors.info.addfail"));
					saveErrors(request, errors);
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.hostel.checkin.not.valid"));
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
				hostelVisitorsInfoForm.setErrorMessage(msg);
				hostelVisitorsInfoForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("Leaving action class addPeriod");
		//setVisitorsListList(hostelVisitorsInfoForm);
		setRequiredDatatoForm(hostelVisitorsInfoForm, request);
		return mapping.findForward(CMSConstants.INIT_HOSTEL_VISITORS_INFO);

	}
	/**
	 * time validation. checking time format, start time should not be greater than end time.
	 * @param periodForm
	 * @param errors
	 */
	private void validateTime(HostelVisitorsInfoForm hostelVisitorsInfoForm, ActionErrors errors){
//		if (errors == null){
//			errors = new ActionErrors();}
			//time format checking
		if(CommonUtil.checkForEmpty(hostelVisitorsInfoForm.getInHours())){
			if(Integer.parseInt(hostelVisitorsInfoForm.getInHours())>=24){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}
	}
	/**
	 * reset the values
	 */
	public void reset(HostelVisitorsInfoForm hostelVisitorsInfoForm){
		hostelVisitorsInfoForm.setVisitorName(null);
		hostelVisitorsInfoForm.setContactNo(null);
		hostelVisitorsInfoForm.setDate(null);
		hostelVisitorsInfoForm.setInHours(null);
		hostelVisitorsInfoForm.setInMins(null);
		hostelVisitorsInfoForm.setOutHours(null);
		hostelVisitorsInfoForm.setOutMins(null);
		hostelVisitorsInfoForm.setHostelId(null);
		hostelVisitorsInfoForm.setAcademicYear(null);
		hostelVisitorsInfoForm.setRegNo(null);
		hostelVisitorsInfoForm.setHostelVisitorsInfoTosList(null);
		
	}
	/**
	 * getting visitors details
	 */
	public void setVisitorsListList(String regNo,HostelVisitorsInfoForm hostelVisitorsInfoForm)throws Exception{
		List<HostelVisitorsInfoTo> visitorsList=HostelVisitorsInfoHandler.getInstance().getVisitorsList(regNo,hostelVisitorsInfoForm);
		hostelVisitorsInfoForm.setHostelVisitorsInfoTosList(visitorsList);
	}
	/**
	 * deleting the visitors information
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteVisitorsInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		HostelVisitorsInfoForm hostelVisitorsInfoForm=(HostelVisitorsInfoForm)form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = HostelVisitorsInfoHandler.getInstance().deleteVisitorsInfo(hostelVisitorsInfoForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage("knowledgepro.hostel.visitorInfo.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				reset(hostelVisitorsInfoForm);
			} else {
				ActionMessage message = new ActionMessage("knowledgepro.hostel.visitorInfo.delete.fail");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			HttpSession session=request.getSession();
			setVisitorsListList(session.getAttribute("regNo").toString(),hostelVisitorsInfoForm);
			setRequiredDatatoForm(hostelVisitorsInfoForm, request);
		} catch (Exception e) {
			log.error("error submit biometric...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelVisitorsInfoForm.setErrorMessage(msg);
				hostelVisitorsInfoForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				hostelVisitorsInfoForm.setErrorMessage(msg);
				hostelVisitorsInfoForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete BiometricDetails ");
		return mapping.findForward(CMSConstants.EDIT_VISITORS_INFO);
	}
	/**
	 *search  visitors information
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchVisitorsInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside action class. addPeriod Action");
		HostelVisitorsInfoForm hostelVisitorsInfoForm=(HostelVisitorsInfoForm)form;
		hostelVisitorsInfoForm.setHostelVisitorsInfoTosList(null);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isAdded=false;
		try {
			//mandatory message for start & end time
			boolean flag=false;
			if(hostelVisitorsInfoForm.getRegNo()!=null && !hostelVisitorsInfoForm.getRegNo().isEmpty()){
				flag=false;
			}else{
				flag=true;
			}
			if(flag){
				errors.add("knowledgepro.hostel.regNo.select",new ActionError("knowledgepro.hostel.regNo.select"));
				saveErrors(request, errors);
				setRequiredDatatoForm(hostelVisitorsInfoForm, request);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_VISITORS_INFO);
			}else{
				setVisitorsListList(hostelVisitorsInfoForm.getRegNo(),hostelVisitorsInfoForm);
				HttpSession session=request.getSession();
				session.setAttribute("hostelId", hostelVisitorsInfoForm.getHostelId());
				session.setAttribute("regNo", hostelVisitorsInfoForm.getRegNo());
				hostelVisitorsInfoForm.setHostelId(null);
				hostelVisitorsInfoForm.setRegNo(null);
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
				hostelVisitorsInfoForm.setErrorMessage(msg);
				hostelVisitorsInfoForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("Leaving action class addPeriod");
		//setVisitorsListList(hostelVisitorsInfoForm);
		setRequiredDatatoForm(hostelVisitorsInfoForm, request);
		return mapping.findForward(CMSConstants.EDIT_VISITORS_INFO);
	}
	/**
	 * editing the visitors information
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editVisitorsInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelVisitorsInfoForm hostelVisitorsInfoForm=(HostelVisitorsInfoForm)form;
		hostelVisitorsInfoForm.setHostelVisitorsInfoTosList(null);
		hostelVisitorsInfoForm.reset();
		log.debug("Entering editVisitorsInfo ");
		try{
			setRequiredDatatoForm(hostelVisitorsInfoForm, request);
			HostelVisitorsInfoHandler.getInstance().editVisitorsInfo(hostelVisitorsInfoForm);
			request.setAttribute("Operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editVisitorsInfo ");
		}catch (Exception e) {
			log.error("error in editing editVisitorsInfo...", e);
			String msg = super.handleApplicationException(e);
			hostelVisitorsInfoForm.setErrorMessage(msg);
			hostelVisitorsInfoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		HttpSession session=request.getSession();
		setVisitorsListList(session.getAttribute("regNo").toString(),hostelVisitorsInfoForm);
		return mapping.findForward(CMSConstants.EDIT_VISITORS_INFO);
	}
	/**
	 * update the visitors information
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateVisitorsInformation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering updateVisitorsInformation");
		HostelVisitorsInfoForm hostelVisitorsInfoForm=(HostelVisitorsInfoForm)form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = hostelVisitorsInfoForm.validate(mapping, request);
		try {
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setRequiredDatatoForm(hostelVisitorsInfoForm, request);
				return mapping.findForward(CMSConstants.EDIT_VISITORS_INFO);
				
			}else{
					boolean isUpdated = HostelVisitorsInfoHandler.getInstance().updateVisitorsInformation(hostelVisitorsInfoForm);
						if (isUpdated) {
							ActionMessage message = new ActionMessage("knowledgepro.hostel.visitorInfo.update.success");
							messages.add("messages", message);
							saveMessages(request, messages);
							reset(hostelVisitorsInfoForm);
							request.setAttribute("Operation", "add");
						}else {
							ActionMessage message = new ActionMessage("knowledgepro.hostel.visitorInfo.update.fail");
							messages.add("messages", message);
							saveMessages(request, messages);
						}
						HttpSession session=request.getSession();
						setVisitorsListList(session.getAttribute("regNo").toString(),hostelVisitorsInfoForm);
						setRequiredDatatoForm(hostelVisitorsInfoForm, request);
			}
		} catch (Exception e) {
			log.error("error submit updateVisitorsInformation...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelVisitorsInfoForm.setErrorMessage(msg);
				hostelVisitorsInfoForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				hostelVisitorsInfoForm.setErrorMessage(msg);
				hostelVisitorsInfoForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. updateVisitorsInformation ");
		return mapping.findForward(CMSConstants.EDIT_VISITORS_INFO);
	}
	
}
