package com.kp.cms.transactionsimpl.admission;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IDownloadInterviewFormat;
import com.kp.cms.utilities.HibernateUtil;

public class DownloadInterviewFormatTransactionImpl implements
		IDownloadInterviewFormat {
	/**
	 * Singleton object of DownloadInterviewFormatHelper
	 */
	private static volatile DownloadInterviewFormatTransactionImpl downloadInterviewFormatTransactionImpl = null;
	private static final Log log = LogFactory.getLog(DownloadInterviewFormatTransactionImpl.class);
	private DownloadInterviewFormatTransactionImpl() {
		
	}
	/**
	 * return singleton object of DownloadInterviewFormatTransactionImpl.
	 * @return
	 */
	public static DownloadInterviewFormatTransactionImpl getInstance() {
		if (downloadInterviewFormatTransactionImpl == null) {
			downloadInterviewFormatTransactionImpl = new DownloadInterviewFormatTransactionImpl();
		}
		return downloadInterviewFormatTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IDownloadInterviewFormat#getInterviewPerPanelMap(java.lang.String)
	 */
	@Override
	public Map<Integer, Integer> getInterviewPerPanelMap(String query) throws Exception {
		Session session = null;
		List<Object[]> list = null;
		Map<Integer, Integer> panelMap=new HashMap<Integer, Integer>();
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			list = selectedCandidatesQuery.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					if(objects[0]!=null && objects[1]!=null)
					panelMap.put(Integer.parseInt(objects[0].toString()),Integer.parseInt(objects[1].toString()));
				}
				
			}
			return panelMap;
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
	
	public List getCandidates(String SearchCriteria) throws Exception{
		Session session = null;
		List selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(SearchCriteria);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
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
