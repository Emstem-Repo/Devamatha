package com.kp.cms.transactionsimpl.pettycash;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.pettycash.IConsolidatedCollectionLedger;
import com.kp.cms.utilities.HibernateUtil;

public class ConsolidatedCollectionLedgerTransactionImpl implements IConsolidatedCollectionLedger {
	private static final Log log = LogFactory.getLog(ConsolidatedCollectionLedgerTransactionImpl.class);

	/*get the required data for the search Query
	 *  (non-Javadoc)
	 * @see com.kp.cms.transactions.petticash.ICollectionLedger#getListOfData(java.lang.String)
	 */
	public List getListOfData(String searchCriteria) throws Exception {
		Session session = null;
		List pcAccountList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createSQLQuery(searchCriteria);
			pcAccountList = selectedCandidatesQuery.list();
			return pcAccountList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	
	/* verifying whether the user is existed
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.petticash.ICollectionLedger#verifyUser(java.lang.String)
	 */
	public Users verifyUser(String userName) throws Exception {
		Session session = null;
		Users user=null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String query="from Users users where users.userName=:name and users.isActive = 1";
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesQuery.setString("name",userName);
			user =(Users) selectedCandidatesQuery.uniqueResult();
			
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return user;
	}

}
