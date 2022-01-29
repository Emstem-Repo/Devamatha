package com.kp.cms.transactionsimpl.exam;

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
import com.kp.cms.bo.exam.ExamRevaluationFee;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamRevaluationFeeForm;
import com.kp.cms.transactions.exam.IExamRevaluationFeeTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamRevaluationFeeTxnImpl implements IExamRevaluationFeeTxn{
	private static final Log log = LogFactory
	.getLog(ExamRevaluationFeeTxnImpl.class);
	private static volatile ExamRevaluationFeeTxnImpl examRevaluationtxn = null;
	public static ExamRevaluationFeeTxnImpl getInstance() {
		if (examRevaluationtxn == null) {
			examRevaluationtxn = new ExamRevaluationFeeTxnImpl();
		}
		return examRevaluationtxn;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationFeeTxn#duplicateCheck(javax.servlet.http.HttpSession, org.apache.struts.action.ActionErrors, com.kp.cms.forms.exam.ExamRevaluationFeeForm)
	 */
	@Override
	public boolean duplicateCheck(HttpSession hsession,
			ActionErrors errors, ExamRevaluationFeeForm examRevaluationFeeForm) {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from ExamRevaluationFee revaluation where revaluation.programType="+Integer.parseInt(examRevaluationFeeForm.getProgramTypeId())+" and revaluation.type=:type";
			Query query=session.createQuery(quer);
			query.setString("type", examRevaluationFeeForm.getType());
			//query.setString("scales", payScaleForm.getScale());
			ExamRevaluationFee revaluationFee=(ExamRevaluationFee)query.uniqueResult();
			if(revaluationFee!=null){
				if(examRevaluationFeeForm.getId()!=0){
			      if(revaluationFee.getId()==examRevaluationFeeForm.getId()){
				    flag=false;
			      }else if(revaluationFee.getIsActive()){
				     flag=true;
			         errors.add("error", new ActionError("knowledgepro.exam.revaluationfee.duplicate"));
			       }
			      else{
					   flag=true;
					   examRevaluationFeeForm.setId(revaluationFee.getId());
					   throw new ReActivateException(revaluationFee.getId());
				   }
				}else if(revaluationFee.getIsActive()){
					flag=true;
			        errors.add("error", new ActionError("knowledgepro.exam.revaluationfee.duplicate"));
				}
				else{
					  flag=true;
					  examRevaluationFeeForm.setId(revaluationFee.getId());
					  throw new ReActivateException(revaluationFee.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.EXAM_REVALUATION_FEE_ACTIVE));
				//saveErrors(request, errors);
				hsession.setAttribute("ReactivateId", examRevaluationFeeForm.getId());
			}
		}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationFeeTxn#addRevaluationFee(com.kp.cms.bo.exam.ExamRevaluationFee)
	 */
	@Override
	public boolean addRevaluationFee(ExamRevaluationFee revaluationFee) {
		Session session=null;
		Transaction transaction=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(revaluationFee);
			transaction.commit();
			flag=true;
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			flag=false;
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationFeeTxn#getRevaluationFeeById(int)
	 */
	@Override
	public ExamRevaluationFee getRevaluationFeeById(int id) {
		Session session=null;
		ExamRevaluationFee revaluation=null;
		try{
			session=HibernateUtil.getSession();
			String str="from ExamRevaluationFee fee where fee.id="+id;
			Query query=session.createQuery(str);
			revaluation=(ExamRevaluationFee)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting RevaluationFee by id..." , e);
			session.flush();
			session.close();
			
		}
		return revaluation;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationFeeTxn#updateRevaluationFee(com.kp.cms.bo.exam.ExamRevaluationFee)
	 */
	@Override
	public boolean updateRevaluationFee(ExamRevaluationFee revaluationFee) {
		Session session=null;
		Transaction transaction=null;
		boolean flag = false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			//session.update(revaluationFee);
			session.merge(revaluationFee);
			transaction.commit();
			flag = true;
			session.flush();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			flag = false;
			log.debug("Error during updating RevaluationFee data...", e);
		}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationFeeTxn#deleteRevaluationFee(int)
	 */
	@Override
	public boolean deleteRevaluationFee(int id) {
		Session session=null;
        Transaction transaction=null;
        boolean flag=false;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from ExamRevaluationFee fee where fee.id="+id;
      	  ExamRevaluationFee revaluationFee=(ExamRevaluationFee)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	  revaluationFee.setIsActive(false);
      	    session.update(revaluationFee);
      	    transaction.commit();
      	    flag = true;
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
            flag = false;
      	log.debug("Error during deleting RevaluationFee data...", e);
      	
      }
return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationFeeTxn#reactivateRevaluationFee(com.kp.cms.forms.exam.ExamRevaluationFeeForm)
	 */
	@Override
	public boolean reactivateRevaluationFee(
			ExamRevaluationFeeForm examRevaluationFeeForm) throws Exception {
		log.info("Entering into reactivateRevaluationFee");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from ExamRevaluationFee fee where fee.id="+examRevaluationFeeForm.getId();
		   Query query=session.createQuery(quer);
		   ExamRevaluationFee revaluation=(ExamRevaluationFee)query.uniqueResult();
		   revaluation.setIsActive(true);
		   revaluation.setModifiedBy(examRevaluationFeeForm.getUserId());
		   revaluation.setLastModifiedDate(new Date());
		   session.update(revaluation);
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamRevaluationFeeTxn#getRevaluationFeeList()
	 */
	@Override
	public List<ExamRevaluationFee> getRevaluationFeeList() {
		Session session=null;
		List<ExamRevaluationFee> RevaluationList=null;
		try{
			String query="from ExamRevaluationFee fee where fee.isActive=1";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			RevaluationList=querry.list();
			session.close();
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
		}
		return RevaluationList;
	}

}
