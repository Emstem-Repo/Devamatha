package com.kp.cms.transactionsimpl.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvItemType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.transactions.inventory.IItemTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ItemTxnImpl implements IItemTransaction{
	
	private static final Log log = LogFactory.getLog(ItemTxnImpl.class);

	@Override
	public List<InvItem> getItemList(int id) throws Exception {
		Session session = null;
		List<InvItem> itemList = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if (id != 0) {
				Query query = session.createQuery("from InvItem invItem where invItem.id = :id and invItem.isActive = 1");
				query.setInteger("id", id);
				itemList = query.list();
			} else {
				Query query = session.createQuery("from InvItem invItem where invItem.isActive = 1");
				itemList = query.list();
			}
		} catch (Exception e) {
			log.error("Error while getting itemList..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return itemList;
	}

	@Override
	public boolean addItem(InvItem invItem, String mode,
			Boolean originalChangedNotChanged) throws DuplicateException,
			Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(invItem);
			}else{
				session.update(invItem);
			}
			transaction.commit();
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error during saving Item..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving Item..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if ( session!= null){
				session.flush();
				session.close();
			}
		}
		return result;
	}

	@Override
	public boolean deleteItem(int id, Boolean activate, String userId)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			InvItem invItem = (InvItem) session.get(InvItem.class, id);
			if (activate) {
				invItem.setIsActive(true);
				invItem.setLastModifiedDate(new Date());
				invItem.setModifiedBy(userId);
			} else {
				invItem.setIsActive(false);
				invItem.setLastModifiedDate(new Date());
				invItem.setModifiedBy(userId);
			}
			session.update(invItem);
			transaction.commit();
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error while deleting Item entry..." + e);
			if (transaction != null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error while deleting Item entry..." + e);
			if (transaction != null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if ( session!= null){
				session.flush();
				session.close();
			}
		}
		return result;
	}

	@Override
	public InvItem isItemDuplicated(InvItem oldInvItem) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		InvItem invItem;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvItem invItem where invItem.code = :itemCode");
			query.setString("itemCode", oldInvItem.getCode());
			invItem = (InvItem) query.uniqueResult();
			
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			throw new ApplicationException(e);
		} finally{
			if( session!= null){
				session.flush();
				//session.close();
			}
		}
		return invItem;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IItemTransaction#getItemTypeList()
	 */
	@Override
	public List<InvItemType> getItemTypeList() throws Exception {
		log.debug("impl: inside getItemCategory");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InvItemType i");
			List<InvItemType> list = query.list();

			session.flush();
			log.debug("impl: leaving getItemTypeList");
			return list;
		} catch (Exception e) {
			log.error("Error in getItemTypeList..." + e);
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IItemTransaction#getId(java.lang.String)
	 */
	@Override
	public String getId(String newEntryName,String boName) throws Exception {
		log.debug("impl: inside getId");
		Session session = null;
		String que="";
		String result="";
		try {
			session = HibernateUtil.getSession();
			if(boName.equalsIgnoreCase("InvSubCategoryBo")){
				String[] str=newEntryName.split("_");
				que="select id from "+boName+" b where b.subCategoryName='"+str[0]+"' and b.isActive=1 and b.invItemCategory.id="+str[1];
			}
			else if(boName.startsWith("InvLocation_")){
				String[] str=boName.split("_");
				que="select id from "+str[0]+" b where b.name='"+newEntryName+"' and b.isActive=1 and b.invCampusId.id="+str[1];
			}
			else if(boName.equalsIgnoreCase("InvItem")){
				que="select id from "+boName+" b where b.code='"+newEntryName+"' and b.isActive=1";
			}
			else que="select id from "+boName+" b where b.name='"+newEntryName+"' and b.isActive=1";
			Query query = session.createQuery(que);
			Integer res=(Integer)query.uniqueResult();
			if(res!=null && res>0)
				return  Integer.toString(res);
			session.flush();
			
			log.debug("impl: leaving getId");
		} catch (Exception e) {
			log.error("Error in getId..." + e);
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
		return result;
	}
}