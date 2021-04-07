package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.forms.admission.AdmissionStatusForm;
import com.kp.cms.helpers.admission.AdmissionStatusHelper;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.transactions.admission.IAdmissionStatusTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionStatusTransactionImpl;


public class AdmissionStatusHandler {

	private static final Log log = LogFactory.getLog(AdmissionStatusHandler.class);
	public static volatile AdmissionStatusHandler admissionStatusHandler = null;

	private AdmissionStatusHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static AdmissionStatusHandler getInstance() {
		if (admissionStatusHandler == null) {
			admissionStatusHandler = new AdmissionStatusHandler();
		}
		return admissionStatusHandler;
	}
	
	/**
	 * 
	 * @param Passing application no. and gets the exactly matching records from AdmAppln table 
	 * @return
	 */
	public AdmissionStatusTO getInterviewResult(String applicationNo, int appliedYear) throws Exception
	{
		log.info("Entering into getInterviewResult of AdmissionStatusHandler");
		IAdmissionStatusTransaction admissionStatusTransaction=new AdmissionStatusTransactionImpl();
//		Calendar cal= Calendar.getInstance();
		AdmAppln admAppln=admissionStatusTransaction.getInterviewResult(applicationNo,appliedYear/*cal.get(Calendar.YEAR)*/);
		
		AdmissionStatusTO admissionStatusTO = AdmissionStatusHelper.getInstance().getInterviewStatus(admAppln);
		log.info("Leaving from getInterviewResult of AdmissionStatusHandler");
		return admissionStatusTO;
	}


	public List<InterviewCardTO> getStudentsList(String applicationNo ) throws Exception{
		log.info("Entering into getStudentsList of AdmissionStatusHandler");
		
		IAdmissionStatusTransaction admissionStatusTransaction=new AdmissionStatusTransactionImpl();
		List<InterviewCard> iCard = admissionStatusTransaction.getStudentsList(Integer.parseInt(applicationNo));
		log.info("Leaving into getStudentsList of AdmissionStatusHandler");
		
		return AdmissionStatusHelper.getInstance().getInterviewCardTO(iCard);
	}

	public List<InterviewCard> getStudentAdmitCardDetails(String applicationNo, String interviewTypeId) throws Exception{
		log.info("Entering into getStudentsList of AdmissionStatusHandler");
		
		IAdmissionStatusTransaction admissionStatusTransaction=new AdmissionStatusTransactionImpl();
		log.info("Leaving into getStudentsList of AdmissionStatusHandler");
		
		return admissionStatusTransaction.getStudentAdmitCardDetails(Integer.parseInt(applicationNo), Integer.parseInt(interviewTypeId));
	}

	public List<AdmissionStatusTO> getDetailsAdmAppln(String applicationNo, AdmissionStatusForm admissionStatusForm)throws Exception
	{
		log.info("Entering into getDetailsAdmAppln of AdmissionStatusHandler");
		IAdmissionStatusTransaction admissionStatusTransaction=new AdmissionStatusTransactionImpl();
		List<AdmAppln> newList=admissionStatusTransaction.getDetailsAdmAppln(applicationNo);
		log.info("Leaving into getDetailsAdmAppln of AdmissionStatusHandler");
		return AdmissionStatusHelper.getInstance().populateAdmApplnBOtoTO(newList,admissionStatusForm);
	}
	
	public AdmAppln getApplicantDetails(String applicationNo)throws Exception
	{
		log.info("Entering into getApplicantDetails of AdmissionStatusHandler");
		IAdmissionStatusTransaction admissionStatusTransaction=new AdmissionStatusTransactionImpl();
		List<AdmAppln> newList=admissionStatusTransaction.getDetailsAdmAppln(applicationNo);
		log.info("Leaving into getApplicantDetails of AdmissionStatusHandler");
		if(newList!= null && newList.size() > 0){
			return newList.get(0); 
		}
		else{
			return new AdmAppln();
		}
	}

	/**
	 * Checks if appln n dob exists in Appln Acknowledgement table
	 * @param applicationNo
	 * @param dateOfBirth
	 * @return
	 * @throws Exception
	 */
	public boolean checkApplnAvailableInAck(String applicationNo, String dateOfBirth) throws Exception {
		IAdmissionStatusTransaction txn=new AdmissionStatusTransactionImpl();
		boolean exists=false;
		exists=txn.getApplnAcknowledgement(applicationNo,dateOfBirth);
		return exists;
	}

	/**
	 * @param applicationNo
	 * @return
	 * @throws Exception
	 */
	public List<InterviewCardHistory> getStudentAdmitCardHistoryDetails( String applicationNo) throws Exception{
		log.info("Entering into getStudentAdmitCardHistoryDetails of AdmissionStatusHandler");
		
		IAdmissionStatusTransaction admissionStatusTransaction=new AdmissionStatusTransactionImpl();
		log.info("Leaving into getStudentAdmitCardHistoryDetails of AdmissionStatusHandler");
		
		return admissionStatusTransaction.getStudentAdmitCardHistoryDetails(Integer.parseInt(applicationNo));
	}
	
	// vinodha	
	public AdmissionStatusTO getApplicationDetailsForMemo(String applicationNo, AdmissionStatusForm admissionStatusForm)throws Exception
	{
		log.info("Entering into getDetailsAdmAppln of AdmissionStatusHandler");
		IAdmissionStatusTransaction admissionStatusTransaction=new AdmissionStatusTransactionImpl();
		AdmAppln newList=admissionStatusTransaction.getApplicationDetailsForMemo(admissionStatusForm);
		log.info("Leaving into getDetailsAdmAppln of AdmissionStatusHandler");
		return AdmissionStatusHelper.getInstance().populateAdmApplnBOtoTOForMemo(newList,admissionStatusForm);
	}
}