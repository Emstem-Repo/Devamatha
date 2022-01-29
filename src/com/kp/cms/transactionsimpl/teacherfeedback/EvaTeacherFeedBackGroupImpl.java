package com.kp.cms.transactionsimpl.teacherfeedback;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackGroupForm;
import com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedBackGroupTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class EvaTeacherFeedBackGroupImpl implements IEvaTeacherFeedBackGroupTransaction {

	private static final Log log = LogFactory.getLog(EvaTeacherFeedBackGroupImpl.class);
	public static volatile EvaTeacherFeedBackGroupImpl evaTeacherFeedBackGroupImpl = null;

	public static EvaTeacherFeedBackGroupImpl getInstance()
	{
		if(evaTeacherFeedBackGroupImpl == null)
		{
			evaTeacherFeedBackGroupImpl = new EvaTeacherFeedBackGroupImpl();
			return evaTeacherFeedBackGroupImpl;
		} else
		{
			return evaTeacherFeedBackGroupImpl;
		}
	}

	@Override
	public List<EvaTeacherFeedbackGroup> getTeacherFeedBackGroup()throws Exception
	{
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EvaTeacherFeedbackGroup a where a.isActive=1");
			List<EvaTeacherFeedbackGroup> list = query.list();
			session.flush();
			return list;
		}catch (Exception e) {
			log.error("Error during getting getTeacherFeedBackGroup Entry..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	@Override
	public EvaTeacherFeedbackGroup isNameExist(String name, String order, EvaTeacherFeedBackGroupForm teacherFeedBackGroupForm)
	throws Exception{
		log.debug("inside isNameExist");
		Session session = null;
		EvaTeacherFeedbackGroup evaname;
		EvaTeacherFeedbackGroup evaorder;
		try{
			session = HibernateUtil.getSession();
			String quer = "from EvaTeacherFeedbackGroup a where a.name ='"+name+"'";
			String que = "from EvaTeacherFeedbackGroup a where a.disOrder="+order;
			Query quename = session.createQuery(quer);
			Query queorder = session.createQuery(que);
			evaname = (EvaTeacherFeedbackGroup)quename.uniqueResult();
			evaorder=(EvaTeacherFeedbackGroup)queorder.uniqueResult();
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
				log.debug("evaTeacherFeedbackGroup");
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
    public boolean addFeedbackGroup(EvaTeacherFeedbackGroup feedbackGroup)
        throws Exception{
    	log.info("call of addFeedbackGroup in EvaTeacherFeedBackGroupImpl class.");
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
        log.info("end of addFeedbackGroup in EvaTeacherFeedBackGroupImpl class.");
        return isAdded;
    }
	
	@Override
    public EvaTeacherFeedbackGroup editFeedBackGroup(int id) throws Exception {
    	log.info("call of editDesignationEntry in EvaTeacherFeedBackGroupImpl class.");
    	Session session = null;
    	EvaTeacherFeedbackGroup teacherFeedbackGroup=null;
        try{   
        	session = HibernateUtil.getSession();
            Query query = session.createQuery("from EvaTeacherFeedbackGroup c where c.id = :id");
            query.setInteger("id", id);
            teacherFeedbackGroup = (EvaTeacherFeedbackGroup)query.uniqueResult();
        }catch(Exception e)
        {
            log.error("Unable to editFeedBackGroup", e);
        }  finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        log.info("end of editFeedBackGroup in EvaTeacherFeedBackGroupImpl class.");
        return teacherFeedbackGroup;
    }
	
	@Override
    public List<EvaTeacherFeedbackGroup> getStudentFeedBackGroup()throws Exception
    {
        Session session = null;
        try{
        	session = HibernateUtil.getSession();
    		Query query = session.createQuery("from EvaTeacherFeedbackGroup a where a.isActive=1");
    		List<EvaTeacherFeedbackGroup> list = query.list();
    		session.flush();
    		return list;
        }catch (Exception e) {
			log.error("Error during getting EvaTeacherFeedbackGroup Entry..." ,e);
			session.flush();
	        throw new ApplicationException(e);
		}
    }
	
	@Override
    public boolean updateFeedbackGroup(EvaTeacherFeedbackGroup teacherFeedbackGroup)
        throws Exception {
    	log.info("call of updateFeedbackGroup in EvaTeacherFeedBackGroupImpl class.");
        Session session=null;
        boolean isUpdated=false;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.update(teacherFeedbackGroup);
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
        log.info("end of updateFeedbackGroup in EvaTeacherFeedBackGroupImpl class.");
        return isUpdated;
    }
    @Override
    public boolean deleteFeedBackGroup(int id, String userId)
        throws Exception {
    	log.info("call of deleteFeedBackGroup in EvaTeacherFeedBackGroupImpl class.");
        Session session = null;
        boolean isDeleted = false;
        Transaction transaction = null;
        try{
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            EvaTeacherFeedbackGroup teacherFeedbackGroup = (EvaTeacherFeedbackGroup) session.get(EvaTeacherFeedbackGroup.class, id);
            teacherFeedbackGroup.setLastModifiedDate(new Date());
            teacherFeedbackGroup.setModifiedBy(userId);
            teacherFeedbackGroup.setIsActive(Boolean.FALSE);
            session.update(teacherFeedbackGroup);
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
        log.info("end of deleteFeedBackGroup in EvaTeacherFeedBackGroupImpl class.");
        return isDeleted;
    }
    @Override
    public boolean reActivateFeedBackGroup(String name, String userId, int id)
        throws Exception{
    	log.info("call of reActivateFeedBackGroup in EvaTeacherFeedBackGroupImpl class.");
        Session session = null;
        Transaction transaction = null;
        boolean isActivated = false;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from EvaTeacherFeedbackGroup c where c.id ="+id);
            EvaTeacherFeedbackGroup teacherFeedbackGroup = (EvaTeacherFeedbackGroup)query.uniqueResult();
            transaction = session.beginTransaction();
            teacherFeedbackGroup.setIsActive(Boolean.valueOf(true));
            teacherFeedbackGroup.setModifiedBy(userId);
            teacherFeedbackGroup.setLastModifiedDate(new Date());
            session.update(teacherFeedbackGroup);
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
        log.info("end of reActivateFeedBackGroup in EvaTeacherFeedBackGroupImpl class.");
        return isActivated;
    }
}
