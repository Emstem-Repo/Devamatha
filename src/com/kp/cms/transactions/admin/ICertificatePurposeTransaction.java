package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.forms.admin.CertificatePurposeForm;

/**
 * 
 * Interface class for Purpose Transaction
 */
public interface ICertificatePurposeTransaction {
	
	public List<CertificateRequestPurpose> getPurposeFields() throws Exception;
	
	public CertificateRequestPurpose isDuplicateCertificatePurpose(CertificateRequestPurpose dupliPurpose)throws Exception;

	public boolean addPurpose(CertificateRequestPurpose certificateRequestPurpose, String mode) throws Exception;

	public boolean deleteCertificatePurpose(int purposeId, boolean activate,
			CertificatePurposeForm certificateRequestPurposeForm)throws Exception;

	public CertificateRequestPurpose getPurposedetails(int id) throws Exception;
}
