package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlAttendance;
import com.kp.cms.bo.admin.HlGroupStudent;

public interface IAttendanceTransactions {
	public List<HlGroupStudent> getStudentDeatilsByGroupId(int groupId) throws Exception;
	public boolean addAttendance(List<HlAttendance>attBOList) throws Exception;
	public Boolean isAttendanceEntryDuplicated(String attDate, String groupId, String hostelId) throws Exception;
}
