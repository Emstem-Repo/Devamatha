package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admission.AdmissionTcDetails;

public interface IAdmissionTcDetailsTransaction {

	public boolean uploadTcDetails(List<AdmissionTcDetails> tcDetailsList)throws Exception;

	public List<AdmissionTcDetails> getAdmTcDetailsList(StringBuffer query)throws Exception;

}
