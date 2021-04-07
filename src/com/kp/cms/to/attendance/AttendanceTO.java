package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



public class AttendanceTO implements Serializable{
	
	private int id;
	private String type;
	private String periods;
	private String activity;
	private String subject;
	private String batch;
	private String teachers;
	private boolean checked;
	private String modifiedBy;
	private String lastModifiedDate; 
	private String acadamicYear;
	private String toDate;
	private String fromDate;
	private String className;
	private String dates;
	//subjectId int changed by String by mehaboob
	private String subjectId;
	private String day;	
	private String subjectName;
	
	private String classId="";
	private int attendanceTypeId;
	private int batchId;
	private int activityId;
	private int periodId;
	private Map<Integer, String> periodMap;
	//map<Integer,String> changed by Map<String,String>
	private Map<String, String> subjectMap;
	private Map<Integer, String> additionalUserMap;
	private Map<Integer,String> additionalPeriods;
	private String checked1;
	private List<Integer> batchList;
	private Boolean Error;
	private Map<Integer, Integer> subjectBatchMap;
	private String periodName;
	//raghu
	private String slipNo;
	private Boolean isTimeTable;
	private String time;
	private String timeHours;
	private String timeMins;
	private String sessionName;
	private String evenOrOdd;
	private int classSchemewiseId;
	
	private Boolean isAttendanceEntered;
	
	
	
	public String getSlipNo() {
		return slipNo;
	}
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	public Boolean getIsTimeTable() {
		return isTimeTable;
	}
	public void setIsTimeTable(Boolean isTimeTable) {
		this.isTimeTable = isTimeTable;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String string) {
		this.day = string; 
	}
	public String getAcadamicYear() {
		return acadamicYear;
	}
	public void setAcadamicYear(String acadamicYear) {
		this.acadamicYear = acadamicYear;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the periods
	 */
	public String getPeriods() {
		return periods;
	}
	/**
	 * @param periods the periods to set
	 */
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}
	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
	/**
	 * @return the batch
	 */
	public String getBatch() {
		return batch;
	}
	/**
	 * @param batch the batch to set
	 */
	public void setBatch(String batch) {
		this.batch = batch;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the teachers
	 */
	public String getTeachers() {
		return teachers;
	}
	/**
	 * @param teachers the teachers to set
	 */
	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public int getAttendanceTypeId() {
		return attendanceTypeId;
	}
	public void setAttendanceTypeId(int attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getPeriodId() {
		return periodId;
	}
	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}
	public Map<Integer, String> getPeriodMap() {
		return periodMap;
	}
	public void setPeriodMap(Map<Integer, String> periodMap) {
		this.periodMap = periodMap;
	}
	
	public Map<Integer, String> getAdditionalUserMap() {
		return additionalUserMap;
	}
	public void setAdditionalUserMap(Map<Integer, String> additionalUserMap) {
		this.additionalUserMap = additionalUserMap;
	}
	public Map<Integer, String> getAdditionalPeriods() {
		return additionalPeriods;
	}
	public void setAdditionalPeriods(Map<Integer, String> additionalPeriods) {
		this.additionalPeriods = additionalPeriods;
	}
	public String getChecked1() {
		return checked1;
	}
	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}
	public Map<String, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<String, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public List<Integer> getBatchList() {
		return batchList;
	}
	public void setBatchList(List<Integer> batchList) {
		this.batchList = batchList;
	}
	public Boolean getError() {
		return Error;
	}
	public void setError(Boolean error) {
		Error = error;
	}
	public Map<Integer, Integer> getSubjectBatchMap() {
		return subjectBatchMap;
	}
	public void setSubjectBatchMap(Map<Integer, Integer> subjectBatchMap) {
		this.subjectBatchMap = subjectBatchMap;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimeHours() {
		return timeHours;
	}
	public void setTimeHours(String timeHours) {
		this.timeHours = timeHours;
	}
	public String getTimeMins() {
		return timeMins;
	}
	public void setTimeMins(String timeMins) {
		this.timeMins = timeMins;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public String getEvenOrOdd() {
		return evenOrOdd;
	}
	public void setEvenOrOdd(String evenOrOdd) {
		this.evenOrOdd = evenOrOdd;
	}
	public int getClassSchemewiseId() {
		return classSchemewiseId;
	}
	public void setClassSchemewiseId(int classSchemewiseId) {
		this.classSchemewiseId = classSchemewiseId;
	}
	public Boolean getIsAttendanceEntered() {
		return isAttendanceEntered;
	}
	public void setIsAttendanceEntered(Boolean isAttendanceEntered) {
		this.isAttendanceEntered = isAttendanceEntered;
	}
	
}
