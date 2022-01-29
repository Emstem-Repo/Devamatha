package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.kp.cms.to.hostel.HostelDailyReportTo;
import com.kp.cms.transactions.hostel.IHostelDailyReportTransactions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.transactionsimpl.hostel.HostelDailyReportTransactionImpl;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.forms.hostel.HostelDailyReportForm;
import com.kp.cms.forms.hostel.HostelReqReportForm;
import com.kp.cms.to.hostel.HostelReqReportTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelReqReportTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelReqReportTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelDailyReportHelper {
	
	private static Log log = LogFactory.getLog(HostelDailyReportHelper.class);
	private static volatile HostelDailyReportHelper hostelDailyReportHelper = null;
	IHostelDailyReportTransactions transaction = HostelDailyReportTransactionImpl.getInstance();
	
	private HostelDailyReportHelper() {
	}
	
	public static HostelDailyReportHelper getInstance() {
		if (hostelDailyReportHelper == null) {
			hostelDailyReportHelper = new HostelDailyReportHelper();
		}
		return hostelDailyReportHelper;
	}
	
	/**
	 * building the query based on input fields
	 * @param HostelReqReportForm
	 * @return
	 */
	public String getSearchCriteria(HostelDailyReportForm hostelDailyReportForm ) {
		
		log.info("Entering getSelectionSearchCriteria of HostelReqReportHelper");
		String statusCriteria = commonSearch(hostelDailyReportForm);
		String searchCriteria= "";
		searchCriteria = statusCriteria +" and h.isActive =1 " +"group by h.admAppln.personalData.firstName";
		log.info("Exiting getSelectionSearchCriteria of HostelReqReportHelper");
		return searchCriteria;
	}
	/**
	 * common query for all type of inputs
	 * @param HostelReqReportForm
	 * @return
	 */
	private String commonSearch(HostelDailyReportForm hostelDailyReportForm) {
		
		log.info("entered commonSearch of HostelReqReportHelper ");
		String searchCriteria = " select h.hlApplicationForm.isStaff," 
									+" s.id," 
									+" h.admAppln.personalData.firstName," 
									+" h.admAppln.personalData.middleName," 
									+" h.admAppln.personalData.lastName,"
									+" h.admAppln.personalData.mobileNo1," 
									+" h.admAppln.personalData.mobileNo2," 
									+" h.admAppln.personalData.mobileNo3," 
									+" s.classSchemewise.classes.name," 
									+" h.admAppln.personalData.guardianMob1," 
									+" h.admAppln.personalData.guardianMob2," 
									+" h.admAppln.personalData.guardianMob3," 
									+" h.admAppln.personalData.parentMob1," 
									+" h.admAppln.personalData.parentMob2," 
									+" h.admAppln.personalData.parentMob3," 
									+" h.hlRoom.name," 
									+" h.hlApplicationForm.employee.id," 
									+" h.hlApplicationForm.employee.firstName," 
									+" h.hlApplicationForm.employee.middleName," 
									+" h.hlApplicationForm.employee.lastName," 
									+" h.hlApplicationForm.employee.mobile," 
									+" h.hlApplicationForm.employee.emergencyMobile," 
									+" h.hlApplicationForm.employee.emergencyHomeTelephone," 
									+" h.hlApplicationForm.hlHostelByHlAppliedHostelId.name" 
									+" from HlRoomTransaction h" 
									+" inner join h.admAppln.students s" ;
								
								
		if (hostelDailyReportForm.getHostelId()!=null && !hostelDailyReportForm.getHostelId().isEmpty()) {
			String hostelqry = " where h.hlApplicationForm.hlHostelByHlAppliedHostelId="+"'"+ hostelDailyReportForm.getHostelId()+"'";
			searchCriteria = searchCriteria + hostelqry;
		}

		if (hostelDailyReportForm.getFromDate()!=null && hostelDailyReportForm.getFromDate().trim().length() > 0
				&&hostelDailyReportForm.getToDate()!=null && hostelDailyReportForm.getToDate().trim().length() > 0) {
			String dateQry = " and h.admAppln.id = s.admAppln.id" 
							   +" and h.hlApplicationForm.hlStatus.id=2"
							   +" and (h.txnDate)>("+"'"+ CommonUtil.ConvertStringToSQLDate(hostelDailyReportForm.getFromDate())+"'"+")"
							   +" and (h.txnDate)<("+"'"+ CommonUtil.ConvertStringToSQLDate(hostelDailyReportForm.getToDate())+"'"+")";
			searchCriteria = searchCriteria + dateQry;
		}
		log.info("exiting commonSearch of HostelReqReportHelper");
		return searchCriteria;
	}
	
	/**
	 * converting the list of Bo's to TO's
	 * @param hostelReqReportDetailsBo
	 * @return
	 * @throws Exception
	 */
	public List<HostelDailyReportTo> convertBOtoTO(List<Object> hostelDailyReportDetailsBo,HostelDailyReportForm hostelDailyReportForm) throws Exception{
		 log.info("inside convertBOtoTO of HostelReqReportHelper");
		 List<HostelDailyReportTo> hostelDailyReportToList = new ArrayList<HostelDailyReportTo>();
		 HostelDailyReportTo hostelDailyReportTo = null;
		Iterator<Object> applicantIt = hostelDailyReportDetailsBo.iterator();
		String status = "false";
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			hostelDailyReportTo = new HostelDailyReportTo();
			//0-application id
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				//status=object[0].toString()	;
				hostelDailyReportTo.setIsStaff(object[0].toString());
			}
			
			if(status.equalsIgnoreCase(object[0].toString())){
				
				if(object[1]!= null && !object[1].toString().isEmpty()){
					hostelDailyReportTo.setStId(object[1].toString());
				}
				if(object[2]!=null && object[3]!=null && object[4]!=null && !object[2].toString().isEmpty()&& !object[3].toString().isEmpty() && !object[4].toString().isEmpty()){
					hostelDailyReportTo.setStudName(object[2].toString()+" "+ object[3].toString()+" "+ object[4].toString());
				}else if(object[2]!=null && object[3]!=null  && !object[2].toString().isEmpty()&& !object[3].toString().isEmpty()){
					hostelDailyReportTo.setStudName(object[2].toString()+" "+ object[3].toString());
				}else if(object[2]!=null && !object[2].toString().isEmpty()){
					hostelDailyReportTo.setStudName(object[2].toString());
				}
				if(object[5]!=null && !object[5].toString().isEmpty()){
					hostelDailyReportTo.setStMobileNo(object[5].toString());
				}else if(object[6]!=null && !object[6].toString().isEmpty()){
					hostelDailyReportTo.setStMobileNo(object[6].toString());
				}else if(object[7]!=null && !object[7].toString().isEmpty()){
					hostelDailyReportTo.setStMobileNo(object[7].toString());
				}
				
				if(object[8]!=null && !object[8].toString().isEmpty()){
					hostelDailyReportTo.setClassName(object[8].toString());
				}
				
				if(object[9]!=null && !object[9].toString().isEmpty() ){
					hostelDailyReportTo.setGdMobileNo(object[9].toString());
				}else if(object[10]!=null && !object[10].toString().isEmpty()){
					hostelDailyReportTo.setGdMobileNo(object[10].toString());
				}else if(object[11]!=null && !object[11].toString().isEmpty()){
					hostelDailyReportTo.setGdMobileNo(object[11].toString());
				}
				
				if(object[12]!=null && !object[12].toString().isEmpty()){
					hostelDailyReportTo.setPmobileNo(object[12].toString());
				}else if(object[13]!=null && !object[13].toString().isEmpty()){
					hostelDailyReportTo.setPmobileNo(object[13].toString());
				}else if(object[14]!=null && !object[14].toString().isEmpty()){
					hostelDailyReportTo.setPmobileNo(object[14].toString());
				}
				
				if(object[15]!=null && !object[15].toString().isEmpty()){
					hostelDailyReportTo.setRoomName(object[15].toString());
				}
				if(object[23]!= null && !object[23].toString().isEmpty()){
					hostelDailyReportTo.setHostelName(object[23].toString());
				}
			}
					
			else{
				
				if(object[16]!=null && !object[16].toString().isEmpty()){
					hostelDailyReportTo.setEmpId(object[16].toString());
				}
					
				if(object[17]!=null && object[18]!=null && object[19]!=null && !object[17].toString().isEmpty()&& !object[18].toString().isEmpty() && !object[19].toString().isEmpty()){
					hostelDailyReportTo.setEmpName(object[17].toString()+" "+ object[18].toString()+" "+ object[19].toString());
				}else if(object[17]!=null && object[18]!=null  && !object[17].toString().isEmpty()&& !object[18].toString().isEmpty()){
					hostelDailyReportTo.setEmpName(object[17].toString()+" "+ object[18].toString());
				}else if(object[17]!=null && !object[17].toString().isEmpty()){
					hostelDailyReportTo.setEmpName(object[17].toString());
				}
				if(object[20]!= null && !object[20].toString().isEmpty()){
					hostelDailyReportTo.setEmobileNo(object[20].toString());
				}
				if(object[21]!= null && !object[21].toString().isEmpty()){
					hostelDailyReportTo.setEemergencyMobile(object[21].toString());
				}
				if(object[22]!= null && !object[22].toString().isEmpty()){
					hostelDailyReportTo.setEemergencyHomeTelephone(object[22].toString());
				}
				if(object[23]!= null && !object[23].toString().isEmpty()){
					hostelDailyReportTo.setHostelName(object[23].toString());
				}
				if(object[15]!=null && !object[15].toString().isEmpty()){
					hostelDailyReportTo.setRoomName(object[15].toString());
				}
			}
			hostelDailyReportToList.add(hostelDailyReportTo);
		}
		
		 log.info("Exiting convertBOtoTO of HostelReqReportHelper");
		return hostelDailyReportToList;
	}
	
	
	public List<HostelTO> copyHostelBosToTos(List<HlHostel> hostelList) {
		 log.info("Entering copyHostelBosToTos of HostelDailyReportHelper");
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
		
		 log.info("Exiting copyHostelBosToTos of HostelDailyReportHelper");
		 return hostelTOList;
	}

}
