package com.kp.cms.transactionsimpl.employee;

import java.io.FileOutputStream;
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
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditNewForm;
import com.kp.cms.transactions.employee.IEmployeeEditInfoNewTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class EmployeeEditInfoNewTransactionImpl  implements IEmployeeEditInfoNewTransaction{
	
		private static final Log log = LogFactory
				.getLog(EmployeeEditInfoNewTransactionImpl.class);

		public static volatile EmployeeEditInfoNewTransactionImpl obImpl = null;

		public static EmployeeEditInfoNewTransactionImpl getInstance() {
			if (obImpl == null) {
				obImpl = new EmployeeEditInfoNewTransactionImpl();
			}
			return obImpl;
		}

		

		@Override
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



		@Override
		public StringBuffer getSerchedEmployeeQuery(int departmentId,
				int designationId, int streamId, int empTypeId, Integer eid,
				Integer rid2, int principleid, int adminid, int testid,
				String tempTeachingStaff) throws Exception {
			StringBuffer query = new StringBuffer(
					"from Employee e "
					+" where e.isActive = 1 ");
			
			if  (rid2 ==principleid || rid2==adminid || rid2==testid)  {
				
			}
			else
				query = query.append(" and e.id='"
						+ eid+"'");
				
			
			
			if(tempTeachingStaff!=null)
			{
				if (tempTeachingStaff.equals("1")) {
					
					query = query.append(" and e.teachingStaff= 1 ");
				}
				else if (tempTeachingStaff.equals("0")) {
					
					query = query.append(" and e.teachingStaff= 0 ");
				}
				else if (tempTeachingStaff.equals("2")) {
				//	query = query.append(" and e.teachingStaff is not null");
					query = query.append(" and (e.teachingStaff= 0 OR e.teachingStaff= 1)");
				}
			}
			if (departmentId > 0) {
					query = query
							.append(" and e.department.id='"
									+ departmentId+"'");
				}
			if (streamId > 0) {
				query = query
						.append(" and e.streamId.id='"
								+ streamId+"'");
			}
			if (designationId > 0) {
					query = query
							.append(" and e.designation.id='"
									+ designationId+"'");
				}
			if (empTypeId > 0) {
				query = query
						.append(" and e.emptype.id='"
								+ empTypeId+"'");
			}
			
			query.append(" order by e.firstName");
		return query;
		}



		@Override
		public List<Department> getEmployeeDepartment() throws Exception {
			
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



		@Override
		public List<Designation> getEmployeeDesignation() throws Exception {
			Session session = null;
			List<Designation> DesList;
		
			try {
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from Designation c where c.isActive=true");
				DesList=query.list();
				
			} catch (Exception e) {
				throw new ApplicationException(e);
			}
			return DesList;
		}



		@Override
		public Employee GetEmpDetails(int employeeId)
				throws Exception {

			Session session=null;
			Employee employee=null;
			try {
			session=HibernateUtil.getSession();
				Query query=session.createQuery("from Employee e where e.id='"+employeeId +"' and e.isActive=1");
				employee=(Employee) query.uniqueResult();
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}/*finally{
			if(session!=null){
				session.flush();
			}
		}*/
			return employee;
		
		}



		@Override
		public boolean saveEmployee(Employee employee,EmployeeInfoEditNewForm employeeInfoEditNewForm)throws Exception {
			Session session=null;
			boolean flag=false;
			Transaction tx=null;
			try{
					session=HibernateUtil.getSession();
					tx=session.beginTransaction();
					tx.begin();
					session.saveOrUpdate(employee);
					tx.commit();
					flag=true;
			}catch(Exception e){
				tx.rollback();
				throw new ApplicationException(e);
			}finally{
				if(session!=null){
					session.flush();
				}
			}
			return flag;
		}



		@Override
		public Users userExists(EmployeeInfoEditNewForm employeeInfoEditNewForm) throws Exception {
			Session session=null;
			Users user=null;
			try{
				session=HibernateUtil.getSession();
				String quer="from Users user where user.employee.id="+employeeInfoEditNewForm.getEmployeeId();
				Query query=session.createQuery(quer);
				user=(Users)query.uniqueResult();
			}catch(Exception e){
				log.error("Error while getting userExists.." +e);
			}
			return user;
		}



		@Override
		public boolean updateUser(Users user) throws Exception {
			Session session=null;
			boolean flag=false;
			Transaction tx=null;
			try{
					session=HibernateUtil.getSession();
					tx=session.beginTransaction();
					tx.begin();
					session.save(user);
					tx.commit();
					flag=true;
			}catch(Exception e){
				tx.rollback();
				throw new ApplicationException(e);
			}finally{
				if(session!=null){
					session.flush();
				}
			}
			return flag;
		}



		@Override
		public Map<String, String> getDesignationMap() throws Exception {
			Session session=null;
			Map<String,String> map=new HashMap<String, String>();
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from Designation d where d.isActive=true");
				List<Designation> list=query.list();
				if(list!=null){
					Iterator<Designation> iterator=list.iterator();
					while(iterator.hasNext()){
						Designation designation=iterator.next();
						if(designation.getId()!=0 && designation.getName()!=null && !designation.getName().isEmpty())
						map.put(String.valueOf(designation.getId()),designation.getName());
					}
				}
			}catch (Exception exception) {
				
				throw new ApplicationException();
			}/*finally{
				if(session!=null){
					session.flush();
				}
			}*/
			map = (Map<String, String>) CommonUtil.sortMapByValue(map);
			return map;
		}



		@Override
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
			}/*finally{
				if(session!=null){
					session.flush();
				}
			}*/
			map = (Map<String, String>) CommonUtil.sortMapByValue(map);
			return map;
		}



		@Override
		public Users getUser(EmployeeInfoEditNewForm objform) throws Exception {
			Session session=null;
			Users user=null;
			try{
				session=HibernateUtil.getSession();
				String quer="from Users user where user.id="+objform.getSelectedEmployeeId();
				Query query=session.createQuery(quer);
				user=(Users)query.uniqueResult();
			}catch(Exception e){
				log.error("Error while getting userExists.." +e);
			}
			return user;
		}



		@Override
		public GuestFaculty getGuestDetails(int guestId)
				throws Exception {
			Session session=null;
			GuestFaculty employee=null;
			try {
			session=HibernateUtil.getSession();
				Query query=session.createQuery("from GuestFaculty e where e.id='"+guestId +"' and e.isActive=1");
				employee=(GuestFaculty) query.uniqueResult();
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}/*finally{
			if(session!=null){
				session.flush();
			}
		}*/
			return employee;
		}



		@Override
		public boolean saveGuestData(GuestFaculty guest,
				EmployeeInfoEditNewForm employeeInfoEditNewForm)
				throws Exception {
			Session session=null;
			boolean flag=false;
			Transaction tx=null;
			try{
					session=HibernateUtil.getSession();
					tx=session.beginTransaction();
					tx.begin();
					session.saveOrUpdate(guest);
					tx.commit();
					flag=true;
			}catch(Exception e){
				tx.rollback();
				throw new ApplicationException(e);
			}finally{
				if(session!=null){
					session.flush();
				}
			}
			return flag;
		}



		@Override
		public Users userExists1(EmployeeInfoEditNewForm employeeInfoEditNewForm)
				throws Exception {
			Session session=null;
			Users user=null;
			try{
				session=HibernateUtil.getSession();
				String quer="from Users user where user.guest.id="+employeeInfoEditNewForm.getGuestId();
				Query query=session.createQuery(quer);
				user=(Users)query.uniqueResult();
			}catch(Exception e){
				log.error("Error while getting userExists.." +e);
			}
			return user;
		}



		



		

		

}
