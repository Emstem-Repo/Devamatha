package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admin.DateOfBirthPrintForm;
import com.kp.cms.helpers.admin.DateOfBirthPrintHelper;
import com.kp.cms.transactions.admin.IDateOfBirthPrintTransaction;
import com.kp.cms.transactionsimpl.admin.DateOfBirthPrintTransactionImpl;

public class DateOfBirthPrintHandler {
	private static final Log log = LogFactory.getLog(DateOfBirthPrintHandler.class);
	public static volatile DateOfBirthPrintHandler dateOfBirthPrintHandler =null;
	/**
	 * @return
	 */
	public static DateOfBirthPrintHandler getInstance(){
		if(dateOfBirthPrintHandler == null){
			dateOfBirthPrintHandler = new DateOfBirthPrintHandler();
			return dateOfBirthPrintHandler;
		}
		return dateOfBirthPrintHandler;
	}
	/**
	 * @param addressPrintForm
	 * @param request
	 * @throws Exception
	 */
	public void getAddressPrintDetails(DateOfBirthPrintForm dateOfBirthPrintForm,
			HttpServletRequest request) throws Exception {
		IDateOfBirthPrintTransaction iDateOfBirthPrintTransaction = DateOfBirthPrintTransactionImpl.getInstance();
		//String year = iDateOfBirthPrintTransaction.getStudentAcademicYear(dateOfBirthPrintForm);
		 List<Student> stuList = iDateOfBirthPrintTransaction.getRequiredRegdNos(dateOfBirthPrintForm.getRegNoFrom(), dateOfBirthPrintForm.getRegNoTo());
		List<String> messageList = DateOfBirthPrintHelper.getInstance().copyBosToList(stuList, request, dateOfBirthPrintForm);
		dateOfBirthPrintForm.setMessageList(messageList);
	}
}
