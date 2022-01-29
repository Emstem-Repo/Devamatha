package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.AttendanceSlipNumber;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceiptNumber;

public interface ILastSlipNumberTransaction {
	
	public  List<AttendanceSlipNumber> getSlipNumberDetails() throws Exception;
	public  AttendanceSlipNumber getLastSlipNumberYear(int year)throws Exception;
	List<PcFinancialYear> getFinancialYearList() throws Exception;
	public boolean addLastSlipNumber(AttendanceSlipNumber attendanceSlipNumber) throws Exception;
	public boolean deleteLastSlipNumber(int slipId, String userId)throws Exception;
	public boolean reActivateLastSlipNumber(int year, String userId)throws Exception;
	public AttendanceSlipNumber getLastSlipNumberDetailsonId(int id)throws Exception;
	public boolean updateLastSlipNumber(AttendanceSlipNumber attendanceSlipNumber)throws Exception;

}
