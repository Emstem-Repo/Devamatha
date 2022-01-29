package com.kp.cms.to.reports;
import java.io.Serializable;

public class ViewInternalMarkTO implements Serializable {
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Integer id;
		private Integer academicYear;
		private String teacherName;
		private String className;
		private String subjectName;
		private String numericCode;
		private int teacherId;
		private String departmentName;
		private String deptTeacherName;
		
		public ViewInternalMarkTO() {
			super();
		}
		public Integer getAcademicYear() {
			return academicYear;
		}
		public void setAcademicYear(Integer academicYear) {
			this.academicYear = academicYear;
		}
		public String getTeacherName() {
			return teacherName;
		}
		public void setTeacherName(String teacherName) {
			this.teacherName = teacherName;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getSubjectName() {
			return subjectName;
		}
		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getId() {
			return id;
		}
		public String getNumericCode() {
			return numericCode;
		}
		public void setNumericCode(String numericCode) {
			this.numericCode = numericCode;
		}
		public void setTeacherId(int teacherId) {
			this.teacherId = teacherId;
		}
		public int getTeacherId() {
			return teacherId;
		}
		public String getDepartmentName() {
			return departmentName;
		}
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}
		public String getDeptTeacherName() {
			return deptTeacherName;
		}
		public void setDeptTeacherName(String deptTeacherName) {
			this.deptTeacherName = deptTeacherName;
		}
		
		

	}



