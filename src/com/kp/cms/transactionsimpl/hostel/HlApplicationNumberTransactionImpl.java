package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HostelApplicationNumber;
import com.kp.cms.transactions.hostel.IHlApplicationNumberTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class HlApplicationNumberTransactionImpl implements IHlApplicationNumberTransaction{
	public static volatile HlApplicationNumberTransactionImpl impl = null;
	private static Log log = LogFactory.getLog(HlApplicationNumberTransactionImpl.class);
	public static HlApplicationNumberTransactionImpl getInstance() {
		if (impl == null) {
			impl = new HlApplicationNumberTransactionImpl();
			return impl;
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHlApplicationNumberTransaction#getHostelList()
	 */
	@Override
	public Map<Integer, String> getHostelList() throws Exception {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from HlHostel hos where hos.isActive = 1 ");
			List<HlHostel> hostelList = query.list();
			Map<Integer, String> hostelMap = new HashMap<Integer, String>();
			Iterator<HlHostel> itr = hostelList.iterator();
			HlHostel hlHostel;

			while (itr.hasNext()) {
				hlHostel = (HlHostel) itr.next();
				hostelMap.put(hlHostel.getId(), hlHostel.getName());
			}
			hostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelMap);

			return hostelMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	@Override
	public boolean save(HostelApplicationNumber number, String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session =HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("ADD"))
				session.save(number);
			else
				session.update(number);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	@Override
	public boolean delete(int id) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session =HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			HostelApplicationNumber number = new HostelApplicationNumber();
			number.setId(id);
			session.delete(number);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	@Override
	public List<HostelApplicationNumber> getHostelApplnNumbers()
			throws Exception {
		List<HostelApplicationNumber> list = new ArrayList<HostelApplicationNumber>();
		Session session = null;
		try{
			session =HibernateUtil.getSession();
			Query query = session.createQuery(" from HostelApplicationNumber hos where hos.isActive = 1 ");
			list = query.list();
		}catch (Exception e) {
			if (session != null){
				session.close();
			}
		}
		return list;
	}
	
	
}
