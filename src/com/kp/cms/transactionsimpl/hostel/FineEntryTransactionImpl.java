package com.kp.cms.transactionsimpl.hostel;

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

import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.hostel.FineEntryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.FineEntryForm;
import com.kp.cms.transactions.hostel.IFineEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class FineEntryTransactionImpl implements IFineEntryTransaction{
	public static volatile FineEntryTransactionImpl fineEntryTransactionImpl = null;
	private static Log log = LogFactory.getLog(FineEntryTransactionImpl.class);
	public static FineEntryTransactionImpl getInstance() {
		if (fineEntryTransactionImpl == null) {
			fineEntryTransactionImpl = new FineEntryTransactionImpl();
			return fineEntryTransactionImpl;
		}
		return fineEntryTransactionImpl;
	}
	@Override
	public Map<String, String> getFineCategoryList() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from FineCategoryBo a where a.isActive=1");
			List<FineCategoryBo> list=query.list();
			if(list!=null){
				Iterator<FineCategoryBo> iterator=list.iterator();
				while(iterator.hasNext()){
					FineCategoryBo fineCategoryBo=iterator.next();
					map.put(String.valueOf(fineCategoryBo.getId()),fineCategoryBo.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
			return map;
	
}
	@Override
	public boolean addFineEntry(FineEntryBo fineEntryBo,String add) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
				if(add.equalsIgnoreCase("add")){
					session.save(fineEntryBo);
				}else if(add.equalsIgnoreCase("update")){
					session.merge(fineEntryBo);
				}
				transaction.commit();
				flag=true;
		}catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}finally{
			if(session !=null){
				session.flush();
				}
		}
		return flag;
	}
	@Override
	public List<FineEntryBo> getFineEntry() throws Exception {
		log.debug("impl: inside getFineCategory");
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FineEntryBo a where a.isActive=1");
			List<FineEntryBo> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public boolean deleteFineEntry(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from FineEntryBo h where h.id="+id;
      	  FineEntryBo fineEntryBo=(FineEntryBo)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    fineEntryBo.setIsActive(false);
      	    session.update(fineEntryBo);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting fineEntryBo data...", e);
      }
          return true;
	}
	@Override
	public FineEntryBo getFineEntryById(int id) throws Exception {
		log.debug("impl: inside getFineCategory");
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FineEntryBo a where a.isActive=1 and a.id="+id);
			FineEntryBo fineEntryBo=(FineEntryBo)query.uniqueResult();
			session.flush();
			return fineEntryBo;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public boolean updateWhenPaidTheFine(int id,String paid) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from FineEntryBo h where h.id="+id;
      	  FineEntryBo fineEntryBo=(FineEntryBo)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    if(paid.equalsIgnoreCase("false")){
      	    	fineEntryBo.setPaid(true);
      	    }else{
      	    	fineEntryBo.setPaid(false);
      	    }
      	    session.update(fineEntryBo);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting fineEntryBo data...", e);
      }
          return true;
	}
	@Override
	public FineEntryBo getLastFineEntryBo(FineEntryForm fineEntryForm,String add) throws Exception {
		log.debug("impl: inside getFineCategory");
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query;
			if(add.equalsIgnoreCase("add")){
				 query = session.createQuery("from FineEntryBo f where f.id=(select max(id) from FineEntryBo a where a.isActive=1) ");
			}else{
				query = session.createQuery("from FineEntryBo a where a.isActive=1 and a.id="+fineEntryForm.getId());
			}
			FineEntryBo fineEntryBo=(FineEntryBo)query.uniqueResult();
			session.flush();
			return fineEntryBo;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IFineEntryTransaction#getSearchFineEntryListForRegNo(com.kp.cms.forms.hostel.FineEntryForm)
	 */
	@Override
	public List<FineEntryBo> getSearchFineEntryListForRegNo( FineEntryForm fineEntryForm) throws Exception {
		log.debug("impl: inside getSearchFineEntryListForRegNo");
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from FineEntryBo fineEntryBo where fineEntryBo.isActive=1 "+
							" and fineEntryBo.hlAdmissionId.studentId.registerNo='"+fineEntryForm.getRegNo()+"'"+
							" and fineEntryBo.hlAdmissionId.academicYear='"+fineEntryForm.getAcademicYear()+"'"+
							" and fineEntryBo.hlAdmissionId.isActive=1";
			Query query = session.createQuery(hqlQuery);
			List<FineEntryBo> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			log.error("Error during  getSearchFineEntryListForRegNo...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		
	}

}
