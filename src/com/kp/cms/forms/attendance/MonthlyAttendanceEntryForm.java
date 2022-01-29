package com.kp.cms.forms.attendance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.MonthlyAttendanceDaysTO;
import com.kp.cms.to.attendance.MonthlyAttendanceTO;

/**
 *
 *
 */
public class MonthlyAttendanceEntryForm extends BaseActionForm {

	private String[] classes;

	private String[] teachers;

	private String activityId;
	
	private String batchId;

	private Map<Integer, String> teachersMap;

	private Map<Integer, String> attendanceTypes;

	private Map<Integer, String> activityMap;

	private Map<Integer, String> classMap = new HashMap<Integer, String>();

	private Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
	
	private Map<Integer,String> batchMap = new HashMap<Integer, String>();

	private String classMandatry;

	private String subjectMandatory;

	private String periodMandatory;

	private String classSelectedIndex;

	private String teacherSelectedIndex;

	private String periodSelectedIndex;

	private String teacherMandatory;

	private String activityTypeMandatory;

	private String month;

	private String academicYear;
	
	private String classEntry;
	
	private String periodEntry;
	
	private String teacherEntry;
	
    private boolean regNoDisplay;;
	
	private Set<Integer> selectedClasses;
	
	private int noOfDaysInMonth;
	
	private Set<Integer> selectedTeacher;

	private List<MonthlyAttendanceTO> monthlyAttendanceTOList;
	
	private List<Integer> noOfDaysList;
	
	private String operationMode;
	
	private String attenType;
	private String acaYear;
	private String attendanceClass;
	private String batchName;
	private String attendanceSubject;
	private String batchMandatory;
	
	private List<MonthlyAttendanceDaysTO> daysList;
	private String year1;
	public String getAttenType() {
		return attenType;
	}

	public void setAttenType(String attenType) {
		this.attenType = attenType;
	}

	public String getAcaYear() {
		return acaYear;
	}

	public void setAcaYear(String acaYear) {
		this.acaYear = acaYear;
	}

	public String getAttendanceClass() {
		return attendanceClass;
	}

	public void setAttendanceClass(String attendanceClass) {
		this.attendanceClass = attendanceClass;
	}

	public String getAttendanceSubject() {
		return attendanceSubject;
	}

	public void setAttendanceSubject(String attendanceSubject) {
		this.attendanceSubject = attendanceSubject;
	}

	public String getAttendanceTeacher() {
		return attendanceTeacher;
	}

	public void setAttendanceTeacher(String attendanceTeacher) {
		this.attendanceTeacher = attendanceTeacher;
	}

	public String getAttendancePeriod() {
		return attendancePeriod;
	}

	public void setAttendancePeriod(String attendancePeriod) {
		this.attendancePeriod = attendancePeriod;
	}

	private String attendanceTeacher;
	private String attendancePeriod;

	public String[] getClasses() {
		return classes;
	}

	public void setClasses(String[] classes) {
		this.classes = classes;
	}

	public String[] getTeachers() {
		return teachers;
	}

	public void setTeachers(String[] teachers) {
		this.teachers = teachers;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}

	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}

	
	public Map<Integer, String> getAttendanceTypes() {
		return attendanceTypes;
	}

	public void setAttendanceTypes(Map<Integer, String> attendanceTypes) {
		this.attendanceTypes = attendanceTypes;
	}

	public Map<Integer, String> getActivityMap() {
		return activityMap;
	}

	public void setActivityMap(Map<Integer, String> activityMap) {
		this.activityMap = activityMap;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}

	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}

	public String getClassMandatry() {
		return classMandatry;
	}

	public void setClassMandatry(String classMandatry) {
		this.classMandatry = classMandatry;
	}

	public String getSubjectMandatory() {
		return subjectMandatory;
	}

	public void setSubjectMandatory(String subjectMandatory) {
		this.subjectMandatory = subjectMandatory;
	}

	public String getPeriodMandatory() {
		return periodMandatory;
	}

	public void setPeriodMandatory(String periodMandatory) {
		this.periodMandatory = periodMandatory;
	}

	public String getTeacherMandatory() {
		return teacherMandatory;
	}

	public void setTeacherMandatory(String teacherMandatory) {
		this.teacherMandatory = teacherMandatory;
	}

	public String getActivityTypeMandatory() {
		return activityTypeMandatory;
	}

	public void setActivityTypeMandatory(String activityTypeMandatory) {
		this.activityTypeMandatory = activityTypeMandatory;
	}

	public String getClassSelectedIndex() {
		return classSelectedIndex;
	}

	public void setClassSelectedIndex(String classSelectedIndex) {
		this.classSelectedIndex = classSelectedIndex;
	}

	public String getTeacherSelectedIndex() {
		return teacherSelectedIndex;
	}

	public void setTeacherSelectedIndex(String teacherSelectedIndex) {
		this.teacherSelectedIndex = teacherSelectedIndex;
	}

	public String getPeriodSelectedIndex() {
		return periodSelectedIndex;
	}

	public void setPeriodSelectedIndex(String periodSelectedIndex) {
		this.periodSelectedIndex = periodSelectedIndex;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public void resetEntries() {
		super.clear();
		this.classes = null;
		this.teachers = null;
		this.batchId = null;
		this.activityId = null;
		this.teachersMap = new HashMap<Integer, String>();
		this.attendanceTypes = new HashMap<Integer, String>();
		this.activityMap = new HashMap<Integer, String>();
		this.classMap = new HashMap<Integer, String>();
		this.subjectMap = new HashMap<Integer, String>();
		this.classMandatry = null;
		this.subjectMandatory = null;
		this.batchMap = new HashMap<Integer, String>();
		this.classSelectedIndex = null;
		this.teacherSelectedIndex = null;
		this.periodSelectedIndex = null;
		this.teacherMandatory = null;
		this.activityTypeMandatory = null;
		this.month = null;
		this.academicYear = null;

		classEntry = null;

		teacherEntry = null;

		selectedClasses = new HashSet<Integer>();

		noOfDaysInMonth = 0;

		selectedTeacher = new HashSet<Integer>();

		monthlyAttendanceTOList = null;

		noOfDaysList = null;

		attenType = null;
		acaYear = null;
		attendanceClass = null;
		attendanceSubject = null;
		super.attendanceTypeId = null;

	}

	
	/**
	 * @return the batchMandatory
	 */
	public String getBatchMandatory() {
		return batchMandatory;
	}


	/**
	 * @param batchMandatory the batchMandatory to set
	 */
	public void setBatchMandatory(String batchMandatory) {
		this.batchMandatory = batchMandatory;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.forms.BaseActionForm#validate(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		if (this.classSelectedIndex != null
				&& this.classSelectedIndex.equals("-1")) {
			this.classes = null;
		}
		if (this.teacherSelectedIndex != null
				&& this.teacherSelectedIndex.equals("-1")) {
			this.teachers = null;
		}
		
		if (this.classMandatry != null
				&& this.classMandatry.equalsIgnoreCase("yes")
				&& (this.classes == null || this.classes.length == 0)) {
			actionErrors.add("errors", new ActionError(
					CMSConstants.ATTENDANCE_CLASS_REQUIRED));
		}
		if (this.subjectMandatory != null
				&& this.subjectMandatory.equals("yes")
				&& (this.subjectId == null || this.subjectId.length() == 0)) {
			actionErrors.add("errors", new ActionError(
					CMSConstants.ATTENDANCE_SUBJECT_REQUIRED));
		}
		if (this.teacherMandatory != null
				&& this.teacherMandatory.equals("yes")
				&& (this.teachers == null || this.teachers.length == 0)) {
			
			actionErrors.add("errors", new ActionError(
					CMSConstants.ATTENDANCE_TEACHER_REQUIRED));
		}
		if(this.batchMandatory != null && this.batchMandatory.equals("yes") && (this.batchId == null || this.batchId.length() ==0 )) {
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_BATCH_REQUIRED));
		}

		if (this.activityTypeMandatory != null
				&& this.activityTypeMandatory.equals("yes")
				&& (this.activityId == null || this.activityId.length() == 0)) {
			actionErrors.add("errors", new ActionError(
					CMSConstants.ATTENDANCE_ACTIVITY_REQUIRED));
		}
		
		if(this.activityId !=null && this.activityId.length()!=0 && this.subjectId != null && this.subjectId.length() !=0) {
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_BOTHATTENSUB_NOTALLOWED));
		}
		/*
		if(this.getMonth() != null & !this.getMonth().equals("")) {
			Integer month = Integer.valueOf(this.getMonth()) + 1;
			Date date = CommonUtil.ConvertStringToDate("01/" + month + "/"
					+ this.getAcademicYear());

			Date curdate = new Date();
			if (date.compareTo(curdate) == 1) {
				actionErrors.add("errors", new ActionMessage(
						CMSConstants.FEE_NO_FUTURE_DATE_));
			}
		}
		*/
	
			
		return actionErrors;
	}
	
	/**
	 * @return the batchMap
	 */
	public Map<Integer, String> getBatchMap() {
		return batchMap;
	}


	/**
	 * @param batchMap the batchMap to set
	 */
	public void setBatchMap(Map<Integer, String> batchMap) {
		this.batchMap = batchMap;
	}

	public String getClassEntry() {
		return classEntry;
	}

	public void setClassEntry(String classEntry) {
		this.classEntry = classEntry;
	}

	public String getPeriodEntry() {
		return periodEntry;
	}

	public void setPeriodEntry(String periodEntry) {
		this.periodEntry = periodEntry;
	}

	public String getTeacherEntry() {
		return teacherEntry;
	}

	public void setTeacherEntry(String teacherEntry) {
		this.teacherEntry = teacherEntry;
	}

	

	public Set<Integer> getSelectedClasses() {
		return selectedClasses;
	}

	public void setSelectedClasses(Set<Integer> selectedClasses) {
		this.selectedClasses = selectedClasses;
	}

	public int getNoOfDaysInMonth() {
		return noOfDaysInMonth;
	}

	public void setNoOfDaysInMonth(int noOfDaysInMonth) {
		this.noOfDaysInMonth = noOfDaysInMonth;
	}

	public Set<Integer> getSelectedTeacher() {
		return selectedTeacher;
	}

	public void setSelectedTeacher(Set<Integer> selectedTeacher) {
		this.selectedTeacher = selectedTeacher;
	}

	public List<MonthlyAttendanceTO> getMonthlyAttendanceTOList() {
		return monthlyAttendanceTOList;
	}

	public void setMonthlyAttendanceTOList(
			List<MonthlyAttendanceTO> monthlyAttendanceTOList) {
		this.monthlyAttendanceTOList = monthlyAttendanceTOList;
	}

	public List<Integer> getNoOfDaysList() {
		return noOfDaysList;
	}

	public void setNoOfDaysList(List<Integer> noOfDaysList) {
		this.noOfDaysList = noOfDaysList;
	}

	public boolean isRegNoDisplay() {
		return regNoDisplay;
	}

	public void setRegNoDisplay(boolean regNoDisplay) {
		this.regNoDisplay = regNoDisplay;
	}

	public String getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public List<MonthlyAttendanceDaysTO> getDaysList() {
		return daysList;
	}

	public void setDaysList(List<MonthlyAttendanceDaysTO> daysList) {
		this.daysList = daysList;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getYear1() {
		return year1;
	}

	public void setYear1(String year1) {
		this.year1 = year1;
	}

	

}
