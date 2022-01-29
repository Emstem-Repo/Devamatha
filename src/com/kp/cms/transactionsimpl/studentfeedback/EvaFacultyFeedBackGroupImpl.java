package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackGroupForm;
import com.kp.cms.transactions.studentfeedback.IEvaFacultyFeedBackGroupTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EvaFacultyFeedBackGroupImpl implements IEvaFacultyFeedBackGroupTransaction
{
	private static final Log log = LogFactory.getLog(EvaFacultyFeedBackGroupImpl.class);
    public static volatile EvaFacultyFeedBackGroupImpl evaFacultyFeedBackGroupImpl = null;


    public static EvaFacultyFeedBackGroupImpl getInstance()
    {
        if(evaFacultyFeedBackGroupImpl == null)
        {
            evaFacultyFeedBackGroupImpl = new EvaFacultyFeedBackGroupImpl();
            return evaFacultyFeedBackGroupImpl;
        } else
        {
            return evaFacultyFeedBackGroupImpl;
        }
    }
    @Override
    public List<EvaFacultyFeedBackGroup> getFacultyFeedBackGroup()throws Exception
    {
        Session session = null;
        try{
        	session = HibernateUtil.getSession();
    		Query query = session.createQuery("from EvaFacultyFeedBackGroup a where a.isActive=1");
    		List<EvaFacultyFeedBackGroup> list = query.list();
    		session.flush();
    		return list;
        }catch (Exception e) {
			log.error("Error during getting EvaFacultyFeedBackGroup Entry..." ,e);
			session.flush();
	        throw new ApplicationException(e);
		}
    }
    @Override
    public EvaFacultyFeedBackGroup isNameExist(String name, String order, EvaFacultyFeedBackGroupForm facultyFeedBackGroupForm)
        throws Exception{
    	log.debug("inside isNameExist");
        Session session = null;
        String sqlString = "";
        EvaFacultyFeedBackGroup evaFacultyFeedbackGroup;
        EvaFacultyFeedBackGroup evaname;
        EvaFacultyFeedBackGroup evaorder;
        try{
        session = HibernateUtil.getSession();
        String quer = "from EvaFacultyFeedBackGroup a where a.name ='"+name+"'";
        String que = "from EvaFacultyFeedBackGroup a where a.order="+order;
        Query quename = session.createQuery(quer);
        Query queorder = session.createQuery(que);
        evaname = (EvaFacultyFeedBackGroup)quename.uniqueResult();
        evaorder=(EvaFacultyFeedBackGroup)queorder.uniqueResult();
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
    		//session.close();z
    		throw new ApplicationException(e);
    	}
		return evaorder;
    }
    @Override
    public boolean addFeedbackGroup(EvaFacultyFeedBackGroup feedbackGroup)
        throws Exception{
    	log.info("call of addFeedbackGroup in EvaFacultyFeedBackGroupImpl class.");
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
        log.info("end of addFeedbackGroup in EvaFacultyFeedBackGroupImpl class.");
        return isAdded;
    }
    @Override
    public EvaFacultyFeedBackGroup editFeedBackGroup(int id) throws Exception {
    	log.info("call of editDesignationEntry in EvaFacultyFeedBackGroupImpl class.");
    	Session session = null;
    	EvaFacultyFeedBackGroup facultyFeedbackGroup=null;
        try{   
        	session = HibernateUtil.getSession();
            Query query = session.createQuery("from EvaFacultyFeedBackGroup c where c.id = :id");
            query.setInteger("id", id);
            facultyFeedbackGroup = (EvaFacultyFeedBackGroup)query.uniqueResult();
        }catch(Exception e)
        {
            log.error("Unable to editFeedBackGroup", e);
        }  finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        log.info("end of editFeedBackGroup in EvaFacultyFeedBackGroupImpl class.");
        return facultyFeedbackGroup;
    }
    @Override
    public boolean updateFeedbackGroup(EvaFacultyFeedBackGroup facultyFeedbackGroup)
        throws Exception {
    	log.info("call of updateFeedbackGroup in EvaFacultyFeedBackGroupImpl class.");
        Session session=null;
        boolean isUpdated=false;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.update(facultyFeedbackGroup);
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
        log.info("end of updateFeedbackGroup in EvaFacultyFeedBackGroupImpl class.");
        return isUpdated;
    }
    @Override
    public boolean deleteFeedBackGroup(int id, String userId)
        throws Exception {
    	log.info("call of deleteFeedBackGroup in EvaFacultyFeedBackGroupImpl class.");
        Session session = null;
        boolean isDeleted = false;
        Transaction transaction = null;
        try{
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            EvaFacultyFeedBackGroup facultyFeedbackGroup = (EvaFacultyFeedBackGroup) session.get(EvaFacultyFeedBackGroup.class, id);
            facultyFeedbackGroup.setLastModifiedDate(new Date());
            facultyFeedbackGroup.setModifiedBy(userId);
            facultyFeedbackGroup.setIsActive(Boolean.FALSE);
            session.update(facultyFeedbackGroup);
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
        log.info("end of deleteFeedBackGroup in EvaFacultyFeedBackGroupImpl class.");
        return isDeleted;
    }
    @Override
    public boolean reActivateFeedBackGroup(String name, String userId, int id)
        throws Exception{
    	log.info("call of reActivateFeedBackGroup in EvaFacultyFeedBackGroupImpl class.");
        Session session = null;
        Transaction transaction = null;
        boolean isActivated = false;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from EvaFacultyFeedBackGroup c where c.id ="+id);
            EvaFacultyFeedBackGroup facultyFeedbackGroup = (EvaFacultyFeedBackGroup)query.uniqueResult();
            transaction = session.beginTransaction();
            facultyFeedbackGroup.setIsActive(Boolean.valueOf(true));
            facultyFeedbackGroup.setModifiedBy(userId);
            facultyFeedbackGroup.setLastModifiedDate(new Date());
            session.update(facultyFeedbackGroup);
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
        log.info("end of reActivateFeedBackGroup in EvaFacultyFeedBackGroupImpl class.");
        return isActivated;
    }

}
