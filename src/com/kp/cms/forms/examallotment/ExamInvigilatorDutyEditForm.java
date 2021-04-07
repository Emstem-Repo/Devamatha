package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyEditTo;

public class ExamInvigilatorDutyEditForm extends BaseActionForm{
	private Map<Integer,String> workLocationMap;
	private Map<Integer,String> examMap;
	private String examId;
	private String examExaminerType;
	private String examRoomNo;
	private String examFacultyId;
	private String examDate;
	private String examSession;
	private List<ExamInvigilatorDutyEditTo> list;
	private Map<Integer,String> roomNoMap;
	private Map<Integer,String> facultyMap;
	private int id;
	private String tmpDate;
	private String tmpSession;
	private String tmpFacultyId;
	private String tmpExaminerType;
	private String tmpRoomNoId;
	private List<ExamInvigilatorDutyEditTo> addMorelist;
	private boolean addMoreFlag;
	private String focus;
	private int countValue;
	private int addMoreListSize;
	private Map<Integer,String> examSessionMap;
	private String tempAcademicYear;
	
	
	
	public String getTempAcademicYear() {
		return tempAcademicYear;
	}
	public void setTempAcademicYear(String tempAcademicYear) {
		this.tempAcademicYear = tempAcademicYear;
	}
	public int getAddMoreListSize() {
		return addMoreListSize;
	}
	public void setAddMoreListSize(int addMoreListSize) {
		this.addMoreListSize = addMoreListSize;
	}
	public int getCountValue() {
		return countValue;
	}
	public void setCountValue(int countValue) {
		this.countValue = countValue;
	}
	public String getFocus() {
		return focus;
	}
	public void setFocus(String focus) {
		this.focus = focus;
	}
	public boolean isAddMoreFlag() {
		return addMoreFlag;
	}
	public void setAddMoreFlag(boolean addMoreFlag) {
		this.addMoreFlag = addMoreFlag;
	}
	public List<ExamInvigilatorDutyEditTo> getAddMorelist() {
		return addMorelist;
	}
	public void setAddMorelist(List<ExamInvigilatorDutyEditTo> addMorelist) {
		this.addMorelist = addMorelist;
	}
	public String getTmpDate() {
		return tmpDate;
	}
	public void setTmpDate(String tmpDate) {
		this.tmpDate = tmpDate;
	}
	public String getTmpSession() {
		return tmpSession;
	}
	public void setTmpSession(String tmpSession) {
		this.tmpSession = tmpSession;
	}
	public String getTmpFacultyId() {
		return tmpFacultyId;
	}
	public void setTmpFacultyId(String tmpFacultyId) {
		this.tmpFacultyId = tmpFacultyId;
	}
	public String getTmpExaminerType() {
		return tmpExaminerType;
	}
	public void setTmpExaminerType(String tmpExaminerType) {
		this.tmpExaminerType = tmpExaminerType;
	}
	public String getTmpRoomNoId() {
		return tmpRoomNoId;
	}
	public void setTmpRoomNoId(String tmpRoomNoId) {
		this.tmpRoomNoId = tmpRoomNoId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<Integer, String> getFacultyMap() {
		return facultyMap;
	}
	public void setFacultyMap(Map<Integer, String> facultyMap) {
		this.facultyMap = facultyMap;
	}
	public Map<Integer, String> getRoomNoMap() {
		return roomNoMap;
	}
	public void setRoomNoMap(Map<Integer, String> roomNoMap) {
		this.roomNoMap = roomNoMap;
	}
	public List<ExamInvigilatorDutyEditTo> getList() {
		return list;
	}
	public void setList(List<ExamInvigilatorDutyEditTo> list) {
		this.list = list;
	}
	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}
	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}
	public Map<Integer, String> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, String> examMap) {
		this.examMap = examMap;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public String getExamExaminerType() {
		return examExaminerType;
	}
	public void setExamExaminerType(String examExaminerType) {
		this.examExaminerType = examExaminerType;
	}
	public String getExamRoomNo() {
		return examRoomNo;
	}
	public void setExamRoomNo(String examRoomNo) {
		this.examRoomNo = examRoomNo;
	}
	public String getExamFacultyId() {
		return examFacultyId;
	}
	public void setExamFacultyId(String examFacultyId) {
		this.examFacultyId = examFacultyId;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	
	
	public String getExamSession() {
		return examSession;
	}
	public void setExamSession(String examSession) {
		this.examSession = examSession;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public Map<Integer, String> getExamSessionMap() {
		return examSessionMap;
	}
	public void setExamSessionMap(Map<Integer, String> examSessionMap) {
		this.examSessionMap = examSessionMap;
	}
}
