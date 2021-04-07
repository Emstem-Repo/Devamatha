package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpOnlineLeave;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.forms.employee.ModifyEmployeeLeaveForm;
import com.kp.cms.transactions.employee.ILeaveInitializationTransaction;
import com.kp.cms.transactions.exam.IModifyIEmployeeLeaveTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ModifyEmployeeLeaveTransactionImpl implements IModifyIEmployeeLeaveTransaction {
	/**
	 * Singleton object of ModifyEmployeeLeaveTransactionImpl
	 */
	private static volatile ModifyEmployeeLeaveTransactionImpl modifyEmployeeLeaveTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ModifyEmployeeLeaveTransactionImpl.class);
	private ModifyEmployeeLeaveTransactionImpl() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveTransactionImpl.
	 * @return
	 */
	public static ModifyEmployeeLeaveTransactionImpl getInstance() {
		if (modifyEmployeeLeaveTransactionImpl == null) {
			modifyEmployeeLeaveTransactionImpl = new ModifyEmployeeLeaveTransactionImpl();
		}
		return modifyEmployeeLeaveTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction#getEmployeeDetails(java.lang.String, java.lang.String)
	 */
	@Override
	public Object[] getEmployeeDetails(String empCode, String fingerPrintId) throws Exception {
		Session session = null;
		Object[] selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="select e.firstName, e.middleName,e.lastName, e.department.name, e.designation.name ,e.id" +
			" from Employee e where e.isActive=1";
			if(empCode!=null && !empCode.isEmpty()){
				query=query+" and e.code=" +empCode;
						
			}
			if(fingerPrintId!=null && !fingerPrintId.isEmpty()){
				query=query+" and e.fingerPrintId="+fingerPrintId;
			}
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList =(Object[]) selectedCandidatesQuery.uniqueResult();
			return selectedCandidatesList;
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
	 * @see com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction#getRemainingDaysForEmployeeAndLeaveType(com.kp.cms.forms.employee.EmployeeApplyLeaveForm)
	 */
	@Override
	public EmpLeave getRemainingDaysForEmployeeAndLeaveType( ModifyEmployeeLeaveForm modifyEmployeeLeaveForm, int year) throws Exception {
		Session session = null;
		EmpLeave bo = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +modifyEmployeeLeaveForm.getLeaveTypeId()+
					" and e.employee.id=" +modifyEmployeeLeaveForm.getEmployeeId()+
					" and e.year="+year;
			Query selectedCandidatesQuery=session.createQuery(query);
			bo =(EmpLeave) selectedCandidatesQuery.uniqueResult();
			if(bo==null || bo.getLeavesRemaining()==null)
				return new EmpLeave();
			else
				return bo;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction#saveApplyLeave(com.kp.cms.bo.admin.EmpApplyLeave, com.kp.cms.forms.employee.EmployeeApplyLeaveForm, java.util.List, long)
	 */
	public boolean saveApplyLeave(EmpApplyLeave bo, ModifyEmployeeLeaveForm modifyEmployeeLeaveForm, List<Integer> commonLeaves,double daysDifference, int year,String mode,double daysBetween) {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(bo);
			if(commonLeaves.contains(bo.getEmpLeaveType().getId())){
				String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +modifyEmployeeLeaveForm.getLeaveTypeId()+
				" and e.employee.id=" +modifyEmployeeLeaveForm.getEmployeeId()+
				" and e.year="+year;
				Query selectedCandidatesQuery=session.createQuery(query);
				EmpLeave empLeave =(EmpLeave) selectedCandidatesQuery.uniqueResult();
				double leavesRemaining=0;
				double leavesSanctioned=0;
				if(empLeave==null){
					empLeave=new EmpLeave();
					empLeave.setEmpLeaveType(bo.getEmpLeaveType());
					empLeave.setEmployee(bo.getEmployee());
					empLeave.setIsActive(true);
					empLeave.setCreatedBy(modifyEmployeeLeaveForm.getUserId());
					empLeave.setCreatedDate(new Date());
					empLeave.setLeavesAllocated(Double.valueOf(0));
					empLeave.setLeavesSanctioned(Double.valueOf(0));
					empLeave.setYear(year);
					leavesSanctioned=daysBetween;
					int empTypeId =getEmployeeTypeIdB(bo.getEmployee().getId());
						ILeaveInitializationTransaction transaction2=LeaveInitializationTransactionImpl.getInstance();
						Map<Integer,String> monthMap=transaction2.getMonthByEmployeeType();
						if(empTypeId!=0 && monthMap.containsKey(empTypeId)){
							empLeave.setMonth(monthMap.get(empTypeId));
						}
					
				}else{
					empLeave.setModifiedBy(modifyEmployeeLeaveForm.getUserId());
					empLeave.setLastModifiedDate(new Date());
					leavesRemaining=empLeave.getLeavesSanctioned();
					leavesSanctioned = empLeave.getLeavesSanctioned();
					if(modifyEmployeeLeaveForm.getOldLeaveTypeId()!=null && modifyEmployeeLeaveForm.getLeaveTypeId()!=null && !modifyEmployeeLeaveForm.getOldLeaveTypeId().equalsIgnoreCase(modifyEmployeeLeaveForm.getLeaveTypeId())){
						leavesRemaining=leavesRemaining-daysBetween;
						leavesSanctioned=leavesSanctioned+daysBetween;
					}else{
						if(mode.equalsIgnoreCase("Add")){
							leavesRemaining = leavesRemaining-daysDifference;
							leavesSanctioned=leavesSanctioned+daysDifference;
						}else if(mode.equalsIgnoreCase("Subtract")){
							leavesRemaining = leavesRemaining+daysDifference;
							leavesSanctioned=leavesSanctioned-daysDifference;
						}
						empLeave.setLeavesSanctioned(leavesSanctioned);
					}
					
				}
				EmpLeaveType type=(EmpLeaveType)session.get(EmpLeaveType.class,empLeave.getEmpLeaveType().getId());
				if(type.getIsLeave()){
					empLeave.setLeavesRemaining(leavesRemaining);
				}else{
					empLeave.setLeavesRemaining(0.0);
					empLeave.setLeavesSanctioned(leavesSanctioned);
				}
				session.saveOrUpdate(empLeave);
			}else{
				String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +modifyEmployeeLeaveForm.getLeaveTypeId()+
				" and e.employee.id=" +modifyEmployeeLeaveForm.getEmployeeId()+
				" and e.year="+year;
				Query selectedCandidatesQuery=session.createQuery(query);
				EmpLeave empLeave =(EmpLeave) selectedCandidatesQuery.uniqueResult();
				if(modifyEmployeeLeaveForm.getOldLeaveTypeId()!=null && modifyEmployeeLeaveForm.getLeaveTypeId()!=null && !modifyEmployeeLeaveForm.getOldLeaveTypeId().equalsIgnoreCase(modifyEmployeeLeaveForm.getLeaveTypeId())){
					double remaningLeaves=empLeave.getLeavesRemaining()-daysBetween;
					double sanctionedLeaves=empLeave.getLeavesSanctioned()+daysBetween;
					empLeave.setLeavesRemaining(remaningLeaves);
					empLeave.setLeavesSanctioned(sanctionedLeaves);
				}else{
					if(mode.equalsIgnoreCase("Add")){
						double remaningLeaves=empLeave.getLeavesRemaining()-daysDifference;
						double sanctionedLeaves=empLeave.getLeavesSanctioned()+daysDifference;
						empLeave.setLeavesRemaining(remaningLeaves);
						empLeave.setLeavesSanctioned(sanctionedLeaves);
					}else if(mode.equalsIgnoreCase("Subtract")){
						double remaningLeaves=empLeave.getLeavesRemaining()+daysDifference;
						double sanctionedLeaves=empLeave.getLeavesSanctioned()-daysDifference;
						empLeave.setLeavesRemaining(remaningLeaves);
						empLeave.setLeavesSanctioned(sanctionedLeaves);
					}else{
						double remaningLeaves=empLeave.getLeavesRemaining();
						double sanctionedLeaves=empLeave.getLeavesSanctioned();
						empLeave.setLeavesRemaining(remaningLeaves);
						empLeave.setLeavesSanctioned(sanctionedLeaves);
					}
				}
				empLeave.setModifiedBy(modifyEmployeeLeaveForm.getUserId());
				empLeave.setLastModifiedDate(new Date());
				session.update(empLeave);
			}
			if(modifyEmployeeLeaveForm.getOldLeaveTypeId()!=null && modifyEmployeeLeaveForm.getLeaveTypeId()!=null && !modifyEmployeeLeaveForm.getOldLeaveTypeId().equalsIgnoreCase(modifyEmployeeLeaveForm.getLeaveTypeId())){
				String query1="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +modifyEmployeeLeaveForm.getOldLeaveTypeId()+
				" and e.employee.id=" +modifyEmployeeLeaveForm.getEmployeeId()+
				" and e.year="+year;
				Query selectedCandidatesQuery1=session.createQuery(query1);
				EmpLeave empLeave =(EmpLeave) selectedCandidatesQuery1.uniqueResult();
				empLeave.setModifiedBy(modifyEmployeeLeaveForm.getUserId());
				empLeave.setLastModifiedDate(new Date());
				double leavesRemaining=0;
				double leavesSanctioned=0;
				leavesRemaining=empLeave.getLeavesRemaining();
				leavesSanctioned = empLeave.getLeavesSanctioned();
				EmpLeaveType type=(EmpLeaveType)session.get(EmpLeaveType.class,empLeave.getEmpLeaveType().getId());
				if(leavesSanctioned!=0){
					leavesSanctioned=leavesSanctioned-modifyEmployeeLeaveForm.getNoOfDays();
				}
					leavesRemaining=leavesRemaining+modifyEmployeeLeaveForm.getNoOfDays();
				
				if(type.getIsLeave()){
					empLeave.setLeavesRemaining(leavesRemaining);
					empLeave.setLeavesSanctioned(leavesSanctioned);
				}else{
					empLeave.setLeavesRemaining(0.0);
					empLeave.setLeavesSanctioned(leavesSanctioned);
				}
				/*empLeave.setLeavesSanctioned(leavesSanctioned);
				empLeave.setLeavesRemaining(leavesRemaining);*/
				session.update(empLeave);
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IModifyIEmployeeLeaveTransaction#getLeavesTaken(java.lang.String)
	 */
	@Override
	public double getLeavesTaken(String query) throws Exception {
		Session session = null;
		Double bo = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			bo =(Double) selectedCandidatesQuery.uniqueResult();
			if(bo==null)
				return 0;
			else
				return bo;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IModifyIEmployeeLeaveTransaction#getEmployeeLeaveDetails(java.lang.String, java.lang.String)
	 */
	@Override
	public List<EmpApplyLeave> getEmployeeLeaveDetails(String empCode, String fingerPrintId, ModifyEmployeeLeaveForm modifyEmployeeLeaveForm)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpApplyLeave emp where emp.year = '"+modifyEmployeeLeaveForm.getAcademicYear()+"' and emp.isActive = 1 and emp.isCanceled = 0";
			if(empCode!=null && !empCode.isEmpty()){
				String employeeQuery = "select e.id from Employee e where e.code ='"+empCode+"'";
				Query empQuery = session.createQuery(employeeQuery);
				int empId = (Integer)empQuery.uniqueResult();
				query=query+" and emp.employee.id=" +empId;
						
			}
			if(fingerPrintId!=null && !fingerPrintId.isEmpty()){
				String employeeQuery = "select e.id from Employee e where e.fingerPrintId ='"+fingerPrintId+"'";
				Query empQuery = session.createQuery(employeeQuery);
				int empId = (Integer)empQuery.uniqueResult();
				query=query+" and emp.employee.id="+empId;
			}
			Query leaveQuery=session.createQuery(query);
			List<EmpApplyLeave> employeeLeave = leaveQuery.list();
			return  employeeLeave;
		} catch (Exception e) {
			log.error("Error while retrieving employee leave Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IModifyIEmployeeLeaveTransaction#getLeaveDetails(int)
	 */
	@Override
	public EmpApplyLeave getLeaveDetails(int id) throws Exception {


		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpApplyLeave emp where emp.id ="+id;
			Query leaveQuery=session.createQuery(query);
			EmpApplyLeave employeeLeave = (EmpApplyLeave)leaveQuery.uniqueResult();
			return  employeeLeave;
		} catch (Exception e) {
			log.error("Error while retrieving employee leave Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	
	}
	
	/**
	 * @param bo
	 * @param modifyEmployeeLeaveForm
	 * @param commonLeaves
	 * @param daysDifference
	 * @param year
	 * @param mode
	 * @return
	 */
	public boolean cancelLeave(EmpApplyLeave bo, ModifyEmployeeLeaveForm modifyEmployeeLeaveForm, List<Integer> commonLeaves,double daysDifference, int year) {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(bo);
			EmpOnlineLeave onlineLeave = (EmpOnlineLeave) session.createQuery("from EmpOnlineLeave e where e.isActive=1 and e.fromDate='"+bo.getFromDate()+"' and e.toDate='"+bo.getToDate()+"'").uniqueResult();
			if(onlineLeave != null){
				onlineLeave.setIsActive(false);
				session.update(onlineLeave);
			}
				String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +modifyEmployeeLeaveForm.getLeaveTypeId()+
				" and e.employee.id=" +modifyEmployeeLeaveForm.getEmployeeId()+
				" and e.year="+year;
				Query selectedCandidatesQuery=session.createQuery(query);
				EmpLeave empLeave =(EmpLeave) selectedCandidatesQuery.uniqueResult();
					if(empLeave != null){
						if(empLeave.getLeavesRemaining() != null){
							double remaningLeaves=empLeave.getLeavesRemaining();
							remaningLeaves = remaningLeaves + daysDifference;
							empLeave.setLeavesRemaining(remaningLeaves);
						}
						if(empLeave.getLeavesSanctioned() != null){
							double sanctionedLeaves=empLeave.getLeavesSanctioned();
							sanctionedLeaves = sanctionedLeaves - daysDifference;
							empLeave.setLeavesSanctioned(sanctionedLeaves);
						}
						empLeave.setModifiedBy(modifyEmployeeLeaveForm.getUserId());
						empLeave.setLastModifiedDate(new Date());
						session.update(empLeave);
					}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	@Override
	public int getEmployeeTypeIdB(int empId) throws Exception {
		Session session = null;
		int empTypeId=0;
		try{
			session = HibernateUtil.getSession();
			String Squery = "select e.emptype.id from Employee e where e.id="+empId;
			Query query = session.createQuery(Squery);
			empTypeId= (Integer)query.uniqueResult();
			return empTypeId;
		}catch(Exception e){
			if(session!=null)
				session.close();
		}
		
		return empTypeId;
	}
	
}
