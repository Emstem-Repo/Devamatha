package com.kp.cms.transactionsimpl.serviceLearning;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.InternalExamGrievanceBo;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningActivityBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryAdminBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.serviceLearning.ProgrammeEntryForm;
import com.kp.cms.forms.serviceLearning.ServiceLearningMarksEntryForm;
import com.kp.cms.transactions.serviceLearning.IServiceLearningMarksEntryTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ServiceLearningMarksEntryTransactionImpl implements IServiceLearningMarksEntryTransaction{
	
	private static final Log log = LogFactory.getLog(ServiceLearningMarksEntryTransactionImpl.class);
	
	public ProgrammeEntryBo getProgrammesById(
			ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws ApplicationException {
		Session session=null;
		String query = null;
		ProgrammeEntryBo programmeEntryBo;
		try{
			session=HibernateUtil.getSession();
			String date = serviceLearningMarksEntryForm.getStartDate();
			Date curDate = CommonUtil.ConvertStringToSQLDate(date);
			   query="from ProgrammeEntryBo a where  '" + curDate + "' between a.startDate and a.endDate and a.id="+Integer.parseInt(serviceLearningMarksEntryForm.getProgramId()) ;
			
			Query que=session.createQuery(query);
			
			programmeEntryBo = (ProgrammeEntryBo) que.uniqueResult();
 			session.flush();
			//session.close();
			//if(attendanceEntryDay!=null)
				return programmeEntryBo;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public List<ServiceLearningActivityBo> getServiceLearningActivityByProgrameId(
			ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws ApplicationException {
		Session session=null;
		String query = null;
		List<ServiceLearningActivityBo> serviceLearningActivityBo;
		try{
			session=HibernateUtil.getSession();
			   query="from ServiceLearningActivityBo a where  a.programmeEntryBo.id =  "+Integer.parseInt(serviceLearningMarksEntryForm.getProgramId()) ;
			
			Query que=session.createQuery(query);
			
			serviceLearningActivityBo = que.list();
			session.flush();
			//session.close();
			//if(attendanceEntryDay!=null)
				return serviceLearningActivityBo;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public boolean saveServiceLearningActivity(List<ServiceLearningMarksEntryBo> serviceLearningMarksEntryBoList) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isAdded=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			Iterator<ServiceLearningMarksEntryBo> itr = serviceLearningMarksEntryBoList.iterator();
			while(itr.hasNext()){
				ServiceLearningMarksEntryBo serviceLearningMarksEntryBo = itr.next();
				session.saveOrUpdate(serviceLearningMarksEntryBo);
			}
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	
	public boolean saveServiceLearningActivityAdmin(List<ServiceLearningMarksEntryAdminBo> serviceLearningMarksEntryBoList) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isAdded=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			Iterator<ServiceLearningMarksEntryAdminBo> itr = serviceLearningMarksEntryBoList.iterator();
			while(itr.hasNext()){
				ServiceLearningMarksEntryAdminBo serviceLearningMarksEntryBo = itr.next();
				session.saveOrUpdate(serviceLearningMarksEntryBo);
			}
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	
	public List getOverallMarkByStudentId(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception {
		Session session = null;
		List overallMark = null;
		try {
			session = HibernateUtil.getSession();
			String queryString = " select sm.id,sm.student.id,sum(sm.mark),sm.student.admAppln.personalData.firstName,sm.student.registerNo,sm.student.classSchemewise.classes.course.name "+
                                 " from ServiceLearningMarksEntryBo sm where(sm.student.id in (select s.id from Student s where s.classSchemewise.classes.course.id="+Integer.parseInt(serviceLearningMarksEntryForm.getCourseId())+" and "+
                                 " s.classSchemewise.classes.course.program.programType.id="+Integer.parseInt(serviceLearningMarksEntryForm.getProgramTypeId())+" and s.admAppln.appliedYear="+serviceLearningMarksEntryForm.getYear()+"))"+
                                 " group by sm.student.id "; 
			Query query = session.createQuery(queryString);
			overallMark =  query.list();
			session.flush();
			return overallMark;
		} catch (Exception e) {
			log.error("Error in getTermNumber..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	public List<Student> getOverallMark(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception {
		Session session = null;
		List<Student> student = null;
		try {
			session = HibernateUtil.getSession();
			String queryString = " select s from Student s where " +
					             " s.isAdmitted=1 and s.isActive = 1 and s.admAppln.isSelected=1 and s.admAppln.isCancelled=0  and s.admAppln.isApproved=1 " +
					             " and s.classSchemewise.classes.course.id="+Integer.parseInt(serviceLearningMarksEntryForm.getCourseId())+" and "+
                                 " s.classSchemewise.classes.course.program.programType.id="+Integer.parseInt(serviceLearningMarksEntryForm.getProgramTypeId())+" and s.admAppln.appliedYear="+serviceLearningMarksEntryForm.getYear()+
                                 " group by s.id order by s.registerNo"; 
			Query query = session.createQuery(queryString);
			student =  query.list();
			session.flush();
			return student;
		} catch (Exception e) {
			log.error("Error in getTermNumber..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public ServiceLearningMarksEntryBo getServiceLearningmark(int studentId,int programId,int deptId) throws ApplicationException {
		Session session=null;
		String query = null;
		ServiceLearningMarksEntryBo serviceLearningMarksEntryBo;
		try{
			session=HibernateUtil.getSession();
			query="from ServiceLearningMarksEntryBo s where s.student.id= "+ studentId +" and s.programmeEntryBo.id= "+ programId +" and s.departmentNameEntry.id=" + deptId ;
			
			Query que=session.createQuery(query);
			
			serviceLearningMarksEntryBo = (ServiceLearningMarksEntryBo) que.uniqueResult();
 			session.flush();
			//session.close();
			//if(attendanceEntryDay!=null)
				return serviceLearningMarksEntryBo;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	public ServiceLearningMarksEntryAdminBo getServiceLearningmarkAdmin(int studentId,int programId,int deptId) throws ApplicationException {
		Session session=null;
		String query = null;
		ServiceLearningMarksEntryAdminBo serviceLearningMarksEntryBo;
		try{
			session=HibernateUtil.getSession();
			query="from ServiceLearningMarksEntryAdminBo s where s.student.id= "+ studentId +" and s.programmeEntryBo.id= "+ programId +" and s.departmentNameEntry.id=" + deptId ;
			
			Query que=session.createQuery(query);
			
			serviceLearningMarksEntryBo = (ServiceLearningMarksEntryAdminBo) que.uniqueResult();
 			session.flush();
			//session.close();
			//if(attendanceEntryDay!=null)
				return serviceLearningMarksEntryBo;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public List<ServiceLearningMarksEntryBo> getStudentDetailsById(int stdId) throws Exception {
		Session session = null;
		List<ServiceLearningMarksEntryBo>  serviceLearningMarksEntryBo= null;
		try {
			session = HibernateUtil.getSession();
			String queryString = " from ServiceLearningMarksEntryBo s where s.student.id= " +stdId; 
			Query query = session.createQuery(queryString);
			serviceLearningMarksEntryBo =  query.list();
			session.flush();
			return serviceLearningMarksEntryBo;
		} catch (Exception e) {
			log.error("Error in getTermNumber..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public ServiceLearningActivityBo getServiceLearningActivity(int programId,int deptId,int studentId) throws ApplicationException {
		Session session=null;
		String query = null;
		ServiceLearningActivityBo serviceLearningActivityBo;
		try{
			session=HibernateUtil.getSession();
			query="from ServiceLearningActivityBo s where s.student.id= "+ studentId +" and s.programmeEntryBo.id= "+ programId +" and s.departmentNameEntry.id=" + deptId ;
			
			Query que=session.createQuery(query);
			
			serviceLearningActivityBo = (ServiceLearningActivityBo) que.uniqueResult();
 			session.flush();
			//session.close();
			//if(attendanceEntryDay!=null)
				return serviceLearningActivityBo;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public List<Integer> getStudentIds() throws Exception {
		Session session = null;
		List<Integer> stdids =null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "select s.student.id from ServiceLearningMarksEntryBo s where s.mark is not null"; 
			Query query = session.createQuery(queryString);
			stdids =  query.list();
			session.flush();
			return stdids;
		} catch (Exception e) {
			log.error("Error in getTermNumber..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	public List<Integer> getStudentIdsAdmin() throws Exception {
		Session session = null;
		List<Integer> stdids =null;
		try {
			
			session = HibernateUtil.getSession();
			String queryString = "select s.student.id from ServiceLearningMarksEntryAdminBo s where s.mark is not null"; 
			Query query = session.createQuery(queryString);
			stdids =  query.list();
			session.flush();
			return stdids;
		} catch (Exception e) {
			log.error("Error in getTermNumber..." ,e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	
	public boolean deleteRecord(int id) throws ApplicationException {
		Session session=null;
		Transaction tx = null;
		ServiceLearningMarksEntryBo serviceLearningMarksEntryBo;
		try{
			session=HibernateUtil.getSession();
			 tx= session.beginTransaction();
			Query query = session.createQuery("delete from ServiceLearningMarksEntryBo s where s.id= "+id);
			
			query.executeUpdate();
			tx.commit();
			session.flush();
			//session.close();
			return true;
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	@Override
	public ServiceLearningMarksEntryBo getServiceLearningmarkEntry(
			int studentId, int programId) throws ApplicationException {
		Session session=null;
		String query = null;
		ServiceLearningMarksEntryBo serviceLearningMarksEntryBo;
		try{
			session=HibernateUtil.getSession();
			query="from ServiceLearningMarksEntryBo s where s.student.id= "+ studentId +" and s.programmeEntryBo.id= "+ programId;;	
			Query que=session.createQuery(query);
			serviceLearningMarksEntryBo = (ServiceLearningMarksEntryBo) que.uniqueResult();	
			return serviceLearningMarksEntryBo;
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public ServiceLearningActivityBo getServiceLearningActivityWithoutDept(
			int programId, int studentId)throws Exception {
		Session session=null;
		String query = null;
		ServiceLearningActivityBo serviceLearningActivityBo;
		try{
			session=HibernateUtil.getSession();
			query="from ServiceLearningActivityBo s where s.student.id= "+ studentId +" and s.programmeEntryBo.id= "+ programId ; 
			
			Query que=session.createQuery(query);
			
			serviceLearningActivityBo = (ServiceLearningActivityBo) que.uniqueResult();
 			session.flush();
			//session.close();
			//if(attendanceEntryDay!=null)
				return serviceLearningActivityBo;
			//else
				//return false;
			   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	@Override
	public ServiceLearningMarksEntryBo getServiceLearningmark(int StudentId,
			int programId)throws Exception {

		Session session =null;
		ServiceLearningMarksEntryBo serviceLearningMarksEntryBo =null;
		try{
			session=HibernateUtil.getSession();
			String str = " from ServiceLearningMarksEntryBo s where s.student.id= "+ StudentId +" and s.programmeEntryBo.id= "+ programId ;
			Query que=session.createQuery(str);
			
			serviceLearningMarksEntryBo = (ServiceLearningMarksEntryBo) que.uniqueResult();
 			session.flush();
			return serviceLearningMarksEntryBo;	   
		}catch (Exception e) {
			log.error("Error during duplcation checking..." ,e);
			throw new ApplicationException(e);
		}
	}
}
