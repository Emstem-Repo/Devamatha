package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.studentfeedback.EvaHiddenSubjectTeacher;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.studentfeedback.EvaHiddenSubjectTeacherForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.studentfeedback.EvaHiddenSubjectTeacherTo;
import com.kp.cms.transactions.studentfeedback.IEvaHiddenSubjectTeacherTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class EvaHiddenSubjectTeacherImpl
    implements IEvaHiddenSubjectTeacherTransaction
{

    private static final Log log = LogFactory.getLog(EvaHiddenSubjectTeacherImpl.class);
    public static volatile EvaHiddenSubjectTeacherImpl evaStudentFeedBackQuestionImpl = null;

    public static EvaHiddenSubjectTeacherImpl getInstance()
    {
        if(evaStudentFeedBackQuestionImpl == null)
        {
            evaStudentFeedBackQuestionImpl = new EvaHiddenSubjectTeacherImpl();
            return evaStudentFeedBackQuestionImpl;
        } else
        {
            return evaStudentFeedBackQuestionImpl;
        }
    }
    public boolean deleteHiddenSubject(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm)
        throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        Boolean isHide=false;
        try
        {
            session = HibernateUtil.getSession();
            Query query=session.createQuery("from EvaHiddenSubjectTeacher a where a.id="+evaHiddenSubjectTeacherForm.getId());
            EvaHiddenSubjectTeacher hiddenSubjectTeacher = (EvaHiddenSubjectTeacher)query.uniqueResult();
            transaction = session.beginTransaction();
            session.delete(hiddenSubjectTeacher);
            transaction.commit();
            isHide=true;
         }
        catch(Exception e)
        {
        	isHide=false;
            if(transaction != null)
            {
                transaction.rollback();
            }
            log.debug("Error during deleting deleteFeedBackQuestion data...", e);
        }finally {
    		if (session != null) {
    			session.flush();
    			session.close();
    		}
    	}
        return isHide;
    }
	@Override
	public List<EvaHiddenSubjectTeacher> getHideTeacherList(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception {
		log.info("call of getHideTeacherList in EvaHiddenSubjectTeacherImpl class.");
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(" from EvaHiddenSubjectTeacher hide"
					                       +" where hide.classId.id="+evaHiddenSubjectTeacherForm.getClassesId()
					                       +" and classId.curriculumSchemeDuration.academicYear="+evaHiddenSubjectTeacherForm.getYear()
					                       +" order by hide.subjectId.name");
			List<EvaHiddenSubjectTeacher> teacher=query.list();
			
			log.info("end of getHideTeacherList in EvaHiddenSubjectTeacherImpl class.");
		    return teacher;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	       
	}
	@Override
	public List<Object[]> getTeacherClassList(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception {
		log.info("call of getTeacherClassList in EvaHiddenSubjectTeacherImpl class.");
		Session session=null;
		
		try {
			session=HibernateUtil.getSession();
			Query query=session.createSQLQuery(" select teacher.id,classes.name,users.user_name,subject.name name1,subject.code"
					                      + " from teacher_class_subject teacher "
					                      + " left join eva_hidden_subject_teacher hidden on hidden.subject_id = teacher.subject_id "
					                      + " and hidden.teacher_id = teacher.teacher_id"
					                      + " and hidden.class_schemewise_id=teacher.class_schemewise_id"
					                      + " inner join class_schemewise ON teacher.class_schemewise_id = class_schemewise.id"
					                      + " inner join classes ON class_schemewise.class_id = classes.id"
					                      + " inner join subject ON teacher.subject_id = subject.id"
					                      + " inner join users ON teacher.teacher_id = users.id"
					                      + " where teacher.is_active=1 "
					                      + " and teacher.year="+evaHiddenSubjectTeacherForm.getYear()
					                      + " and class_schemewise.id="+evaHiddenSubjectTeacherForm.getClassesId()
					                      + " and hidden.id is null"
										  + " and users.is_active=1"
										  + " and classes.is_active=1"
										  + " and subject.is_active"
										  +" order by subject.name");
		
			List<Object[]> classsubject=query.list();		   
			log.info("end of getHideTeacherList in EvaHiddenSubjectTeacherImpl class.");
			return classsubject;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		 
	  
	}
	@Override
	public TeacherClassSubject setHideTeacherList(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm)
			throws Exception {
		Session session=null;
		TeacherClassSubject teacherClassSubject=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from TeacherClassSubject teacher where teacher.id="+evaHiddenSubjectTeacherForm.getId());
			teacherClassSubject=(TeacherClassSubject)query.uniqueResult();	   
			                     
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of editDesignationEntry in EvaHiddenSubjectTeacherImpl class.");
		return teacherClassSubject;
		
	}
	@Override
	public boolean addHidelist(EvaHiddenSubjectTeacher hidelist)throws Exception {
		log.info("call of addDesignationEntry in EvaHiddenSubjectTeacherImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(hidelist);
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
		log.info("end of addDesignationEntry in EvaHiddenSubjectTeacherImpl class.");
		return isAdded;
}
	@Override
	public boolean duplicateCheck(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm,ActionErrors errors) throws Exception {
        Session session=null;
        boolean flag=false;
        TeacherClassSubject teacherClassSubject;
        EvaHiddenSubjectTeacher hiddenSubjectTeacher;
        try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from TeacherClassSubject a where a.id="+evaHiddenSubjectTeacherForm.getId());
			teacherClassSubject=(TeacherClassSubject)query.uniqueResult();
        	Query querry=session.createQuery("from EvaHiddenSubjectTeacher hide where hide.classId.id="+teacherClassSubject.getClassId().getId()
					                        + " and hide.teacherId.id="+teacherClassSubject.getTeacherId().getId()
					                        + " and hide.subjectId.id="+teacherClassSubject.getSubject().getId());
			hiddenSubjectTeacher=(EvaHiddenSubjectTeacher)querry.uniqueResult();
			if(hiddenSubjectTeacher!=null){
				 flag = true;
	             errors.add("error", new ActionError("knowledgepro.teacher.subject.hidden"));
			}else
			{
				 flag = false;
			}
		} catch (Exception e) {
			  log.debug("Exception", e);
	            flag = true;
		}
		return flag;
	}
	
}
