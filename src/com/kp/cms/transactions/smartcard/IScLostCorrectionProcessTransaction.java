package com.kp.cms.transactions.smartcard;

import java.util.List;

import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessForm;

public interface IScLostCorrectionProcessTransaction {
	
	public List<ScLostCorrection> getDetailsList(ScLostCorrectionProcessForm scProcessForm)throws Exception;
	
	public boolean setStatus(ScLostCorrectionProcessForm scForm)throws Exception;
	
	List<Integer> getStudentsWithoutPhotosAndRegNos(ScLostCorrectionProcessForm scForm) throws Exception;
	
	public List<ScLostCorrection> getAllStudentList(List<Integer> idList)throws Exception;

	public List<Integer> getEmployeesWithoutPhotosAndRegNos(ScLostCorrectionProcessForm scForm) throws Exception;

}
