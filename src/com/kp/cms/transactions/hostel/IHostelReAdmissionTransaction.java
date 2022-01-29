package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.hostel.HostelReAdmissionForm;

public interface IHostelReAdmissionTransaction {

	public List<HostelOnlineApplication> getStudentDetailsByHostelIdAndYear(HostelReAdmissionForm admissionForm)throws Exception;

	public boolean updateHostelOnlineApplication(List<HostelOnlineApplication> applications)throws Exception;
	
	public Student getStudentDetails(int id)throws Exception;

}
