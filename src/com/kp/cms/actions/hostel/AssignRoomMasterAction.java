package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.hostel.AssignRoomMasterForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.AssignRoomMasterHandler;
import com.kp.cms.handlers.hostel.HostelApplicationHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.helpers.hostel.AssignRoomMasterHelper;
import com.kp.cms.to.hostel.AssignRoomMasterTo;
import com.kp.cms.to.hostel.HlBedsTO;
import com.kp.cms.to.hostel.HlRoomTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.transactions.hostel.IAssignRoomTransactions;
import com.kp.cms.transactionsimpl.hostel.AssignRoomTransactionImpl;

@SuppressWarnings("deprecation")
public class AssignRoomMasterAction extends BaseDispatchAction{
	public static final Log log = LogFactory.getLog(AssignRoomMasterAction.class);

	/**
	 * Initialize room master
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAssignRoomMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("Entering initAssignRoomMaster ");
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setDataToForm(roomForm);
			setUserId(request, roomForm);
			resetFields(roomForm);
		} catch (Exception e) {
			log.error("error initAssignRoomMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				roomForm.setErrorMessage(msg);
				roomForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
	}

	log.debug("Leaving initAssignRoomMaster ");

	return mapping.findForward(CMSConstants.ASSIGN_ROOM_MASTER);

	}
	/**
	 * setting hostelList to request
	 * @param request
	 * @throws Exception 
	 */
	public void setHostelListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setHostelListToRequest");
		 List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		request.setAttribute("hostelList", hostelList);
	}
	/**
	 * setting roomTypeNameList to form
	 * @param roomForm
	 * @param request
	 * @throws Exception
	 */
	public void setRoomTypeListToRequest(AssignRoomMasterForm roomForm, HttpServletRequest request) throws Exception {
		log.debug("inside setRoomTypeListToRequest");
		List<RoomTypeTO> roomTypeNameList;
		if(roomForm.getHostelId()!= null && !roomForm.getHostelId().isEmpty()){
			roomTypeNameList = HostelApplicationHandler.getInstance().getRoomTypesonHostelId(Integer.parseInt(roomForm.getHostelId()),roomForm);
		}else{
			roomTypeNameList = HostelApplicationHandler.getInstance().getRoomTypesonHostelId(Integer.parseInt(roomForm.getSearchedHostelId()),roomForm);
		}
		roomForm.setRoomTypeList(roomTypeNameList);
	}

	
	
	/**
	 * forward to the second page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return submit Assign Room Master
	 * @throws Exception
	 */
	public ActionForward submitRoomMaster(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside submitRoomMaster");
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		 ActionErrors errors = roomForm.validate(mapping, request);
		try{
			if(roomForm.getBlock() == null || roomForm.getBlock().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.Hostel.room.block.required"));
			}
			if(roomForm.getUnit() == null || roomForm.getUnit().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.Hostel.room.unit.required"));
			}
			if(errors.isEmpty()){
				IAssignRoomTransactions irTransactions =AssignRoomTransactionImpl.getInstance();
				boolean dupli=irTransactions.getRoomsByHostelAndFloorForDuplicateCheck1(roomForm);
				if(!dupli){
					Integer noOfRooms = 0;
					
					if(roomForm.getNoOfRooms()!= null && !roomForm.getNoOfRooms().trim().isEmpty()){
						noOfRooms = Integer.parseInt(roomForm.getNoOfRooms());
					}
					List<HlRoomTO> roomList = roomForm.getRoomList();
					if(roomList==null){
						roomList = new ArrayList<HlRoomTO>();
					}else{
						noOfRooms = noOfRooms-roomList.size();
					}
					for(int j = 1; j <= noOfRooms; j++){
						HlRoomTO roomTO = new HlRoomTO(); 
						roomTO.setName("");
						roomTO.setRoomTypeId(0);
						roomList.add(roomTO);
					}
					roomForm.setRoomList(roomList);
					setRoomTypeListToRequest(roomForm, request);
				}else{
					errors.add("error", new ActionError("knowledgepro.hostel.room.already.booked", roomForm.getNoOfRooms()));
					saveErrors(request, errors);
					setDataToForm(roomForm);				
					return mapping.findForward(CMSConstants.ASSIGN_ROOM_MASTER);
				}
			}else{
				setDataToForm(roomForm);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ASSIGN_ROOM_MASTER);
			}
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			roomForm.setErrorMessage(msg);
			roomForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("leaving submitRoomMaster");
		return mapping.findForward(CMSConstants.SUBMIT_ROOM_MASTER);
	}
	
	/**
	 * @param roomForm
	 * @throws Exception
	 */
	private void setDataToForm(AssignRoomMasterForm roomForm) throws Exception{
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
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBedNumbers(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("Entering getBedNumbers ");
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		try {
			List<HlRoomTO> roomList = roomForm.getRoomList();
			if(roomList != null){
				List<HlRoomTO> rooms = new ArrayList<HlRoomTO>();
				Iterator<HlRoomTO> iterator = roomList.iterator();
				while (iterator.hasNext()) {
					HlRoomTO hlRoomTO = (HlRoomTO) iterator.next();
					List<HlBedsTO> tos = new ArrayList<HlBedsTO>();
					Integer noOfOccupants=roomForm.getRoomTypeMap().get(hlRoomTO.getRoomTypeId());
					if(noOfOccupants == null){
						noOfOccupants = 0;
					}
					if(hlRoomTO.getHlBeds() == null || hlRoomTO.getHlBeds().isEmpty()){
						for(int i = 1;i<=noOfOccupants;i++ ){
							HlBedsTO to = new HlBedsTO();
							to.setBedNo("");
							to.setIsActive(true);
							tos.add(to);
						}
					}else{
						Iterator<HlBedsTO> iterator2 = hlRoomTO.getHlBeds().iterator();
						int count =1;
						while (iterator2.hasNext()) {
							HlBedsTO hlBedsTO = (HlBedsTO) iterator2.next();
							if(count<= noOfOccupants){
								hlBedsTO.setIsActive(true);
							}else{
								hlBedsTO.setIsActive(false);
							}
							tos.add(hlBedsTO);
							count++;
						}
						if(tos.size() != noOfOccupants){
							for(int i = tos.size()+1;i<=noOfOccupants;i++ ){
								HlBedsTO to = new HlBedsTO();
								to.setBedNo("");
								to.setIsActive(true);
								tos.add(to);
							}
						}
					}
					hlRoomTO.setHlBeds(tos);
					rooms.add(hlRoomTO);
				}
				roomForm.setRoomList(rooms);
			}
		}catch (Exception e) {
			log.error("error getBedNumbers...", e);
			String msg = super.handleApplicationException(e);
			roomForm.setErrorMessage(msg);
			roomForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	log.debug("Leaving getBedNumbers ");
	return mapping.findForward(CMSConstants.SUBMIT_ROOM_MASTER);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBedNumbersForEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("Entering getBedNumbers ");
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		try {
			List<HlRoomTO> roomList = roomForm.getRoomList();
			if(roomList != null){
				List<HlRoomTO> rooms = new ArrayList<HlRoomTO>();
				Iterator<HlRoomTO> iterator = roomList.iterator();
				while (iterator.hasNext()) {
					HlRoomTO hlRoomTO = (HlRoomTO) iterator.next();
					List<HlBedsTO> tos = new ArrayList<HlBedsTO>();
					Integer noOfOccupants=roomForm.getRoomTypeMap().get(hlRoomTO.getRoomTypeId());
					if(noOfOccupants == null){
						noOfOccupants = 0;
					}
					if(hlRoomTO.getHlBeds() == null || hlRoomTO.getHlBeds().isEmpty()){
						for(int i = 1;i<=noOfOccupants;i++ ){
							HlBedsTO to = new HlBedsTO();
							to.setBedNo("");
							to.setIsActive(true);
							tos.add(to);
						}
					}else{
						Iterator<HlBedsTO> iterator2 = hlRoomTO.getHlBeds().iterator();
						int count =1;
						while (iterator2.hasNext()) {
							HlBedsTO hlBedsTO = (HlBedsTO) iterator2.next();
							if(count<= noOfOccupants){
								hlBedsTO.setIsActive(true);
							}else{
								hlBedsTO.setIsActive(false);
							}
							tos.add(hlBedsTO);
							count++;
						}
						if(tos.size() != noOfOccupants){
							for(int i = tos.size()+1;i<=noOfOccupants;i++ ){
								HlBedsTO to = new HlBedsTO();
								to.setBedNo("");
								to.setIsActive(true);
								tos.add(to);
							}
						}
					}
					hlRoomTO.setHlBeds(tos);
					rooms.add(hlRoomTO);
				}
				roomForm.setRoomList(rooms);
			}
		}catch (Exception e) {
			log.error("error getBedNumbers...", e);
			String msg = super.handleApplicationException(e);
			roomForm.setErrorMessage(msg);
			roomForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	log.debug("Leaving getBedNumbers ");
	return mapping.findForward(CMSConstants.EDIT_ROOM_MASTER);

	}
	/**
	 * save method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return saveRooms
	 * @throws Exception
	 */
	public ActionForward saveRooms(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside saveRooms");
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		Iterator<HlRoomTO> rmItr = roomForm.getRoomList().iterator();
		HlRoomTO hlRoomTO; 
		Boolean isAdded;
		try {
			//room no. empty checking
			while(rmItr.hasNext()){
				hlRoomTO = rmItr.next();
				if(hlRoomTO.getName() == null || hlRoomTO.getName().trim().isEmpty()){
					if (errors.get("knowledgepro.hostel.room.required") != null && !errors.get("knowledgepro.hostel.room.required").hasNext()) {
						errors.add("error", new ActionError("knowledgepro.hostel.room.required"));
					}
				}
//				break;
			}
			if (!errors.isEmpty()) {
				setRoomTypeListToRequest(roomForm, request);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SUBMIT_ROOM_MASTER);
			}
			//room type empty checking
			Iterator<HlRoomTO> rmItr1 = roomForm.getRoomList().iterator();
			while (rmItr1.hasNext()){
				hlRoomTO = rmItr1.next();
				if(hlRoomTO.getName() != null && !hlRoomTO.getName().trim().isEmpty() &&
					hlRoomTO.getRoomTypeId() == 0){
					errors.add("error", new ActionError("knowledgepro.hostel.room.type.required.room", hlRoomTO.getName()));
				}
			}
			if (errors != null && !errors.isEmpty()) {
				setRoomTypeListToRequest(roomForm, request);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SUBMIT_ROOM_MASTER);
			}
			
			isAdded = AssignRoomMasterHandler.getInstance().saveRoomDetails(roomForm); 
			setRoomTypeListToRequest(roomForm, request);
			setHostelListToRequest(request);
		} catch (DuplicateException e1) {
			if(roomForm.getFormValueDupl()){
				errors.add("error", new ActionError("knowledgepro.hostel.room.room.no.dupl", roomForm.getRoomNo()));
			}
			else
			{
				errors.add("error", new ActionError("knowledgepro.hostel.room.room.no.exist", roomForm.getHostelName()));
			}
			saveErrors(request, errors);
			setRoomTypeListToRequest(roomForm, request);
			return mapping.findForward(CMSConstants.SUBMIT_ROOM_MASTER);
		} catch (Exception e) {
			log.error("error in saveRooms...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				roomForm.setErrorMessage(msg);
				roomForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.room.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			resetFields(roomForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.room.addfailure"));
			saveErrors(request, errors);
		}
		
		log.debug("leaving saveRooms");
		return mapping.findForward(CMSConstants.ASSIGN_ROOM_MASTER);
	}	
	/**
	 * reset
	 * @param roomForm
	 */
	public void resetFields(AssignRoomMasterForm roomForm){
		roomForm.setFloorNo(null);
		roomForm.setNoOfRooms(null);
		roomForm.setHostelId(null);
		roomForm.setRoomMaster(null);
		roomForm.setUnitMap(null);
		roomForm.setBlockMap(null);
		roomForm.setFloorMap(null);
	}
	/**
	 * validation for No of Room
	 * @param noOfroom
	 * @return
	 */
	private boolean validateRoomNo(String noOfroom)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^0-9]+");
        Matcher matcher = pattern.matcher(noOfroom);
        result = matcher.find();
        return result;

	}
	/**
	 * setting floor map to request
	 * @param request
	 * @param roomForm
	 */
	public void setFloorMapToRequest(HttpServletRequest request,  AssignRoomMasterForm roomForm) {
		if (roomForm.getHostelId() != null
				&& !(roomForm.getHostelId().isEmpty())) {
			Map<Integer, String> floorMap = CommonAjaxHandler.getInstance().getFloorsByHostel(Integer.parseInt(roomForm.getUnitId()));
			request.setAttribute("floorMap", floorMap);
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
	public ActionForward searchRoomMaster(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside submitRoomMaster");
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		
		ActionErrors errors = new ActionErrors();
		validateSearchRoom(roomForm,errors);
		try
		{
			if (errors != null && !errors.isEmpty()) 
			{
				saveErrors(request, errors);
			}
			else
			{
				roomForm.setUnitId(roomForm.getUnit());
				roomForm.setBlockId(roomForm.getBlock());
				roomForm.setBlockNameForEdit(roomForm.getBlockName());
				roomForm.setUnitNameForEdit(roomForm.getUnitName());
				AssignRoomMasterTo assignRoomMasterTo=AssignRoomMasterHandler.getInstance().getRoomTo(roomForm);
				if(assignRoomMasterTo==null)
				{
					errors.add("error", new ActionError("knowledgepro.viewTimeTable.norecordsfound"));
					saveErrors(request, errors);
					roomForm.setRoomMaster(assignRoomMasterTo);
					setDataToForm(roomForm);
				}
				else
				{
					roomForm.setSearchedFloorNumber(roomForm.getFloorNo());
					roomForm.setSearchedHostelId(roomForm.getHostelId());
					roomForm.setRoomMaster(assignRoomMasterTo);
				}	
			}	
		}
		catch (Exception e) 
		{
			log.error("error in submitRoomMaster...", e);
			if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				roomForm.setErrorMessage(msg);
				roomForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else
			{
				throw e;
			}
		}
		setHostelListToRequest(request);
		setFloorMapToRequest(request, roomForm);
		log.debug("leaving submitRoomMaster");
		return mapping.findForward(CMSConstants.ASSIGN_ROOM_MASTER);	
	}
	/**
	 * @param roomForm
	 * @param errors
	 */
	private void validateSearchRoom(AssignRoomMasterForm roomForm,ActionErrors errors) 
	{
		if(roomForm.getHostelId()==null || roomForm.getHostelId().isEmpty())
		{
			errors.add("error", new ActionError("knowledgepro.hostel.hostel.name.required"));
		}
		if(roomForm.getFloorNo()==null || roomForm.getFloorNo().isEmpty())
		{
			errors.add("error", new ActionError("knowledgepro.hostel.room.floor.no.required"));
		}
		if(roomForm.getBlock() == null || roomForm.getBlock().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.Hostel.room.block.required"));
		}
		if(roomForm.getUnit() == null || roomForm.getUnit().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.Hostel.room.unit.required"));
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
	public ActionForward editRoomMaster(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		try {
			//creating roomList and setting to form
			List<HlRoomTO> roomList = AssignRoomMasterHandler.getInstance().getRoomList(roomForm); 
			if(roomList!=null && !roomList.isEmpty()){
				Collections.sort(roomList);
			}
			roomForm.setRoomList(roomList);
			roomForm.setNoOfRooms(Integer.toString(roomList.size()));
			setRoomTypeListToRequest(roomForm, request);
		} catch (Exception e) {
			log.error("error in submitRoomMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				roomForm.setErrorMessage(msg);
				roomForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		return mapping.findForward(CMSConstants.EDIT_ROOM_MASTER);	
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteRoom(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			//creating roomList and setting to form
			boolean isDeleted=AssignRoomMasterHandler.getInstance().deleteRoom(roomForm.getRoomNo());
			if(isDeleted)
			{
				ActionMessage message = new ActionMessage("knowledgepro.hostel.room.room.deleted.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			else
			{
				errors.add("error", new ActionError("knowledgepro.hostel.room.addfailure"));
				saveErrors(request, errors);
			}
			List<HlRoomTO> roomList = AssignRoomMasterHandler.getInstance().getRoomList(roomForm); 
			roomForm.setRoomList(roomList);
			roomForm.setNoOfRooms(Integer.toString(roomList.size()));
			setRoomTypeListToRequest(roomForm, request);
		} catch (Exception e) {
			log.error("error in submitRoomMaster...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				roomForm.setErrorMessage(msg);
				roomForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		return mapping.findForward(CMSConstants.EDIT_ROOM_MASTER);	
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateRooms(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside update rooms");
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		Iterator<HlRoomTO> rmItr = roomForm.getRoomList().iterator();
		HlRoomTO hlRoomTO; 
		Boolean isUpdated=false;
		try {
			//room no. empty checking
			if(isCancelled(request))
			{
				List<HlRoomTO> roomList = AssignRoomMasterHandler.getInstance().getRoomList(roomForm); 
				roomForm.setRoomList(roomList);
				roomForm.setNoOfRooms(Integer.toString(roomList.size()));
				setRoomTypeListToRequest(roomForm, request);
				return mapping.findForward(CMSConstants.EDIT_ROOM_MASTER);	
			}
			else
			{
			
				while(rmItr.hasNext()){
					hlRoomTO = rmItr.next();
					if(hlRoomTO.getName() == null || hlRoomTO.getName().trim().isEmpty()){
						if (errors.get("knowledgepro.hostel.room.required") != null && !errors.get("knowledgepro.hostel.room.required").hasNext()) {
							errors.add("error", new ActionError("knowledgepro.hostel.room.required"));
						}
					}
				}
				if (errors != null && !errors.isEmpty()) {
					setRoomTypeListToRequest(roomForm, request);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.EDIT_ROOM_MASTER);
				}
				//room type empty checking
				Iterator<HlRoomTO> rmItr1 = roomForm.getRoomList().iterator();
				while (rmItr1.hasNext()){
					hlRoomTO = rmItr1.next();
					if(hlRoomTO.getName() != null && !hlRoomTO.getName().trim().isEmpty() &&
						hlRoomTO.getRoomTypeId() == 0){
						errors.add("error", new ActionError("knowledgepro.hostel.room.type.required.room", hlRoomTO.getName()));
					}
				}
				if (!errors.isEmpty()) {
					setRoomTypeListToRequest(roomForm, request);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.EDIT_ROOM_MASTER);
				}
				
				isUpdated = AssignRoomMasterHandler.getInstance().updateRoomDetails(roomForm);
				setRoomTypeListToRequest(roomForm, request);
				setHostelListToRequest(request);
			}	
			
		} catch (DuplicateException e1) {
			if(roomForm.getFormValueDupl()){
				errors.add("error", new ActionError("knowledgepro.hostel.room.room.no.dupl", roomForm.getRoomNo()));
			}
			else
			{
				errors.add("error", new ActionError("knowledgepro.hostel.room.room.no.exist", roomForm.getHostelName()));
			}
			saveErrors(request, errors);
			setRoomTypeListToRequest(roomForm, request);
			return mapping.findForward(CMSConstants.EDIT_ROOM_MASTER);
		} catch (Exception e) {
			log.error("error in saveRooms...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				roomForm.setErrorMessage(msg);
				roomForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.hostel.room.updateSuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			resetFields(roomForm);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.hostel.room.updateFailure"));
			saveErrors(request, errors);
		}
		
		log.debug("leaving saveRooms");
		return mapping.findForward(CMSConstants.ASSIGN_ROOM_MASTER);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayHomePage(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		AssignRoomMasterForm roomForm = (AssignRoomMasterForm) form;
		roomForm.setNoOfRooms(null);
		roomForm.setHostelId(null);
		return mapping.findForward(CMSConstants.ASSIGN_ROOM_MASTER);	
	}
}