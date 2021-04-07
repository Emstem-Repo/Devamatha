package com.kp.cms.handlers.pettycash;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.forms.pettycash.StudentCollectionReportForm;
import com.kp.cms.helpers.pettycash.StudentCollectionReportHelper;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.to.pettycash.StudentCollectionReportTO;
import com.kp.cms.transactions.pettycash.IStudentCollectionReportTransaction;
import com.kp.cms.transactionsimpl.pettycash.StudentCollectionReportTransactionImpl;

public class StudentCollectionReportHandler {

	
	
	private static final Log log = LogFactory.getLog(StudentCollectionReportHandler.class);
	
	private static volatile StudentCollectionReportHandler studentCollectionReportHandler;
	
	
	public static StudentCollectionReportHandler getInstance()
	{
		if(studentCollectionReportHandler==null)
		{
			studentCollectionReportHandler = new StudentCollectionReportHandler();
			return studentCollectionReportHandler;
		}
		return studentCollectionReportHandler;
		
	}
	
	public List<StudentCollectionReportTO> getDetails(StudentCollectionReportForm studentCollectionReportForm)throws Exception
	{
		IStudentCollectionReportTransaction studentCollectionreportTransaction = StudentCollectionReportTransactionImpl.getInstance();
		
		String searchCriteria = StudentCollectionReportHelper.getinstance().getSearchCriteria(studentCollectionReportForm);
		List studentCollectiondetailsList =studentCollectionreportTransaction.getStudentCollectionReport(searchCriteria);
		
		List<StudentCollectionReportTO> studentCollectiondetailsListTo =  StudentCollectionReportHelper.getinstance().convertBotoTo(studentCollectiondetailsList);
	
		return studentCollectiondetailsListTo;
	}
}
