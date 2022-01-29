package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;

public interface ICertificateCourseEntryTxn {
	public boolean addCertificateCourse(CertificateCourse certificateCourse) throws Exception;
	public List<CertificateCourse> getActiveCertificateCourses(int year,String semType)throws Exception;
	public CertificateCourse isCertificateCourseNameDuplcated(CertificateCourse duplCertificateCourse) throws Exception;
	public boolean deleteCertificateCourseEntry(int id, Boolean activate, CertificateCourseEntryForm certificateCourseEntryForm) throws Exception;
	public List<Subject> getSubjects() throws Exception;
	public CertificateCourse editCertificateCourse(CertificateCourseEntryForm certificateCourseEntryForm) throws Exception;
	public boolean updateCertificateCourse(CertificateCourse certificateCourse)throws Exception;
	public List<CertificateCourse> getActiveCertificateCourses(int year)throws Exception;
	public CertificateCourse isSubjectDuplicate( CertificateCourse duplCertificateCourse) throws Exception;
}
