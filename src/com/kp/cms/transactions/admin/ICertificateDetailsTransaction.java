package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.AssignCertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsRoles;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackQuestionForm;
import com.kp.cms.to.admin.CertificateDetailsTo;

public interface ICertificateDetailsTransaction
{
    public List<CertificateDetails> getCertificateList() throws Exception;

    public boolean duplicateCheck(CertificateDetailsForm certificateDetailsForm, ActionErrors actionerrors, HttpSession httpsession)throws Exception;

    public boolean addcertificateDetails(CertificateDetails certificateDetails, String s) throws Exception;

    public  CertificateDetails getcertificateDetailsById(int i) throws Exception;

    public boolean deleteCertificateDetails(int i) throws Exception;

    public boolean reActivateCertificateDetails(CertificateDetailsForm certificateDetailsForm) throws Exception;

	public List<Roles> getgetRoles() throws Exception;

	public boolean addCertificateDetails( List<CertificateDetailsRoles> certificateDetailsRoles) throws Exception ;

	public boolean addCertificateDetailsPurpose(List<AssignCertificateRequestPurpose> certificateReqPurpose) throws Exception ;

	public Map<Integer,Integer> getAssignRoles(CertificateDetailsForm certificateDetailsForm) throws Exception;
	
	public List<CertificateRequestPurpose> getPurpose() throws Exception;
	
	public Map<Integer,Integer> getAssignPurpose(CertificateDetailsForm certificateDetailsForm) throws Exception;
	
	public boolean addCertificateDetailsTemplate(CertificateDetailsTemplate certificateTemplate) throws Exception ;
	
	public String getCertificateName (int empId)throws Exception;
	
	public List<CertificateDetailsTemplate> getGroupTemplates(String templateName) throws Exception;
	
	
	public boolean saveGroupTemplate(CertificateDetailsTemplate groupTemplate) throws Exception;
	public boolean deleteGroupTemplate(CertificateDetailsTemplate groupTemplate) throws Exception;
	public List<CertificateDetailsTemplate> checkDuplicate(String templateName) throws Exception;
	public List<CertificateDetailsTemplate> getGroupTemplate(String templateName) throws Exception;
	public List<CertificateDetailsTemplate> getDuplicateCheckList(String templateName)throws Exception;
	public List<CertificateDetailsTemplate> checkDuplicateCertificateTemplate(String certName) throws Exception;
}
