package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.ReceivedThroughForm;
import com.kp.cms.to.admission.ReceivedThroughTo;
import com.kp.cms.transactions.admission.IReceivedThroughTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ReceivedThroughTxnImpl implements IReceivedThroughTxn{
	private static final Log log = LogFactory
	.getLog(ReceivedThroughTxnImpl.class);
	private static volatile ReceivedThroughTxnImpl receivedThrough = null;
	public static ReceivedThroughTxnImpl getInstance(){
		if(receivedThrough == null){
			receivedThrough = new  ReceivedThroughTxnImpl();
			return receivedThrough;
		}
		return receivedThrough;
	}
	@Override
	public boolean addReceivedThrough(ReceivedThrough received,String mode)
			throws Exception {
		// TODO Auto-generated method stub
		Session session =null;
		Transaction transaction =null;
		boolean flag =false;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("add"))
			    session.save(received);
			else
				session.merge(received);
			transaction.commit();
			flag =true;
		}catch(Exception exception){
			if(transaction!=null)
			     transaction.rollback();
			flag = false;
			throw new BusinessException(exception);
		}
		finally{
			if (session != null){
				session.flush();
				session.close();
			}
		}
		return flag;
	}
	@Override
	public boolean duplicateCheck(HttpSession hSession, ActionErrors errors,
			ReceivedThroughForm receivedThroughForm) {
		// TODO Auto-generated method stub
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from ReceivedThrough receive where receive.receivedThrough=:name";
			Query query=session.createQuery(quer);
			query.setString("name", receivedThroughForm.getReceivedThrough());
			//query.setString("scales", payScaleForm.getScale());
			ReceivedThrough received=(ReceivedThrough)query.uniqueResult();
			if(received!=null){
				if(receivedThroughForm.getId()!=0){
			      if(received.getId()==receivedThroughForm.getId()){
				    flag=false;
			      }else if(received.getIsActive()){
				     flag=true;
			         errors.add("error", new ActionError("knowledgepro.admission.receivedThrough.duplicate"));
			       }
			      else{
					   flag=true;
					   receivedThroughForm.setId(received.getId());
					   throw new ReActivateException(received.getId());
				   }
				}else if(received.getIsActive()){
					flag=true;
			        errors.add("error", new ActionError("knowledgepro.admission.receivedThrough.duplicate"));
				}
				else{
					  flag=true;
					  receivedThroughForm.setId(received.getId());
					  throw new ReActivateException(received.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.RECEIVED_THROUGH_REACTIVATE));
				//saveErrors(request, errors);
				hSession.setAttribute("ReactivateId", receivedThroughForm.getId());
			}
		}
		return flag;
	}
	@Override
	public ReceivedThrough getReceivedThroughById(int id) {
		Session session=null;
		ReceivedThrough received=null;
		try{
			session=HibernateUtil.getSession();
			String str="from ReceivedThrough receive where receive.id="+id;
			Query query=session.createQuery(str);
			received=(ReceivedThrough)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting ReceivedThrough by id..." , e);
		}
		return received;
	}
	@Override
	public boolean deleteReceivedThrough(int id) {
		// TODO Auto-generated method stub
		Session session=null;
        Transaction transaction=null;
        boolean flag =false;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from ReceivedThrough receive where receive.id="+id;
      	  ReceivedThrough received=(ReceivedThrough)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	  received.setIsActive(false);
      	    session.update(received);
      	    transaction.commit();
      	    flag = true;
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	    flag = false;
      	log.debug("Error during deleting ReceivedThrough data...", e);
	      }
          return flag;
	}
	@Override
	public boolean reactivateReceivedThrough(
			ReceivedThroughForm receivedThroughForm) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from ReceivedThrough receive where receive.id="+receivedThroughForm.getId();
		   Query query=session.createQuery(quer);
		   ReceivedThrough received=(ReceivedThrough)query.uniqueResult();
		   received.setIsActive(true);
		   received.setModifiedBy(receivedThroughForm.getUserId());
		   received.setLastModifiedDate(new Date());
		   session.update(received);
		   transaction.commit();
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
	}
	@Override
	public List<ReceivedThrough> getReceivedThroughList() {
		Session session=null;
		List<ReceivedThrough> receivedThroughList=null;
		try{
			String query="from ReceivedThrough receive where receive.isActive=1";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			receivedThroughList=querry.list();
			session.close();
		}catch(Exception exception){
			log.error("Error during getting ReceivedThrough by id..." , exception);
		}
		return receivedThroughList;
	}
}
