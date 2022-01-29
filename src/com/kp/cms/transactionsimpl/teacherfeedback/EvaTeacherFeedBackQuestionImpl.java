package com.kp.cms.transactionsimpl.teacherfeedback;

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

import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackGroup;
import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackQuestion;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackQuestionForm;
import com.kp.cms.transactions.teacherfeedback.IEvaTeacherFeedBackQuestionTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EvaTeacherFeedBackQuestionImpl implements IEvaTeacherFeedBackQuestionTransaction{

    private static final Log log = LogFactory.getLog(EvaTeacherFeedBackQuestionImpl.class);
    public static volatile EvaTeacherFeedBackQuestionImpl evaTeacherFeedBackQuestionImpl = null;

    public static EvaTeacherFeedBackQuestionImpl getInstance()
    {
        if(evaTeacherFeedBackQuestionImpl == null)
        {
        	evaTeacherFeedBackQuestionImpl = new EvaTeacherFeedBackQuestionImpl();
            return evaTeacherFeedBackQuestionImpl;
        } else
        {
            return evaTeacherFeedBackQuestionImpl;
        }
    }

    public List<EvaTeacherFeedbackGroup> getTeacherFeedBackGroup()
        throws Exception{
        Session session = null;
        try {
			session = HibernateUtil.getSession();
			String programHibernateQuery = "from EvaTeacherFeedbackGroup where isActive=1";
			List<EvaTeacherFeedbackGroup> feedBackGroup = session.createQuery(programHibernateQuery).list();
			session.flush();
			return feedBackGroup;
        }catch (Exception e) {
			if (session != null){
				session.flush();
			}	
			throw  new ApplicationException(e);
        }
  }

    public List<EvaTeacherFeedbackQuestion> getFeedBackQusestionList()
        throws Exception{
        log.info("call of getFeedBackQusestionList in EvaTeacherFeedBackQuestionImpl class.");
        Session session = null;
        List<EvaTeacherFeedbackQuestion> questionList;
        try
        {
        	session = InitSessionFactory.getInstance().openSession();
            Query query = session.createQuery("from EvaTeacherFeedbackQuestion sub where sub.isActive=1");
            questionList = query.list();
         }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        log.info("end of getFeedBackQusestionList in EvaTeacherFeedBackQuestionImpl class.");
        return questionList;
    }
    							  
    public boolean duplicateCheck(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm, ActionErrors errors, HttpSession ssession)
        throws Exception{
        Session session = null;
        boolean flag = false;
        EvaTeacherFeedbackQuestion teacherFeedbackQuestion;
        EvaTeacherFeedbackQuestion teacherFeedbackQuestion1;
        try
        {
            session = HibernateUtil.getSession();
            String quer = "from EvaTeacherFeedbackQuestion a where a.groupId=:group and a.question=:question";
            String que = "from EvaTeacherFeedbackQuestion a where a.groupId=:group and a.order=:order";
            Query queryy = session.createQuery(quer);
            Query query = session.createQuery(que);
            query.setString("order", teacherFeedBackQuestionForm.getOrder());
            query.setString("group", teacherFeedBackQuestionForm.getGroupId());
            queryy.setString("question", teacherFeedBackQuestionForm.getQuestion());
            queryy.setString("group", teacherFeedBackQuestionForm.getGroupId());
            teacherFeedbackQuestion = (EvaTeacherFeedbackQuestion)queryy.uniqueResult();
            teacherFeedbackQuestion1=(EvaTeacherFeedbackQuestion)query.uniqueResult();
            if((teacherFeedbackQuestion != null && !teacherFeedbackQuestion.toString().isEmpty()) || (teacherFeedbackQuestion1!=null && !teacherFeedbackQuestion1.toString().isEmpty()))
            {
                if(teacherFeedBackQuestionForm.getId() != 0)
                {   
                       	  if(((teacherFeedBackQuestionForm!=null) && (teacherFeedBackQuestionForm.getId() == teacherFeedbackQuestion.getId())) && ((teacherFeedbackQuestion1!=null) && (teacherFeedBackQuestionForm.getId() == teacherFeedbackQuestion1.getId())))
         	               {
         		               flag = false;
         	                }
                           else if((teacherFeedbackQuestion!=null) && (teacherFeedBackQuestionForm.getId() == teacherFeedbackQuestion.getId()))
                	      { 
                		     if(teacherFeedbackQuestion1==null)
                		       {
                			   flag = false;
                		       } else{
                		       flag = true;
                               errors.add("error", new ActionError("knowledgepro.studentFeedBack.grouporderexit"));
                		      }
                	      }else if((teacherFeedbackQuestion1!=null) && (teacherFeedBackQuestionForm.getId() == teacherFeedbackQuestion1.getId()))
                	      { 
                		     if(teacherFeedbackQuestion==null)
                		       {
                		       flag = false;
                	           }else{
                		       flag = true;
                               errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderexit"));
                	           }
                         }else if(teacherFeedbackQuestion1!=null && teacherFeedbackQuestion!=null )
                	      {  
                        	 flag = true;
                             errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderrexit"));
                	           
                         }else
                         {
                        flag = true;
                        teacherFeedBackQuestionForm.setGroupId(String.valueOf(teacherFeedbackQuestion1.getGroupId().getId()));
                        teacherFeedBackQuestionForm.setId(teacherFeedbackQuestion.getId());
                        throw new ReActivateException(teacherFeedbackQuestion.getId());
                    }
                } else if((teacherFeedbackQuestion!=null && teacherFeedbackQuestion.getIsActive().booleanValue()) && (teacherFeedbackQuestion1!=null && teacherFeedbackQuestion1.getIsActive().booleanValue()))
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionDataorderexit"));
                } else if(teacherFeedbackQuestion!=null && teacherFeedbackQuestion.getIsActive().booleanValue())
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderexit"));
                }else if(teacherFeedbackQuestion1!=null && teacherFeedbackQuestion1.getIsActive().booleanValue())
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.grouporderexit"));
                }else {
                    flag = true;
                    teacherFeedBackQuestionForm.setId(teacherFeedbackQuestion.getId());
                    throw new ReActivateException(teacherFeedbackQuestion.getId());
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
            errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderexit"));
            if(e instanceof ReActivateException)
            {
                errors.add("error", new ActionError("knowledgepro.studentFeedBack.reactivate"));
                ssession.setAttribute("ReactivateId", Integer.valueOf(teacherFeedBackQuestionForm.getId()));
            }
        }
        return flag;
    }

    public boolean addFeedBackQuestion(EvaTeacherFeedbackQuestion teacherFeedbackQuestion)
        throws Exception{
    	log.info("call of addFeedBackQuestion in EvaTeacherFeedBackQuestionImpl class.");
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
    	try {
    		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
    		session = sessionFactory.openSession();*/
    		session = HibernateUtil.getSession();
    		transaction = session.beginTransaction();
    		transaction.begin();
    		session.save(teacherFeedbackQuestion);
    		transaction.commit();
    		
    		isAdded = true;
    	} catch (Exception e) {
    		isAdded = false;
    		log.error("Unable to addFeedBackQuestion" , e);
    		throw new ApplicationException(e);
    	} finally {
    		if (session != null) {
    			session.flush();
    		    session.close();
    		}
    	}
    	log.info("end of addFeedBackQuestion in EvaTeacherFeedBackQuestionImpl class.");
    	return isAdded;
}

    public EvaTeacherFeedbackQuestion getFeedBackQuestionById(int id)
        throws Exception
    {
        Session session = null;
        EvaTeacherFeedbackQuestion teacherFeedbackQuestion = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from EvaTeacherFeedbackQuestion a where a.id=")).append(id).toString();
            Query query = session.createQuery(str);
            teacherFeedbackQuestion = (EvaTeacherFeedbackQuestion)query.uniqueResult();
        }
        catch (Exception e) {
    		log.error("Unable to getFeedBackQuestionById", e);
    	} finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        return teacherFeedbackQuestion;
    }

    public boolean deleteFeedBackQuestion(int id)
        throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = InitSessionFactory.getInstance().openSession();
            String str = (new StringBuilder("from EvaTeacherFeedbackQuestion a where a.id=")).append(id).toString();
            EvaTeacherFeedbackQuestion teacherFeedbackQuestion = (EvaTeacherFeedbackQuestion)session.createQuery(str).uniqueResult();
            transaction = session.beginTransaction();
            session.delete(teacherFeedbackQuestion);
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

    public boolean reActivateFeedBackQuestion(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm)
        throws Exception{
    	log.info("Entering into reActivateFeedBackQuestion of EvaTeacherFeedBackQuestionImpl");
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            String quer ="from EvaTeacherFeedbackQuestion a where a.id="+teacherFeedBackQuestionForm.getId();
            Query query = session.createQuery(quer);
            EvaTeacherFeedbackQuestion teacherFeedbackQuestion = (EvaTeacherFeedbackQuestion)query.uniqueResult();
            teacherFeedbackQuestion.setIsActive(Boolean.valueOf(true));
            teacherFeedbackQuestion.setModifiedBy(teacherFeedBackQuestionForm.getUserId());
            teacherFeedbackQuestion.setLastModifiedDate(new Date());
            session.update(teacherFeedbackQuestion);
            transaction.commit();
        }
        catch(Exception e)
        {
			log.debug("Exception" + e.getMessage());
			return false;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return true;
    }

	@Override
	public boolean updateFeedBackQuestion(EvaTeacherFeedbackQuestion teacherFeedbackQuestion)throws Exception {
		log.info("call of updateFeedBackQuestion in EvaTeacherFeedBackQuestionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(teacherFeedbackQuestion);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			log.error("Unable to updateFeedBackQuestion", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of updateFeedBackQuestion in EvaTeacherFeedBackQuestionImpl class.");
		return isUpdated;
}

}
