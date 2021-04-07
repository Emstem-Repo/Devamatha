package com.kp.cms.transactionsimpl.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.PrincipalRemarks;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsSubInternalBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class StudentWiseAttendanceSummaryTransactionImpl implements IStudentWiseAttendanceSummaryTransaction {
	private static final Log log = LogFactory.getLog(StudentWiseAttendanceSummaryTransactionImpl.class);
	/** 
	 * @see com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction#getStudentWiseAttendanceSummaryInformation(java.lang.String)
	 */
	@Override
	public List<Object[]> getStudentWiseAttendanceSummaryInformation(
			String absenceInformationQuery) throws ApplicationException {
		Session session = null;
		List<Object[]> studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session
					.createQuery(absenceInformationQuery);
			studentSearchResult = studentQuery.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);			
		} finally {
			if (session != null) {
				session.flush();
				////session.close();
			}
		}
		return studentSearchResult;
	}

	/**
	 * Used to get Student by Regd/Roll No.
	 */
	public Student getStudentByRegdRollNo(int registerNo) throws Exception {
		log.info("Entering into StudentWiseAttendanceSummaryTransactionImpl of getStudentByRegdRollNo");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		Student student = null;
	
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query1 = session.createQuery("from Student s where s.isActive = 1 and s.id = :registerNo");				                          
		 query1.setInteger("registerNo", registerNo);
		 student = (Student) query1.uniqueResult();		 
		 return student;
		}
		catch (Exception e) {	
			log.error("Exception occured while getDetailByRoomTypeId in RoomTypeTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			////session.close();
		}
		log.info("Leaving into StudentWiseAttendanceSummaryTransactionImpl of getStudentByRegdRollNo");
		}
	}

	/**
	 * Used to get Students 
	 */
	public List<Object[]> getStudentBySearch(String dyanmicQuery) throws Exception {
		log.info("Entering into StudentWiseAttendanceSummaryTransactionImpl of getStudentBySearch");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery(dyanmicQuery);				                          
		 List<Object[]> studentList = query.list();
			 
		 return studentList;
		}
		catch (Exception e) {	
			log.error("Exception occured while getStudentBySearch in StudentWiseAttendanceSummaryTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			////session.close();
		}
		log.info("Leaving into StudentWiseAttendanceSummaryTransactionImpl of getStudentBySearch");
		}
	}

	/**
	 * Used to get absence period informations
	 */
	public List<AttendanceStudent> getAbsencePeriodDetails(
			String absenceSearchCriteria)throws Exception {
		log.info("Entering into StudentWiseAttendanceSummaryTransactionImpl of getAbsencePeriodDetails");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		try { 
		 //session = sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery(absenceSearchCriteria);				                          
		 List<AttendanceStudent> absencePeriodList = query.list();			 
		 return absencePeriodList;
		}
		catch (Exception e) {	
			log.error("Exception occured while getAbsencePeriodDetails in StudentWiseAttendanceSummaryTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			////session.close();
		}
		log.info("Leaving into StudentWiseAttendanceSummaryTransactionImpl of getAbsencePeriodDetails");
		}
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<StudentRemarks> getStaffRemarks(int studentId) throws Exception {
		Session session =null;
		
		List<StudentRemarks> list = null;
		try {
			/* SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			 Query query = session.createQuery(" from StudentRemarks s where s.student.id =" + studentId);
			
			 list=query.list();

			 session.flush();
			//session.close();
		 } catch (Exception e) {

			 session.flush();
			 ////session.close();
			 log.error("Error while getting getStaffRemarks.."+e);
			 throw  new ApplicationException(e); 
		 }
		return list;
	}
	/**
	 * 
	 * @param principalRemarks
	 * @return
	 * @throws Exception
	 */
	public boolean addPrincipalRemarks(PrincipalRemarks principalRemarks) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(principalRemarks);
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving addPrincipalRemarks data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving addPrincipalRemarks data..." ,e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public PrincipalRemarks getPricipalRemarks(int studentId) throws Exception {
		Session session =null;
		
		List<PrincipalRemarks> list = null;
		PrincipalRemarks principalRemarks = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();

			 Query query = session.createQuery(" from PrincipalRemarks s where s.student.id =" + studentId);
			
			 list=query.list();
			 if(list!= null && list.size() > 0){
				 principalRemarks =  list.get(0);
			 }

			 session.flush();
			//session.close();
		 } catch (Exception e) {

			 session.flush();
			 //session.close();
			 log.error("Error while getting getStaffRemarks.."+e);
			 throw  new ApplicationException(e); 
		 }
		return principalRemarks;
	}

	@Override
	public List<Integer> getPeriodList(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm)
			throws Exception {
		Session session =null;
		List<Integer> list = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("SELECT period.id  FROM (period period " +
			 		"INNER JOIN class_schemewise class_schemewise " +
			 		"ON (period.class_schemewise_id = class_schemewise.id))" +
			 		" INNER JOIN student student ON (student.class_schemewise_id = class_schemewise.id) WHERE student.id =" + Integer.parseInt(attendanceSummaryForm.getStudentID()));
			 list=query.list();
			 session.flush();
			//session.close();
		 } catch (Exception e) {
			 session.flush();
			 //session.close();
			 log.error("Error while getting periodlist for student.."+e);
			 throw  new ApplicationException(e); 
		 }
		 return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MarksEntryDetails> getStudentWiseExamMarkDetailsView(int studentId, int classId) throws ApplicationException {
		Session session = null;
		List<MarksEntryDetails> examMarksEntryDetailsBOList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("from MarksEntryDetails md where md.marksEntry.exam.examTypeID in (4,5) and md.marksEntry.student.id = "+ studentId +" and md.marksEntry.classes.id= " +classId);
			 examMarksEntryDetailsBOList = studentQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				////session.close();
			}
		}
		return examMarksEntryDetailsBOList;
	}
	
	public List<ExamMarksEntryDetailsBO> getStudentWiseExamMarkDetails(int studentId) throws ApplicationException {
		Session session = null;
		List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String query = "";
			boolean isCjc = CMSConstants.LINK_FOR_CJC;
			if(isCjc){
				query ="from ExamMarksEntryDetailsBO det where det.examMarksEntryBO.examDefinitionBO.examTypeID in(4,5) and det.examMarksEntryBO.studentId = " + studentId+" and det.subjectUtilBO.code !='VED'";
			}else{
				query = "from ExamMarksEntryDetailsBO det where det.examMarksEntryBO.examDefinitionBO.examTypeID in(4,5) and det.examMarksEntryBO.studentId = " + studentId;
			}
			Query studentQuery = session.createQuery(query);
			examMarksEntryDetailsBOList = studentQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				////session.close();
			}
		}
		return examMarksEntryDetailsBOList;
	}	
	/**
	 * 
	 * @param groupIds
	 * @return
	 * @throws ApplicationException
	 */
	public HashMap<Integer, String> getSubjectsBySubjectGroupId(String groupIds) throws ApplicationException {
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectsList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createQuery("from SubjectGroupSubjects grp where grp.subjectGroup.id in (" + groupIds + ") and grp.isActive=1 ");
			subjectGroupSubjectsList = subjectQuery.list();
			if(subjectGroupSubjectsList!= null && subjectGroupSubjectsList.size() > 0){  
				Iterator<SubjectGroupSubjects>  subItr = subjectGroupSubjectsList.iterator();
				while (subItr.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr
							.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getIsActive())
					subjectMap.put(subjectGroupSubjects.getSubject().getId(), subjectGroupSubjects.getSubject().getName());
				}
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}
	
	public HashMap<Integer, String> getSubjectsBySubjectGroupIdCJC(String groupIds) throws ApplicationException {
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectsList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createQuery("from SubjectGroupSubjects grp where grp.subjectGroup.id in (" + groupIds + ") and grp.isActive=1 and (grp.subject.isAdditionalSubject=0 or grp.subject.isAdditionalSubject is null)");
			subjectGroupSubjectsList = subjectQuery.list();
			if(subjectGroupSubjectsList!= null && subjectGroupSubjectsList.size() > 0){  
				Iterator<SubjectGroupSubjects>  subItr = subjectGroupSubjectsList.iterator();
				while (subItr.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr
							.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getIsActive())
					subjectMap.put(subjectGroupSubjects.getSubject().getId(), subjectGroupSubjects.getSubject().getName());
				}
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}	
	
	public HashMap<Integer, String> getSubjectsBySubjectGroupIdLogin(String groupIds) throws ApplicationException {
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectsList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createQuery("from SubjectGroupSubjects grp where grp.subjectGroup.id in (" + groupIds + ") and grp.isActive=1 and grp.subject.isAdditionalSubject=0 or grp.subject.isAdditionalSubject is null)");
			subjectGroupSubjectsList = subjectQuery.list();
			if(subjectGroupSubjectsList!= null && subjectGroupSubjectsList.size() > 0){
				Iterator<SubjectGroupSubjects>  subItr = subjectGroupSubjectsList.iterator();
				while (subItr.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr
							.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getIsActive())
					subjectMap.put(subjectGroupSubjects.getSubject().getId(), subjectGroupSubjects.getSubject().getName());
				}
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}
	
	public HashMap<Integer, String> getSubjectsBySubjectGroupIdAdditional(String groupIds) throws ApplicationException {
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectsList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createQuery("from SubjectGroupSubjects grp where grp.subjectGroup.id in (" + groupIds + ") and grp.isActive=1 and grp.subject.isAdditionalSubject=1");
			subjectGroupSubjectsList = subjectQuery.list();
			if(subjectGroupSubjectsList!= null && subjectGroupSubjectsList.size() > 0){
				Iterator<SubjectGroupSubjects>  subItr = subjectGroupSubjectsList.iterator();
				while (subItr.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr
							.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getIsActive())
					subjectMap.put(subjectGroupSubjects.getSubject().getId(), subjectGroupSubjects.getSubject().getName());
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}
	/**
	 * 
	 * @param classId
	 * @return
	 * @throws ApplicationException
	 */
	public List<Integer> getExamPublishedIds(int classId) throws ApplicationException {
		Session session = null;
		List<ExamPublishExamResultsBO> examPublishExamResultsBOList = null;
		List<Integer> publishedExamIds = new ArrayList<Integer>();
		try {
			session = HibernateUtil.getSession();
			Query examQuery = session.createQuery("from ExamPublishExamResultsBO res where res.classId = " + classId);
			examPublishExamResultsBOList = examQuery.list();
			
			if(examPublishExamResultsBOList!= null && examPublishExamResultsBOList.size() > 0){
				Iterator<ExamPublishExamResultsBO> itr = examPublishExamResultsBOList.iterator();
				while (itr.hasNext()) {
					ExamPublishExamResultsBO examPublishExamResultsBO = (ExamPublishExamResultsBO) itr
							.next();
					publishedExamIds.add(examPublishExamResultsBO.getExamId());
				}
			}
			
			

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return publishedExamIds;
	}
	
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws ApplicationException
	 */
	public Integer getClassId(int studentId) throws ApplicationException {
		Session session = null;
		
		Integer classId = -1;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("select classSchemewise.classes.id from Student st where st.id = " + studentId);

			List<Object[]> obj = studentQuery.list();
			if(obj!= null && obj.size() > 0){
				Iterator<Object[]> itr = obj.iterator();
				while (itr.hasNext()) {
					Object id = itr.next();
					if(id!=null)
					classId = Integer.parseInt(id.toString());
				}
			}

			return classId;
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}	
	
	public Integer getClassIdPrevious(int studentId, int schemeNo) throws ApplicationException {
		Session session = null;
		
		Integer classId = -1;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("select classId from ExamStudentPreviousClassDetailsBO where studentId = '"+studentId+"' and schemeNo='"+schemeNo+"'");

			List<Object[]> obj = studentQuery.list();
			if(obj!= null && obj.size() > 0){
				Iterator<Object[]> itr = obj.iterator();
				while (itr.hasNext()) {
					Object id = itr.next();
					if(id!=null)
					classId = Integer.parseInt(id.toString());
				}
			}

			return classId;
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public Map<Integer, Integer> getSubjectOrder(int courseId,int semNo,int semesterAcademicYear) throws ApplicationException {
		Session session = null;
		
		Map<Integer, Integer> orderMap = new HashMap<Integer, Integer>();
		try {
			session = HibernateUtil.getSession();
			String q="from ExamSubDefinitionCourseWiseBO e where e.courseId = "+ courseId;
			if(semNo>0)
				q= q+" and e.schemeNo=" +semNo;
			if(semesterAcademicYear>0)
				q= q+" and e.academicYear=" +semesterAcademicYear;
			Query query = session.createQuery(q);

			List<ExamSubDefinitionCourseWiseBO> examSubDefinitionCourseWiseBOList = query.list();
			if(examSubDefinitionCourseWiseBOList!= null && examSubDefinitionCourseWiseBOList.size() > 0){
				Iterator<ExamSubDefinitionCourseWiseBO> itr = examSubDefinitionCourseWiseBOList.iterator();
				while (itr.hasNext()) {
					ExamSubDefinitionCourseWiseBO examSubDefinitionCourseWiseBO = itr.next();
					orderMap.put(examSubDefinitionCourseWiseBO.getSubjectId(), examSubDefinitionCourseWiseBO.getSubjectOrder());
				}
			}
			return orderMap;
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/**
	 * 
	 * @param groupIds
	 * @return
	 * @throws ApplicationException
	 */
	public HashMap<Integer, String> getSubjectCodesBySubjectGroupId(String groupIds) throws ApplicationException {
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectsList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createQuery("from SubjectGroupSubjects grp where grp.subjectGroup.id in (" + groupIds + ") and grp.isActive=1");
			subjectGroupSubjectsList = subjectQuery.list();
			if(subjectGroupSubjectsList!= null && subjectGroupSubjectsList.size() > 0){
				Iterator<SubjectGroupSubjects>  subItr = subjectGroupSubjectsList.iterator();
				while (subItr.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr
							.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getIsActive())
					subjectMap.put(subjectGroupSubjects.getSubject().getId(), subjectGroupSubjects.getSubject().getCode());
				}
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}
	public HashMap<Integer, String> getSubjectCodesBySubjectGroupIdCJC(String groupIds) throws ApplicationException {
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectsList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createQuery("from SubjectGroupSubjects grp where grp.subjectGroup.id in (" + groupIds + ") and grp.isActive=1 and (grp.subject.isAdditionalSubject=0 or grp.subject.isAdditionalSubject is null)");
			subjectGroupSubjectsList = subjectQuery.list();
			if(subjectGroupSubjectsList!= null && subjectGroupSubjectsList.size() > 0){
				Iterator<SubjectGroupSubjects>  subItr = subjectGroupSubjectsList.iterator();
				while (subItr.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr
							.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getIsActive())
					subjectMap.put(subjectGroupSubjects.getSubject().getId(), subjectGroupSubjects.getSubject().getCode());
				}
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction#getMaxMarksFromExamSubjectRuleSettingsSubInternal(int, int)
	 */
	@Override
	public List<ExamSubjectRuleSettingsSubInternalBO> getMaxMarksFromExamSubjectRuleSettingsSubInternal(
			int internalExamTypeId, int subjectId, String courseId, int studentId)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query stuQuery = session.createQuery("from Student s where s.id = '"+studentId+"'");
			List<Student> studentList =  stuQuery.list();
			Iterator<Student> stuIterator = studentList.iterator();
			String academicYear = "";
			String semNo = "";
			while (stuIterator.hasNext()) {
				Student student = (Student) stuIterator.next();
				academicYear = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
				semNo = String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getSemesterYearNo());
			}
			Query examQuery = session.createQuery("select e.id from ExamSubjectRuleSettingsBO e where e.academicYear = '"+academicYear+"'"+
												  " and e.schemeNo = '"+semNo+"' and e.courseId = '"+courseId+"' and e.subjectId = '"+subjectId+"'");
			List<Integer> examSubjectRuleSettingsId = examQuery.list();
			if(examSubjectRuleSettingsId!=null && !examSubjectRuleSettingsId.isEmpty()){
			Query query = session.createQuery("from ExamSubjectRuleSettingsSubInternalBO e where e.examSubjectRuleSettingsBO.id in (:examSubjectRuleIdList) and e.internalExamTypeId = '"+internalExamTypeId+"'");
			query.setParameterList("examSubjectRuleIdList", examSubjectRuleSettingsId);
			List<ExamSubjectRuleSettingsSubInternalBO> examSubjectRuleSettingsSubInternalBOs = query.list();
			return examSubjectRuleSettingsSubInternalBOs;
			}else
				return new ArrayList<ExamSubjectRuleSettingsSubInternalBO>();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction#getExamDefinationList(java.util.List)
	 */
	@Override
	public List<ExamDefinitionBO> getExamDefinationList(List<String> examCodeList)
			throws Exception {
		Session session = null;
		List<ExamDefinitionBO> examDefinationBOList = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query examQuery = session.createQuery("select e from ExamDefinitionBO e where e.examCode in (:ExamCode) order by e.year,month  ");
			examQuery.setParameterList("ExamCode", examCodeList);
			examDefinationBOList = examQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
		return examDefinationBOList;
		
	}

	@Override
	public List<Subject> getSubjectsListForStudent(int studentId)
			throws Exception {
		Session session = null;
		List<Subject> subject = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query examQuery = session.createQuery("from Student s where s.id ='"+studentId+"'");
			Student student= (Student)examQuery.uniqueResult();
			int applnId = student.getAdmAppln().getId();
			String subQuery =   "select sub.subject from SubjectGroupSubjects sub"+ 
								" where sub.subjectGroup.id in "+
								" (select app.subjectGroup.id from ApplicantSubjectGroup app where app.admAppln.id = '"+applnId+"')"+
								" and sub.isActive = 1";
			Query subjectQuery = session.createQuery(subQuery);
			subject = subjectQuery.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return subject;
	}
	
	public HashMap<Integer, String> getSubjectCodesBySubjectGroupIdCJCAdditional(String groupIds) throws ApplicationException {
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectsList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createQuery("from SubjectGroupSubjects grp where grp.subjectGroup.id in (" + groupIds + ") and grp.isActive=1 and grp.subject.isAdditionalSubject=1");
			subjectGroupSubjectsList = subjectQuery.list();
			if(subjectGroupSubjectsList!= null && subjectGroupSubjectsList.size() > 0){
				Iterator<SubjectGroupSubjects>  subItr = subjectGroupSubjectsList.iterator();
				while (subItr.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr
							.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getIsActive())
					subjectMap.put(subjectGroupSubjects.getSubject().getId(), subjectGroupSubjects.getSubject().getCode());
				}
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}
	public HashMap<Integer, String> getSubjectsBySubjectGroupIdCJCAdditional(String groupIds) throws ApplicationException {
		Session session = null;
		List<SubjectGroupSubjects> subjectGroupSubjectsList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createQuery("from SubjectGroupSubjects grp where grp.subjectGroup.id in (" + groupIds + ") and grp.isActive=1 and grp.subject.isAdditionalSubject=1 ");
			subjectGroupSubjectsList = subjectQuery.list();
			if(subjectGroupSubjectsList!= null && subjectGroupSubjectsList.size() > 0){  
				Iterator<SubjectGroupSubjects>  subItr = subjectGroupSubjectsList.iterator();
				while (subItr.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr
							.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getIsActive())
					subjectMap.put(subjectGroupSubjects.getSubject().getId(), subjectGroupSubjects.getSubject().getName());
				}
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}	
	
	@SuppressWarnings("unchecked")
	public List<MarksEntryDetails> getStudentWiseExamMarkDetailsViewAdditional(int studentId, int classId) throws ApplicationException {
		Session session = null;
		List<MarksEntryDetails> examMarksEntryDetailsBOList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("from MarksEntryDetails md where md.marksEntry.exam.examTypeID in (4,5) and md.marksEntry.student.id = "+ studentId +" and md.marksEntry.classes.id= " +classId);
			 examMarksEntryDetailsBOList = studentQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				////session.close();
			}
		}
		return examMarksEntryDetailsBOList;
	}

	/* (non-Javadoc)
	 * retuns student semester and semester's academic year
	 * @see com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction#getSudentSemAcademicYear(int)
	 */
	@Override
	public Student getSudentSemAcademicYear(int studentId) throws Exception {
		Session session = null;
		Student stu = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("from Student s where s.id="+studentId);
			stu = (Student)studentQuery.uniqueResult();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				////session.close();
			}
		}
		return stu;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction#getClassSchemeSemAcademicYear(int)
	 */
	@Override
	public ClassSchemewise getClassSchemeSemAcademicYear(int classId) throws Exception {
		Session session = null;
		ClassSchemewise clas = null;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("from ClassSchemewise s where s.classes.id="+classId);
			clas = (ClassSchemewise)studentQuery.uniqueResult();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return clas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> setPreviousClassId(String studentid) throws Exception {
		Session session = null;
		List<Object[]> classesList = null;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(" select classes.id,classes.name from student inner join EXAM_student_previous_class_details on EXAM_student_previous_class_details.student_id = student.id" +
					                                 " inner join classes ON EXAM_student_previous_class_details.class_id = classes.id " +
					                                 " where student.id="+studentid+" and classes.is_active=1");
			classesList = query.list();
			if(classesList!= null && classesList.size() > 0){  
				Iterator<Object[]>  claItr = classesList.iterator();
				while (claItr.hasNext()) {
					Object[] classAll = (Object[]) claItr.next();
					if(classAll!=null)
						classMap.put(Integer.parseInt(classAll[0].toString()),classAll[1].toString());
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		classMap=CommonUtil.sortMapByValueDesc(classMap);
		return (HashMap<Integer, String>) classMap;
	}

	@Override
	public HashMap<Integer, String> getSubjectsByClassId(String classesId)throws Exception {
		Session session = null;
		List<Object[]> objList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createSQLQuery(" select subject.id,subject.name from attendance inner join attendance_class on attendance_class.attendance_id = attendance.id" +
					                                    " inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id inner join classes on class_schemewise.class_id = classes.id " +
					                                    " inner join subject ON attendance.subject_id = subject.id inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " +
					                                    " and subject_group_subjects.is_active=1 where attendance.is_canceled=0 and (subject.is_additional_subject=0 or subject.is_additional_subject=null)" +
					                                    " and classes.id="+classesId+" group by subject.id ");
			objList = subjectQuery.list();
			if(objList!= null && objList.size() > 0){
				Iterator<Object[]>  subItr = objList.iterator();
				while (subItr.hasNext()) {
					Object[] obj = (Object[]) subItr.next();
					if(obj!=null)
					subjectMap.put(Integer.parseInt(obj[0].toString()),obj[1].toString());
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}

	@Override
	public HashMap<Integer, String> getSubjectsByClassIdAdditional(	String classesId) throws Exception {
		Session session = null;
		List<Object[]> objList = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			session = HibernateUtil.getSession();
			Query subjectQuery = session.createSQLQuery(" select subject.id,subject.name from attendance inner join attendance_class on attendance_class.attendance_id = attendance.id" +
					                                    " inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id inner join classes on class_schemewise.class_id = classes.id " +
					                                    " inner join subject ON attendance.subject_id = subject.id inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " +
					                                    " and subject_group_subjects.is_active=1 where attendance.is_canceled=0 and subject.is_additional_subject=1 " +
					                                    " and classes.id="+classesId+" group by subject.id ");
			objList = subjectQuery.list();
			if(objList!= null && objList.size() > 0){
				Iterator<Object[]>  subItr = objList.iterator();
				while (subItr.hasNext()) {
					Object[] obj = (Object[]) subItr.next();
					if(obj!=null)
					subjectMap.put(Integer.parseInt(obj[0].toString()),obj[1].toString());
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return (HashMap<Integer, String>) subjectMap;
	}

	@Override
	public List<String> getPreviousPeriodList(	StudentWiseAttendanceSummaryForm attendanceSummaryForm)	throws Exception {
		Session session =null;
		List<String> list = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery(" SELECT period.period_name from student inner join attendance_student on attendance_student.student_id = student.id inner join attendance ON attendance_student.attendance_id = attendance.id " +
			 		" inner join attendance_class on attendance_class.attendance_id = attendance.id inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id inner join classes on class_schemewise.class_id = classes.id " +
			 		" inner join subject ON attendance.subject_id = subject.id " +
			 		" inner join attendance_period on attendance_period.attendance_id = attendance.id inner join period ON attendance_period.period_id = period.id /* and period.class_schemewise_id = class_schemewise.id */" +
			 		" where attendance_student.is_present=0 and attendance.is_canceled=0" +
			 		" and classes.id="+attendanceSummaryForm.getClassesId()+
			 		" and student.id ="+Integer.parseInt(attendanceSummaryForm.getStudentID())+
			 		" and subject.id ="+Integer.parseInt(attendanceSummaryForm.getSubjectId())+
			 		" group by period.period_name " );
			 list=query.list();
			 session.flush();
			//session.close();
		 } catch (Exception e) {
			 session.flush();
			 //session.close();
			 log.error("Error while getting periodlist for student.."+e);
			 throw  new ApplicationException(e); 
		 }
		 return list;
	}

	@Override
	public String getClassesName (String classesId) throws Exception {
		Session session =null;
		String str ="";
		try {
			session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("select classes.name from classes where classes.id="+classesId+" and classes.is_active=1");
			 str=(String) query.uniqueResult();
			 session.flush();
		 } catch (Exception e) {
			 session.flush();
			 throw  new ApplicationException(e); 
		 }
		 return str;
	}
	
	public List<Object[]> getAttendanceDateAndDayOnClass(int classesId) throws Exception{
		Session session = null;
		List<Object[]> attDateList = new LinkedList<Object[]>();
		LinkedHashMap<String, String> attDateMap = new LinkedHashMap<String, String>();
		try {
			session = HibernateUtil.getSession();
			session.close();
			session = HibernateUtil.getSession();
			String dyanmicQuery=" select distinct concat( year(attendance.attendance_date),'-',month(attendance.attendance_date),'-',day(attendance.attendance_date)) as dt1," +
								" dayname(attendance.attendance_date) as d, concat( day(attendance.attendance_date),'/',month(attendance.attendance_date),'/',year(attendance.attendance_date)) as dt2" +
								" from attendance_class attendance_class " +
								" inner join attendance attendance ON attendance_class.attendance_id = attendance.id" +
								" INNER JOIN class_schemewise class_schemewise on class_schemewise.id=attendance_class.class_schemewise_id" +
								" INNER JOIN classes classes ON (class_schemewise.class_id = classes.id)" +
								" where classes.id="+classesId+" and attendance.is_canceled=0 order by attendance.attendance_date";
								Query subjectQuery = session.createSQLQuery(dyanmicQuery);
								attDateList = subjectQuery.list();
			

		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return attDateList;
	}
	
	

	public List<Object[]> getTeacherNotTakenAttendanceSummary(int classesId,int studentId,List<Object[]> attDates)throws Exception{
		
		Session session = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 session.close();
		 session = HibernateUtil.getSession();
		 List<Object[]> teacherNotTakenAttendanceSummary=new LinkedList<Object[]>();
		 Iterator<Object[]>  subItr = attDates.iterator();
			while (subItr.hasNext()) {
				Object[] obj =  subItr.next();
				
			
			
		 String dyanmicQuery="SELECT tt_period_week.week_day,users.user_name,subject.name,period.period_name " +
	 
	 	 "FROM   tt_subject_batch tt_subject_batch " +

		"INNER JOIN tt_period_week tt_period_week  ON (tt_subject_batch.tt_period_id = tt_period_week.id) " +
		"INNER JOIN tt_class tt_class  ON (tt_period_week.tt_class_id = tt_class.id) " +
		"INNER JOIN tt_users tt_users ON (tt_users.tt_subject_id = tt_subject_batch.id) " +
		"inner join class_schemewise class_schemewise on (tt_class.class_schemewise_id=class_schemewise.id) " +
		"INNER JOIN classes classes ON (class_schemewise.class_id = classes.id) " +
		"INNER JOIN course course ON (classes.course_id = course.id) " +
		"INNER JOIN program program ON (course.program_id = program.id) " +
		"INNER JOIN program_type program_type ON (program.program_type_id = program_type.id) " +
		"inner join subject subject on(subject.id=tt_subject_batch.subject_id) " +
		"INNER JOIN users users ON (tt_users.user_id = users.id) " +
	 	"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
		"Inner join student student on student.class_schemewise_id=class_schemewise.id " +
	   	"inner join period period  on period.id=tt_period_week.period_id " +
		 
		 
		" where period.id not in (SELECT  period.id  FROM    period period " +
                   
				"INNER JOIN class_schemewise class_schemewise ON (period.class_schemewise_id = class_schemewise.id) " +
            	"INNER JOIN attendance_period attendance_period ON (attendance_period.period_id = period.id) " +
            	"INNER JOIN attendance attendance ON (attendance_period.attendance_id = attendance.id) " +
        		"INNER JOIN attendance_class attendance_class ON (attendance_class.attendance_id = attendance.id) " +
            	"INNER JOIN classes classes ON (class_schemewise.class_id = classes.id) " +
				"INNER JOIN attendance_instructor attendance_instructor ON (attendance_instructor.attendance_id = attendance.id) " +
				"INNER JOIN users users ON (attendance_instructor.users_id = users.id) " +
				"INNER JOIN attendance_student attendance_student on attendance_student.attendance_id=attendance.id " +
				"Inner join student student on student.id=attendance_student.student_id " +
				" where attendance.attendance_date='"+obj[0].toString()+"'  and classes.id="+classesId+" and student.id="+studentId+")" +
								
		" and  tt_subject_batch.is_active=1 and  users.is_active=1 and  period.is_active=1 and  classes.is_active=1 " +
		" and  tt_class.is_active=1  and  student.id="+studentId+" and classes.id="+classesId+" and tt_period_week.week_day='"+obj[1].toString()+"'" +
		" group by tt_period_week.id order by period.period_name";
		 
		 Query query = session.createSQLQuery(dyanmicQuery);				                          
		 List<Object[]> studentList = query.list();
		
		 if(studentList!=null && studentList.size()!=0){
			 Iterator<Object[]>  attItr = studentList.iterator();
				while (attItr.hasNext()) {
					Object[] o =  attItr.next();
				
					Object[] o1=new Object[5];
			 
					o1[0]=o[0].toString();
					o1[1]=o[1].toString();
					o1[2]=o[2].toString();
					o1[3]=o[3].toString();
					o1[4]=obj[2].toString();
					teacherNotTakenAttendanceSummary.add(o1);
				}
		 }
		 
		 
		}
			
			
		 return teacherNotTakenAttendanceSummary;
		}
		catch (Exception e) {	
			log.error("Exception occured while getTeacherNotTakenAttendanceSummary in StudentWiseAttendanceSummaryTransactionImpl :"+e);
			throw  e;
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			////session.close();
		}
		log.info("Leaving into StudentWiseAttendanceSummaryTransactionImpl of getTeacherNotTakenAttendanceSummary");
		}
		
		
	}
	
	public List<Object[]> getAttendanceDateAndDayOnClassTeacher(int classesId) throws Exception{
		Session session = null;
		List<Object[]> attDateList = new LinkedList<Object[]>();
		LinkedHashMap<String, String> attDateMap = new LinkedHashMap<String, String>();
		try {
			session = HibernateUtil.getSession();
			session.close();
			session = HibernateUtil.getSession();
			String dyanmicQuery=" select distinct concat( year(attendance.attendance_date),'-',month(attendance.attendance_date),'-',day(attendance.attendance_date)) as dt1," +
								" dayname(attendance.attendance_date) as d, concat( day(attendance.attendance_date),'/',month(attendance.attendance_date),'/',year(attendance.attendance_date)) as dt2" +
								" from attendance_class attendance_class " +
								" inner join attendance attendance ON attendance_class.attendance_id = attendance.id" +
								" INNER JOIN class_schemewise class_schemewise on class_schemewise.id=attendance_class.class_schemewise_id" +
								" INNER JOIN classes classes ON (class_schemewise.class_id = classes.id)" +
								" where class_schemewise.id="+classesId+" and attendance.is_canceled=0 order by attendance.attendance_date";
								Query subjectQuery = session.createSQLQuery(dyanmicQuery);
								attDateList = subjectQuery.list();
			

		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return attDateList;
	}
	
	
	public List<Object[]> getClassTeacherNotTakenAttendanceSummary(int classesId,int userId,List<Object[]> attDates)throws Exception{
		
		Session session = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 session.close();
		 session = HibernateUtil.getSession();
		 String dyanmicQuery=null;
		 List<Object[]> teacherNotTakenAttendanceSummary=new LinkedList<Object[]>();
		 Iterator<Object[]>  subItr = attDates.iterator();
			while (subItr.hasNext()) {
				Object[] obj =  subItr.next();
				
			
			
		 dyanmicQuery="SELECT tt_period_week.week_day as wday,users.user_name as uname,subject.name as sname,period.period_name as pname,classes.name as cname " +
	 
	 	 "FROM   tt_subject_batch tt_subject_batch " +

		"INNER JOIN tt_period_week tt_period_week  ON (tt_subject_batch.tt_period_id = tt_period_week.id) " +
		"INNER JOIN tt_class tt_class  ON (tt_period_week.tt_class_id = tt_class.id) " +
		"INNER JOIN tt_users tt_users ON (tt_users.tt_subject_id = tt_subject_batch.id) " +
		"inner join class_schemewise class_schemewise on (tt_class.class_schemewise_id=class_schemewise.id) " +
		"INNER JOIN classes classes ON (class_schemewise.class_id = classes.id) " +
		"INNER JOIN course course ON (classes.course_id = course.id) " +
		"INNER JOIN program program ON (course.program_id = program.id) " +
		"INNER JOIN program_type program_type ON (program.program_type_id = program_type.id) " +
		"inner join subject subject on(subject.id=tt_subject_batch.subject_id) " +
		"INNER JOIN users users ON (tt_users.user_id = users.id) " +
	 	"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
		"Inner join student student on student.class_schemewise_id=class_schemewise.id " +
	   	"inner join period period  on period.id=tt_period_week.period_id " +
		 
		 
		" where period.id not in (SELECT  period.id  FROM    period period " +
                   
				"INNER JOIN class_schemewise class_schemewise ON (period.class_schemewise_id = class_schemewise.id) " +
            	"INNER JOIN attendance_period attendance_period ON (attendance_period.period_id = period.id) " +
            	"INNER JOIN attendance attendance ON (attendance_period.attendance_id = attendance.id) " +
        		"INNER JOIN attendance_class attendance_class ON (attendance_class.attendance_id = attendance.id) " +
            	"INNER JOIN classes classes ON (class_schemewise.class_id = classes.id) " +
				"INNER JOIN attendance_instructor attendance_instructor ON (attendance_instructor.attendance_id = attendance.id) " +
				"INNER JOIN users users ON (attendance_instructor.users_id = users.id) " +
				"INNER JOIN attendance_student attendance_student on attendance_student.attendance_id=attendance.id " +
				"Inner join student student on student.id=attendance_student.student_id " +
				" where attendance.attendance_date='"+obj[0].toString()+"'  and class_schemewise.id="+classesId+" )" +
								
		" and  tt_subject_batch.is_active=1 and  users.is_active=1 and  period.is_active=1 and  classes.is_active=1 " +
		" and  tt_class.is_active=1  and class_schemewise.id="+classesId+" and tt_period_week.week_day='"+obj[1].toString()+"'" ;
		if(userId!=0){
			 dyanmicQuery=dyanmicQuery+" and users.id="+userId;
		}
		 dyanmicQuery=dyanmicQuery+" group by tt_period_week.id order by period.period_name";
		 
		 Query query = session.createSQLQuery(dyanmicQuery);				                          
		 List<Object[]> studentList = query.list();
		
		 if(studentList!=null && studentList.size()!=0){
			 Iterator<Object[]>  attItr = studentList.iterator();
				while (attItr.hasNext()) {
					Object[] o =  attItr.next();
				
					Object[] o1=new Object[6];
			 
					o1[0]=o[0].toString();
					o1[1]=o[1].toString();
					o1[2]=o[2].toString();
					o1[3]=o[3].toString();
					o1[4]=obj[2].toString();
					o1[5]=o[4].toString();
					teacherNotTakenAttendanceSummary.add(o1);
				}
		 }
		 
		 
		}
			
			
		 return teacherNotTakenAttendanceSummary;
		}
		catch (Exception e) {	
			log.error("Exception occured while getTeacherNotTakenAttendanceSummary in StudentWiseAttendanceSummaryTransactionImpl :"+e);
			throw  e;
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			////session.close();
		}
		log.info("Leaving into StudentWiseAttendanceSummaryTransactionImpl of getTeacherNotTakenAttendanceSummary");
		}
		
		
	}

	@Override
	public List<Object[]> getAttendenceRecord(int studentId, String courseId,int classId, int acadamicYear) throws Exception {
		Session session = null;
		List<Object[]> totalAttendenceList = new ArrayList<Object[]>();
		try{
			session = HibernateUtil.getSession();
			StringBuilder sql = new StringBuilder("SELECT");
			sql.append(" CLASS_MAIN.attendance_date,");
			sql.append(" CLASS_MAIN.attid,");
			sql.append(" CLASS_MAIN.subject_id,");
			sql.append(" CLASS_MAIN.is_cocurricular_leave,");
			sql.append(" CLASS_MAIN.is_present,");
			sql.append(" CLASS_MAIN.hoursheld,");
			sql.append(" CLASS_MAIN.student_id,");
			sql.append(" CLASS_MAIN.class_schemewise_id,");
			sql.append(" CLASS_MAIN.class_id1,");
			sql.append(" CLASS_MAIN.class_name1,");
			sql.append(" CLASS_MAIN.term_number,");
			sql.append(" CLASS_MAIN.academic_year,");
			sql.append(" CLASS_MAIN.course_id1,");
			sql.append(" CLASS_MAIN.course_name1,");
			sql.append(" CLASS_MAIN.student_id1,");
			sql.append(" CLASS_MAIN.register_no,");
			sql.append(" CLASS_MAIN.roll_no,");
			sql.append(" CLASS_MAIN.first_name,");
			sql.append(" CLASS_MAIN.MaxHrs,");
			sql.append(" sum(CLASS_MAIN.hours_held) as taken_hrs,");
			sql.append(" sum(if(CLASS_MAIN.is_present=1 || CLASS_MAIN.is_cocurricular_leave=1,CLASS_MAIN.hours_held,0)) as present_hrs,");
			sql.append(" sum(if(CLASS_MAIN.is_present=0 && IFNULL(CLASS_MAIN.is_cocurricular_leave,0)=0,CLASS_MAIN.hours_held,0)) as absent_hrs,");
			sql.append(" if(0=sum(if(CLASS_MAIN.is_present=0 && IFNULL(CLASS_MAIN.is_cocurricular_leave,0)=0,CLASS_MAIN.hours_held,0)),1,0) as HA,");
			sql.append(" if(1=sum(if(CLASS_MAIN.is_present=0 && IFNULL(CLASS_MAIN.is_cocurricular_leave,0)=0,CLASS_MAIN.hours_held,0)),0.5,0) as HA_halfday");
			sql.append(" FROM");
			sql.append(" ( ");
			sql.append(" select attendance.attendance_date,");
			sql.append(" attendance.id as attid,");
			sql.append(" attendance.subject_id,");
			sql.append(" attendance.hours_held,");
			sql.append(" attendance_student.is_cocurricular_leave,");
			sql.append(" attendance_student.is_present,");
			sql.append(" 1 as hoursheld,");
			sql.append(" attendance_student.student_id,");
			sql.append(" attendance_class.class_schemewise_id,");
			sql.append(" classes.id as class_id1,");
			sql.append(" classes.name as class_name1,");
			sql.append(" classes.term_number,");
			sql.append(" curriculum_scheme_duration.academic_year,");
			sql.append(" course.id as course_id1,");
			sql.append(" course.name as course_name1,");
			sql.append(" student.id as student_id1,");
			sql.append(" student.register_no,");
			sql.append(" student.roll_no,");
			sql.append(" personal_data.first_name,");
			sql.append(" 6 as MaxHrs");
			sql.append(" from");
			sql.append(" attendance");
			sql.append(" inner join attendance_student on attendance_student.attendance_id = attendance.id");
			sql.append(" inner join attendance_class on attendance_class.attendance_id = attendance.id");
			sql.append(" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join classes ON class_schemewise.class_id = classes.id");
			sql.append(" inner join student on attendance_student.student_id=student.id");
			sql.append(" and student.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join adm_appln on student.adm_appln_id=adm_appln.id");
			sql.append(" inner join personal_data on adm_appln.personal_data_id= personal_data.id");
			sql.append(" inner join course on classes.course_id = course.id");
			sql.append(" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id");
			sql.append(" where attendance.is_canceled=0");
			sql.append(" and student.is_admitted=1");
			sql.append(" and adm_appln.is_cancelled=0");
			sql.append(" and curriculum_scheme_duration.academic_year= :academicYear");
			sql.append(" and course.id= :courseId");
			sql.append(" and classes.id= :classId");
			sql.append(" and attendance_student.student_id = :studentId");
			sql.append(" group by attendance.id,attendance_student.student_id,attendance_class.class_schemewise_id,attendance.attendance_date");
			sql.append(" UNION");
			sql.append(" select attendance.attendance_date,");
			sql.append(" attendance.id as attid,");
			sql.append(" attendance.subject_id,");
			sql.append(" attendance.hours_held,");
			sql.append(" attendance_student.is_cocurricular_leave,");
			sql.append(" attendance_student.is_present,");
			sql.append(" 1 as hoursheld,");
			sql.append(" attendance_student.student_id,");
			sql.append(" attendance_class.class_schemewise_id,");
			sql.append(" classes.id as class_id1,");
			sql.append(" classes.name as class_name1,");
			sql.append(" classes.term_number,");
			sql.append(" curriculum_scheme_duration.academic_year,");
			sql.append(" course.id as course_id1,");
			sql.append(" course.name as course_name1,");
			sql.append(" student.id as student_id1,");
			sql.append(" student.register_no,");
			sql.append(" student.roll_no,");
			sql.append(" personal_data.first_name,");
			sql.append(" 6 as MaxHrs");
			sql.append(" from");
			sql.append(" attendance");
			sql.append(" inner join attendance_student on attendance_student.attendance_id = attendance.id");
			sql.append(" inner join attendance_class on attendance_class.attendance_id = attendance.id");
			sql.append(" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join classes ON class_schemewise.class_id = classes.id");
			sql.append(" inner join EXAM_student_previous_class_details");
			sql.append(" on EXAM_student_previous_class_details.class_id = classes.id");
			sql.append(" and EXAM_student_previous_class_details.student_id = attendance_student.student_id");
			sql.append(" inner join student on attendance_student.student_id=student.id");
			sql.append(" inner join adm_appln on student.adm_appln_id=adm_appln.id");
			sql.append(" inner join personal_data on adm_appln.personal_data_id= personal_data.id");
			sql.append(" inner join course on classes.course_id = course.id");
			sql.append(" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id");
			sql.append(" where attendance.is_canceled=0");
			sql.append(" and student.is_admitted=1");
			sql.append(" and adm_appln.is_cancelled=0");
			sql.append(" and curriculum_scheme_duration.academic_year= :academicYear");
			sql.append(" and course.id= :courseId");
			sql.append(" and classes.id= :classId");
			sql.append(" and attendance_student.student_id = :studentId");
			sql.append(" group by attendance.id,attendance_student.student_id,attendance_class.class_schemewise_id,attendance.attendance_date");
			sql.append(" ) as CLASS_MAIN");
			sql.append(" group by CLASS_MAIN.student_id,CLASS_MAIN.class_schemewise_id,CLASS_MAIN.attendance_date");
			Query query = session.createSQLQuery(sql.toString())
							.setInteger("courseId", Integer.parseInt(courseId))
							.setInteger("classId", classId)
							.setInteger("studentId", studentId)
							.setString("academicYear", String.valueOf(acadamicYear));
			 totalAttendenceList = query.list();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return totalAttendenceList;
	}

	@Override
	public List<Object[]> getSubjectWiseAttendenceInHrs(int studentId,String courseId, int classId, int acadamicYear, Integer subjectId)
			throws Exception {
		Session session = null;
		List<Object[]> subjectWiseAttendenceHrsList = new ArrayList<Object[]>();
		try{
			session = HibernateUtil.getSession();
			StringBuilder sql = new StringBuilder("SELECT");
			sql.append(" CLASS_MAIN.attendance_date,");
			sql.append(" CLASS_MAIN.attid,");
			sql.append(" CLASS_MAIN.subject_id,");
			sql.append(" CLASS_MAIN.subject,");
			sql.append(" CLASS_MAIN.is_cocurricular_leave,");
			sql.append(" CLASS_MAIN.is_present,");
			sql.append(" CLASS_MAIN.student_id,");
			sql.append(" CLASS_MAIN.class_schemewise_id,");
			sql.append(" CLASS_MAIN.class_id1,");
			sql.append(" CLASS_MAIN.class_name1,");
			sql.append(" CLASS_MAIN.term_number,");
			sql.append(" CLASS_MAIN.academic_year,");
			sql.append(" CLASS_MAIN.course_id1,");
			sql.append(" CLASS_MAIN.course_name1,");
			sql.append(" CLASS_MAIN.student_id1,");
			sql.append(" CLASS_MAIN.register_no,");
			sql.append(" CLASS_MAIN.roll_no,");
			sql.append(" CLASS_MAIN.first_name,");
			sql.append(" sum(CLASS_MAIN.hours_held) as taken_hrs,");
			sql.append(" sum(CLASS_MAIN.present) as HA");
			sql.append(" from");
			sql.append(" (");
			sql.append(" select attendance.attendance_date,");
			sql.append(" attendance.id as attid,");
			sql.append(" attendance.subject_id,");
			sql.append(" attendance.hours_held,");
			sql.append(" attendance_student.is_cocurricular_leave,");
			sql.append(" attendance_student.is_present,");
			sql.append(" attendance_student.student_id,");
			sql.append(" attendance_class.class_schemewise_id,");
			sql.append(" classes.id as class_id1,");
			sql.append(" classes.name as class_name1,");
			sql.append(" classes.term_number,");
			sql.append(" curriculum_scheme_duration.academic_year,");
			sql.append(" course.id as course_id1,");
			sql.append(" course.name as course_name1,");
			sql.append(" student.id as student_id1,");
			sql.append(" student.register_no,");
			sql.append(" student.roll_no,");
			sql.append(" personal_data.first_name,");
			sql.append(" subject.id as sub_id,");
			sql.append(" subject.name as subject,");
			sql.append(" if((attendance_student.is_present=1 || attendance_student.is_cocurricular_leave=1),attendance.hours_held,0) as present");
			sql.append(" from");
			sql.append(" attendance");
			sql.append(" inner join attendance_student on attendance_student.attendance_id = attendance.id");
			sql.append(" inner join attendance_class on attendance_class.attendance_id = attendance.id");
			sql.append(" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join classes ON class_schemewise.class_id = classes.id");
			sql.append(" inner join student on attendance_student.student_id=student.id");
			sql.append(" and student.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join adm_appln on student.adm_appln_id=adm_appln.id");
			sql.append(" inner join personal_data on adm_appln.personal_data_id= personal_data.id");
			sql.append(" inner join course on classes.course_id = course.id");
			sql.append(" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id");
			sql.append(" inner join subject ON attendance.subject_id = subject.id");
			sql.append(" where attendance.is_canceled=0");
			sql.append(" and student.is_admitted=1");
			sql.append(" and adm_appln.is_cancelled=0");
			sql.append(" and curriculum_scheme_duration.academic_year= :academicYear");
			sql.append(" and course.id= :courseId");
			sql.append(" and classes.id= :classId");
			sql.append(" and attendance_student.student_id = :studentId");
			sql.append(" and subject.id= :subjectId");
			sql.append(" group by attendance.id,attendance_student.student_id,attendance_class.class_schemewise_id,subject.id,attendance.attendance_date");
			sql.append(" UNION");
			sql.append(" select attendance.attendance_date,");
			sql.append(" attendance.id as attid,");
			sql.append(" attendance.subject_id,");
			sql.append(" attendance.hours_held,");
			sql.append(" attendance_student.is_cocurricular_leave,");
			sql.append(" attendance_student.is_present,");
			sql.append(" attendance_student.student_id,");
			sql.append(" attendance_class.class_schemewise_id,");
			sql.append(" classes.id as class_id1,");
			sql.append(" classes.name as class_name1,");
			sql.append(" classes.term_number,");
			sql.append(" curriculum_scheme_duration.academic_year,");
			sql.append(" course.id as course_id1,");
			sql.append(" course.name as course_name1,");
			sql.append(" student.id as student_id1,");
			sql.append(" student.register_no,");
			sql.append(" student.roll_no,");
			sql.append(" personal_data.first_name,");
			sql.append(" subject.id as sub_id,");
			sql.append(" subject.name as subject,");
			sql.append(" if((attendance_student.is_present=1 || attendance_student.is_cocurricular_leave=1),attendance.hours_held,0) as present");
			sql.append(" from");
			sql.append(" attendance");
			sql.append(" inner join attendance_student on attendance_student.attendance_id = attendance.id");
			sql.append(" inner join attendance_class on attendance_class.attendance_id = attendance.id");
			sql.append(" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join classes ON class_schemewise.class_id = classes.id");
			sql.append(" inner join EXAM_student_previous_class_details");
			sql.append(" on EXAM_student_previous_class_details.class_id = classes.id");
			sql.append(" and EXAM_student_previous_class_details.student_id = attendance_student.student_id");
			sql.append(" inner join student on attendance_student.student_id=student.id");
			sql.append(" inner join adm_appln on student.adm_appln_id=adm_appln.id");
			sql.append(" inner join personal_data on adm_appln.personal_data_id= personal_data.id");
			sql.append(" inner join course on classes.course_id = course.id");
			sql.append(" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id");
			sql.append(" inner join subject ON attendance.subject_id = subject.id");
			sql.append(" where attendance.is_canceled=0");
			sql.append(" and student.is_admitted=1");
			sql.append(" and adm_appln.is_cancelled=0");
			sql.append(" and curriculum_scheme_duration.academic_year= :academicYear");
			sql.append(" and course.id= :courseId");
			sql.append(" and classes.id= :classId");
			sql.append(" and attendance_student.student_id = :studentId");
			sql.append(" and subject.id= :subjectId");
			sql.append(" group by attendance.id,attendance_student.student_id,attendance_class.class_schemewise_id,subject.id,attendance.attendance_date) as CLASS_MAIN");
			sql.append(" group by CLASS_MAIN.student_id,CLASS_MAIN.class_schemewise_id,CLASS_MAIN.subject_id,CLASS_MAIN.attendance_date");
			Query query = session.createSQLQuery(sql.toString())
			             .setInteger("courseId", Integer.parseInt(courseId))
			              .setInteger("classId", classId)
			              .setInteger("studentId", studentId)
			              .setString("academicYear", String.valueOf(acadamicYear))
			              .setInteger("subjectId", subjectId);
			
			subjectWiseAttendenceHrsList = query.list();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return subjectWiseAttendenceHrsList;
	}
	
	/*distinct concat( year(attendance.attendance_date),'-',month(attendance.attendance_date),'-',day(attendance.attendance_date)) as dt1, dayname(attendance.attendance_date) as d 
	  from attendance_class attendance_class 
		inner join attendance attendance ON attendance_class.attendance_id = attendance.id
		INNER JOIN class_schemewise class_schemewise on class_schemewise.id=attendance_class.class_schemewise_id
		INNER JOIN classes classes ON (class_schemewise.class_id = classes.id)
		where classes.id=478 and attendance.is_canceled=0 order by attendance.attendance_date*/
	
	
	/*SELECT tt_period_week.week_day, users.user_name, period.period_name, subject.name
	 
	 	 FROM   tt_subject_batch tt_subject_batch

		INNER JOIN tt_period_week tt_period_week  ON (tt_subject_batch.tt_period_id = tt_period_week.id)
		INNER JOIN tt_class tt_class  ON (tt_period_week.tt_class_id = tt_class.id)
		INNER JOIN tt_users tt_users ON (tt_users.tt_subject_id = tt_subject_batch.id)
		inner join class_schemewise class_schemewise on (tt_class.class_schemewise_id=class_schemewise.id)
		INNER JOIN classes classes ON (class_schemewise.class_id = classes.id)
		INNER JOIN course course ON (classes.course_id = course.id)
		INNER JOIN program program ON (course.program_id = program.id)
		INNER JOIN program_type program_type ON (program.program_type_id = program_type.id)
		inner join subject subject on(subject.id=tt_subject_batch.subject_id)
		INNER JOIN users users ON (tt_users.user_id = users.id)
	 	inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id
		Inner join student student on student.class_schemewise_id=class_schemewise.id
	   	inner join period period  on period.id=tt_period_week.period_id
		 
		 
		 where period.id not in (SELECT  period.id  FROM    period period
                   
				INNER JOIN class_schemewise class_schemewise ON (period.class_schemewise_id = class_schemewise.id)
            	INNER JOIN attendance_period attendance_period ON (attendance_period.period_id = period.id)
            	INNER JOIN attendance attendance ON (attendance_period.attendance_id = attendance.id)
        		INNER JOIN attendance_class attendance_class ON (attendance_class.attendance_id = attendance.id)
            	INNER JOIN classes classes ON (class_schemewise.class_id = classes.id)
				INNER JOIN attendance_instructor attendance_instructor ON (attendance_instructor.attendance_id = attendance.id)
				INNER JOIN users users ON (attendance_instructor.users_id = users.id)
				INNER JOIN attendance_student attendance_student on attendance_student.attendance_id=attendance.id
				Inner join student student on student.id=attendance_student.student_id
		 where attendance.attendance_date='2016-09-07'  and classes.id=478 and student.id=12369)
								
		 and  tt_subject_batch.is_active=1 and  users.is_active=1 and  period.is_active=1 and  classes.is_active=1 
		 and  tt_class.is_active=1 and classes.id=478 and  student.id=12369 and classes.id=478 and tt_period_week.week_day='Wednesday'
		 group by tt_period_week.id order by period.period_name*/

	
	
}
