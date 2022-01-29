package com.kp.cms.transactionsimpl.attendance;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamStudentAttendanceMarksCjcBO;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.bo.exam.SubjectRuleSettingsAttendance;
import com.kp.cms.bo.exam.SubjectRuleSettingsSubInternal;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.attandance.ICalculateAttendanceMarkTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CalculateAttendanceMarkImpl implements ICalculateAttendanceMarkTransaction {
	static DecimalFormat df = new DecimalFormat("#.##");
	/**
	 * Singleton object of NewUpdateProccessTransactionImpl
	 */
	private static volatile CalculateAttendanceMarkImpl calAttMarkImpl = null;
	private static final Log log = LogFactory.getLog(CalculateAttendanceMarkImpl.class);
	private CalculateAttendanceMarkImpl() {
		
	}
	/**
	 * return singleton object of NewUpdateProccessTransactionImpl.
	 * @return
	 */
	public static CalculateAttendanceMarkImpl getInstance() {
		if (calAttMarkImpl == null) {
			calAttMarkImpl = new CalculateAttendanceMarkImpl();
		}
		return calAttMarkImpl;
	}
	
	
	public List getDataByQuery(String query) throws Exception {
		
		Session session = null;
		List selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
	
	
	public boolean saveUpdateProcess(List<StudentSupplementaryImprovementApplication> boList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(boList!=null){
				Iterator<StudentSupplementaryImprovementApplication> tcIterator = boList.iterator();
				int count = 0;
				while(tcIterator.hasNext()){
					StudentSupplementaryImprovementApplication bo = tcIterator.next();
					int chance=0;
					Integer chan=(Integer)session.createQuery("select max(s.chance)" +
							" from StudentSupplementaryImprovementApplication s where s.student.id="+bo.getStudent().getId()+
							" and s.classes.id="+bo.getClasses().getId()+" and s.examDefinition.id!="+bo.getExamDefinition().getId()).uniqueResult();
					if(chan!=null){
						chance=chan;
					}
					chance+=1;
					bo.setChance(chance);
					session.save(bo);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				}
			}
			transaction.commit();
			session.flush();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	
	public boolean deleteOldRecords(int id, String examId) throws Exception {
		log.debug("inside deleleAlreadyExistedRecords");
		Session session = null;
		Transaction tx = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
		    tx= session.beginTransaction();
			Query query = session.createQuery(" delete from StudentSupplementaryImprovementApplication s where s.examDefinition.id = " + examId + " and s.classes.id = " + id);
   		    query.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
			log.debug("leaving deleleAlreadyExistedRecords");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error while deleting"	, e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error while deleting" + e);
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * @param classId
	 * @param studentId
	 * @return
	 */
	@Override
	public Map<String, StudentAttendanceTO> getAttendanceForStudent(int classId, int studentId,List<Integer> subIdList) throws Exception {
		Map<String, StudentAttendanceTO> map=new HashMap<String, StudentAttendanceTO>();

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<Object[]> list =session.createQuery("select ast.attendance.subject.id, sum(ast.attendance.hoursHeld)," + 
			" sum(case when (ast.isPresent=1 or ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end)" +
			" from AttendanceStudent ast" +
			" join ast.attendance.attendanceClasses ac" +
			" where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
			" and ac.classSchemewise.classes.id=" +classId+
			" and ast.attendance.subject.id in (:subList)"+
			" group by ac.attendance.subject.id").setParameterList("subList",subIdList).list();
			/*List<Object[]> list =session.createQuery("select ast.attendance.subject.id,ast.attendance.attendanceType.id," +
					" sum(ast.attendance.hoursHeld), " +
					" sum(case when (ast.isPresent=1) then ast.attendance.hoursHeld else 0 end)," +
					" sum(case when (ast.isCoCurricularLeave=1) then ast.attendance.hoursHeld else 0 end), " +
					" sum(case when (ast.isOnLeave=1) then ast.attendance.hoursHeld else 0 end)" +
					" from AttendanceStudent ast" +
					" join ast.attendance.attendanceClasses ac" +
					" where ast.attendance.isCanceled=0 and ast.student.id=" +studentId+
					" and ac.classSchemewise.classes.id=" +classId+
					" and ast.attendance.subject.id in (:subList)"+
					" group by ac.attendance.subject.id,ac.attendance.attendanceType.id").setParameterList("subList",subIdList).list();*/
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] bo=itr.next();
					StudentAttendanceTO sto=new StudentAttendanceTO();
					if(bo[0]!=null)
					sto.setSubjectId(Integer.parseInt(bo[0].toString()));
					if(bo[1]!=null)
					sto.setSubjectHoursHeld(Integer.parseInt(bo[1].toString()));
					if(bo[2]!=null)
					sto.setPresentHoursAtt(Integer.parseInt(bo[2].toString()));
					
					
					map.put(String.valueOf(sto.getSubjectId()),sto);
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public double getAttendanceMarksForPercentage(int courseId, Integer subId,
			float percentage) throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		int marks = 0;
		List l=session.createQuery("from ExamSubCoursewiseAttendanceMarksBO e where e.subjectId = "+subId+" and e.courseId="+courseId).list();
		Query query=null;
		if(l!=null && !l.isEmpty()){
			query=session.createQuery( "select e.attendanceMarks"
					+ " from ExamSubCoursewiseAttendanceMarksBO e"
					+ " where e.subjectId = :subId and e.courseId=:courseId"
					+ " and e.fromPrcntgAttndnc <= :totalCalculation"
					+ " and e.toPrcntgAttndnc >= :totalCalculation").setParameter("subId", subId).setParameter("courseId", courseId).setParameter("totalCalculation",new BigDecimal(percentage));
		}else{
			query=session.createQuery("select a.marks"
					+ " from ExamAttendanceMarksBO a"
					+ " where a.courseId = :courseId and a.fromPercentage <= :totalCalculation"
					+ " and a.toPercentage >= :totalCalculation").setParameter("courseId", courseId).setParameter("totalCalculation",new BigDecimal(percentage));
		}

		List list = query.list();
		if (list != null && list.size() > 0) {
			marks = new Double(list.get(0).toString()).intValue();
		}
		return marks;
	}
	
	public boolean saveAttendanceMarks( List<ExamStudentAttendanceMarksCjcBO> boList) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		
		Transaction transaction = null;
		ExamStudentAttendanceMarksCjcBO bo;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamStudentAttendanceMarksCjcBO> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();
				ExamStudentAttendanceMarksCjcBO bo1=(ExamStudentAttendanceMarksCjcBO)session.createQuery("from ExamStudentAttendanceMarksCjcBO s" +
						" where s.classes.id="+bo.getClasses().getId()+" and s.student.id="+bo.getStudent().getId()+
						" and s.subject.id="+bo.getSubject().getId()).uniqueResult();
				if(bo1!=null){
					bo1.setAttendanceMarks(bo.getAttendanceMarks());
					bo1.setAttendancePercentage(bo.getAttendancePercentage());
					bo1.setClasses(bo.getClasses());
					bo1.setStudent(bo.getStudent());
					bo1.setSubject(bo.getSubject());
					bo1.setIsActive(bo.getIsActive());
					bo1.setCreatedBy(bo.getCreatedBy());
					bo1.setCreatedDate(new Date());
					bo1.setModifiedBy(bo.getModifiedBy());
					bo1.setLastModifiedDate(new Date());
					session.merge(bo1);
				}else{
					
					session.save(bo);
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	@Override
	public Map<Integer, SubjectRuleSettingsTO> getSubjectRuleSettings(
			int courseId, Integer year, Integer termNo) throws Exception {
		Map<Integer, SubjectRuleSettingsTO> map=new HashMap<Integer, SubjectRuleSettingsTO>();

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<SubjectRuleSettings> list =session.createQuery("from SubjectRuleSettings s where s.isActive=1" +
																" and s.academicYear=" +year+
																" and s.course.id=" +courseId+
																" and s.schemeNo=" +termNo+
																" and s.subject.isActive=1").list();
			if(list!=null && !list.isEmpty()){
				Iterator<SubjectRuleSettings> itr=list.iterator();
				while (itr.hasNext()) {
					SubjectRuleSettings bo=itr.next();
					SubjectRuleSettingsTO sto=new SubjectRuleSettingsTO();
					sto.setSubjectId(bo.getSubject().getId());
					sto.setIsTheoryPractical(bo.getSubject().getIsTheoryPractical());
					if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("T")){
						if(bo.getFinalTheoryInternalIsAttendance()!=null){
							sto.setTheoryAttendanceCheck(bo.getFinalTheoryInternalIsAttendance());
						}
						if(bo.getFinalTheoryInternalIsAssignment()!=null)
							sto.setTheoryAssignmentCheck(bo.getFinalTheoryInternalIsAssignment());
						if(bo.getFinalTheoryInternalIsSubInternal()!=null)
							sto.setTheoryInteralCheck(bo.getFinalTheoryInternalIsSubInternal());
						if(bo.getSelectBestOfTheoryInternal()!=null)
							sto.setTheoryBest(bo.getSelectBestOfTheoryInternal());
					}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("P")){
						if(bo.getFinalPracticalInternalIsAttendance()!=null){
							sto.setPracticalAttendanceCheck(bo.getFinalPracticalInternalIsAttendance());
						}
						if(bo.getFinalPracticalInternalIsAssignment()!=null)
							sto.setPracticalAssigntmentCheck(bo.getFinalPracticalInternalIsAssignment());
						if(bo.getFinalPracticalInternalIsSubInternal()!=null)
							sto.setPracticalInternalCheck(bo.getFinalPracticalInternalIsSubInternal());
						if(bo.getSelectBestOfPracticalInternal()!=null)
							sto.setPracticalBest(bo.getSelectBestOfPracticalInternal());
					}else if(bo.getSubject().getIsTheoryPractical().equalsIgnoreCase("B")){
						if(bo.getFinalTheoryInternalIsAttendance()!=null){
							sto.setTheoryAttendanceCheck(bo.getFinalTheoryInternalIsAttendance());
						}
						if(bo.getFinalTheoryInternalIsAssignment()!=null)
							sto.setTheoryAssignmentCheck(bo.getFinalTheoryInternalIsAssignment());
						if(bo.getSelectBestOfTheoryInternal()!=null)
							sto.setTheoryBest(bo.getSelectBestOfTheoryInternal());
						if(bo.getSelectBestOfPracticalInternal()!=null)
							sto.setPracticalBest(bo.getSelectBestOfPracticalInternal());
						
						if(bo.getFinalPracticalInternalIsAttendance()!=null){
							sto.setPracticalAttendanceCheck(bo.getFinalPracticalInternalIsAttendance());
						}
						if(bo.getFinalPracticalInternalIsAssignment()!=null)
							sto.setPracticalAssigntmentCheck(bo.getFinalPracticalInternalIsAssignment());
						if(bo.getFinalTheoryInternalIsSubInternal()!=null)
							sto.setTheoryInteralCheck(bo.getFinalTheoryInternalIsSubInternal());
						if(bo.getFinalPracticalInternalIsSubInternal()!=null)
							sto.setPracticalInternalCheck(bo.getFinalPracticalInternalIsSubInternal());
					}
					Set<SubjectRuleSettingsSubInternal> subSet=bo.getExamSubjectRuleSettingsSubInternals();
					if(subSet!=null && !subSet.isEmpty()){
						Iterator<SubjectRuleSettingsSubInternal> subItr=subSet.iterator();
						while (subItr.hasNext()) {
							SubjectRuleSettingsSubInternal subBo = (SubjectRuleSettingsSubInternal) subItr.next();
							if(subBo.getMaximumMark()!=null){
								String subType=subBo.getIsTheoryPractical().toString();
								if(subType.equalsIgnoreCase("t")){
									sto.setTheoryIndCheck(true);
								}
								if(subType.equalsIgnoreCase("p")){
									sto.setPracticalIndCheck(true);
								}
							}
						}
					}
					Set<SubjectRuleSettingsAttendance> attSet=bo.getExamSubjectRuleSettingsAttendances();
					if(attSet!=null && !attSet.isEmpty()){
						Iterator<SubjectRuleSettingsAttendance> attItr=attSet.iterator();
						while (attItr.hasNext()) {
							SubjectRuleSettingsAttendance attBo = attItr.next();
							String subType=attBo.getIsTheoryPractical().toString();
							if(subType.equalsIgnoreCase("t")){
								if(attBo.getIsCoCurricular()!=null)
									sto.setTheoryCoLeaveCheck(attBo.getIsCoCurricular());
								if(attBo.getIsLeave()!=null)
									sto.setTheoryLeaveCheck(attBo.getIsLeave());
								if(attBo.getAttendanceTypeId()!=null)
									sto.setTheoryAttTypeId(attBo.getAttendanceTypeId());
							}else if(subType.equalsIgnoreCase("p")){
								if(attBo.getIsCoCurricular()!=null)
									sto.setPracticalCoLeaveCheck(attBo.getIsCoCurricular());
								if(attBo.getIsLeave()!=null)
									sto.setTheoryLeaveCheck(attBo.getIsLeave());
								if(attBo.getAttendanceTypeId()!=null)
									sto.setPracticalAttTypeId(attBo.getAttendanceTypeId());
							}
						}
					}
					
					map.put(bo.getSubject().getId(),sto);
				}
			}
			return map;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
}
