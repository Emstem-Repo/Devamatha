package com.kp.cms.transactionsimpl.hostel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.helpers.hostel.HostelReservationHelper;
import com.kp.cms.to.hostel.VRequisitionsTO;
import com.kp.cms.transactions.hostel.IRequisitionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class RequisitionTransactionImpl  implements IRequisitionTransaction{
	private static final Log log = LogFactory.getLog(RequisitionTransactionImpl.class);
	
	
	@Override
	public List<Object[]> getRequisitionDetails(String dynamicQuery)throws Exception {
	
		
		log.info("Entering into RequisitionTransactionImpl--- getRequisitionDetails");
		Session session = null;
		List<Object[]> requisitionList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			requisitionList = session.createQuery(dynamicQuery).list();
		} catch (Exception e) {		
			log.error("Exception occured in getting all getRequisitionDetails Details in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("Leaving into RequisitionTransactionImpl --- getRequisitionDetails");
		return requisitionList;
		
		
	}
	@Override
	public List<HlApplicationForm> getRequisitionDetailsToShow(String dynamicQuery)
			throws Exception {
		log.info("Entering into RequisitionTransactionImpl--- getRequisitionDetailsToShow");
		Session session = null;
		List<HlApplicationForm> requisitionList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			requisitionList = session.createQuery(dynamicQuery).list();
					} catch (Exception e) {		
			log.error("Exception occured in getting all getRequisitionDetailsToShow Details in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("Leaving into RequisitionTransactionImpl --- getRequisitionDetailsToShow");
		return requisitionList;
	}
	@Override
	public boolean markAsApprove(List<VRequisitionsTO> list1) {
		log.debug("TXN Impl : Entering markAsPaid");
		Session session = null;
		Properties prop = new Properties();
		try {
			InputStream in = HostelReservationHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(in);
			session = HibernateUtil.getSession();
			Iterator<VRequisitionsTO> itr = list1.iterator();
			int count = 0 ;
			Transaction tx = session.beginTransaction();
			tx.begin();
			while(itr.hasNext()) {
				VRequisitionsTO vto=itr.next();
				int statusId=0;
               String status=vto.getStatus();
               if(status.equalsIgnoreCase("Approved"))
               {
            	   statusId=Integer.parseInt(prop.getProperty("knowledgepro.hostel.status.Approved.id"));
               }
               if(status.equalsIgnoreCase("pending"))
               {
            	   statusId=Integer.parseInt(prop.getProperty("knowledgepro.hostel.status.pending.id"));
               }
               if(status.equalsIgnoreCase("Rejected"))
               {
            	   statusId=Integer.parseInt(prop.getProperty("knowledgepro.hostel.status.Rejected.id"));
               }
               HlApplicationForm hlApplicationForm=(HlApplicationForm)session.get(HlApplicationForm.class,vto.getId());
              	if(hlApplicationForm.getHlHostelByHlApprovedHostelId()==null){
              		hlApplicationForm.setHlHostelByHlApprovedHostelId(hlApplicationForm.getHlHostelByHlAppliedHostelId());
              	}
              	if(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId()==null){
              		hlApplicationForm.setHlRoomTypeByHlApprovedRoomTypeId(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId());
              	}
              	HlStatus hlStatus=new HlStatus();
              	hlStatus.setId(statusId);
              	hlApplicationForm.setHlStatus(hlStatus);
              	session.update(hlApplicationForm);
		   		if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}	
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.debug("TXN Impl : Leaving markAsPaid with success");	
		
		} catch (Exception e) {
			log.debug("TXN Impl : Leaving markAsPaid with Exception");
			
		}
		
		return true;
		}

	
			@Override
		public boolean updateStatus(int hlId, int rid, int hid, String status)
				throws Exception {
			log.debug("TXN Impl : Entering updateStatus");
			Session session = null;
			try {
				/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session =sessionFactory.openSession();*/
				session = HibernateUtil.getSession();
				
				int statusId=0;
	                 if(status.equalsIgnoreCase("Approved"))
	               {
	                	 statusId=1;
	               }
	               if(status.equalsIgnoreCase("pending"))
	               {
	            	   statusId=9;
	               }
	               if(status.equalsIgnoreCase("Rejected"))
	               {
	            	   statusId=10;
	               }	
				Transaction tx = session.beginTransaction();
				tx.begin();
					Query query = session.createQuery("update HlApplicationForm set hlStatus = :statusId,hlRoomTypeByHlApprovedRoomTypeId= :rid,hlHostelByHlApprovedHostelId= :hid" +
					 						" where id = :id");
					query.setInteger("id",hlId );
					query.setInteger("statusId", statusId);
					query.setInteger("rid", rid);
					query.setInteger("hid", hid);
				query.executeUpdate();
			   	session.flush();
				session.clear();
				tx.commit();
				session.close();
				//sessionFactory.close();
				log.debug("TXN Impl : Leaving updateStatus with success");	
			} catch (Exception e) {
				log.debug("TXN Impl : Leaving updateStatus with Exception");
			}
			
		return true;
		}

}
