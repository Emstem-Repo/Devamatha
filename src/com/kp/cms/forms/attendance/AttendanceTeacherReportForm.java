package com.kp.cms.forms.attendance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.attendance.StudentAbsentDetailsTO;

public class AttendanceTeacherReportForm extends BaseActionForm{
	private String previousStartDate;
	private String previousEndDate;
	private String startDate;
	private String endDate;
	private String[] teacherType;
	private Map<Integer,String> teachersMap;
	List<PeriodTO> periodList;
	private String userName;
	private int attendanceId;
	private List<StudentAbsentDetailsTO> studentAbsentList;
	private String periods;
	private String attendanceDate;
	private String periodName;
	private String classSchemes;
	private String classes;
	private String batchName;
	public String[] getTeacherType() {
		return teacherType;
	}
	public void setTeacherType(String[] teacherType) {
		this.teacherType = teacherType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
	public void resetFields() {
		// TODO Auto-generated method stub
		this.startDate = null;
		this.endDate = null;
		this.teacherType = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<PeriodTO> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<PeriodTO> periodList) {
		this.periodList = periodList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAttendanceId() {
		return attendanceId;
	}
	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}
	public List<StudentAbsentDetailsTO> getStudentAbsentList() {
		return studentAbsentList;
	}
	public void setStudentAbsentList(List<StudentAbsentDetailsTO> studentAbsentList) {
		this.studentAbsentList = studentAbsentList;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setClassSchemes(String classSchemes) {
		this.classSchemes = classSchemes;
	}
	public String getClassSchemes() {
		return classSchemes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getClasses() {
		return classes;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getPreviousStartDate() {
		return previousStartDate;
	}
	public void setPreviousStartDate(String previousStartDate) {
		this.previousStartDate = previousStartDate;
	}
	public String getPreviousEndDate() {
		return previousEndDate;
	}
	public void setPreviousEndDate(String previousEndDate) {
		this.previousEndDate = previousEndDate;
	}

	
}
