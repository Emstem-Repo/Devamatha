package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamRoomAllotmentSpecializationTo;

public class ExamRoomAllotmentSpecializationForm extends BaseActionForm{
	private int id;
	private String midEndSem;
	private String[] unselectedCourses;
	private String[] selectedCourses;
	private Map<Integer,String> courseMap;
	private Map<Integer,String> tempCourseMap;
	private List<ExamRoomAllotmentSpecializationTo> examRoomSpecializationTo;
	private Map<String,Map<Integer,ExamRoomAllotmentSpecializationTo>> midOrEndSemCoursesMap;
	private String courseList;
	private Map<Integer,String> selectedCourseMap;
	private String hide;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMidEndSem() {
		return midEndSem;
	}
	public void setMidEndSem(String midEndSem) {
		this.midEndSem = midEndSem;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public List<ExamRoomAllotmentSpecializationTo> getExamRoomSpecializationTo() {
		return examRoomSpecializationTo;
	}
	public void setExamRoomSpecializationTo(
			List<ExamRoomAllotmentSpecializationTo> examRoomSpecializationTo) {
		this.examRoomSpecializationTo = examRoomSpecializationTo;
	}
	
	public String[] getUnselectedCourses() {
		return unselectedCourses;
	}
	public void setUnselectedCourses(String[] unselectedCourses) {
		this.unselectedCourses = unselectedCourses;
	}
	public String[] getSelectedCourses() {
		return selectedCourses;
	}
	public void setSelectedCourses(String[] selectedCourses) {
		this.selectedCourses = selectedCourses;
	}
	public void reset() {
		super.setSchemeNo(null);
		this.midEndSem = null;
		this.courseMap = null;
		this.examRoomSpecializationTo = null;
		this.unselectedCourses = null;
		this.selectedCourses = null;
		this.midOrEndSemCoursesMap = null;
		this.selectedCourseMap = null;
		this.courseList = null;
		this.hide = "false";
	}
	public Map<String, Map<Integer, ExamRoomAllotmentSpecializationTo>> getMidOrEndSemCoursesMap() {
		return midOrEndSemCoursesMap;
	}
	public void setMidOrEndSemCoursesMap(
			Map<String, Map<Integer, ExamRoomAllotmentSpecializationTo>> midOrEndSemCoursesMap) {
		this.midOrEndSemCoursesMap = midOrEndSemCoursesMap;
	}
	public Map<Integer, String> getTempCourseMap() {
		return tempCourseMap;
	}
	public void setTempCourseMap(Map<Integer, String> tempCourseMap) {
		this.tempCourseMap = tempCourseMap;
	}
	public String getCourseList() {
		return courseList;
	}
	public void setCourseList(String courseList) {
		this.courseList = courseList;
	}
	public Map<Integer, String> getSelectedCourseMap() {
		return selectedCourseMap;
	}
	public void setSelectedCourseMap(Map<Integer, String> selectedCourseMap) {
		this.selectedCourseMap = selectedCourseMap;
	}
	public String getHide() {
		return hide;
	}
	public void setHide(String hide) {
		this.hide = hide;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
}
