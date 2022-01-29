	package com.kp.cms.transactionsimpl.admin;
	
	import java.util.ArrayList;
import java.util.Date;
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
	
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.transactions.admin.IDepartmentEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
	
	public class DepartmentEntryTransactionImpl implements IDepartmentEntryTransaction{
		private static final Log log = LogFactory.getLog(DepartmentEntryTransactionImpl.class);
		public static volatile DepartmentEntryTransactionImpl departmentEntryTransactionImpl=null;
		public static DepartmentEntryTransactionImpl getInstance(){
			if(departmentEntryTransactionImpl == null){
				departmentEntryTransactionImpl= new DepartmentEntryTransactionImpl();
				return departmentEntryTransactionImpl;
			}
			return departmentEntryTransactionImpl;
		}
		
		/**
		 * This method will retrieve all the Department records.
		 */
		public List<Department> getDepartmentFields() throws Exception {
			Session session = null;
			List<Department> result;
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = InitSessionFactory.getInstance().openSession();
				Query query = session.createQuery("select a from Department a left join a.employeeStreamBO b where a.isActive=1 order by a.employeeStreamBO.name, a.name");
					
				
				result = query.list();
				session.flush();
				//session.close();
				// sessionFactory.close();
			} catch (Exception e) {
				log.error("Error during getting Department..." + e);
				if (session != null) {
					session.flush();
					// session.close();
				}
				throw new ApplicationException(e);
			}
			return result;
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.IDepartmentEntryTransaction#isDuplicateDepartmentEntry(com.kp.cms.bo.admin.Department)
		 */
		@Override
		public Department isDuplicateDepartmentEntry(Department dupliDepartment)
				throws Exception {
			Session session=null;
			Department department;
			try{
				session=HibernateUtil.getSession();
				String query="from Department depart where depart.name=:department";
				Query que=session.createQuery(query);
				que.setString("department", dupliDepartment.getName());
				department=(Department) que.uniqueResult();
				session.flush();
			}catch (Exception e) {
				log.error("Error during duplcation checking..." ,e);
				//session.flush();
				//session.close();
				throw new ApplicationException(e);
			}
			return department;
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.IDepartmentEntryTransaction#addDepartment(com.kp.cms.bo.admin.Department, java.lang.String)
		 */
		@Override
		public boolean addDepartment(Department department, String mode)throws Exception{
			Session session = null;
			Transaction transaction= null;
			boolean isAdded=false;
			try{
				session=InitSessionFactory.getInstance().openSession();
				transaction=session.beginTransaction();
				transaction.begin();
				if(mode.equalsIgnoreCase("Add")){
					session.save(department);
				}else if(mode.equalsIgnoreCase("Edit")){
					session.update(department);
				}
				transaction.commit();
				session.flush();
				session.close();
				isAdded = true;
			}catch (ConstraintViolationException e) {
				if(transaction!=null)
				     transaction.rollback();
				log.error("Error during saving admitted Through data..." ,e);
				throw new BusinessException(e);
			} catch (Exception e) {
				if(transaction!=null)
				     transaction.rollback();
				log.error("Error during saving admitted Through data..." ,e);
				throw new ApplicationException(e);
			}
			return isAdded;
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.IDepartmentEntryTransaction#deleteDepartmentEntry(int, boolean, com.kp.cms.forms.admin.DepartmentEntryForm)
		 */
		@Override
		public boolean deleteDepartmentEntry(int departmentId,
				boolean activate, DepartmentEntryForm departmentEntryForm)
				throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isDeleted= false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			Department department= (Department)session.get(Department.class, departmentId);
			if(activate){
				department.setIsActive(true);
			}else{
				department.setIsActive(false);
			}
			department.setLastModifiedDate(new Date());
			department.setModifiedBy(departmentEntryForm.getUserId());
			session.update(department);
			transaction.commit();
			session.flush();
			//session.close();
			 isDeleted = true;
		}catch (ConstraintViolationException e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting Department data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			     transaction.rollback();
			log.error("Error during deleting Department data..." ,e);
			throw new ApplicationException(e);
		}
		return isDeleted;
			
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.IDepartmentEntryTransaction#getEmpStream()
		 */
		@Override
		public Map<Integer, String> getEmpStream() throws Exception {
			Session session = null;
			List<EmployeeStreamBO> list;
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

		@Override
		public Department isDuplicateDepartmentCodeEntry(Department dupliDepartmentBO) throws Exception {
			Session session=null;
			Department department;
			try{
				session=HibernateUtil.getSession();
				String query="from Department depart where depart.code=:departmentCode";
				
				Query que=session.createQuery(query);
				que.setString("departmentCode", dupliDepartmentBO.getCode());
				department=(Department) que.uniqueResult();
				session.flush();
			}catch (Exception e) {
				log.error("Error during duplcation checking..." ,e);
				//session.flush();
				//session.close();
				throw new ApplicationException(e);
			}
			return department;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.IDepartmentEntryTransaction#getDepartmentdetails(int)
		 */
		@Override
		public Department getDepartmentdetails(int id) throws Exception {
			Session session=null;
			Department department=null;
			try{
				session=HibernateUtil.getSession();
				String str="from Department dept where dept.id="+id;
				Query query=session.createQuery(str);
				department=(Department)query.uniqueResult();
				
			}catch(Exception e){
				log.error("Error during getting TeacherDepartment by id..." , e);
				//session.flush();
				//session.close();
				
			}
			return department;
			
		}
	}
