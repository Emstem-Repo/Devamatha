package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.attendance.CocurricularAttendnaceEntryForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;

public interface ICocurricularAttendnaceEntryTransaction {

	public List<Student> getStudentList(String studentQuery) throws Exception;
	public Student getStudentByRegNo(String studentByRegnoQuery) throws Exception;
	public List<Object[]> getAttendnaceListByDates(String attendanceQuery) throws Exception;
	public List<Period> getPeriodListByClass(String classes) throws Exception;
	public Map<Integer, String> getCocurricularActivity() throws Exception;
	public boolean saveCocurricularLeaveDetails(List<StuCocurrLeave> cocurricularList, List<ApproveLeaveTO> approveLeaveTOs, CocurricularAttendnaceEntryForm cocurricularAttendnaceEntryForm) throws Exception;
	public List<Object[]> getDuplicateRecords(String attendanceDuplicateQuery)throws Exception;
	public Map<Integer, String> getSubjectMap()throws Exception;

	

}
