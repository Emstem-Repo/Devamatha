package com.kp.cms.actions.hostel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javassist.expr.Instanceof;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.handlers.hostel.HlAdmissionHandler;
import com.kp.cms.helpers.hostel.HlAdmissionHelper;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.transactions.hostel.IHladmissionTransaction;
import com.kp.cms.transactionsimpl.hostel.HlAdmissionImpl;

public class HlAdmissionAction extends BaseDispatchAction{
	private static final Log log=LogFactory.getLog(HlAdmissionAction.class);

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHlAdmission(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initHlAdmission method in HlAdmissionAction class.");
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		hlAdmissionForm.setCheckAdmission("TRUE");
		hlAdmissionForm.setAdmissionType("New");
	    hlAdmissionForm.reset1();
		setHostelToRequest(request,hlAdmissionForm);
		hlAdmissionForm.setPrint(null);
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		setRoomTypeToRequest(request,hlAdmissionForm);
		log.debug("Leaving inithlAdmission");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHlAdmissionFromAdmission(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initHlAdmission method in HlAdmissionAction class.");
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		hlAdmissionForm.setCheckAdmission(null);
		hlAdmissionForm.setHlAdmissionList(null);
		hlAdmissionForm.reset();
		if(hlAdmissionForm.getHostelApplNo()!=null){
			hlAdmissionForm.setApplNo(hlAdmissionForm.getHostelApplNo());
			String name = CommonAjaxHandler.getInstance().getStudentNameInHostel(null, hlAdmissionForm.getHostelApplNo(),request);
			String[] arr=name.split(";");
			hlAdmissionForm.setStudentName(arr[0]);
			hlAdmissionForm.setTempStudentName(arr[0]);
			hlAdmissionForm.setCheckRegNo(hlAdmissionForm.getHostelApplNo());
		}
		setHostelToRequest(request,hlAdmissionForm);
		hlAdmissionForm.setPrint(null);
		setRoomTypeToRequest(request,hlAdmissionForm);
		log.debug("Leaving inithlAdmission");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	
	
	private void setBedByRoom(HttpServletRequest request,HlAdmissionForm hlAdmissionForm) throws Exception{
		log.debug("start setRoomTypeToRequest");
		Map<Integer, String> bedList=null;
		if(hlAdmissionForm.getRoomId()!=null && !hlAdmissionForm.getRoomId().isEmpty()){
			bedList = CommonAjaxHandler.getInstance() .getBedByRoomId(Integer.parseInt(hlAdmissionForm.getRoomId()));
		}
		request.setAttribute("bedMap", bedList);
		log.debug("exit setRoomTypeToRequest");
}

	private void setRoomByRoomType(HttpServletRequest request,HlAdmissionForm hlAdmissionForm) throws Exception{
		log.debug("start setRoomTypeToRequest");
		Map<Integer, String> roomList=null;
		if(hlAdmissionForm.getRoomTypeName()!=null && !hlAdmissionForm.getRoomTypeName().isEmpty()){
			roomList = CommonAjaxHandler.getInstance() .setRoomByRoomType(Integer.parseInt(hlAdmissionForm.getRoomTypeName()));
		}
		request.setAttribute("roomMap", roomList);
		log.debug("exit setRoomTypeToRequest");
}

	/**
	 * @param request 
	 * @param hlAdmissionForm 
	 * @throws Exception
	 */
	public void setHostelToRequest(HttpServletRequest request, HlAdmissionForm hlAdmissionForm)throws Exception
	{
			log.debug("start setHostelEntryDetailsToRequest");
			Map<Integer, String> hostelmap=null;
			IHladmissionTransaction transaction = new HlAdmissionImpl();
			String gender=transaction.getGenderPersonaldata(hlAdmissionForm);
			if(gender!=null && !gender.isEmpty()){
			hostelmap = CommonAjaxHandler.getInstance().getHostelBygender(gender);
			}else{
				hostelmap = CommonAjaxHandler.getInstance().getHostel();
			}
			request.setAttribute("hostelmap", hostelmap);
			log.debug("exit setHostelEntryDetailsToRequest");
	}
	/**
	 * @param request
	 * @param hlAdmissionForm 
	 * @throws Exception
	 */
	public void setRoomTypeToRequest(HttpServletRequest request, HlAdmissionForm hlAdmissionForm)throws Exception
	{
			log.debug("start setRoomTypeToRequest");
			Map<Integer, String> roomTypeList=null;
			if(hlAdmissionForm.getHostelName()!=null && !hlAdmissionForm.getHostelName().isEmpty()){
				roomTypeList = CommonAjaxHandler.getInstance() .getRoomTypeByHostelBYstudent(Integer.parseInt(hlAdmissionForm.getHostelName()));
			}
			request.setAttribute("roomTypeMap", roomTypeList);
			log.debug("exit setRoomTypeToRequest");
	}
	/**
	 * @param HlAdmissionForm
	 * @param errors 
	 * @param request 
	 * @param mode 
	 */
	public void setRequestedDateToForm(HlAdmissionForm hlAdmissionForm, ActionErrors errors, HttpServletRequest request, String mode) throws Exception{
		List<HlAdmissionTo> hlAdmissionList=HlAdmissionHandler.getInstance().gethlAdmissionList(hlAdmissionForm,errors,request,mode);
		hlAdmissionForm.setHlAdmissionList(hlAdmissionList);
	}
	
	public ActionForward HostelAdmissionSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
	throws Exception{
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		ActionErrors errors=new ActionErrors();
		hlAdmissionForm.setPrint(null);
		String mode="search";
		if((hlAdmissionForm.getStudentName()==null || hlAdmissionForm.getStudentName().isEmpty()) &&
		   ( hlAdmissionForm.getRegNo()==null || hlAdmissionForm.getRegNo().isEmpty()) &&
			(hlAdmissionForm.getApplNo()==null || hlAdmissionForm.getApplNo().isEmpty()) &&
			(hlAdmissionForm.getHostelName()==null || hlAdmissionForm.getHostelName().isEmpty())){
			errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.regapplicaname"));
		}else{
			setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
		}
	 hlAdmissionForm.setWaitingList(false);
	 hlAdmissionForm.setStudentInWaitingList(false);
	 addErrors(request, errors);
	 setHostelToRequest(request,hlAdmissionForm);
	 setRoomTypeToRequest(request,hlAdmissionForm);
	return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward addHostelAdmission(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
	throws Exception{
		log.info("call of addhlAdmission method in InvSubCategogyAction class.");
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		setUserId(request,hlAdmissionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		String mode="add";
		hlAdmissionForm.setPrint(null);
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		HttpSession session=request.getSession();
		hlAdmissionForm.setId(0);
		if(errors==null || errors.isEmpty()){
			if((hlAdmissionForm.getStudentName()==null || hlAdmissionForm.getStudentName().isEmpty()) &&
					   ( hlAdmissionForm.getRegNo()==null || hlAdmissionForm.getRegNo().isEmpty()) &&
						( hlAdmissionForm.getApplNo()==null || hlAdmissionForm.getApplNo().isEmpty())){
						errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.regapplicaname"));
			}
			if(hlAdmissionForm.getHostelName() == null || hlAdmissionForm.getHostelName().isEmpty()){
				errors.add("error", new ActionError( "knowledgepro.hostel.hlAdmission.name"));
			}
			if(hlAdmissionForm.getRoomTypeName() == null || hlAdmissionForm.getRoomTypeName().isEmpty()){
				errors.add("error", new ActionError( "knowledgepro.hostel.hlAdmission.roomtype"));
			}
		}if(errors!=null && !errors.isEmpty()){
			saveErrors(request, errors);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
		}
		errors = hlAdmissionForm.validate(mapping, request);
		if(errors==null || errors.isEmpty()){
		getRoomAvailability(hlAdmissionForm,errors);
		}
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				String gender=HlAdmissionHandler.getInstance().genderCheck(hlAdmissionForm,errors,session);
				if(gender!=null && !gender.isEmpty()){
					if(gender.equalsIgnoreCase("NoData")){
						errors.add("error", new ActionError( "knowledgepro.hostel.student.notadmitted"));
					}else{
					errors.add("error", new ActionError( "knowledgepro.hostel.gender.selection",gender));
					}
					addErrors(request, errors);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
				}
				boolean isDuplicate=HlAdmissionHandler.getInstance().duplicateCheck(hlAdmissionForm,errors,session);
				if(!isDuplicate){
				IHladmissionTransaction transaction=new HlAdmissionImpl();
				List<HlAdmissionBookingWaitingBo> list=transaction.checkStudentsAreThereInWaitingList(hlAdmissionForm);
				String WaitingId=(String) session.getAttribute("StudentInWaitingListId");
				String priorityNo =(String) session.getAttribute("studentWaitingListPriorityNumber");
				if(list!=null && !list.isEmpty())
				{
				if(priorityNo!=null && !priorityNo.isEmpty())
				{
				isAdded = HlAdmissionHandler.getInstance().addhlAdmission(hlAdmissionForm,"Add");
				if (isAdded) {
					//String WaitingId=(String) session.getAttribute("StudentInWaitingListId");
					if(WaitingId!=null)
					{
					HlAdmissionHandler.getInstance().resetStudentInWaitingList(Integer.parseInt(WaitingId),hlAdmissionForm);
					}
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.inventory.hlAdmission.addsuccess"));
					saveMessages(request, messages);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				 // not Required Now
				//	hlAdmissionForm.setPrint("print");
					hlAdmissionForm.reset();
					} else {
					errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.addfailure"));
					addErrors(request, errors);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				}
				}
				else
				{
				errors.add("error", new ActionError( "knowledgepro.Hostel.Admission.seatAvailable.NotInWaitingList"));
				addErrors(request, errors);
				setHostelToRequest(request,hlAdmissionForm);
				setRoomTypeToRequest(request,hlAdmissionForm);
				setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				}
				}
				else{
					isAdded = HlAdmissionHandler.getInstance().addhlAdmission(hlAdmissionForm,"Add");
					if (isAdded) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.inventory.hlAdmission.addsuccess"));
						saveMessages(request, messages);
						setHostelToRequest(request,hlAdmissionForm);
						setRoomTypeToRequest(request,hlAdmissionForm);
						setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
						hlAdmissionForm.reset();
						} else {
						errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.addfailure"));
						addErrors(request, errors);
						setHostelToRequest(request,hlAdmissionForm);
						setRoomTypeToRequest(request,hlAdmissionForm);
						setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
					}
				}
				}
				else{
					addErrors(request, errors);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				}			
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				hlAdmissionForm.setErrorMessage(msg);
				hlAdmissionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
			return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
		}
		
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	
	private void getRoomAvailability(HlAdmissionForm hlAdmissionForm, ActionErrors errors) throws Exception {
		HlAdmissionHandler.getInstance().getRoomAvailability(hlAdmissionForm,errors);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editHostelAdmission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		 ActionErrors errors = hlAdmissionForm.validate(mapping, request);
		log.debug("Entering ValuatorCharges ");
		hlAdmissionForm.setPrint(null);
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		try {
			HlAdmissionHandler.getInstance().edithlAdmission(hlAdmissionForm,errors);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			if(errors.isEmpty()){
			hlAdmissionForm.setEditCheck("edit");
			request.setAttribute("hlAdmission", "edit");
			}
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		 saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHostelAdmission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatevaluatorCharges Action");
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		HttpSession session=request.getSession();
		hlAdmissionForm.setPrint(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hlAdmissionForm.validate(mapping, request);
		boolean isUpdated = false;
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);	
		String mode="update";
		if(hlAdmissionForm.getRoomTypeId()!=Integer.parseInt(hlAdmissionForm.getRoomTypeName())){
		if(errors.isEmpty()){
		getRoomAvailability(hlAdmissionForm,errors);
		}
		}
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				hlAdmissionForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				HlAdmissionHandler.getInstance().edithlAdmission(hlAdmissionForm,errors);
				if(errors!=null && !errors.isEmpty()){
				request.setAttribute("hlAdmission", "edit");
				} saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
			}
			setUserId(request, hlAdmissionForm); // setting user id to update
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			String gender=HlAdmissionHandler.getInstance().genderCheck(hlAdmissionForm,errors,session);
			if(gender!=null && !gender.isEmpty()){
				errors.add("error", new ActionError( "knowledgepro.hostel.gender.selection",gender));
				addErrors(request, errors);
				request.setAttribute("hlAdmission", "edit");
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
			}
			boolean isDuplicate=HlAdmissionHandler.getInstance().duplicateCheck(hlAdmissionForm,errors,session);
			if(!isDuplicate){
				isUpdated = HlAdmissionHandler.getInstance().updatehlAdmission(hlAdmissionForm,"Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.inventory.hlAdmission.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				hlAdmissionForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.inventory.hlAdmission.update.failed"));
				addErrors(request, errors);
				setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
			}}
			else{
				request.setAttribute("hlAdmission", "edit");
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in edit valuatorcharges", e);
			String msg = super.handleApplicationException(e);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
			hlAdmissionForm.reset();
			request.setAttribute("HlAdmission", "edit");
			return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
		}
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHostelAdmission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
		hlAdmissionForm.setPrint(null);
		ActionErrors errors = new ActionErrors();
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		if(hlAdmissionForm.getCancelReason()==null || hlAdmissionForm.getCancelReason().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.Hostel.cancel.reason"));
		}if(errors.isEmpty()){
		try {
			boolean isDeleted =HlAdmissionHandler.getInstance().deletehlAdmission(hlAdmissionForm,request);
			IHladmissionTransaction transaction = new HlAdmissionImpl();
			long count=transaction.getWaitingListPriorityNo(hlAdmissionForm);
             String s1=String.valueOf(count);
			if (isDeleted) {
				ActionMessage message = new ActionMessage("knowledgepro.inventory.hlAdmission.delete.success",s1);
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage("knowledgepro.inventory.hlAdmission.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			
		} catch (Exception e) {
			log.error("error submit valuatorCharges...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hlAdmissionForm.setErrorMessage(msg);
				hlAdmissionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				hlAdmissionForm.setErrorMessage(msg);
				hlAdmissionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}}else{
			saveErrors(request, errors);
			HlAdmissionHandler.getInstance().edithlAdmission(hlAdmissionForm,errors);
			 saveErrors(request, errors);
			log.info("Exit from printConsolidatedCollectionLedger");
			return mapping.findForward("getCancelReason");
		}
		setHostelToRequest(request,hlAdmissionForm);
		setRoomTypeToRequest(request,hlAdmissionForm);
		hlAdmissionForm.reset();
		log.debug("Action class. Delete valuatorCharges ");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	/**
	 * printing the Collection Ledger Report Result
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printHostelAdmission(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered into printConsolidatedCollectionLedger");
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		if(hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()){
			hlAdmissionForm.setCheckRegNo(hlAdmissionForm.getRegNo());
		}else if(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()){
			hlAdmissionForm.setCheckRegNo(hlAdmissionForm.getApplNo());
		}
		if(hlAdmissionForm.getId()>0){
		HlAdmissionHandler.getInstance().printHostelAdmission(hlAdmissionForm,request);
		}
		hlAdmissionForm.reset();
		log.info("Exit from printConsolidatedCollectionLedger");
		return mapping.findForward("printHostelAdmission");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCancelReason(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered into printConsolidatedCollectionLedger");
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		ActionErrors errors = new ActionErrors();
		hlAdmissionForm.setCancelReason(null);
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		HlAdmissionHandler.getInstance().edithlAdmission(hlAdmissionForm,errors);
		 saveErrors(request, errors);
		log.info("Exit from printConsolidatedCollectionLedger");
		return mapping.findForward("getCancelReason");
	}
	
	public ActionForward backHlAdmission(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initHlAdmission method in HlAdmissionAction class.");
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		setHostelToRequest(request,hlAdmissionForm);
		hlAdmissionForm.setPrint(null);
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		setRoomTypeToRequest(request,hlAdmissionForm);
		 List<HlAdmissionTo> hlAdmissionToList=HlAdmissionHelper.getInstance().backTosToTOs(hlAdmissionForm);
		 hlAdmissionForm.setHlAdmissionList(hlAdmissionToList);
		 request.getSession().setAttribute("SelectedData", hlAdmissionToList);
		 hlAdmissionForm.reset();
		log.debug("Leaving inithlAdmission");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}

	
	public ActionForward addHostelAdmissionInWaitingList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.info("call of addHostelAdmissionInWaitingList method in InvSubCategogyAction class.");
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		setUserId(request,hlAdmissionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		String mode="add";
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		hlAdmissionForm.setPrint(null);
		HttpSession session=request.getSession();
		if(errors==null || errors.isEmpty()){
			if((hlAdmissionForm.getStudentName()==null || hlAdmissionForm.getStudentName().isEmpty()) &&
					   ( hlAdmissionForm.getRegNo()==null || hlAdmissionForm.getRegNo().isEmpty()) &&
						( hlAdmissionForm.getApplNo()==null || hlAdmissionForm.getApplNo().isEmpty())){
						errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.regapplicaname"));
		}}if(errors!=null && !errors.isEmpty()){
			saveErrors(request, errors);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
		}
		errors = hlAdmissionForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				String gender=HlAdmissionHandler.getInstance().genderCheck(hlAdmissionForm,errors,session);
				if(gender!=null && !gender.isEmpty()){
					if(gender.equalsIgnoreCase("NoData")){
						errors.add("error", new ActionError( "knowledgepro.hostel.student.notadmitted"));
					}else{
					errors.add("error", new ActionError( "knowledgepro.hostel.gender.selection",gender));
					}
					addErrors(request, errors);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
				}
				boolean isDuplicate=HlAdmissionHandler.getInstance().duplicateCheckWaitingList(hlAdmissionForm,errors,session);
				if(!isDuplicate){
				isAdded = HlAdmissionHandler.getInstance().addhlAdmissionWaiting(hlAdmissionForm,"Add");
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.hostel.admission.waiting.list.addSuccess"));
					saveMessages(request, messages);
					setHostelToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
					hlAdmissionForm.reset();
					} else {
					errors.add("error", new ActionError( "knowledgepro.hostel.admission.waiting.list.addFailure"));
					addErrors(request, errors);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				}}
				else{
					addErrors(request, errors);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				}			
				}
 			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				hlAdmissionForm.setErrorMessage(msg);
				hlAdmissionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
			return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
		}
		
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
		
	public ActionForward getWaitingListPriorityNo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		if(hlAdmissionForm.getEditCheck()!=null && !hlAdmissionForm.getEditCheck().isEmpty()){
			request.setAttribute("hlAdmission", "edit");
		}
		try {
			int priorityNo=HlAdmissionHandler.getInstance().getWaitingListPriorityNo(hlAdmissionForm);
			if(priorityNo>0)
			{
				hlAdmissionForm.setWatingPriorityNo(String.valueOf(priorityNo));
				hlAdmissionForm.setWaitingList(true);
			}
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			Map<Integer, String> roomTypeMap=CommonAjaxHandler.getInstance().getRoomTypeByHostelBYstudent(Integer.parseInt(hlAdmissionForm.getHostelId()));
			request.setAttribute("roomTypeMap", roomTypeMap);
		} catch (Exception exception) {
			log.error("Error occured in caste Entry Action", exception);
			String msg = super.handleApplicationException(exception);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
		
	}
	/**
	 * initUploadHostelDetails
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadRoomDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initHlAdmission method in HlAdmissionAction class.");
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		setRequiredDatatoForm(hlAdmissionForm,request);
		log.debug("Leaving inithlAdmission");
		return mapping.findForward(CMSConstants.INIT_UPLOAD_ROOM_DETAILS);
	}
	/**
	 * set required data to form
	 * @param holidaysForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(HlAdmissionForm hlAdmissionForm,HttpServletRequest request) throws Exception{
		 Map<String, String> hostelMap = AvailableSeatsHandler.getInstance().getHostelMap();
		hlAdmissionForm.setHostelMap(hostelMap);
	}
/**
 * uploadRoomDetails
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 */
public ActionForward uploadRoomDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Entered UploadChildrenDetails input");
	HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
	ActionMessages messages=new ActionMessages();
	ActionMessage message1=null;
	setUserId(request, hlAdmissionForm);
	ActionErrors errors=hlAdmissionForm.validate(mapping, request);
	String enterCorrect="";
	try{
		if(!errors.isEmpty())
		{
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_UPLOAD_ROOM_DETAILS);
		}
		if(hlAdmissionForm.getTheFile()==null)
		{
			ActionMessage message=new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
			errors.add(CMSConstants.ERROR,message);
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_UPLOAD_ROOM_DETAILS);
		}
		else
		{
			String academicYear=hlAdmissionForm.getAcademicYear();
			String hostelId=hlAdmissionForm.getHostelId();
				IHladmissionTransaction transaction = new HlAdmissionImpl();
			    Map<String,Integer > hlBlicksMap=transaction.getHlBlocksMap(hostelId);
			    Map<String,Map<String,Integer>> hlUnitsMap=transaction.getHlUnitsMap(hostelId);
			List<String> list=new ArrayList<String>();
			FormFile myFile=hlAdmissionForm.getTheFile();
			String contentType=myFile.getContentType();
			String fileName=myFile.getFileName();
			File file=null;
			Properties prop=new Properties();
			InputStream stream=HlAdmissionAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			List<HlAdmissionBo> results=null;
			 //if the uploading file is excel file
		    if(fileName.endsWith(".xls")){
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_ROOM_DETAILS_FILE);
		    	String filePath=request.getRealPath("");
		    	filePath = filePath + "//TempFiles//";
				File file1 = new File(filePath+source1);
				InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			OutputStream out = new FileOutputStream(file1);
			byte buffer[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
			String source=prop.getProperty(CMSConstants.UPLOAD_ROOM_DETAILS_FILE);
			file = new File(filePath+source);
			
			POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
		    HSSFWorkbook workbook = new HSSFWorkbook(system);
		    
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell;
		    int rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0; // No of columns
		    int tmp = 0;
		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(int i = 0; i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) {
			            cols = tmp;
			            break;
		            }
		        }
		    }
		    boolean isAdded = false;
		    results= new ArrayList<HlAdmissionBo>();
		    for(int r = 1; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		        	String regNo=null;
                	String block=null;
                	String unit=null;
                	String roomNo=null;
                	String bedNo=null;
                	String biometricId=null;
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte) c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
		                	String cell2=cell.toString();
		                	
		                	if(cell.getCellNum() == 0){
								if(!StringUtils.isEmpty((cell2))){
									try
									{
										Double a = Double.parseDouble(cell2);
										Long x=(a).longValue(); 
										regNo=String.valueOf(x);
									}
									catch(NumberFormatException e)
									{
										regNo=cell2;
									}
									/*if(cell instanceof String)
									Double a = Double.parseDouble(cell2);
									Long x=(a).longValue();
									//int a=(int)Float.parseFloat(cell2.toString().trim());
									regNo=String.valueOf(x);*/
								}
		                   	}
							if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell2)){
								block=cell2.trim();
							}
							if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell2)){
								unit=cell2.trim();
							}
							if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell2)){
								roomNo=removeFileExtension(cell2.trim());
							}
							if(cell.getCellNum()==4 && !StringUtils.isEmpty(cell2))
							{
								bedNo=removeFileExtension(cell2.trim());
							}
							if(cell.getCellNum()==5 && !StringUtils.isEmpty(cell2))
							{
								biometricId=removeFileExtension(cell2.trim());
							}
		                }
		            }
		            HlAdmissionBo uploadBo=HlAdmissionHandler.getInstance().getAdmissionId(hostelId,regNo,academicYear); 
		         if(uploadBo!=null){
		        	 Map<String,Integer> unitsMap=hlUnitsMap.get(block);
		        	 if(hlBlicksMap.get(block)!=null && unitsMap.get(unit)!=null){
		        		 HlRoom hlRoom=HlAdmissionHandler.getInstance().getRoomIdAndRoomTypeId(hostelId,hlBlicksMap.get(block),unitsMap.get(unit),roomNo);
		        		 if(hlRoom!=null){
		        			 int roomId=hlRoom.getId();
		        			 int rTypeId=hlRoom.getHlRoomType().getId();
		        			 HlBeds hlBed=HlAdmissionHandler.getInstance().getRoomBedId(roomId,bedNo,rTypeId,hostelId);
		        			 HlBeds hlBeds=null;
		        			 	if(hlBed!=null){
		        			 		HlRoom hlRoom2=new HlRoom();
		        			 		hlRoom2.setId(roomId);
		        			 		uploadBo.setRoomId(hlRoom2);
		        			 		HlRoomType hlRoomType=new HlRoomType();
		        			 		hlRoomType.setId(hlRoom.getHlRoomType().getId());
		        			 		uploadBo.setRoomTypeId(hlRoomType);
		        			 		hlBeds=new HlBeds();
		        			 		hlBeds.setId(hlBed.getId());
		        			 		uploadBo.setBedId(hlBeds);
		        			 		if(uploadBo.getBiometricId()!=null && !uploadBo.getBiometricId().isEmpty()){
		        			 			uploadBo.setBiometricId(uploadBo.getBiometricId());
		        			 		}else{
		        			 			uploadBo.setBiometricId(biometricId);
		        			 		}
		        			 		list.add(regNo);
		        			 	}else{ 
		        			 		message1 = new ActionMessage("Knowledgepro.hostel.suggestion.messages1",regNo);
		        			 		messages.add("messages", message1);
		        			 		saveMessages(request, messages);
		        			 	}
		        		 }else{
		        			 message1 = new ActionMessage("Knowledgepro.hostel.suggestion.messages2",regNo);
		        			 messages.add("messages", message1);
		        			 saveMessages(request, messages);
		        		 }
		        		 if(!enterCorrect.isEmpty()){
		        			 int length=enterCorrect.length();
		        			 if(enterCorrect.startsWith(",")){
		        				 enterCorrect=enterCorrect.substring(1, length);
		        			 }
		        			 errors.add("error", new ActionError("knowledgepro.child.details.entered.incorrect",enterCorrect));
		        			 addErrors(request, errors);
		        			 return mapping.findForward(CMSConstants.INIT_UPLOAD_ROOM_DETAILS);
		        		 }
		        		 if(uploadBo!=null){
		        			 uploadBo.setModifiedBy(hlAdmissionForm.getUserId());
		        			 uploadBo.setLastModifiedDate(new Date());
		        			 results.add(uploadBo);
		        		 }
		        	}else{
		        		message1 = new ActionMessage("Knowledgepro.hostel.suggestion.messages4",regNo);
			        	messages.add("messages", message1);
				    	saveMessages(request, messages);
			        	continue;
		        	 }
		          
		        	}else {
		        		message1 = new ActionMessage("Knowledgepro.hostel.suggestion.messages",regNo);
		        		messages.add("messages", message1);
			    		saveMessages(request, messages);
		        		continue;		        	
		        	}
		    	
		    }
		    }
		    if(rows<=1)
		    {
		    	errors.add("error", new ActionError("knowledgepro.child.details.empty"));
		    	addErrors(request, errors);
		    	return mapping.findForward(CMSConstants.INIT_UPLOAD_ROOM_DETAILS);
		    }
		   // String user=hlAdmissionForm.getUserId();
		    if(results!=null && results.size()>0){
		   isAdded=HlAdmissionHandler.getInstance().addUploadRoomDetails(results);
		    }
		    if(messages!=null && !messages.isEmpty()){
		    		saveMessages(request, messages);
		    		return mapping.findForward(CMSConstants.INIT_UPLOAD_ROOM_DETAILS);
	    		}else if(isAdded){
	    		//if adding is success
	    			ActionMessage message =null;
	    			if(rows==list.size()+1){
	    				message = new ActionMessage(CMSConstants.UPLOAD_ROOM_DETAILS_SUCCESS);
	    			}else{
	    				StringBuilder regNos= new StringBuilder();
	    				Iterator<String> iterator=list.iterator();
	    				while (iterator.hasNext()) {
							String regNumber = (String) iterator.next();
							regNos.append(",").append(regNumber);
							message = new ActionMessage("knowledgepro.hostel.upload.success",regNos);
						}
	    			}
	    				messages.add("messages", message);
	    				saveMessages(request, messages);
	    				hlAdmissionForm.resetValues();
	    		}else{
	    		//if adding is failure
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ROOM_DETAILS_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    		}
		    }else{
		    	ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ROOM_DETAILS_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    }
		}
	}
	catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		hlAdmissionForm.setErrorMessage(msg);
		hlAdmissionForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	log.info("Leaving UploadChildrenDetails input");
	return mapping.findForward(CMSConstants.INIT_UPLOAD_ROOM_DETAILS);
}

protected class ByteArrayStreamInfo implements StreamInfo {
		
		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myDfBytes) {
			this.contentType = contentType;
			this.bytes = myDfBytes;
		}
	
		public String getContentType() {
			return contentType;
		}
	
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}

public String removeFileExtension(String fileName)
{ 
if(null != fileName && fileName.contains("."))
{
return fileName.substring(0, fileName.lastIndexOf("."));
}
return fileName;
}

public ActionForward getStudentInWaitingListWithPriorityNo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
{
	HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
	HttpSession session=request.getSession();
	try {
		if((hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()) ||(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty()))
		{
		int priorityNo=HlAdmissionHandler.getInstance().getStudentInWaitingListWithPriorityNo(hlAdmissionForm,session);
		if(priorityNo>0)
		{
			hlAdmissionForm.setWatingPriorityNo(String.valueOf(priorityNo));
			hlAdmissionForm.setStudentInWaitingList(true);
			hlAdmissionForm.setWaitingList(false);
			session.setAttribute("studentWaitingListPriorityNumber", String.valueOf(priorityNo));
		}
		else
		{
			session.setAttribute("studentWaitingListPriorityNumber", null);
			hlAdmissionForm.setStudentInWaitingList(false);
			hlAdmissionForm.setWaitingList(false);
		}
		}
		setHostelToRequest(request,hlAdmissionForm);
		setRoomTypeToRequest(request,hlAdmissionForm);
		Map<Integer, String> roomTypeMap=CommonAjaxHandler.getInstance().getRoomTypeByHostelBYstudent(Integer.parseInt(hlAdmissionForm.getHostelId()));
		request.setAttribute("roomTypeMap", roomTypeMap);
		if(hlAdmissionForm.getEditCheck()!=null && !hlAdmissionForm.getEditCheck().isEmpty()){
			request.setAttribute("hlAdmission","edit");
		}
	} catch (Exception exception) {
		log.error("Error occured in caste Entry Action", exception);
		String msg = super.handleApplicationException(exception);
		hlAdmissionForm.setErrorMessage(msg);
		hlAdmissionForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	
}
public ActionForward studentInWaitingListToHostelAdmission(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
{
   
	HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
	setUserId(request,hlAdmissionForm);
	ActionMessages messages = new ActionMessages();
	hlAdmissionForm.setPrint(null);
	hlAdmissionForm.setWaitingList(false);
	hlAdmissionForm.setStudentInWaitingList(false);
	//HttpSession session=request.getSession();
	ActionErrors errors=hlAdmissionForm.validate(mapping, request);
	String mode="add";
	boolean isAdded=false;
	if(errors.isEmpty())
	{
	try {
		
		isAdded=HlAdmissionHandler.getInstance().addhlAdmission(hlAdmissionForm,"Add");
		if (isAdded) {
			messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.inventory.hlAdmission.addsuccess"));
			saveMessages(request, messages);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
			hlAdmissionForm.reset();
			} else {
			errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.addfailure"));
			addErrors(request, errors);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
		}
	}
		catch (Exception exception) {
			log.error("Error occured in caste Entry Action", exception);
			String msg = super.handleApplicationException(exception);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	} else {
		saveErrors(request, errors);
		setHostelToRequest(request,hlAdmissionForm);
		setRoomTypeToRequest(request,hlAdmissionForm);
		setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	
	log.info("end of studentInWaitingListToHostelAdmission method in HlAdmissionAction class.");
	return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward newHostelAdmission(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse responce) throws Exception{
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		setUserId(request,hlAdmissionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		String mode="add";
		hlAdmissionForm.setPrint(null);
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		hlAdmissionForm.setId(0);
		HttpSession session=request.getSession();
		try{
			if(errors==null || errors.isEmpty()){
				if((hlAdmissionForm.getStudentName()==null || hlAdmissionForm.getStudentName().isEmpty()) &&
						   ( hlAdmissionForm.getRegNo()==null || hlAdmissionForm.getRegNo().isEmpty()) &&
							( hlAdmissionForm.getApplNo()==null || hlAdmissionForm.getApplNo().isEmpty())){
					errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.regapplicaname"));
				}
				if(hlAdmissionForm.getHostelName() == null || hlAdmissionForm.getHostelName().isEmpty()){
					errors.add("error", new ActionError( "knowledgepro.hostel.hlAdmission.name"));
				}
				if(hlAdmissionForm.getRoomTypeName() == null || hlAdmissionForm.getRoomTypeName().isEmpty()){
					errors.add("error", new ActionError( "knowledgepro.hostel.hlAdmission.roomtype"));
				}
			}
			if(errors!=null && !errors.isEmpty()){
				saveErrors(request, errors);
				setHostelToRequest(request,hlAdmissionForm);
				setRoomTypeToRequest(request,hlAdmissionForm);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
			}else{
				getRoomAvailability(hlAdmissionForm,errors);
			}
			if (errors.isEmpty()) {
				String gender=HlAdmissionHandler.getInstance().genderCheck(hlAdmissionForm,errors,session);
				if(gender!=null && !gender.isEmpty()){
					if(gender.equalsIgnoreCase("NoData")){
						errors.add("error", new ActionError( "knowledgepro.hostel.student.notadmitted"));
					}else{
						errors.add("error", new ActionError( "knowledgepro.hostel.gender.selection",gender));
					}
					addErrors(request, errors);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
				}
				boolean isDuplicate=HlAdmissionHandler.getInstance().duplicateCheck(hlAdmissionForm,errors,session);
				if(!isDuplicate){
					boolean isAdded = false;
					IHladmissionTransaction transaction=new HlAdmissionImpl();
					List<HlAdmissionBookingWaitingBo> list=transaction.checkStudentsAreThereInWaitingList(hlAdmissionForm);
					String WaitingId=(String) session.getAttribute("StudentInWaitingListId");
					String priorityNo =(String) session.getAttribute("studentWaitingListPriorityNumber");
					if(list!=null && !list.isEmpty()){
						if(priorityNo!=null && !priorityNo.isEmpty()){
							isAdded = HlAdmissionHandler.getInstance().addhlAdmission(hlAdmissionForm,"Add");
							if (isAdded) {
								//String WaitingId=(String) session.getAttribute("StudentInWaitingListId");
								if(WaitingId!=null)	{
									HlAdmissionHandler.getInstance().resetStudentInWaitingList(Integer.parseInt(WaitingId),hlAdmissionForm);
								}
								messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.inventory.hlAdmission.addsuccess"));
								saveMessages(request, messages);
								setHostelToRequest(request,hlAdmissionForm);
								setRoomTypeToRequest(request,hlAdmissionForm);
								setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
							 // not Required Now
							//	hlAdmissionForm.setPrint("print");
								hlAdmissionForm.reset();
							} else {
								errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.addfailure"));
								addErrors(request, errors);
								setHostelToRequest(request,hlAdmissionForm);
								setRoomTypeToRequest(request,hlAdmissionForm);
								setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
							}
						}else{
							errors.add("error", new ActionError( "knowledgepro.Hostel.Admission.seatAvailable.NotInWaitingList"));
							addErrors(request, errors);
							setHostelToRequest(request,hlAdmissionForm);
							setRoomTypeToRequest(request,hlAdmissionForm);
							setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
						}
					}else{
						isAdded = HlAdmissionHandler.getInstance().addhlAdmission(hlAdmissionForm,"Add");
						if (isAdded) {
							messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.inventory.hlAdmission.addsuccess"));
							saveMessages(request, messages);
							setHostelToRequest(request,hlAdmissionForm);
							setRoomTypeToRequest(request,hlAdmissionForm);
							setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
							hlAdmissionForm.reset();
							hlAdmissionForm.setPrint("true");
						} else {
							errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.addfailure"));
							addErrors(request, errors);
							setHostelToRequest(request,hlAdmissionForm);
							setRoomTypeToRequest(request,hlAdmissionForm);
							setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
						}
					}
				}else{
					addErrors(request, errors);
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				}
			}else{
				saveErrors(request, errors);
				setHostelToRequest(request,hlAdmissionForm);
				setRoomTypeToRequest(request,hlAdmissionForm);
				setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
			}
		}catch (Exception e) {
			log.error("Error occured in caste Hostel Admission Action", e);
			String msg = super.handleApplicationException(e);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
	}
	/**
	 * printing the Collection Ledger Report Result
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printHlAdmission(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HlAdmissionForm hlAdmissionForm = (HlAdmissionForm) form;
		hlAdmissionForm.setWaitingList(false);
		hlAdmissionForm.setStudentInWaitingList(false);
		hlAdmissionForm.setPrint(null);
		hlAdmissionForm.setProgramYear(null);
		try{
			if(hlAdmissionForm.getId()>0){
				HlAdmissionHandler.getInstance().printHlAdmission(hlAdmissionForm,request);
				if(hlAdmissionForm.getYear()!=null){
					hlAdmissionForm.setProgramYear(hlAdmissionForm.getYear()+"-"+(Integer.parseInt(hlAdmissionForm.getYear())+1));
				}else{
					hlAdmissionForm.setProgramYear(hlAdmissionForm.getAcademicYear()+"-"+(Integer.parseInt(hlAdmissionForm.getAcademicYear())+1));
				}
			}
		}catch (Exception e) {
			log.error("Error occured in caste Hostel Admission Action", e);
			String msg = super.handleApplicationException(e);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//hlAdmissionForm.reset();
		return mapping.findForward("printHostelAdmission");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkStudentApplication(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		HlAdmissionForm hlAdmissionForm=(HlAdmissionForm) form;
		HttpSession session=request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			
			if((hlAdmissionForm.getRegNo()!=null && !hlAdmissionForm.getRegNo().isEmpty()) 
					||(hlAdmissionForm.getApplNo()!=null && !hlAdmissionForm.getApplNo().isEmpty())){
				hlAdmissionForm.setHostelName("");
				hlAdmissionForm.setRoomTypeName("");
				hlAdmissionForm.setStudentName(request.getParameter("studentName").toString());
				HlAdmissionHandler.getInstance().setHostelApplicationValuesToForm(hlAdmissionForm);
				if(hlAdmissionForm.getHlApplicationNo() == null || hlAdmissionForm.getHlApplicationNo().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.hlAdmission.student.not.applied"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
				}else{
					IHladmissionTransaction transaction = new HlAdmissionImpl();
					hlAdmissionForm.setTempSeatAvailable("");
					hlAdmissionForm.setSeatAvailable("");
					BigDecimal number = transaction.getNumberOfSeatsAvailable(hlAdmissionForm.getHostelName(), hlAdmissionForm.getRoomTypeName(),hlAdmissionForm.getYear(),request);
					if(number != null){
						hlAdmissionForm.setTempSeatAvailable(String.valueOf(number.intValue()));
						hlAdmissionForm.setSeatAvailable(String.valueOf(number.intValue()));
					}else{
						hlAdmissionForm.setTempSeatAvailable(String.valueOf(0));
						hlAdmissionForm.setSeatAvailable(String.valueOf(0));
					}
					int priorityNo=HlAdmissionHandler.getInstance().getStudentInWaitingListWithPriorityNo(hlAdmissionForm,session);
					if(priorityNo>0){
						hlAdmissionForm.setWatingPriorityNo(String.valueOf(priorityNo));
						hlAdmissionForm.setStudentInWaitingList(true);
						hlAdmissionForm.setWaitingList(false);
						session.setAttribute("studentWaitingListPriorityNumber", String.valueOf(priorityNo));
					}else{
						session.setAttribute("studentWaitingListPriorityNumber", null);
						hlAdmissionForm.setStudentInWaitingList(false);
						hlAdmissionForm.setWaitingList(false);
					}
					setHostelToRequest(request,hlAdmissionForm);
					setRoomTypeToRequest(request,hlAdmissionForm);
					if(hlAdmissionForm.getEditCheck()!=null && !hlAdmissionForm.getEditCheck().isEmpty()){
						request.setAttribute("hlAdmission","edit");
					}
				}
			}
		} catch (Exception exception) {
			log.error("Error occured in caste Entry Action", exception);
			String msg = super.handleApplicationException(exception);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_ADMISSION);
		
	}
}
