package com.kp.cms.transactions.employee;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;

public interface IEmpLeaveAllotmentTxn {
public List<EmpLeaveAllotment> getLeaveAllotments()throws Exception; 
public List<EmpType> getEmpType()throws Exception; 
public boolean addLeaveAllot(EmpLeaveAllotment leaveAllot)throws Exception;
public EmpLeaveAllotment getLeaveAllotmentById(int id)throws Exception;
public boolean updateLeaveAllotment(EmpLeaveAllotment leaveAllotment)throws Exception;
public boolean deleteLeaveAllotment(int id)throws Exception;
public boolean duplicateCheck(String empType,String leaveType,HttpSession session,ActionErrors errors,int id,EmpLeaveAllotmentForm empLeaveAllotForm)throws Exception;
public int getEmpSettingsLeaveType()throws Exception;
public List<EmpLeaveType> getEmpLeaveType(int settingsLeave)throws Exception;
public boolean reactivateLeaveAllotment(EmpLeaveAllotmentForm empLeaveAllotForm)throws Exception;
}
