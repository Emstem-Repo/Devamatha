package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.HostelVisitorsInfoBo;
import com.kp.cms.forms.hostel.HostelVisitorsInfoForm;

public interface IHostelVisitorsInfoTransaction {
	String getHlAdmissionId(String year, String regNo)throws Exception;
	boolean addHostelVisitorsInfo(HostelVisitorsInfoBo hostelVisitorsInfoBo)throws Exception;
	List<HostelVisitorsInfoBo> getVisitorsList(String regNo)throws Exception;
	public HlAdmissionBo getRegisterNo(int id)throws Exception;
	public boolean deleteVisitorsInfo(int id)throws Exception;
	public HostelVisitorsInfoBo getVisitorsInfoById(int id)throws Exception;
	boolean updateHostelVisitorsInfo(HostelVisitorsInfoBo hostelVisitorsInfoBo)throws Exception;
}
