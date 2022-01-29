package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.forms.exam.InternalMarksEntryForm;

public interface IInternalMarksEntryImpl {

	public List<Object[]> getCurrentExamDetails(InternalMarksEntryForm objform) throws Exception;
	boolean saveMarks(InternalMarksEntryForm objform) throws Exception ;
	Double getMaxMarkOfSubject(InternalMarksEntryForm objform) throws Exception;
	List<OpenInternalExamForClasses> getOpendExamDetails() throws Exception;
	public List<Object[]> getPracticalCurrentExamDetails(
			InternalMarksEntryForm objform) throws Exception;
	public Map<Integer, String> getTeachersMap(String userId) throws Exception;

}
