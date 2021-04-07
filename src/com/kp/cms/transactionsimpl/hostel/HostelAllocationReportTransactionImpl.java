package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelAllocationReportForm;
import com.kp.cms.transactions.hostel.IHostelAllocationReportTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class HostelAllocationReportTransactionImpl implements IHostelAllocationReportTransactions {
	private static final Log log = LogFactory.getLog(HostelAllocationReportTransactionImpl.class);
	@Override
	public List<Object[]> getAllocationDetails(HostelAllocationReportForm hostelAllocationReportForm)
			throws Exception {
		log.debug("Entering getHostelNames of HostelAllocationTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		List<Object[]>  list=null;
			try {
				//session = HibernateUtil.getSessionFactory().openSession();
				session = HibernateUtil.getSession();
				String sqlString = "select hl.hlApplicationForm.hlHostelByHlApprovedHostelId.name,hl.hlRoom.id, hl.txnDate, hl.hlApplicationForm.hlRoomTypeByHlAppliedRoomTypeId.name from HlRoomTransaction hl where hl.hlStatus.id='7'"
					+" and hl.txnDate between '"+ CommonUtil.ConvertStringToSQLDate(hostelAllocationReportForm.getFromDate())+" 00:00:00.0'"+" and '"+CommonUtil.ConvertStringToSQLDate(hostelAllocationReportForm.getToDate())+" 23:59:59.0'";
				list=session.createQuery(sqlString).list();
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
				log.debug("Exiting getHostelNames of HostelAllocationTransactionImpl");
		return list;
	}

}
