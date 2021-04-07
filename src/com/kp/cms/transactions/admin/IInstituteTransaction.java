package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.College;


public interface IInstituteTransaction {
	public List<College> getCollegeNames() throws Exception;
	public College isInstituteNameDuplcated(College duplCollege) throws Exception;
	public boolean addInstitute(College college) throws Exception;	
	public boolean updateInstitute(College college) throws Exception;
	public boolean deleteInstitute(int instId, Boolean activate, String userId) throws Exception;	
	
}
