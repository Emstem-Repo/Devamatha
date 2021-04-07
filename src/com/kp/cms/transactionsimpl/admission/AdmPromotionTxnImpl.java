package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admission.PromoteSecondLang;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.AdmPromotionForm;
import com.kp.cms.transactions.admission.IAdmPromotionTxn;
import com.kp.cms.utilities.HibernateUtil;

public class AdmPromotionTxnImpl implements IAdmPromotionTxn{
	private static volatile AdmPromotionTxnImpl admPromotionTxn = null;
	public static AdmPromotionTxnImpl getInstance(){
		if(admPromotionTxn == null){
			admPromotionTxn = new AdmPromotionTxnImpl();
			return admPromotionTxn;
		}
		return admPromotionTxn;
	}
	@Override
	public boolean addPromoteSecondLang(List<PromoteSecondLang> list,
			AdmPromotionForm admPromote) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction tx = null;
		int count=0;
		List<String> codes=new ArrayList<String>();
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			if(list!=null){
				Iterator<PromoteSecondLang> iterator= list.iterator();
				int size=list.size();
				while (iterator.hasNext()) {
					
					PromoteSecondLang secondLang = (PromoteSecondLang) iterator .next();
					String str= "from PromoteSecondLang lang where (lang.secondLanguage = '"+secondLang.getSecondLanguage()+"' or lang.langCode ='"+secondLang.getLangCode()+"') and lang.academicYear="+admPromote.getAcademicYear();
					Query query = session.createQuery(str);
					PromoteSecondLang secondLan = (PromoteSecondLang) query.uniqueResult();
					if(secondLan!=null){
						if(secondLang.getLangCode()!=null && !secondLang.getLangCode().isEmpty())
							codes.add(secondLang.getLangCode());
						else if(secondLang.getSecondLanguage()!=null && !secondLang.getSecondLanguage().isEmpty())
       						codes.add(secondLang.getSecondLanguage());
						count++;
					}
					else{
						session.save(secondLang);
					}
				}
				admPromote.setCodes(codes);
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
	@Override
	public boolean addPromoteSupliMarks(List<PromoteSupliMarks> list,
			AdmPromotionForm admPromote) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction tx = null;
		int count=0;
		List<String> regNos=new ArrayList<String>();
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			if(list!=null){
				Iterator<PromoteSupliMarks> iterator= list.iterator();
				int size=list.size();
				while (iterator.hasNext()) {
					
					PromoteSupliMarks supliMarks = (PromoteSupliMarks) iterator .next();
					String str= "from PromoteSupliMarks supli where supli.regNo = '"+supliMarks.getRegNo()+"' and supli.classCode ='"+supliMarks.getClassCode()+"' and supli.academicYear ='"+supliMarks.getAcademicYear()+"'";
					Query query = session.createQuery(str);
					PromoteSupliMarks supli = (PromoteSupliMarks) query.uniqueResult();
					if(supli!=null){
						if(supliMarks.getRegNo()!=null)
						     regNos.add(supliMarks.getRegNo());
						count++;
					}
					else{
						session.save(supliMarks);
					}
				}
				admPromote.setRegNos(regNos);
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
}
