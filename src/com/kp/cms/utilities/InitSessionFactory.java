package com.kp.cms.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Kalyan Chakravarthy This class guarantees that only one single
 *         SessionFactory is instantiated and that the configuration is done
 *         thread safe as singleton. Actually it only wraps the Hibernate
 *         SessionFactory. When a JNDI name is configured the session is bound
 *         to to JNDI, else it is only saved locally. You are free to use any
 *         kind of JTA or Thread transactionFactories.
 */
public class InitSessionFactory {

	/**
	 * Default constructor.
	 */
	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml  
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
	} catch (Throwable ex) {
			ex.printStackTrace();
			// Make sure you log the exception, as it might be swallowed
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static SessionFactory getInstance() {
		return sessionFactory;
	}
}