package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeApprover;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.bo.employee.EmpResearchPublicMaster;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.employee.HodApprover;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.EmpAllowanceForm;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.forms.employee.EmpResPubPendApprovalForm;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.transactions.employee.IEmpResPubDetailsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EmpResPubDetailsImpl implements IEmpResPubDetailsTransaction {
	private static final Log log = LogFactory
	.getLog(EmpResPubDetailsImpl.class);

public static volatile EmpResPubDetailsImpl obImpl = null;

public static EmpResPubDetailsImpl getInstance() {
if (obImpl == null) {
	obImpl = new EmpResPubDetailsImpl();
}
return obImpl;
}


public List<EmpResearchPublicDetails> GetResearchDetails(EmpResPubDetailsForm empResPubForm)throws Exception
{	
	List<EmpResearchPublicDetails> list =null;
		Session session=null;
		
		try{
			session=HibernateUtil.getSession();
			String quer="from EmpResearchPublicDetails emp where emp.employeeId='"+empResPubForm.getEmployeeId()+"' and emp.isActive= true and emp.isEmployee =true order by emp.createdDate";
			Query query=session.createQuery(quer);
			list= query.list();
			
		}catch(Exception e){
			log.error("Error while getting EmpResearchPublicDetails" +e);
		}
		return list;
		
	}
public List<EmpResearchPublicDetails> GetResearchDetailsRej(EmpResPubDetailsForm empResPubForm)throws Exception
{	
	List<EmpResearchPublicDetails> list =null;
		Session session=null;
		
		try{
			session=HibernateUtil.getSession();
			String quer="from EmpResearchPublicDetails emp where emp.employeeId='"+empResPubForm.getEmployeeId()+"' and emp.isActive= true and emp.isEmployee =true and emp.isRejected=1 order by emp.createdDate";
			Query query=session.createQuery(quer);
			list= query.list();
			
		}catch(Exception e){
			log.error("Error while getting EmpResearchPublicDetails" +e);
		}
		return list;
		
	}

public List<EmpResearchPublicDetails> GetResearchDetailsAdmin(EmpResPubDetailsForm empResPubForm)throws Exception
{	
	List<EmpResearchPublicDetails> list =null;
		Session session=null;
		String quer=null;
		try{
			session=HibernateUtil.getSession();
			if(empResPubForm.getIsEmployee().equalsIgnoreCase("true")){
				quer="from EmpResearchPublicDetails emp where emp.employeeId='"+empResPubForm.getSelectedEmployeeId()+"' and emp.isActive= true and emp.isEmployee =true order by emp.createdDate";
			}else
			{
				quer="from EmpResearchPublicDetails emp where emp.guestId='"+empResPubForm.getSelectedEmployeeId()+"' and emp.isActive= true and emp.isEmployee =false order by emp.createdDate";
			}
			Query query=session.createQuery(quer);
			list= query.list();
			
		}catch(Exception e){
			log.error("Error while getting EmpResearchPublicDetails" +e);
		}
		return list;
	}

public Employee getEmployeeIdFromUserId(EmpResPubDetailsForm empResPubForm) throws Exception {
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

public HodApprover getApproverIdFromEmpId (int empId)throws Exception{

	Session session=null;
	HodApprover emp=null;
	try {
	session=HibernateUtil.getSession();
		Query query=session.createQuery("from HodApprover e where e.isActive=1 and e.employee.id="+empId);
		emp=(HodApprover) query.uniqueResult();

}catch (Exception exception) {
	
	throw new ApplicationException();
}finally{
	if(session!=null){
		session.flush();
	}
}
	return emp;
}


public String getApproverEmailId (int approverId)throws Exception
{
	
	Session session=null;
	String emailId="";
	
	try{
		session=HibernateUtil.getSession();
		String quer="select d.email from Department d join d.employees e where d.isActive=1 and e.id="+approverId;
		Query query=session.createQuery(quer);
		emailId=query.uniqueResult().toString();
		
	}catch(Exception e){
		log.error("Error while getting Employee id.." +e);
	}
	return emailId;
}

@SuppressWarnings("unchecked")
public Map<String, String> getResPubMasterMap() throws Exception {
	Session session=null;
	Map<String,String> map=new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmpResearchPublicMaster c where c.isActive=true");
		List<EmpResearchPublicMaster> list=query.list();
		if(list!=null){
			Iterator<EmpResearchPublicMaster> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpResearchPublicMaster empResPubMaster =iterator.next();
				if(empResPubMaster.getId() !=0 && empResPubMaster.getName()!=null && !empResPubMaster.getName().isEmpty())
				map.put(String.valueOf(empResPubMaster.getId()),empResPubMaster.getName());
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

public List<EmpResearchPublicMaster> getEmpResPublicList() throws Exception {
	Session session=null;
	List<EmpResearchPublicMaster> resPub=null;
	try{
		session=HibernateUtil.getSession();
		String quer="from EmpResearchPublicMaster al where al.isActive=true";
		Query query=session.createQuery(quer);
		resPub=query.list();
	}catch(Exception e){
		log.debug("Error during saving data...", e);
	}
	return resPub;
}
@Override
public boolean duplicateCheck(String name, HttpSession hSession,
		ActionErrors errors, EmpResPubDetailsForm empResPubForm) {
	Session session=null;
	boolean flag=false;
	try{
		session=HibernateUtil.getSession();
		String quer="from EmpResearchPublicMaster a where a.name=:Name ";
		Query query=session.createQuery(quer);
		query.setString("Name", name);
		
		EmpResearchPublicMaster resPub=(EmpResearchPublicMaster)query.uniqueResult();
		if(resPub!=null && !resPub.toString().isEmpty()){
			int id=Integer.parseInt(empResPubForm.getEmpResPubMasterId());
			if(id!=0){
		      if(resPub.getId()==id){
			    flag=false;
		      }else if(resPub.getIsActive()){
			     flag=true;
		         errors.add("error", new ActionError("knowledgepro.employee.allowance.duplicate"));
		       }
		      else{
				   flag=true;
				   empResPubForm.setId(String.valueOf(resPub.getId()));
				   throw new ReActivateException(resPub.getId());
			   }
			}else if(resPub.getIsActive()){
				flag=true;
		        errors.add("error", new ActionError("knowledgepro.employee.allowance.duplicate"));
			}
			else{
				  flag=true;
				  empResPubForm.setId(String.valueOf(resPub.getId()));
				  throw new ReActivateException(resPub.getId());
			 } 
		}else
			flag=false;
			
	}catch(Exception e){
		flag=true;
		log.debug("Reactivate Exception", e);
		if(e instanceof ReActivateException){
			errors.add("error", new ActionError(CMSConstants.EMP_ALLOWANCE_TYPE));
			//saveErrors(request, errors);
			hSession.setAttribute("ReactivateId", empResPubForm.getId());
		}else{
			errors.add("error", new ActionError("knowledgepro.employee.allowance.fail"));
		}
	}
	return flag;
}
@Override
public boolean addResPubMaster(EmpResearchPublicMaster resPub) throws Exception {
	Session session=null;
	Transaction transaction=null;
	try{
		session=HibernateUtil.getSession();
		transaction=session.beginTransaction();
		transaction.begin();
		session.save(resPub);
		transaction.commit();
	}catch(Exception exception){
		if (transaction != null)
			transaction.rollback();
		log.debug("Error during saving data...", exception);
	}
	finally{
		session.flush();
		session.close();
	}
	return true;
}
@Override
public EmpResearchPublicMaster getResPubMasterById(int id) throws Exception {
	Session session=null;
	EmpResearchPublicMaster resPub=null;
	try{
		session=HibernateUtil.getSession();
		String str="from EmpAllowance a where a.id="+id;
		Query query=session.createQuery(str);
		resPub=(EmpResearchPublicMaster)query.uniqueResult();
		
	}catch(Exception e){
		log.error("Error during getting allowance by id..." , e);
		session.flush();
		session.close();
		
	}
	return resPub;
}
@Override
public boolean updateResPubMaster(EmpResearchPublicMaster resPub) throws Exception {
	Session session=null;
	Transaction transaction=null;
	try{
		session=HibernateUtil.getSession();
		transaction=session.beginTransaction();
		transaction.begin();
		session.merge(resPub);
		transaction.commit();
		session.flush();
		session.close();
	}catch(Exception e){
		if (transaction != null)
			transaction.rollback();
		log.debug("Error during updating Allowance data...", e);
	}
	return true;
}
@Override
public boolean deleteResPubMaster(int id) throws Exception {
	Session session=null;
    Transaction transaction=null;
    try{
  	    session=InitSessionFactory.getInstance().openSession();
  	    String str="from EmpAllowance a where a.id="+id;
  	    EmpResearchPublicMaster resPub=(EmpResearchPublicMaster)session.createQuery(str).uniqueResult();
  	    transaction=session.beginTransaction();
  	    resPub.setIsActive(false);
  	    session.update(resPub);
  	    transaction.commit();
  	    session.close();
      }catch(Exception e){
  	    if (transaction != null)
  		   transaction.rollback();
  	log.debug("Error during deleting allowance data...", e);
  	
     }
   return true;
}
@Override
public boolean reactivateResPubMaster(EmpResPubDetailsForm empResPubForm)
		throws Exception {
	log.info("Entering into EmpAllowanceTxnImpl of reactivateAllowance");
	//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
	Session session=null;
	Transaction transaction=null;
	try{
	   session=HibernateUtil.getSession();
	   transaction=session.beginTransaction();
	   String quer="from EmpResearchPublicMaster a where a.id="+empResPubForm.getEmpResPubMasterId();
	   Query query=session.createQuery(quer);
	   EmpResearchPublicMaster resPub=(EmpResearchPublicMaster)query.uniqueResult();
	   resPub.setIsActive(true);
	   resPub.setModifiedBy(empResPubForm.getUserId());
	   resPub.setLastModifiedDate(new Date());
	   session.update(resPub);
	   transaction.commit();
	}catch(Exception e){
		log.debug("Exception" + e.getMessage());
		return false;
	}finally{
		if(session!=null)
			session.close();
	}
	return true;
}




public StringBuffer getSerchedEmployeeQuery(String FingerPrintId, int departmentId,
		String Name, String Active)
		throws Exception {
		StringBuffer query = new StringBuffer(
				"from Employee e "
				+" where e.isActive = 1 ");
		
		if (FingerPrintId != null && !StringUtils.isEmpty(FingerPrintId.trim())) {
			query = query.append(" and e.fingerPrintId='"
					+ FingerPrintId+"'");
		}
		if (Name != null && !StringUtils.isEmpty(Name.trim())) {
			query = query.append(" and e.firstName like '"
					+ Name+"%'");
		}
		if(Active !=null)
		{
			if (Active.equals("1")) {
				
				query = query.append(" and e.active= 1 ");
			}
			else if (Active.equals("0")) {
				
				query = query.append(" and e.active= 0 ");
			}
		}
		
		if (departmentId > 0) {
				query = query
						.append(" and e.department.id='"
								+ departmentId+"'");
			}
				
		query.append(" order by e.firstName");
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

public StringBuffer getSerchedEmployeeQueryAdmin(String FingerPrintId, int departmentId,
		int designationId, String Name, String Active, int streamId, String tempTeachingStaff,int empTypeId)
		throws Exception {
		StringBuffer query = new StringBuffer(
				"from Employee e "
				+" where e.isActive = 1 ");
		if (FingerPrintId != null && !StringUtils.isEmpty(FingerPrintId.trim())) {
			query = query.append(" and e.fingerPrintId='"
					+ FingerPrintId+"'");
		}
		if (Name != null && !StringUtils.isEmpty(Name.trim())) {
			query = query.append(" and e.firstName like '"
					+ Name+"%'");
		}
		if(Active !=null)
		{
			if (Active.equals("1")) {
				
				query = query.append(" and e.active= 1 ");
			}
			else if (Active.equals("0")) {
				
				query = query.append(" and e.active= 0 ");
			}
		}
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

public List<Employee> getSerchedEmployeeAdmin(StringBuffer query)
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


public List<Designation> getEmployeeDesignation() throws Exception 
{
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
 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getDesignationMap()
 */
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
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	map = (Map<String, String>) CommonUtil.sortMapByValue(map);
	return map;
}

public Map<String, String> getEmpTypeMap() throws Exception {
	Session session=null;
	Map<String,String> map=new LinkedHashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmpType r where r.isActive=true");
		List<EmpType> list=query.list();
		if(list!=null){
			Iterator<EmpType> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpType empType=iterator.next();
				if(empType!=null && empType.getName()!=null && !empType.getName().isEmpty() && empType.getId()>0)
				map.put(String.valueOf(empType.getId()),empType.getName());
			}
		}
	}catch (Exception exception) {
		// TODO: handle exception
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	map = (Map<String, String>) CommonUtil.sortMapByValue(map);
	return map;
}

public Map<String, String> getStreamMap() throws Exception {
	Session session=null;
	Map<String,String> map=new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmployeeStreamBO s where s.isActive=true");
		List<EmployeeStreamBO> list=query.list();
		if(list!=null){
			Iterator<EmployeeStreamBO> iterator=list.iterator();
			while(iterator.hasNext()){
				EmployeeStreamBO stream=iterator.next();
				if(stream!=null && stream.getName()!=null && !stream.getName().isEmpty() && stream.getId()>0)
				map.put(String.valueOf(stream.getId()),stream.getName());
			}
		}
	}catch (Exception exception) {
		// TODO: handle exception
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	map = (Map<String, String>) CommonUtil.sortMapByValue(map);
	return map;
}
public Employee GetEmployee(EmpResPubDetailsForm objform)throws Exception
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

public StringBuffer getSerchedGuestQueryAdmin(String FingerPrintId, int departmentId,
		int designationId, String Name, String Active, int streamId, String tempTeachingStaff,int empTypeId)
		throws Exception {
		StringBuffer query = new StringBuffer(
				"from GuestFaculty e "
				+" where e.isActive = 1 ");
		if (FingerPrintId != null && !StringUtils.isEmpty(FingerPrintId.trim())) {
			query = query.append(" and e.fingerPrintId='"
					+ FingerPrintId+"'");
		}
		if (Name != null && !StringUtils.isEmpty(Name.trim())) {
			query = query.append(" and e.firstName like '"
					+ Name+"%'");
		}
		if(Active !=null)
		{
			if (Active.equals("1")) {
				
				query = query.append(" and e.active= 1 ");
			}
			else if (Active.equals("0")) {
				
				query = query.append(" and e.active= 0 ");
			}
		}
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

public List<GuestFaculty> getSerchedGuestAdmin(StringBuffer query)
		throws Exception {
	Session session = null;
	List<GuestFaculty> empList;

	try {
		session = HibernateUtil.getSession();
		Query queri = session.createQuery(query.toString());
		empList = queri.list();
		
	} catch (Exception e) {
		throw new ApplicationException(e);
	}
	return empList;
}

public GuestFaculty GetGuest(EmpResPubDetailsForm objform)throws Exception
{
		GuestFaculty list =null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from GuestFaculty emp where emp.id='"+objform.getSelectedEmployeeId()+"' and emp.isActive= true ";
			Query query=session.createQuery(quer);
			list= (GuestFaculty) query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error while getting GetEmployee" +e);
		}
		return list;
		
	}

}
