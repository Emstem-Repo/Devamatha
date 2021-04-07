package com.kp.cms.handlers.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.forms.hostel.HostelTransactionForm;
import com.kp.cms.helpers.hostel.HostelTransactionHelper;
import com.kp.cms.to.hostel.HostelTransactionTo;
import com.kp.cms.transactions.exam.IExamMarksVerificationCorrectionTransaction;
import com.kp.cms.transactions.hostel.IHostelTransactions;
import com.kp.cms.transactionsimpl.exam.ExamMarksVerificationCorrectionTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelTransactionImpl;


public class HostelTransactionHandler {
	private static final Log log=LogFactory.getLog(HostelTransactionHandler.class);
	public static volatile HostelTransactionHandler hostelTransactionHandler=null;
	public static HostelTransactionHandler getInstance()
	{
		if(hostelTransactionHandler==null)
		{
			hostelTransactionHandler=new HostelTransactionHandler();
			return hostelTransactionHandler;
		}
		return hostelTransactionHandler;
	}
	public List<HostelTransactionTo> getstudentDetails(HostelTransactionForm hlTransactionForm)throws Exception{
		IHostelTransactions transaction=HostelTransactionImpl.getInstance();
		List<HlAdmissionBo> BOList=transaction.getStudentList(hlTransactionForm);
		List<HostelTransactionTo> studentDetailList=HostelTransactionHelper.getInstance().convertBotoTo(BOList);
		return studentDetailList;
	}
	
	public boolean checkValidStudentRegNo(HostelTransactionForm hlTransactionForm) throws Exception{
		IExamMarksVerificationCorrectionTransaction transaction1=ExamMarksVerificationCorrectionTransactionImpl.getInstance();
		Integer studentId=transaction1.getStudentId(hlTransactionForm.getRegno());
		boolean isValidStudent=false;
		if(studentId!=null && studentId>0){
			isValidStudent=true;
		}
		return isValidStudent;
	}
}
