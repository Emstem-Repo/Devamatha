package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlInOut;
import com.kp.cms.to.hostel.StudentInoutTo;

public interface IStudentInoutTransactions {
	public List<Object> getStudentDetails(String searchQuery) throws Exception;
	public String submitStudentDetails(HlInOut hlInOut) throws Exception;
	public boolean checkStudentInOutForADay(StudentInoutTo studentInoutTo)throws Exception;

}
