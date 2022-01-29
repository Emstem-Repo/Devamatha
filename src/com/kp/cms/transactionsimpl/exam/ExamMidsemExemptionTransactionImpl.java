package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidsemExemption;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamMidsemExemptionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author dIlIp
 *
 */
public class ExamMidsemExemptionTransactionImpl implements IExamMidsemExemptionTransaction{
	
	private static volatile ExamMidsemExemptionTransactionImpl examMidsemExemptionTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ExamMidsemExemptionTransactionImpl.class);
	private ExamMidsemExemptionTransactionImpl() {
		
	}
	/**
	 * return singleton object of ExamMidsemExemptionTransactionImpl.
	 * @return
	 */
	public static ExamMidsemExemptionTransactionImpl getInstance() {
		if (examMidsemExemptionTransactionImpl == null) {
			examMidsemExemptionTransactionImpl = new ExamMidsemExemptionTransactionImpl();
		}
		return examMidsemExemptionTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction#getExamByYear(int)
	 */
	public ArrayList<KeyValueTO> getExamByYear(int academicYear) throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		
		String SQL_QUERY = "from ExamDefinition e where e.examType.id='4' "+
				"and e.delIsActive = 1 and e.isActive = 1 and academicYear="+academicYear+" order by e.id";

		Query query = session.createQuery(SQL_QUERY);

		List<ExamDefinition> list = query.list();
		Iterator<ExamDefinition> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinition row = (ExamDefinition) it.next();
			listTO.add(new KeyValueTO(row.getId(), row.getName()));
		}
		return listTO;

	}
	public ArrayList<KeyValueTO> getExamByYearOnly(int academicYear) throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		
		String SQL_QUERY = "from ExamDefinition e where e.examType.id='5' "+
				"and e.delIsActive = 1 and e.isActive = 1 and academicYear="+academicYear+" order by e.id";

		Query query = session.createQuery(SQL_QUERY);

		List<ExamDefinition> list = query.list();
		Iterator<ExamDefinition> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinition row = (ExamDefinition) it.next();
			listTO.add(new KeyValueTO(row.getId(), row.getName()));
		}
		return listTO;

	}
	
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction#getclassByRegNo(int)
	 */
	public ArrayList<KeyValueTO> getclassByRegNo(int regNo) throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		
		String SQL_QUERY = "from Student s where s.isAdmitted=1 and s.admAppln.isCancelled=0" +
		" and (s.isHide=0 or s.isHide is null) and s.registerNo='"+regNo+"'";

		Query query = session.createQuery(SQL_QUERY);

		List<Student> list = query.list();
		Iterator<Student> it = list.iterator();
		while (it.hasNext()) {
			Student sturow = (Student) it.next();
			listTO.add(new KeyValueTO(sturow.getClassSchemewise().getClasses().getId(), sturow.getClassSchemewise().getClasses().getName()));
			
			if(sturow.getStudentPreviousClassesHistory()!=null && !sturow.getStudentPreviousClassesHistory().isEmpty()){
				Set<StudentPreviousClassHistory> stuClassHistory =sturow.getStudentPreviousClassesHistory();
				Iterator<StudentPreviousClassHistory> itrClass = stuClassHistory.iterator();
				while (itrClass.hasNext()) {
					StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrClass.next();
					listTO.add(new KeyValueTO(studentPreviousClassHistory.getClasses().getId(), studentPreviousClassHistory.getClasses().getName()));
				}
			}
		}
		return listTO;

	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction#getExamName(com.kp.cms.forms.exam.ExamMidsemExemptionForm)
	 */
	public ExamDefinitionBO getExamName(ExamMidsemExemptionForm exemptionForm) throws Exception {
		
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String strQuery="from ExamDefinitionBO e where e.id="+exemptionForm.getExamId() ;
			Query query = session.createQuery(strQuery);
			
			ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) query.uniqueResult();
			session.flush();
			session.close();
			//sessionFactory.close();
			return examDefinitionBO;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction#getStudentBO(com.kp.cms.forms.exam.ExamMidsemExemptionForm)
	 */
	public Student getStudentBO(ExamMidsemExemptionForm exemptionForm) throws Exception {
		Session session = null;
		Student stu= null;
		String query="";
		try {
			session = HibernateUtil.getSession();
			
			query= "from Student s where s.isAdmitted=1 and s.admAppln.isCancelled=0" +
					" and (s.isHide=0 or s.isHide is null) and s.registerNo='"+exemptionForm.getRegNo()+"'";
			stu =(Student) session.createQuery(query).uniqueResult();
			
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return stu;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction#save(com.kp.cms.bo.exam.ExamMidsemExemption)
	 */
	public boolean save(ExamMidsemExemption exemptionBO) throws Exception {
		
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(exemptionBO);
			txn.commit();
			session.flush();
			//session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveSubjects..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during saveSubjects..." + e);
			throw new ApplicationException(e);
		}
		return result;

	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamMidsemExemptionTransaction#getPreviousSelected(com.kp.cms.forms.exam.ExamMidsemExemptionForm, int)
	 */
	public ExamMidsemExemption getPreviousSelected(ExamMidsemExemptionForm exemptionForm, int studentId) throws Exception {
		
		Session session = null;
		try {
/*			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String strQuery="from ExamMidsemExemption e where e.isActive=1 and e.examId="+exemptionForm.getExamId()+" and e.studentId="+studentId+" and e.classId="+exemptionForm.getClassId();
			Query query = session.createQuery(strQuery);
			
			ExamMidsemExemption exemption = (ExamMidsemExemption) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return exemption;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	
		
	}
	
	
	
}
