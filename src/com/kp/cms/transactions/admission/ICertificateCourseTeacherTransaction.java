package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.admission.CertificateCourseTeacherForm;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;

public interface ICertificateCourseTeacherTransaction {
	public boolean addCertificateCourseTeacher(CertificateCourseTeacher certificateCourseTeacher) throws Exception;
	CertificateCourseTeacher checkDuplicate(int cerCourseId,int teacherId )throws Exception;
	public List<CertificateCourseTeacher> getList() throws Exception;
	public boolean reActive(int oldId,String userId) throws Exception;
	public CertificateCourseTeacher editCertificateCourseTeacher(String certificateCourseTeachId) throws Exception;
	public boolean deleteCertificateCourseTeacher(int certificateCourTeaId , String userId ) throws Exception;
	public boolean updateCertificateCourseTeacher(CertificateCourseTeacher certificateCourseTeacher) throws Exception;
}
