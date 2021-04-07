package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.admission.StudentCertificateCourseTO;
import com.kp.cms.transactions.admission.IStudentCertificateCourseTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;



public class StudentCertificateCourseTxnImpl implements IStudentCertificateCourseTxn {
	public static volatile StudentCertificateCourseTxnImpl studentCertificateCourseTxnImpl = null;
	private static final Log log = LogFactory.getLog(StudentCertificateCourseTxnImpl.class);
	
	public static StudentCertificateCourseTxnImpl getInstance() {
		if (studentCertificateCourseTxnImpl == null) {
			studentCertificateCourseTxnImpl = new StudentCertificateCourseTxnImpl();
			return studentCertificateCourseTxnImpl;
		}
		return studentCertificateCourseTxnImpl;
	}
	/**
	 * 
	 * @param programTypeId
	 * @param programId
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public List<CertificateCourse> getMandatoryCertificateCourses(int programTypeId, int programId, String stream)throws Exception
	{
		Session session = null;
		List<CertificateCourse> courseBoList; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("from CertificateCourse c where c.isActive = 1 and " +
							" c.stream != :stream and c.isOptional = 0");
			//query.setParameter("typeId", programTypeId);
			query.setParameter("stream", stream);
			
			courseBoList = query.list();
			
			
			} catch (Exception e) {
			log.error("Error in getMandatoryCertificateCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				}
			}
			return courseBoList;
	}
	/**
	 * 
	 * @param programTypeId
	 * @param programId
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public List<CertificateCourse> getOptionalCertificateCourses(int programTypeId, int programId, String stream)throws Exception
	{
		Session session = null;
		List<CertificateCourse> courseBoList; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("from CertificateCourse c where c.isActive = 1 and " +
							"c.isOptional = 1 or (c.stream = :stream and c.isOptional = 0)");
			//query.setParameter("typeId", programTypeId);
			query.setParameter("stream", stream);
			
			courseBoList = query.list();
			
			
			} catch (Exception e) {
			log.error("Error in getMandatoryCertificateCourses of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				}
			}
			return courseBoList;
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public Program getProgramByStudentId(int studentId)throws Exception
	{
		Session session = null;
		Program program; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("select s.admAppln.courseBySelectedCourseId.program from Student s where s.isActive = 1 and s.id = :studentId");
			query.setParameter("studentId", studentId);
			
			program = (Program) query.uniqueResult();
			
			} catch (Exception e) {
				log.error("Error in getProgramByStudentId of Course Impl",e);
				throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				}
			}
			return program;
	}
	
	/**
	 * 
	 * @param certificateCourse
	 * @return
	 * @throws Exception
	 */
	public boolean addStudentCertificateCourse(StudentCertificateCourse certificateCourse) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			if(session!=null){
			    transaction = session.beginTransaction();
			    transaction.begin();
			    session.save(certificateCourse);
			    transaction.commit();
			    session.flush();
			    session.close();
			    return true;
			}else
				return false;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			      transaction.rollback();
			log.error("Error during saving addStudentCertificateCourse..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			      transaction.rollback();
			log.error("Error during saving addStudentCertificateCourse data..." , e);
			throw new ApplicationException(e);
		}

	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public Integer getSchemeNoByStudentId(int studentId)throws Exception{
		Session session = null;
		List<Integer> schemeList; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("select s.classSchemewise.classes.termNumber from Student s where s.isActive = 1 and s.id = :studentId");
			query.setParameter("studentId", studentId);
			
			schemeList = query.list();
			
		} catch (Exception e) {
			log.error("Error in getProgramByStudentId of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		if(schemeList!= null && schemeList.size() > 0){
			return schemeList.get(0);
		}
		else{
			return 0;
		}
	}
		
	/**
	 * 
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<CertificateCourseTeacher> getCertificateCourseTeacher(int courseId)throws Exception
	{
		Session session = null;
		List<CertificateCourseTeacher> courseBoList; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("from CertificateCourseTeacher c where c.isActive = 1 and " +
							" c.certificateCourse.id = :courseId");
			query.setParameter("courseId", courseId);
			
			courseBoList = query.list();
			
			
			} catch (Exception e) {
			log.error("Error in getCertificateCourseTeacher of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				}
			}
			return courseBoList;
	}
	public Double getFeeAmount(int courseId)throws Exception
	{
		Session session = null;
		Double amount = 0.0;
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("select sum(amount) from FeeAdditionalAccountAssignment f where f.isActive = 1 and " +
							" f.feeAdditional.certificateCourse.id = :courseId");
			query.setParameter("courseId", courseId);
			
			List courseBoList = query.list();
			if (courseBoList != null && courseBoList.size() > 0) {
				if (courseBoList.get(0) != null)
					amount = new Double(courseBoList.get(0).toString());
			}
			
			
			} 
		catch (RuntimeException e) {
			log.error("Error in getCertificateCourseTeacher of Course Impl",e);
			throw new ApplicationException(e);
			}catch (Exception e) {
			log.error("Error in getCertificateCourseTeacher of Course Impl",e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				}
			}
			return amount;
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public Student getStudentDetails(int studentId)throws Exception{
		Session session = null;
		List<Student> studentList; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("select s from Student s left join s.admAppln" +
					" where s.isActive = 1 and s.id = :studentId");
			
			query.setParameter("studentId", studentId);
			
			studentList = query.list();
			
		} catch (Exception e) {
			log.error("Error in getStudentDetails of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		if(studentList!= null && studentList.size() > 0){
			return studentList.get(0);
		}
		else{
			return null;
		}
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getStudentCertificateCourseDetails(int studentId)throws Exception{
		Session session = null;
		List<Integer> idList; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("select s.certificateCourse.id from StudentCertificateCourse s " +
					" where s.student.id = :studentId");
			
			query.setParameter("studentId", studentId);
			
			idList = query.list();
			
		} catch (Exception e) {
			log.error("Error in getStudentCertificateCourseDetails of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return idList;
	}
	/**
	 * 
	 * @param studentId
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public boolean isAppliedForSemester(int studentId, int schemeNo)throws Exception{
		Session session = null;
		List<StudentCertificateCourse> list; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("from StudentCertificateCourse s " +
					" where s.isCancelled=0 and s.student.id = :studentId  and s.schemeNo = :schemeNo");
			
			query.setParameter("studentId", studentId);
			query.setParameter("schemeNo", schemeNo);
			
			list = query.list();
			
		} catch (Exception e) {
			log.error("Error in getStudentCertificateCourseDetails of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> getSubjectMap() throws Exception{
		Session session = null;
		List<CertificateCourse> list; 
		Map<Integer, Integer> subjectMap = new HashMap<Integer, Integer>();
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("from CertificateCourse s ");
					
			
			list = query.list();
			if(list!= null && list.size() > 0){
				CertificateCourse certificateCourse;
				Iterator<CertificateCourse> itr = list.iterator();
				while (itr.hasNext()){
					certificateCourse = itr.next();
				//	subjectMap.put(certificateCourse.getId(), certificateCourse.getSubject().getId());	
				}
			}
			
		} catch (Exception e) {
			log.error("Error in getStudentCertificateCourseDetails of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return subjectMap;
	}
	/**
	 * 
	 * @param courseId
	 * @param schemeNo
	 * @param appliedYear
	 * @return
	 * @throws Exception
	 */
	public Integer getAcademicYear(int courseId, int schemeNo, int appliedYear)throws Exception{
		Session session = null;
		List<Integer> list; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("select c.academicYear from CurriculumSchemeDuration c " +
					" where c.semesterYearNo = :schemeNo and c.curriculumScheme.year = :year and c.curriculumScheme.course.id = :courseId");
			
			query.setInteger("year", appliedYear);
			query.setInteger("schemeNo", schemeNo);
			query.setInteger("courseId", courseId);
			
			list = query.list();
			
		} catch (Exception e) {
			log.error("Error in getStudentCertificateCourseDetails of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	
	public List<StudentCertificateCourse> getAppliedCourses(int studentId)throws Exception{
		Session session = null;
		List<StudentCertificateCourse> list; 
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("from StudentCertificateCourse s " +
					" where s.student.id = :studentId ");
			
			query.setParameter("studentId", studentId);

			
			list = query.list();
			
		} catch (Exception e) {
			log.error("Error in getStudentCertificateCourseDetails of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	public String getCertificateCourseStudentId(String registerNo) throws Exception {
		Session session = null;
		String studentId = null;
		try {
			session = HibernateUtil.getSession();
			Query query =  session.createQuery("from Student s where s.registerNo='"+registerNo+"' and s.admAppln.isCancelled=0");
			Student student=(Student) query.uniqueResult();
			if(student!=null){
				studentId = Integer.toString(student.getId());
			}
			
			return studentId;
		} catch (Exception e) {
			log.error("Error in getStudentCertificateCourseDetails of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentCertificateCourseTxn#studentCertificateCourseCount(com.kp.cms.bo.admin.StudentCertificateCourse)
	 */
	public Long studentCertificateCourseCount(StudentCertificateCourse certificateCourse) throws Exception {
		Session session = null;
		long count =0;
		try {
			int certificateCourseId = certificateCourse.getCertificateCourse().getId();
			String semType="ODD";
			if(certificateCourse.getSchemeNo()>0){
				if(certificateCourse.getSchemeNo()%2==0){
					semType="EVEN";
				}
			}
			session = HibernateUtil.getSession();
			String hqlQuery = "select count (*) from StudentCertificateCourse s where s.isCancelled=0 and s.certificateCourse.id = "+certificateCourseId+" and s.certificateCourse.semType='"+semType+"'";
			Query query =  session.createQuery(hqlQuery);
			count =  (Long)query.uniqueResult();
			return count;
		} catch (ConstraintViolationException e) {
			log.error("Error during saving studentCertificateCourseCount..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving studentCertificateCourseCount data..." , e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * @param registerNo
	 * @return
	 * @throws Exception
	 */
	public Integer getCertificateCourseMaxIntake(StudentCertificateCourse certificateCourse) throws Exception {
		Session session = null;
		int maxIntake=0;
		try {
			int certificateCourseId=certificateCourse.getCertificateCourse().getId();
			session = HibernateUtil.getSession();
			String hqlQuery = "select c.maxIntake from CertificateCourse c where c.id = "+certificateCourseId;
			Query query =  session.createQuery(hqlQuery);
			maxIntake=(Integer) query.uniqueResult();
			return maxIntake;
		} catch (Exception e) {
			log.error("Error in getStudentCertificateCourseDetails of Course Impl",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentCertificateCourseTxn#getDataForQuery(java.lang.String)
	 */
	@Override
	public List getDataForQuery(String dataQuery) throws Exception {
		Session session = null;
		List dataList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(dataQuery);
			dataList = selectedCandidatesQuery.list();
			return dataList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IStudentCertificateCourseTxn#getStudentCertificateListForCancallation(java.lang.String)
	 */
	@Override
	public List<StudentCertificateCourse> getStudentCertificateListForCancallation(String query) throws Exception {
		Session session = null;// initalizing session with null value
		List<StudentCertificateCourse> selectedCandidatesList = null;// initializing list with null value
		try {
			session = HibernateUtil.getSession();// creating session object using Hibernate Util Class
			Query selectedCandidatesQuery=session.createQuery(query);// creating Query object using session for interacting with the database
			selectedCandidatesList = selectedCandidatesQuery.list();// session will return list of data based on the query
			return selectedCandidatesList;// returning the list to handler
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
	public boolean updateCertificateCourse(List<StudentCertificateCourseTO> studentCertificateCourse,String userId) throws Exception {
		Session session = null;// initalizing session with null value
		Transaction transaction=null;// initalizing transaction with null value
		try {
			session = HibernateUtil.getSession();// creating session object using Hibernate Util Class
			transaction=session.beginTransaction();
			transaction.begin();
			Iterator<StudentCertificateCourseTO> itr=studentCertificateCourse.iterator();
			while (itr.hasNext()) {
				StudentCertificateCourseTO to = (StudentCertificateCourseTO) itr.next();
				if(to.getChecked() != null && to.getChecked().equalsIgnoreCase("on")){
					StudentCertificateCourse bo=(StudentCertificateCourse)session.get(StudentCertificateCourse.class,to.getId());
					bo.setIsCancelled(true);
					bo.setModifiedBy(userId);
					bo.setLastModifiedDate(new Date());
					session.update(bo);
				}
				
			}
			transaction.commit();
			return true;// returning the list to handler
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			if(transaction!=null){
				transaction.rollback();
			}
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	public List getCourseName(String dataQuery) throws Exception {
		Session session = null;
		List dataList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCourseQuery=session.createQuery(dataQuery);
			dataList = selectedCourseQuery.list();
			return dataList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
		
		public List getCourseData(String dataQuery) throws Exception {
			Session session = null;
			List dataList = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCourseQuery=session.createSQLQuery(dataQuery);
				dataList = selectedCourseQuery.list();
				return dataList;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}	
		
	}
		
		public List getCourseCodeName(String dataQuery) throws Exception {
			Session session = null;
			List dataList = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCourseQuery=session.createQuery(dataQuery);
				dataList = selectedCourseQuery.list();
				return dataList;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
		}
	public List  getSubjectGroupNameExist(String dataQuery) throws Exception {
			Session session = null;
			List  dataList = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCourseQuery=session.createQuery(dataQuery);
				dataList = selectedCourseQuery.list();
				return dataList;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
		}
	
	public void savesubjectGroupName(SubjectGroup sb) throws Exception
	{
	Session session = null;
	Transaction txn = null;
	try {
		session = InitSessionFactory.getInstance().openSession();
		txn = session.beginTransaction();
		session.saveOrUpdate(sb);
				txn.commit();
		}
	catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
}
	
	public List  getSubject(String dataQuery) throws Exception {
		Session session = null;
		List  dataList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCourseQuery=session.createQuery(dataQuery);
			dataList = selectedCourseQuery.list();
			return dataList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public void savesubgrpSub(SubjectGroupSubjects sbs) throws Exception
	{
	Session session = null;
	Transaction txn = null;
	try {
		session = InitSessionFactory.getInstance().openSession();
		txn = session.beginTransaction();
		session.saveOrUpdate(sbs);
				txn.commit();
		}
	catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
}
	
	public void curriculumSchemeSubject(CurriculumSchemeSubject css) throws Exception
	{
	Session session = null;
	Transaction txn = null;
	try {
		session = InitSessionFactory.getInstance().openSession();
		txn = session.beginTransaction();
		session.saveOrUpdate(css);
				txn.commit();
		}
	catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
}
	public void applicantSubjectGroup(ApplicantSubjectGroup asg) throws Exception
	{
	Session session = null;
	Transaction txn = null;
	try {
		session = InitSessionFactory.getInstance().openSession();
		txn = session.beginTransaction();
		session.saveOrUpdate(asg);
				txn.commit();
		}
	catch (Exception e) {
		log.error("Error while retrieving selected candidates.." +e);
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
		}
	}
}
	public List getDuplicateInCurriculum(String dataQuery) throws Exception {
		Session session = null;
		List  dataList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCourseQuery=session.createQuery(dataQuery);
			dataList = selectedCourseQuery.list();
			return dataList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	public List getDuplicateInApplicantSubGrp(String dataQuery) throws Exception {
		Session session = null;
		List  dataList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCourseQuery=session.createQuery(dataQuery);
			dataList = selectedCourseQuery.list();
			return dataList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	public List getDuplicateSubGrpInSGS(String dataQuery) throws Exception {
		Session session = null;
		List  dataList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCourseQuery=session.createQuery(dataQuery);
			dataList = selectedCourseQuery.list();
			return dataList;
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