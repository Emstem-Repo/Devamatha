package com.kp.cms.transactionsimpl.admission;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admission.AdmissionSubject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.IAdmissionSubjectTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AdmissionStudentImpl  implements IAdmissionSubjectTransaction {
	private static volatile AdmissionStudentImpl admissionStudentImpl = null;
	public static AdmissionStudentImpl getInstance(){
		if(admissionStudentImpl == null){
			admissionStudentImpl = new AdmissionStudentImpl();
			return admissionStudentImpl;
		}
		return admissionStudentImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionSubjectTransaction#uploadTcDetails(java.util.List)
	 */
	@Override
	public boolean uploadTcDetails(List<AdmissionSubject> admissionSubject) throws Exception {
		Session session = null;
		Transaction tx =null;
		boolean isAdded = false;
		AdmissionSubject AdmSubject=null;
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			if(admissionSubject!=null){
				Iterator<AdmissionSubject> iterator = admissionSubject.iterator();
				while (iterator.hasNext()) {
					AdmissionSubject admSubject=(AdmissionSubject) iterator .next();
					if(admSubject.getClasses()!=null && !admSubject.getClasses().isEmpty()){
					String str= "from AdmissionSubject admSub where admSub.academicYear="+admSubject.getAcademicYear()+"and admSub.classes = '"+admSubject.getClasses()+"'";
					Query query = session.createQuery(str);
					AdmSubject = (AdmissionSubject) query.uniqueResult();
					if(AdmSubject == null){
						session.save(admSubject);
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
}
