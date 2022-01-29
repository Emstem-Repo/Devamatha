package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.AddressPrintForm;
import com.kp.cms.forms.admin.PrintPasswordForm;
import com.kp.cms.helpers.admin.AddressPrintHelper;
import com.kp.cms.helpers.admin.PasswordPrintHelper;
import com.kp.cms.transactions.admin.IAddressPrintTransaction;
import com.kp.cms.transactions.admin.IPasswordPrintTransaction;
import com.kp.cms.transactionsimpl.admin.AddressPrintTransactionImpl;
import com.kp.cms.transactionsimpl.admin.PasswordPrintTransactionImpl;

public class AddressPrintHandler {
	private static final Log log = LogFactory.getLog(AddressPrintHandler.class);
	public static volatile AddressPrintHandler addressPrintHandler =null;
	/**
	 * @return
	 */
	private AddressPrintHandler(){
		
	}
	public static AddressPrintHandler getInstance(){
		if(addressPrintHandler == null){
			addressPrintHandler = new AddressPrintHandler();
			return addressPrintHandler;
		}
		return addressPrintHandler;
	}
	/**
	 * @param addressPrintForm
	 * @param request
	 * @throws Exception
	 */
	public void getAddressPrintDetails(AddressPrintForm addressPrintForm,
			HttpServletRequest request) throws Exception {
		IAddressPrintTransaction iAddressPrintTransaction = AddressPrintTransactionImpl.getInstance();
		 List<Student> stuList = iAddressPrintTransaction.getRequiredRegdNos(addressPrintForm.getRegNoFrom(), addressPrintForm.getRegNoTo());
		List<String> messageList = AddressPrintHelper.getInstance().copyBosToList(stuList, request, addressPrintForm);
		addressPrintForm.setMessageList(messageList);
	}
}
