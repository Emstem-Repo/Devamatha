package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.forms.admin.ConvocationRegistrationStatusForm;

public interface IConvocationRegistrationStatusTransaction {
	public ConvocationRegistration getGuestPassCount(ConvocationRegistrationStatusForm form) throws Exception;
	public boolean updateData(ConvocationRegistration bo)throws Exception;
	public Object[] getStudentId(String studentRegNo)throws Exception;
	public ConvocationSession getDateAndTime(ConvocationRegistrationStatusForm form)throws Exception;
}
