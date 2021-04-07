package com.kp.cms.handlers.exam;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.ModerationMarksEntryBo;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ModerationMarksEntryForm;
import com.kp.cms.forms.exam.NewStudentMarksCorrectionForm;
import com.kp.cms.helpers.exam.ModerationMarksEntryHelper;
import com.kp.cms.helpers.exam.NewStudentMarksCorrectionHelper;
import com.kp.cms.to.exam.ModerationMarksEntryTo;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewStudentMarksCorrectionTransaction;
import com.kp.cms.transactions.exam.ImoderationMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.ModerationMarksEntrtyTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewStudentMarksCorrectionTransactionImpl;

public class ModerationMarksEntryHandler {
	
	public static volatile ModerationMarksEntryHandler handler = null;
	
	public static ModerationMarksEntryHandler getInstance() {
		if (handler == null) {
			handler = new ModerationMarksEntryHandler();
		}
		return handler;
	}

	public boolean checkFinalMark(ModerationMarksEntryForm moderationForm) throws ApplicationException {
		ImoderationMarksEntryTransaction txn = new ModerationMarksEntrtyTransactionImpl();
		return txn.checkFinalMark(moderationForm);
	}

	public boolean getStudentDetails(ModerationMarksEntryForm moderationForm) throws ApplicationException {
		 ImoderationMarksEntryTransaction txn = new ModerationMarksEntrtyTransactionImpl();
		 List<ModerationMarksEntryTo> toList = new LinkedList<ModerationMarksEntryTo>(); 

		 boolean check = false;
 		 List<StudentFinalMarkDetails> finalMarkList = txn.getStudentDetails(moderationForm);//getting data from final mark details
		 Map<Integer,ModerationMarksEntryBo> moderationMap = txn.getModerationList(moderationForm);// getting data from moderation table
		 if(finalMarkList!=null && finalMarkList.size()!=0){
		   ModerationMarksEntryHelper.getInstance().convertBotoTo(moderationForm,moderationMap,finalMarkList);   
		   check = true; 
		 }
		return check;
	}
	
	public Map<Integer, SubjectMarksTO> getMaxMarksMap(ModerationMarksEntryForm mForm) throws Exception {
		String query=ModerationMarksEntryHelper.getInstance().getMaxMarksQuery(mForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		Map<Integer,SubjectMarksTO> maxMarks = ModerationMarksEntryHelper.getInstance().convertMaxMark(list);
		return maxMarks;
		
	}

	public boolean saveChangedMarks(ModerationMarksEntryForm mForm) throws Exception{
		List<ModerationMarksEntryBo> boList=ModerationMarksEntryHelper.getInstance().convertTotoBoList(mForm);
		ImoderationMarksEntryTransaction transaction=ModerationMarksEntrtyTransactionImpl.getInstance();
		//transaction.updateModifiedMarks(newStudentMarksCorrectionForm.getMarksList(),newStudentMarksCorrectionForm.getUserId(),newStudentMarksCorrectionForm.getMarksCardNo());
		return transaction.saveMarks(boList);
	}
	
	
}
