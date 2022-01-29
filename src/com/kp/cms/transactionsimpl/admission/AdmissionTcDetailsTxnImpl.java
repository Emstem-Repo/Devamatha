package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmMeritList;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admission.AdmissionTcDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.IAdmissionTcDetailsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AdmissionTcDetailsTxnImpl implements IAdmissionTcDetailsTransaction {
	private static volatile AdmissionTcDetailsTxnImpl admissionTcDetailsTxnImpl = null;
	public static AdmissionTcDetailsTxnImpl getInstance(){
		if(admissionTcDetailsTxnImpl == null){
			admissionTcDetailsTxnImpl = new AdmissionTcDetailsTxnImpl();
			return admissionTcDetailsTxnImpl;
		}
		return admissionTcDetailsTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionTcDetailsTransaction#uploadTcDetails(java.util.List)
	 */
	@Override
	public boolean uploadTcDetails(List<AdmissionTcDetails> tcDetailsList)throws Exception {
		Session session = null;
		Transaction tx =null;
		boolean isAdded = false;
		AdmissionTcDetails tcDetails =null;
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			if(tcDetailsList!=null){
				Iterator<AdmissionTcDetails> iterator = tcDetailsList.iterator();
				while (iterator.hasNext()) {
					AdmissionTcDetails admissionTcDetails = (AdmissionTcDetails) iterator .next();
					if(admissionTcDetails.getRegNo()!=null && !admissionTcDetails.getRegNo().isEmpty()){
					String str= "from AdmissionTcDetails admTc where admTc.academicYear="+admissionTcDetails.getAcademicYear()+"and admTc.regNo = '"+admissionTcDetails.getRegNo()+"'";
					Query query = session.createQuery(str);
					 tcDetails = (AdmissionTcDetails) query.uniqueResult();
					if(tcDetails == null){
						session.save(admissionTcDetails);
					   }
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
	public List<AdmissionTcDetails> getAdmTcDetailsList(StringBuffer query)
			throws Exception {
		 List<AdmissionTcDetails> tcDetails = null;
		 Session session = null;
		 try{
			 session = HibernateUtil.getSession();
			 Query qry = session.createQuery(query.toString());
			 tcDetails = qry.list();
		 }catch (Exception e) {
				throw new ApplicationException(e);
			}
		 
		return tcDetails;
	}
}
