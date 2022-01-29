package com.kp.cms.transactions.exam;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Student;

public interface ITempHallTicketOrIDCardTransaction {
	public Object[] printHallTicket(String regNo)throws Exception;

}
