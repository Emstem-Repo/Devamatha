package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.AdminMarksCardForm;
import com.kp.cms.helpers.exam.AdminMarksCardHelper;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.helpers.exam.ProgressCardHelper;
import com.kp.cms.to.admin.ProgressCardTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.exam.ProgressCardStudentDetailTO;
import com.kp.cms.transactions.exam.IDownloadHallTicketTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.IProgressCardTransaction;
import com.kp.cms.transactionsimpl.admin.ProgressCardImpl;
import com.kp.cms.transactionsimpl.exam.DownloadHallTicketTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.MarkComparator;

public class AdminProgressCardHandler {
	private static AdminProgressCardHandler adminProgressCardHandler=null;

	public static AdminProgressCardHandler getInstance(){
		if (adminProgressCardHandler==null) {
			adminProgressCardHandler=new AdminProgressCardHandler();
		}
		return adminProgressCardHandler;
	}

	public List<ProgressCardStudentDetailTO> getStudentListByClass(AdminMarksCardForm adminMarksCardForm)throws Exception
	{
		IProgressCardTransaction feePaymentTransaction = ProgressCardImpl.getInstance();
		List<Integer> detainedOrDiscontinuedStudents = ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();

		List<Student> studentList =feePaymentTransaction.getStudentsByClassName(adminMarksCardForm, detainedOrDiscontinuedStudents);
		List<ProgressCardStudentDetailTO> feeStudentList = ProgressCardHelper.getInstance().studentDetailstoTO(studentList);
		return feeStudentList;
	}

	public List<ProgressCardTo> getStudentForInput( AdminMarksCardForm adminMarksCardForm,HttpServletRequest request) throws Exception{
		IDownloadHallTicketTransaction transaction1= DownloadHallTicketTransactionImpl.getInstance();
		List<ProgressCardTo> progressCardList=new ArrayList();
		String marksCardQuery=ProgressCardHelper.getInstance()
			.getQueryForUGStudentMarksCard(Integer.parseInt(adminMarksCardForm.getClassId()),
					Integer.parseInt(adminMarksCardForm.getStudentId()), Integer.parseInt(adminMarksCardForm.getYear()));
	List<Object[]> ugMarksCardData=transaction1.getStudentHallTicket(marksCardQuery);

	if(ugMarksCardData.size()>0)
		progressCardList= ProgressCardHelper.getInstance()
		.getMarksCardForUg(ugMarksCardData,Integer.parseInt(adminMarksCardForm.getStudentId()));
	return progressCardList;
	}

}
