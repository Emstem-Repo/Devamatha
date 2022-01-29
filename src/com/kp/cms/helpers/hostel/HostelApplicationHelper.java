package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Collections;
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

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.admin.HlRoomTypeImage;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.StudentLoginInfoNotFoundException;
import com.kp.cms.forms.hostel.AssignRoomMasterForm;
import com.kp.cms.forms.hostel.HostelApplicationForm;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.HlApplicationFeeTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeFacilityTO;
import com.kp.cms.to.hostel.RoomTypeImageTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.to.hostel.RoomTypeWithAmountTO;
import com.kp.cms.transactions.hostel.IHostelApplicationTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelApplicationTransactionImpl;

public class HostelApplicationHelper {
private static final Log log = LogFactory.getLog(HostelApplicationHelper.class);
	
	public static volatile HostelApplicationHelper hostelApplicationHelper = null;
	private HostelApplicationHelper(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static HostelApplicationHelper getInstance() {
		if (hostelApplicationHelper == null) {
			hostelApplicationHelper = new HostelApplicationHelper();
		}
		return hostelApplicationHelper;
	}
	/**
	 * Used to convert hostel BO to TO
	 */
	public List<HostelTO> copyHostelBOToTO(List<HlHostel>hostelList)throws Exception{
		log.info("Entering into copyHostelBOToTO of HostelApplicationHelper");
		List<HostelTO> hosteTOlList = new ArrayList<HostelTO>();
		HostelTO hostelTO = null;
		if(hostelList!=null && !hostelList.isEmpty()){
			Iterator<HlHostel> hostelIterator = hostelList.iterator();
			while (hostelIterator.hasNext()) {
				HlHostel hlHostel = hostelIterator.next();
				hostelTO = new HostelTO();
				hostelTO.setId(hlHostel.getId());
					if(hlHostel.getName().trim()!=null){
						hostelTO.setName(hlHostel.getName());
					}
					if(hlHostel.getAddressLine1().trim()!=null){
						hostelTO.setAddressLine1(hlHostel.getAddressLine1()+",");
					}
					if(hlHostel.getAddressLine2().trim()!=null && !hlHostel.getAddressLine2().trim().equals(CMSConstants.RECIEVER_PROVIDER_URL)){
						hostelTO.setAddressLine2(hlHostel.getAddressLine2());
					}
					hosteTOlList.add(hostelTO);
			}
		}
		log.info("Leaving into copyHostelBOToTO of HostelApplicationHelper");
		return hosteTOlList;
	}
	
	/**
	 * Used to convert RoomType BO name to TOs
	 * @param roomForm 
	 */
	public List<RoomTypeTO> copyRoomTypeBOToTOs(List<HlRoomType>roomTypeNameList, AssignRoomMasterForm roomForm)throws Exception{
		log.info("Entering into copyRoomTypeBOToTOs of HostelApplicationHelper");
		List<RoomTypeTO> nameList = new ArrayList<RoomTypeTO>();
		RoomTypeTO roomTypeTO = null;
		HostelTO hostelTO = null;
		Map<Integer, Integer> map =new HashMap<Integer, Integer>();
		if(roomTypeNameList!=null && !roomTypeNameList.isEmpty()){
			Iterator<HlRoomType> iterator = roomTypeNameList.iterator();
			while (iterator.hasNext()) {
				HlRoomType roomType = (HlRoomType)iterator.next();
				roomTypeTO = new RoomTypeTO();
				if(roomType.getId() !=0){
					map.put(roomType.getId(), roomType.getNoOfOccupants());
				}
				roomTypeTO.setId(String.valueOf(roomType.getId()));
					if(roomType.getName()!=null && !roomType.getName().equals(CMSConstants.RECIEVER_PROVIDER_URL)){
					roomTypeTO.setName(roomType.getName());
					}
					hostelTO = new HostelTO();
					if(roomType.getHlHostel()!=null){
						hostelTO.setId(roomType.getHlHostel().getId());
					}
				roomTypeTO.setHostelTO(hostelTO);
				nameList.add(roomTypeTO);
			}
			roomForm.setRoomTypeMap(map);
		}
		log.info("Leaving into copyRoomTypeBOToTOs of HostelApplicationHelper");
		return nameList;
	}
	
	/**
	 * Used to prepare the application information details
	 */
	public HlApplicationForm prepareApplicationInformation(HostelApplicationForm applicationForm, String studentid, String studentLoginId)throws Exception{
		log.info("Entering into prepareApplicationInformation of HostelApplicationHelper");
		HlApplicationForm applicationFormBO = new HlApplicationForm();
		HlHostel hostel = new HlHostel();
		HlRoomType roomType = new HlRoomType();
		AdmAppln admAppln = new AdmAppln();
		HlStatus hlStatus = new HlStatus();
		IHostelApplicationTransaction transaction = new HostelApplicationTransactionImpl();
		//Get the studentId and get the AdmAppln ID based on that
		int admApplnId = 0;
		if(studentid != null){
			admApplnId = transaction.getAdmApplnIDOnStudentID(Integer.valueOf(studentid.trim()));
		}
		if(admApplnId <= 0){
			throw new StudentLoginInfoNotFoundException();
		}
		//Used to get the status Id
		int statusId = CMSConstants.HOSTEL_STATUS_APPLIED_ID;//transaction.getAppliedStatusId();
		int maxRequisitionNo = transaction.getMaxRequisitionNo();
		int newRequisitionNo= maxRequisitionNo + 1;
		
		hostel.setId(Integer.parseInt(applicationForm.getHostelId().trim()));
		roomType.setId(Integer.parseInt(applicationForm.getRoomTypeCheck().trim()));
		admAppln.setId(admApplnId);
		hlStatus.setId(statusId);
		
		applicationFormBO.setHlHostelByHlAppliedHostelId(hostel);
		applicationFormBO.setHlRoomTypeByHlAppliedRoomTypeId(roomType);
		applicationFormBO.setHlHostelByHlApprovedHostelId(hostel);
		applicationFormBO.setHlRoomTypeByHlApprovedRoomTypeId(roomType);
		applicationFormBO.setAdmAppln(admAppln);
		applicationFormBO.setHlStatus(hlStatus);
		
		applicationFormBO.setRequisitionNo(newRequisitionNo);
		applicationForm.setMaxRequisitionNo(String.valueOf(newRequisitionNo));
		
		applicationFormBO.setIsEnteredByAdmin(false);
		applicationFormBO.setIsStaff(false);
		applicationFormBO.setAppliedDate(new Date());
		if(applicationForm.getClinicalRemarks().trim()!=null && !StringUtils.isEmpty(applicationForm.getClinicalRemarks().trim())){
			applicationFormBO.setClinicalRemarks(applicationForm.getClinicalRemarks());
		}
		if(applicationForm.getSicknessRelatedInfo().trim()!=null && !StringUtils.isEmpty(applicationForm.getSicknessRelatedInfo().trim())){
			applicationFormBO.setSicknessRelatedInfo(applicationForm.getSicknessRelatedInfo());
		}
		applicationFormBO.setCreatedBy(studentLoginId);
		applicationFormBO.setCreatedDate(new Date());
		applicationFormBO.setModifiedBy(studentLoginId);
		applicationFormBO.setLastModifiedDate(new Date());
		applicationFormBO.setIsActive(true);
		log.info("Leaving into prepareApplicationInformation of HostelApplicationHelper");
		return applicationFormBO;
	}
	
	/**
	 * Used for preparing the view for terms and condition
	 */
	public HostelTO prepareTermsConditionView(HlHostel hostel, HostelApplicationForm applicationForm,
	HttpServletRequest request)throws Exception{
		log.info("Entering into prepareTermsConditionView of HostelApplicationHelper");
		HostelTO hostelTO = null;
		if(hostel!=null){
			hostelTO = new HostelTO();
			if(hostel.getTermsConditions()!=null){
				hostelTO.setTermsConditions(hostel.getTermsConditions());
			}
			if(hostel.getFileName()!=null){
				hostelTO.setFileName(hostel.getFileName());
			}
			if(hostel.getContentType()!=null){
				hostelTO.setContentType(hostel.getContentType());
			}
		}		
		log.info("Leaving into prepareTermsConditionView of HostelApplicationHelper");
		return hostelTO;
	}
	
	/**
	 * Used to prepare properties to view the hostel details
	 */
	public void preparePropertiesToView(HlHostel hostelBO, HostelApplicationForm applicationForm, HttpServletRequest request)throws Exception{
		
		HttpSession session = request.getSession(false);
		//Prepare the hostel name and address and keep in form for view
		if(hostelBO!=null){
			HostelTO hostelTO = new HostelTO();
			if(hostelBO.getName()!=null){
				hostelTO.setName(hostelBO.getName());
			}
			if(hostelBO.getAddressLine1()!=null){
				hostelTO.setAddressLine1(hostelBO.getAddressLine1()+",");
			}
			if(hostelBO.getAddressLine2()!=null){
				hostelTO.setAddressLine2(hostelBO.getAddressLine2());
			}
			applicationForm.setHostelTO(hostelTO);
		}
		
		//Prepare the roomtypes along with facilities and images		
		if(hostelBO!=null && hostelBO.getHlRoomTypes()!=null && !hostelBO.getHlRoomTypes().isEmpty()){
			List<RoomTypeTO> roomTypeList = new ArrayList<RoomTypeTO>();
			RoomTypeTO roomTypeTO = null;
			FacilityTO facilityTO = null;
			RoomTypeImageTO roomTypeImageTO = null;
			RoomTypeFacilityTO roomTypeFacilityTO = null;
			Iterator<HlRoomType> it = hostelBO.getHlRoomTypes().iterator();
			while (it.hasNext()) {
				HlRoomType hlRoomType = it.next();
				if(hlRoomType.getIsActive()){
					roomTypeTO = new RoomTypeTO();
					if(hlRoomType.getName()!= null){
						roomTypeTO.setName(hlRoomType.getName());
					}
					//Prepare the facilities
						if(hlRoomType.getHlRoomTypeFacilities()!=null && !hlRoomType.getHlRoomTypeFacilities().isEmpty()){
							List<RoomTypeFacilityTO> facilityList = new ArrayList<RoomTypeFacilityTO>();
							Iterator<HlRoomTypeFacility> itr = hlRoomType.getHlRoomTypeFacilities().iterator();
							String temp="";
							while (itr.hasNext()) {
								HlRoomTypeFacility hlRoomTypeFacility = itr.next();
								roomTypeFacilityTO = new RoomTypeFacilityTO();
								if(hlRoomTypeFacility.getIsActive()&&
								hlRoomTypeFacility.getHlFacility()!=null && 
								hlRoomTypeFacility.getHlFacility().getIsActive()){ 
									facilityTO = new FacilityTO();
									String facilityName = "";																		
									if(hlRoomTypeFacility.getHlFacility().getName()!= null && temp.equals("")){
										facilityName= hlRoomTypeFacility.getHlFacility().getName();
									}
									else if(hlRoomTypeFacility.getHlFacility().getName()!= null && !temp.equals("")){
										facilityName= ", "+hlRoomTypeFacility.getHlFacility().getName();
									}
									facilityTO.setName(facilityName);
									roomTypeFacilityTO.setFacilityTO(facilityTO);
									facilityList.add(roomTypeFacilityTO);
									temp = facilityName;
								}
							}
							roomTypeTO.setFacilityList(facilityList);
						}
					//Prepare the images
					if(hlRoomType.getHlRoomTypeImages()!=null && !hlRoomType.getHlRoomTypeImages().isEmpty()){
						List<RoomTypeImageTO> imageList = new ArrayList<RoomTypeImageTO>();
						Iterator<HlRoomTypeImage> iterator = hlRoomType.getHlRoomTypeImages().iterator();
						while (iterator.hasNext()) {							
								HlRoomTypeImage hlRoomTypeImage = iterator.next();
								if(hlRoomTypeImage.getIsActive()){
									roomTypeImageTO = new RoomTypeImageTO();
									roomTypeImageTO.setId(hlRoomTypeImage.getId());
									
									roomTypeImageTO.setRoomTypeId(hlRoomType.getId());
									if(hlRoomTypeImage.getImage()!=null){
										roomTypeImageTO.setImage(hlRoomTypeImage.getImage());
									}
									roomTypeImageTO.setCountId(imageList.size()+1);
									imageList.add(roomTypeImageTO);
							}
						}						
						roomTypeTO.setImageList(imageList);						
						session.setAttribute("imageList_" + hlRoomType.getId(), imageList);
					}
					roomTypeList.add(roomTypeTO);				
			}
			}	
			applicationForm.setRoomTypeNameList(roomTypeList);
		}	
	}
	/**
	 * Used to convert RoomType BO name to TOs
	 */
	public List<RoomTypeWithAmountTO> copyFeesBOsToTO(List<HlFees> feesList)throws Exception{
		log.info("Entering into copyFeesBOsToTO of HostelApplicationHelper");
		Iterator<HlFees> feeIterator = feesList.iterator();
		List<HlApplicationFeeTO> feeToList = new ArrayList<HlApplicationFeeTO>();
		HlApplicationFeeTO applicationFeeTO;
		Map<String, List<HlApplicationFeeTO>> feeMap = new HashMap<String, List<HlApplicationFeeTO>>();
		Set<Integer> feeTypeCodemap = new HashSet<Integer>();
		while(feeIterator.hasNext()){
			applicationFeeTO = new HlApplicationFeeTO();
			HlFees hlFees = feeIterator.next();
			String roomTypeName = hlFees.getHlRoomType().getName();
			if(roomTypeName!= null){
				if(feeMap.containsKey(roomTypeName)){
					feeToList = feeMap.get(roomTypeName); 
				}
				else
				{
					feeToList = new ArrayList<HlApplicationFeeTO>();
				}
			}
			if(hlFees.getFeeAmount()!=null)
				applicationFeeTO.setAmount(hlFees.getFeeAmount().toString());
			else
				applicationFeeTO.setAmount("");
			applicationFeeTO.setFeeTypeId(hlFees.getHlFeeType().getId());
			applicationFeeTO.setFeeTypeName(hlFees.getHlFeeType().getName());
			feeToList.add(applicationFeeTO);
			feeMap.put(roomTypeName, feeToList);
			if(hlFees.getHlFeeType()!= null){
				if(!feeTypeCodemap.contains(hlFees.getHlFeeType().getId())){
					feeTypeCodemap.add(hlFees.getHlFeeType().getId());
				}
			}
		}
		
		Iterator<String> keyIterator = feeMap.keySet().iterator();
		Set<Integer> tempMap = new HashSet<Integer>();
		HlApplicationFeeTO tempTo;  
		List<RoomTypeWithAmountTO> roomTypeWithAmountList = new ArrayList<RoomTypeWithAmountTO>();
		RoomTypeWithAmountTO roomAmountTO;
		while (keyIterator.hasNext()) {
			String string = (String) keyIterator.next();
			feeToList = feeMap.get(string);
			Iterator<HlApplicationFeeTO> tempItr = feeToList.iterator();
			while(tempItr.hasNext()){
				tempTo = tempItr.next();
				tempMap.add(tempTo.getFeeTypeId());
			}
			HlApplicationFeeTO newTo; 
			Iterator<Integer> origItr = feeTypeCodemap.iterator();
			while(origItr.hasNext()){
				int id = origItr.next();
				if(!tempMap.contains(id)){
					newTo = new HlApplicationFeeTO();
					newTo.setFeeTypeId(id);
					newTo.setAmount("");
					feeToList.add(newTo);
				}
			}
			roomAmountTO = new RoomTypeWithAmountTO();
			roomAmountTO.setRoomType(string);
			Collections.sort(feeToList);
			roomAmountTO.setAmountList(feeToList);
			roomTypeWithAmountList.add(roomAmountTO);
		}		
		
		log.info("Leaving into copyFeesBOsToTO of HostelApplicationHelper");
		return roomTypeWithAmountList;
	}	
}
