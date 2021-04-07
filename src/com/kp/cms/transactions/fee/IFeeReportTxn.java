package com.kp.cms.transactions.fee;

import java.util.List;

/**
 * @author kalyan.c
 *
 */
public interface IFeeReportTxn {
	public List getFeePaymentsByRegistrationNoAndSems(String registrationNo) throws Exception; 
	public List getFeePaymentsByApplicationNoAndSems(String applicationNo) throws Exception;
	public List getFeePaymentsByStudentDetails(int courseID,int year,int semister,String status) throws Exception; 
}
