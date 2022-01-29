package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.pettycash.CashCollectionForm;

public interface ICashCollectionTransaction {
	
	public int getLastReceiptNumberAndUpdate() throws Exception;
//	public List<PcFinancialYear> getAcademicYear() throws Exception;
	public List<PcAccountHead> getAccountNameWithCodeToList(String type) throws Exception;
	public List<PcReceipts>getFineDetails(String id) throws Exception;
	public List getAmount(String accId) throws Exception;
	public List<PersonalData> getStudentName(String query)throws Exception;
	public List<Object[]> getUserPrivilage(String userId) throws Exception;
	public PcFinancialYear getFinancialyearId(String financialYear) throws Exception;
	public Student getStudentBo(String query) throws Exception;
	public Integer saveCashCollection(List<PcReceipts> pcReceipts,CashCollectionForm cashCollectionForm,PcFinancialYear pcfinancialYear) throws Exception;
	//public String getStudentId(String name,String accYear,String appRollRegNo)throws Exception;
	public List<Object[]> getStartAndEndDate(Integer year) throws Exception;
	public Users getUserFromUserId(String userId) throws Exception;
	public void deletePcReceipt(int pcReceiptId,String accId, String userId)throws Exception;
	//printing the receipts related
	
	public List<PcFinancialYear> getFinancialYearList() throws Exception;
	public int getFinancialYear() throws Exception;
	public List<PcReceipts> getReceiptDetailsForEdit (int receiptNo,int financialYearId) throws Exception;
	public int getCurrentFinancialYear() throws Exception;
}
