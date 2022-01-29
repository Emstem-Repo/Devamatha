package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.forms.hostel.UploadTheOfflineApplicationDetailsToSystemForm;

public interface IUploadOfflineAppliDetailsTransaction {
	public Map<String,Integer> getRoomType(int hostelId)throws Exception;
	public boolean saveUploadOfflineApplication(Map<Integer,HostelOnlineApplication> boList,
			UploadTheOfflineApplicationDetailsToSystemForm objform)throws Exception;
	public	Map<String,Integer> getStudentIdByStudentRegNum(List<String> regNumList) throws Exception;

}
