package com.kp.cms.handlers.admin;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.forms.admin.ConvocationRegistrationStatusForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.helpers.admin.ConvocationRegistrationStatusHelper;
import com.kp.cms.transactions.admin.IConvocationRegistrationStatusTransaction;
import com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction;
import com.kp.cms.transactionsimpl.admin.ConvocationRegistrationStatusTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamMarksVerificationCorrectionTransactionImpl;


public class ConvocationRegistrationStatusHandler {
	
	private static volatile ConvocationRegistrationStatusHandler convocationRegistrationStatusHandler = null;
	private static final Log log = LogFactory.getLog(ConvocationRegistrationStatusHandler.class);
	private ConvocationRegistrationStatusHandler(){
		
	}
	/**
	 * @return
	 */
	public static ConvocationRegistrationStatusHandler getInstance() {
		if (convocationRegistrationStatusHandler == null) {
			convocationRegistrationStatusHandler = new ConvocationRegistrationStatusHandler();
		}
		return convocationRegistrationStatusHandler;
	}
	
	public boolean checkValidStudentRegNo(ConvocationRegistrationStatusForm Form) throws Exception{
		IExamMarksVerificationCorrectionTransaction transaction1=ExamMarksVerificationCorrectionTransactionImpl.getInstance();
		Integer studentId=transaction1.getStudentId(Form.getRegisterNo());
		boolean isValidStudent=false;
		if(studentId!=null && studentId>0){
			isValidStudent=true;
		}
		return isValidStudent;
	}
	
	public boolean checkStudentAppliedForConvocation(ConvocationRegistrationStatusForm Form) throws Exception{
		boolean isAppliedForConvocation=false;
			IConvocationRegistrationStatusTransaction transaction=ConvocationRegistrationStatusTransactionImpl.getInstance();
			Object[] objects=transaction.getStudentId(Form.getRegisterNo());
			if(objects!=null ){
				if(objects[0]!=null && !objects[0].toString().isEmpty()){
					isAppliedForConvocation=true;
					Form.setStudentId(Integer.parseInt(objects[0].toString())); 
						if(objects[1]!=null && !objects[1].toString().isEmpty()){
							Form.setCourseName((objects[1].toString()));
						}
						if(objects[2]!=null && !objects[2].toString().isEmpty()){
							Form.setCourseid(Integer.parseInt(objects[2].toString()));
							}
						}
					}
		return isAppliedForConvocation;
	}
	public void checkStudentDetails(ConvocationRegistrationStatusForm Form) throws Exception{
		IConvocationRegistrationStatusTransaction transaction=ConvocationRegistrationStatusTransactionImpl.getInstance();
		ConvocationRegistration bo=transaction.getGuestPassCount(Form);
		ConvocationSession csession=transaction.getDateAndTime(Form);
		ConvocationRegistrationStatusHelper.getInstance().convertBotoTo(bo,Form,csession);
	}
	public boolean updateData(ConvocationRegistrationStatusForm Form) throws Exception{
		IConvocationRegistrationStatusTransaction transaction=ConvocationRegistrationStatusTransactionImpl.getInstance();
		boolean isAdded=false;
		ConvocationRegistration bo=ConvocationRegistrationStatusHelper.getInstance().convertFormToBo(Form);
		isAdded=transaction.updateData(bo);
		return isAdded;
	}

}
