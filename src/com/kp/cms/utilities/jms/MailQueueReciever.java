package com.kp.cms.utilities.jms;

import java.util.Hashtable;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.utilities.CommonUtil;

/**
 * 
 * jms queue listener
 * JMS UTILITIES
 * 
 */
public class MailQueueReciever implements MessageListener {
	private static final Log log = LogFactory.getLog(MailQueueReciever.class);
	/* (non-Javadoc)
	 * listens message always
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message msg) {
		try {
			
			Object objM = ((ObjectMessage) msg).getObject();
			MailTO mailto=(MailTO) PortableRemoteObject.narrow(objM, MailTO.class);
			log.debug("Mail Addresss......"+mailto.getFromAddress());
			//uses Java mail api
			boolean mailsent=CommonUtil.sendMail(mailto);
			if(mailsent){
				msg.acknowledge();
			}
		} catch (javax.jms.JMSException e) {
			log.error("error in JMS reciever......", e);
		}
	}
	/**
	 * get initial context
	 * @return
	 * @throws NamingException
	 */
	public static InitialContext getContext() throws NamingException {
		
		Hashtable props = new Hashtable();
		props.put(Context.PROVIDER_URL,CMSConstants.RECIEVER_PROVIDER_URL);
		props.put(Context.INITIAL_CONTEXT_FACTORY,CMSConstants.RECIEVER_INITIAL_CONTEXT_FACTORY);
		return new InitialContext(props);
		}

	

}
