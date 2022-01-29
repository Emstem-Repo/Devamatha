package com.kp.cms.transactionsimpl.admission;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.bo.admin.GenerateMail;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.ParishBO;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.admission.InterviewTimeSelection;
import com.kp.cms.bo.admission.InterviewVenueSelection;
import com.kp.cms.bo.admission.PrerequisitsYearMonth;
import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.AdmissionStatusForm;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.forms.sap.ExamRegistrationDetailsForm;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.to.admission.InterviewSelectionScheduleTO;
import com.kp.cms.to.admission.InterviewTimeSelectionTO;
import com.kp.cms.to.admission.InterviewVenuSelectionTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * 
 * 
 * DAO Class for AdmissionFom Action
 * 
 */
public class AdmissionFormTransactionImpl implements IAdmissionFormTransaction {

	private static final Log log = LogFactory
			.getLog(AdmissionFormTransactionImpl.class);

	private static volatile AdmissionFormTransactionImpl instance=null;

	public static IAdmissionFormTransaction getInstance() {
		// TODO Auto-generated method stub
		if(instance==null)
			instance=new AdmissionFormTransactionImpl();
		return instance;
	}

	/*
	 * (non-Javadoc) saves complete application to DB
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * persistCompleteApplicationData(com.kp.cms.bo.admin.Student)
	 */
	@Override
	public boolean persistCompleteApplicationData(Student newStudent, AdmissionFormForm admForm)
			throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.save(newStudent);
			txn.commit();
			session.flush();
			session.close();
			//			sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			txn.rollback();
			log.error("Error during saving complete application data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			txn.rollback();
			log.error("Error during saving complete application data...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc) fetches all active nationalities
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IAdmissionFormTransaction#getNationalities
	 * ()
	 */
	@Override
	public List<Nationality> getNationalities() throws Exception {
		Session session = null;

		try {
			/*			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Nationality n where n.isActive=1");
			query.setReadOnly(true);
			List<Nationality> list = query.list();

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting nationalities...", e);
			if(session!= null){
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) get all active currency types
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IAdmissionFormTransaction#getCurrencies
	 * ()
	 */
	@Override
	public List<Currency> getCurrencies() throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Currency c where c.isActive=1");
			query.setReadOnly(true);
			List<Currency> list = query.list();

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) fetches all active incomes
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IAdmissionFormTransaction#getIncomes()
	 */
	@Override
	public List<Income> getIncomes() throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Income i where i.isActive=1 ");
			List<Income> list = query.list();

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting incomes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) get all active resident categories
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IAdmissionFormTransaction#getResidentTypes
	 * ()
	 */
	@Override
	public List<ResidentCategory> getResidentTypes() throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ResidentCategory c where c.isActive=1");
			List<ResidentCategory> list = query.list();

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting resident categories..." + e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}

	}

	/*
	 * (non-Javadoc) get all markcard types for course
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IAdmissionFormTransaction#getExamtypes
	 * ()
	 */
	public List<DocChecklist> getExamtypes(int courseId, int year)
			throws Exception {
		List<DocChecklist> docchecklist = new ArrayList<DocChecklist>();
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocChecklist d where d.course.id = :courseId and d.year=:year and d.isActive=1 and d.docType.isActive=1");
			query.setInteger("courseId", courseId);
			query.setInteger("year", year);
			List<DocChecklist> listofdocs = query.list();
			if (listofdocs != null && !listofdocs.isEmpty()) {
				Iterator<DocChecklist> itr = listofdocs.iterator();
				DocChecklist checkdocs;
				while (itr.hasNext()) {
					checkdocs = (DocChecklist) itr.next();
					if (checkdocs != null && checkdocs.getIsMarksCard()) {
						docchecklist.add(checkdocs);

					}
				}

			}

		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}

			log.error("Error during getting doc checklists...", e);
			throw new ApplicationException(e);
		}

		session.flush();
		//		session.close();
		return docchecklist;
	}

	/*
	 * (non-Javadoc) get list of docs needed for course
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getNeededDocumentList(java.lang.String)
	 */
	@Override
	public List<DocChecklist> getNeededDocumentList(String courseID)
			throws Exception {
		Session session = null;

		List<DocChecklist> listofdocs = null;
		// check consolidated needed or not
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocChecklist d where d.course.id = :courseId and d.isActive=1");
			query.setInteger("courseId", Integer.parseInt(courseID));
			listofdocs = query.list();

			session.flush();
			//			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}
			log.error(
					"Error during getting needed documents for attachment...",
					e);
			throw new ApplicationException(e);
		}
		return listofdocs;
	}

	/*
	 * (non-Javadoc) fetches all active preferences for the course
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getCourseForPreference(java.lang.String)
	 */
	@Override
	public List<Course> getCourseForPreference(String courseId)
			throws Exception {
		Session session = null;
		//		Preferences preference = null;
		List<Course> courseList = new ArrayList<Course>();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select c.courseByPrefCourseId from Preferences c where c.courseByCourseId.id = :courseId and c.isActive = 1");
			query.setInteger("courseId", Integer.parseInt(courseId));
			courseList=query.list();

			/*Commented By Manu,Iteration is not required,directly get list of CourseByPrefCourseId()			
			Query query = session.createQuery(" from Preferences c where courseByCourseId.id = :courseId and isActive = 1");
			query.setInteger("courseId", Integer.parseInt(courseId));
			// boolean isDefaultCourseAdded = false;
			for (Iterator iterator = query.iterate(); iterator.hasNext();) {
				preference = (Preferences) iterator.next();
				// if(!isDefaultCourseAdded){
				// Course prefCourse1=preference.getCourseByCourseId();
				// courseList.add(prefCourse1);
				// isDefaultCourseAdded = true;
				// }
				Course prefCourse = preference.getCourseByPrefCourseId();
				courseList.add(prefCourse);
			}
			 */
			session.flush();
			//			session.close();
			return courseList;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			log.error("Error during getting program type for preference...", e);
			throw new ApplicationException(e);
		}
	}


	public List<Course> getCourseForPreferencesbyug(String ugId)
			throws Exception {
		Session session = null;
		//Preferences preference = null;
		List<Course> courseList = new ArrayList<Course>();
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from Course c where c.program.programType.id="+Integer.parseInt(ugId));

			courseList=query.list();

			/*Commented By Manu,Iteration is not required,directly get list of CourseByPrefCourseId()			
	Query query = session.createQuery(" from Preferences c where courseByCourseId.id = :courseId and isActive = 1");
	query.setInteger("courseId", Integer.parseInt(courseId));
	// boolean isDefaultCourseAdded = false;
	for (Iterator iterator = query.iterate(); iterator.hasNext();) {
		preference = (Preferences) iterator.next();
		// if(!isDefaultCourseAdded){
		// Course prefCourse1=preference.getCourseByCourseId();
		// courseList.add(prefCourse1);
		// isDefaultCourseAdded = true;
		// }
		Course prefCourse = preference.getCourseByPrefCourseId();
		courseList.add(prefCourse);
	}
			 */
			session.flush();
			//	session.close();
			return courseList;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			log.error("Error during getting program type for preference...", e);
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) checks appln.no exists or not for year
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * checkApplicationNoUniqueForYear(int, int)
	 */
	@Override
	public boolean checkApplicationNoUniqueForYear(int applnNo, int year)
			throws Exception {
		Session session = null;
		boolean unique = true;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select a.applnNo from AdmAppln a where a.applnNo= :applnNo and a.appliedYear= :appliedYear ");
			query.setInteger("applnNo", applnNo);
			query.setInteger("appliedYear", year);
			List<AdmAppln> list = query.list();
			if (list != null && !list.isEmpty()) {
				unique = false;
			}
			session.flush();
			//			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}
			log.error("Error during getting program type for preference...", e);
			throw new ApplicationException(e);
		}
		return unique;
	}

	/*
	 * (non-Javadoc) get latest challan no from challan-ref table
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getCurrentChallanNo()
	 */
	@Override
	public String getCurrentChallanNo() throws Exception {
		Session session = null;
		String refNO = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select a.currentNo from ChallanReference a where a.id=1");
			refNO = (String) query.uniqueResult();
			session.flush();
			//			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}
			log.error("Error during getting current challanNO...", e);
			throw new ApplicationException(e);
		}
		return refNO;
	}

	/*
	 * (non-Javadoc) update challan sequence
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * updateLatestChallanNo(java.lang.String)
	 */
	@Override
	public boolean updateLatestChallanNo(String latestRefNo) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();

			Query updateWeigh = session
					.createQuery("update ChallanReference a set a.currentNo=:latestRefNo where a.id=:id");
			updateWeigh.setString("latestRefNo", latestRefNo);
			updateWeigh.setInteger("id", CMSConstants.CHALLAN_REF_ID);
			updateWeigh.executeUpdate();
			txn.commit();
			session.flush();
			session.close();
			//			sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			txn.rollback();
			log.error("Error during updating latest challan no...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			txn.rollback();

			log.error("Error during updating latest challan no...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc) gets all pre requisites for course
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getCoursePrerequisites(int)
	 */
	@Override
	public List<CoursePrerequisite> getCoursePrerequisites(int courseID)
			throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CoursePrerequisite c where c.course.id= :courseID and c.isActive=1");
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			List<CoursePrerequisite> list = query.list();
			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting CoursePrerequisite...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) checks appln no range for course
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * checkApplicationNoInRange(int, boolean, int, java.lang.String)
	 */
	@Override
	public boolean checkApplicationNoInRange(int applicationNumber,
			boolean onlineApply, int appliedyear, String courseId)
					throws Exception {
		Session session = null;
		boolean inRange = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setString("courseID", courseId);
			query.setReadOnly(true);
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber crsAppNo = (CourseApplicationNumber) appItr
							.next();
					if (onlineApply && crsAppNo.getApplicationNumber() != null) {
						String onlineFrom = crsAppNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						String onlineTo = crsAppNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						if (StringUtils.isNumeric(onlineFrom)
								&& StringUtils.isNumeric(onlineTo)) {
							int onlineStart = Integer.parseInt(onlineFrom);
							int onlineEnd = Integer.parseInt(onlineTo);
							if (applicationNumber >= onlineStart
									&& applicationNumber <= onlineEnd) {
								return true;
							}
						} else {
							return false;
						}
					} else if (!onlineApply
							&& crsAppNo.getApplicationNumber() != null) {
						String offlineFrom = crsAppNo.getApplicationNumber()
								.getOfflineApplnNoFrom();
						String offlineTo = crsAppNo.getApplicationNumber()
								.getOfflineApplnNoTo();
						if (offlineFrom != null
								&& !StringUtils.isEmpty(offlineFrom)
								&& offlineTo != null
								&& !StringUtils.isEmpty(offlineTo)
								&& StringUtils.isNumeric(offlineFrom)
								&& StringUtils.isNumeric(offlineTo)) {
							int offlineStart = Integer.parseInt(offlineFrom);
							int offlineEnd = Integer.parseInt(offlineTo);
							if (applicationNumber >= offlineStart
									&& applicationNumber <= offlineEnd) {
								return true;
							}
						} else {
							return false;
						}
					}
				}
			}

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return inRange;
		} catch (Exception e) {
			log.error("Error during getting checkApplicationNoInRange...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) checks appln no configured for course or not
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * checkApplicationNoConfigured(int, java.lang.String)
	 */
	public boolean checkApplicationNoConfigured(int appliedyear, String courseId)
			throws Exception {
		Session session = null;
		boolean inRange = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setString("courseID", courseId);
			query.setReadOnly(true);
			List<CourseApplicationNumber> list = query.list();
			if (list != null && !list.isEmpty()) {
				inRange = true;
			}
			return inRange;
		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) gets generated appln no for online
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getGeneratedOnlineAppNo(int, int, boolean)
	 */
	@Override
	public String getGeneratedOnlineAppNo(int courseID, int appliedyear,
			boolean isOnline) throws Exception {
		Session session = null;
		String generatedNo = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			// get the online range for course
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			String onlineFrom = null;
			String onlineTo = null;
			int onlineStart = 0;
			int onlineEnd = 0;
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber appNo = (CourseApplicationNumber) appItr
							.next();
					if (isOnline && appNo.getApplicationNumber() != null) {
						onlineFrom = appNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						onlineTo = appNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						onlineStart = Integer.parseInt(onlineFrom);
						onlineEnd = Integer.parseInt(onlineTo);
					}
				}
			}
			// check max appno in range
			query = session
					.createQuery("select max(a.applnNo) from AdmAppln a where a.appliedYear=:year and a.applnNo BETWEEN :startRange AND :endRange");
			query.setInteger("year", appliedyear);
			// query.setInteger("courseID", courseID);
			query.setInteger("startRange", onlineStart);
			query.setInteger("endRange", onlineEnd);
			query.setReadOnly(true);
			Integer maxNo = (Integer) query.uniqueResult();
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
			} else {
				int generatedInt = 0;

				generatedInt = maxNo;
				generatedInt++;

				generatedNo = String.valueOf(generatedInt);
			}
			session.flush();
			//			session.close();
			//			sessionFactory.close();

			return generatedNo;
		} catch (Exception e) {
			log.error("Error during auto generated online appl.no...", e);

			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) updates edit data
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * updateCompleteApplication(com.kp.cms.bo.admin.AdmAppln, boolean)
	 */
	@Override
	public boolean updateCompleteApplication(AdmAppln admBO,
			boolean admissionForm) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			//			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(admBO);
			// if admission, set student admitted
			if (admissionForm) {

				if (admBO.getIsApproved()) {
					Integer maxNo = 1;
					Query query = session
							.createQuery("select max(student.programTypeSlNo)"
									+ " from Student student "
									+ " where student.admAppln.courseBySelectedCourseId.program.programType.id = :programTypeId "
									+ " and student.admAppln.appliedYear = :year");
					query.setInteger("programTypeId", admBO.getCourse()
							.getProgram().getProgramType().getId());
					query.setInteger("year", admBO.getAppliedYear().intValue());
					if (query.uniqueResult() != null
							&& !query.uniqueResult().equals("0")) {
						maxNo = (Integer) query.uniqueResult();
						maxNo = maxNo + 1;
					}
					query = session
							.createQuery("update Student st set st.isAdmitted= :sele,st.isActive= :act, st.programTypeSlNo = :maxNo, st.isSCDataGenerated=:gene, st.isSCDataDelivered=:deli, st.isHide=:hide where st.admAppln.id= :admId");
					query.setBoolean("sele", true);
					query.setBoolean("act", true);
					query.setBoolean("gene", false);
					query.setBoolean("deli", false);
					query.setBoolean("hide", false);
					query.setInteger("maxNo", maxNo.intValue());
					query.setInteger("admId", admBO.getId());
					query.executeUpdate();
					admBO.setIsSelected(true);
					session.update(admBO);
				} else {
					Query query = session
							.createQuery("update Student st set st.isAdmitted= :sele, st.isHide=:hide where st.admAppln.id= :admId");
					query.setBoolean("sele", false);
					query.setBoolean("hide", false);
					query.setInteger("admId", admBO.getId());
					query.executeUpdate();
				}
			}
			txn.commit();
			session.flush();
			session.close();
			//			sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during updating complete application data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			log.error("Error during updating complete application data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc) checks seat allocation exceeded for admitted through Id
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * checkSeatAllocationExceeded(int, int)
	 */
	@Override
	public boolean checkSeatAllocationExceeded(int admId, int courseID)
			throws Exception {
		Session session = null;
		boolean exceed = false;
		int configSeats = -1;
		long appliedSeats = -1;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from SeatAllocation a where a.admittedThrough.id= :admId and a.course.id= :courseID");
			query.setInteger("admId", admId);
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			SeatAllocation seat = (SeatAllocation) query.uniqueResult();
			if (seat != null) {
				configSeats = seat.getNoOfSeats();
			} else {
				return false;
			}
			query = session
					.createQuery("select count(*) from AdmAppln a where a.admittedThrough.id= :admitId and a.course.id= :crsID");
			query.setInteger("admitId", admId);
			query.setInteger("crsID", courseID);
			query.setReadOnly(true);
			Long appliedSeat = (Long) query.uniqueResult();
			if (appliedSeat != null) {
				appliedSeats = appliedSeat.longValue();
			}
			if (configSeats <= appliedSeats) {
				exceed = true;
			}
			return exceed;
		} catch (Exception e) {
			log.error("Error during getting checkSeatAllocationExceeded..." + e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method is used to check existence of application number, courseId
	 * and admission year.
	 */

	@Override
	public List<Integer> checkApplicationNumberCancel(String searchCriteria)
			throws Exception {
		Session session = null;

		List<Integer> applno;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			applno = session.createQuery(searchCriteria).list();
		} catch (Exception e) {
			log.error("Error during checkApplicationNumberCancel...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return applno;
	}

	public int getStudentId(int appNumber, int admissionYear) throws Exception {
		Session session = null;
		Transaction transaction = null;
		int stid = 0;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("select id from Student s where s.isActive=1 and s.admAppln.applnNo= "
							+ appNumber
							+ " and s.admAppln.appliedYear= "
							+ admissionYear);
			if (query.uniqueResult() != null) {
				stid = (Integer) query.uniqueResult();
			}
		} catch (Exception e) {
			log.error("Error during getStudentId...", e);

			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return stid;
	}

	/**
	 * This method is used to update the remarks for cancellation and isCanceled
	 * field to true.
	 */

	public boolean updateApplicationNumberCancel(int appNumber,
			int admissionYear, String remarks,String cancelDate,String lastModifiedBy) throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean isUpdated = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			java.util.Date lastModifiedDate = new java.util.Date();
			Query query = session
					.createQuery("update AdmAppln adm set adm.isSelected= :sele, adm.isCancelled= :cancel, adm.cancelRemarks= :cancelRemarks,adm.cancelDate =:cancelDate,adm.modifiedBy = :lastModifiedBy,adm.lastModifiedDate =:lastModifiedDate where adm.applnNo= :appno and adm.appliedYear= :year ");
			query.setBoolean("sele", false);
			query.setBoolean("cancel", true);
			query.setInteger("appno", appNumber);
			query.setInteger("year", admissionYear);
			query.setString("cancelRemarks", remarks);
			query.setDate("cancelDate", CommonUtil.ConvertStringToSQLDate(cancelDate));
			query.setDate("lastModifiedDate", lastModifiedDate);
			query.setString("lastModifiedBy", lastModifiedBy);
			query.executeUpdate();
			txn.commit();
			AdmAppln admAppln=(AdmAppln)session .createQuery("from AdmAppln adm where adm.applnNo= :appno and adm.appliedYear= :year ").setInteger("appno", appNumber).setInteger("year", admissionYear).uniqueResult();
			if(admAppln.getCourseBySelectedCourseId().getIsApplicationProcessSms()){
				String mobileNo="";
				if(admAppln.getPersonalData().getMobileNo1()!=null && !admAppln.getPersonalData().getMobileNo1().isEmpty()){
					if(admAppln.getPersonalData().getMobileNo1().trim().equals("0091") || admAppln.getPersonalData().getMobileNo1().trim().equals("+91")
							|| admAppln.getPersonalData().getMobileNo1().trim().equals("091") || admAppln.getPersonalData().getMobileNo1().trim().equals("0"))
						mobileNo = "91";
					else
						mobileNo=admAppln.getPersonalData().getMobileNo1();
				}else{
					mobileNo="91";
				}
				if(admAppln.getPersonalData().getMobileNo2()!=null && !admAppln.getPersonalData().getMobileNo2().isEmpty()){
					mobileNo=mobileNo+admAppln.getPersonalData().getMobileNo2();
				}
				if(mobileNo.length()==12){
					UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_ADMISSION_CANCELLATION,admAppln);
				}
			}
			isUpdated = true;
		} catch (Exception e) {
			log.error("Error during updateApplicationNumberCancel..." + e);
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return isUpdated;
	}

	/*
	 * (non-Javadoc) updates student record
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * updateStudentRecord(int)
	 */
	public boolean updateStudentRecord(int studentId,Boolean removeRegNo) throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean isUpdated = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			Student student = (Student) session.get(Student.class, studentId);
			student.setIsAdmitted(false);
			if(removeRegNo)
				student.setRegisterNo(null);
			session.update(student);
			//code added by sudhir
			List<StudentLogin> studentLogin = session.createQuery("from StudentLogin st where st.student.id="+studentId).list();
			if(studentLogin!=null && studentLogin.size()!=0){
				Iterator<StudentLogin> i=studentLogin.iterator();
				while(i.hasNext()){
					StudentLogin s=i.next();
					s.setIsActive(false);
					session.update(s);
				}

			}
			//
			txn.commit();
			isUpdated = true;
		} catch (Exception e) {
			log.error("Error during updateStudentRecord..." + e);
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}

	/*
	 * (non-Javadoc) fetches applicant details for appln no.
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getApplicantDetails(java.lang.String, int, boolean)
	 */
	@Override
	public AdmAppln getApplicantDetails(String applicationNumber,
			int applicationYear, boolean admissionForm) throws Exception {

		Session session = null;
		AdmAppln applicantDetails = null;
		int appNO = 0;
		if (StringUtils.isNumeric(applicationNumber))
			appNO = Integer.parseInt(applicationNumber);
		try {
			//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "";
			if (admissionForm) {
				sql = "from AdmAppln a where a.applnNo=:AppNO and a.appliedYear="
						+ applicationYear
						+ "and a.isFinalMeritApproved=1 and a.isCancelled=0";
			} else {
				sql = "from AdmAppln a where a.applnNo=:AppNO and a.appliedYear="
						+ applicationYear + "and a.isCancelled=0";
			}
			Query qr = session.createQuery(sql);
			qr.setInteger("AppNO", appNO);
			applicantDetails = (AdmAppln) qr.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return applicantDetails;
	}

	/*
	 * (non-Javadoc) fetches approval applicant details
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getApprovalApplicantDetails(java.lang.String, int, boolean)
	 */
	@Override
	public AdmAppln getApprovalApplicantDetails(String applicationNumber,
			int applicationYear, boolean approval) throws Exception {

		Session session = null;
		AdmAppln applicantDetails = null;
		int appNO = 0;
		if (StringUtils.isNumeric(applicationNumber))
			appNO = Integer.parseInt(applicationNumber);
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "";

			sql = "from AdmAppln a where a.applnNo=:AppNO and a.appliedYear="
					+ applicationYear
					+ "and a.isFinalMeritApproved=1 and a.isApproved=0 and a.isCancelled=0";
			Query qr = session.createQuery(sql);
			qr.setInteger("AppNO", appNO);
			applicantDetails = (AdmAppln) qr.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return applicantDetails;
	}

	/*
	 * (non-Javadoc) updates approval
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IAdmissionFormTransaction#updateApproval
	 * (int, int, java.lang.String)
	 */
	@Override
	public boolean updateApproval(int appNO, int appYear, String appRemark,String admthrid)
			throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean isUpdated = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			Query query = session
					.createQuery("from AdmAppln adm where adm.applnNo= :appno  and adm.appliedYear= :year ");
			query.setInteger("appno", appNO);
			query.setInteger("year", appYear);
			AdmAppln admBO = (AdmAppln) query.uniqueResult();
			if (admBO != null) {
				Integer maxNo = 1;
				query = session
						.createQuery("select max(student.programTypeSlNo)"
								+ " from Student student "
								+ " where student.admAppln.courseBySelectedCourseId.program.programType.id = :programTypeId "
								+ " and student.admAppln.appliedYear = :year");
				query.setInteger("programTypeId", admBO.getCourse()
						.getProgram().getProgramType().getId());
				query.setInteger("year", admBO.getAppliedYear().intValue());
				if (query.uniqueResult() != null
						&& !query.uniqueResult().equals("0")) {
					maxNo = (Integer) query.uniqueResult();
					maxNo = maxNo + 1;
				}

				admBO.setIsApproved(true);
				admBO.setApprovalRemark(appRemark);

				//raghu
				if(admthrid!=null && !admthrid.equalsIgnoreCase("")){

					AdmittedThrough through=new AdmittedThrough();
					through.setId(Integer.parseInt(admthrid));
					admBO.setAdmittedThrough(through);
				}

				session.saveOrUpdate(admBO);
				query = session
						.createQuery("update Student st set st.isAdmitted= :sele,st.isActive= :act, st.programTypeSlNo = :maxNo, st.isHide=:hide where st.admAppln.id= :admId");
				query.setBoolean("sele", true);
				query.setBoolean("act", true);
				query.setBoolean("hide", false);
				query.setInteger("maxNo", maxNo.intValue());
				query.setInteger("admId", admBO.getId());
				query.executeUpdate();
			}
			txn.commit();
			isUpdated = true;
		} catch (Exception e) {
			log.error("Error during updateApproval..." + e);
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}

	/*
	 * (non-Javadoc) checks duplicate prerequisite
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * checkDuplicatePrerequisite(int, java.lang.String)
	 */
	@Override
	public boolean checkDuplicatePrerequisite(int examYear, String rollNo)
			throws Exception {
		boolean duplicate = false;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CandidatePrerequisiteMarks c where c.examYear= :examYear  and c.rollNo= :rollNo ");
			query.setInteger("examYear", examYear);
			query.setString("rollNo", rollNo);
			List<CandidatePrerequisiteMarks> bos = query.list();
			if (bos != null && !bos.isEmpty()) {
				duplicate = true;
			}

		} catch (Exception e) {
			log.error("Error during checkDuplicatePrerequisite..." + e);
			duplicate = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return duplicate;
	}

	/*
	 * (non-Javadoc) checks duplicates payment info
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * checkPaymentDetailsDuplicate(java.lang.String, java.lang.String,
	 * java.sql.Date, int, java.lang.String)
	 */
	@Override
	public boolean checkPaymentDetailsDuplicate(String challanNo,
			String journalNo, Date applicationDate, int year) throws Exception {
		boolean duplicate = false;
		Session session = null;
		String quer="";
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			//raghu
			challanNo="SelectedDDPayment";
			if(!challanNo.equalsIgnoreCase("SelectedDDPayment"))
				quer="from AdmAppln adm where adm.challanRefNo= :challanRefNo  and adm.journalNo= :journalNo and adm.date= :date and adm.appliedYear= :appliedYear";
			else 
				quer="from AdmAppln adm where  adm.journalNo= :journalNo and adm.date= :date and adm.appliedYear= :appliedYear";	
			Query query = session.createQuery(quer);
			query.setInteger("appliedYear", year);
			//if(!challanNo.equalsIgnoreCase("SelectedDDPayment"))
			//query.setString("challanRefNo", challanNo);
			query.setString("journalNo", journalNo);
			query.setDate("date", applicationDate);
			// query.setString("name",firstName);
			List<AdmAppln> bos = query.list();
			if (bos != null && !bos.isEmpty()) {
				duplicate = true;
			}

		} catch (Exception e) {
			log.error("Error during checkPaymentDetailsDuplicate..." + e);
			duplicate = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return duplicate;
	}

	/*
	 * (non-Javadoc) checks valid offline appln. no or not
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * checkValidOfflineNumber(int, int)
	 */
	@Override
	public boolean checkValidOfflineNumber(int applicationNumber,
			int appliedyear) throws Exception {
		Session session = null;
		boolean inRange = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from ApplicationNumber a where a.year= :year and  a.isActive=1");
			query.setInteger("year", appliedyear);

			query.setReadOnly(true);
			List<ApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<ApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					ApplicationNumber crsAppNo = (ApplicationNumber) appItr
							.next();
					if (crsAppNo != null) {
						String offlineFrom = crsAppNo.getOfflineApplnNoFrom();
						String offlineTo = crsAppNo.getOfflineApplnNoTo();
						if (offlineFrom != null
								&& !StringUtils.isEmpty(offlineFrom)
								&& offlineTo != null
								&& !StringUtils.isEmpty(offlineTo)
								&& StringUtils.isNumeric(offlineFrom)
								&& StringUtils.isNumeric(offlineTo)) {
							int offlineStart = Integer.parseInt(offlineFrom);
							int offlineEnd = Integer.parseInt(offlineTo);
							if (applicationNumber >= offlineStart
									&& applicationNumber <= offlineEnd) {
								inRange = true;
								break;
							}
						} else {
							inRange = false;
						}
					}
				}
			}

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return inRange;
		} catch (Exception e) {
			log.error("Error during checkValidOfflineNumber..." + e);

			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	/*
	 * (non-Javadoc) fetches eligibity criterias
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getEligibiltyCriteriaForCourse(int, java.lang.Integer)
	 */
	@Override
	public List<EligibilityCriteria> getEligibiltyCriteriaForCourse(
			int courseId, Integer year) throws Exception {
		List<EligibilityCriteria> bos;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EligibilityCriteria elg where elg.course.id= :courseid and elg.year= :YEAR and elg.isActive=1");
			query.setInteger("courseid", courseId);
			query.setInteger("YEAR", year);
			bos = query.list();
		} catch (Exception e) {
			log.error("Error during getEligibiltyCriteriaForCourse..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return bos;
	}

	/*
	 * (non-Javadoc) checks applicant already admitted or not
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * checkAdmittedOrNot(int, int, java.lang.Integer)
	 */
	@Override
	public boolean checkAdmittedOrNot(int applnNo, int courseid,
			Integer appliedYear) throws Exception {
		boolean admitted = false;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Student s where s.admAppln.courseBySelectedCourseId.id= :courseid and s.admAppln.appliedYear= :YEAR and s.admAppln.applnNo= :AppNo and s.isAdmitted=1");
			query.setInteger("courseid", courseid);
			query.setInteger("YEAR", appliedYear);
			query.setInteger("AppNo", applnNo);

			List<Student> students = query.list();
			if (students != null && !students.isEmpty())
				admitted = true;
		} catch (Exception e) {
			log.error("Error during checkAdmittedOrNot..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return admitted;
	}

	/*
	 * (non-Javadoc) fetches application details
	 * 
	 * @seecom.kp.cms.transactions.admission.IAdmissionFormTransaction#
	 * getApprovalApplicantDetails(java.lang.String, int, boolean)
	 */
	@Override
	public AdmAppln getApplicationDetails(String applicationNumber,
			int applicationYear) throws Exception {

		Session session = null;
		AdmAppln applicantDetails = null;
		int appNO = 0;
		if (StringUtils.isNumeric(applicationNumber))
			appNO = Integer.parseInt(applicationNumber);
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "";

			sql = "from AdmAppln a where a.applnNo=:AppNO and a.appliedYear="
					+ applicationYear;
			Query qr = session.createQuery(sql);
			qr.setInteger("AppNO", appNO);
			applicantDetails = (AdmAppln) qr.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return applicantDetails;
	}

	/*
	 * (non-Javadoc) creates new student biodata
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IStudentEditTransaction#createNewStudent
	 * (com.kp.cms.bo.admin.Student)
	 */
	@Override
	public boolean createNewApplicant(Student admBO, AdmissionFormForm admForm) throws Exception {
		boolean result = false;
		Session session = null;
		Transaction transaction=null;
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();

			AdmApplnTO admApplnTO = admForm.getApplicantDetails();
			int year = admBO.getAdmAppln().getAppliedYear();
			admBO.setIsActive(true);

			int courseId = admBO.getAdmAppln().getCourseBySelectedCourseId().getId();				


			String generatedNo = this.saveOnlineAppNo(courseId, year, true,admBO,admForm.getCandidateRefNo(),admForm);

			admApplnTO.setApplnNo(Integer.parseInt(generatedNo));
			admForm.setApplicantDetails(admApplnTO);
			admForm.setApplicationNumber(generatedNo);
			admForm.setDataSaved(true);
			result = true;
		} catch (ConstraintViolationException e) {
			//			if (session.isOpen())
			//				txn.rollback();
			log.error("Error during updating complete student data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			//			if (session.isOpen())
			//				txn.rollback();

			log.error("Error during updating complete student data...", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.admission.IAdmissionFormTransaction#getDocExamsByID
	 * (int)
	 */
	@Override
	public List<DocTypeExams> getDocExamsByID(int id) throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from DocTypeExams c where c.isActive=1 and c.docType.isActive=1 and c.docType.id= :docID");
			query.setInteger("docID", id);
			query.setReadOnly(true);
			List<DocTypeExams> list = query.list();

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	@Override
	public int getAppliedYearForProgram(int programId) throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Program program = (Program) session.createQuery(
					"from Program p where p.isActive=1 and p.id= " + programId)
					.uniqueResult();

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			if (program != null && program.getAcademicYear() != null) {
				return program.getAcademicYear();
			} else {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(cal.YEAR);
				return year;
			}
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}


	/*	public String saveOnlineAppNo(int courseID, int appliedyear,
			boolean isOnline, Student admBO) throws Exception {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			int islock=0;
			//session = HibernateUtil.getSession();

			txn=session.beginTransaction();

			admBO.getAdmAppln().setApplnNo(0);
			session.saveOrUpdate(admBO);

			session.lock(admBO, LockMode.READ);
			// get the online range for course
			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			String onlineFrom = null;
			String onlineTo = null;
			int onlineStart = 0;
			int onlineEnd = 0;
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber appNo = (CourseApplicationNumber) appItr
							.next();
					if (isOnline && appNo.getApplicationNumber() != null) {
						onlineFrom = appNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						onlineTo = appNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						onlineStart = Integer.parseInt(onlineFrom);
						onlineEnd = Integer.parseInt(onlineTo);
					}
				}
			}
			Query q2=null;
			while(islock==0){
				q2=session.createSQLQuery("update applock set applock.islock=1 where applock.islock=0");
				islock=q2.executeUpdate();
				if(islock==0){
					Thread.sleep(100);
				}
			}
			// check max appno in range
			query = session
					.createQuery("select max(a.applnNo) from AdmAppln a where a.appliedYear=:year and a.applnNo BETWEEN :startRange AND :endRange");
			query.setInteger("year", appliedyear);
			// query.setInteger("courseID", courseID);
			query.setInteger("startRange", onlineStart);
			query.setInteger("endRange", onlineEnd);
			query.setReadOnly(true);
			Integer maxNo = (Integer) query.uniqueResult();
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
			} else {
				int generatedInt = 0;

				generatedInt = maxNo;
				generatedInt++;

				generatedNo = String.valueOf(generatedInt);
			}	
			admBO.getAdmAppln().setApplnNo(Integer.parseInt(generatedNo));
			session.update(admBO);
			Query q1=session.createSQLQuery("update applock set applock.islock=0");
			q1.executeUpdate();
			txn.commit();
			session.flush();
			session.close();
			sessionFactory.close();

			return generatedNo;
		} catch (Exception e) {
			log.error("ERROR IN FINAL SAVE", e);
			if (session.isOpen()){
				txn.rollback();
			session.flush();
			session.close();
			}
			throw new ApplicationException(e);
		}
	}

	public String saveOnlineAppNo(int courseID, int appliedyear,
			boolean isOnline, Student admBO) throws Exception {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			txn=session.beginTransaction();

			admBO.getAdmAppln().setApplnNo(0);
			session.saveOrUpdate(admBO);

			session.lock(admBO, LockMode.UPGRADE);
			// get the online range for course
			// 1. Create the query on application_number table
			// 2. Lock the query with the application_number object
			// 3. If isonline = true then Retrieve the current_online_application_no value else r
			// retrieve current_offline_application_no. 
			// New application id = 1 + the value from step 3
			// 4. update the value of the current app and commit the transaction
			// 


			Query query = session
					.createQuery("from CourseApplicationNumber a where a.applicationNumber.year= :year and a.course.id= :courseID and a.isActive=1 and a.applicationNumber.isActive=1");

			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setReadOnly(true);
			String onlineFrom = null;
			String onlineTo = null;
			int onlineStart = 0;
			int onlineEnd = 0;
			List<CourseApplicationNumber> list = query.list();
			if (list != null) {
				Iterator<CourseApplicationNumber> appItr = list.iterator();
				while (appItr.hasNext()) {
					CourseApplicationNumber appNo = (CourseApplicationNumber) appItr
							.next();
					if (isOnline && appNo.getApplicationNumber() != null) {
						onlineFrom = appNo.getApplicationNumber()
								.getOnlineApplnNoFrom();
						onlineTo = appNo.getApplicationNumber()
								.getOnlineApplnNoTo();
						onlineStart = Integer.parseInt(onlineFrom);
						onlineEnd = Integer.parseInt(onlineTo);
					}
				}
			}
			// check max appno in range
			query = session
					.createQuery("select max(a.applnNo) from AdmAppln a where a.appliedYear=:year and a.applnNo BETWEEN :startRange AND :endRange");
			query.setInteger("year", appliedyear);
			// query.setInteger("courseID", courseID);
			query.setInteger("startRange", onlineStart);
			query.setInteger("endRange", onlineEnd);
			query.setReadOnly(true);
			Integer maxNo = (Integer) query.uniqueResult();
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
			} else {
				int generatedInt = 0;

				generatedInt = maxNo;
				generatedInt++;

				generatedNo = String.valueOf(generatedInt);
			}	
			admBO.getAdmAppln().setApplnNo(Integer.parseInt(generatedNo));
			session.update(admBO);
			txn.commit();
			session.flush();
			session.close();
			//sessionFactory.close();

			return generatedNo;
		} catch (Exception e) {
			log.error("ERROR IN FINAL SAVE", e);
			if (session.isOpen()){
				txn.rollback();
			session.flush();
			session.close();
			}
			throw new ApplicationException(e);
		}
	}*/
	public String saveOnlineAppNo(int courseID, int appliedyear,
			boolean isOnline, Student admBO,String candidateRefNo, AdmissionFormForm admForm) throws Exception {
		Session session = null;
		String generatedNo = null;
		Transaction txn=null,txn1=null,transaction=null;
		String onlineFrom = null;
		String onlineTo = null;
		int onlineStart = 0;
		int onlineEnd = 0;
		Integer maxNo =null;
		int generatedInt = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			//session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			// get the online range for course
			// 1. Create the query on application_number table
			// 2. Lock the query with the application_number object
			// 3. If isonline = true then Retrieve the current_online_application_no value else r
			// retrieve current_offline_application_no. 
			// New application id = 1 + the value from step 3
			// 4. update the value of the current app and commit the transaction
			Query query = session.createQuery("select c from CourseApplicationNumber a " +
					" join a.applicationNumber c " +
					" where a.applicationNumber.year= :year and a.course.id= :courseID " +
					" and a.isActive=1 and a.applicationNumber.isActive=1");
			query.setInteger("year", appliedyear);
			query.setInteger("courseID", courseID);
			query.setLockMode("c", LockMode.UPGRADE);
			ApplicationNumber appNo =(ApplicationNumber) query.uniqueResult();
			if (isOnline && appNo!= null) {
				onlineFrom = appNo.getOnlineApplnNoFrom();
				onlineTo = appNo.getOnlineApplnNoTo();
				onlineStart = Integer.parseInt(onlineFrom);
				onlineEnd = Integer.parseInt(onlineTo);
				maxNo = Integer.parseInt(appNo.getCurrentOnlineApplicationNo());
			}
			if (maxNo == null) {
				generatedNo = String.valueOf(onlineStart);
				maxNo =onlineStart;
			} else if (onlineEnd == maxNo) {
				generatedNo = null;
				maxNo=null;
			} else {
				generatedInt = maxNo;
				generatedInt=generatedInt+1;
				maxNo=maxNo+1;
			}
			boolean isExist=false;

			do{
				List<AdmAppln> bos=session.createQuery("from AdmAppln a where a.applnNo="+generatedInt+" and a.appliedYear="+appliedyear).list();
				if(bos!=null && !bos.isEmpty()){
					isExist=true;
					generatedInt=generatedInt+1;
					maxNo=maxNo+1;
				}else{
					isExist=false;
				}
			}while(isExist);

			generatedNo = String.valueOf(generatedInt);
			appNo.setCurrentOnlineApplicationNo(String.valueOf(maxNo));
			session.update(appNo);
			txn.commit();
			txn1=session.beginTransaction();
			admBO.getAdmAppln().setApplnNo(Integer.parseInt(generatedNo));
			session.saveOrUpdate(admBO);
			txn1.commit();
			admForm.setStudentId(admBO.getId());
			if(candidateRefNo!=null && !candidateRefNo.isEmpty()){
				String query1=" from CandidatePGIDetails c where c.candidateRefNo='"+candidateRefNo+"'";
				CandidatePGIDetails candidatePgiBo=(CandidatePGIDetails)session.createQuery(query1).uniqueResult();
				if(admBO.getAdmAppln().getId()>0){
					transaction=session.beginTransaction();
					AdmAppln admAppln=new AdmAppln();
					admAppln.setId(admBO.getAdmAppln().getId());
					candidatePgiBo.setAdmAppln(admAppln);
					session.update(candidatePgiBo);
					transaction.commit();
				}
			}
			session.flush();
			session.close();
			//sessionFactory.close();

			return generatedNo;
		} catch (Exception e) {
			log.error("ERROR IN FINAL SAVE", e);
			if (session.isOpen()){
				if(txn!=null)
					txn.rollback();
				if(txn1!=null)
					txn1.rollback();
				if(transaction!=null)
					transaction.rollback();
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
	}
	@Override
	public List<ExamCenter> getExamCenterForProgram(int programId) throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();


			Query query = session
					.createQuery("from ExamCenter e where e.isActive=1 and e.program.id= " + programId);
			List<ExamCenter> list = query.list();

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	@Override
	public Map<String, Integer> getCourseMapByInputId(String searchQuery)
			throws Exception {

		Session session = null;
		Map<String, Integer> courseMap=new HashMap<String, Integer>();

		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery(searchQuery);
			List<Course> list = query.list();
			Iterator<Course> itr=list.iterator();
			while (itr.hasNext()) {
				Course course = (Course) itr.next();
				if(course.getCode()!=null)
					courseMap.put(course.getCode(),course.getId());
			}

			session.flush();
			session.close();
			return courseMap;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	@Override
	public String getInterviewDateOfApplicant(String applicationNumber,int year)
			throws Exception {
		Session session = null;
		String date=null;
		Date d=null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("select max(i.interview.date) from InterviewCard i where i.admAppln.applnNo="+applicationNumber+" and i.admAppln.appliedYear="+year );
			d =(Date) query.uniqueResult();
			if(d!=null){
				date=CommonUtil.ConvertStringToDateFormat(d.toString(), "yyyy-mm-dd", "dd/mm/yyyy");
			}
			session.flush();
			session.close();
			return date;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/***
	 * 
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughIdForInst(int instId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.university.id is null and college.id = " + instId + " and nationality.id is null and residentCategory.id is null");

			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForInst...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * @param natId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughIdForNationality(int natId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.nationality.id="+natId + " and f.college.id is null and f.university.id is null and f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForNationality...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	
	/**
	 * 
	 * @param natId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughIdForUniveristy(int uniId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.university.id="+uniId + " and college.id is null and nationality.id is null and residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughIdForUniveristy...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughResidentCategory(int resId) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where f.residentCategory.id="+resId + " and f.college.id is null and f.nationality.id is null and f.university.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	

	/**
	 * 
	 * @param instId
	 * @param natId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstNationality(int instId, int natId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + "and" +
					" f.university.id is null and " +
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	

	/**
	 * 
	 * @param instId
	 * @param natId
	 * @param uniId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstNationalityUni(int instId, int natId, int uniId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id =" + natId + " and " +
					" f.university.id  =" + uniId + " and " + 
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}		

	/**
	 * 
	 * @param instId
	 * @param natId
	 * @param uniId
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstNationalityUniRes(int instId, int natId, int uniId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id =" + resId);
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughResidentCategory...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForOtherThanIndia() throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" nationality.name = 'Other'");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForOtherThanIndia...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}			

	/**
	 * 
	 */
	public int getAdmittedThroughForInstUni(int instId, int uniId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id =" + uniId + " and " + 
					" f.residentCategory.id is null");
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstUni...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}		

	/**
	 * 
	 * @param instId
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstRes(int instId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id  is null and " + 
					" f.residentCategory.id =" + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}		
	/***
	 * 
	 */
	public int getAdmittedThroughForNatUni(int natId, int uniId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " +
					" f.residentCategory.id is null " );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}		


	public int getAdmittedThroughForNatRes(int natId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  is null and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param resId
	 * @param uniId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForResUni(int resId, int uniId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id is null and " +
					" f.university.id  = " + uniId + " and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * @param instId
	 * @param natId
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForInstNatRes(int instId, int natId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  is null and " +
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	
	/**
	 * 
	 * @param natId
	 * @param uniId
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForNatUniRes(int natId, int uniId, int resId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id is null and " +
					" f.nationality.id = " + natId + " and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}	

	/**
	 * 
	 * @param uniId
	 * @param resId
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	public int getAdmittedThroughForUniResInst(int uniId, int resId, int instId ) throws Exception {
		Session session = null;
		FeeCriteria feeCriteria;
		int admittedId = 0;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeeCriteria f where " +
					" f.college.id = " + instId + " and " +
					" f.nationality.id is null and " +
					" f.university.id  = " + uniId + " and " + 
					" f.residentCategory.id = " + resId );
			feeCriteria =(FeeCriteria) query.uniqueResult();
			if(feeCriteria!=null){
				admittedId = feeCriteria.getAdmittedThrough().getId();
			}
			session.flush();
			session.close();
			return admittedId;
		} catch (Exception e) {
			log.error("Error in getAdmittedThroughForInstRes...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	@Override
	public Set<Integer> getSubjectGroupByYearAndCourse(Integer appliedYear,
			int id) throws Exception {
		Session session = null;
		Transaction txn = null;
		List subjectGroupList=null;
		Set<Integer> setSubjectGroup=new HashSet<Integer>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q=(Query)session.createQuery("from CurriculumSchemeSubject c " +
					" join c.curriculumSchemeDuration c1 " +
					" join c1.curriculumScheme cs " +
					" where cs.course.id=:cid and cs.year=:year and c1.semesterYearNo=1");
			q.setInteger("cid",id);
			q.setInteger("year",appliedYear);
			subjectGroupList=q.list();
			if(subjectGroupList!=null){
				Iterator itr=subjectGroupList.iterator();
				while (itr.hasNext()) {
					Object[] object = (Object[]) itr.next();
					if(object[0]!=null){
						CurriculumSchemeSubject c=(CurriculumSchemeSubject)object[0];
						if(c.getSubjectGroup()!=null && !setSubjectGroup.contains(c.getSubjectGroup().getId())){
							setSubjectGroup.add(c.getSubjectGroup().getId());
						}
					}

				}
			}
			session.flush();
			session.close();
		} catch (ConstraintViolationException e) {
			log.error("Error during saving complete application data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving complete application data...", e);
			throw new ApplicationException(e);
		}
		return setSubjectGroup;
	}
	@Override
	public AdmAppln getListOfSubmittedDetails(int applicationNo,
			int applicationYear) {
		Session session = null;
		List<Object> list = null;
		AdmAppln admAppln=null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(AdmAppln.class);
			crit.add(Restrictions.eq("applnNo", applicationNo));
			crit.add(Restrictions.eq("appliedYear", applicationYear));
			crit.setMaxResults(1);
			list = crit.list(); 
			admAppln=(AdmAppln) list.get(0);
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		if (list == null || list.size() < 1) {
			return null;
		}
		return admAppln;
	}	
	public int getProgrameIdForCourse(int courseId) throws Exception {
		log.debug("inside getProgrameId");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select c.program.id from Course c where c.isActive = 1 and c.id = "+courseId);
			Integer programId = (Integer) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getPrograme");
			if(programId!= null){
				return programId.intValue();
			}
			else{
				return 0;
			}

		} catch (Exception e) {
			log.error("Error during getting program...",e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<DocChecklist> getRequiredDocsForCourseAndProgram(int courseId,
			int programId, int applicationYear) {
		// TODO Auto-generated method stub
		Session session = null;
		List<DocChecklist> list = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from DocChecklist doc where doc.isActive = 1 and doc.program.id= :programId and doc.course.id= :courseId and doc.year= :applicationYear");
			query.setParameter("programId", programId);
			query.setParameter("courseId", courseId);
			query.setParameter("applicationYear", applicationYear);
			list = query.list(); 
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		if (list == null || list.size() < 1) {
			return new ArrayList<DocChecklist>();
		}
		return list;
	}

	@Override
	public List<ApplnDoc> getListOfDocuments(String applicationNumber,
			int applicationYear) {
		Session session = null;
		List<ApplnDoc> list=new ArrayList<ApplnDoc>();
		try {
			//			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//			session = sessionFactory.openSession();
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ApplnDoc a where a.admAppln.applnNo ="+applicationNumber +
					" and a.admAppln.appliedYear="+applicationYear);
			list= query.list();
			session.flush();
			//			session.close();
		} catch (Exception e) {
			log.error("Error in getListOfDocuments...", e);
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	/**
	 * This method is used to check existence of application number, courseId
	 * and admission year.
	 */

	@Override
	public AdmAppln checkApplicationNumberForCancel(String searchCriteria)
			throws Exception {
		Session session = null;

		AdmAppln admAppln=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			List<AdmAppln> list = session.createQuery(searchCriteria).list();
			if(list!=null && !list.isEmpty()){
				admAppln=list.get(0);
			}
		} catch (Exception e) {
			log.error("Error during checkApplicationNumberCancel...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return admAppln;
	}

	@Override
	public Map<String, Integer> getCourseMap() throws Exception {
		Session session = null;
		Map<String, Integer> courseMap=new HashMap<String, Integer>();

		try {
			String quer="from Course c where c.isActive=1";
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery(quer);
			List<Course> list = query.list();
			Iterator<Course> itr=list.iterator();
			while (itr.hasNext()) {
				Course course = (Course) itr.next();
				if(course.getCode()!=null)
					courseMap.put(course.getCode(),course.getId());
			}

			session.flush();
			session.close();
			return courseMap;
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
	}

	/*public AdmAppln getApplicantStatusDetails(String applicationNumber,int applicationYear,int courseId) throws Exception{

		Session session = null;
		AdmAppln applicantDetails = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			applicantDetails = (AdmAppln) session.createQuery("from AdmAppln a where a.applnNo="+applicationNumber+" and a.appliedYear="+applicationYear+" and a.course.id="+courseId).uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return applicantDetails;
	}
}*/

	public List getApplicantStatusDetails(String applicationNumber,int applicationYear,int courseId) throws Exception {
		Session session = null;
		List dataList = null;
		try {
			session = HibernateUtil.getSession();

			String dataQuery="select a.adm_status, mid(a.adm_status,1,50) from adm_appln a where a.appln_no="+applicationNumber+" and a.course_id="+courseId+" and a.applied_year="+applicationYear;
			Query selCandidatesQuery=session.createSQLQuery(dataQuery);
			dataList = selCandidatesQuery.list();
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

	@SuppressWarnings("unchecked")
	public Map<String, String> getMonthMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from PrerequisitsYearMonth pre where pre.isActive=true group by pre.month");
			List<PrerequisitsYearMonth> list=query.list();
			if(list!=null){
				Iterator<PrerequisitsYearMonth> iterator=list.iterator();
				while(iterator.hasNext()){
					PrerequisitsYearMonth pre=iterator.next();
					if(pre!=null && pre.getMonth()!=null && pre.getMonth()>0 && pre.getId()>0)
					{
						if(pre.getMonth()==1)
							pre.setMonthName("JAN");
						if(pre.getMonth()==2)
							pre.setMonthName("FEB");
						if(pre.getMonth()==3)
							pre.setMonthName("MAR");
						if(pre.getMonth()==4)
							pre.setMonthName("APR");
						if(pre.getMonth()==5)
							pre.setMonthName("MAY");
						if(pre.getMonth()==6)
							pre.setMonthName("JUN");
						if(pre.getMonth()==7)
							pre.setMonthName("JUL");
						if(pre.getMonth()==8)
							pre.setMonthName("AUG");
						if(pre.getMonth()==9)
							pre.setMonthName("SEP");
						if(pre.getMonth()==10)
							pre.setMonthName("OCT");
						if(pre.getMonth()==11)
							pre.setMonthName("NOV");
						if(pre.getMonth()==12)
							pre.setMonthName("DEC");
						map.put(String.valueOf(pre.getMonth()),pre.getMonthName());
					}
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}


	@SuppressWarnings("unchecked")
	public Map<String, String> getYearByMonth(String Month) throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from PrerequisitsYearMonth pre where pre.isActive=true and pre.month="+ Month +" group by  pre.year");
			List<PrerequisitsYearMonth> list=query.list();
			if(list!=null){
				Iterator<PrerequisitsYearMonth> iterator=list.iterator();
				while(iterator.hasNext()){
					PrerequisitsYearMonth pre=iterator.next();
					if(pre!=null && pre.getYear()!=null && pre.getYear()>0 && pre.getId()>0)
						map.put(String.valueOf(pre.getYear()),String.valueOf(pre.getYear()));
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getYear() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from PrerequisitsYearMonth pre where pre.isActive=true group by pre.year");
			List<PrerequisitsYearMonth> list=query.list();
			if(list!=null){
				Iterator<PrerequisitsYearMonth> iterator=list.iterator();
				while(iterator.hasNext()){
					PrerequisitsYearMonth pre=iterator.next();
					if(pre!=null && pre.getYear()!=null && pre.getYear()>0 && pre.getId()>0)
						map.put(String.valueOf(pre.getYear()),String.valueOf(pre.getYear()));
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getMails()
	 */
	@Override
	public List<GenerateMail> getMails() throws Exception {
		Session session = null;
		List<GenerateMail> messageList = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query selectedCandidatesQuery=session.createQuery("from GenerateMail m ");
			selectedCandidatesQuery.setMaxResults(15);
			messageList = selectedCandidatesQuery.list();
			return messageList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#generateCandidateRefNo(com.kp.cms.bo.admin.CandidatePGIDetails)
	 */
	@Override
	public synchronized String generateCandidateRefNo(CandidatePGIDetails bo) throws Exception {
		Session session=null;
		Transaction transaction=null;
		String candidateRefNo="";
		try {
			session=HibernateUtil.getSession();
			Query hql = session.createQuery("select isAided from StudentOnlineApplication where id =:uniqueId");
			hql.setParameter("uniqueId", bo.getUniqueId().getId());
			boolean aided = (Boolean)hql.uniqueResult();
			transaction=session.beginTransaction();
			if(bo!=null){
				session.save(bo);
			}
			transaction.commit();
			int savedId=bo.getId();
			if(savedId>0){
				transaction=session.beginTransaction();
				if(!aided)
					candidateRefNo="SFBM"+String.valueOf(savedId);
				else
					candidateRefNo="RFBM"+String.valueOf(savedId);
				bo.setCandidateRefNo(candidateRefNo);
				bo.setTxnRefNo(CMSConstants.PGI_MERCHANT_ID+candidateRefNo);
				session.update(bo);
				transaction.commit();
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e);
			throw  new ApplicationException(e);
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return candidateRefNo;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#updateReceivedStatus(com.kp.cms.bo.admin.CandidatePGIDetails)
	 */
	@Override
	public boolean updateReceivedStatus(CandidatePGIDetails bo,AdmissionFormForm admForm)
			throws Exception {
		log.info("Entered into generateCandidateRefNo-AdmissionFormTransactionImpl");
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		admForm.setIsTnxStatusSuccess(false);
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(bo!=null){
				String query=" from CandidatePGIDetails c where c.candidateRefNo='"+bo.getCandidateRefNo()
				+"' and c.txnAmount="+admForm.getApplicationAmount()+" and c.txnStatus='Pending'";
				CandidatePGIDetails candidatePgiBo=(CandidatePGIDetails)session.createQuery(query).uniqueResult();
				if(candidatePgiBo!=null){
					candidatePgiBo.setTxnRefNo(bo.getTxnRefNo());
					//candidatePgiBo.setBankId(bo.getBankId());
					candidatePgiBo.setBankRefNo(bo.getBankRefNo());
					candidatePgiBo.setTxnDate(bo.getTxnDate());
					candidatePgiBo.setTxnStatus(bo.getTxnStatus());

					//raghu
					candidatePgiBo.setMode(bo.getMode());
					candidatePgiBo.setUnmappedStatus(bo.getUnmappedStatus());
					candidatePgiBo.setMihpayId(bo.getMihpayId());
					candidatePgiBo.setPgType(bo.getPgType());
					candidatePgiBo.setAdditionalCharges(bo.getAdditionalCharges());

					session.update(candidatePgiBo);
					if(bo.getTxnStatus()!=null && bo.getTxnStatus().equalsIgnoreCase("Success")){
						admForm.setIsTnxStatusSuccess(true);
						admForm.setPaymentSuccess(true);
					}
					admForm.setCandidateRefNo(candidatePgiBo.getCandidateRefNo());
					admForm.setTransactionRefNO(bo.getTxnRefNo());
					isUpdated=true;
				}
			}
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e);
			throw  new ApplicationException(e);
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return isUpdated;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getPaidCandidateDetails(java.lang.String)
	 */
	@Override
	public List<CandidatePGIDetails> getPaidCandidateDetails(String query) throws Exception {
		log.info("Entered into getPaidCandidateDetails-AdmissionFormTransactionImpl");
		Session session=null;
		/* modified by sudhir 
		 * changed the return type from CandidatePGIDetails to List<CandidatePGIDetails>
		 * */
		List<CandidatePGIDetails> candidatePGIDetails;
		try {
			session=HibernateUtil.getSession();
			candidatePGIDetails=session.createQuery(query).list();

		} catch (Exception e) {
			if (session != null){
				session.flush();
				session.close();
			}
			log.error("Error in getPaidCandidateDetails-AdmissionFormTransactionImpl..."+e);
			throw  new ApplicationException(e);
		}
		log.info("Exit getPaidCandidateDetails-AdmissionFormTransactionImpl");
		return candidatePGIDetails;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getApplicantFeedback()
	 */
	@Override
	public List<ApplicantFeedback> getApplicantFeedback() throws Exception {
		log.info("Entered into getApplicantFeedback-AdmissionFormTransactionImpl");
		Session session=null;
		List<ApplicantFeedback> applicantFeedback;
		try {
			session=HibernateUtil.getSession();
			applicantFeedback=session.createQuery("from ApplicantFeedback a where a.isActive=1").list();

		} catch (Exception e) {
			if (session != null){
				session.flush();
				session.close();
			}
			log.error("Error in getApplicantFeedback-AdmissionFormTransactionImpl..."+e);
			throw  new ApplicationException(e);
		}
		log.info("Exit getApplicantFeedback-AdmissionFormTransactionImpl");
		return applicantFeedback;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#checkWorkExperianceMandatory(java.lang.String)
	 */
	@Override
	public boolean checkWorkExperianceMandatory(String courseId)
			throws Exception {
		boolean isMandatory = false;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			isMandatory = (Boolean)session.createQuery("select c.isWorkExperienceMandatory from Course c where c.id = "+courseId).uniqueResult();
		}catch (Exception e) {
			if(session != null){
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
		return isMandatory;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getPrerequisiteMarks(java.lang.String)
	 */
	@Override
	public List<CandidatePrerequisiteMarks> getPrerequisiteMarks(
			String applicationNumber) throws Exception {
		Session session = null;
		List<CandidatePrerequisiteMarks> list = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from CandidatePrerequisiteMarks c where c.admAppln.applnNo="+applicationNumber);
			list = query.list();
		} catch (Exception e) {
			if(session != null){
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
		return list;
	}

	public String getCandidatePGIDetails(int admApplnId) throws Exception
	{
		Session session = null;
		String txnRefNo = null;
		int appNO = 0;

		try {
			session = HibernateUtil.getSession();
			String query = "";
			query="select cp.txnRefNo from CandidatePGIDetails cp " +
					"where cp.admAppln.id is not null " +
					"and cp.admAppln.id="+ admApplnId +" and cp.txnRefNo is not null";
			Query qr = session.createQuery(query);
			txnRefNo = (String) qr.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//			session.close();
			}
		}
		return txnRefNo;
	}

	public CandidatePGIDetails getCandidateDetails(int admApplnId) throws Exception {
		Session session = null;
		CandidatePGIDetails details;
		try{
			session = HibernateUtil.getSession();
			String query = "from CandidatePGIDetails c where c.txnRefNo is not null and c.admAppln.id="+ admApplnId;
			details = (CandidatePGIDetails)session.createQuery(query).uniqueResult();
		}catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return details;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getStudentMails()
	 */
	@Override
	public List<CandidatePGIDetails> getStudentMails() throws Exception {
		Session session = null;
		List<CandidatePGIDetails> mailIds = null;
		try{
			session = HibernateUtil.getSession();
			String query =  "from CandidatePGIDetails c " +
					"where c.refundGenerated=0 and c.admAppln is null" +
					" and c.txnStatus='Success' and (c.mailCount<3 or c.mailCount is null) ";
			mailIds = session.createQuery(query).list();
		}catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return mailIds;
	}

	@Override
	public List<PhdDocumentSubmissionSchedule> DocumentPendingMail(String mode) throws Exception {
		Session session = null;
		List<PhdDocumentSubmissionSchedule> mailIds = new ArrayList<PhdDocumentSubmissionSchedule>();
		Integer setting=null;
		try{
			session = HibernateUtil.getSession();
			if(mode.equalsIgnoreCase("PendingMail")){
				String quer=" select bo.reminderMailBefore from PhdSettingBO bo where bo.isActive=1";
				setting=(Integer)session.createQuery(quer).uniqueResult();
				String query =  " from PhdDocumentSubmissionSchedule documet " +
						" where documet.isReminderMail=1" +
						" and documet.submited=0 " +
						" and documet.isActive=1 " +
						" and documet.assignDate is not null " +
						" and DATEDIFF(documet.assignDate,current_date())="+setting;
				mailIds = session.createQuery(query).list();
			}if(mode.equalsIgnoreCase("DueMail")){
				String quer=" select bo.dueMailAfter from PhdSettingBO bo where bo.isActive=1";
				setting=(Integer) session.createQuery(quer).uniqueResult();
				String query =  " from PhdDocumentSubmissionSchedule documet " +
						" where documet.isReminderMail=1" +
						" and documet.submited=0 " +
						" and documet.isActive=1 " +
						" and documet.assignDate is not null " +
						" and DATEDIFF(current_date(),documet.assignDate)="+setting;
				mailIds = session.createQuery(query).list();
			}}catch (Exception e) {
				log.error("Error while getting applicant details..." + e);
				throw new ApplicationException(e);
			}finally {
				if (session != null) {
					session.flush();
					//				session.close();
				}
			}
		return mailIds;
	}

	@Override
	public List<PhdDocumentSubmissionSchedule> PendingDocumentSearch(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm)throws Exception {
		log.info("call of getPendingDocumentSearch method in PhdDocumentSubmissionScheduleTransactionImpl class.");
		Session session = null;
		ArrayList<Integer> courseId = new ArrayList<Integer>();
		if(documentSubmissionScheduleForm.getSelectedcourseId()!=null){
			String[] co=documentSubmissionScheduleForm.getSelectedcourseId();
			for (int i = 0; i <co.length; i++) {
				if(!co[i].isEmpty()){
					courseId.add(Integer.parseInt(co[i]));
				}
			}
		}
		List<PhdDocumentSubmissionSchedule> pendingDocument =null;
		try{
			session = HibernateUtil.getSession(); 
			String str= " from PhdDocumentSubmissionSchedule documet " +
					" where documet.isReminderMail=1 "+
					" and documet.submited=0 "+
					" and documet.isActive=1 "+
					" and documet.assignDate <='"+CommonUtil.ConvertStringToSQLDate(documentSubmissionScheduleForm.getStartDate())+"'"+
					" and documet.studentId.admAppln.appliedYear="+documentSubmissionScheduleForm.getYear();

			if(courseId!=null && !courseId.isEmpty()){
				str=str+" and documet.studentId.admAppln.courseBySelectedCourseId.id in (:courses)" ;
			}
			Query query = session.createQuery(str);
			if(courseId!=null && !courseId.isEmpty()){
				query.setParameterList("courses", courseId);
			}
			pendingDocument= query.list();
			log.info("end of getPendingDocumentSearch method in PhdDocumentSubmissionScheduleTransactionImpl class.");
			return pendingDocument;
		}catch (Exception exception) {
			if( session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(exception);
		}


	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getExamTimeTableDetailsForPreviousDay(java.sql.Date)
	 */
	@Override
	public List<Object[]> getExamTimeTableDetailsForPreviousDay( Date previousDayDate)throws Exception {
		Session session  = null;
		List<Object[]> timeTableBOs  = null;

		try{
			session = HibernateUtil.getSession();
			String timetableQuery = " select course.exam_id,examTimeTable.subject_id from EXAM_time_table examTimeTable" +
					" join EXAM_exam_course_scheme_details course on course.id=examTimeTable.exam_course_scheme_id "+
					" where date(examTimeTable.date_starttime)='"+previousDayDate+"'"+
					" and examTimeTable.is_active =1 " +
					" group by examTimeTable.subject_id,course.exam_id" ;
			Query query = session.createSQLQuery(timetableQuery);
			timeTableBOs = query.list();

		}catch (Exception exception) {
			if( session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(exception);
		}
		return timeTableBOs;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getvaluationScheduleDeatils(java.util.Map.Entry)
	 */
	@Override
	public List<ExamValuationScheduleDetails> getvaluationScheduleDeatils(int examId,List<Integer> subjectIds) throws Exception {
		List<ExamValuationScheduleDetails> scheduleDetails;
		Session session =null;
		try{
			session = HibernateUtil.getSession();
			String valuationDetailsQuery = "from ExamValuationScheduleDetails valuationScheduleDetails " +
					" where valuationScheduleDetails.exam.id="+examId+
					" and valuationScheduleDetails.subject.id in (:subjectListids)" +
					" and valuationScheduleDetails.isActive = 1";
			Query query = session.createQuery(valuationDetailsQuery);
			query.setParameterList("subjectListids", subjectIds);
			scheduleDetails = query.list();
		}catch (Exception exception) {
			if( session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(exception);
		}
		return scheduleDetails;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getNoOfScriptsByExamIdSubjId(int, java.util.List)
	 */
	@Override
	public Map<Integer, Map<Integer, Integer>> getNoOfScriptsByExamIdSubjId( int examId, List<Integer> subjectIds) throws Exception {
		Map<Integer, Map<Integer, Integer>> examSubjectWiseNoOfPapersMap = new HashMap<Integer, Map<Integer,Integer>>();
		Session session =null;
		try{
			session = HibernateUtil.getSession();
			Iterator<Integer> iterator = subjectIds.iterator();
			while (iterator.hasNext()) {
				Integer subjectId = (Integer) iterator.next();

			}

		}catch (Exception exception) {
			if( session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(exception);
		}
		return examSubjectWiseNoOfPapersMap;
	}
	@Override
	public List<Object[]> SendMailForAm(int year) throws Exception{
		Session session = null;
		List<Object[]> mailIds =null;
		try{
			session = HibernateUtil.getSession();
			String query =" select distinct employee.id as eid,employee.first_name as ename,employee.work_email as email,employee.current_address_mobile1," +
					" guest_faculty.id as gid,guest_faculty.first_name as gname,guest_faculty.email as ggmail,guest_faculty.mobile as gmobile," +
					" subject.id as sid,subject.code,subject.name as sname,DATE_FORMAT(EXAM_time_table.date_starttime,'%d/%m/%Y') as examdate,date_format(EXAM_time_table.date_starttime,'%h:%i %p') as esessions " +
					" from EXAM_time_table " +
					" inner join subject on EXAM_time_table.subject_id = subject.id " +
					" inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " +
					" and subject_group_subjects.is_active=1 " +
					" inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id " +
					" inner join curriculum_scheme_subject on curriculum_scheme_subject.subject_group_id = subject_group.id " +
					" inner join curriculum_scheme_duration ON curriculum_scheme_subject.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
					" left join EXAM_exam_course_scheme_details ON EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " +
					" and curriculum_scheme_duration.semester_year_no=EXAM_exam_course_scheme_details.scheme_no " +
					" and EXAM_exam_course_scheme_details.is_active=1 " +
					" inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id " +
					" and curriculum_scheme_duration.academic_year=EXAM_definition.academic_year " +
					" inner join exam_type ON EXAM_definition.exam_type_id = exam_type.id " +
					" inner join course ON EXAM_exam_course_scheme_details.course_id = course.id " +
					" inner join program ON course.program_id = program.id " +
					" inner join teacher_class_subject on teacher_class_subject.subject_id=subject.id " +
					" inner join class_schemewise on teacher_class_subject.class_schemewise_id = class_schemewise.id " +
					" and class_schemewise.curriculum_scheme_duration_id=curriculum_scheme_duration.id " +
					" inner join users on teacher_class_subject.teacher_id = users.id " +
					" and users.is_active=1 " +
					" and users.active=1 " +
					" left join employee ON users.employee_id = employee.id " +
					" and employee.is_active=1 " +
					" and employee.active=1 " +
					" left join guest_faculty on users.guest_id = guest_faculty.id " +
					" and guest_faculty.is_active=1 " +
					" and guest_faculty.active=1 " +
					" where subject.is_certificate_course=0 " +
					" and EXAM_time_table.is_active=1 " +
					" and EXAM_time_table.is_active=1 " +
					" and exam_type.name like '%regu%' " +
					" and exam_type.name not like '%supp%' " +
					//	" and DATE_FORMAT(EXAM_time_table.date_starttime,'%p')='AM' " +
					" and TIME(EXAM_time_table.date_starttime) >= '00:00:00' " +
					" and TIME(EXAM_time_table.date_starttime) <= '11:59:59' " +
					" and DATE_FORMAT(EXAM_time_table.date_starttime,'%Y-%m-%d')=ADDDATE(DATE_FORMAT(CURDATE(),'%Y-%m-%d'),1)" +
					" and curriculum_scheme_duration.academic_year="+year +
					" group by subject.id,users.id,EXAM_time_table.date_starttime ";

			mailIds = session.createSQLQuery(query).list();

		}catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return mailIds;
	}

	@Override
	public List<Object[]> SendMailForPm(int year) throws Exception {
		Session session = null;
		List<Object[]> mailIds =null;

		try{
			session = HibernateUtil.getSession();
			String query =" select distinct employee.id as eid,employee.first_name as ename,employee.work_email as email,employee.current_address_mobile1," +
					" guest_faculty.id as gid,guest_faculty.first_name as gname,guest_faculty.email as ggmail,guest_faculty.mobile as gmobile," +
					" subject.id as sid,subject.code,subject.name as sname,DATE_FORMAT(EXAM_time_table.date_starttime,'%d/%m/%Y') as examdate,date_format(EXAM_time_table.date_starttime,'%h:%i %p') as esessions " +
					" from EXAM_time_table " +
					" inner join subject on EXAM_time_table.subject_id = subject.id " +
					" inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " +
					" and subject_group_subjects.is_active=1 " +
					" inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id " +
					" inner join curriculum_scheme_subject on curriculum_scheme_subject.subject_group_id = subject_group.id " +
					" inner join curriculum_scheme_duration ON curriculum_scheme_subject.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
					" left join EXAM_exam_course_scheme_details ON EXAM_time_table.exam_course_scheme_id = EXAM_exam_course_scheme_details.id " +
					" and curriculum_scheme_duration.semester_year_no=EXAM_exam_course_scheme_details.scheme_no " +
					" and EXAM_exam_course_scheme_details.is_active=1 " +
					" inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id " +
					" and curriculum_scheme_duration.academic_year=EXAM_definition.academic_year " +
					" inner join exam_type ON EXAM_definition.exam_type_id = exam_type.id " +
					" inner join course ON EXAM_exam_course_scheme_details.course_id = course.id " +
					" inner join program ON course.program_id = program.id " +
					" inner join teacher_class_subject on teacher_class_subject.subject_id=subject.id " +
					" inner join class_schemewise on teacher_class_subject.class_schemewise_id = class_schemewise.id " +
					" and class_schemewise.curriculum_scheme_duration_id=curriculum_scheme_duration.id " +
					" inner join users on teacher_class_subject.teacher_id = users.id " +
					" and users.is_active=1" +
					" and users.active=1" +
					" left join employee ON users.employee_id = employee.id " +
					" and employee.is_active=1 " +
					" and employee.active=1" +
					" left join guest_faculty on users.guest_id = guest_faculty.id " +
					" and guest_faculty.is_active=1" +
					" and guest_faculty.active=1 " +
					" where subject.is_certificate_course=0 " +
					" and EXAM_time_table.is_active=1 " +
					" and EXAM_time_table.is_active=1 " +
					" and exam_type.name like '%regu%' " +
					" and exam_type.name not like '%supp%' " +
					" and TIME(EXAM_time_table.date_starttime) > '11:59:59' " +
					" and TIME(EXAM_time_table.date_starttime) <= '23:59:59' " +
					" and DATE_FORMAT(EXAM_time_table.date_starttime,'%Y-%m-%d')=DATE_FORMAT(CURDATE(),'%Y-%m-%d')" +
					" and curriculum_scheme_duration.academic_year="+year+
					" group by subject.id,users.id,EXAM_time_table.date_starttime";

			mailIds = session.createSQLQuery(query).list();

		}catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//				session.close();
			}
		}
		return mailIds;
	}
	@SuppressWarnings("unchecked")
	public List<InterviewSelectionSchedule> getInterviewSelectionScheduleByPgmId(int programId, int year)throws Exception{
		List<InterviewSelectionSchedule> schedule=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("select iss from InterviewSelectionSchedule iss "+
					"left join iss.programId p "+
					"where  iss.isActive=1 and p.isOpen=1 and p.id= "+ programId
					+" and p.isActive=1 and iss.academicYear= '"+year+"'");
			schedule = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return schedule;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]>  getInterviewSelectionScheduleOnline(int programId, int year)throws Exception{
		List<Object[]>  schedule=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createSQLQuery("select interview_selection_schedule.id, " +
					"interview_selection_schedule.venue_id, interview_selection_schedule.max_no_seats_online, "+
					"interview_selection_schedule.max_no_seats_offline,interview_selection_schedule.cut_off_date, "+
					"date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y'), "+
					"count(adm_appln.id) as total_applied, exam_center.center " +
					"from interview_selection_schedule "+
					"left join adm_appln on adm_appln.interview_schedule_id = interview_selection_schedule.id and adm_appln.mode='Online'"+
					"inner join exam_center on interview_selection_schedule.venue_id= exam_center.id "+
					"where interview_selection_schedule.program_id= "+programId +" and interview_selection_schedule.academic_year="+year
					+ " and interview_selection_schedule.cut_off_date>=curdate() and interview_selection_schedule.is_active=1 "+
					"group by interview_selection_schedule.id" );


			schedule = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return schedule;
	}

	public List<Object[]>  getInterviewSelectionScheduleOffline(int programId, int year)throws Exception{
		List<Object[]>  schedule=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createSQLQuery("select interview_selection_schedule.id, interview_selection_schedule.venue_id, interview_selection_schedule.max_no_seats_online, "+
					"interview_selection_schedule.max_no_seats_offline,interview_selection_schedule.cut_off_date, "+
					"date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y'), "+
					"count(adm_appln.id) as total_applied, exam_center.center " +
					"from interview_selection_schedule "+
					"left join adm_appln on adm_appln.interview_schedule_id = interview_selection_schedule.id and adm_appln.mode!='Online'"+
					"inner join exam_center on interview_selection_schedule.venue_id= exam_center.id "+
					"where interview_selection_schedule.program_id= "+programId +" and interview_selection_schedule.academic_year="+year
					+ " and interview_selection_schedule.cut_off_date>=curdate() and interview_selection_schedule.is_active=1"+
					" group by interview_selection_schedule.id" );


			schedule = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return schedule;
	}


	public List<Object[]> getPreviousInterviewSelectionSchedule(int programId, int year, boolean isOnlineApply)throws Exception{
		List<Object[]> schedule=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();

			/*Query query=session.createQuery("select count(adm.intSelectionId), iss.id "+
											"from AdmInterviewSelectionSchedule adm "+
											"join adm.intSelectionId iss "+
											"join iss.programId p "+
											"where iss.isActive=1  "+
											"and p.id='"+programId+"' and p.isOpen=1 and adm.applnId.isCancelled=0 "+
										"and p.isActive=1 and iss.academicYear='"+year+"'");*/
			Query query=null;	
			if(isOnlineApply){
				query=session.createQuery("select count(adm.interScheduleSelection.id), iss.id "+
						"from AdmAppln adm "+
						"join adm.interScheduleSelection iss "+
						"join iss.programId p "+
						"where iss.isActive=1  "+
						"and p.id='"+programId+"' and p.isOpen=1 and adm.isCancelled=0 and adm.mode='Online'"+
						"and p.isActive=1 and iss.academicYear='"+year+"' group by adm.interScheduleSelection.id order by iss.selectionProcessDate");
			}else
			{
				query=session.createQuery("select count(adm.interScheduleSelection.id), iss.id "+
						"from AdmAppln adm "+
						"join adm.interScheduleSelection iss "+
						"join iss.programId p "+
						"where iss.isActive=1  "+
						"and p.id='"+programId+"' and p.isOpen=1 and adm.isCancelled=0 and adm.mode!='Online'"+
						"and p.isActive=1 and iss.academicYear='"+year+"' group by adm.interScheduleSelection.id order by iss.selectionProcessDate");
			}
			schedule = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return schedule;
	}

	/*public List<InterviewVenueSelection> getInterviewVenuSelection(int programId)throws Exception{
		List<InterviewVenueSelection> venue=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();

			Query query = session.createQuery("select v from InterviewVenueSelection v left join v.interviewSelectionSchedule s left join s.programId p where v.isActive=1 and s.isActive=1 and p.id="+programId+" and p.isActive=1" );
					//"from InterviewVenueSelection v where v.isActive=1 where ");
			venue = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return venue;
	}

	public List<InterviewTimeSelection> getInterviewTimeSelection(int programId)throws Exception{
		List<InterviewTimeSelection> time=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();

			Query query = session.createQuery("select v from InterviewTimeSelection v left join v.interviewSelectionSchedule s left join s.programId p where v.isActive=1 and s.isActive=1 and p.id="+programId+" and p.isActive=1");
			time = query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return time;
	}*/

	public int getExamCenterFromInterviewVenue(String interviewVenue, AdmissionFormForm admForm)throws Exception{
		int CenterId=0;
		InterviewVenueSelection venue;
		Session session = null;
		try {
			session = HibernateUtil.getSession();

			Query query = session.createQuery("from InterviewVenueSelection v where v.isActive=1 and v.id='"+interviewVenue+"'");
			venue = (InterviewVenueSelection) query.uniqueResult();
			if(venue.getExamCenter().getId()>0){
				CenterId=venue.getExamCenter().getId();
				admForm.setSelectedVenue(venue.getExamCenter().getCenter());
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return CenterId;
	}


	public void getDateFromSelectionProcessId(String InterviewSelectionDate, AdmissionFormForm admForm)throws Exception
	{
		InterviewSelectionSchedule schedule;
		Session session = null;
		try {
			session = HibernateUtil.getSession();

			Query query = session.createQuery("from InterviewSelectionSchedule v where v.isActive=1 and v.id='"+InterviewSelectionDate+"'");
			schedule = (InterviewSelectionSchedule) query.uniqueResult();
			if(schedule.getSelectionProcessDate()!=null){
				admForm.setSelectedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(schedule.getSelectionProcessDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}


	public InterviewCardTO getInterviewScheduleDetails(AdmissionFormForm admForm)throws Exception{
		InterviewCardTO cardTo=new InterviewCardTO();
		List<Object[]> inter;
		Object[]ObjExamCenter;
		Session session = null;
		String Venue=null;
		try {
			session = HibernateUtil.getSession();
			Query examCenterQuery = session.createQuery("select ec.address1, ec.address2, ec.center "+
					"from ExamCenter ec "+
					"where ec.isActive=1 and ec.id= "+admForm.getInterviewVenue());	
			ObjExamCenter = (Object[]) examCenterQuery.uniqueResult();
			if(ObjExamCenter!=null){
				if(ObjExamCenter[0]!=null && ObjExamCenter[1]!=null && ObjExamCenter[2]!=null ){
					Venue=ObjExamCenter[0].toString()+","+ObjExamCenter[1].toString()+","+ObjExamCenter[2].toString();
				}
			}
			Query query1 = session.createSQLQuery("select interview_time_selection.max_seats, "+
					"count(distinct interview_card.adm_appln_id) as total_generated, "+
					"interview_time_selection.time, "+
					"interview_time_selection.end_time, date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y') "+
					"from interview_time_selection "+
					"inner join interview_selection_schedule ON interview_time_selection.select_schedule_id = interview_selection_schedule.id "+
					"left join interview_schedule on interview_schedule.interview_selection_schedule_id = interview_selection_schedule.id "+
					/*"and interview_time_selection.time=interview_schedule.start_time "+
												"and interview_time_selection.end_time=interview_schedule.end_time "+*/
												"left join interview_card on interview_card.interview_schedule_id = interview_schedule.id "+
												"and interview_card.reporting_time=interview_time_selection.time "+
												"where interview_selection_schedule.id="+ admForm.getInterviewSelectionDate()
												+" and interview_time_selection.is_active=1 "+
												"group by interview_selection_schedule.id,interview_time_selection.time "+
					"order by interview_time_selection.time");
			inter =  query1.list();

			if(inter!=null){

				Iterator<Object[]> itr= inter.iterator();
				while (itr.hasNext()) {
					boolean flag=false;
					Object[] obj = (Object[]) itr.next();

					if(obj[0].toString()!=null && Integer.parseInt(obj[0].toString())>0)
					{
						if(Integer.parseInt(obj[0].toString())>Integer.parseInt(obj[1].toString()))
						{
							if(obj[4].toString()!=null){
								cardTo.setInterviewDate(obj[4].toString());
								cardTo.setInterviewDate(obj[4].toString());
							}
							if(obj[2].toString()!=null){
								cardTo.setTime(obj[2].toString());
							}
							if(obj[3].toString()!=null){
								cardTo.setEndtime(obj[3].toString());
							}
							if(Venue!=null){
								cardTo.setVenue(Venue);
							}
							cardTo.setAdmApplnId(Integer.parseInt(admForm.getAdmApplnId()));
							flag=true;
						}
					}
					if(flag){
						break;
					}
				}
			}

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return cardTo;
	}

	public InterviewCard getInterviewCardBo(int admApplnId, int interviewScheduleId) throws Exception {
		InterviewCard cardBo = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewCard e" +
					" where e.admAppln.id= " + admApplnId+" and e.interview.id="+interviewScheduleId);
			cardBo = (InterviewCard)query.uniqueResult();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		}
		return cardBo;
	}

	public InterviewSchedule getInterviewSchedule(InterviewCardTO interviewCardTO, AdmissionFormForm admForm) throws Exception {
		InterviewSchedule interviewSchedule = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewSchedule e" +
					" where e.date= '" +CommonUtil.ConvertStringToSQLDate(interviewCardTO.getInterviewDate())  + "' and e.startTime = '" + interviewCardTO.getTime()+
					"' and e.selectionScheduleId='"+admForm.getInterviewSelectionDate()+"' and e.interview.interviewProgramCourse.id=(select p.id from InterviewProgramCourse p where p.program.id='"+admForm.getProgramId() 
					+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getApplicationYear()+"' and p.isActive=1)");
			interviewSchedule=(InterviewSchedule) query.uniqueResult();
			if(interviewSchedule==null){
				Interview interview=null;
				//Set<InterviewSchedule> interviewScheduleSet;
				Query que = session.createQuery("from Interview e" +
						" where e.year="+admForm.getApplicationYear()+" and e.interviewProgramCourse.id=(select p.id from InterviewProgramCourse p where p.program.id='"+admForm.getProgramId() 
						+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getApplicationYear()+"' and p.isActive=1)");

				List<Interview> intList=que.list();
				if(intList!=null && !intList.isEmpty()){
					interview=intList.get(0);
				}
				else{
					transaction = session.beginTransaction();
					interview=new Interview();
					InterviewProgramCourse interviewProgramCourse=new InterviewProgramCourse();
					if(interviewCardTO.getInterviewPrgCrsId()==0){
						int interviewPgmCourse= getInterviewPrgCourse(admForm);
						interviewProgramCourse.setId(interviewPgmCourse);
					}else
					{
						interviewProgramCourse.setId(interviewCardTO.getInterviewPrgCrsId());
					}
					interview.setInterviewProgramCourse(interviewProgramCourse);
					interview.setYear(interviewCardTO.getAppliedYear());
					interview.setCreatedBy(admForm.getUserId());
					interview.setCreatedDate(new java.util.Date());
					interview.setModifiedBy(admForm.getUserId());
					interview.setLastModifiedDate(new java.util.Date());	
					session.save(interview);
					transaction.commit();
				}
				if(interview!=null && interview.getId()>0){
					transaction = session.beginTransaction();
					interviewSchedule=new InterviewSchedule();
					interviewSchedule.setInterview(interview);
					interviewSchedule.setDate(CommonUtil.ConvertStringToSQLDate(interviewCardTO.getInterviewDate()));
					interviewSchedule.setStartTime(interviewCardTO.getTime());
					interviewSchedule.setEndTime(interviewCardTO.getEndtime());
					InterviewSelectionSchedule sc=new InterviewSelectionSchedule();
					sc.setId(Integer.parseInt(admForm.getInterviewSelectionDate()));
					interviewSchedule.setSelectionScheduleId(sc);
					interviewSchedule.setVenue(interviewCardTO.getVenue());
					interviewSchedule.setCreatedBy(Integer.parseInt(admForm.getUserId()));
					interviewSchedule.setCreatedDate(new java.util.Date());
					interviewSchedule.setLastModifiedDate(new java.util.Date());
					interviewSchedule.setModifiedBy(admForm.getUserId());
					session.save(interviewSchedule);
					transaction.commit();
				}
			}
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return interviewSchedule;
	}

	public Integer getInterViewPgmCourse(AdmissionFormForm admForm)throws Exception {

		Integer interPgmCourse = 0;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select i.id from InterviewProgramCourse i where i.course.id='"+admForm.getCourseId()+"' and i.year='"+admForm.getApplicationYear()+"' and i.sequence=1 and i.isActive=1");
			interPgmCourse = (Integer) query.uniqueResult();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		}
		return interPgmCourse;
	}	



	public boolean addSelectionProcessWorkflowData(List<InterviewCard> interviewCardsToSave,String user, AdmissionFormForm admForm, Integer interViewPgmCourse) throws Exception {

		Session session = null;
		Transaction transaction = null;
		boolean isAdd=false;
		InterviewCard oldInterviewCard=null;
		InterviewSelected interSelected=null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			if(interviewCardsToSave!=null && !interviewCardsToSave.isEmpty()){
				Iterator it = interviewCardsToSave.iterator();
				int count = 0;
				while (it.hasNext()) {
					InterviewCard card = (InterviewCard) it.next();
					if(card!=null){
						Query query = session.createQuery("FROM InterviewCard e" +
								" where e.admAppln.id = " + card.getAdmAppln().getId() + 
								" and e.interview.interview.interviewProgramCourse.id = (select p.id from InterviewProgramCourse p where p.program.id='"+admForm.getProgramId() 
								+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getApplicationYear()+"' and p.isActive=1)");

						List<InterviewCard> cardList = query.list();
						if(cardList!= null && cardList.size() > 0){
							oldInterviewCard = cardList.get(0);
						}
						if(oldInterviewCard!= null){
							card.setId(oldInterviewCard.getId());
							session.merge(card);
						}
						else{
							session.save(card);
						}
						Query que = session.createQuery("From InterviewSelected e" +
								" where e.admAppln.id = " + card.getAdmAppln().getId() +
								" and e.interviewProgramCourse.id = (select p.id from InterviewProgramCourse p where p.program.id='"+admForm.getProgramId() 
								+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getApplicationYear()+"' and p.isActive=1)");

						List<InterviewSelected> selectedList = que.list();
						if(selectedList!= null && selectedList.size() > 0){
							interSelected = selectedList.get(0);
							interSelected.setIsCardGenerated(true);
							interSelected.setLastModifiedDate(new java.util.Date());
							interSelected.setModifiedBy(user);
							session.update(interSelected);
						}
						else{
							interSelected=new InterviewSelected();
							InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
							interviewProgramCourse.setId(interViewPgmCourse);
							interSelected.setInterviewProgramCourse(interviewProgramCourse);
							AdmAppln adm=new AdmAppln();
							adm.setId(card.getAdmAppln().getId());
							interSelected.setAdmAppln(adm);
							interSelected.setIsCardGenerated(true);
							interSelected.setCreatedBy(user);
							interSelected.setCreatedDate(new java.util.Date());
							interSelected.setLastModifiedDate(new java.util.Date());
							interSelected.setModifiedBy(user);
							session.save(interSelected);
						}
						if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
					}
					admForm.setAdmApplnId(String.valueOf(card.getAdmAppln().getId()));

				}
				transaction.commit();
				session.flush();
				session.close();
				if(admForm.isExamCenterRequired()){
					updateSeatNo(Integer.parseInt(admForm.getAdmApplnId()));
				}		

				isAdd=true;
			}
		}catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			session.close();
			isAdd=false;
			throw  new ApplicationException(e);
		} finally {
			/*if (session != null) {
				session.flush();
				session.close();
			}*/
		}
		return isAdd;

	}

	public String getAdmApplnId(AdmissionFormForm admForm) throws Exception
	{
		String admapplnId=null;
		AdmAppln appln=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from AdmAppln a where a.appliedYear="+admForm.getApplicationYear()+" and a.applnNo="+admForm.getApplicationNumber()+" and a.isCancelled=0 ");
			appln = (AdmAppln)query.uniqueResult();
			if(appln!=null){
				admapplnId=String.valueOf(appln.getId());
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return admapplnId;

	}

	public Integer getExamCenter(int programId, int examCenterId) throws Exception {
		log.info("entered getProgramDetails..");
		Session session = null;
		int currentSeatNo = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamCenter e where e.id = :examCenterId and e.program.id = :programId");
			query.setInteger("examCenterId", examCenterId);
			query.setInteger("programId", programId);
			List<ExamCenter> examCenterList = query.list();
			if(examCenterList!= null && examCenterList.size()!= 0){
				ExamCenter examCenter = (ExamCenter) examCenterList.get(0);
				currentSeatNo = examCenter.getCurrentSeatNo();
			}
			session.flush();
			return currentSeatNo;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	public void updateSeatNo(int applnId) throws ApplicationException {
		Session session = null;
		Transaction transaction = null;
		Transaction transaction1 = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();

			AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class, applnId);
			if(admAppln.getExamCenter() != null){
				int programId = admAppln.getCourseBySelectedCourseId().getProgram().getId(); 
				int examCenterId = admAppln.getExamCenter().getId();
				int seatNo = getExamCenter(programId,examCenterId); 
				if((admAppln.getSeatNo() == null || admAppln.getSeatNo().trim().isEmpty()) && seatNo > 0){
					admAppln.setSeatNo(Integer.toString(seatNo));
					//						admAppln.setAdmStatus(CMSConstants.COMMON_MSG_ADM_STATUS);
					session.update(admAppln);
					transaction.commit();
					transaction1=session.beginTransaction();
					int newSeatNo = seatNo + 1;
					ExamCenter examCenter = (ExamCenter) session.get(ExamCenter.class, examCenterId);
					examCenter.setCurrentSeatNo(newSeatNo);
					session.update(examCenter);
					transaction1.commit();
				}
			}

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	public int getInterviewPrgCourse (AdmissionFormForm admForm)throws ApplicationException {
		log.info("entered getProgramDetails..");
		Session session = null;
		int InterviewPrgCourseId = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewProgramCourse pc "
					+" where pc.program.id='"+admForm.getProgramId()+"'" +
					"and pc.course.id='"+admForm.getCourseId()+"' and pc.year='"+admForm.getYear()+"'" +
					" and pc.isActive=1 and pc.sequence=1 ");
			List<InterviewProgramCourse> List = query.list();
			if(List!= null && List.size()!= 0){
				InterviewProgramCourse pgmCourse = (InterviewProgramCourse) List.get(0);
				InterviewPrgCourseId = pgmCourse.getId();
			}
			//session.flush();
			return InterviewPrgCourseId;
		} catch (Exception e) {
			session.flush();
			throw new ApplicationException(e);
		}
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getCurrentChallanNo()
	 */
	public String getCouseNameByCourseId(String courseId) throws Exception {
		Session session = null;
		String courseName = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select c.name from Course c where c.isActive=1 and c.id="+courseId);
			courseName = (String) query.uniqueResult();
			//session.flush();
			//			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
			}
			log.error("Error during getting current challanNO...", e);
			throw new ApplicationException(e);
		}
		return courseName;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAdmissionFormTransaction#getResidenceNameByResidanceId(java.lang.String)
	 */
	public String getResidenceNameByResidanceId(String residenceId) throws Exception {
		Session session = null;
		String residenceName = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select c.name from ResidentCategory c where c.isActive=1 and c.id="+residenceId);
			residenceName = (String) query.uniqueResult();
			//session.flush();
			//			session.close();
		} catch (Exception e) {

			if (session != null) {
				//session.flush();
			}
			log.error("Error during getting current challanNO...", e);
			throw new ApplicationException(e);
		}
		return residenceName;
	}

	@Override
	public Map<Integer, String> getParishByDiose(String dioid) throws Exception {
		// TODO Auto-generated method stub
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ParishBO p where p.isActive=1 and p.dioceseId.id="+Integer.parseInt(dioid));
			List<ParishBO> list=query.list();
			if(list!=null){
				Iterator<ParishBO> iterator=list.iterator();
				while(iterator.hasNext()){
					ParishBO pre=iterator.next();
					if(pre!=null && pre.getName()!=null &&  pre.getId()>0)
						map.put(pre.getId(),String.valueOf(pre.getName()));
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				//session.flush();
			}
		}
		map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public Map<Integer,String> get12thclassSubject(String docName,String language) throws Exception{
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer,String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AdmSubjectForRank a where a.stream=:docName and a.groupName!=:language");
			query.setString("docName",docName);
			query.setString("language",language);
			List<AdmSubjectForRank> list=query.list();
			if(list!=null){
				Iterator <AdmSubjectForRank> itr=list.iterator();
				while(itr.hasNext()){
					AdmSubjectForRank adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){

						map.put( adm.getId(), adm.getName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				//session.flush();
			}
		}
		map=(Map<Integer,String>) CommonUtil.sortMapByValue(map);
		return map;

	}

	public Map<Integer,String> get12thclassLangSubject(String docName,String language) throws Exception{
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer,String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AdmSubjectForRank a where a.stream=:docName and a.groupName=:language");
			query.setString("docName",docName);
			query.setString("language",language);
			List<AdmSubjectForRank> list=query.list();
			if(list!=null){
				Iterator <AdmSubjectForRank> itr=list.iterator();
				while(itr.hasNext()){
					AdmSubjectForRank adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){

						map.put( adm.getId(), adm.getName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				//session.flush();
			}
		}
		map=(Map<Integer,String>) CommonUtil.sortMapByValue(map);
		return map;

	}

	@Override
	public String getProgramId(int courseId) throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			Course course = (Course) session.createQuery(
					"from Course c where c.isActive=1 and c.id= " + courseId)
					.uniqueResult();

			session.flush();
			//			session.close();
			//			sessionFactory.close();
			if (course != null && course.getProgram().getId() != 0 && String.valueOf(course.getProgram().getId())!=null) {
				return String.valueOf(course.getProgram().getId());
			} 
			else
				return null;

		} catch (Exception e) {
			log.error("Error during getting currencies...", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}

	}


	@Override
	public List<AdmSubjectMarkForRank> get12thsubjmark(int id,String doctype) throws Exception {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AdmSubjectMarkForRank a where a.ednQualification.personalData.id=:id and a.ednQualification.docChecklist.docType.name=:name");
			query.setInteger("id",id);
			query.setString("name",doctype);
			List<AdmSubjectMarkForRank> list = query.list();
			return list;

		}catch (Exception e) {
			log.error("Error during getting 12th class subject marks", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}



	}

	@Override
	public List<CandidatePreference> getpreferences(int id) throws ApplicationException {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from CandidatePreference a where a.admAppln.id=:id order by a.prefNo asc");
			query.setInteger("id",id);
			List<CandidatePreference> list = query.list();
			return list;

		}catch (Exception e) {
			log.error("Error during getting 12th class subject marks", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}

	}
	//vibin for status change
	@Override
	public StudentCourseAllotment getallotmentdetails(int id,int max) throws Exception {
		Session session = null;
		StudentCourseAllotment allotment= null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from StudentCourseAllotment s where s.admAppln.id=:id and s.allotmentNo=:max");
			query.setInteger("id",id);
			query.setInteger("max", max);
			allotment = (StudentCourseAllotment) query.uniqueResult();


		}catch (Exception e) {
			log.error("Error during getting allotment deatails", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
		return allotment;
	}

	@Override
	public Integer getmaxallotment(int pgtype,int appliedYear) throws Exception {

		Session session = null;
		Integer max ;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select max(s.allotmentNo) from StudentCourseAllotment s where s.admAppln.appliedYear=:appliedYear and s.course.program.programType.id=:pgtype ");
			query.setInteger("appliedYear",appliedYear);
			query.setInteger("pgtype",pgtype);
			max = (Integer) query.uniqueResult();



		}catch (Exception e) {
			log.error("Error during getting max allotment no", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
		return max;

	}

	public Map<Integer,String> get12thclassSub(String docName,String sub) throws Exception{
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer,String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AdmSubjectForRank a where a.stream=:docName and a.groupName=:sub");
			query.setString("docName",docName);
			query.setString("sub",sub);
			List<AdmSubjectForRank> list=query.list();
			if(list!=null){
				Iterator <AdmSubjectForRank> itr=list.iterator();
				while(itr.hasNext()){
					AdmSubjectForRank adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){

						map.put( adm.getId(), adm.getName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				//session.flush();
			}
		}
		map=(Map<Integer,String>) CommonUtil.sortMapByValue(map);
		return map;

	}

	public StudentRank getrankdetails(int applnid, int courseid) throws ApplicationException {
		Session session = null;
		StudentRank rank = null;
		try{

			session = HibernateUtil.getSession();
			Query query = session.createQuery("from StudentRank s where s.admAppln.id=:id and s.course.id=:course");
			query.setInteger("id",applnid);
			query.setInteger("course", courseid);
			rank = (StudentRank) query.uniqueResult();



		}catch (Exception e) {
			log.error("Error during getting max allotment no", e);

			if (session != null) {
				//session.flush();
			}
			throw new ApplicationException(e);
		}
		return rank;
	}

	//raghu write newly

	@Override
	public int getAppliedYearForProgramType(int programtypeId) throws Exception {
		Session session = null;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			int year=0;
			List programList =  session.createQuery(
					"from Program p where p.isActive=1 and p.programType.id= " + programtypeId).list();
			Program program = (Program)programList.get(0);
			session.flush();
			//			session.close();
			//			sessionFactory.close();
			if (program != null && program.getAcademicYear() != null) {
				year=program.getAcademicYear();
				return year;
			} else {
				Calendar cal = Calendar.getInstance();
				year = cal.get(cal.YEAR);
				return year;
			}
		} catch (Exception e) {
			log.error("Error during getting currencies...", e);


			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}



	public Map<Integer,String> getStreamMap() throws Exception{

		Session session=null;
		Map<Integer,String> map=new LinkedHashMap<Integer,String>();
		try{
			session=HibernateUtil.getSession();
			List<EducationStream> list=new LinkedList<EducationStream>();
			Query query=session.createQuery("from EducationStream a where a.isActive=1 order by a.id");

			list=query.list();
			if(list!=null){
				Iterator <EducationStream> itr=list.iterator();
				while(itr.hasNext()){
					EducationStream adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){

						map.put( adm.getId(), adm.getName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				//session.close();
			}
		}
		//map=(Map<Integer,String>) CommonUtil.sortMapByValue(map);
		return map;

	}




	//this is for optional

	public Map<Integer,String> get12thclassSub1(String docName,String sub) throws Exception{
		Session session=null;
		Map<Integer,String> map=new LinkedHashMap<Integer,String>();
		List<AdmSubjectForRank> list=new LinkedList<AdmSubjectForRank>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AdmSubjectForRank a where a.isActive=1 and a.stream=:docName and a.groupName=:sub order by a.id");
			query.setString("docName",docName);
			query.setString("sub",sub);
			list=query.list();
			if(list!=null){
				Iterator <AdmSubjectForRank> itr=list.iterator();
				while(itr.hasNext()){
					AdmSubjectForRank adm=itr.next();
					if(adm!=null && adm.getName()!=null && adm.getId()>0){
						if(!adm.getName().equalsIgnoreCase("English"))
							map.put( adm.getId(), adm.getName());
					}
				}
			}
		}
		catch(Exception exception){
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				//session.close();
			}
		}
		//map=(Map<Integer,String>) CommonUtil.sortMapByValue(map);
		return map;

	}

	//vinodha
	public StudentIndexMark getStudentIndexMarks(AdmissionStatusForm admForm) throws Exception {

		Session session = null;
		StudentIndexMark applicantDetails = null;
		List<StudentIndexMark> list = null;
		int appNO = 0;
		if (StringUtils.isNumeric(admForm.getApplicationNo()))
			appNO = Integer.parseInt(admForm.getApplicationNo());
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "";

			sql = "select s from StudentIndexMark s where s.admAppln.applnNo=:AppNO and s.course.id=:CourseId " +
					" and s.admAppln.appliedYear=:AdmYear";
			Query qr = session.createQuery(sql);
			qr.setInteger("AppNO", appNO);
			qr.setInteger("CourseId", Integer.parseInt(admForm.getCourseId()));
			qr.setInteger("AdmYear", Integer.parseInt(admForm.getAcademicYear()));
			list = qr.list();
			Iterator<StudentIndexMark> itr = list.iterator();
			while(itr.hasNext())
			{
				applicantDetails = itr.next();
				break;
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//			session.close();
			}
		}
		return applicantDetails;
	}

	// vinodha
	public CandidatePreference getApplicationDetailsForMemoFromAdmAppln(AdmissionStatusForm admForm) throws Exception {

		Session session = null;
		CandidatePreference applicantDetails = null;

		int appNO = 0;
		if (StringUtils.isNumeric(admForm.getApplicationNo()))
			appNO = Integer.parseInt(admForm.getApplicationNo());
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sql = "";

			sql = "select c from CandidatePreference c where c.admAppln.applnNo=:AppNO and c.course.id=:CourseId " +
					" and c.admAppln.appliedYear=:AdmYear";
			Query qr = session.createQuery(sql);
			qr.setInteger("AppNO", appNO);
			qr.setInteger("CourseId", Integer.parseInt(admForm.getCourseId()));
			qr.setInteger("AdmYear", Integer.parseInt(admForm.getAcademicYear()));
			applicantDetails = (CandidatePreference) qr.uniqueResult();
		} catch (Exception e) {
			log.error("Error while getting applicant details..." + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//			session.close();
			}
		}
		return applicantDetails;
	}

	@Override
	public String getPaymentStatus(String applicationNo) throws Exception {

		log.info("Entered into generateCandidateRefNo-AdmissionFormTransactionImpl");
		Session session=null;
		String status=null;
		try {
			session=HibernateUtil.getSession();
			String query="select c.txnStatus from CandidatePGIDetails c where c.admAppln.applnNo="+applicationNo;
			status=(String)session.createQuery(query).uniqueResult();
			session.flush();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (session != null){
				session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e);
			throw  new ApplicationException(e);
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return status;

	}

	public boolean updatePendingOnlineApp(AdmissionFormForm admForm) throws Exception {		

		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			int flag=0;
			if(admForm.getCorrectionFor() != null && !admForm.getCorrectionFor().isEmpty() && admForm.getCorrectionFor().equalsIgnoreCase("Online Application")) {

				String query="from CandidatePGIDetails c where c.uniqueId=(select m.uniqueId from CandidatePGIDetails m where m.candidateRefNo=:candidateRefNo)";
				List<CandidatePGIDetails> candidatePgiBolist=(List<CandidatePGIDetails>)session.createQuery(query).setString("candidateRefNo", admForm.getCandidateRefNo()).list();
				if (candidatePgiBolist!=null) {

					for(CandidatePGIDetails pgi:candidatePgiBolist){
						if (pgi.getTxnStatus()=="success" || pgi.getTxnStatus().equalsIgnoreCase("success")) {
							flag++;
						}
					}

					if(flag==0){
						for(CandidatePGIDetails candidatePgiBo:candidatePgiBolist){
							if (candidatePgiBo.getCandidateRefNo()==admForm.getCandidateRefNo() || candidatePgiBo.getCandidateRefNo().equalsIgnoreCase(admForm.getCandidateRefNo())) {
								candidatePgiBo.setTxnStatus("success");
								candidatePgiBo.setTxnDate(candidatePgiBo.getCreatedDate());
								candidatePgiBo.setTxnRefNo(candidatePgiBo.getTxnRefNo());
								candidatePgiBo.setModifiedBy(admForm.getUserId());
								candidatePgiBo.setLastModifiedDate(new java.util.Date());

								session.update(candidatePgiBo);

								String queryForAdmAppln = " from AdmAppln appln where appln.studentOnlineApplication = " + candidatePgiBo.getUniqueId().getId();
								AdmAppln admAppln = (AdmAppln) session.createQuery(queryForAdmAppln).uniqueResult();
								admAppln.setCurrentPageName("payment");
								admAppln.setJournalNo(candidatePgiBo.getTxnRefNo());
								admAppln.setDate(CommonUtil.ConvertStringToDate(admForm.getTxnDate()));
								admAppln.setAmount(candidatePgiBo.getTxnAmount());
								admForm.setEmailId(admAppln.getStudentOnlineApplication().getEmailId());
								session.update(admAppln);session.update(admAppln);


								
								isUpdated=true;
							}
						}
					}
						transaction.commit();
				}				
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}			
			throw  new ApplicationException(e);
		}
		return isUpdated;
	}
	@Override
	public Boolean getApplicantDetails(AdmissionFormForm admForm) throws Exception {

		log.info("Entered into generateCandidateRefNo-AdmissionFormTransactionImpl");
		Session session=null;
		boolean flag=false;
		try {
			session=HibernateUtil.getSession();
			String query="from CandidatePGIDetails c where c.uniqueId=(select m.uniqueId from CandidatePGIDetails m where m.candidateRefNo=:candidateRefNo)";
			List<CandidatePGIDetails> candidatePgiBolist=(List<CandidatePGIDetails>)session.createQuery(query).setString("candidateRefNo", admForm.getCandidateRefNo()).list();
			for(CandidatePGIDetails candidatepgiBo:candidatePgiBolist){
				if (candidatepgiBo.getUniqueId().getName()!=null || !candidatepgiBo.getUniqueId().getName().isEmpty()) {
					admForm.setFirstName(candidatepgiBo.getUniqueId().getName());
					admForm.setApplicationAmount(candidatepgiBo.getTxnAmount().toString());	
					flag=true;
				}
			}
			session.flush();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (session != null){
				session.flush();
				session.close();
			}
			log.error("Error in generateCandidateRefNo-AdmissionFormTransactionImpl..."+e);
			throw  new ApplicationException(e);
		}
		log.info("Exit generateCandidateRefNo-AdmissionFormTransactionImpl");
		return flag;

	}
}