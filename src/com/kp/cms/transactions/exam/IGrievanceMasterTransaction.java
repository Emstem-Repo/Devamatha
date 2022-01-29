package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.forms.exam.GrievanceMasterForm;

public interface IGrievanceMasterTransaction {
	public List<GrievanceNumber> getAllGrievanceNumber() throws Exception;
	public GrievanceNumber isGrievanceNumberDuplcated(GrievanceMasterForm grievanceMasterForm) throws Exception;
	public boolean addGrievanceMaster(GrievanceNumber bo,String mode) throws Exception ;
	public boolean deleteGrievanceMaster(int id, Boolean activate, GrievanceMasterForm grievanceMasterForm) throws Exception;

}
