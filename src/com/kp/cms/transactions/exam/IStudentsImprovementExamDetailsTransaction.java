package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.bo.exam.StudentsImprovementExamDetailsBO;
import com.kp.cms.forms.exam.StudentsImprovementExamDetailsForm;

public interface IStudentsImprovementExamDetailsTransaction {
	
	List loadClassByExamNameAndYear(StudentsImprovementExamDetailsForm form) throws Exception;
	
	public List<ExamStudentFinalMarkDetailsBO> getStudentsImpExamDetails(
			StudentsImprovementExamDetailsForm actionForm) throws Exception;
	
	public List<ExamStudentFinalMarkDetailsBO> getStudentsRegularExamMarks(
			StudentsImprovementExamDetailsForm actionForm) throws Exception ;
	
	public boolean saveStudentsImprovementExamMarksFlag(List<StudentsImprovementExamDetailsBO> boList) throws Exception;
	
	public boolean deleleteAlreadyExistedRecords(int classId)throws Exception;

	
}
