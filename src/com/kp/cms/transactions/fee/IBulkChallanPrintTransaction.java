package com.kp.cms.transactions.fee;

import java.util.List;

import com.kp.cms.bo.admin.FeePayment;

public interface IBulkChallanPrintTransaction {

	List<FeePayment> getChallanData(String fromDate, String toDate) throws Exception;

	byte[] getLogoById(int accId) throws Exception;

}
