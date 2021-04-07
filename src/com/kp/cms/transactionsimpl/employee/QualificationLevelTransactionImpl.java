package com.kp.cms.transactionsimpl.employee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.QualificationLevelForm;
import com.kp.cms.transactions.employee.IQualificationLevelTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class QualificationLevelTransactionImpl implements IQualificationLevelTransaction {
	private static final Log log = LogFactory.getLog(QualificationLevelTransactionImpl.class);
	public static volatile QualificationLevelTransactionImpl qualificationLevelTransactionImpl=null;
	public static QualificationLevelTransactionImpl getInstance(){
		if(qualificationLevelTransactionImpl == null){
			qualificationLevelTransactionImpl = new QualificationLevelTransactionImpl();
			return qualificationLevelTransactionImpl;
		}
		return qualificationLevelTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IQualificationLevelTransaction#getQualificationLevel()
	 */
	public List<QualificationLevelBO> getQualificationLevel() throws Exception {
		log.debug("inside getQualificationLevel");
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from QualificationLevelBO q where q.isActive = 1");
			List<QualificationLevelBO> list= query.list();
			session.flush();
			log.debug("Leave getQualificationLevel");
			return list;
		}catch (Exception e) {
			 log.error("Error in getEducationDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IQualificationLevelTransaction#isDuplicated(com.kp.cms.forms.employee.QualificationLevelForm)
	 */
	@Override
	public QualificationLevelBO isDuplicated(
			QualificationLevelForm qualificationLevelForm) throws Exception {
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from QualificationLevelBO q where q.name = :name and q.isActive=1");
			query.setString("name", qualificationLevelForm.getName());
			QualificationLevelBO bo=(QualificationLevelBO) query.uniqueResult();
			session.flush();
			session.close();
			return bo;
		}catch (Exception exception) {
			log
			.error("Error during duplication isDuplicated checking..."
					+ exception);
		session.flush();
		session.close();
		throw new ApplicationException(exception);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IQualificationLevelTransaction#addQualificationLevel(com.kp.cms.bo.admin.QualificationLevelBO, java.lang.String)
	 */
	@Override
	public boolean addQualificationLevel(QualificationLevelBO bo, String mode)throws Exception {
		Session session=null;
		boolean result=false;
		Transaction tx=null;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(bo);
			}else if (mode.equalsIgnoreCase("Edit")){
				session.update(bo);
			}
			tx.commit();
			session.flush();
			session.close();
			result=true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Qualification Level data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Qualification Level data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IQualificationLevelTransaction#deleteQualificationLevel(int, boolean, com.kp.cms.forms.employee.QualificationLevelForm)
	 */
	@Override
	public boolean deleteQualificationLevel(int id, boolean activate,QualificationLevelForm qualificationLevelForm) throws Exception {
		Session session=null;
		Transaction tx=null;
		boolean isDeleted=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			QualificationLevelBO qualificationLevelBO=(QualificationLevelBO)session.get(QualificationLevelBO.class, id);
			if(activate){
				qualificationLevelBO.setIsActive(true);
			}else{
				qualificationLevelBO.setIsActive(false);
			}
			qualificationLevelBO.setModifiedBy(qualificationLevelForm.getUserId());
			qualificationLevelBO.setLastModifiedDate(new Date());
			session.update(qualificationLevelBO);
			tx.commit();
			session.flush();
			session.close();
			isDeleted=true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteQualificationLevel..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteQualificationLevel.." , e);
			throw new ApplicationException(e);
		}
		return isDeleted;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IQualificationLevelTransaction#editQualificationLevel(int)
	 */
	@Override
	public QualificationLevelBO editQualificationLevel(int id)
			throws Exception {
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String str="from QualificationLevelBO q where q.isActive = 1 and q.id=" +id;
			Query query=session.createQuery(str);
			QualificationLevelBO list=(QualificationLevelBO) query.uniqueResult();
			session.flush();
			return list;
		}catch (Exception exception) {
			
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
	}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IQualificationLevelTransaction#isDuplicateDisplayOrder(com.kp.cms.forms.employee.QualificationLevelForm)
	 */
	@Override
	public QualificationLevelBO isDuplicateDisplayOrder(QualificationLevelForm qualificationLevelForm) throws Exception {
		Session session=null;
		QualificationLevelBO bo;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from QualificationLevelBO q where q.displayOrder = :displayOrder and q.isActive=1 ");
			query.setInteger("displayOrder", qualificationLevelForm.getDisplayOrder());
			bo=(QualificationLevelBO) query.uniqueResult();
			session.flush();
			session.close();
		}catch (Exception exception) {
			log
			.error("Error during duplcation isDuplicated checking..."
					+ exception);
		session.flush();
		session.close();
		throw new ApplicationException(exception);
		}
		return bo;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IQualificationLevelTransaction#isReactivate(com.kp.cms.forms.employee.QualificationLevelForm)
	 */
	@Override
	public QualificationLevelBO isReactivate(QualificationLevelForm qualificationLevelForm) throws Exception {
		Session session=null;
		QualificationLevelBO bo;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from QualificationLevelBO q where q.name = :name and q.displayOrder = :displayOrder and q.isActive=0 ");
			query.setString("name", qualificationLevelForm.getName());
			query.setInteger("displayOrder", qualificationLevelForm.getDisplayOrder());
			bo=(QualificationLevelBO) query.uniqueResult();
			session.flush();
			session.close();
		}catch (Exception exception) {
			log
			.error("Error during duplcation isDuplicated checking..."
					+ exception);
		session.flush();
		session.close();
		throw new ApplicationException(exception);
		}
		return bo;
	}
	
	
}
