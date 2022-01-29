package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.MBAEntranceExam;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.MBAEntranceExamForm;
import com.kp.cms.helpers.admin.MBAEntranceExamHelper;
import com.kp.cms.to.admin.MBAEntranceExamTO;
import com.kp.cms.transactions.admin.IMBAEntranceExamTransaction;
import com.kp.cms.transactionsimpl.admin.MBAEntranceExamTransactionImpl;

public class MBAEntranceExamHandler {

	private static volatile MBAEntranceExamHandler obj;
	
	private MBAEntranceExamHandler() {
		
	}
	
	public static MBAEntranceExamHandler getInstance() {
		if(obj == null) {
			obj = new MBAEntranceExamHandler();
		}
		return obj;
	}
	
	public List<MBAEntranceExamTO> getMBAEntranceExams() throws Exception {
		IMBAEntranceExamTransaction tx = MBAEntranceExamTransactionImpl.getInstance();
		List<MBAEntranceExam> mbaEntranceExams = tx.getMBAEntranceExams();
		List<MBAEntranceExamTO> mbaEntranceExamTOs = MBAEntranceExamHelper.getInstance().convertBOToTO(mbaEntranceExams);
		return mbaEntranceExamTOs;
	}
	
	public boolean addMBAEntranceExam(MBAEntranceExamForm mbaEntranceExamForm, String mode) throws Exception {
		IMBAEntranceExamTransaction tx = MBAEntranceExamTransactionImpl.getInstance();
		boolean isAdded = false;
		boolean originalNotChanged = false;
		String name = "";
		String origName = "";
		
		if(mbaEntranceExamForm.getName() != null && !mbaEntranceExamForm.getName().equals("")) {
			name = mbaEntranceExamForm.getName();
		}
		if(mbaEntranceExamForm.getOrigName() != null && !mbaEntranceExamForm.getOrigName().equals(""))	{
			origName = mbaEntranceExamForm.getOrigName();
		}
		
		if(origName.equalsIgnoreCase(name)) {
			originalNotChanged = true;
		}
		if(mode.equalsIgnoreCase("Add")) {
			originalNotChanged = false;
		}
		
		if(!originalNotChanged){
			MBAEntranceExam dupMBAEntranceExam = MBAEntranceExamHelper.getInstance().convertFormToBO(mbaEntranceExamForm);
			dupMBAEntranceExam = tx.isDuplicate(dupMBAEntranceExam);
			
			if(dupMBAEntranceExam != null && dupMBAEntranceExam.getIsActive()) {
				throw new DuplicateException();
			}
			else if(dupMBAEntranceExam != null && !dupMBAEntranceExam.getIsActive()) {
				mbaEntranceExamForm.setDupId(dupMBAEntranceExam.getId());
				throw new ReActivateException();
			}
		}
		
		MBAEntranceExam studentSpecialization = MBAEntranceExamHelper.getInstance().convertFormToBO(mbaEntranceExamForm);
		if(mode.equalsIgnoreCase("Add")) {
			studentSpecialization.setCreatedBy(mbaEntranceExamForm.getUserId());
			studentSpecialization.setCreatedDate(new Date());
			studentSpecialization.setModifiedBy(mbaEntranceExamForm.getUserId());
			studentSpecialization.setLastModifiedDate(new Date());
		}
		else {
			studentSpecialization.setModifiedBy(mbaEntranceExamForm.getUserId());
			studentSpecialization.setLastModifiedDate(new Date());
		}
		
		isAdded = tx.addMBAEntranceExam(studentSpecialization, mode);
		return isAdded;
	}
	
	public boolean deleteMBAEntranceExam(int dupId, boolean activate, MBAEntranceExamForm mbaEntranceExamForm) throws Exception {
		IMBAEntranceExamTransaction tx = MBAEntranceExamTransactionImpl.getInstance();
		boolean deleted = tx.deleteMBAEntranceExam(dupId, activate, mbaEntranceExamForm);
		return deleted;
	}
	
	public Map<Integer, String> getMBAEntranceExamMap() throws Exception {
		IMBAEntranceExamTransaction tx = MBAEntranceExamTransactionImpl.getInstance();
		List<MBAEntranceExam> mbaEntranceExams = tx.getMBAEntranceExams();
		return MBAEntranceExamHelper.getInstance().getMap(mbaEntranceExams);
	}
}
