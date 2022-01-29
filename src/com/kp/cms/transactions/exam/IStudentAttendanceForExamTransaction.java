package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.forms.exam.StudentAttendanceForExamForm;
import com.kp.cms.to.exam.StudentMarksTO;

public interface IStudentAttendanceForExamTransaction {

	List getDataForQuery(String currentStudentQuery, List<Integer> idList) throws Exception;

	List<String> getDataForMarksQuery(String marksQuery,List<Integer> idList) throws Exception;

	boolean saveMarks(List<StudentMarksTO> stuList,StudentAttendanceForExamForm studentAttendanceForExamForm) throws Exception;
	
	List getDataForQuery1(String currentStudentQuery, List<String> registerNoList) throws Exception;
	
	public List<Object[]> getStuExamDetails(String stuExamDetailsQuery) throws Exception;
	
	
}
