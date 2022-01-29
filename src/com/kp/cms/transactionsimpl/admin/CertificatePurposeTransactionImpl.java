	package com.kp.cms.transactionsimpl.admin;
	
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
	
import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.CertificatePurposeForm;
import com.kp.cms.transactions.admin.ICertificatePurposeTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
	
	public class CertificatePurposeTransactionImpl implements ICertificatePurposeTransaction{
		private static final Log log = LogFactory.getLog(CertificatePurposeTransactionImpl.class);
		public static volatile CertificatePurposeTransactionImpl certificatePurposeTransactionImpl=null;
		public static CertificatePurposeTransactionImpl getInstance(){
			if(certificatePurposeTransactionImpl == null){
				certificatePurposeTransactionImpl= new CertificatePurposeTransactionImpl();
				return certificatePurposeTransactionImpl;
			}
			return certificatePurposeTransactionImpl;
		}
		
		/**
		 * This method will retrieve all the CertificatePurpose records.
		 */
		public List<CertificateRequestPurpose> getPurposeFields() throws Exception {
			log.debug("impl: inside getPurposeFields");
			Session session = null;
			List result ;
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = InitSessionFactory.getInstance().openSession();
				Query query = session.createQuery("from CertificateRequestPurpose c where c.isActive=1");
					
				
				List<CertificateRequestPurpose> list = query.list();
				session.flush();
				//session.close();
				// sessionFactory.close();
				log.debug("impl: leaving getPurposeFields");
				result = list;
			} catch (Exception e) {
				log.error("Error during getting Purpose..." + e);
				if (session != null) {
					session.flush();
					// session.close();
				}
				throw new ApplicationException(e);
			}
			return result;
		}

		@Override
		public CertificateRequestPurpose isDuplicateCertificatePurpose(CertificateRequestPurpose dupliPurpose)
				throws Exception {
			Session session=null;
			CertificateRequestPurpose certificateRequestPurpose;
			try{
				session=HibernateUtil.getSession();
				String query="from CertificateRequestPurpose c where c.isActive = 1 and c.purposeName=:purpose" ;
				
				Query que=session.createQuery(query);
				que.setString("purpose", dupliPurpose.getPurposeName());
				certificateRequestPurpose=(CertificateRequestPurpose) que.uniqueResult();
				session.flush();
			}catch (Exception e) {
				log.error("Error during duplcation checking..." ,e);
				session.flush();
				//session.close();
				throw new ApplicationException(e);
			}
			return certificateRequestPurpose;
		}		
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.ICertificatePurposeTransaction#addPurpose(com.kp.cms.bo.admin.CertificatePurpose, java.lang.String)
		 */
		@Override
		public boolean addPurpose(CertificateRequestPurpose certificatePurpose, String mode)throws Exception{
			Session session = null;
			Transaction transaction= null;
			boolean isAdded=false;
			try{
				session=InitSessionFactory.getInstance().openSession();
				transaction=session.beginTransaction();
				transaction.begin();
				if(mode.equalsIgnoreCase("Add")){
					session.save(certificatePurpose);
				}else if(mode.equalsIgnoreCase("Edit")){
					session.update(certificatePurpose);
				}
				transaction.commit();
				session.flush();
				session.close();
				isAdded = true;
			}catch (ConstraintViolationException e) {
				transaction.rollback();
				log.error("Error during saving admitted Through data..." ,e);
				throw new BusinessException(e);
			} catch (Exception e) {
				transaction.rollback();
				log.error("Error during saving admitted Through data..." ,e);
				throw new ApplicationException(e);
			}
			return isAdded;
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.ICertificatePurposeTransaction#deleteCertificatePurpose(int, boolean, com.kp.cms.forms.admin.CertificatePurposeForm)
		 */
		@Override
		public boolean deleteCertificatePurpose(int purposeId,
				boolean activate, CertificatePurposeForm certificatePurposeForm)
				throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isDeleted= false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			CertificateRequestPurpose certificatePurpose= (CertificateRequestPurpose)session.get(CertificateRequestPurpose.class, purposeId);
			if(activate){
				certificatePurpose.setIsActive(true);
			}else{
				certificatePurpose.setIsActive(false);
			}
			certificatePurpose.setLastModifiedDate(new Date());
			certificatePurpose.setModifiedBy(certificatePurposeForm.getUserId());
			session.update(certificatePurpose);
			transaction.commit();
			session.flush();
			//session.close();
			 isDeleted = true;
		}catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during deleting CertificatePurpose data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during deleting CertificatePurpose data..." ,e);
			throw new ApplicationException(e);
		}
		return isDeleted;
			
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.ICertificatePurposeTransaction#getPurposedetails(int)
		 */
		@Override
		public CertificateRequestPurpose getPurposedetails(int id) throws Exception {
			Session session=null;
			CertificateRequestPurpose certificatePurpose=null;
			try{
				session=HibernateUtil.getSession();
				String str="from CertificateRequestPurpose c where c.id="+id;
				Query query=session.createQuery(str);
				certificatePurpose=(CertificateRequestPurpose)query.uniqueResult();
				
			}catch(Exception e){
				log.error("Error during getting CertificatePurpose by id..." , e);
				session.flush();
				session.close();
				
			}
			return certificatePurpose;
			
		}
	}
