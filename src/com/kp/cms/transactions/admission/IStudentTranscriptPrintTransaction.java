package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.StudentCertificateDetails;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.admission.StudentTranscriptPrintForm;

public interface IStudentTranscriptPrintTransaction {
	
	public Student verifyRegisterNumberAndGetDetails(StudentTranscriptPrintForm certificateForm) throws Exception;
	
	public StudentCertificateDetails checkForAlreadyPrinted(StudentTranscriptPrintForm certificateForm) throws Exception;
	
	public boolean saveStudentMarksCardsPrint(StudentCertificateDetails details) throws Exception;
	
	public boolean saveStudentCertificateNumberCurrentNumber(StudentTranscriptPrintForm certificateForm) throws Exception;

	public int getClassId(int studentId,int schemeNo) throws Exception;

	public List<Object[]> getStudentMarks(String consolidateQuery, int stuId) throws Exception;
	
	List<String> getSupplimentaryAppeared(int studentId) throws Exception;

	public void getStudentAcademicDetails(StudentTranscriptPrintForm certificateForm) throws Exception;

	public ExamStudentDetentionRejoinDetails verifyStudentDetentionDiscontinued(int stuId) throws Exception;

}
