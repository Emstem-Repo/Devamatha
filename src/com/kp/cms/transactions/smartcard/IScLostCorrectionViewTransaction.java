package com.kp.cms.transactions.smartcard;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.forms.smartcard.ScLostCorrectionViewForm;

public interface IScLostCorrectionViewTransaction {
	
	public List<ScLostCorrection> getDetailsList(String searchCriteria)throws Exception;

	public List<ScLostCorrection> getAllSelectedList(List<Integer> idList)throws Exception;
	
	public boolean setStatus(ScLostCorrectionViewForm scForm)throws Exception;
	
	public List<ScLostCorrection> getDetailsListAfter(ScLostCorrectionViewForm scForm)throws Exception;
	
	List<Integer> getStudentsWithoutPhotosAndRegNos(ScLostCorrectionViewForm scForm) throws Exception;
	
	Map<String, byte[]> getStudentPhotos(List<Integer> studentIds) throws Exception;
	
	Map<String, byte[]> getEmployeePhotos(List<Integer> employeeIds) throws Exception;

	public List<Integer> getEmployeesWithoutPhotosAndRegNos(ScLostCorrectionViewForm scForm) throws Exception;
	
}
