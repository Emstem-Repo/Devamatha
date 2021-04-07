package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.kp.cms.to.hostel.HostelAbsentiesReportTo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.forms.hostel.HostelAbsentiesReportForm;
import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.forms.hostel.HostelDailyReportForm;
import com.kp.cms.to.hostel.HlGroupTo;
import com.kp.cms.to.hostel.HostelDailyReportTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.transactions.hostel.IHostelAbsentiesReportTransactions;
import com.kp.cms.transactions.hostel.IHostelDailyReportTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelAbsentiesReportTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelDailyReportTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelAbsentiesReportHelper {
	
	private static Log log = LogFactory.getLog(HostelAbsentiesReportHelper.class);
	private static volatile HostelAbsentiesReportHelper hostelAbsentiesReportHelper = null;
	IHostelAbsentiesReportTransactions transaction = HostelAbsentiesReportTransactionImpl.getInstance();
	
	private HostelAbsentiesReportHelper() {
	}
	
	public static HostelAbsentiesReportHelper getInstance() {
		if (hostelAbsentiesReportHelper == null) {
			hostelAbsentiesReportHelper = new HostelAbsentiesReportHelper();
		}
		return hostelAbsentiesReportHelper;
	}
	

	/**
	 * building the query based on input fields
	 * @param HostelReqReportForm
	 * @return
	 */
	public String getSearchCriteria(HostelAbsentiesReportForm hostelAbsentiesReportForm ) {
		
		log.info("Entering getSelectionSearchCriteria of HostelReqReportHelper");
		String statusCriteria = commonSearch(hostelAbsentiesReportForm);
		String searchCriteria= "";
		searchCriteria = statusCriteria +" and h.isActive =1 " +" group by h.admAppln.personalData.firstName,ha.hlGroupStudent.id,s.registerNo";
		log.info("Exiting getSelectionSearchCriteria of HostelReqReportHelper");
		return searchCriteria;
	}
	/**
	 * common query for all type of inputs
	 * @param HostelReqReportForm
	 * @return
	 */
	private String commonSearch(HostelAbsentiesReportForm hostelAbsentiesReportForm) {
		
		log.info("entered commonSearch of HostelReqReportHelper ");
		String searchCriteria = " select s.registerNo," 
									+" h.admAppln.personalData.firstName," 
									+" h.admAppln.personalData.middleName," 
									+" h.admAppln.personalData.lastName," 
									+" h.hlApplicationForm.hlHostelByHlAppliedHostelId.name," 
									+" s.classSchemewise.classes.name," 
									+" h.hlRoom.name," 
									+" ha.hlGroupStudent.id," 
									+" count(ha.hlGroupStudent.id)" 
									+" from HlRoomTransaction h" 
									+" inner join h.hlApplicationForm.hlHostelByHlAppliedHostelId.hlAttendances ha" 
									+" inner join h.admAppln.students s" ;
									
	
		if (hostelAbsentiesReportForm.getHostelId()!=null && !hostelAbsentiesReportForm.getHostelId().isEmpty()) {
			String hostelqry = " where h.hlApplicationForm.hlHostelByHlAppliedHostelId="+"'"+ hostelAbsentiesReportForm.getHostelId()+"'";
			searchCriteria = searchCriteria + hostelqry;
		}
		if (hostelAbsentiesReportForm.getHlGroupId()!=null && !hostelAbsentiesReportForm.getHlGroupId().isEmpty()) {
			String groupqry = " and ha.hlGroupStudent.hlGroup.id="+"'"+ hostelAbsentiesReportForm.getHlGroupId()+"'";
			searchCriteria = searchCriteria + groupqry;
		}
		if (hostelAbsentiesReportForm.getFromDate()!=null && hostelAbsentiesReportForm.getFromDate().trim().length() > 0
				&&hostelAbsentiesReportForm.getToDate()!=null && hostelAbsentiesReportForm.getToDate().trim().length() > 0) {
			String dateQry = " and h.admAppln.id = s.admAppln.id" 
							   +" and h.hlApplicationForm.hlHostelByHlAppliedHostelId.id = ha.hlHostel.id" 
							   +" and h.hlApplicationForm.hlStatus.id=2" 
							   +" and h.hlApplicationForm.isStaff = 0"
							   +" and (ha.attendanceDate)>("+"'"+ CommonUtil.ConvertStringToSQLDate(hostelAbsentiesReportForm.getFromDate())+"'"+")"
							   +" and (ha.attendanceDate)<("+"'"+ CommonUtil.ConvertStringToSQLDate(hostelAbsentiesReportForm.getToDate())+"'"+")";
			searchCriteria = searchCriteria + dateQry;
		}
		log.info("exiting commonSearch of HostelReqReportHelper");
		return searchCriteria;
	}
	
	public List<HostelTO> copyHostelBosToTos(List<HlHostel> hostelList) {
		 log.info("Entering copyHostelBosToTos of HostelAbsentiesReportHelper");
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
		 log.info("Exiting copyHostelBosToTos of HostelAbsentiesReportHelper");
		 return hostelTOList;
	}
	
	/**
	 * converting the list of Bo's to TO's
	 * @param hostelReqReportDetailsBo
	 * @return
	 * @throws Exception
	 */
	public List<HostelAbsentiesReportTo> convertBOtoTO(List<Object> hostelAbsentiesDetailsBo,HostelAbsentiesReportForm hostelAbsentiesReportForm) throws Exception{
		 log.info("inside convertBOtoTO of HostelReqReportHelper");
		 List<HostelAbsentiesReportTo> hostelAbsentiesToList = new ArrayList<HostelAbsentiesReportTo>();
		 HostelAbsentiesReportTo hostelAbsentiesReportTo = null;
		Iterator<Object> applicantIt = hostelAbsentiesDetailsBo.iterator();
	
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			hostelAbsentiesReportTo = new HostelAbsentiesReportTo();
			//0-application id
				if(object[0]!=null && !object[0].toString().isEmpty())
				{
					hostelAbsentiesReportTo.setRegNo(object[0].toString());
				}
				if(object[1]!=null && object[2]!=null && object[3]!=null && !object[1].toString().isEmpty()&& !object[2].toString().isEmpty() && !object[3].toString().isEmpty()){
					hostelAbsentiesReportTo.setStudName(object[1].toString()+" "+ object[2].toString()+" "+ object[3].toString());
				}else if(object[1]!=null && object[2]!=null  && !object[1].toString().isEmpty()&& !object[2].toString().isEmpty()){
					hostelAbsentiesReportTo.setStudName(object[1].toString()+" "+ object[2].toString());
				}else if(object[1]!=null && !object[1].toString().isEmpty()){
					hostelAbsentiesReportTo.setStudName(object[1].toString());
				}
				if(object[4]!=null && !object[4].toString().isEmpty()){
					hostelAbsentiesReportTo.setHostName(object[4].toString());
				}
				if(object[5]!=null && !object[5].toString().isEmpty()){
					hostelAbsentiesReportTo.setClassName(object[5].toString());
				}
				if(object[6]!=null && !object[6].toString().isEmpty()){
					hostelAbsentiesReportTo.setRoomNo(object[6].toString());
				}
				if(object[7]!=null && !object[7].toString().isEmpty()){
					hostelAbsentiesReportTo.setHlgId(object[7].toString());
				}
				if(object[8]!=null && !object[8].toString().isEmpty() ){
					hostelAbsentiesReportTo.setCount(Integer.parseInt(object[8].toString()));
				}
				/*if(object[9]!=null && !object[9].toString().isEmpty() ){
					hostelAbsentiesReportTo.setHlgName(object[9].toString());
				}*/
					hostelAbsentiesToList.add(hostelAbsentiesReportTo);
		}
		
		 log.info("Exiting convertBOtoTO of HostelReqReportHelper");
		return hostelAbsentiesToList;
	}
	public List<HlGroupTo> copyHlGroupBosToTos(List<HlGroup> hlGroupList) {
		 log.info("Entering copyHlGroupBosToTos of HostelAbsentiesReportHelper");
		List<HlGroupTo> hostelTOList = new ArrayList<HlGroupTo>();
		Iterator<HlGroup> iterator = hlGroupList.iterator();
		HlGroup hlGroup;
		HlGroupTo hlGroupTo = null;
		
		while (iterator.hasNext()) {
			
			hlGroupTo = new HlGroupTo();
			hlGroup = (HlGroup) iterator.next();
			if(hlGroup.getName()!=null && !hlGroup.getName().isEmpty()){
				hlGroupTo.setName(hlGroup.getName());
			}
			hlGroupTo.setId(hlGroup.getId());
			hostelTOList.add(hlGroupTo);
		}
		
		 log.info("Exiting copyHlGroupBosToTos of HostelAbsentiesReportHelper");
		 return hostelTOList;
	}

}
