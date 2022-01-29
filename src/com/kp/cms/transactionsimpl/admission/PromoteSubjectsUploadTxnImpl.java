package com.kp.cms.transactionsimpl.admission;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.PromoteSubjectsUploadForm;
import com.kp.cms.transactions.admission.IPromoteSubjectsUploadTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PromoteSubjectsUploadTxnImpl implements IPromoteSubjectsUploadTransaction{
	private static volatile PromoteSubjectsUploadTxnImpl uploadTxnImpl = null;
	public static PromoteSubjectsUploadTxnImpl getInstance(){
		if(uploadTxnImpl == null){
			uploadTxnImpl = new PromoteSubjectsUploadTxnImpl();
			return uploadTxnImpl;
		}
		return uploadTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPromoteSubjectsUploadTransaction#UploadPromoteSubjects(java.util.List)
	 */
	@Override
	public boolean UploadPromoteSubjects(List<PromoteSubjects> promoteSubjects)
			throws Exception {
		boolean isAdded = false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(promoteSubjects!=null){
				Iterator<PromoteSubjects> iterator = promoteSubjects.iterator();
				while (iterator.hasNext()) {
					PromoteSubjects promoteSubjects2 = (PromoteSubjects) iterator .next();
					if(promoteSubjects2!=null){
						session.save(promoteSubjects2);
					}
						
				}
				tx.commit();
				session.flush();
				isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	@Override
	public boolean isDuplicateYear(PromoteSubjectsUploadForm proSubjectsUploadForm) throws Exception {
		Session session=null;
		List<PromoteSubjects> promote= null;
		try{
			session=HibernateUtil.getSession();
			String query="from PromoteSubjects promote where promote.academicYear=:promodupl";
			
			Query que=session.createQuery(query);
			que.setString("promodupl", proSubjectsUploadForm.getAcademicYear());
			promote= que.list();
			session.flush();
			if (promote != null && !promote.isEmpty()) {
				return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			session.flush();
			//session.close();
			return false;
		}
	}
}
