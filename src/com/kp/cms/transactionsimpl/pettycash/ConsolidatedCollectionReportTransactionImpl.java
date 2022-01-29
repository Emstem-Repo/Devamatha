package com.kp.cms.transactionsimpl.pettycash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.pettycash.IConsolidatedCollectionReport;
import com.kp.cms.utilities.HibernateUtil;

public class ConsolidatedCollectionReportTransactionImpl implements
		IConsolidatedCollectionReport {

	private static final Log log = LogFactory.getLog(ConsolidatedCollectionReportTransactionImpl.class);
	/*get the required data for the search Query
	 *  (non-Javadoc)
	 * @see com.kp.cms.transactions.petticash.IConsolidatedCollectionReport#getListOfData(java.lang.String)
	 */
	public List getListOfData(String searchCriteria) throws Exception {
		Session session = null;
		List pcAccountList = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session=HibernateUtil.getSession();
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
	
	/* getting the list of AccNo from database which is Active
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.pettycash.IConsolidatedCollectionReport#getListOfAccNo()
	 */
	public List<PcBankAccNumber> getListOfAccNo() throws Exception {
		Session session=null;
		List<PcBankAccNumber> accountList;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PcBankAccNumber p where p.isActive = 1");
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

	@Override
	public Map<Integer, String> getGroupNameMap() throws Exception {
		Session session = null;
		List<Object[]> pcAccountList = null;
		Map<Integer,String> groupNameMap=new HashMap<Integer, String>();
		try {
			session=HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select g.id,g.code from PcAccHeadGroup g where g.isActive=1");
			pcAccountList = selectedCandidatesQuery.list();
			if(!pcAccountList.isEmpty()){
				Iterator<Object[]> itr=pcAccountList.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null){
						groupNameMap.put(Integer.parseInt(objects[0].toString()),objects[1].toString());
					}
				}
			}
			return groupNameMap;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
}
