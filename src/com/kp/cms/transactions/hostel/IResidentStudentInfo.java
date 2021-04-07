package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlAttendance;

public interface IResidentStudentInfo {
	//get list of candidates based on Name
	public List<HlApplicationForm> getListOfCandidates(String SearchCriteria) throws Exception;
	//get Student based on ID
	public HlApplicationForm getCandidatesById(String SearchCriteria) throws Exception;
	//getting attendance data for student
	public List<HlAttendance> getAttendanceList(int id) throws Exception;
	public HlApplicationForm getCandidatesByHlAppId(String hlappId) throws Exception;
}
