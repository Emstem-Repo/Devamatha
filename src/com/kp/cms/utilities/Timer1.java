package com.kp.cms.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.kp.cms.bo.admin.MobileMessagingSchedule;
import com.kp.cms.handlers.admin.SheduledSMSHandler;
import com.kp.cms.utilities.jms.SMS_Message;


public class Timer1 {
	public static String dt;
	public void timeout() {
        try {
        	SMSUtil s =new SMSUtil();
        	SMSUtils smsUtils=new SMSUtils();
        	ConverationUtil converationUtil=new ConverationUtil();
        	List<SMS_Message> list=converationUtil.convertBotoTO(s.getListOfSMS());
        	List<SMS_Message> mobList=smsUtils.sendSMS(list);
        	s.updateSMS(converationUtil.convertTotoBO(mobList));
        	List<SMS_Message> shedulList = converationUtil.convertSheduleBOtoTO(s.getMessageList());
        	List<SMS_Message> moList=smsUtils.sendSMS(shedulList);
        	s.updateSMSForSchedule(converationUtil.convertTotoBOForSchedule(moList));
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	/**
	 * @param args
	 */
	public static void sms() {
		Timer t = new Timer();
		final Timer1 t1=new Timer1();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				t1.timeout();
			}
		},20000,60000);

	}
	
}
