package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.HlStudentChInEditForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HlStudentCheckInHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHlStudentCheckInTransaction;
import com.kp.cms.transactionsimpl.hostel.HlStudentCheckInImpl;

public class HlStudentCheckInAndEditAction extends BaseDispatchAction{
	private static final Log log=LogFactory.getLog(HlStudentCheckInAndEditAction.class);
	IHlStudentCheckInTransaction transaction = new HlStudentCheckInImpl();
	
	
	public ActionForward initHlAdmission(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initHlAdmission method in HlAdmissionAction class.");
		HlStudentChInEditForm hlAdmissionForm=(HlStudentChInEditForm) form;
		hlAdmissionForm.reset();
		setDataToForm(hlAdmissionForm);
		setAllFacilityToForm(hlAdmissionForm);
		setHostelToRequest(request,hlAdmissionForm);
	
		setRoomTypeToRequest(request,hlAdmissionForm);
		log.debug("Leaving inithlAdmission");
		return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
	}
	
	private void setDataToForm(HlStudentChInEditForm roomForm) throws Exception{
		 List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		roomForm.setHostelList(hostelList);
		if(roomForm.getHostelId() != null && !roomForm.getHostelId().trim().isEmpty()){
			roomForm.setBlockMap(HostelEntryHandler.getInstance().getBlocks(roomForm.getHostelId()));
		}
		if(roomForm.getBlock() != null && !roomForm.getBlock().trim().isEmpty()){
			roomForm.setUnitMap(HostelEntryHandler.getInstance().getUnits(roomForm.getBlock()));
		}
		if(roomForm.getUnit() != null && !roomForm.getUnit().trim().isEmpty()){
			roomForm.setFloorMap(CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(roomForm.getUnit())));
		}
	}
	
	public void setHostelToRequest(HttpServletRequest request, HlStudentChInEditForm hlAdmissionForm)throws Exception
	{
			log.debug("start setHostelEntryDetailsToRequest");
			Map<Integer, String> hostelmap=null;
			
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
	public void setRoomTypeToRequest(HttpServletRequest request, HlStudentChInEditForm hlAdmissionForm)throws Exception
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRooms(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> roomMap = new HashMap<Integer, String>();
		try{
			roomMap = HlStudentCheckInHandler.getInstance().getRooms(baseActionForm.getHostelId(),baseActionForm.getHostelroomType(),
					baseActionForm.getAcademicYear(),baseActionForm.getBlockId(),baseActionForm.getUnitId(),baseActionForm.getFloorNo(),request.getParameter("studentId"),roomMap);
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, roomMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBedsAvailable(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HlStudentChInEditForm baseActionForm = (HlStudentChInEditForm) form;
		String roomId=baseActionForm.getRoomId();
		if(!roomId.isEmpty()){
		int academicYear=Integer.parseInt(baseActionForm.getAcademicYear());
		Map<Integer, String> bedMap = 
			CommonAjaxHandler.getInstance().getBedsAvailable(roomId,academicYear);
		request.setAttribute(CMSConstants.OPTION_MAP, bedMap);
		//Added by Dilip
		}else{
			String roomIdd="0";
			int academicYear=Integer.parseInt(baseActionForm.getAcademicYear());
			Map<Integer, String> bedMap = 
				CommonAjaxHandler.getInstance().getBedsAvailable(roomIdd,academicYear);
			request.setAttribute(CMSConstants.OPTION_MAP, bedMap);
		}
		//Added by Dilip
	return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responce
	 * @return
	 * @throws Exception
	 */
	public ActionForward HostelAdmissionSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
	throws Exception{
		HlStudentChInEditForm hlAdmissionForm=(HlStudentChInEditForm) form;
		ActionErrors errors=new ActionErrors();
		try{
			hlAdmissionForm.setPrint(null);
			String mode="search";
			if(( hlAdmissionForm.getRegNo()==null || hlAdmissionForm.getRegNo().isEmpty()) &&
				(hlAdmissionForm.getApplNo()==null || hlAdmissionForm.getApplNo().isEmpty()) &&
				(hlAdmissionForm.getHlApplicationNo()==null || hlAdmissionForm.getHlApplicationNo().isEmpty())){
				errors.add("error", new ActionError( "knowledgepro.hostel.hlAdmission.regapplicaname"));
			}else{
				setAllFacilityToForm(hlAdmissionForm);
				setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				hlAdmissionForm.setDataAvailable("true");
			}
			if(!errors.isEmpty())
				hlAdmissionForm.setDataAvailable("false");
				addErrors(request, errors);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
	}
	
	/**
	 * @param hlAdmissionForm
	 * @param errors
	 * @param request
	 * @param mode
	 * @throws Exception
	 */
	public void setRequestedDateToForm(HlStudentChInEditForm hlAdmissionForm, ActionErrors errors, HttpServletRequest request, String mode) throws Exception{
		List<HlAdmissionTo> hlAdmissionList=HlStudentCheckInHandler.getInstance().gethlAdmissionList(hlAdmissionForm,errors,request,mode);
		hlAdmissionForm.setHlAdmissionList(hlAdmissionList);
	}
	
	/*public ActionForward addHostelStudentCheckIn(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
	throws Exception{
		log.info("call of addhlAdmission method in InvSubCategogyAction class.");
		HlStudentChInEditForm hlAdmissionForm = (HlStudentChInEditForm) form;
		setUserId(request,hlAdmissionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		String mode="add";
		hlAdmissionForm.setPrint(null);
		HttpSession session=request.getSession();
		if(errors==null || errors.isEmpty()){
			if((hlAdmissionForm.getHlApplicationNo()==null || hlAdmissionForm.getHlApplicationNo().isEmpty()) &&
					   ( hlAdmissionForm.getRegNo()==null || hlAdmissionForm.getRegNo().isEmpty()) &&
						( hlAdmissionForm.getApplNo()==null || hlAdmissionForm.getApplNo().isEmpty())){
						errors.add("error", new ActionError( "knowledgepro.inventory.hlAdmission.regapplicaname"));
		}}if(errors!=null && !errors.isEmpty()){
			saveErrors(request, errors);
			setHostelToRequest(request,hlAdmissionForm);
			setRoomTypeToRequest(request,hlAdmissionForm);
			return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
		}
		errors = hlAdmissionForm.validate(mapping, request);
		if(errors==null || errors.isEmpty()){
	//	getRoomAvailability(hlAdmissionForm,errors);
		}
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				String gender=HlStudentCheckInHandler.getInstance().genderCheck(hlAdmissionForm,errors,session);
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
					return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
				}
				boolean isDuplicate=HlStudentCheckInHandler.getInstance().duplicateCheck(hlAdmissionForm,errors,session);
				if(!isDuplicate){
				isAdded = HlStudentCheckInHandler.getInstance().addhlAdmission(hlAdmissionForm,"Add");
				if (isAdded) {
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
			return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
		}
		
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
	}*/
	
	
	public ActionForward updateHostelAdmission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updatevaluatorCharges Action");
		HlStudentChInEditForm hlAdmissionForm = (HlStudentChInEditForm) form;
		HttpSession session=request.getSession();
		hlAdmissionForm.setPrint(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hlAdmissionForm.validate(mapping, request);
		boolean isUpdated = false;
		String mode="update";
		//Added by Dilip
		if(hlAdmissionForm.getIsCheckIn().equalsIgnoreCase("0")){
			errors.add("error", new ActionError("knowledgepro.inventory.hlAdmission.select.checkin"));
			addErrors(request, errors);
			saveErrors(request, errors);
			if(hlAdmissionForm.getHostelId()!=null && !hlAdmissionForm.getHostelId().isEmpty()){
				Map<Integer, String> RoomTypeMap = CommonAjaxHandler.getInstance().getRoomTypeByHostelBYstudent(Integer.parseInt(hlAdmissionForm.getHostelId()));
				Map<Integer, String> BlockMap = HostelEntryHandler.getInstance().getBlocks(String.valueOf(hlAdmissionForm.getHostelId()));
				Map<Integer, String> FloorMap = CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(hlAdmissionForm.getHostelId()));
				hlAdmissionForm.setRoomTypeMap(RoomTypeMap);
				hlAdmissionForm.setBlockMap(BlockMap);
				hlAdmissionForm.setFloorMap(FloorMap);
			}
			if(hlAdmissionForm.getBlock()!=null && !hlAdmissionForm.getBlock().isEmpty()){
				Map<Integer, String> UnitMap = HostelEntryHandler.getInstance().getUnits(String.valueOf(hlAdmissionForm.getBlock()));
				hlAdmissionForm.setUnitMap(UnitMap);
			}
			if(hlAdmissionForm.getFloorNo()!=null && !hlAdmissionForm.getFloorNo().isEmpty()
					&& hlAdmissionForm.getHostelId()!=null && !hlAdmissionForm.getHostelId().isEmpty()
					&& hlAdmissionForm.getRoomTypeName()!=null && !hlAdmissionForm.getRoomTypeName().isEmpty()){
				Map<Integer, String> RoomMap = CommonAjaxHandler.getInstance().getRoomsAvailable(hlAdmissionForm.getHostelId(),hlAdmissionForm.getRoomTypeName(),hlAdmissionForm.getAcademicYear()
						,hlAdmissionForm.getBlock(),hlAdmissionForm.getUnit(),hlAdmissionForm.getFloorNo());
				
				hlAdmissionForm.setRoomMap(RoomMap);
			}
			if(hlAdmissionForm.getRoomId()!=null && !hlAdmissionForm.getRoomId().isEmpty()){
				Map<Integer, String> BedMap = CommonAjaxHandler.getInstance().getBedByRoom(Integer.parseInt(hlAdmissionForm.getRoomId()));
				hlAdmissionForm.setBedMap(BedMap);
			}
			if(hlAdmissionForm.getFacilityList()!=null && ! hlAdmissionForm.getFacilityList().isEmpty()){
				List<FacilityTO> toList = new ArrayList<FacilityTO>();
				Iterator<FacilityTO> facItr=hlAdmissionForm.getFacilityList().iterator();
				while(facItr.hasNext()){
					FacilityTO to =facItr.next();
					
					if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
						to.setDummySelected(true);
						to.setChecked(null);
						toList.add(to);
					}
					else{
						to.setDummySelected(false);
						toList.add(to);
					}
				}
				hlAdmissionForm.setFacilityList(toList);
			}
			
			request.setAttribute("HlAdmission", "edit");
			return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
		}
		//Added by Dilip
		
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				hlAdmissionForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				HlStudentCheckInHandler.getInstance().edithlAdmission(hlAdmissionForm);
				request.setAttribute("hlAdmission", "edit");
				return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
			}
			setUserId(request, hlAdmissionForm); // setting user id to update
			boolean isDuplicate=false;
			if(hlAdmissionForm.getBiometricId()!=null && !hlAdmissionForm.getBiometricId().isEmpty()){
				isDuplicate=HlStudentCheckInHandler.getInstance().duplicateCheck(hlAdmissionForm,errors,session);
			}
			if(!isDuplicate){
				isUpdated = HlStudentCheckInHandler.getInstance().updatehlAdmission(hlAdmissionForm,"Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.inventory.hlAdmission.checkin.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
				hlAdmissionForm.reset();
			} else {
				errors.add("error", new ActionError("knowledgepro.inventory.hlAdmission.checkin.failed"));
				addErrors(request, errors);
				setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
			}
			}//modified by Dilip
			else{
				 if(!errors.isEmpty())
				addErrors(request, errors);
				 saveErrors(request, errors);
					if(hlAdmissionForm.getBlock()!=null && !hlAdmissionForm.getBlock().isEmpty()){
					Map<Integer, String> UnitMap = HostelEntryHandler.getInstance().getUnits(String.valueOf(hlAdmissionForm.getBlock()));
					hlAdmissionForm.setUnitMap(UnitMap);
					}
					hlAdmissionForm.setRoomMap(null);
					if(hlAdmissionForm.getFloorNo()!=null && !hlAdmissionForm.getFloorNo().isEmpty()
							&& hlAdmissionForm.getHostelId()!=null && !hlAdmissionForm.getHostelId().isEmpty()
							&& hlAdmissionForm.getRoomTypeName()!=null && !hlAdmissionForm.getRoomTypeName().isEmpty()){
						Map<Integer, String> RoomMap = CommonAjaxHandler.getInstance().getRoomsAvailable(hlAdmissionForm.getHostelId(),hlAdmissionForm.getRoomTypeName(),hlAdmissionForm.getAcademicYear()
								,hlAdmissionForm.getBlock(),hlAdmissionForm.getUnit(),hlAdmissionForm.getFloorNo());
						
						hlAdmissionForm.setRoomMap(RoomMap);
					}
					if(hlAdmissionForm.getRoomId()!=null && !hlAdmissionForm.getRoomId().isEmpty()){
						Map<Integer, String> BedMap = CommonAjaxHandler.getInstance().getBedByRoom(Integer.parseInt(hlAdmissionForm.getRoomId()));
					hlAdmissionForm.setBedMap(BedMap);
					}
					
					if(hlAdmissionForm.getFacilityList()!=null && ! hlAdmissionForm.getFacilityList().isEmpty()){
						List<FacilityTO> toList = new ArrayList<FacilityTO>();
						Iterator<FacilityTO> facItr=hlAdmissionForm.getFacilityList().iterator();
						while(facItr.hasNext()){
							FacilityTO to =facItr.next();
							if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
								to.setDummySelected(true);
								toList.add(to);
							}
							else{
								to.setDummySelected(false);
								toList.add(to);
							}
						}
						hlAdmissionForm.setFacilityList(toList);
					}
			}
			//modified by Dilip
		} catch (Exception e) {
			log.error("Error occured in Action", e);
			String msg = super.handleApplicationException(e);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			if(hlAdmissionForm.getFacilityList()!=null && ! hlAdmissionForm.getFacilityList().isEmpty()){
				List<FacilityTO> toList = new ArrayList<FacilityTO>();
				Iterator<FacilityTO> facItr=hlAdmissionForm.getFacilityList().iterator();
				while(facItr.hasNext()){
					FacilityTO to =facItr.next();
					if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
						to.setDummySelected(true);
						to.setChecked(null);
						toList.add(to);
					}else{
						to.setDummySelected(false);
						toList.add(to);
					}
				}
				hlAdmissionForm.setFacilityList(toList);
			}
			saveErrors(request, errors);
		// commented by chandra
			//setRequestedDateToForm(hlAdmissionForm,errors,request,mode);
			//hlAdmissionForm.reset();
			request.setAttribute("HlAdmission", "edit");
			return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
		}
		log.debug("Exit: action ");
		return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
	}
	
	public void setAllFacilityToForm(HlStudentChInEditForm hlAdmissionForm)throws Exception {
		log.info("entering into setAllFacilityToForm  Action");
		List<FacilityTO> facilityList = HlStudentCheckInHandler.getInstance().getAllFacility();
		hlAdmissionForm.setFacilityList(facilityList);
		log.info("Leaving into setAllFacilityToForm  Action");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHlAdmissionNew(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HlStudentChInEditForm hlAdmissionForm=(HlStudentChInEditForm) form;
		hlAdmissionForm.reset();
		return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responce
	 * @return
	 * @throws Exception
	 */
	public ActionForward HostelAdmissionSearchNew(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse responce) throws Exception {
		
		HlStudentChInEditForm hlAdmissionForm=(HlStudentChInEditForm) form;
		ActionErrors errors=new ActionErrors();
		try{
			hlAdmissionForm.setPrint(null);
			String mode="search";
			if(( hlAdmissionForm.getRegNo()==null || hlAdmissionForm.getRegNo().isEmpty()) &&
				(hlAdmissionForm.getApplNo()==null || hlAdmissionForm.getApplNo().isEmpty()) &&
				(hlAdmissionForm.getHlApplicationNo()==null || hlAdmissionForm.getHlApplicationNo().isEmpty())){
				errors.add("error", new ActionError( "knowledgepro.hostel.hlAdmission.regapplicaname"));
			}else{
				setAllFacilityToForm(hlAdmissionForm);
				setRequestedDateToFormNew(hlAdmissionForm,errors,request,mode);
				hlAdmissionForm.setDataAvailable("true");
			}
			if(!errors.isEmpty())
				hlAdmissionForm.setDataAvailable("false");
				addErrors(request, errors);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hlAdmissionForm.setErrorMessage(msg);
			hlAdmissionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_STUDENT_CHECK_IN);
	}
	/**
	 * @param hlAdmissionForm
	 * @param errors
	 * @param request
	 * @param mode
	 * @throws Exception
	 */
	public void setRequestedDateToFormNew(HlStudentChInEditForm hlAdmissionForm, ActionErrors errors, HttpServletRequest request, String mode) throws Exception{
		List<HlAdmissionTo> hlAdmissionList=HlStudentCheckInHandler.getInstance().gethlAdmissionListNew(hlAdmissionForm,errors,request,mode);
		hlAdmissionForm.setHlAdmissionList(hlAdmissionList);
	}
}