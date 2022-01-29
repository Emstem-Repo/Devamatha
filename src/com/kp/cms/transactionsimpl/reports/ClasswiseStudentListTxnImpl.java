package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IClasswiseStudentListTxn;
import com.kp.cms.utilities.HibernateUtil;

public class ClasswiseStudentListTxnImpl implements IClasswiseStudentListTxn{
	private static final Log log = LogFactory.getLog(ClasswiseStudentListTxnImpl.class);
	public static volatile ClasswiseStudentListTxnImpl classwiseStudentListTxnImpl = null;
	
	public static ClasswiseStudentListTxnImpl getInstance() {
		if(classwiseStudentListTxnImpl == null){
			classwiseStudentListTxnImpl = new ClasswiseStudentListTxnImpl();
		}
		return classwiseStudentListTxnImpl;
	}
	
	/**
	 * 
	 * @param searchCriteria
	 * @return
	 * @throws Exception
	 */
	public List<Student> getStudentList(String searchCriteria) throws Exception {
		log.debug("inside getStudentList");
		
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery(searchCriteria);
			List<Student> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getStudentList");
			return list;
		} catch (Exception e) {
			log.error("Error during getting getStudentList..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}
	
}
