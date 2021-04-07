package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpInterview;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.to.employee.InterviewCommentsTO;

public interface IInterviewCommentsTransaction {

	List<Object[]> getDetails(String name) throws Exception;

	List<Object[]> getInterviewDetails(int id)throws Exception;

	boolean save(EmpInterview objBO)throws Exception;

	EmpOnlineResume getResumeDetails(int id) throws  Exception;

	List<EmpInterview> getInterviewComments(int id)throws Exception;

	boolean updateStatus(List<InterviewCommentsTO> listOfDetails)throws Exception;
}
