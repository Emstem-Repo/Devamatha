package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpOnlineResumeUsers;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.DownloadApplicationForm;
import com.kp.cms.transactions.employee.IDownloadApplicationTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class DownloadApplicationTxnImpl implements IDownloadApplicationTransaction{
	private static final Log log = LogFactory.getLog(DownloadApplicationTxnImpl.class);
	public static volatile DownloadApplicationTxnImpl txnImpl = null;
	public static DownloadApplicationTxnImpl getInstance(){
		if(txnImpl == null){
			txnImpl = new DownloadApplicationTxnImpl();
			return txnImpl;
		}
		return txnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IDownloadApplicationTransaction#getDepartmentList()
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IDownloadApplicationTransaction#getEmpQualificationList()
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
	 * @see com.kp.cms.transactions.employee.IDownloadApplicationTransaction#getEmployeeDetails(com.kp.cms.forms.employee.DownloadApplicationForm)
	 */
	@Override
	public List<EmpOnlineResumeUsers> getEmployeeDetails( DownloadApplicationForm downloadApplicationForm) throws Exception {
		Session session = null;
		List<EmpOnlineResumeUsers> onlineResumeUsers;
		try{
			session = HibernateUtil.getSession();
			String query = " from EmpOnlineResumeUsers empOnlineUsers where empOnlineUsers.isActive=1 " +
											" and empOnlineUsers.users.id="+Integer.parseInt(downloadApplicationForm.getUserId());
			if(downloadApplicationForm.getIsCjc()){
				if(downloadApplicationForm.getEmpSubjectId()!=null && !downloadApplicationForm.getEmpSubjectId().isEmpty())
					query  = query + " and empOnlineUsers.onlineResume.empSubject.id= '"+Integer.parseInt(downloadApplicationForm.getEmpSubjectId())+"'";
			}
			else{
				if(downloadApplicationForm.getDepartmentId()!=null && !downloadApplicationForm.getDepartmentId().isEmpty()){
					query  = query + " and empOnlineUsers.onlineResume.department.id= '"+Integer.parseInt(downloadApplicationForm.getDepartmentId())+"'";
				}
			}
			if(downloadApplicationForm.getDesignationId()!=null && !downloadApplicationForm.getDesignationId().isEmpty()){
				query  = query + " and empOnlineUsers.onlineResume.postAppliedDesig= '"+downloadApplicationForm.getDesignationId()+"'";
			}
			if(downloadApplicationForm.getQualificationId() != null && !downloadApplicationForm.getQualificationId().isEmpty()){
				query = query + " and empOnlineUsers.onlineResume.empQualificationLevel.id = "+downloadApplicationForm.getQualificationId();
			}
			if(downloadApplicationForm.getStartDate() != null && !downloadApplicationForm.getStartDate().isEmpty() && downloadApplicationForm.getEndDate() != null && !downloadApplicationForm.getEndDate().isEmpty()){
				Date startDate = CommonUtil.ConvertStringToSQLDate(downloadApplicationForm.getStartDate());
				Date endDate = CommonUtil.ConvertStringToSQLDate(downloadApplicationForm.getEndDate());
				query = query + " and empOnlineUsers.onlineResume.dateOfSubmission between '"+startDate+"' and '"+ endDate+"'";
			}
			if(downloadApplicationForm.getName() != null && !downloadApplicationForm.getName().isEmpty()){
				query = query + " and empOnlineUsers.onlineResume.name like '"+downloadApplicationForm.getName()+"%'" ;
			}
			if(downloadApplicationForm.getApplnNo() != null && !downloadApplicationForm.getApplnNo().isEmpty()){
				query = query + " and empOnlineUsers.onlineResume.applicationNo = '"+downloadApplicationForm.getApplnNo()+"'" ;
			}
			if(downloadApplicationForm.getStatus()!= null && !downloadApplicationForm.getStatus().isEmpty()){
				query = query + " and empOnlineUsers.onlineResume.status = '"+downloadApplicationForm.getStatus()+"'";
			}
			Query hqlQuery=session.createQuery(query);
			onlineResumeUsers = hqlQuery.list();
		}catch (Exception e) {
			log.error("Error while retrieving EmpQualificationLevel Details.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return onlineResumeUsers;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IDownloadApplicationTransaction#getDetailsForEmployee(java.lang.String)
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
	 * @see com.kp.cms.transactions.employee.IDownloadApplicationTransaction#getEmployeeEducationDetails(java.lang.String)
	 */
	@Override
	public List<EmpOnlineEducationalDetails> getEmployeeEducationDetails( String empId) throws Exception {
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
	public List<EmpOnlinePreviousExperience> getEmployeeExperienceDetails( String empId) throws Exception {
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
}
