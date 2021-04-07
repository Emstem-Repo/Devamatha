package com.kp.cms.helpers.hostel;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
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
import com.kp.cms.forms.hostel.HostelCheckoutForm;
import com.kp.cms.to.hostel.HlCheckinCheckoutFacilityTo;
import com.kp.cms.to.hostel.HlDamageTO;
import com.kp.cms.to.hostel.HlRoomTypeFacilityTo;
import com.kp.cms.to.hostel.HostelCheckoutTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelCheckoutTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelCheckoutTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelCheckoutHelper {
	
	private static Log log = LogFactory.getLog(HostelCheckoutHelper.class);
	private static volatile HostelCheckoutHelper hostelCheckoutHelper = null;
	IHostelCheckoutTransactions transaction = HostelCheckoutTransactionImpl.getInstance();
	
	private HostelCheckoutHelper() {
	}
	
	public static HostelCheckoutHelper getInstance() {
		if (hostelCheckoutHelper == null) {
			hostelCheckoutHelper = new HostelCheckoutHelper();
		}
		return hostelCheckoutHelper;
	}
	
	/**
	 * common query for all type of inputs
	 * @param HostelCheckinForm
	 * @return
	 */
	private String commonSearch(HostelCheckoutForm hostelCheckoutForm) throws Exception{
		
		log.info("entered commonSearch of HostelCheckoutHelper ");
		   Properties prop = new Properties();
		 InputStream in = HostelCheckoutHelper.class.getClassLoader().getResourceAsStream("resources/application.properties");
	        prop.load(in);
	        String roomStatusId = prop.getProperty("knowledgepro.hostel.visitor.room.CheckInstatus");
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
								" h.txnDate"+
								" from HlRoomTransaction h";
								
		
		String searchForStaffId ="select h.id,"+"h.admAppln.id,"+"h.employee.id," +
								" h.employee.firstName," +
								" h.employee.middleName," +
								" h.employee.lastName," +
								" h.hlRoom.id," +
								" h.hlRoom.name," +
								" h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.id," +
								" h.hlApplicationForm.hlRoomTypeByHlApprovedRoomTypeId.name," +
								" h.bedNo,"+
								" h.hlApplicationForm.id," +
								" h.hlStatus.id," +
								" h.hlStatus.statusType," +
								" h.currentOccupantsCount," +
								" h.currentReservationCount," +
								" h.txnDate"+
								" from HlRoomTransaction h";						
		if (hostelCheckoutForm.getAppNo()!=null && hostelCheckoutForm.getAppNo().trim().length() > 0) {
			String applicationNo = " where h.hlApplicationForm.hlStatus.id= "+roomStatusId+					
									" and h.admAppln.applnNo='"+ hostelCheckoutForm.getAppNo()+"' and h.admAppln.appliedYear="+hostelCheckoutForm.getYear();
			searchCriteria = searchCriteria + applicationNo;
		}else if (hostelCheckoutForm.getRegNo()!=null && hostelCheckoutForm.getRegNo().trim().length() > 0) {
			String registerNo = " inner join h.admAppln.students student"
								+" where h.admAppln.id = student.admAppln.id"
								+" and h.hlApplicationForm.hlStatus.id="+roomStatusId
								+" and student.registerNo = "+
								"'"+ hostelCheckoutForm.getRegNo()+"' and h.admAppln.appliedYear="+hostelCheckoutForm.getYear();
			searchCriteria = searchCriteria + registerNo;
		}else if (hostelCheckoutForm.getRollNo()!=null && hostelCheckoutForm.getRollNo().trim().length() > 0) {
			String rollNo = " inner join h.admAppln.students student "
				+" where h.admAppln.id = student.admAppln.id "
				+"and h.hlApplicationForm.hlStatus.id= "+roomStatusId
				+" and student.rollNo="+
				"'"+ hostelCheckoutForm.getRollNo()+"' and h.admAppln.appliedYear="+hostelCheckoutForm.getYear();
			searchCriteria = searchCriteria + rollNo;
		}else if(hostelCheckoutForm.getStaffId()!= null && hostelCheckoutForm.getStaffId().trim().length() > 0){
			String staffID = " where h.hlApplicationForm.hlStatus.id="+roomStatusId
							+" and h.employee.code='"+hostelCheckoutForm.getStaffId()+"'";
			searchCriteria = searchForStaffId + staffID;
		}
		if (hostelCheckoutForm.getHostelId()!=null && hostelCheckoutForm.getHostelId().trim().length() > 0) {
			String appliedHostel = " and h.hlApplicationForm.hlHostelByHlApprovedHostelId.id="
					+ hostelCheckoutForm.getHostelId();
			searchCriteria = searchCriteria + appliedHostel;
		}
		log.info("exiting commonSearch of HostelCheckoutHelper");
		return searchCriteria;
	}
	
	/**
	 * building the query based on input fields
	 * @param HostelCheckinForm
	 * @return
	 */
	public String getSearchCriteria(HostelCheckoutForm hostelCheckoutForm) throws Exception{
		
		log.info("Entering getSelectionSearchCriteria of HostelCheckoutHelper");
		String statusCriteria = commonSearch(hostelCheckoutForm);
		String searchCriteria= "";
		searchCriteria = statusCriteria +" and h.isActive =1  and h.hlStatus.id=2";
		log.info("Exiting getSelectionSearchCriteria of HostelCheckoutHelper");
		return searchCriteria;
	}
	
	/**
	 * getting damage details
	 * building the query based on input fields
	 * @param HostelCheckinForm
	 * @return
	 */
	public String getDamageCriteria(HostelCheckoutForm hostelCheckoutForm) {
		log.info("Entering getDamageCriteria of HostelCheckoutHelper");
		String damageCriteria="select hld.id," +
								"hld.amount," +
								"hld.date," +
								"hld.description," +
								"hld.time " +
								"from HlDamage hld " +
								"where hld.isActive=1 and hld.hlApplicationForm.id=" +
								"'"+ hostelCheckoutForm.getFormId()+"'" +
								"or hld.employee.id=" +
								"'"+ hostelCheckoutForm.getStaffId()+"'";
		log.info("Exiting getDamageCriteria of HostelCheckoutHelper");
		return damageCriteria;
	}
	/**
	 * converting the list of Bo's to TO's
	 * @param applicantHostelDetails
	 * @return
	 * @throws Exception
	 */
	public HostelCheckoutTo convertBOtoTO(List<Object> applicantHostelDetails,HostelCheckoutForm hostelCheckoutForm) throws Exception{
		 log.info("inside convertBOtoTO of HostelCheckoutHelper");
//		 List<HostelCheckoutTo> hostelCheckoutToList = new ArrayList<HostelCheckoutTo>();
		HostelCheckoutTo applicantHostelDetailsTO = null;
		Iterator<Object> applicantIt = applicantHostelDetails.iterator();
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			applicantHostelDetailsTO = new HostelCheckoutTo();
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
			//6-reservation date
			if(object[6]!=null && !object[6].toString().isEmpty())
			{
				applicantHostelDetailsTO.setRoomId(object[6].toString());
			}
			//7approved hostel id
			if(object[7]!=null && !object[7].toString().isEmpty()){
				applicantHostelDetailsTO.setRoomName(object[7].toString());
			}
			//8-approved room type .
			if(object[8]!=null && !object[8].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomTypeId(object[8].toString());
				List<HlRoomTypeFacilityTo> roomFacilityList=getRoomTypeFacilityByRoomTypeId(Integer.parseInt(object[8].toString()));
				applicantHostelDetailsTO.setRoomFacilityList(roomFacilityList);
			}
			//9-approved room type name
			if(object[9]!=null && !object[9].toString().isEmpty() ){
				applicantHostelDetailsTO.setRoomType(object[9].toString());
			}
			//10 - bed No
			if(object[10]!=null && !object[10].toString().isEmpty()){
				applicantHostelDetailsTO.setBedNo(object[10].toString());
			}
			if(object[11]!=null && !object[11].toString().isEmpty()){
				applicantHostelDetailsTO.setHlAppFormId(Integer.valueOf(object[11].toString()));
				hostelCheckoutForm.setFormId(Integer.parseInt(object[11].toString()));
				List<HlCheckinCheckoutFacilityTo> checkinCheckoutFacilityList = getCheckinCheckoutFacilityList(Integer.parseInt(object[11].toString()),Integer.parseInt(object[0].toString()));
				applicantHostelDetailsTO.setCheckoutList(checkinCheckoutFacilityList);
			}
			if(object[12]!=null && !object[12].toString().isEmpty()){
				applicantHostelDetailsTO.setStatusId(Integer.valueOf(object[12].toString()));
				hostelCheckoutForm.setStatusId((Integer.parseInt(object[12].toString())));
			}
			if(object[13]!=null && !object[13].toString().isEmpty()){
				applicantHostelDetailsTO.setStatusType(object[13].toString());
				//hostelCheckoutForm.setStatusType(object[13].toString());
			}
			if(object[14]!=null && !object[14].toString().isEmpty()){
				applicantHostelDetailsTO.setCurrentOccupantsCount(Integer.parseInt((object[14]).toString()));
			}
			if(object[15]!=null && !object[15].toString().isEmpty()){
				applicantHostelDetailsTO.setCurrentReservationCount(Integer.parseInt((object[15]).toString()));
			}
			if(object[16]!=null && !object[16].toString().isEmpty()){
				String dateString = object[16].toString().substring(0, 10);
				String inputDateFormat = "yyyy-mm-dd";
				String outPutdateFormat = "dd/mm/yyyy";
				//hostelAllocationform.setReceiptDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
				applicantHostelDetailsTO.setTxnDate(CommonUtil.ConvertStringToDateFormat(dateString, inputDateFormat, outPutdateFormat));
				//hostelCheckoutForm.setTxnDate(CommonUtil.ConvertStringToDate(object[16].toString()));
			}
		}
		//hostelCheckoutToList.add(applicantHostelDetailsTO);
		 log.info("Exiting convertBOtoTO of HostelCheckoutHelper");
		return applicantHostelDetailsTO;
	}
	/**
	* getting the CheckinCheckoutFacility  based on AppFormId,Roomtx Id
	 * @param string
	 */
	private List<HlCheckinCheckoutFacilityTo> getCheckinCheckoutFacilityList(int formId, int roomTxId) throws Exception{
		List<HlCheckinCheckoutFacility> checkinCheckoutFacilityList = transaction.getCheckinCheckoutFacilityList(formId,roomTxId);
		List<HlCheckinCheckoutFacilityTo> checkinCheckoutFacilityListTo =copyHlCinoutBosToTos(checkinCheckoutFacilityList);
		log.info("Exitinging getHostelDetails of HostelCheckoutHandler");
		return checkinCheckoutFacilityListTo;
	}
	
	/**
	  * getting the data from the database to keep in the form
	 * @param hostelCheckoutform
	 * @param hostelApplicantDetails
	 * @throws Exception
	 */
	 public List<HlCheckinCheckoutFacilityTo> copyHlCinoutBosToTos(List<HlCheckinCheckoutFacility> checkinCheckoutFacilityList) {
		 log.info("Entering copyHlCinoutBosToTos of HostelCheckInOutHelper");
		 List<HlCheckinCheckoutFacilityTo > hlCheckinCheckoutFacilityToList = new ArrayList<HlCheckinCheckoutFacilityTo>();
		 Iterator<HlCheckinCheckoutFacility > iterator = checkinCheckoutFacilityList.iterator();
		 HlCheckinCheckoutFacility hlCinoutFacility;
		 HlCheckinCheckoutFacilityTo hlCinoutFacilityTo = null;
		while (iterator.hasNext()) {			
			hlCinoutFacilityTo = new HlCheckinCheckoutFacilityTo();
			hlCinoutFacility = (HlCheckinCheckoutFacility) iterator.next();
			 	if(hlCinoutFacility.getHlFacility()!=null){
			 		if(hlCinoutFacility.getHlFacility().getName()!=null)
			 			hlCinoutFacilityTo.setName(hlCinoutFacility.getHlFacility().getName());
			 		hlCinoutFacilityTo.setHlFacilityId(hlCinoutFacility.getHlFacility().getId());
			 		if(hlCinoutFacility.getHlFacility().getId()>0){
			 		hlCinoutFacilityTo.setSelected(true);
			 		hlCinoutFacilityTo.setDummySelected(true);
			 		}
			 				//hlRoomTypeFacilityTo.setId(hlRoomTypeFacility.getHlFacility().getId());
			 	}
			 	hlCinoutFacilityTo.setId(hlCinoutFacility.getId());
			 	hlCheckinCheckoutFacilityToList.add(hlCinoutFacilityTo);
		}		
		 log.info("Exiting copyHlCinoutBosToTos of HostelCheckInOutHelper");
		 return hlCheckinCheckoutFacilityToList;
	}
	 /**
	  * converting damageDetailsBo to damageDetailsTo
	 * @param hostelCheckoutform
	 * @param damageDetails
	 * @throws Exception
	 */
	 public List<HlDamageTO> convertDamageBOtoTO(List<Object> damageDetails,HostelCheckoutForm hostelCheckoutForm) throws Exception{
		 log.info("inside convertBOtoTO of HostelCheckoutHelper");

		 List<HlDamageTO>damageList=new ArrayList<HlDamageTO>();
		 HlDamageTO   damageDetailsTo= null;
		Iterator<Object> applicantIt = damageDetails.iterator();
		while(applicantIt.hasNext()){
			Object[] object =(Object[]) applicantIt.next();
			damageDetailsTo = new HlDamageTO();
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				damageDetailsTo.setId(Integer.parseInt(object[0].toString()));
			}
			if(object[1]!=null && !object[1].toString().isEmpty())
			{
				damageDetailsTo.setAmount(BigDecimal.valueOf(Double.parseDouble(object[1].toString())));
			}
			if(object[2]!=null && !object[2].toString().isEmpty())
			{
				damageDetailsTo.setDate(CommonUtil.formatSqlDate1(object[2].toString()));
			}
			if(object[3]!=null && !object[3].toString().isEmpty())
			{
				damageDetailsTo.setDescription(object[3].toString());
			}
			if(object[4]!=null && !object[4].toString().isEmpty())
			{
				damageDetailsTo.setTime(object[4].toString());
			}
			damageList.add(damageDetailsTo);
		}
		return damageList;
			
		}
	 
	 /**
	  * saving date to database
	 * @param hostelCheckinform
	 * @param hostelApplicantDetails//HlCheckinCheckoutFacilityTo
	 * @throws Exception
	 */
	
	public List<HlCheckinCheckoutFacility> saveFecilities(HostelCheckoutForm hostelCheckoutform,HostelCheckoutTo hostelCheckoutTo) throws Exception {
		 log.info("Entering saveCheckoutDetails of HostelCheckoutHelper");
		 HlRoomTransaction hlRoomTransaction = new HlRoomTransaction();
		 HlApplicationForm hlApplicationForm= null;
		 List<HlCheckinCheckoutFacility> hlCheckinCheckoutfacilityList=new ArrayList<HlCheckinCheckoutFacility>();
		 	
			if(hostelCheckoutTo!=null){
				if(hostelCheckoutTo.getCheckoutList()!=null){
						Iterator<HlCheckinCheckoutFacilityTo>	facilityItr =hostelCheckoutTo.getCheckoutList().iterator();
						while (facilityItr.hasNext()) {
							HlCheckinCheckoutFacilityTo hlCheckinCheckoutFacilityTo = (HlCheckinCheckoutFacilityTo) facilityItr.next();
								if(hlCheckinCheckoutFacilityTo.getId()>0 ){
									if(hlCheckinCheckoutFacilityTo.isSelected()){
								HlCheckinCheckoutFacility  hlCheckinCheckoutFacility = new HlCheckinCheckoutFacility();	
								HlFacility hlFacility=new HlFacility();	
								hlFacility.setId(hlCheckinCheckoutFacilityTo.getHlFacilityId());
								if(hostelCheckoutTo.getHlAppFormId()>0){
									hlApplicationForm = new HlApplicationForm();
									hlApplicationForm.setId(hostelCheckoutTo.getHlAppFormId());
								}
								if(hostelCheckoutTo.getId()!=null){
									hlRoomTransaction = new HlRoomTransaction();
									hlRoomTransaction.setId(Integer.parseInt(hostelCheckoutTo.getId()));
								}
								hlCheckinCheckoutFacility.setHlFacility(hlFacility);
								hlCheckinCheckoutFacility.setHlRoomTransaction(hlRoomTransaction);
								hlCheckinCheckoutFacility.setHlApplicationForm(hlApplicationForm);
								hlCheckinCheckoutFacility.setModifiedBy(hostelCheckoutform.getUserId());
								hlCheckinCheckoutFacility.setLastModifiedDate(new Date());
								hlCheckinCheckoutFacility.setIsActive(true);
								hlCheckinCheckoutfacilityList.add(hlCheckinCheckoutFacility);
								}
								}
							/*hlRoomTransaction.setModifiedBy(hostelCheckinform.getUserId());
							hlRoomTransaction.setLastModifiedDate(new Date());*/
						}
				}
				
			}
			return hlCheckinCheckoutfacilityList;
	}
		
	/**
	  * getting the room Type Facilities based on roomType Id
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
		 log.info("Entering copyHostelBosToTos of HostelCheckoutHelper");
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
//			 			hlRoomTypeFacilityTo.setSelected(true);
			 				//hlRoomTypeFacilityTo.setId(hlRoomTypeFacility.getHlFacility().getId());
			 	}
			 	hlRoomTypeFacilityTo.setId(hlRoomTypeFacility.getId());
			 	hlRoomTypeFacilityToList.add(hlRoomTypeFacilityTo);
		}
		
		 log.info("Exiting copyHostelBosToTos of HostelCheckoutHelper");
		 return hlRoomTypeFacilityToList;
	}
	 
	 
	 
	 /**
	  * saving data to database
	 * @param hostelCheckoutform
	 * @param hostelApplicantDetails
	 * @throws Exception
	 */	 
 
public HlRoomTransaction saveCheckoutDetails(HostelCheckoutForm hostelCheckoutform,HostelCheckoutTo hostelCheckoutTo) throws Exception {
	 log.info("Entering saveCheckoutDetails of HostelCheckoutHelper");
	
	 HlStatus  hlStatus = null;		

	 HlRoomTransaction hlRoomTransaction = new HlRoomTransaction();
	 AdmAppln admAppl = null;			 
	 Employee employee =null;
	 HlApplicationForm hlApplicationForm= null;
	 HlRoom hlRoom = null;
		if(hostelCheckoutTo!=null){
			if(hostelCheckoutTo.getApplicationId()!=0){
				admAppl = new AdmAppln();
				admAppl.setId(hostelCheckoutTo.getApplicationId());					
			}
			if(hostelCheckoutTo.getStatusId()!=0){
				hlStatus =new HlStatus();
				hlStatus.setId(3);					
			}
			if(hostelCheckoutTo.getHlAppFormId()!=0){
				hlApplicationForm = new HlApplicationForm();
				hlApplicationForm.setId(hostelCheckoutTo.getHlAppFormId());
			}
			if(hostelCheckoutTo.getStaffId()!=0){ 
				employee = new Employee();
				employee.setId(hostelCheckoutTo.getStaffId()); 
			}
			if(hostelCheckoutTo.getRoomId()!=null&& !hostelCheckoutTo.getRoomName().isEmpty()){ 
				hlRoom = new HlRoom();
				hlRoom.setId(Integer.parseInt(hostelCheckoutTo.getRoomId())); 					
			}
			if(hostelCheckoutTo.getBedNo()!=null){
				hlRoomTransaction.setBedNo(Integer.parseInt(hostelCheckoutTo.getBedNo()));
			}
			if(hostelCheckoutform.getCheckoutDate()!=null){
				Calendar cal=Calendar.getInstance();
        		String finalTime = hostelCheckoutform.getCheckoutDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
				hlRoomTransaction.setTxnDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
			}
			if(hostelCheckoutform.getRemarks()!=null && !hostelCheckoutform.getRemarks().isEmpty()){
				hlRoomTransaction.setRemarks(hostelCheckoutform.getRemarks());
			}
			hlRoomTransaction.setCreatedBy(hostelCheckoutform.getUserId());
			hlRoomTransaction.setCreatedDate(new Date()); 
			hlRoomTransaction.setModifiedBy(hostelCheckoutform.getUserId());
			hlRoomTransaction.setLastModifiedDate(new Date());
			hlRoomTransaction.setIsActive(true);
			hlRoomTransaction.setCurrentOccupantsCount(hostelCheckoutTo.getCurrentOccupantsCount()-1);
			hlRoomTransaction.setCurrentReservationCount(hostelCheckoutTo.getCurrentReservationCount());
			hlRoomTransaction.setHlRoom(hlRoom);
			hlRoomTransaction.setHlStatus(hlStatus);
			hlRoomTransaction.setAdmAppln(admAppl);
			hlRoomTransaction.setEmployee(employee);
			hlRoomTransaction.setHlApplicationForm(hlApplicationForm);
		}
		return hlRoomTransaction;
	}	

	
	
	public List<HostelTO> copyHostelBosToTos(List<HlHostel> hostelList) {
		 log.info("Entering copyHostelBosToTos of HostelCheckoutHelper");
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
		
		 log.info("Exiting copyHostelBosToTos of HostelCheckoutHelper");
		 return hostelTOList;
	}
}

	


