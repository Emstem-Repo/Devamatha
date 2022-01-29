package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.reports.IListofRollRegNoTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ListofRollRegNoTxnImpl implements IListofRollRegNoTransaction {

	private static final Log log = LogFactory.getLog(ListofRollRegNoTxnImpl.class);
	public static volatile ListofRollRegNoTxnImpl listofRollRegNoTxnImpl = null;

	public static ListofRollRegNoTxnImpl getInstance() {
		if (listofRollRegNoTxnImpl == null) {
			listofRollRegNoTxnImpl = new ListofRollRegNoTxnImpl();
			return listofRollRegNoTxnImpl;
		}
		return listofRollRegNoTxnImpl;
	}

	/**
	 * Is used to get the students based on program type and class
	 */
	public List<Student> getListofRollRegdNos(int progTypeId, int classId) throws Exception{
		log.debug("start getListofRollRegdNos");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session =InitSessionFactory.getInstance().openSession();	*/
			session = HibernateUtil.getSession();
		 	StringBuffer queryString = new StringBuffer();
		 	queryString.append("from Student student" +
						" where student.isActive = 1 " +
						" and  admAppln.courseBySelectedCourseId.program.programType.id = '" + progTypeId + "'");
		 	if(classId > 0){
		 		queryString.append(" and student.classSchemewise.classes.id = '" + classId + "'");	
		 	}
		 	queryString.append(" order by student.classSchemewise.classes.id, rollNo");

		 	List<Student> studentList = session.createQuery(queryString.toString()).list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("exit getListofRollRegdNos");
			return studentList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
			
}
