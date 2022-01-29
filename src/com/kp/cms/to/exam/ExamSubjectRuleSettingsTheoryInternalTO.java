package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.to.attendance.AttendanceTypeTO;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsTheoryInternalTO  implements Serializable
{
	
	private  List<ExamSubjectRuleSettingsSubInternalTO> subInternalList;
	private String selectedAttendanceType;
	private  List<ExamSubjectRuleSettingsAssignmentTO> assignmentList;
	private ExamSubjectRuleSettingsAttendanceTO listAttendanceTO;
	private String selectTheBest;
	private String leaveAttendance;
	private String coCurricularAttendance;
	private String subInternalChecked;
	private String attendanceChecked;
	private String assignmentChecked;
	private String finalInternalMinimumMarks;
	private String finalEntryMaximumMarks;
	private String finalInternalMaximumMarks;
	private String totalMinimummumMarks;
	private String totalMaximumMarks;
	private String totalentryMaximumMarks;
	private boolean checkedActiveDummySubInt;
	private boolean checkedActiveDummyAttendance;
	private boolean checkedActiveDummyAssignment;
	List<AttendanceTypeTO> attendanceTypeList;
	private String attendanceTypeId;
	private String dupLeave;
	private String dupCoCurricular;
	private String dupsubInternalChecked;
	private String dupattendanceChecked;
	private String dupassignmentChecked;
	private int subjectRuleAttendanceId;
	
	public ExamSubjectRuleSettingsTheoryInternalTO() {
		super();
	}
	public ExamSubjectRuleSettingsTheoryInternalTO(String assignmentChecked,
			List<ExamSubjectRuleSettingsAssignmentTO> assignmentList, String attendanceChecked,
			String coCurricularAttendance, String finalEntryMaximumMarks,
			String finalInternalMaximumMarks, String finalInternalMinimumMarks,
			String leaveAttendance, String selectTheBest,
			String selectedAttendanceType, String subInternalChecked,
			List<ExamSubjectRuleSettingsSubInternalTO> subInternalList, String totalMaximumMarks,
			String totalMinimummumMarks, String totalentryMaximumMarks) {
		super();
		this.assignmentChecked = assignmentChecked;
		this.assignmentList = assignmentList;
		this.attendanceChecked = attendanceChecked;
		this.coCurricularAttendance = coCurricularAttendance;
		this.finalEntryMaximumMarks = finalEntryMaximumMarks;
		this.finalInternalMaximumMarks = finalInternalMaximumMarks;
		this.finalInternalMinimumMarks = finalInternalMinimumMarks;
		this.leaveAttendance = leaveAttendance;
		this.selectTheBest = selectTheBest;
		this.selectedAttendanceType = selectedAttendanceType;
		this.subInternalChecked = subInternalChecked;
		this.subInternalList = subInternalList;
		this.totalMaximumMarks = totalMaximumMarks;
		this.totalMinimummumMarks = totalMinimummumMarks;
		this.totalentryMaximumMarks = totalentryMaximumMarks;
	}
	public List<ExamSubjectRuleSettingsSubInternalTO> getSubInternalList() {
		return subInternalList;
	}
	public void setSubInternalList(List<ExamSubjectRuleSettingsSubInternalTO> subInternalList) {
		this.subInternalList = subInternalList;
	}
	public String getSelectedAttendanceType() {
		return selectedAttendanceType;
	}
	public void setSelectedAttendanceType(String selectedAttendanceType) {
		this.selectedAttendanceType = selectedAttendanceType;
	}
	public List<ExamSubjectRuleSettingsAssignmentTO> getAssignmentList() {
		return assignmentList;
	}
	public void setAssignmentList(List<ExamSubjectRuleSettingsAssignmentTO> assignmentList) {
		this.assignmentList = assignmentList;
	}
	public String getSelectTheBest() {
		return selectTheBest;
	}
	public void setSelectTheBest(String selectTheBest) {
		this.selectTheBest = selectTheBest;
	}
	public String getLeaveAttendance() {
		return leaveAttendance;
	}
	public void setLeaveAttendance(String leaveAttendance) {
		this.leaveAttendance = leaveAttendance;
	}
	public String getCoCurricularAttendance() {
		return coCurricularAttendance;
	}
	public void setCoCurricularAttendance(String coCurricularAttendance) {
		this.coCurricularAttendance = coCurricularAttendance;
	}
	public String getSubInternalChecked() {
		return subInternalChecked;
	}
	public void setSubInternalChecked(String subInternalChecked) {
		this.subInternalChecked = subInternalChecked;
	}
	public String getAttendanceChecked() {
		return attendanceChecked;
	}
	public void setAttendanceChecked(String attendanceChecked) {
		this.attendanceChecked = attendanceChecked;
	}
	public String getAssignmentChecked() {
		return assignmentChecked;
	}
	public void setAssignmentChecked(String assignmentChecked) {
		this.assignmentChecked = assignmentChecked;
	}
	public String getFinalInternalMinimumMarks() {
		return finalInternalMinimumMarks;
	}
	public void setFinalInternalMinimumMarks(String finalInternalMinimumMarks) {
		this.finalInternalMinimumMarks = finalInternalMinimumMarks;
	}
	public String getFinalEntryMaximumMarks() {
		return finalEntryMaximumMarks;
	}
	public void setFinalEntryMaximumMarks(String finalEntryMaximumMarks) {
		this.finalEntryMaximumMarks = finalEntryMaximumMarks;
	}
	public String getFinalInternalMaximumMarks() {
		return finalInternalMaximumMarks;
	}
	public void setFinalInternalMaximumMarks(String finalInternalMaximumMarks) {
		this.finalInternalMaximumMarks = finalInternalMaximumMarks;
	}
	public void setTotalMinimummumMarks(String totalMinimummumMarks) {
		this.totalMinimummumMarks = totalMinimummumMarks;
	}
	public String getTotalMinimummumMarks() {
		return totalMinimummumMarks;
	}
	public void setTotalMaximumMarks(String totalMaximumMarks) {
		this.totalMaximumMarks = totalMaximumMarks;
	}
	public String getTotalMaximumMarks() {
		return totalMaximumMarks;
	}
	public void setTotalentryMaximumMarks(String totalentryMaximumMarks) {
		this.totalentryMaximumMarks = totalentryMaximumMarks;
	}
	public String getTotalentryMaximumMarks() {
		return totalentryMaximumMarks;
	}
	public void setListAttendanceTO(ExamSubjectRuleSettingsAttendanceTO listAttendanceTO) {
		this.listAttendanceTO = listAttendanceTO;
	}
	public ExamSubjectRuleSettingsAttendanceTO getListAttendanceTO() {
		return listAttendanceTO;
	}
	public void setCheckedActiveDummySubInt(boolean checkedActiveDummySubInt) {
		this.checkedActiveDummySubInt = checkedActiveDummySubInt;
	}
	public boolean getCheckedActiveDummySubInt() {
		return checkedActiveDummySubInt;
	}
	public void setCheckedActiveDummyAttendance(boolean checkedActiveDummyAttendance) {
		this.checkedActiveDummyAttendance = checkedActiveDummyAttendance;
	}
	public boolean getCheckedActiveDummyAttendance() {
		return checkedActiveDummyAttendance;
	}
	public void setCheckedActiveDummyAssignment(boolean checkedActiveDummyAssignment) {
		this.checkedActiveDummyAssignment = checkedActiveDummyAssignment;
	}
	public boolean getCheckedActiveDummyAssignment() {
		return checkedActiveDummyAssignment;
	}
	public List<AttendanceTypeTO> getAttendanceTypeList() {
		return attendanceTypeList;
	}
	public void setAttendanceTypeList(List<AttendanceTypeTO> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}
	public String getAttendanceTypeId() {
		return attendanceTypeId;
	}
	public void setAttendanceTypeId(String attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}
	public String getDupLeave() {
		return dupLeave;
	}
	public void setDupLeave(String dupLeave) {
		this.dupLeave = dupLeave;
	}
	public String getDupCoCurricular() {
		return dupCoCurricular;
	}
	public void setDupCoCurricular(String dupCoCurricular) {
		this.dupCoCurricular = dupCoCurricular;
	}
	public String getDupsubInternalChecked() {
		return dupsubInternalChecked;
	}
	public void setDupsubInternalChecked(String dupsubInternalChecked) {
		this.dupsubInternalChecked = dupsubInternalChecked;
	}
	public String getDupattendanceChecked() {
		return dupattendanceChecked;
	}
	public void setDupattendanceChecked(String dupattendanceChecked) {
		this.dupattendanceChecked = dupattendanceChecked;
	}
	public String getDupassignmentChecked() {
		return dupassignmentChecked;
	}
	public void setDupassignmentChecked(String dupassignmentChecked) {
		this.dupassignmentChecked = dupassignmentChecked;
	}
	public int getSubjectRuleAttendanceId() {
		return subjectRuleAttendanceId;
	}
	public void setSubjectRuleAttendanceId(int subjectRuleAttendanceId) {
		this.subjectRuleAttendanceId = subjectRuleAttendanceId;
	}
}
