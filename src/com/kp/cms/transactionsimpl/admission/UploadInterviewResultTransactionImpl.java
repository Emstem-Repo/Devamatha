package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewResultDetail;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.StudentSpecializationPrefered;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.transactions.admission.IUploadInterviewResultTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class UploadInterviewResultTransactionImpl implements IUploadInterviewResultTransaction{

	private static final Log log = LogFactory.getLog(InterviewResultsEntryTransactionImpl.class);
	
	/**
	 *	This method is used to get AdmAppln details from database based on courseId,year. 
	 */
	
	@Override
	public Map<Integer, Integer> getAdmApplnDetails(int year, int courseId) throws Exception {
		log.info("call of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select applnNo, id from AdmAppln a where a.course.id= :courseId and a.appliedYear= :year");
			query.setInteger("courseId",courseId);
			query.setInteger("year",year);
			Iterator iterator = query.iterate();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj!=null){
					map.put((Integer)obj[0],(Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		return map;
	}

	/**
	 * This method is used to get interviewStatusDetails from database.
	 */
	
	@Override
	public Map<String,Integer> getInterviewStatusDetails() throws Exception{
		log.info("call of getInterviewStatusDetails method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Map<String,Integer> statusMap = new HashMap<String, Integer>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select name, id from InterviewStatus ins");
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				if(obj!=null){
					statusMap.put((String)obj[0], (Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getInterviewStatusDetails method in  UploadInterviewResultTransactionImpl class.");
		return statusMap;
	}

	/**
	 * This method is used to get grade details from database.
	 */
	
	@Override
	public Map<String ,Integer> getGradeDetails() throws Exception {
		log.info("call of getGradeDetails method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Map<String, Integer> gradeMap = new HashMap<String, Integer>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select grade, id from Grade g where g.isActive =1");
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				if(obj!=null && !obj.toString().isEmpty()){
					gradeMap.put(obj[0].toString().toUpperCase(), (Integer)obj[1]);
					gradeMap.put(obj[0].toString().toLowerCase(), (Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getGradeDetails method in  UploadInterviewResultTransactionImpl class.");
		return gradeMap;
	}

	/**
	 * This method is used to save the uploaded data to database.
	 */
	
	@Override
	public boolean addUploadData(List<InterviewResult> interviewResult,List<StudentSpecializationPrefered> studentSpecializationBoList,String user) throws Exception {
		log.info("call of addUploadData method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			//session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if (interviewResult != null && !interviewResult.isEmpty()) {
				Iterator<InterviewResult> iterator = interviewResult.iterator();
				while (iterator.hasNext()) {
					InterviewResult interview = iterator.next();
					session.saveOrUpdate(interview); 
					//session.save(interview);
				}
			}
			if(studentSpecializationBoList!=null && !studentSpecializationBoList.isEmpty()){
				Iterator<StudentSpecializationPrefered> itr=studentSpecializationBoList.iterator();
				StudentSpecializationPrefered stu=null;
				while (itr.hasNext()) {
					StudentSpecializationPrefered studentSpecializationPrefered =  itr.next();
					AdmAppln adm=studentSpecializationPrefered.getAdmAppln();
					if(adm!=null && adm.getId()!=0){
						 stu=(StudentSpecializationPrefered)session.createQuery("from StudentSpecializationPrefered s where s.admAppln.id=:admId").setInteger("admId", adm.getId()).uniqueResult();
						 if(stu!=null){
							 studentSpecializationPrefered.setId(stu.getId());
						 }else{
							 studentSpecializationPrefered.setCreatedBy(user);
							 studentSpecializationPrefered.setCreatedDate(new Date());
						 }
						 studentSpecializationPrefered.setModifiedBy(user);
						 studentSpecializationPrefered.setLastModifiedDate(new Date());
						 if(studentSpecializationPrefered.getBackLogs()==null)
							 studentSpecializationPrefered.setBackLogs(false);
						 if(studentSpecializationPrefered.getId()>0)
							 session.merge(studentSpecializationPrefered);
						 else
							 session.save(studentSpecializationPrefered);
					}
				}
			}
			transaction.commit();
			isAdded = true;
		}catch (Exception e) {
			isAdded = false;
			if(transaction!=null)
				transaction.rollback();
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addUploadData method in  UploadInterviewResultTransactionImpl class.");
		return isAdded;
	}

	/**
	 * This method is used to check duplicate interviewResult.
	 */
	
	@Override
	public boolean checkDuplicate(int roundId, int admId, int subRoundId) throws Exception {
		log.info("call of checkDuplicate method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		boolean isExist =false;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select id from InterviewResult iresult where iresult.interviewProgramCourse.id= :ipcId and iresult.admAppln.id= :admId and iresult.interviewSubRounds.id= :subId and iresult.isActive = 1");
			query.setInteger("ipcId",roundId);
			query.setInteger("admId", admId);
			query.setInteger("subId",subRoundId);
			List<InterviewResult> list=query.list();
			 if(list!=null && !list.isEmpty()){
				 isExist=true;
			 }
		} catch (Exception e) {
			isExist =true;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of checkDuplicate method in  UploadInterviewResultTransactionImpl class.");
		return isExist;
	}

	/**
	 * This method is used to check duplicate interviewResult.
	 */
	
	@Override
	public boolean checkDuplicateWithoutSubRound(int roundId, int admId) throws Exception {
		log.info("call of checkDuplicate method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		boolean isExist =true;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select id from InterviewResult iresult where iresult.interviewProgramCourse.id= :ipcId and iresult.admAppln.id= :admId and iresult.isActive = 1");
			query.setInteger("ipcId",roundId);
			query.setInteger("admId", admId);
			List<InterviewResult> list=query.list();
			 if(list!=null && !list.isEmpty()){
				 isExist=false;
			 }
		} catch (Exception e) {
			isExist =true;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of checkDuplicate method in  UploadInterviewResultTransactionImpl class.");
		return isExist;
	}
	
	/**
	 * 	This method is used to get interviewResutlId from database.
	 */
	 
	@Override
	public int getInterviewResultId(int roundId, int admId, int subRoundId) throws Exception {
		log.info("call of getInterviewResultId method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		int id = 0;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer queryString = new StringBuffer("");
			queryString.append("select id from InterviewResult iresult where iresult.interviewProgramCourse.id= :ipcId and iresult.admAppln.id= :admId and iresult.isActive = 1");
			if(subRoundId > 0){
				queryString.append(" and iresult.interviewSubRounds.id= :subId ");
			}			
			Query query = session.createQuery(queryString.toString());
			query.setInteger("ipcId",roundId);
			query.setInteger("admId", admId);
			if(subRoundId > 0){
				query.setInteger("subId", subRoundId);
			}
			if(query.uniqueResult() != null){
				id = (Integer)query.uniqueResult();
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getInterviewResultId method in  UploadInterviewResultTransactionImpl class.");
		return id;
	}

	@Override
	public int getInterviewResultIdWithoutSubround(int roundId, int admId)
			throws Exception {
		log.info("call of getInterviewResultId method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		int id = 0;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select id from InterviewResult iresult where iresult.interviewProgramCourse.id= :ipcId and iresult.admAppln.id= :admId and iresult.isActive = 1");
			query.setInteger("ipcId",roundId);
			query.setInteger("admId", admId);
			if(query.uniqueResult() != null){
				id = (Integer)query.uniqueResult();
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getInterviewResultId method in  UploadInterviewResultTransactionImpl class.");
		return id;
	}

	@Override
	public List<Integer> getInterviewResultDetailId(int interviewResultId, int gradeId)
			throws Exception {
		log.info("call of getInterviewResultDetailId method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		List<Integer> interviewResultDetailList = new LinkedList<Integer>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select id from InterviewResultDetail iresultDet where iresultDet.interviewResult.id= :irsId and iresultDet.grade.id= :gradeId");
			query.setInteger("irsId",interviewResultId);
			query.setInteger("gradeId", gradeId);
			if(query.list() != null){
				interviewResultDetailList = query.list();
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getInterviewResultDetailId method in  UploadInterviewResultTransactionImpl class.");
		return interviewResultDetailList;
	}

	@Override
	public List<InterviewResultDetail> getInterviewResultDetailList(
			int iresultId, int gradeId) throws Exception {
		log.info("call of getInterviewResultDetailId method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		List<InterviewResultDetail> interviewResultDetailList = new LinkedList<InterviewResultDetail>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewResultDetail iresultDet where iresultDet.interviewResult.id= :irsId and iresultDet.grade.id= :gradeId");
			query.setInteger("irsId",iresultId);
			query.setInteger("gradeId", gradeId);
			if(query.list() != null){
				interviewResultDetailList = query.list();
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getInterviewResultDetailId method in  UploadInterviewResultTransactionImpl class.");
		return interviewResultDetailList;
	}
	@Override
	public int getInterviewersPerPanel(int mainroundId, int subroundId)
			throws Exception {
		Session session = null;
		InterviewProgramCourse interviewProgramCourse = null;
		InterviewSubRounds interviewSubRounds = null;
		int interviewersPerPanel = 0;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(subroundId != 0){
				interviewSubRounds = (InterviewSubRounds) session.createQuery("from InterviewSubRounds interviewSubRounds where interviewSubRounds.id = " + subroundId + " and interviewSubRounds.isActive = 1").uniqueResult();
				interviewersPerPanel = interviewSubRounds.getNoOfInterviewsPerPanel().intValue();
			}else if(mainroundId != 0){
				interviewProgramCourse = (InterviewProgramCourse) session.createQuery("from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.id = " + mainroundId + " and interviewProgramCourse.isActive = 1").uniqueResult();
				interviewersPerPanel = interviewProgramCourse.getNoOfInterviewsPerPanel().intValue();
			}
			
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return interviewersPerPanel;
	}
	/**
	 * This method is used to save the uploaded data to database.
	 */
	
	@Override
	public boolean addEntranceUploadData(List<InterviewResult> interviewResult) throws Exception {
		log.info("call of addUploadData method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if (interviewResult != null && !interviewResult.isEmpty()) {
				Iterator<InterviewResult> iterator = interviewResult.iterator();
				while (iterator.hasNext()) {
					InterviewResult interview = iterator.next();
					session.saveOrUpdate(interview); 
				}
			transaction.commit();
			isAdded = true;
			}
		}catch (Exception e) {
			isAdded = false;
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addUploadData method in  UploadInterviewResultTransactionImpl class.");
		return isAdded;
	}

	@Override
	public int getEntranceIntResultDetailId(int intId) throws Exception {
		log.info("call of getInterviewResultId method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		int id = 0;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			//session = HibernateUtil.getSession();
			Query query = session.createQuery("select id from InterviewResultDetail iresult where iresult.interviewResult.id= " + intId );
			if(query.uniqueResult() != null){
				id = (Integer)query.uniqueResult();
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of getInterviewResultId method in  UploadInterviewResultTransactionImpl class.");
		return id;
	}

	@Override
	public Map<Integer, Integer> getAdmApplnDetailsForSelectedCourses(
			int applicationYear, List<Integer> coursesList,InterviewResultEntryForm interviewResultEntryForm) throws Exception {
		log.info("call of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Map<Integer,Integer> intPrgCoursemap = new HashMap<Integer, Integer>();
			Query query = session.createQuery("select a.applnNo, a.id, interviewProgramCourse.id, a.courseBySelectedCourseId.id" +
												" from AdmAppln a" +
												" inner join a.courseBySelectedCourseId.interviewProgramCourses interviewProgramCourse "+
												" where a.courseBySelectedCourseId.id in (:courseId) and a.appliedYear= :year");
			query.setParameterList("courseId",coursesList);
			query.setInteger("year",applicationYear);
			Iterator iterator = query.iterate();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj!=null){
					map.put((Integer)obj[0],(Integer)obj[1]);
					intPrgCoursemap.put((Integer)obj[0],(Integer)obj[3]);
				}
			}
			interviewResultEntryForm.setIntPrgCourseMap(intPrgCoursemap);
			Query hqlQuery = session.createQuery("from InterviewProgramCourse c where c.course.id in(:courseId) and c.year= :year and c.isActive = 1 order by c.sequence");
			hqlQuery.setParameterList("courseId",coursesList);
			hqlQuery.setInteger("year",applicationYear);
			List<InterviewProgramCourse> list = hqlQuery.list();
			Iterator<InterviewProgramCourse> iterator2 = list.iterator();
			if(list != null && !list.isEmpty()){
				Map<String, Map<Integer, Integer>> interviewMap = new HashMap<String, Map<Integer,Integer>>();
				while (iterator2.hasNext()) {
					InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) iterator2.next();
					if(interviewProgramCourse.getName() != null && !interviewProgramCourse.getName().isEmpty()){
						Map<Integer, Integer> subMap = null;
						if(interviewMap.containsKey(interviewProgramCourse.getName())){
							subMap = interviewMap.remove(interviewProgramCourse.getName());
						}else{
							subMap = new HashMap<Integer, Integer>();
						}
						if(interviewProgramCourse.getCourse() != null){
							subMap.put(interviewProgramCourse.getCourse().getId(), interviewProgramCourse.getId());
						}
						interviewMap.put(interviewProgramCourse.getName(),subMap);
					}
				}
				interviewResultEntryForm.setInterviewTypeMap(interviewMap);
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getAdmApplnDetails method in  UploadInterviewResultTransactionImpl class.");
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IUploadInterviewResultTransaction#checkSubroundData(com.kp.cms.forms.admission.InterviewResultEntryForm)
	 */
	@Override
	public boolean checkSubroundData(InterviewResultEntryForm interviewResultEntryForm) throws Exception {
		Session session = null;
		boolean value = false;
		try{
			session = HibernateUtil.getSession();
			String[] courseids = interviewResultEntryForm.getCourses();
			List<Integer> courseList = new ArrayList<Integer>();
			for (int i = 0; i < courseids.length; i++) {
				if(courseids[i] != null && !courseids[i].trim().isEmpty())
				courseList.add(Integer.parseInt(courseids[i]));
			}
			Query query = session.createQuery("select c.id from InterviewProgramCourse c where c.course.id in(:courseId) and c.year= :year and c.isActive = 1 and c.name='"+interviewResultEntryForm.getInterviewTypeId()+"' order by c.sequence");
			query.setParameterList("courseId", courseList);
			query.setString("year", interviewResultEntryForm.getApplicationYear());
			List<Integer> interviewIds = query.list();
			if(courseList.size() > interviewIds.size()){
				value = true;
			}
			if(interviewResultEntryForm.getInterviewSubroundId() != null && !interviewResultEntryForm.getInterviewSubroundId().trim().isEmpty()){
				if(interviewIds != null){
					Query query2 = session.createQuery("select i from InterviewSubRounds i where i.interviewProgramCourse.id in(:interviewTypeId) and i.isActive = 1 and i.name=(select s.name from InterviewSubRounds s where s.id="+interviewResultEntryForm.getInterviewSubroundId()+")");
					query2.setParameterList("interviewTypeId", interviewIds);
					List<InterviewSubRounds> subroundIds = query2.list();
					if(interviewIds.size() > subroundIds.size()){
						value = true;
					}
					Iterator<InterviewSubRounds> iterator = subroundIds.iterator();
					Map<Integer, Integer> subroundMap = new HashMap<Integer, Integer>();
					while (iterator.hasNext()) {
						InterviewSubRounds interviewSubRounds = (InterviewSubRounds) iterator.next();
						if(interviewSubRounds.getInterviewProgramCourse() != null && interviewSubRounds.getInterviewProgramCourse().getId() != 0){
							subroundMap.put(interviewSubRounds.getInterviewProgramCourse().getId(), interviewSubRounds.getId());
						}
					}
					interviewResultEntryForm.setSubroundMap(subroundMap);
				}
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		}
		finally {
			if (session != null) {
				session.flush();
			}
		}
		return value;
	}
	
}