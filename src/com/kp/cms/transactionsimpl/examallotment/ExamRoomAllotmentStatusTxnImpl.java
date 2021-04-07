package com.kp.cms.transactionsimpl.examallotment;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentStatusTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class ExamRoomAllotmentStatusTxnImpl implements IExamRoomAllotmentStatusTransactions{
	private static volatile ExamRoomAllotmentStatusTxnImpl txnImpl = null;
	public static ExamRoomAllotmentStatusTxnImpl getInstance(){
		if(txnImpl == null){
			txnImpl  = new ExamRoomAllotmentStatusTxnImpl();
		}
		return txnImpl;
	}
	private ExamRoomAllotmentStatusTxnImpl(){
		
	}
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentStatusTxnImpl.class);
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentStatusTransactions#getRoomsAllotedForCycle(java.lang.String)
	 */
	@Override
	public List<Object[]> getRoomsAllotedForCycle( String hqlQuery) throws Exception {
		Session session = null;
		List<Object[]> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery(hqlQuery);
			list = query.list();
			return list;
		} catch (Exception e) {
			log.error("Error while retrieving getRoomsAllotedForCycle.." +e);
			throw  new ApplicationException(e);
		} /*finally {
			if (session != null) {
				session.flush();
			}
		}*/
		
	}
}
