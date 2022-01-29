package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.NewsEvents;
import com.kp.cms.bo.employee.OnlineLeaveAppInstructions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.OnlineLeaveAppInstructionForm;
import com.kp.cms.transactions.employee.IOnlineLeaveAppInstructionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class OnlineLeaveAppInstructionTxnImpl implements IOnlineLeaveAppInstructionTransaction {
	public static volatile OnlineLeaveAppInstructionTxnImpl appInstructionTxnImpl = null;
	public static OnlineLeaveAppInstructionTxnImpl getInstance(){
		if(appInstructionTxnImpl == null){
			appInstructionTxnImpl = new OnlineLeaveAppInstructionTxnImpl();
			return appInstructionTxnImpl;
		}
		return appInstructionTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IOnlineLeaveAppInstructionTransaction#getOnlineLeaveInstructions()
	 */
	@Override
	public List<OnlineLeaveAppInstructions> getOnlineLeaveInstructions() throws Exception {
		List<OnlineLeaveAppInstructions> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			list = session.createQuery("from OnlineLeaveAppInstructions leave ").list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IOnlineLeaveAppInstructionTransaction#checkDuplicateNewsEvents(java.lang.String)
	 */
	@Override
	public boolean checkDuplicateNewsEvents(String descMessage)
			throws Exception {
		Session session = null;
		boolean isExist = false;
		try {
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("select leave.description from OnlineLeaveAppInstructions leave");
			List<NewsEvents> list=query.list();
			 if(list!=null && !list.isEmpty() && list.contains(descMessage)){
				 isExist = true;
			 }
		} catch (Exception e) {
			isExist = false;
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isExist;
	}
	@Override
	public boolean saveLeaveInstructions( OnlineLeaveAppInstructions appInstructions) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(appInstructions);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IOnlineLeaveAppInstructionTransaction#getleaveAppInstructions(com.kp.cms.forms.employee.OnlineLeaveAppInstructionForm)
	 */
	@Override
	public OnlineLeaveAppInstructions getleaveAppInstructions( OnlineLeaveAppInstructionForm appInstructionForm) throws Exception {
		Session session = null;
		OnlineLeaveAppInstructions leaveAppInstructions = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from OnlineLeaveAppInstructions leave where leave.id="+appInstructionForm.getOnlineLeaveAppId();
			Query query = session.createQuery(str);
			leaveAppInstructions = (OnlineLeaveAppInstructions) query.uniqueResult();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return leaveAppInstructions;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IOnlineLeaveAppInstructionTransaction#deleteLeaveAppIns(int)
	 */
	@Override
	public boolean deleteLeaveAppIns(int leaveAppInsId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			OnlineLeaveAppInstructions leave = (OnlineLeaveAppInstructions) session.get(OnlineLeaveAppInstructions.class, leaveAppInsId);
			session.delete(leave);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDeleted;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IOnlineLeaveAppInstructionTransaction#updateLeaveInstructions(com.kp.cms.bo.employee.OnlineLeaveAppInstructions)
	 */
	@Override
	public boolean updateLeaveInstructions(OnlineLeaveAppInstructionForm appInstructionForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			OnlineLeaveAppInstructions leave = (OnlineLeaveAppInstructions) session.get(OnlineLeaveAppInstructions.class, appInstructionForm.getOnlineLeaveAppId());
			leave.setDescription(appInstructionForm.getDescription());
			leave.setLastModifiedDate(new Date());
			leave.setModifiedBy(appInstructionForm.getUserId());
			session.update(leave);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}

}
