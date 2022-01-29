package com.kp.cms.to.timetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("serial")
public class TimeAllocationTo extends TimeTableModuleTo implements Serializable {

	private int day;
	private String dayName;
	private String[] periods;
	private Integer subjectId;
	private String subjectType;
	private Integer roomCodeId;
	private String[] classValues;
	private String[] batchValues;
	private String teachingStaffName;
	private Integer teachingStaffId;
	private String academicYear;
	private String className;
	private String subjectName;
	private String batchName;
	private String periodName;
	private String roomCode;
	private ArrayList<TimeAllocationTo> bottomGrid;
	private Map<Integer, String> classMap;
	private Map<Integer, String> subjectMap;
	private Map<Integer, String> batchMap;
	private Map<Integer, String> roomCodeList;
	private Map<Integer, String> periodsList;
	private Map<Integer, Integer> teachingStaffList;

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

	public Map<Integer, String> getBatchMap() {
		return batchMap;
	}

	public void setBatchMap(Map<Integer, String> batchMap) {
		this.batchMap = batchMap;
	}

	public Map<Integer, String> getRoomCodeList() {
		return roomCodeList;
	}

	public void setRoomCodeList(Map<Integer, String> roomCodeList) {
		this.roomCodeList = roomCodeList;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public int getDay() {
		return day;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getRoomCodeId() {
		return roomCodeId;
	}

	public void setRoomCodeId(Integer roomCodeId) {
		this.roomCodeId = roomCodeId;
	}

	public String[] getClassValues() {
		return classValues;
	}

	public void setClassValues(String[] classValues) {
		this.classValues = classValues;
	}

	public String[] getBatchValues() {
		return batchValues;
	}

	public void setBatchValues(String[] batchValues) {
		this.batchValues = batchValues;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String[] getPeriods() {
		return periods;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public Integer getTeachingStaffId() {
		return teachingStaffId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setTeachingStaffId(Integer teachingStaffId) {
		this.teachingStaffId = teachingStaffId;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public void setPeriods(String[] periods) {
		this.periods = periods;
	}

	public ArrayList<TimeAllocationTo> getBottomGrid() {
		return bottomGrid;
	}

	public void setBottomGrid(ArrayList<TimeAllocationTo> bottomGrid) {
		this.bottomGrid = bottomGrid;
	}

	public Map<Integer, String> getPeriodsList() {
		return periodsList;
	}

	public void setPeriodsList(Map<Integer, String> periodsList) {
		this.periodsList = periodsList;
	}

	public String getTeachingStaffName() {
		return teachingStaffName;
	}

	public void setTeachingStaffName(String teachingStaffName) {
		this.teachingStaffName = teachingStaffName;
	}

	public Map<Integer, Integer> getTeachingStaffList() {
		return teachingStaffList;
	}

	public void setTeachingStaffList(Map<Integer, Integer> teachingStaffList) {
		this.teachingStaffList = teachingStaffList;
	}

}
