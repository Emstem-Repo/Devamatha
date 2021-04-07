package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admission.ExamCenterForm;

public interface IExamCenterTransactions {
	
	public List<ExamCenter> getExamCenterDetails() throws Exception;
	
	public boolean getExamCenterDefineInProgram(int pgmId) throws Exception;

	public boolean addExamCenter(ExamCenter examCenterBO,String mode) throws Exception;
	
	public  ExamCenter checkDuplicate(ExamCenterForm examCenterForm) throws Exception;

	public ExamCenter getExamCenterDetailsToEdit(int id) throws Exception;

	public boolean deleteExamCenter(int centerId, boolean activate,ExamCenterForm examCenterForm) throws Exception;

}
