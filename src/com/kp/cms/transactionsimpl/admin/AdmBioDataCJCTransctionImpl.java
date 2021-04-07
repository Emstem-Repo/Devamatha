package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AdmBioDataCJCForm;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.transactions.admin.IAdmBioDataCJCTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AdmBioDataCJCTransctionImpl implements IAdmBioDataCJCTransaction{
	private static volatile AdmBioDataCJCTransctionImpl admBioDataCJCTransctionImpl = null;
	public static AdmBioDataCJCTransctionImpl getInstance(){
		if(admBioDataCJCTransctionImpl == null){
			admBioDataCJCTransctionImpl = new AdmBioDataCJCTransctionImpl();
			return admBioDataCJCTransctionImpl;
		}
		return admBioDataCJCTransctionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAdmBioDataCJCTransaction#addAdmBioData(java.util.List)
	 */
	@Override
	public boolean addAdmBioData(List<AdmBioDataCJC> admBioData,AdmBioDataCJCForm admBioDataForm) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction tx = null;
		List<String> regNos=new ArrayList<String>();
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			String programHibernateQuery = "select merit.regNo from AdmBioDataCJC merit where merit.academicYear="+admBioDataForm.getAcademicYear();
			List<Integer> admBoList = session.createQuery(programHibernateQuery).list();
			if(admBioData!=null){
				for(AdmBioDataCJC admMerit:admBioData){
					if (admMerit.getRegNo()!= null) {
						if (admBoList.contains(admMerit.getRegNo())) {
							//session.update(admMerit);
							regNos.add(admMerit.getRegNo());
						} else {
							session.save(admMerit);
						}
					}
				}
				admBioDataForm.setRegNos(regNos);
				tx.commit();
				session.flush();
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
}
