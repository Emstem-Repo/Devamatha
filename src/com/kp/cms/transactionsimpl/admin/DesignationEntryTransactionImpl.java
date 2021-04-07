package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Designation;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.IDesignationEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;



public class DesignationEntryTransactionImpl implements  IDesignationEntryTransaction{
	
	private static final Log log = LogFactory.getLog(DesignationEntryTransactionImpl.class);
    public static volatile DesignationEntryTransactionImpl designationOrderTransactionImpl = null;

public static DesignationEntryTransactionImpl getInstance() {
if (designationOrderTransactionImpl == null) {
	designationOrderTransactionImpl = new DesignationEntryTransactionImpl();
	return designationOrderTransactionImpl;
}
return designationOrderTransactionImpl;
}

@Override
public List<Designation> getDesignationEntry() throws Exception {
	Session session = null;
	try {
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();
		//session = sessionFactory.openSession();
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from Designation a where a.isActive=1");
			List<Designation> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting Designation Entry..." ,e);
			//session.close();
			throw new ApplicationException(e);
		}
	}

@Override
public Designation isNameExist(String name, int id) throws Exception {
	Session session = null;
	Designation designation;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String sqlString = "from Designation a where name = :curName";
		if(id !=0){
			sqlString = sqlString + " and id != :curId";
		}
		Query query = session.createQuery(sqlString);
		query.setString("curName", name);
		if(id !=0){
			query.setInteger("curId", id);
		}
		
		designation = (Designation) query.uniqueResult();
		session.flush();
		//session.close();
		//sessionFactory.close();
		return designation;
	} catch (Exception e) {
		log.error("Error during duplcation checking..." , e);
		//session.close();
		throw new ApplicationException(e);
	}
}

@Override
public Designation isorderExist(String order, int id) throws Exception {
	Session session = null;
	Designation designation;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String sqlString = "from Designation a where a.order = :curorder";
		if(id !=0){
			sqlString = sqlString + " and id != :curId";
		}
		Query query = session.createQuery(sqlString);
		query.setString("curorder", order);
		if(id !=0){
			query.setInteger("curId", id);
		}
		
		designation = (Designation) query.uniqueResult();
		session.flush();
		//session.close();
		//sessionFactory.close();
		return designation;
	} catch (Exception e) {
		log.error("Error during duplcation checking..." , e);
		//session.close();
		throw new ApplicationException(e);
	}
}

@Override
public boolean addDesignationEntry(Designation designation) throws Exception {
	Session session = null;
	Transaction transaction = null;
	boolean isAdded = false;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		transaction.begin();
		session.save(designation);
		transaction.commit();
		isAdded = true;
	} catch (Exception e) {
		isAdded = false;
		log.error("Unable to addDesignationEntry" , e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
	return isAdded;
}

@Override
public Designation editDesignationEntry(int id) throws Exception {
	Session session = null;
	Designation designation = null;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from Designation c where c.id = :id");
		query.setInteger("id", id);
		designation = (Designation) query.uniqueResult();
	} catch (Exception e) {
		log.error("Unable to editDesignationEntry", e);
	} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
	return designation;
}

@Override
public boolean updatedesignationEntry(Designation designation) throws Exception {
	Session session = null;
	Transaction transaction = null;
	boolean isUpdated = false;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		transaction.begin();
		session.update(designation);
		transaction.commit();
		isUpdated = true;
	} catch (Exception e) {
		isUpdated = false;
		log.error("Unable to updatedesignationEntry", e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
	return isUpdated;
}

@Override
public boolean deleteDesignationEntry(int id, String UserId) throws Exception {
	Session session = null;
	Transaction transaction = null;
	boolean isDeleted = false;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		transaction.begin();
		Designation designation = (Designation) session.get(Designation.class, id);
		designation.setLastModifiedDate(new Date());
		designation.setModifiedBy(UserId);
		designation.setIsActive(Boolean.FALSE);
		session.update(designation);
		transaction.commit();
		isDeleted = true;
	} catch (Exception e) {
		isDeleted = false;
		log.error("Unable to delete FeeHeadings" ,e);
	} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
	return isDeleted;
}

@Override
public boolean reActivateDesignationEntry(String name, String UserId,int id) throws Exception {
	Session session = null;
	Transaction transaction = null;
	boolean isActivated = false;
	try {
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		
		Query query = session
				.createQuery("from Designation c where c.id ="+id);
		//query.setString("name", name);
		Designation designation = (Designation) query.uniqueResult();
		transaction = session.beginTransaction();
		designation.setIsActive(true);
		designation.setModifiedBy(UserId);
		designation.setLastModifiedDate(new Date());
		session.update(designation);
		transaction.commit();
		isActivated = true;
	} catch (Exception e) {
		isActivated = false;
		log.error("Unable to reActivateDesignationEntry" , e);
		if (transaction != null) {
			transaction.rollback();
		}
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
	return isActivated;
}



}

