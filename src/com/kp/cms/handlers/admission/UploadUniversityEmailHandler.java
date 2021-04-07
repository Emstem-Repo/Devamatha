package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.transactions.admission.IUploadUniversityEmailTransaction;
import com.kp.cms.transactionsimpl.admission.UploadUniversityEmailTransactionimpl;

public class UploadUniversityEmailHandler {
	/**
	 * Singleton object of UploadUniversityEmailHandler
	 */
	private static volatile UploadUniversityEmailHandler uploadUniversityEmailHandler = null;
	private static final Log log = LogFactory.getLog(UploadUniversityEmailHandler.class);
	private UploadUniversityEmailHandler() {
		
	}
	/**
	 * return singleton object of UploadUniversityEmailHandler.
	 * @return
	 */
	public static UploadUniversityEmailHandler getInstance() {
		if (uploadUniversityEmailHandler == null) {
			uploadUniversityEmailHandler = new UploadUniversityEmailHandler();
		}
		return uploadUniversityEmailHandler;
	}
	/**
	 * returns the map with registerNo's and PersonalData BO after fetching from the database
	 * @return
	 * @throws Exception
	 */
	public PersonalData getStudentDetails(String registerNo) throws Exception {
		IUploadUniversityEmailTransaction txn=UploadUniversityEmailTransactionimpl.getInstance();
		PersonalData personalDetails=txn.getPersonalDetails(registerNo);
		return personalDetails;
	}
	/**
	 * passing the list of personalDataBO to impl for saving
	 * @param personalDataBOList
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(List<PersonalData> personalDataBOList,String user) throws Exception{
		boolean isAdded=false;
		IUploadUniversityEmailTransaction txn=UploadUniversityEmailTransactionimpl.getInstance();
		isAdded=txn.uploadUniversityEmail(personalDataBOList,user);
		return isAdded;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getPersonalDataMap() throws Exception{
		IUploadUniversityEmailTransaction transaction=UploadUniversityEmailTransactionimpl.getInstance();
		return transaction.getAllStudents();
	}
	
}
