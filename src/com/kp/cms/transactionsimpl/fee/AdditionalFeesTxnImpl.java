package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.AdditionalFees;
import com.kp.cms.bo.admin.FeesClassFee;
import com.kp.cms.bo.admin.FeesFeeDetails;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.fee.AdditionalFeesForm;
import com.kp.cms.transactions.fee.IAdditionalFeesTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AdditionalFeesTxnImpl implements IAdditionalFeesTransaction{
	private static volatile AdditionalFeesTxnImpl additionalFeesTxnImpl = null;
	public static AdditionalFeesTxnImpl getInstance(){
		if(additionalFeesTxnImpl == null){
			additionalFeesTxnImpl = new AdditionalFeesTxnImpl();
			return additionalFeesTxnImpl;
		}
		return additionalFeesTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IAdditionalFeesTransaction#uploadAdditionalFees(java.util.List)
	 */
	@Override
	public boolean uploadAdditionalFees(List<AdditionalFees> additionalFees,AdditionalFeesForm additionalFeesForm)
			throws Exception {
		boolean isAdded = false;
		Session session = null;
		Transaction tx = null;
		List<String> feeCodes=new ArrayList<String>();
		int count=0;
		try{
			session = HibernateUtil.getSession();
			tx= session.beginTransaction();
			if(additionalFees!=null){
				Iterator<AdditionalFees> iterator = additionalFees.iterator();
				int size =additionalFees.size();
				while(iterator.hasNext()){
					AdditionalFees fees= (AdditionalFees)iterator.next();
					String str= "from AdditionalFees fee where fee.feesCode = '"+fees.getFeesCode()+"' and fee.academicYear ="+fees.getAcademicYear();
					Query query = session.createQuery(str);
					AdditionalFees fee = (AdditionalFees)query.uniqueResult();
					if(fee!=null){
						feeCodes.add(fees.getFeesCode());
						count++;
					}else{
						session.save(fees);
					}
				}
				additionalFeesForm.setFeeCodeList(feeCodes);
				tx.commit();
				session.flush();
				if(size==count)
					isAdded=false;
				else
				    isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IAdditionalFeesTransaction#uploadFeeDetails(java.util.List)
	 */
	@Override
	public boolean uploadFeeDetails(List<FeesFeeDetails> feeDetails,AdditionalFeesForm additionalFeesForm)
			throws Exception {
		boolean isAdded = false;
		Session session = null;
		Transaction tx = null;
		List<Integer> billNos=new ArrayList<Integer>();
		int count=0;
		try{
			session = HibernateUtil.getSession();
			tx= session.beginTransaction();
			if(feeDetails!=null){
				Iterator<FeesFeeDetails> iterator = feeDetails.iterator();
				int size=feeDetails.size();
				while (iterator.hasNext()) {
					FeesFeeDetails feesFeeDetails = (FeesFeeDetails) iterator .next();
					String str= "from FeesFeeDetails fee where fee.billNo = "+feesFeeDetails.getBillNo()+" and fee.academicYear ="+feesFeeDetails.getAcademicYear()+" and fee.feesCode='"+feesFeeDetails.getFeesCode()+"'";
					Query query = session.createQuery(str);
					FeesFeeDetails fee = (FeesFeeDetails) query.uniqueResult();
					if(fee!=null){
						if(feesFeeDetails.getBillNo()!=0)
							billNos.add(feesFeeDetails.getBillNo());
						count++;
					}
					else{
						session.save(feesFeeDetails);
					}
				}
				additionalFeesForm.setBillNoList(billNos);
				tx.commit();
				session.flush();
				if(size==count)
					isAdded=false;
				else
				    isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IAdditionalFeesTransaction#uploadClassFees(java.util.List)
	 */
	@Override
	public boolean uploadClassFees(List<FeesClassFee> classFees)
			throws Exception {
		boolean isAdded = false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx= session.beginTransaction();
			if(classFees!=null){
				Iterator<FeesClassFee> iterator = classFees.iterator();
				while (iterator.hasNext()) {
					FeesClassFee feesClassFee = (FeesClassFee) iterator.next();
					if(feesClassFee!=null){
						session.save(feesClassFee);
					}
				}
				tx.commit();
				session.flush();
				isAdded= true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	@Override
	public boolean isDuplicateYear(AdditionalFeesForm additionalFeesForm)throws Exception {
		Session session=null;
		List<FeesClassFee> promote= null;
		try{
			session=HibernateUtil.getSession();
			String query="from FeesClassFee promote where promote.academicYear=:promodupl";
			
			Query que=session.createQuery(query);
			que.setString("promodupl", additionalFeesForm.getAcademicYear());
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
