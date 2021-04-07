package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.to.hostel.HostelStudentExtractTo;
import com.kp.cms.forms.hostel.HostelReqReportForm;
import com.kp.cms.forms.hostel.HostelStudentExtractForm;
import com.kp.cms.to.hostel.HostelReqReportTo;
import com.kp.cms.transactions.hostel.IHostelReqReportTransactions;
import com.kp.cms.transactions.hostel.IHostelStudentExtractTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelReqReportTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelStudentExtractTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelStudentExtractHelper {
	
	private static Log log = LogFactory.getLog(HostelStudentExtractHelper.class);
	private static volatile HostelStudentExtractHelper hostelStudentExtractHelper = null;
	IHostelStudentExtractTransactions transaction = HostelStudentExtractTransactionImpl.getInstance();
	
	private HostelStudentExtractHelper() {
	}
	
	public static HostelStudentExtractHelper getInstance() {
		if (hostelStudentExtractHelper == null) {
			hostelStudentExtractHelper = new HostelStudentExtractHelper();
		}
		return hostelStudentExtractHelper;
	}
	/**
	 * building the query based on input fields
	 * @param HostelReqReportForm
	 * @return
	 */
	public String getSearchCriteria(HostelStudentExtractForm hostelStudentExtractForm) {
		
		log.info("Entering getSelectionSearchCriteria of HostelReqReportHelper");
		String statusCriteria = commonSearch(hostelStudentExtractForm);
		String searchCriteria= "";
		searchCriteria = statusCriteria +" and h.isActive =1 " +" group by h.admAppln.personalData.firstName";
		log.info("Exiting getSelectionSearchCriteria of HostelReqReportHelper");
		return searchCriteria;
	}
	
	/**
	  * converting string to sqldate
	 * @param StudentInoutForm
	 * @param hostelApplicantDetails
	 * @throws Exception
	 */	 
	public static java.sql.Date ConvertStringToSQLDateTime(String dateString) {
		String formatDate="";
		if(dateString!=null & !dateString.isEmpty())
			formatDate=CommonUtil.ConvertStringToDateFormat(dateString, "dd/MM/yyyy hh:mm:ss", "MM/dd/yyyy hh:mm:ss");
		java.sql.Date sqldate = null;
		if(formatDate!=null & !formatDate.isEmpty()){
		Date date = new Date(formatDate);
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		sqldate = new java.sql.Date(cal.getTimeInMillis());
		}
		return sqldate;
	}
	/**
	 * common query for all type of inputs
	 * @param HostelReqReportForm
	 * @return
	 */
	private String commonSearch(HostelStudentExtractForm hostelStudentExtractForm) {
		
		log.info("entered commonSearch of HostelStudentExtractHelper ");
		String searchCriteria = " select h.hlRoom.name,"
							    +" h.admAppln.personalData.firstName," 
							    +" h.admAppln.personalData.middleName," 
								+" h.admAppln.personalData.lastName," 
								+" s.registerNo," 
								+" s.classSchemewise.classes.name," 
								+" ht.name," 
								+" DateDiff(hl.endDate,hl.startDate) " 
								+" from HlRoomTransaction h" 
								+" inner join h.hlApplicationForm.hlLeaves hl" 
								+" inner join h.admAppln.students s" 
								+" inner join hl.hlLeaveType ht" ;
		
		if (hostelStudentExtractForm.getFromDate()!=null && hostelStudentExtractForm.getFromDate().trim().length() > 0
				&&hostelStudentExtractForm.getToDate()!=null && hostelStudentExtractForm.getToDate().trim().length() > 0) {
			String extractQry = " where h.admAppln.id = s.admAppln.id" 
							   +" and h.hlApplicationForm.hlStatus.id=2"
							   +" and (hl.startDate)>("+"'"+ CommonUtil.ConvertStringToSQLDate(hostelStudentExtractForm.getFromDate())+"'"+")"
							   +" and (hl.endDate)<("+"'"+ CommonUtil.ConvertStringToSQLDate(hostelStudentExtractForm.getToDate())+"'"+")";
			searchCriteria = searchCriteria + extractQry;
		}
		/*if(hostelStudentExtractForm.getAppliedDate()!= null && hostelStudentExtractForm.getAppliedDate().trim().length() > 0){
			String appDate = " and h.appliedDate= "
							+"'"+ CommonUtil.ConvertStringToSQLDate(hostelStudentExtractForm.getAppliedDate())+"'";
			searchCriteria = searchCriteria + appDate;
		}*/
		log.info("exiting commonSearch of HostelStudentExtractHelper");
		return searchCriteria;
	}

	/**
	 * converting the list of Bo's to TO's
	 * @param hostelReqReportDetailsBo
	 * @return
	 * @throws Exception
	 */
	public List<HostelStudentExtractTo> convertBOtoTO(List<Object> hostelReqReportDetailsBo,HostelStudentExtractForm hostelStudentExtractForm) throws Exception{
		 log.info("inside convertBOtoTO of HostelStudentExtractHelper");
		 List<HostelStudentExtractTo> hostelStudentExtractToList = new ArrayList<HostelStudentExtractTo>();
		 HostelStudentExtractTo hostelStudentExtractTo = null;
		Iterator<Object> applicantIt = hostelReqReportDetailsBo.iterator();
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			hostelStudentExtractTo = new HostelStudentExtractTo();
			//0-application id
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				hostelStudentExtractTo.setRoomNo(object[0].toString());
			}
			
			//3,4,5 - student/staff name
			if(object[1]!=null && object[2]!=null && object[3]!=null && !object[1].toString().isEmpty()&& !object[2].toString().isEmpty() && !object[3].toString().isEmpty()){
				hostelStudentExtractTo.setApplicantName(object[1].toString()+" "+ object[2].toString()+" "+ object[3].toString());
			}else if(object[1]!=null && object[2]!=null  && !object[1].toString().isEmpty()&& !object[2].toString().isEmpty()){
				hostelStudentExtractTo.setApplicantName(object[1].toString()+" "+ object[2].toString());
			}else if(object[1]!=null && !object[1].toString().isEmpty()){
				hostelStudentExtractTo.setApplicantName(object[1].toString());
			}
		
			if(object[4]!=null && !object[4].toString().isEmpty())
			{
				hostelStudentExtractTo.setRegNo(object[4].toString());
			}
			
			if(object[5]!=null && !object[5].toString().isEmpty()){
				hostelStudentExtractTo.setClassName(object[5].toString());
			}
			
			if(object[6]!=null && !object[6].toString().isEmpty() ){
				hostelStudentExtractTo.setLeaveType(object[6].toString());
			}
			if(object[7]!=null && !object[7].toString().isEmpty()){
				hostelStudentExtractTo.setDiffDates(Integer.parseInt(object[7].toString()));
			}
			hostelStudentExtractToList.add(hostelStudentExtractTo);
		}
		
		 log.info("Exiting convertBOtoTO of HostelStudentExtractHelper");
		return hostelStudentExtractToList;
	}
	
}
