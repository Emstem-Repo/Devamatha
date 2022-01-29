package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.employee.EventLoactionBiometricDetailsBo;
import com.kp.cms.bo.employee.EventScheduleForAttendanceBo;
import com.kp.cms.bo.employee.EventScheduleStaffAttendanceBo;
import com.kp.cms.bo.employee.EventScheduleStudentAttendanceBo;
import com.kp.cms.forms.employee.EventScheduleForAttendanceForm;

public interface IEventScheduleForAttendanceTransaction {
	public Map<String,String> getEventLocationData()throws Exception;
	public boolean addStudentOrStaffData(EventScheduleForAttendanceBo bo,EventScheduleForAttendanceForm eventScheduleform)throws Exception;
	public int getPeriodIdByClassIdAndPeriodId(String periodId,Integer id)throws Exception;
	public int getClassIdByClassSchemewiseID(Integer classId)throws Exception;
	public List<EventScheduleForAttendanceBo> getstudentAndStaffData()throws Exception;
	public EventScheduleForAttendanceBo getEventSchAttDetailsById(int id)throws Exception;
	public int getPeriodNameByperiodId(int id)throws Exception;
	public boolean dupcheckingForStudentOrStaffData(EventScheduleForAttendanceBo bo,EventScheduleForAttendanceForm eventScheduleform)throws Exception;
	public boolean addOrUpdateStudentOrStaffData(EventScheduleForAttendanceBo bo,EventScheduleForAttendanceForm eventScheduleform)throws Exception;
	public boolean deleteEventScheduleStudentOrStaffDetails(int id,EventScheduleForAttendanceForm eventScheduleform)throws Exception;
	public EventScheduleStudentAttendanceBo getStudentData(int id)throws Exception;
	public EventScheduleStaffAttendanceBo getStaffData(int id)throws Exception;
	public EventScheduleStudentAttendanceBo getInActiveStudentId(int id)throws Exception;
	public EventScheduleStaffAttendanceBo getInActiveStaffData(int id)throws Exception;
}
