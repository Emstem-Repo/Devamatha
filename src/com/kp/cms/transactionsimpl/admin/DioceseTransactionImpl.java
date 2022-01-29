package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.DioceseBO;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.DioceseForm;
import com.kp.cms.forms.admin.SubReligionForm;
import com.kp.cms.to.admin.DioceseTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class DioceseTransactionImpl {

	private static final Log log = LogFactory.getLog(DioceseTransactionImpl.class);
	public static volatile DioceseTransactionImpl dioceseTransactionImpl = null;

	public static DioceseTransactionImpl getInstance() {
		if (dioceseTransactionImpl == null) {
			dioceseTransactionImpl = new DioceseTransactionImpl();
			return dioceseTransactionImpl;
		}
		return dioceseTransactionImpl;
	}
	public boolean addDiocesetran(DioceseBO diocese,String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			     tx = session.beginTransaction();
			     tx.begin();
			     
			     if (mode.equalsIgnoreCase("Add")) { //add mode
				     session.save(diocese);
			     } else if (mode.equalsIgnoreCase("Edit")) {  //edit mode
				 session.update(diocese);
			       }
				     tx.commit();
				 
			       }
			return true;
		
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during saving addSubReligion data...", e);
			throw new ApplicationException(e);
		}finally{
			
		     session.flush();
		     session.close();
		}
		
	}
	  public List<DioceseTo> getDiocesesList() throws Exception {
	  		Session session=null;
	  		
	  		try{
	  			session=HibernateUtil.getSession();
	  			Query query=session.createQuery("from DioceseBO d where d.isActive = 1");
	  			List<DioceseTo> list=query.list();
	  			/*if(list!=null){
	  				Iterator<DioceseBO> iterator=list.iterator();
	  				while(iterator.hasNext()){
	  					DioceseBO s=iterator.next();
	  					if(s != null && s.getId() != 0 && s.getName() != null)
	  						li.put(s.getId(),s.getName());
	  				}
	  			}*/
	  			return list;
	  		}
	          catch (RuntimeException runtime) {
	  			
	  			throw new ApplicationException();
	  		}catch (Exception exception) {
	  			
	  			throw new ApplicationException();
	  		}finally{
	  			if(session!=null){
	  				session.flush();
	  			}
	  		}
	  		//map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
	  		
	  	}
	
	public List<DioceseBO> getDioceseTran() throws ApplicationException
	{
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			    Query query = session.createQuery("from DioceseBO d where d.isActive = 1 ");
			    List<DioceseBO> list = query.list();
				session.flush();
				//session.close();
			    return list;
			}else
				return null;
		} catch (Exception e) {
			log.error("Error in getSubReligion.. impl", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	public boolean deleteDiocese(int dioceseId, Boolean activate, DioceseForm dsForm) throws Exception{
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			DioceseBO dioceseBo = (DioceseBO) session.get(DioceseBO.class, dioceseId);
			if (activate) {
				dioceseBo.setIsActive(true);
			} else {
				dioceseBo.setIsActive(false);
			}
			dioceseBo.setModifiedBy(dsForm.getUserId());
			dioceseBo.setLastModifiedDate(new Date());
			session.update(dioceseBo);
			tx.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during deleting Sub Religion...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
		    	tx.rollback();
			log.error("Error during deleting Sub Religion...", e);
			throw new ApplicationException(e);
		}
	 
	 }
	
	public DioceseBO isDioceseDuplicated(DioceseBO duplDiocese) throws Exception {
		Session session = null;
		DioceseBO diocese;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from DioceseBO a where a.name = :dioceseName and subReligionId.id = :subReligionId");
			if(duplDiocese.getId() !=0){
				sqlString = sqlString.append(" and id != :dioceseId");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setString("dioceseName", duplDiocese.getName());
			query.setInteger("subReligionId", duplDiocese.getSubReligionId().getId());
			if(duplDiocese.getId() !=0){
				query.setInteger("dioceseId", duplDiocese.getId());
			}
			
			diocese = (DioceseBO) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return diocese;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public List<DioceseBO> getDiocese() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if(session!=null){
			    Query query = session.createQuery("from DioceseBO r where r.isActive = 1 ");
			    List<DioceseBO> list = query.list();
				session.flush();
				//session.close();
			    return list;
			}else
				return null;
		} catch (Exception e) {
			log.error("Error in getSubReligion.. impl", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
}
