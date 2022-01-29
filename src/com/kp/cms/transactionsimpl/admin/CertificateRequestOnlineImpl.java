package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateOnlineStudentRequest;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.bo.exam.OnlineBillNumber;
import com.kp.cms.bo.exam.OnlinePaymentReciepts;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.forms.admin.CertificateRequestOnlineForm;
import com.kp.cms.transactions.admin.ICertificateRequestOnlineTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CertificateRequestOnlineImpl implements ICertificateRequestOnlineTransaction{
	
	private static final Logger log = Logger.getLogger(CertificateRequestOnlineImpl.class);
	 
	public List<CertificateDetails> getCertificateDetails()throws Exception {
	Session session = null;
	List<CertificateDetails> crList;
	try {
		session = HibernateUtil.getSession();
		Query query=session.createQuery("from CertificateDetails c where c.isActive=true");
		crList=query.list();
		
	} catch (Exception e) {
		throw new ApplicationException(e);
	}
	return crList;
 }
	
	public void updateAndGenerateRecieptNoOnlinePaymentReciept(
			OnlinePaymentReciepts onlinePaymentReciepts) throws Exception {
		Session session = null;
		Transaction transaction = null;
		Transaction tx = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			
			Query query = session.createQuery("from OnlineBillNumber o where o.pcFinancialYear.id = :year and o.isActive = 1").setInteger("year",onlinePaymentReciepts.getPcFinancialYear().getId());
			 if(query.list() == null || query.list().size() == 0) {
				 throw new BillGenerationException();
			 }
			 OnlineBillNumber feeBillNumber = (OnlineBillNumber)query.list().get(0);
			 onlinePaymentReciepts.setRecieptNo(Integer.parseInt(feeBillNumber.getCurrentBillNo()));
			 int feeBillNo = Integer.parseInt(feeBillNumber.getCurrentBillNo());
			 Transaction tx2=session.beginTransaction();
			 feeBillNo=feeBillNo+1;
			 feeBillNumber.setCurrentBillNo(String.valueOf(feeBillNo));
			 session.update(feeBillNumber);
			 tx2.commit();
			 
			 // code written by Mary
			boolean isExist=false;
			do{
				List<FeePayment> bos=session.createQuery("from OnlinePaymentReciepts f where f.recieptNo='"+feeBillNo+"' and f.pcFinancialYear.id="+onlinePaymentReciepts.getPcFinancialYear().getId()).list();
				if(bos!=null && !bos.isEmpty()){
					isExist=true;
					feeBillNo=feeBillNo+1;
					feeBillNumber.setCurrentBillNo(String.valueOf(feeBillNo));
				}else{
					isExist=false;
				}
			}while(isExist);
			tx=session.beginTransaction();
			tx.begin();
			feeBillNumber.setCurrentBillNo(String.valueOf(feeBillNo));
			session.merge(feeBillNumber);
			tx.commit();
			//Old Code
			Transaction tx1 = session.beginTransaction();
		//	tx1 = session.beginTransaction();
			tx1.begin();
			onlinePaymentReciepts.setRecieptNo(Integer.parseInt(feeBillNumber.getCurrentBillNo()));
			 session.merge(onlinePaymentReciepts);
			 tx1.commit();
			 transaction = session.beginTransaction();
				transaction.begin();
			session.merge(onlinePaymentReciepts);
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
		}
	}
	
	public boolean saveCertificate( List<CertificateOnlineStudentRequest> boList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(boList!=null && !boList.isEmpty()){
				Iterator<CertificateOnlineStudentRequest> itr=boList.iterator();
				while (itr.hasNext()) {
					CertificateOnlineStudentRequest bo = itr .next();
					if(bo.getId()>0)
						session.update(bo);
					else
						session.save(bo);
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	
	public void getStudentId(int userId,CertificateRequestOnlineForm crForm) throws Exception {
		Session session=null;
		String id="";
		try{
			session=HibernateUtil.getSession();
			String quer="select s.id from Student s inner join s.studentLogins log where log.id="+userId;
			Query query=session.createQuery(quer);
			id=query.uniqueResult().toString();
			crForm.setStudentId(Integer.parseInt(id));
		}catch(Exception e){
			log.error("Error while getting Employee id.." +e);
		}
	}
	
	public StringBuffer getSerchedStudentsQuery(CertificateRequestOnlineForm stForm) throws Exception {
			//List<Student> stList = new ArrayList<Student>();
				StringBuffer query = new StringBuffer("from CertificateOnlineStudentRequest cs "+
						"where cs.isActive = 1 "+
						"and cs.certificateDetailsId.id in (select r.certificateId.id from CertificateDetailsRoles r " +
						"where r.certificateRolesId.id = (select u.roles.id from Users u where u.id='"+stForm.getUserId() +"'))");
				if(stForm.getSearchType().equalsIgnoreCase("completed")){
					query = query.append(" and cs.isCompleted=1 and (cs.isRejected=0  or cs.isRejected is null)");
				}
				if(stForm.getSearchType().equalsIgnoreCase("rejected")){
					query = query.append(" and (cs.isCompleted=0  or cs.isCompleted is null) and cs.isRejected=1");
				}
				if(stForm.getSearchType().equalsIgnoreCase("pending")){
					query = query.append(" and (cs.isCompleted=0  or cs.isCompleted is null) and (cs.isRejected=0  or cs.isRejected is null)");
				}
				if(stForm.getSearchType().equalsIgnoreCase("issued")){
					query = query.append(" and cs.isCompleted=1 and cs.isIssued=1");
				}
						
				if (stForm.getTempFirstName() != null && !StringUtils.isEmpty(stForm.getTempFirstName().trim())) 
				{
					query = query.append(" and cs.studentId in (select s.id from Student s where s.admAppln.personalData.firstName LIKE '%"
										+ stForm.getTempFirstName() + "%')");
				}
				if (stForm.getCertificateId() != null && !StringUtils.isEmpty(stForm.getCertificateId().trim())) 
				{
						query = query.append(" and cs.certificateDetailsId.id='"+ stForm.getCertificateId() + "'");
				}
				if (stForm.getTempRegisterNo() != null && !StringUtils.isEmpty(stForm.getTempRegisterNo().trim())) 
				{				
					query = query.append(" and cs.studentId.registerNo='"+stForm.getTempRegisterNo()+"'" );
				}
				if(stForm.getStartDate() != null && !stForm.getStartDate().isEmpty() && stForm.getEndDate() != null && !stForm.getEndDate().isEmpty()){
					Date startDate = CommonUtil.ConvertStringToSQLDate(stForm.getStartDate());
					Date endDate = CommonUtil.ConvertStringToSQLDate(stForm.getEndDate());
					query = query.append(" and cs.appliedDate between '"+startDate+"' and '"+ endDate+"'");
				}
				query.append(" order by cs.appliedDate");
			return query;
			}
			
			public List<CertificateOnlineStudentRequest> getSerchedStudentsCertificateApplication(StringBuffer query)
				throws Exception {
			Session session = null;
			List<CertificateOnlineStudentRequest> stList;
			try {
				session = HibernateUtil.getSession();
				Query queri = session.createQuery(query.toString());
				stList = queri.list();
			} catch (Exception e) {
				throw new ApplicationException(e);
			}
			return stList;
			}
			
	
			public boolean saveCompletedCertificate(List<CertificateOnlineStudentRequest> cert) throws Exception {
				Session session=null;
				boolean flag=false;
				Transaction transaction=null;
				CertificateOnlineStudentRequest cc;
				try{
					session = InitSessionFactory.getInstance().openSession();
					transaction = session.beginTransaction();
					transaction.begin();
					Iterator<CertificateOnlineStudentRequest> tcIterator = cert.iterator();
					
					while(tcIterator.hasNext()){
						cc = tcIterator.next();
						session.saveOrUpdate(cc);
					}
					transaction.commit();
					session.flush();
					log.debug("leaving saveCompletedCertificate");
					flag=true;
				}catch(Exception e){
					if(transaction!=null)
					     transaction.rollback();
					throw new ApplicationException(e);
				}finally{
					if(session!=null){
					session.flush();
					}
				}
				return flag;
			}
			
			public String getDescription(int id)throws Exception {
				Session session = null;
				String desc = "";
				try {
					session = HibernateUtil.getSession();
					Query query=session.createQuery("select c.description from CertificateDetails c where c.id="+id+" and  c.isActive=true");
					desc=(String)query.uniqueResult();
					
				} catch (Exception e) {
					throw new ApplicationException(e);
				}
				return desc;
			}
			
			public List<CertificateDetailsTemplate> getGroupTemplates(int id) throws Exception {
				Session session = null;
				List<CertificateDetailsTemplate> cert = null;
				String sqlQuery="";
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					 session = HibernateUtil.getSession();
					 if (id>0) {
						 sqlQuery="from CertificateDetailsTemplate t where t.isActive=1 and t.id="+ id ;
					} 
					 Query query=session.createQuery(sqlQuery);
					 cert =  query.list();
					 
					 return cert;
				 } catch (Exception e) {
					 log.debug("Txn Impl : Leaving getGroupTemplates with Exception"+e.getMessage());
					 throw e;
				 }finally{
					if (session != null) {
						session.flush();
						//session.close();
					}
				 }
			}
			
		public Student getstudentDetails(String RegNo) throws Exception{
			Session session = null;
			Student cert = null;
			String sqlQuery="";
			try {
				 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
				 session = HibernateUtil.getSession();
				 if (RegNo!=null && !RegNo.isEmpty()) {
					 sqlQuery="from Student s where s.isActive=1 and s.registerNo="+ RegNo ;
				} 
				 Query query=session.createQuery(sqlQuery);
				 cert =  (Student)query.uniqueResult();
				 
				 return cert;
			 } catch (Exception e) {
				 log.debug("Txn Impl : Leaving getstudentDetails with Exception"+e.getMessage());
				 throw e;
			 }finally{
				if (session != null) {
					session.flush();
					//session.close();
				}
			 }
		}
		
	public StringBuffer getCertificateStatus(CertificateRequestOnlineForm stForm) throws Exception{
			StringBuffer query = new StringBuffer("from CertificateOnlineStudentRequest cs where cs.isActive = 1");
			if (stForm.getStudentId() >0) 
			{				
				query = query.append(" and cs.studentId="+stForm.getStudentId());
			}
			query.append(" order by cs.appliedDate");
		return query;
		}
		
		public List<CertificateOnlineStudentRequest> getCertificateStatus(StringBuffer query)
			throws Exception {
		Session session = null;
		List<CertificateOnlineStudentRequest> stList;
		try {
			session = HibernateUtil.getSession();
			Query queri = session.createQuery(query.toString());
			stList = queri.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return stList;
		}
		
}
