package com.kp.cms.forms.examallotment;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;

public class ExamRoomAllotmentForCJCForm extends BaseActionForm {
	private int id;
	private String cycleId;
	private String allottedDate;
	private String sessionId;
	private Map<Integer,String> cycleMap;
	private Map<Integer,String> roomMap;
	private Map<Integer,String> campusMap;
	private Map<Integer,String> examMap;
	private Map<Integer,String> sessionMap;
	private String isDateWise;
	private String noOfStudents;
	private String fromRegNo;
	private String toRegNo;
	private Map<Integer,String> subjectMap;
	private List<RoomAllotmentStatusTo> roomAllotmentStatusTo;
	private String classId1;
	private String noOfStudents1;
	private String fromRegNo1;
	private String toRegNo1;
	private String noOfStudents2;
	private String fromRegNo2;
	private String toRegNo2;
	private Map<Integer,String> classMap1;
	private List<ExamRoomAllotmentDetailsTO> allotmentDetailsToList;
	private Map<String, RoomAllotmentStatusDetailsTo> allotStudentDetailsMap;
	private String subjectId1;
	private String noOfStudents3;
	private String fromRegNo3;
	private String toRegNo3;
	private Map<Integer,String> subjectMap1;
	private int examRoomAllotmentId;
	private int columnOneTotalSeats;
	private int columnTwoTotalSeats ;
	private int colOneAllotedSeats ;
	private int colTwoAllotedSeats;
	private int colOneAvailableSeats;
	private int colTwoAvailableSeats;
	private String tempField;
	private String roomName;
	private String campusName;
	private String dateName;
	private String sessionName;
	private String cycleName;
	private int oddClassOrSubjectCount;
	private int evenClassOrSubjectCount;
	private String allotAllCol;
	private Map<Integer,LinkedList<String>> lastRegNoMap ;
	private String tempAllotAllCol;
	private int tempTotalSeats;
	private int tempAllotedSeats;
	private int tempAvailableSeats;
	public List<ExamRoomAllotmentDetailsTO> getAllotmentDetailsToList() {
		return allotmentDetailsToList;
	}
	public void setAllotmentDetailsToList(
			List<ExamRoomAllotmentDetailsTO> allotmentDetailsToList) {
		this.allotmentDetailsToList = allotmentDetailsToList;
	}
	public Map<Integer, String> getClassMap1() {
		return classMap1;
	}
	public void setClassMap1(Map<Integer, String> classMap1) {
		this.classMap1 = classMap1;
	}
	public String getCycleId() {
		return cycleId;
	}
	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	public Map<Integer, String> getCycleMap() {
		return cycleMap;
	}
	public void setCycleMap(Map<Integer, String> cycleMap) {
		this.cycleMap = cycleMap;
	}
	public Map<Integer, String> getRoomMap() {
		return roomMap;
	}
	public void setRoomMap(Map<Integer, String> roomMap) {
		this.roomMap = roomMap;
	}
	public Map<Integer, String> getCampusMap() {
		return campusMap;
	}
	public void setCampusMap(Map<Integer, String> campusMap) {
		this.campusMap = campusMap;
	}
	public Map<Integer, String> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAllottedDate() {
		return allottedDate;
	}
	public void setAllottedDate(String allottedDate) {
		this.allottedDate = allottedDate;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Map<Integer, String> getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(Map<Integer, String> sessionMap) {
		this.sessionMap = sessionMap;
	}
	public String getNoOfStudents() {
		return noOfStudents;
	}
	public void setNoOfStudents(String noOfStudents) {
		this.noOfStudents = noOfStudents;
	}
	public String getFromRegNo() {
		return fromRegNo;
	}
	public void setFromRegNo(String fromRegNo) {
		this.fromRegNo = fromRegNo;
	}
	public String getToRegNo() {
		return toRegNo;
	}
	public void setToRegNo(String toRegNo) {
		this.toRegNo = toRegNo;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	public String getIsDateWise() {
		return isDateWise;
	}
	public void setIsDateWise(String isDateWise) {
		this.isDateWise = isDateWise;
	}
	public void reset() {
		this.id = 0;
		this.cycleId = null;
		this.allottedDate = null;
		this.sessionId = null;
		this.isDateWise = "No";
		this.noOfStudents = null;
		this.fromRegNo = null;
		this.toRegNo = null;
		this.setSubjectMap(null);
		this.setCycleMap(null);
		this.setExamMap(null);
		this.setCampusMap(null);
		this.setRoomMap(null);
		this.setSubjectMap(null);
		super.setSubjectId(null) ;
		super.setAcademicYear(null);
		super.setExamid(0);
		super.setCampusId(null);
		super.setRoomId(null);
		super.setExamType(null);
		super.setClassId(null);
		this.setRoomAllotmentStatusTo(null);
		this.classId1 = null;
		this.noOfStudents1= null;
		this.noOfStudents2 = null;
		this.fromRegNo1 = null;
		this.toRegNo2 = null;
		this.fromRegNo2 = null;
		this.toRegNo1 = null;
		this.classMap1 = null;
		super.setClassMap(null);
		this.allotmentDetailsToList = null;
		this.setAllotStudentDetailsMap(null);
		this.subjectId1 = null;
		this.subjectMap1 = null;
		this.noOfStudents3 = null;
		this.fromRegNo3 = null;
		this.toRegNo3 = null;
		this.examRoomAllotmentId = 0;
		this.colOneAllotedSeats = 0;
		this.colOneAvailableSeats = 0;
		this.colTwoAllotedSeats = 0;
		this.colTwoAvailableSeats = 0;
		this.columnOneTotalSeats = 0;
		this.columnTwoTotalSeats =0;
		this.tempField = null;
		this.roomName = null;
		this.dateName = null;
		this.campusName = null;
		this.sessionName = null;
		this.cycleName = null;
		super.setExamName(null);
		this.oddClassOrSubjectCount = 0;
		this.evenClassOrSubjectCount = 0;
		this.allotAllCol = "off";
		this.lastRegNoMap = null;
		this.tempTotalSeats = 0;
		this.tempAllotedSeats = 0;
		this.tempAvailableSeats = 0;
	}
	public String getClassId1() {
		return classId1;
	}
	public void setClassId1(String classId1) {
		this.classId1 = classId1;
	}
	public String getNoOfStudents1() {
		return noOfStudents1;
	}
	public void setNoOfStudents1(String noOfStudents1) {
		this.noOfStudents1 = noOfStudents1;
	}
	public String getFromRegNo1() {
		return fromRegNo1;
	}
	public void setFromRegNo1(String fromRegNo1) {
		this.fromRegNo1 = fromRegNo1;
	}
	public String getToRegNo1() {
		return toRegNo1;
	}
	public void setToRegNo1(String toRegNo1) {
		this.toRegNo1 = toRegNo1;
	}
	public String getNoOfStudents2() {
		return noOfStudents2;
	}
	public void setNoOfStudents2(String noOfStudents2) {
		this.noOfStudents2 = noOfStudents2;
	}
	public String getFromRegNo2() {
		return fromRegNo2;
	}
	public void setFromRegNo2(String fromRegNo2) {
		this.fromRegNo2 = fromRegNo2;
	}
	public String getToRegNo2() {
		return toRegNo2;
	}
	public void setToRegNo2(String toRegNo2) {
		this.toRegNo2 = toRegNo2;
	}
	public List<RoomAllotmentStatusTo> getRoomAllotmentStatusTo() {
		return roomAllotmentStatusTo;
	}
	public void setRoomAllotmentStatusTo(
			List<RoomAllotmentStatusTo> roomAllotmentStatusTo) {
		this.roomAllotmentStatusTo = roomAllotmentStatusTo;
	}
	public Map<String, RoomAllotmentStatusDetailsTo> getAllotStudentDetailsMap() {
		return allotStudentDetailsMap;
	}
	public void setAllotStudentDetailsMap(
			Map<String, RoomAllotmentStatusDetailsTo> allotStudentDetailsMap) {
		this.allotStudentDetailsMap = allotStudentDetailsMap;
	}
	public String getSubjectId1() {
		return subjectId1;
	}
	public void setSubjectId1(String subjectId1) {
		this.subjectId1 = subjectId1;
	}
	public String getNoOfStudents3() {
		return noOfStudents3;
	}
	public void setNoOfStudents3(String noOfStudents3) {
		this.noOfStudents3 = noOfStudents3;
	}
	public String getFromRegNo3() {
		return fromRegNo3;
	}
	public void setFromRegNo3(String fromRegNo3) {
		this.fromRegNo3 = fromRegNo3;
	}
	public String getToRegNo3() {
		return toRegNo3;
	}
	public void setToRegNo3(String toRegNo3) {
		this.toRegNo3 = toRegNo3;
	}
	public Map<Integer, String> getSubjectMap1() {
		return subjectMap1;
	}
	public void setSubjectMap1(Map<Integer, String> subjectMap1) {
		this.subjectMap1 = subjectMap1;
	}
	public int getExamRoomAllotmentId() {
		return examRoomAllotmentId;
	}
	public void setExamRoomAllotmentId(int examRoomAllotmentId) {
		this.examRoomAllotmentId = examRoomAllotmentId;
	}
	public int getColumnOneTotalSeats() {
		return columnOneTotalSeats;
	}
	public void setColumnOneTotalSeats(int columnOneTotalSeats) {
		this.columnOneTotalSeats = columnOneTotalSeats;
	}
	public int getColumnTwoTotalSeats() {
		return columnTwoTotalSeats;
	}
	public void setColumnTwoTotalSeats(int columnTwoTotalSeats) {
		this.columnTwoTotalSeats = columnTwoTotalSeats;
	}
	public int getColOneAllotedSeats() {
		return colOneAllotedSeats;
	}
	public void setColOneAllotedSeats(int colOneAllotedSeats) {
		this.colOneAllotedSeats = colOneAllotedSeats;
	}
	public int getColTwoAllotedSeats() {
		return colTwoAllotedSeats;
	}
	public void setColTwoAllotedSeats(int colTwoAllotedSeats) {
		this.colTwoAllotedSeats = colTwoAllotedSeats;
	}
	public int getColOneAvailableSeats() {
		return colOneAvailableSeats;
	}
	public void setColOneAvailableSeats(int colOneAvailableSeats) {
		this.colOneAvailableSeats = colOneAvailableSeats;
	}
	public int getColTwoAvailableSeats() {
		return colTwoAvailableSeats;
	}
	public void setColTwoAvailableSeats(int colTwoAvailableSeats) {
		this.colTwoAvailableSeats = colTwoAvailableSeats;
	}
	public String getTempField() {
		return tempField;
	}
	public void setTempField(String tempField) {
		this.tempField = tempField;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getDateName() {
		return dateName;
	}
	public void setDateName(String dateName) {
		this.dateName = dateName;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public String getCycleName() {
		return cycleName;
	}
	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}
	public int getOddClassOrSubjectCount() {
		return oddClassOrSubjectCount;
	}
	public void setOddClassOrSubjectCount(int oddClassOrSubjectCount) {
		this.oddClassOrSubjectCount = oddClassOrSubjectCount;
	}
	public int getEvenClassOrSubjectCount() {
		return evenClassOrSubjectCount;
	}
	public void setEvenClassOrSubjectCount(int evenClassOrSubjectCount) {
		this.evenClassOrSubjectCount = evenClassOrSubjectCount;
	}
	public String getAllotAllCol() {
		return allotAllCol;
	}
	public void setAllotAllCol(String allotAllCol) {
		this.allotAllCol = allotAllCol;
	}
	public Map<Integer, LinkedList<String>> getLastRegNoMap() {
		return lastRegNoMap;
	}
	public void setLastRegNoMap(Map<Integer, LinkedList<String>> lastRegNoMap) {
		this.lastRegNoMap = lastRegNoMap;
	}
	public String getTempAllotAllCol() {
		return tempAllotAllCol;
	}
	public void setTempAllotAllCol(String tempAllotAllCol) {
		this.tempAllotAllCol = tempAllotAllCol;
	}
	public int getTempTotalSeats() {
		return tempTotalSeats;
	}
	public void setTempTotalSeats(int tempTotalSeats) {
		this.tempTotalSeats = tempTotalSeats;
	}
	public int getTempAllotedSeats() {
		return tempAllotedSeats;
	}
	public void setTempAllotedSeats(int tempAllotedSeats) {
		this.tempAllotedSeats = tempAllotedSeats;
	}
	public int getTempAvailableSeats() {
		return tempAvailableSeats;
	}
	public void setTempAvailableSeats(int tempAvailableSeats) {
		this.tempAvailableSeats = tempAvailableSeats;
	}
}
