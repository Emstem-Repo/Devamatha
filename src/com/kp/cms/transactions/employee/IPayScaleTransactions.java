package com.kp.cms.transactions.employee;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.forms.employee.PayScaleDetailsForm;


public interface IPayScaleTransactions {
	public boolean addPayScale(PayScaleBO payScale);
	public List<PayScaleBO> getPayscaleList();
	public PayScaleBO getPayScaleById(int id); 
	public boolean updatePayScale(PayScaleBO payScale);
	public boolean deletePayScale(int id);
	public boolean duplicateCheck(String name,HttpSession session,ActionErrors errors,PayScaleDetailsForm payScaleForm);
	public boolean reactivatePayScale(PayScaleDetailsForm payScaleForm)throws Exception;
}
