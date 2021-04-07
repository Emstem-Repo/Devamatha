package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamRevaluationTO;
import com.kp.cms.to.exam.ExamSettingCourseTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamSettingCourseForm extends BaseActionForm {

	/**
	 * Used in Exam setting course
	 */

	private int id;

	private String revaluation;
	private String improvement;
	private String supplementaryForFailedSubject;

	private String minReqAttendanceWithoutFine;
	private String minReqAttendanceWithFine;

	private String aggregatePass;
	private String individualPass;

	private List<ExamSettingCourseTO> settingCourseList;
	private List<KeyValueTO> programTypeList;
	private List<ExamRevaluationTO> revaluationTypeList;

	private List<Integer> listCourses;
	private String[] selectedCourse;
	private String[] selectedCourseIdNos;
	private String[] revaluationType;
	private Map<Integer, String> coursesMap;
	private String college;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage() {
	super.clear();
		this.aggregatePass = null;
		this.improvement = "";
		this.individualPass = null;
		this.minReqAttendanceWithFine = null;
		this.minReqAttendanceWithoutFine = null;
		this.revaluation = null;
		this.supplementaryForFailedSubject = "";
		this.coursesMap= null;
	}

	public List<Integer> getListCourses() {
		return listCourses;
	}

	public void setListCourses(List<Integer> listCourses) {
		this.listCourses = listCourses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRevaluation() {
		return revaluation;
	}

	public void setRevaluation(String revaluation) {
		this.revaluation = revaluation;
	}

	public String getImprovement() {
		return improvement;
	}

	public void setImprovement(String improvement) {
		this.improvement = improvement;
	}

	public String getSupplementaryForFailedSubject() {
		return supplementaryForFailedSubject;
	}

	public void setSupplementaryForFailedSubject(
			String supplementaryForFailedSubject) {
		this.supplementaryForFailedSubject = supplementaryForFailedSubject;
	}

	public String getMinReqAttendanceWithoutFine() {
		return minReqAttendanceWithoutFine;
	}

	public void setMinReqAttendanceWithoutFine(
			String minReqAttendanceWithoutFine) {
		this.minReqAttendanceWithoutFine = minReqAttendanceWithoutFine;
	}

	public String getMinReqAttendanceWithFine() {
		return minReqAttendanceWithFine;
	}

	public void setMinReqAttendanceWithFine(String minReqAttendanceWithFine) {
		this.minReqAttendanceWithFine = minReqAttendanceWithFine;
	}

	public String getAggregatePass() {
		return aggregatePass;
	}

	public void setAggregatePass(String aggregatePass) {
		this.aggregatePass = aggregatePass;
	}

	public String getIndividualPass() {
		return individualPass;
	}

	public void setIndividualPass(String individualPass) {
		this.individualPass = individualPass;
	}

	public List<ExamSettingCourseTO> getSettingCourseList() {
		return settingCourseList;
	}

	public void setSettingCourseList(List<ExamSettingCourseTO> settingCourseList) {
		this.settingCourseList = settingCourseList;
	}

	public List<KeyValueTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<KeyValueTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String[] getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(String[] selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public String[] getSelectedCourseIdNos() {
		return selectedCourseIdNos;
	}

	public void setSelectedCourseIdNos(String[] selectedCourseIdNos) {
		this.selectedCourseIdNos = selectedCourseIdNos;
	}

	public void setRevaluationTypeList(
			List<ExamRevaluationTO> revaluationTypeList) {
		this.revaluationTypeList = revaluationTypeList;
	}

	public List<ExamRevaluationTO> getRevaluationTypeList() {
		return revaluationTypeList;
	}

	public void setRevaluationType(String[] revaluationType) {
		this.revaluationType = revaluationType;
	}

	public String[] getRevaluationType() {
		return revaluationType;
	}

	public void setCoursesMap(Map<Integer, String> coursesMap) {
		this.coursesMap = coursesMap;
	}

	public Map<Integer, String> getCoursesMap() {
		return coursesMap;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}
	
	

}
