package com.kp.cms.to.timetable;

import java.io.Serializable;
import java.util.Map;

public class TTSubjectBatchTo implements Serializable,Comparable<TTSubjectBatchTo> {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String subjectId;
	private String batchId;
	private String activityId;
	private String attendanceTypeId;
	private String periodName;
	private String subjectName;
	private String batchName;
	private String attendanceType;
	private Boolean isActive;
	private Map<Integer,Integer> userMap;
	private String[] userId;
	private String userName;
	private String roomNo;
	private Map<Integer,String> batchs;
	private String origSubjectId;
	private String origBatchId;
	private String origActivityId;
	private String origAttendanceTypeId;
	private String origAttendanceType;
	private String[] origUserId;
	private Boolean origIsActive;
	private String origRoomNo;
	private int deleteCount;
	private Map<Integer,String> activity;
	private String attTypeName;
	private String activityName;
	private Map<Integer,String> teachersMap;
	private String depId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getAttendanceTypeId() {
		return attendanceTypeId;
	}
	public void setAttendanceTypeId(String attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
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
	public String getAttendanceType() {
		return attendanceType;
	}
	public void setAttendanceType(String attendanceType) {
		this.attendanceType = attendanceType;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Map<Integer, Integer> getUserMap() {
		return userMap;
	}
	public void setUserMap(Map<Integer, Integer> userMap) {
		this.userMap = userMap;
	}
	public String[] getUserId() {
		return userId;
	}
	public void setUserId(String[] userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public Map<Integer, String> getBatchs() {
		return batchs;
	}
	public void setBatchs(Map<Integer, String> batchs) {
		this.batchs = batchs;
	}
	public int getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	public String getOrigSubjectId() {
		return origSubjectId;
	}
	public void setOrigSubjectId(String origSubjectId) {
		this.origSubjectId = origSubjectId;
	}
	public String getOrigBatchId() {
		return origBatchId;
	}
	public void setOrigBatchId(String origBatchId) {
		this.origBatchId = origBatchId;
	}
	public String getOrigAttendanceTypeId() {
		return origAttendanceTypeId;
	}
	public void setOrigAttendanceTypeId(String origAttendanceTypeId) {
		this.origAttendanceTypeId = origAttendanceTypeId;
	}
	public String getOrigAttendanceType() {
		return origAttendanceType;
	}
	public void setOrigAttendanceType(String origAttendanceType) {
		this.origAttendanceType = origAttendanceType;
	}
	public String[] getOrigUserId() {
		return origUserId;
	}
	public void setOrigUserId(String[] origUserId) {
		this.origUserId = origUserId;
	}
	public String getOrigRoomNo() {
		return origRoomNo;
	}
	public void setOrigRoomNo(String origRoomNo) {
		this.origRoomNo = origRoomNo;
	}
	public Boolean getOrigIsActive() {
		return origIsActive;
	}
	public void setOrigIsActive(Boolean origIsActive) {
		this.origIsActive = origIsActive;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getOrigActivityId() {
		return origActivityId;
	}
	public void setOrigActivityId(String origActivityId) {
		this.origActivityId = origActivityId;
	}
	public Map<Integer, String> getActivity() {
		return activity;
	}
	public void setActivity(Map<Integer, String> activity) {
		this.activity = activity;
	}
	public String getAttTypeName() {
		return attTypeName;
	}
	public void setAttTypeName(String attTypeName) {
		this.attTypeName = attTypeName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	@Override
	public int compareTo(TTSubjectBatchTo arg0) {

		if (arg0 != null && this != null) {
			if (this.getDeleteCount() > arg0.getDeleteCount())
				return 1;
			else if(this.getDeleteCount() < arg0.getDeleteCount())
				return -1;
			else
				return 0;
		}
		return 0;
	}
}