package com.kp.cms.transactionsimpl.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.transactions.admission.IPublishAdmitCardTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PublishAdmitCardTransactionImpl implements IPublishAdmitCardTransaction {
	
	private static Log log = LogFactory.getLog(PublishAdmitCardTransactionImpl.class);

	@Override
	public List<AdmAppln> getCandidatesList() {
		List<AdmAppln> selectedCandidatesList = null;
		Session session = null;
		try {

			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();

			String selectedCandidates = "from AdmAppln admAppln where admAppln.isSelected = true";

			selectedCandidatesList = session.createQuery(selectedCandidates)
					.list();
			session.flush();
			//session.close();
		} catch (Exception e) {
			log.error("Error while getting candidates list "+e);
			if (session != null) {
				session.flush();
				session.close();
			}
		}

		return selectedCandidatesList;
	}
	
	

}
