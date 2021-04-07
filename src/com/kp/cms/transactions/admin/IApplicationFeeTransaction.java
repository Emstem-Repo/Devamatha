package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.Caste;

public interface IApplicationFeeTransaction 
{
	public List<ApplicationFee> getAppFees();
	
	public ApplicationFee isAppFeeDuplicated(ApplicationFee oldcappfee) throws Exception;
	public boolean addAppFee(ApplicationFee appFee) throws Exception;
	public boolean updateAppFee(ApplicationFee appFee) throws Exception;
	public boolean deleteAppFee(int appfeeId,String userId) throws Exception;
	public boolean reActivateAppFee(ApplicationFee appfee,String userId) throws Exception;
	public ApplicationFee getAppFeeList(int appfeeId);

}
