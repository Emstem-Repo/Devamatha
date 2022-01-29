package com.kp.cms.handlers.serviceLearning;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.serviceLearning.DepartmentNameEntryForm;
import com.kp.cms.forms.serviceLearning.ProgrammeEntryForm;
import com.kp.cms.helpers.serviceLearning.DepartmentNameEntryHelper;
import com.kp.cms.helpers.serviceLearning.ProgrammeEntryHelper;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.transactions.serviceLearning.IDepartmentNameEntryTransaction;
import com.kp.cms.transactions.serviceLearning.IProgrammeEntryTransaction;
import com.kp.cms.transactionsimpl.serviceLearning.DepartmentNameEntryImpl;
import com.kp.cms.transactionsimpl.serviceLearning.ProgrammeEntryTransactionImpl;

public class ProgrammeEntryHandler {
	private static volatile ProgrammeEntryHandler programmeEntryHandler = null;
	private ProgrammeEntryHandler(){

	}
	public static ProgrammeEntryHandler getInstance() {
		if (programmeEntryHandler == null) {
			programmeEntryHandler = new ProgrammeEntryHandler();
		}
		return programmeEntryHandler;
	}
	
	public List<ProgrammeEntryTo> getProgrammeNameEntry() throws Exception{
		IProgrammeEntryTransaction transaction = new ProgrammeEntryTransactionImpl();
		List<ProgrammeEntryBo> programmeEntryBoList = transaction.getProgrammeNameEntry();
		List<ProgrammeEntryTo> programmeEntryToList = ProgrammeEntryHelper.getInstance().convertBOsToTos(programmeEntryBoList);
		return programmeEntryToList;
		
	}
	
	public boolean addProgrammeNameEntry(ProgrammeEntryForm programmeEntryForm,String mode,HttpServletRequest request) throws Exception{
		IProgrammeEntryTransaction transaction = new ProgrammeEntryTransactionImpl();
		ProgrammeEntryBo programmeEntryBo = null;


		ProgrammeEntryBo dupDepartmentEntry= transaction.isDuplicateProgrammeEntryDetail(programmeEntryForm, mode);
		if(dupDepartmentEntry!=null && dupDepartmentEntry.getIsActive()==true)
		{
			throw new DuplicateException();
		}
		if(dupDepartmentEntry!=null && dupDepartmentEntry.getIsActive()==false){
			request.getSession().setAttribute("ProgrammeEntryBo",dupDepartmentEntry);

			programmeEntryForm.setDupId(String.valueOf(dupDepartmentEntry.getId()));
			throw new ReActivateException();
		}
		else
		{
			programmeEntryBo=ProgrammeEntryHelper.getInstance().addProgrammeNameEntry(programmeEntryForm, mode);
		}
		if(mode.equals("Add")){
			programmeEntryBo.setCreatedBy(programmeEntryForm.getUserId());
			programmeEntryBo.setModifiedBy(programmeEntryForm.getUserId());
			programmeEntryBo.setLastModifiedDate(new Date());
			programmeEntryBo.setCreatedDate(new Date());
			programmeEntryBo.setIsActive(true);
		}else{ // Edit
			programmeEntryBo.setLastModifiedDate(new Date());
			programmeEntryBo.setModifiedBy(programmeEntryForm.getUserId());
		}
		boolean isadded=transaction.addProgrammeNameEntry(programmeEntryBo, mode);
		return isadded;

	}
	
	public boolean deleteProgrammeNameEntry(int id,boolean activate,ProgrammeEntryForm programmeEntryForm) throws Exception{
		boolean isDeleted=false;
		IProgrammeEntryTransaction transaction = new ProgrammeEntryTransactionImpl();
	    isDeleted=transaction.deleteProgrammeNameEntry(id, activate, programmeEntryForm);
		return isDeleted;
	}
	
	public void editProgrammeNameEntry(ProgrammeEntryForm programmeEntryForm) throws Exception{
		IProgrammeEntryTransaction transaction = new ProgrammeEntryTransactionImpl();

		if(((programmeEntryForm.getId())!= null && !programmeEntryForm.getId().equalsIgnoreCase(""))){
			ProgrammeEntryBo programmeEntryBo = transaction.getProgrammeNameEntryDetails(programmeEntryForm.getId());
			ProgrammeEntryHelper.getInstance().setProgrammeNameEntryDetails(programmeEntryForm, programmeEntryBo);
		}
	}
	
	public boolean reActivateProgrammeNameEntry(ProgrammeEntryBo programmeEntryBo,String userId) throws Exception{
		IProgrammeEntryTransaction transaction = new ProgrammeEntryTransactionImpl();
		boolean isHostelNameEntry=transaction.reActivateProgrammeNameEntry(programmeEntryBo, userId);
		return isHostelNameEntry;
	}



}
