package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.List;
import com.kp.cms.bo.admin.Subject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelStudentEvaluationTransaction;
import com.kp.cms.utilities.HibernateUtil;

 public class HostelStudentEvaluationTransactionImpl implements IHostelStudentEvaluationTransaction{
	 
	 private static Log log=LogFactory.getLog( HostelStudentEvaluationTransactionImpl.class);
		
	 public static volatile HostelStudentEvaluationTransactionImpl hostelStudentEvaluationTransactionImpl=null;
	 
    public static HostelStudentEvaluationTransactionImpl getInstance(){
		 
		 if(hostelStudentEvaluationTransactionImpl==null){
			 hostelStudentEvaluationTransactionImpl=new HostelStudentEvaluationTransactionImpl(); 
		 }
		 return hostelStudentEvaluationTransactionImpl;
	 } 
    public List<HlApplicationForm> getHlApplicationForm(int hostelId,Date fromDate,Date toDate,String year)throws Exception{
    	
    	Session session = null;
		List<HlApplicationForm> hlApplicationFormList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("select hlApp from HlApplicationForm hlApp join hlApp.hlRoomTransactions hlRoomtxn where " +
					" hlApp.hlHostelByHlAppliedHostelId.id=:hostelId and hlApp.isActive=1" +
					" and hlRoomtxn.txnDate >= :fromDate and hlRoomtxn.txnDate <= :toDate and hlApp.admAppln.appliedYear=" +year+
					" and hlRoomtxn.hlStatus.statusType=:checkedIn  and hlRoomtxn.isActive=1 group by hlApp.id");
				
			query.setInteger("hostelId", hostelId);  
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setString("checkedIn",CMSConstants.CheckedIn);
			//query.setString("checkedOut",CMSConstants.CheckedOut);
			hlApplicationFormList =query.list();
			return hlApplicationFormList;
		} catch (Exception e) {
			log.error("Error while getHlApplicationForm." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
    	
    }
    
     public List<HlDisciplinaryType> getDisciplinaryTypes()throws Exception{
    	
    	Session session = null;
		List<HlDisciplinaryType> hlDisciplinaryTypeList = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("select hlDisciplinarType from HlDisciplinaryType hlDisciplinarType where hlDisciplinarType.isActive=1");
			hlDisciplinaryTypeList =query.list();
			return hlDisciplinaryTypeList;
		} catch (Exception e) {
			log.error("Error while getDisciplinaryTypes." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
    }
     
    public AttendanceStudent getAttendanceStudent(int studentId) throws Exception{
    	
    	Session session = null;
    	AttendanceStudent attnStud=null;
    	try{
    		session = HibernateUtil.getSession();
    		Query query=session.createQuery("select attnStud from AttendanceStudent attnStud where attnStud.student.id=:studentId and attnStud.isPresent=1");
    		query.setInteger("studentId", studentId);
    		attnStud=(AttendanceStudent)query.uniqueResult();
    		return attnStud;
    	 }
    	 catch (Exception e) {
 			log.error("Error while getAttendanceStudent." +e);
 			throw  new ApplicationException(e);
 		 } finally {
 			if (session != null) {
 				session.flush();
 				//session.close();
 			}
 		}
		
	} 
    public List getAttendanceStudentsAttended(Date fromDate,Date toDate,int academicYear,int studentId) throws Exception{
      
    	Session session = null;
    	List attnStudnts;
    	try{
    		session = HibernateUtil.getSession();
    		Query query=session.createQuery("select count(attnStud.id) from AttendanceStudent attnStud " +
    				      " where  attnStud.isPresent=1 and attnStud.attendance.attendanceDate >= :fromDate and attnStud.attendance.attendanceDate <= :toDate " +
    				      " and attnStud.attendance.isCanceled = 0  and attnStud.student.id= :studentId" +
    				      " and attnStud.student.admAppln.courseBySelectedCourseId.program.academicYear= :academicYear group by attnStud.student.id");
    		query.setInteger("studentId", studentId);
    		query.setDate("fromDate",fromDate);
    		query.setDate("toDate", toDate);
    		query.setInteger("academicYear", academicYear);
    		attnStudnts=query.list();
    		return attnStudnts;
    	 }
    	 catch (Exception e) {
 			log.error("Error while getAttendanceStudentAttended." +e);
 			throw  new ApplicationException(e);
 		 } finally {
 			if (session != null) {
 				session.flush();
 				//session.close();
 			}
 		} 
    }
    
    public List getAttendanceStudents(Date fromDate,Date toDate,int academicYear,int studentId) throws Exception{
        
    	Session session = null;
    	List attnStudnts;
    	try{
    		session = HibernateUtil.getSession();
    		Query query=session.createQuery("select count(attnStud.id) from AttendanceStudent attnStud " +
    				      " where attnStud.attendance.attendanceDate >= :fromDate and attnStud.attendance.attendanceDate <= :toDate " +
    				      " and  attnStud.attendance.isCanceled = 0  and attnStud.student.id= :studentId " +
    				      " and attnStud.student.admAppln.courseBySelectedCourseId.program.academicYear= :academicYear group by attnStud.student.id");
    		query.setInteger("studentId", studentId);
    		query.setDate("fromDate",fromDate);
    		query.setDate("toDate", toDate);
    		query.setInteger("academicYear", academicYear);
    		attnStudnts=query.list();
    		return attnStudnts;
    	 }
    	 catch (Exception e) {
 			log.error("Error while getAttendanceStudent." +e);
 			throw  new ApplicationException(e);
 		 } finally {
 			if (session != null) {
 				session.flush();
 				//session.close();
 			}
 		} 
    }
    
    public ExamMarksEntryBO getExamMarksEntryBO(int studentId)throws Exception{
    	Session session = null;
    	ExamMarksEntryBO marksEntryBO=null;
    	try{
    		session = HibernateUtil.getSession();
    		Query query=session.createQuery("select examMarksEntryBO from ExamMarksEntryBO examMarksEntryBO " +
    				 " where examMarksEntryBO.examDefinitionBO.isCurrent=1 and examMarksEntryBO.examDefinitionBO.isActive=1 " +
    				 " and examMarksEntryBO.studentId=:studentId");
    		//query.setInteger("examTypeId",CMSConstants.EXAM_TYPE_ID);
    		query.setInteger("studentId", studentId);
    		marksEntryBO=(ExamMarksEntryBO)query.uniqueResult();
    		return marksEntryBO;
    	 }
    	 catch (Exception e) {
 			log.error("Error while getExamMarksEntryBO." +e);
 			throw  new ApplicationException(e);
 		 } finally {
 			if (session != null) {
 				session.flush();
 				//session.close();
 			}
 		}
    }
    
    public Subject getSubject(int subjectId)throws Exception{
    	Session session=null;
    	Subject subject=null;
    	try{
    		 session=HibernateUtil.getSession();
    		 Query query=session.createQuery("select subject from Subject subject where subject.id =:subjectId and subject.isActive=1");
    		 query.setInteger("subjectId",subjectId);
    		 subject=(Subject)query.uniqueResult();
    		 return subject;
    	}
    	catch (Exception e){
    		log.error("Error while getSubject." +e);	
    		throw new ApplicationException(e);
    	}finally{
    		if(session !=null){
    			session.flush();
    		}
    	}
    }
    				
    }
