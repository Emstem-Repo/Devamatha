package com.kp.cms.transactions.serviceLearning;

import java.util.List;

import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.serviceLearning.ProgrammeEntryForm;

public interface IProgrammeEntryTransaction {

	public List<ProgrammeEntryBo> getProgrammeNameEntry()throws Exception;
	public ProgrammeEntryBo isDuplicateProgrammeEntryDetail(
			ProgrammeEntryForm programmeEntryForm, String mode) throws ApplicationException;
	public boolean addProgrammeNameEntry(ProgrammeEntryBo programmeEntryBo ,String mode)
	throws Exception ;
	public boolean deleteProgrammeNameEntry(int id, boolean activate,
			ProgrammeEntryForm programmeEntryForm) throws Exception ;
	public ProgrammeEntryBo getProgrammeNameEntryDetails(String id) throws Exception;
	public boolean reActivateProgrammeNameEntry(ProgrammeEntryBo programmeEntryBo,String userId) throws Exception;
	public List<ProgrammeEntryBo> getProgrammeNameEntryRestrictionByDate();
}
