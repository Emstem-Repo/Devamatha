package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.hostel.StudentReqStatusTO;
import com.kp.cms.transactions.hostel.IStudentReqStatusTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class StudentReqStatusTransactionImpl implements IStudentReqStatusTransaction{
	
	public static Log log = LogFactory.getLog(StudentReqStatusTransactionImpl.class);
	public static volatile StudentReqStatusTransactionImpl reqStatusTransactionImpl;
	
	public static StudentReqStatusTransactionImpl getInstance(){
		if(reqStatusTransactionImpl == null){
			reqStatusTransactionImpl = new StudentReqStatusTransactionImpl();
			return reqStatusTransactionImpl;
		}
		return reqStatusTransactionImpl;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HlApplicationForm> getStudentDetailsList(int hlId) throws Exception {
		log.debug("entering getStudentId in StudentReqStatusTransactionImpl class");
		Session session = null;
		List<HlApplicationForm> queryList;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			queryList = session.createQuery("from HlApplicationForm hlapp where hlapp.id ='"+hlId+"' and hlapp.isActive = 1").list();
						
		 } catch (Exception e) {
			 log.error("error occured in getStudentId StudentReqStatusTransactionImpl class...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		 log.debug("exit of getStudentId in StudentReqStatusTransactionImpl class");
		return queryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getAdmApplnId(int studentId) throws Exception {
		log.info("entering of getAdmApplId in StudentReqStatusTransactionImpl class");
		Session session = null;
		Transaction transaction = null;
		int id = 0;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			List queryList = session.createQuery("select st.admAppln.id from Student st where st.id = '"+studentId+"' and st.isActive= 1").list();
			
			if(queryList!=null && !queryList.isEmpty()){
				id = (Integer) session.createQuery("select st.admAppln.id from Student st where st.id = '"+studentId+"' and st.isActive= 1").uniqueResult();
			}
		}catch(Exception e){
			log.error("Error while getting admApplnId in StudentReqStatusTransactionImpl..",e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getAdmApplnId in StudentReqTransactionImpl class");
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getStudentId(int studentLoginId) throws Exception {
		log.info("entering of getStudentId in StudentReqStatusTransactionImpl class...");
		Session session = null;
		Transaction transaction = null;
		int id = 0;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			List queryList = session.createQuery("select stlogin.student.id from StudentLogin stlogin where stlogin.id = '"+studentLoginId+"' and stlogin.isActive= 1").list();
			
			if(queryList!=null && !queryList.isEmpty()){
				id = (Integer) session.createQuery("select stlogin.student.id from StudentLogin stlogin where stlogin.id ='"+studentLoginId+"' and stlogin.isActive= 1").uniqueResult();
			}
		}catch(Exception e){
			log.error("error occured in getStudentId of StudentReqStatusTransactionImpl...",e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit of getStudentId in StudentReqStatusTransactionImpl class...");
		return id;
	}

	@Override
	public List<StudentReqStatusTO> getStudentReqDetailsList(String query)
			throws Exception {
		log.debug("entering getStudentId in StudentReqStatusTransactionImpl class");
		Session session = null;
		List<StudentReqStatusTO> list=new ArrayList<StudentReqStatusTO>();
		try {
//			SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<HlApplicationForm> queryList = session.createQuery(query).list();
			if(queryList!=null && !queryList.isEmpty()){
				Iterator<HlApplicationForm> itr=queryList.iterator();
				while (itr.hasNext()) {
					HlApplicationForm hlApplicationForm = (HlApplicationForm) itr
							.next();
					StudentReqStatusTO reqStatusTO=new  StudentReqStatusTO();
					reqStatusTO.setId(hlApplicationForm.getId());
					reqStatusTO.setName(Integer.toString(hlApplicationForm.getRequisitionNo()));
					list.add(reqStatusTO);
				}
				
			}			
		 } catch (Exception e) {
			 log.error("error occured in getStudentId StudentReqStatusTransactionImpl class...",e);
			 session.flush();
//			 session.close();
			 throw  new ApplicationException(e);
		 }finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
		 log.debug("exit of getStudentId in StudentReqStatusTransactionImpl class");
		return list;
	
	}
}