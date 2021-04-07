package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AdmittedThroughForm;
import com.kp.cms.forms.admin.UGCoursesForm;
import com.kp.cms.transactions.admin.IUGCoursesTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UGCoursesTransactionImpl implements IUGCoursesTransaction {
	private static final Log log = LogFactory
	.getLog(UGCoursesTransactionImpl.class);
	
public static volatile UGCoursesTransactionImpl ugCoursesTransactionImpl = null;

public static UGCoursesTransactionImpl getInstance() {
if (ugCoursesTransactionImpl == null) {
	ugCoursesTransactionImpl = new UGCoursesTransactionImpl();
	return ugCoursesTransactionImpl;
}
return ugCoursesTransactionImpl;
}

/**
* This will retrieve all the Admitted Through records from database.
* 
* @return all AdmittedThrough
*/

public List<UGCoursesBO> getUGCourses() throws Exception {
Session session = null;
try {
	//SessionFactory sessionFactory = InitSessionFactory.getInstance();
	//session = sessionFactory.openSession();
	session = HibernateUtil.getSession();
	Query query = session
			.createQuery("from UGCoursesBO a where isActive=1");
	query.setFlushMode(FlushMode.COMMIT);
	List<UGCoursesBO> list = query.list();
	//session.flush();
	//session.close();
	//sessionFactory.close();
	return list;
} catch (Exception e) {
	log.error("Error during getting UG Courses..." ,e);
	//session.flush();
	//session.close();
	throw new ApplicationException(e);
}
}

/**
* This method add new Admitted Through to Database.
* 
* @return true / flase based on result.
* @throws Exception
*/

public boolean addUGCourses(UGCoursesBO ugCourses, String mode) throws Exception {
Session session = null;
Transaction transaction = null;
try {
	//SessionFactory sessionFactory = InitSessionFactory.getInstance();
	//session = sessionFactory.openSession();
	session = HibernateUtil.getSession();
	transaction = session.beginTransaction();
	transaction.begin();
	if (mode.equalsIgnoreCase("Add")) {
		session.save(ugCourses);
	} else if (mode.equalsIgnoreCase("Edit")) {
		session.update(ugCourses);
	}
	transaction.commit();
	session.flush();
	//session.close();
	return true;
} catch (ConstraintViolationException e) {
	if(transaction!=null)
		transaction.rollback();
	log.error("Error during saving ug Courses data..." ,e);
	throw new BusinessException(e);
} catch (Exception e) {
	if(transaction!=null)
	     transaction.rollback();
	log.error("Error during saving ug Courses data..." ,e);
	throw new ApplicationException(e);
}
}

/**
* This will delete a Admitted Through from database.
* 
* @return true/false
* @throws Exception
*/

public boolean deleteUGCourses(int admId, Boolean activate, UGCoursesForm ugCoursesForm)
	throws Exception {
Session session = null;
Transaction transaction = null;
boolean result = false;
try {
	//SessionFactory sessionFactory = InitSessionFactory.getInstance();
	//session = sessionFactory.openSession();
	session = HibernateUtil.getSession();
	transaction = session.beginTransaction();
	transaction.begin();
	UGCoursesBO ugCourses = (UGCoursesBO) session.get(
			UGCoursesBO.class, admId);
	if (activate) {
		ugCourses.setIsActive(true);
	}else
	{
		ugCourses.setIsActive(false);
	}
	ugCourses.setModifiedBy(ugCoursesForm.getUserId());
	ugCourses.setLastModifiedDate(new Date());
	session.update(ugCourses);
	transaction.commit();
	session.flush();
	session.close();
	result = true;
} catch (ConstraintViolationException e) {
	if(transaction!=null)
	     transaction.rollback();
	log.error("Error during deleting UG Courses data..." ,e);
	throw new BusinessException(e);
} catch (Exception e) {
	if(transaction!=null)
		transaction.rollback();
	log.error("Error during deleting UG Courses data..." ,e);
	throw new ApplicationException(e);
}
return result;
}
/**
* duplication for admitted through
*/
public UGCoursesBO isUGCoursesDuplcated(UGCoursesBO oldaugCourses) throws Exception {
Session session = null;
UGCoursesBO ugCourses;
//AdmittedThrough result = admittedThrough = new AdmittedThrough();
try {
	//SessionFactory sessionFactory = InitSessionFactory.getInstance();
	//session = sessionFactory.openSession();
	session = HibernateUtil.getSession();
	Query query = session
			.createQuery("from UGCoursesBO a where name = :ugCourses");
	query.setString("ugCourses", oldaugCourses.getName());
	ugCourses =(UGCoursesBO)query.uniqueResult();
	session.flush();
	//session.close();
	//sessionFactory.close();
	//result = admittedThrough;
} catch (Exception e) {
	log.error("Error during duplcation checking..." ,e);
	//session.flush();
	//session.close();
	throw new ApplicationException(e);
}
return ugCourses;
}
}
