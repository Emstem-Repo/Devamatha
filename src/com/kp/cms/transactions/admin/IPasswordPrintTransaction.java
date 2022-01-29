package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.PrintPasswordForm;

public interface IPasswordPrintTransaction {
	public List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo) throws Exception;
	public List<Student>getRequiredRollNos(String regNoFrom, String regNoTo) throws Exception;
	public List<Student> getPasswordPrintDetailsByProgrammAndSemOrClass(PrintPasswordForm passwordForm) throws Exception;
	
}
