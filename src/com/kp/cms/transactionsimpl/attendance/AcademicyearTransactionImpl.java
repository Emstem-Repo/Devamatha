package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactionsimpl.fee.InstallmentPaymentTransactionImpl;
import com.kp.cms.utilities.HibernateUtil;

public class AcademicyearTransactionImpl {
	private static final Log log = LogFactory.getLog(InstallmentPaymentTransactionImpl.class);
	/**
	 * Gets all payment types
	 */
	public List<AcademicYear> getAllAcademicYear() throws Exception {
		Session session = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<AcademicYear> boList = session.createQuery("from AcademicYear where isActive=1 order by year").list();
			return boList;
		} catch (Exception e) {
		log.error("Exception ocured at getting all records of AcademicYear Mode :",e);
			throw  new ApplicationException(e);
		}
	}
	/**
	 * @return
	 * @throws ApplicationException 
	 */
	public int getCurrentAcademicYear() throws ApplicationException {
		Session session = null;
		int year=0;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			AcademicYear academicYear = (AcademicYear)session.createQuery("from AcademicYear a where a.isActive=1 and a.isCurrentForAdmission=1").uniqueResult();
			if(academicYear != null){
				year = academicYear.getYear();
			}
			return year;
		} catch (Exception e) {
		log.error("Exception ocured at getting  AcademicYear :",e);
			throw  new ApplicationException(e);
		}
	}
		public int getCurrentAcademicYearforTeacher() throws ApplicationException {
			Session session = null;
			int year=0;
			try {
				//session =InitSessionFactory.getInstance().openSession();
				session = HibernateUtil.getSession();
				AcademicYear academicYear = (AcademicYear)session.createQuery("from AcademicYear a where a.isActive=1 and a.isCurrent=1").uniqueResult();
				if(academicYear != null){
					year = academicYear.getYear();
				}
				return year;
			} catch (Exception e) {
			log.error("Exception ocured at getting  AcademicYear :",e);
				throw  new ApplicationException(e);
			}
		}
}
