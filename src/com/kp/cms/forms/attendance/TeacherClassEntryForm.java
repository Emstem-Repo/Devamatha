package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;

public class TeacherClassEntryForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9064108391068196298L;
	private int id;
//	private String academicYear;
	private String[] selectedClasses;
	private String subjectId;
	private String teachers;
	private Map<Integer, String> classMap;
	private Map<Integer,String> teachersMap; 
	private List<TeacherClassEntryTo> listTeacherClassEntry;
	private Map<Integer,String> subjectMap; 
	private String numericCode;
	private String year;
	private String selectedClassesArray1;
	private String roleId;
	
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	

	
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getTeachers() {
		return teachers;
	}
	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.selectedClasses = null;
		subjectId = null;
		teachers = null;
		this.numericCode=null;
		this.setYear(null);
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		String formName = request.getParameter("formName");
		//ActionErrors actionErrors = new ActionErrors();
		//actionErrors = super.validate(mapping, request, formName);

		return super.validate(mapping, request, formName);
	}
	public void setListTeacherClassEntry(List<TeacherClassEntryTo> listTeacherClassEntry) {
		this.listTeacherClassEntry = listTeacherClassEntry;
	}
	public List<TeacherClassEntryTo> getListTeacherClassEntry() {
		return listTeacherClassEntry;
	}
	public void setSubjectMap(Map<Integer,String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	public Map<Integer,String> getSubjectMap() {
		return subjectMap;
	}
	public String getNumericCode() {
		return numericCode;
	}
	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSelectedClassesArray1() {
		return selectedClassesArray1;
	}
	public void setSelectedClassesArray1(String selectedClassesArray1) {
		this.selectedClassesArray1 = selectedClassesArray1;
	}
	public String[] getSelectedClasses() {
		return selectedClasses;
	}
	public void setSelectedClasses(String[] selectedClasses) {
		this.selectedClasses = selectedClasses;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}