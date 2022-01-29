package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.forms.hostel.ReadmissionSelectionUploadForm;

public interface IReadmissionSelectionUploadTransaction {
	public	Map<String,Integer> getStudentIdByStudentRegNum(List<String> regNumList) throws Exception;
	
	public boolean saveUploadOfflineApplication(Map<Integer,HostelOnlineApplication> boList,
			ReadmissionSelectionUploadForm objform,Map<String,Integer> registerNumMap)throws Exception;

}
