package com.kp.cms.transactionsimpl.admission;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.UncheckGeneratedSmartCardForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IUncheckGeneratedSmartCardTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class UncheckGeneratedSmartCardTransactionimpl implements IUncheckGeneratedSmartCardTransaction {
	/**
	 * Singleton object of UncheckGeneratedSmartCardTransactionimpl
	 */
	private static volatile UncheckGeneratedSmartCardTransactionimpl uncheckGeneratedSmartCardTransactionimpl = null;
	private static final Log log = LogFactory.getLog(UncheckGeneratedSmartCardTransactionimpl.class);
	private UncheckGeneratedSmartCardTransactionimpl() {
		
	}
	/**
	 * return singleton object of UncheckGeneratedSmartCardTransactionimpl.
	 * @return
	 */
	public static UncheckGeneratedSmartCardTransactionimpl getInstance() {
		if (uncheckGeneratedSmartCardTransactionimpl == null) {
			uncheckGeneratedSmartCardTransactionimpl = new UncheckGeneratedSmartCardTransactionimpl();
		}
		return uncheckGeneratedSmartCardTransactionimpl;
	}
	/* (non-Javadoc)
	 * Querying the database to get the list of students for whom smart card data has been generated according to the input
	 * @see com.kp.cms.transactions.admission.IUncheckGeneratedSmartCardTransaction#getGeneratedStudentList()
	 */
	@Override
	public List<Student> getGeneratedStudentList(UncheckGeneratedSmartCardForm uncheckGenSCForm) throws Exception {

		Session session = null;
		List<Student> scDataGeneratedStudentsList = null;
		try {
			session = HibernateUtil.getSession();
			String query= "from Student s where s.isAdmitted=1 and s.admAppln.isCancelled=0" +
					" and (s.isHide=0 or s.isHide is null) and s.isSCDataGenerated=1  and s.admAppln.appliedYear="+uncheckGenSCForm.getAcademicYear();
			
			if(uncheckGenSCForm.getProgramTypeId()!=null && !uncheckGenSCForm.getProgramTypeId().isEmpty()){
				query+=" and s.admAppln.courseBySelectedCourseId.program.programType.id= "+uncheckGenSCForm.getProgramTypeId();
			}
			if(uncheckGenSCForm.getProgramId()!=null && !uncheckGenSCForm.getProgramId().isEmpty()){
				query+=" and s.admAppln.courseBySelectedCourseId.program.id= "+uncheckGenSCForm.getProgramId();
			}
			if(uncheckGenSCForm.getCourseId()!=null && !uncheckGenSCForm.getCourseId().isEmpty()){
				query+=" and s.admAppln.courseBySelectedCourseId.id= "+uncheckGenSCForm.getCourseId();
			}
			if(uncheckGenSCForm.getRegNoFrom()!=null && !uncheckGenSCForm.getRegNoFrom().isEmpty()){
				query+=" and s.registerNo >= '"+uncheckGenSCForm.getRegNoFrom()+"'";
			}
			if(uncheckGenSCForm.getRegNoTo()!=null && !uncheckGenSCForm.getRegNoTo().isEmpty()){
				query+=" and s.registerNo <= '"+uncheckGenSCForm.getRegNoTo()+"'";
			}
			query+="  order by s.admAppln.courseBySelectedCourseId.id, s.registerNo";
			scDataGeneratedStudentsList = session.createQuery(query).list();
			return scDataGeneratedStudentsList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * Updating the isSCDataGenerated flag in Student for the list of selected students 
	 * @see com.kp.cms.transactions.admission.IUncheckGeneratedSmartCardTransaction#updateGeneratedFlag(java.util.List)
	 */
	@Override
	public boolean updateGeneratedFlag(List<StudentTO> generatedStudentList,UncheckGeneratedSmartCardForm uncheckForm) throws Exception {
		Session session = null;
		boolean flagSet=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(generatedStudentList!=null && !generatedStudentList.isEmpty()){
				Iterator<StudentTO> itr=generatedStudentList.iterator();
				while (itr.hasNext()) {
					StudentTO studentTO = (StudentTO) itr.next();
					if(studentTO.getChecked1()!=null && studentTO.getChecked1().equalsIgnoreCase("On")){
						Student st= (Student)session.get(Student.class,studentTO.getId());
						st.setIsSCDataGenerated(false);
						st.setIsSCDataDelivered(false);
						st.setLastModifiedDate(new Date());
						st.setModifiedBy(uncheckForm.getUserId());
						session.update(st);
						flagSet=true;
					}
				}
			}
			txn.commit();
			return flagSet;
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * Querying the database to get the list of employees whose SCData is generated for the input criteria
	 * @see com.kp.cms.transactions.admission.IUncheckGeneratedSmartCardTransaction#getGeneratedEmployeeList(com.kp.cms.forms.admission.UncheckGeneratedSmartCardForm)
	 */
	@Override
	public List<Employee> getGeneratedEmployeeList(UncheckGeneratedSmartCardForm uncheckForm) throws Exception {

		Session session = null;
		List<Employee> scDataGeneratedEmployeeList = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			String query= "from Employee e where e.active=1 and e.isActive=1 and e.isSCDataGenerated=1 ";
			if(uncheckForm.getDepartmentId()!=null && !uncheckForm.getDepartmentId().trim().isEmpty()){
				query=query+" and e.department.id="+uncheckForm.getDepartmentId();
			}
			if(uncheckForm.getEmpIdFrom()!=null && !uncheckForm.getEmpIdFrom().trim().isEmpty()){
				query+=" and e.fingerPrintId >= '"+uncheckForm.getEmpIdFrom()+"'";
			}
			if(uncheckForm.getEmpIdTo()!=null && !uncheckForm.getEmpIdTo().trim().isEmpty()){
				query+=" and e.fingerPrintId <= '"+uncheckForm.getEmpIdTo()+"'";
			}
			query+="  order by e.department.id,e.fingerPrintId";
			scDataGeneratedEmployeeList = session.createQuery(query).list();
			return scDataGeneratedEmployeeList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public boolean updateGeneratedEmployeeFlag(List<EmployeeTO> generatedEmployeeList,UncheckGeneratedSmartCardForm uncheckForm) throws Exception {
		Session session = null;
		boolean flagSet=false;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(generatedEmployeeList!=null && !generatedEmployeeList.isEmpty()){
				Iterator<EmployeeTO> itr=generatedEmployeeList.iterator();
				while (itr.hasNext()) {
					EmployeeTO employeeTO = (EmployeeTO) itr.next();
					if(employeeTO.getChecked1()!=null && employeeTO.getChecked1().equalsIgnoreCase("On")){
						Employee emp= (Employee)session.get(Employee.class,employeeTO.getId());
						emp.setIsSCDataGenerated(false);
						emp.setIsSCDataDelivered(false);
						emp.setLastModifiedDate(new Date());
						emp.setModifiedBy(uncheckForm.getUserId());
						session.update(emp);
						flagSet=true;
					}
				}
			}
			txn.commit();
			return flagSet;
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
}
