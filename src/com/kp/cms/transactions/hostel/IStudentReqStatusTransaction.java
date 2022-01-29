package com.kp.cms.transactions.hostel;

import java.util.List;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.to.hostel.StudentReqStatusTO;

public interface IStudentReqStatusTransaction {
	
	public int getStudentId(int studentLoginId) throws Exception;
	
	public int getAdmApplnId(int studentId) throws Exception;

	public List<HlApplicationForm> getStudentDetailsList(int admApplnId) throws Exception;

	public List<StudentReqStatusTO> getStudentReqDetailsList(String query) throws Exception;

}
