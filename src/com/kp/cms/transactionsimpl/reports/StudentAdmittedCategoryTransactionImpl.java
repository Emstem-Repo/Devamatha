package com.kp.cms.transactionsimpl.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IStudentAdmittedCategoryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentAdmittedCategoryTransactionImpl implements
		IStudentAdmittedCategoryTransaction {

	private static final Log log = LogFactory.getLog(StudentAdmittedCategoryTransactionImpl.class);
	
	@Override
	public List<Student> getSearchedStudents(int programId, int casteId)
			throws Exception {
		Session session = null;

		try {
			/* SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
			 session = HibernateUtil.getSession(); 

			 Query query = session.createQuery("from Student st where st.isAdmitted=1 and st.admAppln.courseBySelectedCourseId.program.id= :progID and st.admAppln.personalData.caste.id= :casteID");
			 query.setReadOnly(true);
			 query.setInteger("progID", programId);
			 query.setInteger("casteID", casteId);
			 List<Student> list = query.list();

			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getting students...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}

	
	
}
