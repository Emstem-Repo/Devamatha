package com.kp.cms.to.admission;

import java.util.List;

import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;

public class PreferenceTO {
	private int id;
	private String programTypeId;
	private String programId;
	private String courseId;
	private String firstprefCourseName;
	private String firstprefCourseCode;
	private String firstprefPgmName;
	private String firstprefPgmTypeName;
	private String secondprefCourseName;
	private String secondprefCourseCode;
	private String secondprefPgmName;
	private String secondprefPgmTypeName;
	private String thirdprefCourseName;
	private String thirdprefCourseCode;
	private String thirdprefPgmName;
	private String thirdprefPgmTypeName;
	private String firstPrefCourseId;
	private String secondPrefCourseId;
	private String thirdPrefCourseId;
	private String firstPrefProgramId;
	private String secondPrefProgramId;
	private String thirdPrefProgramId;
	private String firstPrefProgramTypeId;
	private String secondPrefProgramTypeId;
	private String thirdPrefProgramTypeId;
	private List<CourseTO> prefcourses;
	private List<ProgramTypeTO> prefProgramtypes;
	private List<ProgramTO> prefprograms;
	private String selectedPrefId;
	
	private int firstPerfId;
	private int secondPerfId;
	private int thirdPerfId;
	
	public String getFirstPrefCourseId() {
		return firstPrefCourseId;
	}
	public void setFirstPrefCourseId(String firstPrefCourseId) {
		this.firstPrefCourseId = firstPrefCourseId;
	}
	public String getSecondPrefCourseId() {
		return secondPrefCourseId;
	}
	public void setSecondPrefCourseId(String secondPrefCourseId) {
		this.secondPrefCourseId = secondPrefCourseId;
	}
	public String getThirdPrefCourseId() {
		return thirdPrefCourseId;
	}
	public void setThirdPrefCourseId(String thirdPrefCourseId) {
		this.thirdPrefCourseId = thirdPrefCourseId;
	}
		
	public String getFirstprefCourseName() {
		return firstprefCourseName;
	}
	public void setFirstprefCourseName(String firstprefCourseName) {
		this.firstprefCourseName = firstprefCourseName;
	}
	public String getFirstprefPgmName() {
		return firstprefPgmName;
	}
	public void setFirstprefPgmName(String firstprefPgmName) {
		this.firstprefPgmName = firstprefPgmName;
	}
	public String getFirstprefPgmTypeName() {
		return firstprefPgmTypeName;
	}
	public void setFirstprefPgmTypeName(String firstprefPgmTypeName) {
		this.firstprefPgmTypeName = firstprefPgmTypeName;
	}
	public String getSecondprefCourseName() {
		return secondprefCourseName;
	}
	public void setSecondprefCourseName(String secondprefCourseName) {
		this.secondprefCourseName = secondprefCourseName;
	}
	public String getSecondprefPgmName() {
		return secondprefPgmName;
	}
	public void setSecondprefPgmName(String secondprefPgmName) {
		this.secondprefPgmName = secondprefPgmName;
	}
	public String getSecondprefPgmTypeName() {
		return secondprefPgmTypeName;
	}
	public void setSecondprefPgmTypeName(String secondprefPgmTypeName) {
		this.secondprefPgmTypeName = secondprefPgmTypeName;
	}
	public String getThirdprefCourseName() {
		return thirdprefCourseName;
	}
	public void setThirdprefCourseName(String thirdprefCourseName) {
		this.thirdprefCourseName = thirdprefCourseName;
	}
	public String getThirdprefPgmName() {
		return thirdprefPgmName;
	}
	public void setThirdprefPgmName(String thirdprefPgmName) {
		this.thirdprefPgmName = thirdprefPgmName;
	}
	public String getThirdprefPgmTypeName() {
		return thirdprefPgmTypeName;
	}
	public void setThirdprefPgmTypeName(String thirdprefPgmTypeName) {
		this.thirdprefPgmTypeName = thirdprefPgmTypeName;
	}

	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFirstPerfId() {
		return firstPerfId;
	}
	public void setFirstPerfId(int firstPerfId) {
		this.firstPerfId = firstPerfId;
	}
	public int getSecondPerfId() {
		return secondPerfId;
	}
	public void setSecondPerfId(int secondPerfId) {
		this.secondPerfId = secondPerfId;
	}
	public int getThirdPerfId() {
		return thirdPerfId;
	}
	public void setThirdPerfId(int thirdPerfId) {
		this.thirdPerfId = thirdPerfId;
	}
	public List<CourseTO> getPrefcourses() {
		return prefcourses;
	}
	public void setPrefcourses(List<CourseTO> prefcourses) {
		this.prefcourses = prefcourses;
	}
	public List<ProgramTypeTO> getPrefProgramtypes() {
		return prefProgramtypes;
	}
	public void setPrefProgramtypes(List<ProgramTypeTO> prefProgramtypes) {
		this.prefProgramtypes = prefProgramtypes;
	}
	public List<ProgramTO> getPrefprograms() {
		return prefprograms;
	}
	public void setPrefprograms(List<ProgramTO> prefprograms) {
		this.prefprograms = prefprograms;
	}
	public String getFirstPrefProgramId() {
		return firstPrefProgramId;
	}
	public void setFirstPrefProgramId(String firstPrefProgramId) {
		this.firstPrefProgramId = firstPrefProgramId;
	}
	public String getSecondPrefProgramId() {
		return secondPrefProgramId;
	}
	public void setSecondPrefProgramId(String secondPrefProgramId) {
		this.secondPrefProgramId = secondPrefProgramId;
	}
	public String getThirdPrefProgramId() {
		return thirdPrefProgramId;
	}
	public void setThirdPrefProgramId(String thirdPrefProgramId) {
		this.thirdPrefProgramId = thirdPrefProgramId;
	}
	public String getFirstPrefProgramTypeId() {
		return firstPrefProgramTypeId;
	}
	public void setFirstPrefProgramTypeId(String firstPrefProgramTypeId) {
		this.firstPrefProgramTypeId = firstPrefProgramTypeId;
	}
	public String getSecondPrefProgramTypeId() {
		return secondPrefProgramTypeId;
	}
	public void setSecondPrefProgramTypeId(String secondPrefProgramTypeId) {
		this.secondPrefProgramTypeId = secondPrefProgramTypeId;
	}
	public String getThirdPrefProgramTypeId() {
		return thirdPrefProgramTypeId;
	}
	public void setThirdPrefProgramTypeId(String thirdPrefProgramTypeId) {
		this.thirdPrefProgramTypeId = thirdPrefProgramTypeId;
	}
	public String getSelectedPrefId() {
		return selectedPrefId;
	}
	public void setSelectedPrefId(String selectedPrefId) {
		this.selectedPrefId = selectedPrefId;
	}
	public String getFirstprefCourseCode() {
		return firstprefCourseCode;
	}
	public void setFirstprefCourseCode(String firstprefCourseCode) {
		this.firstprefCourseCode = firstprefCourseCode;
	}
	public String getSecondprefCourseCode() {
		return secondprefCourseCode;
	}
	public void setSecondprefCourseCode(String secondprefCourseCode) {
		this.secondprefCourseCode = secondprefCourseCode;
	}
	public String getThirdprefCourseCode() {
		return thirdprefCourseCode;
	}
	public void setThirdprefCourseCode(String thirdprefCourseCode) {
		this.thirdprefCourseCode = thirdprefCourseCode;
	}
	
}
