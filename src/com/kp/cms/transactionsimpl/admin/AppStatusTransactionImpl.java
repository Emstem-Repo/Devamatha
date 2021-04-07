package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AppStatusForm;
import com.kp.cms.transactions.admin.IAppStatusTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AppStatusTransactionImpl implements IAppStatusTransaction{
	private static final Log log = LogFactory.getLog(AppStatusTransactionImpl.class);
 public static volatile AppStatusTransactionImpl appStatusTransactionImpl = null;
 public static AppStatusTransactionImpl getInstance(){
	 if(appStatusTransactionImpl == null){
		 appStatusTransactionImpl = new AppStatusTransactionImpl();
		 return appStatusTransactionImpl;
	 }
	 return appStatusTransactionImpl;
 }
@Override
public List<ApplicationStatus> getApplicationStatus() throws Exception {
	Session session=null;
	List<ApplicationStatus> result;
	try{
		session=HibernateUtil.getSession();
		String str="from ApplicationStatus a where a.isActive=1";
		Query query=session.createQuery(str);
		result=query.list();
		session.flush();
	}catch (Exception e) {
		log.error("Error during getting Department..." + e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return result;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admin.IAppStatusTransaction#addAppStatus(com.kp.cms.bo.admin.ApplicationStatus)
 */
@Override
public boolean addAppStatus(ApplicationStatus applicationStatus,String mode) throws Exception {
	Session session= null;
	Transaction transaction=null;
	boolean isAdded=false;
	try{
		session=HibernateUtil.getSession();
		transaction=session.beginTransaction();
		transaction.begin();
		if(mode.equalsIgnoreCase("Add")){
			session.save(applicationStatus);
		}else if(mode.equalsIgnoreCase("Edit")){
			session.update(applicationStatus);
		}
		transaction.commit();
		session.flush();
		session.close();
		isAdded=true;
	}catch (ConstraintViolationException e) {
		if(transaction!=null)
		    transaction.rollback();
		log.error("Error during saving ApplicationStatus data..." ,e);
		throw new BusinessException(e);
	} catch (Exception e) {
		if(transaction!=null)
		    transaction.rollback();
		log.error("Error during saving ApplicationStatus data..." ,e);
		throw new ApplicationException(e);
	}
	return isAdded;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admin.IAppStatusTransaction#deleteAppStatus(int, com.kp.cms.forms.admin.AppStatusForm, com.kp.cms.forms.admin.AppStatusForm)
 */
@Override
public boolean deleteAppStatus(int appStatusId, AppStatusForm appStatusForm,
		boolean activate) throws Exception {
	Session session=null;
	Transaction transaction=null;
	boolean isDeleted= false;
	try{
		session=HibernateUtil.getSession();
		transaction=session.beginTransaction();
		transaction.begin();
		ApplicationStatus applicationStatus= (ApplicationStatus)session.get(ApplicationStatus.class, appStatusId);
		if(activate){
			applicationStatus.setIsActive(true);
		}else{
			applicationStatus.setIsActive(false);
		}
		applicationStatus.setLastModifiedDate(new Date());
		applicationStatus.setModifiedBy(appStatusForm.getUserId());
		session.update(applicationStatus);
		transaction.commit();
		session.flush();
		//session.close();
		 isDeleted = true;
	}catch (ConstraintViolationException e) {
		if(transaction!=null)
		     transaction.rollback();
		log.error("Error during deleting ApplicationStatus data..." ,e);
		throw new BusinessException(e);
	} catch (Exception e) {
		if(transaction!=null)
		     transaction.rollback();
		log.error("Error during deleting ApplicationStatus data..." ,e);
		throw new ApplicationException(e);
	}
	return isDeleted;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admin.IAppStatusTransaction#isDuplicateEntry(com.kp.cms.forms.admin.AppStatusForm)
 */
@Override
public ApplicationStatus isDuplicateEntry(AppStatusForm appStatusForm) throws Exception {
	Session session=null;
	ApplicationStatus applicationStatus;
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from ApplicationStatus appStatus where appStatus.name=:applicationStatus");
		query.setString("applicationStatus", appStatusForm.getApplicationStatus());
		applicationStatus=(ApplicationStatus) query.uniqueResult();
		session.flush();
	}catch (Exception e) {
		log.error("Error during duplcation checking..." ,e);
		//session.close();
		throw new ApplicationException(e);
	}
	return applicationStatus;
}
}
