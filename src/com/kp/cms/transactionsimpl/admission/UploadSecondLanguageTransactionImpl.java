package com.kp.cms.transactionsimpl.admission;

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

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.admission.IUploadSecondLanguageTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class UploadSecondLanguageTransactionImpl implements
		IUploadSecondLanguageTransaction {
	
	private static final Log log = LogFactory.getLog(UploadSecondLanguageTransactionImpl.class);
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IUploadSecondLanguageTransaction#getAppDetails(java.lang.String)
	 */
	@Override
	public Map<String, Integer> getAppDetails(String searchQuery) throws Exception {
		Session session = null;
		Map<String,Integer> map = new HashMap<String, Integer>();
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session.createQuery(searchQuery);
			Iterator iterator = query.iterate();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj!=null){
					map.put(obj[0].toString().toUpperCase(),(Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of getAppDetails method in  UploadSecondLanguageTransactionImpl class.");
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IUploadSecondLanguageTransaction#updatePersonalData(java.util.List, java.lang.String)
	 */
	@Override
	public boolean updatePersonalData(List<PersonalDataTO> results, String user)
			throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<PersonalDataTO> tcIterator = results.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				PersonalDataTO  personalDataTO = tcIterator.next();
				PersonalData personalData=(PersonalData)session.get(PersonalData.class, personalDataTO.getId());
				if(personalData!=null){
					personalData.setSecondLanguage(personalDataTO.getSecondLanguage());
					personalData.setModifiedBy(user);
					personalData.setLastModifiedDate(new Date());
					session.update(personalData);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public Map<String, String> getSeondLanguages() throws Exception {

		Session session = null;
		Map<String,String> map = new HashMap<String, String>();
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session.createQuery("select e.name,e.name from ExamSecondLanguageMasterBO e where e.isActive=1");
			Iterator iterator = query.iterate();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj!=null){
					map.put(obj[0].toString(),obj[1].toString());
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of getAppDetails method in  UploadSecondLanguageTransactionImpl class.");
		return map;
	
	}

}
