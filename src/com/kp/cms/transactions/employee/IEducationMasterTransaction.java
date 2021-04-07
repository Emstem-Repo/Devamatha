package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpEducationMaster;
import com.kp.cms.forms.employee.EducationMasterForm;

public interface IEducationMasterTransaction {
	public EmpEducationMaster isEducationDuplcated(EducationMasterForm educationMasterForm) throws Exception;
	public boolean addEducationMaster(EmpEducationMaster educationMaster, String mode) throws Exception;
	public List<EmpEducationMaster> getEducationDetails() throws Exception;
	public boolean deleteEducation(int id, Boolean activate, EducationMasterForm educationMasterForm) throws Exception;	
}
