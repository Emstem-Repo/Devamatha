package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.forms.exam.NewInternalMarksEntryForm;
import com.kp.cms.helpers.exam.NewInternalMarksEntryHelper;
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.transactions.exam.INewInternalMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewInternalMarksEntryTransactionImpl;

public class NewInternalMarksEntryHandler {
	private static volatile NewInternalMarksEntryHandler marksEntryHandler = null;
	private static final Log log = LogFactory.getLog(NewInternalMarksEntryHandler.class);
	NewInternalMarksEntryHelper helper = NewInternalMarksEntryHelper.getInstance();
	INewInternalMarksEntryTransaction transaction = NewInternalMarksEntryTransactionImpl.getInstance();
	private NewInternalMarksEntryHandler (){
		
	}
	public static NewInternalMarksEntryHandler getInstance(){
		if(marksEntryHandler == null){
			marksEntryHandler = new NewInternalMarksEntryHandler();
		}
		return marksEntryHandler;
	}
	/**
	 * @param internalMarksEntryForm
	 * @throws Exception
	 */
	public void getInternalMarksDetails( NewInternalMarksEntryForm internalMarksEntryForm) throws Exception{
		log.info("ebterd getInternalMarksDetails method");
//		 get the opened exam list from the database
		List<OpenInternalExamForClasses> openExamList = transaction.openInternalExamClasses();
//		convert the list of bo's into Map.
		Map<Integer,Map<String,Map<Integer,OpenInternalExamForClasses>>> openExamDetailsMap=helper.convertBoListTOMap(internalMarksEntryForm,openExamList);
//		get the current exam details based on userId.	
		List<Object[]> internalExamList = transaction.getCurrentInternalExamDetails(internalMarksEntryForm);
		if(internalExamList!= null && !internalExamList.isEmpty()){
//			convert list of objects to list of To's, and check whether the subject is having batch or not.
			List<InternalMarksEntryTO> listOfDetails = helper.convertBoListTOTolist(internalExamList);
//			Iterate the InternalMarksEntryTOList and get the List of students for each batch or a class . check the subject of each batch or class is opened for current day or not.
			helper.convertBOtoTO(listOfDetails,internalMarksEntryForm,"Theory",openExamDetailsMap);
		}
	}
	/**
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public Double getMaxMarkOfSubject(NewInternalMarksEntryForm objform) throws Exception{
		return transaction.getMaxMarksOfSubject(objform);
	}
	/**
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public boolean saveInternalMarks(NewInternalMarksEntryForm objform) throws Exception{
		return transaction.saveMarks(objform);
	}
	public String getTeacherMobileNoByUserId(String userId) throws Exception {
		Users users= transaction.getMobileNoByUserId(userId);
		String mobileNo=null;
		if(users!=null){
			if(users.getEmployee()!=null && users.getEmployee().getCurrentAddressMobile1()!=null 
					&& !users.getEmployee().getCurrentAddressMobile1().isEmpty()){
				mobileNo=users.getEmployee().getCurrentAddressMobile1();
			}else if(users.getGuest()!=null && users.getGuest().getCurrentAddressMobile1()!=null 
					&& !users.getGuest().getCurrentAddressMobile1().isEmpty()){
				mobileNo=users.getEmployee().getCurrentAddressMobile1();
			}
		}
		return mobileNo;
	}
}
