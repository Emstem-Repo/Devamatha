package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.forms.exam.NewInternalMarksEntryForm;
import com.kp.cms.to.exam.InternalMarksEntryTO;

public interface INewInternalMarksEntryTransaction {

	List<OpenInternalExamForClasses> openInternalExamClasses()throws Exception;

	List<Object[]> getCurrentInternalExamDetails( NewInternalMarksEntryForm internalMarksEntryForm)throws Exception;

	List getQueryForCurrentBatchStudents(int subId, List<Integer> classList, int batchId)throws Exception;

	List getAlreadyEnteredMarksForBatchStudents(int subId, int examId, String subjectType, List currentStudentList)throws Exception;

	List getDataForQuery(String marksQuery)throws Exception;

	Double getMaxMarksOfSubject(NewInternalMarksEntryForm objform)throws Exception;

	boolean saveMarks(NewInternalMarksEntryForm objform)throws Exception;

	Users getMobileNoByUserId(String userId)throws Exception;

	Double getMaxMarksDefineForSubject(InternalMarksEntryTO internalMarksEntryTO)throws Exception;
}
