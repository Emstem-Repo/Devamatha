package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlVisitorInfo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IVisitorInfoTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.PropertyUtil;

public class VisitorInfoTransactionImpl extends PropertyUtil implements IVisitorInfoTransaction
{

    private static final Log log = LogFactory.getLog(VisitorInfoTransactionImpl.class);

    public VisitorInfoTransactionImpl()
    {
    }

    public List<HlRoomTransaction> getListOfAppDetails(String query)
        throws Exception
    {
        Session session;
        List<HlRoomTransaction> queryList;
        log.debug("entering getListOfAppDetails in VisitorInfoTransactionImpl class");
        session = null;
        try
        {
            /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
            session = sessionFactory.openSession();*/
        	session = HibernateUtil.getSession();
            queryList = session.createQuery(query).list();
        } catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
        } finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
        }
        log.debug("exit from getListOfAppDetails in VisitorInfoTransactionImpl class");
        return queryList;
    }

    public boolean submitVisitorDetails(HlVisitorInfo hlVisitorInfo)
        throws Exception
    {
        return save(hlVisitorInfo);
    }
    
    public List<HlHostel> getHostelNames() throws Exception {
		log.debug("Entering getHostelNames of HostelCheckinTransactionImpl");
		Session session = null;
		List<HlHostel> hlhostellist = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sqlString = "from HlHostel h where h.isActive = 1 order by h.name";
			hlhostellist = session.createQuery(sqlString).list();
			if(hlhostellist!= null && hlhostellist.size() > 0){
				return hlhostellist;
			}
		}
			catch (Exception e) {
				log.error("Exception ocured at getting all records of hostels :",e);
					throw  new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
				log.debug("Exiting getHostelNames of HostelCheckinTransactionImpl");
		return hlhostellist;
	}

}
