package com.kp.cms.handler.alumini;

import java.util.HashMap;

import com.kp.cms.bo.alumini.AlumniRegistrationDetails;
import com.kp.cms.forms.alumini.AluminiRegistrationForm;
import com.kp.cms.helpers.alumini.AluminiRegistrationHelper;
import com.kp.cms.transactions.alumini.IAluminiRegistrationTransaction;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.alumini.AluminiRegistrationTxnImpl;

public class AluminiRegistrationHandler {

	private static volatile AluminiRegistrationHandler obj;
	
	private AluminiRegistrationHandler() {
		
	}
	
	public static AluminiRegistrationHandler getInstance() {
		if(obj == null) {
			obj = new AluminiRegistrationHandler();
		}
		return obj;
	}
	
	public boolean checkMobileNumberForDuplication(AluminiRegistrationForm aluminiRegistrationForm) throws Exception {
		IAluminiRegistrationTransaction tx = AluminiRegistrationTxnImpl.getInstance();
		return tx.isPhoneNumberDuplicate(aluminiRegistrationForm.getMobileNo2());
	}
	
	public boolean saveAluminiRegistrationDetails(AluminiRegistrationForm aluminiRegistrationForm) throws Exception {
		
		AluminiRegistrationHelper aluminiRegistrationHelper = AluminiRegistrationHelper.getInstance();
		AlumniRegistrationDetails alumniRegistrationDetails = aluminiRegistrationHelper.convertFormToBO(aluminiRegistrationForm);
		IAluminiRegistrationTransaction tx = AluminiRegistrationTxnImpl.getInstance();
		
		return tx.saveAlimniRegistartion(alumniRegistrationDetails);
	}
	
	public HashMap<Integer, String> getCoursesToForm() throws Exception {
		
		return CourseTransactionImpl.getInstance().getCourseMap();
	}
}
