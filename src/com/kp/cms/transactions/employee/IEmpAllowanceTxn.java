package com.kp.cms.transactions.employee;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.forms.employee.EmpAllowanceForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;

public interface IEmpAllowanceTxn {
   public List<EmpAllowance> getEmpAllowance()throws Exception;
   public boolean duplicateCheck(String name,HttpSession session,ActionErrors errors,EmpAllowanceForm empAllowanceForm);
   public boolean addAllowance(EmpAllowance allowance)throws Exception;
   public EmpAllowance getAllowanceById(int id)throws Exception;
   public boolean updateAllowance(EmpAllowance allowance)throws Exception;
   public boolean deleteAllowance(int id)throws Exception;
   public boolean reactivateAllowance(EmpAllowanceForm empAllowanceForm)throws Exception;
}
