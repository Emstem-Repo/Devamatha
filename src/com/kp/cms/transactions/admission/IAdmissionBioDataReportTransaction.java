package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AdmBioDataCJC;

public interface IAdmissionBioDataReportTransaction {

	public List<AdmBioDataCJC> getAdmBioDataDetails(StringBuffer query)throws Exception;

}
