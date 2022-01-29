package com.kp.cms.transactionsimpl.employee;

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
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpOnlineResumeUsers;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.DownloadEmployeeResumeForm;
import com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class DownloadEmployeeResumeTransactionImpl implements IDownloadEmployeeResumeTransaction {
	/**
	 * Singleton object of DownloadEmployeeResumeTransactionImpl
	 */
	private static volatile DownloadEmployeeResumeTransactionImpl downloadEmployeeResumeTransactionImpl = null;
	private static final Log log = LogFactory.getLog(DownloadEmployeeResumeTransactionImpl.class);
	private DownloadEmployeeResumeTransactionImpl() {
		
	}
	/**
	 * return singleton object of DownloadEmployeeResumeTransactionImpl.
	 * @return
	 */
	public static DownloadEmployeeResumeTransactionImpl getInstance() {
		if (downloadEmployeeResumeTransactionImpl == null) {
			downloadEmployeeResumeTransactionImpl = new DownloadEmployeeResumeTransactionImpl();
		}
		return downloadEmployeeResumeTransactionImpl;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getDepartmentList()
	 */
	@Override
	public List<Department> getDepartmentList() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from Department dept where dept.isActive=1 order by dept.name";
			Query hqlQuery=session.createQuery(query);
			List<Department> departmentList = hqlQuery.list();
			return  departmentList;
		} catch (Exception e) {
			log.error("Error while retrieving Department Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	
	}
	
	public List<Department> getDepartmentList1() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="select dep from Department dep join dep.employees e where e.teachingStaff=true and dep.isActive =true group by dep.name order by dep.name";
			Query hqlQuery=session.createQuery(query);
			List<Department> departmentList = hqlQuery.list();
			return  departmentList;
		} catch (Exception e) {
			log.error("Error while retrieving Department Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getDesignationList()
	 */
	@Override
	public List<Designation> getDesignationList() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from Designation des where des.isActive=1";
			Query hqlQuery=session.createQuery(query);
			List<Designation> designationList = hqlQuery.list();
			return  designationList;
		} catch (Exception e) {
			log.error("Error while retrieving Designation Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getEmpQualificationList()
	 */
	@Override
	public List<EmpQualificationLevel> getEmpQualificationList() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpQualificationLevel emp where emp.isActive=1";
			Query hqlQuery=session.createQuery(query);
			List<EmpQualificationLevel> qualificationList = hqlQuery.list();
			return  qualificationList;
		} catch (Exception e) {
			log.error("Error while retrieving EmpQualificationLevel Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getEmployeeDetails()
	 */
	@Override
	public List<EmpOnlineResume> getEmployeeDetails(DownloadEmployeeResumeForm downloadEmployeeResumeForm) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpOnlineResume emp where emp.isActive=1";
			if(downloadEmployeeResumeForm.getIsCjc()){
				if(downloadEmployeeResumeForm.getEmpSubjectId()!=null && !downloadEmployeeResumeForm.getEmpSubjectId().isEmpty())
					query = query + " and emp.empSubject.id = "+downloadEmployeeResumeForm.getEmpSubjectId();
			}else{
				if(downloadEmployeeResumeForm.getDepartmentId() != null && !downloadEmployeeResumeForm.getDepartmentId().isEmpty()){
					query = query + " and emp.department.id = "+downloadEmployeeResumeForm.getDepartmentId();
				}
			}
			if(downloadEmployeeResumeForm.getDesignationId() != null && !downloadEmployeeResumeForm.getDesignationId().isEmpty()){
				query = query + " and emp.postAppliedDesig = '"+downloadEmployeeResumeForm.getDesignationId()+"'";
			}
			if(downloadEmployeeResumeForm.getQualificationId() != null && !downloadEmployeeResumeForm.getQualificationId().isEmpty()){
				query = query + " and emp.empQualificationLevel.id = "+downloadEmployeeResumeForm.getQualificationId();
			}
			if(downloadEmployeeResumeForm.getStartDate() != null && !downloadEmployeeResumeForm.getStartDate().isEmpty() && downloadEmployeeResumeForm.getEndDate() != null && !downloadEmployeeResumeForm.getEndDate().isEmpty()){
				Date startDate = CommonUtil.ConvertStringToSQLDate(downloadEmployeeResumeForm.getStartDate());
				Date endDate = CommonUtil.ConvertStringToSQLDate(downloadEmployeeResumeForm.getEndDate());
				query = query + " and emp.dateOfSubmission between '"+startDate+"' and '"+ endDate+"'";
			}
			if(downloadEmployeeResumeForm.getName() != null && !downloadEmployeeResumeForm.getName().isEmpty()){
				query = query + " and emp.name like '"+downloadEmployeeResumeForm.getName()+"%'" ;
			}
			if(downloadEmployeeResumeForm.getApplnNo() != null && !downloadEmployeeResumeForm.getApplnNo().isEmpty()){
				query = query + " and emp.applicationNo = '"+downloadEmployeeResumeForm.getApplnNo()+"'" ;
			}
			if(downloadEmployeeResumeForm.getStatus()!= null && !downloadEmployeeResumeForm.getStatus().isEmpty()){
				query = query + " and emp.status = '"+downloadEmployeeResumeForm.getStatus()+"'";
			}
			Query hqlQuery=session.createQuery(query);
			List<EmpOnlineResume> empOnlineResumes = hqlQuery.list();
			return  empOnlineResumes;
		} catch (Exception e) {
			log.error("Error while retrieving EmpQualificationLevel Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getDetailsForEmployee()
	 */
	@Override
	public EmpOnlineResume getDetailsForEmployee(String empId) throws Exception {
		
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpOnlineResume emp where emp.isActive=1 and emp.id = '"+empId+"'";
			Query hqlQuery=session.createQuery(query);
			EmpOnlineResume empOnlineResumes = (EmpOnlineResume)hqlQuery.uniqueResult();
			return  empOnlineResumes;
		} catch (Exception e) {
			log.error("Error while retrieving EmpQualificationLevel Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getEmployeeEducationDetails(java.lang.String)
	 */
	@Override
	public List<EmpOnlineEducationalDetails> getEmployeeEducationDetails(String empId)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpOnlineEducationalDetails emp where emp.empOnlineResume.id = '"+empId+"' order by emp.empQualificationLevel.displayOrder";
			Query hqlQuery=session.createQuery(query);
			List<EmpOnlineEducationalDetails> empOnlineEducationalDetails = hqlQuery.list();
			return  empOnlineEducationalDetails;
		} catch (Exception e) {
			log.error("Error while retrieving EmpQualificationLevel Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public List<EmpOnlinePreviousExperience> getEmployeeExperienceDetails(
			String empId) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String query="from EmpOnlinePreviousExperience emp where emp.empOnlineResume.id = '"+empId+"'";
			Query hqlQuery=session.createQuery(query);
			List<EmpOnlinePreviousExperience> empOnlineEducationalDetails = hqlQuery.list();
			return  empOnlineEducationalDetails;
		} catch (Exception e) {
			log.error("Error while retrieving EmpOnlinePreviousExperience Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getEmpDetailsByAppNo(int)
	 */
	@Override
	public EmpOnlineResume getEmpDetailsByAppNo(int appNo) throws Exception {
		Session session=null;
		EmpOnlineResume empOnlineResume;
		try{
			session = HibernateUtil.getSession();
			String quer = "from EmpOnlineResume emp where emp.applicationNo="+appNo;
			Query query =session.createQuery(quer);
			empOnlineResume = (EmpOnlineResume)query.uniqueResult();
		}catch(Exception e){
			log.error("Error while retrieving EmpOnlineResume Details.." +e);
			throw  new ApplicationException(e);
		}
		return empOnlineResume;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#saveStatus(com.kp.cms.forms.employee.DownloadEmployeeResumeForm)
	 */
	@Override
	public boolean saveStatus(
			DownloadEmployeeResumeForm downloadEmployeeResumeForm)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=false;
		try{
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			String quer="from EmpOnlineResume emp where emp.applicationNo="+downloadEmployeeResumeForm.getApplicationNo();
			Query query=session.createQuery(quer);
			EmpOnlineResume onlineResume = (EmpOnlineResume)query.uniqueResult();
			onlineResume.setStatus(downloadEmployeeResumeForm.getStatus());
			if(downloadEmployeeResumeForm.getStatusDate()!=null && !downloadEmployeeResumeForm.getStatusDate().isEmpty()){
				 Date statusDate=CommonUtil.ConvertStringToDate(downloadEmployeeResumeForm.getStatusDate());
			     onlineResume.setStatusDate(statusDate);
			}
			session.update(onlineResume);
			transaction.commit();
			flag = true;
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			flag = false;
			log.debug("Error during saving data...", e);
		}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#setStatus(int)
	 */
	@Override
	public void setStatus(int appNo) throws Exception {
		Session session=null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			String quer = "from EmpOnlineResume emp where emp.applicationNo="+appNo;
			Query query =session.createQuery(quer);
			EmpOnlineResume empOnlineResume = (EmpOnlineResume)query.uniqueResult();
			empOnlineResume.setStatus("Forwarded");
			empOnlineResume.setStatusDate(new Date());
			session.update(empOnlineResume);
			transaction.commit();
			
		}catch(Exception e){
			transaction.rollback();
			log.error("Error while setting Status.." +e);
			throw  new ApplicationException(e);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getUsersList()
	 */
	@Override
	public List<Object[]> getUsersList() throws Exception {
		Session session =null;
		Transaction transaction = null;
		List<Object[]> usersList =null;
		try{
			session = HibernateUtil.getSession();
			transaction= session.beginTransaction();
			transaction.begin();
			String str= "select u.id,u from Users u left join u.employee e  with (e.active=1 and e.isActive=1) " +
						 " where  u.isActive=1 and u.active=1  and u.userName is not null";
			Query query = session.createQuery(str);
			usersList= query.list();
		}catch(Exception e){
			transaction.rollback();
			log.error("Error while setting Status.." +e);
			throw  new ApplicationException(e);
		}
		return usersList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#saveEmpOnlineResumeUsersDetails(java.lang.String[], int)
	 */
	@Override
	public boolean saveEmpOnlineResumeUsersDetails(String[] userIds, int empOnlineResumeId,DownloadEmployeeResumeForm downloadEmployeeResumeForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isSave = false;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(userIds!=null && !userIds.toString().isEmpty()){
				for(int i=0;i<userIds.length;i++){
					int userId=Integer.parseInt(userIds[i]);
					EmpOnlineResumeUsers onlineResumeUsers = new EmpOnlineResumeUsers();
					EmpOnlineResume resume = new EmpOnlineResume();
					Users users = new Users();
					resume.setId(empOnlineResumeId);
					users.setId(userId);
					onlineResumeUsers.setOnlineResume(resume);
					onlineResumeUsers.setUsers(users);
					onlineResumeUsers.setCreatedBy(downloadEmployeeResumeForm.getUserId());
					onlineResumeUsers.setCreatedDate(new Date());
					onlineResumeUsers.setModifiedBy(downloadEmployeeResumeForm.getUserId());
					onlineResumeUsers.setLastModifiedDate(new Date());
					onlineResumeUsers.setIsActive(true);
					session.save(onlineResumeUsers);
				}
				transaction.commit();
				isSave = true;
			}
		}catch(Exception e){
			transaction.rollback();
			log.error("Error while saving.." +e);
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isSave;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction#getEmployeeForwardedDetails(com.kp.cms.forms.employee.DownloadEmployeeResumeForm)
	 */
	@Override
	public List<EmpOnlineResumeUsers> getEmployeeForwardedDetails( DownloadEmployeeResumeForm downloadEmployeeResumeForm)
			throws Exception {
		Session session  = null;
		List<EmpOnlineResumeUsers>  list = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = " from EmpOnlineResumeUsers empOnlineUsers where empOnlineUsers.isActive=1 " +
							" and empOnlineUsers.onlineResume.status ='Forwarded'" ;
							if(downloadEmployeeResumeForm.getDepartmentId()!=null && !downloadEmployeeResumeForm.getDepartmentId().isEmpty()){
								hqlQuery  = hqlQuery + " and empOnlineUsers.onlineResume.department.id= '"+downloadEmployeeResumeForm.getDepartmentId()+"'";
							}
							if(downloadEmployeeResumeForm.getDesignationId()!=null && !downloadEmployeeResumeForm.getDesignationId().isEmpty()){
								hqlQuery  = hqlQuery + " and empOnlineUsers.onlineResume.postAppliedDesig= '"+downloadEmployeeResumeForm.getDesignationId()+"'";
							}
							if(downloadEmployeeResumeForm.getQualificationId() != null && !downloadEmployeeResumeForm.getQualificationId().isEmpty()){
								hqlQuery = hqlQuery + " and empOnlineUsers.onlineResume.empQualificationLevel.id = "+downloadEmployeeResumeForm.getQualificationId();
							}
							if(downloadEmployeeResumeForm.getStartDate() != null && !downloadEmployeeResumeForm.getStartDate().isEmpty() && downloadEmployeeResumeForm.getEndDate() != null && !downloadEmployeeResumeForm.getEndDate().isEmpty()){
							Date startDate = CommonUtil.ConvertStringToSQLDate(downloadEmployeeResumeForm.getStartDate());
							Date endDate = CommonUtil.ConvertStringToSQLDate(downloadEmployeeResumeForm.getEndDate());
							hqlQuery = hqlQuery + " and empOnlineUsers.onlineResume.dateOfSubmission between '"+startDate+"' and '"+ endDate+"'";
							}
							if(downloadEmployeeResumeForm.getName() != null && !downloadEmployeeResumeForm.getName().isEmpty()){
								hqlQuery = hqlQuery + " and empOnlineUsers.onlineResume.name like '"+downloadEmployeeResumeForm.getName()+"%'" ;
							}
							if(downloadEmployeeResumeForm.getApplnNo() != null && !downloadEmployeeResumeForm.getApplnNo().isEmpty()){
								hqlQuery = hqlQuery + " and empOnlineUsers.onlineResume.applicationNo = '"+downloadEmployeeResumeForm.getApplnNo()+"'" ;
							}
							/*if(downloadEmployeeResumeForm.getStatus()!= null && !downloadEmployeeResumeForm.getStatus().isEmpty()){
							query = query + " and empOnlineUsers.onlineResume.status = '"+downloadEmployeeResumeForm.getStatus()+"'";
							}*/
			Query query = session.createQuery(hqlQuery);
			list = query.list();
		}catch(Exception e){
			log.error("Error while setting Status.." +e);
			throw  new ApplicationException(e);
		}
		return list;
	}
	@Override
	public Map<Integer,String> getEmployeeSubjects() throws Exception {
		Session session = null;
		Map<Integer,String> empSubjectsMap = new HashMap<Integer,String>();
		try {
			session = HibernateUtil.getSession();
			String query="from EmployeeSubject emp where emp.isActive=1";
			Query hqlQuery=session.createQuery(query);
			List<EmployeeSubject> empSubjects = hqlQuery.list();
			Iterator<EmployeeSubject> itr = empSubjects.iterator();
			while(itr.hasNext()){
				EmployeeSubject empSub = itr.next();
				empSubjectsMap.put(empSub.getId(), empSub.getSubjectName());
			}
			return  empSubjectsMap;
		} catch (Exception e) {
			log.error("Error while retrieving getEmployeeSubjects Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	
}
