	package com.kp.cms.transactionsimpl.employee;
	
	import java.util.Date;
import java.util.List;

import javax.transaction.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.employee.InterviewRatingFactor;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.InterviewRatingFactorForm;
import com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction;
import com.kp.cms.utilities.HibernateUtil;
	
	public class InterviewRatingFactorTxnImpl implements IInterviewRatingFactorTransaction{
		private static final Log log = LogFactory.getLog(InterviewRatingFactorTxnImpl.class);
		public static volatile InterviewRatingFactorTxnImpl interviewRatingFactorTransactionImpl = null;
		public static InterviewRatingFactorTxnImpl getInstance(){
			if(interviewRatingFactorTransactionImpl == null){
				interviewRatingFactorTransactionImpl = new InterviewRatingFactorTxnImpl();
				return interviewRatingFactorTransactionImpl;
			}
			return interviewRatingFactorTransactionImpl;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction#getInterviewRatingList()
		 */
		@Override
		public List<InterviewRatingFactor> getInterviewRatingList()
				throws Exception {
			log.debug("inside getInterviewRatingList");
			Session session=null;
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from InterviewRatingFactor i where i.isActive = 1");
				List<InterviewRatingFactor> list = query.list();
				session.flush();
				log.debug("Leave getInterviewRatingList");
				return list;
			}catch (Exception e) {
				 log.error("Error in getEducationDetails...",e);
				 session.flush();
				 session.close();
				 throw  new ApplicationException(e);
			 }
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction#addInterviewRatingFactor(com.kp.cms.bo.employee.InterviewRatingFactor, java.lang.String)
		 */
		@Override
		public boolean addInterviewRatingFactor(
				InterviewRatingFactor ratingFactor, String mode) throws Exception {
			Session session=null;
			@SuppressWarnings("unused")
			org.hibernate.Transaction transaction=null;
			boolean isAdded=false;
			try{
				session=HibernateUtil.getSession();
				transaction= session.beginTransaction();
				if(mode.equalsIgnoreCase("Add")){
					session.save(ratingFactor);
				}else if(mode.equalsIgnoreCase("Edit")){
					session.update(ratingFactor);
				}
				transaction.commit();
				session.flush();
				
				isAdded=true;
			}catch (ConstraintViolationException e) {
				transaction.rollback();
				log.error("Error during saving Qualification Level data..." + e);
				throw new BusinessException(e);
			} catch (Exception e) {
				transaction.rollback();
				log.error("Error during saving Qualification Level data..." + e);
				throw new ApplicationException(e);
			}
			return isAdded;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction#isDuplicated(com.kp.cms.forms.employee.InterviewRatingFactorForm)
		 */
		@Override
		public InterviewRatingFactor isDuplicated(InterviewRatingFactorForm interviewRatingForm) throws Exception {
			Session session=null;
			@SuppressWarnings("unused")
			InterviewRatingFactor interviewRatingFactor=null;
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from InterviewRatingFactor i where i.ratingFactor = :ratingFactor and i.teaching = :teaching and i.isActive=1");
				query.setString("ratingFactor", interviewRatingForm.getRatingFactor());
				query.setBoolean("teaching", interviewRatingForm.getTeaching());
				interviewRatingFactor=(InterviewRatingFactor)query.uniqueResult();
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
			return interviewRatingFactor;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction#editInterviewRatingFactor(int)
		 */
		@Override
		public InterviewRatingFactor editInterviewRatingFactor(int id)throws Exception {
			Session session=null;
			InterviewRatingFactor ratingFactor=null;
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from InterviewRatingFactor i where i.isActive = 1 and i.id =" +id);
				ratingFactor=(InterviewRatingFactor)query.uniqueResult();
				session.flush();
			}catch (Exception exception) {
				
				 if( session != null){
					 session.flush();
					 //session.close();
				 }
				 throw new ApplicationException(exception);
		}
			return ratingFactor;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction#deleteInterviewRatingFactor(int, boolean, com.kp.cms.forms.employee.InterviewRatingFactorForm)
		 */
		@Override
		public boolean deleteInterviewRatingFactor(int id, boolean activate,InterviewRatingFactorForm ratingFactorForm) throws Exception {
			Session session=null;
			org.hibernate.Transaction tx=null;
			boolean isDeleted=false;
			try{
				session=HibernateUtil.getSession();
				tx=session.beginTransaction();
				tx.begin();
				InterviewRatingFactor interviewRatingFactor=(InterviewRatingFactor)session.get(InterviewRatingFactor.class, id);
				if(activate){
					interviewRatingFactor.setIsActive(true);
				}else{
					interviewRatingFactor.setIsActive(false);
				}
				interviewRatingFactor.setModifiedBy(ratingFactorForm.getUserId());
				interviewRatingFactor.setLastModifiedDate(new Date());
				session.update(interviewRatingFactor);
				tx.commit();
				session.flush();
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
		 * @see com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction#isDuplicatedDisplayOrder(com.kp.cms.forms.employee.InterviewRatingFactorForm)
		 */
		@Override
		public InterviewRatingFactor isDuplicatedDisplayOrder(
				InterviewRatingFactorForm interviewRatingForm) throws Exception {
			Session session=null;
			InterviewRatingFactor factor;
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from InterviewRatingFactor i where i.displayOrder = :displayOrder and i.teaching = :teaching and i.isActive=1");
				query.setInteger("displayOrder", interviewRatingForm.getDisplayOrder());
				query.setBoolean("teaching", interviewRatingForm.getTeaching());
				factor=(InterviewRatingFactor)query.uniqueResult();
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
			return factor;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.employee.IInterviewRatingFactorTransaction#isReactivate(com.kp.cms.forms.employee.InterviewRatingFactorForm)
		 */
		@Override
		public InterviewRatingFactor isReactivate(InterviewRatingFactorForm interviewRatingForm) throws Exception {
			Session session=null;
			InterviewRatingFactor factor;
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from InterviewRatingFactor i where i.ratingFactor =:ratingFactor and i.displayOrder =:displayOrder and i.teaching =:teaching and i.isActive = 0");
				query.setString("ratingFactor", interviewRatingForm.getRatingFactor());
				query.setInteger("displayOrder", interviewRatingForm.getDisplayOrder());
				query.setBoolean("teaching", interviewRatingForm.getTeaching());
				factor=(InterviewRatingFactor) query.uniqueResult();
				session.flush();
				session.close();
			}catch (Exception e) {
				log
				.error("Error during duplcation isDuplicated checking..."
						+ e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
			}
			return factor;
		}
		
	}
