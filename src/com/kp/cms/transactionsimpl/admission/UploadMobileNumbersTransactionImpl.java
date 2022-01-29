package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.admission.IUploadMobileNumbersTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadMobileNumbersTransactionImpl implements IUploadMobileNumbersTransaction {
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IUploadSecondLanguageTransaction#updatePersonalData(java.util.List, java.lang.String)
	 */
	@Override
	public boolean uploadData(List<PersonalDataTO> results, String user)
			throws Exception {
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
					if(personalDataTO.getMobileNo1()!=null){
						personalData.setMobileNo1(personalDataTO.getMobileNo1());
					}
					if(personalDataTO.getMobileNo2()!=null){
						personalData.setMobileNo2(personalDataTO.getMobileNo2());
					}
					if(personalDataTO.getParentMob1()!=null){
						personalData.setParentMob1(personalDataTO.getParentMob1());
					}
					if(personalDataTO.getParentMob2()!=null){
						personalData.setParentMob2(personalDataTO.getParentMob2());
					}
					if(personalDataTO.getPhNo1()!=null){
						personalData.setPhNo1(personalDataTO.getPhNo1());
					}
					if(personalDataTO.getPhNo2()!=null){
						personalData.setPhNo2(personalDataTO.getPhNo2());
					}
					if(personalDataTO.getPhNo3()!=null){
						personalData.setPhNo3(personalDataTO.getPhNo3());
					}
					if(personalDataTO.getParentPh1()!=null){
						personalData.setParentPh1(personalDataTO.getParentPh1());
					}
					if(personalDataTO.getParentPh2()!=null){
						personalData.setParentPh2(personalDataTO.getParentPh2());
					}
					if(personalDataTO.getParentPh3()!=null){
						personalData.setParentPh3(personalDataTO.getParentPh3());
					}
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
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

}
