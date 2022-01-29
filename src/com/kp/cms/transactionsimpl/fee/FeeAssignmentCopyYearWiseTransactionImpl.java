package com.kp.cms.transactionsimpl.fee;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Fee;
import com.kp.cms.transactions.fee.IFeeAssignmentCopyYearWiseTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class FeeAssignmentCopyYearWiseTransactionImpl implements IFeeAssignmentCopyYearWiseTransaction
{
	public List<Fee> getExistingFeeDefinitions(String queryToGetExistingFeesDefinition)throws Exception
	{ 
		Session session=null;
		List<Fee>feeList=null;
		try
		{
			session=HibernateUtil.getSession();
			Query query=session.createQuery(queryToGetExistingFeesDefinition);
			feeList=query.list();
			
		}
		catch (Exception e) 
		{
			throw e;
		}
		return feeList;
	}
	
	public boolean isFeeDefinitionToBeCopiedAlreadyExists(String queryToGetFeeDefinitionToBecopiedExists)throws Exception
	{
		Session session=null;
		List<Fee>feeList=null;
		try
		{
			session=HibernateUtil.getSession();
			Query query=session.createQuery(queryToGetFeeDefinitionToBecopiedExists);
			feeList=query.list();
			if(feeList!=null && feeList.size()!=0)
				return false;
		}
		catch (Exception e) 
		{
			throw e;
		}
		return true;
	}
	
	public boolean copyFees(List<Fee> feeListToBeCopied)throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			int i=0;
			for(Fee fee:feeListToBeCopied)
			{
				session.save(fee);
				if(i++%20==0)
				{
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			return true;
		}
		catch (Exception e) {
			throw e;
		}
		
	}
}
