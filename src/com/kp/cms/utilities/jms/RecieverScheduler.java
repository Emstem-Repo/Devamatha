package com.kp.cms.utilities.jms;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.constants.CMSConstants;
/**
 * On startup, starts message listener
 *
 * JMS UTILITIES
 * 
 */
public class RecieverScheduler extends Thread {
	private static final Log log = LogFactory.getLog(RecieverScheduler.class);
	private static QueueConnection con=null;
	@Override
	public void run() {
	
		 try{
				log.info("initializing mail reciever servelet.....");
				final InitialContext ctx1 = MailQueueReciever.getContext();
				if(con==null){
					final QueueConnectionFactory cf1 = (QueueConnectionFactory) ctx1.lookup(CMSConstants.RECIEVER_CONNECTION_FACTORY);
					con = cf1.createQueueConnection();
				}
				final QueueSession sess = con.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
				final Queue que = (Queue) ctx1.lookup(CMSConstants.RECIEVER_QUEUE);
				
				final QueueReceiver rcv = sess.createReceiver(que);
				
				rcv.setMessageListener(new MailQueueReciever());
				//c1.setExceptionListener(new MessageExceptionListner());
				con.start();
				log.info("mail reciever started.....");
				}
		 		catch (Exception e) {
					log.error("mail reciever init exception.....",e);
				}
		 		
		
	}

}
