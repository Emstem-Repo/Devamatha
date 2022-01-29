package com.kp.cms.transactionsimpl.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvRequestItem;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.IInventoryRequestTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class InventoryRequestTxnImpl implements IInventoryRequestTransaction{
	private static final Log log = LogFactory.getLog(InventoryRequestTxnImpl.class);

	@Override
	public List<InvRequestItem> getInventoryRequest(int invLocationId) throws Exception {
		Session session = null;
		List<InvRequestItem> inventoryRequestList = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvRequestItem requestItem" +
					" where requestItem.invRequest.invLocation.id = :invLocationId" +
					" and requestItem.invRequest.isActive = 1" +
					" and requestItem.isActive = 1");
				query.setInteger("invLocationId", invLocationId);
				inventoryRequestList = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return inventoryRequestList;
	}
	
	@Override
	public List<InvItemStock> getAvailableStocks(int invLocationId) throws Exception {
		Session session = null;
		List<InvItemStock> availableStockList = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvItemStock stock" +
					" where stock.invLocation.id = :invLocationId" +
					" and stock.isActive = 1");
			query.setInteger("invLocationId", invLocationId);
			availableStockList = query.list();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return availableStockList;
	}
}