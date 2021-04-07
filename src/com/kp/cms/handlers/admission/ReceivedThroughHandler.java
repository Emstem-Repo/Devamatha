package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.forms.admission.ReceivedThroughForm;
import com.kp.cms.helpers.admission.ReceivedThroughHelper;
import com.kp.cms.to.admission.ReceivedThroughTo;
import com.kp.cms.transactions.admission.IReceivedThroughTxn;
import com.kp.cms.transactionsimpl.admission.ReceivedThroughTxnImpl;

public class ReceivedThroughHandler {
	private static volatile ReceivedThroughHandler receivedThroughHandler = null;
	IReceivedThroughTxn transaction =ReceivedThroughTxnImpl.getInstance();
	/**
	 * @return
	 */
	public static ReceivedThroughHandler getInstance(){
		if(receivedThroughHandler == null){
			receivedThroughHandler = new ReceivedThroughHandler();
			return receivedThroughHandler;
		}
		return receivedThroughHandler;
	}
	
	/**
	 * @param receivedThroughForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addReceivedThrough(ReceivedThroughForm receivedThroughForm,String mode) throws Exception {
		ReceivedThrough received = ReceivedThroughHelper.getInstance().convertFormToBO(receivedThroughForm, mode);
		boolean isAdd = transaction.addReceivedThrough(received, mode);
		return isAdd;
	}
	
	/**
	 * @param receivedThroughForm
	 * @param errors
	 * @param session
	 * @return
	 */
	public boolean duplicateCheck(ReceivedThroughForm receivedThroughForm,ActionErrors errors,HttpSession session){
		boolean duplicate=transaction.duplicateCheck(session,errors,receivedThroughForm);
		return duplicate;
	}
	/**
	 * @param receivedThroughForm
	 * @throws Exception
	 */
	public void editReceivedThrough(ReceivedThroughForm receivedThroughForm)throws Exception{
		ReceivedThrough received=transaction.getReceivedThroughById(receivedThroughForm.getId());
		ReceivedThroughHelper.getInstance().setBotoForm(receivedThroughForm, received);
	}
    /**
     * @param receivedThroughForm
     * @return
     * @throws Exception
     */
    public boolean deleteReceivedThrough(ReceivedThroughForm receivedThroughForm)throws Exception{
		boolean isDeleted=transaction.deleteReceivedThrough(receivedThroughForm.getId());
		return isDeleted;
	}
    public boolean reactivateReceivedThrough(ReceivedThroughForm receivedThroughForm,String userId)
	 throws Exception{
       return transaction.reactivateReceivedThrough(receivedThroughForm);
   }
    public List<ReceivedThroughTo> getReceivedThroughList(){
		List<ReceivedThrough> receivedThroughList=transaction.getReceivedThroughList();
		List<ReceivedThroughTo> receivedThroughToList = ReceivedThroughHelper.getInstance().convertBoToTO(receivedThroughList);
		return receivedThroughToList;
	}
}
