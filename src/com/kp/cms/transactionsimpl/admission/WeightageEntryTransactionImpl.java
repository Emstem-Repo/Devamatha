package com.kp.cms.transactionsimpl.admission;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantWorkExperience;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.admin.Weightage;
import com.kp.cms.bo.admin.WeightageDefinition;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.WeightageDefenitionForm;
import com.kp.cms.to.admission.CasteWeightageTO;
import com.kp.cms.to.admission.CountryWeightageTO;
import com.kp.cms.to.admission.CoursePrerequisiteWeightageTO;
import com.kp.cms.to.admission.EducationalWeightageAdjustedTO;
import com.kp.cms.to.admission.EducationalWeightageDefenitionTO;
import com.kp.cms.to.admission.GenderWeightageTO;
import com.kp.cms.to.admission.GeneralWeightageAdjustedTO;
import com.kp.cms.to.admission.InstituteWeightageTO;
import com.kp.cms.to.admission.InterviewSubroundDisplayTO;
import com.kp.cms.to.admission.InterviewWeightageAdjustedTO;
import com.kp.cms.to.admission.InterviewWeightageDefenitionTO;
import com.kp.cms.to.admission.NationalityWeightageTO;
import com.kp.cms.to.admission.PreviousQualificationWeightageTO;
import com.kp.cms.to.admission.ReligionWeightageTO;
import com.kp.cms.to.admission.ResidentCategoryWeightageTO;
import com.kp.cms.to.admission.RuralUrbanWeightageTO;
import com.kp.cms.to.admission.SubReligionWeightageTO;
import com.kp.cms.to.admission.TotalWeightageAdjustedTO;
import com.kp.cms.to.admission.UniversityWeightageTO;
import com.kp.cms.to.admission.WorkExperienceWeightageTO;
import com.kp.cms.transactions.admission.IWeightageEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class WeightageEntryTransactionImpl implements
		IWeightageEntryTransaction {
	private static final Log log = LogFactory.getLog(WeightageEntryTransactionImpl.class);
	/** @see com.brio.cms.transactions.admission.IWeightageEntryTransaction#
	 * getEducationalWeightageTypes(java.lang.Integer)
	 */
	@Override
	public List<DocChecklist> getEducationalWeightageTypes(Integer courseId,
			Integer year) throws ApplicationException {
		log.info("entering into getEducationalWeightageTypes of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<DocChecklist> educationalWeightageList = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String educationalWeightage = " from DocChecklist docChecklist where docChecklist.isMarksCard = true and docChecklist.course.id = :courseId and docChecklist.year = :year and docChecklist.isActive = true";
			Query educationalWeightageQuery = session
					.createQuery(educationalWeightage);
			educationalWeightageQuery.setInteger("courseId", courseId);
			educationalWeightageQuery.setInteger("year", year);
			educationalWeightageList = educationalWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getEducationalWeightageTypes of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getEducationalWeightageTypes of WeightageEntryTransactionImpl class.");
		return educationalWeightageList;
	}

	/** @see com.brio.cms.transactions.admission.IWeightageEntryTransaction#
	 * getInterviewWeightageTypes(java.lang.Integer)
	 */
	@Override
	public List<InterviewProgramCourse> getInterviewWeightageTypes(
			Integer courseId, Integer year) throws ApplicationException {
		log.info("entering into getInterviewWeightageTypes of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<InterviewProgramCourse> interviewWeightageList = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			
			String interviewWeightage = " from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.course.id = :courseId  and interviewProgramCourse.year = :year and interviewProgramCourse.isActive = true  order by interviewProgramCourse.sequence";
			Query interviewWeightageQuery = session
					.createQuery(interviewWeightage);
			interviewWeightageQuery.setInteger("courseId", courseId);
			interviewWeightageQuery.setInteger("year", year);
			interviewWeightageList = interviewWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getInterviewWeightageTypes of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getInterviewWeightageTypes of WeightageEntryTransactionImpl class.");
		return interviewWeightageList;

	}

	/**
	 *  
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getReligionSectionWeightage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List getReligionSectionWeightage(Integer courseId, Integer year)
			throws ApplicationException {
		log.info("entering into getReligionSectionWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List religionSectionWeightageList = null;

		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String religionSectionWeightage = " select religionSection.id,religionSection.name,weightage_definition.id,weightage_definition.weightage_1 from WeightageDefinition weightage_definition "
					+ " right outer join weightage_definition.weightage weightage  with weightage.course.id = :courseId and weightage.year = :year"
					+ " right outer join "
					+ " weightage_definition.religionSection religionSection";
			Query religionSectionWeightageQuery = session
					.createQuery(religionSectionWeightage);
			religionSectionWeightageQuery.setInteger("courseId", courseId);
			religionSectionWeightageQuery.setInteger("year", year);
			religionSectionWeightageList = religionSectionWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getReligionSectionWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getReligionSectionWeightage of WeightageEntryTransactionImpl class.");
		return religionSectionWeightageList;

	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getCountryWeightage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List getCountryWeightage(Integer courseId, Integer year)
			throws ApplicationException {
		log.info("entering into getCountryWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List countryWeightageList = null;

		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String countryWeightage = " select country.id,country.name,weightage_definition.id,weightage_definition.weightage_1 from WeightageDefinition weightage_definition"
					+ " right outer join weightage_definition.weightage weightage  with weightage.course.id = :courseId and weightage.year = :year"
					+ " right outer join "
					+ " weightage_definition.country country where  country.isActive = true";
			Query countryWeightageQuery = session.createQuery(countryWeightage);
			countryWeightageQuery.setInteger("courseId", courseId);
			countryWeightageQuery.setInteger("year", year);
			countryWeightageList = countryWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getCountryWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getCountryWeightage of WeightageEntryTransactionImpl class.");
		return countryWeightageList;

	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getMaleWeightageDefinition(java.lang.Integer, java.lang.Integer)
	 */
	public List getMaleWeightageDefinition(Integer courseId, Integer year)
			throws ApplicationException {
		log.info("entering into getMaleWeightageDefinition of WeightageEntryTransactionImpl class.");
		Session session = null;
		List maleWeightageDefinition = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String maleWeightageDefenitionQuery = " from WeightageDefinition weightageDefenition "
					+ " inner join weightageDefenition.weightage weightage "
					+ " with weightage.course.id = :courseId"
					+ " and weightage.year = :year "
					+ " where weightageDefenition.gender = 'Male' ";

			Query genderWeightageQuery = session
					.createQuery(maleWeightageDefenitionQuery);
			genderWeightageQuery.setInteger("courseId", courseId);
			genderWeightageQuery.setInteger("year", year);
			maleWeightageDefinition = genderWeightageQuery.list();

		} catch (Exception e) {
			log.info("error in getMaleWeightageDefinition of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getMaleWeightageDefinition of WeightageEntryTransactionImpl class.");
		return maleWeightageDefinition;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getFeMaleWeightageDefinition(java.lang.Integer, java.lang.Integer)
	 */
	public List getFeMaleWeightageDefinition(Integer courseId, Integer year)
			throws ApplicationException {
		log.info("entering into getFeMaleWeightageDefinition of WeightageEntryTransactionImpl class.");
		Session session = null;
		List femaleWeightageDefinition = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String femaleWeightageDefenitionQuery = " from WeightageDefinition weightageDefenition "
					+ " inner join weightageDefenition.weightage weightage "
					+ " with weightage.course.id = :courseId"
					+ " and weightage.year = :year "
					+ " where weightageDefenition.gender = 'Female' ";

			Query genderWeightageQuery = session
					.createQuery(femaleWeightageDefenitionQuery);
			genderWeightageQuery.setInteger("courseId", courseId);
			genderWeightageQuery.setInteger("year", year);
			femaleWeightageDefinition = genderWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getFeMaleWeightageDefinition of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getFeMaleWeightageDefinition of WeightageEntryTransactionImpl class.");
		return femaleWeightageDefinition;

	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getRuralWeightageDefinition(java.lang.Integer, java.lang.Integer)
	 */
	public List getRuralWeightageDefinition(Integer courseId, Integer year)
			throws ApplicationException {
		log.info("entering into getRuralWeightageDefinition of WeightageEntryTransactionImpl class.");
		Session session = null;
		List ruralWeightageDefinition = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String ruralWeightageDefinitionQuery = " from WeightageDefinition weightageDefenition "
					+ " inner join weightageDefenition.weightage weightage "
					+ " with weightage.course.id = :courseId"
					+ " and weightage.year = :year "
					+ " where weightageDefenition.ruralUrban = 'R' ";

			Query genderWeightageQuery = session
					.createQuery(ruralWeightageDefinitionQuery);
			genderWeightageQuery.setInteger("courseId", courseId);
			genderWeightageQuery.setInteger("year", year);
			ruralWeightageDefinition = genderWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getRuralWeightageDefinition of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getRuralWeightageDefinition of WeightageEntryTransactionImpl class.");
		return ruralWeightageDefinition;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getUrbanWeightageDefinition(java.lang.Integer, java.lang.Integer)
	 */
	public List getUrbanWeightageDefinition(Integer courseId, Integer year)
			throws ApplicationException {
		log.info("entering into getUrbanWeightageDefinition of WeightageEntryTransactionImpl class.");
		Session session = null;
		List urbanWeightageDefinition = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String urbanWeightageDefinitionQuery = " from WeightageDefinition weightageDefenition "
					+ " inner join weightageDefenition.weightage weightage "
					+ " with weightage.course.id = :courseId"
					+ " and weightage.year = :year "
					+ " where weightageDefenition.ruralUrban = 'U' ";

			Query genderWeightageQuery = session
					.createQuery(urbanWeightageDefinitionQuery);
			genderWeightageQuery.setInteger("courseId", courseId);
			genderWeightageQuery.setInteger("year", year);
			urbanWeightageDefinition = genderWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getUrbanWeightageDefinition of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getUrbanWeightageDefinition of WeightageEntryTransactionImpl class.");
		return urbanWeightageDefinition;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getInstituteWeightage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<WeightageDefinition> getInstituteWeightage(Integer courseId,
			Integer year) throws ApplicationException {
		log.info("entering into getInstituteWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<WeightageDefinition> instituteWeightageList = null;

		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String instituteWeightage = " select college.id,college.name,weightage_definition.id,weightage_definition.weightage_1 from WeightageDefinition weightage_definition "
					+ " right outer join weightage_definition.weightage weightage  with weightage.course.id = :courseId and weightage.year = :year"
					+ " right outer join "
					+ " weightage_definition.college college where  college.isActive = true ";
			Query instituteWeightageQuery = session
					.createQuery(instituteWeightage);
			instituteWeightageQuery.setInteger("courseId", courseId);
			instituteWeightageQuery.setInteger("year", year);
			instituteWeightageList = instituteWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getInstituteWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getInstituteWeightage of WeightageEntryTransactionImpl class.");
		return instituteWeightageList;

	}
	
	
	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getNationalityWeightage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<WeightageDefinition> getNationalityWeightage(Integer courseId,
			Integer year) throws ApplicationException {
		log.info("entering into getNationalityWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<WeightageDefinition> nationalityWeightageList = null;

		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String nationalityWeightage = " select nationality.id,nationality.name,weightage_definition.id,"
					+ " weightage_definition.weightage_1 from WeightageDefinition weightage_definition "
					+ " right outer join weightage_definition.weightage weightage "
					+ " with weightage.course.id = :courseId and weightage.year = :year "
					+ " right outer join "
					+ " weightage_definition.nationality nationality";
			Query nationalityWeightageQuery = session
					.createQuery(nationalityWeightage);
			nationalityWeightageQuery.setInteger("courseId", courseId);
			nationalityWeightageQuery.setInteger("year", year);
			nationalityWeightageList = nationalityWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getNationalityWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getNationalityWeightage of WeightageEntryTransactionImpl class.");
		return nationalityWeightageList;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getReligionWeightage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<WeightageDefinition> getReligionWeightage(Integer courseId,
			Integer year) throws ApplicationException {
		log.info("entering into getReligionWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<WeightageDefinition> religionWeightageList = null;

		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String religionWeightage = " select religion.id,religion.name,weightage_definition.id,weightage_definition.weightage_1 from WeightageDefinition weightage_definition "
					+ " right outer join weightage_definition.weightage weightage  with weightage.course.id = :courseId and weightage.year = :year "
					+ " right outer join "
					+ " weightage_definition.religion religion where  religion.isActive = true";
			Query religionWeightageQuery = session
					.createQuery(religionWeightage);
			religionWeightageQuery.setInteger("courseId", courseId);
			religionWeightageQuery.setInteger("year", year);
			religionWeightageList = religionWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getReligionWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getReligionWeightage of WeightageEntryTransactionImpl class.");
		return religionWeightageList;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getResidentCategoryWeightage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<WeightageDefinition> getResidentCategoryWeightage(
			Integer courseId, Integer year) throws ApplicationException {
		log.info("entering into getResidentCategoryWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<WeightageDefinition> residentCategoryWeightageList = null;

		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String residentCategoryWeightage = "select residentCategory.id,residentCategory.name,weightage_definition.id,weightage_definition.weightage_1 from "
					+ "WeightageDefinition weightage_definition  right outer join weightage_definition.weightage weightage  with weightage.course.id = :courseId and weightage.year = :year"
					+ " right outer join "
					+ " weightage_definition.residentCategory residentCategory where  residentCategory.isActive = true ";
			Query residentCategoryWeightageQuery = session
					.createQuery(residentCategoryWeightage);
			residentCategoryWeightageQuery.setInteger("courseId", courseId);
			residentCategoryWeightageQuery.setInteger("year", year);
			residentCategoryWeightageList = residentCategoryWeightageQuery
					.list();
		} catch (Exception e) {
			log.info("error in getResidentCategoryWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getResidentCategoryWeightage of WeightageEntryTransactionImpl class.");
		return residentCategoryWeightageList;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getUniversityWeightage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<WeightageDefinition> getUniversityWeightage(Integer courseId,
			Integer year) throws ApplicationException {
		log.info("entering into getUniversityWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<WeightageDefinition> universityWeightageList = null;

		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			
			session = HibernateUtil.getSession();
			String universityWeightage = "select university.id,university.name,weightage_definition.id,weightage_definition.weightage_1 from WeightageDefinition weightage_definition "
					+ " right outer join weightage_definition.weightage weightage  with weightage.course.id = :courseId and weightage.year = :year"
					+ " right outer join "
					+ " weightage_definition.university university where university.isActive = true ";
			Query universityWeightageQuery = session
					.createQuery(universityWeightage);
			universityWeightageQuery.setInteger("courseId", courseId);
			universityWeightageQuery.setInteger("year", year);
			universityWeightageList = universityWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getUniversityWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getUniversityWeightage of WeightageEntryTransactionImpl class.");
		return universityWeightageList;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getCasteWeightage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<WeightageDefinition> getCasteWeightage(Integer courseId,
			Integer year) throws ApplicationException {
		log.info("entering into getCasteWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<WeightageDefinition> casteWeightageList = null;

		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String religionWeightage = " select caste.id,caste.name,weightage_definition.id,"
					+ " weightage_definition.weightage_1 from WeightageDefinition weightage_definition "
					+ " right outer join weightage_definition.weightage weightage  with weightage.course.id = :courseId "
					+ " and weightage.year = :year "
					+ " right outer join "
					+ " weightage_definition.caste caste where  caste.isActive = true ";
			Query casteWeightageQuery = session.createQuery(religionWeightage);
			casteWeightageQuery.setInteger("courseId", courseId);
			casteWeightageQuery.setInteger("year", year);
			casteWeightageList = casteWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getCasteWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getCasteWeightage of WeightageEntryTransactionImpl class.");
		return casteWeightageList;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#submitWeightageDefenition(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void submitWeightageDefenition(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into submitWeightageDefenition of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			Weightage weightage = getWeightageDefenition(weightagDefenitionForm);
			if (weightage == null) {
				Weightage weightageEntry = new Weightage();

				Course course = new Course();
				course.setId(Integer.valueOf(weightagDefenitionForm
						.getCourseId()));
				weightageEntry.setCourse(course);

				Program program = new Program();
				program.setId(Integer.valueOf(weightagDefenitionForm
						.getProgramId()));
				weightageEntry.setProgram(program);

				weightageEntry.setYear(Integer.valueOf(weightagDefenitionForm
						.getYear()));
				weightageEntry.setCreatedDate(new Date());
				weightageEntry.setCreatedBy(weightagDefenitionForm.getUserId());
				weightageEntry.setEducationWeightage(new BigDecimal(
						weightagDefenitionForm.getEducationWeightage()));
				weightageEntry.setInterviewWeightage(new BigDecimal(
						weightagDefenitionForm.getInterviewWeightage()));
				weightageEntry.setPrerequisiteWeightage(new BigDecimal(
						weightagDefenitionForm.getPrerequisiteWeightage()));
				session.save(weightageEntry);

			} else {

				Weightage weightageEntry = (Weightage) session.get(
						Weightage.class, weightage.getId());
				weightageEntry.setLastModifiedDate(new Date());
				weightageEntry
						.setModifiedBy(weightagDefenitionForm.getUserId());
				weightageEntry.setEducationWeightage(new BigDecimal(
						weightagDefenitionForm.getEducationWeightage()));
				weightageEntry.setInterviewWeightage(new BigDecimal(
						weightagDefenitionForm.getInterviewWeightage()));
				weightageEntry.setPrerequisiteWeightage(new BigDecimal(
						weightagDefenitionForm.getPrerequisiteWeightage()));
				session.update(weightageEntry);
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in submitWeightageDefenition of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of submitWeightageDefenition of WeightageEntryTransactionImpl class.");
	}
    
	public Weightage getWeightageByCourseAndYear(int courseId,int year)throws ApplicationException{
		log.info("entering into getWeightageByCourseAndYear of WeightageEntryTransactionImpl class.");
		Session session = null;
		Weightage weightage = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String weightageQueryString = "from Weightage weightage"
					+ " where weightage.course.id = :courseId "
					+ " and weightage.year = :year";
			Query WeightageQuery = session.createQuery(weightageQueryString);
			WeightageQuery.setInteger("courseId",courseId);
					
			WeightageQuery.setInteger("year",year);
					
			weightage = (Weightage) WeightageQuery.uniqueResult();
		} catch (Exception e) {
			log.info("error in getWeightageByCourseAndYear of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getWeightageDefenition of WeightageEntryTransactionImpl class.");
		return weightage;
	}
	
	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getWeightageDefenition(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	public Weightage getWeightageDefenition(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into getWeightageDefenition of WeightageEntryTransactionImpl class.");
		Session session = null;
		Weightage weightage = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String weightageQueryString = "from Weightage weightage"
					+ " where weightage.course.id = :courseId "
					+ " and weightage.year = :year";
			Query WeightageQuery = session.createQuery(weightageQueryString);
			WeightageQuery.setInteger("courseId", Integer
					.valueOf(weightagDefenitionForm.getCourseId()));
			WeightageQuery.setInteger("year", Integer
					.valueOf(weightagDefenitionForm.getYear()));
			weightage = (Weightage) WeightageQuery.uniqueResult();
		} catch (Exception e) {
			log.info("error in getWeightageDefenition of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getWeightageDefenition of WeightageEntryTransactionImpl class.");
		return weightage;
	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#submitEducationalWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void submitEducationalWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into submitEducationalWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<EducationalWeightageDefenitionTO> educationalList = weightagDefenitionForm
					.getEducationWeightageList();
			Iterator<EducationalWeightageDefenitionTO> educationWeightageIterator = educationalList
					.iterator();
			while (educationWeightageIterator.hasNext()) {
				EducationalWeightageDefenitionTO educationalWeightageDefenitionTO = (EducationalWeightageDefenitionTO) educationWeightageIterator
						.next();

				DocChecklist docChecklist = (DocChecklist) session.get(
						DocChecklist.class, educationalWeightageDefenitionTO
								.getDocCheckListId());
				Weightage weightage = new Weightage();

				weightage.setId(weightagDefenitionForm.getWeightageId());
				docChecklist.setLastModifiedDate(new Date());
				docChecklist.setModifiedBy(weightagDefenitionForm.getUserId());
				docChecklist.setWeightage(weightage);
				if (educationalWeightageDefenitionTO.getWeightagePercentage() != null
						&& educationalWeightageDefenitionTO
								.getWeightagePercentage().length() > 0) {
					docChecklist.setWeightagePercentage(new BigDecimal(
							educationalWeightageDefenitionTO
									.getWeightagePercentage()));
				}

				session.update(docChecklist);

			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in submitEducationalWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of submitEducationalWeightage of WeightageEntryTransactionImpl class.");
	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#submitInterviewWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void submitInterviewWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into submitInterviewWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<InterviewWeightageDefenitionTO> interviewList = weightagDefenitionForm
					.getInterviewWeightageList();
			Iterator<InterviewWeightageDefenitionTO> interviewWeightageIterator = interviewList
					.iterator();
			while (interviewWeightageIterator.hasNext()) {
				InterviewWeightageDefenitionTO interviewWeightageDefenitionTO = (InterviewWeightageDefenitionTO) interviewWeightageIterator
						.next();

				if(interviewWeightageDefenitionTO.getSubRoundList()!= null && interviewWeightageDefenitionTO.getSubRoundList().size() > 0){
					Iterator<InterviewSubroundDisplayTO> subRoundItr = interviewWeightageDefenitionTO.getSubRoundList().iterator();
					while (subRoundItr.hasNext()) {
						InterviewSubroundDisplayTO interviewSubroundDisplayTO = (InterviewSubroundDisplayTO) subRoundItr
								.next();
						InterviewSubRounds interviewSubRounds = (InterviewSubRounds) session.get(InterviewSubRounds.class, interviewSubroundDisplayTO.getSubroundId());
						
						
						Weightage weightage = new Weightage();
						
						weightage.setId(weightagDefenitionForm.getWeightageId());
						interviewSubRounds.setLastModifiedDate(new Date());
						interviewSubRounds.setModifiedBy(weightagDefenitionForm
								.getUserId());
						interviewSubRounds.setWeightage(weightage);
						if (interviewSubroundDisplayTO.getWeightagePercentage() != null
								&& interviewSubroundDisplayTO
										.getWeightagePercentage().length() > 0) {
							interviewSubRounds
									.setWeightagePercentage(new BigDecimal(
											interviewSubroundDisplayTO
													.getWeightagePercentage()));						
						
						}
						session.update(interviewSubRounds);
					}
				}
				else{
					InterviewProgramCourse interviewProgramCourse = (InterviewProgramCourse) session
							.get(InterviewProgramCourse.class,
									interviewWeightageDefenitionTO
											.getInterviewProgramCourseId());
	
					Weightage weightage = new Weightage();
	
					weightage.setId(weightagDefenitionForm.getWeightageId());
					interviewProgramCourse.setLastModifiedDate(new Date());
					interviewProgramCourse.setModifiedBy(weightagDefenitionForm
							.getUserId());
					interviewProgramCourse.setWeightage(weightage);
					if (interviewWeightageDefenitionTO.getWeightagePercentage() != null
							&& interviewWeightageDefenitionTO
									.getWeightagePercentage().length() > 0) {
						interviewProgramCourse
								.setWeightagePercentage(new BigDecimal(
										interviewWeightageDefenitionTO
												.getWeightagePercentage()));
					}
	
					session.update(interviewProgramCourse);
				}

			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in submitInterviewWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of submitInterviewWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateBelongsToWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateBelongsToWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateBelongsToWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<RuralUrbanWeightageTO> ruralUrbanWeightageList = weightagDefenitionForm
					.getRuralUrbanWeightageList();

			Iterator<RuralUrbanWeightageTO> ruralUrbanWeightageIterator = ruralUrbanWeightageList
					.iterator();

			while (ruralUrbanWeightageIterator.hasNext()) {
				RuralUrbanWeightageTO ruralUrbanWeightageTO = (RuralUrbanWeightageTO) ruralUrbanWeightageIterator
						.next();

				if (ruralUrbanWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class,
									ruralUrbanWeightageTO.getWeightageId());
					if (ruralUrbanWeightageTO.getWeightagePercentage() != null
							&& ruralUrbanWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition
								.setWeightage_1(new BigDecimal(
										ruralUrbanWeightageTO
												.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();
					if (ruralUrbanWeightageTO.getRuralUrban().equalsIgnoreCase(
							"Rural")) {
						weightageDefinition.setRuralUrban('R');
					} else {
						weightageDefinition.setRuralUrban('U');
					}

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (ruralUrbanWeightageTO.getWeightagePercentage() != null
							&& ruralUrbanWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition
								.setWeightage_1(new BigDecimal(
										ruralUrbanWeightageTO
												.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedDate(new Date());
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					session.save(weightageDefinition);
				}

			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateBelongsToWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateBelongsToWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateReligionSectionWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateReligionSectionWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateReligionSectionWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<SubReligionWeightageTO> casteCategoryWeightageList = weightagDefenitionForm
					.getReligionSectionWeightageList();

			Iterator<SubReligionWeightageTO> casteCategoryWeightageIterator = casteCategoryWeightageList
					.iterator();

			while (casteCategoryWeightageIterator.hasNext()) {
				SubReligionWeightageTO casteCategoryWeightageTO = (SubReligionWeightageTO) casteCategoryWeightageIterator
						.next();

				if (casteCategoryWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class,
									casteCategoryWeightageTO.getWeightageId());
					if (casteCategoryWeightageTO.getWeightagePercentage() != null
							&& casteCategoryWeightageTO
									.getWeightagePercentage().length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								casteCategoryWeightageTO
										.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();
					ReligionSection religionSection = new ReligionSection();
					religionSection.setId(casteCategoryWeightageTO
							.getReligionSectionId());

					weightageDefinition.setReligionSection(religionSection);

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (casteCategoryWeightageTO.getWeightagePercentage() != null
							&& casteCategoryWeightageTO
									.getWeightagePercentage().length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								casteCategoryWeightageTO
										.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateReligionSectionWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateReligionSectionWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateCasteWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateCasteWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateCasteWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<CasteWeightageTO> casteWeightageList = weightagDefenitionForm
					.getCasteWeightageList();

			Iterator<CasteWeightageTO> casteWeightageIterator = casteWeightageList
					.iterator();

			while (casteWeightageIterator.hasNext()) {
				CasteWeightageTO casteWeightageTO = (CasteWeightageTO) casteWeightageIterator
						.next();

				if (casteWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class, casteWeightageTO
									.getWeightageId());
					if (casteWeightageTO.getWeightagePercentage() != null
							&& casteWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								casteWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();
					Caste caste = new Caste();
					caste.setId(casteWeightageTO.getCasteId());

					weightageDefinition.setCaste(caste);

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (casteWeightageTO.getWeightagePercentage() != null
							&& casteWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								casteWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateCasteWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateCasteWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateCountryWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateCountryWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateCountryWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<CountryWeightageTO> countryWeightageList = weightagDefenitionForm
					.getCountryWeightageList();

			Iterator<CountryWeightageTO> countryWeightageIterator = countryWeightageList
					.iterator();

			while (countryWeightageIterator.hasNext()) {
				CountryWeightageTO casteWeightageTO = (CountryWeightageTO) countryWeightageIterator
						.next();
				if (casteWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class, casteWeightageTO
									.getWeightageId());
					if (casteWeightageTO.getWeightagePercentage() != null
							&& casteWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								casteWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();
					Country country = new Country();
					country.setId(casteWeightageTO.getCountryId());

					weightageDefinition.setCountry(country);

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (casteWeightageTO.getWeightagePercentage() != null
							&& casteWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								casteWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();	
			}
			log.info("error in updateCountryWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateCountryWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateGenderWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateGenderWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateGenderWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<GenderWeightageTO> genderWeightageList = weightagDefenitionForm
					.getGenderWeightageList();

			Iterator<GenderWeightageTO> genderWeightageIterator = genderWeightageList
					.iterator();
			while (genderWeightageIterator.hasNext()) {
				GenderWeightageTO genderWeightageTO = (GenderWeightageTO) genderWeightageIterator
						.next();

				if (genderWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class, genderWeightageTO
									.getWeightageId());
					if (genderWeightageTO.getWeightagePercentage() != null
							&& genderWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								genderWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();

					weightageDefinition.setGender(genderWeightageTO
							.getGetnder());

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (genderWeightageTO.getWeightagePercentage() != null
							&& genderWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								genderWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateGenderWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateGenderWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateInstituteWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateInstituteWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateInstituteWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<InstituteWeightageTO> instituteWeightageList = weightagDefenitionForm
					.getInstituteWeightageList();

			Iterator<InstituteWeightageTO> countryWeightageIterator = instituteWeightageList
					.iterator();

			while (countryWeightageIterator.hasNext()) {
				InstituteWeightageTO instituteWeightageTO = (InstituteWeightageTO) countryWeightageIterator
						.next();

				if (instituteWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class,
									instituteWeightageTO.getWeightageId());
					if (instituteWeightageTO.getWeightagePercentage() != null
							&& instituteWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								instituteWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();
					College college = new College();
					college.setId(instituteWeightageTO.getInstituteId());

					weightageDefinition.setCollege(college);

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (instituteWeightageTO.getWeightagePercentage() != null
							&& instituteWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								instituteWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateInstituteWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateInstituteWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateNationalityWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateNationalityWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateNationalityWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<NationalityWeightageTO> nationalityWeightageList = weightagDefenitionForm
					.getNationalityWeightageList();

			Iterator<NationalityWeightageTO> nationalityWeightageIterator = nationalityWeightageList
					.iterator();

			while (nationalityWeightageIterator.hasNext()) {
				NationalityWeightageTO nationalityWeightageTO = (NationalityWeightageTO) nationalityWeightageIterator
						.next();

				if (nationalityWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class,
									nationalityWeightageTO.getWeightageId());
					if (nationalityWeightageTO.getWeightagePercentage() != null
							&& nationalityWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition
								.setWeightage_1(new BigDecimal(
										nationalityWeightageTO
												.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();
					Nationality nationality = new Nationality();
					nationality
							.setId(nationalityWeightageTO.getNationalityId());

					weightageDefinition.setNationality(nationality);

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (nationalityWeightageTO.getWeightagePercentage() != null
							&& nationalityWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition
								.setWeightage_1(new BigDecimal(
										nationalityWeightageTO
												.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateNationalityWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateNationalityWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateReligionWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateReligionWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateReligionWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<ReligionWeightageTO> religionWeightageList = weightagDefenitionForm
					.getReligionWeightageList();

			Iterator<ReligionWeightageTO> religionWeightageIterator = religionWeightageList
					.iterator();

			while (religionWeightageIterator.hasNext()) {
				ReligionWeightageTO religionWeightageTO = (ReligionWeightageTO) religionWeightageIterator
						.next();

				if (religionWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class, religionWeightageTO
									.getWeightageId());
					if (religionWeightageTO.getWeightagePercentage() != null
							&& religionWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								religionWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();

					Religion religion = new Religion();
					religion.setId(religionWeightageTO.getReligionId());

					weightageDefinition.setReligion(religion);

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (religionWeightageTO.getWeightagePercentage() != null
							&& religionWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								religionWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateReligionWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateReligionWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateResidentCategoryWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateResidentCategoryWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateResidentCategoryWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<ResidentCategoryWeightageTO> residentCategoryWeightageList = weightagDefenitionForm
					.getResidentCategoryWeightageList();

			Iterator<ResidentCategoryWeightageTO> residentCategoryWeightageIterator = residentCategoryWeightageList
					.iterator();

			while (residentCategoryWeightageIterator.hasNext()) {
				ResidentCategoryWeightageTO residentCategoryWeightageTO = (ResidentCategoryWeightageTO) residentCategoryWeightageIterator
						.next();

				if (residentCategoryWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class,
									residentCategoryWeightageTO
											.getWeightageId());
					if (residentCategoryWeightageTO.getWeightagePercentage() != null
							&& residentCategoryWeightageTO
									.getWeightagePercentage().length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								residentCategoryWeightageTO
										.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();

					ResidentCategory residentCategory = new ResidentCategory();
					residentCategory.setId(residentCategoryWeightageTO
							.getReseidentCategoryId());

					weightageDefinition.setResidentCategory(residentCategory);

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (residentCategoryWeightageTO.getWeightagePercentage() != null
							&& residentCategoryWeightageTO
									.getWeightagePercentage().length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								residentCategoryWeightageTO
										.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateResidentCategoryWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateResidentCategoryWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateUniversityWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void updateUniversityWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateUniversityWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();

			List<UniversityWeightageTO> universityWeightageList = weightagDefenitionForm
					.getUniversityWeightageList();

			Iterator<UniversityWeightageTO> universityWeightageIterator = universityWeightageList
					.iterator();

			while (universityWeightageIterator.hasNext()) {
				UniversityWeightageTO universityWeightageTO = (UniversityWeightageTO) universityWeightageIterator
						.next();

				if (universityWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class,
									universityWeightageTO.getWeightageId());
					if (universityWeightageTO.getWeightagePercentage() != null
							&& universityWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition
								.setWeightage_1(new BigDecimal(
										universityWeightageTO
												.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();

					University university = new University();
					university.setId(universityWeightageTO.getUniversityId());

					weightageDefinition.setUniversity(university);

					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (universityWeightageTO.getWeightagePercentage() != null
							&& universityWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition
								.setWeightage_1(new BigDecimal(
										universityWeightageTO
												.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateUniversityWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateUniversityWeightage of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getInstituteList()
	 */
	public List<College> getInstituteList() throws ApplicationException {
		log.info("entering into getInstituteList of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<College> instituteList = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			String instituteString = " from College college where college.isActive = true";
			Query instituteQuery = session.createQuery(instituteString);
			instituteList = instituteQuery.list();
		} catch (Exception e) {
			log.info("error in getInstituteList of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of getInstituteList of WeightageEntryTransactionImpl class.");
		return instituteList;
	}

	public List<Country> getCountryList() throws ApplicationException {
		log.info("entering into getCountryList of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<Country> countryList = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			String instituteString = " from Country country where college.isActive = true";
			Query instituteQuery = session.createQuery(instituteString);
			countryList = instituteQuery.list();
		} catch (Exception e) {
			log.info("error in getCountryList of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of getCountryList of WeightageEntryTransactionImpl class.");
		return countryList;
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getEducationalPercentageList(int, int)
	 */
	public List getEducationalPercentageList(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getEducationalPercentageList of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<DocChecklist> educationalPercentageList = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String educationalWeightage = " from  EdnQualification ednQualification inner join ednQualification.docChecklist docCheckList with "
					+ "  docCheckList.course.id = :courseId "
					+ " and docCheckList.year = :year";
			Query educationalPercentageQuery = session
					.createQuery(educationalWeightage);
			educationalPercentageQuery.setInteger("courseId", courseId);
			educationalPercentageQuery.setInteger("year", year);
			educationalPercentageList = educationalPercentageQuery.list();
		} catch (Exception e) {
			log.info("error in getEducationalPercentageList of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getEducationalPercentageList of WeightageEntryTransactionImpl class.");
		return educationalPercentageList;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateEducationalWeightageAdjustedMarks(java.util.List, java.lang.String)
	 */
	public void updateEducationalWeightageAdjustedMarks(
			List<EducationalWeightageAdjustedTO> updatedEducationalWeightageList,
			String userId) throws ApplicationException {
		log.info("entering into updateEducationalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			Iterator<EducationalWeightageAdjustedTO> educationWeightageIterator = updatedEducationalWeightageList
					.iterator();
			while (educationWeightageIterator.hasNext()) {

				EducationalWeightageAdjustedTO educationalPercentageTO = (EducationalWeightageAdjustedTO) educationWeightageIterator
						.next();

				EdnQualification ednQualification = (EdnQualification) session
						.get(EdnQualification.class, educationalPercentageTO
								.getEducationQualificationId());
				ednQualification
						.setWeightageAdjustedMarks(educationalPercentageTO
								.getWeightageAdjustedMarks());
				ednQualification.setLastModifiedDate(new Date());
				ednQualification.setModifiedBy(userId);
				session.update(ednQualification);
				/*
				String ednQuery = "update EdnQualification edn set edn.weightageAdjustedMarks = " + educationalPercentageTO
				.getWeightageAdjustedMarks() +  " where edn.id = " + educationalPercentageTO.getEducationQualificationId();				
				Query query = session.createQuery(ednQuery);
				query.executeUpdate();*/
				
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateEducationalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateEducationalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getInterviewPercentageList(int, int)
	 */
	public List getInterviewPercentageList(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getInterviewPercentageList of WeightageEntryTransactionImpl class.");
		Session session = null;
		List interviewPercentageWeightage;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
*/
			session = HibernateUtil.getSession();
			
//			String interviewPercentage = " from InterviewResult interviewResult "
//					+ "  inner  join interviewResult.grade grade inner join interviewResult.interviewProgramCourse interviewProgramCourse "
//					+ "  with interviewProgramCourse.course.id =  :courseId"
//					+ "  and interviewProgramCourse.year = :year ";
			String interviewPercentage = " from InterviewResult interviewResult "
				+ "  inner join interviewResult.interviewProgramCourse interviewProgramCourse "
				+ "  with interviewProgramCourse.course.id =  :courseId"
				+ "  and interviewProgramCourse.year = :year ";

			
			Query interviewPercentageQuery = session
					.createQuery(interviewPercentage);
			interviewPercentageQuery.setInteger("courseId", courseId);
			interviewPercentageQuery.setInteger("year", year);
			interviewPercentageWeightage = interviewPercentageQuery.list();
		} catch (Exception e) {
			log.info("error in getInterviewPercentageList of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getInterviewPercentageList of WeightageEntryTransactionImpl class.");
		return interviewPercentageWeightage;
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateInterviewWeightageAdjustedMarks(java.util.List, java.lang.String)
	 */
	public void updateInterviewWeightageAdjustedMarks(
			List<InterviewWeightageAdjustedTO> updatedInterviewWeightageList,
			String userId) throws ApplicationException {
		log.info("entering into updateInterviewWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {

/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			Iterator<InterviewWeightageAdjustedTO> interviewWeightageIterator = updatedInterviewWeightageList
					.iterator();
			while (interviewWeightageIterator.hasNext()) {
				InterviewWeightageAdjustedTO interviewPercentageTO = (InterviewWeightageAdjustedTO) interviewWeightageIterator
						.next();
				InterviewResult ednQualification = (InterviewResult) session
						.get(InterviewResult.class, interviewPercentageTO
								.getInterviewResultId().getId());
				ednQualification
						.setWeightageAdjustedMarks(interviewPercentageTO
								.getWeightageAdjustedMarks());
				ednQualification.setLastModifiedDate(new Date());
				ednQualification.setModifiedBy(userId);
				session.update(ednQualification);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateInterviewWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateInterviewWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getTotalGeneralWeightage(int, int)
	 */
	public List getTotalGeneralWeightage(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getTotalGeneralWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List generalWeightage;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			

			String generalWeightageString = " select admAppln.id , "
					+ "  sum(weightageDefenition.weightage_1)"
					+ "  from AdmAppln admAppln inner join admAppln.personalData personalData ,"
					+ "  WeightageDefinition weightageDefenition inner join weightageDefenition.weightage  weightage"
					+ "  where weightage.course.id = :courseId"
					+ "   and weightage.year = :year"
					+ " and weightage.year = admAppln.appliedYear"
					+ "  and admAppln.courseBySelectedCourseId.id = weightage.course.id and("
					+ "  weightageDefenition.caste.id = personalData.caste.id"
					+ "   or weightageDefenition.residentCategory.id = personalData.residentCategory.id"
					+ "  or weightageDefenition.gender = personalData.gender"
					+ "    or weightageDefenition.country.id = personalData.countryByCountryId"
					+ "    or weightageDefenition.nationality.id = personalData.nationality.id"
					+ " or weightageDefenition.ruralUrban = personalData.ruralUrban "
					+ " or weightageDefenition.religion.id = personalData.religion.id "
					+ "  or weightageDefenition.religionSection.id = personalData.religionSection.id "
					+ "    )   group by admAppln.id";

			Query generalWeightageQuery = session
					.createQuery(generalWeightageString);
			generalWeightageQuery.setInteger("courseId", courseId);
			generalWeightageQuery.setInteger("year", year);
			generalWeightage = generalWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getTotalGeneralWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getTotalGeneralWeightage of WeightageEntryTransactionImpl class.");
		return generalWeightage;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getTotalGeneralalWeightage(int, int)
	 */
	public List getTotalGeneralInstitutionalWeightage(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getTotalGeneralInstitutionalWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List generalWeightage;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();

			String generalWeightageString = " select admAppln.id , "
					+ "  sum(weightageDefenition.weightage_1)"
					+ "  from AdmAppln admAppln inner join admAppln.personalData personalData join personalData.ednQualifications eq ,"
					+ "  WeightageDefinition weightageDefenition inner join weightageDefenition.weightage  weightage"
					+ "  where weightage.course.id = :courseId"
					+ "   and weightage.year = :year"
					+ " and weightage.year = admAppln.appliedYear"
					+ "  and admAppln.courseBySelectedCourseId.id = weightage.course.id and("
					+ "   weightageDefenition.university.id = eq.university.id"
					+ "   or weightageDefenition.college.id = eq.college.id"
					+ "    )   group by admAppln.id";

			Query generalWeightageQuery = session
					.createQuery(generalWeightageString);
			generalWeightageQuery.setInteger("courseId", courseId);
			generalWeightageQuery.setInteger("year", year);
			generalWeightage = generalWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getTotalGeneralInstitutionalWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getTotalGeneralInstitutionalWeightage of WeightageEntryTransactionImpl class.");
		return generalWeightage;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateGeneralWeightageAdjustedMarks(java.util.List, java.lang.String)
	 */
	public void updateGeneralWeightageAdjustedMarks(
			List<GeneralWeightageAdjustedTO> updatedgeneralWeightageList,
			String userId) throws ApplicationException {
		log.info("entering into updateGeneralWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			transaction = session.beginTransaction();

			Iterator<GeneralWeightageAdjustedTO> generalWeightageIterator = updatedgeneralWeightageList
					.iterator();

			while (generalWeightageIterator.hasNext()) {
				GeneralWeightageAdjustedTO generalWeightageAdjustedTO = (GeneralWeightageAdjustedTO) generalWeightageIterator
						.next();
				AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class,
						generalWeightageAdjustedTO.getAdmApplnId());
				admAppln.setWeightageAdjustedMarks(generalWeightageAdjustedTO
						.getWeightageAdjustedMarks());
				admAppln.setLastModifiedDate(new Date());
				admAppln.setModifiedBy(userId);
				session.update(admAppln);
				/*
				Date modifiedDate = new Date(); 
				String admQuery = "update AdmAppln adm set adm.weightageAdjustedMarks = :weightageAdjustedMarks"
					+ " where adm.id = :admApplnId";
					Query query = session.createQuery(admQuery);
					query.setBigDecimal("weightageAdjustedMarks", generalWeightageAdjustedTO.getWeightageAdjustedMarks());
					query.setInteger("admApplnId", generalWeightageAdjustedTO.getAdmApplnId());
					query.executeUpdate();	
					*/			
			}

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateGeneralWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateGeneralWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getTotalEducationalWeightageAdjustedMarks(int, int)
	 */
	public List getTotalEducationalWeightageAdjustedMarks(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getTotalEducationalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		List generalWeightage;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String totalEducationalWeightageAdjustedMarks = "select admAppln.id  ,sum(ednQualification.weightageAdjustedMarks)"
					+ " from EdnQualification ednQualification join ednQualification.personalData  personalData "
					+ "join personalData.admApplns admAppln with admAppln.courseBySelectedCourseId.id = :courseId "
					+ "	and admAppln.appliedYear = :year  group by ednQualification.personalData.id";

			Query generalWeightageQuery = session
					.createQuery(totalEducationalWeightageAdjustedMarks);
			generalWeightageQuery.setInteger("courseId", courseId);
			generalWeightageQuery.setInteger("year", year);
			generalWeightage = generalWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getTotalEducationalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getTotalEducationalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		return generalWeightage;
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getTotalPrerequisiteWeightageAdjustedMarks(int, int)
	 */
	public List getTotalPrerequisiteWeightageAdjustedMarks(int courseId,
			int year) throws ApplicationException {
		log.info("entering into getTotalPrerequisiteWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		List generalWeightage;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String totalEducationalWeightageAdjustedMarks = " select candidatePrerequisiteMarks.admAppln.id , "
					+ "  max(candidatePrerequisiteMarks.weightageAdjustedMarks) "
					+ "  from CandidatePrerequisiteMarks candidatePrerequisiteMarks inner join candidatePrerequisiteMarks.admAppln admAppln "
					+ "  with admAppln.courseBySelectedCourseId.id = :courseId and "
					+ "  admAppln.appliedYear = :year group by candidatePrerequisiteMarks.admAppln.id  ";

			Query generalWeightageQuery = session
					.createQuery(totalEducationalWeightageAdjustedMarks);
			generalWeightageQuery.setInteger("courseId", courseId);
			generalWeightageQuery.setInteger("year", year);
			generalWeightage = generalWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getTotalPrerequisiteWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getTotalPrerequisiteWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		return generalWeightage;
	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getTotalInterviewWeightageAdjustedMarks(int, int)
	 */
	public List getTotalInterviewWeightageAdjustedMarks(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getTotalInterviewWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		List generalWeightage;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String totalInterviewWeightageAdjustedMarks = "select intervierResult.admAppln.id , sum(intervierResult.weightageAdjustedMarks) from InterviewResult intervierResult "
					+ " right join intervierResult.admAppln admappln with"
					+ " admappln.courseBySelectedCourseId.id = :courseId and admappln.appliedYear = :year group by intervierResult.admAppln.id";

			Query generalWeightageQuery = session
					.createQuery(totalInterviewWeightageAdjustedMarks);
			generalWeightageQuery.setInteger("courseId", courseId);
			generalWeightageQuery.setInteger("year", year);
			generalWeightage = generalWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getTotalInterviewWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getTotalInterviewWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		return generalWeightage;
	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getTotalWeightageAdjustedMarks(int, int)
	 */
	public List getTotalWeightageAdjustedMarks(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getTotalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		List generalWeightage;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();

			String totalWeightage = " select admAppln.id , admAppln.weightageAdjustedMarks, weightage.interviewWeightage , weightage.educationWeightage , weightage.prerequisiteWeightage"
					+ "  from AdmAppln admAppln , "
					+ " Weightage weightage"
					+ " where "
					+ " weightage.course.id = admAppln.courseBySelectedCourseId.id"
					+ " and weightage.year = admAppln.appliedYear"
					+ " and weightage.year = :year"
					+ " and weightage.course.id = :courseId"
					+ " group by admAppln.id";

			Query generalWeightageQuery = session.createQuery(totalWeightage);
			generalWeightageQuery.setInteger("courseId", courseId);
			generalWeightageQuery.setInteger("year", year);
			generalWeightage = generalWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getTotalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getTotalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		return generalWeightage;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#updateTotalWeightageAdjustedMarks(java.util.List, java.lang.String)
	 */
	public void updateTotalWeightageAdjustedMarks(
			List<TotalWeightageAdjustedTO> updatedtotalWeightageList,
			String userId) throws ApplicationException {
		log.info("entering into updateTotalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			transaction = session.beginTransaction();

			Iterator<TotalWeightageAdjustedTO> totalWeightageIterator = updatedtotalWeightageList
					.iterator();

			while (totalWeightageIterator.hasNext()) {
				TotalWeightageAdjustedTO generalWeightageAdjustedTO = (TotalWeightageAdjustedTO) totalWeightageIterator
						.next();
				AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class,
						generalWeightageAdjustedTO.getAdmApplnId());
				admAppln.setTotalWeightage(generalWeightageAdjustedTO
						.getWeightageAdjustedMarks());
				admAppln.setLastModifiedDate(new Date());
				admAppln.setModifiedBy(userId);
				session.update(admAppln);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateTotalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateTotalWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getPrerequisiteWeightageTypes(int, int)
	 */
	@Override
	public List<CoursePrerequisite> getPrerequisiteWeightageTypes(int courseId,
			int year) throws ApplicationException {
		log.info("entering into getPrerequisiteWeightageTypes of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<CoursePrerequisite> coursePrerequisiteWeightageList = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String coursePrerequisiteWeightageQuery = " from CoursePrerequisite coursePrerequisite where  coursePrerequisite.course.id = :courseId and coursePrerequisite.isActive = true";
			Query prerequisiteWeightageQuery = session
					.createQuery(coursePrerequisiteWeightageQuery);
			prerequisiteWeightageQuery.setInteger("courseId", courseId);
			coursePrerequisiteWeightageList = prerequisiteWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getPrerequisiteWeightageTypes of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getPrerequisiteWeightageTypes of WeightageEntryTransactionImpl class.");
		return coursePrerequisiteWeightageList;

	}

	/** 
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#submitPrerequisiteWeightage(com.kp.cms.forms.admission.WeightageDefenitionForm)
	 */
	@Override
	public void submitPrerequisiteWeightage(
			WeightageDefenitionForm weightageDefenitionForm)
			throws ApplicationException {
		log.info("entering into submitPrerequisiteWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			List<CoursePrerequisiteWeightageTO> prerequisiteList = weightageDefenitionForm
					.getPrerequisiteWeightageList();

			Iterator<CoursePrerequisiteWeightageTO> prerequisiteWeightageIterator = prerequisiteList
					.iterator();
			while (prerequisiteWeightageIterator.hasNext()) {
				CoursePrerequisiteWeightageTO prerequisiteWeightageDefenitionTO = (CoursePrerequisiteWeightageTO) prerequisiteWeightageIterator
						.next();

				CoursePrerequisite coursePrerequisiteWeightage = (CoursePrerequisite) session
						.get(CoursePrerequisite.class,
								prerequisiteWeightageDefenitionTO
										.getCoursePrerequisiteid());
				Weightage weightage = new Weightage();

				weightage.setId(weightageDefenitionForm.getWeightageId());
				coursePrerequisiteWeightage.setLastModifiedDate(new Date());
				coursePrerequisiteWeightage
						.setModifiedBy(weightageDefenitionForm.getUserId());
				coursePrerequisiteWeightage.setWeightage(weightage);
				if (prerequisiteWeightageDefenitionTO.getWeightagePercentage() != null
						&& prerequisiteWeightageDefenitionTO
								.getWeightagePercentage().length() > 0) {
					coursePrerequisiteWeightage
							.setWeightagePercentage(new BigDecimal(
									prerequisiteWeightageDefenitionTO
											.getWeightagePercentage()));
				}

				session.update(coursePrerequisiteWeightage);

			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in submitPrerequisiteWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of submitPrerequisiteWeightage of WeightageEntryTransactionImpl class.");
	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#getPrerequisitePercentageList(int, int)
	 */
	@Override
	public List getPrerequisitePercentageList(int courseId, int year)
			throws ApplicationException {
		log.info("entering into getPrerequisitePercentageList of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<WeightageDefinition> prerequisiteWeightageList = null;

		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			String prerequisiteWeightage = " from CandidatePrerequisiteMarks candidatePrerequisiteMarks "
					+ " inner join candidatePrerequisiteMarks.prerequisite prerequisite "
					+ " inner join prerequisite.coursePrerequisites coursePrerequisites  "
					+ " where coursePrerequisites.course = :courseId and coursePrerequisites.isActive = true ";
			Query prerequisiteWeightageQuery = session
					.createQuery(prerequisiteWeightage);
			prerequisiteWeightageQuery.setInteger("courseId", courseId);
			prerequisiteWeightageList = prerequisiteWeightageQuery.list();
		} catch (Exception e) {
			log.info("error in getPrerequisitePercentageList of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getPrerequisitePercentageList of WeightageEntryTransactionImpl class.");
		return prerequisiteWeightageList;

	}

	/**
	 * @see com.kp.cms.transactions.admission.IWeightageEntryTransaction#persistPrerequisiteWeightageAdjustedMarks(java.util.List, java.lang.String)
	 */
	@Override
	public void persistPrerequisiteWeightageAdjustedMarks(
			List<CoursePrerequisiteWeightageTO> updatedPrerequisitePercentageList,
			String userId) throws ApplicationException {
		log.info("entering into persistPrerequisiteWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			
			transaction = session.beginTransaction();

			Iterator<CoursePrerequisiteWeightageTO> prerequisiteWeightageIterator = updatedPrerequisitePercentageList
					.iterator();
			while (prerequisiteWeightageIterator.hasNext()) {

				CoursePrerequisiteWeightageTO coursePrerequisiteWeightageTO = (CoursePrerequisiteWeightageTO) prerequisiteWeightageIterator
						.next();

				CandidatePrerequisiteMarks candidatePrerequisiteMarks = (CandidatePrerequisiteMarks) session
						.get(CandidatePrerequisiteMarks.class,
								coursePrerequisiteWeightageTO
										.getCandidatesPrerequisiteId());
				candidatePrerequisiteMarks
						.setWeightageAdjustedMarks(coursePrerequisiteWeightageTO
								.getWeightageAdjustedMarks());
				candidatePrerequisiteMarks.setLastModifiedDate(new Date());
				candidatePrerequisiteMarks.setModifiedBy(userId);
				session.update(candidatePrerequisiteMarks);
			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in persistPrerequisiteWeightageAdjustedMarks of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of persistPrerequisiteWeightageAdjustedMarks of WeightageEntryTransactionImpl class.");
	}
	
   // to get previous qualification
	
	public List getPreviousQualificationWeightage(Integer courseId,
			Integer year) throws ApplicationException {
		log.info("entering into getPreviousQualificationWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<WeightageDefinition> previousQualificationWeightageList = null;

		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			String previousQualificationWeightage = " select docTypeExams.id,docTypeExams.name,weightage_definition.id,weightage_definition.weightage_1 from WeightageDefinition weightage_definition "
					+ " right outer join weightage_definition.weightage weightage  with weightage.course.id = :courseId and weightage.year = :year"
					+ " right outer join "
					+ " weightage_definition.docTypeExams docTypeExams where  docTypeExams.isActive = true ";
			Query previousQualificationWeightageListQuery = session
					.createQuery(previousQualificationWeightage);
			previousQualificationWeightageListQuery.setInteger("courseId", courseId);
			previousQualificationWeightageListQuery.setInteger("year", year);
			previousQualificationWeightageList = previousQualificationWeightageListQuery.list();
		} catch (Exception e) {
			log.info("error in getPreviousQualificationWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getPreviousQualificationWeightage of WeightageEntryTransactionImpl class.");
		return previousQualificationWeightageList;

	}
	
	// To update of previous qualification
	
	public void updatePreviousQualificationWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updatePreviousQualificationWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			List<PreviousQualificationWeightageTO> qualificationWeightageList = weightagDefenitionForm
					.getPreviousQualificationWeightageList();

			Iterator<PreviousQualificationWeightageTO> qualificationWeightageIterator = qualificationWeightageList
					.iterator();

			while (qualificationWeightageIterator.hasNext()) {
				PreviousQualificationWeightageTO qualificationWeightageTO = (PreviousQualificationWeightageTO) qualificationWeightageIterator
						.next();

				if (qualificationWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class,
									qualificationWeightageTO.getWeightageId());
					if (qualificationWeightageTO.getWeightagePercentage() != null
							&& qualificationWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								qualificationWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();
					DocTypeExams previousQualification = new DocTypeExams();
					previousQualification.setId(qualificationWeightageTO.getDocTypeExamId());
					weightageDefinition.setDocTypeExams(previousQualification);
					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (qualificationWeightageTO.getWeightagePercentage() != null
							&& qualificationWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition.setWeightage_1(new BigDecimal(
								qualificationWeightageTO.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					weightageDefinition.setCreatedDate(new Date());
					session.save(weightageDefinition);
				}
			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updatePreviousQualificationWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updatePreviousQualificationWeightage of WeightageEntryTransactionImpl class.");
	}
	
	//to get weightageDefinition from weightageId and docTypeExamId
	
	public WeightageDefinition getWeightageDefenitionByWeightageIdAndDocTypeExamId(
			int weightageId,int docTypeExamId)
			throws ApplicationException {
		log.info("entering into getWeightageDefenitionByWeightageIdAndDocTypeExamId of WeightageEntryTransactionImpl class.");
		Session session = null;
		WeightageDefinition weightageDef = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();			
			String weightageDefQueryString = "from WeightageDefinition weightageDef"
					+ " where weightageDef.weightage.id = :weightageId "
					+ " and weightageDef.docTypeExams.id = :docTypeExamId";
			Query weightageDefinitionQuery = session.createQuery(weightageDefQueryString);
			weightageDefinitionQuery.setInteger("weightageId", weightageId);
					
			weightageDefinitionQuery.setInteger("docTypeExamId", docTypeExamId);
					
			weightageDef = (WeightageDefinition) weightageDefinitionQuery.uniqueResult();
		} catch (Exception e) {
			log.info("error in getWeightageDefenitionByWeightageIdAndDocTypeExamId of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getWeightageDefenitionByWeightageIdAndDocTypeExamId of WeightageEntryTransactionImpl class.");
		return weightageDef;
	}
	
	public void updateWorkExperienceWeightage(
			WeightageDefenitionForm weightagDefenitionForm)
			throws ApplicationException {
		log.info("entering into updateWorkExperienceWeightage of WeightageEntryTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			List<WorkExperienceWeightageTO> workExpWeightageList = weightagDefenitionForm
					.getWorkExperienceWeightageList();

			Iterator<WorkExperienceWeightageTO> workExpWeightageIterator = workExpWeightageList
					.iterator();

			while (workExpWeightageIterator.hasNext()) {
				WorkExperienceWeightageTO workExpWeightageTO = (WorkExperienceWeightageTO) workExpWeightageIterator
						.next();

				if (workExpWeightageTO.getWeightageId() != null) {

					WeightageDefinition weightageDefinition = (WeightageDefinition) session
							.get(WeightageDefinition.class,
									workExpWeightageTO.getWeightageId());
					if (workExpWeightageTO.getWeightagePercentage() != null
							&& workExpWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition
								.setWeightage_1(new BigDecimal(
										workExpWeightageTO
												.getWeightagePercentage()));
					}
					weightageDefinition.setLastModifiedDate(new Date());
					weightageDefinition.setModifiedBy(weightagDefenitionForm
							.getUserId());
					session.update(weightageDefinition);
				} else {

					WeightageDefinition weightageDefinition = new WeightageDefinition();
					if (workExpWeightageTO.getExperienceName().equalsIgnoreCase(
							"0 - 1 year")) {
						weightageDefinition.setWorkExp("0-1");
					}if (workExpWeightageTO.getExperienceName().equalsIgnoreCase(
							"1 - 2 years")) {
						weightageDefinition.setWorkExp("1-2");
					}if (workExpWeightageTO.getExperienceName().equalsIgnoreCase(
							"2 - 3 years")) {
						weightageDefinition.setWorkExp("2-3");
					}if (workExpWeightageTO.getExperienceName().equalsIgnoreCase(
							"3 - 4 years")) {
						weightageDefinition.setWorkExp("3-4");
					}if (workExpWeightageTO.getExperienceName().equalsIgnoreCase(
							"4 - 5 years")) {
						weightageDefinition.setWorkExp("4-5");
					}
					Weightage weightage = new Weightage();
					weightage.setId(weightagDefenitionForm.getWeightageId());
					weightageDefinition.setWeightage(weightage);
					if (workExpWeightageTO.getWeightagePercentage() != null
							&& workExpWeightageTO.getWeightagePercentage()
									.length() > 0) {
						weightageDefinition
								.setWeightage_1(new BigDecimal(
										workExpWeightageTO
												.getWeightagePercentage()));
					}
					weightageDefinition.setCreatedDate(new Date());
					weightageDefinition.setCreatedBy(weightagDefenitionForm
							.getUserId());
					session.save(weightageDefinition);
				}

			}

			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.info("error in updateWorkExperienceWeightage of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("exit of updateWorkExperienceWeightage of WeightageEntryTransactionImpl class.");
	}

	public List get0to1yearWorkExpWeightageDefinition(Integer courseId, Integer year)
	throws ApplicationException {
	log.info("entering into get0to1yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	Session session = null;
	List for0to1workExpWeightageDefinition = null;
	try {
/*		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String workExpWeightageDefinitionQuery = " from WeightageDefinition weightageDefenition "
				+ " inner join weightageDefenition.weightage weightage "
				+ " with weightage.course.id = :courseId"
				+ " and weightage.year = :year "
				+ " where weightageDefenition.workExp = '0-1' ";
	
		Query workExpWeightageQuery= session
				.createQuery(workExpWeightageDefinitionQuery );
		workExpWeightageQuery.setInteger("courseId", courseId);
		workExpWeightageQuery.setInteger("year", year);
		for0to1workExpWeightageDefinition = workExpWeightageQuery.list();
	} catch (Exception e) {
		log.info("error in get0to1yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.",e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
	}
	log.info("exit of get0to1yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	return for0to1workExpWeightageDefinition;
	
	}
	public List get1to2yearWorkExpWeightageDefinition(Integer courseId, Integer year)
	throws ApplicationException {
	log.info("entering into get1to2yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	Session session = null;
	List for1to2workExpWeightageDefinition = null;
	try {
/*		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String workExpWeightageDefinitionQuery = " from WeightageDefinition weightageDefenition "
				+ " inner join weightageDefenition.weightage weightage "
				+ " with weightage.course.id = :courseId"
				+ " and weightage.year = :year "
				+ " where weightageDefenition.workExp = '1-2' ";
	
		Query workExpWeightageQuery= session
				.createQuery(workExpWeightageDefinitionQuery );
		workExpWeightageQuery.setInteger("courseId", courseId);
		workExpWeightageQuery.setInteger("year", year);
		for1to2workExpWeightageDefinition = workExpWeightageQuery.list();
	} catch (Exception e) {
		log.info("error in get1to2yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.",e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
	}
	log.info("exit of get1to2yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	return for1to2workExpWeightageDefinition;
	
	}
	public List get2to3yearWorkExpWeightageDefinition(Integer courseId, Integer year)
	throws ApplicationException {
	log.info("entering into get2to3yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	Session session = null;
	List for2to3workExpWeightageDefinition = null;
	try {
/*		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String workExpWeightageDefinitionQuery = " from WeightageDefinition weightageDefenition "
				+ " inner join weightageDefenition.weightage weightage "
				+ " with weightage.course.id = :courseId"
				+ " and weightage.year = :year "
				+ " where weightageDefenition.workExp = '2-3' ";
	
		Query workExpWeightageQuery= session
				.createQuery(workExpWeightageDefinitionQuery );
		workExpWeightageQuery.setInteger("courseId", courseId);
		workExpWeightageQuery.setInteger("year", year);
		for2to3workExpWeightageDefinition = workExpWeightageQuery.list();
	} catch (Exception e) {
		log.info("error in get2to3yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.",e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
	}
	log.info("exit of get2to3yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	return for2to3workExpWeightageDefinition;
	
	}
	public List get3to4yearWorkExpWeightageDefinition(Integer courseId, Integer year)
	throws ApplicationException {
	log.info("entering into get3to4yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	Session session = null;
	List for3to4workExpWeightageDefinition = null;
	try {
/*		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String workExpWeightageDefinitionQuery = " from WeightageDefinition weightageDefenition "
				+ " inner join weightageDefenition.weightage weightage "
				+ " with weightage.course.id = :courseId"
				+ " and weightage.year = :year "
				+ " where weightageDefenition.workExp = '3-4' ";
	
		Query workExpWeightageQuery= session
				.createQuery(workExpWeightageDefinitionQuery );
		workExpWeightageQuery.setInteger("courseId", courseId);
		workExpWeightageQuery.setInteger("year", year);
		for3to4workExpWeightageDefinition = workExpWeightageQuery.list();
	} catch (Exception e) {
		log.info("error in get3to4yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.",e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
	}
	log.info("exit of get3to4yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	return for3to4workExpWeightageDefinition;
	
	}
	public List get4to5yearWorkExpWeightageDefinition(Integer courseId, Integer year)
	throws ApplicationException {
	log.info("entering into get4to5yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	Session session = null;
	List for4to5workExpWeightageDefinition = null;
	try {
/*		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
	
		String workExpWeightageDefinitionQuery = " from WeightageDefinition weightageDefenition "
				+ " inner join weightageDefenition.weightage weightage "
				+ " with weightage.course.id = :courseId"
				+ " and weightage.year = :year "
				+ " where weightageDefenition.workExp = '4-5' ";
	
		Query workExpWeightageQuery= session
				.createQuery(workExpWeightageDefinitionQuery );
		workExpWeightageQuery.setInteger("courseId", courseId);
		workExpWeightageQuery.setInteger("year", year);
		for4to5workExpWeightageDefinition  = workExpWeightageQuery.list();
	} catch (Exception e) {
		log.info("error in get4to5yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.",e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
	}
	log.info("exit of get4to5yearWorkExpWeightageDefinition of WeightageEntryTransactionImpl class.");
	return for4to5workExpWeightageDefinition ;
	
	}
	
	public List getTotalGeneralPreviousQualificationWeightage(int courseId, int year)
		throws ApplicationException {
	log.info("entering into getTotalGeneralPreviousQualificationWeightage of WeightageEntryTransactionImpl class.");
	Session session = null;
	List generalWeightage;
	try {
/*		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String generalWeightageString = " select admAppln.id , "
				+ "  sum(weightageDefenition.weightage_1)"
				+ "  from AdmAppln admAppln inner join admAppln.personalData personalData join personalData.ednQualifications eq ,"
				+ "  WeightageDefinition weightageDefenition inner join weightageDefenition.weightage  weightage"
				+ "  where weightage.course.id = :courseId"
				+ "  and weightage.year = :year"
				+ "  and weightage.year = admAppln.appliedYear"
				+ "  and admAppln.courseBySelectedCourseId.id = weightage.course.id and("
				+ "  weightageDefenition.docTypeExams.id = eq.docTypeExams.id"
				+ "    )   group by admAppln.id";
	
		Query generalWeightageQuery = session
				.createQuery(generalWeightageString);
		generalWeightageQuery.setInteger("courseId", courseId);
		generalWeightageQuery.setInteger("year", year);
		generalWeightage = generalWeightageQuery.list();
	} catch (Exception e) {
		log.info("error in getTotalGeneralPreviousQualificationWeightage of WeightageEntryTransactionImpl class.",e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
	}
	log.info("exit of getTotalGeneralPreviousQualificationWeightage of WeightageEntryTransactionImpl class.");
	return generalWeightage;

   }
    // getting work experience weightage after calculating noOf year experience for the applicant
	
	public List getWorkExperienceWeightage(int courseId, int year,String yearsOfExp)
	throws ApplicationException {
	log.info("entering into getWorkExperienceWeightage of WeightageEntryTransactionImpl class.");
	Session session = null;
	List generalWeightage;
	try {
/*		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String generalWeightageString = " select admAppln.id , "
				+ "  sum(weightageDefenition.weightage_1)"
				+ "  from AdmAppln admAppln,"
				+ "  WeightageDefinition weightageDefenition inner join weightageDefenition.weightage  weightage"
				+ "  where weightage.course.id = :courseId"
				+ "  and weightage.year = :year"
				+ "  and weightage.year = admAppln.appliedYear"
				+ "  and admAppln.courseBySelectedCourseId.id = weightage.course.id and("
				+ "  weightageDefenition.workExp= : yearsOfExp"
				+ "    )   group by admAppln.id";
	
		Query generalWeightageQuery = session
				.createQuery(generalWeightageString);
		generalWeightageQuery.setInteger("courseId", courseId);
		generalWeightageQuery.setInteger("year", year);
		generalWeightageQuery.setString("yearsOfExp",yearsOfExp);
		generalWeightage = generalWeightageQuery.list();
	} catch (Exception e) {
		log.info("error in getWorkExperienceWeightage of WeightageEntryTransactionImpl class.",e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
	}
	log.info("exit of getWorkExperienceWeightage of WeightageEntryTransactionImpl class.");
	return generalWeightage;

}		
	//to get weightage definition for work experience
	
	public List<WeightageDefinition> getweightageDefinitions(int courseId, int year)
	throws ApplicationException {
		
	log.info("entering into getweightageDefinitions of WeightageEntryTransactionImpl class.");
	Session session = null;
	List generalWeightage;
	try {
/*		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
			
		String weightageDefString = " select weightageDefinition  from WeightageDefinition weightageDefinition"
				+ "  inner join weightageDefinition.weightage weightage where weightage.course.id =:courseId "
				+ "  and weightage.year =:year"
				+ "  and weightageDefinition.workExp is not null";
		Query weightageDefQuery = session
				.createQuery(weightageDefString);
		weightageDefQuery.setInteger("courseId", courseId);
		weightageDefQuery.setInteger("year", year);
		generalWeightage = weightageDefQuery.list();
	} catch (Exception e) {
		log.info("error in getweightageDefinitions of WeightageEntryTransactionImpl class.",e);
		throw new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
//			session.close();
		}
	}
	log.info("exit of getweightageDefinitions of WeightageEntryTransactionImpl class.");
	return generalWeightage;

}		
	
	public List<ApplicantWorkExperience> getWorkExperienceList(int admApplnId) throws ApplicationException {
		log.info("entering into getWorkExperienceList() of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<ApplicantWorkExperience> workExperienceList = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String workExpString = " from ApplicantWorkExperience workexp"
					+ " where workexp.admApplnId = :admApplnId"
					+ " and workexp.fromDate is not null and workexp.toDate is not null";
			Query workExpQuery = session.createQuery(workExpString);
			workExpQuery.setInteger("admApplnId",admApplnId);
			workExperienceList = workExpQuery.list();
		} catch (Exception e) {
			log.info("error in getWorkExperienceList() of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {  
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getWorkExperienceList of WeightageEntryTransactionImpl class.");
		return workExperienceList;
	}
	
// to get all the AdmAppln Ids  having work experience for the particular year and course
	
	public List<Integer> getAdmApplnHavingWorkExp(int courseId,int year) throws ApplicationException {
		log.info("entering into getAdmApplnHavingWorkExp of WeightageEntryTransactionImpl class.");
		Session session = null;
		List<Integer> admApplnIdsHavingWorkExp = null;
		try {
/*			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String admApplnsString= "select distinct(adm.id) from ApplicantWorkExperience wexp join wexp.admApplnId adm "
					                +"where adm.course.id=:courseId "
									+"and adm.appliedYear=:year "
									+"and wexp.fromDate is not null and wexp.toDate is not null";
			Query admApplnQuery = session.createQuery(admApplnsString);
			admApplnQuery.setInteger("courseId", courseId);
			admApplnQuery.setInteger("year",year);
			
			admApplnIdsHavingWorkExp = admApplnQuery.list();
		} catch (Exception e) {
			log.info("error in getAdmApplnHavingWorkExp of WeightageEntryTransactionImpl class.",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {  
				session.flush();
//				session.close();
			}
		}
		log.info("exit of getAdmApplnHavingWorkExp of WeightageEntryTransactionImpl class.");
		return admApplnIdsHavingWorkExp;
	}
	
}