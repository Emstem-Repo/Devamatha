package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpAppraisal;
import com.kp.cms.bo.admin.EmpAppraisalDetails;
import com.kp.cms.bo.admin.EmpAttribute;
import com.kp.cms.forms.employee.PrincipalAppraisalForm;
import com.kp.cms.to.employee.AppraisalsTO;

public interface IPrincipalAppraisalTransaction {

	public List<EmpAttribute>getAllEmpAttributes(boolean isEmployee)throws Exception;
	public boolean submitAppraisal(EmpAppraisal appraisal)throws Exception;
        public List<EmpAppraisal> getAppraisals();
	public EmpAppraisal getAppraisalDetails(PrincipalAppraisalForm principalAppraisal) ;
}
