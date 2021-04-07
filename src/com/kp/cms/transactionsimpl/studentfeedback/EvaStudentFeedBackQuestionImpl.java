package com.kp.cms.transactionsimpl.studentfeedback;

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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackGroup;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackQuestionForm;
import com.kp.cms.transactions.studentfeedback.IEvaStudentFeedBackQuestionTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EvaStudentFeedBackQuestionImpl
    implements IEvaStudentFeedBackQuestionTransaction
{

    private static final Log log = LogFactory.getLog(EvaStudentFeedBackQuestionImpl.class);
    public static volatile EvaStudentFeedBackQuestionImpl evaStudentFeedBackQuestionImpl = null;

    public static EvaStudentFeedBackQuestionImpl getInstance()
    {
        if(evaStudentFeedBackQuestionImpl == null)
        {
            evaStudentFeedBackQuestionImpl = new EvaStudentFeedBackQuestionImpl();
            return evaStudentFeedBackQuestionImpl;
        } else
        {
            return evaStudentFeedBackQuestionImpl;
        }
    }

    public List<EvaStudentFeedbackGroup> getStudentFeedBackGroup()
        throws Exception{
        Session session = null;
        try {
			session = HibernateUtil.getSession();
			String programHibernateQuery = "from EvaStudentFeedbackGroup where isActive=1";
			List<EvaStudentFeedbackGroup> feedBackGroup = session.createQuery(programHibernateQuery).list();
			session.flush();
			return feedBackGroup;
        }catch (Exception e) {
			if (session != null){
				session.flush();
			}	
			throw  new ApplicationException(e);
        }
  }

    public List<EvaStudentFeedbackQuestion> getFeedBackQusestionList()
        throws Exception{
        log.info("call of getFeedBackQusestionList in EvaStudentFeedBackQuestionImpl class.");
        Session session = null;
        List<EvaStudentFeedbackQuestion> questionList;
        try
        {
        	session = InitSessionFactory.getInstance().openSession();
            Query query = session.createQuery("from EvaStudentFeedbackQuestion sub where sub.isActive=1");
            questionList = query.list();
         }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        log.info("end of getFeedBackQusestionList in EvaStudentFeedBackQuestionImpl class.");
        return questionList;
    }

    public boolean duplicateCheck(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm, ActionErrors errors, HttpSession ssession)
        throws Exception{
        Session session = null;
        boolean flag = false;
        EvaStudentFeedbackQuestion studentFeedbackQuestion;
        EvaStudentFeedbackQuestion studentFeedbackQuestion1;
        try
        {
            session = HibernateUtil.getSession();
            String quer = "from EvaStudentFeedbackQuestion a where a.groupId=:group and a.question=:question";
            String que = "from EvaStudentFeedbackQuestion a where a.groupId=:group and a.order=:order";
            Query queryy = session.createQuery(quer);
            Query query = session.createQuery(que);
            query.setString("order", studentFeedBackQuestionForm.getOrder());
            query.setString("group", studentFeedBackQuestionForm.getGroupId());
            queryy.setString("question", studentFeedBackQuestionForm.getQuestion());
            queryy.setString("group", studentFeedBackQuestionForm.getGroupId());
            studentFeedbackQuestion = (EvaStudentFeedbackQuestion)queryy.uniqueResult();
            studentFeedbackQuestion1=(EvaStudentFeedbackQuestion)query.uniqueResult();
            if((studentFeedbackQuestion != null && !studentFeedbackQuestion.toString().isEmpty()) || (studentFeedbackQuestion1!=null && !studentFeedbackQuestion1.toString().isEmpty()))
            {
                if(studentFeedBackQuestionForm.getId() != 0)
                {   
                       	  if(((studentFeedbackQuestion!=null) && (studentFeedBackQuestionForm.getId() == studentFeedbackQuestion.getId())) && ((studentFeedbackQuestion1!=null) && (studentFeedBackQuestionForm.getId() == studentFeedbackQuestion1.getId())))
         	               {
         		               flag = false;
         	                }
                           else if((studentFeedbackQuestion!=null) && (studentFeedBackQuestionForm.getId() == studentFeedbackQuestion.getId()))
                	      { 
                		     if(studentFeedbackQuestion1==null)
                		       {
                			   flag = false;
                		       } else{
                		       flag = true;
                               errors.add("error", new ActionError("knowledgepro.studentFeedBack.grouporderexit"));
                		      }
                	      }else if((studentFeedbackQuestion1!=null) && (studentFeedBackQuestionForm.getId() == studentFeedbackQuestion1.getId()))
                	      { 
                		     if(studentFeedbackQuestion==null)
                		       {
                		       flag = false;
                	           }else{
                		       flag = true;
                               errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderexit"));
                	           }
                         }else if(studentFeedbackQuestion1!=null && studentFeedbackQuestion!=null )
                	      {  
                        	 flag = true;
                             errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderrexit"));
                	           
                         }else
                         {
                        flag = true;
                        studentFeedBackQuestionForm.setGroupId(String.valueOf(studentFeedbackQuestion1.getGroupId().getId()));
                        studentFeedBackQuestionForm.setId(studentFeedbackQuestion.getId());
                        throw new ReActivateException(studentFeedbackQuestion.getId());
                    }
                } else if((studentFeedbackQuestion!=null && studentFeedbackQuestion.getIsActive().booleanValue()) && (studentFeedbackQuestion1!=null && studentFeedbackQuestion1.getIsActive().booleanValue()))
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionDataorderexit"));
                } else if(studentFeedbackQuestion!=null && studentFeedbackQuestion.getIsActive().booleanValue())
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderexit"));
                }else if(studentFeedbackQuestion1!=null && studentFeedbackQuestion1.getIsActive().booleanValue())
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.grouporderexit"));
                }else {
                    flag = true;
                    studentFeedBackQuestionForm.setId(studentFeedbackQuestion.getId());
                    throw new ReActivateException(studentFeedbackQuestion.getId());
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
                ssession.setAttribute("ReactivateId", Integer.valueOf(studentFeedBackQuestionForm.getId()));
            }
        }
        return flag;
    }

    public boolean addFeedBackQuestion(EvaStudentFeedbackQuestion studentFeedbackQuestion)
        throws Exception{
    	log.info("call of addDesignationEntry in designationOrderTransactionImpl class.");
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
    	try {
    		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
    		session = sessionFactory.openSession();*/
    		session = HibernateUtil.getSession();
    		transaction = session.beginTransaction();
    		transaction.begin();
    		session.save(studentFeedbackQuestion);
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

    public EvaStudentFeedbackQuestion getFeedBackQuestionById(int id)
        throws Exception
    {
        Session session = null;
        EvaStudentFeedbackQuestion studentFeedbackQuestion = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from EvaStudentFeedbackQuestion a where a.id=")).append(id).toString();
            Query query = session.createQuery(str);
            studentFeedbackQuestion = (EvaStudentFeedbackQuestion)query.uniqueResult();
        }
        catch (Exception e) {
    		log.error("Unable to editDesignationEntry", e);
    	} finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        return studentFeedbackQuestion;
    }

    public boolean deleteFeedBackQuestion(int id)
        throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = InitSessionFactory.getInstance().openSession();
            String str = (new StringBuilder("from EvaStudentFeedbackQuestion a where a.id=")).append(id).toString();
            EvaStudentFeedbackQuestion studentFeedbackQuestion = (EvaStudentFeedbackQuestion)session.createQuery(str).uniqueResult();
            transaction = session.beginTransaction();
            session.delete(studentFeedbackQuestion);
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

    public boolean reActivateFeedBackQuestion(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm)
        throws Exception{
    	log.info("Entering into reActivateFeedBackQuestion of reactivateDocExamType");
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            String quer ="from EvaStudentFeedbackQuestion a where a.id="+studentFeedBackQuestionForm.getId();
            Query query = session.createQuery(quer);
            EvaStudentFeedbackQuestion leave = (EvaStudentFeedbackQuestion)query.uniqueResult();
            leave.setIsActive(Boolean.valueOf(true));
            leave.setModifiedBy(studentFeedBackQuestionForm.getUserId());
            leave.setLastModifiedDate(new Date());
            session.update(leave);
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
	public boolean updateFeedBackQuestion(EvaStudentFeedbackQuestion studentFeedbackQuestion)throws Exception {
		log.info("call of updatedesignationEntry in designationOrderTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(studentFeedbackQuestion);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			log.error("Unable to updatedesignationEntry", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of updatedesignationEntry in designationOrderTransactionImpl class.");
		return isUpdated;
}

}
