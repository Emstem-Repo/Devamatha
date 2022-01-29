package com.kp.cms.transactionsimpl.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.IRequestVsIssueTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class RequestVsIssueTxnImpl implements IRequestVsIssueTransaction {
	
	private static final Log log = LogFactory.getLog(RequestVsIssueTxnImpl.class);

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IRequestVsIssueTransaction#getRequestIssueList(int, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Object[]> getRequestIssueList(int invLocationId, String startDate, String endDate)
			throws Exception {
		Session session = null;
		List<Object[]> requestItemList = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query;
			
			if(startDate!=null && endDate!=null && !startDate.isEmpty() && !endDate.isEmpty()){
				query = session.createQuery("select requestItem.invItem.code," +
						" requestItem.invItem.name," +
						" requestItem.quantity," +
						" issueItems.quantity," +
						" requestItem.invRequest.requestedBy," +
						" issues.issueTo," +
						" requestItem.invRequest.requestDate," +
						" issues.issueDate" +
						" from InvRequestItem requestItem" +
						" join requestItem.invRequest.invIssues issues" +
						" join issues.invIssueItems issueItems" +
						" where issues.invLocation.id = :invLocationId" +
						" and issues.isActive = 1" +
						" and issueItems.isActive = 1" +
						" and requestItem.isActive = 1" +
						" and requestItem.invRequest.isActive = 1" +
						" and requestItem.invRequest.requestDate between :startDate and :endDate" +
						" and issues.issueDate between :startDate and :endDate" +
						" group by requestItem.id");
			}else{
				query = session.createQuery("select requestItem.invItem.code," +
						" requestItem.invItem.name," +
						" requestItem.quantity," +
						" issueItems.quantity," +
						" requestItem.invRequest.requestedBy," +
						" issues.issueTo," +
						" requestItem.invRequest.requestDate," +
						" issues.issueDate" +
						" from InvRequestItem requestItem" +
						" join requestItem.invRequest.invIssues issues" +
						" join issues.invIssueItems issueItems" +
						" where issues.invLocation.id = :invLocationId" +
						" and issues.isActive = 1" +
						" and issueItems.isActive = 1" +
						" and requestItem.isActive = 1" +
						" and requestItem.invRequest.isActive = 1" +
						" group by requestItem.id");
			}
			query.setInteger("invLocationId", invLocationId);
			if(startDate!=null && endDate!=null && !startDate.isEmpty() && !endDate.isEmpty()){
				query.setDate("startDate", CommonUtil.ConvertStringToSQLDate(startDate));
				query.setDate("endDate", CommonUtil.ConvertStringToSQLDate(endDate));
			}	
			requestItemList = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return requestItemList;
	}
}
