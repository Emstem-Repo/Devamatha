package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.SendSmsToClassForm;

public interface ISendSmsToClassTransaction {

	List<Student> getStudentForClass(String studentForClassQuery) ;

	Student getStudentDetails(SendSmsToClassForm sendSmsToClassForm)throws Exception;

}
