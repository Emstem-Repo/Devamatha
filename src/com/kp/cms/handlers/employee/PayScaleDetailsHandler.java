package com.kp.cms.handlers.employee;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.PayScaleBO;

import com.kp.cms.forms.employee.PayScaleDetailsForm;

import com.kp.cms.helpers.employee.PayScaleDetailsHelper;
import com.kp.cms.to.employee.PayScaleTO;
import com.kp.cms.transactions.employee.IPayScaleTransactions;
import com.kp.cms.transactionsimpl.employee.PayScaleTransactionImpl;



public class PayScaleDetailsHandler {
	private static final Log log = LogFactory.getLog(PayScaleDetailsHandler.class);
	public static volatile PayScaleDetailsHandler payScaleHandler = null;
	private PayScaleDetailsHandler(){
		
	}
	public static PayScaleDetailsHandler getInstance() {
		if (payScaleHandler == null) {
			payScaleHandler = new PayScaleDetailsHandler();
			return payScaleHandler;
		}
		return payScaleHandler;
	}
	/**
	 * @param payScaleForm
	 * @return
	 */
	public boolean addPayScale(PayScaleDetailsForm payScaleForm){
		PayScaleBO payScale=PayScaleDetailsHelper.getInstance().convertFormTOBO(payScaleForm);
		IPayScaleTransactions transaction=new PayScaleTransactionImpl();
		boolean isAdded=transaction.addPayScale(payScale);
		return isAdded;
	}
	/**
	 * @return
	 */
	public List<PayScaleTO> getPayscaleList(){
		IPayScaleTransactions transaction=new PayScaleTransactionImpl();
		List<PayScaleBO> payScaleBo=transaction.getPayscaleList();
		List<PayScaleTO> payScaleTo=PayScaleDetailsHelper.getInstance().convertBosToTOs(payScaleBo);
		return payScaleTo;
	}
	public void editPayScale(PayScaleDetailsForm payScaleForm)throws Exception{
		IPayScaleTransactions transaction=new PayScaleTransactionImpl();
		PayScaleBO payScaleBo=transaction.getPayScaleById(payScaleForm.getId());
		PayScaleDetailsHelper.getInstance().setBotoForm(payScaleForm, payScaleBo);
	}
	public boolean updatePayScale(PayScaleDetailsForm payScaleForm){
		PayScaleBO payScaleBo=PayScaleDetailsHelper.getInstance().convertFormToBo(payScaleForm);
		IPayScaleTransactions transaction=new PayScaleTransactionImpl();
		boolean isUpdated=transaction.updatePayScale(payScaleBo);
		return isUpdated;
	}
	public boolean deletePayScale(PayScaleDetailsForm payScaleForm)throws Exception{
		
		IPayScaleTransactions transaction=new PayScaleTransactionImpl();
			boolean isDeleted=transaction.deletePayScale(payScaleForm.getId());
			return isDeleted;
		}
	public boolean duplicateCheck(PayScaleDetailsForm payScaleForm,ActionErrors errors,HttpSession session){
		IPayScaleTransactions transaction=new PayScaleTransactionImpl();
		boolean duplicate=transaction.duplicateCheck(payScaleForm.getPayScale(),session,errors,payScaleForm);
		return duplicate;
	}
	public boolean reactivatePayScale(PayScaleDetailsForm payScaleForm,String userId)
	 throws Exception{
		IPayScaleTransactions transaction=new PayScaleTransactionImpl();
        return transaction.reactivatePayScale(payScaleForm);
    }
}
