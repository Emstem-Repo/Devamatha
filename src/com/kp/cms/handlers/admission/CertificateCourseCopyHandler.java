package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.forms.admission.CertificateCourseCopyForm;
import com.kp.cms.helpers.admission.CertificateCourseCopyHelper;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.transactions.admission.ICertificateCourseCopy;
import com.kp.cms.transactionsimpl.admission.CertificateCourseCopyTxnImpl;

public class CertificateCourseCopyHandler {

	public static volatile CertificateCourseCopyHandler certificateCourseCopyHandler = null;

	public static CertificateCourseCopyHandler getInstance() {
		if (certificateCourseCopyHandler == null) {
			certificateCourseCopyHandler = new CertificateCourseCopyHandler();
			return certificateCourseCopyHandler;
		}
		return certificateCourseCopyHandler;
	}

	/**
	 * @param copyForm
	 * @return
	 * @throws Exception
	 */
	public boolean certificateCourseCopy(CertificateCourseCopyForm copyForm)
			throws Exception {
		ICertificateCourseCopy transactions = CertificateCourseCopyTxnImpl
				.getInstance();
		List<CertificateCourse> fromCertificateCourses = transactions
				.fromCertificateCourseCopy(Integer.valueOf(copyForm
						.getFromAcademicYear()), copyForm.getFromSemType());
		List<CertificateCourse> toCertificateCourses = transactions
				.fromCertificateCourseCopy(Integer.valueOf(copyForm
						.getToAcademicYear()), copyForm.getToSemType());
		List<CertificateCourse> tocourse = CertificateCourseCopyHelper
				.getInstance().copyFromCourseToToCourse(fromCertificateCourses,
						toCertificateCourses, copyForm);
		return transactions.certificateCourseCopy(tocourse);

	}

}
