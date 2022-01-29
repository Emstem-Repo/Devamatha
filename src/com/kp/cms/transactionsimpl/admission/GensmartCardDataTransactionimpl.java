package com.kp.cms.transactionsimpl.admission;

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

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.transactions.admission.IGensmartCardDataTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class GensmartCardDataTransactionimpl implements IGensmartCardDataTransaction {
	/**
	 * Singleton object of GensmartCardDataTransactionimpl
	 */
	private static volatile GensmartCardDataTransactionimpl gensmartCardDataTransactionimpl = null;
	private static final Log log = LogFactory.getLog(GensmartCardDataTransactionimpl.class);
	private GensmartCardDataTransactionimpl() {
		
	}
	/**
	 * return singleton object of GensmartCardDataTransactionimpl.
	 * @return
	 */
	public static GensmartCardDataTransactionimpl getInstance() {
		if (gensmartCardDataTransactionimpl == null) {
			gensmartCardDataTransactionimpl = new GensmartCardDataTransactionimpl();
		}
		return gensmartCardDataTransactionimpl;
	}
	/* (non-Javadoc)
	 * getting the students BO based on the input 
	 * @see com.kp.cms.transactions.admission.IGensmartCardDataTransaction#getStudentList(com.kp.cms.forms.admission.GensmartCardDataForm)
	 */
	@Override
	public List<Student> getStudentList(GensmartCardDataForm genscDataForm) throws Exception {
		Session session = null;
		List<Student> selectedStudentsList = null;
		try {
			session = HibernateUtil.getSession();
			String query= "from Student s where s.isAdmitted=1 and s.admAppln.isCancelled=0" +
					" and (s.isHide=0 or s.isHide is null) and s.isSCDataGenerated=0  and s.admAppln.appliedYear="+genscDataForm.getAcademicYear();
			
			if(genscDataForm.getProgramTypeId()!=null && !genscDataForm.getProgramTypeId().isEmpty()){
				query+=" and s.admAppln.courseBySelectedCourseId.program.programType.id= "+genscDataForm.getProgramTypeId();
			}
			if(genscDataForm.getProgramId()!=null && !genscDataForm.getProgramId().isEmpty()){
				query+=" and s.admAppln.courseBySelectedCourseId.program.id= "+genscDataForm.getProgramId();
			}
			if(genscDataForm.getCourseId()!=null && !genscDataForm.getCourseId().isEmpty()){
				query+=" and s.admAppln.courseBySelectedCourseId.id= "+genscDataForm.getCourseId();
			}
			if(genscDataForm.getRegNoFrom()!=null && !genscDataForm.getRegNoFrom().isEmpty()){
				query+=" and s.registerNo >= '"+genscDataForm.getRegNoFrom()+"'";
			}
			if(genscDataForm.getRegNoTo()!=null && !genscDataForm.getRegNoTo().isEmpty()){
				query+=" and s.registerNo <= '"+genscDataForm.getRegNoTo()+"'";
			}
			query+="  order by s.admAppln.courseBySelectedCourseId.id, s.registerNo";
			selectedStudentsList = session.createQuery(query).list();
			return selectedStudentsList;
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
	public Map<String, byte[]> getStudentPhotos(List<Integer> studentIds)
			throws Exception {
		Session session = null;
		Map<String, byte[]> studentPhotos=new HashMap<String, byte[]>();
		List list = null;
		try {
			if(studentIds!=null && !studentIds.isEmpty()){
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery("select s.registerNo, docs.document from Student s" +
						" join s.admAppln.applnDocs docs where (s.isHide=0 or s.isHide is null) and s.isSCDataGenerated=0 and s.admAppln.isCancelled=0 and docs.isPhoto=1 and docs.document is not null and docs.document!='' and s.id in (:studentList) and s.registerNo is not null and s.registerNo!=''  group by s.id order by s.registerNo").setParameterList("studentList",studentIds);
				list = selectedCandidatesQuery.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null && objects[1]!=null)
							studentPhotos.put(objects[0].toString(),(byte[])objects[1]);
					}
				}
			}
			return studentPhotos;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	@Override
	public String getCourseName(int courseId) throws Exception {
		Session session = null;
		String course=null;
		try {
				session = HibernateUtil.getSession();
				Query getCName=session.createQuery("select c.name from Course c where c.id="+courseId );
				course = (String)getCName.uniqueResult();
				if(course!=null && !course.isEmpty()){
					return course;
				}
				else return "";
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
	 * updating the field is_sc_data_generated in DB
	 * @see com.kp.cms.transactions.admission.IGensmartCardDataTransaction#update(java.util.List)
	 */
	@Override
	
	public void update(List<Integer> studentIds,String user) throws Exception {
		Session session = null;
		Transaction txn=null;
		try {
				session = HibernateUtil.getSession();
				txn=session.beginTransaction();
				if(studentIds!=null && !studentIds.isEmpty()){
					Iterator<Integer> itr=studentIds.iterator();
					while(itr.hasNext()){
						int id= itr.next();
						Student st= (Student)session.get(Student.class,id );
						st.setIsSCDataGenerated(true);
						st.setLastModifiedDate(new Date());
						st.setModifiedBy(user);
						session.update(st);
					}
				}
				txn.commit();
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * getting the ids without photos and regNos to display in the form
	 * @see com.kp.cms.transactions.admission.IGensmartCardDataTransaction#getStudentsWithoutPhotosAndRegNos(java.util.List)
	 */
	@Override
	public List<Integer> getStudentsWithoutPhotosAndRegNos(GensmartCardDataForm genscForm) throws Exception {
		Session session = null;
		List<String> stuRegNos=new ArrayList<String>();
		List<String> applnNos=new ArrayList<String>();
		List list=null;
		List<Integer> studIds=genscForm.getStudentIds();
		List<Integer> stuIdsWithoutPhotosOrRegNos = new ArrayList<Integer>();
		try {
				session = HibernateUtil.getSession();
				Query query=session.createQuery("select s.registerNo,s.admAppln.applnNo,s.id from Student s" +
						" left join s.admAppln.applnDocs docs " +
						" where s.isAdmitted=1 and s.isSCDataGenerated=0 " +
						" and s.admAppln.isCancelled=0 and (s.isHide=0 or s.isHide is null) and docs.isPhoto=1 and " +
						" ((docs.document is null or docs.document='') or (s.registerNo is null or s.registerNo='')) and s.id in (:studentIds) group by s.id order by s.registerNo" ).setParameterList("studentIds", studIds);
				list=query.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object> itr=list.iterator();
					while(itr.hasNext()){
					Object[] obj=(Object[])itr.next();
					if(obj[0]!=null && !obj[0].toString().trim().isEmpty()){
						stuRegNos.add(obj[0].toString());
					}
					else if(obj[1]!=null){
						applnNos.add(obj[1].toString());
					}
					if(obj[2]!=null){
						stuIdsWithoutPhotosOrRegNos.add((Integer)obj[2]);
					}
				}
				genscForm.setStudentsWithoutPhotos(stuRegNos);
				genscForm.setStudentsWithoutRegNos(applnNos);
				
			}
				return stuIdsWithoutPhotosOrRegNos;	
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
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
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	/* (non-Javadoc)
	 * querying the database to get the employee bo
	 * @see com.kp.cms.transactions.admission.IGensmartCardDataTransaction#getEmployeeList(com.kp.cms.forms.admission.GensmartCardDataForm)
	 */
	@Override
	public List<Employee> getEmployeeList(GensmartCardDataForm genscDataForm) throws Exception {
		Session session = null;
		List<Employee> selectedEmployeeList = null;
		try {
			session = HibernateUtil.getSession();
			String query= "from Employee e where e.active=1 and e.isActive=1 and e.isSCDataGenerated=0";
			
			if(genscDataForm.getDepartmentId()!=null && !genscDataForm.getDepartmentId().isEmpty()){
				query+=" and e.department.id="+genscDataForm.getDepartmentId();
			}
			
			if(genscDataForm.getEmpIdFrom()!=null && !genscDataForm.getEmpIdFrom().isEmpty()){
				query+=" and e.fingerPrintId >= '"+genscDataForm.getEmpIdFrom()+"'";
			}
			if(genscDataForm.getEmpIdTo()!=null && !genscDataForm.getEmpIdTo().isEmpty()){
				query+=" and e.fingerPrintId <= '"+genscDataForm.getEmpIdTo()+"'";
			}
			query+="  order by e.department.id,e.fingerPrintId";
			selectedEmployeeList = session.createQuery(query).list();
			return selectedEmployeeList;
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
	 * Querying the database to get emp ids without photo and empFingerPrintId
	 * @see com.kp.cms.transactions.admission.IGensmartCardDataTransaction#getEmployeesWithoutPhotosAndRegNos(com.kp.cms.forms.admission.GensmartCardDataForm)
	 */
	@Override
	public List<Integer> getEmployeesWithoutPhotosAndRegNos(GensmartCardDataForm genscDataForm) throws Exception {
		Session session = null;
		List<String> empFingerPrintIds=new ArrayList<String>();
		List<String> firstNames=new ArrayList<String>();
		List list=null;
		List<Integer> empIds=genscDataForm.getEmployeeIds();
		List<Integer> empIdsWithoutPhotosOrRegNos = new ArrayList<Integer>();
		try {
				session = HibernateUtil.getSession();
				Query query=session.createQuery("select e.fingerPrintId,e.firstName,e.id from Employee e join e.empImages i where e.active=1  and e.isActive=1 " +
						" and ((i.empPhoto is null or i.empPhoto='') or (e.fingerPrintId is null or e.fingerPrintId='')) and e.isSCDataGenerated=0 and e.id in (:employeeIds) group by e.id").setParameterList("employeeIds", empIds);
				list=query.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object> itr=list.iterator();
					while(itr.hasNext()){
					Object[] obj=(Object[])itr.next();
					if(obj[0]!=null && !obj[0].toString().trim().isEmpty()){
						empFingerPrintIds.add(obj[0].toString());
					}
					else if(obj[1]!=null && !obj[1].toString().trim().isEmpty()){
						firstNames.add(obj[1].toString());
					}
					if(obj[2]!=null){
						empIdsWithoutPhotosOrRegNos.add((Integer)obj[2]);
					}
				}
					genscDataForm.setEmployeesWithoutPhotos(empFingerPrintIds);
					genscDataForm.setEmployeesWithoutFingerPrintIds(firstNames);
				
			}
				return empIdsWithoutPhotosOrRegNos;	
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
	 *Querying the database to get the department name to set in the text file
	 * @see com.kp.cms.transactions.admission.IGensmartCardDataTransaction#getDepartmentName(int)
	 */
	@Override
	public String getDepartmentName(int deptId) throws Exception {
		Session session = null;
		String department=null;
		try {
				session = HibernateUtil.getSession();
				Query getDeptName=session.createQuery("select d.name from Department d where d.id="+deptId );
				department = (String)getDeptName.uniqueResult();
				if(department!=null && !department.isEmpty()){
					return department;
				}
				else return "";
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	@Override
	public Map<String, byte[]> getEmployeePhotos(List<Integer> employeeIds) throws Exception {
		Session session = null;
		Map<String, byte[]> employeePhotos=new HashMap<String, byte[]>();
		List list = null;
		try {
			if(employeeIds!=null && !employeeIds.isEmpty()){
				session = HibernateUtil.getSession();
				String hql="select e.employee.fingerPrintId,e.empPhoto from EmpImages e where (e.empPhoto <> null or trim(e.empPhoto) <> '')" +
						" and e.employee.isActive=1 and e.employee.active=1 and e.employee.fingerPrintId  is not null and e.employee.fingerPrintId!='' and e.employee.isSCDataGenerated=0 and e.employee.id in (:employeeList)" +
						" group by e.employee.id order by e.employee.fingerPrintId ";
				Query selectedEmployeesQuery=session.createQuery(hql).setParameterList("employeeList",employeeIds);
				list = selectedEmployeesQuery.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null && objects[1]!=null)
							employeePhotos.put(objects[0].toString(),(byte[])objects[1]);
					}
				}
			}
			return employeePhotos;
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
	 * updating the isSCDataGenrated as true into database for the empIds passed
	 * @see com.kp.cms.transactions.admission.IGensmartCardDataTransaction#updateEmployee(java.util.List)
	 */
	@Override
	public void updateEmployee(List<Integer> empIdToSetGenerated,String user)
			throws Exception {
		Session session = null;
		Transaction txn=null;
		try {
				session = HibernateUtil.getSession();
				txn=session.beginTransaction();
				if(empIdToSetGenerated!=null && !empIdToSetGenerated.isEmpty()){
					Iterator<Integer> itr=empIdToSetGenerated.iterator();
					while(itr.hasNext()){
						int id= itr.next();
						Employee emp= (Employee)session.get(Employee.class,id );
						emp.setIsSCDataGenerated(true);
						emp.setLastModifiedDate(new Date());
						emp.setModifiedBy(user);
						session.update(emp);
					}
				}
				txn.commit();
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

}
