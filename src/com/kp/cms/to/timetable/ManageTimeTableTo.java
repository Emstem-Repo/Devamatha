package com.kp.cms.to.timetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ManageTimeTableTo extends TimeTableModuleTo implements
		Serializable {

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
	private HashMap<String, String> subjectTypeMap;
	private Map<Integer, String> dayMap;
	private ArrayList<ManageTimeTableTo> bottomGrid;
	private Map<Integer, String> classMap;
	private Map<Integer, String> subjectMap;
	private Map<Integer, String> batchMap;
	private Map<Integer, String> roomCodeList;
	private Map<Integer, String> periodsList;
	private HashMap<Integer, String> teachingStaffList;

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

	public HashMap<String, String> getSubjectTypeMap() {
		return subjectTypeMap;
	}

	public void setSubjectTypeMap(HashMap<String, String> subjectTypeMap) {
		this.subjectTypeMap = subjectTypeMap;
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

	public ArrayList<ManageTimeTableTo> getBottomGrid() {
		return bottomGrid;
	}

	public void setBottomGrid(ArrayList<ManageTimeTableTo> bottomGrid) {
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

	public HashMap<Integer, String> getTeachingStaffList() {
		return teachingStaffList;
	}

	public void setTeachingStaffList(HashMap<Integer, String> teachingStaffList) {
		this.teachingStaffList = teachingStaffList;
	}

	public void setDayMap(Map<Integer, String> dayMap) {
		this.dayMap = dayMap;
	}

	public Map<Integer, String> getDayMap() {
		return dayMap;
	}

}
