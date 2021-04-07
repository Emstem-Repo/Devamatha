package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.ManualAttendanceEntryTO;

public class ManualAttendanceEntryForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer,String>employeeList;
	private String startDate;
	private String endDate;
	private String employeeId;
	private String employeeName;
	private String attendanceDate;
	private String inTimeHrs="0";
	private String inTimeMins="0";
	private String outTimeHrs="0";
	private String outTimeMins="0";
	private String dummyAttendanceDate;
	private Integer id;
	private List<ManualAttendanceEntryTO> employeesToMarkAttendanceList;
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void clear() {
		super.clear();
		startDate=null;
		endDate=null;
		employeeId=null;
		employeeName=null;
		attendanceDate=null;
		inTimeHrs="0";
		inTimeMins="0";
		outTimeHrs="0";
		outTimeMins="0";
		id=null;
		dummyAttendanceDate=null;
	}
	public Map<Integer, String> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(Map<Integer, String> employeeList) {
		this.employeeList = employeeList;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public List<ManualAttendanceEntryTO> getEmployeesToMarkAttendanceList() {
		return employeesToMarkAttendanceList;
	}

	public void setEmployeesToMarkAttendanceList(
			List<ManualAttendanceEntryTO> employeesToMarkAttendanceList) {
		this.employeesToMarkAttendanceList = employeesToMarkAttendanceList;
	}

	public String getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getInTimeHrs() {
		return inTimeHrs;
	}

	public void setInTimeHrs(String inTimeHrs) {
		this.inTimeHrs = inTimeHrs;
	}

	public String getInTimeMins() {
		return inTimeMins;
	}

	public void setInTimeMins(String inTimeMins) {
		this.inTimeMins = inTimeMins;
	}

	public String getOutTimeHrs() {
		return outTimeHrs;
	}

	public void setOutTimeHrs(String outTimeHrs) {
		this.outTimeHrs = outTimeHrs;
	}

	public String getOutTimeMins() {
		return outTimeMins;
	}

	public void setOutTimeMins(String outTimeMins) {
		this.outTimeMins = outTimeMins;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDummyAttendanceDate() {
		return dummyAttendanceDate;
	}

	public void setDummyAttendanceDate(String dummyAttendanceDate) {
		this.dummyAttendanceDate = dummyAttendanceDate;
	}
	
	
}