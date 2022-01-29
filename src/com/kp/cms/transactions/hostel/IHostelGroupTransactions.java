package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.forms.hostel.HostelGroupForm;

public interface IHostelGroupTransactions {
	public List<HlApplicationForm> getStudentDeatils(String query) throws Exception;
	public boolean addHostelGroup(HlGroup hlGroup, String mode) throws Exception;	
	public List<HlGroup> getHostelGroup(String query) throws Exception;
	public HlGroup getHostelGroupById(int id) throws Exception;	
	public boolean deleteHostelGroup(int id, Boolean activate, HostelGroupForm hForm) throws Exception;
	public HlGroup isHostelGroupDuplcated(String groupName, int hostelId, int id) throws Exception;	
	public boolean isHostelGroupStudentsDuplcated(int appFormId, int id) throws Exception;
}
