package com.kp.cms.transactionsimpl.phd;

import java.util.ArrayList;
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

import com.kp.cms.bo.phd.PhdSynopsisDefenseBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.phd.PhdSynopsisDefenseForm;
import com.kp.cms.transactions.phd.IPhdSynopsisDefenseTransactions;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class PhdSynopsisDefenseImpl implements IPhdSynopsisDefenseTransactions {
	private static Log log = LogFactory.getLog(PhdSynopsisDefenseImpl.class);
	public static volatile PhdSynopsisDefenseImpl examCceFactorImpl = null;
	
	public static PhdSynopsisDefenseImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new PhdSynopsisDefenseImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	@Override
	public boolean addSynopsisDefense(PhdSynopsisDefenseBO synopsisBO,String mode) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(synopsisBO);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.merge(synopsisBO);
			}
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
	@Override
	public PhdSynopsisDefenseBO getSynopsisDefenseById(int id) throws Exception {
        Session session = null;
        PhdSynopsisDefenseBO synopsisDefense = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from PhdSynopsisDefenseBO a where a.id=")).append(id).toString()+" and a.isActive=1";
            Query query = session.createQuery(str);
            synopsisDefense = (PhdSynopsisDefenseBO)query.uniqueResult();
            session.flush();
        }
        catch(Exception e)
        {
            log.error("Error during getting getFeedBackQuestionById by id...", e);
            session.flush();
            session.close();
        }
        return synopsisDefense;
    }
    /**
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteSynopsisDefense(int id)
    throws Exception
{
    Session session = null;
    Transaction transaction = null;
    try
    {
        session = InitSessionFactory.getInstance().openSession();
        String str = (new StringBuilder("from PhdSynopsisDefenseBO a where a.id=")).append(id).toString();
        PhdSynopsisDefenseBO examCceFactor = (PhdSynopsisDefenseBO)session.createQuery(str).uniqueResult();
        transaction = session.beginTransaction();
        examCceFactor.setIsActive(false);
        session.update(examCceFactor);
        transaction.commit();
        session.close();
    }
    catch(Exception e)
    {
        if(transaction != null)
        {
            transaction.rollback();
        }
        log.debug("Error during deleting deleteFeedBackQuestion data...", e);
    }
    return true;
   }
	@Override
	public List<Object[]> getStudentDetails(PhdSynopsisDefenseForm objForm,ActionErrors errors) throws Exception {
		Session session=null;
		List<Object[]> list=null;
		try {
			session=HibernateUtil.getSession();
			String student=" select per.firstName,per.middleName,per.lastName,s.id,cl.name,cl.id,co.name,co.id "+
                           " from Student s"+
                           " join s.admAppln adm"+
                           " join adm.personalData per"+
                           " join adm.course co"+
                           " join s.classSchemewise cls"+
                           " join cls.classes cl"+
                           " where s.registerNo='"+objForm.getRegisterNo()+"'"+
                           " and cl.isActive=1"+
                           " and s.isAdmitted=1"+
                           " and s.registerNo!=''" +
                           " and adm.isCancelled=0"+
                           " and s.isHide=0"+
                           " and s.id not in ( select rejoins.student.id from ExamStudentDetentionRejoinDetails rejoins " +
                           " where ((rejoins.detain=1) or(rejoins.discontinued=1)) and ((rejoins.rejoin=0) or (rejoins.rejoin is null)))";
		Query query=session.createQuery(student);
		list=query.list();
		if(list.isEmpty() && !objForm.getRegisterNo().isEmpty()){
		 errors.add("error", new ActionError("knowledgepro.phd.SynopsisDefense.valid"));
		}if(list.isEmpty() && objForm.getRegisterNo().isEmpty()){
		 errors.add("error", new ActionError("knowledgepro.phd.SynopsisDefense.notBlank"));
		}
		return list;	
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		
	}
	@Override
	public List<PhdSynopsisDefenseBO> getPhdSynopsisdefence(PhdSynopsisDefenseForm objForm) throws Exception {
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(" select phd from PhdSynopsisDefenseBO phd"
					                       +" join phd.studentId s"
					                       +" where s.registerNo='"+objForm.getRegisterNo()+"'"
			                               +" and phd.isActive=1");
					                      
			List<PhdSynopsisDefenseBO> list=query.list();
			log.info("end of getPhdSynopsisdefence in PhdSynopsisDefenseImpl class.");
		    return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	@Override
	public boolean duplicateCheck(PhdSynopsisDefenseForm objForm,ActionErrors errors, HttpSession sessions) {
		Session session=null;
		boolean flag=false;
		PhdSynopsisDefenseBO subCategory;
		try{
			session=HibernateUtil.getSession();
			String quer="select synopsisDefense from PhdSynopsisDefenseBO synopsisDefense "+
			" join synopsisDefense.studentId s"+
			" where s.registerNo=:registerNo and synopsisDefense.name=:name and synopsisDefense.contactNo=:contact and synopsisDefense.type=:type";
			Query query=session.createQuery(quer);
			query.setString("registerNo", objForm.getRegisterNo());
			query.setString("name", objForm.getName());
			query.setString("contact", objForm.getContactNo());
			query.setString("type", objForm.getType());
			subCategory=(PhdSynopsisDefenseBO)query.uniqueResult();
			if(subCategory!=null && !subCategory.toString().isEmpty()){
				if(objForm.getId()!=0){
			      if(subCategory.getId()==objForm.getId()){
			    	  if(!subCategory.getIsActive()){
						     flag=true;
						     throw new ReActivateException(subCategory.getId());
			    	  }else{
			    		  flag=false;
			    	  }
				    
			      }else if(subCategory.getIsActive()){
				     flag=true;
				     errors.add("error", new ActionError("knowledgepro.phd.SynopsisDefense.nameexit"));
			       }
			      else{
					   flag=true;
					   objForm.setId(subCategory.getId());
					   throw new ReActivateException(subCategory.getId());
				   }
				}else if(subCategory.getIsActive()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.phd.SynopsisDefense.nameexit"));
				}
				else{
					  flag=true;
					  subCategory.setId(subCategory.getId());
					  objForm.setId(subCategory.getId());
					  throw new ReActivateException(subCategory.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.SYNOPSIS_DEFENSE_REACTIVATE));
				//saveErrors(request, errors);
				sessions.setAttribute("ReactivateId", objForm.getId());
			}
		}
		return flag;
	}
	@Override
	public boolean reactivateSynopsisDefense(PhdSynopsisDefenseForm objForm)throws Exception {
		log.info("Entering into ValuatorChargesImpl of reactivateDocExamType");
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from PhdSynopsisDefenseBO subcategory where subcategory.id="+objForm.getId();
		   Query query=session.createQuery(quer);
		   PhdSynopsisDefenseBO leave=(PhdSynopsisDefenseBO)query.uniqueResult();
		   leave.setIsActive(true);
		   leave.setModifiedBy(objForm.getUserId());
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
