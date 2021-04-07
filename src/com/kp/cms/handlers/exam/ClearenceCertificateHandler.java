package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.forms.exam.ClearenceCertificateForm;
import com.kp.cms.helpers.exam.ClearenceCertificateHelper;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.to.exam.ClearanceCertificateTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class ClearenceCertificateHandler {
	/**
	 * Singleton object of ClearenceCertificateHandler
	 */
	private static volatile ClearenceCertificateHandler clearenceCertificateHandler = null;
	private static final Log log = LogFactory.getLog(ClearenceCertificateHandler.class);
	private ClearenceCertificateHandler() {
		
	}
	/**
	 * return singleton object of ClearenceCertificateHandler.
	 * @return
	 */
	public static ClearenceCertificateHandler getInstance() {
		if (clearenceCertificateHandler == null) {
			clearenceCertificateHandler = new ClearenceCertificateHandler();
		}
		return clearenceCertificateHandler;
	}
	/**
	 * @param clearenceCertificateForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<ClearanceCertificateTO> getStudentForInput(ClearenceCertificateForm clearenceCertificateForm,
			HttpServletRequest request) throws Exception {
		List<ClearanceCertificateTO> certificateList=new ArrayList<ClearanceCertificateTO>();
		List<Integer> studentIds=new ArrayList<Integer>();
		String query=ClearenceCertificateHelper.getInstance().getCurrentClassStudentsQuery(clearenceCertificateForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			studentIds.addAll(list);
		}
		String previousQuery=ClearenceCertificateHelper.getInstance().getPreviousClassStudentsQuery(clearenceCertificateForm);
		List previousList=transaction.getDataForQuery(previousQuery);
		if(previousList!=null && !previousList.isEmpty()){
			studentIds.addAll(previousList);
		}
		Iterator<Integer> stuItr=studentIds.iterator();
		while (stuItr.hasNext()) {
			Integer studentId =stuItr.next();
			ExamBlockUnblockHallTicketBO bo=transaction.getExamBlockUnblockHallTicket(studentId,Integer.parseInt(clearenceCertificateForm.getExamId()),Integer.parseInt(clearenceCertificateForm.getClassId()),clearenceCertificateForm.getHallTicketOrMarksCard());
			if(bo!=null)
				certificateList.add(DownloadHallTicketHelper.getInstance().convertBotoTo(bo));
		}
		return certificateList;
	}
	
	/**
	 * @param clearenceCertificateForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<ClearanceCertificateTO> getBlockStudentsForInput(ClearenceCertificateForm clearenceCertificateForm,
			HttpServletRequest request) throws Exception {
		
		String query=ClearenceCertificateHelper.getInstance().getBlockedStudentsQuery(clearenceCertificateForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<ExamBlockUnblockHallTicketBO> boList=transaction.getDataForQuery(query);
		String query1=ClearenceCertificateHelper.getInstance().getBlockedStudentsQueryForOtherExam(clearenceCertificateForm);
		List<Object[]> studentIds=transaction.getDataForQuery(query1);
		Map<Integer,String> studentMap=new HashMap<Integer,String>();
		if(studentIds!=null && !studentIds.isEmpty()){
			for (Iterator iterator = studentIds.iterator(); iterator.hasNext();) {
				Object[] obj= (Object[]) iterator.next();
				if(obj[0]!=null && obj[1]!=null){
					studentMap.put(Integer.parseInt(obj[0].toString()),obj[1].toString());
				}
			}
		}
		List<ClearanceCertificateTO> certificateList=new ArrayList<ClearanceCertificateTO>();
		if(boList!=null && !boList.isEmpty()){
			Iterator<ExamBlockUnblockHallTicketBO> itr=boList.iterator();
			while (itr.hasNext()) {
				ExamBlockUnblockHallTicketBO bo =itr .next();
				if(studentMap.containsKey(bo.getStudentId())){
					clearenceCertificateForm.setMsg(bo.getStudentUtilBO().getRegisterNo()+" "+(clearenceCertificateForm.getHallTicketOrMarksCard().equalsIgnoreCase("H")?"Hall Ticket":"MarksCard")+" is Blocked For Another Exam"+studentMap.get(bo.getStudentId()));
				}else
					certificateList.add(DownloadHallTicketHelper.getInstance().convertBotoTo(bo));
			}
		}
		return certificateList;
	}
}
