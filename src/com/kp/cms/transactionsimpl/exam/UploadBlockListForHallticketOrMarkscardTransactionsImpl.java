package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.UploadBlockListForHallticketOrMarkscardForm;
import com.kp.cms.to.exam.ValuationChallanTO;
import com.kp.cms.transactions.exam.IUploadBlockListForHallticketOrMarkscardTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadBlockListForHallticketOrMarkscardTransactionsImpl implements IUploadBlockListForHallticketOrMarkscardTransaction{
	private static volatile UploadBlockListForHallticketOrMarkscardTransactionsImpl uploadBlockListForHallticketOrMarkscardTransactionsImpl = null;
	private static final Log log = LogFactory.getLog(UploadBlockListForHallticketOrMarkscardTransactionsImpl.class);
	/**
	 * @return
	 */
	public static UploadBlockListForHallticketOrMarkscardTransactionsImpl getInstance() {
		if (uploadBlockListForHallticketOrMarkscardTransactionsImpl == null) {
			uploadBlockListForHallticketOrMarkscardTransactionsImpl = new UploadBlockListForHallticketOrMarkscardTransactionsImpl();
		}
		return uploadBlockListForHallticketOrMarkscardTransactionsImpl;
	}
	
	public	Map<String,Integer> getClassIdByClassNameAndYear(String year) throws Exception{

		Session session=null;
		
		Map<String,Integer> classidMap=new HashMap<String,Integer>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ClassSchemewise cs where cs.curriculumSchemeDuration.academicYear=:year ");
			query.setParameter("year", (Integer.parseInt(year)));
			List<ClassSchemewise> list=query.list();
			if(list!=null){
				Iterator<ClassSchemewise> iterator=list.iterator();
				while (iterator.hasNext()) {
					ClassSchemewise classes = iterator.next();
					if(classes.getClasses().getName() != null && !classes.getClasses().getName().isEmpty() && classes.getClasses().getId()!=0 ){
						classidMap.put(classes.getClasses().getName(),classes.getClasses().getId());
					}
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
			session.close();
		}
		
		return classidMap;
    }
    
    public	Map<String,Integer> getStudentIdByStudentRegNum(String year) throws Exception{

		Session session=null;
		Map<String,Integer> studentIdMap=new HashMap<String,Integer>();
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.registerNo != null and s.isHide = 0 and s.admAppln.isCancelled=0 and s.classSchemewise.curriculumSchemeDuration.academicYear=:year");
		  query.setParameter("year", (Integer.parseInt(year)));
		  List<Student> listl=query.list();
			Iterator<Student> itr = listl.iterator();
			while (itr.hasNext()) {
				Student student = (Student) itr.next();
				studentIdMap.put(student.getRegisterNo(), student.getId());
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
			session.close();
		}
		
		return studentIdMap;
    }
    
    public boolean uploadBlockListForHallticketOrMarkscard(Map<Integer,ExamBlockUnblockHallTicketBO> ebo,UploadBlockListForHallticketOrMarkscardForm objform ) throws Exception {
		log.debug("inside uploadBlockListForHallticketOrMarkscard");
		
		//ExamBlockUnblockHallTicketBO examBo = new ExamBlockUnblockHallTicketBO();
		
		Session session = null;
		Transaction transaction = null;
		boolean isSaved=false;
			try {
			
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			//session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if(ebo!=null && !ebo.isEmpty()){
			//Iterator<ExamBlockUnblockHallTicketBO> itr=ebo.iterator();
			Iterator<Entry<Integer, ExamBlockUnblockHallTicketBO>> iterator = ebo.entrySet().iterator();
			while (iterator.hasNext()) {
				int count=0;
				Map.Entry<Integer, ExamBlockUnblockHallTicketBO> entry = (Map.Entry<Integer, ExamBlockUnblockHallTicketBO>) iterator.next();
				ExamBlockUnblockHallTicketBO bo=entry.getValue();
			//while(itr.hasNext()){
			//ExamBlockUnblockHallTicketBO bo=itr.next();
			Query query = session.createQuery("from ExamBlockUnblockHallTicketBO e where e.classId = :classId and e.examId= :examId and e.hallTktOrMarksCard=:hallTktOrMarksCard and e.studentId=:studentId");
			if( bo.getClassId()!= 0){
			query.setInteger("classId", bo.getClassId());
			}
			if( bo.getExamId()!= 0){
			query.setInteger("examId", bo.getExamId());
			}
			query.setString("hallTktOrMarksCard", bo.getHallTktOrMarksCard());
			if( bo.getStudentId()!= 0){
				query.setInteger("studentId", bo.getStudentId());
			}
			ExamBlockUnblockHallTicketBO examBo = (ExamBlockUnblockHallTicketBO)query.uniqueResult();
			if(examBo !=null){
				if(count==0){
				String reason=examBo.getBlockReason()+","+bo.getBlockReason();
				examBo.setBlockReason(reason);
				examBo.setLastModifiedDate(new Date());
				examBo.setModifiedBy(objform.getUserId());
				session.update(examBo);
				}
			}else{
				ExamBlockUnblockHallTicketBO exam = new ExamBlockUnblockHallTicketBO();
				exam.setClassId(bo.getClassId());
				exam.setBlockReason(bo.getBlockReason());
				exam.setExamId(bo.getExamId());
				exam.setHallTktOrMarksCard(bo.getHallTktOrMarksCard());
				exam.setStudentId(bo.getStudentId());
				exam.setLastModifiedDate(new Date());
				exam.setModifiedBy(objform.getUserId());
				exam.setCreatedDate(new Date());
				exam.setCreatedBy(objform.getUserId());
				session.save(exam);
			}
			isSaved=true;
			/*session.flush();*/
			}
			transaction.commit();
			session.flush();
			}else{
				isSaved=false;
			}
			session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocTypeDuplcated");
		return isSaved;
	}
    
    
    public ExamBlockUnblockHallTicketBO getExamBo(ExamBlockUnblockHallTicketBO ebo) throws Exception {
		log.debug("inside uploadBlockListForHallticketOrMarkscard");
		
		ExamBlockUnblockHallTicketBO examBo = null;
		
		Session session = null;
		Transaction transaction = null;
			try {
			
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session.createQuery("from ExamBlockUnblockHallTicketBO e where e.classId = :classId and e.examId= :examId and e.hallTktOrMarksCard=:hallTktOrMarksCard and e.studentId=:studentId");
			if( ebo.getClassId()!= 0){
			query.setInteger("classId", ebo.getClassId());
			}
			if( ebo.getExamId()!= 0){
			query.setInteger("examId", ebo.getExamId());
			}
			query.setString("hallTktOrMarksCard", ebo.getHallTktOrMarksCard());
			if( ebo.getStudentId()!= 0){
				query.setInteger("studentId", ebo.getStudentId());
			}
			 examBo = (ExamBlockUnblockHallTicketBO)query.uniqueResult();
			transaction.commit();
			session.flush();
			session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocTypeDuplcated");
		return examBo;
	}
    
    
    public	int getStudentRegisterNo(int studentId) throws Exception{
    	String regNo="";
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("select s.registerNo from  Student s where s.registerNo != null and s.isHide = 0 and s.admAppln.isCancelled=0 and s.id="+studentId);
		   regNo=(String)query.uniqueResult();
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
			session.close();
		}
		
		return Integer.parseInt(regNo);
    }
    
    
    /* (non-Javadoc) checking student by passing class (is student is related to for this class)
     * @see com.kp.cms.transactions.exam.IUploadBlockListForHallticketOrMarkscardTransaction#getStudentIdMapByClassId(int, int)
     */
    public boolean getStudentIdMapByClassId(int classId,int studentId ) throws Exception {
    String regNo="";
	Session session=null;
	boolean isStudent=false;
	try{
		session=HibernateUtil.getSession();
		Query query = session.createQuery("select s.registerNo from  Student s where s.registerNo != null and s.isHide = 0 "+
											" and s.admAppln.isCancelled=0 and s.id="+studentId+
											" and s.classSchemewise.classes.id="+classId);
	   regNo=(String)query.uniqueResult();
	   if(regNo!=null && !regNo.isEmpty()){
		   isStudent=true; 
	   }
	}catch (Exception e) {
		// TODO: handle exception
		throw new ApplicationException();
	}finally{
		if(session!=null)
			session.flush();
		session.close();
	}
	
	return isStudent;
	}
    
 }