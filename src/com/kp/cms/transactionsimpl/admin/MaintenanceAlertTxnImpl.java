package com.kp.cms.transactionsimpl.admin;

import java.sql.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.MaintenanceAlert;
import com.kp.cms.forms.admin.MaintenanceAlertForm;
import com.kp.cms.transactions.admin.IMaintenanceAlert;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class MaintenanceAlertTxnImpl implements IMaintenanceAlert {

    public static volatile MaintenanceAlertTxnImpl maintenanceAlertTxnImpl = null;
    private static Log log = LogFactory.getLog(MaintenanceAlertTxnImpl.class);
  
    /**
     * @return
     */
    public static MaintenanceAlertTxnImpl getInstance() {
		if (maintenanceAlertTxnImpl == null) {
			maintenanceAlertTxnImpl = new MaintenanceAlertTxnImpl();
			return maintenanceAlertTxnImpl;
		}
		return maintenanceAlertTxnImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMaintenanceAlert#addMaintenanceAlert(com.kp.cms.bo.admin.MaintenanceAlert, java.lang.String)
	 */
	@Override
	public boolean addOrUpdateMaintenanceAlert(MaintenanceAlert maintenanceAlert,String mode) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add"))
			    session.save(maintenanceAlert);
			else
				session.merge(maintenanceAlert);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMaintenanceAlert#duplicateCheck(com.kp.cms.forms.admin.MaintenanceAlertForm)
	 */
	@Override
	public boolean duplicateCheck(MaintenanceAlertForm alertForm) throws Exception {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from MaintenanceAlert maintenance where maintenance.maintenanceDate='"+CommonUtil.ConvertStringToSQLDate(alertForm.getMaintenanceDate())+"' and maintenance.isActive=1";
			Query query=session.createQuery(quer);
			MaintenanceAlert maintenanceAlert=(MaintenanceAlert) query.uniqueResult();
			if(maintenanceAlert!=null){
				if(alertForm.getId()>0){
					flag=false;
				}else{
				flag=true;
				}
			}else{
				flag=false;
			}
				
		}catch(Exception e){
			log.info("exception occured in duplicate check..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMaintenanceAlert#getMaintenanceDetails()
	 */
	@Override
	public MaintenanceAlert getMaintenanceDetails() throws Exception {
		Session session=null;
		MaintenanceAlert maintenanceAlert=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from MaintenanceAlert maintenance where maintenance.maintenanceDate='"+new Date(new java.util.Date().getTime())+"' and maintenance.isActive=1";
			Query query=session.createQuery(quer);
			maintenanceAlert=(MaintenanceAlert) query.uniqueResult();
		}catch(Exception e){
			log.info("exception occured in duplicate check..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return maintenanceAlert;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMaintenanceAlert#getMaintenaceDetailsById(int)
	 */
	@Override
	public MaintenanceAlert getMaintenaceDetailsById(int id) throws Exception {
		Session session=null;
		MaintenanceAlert maintenanceAlert=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from MaintenanceAlert maintenance where maintenance.id="+id+" and maintenance.isActive=1";
			Query query=session.createQuery(quer);
			maintenanceAlert=(MaintenanceAlert) query.uniqueResult();
		}catch(Exception e){
			log.info("exception occured in duplicate check..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return maintenanceAlert;
	}

	
}
