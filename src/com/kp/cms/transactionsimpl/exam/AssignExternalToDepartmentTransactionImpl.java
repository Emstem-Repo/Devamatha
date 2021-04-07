package com.kp.cms.transactionsimpl.exam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.ExternalEvaluatorsDepartment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.exam.AssignExternalToDepartmentForm;
import com.kp.cms.transactions.exam.IAssignExternalToDepartmentTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class AssignExternalToDepartmentTransactionImpl implements IAssignExternalToDepartmentTransaction{
	public static volatile AssignExternalToDepartmentTransactionImpl assignExternalToDepartmentTransactionImpl = null;
	private static final Log log = LogFactory.getLog(AssignExternalToDepartmentTransactionImpl.class);

	public static AssignExternalToDepartmentTransactionImpl getInstance() {
		if (assignExternalToDepartmentTransactionImpl == null) {
			assignExternalToDepartmentTransactionImpl = new AssignExternalToDepartmentTransactionImpl();
			return assignExternalToDepartmentTransactionImpl;
		}
		return assignExternalToDepartmentTransactionImpl;
	}

	public List<Department> getDepartment() throws Exception {
        log.info("call of getAssignFee");
        List<Department> boList=null;
        Session session = null;
        try
        {
            //session = HibernateUtil.getSession();
        	session = InitSessionFactory.getInstance().openSession();
            boList = session.createQuery("from Department d where d.isActive = 1 and d.isAcademic=1 order by name").list();
        }
        catch(Exception e)
        {
            log.error("In AssignFees", e);
            throw e;
        }
        
        log.info("end of AssignFees.");
        return boList;
    }
	public List<ExamValuators> getExternalDetails() throws Exception {
        log.info("call of getAssignFee");
        List<ExamValuators> boList=null;
        Session session = null;
        try
        {
            //session = HibernateUtil.getSession();
        	session = InitSessionFactory.getInstance().openSession();
            boList = session.createQuery("from ExamValuators d where d.isActive = 1 order by name").list();
        }
        catch(Exception e)
        {
            log.error("In AssignFees", e);
            throw e;
        }
        
        log.info("end of AssignFees.");
        return boList;
    }
	
	public boolean saveEvaluators(List<ExternalEvaluatorsDepartment> bolist) throws Exception {
		log.info("Start of addCourse of CourseTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(bolist!=null){
				Iterator<ExternalEvaluatorsDepartment> iterator=bolist.iterator();
				while(iterator.hasNext()){
					ExternalEvaluatorsDepartment cd=(ExternalEvaluatorsDepartment)iterator.next();
				session.saveOrUpdate(cd);
				result = true;
				}
			}
			transaction.commit();
			session.close();
			
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving course data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving course data..." , e);
			throw new ApplicationException(e);
		}
		log.info("End of addCourse of CourseTransactionImpl");
		return result;
	}
	
	
	public Map<Integer,Integer> getExternalEvaluatorsDepartmentList(AssignExternalToDepartmentForm assignExternalToDepartmentForm) throws Exception {
        log.info("call of getAssignFee");
        Session session = null;
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
       // List<CertificateDetailsRoles> AssignList = new ArrayList<CertificateDetailsRoles>();
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("select ev.evaluators.id,ev.id from ExternalEvaluatorsDepartment ev where ev.isActive=1 and  ev.department.id="+assignExternalToDepartmentForm.getDepartmentId());
           List<Object[]> list = query.list();
            if(list!=null && !list.isEmpty()){
            	Iterator<Object[]> iterator = list.iterator();
            	while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
						map.put(Integer.parseInt(objects[0].toString()), Integer.parseInt(objects[1].toString()));
					}
				}
            }
        }
        catch(Exception e)
        {
            log.error("In AssignFees", e);
            throw e;
        }
        
        log.info("end of AssignFees.");
        return map;
    }
}
