package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackGroupForm;
import com.kp.cms.transactions.studentfeedback.IEvaStudentFeedBackGroupTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EvaStudentFeedBackGroupImpl implements IEvaStudentFeedBackGroupTransaction
{
	private static final Log log = LogFactory.getLog(EvaStudentFeedBackGroupImpl.class);
    public static volatile EvaStudentFeedBackGroupImpl evaStudentFeedBackGroupImpl = null;


    public static EvaStudentFeedBackGroupImpl getInstance()
    {
        if(evaStudentFeedBackGroupImpl == null)
        {
            evaStudentFeedBackGroupImpl = new EvaStudentFeedBackGroupImpl();
            return evaStudentFeedBackGroupImpl;
        } else
        {
            return evaStudentFeedBackGroupImpl;
        }
    }
    @Override
    public List<EvaStudentFeedbackGroup> getStudentFeedBackGroup()throws Exception
    {
        Session session = null;
        try{
        	session = HibernateUtil.getSession();
    		Query query = session.createQuery("from EvaStudentFeedbackGroup a where a.isActive=1");
    		List<EvaStudentFeedbackGroup> list = query.list();
    		session.flush();
    		return list;
        }catch (Exception e) {
			log.error("Error during getting EvaStudentFeedbackGroup Entry..." ,e);
			session.flush();
	        throw new ApplicationException(e);
		}
    }
    @Override
    public EvaStudentFeedbackGroup isNameExist(String name, String order, EvaStudentFeedBackGroupForm studentFeedBackGroupForm)
        throws Exception{
    	log.debug("inside isNameExist");
        Session session = null;
        String sqlString = "";
        EvaStudentFeedbackGroup evaStudentFeedbackGroup;
        EvaStudentFeedbackGroup evaname;
        EvaStudentFeedbackGroup evaorder;
        try{
        session = HibernateUtil.getSession();
        String quer = "from EvaStudentFeedbackGroup a where a.name ='"+name+"'";
        String que = "from EvaStudentFeedbackGroup a where a.disOrder="+order;
        Query quename = session.createQuery(quer);
        Query queorder = session.createQuery(que);
        evaname = (EvaStudentFeedbackGroup)quename.uniqueResult();
        evaorder=(EvaStudentFeedbackGroup)queorder.uniqueResult();
       if((evaname == null) || (evaorder == null))
        {
        	if((evaname==null) && (evaorder != null))
        	{
        	  return evaorder;
        	}
        	if((evaname!=null) && (evaorder == null))
        	{
        	  return evaname;
        	}if((evaname == null) && (evaorder == null))
        	{
        		return null;
        	}
        	 session.flush();
             log.debug("evaFacultyFeedbackGroup");
       }else{
        	return evaname;
        }
        } catch (Exception e) {
    		log.error("Error during duplcation checking..." , e);
    		session.flush();
    		//session.close();
    		throw new ApplicationException(e);
    	}
        return evaorder;
    }
    @Override
    public boolean addFeedbackGroup(EvaStudentFeedbackGroup feedbackGroup)
        throws Exception{
    	log.info("call of addFeedbackGroup in EvaStudentFeedBackGroupImpl class.");
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
        try{
        session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		transaction.begin();
		session.save(feedbackGroup);
		transaction.commit();
		isAdded = true;
		}
        catch(Exception e){
            isAdded = false;
            log.error("Unable to feedbackGroup", e);
            throw new ApplicationException(e);
        } finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        log.info("end of addFeedbackGroup in EvaStudentFeedBackGroupImpl class.");
        return isAdded;
    }
    @Override
    public EvaStudentFeedbackGroup editFeedBackGroup(int id) throws Exception {
    	log.info("call of editDesignationEntry in EvaStudentFeedBackGroupImpl class.");
    	Session session = null;
    	EvaStudentFeedbackGroup studentFeedbackGroup=null;
        try{   
        	session = HibernateUtil.getSession();
            Query query = session.createQuery("from EvaStudentFeedbackGroup c where c.id = :id");
            query.setInteger("id", id);
            studentFeedbackGroup = (EvaStudentFeedbackGroup)query.uniqueResult();
        }catch(Exception e)
        {
            log.error("Unable to editFeedBackGroup", e);
        }  finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        log.info("end of editFeedBackGroup in EvaStudentFeedBackGroupImpl class.");
        return studentFeedbackGroup;
    }
    @Override
    public boolean updateFeedbackGroup(EvaStudentFeedbackGroup studentFeedbackGroup)
        throws Exception {
    	log.info("call of updateFeedbackGroup in EvaStudentFeedBackGroupImpl class.");
        Session session=null;
        boolean isUpdated=false;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.update(studentFeedbackGroup);
            transaction.commit();
            isUpdated = true;
        }
        catch(Exception e)
        {
            isUpdated = false;
            log.error("Unable to updateFeedbackGroup", e);
            throw new ApplicationException(e);
        } finally {
    		if (session != null) {
    			session.flush();
    			session.close();
    		}
    	}
        log.info("end of updateFeedbackGroup in EvaStudentFeedBackGroupImpl class.");
        return isUpdated;
    }
    @Override
    public boolean deleteFeedBackGroup(int id, String userId)
        throws Exception {
    	log.info("call of deleteFeedBackGroup in EvaStudentFeedBackGroupImpl class.");
        Session session = null;
        boolean isDeleted = false;
        Transaction transaction = null;
        try{
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            EvaStudentFeedbackGroup studentFeedbackGroup = (EvaStudentFeedbackGroup) session.get(EvaStudentFeedbackGroup.class, id);
            studentFeedbackGroup.setLastModifiedDate(new Date());
            studentFeedbackGroup.setModifiedBy(userId);
            studentFeedbackGroup.setIsActive(Boolean.FALSE);
            session.update(studentFeedbackGroup);
            transaction.commit();
            isDeleted = true;
        }
        catch(Exception e)
        {
            isDeleted = false;
            log.error("Unable to delete FeedBackGroup", e);
        }finally {
    		if (session != null) {
    			session.flush();
    			session.close();
    		}
    	}
        log.info("end of deleteFeedBackGroup in EvaStudentFeedBackGroupImpl class.");
        return isDeleted;
    }
    @Override
    public boolean reActivateFeedBackGroup(String name, String userId, int id)
        throws Exception{
    	log.info("call of reActivateFeedBackGroup in EvaStudentFeedBackGroupImpl class.");
        Session session = null;
        Transaction transaction = null;
        boolean isActivated = false;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from EvaStudentFeedbackGroup c where c.id ="+id);
            EvaStudentFeedbackGroup studentFeedbackGroup = (EvaStudentFeedbackGroup)query.uniqueResult();
            transaction = session.beginTransaction();
            studentFeedbackGroup.setIsActive(Boolean.valueOf(true));
            studentFeedbackGroup.setModifiedBy(userId);
            studentFeedbackGroup.setLastModifiedDate(new Date());
            session.update(studentFeedbackGroup);
            transaction.commit();
            isActivated = true;
        }
        catch(Exception e)
        {
            isActivated = false;
            log.error("Unable to reActivateFeedBackGroup", e);
            if(transaction != null)
            {
                transaction.rollback();
            }
            throw new ApplicationException(e);
        }finally {
    		if (session != null) {
    			session.flush();
    			session.close();
    		}
    	}
        log.info("end of reActivateFeedBackGroup in EvaStudentFeedBackGroupImpl class.");
        return isActivated;
    }

}
