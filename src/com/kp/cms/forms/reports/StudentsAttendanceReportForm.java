package com.kp.cms.forms.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.reports.StudentsAttendanceReportTO;
import com.kp.cms.utilities.CommonUtil;

public class StudentsAttendanceReportForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String academicYear;
	private String[] attendanceType;
	private String startDate;
	private String endDate;
	private String courseName;	
	private Boolean leaveType;
	private String fromPercentage;
	private String toPercentage;
	private List<StudentsAttendanceReportTO> studentsAttendanceResult;
	private String isMonthlyAttendance;
	private Map<Integer, String> classMap = new HashMap<Integer, String>();
	private List<AttendanceTypeTO> attendanceTypeList;
	private String[] classesName;
	private Boolean coCurricularLeave;
	private String mode;
	private String downloadExcel;
	
	
	
	public String getIsMonthlyAttendance() {
		return isMonthlyAttendance;
	}
	public void setIsMonthlyAttendance(String isMonthlyAttendance) {
		this.isMonthlyAttendance = isMonthlyAttendance;
	}
	public List<StudentsAttendanceReportTO> getStudentsAttendanceResult() {
		return studentsAttendanceResult;
	}
	public void setStudentsAttendanceResult(
			List<StudentsAttendanceReportTO> studentsAttendanceResult) {
		this.studentsAttendanceResult = studentsAttendanceResult;
	}
	public String getFromPercentage() {
		return fromPercentage;
	}
	public void setFromPercentage(String fromPercentage) {
		this.fromPercentage = fromPercentage;
	}
	public String getToPercentage() {
		return toPercentage;
	}
	public void setToPercentage(String toPercentage) {
		this.toPercentage = toPercentage;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String[] getAttendanceType() {
		return attendanceType;
	}
	public void setAttendanceType(String[] attendanceType) {
		this.attendanceType = attendanceType;
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
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Boolean getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(Boolean leaveType) {
		this.leaveType = leaveType;
	}
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public List<AttendanceTypeTO> getAttendanceTypeList() {
		return attendanceTypeList;
	}
	public void setAttendanceTypeList(List<AttendanceTypeTO> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}
	public String[] getClassesName() {
		return classesName;
	}
	public void setClassesName(String[] classesName) {
		this.classesName = classesName;
	}
	public Boolean getCoCurricularLeave() {
		return coCurricularLeave;
	}
	public void setCoCurricularLeave(Boolean coCurricularLeave) {
		this.coCurricularLeave = coCurricularLeave;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void resetFields() {
		this.academicYear = null;
		this.attendanceType = null;		
		this.startDate = null;
		this.endDate = CommonUtil.getTodayDate();
		this.courseName = null;
		this.fromPercentage = null;
		this.toPercentage = null;
		this.leaveType = false;
		this.classesName = null;
		this.coCurricularLeave=true;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getDownloadExcel() {
		return downloadExcel;
	}
	public void setDownloadExcel(String downloadExcel) {
		this.downloadExcel = downloadExcel;
	}
}