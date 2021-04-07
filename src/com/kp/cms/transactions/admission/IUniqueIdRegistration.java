package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.StudentOnlineApplication;
import com.kp.cms.forms.admission.UniqueIdRegistrationForm;

public interface IUniqueIdRegistration {
	
	boolean registerOnlineApplication(StudentOnlineApplication onlineApplication)throws Exception;

	StudentOnlineApplication getLoginOnlineApplicationDetails( UniqueIdRegistrationForm objForm, String mode)throws Exception;

	
	List<AdmAppln> getAppliedCoursesList( StudentOnlineApplication onlineApplication)throws Exception;

	public boolean IsExistedMail(UniqueIdRegistrationForm objForm) throws Exception;

	AdmAppln getApplicationNoDetails(UniqueIdRegistrationForm objForm, String string)throws Exception;

	String getAdmissionAcademicYear()throws Exception;

	List<CandidatePGIDetails> getOnlinePaymentDetails(int uniqueId)throws Exception;
	
	String getApplicationFees(String year,String programTypeID,String category)throws Exception;

	boolean checkDuplicateNumber(Integer year,String challanNo)throws Exception;
	
	boolean checkDuplicateUniqueId(Integer year,String uniqueID)throws Exception;
	
	public boolean IsExistedPhoneNumber(UniqueIdRegistrationForm objForm) throws Exception;
}
