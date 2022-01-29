package com.kp.cms.to.employee;
	
	import java.util.Map;

	public class GuestEducationalDetailsTO {
		
		private String educationId;
		private String course;
		private String specialization;
		private String yearOfComp;
		private String grade;
		private String institute;
		private Map<String,String> fixedMap;
		private Map<String,String> levelMap;
		private String qualification;
		private String year;
		private int educationDetailsID; 
		private String focus;
		
		public String getEducationId() {
			return educationId;
		}
		public void setEducationId(String educationId) {
			this.educationId = educationId;
		}
		public String getCourse() {
			return course;
		}
		public String getSpecialization() {
			return specialization;
		}
		public String getYearOfComp() {
			return yearOfComp;
		}
		public String getGrade() {
			return grade;
		}
		public String getInstitute() {
			return institute;
		}
		public void setCourse(String course) {
			this.course = course;
		}
		public void setSpecialization(String specialization) {
			this.specialization = specialization;
		}
		public void setYearOfComp(String yearOfComp) {
			this.yearOfComp = yearOfComp;
		}
		public void setGrade(String grade) {
			this.grade = grade;
		}
		public void setInstitute(String institute) {
			this.institute = institute;
		}
		public Map<String, String> getFixedMap() {
			return fixedMap;
		}
		public Map<String, String> getLevelMap() {
			return levelMap;
		}
		public void setFixedMap(Map<String, String> fixedMap) {
			this.fixedMap = fixedMap;
		}
		public void setLevelMap(Map<String, String> levelMap) {
			this.levelMap = levelMap;
		}
		public String getQualification() {
			return qualification;
		}
		public void setQualification(String qualification) {
			this.qualification = qualification;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public int getEducationDetailsID() {
			return educationDetailsID;
		}
		public void setEducationDetailsID(int educationDetailsID) {
			this.educationDetailsID = educationDetailsID;
		}
		public void setFocus(String focus) {
			this.focus = focus;
		}
		public String getFocus() {
			return focus;
		}
		
	}



