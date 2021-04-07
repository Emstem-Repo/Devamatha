package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.hostel.BiometricBo;
import com.kp.cms.bo.hostel.HostelVisitorsInfoBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelVisitorsInfoForm;
import com.kp.cms.transactions.hostel.IHostelVisitorsInfoTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class HostelVisitorsInfoTransactionImpl implements IHostelVisitorsInfoTransaction{
	private static Log log = LogFactory.getLog(HostelVisitorsInfoTransactionImpl.class);
	/**
	 * instance()
	 */
	public static volatile HostelVisitorsInfoTransactionImpl hostelVisitorsInfoTransactionImpl = null;
	public static HostelVisitorsInfoTransactionImpl getInstance() {
		if (hostelVisitorsInfoTransactionImpl == null) {
			hostelVisitorsInfoTransactionImpl = new HostelVisitorsInfoTransactionImpl();
			return hostelVisitorsInfoTransactionImpl;
		}
		return hostelVisitorsInfoTransactionImpl;
	}
	@Override
	public String getHlAdmissionId(String academicYear, String regNo)
			throws Exception {
		Session session = null;
		String hlAdmId=null;
		int academicyear=Integer.parseInt(academicYear);
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlAdmissionBo h where h.isActive=1 and h.academicYear="+academicyear+"" +
					" and h.studentId.registerNo='"+regNo+"'");
			HlAdmissionBo hlAdmissionBo=(HlAdmissionBo)query.uniqueResult();
			session.flush();
			if(hlAdmissionBo!=null){
				hlAdmId=String.valueOf(hlAdmissionBo.getId());
			}
			return hlAdmId;
			
		} catch (Exception e) {
			log.debug("Error during duplcation checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public boolean addHostelVisitorsInfo(
			HostelVisitorsInfoBo hostelVisitorsInfoBo) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
				session.save(hostelVisitorsInfoBo);
				transaction.commit();
				flag=true;
		}catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}finally{
			if(session !=null){
				session.flush();
				}
		}
		return flag;
	}
	@Override
	public List<HostelVisitorsInfoBo> getVisitorsList(String regNo) throws Exception {
		List<HostelVisitorsInfoBo> listVisitorsInfoBos=null;
		Session session = null;
		try{
			int i;
			session = HibernateUtil.getSession();
			String hqlquery=null;
			if(regNo!=null && !regNo.isEmpty()){
				hqlquery="from HostelVisitorsInfoBo h where h.isActive=1 and h.hlAdmissionId.studentId.registerNo="+regNo;
			}else{
				return listVisitorsInfoBos;
			}
			Query query = session.createQuery(hqlquery);
			listVisitorsInfoBos=query.list();
			session.flush();
		}catch (Exception e) {
			log.debug("Error during duplcation checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return listVisitorsInfoBos;
	}
	@Override
	public HlAdmissionBo getRegisterNo(int id) throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlAdmissionBo h where h.id="+id);
			HlAdmissionBo admissionBo=(HlAdmissionBo) query.uniqueResult();
			
			session.flush();
			return admissionBo;
			
		} catch (Exception e) {
			log.debug("Error during duplcation checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public boolean deleteVisitorsInfo(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from HostelVisitorsInfoBo h where h.id="+id;
      	  HostelVisitorsInfoBo hostelVisitorsInfoBo=(HostelVisitorsInfoBo)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    hostelVisitorsInfoBo.setIsActive(false);
      	    session.update(hostelVisitorsInfoBo);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting biometricDetails data...", e);
      }
return true;
		
	}
	@Override
	public HostelVisitorsInfoBo getVisitorsInfoById(int id) throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HostelVisitorsInfoBo h where h.id="+id);
			HostelVisitorsInfoBo hostelVisitorsInfoBo=(HostelVisitorsInfoBo) query.uniqueResult();
			
			session.flush();
			return hostelVisitorsInfoBo;
			
		} catch (Exception e) {
			log.debug("Error during duplcation checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public boolean updateHostelVisitorsInfo(
			HostelVisitorsInfoBo hostelVisitorsInfoBo) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
				session.update(hostelVisitorsInfoBo);
				transaction.commit();
				flag=true;
		}catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}finally{
			if(session !=null){
				session.flush();
				}
		}
		return flag;
	}

}
