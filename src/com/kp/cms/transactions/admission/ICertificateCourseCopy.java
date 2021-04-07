package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.CertificateCourseCopyForm;
import com.kp.cms.to.admission.CertificateCourseTO;

public interface ICertificateCourseCopy {
	
	public List<CertificateCourse> fromCertificateCourseCopy(int fromYear, String fromSemType) throws Exception;
    public boolean certificateCourseCopy(List<CertificateCourse> certificateCoursesList)throws Exception;
}
