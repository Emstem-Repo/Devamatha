package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpExceptionTypeBO;
import com.kp.cms.forms.employee.ExceptionTypesForm;
import com.kp.cms.helpers.employee.ExceptionTypesHelper;
import com.kp.cms.helpers.employee.ReadAttendanceFileHelper;
import com.kp.cms.to.employee.ExceptionTypeTO;
import com.kp.cms.transactions.employee.IExceptionTypesTransaction;
import com.kp.cms.transactionsimpl.employee.ExceptionTypesTransactionImpl;

public class ExceptionTypesHandler {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(ExceptionTypesHandler.class);
	public static volatile ExceptionTypesHandler objHandler = null;

	public static ExceptionTypesHandler getInstance() {
		if (objHandler == null) {
			objHandler = new ExceptionTypesHandler();
			return objHandler;
		}
		return objHandler;
	}

	public boolean addExceptionTypes(ExceptionTypesForm objForm)
			throws Exception {
		IExceptionTypesTransaction iTransaction = ExceptionTypesTransactionImpl.getInstance();
		iTransaction.isDuplicated(objForm,objForm.getExceptionType(),objForm.getExceptionShortName(),0);
		EmpExceptionTypeBO Bo = ExceptionTypesHelper.getInstance().copyDataFromFormToBO(objForm,"add");
		boolean isAdded = false;
		isAdded = iTransaction.addAttendance(Bo,"add");
		return isAdded;

	}

	public List<ExceptionTypeTO> getExceptionTypes() throws Exception {
		IExceptionTypesTransaction iTransaction = ExceptionTypesTransactionImpl
				.getInstance();
		return ExceptionTypesHelper.getInstance().convertBOToTO(
				iTransaction.getExceptionTypes());
	}

	public boolean deleteExceptionTypes(ExceptionTypesForm objForm)
			throws Exception {
		IExceptionTypesTransaction iTransaction = ExceptionTypesTransactionImpl
				.getInstance();
		return iTransaction.deleteExceptionTypes(objForm.getId(), objForm
				.getUserId());
	}

	public ExceptionTypesForm getUpdatableForm(ExceptionTypesForm objForm)
			throws Exception {
		IExceptionTypesTransaction iTransaction = ExceptionTypesTransactionImpl
				.getInstance();

		return ExceptionTypesHelper.getInstance().convertBOToForm(
				iTransaction.getException(objForm.getId()), objForm);
	}

	public boolean updateExceptionTypes(ExceptionTypesForm objForm) throws Exception{
		IExceptionTypesTransaction iTransaction = ExceptionTypesTransactionImpl
		.getInstance();
		iTransaction.isDuplicated(objForm,objForm.getExceptionType(),objForm.getExceptionShortName(),objForm.getId());

		EmpExceptionTypeBO Bo = ExceptionTypesHelper.getInstance()
				.copyDataFromFormToBO(objForm,"update");

		boolean isAdded = false;
		isAdded = iTransaction.addAttendance(Bo,"update");
		return isAdded;

		
	}

	public boolean reactivateExceptionType(int oldId, String userId) throws Exception{
		IExceptionTypesTransaction iTransaction = ExceptionTypesTransactionImpl.getInstance();
		return iTransaction.reactivate(oldId,userId);
	}

}
