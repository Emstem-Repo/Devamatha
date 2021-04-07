package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.MobileMessagingSchedule;
import com.kp.cms.bo.admin.Student;

public interface ISheduledSMSTransaction {

	List<Student> getStudentList(String claIds)throws Exception;


}
