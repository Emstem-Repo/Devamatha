package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.CharacterCertificateForm;
import com.kp.cms.helpers.admin.CharacterCertificateHelper;
import com.kp.cms.transactions.admin.ICharacterCertificateTransaction;
import com.kp.cms.transactionsimpl.admin.CharacterCertificateTransactionImpl;
import com.kp.cms.transactionsimpl.admin.DateOfBirthPrintTransactionImpl;

public class CharacterCertificateHandler {
	private static final Log log = LogFactory.getLog(CharacterCertificateHandler.class);
	public static volatile CharacterCertificateHandler characterCertificateHandler =null;
	/**
	 * @return
	 */
	private CharacterCertificateHandler(){
		
	}
	public static CharacterCertificateHandler getInstance(){
		if(characterCertificateHandler == null){
			characterCertificateHandler = new CharacterCertificateHandler();
			return characterCertificateHandler;
		}
		return characterCertificateHandler;
	}
	/**
	 * @param addressPrintForm
	 * @param request
	 * @throws Exception
	 */
	public void getAddressPrintDetails(CharacterCertificateForm characterCertificateForm,
			HttpServletRequest request) throws Exception {
		ICharacterCertificateTransaction iCharacterCertificateTransaction = CharacterCertificateTransactionImpl.getInstance();
		 List<Student> stuList = iCharacterCertificateTransaction.getRequiredRegdNos(characterCertificateForm.getRegNoFrom(), characterCertificateForm.getRegNoTo());
		List<String> messageList = CharacterCertificateHelper.getInstance().copyBosToList(stuList, request, characterCertificateForm);
		characterCertificateForm.setMessageList(messageList);
	}
}
