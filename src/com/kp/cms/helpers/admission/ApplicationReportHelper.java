package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.to.admission.ApplicationReportTO;
import com.kp.cms.transactions.admission.IApplicationReportTransaction;
import com.kp.cms.transactionsimpl.admission.ApplicationReportTransactionImpl;

/**
 * 
 * @author kshirod.k
 * A helper class for Application Report
 *
 *
 */


public class ApplicationReportHelper {

	private static final Log log = LogFactory.getLog(ApplicationReportHelper.class);

	public static volatile ApplicationReportHelper applicationReportHelper = null;

	private ApplicationReportHelper() {

	}

	IApplicationReportTransaction transaction = new ApplicationReportTransactionImpl();

	/**
	 * 
	 * @returns a single instance (Singleton)every time.
	 */

	public static ApplicationReportHelper getInstance() {
		if (applicationReportHelper == null) {
			applicationReportHelper = new ApplicationReportHelper();
			return applicationReportHelper;
		}
		return applicationReportHelper;
	}
	
	/**
	 * 
	 * @param issuedApplicationList
	 * @param receivedApplicationList
	 * @returns all the issued and received applications for a course and year 
	 */
	public List<ApplicationReportTO> populateBOtoTO(List<OfflineDetails> issuedApplicationList,	List<Student> receivedApplicationList) throws Exception{
		log.debug("Inside of ApplicationReportHelper populateBOtoTO");
		List<ApplicationReportTO> issuedAndReceivedApplicationsList = new ArrayList<ApplicationReportTO>();
		
		ApplicationReportTO applicationReportTO = null;
		Student student=null;
		OfflineDetails offlineDetails=null;
			
		Map<Integer, String> registerNoMap = new HashMap<Integer, String>();
		Map<Integer, String> rollNoMap = new HashMap<Integer, String>();
		/**
		 * Create a set and keep all the received applications (retrieved from AdmAppln table)in that		
		 */
		if(receivedApplicationList!=null && !receivedApplicationList.isEmpty()){
			Iterator<Student> iterator = receivedApplicationList.iterator();
			while (iterator.hasNext()) {
				student = iterator.next();
				String regdNo = "";
				String rollNo = "";
				if(student.getAdmAppln()!=null && student.getAdmAppln().getApplnNo()!=0){
				if(student.getRollNo()!=null){
					rollNo = student.getRollNo();	
				}
				if(student.getRegisterNo()!=null){
					regdNo = student.getRegisterNo();	
				}
				registerNoMap.put(student.getAdmAppln().getApplnNo(), regdNo);
				rollNoMap.put(student.getAdmAppln().getApplnNo(), rollNo);					
				}
			}
		}
		/**
		 * Get all the issued applications from OfflineDetails table and set those to TO
		 */
		if(issuedApplicationList!=null && !issuedApplicationList.isEmpty()){
			Iterator<OfflineDetails> iterator = issuedApplicationList.iterator();
			while (iterator.hasNext()) {
				offlineDetails = iterator.next();
				applicationReportTO=new ApplicationReportTO();
				applicationReportTO.setIssuedApplicationNo(offlineDetails.getApplnNo()!=0 ? offlineDetails.getApplnNo():0);
				//Checks if the issued application no. is present in the map then make flag "Yes" otherwise "No".
				if(registerNoMap.containsKey(offlineDetails.getApplnNo()) && rollNoMap.containsKey(offlineDetails.getApplnNo())){
					applicationReportTO.setFlag("Yes");
					applicationReportTO.setRegisterNo(registerNoMap.get(offlineDetails.getApplnNo()));
					applicationReportTO.setRollNo(rollNoMap.get(offlineDetails.getApplnNo()));
				}
				else{
					applicationReportTO.setFlag("No");
					applicationReportTO.setRegisterNo(registerNoMap.get(offlineDetails.getApplnNo()));
					applicationReportTO.setRollNo(rollNoMap.get(offlineDetails.getApplnNo()));
				}
				issuedAndReceivedApplicationsList.add(applicationReportTO);
			}
		}	
		log.debug("End of ApplicationReportHelper populateBOtoTO");
		return issuedAndReceivedApplicationsList;
	}
}
