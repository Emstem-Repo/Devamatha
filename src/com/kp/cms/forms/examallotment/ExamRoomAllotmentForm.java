package com.kp.cms.forms.examallotment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentTO;

public class ExamRoomAllotmentForm extends BaseActionForm {
	
	private String programType;
	private String examId;
	private String campus;
	private String cycleId;
	private Map<Integer, String> cycleMap;
	private String[] allotmentType;
	private List<HallTicketTo> studentList;
	private List<ExamRoomAllotmentTO> allotmentList;
	private Map<Integer, String> examNameList;
	private Map<Integer,String> workLocationMap;
	private String midOrEndSem;
	private String sessionTime;
	private List<Integer> allotedRooms;
	List<ExamRoomAllotmentDetailsTO> roomList;
	private String examSessionId;
	private String date;
	private String session;
	private String[] classes;
	private String[] selectedClasses;
	private String[] selectedRooms;
	private String[] rooms;
	private Map<Integer,String> classesMap = new HashMap<Integer, String>();
	private Map<Integer,String> sessionMap= new HashMap<Integer, String>();
	private Map<Integer,String> selectedClassesMap= new HashMap<Integer, String>();
	private Map<Integer,String> selectedRoomsMap= new HashMap<Integer, String>();
	private int remainingStudentsCount ;
	private int totalStudentsCount;
	private int totalRoomCount;
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		 ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields() {
		this.programType=null;
		this.cycleId=null;
		this.allotmentType=null;
		this.allotmentList=null;
		this.campus=null;
		this.examId=null;
		this.midOrEndSem=null;
		this.allotedRooms=null;
		this.examSessionId=null;
		this.date = null;
		this.session = null;
		this.classes = null;
		this.rooms = null;
		this.selectedClasses =null;
		this.selectedRooms = null;
		this.setClassesMap(null);
		this.selectedClassesMap = null;
		this.selectedRoomsMap = null;
		super.setCollectionMap(null);
		this.remainingStudentsCount = 0;
		this.totalRoomCount = 0;
		this.totalStudentsCount = 0;
		super.setErrorMessage(null);
	}

	
	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	

	public List<HallTicketTo> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<HallTicketTo> studentList) {
		this.studentList = studentList;
	}

	public String[] getAllotmentType() {
		return allotmentType;
	}

	public void setAllotmentType(String[] allotmentType) {
		this.allotmentType = allotmentType;
	}

	public List<ExamRoomAllotmentTO> getAllotmentList() {
		return allotmentList;
	}

	public void setAllotmentList(List<ExamRoomAllotmentTO> allotmentList) {
		this.allotmentList = allotmentList;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public Map<Integer, String> getCycleMap() {
		return cycleMap;
	}

	public void setCycleMap(Map<Integer, String> cycleMap) {
		this.cycleMap = cycleMap;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}

	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}

	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}

	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}

	public String getMidOrEndSem() {
		return midOrEndSem;
	}

	public void setMidOrEndSem(String midOrEndSem) {
		this.midOrEndSem = midOrEndSem;
	}

	public String getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}

	public List<Integer> getAllotedRooms() {
		return allotedRooms;
	}

	public void setAllotedRooms(List<Integer> allotedRooms) {
		this.allotedRooms = allotedRooms;
	}

	public List<ExamRoomAllotmentDetailsTO> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<ExamRoomAllotmentDetailsTO> roomList) {
		this.roomList = roomList;
	}

	public String getExamSessionId() {
		return examSessionId;
	}

	public void setExamSessionId(String examSessionId) {
		this.examSessionId = examSessionId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String[] getClasses() {
		return classes;
	}

	public void setClasses(String[] classes) {
		this.classes = classes;
	}

	public String[] getSelectedClasses() {
		return selectedClasses;
	}

	public void setSelectedClasses(String[] selectedClasses) {
		this.selectedClasses = selectedClasses;
	}

	public String[] getSelectedRooms() {
		return selectedRooms;
	}

	public void setSelectedRooms(String[] selectedRooms) {
		this.selectedRooms = selectedRooms;
	}

	public String[] getRooms() {
		return rooms;
	}

	public void setRooms(String[] rooms) {
		this.rooms = rooms;
	}

	public Map<Integer, String> getClassesMap() {
		return classesMap;
	}

	public void setClassesMap(Map<Integer, String> classesMap) {
		this.classesMap = classesMap;
	}

	public Map<Integer, String> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<Integer, String> sessionMap) {
		this.sessionMap = sessionMap;
	}

	public Map<Integer, String> getSelectedClassesMap() {
		return selectedClassesMap;
	}

	public void setSelectedClassesMap(Map<Integer, String> selectedClassesMap) {
		this.selectedClassesMap = selectedClassesMap;
	}

	public Map<Integer, String> getSelectedRoomsMap() {
		return selectedRoomsMap;
	}

	public void setSelectedRoomsMap(Map<Integer, String> selectedRoomsMap) {
		this.selectedRoomsMap = selectedRoomsMap;
	}

	public int getRemainingStudentsCount() {
		return remainingStudentsCount;
	}

	public void setRemainingStudentsCount(int remainingStudentsCount) {
		this.remainingStudentsCount = remainingStudentsCount;
	}

	public int getTotalStudentsCount() {
		return totalStudentsCount;
	}

	public void setTotalStudentsCount(int totalStudentsCount) {
		this.totalStudentsCount = totalStudentsCount;
	}

	public int getTotalRoomCount() {
		return totalRoomCount;
	}

	public void setTotalRoomCount(int totalRoomCount) {
		this.totalRoomCount = totalRoomCount;
	}
	
	
}
