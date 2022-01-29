package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.forms.admin.AdmBioDataCJCForm;
import com.kp.cms.to.admin.AdmBioDataCJCTo;

public interface IAdmBioDataCJCTransaction {

	public boolean addAdmBioData(List<AdmBioDataCJC> list, AdmBioDataCJCForm admBioDataForm)throws Exception;

}
