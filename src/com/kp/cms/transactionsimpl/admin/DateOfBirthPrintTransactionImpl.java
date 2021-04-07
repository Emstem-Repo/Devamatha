package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IDateOfBirthPrintTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class DateOfBirthPrintTransactionImpl implements IDateOfBirthPrintTransaction{
	public static volatile DateOfBirthPrintTransactionImpl dateOfBirthPrintTransactionImpl=null;

	/**
	 * @return
	 */
	public static DateOfBirthPrintTransactionImpl getInstance() {
		if(dateOfBirthPrintTransactionImpl == null){
			dateOfBirthPrintTransactionImpl = new DateOfBirthPrintTransactionImpl();
			return dateOfBirthPrintTransactionImpl;
		}
		return dateOfBirthPrintTransactionImpl;
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
}
