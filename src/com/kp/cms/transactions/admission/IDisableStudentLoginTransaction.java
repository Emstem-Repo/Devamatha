package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.forms.admission.DisableStudentLoginForm;

public interface IDisableStudentLoginTransaction {

	public List<String> getStudentDetails(StringBuffer query)throws Exception;

	public boolean checkForDisableStudentLogin(List<String> studentIds, DisableStudentLoginForm disableStudentLoginForm)throws Exception;

}
