package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.ApplnMandatoryBO;

public interface IApplnFormMandatoryTransactions  {
	public List<ApplnMandatoryBO> getList() throws Exception;
	public boolean updateMandatoryField(List<ApplnMandatoryBO> applnMandatoryBO) throws Exception;
}
