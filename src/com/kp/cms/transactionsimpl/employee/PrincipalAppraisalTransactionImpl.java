package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpAppraisal;
import com.kp.cms.bo.admin.EmpAppraisalDetails;
import com.kp.cms.bo.admin.EmpAttribute;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.PrincipalAppraisalForm;
import com.kp.cms.to.employee.AppraisalsTO;
import com.kp.cms.transactions.employee.IPrincipalAppraisalTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class PrincipalAppraisalTransactionImpl implements IPrincipalAppraisalTransaction {
	private static final Log log = LogFactory.getLog(PrincipalAppraisalTransactionImpl.class);

	/**
	 * Gets all emp attributes
	 */
	public List<EmpAttribute> getAllEmpAttributes(boolean isEmployee) throws Exception {
		log.info("Inside of getAllEmpAttributes of PrincipalAppraisalTransactionImpl");
		Session session = null;
		List<EmpAttribute> attList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			if(isEmployee){
				attList = session.createQuery("from EmpAttribute e where e.isActive = 1 and" +
					" e.isEmployee = 1 order by e.id").list();
			}
			else{
				attList = session.createQuery("from EmpAttribute e where e.isActive = 1 and" +
				" e.isEmployee = 0 order by e.id").list();
			}
			log.info("End of getAllEmpAttributes of PrincipalAppraisalTransactionImpl");
			return attList;
		} catch (Exception e) {
			log.error("Error occured in getAllEmpAttributes of PrincipalAppraisalTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/**
	 * Submits the appraisal both for principal
	 */
	public boolean submitAppraisal(EmpAppraisal appraisal) throws Exception {
		log.info("Inside of submitAppraisal of PrincipalAppraisalTransactionImpl");
		Session session = null;
		Transaction transaction = null;		
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			session.save(appraisal);
			transaction.commit();
			log.info("End of submitAppraisal of PrincipalAppraisalTransactionImpl");
			return true;
		} catch (Exception e) {
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Error occured in submitAppraisal of PrincipalAppraisalTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
       public List<EmpAppraisal> getAppraisals(){
		
		Session session=null;
		List<EmpAppraisal> appraisalList=null;
		EmpAppraisal appraisalDetails=null;
		
		try{
			session=HibernateUtil.getSession();
			String query="from EmpAppraisal e   ";
			Query appraisals=session.createQuery(query);
		  
			appraisalList=appraisals.list();
			 /*Iterator it=appraisalList.iterator();
			 while(it.hasNext()){
				AppraisalsTO appraisal=new AppraisalsTO();
				
				 appraisalDetails=(EmpAppraisal) it.next();
				 //appraisalDetails1=(EmpAppraisalDetails) it.next();
				//appraisal.setAttributeName(appraisalDetails.getEmpAttribute().getName());
				//appraisal.setAttributeValue(appraisalDetails.getAttributeValue());
				//appraisal.setDepartmentName(appraisalDetails.getEmpAppraisal().getDepartment().getName());
				//appraisal.setCreatedBy(appraisalDetails.getCreatedBy());
				//appraisal.setRecomand(appraisalDetails.getEmpAppraisal().getRecommendation());
				//appraisal.setDetailsId(appraisalDetails.getId());
				appraisal.setAppraisalId(appraisalDetails.getId());
				appraisal.setDepartmentName(appraisalDetails.getDepartment().getName());
				appraisal.setEmployeeName(appraisalDetails.getEmployeeByAppraiseEmpId().getFirstName());
				appraisalTO.add(appraisal);
			 }*/
			 
		}catch(Exception e){
			e.printStackTrace();
		}
		return appraisalList;
	}
	public EmpAppraisal getAppraisalDetails(PrincipalAppraisalForm principalAppraisal){
		EmpAppraisal empAppraisals=null;
		
		try{
		String query="from EmpAppraisal ea where ea.id="+principalAppraisal.getAppraiseId();
		Session session=HibernateUtil.getSession();
		Query getAppraisals=session.createQuery(query);
		empAppraisals=(EmpAppraisal)getAppraisals.uniqueResult();
		
		}catch(Exception e){
			
		}
		return empAppraisals;
	}
}
