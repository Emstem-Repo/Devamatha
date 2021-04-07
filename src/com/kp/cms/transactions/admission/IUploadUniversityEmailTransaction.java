package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.PersonalData;

public interface IUploadUniversityEmailTransaction {

	boolean uploadUniversityEmail(List<PersonalData> personalDataBOList,String user) throws Exception;

	PersonalData getPersonalDetails(String registerNo) throws Exception;

	Map<String, Integer> getAllStudents() throws Exception;

}
