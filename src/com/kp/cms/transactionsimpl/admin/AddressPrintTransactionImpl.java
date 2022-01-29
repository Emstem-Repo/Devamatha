package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IAddressPrintTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AddressPrintTransactionImpl implements IAddressPrintTransaction{
	public static volatile AddressPrintTransactionImpl addressPrintTransactionImpl=null;

	/**
	 * @return
	 */
	public static AddressPrintTransactionImpl getInstance() {
		if(addressPrintTransactionImpl == null){
			addressPrintTransactionImpl = new AddressPrintTransactionImpl();
			return addressPrintTransactionImpl;
		}
		return addressPrintTransactionImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAddressPrintTransaction#getRequiredRegdNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo)
			throws Exception {
		Session session = null;
		List<Student> regNoList;
		try {
			session = HibernateUtil.getSession();
			if(StringUtils.isNumeric(regNoFrom) && StringUtils.isNumeric(regNoTo)){
				regNoList = session.createQuery("from Student student" + " where student.isActive = 1 and student.registerNo between '" + regNoFrom + "' and '"+ regNoTo +"' and student.admAppln.isCancelled=0").list();
			}
			else{
				regNoList = session.createQuery("from Student student" + " where student.isActive = 1 and student.registerNo between '" + regNoFrom +"' and '"+ regNoTo+"' and student.admAppln.isCancelled=0").list();
			}		
			session.flush();
			return regNoList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}

	/* 
	 * 
	 * 
	 * @see com.kp.cms.transactions.admin.IAddressPrintTransaction#getRequiredRollNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Student> getRequiredRollNos(String rollNoFrom, String rollNoTo)
			throws Exception {
		Session session = null;
		List<Student> rollNoList;
		try {
			session = HibernateUtil.getSession();
			if(StringUtils.isNumeric(rollNoFrom) && StringUtils.isNumeric(rollNoTo)){
				rollNoList = session.createQuery("from Student student" + " where student.isActive = 1 and student.rollNo between '" + rollNoFrom +"' and '"+ rollNoTo +"' and student.admAppln.isCancelled=0").list();
			}
			else{
				rollNoList = session.createQuery("from Student student" + " where student.isActive = 1 and student.rollNo between '" + rollNoFrom +"' and '"+ rollNoTo+"' and student.admAppln.isCancelled=0").list();
			}			
			session.flush();
			return rollNoList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	
}
