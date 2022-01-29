package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.forms.hostel.HostelLeaveForm;

public interface IHostelLeaveTransaction {

	
	public List<HlLeave> getLeaveTypeList() throws Exception;
	public boolean saveOrUpdateHostelLeaveDetails(HlLeave leaveType,String mode) throws Exception;
	public boolean duplicateHostelLeaveCheck(String query,HostelLeaveForm hostelLeaveForm) throws Exception;
	public boolean verifyRegisterNo(HostelLeaveForm hostelLeaveForm) throws Exception;
	public List<HlLeave> getStudentLeaveDetails(HostelLeaveForm  leaveForm) throws Exception;
	public HlLeave editStudentLeaveDetails(int id) throws Exception;
	public HlLeave getHostelLeaveById(String id) throws Exception;
	HlUnits getStudentHostelDetails(String studentId, HostelLeaveForm hostelLeaveForm) throws Exception;
	public boolean saveStudentLeave(HlLeave leave) throws Exception;
	public List<HlLeave> getDataForQuery(String query) throws Exception;
	public List<HlLeave> getTotalLeaves(String studentId) throws Exception;
	public boolean cancelLeave(int hlLeaveId) throws Exception;
	public boolean checkStudentDetails(int studentId) throws Exception;
	public List<HlLeave> getPreviousList(String regNo)throws Exception;
	public boolean checkIsHoliday(String holiday)throws Exception;
	
}
