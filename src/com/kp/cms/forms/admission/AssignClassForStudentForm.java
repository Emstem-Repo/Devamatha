package com.kp.cms.forms.admission;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.AssingClassForStudentTO;

public class AssignClassForStudentForm extends BaseActionForm {
	private String programId;
	private String courseId;
	private List<AssingClassForStudentTO> assingClassForStudentTOs;
	private String sortBy;
	private String startNumber;
	private String endNumber;
	private String classId;
	private String sectionId;
	private String admissionYear;
	private String academicYear;
	private String languageNo;
	private String percentageNo;
	private String genderNo;
	private String nameNo;
	private String categoryNo;
	private String sectionNo;
	private String[] classes;
	//Code added by sudhir
	private String classNo;
	//
	
	
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public List<AssingClassForStudentTO> getAssingClassForStudentTOs() {
		return assingClassForStudentTOs;
	}
	public void setAssingClassForStudentTOs(
			List<AssingClassForStudentTO> assingClassForStudentTOs) {
		this.assingClassForStudentTOs = assingClassForStudentTOs;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(String startNumber) {
		this.startNumber = startNumber;
	}
	public String getEndNumber() {
		return endNumber;
	}
	public void setEndNumber(String endNumber) {
		this.endNumber = endNumber;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(String admissionYear) {
		this.admissionYear = admissionYear;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getLanguageNo() {
		return languageNo;
	}
	public void setLanguageNo(String languageNo) {
		this.languageNo = languageNo;
	}
	public String getPercentageNo() {
		return percentageNo;
	}
	public void setPercentageNo(String percentageNo) {
		this.percentageNo = percentageNo;
	}
	public String getGenderNo() {
		return genderNo;
	}
	public void setGenderNo(String genderNo) {
		this.genderNo = genderNo;
	}
	public String getNameNo() {
		return nameNo;
	}
	public void setNameNo(String nameNo) {
		this.nameNo = nameNo;
	}
	public String getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getSectionNo() {
		return sectionNo;
	}
	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}
	public String[] getClasses() {
		return classes;
	}
	public void setClasses(String[] classes) {
		this.classes = classes;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getClassNo() {
		return classNo;
	}
	
}
