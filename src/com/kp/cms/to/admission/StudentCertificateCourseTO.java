package com.kp.cms.to.admission;

import java.io.Serializable;

public class StudentCertificateCourseTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String certificateCourseName;
	private String checked;
	private String studentName;
	private String registerNo;
	private int studentId;
	
	
	//Mary code
	
	private String selectedCourseId;
	private String curriculumSchemeDurationId;
	private String admApplnId;
	private String semester;
	
	//Mary code ends
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCertificateCourseName() {
		return certificateCourseName;
	}
	public void setCertificateCourseName(String certificateCourseName) {
		this.certificateCourseName = certificateCourseName;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getSelectedCourseId() {
		return selectedCourseId;
	}
	public void setSelectedCourseId(String selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
	}
	public String getAdmApplnId() {
		return admApplnId;
	}
	public void setAdmApplnId(String admApplnId) {
		this.admApplnId = admApplnId;
	}
	public String getCurriculumSchemeDurationId() {
		return curriculumSchemeDurationId;
	}
	public void setCurriculumSchemeDurationId(String curriculumSchemeDurationId) {
		this.curriculumSchemeDurationId = curriculumSchemeDurationId;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
}
