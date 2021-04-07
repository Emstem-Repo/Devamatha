package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.ExamCenterForm;
import com.kp.cms.transactions.admission.IExamCenterTransactions;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamCenterTransactionImpl implements IExamCenterTransactions {
		private static final Logger log = Logger.getLogger(ExamCenterTransactionImpl.class);	
		public static volatile ExamCenterTransactionImpl examCenterTransactionImpl = null;
		
		public static ExamCenterTransactionImpl getInstance(){
			if(examCenterTransactionImpl == null){
				examCenterTransactionImpl = new ExamCenterTransactionImpl();
				return examCenterTransactionImpl;
			}
			return examCenterTransactionImpl;
		}
		
		/* used to get details to display
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IExamCenterTransactions#getExamCenterDetails()
		 */
		public List<ExamCenter> getExamCenterDetails() throws Exception{
			
			Session session = null;
			try{
				session=HibernateUtil.getSession();
				List<ExamCenter> examCenterBO= session.createQuery("from ExamCenter e where e.isActive=1").list();
				session.flush();
				return examCenterBO;
			}catch (Exception e) {
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
		}
		
		public boolean getExamCenterDefineInProgram(int pgmId) throws Exception{
			Session session = null;
			boolean isExamCentreRequired=false;
			try{
				session=HibernateUtil.getSession();
				Program pgm= (Program) session.createQuery("from Program e where e.isActive=1 and e.id="+pgmId).uniqueResult();
				session.flush();
				if(pgm!=null){
					isExamCentreRequired= pgm.getIsExamCenterRequired();
				}
				return isExamCentreRequired;
			}catch (Exception e) {
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
		}
		
		/* used to add center
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IExamCenterTransactions#addExamCenter(com.kp.cms.bo.admin.ExamCenter, java.lang.String)
		 */
		public boolean addExamCenter(ExamCenter examCenterBO,String mode) throws Exception{
			Session session = null;
			Transaction transaction = null;
			boolean result = false;
			try {
				SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				if ("Add".equalsIgnoreCase(mode)) {
					session.save(examCenterBO);
				} else {
					session.update(examCenterBO);
				}
				transaction.commit();
				session.close();
				sessionFactory.close();
				result = true;
			} catch (ConstraintViolationException e) {
				if(transaction!=null)
				      transaction.rollback();
				log.error("Error during saving ExamCenter data..." , e);
				throw new BusinessException(e);
			} catch (Exception e) {
				if(transaction!=null)
				      transaction.rollback();
				log.error("Error during saving ExamCenter data..." , e);
				throw new ApplicationException(e);
			}
			return result;
		}
		
		/* used to check duplicate
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IExamCenterTransactions#checkDuplicate(com.kp.cms.forms.admission.ExamCenterForm)
		 */
		public  ExamCenter checkDuplicate(ExamCenterForm examCenterForm) throws Exception{
			Session session = null;
			try{
				session=HibernateUtil.getSession();
				ExamCenter examCenterBO= (ExamCenter)session.createQuery("from ExamCenter e where e.program.id='"+examCenterForm.getProgramId()+"' and e.center='"+examCenterForm.getCenter()+"'").uniqueResult();
				session.flush();
				return examCenterBO;
			}
			catch (RuntimeException e) {
				throw new ApplicationException();
			}catch (Exception e) {
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
			
		}
		
		/* used to get details to edit
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IExamCenterTransactions#getExamCenterDetailsToEdit(int)
		 */
		public ExamCenter getExamCenterDetailsToEdit(int id) throws Exception{
			
			Session session = null;
			try{
				session=HibernateUtil.getSession();
				ExamCenter examCenterBO= (ExamCenter) session.createQuery("from ExamCenter e where e.id='"+id+"'").uniqueResult();
				session.flush();
				return examCenterBO;
			}
			catch (RuntimeException e) {
				throw new ApplicationException();
			}catch (Exception e) {
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
			
		}
		
		/* Used to deleting center
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IExamCenterTransactions#deleteExamCenter(int, boolean, com.kp.cms.forms.admission.ExamCenterForm)
		 */
		public boolean deleteExamCenter(int centerId, boolean activate,ExamCenterForm examCenterForm) throws Exception {
			Session session = null;
			Transaction transaction = null;
			try {
				SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				ExamCenter examCenter = (ExamCenter) session.get(ExamCenter.class, centerId);
				if(activate){
					examCenter.setIsActive(true);
				}
				else
				{
					examCenter.setIsActive(false);
				}
				examCenter.setModifiedBy(examCenterForm.getUserId());
				examCenter.setLastModifiedDate(new Date());
				session.update(examCenter);
				transaction.commit();
				session.flush();
				session.close();
				return true;
			} catch (ConstraintViolationException e) {
				if(transaction!=null)
				      transaction.rollback();
				log.error("Error during deleting ExamCenter data..." ,e);
				throw new BusinessException(e);
			} catch (Exception e) {
				if(transaction!=null)
				      transaction.rollback();
				log.error("Error during deleting ExamCenter data..." , e);
				throw new ApplicationException(e);
			}

		}
			
		
}
