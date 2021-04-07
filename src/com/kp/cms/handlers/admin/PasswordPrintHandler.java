package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.PrintPasswordForm;
import com.kp.cms.helpers.admin.PasswordPrintHelper;
import com.kp.cms.transactions.admin.IPasswordPrintTransaction;
import com.kp.cms.transactionsimpl.admin.PasswordPrintTransactionImpl;

public class PasswordPrintHandler {
	private static final Log log = LogFactory.getLog(PasswordPrintHandler.class);
	public static volatile PasswordPrintHandler passwordPrintHandler = null;

	public static PasswordPrintHandler getInstance() {
		if (passwordPrintHandler == null) {
			passwordPrintHandler = new PasswordPrintHandler();
			return passwordPrintHandler;
		}
		return passwordPrintHandler;
	}
	public void getPasswordPrintDetails(PrintPasswordForm prForm, HttpServletRequest request) throws Exception {
		IPasswordPrintTransaction iPrintTran = PasswordPrintTransactionImpl.getInstance();
		List<Student> stuList;
		Boolean isStudent = false;
		if("true".equalsIgnoreCase(prForm.getIsStudent())){
			isStudent = true;
		}
		if("true".equalsIgnoreCase(prForm.getIsRollNo())){
			stuList = iPrintTran.getRequiredRollNos(prForm.getRegNoFrom(), prForm.getRegNoTo());
		}
		else
		{
			stuList = iPrintTran.getRequiredRegdNos(prForm.getRegNoFrom(), prForm.getRegNoTo());
		}
		Collections.sort(stuList);
		List<String> messageList = PasswordPrintHelper.getInstance().copyBosToList(stuList, isStudent, request);
		prForm.setMessageList(messageList);
	}
	public void getPasswordPrintDetailsByProgrammAndSemOrClass(PrintPasswordForm passwordForm, HttpServletRequest request) throws Exception
	{
		IPasswordPrintTransaction iPrintTran = PasswordPrintTransactionImpl.getInstance();
		Boolean isStudent = false;
		if("true".equalsIgnoreCase(passwordForm.getIsStudent())){
			isStudent = true;
		}
		List<Student> stuList=iPrintTran.getPasswordPrintDetailsByProgrammAndSemOrClass(passwordForm);
		Collections.sort(stuList);
		List<String> messageList = PasswordPrintHelper.getInstance().copyBosToList(stuList, isStudent, request);
		passwordForm.setMessageList(messageList);
	}
	

}
