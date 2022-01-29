package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;

public interface IUploadFinalMeritListTxn {
	public Map<String, Integer> getCourseByProgram();
	public boolean addFInalMeritUploaded(List<AdmAppln> applnList, String user,List<AdmapplnAdditionalInfo> admAdditionalList) throws Exception;
}
