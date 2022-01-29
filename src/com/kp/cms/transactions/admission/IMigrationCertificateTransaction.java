package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.StudentCertificateDetails;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.admission.MigrationCertificateForm;

public interface IMigrationCertificateTransaction {
	
	public Student verifyRegisterNumberAndGetDetails(MigrationCertificateForm certificateForm) throws Exception;

	public StudentCertificateDetails checkForAlreadyPrinted(MigrationCertificateForm certificateForm) throws Exception;

	public boolean saveMigrationCertificate(StudentCertificateDetails details) throws Exception;

	public void getStudentAcademicDetails(MigrationCertificateForm certificateForm) throws Exception;

	public boolean saveMigrationCertificateCurrentNumber(MigrationCertificateForm certificateForm) throws Exception;

	public ExamStudentDetentionRejoinDetails verifyStudentDetentionDiscontinued(int stuId) throws Exception;

}
