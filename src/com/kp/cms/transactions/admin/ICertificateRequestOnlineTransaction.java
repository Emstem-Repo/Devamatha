package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateOnlineStudentRequest;
import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.forms.admin.CertificateRequestOnlineForm;

public interface ICertificateRequestOnlineTransaction {
	
	public List<CertificateDetails> getCertificateDetails()throws Exception;
	void updateAndGenerateRecieptNoOnlinePaymentReciept(OnlinePaymentReciepts onlinePaymentReciepts) throws Exception;
	boolean saveCertificate( List<CertificateOnlineStudentRequest> boList) throws Exception;
	public void getStudentId(int userId,CertificateRequestOnlineForm crForm) throws Exception;
	StringBuffer getSerchedStudentsQuery(CertificateRequestOnlineForm stForm) throws Exception;
	public List<CertificateOnlineStudentRequest> getSerchedStudentsCertificateApplication(StringBuffer query)	throws Exception;
	boolean saveCompletedCertificate(List<CertificateOnlineStudentRequest> cert)throws Exception;
	public String getDescription(int id)throws Exception;
	public List<CertificateDetailsTemplate> getGroupTemplates(int Id) throws Exception;
	public Student getstudentDetails(String RegNo) throws Exception;
	StringBuffer getCertificateStatus(CertificateRequestOnlineForm stForm) throws Exception;
	public List<CertificateOnlineStudentRequest> getCertificateStatus(StringBuffer query)	throws Exception;
	
}
