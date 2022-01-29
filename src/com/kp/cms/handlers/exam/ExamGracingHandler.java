package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ExamGracingBO;
import com.kp.cms.bo.exam.ExamGracingProcessBO;
import com.kp.cms.bo.exam.ExamGracingSubjectMarksBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExamGracingForm;
import com.kp.cms.helpers.exam.ExamGracingHelper;
import com.kp.cms.to.exam.ExamGracingSubjectMarksTo;
import com.kp.cms.to.exam.ExamGracingTo;
import com.kp.cms.transactions.admission.ITransferCertificateTransaction;
import com.kp.cms.transactions.exam.IExamGracingTransaction;
import com.kp.cms.transactionsimpl.exam.ExamGracingTransactionImpl;

public class ExamGracingHandler {
	private static volatile ExamGracingHandler examGracingHandler = null;
	
	public static ExamGracingHandler getInstance() {
		if (examGracingHandler == null) {
			examGracingHandler = new ExamGracingHandler();
		}
		return examGracingHandler;
	}

	public List<ExamGracingTo> getSudentList(ExamGracingForm graceForm) throws ApplicationException {
		IExamGracingTransaction txn = new ExamGracingTransactionImpl();
		ExamGracingHelper hlpr = new ExamGracingHelper();
		List<ExamGracingBO> boList = new LinkedList<ExamGracingBO>();
		List<ExamGracingTo> toList = new LinkedList<ExamGracingTo>();
		boList = txn.getStudentList(graceForm);
		if(boList!=null && boList.size()>0){
			toList = hlpr.convertGracingBotoTo(boList);
		}
		return toList;
	}
	public boolean gracingProcess(ExamGracingForm graceForm,List<ExamGracingTo> toList) throws Exception {
		IExamGracingTransaction txn = new ExamGracingTransactionImpl();
		boolean isSaved = false;
		ExamGracingHelper hlpr = new ExamGracingHelper();
		List<ConsolidateMarksCard> consolList = new LinkedList<ConsolidateMarksCard>();
		List<ExamGracingBO> boList = new LinkedList<ExamGracingBO>();
		List<ExamGracingSubjectMarksTo> marksTo = new LinkedList<ExamGracingSubjectMarksTo>();
		ExamGracingProcessBO markBo = new ExamGracingProcessBO();
		List<ExamGracingProcessBO> markBoList = new ArrayList<ExamGracingProcessBO>();
		Iterator<ExamGracingTo> itr1 = toList.iterator();
		
		while (itr1.hasNext()) {
			ExamGracingTo gracingTo = (ExamGracingTo) itr1.next();
			consolList  = txn.getConsolidatedMarks(gracingTo);
			txn.setSupplyId(gracingTo);
			graceForm.setTotalGraceMark(0.0);
			graceForm.setTotalSubMark(0.0);
			if(consolList!=null && consolList.size()>0){
			marksTo = hlpr.convertConsolidatedtoMarkTo(consolList,graceForm,gracingTo);
			markBo = hlpr.convertMarkToToBo(marksTo,gracingTo,graceForm);
			markBoList.add(markBo);
			}
		}
		
		isSaved = txn.saveGracing(markBoList,graceForm);
		boList = txn.getProcessedStudentList(graceForm);
		for(ExamGracingBO bo : boList){
			boolean assigned  = txn.getAssignGracing(graceForm,bo);
		}
		
		return isSaved;
	}

	public boolean checkStudent(ExamGracingForm graceForm) throws Exception {
		IExamGracingTransaction txn = new ExamGracingTransactionImpl();
		boolean isStudent = false;
		Student student = txn.getStudent(graceForm);
		if(student!=null){
		  graceForm.setCourseId(String.valueOf(student.getAdmAppln().getCourseBySelectedCourseId().getId()));	
		  graceForm.setAppliedYear(String.valueOf(student.getAdmAppln().getAppliedYear()));
		  graceForm.setStudentId(String.valueOf(student.getId()));
		  isStudent = true;
		}
		return isStudent;
	}

	public boolean alreadyExist(ExamGracingForm graceForm) throws Exception {
		IExamGracingTransaction txn = new ExamGracingTransactionImpl();
		boolean exist = false;
		ExamGracingBO bo = txn.getGracingBo(graceForm);
		if(bo!=null)
			exist=true;
		return exist;
	}

	public boolean assignGracing(ExamGracingForm graceForm) throws Exception {
		IExamGracingTransaction txn = new ExamGracingTransactionImpl();
        ExamGracingBO bo = new ExamGracingBO();
		ExamGracingHelper hlpr = new ExamGracingHelper();
		boolean assigned = false;
		bo = hlpr.convertGracingFormtoBo(graceForm);
		assigned  = txn.getAssignGracing(graceForm,bo);
		return assigned;
	}
	
}
