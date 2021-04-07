package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.forms.hostel.HostelCheckinForm;
import com.kp.cms.forms.hostel.HostelReqReportForm;
import com.kp.cms.to.hostel.HlRoomTypeFacilityTo;
import com.kp.cms.to.hostel.HostelCheckinTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelReqReportTo;
import  com.kp.cms.transactions.hostel.IHostelReqReportTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelReqReportTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelReqReportHelper {
	private static Log log = LogFactory.getLog(HostelReqReportHelper.class);
	private static volatile HostelReqReportHelper hostelReqReportHelper = null;
	IHostelReqReportTransactions transaction = HostelReqReportTransactionImpl.getInstance();
	
	private HostelReqReportHelper() {
	}
	
	public static HostelReqReportHelper getInstance() {
		if (hostelReqReportHelper == null) {
			hostelReqReportHelper = new HostelReqReportHelper();
		}
		return hostelReqReportHelper;
	}
	
	/**
	 * building the query based on input fields
	 * @param HostelReqReportForm
	 * @return
	 */
	public String getSearchCriteria(HostelReqReportForm hostelReqReportForm) {
		
		log.info("Entering getSelectionSearchCriteria of HostelReqReportHelper");
		String statusCriteria = commonSearch(hostelReqReportForm);
		String searchCriteria= "";
		searchCriteria = statusCriteria +" and h.isActive =1 " +"group by h.id";
		log.info("Exiting getSelectionSearchCriteria of HostelReqReportHelper");
		return searchCriteria;
	}
	/**
	 * common query for all type of inputs
	 * @param HostelReqReportForm
	 * @return
	 */
	private String commonSearch(HostelReqReportForm hostelReqReportForm) {
		
		log.info("entered commonSearch of HostelReqReportHelper ");
		String searchCriteria = " select h.id," +
								" h.admAppln.id," +
								" h.employee.id," +
								" h.admAppln.personalData.firstName," +
								" h.admAppln.personalData.middleName," +
								" h.admAppln.personalData.lastName," +
								" h.admAppln.applnNo," +
								" h.appliedDate," +
								" h.hlHostelByHlAppliedHostelId.id," +
								" h.hlHostelByHlAppliedHostelId.name," +
								" h.hlRoomTypeByHlAppliedRoomTypeId.id," +
								" h.hlRoomTypeByHlAppliedRoomTypeId.name," +
								" flRoom.noOfRooms" +
								" from HlApplicationForm h" +
								" inner join h.hlHostelByHlAppliedHostelId.hlFloorRooms flRoom " +
								" inner join h.admAppln.students student";
		if (hostelReqReportForm.getHostelId()!=null && !hostelReqReportForm.getHostelId().isEmpty()) {
			String hostelqry = " where h.hlHostelByHlAppliedHostelId.id = flRoom.hlHostel.id" 
							   +" and h.hlRoomTypeByHlAppliedRoomTypeId = flRoom.hlRoomType.id"
							   +" and h.admAppln.id = student.admAppln.id "  
							   +" and h.hlHostelByHlAppliedHostelId="+"'"+ hostelReqReportForm.getHostelId()+"'";
			searchCriteria = searchCriteria + hostelqry;
		}
		if(hostelReqReportForm.getAppliedDate()!= null && hostelReqReportForm.getAppliedDate().trim().length() > 0){
			String appDate = " and h.appliedDate= "
							+"'"+ CommonUtil.ConvertStringToSQLDate(hostelReqReportForm.getAppliedDate())+"'";
			searchCriteria = searchCriteria + appDate;
		}
		log.info("exiting commonSearch of HostelReqReportHelper");
		return searchCriteria;
	}
	/**
	 * building the query based on input fields
	 * @param HostelReqReportForm
	 * @return
	 */
	public String getRoomTypeCriteria(HostelReqReportForm hostelReqReportForm) {
		
		log.info("Entering getSelectionSearchCriteria of HostelReqReportHelper");
		String roomTypeCriteria = roomTypeSearch(hostelReqReportForm);
		String searchCriteria= "";
		roomTypeCriteria = roomTypeCriteria +" and h.isActive =1 " +" group by h.id";
		log.info("Exiting getSelectionSearchCriteria of HostelReqReportHelper");
		return roomTypeCriteria;
	}
	/**
	 * common query for all type of inputs
	 * @param HostelReqReportForm
	 * @return
	 */
	private String roomTypeSearch(HostelReqReportForm hostelReqReportForm) {
		
		log.info("entered roomTypeSearch  of HostelReqReportHelper ");
		String searchCriteria = " select distinct h.hlRoomTypeByHlAppliedRoomTypeId.name," +
								" flRoom.noOfRooms" +
								" from HlApplicationForm h" +
								" inner join h.hlHostelByHlAppliedHostelId.hlFloorRooms flRoom " ;
		if (hostelReqReportForm.getHostelId()!=null && !hostelReqReportForm.getHostelId().isEmpty()) {
			String hostelqry = " where h.hlHostelByHlAppliedHostelId.id = flRoom.hlHostel.id" 
							   +" and h.hlRoomTypeByHlAppliedRoomTypeId = flRoom.hlRoomType.id"
							   +" and h.hlHostelByHlAppliedHostelId="+"'"+ hostelReqReportForm.getHostelId()+"'";
			searchCriteria = searchCriteria + hostelqry;
		}
		if(hostelReqReportForm.getAppliedDate()!= null && hostelReqReportForm.getAppliedDate().trim().length() > 0){
			String appDate = " and h.appliedDate= "
							+"'"+ CommonUtil.ConvertStringToSQLDate(hostelReqReportForm.getAppliedDate())+"'";
			searchCriteria = searchCriteria + appDate;
		}
		log.info("exiting roomTypeSearch of HostelReqReportHelper");
		return searchCriteria;
	}
	/**
	 * converting the list of Bo's to TO's
	 * @param hostelReqReportDetailsBo
	 * @return
	 * @throws Exception
	 */
	public List<HostelReqReportTo> convertBOtoTO(List<Object> hostelReqReportDetailsBo,HostelReqReportForm hostelReqReportForm) throws Exception{
		 log.info("inside convertBOtoTO of HostelReqReportHelper");
		 List<HostelReqReportTo> hostelReqReportToList = new ArrayList<HostelReqReportTo>();
		 HostelReqReportTo hostelReqReportTo = null;
		Iterator<Object> applicantIt = hostelReqReportDetailsBo.iterator();
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			hostelReqReportTo = new HostelReqReportTo();
			//0-application id
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				hostelReqReportTo.setId(object[0].toString());
			}
			//1-admnApplid
			if(object[1]!= null && !object[1].toString().isEmpty()){
				hostelReqReportTo.setAdmApplnId(Integer.valueOf(object[1].toString()));
			}
			//2-staffid
			if(object[2]!= null && !object[2].toString().isEmpty()){
				hostelReqReportTo.setEmployeeId(Integer.valueOf(object[2].toString()));
			}
			//3,4,5 - student/staff name
			if(object[3]!=null && object[4]!=null && object[5]!=null && !object[3].toString().isEmpty()&& !object[4].toString().isEmpty() && !object[5].toString().isEmpty()){
				hostelReqReportTo.setApplicantName(object[3].toString()+" "+ object[4].toString()+" "+ object[5].toString());
			}else if(object[3]!=null && object[4]!=null  && !object[3].toString().isEmpty()&& !object[4].toString().isEmpty()){
				hostelReqReportTo.setApplicantName(object[3].toString()+" "+ object[4].toString());
			}else if(object[3]!=null && !object[3].toString().isEmpty()){
				hostelReqReportTo.setApplicantName(object[3].toString());
			}
			//6-reservation date
			if(object[6]!=null && !object[6].toString().isEmpty())
			{
				hostelReqReportTo.setApplnNo(Integer.valueOf(object[6].toString()));
			}
			//7approved hostel id
			if(object[7]!=null && !object[7].toString().isEmpty()){
				hostelReqReportTo.setAppliedDate(object[7].toString());
			}
			//8-approved room type .
			if(object[8]!=null && !object[8].toString().isEmpty() ){
				hostelReqReportTo.setHostelId(object[8].toString());
			}
			//9-approved room type name
			if(object[9]!=null && !object[9].toString().isEmpty() ){
				hostelReqReportTo.setHostelName(object[9].toString());
			}
			//10 - bed No
			if(object[10]!=null && !object[10].toString().isEmpty()){
				hostelReqReportTo.setRoomTypeId(object[10].toString());
			}
			if(object[11]!=null && !object[11].toString().isEmpty()){
				hostelReqReportTo.setRoomTypeName(object[11].toString());
			}
			if(object[12]!=null && !object[12].toString().isEmpty()){
				hostelReqReportTo.setNoOfRooms(Integer.valueOf(object[12].toString()));
			}
			hostelReqReportToList.add(hostelReqReportTo);
		}
		
		 log.info("Exiting convertBOtoTO of HostelReqReportHelper");
		return hostelReqReportToList;
	}
	public List<HostelTO> copyHostelBosToTos(List<HlHostel> hostelList) {
		 log.info("Entering copyHostelBosToTos of HostelReqReportHelper");
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
		
		 log.info("Exiting copyHostelBosToTos of HostelReqReportHelper");
		 return hostelTOList;
	}
	/**
	 * converting the list of Bo's to TO's
	 * @param hostelReqReportDetailsBo
	 * @return
	 * @throws Exception
	 */
	public List<HostelReqReportTo> convertRoomBOtoTO(List<Object> hostelRoomTypeBo,HostelReqReportForm hostelReqReportForm) throws Exception{
		 log.info("inside convertBOtoTO of HostelReqReportHelper");
		 List<HostelReqReportTo> hostelRoomTypeToList = new ArrayList<HostelReqReportTo>();
		 HostelReqReportTo hostelRoomTypeTo = null;
		Iterator<Object> applicantIt = hostelRoomTypeBo.iterator();
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			hostelRoomTypeTo = new HostelReqReportTo();
			//0-application id
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				hostelRoomTypeTo.setRoomTypeName(object[0].toString());
			}
			//1-admnApplid
			if(object[1]!= null && !object[1].toString().isEmpty()){
				hostelRoomTypeTo.setNoOfRooms(Integer.valueOf(object[1].toString()));
			}
			hostelRoomTypeToList.add(hostelRoomTypeTo);
		}
		
		 log.info("Exiting convertBOtoTO of HostelReqReportHelper");
		return hostelRoomTypeToList;
	}
}
