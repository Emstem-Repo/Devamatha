package com.kp.cms.transactionsimpl.admission;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.AdmLoanLetterDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AdmLoanLetterForm;
import com.kp.cms.to.admission.AdmLoanLetterDetailsTO;
import com.kp.cms.transactions.admission.IAdmLoanLetterTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class AdmLoanLetterTransactionImpl implements IAdmLoanLetterTransaction {
	
	private static volatile AdmLoanLetterTransactionImpl admLoanLetterTransactionImpl = null;
	private static final Log log = LogFactory.getLog(AdmLoanLetterTransactionImpl.class);
	
	private AdmLoanLetterTransactionImpl() {
		
	}
	
	public static AdmLoanLetterTransactionImpl getInstance() {
		if (admLoanLetterTransactionImpl == null) {
			admLoanLetterTransactionImpl = new AdmLoanLetterTransactionImpl();
		}
		return admLoanLetterTransactionImpl;
	}
	
	public List<Student> getStudentListBO(String query) throws Exception 
	{
		Session session = null;
		List<Student> admApplnBOList = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			admApplnBOList = session.createQuery(query).list();
			return admApplnBOList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	public List<Object[]> getStudentDetails(String query) throws Exception 
	{
		Session session = null;
		List<Object[]> admApplnBOList = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			admApplnBOList = session.createQuery(query).list();
			return admApplnBOList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
	public boolean addLoan(AdmLoanLetterForm letterForm) throws Exception {
		Session session = null;
		boolean flagSet=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(letterForm.getStudentInfoList()!=null && !letterForm.getStudentInfoList().isEmpty()){
				Iterator<AdmLoanLetterDetailsTO> itr=letterForm.getStudentInfoList().iterator();
				while (itr.hasNext()) {
					AdmLoanLetterDetailsTO admLoanLetterDetailsTO = (AdmLoanLetterDetailsTO) itr.next();
					
					if(admLoanLetterDetailsTO.isTempChecked() && admLoanLetterDetailsTO.getChecked1()!=null &&
							admLoanLetterDetailsTO.getChecked1().equalsIgnoreCase("On")){
					
						String quer="from AdmLoanLetterDetails a where a.admApplnId.id="+admLoanLetterDetailsTO.getAdmApplnId();
						Query query=session.createQuery(quer);
						AdmLoanLetterDetails details=(AdmLoanLetterDetails)query.uniqueResult();
						if(details!=null && !details.toString().isEmpty()){
							details.setIsLoanLetterIssued(true);
							details.setLoanLetterIssuedOn(details.getLoanLetterIssuedOn());
							details.setIsActive(true);
							details.setLastModifiedDate(new Date());
							details.setModifiedBy(letterForm.getUserId());
							session.update(details);
						}
						else
						{
							AdmLoanLetterDetails det= new AdmLoanLetterDetails();
							AdmAppln appln = new AdmAppln();
							appln.setId(admLoanLetterDetailsTO.getAdmApplnId());
							det.setAdmApplnId(appln);
							det.setIsLoanLetterIssued(true);
							det.setLoanLetterIssuedOn(new Date());
							det.setIsActive(true);
							det.setCreatedBy(letterForm.getUserId());
							det.setCreatedDate(new Date());
							det.setLastModifiedDate(new Date());
							det.setModifiedBy(letterForm.getUserId());
							session.save(det);
						}
					}else if(!admLoanLetterDetailsTO.isTempChecked() && admLoanLetterDetailsTO.getChecked1()!=null &&
							!admLoanLetterDetailsTO.getChecked1().equalsIgnoreCase("On")){
						
						String quer="from AdmLoanLetterDetails a where a.admApplnId.id="+admLoanLetterDetailsTO.getAdmApplnId();
						Query query=session.createQuery(quer);
						AdmLoanLetterDetails details=(AdmLoanLetterDetails)query.uniqueResult();
						if(details!=null && !details.toString().isEmpty()){
							details.setIsLoanLetterIssued(false);
							details.setIsActive(true);
							details.setLastModifiedDate(new Date());
							details.setModifiedBy(letterForm.getUserId());
							session.update(details);
						}
						else
						{
							AdmLoanLetterDetails det=(AdmLoanLetterDetails)query.uniqueResult();
							AdmAppln appln = new AdmAppln();
							appln.setId(admLoanLetterDetailsTO.getAdmApplnId());
							det.setAdmApplnId(appln);
							det.setIsLoanLetterIssued(false);
							det.setIsActive(true);
							det.setCreatedBy(letterForm.getUserId());
							det.setCreatedDate(new Date());
							det.setLastModifiedDate(new Date());
							det.setModifiedBy(letterForm.getUserId());
							session.save(det);
						}
					}
		
		}
			}
			flagSet=true;
			txn.commit();
			return flagSet;
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while updating selected candidates data.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
			
		}
	}

}