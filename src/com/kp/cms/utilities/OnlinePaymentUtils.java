package com.kp.cms.utilities;

import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.constants.CMSConstants;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

public class OnlinePaymentUtils {
	public static final String MSISDN = "MSISDN";
	public static final String PTRefId = "PTRefId";
	public static final String PTDateTime = "PTDateTime";
	public static final String Amount = "Amount";
	public static final String PTVendorId = "PTVendorId";
	public static final String PTOrderId = "PTOrderId";
	public static final String SPID = "SPID";
	public static final String TranType = "TranType";
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static void dedactAmountFromAccount(String registerNo,int id,double feeAmount,OnlinePaymentReciepts bo) throws Exception {
		boolean netOrIOExceptionRaised = false;
		StringBuffer response=new StringBuffer();
		try {
			String requestUrl=formatRequestForBank(registerNo,id,feeAmount).toString();
			System.out.println("URL : "+requestUrl );
			response=SMSUtils.send_request(false,requestUrl);
			
		}catch (TimeoutException e) {
			e.printStackTrace();
			netOrIOExceptionRaised = true;
		}catch (Exception e) {
			e.printStackTrace();
			netOrIOExceptionRaised = true;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		formatResponse(bo, response, netOrIOExceptionRaised);
		PropertyUtil.getInstance().update(bo);
	}
	/**
	 * @param bo
	 * @return
	 * @throws Throwable
	 */
	public static StringBuffer formatRequestForBank(String  registerNo,int id,double feeAmount) throws Exception {
		
		StringBuffer str = new StringBuffer();
		
		// Base
		str.append(CMSConstants.bankLink);
		str.append("&");
		str.append(SMSUtils.getNVP(MSISDN,"REG"+ registerNo/*"9633300817"*/, false));
		str.append(SMSUtils.getNVP(PTRefId,String.valueOf(id), false));
		str.append(SMSUtils.getNVP(PTDateTime,CommonUtil.formatDateToDesiredFormatString(CommonUtil.getTodayDate(),"dd/MM/yyyy","dd-MM-yyyy"), false));
		str.append(SMSUtils.getNVP(Amount,String.valueOf(feeAmount)/*1.0*/ , false));// in real time uncomment feeAmount and remove 1.00 ruppee
		str.append(SMSUtils.getNVP(PTVendorId,String.valueOf("SIB"), false));
		str.append(SMSUtils.getNVP(PTOrderId,String.valueOf(id), false));
		str.append(SMSUtils.getNVP(SPID,"FEESCOLLECT", false));
		str.append(TranType+"="+"FTD");
		
		return str;
	}
	
	/**
	 * @param bo
	 * @param response
	 * @param netOrIOException
	 */
	public static void formatResponse(OnlinePaymentReciepts bo, StringBuffer response, boolean netOrIOException){
		boolean isPaymentFailed=true;
		// Process error if any
		if (netOrIOException || !response.toString().contains("MSISDN=")){
			bo.setStatus("Payment Failed Due to TimeOut");
			bo.setIsPaymentFailed(isPaymentFailed);
		}else{
			int x1=response.indexOf("MSISDN=");
			int x2 = x1 + "MSISDN=".length();
			int x3 = response.indexOf("&", x2);
			
			int x4=x3+1+"PTRefId=".length();
			int x5 = response.indexOf("&", x4);
			
			int x6=x5+1+"PTDateTime=".length();
			int x7 = response.indexOf("&", x6);
			bo.setTransactionDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(response.substring(x6,x7), "dd-MM-yyyy","dd/MM/yyyy")));
			
			int x8=x7+1+"status=".length();
			int x9 = response.indexOf("&", x8);
			String status=response.substring(x8,x9);
			if(status.equalsIgnoreCase("000") /*|| status.equalsIgnoreCase("412") || status.equalsIgnoreCase("414") || status.equalsIgnoreCase("409")*/){
				bo.setStatus("success");	
				isPaymentFailed=false;				
			}else if(status.equalsIgnoreCase("114")){
				bo.setStatus("Invalid Account Number");
			}else if(status.equalsIgnoreCase("116")){
				bo.setStatus("Insufficient Balance");
			}else if(status.equalsIgnoreCase("119")){
				bo.setStatus("Transaction Not allowed");
			}else if(status.equalsIgnoreCase("121")){
				bo.setStatus("with drawal limit exceeded");
			}else if(status.equalsIgnoreCase("902")){
				bo.setStatus("Invalid function code");
			}else if(status.equalsIgnoreCase("909")){
				bo.setStatus("system Malfunction");
			}else if(status.equalsIgnoreCase("913")){
				bo.setStatus("Duplicate transaction");
			}else
				bo.setStatus("Payment Failed");
				
			int x10=x9+1+"BankConfirmationId=".length();
			int x11= response.length();
			bo.setBankConfirmationId(response.substring(x10,x11));
			bo.setIsPaymentFailed(isPaymentFailed);
		}
	}
}
