package com.kp.cms.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.utilities.jms.SMS_Message;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

public class SMSUtils {
	public static final String MSISDN = "MSISDN";
	public static final String PTRefId = "PTRefId";
	public static final String PTDateTime = "PTDateTime";
	public static final String Amount = "Amount";
	public static final String PTVendorId = "PTVendorId";
	public static final String PTOrderId = "PTOrderId";
	public static final String SPID = "SPID";
	public static final String TranType = "TranType";
		
	
	
	
	
	

	/*
	 * 	String destination_number; // MobileNo
	String message_body; // Message
	String message_priority;
	String sender_number; // CDMA Header
	String sender_name; // SenderID
	 */
	
	/**
	 * Response samples:
	 * MessageSent GUID="12143003" SUBMITDATE="7/15/2010 2:55:11 PM"
	 * MessageSent GUID="12143073" SUBMITDATE="7/15/2010 2:56:35 PM"
	 * 
	 * Error:
	 * Code=4343 SendSMS Pageload
	 */
	static String base_url ="";
	static {
		Properties prop = new Properties();
		try {
			InputStream in = SMSUtils.class.getClassLoader()
			.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		} catch (FileNotFoundException e) {	
		} catch (IOException e) {
		}
		base_url = prop.getProperty("smsServer");
	}
	
	/**
	 * @param messages
	 * @return
	 */
	public static List<SMS_Message> sendSMS(List<SMS_Message> messages){
		
		List<SMS_Message> retMsgs = new ArrayList<SMS_Message>();
		
		Iterator<SMS_Message> iterator = messages.iterator();
		while (iterator.hasNext()) {

			SMS_Message smsMessage = iterator.next();
			SMS_Message msg = sendSMS(smsMessage);
			retMsgs.add(msg);

		}
		return retMsgs;
	}
	
	/**
	 * @param message
	 * @return
	 */
	public static SMS_Message sendSMS(SMS_Message message) {
		
		boolean netOrIOExceptionRaised = false;
		StringBuffer response1 = null;
		StringBuffer response = null;
		try{
			String s = formatRequest(message).toString();
			response1 = send_request(false, s);
			String responseResend = formatRequestResend(response1.substring(0, 36)).toString();
			response = send_request(false, responseResend);
			System.out.println(response+"==============Message Response============");
		}catch(Throwable t){
			netOrIOExceptionRaised = true;
			t.printStackTrace();
		}
		
		SMS_Message msg = formatResponse(message, response, netOrIOExceptionRaised,response1);
		return msg;
	}
	
	/**
	 * @param msg
	 * @param response
	 * @param netOrIOException
	 * @return
	 */
	public static SMS_Message formatResponse(SMS_Message msg, StringBuffer response, boolean netOrIOException,StringBuffer response1){
		
		// Process error if any
		if (netOrIOException){
			msg.setMessage_status(SMS_Message.MESSAGE_RETRY);
			//raghu
			msg.setSms_gateway_response(response1.toString());
		}else{
			int c1 = response.indexOf("Code");
			if (c1 > 0){
				// Error returned
				msg.setSms_gateway_response(response1.toString());
				msg.setMessage_status(SMS_Message.MESSAGE_FAIL);
				
			}else{
				// 
				// MessageSent GUID="12143003" SUBMITDATE="7/15/2010 2:55:11 PM"
				int x1 = response.indexOf("Message");
				if (x1 < 0){
					// Unknown Error from gateway
					msg.setSms_gateway_response(response1.toString());
					if(response.substring(7, 11).equalsIgnoreCase("8448") || response.toString().equalsIgnoreCase("Code : submit")){
						msg.setMessage_status(SMS_Message.MESSAGE_SUCCESS);
					}
					else
					msg.setMessage_status(SMS_Message.MESSAGE_FAIL);
					
				}else{
					// Success
					//int x2 = x1 + "MessageSent".length() + 1;
					
					// /* code commented by chandra
					
					/*int x3 = response.indexOf("GUID=\"");
					int x4 = x3 + 1 + "GUID=\"".length();
					int x5 = response.indexOf("\"", x4);
					String guid = response.substring(x4, x5);*/
					
					// */ code commented by chandra
					
					int x3 = response.indexOf("GID=");
					int x4 = x3 +"GID=".length();
					int x5 = response.indexOf(" ", x4);
					
					String guid = response.substring(x4, x5); 
					msg.setSms_guid(guid);
					msg.setSms_gateway_response(response1.toString());
					msg.setMessage_status(SMS_Message.MESSAGE_SUCCESS);
				}
				
			}
		}
		return msg;
		
	}
	
	/**
	 * @param name
	 * @param value
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer getNVP(String name, String value, boolean encode) throws Exception {
		StringBuffer s = new StringBuffer();
		s.append(name);
		s.append("=");
		if (encode){
			s.append(URLEncoder.encode(value, "utf-8"));
		}else{
			s.append(value);
		}
		s.append("&");
		return s;
	}
	/**
	 * @param msg
	 * @return
	 * @throws Throwable
	 */
	public static StringBuffer formatRequest(SMS_Message msg) throws Throwable {
		
		
		/* old code
		
		StringBuffer str = new StringBuffer();
		
		// Base
		str.append(base_url);
		// commented by chandra
		//str.append(getNVP(SMS_Message.DEST_NO, msg.getDestination_number(), false));
		//str.append(getNVP(SMS_Message.MESSAGE, msg.getMessage_body(), true));
		// 
//		str.append(getNVP(SMS_Message.SENDER_NAME, msg.getSender_name(), true));
//		str.append(getNVP(SMS_Message.SENDER_NUMBER, msg.getSender_number(), false));
		
		// code added by chandra
		str.append(getNVP(SMS_Message.TO_NO, msg.getDestination_number(), false));
		str.append(getNVP(SMS_Message.NEWMESSAGE_NO, msg.getMessage_body(), true));
		return str;
	*/
		

		
		StringBuffer str = new StringBuffer();
		Properties prop = new Properties();
		InputStream stream = SMSUtils.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_PROPERTIES);
		prop.load(stream);
		String smsServer=prop.getProperty("smsServer");
		String smsServerDet=prop.getProperty("smsServerDet");
		String smsMsgStartString=prop.getProperty("smsMsgStartString");
		String smsServer1 = prop.getProperty("smsServer1");
		str.append(smsServer);
		str.append(msg.getDestination_number());
		str.append(smsServer1);
		str.append(msg.getMessage_body());
		str.append(smsMsgStartString);
		//str.append(smsServerDet);
		return str;
	
	
	}

	/**
	 * @param quiet
	 * @param urlString
	 * @return
	 * @throws Throwable
	 */
	public static StringBuffer send_request(boolean quiet, String urlString)
			throws Exception {
		
		/*
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(40000);
		byte buffer[] = new byte[8192];
		int read = 0;
		// do request
		long time = System.currentTimeMillis();
		connection.connect();
		InputStream responseBodyStream = connection.getInputStream();
		StringBuffer responseBody = new StringBuffer();
		while ((read = responseBodyStream.read(buffer)) != -1) {
			responseBody.append(new String(buffer, 0, read));
		}
		connection.disconnect();
		time = System.currentTimeMillis() - time;
		return responseBody;
	*/
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		//connection.setRequestMethod("GET");
		StringBuffer responseBody = new StringBuffer();
		byte buffer[] = new byte[8192];
		int read = 0;
		// do request
		long time = System.currentTimeMillis();
		connection.connect();
		InputStream responseBodyStream = connection.getInputStream();
		
		while ((read = responseBodyStream.read(buffer)) != -1) {
			responseBody.append(new String(buffer, 0, read));
		}
		connection.disconnect();
		time = System.currentTimeMillis() - time;
		System.out.println("=============================  MESSAGE TAKEN TIME ===============================: "+time);
		return responseBody;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static void dedactAmountFromAccount(StudentCertificateCourse bo,double feeAmount) throws Exception {
		boolean netOrIOExceptionRaised = false;
		StringBuffer response=new StringBuffer();
		try {
			String requestUrl=formatRequestForBank(bo,feeAmount).toString();
			response=send_request(false,requestUrl);
			
		}catch (TimeoutException e) {
			e.printStackTrace();
			netOrIOExceptionRaised = true;
		}catch (Exception e) {
			e.printStackTrace();
			netOrIOExceptionRaised = true;
		}
		formatResponse(bo, response, netOrIOExceptionRaised);
		PropertyUtil.getInstance().update(bo);
	}
	/**
	 * @param bo
	 * @return
	 * @throws Throwable
	 */
	public static StringBuffer formatRequestForBank(StudentCertificateCourse bo,double feeAmount) throws Exception {
		
		StringBuffer str = new StringBuffer();
		
		// Base
		str.append(CMSConstants.bankLink);
		str.append("&");
		str.append(getNVP(MSISDN,"REG"+ bo.getStudent().getRegisterNo()/*"9633300817"*/, false));
		str.append(getNVP(PTRefId,String.valueOf(bo.getId()), false));
		str.append(getNVP(PTDateTime,CommonUtil.formatDateToDesiredFormatString(CommonUtil.getTodayDate(),"dd/MM/yyyy","dd-MM-yyyy"), false));
		str.append(getNVP(Amount,String.valueOf(feeAmount)/*1.0*/ , false));// in real time uncomment feeAmount and remove 1.00 ruppee
		str.append(getNVP(PTVendorId,String.valueOf("SIB"), false));
		str.append(getNVP(PTOrderId,String.valueOf(bo.getId()), false));
		str.append(getNVP(SPID,"FEESCOLLECT", false));
		str.append(TranType+"="+"FTD");
		
		return str;
	}
	
	/**
	 * @param bo
	 * @param response
	 * @param netOrIOException
	 */
	public static void formatResponse(StudentCertificateCourse bo, StringBuffer response, boolean netOrIOException){
		boolean isPaymentFailed=true;
		// Process error if any
		if (netOrIOException || !response.toString().contains("MSISDN=")){
			bo.setStatus("Payment Failed Due to TimeOut");
			bo.setIsCancelled(true);
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

	
	public static StringBuffer formatRequestResend(String msg) throws Throwable {
				
		StringBuffer str = new StringBuffer();
		Properties prop = new Properties();
		InputStream stream = SMSUtils.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_PROPERTIES);
		prop.load(stream);
		String smsServer=prop.getProperty("smsServerForStatus");
		str.append(smsServer);
	    str.append(msg);
		return str; 
	
	
	}
	
	//raghu new
	public List<SMS_Message> sendSMSAutomatic(List<SMS_Message> listSms) {
		List<SMS_Message> retMsgs = new ArrayList<SMS_Message>();
		
		Iterator<SMS_Message> iterator = listSms.iterator();
		while (iterator.hasNext()) {

			SMS_Message smsMessage = iterator.next();
			SMS_Message msg = sendSMSAutomatic(smsMessage);
			retMsgs.add(msg);

		}
		return retMsgs;
	}
	
	public static SMS_Message sendSMSAutomatic(SMS_Message message) {
		
		boolean netOrIOExceptionRaised = false;
		StringBuffer response = null;
		try{
			String s = formatRequest(message).toString();
			response = send_request(false, s);
			
		}catch(Throwable t){
			netOrIOExceptionRaised = true;
			t.printStackTrace();
		}
		
		SMS_Message msg = formatResponseAutomatic(message, response, netOrIOExceptionRaised);
		return msg;
	}
	
	public static SMS_Message formatResponseAutomatic(SMS_Message msg, StringBuffer response, boolean netOrIOException){
		
		// Process error if any
		if (netOrIOException){
			//raghu
			msg.setSms_gateway_response(response.toString());
			msg.setMessage_status(SMS_Message.MESSAGE_RETRY);
		}else{
			
				if (response!=null && response.length()<35){
					// Unknown Error from gateway
					msg.setSms_gateway_response(response.toString());
					msg.setMessage_status(SMS_Message.MESSAGE_FAIL);
				}else if (response!=null){
										
					msg.setSms_gateway_response(response.toString());
					msg.setMessage_status(SMS_Message.MESSAGE_SUCCESS);
				}else{
					msg.setMessage_status(SMS_Message.MESSAGE_RETRY);
				}
				
			}
			return msg;
		
	}
	public static List<SMS_Message> sendSMSBulk(List<SMS_Message> messages){
		
		List<SMS_Message> retMsgs = new ArrayList<SMS_Message>();
		
		Iterator<SMS_Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			try {
				Thread.sleep(200);
				SMS_Message smsMessage = iterator.next();
				//this is for checking without msgs
				//SMS_Message msg=new SMS_Message();
				SMS_Message msg = sendSMS(smsMessage);
				retMsgs.add(msg);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return retMsgs;
	}
	
}