package com.kp.cms.transactions.hostel;
import java.util.List;

import com.kp.cms.bo.admin.HlFeeType;
import com.kp.cms.bo.admin.HlFees;
public interface IHostelFeeTransactions {
	public List<HlFeeType> getFeeListToDisplay() throws Exception;
	public List<HlFees> getFeeDetailedListToDisplay() throws Exception;
	public List<HlFees> getFeeDetailedListToView(int hostelId,int roomTypeId) throws Exception;
	public boolean deleteFeeDetails(int hostelId, int roomId) throws Exception ;
	public String saveFeeDetails(List<HlFees> hlFeeList)throws Exception;
	public List<HlFees> getFeeDetailedListForActive(int hostelId,int roomTypeId) throws Exception;
	public boolean reActivateFeeList(List<HlFees> hlFeeBoList)throws Exception;
	public boolean updateFeeDetails(List<HlFees> hlFeesBo) throws Exception;

}
