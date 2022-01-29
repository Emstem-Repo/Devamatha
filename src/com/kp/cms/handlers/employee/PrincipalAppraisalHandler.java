package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmpAppraisal;
import com.kp.cms.bo.admin.EmpAppraisalDetails;
import com.kp.cms.bo.admin.EmpAttribute;
import com.kp.cms.forms.employee.PrincipalAppraisalForm;
import com.kp.cms.helpers.admin.SingleFieldMasterHelper;
import com.kp.cms.helpers.employee.PrincipalAppraisalHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.employee.AppraisalsTO;
import com.kp.cms.to.employee.EmpAttributeTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.employee.IPrincipalAppraisalTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.employee.PrincipalAppraisalTransactionImpl;

public class PrincipalAppraisalHandler {
	private static final Log log = LogFactory.getLog(PrincipalAppraisalHandler.class);
	public static volatile PrincipalAppraisalHandler principalAppraisalHandler = null;

	private PrincipalAppraisalHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static PrincipalAppraisalHandler getInstance() {
		if (principalAppraisalHandler == null) {
			principalAppraisalHandler = new PrincipalAppraisalHandler();
		}
		return principalAppraisalHandler;
	}
	
	/**
	 * Used to get all departments
	 */
	public List<SingleFieldMasterTO>getAllDepartments()throws Exception{
		log.info("entering into getAllDepartments PrincipalAppraisalHandler");
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl.getInstance();
		List<Department> department = singleFieldMasterTransaction.getDepartmentFields();
		log.info("Leaving into getAllDepartments PrincipalAppraisalHandler");
		return SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(department, "Department");
	}
	
	/**
	 * Used to get All employee Attributes
	 */
	public List<EmpAttributeTO>getAllAttributes(boolean isEmployee)throws Exception{
		log.info("entering into getAllAttributes PrincipalAppraisalAction");
		IPrincipalAppraisalTransaction transaction = new PrincipalAppraisalTransactionImpl();
		List<EmpAttribute> attBOList = transaction.getAllEmpAttributes(isEmployee);
		log.info("Leaving into getAllAttributes PrincipalAppraisalAction");
		return PrincipalAppraisalHelper.getInstance().convertAttributeBOToTO(attBOList);
	}
	
	/**
	 * Used to submit appraisal
	 */
	public boolean submitAppraisal(PrincipalAppraisalForm appraisalForm, 
		boolean isPrincipal, String loginUserEmpId)throws Exception{
		IPrincipalAppraisalTransaction transaction = new PrincipalAppraisalTransactionImpl();
		EmpAppraisal appraisal = PrincipalAppraisalHelper.getInstance().generateAppraisalBO(appraisalForm, isPrincipal, loginUserEmpId);
		return transaction.submitAppraisal(appraisal);
	}
public List<AppraisalsTO> getAppraisalsDetails(){
		IPrincipalAppraisalTransaction transaction = new PrincipalAppraisalTransactionImpl();
		List<EmpAppraisal> appraisalDetails=transaction.getAppraisals();
		List<AppraisalsTO> appraisalListTO=PrincipalAppraisalHelper.getInstance().convertBlistTOTlist(appraisalDetails);
		return appraisalListTO;
	}
	public AppraisalsTO getAppraisalDetails(PrincipalAppraisalForm pAppraisal){
		IPrincipalAppraisalTransaction transaction = new PrincipalAppraisalTransactionImpl();
		EmpAppraisal appraisalDetails=transaction.getAppraisalDetails(pAppraisal);
		AppraisalsTO appraisalsTO=PrincipalAppraisalHelper.getInstance().convertBOToTO(appraisalDetails);
		return appraisalsTO;
	}
}
