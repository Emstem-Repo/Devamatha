package com.kp.cms.handlers.employee;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.EmpHrPolicy;
import com.kp.cms.forms.employee.HrPolicyForm;
import com.kp.cms.transactions.employee.IHRPolicyTransaction;
import com.kp.cms.transactionsimpl.employee.HrPolicyTransactionImpl;

public class HrPolicyHandler {

private static final Log log = LogFactory.getLog(HrPolicyHandler.class);
	
	public static volatile HrPolicyHandler self=null;
	public static HrPolicyHandler getInstance(){
		if(self==null){
			self= new HrPolicyHandler();
		}
		return self;
	}
	
	IHRPolicyTransaction policyTransaction = new HrPolicyTransactionImpl(); 
	public List<EmpHrPolicy> getAllHRPolicies() throws Exception {
		log.info("entering of getAllHRPolicies in HrPolicyHandler class..");
		List<EmpHrPolicy> policiesList = policyTransaction.getHrPolicyDetails();
		log.info("exit of getAllHRPolicies in HrPolicyHandler class..");
		return policiesList;
	}
	
	/**
	 * This method is used to save the HR policy to database.
	 * @param hrPolicyForm
	 * @param contentType
	 * @param fileData
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	
	public boolean saveHrPolicy(HrPolicyForm hrPolicyForm, String contentType, byte[] fileData, String fileName) throws Exception {
		log.info("entering of saveHrPolicy in HrPolicyHandler class..");
		
		EmpHrPolicy empHrPolicy = new EmpHrPolicy();
		
		empHrPolicy.setCreatedBy(hrPolicyForm.getUserId());
		empHrPolicy.setCreatedDate(new Date());
		empHrPolicy.setLastModifiedDate(new Date());
		empHrPolicy.setModifiedBy(hrPolicyForm.getUserId());
		empHrPolicy.setIsActive(Boolean.TRUE);
		empHrPolicy.setName(hrPolicyForm.getPolicyName());
		empHrPolicy.setContentType(contentType);
		empHrPolicy.setDocument(fileData);
		empHrPolicy.setFileName(fileName);

		log.info("exit of saveHrPolicy in HrPolicyHandler class..");
		return policyTransaction.saveHrPolicy(empHrPolicy);
	}
	
	/**
	 * This method is used to delete the HR policy from database.
	 * @param id
	 * @return
	 * @throws Exception
	 */
	
	public boolean deleteHrPolicy(int id) throws Exception {
		log.info("entering of deleteHrPolicy in HrPolicyHandler class..");
		
		log.info("exit of deleteHrPolicy in HrPolicyHandler class..");
		return policyTransaction.deleteHrPolicy(id);
	}
	
	/**
	 * This method is used to load the HR policy based on policy id.
	 * @param policyId
	 * @return
	 * @throws Exception
	 */
	
	public EmpHrPolicy getHrPolicy(int policyId) throws Exception {
		log.info("entering of getHrPolicy in HrPolicyHandler class..");
		
		log.info("exit of getHrPolicy in HrPolicyHandler class..");
		return policyTransaction.getHrPolicy(policyId);
		
	}

	/**
	 * @param policyName
	 * @return
	 * @throws Exception
	 */
	public boolean isDuplicateHrpolicyName(String policyName) throws Exception {
		return policyTransaction.isDuplicateHrPolicyName(policyName);
	}
}