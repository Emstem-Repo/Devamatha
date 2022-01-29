package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.employee.EventScheduleStudentAttendanceBo;
import com.kp.cms.bo.hostel.HlRoomTypeFees;
import com.kp.cms.bo.admin.HlRoomTypeImage;
import com.kp.cms.forms.hostel.RoomTypeForm;
import com.kp.cms.handlers.hostel.RoomTypeHandler;
import com.kp.cms.to.admin.CertificatePurposeTO;
import com.kp.cms.to.fee.FeeAdditionalTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeFacilityTO;
import com.kp.cms.to.hostel.RoomTypeFeesTO;
import com.kp.cms.to.hostel.RoomTypeImageTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.transactions.hostel.IRoomTypeTransaction;
import com.kp.cms.transactionsimpl.hostel.RoomTypeTransactionImpl;

public class RoomTypeHelper {

private static final Log log = LogFactory.getLog(RoomTypeHelper.class);
	
	public static volatile RoomTypeHelper roomTypeHelper = null;

	private RoomTypeHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static RoomTypeHelper getInstance() {
		if (roomTypeHelper == null) {
			roomTypeHelper = new RoomTypeHelper();
		}
		return roomTypeHelper;
	}	
	/**
	 * Used to convert Facility BO to TO
	 */
	public List<FacilityTO> copyFacilityBOToTO(List<HlFacility> facilityBOList)throws Exception{
		log.info("Entering into copyFacilityBOToTO of RoomTypeHelper");
		List<FacilityTO> facilityList = new ArrayList<FacilityTO>();
		HlFacility facility;
		FacilityTO facilityTO = null;
		if(facilityBOList!=null && !facilityBOList.isEmpty()){
			Iterator<HlFacility> iterator = facilityBOList.iterator();
			while (iterator.hasNext()) {
				facility = iterator.next();
				facilityTO = new FacilityTO();
				facilityTO.setId(facility.getId());
				if(facility.getName() != null){
					facilityTO.setName(facility.getName());
				}
				facilityTO.setDummySelected(false);
				facilityTO.setSelected(false);
				facilityList.add(facilityTO);
				facilityTO.setFacilityCount(facilityList.size());
			}
		}
		log.info("Leaving into copyFacilityBOToTO of RoomTypeHelper");
		return facilityList;
	}
	
	/**
	 * Used while insert
	 * Prepares RoomType BO for Save
	 */	
	public HlRoomType prepareRoomTypeBOForSave(RoomTypeForm roomTypeForm)throws Exception{
		log.info("Entering into prepareRoomTypeBOForSave of RoomTypeHelper");
		HlRoomType roomType = new HlRoomType();
		Set<HlRoomTypeImage> roomTypeImageSet = new HashSet<HlRoomTypeImage>();
		Set<HlRoomTypeFacility> roomTypeFacilitySet = new HashSet<HlRoomTypeFacility>();
		HlRoomTypeImage roomTypeImage = null;
		HlRoomTypeFacility roomTypeFacility = null;
		HlFacility facility = null;
		HlHostel hostel = new HlHostel();		
		if(roomTypeForm.getRoomType().trim() != null){
			roomType.setName(roomTypeForm.getRoomType());
		}
		if(roomTypeForm.getDescription().trim()!= null){
			roomType.setDescription(roomTypeForm.getDescription().trim());
		}
		if(roomTypeForm.getMaxOccupants() != null){
			roomType.setNoOfOccupants(Integer.parseInt(roomTypeForm.getMaxOccupants().trim()));
		}
		if(roomTypeForm.getRoomCategory()!=null && !roomTypeForm.getRoomCategory().isEmpty())
		{
			roomType.setRoomCategory(roomTypeForm.getRoomCategory());
		}
		if(roomTypeForm.getHostelId() != null){
			hostel.setId(Integer.parseInt(roomTypeForm.getHostelId()));
			roomType.setHlHostel(hostel);
		}
		roomType.setIsActive(true);
		roomType.setCreatedBy(roomTypeForm.getUserId());
		roomType.setCreatedDate(new Date());
		roomType.setModifiedBy(roomTypeForm.getUserId());
		roomType.setLastModifiedDate(new Date());
		
		if(roomTypeForm.getFacilityList() != null && !roomTypeForm.getFacilityList().isEmpty()){
			Iterator<FacilityTO> iterator = roomTypeForm.getFacilityList().iterator();
			while (iterator.hasNext()) {
				FacilityTO facilityTO = iterator.next();
				if(facilityTO.isSelected()){
					roomTypeFacility = new HlRoomTypeFacility();
					facility = new HlFacility();
					facility.setId(facilityTO.getId());
					roomTypeFacility.setHlFacility(facility);
					if(facilityTO.getQuantity() != null){
						roomTypeFacility.setQuantity(Integer.parseInt(facilityTO.getQuantity()));
					}
					roomTypeFacility.setCreatedBy(roomTypeForm.getUserId());
					roomTypeFacility.setCreatedDate(new Date());
					roomTypeFacility.setModifiedBy(roomTypeForm.getUserId());
					roomTypeFacility.setLastModifiedDate(new Date());
					roomTypeFacility.setIsActive(true);
					roomTypeFacilitySet.add(roomTypeFacility);
				}
			}
			roomType.setHlRoomTypeFacilities(roomTypeFacilitySet);
		}
		
		if(roomTypeForm.getImageList() != null && !roomTypeForm.getImageList().isEmpty()){
			Iterator<RoomTypeImageTO> iterator = roomTypeForm.getImageList().iterator();
			while (iterator.hasNext()) {
				RoomTypeImageTO roomTypeImageTO = iterator.next();
				if(roomTypeImageTO.getImage()!=null){
					roomTypeImage = new HlRoomTypeImage();
					roomTypeImage.setImage(roomTypeImageTO.getImage());
					roomTypeImage.setIsActive(true);
					roomTypeImage.setCreatedBy(roomTypeForm.getUserId());
					roomTypeImage.setModifiedBy(roomTypeForm.getUserId());
					roomTypeImage.setCreatedDate(new Date());
					roomTypeImage.setLastModifiedDate(new Date());
					roomTypeImageSet.add(roomTypeImage);
				}
			}
			roomType.setHlRoomTypeImages(roomTypeImageSet);
		}
		log.info("Leaving into prepareRoomTypeBOForSave of RoomTypeHelper");
		return roomType;
	}
	
	/**
	 * Converts all Room Type BO to TO
	 * USed while diplaying all roomtype in UI
	 */
	public List<RoomTypeTO> copyRoomTypeBOToTO(List<HlRoomType> roomTypeBOList)throws Exception{
		log.info("Entering into copyRoomTypeBOToTO of RoomTypeHelper");
		List<RoomTypeTO> roomTypeList = new ArrayList<RoomTypeTO>();
		RoomTypeTO roomTypeTO = null;
		HostelTO hostelTO = null;
		if(roomTypeBOList!=null && !roomTypeBOList.isEmpty()){
			Iterator<HlRoomType> it = roomTypeBOList.iterator();
			while (it.hasNext()) {
				HlRoomType hlRoomType = (HlRoomType) it.next();
				roomTypeTO = new RoomTypeTO();				
				roomTypeTO.setId(String.valueOf(hlRoomType.getId()));
				if(hlRoomType.getName() != null){
					roomTypeTO.setName(hlRoomType.getName());
				}
				if(hlRoomType.getHlHostel() != null){
					hostelTO = new HostelTO();
					hostelTO.setId(hlRoomType.getHlHostel().getId());
					if(hlRoomType.getHlHostel().getName() != null){
						hostelTO.setName(hlRoomType.getHlHostel().getName());
					}
					roomTypeTO.setHostelTO(hostelTO);
				}
				roomTypeList.add(roomTypeTO);
			}
		}
		log.info("Leaving into copyRoomTypeBOToTO of RoomTypeHelper");
		return roomTypeList;
	}
	
	/**
	 * Used when edit button is clicked
	 * Copies all RoomTYpe BO to TO and also sets to form
	 */
	public void setRoomTypeDetailsToForm(HlRoomType roomType, RoomTypeForm typeForm, HttpServletRequest request)throws Exception{
		log.info("Entering into setRoomTypeDetailsToForm of RoomTypeHelper");
		if(roomType!=null){
			typeForm.setRoomTypeId(String.valueOf(roomType.getId()));
			if(roomType.getHlHostel() != null){
				typeForm.setHostelId(String.valueOf(roomType.getHlHostel().getId()));
				typeForm.setOldHostelId(String.valueOf(roomType.getHlHostel().getId()));
			}
			if(roomType.getName() != null){
				typeForm.setRoomType(roomType.getName());
				typeForm.setOldRoomType(roomType.getName());
			}
			if(roomType.getDescription() != null){
				typeForm.setDescription(roomType.getDescription());
			}
			if(roomType.getNoOfOccupants() != null){
				typeForm.setMaxOccupants(String.valueOf(roomType.getNoOfOccupants()));
			}
			if(roomType.getRoomCategory()!=null){
				typeForm.setRoomCategory(roomType.getRoomCategory());
			}
			//Used For Image
			if(roomType.getHlRoomTypeImages() != null && !roomType.getHlRoomTypeImages().isEmpty()){
				Set<HlRoomTypeImage> imageSet = roomType.getHlRoomTypeImages();
				//Send the BO and prepare the images and set to form
				setImagesToFormWhileEdit(typeForm,imageSet, request);
			}
			else{
				typeForm.setImageList(null);
			}
			//Used for facility
			if(roomType.getHlRoomTypeFacilities() != null && !roomType.getHlRoomTypeFacilities().isEmpty()){
				Set<HlRoomTypeFacility> facilityBOSet = roomType.getHlRoomTypeFacilities();
				setFacilitiesToForm(facilityBOSet, typeForm);
			}
			else{
				List<FacilityTO> facilityList = RoomTypeHandler.getInstance().getAllFacility();
				typeForm.setFacilityList(facilityList);
			}
		}
		log.info("Leaving into setRoomTypeDetailsToForm of RoomTypeHelper");
	}
	
	/**
	 * Gets the already present facilities
	 */
	public void setFacilitiesToForm(Set<HlRoomTypeFacility> facilityBOSet,RoomTypeForm typeForm)throws Exception{
		log.info("Entering into setFacilitiesToForm of RoomTypeHelper");
		//Gets all facilities
		List<FacilityTO> facilityList = RoomTypeHandler.getInstance().getAllFacility();
		Map<Integer, String> facilityMap = new HashMap<Integer, String>();
		//Used to keep facility id as key and values as Roomtypefacilty TO, WIll used in update mode
		Map<Integer, RoomTypeFacilityTO>  roomTypeFacilityMap = new HashMap<Integer, RoomTypeFacilityTO>();
		Map<Integer, Integer> inActiveRoomTypeFacilityMap=new HashMap<Integer, Integer>();
		//Used to get already present facility Ids and quantities
		if(facilityBOSet!=null){
			Iterator<HlRoomTypeFacility> iterator = facilityBOSet.iterator();
			while (iterator.hasNext()) {
				HlRoomTypeFacility hlRoomTypeFacility = iterator.next();
				int facilityId = 0;
				String quantity = "";
				if(hlRoomTypeFacility.getIsActive() && hlRoomTypeFacility.getHlFacility() != null){
					facilityId = hlRoomTypeFacility.getHlFacility().getId();
					if(hlRoomTypeFacility.getQuantity() != null){
						quantity = String.valueOf(hlRoomTypeFacility.getQuantity());
					}
					facilityMap.put(facilityId, quantity);					
					RoomTypeFacilityTO roomTypeFacilityTO = new RoomTypeFacilityTO();
					RoomTypeTO roomTypeTO = new RoomTypeTO();
					roomTypeFacilityTO.setId(hlRoomTypeFacility.getId());
					
					if(hlRoomTypeFacility.getHlRoomType() != null){
						roomTypeTO.setId(String.valueOf(hlRoomTypeFacility.getHlRoomType().getId()));
					}				
					roomTypeFacilityTO.setRoomTypeTO(roomTypeTO);
					roomTypeFacilityMap.put(facilityId, roomTypeFacilityTO);					
				}//Added by Dilip
				else if(!hlRoomTypeFacility.getIsActive() && hlRoomTypeFacility.getHlFacility() != null){
					facilityId = hlRoomTypeFacility.getId();
					inActiveRoomTypeFacilityMap.put(facilityId, facilityId);
				}
				//
			}
		}
		//Keeps in form. will used while update
		typeForm.setRoomTypeFacilityMap(roomTypeFacilityMap);
		typeForm.setInActiveRoomTypeFacilityMap(inActiveRoomTypeFacilityMap);
		List<FacilityTO> facilityListnew=new ArrayList<FacilityTO>();
		//Iterate all the facility List and check which are already present and get their quantity and set to form
		if(facilityList!=null && !facilityList.isEmpty()){
			Iterator<FacilityTO>itr = facilityList.iterator();
			while (itr.hasNext()) {
				FacilityTO facilityTO = itr.next();
				if(facilityMap != null && facilityMap.containsKey(facilityTO.getId())){
					facilityTO.setQuantity(String.valueOf(facilityMap.get(facilityTO.getId())));
					facilityTO.setDummySelected(true);
					facilityTO.setName(facilityTO.getName());
					facilityListnew.add(facilityTO);
				}//  /* code added by chandra
				else{
					facilityTO.setQuantity(String.valueOf(facilityTO.getId()));
					facilityTO.setId(facilityTO.getId());
					facilityTO.setDummySelected(false);
					facilityTO.setSelected(false);
					facilityTO.setName(facilityTO.getName());
					facilityListnew.add(facilityTO);
				}
				// */
			}
		}
		log.info("Leaving into setFacilitiesToForm of RoomTypeHelper");
		typeForm.setFacilityList(facilityListnew);
	}
		
	
	/**
	 * 
	 * @param roomTypeForm
	 * @param imageSet
	 * @param request
	 * Preapres image List while Edit
	 * @throws Exception
	 */
	public void setImagesToFormWhileEdit(RoomTypeForm roomTypeForm, Set<HlRoomTypeImage> imageSet, HttpServletRequest request)throws Exception{
		log.info("Entering into setImagesToFormWhileEdit of RoomTypeHelper");
		Map<Integer, Integer> roomTypeImageMap = new HashMap<Integer, Integer>();
		if(imageSet!=null && !imageSet.isEmpty()){
			HttpSession session = request.getSession(false);
			List<RoomTypeImageTO> imageList = new ArrayList<RoomTypeImageTO>();
			HlRoomTypeImage typeImage = null;
			RoomTypeImageTO imageTO = null;
			Iterator<HlRoomTypeImage> it = imageSet.iterator();
			while (it.hasNext()) {
				typeImage = (HlRoomTypeImage) it.next();
				if(typeImage.getImage() != null){
					imageTO = new RoomTypeImageTO();
					imageTO.setId(typeImage.getId());
					imageTO.setImage(typeImage.getImage());
					imageTO.setCountId(imageList.size()+1);
					imageList.add(imageTO);
					
					int roomTypeId = 0;
					if(typeImage.getHlRoomType() != null){
						roomTypeId = typeImage.getHlRoomType().getId();
					}
					roomTypeImageMap.put(typeImage.getId(), roomTypeId);
				}
			}
			roomTypeForm.setImageList(imageList);
			if(session.getAttribute("imageList")!=null){
				session.removeAttribute("imageList");
			}
			session.setAttribute("imageList", imageList);
		}
		roomTypeForm.setRoomTypeImageMap(roomTypeImageMap);
		log.info("Leaving into setImagesToFormWhileEdit of RoomTypeHelper");
	}
	
	/**
	 * Used to update a roomtype
	 */
	public HlRoomType populateTOToBOWhileUpdate(RoomTypeForm typeForm)throws Exception{
		log.info("Entering into populateTOToBOWhileUpdate of RoomTypeHelper");
		
		Map<Integer, RoomTypeFacilityTO> roomTypeFacilityMap = typeForm.getRoomTypeFacilityMap();
		Map<Integer, Integer> roomTypeImageMap = typeForm.getRoomTypeImageMap();
		Map<Integer, Integer> inActiveRoomTypeFacilityMap=typeForm.getInActiveRoomTypeFacilityMap();
		
		Set<HlRoomTypeImage> roomTypeImageSet = new HashSet<HlRoomTypeImage>();
		Set<HlRoomTypeFacility> roomTypeFacilitySet = new HashSet<HlRoomTypeFacility>();
		
		HlRoomTypeImage roomTypeImage = null;
		HlRoomTypeFacility roomTypeFacility = null;
		HlFacility facility = null;		
		
		HlRoomType roomType = new HlRoomType();
		HlHostel hostel = new HlHostel();
		
		roomType.setId(Integer.parseInt(typeForm.getRoomTypeId()));
		hostel.setId(Integer.parseInt(typeForm.getHostelId()));
		roomType.setHlHostel(hostel);
		roomType.setName(typeForm.getRoomType());
		roomType.setNoOfOccupants(Integer.parseInt(typeForm.getMaxOccupants()));
		if(typeForm.getDescription()!=null){
			roomType.setDescription(typeForm.getDescription());
		}
		if(typeForm.getRoomCategory()!=null && !typeForm.getRoomCategory().isEmpty())
		{
			roomType.setRoomCategory(typeForm.getRoomCategory());
		}
		roomType.setIsActive(true);
		roomType.setLastModifiedDate(new Date());
		roomType.setModifiedBy(typeForm.getUserId());
				
		//Prepares the image list
		if(typeForm.getImageList()!=null && !typeForm.getImageList().isEmpty()){
			Iterator<RoomTypeImageTO> iterator = typeForm.getImageList().iterator();
			while (iterator.hasNext()) {
				RoomTypeImageTO roomTypeImageTO = iterator.next();			
				if(roomTypeImageMap!=null && roomTypeImageMap.containsKey(roomTypeImageTO.getId())){					
					roomTypeImage = new HlRoomTypeImage();					
					roomTypeImage.setId(roomTypeImageTO.getId());					
					HlRoomType roomType1  = new HlRoomType();
					roomType1.setId(roomTypeImageMap.get(roomTypeImageTO.getId()));
					roomTypeImage.setHlRoomType(roomType1);
					
						if(roomTypeImageTO.getImage()!=null){
							roomTypeImage.setImage(roomTypeImageTO.getImage());
						}
					roomTypeImage.setIsActive(true);
					roomTypeImage.setModifiedBy(typeForm.getUserId());
					roomTypeImage.setLastModifiedDate(new Date());					
					roomTypeImageSet.add(roomTypeImage);
					}
				//Works for new images added
				else if(roomTypeImageMap!=null && !roomTypeImageMap.containsKey(roomTypeImageTO.getId())){
					roomTypeImage = new HlRoomTypeImage();
						if(roomTypeImageTO.getImage()!=null){
							roomTypeImage.setImage(roomTypeImageTO.getImage());
						}
					roomTypeImage.setIsActive(true);
					roomTypeImage.setCreatedBy(typeForm.getUserId());
					roomTypeImage.setModifiedBy(typeForm.getUserId());
					roomTypeImage.setCreatedDate(new Date());
					roomTypeImage.setLastModifiedDate(new Date());
					
					roomTypeImageSet.add(roomTypeImage);
				}
				//Works when Previously no images there. Now newly added images in update mode
				else if(roomTypeImageMap==null || roomTypeImageMap.isEmpty()){
					roomTypeImage = new HlRoomTypeImage();
					if(roomTypeImageTO.getImage()!=null){
						roomTypeImage.setImage(roomTypeImageTO.getImage());
					}
					roomTypeImage.setIsActive(true);
					roomTypeImage.setCreatedBy(typeForm.getUserId());
					roomTypeImage.setModifiedBy(typeForm.getUserId());
					roomTypeImage.setCreatedDate(new Date());
					roomTypeImage.setLastModifiedDate(new Date());
					
					roomTypeImageSet.add(roomTypeImage);
				}
				}
			}			
			//Prepares the roomType Facility 		
			if(typeForm.getFacilityList()!=null && !typeForm.getFacilityList().isEmpty()){
				Iterator<FacilityTO> iterator = typeForm.getFacilityList().iterator();
				while (iterator.hasNext()) {
					FacilityTO facilityTO = (FacilityTO) iterator.next();					
					//Works only when previously checked and now checked and may have different quantity
					if(facilityTO.isSelected() && roomTypeFacilityMap!=null &&
						roomTypeFacilityMap.containsKey(facilityTO.getId())){			
						//Gets the old TO
						RoomTypeFacilityTO roomTypeFacilityTO = roomTypeFacilityMap.get(facilityTO.getId());						
						roomTypeFacility = new HlRoomTypeFacility();						
						roomTypeFacility.setId(roomTypeFacilityTO.getId());
						
							if(roomTypeFacilityTO.getRoomTypeTO()!=null){
								HlRoomType hlRoomType = new HlRoomType();
								hlRoomType.setId(Integer.parseInt(roomTypeFacilityTO.getRoomTypeTO().getId()));
								roomTypeFacility.setHlRoomType(hlRoomType);
							}							
							facility = new HlFacility();
							facility.setId(facilityTO.getId());
							roomTypeFacility.setHlFacility(facility);
							if(facilityTO.getQuantity()!=null && !facilityTO.getQuantity().isEmpty()){
								roomTypeFacility.setQuantity(Integer.parseInt(facilityTO.getQuantity()));
							}
							
							roomTypeFacility.setModifiedBy(typeForm.getUserId());
							roomTypeFacility.setLastModifiedDate(new Date());
							roomTypeFacility.setIsActive(true);
							
							roomTypeFacilitySet.add(roomTypeFacility);
					}
					//Works when previously not present and now selected. New records going to be add
					else if(facilityTO.isSelected() && roomTypeFacilityMap!=null &&
							!roomTypeFacilityMap.containsKey(facilityTO.getId())){	
							roomTypeFacility = new HlRoomTypeFacility();
							facility = new HlFacility();
							facility.setId(facilityTO.getId());
							roomTypeFacility.setHlFacility(facility);
							if(facilityTO.getQuantity()!=null && !facilityTO.getQuantity().isEmpty())
							roomTypeFacility.setQuantity(Integer.parseInt(facilityTO.getQuantity()));
							roomTypeFacility.setModifiedBy(typeForm.getUserId());
							roomTypeFacility.setLastModifiedDate(new Date());
							roomTypeFacility.setCreatedBy(typeForm.getUserId());
							roomTypeFacility.setCreatedDate(new Date());
							roomTypeFacility.setIsActive(true);
							
							roomTypeFacilitySet.add(roomTypeFacility);
						}
					//Works when previously present and now not at all selected. Make the records inactive
					else if(!facilityTO.isSelected() && roomTypeFacilityMap!=null &&
							roomTypeFacilityMap.containsKey(facilityTO.getId())){						
							RoomTypeFacilityTO roomTypeFacilityTO = roomTypeFacilityMap.get(facilityTO.getId());						
							roomTypeFacility = new HlRoomTypeFacility();						
							roomTypeFacility.setId(roomTypeFacilityTO.getId());						
							if(roomTypeFacilityTO.getRoomTypeTO()!=null){
								HlRoomType hlRoomType = new HlRoomType();
								hlRoomType.setId(Integer.parseInt(roomTypeFacilityTO.getRoomTypeTO().getId()));
								roomTypeFacility.setHlRoomType(hlRoomType);
							}							
							facility = new HlFacility();
							facility.setId(facilityTO.getId());
							roomTypeFacility.setHlFacility(facility);
							if(facilityTO.getQuantity()!=null && !StringUtils.isEmpty(facilityTO.getQuantity())){
								roomTypeFacility.setQuantity(Integer.parseInt(facilityTO.getQuantity()));
							}
							roomTypeFacility.setModifiedBy(typeForm.getUserId());
							roomTypeFacility.setLastModifiedDate(new Date());
							roomTypeFacility.setIsActive(false);							
							roomTypeFacilitySet.add(roomTypeFacility);
					}
					//Works when previously no facility added. But adding in update mode. (new records)
					else if(roomTypeFacilityMap == null && facilityTO.isSelected()){
						roomTypeFacility = new HlRoomTypeFacility();
						facility = new HlFacility();
						facility.setId(facilityTO.getId());
						roomTypeFacility.setHlFacility(facility);
						if(facilityTO.getQuantity()!=null && !StringUtils.isEmpty(facilityTO.getQuantity())){
						roomTypeFacility.setQuantity(Integer.parseInt(facilityTO.getQuantity()));}
						roomTypeFacility.setModifiedBy(typeForm.getUserId());
						roomTypeFacility.setLastModifiedDate(new Date());
						roomTypeFacility.setCreatedBy(typeForm.getUserId());
						roomTypeFacility.setCreatedDate(new Date());
						roomTypeFacility.setIsActive(true);						
						roomTypeFacilitySet.add(roomTypeFacility);
					}
					
					}			
				}
			//Added by Dilip
			if(inActiveRoomTypeFacilityMap!=null){
				for (Integer key : inActiveRoomTypeFacilityMap.keySet()) {
					IRoomTypeTransaction itransaction = new RoomTypeTransactionImpl();
					HlRoomTypeFacility hlRoomTypeFacility = itransaction.getInActiveRoomTypeFacility(key);
					roomTypeFacilitySet.add(hlRoomTypeFacility);
				 }
			}
			roomType.setHlRoomTypeImages(roomTypeImageSet);
			roomType.setHlRoomTypeFacilities(roomTypeFacilitySet);
			log.info("Leaving into populateTOToBOWhileUpdate of RoomTypeHelper");
			return roomType;
			}

	public List<HlRoomTypeFees> convertFormToBos(RoomTypeForm typeForm) {
		
		List<HlRoomTypeFees> boList=new ArrayList<HlRoomTypeFees>();
		Iterator<FeeGroupTO> itr=typeForm.getFeeList().iterator();
		/*while (itr.hasNext()) {
			FeeGroupTO feeTO = (FeeGroupTO) itr.next();
			if(feeTO.isChecked()){
			HlRoomTypeFees feeBo=new HlRoomTypeFees();
			HlHostel hostel=new HlHostel();
			HlRoomType room=new HlRoomType();
			FeeGroup fee=new FeeGroup();
			hostel.setId(Integer.parseInt(typeForm.getHostelId()));
			room.setId(Integer.parseInt(typeForm.getRoomTypeId()));
			fee.setId(feeTO.getId());
			if (feeTO.getAssignedFeeId() > 0) {
				feeBo.setId(feeTO.getAssignedFeeId());
			}
			feeBo.setHostelId(hostel);
			feeBo.setRoomTypeId(room);
			feeBo.setFeegroupId(fee);
			feeBo.setModifiedBy(typeForm.getUserId());
			feeBo.setLastModifiedDate(new Date());
			feeBo.setCreatedBy(typeForm.getUserId());
			feeBo.setCreatedDate(new Date());
			feeBo.setIsActive(true);	
			boList.add(feeBo);
			typeForm.setFeeSelected(true);
			} else if(!feeTO.isChecked() && feeTO.isTempChecked()){
				
				HlRoomTypeFees feeBo=new HlRoomTypeFees();
				HlHostel hostel=new HlHostel();
				HlRoomType room=new HlRoomType();
				FeeGroup fee=new FeeGroup();
				hostel.setId(Integer.parseInt(typeForm.getHostelId()));
				room.setId(Integer.parseInt(typeForm.getRoomTypeId()));
				if (feeTO.getAssignedFeeId() > 0) {
					feeBo.setId(feeTO.getAssignedFeeId());
				}
				fee.setId(feeTO.getId());
				feeBo.setHostelId(hostel);
				feeBo.setRoomTypeId(room);
				feeBo.setFeegroupId(fee);
				feeBo.setModifiedBy(typeForm.getUserId());
				feeBo.setLastModifiedDate(new Date());
				feeBo.setCreatedBy(typeForm.getUserId());
				feeBo.setCreatedDate(new Date());
				feeBo.setIsActive(false);	
				boList.add(feeBo);
		 }
		}*/
		return boList;
	}

	public List<RoomTypeFeesTO> convertFeeBosToTos(List<HlRoomTypeFees> roomFeesBo) {
		List<RoomTypeFeesTO> roomTypeList=new ArrayList<RoomTypeFeesTO>();
		Iterator<HlRoomTypeFees> listBo=roomFeesBo.iterator();
		while (listBo.hasNext()) {
			HlRoomTypeFees roomBo = (HlRoomTypeFees) listBo.next();
			RoomTypeFeesTO roomTO=new RoomTypeFeesTO();
			roomTO.setId(Integer.toString(roomBo.getId()));
			roomTO.setRoomTypeName(roomBo.getRoomTypeId().getName());
			roomTO.setRoomTypeId(Integer.toString(roomBo.getRoomTypeId().getId()));
			roomTO.setHostelTypeId(Integer.toString(roomBo.getHostelId().getId()));
			roomTO.setHostelTypeName(roomBo.getHostelId().getName());
			roomTO.setGroupName(roomBo.getFeegroupId().getName());
			roomTypeList.add(roomTO);
		}
		return roomTypeList;
	}
	
	public List<FeeGroupTO> copyFeeBosToTos(List<FeeGroup> feeAssignments) throws Exception{
		log.debug("Helper : Entering copyFeeAdditionalBosToTos ");
		List<FeeGroupTO> feeAssignmentToList = new ArrayList<FeeGroupTO>();
		Iterator<FeeGroup> itr = feeAssignments.iterator();
		FeeGroup feegp;
		FeeGroupTO feeGrpTo;
		while(itr.hasNext()) {
			feegp = itr.next();
			feeGrpTo = new FeeGroupTO();
			feeGrpTo.setId(feegp.getId());
			feeGrpTo.setName(feegp.getName());
			if(feegp.getIsOptional())
			feeGrpTo.setOptional(String.valueOf(feegp.getIsOptional()));
			//feeGrpTo.setHostelFees(String.valueOf(feegp.getHostelFees()));
			feeAssignmentToList.add(feeGrpTo);
		}
		log.debug("Helper : Leaving copyFeeAdditionalBosToTos ");
	return feeAssignmentToList;
	}
	
	 public  List<FeeGroupTO> setAssignFee(List<FeeGroup> feeList, Map<Integer,Integer> feeMap) {
		   List<FeeGroupTO> list =new ArrayList<FeeGroupTO>();
		   if(feeList!=null && !feeList.toString().isEmpty()){
			   Iterator<FeeGroup> iterator = feeList.iterator();
			   while (iterator.hasNext()) {
				FeeGroup feeDb = (FeeGroup) iterator.next();
				FeeGroupTO feeTO = new FeeGroupTO();
				if(feeMap.containsKey(feeDb.getId())){
					
					feeTO.setId(feeDb.getId());
					feeTO.setName(feeDb.getName());
					//feeTO.setTempChecked(true);
					//feeTO.setAssignedFeeId(feeMap.get(feeDb.getId()));
	    			list.add(feeTO);
				}else{
					feeTO.setId(feeDb.getId());
					feeTO.setName(feeDb.getName());
	    			list.add(feeTO);
				}
			}
		   }
			return list;
		}
}
