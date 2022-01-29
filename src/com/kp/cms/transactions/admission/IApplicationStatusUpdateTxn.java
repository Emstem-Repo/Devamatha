package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.ApplicationStatusUpdate;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.forms.admission.ApplicationStatusUpdateForm;

public interface IApplicationStatusUpdateTxn {
	public List<ApplicationStatus> getApplicationStatus()throws Exception;
	public List<ApplicationStatusUpdate> getApplicationStatusUpdate()throws Exception; 
	public boolean duplicateCheck(ApplicationStatusUpdateForm applicationStatusUpdateForm,int applnNO)throws Exception;
	public boolean addApplicationStatusUpdate(ApplicationStatusUpdate statusUpdate,ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception;
	public List<ApplicationStatusUpdate> getApplicationStatusUpdateByAppNo(ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception;
	public Map<String,Integer> getAdmApplnMap()throws Exception;
	public int getAdmApplnId(String applicationNo)throws Exception;
	public Map<String,String> getApplicationStatusMap() throws Exception; 
	public boolean saveApplicationStatusUpdate(List<ApplicationStatusUpdate> list,ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception;
	public boolean checkIfCardGeneratedForStudent(String applicationNo) throws Exception;
}
