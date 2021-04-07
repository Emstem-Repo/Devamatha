package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.reports.StudentDetailsReportForm;

/**
 * @author dIlIp
 *
 */
public interface IStudentDetailsReportTransaction {

	public List<Student> getSearchedStudents(StringBuffer query)throws Exception;
	
	public List<String> getDeanery() throws Exception;

	public List<ExamStudentDetentionRejoinDetails> getExamStudentDetentionRejoinDetails(StudentDetailsReportForm stForm);
	

}
