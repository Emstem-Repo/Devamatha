package com.kp.cms.handlers.admin;

import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.FreeFoodIssueBo;
import com.kp.cms.forms.admin.FreeFoodIssueForm;
import com.kp.cms.helpers.admin.FreeFoodIssueHelper;
import com.kp.cms.to.admin.FreeFoodIssueTo;
import com.kp.cms.transactions.admin.IFreeFoodIssueTransaction;
import com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction;
import com.kp.cms.transactionsimpl.admin.FreeFoodIssueTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ExamMarksVerificationCorrectionTransactionImpl;

public class FreeFoodIssueHandler {
	public static volatile FreeFoodIssueHandler freeFoodIssueHandler = null;
	
	public static FreeFoodIssueHandler getInstance() {
		if (freeFoodIssueHandler == null) {
			freeFoodIssueHandler = new FreeFoodIssueHandler();
		}
		return freeFoodIssueHandler;
	}
	
	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 */
	public boolean checkValidStudentRegNo(String regNo) throws Exception{
		IExamMarksVerificationCorrectionTransaction transaction1=ExamMarksVerificationCorrectionTransactionImpl.getInstance();
		Integer studentId=transaction1.getStudentId(regNo);
		boolean isValidStudent=false;
		if(studentId!=null && studentId>0){
			isValidStudent=true;
		}
		return isValidStudent;
	}
	/**
	 * @param regNo
	 * @param Form
	 * @return
	 * @throws Exception
	 */
	public List<FreeFoodIssueTo> checkingIsStudentEligibility(String regNo,FreeFoodIssueForm Form)throws Exception{
		IFreeFoodIssueTransaction transaction=FreeFoodIssueTransactionImpl.getInstance();
		List<Object[]> boList=transaction.getStudentDetails(regNo);
		List<FreeFoodIssueTo> toList=FreeFoodIssueHelper.getInstance().convertBotoTo(boList,Form);
		if(!Form.getIsEligible()){
			Integer issuedCount=transaction.foodIssuedCount(regNo);
			Form.setFoodIssuedTimes(issuedCount);
		}
		return toList;
	}
	/**
	 * @param Form
	 * @return
	 * @throws Exception
	 */
	public boolean saveStudentDetails(FreeFoodIssueForm Form)throws Exception{
		IFreeFoodIssueTransaction transaction=FreeFoodIssueTransactionImpl.getInstance();
		FreeFoodIssueBo bo=FreeFoodIssueHelper.getInstance().convertFormToBo(Form);
		boolean isSaved=transaction.saveStudentdata(bo);
		return isSaved;
	}
	
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateEntry(FreeFoodIssueForm form) throws Exception{
		boolean isDuplicate=false;
		IFreeFoodIssueTransaction transaction=FreeFoodIssueTransactionImpl.getInstance();
		List<Object[]> boList=transaction.checkingDuplicateEntry(form.getRegisterNo());
		if(boList!=null && !boList.isEmpty()){
			Iterator<Object[]> itr=boList.iterator();
				while(itr.hasNext()){
					Object[] bo=(Object[])itr.next();
					String time=(bo[1]).toString();
					/*if(Integer.parseInt(time) <12){
						time=time+"am";
					}else{
						int time1=24-Integer.parseInt(time);
						time=String.valueOf(time1)+"pm";
					}*/
					
					form.setTime(time);
				}
			isDuplicate=true;
		}else{
			isDuplicate=false;
		}
		return isDuplicate;
	}


}
