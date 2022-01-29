package com.kp.cms.transactionsimpl.employee;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.bo.employee.EmpEventVacationDepartment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmpEventVacationForm;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.transactions.employee.IEmpEventVacation;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class EmpEventVacationImpl implements IEmpEventVacation {
	private static Log log = LogFactory.getLog(EmpEventVacationImpl.class);
	
	private static volatile EmpEventVacationImpl instance=null;
	
	/**
	 * 
	 */
	private EmpEventVacationImpl(){}

	/**
	 * @return
	 */
	public static IEmpEventVacation getInstance() {
		// TODO Auto-generated method stub
		if(instance==null)
			instance=new EmpEventVacationImpl();
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpEventVacation#getDepartments()
	 */
	@Override
	public Map<String, String> getDepartments() throws Exception {
		// TODO Auto-generated method stub
		List<Department> list=null;
		Map<String, String> deptMap=new LinkedHashMap<String, String>();
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Department d where d.isActive=1 order by d.name ");
			list=query.list();
			if(list!=null){
				Iterator< Department> iterator=list.iterator();
				while(iterator.hasNext()){
					Department dept=iterator.next();
					if(dept!=null){
						if(dept.getName()!=null && !dept.getName().isEmpty() && dept.getId()>0){
							deptMap.put(String.valueOf(dept.getId()), dept.getName());
						}
					}
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException(exception);
			
		}finally{
			if (session != null) {
				session.flush();
				
			}
		}
		return deptMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpEventVacation#submitEvent(com.kp.cms.bo.employee.EmpEventVacation)
	 */
	@Override
	public Boolean submitEvent(EmpEventVacation empEventVacation)throws Exception {
		Session session=null;
		Transaction tx=null;
		Boolean result=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			session.clear();
			session.saveOrUpdate(empEventVacation);
			tx.commit();
			session.flush();
			
			result = true;
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException(e);
		}finally{
			session.flush();
			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpEventVacation#getEmpVacationList()
	 */
	@Override
	public List<EmpEventVacation> getEmpVacationList() throws Exception {
		List<EmpEventVacation> list=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpEventVacation e where e.isActive is true");
			list=query.list();
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException(exception);
			
		}finally{
			if (session != null) {
				session.flush();
				
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpEventVacation#getEmpEventVacation(java.lang.String)
	 */
	@Override
	public EmpEventVacation getEmpEventVacation(String id) throws Exception {
		EmpEventVacation empEventVacation=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			if(id!=null && !id.isEmpty()){
				Query query=session.createQuery("from EmpEventVacation e where e.id="+Integer.parseInt(id)+" and e.isActive is true");
				empEventVacation=(EmpEventVacation)query.uniqueResult();
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException(exception);
			
		}finally{
			if (session != null) {
				session.flush();
				
			}
		}
		return empEventVacation;
	}

	@SuppressWarnings("unchecked")
	public List<EmpEventVacation> getEmpReactivation(EmpEventVacationForm empEventVacationForm) throws Exception {
		List<EmpEventVacation> empEventVacation=null;
		List<Integer> toList=new ArrayList<Integer>();
		for(int count=0;count<empEventVacationForm.getDepartment().length;count++)
		{
			Integer departmentId=Integer.parseInt(empEventVacationForm.getDepartment()[count]);
			toList.add(departmentId);
		}
	
				
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			if(empEventVacationForm.getStreamId()!=null)
			{
			Query query1=session.createQuery("from EmpEventVacation e join e.empEventVacationDepartment dept " +
					"where e.type='"+empEventVacationForm.getType()+"' and e.teachingStaff='"+empEventVacationForm.getTeachingStaff()+"' " +
					"and e.fromDate='"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getFromDate())+"' " +
					"and e.toDate='"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getToDate())+"' " +
					"and e.stream.id='"+empEventVacationForm.getStreamId()+"' and dept.department.id in (:department1) and e.isActive='0' group by e.id");
			query1.setParameterList("department1", toList);
			empEventVacation=query1.list();
			System.out.println("________________-  If done     --------------------------------------------");
			}
			
			else
			{
				Query query1=session.createQuery("select e from EmpEventVacation e join e.empEventVacationDepartment dept " +
						"where e.type='"+empEventVacationForm.getType()+"' and e.teachingStaff='"+empEventVacationForm.getTeachingStaff()+"' " +
						"and e.fromDate='"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getFromDate())+"' " +
						"and e.toDate='"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getToDate())+"' " +
						"and e.isActive='0' and dept.department.id in (:department1) group by e.id");
				query1.setParameterList("department1", toList);
				empEventVacation=query1.list();
				System.out.println("________________-Else done       --------------------------------------------");
			}
				
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException(exception);
		}finally{
			if (session != null) {
				session.flush();
			}
		}
		return empEventVacation;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpEventVacation#getEmpEventUnique(java.lang.String, java.sql.Date, java.sql.Date)
	 */
	
	public List<EmpEventVacation> getEmpEventUnique(EmpEventVacationForm empEventVacationForm) throws Exception {
		List<EmpEventVacation> empEventVacation=null;
		List<Integer> toList=new ArrayList<Integer>();
		for(int count=0;count<empEventVacationForm.getDepartment().length;count++)
		{
			Integer departmentId=Integer.parseInt(empEventVacationForm.getDepartment()[count]);
			toList.add(departmentId);
		}
				
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			if(empEventVacationForm.getStreamId()!=null)
			{
			Query query=session.createQuery("select e from EmpEventVacation e join e.empEventVacationDepartment dept " +
					"where e.teachingStaff='"+empEventVacationForm.getTeachingStaff()+"' " +
					"and e.fromDate between '"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getFromDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getToDate())+"' " +
					"and e.toDate between '"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getFromDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getToDate())+"' " +
					"and e.stream.id='"+empEventVacationForm.getStreamId()+"' and dept.department.id in (:department) group by e.id");
			query.setParameterList("department", toList);
			empEventVacation=query.list();
			}
			else
			{
				Query query=session.createQuery("select e from EmpEventVacation e join e.empEventVacationDepartment dept " +
						"where  e.teachingStaff='"+empEventVacationForm.getTeachingStaff()+"' " +
						"and e.fromDate between '"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getFromDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getToDate())+"' " +
						"and e.toDate between '"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getFromDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(empEventVacationForm.getToDate())+"' " +
						"and dept.department.id in (:department) group by e.id");
				query.setParameterList("department", toList);
				empEventVacation=query.list();
			}
				
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException(exception);
			
		}finally{
			if (session != null) {
				session.flush();
			}
		}
		return empEventVacation;
	}

	@Override
	public Map<Integer, String> getStreamsMap() throws Exception {
		Session session = null;
		List<EmployeeStreamBO> list = null;
		Map<Integer, String> empStreamList= new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			String str="from EmployeeStreamBO empstream where empstream.isActive=1";
			Query query=session.createQuery(str);
			list= query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		if(list!=null){
			Iterator<EmployeeStreamBO> iterator = list.iterator();
			while (iterator.hasNext()) {
				EmployeeStreamBO employeeStreamBO = (EmployeeStreamBO) iterator.next();
				if(employeeStreamBO.getId() > 0){
					empStreamList.put(employeeStreamBO.getId(), employeeStreamBO.getName());
				}
			}
		}
		return empStreamList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpEventVacation#getSearchedDepartmentStreamNames(java.lang.String)
	 */
	@Override
	public List<Department> getSearchedDepartmentStreamNames(String streamId,String teachingStaff)
			throws Exception {
		List<Department> departmentStreamNameList = null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String str="select department from Department department";
			boolean isTeaching=false;
			/*if(teachingStaff!=null && !teachingStaff.isEmpty()){
				str=str+" join department.employees e where e.teachingStaff="+teachingStaff+" and ";
				if(teachingStaff.equalsIgnoreCase("1")){
					str=str+"  department.isAcademic="+1+" and ";
				}else{
					str=str+"  department.isAcademic="+0+" and ";
				}
				isTeaching=true;
			}
			str=str+(!isTeaching?" where ":" ")+" department.isActive=1 "+((streamId!=null && !streamId.isEmpty())?("and department.employeeStreamBO.id='"+streamId+"'"):"");
			Commented by Cimi on 29/12/12 as the teaching no teaching filteration is not required for department listing in exam/event/vacation screen*/ 
			str=str+" where department.isActive=1 "+((streamId!=null && !streamId.isEmpty())?("and department.employeeStreamBO.id='"+streamId+"'"):"");
			
			Query query=session.createQuery(str);
			departmentStreamNameList=query.list();
			session.flush();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return departmentStreamNameList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpEventVacation#getSearchedDepartmentStreamNames()
	 */
	@Override
	
							
	public List<Department> getSearchedDepartmentStreamNames(String teaching) throws Exception {
		List<Department> departmentStreamNameList = null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String str="select d from Department d join d.employees e where d.isActive=1";
			/*if(teaching != null && !teaching.trim().isEmpty()){
				str = str + " and  e.teachingStaff="+teaching;
			}Commented by Cimi on 29/12/12 as the teaching no teaching filteration is not required for department listing in exam/event/vacation screen*/
			str = str + " order by d.name";
			Query query=session.createQuery(str);
			departmentStreamNameList=query.list();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return departmentStreamNameList;
	}
	
}
