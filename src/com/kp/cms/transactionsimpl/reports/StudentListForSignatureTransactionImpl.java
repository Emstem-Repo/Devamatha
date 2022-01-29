package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.StudentListForSignatureForm;
import com.kp.cms.transactions.reports.IStudentListForSignatureTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentListForSignatureTransactionImpl implements IStudentListForSignatureTransaction {
	private static final Log log = LogFactory.getLog(StudentListForSignatureTransactionImpl.class);
	public static volatile StudentListForSignatureTransactionImpl studentListForSignatureTransactionImpl = null;
	
	public static StudentListForSignatureTransactionImpl getInstance() {
		if(studentListForSignatureTransactionImpl == null){
			studentListForSignatureTransactionImpl = new StudentListForSignatureTransactionImpl();
		}
		return studentListForSignatureTransactionImpl;
	}
	
	/**
	 * 
	 * @param stForm
	 * @return
	 * @throws Exception
	 */
	public List<Student> getStudentList(StudentListForSignatureForm stForm) throws Exception {
		log.debug("inside getStudentList");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();

			StringBuffer sqlString = new StringBuffer("from Student st" +
								 " where st.admAppln.isCancelled = 0" +
								 " and st.isActive = 1" +
								 " and st.classSchemewise.classes.id = :classId" +
								 " order by  st.rollNo,  st.registerNo");		
			
			
			Query query = session.createQuery(sqlString.toString());
			
			query.setString("classId", stForm.getClassId());

			
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
