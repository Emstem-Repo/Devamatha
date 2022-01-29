package com.kp.cms.transactionsimpl.pettycash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.AdmMeritList;
import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.bo.admin.PettyCashCollectionDetails;
import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.pettycash.AccountHeadsForm;
import com.kp.cms.transactions.pettycash.IAccountHeadsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AccountHeadsTxnImpln implements IAccountHeadsTransaction {
	private static volatile AccountHeadsTxnImpln accountHeadsTxnImpln = null;

	public static AccountHeadsTxnImpln getInstance() {
		if (accountHeadsTxnImpln == null) {
			accountHeadsTxnImpln = new AccountHeadsTxnImpln();
			return accountHeadsTxnImpln;
		}
		return accountHeadsTxnImpln;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.pettycash.IAccountHeadsTransaction#uploadAccountHeads
	 * (java.util.List)
	 */
	@Override
	public boolean uploadAccountHeads(List<AccountHeads> accountHeads)
			throws Exception {
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if (accountHeads != null) {
				Iterator<AccountHeads> iterator = accountHeads.iterator();
				while (iterator.hasNext()) {
					AccountHeads accountHeads2 = (AccountHeads) iterator.next();
					
					if (accountHeads2 != null) {
						session.save(accountHeads2);
					}
				}
				transaction.commit();
				session.flush();
				isAdded = true;
			}
		} catch (ConstraintViolationException e) {
			transaction.rollback();

			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.pettycash.IAccountHeadsTransaction#
	 * uploadPettyCashCollection(java.util.List)
	 */
	@Override
	public boolean uploadPettyCashCollection(
			List<PettyCashCollection> cashCollections,AccountHeadsForm accountHeadsForm) throws Exception {
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		PettyCashCollection collection=null;
		List<String> applnRegNos=new ArrayList<String>();
		int count=0;
		int count1=0;
		String str =" ";
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if (cashCollections != null) {
				Iterator<PettyCashCollection> iterator = cashCollections
						.iterator();
				int size=cashCollections.size();
				while (iterator.hasNext()) {
					PettyCashCollection pettyCashCollection = (PettyCashCollection) iterator .next();
					if(pettyCashCollection.getAplRegNo()!=null){
						 str= "from PettyCashCollection pettyCash where pettyCash.receiptNo="+pettyCashCollection.getReceiptNo()+" and pettyCash.aplRegNo = '"+pettyCashCollection.getAplRegNo()+"' and pettyCash.academicYear="+pettyCashCollection.getAcademicYear();
					}else{
						str= "from PettyCashCollection pettyCash where pettyCash.receiptNo="+pettyCashCollection.getReceiptNo()+" and pettyCash.academicYear="+pettyCashCollection.getAcademicYear();
					}
					Query query = session.createQuery(str);
					collection = (PettyCashCollection) query.uniqueResult();
					if(collection == null){
						session.save(pettyCashCollection);
					}else{
						if(pettyCashCollection.getAplRegNo()!=null)
							applnRegNos.add(pettyCashCollection.getAplRegNo());
						count++;
					}
					count1++;
				}
				transaction.commit();
				accountHeadsForm.setRegNos(applnRegNos);
				session.flush();
				if(size == count)
				    isAdded = false;
				else
					isAdded = true;
			}
		} catch (ConstraintViolationException e) {
			transaction.rollback();

			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.pettycash.IAccountHeadsTransaction#
	 * uploadPettyCashCollectionDetails(java.util.List)
	 */
	@Override
	public boolean uploadPettyCashCollectionDetails(
			List<PettyCashCollectionDetails> cashCollectionDetails,AccountHeadsForm accountHeadsForm)
			throws Exception {
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		PettyCashCollectionDetails details =null;
		String str = "";
		int count =0;
		List<String> receiptNos=new ArrayList<String>();
		Map<String, List<Integer>> map=new HashMap<String, List<Integer>>();
		try {
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		String programHibernateQuery = "from PettyCashCollectionDetails pettyCashDetails where pettyCashDetails.academicYear="+accountHeadsForm.getAcademicYear();
		List<PettyCashCollectionDetails> admList = session.createQuery(programHibernateQuery).list();
		for(PettyCashCollectionDetails p:admList){
			List<Integer> list=null;
			List<Integer> existList=null;
			if(!map.containsKey(p.getAccCode())){
				list=new ArrayList<Integer>();
				list.add(p.getReceiptNo());
				map.put(p.getAccCode(), list);
			}else{
				existList=map.remove(p.getAccCode());
				existList.add(p.getReceiptNo());
				map.put(p.getAccCode(), existList);
				
			}
		}				
			if (cashCollectionDetails != null) {
				Iterator<PettyCashCollectionDetails> iterator = cashCollectionDetails .iterator();
				int size=cashCollectionDetails.size();
				int c=0;
				while (iterator.hasNext()) {
					List<Integer> checkList=new ArrayList<Integer>();
					PettyCashCollectionDetails pettyCashCollectionDetails = (PettyCashCollectionDetails) iterator .next();
					if(map != null && !map.isEmpty()){
						if(map.containsKey(pettyCashCollectionDetails.getAccCode()))
						checkList=map.get(pettyCashCollectionDetails.getAccCode());
					}
					if (checkList.contains(pettyCashCollectionDetails.getReceiptNo())) {
						receiptNos.add(String.valueOf(pettyCashCollectionDetails.getReceiptNo()));
						count++;
					}else{
						session.save(pettyCashCollectionDetails);
					}
					if(++c % 20 == 0){
						session.flush();
						session.clear();
					}
				}
				transaction.commit();
				accountHeadsForm.setReceiptNos(receiptNos);
				session.flush();
				if(size == count)
					isAdded = false;
				else
    			    isAdded = true;
			}
		} catch (ConstraintViolationException e) {
			transaction.rollback();

			throw new BusinessException(e);

		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	@Override
	public boolean isDuplicateYear(AccountHeadsForm accountHeadsForm)throws Exception {
		Session session=null;
		List<AccountHeads> promote= null;
		try{
			session=HibernateUtil.getSession();
			String query="from AccountHeads promote where promote.academicYear=:promodupl";
			
			Query que=session.createQuery(query);
			que.setString("promodupl", accountHeadsForm.getAcademicYear());
			promote= que.list();
			session.flush();
			if (promote != null && !promote.isEmpty()) {
				return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			session.flush();
			//session.close();
			return false;
		}
	}
}
