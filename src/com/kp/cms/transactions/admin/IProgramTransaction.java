package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admin.ProgramForm;

public interface IProgramTransaction {
	public List<Program> getPrograme() throws Exception;
	public boolean addProgram(Program program, String mode) throws Exception;
	public boolean deleteProgram(int programId, Boolean activate, ProgramForm programForm) throws Exception;
	public boolean isProgramNameDuplcated(ProgramForm programForm) throws Exception;	
	public boolean isProgramCodeDuplcated(String programCode) throws Exception;	
	public Program isProgramToBeActivated(ProgramForm programForm) throws Exception;	
	public Program getProgramDetails(int id) throws Exception;
	
}
