package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.forms.employee.EmpResPubPendApprovalForm;
import com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author mary
 *
 */
public class EmpResPubPendApprovalImpl implements IEmpResPubPendApprovalTransaction {
	private static final Log log = LogFactory
	.getLog(EmpResPubPendApprovalImpl.class);

public static volatile EmpResPubPendApprovalImpl obImpl = null;

public static EmpResPubPendApprovalImpl getInstance() {
if (obImpl == null) {
	obImpl = new EmpResPubPendApprovalImpl();
}
return obImpl;
}


			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction#getSerchedEmployee(java.lang.StringBuffer)
			 */
			public List<Employee> getSerchedEmployee(String query)
			throws Exception {
			Session session = null;
			List<Employee> empList;
			
			try {
			session = HibernateUtil.getSession();
			Query queri = session.createQuery(query);
			empList = queri.list();
			
			} catch (Exception e) {
			throw new ApplicationException(e);
			}
			return empList;
			}
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction#GetResearchDetails(com.kp.cms.forms.employee.EmpResPubPendApprovalForm)
			 */
			public List<EmpResearchPublicDetails> GetResearchDetails(int empId)throws Exception {
				List<EmpResearchPublicDetails> list =null;
					Session session=null;
					
					try{
						session=HibernateUtil.getSession();
						String quer="from EmpResearchPublicDetails emp where emp.employeeId='"+empId+"' and emp.isActive= true and emp.isEmployee =true and (emp.isApproved=0 or emp.isApproved is null) and (emp.isRejected=0 or emp.isRejected is null) order by emp.createdDate";
						Query query=session.createQuery(quer);
						list= query.list();
						
					}catch(Exception e){
						log.error("Error while getting EmpResearchPublicDetails" +e);
					}
					return list;
					
				}
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction#GetResearchDetails(com.kp.cms.forms.employee.EmpResPubPendApprovalForm)
			 */
			public List<EmpResearchPublicDetails> getEmployeeResearchDetailsApproved(int empId)throws Exception {
				List<EmpResearchPublicDetails> list =null;
					Session session=null;
					
					try{
						session=HibernateUtil.getSession();
						String quer="from EmpResearchPublicDetails emp where emp.employeeId='"+empId+"' and emp.isActive= true and emp.isEmployee =true and emp.isApproved=true order by emp.createdDate";
						Query query=session.createQuery(quer);
						list= query.list();
						
					}catch(Exception e){
						log.error("Error while getting EmpResearchPublicDetails" +e);
					}
					return list;
					
				}
			public List<EmpResearchPublicDetails> getEmployeeApprovalPendingByEmployeeId(int empId)throws Exception {
				List<EmpResearchPublicDetails> list =null;
					Session session=null;
					
					try{
						session=HibernateUtil.getSession();
						String quer="from EmpResearchPublicDetails emp where emp.employeeId='"+empId+"' and emp.isActive= true and emp.isEmployee =true and (emp.isApproved=0 or emp.isApproved is null) and (emp.isRejected=0 or emp.isRejected is null) order by emp.createdDate";
						Query query=session.createQuery(quer);
						list= query.list();
						
					}catch(Exception e){
						log.error("Error while getting EmpResearchPublicDetails" +e);
					}
					return list;
					
				}
			
			
			/**
			 * @param objform
			 * @return
			 * @throws Exception
			 */
			public Employee GetEditEmpDetails(EmpResPubPendApprovalForm objform) throws Exception {
				Session session=null;
				Employee employee=null;
				try {
				session=HibernateUtil.getSession();
				if(StringUtils.isNotEmpty(objform.getSelectedEmployeeId())){
					Query query=session.createQuery("from Employee e where e.id='"+objform.getSelectedEmployeeId() +"' and e.isActive=1");
					employee=(Employee) query.uniqueResult();
				}
			
			}catch (Exception exception) {
				
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
				return employee;
			}

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction#getEmployeeDepartment()
			 */
			public List<Department> getEmployeeDepartment() throws Exception 
			{
			Session session = null;
			List<Department> depList;
		
			try {
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from Department c where c.isActive=true");
				 depList=query.list();
				
				
			} catch (Exception e) {
				throw new ApplicationException(e);
			}
			return depList;
		}
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction#getEmployeeIdFromUserId(com.kp.cms.forms.employee.EmpResPubPendApprovalForm)
			 */
			public int getEmployeeIdFromUserId (EmpResPubPendApprovalForm empResPubForm)throws Exception
			{	
				String userId=empResPubForm.getUserId();
					Session session=null;
					String id="";
					int empId=0;
					try{
						session=HibernateUtil.getSession();
						String quer="select emp.id from Employee emp inner join emp.userses users where users.id="+userId+" and emp.id!=null";
						Query query=session.createQuery(quer);
						id=query.uniqueResult().toString();
						 empId=Integer.parseInt(id);
					}catch(Exception e){
						log.error("Error while getting Employee id.." +e);
					}
					return empId;
				}

			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.employee.IEmpResPubPendApprovalTransaction#saveEmpResPub(java.util.List)
			 */
			public boolean saveEmpResPub(List<EmpResearchPublicDetails> empResPubDetails) throws Exception {
				Session session=null;
				boolean flag=false;
				Transaction transaction=null;
				EmpResearchPublicDetails empResearchPubDets;
				try{
					session = InitSessionFactory.getInstance().openSession();
					transaction = session.beginTransaction();
					transaction.begin();
					Iterator<EmpResearchPublicDetails> tcIterator = empResPubDetails.iterator();
					
					while(tcIterator.hasNext()){
						empResearchPubDets = tcIterator.next();
						session.saveOrUpdate(empResearchPubDets);
							
					}
					transaction.commit();
					session.flush();
					log.debug("leaving saveEmpResPub");
					flag=true;
						
				}catch(Exception e){
					transaction.rollback();
					throw new ApplicationException(e);
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				return flag;
			}
			
			
			public Employee getEmployeeIdFromUserIdEmp(EmpResPubPendApprovalForm empResPubForm) throws Exception {
				String userId=empResPubForm.getUserId();
				Session session=null;
				Employee employee=null;
				try {
				session=HibernateUtil.getSession();
					Query query=session.createQuery("select u.employee from Users u where u.isActive=1 and u.id="+userId);
					employee=(Employee) query.uniqueResult();

			}catch (Exception exception) {
				
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
				return employee;
			}
			public Employee getEmailfromEmployeeId(int empId) throws Exception {
				Session session=null;
				Employee emp=null;
				try {
				session=HibernateUtil.getSession();
					Query query=session.createQuery("from Employee e where e.id="+empId);
					emp=(Employee) query.uniqueResult();

			}catch (Exception exception) {
				
				throw new ApplicationException();
			}finally{
				if(session!=null){
					session.flush();
				}
			}
				return emp;
			}
			
	public StringBuffer getSerchedEmployeeQuery(String userId, String FingerPrintId, int departmentId, String Name, String Active, boolean isApprovedList , boolean isPending)
					throws Exception {
					StringBuffer query = new StringBuffer(
								"select empres.employeeId from EmpResearchPublicDetails empres" +
								" where empres.isActive=1 and emp.isEmployee =true and empres.approverId.id=(select u.employee.id from Users u where u.id="+userId+")");
					if(isPending)
					{
						query = query.append(" and empres.isApproved=0 and (empres.isRejected=0 or empres.isRejected is null)");
					}
					else if(isApprovedList)
					{
						query = query.append(" and empres.isApproved=1 and (empres.isRejected=0 or empres.isRejected is null)");
						
					}
						
					if (FingerPrintId != null && !StringUtils.isEmpty(FingerPrintId.trim())) {
						query = query.append(" and empres.employeeId.fingerPrintId='"
								+ FingerPrintId+"'");
					}
					if (Name != null && !StringUtils.isEmpty(Name.trim())) {
						query = query.append(" and empres.employeeId.firstName like '"
								+ Name+"%'");
					}
					if(Active !=null)
					{
						if (Active.equals("1")) {
							
							query = query.append(" and empres.employeeId.active= 1 ");
						}
						else if (Active.equals("0")) {
							
							query = query.append(" and empres.employeeId.active= 0 ");
						}
					}
					
					if (departmentId > 0) {
							query = query
									.append(" and empres.employeeId.department.id='"
											+ departmentId+"'");
						}
					query.append("group by empres.employeeId order by empres.employeeId.firstName ");
				return query;
			}
			
			public List<Employee> getSerchedEmployee(StringBuffer query)
					throws Exception {
				Session session = null;
				List<Employee> empList;
				try {
					session = HibernateUtil.getSession();
					Query queri = session.createQuery(query.toString());
					empList = queri.list();
					
				} catch (Exception e) {
					throw new ApplicationException(e);
				}
				return empList;
			}
			
			
			public Map<String, String> getDepartmentMap() throws Exception {
				Session session=null;
				Map<String,String> map=new HashMap<String, String>();
				try{
					session=HibernateUtil.getSession();
					Query query=session.createQuery("from Department d where d.isActive=true");
					List<Department> list=query.list();
					if(list!=null){
						Iterator<Department> iterator=list.iterator();
						while(iterator.hasNext()){
							Department department=iterator.next();
							if(department.getId()!=0 && department.getName()!=null && !department.getName().isEmpty())
							map.put(String.valueOf(department.getId()),department.getName());
						}
					}
				}catch (Exception exception) {
					
					throw new ApplicationException();
				}finally{
					if(session!=null){
						session.flush();
					}
				}
				map = (Map<String, String>) CommonUtil.sortMapByValue(map);
				return map;
			}


	public Employee GetEmployee(EmpResPubPendApprovalForm objform)throws Exception
	{
			Employee list =null;
			Session session=null;
			
			try{
				session=HibernateUtil.getSession();
				String quer="from Employee emp where emp.id='"+objform.getSelectedEmployeeId()+"' and emp.isActive= true";
				Query query=session.createQuery(quer);
				list= (Employee) query.uniqueResult();
				
			}catch(Exception e){
				log.error("Error while getting GetEmployee" +e);
			}
			return list;
			
		}
			
}
