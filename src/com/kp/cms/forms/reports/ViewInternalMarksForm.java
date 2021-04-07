package com.kp.cms.forms.reports;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;

public class ViewInternalMarksForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String classId;
	private String subjectId;
	private String teachers;
	private Map<Integer, String> classMap;
	private List<TeacherClassEntryTo> listTeacherClassEntry;
	private Map<Integer,String> subjectMap; 
	private List<ExamMarksEntryDetailsTO> listCourseDetails;
	private String year;
	private String userId;
	private List<String> examNames;
	
	public void reset(){
		subjectId=null;
		classId=null;
		year=null;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTeachers() {
		return teachers;
	}
	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public List<TeacherClassEntryTo> getListTeacherClassEntry() {
		return listTeacherClassEntry;
	}
	public void setListTeacherClassEntry(
			List<TeacherClassEntryTo> listTeacherClassEntry) {
		this.listTeacherClassEntry = listTeacherClassEntry;
	}
	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}
	public void setSubjectMap(Map<Integer, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public List<ExamMarksEntryDetailsTO> getListCourseDetails() {
		return listCourseDetails;
	}
	public void setListCourseDetails(List<ExamMarksEntryDetailsTO> listCourseDetails) {
		this.listCourseDetails = listCourseDetails;
	}
	public List<String> getExamNames() {
		return examNames;
	}
	public void setExamNames(List<String> examNames) {
		this.examNames = examNames;
	}
}
