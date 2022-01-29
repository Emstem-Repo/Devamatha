package com.kp.cms.transactions.studentLogin;

import com.kp.cms.bo.studentLogin.StudentCarPass;
import com.kp.cms.forms.studentLogin.StudentCarPassForm;

public interface IStudentCarPassTransaction {

	public boolean SaveStudentCarDetails(StudentCarPass studentCarPass) throws Exception;
	public StudentCarPass getStudentCarDetailsByStudentId(int studentId) throws Exception;
	public StudentCarPass checkStudentCarPass(StudentCarPassForm studentCarPassForm) throws Exception;
	public Long getRegisterCarPasses() throws Exception;
}
