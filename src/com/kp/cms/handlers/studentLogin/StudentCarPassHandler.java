package com.kp.cms.handlers.studentLogin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.studentLogin.StudentCarPass;
import com.kp.cms.forms.studentLogin.StudentCarPassForm;
import com.kp.cms.helpers.studentLogin.StudentCarPassHelper;
import com.kp.cms.transactions.studentLogin.IStudentCarPassTransaction;
import com.kp.cms.transactionsimpl.studentLogin.StudentCarPassTransactionImpl;


public class StudentCarPassHandler {
	private static final Log log = LogFactory.getLog(StudentCarPassHandler.class);	
	public static volatile StudentCarPassHandler studentCarPassHandler = null;
	
	
	public static StudentCarPassHandler getInstance() {
		if (studentCarPassHandler == null) {
			studentCarPassHandler = new StudentCarPassHandler();
		}
		return studentCarPassHandler;
	}
	IStudentCarPassTransaction carPassTransaction=new StudentCarPassTransactionImpl();
	
	/**
	 * @param studentCarPassForm
	 * @return
	 * @throws Exception 
	 */
	public void saveStudentCarDetailsAndPrint(StudentCarPassForm studentCarPassForm) throws Exception{
		StudentCarPass studentCarPass=carPassTransaction.checkStudentCarPass(studentCarPassForm);
		if(studentCarPass==null){
			StudentCarPass studentCarPass1=StudentCarPassHelper.getInstance().convertFormToBo(studentCarPassForm);
			boolean checkSaveCar=carPassTransaction.SaveStudentCarDetails(studentCarPass1);
			if(checkSaveCar){
				StudentCarPass carPass=carPassTransaction.getStudentCarDetailsByStudentId(Integer.parseInt(studentCarPassForm.getStudentId()));
				StudentCarPassHelper.getInstance().convertBotoTo(carPass,studentCarPassForm);
			}
		}else{
			StudentCarPassHelper.getInstance().convertBotoTo(studentCarPass,studentCarPassForm);
		}
		
	}
	/**
	 * @param studentCarPassForm
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public void getStudentCarPassDetails(StudentCarPassForm studentCarPassForm) throws Exception{
		StudentCarPass carPass=carPassTransaction.getStudentCarDetailsByStudentId(Integer.parseInt(studentCarPassForm.getStudentId()));
		StudentCarPassHelper.getInstance().convertBotoForm(carPass,studentCarPassForm);
	}
	public Long getRegisterCarPasses() throws Exception{
		return carPassTransaction.getRegisterCarPasses();
	}
	
}
