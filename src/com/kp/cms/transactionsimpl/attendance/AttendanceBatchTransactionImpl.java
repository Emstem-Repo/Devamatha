package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttendanceBatchForm;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;
import com.kp.cms.transactions.attandance.IAttendanceBatchTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class AttendanceBatchTransactionImpl implements
		IAttendanceBatchTransaction {
	/**
	 * Singleton object of AttendanceBatchTransactionImpl
	 */
	private static volatile AttendanceBatchTransactionImpl attendanceBatchTransactionImpl = null;
	private static final Log log = LogFactory.getLog(AttendanceBatchTransactionImpl.class);
	private AttendanceBatchTransactionImpl() {
		
	}
	/**
	 * return singleton object of AttendanceBatchTransactionImpl.
	 * @return
	 */
	public static AttendanceBatchTransactionImpl getInstance() {
		if (attendanceBatchTransactionImpl == null) {
			attendanceBatchTransactionImpl = new AttendanceBatchTransactionImpl();
		}
		return attendanceBatchTransactionImpl;
	}
	@Override
	public List<Batch> getBatchsByClassId(String[] classIds, String subjectId,
			String activityAttendance,boolean checkActive,String batchName) throws Exception {
		log.info("Inside of getBatchsByClassId of CreatePracticalBatchTransactionImpl");
		List<Batch> batchDetailsList;
		Session session = null;
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<classIds.length;i++){
			 intType.append(classIds[i]);
			 if(i<(classIds.length-1)){
				 intType.append(",");
			 }
		}
			try {
				session =InitSessionFactory.getInstance().openSession();
				String query="select bs.batch from BatchStudent bs" +
						" where bs.classSchemewise.classes.isActive=1" +
						" and bs.classSchemewise.id in ( "+intType+" )";
				if(checkActive)
					query=query+" and bs.batch.isActive=1";
				if(!batchName.isEmpty())
					query=query+" and bs.batch.batchName='"+batchName+"'";
				
				if(subjectId!=null && !subjectId.isEmpty()){
					query=query+" and bs.batch.subject.id="+subjectId;
				}
				if(activityAttendance!=null && !activityAttendance.isEmpty()){
					query=query+" and bs.batch.activity.id="+activityAttendance;
				}
				query=query+" group by bs.batch.id";
				batchDetailsList = session.createQuery(query).list();		
			} catch (Exception e) {
			log.error("Exception ocured at getBatchsByClassId of CreatePracticalBatchTransactionImpl :"+e);
				throw  new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getBatchsByClassId of CreatePracticalBatchTransactionImpl");
		return batchDetailsList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceBatchTransaction#deletePracticalBatch(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deletePracticalBatch(String batchId, String userId)
			throws Exception {
		log.info("Entering into deletePracticalBatch");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Batch docTypeExams=(Batch)session.get(Batch.class,Integer.parseInt(batchId));
			docTypeExams.setIsActive(false);
			docTypeExams.setModifiedBy(userId);
			docTypeExams.setLastModifiedDate(new Date());
			session.update(docTypeExams);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
				return false;
			}
			log.error("Exception occured while delete deletePracticalBatch :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Leaving into deletePracticalBatch");
		}
	}
	@Override
	public boolean reactivePracticalBatch(String batchId, String userId)
			throws Exception {
		log.info("Entering into deletePracticalBatch");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			String selectedBatchs[] = batchId.split(",");
			for (int i = 0; i < selectedBatchs.length; i++) {
				Batch docTypeExams=(Batch)session.get(Batch.class,Integer.parseInt(selectedBatchs[i]));
				docTypeExams.setIsActive(true);
				docTypeExams.setModifiedBy(userId);
				docTypeExams.setLastModifiedDate(new Date());
				session.update(docTypeExams);
			}
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
				return false;
			}
			log.error("Exception occured while delete deletePracticalBatch :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Leaving into deletePracticalBatch");
		}
	}
	@Override
	public Map<Integer, Integer> getExistsStudentForBatch(String batchId)
			throws Exception {
		log.info("Entering into deletePracticalBatch");
		Session session = null;
		try {
			Map<Integer,Integer> existMap=new HashMap<Integer, Integer>();
			session = HibernateUtil.getSession();
			Batch docTypeExams=(Batch)session.get(Batch.class,Integer.parseInt(batchId));
			Set<BatchStudent> set=docTypeExams.getBatchStudents();
			Iterator<BatchStudent> itr=set.iterator();
			while (itr.hasNext()) {
				BatchStudent batchStudent = (BatchStudent) itr.next();
				if(batchStudent.getIsActive()!=null && batchStudent.getIsActive())
					existMap.put(batchStudent.getStudent().getId(),batchStudent.getId());
			}
			return existMap;
		} catch (Exception e) {	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
		log.info("Leaving into deletePracticalBatch");
		}
	}
	@Override
	public Map<Integer, String> getStudentExistInAnotherBatch(String query)
			throws Exception {
		log.info("Entering into getStudentExistInAnotherBatch");
		Session session = null;
		try {
			Map<Integer,String> existMap=new HashMap<Integer, String>();
			session = HibernateUtil.getSession();
			List<Object[]> list=session.createQuery(query).list();
			Iterator<Object[] > itr=list.iterator();
			while (itr.hasNext()) {
				Object[] obj = itr.next();
				if(obj[0]!=null && obj[1]!=null)
					existMap.put(Integer.parseInt(obj[0].toString()),obj[1].toString());
			}
			return existMap;
		} catch (Exception e) {	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
		log.info("Leaving into getStudentExistInAnotherBatch");
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceBatchTransaction#savePracticalBatch(com.kp.cms.forms.attendance.AttendanceBatchForm)
	 */
	@Override
	public boolean savePracticalBatch(AttendanceBatchForm attendanceBatchForm)
			throws Exception {
		log.debug("inside savePracticalBatch");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Batch batch=null;
			if(attendanceBatchForm.getBatchId()!=null && !attendanceBatchForm.getBatchId().isEmpty()){
				batch=(Batch)session.get(Batch.class, Integer.parseInt(attendanceBatchForm.getBatchId()));
				batch.setBatchName(attendanceBatchForm.getBatchName());
				batch.setModifiedBy(attendanceBatchForm.getUserId());
				batch.setLastModifiedDate(new Date());
				session.update(batch);
			}else{
				batch=new Batch();
				batch.setBatchName(attendanceBatchForm.getBatchName());
				batch.setCreatedBy(attendanceBatchForm.getUserId());
				batch.setModifiedBy(attendanceBatchForm.getUserId());
				batch.setLastModifiedDate(new Date());
				batch.setCreatedDate(new Date());
				batch.setIsActive(true);
				if(attendanceBatchForm.getSubjectId()!=null && !attendanceBatchForm.getSubjectId().isEmpty()){
					Subject subject=new Subject();
					subject.setId(Integer.parseInt(attendanceBatchForm.getSubjectId()));
					batch.setSubject(subject);
				}
				if(attendanceBatchForm.getActivityAttendance()!=null && !attendanceBatchForm.getActivityAttendance().isEmpty()){
					Activity activity=new Activity();
					activity.setId(Integer.parseInt(attendanceBatchForm.getActivityAttendance()));
					batch.setActivity(activity);
				}
				session.save(batch);
			}
			Iterator<CreatePracticalBatchTO> tcIterator = attendanceBatchForm.getStudentList().iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				CreatePracticalBatchTO to = tcIterator.next();
				if(to.isCheckValue() && to.getChecked().equalsIgnoreCase("true")){
					BatchStudent batchStudent=new BatchStudent();
					if(to.getId()>0){
						batchStudent.setId(to.getId());
					}else{
						Integer id=(Integer)session.createQuery("select b.id from BatchStudent b where b.student.id="+to.getStudentTO().getId()+
								" and b.classSchemewise.id="+to.getClassSchemewiseTO().getId()+" and b.batch.id="+batch.getId()).uniqueResult();
						if(id!=null && id>0){
							batchStudent.setId(id);
						}else{
							batchStudent.setCreatedDate(new Date());
							batchStudent.setCreatedBy(attendanceBatchForm.getUserId());
						}
					}
					batchStudent.setBatch(batch);
					Student student=new Student();
					student.setId(to.getStudentTO().getId());
					batchStudent.setStudent(student);
					ClassSchemewise  classSchemewise=new ClassSchemewise();
					classSchemewise.setId(to.getClassSchemewiseTO().getId());
					batchStudent.setClassSchemewise(classSchemewise);
					batchStudent.setModifiedBy(attendanceBatchForm.getUserId());
					batchStudent.setLastModifiedDate(new Date());
					batchStudent.setIsActive(true);
					session.saveOrUpdate(batchStudent);
				}
				if(!to.isDummyCheckValue() && !to.isCheckValue()){
					if(to.getId()>0){
						BatchStudent batchStudent=(BatchStudent)session.get(BatchStudent.class, to.getId());
						batchStudent.setIsActive(false);
						batchStudent.setModifiedBy(attendanceBatchForm.getUserId());
						batchStudent.setLastModifiedDate(new Date());
						session.update(batchStudent);
					}
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving savePracticalBatch");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in savePracticalBatch impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in savePracticalBatch impl...", e);
			throw new ApplicationException(e);
		}
	}
}
