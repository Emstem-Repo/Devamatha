package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmMeritList;
import com.kp.cms.bo.admin.AttnBiodataPuc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttnBiodataPucForm;
import com.kp.cms.transactions.attandance.IAttnBiodataPucTransction;
import com.kp.cms.utilities.HibernateUtil;

public class AttnBiodataPucTxnImpl implements IAttnBiodataPucTransction{
	private static volatile AttnBiodataPucTxnImpl attnBiodataPucTxnImpl= null;
	public static AttnBiodataPucTxnImpl getInstance(){
		if(attnBiodataPucTxnImpl == null){
			attnBiodataPucTxnImpl = new AttnBiodataPucTxnImpl();
			return attnBiodataPucTxnImpl;
		}
		return attnBiodataPucTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttnBiodataPucTransction#uploadAttnBioData(java.util.List)
	 */
	@Override
	public boolean uploadAttnBioData(List<AttnBiodataPuc> biodataPuc,AttnBiodataPucForm attnBioDataForm)
			throws Exception {
		boolean isAdded = false;
		Session session =null;
		Transaction tx = null;
		AttnBiodataPuc puc = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			String programHibernateQuery = "select merit.regNo from AttnBiodataPuc merit where merit.academicYear="+attnBioDataForm.getAcademicYear();
			List<String> admList = session.createQuery(programHibernateQuery).list();
			if(biodataPuc!=null){
				for(AttnBiodataPuc admMerit:biodataPuc){
					if (admMerit.getRegNo()!= null) {
						if (!admList.contains(admMerit.getRegNo())) {
							session.save(admMerit);
						} 
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
}
