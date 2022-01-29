package com.kp.cms.transactionsimpl.exam;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamFooterAgreementImpl extends ExamGenImpl {

	public void isDuplicate(int id, String name, int programTypeId)
			throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamFooterAgreementBO.class);
			crit.add(Restrictions.eq("name", name));
			crit.add(Restrictions.eq("programTypeId", programTypeId));
			List<ExamFooterAgreementBO> list = crit.list();

			if (list.size() > 0) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					ExamFooterAgreementBO eBO = (ExamFooterAgreementBO) it
							.next();
					if (eBO.getIsActive() == true && id != eBO.getId()) {
						throw new DuplicateException();
					}

				}
			}
			session.flush();
		} catch (Exception e) {
			throw e;
		}

	}

}
