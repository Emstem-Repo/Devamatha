package com.kp.cms.transactions.sap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.sap.ExamScheduleDate;
import com.kp.cms.bo.sap.ExamScheduleUsers;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.forms.sap.ExamScheduleForm;

public interface IExamScheduleTransaction {
	public Map<Integer,String> getWorkLocation()throws Exception;
	public boolean saveVenueAndInvigilators(ExamScheduleDate bo)throws Exception;
	public List<ExamScheduleDate> getVenueAndInvigilatorsByMonthAndYear(ExamScheduleForm examScheduleForm)throws Exception;
	public ExamScheduleDate getVenueAndInvigilatorDetails(ExamScheduleForm examScheduleForm)throws Exception;
	public boolean deleteVenueAndInvigilatorDetails(ExamScheduleForm examScheduleForm)throws Exception;
	public boolean deleteVenue(int id)throws Exception;
	public Set<ExamScheduleVenue> getVenueList(ExamScheduleForm examScheduleForm)throws Exception;
	public boolean updateVenueAndInvigilators(ExamScheduleDate bo)throws Exception;
	public ExamScheduleDate validateSessionAndSessionorder(ExamScheduleForm examScheduleForm,String str)throws Exception;
	public ExamScheduleUsers getexamScheduleUsersData(int id)throws Exception;
	public ExamScheduleUsers getInActiveExamScheduleUsersData(int id)throws Exception;
	
	
}
