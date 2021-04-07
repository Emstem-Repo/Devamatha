package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.TelephoneExtensionNumBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.TelephoneDirectoryForm;
import com.kp.cms.transactions.employee.ITelephoneDirectoryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class TelephoneDirectoryTransactionImpl implements ITelephoneDirectoryTransaction {
	private static volatile TelephoneDirectoryTransactionImpl transactionImpl = null;
	public static TelephoneDirectoryTransactionImpl getInstance(){
		if(transactionImpl == null){
			transactionImpl = new TelephoneDirectoryTransactionImpl();
			return transactionImpl;
		}
		return transactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.ITelephoneDirectoryTransaction#getSearchDetails(com.kp.cms.forms.employee.TelephoneDirectoryForm)
	 */
	@Override
	public List<Employee> getSearchDetails(TelephoneDirectoryForm objForm)
			throws Exception {
		Session session =null;
		List<Employee> empLists;
		try{
			session = HibernateUtil.getSession();
			
			String str=  "from Employee emp where emp.active=1 and emp.isActive=1" ;
			if(objForm.getDepartmentId()!=null && !objForm.getDepartmentId().trim().isEmpty())
				str = str + " and emp.department.id=" +Integer.parseInt(objForm.getDepartmentId());
			if(objForm.getEmployeeName()!=null && !objForm.getEmployeeName().trim().isEmpty())
				str = str + " and emp.firstName like '%"+ objForm.getEmployeeName()+"%'";
			if(objForm.getFingerPrintId()!=null && !objForm.getFingerPrintId().trim().isEmpty())
				str = str + " and emp.fingerPrintId="+objForm.getFingerPrintId();
			if(objForm.getExtNo()!=null && !objForm.getExtNo().trim().isEmpty())
				str = str + " and emp.extensionNumber='"+objForm.getExtNo()+"'";
			Query query = session.createQuery(str);
			empLists = query.list();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return empLists;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.ITelephoneDirectoryTransaction#getDepartmentList()
	 */
	@Override
	public List<Department> getDepartmentList() throws Exception {
		Session session = null;
		List<Department> deptBo;
		try{
			session = HibernateUtil.getSession();
			String str = "from Department dept where dept.isActive =1 order by dept.name";
			Query query = session.createQuery(str);
			deptBo = query.list();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return deptBo;
	}
	@Override
	public EmpImages getEmpimages(int id) throws Exception {
		Session session = null;
		EmpImages empImages=null;
		try{
			session = HibernateUtil.getSession();
			String str = "from EmpImages dept where dept.employee.id ="+id;
			Query query = session.createQuery(str);
			empImages =(EmpImages)query.uniqueResult();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return empImages;
	}
	@Override
	public List<TelephoneExtensionNumBo> getTelephoneExtensionNumBo()
			throws Exception {
		Session session = null;
		List<TelephoneExtensionNumBo> list = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from TelephoneExtensionNumBo t where t.isActive =1";
			Query query = session.createQuery(str);
			list = query.list();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return list;
	}

}
