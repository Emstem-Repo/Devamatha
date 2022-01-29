package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.FreeFoodIssueBo;
import com.kp.cms.bo.admin.Student;

public interface IFreeFoodIssueTransaction {
	public List<Object[]> getStudentDetails(String registerNumber)throws Exception;
	public boolean saveStudentdata(FreeFoodIssueBo bo)throws Exception;
	public List<Object[]> checkingDuplicateEntry(String registerNumber)throws Exception;
	public Integer foodIssuedCount(String registerNumber)throws Exception;

}
