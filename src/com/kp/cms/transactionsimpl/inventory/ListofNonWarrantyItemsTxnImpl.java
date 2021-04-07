package com.kp.cms.transactionsimpl.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.IListofNonWarrantyItemsTxn;
import com.kp.cms.utilities.HibernateUtil;

public class ListofNonWarrantyItemsTxnImpl implements IListofNonWarrantyItemsTxn {
	private static Log log = LogFactory.getLog(ListofNonWarrantyItemsTxnImpl.class);
	
	public static volatile ListofNonWarrantyItemsTxnImpl listofNonWarrantyItemsTxnImpl = null;
	
	public static ListofNonWarrantyItemsTxnImpl getInstance() {
		if (listofNonWarrantyItemsTxnImpl == null) {
			listofNonWarrantyItemsTxnImpl = new ListofNonWarrantyItemsTxnImpl();
			return listofNonWarrantyItemsTxnImpl;
		}
		return listofNonWarrantyItemsTxnImpl;
	}	
	

	/**
	 * get all warranty
	 * @param itemCategoryId
	 * @return
	 * @throws Exception
	 */
	public List<InvItem> getWarrantyItems(int itemCategoryId) throws Exception {
		log.debug("inside getWarrantyItems");
		Session session = null;
		
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer();
			
			sqlString.append("from InvItem i where i.isActive = 1 and isWarranty = 0");
			if(itemCategoryId > 0){
				sqlString.append(" and i.invItemCategory.id = " + itemCategoryId);
			}
			Query query = session.createQuery(sqlString.toString());			
			List<InvItem> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getWarrantyItems");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getWarrantyItems...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}	
}
