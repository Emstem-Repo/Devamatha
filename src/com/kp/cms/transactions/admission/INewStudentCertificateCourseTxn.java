package com.kp.cms.transactions.admission;

import com.kp.cms.bo.admin.StudentCertificateCourse;

public interface INewStudentCertificateCourseTxn {

	int saveCertificateCourse( StudentCertificateCourse studentCertificateCourse,int groupId) throws Exception;

}
