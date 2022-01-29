package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.forms.hostel.HostelCheckinForm;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.hostel.HlRoomTypeFacilityTo;
import com.kp.cms.to.hostel.HostelCheckinTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelCheckinTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelCheckinTransactionImpl;
import com.kp.cms.utilities.CommonUtil;



public class HostelCheckinHelper {
	
	private static Log log = LogFactory.getLog(HostelCheckinHelper.class);
	private static volatile HostelCheckinHelper hostelCheckinHelper = null;
	IHostelCheckinTransactions transaction = HostelCheckinTransactionImpl.getInstance();
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	
	private HostelCheckinHelper() {
	}
	
	public static HostelCheckinHelper getInstance() {
		if (hostelCheckinHelper == null) {
			hostelCheckinHelper = new HostelCheckinHelper();
		}
		return hostelCheckinHelper;
	}
	
	/**
	 * common query for all type of inputs
	 * @param HostelCheckinForm
	 * @return
	 */
	private String commonSearch(HostelCheckinForm hostelCheckinForm) {
		
		log.info("entered commonSearch of HostelCheckinHelper ");
		String searchCriteria = "select h.id," +
								" h.hlRoom.name,"+
								" h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name," +
								" h.bedNo," +
								" h.hlApplicationForm.hlHostelByHlApprovedHostelId.name,"+
								" h.hlRoom.floorNo ,"+
								" h.hlApplicationForm.isStaff,"+
								" h.admAppln.id"+
								" from HlRoomTransaction h";
		String serahcByStaff="select h.id," +
							 " h.hlRoom.name,"+
							 " h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name," +
							 " h.bedNo," +
							 " h.hlApplicationForm.hlHostelByHlApprovedHostelId.name,"+
							 " h.hlRoom.floorNo ,"+
							 " h.hlApplicationForm.isStaff,"+
							 "h.employee.id"+	
							 " from HlRoomTransaction h";
		
		if (hostelCheckinForm.getAppNo()!=null && hostelCheckinForm.getAppNo().trim().length() > 0) {
			String applicationNo = " where h.hlStatus.id=7 and h.hlApplicationForm.hlStatus.id=7 "+					
									" and h.admAppln.applnNo="+ hostelCheckinForm.getAppNo()+" and h.admAppln.appliedYear="+hostelCheckinForm.getYear();
			searchCriteria = searchCriteria + applicationNo;
		}else if (hostelCheckinForm.getRegNo()!=null && hostelCheckinForm.getRegNo().trim().length() > 0) {
			String registerNo = " inner join h.admAppln.students student"
								+" where h.admAppln.appliedYear="+hostelCheckinForm.getYear()
								+" and h.hlStatus.id=7 and h.hlApplicationForm.hlStatus.id=7 "
								+" and student.registerNo = "+
								"'"+ hostelCheckinForm.getRegNo()+"'";
			searchCriteria = searchCriteria + registerNo;
		}else if (hostelCheckinForm.getRollNo()!=null && hostelCheckinForm.getRollNo().trim().length() > 0) {
			String rollNo = " inner join h.admAppln.students student "
				+" where h.admAppln.appliedYear="+hostelCheckinForm.getYear()
				+"and h.hlStatus.id=7 and h.hlApplicationForm.hlStatus.id=7 "
				+" and student.rollNo="+
				"'"+ hostelCheckinForm.getRollNo()+"'";
			searchCriteria = searchCriteria + rollNo;
		}else if(hostelCheckinForm.getStaffId()!= null && hostelCheckinForm.getStaffId().trim().length() > 0){
			String staffID = " where h.hlStatus.id=7 and h.hlApplicationForm.hlStatus.id=7 "
							+" and h.employee.code="
							+"'"+ hostelCheckinForm.getStaffId()+"'";
			searchCriteria = serahcByStaff + staffID;
		}
		if (hostelCheckinForm.getHostelId()!=null && hostelCheckinForm.getHostelId().trim().length() > 0) {
			String appliedHostel = " and h.hlApplicationForm.hlHostelByHlApprovedHostelId.id="
					+ hostelCheckinForm.getHostelId();
			searchCriteria = searchCriteria + appliedHostel;
		}
		log.info("exiting commonSearch of HostelCheckinHelper");
		return searchCriteria;
	}
	
	/**
	 * building the query based on input fields
	 * @param HostelCheckinForm
	 * @return
	 */
	public String getSearchCriteria(HostelCheckinForm hostelCheckinForm) {
		
		log.info("Entering getSelectionSearchCriteria of HostelCheckinHelper");
		String statusCriteria = commonSearch(hostelCheckinForm);
		String searchCriteria= "";
		searchCriteria = statusCriteria +" and h.isActive =1 ";
		log.info("Exiting getSelectionSearchCriteria of HostelCheckinHelper");
		return searchCriteria;
	}
	
	/**
	 * converting the list of Bo's to TO's
	 * @param applicantHostelDetails
	 * @return
	 * @throws Exception
	 */
	public List<HostelCheckinTo> convertBOtoTO(List<Object> applicantHostelDetails,HostelCheckinForm hostelCheckinForm) throws Exception{
		 log.info("inside convertBOtoTO of HostelCheckinHelper");
		 List<HostelCheckinTo> hostelCheckinToList = new ArrayList<HostelCheckinTo>();
		HostelCheckinTo applicantHostelDetailsTO = null;
		Iterator<Object> applicantIt = applicantHostelDetails.iterator();
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			applicantHostelDetailsTO = new HostelCheckinTo();
			//0-application id
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				applicantHostelDetailsTO.setId(object[0].toString());
			}
			//1-admnApplid
			if(object[1]!= null && !object[1].toString().isEmpty()){
				applicantHostelDetailsTO.setApplicationId(Integer.valueOf(object[1].toString()));
			}
			//2-staffid
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
			//6-Room id
			if(object[6]!=null && !object[6].toString().isEmpty())
			{
				applicantHostelDetailsTO.setRoomId(object[6].toString());
			}
			//7-Room Name
			if(object[7]!=null && !object[7].toString().isEmpty()){
				applicantHostelDetailsTO.setRoomName(object[7].toString());
			}
			//8-approved room type .
			if(object[8]!=null && !object[8].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomTypeId(object[8].toString());
				List<HlRoomTypeFacilityTo> roomFacilityList=getRoomTypeFacilityByRoomTypeId(Integer.parseInt(object[8].toString()));
				applicantHostelDetailsTO.setRoomFacilityList(roomFacilityList);
			}
			//9-approved room type 
			if(object[9]!=null && !object[9].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomType(object[9].toString());
			}
			//10 - bed No
			if(object[10]!=null && !object[10].toString().isEmpty()){
				applicantHostelDetailsTO.setBedNo(object[10].toString());
			}
			//11- application id
			if(object[11]!=null && !object[11].toString().isEmpty()){
				applicantHostelDetailsTO.setHlAppFormId(Integer.valueOf(object[11].toString()));
				hostelCheckinForm.setFormId((Integer.parseInt(object[11].toString())));
			}
			//12-status id
			if(object[12]!=null && !object[12].toString().isEmpty()){
				applicantHostelDetailsTO.setStatusId(Integer.valueOf(object[12].toString()));
				hostelCheckinForm.setStatusId((Integer.parseInt(object[12].toString())));
			}
			//13-status type
			if(object[13]!=null && !object[13].toString().isEmpty()){
				applicantHostelDetailsTO.setStatusType(object[13].toString());
				hostelCheckinForm.setStatusType(object[13].toString());
			}
			//14-currentOccupnats count
			if(object[14]!=null && !object[14].toString().isEmpty()){
				applicantHostelDetailsTO.setCurrentOccupantsCount(Integer.parseInt((object[14]).toString()));
			}
			//15-currentReservation count
			if(object[15]!=null && !object[15].toString().isEmpty()){
				applicantHostelDetailsTO.setCurrentReservationCount(Integer.parseInt((object[15]).toString()));
			}
			//16-alloted date
			if(object[16]!=null && !object[16].toString().isEmpty()){
				applicantHostelDetailsTO.setAllotedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)object[16]), SQL_DATEFORMAT,FROM_DATEFORMAT));
			}
			//17-Hostel name
			if(object[17]!=null && !object[17].toString().isEmpty())
			{
				applicantHostelDetailsTO.setHostelName(object[17].toString());	
			}
			//18-floor no
			if(object[18]!=null && !object[18].toString().isEmpty())
			{
				applicantHostelDetailsTO.setFloorNo(Integer.parseInt(object[18].toString()));
			}
			hostelCheckinToList.add(applicantHostelDetailsTO);
		}
		
		 log.info("Exiting convertBOtoTO of HostelCheckinHelper");
		return hostelCheckinToList;
	}
	
	
	 /**
	  * getting the room Type Facilitys based on roomType Id
	 * @param string
	 */
	private List<HlRoomTypeFacilityTo> getRoomTypeFacilityByRoomTypeId(int roomTypeId) throws Exception{
		List<HlRoomTypeFacility> hlRoomTypeFecilityList = transaction.getHlFecilityDetails(roomTypeId);
		List<HlRoomTypeFacilityTo> hlRoomTypeFecilityTOList =copyHlFacilityBosToTos(hlRoomTypeFecilityList );
		log.info("Exitinging getHostelDetails of HostelCheckinHandler");
		return hlRoomTypeFecilityTOList;
	}

	/**
	  * getting the data from the database to keep in the form
	 * @param hostelCheckinform
	 * @param hostelApplicantDetails
	 * @throws Exception
	 */
	
	 
	 public List<HlRoomTypeFacilityTo> copyHlFacilityBosToTos(List<HlRoomTypeFacility> hlRoomTypeFacilityList) {
		 log.info("Entering copyHostelBosToTos of HostelCheckinHelper");
		 List<HlRoomTypeFacilityTo > hlRoomTypeFacilityToList = new ArrayList<HlRoomTypeFacilityTo>();
		Iterator<HlRoomTypeFacility > iterator = hlRoomTypeFacilityList.iterator();
		HlRoomTypeFacility hlRoomTypeFacility;
		HlRoomTypeFacilityTo hlRoomTypeFacilityTo = null;
		
		while (iterator.hasNext()) {
			
			 hlRoomTypeFacilityTo = new HlRoomTypeFacilityTo();
			hlRoomTypeFacility = (HlRoomTypeFacility) iterator.next();
			if(hlRoomTypeFacility.getHlFacility()!=null){
				if(hlRoomTypeFacility.getHlFacility().getName()!=null)
				hlRoomTypeFacilityTo.setName(hlRoomTypeFacility.getHlFacility().getName());
				hlRoomTypeFacilityTo.setHlFacilityId(hlRoomTypeFacility.getHlFacility().getId());
				hlRoomTypeFacilityTo.setTempChecked(true);
				//hlRoomTypeFacilityTo.setId(hlRoomTypeFacility.getHlFacility().getId());
			}
			hlRoomTypeFacilityTo.setId(hlRoomTypeFacility.getId());
			hlRoomTypeFacilityToList.add(hlRoomTypeFacilityTo);
		}
		
		 log.info("Exiting copyHostelBosToTos of HostelCheckinHelper");
		 return hlRoomTypeFacilityToList;
	}


		/**
		  * saving date to database
		 * @param hostelCheckinform
		 * @param hostelApplicantDetails
		 * @throws Exception
		 */	 
	 
	public HlRoomTransaction saveCheckinDetails(HostelCheckinForm hostelCheckinform,HostelCheckinTo hostelCheckinTo) throws Exception {
		 log.info("Entering saveCheckinDetails of HostelCheckinHelper");
		
		 String txnStatus = "failed";
		 String status="failed";
		 HlStatus  hlStatus = null;		

		 HlRoomTransaction hlRoomTransaction = new HlRoomTransaction();
		 AdmAppln admAppl = null;			 
		 Employee employee =null;
		 HlApplicationForm hlApplicationForm= null;
		 HlRoom hlRoom = null;
		 
			
			if(hostelCheckinTo!=null){
				/*if(hostelCheckinTo.getId()!=null && !hostelCheckinTo.getId().isEmpty()){
					hlRoomTransaction.setId(Integer.valueOf(hostelCheckinTo.getId()));
				}*/			
				if(hostelCheckinTo.getApplicationId()!=0){
					admAppl = new AdmAppln();
					admAppl.setId(hostelCheckinTo.getApplicationId());					
				}
				if(hostelCheckinTo.getStatusId()!=0){
					hlStatus =new HlStatus();
					hlStatus.setId(2);					
				}
				if(hostelCheckinTo.getHlAppFormId()!=0){
					hlApplicationForm = new HlApplicationForm();
					hlApplicationForm.setId(hostelCheckinTo.getHlAppFormId());
					hlApplicationForm.setFingerPrintId(hostelCheckinform.getFingerPrintId());
				}
				if(hostelCheckinTo.getStaffId()!=0){ 
					employee = new Employee();
					employee.setId(hostelCheckinTo.getStaffId()); 
				}
				if(hostelCheckinTo.getRoomId()!=null&& !hostelCheckinTo.getRoomName().isEmpty()){ 
					hlRoom = new HlRoom();
					hlRoom.setId(Integer.parseInt(hostelCheckinTo.getRoomId())); 					
				}
				if(hostelCheckinTo.getBedNo()!=null){
					hlRoomTransaction.setBedNo(Integer.parseInt(hostelCheckinTo.getBedNo()));
				}
				if(hostelCheckinform.getTxnDate()!=null){
					Calendar cal=Calendar.getInstance();
	        		String finalTime = hostelCheckinform.getTxnDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
	        		hlRoomTransaction.setTxnDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
				}
				if(hostelCheckinform.getComments()!=null && !hostelCheckinform.getComments().isEmpty()){
					hlRoomTransaction.setComments(hostelCheckinform.getComments());
				}	
				if(hostelCheckinform.getRemarks()!=null && !hostelCheckinform.getRemarks().isEmpty()){
					hlRoomTransaction.setRemarks(hostelCheckinform.getRemarks());
				}
				/*if(hostelCheckinform.getFecilityList()!=null && !hostelCheckinform.getFecilityList().isEmpty()){
					List<String> facilityList=hostelCheckinform.getFecilityList()
					hlFacility.setName(name)
				}*/
				 
				hlRoomTransaction.setCreatedBy(hostelCheckinform.getUserId());
				hlRoomTransaction.setCreatedDate(new Date()); 
				hlRoomTransaction.setModifiedBy(hostelCheckinform.getUserId());
				hlRoomTransaction.setLastModifiedDate(new Date());
				hlRoomTransaction.setIsActive(true);
				hlRoomTransaction.setCurrentOccupantsCount(hostelCheckinTo.getCurrentOccupantsCount());
				hlRoomTransaction.setCurrentReservationCount(hostelCheckinTo.getCurrentReservationCount());
				hlRoomTransaction.setHlRoom(hlRoom);
				hlRoomTransaction.setHlStatus(hlStatus);
				hlRoomTransaction.setAdmAppln(admAppl);
				hlRoomTransaction.setEmployee(employee);
				
				hlRoomTransaction.setHlApplicationForm(hlApplicationForm);
			}
			return hlRoomTransaction;
	}	
	
	
	public List<HlRoomTransaction> prepareBoListToMakeActive(List<HlRoomTransaction> boList,String userId) throws 

	Exception
		{
			log.debug("Entering prepareBoListToMakeActive in HostelCheckin Helper");
			List<HlRoomTransaction> boListWithActive = new ArrayList<HlRoomTransaction>();
			
			for (HlRoomTransaction hlRoomTransaction : boList) {
				hlRoomTransaction.setIsActive(true);
				hlRoomTransaction.setLastModifiedDate(new Date());
				hlRoomTransaction.setModifiedBy(userId);
				boListWithActive.add(hlRoomTransaction);
			}
			log.debug("Exiting prepareBoListToMakeActive in HostelCheckin Helper");
			return boListWithActive;
		}
	

	/**
	  * saving date to database
	 * @param hostelCheckinform
	 * @param hostelApplicantDetails
	 * @throws Exception
	 */
	
	public List<HlCheckinCheckoutFacility> saveFecilities(HostelCheckinForm hostelCheckinform,HostelCheckinTo hostelCheckinTo,Integer transactionId) throws Exception {
		 log.info("Entering saveCheckinDetails of HostelCheckinHelper");
		 HlApplicationForm hlApplicationForm= null;
		 List<HlCheckinCheckoutFacility> facilityList=new ArrayList<HlCheckinCheckoutFacility>();
		 	
			if(hostelCheckinTo!=null){
				if(hostelCheckinTo.getRoomFacilityList()!=null){
						Iterator<HlRoomTypeFacilityTo>	facilityItr =hostelCheckinTo.getRoomFacilityList().iterator();
						while (facilityItr.hasNext()) {
							HlRoomTypeFacilityTo hlRoomTypeFacilityTo = (HlRoomTypeFacilityTo) facilityItr.next();
								if(hlRoomTypeFacilityTo.getId()>0 && hlRoomTypeFacilityTo.isChecked()==true){
								HlCheckinCheckoutFacility  hlCheckinCheckoutFacility = new HlCheckinCheckoutFacility();	
								HlFacility hlFacility=new HlFacility();	
								hlFacility.setId(hlRoomTypeFacilityTo.getHlFacilityId());
								if(hostelCheckinTo.getHlAppFormId()>0){
									hlApplicationForm = new HlApplicationForm();
									hlApplicationForm.setId(hostelCheckinTo.getHlAppFormId());
								}
								HlRoomTransaction hlRoomTransaction = new HlRoomTransaction();
								hlRoomTransaction.setId(transactionId);
								
								hlCheckinCheckoutFacility.setHlFacility(hlFacility);
								hlCheckinCheckoutFacility.setHlRoomTransaction(hlRoomTransaction);
								hlCheckinCheckoutFacility.setHlApplicationForm(hlApplicationForm);
								hlCheckinCheckoutFacility.setModifiedBy(hostelCheckinform.getUserId());
								hlCheckinCheckoutFacility.setLastModifiedDate(new Date());
								hlCheckinCheckoutFacility.setIsActive(true);
								facilityList.add(hlCheckinCheckoutFacility);
								}
							/*hlRoomTransaction.setModifiedBy(hostelCheckinform.getUserId());
							hlRoomTransaction.setLastModifiedDate(new Date());*/
						}
				}
				
			}
			return facilityList;
	}
		
	
		
	public List<HostelTO> copyHostelBosToTos(List<HlHostel> hostelList) {
		 log.info("Entering copyHostelBosToTos of HostelCheckinHelper");
		List<HostelTO> hostelTOList = new ArrayList<HostelTO>();
		Iterator<HlHostel> iterator = hostelList.iterator();
		HlHostel hlHostel;
		HostelTO hostelTO = null;
		
		while (iterator.hasNext()) {
			
			hostelTO = new HostelTO();
			hlHostel = (HlHostel) iterator.next();
			if(hlHostel.getName()!=null && !hlHostel.getName().isEmpty()){
			hostelTO.setName(hlHostel.getName());
			}
			hostelTO.setId(hlHostel.getId());
			hostelTOList.add(hostelTO);
		}
		
		 log.info("Exiting copyHostelBosToTos of HostelCheckinHelper");
		 return hostelTOList;
	}
	
public String getChecklinDetailsCriteria(HostelCheckinForm hostelCheckinForm) {
		
		log.info("entered commonSearch of HostelCheckinHelper ");
		String searchCriteria = "select h.id, h.admAppln.id, h.employee.id," +
								" h.admAppln.personalData.firstName," +
								" h.admAppln.personalData.middleName," +
								" h.admAppln.personalData.lastName," +
								" h.hlRoom.id," +
								" h.hlRoom.name," +
								" h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.id," +
								" h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name," +
								" h.bedNo," +
								" h.hlApplicationForm.id," +
								" h.hlStatus.id," +
								" h.hlStatus.statusType," +
								" h.currentOccupantsCount," +
								" h.currentReservationCount," +
								" h.txnDate," +
								" h.hlApplicationForm.hlHostelByHlApprovedHostelId.name,"+
								" h.hlRoom.floorNo "+
								" from HlRoomTransaction h where h.id="+hostelCheckinForm.getTransactionId();
		
		String searchForStaffId="select h.id, h.admAppln.id, h.employee.id,"+
								"h.hlApplicationForm.employee.firstName,"+
								"h.hlApplicationForm.employee.middleName,"+
								"h.hlApplicationForm.employee.lastName,"+
								" h.hlRoom.id," +
								" h.hlRoom.name," +
								" h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.id," +
								" h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name," +
								" h.bedNo," +
								" h.hlApplicationForm.id," +
								" h.hlStatus.id," +
								" h.hlStatus.statusType," +
								" h.currentOccupantsCount," +
								" h.currentReservationCount," +
								" h.txnDate," +
								" h.hlApplicationForm.hlHostelByHlApprovedHostelId.name,"+
								" h.hlRoom.floorNo "+
								" from HlRoomTransaction h where h.id="+hostelCheckinForm.getTransactionId();
		log.info("exiting commonSearch of HostelCheckinHelper");
		if(hostelCheckinForm.getIsStaff().equalsIgnoreCase("true"))
			return searchForStaffId;
		else
			return searchCriteria;
	}

public List<HostelCheckinTo> convertBOtoTOList(List<Object> applicantHostelDetails, HostelCheckinForm hostelCheckinForm) {
	// TODO Auto-generated method stub
	 log.info("inside convertBOtoTO of HostelCheckinHelper");
	 List<HostelCheckinTo> hostelCheckinToList = new ArrayList<HostelCheckinTo>();
	HostelCheckinTo applicantHostelDetailsTO = null;
	Iterator<Object> applicantIt = applicantHostelDetails.iterator();
	while(applicantIt.hasNext())
	{
		Object[] object =(Object[]) applicantIt.next();
		applicantHostelDetailsTO = new HostelCheckinTo();
		//0-application id
		if(object[0]!=null && !object[0].toString().isEmpty())
		{
			applicantHostelDetailsTO.setId(object[0].toString());
		}
		//1-Room Name
		if(object[1]!=null && !object[1].toString().isEmpty()){
			applicantHostelDetailsTO.setRoomName(object[1].toString());
		}
		//2-approved room type 
		if(object[2]!=null && !object[2].toString().isEmpty() ){
			applicantHostelDetailsTO.setRoomType(object[2].toString());
		}
		//3 - bed No
		if(object[3]!=null && !object[3].toString().isEmpty()){
			applicantHostelDetailsTO.setBedNo(object[3].toString());
		}
		//4-Hostel name
		if(object[4]!=null && !object[4].toString().isEmpty())
		{
			applicantHostelDetailsTO.setHostelName(object[4].toString());	
		}
		//5-floor no
		if(object[5]!=null && !object[5].toString().isEmpty())
		{
			applicantHostelDetailsTO.setFloorNo(Integer.parseInt(object[5].toString()));
		}
		//6-isStaff
		if(object[6]!=null && !object[6].toString().isEmpty())
		{
			applicantHostelDetailsTO.setIsStaff(object[6].toString());
		}
		if(object[7]!=null && !object[7].toString().isEmpty())
		{
			applicantHostelDetailsTO.setApplicationId(Integer.parseInt(object[7].toString()));
		}
		hostelCheckinToList.add(applicantHostelDetailsTO);
	}
	
	 log.info("Exiting convertBOtoTO of HostelCheckinHelper");
	return hostelCheckinToList;
}
}
