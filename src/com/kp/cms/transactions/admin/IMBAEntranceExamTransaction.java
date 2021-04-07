package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.MBAEntranceExam;
import com.kp.cms.forms.admin.MBAEntranceExamForm;

public interface IMBAEntranceExamTransaction {

	public List<MBAEntranceExam> getMBAEntranceExams() throws Exception;
	public boolean addMBAEntranceExam(MBAEntranceExam concessionType, String mode) throws Exception;
	public boolean deleteMBAEntranceExam(int dupId, boolean activate, MBAEntranceExamForm mbaEntranceExamsForm) throws Exception;
	public MBAEntranceExam isDuplicate(MBAEntranceExam oldConcessionType) throws Exception;
}
