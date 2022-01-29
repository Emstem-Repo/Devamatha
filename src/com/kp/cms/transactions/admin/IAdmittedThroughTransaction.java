package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.forms.admin.AdmittedThroughForm;

public interface IAdmittedThroughTransaction {
	public List<AdmittedThrough> getAdmittedThrough() throws Exception;
	public boolean addAdmittedThrough(AdmittedThrough admittedThrough, String mode) throws Exception;
	public boolean deleteAdmittedThrough(int id, Boolean activate, AdmittedThroughForm admittedThroughForm) throws Exception;
	public AdmittedThrough isAdmittedThroughDuplcated(AdmittedThrough oldadmittedThrough)
	throws Exception;	
}
