package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.to.admission.PersonalDataTO;

public interface IUploadSecondLanguageTransaction {
	//getting the personal Data id and register for that course and year
	Map<String, Integer> getAppDetails(String query) throws Exception;
	//updating the second language for particular Record
	boolean updatePersonalData(List<PersonalDataTO> results, String user) throws Exception;
	Map<String, String> getSeondLanguages() throws Exception;

}
