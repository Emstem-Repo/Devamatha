package com.kp.cms.transactions.smartcard;

import java.util.List;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.forms.smartcard.ScLostCorrectionForm;

public interface IScLostCorrectionTransaction {
	
	public Student verifyRegisterNumberAndGetDetails(ScLostCorrectionForm scForm) throws Exception;
	
	public boolean addScLostCorrection(ScLostCorrection scCorrection)throws Exception;
	
	public ScLostCorrection checkForDuplicate(ScLostCorrectionForm scForm)throws Exception;
	
	public List<ScLostCorrection> getScHistory(ScLostCorrectionForm scForm)throws Exception;
	
	public ScLostCorrection checkForPresent(ScLostCorrectionForm scForm)throws Exception;
	
	public boolean cancelScLostCorrection(int id, String userId)throws Exception;
	
	public Student verifyRegisterNumberAndGetDetailsAcc(String string)throws Exception;
	
	public Employee verifyEmployeeIdAndGetEmployeeDetails(ScLostCorrectionForm scForm) throws Exception;

}
