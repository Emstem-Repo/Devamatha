package com.kp.cms.transactionsimpl.examallotment;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.examallotment.ExamRoomAllotmentSpecialization;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentSpecializationForm;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentSpecializationTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class ExamRoomAllotmentSpecializationTxnImpl implements IExamRoomAllotmentSpecializationTransactions{
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentSpecializationTxnImpl.class);
	private static volatile ExamRoomAllotmentSpecializationTxnImpl txnImpl =null;
	public static ExamRoomAllotmentSpecializationTxnImpl getInstance(){
		if(txnImpl == null){
			txnImpl = new ExamRoomAllotmentSpecializationTxnImpl();
		}
		return txnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentSpecializationTransactions#saveOrUpdateBOList(java.util.List)
	 */
	@Override
	public boolean saveOrUpdateBOList( List<ExamRoomAllotmentSpecialization> boList) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try {
			if (boList != null && !boList.isEmpty()) {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				tx.begin();
				int count = 0;
				Iterator<ExamRoomAllotmentSpecialization> iterator = boList .iterator();
				while (iterator.hasNext()) {
					ExamRoomAllotmentSpecialization bo = (ExamRoomAllotmentSpecialization) iterator .next();
					session.saveOrUpdate(bo);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}
				}
				tx.commit();
				session.flush();
				session.close();
				isAdded = true;
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return isAdded;
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentSpecializationTransactions#getAlreadyExistedBoList(java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public List<ExamRoomAllotmentSpecialization> getAlreadyExistedBoList(String midEndSem, String schemeNo)
			throws Exception {
		Session session = null;
		List<ExamRoomAllotmentSpecialization> existedBoList = null;
		String midOrEnd =null;
		try{
			if(midEndSem.equalsIgnoreCase("Mid Sem")){
				midOrEnd = "M";
			}else if(midEndSem.equalsIgnoreCase("End Sem")){
				midOrEnd = "E";
			}
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamRoomAllotmentSpecialization spe where "
							+ " spe.midOrEndSem='"+midOrEnd+"'"
							+ " and spe.schemeNo="+Integer.parseInt(schemeNo) 
							+ " and spe.isActive= 1";
			Query query =  session.createQuery(hqlQuery);
			existedBoList = query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}
		return existedBoList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentSpecializationTransactions#deleteSpecializationDetails(java.util.List, com.kp.cms.forms.examallotment.ExamRoomAllotmentSpecializationForm)
	 */
	@Override
	public boolean deleteSpecializationDetails( List<ExamRoomAllotmentSpecialization> deletingBOList,ExamRoomAllotmentSpecializationForm objForm)
			throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean isDeleted = false;
	try{
		if(deletingBOList!=null && !deletingBOList.isEmpty()){
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Iterator<ExamRoomAllotmentSpecialization> iterator = deletingBOList.iterator();
			while (iterator.hasNext()) {
				ExamRoomAllotmentSpecialization bo = (ExamRoomAllotmentSpecialization) iterator .next();
				bo.setIsActive(false);
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(objForm.getUserId());
				session.save(bo);
			}
			tx.commit();
			session.flush();
			session.close();
			isDeleted = true;
		}
	}catch (Exception e) {
		if (tx != null) {
			tx.rollback();
		}
		if (session != null) {
			session.flush();
			session.close();
		}
		return isDeleted;
	}
		return isDeleted;
	}
	
}
