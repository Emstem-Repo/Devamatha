package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.hibernate.Session;

import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.transactions.admin.IResidentCategory;
import com.kp.cms.utilities.HibernateUtil;

public class ResidentCategoryTransactionImpl implements IResidentCategory {

	@Override
	public List<ResidentCategory> getResidentCategory() {
		Session session = null;
		List<ResidentCategory> residentCategoryBoList = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			residentCategoryBoList = session.createQuery("from ResidentCategory residentCategory where residentCategory.isActive = 1").list();
			if (session != null){
				session.flush();
				//session.close();
			}
		} catch (Exception e) {
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return residentCategoryBoList;
	}

}
