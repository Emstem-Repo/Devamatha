package com.kp.cms.handlers.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.exam.TempHallTicketOrIDCardForm;
import com.kp.cms.helpers.exam.TempHallTicketOrIDCardHelper;
import com.kp.cms.transactions.exam.ITempHallTicketOrIDCardTransaction;
import com.kp.cms.transactionsimpl.exam.TempHallTicketOrIDCardTransImpl;
import com.kp.cms.utilities.CommonUtil;

public class TempHallticketOrIDCardHandler {
	private static final Log log = LogFactory.getLog(TempHallticketOrIDCardHandler.class);
	ITempHallTicketOrIDCardTransaction transaction=TempHallTicketOrIDCardTransImpl.getInstance();
	public static volatile TempHallticketOrIDCardHandler tempHallticketOrIDCardHandler=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static TempHallticketOrIDCardHandler getInstance(){
		if(tempHallticketOrIDCardHandler==null){
			tempHallticketOrIDCardHandler= new TempHallticketOrIDCardHandler();}
		return tempHallticketOrIDCardHandler;
	}
	/**
	 * get the student details to print hall ticket
	 */
	public void printHallticket(TempHallTicketOrIDCardForm tempHallTicketOrIDCardForm)throws Exception{
		Object object[]=transaction.printHallTicket(tempHallTicketOrIDCardForm.getRegisterNO());
		if(object!=null){
			Student student=(Student)object[0];
			if(student!=null){
				tempHallTicketOrIDCardForm.setRegNo(student.getRegisterNo());
				String name=student.getAdmAppln().getPersonalData().getFirstName();
				if(student.getAdmAppln().getPersonalData().getMiddleName()!=null && !student.getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
					name=" "+student.getAdmAppln().getPersonalData().getMiddleName();
				}
				if(student.getAdmAppln().getPersonalData().getLastName()!=null && !student.getAdmAppln().getPersonalData().getLastName().isEmpty()){
					name=" "+student.getAdmAppln().getPersonalData().getLastName();
				}
				tempHallTicketOrIDCardForm.setNameOfStudent(name);
				tempHallTicketOrIDCardForm.setClassName(student.getClassSchemewise().getClasses().getName());
				tempHallTicketOrIDCardForm.setDate(CommonUtil.getTodayDate());
			}
			if(object[1]!=null){
				byte[] photo=(byte[])object[1];
				tempHallTicketOrIDCardForm.setPhotoBytes(photo);
			}
			
		}
	}
	/**
	 * get the student details to print hall ticket
	 */
	public void printHallticket(TempHallTicketOrIDCardForm tempHallTicketOrIDCardForm,String regNo)throws Exception{
		Object object[]=transaction.printHallTicket(regNo);
		if(object!=null){
			Student student=(Student)object[0];
			if(student!=null){
				tempHallTicketOrIDCardForm.setRegNo(student.getRegisterNo());
				String name=student.getAdmAppln().getPersonalData().getFirstName();
				if(student.getAdmAppln().getPersonalData().getMiddleName()!=null && !student.getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
					name=" "+student.getAdmAppln().getPersonalData().getMiddleName();
				}
				if(student.getAdmAppln().getPersonalData().getLastName()!=null && !student.getAdmAppln().getPersonalData().getLastName().isEmpty()){
					name=" "+student.getAdmAppln().getPersonalData().getLastName();
				}
				tempHallTicketOrIDCardForm.setNameOfStudent(name);
				tempHallTicketOrIDCardForm.setClassName(student.getClassSchemewise().getClasses().getName());
				tempHallTicketOrIDCardForm.setDate(CommonUtil.getTodayDate());
			}
			if(object[1]!=null){
				byte[] photo=(byte[])object[1];
				tempHallTicketOrIDCardForm.setPhotoBytes(photo);
			}
			tempHallTicketOrIDCardForm.setRegisterNO(null);
			
		}
	}
}
