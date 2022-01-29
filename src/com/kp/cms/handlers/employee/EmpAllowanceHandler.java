package com.kp.cms.handlers.employee;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.forms.employee.EmpAllowanceForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.helpers.employee.EmpAllowanceHelper;
import com.kp.cms.helpers.employee.PayScaleDetailsHelper;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.transactions.employee.IEmpAllowanceTxn;
import com.kp.cms.transactions.employee.IPayScaleTransactions;
import com.kp.cms.transactionsimpl.employee.EmpAllowanceTxnImpl;
import com.kp.cms.transactionsimpl.employee.PayScaleTransactionImpl;


public class EmpAllowanceHandler {
	private static volatile EmpAllowanceHandler instance=null;
    private EmpAllowanceHandler(){
		
	}
    public static EmpAllowanceHandler getInstance(){
	   if(instance==null){
		   instance=new EmpAllowanceHandler();
	   }
	   return instance;
    }
    public List<EmpAllowanceTO> getEmpAllowance()throws Exception{
    	IEmpAllowanceTxn txn=EmpAllowanceTxnImpl.getInstance();
    	List<EmpAllowance> allowance=txn.getEmpAllowance();
    	List<EmpAllowanceTO> allowanceTO=EmpAllowanceHelper.getInstance().convertBOtoTO(allowance);
    	return allowanceTO;
    }
    public boolean duplicateCheck(EmpAllowanceForm empAllowanceForm,ActionErrors errors,HttpSession session){
    	IEmpAllowanceTxn transaction=EmpAllowanceTxnImpl.getInstance();
		boolean duplicate=transaction.duplicateCheck(empAllowanceForm.getAllowanceType(),session,errors,empAllowanceForm);
		return duplicate;
	}
    public boolean addAllowance(EmpAllowanceForm empAllowanceForm,String mode)throws Exception{
    	EmpAllowance allowance=EmpAllowanceHelper.getInstance().convertFormTOBO(empAllowanceForm,mode);
    	IEmpAllowanceTxn transaction=EmpAllowanceTxnImpl.getInstance();
		boolean isAdded=transaction.addAllowance(allowance);
		return isAdded;
	}
    public void editAllowance(EmpAllowanceForm empAllowanceForm)throws Exception{
    	IEmpAllowanceTxn transaction=EmpAllowanceTxnImpl.getInstance();
    	EmpAllowance allowance=transaction.getAllowanceById(empAllowanceForm.getId());
    	EmpAllowanceHelper.getInstance().setBotoForm(empAllowanceForm, allowance);
	}
    public boolean updateAllowance(EmpAllowanceForm empAllowanceForm,String mode)throws Exception{
    	EmpAllowance allowance=EmpAllowanceHelper.getInstance().convertFormTOBO(empAllowanceForm,mode);
    	IEmpAllowanceTxn transaction=EmpAllowanceTxnImpl.getInstance();
		boolean isUpdated=transaction.updateAllowance(allowance);
		return isUpdated;
	}
    public boolean deleteAllowance(EmpAllowanceForm empAllowanceForm)throws Exception{
		
    	IEmpAllowanceTxn transaction=EmpAllowanceTxnImpl.getInstance();
		boolean isDeleted=transaction.deleteAllowance(empAllowanceForm.getId());
		return isDeleted;
    }
    public boolean reactivateAllowance(EmpAllowanceForm empAllowanceForm,String userId)
	 throws Exception{
    	IEmpAllowanceTxn transaction=EmpAllowanceTxnImpl.getInstance();
       return transaction.reactivateAllowance(empAllowanceForm);
   }
}
