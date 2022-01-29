package com.kp.cms.transactionsimpl.employee;

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
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.transactions.employee.IPayScaleTransactions;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class PayScaleTransactionImpl implements IPayScaleTransactions{
	private static final Log log = LogFactory
	.getLog(PayScaleTransactionImpl.class);
	@Override
	public boolean addPayScale(PayScaleBO payScale) {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(payScale);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return true;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IPayScaleTransactions#getPayscaleList()
	 */
	@Override
	public List<PayScaleBO> getPayscaleList() {
		Session session=null;
		List<PayScaleBO> payScaleList=null;
		try{
			String query="from PayScaleBO payscale where payscale.isActive=1";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			payScaleList=querry.list();
			session.close();
		}catch(Exception exception){
			log.error("Error during getting PayScaleList by id..." , exception);
			session.flush();
			session.close();
		}
		return payScaleList;
	}
	@Override
	public PayScaleBO getPayScaleById(int id) {
		Session session=null;
		PayScaleBO payScaleBo=null;
		try{
			session=HibernateUtil.getSession();
			String str="from PayScaleBO pscale where pscale.id="+id;
			Query query=session.createQuery(str);
			payScaleBo=(PayScaleBO)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting PayScale by id..." , e);
			session.flush();
			session.close();
			
		}
		return payScaleBo;
		
	}
	@Override
	public boolean updatePayScale(PayScaleBO payScale)
			 {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.merge(payScale);
			transaction.commit();
			session.flush();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during updating PayScale data...", e);
		}
		return true;
	}
	@Override
	public boolean deletePayScale(int id) {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from PayScaleBO pscale where pscale.id="+id;
      	    PayScaleBO payScale=(PayScaleBO)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    payScale.setIsActive(false);
      	    session.update(payScale);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting PayScale data...", e);
      	
      }
return true;
		
	}
	@Override
	public boolean duplicateCheck(String name,HttpSession hSession,ActionErrors errors,PayScaleDetailsForm payScaleForm) {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from PayScaleBO pay where pay.payScale=:payScaleName";
			Query query=session.createQuery(quer);
			query.setString("payScaleName", name);
			//query.setString("scales", payScaleForm.getScale());
			PayScaleBO payScale=(PayScaleBO)query.uniqueResult();
			if(payScale!=null && !payScale.toString().isEmpty()){
				if(payScaleForm.getId()!=0){
			      if(payScale.getId()==payScaleForm.getId()){
				    flag=false;
			      }else if(payScale.getIsActive()){
				     flag=true;
			         errors.add("error", new ActionError("knowledgepro.employee.PayScale.duplicate"));
			       }
			      else{
					   flag=true;
					   payScaleForm.setId(payScale.getId());
					   throw new ReActivateException(payScale.getId());
				   }
				}else if(payScale.getIsActive()){
					flag=true;
			        errors.add("error", new ActionError("knowledgepro.employee.PayScale.duplicate"));
				}
				else{
					  flag=true;
					  payScaleForm.setId(payScale.getId());
					  throw new ReActivateException(payScale.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.EMP_PAYSCALE_REACTIVATE));
				//saveErrors(request, errors);
				hSession.setAttribute("ReactivateId", payScaleForm.getId());
			}
		}
		return flag;
	}
	public boolean reactivatePayScale(PayScaleDetailsForm payScaleForm)throws Exception
	  {
	log.info("Entering into DocumentExamEntryTransactionImpl of reactivateDocExamType");
	//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
	Session session=null;
	Transaction transaction=null;
	try{
	   session=HibernateUtil.getSession();
	   transaction=session.beginTransaction();
	   String quer="from PayScaleBO scale where scale.id="+payScaleForm.getId();
	   Query query=session.createQuery(quer);
	   PayScaleBO leave=(PayScaleBO)query.uniqueResult();
	   leave.setIsActive(true);
	   leave.setModifiedBy(payScaleForm.getUserId());
	   leave.setLastModifiedDate(new Date());
	   session.update(leave);
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

}
