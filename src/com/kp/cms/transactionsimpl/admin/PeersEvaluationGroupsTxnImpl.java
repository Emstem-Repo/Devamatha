package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PeersEvaluationGroups;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.PeersEvaluationGroupsForm;
import com.kp.cms.transactions.admin.IPeersEvaluationGroupsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PeersEvaluationGroupsTxnImpl implements IPeersEvaluationGroupsTransaction{
	public static volatile PeersEvaluationGroupsTxnImpl impl = null;
	public static PeersEvaluationGroupsTxnImpl getInstance(){
		if(impl == null){
			impl = new PeersEvaluationGroupsTxnImpl();
			return impl;
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationGroupsTransaction#getPeersEvaluationGroupsList()
	 */
	@Override
	public List<PeersEvaluationGroups> getPeersEvaluationGroupsList() throws Exception {
		Session session = null;
		List<PeersEvaluationGroups> peersEvaluationGroups;
		try{
			session = HibernateUtil.getSession();
			String str = "from PeersEvaluationGroups groups where groups.isActive = 1";
			Query query = session.createQuery(str);
			peersEvaluationGroups = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
		}
		}
		return peersEvaluationGroups;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationGroupsTransaction#checkDuplicate(com.kp.cms.forms.admin.PeersEvaluationGroupsForm)
	 */
	@Override
	public boolean checkDuplicate( PeersEvaluationGroupsForm peersEvaluationGroupsForm) throws Exception {
		Session session = null;
		boolean isDuplicate = false;
		try{
			session = HibernateUtil.getSession();
			String str = "from PeersEvaluationGroups grps where grps.isActive = 1 and grps.name ='"+peersEvaluationGroupsForm.getName()+"'";
			Query query = session.createQuery(str);
			PeersEvaluationGroups evaluationGroups = (PeersEvaluationGroups)query.uniqueResult();
			if(evaluationGroups!=null && !evaluationGroups.toString().isEmpty()){
				if(evaluationGroups.getId() == peersEvaluationGroupsForm.getId()){
					isDuplicate = false;
				}else{
					isDuplicate = true;
				}
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isDuplicate;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationGroupsTransaction#addPeersEvaluationGroups(com.kp.cms.forms.admin.PeersEvaluationGroupsForm, java.lang.String)
	 */
	@Override
	public boolean addPeersEvaluationGroups( PeersEvaluationGroups evaluationGroups,String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(evaluationGroups);
			}else if(mode.equalsIgnoreCase("Edit")){
				session.update(evaluationGroups);
			}
			tx.commit();
			isAdded = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaluationGroupsTransaction#deletePeersEvaluationGroups(com.kp.cms.forms.admin.PeersEvaluationGroupsForm)
	 */
	@Override
	public boolean deletePeersEvaluationGroups( PeersEvaluationGroupsForm peersEvaluationGroupsForm) throws Exception {
		Session session = null;
		Transaction txTransaction = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			txTransaction = session.beginTransaction();
			txTransaction.begin();
			PeersEvaluationGroups groups = (PeersEvaluationGroups)session.get(PeersEvaluationGroups.class, peersEvaluationGroupsForm.getId());
			groups.setIsActive(false);
			groups.setModifiedBy(peersEvaluationGroupsForm.getUserId());
			groups.setLastModifiedBy(new Date());
			session.update(groups);
			txTransaction.commit();
			isDeleted = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isDeleted;
	}
}
