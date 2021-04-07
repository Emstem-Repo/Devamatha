package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.bo.admin.AdmFeeMain;
import com.kp.cms.bo.admin.AdmMeritList;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AdmMeritListForm;
import com.kp.cms.transactions.admin.IAdmMeritTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AdmMeritTxnImpl implements IAdmMeritTransaction{
	private static volatile AdmMeritTxnImpl admMeritTxn = null;
	public static AdmMeritTxnImpl getInstance(){
		if(admMeritTxn == null){
			admMeritTxn = new AdmMeritTxnImpl();
			return admMeritTxn;
		}
		return admMeritTxn;
	}
	@Override
	public boolean addAdmMeritList(List<AdmMeritList> list,AdmMeritListForm admMeritForm) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction tx = null;
		List<Integer> appNos=new ArrayList<Integer>();
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			String programHibernateQuery = "select merit.appNo from AdmMeritList merit where merit.academicYear="+admMeritForm.getAcademicYear();
			List<Integer> admList = session.createQuery(programHibernateQuery).list();
			if(list!=null){
//				Iterator<AdmMeritList> iterator= list.iterator();
//				while (iterator.hasNext()) {
//					AdmMeritList admMerit = (AdmMeritList) iterator .next();
				for(AdmMeritList admMerit:list){
					if (admMerit.getAppNo()!= null) {
						if (admList.contains(admMerit.getAppNo())) {
							appNos.add(admMerit.getAppNo());
						} else {
							session.save(admMerit);
						}
					}
				}
				admMeritForm.setAppNos(appNos);
				tx.commit();
				session.flush();
				isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			
			throw new BusinessException(e);
		} catch (Exception e) {
			isAdded=false;
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
		
	}
	
	@Override
	public boolean addAdmFeeMain(List<AdmFeeMain> list,
			AdmMeritListForm admMeritForm) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction tx = null;
		int count=0;
		List<String> regNos=new ArrayList<String>();
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			if(list!=null){
				int size=list.size();
				Iterator<AdmFeeMain> iterator= list.iterator();
				while (iterator.hasNext()) {
					AdmFeeMain feeMain = (AdmFeeMain) iterator .next();
					String str= "from AdmFeeMain fee where fee.billNo = '"+feeMain.getBillNo()+"' and fee.date =:feedate";
					Query query = session.createQuery(str);
					query.setDate("feedate",feeMain.getDate());
					AdmFeeMain fee = (AdmFeeMain) query.uniqueResult();
					if(fee!=null){
						regNos.add(feeMain.getApplnRegNo());
						count++;
					}
					else{
						session.save(feeMain);
					}
				}
				admMeritForm.setRegNos(regNos);
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
			isAdded=false;
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAdmMeritTransaction#getAdmMeritList(java.lang.StringBuffer)
	 */
	@Override
	public List<AdmMeritList> getAdmMeritList(StringBuffer query)
			throws Exception {
		 List<AdmMeritList> admMeritLists;
		 Session session = null;
		 try{
			 session = HibernateUtil.getSession();
			 Query qry = session.createQuery(query.toString());
			 admMeritLists = qry.list();
		 }catch (Exception e) {
				throw new ApplicationException(e);
			}
		return admMeritLists;
	}

   
}
