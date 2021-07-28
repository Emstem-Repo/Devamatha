package com.kp.cms.transactionsimpl.ajax;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import sun.print.resources.serviceui;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.ApplicationStatusUpdate;
import com.kp.cms.bo.admin.AttendanceCondonation;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.City;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.bo.admin.District;
import com.kp.cms.bo.admin.EmpEducationMaster;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvSubCategoryBo;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.RegisterNumberFromSmartCard;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.bo.admin.TTPeriodWeek;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.admission.InterviewTimeSelection;
import com.kp.cms.bo.admission.InterviewVenueSelection;
import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.bo.studentfeedback.BlockBo;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.handlers.admission.ExportPhotosHandler;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.transactions.admin.IUniversityTxn;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactionsimpl.admin.UniversityTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * 
 * Transaction Implementation Class for Common Ajax related transactions across
 * project.
 */

public class CommonAjaxImpl extends CommonAjaxExamImpl implements ICommonAjax {
	private static final Log log = LogFactory.getLog(CommonAjaxImpl.class);
	private static Map<Integer, String> pMap = null;
	static {
		pMap = new HashMap<Integer, String>();
		pMap.put(13, "01");
		pMap.put(14, "02");
		pMap.put(15, "03");
		pMap.put(16, "04");
		pMap.put(17, "05");
		pMap.put(18, "06");
		pMap.put(19, "07");
		pMap.put(20, "08");
		pMap.put(21, "09");
		pMap.put(22, "10");
		pMap.put(23, "11");
		pMap.put(24, "12");
	}
	
	
	private static volatile CommonAjaxImpl commonAjaxImpl = null;

	public static CommonAjaxImpl getInstance() {
		if (commonAjaxImpl == null) {
			commonAjaxImpl = new CommonAjaxImpl();
			return commonAjaxImpl;
		}
		return commonAjaxImpl;
	}

	/**
	 * 
	 * @param id
	 * @return cityMap <key,value> Ex:<1,bangalore> <2,tumkur>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCitiesByState(int id) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from State where id = :stateId");
			query.setInteger("stateId", id);
			State state = (State) query.list().get(0);
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Iterator<City> itr = state.getCities().iterator();
			City city;
			while (itr.hasNext()) {
				city = (City) itr.next();
				cityMap.put(city.getId(), city.getName());
			}

			// //session.close();
			cityMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(cityMap);
			return cityMap;
		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 * @return stateMap <key,value> Ex:<1,karnataka> <2,kerala>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getStatesByCountry(int id) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from State s where s.country.id = :countryId and s.isActive = 1 and s.country.isActive = 1 order by s.name");
			query.setInteger("countryId", id);
			List<State> stateList = query.list();
			Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
			if (stateList != null) {
				Iterator<State> itr = stateList.iterator();
				while (itr.hasNext()) {
					State state = itr.next();
					if (state != null) {
						stateMap.put(state.getId(), state.getName());
					}
				}
			}
			// session.close();
			stateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(stateMap);
			return stateMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new LinkedHashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 * @return citiesMap <key,value> Ex:<1,karnataka> <2,kerala>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCitiesByCountry(int id) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Country where id = :countryId");
			query.setInteger("countryId", id);
			Country country = (Country) query.list().get(0);
			Map<Integer, String> citiesMap = new HashMap<Integer, String>();
			Iterator<State> itr = country.getStates().iterator();
			State state;
			City city;
			while (itr.hasNext()) {
				state = (State) itr.next();
				Iterator<City> itr1 = state.getCities().iterator();
				while (itr1.hasNext()) {
					city = (City) itr1.next();
					citiesMap.put(city.getId(), city.getName());
				}
			}
			// session.close();
			citiesMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(citiesMap);
			return citiesMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 * @return stateMap <key,value> Ex:<1,karnataka> <2,kerala>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getApplnProgramsByProgramType(int id) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from ProgramType where isActive =1 and id = :programTypeId")
					.setInteger("programTypeId", id);
			ProgramType programType = (ProgramType) query.list().get(0);
			Map<Integer, String> programMap = new HashMap<Integer, String>();
			Iterator<Program> itr = programType.getPrograms().iterator();
			Program program;
			while (itr.hasNext()) {
				program = (Program) itr.next();
				if (program.getIsActive() && program.getIsOpen()) {
					programMap.put(program.getId(), program.getName());
				}
			}

			// session.close();
			programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);
			return programMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.ajax.ICommonAjax#getActivityByAttendenceType(int)
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getActivityByAttendenceType(Set id) {
		try {
			Session session = null;
			Map<Integer, String> activityMap = new HashMap<Integer, String>();
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from AttendanceType where isActive =1 and id in (:attendanceTypeId)");
			query.setParameterList("attendanceTypeId", id);
			List attendanceTypeList = query.list();
			AttendanceType attendanceType = null;
			if (attendanceTypeList != null) {
				Iterator it = attendanceTypeList.iterator();
				while (it.hasNext()) {
					attendanceType = (AttendanceType) it.next();
					Iterator<Activity> itr = attendanceType.getActivities()
							.iterator();
					Activity activity;
					while (itr.hasNext()) {
						activity = (Activity) itr.next();
						if (activity.getIsActive() == true)
							activityMap.put(activity.getId(), activity
									.getName());
					}
				}
			}

			// session.close();
			activityMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(activityMap);
			return activityMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * @param id
	 * @return interviewSubroundsMap
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewSubroundsByInterviewType(int id) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewSubRounds where interviewProgramCourse.id = :interviewTypeId and isActive = 1");
			query.setInteger("interviewTypeId", id);

			Map<Integer, String> interviewSubroundsMap = new LinkedHashMap<Integer, String>();
			Iterator<InterviewSubRounds> interviewSubroundsIterator = query
					.iterate();

			while (interviewSubroundsIterator.hasNext()) {
				InterviewSubRounds interviewProgramCourse = (InterviewSubRounds) interviewSubroundsIterator
						.next();
				interviewSubroundsMap.put(interviewProgramCourse.getId(),
						interviewProgramCourse.getName());
			}
			interviewSubroundsMap=CommonUtil.sortMapByValue(interviewSubroundsMap);
			return interviewSubroundsMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}
		return new HashMap<Integer, String>();
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewSubroundsByApplicationId(int id,
			int applicationId) {
		Session session = null;
		try {

			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewSubRounds interviewSubrounds "
							+ " where interviewSubrounds.id not in( "
							+ " select interviewResult.interviewSubRounds.id "
							+ " from InterviewResult interviewResult "
							+ " where interviewResult.admAppln.id = :applicationId"
							+ " and interviewResult.interviewSubRounds.id <> null) "
							+ " and interviewSubrounds.interviewProgramCourse.id = :interviewTypeId"
							+ " and interviewSubrounds.isActive = 1");
			query.setInteger("interviewTypeId", id);
			query.setInteger("applicationId", applicationId);

			Map<Integer, String> interviewSubroundsMap = new LinkedHashMap<Integer, String>();
			Iterator<InterviewSubRounds> interviewSubroundsIterator = query
					.iterate();

			while (interviewSubroundsIterator.hasNext()) {
				InterviewSubRounds interviewProgramCourse = (InterviewSubRounds) interviewSubroundsIterator
						.next();
				interviewSubroundsMap.put(interviewProgramCourse.getId(),
						interviewProgramCourse.getName());
			}
			interviewSubroundsMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interviewSubroundsMap);
			return interviewSubroundsMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 * @return courseMap <key,value>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByProgram(int id) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(
					"from Program where id = :programId and isActive =1 ")
					.setInteger("programId", id);
			Program program = new Program();
			if (!query.list().isEmpty())
				program = (Program) query.list().get(0);
			Map<Integer, String> courseMap = new HashMap<Integer, String>();
			Iterator<Course> itr = program.getCourses().iterator();
			Course course;
			while (itr.hasNext()) {
				course = (Course) itr.next();
				if (course.getIsActive())
					courseMap.put(course.getId(), course.getName());
			}
			courseMap=CommonUtil.sortMapByValue(courseMap);
			
			// session.close();
			
			return courseMap;
		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 * @return courseMap <key,value>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCollegeByUniversity(int id) {
		Session session = null;
		IUniversityTxn txn = UniversityTxnImpl.getInstance();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from University where id = :universityId and isActive=1");
			query.setInteger("universityId", id);
			List<University> universityList = query.list();
			if (universityList != null && !universityList.isEmpty()) {
				University university = (University) query.list().get(0);
				Map<Integer, String> collegeMap = new HashMap<Integer, String>();
				// Iterator<College> itr = university.getColleges().iterator();
				List<College> collegeList = txn
						.getCollegeByUniversity(university.getId());
				Iterator<College> itr = collegeList.iterator();
				College college;
				while (itr.hasNext()) {
					college = (College) itr.next();
					if (college.getIsActive() != null && college.getIsActive())
						collegeMap.put(college.getId(), college.getName());
				}
				// session.close();
				collegeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(collegeMap);
				return collegeMap;
			} else {
				return new HashMap<Integer, String>();
			}

		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	
	public List<CollegeTO> getCollegeByUniversityList(int id) {
		Session session = null;
		IUniversityTxn txn = UniversityTxnImpl.getInstance();
		List<CollegeTO> collegeList=null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from University where id = :universityId and isActive=1");
			query.setInteger("universityId", id);
			List<University> universityList = query.list();
			if (universityList != null && !universityList.isEmpty()) {
				University university = (University) query.list().get(0);
				// Iterator<College> itr = university.getColleges().iterator();
				collegeList = txn.getCollegeByUniversityList(university.getId());
				
				// session.close();
				
				return collegeList;
			} else {
				return collegeList;
			}

		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return collegeList;
	}
	/**
	 * 
	 * @param id
	 * @return courseMap <key,value>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubReligionByReligion(int id) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ReligionSection r where r.religion.id = :religionId and r.isActive = 1 and r.religion.isActive = 1 order by r.name");
			query.setInteger("religionId", id);
			List<ReligionSection> religionSectionList = query.list();
			Map<Integer, String> subreligionMap = new LinkedHashMap<Integer, String>();
			if (religionSectionList != null) {
				Iterator<ReligionSection> itr = religionSectionList.iterator();
				while (itr.hasNext()) {
					ReligionSection religionSection = itr.next();
					if (religionSection != null) {
						subreligionMap.put(religionSection.getId(),
								religionSection.getName());
					}
				}
			}
			// session.close();
			subreligionMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subreligionMap);
			return subreligionMap;
		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return new LinkedHashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 * @return subjectGroupMap <key,value> Ex:<1,group1> <2,group2>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectGroupsByCourse(int id) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Course course = (Course) session.get(Course.class, id);
			Map<Integer, String> subjectGroupMap = new HashMap<Integer, String>();
			Iterator<SubjectGroup> itr = course.getSubjectGroups().iterator();
			SubjectGroup subjectGroup;
			while (itr.hasNext()) {
				subjectGroup = (SubjectGroup) itr.next();
				subjectGroupMap.put(subjectGroup.getId(), subjectGroup
						.getName());
			}

			// session.close();
			subjectGroupMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectGroupMap);
			return subjectGroupMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	public int getCandidateCount(int courseID, int year, int interviewid) {
		Session session = null;
		int count = 0;
		boolean isCardGenerated = false;
		boolean isbypassed = false;
		try {

			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			Query query = session
					.createQuery("from InterviewSelected i where i.interviewProgramCourse.id = :interviewid and i.interviewProgramCourse.course.id = :courseID and i.admAppln.appliedYear = :year and i.isCardGenerated = :isCardGenerated and i.admAppln.isBypassed = :isbypassed");
			query.setInteger("interviewid", interviewid);
			query.setInteger("year", year);
			query.setInteger("courseID", courseID);
			query.setBoolean("isCardGenerated", isCardGenerated);
			query.setBoolean("isbypassed", isbypassed);
			List<InterviewSelected> interviewTypes = query.list();
			count = interviewTypes.size();
			return count;
		} catch (Exception e) {
			// if (session != null)
			// session.close();
			return count;
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param Checking
	 *            in database for duplicate record based on the courseID and
	 *            year
	 * @returns if any exact record exists.
	 * @throws Exception
	 */
	public List<CurriculumScheme> getSemistersOnYearAndCourse(int courseId,
			int year) {
		Session session = null;
		Transaction txn = null;
		List<CurriculumScheme> isExistCourseId = new ArrayList<CurriculumScheme>();
		try {
			// session=HibernateUtil.getSessionFactory().openSession();
			session = InitSessionFactory.getInstance().openSession();
			txn = session.beginTransaction();
			isExistCourseId = session.createQuery(
					"from CurriculumScheme scheme where scheme.course.id = '"
							+ courseId + " ' and scheme.year = ' " + year
							+ " ' ").list();
			txn.commit();
			session.flush();
			// session.close();
		} catch (Exception e) {
			txn.rollback();
			session.flush();
			// session.close();
		}
		return isExistCourseId;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param Checking
	 *            in database for duplicate record based on the courseID and
	 *            year
	 * @returns if any exact record exists.
	 * @throws Exception
	 */
	public List<CurriculumSchemeDuration> getSemistersOnYearAndCourseScheme(
			int courseId, int year) {
		Session session = null;
		Transaction txn = null;
		List<CurriculumSchemeDuration> isExistCourseId = new ArrayList<CurriculumSchemeDuration>();
		try {
			// session=HibernateUtil.getSessionFactory().openSession();
			session = InitSessionFactory.getInstance().openSession();
			txn = session.beginTransaction();
			isExistCourseId = session
					.createQuery(
							"from CurriculumSchemeDuration schemeDuration where schemeDuration.curriculumScheme.course.id = '"
									+ courseId
									+ " ' and schemeDuration.academicYear = ' "
									+ year
									+ " ' order by schemeDuration.semesterYearNo ")
					.list();
			txn.commit();
			session.flush();
			// session.close();
		} catch (Exception e) {
			txn.rollback();
			session.flush();
			// session.close();
		}
		return isExistCourseId;
	}

	/**
	 * 
	 * @param id
	 *            Getting classes form ClassSchemewise based on the year
	 * @return classMap <key,value>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByYear(int year) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
						"from ClassSchemewise c  where  c.classes.course.isActive = 1  and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear  order by c.classes.termNumber,c.classes.course.program.programType.id,c.classes.course.program.id  ")
							.setInteger("academicYear", year);
			List<ClassSchemewise> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<ClassSchemewise> itr = classList.iterator();
			ClassSchemewise classSchemewise;

			while (itr.hasNext()) {
				classSchemewise = (ClassSchemewise) itr.next();
				classMap.put(classSchemewise.getId(), classSchemewise
						.getClasses().getName());
			}
			session.flush();
			// session.close();
			
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	public Map<Integer, String> getClassesByYearNew(int year) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear order by c.classes.name")
					.setInteger("academicYear", year);
			List<ClassSchemewise> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<ClassSchemewise> itr = classList.iterator();
			ClassSchemewise classSchemewise;

			while (itr.hasNext()) {
				classSchemewise = (ClassSchemewise) itr.next();
				classMap.put(classSchemewise.getClasses().getId(), classSchemewise
						.getClasses().getName());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getClassesByYearFromGeneratedTimeTable(int year) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from TimeAllocationBo bo where bo.academicYear= :academicYear")
					.setInteger("academicYear", year);
			List<TimeAllocationBo> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<TimeAllocationBo> itr = classList.iterator();
			TimeAllocationBo timeAllocationBo;
			ClassSchemewise classSchemewise;

			while (itr.hasNext()) {
				timeAllocationBo = (TimeAllocationBo) itr.next();
				classSchemewise = timeAllocationBo.getClassId();
				classMap.put(classSchemewise.getId(), classSchemewise
						.getClasses().getName());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getPeriodByClassSchemewiseId(int classSchemeId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Period period where period.classSchemewise.id = :classSchemeId  and period.isActive  = true order by period.periodName")
					.setInteger("classSchemeId", classSchemeId);

			Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();

			Iterator<Period> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Period period = (Period) periodIterator.next();
				if(period.getStartTime()!=null && !period.getStartTime().isEmpty()){
					int st=Integer.parseInt(period.getStartTime().substring(0,2));
					int et=Integer.parseInt(period.getEndTime().substring(0,2));
					if(st<=12){
						periodMap.put(period.getId(), period.getPeriodName()+"("+period.getStartTime().substring(0,5)+"-"+period.getEndTime().substring(0,5)+")");
					}else{
						periodMap.put(period.getId(), period.getPeriodName()+"("+pMap.get(st)+period.getStartTime().substring(2,5)+"-"+pMap.get(et)+period.getEndTime().substring(2,5)+")");
					}
				}
			}
			periodMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(periodMap);
			return periodMap;
		} catch (Exception e) {
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getPeriodByClassSchemewiseId(
			Set<Integer> classSchemeId) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Period period where period.classSchemewise.id in (:classSchemeId)  and period.isActive  = true order by period.periodName")
					.setParameterList("classSchemeId", classSchemeId);

			Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();

			Iterator<Period> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Period period = (Period) periodIterator.next();
				if(period.getStartTime()!=null && !period.getStartTime().isEmpty()){
					//int st=Integer.parseInt(period.getStartTime().substring(0,2));
					//int et=Integer.parseInt(period.getEndTime().substring(0,2));
					//modified by chandra
					//periodMap.put(period.getId(), period.getPeriodName()+"("+period.getStartTime().substring(0,5)+"-"+period.getEndTime().substring(0,5)+")");
					periodMap.put(period.getId(), period.getPeriodName());
					/*if(st<=12){
						periodMap.put(period.getId(), period.getPeriodName()+"("+period.getStartTime().substring(0,5)+"-"+period.getEndTime().substring(0,5)+")");
					}else{
						periodMap.put(period.getId(), period.getPeriodName()+"("+pMap.get(st)+period.getStartTime().substring(2,5)+"-"+pMap.get(et)+period.getEndTime().substring(2,5)+")");
					}*/
				}
			}
			periodMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(periodMap);
			return periodMap;
		} catch (Exception e) {
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

	/**
	 * Get class Details based on Id from ClassSchemewise
	 */

	public ClassSchemewise getDetailsonClassSchemewiseId(int id) {
		Session session = null;
		ClassSchemewise classSchemewise = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			classSchemewise = (ClassSchemewise) session.createQuery(
					"from ClassSchemewise c where c.id = " + id).uniqueResult();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
			// session.close();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
			return classSchemewise;
	}
	public ClassSchemewise getDetailsonClassSchemewiseIdNew(int id) {
		Session session = null;
		ClassSchemewise classSchemewise = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			classSchemewise = (ClassSchemewise) session.createQuery(
					"from ClassSchemewise c where c.classes.id = " + id).uniqueResult();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
			// session.close();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
			return classSchemewise;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesByClassSchemewiseId(
			Set<Integer> classSchemeId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Batch batch where batch.classSchemewise.id in (:classSchemeId)  and batch.isActive  = 1 order by batch.batchName")
					.setParameterList("classSchemeId", classSchemeId);

			Map<Integer, String> batchMap = new HashMap<Integer, String>();

			Iterator<Batch> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Batch batch = periodIterator.next();
				batchMap.put(batch.getId(), batch.getBatchName());
			}
			batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
			return batchMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesBySubjectAndClassScheme(
			int SubjectId, Set<Integer> classSchemeId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Batch batch where batch.classSchemewise.id in (:classSchemeId) and batch.subject.id = :subjectId  and batch.isActive  = 1 order by batch.batchName")
					.setParameterList("classSchemeId", classSchemeId)
					.setParameter("subjectId", SubjectId);

			Map<Integer, String> batchMap = new HashMap<Integer, String>();

			Iterator<Batch> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Batch batch = periodIterator.next();
				batchMap.put(batch.getId(), batch.getBatchName());
			}
			batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
			return batchMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	public AttendanceType getMandatoryByAttenadnce(int attendanceTypeId) {
		AttendanceType attendanceType;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			attendanceType = (AttendanceType) session.get(AttendanceType.class,
					attendanceTypeId);
			// session.close();
			
			return attendanceType;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return null;
		}
	}

	/**
	 * Gets subject from curriculumschemeDuration based on the courseId, year
	 * and term
	 * 
	 */
	public Map<Integer, String> getSubjectByCourseIdTermYear(int courseId,
			int year, int term) {
	
		List<CurriculumSchemeDuration> curriculumSchemeDurationList = null;
		CurriculumSchemeDuration curriculumSchemeDuration = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			curriculumSchemeDurationList = session.createQuery(
					"from CurriculumSchemeDuration c where c.curriculumScheme.course.id = "
							+ courseId + " and c.academicYear = " + year
							+ " and c.semesterYearNo = " + term).list();
			int count = 0;
			if (!curriculumSchemeDurationList.isEmpty()) {
				curriculumSchemeDuration = curriculumSchemeDurationList.get(0);
			}

			if (curriculumSchemeDuration != null) {
				CurriculumSchemeSubject curriculumSchemeSubject;
				Set<CurriculumSchemeSubject> curriculumsubjectSet = curriculumSchemeDuration
						.getCurriculumSchemeSubjects();
				if (curriculumsubjectSet != null
						&& !curriculumsubjectSet.isEmpty()) {
					Iterator<CurriculumSchemeSubject> it1 = curriculumsubjectSet
							.iterator();
					
					while (it1.hasNext()) {
						curriculumSchemeSubject = it1.next();
						if (curriculumSchemeSubject.getSubjectGroup() != null
								&& curriculumSchemeSubject.getSubjectGroup()
										.getSubjectGroupSubjectses() != null
								&& !curriculumSchemeSubject.getSubjectGroup()
										.getSubjectGroupSubjectses().isEmpty()) {
							Set<SubjectGroupSubjects> subjectSet = curriculumSchemeSubject
									.getSubjectGroup()
									.getSubjectGroupSubjectses();
							if (curriculumSchemeSubject.getSubjectGroup() != null
									&& curriculumSchemeSubject
											.getSubjectGroup().getIsActive() == true) {
								if (subjectSet != null && !subjectSet.isEmpty()) {
									SubjectGroupSubjects subjectGroupSubjects;
									Iterator<SubjectGroupSubjects> it2 = subjectSet
											.iterator();
									
									while (it2.hasNext()) {
										subjectGroupSubjects = it2.next();
										if (subjectGroupSubjects.getIsActive() == true
												&& subjectGroupSubjects
														.getSubject() != null
												&& subjectGroupSubjects
														.getSubject()
														.getIsActive() != null
												&& subjectGroupSubjects
														.getSubject()
														.getIsActive() == true
												&& subjectGroupSubjects
														.getSubject().getId() != 0
												&& subjectGroupSubjects
														.getSubject().getName() != null
												&& (subjectGroupSubjects.getSubject().getIsCertificateCourse()==null || 
														subjectGroupSubjects.getSubject().getIsCertificateCourse()==false)) {
											subjectMap
													.put(
															subjectGroupSubjects
																	.getSubject()
																	.getId(),
															subjectGroupSubjects
																	.getSubject()
																	.getName()
																	+ "("
																	+ subjectGroupSubjects
																			.getSubject()
																			.getCode()
																	+ ")");
										//	sub[count]=String.valueOf(subjectGroupSubjects.getSubject().getId());
										//	count=count+1;
										}
										
									}
									
								}
							}
						}
					}
				}
				
			}
			session.flush();
			// session.close();
			Map<Integer, String> valueMap = sortByValue(subjectMap);
			return valueMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * Get class Details based on Id from ClassSchemewise
	 */

	public List<ClassSchemewise> getDetailsOnClassSchemewiseId(Set<Integer> ids) {
		Session session = null;
		List<ClassSchemewise> list;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session
					.createQuery("from ClassSchemewise c where c.id in (:ids)");
			query.setParameterList("ids", ids);
			list = query.list();
			return list;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
			// session.close();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return new ArrayList<ClassSchemewise>();
	}

	/**
	 * 
	 * @param id
	 *            Getting subjects form DetailedSubjects based on the course
	 * @return subjectMap <key,value>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectsDetailsByCourse(int courseId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DetailedSubjects s where s.isActive = 1 and course.id = '"
							+ courseId + "'");
			List<DetailedSubjects> subjectList = query.list();
			Map<Integer, String> subjectMap = new HashMap<Integer, String>();
			Iterator<DetailedSubjects> itr = subjectList.iterator();
			DetailedSubjects detailedSubjects;

			while (itr.hasNext()) {
				detailedSubjects = (DetailedSubjects) itr.next();
				subjectMap.put(detailedSubjects.getId(), detailedSubjects
						.getSubjectName());
			}
			session.flush();
			// session.close();
			subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
			return subjectMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	public Map<Integer, String> sortByValue(Map<Integer, String> map) {
		List<Entry<Integer, String>> list = new LinkedList<Entry<Integer, String>>(
				map.entrySet());

		Collections.sort(list, new Comparator<Entry<Integer, String>>() {

			@Override
			public int compare(Map.Entry<Integer, String> o1,
					Map.Entry<Integer, String> o2) {
				return o1.getValue().compareTo(o2.getValue());

			}

		});
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();
		Iterator<Entry<Integer, String>> listIterator = list.iterator();
		while (listIterator.hasNext()) {
			Map.Entry<java.lang.Integer, java.lang.String> entry = (Map.Entry<java.lang.Integer, java.lang.String>) listIterator
					.next();
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getFloorsByHostel(int hostelId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlUnits h where h.isActive = 1 and  h.id = "+ hostelId);
			List<HlUnits> hostelList = query.list();
			Map<Integer, String> floorMap = new HashMap<Integer, String>();
			Iterator<HlUnits> itr = hostelList.iterator();
			HlUnits hlHostel;
			int floorNo = 0;
			while (itr.hasNext()) {
				hlHostel = (HlUnits) itr.next();
				floorNo = hlHostel.getFloorNo();
			}
			for (int i = 1; i <= floorNo; i++) {
				floorMap.put(i, Integer.toString(i));
			}
			session.flush();
			// session.close();
			floorMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(floorMap);

			return floorMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 *            Getting group names form HlGroup based on the hostelId
	 * @return groupMap <key,value>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getHlGroupsByHostel(int hostelId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlGroup h where h.isActive = 1 and hlHostel.id = '"
							+ hostelId + "'");
			List<HlGroup> groupList = query.list();
			Map<Integer, String> groupMap = new HashMap<Integer, String>();
			Iterator<HlGroup> itr = groupList.iterator();
			HlGroup hlGroup;

			while (itr.hasNext()) {
				hlGroup = (HlGroup) itr.next();
				groupMap.put(hlGroup.getId(), hlGroup.getName());
			}
			session.flush();
			// session.close();
			groupMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(groupMap);

			return groupMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 *            Getting Room types based on the hostelId
	 * @return groupMap <key,value>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomTypeByHostel(int hostelId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlRoomType h where h.isActive = 1 and h.hlHostel.id = '"
							+ hostelId + "'");
			List<HlRoomType> roomTypeList = query.list();
			Map<Integer, String> roomTypeMap = new HashMap<Integer, String>();
			Iterator<HlRoomType> itr = roomTypeList.iterator();
			HlRoomType hlRoomType;

			while (itr.hasNext()) {
				hlRoomType = (HlRoomType) itr.next();
				roomTypeMap.put(hlRoomType.getId(), hlRoomType.getName());
			}
			session.flush();
			// session.close();
			roomTypeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomTypeMap);

			return roomTypeMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getEmployeesByDepartment(int departmentId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Employee e where e.department.id = "
							+ departmentId);
			List<Employee> empList = query.list();
			Map<Integer, String> empMap = new LinkedHashMap<Integer, String>();
			Iterator<Employee> itr = empList.iterator();
			Employee employee;

			while (itr.hasNext()) {
				StringBuffer buffer = new StringBuffer();
				employee = (Employee) itr.next();
				if (employee.getFirstName() != null) {
					buffer.append(employee.getFirstName());
				}
				if (employee.getMiddleName() != null) {
					buffer.append(" " + employee.getMiddleName());
				}
				if (employee.getLastName() != null) {
					buffer.append(" " + employee.getLastName());
				}
				empMap.put(employee.getId(), buffer.toString());
			}
			session.flush();
			// session.close();
			empMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(empMap);

			return empMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * Gets classes By Program
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByProgram(int programId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Classes c where c.isActive = 1 and c.course.program.id = :programId order by c.id")
					.setInteger("programId", programId);
			List<Classes> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<Classes> itr = classList.iterator();
			Classes classes;
			while (itr.hasNext()) {
				classes = (Classes) itr.next();
				classMap.put(classes.getId(), classes.getName());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 * @return stateMap <key,value> Ex:<1,karnataka> <2,kerala>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getinterViewTypybyCourseWithoutYear(int id) {

		Session session = null;
		try {

			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewProgramCourse where course.id = :courseId and isActive = true order by sequence");
			query.setInteger("courseId", id);
			// query.setInteger("year", year);

			Map<Integer, String> interViewMap = new LinkedHashMap<Integer, String>();
			Iterator<InterviewProgramCourse> interviewProgramCourseIterator = query
					.iterate();

			while (interviewProgramCourseIterator.hasNext()) {
				InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) interviewProgramCourseIterator
						.next();
				interViewMap.put(interviewProgramCourse.getId(),
						interviewProgramCourse.getName());
			}
			interViewMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interViewMap);

			return interViewMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}
		return new HashMap<Integer, String>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getInterviewTypebyProgramWithoutYear(int id) {
		Session session = null;
		try {

			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewProgramCourse where program.id = :programId and isActive = true order by sequence");
			query.setInteger("programId", id);

			Map<Integer, String> interviewMap = new LinkedHashMap<Integer, String>();
			Iterator<InterviewProgramCourse> interviewProgramCourseIterator = query
					.iterate();

			while (interviewProgramCourseIterator.hasNext()) {
				InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) interviewProgramCourseIterator
						.next();
				interviewMap.put(interviewProgramCourse.getId(),
						interviewProgramCourse.getName());
			}
			interviewMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interviewMap);

			return interviewMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * Gets classes By ProgramType
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByProgramType(int programTypeId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Classes c where c.isActive = 1 and c.course.program.programType.id = :programTypeId order by c.id")
					.setInteger("programTypeId", programTypeId);
			List<Classes> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<Classes> itr = classList.iterator();
			Classes classes;
			while (itr.hasNext()) {
				classes = (Classes) itr.next();
				classMap.put(classes.getId(), classes.getName());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	// petticash Related - getting the student name according to the
	// app/reg/roll no

	public List<Object[]> getStudentName(int optionNo, String appRegRollNo,
			String academicYear,HttpServletRequest request) throws Exception {
		    Session session = null;
			List<Object[]> studentDetailsAppliNo = null;
			List<Object[]> studentDetailsRegNo = null;
			List<Integer> optionss= new ArrayList<Integer>();
			List<Integer> optionsss= new ArrayList<Integer>();
			try {
				//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = HibernateUtil.getSession();
				if(StringUtils.isNumeric(appRegRollNo)){
				String applino = "select st.admAppln.personalData.firstName,st.admAppln.personalData.middleName,st.admAppln.personalData.lastName from Student st"
				                        + " where st.admAppln.appliedYear=" + academicYear
				                        + " and st.isActive=1"
				                        + " and st.admAppln.applnNo="
				                        + Integer.parseInt(appRegRollNo);
				Query query = session.createQuery(applino);
				studentDetailsAppliNo = query.list();
				}
				if(studentDetailsAppliNo==null || studentDetailsAppliNo.isEmpty()){
				String regno = "select st.admAppln.personalData.firstName,st.admAppln.personalData.middleName,st.admAppln.personalData.lastName from Student st"
					+ " where st.registerNo=" + "'" + appRegRollNo + "'"
	                + " and st.isActive=1";
	           Query query1 = session.createQuery(regno);
	           studentDetailsRegNo = query1.list();
				}if((studentDetailsAppliNo==null || studentDetailsAppliNo.isEmpty()) && (studentDetailsRegNo==null || studentDetailsRegNo.isEmpty())){
				
					String option=" select pc.id from PcAccountHead pc"
			             + " join pc.programId pro"
			             + " join pro.courses co "
			             + " join co.courseApplicationNumbers ap"
			             + " join ap.applicationNumber app "
			             + " where app.offlineApplnNoFrom <="+Integer.parseInt(appRegRollNo)
			             + " and app.offlineApplnNoTo>="+Integer.parseInt(appRegRollNo)
			             + " and pc.isActive=1"
			             + " group by pc.id,pro.id";
					Query query2=session.createQuery(option);
					optionss=query2.list();
					Iterator<Integer> itr=optionss.iterator();
					Integer opt=0;
					while (itr.hasNext()) {
						opt = itr.next();
						}
					request.setAttribute("list", opt);
				if(optionss==null || optionss.isEmpty()){
					String optionn=" select pro.id from ApplicationNumber ap"
			             + " join ap.courseApplicationNumbers cou"
			             + " join cou.course co "
			             + " join co.program pro"
			             + " where ap.offlineApplnNoFrom <="+Integer.parseInt(appRegRollNo)
			             + " and ap.offlineApplnNoTo>="+Integer.parseInt(appRegRollNo)
			             + " group by pro.id";
					Query query3=session.createQuery(optionn);
					optionsss=query3.list();
				 }				
				}
				
				// where st.admAppln.appliedYear = 2009 and st.admAppln.applnNo = 2
				// '"+CommonUtil.ConvertStringToSQLDate(studentForm.getStartDate())+"'"
//				if (optionNo == 1 && StringUtils.isNumeric(appRegRollNo)) {
//					searchCriteria = searchCriteria
//							+ " where st.admAppln.appliedYear=" + academicYear
//							+ " and st.admAppln.applnNo="
//							+ Integer.parseInt(appRegRollNo);
//				} else if (optionNo == 2) {
//					// searchCriteria=searchCriteria+
//					// " and pcReceipts.student.registerNo="+appRegRollNo;
//					searchCriteria = searchCriteria
//							+ " where st.admAppln.appliedYear=" + academicYear
//							+ " and st.rollNo=" + "'" + appRegRollNo + "'";
//				} else if (optionNo == 3) {
//					// searchCriteria=searchCriteria+" and pcReceipts.student.rollNo="
//					// +appRegRollNo;
//					searchCriteria = searchCriteria
//							+ " where st.registerNo=" + "'" + appRegRollNo + "'";
//				}
//				searchCriteria = searchCriteria + " and st.isActive=1";
				// searchCriteria=searchCriteria+" and pcReceipts.student.isActive=1"
				// ;
				// String q =
				// "select st.admAppln.personalData.firstName,st.admAppln.personalData.middleName,st.admAppln.personalData.lastName from Student st where st.admAppln.appliedYear='2009'and st.admAppln.applnNo=1"
				// ;

//				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//				session = HibernateUtil.getSession();
				// Transaction transaction = session.beginTransaction();
				// transaction.begin();
//				Query query = session.createQuery(searchCriteria);
//				studentDetails = query.list();

			} catch (Exception e) {
				throw new ApplicationException();
			} finally {
				if (session != null) {
					session.flush();
					// session.close();
				}
			}   if(studentDetailsAppliNo!=null && !studentDetailsAppliNo.isEmpty()){
				request.setAttribute("options", "Appl");
				 request.setAttribute("list", "");
			    return studentDetailsAppliNo;
			    }
			   if(studentDetailsRegNo!=null && !studentDetailsRegNo.isEmpty()){
				 request.setAttribute("options", "Regn");
				 request.setAttribute("list", "");
			    return studentDetailsRegNo;
			    }
			   if(optionss!=null && !optionss.isEmpty()){
				   request.setAttribute("options", "");
				return studentDetailsRegNo;
			   }if(optionsss!=null && !optionsss.isEmpty()){
				   request.setAttribute("options", "Appl");
				   request.setAttribute("list", "");
			   }if(optionsss==null || optionsss.isEmpty()){
				   request.setAttribute("options", "");
				   request.setAttribute("list", "");
			   }return studentDetailsRegNo;
			   }

	public PcAccountHead getAmount(int id) throws Exception {
		PcAccountHead pcAccountHead = null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from PcAccountHead ph where ph.id=" + id
							+ " and ph.isActive = 1 and ph.amount!=null");

			pcAccountHead = (PcAccountHead) query.uniqueResult();

			session.flush();
			// session.close();
			return pcAccountHead;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return pcAccountHead;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomsMapByHostelId(String hostelId) {
		Session session = null;
		List<HlRoom> list = null;
		Map<Integer, String> roomsMap = new LinkedHashMap();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String queryString = "from HlRoom h where h.hlHostel.id=:HostelId and h.isActive=1";
			Query query = session.createQuery(queryString);
			query.setInteger("HostelId", Integer.valueOf(hostelId));
			list = query.list();
			for (HlRoom hlRoom : list) {
				roomsMap.put(hlRoom.getId(), "(Floor:"+hlRoom.getFloorNo()+")"+hlRoom.getName());
			}
			roomsMap=CommonUtil.sortMapByValueForAlphaNumeric(roomsMap);
			// session.close();
			// sessionFactory.close();
		} catch (Exception e) {
			log.debug("Error during getRoomList data..." + e.getMessage());
		}
		return roomsMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomsByFloors(int hostelId, int floorNo,
			int roomtypeId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlRoom h where h.isActive = 1 and  h.hlHostel.id='"
							+ hostelId
							+ "' and  h.hlRoomType.id='"
							+ roomtypeId + "' and  h.floorNo='" + floorNo + "' order by h.name");
			List<HlRoom> roomlist = query.list();
			Map<Integer, String> roomMap = new HashMap<Integer, String>();
			Iterator<HlRoom> itr = roomlist.iterator();
			HlRoom hlRoom;
			// int roomNo = 0;
			while (itr.hasNext()) {
				hlRoom = (HlRoom) itr.next();
				roomMap.put(Integer.valueOf(hlRoom.getId()), hlRoom.getName());

			}
			roomMap=CommonUtil.sortMapByValueForAlphaNumeric(roomMap);
			/*
			 * for (int i = 1; i <= roomNo; i++){ roomMap.put(i,
			 * Integer.toString(i)); }
			 */
			session.flush();
			// session.close();
			return roomMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	@Override
	public Map<Integer, String> getRoomsByHostel(int hostelId) {

		Session session = null;
		List<HlRoomType> list = null;
		Map<Integer, String> roomsMap = new LinkedHashMap();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String queryString = "from HlRoomType h where h.hlHostel.id=:HostelId and h.isActive=1";
			Query query = session.createQuery(queryString);
			query.setInteger("HostelId", Integer.valueOf(hostelId));
			list = query.list();
			if (list != null && !list.isEmpty()) {
				for (HlRoomType hlRoom : list) {
					roomsMap.put(hlRoom.getId(), hlRoom.getName());
				}
			}
			// session.close();
			// sessionFactory.close();
		} catch (Exception e) {
			log.debug("Error during getRoomList data..." + e.getMessage());
		}
		return roomsMap;

	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBedByRoomId(int roomtypeId) {
		try {
			int noOfOcuupants = 0;
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select hlRoomType.noOfOccupants from HlRoomType hlRoomType"
							+ " where hlRoomType.id ="
							+ roomtypeId
							+ " and hlRoomType.isActive = 1");
			if (query.uniqueResult() != null)
				noOfOcuupants = (Integer) query.uniqueResult();
			Map<Integer, String> bedMap = new HashMap<Integer, String>();
			for (int i = 1; i <= noOfOcuupants; i++) {
				bedMap.put(i, Integer.toString(i));
			}
			session.flush();
			// session.close();
			bedMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(bedMap);

			return bedMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	// --------------------------------mine

	/**
	 * 
	 * @param id
	 * @return stateMap <key,value> Ex:<1,karnataka> <2,kerala>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getProgramsByProgramType(int id) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from ProgramType where isActive =1 and id = :programTypeId")
					.setInteger("programTypeId", id);
			ProgramType programType = (ProgramType) query.list().get(0);
			Map<Integer, String> programMap = new HashMap<Integer, String>();
			Iterator<Program> itr = programType.getPrograms().iterator();
			Program program;
			while (itr.hasNext()) {
				program = (Program) itr.next();
				if (program.getIsActive()) {
					programMap.put(program.getId(), program.getName());
				}
			}
			programMap = CommonUtil.sortMapByValue(programMap); 
			// session.close();
			programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);

			return programMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * 
	 * @param id
	 * @return stateMap <key,value> Ex:<1,karnataka> <2,kerala>
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getinterViewTypybyCourse(int id, int year) {
		Session session = null;
		Map<Integer, String> interViewMap = new LinkedHashMap<Integer, String>();

		try {

			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewProgramCourse where course.id = :courseId and year= :year and isActive = true order by sequence");
			query.setInteger("courseId", id);
			query.setInteger("year", year);

			Iterator<InterviewProgramCourse> interviewProgramCourseIterator = query
					.iterate();

			while (interviewProgramCourseIterator.hasNext()) {
				InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) interviewProgramCourseIterator
						.next();
				interViewMap.put(interviewProgramCourse.getId(),
						interviewProgramCourse.getName());
			}
			interViewMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interViewMap);

			return interViewMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}

		return interViewMap;
	}

	/**
		 * 
		 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewTypebyProgram(int id, int year) {
		Session session = null;
		Map<Integer, String> interviewMap = new LinkedHashMap<Integer, String>();
		try {

			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from InterviewProgramCourse where program.id = :programId and year= :year and isActive = true order by sequence");
			query.setInteger("programId", id);
			query.setInteger("year", year);

			Iterator<InterviewProgramCourse> interviewProgramCourseIterator = query
					.iterate();

			while (interviewProgramCourseIterator.hasNext()) {
				InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) interviewProgramCourseIterator
						.next();
				String display = interviewProgramCourse.getName() + "("
						+ interviewProgramCourse.getCourse().getName() + ")";

				interviewMap.put(interviewProgramCourse.getId(), display);
			}
			interviewMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interviewMap);

			return interviewMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}

		return interviewMap;
	}

	public int getCandidateCount(int courseId, int programId, int year,
			ArrayList<Integer> interviewList, int examCenterId) {
		Session session = null;
		int count = 0;
		boolean isCardGenerated = false;
		boolean isbypassed = false;
		try {
			Query query = null;
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			if (courseId != 0) {
				if (examCenterId != 0) {
					query = session
							.createQuery("select count(i.admAppln.id) from InterviewSelected i where i.interviewProgramCourse.id in (:interviewid) and i.interviewProgramCourse.course.id = :courseID and i.admAppln.appliedYear = :year and i.isCardGenerated = :isCardGenerated and i.admAppln.isSelected=0 and i.admAppln.isBypassed = :isbypassed and "
									+ " i.admAppln.examCenter.id = "
									+ examCenterId);
				} else {
					if (examCenterId != 0) {
						query = session
								.createQuery("select count(i.admAppln.id) from InterviewSelected i where i.interviewProgramCourse.id in (:interviewid) and i.interviewProgramCourse.course.id = :courseID and i.admAppln.appliedYear = :year and i.isCardGenerated = :isCardGenerated and i.admAppln.isSelected=0 and i.admAppln.isBypassed = :isbypassed and "
										+ " i.admAppln.examCenter.id = "
										+ examCenterId);
					} else {
						query = session
								.createQuery("select count(i.admAppln.id) from InterviewSelected i where i.interviewProgramCourse.id in (:interviewid) and i.interviewProgramCourse.course.id = :courseID and i.admAppln.appliedYear = :year and i.isCardGenerated = :isCardGenerated and i.admAppln.isSelected=0 and i.admAppln.isBypassed = :isbypassed");
					}
				}
				query.setInteger("courseID", courseId);
			} else {
				if (examCenterId != 0) {
					query = session
							.createQuery("select count(i.admAppln.id) from InterviewSelected i where i.interviewProgramCourse.id in (:interviewid) and i.interviewProgramCourse.program.id = :programID and i.admAppln.appliedYear = :year and i.isCardGenerated = :isCardGenerated and i.admAppln.isSelected=0 and i.admAppln.isBypassed = :isbypassed and "
									+ " i.admAppln.examCenter.id = "
									+ examCenterId);
				} else {
					query = session
							.createQuery("select count(i.admAppln.id) from InterviewSelected i where i.interviewProgramCourse.id in (:interviewid) and i.interviewProgramCourse.program.id = :programID and i.admAppln.appliedYear = :year and i.isCardGenerated = :isCardGenerated and i.admAppln.isBypassed = :isbypassed and i.admAppln.isSelected=0");
				}

				query.setInteger("programID", programId);
			}
			query.setParameterList("interviewid", interviewList);
			query.setInteger("year", year);
			query.setBoolean("isCardGenerated", isCardGenerated);
			query.setBoolean("isbypassed", isbypassed);
			ArrayList<Long> l = new ArrayList<Long>(query.list());

			count = l.get(0).intValue();
			return count;
		} catch (Exception e) {
			// if (session != null)
			// session.close();
			return count;
		}
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getRoomTypesByHostelAndByFloor(int hostelId)
			throws Exception {
		Map<Integer, String> roomTypeMap = new HashMap<Integer, String>();
		try {
			Session session = null;
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("select hlRoomTpe.id, hlRoomTpe.name from HlRoomType hlRoomTpe where hlRoomTpe.isActive = 1 and hlRoomTpe.hlHostel.id='"
							+ hostelId + "'");

			 List<Object[]> objList = (List<Object[]>) query.list();
			for (Object[] objects : objList) {
				roomTypeMap.put(Integer.valueOf(objects[0].toString()),
						objects[1].toString());
			}
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		roomTypeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomTypeMap);

		return roomTypeMap;
	}

	/**
	 * 
	 * @param id
	 * @return courseMap <key,value>
	 */

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getExamCenterForProgram(int id) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(
					"from Program where id = :programId and isActive =1 ")
					.setInteger("programId", id);
			Program program = new Program();
			if (!query.list().isEmpty())
				program = (Program) query.list().get(0);
			Map<Integer, String> examCenterMap = new HashMap<Integer, String>();
			Iterator<ExamCenter> itr = program.getExamCenters().iterator();
			ExamCenter examCenter;
			while (itr.hasNext()) {
				examCenter = (ExamCenter) itr.next();
				examCenterMap.put(examCenter.getId(), examCenter.getCenter());
			}
			// session.close();
			examCenterMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examCenterMap);

			return examCenterMap;
		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/**
	 * @author jboss
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getSubjectByCourseIdTermYearTeacher(int teacherId,String[] selectedClasses,HttpSession httpSession,boolean subjectChange) {
		Map<String, List<Integer>> subjectCodeGroupMap=new HashMap<String, List<Integer>>();
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			StringBuilder intType =new StringBuilder();
			for (int i = 0; i < selectedClasses.length; i++) {
				intType.append(selectedClasses[i]);
				 if(i<(selectedClasses.length-1)){
					 intType.append(",");
				 }
			}
			Query query = session.createQuery(" select tcs "
					+ " from TeacherClassSubject tcs "
					+ " where tcs.isActive=1 and tcs.teacherId = ? " + " and tcs.classId in ("
					+ intType + ")");
			query.setInteger(0, teacherId);
			// query.setInteger(1, courseId);
			Iterator<TeacherClassSubject> tuples = query.list().iterator();
			Map<String, String> classMap = new LinkedHashMap<String, String>();
			while (tuples.hasNext()) {
				TeacherClassSubject tcs = (TeacherClassSubject) tuples.next();
				//code added by mehaboob start
				if(tcs.getSubject().getSubjectCodeGroup()!=null && tcs.getSubject().getSubjectCodeGroup().getId()!=0){
					//if & contains replace & to and mahi
					String subjectCodeName=tcs.getSubject().getSubjectCodeGroup().getSubjectsGroupName();
					if(subjectCodeName.contains("&")){
						subjectCodeName=subjectCodeName.replace("&", "and");
					}
					//end
					if(subjectCodeGroupMap.containsKey(String.valueOf(tcs.getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName)){
						 List<Integer> subjectIds=subjectCodeGroupMap.get(String.valueOf(tcs.getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName);
						//if all subject contains different subjectCodeGroup start
						 for (Integer subjectId : subjectIds) {
							if(classMap.containsKey(String.valueOf(subjectId)) && !subjectChange){
								classMap.remove(String.valueOf(subjectId));
							}
						}
						 //end
						 if(!subjectIds.contains(tcs.getSubject().getId())) {
							 subjectIds.add(tcs.getSubject().getId()); 
						 }
						  subjectCodeGroupMap.put(String.valueOf(tcs.getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName, subjectIds);
						  if(!classMap.containsKey(String.valueOf(tcs.getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName)){
							  classMap.put(String.valueOf(tcs.getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName, tcs.getSubject().getSubjectCodeGroup().getSubjectsGroupName());
						  }
					}else{
						if(tcs.getSubject().getIsActive() && tcs.getIsActive()){
							List<Integer> subjectIds=new ArrayList<Integer>();
							subjectIds.add(tcs.getSubject().getId());
							
							subjectCodeGroupMap.put(String.valueOf(tcs.getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName, subjectIds);
							//if all subject contains different subjectCodeGroup start
							if(!classMap.containsKey(String.valueOf(tcs.getSubject().getId()))){
								  classMap.put(String.valueOf(tcs.getSubject().getId()), tcs.getSubject().getName()+ "(" + tcs.getSubject().getCode() + ")");
							  }
							//end
						}
					}
				}else{
					if(tcs.getSubject().getIsActive() && tcs.getIsActive())
						classMap.put(String.valueOf(tcs.getSubject().getId()), tcs.getSubject().getName()+ "(" + tcs.getSubject().getCode() + ")");
				}
				//end
			}
			session.flush();
			classMap = (HashMap<String, String>) CommonUtil.sortMapByValue(classMap);
			httpSession.setAttribute("SubjectCodeGroupMap", subjectCodeGroupMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<String, String>();

	}

	/**
	 * @author jboss
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByTeacher(int teacherId, String year) {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select tcs "
							+ " from TeacherClassSubject tcs"
							+ " where tcs.teacherId = ? and tcs.year=? and tcs.classId.classes.isActive=1 " 
							//raghu
							+" and tcs.isActive=1 "
									+"and tcs.subject.isActive=1");
			query.setInteger(0, teacherId);
			query.setString(1, year);
			Iterator<TeacherClassSubject> tuples = query.list().iterator();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			while (tuples.hasNext()) {
				TeacherClassSubject tcs = (TeacherClassSubject) tuples.next();
				classMap.put(tcs.getClassId().getId(), tcs.getClassId()
						.getClasses().getName());
			}
			session.flush();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	public Map<Integer, String> getProgramsByAdmitedYear(int year)
			throws Exception {
		ExportPhotosHandler handler = new ExportPhotosHandler();
		return handler.getPrograms(year);
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByYearForMuliSelect(int year) {
		Session session = null;
		try {

			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear order by c.classes.name")
					.setInteger("academicYear", year);
			List<ClassSchemewise> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<ClassSchemewise> itr = classList.iterator();
			ClassSchemewise classSchemewise;

			while (itr.hasNext()) {
				classSchemewise = (ClassSchemewise) itr.next();
				classMap.put(classSchemewise.getClasses().getId(),
						classSchemewise.getClasses().getName());
			}
			session.flush();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.close();
		}

		return new HashMap<Integer, String>();
	}

	@Override
	public Map<Integer, String> getSectionByCourseIdSchemeId(String courseId,
			String schemeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getPeriodByClassSchemewiseIds(
			String[] classSchemeWiseIds) {
		Session session = null;
		ArrayList<Integer> classList = new ArrayList<Integer>();
		if (classSchemeWiseIds != null) {
			for (String string : classSchemeWiseIds) {
				if (string != null) {
					classList.add(Integer.parseInt(string));
				}
			}
		}
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Period period where period.classSchemewise.id in( :classList)  and period.isActive  = 1 order by period.periodName")
					.setParameterList("classList", classList);

			Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();

			Iterator<Period> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Period period = (Period) periodIterator.next();
				periodMap.put(period.getId(), period.getPeriodName());
			}
			periodMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(periodMap);

			return periodMap;
		} catch (Exception e) {
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getRoomsByClassSchemewiseIds(
			ArrayList<Integer> classSchemeWiseIds) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamRoomMasterBO bo");
			Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();
			List list = query.list();
			Iterator<ExamRoomMasterBO> periodIterator = list.iterator();
			while (periodIterator.hasNext()) {
				ExamRoomMasterBO bo = (ExamRoomMasterBO) periodIterator.next();
				periodMap.put(bo.getId(), bo.getRoomNo());
			}
			periodMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(periodMap);

			return periodMap;
		} catch (Exception e) {
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getEducationByQualificationId(
			int qualificationId) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpEducationMaster i where i.isActive = 1 and i.empQualificationLevel.id="
							+ qualificationId);

			Map<Integer, String> educationMap = new LinkedHashMap<Integer, String>();
			List<EmpEducationMaster> list = query.list();
			if (list != null) {
				Iterator<EmpEducationMaster> periodIterator = list.iterator();
				while (periodIterator.hasNext()) {
					EmpEducationMaster objBO = (EmpEducationMaster) periodIterator
							.next();
					educationMap.put(objBO.getId(), objBO.getName());
				}
			}
			educationMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(educationMap);

			return educationMap;
		} catch (Exception e) {
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();

			}
		}

	}
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSpecializationByCourse(
			int courseId) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamSpecializationBO e where e.isActive = 1 and courseUtilBO.courseID = " + courseId);

			Map<Integer, String> specializationMap = new LinkedHashMap<Integer, String>();
			List<ExamSpecializationBO> list = query.list();
			if (list != null) {
				Iterator<ExamSpecializationBO> periodIterator = list.iterator();
				while (periodIterator.hasNext()) {
					ExamSpecializationBO objBO = (ExamSpecializationBO) periodIterator
							.next();
					specializationMap.put(objBO.getId(), objBO.getName());
				}
			}
			specializationMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(specializationMap);

			return specializationMap;
		} catch (Exception e) {
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();

			}
		}

	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesByActivityAndClassScheme(
			int activityId, Set<Integer> classSchemeId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from Batch batch where batch.classSchemewise.id in (:classSchemeId) and batch.activity.id = :activityId  and batch.isActive  = 1 order by batch.batchName")
					.setParameterList("classSchemeId", classSchemeId)
					.setParameter("activityId", activityId);

			Map<Integer, String> batchMap = new HashMap<Integer, String>();

			Iterator<Batch> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Batch batch = periodIterator.next();
				batchMap.put(batch.getId(), batch.getBatchName());
			}
			batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);

			return batchMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
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
	public List<ClassSchemewise> getClassSchemeByCourseId(int semNo,
			Integer year, int courseId) throws Exception {
		Session session = null;
		List<ClassSchemewise> classes = null;
		try {
			session = HibernateUtil.getSession();
			String sql ="select cs "
				+ " from ClassSchemewise cs"
				+ " where cs.curriculumSchemeDuration.curriculumScheme.year ="+ year
				+ " and cs.curriculumSchemeDuration.curriculumScheme.course.id=" + courseId +" order by cs"	;
			/*String sql = "select cs "
					+ " from Classes c1  "
					+ " join c1.classSchemewises cs "
					+ " where c1.isActive=1 and cs.curriculumSchemeDuration.curriculumScheme.year = "
					+ year
					+ " and c1.course.id=" + courseId;*/
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
	
	public List<String> getSectionsByCourseAndSemester(int courseId,int year)
	{
		Session session = null;
		List<String> objList = null;
		try {
			session = HibernateUtil.getSession();
			String sql = "select c.classes.sectionName"
					+ " from ClassSchemewise c"
					+ " where c.classes.sectionName is not empty" 
					+ " and c.classes.course.id="+ courseId
					+ " and c.classes.termNumber=1"
					+ " and c.curriculumSchemeDuration.academicYear ="+year;
			Query queri = session.createQuery(sql);
			objList = queri.list();
		} catch (Exception e) {
			log.error("Error in getClassSchemeForStudent..." + e);
			
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return objList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCourseByProgramInOrder(int id) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(
					"from Course c where c.program.id = :programId and isActive =1 order by c.name")
					.setInteger("programId", id);
			 List<Course> courseList = query.list();
			Map<Integer, String> courseMap = new HashMap<Integer, String>();
			Iterator<Course> itr = courseList.iterator();
			Course course;
			while (itr.hasNext()) {
				course = (Course) itr.next();
					courseMap.put(course.getId(), course.getName());
			}
			courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);

			return courseMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	@Override
	public int getCount(int courseId, int programId, int year,
			ArrayList<Integer> interviewList, int examCenterId, String stime,
			String etime, List<Date> dateList) {
		Session session = null;
		int count = 0;
		try {
			Query query = null;
			session = HibernateUtil.getSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			if (courseId != 0) {
				//" and int.admAppln.courseBySelectedCourseId.program.id = 3"
				query = session.createQuery("select count(id) from InterviewCard int " 
				+" where int.admAppln.courseBySelectedCourseId =:courseID" 
				+" and int.interview.interview.interviewProgramCourse.id in (:interviewList)"
				+" and int.time = '" + stime + "'"   
				+" and int.interview.date in ( :dateList )" 
				+" and int.admAppln.appliedYear = :year");
				query.setInteger("courseID", courseId);
			} else {
				query = session.createQuery("select count(id) from InterviewCard int " 
						+" where int.admAppln.courseBySelectedCourseId.program.id=:programID" 
						+" and int.interview.interview.interviewProgramCourse.id in (:interviewList)"
						+" and int.time = '" + stime + "'"   
						+" and int.interview.date in ( :dateList )" 
						+" and int.admAppln.appliedYear = :year");
				query.setInteger("programID", programId);
			}
			query.setParameterList("interviewList", interviewList);
			query.setParameterList("dateList", dateList);
			query.setInteger("year", year);
			ArrayList<Long> l = new ArrayList<Long>(query.list());

			count = l.get(0).intValue();
			return count;
		} catch (Exception e) {
			return count;
		}
	}

	@Override
	public Map<Integer, String> getRoomsByFloor(int hostelId, int floorNo) {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlRoom h where h.isActive = 1 and  h.hlHostel.id='"
							+ hostelId+"' and  h.floorNo='" + floorNo + "' order by h.name");
			List<HlRoom> roomlist = query.list();
			Map<Integer, String> roomMap = new HashMap<Integer, String>();
			Iterator<HlRoom> itr = roomlist.iterator();
			HlRoom hlRoom;
			// int roomNo = 0;
			while (itr.hasNext()) {
				hlRoom = (HlRoom) itr.next();
				roomMap.put(Integer.valueOf(hlRoom.getId()), hlRoom.getName());

			}
			roomMap=CommonUtil.sortMapByValueForAlphaNumeric(roomMap);
			session.flush();
			roomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomMap);

			return roomMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	/**
	 * @param id
	 * @return interviewSubroundsMap
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewSubroundsByInterviewTypeForMultiSelect(String[] id) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String [] tempArray =id;
			StringBuilder intType =new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
			Query query = session
					.createQuery("from InterviewSubRounds where interviewProgramCourse.id in ("+intType+") and isActive = 1");

			Map<Integer, String> interviewSubroundsMap = new LinkedHashMap<Integer, String>();
			Iterator<InterviewSubRounds> interviewSubroundsIterator = query.iterate();

			while (interviewSubroundsIterator.hasNext()) {
				InterviewSubRounds interviewProgramCourse = (InterviewSubRounds) interviewSubroundsIterator
						.next();
				interviewSubroundsMap.put(interviewProgramCourse.getId(),
						interviewProgramCourse.getName());
			}
			interviewSubroundsMap=CommonUtil.sortMapByValue(interviewSubroundsMap);
			return interviewSubroundsMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}
		return new HashMap<Integer, String>();
	}
	
	public String getRegisterNo(String ipAddress)
	{
		Session session = null;
		String registerNo=" ";
		Transaction tran=null;
		try {
			session = HibernateUtil.getSession();
			tran=session.beginTransaction();
			RegisterNumberFromSmartCard registerNumberFromSmartCard =(RegisterNumberFromSmartCard) session.createQuery("from RegisterNumberFromSmartCard where ipAddress='"+ipAddress+"'").uniqueResult();
			if(registerNumberFromSmartCard==null)
				registerNo=" ";
			if(registerNo!=null)
			{
				registerNo=registerNumberFromSmartCard.getRegisterNumber();
				session.delete(registerNumberFromSmartCard);
				tran.commit();
			}
				
			
			return registerNo;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			tran.rollback();
		} finally {
			// if (session != null)
			// session.close();
		}
		return registerNo;
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getEmployeeCodeName(String eCodeName){
		Session session = null;
		Map<Integer, String> employeeMap = new HashMap<Integer, String>();
		Object[] employee;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select e.id, e.firstName, e.middleName, e.lastName,e.code from Employee e where e.isActive=1");
			if (query.list() != null) {
				Iterator<Object[]> iterator = query.list().iterator();
				while (iterator.hasNext()) {
					employee = (Object[]) iterator.next();
					int id = 0;
					if (employee[0] != null
							&& Integer.parseInt(employee[0].toString()) != 0) {
						id = Integer.parseInt(employee[0].toString());
					}
					String name = "";
					if(eCodeName.equals("eCode"))
					{
					if (employee[4] != null) {
						name = employee[4].toString();
					}
					if (employee[1] != null) {
						name = name + "(" + employee[1].toString();
					}
					if (employee[2] != null) {
						name = name + " " + employee[2].toString();
					}
					if (employee[3] != null) {
						name = name + " " + employee[3].toString()+")";
					}
					}else{
						if (employee[1] != null) {
							name = employee[1].toString();
						}
						if (employee[2] != null) {
							name = name + " " + employee[2].toString();
						}
						if (employee[3] != null) {
							name = name + " " + employee[3].toString();
						}
						if (employee[4] != null) {
							name = name + "(" + employee[4].toString()+")";
						}
					}

					employeeMap.put(id, name);
				}
			}
			employeeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(employeeMap);

			return employeeMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
@SuppressWarnings("unchecked")
public Map<Integer, String> getSubjectGroupByFeeAdditionalForMultiSelect(String[] id){
		
		Session session = null;
		FeeAdditional feeAdditional;
		SubjectGroupSubjects subjectGroupSubjects;
		Map<Integer, String> subjectGroupMap = new HashMap<Integer, String>();
		int subGroupId;
		String subGroupName;
		try {
			int sid=0;
			session = HibernateUtil.getSession();
			String [] tempArray =id;
			StringBuilder feeGroupId=new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				 feeGroupId.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 feeGroupId.append(",");
				 }
			}
			Query query = session
					.createQuery("from FeeAdditional f where f.feeGroup.id in ("+feeGroupId+") and f.isActive = 1");
			if (query.list() != null) {
				Iterator<FeeAdditional> iterator = query.list().iterator();
				while (iterator.hasNext()) {
					feeAdditional = (FeeAdditional)iterator.next();
					
					//if (feeAdditional.getCertificateCourse() != null) {
//						if(feeAdditional.getCertificateCourse().getSubject()!=null){
//							sid=feeAdditional.getCertificateCourse().getSubject().getId();
//						}
					//}
					
				}
			}
			if(sid>0){
				query = session
				.createQuery("from SubjectGroupSubjects s where s.subject.id='"+sid+"'and s.isActive = 1");
					if (query.list() != null) {
						Iterator<SubjectGroupSubjects> iterator = query.list().iterator();
						while (iterator.hasNext()) {
							subjectGroupSubjects = (SubjectGroupSubjects)iterator.next();
						subGroupId=	subjectGroupSubjects.getSubjectGroup().getId();
						subGroupName=subjectGroupSubjects.getSubjectGroup().getName();
						subjectGroupMap.put(subGroupId, subGroupName);
						}
					}
				}
			subjectGroupMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectGroupMap);

			return subjectGroupMap;
			
		}catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getAttendanceSubjectsByRegisterNo(String regNo)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select s.attendance.subject from AttendanceStudent s" +
					" join s.attendance.attendanceClasses c where s.student.registerNo=:regNo" +
					" and c.classSchemewise.classes.termNumber=( select st.classSchemewise.classes.termNumber" +
					" from Student st where st.registerNo=:regNo ) group by s.attendance.subject.id");
			query.setString("regNo", regNo);
			List<Subject> list = query.list();
			Map<Integer, String> subjectMap = new HashMap<Integer, String>();
			Iterator<Subject> itr = list.iterator();
			Subject s;
			while (itr.hasNext()) {
				s = (Subject) itr.next();
				subjectMap.put(s.getId(), s.getName());
			}
			subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);

			return subjectMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCoursesByYear(int year) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from CertificateCourse c where c.isActive = 1 and c.year = :year order by c.certificateCourseName")
					.setInteger("year", year);
			List<CertificateCourse> classList = query.list();
			Map<Integer, String> courseMap = new LinkedHashMap<Integer, String>();
			Iterator<CertificateCourse> itr = classList.iterator();
			CertificateCourse certificateCourse;

			while (itr.hasNext()) {
				certificateCourse = (CertificateCourse) itr.next();
				courseMap.put(certificateCourse.getId(), certificateCourse.getCertificateCourseName()+"("+certificateCourse.getSemType()+")");
						
			}
			session.flush();
			// session.close();
			courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
			return courseMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	
	
	/**
	 * Made class Schemewise from Batch to BatchStudent so that we are writing new method
	 * @param SubjectId
	 * @param classSchemeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesBySubjectAndClassScheme1(String SubjectId, Set<Integer> classSchemeId,HttpSession httpSession) {
		Session session = null;
		//added by mehaboob start
		Map<Integer, List<Integer>> subjectBatchMap=new HashMap<Integer, List<Integer>>();
		Map<Integer, String> batchMap=null;
		//end
		try {
			session = HibernateUtil.getSession();
			//code added by mehaboob start 
			Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) httpSession.getAttribute("SubjectCodeGroupMap");
			if(subjectCodeGroupMap!=null && !subjectCodeGroupMap.isEmpty()){
				if(subjectCodeGroupMap.containsKey(SubjectId)){
					 batchMap=new HashMap<Integer, String>();
					List<Integer> subjectIdList=subjectCodeGroupMap.get(SubjectId);
					for (Integer subjectId1 : subjectIdList) {
						Query query1=session.createQuery("select subject.code from Subject subject where subject.id="+subjectId1);
						String subjectCode=(String) query1.uniqueResult();
						//end
						Query query = session
								.createQuery(
										"select bs.batch" +
										" from BatchStudent bs" +
										" where bs.batch.isActive=1" +
										" and bs.classSchemewise.classes.isActive=1" +
										" and bs.classSchemewise.id in (:classSchemeId) and bs.batch.subject.id=:subjectId" +
										" group by bs.batch.id order by bs.batch.batchName")
								.setParameterList("classSchemeId", classSchemeId)
								.setParameter("subjectId", subjectId1);

						//Map<Integer, String> batchMap = new HashMap<Integer, String>();

						Iterator<Batch> periodIterator = query.iterate();
						while (periodIterator.hasNext()) {
							Batch batch = periodIterator.next();
							//added by mehaboob start
							if(subjectCode!=null && !subjectCode.isEmpty()){
								if(!batchMap.containsKey(batch.getId())){
								batchMap.put(batch.getId(), batch.getBatchName()+"("+subjectCode+")");
								}
							}else{
								if(!batchMap.containsKey(batch.getId())){
								batchMap.put(batch.getId(), batch.getBatchName());
								}
							}
							//end
							if(subjectBatchMap.containsKey(subjectId1)){
								List<Integer> batchIdList=subjectBatchMap.get(subjectId1);
								if(!batchIdList.contains(batch.getId())){
								batchIdList.add(batch.getId());
								}
								subjectBatchMap.put(subjectId1, batchIdList);
							}else{
								List<Integer> batchIdList=new ArrayList<Integer>();
								batchIdList.add(batch.getId());
								subjectBatchMap.put(subjectId1, batchIdList);
							}
							
						}
					
					}
				}else{
					Query query1=session.createQuery("select subject.code from Subject subject where subject.id="+Integer.parseInt(SubjectId));
					String subjectCode=(String) query1.uniqueResult();
					//end
					Query query = session
							.createQuery(
									"select bs.batch" +
									" from BatchStudent bs" +
									" where bs.batch.isActive=1" +
									" and bs.classSchemewise.classes.isActive=1" +
									" and bs.classSchemewise.id in (:classSchemeId) and bs.batch.subject.id=:subjectId" +
									" group by bs.batch.id order by bs.batch.batchName")
							.setParameterList("classSchemeId", classSchemeId)
							.setParameter("subjectId", Integer.parseInt(SubjectId));

					 batchMap = new HashMap<Integer, String>();

					Iterator<Batch> periodIterator = query.iterate();
					while (periodIterator.hasNext()) {
						Batch batch = periodIterator.next();
						//added by mehaboob start
						if(subjectCode!=null && !subjectCode.isEmpty()){
							batchMap.put(batch.getId(), batch.getBatchName()+"("+subjectCode+")");
						}else{
							batchMap.put(batch.getId(), batch.getBatchName());	
						}
						if(subjectBatchMap.containsKey(Integer.parseInt(SubjectId))){
							List<Integer> batchIdList=subjectBatchMap.get(Integer.parseInt(SubjectId));
							batchIdList.add(batch.getId());
							subjectBatchMap.put(Integer.parseInt(SubjectId), batchIdList);
						}else{
							List<Integer> batchIdList=new ArrayList<Integer>();
							batchIdList.add(batch.getId());
							subjectBatchMap.put(Integer.parseInt(SubjectId), batchIdList);
						}
						
						//end
					}
					batchMap=(HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
				}
				
			}else{
				Query query1=session.createQuery("select subject.code from Subject subject where subject.id="+Integer.parseInt(SubjectId));
				String subjectCode=(String) query1.uniqueResult();
				//end
				Query query = session
						.createQuery(
								"select bs.batch" +
								" from BatchStudent bs" +
								" where bs.batch.isActive=1" +
								" and bs.classSchemewise.classes.isActive=1" +
								" and bs.classSchemewise.id in (:classSchemeId) and bs.batch.subject.id=:subjectId" +
								" group by bs.batch.id order by bs.batch.batchName")
						.setParameterList("classSchemeId", classSchemeId)
						.setParameter("subjectId", Integer.parseInt(SubjectId));

				 batchMap = new HashMap<Integer, String>();

				Iterator<Batch> periodIterator = query.iterate();
				while (periodIterator.hasNext()) {
					Batch batch = periodIterator.next();
					//added by mehaboob start
					if(subjectCode!=null && !subjectCode.isEmpty()){
						batchMap.put(batch.getId(), batch.getBatchName()+"("+subjectCode+")");
					}else{
						batchMap.put(batch.getId(), batch.getBatchName());	
					}
					
					if(subjectBatchMap.containsKey(Integer.parseInt(SubjectId))){
						List<Integer> batchIdList=subjectBatchMap.get(Integer.parseInt(SubjectId));
						batchIdList.add(batch.getId());
						subjectBatchMap.put(Integer.parseInt(SubjectId), batchIdList);
					}else{
						List<Integer> batchIdList=new ArrayList<Integer>();
						batchIdList.add(batch.getId());
						subjectBatchMap.put(Integer.parseInt(SubjectId), batchIdList);
					}
					//end
				}
				batchMap=(HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
			}
			//end
			httpSession.setAttribute("SubjectBatchMap", subjectBatchMap);
			return batchMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}
	
	/**
	 * * Made class Schemewise from Batch to BatchStudent so that we are writing new method
	 * @param activityId
	 * @param classSchemeId
	 * @return
	 */
	public Map<Integer, String> getBatchesByActivityAndClassScheme1(int activityId, Set<Integer> classSchemeId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select bs.batch" +
					" from BatchStudent bs where bs.batch.isActive=1" +
					" and bs.classSchemewise.classes.isActive=1" +
					" and bs.classSchemewise.id in (:classSchemeId) and bs.batch.activity.id=:activityId" +
					" group by bs.batch.id order by bs.batch.batchName")
					.setParameterList("classSchemeId", classSchemeId)
					.setParameter("activityId", activityId);

			Map<Integer, String> batchMap = new HashMap<Integer, String>();

			Iterator<Batch> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Batch batch = periodIterator.next();
				batchMap.put(batch.getId(), batch.getBatchName());
			}
			batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);

			return batchMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}
	/* 
	 *  Made class Schemewise from Batch to BatchStudent so that we are writing new method
	 * (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getBatchesByClassSchemewiseId(java.util.Set)
	 */
	public Map<Integer, String> getBatchesByClassSchemewiseId1(Set<Integer> classSchemeId) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select bs.batch from BatchStudent bs" +
					" where bs.batch.isActive=1" +
					" and bs.classSchemewise.classes.isActive=1 and bs.classSchemewise.id in (:classSchemeId)" +
					" group by bs.batch.id order by bs.batch.batchName").setParameterList("classSchemeId", classSchemeId);

			Map<Integer, String> batchMap = new HashMap<Integer, String>();

			Iterator<Batch> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Batch batch = periodIterator.next();
				batchMap.put(batch.getId(), batch.getBatchName());
			}
			batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
			return batchMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	@Override
	public List<String> getDynamicFingerPrintIds(String fingerPrintId,String userId)
			throws Exception {
		Session session=null;
		List<String> fingerPrintIds=null;
		fingerPrintId=fingerPrintId+"%";
		try{
			session=HibernateUtil.getSession();
			String mark="%";
			String quer="select emp.fingerPrintId from Employee emp where emp.department.id in "+
                         "(select td.departmentId.id from TeacherDepartment td where td.teacherId="+userId+") and emp.fingerPrintId like :fingerprint";
			Query query=session.createQuery(quer);
			query.setString("fingerprint", fingerPrintId);
			fingerPrintIds=query.list();
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
		}
		return fingerPrintIds;
	}

	@Override
	public Map<Integer, String> getCourseByProgramForOnline(int id) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(
					"from Program where id = :programId and isActive =1 ")
					.setInteger("programId", id);
			Program program = new Program();
			if (!query.list().isEmpty())
				program = (Program) query.list().get(0);
			Map<Integer, String> courseMap = new HashMap<Integer, String>();
			Iterator<Course> itr = program.getCourses().iterator();
			Course course;
			while (itr.hasNext()) {
				course = (Course) itr.next();
				if (course.getIsActive() && course.getIsAppearInOnline())
					courseMap.put(course.getId(), course.getName());
			}
			courseMap=CommonUtil.sortMapByValue(courseMap);
			
			// session.close();
			
			return courseMap;
		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getExamNameByExamTypeAndYear(java.lang.String, int)
	 */
	public Map<Integer, String> getExamNameByExamTypeAndYear(String examType, int year) throws Exception {
		Session session = null;
		Map<Integer, String> map=new HashMap<Integer, String>();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String SQL_QUERY = null;
			if(StringUtils.isNumeric(examType)){
				SQL_QUERY="from ExamDefinitionBO e where e.examTypeUtilBO.id=" +examType+
						" and e.delIsActive = 1 and e.isActive = 1 and e.academicYear="+year;
			}
			else {
				if (examType.contains("Reg")) {
					SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Reg%' and e.delIsActive = 1 and e.isActive = 1 and e.academicYear="+year;
				} else if (examType.contains("Suppl")) {
					SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Suppl%' and e.delIsActive = 1 and e.isActive = 1 and e.academicYear="+year;
				} else if (examType.contains("Int")) {
					SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Int%' and e.delIsActive = 1 and e.isActive = 1 and e.academicYear="+year;
				}
			}
			Query query = session.createQuery(SQL_QUERY);
			Iterator<ExamDefinitionBO> itr=query.list().iterator();
			while (itr.hasNext()) {
				ExamDefinitionBO exam= itr.next();
				map.put(exam.getId(),exam.getName());
			}
			return map;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}

	@Override
	public Map<Integer, String> getCourseByYear(int year) throws Exception {
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				
				Query query = session .createQuery( "from CurriculumScheme scheme where  scheme.year = :year order by scheme.course.name") .setInteger("year", year);
				List<CurriculumScheme> courseList = query.list();
				Map<Integer, String> courseMap = new HashMap<Integer, String>();
				Iterator<CurriculumScheme> itr = courseList.iterator();
				CurriculumScheme scheme;
				Course course;
				while (itr.hasNext()) {
					scheme = (CurriculumScheme) itr.next();
					course=scheme.getCourse();
					courseMap.put(course.getId(),course.getName());
				}
				session.flush();
				// session.close();
//				courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
				return courseMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}

			return new HashMap<Integer, String>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getClassesByCourse(int, int)
	 */
	@Override
	public Map<Integer, String> getClassesByCourse(int courseId,int year)
			throws Exception {
		try {
			Session session = HibernateUtil.getSession();
			Query query = session .createQuery( "from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.classes.course.id=:courseId and c.curriculumSchemeDuration.academicYear = :academicYear order by c.classes.name")
			.setInteger("courseId", courseId);
			query.setInteger("academicYear", year);
			List<ClassSchemewise> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<ClassSchemewise> itr = classList.iterator();
			ClassSchemewise classes;

			while (itr.hasNext()) {
				classes = (ClassSchemewise) itr.next();
				classMap.put(classes.getId(), classes.getClasses().getName());
						
			}
			session.flush();
//			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getPeriodsByTeacher(int, java.lang.String)
	 */
	@Override
	public AttendanceTO getPeriodsByTeacher(String teacherId, String year,String day,String date)
			throws Exception {
		AttendanceTO to=new AttendanceTO();
		 int periodId=0;
		try {
			Session session = HibernateUtil.getSession();
			Query query = session .createQuery( "select t.ttSubjectBatch.ttPeriodWeek.period from TTUsers t where t.users.id in ("+teacherId+") " +
					" and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = :academicYear and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"'" +
							" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate" +
							" group by t.ttSubjectBatch.ttPeriodWeek.period.periodName order by t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id");
			query.setInteger("academicYear", Integer.parseInt(year));
			List<Period> classList = query.list();
			Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();
			Map<Integer, String> additionalUserMap = new LinkedHashMap<Integer, String>();
			
			//code for solving academic year problem during corona pandemic
			//start
			/*StringBuffer st=new StringBuffer("(");
			for (Integer sem : CMSConstants.ACADEMIC_YEAR_OLD_SEM) {
				st.append(sem+",");
				
			}
			st.deleteCharAt(st.length()-1);
			st.append(")");
			String sm=st.toString();
			Query query1 = session .createQuery( "select t.ttSubjectBatch.ttPeriodWeek.period from TTUsers t where t.users.id in ("+teacherId+") " +
					" and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = :academicYear and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"'" +
							" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate" +
							" and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.semesterYearNo in"+sm+
							" group by t.ttSubjectBatch.ttPeriodWeek.period.periodName order by t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id");
			query1.setInteger("academicYear", Integer.parseInt(year)-1);
			List<Period> classList1 = query1.list();*/
			//end
			
			if((classList!=null && !classList.isEmpty()) /*|| (classList1!=null && !classList1.isEmpty())*/){
			Iterator<Period> itr = classList.iterator();
			Period period;
			List<Integer> periodList=new ArrayList<Integer>();
			while (itr.hasNext()) {
				period = itr.next();
				//periodMap.put(period.getId(), period.getPeriodName()+"("+period.getStartTime()+"-"+period.getEndTime()+")");
				periodMap.put(period.getId(), period.getPeriodName());
				periodList.add(period.getId());
			}
			
			//code for solving academic year problem during corona pandemic
			//start
			
			/*Iterator<Period> itr1 = classList1.iterator();
			while (itr1.hasNext()) {
				period = itr1.next();
				//periodMap.put(period.getId(), period.getPeriodName()+"("+period.getStartTime()+"-"+period.getEndTime()+")");
				periodMap.put(period.getId(), period.getPeriodName());
				periodList.add(period.getId());
			}*/
			
			//end
			
			Integer s=(Integer)session.createSQLQuery(" select p.id from period p where p.is_active=1 and '"+CommonUtil.getCurrentTime()+"' between subtime(p.start_time,'00:10:00') and addtime(p.start_time,concat('00:',(select time_limit_for_automatic_att_entry from organisation),':00')) and p.id in (:periodId)").setParameterList("periodId", periodList).uniqueResult();
			if(s!=null ){
				periodId=s;
				List<TTUsers> userList=session.createQuery("from TTUsers t where   t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.weekDay=:day and t.ttSubjectBatch.ttPeriodWeek.period.id=:periodId order by t.users.userName ").setString("day",day).setInteger("periodId", periodId).list();
				if(userList!=null && !userList.isEmpty()){
					Iterator<TTUsers> userItr=userList.iterator();
					while (userItr.hasNext()) {
						TTUsers bo=userItr.next();
						if(!teacherId.contains(String.valueOf(bo.getUsers().getId()))){
							additionalUserMap.put(bo.getUsers().getId(),bo.getUsers().getUserName());
						}
					}
				}
			}
			session.flush();
			periodMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(periodMap);
			}
			//added by mahi
			if(periodId>0){
				String periodName=periodMap.get(periodId);
				if(periodName!=null && !periodName.isEmpty()){
					to.setPeriodName(periodName);
				}
			}
			//end
			to.setPeriodId(periodId);
			to.setPeriodMap(periodMap);
			to.setAdditionalUserMap(additionalUserMap);
			return to;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return to;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getClassesByPeriodsAndTeachers(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<Integer, String> getClassesByPeriodsAndTeachers(String teachers, String year, String periods,String day,String date) throws Exception {
		try {
			Session session = HibernateUtil.getSession();
			Query query = session .createQuery( "select t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise from TTUsers t where t.users.id in ("+teachers+") and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = :year and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.period.periodName in ( select p.periodName from Period p where p.isActive=1 and p.id in ("+periods+") ) and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"' " +
					" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate" +		
					" group by t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id");
			//athira
			/*Query query = session .createQuery( "select t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise from TTUsers t where t.ttSubjectBatch.ttPeriodWeek.ttClasses.isApproved=1 and t.users.id in ("+teachers+") and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = :year and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.period.periodName in ( select p.periodName from Period p where p.isActive=1 and p.id in ("+periods+") ) and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"' " +
					" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate" +		
					" group by t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id");*/
			query.setInteger("year", Integer.parseInt(year));
			List<ClassSchemewise> classList = query.list();
			
			//code for solving academic year issue due to corona
			//start
			
			/*StringBuffer st=new StringBuffer("(");
			for (Integer sem : CMSConstants.ACADEMIC_YEAR_OLD_SEM) {
				st.append(sem+",");
				
			}
			st.deleteCharAt(st.length()-1);
			st.append(")");
			String sm=st.toString();
			Query query1 = session .createQuery( "select t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise from TTUsers t where t.users.id in ("+teachers+") and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = :year and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.period.periodName in ( select p.periodName from Period p where p.isActive=1 and p.id in ("+periods+") ) and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"' " +
					" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate" +		
					" and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.semesterYearNo in"+sm+
					" group by t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id");
			//athira
			query1.setInteger("year", Integer.parseInt(year)-1);
			List<ClassSchemewise> classList1 = query1.list();*/
			
			//end
			
			Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();
			Iterator<ClassSchemewise> itr = classList.iterator();
			ClassSchemewise classSchemewise;
			while (itr.hasNext()) {
				classSchemewise = itr.next();
				periodMap.put(classSchemewise.getId(), classSchemewise.getClasses().getName());
			}
			//code for solving academic year issue due to corona
			//start
			/*Iterator<ClassSchemewise> itr1 = classList1.iterator();
			while (itr1.hasNext()) {
				classSchemewise = itr1.next();
				periodMap.put(classSchemewise.getId(), classSchemewise.getClasses().getName());
			}*/
			//end
			session.flush();
			periodMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(periodMap);
			return periodMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getSubjectsByPeriodsAndTeachers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 subjectMap<Integer,String> changed to subjectMap<String,String> by mehaboob
	 */
	@Override
	public AttendanceTO getSubjectsByPeriodsAndTeachers( String teachers, String year, String periods,String day,String date,String periodName,HttpSession httpSession) throws Exception { 
		AttendanceTO to=new AttendanceTO();
		try {
			String classId="";
			String subjectId="";
			String oneSubjectId="";
			int attendanceTypeId=0;
			int batchId=0;
			int activityId=0;
			//added by mehaboob count start
			int count=1;
			boolean isError=false;
			//end
			//added by mehaboob start
			Map<String, List<Integer>> subjectCodeGroupMap=new HashMap<String, List<Integer>>();
			List<Integer> batchIdList=new ArrayList<Integer>();
			//key is subjectId and value is batchId subjectBatchMap
			Map<Integer, Integer> subjectBatchMap=new HashMap<Integer, Integer>();
			//end
			Map<Integer,String> additionalPeriods=new LinkedHashMap<Integer, String>();
			Session session = HibernateUtil.getSession();
			Query query = session .createQuery( "select t from TTUsers t where t.ttSubjectBatch.ttPeriodWeek.ttClasses.isApproved=1 and t.users.id in ("+teachers+") and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = :year and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.period.periodName in ( select p.periodName from Period p where p.isActive=1 and p.id in ("+periods+") ) and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"' " +
					" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate" );
			query.setInteger("year", Integer.parseInt(year));
			List<TTUsers> classList = query.list();
			//academic year issue code due tocovid pandemic
			//start
			/*StringBuffer st=new StringBuffer("(");
			for (Integer sem : CMSConstants.ACADEMIC_YEAR_OLD_SEM) {
				st.append(sem+",");
				
			}
			st.deleteCharAt(st.length()-1);
			st.append(")");
			String sm=st.toString();
			Query query1 = session .createQuery( "select t from TTUsers t where t.ttSubjectBatch.ttPeriodWeek.ttClasses.isApproved=1 and t.users.id in ("+teachers+") and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = :year and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.period.periodName in ( select p.periodName from Period p where p.isActive=1 and p.id in ("+periods+") ) and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"' " +
					" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate"+
					" and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.semesterYearNo in"+sm	);
			query1.setInteger("year", Integer.parseInt(year)-1);
			List<TTUsers> classList1 = query1.list();
			for (TTUsers ttUsers : classList1) {
				classList.add(ttUsers);
			}*/
			
			//end
			Map<String, String> periodMap = new LinkedHashMap<String, String>();
			Iterator<TTUsers> itr = classList.iterator();
			TTUsers ttUsers;
			while (itr.hasNext()) {
				ttUsers = itr.next();
				if(ttUsers.getTtSubjectBatch().getSubject()!=null)
					periodMap.put(String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getId()), ttUsers.getTtSubjectBatch().getSubject().getName()+"("+ttUsers.getTtSubjectBatch().getSubject().getCode()+")");
				if(classId.isEmpty())
					classId=String.valueOf(ttUsers.getTtSubjectBatch().getTtPeriodWeek().getTtClasses().getClassSchemewise().getId());
				else
					classId=classId+","+String.valueOf(ttUsers.getTtSubjectBatch().getTtPeriodWeek().getTtClasses().getClassSchemewise().getId());
				if(activityId==0 && ttUsers.getTtSubjectBatch().getSubject()!=null){
					//code added by mehaboob start
					if(ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup()!=null && ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getId()!=0){
						String subjectCodeName=ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getSubjectsGroupName();
						if(subjectCodeName.contains("&")){
							subjectCodeName=subjectCodeName.replace("&", "and");
						}
						if(subjectCodeGroupMap.containsKey(String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName)){
							List<Integer> subjectIds=subjectCodeGroupMap.get(String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName);
							if(!subjectIds.contains(ttUsers.getTtSubjectBatch().getSubject().getId())){
								subjectIds.add(ttUsers.getTtSubjectBatch().getSubject().getId());
								subjectId=String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName;
								periodMap.put(String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName, ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getSubjectsGroupName());
							}
							subjectCodeGroupMap.put(String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName, subjectIds);
							if(ttUsers.getTtSubjectBatch().getBatch()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive()){
								if(!batchIdList.contains(ttUsers.getTtSubjectBatch().getBatch().getId())){
									batchIdList.add(ttUsers.getTtSubjectBatch().getBatch().getId());
									subjectBatchMap.put(ttUsers.getTtSubjectBatch().getSubject().getId(), ttUsers.getTtSubjectBatch().getBatch().getId());
								}
							}
							
						}else{
							if(subjectCodeGroupMap.isEmpty()){
								List<Integer> subjectIds=new ArrayList<Integer>();
								subjectIds.add(ttUsers.getTtSubjectBatch().getSubject().getId());
								subjectCodeGroupMap.put(String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName, subjectIds);
								oneSubjectId=String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getSubjectCodeGroup().getId())+"_"+subjectCodeName;
								if(ttUsers.getTtSubjectBatch().getBatch()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive()){
									batchIdList.add(ttUsers.getTtSubjectBatch().getBatch().getId());
									   subjectBatchMap.put(ttUsers.getTtSubjectBatch().getSubject().getId(), ttUsers.getTtSubjectBatch().getBatch().getId());
								}
							}else{
								isError=true;
							}
							
						}
					}else{
						if(subjectCodeGroupMap.isEmpty()){
							if(count==1){
								subjectId=String.valueOf(ttUsers.getTtSubjectBatch().getSubject().getId());
								
								if(ttUsers.getTtSubjectBatch().getBatch()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive())
									batchId=ttUsers.getTtSubjectBatch().getBatch().getId();
							}
							count++;
						}else{
							isError=true;
						}
					}
					//end
				}
				if(attendanceTypeId==0){
					String ispractical=ttUsers.getTtSubjectBatch().getSubject().getIsTheoryPractical();
					if (ispractical=="T" || ispractical.equalsIgnoreCase("T")) {
						attendanceTypeId=3;
					}
					if (ispractical=="P" || ispractical.equalsIgnoreCase("P")) {
						attendanceTypeId=2;
					}
				}
				if(activityId==0  && subjectId.isEmpty() && ttUsers.getTtSubjectBatch().getActivity()!=null){
					activityId=ttUsers.getTtSubjectBatch().getActivity().getId();
					if(ttUsers.getTtSubjectBatch().getBatch()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive())
						batchId=ttUsers.getTtSubjectBatch().getBatch().getId();
				}
			}
			//code added by mehaboob start
			if(!isError){
				if(subjectId.isEmpty()){
					List<Integer> subjectIDs=subjectCodeGroupMap.get(oneSubjectId);
					if(subjectIDs!=null && !subjectIDs.isEmpty()){
					if(subjectIDs.size()==1){
				        for (Integer integer : subjectIDs) {
							subjectId=String.valueOf(integer);
							//athira
							if(!subjectBatchMap.isEmpty()){
							batchId=subjectBatchMap.get(integer);
							}
						}
					}
					}
					subjectCodeGroupMap.remove(oneSubjectId);
				}
				if(!subjectCodeGroupMap.isEmpty()){
				   List<Integer> subjectIDs=subjectCodeGroupMap.get(subjectId);
				   if(!subjectBatchMap.isEmpty()){
					   for (Integer integer : subjectIDs) {
						batchId=subjectBatchMap.get(integer);
						if(!classId.isEmpty() && (activityId>0 || integer>0)){
							List<TTPeriodWeek> periodList=session.createQuery("select t from TTPeriodWeek t join t.ttSubjectBatchs ts join ts.ttUsers tu where t.isActive=1 and ts.isActive=1 and t.ttClasses.isActive=1 and tu.isActive=1 and tu.users.isActive=1 and tu.users.id in (" +teachers+")"+
									" and t.ttClasses.isApproved=1 " +( integer!=0? " and ts.subject.id="+integer:"") +(activityId!=0? " and ts.activity.id= " +activityId:"")+
									" and t.ttClasses.classSchemewise.id in ("+classId+") " +(batchId!=0?" and ts.batch.id="+batchId:"")+" and t.weekDay='"+day+"' group by t.period.periodName order by t.period.periodName ").list();
							if(periodList!=null && !periodList.isEmpty()){
								Iterator<TTPeriodWeek> periodItr=periodList.iterator();
								while (periodItr.hasNext()) {
									TTPeriodWeek bo=periodItr .next();
									if(periodName!=null && !periodName.isEmpty()){
									String pName = periodName.substring(0, periodName.indexOf('('));
									if(!pName.contains(String.valueOf(bo.getPeriod().getPeriodName()))){
										additionalPeriods.put(bo.getPeriod().getId(), bo.getPeriod().getPeriodName());
									}
									}else{
										if(!periods.contains(String.valueOf(bo.getPeriod().getId()))){
											additionalPeriods.put(bo.getPeriod().getId(), bo.getPeriod().getPeriodName());
										}
									}
								}
							}
						}
					}
					   to.setAdditionalPeriods(additionalPeriods); 
					   to.setSubjectBatchMap(subjectBatchMap);
				   }else{
					   for (Integer integer : subjectIDs) {
						   if(!classId.isEmpty() && (activityId>0 || integer>0)){
								List<TTPeriodWeek> periodList=session.createQuery("select t from TTPeriodWeek t join t.ttSubjectBatchs ts join ts.ttUsers tu where t.isActive=1 and ts.isActive=1 and t.ttClasses.isActive=1 and tu.isActive=1 and tu.users.isActive=1 and tu.users.id in (" +teachers+")"+
										" and t.ttClasses.isApproved=1 " +( integer!=0? " and ts.subject.id="+integer:"") +(activityId!=0? " and ts.activity.id= " +activityId:"")+
										" and t.ttClasses.classSchemewise.id in ("+classId+") " +(batchId!=0?" and ts.batch.id="+batchId:"")+" and t.weekDay='"+day+"' group by t.period.periodName order by t.period.periodName ").list();
								if(periodList!=null && !periodList.isEmpty()){
									Iterator<TTPeriodWeek> periodItr=periodList.iterator();
									while (periodItr.hasNext()) {
										TTPeriodWeek bo=periodItr .next();
										if(periodName!=null &&!periodName.isEmpty()){
										String pName = periodName.substring(0, periodName.indexOf('('));
										if(!pName.contains(String.valueOf(bo.getPeriod().getPeriodName()))){
											additionalPeriods.put(bo.getPeriod().getId(), bo.getPeriod().getPeriodName());
										}
										}else{
											if(!periods.contains(String.valueOf(bo.getPeriod().getId()))){
												additionalPeriods.put(bo.getPeriod().getId(), bo.getPeriod().getPeriodName());
											}
										}
									}
								}
							}   
					   }
					   to.setAdditionalPeriods(additionalPeriods);   
				   }
				   to.setSubjectId(subjectId);
				   httpSession.setAttribute("SubjectCodeGroupMap", subjectCodeGroupMap);
				}else{
					to.setSubjectId(subjectId);
					
					if(!classId.isEmpty() && (activityId>0 || Integer.parseInt(subjectId)>0)){
						
						/*String queryString="select t from TTPeriodWeek t join t.ttSubjectBatchs ts join ts.ttUsers tu where t.isActive=1 and ts.isActive=1 and t.ttClasses.isActive=1 and tu.isActive=1 and tu.users.isActive=1 and tu.users.id in (" +teachers+")"+
						" and t.ttClasses.isApproved=1 " +(activityId!=0?
						" and ts.activity.id= " +activityId:"")+
						" and t.ttClasses.classSchemewise.id in ("+classId+") and ts.attendanceType.id="+attendanceTypeId +(batchId!=0?" and ts.batch.id="+batchId:"")+" and t.weekDay='"+day+"' ";
						if(!subjectId.isEmpty()){
							queryString = queryString+ (Integer.parseInt(subjectId)!=0? " and ts.subject.id="+Integer.parseInt(subjectId):"");
						}
						queryString = queryString +"group by t.period.periodName order by t.period.periodName";
						List<TTPeriodWeek> periodList=session.createQuery(queryString).list();
						*/
						StringBuffer queryString=new StringBuffer("select t from TTPeriodWeek t join t.ttSubjectBatchs ts join ts.ttUsers tu where t.isActive=1 and ts.isActive=1 and t.ttClasses.isActive=1 and tu.isActive=1 and tu.users.isActive=1 and tu.users.id in (" +teachers+")"+
								" and t.ttClasses.isApproved=1 " +(activityId!=0?
								" and ts.activity.id= " +activityId:"")+
								" and t.ttClasses.classSchemewise.id in ("+classId+") " +(batchId!=0?" and ts.batch.id="+batchId:"")+" and t.weekDay='"+day+"' ");
								if(!subjectId.isEmpty()){
									queryString.append((Integer.parseInt(subjectId)!=0? " and ts.subject.id="+Integer.parseInt(subjectId):""));
								}
								queryString.append("group by t.period.periodName order by t.period.periodName");
								List<TTPeriodWeek> periodList=session.createQuery(queryString.toString()).list();
								
						/*List<TTPeriodWeek> periodList=session.createQuery("select t from TTPeriodWeek t join t.ttSubjectBatchs ts join ts.ttUsers tu where t.isActive=1 and ts.isActive=1 and t.ttClasses.isActive=1 and tu.isActive=1 and tu.users.isActive=1 and tu.users.id in (" +teachers+")"+
								" and t.ttClasses.isApproved=1 " 
								+( Integer.parseInt(subjectId)!=0? " and ts.subject.id="+Integer.parseInt(subjectId):"") 
								+(activityId!=0? 
								" and ts.activity.id= " +activityId:"")+
								" and t.ttClasses.classSchemewise.id in ("+classId+") and ts.attendanceType.id="+attendanceTypeId +(batchId!=0?" and ts.batch.id="+batchId:"")+" and t.weekDay='"+day+"' group by t.period.periodName order by t.period.periodName ").list();*/
						if(periodList!=null && !periodList.isEmpty()){
							Iterator<TTPeriodWeek> periodItr=periodList.iterator();
							while (periodItr.hasNext()) {
								TTPeriodWeek bo=periodItr .next();
								if(!periods.contains(String.valueOf(bo.getPeriod().getId()))){
									additionalPeriods.put(bo.getPeriod().getId(), bo.getPeriod().getPeriodName());
								}
							}
						}
					}
					to.setAdditionalPeriods(additionalPeriods);
				}
				//end
				//session.flush();
				periodMap = (HashMap<String, String>) CommonUtil.sortMapByValue(periodMap);
				to.setSubjectMap(periodMap);
				to.setActivityId(activityId);
				if(!batchIdList.isEmpty() && batchIdList.size()>1){
					to.setBatchList(batchIdList);
				}else{
					to.setBatchId(batchId) ;
				}
			}
			to.setError(isError);
			to.setAttendanceTypeId(attendanceTypeId);
			to.setClassId(classId);
			
			
			return to;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			throw e;
		}

		//return to;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getBatchesByPeriodsAndTeachers(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<Integer, String> getBatchesByPeriodsAndTeachers( String teacherId, String year, String periods, String day,String date) throws Exception {
		try {
			Session session = HibernateUtil.getSession();
			Query query = session .createQuery( "select t from TTUsers t where t.ttSubjectBatch.ttPeriodWeek.ttClasses.isApproved=1 and t.users.id in ("+teacherId+") and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.academicYear = :year and t.ttSubjectBatch.isActive=1 and t.isActive=1 and t.ttSubjectBatch.ttPeriodWeek.period.periodName in ( select p.periodName from Period p where p.isActive=1 and p.id in ("+periods+") ) and t.ttSubjectBatch.ttPeriodWeek.weekDay='"+day+"' " +
					" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.startDate and t.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.curriculumSchemeDuration.endDate" +		
			" group by t.ttSubjectBatch.subject.id");
			query.setInteger("year", Integer.parseInt(year));
			List<TTUsers> classList = query.list();
			Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();
			Iterator<TTUsers> itr = classList.iterator();
			TTUsers ttUsers;
			while (itr.hasNext()) {
				ttUsers = itr.next();
				if(ttUsers.getTtSubjectBatch().getBatch()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive()!=null && ttUsers.getTtSubjectBatch().getBatch().getIsActive())
					if(ttUsers.getTtSubjectBatch().getBatch().getSubject()!=null && ttUsers.getTtSubjectBatch().getBatch().getSubject().getCode()!=null 
							&& !ttUsers.getTtSubjectBatch().getBatch().getSubject().getCode().isEmpty()){
						periodMap.put(ttUsers.getTtSubjectBatch().getBatch().getId(), ttUsers.getTtSubjectBatch().getBatch().getBatchName()+"("+ttUsers.getTtSubjectBatch().getBatch().getSubject().getCode()+")");
					}else{
						periodMap.put(ttUsers.getTtSubjectBatch().getBatch().getId(), ttUsers.getTtSubjectBatch().getBatch().getBatchName());
					}
			}
			session.flush();
			periodMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(periodMap);
			return periodMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getSubCategoryMap(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getSubCategoryMap(String categoryId)
			throws Exception {
		try {
			Session session = HibernateUtil.getSession();
			Query query = session .createQuery("from InvSubCategoryBo b where b.isActive=1 and b.invItemCategory.id=:itemCategory");
			query.setInteger("itemCategory", Integer.parseInt(categoryId));
			List<InvSubCategoryBo> subCategoryList = query.list();
			Map<Integer, String> subCategoryMap = new LinkedHashMap<Integer, String>();
			Iterator<InvSubCategoryBo> itr = subCategoryList.iterator();
			while (itr.hasNext()) {
				InvSubCategoryBo bo = itr.next();
				if(bo.getSubCategoryName()!=null)
					subCategoryMap.put(bo.getId(), bo.getSubCategoryName());
			}
			session.flush();
			subCategoryMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subCategoryMap);
			return subCategoryMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getSpecializationBySubGrpWithoutCommSubjAndSecndLang(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getSpecializationBySubGrpWithoutCommSubjAndSecndLang(
			String subjectGroupId) throws Exception {
		Map<Integer, String> specializationMap = new LinkedHashMap<Integer, String>();
		try{
			Session session = HibernateUtil.getSession();
			Query query = session.createQuery("from SubjectGroup grp where grp.id=:subjectGroupId and grp.isCommonSubGrp = 0 and grp.secondLanguageId = null");
			query.setInteger("subjectGroupId", Integer.parseInt(subjectGroupId));
			SubjectGroup group = (SubjectGroup) query.uniqueResult();
			if(group!=null){
				if(group.getCourse()!=null){
					Query query1 = session.createQuery("from ExamSpecializationBO exam where exam.courseUtilBO.courseID=:courseId and exam.isActive=1");
					query1.setInteger("courseId", group.getCourse().getId());
					List<ExamSpecializationBO> examSpecializationBO = query1.list();
					if(examSpecializationBO!=null){
						Iterator<ExamSpecializationBO> iterator = examSpecializationBO.iterator();
						while (iterator.hasNext()) {
							ExamSpecializationBO bo = (ExamSpecializationBO) iterator .next();
							if(bo.getName()!=null){
								specializationMap.put(bo.getId(), bo.getName());
							}
						}
					}
				}
				
			}
		}catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return specializationMap;
	}
	@Override
	public Map<Integer, String> getInvLocationMap(String campusId)
			throws Exception {
		try {
			Session session = HibernateUtil.getSession();
			Query query = session .createQuery("from InvLocation b where b.isActive=1 and b.invCampusId.id=:campus");
			query.setInteger("campus", Integer.parseInt(campusId));
			List<InvLocation> locationList = query.list();
			Map<Integer, String> invLocationMap = new LinkedHashMap<Integer, String>();
			Iterator<InvLocation> itr = locationList.iterator();
			while (itr.hasNext()) {
				InvLocation bo = itr.next();
				if(bo.getName()!=null)
					invLocationMap.put(bo.getId(), bo.getName());
			}
			session.flush();
			invLocationMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(invLocationMap);
			return invLocationMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getName(java.lang.String)
	 */
	@Override
	public String getName(String query) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		String name = "";
		try{
			session = HibernateUtil.getSession();
			Query querys = session.createQuery(query);
			name = querys.uniqueResult().toString();
			session.close();
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
		}
		return name;
	}

	@Override
	public boolean checkReceivedThrough(String query,HttpServletRequest request) throws Exception {
		Session session = null;
		boolean flag = false;
		try{
			session = HibernateUtil.getSession();
			Query querys = session.createQuery(query);
			ReceivedThrough receive =(ReceivedThrough)querys.uniqueResult();
			if(receive!=null){
				flag = true;
				if(receive.getSlipRequired())
					request.setAttribute("slip", receive.getSlipRequired());
				else
					request.setAttribute("slip", false);
			}else{
				request.setAttribute("slip", true);
			}
			session.close();
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
		}
		return flag;
	}
	
	
	public Map<Integer, String> getCourseByProgram1(Set<Integer> ids) {
		Session session = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session
					.createQuery("from Program c where c.isActive =1 and c.id in (:ids)");
			 			query.setParameterList("ids", ids);
			Program program = new Program();
			if (!query.list().isEmpty())
				program = (Program) query.list().get(0);
			Map<Integer, String> courseMap = new HashMap<Integer, String>();
			Iterator<Course> itr = program.getCourses().iterator();
			Course course;
			while (itr.hasNext()) {
				course = (Course) itr.next();
				if (course.getIsActive())
					courseMap.put(course.getId(), course.getName());
			}
			courseMap=CommonUtil.sortMapByValue(courseMap);
			
			// session.close();
			
			return courseMap;
		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	
	
	public Map<Integer, String> getCourseByProgram(Set<Integer> ids) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
			.createQuery("from Program c where c.isActive =1 and c.id in (:ids)");
	 			query.setParameterList("ids", ids);
			Map<Integer, String> courseMap = new HashMap<Integer, String>();
			Iterator<Program> periodIterator = query.iterate();
			while (periodIterator.hasNext()) {
				Program ps = periodIterator.next();
				Iterator<Course> itr = ps.getCourses().iterator();
				Course course;
				while (itr.hasNext()) {
					course = (Course) itr.next();
					if (course.getIsActive())
				courseMap.put(course.getId(), course.getName());
			}
			}
			courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
			return courseMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			return new HashMap<Integer, String>();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	@Override
	public Map<Integer, String> getSubjectByYear(String year) throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" select sub.subject from SubjectGroupSubjects sub " +
					                          " inner join sub.subjectGroup.curriculumSchemeSubjects cur "+
					                          " inner join cur.curriculumSchemeDuration cd "+
					                          " where sub.isActive=1 and sub.subjectGroup.isActive=1 and sub.subject.isActive=1"+
					                          " and cd.academicYear=:academicYear group by sub.subject.id,cd.academicYear order by sub.subject.code")
										        .setString("academicYear", year);
			List<Subject> subjectList=query.list();
			Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
			Iterator<Subject> itr = subjectList.iterator();
			Subject subject;

			while (itr.hasNext()) {
				subject = (Subject) itr.next();
				subjectMap.put(subject.getId(), subject.getCode()+'-'+subject.getName());
			}
			subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
			return subjectMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

	@Override
	public Map<Integer, String> getExamByYear(String year) throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from ExamDefinition exam " +
					                          " where exam.isActive=1 "+
					                          " and exam.academicYear=:academicYear group by exam.id,exam.academicYear order by exam.id")
										        .setString("academicYear", year);
			List<ExamDefinition> examList=query.list();
			Map<Integer, String> examMap = new LinkedHashMap<Integer, String>();
			Iterator<ExamDefinition> itr = examList.iterator();
			ExamDefinition exam;

			while (itr.hasNext()) {
				exam = (ExamDefinition) itr.next();
				examMap.put(exam.getId(), exam.getName());
			}
			examMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(examMap);
			return examMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getCourseByApplnNoYear(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<Integer, String> getCourseByApplnNoYear(String appNo, String year) throws Exception {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			String que=" from ApplicationNumber a where a.isActive=1 and a.year=" +Integer.valueOf(year)+
					" and ((" +appNo+
					" between a.onlineApplnNoFrom and a.onlineApplnNoTo) or ( " +appNo+
					" between a.offlineApplnNoFrom and a.offlineApplnNoTo))";
			Query query = session.createQuery(que);
			List<ApplicationNumber> appNoList=query.list();
			Map<Integer, String> coursesMap = new LinkedHashMap<Integer, String>();
			Iterator<ApplicationNumber> itr = appNoList.iterator();
			ApplicationNumber applicationNumber;
			while (itr.hasNext()) {
				applicationNumber = (ApplicationNumber) itr.next();
				if(applicationNumber.getCourseApplicationNumbers()!=null && !applicationNumber.getCourseApplicationNumbers().isEmpty()){
					for (CourseApplicationNumber courseAppNo: applicationNumber.getCourseApplicationNumbers()) {
						if(courseAppNo.getCourse()!=null)
						coursesMap.put(courseAppNo.getCourse().getId(), courseAppNo.getCourse().getName());
					}
				}
			}
			coursesMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(coursesMap);
			return coursesMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByYear(int year, int teacherId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TeacherClassSubject t "+
						"where t.teacherId.id="+teacherId+" and t.year="+year+" and t.isActive=1");
					
			        
			List<TeacherClassSubject> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			if(classList!=null && !classList.isEmpty())
			{
				Iterator<TeacherClassSubject> itr = classList.iterator();
				TeacherClassSubject teacherclass;

				while (itr.hasNext()) {
					teacherclass = (TeacherClassSubject) itr.next();
				classMap.put(teacherclass.getClassId().getClasses().getId(), teacherclass.getClassId().getClasses().getName());
				}
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	
	public Map<Integer, String> getClassByYearUserId(int year, int teacherId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TeacherClass t " +
					" where t.classId.curriculumSchemeDuration.academicYear="+year+" and t.isActive=1 and t.teacherId.id="+teacherId+" order by t.classId.classes.nameb");
			        
			List<TeacherClass> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			if(classList!=null && !classList.isEmpty())
			{
				Iterator<TeacherClass> itr = classList.iterator();
				TeacherClass teacherclass;

				while (itr.hasNext()) {
					teacherclass = (TeacherClass) itr.next();
				classMap.put(teacherclass.getClassId().getClasses().getId(), teacherclass.getClassId().getClasses().getName());
				}
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getinterViewTypybyCourseNew(java.util.List, int)
	 */
	@Override
	public Map<Integer, String> getinterViewTypybyCourseNew(
			List<Integer> courseList, int year) throws Exception {
		Session session = null;
		Map<Integer, String> interViewMap = new LinkedHashMap<Integer, String>();

		try {

			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewProgramCourse where course.id in(:courseId) and year= :year and isActive = true group by name order by sequence");
			query.setParameterList("courseId", courseList);
			query.setInteger("year", year);

			Iterator<InterviewProgramCourse> interviewProgramCourseIterator = query
					.iterate();

			while (interviewProgramCourseIterator.hasNext()) {
				InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) interviewProgramCourseIterator
						.next();
				interViewMap.put(interviewProgramCourse.getId(),
						interviewProgramCourse.getName());
			}
			interViewMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(interViewMap);

			return interViewMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}

		return interViewMap;
	}
	/**
	 * @param id
	 * @return interviewSubroundsMap
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getInterviewSubroundsByInterviewTypeNew(String interviewType, List<Integer> courseList, String year) {
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query hqlQuery = session
			.createQuery("select c.id from InterviewProgramCourse c where c.course.id in(:courseId) and c.year= :year and c.isActive = true order by c.sequence");
			hqlQuery.setParameterList("courseId", courseList);
			hqlQuery.setString("year", year);
			List<Integer> interviewTypeIds = hqlQuery.list();
			Map<Integer, String> interviewSubroundsMap = new LinkedHashMap<Integer, String>();
			if(interviewTypeIds != null){
				Query query = session.createQuery("from InterviewSubRounds i where i.interviewProgramCourse.id in(:interviewTypeId) and i.isActive = 1 group by i.name");
				query.setParameterList("interviewTypeId", interviewTypeIds);
				Iterator<InterviewSubRounds> interviewSubroundsIterator = query
				.iterate();
				
				while (interviewSubroundsIterator.hasNext()) {
					InterviewSubRounds interviewProgramCourse = (InterviewSubRounds) interviewSubroundsIterator
					.next();
					interviewSubroundsMap.put(interviewProgramCourse.getId(),
							interviewProgramCourse.getName());
				}
				interviewSubroundsMap=CommonUtil.sortMapByValue(interviewSubroundsMap);
			}
			return interviewSubroundsMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		} finally {
			// if (session != null)
			// session.close();
		}
		return new HashMap<Integer, String>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getFacultyByDepartmentWise(int)
	 */
	@Override
	public Map<Integer, String> getFacultyByDepartmentWise(int departmentId) throws Exception {
		Session session = null;
		Map<Integer, String> facultyMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String str="select u.id, e.firstName from Users u join u.employee e with (e.active=1 and e.isActive=1) where u.employee.id = e.id and u.isActive=1 and u.active =1 and u.isTeachingStaff=1 and u.userName is not null and e.department.id = '"+departmentId+"'";
			Query query = session.createQuery(str);
			List<Object[]> objects = query.list();
			if(objects!=null && !objects.isEmpty()){
				Iterator<Object[]> iterator = objects.iterator();
				while (iterator.hasNext()) {
					Object[] objects2 = (Object[]) iterator.next(); 
					if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
						facultyMap.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
					}
				}
			}
		}catch(Exception e){
			log.error("Exception" , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return facultyMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getGuestFacultyByDepartmentId(int)
	 */
	@Override
	public Map<Integer, String> getGuestFacultyByDepartmentId(int departmentId, Map<Integer, String> facultyMap) throws Exception {
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String str="select u.id, g.firstName from Users u join u.guest g with (g.active=1 and g.isActive=1) where u.guest.id = g.id and u.isActive=1 and u.active =1 and u.isTeachingStaff=1 and u.userName is not null and g.department.id = '"+departmentId+"'";
			Query query = session.createQuery(str);
			List<Object[]> objects = query.list();
			if(objects!=null && !objects.isEmpty()){
				Iterator<Object[]> iterator = objects.iterator();
				while (iterator.hasNext()) {
					Object[] objects2 = (Object[]) iterator.next(); 
					if(!facultyMap.containsKey(Integer.parseInt(objects2[0].toString()))){
					if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
						facultyMap.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
					}
					}
				}
			}
		}catch(Exception e){
			log.error("Exception" , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return facultyMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getUsersByDepartment(int)
	 */
	@Override
	public Map<Integer, String> getUsersByDepartment(int departmentId,Map<Integer, String> facultyMap) throws Exception {
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="select u.id, u.userName from Users u where u.isActive=1 and u.active =1 and u.isTeachingStaff=1 and u.userName is not null and u.department.id = '"+departmentId+"'";
			Query quer=session.createQuery(query);
			List<Object[]> objects=quer.list();
			if(objects!=null && !objects.isEmpty()){
				Iterator<Object[]> iterator = objects.iterator();
				while (iterator.hasNext()) {
					Object[] objects2 = (Object[]) iterator.next(); 
					if(!facultyMap.containsKey(Integer.parseInt(objects2[0].toString()))){
					if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
						facultyMap.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
					}
					}
				}
			}
		}catch(Exception e){
			log.error("Exception" , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		facultyMap = (Map<Integer, String>) CommonUtil.sortMapByValue(facultyMap);
		return facultyMap;
	}
	 /* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getYearWiseDocuments(java.lang.String)
	 */
	public Map<Integer, String> getYearWiseDocuments(String year) throws Exception {
     Session session = null;
     Map<Integer, String> map = new HashMap<Integer, String>();
     try{
         session = HibernateUtil.getSession();
         Query query = session.createQuery((new StringBuilder("from ApplicationStatusUpdate a where a.admApplnNO.appliedYear=")).append(year).toString());
         List<ApplicationStatusUpdate> list = query.list();
         Iterator<ApplicationStatusUpdate> iterator = list.iterator();
         while (iterator.hasNext()) {
			ApplicationStatusUpdate applicationStatusUpdate = (ApplicationStatusUpdate) iterator.next();
			 if(applicationStatusUpdate.getAdmApplnNO() != null && applicationStatusUpdate.getAdmApplnNO().getApplnNo() != 0)
             {
                 map.put(Integer.valueOf(applicationStatusUpdate.getAdmApplnNO().getApplnNo()), applicationStatusUpdate.getApplicationStatus().getShortName());
             }
		}
     }catch(Exception e){
         log.debug((new StringBuilder("Exception")).append(e.getMessage()).toString());
     }
     return map;
 }
	public Map<Integer, String> getExamNameByYear(String academicYear) throws Exception {
		Session session = null;
		Map<Integer, String> map=new HashMap<Integer, String>();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamDefinitionBO e where e.delIsActive = 1 and e.isActive = 1 and e.academicYear="+Integer.parseInt(academicYear));
			Iterator<ExamDefinitionBO> itr=query.list().iterator();
			while (itr.hasNext()) {
				ExamDefinitionBO exam= itr.next();
				map.put(exam.getId(),exam.getName());
			}
			return map;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}
	@Override
	public Map<Integer, String> getClassesForProgram(String programTypeId,String year)
			throws Exception {
	     Session session = null;
	     Map<Integer, String> map = new HashMap<Integer, String>();
	     try{
	         session = HibernateUtil.getSession();
	         Query query = session.createQuery((new StringBuilder("select cs.classes from ClassSchemewise cs where cs.classes.isActive=1 " +
	         														" and cs.classes.course.isActive=1 and cs.classes.course.program.isActive=1 " +
	         														" and cs.curriculumSchemeDuration.academicYear=" +year+
	         														" and cs.classes.course.program.programType.id=")).append(programTypeId).toString());
	         List<Classes> list = query.list();
	         Iterator<Classes> iterator = list.iterator();
	         while (iterator.hasNext()) {
	        	 Classes classes = (Classes) iterator.next();
	        	 if(classes.getId() !=0 && classes.getName() != null){
	        		 map.put(classes.getId(), classes.getName());
	        	 }
			}
	       map =  CommonUtil.sortMapByValue(map);
	     }catch(Exception e){
	         log.debug((new StringBuilder("Exception")).append(e.getMessage()).toString());
	     }
	     return map;
	 }
	/**
	 * Get class Details based on Id from ClassSchemewise
	 */

	public List<ClassSchemewise> getDetailsOnClassSchemewiseIdNew(Set<Integer> ids) {
		Session session = null;
		List<ClassSchemewise> list;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session
					.createQuery("from ClassSchemewise c where c.classes.id in (:ids)");
			query.setParameterList("ids", ids);
			list = query.list();
			return list;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
			// session.close();
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return new ArrayList<ClassSchemewise>();
	}
	@Override
	public String getEmpanelmentNoByguideName(String name,HttpServletRequest request) throws Exception {
		Session session = null;
		String eno=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select c.empanelmentNo from PhdGuideDetailsBO c where c.isActive = 1 and c.id ='"+name+"'");
            eno=(String) query.uniqueResult();
			return eno;
		   } catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.close();
		}
		return eno;
	}
	@Override
	public Map<Integer, String> getSpecializationByClassId( Set<Integer> classesIdsSet) throws Exception {
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			/*Iterator<Integer> iterator = classesIdsSet.iterator();
			while (iterator.hasNext()) {
				Integer classId = (Integer) iterator.next();
				String str = "from ExamSpecializationBO exam where exam.courseUtilBO.courseID in (select cls.course.id from Classes cls where cls.id = "+classId+") and exam.isActive=1";
				Query query =  session.createQuery(str);
				List<ExamSpecializationBO> bos = query.list();
				if(bos!=null && !bos.isEmpty()){
					Iterator<ExamSpecializationBO> iterator1 = bos.iterator();
					while (iterator1.hasNext()) {
						ExamSpecializationBO examSpecializationBO = (ExamSpecializationBO) iterator1 .next();
						map.put(examSpecializationBO.getId(), examSpecializationBO.getName());
					}
				}
			}*/
			String str = "from ExamSpecializationBO exam where exam.courseUtilBO.courseID in (select cls.course.id from Classes cls where cls.id in (:classesIdsSet)) and exam.isActive=1 group by exam.name";
			Query query =  session.createQuery(str);
			query.setParameterList("classesIdsSet", classesIdsSet);
			List<ExamSpecializationBO> bos = query.list();
			if(bos!=null && !bos.isEmpty()){
				Iterator<ExamSpecializationBO> iterator1 = bos.iterator();
				while (iterator1.hasNext()) {
					ExamSpecializationBO examSpecializationBO = (ExamSpecializationBO) iterator1 .next();
					map.put(examSpecializationBO.getId(), examSpecializationBO.getName());
				}
			}
		}catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return map;
	}

	@Override
	public List<Object[]> getStudentNameInHostel(String regNo, String applNo,HttpServletRequest request) throws Exception {
	    Session session = null;
		List<Object[]> name = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			String applino = "select st.admAppln.personalData.firstName,st.admAppln.personalData.middleName,st.admAppln.personalData.lastName,st.admAppln.personalData.gender from Student st"
			                        +" where st.isActive=1" +
			                        " and st.isAdmitted=1" +
			                        " and (st.isHide=0 or st.isHide is null)" +
			                        " and st.admAppln.isCancelled=0";
			if(regNo!=null && !regNo.isEmpty() && StringUtils.isNumeric(regNo)){
				applino=applino+" and st.registerNo='"+regNo+"'";
			}if(applNo!=null && !applNo.isEmpty() && StringUtils.isNumeric(applNo)){
				applino=applino+" and st.admAppln.applnNo="+applNo;
			}
			Query query = session.createQuery(applino);
			name = query.list();
		} catch (Exception e) {
			throw new ApplicationException();
		} finally {
			if (session != null) {
				session.flush();
		 	                  }
		          } 
		  return name;
		   }
	
	@Override
	public Map<Integer, String> getRoomByRoomType(int roomTypeId)throws Exception {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlRoom h where h.isActive = 1 and h.hlRoomType.id = '"
							+ roomTypeId + "'");
			List<HlRoom> roomTypeList = query.list();
			Map<Integer, String> roomTypeMap = new HashMap<Integer, String>();
			Iterator<HlRoom> itr = roomTypeList.iterator();
			HlRoom hlRoom;

			while (itr.hasNext()) {
				hlRoom = (HlRoom) itr.next();
				roomTypeMap.put(hlRoom.getId(), hlRoom.getName());
			}
			session.flush();
			// session.close();
			roomTypeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomTypeMap);

			return roomTypeMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	
	@Override
	public BigDecimal getNumberOfSeatsAvailable(String hostel, String roomtype,String year, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
    Session session = null;
    BigDecimal totalseat =null;
    BigInteger reserved =null;
    BigDecimal available =null;
    String seatTotal="";
	try {
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = HibernateUtil.getSession();
		

		String  str=" select count(hl_admission.student_id) as seats" +
		" from hl_admission " +
		" where hl_admission.is_active=1" +
		" and hl_admission.is_cancelled=0 " +
		" and hl_admission.is_checked_in=1" +
		" and (hl_admission.is_checked_out is null or hl_admission.is_checked_out=0 )" +
	    " and hl_admission.roomtype_id="+roomtype+
	    " and hl_admission.hostel_id="+hostel+
	    " and hl_admission.academic_year="+year+
	    " group by hl_admission.hostel_id,hl_admission.roomtype_id";
		
		Query query = session.createSQLQuery(str);
		reserved = (BigInteger) query.uniqueResult();
		
		
		String  strt=" select hl_available_seats.num_of_available_seats" +
		" from hl_available_seats " +
		" where hl_available_seats.is_active=1" +
	    " and hl_available_seats.hl_roomtype_id="+roomtype+
	    " and hl_available_seats.hl_hostel_id="+hostel+
	    " and hl_available_seats.academic_year="+year;
		
		Query que = session.createSQLQuery(strt);
		seatTotal =(String) que.uniqueResult();
		
		
		if(seatTotal!=null && !seatTotal.isEmpty()){
			totalseat=new BigDecimal(seatTotal);
		}
		if(totalseat==null){
		String strs=" select sum(hl_room_type.no_of_occupants) as seats" +
		" from hl_hostel " +
		" inner join hl_room_type on hl_room_type.hl_hostel_id = hl_hostel.id" +
		" and hl_room_type.is_active=1" +
		" and hl_hostel.is_active=1" +
		" inner join hl_room on hl_room.hl_hostel_id = hl_hostel.id" +
		" and hl_room.hl_room_type_id = hl_room_type.id" +
		" and hl_room.is_active=1" +
	    " where  hl_room_type.id="+roomtype +
	    " and hl_hostel.id="+hostel+
	    " and hl_room_type.room_category='Student'"+
	    " group by hl_hostel.id,hl_room_type.id";
		
		Query queryy = session.createSQLQuery(strs);
		totalseat = (BigDecimal) queryy.uniqueResult();
		}
		if(reserved!=null){
		available=totalseat.subtract(new BigDecimal(reserved));
		}else{
			available=totalseat;
		}
		
	} catch (Exception e) {
		throw new ApplicationException();
	} finally {
		if (session != null) {
			session.flush();
	 	                  }
	          } 
	  return available;
	}
	@Override
	public Map<Integer, String> getCourseByProgramId( Set<Integer> programIdsSet) throws Exception {
		Session session = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		//map.put(0,"Select All");
		try{
			session = HibernateUtil.getSession();
			Iterator<Integer> iterator = programIdsSet.iterator();
			while (iterator.hasNext()) {
				Integer programId = (Integer) iterator.next();
				if(programId==0){
					String str = "from Course exam where exam.isActive=1 and exam.onlyForApplication=0";
					Query query =  session.createQuery(str);
					List<Course> bos = query.list();
					if(bos!=null && !bos.isEmpty()){
						Iterator<Course> iterator1 = bos.iterator();
						while (iterator1.hasNext()) {
							Course course = (Course) iterator1 .next();
							map.put(course.getId(), course.getName());
						}
					}
				}else{
				String str = "from Course exam where exam.program.id="+programId+"and exam.isActive=1 and exam.onlyForApplication=0";
				Query query =  session.createQuery(str);
				List<Course> bos = query.list();
				if(bos!=null && !bos.isEmpty()){
					Iterator<Course> iterator1 = bos.iterator();
					while (iterator1.hasNext()) {
						Course course = (Course) iterator1 .next();
						map.put(course.getId(), course.getName());
					}
				}
				}
			}
		}catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.flush();
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return map;
	}

	@Override
	public Map<Integer, String> getHostelBygender(String hostelGender)throws Exception {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlHostel hos where hos.isActive = 1 " +
							" and hos.gender='"+ hostelGender + "'");
			List<HlHostel> hostelList = query.list();
			Map<Integer, String> hostelMap = new HashMap<Integer, String>();
			Iterator<HlHostel> itr = hostelList.iterator();
			HlHostel hlHostel;

			while (itr.hasNext()) {
				hlHostel = (HlHostel) itr.next();
				hostelMap.put(hlHostel.getId(), hlHostel.getName());
			}
			session.flush();
			// session.close();
			hostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelMap);

			return hostelMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	@Override
	public Map<Integer, String> getRoomTypeByHostelBYstudent(int hostelId)throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlRoomType h where h.isActive = 1 and h.roomCategory='Student' and h.hlHostel.id = '"+ hostelId + "'");
			List<HlRoomType> roomTypeList = query.list();
			Map<Integer, String> roomTypeMap = new HashMap<Integer, String>();
			Iterator<HlRoomType> itr = roomTypeList.iterator();
			HlRoomType hlRoomType;

			while (itr.hasNext()) {
				hlRoomType = (HlRoomType) itr.next();
				roomTypeMap.put(hlRoomType.getId(), hlRoomType.getName());
			}
			session.flush();
			// session.close();
			roomTypeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomTypeMap);

			return roomTypeMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	@Override
	public Map<Integer, String> getHostel() throws Exception {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery(" from HlHostel hos where hos.isActive = 1 ");
			List<HlHostel> hostelList = query.list();
			Map<Integer, String> hostelMap = new HashMap<Integer, String>();
			Iterator<HlHostel> itr = hostelList.iterator();
			HlHostel hlHostel;

			while (itr.hasNext()) {
				hlHostel = (HlHostel) itr.next();
				hostelMap.put(hlHostel.getId(), hlHostel.getName());
			}
			session.flush();
			// session.close();
			hostelMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(hostelMap);

			return hostelMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getStudentNameClass(String regNo, String applNo, String academicYear, String hostelApplNo ,HttpServletRequest request) throws Exception {
	    Session session = null;
		List name = null;
		try {
			session = HibernateUtil.getSession();
			String applino="select hl.studentId.admAppln.personalData.firstName, "+ 
					 "hl.id, hl.roomId.id, hl.bedId.id, hl.roomId.hlBlock.id, hl.roomId.floorNo, "+
					 "hl.roomId.hlUnit.id,hl.hostelId.id, hl.applicationNo, "+
					 "hl.admittedDate, hl.biometricId, hl.checkInDate, hl.isCheckedIn, hl.roomTypeId.id, " +
					 " hl.studentId.admAppln.personalData.gender "+
			         "from HlAdmissionBo hl "+
			         "where hl.studentId.isActive=1 "+
			         "and hl.studentId.isAdmitted=1 "+
			         "and hl.studentId.isHide=0 "+
			         "and hl.studentId.admAppln.isCancelled=0";
			if(regNo!=null && !regNo.isEmpty() && StringUtils.isNumeric(regNo)){
				applino=applino+"and hl.studentId.registerNo='"+regNo+"'";
			}if(applNo!=null && !applNo.isEmpty() && StringUtils.isNumeric(applNo)){
				applino=applino+" and hl.studentId.admAppln.applnNo="+applNo;
			}if(academicYear!=null && !academicYear.isEmpty() && StringUtils.isNumeric(academicYear)){
				applino=applino+" and hl.academicYear="+academicYear;
			}if(hostelApplNo!=null && !hostelApplNo.isEmpty()){
				applino=applino+" hl.applicationNo="+hostelApplNo;
			}
			Query query = session.createQuery(applino);
			name = query.list();
		} catch (Exception e) {
			throw new ApplicationException();
		} finally {
			if (session != null) {
				session.flush();
		 	                  }
		          } 
		  return name;
		   }


	
	@SuppressWarnings("null")
	public Map<Integer, String> getRoomsAvailable(String hstlName,String RoomType,String academicYear,String block,String unit,String floor) throws Exception {
		try {
			StringBuffer query = new StringBuffer("from HlRoom r where r.isActive=1");
			if(hstlName!=null && !hstlName.isEmpty())
			if (hstlName != null && !StringUtils.isEmpty(hstlName.trim())) 
				query = query.append(" and r.hlHostel='"+ hstlName+"'");
			if (RoomType != null && !StringUtils.isEmpty(RoomType.trim()))
				query = query.append(" and r.hlRoomType='"+ RoomType+"'");
			if (block != null && !StringUtils.isEmpty(block.trim())) 
				query = query.append(" and r.hlBlock='"+ block+"'");
			if (unit != null && !StringUtils.isEmpty(unit.trim())) 
				query = query.append(" and r.hlUnit='"+ unit+"'");
		    if (floor != null && !StringUtils.isEmpty(floor.trim())) 
				query = query.append(" and r.floorNo='"+ floor+"'");
			
			Session session = null;
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query.toString());
					
				
			List<HlRoom> roomList = query1.list();
			Map<Integer, String> roomMap = new HashMap<Integer, String>();
			Iterator<HlRoom> itr = roomList.iterator();
			HlRoom room;
			int i;
			List<Integer> roomId=null;
			while (itr.hasNext()) {
				room = (HlRoom) itr.next();
				roomMap.put(room.getId(), room.getName());
				Map<Integer, String> beds=getBedsAvailable(room.getId(),Integer.parseInt(academicYear));
				if(beds.size()<=0){
					roomMap.remove(room.getId());
				}
			}
			session.flush();
			// session.close();
			roomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomMap);
			return roomMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	
	public Map<Integer, String> getBedsAvailable(int roomId,int academicYear) throws Exception
	{
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlBeds b where b.isActive=1 and  b.hlRoom.id= "+roomId+" and b.id not in (select a.bedId from HlAdmissionBo a where a.isCancelled=0 and a.isActive=1 and (a.checkOut is null or a.checkOut=0) and a.roomId="+roomId+" and a.academicYear="+academicYear+")");
			List<HlBeds> roomList = query.list();
			Map<Integer, String> bedMap = new HashMap<Integer, String>();
			Iterator<HlBeds> itr = roomList.iterator();
			HlBeds bed;

			while (itr.hasNext()) {
				bed = (HlBeds) itr.next();
				if(bed.getId() != 0 &&  bed.getBedNo() != null &&  !bed.getBedNo().trim().isEmpty())
					bedMap.put(bed.getId(), bed.getBedNo());
			}
			session.flush();
			// session.close();
			bedMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(bedMap);

			return bedMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
			}
	
	
	public Map<Integer, String> getHlAdmissionData(List<Integer> roomId) throws Exception
	{
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlBeds r where r.hlRoom.id="+roomId);
			List<HlBeds> roomList = query.list();
			Map<Integer, String> bedMap = new HashMap<Integer, String>();
			Iterator<HlBeds> itr = roomList.iterator();
			HlBeds bed;

			while (itr.hasNext()) {
				bed = (HlBeds) itr.next();
				bedMap.put(bed.getId(), bed.getBedNo());
			}
			session.flush();
			// session.close();
			bedMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(bedMap);

			return bedMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			}
		return new HashMap<Integer, String>();
	}
	
	public Map<Integer, String> getBedByRoom(int roomId) throws Exception
	{
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlBeds r where r.hlRoom.id="+roomId);
			List<HlBeds> roomList = query.list();
			Map<Integer, String> bedMap = new HashMap<Integer, String>();
			Iterator<HlBeds> itr = roomList.iterator();
			HlBeds bed;

			while (itr.hasNext()) {
				bed = (HlBeds) itr.next();
				bedMap.put(bed.getId(), bed.getBedNo());
			}
			session.flush();
			// session.close();
			bedMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(bedMap);

			return bedMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			}
		return new HashMap<Integer, String>();
	}

	@Override
	public Map<Integer, String> getRoomsByFloorsCheckIn(int hostelId,int floorNo, int roomTypeId, int roomAlreadyAllotted, int academicYear ) {
		try {
			StringBuffer query = new StringBuffer("from HlRoom r where r.isActive=1");
			if (hostelId >0) 
				query = query.append(" and r.hlHostel='"+ hostelId+"'");
			if (roomTypeId>0)
				query = query.append(" and r.hlRoomType='"+ roomTypeId+"'");
		    if (floorNo >0) 
				query = query.append(" and r.floorNo='"+ floorNo+"'");
			
			Session session = null;
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery(query.toString());
			List<HlRoom> roomList = query1.list();
			
			Query query2 = session.createQuery("from HlRoom h where h.isActive = 1 and  h.hlHostel.id='" + hostelId + "' and  " +
					"h.hlRoomType.id='" + roomTypeId + "' and " +
					"h.id='" + roomAlreadyAllotted + "' and " +
					"h.floorNo='" + floorNo + "' order by h.name");
			List<HlRoom> allottedRoom = query2.list();
			Map<Integer, String> roomMap = new HashMap<Integer, String>();
			Iterator<HlRoom> itr = roomList.iterator();
			HlRoom room;
			List<Integer> roomId=null;
			while (itr.hasNext()) {
				room = (HlRoom) itr.next();
				roomMap.put(room.getId(), room.getName());
				Map<Integer, String> beds=getBedsAvailable(room.getId(), academicYear);
				if(beds.size()<=0){
					roomMap.remove(room.getId());
				}
			}
			Iterator<HlRoom> itr1 = allottedRoom.iterator();
			HlRoom hlRoom;
			while (itr1.hasNext()) {
				hlRoom = (HlRoom) itr1.next();
				roomMap.put(Integer.valueOf(hlRoom.getId()), hlRoom.getName());

			}
			roomMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomMap);
			session.flush();
			return roomMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	public Map<Integer, String> getBedByRoomCheckIn(int roomId,int allottedbed, int academicYear) throws Exception
	{
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlBeds b where b.hlRoom.id= "+roomId+" and b.id not in (select a.bedId from HlAdmissionBo a where a.isCancelled=0 and a.isActive=1 and (a.checkOut is null or a.checkOut=0) and a.roomId="+roomId+"  and a.academicYear="+academicYear+")");
			List<HlBeds> bedList = query.list();
			Query query2 = session.createQuery("from HlBeds b where b.hlRoom.id= "+roomId+" and  b.id=" + allottedbed );
			List<HlBeds> allottedBed = query2.list();
			
			Map<Integer, String> bedMap = new HashMap<Integer, String>();
			Iterator<HlBeds> itr = bedList.iterator();
			HlBeds bed;

			while (itr.hasNext()) {
				bed = (HlBeds) itr.next();
				bedMap.put(bed.getId(), bed.getBedNo());
			}
			
			Iterator<HlBeds> itr1 = allottedBed.iterator();
			HlBeds bed1;
			while (itr1.hasNext()) {
				bed1 = (HlBeds) itr1.next();
				bedMap.put(bed1.getId(), bed1.getBedNo());
			}
			session.flush();
			// session.close();
			bedMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(bedMap);

			return bedMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getSupplementryExamNameByAcademicYear(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getSupplementryExamNameByAcademicYear( String academicYear) throws Exception {
		Session session = null;
		Map<Integer, String> suppliExamNameMap = new HashMap<Integer, String>();
		try{
			session = HibernateUtil.getSession();
			String HQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  in ('Supplementary','Special Supplementary','Regular & Supplementary') and e.delIsActive = 1 and e.isActive=1 and e.academicYear="+academicYear;
			Query query = session.createQuery(HQL_QUERY);
			List<ExamDefinitionBO> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<ExamDefinitionBO> iterator = list.iterator();
				while (iterator.hasNext()) {
					ExamDefinitionBO bo = (ExamDefinitionBO) iterator .next();
					suppliExamNameMap.put(bo.getId(),bo.getName());
				}
			}
		}catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return suppliExamNameMap;
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesBySemAndYear(int year,List<Integer> list)throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear and c.classes.termNumber in (:list) order by c.classes.name");
			query.setParameter("academicYear", year);
			query.setParameterList("list",list);
			List<ClassSchemewise> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<ClassSchemewise> itr = classList.iterator();
			ClassSchemewise classSchemewise;

			while (itr.hasNext()) {
				classSchemewise = (ClassSchemewise) itr.next();
				classMap.put(classSchemewise.getId(), classSchemewise
						.getClasses().getName());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

	@Override
	public List<Integer> getTermNumber() throws Exception {
		List<Integer> list=new ArrayList<Integer>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select distinct(c.termNumber) from Classes c");
			list = query.list();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return  list;
	}

	@Override
	public Map<Integer, String> getClassesByTeacherAndDate(int teacherId,
			String year, String day, String date) throws Exception {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(" select tcs "
							+ " from TeacherClassSubject tcs"
							
							+ " where tcs.teacherId ="+teacherId+" and tcs.year='"+year+"' and tcs.classId.classes.isActive=1 and tcs.subject.isActive=1"
							//raghu
							+" and tcs.isActive=1"
							+" and '"+CommonUtil.ConvertStringToSQLDate(date)+"' between tcs.classId.curriculumSchemeDuration.startDate and tcs.classId.curriculumSchemeDuration.endDate");
			Iterator<TeacherClassSubject> tuples = query.list().iterator();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			while (tuples.hasNext()) {
				TeacherClassSubject tcs = (TeacherClassSubject) tuples.next();
				classMap.put(tcs.getClassId().getId(), tcs.getClassId()
						.getClasses().getName());
			}
			session.flush();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	@Override
	public HlAdmissionBo getHlAdmissionBo(String academicyear,String regNo)
	throws Exception {
Session session = null;
List list=new ArrayList();
try {
	session = HibernateUtil.getSession();
	String str="from HlAdmissionBo h where h.isActive=1 and h.isCheckedIn=1 and (h.checkOut=0 or h.checkOut is null)and h.academicYear='"+academicyear +"' and h.studentId.registerNo='"+regNo+"'" ;
	Query query = session.createQuery(str);
	HlAdmissionBo hlAdmissionBo=(HlAdmissionBo)query.uniqueResult();
	session.flush();
	return hlAdmissionBo;
	
} catch (Exception e) {
	log.debug("Error during duplcation checking...", e);
	session.flush();
	//session.close();
	throw new ApplicationException(e);
}
}
	@SuppressWarnings("unchecked")
	public List<Student> checkRgNoDuplicate(String regNo) {
		List<Student> student=null;
		try { 
			Session session = null;
				session = HibernateUtil.getSession();
			Query query = session.createQuery("from Student s where s.registerNo=:registerNo");
			query.setParameter("registerNo", regNo);
			student = query.list();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return student;
	}
	public String getExamDateBySubject(String examId, String subjectId, HttpServletRequest request) throws Exception {
		Session session = null;
		Timestamp eno=null;
		java.util.Date date =null;
		String d = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select e.dateStarttime from ExamTimeTableBO e where e.subjectId="+subjectId+" and e.examExamCourseSchemeDetailsBO.examId="+examId+" and e.isActive = 1 group by e.subjectId");
            eno=(Timestamp) query.uniqueResult();
            d = String.valueOf(eno);
            /*SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date= format.parse(date1);*/
			return d.substring(0, 10);
		   } catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.close();
		}
		return d;
	}
	

	@Override
	public Map<Integer, String> getStates(int id) throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from State s where s.isActive = 1 order by s.name");
			List<State> stateList = query.list();
			Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
			if (stateList != null) {
				Iterator<State> itr = stateList.iterator();
				while (itr.hasNext()) {
					State state = itr.next();
					if (state != null) {
						stateMap.put(state.getId(), state.getName());
					}
				}
			}
			// session.close();
			stateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(stateMap);
			return stateMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new LinkedHashMap<Integer, String>();
	}

	@Override
	public String getAmountByCategory(int id) throws Exception {
		log.debug("impl: inside getFineCategory");
		String amount=null;
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select a.amount from FineCategoryBo a where a.isActive=1 and a.id="+id);
			amount=(String)query.uniqueResult();
			session.flush();
			return amount;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getEmployeeByprogramId(int)
	 */
	@Override
	public Map<Integer, String> getEmployeeByprogramId(int pid)	throws Exception {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Employee emp where emp.isActive = 1 and emp.department.id= :programId")
					.setInteger("programId", pid);
			List<Employee> empList = query.list();
			Map<Integer, String> employeeMap = new LinkedHashMap<Integer, String>();
			Iterator<Employee> itr = empList.iterator();
			Employee employee;

			while (itr.hasNext()) {
				employee = (Employee) itr.next();
				employeeMap.put(employee.getId(), employee.getFirstName());
			}
			session.flush();
			// session.close();
			employeeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(employeeMap);
			return employeeMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getInternalGuide(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getInternalGuide(String check) throws Exception {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Map<Integer, String> employeeMap = new LinkedHashMap<Integer, String>();
			if(check.equalsIgnoreCase("false")){
			Query query = session.createQuery("from PhdEmployee emp where emp.isActive = 1");
			List<PhdEmployee> empList = query.list();
			Iterator<PhdEmployee> itr = empList.iterator();
			PhdEmployee employee;

			while (itr.hasNext()) {
				employee = (PhdEmployee) itr.next();
				employeeMap.put(employee.getId(), employee.getName());
			}
			session.flush();
			}else{
				Query query = session.createQuery("from PhdInternalGuideBo emp where emp.isActive = 1");
				List<PhdInternalGuideBo> empList = query.list();
				Iterator<PhdInternalGuideBo> itr = empList.iterator();
				PhdInternalGuideBo employee;

				while (itr.hasNext()) {
					employee = (PhdInternalGuideBo) itr.next();
					employeeMap.put(employee.getEmployeeId().getId(), employee.getEmployeeId().getFirstName());
				}
				session.flush();
				}
			
			employeeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(employeeMap);
			return employeeMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getDepartments()
	 */
	public Map<Integer, String> getDepartments() throws Exception {
		Map<Integer, String> departmentMap = new HashMap<Integer, String>();
		Session session = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			String referredByHQ = "select id, name from Department where isActive = 1 order by name";
			Query referredByQuery = session.createQuery(referredByHQ);

			Iterator iterator = referredByQuery.iterate();
			while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();

				departmentMap.put((Integer) row[0], (String) row[1].toString());
			}
		} catch (Exception e) {
			log.error("Error while getting department...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		departmentMap = (Map<Integer, String>)CommonUtil.sortMapByValue(departmentMap);
		return departmentMap;
	}
	@Override
	public Map<Integer, String> getBlockByHostel(int hostelId) {

		Session session = null;
		List<HlBlocks> list = null;
		Map<Integer, String> blockMap = new LinkedHashMap();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String queryString = "from HlBlocks h where h.hlHostel.id=:HostelId and h.isActive=1";
			Query query = session.createQuery(queryString);
			query.setInteger("HostelId", Integer.valueOf(hostelId));
			list = query.list();
			if (list != null && !list.isEmpty()) {
				for (HlBlocks hlBlock : list) {
					blockMap.put(hlBlock.getId(), hlBlock.getName());
				}
			}
			// session.close();
			// sessionFactory.close();
		} catch (Exception e) {
			log.debug("Error during getRoomList data..." + e.getMessage());
		}
		return blockMap;

	}

	@Override
	public Map<Integer, String> getUnitByBlock(int blockId) throws Exception {

		Session session = null;
		List<HlUnits> list = null;
		Map<Integer, String> unitMap = new LinkedHashMap();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String queryString = "from HlUnits h where h.blocks.id=:blockId and h.isActive=1";
			Query query = session.createQuery(queryString);
			query.setInteger("blockId", Integer.valueOf(blockId));
			list = query.list();
			if (list != null && !list.isEmpty()) {
				for (HlUnits hlUnits : list) {
					unitMap.put(hlUnits.getId(), hlUnits.getName());
				}
			}
			// session.close();
			// sessionFactory.close();
		} catch (Exception e) {
			log.debug("Error during getRoomList data..." + e.getMessage());
		}
		return unitMap;

	}

	@Override
	public List<Integer> checkDupilcateOfStaffId(String staffId) throws Exception {
		List<Integer> list=null;
		try { 
			Session session = null;
				session = HibernateUtil.getSession();
			Query query = session.createQuery("select s.id from GuestFaculty s where s.staffId=:staffId");
			query.setParameter("staffId", staffId);
			 list = query.list();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return list;
	}

	@Override
	public Map<Integer, String> getBlockByLocation(int locationId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from BlockBo c where c.isActive = 1 and c.locationId.id = :locationId order by c.locationId.name")
					.setInteger("locationId", locationId);
			List<BlockBo> blockList = query.list();
			Map<Integer, String> blockMap = new LinkedHashMap<Integer, String>();
			Iterator<BlockBo> itr = blockList.iterator();
			BlockBo blockBo;

			while (itr.hasNext()) {
				blockBo = (BlockBo) itr.next();
				blockMap.put(blockBo.getId(), blockBo.getBlockName());
			}
			session.flush();
			// session.close();
			blockMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(blockMap);
			return blockMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getvenueByWorkLocation(int)
	 */
	public Map<Integer, String> getvenueByWorkLocation(int workLocationId) {

		Session session = null;
		List<SapVenue> list = null;
		Map<Integer, String> venueMap = new LinkedHashMap();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String queryString = "from SapVenue v where v.workLocationId.id=:WorkLocationId and v.isActive=1";
			Query query = session.createQuery(queryString);
			query.setInteger("WorkLocationId", Integer.valueOf(workLocationId));
			list = query.list();
			if (list != null && !list.isEmpty()) {
				for (SapVenue venue : list) {
					venueMap.put(venue.getId(), venue.getVenueName());
				}
			}
			// session.close();
			// sessionFactory.close();
		} catch (Exception e) {
			log.debug("Error during getRoomList data..." + e.getMessage());
		}
		return venueMap;

	}

	@Override
	public Map<Integer, String> getCoursebyHostel(int hostelId)	throws Exception {

		Session session = null;
		List<Course> list = null;
		Map<Integer, String> blockMap = new LinkedHashMap();
		try {
			session = HibernateUtil.getSession();
			String queryString = " select co from HlAdmissionBo ha  join ha.studentId stu join stu.admAppln adm join adm.courseBySelectedCourseId co where ha.hostelId.id=:HostelId " +
					             " and ha.isActive=1 and co.isActive=1 group by co.id order by co.code";
			Query query = session.createQuery(queryString);
			query.setInteger("HostelId", Integer.valueOf(hostelId));
			list = query.list();
			if (list != null && !list.isEmpty()) {
				for (Course course : list) {
					blockMap.put(course.getId(), course.getName());
				}
			}
		} catch (Exception e) {
			log.debug("Error during getRoomList data..." + e.getMessage());
		}
		return blockMap;

	}
	
	@Override
	public Map<Integer, String> getClassByHostel(int hostelId) throws Exception {

		Session session = null;
		List<Classes> list = null;
		Map<Integer, String> blockMap = new LinkedHashMap();
		try {
			session = HibernateUtil.getSession();
			String queryString = " select cs from HlAdmissionBo ha join ha.studentId stu join stu.classSchemewise cls join cls.classes cs " +
					             " where ha.hostelId.id=:HostelId " +
					             " and ha.isActive=1 and cs.isActive=1 group by cs.id order by cs.name";
			Query query = session.createQuery(queryString);
			query.setInteger("HostelId", Integer.valueOf(hostelId));
			list = query.list();
			if (list != null && !list.isEmpty()) {
				for (Classes classes : list) {
					blockMap.put(classes.getId(), classes.getName());
				}
			}
		} catch (Exception e) {
			log.debug("Error during getRoomList data..." + e.getMessage());
		}
		return blockMap;

	}
	public Map<Integer, String> getFloorsByUnit(int unitId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlUnits h where h.isActive = 1 and  h.id = "+ unitId);
			List<HlUnits> hostelList = query.list();
			Map<Integer, String> floorMap = new HashMap<Integer, String>();
			Iterator<HlUnits> itr = hostelList.iterator();
			HlUnits hlHostel;
			int floorNo = 0;
			while (itr.hasNext()) {
				hlHostel = (HlUnits) itr.next();
				floorNo = hlHostel.getFloorNo();
			}
			for (int i = 1; i <= floorNo; i++) {
				floorMap.put(i, Integer.toString(i));
			}
			session.flush();
			// session.close();
			floorMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(floorMap);

			return floorMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	
	public Map<Integer, String> getProgramBydeanaryNameAndExam(String deanaryName, int examId) throws Exception {
		Session session = null;
		Map<Integer, String> map=new HashMap<Integer, String>();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String HQL_QUERY = null;
			HQL_QUERY="select distinct  program.id,program.name from EXAM_exam_course_scheme_details "+
						" inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id "+
						" inner join course ON EXAM_exam_course_scheme_details.course_id = course.id "+
						" inner join program ON course.program_id = program.id "+
						" where EXAM_definition.id="+examId +" and program.stream='"+deanaryName+"'";
			Query query = session.createSQLQuery(HQL_QUERY);
			
			List<Object[]> objects = query.list();
			if(objects!=null && !objects.isEmpty()){
				Iterator<Object[]> iterator = objects.iterator();
				while (iterator.hasNext()) {
					Object[] objects2 = (Object[]) iterator.next(); 
					if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
						map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
					}
				}
			}
			return map;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getClassesByProgramAndAcademicYear(int, java.lang.String)
	 */
	public Map<Integer, String> getClassesByProgramAndAcademicYear(int programId, String deaneryName) {
		
		List<Object[]> classList = null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
			
			String sql = "select classes.id,classes.name from classes " +
			"inner join class_schemewise on class_schemewise.class_id = classes.id " +
			"inner join course ON classes.course_id = course.id " +
			"inner join program ON course.program_id = program.id " +
			"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
			"where course.program_id="+programId+" and curriculum_scheme_duration.academic_year="+academicYear;
			
			if(deaneryName!=null && !deaneryName.isEmpty()){
				sql = sql + " and program.stream='"+deaneryName+"'";
			}
			
			Query queri = session.createSQLQuery(sql);
			classList = queri.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<Object[]> itr = classList.iterator();
			Object[] classes;
			while (itr.hasNext()) {
				classes = (Object[]) itr.next();
				classMap.put(Integer.parseInt(classes[0].toString()), classes[1].toString());
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getClassesByProgramTypeAndAcademicYear(int, java.lang.String)
	 */
	public Map<Integer, String> getClassesByProgramTypeAndAcademicYear(int programTypeId, String deaneryName) {
		
		List<Object[]> classList = null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
			
			String sql = "select classes.id,classes.name from classes " +
			"inner join class_schemewise on class_schemewise.class_id = classes.id " +
			"inner join course ON classes.course_id = course.id " +
			"inner join program ON course.program_id = program.id " +
			"inner join program_type ON program.program_type_id = program_type.id " +
			"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
			"where program.program_type_id="+programTypeId+" and curriculum_scheme_duration.academic_year="+academicYear;
			
			if(deaneryName!=null && !deaneryName.isEmpty()){
				sql = sql + " and program.stream='"+deaneryName+"'";
			}
			
			Query queri = session.createSQLQuery(sql);
			classList = queri.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<Object[]> itr = classList.iterator();
			Object[] classes;
			while (itr.hasNext()) {
				classes = (Object[]) itr.next();
				classMap.put(Integer.parseInt(classes[0].toString()), classes[1].toString());
			}
			session.flush();
			session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getClassesByCourseAndAcademicYear(int, java.lang.String)
	 */
	public Map<Integer, String> getClassesByCourseAndAcademicYear(int courseId, String deaneryName) {
		
		List<Object[]> classList = null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
			
			String sql = "select classes.id,classes.name from classes " +
			"inner join class_schemewise on class_schemewise.class_id = classes.id " +
			"inner join course ON classes.course_id = course.id " +
			"inner join program ON course.program_id = program.id " +
			"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
			"where classes.course_id="+courseId+" and curriculum_scheme_duration.academic_year="+academicYear;
			
			if(deaneryName!=null && !deaneryName.isEmpty()){
				sql = sql + " and program.stream='"+deaneryName+"'";
			}
			
			Query queri = session.createSQLQuery(sql);
			classList = queri.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<Object[]> itr = classList.iterator();
			Object[] classes;
			while (itr.hasNext()) {
				classes = (Object[]) itr.next();
				classMap.put(Integer.parseInt(classes[0].toString()), classes[1].toString());
			}
			session.flush();
			session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	@Override
	public boolean duplicateCheckingOfOrderNoByLocationId(String orderNo,
			String locationId) throws Exception {
		boolean flag=false;
        Session session = null;
        BlockBo block;
        try{
        	session = InitSessionFactory.getInstance().openSession();
            Query query = session.createQuery("from BlockBo b where b.isActive=1 and b.locationId="+Integer.parseInt(locationId)+" and b.blockOrder="+Integer.parseInt(orderNo));
            block = (BlockBo) query.uniqueResult();
            if(block!=null){
            	flag=true;
            }
         }catch(Exception e){
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        return flag;
    }
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.ajax.ICommonAjax#getClassesBySemesterAndAcademicYear(int, java.lang.String)
	 */
	public Map<Integer, String> getClassesBySemesterAndAcademicYear(int semester, String deaneryName, int programTypeId, int programId, int courseId) {
		
		List<Object[]> classList = null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
			
			String sql = "select classes.id,classes.name from classes " +
			"inner join class_schemewise on class_schemewise.class_id = classes.id " +
			"inner join course ON classes.course_id = course.id " +
			"inner join program ON course.program_id = program.id " +
			"inner join program_type ON program.program_type_id = program_type.id " +
			"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
			"where classes.term_number="+semester+" and curriculum_scheme_duration.academic_year="+academicYear;
			
			if(programTypeId > 0){
				sql = sql + " and program.program_type_id="+programTypeId;
			}
			if(programId > 0){
				sql = sql + " and course.program_id="+programId;
			}
			if(courseId > 0){
				sql = sql + " and classes.course_id="+courseId;
			}
			if(deaneryName!=null && !deaneryName.isEmpty()){
				sql = sql + " and program.stream='"+deaneryName+"'";
			}
			Query queri = session.createSQLQuery(sql);
			classList = queri.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<Object[]> itr = classList.iterator();
			Object[] classes;
			while (itr.hasNext()) {
				classes = (Object[]) itr.next();
				classMap.put(Integer.parseInt(classes[0].toString()), classes[1].toString());
			}
			session.flush();
			session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	@Override
	public Map<Integer, String> getDepartmentByStream(String streamId)
			throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Department d where d.isActive=1 and  d.employeeStreamBO.id="+Integer.parseInt(streamId));
			List<Department> list=query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Department> iterator=list.iterator();
				while (iterator.hasNext()) {
					Department department = (Department) iterator.next();
					map.put(department.getId(), department.getName());
				}
			}
			
			map = CommonUtil.sortMapByValue(map);
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}

	@Override
	public Map<Integer, String> getRoomNosByCampus(String locationId)
			throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from RoomMaster r where r.isActive=1 and r.blockId.locationId.id="+Integer.parseInt(locationId));
			List<RoomMaster> list=query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<RoomMaster> iterator=list.iterator();
				while (iterator.hasNext()) {
					RoomMaster roomMaster = (RoomMaster) iterator.next();
					map.put(roomMaster.getId(), roomMaster.getRoomNo());
				}
			}
			
			map = CommonUtil.sortMapByValue(map);
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}

	@Override
	public Map<Integer, String> getFacultyByCampus(String locationId)
			throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamInvigilatorAvailable e where e.workLocationId.id="+Integer.parseInt(locationId));
			List<ExamInvigilatorAvailable> list=query.list();
			StringBuilder stringBuilder=null;
			if(list!=null && !list.isEmpty()){
				Iterator<ExamInvigilatorAvailable> iterator=list.iterator();
				while (iterator.hasNext()) {
					stringBuilder=new StringBuilder();
					ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
					if(examInvigilatorAvailable.getTeacherId().getEmployee()!=null){
						if(examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName()!=null){
							stringBuilder.append(examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName());
						}
						if(examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName()!=null){
							stringBuilder.append(examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName());
						}
						map.put(examInvigilatorAvailable.getTeacherId().getId(), stringBuilder.toString());
						}
				}
			}
			
			map = CommonUtil.sortMapByValue(map);
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}

	@Override
	public List<ExamInviligatorDuties> checkDuplicateInvigilator(String string) throws Exception {
		List<ExamInviligatorDuties> list=null;
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery(string);
				list=query.list();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return list;
	}

	@Override
	public Map<Integer, String> facultyByCampusDeptAndDeanery(String string)
			throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery(string);
			List<ExamInvigilatorAvailable> list=query.list();
			StringBuilder stringBuilder=null;
			if(list!=null && !list.isEmpty()){
				Iterator<ExamInvigilatorAvailable> iterator=list.iterator();
				while (iterator.hasNext()) {
					stringBuilder=new StringBuilder();
					ExamInvigilatorAvailable examInvigilatorAvailable = (ExamInvigilatorAvailable) iterator.next();
					if(examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName()!=null){
						stringBuilder.append(examInvigilatorAvailable.getTeacherId().getEmployee().getFirstName());
					}
					if(examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName()!=null){
						stringBuilder.append(examInvigilatorAvailable.getTeacherId().getEmployee().getMiddleName());
					}
					map.put(examInvigilatorAvailable.getTeacherId().getId(), stringBuilder.toString());
				}
			}
			
			map = CommonUtil.sortMapByValue(map);
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}
	
	@Override
	public ExamInviligatorExemptionDatewise checkDuplicateExemption(
			String string) throws Exception {
		ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise=null;
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery(string);
			examInviligatorExemptionDatewise=(ExamInviligatorExemptionDatewise)query.uniqueResult();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return examInviligatorExemptionDatewise;
	}

	@Override
	public boolean checkIsFacultyInExamInvigilatorsAvailable(String locationId,
			String exam1Id, String userId) throws Exception {
		boolean flag=false;
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamInvigilatorAvailable e where e.isActive=0 and e.workLocationId.id="+Integer.parseInt(locationId)+
					" and e.teacherId.id="+Integer.parseInt(userId)+" and e.examId.id="+Integer.parseInt(exam1Id));
			List<ExamInvigilatorAvailable> list=query.list();
			if(list!=null && !list.isEmpty()){
				flag=true;
			}
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return flag;
	}
	
	public Map<Integer, String> getVenueBySelectionDateHl(String selectionScheduleId)throws Exception
	{
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select v from InterviewVenueSelection v " +
					"join v.interviewSelectionSchedule iss " +
					"where iss.id='"+selectionScheduleId+ "'" 
					+" and iss.selectionProcessDate> curDate() and iss.isActive=1 and v.isActive=1 " +
					"order by v");
			List<InterviewVenueSelection> list=query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<InterviewVenueSelection> iterator=list.iterator();
				while (iterator.hasNext()) {
					InterviewVenueSelection venue = (InterviewVenueSelection) iterator.next();
					venue.getInterviewSelectionSchedule().getId();
					map.put(venue.getId(), venue.getExamCenter().getCenter());
				}
			}
			
			map = CommonUtil.sortMapByValue(map);
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}
	
	
	
	/*public Map<Integer, String> getVenueBySelectionDateFirstPref(String selectionScheduleId)throws Exception
	{
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select v from InterviewVenueSelection v " +
					"join v.interviewSelectionSchedule iss " +
					"where iss.id='"+selectionScheduleId+ "'" 
					+" and iss.selectionProcessDate> curDate() and iss.isActive=1 and v.isActive=1 and v.examCenter.center like '%Bangalore%'" +
					"order by v");
			List<InterviewVenueSelection> list=query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<InterviewVenueSelection> iterator=list.iterator();
				while (iterator.hasNext()) {
					InterviewVenueSelection venue = (InterviewVenueSelection) iterator.next();
					venue.getInterviewSelectionSchedule().getId();
					map.put(venue.getId(), venue.getExamCenter().getCenter());
				}
			}
			
			map = CommonUtil.sortMapByValue(map);
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return map;
	}*/
	
	
		public Map<Integer, String> getTimeBySelectionDateHl(String venueId, String selectionScheduleId)throws Exception
		{
			Map<Integer, String> map = new HashMap<Integer, String>();
			try {
				Session session = null;
				session = HibernateUtil.getSession();
				Query query = session.createQuery("select v from InterviewTimeSelection v " +
						"join v.interviewSelectionSchedule iss " +
						"where iss.id='"+selectionScheduleId+ "'"
						+" and iss.selectionProcessDate> curDate() " +
						"order by v");
				List<InterviewTimeSelection> list=query.list();
				if(list!=null && !list.isEmpty()){
					Iterator<InterviewTimeSelection> iterator=list.iterator();
					while (iterator.hasNext()) {
						InterviewTimeSelection time = (InterviewTimeSelection) iterator.next();
						if(time.getId()>0){
							Query queryPrevious = session.createQuery("select count(adm.intTimeId)"+
															"from AdmInterviewSelectionSchedule adm "+
															"where adm.intSelectionId.id='"+selectionScheduleId+ "' and adm.intVenueId='"+venueId+ "' " +
															"and adm.intTimeId='"+time.getId()+"' and adm.isActive=1");
							Object obj= queryPrevious.uniqueResult();
							if(obj!=null){
								if(Integer.parseInt(obj.toString())<time.getMaxSeats())
								{
									map.put(time.getId(), time.getTime());
								}
							
						
							}else
							{
								map.put(time.getId(), time.getTime());
							}
						}
				}
			}
				map = CommonUtil.sortMapByValue(map);
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;
		}
		
		
		
		public Map<Integer, String> getDatesBySelectionVenueOnline(String selectionVenueId, String programId, String programYear)throws Exception
		{
			Map<Integer, String> map = new HashMap<Integer, String>();
			try {
				Session session = null;
				int i=0;
				session = HibernateUtil.getSession();
				Query query = session.createSQLQuery("select interview_selection_schedule.id, interview_selection_schedule.venue_id, "+
												"interview_selection_schedule.max_no_seats_online, interview_selection_schedule.max_no_seats_offline, "+
												"interview_selection_schedule.cut_off_date, date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y'), "+
												"count(adm_appln.id) as total_applied, exam_center.center "+
												"from interview_selection_schedule "+
												"left join adm_appln on adm_appln.interview_schedule_id = interview_selection_schedule.id  and adm_appln.mode='Online'"+
												"inner join exam_center on interview_selection_schedule.venue_id= exam_center.id "+
												"where interview_selection_schedule.program_id="+programId +" and interview_selection_schedule.academic_year="+programYear
												+" and interview_selection_schedule.venue_id="+selectionVenueId
												+" and interview_selection_schedule.cut_off_date>=curdate() and interview_selection_schedule.is_active=1 "+
												"group by interview_selection_schedule.id");
				List<Object[]> list=query.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> iterator=list.iterator();
					while (iterator.hasNext()) {
						Object[] obj = (Object[]) iterator.next();
						if(Integer.parseInt(obj[2].toString())> Integer.parseInt(obj[6].toString())){
							map.put(Integer.parseInt(obj[0].toString()),obj[5].toString());
						}
					}
				}
				map = CommonUtil.sortMapByValue(map);
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;
		}
		
		public Map<Integer, String> getDatesBySelectionVenueOffline(String selectionVenueId, String programId,String programYear)throws Exception
		{
			Map<Integer, String> map = new HashMap<Integer, String>();
			try {
				Session session = null;
				session = HibernateUtil.getSession();
				Query query = session.createSQLQuery("select interview_selection_schedule.id, interview_selection_schedule.venue_id, "+
												"interview_selection_schedule.max_no_seats_online, interview_selection_schedule.max_no_seats_offline, "+
												"interview_selection_schedule.cut_off_date, date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y'), "+
												"count(adm_appln.id) as total_applied, exam_center.center "+
												"from interview_selection_schedule "+
												"left join adm_appln on adm_appln.interview_schedule_id = interview_selection_schedule.id  and adm_appln.mode!='Online'"+
												"inner join exam_center on interview_selection_schedule.venue_id= exam_center.id "+
												"where interview_selection_schedule.program_id="+programId +" and interview_selection_schedule.academic_year="+programYear
												+" and interview_selection_schedule.venue_id="+selectionVenueId
												+" and interview_selection_schedule.cut_off_date>=curdate() and interview_selection_schedule.is_active=1"+
												" group by interview_selection_schedule.id");
				List<Object[]> list=query.list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> iterator=list.iterator();
					while (iterator.hasNext()) {
						Object[] obj = (Object[]) iterator.next();
						if(Integer.parseInt(obj[3].toString())> Integer.parseInt(obj[6].toString())){
							map.put(Integer.parseInt(obj[0].toString()), obj[5].toString());
						}
					}
				}
				map = CommonUtil.sortMapByValue(map);
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;
		}
		
		public Map<Integer, String> getDatesBySelectionVenueOnlineAppEdit(String selectionVenueId, String programId, String programYear, String applNo, String oldSeledctedVenue)throws Exception
		{
			Map<Integer, String> map = new HashMap<Integer, String>();
			try {
				Session session = null;
				int i=0;
				
				session = HibernateUtil.getSession();
				Query query = session.createSQLQuery("select interview_selection_schedule.id, interview_selection_schedule.venue_id, "+
												"interview_selection_schedule.max_no_seats_online, interview_selection_schedule.max_no_seats_offline, "+
												"interview_selection_schedule.cut_off_date, date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y'), "+
												"count(adm_appln.id) as total_applied, exam_center.center "+
												"from interview_selection_schedule "+
												"left join adm_appln on adm_appln.interview_schedule_id = interview_selection_schedule.id  and adm_appln.mode='Online'"+
												"inner join exam_center on interview_selection_schedule.venue_id= exam_center.id "+
												"where interview_selection_schedule.program_id="+programId +" and interview_selection_schedule.academic_year="+programYear
												+" and interview_selection_schedule.venue_id="+selectionVenueId
												+" and interview_selection_schedule.cut_off_date>=curdate() and interview_selection_schedule.is_active=1 "+
												"group by interview_selection_schedule.id");
				List<Object[]> list=query.list();
				
				Query query1=session.createQuery("select a.interScheduleSelection.id,a.interScheduleSelection.selectionProcessDate, a.examCenter.id, a.examCenter.center from AdmAppln a where a.applnNo="+ applNo);
				Object[] appRecord = (Object[]) query1.uniqueResult();
				
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> iterator=list.iterator();
					while (iterator.hasNext()) {
						Object[] obj = (Object[]) iterator.next();
						if(Integer.parseInt(obj[2].toString())> Integer.parseInt(obj[6].toString())){
							map.put(Integer.parseInt(obj[0].toString()),obj[5].toString());
						}
					}
				}
				if((Integer.parseInt(oldSeledctedVenue.trim()))==(Integer.parseInt(selectionVenueId.trim()))){
					if(appRecord!=null){
						if(appRecord[0].toString()!=null && appRecord[1].toString()!=null)
						map.put(Integer.parseInt(appRecord[0].toString()), CommonUtil.formatSqlDate(appRecord[1].toString()));
					}
				}
				map = CommonUtil.sortMapByValue(map);
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;
		}
		
		public Map<Integer, String> getDatesBySelectionVenueOfflineAppEdit(String selectionVenueId, String programId,String programYear, String applNo, String oldSeledctedVenue)throws Exception
		{
			Map<Integer, String> map = new HashMap<Integer, String>();
			try {
				Session session = null;
				session = HibernateUtil.getSession();
				Query query = session.createSQLQuery("select interview_selection_schedule.id, interview_selection_schedule.venue_id, "+
												"interview_selection_schedule.max_no_seats_online, interview_selection_schedule.max_no_seats_offline, "+
												"interview_selection_schedule.cut_off_date, date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y'), "+
												"count(adm_appln.id) as total_applied, exam_center.center "+
												"from interview_selection_schedule "+
												"left join adm_appln on adm_appln.interview_schedule_id = interview_selection_schedule.id  and adm_appln.mode!='Online'"+
												"inner join exam_center on interview_selection_schedule.venue_id= exam_center.id "+
												"where interview_selection_schedule.program_id="+programId +" and interview_selection_schedule.academic_year="+programYear
												+" and interview_selection_schedule.venue_id="+selectionVenueId
												+" and interview_selection_schedule.cut_off_date>=curdate() and interview_selection_schedule.is_active=1"+
												" group by interview_selection_schedule.id");
				List<Object[]> list=query.list();
				Query query1=session.createQuery("select a.interScheduleSelection.id,a.interScheduleSelection.selectionProcessDate, a.examCenter.id, a.examCenter.center from AdmAppln a where a.applnNo="+ applNo);
				Object[] appRecord = (Object[]) query1.uniqueResult();
				
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> iterator=list.iterator();
					while (iterator.hasNext()) {
						Object[] obj = (Object[]) iterator.next();
						if(Integer.parseInt(obj[3].toString())> Integer.parseInt(obj[6].toString())){
							map.put(Integer.parseInt(obj[0].toString()), obj[5].toString());
						}
					}
				}
				if((Integer.parseInt(oldSeledctedVenue.trim()))==(Integer.parseInt(selectionVenueId.trim()))){
					if(appRecord!=null){
						if(appRecord[0].toString()!=null && appRecord[1].toString()!=null)
							map.put(Integer.parseInt(appRecord[0].toString()), CommonUtil.formatSqlDate(appRecord[1].toString()));
					}
				}
				map = CommonUtil.sortMapByValue(map);
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.ajax.ICommonAjax#getPeriodByClassSchemewise(java.util.Set)
		 */
		public Map<Integer, String> getPeriodByClassSchemewise(
				Set<Integer> classSchemeId) {
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				Query query = session
						.createQuery(
								"from Period period where period.classSchemewise.id in (:classSchemeId)  and period.isActive  = true order by period.periodName")
						.setParameterList("classSchemeId", classSchemeId);

				Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();
				Map<String, String> Pmap = new HashMap<String, String>();

				Iterator<Period> periodIterator = query.list().iterator();
				while (periodIterator.hasNext()) {
					Period period = (Period) periodIterator.next();
					
					if(!Pmap.containsKey(period.getStartTime())){
						Pmap.put(period.getStartTime(), period.getPeriodName());
						if(period.getStartTime()!=null && !period.getStartTime().isEmpty()){
							periodMap.put(period.getId(), period.getPeriodName()+"["+period.getStartTime().substring(0,5)+"-"+period.getEndTime().substring(0,5)+"]");
						}
					}
				}
				periodMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(periodMap);
				return periodMap;
			} catch (Exception e) {
				return new HashMap<Integer, String>();
			} finally {
				if (session != null) {
					session.flush();
				}
			}
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.ajax.ICommonAjax#getCommonSubjectsByClass(java.util.Set)
		 */
		public Map<Integer, String> getCommonSubjectsByClass(Set<Integer> classesIdsSet) {
			//  This method is for getting common subjects only  by multiple classes code added by chandra
			Map<Integer, String> subjectMap = new HashMap<Integer, String>();
			Map<Integer, String> subjectMap1 = new HashMap<Integer, String>();
			Map<Integer, String> valueMap = new LinkedHashMap<Integer, String>();
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				
				Iterator<Integer> iterator = classesIdsSet.iterator();
	        	while (iterator.hasNext()) {
	        		Integer classId = (Integer) iterator.next();
				
				//if(classIDList!=null && !classIDList.isEmpty()){
				Query query= session.createQuery("select sub.id,sub.name,sub.code from CurriculumSchemeSubject curSchsub"+
																	" inner join curSchsub.curriculumSchemeDuration curSchDur "+
																	" inner join curSchDur.classSchemewises   clsSchW "+
																	" inner join clsSchW.classes cls "+
																	" inner join curSchsub.subjectGroup subGrp"+
																	" inner join subGrp.subjectGroupSubjectses subGrpSub"+
																	" join subGrpSub.subject sub "+
																	" where subGrp.isActive=1 and cls.isActive=1 and subGrpSub.isActive=1 and sub.isActive=1"+
																	" and (sub.isCertificateCourse=0 or sub.isCertificateCourse is null)"+
																	" and clsSchW.id in(:classIDs) group by sub.id");
				query.setInteger("classIDs", classId);
				List<Object[]> list = query.list();
	            if(list!=null && !list.isEmpty()){
	            	if(subjectMap!=null && !subjectMap.isEmpty()){
	            		Map<Integer, String> commonSubjectsMap=new HashMap<Integer, String>();
	            		Iterator<Object[]> iterator1 = list.iterator();
		            	while (iterator1.hasNext()) {
							Object[] objects = (Object[]) iterator1.next();
							if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
								if(subjectMap.containsKey(Integer.parseInt(objects[0].toString()))){
									commonSubjectsMap.put(Integer.parseInt(objects[0].toString()), (objects[1].toString()).toString()+"("+(objects[2].toString()).toString()+")");
								}
							}
						}
		            	if(commonSubjectsMap!=null && !commonSubjectsMap.isEmpty()){
		            		subjectMap.clear();
		            		subjectMap.putAll(commonSubjectsMap);
		            	}else{
		            		subjectMap1.clear();
		            	}
	            	}else{
		            	Iterator<Object[]> iterator1 = list.iterator();
		            	while (iterator1.hasNext()) {
							Object[] objects = (Object[]) iterator1.next();
							if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
								subjectMap.put(Integer.parseInt(objects[0].toString()), (objects[1].toString()).toString()+"("+(objects[2].toString()).toString()+")");
							}
						}
		            	subjectMap1.putAll(subjectMap);
	            	}
	            }
			}
	        	if(subjectMap1==null || subjectMap1.isEmpty()){
	        		subjectMap.clear();
	        	}
	            valueMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
				//}
				session.flush();
				session.close();
				
				return valueMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return new HashMap<Integer, String>();
		}

		@Override
		public Map<String, String> getYearMapByDept(int deptId,int year)throws Exception {
			Map<String, String> yearMap = new HashMap<String, String>();
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from SyllabusEntry s where s.isActive=1 and s.subject.department.id="+deptId);
				List<SyllabusEntry> syllabusEntries=query.list();
				for (SyllabusEntry syllabusEntry : syllabusEntries) {
					yearMap.put(String.valueOf(syllabusEntry.getBatchYear()), String.valueOf(syllabusEntry.getBatchYear()));
				}
				yearMap = (HashMap<String, String>) CommonUtil.sortMapByValue(yearMap);
				return yearMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}

			return new HashMap<String, String>();
		}

		@Override
		public Map<String, String> getsubjectMapByDeptAndYear(int deptId,
				int year) throws Exception {
			Map<String, String> yearMap = new HashMap<String, String>();
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from SyllabusEntry s where s.isActive=1 and s.subject.department.id="+deptId+" and s.batchYear="+year);
				List<SyllabusEntry> syllabusEntries=query.list();
				for (SyllabusEntry syllabusEntry : syllabusEntries) {
					yearMap.put(String.valueOf(syllabusEntry.getSubject().getId()), syllabusEntry.getSubject().getName()+"("+syllabusEntry.getSubject().getCode()+")");
				}
				yearMap = (HashMap<String, String>) CommonUtil.sortMapByValue(yearMap);
				return yearMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}

			return new HashMap<String, String>();
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.ajax.ICommonAjax#checkStudentApplication(java.lang.String, java.lang.String)
		 */
		@Override
		public String checkStudentApplication(String regNo, String applNo, String year) throws Exception {
		    Session session = null;
			String hostel_roomType = "";
			try {
				//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = HibernateUtil.getSession();
				StringBuffer query1 = new StringBuffer("from HostelOnlineApplication h where h.isActive=1 and h.isSelected=1 and h.academicYear="+year);
				if(regNo!=null && !regNo.isEmpty() && StringUtils.isNumeric(regNo))
					query1.append(" and h.student.registerNo='"+regNo+"'");
				if(applNo!=null && !applNo.isEmpty() && StringUtils.isNumeric(applNo))
					query1.append(" and h.student.admAppln.applnNo='"+applNo+"'");
				HostelOnlineApplication application = (HostelOnlineApplication)session.createQuery(query1.toString()).uniqueResult();
				if(application != null && application.getHlHostel() != null && application.getSelectedRoomType() != null && application.getApplicationNo() != null){
					hostel_roomType = application.getHlHostel().getId()+"%"+application.getSelectedRoomType().getId()+"%"+application.getApplicationNo();
				}
			} catch (Exception e) {
				throw new ApplicationException();
			} finally {
				if (session != null) {
					session.flush();
			 	}
			} 
			return hostel_roomType;
		}
		
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.ajax.ICommonAjax#getCourseByProgram(int)
		 */
		public Map<Integer, String> getExamCentersByProgramId(int id) {
			Session session = null;
			try {
				Map<Integer, String> examCenterMap = new HashMap<Integer, String>();
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session.createQuery(
						"from ExamCenter e where e.program.id = :programId and isActive =1 ")
						.setInteger("programId", id);
				
				List<ExamCenter>  examCenterList=  query.list();
				
				Iterator<ExamCenter> itr = examCenterList.iterator();
				while (itr.hasNext()) {
					ExamCenter bo= (ExamCenter) itr.next();
					if (bo.getIsActive())
						examCenterMap.put(bo.getId(),bo.getCenter());
				}
				examCenterMap=CommonUtil.sortMapByValue(examCenterMap);
				
				// session.close();
				
				return examCenterMap;
			} catch (Exception e) {
				// session.close();
				log.debug("Exception" + e.getMessage());
			}
			return new HashMap<Integer, String>();
		}
		

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.ajax.ICommonAjax#getCentersByProgram(int)
		 */
		public Map<Integer, String> getCentersByProgram(int id) {
			Session session = null;
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from Program where id = :programId and isActive =1 ").setInteger("programId", id);
				Program program = new Program();
				if (!query.list().isEmpty())
					program = (Program) query.list().get(0);
				Map<Integer, String> centerMap = new HashMap<Integer, String>();
				Iterator<ExamCenter> itr = program.getExamCenters().iterator();
				ExamCenter examCenter;
				while (itr.hasNext()) {
					examCenter = (ExamCenter) itr.next();
					if (examCenter.getIsActive())
						centerMap.put(examCenter.getId(), examCenter.getCenter());
				}
				centerMap=CommonUtil.sortMapByValue(centerMap);
				
				// session.close();
				return centerMap;
			} catch (Exception e) {
				// session.close();
				log.debug("Exception" + e.getMessage());
			}
			return new HashMap<Integer, String>();
		}
		
		
		

		public Map<Integer, String> getDistrictByState(int id) {
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session
						.createQuery("from District s where s.state.id = :countryId and s.isActive = 1 and s.state.isActive = 1 order by s.name");
				query.setInteger("countryId", id);
				List<District> stateList = query.list();
				Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
				if (stateList != null) {
					Iterator<District> itr = stateList.iterator();
					while (itr.hasNext()) {
						District state = itr.next();
						if (state != null) {
							stateMap.put(state.getId(), state.getName());
						}
					}
				}
				// session.close();
				stateMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(stateMap);
				return stateMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return new LinkedHashMap<Integer, String>();
		}
		
		
		
		//raghu
		/**
		 * 
		 * @param id
		 * @return courseMap <key,value>
		 */
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getSubReligion() {
			Session session = null;
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from ReligionSection r where  r.isActive = 1  order by r.name");
				
				List<ReligionSection> religionSectionList = query.list();
				Map<Integer, String> subreligionMap = new LinkedHashMap<Integer, String>();
				if (religionSectionList != null) {
					Iterator<ReligionSection> itr = religionSectionList.iterator();
					while (itr.hasNext()) {
						ReligionSection religionSection = itr.next();
						if (religionSection != null) {
							subreligionMap.put(religionSection.getId(),
									religionSection.getName());
						}
					}
				}
				// session.close();
				subreligionMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subreligionMap);
				return subreligionMap;
			} catch (Exception e) {
				// session.close();
				log.debug("Exception" + e.getMessage());
			}
			return new LinkedHashMap<Integer, String>();
		}

		
		
		
		/**
		 * 
		 * @param id
		 * @return courseMap <key,value>
		 */
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getSubCasteByReligion(int id) {
			Session session = null;
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session
						.createQuery("from Caste r where r.religion.id = :religionId and r.isActive = 1 and r.religion.isActive = 1 order by r.name");
				query.setInteger("religionId", id);
				List<Caste> religionSectionList = query.list();
				Map<Integer, String> subreligionMap = new LinkedHashMap<Integer, String>();
				if (religionSectionList != null) {
					Iterator<Caste> itr = religionSectionList.iterator();
					while (itr.hasNext()) {
						Caste religionSection = itr.next();
						if (religionSection != null) {
							subreligionMap.put(religionSection.getId(),
									religionSection.getName());
						}
					}
				}
				// session.close();
				subreligionMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subreligionMap);
				return subreligionMap;
			} catch (Exception e) {
				// session.close();
				log.debug("Exception" + e.getMessage());
			}
			return new LinkedHashMap<Integer, String>();
		}

		

		/**
		 * 
		 * @param id
		 * @return courseMap <key,value>
		 */
		@SuppressWarnings("unchecked")
		public Integer getAttendanceTypeIdBySubject(int id) {
			Session session = null;
			Integer pid=0;
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				//Query query = session.createQuery("select distinct(tt.attendanceType.id) from TTSubjectBatch tt where tt.isActive=1 and tt.subject.id="+id);
				//pid = (Integer)query.uniqueResult();
				//if(pid==null){
					Subject s=(Subject)session.get(Subject.class, id);
					String tp="";
					if(s.getIsTheoryPractical().equalsIgnoreCase("T") || s.getIsTheoryPractical().equalsIgnoreCase("B")){
						tp="theory";
					}else{
						tp="practical";
					}
					
					Query query = session.createQuery("select at.id from AttendanceType at where at.isActive=1 and at.name=:name");
					query.setString("name", tp);
					pid = (Integer)query.uniqueResult();
				
				//}
					
			} catch (Exception e) {
				// session.close();
				log.debug("Exception" + e.getMessage());
			}
			return pid;
		}
		
		// Vinodha for faculty evaluation report
		public Map<Integer, String> getClassesForFacultyEvaluationReport(BaseActionForm baseActionForm, int year) throws Exception
		{
	        log.debug("call of getClasses method in FacultyEvaluationReportImpl.class");
	        Session session = null;
			Map<Integer, String> map=new HashMap<Integer, String>();
	        try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String sql =("select distinct classes.id,classes.name" +
						" from eva_student_feedback_faculty " +
						" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
						" inner join classes ON eva_student_feedback.class_id = classes.id" +
						" inner join eva_student_feedback_session ON eva_student_feedback.session_id = eva_student_feedback_session.id" +
						" where eva_student_feedback_session.academic_year=:academicYear" +
						" and eva_student_feedback_faculty.teacher_id=:userId");
				Query query = session.createSQLQuery(sql);
				query.setInteger("academicYear", year);
				query.setString("userId", baseActionForm.getUserId());			
				List<Object[]> objects = query.list();
				if(objects!=null && !objects.isEmpty()){
					Iterator<Object[]> iterator = objects.iterator();
					while (iterator.hasNext()) {
						Object[] objects2 = (Object[]) iterator.next(); 
						if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
							map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
						}
					}
				}
				return map;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;		
		}
		
		// Vinodha for faculty evaluation report
		public Map<Integer, String> getSubjectsForFacultyEvaluationReport(BaseActionForm baseActionForm, int classId) throws Exception
		{
	        log.debug("call of getClasses method in FacultyEvaluationReportImpl.class");
	        Session session = null;
			Map<Integer, String> map=new HashMap<Integer, String>();
	        try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String sql =("select distinct eva_student_feedback_faculty.subject_id,subject.name" +
						" from eva_student_feedback_faculty " +
						" inner join subject ON eva_student_feedback_faculty.subject_id = subject.id" +
						" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
						" inner join classes ON eva_student_feedback.class_id = classes.id" +
						" where classes.id=:classId and eva_student_feedback_faculty.teacher_id=:userId" +
						" and  eva_student_feedback_faculty.subject_id in " +
						" (select teacher_class_subject.subject_id from teacher_class_subject where teacher_class_subject.teacher_id=:userId " +
						" and teacher_class_subject.is_active=1)");

				Query query = session.createSQLQuery(sql);
				query.setInteger("classId",classId);			
				query.setString("userId", baseActionForm.getUserId());			
				List<Object[]> objects = query.list();
				if(objects!=null && !objects.isEmpty()){
					Iterator<Object[]> iterator = objects.iterator();
					while (iterator.hasNext()) {
						Object[] objects2 = (Object[]) iterator.next(); 
						if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
							map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
						}
					}
				}
				return map;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;		
		}
		public Map<Integer, String> getTeachersForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception
		{
	        log.debug("call of getTeachersForFacultyEvaluationReportPrincipal method in FacultyEvaluationReportImpl.class");
	        Session session = null;
			Map<Integer, String> map=new HashMap<Integer, String>();
	        try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String sql =("select distinct eva_student_feedback_faculty.teacher_id,employee.first_name" +
						" from eva_student_feedback_faculty " +
						" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
						" inner join classes ON eva_student_feedback.class_id = classes.id" +
						" inner join eva_student_feedback_session ON eva_student_feedback.session_id = eva_student_feedback_session.id" +
						" inner join users ON eva_student_feedback_faculty.teacher_id = users.id" +
						" inner join employee ON users.employee_id = employee.id" +
						" where eva_student_feedback_session.academic_year=:year");
				Query query = session.createSQLQuery(sql);
				query.setString("year",baseActionForm.getAcademicYear());			
				List<Object[]> objects = query.list();
				if(objects!=null && !objects.isEmpty()){
					Iterator<Object[]> iterator = objects.iterator();
					while (iterator.hasNext()) {
						Object[] objects2 = (Object[]) iterator.next(); 
						if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
							map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
						}
					}
				}
				return map;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;		
		}
		public Map<Integer, String> getClassesForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception
		{
	        log.debug("call of getClasses method in FacultyEvaluationReportImpl.class");
	        Session session = null;
			Map<Integer, String> map=new HashMap<Integer, String>();
	        try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String sql =("select distinct classes.id,classes.name" +
						" from eva_student_feedback_faculty " +
						" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
						" inner join classes ON eva_student_feedback.class_id = classes.id" +
						" inner join eva_student_feedback_session ON eva_student_feedback.session_id = eva_student_feedback_session.id" +
						" where eva_student_feedback_session.academic_year=:academicYear" +
						" and eva_student_feedback_faculty.teacher_id=:userId");
				Query query = session.createSQLQuery(sql);
				query.setString("academicYear", baseActionForm.getAcademicYear());
				query.setString("userId", baseActionForm.getTeacher());			
				List<Object[]> objects = query.list();
				if(objects!=null && !objects.isEmpty()){
					Iterator<Object[]> iterator = objects.iterator();
					while (iterator.hasNext()) {
						Object[] objects2 = (Object[]) iterator.next(); 
						if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
							map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
						}
					}
				}
				return map;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;		
		}
		public Map<Integer, String> getSubjectsForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm, int classId) throws Exception
		{
	        log.debug("call of getClasses method in FacultyEvaluationReportImpl.class");
	        Session session = null;
			Map<Integer, String> map=new HashMap<Integer, String>();
	        try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String sql =("select distinct eva_student_feedback_faculty.subject_id,subject.name" +
						" from eva_student_feedback_faculty " +
						" inner join subject ON eva_student_feedback_faculty.subject_id = subject.id" +
						" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
						" inner join classes ON eva_student_feedback.class_id = classes.id" +
						" where classes.id=:classId and eva_student_feedback_faculty.teacher_id=:userId");
				Query query = session.createSQLQuery(sql);
				query.setInteger("classId",classId);			
				query.setString("userId", baseActionForm.getTeacher());			
				List<Object[]> objects = query.list();
				if(objects!=null && !objects.isEmpty()){
					Iterator<Object[]> iterator = objects.iterator();
					while (iterator.hasNext()) {
						Object[] objects2 = (Object[]) iterator.next(); 
						if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
							map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
						}
					}
				}
				return map;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;		
		}
		
		public Map<Integer, String> getClassesForEvaluateFacultyFeedback(BaseActionForm baseActionForm, int year) throws Exception
		{
	        log.debug("call of getClasses method in EvaluateFacultyFeedback.class");
	        Session session = null;
			Map<Integer, String> map=new HashMap<Integer, String>();
	        try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String sql =("select distinct classes.id,classes.name" +
						" from eva_student_feedback_faculty " +
						" inner join eva_student_feedback ON eva_student_feedback_faculty.eva_student_feedback_id = eva_student_feedback.id" +
						" inner join classes ON eva_student_feedback.class_id = classes.id" +
						" inner join eva_student_feedback_session ON eva_student_feedback.session_id = eva_student_feedback_session.id" +
						" where eva_student_feedback_session.academic_year=:academicYear");
				Query query = session.createSQLQuery(sql);
				query.setInteger("academicYear", year);
				List<Object[]> objects = query.list();
				if(objects!=null && !objects.isEmpty()){
					Iterator<Object[]> iterator = objects.iterator();
					while (iterator.hasNext()) {
						Object[] objects2 = (Object[]) iterator.next(); 
						if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
							map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
						}
					}
				}
				return map;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;		
		}
		public Map<Integer, String> getDepartmentsForFacultyEvaluationReportPrincipal(BaseActionForm baseActionForm) throws Exception
		{
	        log.debug("call of getTeachersForFacultyEvaluationReportPrincipal method in FacultyEvaluationReportImpl.class");
	        Session session = null;
			Map<Integer, String> map=new HashMap<Integer, String>();
	        try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String sql =("select distinct department.id, department.name from eva_student_feedback_overall_average" +
						" inner join eva_student_feedback_average ON eva_student_feedback_overall_average.eva_student_feedback_average_id = eva_student_feedback_average.id" +
						" inner join users ON eva_student_feedback_overall_average.teacher_id = users.id" +
						" inner join employee ON users.employee_id = employee.id" +
						" inner join department ON employee.department_id = department.id" +
						" where eva_student_feedback_average.year=:year");
				Query query = session.createSQLQuery(sql);
				query.setString("year",baseActionForm.getAcademicYear());			
				List<Object[]> objects = query.list();
				if(objects!=null && !objects.isEmpty()){
					Iterator<Object[]> iterator = objects.iterator();
					while (iterator.hasNext()) {
						Object[] objects2 = (Object[]) iterator.next(); 
						if(objects2[0]!=null && !objects2[0].toString().isEmpty() && objects2[1]!=null && !objects2[1].toString().isEmpty()){
							map.put(Integer.parseInt(objects2[0].toString()), objects2[1].toString());
						}
					}
				}
				return map;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;		
		}
		
		public List<ClassSchemewise> getClassSchemeByCourseId1(int semNo,
				Integer year, int courseId) throws Exception {
			Session session = null;
			List<ClassSchemewise> classes = null;
			try {
				session = HibernateUtil.getSession();
				String sql ="select cs "
					+ " from ClassSchemewise cs"
					+ " where cs.curriculumSchemeDuration.academicYear ="+ year
					+ " and cs.curriculumSchemeDuration.curriculumScheme.course.id=" + courseId +" order by cs"	;
				/*String sql = "select cs "
						+ " from Classes c1  "
						+ " join c1.classSchemewises cs "
						+ " where c1.isActive=1 and cs.curriculumSchemeDuration.curriculumScheme.year = "
						+ year
						+ " and c1.course.id=" + courseId;*/
				Query queri = session.createQuery(sql);
				classes = queri.list();
			} catch (Exception e) {
				log.error("Error in getClassSchemeForStudent..." + e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					//session.flush();
				}
			}
			return classes;
		}


		
		
		//new for admission
		
		public Map<Integer, String> getApplnProgramsByProgramTypeNew(int id) throws Exception{
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				/*Query query = session.createQuery("select pt from ProgramType pt "+
												"join pt.programs p "+
												"join p.courses c "+
												"where pt.id= :ID and pt.isActive =1  and c.isActive=1 " +
												"and c.isAppearInOnline=1 " +
												"group by pt");*/
				Query query = session.createSQLQuery("select program.id,program.name from program "+
										" inner join course on course.program_id = program.id "+
									    " where program.program_type_id=:ID and course.is_appear_in_online=1 and program.is_active=1 and course.is_active=1 "+
										" group by program.id");
				query.setInteger("ID", id);
				//ProgramType programType = (ProgramType)query.uniqueResult();
				Map<Integer, String> programMap = new HashMap<Integer, String>();
				List<Object[]> list=query.list();
				/*Iterator<Program> itr = programType.getPrograms().iterator();
				Program program;
				while (itr.hasNext()) {
					program = (Program) itr.next();
					if (program.getIsActive()) {
					}
				}*/
				if (list!=null && !list.isEmpty()) {
					for (Object[] program : list) {
						programMap.put(Integer.parseInt(program[0].toString()), program[1].toString());
					}
				}
				// session.close();
				programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);
				return programMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
				throw e;
			}
		}
		
		public Map<Integer, String> getCourseByProgramTypeForOnlineNew(int id) throws Exception{
			Session session = null;
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				/*Query query = session.createQuery(
						"from Program p where p.programType.id = :programTypeId and isActive =1 ")
						.setInteger("programTypeId", id);
				Program program = new Program();
				if (!query.list().isEmpty())
					program = (Program) query.list().get(0);
				Map<Integer, String> courseMap = new HashMap<Integer, String>();
				Iterator<Course> itr = program.getCourses().iterator();
				Course course;
				while (itr.hasNext()) {
					course = (Course) itr.next();
					if (course.getIsActive() && course.getIsAppearInOnline())
						courseMap.put(course.getId(), course.getName());
						
				}*/
				Query query = session.createSQLQuery("select course.id,course.name from program "+
						" inner join course on course.program_id = program.id "+
					    " where program.program_type_id=:ID and course.is_appear_in_online=1 and program.is_active=1 and course.is_active=1 "+
						" group by course.id order by course.name");
                query.setInteger("ID", id);
                Map<Integer, String> courseMap = new HashMap<Integer, String>();
				List<Object[]> list=query.list();
				
                if (list!=null && !list.isEmpty()) {
					for (Object[] program : list) {
						courseMap.put(Integer.parseInt(program[0].toString()), program[1].toString());
					}
				}
				// session.close();
                courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
				return courseMap;
				
				
			} catch (Exception e) {
				// session.close();
				log.debug("Exception" + e.getMessage());
				throw e;
			}
		}
		public Map<Integer, String> getProgram(int year) {
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				/*Query query = session
						.createQuery(
								"from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear order by c.classes.name")
						.setInteger("academicYear", year);*/
				Query query = session
				.createQuery(
						" from AdmAppln a where a.appliedYear=:appliedYear group by a.course.id ")
				.setInteger("appliedYear", year);
								
				
				List<AdmAppln> classList = query.list();
				Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
				Iterator<AdmAppln> itr = classList.iterator();
				AdmAppln adm;

				
				
				
				while (itr.hasNext()) {
					adm = (AdmAppln) itr.next();
					classMap.put(adm.getCourse().getId(), adm
							.getCourse().getName());
				}
				//session.flush();
				// session.close();
				return classMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}

			return new HashMap<Integer, String>();
		}
		public Map<Integer, String> getClassNameByExamName(int examId)throws Exception{
			Session session = null;
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			try{
				String SQL_QUERY = "SELECT classes.id, classes.name FROM  classes classes"
				+ " INNER JOIN EXAM_exam_course_scheme_details EXAM_exam_course_scheme_details"
				+ " ON classes.course_id = EXAM_exam_course_scheme_details.course_id"
				+ " AND classes.term_number = EXAM_exam_course_scheme_details.scheme_no"
				+ " INNER JOIN class_schemewise class_schemewise ON class_schemewise.class_id = classes.id"
				+ " INNER JOIN curriculum_scheme_duration curriculum_scheme_duration"
				+ " ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id"
				+ " INNER JOIN EXAM_definition EXAM_definition ON curriculum_scheme_duration.academic_year = EXAM_definition.academic_year"
				+ " AND EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id where EXAM_definition.id=:examId";
				
				Query query = session.createSQLQuery(SQL_QUERY);
				query.setParameter("examId", examId);
				List<Object[]> objList = new ArrayList<Object[]>();
				objList = (List<Object[]>) query.list();
				for (Object[] objects : objList) {
					classMap.put(Integer.valueOf(objects[0].toString()),
							objects[1].toString());
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				log.debug("Exception" + e.getMessage());
			}
			return classMap;
		}
		
		// by Arun Sudhakaran
		public Map<Integer, String> getSemistersByYearAndProgramType(int academicYear, int programTypeId) throws Exception
		{
			Session session = null;
			
			try
			{
				session = HibernateUtil.getSession();
				String sqlQuery = "select classes.term_number from program_type program_type" +
								  " inner join program program on program.program_type_id = program_type.id" +
								  " inner join course course on course.program_id = program.id" +
								  " inner join classes on classes.course_id = course.id" +
								  " inner join class_schemewise on class_schemewise.class_id = classes.id" +
								  " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
								  " inner join curriculum_scheme on curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
								  " where classes.is_active = 1" +
								  "   and course.is_active = 1" +
								  "   and program.is_active = 1" +
								  "   and program_type.is_active = 1" +
								  "   and program_type.id = " + programTypeId +
								  //"   and curriculum_scheme_duration.academic_year = " + academicYear +  [comented to show all semesters in any program type]
								  " group by classes.term_number";
				Query query = session.createSQLQuery(sqlQuery);
				List<Integer> semList = query.list();
				session.flush();
				Map<Integer, String> semMap = new HashMap<Integer, String>();
				
				if(semList != null && !semList.isEmpty())
				{
					Iterator<Integer> semItr = semList.iterator();
					while(semItr.hasNext())
					{
						Integer sem = semItr.next();
						semMap.put(sem, String.valueOf(sem));
					}
				}
				return semMap;
			}
			catch(Exception ex)
			{
				throw new ApplicationException();
			}
		}
		
		// vinodha
		@SuppressWarnings("unchecked")
		public Map<Integer, String> getClassesForAttendanceByYear(int year, String evenOrOdd) {
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = null;
				if(evenOrOdd.equalsIgnoreCase("odd")){
					query = session
						.createQuery(
								"from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear " +
								" and c.classes.termNumber in (1,3,5,7)" +
								" order by c.classes.name")
						.setInteger("academicYear", year);
				}else if(evenOrOdd.equalsIgnoreCase("even")){
					query = session
					.createQuery(
							"from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear " +
							" and c.classes.termNumber in (2,4,6,8)" +
							" order by c.classes.name")
					.setInteger("academicYear", year);
				}
				List<ClassSchemewise> classList = query.list();
				Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
				Iterator<ClassSchemewise> itr = classList.iterator();
				ClassSchemewise classSchemewise;

				while (itr.hasNext()) {
					classSchemewise = (ClassSchemewise) itr.next();
					classMap.put(classSchemewise.getId(), classSchemewise
							.getClasses().getName());
				}
				session.flush();
				// session.close();
				classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
				return classMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}

			return new HashMap<Integer, String>();
		}
		
		public Map<Integer, String> getSemesterMapForExam(int examId) throws Exception
		{
			Session session = null;
			
			try
			{
				session = HibernateUtil.getSession();
				Query query = session.createSQLQuery("select scheme_no from EXAM_exam_course_scheme_details where EXAM_exam_course_scheme_details.exam_id = " + examId +" group by scheme_no");
				List<Integer> schemes = query.list();
				session.flush();
				Map<Integer, String> semMap = new HashMap<Integer, String>();
				Iterator<Integer> it = schemes.iterator();
				while(it.hasNext())
				{
					int sem = it.next();
					semMap.put(sem, String.valueOf(sem));
				}
				return semMap;
			}
			catch(Exception ex)
			{
				if(session != null)
				{
					session.flush();
					session.close();					
				}
				throw new ApplicationException();
			}
		}
		
		public List<AttendanceCondonation> condonationRestrict(String baseStudentId) {

			Session session = null;
			List<AttendanceCondonation> ac = null;
			try {
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from AttendanceCondonation a where a.isActive=1 and a.student.id="+baseStudentId);

				ac = query.list();

			} catch (Exception e) {
				// TODO: handle exception
			}
			return ac;
		}
		
		@Override
		public Map<Integer, String> getClassesByCourseSemesterAndAcademicYear(
				int semester, int academicyear, int programTypeId,
				int programId, int courseId) {
			
			List<Object[]> classList = null;
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				
				String sql = "select classes.id,classes.name from classes " +
				"inner join class_schemewise on class_schemewise.class_id = classes.id " +
				"inner join course ON classes.course_id = course.id " +
				"inner join program ON course.program_id = program.id " +
				"inner join program_type ON program.program_type_id = program_type.id " +
				"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
				"where classes.term_number="+semester+" and curriculum_scheme_duration.academic_year="+academicyear;
				
				if(programTypeId > 0){
					sql = sql + " and program.program_type_id="+programTypeId;
				}
				if(programId > 0){
					sql = sql + " and course.program_id="+programId;
				}
				if(courseId > 0){
					sql = sql + " and classes.course_id="+courseId;
				}
				
				Query queri = session.createSQLQuery(sql);
				classList = queri.list();
				Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
				Iterator<Object[]> itr = classList.iterator();
				Object[] classes;
				while (itr.hasNext()) {
					classes = (Object[]) itr.next();
					classMap.put(Integer.parseInt(classes[0].toString()), classes[1].toString());
				}
				session.flush();
				session.close();
				classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

				return classMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return new HashMap<Integer, String>();
			
		}
		
		public Map<Integer, String> getClassesByProgramTypeAndAcademicYear1(int programTypeId, int academicYear) throws Exception {
			
			List<Object[]> classList = null;
			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				
				String sql = "select classes.id,classes.name from classes " +
				"inner join class_schemewise on class_schemewise.class_id = classes.id " +
				"inner join course ON classes.course_id = course.id " +
				"inner join program ON course.program_id = program.id " +
				"inner join program_type ON program.program_type_id = program_type.id " +
				"inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
				"where program.program_type_id="+programTypeId+" and curriculum_scheme_duration.academic_year="+academicYear;
				
				Query queri = session.createSQLQuery(sql);
				classList = queri.list();
				Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
				Iterator<Object[]> itr = classList.iterator();
				Object[] classes;
				while (itr.hasNext()) {
					classes = (Object[]) itr.next();
					classMap.put(Integer.parseInt(classes[0].toString()), classes[1].toString());
				}
				session.flush();
				session.close();
				classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);

				return classMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return new HashMap<Integer, String>();
		}
		
		public String getAcademicYearForClass(int studentId, int semester) throws Exception {
			
			Session session = null;
			String academicYear = null;
			try {
				session = HibernateUtil.getSession();
				String hql = "select cs.curriculumSchemeDuration.academicYear from ClassSchemewise cs " +
							 "where cs.classes.id = (select stud.classSchemewise.classes.id " +
							 						  "from Student stud " +
							 						  "where stud.classSchemewise.classes.termNumber = :semester " +
							 						  "and stud.id = :studentId)" +
							 "	 or cs.classes.id = (select prev.classes.id " +
							 						  "from StudentPreviousClassHistory prev " +
							 						  "where prev.classes.termNumber = :semester " +
							 						  "and prev.student.id = :studentId)";
				Query query = session.createQuery(hql)
									  .setInteger("studentId", studentId)
									  .setInteger("semester", semester);
				academicYear = query.uniqueResult().toString();				
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
			if(academicYear != null)
				return academicYear;
			else 
				throw new DataNotFoundException();
		}

		@Override
		public ArrayList<Object[]> getProgramsByActivityId(
				String extraCreditActivityType) {

			Session session = null;
			ArrayList<Object[]> list;
			try {
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();
				Query query = (Query) session
						.createQuery("select p.id,p.programmeCode from ProgrammeEntryBo p where p.extraCreditActivityType.id= :extraCreditActivityTypeId and p.isActive=1")
						.setInteger("extraCreditActivityTypeId", Integer.parseInt(extraCreditActivityType));
				list = new ArrayList<Object[]>(query.list());
				session.flush();
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				if (session != null) {
					session.flush();
					session.close();
				}
				list = new ArrayList<Object[]>();
			}
			return list;
		}

		@Override
		public Map<Integer, String> getSemisterByYear(int year) {

			try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session
						.createQuery(
							"from ClassSchemewise c  where  c.classes.course.isActive = 1  and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear  order by c.classes.termNumber,c.classes.course.program.programType.id,c.classes.course.program.id  ")
								.setInteger("academicYear", year);
				List<ClassSchemewise> semisterList = query.list();
				Map<Integer, String> semisterMap = new TreeMap<Integer, String>();
				Iterator<ClassSchemewise> itr = semisterList.iterator();
				ClassSchemewise classSchemewise;

				while (itr.hasNext()) {
					classSchemewise = (ClassSchemewise) itr.next();
					semisterMap.put(classSchemewise
							.getClasses().getTermNumber(), classSchemewise
							.getClasses().getTermNumber().toString());
				}
				session.flush();
				// session.close();
				
				return semisterMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}

			return new HashMap<Integer, String>();
		
		}

		@Override
		public Map<Integer, String> getClassesByYearAndSem(int year,
				int semisterNo) {
			
                try {
				Session session = null;
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				Query query = session
						.createQuery(
							"from ClassSchemewise c  where  c.classes.course.isActive = 1  and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear =:academicYear and c.classes.termNumber= :semisterNo order by c.classes.termNumber,c.classes.course.program.programType.id,c.classes.course.program.id")
								.setInteger("academicYear", year)
								.setInteger("semisterNo", semisterNo);
				List<ClassSchemewise> semisterList = query.list();
				Map<Integer, String> semisterMap = new TreeMap<Integer, String>();
				Iterator<ClassSchemewise> itr = semisterList.iterator();
				ClassSchemewise classSchemewise;

				while (itr.hasNext()) {
					classSchemewise = (ClassSchemewise) itr.next();
					semisterMap.put(classSchemewise.getId(), classSchemewise
							.getClasses().getName());
				}
				session.flush();
				// session.close();
				
				return semisterMap;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}

			return new HashMap<Integer, String>();
		
		
		}

		@Override
		public Map<Integer, String> getClassesByAcademicYear(String academicYear)
				throws Exception {
			Session session = null;
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			
			try{
				session = HibernateUtil.getSession();
				String s = "select classes.id,classes.name from classes " +
						" inner join class_schemewise ON class_schemewise.class_id = classes.id " +
						" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
						" where curriculum_scheme_duration.academic_year= :academicYear ";
				Query query = session.createSQLQuery(s)
								.setString("academicYear", academicYear);
				List<Object[]> classList = query.list();
				Iterator<Object[]> itr = classList.iterator();
				while(itr.hasNext()){
					Object[] obj = itr.next();
					classMap.put(Integer.parseInt(obj[0].toString()), obj[1].toString());
				}
			}catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException(e);
			}
			return classMap;
		}

		@Override
		public Map<Integer, String> getCourseByyearandTerm(String year, String term, String userId, String roleId) {
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				String scheme=null;
				String quer=null;
				if (term=="odd" || term.equalsIgnoreCase("odd")) {
					scheme="1,3,5";
				}else
					scheme="2,4,6";
				if (userId!=null && CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(Integer.parseInt(roleId))) {
					quer=	" select subject.id,subject.name,subject.code"+ 
							" from teacher_class_subject"+ 
							" inner join class_schemewise ON class_schemewise.id = teacher_class_subject.class_schemewise_id"+ 
							" inner join users ON users.id = teacher_class_subject.teacher_id"+ 
							" inner join subject ON subject.id = teacher_class_subject.subject_id"+ 
							" inner join employee ON employee.id = users.employee_id"+ 
							" inner join department ON department.id = employee.department_id"+ 
							" inner join classes ON classes.id = class_schemewise.class_id"+ 
							" inner join course on course.id = classes.course_id"+ 
							" where teacher_class_subject.`year`="+year+ 
							" and subject.scheme_no in("+scheme+")"+
							" and teacher_class_subject.is_active =true"+
							" and teacher_class_subject.teacher_id="+userId;
				}else{
				quer=" select subject.id,subject.name,subject.code"+ 
						" from teacher_class_subject"+ 
						" inner join class_schemewise ON class_schemewise.id = teacher_class_subject.class_schemewise_id"+ 
						" inner join users ON users.id = teacher_class_subject.teacher_id"+ 
						" inner join subject ON subject.id = teacher_class_subject.subject_id"+ 
						" inner join employee ON employee.id = users.employee_id"+ 
						" inner join department ON department.id = employee.department_id"+ 
						" inner join classes ON classes.id = class_schemewise.class_id"+ 
						" inner join course on course.id = classes.course_id"+ 
						" where teacher_class_subject.`year`="+year+ 
						" and teacher_class_subject.is_active =true"+
						" and subject.scheme_no in("+scheme+")";
				}
				SQLQuery query = session
						.createSQLQuery(quer);

				Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();

				List<Object[]> subList = query.list();
				Iterator<Object[]> itrUser=subList.iterator();
				while(itrUser.hasNext()){
					Object[] sub=(Object[])itrUser.next();
					int id=(Integer) sub[0];
					String name=(String) sub[1];
					subjectMap.put(id, name);
				}
				return subjectMap;
			} catch (Exception e) {
				return new HashMap<Integer, String>();
			} finally {
				if (session != null) {
					session.flush();
				}
			}
		}

		@Override
		public Map<Integer, String> getExamNameByYearAndCourseAndSem(int year,int course,int sem) throws Exception {
			Session session = null;
			Map<Integer, String> map=new HashMap<Integer, String>();
			try {
				// SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = HibernateUtil.getSession();
				String SQL_QUERY = "select ecbo.examDefinitionBO from ExamExamCourseSchemeDetailsBO ecbo where ecbo.examDefinitionBO.examTypeUtilBO.name  like '%Int%' " +
									"and ecbo.examDefinitionBO.delIsActive = 1 and ecbo.examDefinitionBO.isActive = 1 " +
									"and ecbo.examDefinitionBO.academicYear="+year+"and ecbo.courseId="+course+"and ecbo.schemeNo="+sem;
					
				
				Query query = session.createQuery(SQL_QUERY);
				Iterator<ExamDefinitionBO> itr=query.list().iterator();
				while (itr.hasNext()) {
					ExamDefinitionBO exam= itr.next();
					map.put(exam.getId(),exam.getName());
				}
				return map;
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return map;
		}

		
}

