package com.kp.cms.actions.hostel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.hostel.RoomTypeForm;
import com.kp.cms.handlers.admin.CertificateDetailsHandler;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.RoomTypeHandler;
import com.kp.cms.to.admin.CertificatePurposeTO;
import com.kp.cms.to.fee.FeeAdditionalTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeFeesTO;
import com.kp.cms.to.hostel.RoomTypeImageTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.utilities.RenderYearList;

@SuppressWarnings("deprecation")
public class RoomTypeAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(RoomTypeAction.class);
	private static final String IMAGELIST = "imageList";
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns the roomtype home page with all the hostels and facilities
	 * Also displays all the roomtypes already available
	 * @throws Exception
	 */
	public ActionForward initRoomType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init room type action");	
		RoomTypeForm roomTypeForm = (RoomTypeForm) form;
		try {
			setAllHostelsToForm(roomTypeForm);
			setAllFacilityToForm(roomTypeForm);
			setAllRoomTypeToForm(roomTypeForm);
			roomTypeForm.clear();
			roomTypeForm.setImageList(null);
		} catch (Exception e) {
			log.error("Error occured in initRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			roomTypeForm.setErrorMessage(msg);
			roomTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  init room type action");
		return mapping.findForward(CMSConstants.INIT_ROOM_TYPE);		
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Works for adding roomtype
	 * @throws Exception
	 */
	public ActionForward submitRoomType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submit room type action");	
		RoomTypeForm roomTypeForm = (RoomTypeForm)form;
		 ActionErrors errors = roomTypeForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		//errors = validateFacility(errors, roomTypeForm);
		validateFormSpecialCharacter(roomTypeForm, errors, request);
		try {
			if(roomTypeForm.getDescription()!= null && !roomTypeForm.getDescription().trim().isEmpty() && roomTypeForm.getDescription().length() > 250){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ROOM_TYPE_DESC_MORE));
				addErrors(request, errors);
				setAllHostelsToForm(roomTypeForm);
				setAllFacilityToForm(roomTypeForm);
				setAllRoomTypeToForm(roomTypeForm);
				return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
			}
			if(errors.isEmpty()){
				setUserId(request, roomTypeForm);
				//Get the hostelId and roomtype from form and check for the duplicate
				int hostelId = Integer.parseInt(roomTypeForm.getHostelId());
				String roomType = roomTypeForm.getRoomType();
				HlRoomType type = RoomTypeHandler.getInstance().getRoomTypeOnHostelName(hostelId, roomType);
				if(type!=null){
					if(type.getIsActive()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_DUPLICATE));
						setAllHostelsToForm(roomTypeForm);
						setAllFacilityToForm(roomTypeForm);
						setAllRoomTypeToForm(roomTypeForm);
						addErrors(request, errors);
					}
					else if(!type.getIsActive()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_REACTIVATE));
						setAllHostelsToForm(roomTypeForm);
						setAllFacilityToForm(roomTypeForm);
						setAllRoomTypeToForm(roomTypeForm);
						roomTypeForm.setOldHostelId(String.valueOf(hostelId));
						roomTypeForm.setOldRoomType(roomType);
						addErrors(request, errors);
					}
				}
				else if(type==null){
					boolean isAdded;
					isAdded = RoomTypeHandler.getInstance().submitRoomType(roomTypeForm);
					if(isAdded){
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_ROOMTYPE_ADD_SUCCESS));
						roomTypeForm.clear();
						setAllHostelsToForm(roomTypeForm);
						setAllFacilityToForm(roomTypeForm);
						setAllRoomTypeToForm(roomTypeForm);
						roomTypeForm.setImageList(null);
						saveMessages(request, messages);
					}
					else{
						setAllHostelsToForm(roomTypeForm);
						setAllFacilityToForm(roomTypeForm);
						setAllRoomTypeToForm(roomTypeForm);
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_ADD_FAILED));
						addErrors(request, errors);
					}
				}
			}
			else{
				setAllHostelsToForm(roomTypeForm);
				setAllRoomTypeToForm(roomTypeForm);
				saveMessages(request, messages);
				resetFacility(roomTypeForm);
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in initRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			roomTypeForm.setErrorMessage(msg);
			roomTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  submit room type action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);		
	}	
	/**
	 * Used to delete a roomtype
	 */
	public ActionForward deleteRoomType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  deleteRoomType action");
		RoomTypeForm roomTypeForm = (RoomTypeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, roomTypeForm);
			boolean isDeleted=false;
			int roomTypeId = Integer.parseInt(roomTypeForm.getRoomTypeId());
			String userId = roomTypeForm.getUserId();
			//Request the handler to delete the selected roomtype
			isDeleted = RoomTypeHandler.getInstance().deleteRoomType(roomTypeId, userId);
			//If success then append the success message
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_ROOMTYPE_DELETE_SUCCESS));
				setAllHostelsToForm(roomTypeForm);
				setAllFacilityToForm(roomTypeForm);
				setAllRoomTypeToForm(roomTypeForm);
				roomTypeForm.clear();
				roomTypeForm.setImageList(null);
				saveMessages(request, messages);
			}
			//Else add the error message
			else {
				setAllHostelsToForm(roomTypeForm);
				setAllFacilityToForm(roomTypeForm);
				setAllRoomTypeToForm(roomTypeForm);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_DELETE_FAILED));
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in deleteRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			roomTypeForm.setErrorMessage(msg);
			roomTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  deleteRoomType action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);	
	}	
	/**
	 * 
	 * @param roomTypeForm
	 * Gets all the hostels 
	 * Required to display in the dropdown
	 * @throws Exception
	 */
	public void setAllHostelsToForm(RoomTypeForm roomTypeForm)throws Exception {
		log.info("entering into setAllHostelsToForm RoomType Action");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		roomTypeForm.setHostelList(hostelList);
		log.info("Leaving into setAllHostelsToForm RoomType Action");
	}
	/**
	 * 
	 * @param roomTypeForm
	 * Gets all the Facilities from Facility Master
	 * @throws Exception
	 */
	public void setAllFacilityToForm(RoomTypeForm roomTypeForm)throws Exception {
		log.info("entering into setAllFacilityToForm RoomType Action");
		List<FacilityTO> facilityList = RoomTypeHandler.getInstance().getAllFacility();
		roomTypeForm.setFacilityList(facilityList);
		log.info("Leaving into setAllFacilityToForm RoomType Action");
	}
	
	/**
	 * Used to prepare image List in Add Mode
	 * For multiple images
	 */
	public ActionForward prepareImageListInAddMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into prepareImageListInAddMode RoomType Action");
		RoomTypeForm roomTypeForm = (RoomTypeForm)form;
		HttpSession session = request.getSession(false);
		RoomTypeImageTO imageTO = null;		
		ActionErrors errors = new ActionErrors();	
		if(roomTypeForm.getImage()!=null && roomTypeForm.getImage().getFileName()!=null
			&& !roomTypeForm.getImage().getFileName().equals("")){
			List<RoomTypeImageTO> imageList = roomTypeForm.getImageList();
			if(imageList==null)
			{
				imageList=new ArrayList<RoomTypeImageTO>();
			}
			if(imageList.size() < 5){
				errors = validateImageSize(roomTypeForm, errors, request);	
				if(errors.isEmpty()){
					imageTO = new RoomTypeImageTO();
					imageTO.setImage(roomTypeForm.getImage().getFileData());
					imageTO.setCountId(imageList.size()+1);
					imageList.add(imageTO);
					roomTypeForm.setImage(null);
					roomTypeForm.setImageList(imageList);
						if(session.getAttribute(IMAGELIST)!=null){
							session.removeAttribute(IMAGELIST);
						}
					session.setAttribute(IMAGELIST, imageList);
				}
			}
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_ADD_COMPLETE));
				addErrors(request, errors);
			}
		}
		resetFacility(roomTypeForm);
		log.info("Leaving into prepareImageListInAddMode RoomType Action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
	}
	/**
	 * Used to prepare image List in Update Mode
	 * For multiple images
	 */
	public ActionForward prepareImageListInUpdateMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into prepareImageListInUpdateMode RoomType Action");
		RoomTypeForm roomTypeForm = (RoomTypeForm)form;
		HttpSession session = request.getSession(false);
		RoomTypeImageTO imageTO = null;
		ActionErrors errors = new ActionErrors();
		
		if(roomTypeForm.getImage()!=null && roomTypeForm.getImage().getFileName()!=null
		&& !roomTypeForm.getImage().getFileName().equals("")){
			List<RoomTypeImageTO> imageList = roomTypeForm.getImageList();
			if(imageList==null)
			{
				imageList=new ArrayList<RoomTypeImageTO>();
			}
			if(imageList.size() < 5){
				errors = validateImageSize(roomTypeForm, errors, request);
				if(errors.isEmpty()){
					imageTO = new RoomTypeImageTO();
					imageTO.setImage(roomTypeForm.getImage().getFileData());
					imageTO.setCountId(imageList.size()+1);
					imageList.add(imageTO);
					roomTypeForm.setImage(null);
					roomTypeForm.setImageList(imageList);
						if(session.getAttribute(IMAGELIST)!=null){
							session.removeAttribute(IMAGELIST);
						}
					session.setAttribute(IMAGELIST, imageList);
				}
			}
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_ADD_COMPLETE));
				addErrors(request, errors);
			}
		}
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		resetFacility(roomTypeForm);
		log.info("Leaving into prepareImageListInUpdateMode RoomType Action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
	}
	
	/**
	 * Used to validate facility and quantity
	 */
	public ActionErrors validateFacility(ActionErrors errors, RoomTypeForm roomTypeForm)throws Exception{
		log.info("Entering into validateFacility of RoomType Action");
		List<FacilityTO> facilityList = roomTypeForm.getFacilityList();
		if(facilityList!=null && !facilityList.isEmpty()){
			Iterator<FacilityTO>iterator = facilityList.iterator();
			while (iterator.hasNext()) {
				FacilityTO facilityTO =  iterator.next();
				if(facilityTO.isSelected()){
					if(facilityTO.getQuantity()==null || StringUtils.isEmpty(facilityTO.getQuantity())){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_QUANTITY_REQUIRED));
					}
					else if(facilityTO.getQuantity()!=null && !StringUtils.isNumeric(facilityTO.getQuantity())){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_QUANTITY_INTEGER));
					}
				}
			}
		}	
		log.info("Leaving into validateFacility of RoomType Action");
		return errors;		
	}
	/**
	 * 
	 * @param roomTypeForm
	 * Gets all roomtype to form
	 * @throws Exception
	 */
	public void setAllRoomTypeToForm(RoomTypeForm roomTypeForm)throws Exception {
		log.info("Entering into setAllRoomTypeToForm of RoomType Action");
		List<RoomTypeTO> roomTypeList = RoomTypeHandler.getInstance().getAllRoomType();
		roomTypeForm.setRoomTypeList(roomTypeList);
		log.info("Leaving into setAllRoomTypeToForm of RoomType Action");
	}
	/**
	 * Used to reactivate roomtype
	 */
	public ActionForward reactivateRoomType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into reactivateRoomType of RoomType Action");
		RoomTypeForm roomTypeForm = (RoomTypeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, roomTypeForm);
			boolean isReactivate;
			int hostelId = Integer.parseInt(roomTypeForm.getOldHostelId());
			String roomType = roomTypeForm.getOldRoomType();
			String userId = roomTypeForm.getUserId();
			isReactivate = RoomTypeHandler.getInstance().reactivateRoomType(hostelId,roomType,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_ROOMTYPE_REACTIVATE_SUCCESS));
				setAllHostelsToForm(roomTypeForm);
				setAllFacilityToForm(roomTypeForm);
				setAllRoomTypeToForm(roomTypeForm);
				roomTypeForm.clear();
				roomTypeForm.setImageList(null);
				saveMessages(request, messages);
			}
			else{
				setAllHostelsToForm(roomTypeForm);
				setAllFacilityToForm(roomTypeForm);
				setAllRoomTypeToForm(roomTypeForm);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_REACTIVATE_FAILED));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			roomTypeForm.setErrorMessage(msg);
			roomTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivateRoomType of RoomType Action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
	}

	/**
	 * Used when edit button is clicked
	 */

	public ActionForward editRoomType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into editRoomType of RoomType Action");
		RoomTypeForm roomTypeForm = (RoomTypeForm)form;
		try {
			RoomTypeHandler.getInstance().getDetailByRoomTypeId(roomTypeForm, request);
			setAllHostelsToForm(roomTypeForm);
			setAllRoomTypeToForm(roomTypeForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in editRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			roomTypeForm.setErrorMessage(msg);
			roomTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into editRoomType of RoomType Action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
	}
	/**
	 * Used to update roomtype
	 */
	public ActionForward updateRoomType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into updateRoomType of RoomType Action");
		RoomTypeForm roomTypeForm = (RoomTypeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = roomTypeForm.validate(mapping, request);
		//errors = validateFacility(errors, roomTypeForm);
		validateFormSpecialCharacter(roomTypeForm, errors, request);
		try {			
			//This condition works when reset button will click in update mode
			if(isCancelled(request)){
				RoomTypeHandler.getInstance().getDetailByRoomTypeId(roomTypeForm, request);
				setAllHostelsToForm(roomTypeForm);
				setAllRoomTypeToForm(roomTypeForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			}
			else if(roomTypeForm.getDescription()!= null && !roomTypeForm.getDescription().trim().isEmpty() && roomTypeForm.getDescription().length() > 250){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ROOM_TYPE_DESC_MORE));
				addErrors(request, errors);
				setAllHostelsToForm(roomTypeForm);
				setAllFacilityToForm(roomTypeForm);
				setAllRoomTypeToForm(roomTypeForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
			}
			// This condition when when there will be no validation error occurs
			else if(errors.isEmpty()) {
				setUserId(request, roomTypeForm);
				boolean isUpdated;
				int oldHostelId = Integer.parseInt(roomTypeForm.getOldHostelId());
				String oldRoomType = roomTypeForm.getOldRoomType().trim();
				//This condition works when the user keeps as the same hostel and roomtype as it was saved
				if(oldHostelId == Integer.parseInt(roomTypeForm.getHostelId().trim()) && 
					oldRoomType.equalsIgnoreCase(roomTypeForm.getRoomType().trim())){
					isUpdated = RoomTypeHandler.getInstance().updateRoomType(roomTypeForm);
					//If update is success
					if(isUpdated){
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_ROOMTYPE_UPDATE_SUCCESS));
						setAllHostelsToForm(roomTypeForm);
						setAllFacilityToForm(roomTypeForm);
						setAllRoomTypeToForm(roomTypeForm);
						roomTypeForm.clear();
						roomTypeForm.setImageList(null);
						saveMessages(request, messages);
					}
					//If update is failure
					else{
						setAllHostelsToForm(roomTypeForm);
						setAllFacilityToForm(roomTypeForm);
						setAllRoomTypeToForm(roomTypeForm);
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_UPDATE_FAILED));
						addErrors(request, errors);
					}
				}
				//This condition works when if the user changed hostel or roomtype or both
				else if(oldHostelId != Integer.parseInt(roomTypeForm.getHostelId().trim()) || 
						!oldRoomType.equalsIgnoreCase(roomTypeForm.getRoomType().trim())){
					int hostelId = Integer.parseInt(roomTypeForm.getHostelId());
					String roomType = roomTypeForm.getRoomType();			
					HlRoomType type = RoomTypeHandler.getInstance().getRoomTypeOnHostelName(hostelId, roomType);
					if(type!=null){
						if(type.getIsActive()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_DUPLICATE));
							setAllHostelsToForm(roomTypeForm);
							setAllFacilityToForm(roomTypeForm);
							setAllRoomTypeToForm(roomTypeForm);
							addErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
						}
						else if(!type.getIsActive()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_REACTIVATE));
							setAllHostelsToForm(roomTypeForm);
							setAllFacilityToForm(roomTypeForm);
							setAllRoomTypeToForm(roomTypeForm);
							roomTypeForm.setOldHostelId(String.valueOf(hostelId));
							roomTypeForm.setOldRoomType(roomType);
							addErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
						}
					}
					else{
						//Request the handler in order to update the roomtype
						isUpdated = RoomTypeHandler.getInstance().updateRoomType(roomTypeForm);
						//If update is success
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_ROOMTYPE_UPDATE_SUCCESS));
							setAllHostelsToForm(roomTypeForm);
							setAllFacilityToForm(roomTypeForm);
							setAllRoomTypeToForm(roomTypeForm);
							roomTypeForm.clear();
							roomTypeForm.setImageList(null);
							saveMessages(request, messages);
						}
						//If update is failure
						else{
							setAllHostelsToForm(roomTypeForm);
							setAllFacilityToForm(roomTypeForm);
							setAllRoomTypeToForm(roomTypeForm);
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_UPDATE_FAILED));
							addErrors(request, errors);
						}
					}
				}
			}
			//This else part will work when validation error occurs. Only gets the data and save the errors
			else{
				setAllRoomTypeToForm(roomTypeForm);
				setAllHostelsToForm(roomTypeForm);
				resetFacility(roomTypeForm);
				saveMessages(request, messages);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in editRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			roomTypeForm.setErrorMessage(msg);
			roomTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("Leaving into updateRoomType of RoomType Action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
	}
	
	/**
	 * Used to reset the check boxes and quantities
	 */
	public void resetFacility(RoomTypeForm typeForm){
		log.info("Entering into resetFacility of RoomType Action");
		if(typeForm.getFacilityList()!=null && !typeForm.getFacilityList().isEmpty()){
			Iterator<FacilityTO> iterator = typeForm.getFacilityList().iterator();
			while (iterator.hasNext()) {
				FacilityTO facilityTO = (FacilityTO) iterator.next();
				if(facilityTO.isSelected()){
					facilityTO.setDummySelected(true);
					facilityTO.setSelected(false);
				}
				else if(!facilityTO.isSelected()){
					facilityTO.setDummySelected(false);
				}
			}
		}
		log.info("Leaving into resetFacility of RoomType Action");
	}
	
	/**
	 * Validates the uploaded file size.
	 * 
	 * @param Checks for the uploaded file and it allows the user to upload maximum of 2MB size
	 * @param errors
	 * @return ActionMessages
	 */
	private ActionErrors validateImageSize(RoomTypeForm roomTypeForm,
			ActionErrors errors, HttpServletRequest request)throws Exception {
		log.info("Entering into validateImageSize of RomType Action");
		FormFile theFile = null;
		if(roomTypeForm.getImage()!=null){
			theFile = roomTypeForm.getImage();
		}
		InputStream propStream=RenderYearList.class.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES_1);
		int maXSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		 }catch (IOException e) {
			 log.error("Error occured in validateImageSize RomType Action",e);
			 throw new ApplicationException();
		}		 
			if(theFile!=null && maXSize< theFile.getFileSize())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE));
				addErrors(request, errors);
			}
			log.info("Leaving into validateImageSize of RomType Action");
			return errors;
	}
	
	/**
	 * Used to delete image in addMode
	 */

	public ActionForward deleteImageInAddMode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into deleteImageInAddMode of RoomType Action");
		RoomTypeForm typeForm = (RoomTypeForm)form;
		ActionMessages messages = new ActionMessages();
		try {
			if (typeForm.getImageList() != null
			&& !typeForm.getImageList().isEmpty()) {
				typeForm.getImageList().remove(typeForm.getPosition()-1);
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_ROOMTYPE_IMAGE_DELETE_SUCCESS));
			}
			setAllHostelsToForm(typeForm);
			setAllRoomTypeToForm(typeForm);
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error("Error occured in deleteImageInAddMode of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			typeForm.setErrorMessage(msg);
			typeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("Leaving into deleteImageInAddMode of RoomType Action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
	}
	
	/**
	 * Used to delete the image in update mode
	 */
	public ActionForward deleteImageInUpdateMode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into deleteImageInAddMode of RoomType Action");
		RoomTypeForm typeForm = (RoomTypeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			if(typeForm.getImageId()!=0){
				boolean isDeleted;
				isDeleted = RoomTypeHandler.getInstance().deleteRoomTypeImage(typeForm.getImageId());
					if(isDeleted){
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_ROOMTYPE_IMAGE_DELETE_SUCCESS));
						saveMessages(request, messages);
					}
					else{
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_ROOMTYPE_IMAGE_DELETE_FAILED));
						addErrors(request, errors);
					}
				RoomTypeHandler.getInstance().getDetailByRoomTypeId(typeForm, request);
			}
			else{
				 if(typeForm.getPosition()!=0){
					typeForm.getImageList().remove(typeForm.getPosition()-1);
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_ROOMTYPE_IMAGE_DELETE_SUCCESS));
					saveMessages(request, messages);
				 }				
			}
			setAllHostelsToForm(typeForm);
			setAllRoomTypeToForm(typeForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in deleteImageInAddMode of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			typeForm.setErrorMessage(msg);
			typeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("Leaving into deleteImageInAddMode of RoomType Action");
		return  mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
	}
	
	/**
	 * special character validation
	 * 
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name) {
		boolean result = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \\. \\s \t \\/ \\( \\) ]+");

		Matcher matcher = pattern.matcher(name);
		result = matcher.find();
		return result;

	}
	
	private void validateFormSpecialCharacter(RoomTypeForm roomTypeForm, ActionErrors errors, HttpServletRequest request)throws Exception
	{
		if(roomTypeForm.getRoomType()!= null && !roomTypeForm.getRoomType().isEmpty() && nameValidate(roomTypeForm.getRoomType()))
		{
			errors.add("error", new ActionError("knowledgepro.hostel.vistorinfo.specialCharacter","Room Type"));
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
	public ActionForward assignFees(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into deleteImageInAddMode of RoomType Action");
		RoomTypeForm typeForm = (RoomTypeForm)form;
		try {
			//List<FeeGroupTO> assignFees=RoomTypeHandler.getInstance().getAssignFeeForRoom();
			List<FeeGroupTO> assignFees=RoomTypeHandler.getInstance().getAssignFees(typeForm);
			
			typeForm.setFeeList(assignFees);
		} catch (Exception e) {
			log.error("Error occured in deleteImageInAddMode of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			typeForm.setErrorMessage(msg);
			typeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("Leaving into deleteImageInAddMode of RoomType Action");
		return  mapping.findForward(CMSConstants.INIT_ASSIGN_FEES);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAssignFees(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		RoomTypeForm typeForm = (RoomTypeForm)form;
		setUserId(request,typeForm);
		//HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				typeForm.setFeeSelected(false);
				isAdded = RoomTypeHandler.getInstance().addAssignFees(typeForm);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.assign.fees.addsuccess"));
					saveMessages(request, messages);
			   } else {
				    if(!typeForm.isFeeSelected()){
				  //  messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.assign.fees.notSelected"));
				    errors.add("error", new ActionError( "knowledgepro.assign.fees.notSelected"));
					addErrors(request, errors);
					saveErrors(request, errors);
				    return  mapping.findForward(CMSConstants.INIT_ASSIGN_FEES);
				    }
				    else
				    {
					errors.add("error", new ActionError( "knowledgepro.assign.fees.addfailure"));
					addErrors(request, errors);
				    }
		     	}
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				typeForm.setErrorMessage(msg);
				typeForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
		}
	//	setAssignRoomFees(typeForm);
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.INIT_ROOM_TYPE);
	}

	private void setAssignRoomFees(RoomTypeForm typeForm) throws Exception{
		List<RoomTypeFeesTO> roomTypeFeesList=RoomTypeHandler.getInstance().setAssignRoomFees(typeForm);
		typeForm.setRoomTypeFeesList(roomTypeFeesList);
		
	}
	 public ActionForward forwardAssignFees(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	    {
	    	log.info("Entering reActivateCertificateDetails Action");
	    	RoomTypeForm typeForm = (RoomTypeForm)form;
	    	  String formName = mapping.getName();
	          request.getSession().setAttribute("formName", formName);
	          List<FeeGroupTO> feeList = RoomTypeHandler.getInstance().getAssignFees(typeForm);
	          typeForm.setFeeList(feeList);
	          typeForm.clear();
	          //setRolesToForm(certificateDetailsForm);
	    	log.info("Leaving into reActivateCertificateDetails of Action");
	        return mapping.findForward(CMSConstants.ASSIGN_PURPOSE);	
	    }
	
	
	
}
