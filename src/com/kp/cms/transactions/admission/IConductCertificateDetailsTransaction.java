package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.ConductCertificateDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ConductCertificateDetailsForm;

public interface IConductCertificateDetailsTransaction {
	public Boolean updateStudentTCDetails(List<Student> studentList,ConductCertificateDetailsForm conductCertificateDetailsForm)
	throws Exception ;
	public boolean checkCcAvailable(int studentId)
	throws Exception;
	public String getSubjectsForAllStudentsByClass(ConductCertificateDetailsForm conductCertificateDetailsForm)throws Exception;
	public ExamDefinitionBO getExamForAllStudentsByClass(ConductCertificateDetailsForm conductCertificateDetailsForm);
	public ConductCertificateDetails getStudentTCDetails(String query) throws Exception;
	public Boolean updateStudentTCDetailsEdit(ConductCertificateDetailsForm conductCertificateDetailsForm)
	throws Exception;
	public List<Student> getStudentList(String classId, String studentId) throws Exception ;
	public ConductCertificateDetails getStudentListIfCcDetailsExist(int studentId)
	throws Exception;
	public String getClourseId(String classId)throws Exception;
	public String getStudentSubjectCode(String classId) throws ApplicationException;
	public int getPtype(String classes);
	public String getStudentroleNo(String studentId);
	public String getAccDuration(String classId);
	public List<Student> getStudentListForCCEntry(ConductCertificateDetailsForm conductCertificateDetailsForm);
	public Boolean updateStudentCCNo(List<Student> studentList,ConductCertificateDetailsForm conductCertificateDetailsForm);

}
