package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.bo.exam.ExamStudentDetentionDetailsBO;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.bo.exam.ExamStudentDiscontinuationDetailsBO;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentRejoinBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.StudentBiodataForm;
import com.kp.cms.transactions.admission.IStudentBiodataTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * 
 * 
 * DAO Class for Student Edit Action
 * 
 */
public class StudentBiodataTransactionImpl  implements IStudentBiodataTransaction {
	private static final Log log = LogFactory
			.getLog(StudentBiodataTransactionImpl.class);

	/*
	 * (non-Javadoc) fetches student list
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IStudentBiodataTransaction#getSerchedStudents
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, int)
	 */
	@Override
	public List<Student> getSerchedStudents(String academicYear,
			String applicationNo, String regNo, String rollNo, int courseId,
			int programId, String firstName, int semister, int progtypeId)
			throws Exception {
		Session session = null;
		List<Student> stList ;
		try {
			StringBuffer query = new StringBuffer(
					"from Student st where st.isAdmitted=1 and st.isActive = 1 and st.admAppln.isSelected=1 and st.admAppln.isCancelled=0  and st.admAppln.isApproved=1 ");
			if (academicYear != null
					&& !StringUtils.isEmpty(academicYear.trim())) {
				query = query.append(" and st.admAppln.appliedYear="
						+ academicYear);
			}
			boolean singlefld = false;
			if (applicationNo != null
					&& !StringUtils.isEmpty(applicationNo.trim())
					&& StringUtils.isNumeric(applicationNo.trim())) {
				int appNO = Integer.parseInt(applicationNo.trim());
				query = query.append(" and st.admAppln.applnNo=" + appNO);
				singlefld = true;
			} else if (rollNo != null && !StringUtils.isEmpty(rollNo.trim())) {
				query = query.append(" and st.rollNo='" + rollNo + "'");
				singlefld = true;
			} else if (regNo != null && !StringUtils.isEmpty(regNo.trim())) {
				query = query.append(" and st.registerNo='" + regNo + "'");
				singlefld = true;
			}
			if (!singlefld) {
				if (progtypeId > 0) {
					query = query
							.append(" and st.admAppln.courseBySelectedCourseId.program.programType.id="
									+ progtypeId);
				}
				if (programId > 0) {
					query = query
							.append(" and st.admAppln.courseBySelectedCourseId.program.id="
									+ programId);
				}
				if (courseId > 0) {
					query = query
							.append(" and st.admAppln.courseBySelectedCourseId.id="
									+ courseId);
				}
				if (firstName != null && !StringUtils.isEmpty(firstName.trim())) {
					query = query
							.append(" and st.admAppln.personalData.firstName LIKE '%"
									+ firstName + "%'");
				}
				if (semister > 0) {
					query = query
							.append(" and st.classSchemewise.curriculumSchemeDuration.semesterYearNo="
									+ semister);
				}
			}
			query = query.append(" order by st.registerNo");
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query queri = session.createQuery(query.toString());
			stList = queri.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return stList;
	}

	/*
	 * (non-Javadoc) fetches student detail
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IStudentBiodataTransaction#getApplicantDetails
	 * (java.lang.String, int)
	 */
	@Override
	public Student getApplicantDetails(String applicationNumber,
			int applicationYear) throws Exception {

		Session session = null;
		Student applicantDetails = null;
		int appNO = 0;
		if (StringUtils.isNumeric(applicationNumber))
			appNO = Integer.parseInt(applicationNumber);
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "";
			sql = "from Student st where st.admAppln.applnNo=:AppNO and st.admAppln.appliedYear="
					+ applicationYear
					+ "and st.admAppln.isSelected=1 and st.admAppln.isCancelled=0 and st.isAdmitted=1 and st.isActive = 1";
			Query qr = session.createQuery(sql);
			qr.setInteger("AppNO", appNO);
			applicantDetails = (Student) qr.uniqueResult();

		} catch (Exception e) {
			log.error("Error while getting student details...", e);

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return applicantDetails;
	}

	/*
	 * (non-Javadoc) updates student details edit
	 * 
	 * @seecom.kp.cms.transactions.admission.IStudentBiodataTransaction#
	 * updateCompleteApplication(com.kp.cms.bo.admin.Student)
	 */
	@Override
	public boolean updateCompleteApplication(Student admBO,
			List<ExamStudentBioDataBO> stuBO,
			ExamStudentDetentionRejoinDetails detentionBO,
			ExamStudentDetentionRejoinDetails discontinueBO,
			ExamStudentDetentionRejoinDetails rejoinBO, String isDetain, String isDiscontinued) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = InitSessionFactory.getInstance().openSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(admBO);
			if(stuBO!= null && !stuBO.isEmpty()){
				Iterator<ExamStudentBioDataBO> itr=stuBO.iterator();
				while (itr.hasNext()) {
					ExamStudentBioDataBO examStudentBioDataBO = (ExamStudentBioDataBO) itr.next();
					session.saveOrUpdate(examStudentBioDataBO);
				}
			}
			if(isDetain.equalsIgnoreCase("yes")){
				if (detentionBO != null && rejoinBO == null){
					session.saveOrUpdate(detentionBO);
				}
			}else if (detentionBO!= null && detentionBO.getId()!= null && detentionBO.getId() > 0){
				session.delete(detentionBO);
			}
			if(isDiscontinued.equalsIgnoreCase("yes")){
				if (discontinueBO != null && rejoinBO == null){
					session.saveOrUpdate(discontinueBO);
				}
			}
			else if (discontinueBO!= null && discontinueBO.getId()!= null && discontinueBO.getId() > 0) {
				session.delete(discontinueBO);
			}
			if (rejoinBO != null)
				session.saveOrUpdate(rejoinBO);
			txn.commit();
			// session.flush();
			session.close();
			// sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen())
				txn.rollback();
			log.error("Error during updating complete student data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen())
				txn.rollback();

			log.error("Error during updating complete student data...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc) creates new student biodata
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IStudentBiodataTransaction#createNewStudent
	 * (com.kp.cms.bo.admin.Student)
	 */
	@Override
	public boolean createNewStudent(Student admBO) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Integer maxNo = 1;
			Query query = session
					.createQuery("select max(student.programTypeSlNo)"
							+ " from Student student "
							+ " where student.admAppln.courseBySelectedCourseId.program.programType.id = :programTypeId "
							+ " and student.admAppln.appliedYear = :year and student.isActive = 1");
			query.setInteger("programTypeId", admBO.getAdmAppln().getCourse()
					.getProgram().getProgramType().getId());
			query.setInteger("year", admBO.getAdmAppln().getAppliedYear()
					.intValue());
			if (query.uniqueResult() != null
					&& !query.uniqueResult().equals("0")) {
				maxNo = (Integer) query.uniqueResult();
				if (maxNo != null)
					maxNo = maxNo + 1;
				else
					maxNo = 1;
				admBO.setProgramTypeSlNo(maxNo);
			} else {
				admBO.setProgramTypeSlNo(1);
			}
			admBO.setIsActive(true);
			
			
			
			
			
			
			
			//admBO.setModifiedBy(admBO.getModifiedBy());
			admBO.setLastModifiedDate(new Date());
			txn = session.beginTransaction();
			session.saveOrUpdate(admBO);
			txn.commit();
			session.flush();
			session.close();
			// sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen())
				txn.rollback();
			log.error("Error during updating complete student data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen())
				txn.rollback();

			log.error("Error during updating complete student data...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc) fetches class scheme wise for student
	 * 
	 * @seecom.kp.cms.transactions.admission.IStudentBiodataTransaction#
	 * getClassSchemeForStudent(int, int)
	 */
	@Override
	public List<ClassSchemewise> getClassSchemeForStudent(int courseid, int year)
			throws Exception {
		Session session = null;
		List<ClassSchemewise> classes = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "";
			sql = "from ClassSchemewise c where c.classes.course.id= :courseId and c.curriculumSchemeDuration.academicYear = :Year and c.classes.isActive=1";
			Query queri = session.createQuery(sql);
			queri.setInteger("courseId", courseid);
			queri.setInteger("Year", year);
			classes = queri.list();

		} catch (Exception e) {
			log.error("Error in getClassSchemeForStudent..." + e);

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return classes;
	}

	/*
	 * (non-Javadoc) checks reg. no. already exist for course and year or not.
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IStudentBiodataTransaction#checkRegNoUnique
	 * (java.lang.String, java.lang.Integer, int)
	 */
	@Override
	public boolean checkRegNoUnique(String regNo, Integer appliedYear)
			throws Exception {
		Session session = null;
		boolean unique = true;

		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" from Student a where a.registerNo= :registerNo and a.admAppln.appliedYear= :appliedYear");
			query.setString("registerNo", regNo);
			query.setInteger("appliedYear", appliedYear);

			List<Student> list = query.list();
			if (list != null && !list.isEmpty())
				unique = false;

			session.flush();
			// session.close();
		} catch (Exception e) {

			session.flush();
			// session.close();
			log.error("Error during checking duplicate reg no..." + e);
			throw new ApplicationException(e);
		}
		return unique;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IStudentBiodataTransaction#deleteStudent
	 * (int)
	 */
	public boolean deleteStudent(int studId) throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			txn.begin();
			Student student = (Student) session.get(Student.class, studId);
			student.setIsActive(false);
			session.update(student);
			txn.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			txn.rollback();
			log.error("Error in delete student details...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			txn.rollback();
			log.error("Error in delete student details...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// Below methods are by 9Elements
	public List<Object[]> viewHistory_SubjectGroupDetails(int studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL_QUERY = "select s.schemeNo, s.subjectGroupUtilBO.name"
				+ "	from ExamStudentSubGrpHistoryBO s where s.studentId=:studentId";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("studentId", studentId);

		return query.list();
	}

	public List<ExamStudentBioDataBO> studentBioData(int studentId) throws Exception {

		boolean result = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			String HQL = null;
			HQL = "from ExamStudentBioDataBO biodata where biodata.studentId = :studentId";
			Query query = session.createQuery(HQL);
			query.setParameter("studentId", studentId);
			List<ExamStudentBioDataBO> list = query.list();
			return list;
		} catch (ConstraintViolationException e) {
			log.error("Error in delete student details...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error in delete student details...", e);
			throw new ApplicationException(e);
		}
	}

	/*public List<Object[]> studentDetention(int studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL = "select detention.id, "
				+ " detention.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo,"
				+ " detention.schemeNo, detention.detentionDate,detention.reason from "
				+ " ExamStudentDetentionDetailsBO detention where detention.studentId = :studentId";
		Query query = session.createQuery(HQL);
		query.setParameter("studentId", studentId);
		return query.list();
	}*/

/*	public List<Object[]> studentDiscontinue(int studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL = "select discn.id,"
				+ " discn.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo, "
				+ "discn.schemeNo, discn.discontinueDate, discn.reason"
				+ " from ExamStudentDiscontinuationDetailsBO discn where discn.studentId = :studentId";

		Query query = session.createQuery(HQL);
		query.setParameter("studentId", studentId);
		return query.list();
	}
*/
	public List<Object[]> studentRejoin(int studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = "select rejoin.rejoinDate, rejoin.classId, rejoin.batch, rejoin.remarks, rejoin.id "
				+ "from ExamStudentRejoinBO rejoin where rejoin.studentId = :studentId ";
		Query query = session.createQuery(HQL);
		query.setParameter("studentId", studentId);

		return query.list();
	}

	// View StudentDiscontinuationDetails
	public ArrayList<ExamStudentDiscontinuationDetailsBO> viewHistory_StuDiscontinuation(
			int studentId) {

		Session session = null;
		ArrayList<ExamStudentDiscontinuationDetailsBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL_QUERY = "select e.id, e.studentId, e.discontinueDate, e.reason, e.schemeNo,"
					+ " e.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
					+ " from ExamStudentDiscontinuationDetailsBO e"
					+ " where e.studentId = :studentId and e.id not in"
					+ "( select max(rjn.id) from ExamStudentDiscontinuationDetailsBO rjn where rjn.studentId = :studentId)";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("studentId", studentId);

			list = new ArrayList(query.list());
			session.flush();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamStudentDiscontinuationDetailsBO>();

		}
		return list;
	}

	// View StudentDetentionDetails
	public ArrayList<ExamStudentDetentionDetailsBO> viewHistory_StuDetention(
			int studentId) {

		Session session = null;
		ArrayList<ExamStudentDetentionDetailsBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL_QUERY = "select e.id, e.studentId, e.detentionDate, e.reason,e.schemeNo,"
					+ " e.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
					+ " from ExamStudentDetentionDetailsBO e"
					+ " where e.studentId = :studentId and e.id not in"
					+ "( select max(rjn.id) from ExamStudentDetentionDetailsBO rjn where rjn.studentId = :studentId)";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("studentId", studentId);

			list = new ArrayList(query.list());
			session.flush();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamStudentDetentionDetailsBO>();

		}
		return list;
	}

	// View StudentRejoinDetails
	public ArrayList<ExamStudentRejoinBO> viewHistory_StuRejoin(int studentId) {

		Session session = null;
		ArrayList<ExamStudentRejoinBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL_QUERY = "select e.id, e.batch, e.rejoinDate, e.remarks, e.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
					+ " from ExamStudentRejoinBO e"
					+ " where e.studentId = :studentId and e.id not in"
					+ "( select max(rjn.id) from ExamStudentRejoinBO rjn where rjn.studentId = :studentId)";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("studentId", studentId);

			list = new ArrayList(query.list());
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamStudentRejoinBO>();

		}
		return list;
	}

	public int getSchemeOfStudent(int studentId) {
		Session session = null;
		ArrayList schemeNo = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL_QUERY = "select e.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo"
					+ " from StudentUtilBO e where e.id = :studentId";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("studentId", studentId);

			schemeNo = new ArrayList(query.list());
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		if (schemeNo != null && schemeNo.size() > 0) {
			return Integer.parseInt(schemeNo.get(0).toString());
		}
		return 0;
	}

	@Override
	public List<SubjectGroup> getSubjectGroupList(int courseId, Integer year)
			throws Exception {
		Session session = null;
		List<SubjectGroup> subjectGroupList = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("select c.subjectGroup "
							+ "from CurriculumSchemeSubject c "
							+ "where c.curriculumSchemeDuration.curriculumScheme.course= "
							+ courseId
							+ "and c.curriculumSchemeDuration.curriculumScheme.year="
							+ year);
			subjectGroupList = query.list();
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return subjectGroupList;
	}

	@Override
	public List<ClassSchemewise> getClassSchemeByCourseId(int semNo,
			Integer year, int courseId) throws Exception {
		Session session = null;
		List<ClassSchemewise> classes = null;
		try {
			session = HibernateUtil.getSession();
			String sql = "select cs "
					+ " from Classes c1  "
					+ " join c1.classSchemewises cs "
					+ " where c1.isActive=1 and cs.curriculumSchemeDuration.academicYear = "
					+ year
					+ " and cs.curriculumSchemeDuration.semesterYearNo ="
					+ semNo + " and c1.course.id=" + courseId;
			Query queri = session.createQuery(sql);
			classes = queri.list();
		} catch (Exception e) {
			log.error("Error in getClassSchemeForStudent..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return classes;
	}

	@Override
	public List<Object[]> getYearandTermNo(int courseid, Integer year)
			throws Exception {
		Session session = null;
		List<Object[]> objList = null;
		try {
			session = HibernateUtil.getSession();
			String sql = "select c2.curriculumSchemeDuration.semesterYearNo,c2.curriculumSchemeDuration.academicYear"
					+ " from CurriculumSchemeSubject c2"
					+ " where c2.curriculumSchemeDuration.curriculumScheme.course="
					+ courseid
					+ " and c2.curriculumSchemeDuration.curriculumScheme.year="
					+ year;
			Query queri = session.createQuery(sql);
			objList = queri.list();
		} catch (Exception e) {
			log.error("Error in getClassSchemeForStudent..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return objList;
	}
	/**
	 * @param academicYear
	 * @param applicationNo
	 * @param regNo
	 * @param rollNo
	 * @param courseId
	 * @param programId
	 * @param firstName
	 * @param semister
	 * @param progtypeId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getSerchedStudentsPhotoList(String academicYear,
			String applicationNo, String regNo, String rollNo, int courseId,
			int programId, String firstName, int semister, int progtypeId)
			throws Exception {
		Session session = null;
		List<Integer> stList;
		try {
			StringBuffer query = new StringBuffer(
					"select st.id from ApplnDoc a"
					+ " join a.admAppln.students st  where  a.isPhoto=1 and  (a.document <> '' or a.document <> null) and st.isAdmitted=1 and st.isActive = 1 and st.admAppln.isSelected=1 and st.admAppln.isCancelled=0  and st.admAppln.isApproved=1 ");
			if (academicYear != null
					&& !StringUtils.isEmpty(academicYear.trim())) {
				query = query.append(" and st.admAppln.appliedYear="
						+ academicYear);
			}
			boolean singlefld = false;
			if (applicationNo != null
					&& !StringUtils.isEmpty(applicationNo.trim())
					&& StringUtils.isNumeric(applicationNo.trim())) {
				int appNO = Integer.parseInt(applicationNo.trim());
				query = query.append(" and st.admAppln.applnNo=" + appNO);
				singlefld = true;
			} else if (rollNo != null && !StringUtils.isEmpty(rollNo.trim())) {
				query = query.append(" and st.rollNo='" + rollNo + "'");
				singlefld = true;
			} else if (regNo != null && !StringUtils.isEmpty(regNo.trim())) {
				query = query.append(" and st.registerNo='" + regNo + "'");
				singlefld = true;
			}
			if (!singlefld) {
				if (progtypeId > 0) {
					query = query
							.append(" and st.admAppln.courseBySelectedCourseId.program.programType.id="
									+ progtypeId);
				}
				if (programId > 0) {
					query = query
							.append(" and st.admAppln.courseBySelectedCourseId.program.id="
									+ programId);
				}
				if (courseId > 0) {
					query = query
							.append(" and st.admAppln.courseBySelectedCourseId.id="
									+ courseId);
				}
				if (firstName != null && !StringUtils.isEmpty(firstName.trim())) {
					query = query
							.append(" and st.admAppln.personalData.firstName LIKE '%"
									+ firstName + "%'");
				}
				if (semister > 0) {
					query = query
							.append(" and st.classSchemewise.curriculumSchemeDuration.semesterYearNo="
									+ semister);
				}
			}
			query = query.append(" order by st.registerNo");
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query queri = session.createQuery(query.toString());
			stList = queri.list();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return stList;
	}

	@Override
	public boolean checkStudentDetailsExists(ExamStudentDetentionDetailsBO stu)
			throws Exception {

		Session session = null;
		List<ExamStudentDetentionDetailsBO> list=null;
		boolean isExists=false;
		try {
			session = HibernateUtil.getSession();
			String hql = "from ExamStudentDetentionDetailsBO e where e.detentionDate='" +stu.getDetentionDate()+
					"' and e.reason='"+stu.getReason()+"' and e.schemeNo="+stu.getSchemeNo()+" and e.studentId="+stu.getStudentId();
			Query qr = session.createQuery(hql);
			list = qr.list();
			if(list!=null && !list.isEmpty()){
				isExists=true;
			}
		} catch (Exception e) {
			log.error("Error while getting student details...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return isExists;
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public ExamStudentDetentionRejoinDetails getDetentionId(int studentId) throws Exception {
		Session session = null;
		ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails = null;
		
		try {
			Date tempDate = null;
			session = HibernateUtil.getSession();
			String sql = "from ExamStudentDetentionRejoinDetails det where " +
						" det.detentionDate = (select max(detentionDate) from ExamStudentDetentionRejoinDetails maxDet where " +
						" maxDet.student.id = " + studentId +  ") and det.student.id =" + studentId + " and (rejoin is null or rejoin = false) ";
			Query qr = session.createQuery(sql);
			List<ExamStudentDetentionRejoinDetails> detList = (List<ExamStudentDetentionRejoinDetails>)qr.list();
			Date detensionDate = null;
			if(detList!= null && detList.size() > 0){
				examStudentDetentionRejoinDetails = detList.get(0);
				detensionDate = examStudentDetentionRejoinDetails.getDetentionDate();
				tempDate = detensionDate;
			}
			sql = "from ExamStudentDetentionRejoinDetails det where " +
			" det.discontinuedDate = (select max(discontinuedDate) from ExamStudentDetentionRejoinDetails maxDet where " +
			" maxDet.student.id = " + studentId +  ") and det.student.id =" + studentId + " and (rejoin is null or rejoin = false)";
			qr = session.createQuery(sql);
			detList = (List<ExamStudentDetentionRejoinDetails>)qr.list();
			Date discontinuedDate = null;
			if(detList!= null && detList.size() > 0){
				examStudentDetentionRejoinDetails = detList.get(0);
				discontinuedDate = examStudentDetentionRejoinDetails.getDiscontinuedDate();
				tempDate = discontinuedDate;
			}
			
			if(discontinuedDate!= null && detensionDate!= null ){
				if(discontinuedDate.compareTo(detensionDate ) == 1){
					tempDate = discontinuedDate;
				}
				else
				{
					tempDate = detensionDate;
				}
			}
			if(tempDate!= null){
				sql = "from ExamStudentDetentionRejoinDetails det where " +
				" det.discontinuedDate = '" +  tempDate +   "' and det.student.id =" + studentId +
				" and (rejoin is null or rejoin = false)";
				qr = session.createQuery(sql);
				detList = (List<ExamStudentDetentionRejoinDetails>)qr.list();
				if(detList!= null && detList.size() > 0){
					examStudentDetentionRejoinDetails = detList.get(0);
				}
				else {
					sql = "from ExamStudentDetentionRejoinDetails det where " +
					" det.detentionDate = '" +  tempDate +   "' and det.student.id =" + studentId +
					" and (rejoin is null or rejoin = false)";
					qr = session.createQuery(sql);
					detList = (List<ExamStudentDetentionRejoinDetails>)qr.list();
					if(detList!= null && detList.size() > 0){
						examStudentDetentionRejoinDetails = detList.get(0);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error while getting student details...", e);

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return examStudentDetentionRejoinDetails;
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public ExamStudentDetentionRejoinDetails studentDetention(int studentId) throws Exception {
		Session session = null;
		ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails = null;
		
		try {
			session = HibernateUtil.getSession();
			String sql = "from ExamStudentDetentionRejoinDetails det where " +
						" det.student.id =" + studentId + " and (det.rejoin is null or det.rejoin = false ) and detain = true";
			Query qr = session.createQuery(sql);
			List<ExamStudentDetentionRejoinDetails> detList = (List<ExamStudentDetentionRejoinDetails>)qr.list();
			if(detList!= null && detList.size() > 0){
				examStudentDetentionRejoinDetails = detList.get(0);
			}
		} catch (Exception e) {
			log.error("Error while getting student details...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return examStudentDetentionRejoinDetails;
	}
	
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public ExamStudentDetentionRejoinDetails studentDiscontinue(int studentId) throws Exception {
		Session session = null;
		ExamStudentDetentionRejoinDetails examStudentDetentionRejoinDetails = null;
		
		try {
			session = HibernateUtil.getSession();
			String sql = "from ExamStudentDetentionRejoinDetails det where " +
						" det.student.id =" + studentId + " and (det.rejoin is null or det.rejoin = false ) and discontinued = true";
			Query qr = session.createQuery(sql);
			List<ExamStudentDetentionRejoinDetails> detList = (List<ExamStudentDetentionRejoinDetails>)qr.list();
			if(detList!= null && detList.size() > 0){
				examStudentDetentionRejoinDetails = detList.get(0);
			}
		} catch (Exception e) {
			log.error("Error while getting student details...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return examStudentDetentionRejoinDetails;
	}
	
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<ExamStudentDetentionRejoinDetails> studentDetainHistory(int studentId, boolean detention) throws Exception {
		Session session = null;
		List<ExamStudentDetentionRejoinDetails> detList;
		
		try {
			session = HibernateUtil.getSession();
			String sql = "from ExamStudentDetentionRejoinDetails det where " +
						" det.student.id =" + studentId + " and (det.rejoin = true )";
			if(detention){
				sql = sql + " and detain = true";
			}
			else{
				sql = sql + " and discontinued = true";
			}
			Query qr = session.createQuery(sql);
			detList = (List<ExamStudentDetentionRejoinDetails>)qr.list();
		} catch (Exception e) {
			log.error("Error while getting student details...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return detList;
	}
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public List<ExamStudentDetentionRejoinDetails> studentRejoinHistory(int studentId) throws Exception {
		Session session = null;
		List<ExamStudentDetentionRejoinDetails> detList;
		
		try {
			session = HibernateUtil.getSession();
			String sql = "from ExamStudentDetentionRejoinDetails det where " +
						" det.student.id =" + studentId + " and (det.rejoin = true )";
			
			Query qr = session.createQuery(sql);
			detList = (List<ExamStudentDetentionRejoinDetails>)qr.list();
		} catch (Exception e) {
			log.error("Error while getting student details...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return detList;
	}

	@Override
	public List<SubjectGroup> getSubjectGroupListBySemester(int courseId,
			Integer year, int semesterNo) throws Exception {
		Session session = null;
		List<SubjectGroup> subjectGroupList = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQl="select c.subjectGroup "
							+ "from CurriculumSchemeSubject c "
							+ "where c.curriculumSchemeDuration.curriculumScheme.course= "
							+ courseId
							+ "and c.curriculumSchemeDuration.curriculumScheme.year="
							+ year;
			if(semesterNo>0){
				HQl=HQl+" and c.curriculumSchemeDuration.semesterYearNo="+semesterNo;
			}
			Query query = session.createQuery(HQl);
			subjectGroupList = query.list();
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return subjectGroupList;
	}

	@Override
	public List<ExamStudentSubGrpHistoryBO> getSubHistoryByStudentId(
			int studentId, int schemeNo,StudentBiodataForm stForm) throws Exception {
		Session session = null;
		Map<Integer,ExamStudentSubGrpHistoryBO> map=new HashMap<Integer, ExamStudentSubGrpHistoryBO>();
		List<ExamStudentSubGrpHistoryBO> classes = null;
		try {
			session = HibernateUtil.getSession();
			String hql = "select s from ExamStudentSubGrpHistoryBO s where s.studentId="+studentId;
			if(schemeNo>0){
				hql=hql+" and s.schemeNo="+schemeNo;
			}
			Query queri = session.createQuery(hql);
			classes = queri.list();
			if(classes!=null && !classes.isEmpty()){
				Iterator<ExamStudentSubGrpHistoryBO> itr=classes.iterator();
				while (itr.hasNext()) {
					ExamStudentSubGrpHistoryBO examStudentSubGrpHistoryBO = (ExamStudentSubGrpHistoryBO) itr.next();
					map.put(examStudentSubGrpHistoryBO.getSubjectGroupId(), examStudentSubGrpHistoryBO);
				}
			}
			stForm.setSubjHistMap(map);
		} catch (Exception e) {
			log.error("Error in getClassSchemeForStudent..." + e);

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return classes;
	}

	@Override
	public boolean updateHistoryDetails(List<ExamStudentSubGrpHistoryBO> stu,
			List<ExamStudentSubGrpHistoryBO> deleteList,ExamStudentPreviousClassDetailsBO previousClassBo) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = InitSessionFactory.getInstance().openSession();
			txn = session.beginTransaction();
			if(stu!= null && !stu.isEmpty()){
				Iterator<ExamStudentSubGrpHistoryBO> itr=stu.iterator();
				while (itr.hasNext()) {
					ExamStudentSubGrpHistoryBO historyBo = (ExamStudentSubGrpHistoryBO) itr.next();
					session.saveOrUpdate(historyBo);
				}
			}
			if(deleteList!=null && !deleteList.isEmpty()){
				Iterator<ExamStudentSubGrpHistoryBO> it=deleteList.iterator();
				while (it.hasNext()) {
					ExamStudentSubGrpHistoryBO examStudentSubGrpHistoryBO = (ExamStudentSubGrpHistoryBO) it.next();
					session.delete(examStudentSubGrpHistoryBO);
				}
			}
			if(previousClassBo!=null){
				session.saveOrUpdate(previousClassBo);
			}
			txn.commit();
			// session.flush();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen())
				txn.rollback();
			log.error("Error during updating complete student data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen())
				txn.rollback();

			log.error("Error during updating complete student data...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	public ExamStudentPreviousClassDetailsBO getPreviousClassHistory(
			int studentId, int scheme) throws Exception {
		Session session = null;
		List<ExamStudentPreviousClassDetailsBO> classHist = null;
		ExamStudentPreviousClassDetailsBO bo=null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQl="from ExamStudentPreviousClassDetailsBO b where b.studentId="+studentId;
			if(scheme>0){
				HQl=HQl+" and b.schemeNo="+scheme;
			}
			Query query = session.createQuery(HQl);
			classHist = query.list();
			if(classHist!=null && !classHist.isEmpty()){
				bo=classHist.get(0);
			}
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return bo;
	}

	@Override
	public Classes getSemesterNoByClassId(int classSchemeId) throws Exception {
		Session session = null;
		Classes bo=null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQl="select c.classes from ClassSchemewise c where c.classes.id="+classSchemeId;
			Query query = session.createQuery(HQl);
			bo =(Classes) query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return bo;
	}

	@Override
	public int getYearForClassId(int classId) throws Exception {

		Session session = null;
		CurriculumSchemeDuration bo=null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQl="select schemewise.curriculumSchemeDuration " +
					" from ClassSchemewise schemewise where schemewise.classes.course.isActive = 1" +
					" and schemewise.classes.isActive = 1" +
					" and schemewise.classes.id="+classId+" group by schemewise.curriculumSchemeDuration.academicYear";
			Query query = session.createQuery(HQl);
			bo =(CurriculumSchemeDuration) query.uniqueResult();
			if(bo!=null){
				return bo.getAcademicYear();
			}
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return 0;
	}
	
	@Override
	public int getSemesterNoByClassSchemeId(int classSchemeId) throws Exception {
		Session session = null;
		ClassSchemewise bo=null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQl="select c from ClassSchemewise c where c.id="+classSchemeId;
			Query query = session.createQuery(HQl);
			bo =(ClassSchemewise) query.uniqueResult();
			session.flush();
			if(bo!=null){
				return bo.getClasses().getTermNumber();
			}
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return 0;
	}
	public List<ExamStudentPreviousClassDetailsBO> viewHistory_ClassGroupDetails(int studentId) {
		Session session = null;
		List<ExamStudentPreviousClassDetailsBO> classes= new ArrayList<ExamStudentPreviousClassDetailsBO>();
		try{
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL_QUERY = "from ExamStudentPreviousClassDetailsBO e where e.studentId="+studentId;
			Query query = session.createQuery(HQL_QUERY);
			//classDetails= (ExamStudentPreviousClassDetailsBO)query.uniqueResult();
			classes=query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		

		return  classes;
	}

	/* 
	 * checking whether student is canceled .
	 * @see com.kp.cms.transactions.admission.IStudentBiodataTransaction#checkStudentIsActive(com.kp.cms.forms.admission.StudentBiodataForm)
	 */
	@Override
	public boolean checkStudentIsActive(StudentBiodataForm stForm)
			throws Exception {
		Session session = null;
		String applicationNo = stForm.getApplicationNo();
		String rollNo = stForm.getRollNo();
		String regNo = stForm.getRegNo();
		boolean singlefld = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			StringBuffer query = new StringBuffer(
					" from Student st where st.admAppln.isCancelled=1 ");
//			if (academicYear != null
//					&& !StringUtils.isEmpty(academicYear.trim())) {
//				query = query.append(" and st.admAppln.appliedYear="
//						+ academicYear);
//			}
			
			if (applicationNo != null
					&& !StringUtils.isEmpty(applicationNo.trim())
					&& StringUtils.isNumeric(applicationNo.trim())) {
				int appNO = Integer.parseInt(applicationNo.trim());
				query = query.append(" and st.admAppln.applnNo=" + appNO);
			} else if (rollNo != null && !StringUtils.isEmpty(rollNo.trim())) {
				query = query.append(" and st.rollNo='" + rollNo + "'");
			} else if (regNo != null && !StringUtils.isEmpty(regNo.trim())) {
				query = query.append(" and st.registerNo='" + regNo + "'");
			}
			Query query1= session.createQuery(query.toString());
			List<Student> list=  query1.list();
			if(list!=null && !list.isEmpty()){
				singlefld=true;
				Iterator<Student> iterator = list.iterator();
				while (iterator.hasNext()) {
					Student student = (Student) iterator.next();
					stForm.setReMark(student.getAdmAppln().getCancelRemarks());
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return singlefld;
	}
}
