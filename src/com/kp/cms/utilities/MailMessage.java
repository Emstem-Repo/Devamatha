package com.kp.cms.utilities;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import com.kp.cms.constants.CMSConstants;

public class MailMessage {
	/*static MimeMessage message = null;
	static MimeMessage carrierMessage = null;*/
	static Transport transport=null;
	static Transport carrierTransport=null;
	public static Session session=null;
	static Session carrierSession=null;
	static {
		Properties config = new Properties() {
			{
		/*		put("mail.smtps.auth", "true");
				put("mail.smtp.host", "smtp.gmail.com");
				put("mail.smtp.port", "465");
				put("mail.smtp.starttls.enable", "true");
				put("mail.transport.protocol", "smtps");*/
				
			/*	put("mail.smtp.host", "smtp.gmail.com");
				put("mail.smtp.socketFactory.port", "465");
				put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				put("mail.smtp.auth", "true");
				put("mail.smtp.port", "465");
				put("mail.transport.protocol", "smtps");*/
				
				put("mail.smtp.host", "smtp.live.com");
				put("mail.smtp.auth", "true");
				put("mail.transport.protocol", "smtp");
				put("mail.smtp.starttls.enable", "true");
				put("mail.smtp.port", "587");


				
			}
		};
		session = Session.getDefaultInstance(config, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(CMSConstants.MAIL_USERID,
						CMSConstants.MAIL_PASSWORD);
			}
		});
		carrierSession = Session.getDefaultInstance(config, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(CMSConstants.MAIL_USERID,
						CMSConstants.MAIL_PASSWORD);
			}
		});

		//
		// Creates email message
		//
		/*message = new MimeMessage(session);
		carrierMessage=new MimeMessage(carrierSession);*/
	}
	
    
	public static Transport initTransport() throws Exception {
			if(transport==null){
			  transport = session.getTransport("smtp");
			  transport.connect("smtp.live.com",CMSConstants.MAIL_USERID,
						CMSConstants.MAIL_PASSWORD);
			}
			return transport;
	}
	public static Transport initCarrierTransport() throws Exception {
		if(carrierTransport==null){
			carrierTransport = carrierSession.getTransport("smtp");
			carrierTransport.connect();
		}
		return carrierTransport;
	}

	
/*	*//**
	 * @return
	 *//*
	public static MimeMessage getmessage(){
		return message;
	}
	*//**
	 * @return
	 *//*
	public static MimeMessage getCarrierMessage(){
		return carrierMessage;
	}*/
	
	public static void removeSession(){
		//raghu
		/*System.out.println("*** Remove Session Entered ********");*/
		
		try {
			if(transport!=null){
					transport.close();
			}
		} catch (MessagingException e) {
			System.out.println("*** BALAJI ********");
			e.printStackTrace();
		}finally{
			session=null;
			Properties config = new Properties() {
				{
					/*put("mail.smtps.auth", "true");
					put("mail.smtp.host", "smtp.gmail.com");
					put("mail.smtp.port", "465");
					put("mail.smtp.starttls.enable", "true");
					put("mail.transport.protocol", "smtps");
					*/
					/*put("mail.smtp.host", "smtp.gmail.com");
					put("mail.smtp.socketFactory.port", "465");
					put("mail.smtp.socketFactory.class",
							"javax.net.ssl.SSLSocketFactory");
					put("mail.smtp.auth", "true");
					put("mail.smtp.port", "465");
					put("mail.transport.protocol", "smtps");*/
					
					put("mail.smtp.host", "smtp.live.com");
					put("mail.smtp.auth", "true");
					put("mail.transport.protocol", "smtp");
					put("mail.smtp.starttls.enable", "true");
					put("mail.smtp.port", "587");
					
					
					
				}
			};
			session = Session.getDefaultInstance(config, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(CMSConstants.MAIL_USERID,
							CMSConstants.MAIL_PASSWORD);
				}
			});
			
			transport=null;
		}
		//raghu
		/*System.out.println("*** Remove Session Exit ********");*/
	}
	
}