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

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.examallotment.ExamInvigilationDutySettings;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamInvigilationDutySettingForm;
import com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author Manu
 *
 */
public class ExamInvigilationDutySettingImpl
    implements ExamInvigilationDutySettingTransaction
{

    private static final Log log = LogFactory.getLog(ExamInvigilationDutySettingImpl.class);
    public static volatile ExamInvigilationDutySettingImpl invigilationImpl = null;

    public static ExamInvigilationDutySettingImpl getInstance()
    {
        if(invigilationImpl == null)
        {
        	invigilationImpl = new ExamInvigilationDutySettingImpl();
            return invigilationImpl;
        } else
        {
            return invigilationImpl;
        }
    }
    /* (non-Javadoc)
     * @see com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction#getEmpLocation()
     */
    public List<EmployeeWorkLocationBO> getEmpLocation()throws Exception{
        Session session = null;
        try {
			session = HibernateUtil.getSession();
			String str = "from EmployeeWorkLocationBO where isActive=1";
			List<EmployeeWorkLocationBO> location = session.createQuery(str).list();
			session.flush();
			return location;
        }catch (Exception e) {
			if (session != null){
				session.flush();
			}	
			throw  new ApplicationException(e);
        }
      }
    /* (non-Javadoc)
     * @see com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction#duplicateCheck(com.kp.cms.forms.exam.ExamInvigilationDutySettingForm, org.apache.struts.action.ActionErrors, javax.servlet.http.HttpSession)
     */
    public boolean duplicateCheck(ExamInvigilationDutySettingForm invigilationForm, ActionErrors errors, HttpSession ssession)
        throws Exception{
        Session session = null;
        boolean flag = false;
        ExamInvigilationDutySettings invigilation;
        try
        {
            session = HibernateUtil.getSession();
            String quer = "from ExamInvigilationDutySettings a where a.workLocationId.id=:location and a.endMid=:endmid";
            Query query = session.createQuery(quer);
            query.setString("location", invigilationForm.getWorkLocationId());
            query.setString("endmid", invigilationForm.getEndMid());
            invigilation = (ExamInvigilationDutySettings)query.uniqueResult();
            if(invigilation != null && !invigilation.toString().isEmpty())
            {
                if(invigilationForm.getId() != 0)
                {   
                       	  if(invigilationForm.getId() == invigilation.getId())
         	               {
         		               flag = false;
         	                }
                          else if(invigilation.getIsActive())
                	      {  
                        	 flag = true;
                             errors.add("error", new ActionError("knowledgepro.admin.EmpJobTitle.name.exists"));
                	           
                         }else if(!invigilation.getIsActive())
                         {
                        flag = true;
                        invigilationForm.setId(invigilation.getId());
                        throw new ReActivateException(invigilation.getId());
                    }
                 } else if(invigilation.getIsActive())
       	            {  
                	 flag = true;
                     errors.add("error", new ActionError("knowledgepro.admin.EmpJobTitle.name.exists"));
        	           
                 }else if(!invigilation.getIsActive())
                 {
                flag = true;
                invigilationForm.setId(invigilation.getId());
                throw new ReActivateException(invigilation.getId());
            }
            } else
            {
                flag = false;
            }
        }
        catch(Exception e)
        {
            log.debug("Reactivate Exception", e);
            flag = true;
            errors.add("error", new ActionError("knowledgepro.admin.EmpJobTitle.name.exists"));
            if(e instanceof ReActivateException)
            {
                errors.add("error", new ActionError("knowledgepro.studentFeedBack.reactivate"));
                ssession.setAttribute("ReactivateId", Integer.valueOf(invigilationForm.getId()));
            }
        }
        return flag;
    }
   
    /* (non-Javadoc)
     * @see com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction#addInvigilation(com.kp.cms.bo.examallotment.ExamInvigilationDutySettings)
     */
    public boolean addInvigilation(ExamInvigilationDutySettings invigilation)throws Exception{
    	log.info("call of addDesignationEntry in designationOrderTransactionImpl class.");
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
    	try {
    		session = HibernateUtil.getSession();
    		transaction = session.beginTransaction();
    		transaction.begin();
    		session.save(invigilation);
    		transaction.commit();
    		
    		isAdded = true;
    	} catch (Exception e) {
    		isAdded = false;
    		log.error("Unable to addDesignationEntry" , e);
    		throw new ApplicationException(e);
    	} finally {
    		if (session != null) {
    			session.flush();
    		    session.close();
    		}
    	}
    	log.info("end of addDesignationEntry in designationOrderTransactionImpl class.");
    	return isAdded;
}
    /* (non-Javadoc)
     * @see com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction#getInvigilationList(com.kp.cms.forms.exam.ExamInvigilationDutySettingForm)
     */
    public List<ExamInvigilationDutySettings> getInvigilationList(ExamInvigilationDutySettingForm invigilationForm)throws Exception{
        Session session = null;
        List<ExamInvigilationDutySettings> invigilationList;
        try
        {
        	session = InitSessionFactory.getInstance().openSession();
            String str ="from ExamInvigilationDutySettings sub where sub.isActive=1";
            Query query = session.createQuery(str);
            invigilationList = query.list();
         }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        return invigilationList;
    }
    /* (non-Javadoc)
     * @see com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction#getInvigilationById(int)
     */
    public ExamInvigilationDutySettings getInvigilationById(int id) throws Exception
    {
        Session session = null;
        ExamInvigilationDutySettings invigilation = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from ExamInvigilationDutySettings a where a.id=")).append(id).toString();
            Query query = session.createQuery(str);
            invigilation = (ExamInvigilationDutySettings)query.uniqueResult();
        }
        catch (Exception e) {
    		log.error("Unable to editDesignationEntry", e);
    	} finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        return invigilation;
    }
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction#updateInvigilationDuty(com.kp.cms.bo.examallotment.ExamInvigilationDutySettings)
	 */
	@Override
	public boolean updateInvigilationDuty(ExamInvigilationDutySettings invigilation)throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(invigilation);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
}
    /* (non-Javadoc)
     * @see com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction#deleteInvigilationDuty(int)
     */
    public boolean deleteInvigilationDuty(int id) throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = InitSessionFactory.getInstance().openSession();
            String str = (new StringBuilder("from ExamInvigilationDutySettings a where a.id=")).append(id).toString();
            ExamInvigilationDutySettings roomMaster = (ExamInvigilationDutySettings)session.createQuery(str).uniqueResult();
            transaction = session.beginTransaction();
            roomMaster.setIsActive(false);
            session.merge(roomMaster);
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

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.ExamInvigilationDutySettingTransaction#reActivateInvigilationDuty(com.kp.cms.forms.exam.ExamInvigilationDutySettingForm)
	 */
	@Override
	public boolean reActivateInvigilationDuty(ExamInvigilationDutySettingForm invigilationForm) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from ExamInvigilationDutySettings document where document.id="+invigilationForm.getId();
		   Query query=session.createQuery(quer);
		   ExamInvigilationDutySettings detailsBO= (ExamInvigilationDutySettings) query.uniqueResult();
		   detailsBO.setIsActive(true);
		   detailsBO.setModifiedBy(invigilationForm.getUserId());
		   detailsBO.setLastModifiedDate(new Date());
		   session.update(detailsBO);
		   transaction.commit();
		}catch(Exception e){
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
	}
}
