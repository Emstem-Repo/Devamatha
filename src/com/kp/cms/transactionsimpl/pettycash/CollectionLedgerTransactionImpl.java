package com.kp.cms.transactionsimpl.pettycash;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.pettycash.ICollectionLedger;
import com.kp.cms.utilities.HibernateUtil;

public class CollectionLedgerTransactionImpl implements ICollectionLedger {
	private static final Log log = LogFactory.getLog(CollectionLedgerTransactionImpl.class);
	
	/* getting the Pc_Account_Head list
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.petticash.ICollectionLedger#getAccountList()
	 */
	@SuppressWarnings("unchecked")
	public List<PcAccountHead> getAccountList() throws Exception {
		Session session=null;
		List<PcAccountHead> accountList;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PcAccountHead pcAccountHead where pcAccountHead.isActive = 1 order by pcAccountHead.accName");
			accountList = query.list();
		
		} catch (Exception e) {
			log.error("Error while getting accountList..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return accountList;
	}
	
	@SuppressWarnings("unchecked")
	public String getAccountName(String Code) throws Exception {
		Session session=null;
		String accountName="";
		try {
			session = HibernateUtil.getSession();
			//Query query = session.createQuery("from PcAccountHead pcAccountHead where pcAccountHead.accCode = " +Code);
			//accountList = query.list();
			Query query = session.createQuery("select accName from PcAccountHead pcAccountHead where pcAccountHead.isActive=1 and pcAccountHead.accCode ='" +Code+"'");
			accountName =(String) query.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting accountList..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return accountName;
	}
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
			Query selectedCandidatesQuery=session.createQuery(searchCriteria);
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

	
	/* verifying the group code is valid or not
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.petticash.ICollectionLedger#verifyGroupCode(java.lang.String)
	 */
	public PcAccHeadGroup verifyGroupCode(String groupCode) throws Exception {
		Session session = null;
		PcAccHeadGroup group=null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String query="from PcAccHeadGroup gr where gr.code=:code and gr.isActive=1";
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesQuery.setString("code",groupCode);
			group =(PcAccHeadGroup) selectedCandidatesQuery.uniqueResult();
			
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return group;
	}
}
