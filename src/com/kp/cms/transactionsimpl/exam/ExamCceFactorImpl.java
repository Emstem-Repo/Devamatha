package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamCceFactorBO;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExamCceFactorForm;
import com.kp.cms.transactions.admission.IExamCceFactorTransactions;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class ExamCceFactorImpl implements IExamCceFactorTransactions {
	private static Log log = LogFactory.getLog(ExamCceFactorImpl.class);
	public static volatile ExamCceFactorImpl examCceFactorImpl = null;
	
	public static ExamCceFactorImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new ExamCceFactorImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	@Override
	public boolean addExamCceFactor(List<ExamCceFactorBO> examCceFactorBO,ActionErrors errors, ExamCceFactorForm objForm,HttpSession hsession) throws Exception {
        Session session;
        Transaction transaction;
        session = null;
        transaction = null;
        ExamCceFactorBO ccelist;
        Iterator<ExamCceFactorBO> itr=examCceFactorBO.iterator();
        boolean flag=true;
       try{
    	  while (itr.hasNext()) {
			ExamCceFactorBO examCceFactors = (ExamCceFactorBO) itr.next();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			String quer="from ExamCceFactorBO cceFactorBO where cceFactorBO.subjectId="+examCceFactors.getSubjectId().getId() +" and cceFactorBO.examId="+examCceFactors.getExamId().getId();
			Query query=session.createQuery(quer);
			ccelist=(ExamCceFactorBO)query.uniqueResult();
			if(ccelist==null){
				session.save(examCceFactors);
			 }if(ccelist!=null && ccelist.getIsActive()){
				 if(ccelist.getId()==examCceFactors.getId()){
					 session.merge(examCceFactors);
				 }else{
				  errors.add("error", new ActionError("knowledgepro.admin.BookingRequirements.name.exists"));
				  flag=false;
				 }
				 }
			if(ccelist!=null && !ccelist.getIsActive()){
					ccelist.setIsActive(true);
					ccelist.setCceFactor(examCceFactors.getCceFactor());
					session.update(ccelist);
				 }
			
    	  }
		transaction.commit();
		}catch(Exception e){
				if (transaction != null)
				transaction.rollback();
				session.close();
			log.debug("Error during saving data...", e);
		}finally{
			session.flush();
			session.close();
		}
        return flag;
    }
	@Override
	public List<ExamCceFactorBO> getExamCceFactorList(String year) throws Exception {
        Session session = null;
        try {
			session = HibernateUtil.getSession();
			String programHibernateQuery = "from ExamCceFactorBO au where au.isActive=1 and au.academicYear="+year;
			List<ExamCceFactorBO> cceFactor = session.createQuery(programHibernateQuery).list();
			session.flush();
			return cceFactor;
        }catch (Exception e) {
			if (session != null){
				session.flush();
			}	
			throw  new ApplicationException(e);
        }
  }
	@Override
	public ExamCceFactorBO getExamCceFactorById(int id) throws Exception {
        Session session = null;
        ExamCceFactorBO examCceFactorBO = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from ExamCceFactorBO a where a.id=")).append(id).toString()+" and a.isActive=1";
            Query query = session.createQuery(str);
            examCceFactorBO = (ExamCceFactorBO)query.uniqueResult();
            session.flush();
        }
        catch(Exception e)
        {
            log.error("Error during getting getFeedBackQuestionById by id...", e);
            session.flush();
            session.close();
        }
        return examCceFactorBO;
    }
    /**
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteExamCceFactor(int id)
    throws Exception
{
    Session session = null;
    Transaction transaction = null;
    try
    {
        session = InitSessionFactory.getInstance().openSession();
        String str = (new StringBuilder("from ExamCceFactorBO a where a.id=")).append(id).toString();
        ExamCceFactorBO examCceFactor = (ExamCceFactorBO)session.createQuery(str).uniqueResult();
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
	public boolean updateExamCceFactor(List<ExamCceFactorBO> examCceFactor,
			ActionErrors errors, ExamCceFactorForm objForm, HttpSession hsession) {
        Session session;
        Transaction transaction;
        session = null;
        transaction = null;
        ExamCceFactorBO ccelist;
        Iterator<ExamCceFactorBO> itr=examCceFactor.iterator();
       try{
    	  while (itr.hasNext()) {
			ExamCceFactorBO examCceFactors = (ExamCceFactorBO) itr.next();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			String quer="from ExamCceFactorBO cceFactorBO where cceFactorBO.subjectId="+examCceFactors.getSubjectId().getId() +" and cceFactorBO.examId="+examCceFactors.getExamId().getId();
			Query query=session.createQuery(quer);
			ccelist=(ExamCceFactorBO)query.uniqueResult();
			if(ccelist==null){
				session.save(examCceFactors);
			}
			if(ccelist!=null){
				ExamDefinition exam=new ExamDefinition();
				Subject subject=new Subject();
				exam.setId(examCceFactors.getExamId().getId());
				subject.setId(examCceFactors.getSubjectId().getId());
				ccelist.setExamId(exam);
				ccelist.setSubjectId(subject);
				ccelist.setCceFactor(examCceFactors.getCceFactor());
				ccelist.setAcademicYear(examCceFactors.getAcademicYear());
				ccelist.setLastModifiedDate(new Date());
				ccelist.setModifiedBy(objForm.getUserId());
				ccelist.setIsActive(Boolean.valueOf(true));
				session.update(ccelist);
			 }
			if(ccelist!=null && !ccelist.getIsActive()){
					ccelist.setIsActive(true);
					ccelist.setCceFactor(examCceFactors.getCceFactor());
					session.update(ccelist);
				 }
    	  }
		transaction.commit();
		}catch(Exception e){
				if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", e);
		}finally{
			session.flush();
			session.close();
		}
        return true;
    }
}
