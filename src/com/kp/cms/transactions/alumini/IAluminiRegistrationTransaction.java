package com.kp.cms.transactions.alumini;

import com.kp.cms.bo.alumini.AlumniRegistrationDetails;

public interface IAluminiRegistrationTransaction {

	public boolean saveAlimniRegistartion(AlumniRegistrationDetails alumniRegistrationDetails) throws Exception;
	public boolean isPhoneNumberDuplicate(String mobileNumber) throws Exception;
}
