package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.forms.admin.ProgramTypeForm;

/**
 * An interface to manage the transactions related to Program Type
 * @author 
 */
public interface IProgramTypeTransaction {
	public boolean addProgramType(ProgramType programType, String mode) throws Exception;
	public List<ProgramType> getProgramType() throws Exception;
	public boolean editProgramType(int programTypeId, String programTypeName)throws Exception;
	public boolean deleteProgramType(int programTypeId,ProgramTypeForm programTypeForm, Boolean activate) throws Exception;
	public ProgramType existanceCheck(String programTypeName, int id) throws Exception;
	public List<ProgramType> getProgramTypeOnlineOpen() throws Exception;
	public List<UGCoursesBO> getUgCourses() throws Exception;
	
}
