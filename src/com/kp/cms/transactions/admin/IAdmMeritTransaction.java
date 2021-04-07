package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AdmFeeMain;
import com.kp.cms.bo.admin.AdmMeritList;
import com.kp.cms.forms.admin.AdmMeritListForm;

public interface IAdmMeritTransaction {
	public boolean addAdmMeritList(List<AdmMeritList> list,AdmMeritListForm admMeritForm)throws Exception;
	public boolean addAdmFeeMain(List<AdmFeeMain> list,AdmMeritListForm admMeritForm)throws Exception;
	public List<AdmMeritList> getAdmMeritList(StringBuffer query)throws Exception;
}
