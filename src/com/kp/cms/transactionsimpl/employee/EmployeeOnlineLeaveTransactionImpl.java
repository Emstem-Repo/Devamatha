package com.kp.cms.transactionsimpl.employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpOnlineLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeApprover;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.forms.employee.EmployeeOnlineLeaveForm;
import com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EmployeeOnlineLeaveTransactionImpl implements
		IEmployeeOnlineLeaveTransaction {
	/**
	 * Singleton object of EmployeeApplyLeaveTransactionImpl
	 */
	private static volatile EmployeeOnlineLeaveTransactionImpl impl = null;
	private static final Log log = LogFactory.getLog(EmployeeOnlineLeaveTransactionImpl.class);
	private EmployeeOnlineLeaveTransactionImpl() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveTransactionImpl.
	 * @return
	 */
	public static EmployeeOnlineLeaveTransactionImpl getInstance() {
		if (impl == null) {
			impl = new EmployeeOnlineLeaveTransactionImpl();
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction#getEmployeeDetails(java.lang.String, java.lang.String)
	 */
	@Override
	public Employee getEmployeeDetails(String userId) throws Exception {
		Session session = null;
		Employee selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="select u.employee from Users u where u.isActive=1";
			if(userId!=null && !userId.isEmpty()){
				query=query+" and u.id=" +userId;
						
			}
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList =(Employee) selectedCandidatesQuery.uniqueResult();
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
	public double getRemainingDaysForEmployeeAndLeaveType(EmployeeOnlineLeaveForm employeeOnlineLeaveForm,int year) throws Exception {
		Session session = null;
		EmpLeave bo = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +employeeOnlineLeaveForm.getLeaveTypeId()+
					" and e.employee.id=" +employeeOnlineLeaveForm.getEmployeeId()+
					" and e.year="+year;
			Query selectedCandidatesQuery=session.createQuery(query);
			bo =(EmpLeave) selectedCandidatesQuery.uniqueResult();
			if(bo==null || bo.getLeavesRemaining()==null)
				return 0;
			else
				return bo.getLeavesRemaining();
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
	public boolean saveApplyLeave(EmpOnlineLeave bo) {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(bo);
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
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#getEmpApplyLeaves(int, int)
	 */
	@Override
	public List<EmpOnlineLeave> getEmpApplyLeaves(int userId,int year) throws Exception {
		Session session=null;
		List<EmpOnlineLeave> applyLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr="from EmpOnlineLeave leave where leave.employee.id in (select user.employee.id from Users user where user.id= "+userId+") and leave.year= "+year;
			Query query=session.createQuery(queryStr);
			applyLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpApplyLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return applyLeave;
	}
	@Override
	public List<EmpLeave> getEmpLeaves(int userId,int year) throws Exception {
		Session session=null;
		List<EmpLeave> empLeave=null;
		try{
			session=HibernateUtil.getSession();
		    String queryStr="from EmpLeave emp where emp.employee.id in (select user.employee.id from Users user where user.id= "+userId+") and emp.year= "+year+" and emp.isActive=1";
		    Query query=session.createQuery(queryStr);
		    empLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return empLeave;
	}
	@Override
	public String getEmpMonth(int userId) throws Exception {
		Session session=null;
		String empMonth=null;
		try{
			session=HibernateUtil.getSession();
		    String queryStr="select DISTINCT leave.month from EmpLeave leave where leave.employee.id in"+
                            "(select user.employee.id from Users user where user.id= "+userId+") and leave.month !=null ";
		    Query query=session.createQuery(queryStr);
		    empMonth=query.uniqueResult().toString();
		}catch(Exception e){
			log.error("Error while getting Employee Month.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return empMonth;
	}
	@Override
	public Date getStartDate(int userId) throws Exception {
		Session session=null;
		String date="";
		Date dates=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr="select max(leave.createdDate) from EmpLeave leave where leave.employee.id in (select user.employee.id from Users user where user.id="+userId+")";
			Query query=session.createQuery(queryStr);
			date=query.uniqueResult().toString();
			 dates=CommonUtil.ConvertDateStringToSQLDate(date);
		}catch(Exception e){
			log.error("Error while getting Employee start date.." +e);
			throw new ApplicationException();
		}
		return dates;
	}
	@Override
	public List<EmpApplyLeave> getApplyLeavesWithFingerPrintId(
			int fingerPrintId, int year) throws Exception {
		Session session=null;
		List<EmpApplyLeave> applyLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr=" from EmpApplyLeave leave where leave.employee.fingerPrintId="+fingerPrintId+" and leave.year= "+year+" and leave.isActive=1 and leave.isCanceled=0";
			Query query=session.createQuery(queryStr);
			applyLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpApplyLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return applyLeave;
	}
	@Override
	public List<EmpLeave> getEmpLeaves(String id,
			int year,String mode) throws Exception {
		Session session=null;
		List<EmpLeave> empLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr="";
			Query query=null;
			if(mode.equalsIgnoreCase("fingerPrint")){
		     queryStr="from EmpLeave leave where leave.employee.fingerPrintId= "+Integer.parseInt(id)+" and leave.year= "+year+" and leave.isActive=1";
		      query=session.createQuery(queryStr);
			}
			else if(mode.equalsIgnoreCase("empCode")){
				 queryStr="from EmpLeave leave where leave.employee.code= :code and leave.year= "+year+" and leave.isActive=1";
			     query=session.createQuery(queryStr);
			    query.setString("code", id);
			}
		    empLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return empLeave;
		
	}
	@Override
	public String getEmpMonthWithFingerPrintId(int fingerPrintId)
			throws Exception {
		Session session=null;
		String empMonth=null;
		try{
			session=HibernateUtil.getSession();
		    String queryStr="select DISTINCT leave.month from EmpLeave leave where leave.employee.fingerPrintId="+fingerPrintId+" and leave.month !=null";
		    Query query=session.createQuery(queryStr);
		    empMonth=query.uniqueResult().toString();
		}catch(Exception e){
			log.error("Error while getting Employee Month.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return empMonth;
		
	}
	@Override
	public Date getStartDateWithFingerPrintId(int fingerPrintId)
			throws Exception {
		Session session=null;
		String date="";
		Date dates=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr="select max(leave.createdDate) from EmpLeave leave where leave.employee.fingerPrintId="+fingerPrintId;
			Query query=session.createQuery(queryStr);
			date=query.uniqueResult().toString();
			 dates=CommonUtil.ConvertDateStringToSQLDate(date);
		}catch(Exception e){
			log.error("Error while getting Employee start date.." +e);
			throw new ApplicationException();
		}
		return dates;
	}
	@Override
	public List<EmpApplyLeave> getApplyLeavesWithEmpCode(String empCode, int year)
			throws Exception {
		Session session=null;
		List<EmpApplyLeave> applyLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr=" from EmpApplyLeave leave where leave.employee.code= :code and leave.year= "+year+" and leave.isActive=1 and leave.isCanceled=0";
			Query query=session.createQuery(queryStr);
			query.setString("code", empCode);
			applyLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpApplyLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return applyLeave;
	}
	@Override
	public String getEmpMonthWithEmpCode(String empCode) throws Exception {
		Session session=null;
		String empMonth="";
		try{
			session=HibernateUtil.getSession();
		    String queryStr="select DISTINCT leave.month from EmpLeave leave where leave.employee.code= :code and leave.month !=null";
		    Query query=session.createQuery(queryStr);
		    query.setString("code", empCode);
		    empMonth=query.uniqueResult().toString();
		}catch(Exception e){
			log.error("Error while getting Employee Month.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return empMonth;
	}
	@Override
	public Date getStartDateWithEmpCode(String empCode) throws Exception {
		Session session=null;
		String date="";
		Date dates=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr="select max(leave.createdDate) from EmpLeave leave where leave.employee.code= :code";
			Query query=session.createQuery(queryStr);
			query.setString("code", empCode);
			date=query.uniqueResult().toString();
			 dates=CommonUtil.ConvertDateStringToSQLDate(date);
		}catch(Exception e){
			log.error("Error while getting Employee start date.." +e);
			throw new ApplicationException();
		}
		return dates;
	}
	@Override
	public List<EmpLeave> getEmpLeavesWithEmpCode(String empCode, int year)
			throws Exception {
		Session session=null;
		List<EmpLeave> empLeave=null;
		try{
			session=HibernateUtil.getSession();
		    String queryStr="from EmpLeave leave where leave.employee.code= :code and leave.year= "+year+" and leave.isActive=1";
		    Query query=session.createQuery(queryStr);
		    query.setString("code", empCode);
		    empLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return empLeave;
	}
	@Override
	public List<String> getFingerPrintIds(int userId) throws Exception {
		Session session=null;
		List<String> fingerPrintList=null;
		try{
			session=HibernateUtil.getSession();
			//String quer="select emp.fingerPrintId from Employee emp where emp.department.id in (select td.departmentId.id from TeacherDepartment td where td.teacherId="+userId+") and emp.fingerPrintId!=null";
			String quer="select emp.fingerPrintId from Employee emp where emp.department.id in (select users.employee.department.id from Users users where users.id="+userId+") and emp.fingerPrintId!=null";
			Query query=session.createQuery(quer);
			fingerPrintList=query.list();
		}catch(Exception e){
			log.error("Error while getting fingerPrintids.." +e);
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return fingerPrintList;
	}
	@Override
	public List<String> getEmpCodes(int userId) throws Exception {
		Session session=null;
		List<String> empCodeList=null;
		try{
			session=HibernateUtil.getSession();
			String quer="select emp.code from Employee emp where emp.department.id in (select td.departmentId.id from TeacherDepartment td where td.teacherId="+userId+")";
			Query query=session.createQuery(quer);
			empCodeList=query.list();
		}catch(Exception e){
			log.error("Error while getting empCode.." +e);
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return empCodeList;
		
	}
	@Override
	public List<String> getEmpNames(int userId) throws Exception {
		Session session=null;
        List<String> empNames=null;
        try{
        	session=HibernateUtil.getSession();
			String quer="select emp.firstName from Employee emp where emp.department.id in (select td.departmentId.id from TeacherDepartment td where td.teacherId="+userId+")";
			Query query=session.createQuery(quer);
			empNames=query.list();	
        }catch(Exception e){
        	log.error("Error while getting EmpNames.." +e);
        	throw new ApplicationException();
        }
		return empNames;
	}
	@Override
	public List<EmpApplyLeave> getApplyLeavesWithName(String empName, int year)
			throws Exception {
		Session session=null;
		List<EmpApplyLeave> applyLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr=" from EmpApplyLeave leave where leave.employee.firstName= :name and leave.year= "+year+" and leave.isActive=1 and leave.isCanceled=0";
			Query query=session.createQuery(queryStr);
			query.setString("name", empName);
			applyLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpApplyLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return applyLeave;
	}
	@Override
	public List<EmpLeave> getEmpLeavesWithName(String empName, int year)
			throws Exception {
		Session session=null;
		List<EmpLeave> empLeave=null;
		try{
			session=HibernateUtil.getSession();
		    String queryStr="from EmpLeave leave where leave.employee.firstName= :name and leave.year= "+year+" and leave.isActive=1";
		    Query query=session.createQuery(queryStr);
		    query.setString("name", empName);
		    empLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return empLeave;
	}
	@Override
	public String getEmpMonthWithEmpName(String empName) throws Exception {
		Session session=null;
		String empMonth="";
		try{
			session=HibernateUtil.getSession();
		    String queryStr="select DISTINCT leave.month from EmpLeave leave where leave.employee.firstName= :name and leave.month !=null";
		    Query query=session.createQuery(queryStr);
		    query.setString("name", empName);
		    empMonth=query.uniqueResult().toString();
		}catch(Exception e){
			log.error("Error while getting Employee Month.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return empMonth;
	}
	@Override
	public Date getStartDateWithEmpName(String empName) throws Exception {
		Session session=null;
		String date="";
		Date dates=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr="select max(leave.createdDate) from EmpLeave leave where leave.employee.firstName= :name";
			Query query=session.createQuery(queryStr);
			query.setString("name", empName);
			date=query.uniqueResult().toString();
			 dates=CommonUtil.ConvertDateStringToSQLDate(date);
		}catch(Exception e){
			log.error("Error while getting Employee start date.." +e);
			throw new ApplicationException();
		}
		return dates;
	}
	@Override
	public Map<String, String> getLeaveTypeMap() throws Exception {
		Session session=null;
		Map<String,String> leaveTypeMap=new HashMap<String,String>();
		List<EmpLeaveType> list=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from EmpLeaveType type where type.isActive=true";
			Query query=session.createQuery(quer);
			list=query.list();
		}catch(Exception e){
			throw new ApplicationException();
		}
		if(list!=null){
			Iterator<EmpLeaveType> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpLeaveType leaveType=iterator.next();
				if(leaveType.getId()>0){
					leaveTypeMap.put(leaveType.getName().trim(), String.valueOf(leaveType.getId()).trim());
				}
			}
		}
		return leaveTypeMap;
	}
	@Override
	public boolean saveEmpApplyLeave(List<EmpApplyLeave> list,EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception {
			int count=0;
			int count1=0;
			Session session=null;
			Transaction transaction=null;
			List<String> fingerPrintIds=new ArrayList<String>();
			List<String> overFlowIds=new ArrayList<String>();
			boolean flag=false;
			
			try {
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				if(list!=null && !list.isEmpty()){
					 Iterator<EmpApplyLeave> iterator=list.iterator(); 
					 while(iterator.hasNext()){
						  boolean isOverFlow=false;
						  EmpApplyLeave applyLeave=iterator.next();
//						  System.out.println(applyLeave.getEmployee().getFingerPrintId());
//						  if(applyLeave.getEmployee().getFingerPrintId().equalsIgnoreCase("316")){//remove this
						  String str="from Employee emp where emp.isActive=1 and emp.fingerPrintId=:fingerId";
						  Query query=session.createQuery(str);
						  query.setParameter("fingerId", applyLeave.getEmployee().getFingerPrintId());
						  Employee employee=(Employee)query.uniqueResult();
						  if(employee!=null){
								   applyLeave.setEmployee(employee);
								   boolean isDuplicate=isDuplicate(applyLeave);
								   if(!isDuplicate){
						                  String str1="from EmpLeave emp where emp.employee.id="+employee.getId()+" and emp.empLeaveType.id="+applyLeave.getEmpLeaveType().getId()+" and emp.year="+applyLeave.getYear() ;
						                  Query query1=session.createQuery(str1);
						                  EmpLeave leave=(EmpLeave)query1.uniqueResult();
						                  if(leave!=null){
							                    if(leave.getLeavesAllocated() < applyLeave.getNoOfDays() && leave.getLeavesRemaining()!=0){
								                       boolean isWrong=true;
								                       employeeApplyLeaveForm.setWrong(isWrong);
								                       throw new ApplicationException();
							                    }else if(leave.getLeavesAllocated()==0 && leave.getLeavesRemaining()==0){
								                       leave.setLeavesSanctioned(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
							                    }
							                    else{
								                       if(applyLeave.getIsHalfDay() && applyLeave.getNoOfDays()==0.5){
									                        double leaves=leave.getLeavesAllocated()-(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
									                        if(leaves<0){
									            	           isOverFlow=true;
									            	           applyLeave=null;
									                        }
									                        if(!isOverFlow){
									                        	leave.setLeavesSanctioned(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
									                        	leave.setLeavesRemaining(leaves);
									                        }
								                       }else{
								                    	   double leaves=leave.getLeavesAllocated()-(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
								                            if(leaves<0){
									            	           isOverFlow=true;
									            	           applyLeave=null;
								                            }
								                            if(!isOverFlow){
								                            	leave.setLeavesRemaining(leaves);
								                            	leave.setLeavesSanctioned(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
								                            }
								                       }
							                    }
							                    System.out
														.println(employee.getFingerPrintId());
							                    leave.setLastModifiedDate(new Date());
							                    leave.setModifiedBy(employeeApplyLeaveForm.getUserId());
							                    if(applyLeave!=null && !isOverFlow){
									                session.saveOrUpdate(applyLeave);
									                session.update(leave);
//									                session.flush();
									                flag=true;
							                    }else{
							        	            overFlowIds.add(employee.getFingerPrintId());
							                    } 
					                      }else{
						                        EmpLeave leaves=new EmpLeave();
						                        String month=getInitializeMonth(employee);
						                        leaves.setEmployee(employee);
						                        leaves.setEmpLeaveType(applyLeave.getEmpLeaveType());
                                                leaves.setMonth(month);
                                                leaves.setLeavesAllocated(0.0);
                                                leaves.setLeavesRemaining(0.0);
                                                leaves.setLeavesSanctioned(applyLeave.getNoOfDays());
                                                leaves.setIsActive(true);
                                                leaves.setCreatedBy(employeeApplyLeaveForm.getUserId());
                                                leaves.setCreatedDate(new Date());
                                                leaves.setModifiedBy(employeeApplyLeaveForm.getUserId());
                                                leaves.setLastModifiedDate(new Date());
                                                if(applyLeave.getYear()!=null){
                        	                          leaves.setYear(applyLeave.getYear());
                                                }else{
                        	                          String year=CommonUtil.getYear(applyLeave.getFromDate());
                                                      leaves.setYear(Integer.parseInt(year));
                                                }
                                                session.saveOrUpdate(applyLeave);
                                                session.save(leaves);
//                                                session.flush();
                                                flag=true;
					                      }
						                  if(++count%20==0){
							                  session.flush();
							                  session.clear();
						                  }
							        }else{//duplicate
								          fingerPrintIds.add(employee.getFingerPrintId());
							        }
						  }
//					 }//remove this
					 }
					 transaction.commit();
					 employeeApplyLeaveForm.setFingerPrintIds(fingerPrintIds);
					 employeeApplyLeaveForm.setOverFlowIds(overFlowIds);
				}
			} catch (Exception e) {
				if(transaction!=null)
				transaction.rollback();
				flag=false;
				log.debug("Error during saving data...", e);
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
					session.close();
				} 
			}
			
			return flag;
		}
	
	/**
	 * @param list
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveEmployeeApplyLeave(List<EmpApplyLeave> list,EmployeeApplyLeaveForm employeeApplyLeaveForm) throws Exception {
		int count=0;
		Session session=null;
		Transaction transaction=null;
		List<String> fingerPrintIds=new ArrayList<String>();
		List<String> overFlowIds=new ArrayList<String>();
		boolean flag=false;
		try {
			session=InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			if(list!=null && !list.isEmpty()){
				 Iterator<EmpApplyLeave> iterator=list.iterator(); 
				 while(iterator.hasNext()){
				    boolean isOverFlow=false;
				    EmpApplyLeave applyLeave=iterator.next();
				    System.out.println("Employee ID:"+applyLeave.getEmployee().getId());
					Query query=session.createQuery("from EmpApplyLeave leave where leave.employee.id="+applyLeave.getEmployee().getId()+" and leave.fromDate=:frmDate and leave.toDate=:toDate");
					query.setDate("frmDate", applyLeave.getFromDate());
					query.setDate("toDate", applyLeave.getToDate());
					List<EmpApplyLeave> leavesList=query.list();
					if(leavesList==null || leavesList.isEmpty()){

		                  String str1="from EmpLeave emp where emp.employee.id="+applyLeave.getEmployee().getId()+" and emp.empLeaveType.id="+applyLeave.getEmpLeaveType().getId()+" and emp.year="+applyLeave.getYear() ;
		                  Query query1=session.createQuery(str1);
		                  EmpLeave leave=(EmpLeave)query1.uniqueResult();
		                  if(leave!=null){
			                    if(leave.getLeavesAllocated() < applyLeave.getNoOfDays() && leave.getLeavesRemaining()!=0){
				                       boolean isWrong=true;
				                       employeeApplyLeaveForm.setWrong(isWrong);
				                       throw new ApplicationException();
			                    }else if(leave.getLeavesAllocated()==0 && leave.getLeavesRemaining()==0){
				                       leave.setLeavesSanctioned(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
			                    }
			                    else{
				                       if(applyLeave.getIsHalfDay() && applyLeave.getNoOfDays()==0.5){
					                        double leaves=leave.getLeavesAllocated()-(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
					                        if(leaves<0){
					            	           isOverFlow=true;
					            	           applyLeave=null;
					                        }
					                        if(!isOverFlow){
					                        	leave.setLeavesSanctioned(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
					                        	leave.setLeavesRemaining(leaves);
					                        }
				                       }else{
				                    	   double leaves=leave.getLeavesAllocated()-(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
				                            if(leaves<0){
					            	           isOverFlow=true;
					            	           applyLeave=null;
				                            }
				                            if(!isOverFlow){
				                            	leave.setLeavesRemaining(leaves);
				                            	leave.setLeavesSanctioned(applyLeave.getNoOfDays()+leave.getLeavesSanctioned());
				                            }
				                       }
			                    }
			                    leave.setLastModifiedDate(new Date());
			                    leave.setModifiedBy(employeeApplyLeaveForm.getUserId());
			                    if(applyLeave!=null && !isOverFlow){
					                session.saveOrUpdate(applyLeave);
					                session.update(leave);
					                flag=true;
			                    }else{
			                    	if(applyLeave!=null && applyLeave.getEmployee().getFingerPrintId()!=null)
			        	            overFlowIds.add(applyLeave.getEmployee().getFingerPrintId());
			                    } 
	                      }else{
		                        EmpLeave leaves=new EmpLeave();
		                        String month=getInitializeMonth(applyLeave.getEmployee());
		                        leaves.setEmployee(applyLeave.getEmployee());
		                        leaves.setEmpLeaveType(applyLeave.getEmpLeaveType());
                              leaves.setMonth(month);
                              leaves.setLeavesAllocated(0.0);
                              leaves.setLeavesRemaining(0.0);
                              leaves.setLeavesSanctioned(applyLeave.getNoOfDays());
                              leaves.setIsActive(true);
                              leaves.setCreatedBy(employeeApplyLeaveForm.getUserId());
                              leaves.setCreatedDate(new Date());
                              leaves.setModifiedBy(employeeApplyLeaveForm.getUserId());
                              leaves.setLastModifiedDate(new Date());
                              if(applyLeave.getYear()!=null){
      	                          leaves.setYear(applyLeave.getYear());
                              }else{
      	                          String year=CommonUtil.getYear(applyLeave.getFromDate());
                                    leaves.setYear(Integer.parseInt(year));
                              }
                              session.saveOrUpdate(applyLeave);
                              session.save(leaves);
//                              session.flush();
                              flag=true;
	                      }
		                  if(++count%20==0){
			                  session.flush();
			                  session.clear();
		                  }
			        
						
					}else{
						fingerPrintIds.add(applyLeave.getEmployee().getFingerPrintId());
					}
				 }
				    
				 transaction.commit();
				 employeeApplyLeaveForm.setFingerPrintIds(fingerPrintIds);
				 employeeApplyLeaveForm.setOverFlowIds(overFlowIds);
			}
		} catch (Exception e) {
			if(transaction!=null)
			transaction.rollback();
			flag=false;
			log.debug("Error during saving data...", e);
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			} 
		}
		
		return flag;
	}
	
	
	
	public boolean isDuplicate(EmpApplyLeave applyLeave){
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String str="from EmpApplyLeave leave where leave.employee.id="+applyLeave.getEmployee().getId()+" and leave.fromDate=:frmDate and leave.toDate=:toDate";
			Query query=session.createQuery(str);
			query.setDate("frmDate", applyLeave.getFromDate());
			query.setDate("toDate", applyLeave.getToDate());
			EmpApplyLeave leave=(EmpApplyLeave)query.uniqueResult();
			if(leave!=null){
				flag=true;
			}else
				flag=false;
		}catch(Exception e){
			log.debug("Error during checking data...", e);
		}
		return flag;
	}
	public String getInitializeMonth(Employee employee){
		Session session=null;
		String month="";
		try{
			session=HibernateUtil.getSession();
			String str="select emp.emptype.leaveInitializeMonth from Employee emp where emp.id="+employee.getId();
			Query query=session.createQuery(str);
		    month=query.uniqueResult().toString();
		}catch(Exception e){
			log.debug("Error during getting data...", e);
		}
		return month;
	}
	@Override
	public int getInitializeMonth(String id,String mode,String name) throws Exception {
		Session session=null;
		String empMonth=null;
		int month=0;
		try{
			session=HibernateUtil.getSession();
			String queryStr="";
			Query query=null;
			if(mode.equalsIgnoreCase("userId")){
				queryStr="select emp.emptype.leaveInitializeMonth from Employee emp inner join emp.userses userss where userss.id="+Integer.parseInt(id);
				query=session.createQuery(queryStr);
			}
			else if(mode.equalsIgnoreCase("fingerPrint")){
		     queryStr="select emp.emptype.leaveInitializeMonth from Employee emp where emp.fingerPrintId="+Integer.parseInt(id);
		     query=session.createQuery(queryStr);
			}else if(mode.equalsIgnoreCase("empCode")){
				 queryStr="select emp.emptype.leaveInitializeMonth from Employee emp where emp.code=:codes";
			     query=session.createQuery(queryStr);
			    query.setString("codes", id);
			}else if(!name.isEmpty()){
				queryStr="select emp.emptype.leaveInitializeMonth from Employee emp where emp.firstName=:name";
				query=session.createQuery(queryStr);
			    query.setString("name", name);
			}
		    empMonth=(String)query.uniqueResult();
		    if(empMonth!=null){
			    Date date = new SimpleDateFormat("MMMMMMMMM", Locale.ENGLISH).parse(empMonth);
			    Calendar cal = Calendar.getInstance();
			    cal.setTime(date);
			    month = cal.get(Calendar.MONTH)+1;
		    }
		}catch(Exception e){
			log.error("Error while getting Employee Month.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return month;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#getAllEmployees()
	 */
	@Override
	public Map<String, Integer> getAllEmployees() throws Exception {
		Session session=null;
		Map<String,Integer> empMap=new HashMap<String,Integer>();
		try{
			session=HibernateUtil.getSession();
			String quer="from Employee e where e.isActive=1 and e.active=1 and e.fingerPrintId is not null";
			Query query=session.createQuery(quer);
			List<Employee> list=query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Employee> itr=list.iterator();
				while (itr.hasNext()) {
					Employee bo = (Employee) itr.next();
					empMap.put(bo.getFingerPrintId(),bo.getId());
				}
			}
		}catch(Exception e){
			throw new ApplicationException();
		}
		
		return empMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction#checkLeaveType(java.lang.String)
	 */
	@Override
	public boolean checkLeaveType(String leaveTypeId) throws Exception {
		Session session = null;
		Boolean continuousdays;
		try{
			session=HibernateUtil.getSession();
			String quer="select e.continuousdays from EmpLeaveType e where e.id="+leaveTypeId;
			Query query=session.createQuery(quer);
			continuousdays=(Boolean)query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException();
		}
		return continuousdays;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#getEmpApplyLeaves(int, int)
	 */
	@Override
	public List<EmpOnlineLeave> getEmpApproveLeaves(int userId,int year) throws Exception {
		Session session=null;
		List<EmpOnlineLeave> applyLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr="from EmpOnlineLeave leave where leave.isApproved=0 and leave.status='Applied'" +
					"and leave.leaveApprover in (select user.employee.id from Users user where user.id= "+userId+")  and leave.isActive=1";
			Query query=session.createQuery(queryStr);
			applyLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpApplyLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return applyLeave;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#saveAndApproveEmployeeLeave(java.util.List, com.kp.cms.forms.employee.EmployeeOnlineLeaveForm, java.util.List, java.util.Map)
	 */
	@Override
	public boolean saveAndApproveEmployeeLeave(List<EmpApplyLeave> approveList,	EmployeeOnlineLeaveForm employeeOnlineLeaveForm,List<Integer> commonLeaves, Map<Integer, String> monthMap)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(approveList != null){
				Iterator<EmpApplyLeave> iterator = approveList.iterator();
				while (iterator.hasNext()) {
					EmpApplyLeave empApplyLeave = (EmpApplyLeave) iterator.next();
					session.save(empApplyLeave);
					
					if(commonLeaves.contains(empApplyLeave.getEmpLeaveType().getId())){
						String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +empApplyLeave.getEmpLeaveType().getId()+
						" and e.employee.id=" +empApplyLeave.getEmployee().getId()+
						" and e.year="+empApplyLeave.getYear();
						Query selectedCandidatesQuery=session.createQuery(query);
						EmpLeave empLeave =(EmpLeave) selectedCandidatesQuery.uniqueResult();
						double leavesRemaining=0;
						if(empLeave==null){
							empLeave=new EmpLeave();
							empLeave.setEmpLeaveType(empApplyLeave.getEmpLeaveType());
							empLeave.setEmployee(empApplyLeave.getEmployee());
							empLeave.setIsActive(true);
							empLeave.setCreatedBy(employeeOnlineLeaveForm.getUserId());
							empLeave.setCreatedDate(new Date());
							empLeave.setLeavesAllocated(Double.valueOf(0));
							empLeave.setLeavesSanctioned(Double.valueOf(0));
							empLeave.setLeavesRemaining(leavesRemaining);
							empLeave.setYear(empApplyLeave.getYear());
							if(empApplyLeave.getEmployee() != null && empApplyLeave.getEmployee().getEmptype() != null && monthMap.containsKey(empApplyLeave.getEmployee().getEmptype().getId())){
								empLeave.setMonth(monthMap.get(empApplyLeave.getEmployee().getEmptype().getId()));
							}
						}else{
							empLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
							empLeave.setLastModifiedDate(new Date());
							leavesRemaining=empLeave.getLeavesSanctioned();
						}
						leavesRemaining=leavesRemaining+empApplyLeave.getNoOfDays();
						empLeave.setLeavesSanctioned(leavesRemaining);
						session.saveOrUpdate(empLeave);
					}else{
						String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +empApplyLeave.getEmpLeaveType().getId()+
								" and e.employee.id=" +empApplyLeave.getEmployee().getId()+
								" and e.year="+empApplyLeave.getYear();
						Query selectedCandidatesQuery=session.createQuery(query);
						EmpLeave empLeave =(EmpLeave) selectedCandidatesQuery.uniqueResult();
						double remaningLeaves=empLeave.getLeavesRemaining()-empApplyLeave.getNoOfDays();
						double sanctionedLeaves=empLeave.getLeavesSanctioned()+empApplyLeave.getNoOfDays();
						empLeave.setLeavesRemaining(remaningLeaves);
						empLeave.setLeavesSanctioned(sanctionedLeaves);
						empLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
						empLeave.setLastModifiedDate(new Date());
						session.update(empLeave);
					}
				}
				List<Integer> approvedIds = employeeOnlineLeaveForm.getApprovedList();
				String hqlQuery = "from EmpOnlineLeave e where e.isActive=1 and e.id in (:ID)";
				Query query = session.createQuery(hqlQuery);
				query.setParameterList("ID", approvedIds);
				List<EmpOnlineLeave> eList = query.list();
				if(eList != null){
					Iterator<EmpOnlineLeave> iterator2 = eList.iterator();
					while (iterator2.hasNext()) {
						EmpOnlineLeave empOnlineLeave = (EmpOnlineLeave) iterator2.next();
						// code changed by sudhir
						/*empOnlineLeave.setApprovedBy(employeeOnlineLeaveForm.getEmployeeId());
						empOnlineLeave.setApprovedDate(new Date());*/
						empOnlineLeave.setLastModifiedDate(new Date());
						empOnlineLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
						empOnlineLeave.setStatus("Authorized");
						empOnlineLeave.setAuthorizedBy(employeeOnlineLeaveForm.getEmployeeId());
						empOnlineLeave.setIsAuthorized(true);
						/*empOnlineLeave.setIsApproved(true);*/
						session.update(empOnlineLeave);
					}
				}
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
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#getEmployeeMap()
	 */
	@Override
	public Map<Integer, String> getEmployeeMap() throws Exception {
		Session session=null;
		Map<Integer,String> empMap=new HashMap<Integer,String>();
		try{
			session=HibernateUtil.getSession();
			String quer="from Employee e where e.isActive=1 and e.active=1 and e.titleId is not null";
			Query query=session.createQuery(quer);
			List<Employee> list=query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Employee> itr=list.iterator();
				while (itr.hasNext()) {
					Employee bo = (Employee) itr.next();
					String name = "";
					if(bo.getFirstName() != null && bo.getTitleId() != null && bo.getTitleId().getTitle() != null){
						name = bo.getFirstName() +"("+bo.getTitleId().getTitle() +")";
					}else if(bo.getFirstName() != null){
						name = bo.getFirstName();
					}
					empMap.put(bo.getId(),name);
				}
			}
		}catch(Exception e){
			throw new ApplicationException();
		}
		
		return empMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#updateEmpOnlineLeave(java.util.List, com.kp.cms.forms.employee.EmployeeOnlineLeaveForm)
	 */
	@Override
	public void updateEmpOnlineLeave(List<Integer> forwardList,	EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(forwardList != null){
				String hqlQuery = "from EmpOnlineLeave e where e.isActive=1 and e.id in (:ID)";
				Query query = session.createQuery(hqlQuery);
				query.setParameterList("ID", forwardList);
				List<EmpOnlineLeave> eList = query.list();
				if(eList != null){
					Iterator<EmpOnlineLeave> iterator2 = eList.iterator();
					while (iterator2.hasNext()) {
						EmpOnlineLeave empOnlineLeave = (EmpOnlineLeave) iterator2.next();
						Employee emp = new Employee();
						emp.setId(Integer.parseInt(employeeOnlineLeaveForm.getEmployeeId()));
						empOnlineLeave.setLeaveApprover(emp);
						empOnlineLeave.setLastModifiedDate(new Date());
						empOnlineLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
						session.update(empOnlineLeave);
					}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#rejectEmpOnlineLeave(java.util.List, com.kp.cms.forms.employee.EmployeeOnlineLeaveForm)
	 */
	@Override
	public void rejectEmpOnlineLeave(List<Integer> forwardList,
			EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(forwardList != null){
				String hqlQuery = "from EmpOnlineLeave e where e.isActive=1 and e.id in (:ID)";
				Query query = session.createQuery(hqlQuery);
				query.setParameterList("ID", forwardList);
				List<EmpOnlineLeave> eList = query.list();
				if(eList != null){
					Iterator<EmpOnlineLeave> iterator2 = eList.iterator();
					while (iterator2.hasNext()) {
						EmpOnlineLeave empOnlineLeave = (EmpOnlineLeave) iterator2.next();
						empOnlineLeave.setRejectedReason(employeeOnlineLeaveForm.getRejectedReason());
						empOnlineLeave.setLastModifiedDate(new Date());
						empOnlineLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
						empOnlineLeave.setStatus("Returned");
						session.update(empOnlineLeave);
					}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#getApproverId(java.lang.String)
	 */
	@Override
	public EmployeeApprover getApproverId(String employeeId) throws Exception {
		Session session=null;
		EmployeeApprover approverId;
		try{
			session=HibernateUtil.getSession();
			String quer="from EmployeeApprover e where e.isActive=1 and e.employee.id="+employeeId;
			Query query=session.createQuery(quer);
			approverId=(EmployeeApprover)query.uniqueResult();
		}catch(Exception e){
			throw new ApplicationException();
		}
		
		return approverId;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction#getEmployeeDetails(java.lang.String, java.lang.String)
	 */
	@Override
	public Employee getEmployee(String userId) throws Exception {
		Session session = null;
		Employee selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			String query="from Employee e where e.isActive=1";
			if(userId!=null && !userId.isEmpty()){
				query=query+" and e.id=" +userId;
						
			}
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList =(Employee) selectedCandidatesQuery.uniqueResult();
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
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#approveEmployeeLeave(com.kp.cms.forms.employee.EmployeeOnlineLeaveForm)
	 */
	@Override
	public boolean approveEmployeeLeave( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception {
		Session session = null;
		Transaction tx= null;
		boolean isApproved = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			List<Integer> approvedIds = employeeOnlineLeaveForm.getApprovedList();
			String hqlQuery = "from EmpOnlineLeave e where e.isActive=1 and e.id in (:ID)";
			Query query = session.createQuery(hqlQuery);
			query.setParameterList("ID", approvedIds);
			List<EmpOnlineLeave> eList = query.list();
			if(eList != null){
				Iterator<EmpOnlineLeave> iterator2 = eList.iterator();
				while (iterator2.hasNext()) {
					EmpOnlineLeave empOnlineLeave = (EmpOnlineLeave) iterator2.next();
					empOnlineLeave.setApprovedBy(employeeOnlineLeaveForm.getEmployeeId());
					empOnlineLeave.setApprovedDate(new Date());
					empOnlineLeave.setLastModifiedDate(new Date());
					empOnlineLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
					empOnlineLeave.setStatus("Pending Authorization");
					empOnlineLeave.setIsApproved(true);
					empOnlineLeave.setIsAuthorized(false);
					session.update(empOnlineLeave);
				}
				isApproved = true;
			}
			tx.commit();
			session.flush();
		}catch (Exception e) {
			if ( tx != null){
				tx.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
		return isApproved;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#getEmpAuthorizationLeaves(int, int)
	 */
	@Override
	public List<EmpOnlineLeave> getEmpAuthorizationLeaves(int parseInt, int year)
			throws Exception {
		Session session=null;
		List<EmpOnlineLeave> applyLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr = "from EmpOnlineLeave leave where leave.isApproved=1 and leave.status='Pending Authorization' and leave.isAuthorized!=1 and leave.isActive=1";
			Query query=session.createQuery(queryStr);
			applyLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpApplyLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return applyLeave;
	}
	/**
	 * @param approvedBy
	 * @return
	 * @throws Exception 
	 */
	public String getapprovedByName(Integer approvedBy) throws Exception {
		Session session = null;
		String employeeName = "";
		try{
			session = HibernateUtil.getSession();
			String str= "from Employee emp where emp.id="+approvedBy+"and emp.isActive=1";
			Query query = session.createQuery(str);
			Employee emp= (Employee) query.uniqueResult();
			if(emp!=null && !emp.toString().isEmpty()){
				if(emp.getFirstName()!=null && !emp.getFirstName().isEmpty()){
					employeeName = emp.getFirstName();
				}
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

		return employeeName;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#requestDocEmpLeave(java.util.List, com.kp.cms.forms.employee.EmployeeOnlineLeaveForm)
	 */
	@Override
	public void requestDocEmpLeave(List<Integer> forwardList, EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(forwardList != null){
				String hqlQuery = "from EmpOnlineLeave e where e.isActive=1 and e.id in (:ID)";
				Query query = session.createQuery(hqlQuery);
				query.setParameterList("ID", forwardList);
				List<EmpOnlineLeave> eList = query.list();
				if(eList != null){
					Iterator<EmpOnlineLeave> iterator2 = eList.iterator();
					while (iterator2.hasNext()) {
						EmpOnlineLeave empOnlineLeave = (EmpOnlineLeave) iterator2.next();
						empOnlineLeave.setRejectedReason(employeeOnlineLeaveForm.getRejectedReason());
						empOnlineLeave.setLastModifiedDate(new Date());
						empOnlineLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
						empOnlineLeave.setStatus("Request Document");
						session.update(empOnlineLeave);
					}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
		}
		
	}
	@Override
	public List<EmpOnlineLeave> getViewReturnedReqDoc(int userId) throws Exception {
		Session session=null;
		List<EmpOnlineLeave> applyLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr = "from EmpOnlineLeave leave where leave.isApproved=1 and (leave.status='Returned' or leave.status='Request Document')  and leave.isAuthorized!=1 and leave.isActive=1";
			Query query=session.createQuery(queryStr);
			applyLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpApplyLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return applyLeave;
	}
	
	public List<EmpOnlineLeave> getViewReturnedReqDocForApproval(int userId) throws Exception {
		Session session=null;
		List<EmpOnlineLeave> applyLeave=null;
		try{
			session=HibernateUtil.getSession();
			String queryStr = "from EmpOnlineLeave leave where leave.isApproved=0 and (leave.status='Returned' or leave.status='Request Document')  and leave.isAuthorized!=1 and leave.isActive=1";
			Query query=session.createQuery(queryStr);
			applyLeave=query.list();
		}catch(Exception e){
			log.error("Error while getting EmpApplyLeaves.." +e);
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return applyLeave;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction#saveAndApproveEmployeeLeave(java.util.List, com.kp.cms.forms.employee.EmployeeOnlineLeaveForm, java.util.List, java.util.Map)
	 */
	@Override
	public boolean saveAndApproveReturnedLeaves(List<EmpApplyLeave> approveList, EmployeeOnlineLeaveForm employeeOnlineLeaveForm,List<Integer> commonLeaves, Map<Integer, String> monthMap)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(approveList != null){
				Iterator<EmpApplyLeave> iterator = approveList.iterator();
				while (iterator.hasNext()) {
					EmpApplyLeave empApplyLeave = (EmpApplyLeave) iterator.next();
					session.save(empApplyLeave);
					
					if(commonLeaves.contains(empApplyLeave.getEmpLeaveType().getId())){
						String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +empApplyLeave.getEmpLeaveType().getId()+
						" and e.employee.id=" +empApplyLeave.getEmployee().getId()+
						" and e.year="+empApplyLeave.getYear();
						Query selectedCandidatesQuery=session.createQuery(query);
						EmpLeave empLeave =(EmpLeave) selectedCandidatesQuery.uniqueResult();
						double leavesRemaining=0;
						if(empLeave==null){
							empLeave=new EmpLeave();
							empLeave.setEmpLeaveType(empApplyLeave.getEmpLeaveType());
							empLeave.setEmployee(empApplyLeave.getEmployee());
							empLeave.setIsActive(true);
							empLeave.setCreatedBy(employeeOnlineLeaveForm.getUserId());
							empLeave.setCreatedDate(new Date());
							empLeave.setLeavesAllocated(Double.valueOf(0));
							empLeave.setLeavesSanctioned(Double.valueOf(0));
							empLeave.setLeavesRemaining(leavesRemaining);
							empLeave.setYear(empApplyLeave.getYear());
							if(empApplyLeave.getEmployee() != null && empApplyLeave.getEmployee().getEmptype() != null && monthMap.containsKey(empApplyLeave.getEmployee().getEmptype().getId())){
								empLeave.setMonth(monthMap.get(empApplyLeave.getEmployee().getEmptype().getId()));
							}
						}else{
							empLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
							empLeave.setLastModifiedDate(new Date());
							leavesRemaining=empLeave.getLeavesSanctioned();
						}
						leavesRemaining=leavesRemaining+empApplyLeave.getNoOfDays();
						empLeave.setLeavesSanctioned(leavesRemaining);
						session.saveOrUpdate(empLeave);
					}else{
						String query="from EmpLeave e where e.isActive=1 and e.empLeaveType.id=" +empApplyLeave.getEmpLeaveType().getId()+
								" and e.employee.id=" +empApplyLeave.getEmployee().getId()+
								" and e.year="+empApplyLeave.getYear();
						Query selectedCandidatesQuery=session.createQuery(query);
						EmpLeave empLeave =(EmpLeave) selectedCandidatesQuery.uniqueResult();
						double remaningLeaves=empLeave.getLeavesRemaining()-empApplyLeave.getNoOfDays();
						double sanctionedLeaves=empLeave.getLeavesSanctioned()+empApplyLeave.getNoOfDays();
						empLeave.setLeavesRemaining(remaningLeaves);
						empLeave.setLeavesSanctioned(sanctionedLeaves);
						empLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
						empLeave.setLastModifiedDate(new Date());
						session.update(empLeave);
					}
				}
				List<Integer> approvedIds = employeeOnlineLeaveForm.getApprovedList();
				String hqlQuery = "from EmpOnlineLeave e where e.isActive=1 and e.id in (:ID)";
				Query query = session.createQuery(hqlQuery);
				query.setParameterList("ID", approvedIds);
				List<EmpOnlineLeave> eList = query.list();
				if(eList != null){
					Iterator<EmpOnlineLeave> iterator2 = eList.iterator();
					while (iterator2.hasNext()) {
						EmpOnlineLeave empOnlineLeave = (EmpOnlineLeave) iterator2.next();
						// code changed by sudhir
						/*empOnlineLeave.setApprovedBy(employeeOnlineLeaveForm.getEmployeeId());
						empOnlineLeave.setApprovedDate(new Date());*/
						empOnlineLeave.setLastModifiedDate(new Date());
						empOnlineLeave.setModifiedBy(employeeOnlineLeaveForm.getUserId());
						empOnlineLeave.setStatus("Approved");
						empOnlineLeave.setAuthorizedBy(employeeOnlineLeaveForm.getEmployeeId());
						empOnlineLeave.setIsApproved(true);
						session.update(empOnlineLeave);
					}
				}
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
}
