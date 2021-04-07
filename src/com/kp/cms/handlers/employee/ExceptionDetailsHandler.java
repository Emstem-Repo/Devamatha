package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpExceptionDetailsBO;
import com.kp.cms.forms.employee.ExceptionDetailseForm;
import com.kp.cms.helpers.employee.ExceptionDetailsHelper;
import com.kp.cms.to.employee.ExceptionDetailsTo;
import com.kp.cms.transactions.employee.IExceptionDetailsTransaction;
import com.kp.cms.transactions.employee.IExceptionTypesTransaction;
import com.kp.cms.transactionsimpl.employee.ExceptionDetailsTransactionImpl;
import com.kp.cms.transactionsimpl.employee.ExceptionTypesTransactionImpl;

public class ExceptionDetailsHandler {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(ExceptionDetailsHandler.class);
	IExceptionDetailsTransaction transaction = new ExceptionDetailsTransactionImpl();
	public static volatile ExceptionDetailsHandler objHandler = null;

	public static ExceptionDetailsHandler getInstance() {
		if (objHandler == null) {
			objHandler = new ExceptionDetailsHandler();
			return objHandler;
		}
		return objHandler;
	}

	public Map<Integer, String> getExceptionTypes() throws Exception {
		IExceptionTypesTransaction iTransaction = ExceptionTypesTransactionImpl
				.getInstance();
		return ExceptionDetailsHelper.getInstance().convertBOToTO(
				iTransaction.getExceptionTypes());

	}

	public boolean addExceptionDetails(ExceptionDetailseForm objForm)
			throws Exception {
		transaction = ExceptionDetailsTransactionImpl
				.getInstance();

		return transaction.addExceptionDetails(ExceptionDetailsHelper
				.getInstance().convertFormTOToBO(objForm));
	}
	
	/**
	 * @param exceptionDetailsToList
	 * @return
	 * @throws Exception
	 */
	public List<ExceptionDetailsTo> getExceptionDetails()
			throws Exception {
			
			List<EmpExceptionDetailsBO> exceptionDetailsBoList = transaction
					.getExceptionDetails();
			List<ExceptionDetailsTo> exceptionDetailsTo = ExceptionDetailsHelper.getInstance()
					.convertBoListToToList(exceptionDetailsBoList);
			return exceptionDetailsTo;

	}
	
	/**
	 * used to delete exceptions details 
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteExceptionDetails(int id, String userId)
			throws Exception {
		log.info("inside deleteExceptionDetails of ExceptionDetailsHandler");
		if(transaction != null)
		{
			return transaction.deleteExceptionDetails(id, userId);
		}
		log.info("existing deleteExceptionDetails of ExceptionDetailsHandler");
		return false;
		
	}
	
	public boolean reActivateExceptionDetails(int id,String userId)throws Exception
	{
		log.info("inside reActivateExceptionDetails of ExceptionDetailsHandler");
		if(transaction != null)
		{
			return transaction.reActivateExceptionDetails(id, userId);
		}
		log.info("Existing reActivateExceptionDetails of ExceptionDetailsHandler");
		return false;
		
	}

	public boolean updateExceptionDetails(
			ExceptionDetailseForm exceptionDetailseForm) throws Exception {
		
		log.info("inside updateExceptionDetails of ExceptionDetailsHandler");
		EmpExceptionDetailsBO exceptionDetailsBO = transaction
		.getExceptionDetailsOnId(exceptionDetailseForm.getId());
		log.info("existing updateExceptionDetails of ExceptionDetailsHandler");
		return transaction.updateExceptionDetails(ExceptionDetailsHelper
				.getInstance().convertTOToBO(exceptionDetailseForm,exceptionDetailsBO),exceptionDetailseForm.getDatesToBeDeleted());
		
	}
	
	public void getExceptionDetailsToEdit(
			ExceptionDetailseForm exceptionDetailseForm) throws Exception {
		
		log.info("inside getExceptionDetailsToEdit of ExceptionDetailsHandler");
		EmpExceptionDetailsBO exceptionDetailsBO = transaction.getExceptionDetailsOnId(exceptionDetailseForm.getId());
		log.info("existing getExceptionDetailsToEdit of ExceptionDetailsHandler");
		ExceptionDetailsHelper
				.getInstance().convertBOToForm(exceptionDetailseForm,exceptionDetailsBO);
		
	}
	
	public EmpExceptionDetailsBO duplicateCheckException(ExceptionDetailseForm exceptionDetailseForm) throws Exception{
		String query=ExceptionDetailsHelper.getInstance().getQueryforCheckDuplicate(exceptionDetailseForm);
		return transaction.duplicateCheckException(query);
	}

}
