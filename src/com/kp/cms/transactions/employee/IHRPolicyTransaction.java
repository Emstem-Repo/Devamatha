package com.kp.cms.transactions.employee;

import java.util.List;
import com.kp.cms.bo.admin.EmpHrPolicy;

public interface IHRPolicyTransaction {

	public List<EmpHrPolicy> getHrPolicyDetails() throws Exception;
	public boolean saveHrPolicy(EmpHrPolicy empHrPolicy) throws Exception;
	public boolean deleteHrPolicy(int id) throws Exception;
	public EmpHrPolicy getHrPolicy(int policyId) throws Exception;
	public boolean isDuplicateHrPolicyName(String policyName) throws Exception;
	
}
