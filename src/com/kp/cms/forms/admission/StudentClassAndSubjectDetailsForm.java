package com.kp.cms.forms.admission;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;

public class StudentClassAndSubjectDetailsForm extends BaseActionForm {
		private String regNo;
		private String applnNo;
		private StudentTO student;
		private boolean studentAvailable;
		private boolean studentPreviousDetails;
		
		public String getRegNo() {
			return regNo;
		}
		public void setRegNo(String regNo) {
			this.regNo = regNo;
		}
		public String getApplnNo() {
			return applnNo;
		}
		public void setApplnNo(String applnNo) {
			this.applnNo = applnNo;
		}
		
		public StudentTO getStudent() {
			return student;
		}
		public void setStudent(StudentTO student) {
			this.student = student;
		}
		public boolean isStudentAvailable() {
			return studentAvailable;
		}
		public void setStudentAvailable(boolean studentAvailable) {
			this.studentAvailable = studentAvailable;
		}
		public boolean isStudentPreviousDetails() {
			return studentPreviousDetails;
		}
		public void setStudentPreviousDetails(boolean studentPreviousDetails) {
			this.studentPreviousDetails = studentPreviousDetails;
		}
		public void resetFields() {
			this.setRegNo(null);
			this.setApplnNo(null);
			this.studentAvailable=false;
			this.studentPreviousDetails=false;
		}
}
