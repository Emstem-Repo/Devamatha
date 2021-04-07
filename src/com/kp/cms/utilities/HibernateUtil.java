package com.kp.cms.utilities;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.exceptions.ApplicationException;

/* @author Kalyan
 *
 * 
 * Hibernate utility to get Session Factory
 */
public class HibernateUtil {

	// private static final SessionFactory sessionFactory;
	//
	// static {
	// try {
	// // Create the SessionFactory from hibernate.cfg.xml
	// sessionFactory = new Configuration().configure()
	// .buildSessionFactory();
	// } catch (Throwable ex) {
	// // Make sure you log the exception, as it might be swallowed
	// System.err.println("Initial SessionFactory creation failed." + ex);
	// throw new ExceptionInInitializerError(ex);
	// }
	// }

	public static SessionFactory getSessionFactory() {
		return InitSessionFactory.getInstance();
	}

	private static final SessionFactory sessionFactory;
	private static final ThreadLocal threadSession = new ThreadLocal();
	private static final ThreadLocal threadTransaction = new ThreadLocal();
	static {
		sessionFactory = InitSessionFactory.getInstance();

	}

	public static void closeSession() {
		try {
			Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null && s.isOpen())
				s.close();

		} catch (HibernateException ex) {
			// throw new ApplicationException(ex);
		}
	}

	public static Session getSession() throws ApplicationException {
		Session s = (Session) threadSession.get();
		// Open a new Session, if this thread has none yet
		try {
			if (s == null || !s.isOpen()) {
				s = sessionFactory.openSession();
				threadSession.set(s);
			}
		} catch (HibernateException ex) {
			
			throw new ApplicationException(ex);
		}
		return s;
	}

}
