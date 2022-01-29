package com.kp.cms.transactions.attandance;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.StuCocurrLeave;

public interface ICocurricularLeaveApproveTransaction {

	public boolean approveCocurricularApplicationsByTeacher(List<StuCocurrLeave> cocurrLeavesBoList) throws Exception;
	public boolean cancelCocurricularApplicationsByTeacher(List<StuCocurrLeave> cocurrLeavesBoList) throws Exception;
	public Map<Integer, String> getAssignedActivityMap(String userId) throws Exception;
	public List<StuCocurrLeave> getCocurricularApplications(String activityIdForQuery, Date fromDate, Date toDate) throws Exception;
	public List<StuCocurrLeave> searchCocurricularApplications(String activityIdForQuery, Date fromDate, Date toDate,String searchType, String userId) throws Exception;

}
