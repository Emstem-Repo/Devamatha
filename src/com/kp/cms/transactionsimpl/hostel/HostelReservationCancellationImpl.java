package com.kp.cms.transactionsimpl.hostel;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelReservationcancellationForm;
import com.kp.cms.handlers.hostel.HostelReservationCancellationHadler;
import com.kp.cms.utilities.HibernateUtil;

public class HostelReservationCancellationImpl {

	/**
	 * @param hostelReservationcancellationForm
	 * @return
	 * @throws ApplicationException
	 */
	public boolean cancelReservation(HostelReservationcancellationForm hostelReservationcancellationForm) throws ApplicationException {
		Session session = null;
		Transaction txn = null;
		int statusId=11;
		String query="from HlRoomTransaction h where h.hlApplicationForm.requisitionNo="+hostelReservationcancellationForm.getReqNo()+" and h.txnDate=(select max(h1.txnDate) from HlRoomTransaction h1 where h1.hlApplicationForm.requisitionNo="+hostelReservationcancellationForm.getReqNo()+")";
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			HlRoomTransaction hlRoomTransaction =(HlRoomTransaction)session.createQuery(query).uniqueResult();
			HlRoomTransaction hlRoomTransaction2=HostelReservationCancellationHadler.getInstance().getNewHlRoomTransactionAfterCancellation(hlRoomTransaction);
			session.save(hlRoomTransaction2);
			HlApplicationForm hlApplicationForm= hlRoomTransaction.getHlApplicationForm();
			HlStatus status=new HlStatus();
			status.setId(statusId);
			hlApplicationForm.setHlStatus(status);
			session.update(hlApplicationForm);
			txn.commit();
			return true;
		} catch (Exception e) {		
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}

	public HlApplicationForm getHlApplicationFormByRequistionNo(String query) throws Exception{
		Session session = null;
		Transaction txn = null;
		HlApplicationForm hlApplicationForm=null;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			hlApplicationForm =(HlApplicationForm)session.createQuery(query).uniqueResult();
			txn.commit();
			return hlApplicationForm;
		} catch (Exception e) {		
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}

}
