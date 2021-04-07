package com.kp.cms.forms.examallotment;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamRoomAllotmentGroupWiseTo;

public class ExamRoomAllotmentGroupWiseForm extends BaseActionForm {

	private int id;
	private String selectedCourses;
	private String[] unSelectesCourses;
	private String courseValue;
	private Map<Integer, String> courseMap;
	private String midOrEndSem;
	private Map<Integer, String> selectedCourseMap;
	private Boolean isDisable;
	private Map<String, Map<Integer, ExamRoomAllotmentGroupWiseTo>> groupWiseMap;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSelectedCourses() {
		return selectedCourses;
	}
	public void setSelectedCourses(String selectedCourses) {
		this.selectedCourses = selectedCourses;
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
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		 ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void reset(){
		this.courseValue=null;
		this.midOrEndSem=null;
		this.courseMap=null;
		this.selectedCourseMap=null;
		this.isDisable=false;
		this.groupWiseMap=null;
		this.selectedCourses=null;
		super.setSchemeNo(null);
	}
	public Map<String, Map<Integer, ExamRoomAllotmentGroupWiseTo>> getGroupWiseMap() {
		return groupWiseMap;
	}
	public void setGroupWiseMap(
			Map<String, Map<Integer, ExamRoomAllotmentGroupWiseTo>> groupWiseMap) {
		this.groupWiseMap = groupWiseMap;
	}
	
	
}
