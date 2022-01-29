package com.kp.cms.utilities.jms;

import java.util.Hashtable;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.constants.CMSConstants;
/**
 * Jms message sender
 * 
 * JMS UTILITIES
 * 
 */

public class MailQueueSender {
	private static final Log log = LogFactory.getLog(MailQueueSender.class);
	/**
	 * @return
	 * @throws NamingException
	 */
	public static InitialContext getContext() throws NamingException {
			Hashtable props = new Hashtable();
			props.put(Context.PROVIDER_URL,CMSConstants.SENDER_PROVIDER_URL);
			props.put(Context.INITIAL_CONTEXT_FACTORY,CMSConstants.SENDER_INITIAL_CONTEXT_FACTORY);
			return new InitialContext(props);
		}
	
	/**
	 * @param mailto
	 * @return
	 */
	public boolean sendMail(MailTO mailto){
		boolean mailSent=false;
		try{ 
			InitialContext ctx = getContext();
			QueueConnectionFactory cfy = (QueueConnectionFactory) ctx.lookup(CMSConstants.SENDER_CONNECTION_FACTORY);
			QueueConnection con = cfy.createQueueConnection();

			QueueSession sess = con.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			Queue que = (Queue) ctx.lookup(CMSConstants.SENDER_QUEUE);
			QueueSender snd = sess.createSender(que);
			ObjectMessage obj =sess.createObjectMessage(); 
			obj.setObject(mailto);
			snd.setDeliveryMode(DeliveryMode.PERSISTENT);
			snd.setTimeToLive(60000);
			snd.send(obj);
			String messageID = obj.getJMSMessageID();
			log.info("messageid in sender$$$$$$$$$$"+messageID);
			snd.close();
	
			mailSent= true;
			sess.close();
			con.close();
		}catch(JMSException je)
		{
			log.error("JMSEXCEPTION>>>", je);
		}catch(NamingException ne){
			log.error("JMSEXCEPTION>>>", ne);
		}
		
		catch (Exception e) {
			log.error("JMSEXCEPTION>>>", e);
		}
		log.info("Mail sent...."+mailSent);
		return mailSent;
	}
}
