package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamRoomAllotSettingPoolWiseTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentPoolTo;

public class ExamRoomAllotmentPoolForm extends BaseActionForm {
	
	private int id;
	private String poolName;
	private List<ExamRoomAllotmentPoolTo> allotmentPoolToList;
	private String selectedCourses;
	private String[] unSelectesCourses;
	private String courseValue;
	private Map<Integer, String> examAllotRoomPoolMap;
	private Map<Integer, String> courseMap;
	private String midOrEndSem;
	private String semesterNo;
	private Map<String, Map<Integer, Map<String, ExamRoomAllotSettingPoolWiseTO>>> midOrEndMap;
	private Map<Integer, String> selectedCourseMap;
	private Boolean isDisable;
	
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		 ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields(){
		this.poolName=null;
		this.unSelectesCourses=null;
		this.selectedCourses=null;
		this.examAllotRoomPoolMap=null;
		this.courseMap=null;
		this.allotmentPoolToList=null;
		this.semesterNo=null;
		this.midOrEndSem=null;
		this.midOrEndMap=null;
		this.courseValue=null;
		this.selectedCourseMap=null;
		this.isDisable=false;
		
	}

	public List<ExamRoomAllotmentPoolTo> getAllotmentPoolToList() {
		return allotmentPoolToList;
	}

	public void setAllotmentPoolToList(
			List<ExamRoomAllotmentPoolTo> allotmentPoolToList) {
		this.allotmentPoolToList = allotmentPoolToList;
	}

	public Map<Integer, String> getExamAllotRoomPoolMap() {
		return examAllotRoomPoolMap;
	}

	public void setExamAllotRoomPoolMap(Map<Integer, String> examAllotRoomPoolMap) {
		this.examAllotRoomPoolMap = examAllotRoomPoolMap;
	}

	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}

	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}

	public String getMidOrEndSem() {
		return midOrEndSem;
	}

	public void setMidOrEndSem(String midOrEndSem) {
		this.midOrEndSem = midOrEndSem;
	}

	public String[] getUnSelectesCourses() {
		return unSelectesCourses;
	}

	public void setUnSelectesCourses(String[] unSelectesCourses) {
		this.unSelectesCourses = unSelectesCourses;
	}

	public String getCourseValue() {
		return courseValue;
	}

	public void setCourseValue(String courseValue) {
		this.courseValue = courseValue;
	}

	public String getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(String selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public String getSemesterNo() {
		return semesterNo;
	}

	public void setSemesterNo(String semesterNo) {
		this.semesterNo = semesterNo;
	}

	public Map<String, Map<Integer, Map<String, ExamRoomAllotSettingPoolWiseTO>>> getMidOrEndMap() {
		return midOrEndMap;
	}

	public void setMidOrEndMap(
			Map<String, Map<Integer, Map<String, ExamRoomAllotSettingPoolWiseTO>>> midOrEndMap) {
		this.midOrEndMap = midOrEndMap;
	}

	public Map<Integer, String> getSelectedCourseMap() {
		return selectedCourseMap;
	}

	public void setSelectedCourseMap(Map<Integer, String> selectedCourseMap) {
		this.selectedCourseMap = selectedCourseMap;
	}

	public Boolean getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}

}
