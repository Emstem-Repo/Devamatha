package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.fee.FeeCriteriaForm;
import com.kp.cms.transactions.fee.IFeeCriteriaTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class FeeCriteriaTransactionImpl implements IFeeCriteriaTransaction {
	private static final Log log = LogFactory.getLog(FeeCriteriaTransactionImpl.class);
	public static FeeCriteriaTransactionImpl feeCriteriaTransactionImpl = null;

	public static FeeCriteriaTransactionImpl getInstance() {
		if (feeCriteriaTransactionImpl == null) {
			feeCriteriaTransactionImpl = new FeeCriteriaTransactionImpl();
			return feeCriteriaTransactionImpl;
		}
		return feeCriteriaTransactionImpl;
	}
	
	/**
	 * 
	 * @param feeCriteria
	 * @return
	 * @throws Exception
	 */
	public boolean addFeeCriteriaToTable(FeeCriteria feeCriteria) throws Exception {
		log.info("addFeeCriteriaToTable");
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(feeCriteria);
			transaction.commit();
			session.close();
			sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addFeeCriteriaToTable..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in  addFeeCriteriaToTable..." , e);
			throw new ApplicationException(e);
		}
		log.info("End of addFeeCriteriaToTable of FeeCriteriaTransactionImpl");
		return result;
	}	
	/**
	 * duplicate check
	 */
	public boolean checkDuplicate(FeeCriteriaForm feeCriteriaForm) throws Exception {
		log.info("Start of isCourseCodeDuplcated of CourseTransactionImpl");
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			StringBuffer sqlString = new StringBuffer("");
			List<String> nullList = new ArrayList<String>();
			if(feeCriteriaForm.getInstituteID()!= null && !feeCriteriaForm.getInstituteID().trim().isEmpty()){
				sqlString.append(" college.id = " + Integer.parseInt(feeCriteriaForm.getInstituteID()));
			}
			else{
				nullList.add(" college.id  ");
			}
			if(feeCriteriaForm.getNationalityID()!= null && !feeCriteriaForm.getNationalityID().trim().isEmpty()){
				if(!sqlString.toString().trim().isEmpty()){
					sqlString.append(" and ");
				}
				sqlString.append(" nationality.id = " + Integer.parseInt(feeCriteriaForm.getNationalityID()));
			}
			else{
				nullList.add(" nationality.id ");
			}
			if(feeCriteriaForm.getUniversityId()!= null && !feeCriteriaForm.getUniversityId().trim().isEmpty()){
				if(!sqlString.toString().trim().isEmpty()){
					sqlString.append(" and ");
				}
				sqlString.append(" university.id = " + Integer.parseInt(feeCriteriaForm.getUniversityId()));
			}
			else{
				nullList.add(" university.id ");
			}
			if(feeCriteriaForm.getResidentCategoryId()!= null && !feeCriteriaForm.getResidentCategoryId().trim().isEmpty()){
				if(!sqlString.toString().trim().isEmpty()){
					sqlString.append(" and ");
				}
				sqlString.append(" residentCategory.id = " + Integer.parseInt(feeCriteriaForm.getResidentCategoryId()));
			}
			else{
				nullList.add(" residentCategory.id  ");
			}
			if(feeCriteriaForm.getLanguage()!= null && !feeCriteriaForm.getLanguage().trim().isEmpty()){
				if(!sqlString.toString().trim().isEmpty()){
					sqlString.append(" and ");
				}
				sqlString.append(" trim(secLanguage) = '" + feeCriteriaForm.getLanguage().trim() + "'");
			}
			else{
				nullList.add(" secLanguage ");	
			}
			
			if(nullList!= null && nullList.size() > 0){
				Iterator<String> nullItr = nullList.iterator();
				while (nullItr.hasNext()) {
					String nullItem = (String) nullItr.next();
					sqlString.append(" and " + "(" + nullItem + " is null or " + nullItem.trim() + " = '')");
				}
			}
				
			Query query = session.createQuery("from FeeCriteria f where " + sqlString.toString());
			List<FeeCriteria> list = query.list();
			session.flush();
			session.close();
			sessionFactory.close();
			if (!list.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			log.error("Error during duplication checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.info("End of checkDuplicate of FeeCriteriaTransactionImpl");
		return false;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getAllFeesGroup() throws Exception {
		log.debug("Trxn Impl : Entering getAllFeesGroup");
		Map<Integer,String> feeOptionalGroupMap = new HashMap<Integer,String>();
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select distinct feeAdditional.id," +
					 " feeAdditional.feeGroup.name "
					 +" from FeeAdditional feeAdditional"  
					 +" inner join feeAdditional.feeGroup.feeHeadings headings" 
					 +" where headings.isActive = 1"
					 +" and feeAdditional.feeGroup.isActive = 1"
					 +" and feeAdditional.isActive = 1 "
					 +" order by feeAdditional.feeGroup.name");
			 
			List<Object[]> list = query.list();
			Iterator<Object[]> itr = list.iterator();
			while(itr.hasNext()) {
				 Object[] row = itr.next();
				 feeOptionalGroupMap.put((Integer)row[0], row[1].toString());
			}
			log.debug("Trxn Impl : Leaving getAllFeesGroup with success");
			return feeOptionalGroupMap;
		 } catch (Exception e) {
			log.debug("Trxn Impl : Leaving getAllFeesGroup with Exception");
			throw e;
		 }
	 }
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<FeeCriteria> getFeeCriterias() throws Exception {
		log.info("Start getFeeCriterias");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from FeeCriteria");
			List<FeeCriteria> list = query.list();
			session.flush();
			//session.close();
			return list;
		} catch (Exception e) {
			log.error("Error in getFeeCriterias..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}	
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	
	public boolean deleteFeeCriteria(int id) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			FeeCriteria feeCriteria = (FeeCriteria) session.get(FeeCriteria.class, id);
			session.delete(feeCriteria);
			transaction.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in deleteFeeCriteria..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in deleteFeeCriteria..." ,e);
			throw new ApplicationException(e);
		}
		return result;
	}	
}
