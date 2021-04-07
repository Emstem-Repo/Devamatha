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
import org.hibernate.Transaction;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackQuestionForm;
import com.kp.cms.transactions.studentfeedback.IEvaFacultyFeedBackQuestionTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EvaFacultyFeedBackQuestionImpl
    implements IEvaFacultyFeedBackQuestionTransaction
{

    private static final Log log = LogFactory.getLog(EvaFacultyFeedBackQuestionImpl.class);
    public static volatile EvaFacultyFeedBackQuestionImpl evaFacultyFeedBackQuestionImpl = null;

    public static EvaFacultyFeedBackQuestionImpl getInstance()
    {
        if(evaFacultyFeedBackQuestionImpl == null)
        {
            evaFacultyFeedBackQuestionImpl = new EvaFacultyFeedBackQuestionImpl();
            return evaFacultyFeedBackQuestionImpl;
        } else
        {
            return evaFacultyFeedBackQuestionImpl;
        }
    }

    public List<EvaFacultyFeedBackGroup> getFacultyFeedBackGroup()
        throws Exception{
        Session session = null;
        try {
			session = HibernateUtil.getSession();
			String programHibernateQuery = "from EvaFacultyFeedBackGroup where isActive=1";
			List<EvaFacultyFeedBackGroup> feedBackGroup = session.createQuery(programHibernateQuery).list();
			return feedBackGroup;
        }catch (Exception e) {
			if (session != null){
				session.flush();
			}	
			throw  new ApplicationException(e);
        }
  }

    public List<EvaFacultyFeedbackQuestion> getFeedBackQusestionList()
        throws Exception{
        log.info("call of getFeedBackQusestionList in EvaFacultyFeedBackQuestionImpl class.");
        Session session = null;
        List<EvaFacultyFeedbackQuestion> questionList;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from EvaFacultyFeedbackQuestion sub where sub.isActive=1");
            questionList = query.list();
        }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        log.info("end of getFeedBackQusestionList in EvaFacultyFeedBackQuestionImpl class.");
        return questionList;
    }

    public boolean duplicateCheck(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm, ActionErrors errors, HttpSession ssession)
        throws Exception{
        Session session = null;
        boolean flag = false;
        EvaFacultyFeedbackQuestion facultyFeedbackQuestion;
        EvaFacultyFeedbackQuestion facultyFeedbackQuestion1;
        try
        {
            session = HibernateUtil.getSession();
            String quer = "from EvaFacultyFeedbackQuestion a where a.groupId=:group and a.question=:question";
            String que = "from EvaFacultyFeedbackQuestion a where a.groupId=:group and a.order=:order";
            Query queryy = session.createQuery(quer);
            Query query = session.createQuery(que);
            query.setString("order", facultyFeedBackQuestionForm.getOrder());
            query.setString("group", facultyFeedBackQuestionForm.getGroupId());
            queryy.setString("question", facultyFeedBackQuestionForm.getQuestion());
            queryy.setString("group", facultyFeedBackQuestionForm.getGroupId());
            facultyFeedbackQuestion = (EvaFacultyFeedbackQuestion)queryy.uniqueResult();
            facultyFeedbackQuestion1=(EvaFacultyFeedbackQuestion)query.uniqueResult();
            if((facultyFeedbackQuestion != null && !facultyFeedbackQuestion.toString().isEmpty()) || (facultyFeedbackQuestion1!=null && !facultyFeedbackQuestion1.toString().isEmpty()))
            {
                if(facultyFeedBackQuestionForm.getId() != 0)
                {   
                       	  if(((facultyFeedbackQuestion!=null) && (facultyFeedBackQuestionForm.getId() == facultyFeedbackQuestion.getId())) && ((facultyFeedbackQuestion1!=null) && (facultyFeedBackQuestionForm.getId() == facultyFeedbackQuestion1.getId())))
         	               {
         		               flag = false;
         	                }
                           else if((facultyFeedbackQuestion!=null) && (facultyFeedBackQuestionForm.getId() == facultyFeedbackQuestion.getId()))
                	      { 
                		     if(facultyFeedbackQuestion1==null)
                		       {
                			   flag = false;
                		       } else{
                		       flag = true;
                               errors.add("error", new ActionError("knowledgepro.studentFeedBack.grouporderexit"));
                		      }
                	      }else if((facultyFeedbackQuestion1!=null) && (facultyFeedBackQuestionForm.getId() == facultyFeedbackQuestion1.getId()))
                	      { 
                		     if(facultyFeedbackQuestion==null)
                		       {
                		       flag = false;
                	           }else{
                		       flag = true;
                               errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderexit"));
                	           }
                         }else if(facultyFeedbackQuestion1!=null && facultyFeedbackQuestion!=null )
                	      {  
                        	 flag = true;
                             errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderrexit"));
                	           
                         }else
                         {
                        flag = true;
                        facultyFeedBackQuestionForm.setGroupId(String.valueOf(facultyFeedbackQuestion1.getGroupId().getId()));
                        facultyFeedBackQuestionForm.setId(facultyFeedbackQuestion.getId());
                       // throw new ReActivateException(facultyFeedbackQuestion.getId());
                    }
                } else if((facultyFeedbackQuestion!=null && facultyFeedbackQuestion.getIsActive().booleanValue()) && (facultyFeedbackQuestion1!=null && facultyFeedbackQuestion1.getIsActive().booleanValue()))
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionDataorderexit"));
                } else if(facultyFeedbackQuestion!=null && facultyFeedbackQuestion.getIsActive().booleanValue())
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionnameorderexit"));
                }else if(facultyFeedbackQuestion1!=null && facultyFeedbackQuestion1.getIsActive().booleanValue())
                {
                    flag = true;
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.grouporderexit"));
                }else {
                    flag = true;
                    facultyFeedBackQuestionForm.setId(facultyFeedbackQuestion.getId());
                    throw new ReActivateException(facultyFeedbackQuestion.getId());
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
                ssession.setAttribute("ReactivateId", Integer.valueOf(facultyFeedBackQuestionForm.getId()));
            }
        }
        return flag;
    }

    public boolean addFeedBackQuestion(EvaFacultyFeedbackQuestion facultyFeedbackQuestion, String mode)
        throws Exception{
        Session session;
        Transaction transaction;
        session = null;
        transaction = null;
        try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(facultyFeedbackQuestion);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.merge(facultyFeedbackQuestion);
			}
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}finally{
			session.flush();
			session.close();
		}
        return true;
    }

    public EvaFacultyFeedbackQuestion getFeedBackQuestionById(int id)
        throws Exception
    {
        Session session = null;
        EvaFacultyFeedbackQuestion facultyFeedbackQuestion = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from EvaFacultyFeedbackQuestion a where a.id=")).append(id).toString();
            Query query = session.createQuery(str);
            facultyFeedbackQuestion = (EvaFacultyFeedbackQuestion)query.uniqueResult();
        }
        catch(Exception e)
        {
            log.error("Error during getting getFeedBackQuestionById by id...", e);
            session.flush();
            session.close();
        }
        return facultyFeedbackQuestion;
    }

    public boolean deleteFeedBackQuestion(int id)
        throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = InitSessionFactory.getInstance().openSession();
            String str = (new StringBuilder("from EvaFacultyFeedbackQuestion a where a.id=")).append(id).toString();
            EvaFacultyFeedbackQuestion facultyFeedbackQuestion = (EvaFacultyFeedbackQuestion)session.createQuery(str).uniqueResult();
            transaction = session.beginTransaction();
            session.delete(facultyFeedbackQuestion);
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

    public boolean reActivateFeedBackQuestion(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm)
        throws Exception{
    	log.info("Entering into reActivateFeedBackQuestion of reactivateDocExamType");
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            String quer ="from EvaFacultyFeedbackQuestion a where a.id="+facultyFeedBackQuestionForm.getId();
            Query query = session.createQuery(quer);
            EvaFacultyFeedbackQuestion leave = (EvaFacultyFeedbackQuestion)query.uniqueResult();
            leave.setIsActive(Boolean.valueOf(true));
            leave.setModifiedBy(facultyFeedBackQuestionForm.getUserId());
            leave.setLastModifiedDate(new Date());
            session.update(leave);
            transaction.commit();
        }
        catch(Exception e)
        {
			log.debug("Exception" + e.getMessage());
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
    }

}
