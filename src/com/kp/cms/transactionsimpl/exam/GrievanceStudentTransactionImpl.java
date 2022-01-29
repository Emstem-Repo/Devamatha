package com.kp.cms.transactionsimpl.exam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.bo.exam.GrievanceStudentBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.GrievanceStudentForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.exam.GrievanceStudentTo;
import com.kp.cms.transactions.IGrievanceStudentTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;


public class GrievanceStudentTransactionImpl implements IGrievanceStudentTransaction{
	private static GrievanceStudentTransactionImpl grievanceStudentTransactionImpl = null;
	private static final Log log = LogFactory.getLog(GrievanceStudentTransactionImpl.class);
	private GrievanceStudentTransactionImpl() {
		
	}
	/**
	 * return singleton object of DDStatusTransactionImpl.
	 * @return
	 */
	public static GrievanceStudentTransactionImpl getInstance() {
		if (grievanceStudentTransactionImpl == null) {
			grievanceStudentTransactionImpl = new GrievanceStudentTransactionImpl();
		}
		return grievanceStudentTransactionImpl;
	}
	
	public List<GrievanceRemarkBo>  getStudents(GrievanceStudentForm grievanceStudentForm) throws Exception  {
		Session session=null;
		 List<GrievanceRemarkBo> grievanceRemarkBoList=null;
		try{
			session= HibernateUtil.getSession();
			String str="";
			if(grievanceStudentForm.getDate()==null){
				String[] a= grievanceStudentForm.getClassId().split("_");
				
				String semNo = a[1].toString();
			 str=" select gr from GrievanceRemarkBo gr  where  gr.examDefinition.id="+Integer.parseInt(grievanceStudentForm.getExamId())+" and gr.semNo="+semNo;
	       }else{
	    	   Date curDate=CommonUtil.ConvertStringToSQLDate(grievanceStudentForm.getDate());
	    	   str="from GrievanceRemarkBo gr  where  gr.createdDate ='" +curDate+ "'";
	       }
	       if(grievanceStudentForm.getRoleId().equalsIgnoreCase("51")){
	        	   str = str+" and gr.isHodPending=1  group by gr.grievanceSerialNo";
           }else{
        	   str = str+" and gr.isControlerPending=1 group by gr.grievanceSerialNo";
           }
			Query query = session.createQuery(str);
			grievanceRemarkBoList=  query.list();
			
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return grievanceRemarkBoList;
	}
	
	public Student getstudentById(Integer studentId) throws Exception {
		Session session = null;
		Student student;
		try {
			 session = HibernateUtil.getSession();
			 String str = "from Student student where student.id="+studentId;
			 student=(Student) session.createQuery(str).uniqueResult();
				  
			return student;
		 } catch (Exception e) { 
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	public List<GrievanceRemarkBo>  getGrievanceStudentdetails(GrievanceStudentForm grievanceStudentForm) throws Exception  {
		Session session=null;
		 List<GrievanceRemarkBo> grievanceRemarkBoList=null;
		try{
			session= HibernateUtil.getSession();
			
			String str=" select gr from GrievanceRemarkBo gr  where  gr.grievanceSerialNo='"+grievanceStudentForm.getGrievanceNo()+"'";
			if(grievanceStudentForm.getRoleId().equalsIgnoreCase("51")){
	        	   str = str+" and gr.isHodPending=1  ";
	           }else{
	        	   str = str+" and gr.isControlerPending=1 and gr.isHodPending!=1";
	           }
			Query query = session.createQuery(str);
			grievanceRemarkBoList=  query.list();
			
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return grievanceRemarkBoList;
	}
	
	public boolean updateGrievanceBo(List<GrievanceStudentTo> studentList,Integer roleId) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isUpdated=false;
		GrievanceRemarkBo grievanceRemarkBo;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			Iterator<GrievanceStudentTo> itr = studentList.iterator();
			while(itr.hasNext()){
				GrievanceStudentTo grievanceStudentTo = itr.next();
				if(grievanceStudentTo.getGrievanceRemark()!=null){
					
					grievanceRemarkBo =(GrievanceRemarkBo) session.get(GrievanceRemarkBo.class,grievanceStudentTo.getId() );
					if(roleId!=0 && roleId==51){//51 role id for checking wether the user is hod or not
						if(!grievanceStudentTo.getGrievanceRemark().trim().equalsIgnoreCase("")){
						grievanceRemarkBo.setHodRemark(grievanceStudentTo.getGrievanceRemark());
						}else{
							grievanceRemarkBo.setHodRemark(null);
						}
					if(grievanceStudentTo.getApproveCheck()!=null && grievanceStudentTo.getApproveCheck().equalsIgnoreCase("on")){
						grievanceRemarkBo.setIsHodApproved(true);
						grievanceRemarkBo.setIsHodPending(false);
					}
					if(grievanceStudentTo.getRejectCheck()!=null && grievanceStudentTo.getRejectCheck().equalsIgnoreCase("on")){
						grievanceRemarkBo.setIsHodRejected(true);
						grievanceRemarkBo.setIsHodPending(false);
					}
					}else{
						if(!grievanceStudentTo.getGrievanceRemark().trim().equalsIgnoreCase("")){
						grievanceRemarkBo.setControlerRemark(grievanceStudentTo.getGrievanceRemark());
						}else{
							grievanceRemarkBo.setControlerRemark(null);
						}
						if(grievanceStudentTo.getApproveCheck()!=null && grievanceStudentTo.getApproveCheck().equalsIgnoreCase("on")){
							grievanceRemarkBo.setIsControlerApproved(true);
							grievanceRemarkBo.setIsControlerPending(false);
						}
						if(grievanceStudentTo.getRejectCheck()!=null && grievanceStudentTo.getRejectCheck().equalsIgnoreCase("on")){
							grievanceRemarkBo.setIsControlerRejected(true);
							grievanceRemarkBo.setIsControlerPending(false);
						}
						
					}
					session.update(grievanceRemarkBo);
				}
			}
			tx.commit();
			session.flush();
			session.close();
			isUpdated = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isUpdated;
	}
	
	public boolean saveRemarks(List<GrievanceStudentBo> grievanceStudentBoList) throws Exception {
		Session session=null;
		Transaction tx =null;
		boolean isSaved=false;
		GrievanceRemarkBo grievanceRemarkBo;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			Iterator<GrievanceStudentBo> itr = grievanceStudentBoList.iterator();
			while(itr.hasNext()){
				GrievanceStudentBo grievanceStudentBo = itr.next();
				if(grievanceStudentBo.getRemark()!=null){
					session.save(grievanceStudentBo);
				}
			}
			tx.commit();
			session.flush();
			session.close();
			isSaved = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isSaved;
	}
	
	public Integer getRoleIdByUserId(GrievanceStudentForm grievanceStudentForm) throws Exception {
		Session session = null;
		Integer roleId=0;
		try {
			 session = HibernateUtil.getSession();
			 String str="select u.roles.id from Users u where u.id="+Integer.parseInt(grievanceStudentForm.getUserId());
				Query query = session.createQuery(str);
				roleId=(Integer) query.uniqueResult();
			if(roleId!=null)	  
			return roleId;
		 } catch (Exception e) { 
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return null;
	}
	public List<GrievanceRemarkBo>  getStudentDetails(LoginForm loginForm) throws Exception  {
		Session session=null;
		 List<GrievanceRemarkBo> grievanceRemarkBoList=null;
		try{
			session= HibernateUtil.getSession();
			
			String str=" select gr from GrievanceRemarkBo gr  where gr.student.id="+loginForm.getStudentId();
           
			Query query = session.createQuery(str);
			grievanceRemarkBoList=  query.list();
			
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return grievanceRemarkBoList;
	}

}
