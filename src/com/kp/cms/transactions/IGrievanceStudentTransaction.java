package com.kp.cms.transactions;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.bo.exam.GrievanceStudentBo;
import com.kp.cms.forms.exam.GrievanceStudentForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.exam.GrievanceStudentTo;


public interface IGrievanceStudentTransaction {
	public List<GrievanceRemarkBo>  getStudents(GrievanceStudentForm grievanceStudentForm) throws Exception ;
	public Student getstudentById(Integer studentId) throws Exception ;
	public List<GrievanceRemarkBo>  getGrievanceStudentdetails(GrievanceStudentForm grievanceStudentForm) throws Exception;
	public boolean updateGrievanceBo(List<GrievanceStudentTo> studentList,Integer roleId) throws Exception;
	public boolean saveRemarks(List<GrievanceStudentBo> grievanceStudentBoList) throws Exception ;
	public Integer getRoleIdByUserId(GrievanceStudentForm grievanceStudentForm) throws Exception;
	public List<GrievanceRemarkBo>  getStudentDetails(LoginForm loginForm) throws Exception ;

}
