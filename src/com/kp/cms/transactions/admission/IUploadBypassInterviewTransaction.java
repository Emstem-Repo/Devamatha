package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.to.admission.AdmApplnTO;

public interface IUploadBypassInterviewTransaction {
	
	public boolean addUploadedData(List<AdmApplnTO> interviewResult, String user) throws Exception;


}