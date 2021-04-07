package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.DataException;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;
import com.kp.cms.transactions.attandance.ICreatePracticalBatchTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CreatePracticalBatchTransactionImpl implements ICreatePracticalBatchTransaction{
	
	private static final Log log = LogFactory.getLog(CreatePracticalBatchTransactionImpl.class);

	/**
	 * Get the subjectGroupIds based on subjectId
	 */
	
	public List<SubjectGroupSubjects> getSubjectGroupDetails(int subjectId)throws Exception {
		log.info("Inside of getSubjectGroupDetails of CreatePracticalBatchTransactionImpl");
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectList=null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			subjectGroupSubjectList = session.createQuery("from SubjectGroupSubjects sub where sub.subjectGroup.isActive = 1" +
			"and sub.subject.isActive = 1 and sub.subject.id = " + subjectId +"order by sub.subjectGroup.id").list();		
		} catch (Exception e) {
		log.error("Exception ocured at getSubjectGroupDetails of CreatePracticalBatchTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		log.info("End of getSubjectGroupDetails of CreatePracticalBatchTransactionImpl");
		return subjectGroupSubjectList;
	}
	
	/**
	 * Used to get Student List based on the classSchemewiseId	
	 */
	
	public List<Student> getStudentList(int classSchemewiseId) throws Exception {
		log.info("Inside of getStudentList of CreatePracticalBatchTransactionImpl");
		Session session = null;
		List<Student> studentList=null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			studentList = session.createQuery("from Student s where s.classSchemewise.classes.isActive = 1 and s.isActive = 1" +
			" and s.classSchemewise.id = " + classSchemewiseId +"order by s.registerNo").list();		
		} catch (Exception e) {
		log.error("Exception ocured at getStudentList of CreatePracticalBatchTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		log.info("End of getStudentList of CreatePracticalBatchTransactionImpl");
		return studentList;
	}

	/**
	 * Is used to get the regd nos between a range
	 */
	public List<String> getRequiredRegdNos(int classSchemewiseId, String regNoFrom, String regNoTo) throws Exception{
		log.info("Inside of getRequiredRegdNos of CreatePracticalBatchTransactionImpl");
		Session session = null;
		List<String> regNoList;
		try {
			//session =InitSessionFactory.getInstance().openSession();			
			session = HibernateUtil.getSession();
				if(StringUtils.isNumeric(regNoFrom) && StringUtils.isNumeric(regNoTo)){
					regNoList = session.createQuery("select student.registerNo" +
							" from Student student" +
							" where student.classSchemewise.classes.isActive = 1 " +
							" and student.classSchemewise.id = " + classSchemewiseId + " " +
							" and student.registerNo between '" + Integer.parseInt(regNoFrom) 
							+"' and '"+ Integer.parseInt(regNoTo) +"' order by student.registerNo").list();
				}
				else{
					regNoList = session.createQuery("select student.registerNo" +
							" from Student student" +
							" where student.classSchemewise.classes.isActive = 1 " +
							" and student.classSchemewise.id = " + classSchemewiseId + " " +
							" and student.registerNo between '" + regNoFrom +"' and '"+ regNoTo+"' order by student.registerNo").list();
				}	
			return regNoList;
		} catch (Exception e) {
			log.error("Exception ocured at getRequiredRegdNos of CreatePracticalBatchTransactionImpl :"+e);
			throw  new BusinessException(e);
		}
		finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}		
	}

	/**
	 * Is used to get the roll nos between a range entered in the UI
	 */
	public List<String> getRequiredRollNos(int classSchemewiseId, String regNoFrom, String regNoTo) throws Exception{
		log.info("Inside of getRequiredRollNos of CreatePracticalBatchTransactionImpl");
		Session session = null;
		List<String> rollNoList;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
				if(StringUtils.isNumeric(regNoFrom) && StringUtils.isNumeric(regNoTo)){
					rollNoList = session.createQuery("select student.rollNo" +
							" from Student student" +
							" where student.classSchemewise.classes.isActive = 1 " +
							" and student.classSchemewise.id = " + classSchemewiseId + " " +
							" and student.rollNo between '" + Integer.parseInt(regNoFrom) 
							+"' and '"+ Integer.parseInt(regNoTo) +"' order by student.rollNo").list();
				}
				else{
					rollNoList = session.createQuery("select student.rollNo" +
							" from Student student" +
							" where student.classSchemewise.classes.isActive = 1 " +
							" and student.classSchemewise.id = " + classSchemewiseId + " " +
							" and student.rollNo between '" + regNoFrom +"' and '"+ regNoTo+"' order by student.rollNo").list();
				}	
			return rollNoList;
		} catch (Exception e) {
			log.error("Exception ocured at getRequiredRollNos of CreatePracticalBatchTransactionImpl :"+e);
			throw  new BusinessException(e);
		}
		finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
	}
/**
 * Used while saving students in batch
 */
	public boolean savePracticalBatch(Batch batch)throws Exception{
		log.info("Inside of savePracticalBatch of CreatePracticalBatchTransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//session = InitSessionFactory.getInstance().openSession();
				session = HibernateUtil.getSession();

				transaction = session.beginTransaction();
				session.save(batch);
				transaction.commit();
				return true;
			}catch(DataException e){
				if(transaction != null){
					transaction.rollback();
					log.error("Exception occured in adding savePracticalBatch in IMPL :"+e);
					throw  new ApplicationException(e);
			}
			}
			catch (Exception e1){	
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in adding savePracticalBatch in IMPL :"+e1);
				throw  new BusinessException(e1);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			}
		log.info("End of savePracticalBatch of CreatePracticalBatchTransactionImpl");
		return false;
	}
	/**
	 * Used while duplicate check on batchName, class and subject
	 */
	public Batch getBatchDetailsbyBatchName(String batchName, CreatePracticalBatchTO batchTO)throws Exception{
		log.info("Inside of getBatchDetailsbyBatchName of CreatePracticalBatchTransactionImpl");
		int classSchemewiseId = batchTO.getClassSchemewiseTO().getId();
		int subjectId = 0;
		if(batchTO.getSubjectTO()!=null && batchTO.getSubjectTO().getId()>0){
			subjectId=batchTO.getSubjectTO().getId();
		}
		int activityId=0;
		if(batchTO.getActivityTO()!=null && batchTO.getActivityTO().getId()>0){
			activityId=batchTO.getActivityTO().getId();
		}
		Session session = null;
		Batch batch;
			try {
				//session =InitSessionFactory.getInstance().openSession();
				session = HibernateUtil.getSession();
				String query="from Batch b where b.batchName = '"+batchName+"'"
				+ "and b.classSchemewise.id = " + classSchemewiseId;
				if(subjectId>0){
					query=query+" and b.subject.id ="+subjectId;
				}
				if(activityId>0){
					query=query+" and b.activity.id="+activityId;
				}
				batch = (Batch) session.createQuery(query).uniqueResult();		
			} catch (Exception e) {
			log.error("Exception ocured at getBatchDetailsbyBatchName of CreatePracticalBatchTransactionImpl :"+e);
				throw  new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getBatchDetailsbyBatchName of CreatePracticalBatchTransactionImpl");
		return batch;
	}

	/**
	 * Used to view all the practical batch details.
	 * Used in edit practical batch section
	 */
	
	public List<Batch>  getPracticalBatchDetailsBySubjectClass(int classSchemewiseId, int subjectId,int activityId) throws Exception {
		log.info("Inside of getAllPracticalBatchDetails of CreatePracticalBatchTransactionImpl");
		List<Batch> batchDetailsList;
		Session session = null;
			try {
				session =InitSessionFactory.getInstance().openSession();//done by priyatham
//				session = HibernateUtil.getSession();
				String query="from Batch batch where batch.isActive = 1 and batch.classSchemewise.classes.isActive = 1 " +
				"and batch.classSchemewise.id = " + classSchemewiseId;
				if(subjectId>0){
					query=query+" and batch.subject.isActive = 1 and batch.subject.id = " + subjectId;
				}
				if(activityId>0){
					query=query+" and batch.activity.isActive=1 and batch.activity.id="+activityId;
				}
				batchDetailsList = session.createQuery(query).list();		
			} catch (Exception e) {
			log.error("Exception ocured at getAllPracticalBatchDetails of CreatePracticalBatchTransactionImpl :"+e);
				throw  new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getAllPracticalBatchDetails of CreatePracticalBatchTransactionImpl");
		return batchDetailsList;
	}

	/**
	 * Used while editing
	 * Gets batch details based on the batchId
	 */
	
	public Batch getBatchDetailsById(int batchId) throws Exception {
		log.info("Inside of getBatchDetailsById of CreatePracticalBatchTransactionImpl");
		Batch batch;
		Session session = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();

			batch = (Batch) session.createQuery("from Batch batch where batch.isActive = 1 and batch.id = " + batchId).uniqueResult();
		} catch (Exception e) {
		log.error("Exception ocured at getAllPracticalBatchDetails of CreatePracticalBatchTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		log.info("End of getBatchDetailsById of CreatePracticalBatchTransactionImpl");
		return batch;
	}

	/**
	 * Used for duplicate check of students
	 */
	
	public List<Batch> getStudentsBySubjectAndClassSchemewise(int subjectId,int classSchemewiseId,int activityId)throws Exception {
		log.info("Inside of getStudentsBySubjectAndClassSchemewise of CreatePracticalBatchTransactionImpl");
		List<Batch> batcList;		
		Session session = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			String query="from Batch batch where batch.isActive = 1 and batch.classSchemewise.id = " + classSchemewiseId;
			if(subjectId>0){
				query=query+" and batch.subject.id = " + subjectId;
			}
			if(activityId>0){
				query=query+" and batch.activity.id="+activityId;
			}
			batcList = session.createQuery(query).list();		
		} catch (Exception e) {
		log.error("Exception ocured at getStudentsBySubjectAndClassSchemewise of CreatePracticalBatchTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		log.info("End of getStudentsBySubjectAndClassSchemewise of CreatePracticalBatchTransactionImpl");
		return batcList;
	}
	/**
	 * Get students based on Id
	 */
	public Student getStudentsById(int id)throws Exception {
		log.info("Inside of getStudentsById of CreatePracticalBatchTransactionImpl");
		Student student;
		Session session = null;
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();

			student = (Student) session.createQuery("from Student student where student.isActive = 1 and student.id = " + id).uniqueResult();
		} catch (Exception e) {
		log.error("Exception ocured at getStudentsById of CreatePracticalBatchTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		log.info("End of getStudentsById of CreatePracticalBatchTransactionImpl");
		return student;		
	}

	/**
	 * Used to delete a practical batch
	 * Makes the isActive as false.
	 */
	
	public boolean deletePracticalBatch(Batch batch)throws Exception {
		log.info("Inside of deletePracticalBatch of CreatePracticalBatchTransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//session = InitSessionFactory.getInstance().openSession();
				session = HibernateUtil.getSession();

				transaction = session.beginTransaction();				
				session.update(batch);
				transaction.commit();				
				log.info("End of deletePracticalBatch of CreatePracticalBatchTransactionImpl");
				return true;
			}
			catch (Exception e){	
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in deletePracticalBatch in IMPL :"+e);
				throw  new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
			}		
		}

	/**
	 * Method is used to reactivate practical batch
	 */
	public boolean reActivatePracticalBatch(String batchName, String userId,
			int classSchemewiseId, int subjectId,int activityId) throws Exception {
		log.info("Inside of reActivatePracticalBatch of CreatePracticalBatchTransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String q="from Batch b where b.classSchemewise.id ="+classSchemewiseId+" and b.batchName ='"+batchName+"'";
				if(subjectId>0){
					q=q+" and b.subject.id="+subjectId;
				}
				if(activityId>0){
					q=q+" and b.activity.id="+activityId;
				}
				//session = sessionFactory.openSession();
				Query query = session.createQuery(q);
//				query.setInteger("classSchemewiseId",classSchemewiseId);
//				query.setInteger("subjectId",subjectId);
//				query.setString("batchName", batchName);
				Batch batch = (Batch) query.uniqueResult();
				transaction = session.beginTransaction();		
				batch.setIsActive(true);
				batch.setLastModifiedDate(new Date());
				batch.setModifiedBy(userId);
				session.update(batch);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reactivating of CreatePracticalBatchTransactionImpl in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivatePracticalBatch of CreatePracticalBatchTransactionImpl");
				}
			}

	/**
	 * Used to update a practical batch
	 */
	public boolean updatePracticalBatch(Batch batch) throws Exception {
		log.info("Inside of updatePracticalBatch of CreatePracticalBatchTransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				session = InitSessionFactory.getInstance().openSession();
//				session = HibernateUtil.getSession();

				transaction = session.beginTransaction();
				session.saveOrUpdate(batch);
				transaction.commit();
				log.info("End of updatePracticalBatch of CreatePracticalBatchTransactionImpl");
				return true;
			}
			catch (Exception e){	
					if(transaction != null){
						transaction.rollback();
					}
				log.error("Exception occured in updating PracticalBatch in IMPL :"+e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
		}
	}

