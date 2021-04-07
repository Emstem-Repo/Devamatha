package com.kp.cms.forms.attendance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AttendanceReEntryTo;

public class RemoveAttendanceForm extends BaseActionForm {
	
	private String regNo;
	private String[] subjects;
	Map<Date, AttendanceReEntryTo> attendanceReEntryTos;
	List<AttendanceReEntryTo> list;
	
	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	
	public String[] getSubjects() {
		return subjects;
	}

	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}
	public Map<Date, AttendanceReEntryTo> getAttendanceReEntryTos() {
		return attendanceReEntryTos;
	}

	public void setAttendanceReEntryTos(
			Map<Date, AttendanceReEntryTo> attendanceReEntryTos) {
		this.attendanceReEntryTos = attendanceReEntryTos;
	}

	public List<AttendanceReEntryTo> getList() {
		return list;
	}

	public void setList(List<AttendanceReEntryTo> list) {
		this.list = list;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * 
	 */
	public void resetFields() {
		this.regNo=null;
		this.subjects=null;
		super.setClassId(null);
		super.setYear(null);
		super.setClassSchemewiseId(null);
	}
	
}
