package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.EmpAgeofRetirement;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.EmpFunctionalArea;
import com.kp.cms.bo.admin.EmpIndustryType;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.EmpWorkType;
import com.kp.cms.bo.admin.EmployeeCategory;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.EventLocation;
import com.kp.cms.bo.admin.FeePaymentMode;
import com.kp.cms.bo.admin.HlComplaintType;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlLeaveType;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.InterviewStatus;
import com.kp.cms.bo.admin.InvCampus;
import com.kp.cms.bo.admin.InvCompany;
import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvUom;
import com.kp.cms.bo.admin.LeaveType;
import com.kp.cms.bo.admin.MeritSet;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Region;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.Services;
import com.kp.cms.bo.admin.Sports;
import com.kp.cms.bo.admin.SubjectCodeGroup;
import com.kp.cms.bo.admin.SubjectType;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.BookingRequirements;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.bo.exam.ExamGenBO;
import com.kp.cms.bo.exam.ExamSubjectTypeMasterBO;
import com.kp.cms.bo.exam.IExamGenBO;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.bo.examallotment.ClassGroup;
import com.kp.cms.bo.examallotment.ExamInviligatorExemption;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.LocationBo;
import com.kp.cms.bo.phd.PhdResearchPublication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.SingleFieldMasterForm;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class SingleFieldMasterTransactionImpl implements
		ISingleFieldMasterTransaction {
	private static final Log log = LogFactory
			.getLog(SingleFieldMasterTransactionImpl.class);
	public static volatile SingleFieldMasterTransactionImpl singleFieldMasterTransactionImpl = null;

	public static SingleFieldMasterTransactionImpl getInstance() {
		if (singleFieldMasterTransactionImpl == null) {
			singleFieldMasterTransactionImpl = new SingleFieldMasterTransactionImpl();
			return singleFieldMasterTransactionImpl;
		}
		return singleFieldMasterTransactionImpl;
	}

	/**
	 * This will retrieve all the caste records.
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Caste> getCasteMasterFields() throws Exception {
		Session session = null;
		List<Caste> result;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Caste a where isActive=1");
			result = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return result;
	}

	public List<EmployeeStreamBO> getEmployeeStream() throws Exception {
		Session session = null;
		List<EmployeeStreamBO> list;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmployeeStreamBO a where isActive=1");
			list = query.list();
			session.flush();
			//result = list;
		} catch (Exception e) {
			log.error("Error during Employee Stream Through...", e);
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all the countires.
	 */
	public List<Country> getCountryMasterFields() throws Exception {
		Session session = null;
		List<Country> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Country a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	
	public List<ExamSubjectTypeMasterBO> getSubjectTypeMasterFields() throws Exception {
		Session session = null;
		List<ExamSubjectTypeMasterBO> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ExamSubjectTypeMasterBO a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all the InterviewStatus records.
	 */
	public List<InterviewStatus> getAdmissionStatusFields() throws Exception {
		Session session = null;
		List<InterviewStatus> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewStatus a where isActive=1");
			 list= query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getAdmissionStatusFields...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the CourseScheme records.
	 */

	public List<CourseScheme> getCourseSchemeFields() throws Exception {
		Session session = null;
		List<CourseScheme> list ;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CourseScheme a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting CourseScheme...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the Prerequisite records.
	 */
	public List<Prerequisite> getPrerequisiteFields() throws Exception {
		Session session = null;
		List<Prerequisite> list ;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Prerequisite a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting CourseScheme...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the MotherTongue records.
	 */

	public List<MotherTongue> getMotherTongueFields() throws Exception {
		Session session = null;
		List<MotherTongue> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from MotherTongue a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting MotherTongue..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the Occupation records.
	 */

	public List<Occupation> getOccupationFields() throws Exception {
		Session session = null;
		List<Occupation> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Occupation a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting MotherTongue..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the DocType records.
	 */

	public List<DocType> getDocTypeFields() throws Exception {
		Session session = null;
		List result;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocType a where isActive=1");
			List<DocType> list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			result = list;
		} catch (Exception e) {
			log.error("Error during getting MotherTongue..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This method will retrieve all the Region records.
	 */
	public List<Region> getRegionFields() throws Exception {
		Session session = null;
		List<Region> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Region a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getRegionFields..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the ResidentCategory records.
	 */
	public List<ResidentCategory> getResidentCategoryFields() throws Exception {
		Session session = null;
		List<ResidentCategory> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ResidentCategory a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting ..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the MeritSet records.
	 */

	public List<MeritSet> getMeritSetFields() throws Exception {
		Session session = null;
		List<MeritSet> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from MeritSet a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting MeritSet..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the FeePaymentMode records.
	 */
	public List<FeePaymentMode> getFeePaymentModeFields() throws Exception {
		Session session = null;
		List<FeePaymentMode> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeePaymentMode a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting FeePaymentMode..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the Religion records.
	 */
	public List<Religion> getReligionFields() throws Exception {
		Session session = null;
		List<Religion> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Religion r where isActive=1 order by r.name");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting MeritSet..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the ProgramType records.
	 */
	public List<ProgramType> getProgramTypeFields() throws Exception {
		Session session = null;
		List<ProgramType> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ProgramType a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting MeritSet..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the University records.
	 */

	public List<University> getUniversityFields() throws Exception {
		Session session = null;
		List<University> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from University a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting University..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the Department records.
	 */
	public List<Department> getDepartmentFields() throws Exception {
		Session session = null;
		List<Department> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Department a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting Department..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This method will retrieve all the Industry Type records.
	 */
	public List<EmpIndustryType> getIndustryType() throws Exception {
		Session session = null;
		List<EmpIndustryType> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpIndustryType a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting industry type..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all the age of retirement.
	 */
	public List<EmpAgeofRetirement> getEmpAgeofRetirement() throws Exception {
		Session session = null;
		List<EmpAgeofRetirement> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpAgeofRetirement a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting EmpAgeofRetirement...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all the allowances.
	 */
	public List<EmpAllowance> getEmpAllowances() throws Exception {
		Session session = null;
		List<EmpAllowance> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpAllowance a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting getEmpAllowances...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all the job types.
	 */
	public List<EmpJobType> getEmpJobType() throws Exception {
		Session session = null;
		List<EmpJobType> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpJobType a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting getEmpJobType...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all the work types.
	 */
	public List<EmpWorkType> getEmpWorkType() throws Exception {
		Session session = null;
		List<EmpWorkType> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpWorkType a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting getEmpWorkType...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * Exams ******* Eligibility Criteria
	 * 
	 */

	public List<Object> getListExamGenBO(String boName_asInput)
			throws Exception {
		ExamGenImpl e = new ExamGenImpl();
		return e.select_ActiveOnly(e.convertBOToClassName(boName_asInput));
	}

	/**
	 * This will delete a record according to the Bo name.
	 * 
	 * @return true/false
	 * @throws Exception
	 */

	public boolean deleteSingleFieldMaster(int id, Boolean activate,
			String boName, SingleFieldMasterForm singleFieldMasterForm)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (boName.equalsIgnoreCase("Caste")) {
				Caste caste = (Caste) session.get(Caste.class, id);
				if (activate) {
					caste.setIsActive(true);
				} else {
					caste.setIsActive(false);
				}
				caste.setModifiedBy(singleFieldMasterForm.getUserId());
				caste.setLastModifiedDate(new Date());

				session.update(caste);
			} else if (boName.equalsIgnoreCase("ExamSubjectTypeMaster")) {
				ExamSubjectTypeMasterBO admissionStatus = (ExamSubjectTypeMasterBO) session
				.get(ExamSubjectTypeMasterBO.class, id);
		if (activate) {
			admissionStatus.setIsActive(true);
		} else {
			admissionStatus.setIsActive(false);
		}
		session.update(admissionStatus);
	}else if (boName.equalsIgnoreCase("Country")) {
				Country country = (Country) session.get(Country.class, id);
				if (activate) {
					country.setIsActive(true);
				} else {
					country.setIsActive(false);
				}
				country.setModifiedBy(singleFieldMasterForm.getUserId());
				country.setLastModifiedDate(new Date());

				session.update(country);
			}else if (boName.equalsIgnoreCase("Sports")) {
				Sports sports = (Sports) session.get(Sports.class, id);
				if (activate) {
					sports.setIsActive(true);
				} else {
					sports.setIsActive(false);
				}
				sports.setModifiedBy(singleFieldMasterForm.getUserId());
				sports.setLastModifiedDate(new Date());

				session.update(sports);
			} else if (boName.equalsIgnoreCase("AdmissionStatus")) {
				InterviewStatus admissionStatus = (InterviewStatus) session
						.get(InterviewStatus.class, id);
				if (activate) {
					admissionStatus.setIsActive(true);
				} else {
					admissionStatus.setIsActive(false);
				}
				session.update(admissionStatus);
			} else if (boName.equalsIgnoreCase("CourseScheme")) {
				CourseScheme courseScheme = (CourseScheme) session.get(
						CourseScheme.class, id);
				if (activate) {
					courseScheme.setIsActive(true);
				} else {
					courseScheme.setIsActive(false);
				}
				courseScheme.setModifiedBy(singleFieldMasterForm.getUserId());
				courseScheme.setLastModifiedDate(new Date());

				session.update(courseScheme);
			} else if (boName.equalsIgnoreCase("PreRequisite")) {
				Prerequisite prerequisite = (Prerequisite) session.get(
						Prerequisite.class, id);
				if (activate) {
					prerequisite.setIsActive(true);
				} else {
					prerequisite.setIsActive(false);
				}
				prerequisite.setModifiedBy(singleFieldMasterForm.getUserId());
				prerequisite.setLastModifiedDate(new Date());
				session.update(prerequisite);
			} else if (boName.equalsIgnoreCase("MotherTongue")) {
				MotherTongue motherTongue = (MotherTongue) session.get(
						MotherTongue.class, id);
				if (activate) {
					motherTongue.setIsActive(true);
				} else {
					motherTongue.setIsActive(false);
				}
				session.update(motherTongue);
			} else if (boName.equalsIgnoreCase("Occupation")) {
				Occupation occupation = (Occupation) session.get(
						Occupation.class, id);
				if (activate) {
					occupation.setIsActive(true);
				} else {
					occupation.setIsActive(false);
				}
				occupation.setModifiedBy(singleFieldMasterForm.getUserId());
				occupation.setLastModifiedDate(new Date());
				session.update(occupation);
			} else if (boName.equalsIgnoreCase("DocType")) {
				DocType docType = (DocType) session.get(DocType.class, id);
				if (activate) {
					docType.setIsActive(true);
				} else {
					docType.setIsActive(false);
				}
				docType.setModifiedBy(singleFieldMasterForm.getUserId());
				docType.setLastModifiedDate(new Date());
				session.update(docType);
			} else if (boName.equalsIgnoreCase("Region")) {
				Region region = (Region) session.get(Region.class, id);
				if (activate) {
					region.setIsActive(true);
				} else {
					region.setIsActive(false);
				}
				region.setModifiedBy(singleFieldMasterForm.getUserId());
				region.setLastModifiedDate(new Date());
				session.update(region);
			} else if (boName.equalsIgnoreCase("ResidentCategory")) {
				ResidentCategory residentCategory = (ResidentCategory) session
						.get(ResidentCategory.class, id);
				if (activate) {
					residentCategory.setIsActive(true);
				} else {
					residentCategory.setIsActive(false);
				}
				residentCategory.setLastModifiedDate(new Date());
				residentCategory.setModifiedBy(singleFieldMasterForm
						.getUserId());
				session.update(residentCategory);
			} else if (boName.equalsIgnoreCase("MeritSet")) {
				MeritSet meritSet = (MeritSet) session.get(MeritSet.class, id);
				if (activate) {
					meritSet.setIsActive(true);
				} else {
					meritSet.setIsActive(false);
				}
				meritSet.setModifiedBy(singleFieldMasterForm.getUserId());
				meritSet.setLastModifiedDate(new Date());
				session.update(meritSet);
			} else if (boName.equalsIgnoreCase("FeePaymentMode")) {
				FeePaymentMode feePaymentMode = (FeePaymentMode) session.get(
						FeePaymentMode.class, id);
				if (activate) {
					feePaymentMode.setIsActive(true);
				} else {
					feePaymentMode.setIsActive(false);
				}
				feePaymentMode.setModifiedBy(singleFieldMasterForm.getUserId());
				feePaymentMode.setLastModifiedDate(new Date());
				session.update(feePaymentMode);
			} else if (boName.equalsIgnoreCase("Religion")) {
				Religion religion = (Religion) session.get(Religion.class, id);
				if (activate) {
					religion.setIsActive(true);
				} else {
					religion.setIsActive(false);
				}
				religion.setModifiedBy(singleFieldMasterForm.getUserId());
				religion.setLastModifiedDate(new Date());
				session.update(religion);
			} else if (boName.equalsIgnoreCase("ProgramType")) {
				ProgramType programType = (ProgramType) session.get(
						ProgramType.class, id);
				if (activate) {
					programType.setIsActive(true);
				} else {
					programType.setIsActive(false);
				}
				programType.setModifiedBy(singleFieldMasterForm.getUserId());
				programType.setLastModifiedDate(new Date());
				session.update(programType);
			} else if (boName.equalsIgnoreCase("University")) {
				University university = (University) session.get(
						University.class, id);
				if (activate) {
					university.setIsActive(true);
				} else {
					university.setIsActive(false);
				}
				university.setModifiedBy(singleFieldMasterForm.getUserId());
				university.setLastModifiedDate(new Date());
				session.update(university);
			} else if (boName.equalsIgnoreCase("Department")) {
				Department department = (Department) session.get(
						Department.class, id);
				if (activate) {
					department.setIsActive(true);
				} else {
					department.setIsActive(false);
				}
				department.setModifiedBy(singleFieldMasterForm.getUserId());
				department.setLastModifiedDate(new Date());
				session.update(department);
			} else if (boName.equalsIgnoreCase("Roles")) {
				Roles roles = (Roles) session.get(Roles.class, id);
				if (activate) {
					roles.setIsActive(true);
				} else {
					roles.setIsActive(false);
				}
				roles.setModifiedBy(singleFieldMasterForm.getUserId());
				roles.setLastModifiedDate(new Date());
				session.update(roles);
			} else if (boName.equalsIgnoreCase("Designation")) {
				Designation designation = (Designation) session.get(
						Designation.class, id);
				if (activate) {
					designation.setIsActive(true);
				} else {
					designation.setIsActive(false);
				}
				designation.setModifiedBy(singleFieldMasterForm.getUserId());
				designation.setLastModifiedDate(new Date());
				session.update(designation);
			} else if (boName.equalsIgnoreCase("EmployeeCategory")) {
				EmployeeCategory employeeCategory = (EmployeeCategory) session
						.get(EmployeeCategory.class, id);
				if (activate) {
					employeeCategory.setIsActive(true);
				} else {
					employeeCategory.setIsActive(false);
				}
				employeeCategory.setModifiedBy(singleFieldMasterForm
						.getUserId());
				employeeCategory.setLastModifiedDate(new Date());
				session.update(employeeCategory);
			} else if (boName.equalsIgnoreCase("LeaveType")) {
				LeaveType leaveType = (LeaveType) session.get(LeaveType.class,
						id);
				if (activate) {
					leaveType.setIsActive(true);
				} else {
					leaveType.setIsActive(false);
				}
				leaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				leaveType.setLastModifiedDate(new Date());
				session.update(leaveType);
			} else if (boName.equalsIgnoreCase("Nationality")) {
				Nationality nationality = (Nationality) session.get(
						Nationality.class, id);
				if (activate) {
					nationality.setIsActive(true);
				} else {
					nationality.setIsActive(false);
				}
				nationality.setModifiedBy(singleFieldMasterForm.getUserId());
				nationality.setLastModifiedDate(new Date());
				session.update(nationality);
			} else if (boName.equalsIgnoreCase("Income")) {
				Income income = (Income) session.get(Income.class, id);
				if (activate) {
					income.setIsActive(true);
				} else {
					income.setIsActive(false);
				}
				income.setModifiedBy(singleFieldMasterForm.getUserId());
				income.setLastModifiedDate(new Date());
				session.update(income);
			} else if (boName.equalsIgnoreCase("HlFacility")) {
				HlFacility hlFacility = (HlFacility) session.get(
						HlFacility.class, id);
				if (activate) {
					hlFacility.setIsActive(true);
				} else {
					hlFacility.setIsActive(false);
				}
				hlFacility.setModifiedBy(singleFieldMasterForm.getUserId());
				hlFacility.setLastModifiedDate(new Date());
				session.update(hlFacility);
			} else if (boName.equalsIgnoreCase("HlComplaintType")) {
				HlComplaintType hlComplaintType = (HlComplaintType) session
						.get(HlComplaintType.class, id);
				if (activate) {
					hlComplaintType.setIsActive(true);
				} else {
					hlComplaintType.setIsActive(false);
				}
				hlComplaintType
						.setModifiedBy(singleFieldMasterForm.getUserId());
				hlComplaintType.setLastModifiedDate(new Date());
				session.update(hlComplaintType);
			} else if (boName.equalsIgnoreCase("HlLeaveType")) {
				HlLeaveType hlLeaveType = (HlLeaveType) session.get(
						HlLeaveType.class, id);
				if (activate) {
					hlLeaveType.setIsActive(true);
				} else {
					hlLeaveType.setIsActive(false);
				}
				hlLeaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				hlLeaveType.setLastModifiedDate(new Date());
				session.update(hlLeaveType);
			} else if (boName.equalsIgnoreCase("InvUom")) {
				InvUom invUom = (InvUom) session.get(InvUom.class, id);
				if (activate) {
					invUom.setIsActive(true);
				} else {
					invUom.setIsActive(false);
				}
				invUom.setModifiedBy(singleFieldMasterForm.getUserId());
				invUom.setLastModifiedDate(new Date());
				session.update(invUom);
			} else if (boName.equalsIgnoreCase("InvUom")) {
				InvUom invUom = (InvUom) session.get(InvUom.class, id);
				if (activate) {
					invUom.setIsActive(true);
				} else {
					invUom.setIsActive(false);
				}
				invUom.setModifiedBy(singleFieldMasterForm.getUserId());
				invUom.setLastModifiedDate(new Date());
				session.update(invUom);
			} else if (boName.equalsIgnoreCase("InvLocation")) {
				InvLocation invLocation = (InvLocation) session.get(
						InvLocation.class, id);
				if (activate) {
					invLocation.setIsActive(true);
				} else {
					invLocation.setIsActive(false);
				}
				invLocation.setModifiedBy(singleFieldMasterForm.getUserId());
				invLocation.setLastModifiedDate(new Date());
				session.update(invLocation);
			} else if (boName.equalsIgnoreCase("InvItemCategory")) {
				InvItemCategory invItemCategory = (InvItemCategory) session
						.get(InvItemCategory.class, id);
				if (activate) {
					invItemCategory.setIsActive(true);
				} else {
					invItemCategory.setIsActive(false);
				}
				invItemCategory
						.setModifiedBy(singleFieldMasterForm.getUserId());
				invItemCategory.setLastModifiedDate(new Date());
				session.update(invItemCategory);
			} else if (boName.equalsIgnoreCase("EmpFunctionalArea")) {
				EmpFunctionalArea empFunctionalArea = (EmpFunctionalArea) session
						.get(EmpFunctionalArea.class, id);
				if (activate) {
					empFunctionalArea.setIsActive(true);
				} else {
					empFunctionalArea.setIsActive(false);
				}
				empFunctionalArea.setModifiedBy(singleFieldMasterForm
						.getUserId());
				empFunctionalArea.setLastModifiedDate(new Date());
				session.update(empFunctionalArea);
			} else if (boName.equalsIgnoreCase("EmpLeaveType")) {
				EmpLeaveType empLeaveType = (EmpLeaveType) session.get(
						EmpLeaveType.class, id);
				if (activate) {
					empLeaveType.setIsActive(true);
				} else {
					empLeaveType.setIsActive(false);
				}
				empLeaveType.setModifiedBy(singleFieldMasterForm.getUserId());
				empLeaveType.setLastModifiedDate(new Date());
				session.update(empLeaveType);
			} else if (boName.equalsIgnoreCase("EmpQualificationLevel")) {
				EmpQualificationLevel empQualificationLevel = (EmpQualificationLevel) session
						.get(EmpQualificationLevel.class, id);
				if (activate) {
					empQualificationLevel.setIsActive(true);
				} else {
					empQualificationLevel.setIsActive(false);
				}
				empQualificationLevel.setModifiedBy(singleFieldMasterForm
						.getUserId());
				empQualificationLevel.setLastModifiedDate(new Date());
				session.update(empQualificationLevel);
			} else if (boName.equalsIgnoreCase("EmpIndustryType")) {
				EmpIndustryType empIndustryType = (EmpIndustryType) session
						.get(EmpIndustryType.class, id);
				if (activate) {
					empIndustryType.setIsActive(true);
				} else {
					empIndustryType.setIsActive(false);
				}
				empIndustryType
						.setModifiedBy(singleFieldMasterForm.getUserId());
				empIndustryType.setLastModifiedDate(new Date());
				session.update(empIndustryType);
			} else if (boName.equalsIgnoreCase("EmpAgeofRetirement")) {
				EmpAgeofRetirement ageofRetirement = (EmpAgeofRetirement) session
						.get(EmpAgeofRetirement.class, id);
				if (activate) {
					ageofRetirement.setIsActive(true);
				} else {
					ageofRetirement.setIsActive(false);
				}
				ageofRetirement
						.setModifiedBy(singleFieldMasterForm.getUserId());
				ageofRetirement.setLastModifiedDate(new Date());
				session.update(ageofRetirement);
			} else if (boName.equalsIgnoreCase("EmpAllowance")) {
				EmpAllowance allowance = (EmpAllowance) session.get(
						EmpAllowance.class, id);
				if (activate) {
					allowance.setIsActive(true);
				} else {
					allowance.setIsActive(false);
				}
				allowance.setModifiedBy(singleFieldMasterForm.getUserId());
				allowance.setLastModifiedDate(new Date());
				session.update(allowance);
			} else if (boName.equalsIgnoreCase("EmpJobType")) {
				EmpJobType empJobType = (EmpJobType) session.get(
						EmpJobType.class, id);
				if (activate) {
					empJobType.setIsActive(true);
				} else {
					empJobType.setIsActive(false);
				}
				empJobType.setModifiedBy(singleFieldMasterForm.getUserId());
				empJobType.setLastModifiedDate(new Date());
				session.update(empJobType);
			} else if (boName.equalsIgnoreCase("EmpWorkType")) {
				EmpWorkType empWorkType = (EmpWorkType) session.get(
						EmpWorkType.class, id);
				if (activate) {
					empWorkType.setIsActive(true);
				} else {
					empWorkType.setIsActive(false);
				}
				empWorkType.setModifiedBy(singleFieldMasterForm.getUserId());
				empWorkType.setLastModifiedDate(new Date());
				session.update(empWorkType);
			} else if (boName.equalsIgnoreCase("PcBankAccNumber")) {
				PcBankAccNumber pcAccNumber = (PcBankAccNumber) session.get(
						PcBankAccNumber.class, id);
				if (activate) {
					pcAccNumber.setIsActive(true);
				} else {
					pcAccNumber.setIsActive(false);
				}
				pcAccNumber.setModifiedBy(singleFieldMasterForm.getUserId());
				pcAccNumber.setLastModifiedDate(new Date());
				session.update(pcAccNumber);
			} // Exam modules
			else if (boName.contains("_Exam_")) {
				ExamGenImpl e = new ExamGenImpl();
				IExamGenBO examGenBO = (IExamGenBO) session.get(e.getClass(e
						.convertBOToClassName(boName)), id);
				if (activate) {
					examGenBO.setIsActive(true);
				} else {
					examGenBO.setIsActive(false);
				}
				examGenBO.setModifiedBy(singleFieldMasterForm.getUserId());
				examGenBO.setLastModifiedDate(new Date());
				session.update(examGenBO);
			} else if (boName.equalsIgnoreCase("Stream")) {
				EmployeeStreamBO objBO = (EmployeeStreamBO) session.get(
						EmployeeStreamBO.class, id);
				if (activate) {
					objBO.setIsActive(true);
				} else {
					objBO.setIsActive(false);
				}
				objBO.setModifiedBy(singleFieldMasterForm.getUserId());
				objBO.setLastModifiedDate(new Date());

				session.update(objBO);
			} else if (boName.equalsIgnoreCase("EmployeeWorkLocation")) {
				EmployeeWorkLocationBO objBO = (EmployeeWorkLocationBO) session
						.get(EmployeeWorkLocationBO.class, id);
				if (activate) {
					objBO.setIsActive(true);
				} else {
					objBO.setIsActive(false);
				}
				objBO.setModifiedBy(singleFieldMasterForm.getUserId());
				objBO.setLastModifiedDate(new Date());

				session.update(objBO);
			} else if (boName.equalsIgnoreCase("EmployeeType")) {
				EmployeeTypeBO objBO = (EmployeeTypeBO) session.get(
						EmployeeTypeBO.class, id);
				if (activate) {
					objBO.setIsActive(true);
				} else {
					objBO.setIsActive(false);
				}
				objBO.setModifiedBy(singleFieldMasterForm.getUserId());
				objBO.setLastModifiedDate(new Date());

				session.update(objBO);
			}else if (boName.equalsIgnoreCase("CharacterAndConduct")) {
				CharacterAndConduct characterAndConduct = (CharacterAndConduct) session.get(CharacterAndConduct.class, id);
				if (activate) {
					characterAndConduct.setIsActive(true);
				} else {
					characterAndConduct.setIsActive(false);
				}
				characterAndConduct.setModifiedBy(singleFieldMasterForm.getUserId());
				characterAndConduct.setLastModifiedDate(new Date());

				session.update(characterAndConduct);
			}else if(boName.equalsIgnoreCase("SubjectAreaBO")){
				SubjectAreaBO subjectAreaBO = (SubjectAreaBO) session.get(SubjectAreaBO.class, id);
				if(activate){
					subjectAreaBO.setIsActive(true);
				}else {
					subjectAreaBO.setIsActive(false);
				}
				subjectAreaBO.setModifiedBy(singleFieldMasterForm.getUserId());
				subjectAreaBO.setLastModifiedDate(new Date());
				session.update(subjectAreaBO);
			}else if (boName.equalsIgnoreCase("EmpJobTitle")) {
				EmpJobTitle empJobTitle = (EmpJobTitle) session.get(EmpJobTitle.class, id);
				if(activate){
					empJobTitle.setIsActive(true);
				}else{
					empJobTitle.setIsActive(false);
				}
				session.update(empJobTitle);
			}else if (boName.equalsIgnoreCase("ApplicationStatus")){
				ApplicationStatus applicationStatus = (ApplicationStatus) session.get(ApplicationStatus.class, id);
				if(activate){
					applicationStatus.setIsActive(true);
				}else {
					applicationStatus.setIsActive(false);
				}
				session.update(applicationStatus);
			}
			else if (boName.equalsIgnoreCase("InvCampus")) {
				InvCampus invCampus = (InvCampus) session.get(InvCampus.class, id);
				if (activate) {
					invCampus.setIsActive(true);
				} else {
					invCampus.setIsActive(false);
				}
				invCampus.setModifiedBy(singleFieldMasterForm.getUserId());
				invCampus.setLastModifiedDate(new Date());
				session.update(invCampus);
			}else if (boName.equalsIgnoreCase("CCGroup")) {
				CCGroup ccGroup = (CCGroup) session.get(CCGroup.class, id);
				if (activate) {
					ccGroup.setIsActive(true);
				} else {
					ccGroup.setIsActive(false);
				}
				ccGroup.setModifiedBy(singleFieldMasterForm.getUserId());
				ccGroup.setLastModifiedDate(new Date());
				session.update(ccGroup);
			}  else if (boName.equalsIgnoreCase("InvCompany")) {
				InvCompany invCompany = (InvCompany) session.get(InvCompany.class, id);
				if (activate) {
					invCompany.setIsActive(true);
				} else {
					invCompany.setIsActive(false);
				}
				invCompany.setModifiedBy(singleFieldMasterForm.getUserId());
				invCompany.setLastModifiedDate(new Date());
				session.update(invCompany);
			}else if (boName.equalsIgnoreCase("ApplicantFeedback")) {
				ApplicantFeedback applicantFeedback = (ApplicantFeedback) session.get(ApplicantFeedback.class, id);
				if (activate) {
					applicantFeedback.setIsActive(true);
				} else {
					applicantFeedback.setIsActive(false);
				}
				applicantFeedback.setModifiedBy(singleFieldMasterForm.getUserId());
				applicantFeedback.setLastModifiedDate(new Date());

				session.update(applicantFeedback);
			}else if (boName.equalsIgnoreCase("Discipline")) {
				DisciplineBo disciplineBo = (DisciplineBo) session.get(DisciplineBo.class, id);
				if (activate) {
					disciplineBo.setIsActive(true);
				} else {
					disciplineBo.setIsActive(false);
				}
				disciplineBo.setModifiedBy(singleFieldMasterForm.getUserId());
				disciplineBo.setLastModifiedDate(new Date());

				session.update(disciplineBo);
			}else if (boName.equalsIgnoreCase("PhdResearchPublication")) {
				PhdResearchPublication phdResearch = (PhdResearchPublication) session.get(PhdResearchPublication.class, id);
				if (activate) {
					phdResearch.setIsActive(true);
				} else {
					phdResearch.setIsActive(false);
				}
				phdResearch.setModifiedBy(singleFieldMasterForm.getUserId());
				phdResearch.setLastModifiedDate(new Date());

				session.update(phdResearch);
			}if (boName.equalsIgnoreCase("Location")) {
				LocationBo locationBo = (LocationBo) session.get(LocationBo.class, id);
				if (activate) {
					locationBo.setIsActive(true);
				} else {
					locationBo.setIsActive(false);
				}
				locationBo.setModifiedBy(singleFieldMasterForm.getUserId());
				locationBo.setLastModifiedDate(new Date());

				session.update(locationBo);
			}/*else if (boName.equalsIgnoreCase("FineCategory")) {
				FineCategoryBo fineCategoryBo = (FineCategoryBo) session.get(FineCategoryBo.class, id);
				if (activate) {
					fineCategoryBo.setIsActive(true);
				} else {
					fineCategoryBo.setIsActive(false);
				}
				fineCategoryBo.setModifiedBy(singleFieldMasterForm.getUserId());
				fineCategoryBo.setLastModifiedDate(new Date());

				session.update(fineCategoryBo);
			}*/
			else if (boName.equalsIgnoreCase("BlockDetails")) {
				BlockDetails block = (BlockDetails) session.get(BlockDetails.class, id);
				if (activate) {
					block.setIsActive(true);
				} else {
					block.setIsActive(false);
				}
				block.setModifiedBy(singleFieldMasterForm.getUserId());
				block.setLastModifiedDate(new Date());
				session.update(block);
			}else if (boName.equalsIgnoreCase("BookingRequirements")) {
				BookingRequirements requirements = (BookingRequirements) session.get(BookingRequirements.class, id);
				if (activate) {
					requirements.setIsActive(true);
				} else {
					requirements.setIsActive(false);
				}
				requirements.setModifiedBy(singleFieldMasterForm.getUserId());
				requirements.setLastModifiedDate(new Date());
				session.update(requirements);
			}else if (boName.equalsIgnoreCase("EventLocation")) {
				EventLocation eventLocation = (EventLocation) session.get(EventLocation.class, id);
				if (activate) {
					eventLocation.setIsActive(true);
				} else {
					eventLocation.setIsActive(false);
				}
				eventLocation.setModifiedBy(singleFieldMasterForm.getUserId());
				eventLocation.setLastModifiedDate(new Date());

				session.update(eventLocation);
			}    else if(boName.equalsIgnoreCase("ClassGroup")){
				ClassGroup classGroup = (ClassGroup)session.get(ClassGroup.class, id);
				if (activate) {
					classGroup.setIsActive(true);
				} else {
					classGroup.setIsActive(false);
				}
				classGroup.setModifiedBy(singleFieldMasterForm.getUserId());
				classGroup.setLastModifiedDate(new Date());

				session.update(classGroup);
			}else if (boName.equalsIgnoreCase("ExamInviligatorExemption")){
				ExamInviligatorExemption examInviligatorExemption = (ExamInviligatorExemption)session.get(ExamInviligatorExemption.class, id);
				if (activate) {
					examInviligatorExemption.setIsActive(true);
				} else {
					examInviligatorExemption.setIsActive(false);
				}
				examInviligatorExemption.setModifiedBy(singleFieldMasterForm.getUserId());
				examInviligatorExemption.setLastModifiedDate(new Date());

				session.update(examInviligatorExemption);
			}else if (boName.equalsIgnoreCase("SubjectCodeGroup")){
				SubjectCodeGroup subjectCodeGroup = (SubjectCodeGroup)session.get(SubjectCodeGroup.class, id);
				if (activate) {
					subjectCodeGroup.setIsActive(true);
				} else {
					subjectCodeGroup.setIsActive(false);
				}
				subjectCodeGroup.setModifiedBy(singleFieldMasterForm.getUserId());
				subjectCodeGroup.setLastModifiedDate(new Date());

				session.update(subjectCodeGroup);
			}else if (boName.equalsIgnoreCase("EmployeeSubject")){
				EmployeeSubject empSubject = (EmployeeSubject)session.get(EmployeeSubject.class, id);
				if (activate) {
					empSubject.setIsActive(true);
				} else {
					empSubject.setIsActive(false);
				}
				empSubject.setModifiedBy(singleFieldMasterForm.getUserId());
				empSubject.setLastModifiedDate(new Date());

				session.update(empSubject);
			}else if (boName.equalsIgnoreCase("Services")){
				Services services = (Services)session.get(Services.class, id);
				if (activate) {
					services.setIsActive(true);
				} else {
					services.setIsActive(false);
				}
				services.setModifiedBy(singleFieldMasterForm.getUserId());
				services.setLastModifiedDate(new Date());

				session.update(services);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during deleting single master data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null){
			   tx.rollback();
			}
			log.error("Error during deleting single master data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * duplication checking for Caste
	 */
	public Caste isCastDuplcated(Caste oldcaste) throws Exception {
		Session session = null;
		Caste caste;
		//Caste result = caste = new Caste();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Caste a where name = :name");
			query.setString("name", oldcaste.getName());
			caste = (Caste) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = caste;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return caste;
	}

	/**
	 * duplication checking for Country
	 */

	public Country isCountryDuplcated(Country oldcountry) throws Exception {
		Session session = null;
		Country country;
		//Country result = country = new Country();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Country a where name = :name");
			query.setString("name", oldcountry.getName());
			country = (Country) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = country;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return country;
	}
	
	public ExamSubjectTypeMasterBO isSubjectTypeDuplcated(ExamSubjectTypeMasterBO oldSubjectType) throws Exception {
		Session session = null;
		ExamSubjectTypeMasterBO subjectType;
		//Country result = country = new Country();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ExamSubjectTypeMasterBO a where name = :name");
			query.setString("name", oldSubjectType.getName());
			subjectType = (ExamSubjectTypeMasterBO) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = country;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return subjectType;
	}

	/**
	 * this will add new caste to the table
	 */

	public boolean addCaste(Caste caste, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equals("Add")) {
				session.save(caste);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(caste);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving caste data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving caste data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * this will add new country to the table
	 */

	public boolean addCountry(Country country, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(country);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(country);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving admitted Through data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving admitted Through data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	public boolean addSubjectType(ExamSubjectTypeMasterBO subjectType, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(subjectType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(subjectType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving admitted Through data..." + e);
			throw new BusinessException(e);
		} catch (Exception ex) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving admitted Through data..." + ex);
			throw new ApplicationException(ex);
		}
		return result;
	}


	// ////////////////////Admission status////////////////////////

	/**
	 * duplication checking for InterviewStatus
	 */

	public InterviewStatus isAdmissionStatusDuplcated(
			InterviewStatus oldadmissionStatus) throws Exception {
		Session session = null;
		InterviewStatus amissionStatus;
		//InterviewStatus result = amissionStatus = new InterviewStatus();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InterviewStatus a where name = :name");
			query.setString("name", oldadmissionStatus.getName());
			amissionStatus = (InterviewStatus) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = amissionStatus;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return amissionStatus;
	}

	/**
	 * this will add admissionStatus to the table
	 */
	public boolean addAdmissionStatus(InterviewStatus admissionStatus,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(admissionStatus);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(admissionStatus);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of admission status///////////////////
	// ////////////////////CourseScheme////////////////////////

	/**
	 * duplication checking for CourseScheme
	 */
	public CourseScheme isCourseSchemeDuplcated(CourseScheme oldcourseScheme)
			throws Exception {
		Session session = null;
		CourseScheme courseScheme;
		//CourseScheme result = courseScheme = new CourseScheme();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CourseScheme a where name = :name");
			query.setString("name", oldcourseScheme.getName());
			courseScheme = (CourseScheme) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = courseScheme;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return courseScheme;
	}

	/**
	 * this will add new courseScheme to the table
	 */
	public boolean addCourseScheme(CourseScheme courseScheme, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(courseScheme);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(courseScheme);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of course scheme///////////////////

	// ////////////////////Prerequisite////////////////////////

	/**
	 * duplication checking for Prerequisite
	 */
	public Prerequisite isPrerequisiteDuplcated(Prerequisite oldprerequisite)
			throws Exception {
		Session session = null;
		Prerequisite prerequisite;
		//Prerequisite result = prerequisite = new Prerequisite();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Prerequisite a where name = :name");
			query.setString("name", oldprerequisite.getName());
			prerequisite = (Prerequisite) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = prerequisite;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return prerequisite;
	}

	/**
	 * this will add pre-requisite to table
	 */
	public boolean addPrerequisite(Prerequisite prerequisite, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(prerequisite);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(prerequisite);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving pre requisite data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving pre requisite data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of Prerequisite///////////////////

	// ////////////////////MotherTongue////////////////////////

	/**
	 * duplication checking for MotherTongue
	 */
	public MotherTongue isMotherTongueDuplcated(MotherTongue oldmotherTongue)
			throws Exception {
		Session session = null;
		MotherTongue motherTongue;
		//MotherTongue result = motherTongue = new MotherTongue();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from MotherTongue a where name = :name");
			query.setString("name", oldmotherTongue.getName());
			motherTongue = (MotherTongue) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = motherTongue;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return motherTongue;
	}

	/**
	 * this will add new motherTongue to table
	 */
	public boolean addMotherTongue(MotherTongue motherTongue, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(motherTongue);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(motherTongue);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of MotherTongue///////////////////

	// ////////////////////Occupation////////////////////////

	/**
	 * duplication checking for Occupation
	 */
	public Occupation isOccupationDuplcated(Occupation oldoccupation)
			throws Exception {
		Session session = null;
		Occupation occupation;
		//Occupation result = occupation = new Occupation();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Occupation a where name = :name");
			query.setString("name", oldoccupation.getName());
			occupation = (Occupation) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = occupation;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return occupation;
	}

	/**
	 * this will add new occupation to the table
	 */
	public boolean addOccupation(Occupation occupation, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(occupation);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(occupation);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of Occupation///////////////////
	// ////////////////DocType/////////////////////////////

	/**
	 * duplication checking for DocType
	 */
	public DocType isDocTypeDuplcated(DocType olddocType) throws Exception {
		Session session = null;
		DocType docType;
		//DocType result = docType = new DocType();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from DocType a where name = :name");
			query.setString("name", olddocType.getName());
			docType = (DocType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = docType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return docType;
	}

	/**
	 * this will add new docType to table
	 */
	public boolean addDocType(DocType docType, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(docType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(docType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
		    	tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of DocType///////////////////
	// ////////////////Region/////////////////////////////

	/**
	 * duplication checking for Region
	 */
	public Region isRegionDuplcated(Region oldregion) throws Exception {
		Session session = null;
		Region region;
		//Region result = region = new Region();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Region a where name = :name");
			query.setString("name", oldregion.getName());
			region = (Region) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = region;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return region;
	}

	/**
	 * this will add new region
	 */
	public boolean addRegion(Region region, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(region);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(region);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of Region///////////////////
	// ////////////////ResidentCategory/////////////////////////////

	/**
	 * duplication checking for ResidentCategory
	 */
	public ResidentCategory isResidentCategoryDuplcated(
			ResidentCategory oldresidentCategory) throws Exception {
		Session session = null;
		ResidentCategory residentCategory;
		//ResidentCategory result = residentCategory = new ResidentCategory();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ResidentCategory a where name = :name");
			query.setString("name", oldresidentCategory.getName());
			residentCategory = (ResidentCategory) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = residentCategory;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return residentCategory;
	}

	/**
	 * this will add new residentCategory
	 */
	public boolean addResidentCategory(ResidentCategory residentCategory,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(residentCategory);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(residentCategory);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of ResidentCategory///////////////////
	// ////////////////MeritSet/////////////////////////////

	/**
	 * duplication checking for MeritSet
	 */
	public MeritSet isMeritSetDuplcated(MeritSet oldmeritSet) throws Exception {
		Session session = null;
		MeritSet meritSet;
		//MeritSet result = meritSet = new MeritSet();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from MeritSet a where name = :name");
			query.setString("name", oldmeritSet.getName());
			meritSet = (MeritSet) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = meritSet;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return meritSet;
	}

	/**
	 * this will add a new merit set to table
	 */
	public boolean addMeritSet(MeritSet meritSet, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(meritSet);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(meritSet);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of MeritSet///////////////////
	// ////////////////ProgramType/////////////////////////////

	/**
	 * duplication checking for program type
	 */
	public ProgramType isProgramTypeDuplcated(ProgramType oldprogramType)
			throws Exception {
		Session session = null;
		ProgramType programType;
		//ProgramType result = programType = new ProgramType();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from ProgramType a where name = :name");
			query.setString("name", oldprogramType.getName());
			programType = (ProgramType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = programType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return programType;
	}

	/**
	 * this will add new program type to table
	 */
	public boolean addProgramType(ProgramType programType, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(programType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(programType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of ProgramType///////////////////

	// ////////////////FeePaymentMode/////////////////////////////

	/**
	 * duplication checking for FeePaymentMode
	 */
	public FeePaymentMode isFeePaymentModeDuplcated(
			FeePaymentMode oldFeePaymentMode) throws Exception {
		Session session = null;
		FeePaymentMode feePaymentMode;
		//FeePaymentMode result = feePaymentMode = new FeePaymentMode();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeePaymentMode a where name = :name");
			query.setString("name", oldFeePaymentMode.getName());
			feePaymentMode = (FeePaymentMode) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = feePaymentMode;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return feePaymentMode;
	}

	/**
	 * this will add new feePaymentMode
	 */
	public boolean addFeePaymentMode(FeePaymentMode feePaymentMode, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(feePaymentMode);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(feePaymentMode);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of FeePaymentMode///////////////////

	// ////////////////Religion/////////////////////////////
	/**
	 * duplication checking for religion
	 */
	public Religion isReligionDuplcated(Religion oldReligion) throws Exception {
		Session session = null;
		Religion religion;
		//Religion result = religion = new Religion();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Religion a where name = :name");
			query.setString("name", oldReligion.getName());
			religion = (Religion) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = religion;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return religion;
	}

	/**
	 * this will add new religion to the table
	 */
	public boolean addReligion(Religion religion, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(religion);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(religion);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of Religion///////////////////
	// ////////////////Department/////////////////////////////

	/**
	 * duplication checking for department
	 */
	public Department isDepartmentDuplcated(Department oldDepartment)
			throws Exception {
		Session session = null;
		Department department;
		//Department result = department = new Department();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Department a where name = :name");
			query.setString("name", oldDepartment.getName());
			department = (Department) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = department;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return department;
	}

	/**
	 * this will add new department to the table
	 */
	public boolean addDepartment(Department department, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(department);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(department);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of Department///////////////////
	// ////////////////University/////////////////////////////

	/**
	 * duplication checking for University
	 */
	public University isUniversityDuplcated(University oldUniversity)
			throws Exception {
		Session session = null;
		University university;
		//University result = university = new University();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from University a where name = :name");
			query.setString("name", oldUniversity.getName());
			university = (University) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = university;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return university;
	}

	/**
	 * this will add new university to table
	 */
	public boolean addUniversity(University university, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(university);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(university);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Admission Status data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// //////////////end of University///////////////////

	// /start Roles Entry form

	/**
	 * duplication checking for Roles
	 */
	public Roles isRolesDuplcated(Roles oldRoles) throws Exception {
		Session session = null;
		Roles roles;
		//Roles result = roles = new Roles();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Roles a where name = :name");
			query.setString("name", oldRoles.getName());
			roles = (Roles) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = roles;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return roles;
	}

	/**
	 * this will add new roles to the table
	 */
	public boolean addRoles(Roles roles, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(roles);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(roles);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Roles data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving roles data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * this will get a list of roles from the table
	 */
	public List<Roles> getRolesFields() throws Exception {
		Session session = null;
		List<Roles> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Roles a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting Roles..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * this will get a list of designation from the table
	 */
	public List<Designation> getDesignationFields() throws Exception {
		Session session = null;
		List<Designation> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Designation a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting Designation..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * this will get a list of EmployeeCategory from table
	 */
	public List<EmployeeCategory> getEmployeeCategoryFields() throws Exception {
		Session session = null;
		List<EmployeeCategory> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmployeeCategory a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting EmployeeCategory..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * duplication checking for designation
	 */
	public Designation isDesignationDuplcated(Designation oldDesignation)
			throws Exception {
		Session session = null;
		Designation designation;
		//Designation result = designation = new Designation();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Designation a where name = :name");
			query.setString("name", oldDesignation.getName());
			designation = (Designation) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = designation;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return designation;
	}

	/**
	 * this will add new designation to the table
	 */
	public boolean addDesignation(Designation designation, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(designation);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(designation);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Roles data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving roles data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	// end of Roles Entry

	/**
	 * duplication checking for EmployeeCategory
	 */
	public EmployeeCategory isEmployeeCategoryDuplcated(
			EmployeeCategory oldEmployeeCategory) throws Exception {
		Session session = null;
		EmployeeCategory employeeCategory;
		//EmployeeCategory result = employeeCategory = new EmployeeCategory();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmployeeCategory a where name = :name");
			query.setString("name", oldEmployeeCategory.getName());
			employeeCategory = (EmployeeCategory) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = employeeCategory;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return employeeCategory;
	}

	/**
	 * this will add new employeeCategory to table
	 */
	public boolean addEmployeeCategory(EmployeeCategory employeeCategory,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(employeeCategory);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(employeeCategory);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving Employee Category data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving Employee Category data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * this will get a list of Leave Type from table
	 */
	public List<LeaveType> getLeaveTypeFields() throws Exception {
		Session session = null;
		List<LeaveType> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from LeaveType a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getLeaveTypeFields impl..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * duplication checking for EmployeeCategory
	 */
	public LeaveType isLeaveTypeDuplcated(LeaveType oldLeaveType)
			throws Exception {
		Session session = null;
		LeaveType leaveType;
		//LeaveType result = leaveType = new LeaveType();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from LeaveType a where name = :name");
			query.setString("name", oldLeaveType.getName());
			leaveType = (LeaveType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = leaveType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return leaveType;
	}

	/**
	 * this will add new leaveType to table
	 */
	public boolean addLeaveType(LeaveType leaveType, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(leaveType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(leaveType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error in addLeaveType" + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error in addLeaveType" + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * duplication checking for Nationality
	 */
	public Nationality isNationalityDuplcated(Nationality oldNationality)
			throws Exception {
		Session session = null;
		Nationality nationality;
		//Nationality result = nationality = new Nationality();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Nationality a where name = :name");
			query.setString("name", oldNationality.getName());
			nationality = (Nationality) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = nationality;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return nationality;
	}

	/**
	 * this will add new nationality to table
	 */
	public boolean addNationality(Nationality nationality, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(nationality);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(nationality);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error in isNationalityDuplcated" + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error in isNationalityDuplcated" + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * this will get a list of Nationality from table
	 */
	public List<Nationality> getNationalityFields() throws Exception {
		Session session = null;
		List<Nationality> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Nationality a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getNationalityFields impl..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * duplication checking for income
	 */
	public Income isIncomeDuplcated(Income oldIncome) throws Exception {
		Session session = null;
		Income income;
		//Income result = income = new Income();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Income i where incomeRange = :income");
			query.setString("income", oldIncome.getIncomeRange());
			income = (Income) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = income;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return income;
	}

	/**
	 * this will add new Income to the table
	 */

	public boolean addIncome(Income income, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(income);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(income);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving income..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving income..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will retrieve all the countires.
	 */
	public List<Income> getIncomeFields() throws Exception {
		Session session = null;
		List<Income> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Income a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getIncomeFields..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * this will add new facility to the table
	 */

	public boolean addHlFacility(HlFacility hlFacility, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(hlFacility);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(hlFacility);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving facility master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
		  	    tx.rollback();
			log.error("Error during saving facility master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will retrieve all the facility.
	 */
	public List<HlFacility> getHlFacilityFields() throws Exception {
		Session session = null;
		List<HlFacility> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlFacility a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getHlFacilityFields..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all the UOM.
	 */
	public List<InvUom> getUOMFields() throws Exception {
		Session session = null;
		List<InvUom> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvUom a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getUOMFields..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all the inventory locations.
	 */
	public List<InvLocation> getInventoryLocations() throws Exception {
		Session session = null;
		List<InvLocation> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InvLocation a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getInventoryLocations..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all item category.
	 */
	public List<InvItemCategory> getItemCategory() throws Exception {
		Session session = null;
		List<InvItemCategory> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InvItemCategory i where i.isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getItemCategory..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all functional area.
	 */
	public List<EmpFunctionalArea> getFunctionalArea() throws Exception {
		Session session = null;
		List<EmpFunctionalArea> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpFunctionalArea i where i.isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getFunctionalArea..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all emp leave type.
	 */
	public List<EmpLeaveType> getEmployeeLeaveType() throws Exception {
		Session session = null;
		List<EmpLeaveType> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpLeaveType i where i.isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getEmployeeLeaveType..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * This will retrieve all emp qualification.
	 */
	public List<EmpQualificationLevel> getEmpQualidication() throws Exception {
		Session session = null;
		List<EmpQualificationLevel> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpQualificationLevel i where i.isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getEmpQualidication..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * duplication checking for income
	 */
	public HlFacility isHlFacilityDuplcated(HlFacility oldHlFacility)
			throws Exception {
		Session session = null;
		HlFacility hlFacility;
		//HlFacility result = hlFacility = new HlFacility();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlFacility h where name = :name");
			query.setString("name", oldHlFacility.getName());
			hlFacility = (HlFacility) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = hlFacility;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return hlFacility;
	}

	/**
	 * This will retrieve all the HlComplaintType.
	 */
	public List<HlComplaintType> getHlComplaintTypeFields() throws Exception {
		Session session = null;
		List<HlComplaintType> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlComplaintType a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getHlComplaintTypeFields..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * duplication checking for HlComplaintType
	 */
	public HlComplaintType isHlComplaintTypeDuplcated(
			HlComplaintType oldHlComplaintType) throws Exception {
		Session session = null;
		HlComplaintType hlComplaintType;
		//HlComplaintType result = hlComplaintType = new HlComplaintType();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlComplaintType h where type = :name");
			query.setString("name", oldHlComplaintType.getType());
			hlComplaintType = (HlComplaintType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = hlComplaintType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return hlComplaintType;
	}

	/**
	 * this will add new HlComplaintType to the table
	 */

	public boolean addHlComplaintType(HlComplaintType hlHlComplaintTypee,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(hlHlComplaintTypee);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(hlHlComplaintTypee);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addHlComplaintType master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addHlComplaintType master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will retrieve all the HlLeaveType.
	 */
	public List<HlLeaveType> getHlLeaveTypeFields() throws Exception {
		Session session = null;
		List<HlLeaveType> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlLeaveType a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getHlLeaveTypeFields..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * duplication checking for HlLeaveType
	 */
	public HlLeaveType isHlLeaveTypeDuplcated(HlLeaveType oldHlLeaveType)
			throws Exception {
		Session session = null;
		HlLeaveType hlHlLeaveType;
		//HlLeaveType result = hlHlLeaveType = new HlLeaveType();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlLeaveType h where h.name = :name");
			query.setString("name", oldHlLeaveType.getName());
			hlHlLeaveType = (HlLeaveType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = hlHlLeaveType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return hlHlLeaveType;
	}

	/**
	 * this will add new HlLeaveType to the table
	 */

	public boolean addHlLeaveType(HlLeaveType hlHlLeaveType, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(hlHlLeaveType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(hlHlLeaveType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addHlLeaveType master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addHlLeaveType master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * duplication checking for UOM
	 */
	public InvUom isInvUomDuplcated(InvUom oldinvUom) throws Exception {
		Session session = null;
		InvUom invUom;
		//InvUom result = invUom = new InvUom();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InvUom i where name = :name");
			query.setString("name", oldinvUom.getName());
			invUom = (InvUom) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = invUom;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return invUom;
	}

	/**
	 * duplication checking for UOM
	 */
	public InvLocation isInvLocationDuplcated(InvLocation oldLocation)
			throws Exception {
		Session session = null;
		InvLocation invLocation;
		//InvLocation result = invLocation = new InvLocation();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InvLocation i where name = :name");
			query.setString("name", oldLocation.getName());
			invLocation = (InvLocation) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
		//	result = invLocation;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return invLocation;
	}

	/**
	 * duplication checking for UOM
	 */
	public InvItemCategory isInvItemCategoryDuplcated(
			InvItemCategory olditemCatg) throws Exception {
		Session session = null;
		InvItemCategory invCategory;
		//InvItemCategory result = invCategory = new InvItemCategory();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InvItemCategory i where name = :name");
			query.setString("name", olditemCatg.getName());
			invCategory = (InvItemCategory) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = invCategory;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return invCategory;
	}

	/**
	 * duplication checking for Functional Area
	 */
	public EmpFunctionalArea isFunctionalAreaDuplicated(
			EmpFunctionalArea oldArea) throws Exception {
		Session session = null;
		EmpFunctionalArea functionalArea;
		//EmpFunctionalArea result = functionalArea = new EmpFunctionalArea();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpFunctionalArea i where name = :name");
			query.setString("name", oldArea.getName());
			functionalArea = (EmpFunctionalArea) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = functionalArea;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return functionalArea;
	}

	/**
	 * 
	 * @param invUom
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addInvUom(InvUom invUom, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(invUom);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(invUom);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addInvUom master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addInvUom master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param invLocation
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addInvLocation(InvLocation invLocation, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(invLocation);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(invLocation);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addInvLocation master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addInvLocation master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param invItemCategory
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addInvItemCategory(InvItemCategory invItemCategory,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(invItemCategory);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(invItemCategory);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addInvLocation master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during saving addInvLocation master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param EmpFunctionalArea
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addFunctionalArea(EmpFunctionalArea functionalArea,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(functionalArea);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(functionalArea);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addFunctionalArea master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addFunctionalArea master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param EmpLeaveType
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addEmpLeaveType(EmpLeaveType empLeaveType, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(empLeaveType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(empLeaveType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addEmpLeaveType master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addEmpLeaveType master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * duplication checking for Leave Type
	 */
	public EmpLeaveType isEmpLeaveTypeDuplicated(EmpLeaveType oldLeaveType)
			throws Exception {
		Session session = null;
		EmpLeaveType empLeaveType;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpLeaveType i where name = :name");
			query.setString("name", oldLeaveType.getName());
			empLeaveType = (EmpLeaveType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return empLeaveType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * duplication checking for qualification level
	 */
	public EmpQualificationLevel isEmpQualificationDuplicated(
			EmpQualificationLevel oldEmpQualification) throws Exception {
		Session session = null;
		EmpQualificationLevel empQualificationLevel;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpQualificationLevel i where name = :name");
			query.setString("name", oldEmpQualification.getName());
			empQualificationLevel = (EmpQualificationLevel) query
					.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return empQualificationLevel;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * duplication checking for qualification level
	 */
	public EmpIndustryType isEmpIndustryTypeDuplicated(
			EmpIndustryType oldEmpIndustryType) throws Exception {
		Session session = null;
		EmpIndustryType empIndustryType;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpIndustryType i where name = :name");
			query.setString("name", oldEmpIndustryType.getName());
			empIndustryType = (EmpIndustryType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return empIndustryType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * duplication checking for EmpAllowance
	 */
	public EmpAllowance isEmpAllowanceTypeDuplicated(EmpAllowance olEmpAllowance)
			throws Exception {
		Session session = null;
		EmpAllowance empAllowance;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpAllowance i where name = :name");
			query.setString("name", olEmpAllowance.getName());
			empAllowance = (EmpAllowance) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return empAllowance;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * duplication checking for EmpJobType
	 */
	public EmpJobType isEmpJobTypeDuplicated(EmpJobType olEmpjobType)
			throws Exception {
		Session session = null;
		EmpJobType empJobType;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpJobType i where name = :name");
			query.setString("name", olEmpjobType.getName());
			empJobType = (EmpJobType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return empJobType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * duplication checking for EmpWorkType
	 */
	public EmpWorkType isEmpWorkTypeDuplicated(EmpWorkType olEmpWorkType)
			throws Exception {
		Session session = null;
		EmpWorkType empWorkType;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpWorkType i where name = :name");
			query.setString("name", olEmpWorkType.getName());
			empWorkType = (EmpWorkType) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return empWorkType;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * @param EmpQualificationLevel
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addEmpQualification(
			EmpQualificationLevel empQualificationLevel, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(empQualificationLevel);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(empQualificationLevel);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addEmpQualification master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addEmpQualification master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param EmpIndustryType
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addEmpIndustryType(EmpIndustryType empIndustryType,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(empIndustryType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(empIndustryType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving EmpIndustryType master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving EmpIndustryType master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 */
	public EmpAgeofRetirement isAgeOfRetirementExists(
			EmpAgeofRetirement oldageAgeofRetirement) throws Exception {
		Session session = null;
		EmpAgeofRetirement ageofRetirement;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpAgeofRetirement a where isActive = 1");
			ageofRetirement = (EmpAgeofRetirement) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return ageofRetirement;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * @param EmpAgeofRetirement
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addEmpAgeofRetirement(EmpAgeofRetirement ageofRetirement,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(ageofRetirement);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(ageofRetirement);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
		 	    tx.rollback();
			log.error("Error during saving EmpAgeofRetirement master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving EmpAgeofRetirement master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param EmpAllowance
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addEmpAllowances(EmpAllowance allowance, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(allowance);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(allowance);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log
					.error("Error during saving addEmpAgeofRetirement master..."
							+ e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
		 	   tx.rollback();
			log
					.error("Error during saving addEmpAgeofRetirement master..."
							+ e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param EmpJobType
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addEmpJobType(EmpJobType empJobType, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(empJobType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(empJobType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving EmpIndustryType master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving EmpIndustryType master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param EmpWorkType
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addEmpWorkType(EmpWorkType empWorkType, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(empWorkType);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(empWorkType);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addEmpWorkType master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addEmpWorkType master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/**
	 * This will retrieve all the work types.
	 */
	public List<PcBankAccNumber> getBankAccNo() throws Exception {
		Session session = null;
		List<PcBankAccNumber> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from PcBankAccNumber a where a.isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting getBankAccNo...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/**
	 * duplication checking for pcBankAccNumber
	 * 
	 * @return
	 */
	public PcBankAccNumber isPcBankAccNumberDuplicated(
			PcBankAccNumber oldPcBankAccNumber) throws Exception {
		Session session = null;
		PcBankAccNumber pcBankAccNumber;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from PcBankAccNumber i where accountNo = :name");
			query.setString("name", oldPcBankAccNumber.getAccountNo());
			pcBankAccNumber = (PcBankAccNumber) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			return pcBankAccNumber;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
	}

	public ExamGenBO isExamGenBODuplicated(ExamGenBO oldExamGenBO,
			String className) throws Exception {
		ExamGenImpl e = new ExamGenImpl();
		return e.select_Unique(className, oldExamGenBO.getName());
	}

	public boolean addUpdateExamGenBO(ExamGenBO examGenBO, String className,
			boolean add) throws Exception {
		ExamGenImpl e = new ExamGenImpl();
		return e.upsert_IExamGenBO(examGenBO, className, add);
	}

	/**
	 * 
	 * @param PcBankAccNumber
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addAccountNoEntry(PcBankAccNumber pcAccNumber, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(pcAccNumber);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(pcAccNumber);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
		    log.error("Error during saving addAccountNoEntry master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addAccountNoEntry master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	public EmployeeStreamBO isEmployeeStreamDuplcated(EmployeeStreamBO objBO)
			throws Exception {
		Session session = null;
		EmployeeStreamBO objStreamBO;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmployeeStreamBO i where i.name = :name");
			query.setString("name", objBO.getName());
			objStreamBO = (EmployeeStreamBO) query.uniqueResult();
			session.flush();
			return objStreamBO;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	public boolean addEmployeeStream(EmployeeStreamBO objBO, String mode)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(objBO);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(objBO);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addEmployeeStream master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addEmployeeStream master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	public List<EmployeeWorkLocationBO> getEmployeeWorkLocationStream()
			throws Exception {
		Session session = null;
		List<EmployeeWorkLocationBO> list;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmployeeWorkLocationBO a where isActive=1");
			list = query.list();
			session.flush();
			//result = list;
		} catch (Exception e) {
			log.error(
					"Error during get Employee Work LocationStream Through...",
					e);
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	public EmployeeWorkLocationBO isEmployeeWorkLocationDuplcated(
			EmployeeWorkLocationBO objBO) throws Exception {
		Session session = null;
		EmployeeWorkLocationBO objStreamBO;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmployeeWorkLocationBO i where i.name = :name");
			query.setString("name", objBO.getName());
			objStreamBO = (EmployeeWorkLocationBO) query.uniqueResult();
			session.flush();
			log.debug("impl: leaving isEmployeeWorkLocationDuplcated");
			return objStreamBO;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	public boolean addEmployeeWorkLocation(EmployeeWorkLocationBO objBO,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(objBO);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(objBO);
			}
			tx.commit();
			session.flush();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addEmployeeWorkLocation master..."
					+ e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
		    	tx.rollback();
			log.error("Error during saving addEmployeeWorkLocation master..."
					+ e);
			throw new ApplicationException(e);
		}
		return result;
	}

	public List<EmployeeTypeBO> getEmployeeType() throws Exception {
		Session session = null;
		List<EmployeeTypeBO> list;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmployeeTypeBO a where isActive=1");
			list = query.list();
			session.flush();
		//	result = list;
		} catch (Exception e) {
			log.error("Error during get get Employee Type Through...", e);
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	
	
	public EmployeeTypeBO isEmployeeTypeDuplcated(
			EmployeeTypeBO objBO) throws Exception {
		Session session = null;
		EmployeeTypeBO objStreamBO;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmployeeTypeBO i where i.name = :name");
			query.setString("name", objBO.getName());
			objStreamBO = (EmployeeTypeBO) query.uniqueResult();
			session.flush();
			return objStreamBO;
		} catch (Exception e) {
			log.error("Error during duplcation isEmployeeTypeDuplcated checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	

	public boolean addEmployeeType(EmployeeTypeBO objBO,
			String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(objBO);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(objBO);
			}
			tx.commit();
			session.flush();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addEmployeeType master..."
					+ e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addEmployeeType master..."
					+ e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	public List<CharacterAndConduct> getCharacterAndConduct() throws Exception{
		Session session = null;
		List<CharacterAndConduct> list;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CharacterAndConduct a where isActive=1");
			list = query.list();
			session.flush();
			//result = list;
		} catch (Exception e) {
			log.error(
					"Error during get CharacterAndConduct Through...",
					e);
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
		
	}
	
	public CharacterAndConduct isCharacterAndConductDuplcated(
			CharacterAndConduct characterAndConduct) throws Exception{
		Session session = null;
		CharacterAndConduct objCharacterAndConduct;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from CharacterAndConduct c where c.name = :name");
			query.setString("name", characterAndConduct.getName());
			objCharacterAndConduct = (CharacterAndConduct) query.uniqueResult();
			session.flush();
			return objCharacterAndConduct;
		} catch (Exception e) {
			log.error("Error during duplcation isCharacterAndConductDuplcated checking..." + e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	public boolean addCharacterAndConduct(
			CharacterAndConduct characterAndConduct, String mode) throws Exception{
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(characterAndConduct);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(characterAndConduct);
			}
			tx.commit();
			session.flush();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addCharacterAndConduct master..."
					+ e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addCharacterAndConduct master..."
					+ e);
			throw new ApplicationException(e);
		}
		return result;
	}
	/* 
	 * to get sports fields from database 
	 * 
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#getSportsMasterFields()
	 */
	public List<Sports> getSportsMasterFields() throws Exception {
		Session session = null;
		List<Sports> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Sports s where s.isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	/* 
	 *
	 * 
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#isSportsDuplcated(com.kp.cms.bo.admin.Sports)
	 */
	public Sports isSportsDuplcated(Sports oldsports) throws Exception {
		Session session = null;
		Sports sports;
		//Sports result = sports = new Sports();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from Sports a where name = :name");
			query.setString("name", oldsports.getName());
			sports = (Sports) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = sports;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return sports;
	}
	
	
	/* 
	 * 
	 * 
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#addSports(com.kp.cms.bo.admin.Sports, java.lang.String)
	 */
	public boolean addSports(Sports sports, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(sports);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(sports);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
		    	tx.rollback();
			log.error("Error during saving admitted Through data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving admitted Through data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#getSubjectArea()
	 */
	@Override
	public List<SubjectAreaBO> getSubjectArea() throws Exception {
		Session session=null;
		List<SubjectAreaBO> list;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from SubjectAreaBO s where s.isActive=1");
			list=query.list();
			session.flush();
			//result=list;
		}catch (Exception e) {
			log.error("Error during getting SubjectArea Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#addSubjectArea(com.kp.cms.bo.exam.SubjectAreaBO, java.lang.String)
	 */
	@Override
	public boolean addSubjectArea(SubjectAreaBO subjectAreaBO, String mode)
			throws Exception {
		Session session=null;
		Transaction tx=null;
		boolean result=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(subjectAreaBO);
			}else if(mode.equalsIgnoreCase("Edit")){
				session.update(subjectAreaBO);
			}
			tx.commit();
			session.flush();
			result = true;
		}catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving SubjectArea data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving SubjectArea data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.admin.ISingleFieldMasterTransaction#
	 * isSubjectAreaDuplicated(com.kp.cms.bo.exam.SubjectAreaBO)
	 */
	@Override
	public SubjectAreaBO isSubjectAreaDuplicated(SubjectAreaBO subjectAreaBO)
			throws Exception {
		Session session = null;
		SubjectAreaBO areaBO;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from SubjectAreaBO c where c.name = :name");
			query.setString("name", subjectAreaBO.getName());
			areaBO = (SubjectAreaBO) query.uniqueResult();
			session.flush();
			return areaBO;
		} catch (Exception e) {
			log
					.error("Error during duplcation isSubjectAreaDuplicated checking..."
							+ e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public EmpJobTitle isEmpJobTitleDuplicated(EmpJobTitle empJobTitle)
			throws Exception {
		Session session = null;
		EmpJobTitle eJobTitle;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from EmpJobTitle job where job.title = :name");
			query.setString("name", empJobTitle.getTitle());
			eJobTitle = (EmpJobTitle) query.uniqueResult();
			session.flush();
			return eJobTitle;
		} catch (Exception e) {
			log
					.error("Error during duplcation isEmpJobTitleDuplicated checking..."
							+ e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public boolean addEmpJobTitle(EmpJobTitle empJobTitle, String mode)
			throws Exception {
		Session session=null;
		Transaction tx=null;
		boolean result=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(empJobTitle);
			}else if(mode.equalsIgnoreCase("Edit")){
				session.update(empJobTitle);
			}
			tx.commit();
			session.flush();
			result = true;
		}catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving empJobTitle data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving empJobTitle data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}
	@Override
	public List<EmpJobTitle> getEmpJobTitle() throws Exception {
		Session session=null;
		List<EmpJobTitle> list;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpJobTitle t where t.isActive = 1");
			list=query.list();
			session.flush();
			//result=list;
		}catch (Exception e) {
			log.error("Error during getting EmpJobTitle Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#getApplicationStatus()
	 */
	@Override
	public List<ApplicationStatus> getApplicationStatus() throws Exception {
		Session session=null;
		List<ApplicationStatus> applicationStatus;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from ApplicationStatus appStatus where appStatus.isActive = 1");
			applicationStatus=query.list();
			session.flush();
			//list = applicationStatus;
		}catch (Exception e) {
			log.error("Error during getting ApplicationStatus Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return applicationStatus;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#isApplicationStatusDuplicated(com.kp.cms.bo.admin.ApplicationStatus)
	 */
	@Override
	public ApplicationStatus isApplicationStatusDuplicated(
			ApplicationStatus applicationStatus) throws Exception {
		Session session = null;
		ApplicationStatus status;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ApplicationStatus a where a.name= :name");
			query.setString("name", applicationStatus.getName());
			status = (ApplicationStatus) query.uniqueResult();
			session.flush();
			return status;
		}catch (Exception e) {
			log.error("Error during getting ApplicationStatus Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#addApplicationStatus(com.kp.cms.bo.admin.ApplicationStatus, java.lang.String)
	 */
	@Override
	public boolean addApplicationStatus(ApplicationStatus applicationStatus,
			String mode) throws Exception {
		boolean isAdded = false;
		Session session=null;
		Transaction tx=null;
		try{
			session  = HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(applicationStatus);
			}else if(mode.equalsIgnoreCase("Edit")){
				session.update(applicationStatus);
			}
			tx.commit();
			session.flush();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			if(tx!=null)
		    	tx.rollback();
			log.error("Error during saving ApplicationStatus data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving ApplicationStatus data..." + e);
			throw new ApplicationException(e);
		}
		return isAdded;
	}

	@Override
	public List<InvCampus> getInvCampus() throws Exception {
		Session session = null;
		List<InvCampus> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvCampus a where isActive=1");
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in getInvCampus..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	@Override
	public InvCampus isInvCampusDuplcated(InvCampus invCampuss) throws Exception {
		Session session = null;
		InvCampus invCampus;
		//InvCampus result = invCampus = new InvCampus();
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from InvCampus i where i.name = :name");
			query.setString("name", invCampuss.getName());
			invCampus = (InvCampus) query.uniqueResult();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = invCampus;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return invCampus;
	}

	@Override
	public boolean addInvCampus(InvCampus invCampus, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(invCampus);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(invCampus);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			    tx.rollback();
			log.error("Error during saving addInvCampus master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addInvCampus master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	public List getMasterEntryData(Class class1) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Criteria criteria=session.createCriteria(class1);
			list = criteria.list();
			return list;
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
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#isDuplicated(java.lang.Class, java.lang.String)
	 */
	@Override
	public Object isDuplicated(Class class1, String name) throws Exception {
		Session session = null;
		Object object = null;
		try {
			session = HibernateUtil.getSession();
			Criteria criteria=session.createCriteria(class1);
			criteria.add(Restrictions.eq("name",name));
			object = criteria.uniqueResult();
			return object;
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
	public boolean addMaster(Object object, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equals("Add")) {
				session.save(object);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(object);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving caste data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving caste data..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	public Object getMasterEntryDataById(Class class1, int id)
			throws Exception {
		Session session = null;
		Object obj= null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			Criteria criteria=session.createCriteria(class1);
			criteria.add(Restrictions.eq("id",id));
			obj= criteria.uniqueResult();
			return obj;
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
	public List<InvCompany> getInvCompany() throws Exception {
		Session session = null;
		List<InvCompany> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvCompany a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error in InvCompany..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#getCasteMasterFields()
	 */
	public List<ApplicantFeedback> getApplicantFeedback() throws Exception {
		Session session = null;
		List<ApplicantFeedback> list;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ApplicantFeedback a where isActive=1");
			list = query.list();
			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting Admitted Through...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#isInvCompanyDuplcated(com.kp.cms.bo.admin.InvCompany)
	 */
	@Override
	public InvCompany isInvCompanyDuplicated(InvCompany invCompany)
			throws Exception {
		Session session = null;
		InvCompany invComp;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvCompany i where i.name = :name");
			query.setString("name", invCompany.getName());
			invComp = (InvCompany) query.uniqueResult();
			
		} catch (Exception e) {
			log.error("Error during duplication checking..." + e);
			//session.flush();
			// session.close();
			throw new ApplicationException(e);
		}
		return invComp;
	}

	@Override
	public boolean addInvCompany(InvCompany invCompany, String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(invCompany);
			} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(invCompany);
			}
			tx.commit();
			session.flush();
			// session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
		   	   tx.rollback();
			log.error("Error during saving addInvCompany master..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addInvCompany master..." + e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#addCourseScheme(com.kp.cms.bo.admin.CourseScheme, java.lang.String)
	 */
public boolean addApplicantFeedback(ApplicantFeedback applicantFeedback, String mode)
	throws Exception {
Session session = null;
Transaction tx = null;
boolean result = false;
try {
	// SessionFactory sessionFactory = InitSessionFactory.getInstance();
	session = HibernateUtil.getSession();
	tx = session.beginTransaction();
	tx.begin();
	if (mode.equalsIgnoreCase("Add")) {
		session.save(applicantFeedback);
	} else if (mode.equalsIgnoreCase("Edit")) {
		session.update(applicantFeedback);
	}
	tx.commit();
	session.flush();
	// session.close();
	result = true;
} catch (ConstraintViolationException e) {
	if(tx!=null)
	   tx.rollback();
	log.error("Error during saving Admission Status data..." + e);
	throw new BusinessException(e);
} catch (Exception e) {
	if(tx!=null)
	   tx.rollback();
	log.error("Error during saving Admission Status data..." + e);
	throw new ApplicationException(e);
}
return result;
}
public ApplicantFeedback isApplicantFeedbackDuplcated(ApplicantFeedback oldapplicantFeedback)
throws Exception {
    Session session = null;
    ApplicantFeedback applicantFeedback;
    //ApplicantFeedback result = applicantFeedback = new ApplicantFeedback();
    try {
        // SessionFactory sessionFactory = InitSessionFactory.getInstance();
        session = HibernateUtil.getSession();
        Query query = session
		.createQuery("from ApplicantFeedback a where name = :name");
        query.setString("name", oldapplicantFeedback.getName());
        applicantFeedback = (ApplicantFeedback) query.uniqueResult();
        session.flush();
        // session.close();
        // sessionFactory.close();
        //result = applicantFeedback;
    } catch (Exception e) {
        log.error("Error during duplcation checking..." + e);
        //session.flush();
        // session.close();
        throw new ApplicationException(e);
      }
       return applicantFeedback;
}

@Override
public List<DisciplineBo> getDisciplines() throws Exception {
	Session session = null;
	List<DisciplineBo> list;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from DisciplineBo a where isActive=1");
		list = query.list();
		session.flush();
		// session.close();
		// sessionFactory.close();
		//result = list;
	} catch (Exception e) {
		log.error("Error during getting Admitted Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return list;
}

@Override
public DisciplineBo isDisciplineDuplcated(DisciplineBo oldDisciplineBo) throws Exception {
	Session session = null;
	DisciplineBo disciplineBo;
	//DisciplineBo result = disciplineBo = new DisciplineBo();
	try {
	session = HibernateUtil.getSession();
	Query query = session.createQuery("from DisciplineBo a where name = :name");
	query.setString("name", oldDisciplineBo.getName());
	disciplineBo = (DisciplineBo) query.uniqueResult();
	session.flush();
	//result = disciplineBo;
	} catch (Exception e) {
	log.error("Error during duplcation checking..." + e);
	//session.flush();
	throw new ApplicationException(e);
	}
	return disciplineBo;
}

@Override
public boolean addDisciplineDetails(DisciplineBo disciplineBo, String mode)
		throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		if (mode.equalsIgnoreCase("Add")) {
			session.save(disciplineBo);
		} else if (mode.equalsIgnoreCase("Edit")) {
			session.update(disciplineBo);
		}
		tx.commit();
		session.flush();
		// session.close();
		result = true;
	} catch (ConstraintViolationException e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving Admission Status data..." + e);
		throw new BusinessException(e);
	} catch (Exception e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving Admission Status data..." + e);
		throw new ApplicationException(e);
	}
	return result;
}

@Override
public List<LocationBo> getLocations() throws Exception {
	Session session = null;
	List<LocationBo> list;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from LocationBo a where isActive=1");
		list = query.list();
		session.flush();
		// session.close();
		// sessionFactory.close();
		//result = list;
	} catch (Exception e) {
		log.error("Error during getting Admitted Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return list;
}

@Override
public LocationBo isLocationDuplcated(LocationBo oldLocationBo) throws Exception {
	Session session = null;
	LocationBo locationBo;
	//LocationBo result = locationBo = new LocationBo();
	try {
	session = HibernateUtil.getSession();
	Query query = session.createQuery("from LocationBo a where name = :name");
	query.setString("name", oldLocationBo.getName());
	locationBo = (LocationBo) query.uniqueResult();
	session.flush();
	//result = locationBo;
	} catch (Exception e) {
	log.error("Error during duplcation checking..." + e);
	//session.flush();
	throw new ApplicationException(e);
	}
	return locationBo;
}

@Override
public boolean addLocationDetails(LocationBo locationBo, String mode)
		throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		if (mode.equalsIgnoreCase("Add")) {
			session.save(locationBo);
		} else if (mode.equalsIgnoreCase("Edit")) {
			session.update(locationBo);
		}
		tx.commit();
		session.flush();
		// session.close();
		result = true;
	} catch (ConstraintViolationException e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving Admission Status data..." + e);
		throw new BusinessException(e);
	} catch (Exception e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving Admission Status data..." + e);
		throw new ApplicationException(e);
	}
	return result;
}
/*
@Override
public List<FineCategoryBo> getFineCategory() throws Exception {
	log.debug("impl: inside getFineCategory");
	Session session = null;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from FineCategoryBo a where a.isActive=1");
		List<FineCategoryBo> list = query.list();
		session.flush();
		return list;
	} catch (Exception e) {
		log.error("Error during getting Admitted Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	
}

@Override
public FineCategoryBo isFineCategoryDuplcated(FineCategoryBo oldfineCategoryBo)
		throws Exception {
	log.debug("impl: inside isApplicantFeedbackDuplcated");
	Session session = null;
	FineCategoryBo fineCategoryBo;
	FineCategoryBo result = fineCategoryBo = new FineCategoryBo();
	try {
	session = HibernateUtil.getSession();
	Query query = session.createQuery("from FineCategoryBo a where name = :name");
	query.setString("name", oldfineCategoryBo.getName());
	fineCategoryBo = (FineCategoryBo) query.uniqueResult();
	session.flush();
	log.debug("impl: leaving isCourseSchemeDuplcated");
	result = fineCategoryBo;
	} catch (Exception e) {
	log.error("Error during duplcation checking..." + e);
	session.flush();
	throw new ApplicationException(e);
	}
	return result;
}

@Override
public boolean addFineCategoryDetails(FineCategoryBo fineCategoryBo, String mode)
		throws Exception {
	log.debug("impl: inside addFineCategoryDetails");
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		if (mode.equalsIgnoreCase("Add")) {
			session.save(fineCategoryBo);
		} else if (mode.equalsIgnoreCase("Edit")) {
			session.update(fineCategoryBo);
		}
		tx.commit();
		session.flush();
		// session.close();
		log.debug("impl: leaving addFineCategoryDetails");
		result = true;
	} catch (ConstraintViolationException e) {
		tx.rollback();
		log.error("Error during saving Admission Status data..." + e);
		throw new BusinessException(e);
	} catch (Exception e) {
		tx.rollback();
		log.error("Error during saving Admission Status data..." + e);
		throw new ApplicationException(e);
	}
	return result;
}*/

@Override
public List<PhdResearchPublication> getResearchPublication() throws Exception {
	Session session = null;
	List<PhdResearchPublication> list;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from PhdResearchPublication a where isActive=1");
		list = query.list();
		session.flush();
		// session.close();
		// sessionFactory.close();
		//result = list;
	} catch (Exception e) {
		log.error("Error during getResearchPublicationFields...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return list;
}

@Override
public PhdResearchPublication isResearchPublicationDuplcated(PhdResearchPublication researchPublication) throws Exception {
	Session session = null;
	PhdResearchPublication phdresurch;
	try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from PhdResearchPublication i where i.name = :name");
		query.setString("name", researchPublication.getName());
		phdresurch = (PhdResearchPublication) query.uniqueResult();
		
	} catch (Exception e) {
		log.error("Error during duplication checking..." + e);
		//session.flush();
		// session.close();
		throw new ApplicationException(e);
	}
	return phdresurch;
}

@Override
public boolean addResearchPublication(PhdResearchPublication researchPublication, String mode)throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		if (mode.equalsIgnoreCase("Add")) {
			session.save(researchPublication);
		} else if (mode.equalsIgnoreCase("Edit")) {
			session.update(researchPublication);
		}
		tx.commit();
		session.flush();
		result = true;
	} catch (ConstraintViolationException e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving add Research Publication..." + e);
		throw new BusinessException(e);
	} catch (Exception e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving add Research Publication..." + e);
		throw new ApplicationException(e);
	}
	return result;
}
@Override
public List<BlockDetails> getBlockDetails() throws Exception {
	Session session = null;
	List<BlockDetails> blockList = null;
	try{
		session = HibernateUtil.getSession();
		String quer = "from BlockDetails bd where bd.isActive = 1";
		Query query = session.createQuery(quer);
		blockList = query.list();
	}catch(Exception exception){
		log.error("Error while retrieving block details.." +exception);
		throw  new ApplicationException(exception);
	}
	return blockList;
}

@Override
public BlockDetails isBlockDetailsDuplicated(BlockDetails blocks)
		throws Exception {
	Session session = null;
	BlockDetails blockDetails;
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from BlockDetails b where b.blockName= :name");
		query.setString("name", blocks.getBlockName());
		blockDetails = (BlockDetails) query.uniqueResult();
		session.flush();
		return blockDetails;
	}catch (Exception e) {
		log.error("Error during getting BlockDetails Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
}

@Override
public BookingRequirements isBookingRequirementsDuplicated(BookingRequirements bookingRequirements)
		throws Exception {
	Session session = null;
	BookingRequirements requirements;
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from BookingRequirements b where b.name= :name");
		query.setString("name", bookingRequirements.getName());
		requirements = (BookingRequirements) query.uniqueResult();
		session.flush();
		return requirements;
	}catch (Exception e) {
		log.error("Error during getting Booking Requirements Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
}

@Override
public List<BookingRequirements> getBookingRequirements() throws Exception {
	Session session = null;
	List<BookingRequirements> bRequirementsList = null;
	try{
		session = HibernateUtil.getSession();
		String quer = "from BookingRequirements br where br.isActive = 1";
		Query query = session.createQuery(quer);
		bRequirementsList = query.list();
	}catch(Exception exception){
		log.error("Error while retrieving booking requirements details.." +exception);
		throw  new ApplicationException(exception);
	}
	return bRequirementsList;
}

@Override
public boolean addBlockDetails(BlockDetails blocks, String mode)
		throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		if (mode.equalsIgnoreCase("Add")) {
			session.save(blocks);
		} else if (mode.equalsIgnoreCase("Edit")) {
			session.update(blocks);
		}
		tx.commit();
		session.flush();
		// session.close();
		result = true;
	} catch (ConstraintViolationException e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving Block Details data..." + e);
		throw new BusinessException(e);
	} catch (Exception e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving Block Details data..." + e);
		throw new ApplicationException(e);
	}
	return result;
}

/* (non-Javadoc)
 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#addBookingRequirements(BookingRequirements, String)
 */
@Override
public boolean addBookingRequirements(BookingRequirements bookingRequirements,
		String mode) throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		tx.begin();
		if (mode.equalsIgnoreCase("Add")) {
			session.save(bookingRequirements);
		} else if (mode.equalsIgnoreCase("Edit")) {
			session.update(bookingRequirements);
		}
		tx.commit();
		session.flush();
		// session.close();
		result = true;
	} catch (ConstraintViolationException e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving Booking Requirements data..." + e);
		throw new BusinessException(e);
	} catch (Exception e) {
		if(tx!=null)
		   tx.rollback();
		log.error("Error during saving Booking Requirements data..." + e);
		throw new ApplicationException(e);
	}
	return result;
}


/* (non-Javadoc)
 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#isEventLocationDuplcated(com.kp.cms.bo.admin.EventLocation)
 */
public EventLocation isEventLocationDuplcated(EventLocation oldEventLocation) throws Exception {
	Session session = null;
	EventLocation eventLocation;
	//EventLocation result = eventLocation = new EventLocation();
	try {
	session = HibernateUtil.getSession();
	Query query = session.createQuery("from EventLocation a where name = :name");
	query.setString("name", oldEventLocation.getName());
	eventLocation = (EventLocation) query.uniqueResult();
	session.flush();
	//result = eventLocation;
	} catch (Exception e) {
	log.error("Error during duplcation checking..." + e);
	//session.flush();
	throw new ApplicationException(e);
	}
	return eventLocation;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#addEventLocation(com.kp.cms.bo.admin.EventLocation, java.lang.String)
 */
public boolean addEventLocation(EventLocation eventLocation, String mode)
throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean result = false;
	try {
	// SessionFactory sessionFactory = InitSessionFactory.getInstance();
	session = HibernateUtil.getSession();
	tx = session.beginTransaction();
	tx.begin();
	if (mode.equalsIgnoreCase("Add")) {
			session.save(eventLocation);
		} else if (mode.equalsIgnoreCase("Edit")) {
				session.update(eventLocation);
			}
				tx.commit();
				session.flush();
				// session.close();
				result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			   tx.rollback();
			log.error("Error during saving addEventLocation Status data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
		       tx.rollback();
		    log.error("Error during saving addEventLocation Status data..." + e);
		    throw new ApplicationException(e);
	}
		return result;
	}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admin.ISingleFieldMasterTransaction#getEventLocation()
 */
public List<EventLocation> getEventLocation() throws Exception {
	Session session = null;
	List<EventLocation> list;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		Query query = session
				.createQuery("from EventLocation a where isActive=1");
		list = query.list();

		session.flush();
		// session.close();
		// sessionFactory.close();
		//result = list;
	} catch (Exception e) {
		log.error("Error during getting Admitted Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return list;
}

@Override
public List<ClassGroup> getClassGroup() throws Exception {
	Session session = null;
	List<ClassGroup> list;
	try {
		session= HibernateUtil.getSession();
		Query query = session.createQuery("from ClassGroup c where c.isActive=1"); 
		list = query.list(); 
	}catch (Exception e) {
		log.error("Error during getting ClassGroup...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return list;
}

@Override
public ClassGroup isClassGroupDuplicated(ClassGroup classGroup)
		throws Exception {
	Session session = null;
	ClassGroup classGrp =null;
	try
	{
		session= HibernateUtil.getSession();
		Query query =session.createQuery("from ClassGroup c where c.isActive=1 and c.name='"+classGroup.getName()+"'");
		classGrp = (ClassGroup) query.uniqueResult();
	}catch (Exception e) {
		log.error("Error during getting ClassGroup...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return classGrp;
}

@Override
public boolean addClassGroupBO(ClassGroup classGroup, String mode)
		throws Exception {
	Session session = null;
	Transaction tx = null;
	boolean isAdded=false;
	try{
		session= HibernateUtil.getSession();
		tx=session.beginTransaction();
		tx.begin();
		if(mode.equalsIgnoreCase("Add")){
			session.save(classGroup);
		}
		else if(mode.equalsIgnoreCase("Edit")){
			session.update(classGroup);
		}
		tx.commit();
		session.flush();
		session.close();
		isAdded=true;
	}catch (Exception e) {
		log.error("Error during getting ClassGroup...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return isAdded;
}

@Override
public List<ExamInviligatorExemption> getExamInviligatorExemption()
		throws Exception {
	Session session= null;
	List<ExamInviligatorExemption>list;
	try{
		session=HibernateUtil.getSession();
		Query query= session.createQuery("from ExamInviligatorExemption E where E.isActive=1");
			list=query.list();
	}catch(Exception e){
		log.error("Error during getting ExamInviligatorExemption...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
		}

      return list;
}

public ExamInviligatorExemption isExamInviligatorExemptionDuplicated(ExamInviligatorExemption bo)
            throws Exception {
            Session session = null;
            ExamInviligatorExemption examInviligatorExemption =null;
        try
           {
            session= HibernateUtil.getSession();
              Query query =session.createQuery("from ExamInviligatorExemption E where E.isActive=1 and E.exemption='"+bo.getExemption()+"'");
              examInviligatorExemption = (ExamInviligatorExemption) query.uniqueResult();
               }catch (Exception e) {
                  log.error("Error during getting ExamInviligatorExemption...", e);
             if (session != null) {
	            session.flush();
	           // session.close();
            }
            throw new ApplicationException(e);
            }
        return examInviligatorExemption;
}
public boolean addExamInviligatorExemptionBO(ExamInviligatorExemption examInviligatorExemption, String mode)
            throws Exception {
           Session session = null;
             Transaction tx = null;
              boolean isAdded=false;
            try{
               session= HibernateUtil.getSession();
              tx=session.beginTransaction();
              tx.begin();
            if(mode.equalsIgnoreCase("Add")){
	            session.save(examInviligatorExemption);
}
else if(mode.equalsIgnoreCase("Edit")){
	session.update(examInviligatorExemption);
}
           tx.commit();
            session.flush();
            session.close();
            isAdded=true;
          }catch (Exception e) {
log.error("Error during getting ExamInviligatorExemption...", e);
if (session != null) {
	session.flush();
	// session.close();
}
throw new ApplicationException(e);
}
return isAdded;
}

@Override
public List<SubjectCodeGroup> getSubjectsCodeGroup() throws Exception {
	Session session= null;
	List<SubjectCodeGroup> list;
	try{
		session=HibernateUtil.getSession();
		Query query= session.createQuery("from SubjectCodeGroup code where code.isActive=1");
			list=query.list();
	}catch(Exception e){
		log.error("Error during getting getSubjectsCodeGroup...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
		}

      return list;
}

@Override
public SubjectCodeGroup isStudentCodeGroupDuplicated(
		SubjectCodeGroup subjectCodeGroup) throws Exception {
	  Session session = null;
	  SubjectCodeGroup codeGroup =null;
  try
     {
      session= HibernateUtil.getSession();
        Query query =session.createQuery("from SubjectCodeGroup code where code.isActive=1 and code.subjectsGroupName='"+subjectCodeGroup.getSubjectsGroupName()+"'");
        codeGroup = (SubjectCodeGroup) query.uniqueResult();
         }catch (Exception e) {
            log.error("Error during getting isStudentCodeGroupDuplicated...", e);
       if (session != null) {
          session.flush();
         // session.close();
      }
      throw new ApplicationException(e);
      }
  return codeGroup;
}

@Override
public boolean addStudentCodeGroup(SubjectCodeGroup subjectCodeGroup,
		String mode) throws Exception {
	  Session session = null;
      Transaction tx = null;
       boolean isAdded=false;
     try{
        session= HibernateUtil.getSession();
       tx=session.beginTransaction();
       tx.begin();
     if(mode.equalsIgnoreCase("Add")){
         session.save(subjectCodeGroup);
   }
    else if(mode.equalsIgnoreCase("Edit")){
     session.update(subjectCodeGroup);
   }
     tx.commit();
      session.flush();
      session.close();
      isAdded=true;
   }catch (Exception e) {
   log.error("Error during getting addStudentCodeGroup...", e);
   if (session != null) {
    session.flush();
     // session.close();
  }
  throw new ApplicationException(e);
  }
  return isAdded;
 }

@Override
public List<EmployeeSubject> getEmployeeSubjects() throws Exception {
	Session session=null;
	List<EmployeeSubject> list;
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmployeeSubject es where es.isActive=1");
		list=query.list();
		session.flush();
		//result=list;
	}catch (Exception e) {
		log.error("Error during getting getEmployeeSubjects Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return list;
}

@Override
public EmployeeSubject isEmployeeSubjectDuplicated(EmployeeSubject empSubject)
		throws Exception {
	Session session = null;
	EmployeeSubject employeeSubject =null;
try
   {
    session= HibernateUtil.getSession();
      Query query =session.createQuery("from EmployeeSubject sub where sub.subjectName='"+empSubject.getSubjectName()+"'");
      employeeSubject = (EmployeeSubject) query.uniqueResult();
       }catch (Exception e) {
          log.error("Error during getting isEmployeeSubjectDuplicated...", e);
     if (session != null) {
        session.flush();
       // session.close();
    }
    throw new ApplicationException(e);
    }
return employeeSubject;
}

@Override
public boolean addEmployeeSubject(EmployeeSubject empSubject, String mode)
		throws Exception {
	Session session = null;
    Transaction tx = null;
     boolean isAdded=false;
   try{
      session= HibernateUtil.getSession();
     tx=session.beginTransaction();
     tx.begin();
   if(mode.equalsIgnoreCase("Add")){
       session.save(empSubject);
 }
  else if(mode.equalsIgnoreCase("Edit")){
   session.update(empSubject);
 }
   tx.commit();
    session.flush();
    session.close();
    isAdded=true;
 }catch (Exception e) {
 log.error("Error during getting addEmployeeSubject...", e);
 if (session != null) {
  session.flush();
   // session.close();
}
throw new ApplicationException(e);
}
return isAdded;
}

@Override
public List<Services> getServices() throws Exception {
	Session session=null;
	List<Services> list;
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from Services s where s.isActive=1 order by s.name");
		list=query.list();
		session.flush();
		//result=list;
	}catch (Exception e) {
		log.error("Error during getting getServices Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	return list;
}

@Override
public Services isServicesDuplicated(Services services)
		throws Exception {
	Session session = null;
	Services service =null;
try
   {
    session= HibernateUtil.getSession();
      Query query =session.createQuery("from Services s where s.name='"+services.getName()+"'");
      service = (Services) query.uniqueResult();
       }catch (Exception e) {
          log.error("Error during getting isServicesDuplicated...", e);
     if (session != null) {
        session.flush();
       // session.close();
    }
    throw new ApplicationException(e);
    }
return service;
}

@Override
public boolean addServices(Services services, String mode)
		throws Exception {
	Session session = null;
    Transaction tx = null;
     boolean isAdded=false;
   try{
      session= HibernateUtil.getSession();
     tx=session.beginTransaction();
     tx.begin();
   if(mode.equalsIgnoreCase("Add")){
       session.save(services);
 }
  else if(mode.equalsIgnoreCase("Edit")){
   session.update(services);
 }
   tx.commit();
    session.flush();
    session.close();
    isAdded=true;
 }catch (Exception e) {
 log.error("Error during getting addEmployeeSubject...", e);
 if (session != null) {
  session.flush();
   // session.close();
}
throw new ApplicationException(e);
}
return isAdded;
}

}




