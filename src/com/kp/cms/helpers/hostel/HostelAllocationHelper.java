package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelAllocationForm;
import com.kp.cms.to.hostel.HlRoomFloorTO;
import com.kp.cms.to.hostel.HostelAllocationTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelAllocationTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelAllocationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelAllocationHelper {
	
	private static Log log = LogFactory.getLog(HostelAllocationHelper.class);
	private static volatile HostelAllocationHelper hostelAllocationHelper = null;
	IHostelAllocationTransactions transaction = HostelAllocationTransactionImpl.getInstance();
	
	private HostelAllocationHelper() {
	}
	
	public static HostelAllocationHelper getInstance() {
		if (hostelAllocationHelper == null) {
			hostelAllocationHelper = new HostelAllocationHelper();
		}
		return hostelAllocationHelper;
	}
	
	/**
	 * 
	 * @param hostelAllocationForm
	 * @return
	 */
	private String commonSearch(HostelAllocationForm hostelAllocationForm) {
		
		log.info("entered commonSearch of HostelAllocationHelper ");
		String searchCriteria = "select h.hlApplicationForm.id,"
								+"h.hlApplicationForm.admAppln.id,"
								+"h.hlApplicationForm.employee.id,"
								+"h.hlApplicationForm.admAppln.personalData.firstName,"
								+"h.hlApplicationForm.admAppln.personalData.middleName,"
								+"h.hlApplicationForm.admAppln.personalData.lastName,"
								+"h.hlApplicationForm.hlHostelByHlApprovedHostelId.id,"
								+"h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.id,"
								+"h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name,"
								+"h.hlApplicationForm.isVeg,"
								+"h.hlApplicationForm.hlStatus.statusType,"
								+"h.hlRoom.floorNo,"
								+"h.hlRoom.id,"
								+"h.txnDate"
								+" from HlRoomTransaction h"
								+" join h.admAppln.students student"
								+" where h.hlStatus.id=8"
								+" and h.hlApplicationForm.hlStatus.id=8"
								+" and h.hlApplicationForm.hlHostelByHlApprovedHostelId.isActive=1"
								+" and h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.isActive=1"
								+" and h.hlApplicationForm.hlStatus.isActive=1" 
								+" and h.hlApplicationForm.id="+hostelAllocationForm.getAppNo();
		
		String searchForStaffId ="select h.hlApplicationForm.id,"
								+"h.hlApplicationForm.admAppln.id,"
								+"h.hlApplicationForm.employee.id,"
								+"h.hlApplicationForm.employee.firstName,"
								+"h.hlApplicationForm.employee.middleName,"
								+"h.hlApplicationForm.employee.lastName,"
								+"h.hlApplicationForm.hlHostelByHlApprovedHostelId.id,"
								+"h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.id,"
								+"h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name,"
								+"h.hlApplicationForm.isVeg,"
								+"h.hlApplicationForm.hlStatus.statusType,"
								+"h.hlRoom.floorNo,"
								+"h.hlRoom.id,"
								+"h.txnDate"
								+" from HlRoomTransaction h"
								+" where h.hlStatus.id=8"
								+" and h.hlApplicationForm.hlStatus.id=8"
								+" and h.hlApplicationForm.hlHostelByHlApprovedHostelId.isActive=1"
								+" and h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.isActive=1"
								+" and h.hlApplicationForm.hlStatus.isActive=1"
								+" and h.hlApplicationForm.id="+hostelAllocationForm.getAppNo();
		log.info("exiting commonSearch of HostelAllocationHelper");
		if(hostelAllocationForm.getIsStaff().equalsIgnoreCase("true"))
			return searchForStaffId;
		else
			return searchCriteria;
	}
	
	/**
	 * 
	 * @param hostelAllocationForm
	 * @return
	 */
	public String getSearchCriteria(
			HostelAllocationForm hostelAllocationForm) {
		
		log.info("Entering getSelectionSearchCriteria of HostelAllocationHelper");
		String statusCriteria = commonSearch(hostelAllocationForm);
		
		String searchCriteria= "";
		
		searchCriteria = statusCriteria +
				" and h.hlApplicationForm.isActive = 1 ";
 		
		log.info("Exiting getSelectionSearchCriteria of HostelAllocationHelper");
		return searchCriteria;
	}
	
	public HostelAllocationTO convertBOtoTO(List<Object> applicantHostelDetails) throws Exception{
		 log.info("inside convertBOtoTO of HostelAllocationHelper");
		HostelAllocationTO applicantHostelDetailsTO = null;
		Iterator<Object> applicantIt = applicantHostelDetails.iterator();
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			applicantHostelDetailsTO = new HostelAllocationTO();
			//0-application id
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				applicantHostelDetailsTO.setId(object[0].toString());
			}
			//1-admnApplid
			if(object[1]!= null && !object[1].toString().isEmpty()){
				applicantHostelDetailsTO.setApplicationId(Integer.valueOf(object[1].toString()));
			}
			//2-employee id
			if(object[2]!= null && !object[2].toString().isEmpty()){
				applicantHostelDetailsTO.setStaffId(Integer.valueOf(object[2].toString()));
			}
			//3,4,5 - student/staff name
			if(object[3]!=null && object[4]!=null && object[5]!=null && !object[3].toString().isEmpty()&& !object[4].toString().isEmpty() && !object[5].toString().isEmpty()){
				applicantHostelDetailsTO.setApplicantName(object[3].toString()+" "+ object[4].toString()+" "+ object[5].toString());
			}else if(object[3]!=null && object[4]!=null  && !object[3].toString().isEmpty()&& !object[4].toString().isEmpty()){
				applicantHostelDetailsTO.setApplicantName(object[3].toString()+" "+ object[4].toString());
			}else if(object[3]!=null && !object[3].toString().isEmpty()){
				applicantHostelDetailsTO.setApplicantName(object[3].toString());
			}
			//6-approved hostel id
			if(object[6]!=null && !object[6].toString().isEmpty()){
				applicantHostelDetailsTO.setHostelId(object[6].toString());
			}
			if(object[6]!=null && !object[6].toString().isEmpty()){
				List<HlRoomFloorTO> floorsList = transaction.getFloorsByHostel(Integer.valueOf(object[6].toString()));
				if(floorsList!=null && !floorsList.isEmpty() ){
				applicantHostelDetailsTO.setFloorNoList(floorsList);
				}
				}
			//7-approved room type .
			if(object[7]!=null && !object[7].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomTypeId(object[7].toString());
			}
			//8-approved room type name
			if(object[8]!=null && !object[8].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomType(object[8].toString());
			}
			//9 - is veg
			if(object[9]!=null && !object[9].toString().isEmpty()){
				if(object[9].toString().equalsIgnoreCase("true")){
				applicantHostelDetailsTO.setIsVeg("Vegetarian");
				}
				else if(object[9].toString().equalsIgnoreCase("false")){
					applicantHostelDetailsTO.setIsVeg("Non Veg");
				}
					
			}//10-status type
			if(object[10]!=null && !object[10].toString().isEmpty() ){
				applicantHostelDetailsTO.setStatusType(object[10].toString());
			}
			//floor no
			if(object[11]!=null && !object[11].toString().isEmpty() ){
				applicantHostelDetailsTO.setFloorNo(object[11].toString());
			}
			//room no
			if(object[12]!=null && !object[12].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomNo(object[12].toString());
			}
			
		}
		 log.info("Exiting convertBOtoTO of HostelAllocationHelper");

		return applicantHostelDetailsTO;
	}
	
	
	 public void setAllDataToForm (HostelAllocationForm hostelAllocationform,HostelAllocationTO hostelApplicantDetails) throws Exception
	 {
		 log.info("inside setAllDataToForm of HostelAllocationHelper");
		 Date reservationDate=null;
		if(hostelApplicantDetails != null){
			 if(hostelApplicantDetails.getApplicantName()!= null && !hostelApplicantDetails.getApplicantName().isEmpty()){
			 hostelAllocationform.setApplicantName(hostelApplicantDetails.getApplicantName());
			}
			if(hostelApplicantDetails.getRoomTypeId()!= null && !hostelApplicantDetails.getRoomTypeId().isEmpty()){
				 hostelAllocationform.setRoomId(hostelApplicantDetails.getRoomTypeId());
				}
			if(hostelApplicantDetails.getRoomType()!= null && !hostelApplicantDetails.getRoomType().isEmpty()){
			 hostelAllocationform.setRoomType(hostelApplicantDetails.getRoomType());
			}
			if(hostelApplicantDetails.getIsVeg()!= null && !hostelApplicantDetails.getIsVeg().isEmpty()){
			 hostelAllocationform.setIsVeg(hostelApplicantDetails.getIsVeg());
			}
			if(hostelApplicantDetails.getStatusType().equalsIgnoreCase("reserved")){
			//get the reservation date from hl_transaction
				reservationDate = transaction.getReservationDate(Integer.valueOf(hostelApplicantDetails.getId()));
			}
			
			if(reservationDate!= null){
				hostelAllocationform.setReservationDate(CommonUtil.getStringDate(reservationDate));
			}
			if(hostelApplicantDetails.getFloorNoList()!=null && !hostelApplicantDetails.getFloorNoList().isEmpty()){
			 hostelAllocationform.setFloorNoList(hostelApplicantDetails.getFloorNoList());
			}
			if(hostelApplicantDetails.getHostelId()!=null && !hostelApplicantDetails.getHostelId().isEmpty()){
			 hostelAllocationform.setHostelId(hostelApplicantDetails.getHostelId());
			}
			if(hostelApplicantDetails.getFloorNo()!=null && !hostelApplicantDetails.getFloorNo().isEmpty())
			{
				hostelAllocationform.setFloorNo(hostelApplicantDetails.getFloorNo());
			}
			if(hostelApplicantDetails.getRoomNo()!=null && !hostelApplicantDetails.getRoomNo().isEmpty())
			{
				hostelAllocationform.setRoomNo(hostelApplicantDetails.getRoomNo());
			}
		}
	 log.info("Exiting setAllDataToForm of HostelAllocationHelper");
	 }
	
	public String saveAllocationDetails(HostelAllocationForm hostelAllocationform,HostelAllocationTO hostelApplicantDetails) throws Exception 
	{
		 log.info("Entering saveAllocationDetails of HostelAllocationHelper");
		 int currentOccupantsNumber=0;
		 int curentReservationCount=0;
		 String txnStatus = "failed";
		 String status="failed";
		 Set<HlRoomTransaction> hlRoomTransactionSet;
		 if(hostelApplicantDetails.getStatusType().equalsIgnoreCase("Allotted") || hostelApplicantDetails.getStatusType().equalsIgnoreCase("CheckedIn"))
		 {
			 status = CMSConstants.HOSTEL_ALLOCATION_ALLREADY_ALLOTTED;
			 return status;
		 }
	
		 //the student/staff is not yet been allotted or checked in
		 else
		 {
		 
			 //need to check the requested bed is allready allocated or not
			 if(hostelAllocationform.getRoomNo()!=null && !hostelAllocationform.getRoomNo().isEmpty())
			 {
				 List<Object> allottedBedList = transaction.getAllocatedBedList(Integer.valueOf(hostelAllocationform.getRoomNo()));
				 if(allottedBedList!=null && !allottedBedList.isEmpty() )
				 {
					 Integer item;
					 Iterator<Object> bedIt = allottedBedList.iterator();
					 while(bedIt.hasNext())
					 {
						 item = (Integer)bedIt.next();
						 if(item!=null)
						 {
							 if(hostelAllocationform.getBedNo().equals(item.toString()))
							 {
								 status = CMSConstants.HOSTEL_ALLOCATION_BED_ALLREADY_ALLOTTED;
								 return status;
							 }
						 }
					 }
				 }
			 }
			 HlStatus  hlStatus = new HlStatus();
			 hlStatus.setId(7);
			 HlApplicationForm hlApplicationForm = new HlApplicationForm();
			 if(hostelApplicantDetails.getId()!=null && !hostelApplicantDetails.getId().isEmpty())
			 {
				 hlApplicationForm = transaction.getApplicationDetailsToUpdate(Integer.valueOf(hostelApplicantDetails.getId()));
			 }
			 if(hlApplicationForm!=null)
			 {
				 hlApplicationForm.setHlStatus(hlStatus);
				 hlApplicationForm.setModifiedBy(hostelAllocationform.getUserId());
				 hlApplicationForm.setLastModifiedDate(new Date());
				 //	prepare a set containing roomTransaction and set it to application form
				 HlRoomTransaction hlRoomTransaction = null;
				 HlRoom hlRoom = new HlRoom();
				 if(hostelAllocationform.getRoomNo()!=null && !hostelAllocationform.getRoomNo().isEmpty()){
					 hlRoom.setId(Integer.parseInt(hostelAllocationform.getRoomNo()));
				 }
				 AdmAppln admAppl = null;
				 Employee employee =null;
				 if(hostelApplicantDetails.getApplicationId()!=0){
					 admAppl = new AdmAppln();
					 admAppl.setId(hostelApplicantDetails.getApplicationId()); 
				 }
				 if(hostelApplicantDetails.getStaffId()!=0)
				 { 
					 employee = new Employee();
					 employee.setId(hostelApplicantDetails.getStaffId()); 
				 }
				 int noOfOccupantsAllowed = transaction.getNumberOfOccupants(Integer.valueOf(hostelAllocationform.getRoomId()));
				 List<Object> currentOccupantList = transaction.getCurrentOccupantsCount(Integer.valueOf(hostelAllocationform.getRoomNo()));
				//if currentOccupantList is null or empty  means he did not reserve the room or he has not allocated a room yet
				// so allocate and only increment no of occupants count
				// if he reserved the room means we will be getting  no of  occupants and  reservation count 
				//so allocate the room and update no of occupants is + 1 and reservtaion count =count -1
				 if(currentOccupantList == null ||currentOccupantList.size()== 0 ||currentOccupantList.isEmpty())
				 {
					 //	for this room nobody reserved or nobody allocated
					 if(noOfOccupantsAllowed!=0){
						 hlRoomTransaction = new HlRoomTransaction();
						 hlRoomTransaction.setAdmAppln(admAppl);
						 hlRoomTransaction.setEmployee(employee);
						 hlRoomTransaction.setHlRoom(hlRoom);
						 hlRoomTransaction.setHlStatus(hlStatus);
						 if(hostelAllocationform.getAllocationDate()!=null && !hostelAllocationform.getAllocationDate().isEmpty()){
							 Calendar cal=Calendar.getInstance();
							 String finalTime = hostelAllocationform.getAllocationDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
							 hlRoomTransaction.setTxnDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
						 }
						 hlRoomTransaction.setBedNo(Integer.valueOf(hostelAllocationform.getBedNo()));
						 hlRoomTransaction.setCurrentOccupantsCount(currentOccupantsNumber + 1);
						 //	set the reservation count = 0
						 hlRoomTransaction.setCurrentReservationCount(0);
						 hlRoomTransaction.setCreatedBy(hostelAllocationform.getUserId());
						 hlRoomTransaction.setCreatedDate(new Date());
						 hlRoomTransaction.setModifiedBy(hostelAllocationform.getUserId());
						 hlRoomTransaction.setLastModifiedDate(new Date());
						 hlRoomTransaction.setIsActive(true);
						 hlRoomTransaction.setHlApplicationForm(hlApplicationForm);
						 hlRoomTransactionSet = new HashSet<HlRoomTransaction>();
						 hlRoomTransactionSet.add(hlRoomTransaction);
						 hlApplicationForm.setHlRoomTransactions(hlRoomTransactionSet);
						 txnStatus = transaction.updateApplicationFormWithTransactionSet(hlApplicationForm);
					 }
					 if(txnStatus.equalsIgnoreCase("success")){
						 status=CMSConstants.HOSTEL_ALLOCATION_SUCCESS;
						 return status;
					 }
					 else
					 {
						 status = "failed";
					 }
				 }
				 //	reserved the room or room is partially or fully occupied by others
				 else
				 {
					//we have one transaction record with that room no- this need to update with noOfocupants+1
					 Iterator<Object> listIt =  currentOccupantList.iterator();
					 while(listIt.hasNext())
					 {
						 Object[] object =(Object[]) listIt.next();	
						 if(object[0]!= null && !object[0].toString().isEmpty()){
							 curentReservationCount = Integer.valueOf(object[0].toString());
						 }
						 if(object[1]!= null && !object[1].toString().isEmpty()){
							 currentOccupantsNumber = Integer.valueOf(object[1].toString());
						 }
					 }
					 if(noOfOccupantsAllowed!= 0 && !Integer.valueOf(noOfOccupantsAllowed).toString().isEmpty() && Integer.valueOf(noOfOccupantsAllowed).toString()!= null )
					 {
						 if(hostelApplicantDetails.getStatusType().equalsIgnoreCase("Reserved"))
						 {
							 if(currentOccupantsNumber + curentReservationCount >noOfOccupantsAllowed || curentReservationCount == 0){
								 status = CMSConstants.HOSTEL_ALLOCATION_RESERVERD_ROOM;
								 return status;
							 }
							 else if(currentOccupantsNumber==noOfOccupantsAllowed){
								 status = CMSConstants.HOSTEL_ALLOCATION_RESERVERD_ROOM_FULL;
								 return status;
							 }
							 else{
								 hlRoomTransaction = new HlRoomTransaction();
								 hlRoomTransaction.setAdmAppln(admAppl);
								 hlRoomTransaction.setEmployee(employee);
								 hlRoomTransaction.setHlRoom(hlRoom);
								 hlRoomTransaction.setHlStatus(hlStatus);
								 if(hostelAllocationform.getAllocationDate()!=null && !hostelAllocationform.getAllocationDate().isEmpty()){
									 Calendar cal=Calendar.getInstance();
									 String finalTime = hostelAllocationform.getAllocationDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
									 hlRoomTransaction.setTxnDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
								 }
								 hlRoomTransaction.setBedNo(Integer.valueOf(hostelAllocationform.getBedNo()));
								 hlRoomTransaction.setCurrentOccupantsCount(currentOccupantsNumber + 1);
								 //and reduce the reservation count by 1
								 hlRoomTransaction.setCurrentReservationCount(curentReservationCount-1);
								 hlRoomTransaction.setCreatedBy(hostelAllocationform.getUserId());
								 hlRoomTransaction.setCreatedDate(new Date());
								 hlRoomTransaction.setModifiedBy(hostelAllocationform.getUserId());
								 hlRoomTransaction.setLastModifiedDate(new Date());
								 hlRoomTransaction.setIsActive(true);
								 hlRoomTransaction.setHlApplicationForm(hlApplicationForm);
								 hlRoomTransactionSet = new HashSet<HlRoomTransaction>();
								 hlRoomTransactionSet.add(hlRoomTransaction);
								 hlApplicationForm.setHlRoomTransactions(hlRoomTransactionSet);
								 txnStatus = transaction.updateApplicationFormWithTransactionSet(hlApplicationForm);
							}
							if(txnStatus.equalsIgnoreCase("success")){
								status=CMSConstants.HOSTEL_ALLOCATION_SUCCESS;
								return status;
							}
						 }
						 // the person did not reserve the room but room is partially occupied
						 else
						 {
							 if(currentOccupantsNumber >= noOfOccupantsAllowed){
								 status =CMSConstants.HOSTEL_ALLOCATION_RESERVERD_ROOM_FULL;
						 }
						 else
						 {
							 hlRoomTransaction = new HlRoomTransaction();
							 hlRoomTransaction.setAdmAppln(admAppl);
							 hlRoomTransaction.setEmployee(employee);
							 hlRoomTransaction.setHlRoom(hlRoom);
							 hlRoomTransaction.setHlStatus(hlStatus);
							 if(hostelAllocationform.getAllocationDate()!=null && !hostelAllocationform.getAllocationDate().isEmpty()){
								 Calendar cal=Calendar.getInstance();
								 String finalTime = hostelAllocationform.getAllocationDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
								 hlRoomTransaction.setTxnDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
							 }
							 hlRoomTransaction.setBedNo(Integer.valueOf(hostelAllocationform.getBedNo()));
							 hlRoomTransaction.setCurrentOccupantsCount(currentOccupantsNumber + 1);
							 hlRoomTransaction.setCurrentReservationCount(curentReservationCount);
							 hlRoomTransaction.setCreatedBy(hostelAllocationform.getUserId());
							 hlRoomTransaction.setCreatedDate(new Date());
							 hlRoomTransaction.setModifiedBy(hostelAllocationform.getUserId());
							 hlRoomTransaction.setLastModifiedDate(new Date());
							 hlRoomTransaction.setIsActive(true);
							 hlRoomTransaction.setHlApplicationForm(hlApplicationForm);
							 hlRoomTransactionSet = new HashSet<HlRoomTransaction>();
							 hlRoomTransactionSet.add(hlRoomTransaction);
							 hlApplicationForm.setHlRoomTransactions(hlRoomTransactionSet);
							 txnStatus = transaction.updateApplicationFormWithTransactionSet(hlApplicationForm); 
						 }
						 if(txnStatus.equalsIgnoreCase("success")){
								status=CMSConstants.HOSTEL_ALLOCATION_SUCCESS;
								return status;
						} 
						 }
		}
						 
				 }
		}
		else
		{
			//applicat details cannot be updated because it is null
			status = "failed";
		}
		}
		
		return status;
	}
	
	public List<HostelTO> copyHostelBosToTos(List<HlHostel> hostelList) {
		 log.info("Entering copyHostelBosToTos of HostelAllocationHelper");
		List<HostelTO> hostelTOList = new ArrayList<HostelTO>();
		Iterator<HlHostel> iterator = hostelList.iterator();
		HlHostel hlHostel;
		HostelTO hostelTO;
		
		while (iterator.hasNext()) {
			hostelTO = new HostelTO();		
			hlHostel = (HlHostel) iterator.next();
			if(hlHostel.getName()!=null && !hlHostel.getName().isEmpty()){
			hostelTO.setName(hlHostel.getName());
			}
			hostelTO.setId(hlHostel.getId());
			hostelTOList.add(hostelTO);
		}
		 log.info("Exiting copyHostelBosToTos of HostelAllocationHelper");
		return hostelTOList;
	}

	public List<HostelAllocationTO> convertBOtoTOList(List<Object> applicantHostelDetails)throws Exception
	{
		// TODO Auto-generated method stub
		List<HostelAllocationTO> appliedHostelList=new ArrayList<HostelAllocationTO>();
		HostelAllocationTO applicantHostelDetailsTO = null;
		Iterator<Object> applicantIt = applicantHostelDetails.iterator();
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			applicantHostelDetailsTO = new HostelAllocationTO();
			//0-application id
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				applicantHostelDetailsTO.setId(object[0].toString());
			}
			//1-admnApplid
			if(object[1]!= null && !object[1].toString().isEmpty()){
				applicantHostelDetailsTO.setApplicationId(Integer.valueOf(object[1].toString()));
			}
			//2-approved hostel name
			if(object[2]!=null && !object[2].toString().isEmpty()){
				applicantHostelDetailsTO.setHostelName(object[2].toString());
			}
			//3-approved room type name
			if(object[3]!=null && !object[3].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomType(object[3].toString());
			}
			//4- floor no
			if(object[4]!=null && !object[4].toString().isEmpty() ){
				applicantHostelDetailsTO.setFloorNo(object[4].toString());
			}
			//5 - room no
			if(object[5]!=null && !object[5].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomNo(object[5].toString());
			}
			//6 - is staff
			if(object[6]!=null && !object[6].toString().isEmpty())
			{
				applicantHostelDetailsTO.setIsStaff(object[6].toString());
			}
			appliedHostelList.add(applicantHostelDetailsTO);
			
		}
		 log.info("Exiting convertBOtoTO of HostelAllocationHelper");

		return appliedHostelList;
	}

	public String getSearchDetails(HostelAllocationForm hostelAllocationForm) {
		// TODO Auto-generated method stub
		log.info("Entering getSelectionSearchCriteria of HostelAllocationHelper");
		String statusCriteria = searchCriteria(hostelAllocationForm);
		
		String searchCriteria= "";
		
		searchCriteria = statusCriteria +
				" and h.hlApplicationForm.isActive = 1 ";
 		
		log.info("Exiting getSelectionSearchCriteria of HostelAllocationHelper");
		return searchCriteria;
	}
	
private String searchCriteria(HostelAllocationForm hostelAllocationForm) {
		
		log.info("entered searchCriteria of HostelAllocationHelper ");
		String searchCriteria = "select h.hlApplicationForm.id,"
								+"h.hlApplicationForm.admAppln.id,"
								+"h.hlApplicationForm.hlHostelByHlApprovedHostelId.name,"
								+"h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name,"
								+"h.hlRoom.floorNo,"
								+"h.hlRoom.name,"
								+"h.hlApplicationForm.isStaff"
								+" from HlRoomTransaction h"
								+" join h.admAppln.students student"
								+" where h.hlStatus.id=8"
								+" and h.hlApplicationForm.hlStatus.id=8"
								+" and h.hlApplicationForm.hlHostelByHlApprovedHostelId.isActive=1"
								+" and h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.isActive=1"
								+" and h.hlApplicationForm.hlStatus.isActive=1" 
								+" and h.hlApplicationForm.admAppln.appliedYear="+hostelAllocationForm.getAcademicYr();
		
		String searchForStaffId ="select h.hlApplicationForm.id,"
								+"h.hlApplicationForm.employee.id,"
								+"h.hlApplicationForm.hlHostelByHlApprovedHostelId.name,"
								+"h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name,"
								+"h.hlRoom.floorNo,"
								+"h.hlRoom.name,"
								+"h.hlApplicationForm.isStaff"
								+" from HlRoomTransaction h"
								+" where h.hlStatus.id=8"
								+" and h.hlApplicationForm.hlStatus.id=8"
								+" and h.hlApplicationForm.hlHostelByHlApprovedHostelId.isActive=1"
								+" and h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.isActive=1"
								+" and h.hlApplicationForm.hlStatus.isActive=1";
		
		if (hostelAllocationForm.getAppNo()!=null && hostelAllocationForm.getAppNo().trim().length() > 0) {
			String applicationNo = " and h.hlApplicationForm.isStaff = false"
									+" and h.hlApplicationForm.admAppln.applnNo = "+
									"'"+ hostelAllocationForm.getAppNo()+"'";
			searchCriteria = searchCriteria + applicationNo;
			
		}else if (hostelAllocationForm.getRegNo()!=null && hostelAllocationForm.getRegNo().trim().length() > 0) {
			String registerNo = " and h.hlApplicationForm.isStaff = false"
								+" and student.isActive=1"
								+" and student.registerNo = "+
								"'"+ hostelAllocationForm.getRegNo()+"'";
			searchCriteria = searchCriteria + registerNo;
			
		}else if (hostelAllocationForm.getRollNo()!=null && hostelAllocationForm.getRollNo().trim().length() > 0) {
			String rollNo = " and h.hlApplicationForm.isStaff = false"
				+" and student.isActive=1"
				+" and student.rollNo = "+
				"'"+ hostelAllocationForm.getRollNo()+"'";
			searchCriteria = searchCriteria + rollNo;

		}else if(hostelAllocationForm.getStaffId()!= null && hostelAllocationForm.getStaffId().trim().length() > 0){
			String staffID = " and h.hlApplicationForm.isStaff = true"
							+" and h.hlApplicationForm.employee.isActive=1"
							+" and hlApplicationForm.employee.code = "+
							"'"+ hostelAllocationForm.getStaffId()+"'";
			searchCriteria = searchForStaffId + staffID;
		}

		if (hostelAllocationForm.getHostelId()!=null && hostelAllocationForm.getHostelId().trim().length() > 0) {
			String appliedHostel = " and h.hlApplicationForm.hlHostelByHlApprovedHostelId.id = "
					+ hostelAllocationForm.getHostelId();
			searchCriteria = searchCriteria + appliedHostel;
		}
		
		log.info("exiting commonSearch of HostelAllocationHelper");
		return searchCriteria;
	}
}
