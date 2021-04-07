package com.kp.cms.transactions.reports;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.exceptions.ApplicationException;

public interface IViewInternalMarksTxn {
	public Map<Integer, String> getClassesByYear(int year, int teacherId);
	public Map<Integer, String> getSubjectByYear(int year, int classId, int teacherId);
	public List<MarksEntryDetails> getStudentWiseExamMarkDetailsView(int subjectId, int classId, int year, int teacherId) throws ApplicationException;
	
	

}
