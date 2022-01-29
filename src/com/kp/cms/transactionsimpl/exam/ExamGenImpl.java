package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.ExamSecondLanguage;
import com.kp.cms.bo.admin.MeritSet;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamCourseGroupCodeBO;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamEligibilityCriteriaMasterBO;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamGenBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamInvigilationDutyBO;
import com.kp.cms.bo.exam.ExamMajorDepatmentCodeBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.ExamRevaluationTypeBO;
import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.bo.exam.ExamRoomTypeBO;
import com.kp.cms.bo.exam.ExamSecondLanguageMasterBO;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamSubjecRuleSettingsMultipleEvaluatorBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAssignmentBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAttendanceBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsMultipleAnsScriptBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsSubInternalBO;
import com.kp.cms.bo.exam.ExamSubjectTypeMasterBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.bo.exam.IExamGenBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamBlockUnblockForm;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;
import com.kp.cms.to.exam.ExamSubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.HibernateUtil;

/**
 * Dec 19, 2009 Created By 9Elements Team
 */

@SuppressWarnings("unchecked")
public class ExamGenImpl {

	private static final Log log = LogFactory.getLog(ExamGenImpl.class);
	private static volatile ExamGenImpl examGenImpl = null;

	public static ExamGenImpl getInstance() {
		if (examGenImpl == null) {
			examGenImpl = new ExamGenImpl();
		}
		return examGenImpl;
	}

	/**
	 * duplication checking && Reactivation
	 */
	public boolean isDuplicated_IExamGenBO(String name, Class className)
			throws Exception {
		Session session = null;
		boolean notDuplicate = true;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session.createCriteria(className);
		crit.add(Restrictions.eq("name", name));

		List<IExamGenBO> list = crit.list();

		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				IExamGenBO objIExamGenBO = (IExamGenBO) it.next();

				if (name.equalsIgnoreCase(objIExamGenBO.getName())
						&& objIExamGenBO.getIsActive() == false) {
					notDuplicate = false;
					throw new ReActivateException();
				} else if (name.equalsIgnoreCase(objIExamGenBO.getName())
						&& objIExamGenBO.getIsActive() == true) {
					notDuplicate = false;
					throw new DuplicateException();
				} else {
					notDuplicate = true;
				}
			}
		} else {
			notDuplicate = true;
		}
		session.flush();
		// session.close();
		return notDuplicate;
	}

	/*
	 * Insert a object into respective tables
	 */
	public int insert(Object obj) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int flag = 1;
		try {
			session.save(obj);
			tx.commit();
			// session.flush();
			session.close();
		} catch (Exception e) {
			flag = 0;
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}

	/*
	 * Insert list of objects into respective tables
	 */
	public int insert_List(ArrayList listBO) {
		
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		int flag = 0;
		try {
				for (Object obj : listBO) {
					flag = 1;
					session = sessionFactory.openSession();
					tx = session.beginTransaction();
					session.save(obj);
					tx.commit();
					session.flush();
					session.close();
					}
			
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}

	/*
	 * Update a hibernate object into respective table
	 */
	public int update(Object obj) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int flag = 1;
		try {

			session.update(obj);
			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			flag = 0;
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return flag;
	}

	/*
	 * Update a hibernate object into respective table
	 */
	public int update_List(ArrayList objList) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			for (Object obj : objList) {
				tx = session.beginTransaction();
				session.update(obj);
				tx.commit();
			}
			session.flush();
			session.close();
			return 1;
		} catch (Exception e) {

			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return 0;
		}
	}

	/*
	 * convert the mode from _Exam_xxxx to ExamxxxBO
	 */
	public String convertBOToClassName(String boName_asInput) {
		String className = boName_asInput.replace("_", "");
		if(className.equalsIgnoreCase("SubjectType")){
			return className;
		}else{
		className = className.concat("BO");
		}
		return className;
	}

	/*
	 * either update or insert examGenBo's
	 */
	public boolean upsert_IExamGenBO(IExamGenBO obj, String className,
			boolean add) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		boolean success = true;
		try {
			if (add) {
				session.save(getClassObj(className, obj));
			} else {
				session.update(getClassObj(className, obj));
			}
			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			success = false;
		}
		return success;
	}

	/*
	 * creating BO objects for single fields enteries
	 */
	private Object getClassObj(String className, IExamGenBO obj) {
		if ("ExamCourseGroupCodeBO".equalsIgnoreCase(className))
			return new ExamCourseGroupCodeBO(obj);

		else if ("ExamInternalExamTypeBO".equalsIgnoreCase(className))
			return new ExamInternalExamTypeBO(obj);

		else if ("ExamInvigilationDutyBO".equalsIgnoreCase(className))
			return new ExamInvigilationDutyBO(obj);

		else if ("ExamMajorDepatmentCodeBO".equalsIgnoreCase(className))
			return new ExamMajorDepatmentCodeBO(obj);

		else if ("ExamMultipleAnswerScriptMasterBO".equalsIgnoreCase(className))
			return new ExamMultipleAnswerScriptMasterBO(obj);

		else if ("ExamRevaluationTypeBO".equalsIgnoreCase(className))
			return new ExamRevaluationTypeBO(obj);

		else if ("ExamSecondLanguageMasterBO".equalsIgnoreCase(className))
			return new ExamSecondLanguageMasterBO(obj);

		else if ("ExamAssignmentTypeMasterBO".equalsIgnoreCase(className))
			return new ExamAssignmentTypeMasterBO(obj);
		else if ("ExamSubjectTypeMasterBO".equalsIgnoreCase(className))
			return new ExamSubjectTypeMasterBO(obj);
		else if ("ExamEligibilityCriteriaMasterBO".equalsIgnoreCase(className))
			return new ExamEligibilityCriteriaMasterBO(obj);

		return null;
	}

	/*
	 * creating BO Class for single fields enteries
	 */
	public Class getClass(String className) {
		if ("ExamCourseGroupCodeBO".equalsIgnoreCase(className))
			return ExamCourseGroupCodeBO.class;

		else if ("ExamInternalExamTypeBO".equalsIgnoreCase(className))
			return ExamInternalExamTypeBO.class;

		else if ("ExamInvigilationDutyBO".equalsIgnoreCase(className))
			return ExamInvigilationDutyBO.class;

		else if ("ExamMajorDepatmentCodeBO".equalsIgnoreCase(className))
			return ExamMajorDepatmentCodeBO.class;

		else if ("ExamMultipleAnswerScriptMasterBO".equalsIgnoreCase(className))
			return ExamMultipleAnswerScriptMasterBO.class;

		else if ("ExamRevaluationTypeBO".equalsIgnoreCase(className))
			return ExamRevaluationTypeBO.class;

		else if ("ExamSecondLanguageMasterBO".equalsIgnoreCase(className))
			return ExamSecondLanguageMasterBO.class;

		else if ("ExamAssignmentTypeMasterBO".equalsIgnoreCase(className))
			return ExamAssignmentTypeMasterBO.class;

		else if ("ExamRoomTypeBO".equalsIgnoreCase(className))
			return ExamRoomTypeBO.class;

		else if ("ExamRoomMasterBO".equalsIgnoreCase(className))
			return ExamRoomMasterBO.class;
		else if ("ExamSubjectTypeMasterBO".equalsIgnoreCase(className))
			return ExamSubjectTypeMasterBO.class;
		else if ("ExamEligibilityCriteriaMasterBO".equalsIgnoreCase(className))
			return ExamEligibilityCriteriaMasterBO.class;
		else if ("ExamProgramTypeUtilBO".equalsIgnoreCase(className))
			return ExamProgramTypeUtilBO.class;
		else if ("ExamDefinitionBO".equalsIgnoreCase(className))
			return ExamDefinitionBO.class;

		// Added for SUBJECT - RULE - SETTINGS
		else if ("ExamSubjectRuleSettingsSubInternalBO"
				.equalsIgnoreCase(className))
			return ExamSubjectRuleSettingsSubInternalBO.class;
		else if ("ExamSubjectRuleSettingsAssignmentBO"
				.equalsIgnoreCase(className))
			return ExamSubjectRuleSettingsAssignmentBO.class;
		else if ("ExamSubjectRuleSettingsAttendanceBO"
				.equalsIgnoreCase(className))
			return ExamSubjectRuleSettingsAttendanceBO.class;
		else if ("ExamSubjectRuleSettingsMultipleAnsScriptBO"
				.equalsIgnoreCase(className))
			return ExamSubjectRuleSettingsMultipleAnsScriptBO.class;
		else if ("ExamSubjecRuleSettingsMultipleEvaluatorBO"
				.equalsIgnoreCase(className))
			return ExamSubjecRuleSettingsMultipleEvaluatorBO.class;
		return null;
	}

	/*
	 * select active objects of Class name passed
	 */
	public List<Object> select_ActiveOnly(String className) {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(getClass(className));
			crit.add(Restrictions.eq("isActive", true));
			list = crit.list();

			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}

	/*
	 * select active objects of Class name passed
	 */
	public List<Object> select_ActiveOnly(Class className) {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			
			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("isActive", true));
			list = crit.list();
			//session.flush();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}

	public List<Object> select_All(Class className) {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(className);

			list = crit.list();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}
	
	public List<ExamSecondLanguage> getSecLanguage() {
		Session session = null;
		List<ExamSecondLanguage> result = null;
		ExamSecondLanguage examSecondLanguage;
		
		try {
//			SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			session = sessionFactory.openSession();
			session = HibernateUtil.getSession();//sessionFactory.openSession();
			Query query = session.createQuery("select id, name from ExamSecondLanguage where isActive = 1");
			List<Object[]> templist = query.list();
			List<ExamSecondLanguage> list = new ArrayList<ExamSecondLanguage>();
			
			Iterator<Object[]> itr = templist.iterator();
			if(templist != null ) {
				while (itr.hasNext()) {
					Object[] row = itr.next();
					examSecondLanguage = new ExamSecondLanguage();
					examSecondLanguage.setId((Integer)row[0]);
					examSecondLanguage.setName((String)row[1]);
					list.add(examSecondLanguage);
				}
			}
			session.flush();
//				session.close();
//				sessionFactory.close();
				result = list;
		} catch (Exception e) {
			log.error("Error during getting ExamSecondLanguageMasterBO..." , e);
			log.error(e.getMessage());
			//session.flush();
			//session.close();
//			throw new ApplicationException(e);
		}
		return result;
	}
	

	/*
	 * select unique ExamGenBO for Class Name passed
	 */
	public ExamGenBO select_Unique(String className, String name) {
		Session session = null;
		List<Object> list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(getClass(className));
			crit.add(Restrictions.eq("name", name));
			crit.setMaxResults(1);

			list = crit.list();
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
		return (ExamGenBO) list.get(0);
	}

	/*
	 * select unique ExamGenBO for Class Name passed
	 */
	public Object select_Unique(int id, Class className) {
		Session session = null;
		List<Object> list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("id", id));
			crit.setMaxResults(1);

			list = crit.list();
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
		return list.get(0);
	}

	/*
	 * select unique ExamGenBO for Class Name passed
	 */
	public Object select_Unique_Active(int id, Class className) {
		Session session = null;
		List<Object> list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("id", id));
			crit.add(Restrictions.eq("isActive", true));
			crit.setMaxResults(1);

			list = crit.list();
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
		return list.get(0);
	}

	public Object select_OneRes_Only(Class className) {
		Session session = null;
		List<Object> list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(className);
			crit.setMaxResults(1);

			list = crit.list();
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
		return list.get(0);
	}

	/*
	 * update for single field entry
	 */
	public boolean update_IExamGenBO(int id, String name, String userId,
			Class className_IExamGenBO) {
		Session session = null;
		Transaction transaction = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			IExamGenBO objExamBO = (IExamGenBO) session.get(
					className_IExamGenBO, id);
			objExamBO.setLastModifiedDate(new Date());
			objExamBO.setName(name);
			objExamBO.setModifiedBy(userId);
			session.update(objExamBO);
			transaction.commit();
			session.flush();
			// session.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			return false;
		}
	}

	/*
	 * delete for single field entry
	 */
	public boolean delete_IExamGenBO(int id, String userId,
			Class className_IExamGenBO) {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();

			IExamGenBO objIExamGenBO = (IExamGenBO) session.get(
					className_IExamGenBO, id);
			objIExamGenBO.setLastModifiedDate(new Date());
			objIExamGenBO.setIsActive(false);
			objIExamGenBO.setModifiedBy(userId);

			session.update(objIExamGenBO);
			transaction.commit();
			session.flush();
			// session.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return false;
		}
	}

	/*
	 * reactivate for single field entry
	 */
	public boolean reActivate_IExamGenBO(String name, String userId,
			Class className_IExamGenBO) {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session1 = sessionFactory.openSession();

			Criteria crit = session1.createCriteria(className_IExamGenBO);
			crit.add(Restrictions.eq("name", name));

			List<IExamGenBO> list = crit.list();

			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();

			if (list.size() > 0) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					IExamGenBO objIExamGenBO = (IExamGenBO) it.next();
					objIExamGenBO.setLastModifiedDate(new Date());
					objIExamGenBO.setModifiedBy(userId);
					objIExamGenBO.setIsActive(true);
					session.update(objIExamGenBO);
				}
			}
			transaction.commit();
			session.flush();
			// session.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			return false;
		}
	}

	/*
	 * reactivate for single field entry
	 */
	public boolean reActivate_IExamGenBO(int id, String userId,
			Class className_IExamGenBO) {
		Session session1 = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session1 = sessionFactory.openSession();
			Transaction transaction = session1.beginTransaction();
			Criteria crit = session1.createCriteria(className_IExamGenBO);
			crit.add(Restrictions.eq("id", id));
			crit.setMaxResults(1);

			List<IExamGenBO> list = crit.list();
			// session = sessionFactory.openSession();

			transaction.begin();

			if (list.size() > 0) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					IExamGenBO objIExamGenBO = (IExamGenBO) it.next();
					objIExamGenBO.setLastModifiedDate(new Date());
					objIExamGenBO.setModifiedBy(userId);
					objIExamGenBO.setIsActive(true);
					session1.update(objIExamGenBO);
				}
			}
			transaction.commit();
			session1.flush();
			// session.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session1 != null) {
				session1.flush();
				session1.close();
			}
			return false;
		}
	}

	// To get specialization for a particular course
	public ArrayList<ExamSpecializationBO> select_ExamSpec(int courseId) {
		Session session = null;
		ArrayList<ExamSpecializationBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamSpecializationBO.class);
			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("isActive", true));

			list = new ArrayList(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSpecializationBO>();

		}
		return list;

	}

	public ArrayList<CurriculumSchemeUtilBO> select_Scheme(int courseId,
			int year) {
		Session session = null;
		ArrayList<CurriculumSchemeUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session
					.createCriteria(CurriculumSchemeUtilBO.class);

			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("year", year));

			list = new ArrayList(crit.list());
			session.flush();
			session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<CurriculumSchemeUtilBO>();

		}
		return list;

	}

	// To get scheme for a particular course
	public ArrayList<CurriculumSchemeUtilBO> select_SchemeIdFromClassId(
			int classId) {
		Session session = null;
		ArrayList<CurriculumSchemeUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session
					.createCriteria(CurriculumSchemeUtilBO.class);

			crit.add(Restrictions.eq("id", classId));

			list = new ArrayList(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<CurriculumSchemeUtilBO>();

		}
		return list;

	}

	public ArrayList<ExamCourseUtilBO> select_course(
			ArrayList<Integer> listcourseId) {
		Session session = null;
		ArrayList<ExamCourseUtilBO> list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamCourseUtilBO.class);
			// crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.in("courseID", listcourseId));

			list = (ArrayList<ExamCourseUtilBO>) crit.list();
			session.flush();
			// session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamCourseUtilBO>();

		}
		return list;
	}

	public ArrayList<ExamCourseUtilBO> select_ActiveOnly(int programTypeId) {
		Session session = null;
		ArrayList<ExamCourseUtilBO> list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = (Query) session
					.createQuery("from ExamCourseUtilBO e where e.program.programType.id = :programTypeId");
			query.setParameter("programTypeId", programTypeId);

			list = new ArrayList<ExamCourseUtilBO>(query.list());

			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamCourseUtilBO>();
		}
		return list;
	}

	public ArrayList<ExamProgramUtilBO> select_ActiveOnly_Program(
			int programTypeId) {
		Session session = null;
		ArrayList<ExamProgramUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = (Query) session
					.createQuery("from ExamProgramUtilBO e where e.programType.id = :programTypeId and e.isActive = 1");
			query.setParameter("programTypeId", programTypeId);

			list = new ArrayList<ExamProgramUtilBO>(query.list());

			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamProgramUtilBO>();
		}
		return list;
	}

	public ArrayList<ExamProgramUtilBO> select_ActiveOnly_Program_byPTypes(
			ArrayList<Integer> pids) {

		Session session = null;
		ArrayList<ExamProgramUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = (Query) session
					.createQuery("from ExamProgramUtilBO e where e.programType.id in (:programTypeId) and e.isActive = 1");
			query.setParameterList("programTypeId", pids);

			list = new ArrayList<ExamProgramUtilBO>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamProgramUtilBO>();
		}
		return list;
	}

	/*
	 * select all the active ExamCourseUtilBO
	 */
	public ArrayList<ExamCourseUtilBO> select_ActiveOnly_CourseUtil() {
		Session session = null;
		ArrayList<ExamCourseUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// String HQL =
			// "from ExamCourseUtilBO bo order by bo.program.programType.programType,bo.program.programName,bo.courseName";
			String HQL = "from ExamCourseUtilBO bo where bo.isActive = 1 and bo.program.isActive=1 group by bo.program.programType,bo.program.programName,bo.courseName"
					+ " order by bo.program.programType desc";
			Query query = session.createQuery(HQL);
			list = (ArrayList<ExamCourseUtilBO>) query.list();
			session.flush();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamCourseUtilBO>();
		}
		return list;
	}

	public ArrayList<Integer> select_studentWithheld(int examId, int courseId,
			int schemeNo) {
		Session session = null;
		ArrayList<Integer> list;
		try {
			String HQL = "select e.studentId from ExamUpdateExcludeWithheldBO e  "
					+ " where e.withheld = 1 and e.examId = :examId and e.courseId = :courseId and e.schemeNo = :schemeNo";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query q = session.createQuery(HQL);
			q.setParameter("examId", examId);
			q.setParameter("courseId", courseId);
			q.setParameter("schemeNo", schemeNo);
			list = new ArrayList<Integer>(q.list());
			session.flush();
			// session.close();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Integer>();
		}
		return list;

	}

	public ArrayList<Integer> select_studentExcludeFromResults(int examId,
			int courseId, int schemeNo) {
		Session session = null;
		ArrayList<Integer> list;
		try {
			String HQL = "select e.studentId from ExamUpdateExcludeWithheldBO e  "
					+ " where e.excludeFromResults = 1 and e.examId = :examId and e.courseId = :courseId and e.schemeNo = :schemeNo";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query q = session.createQuery(HQL);
			q.setParameter("examId", examId);
			q.setParameter("courseId", courseId);
			q.setParameter("schemeNo", schemeNo);
			list = new ArrayList<Integer>(q.list());

			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Integer>();
		}
		return list;

	}

	public ArrayList<ExamSpecializationBO> select_studentSpecializationCourseId(
			int courseId) {
		Session session = null;
		ArrayList<ExamSpecializationBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamSpecializationBO.class);
			crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.eq("courseId", courseId));

			list = new ArrayList<ExamSpecializationBO>(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSpecializationBO>();

		}
		return list;

	}

	// To get section name for a particular course and scheme
	public ArrayList<ClassUtilBO> select_ActiveOnly_SectionUtil(int courseId,
			int schemeId, Integer schemeNo, Integer academicYear) {
		Session session = null;
		ArrayList list;
		try {

			/*String hql = "select cs.classUtilBO.id, cs.classUtilBO.name"
					+ " from ClassSchemewiseUtilBO cs"
					+ " where cs.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId"
					+ " and cs.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId = :schemeId"
					+ " and cs.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo"
					+ " and cs.curriculumSchemeDurationUtilBO.academicYear = :academicYear and cs.classUtilBO";*/
			String hql = "select cs.classes.id, cs.classes.name"
				+ " from ClassSchemewise cs"
				+ " where cs.curriculumSchemeDuration.curriculumScheme.course.id = :courseId"
				+ " and cs.curriculumSchemeDuration.curriculumScheme.courseScheme.id = :schemeId"
				+ " and cs.curriculumSchemeDuration.semesterYearNo = :schemeNo"
				+ " and cs.curriculumSchemeDuration.academicYear = :academicYear and cs.classes.isActive = 1";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);

			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<String>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ClassUtilBO>();
		}
		return list;
	}

	public StudentUtilBO select_student(String rollNo, String registerNo) {

		Session session = null;
		StudentUtilBO s = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(StudentUtilBO.class);
			crit.add(Restrictions.eq("rollNo", rollNo));
			crit.add(Restrictions.eq("registerNo", registerNo));
			s = (StudentUtilBO) crit.uniqueResult();

			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return s;
	}

	// To get studentId from regNo
	public StudentUtilBO select_student(String registerNo) {

		Session session = null;
		StudentUtilBO s = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(StudentUtilBO.class);
			crit.add(Restrictions.eq("registerNo", registerNo));
			s = (StudentUtilBO) crit.uniqueResult();

			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return s;
	}

	// To get studentId from regNo

	public StudentUtilBO select_student_Only(String rollNo, String registerNo,
			boolean useRollNo) {

		Session session = null;
		StudentUtilBO s = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(StudentUtilBO.class);
			if (useRollNo) {
				crit.add(Restrictions.eq("rollNo", rollNo));
			} else {
				crit.add(Restrictions.eq("registerNo", registerNo));
			}
			s = (StudentUtilBO) crit.uniqueResult();

			session.flush();
			// session.close();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return s;
	}

	public StudentUtilBO select_student(String rollNo, String registerNo,
			boolean useRollNo) {

		Session session = null;
		StudentUtilBO s = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(StudentUtilBO.class);
			if (useRollNo) {
				crit.add(Restrictions.eq("rollNo", rollNo));
			} else {
				crit.add(Restrictions.eq("registerNo", registerNo));
			}
			s = (StudentUtilBO) crit.uniqueResult();

			session.flush();
			// session.close();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return s;
	}

	public ArrayList<Integer> select_subjectId(int courseId, String rollNo,
			String regNo, boolean useRollNo, int schemeNo, int studentId,
			boolean isPreviousExam) {

		ArrayList<Integer> listStuId = null;

		String HQL_QUERY = "";
		if (isPreviousExam) {
			HQL_QUERY = "select distinct sgs.subjectUtilBO.id "
					+ " from SubjectGroupSubjectsUtilBO sgs "
					+ " join sgs.subjectGroupUtilBO sg "
					+ " where sg.courseId = :courseId "
					+ " and sgs.subjectGroupUtilBO.id in (select css.subjectGroupId from "
					+ " CurriculumSchemeSubjectUtilBO css "
					+ " where css.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo"
					+ " and css.curriculumSchemeDurationId in "
					+ " (select cls.curriculumSchemeDurationUtilBO.id "
					+ " from ClassSchemewiseUtilBO cls where cls.classId in "
					+ " (select s.classId from ExamStudentPreviousClassDetailsBO s "
					+ " where s.studentId = " + studentId
					+ " and s.schemeNo = :schemeNo)))";
		} else {

			HQL_QUERY = "select distinct sgs.subjectUtilBO.id"
					+ " from SubjectGroupSubjectsUtilBO sgs"
					+ " join sgs.subjectGroupUtilBO sg"
					+ " where sg.courseId = :courseId"
					+ " and sgs.subjectGroupUtilBO.id in (select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
					+ " where css.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo"
					+ " and css.curriculumSchemeDurationId in (select cls.curriculumSchemeDurationUtilBO.id from ClassSchemewiseUtilBO cls"
					+ " where cls.id in (select s.classSchemewiseId from StudentUtilBO s";
		}
		if (!isPreviousExam) {
			if (useRollNo) {
				HQL_QUERY = HQL_QUERY + " where s.rollNo = :rollNo))) ";
			} else {
				HQL_QUERY = HQL_QUERY + " where s.registerNo = :regNo))) ";
			}
		}

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		if (!isPreviousExam) {
			if (useRollNo) {
				query.setParameter("rollNo", rollNo);
			} else {
				query.setParameter("regNo", regNo);
			}
		}

		listStuId = new ArrayList<Integer>();
		Iterator itr = query.list().iterator();
		while (itr.hasNext()) {
			listStuId.add((Integer) itr.next());

		}
		session.flush();
		// session.close();

		return listStuId;
	}

	public ArrayList<CurriculumSchemeUtilBO> select_Scheme_By_Course(
			int courseId) {
		Session session = null;
		ArrayList<CurriculumSchemeUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session
					.createCriteria(CurriculumSchemeUtilBO.class);

			crit.add(Restrictions.eq("courseId", courseId));

			list = new ArrayList(crit.list());
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<CurriculumSchemeUtilBO>();

		}
		return list;

	}

	public ArrayList<ExamDefinitionBO> select_ActiveOnly_ExamName() {
		Session session = null;
		ArrayList<ExamDefinitionBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamDefinitionBO.class);
			crit.addOrder(Order.desc("isCurrent"));
			crit.add(Restrictions.eq("delIsActive", true));
			crit.add(Restrictions.eq("isActive", true));

			list = (ArrayList<ExamDefinitionBO>) crit.list();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamDefinitionBO>();
		}
		return list;

	}

	public ArrayList<ExamSubjectTO> getSubjectByCourse(int courseId,
			int schemeId, int schemeNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList<ExamSubjectTO> list = null;
		try {
			String SQL_QUERY = null;

			SQL_QUERY = "select sub.id, sub.name || '(' || sub.code || ')'"
					+ " from SubjectUtilBO sub"
					+ " where sub.id in ("
					+ " select distinct sgs.subjectUtilBO.id"
					+ " from SubjectGroupSubjectsUtilBO sgs"
					+ " where sgs.subjectGroupUtilBO.id in ("
					+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
					+ " where css.curriculumSchemeDurationId in ("
					+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
					+ " where csd.curriculumSchemeId in ( "
					+ " select cs.id from CurriculumSchemeUtilBO cs"
					+ " where cs.courseId = :courseId"
					+ " and cs.courseSchemeId = :schemeId)"
					+ " and csd.semesterYearNo = :schemeNo ))))";

			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			query.setParameter("schemeNo", schemeNo);
			if (query.list() != null && query.list().size() > 0) {
				list = new ArrayList<ExamSubjectTO>(query.list());
			} else {
				list = new ArrayList<ExamSubjectTO>();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;

	}

	public ArrayList<ExamSubjectTO> getSubjectByCourse(int courseId,
			int schemeId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList<ExamSubjectTO> list = null;
		try {
			String SQL_QUERY = null;

			SQL_QUERY = "select sub.id, sub.name || '(' || sub.code || ')'"
					+ " from SubjectUtilBO sub"
					+ " where sub.id in ("
					+ " select distinct sgs.subjectUtilBO.id"
					+ " from SubjectGroupSubjectsUtilBO sgs"
					+ " where sgs.subjectGroupUtilBO.id in ("
					+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
					+ " where css.curriculumSchemeDurationId in ("
					+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
					+ " where csd.curriculumSchemeId in ( "
					+ " select cs.id from CurriculumSchemeUtilBO cs"
					+ " where cs.courseId = :courseId"
					+ " and cs.courseSchemeId = :schemeId))))";

			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			if (query.list() != null && query.list().size() > 0)
				list = new ArrayList<ExamSubjectTO>(query.list());
			session.flush();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;

	}

	// Unused method
	public List getCourseByExamNameRegNo(int examId, String regNo, String rollNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List list = null;
		try {
			String SQL_QUERY = null;
			SQL_QUERY = "select distinct c.id, c.name from course c join curriculum_scheme on curriculum_scheme.course_id = c.id join "
					+ "curriculum_scheme_duration on curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id join "
					+ "class_schemewise on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id join student on "
					+ "student.class_schemewise_id = class_schemewise.id join EXAM_exam_course_scheme_details on "
					+ "EXAM_exam_course_scheme_details.course_id = c.id where (student.register_no = :register_no or student.roll_no = :roll_no) "
					+ "and EXAM_exam_course_scheme_details.exam_id = :exam_id";
			Query query = session.createSQLQuery(SQL_QUERY);
			query.setParameter("exam_id", examId);
			query.setParameter("register_no", regNo);
			query.setParameter("roll_no", rollNo);
			list = query.list();
			session.flush();
			// session.close();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;
	}

	// To get course of the student(Register No) for a particular exam
	public List getCourseByExamNameRegNoOnly(int examId, String regNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List list = null;
		try {
			String HQL_QUERY = null;

			HQL_QUERY = "select distinct s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId,"
					+ " s.classSchemewiseUtilBO.classUtilBO.examCourseUtilBO.courseName"
					+ " from StudentUtilBO s"
					+ " where s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId in "
					+ " (select eec.courseId from ExamExamCourseSchemeDetailsBO eec where eec.examId = :examId)"
					+ " and s.registerNo = :regNo";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("regNo", regNo);
			list = query.list();
			if(list==null || list.isEmpty()){
				list=session.createQuery("select case when (rejClas is null) then e.classSchemewise.classes.course.id else c.id end, case when (rejClas is null) then e.classSchemewise.classes.course.name else c.name end from ExamStudentDetentionRejoinDetails e left join e.rejoinClassSchemewise rejClas left join rejClas.classes.course c where e.registerNo='"+regNo+"' and (e.classSchemewise.classes.course.id " +
						"in(select eec.courseId from ExamExamCourseSchemeDetailsBO eec where eec.examId = "+examId+") or c.id in (select eec.courseId from ExamExamCourseSchemeDetailsBO eec where eec.examId ="+examId+"))").list();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;
	}

	// To get course of the student(Roll No) for a particular exam
	public List getCourseByExamNameRollNoOnly(int examId, String rollNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List list = null;
		try {
			String HQL_QUERY = null;

			HQL_QUERY = "select distinct s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId,"
					+ " s.classSchemewiseUtilBO.classUtilBO.examCourseUtilBO.courseName"
					+ " from StudentUtilBO s"
					+ " where s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId in "
					+ " (select eec.courseId from ExamExamCourseSchemeDetailsBO eec where eec.examId = :examId)"
					+ " and s.rollNo = :rollNo";

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("examId", examId);
			query.setParameter("rollNo", rollNo);
			list = query.list();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;
	}

	public List<KeyValueTO> getClassValuesByCourseId(int courseId,
			int courseSchemeId, int schemeNo, List<KeyValueTO> list,
			Integer examId){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL = null;

		ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) session.get(
				ExamDefinitionBO.class, examId);	
		
		Integer examTypeId = null;
		if(examDefinitionBO!= null){
			examTypeId =  examDefinitionBO.getExamTypeID();
		}
		String supQuString = "";
		if(examTypeId == 3 || examTypeId == 6){
			 supQuString = " and Cd.academicYear >= (select e.examForJoiningBatch from ExamDefinitionBO e where e.id = :examId)";
		}
		else{
			 supQuString = " and Cd.academicYear = (select e.academicYear from ExamDefinitionBO e where e.id = :examId)";
		}
			
		HQL = " select c.classes.id, c.classes.name, c.curriculumSchemeDuration.curriculumScheme.year from ClassSchemewise c where c.classes.id in (select Cs.classId from ClassSchemewiseUtilBO Cs where "
				+ "Cs.curriculumSchemeDurationUtilBO.id in (select Cd.id from CurriculumSchemeDurationUtilBO Cd where Cd.curriculumSchemeId in "
				+ "(select Cu.id from CurriculumSchemeUtilBO Cu where Cu.courseId = :courseId and Cu.courseSchemeId = :courseSchemeId) and "
				+ "Cd.semesterYearNo = :schemeNo " + supQuString + ")) and c.classes.isActive = 1)";

		Query query = session.createQuery(HQL);
		query.setParameter("courseId", courseId);
		query.setParameter("courseSchemeId", courseSchemeId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("examId", examId);
		Iterator<Object[]> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			if (obj[1] != null) {
				String year = "";
				if(obj[2]!= null ){
					year = " (" + obj[2].toString() + ")";
				}
				list.add(new KeyValueTO(Integer.parseInt(obj[0].toString()),
						obj[1].toString()+year));
			}

		}

		return list;

	}

	public List getCourseIdSchemeNo(int examId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL = null;
		HQL = "select distinct e.courseId, e.courseSchemeId, e.schemeNo from  ExamExamCourseSchemeDetailsBO e where e.examId =:examId";
		Query query = session.createQuery(HQL);
		query.setParameter("examId", examId);

		return query.list();

	}

	public ArrayList<ClassUtilBO> select_Students(ArrayList<Integer> listClassId) {
		Session session = null;

		ArrayList<ClassUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ClassUtilBO.class);
			crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.in("id", listClassId));

			list = new ArrayList<ClassUtilBO>(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			if (session != null) {

				session.flush();
				// session.close();
			}
			list = new ArrayList<ClassUtilBO>();
		}
		return list;
	}

	public int getStudentIdRollORReg(String rollNo, String registerNo,
			boolean rollNoPresent) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		int studentID = 0;
		String HQL = null;
		HQL = "select s.id from StudentUtilBO s where";
		if (rollNoPresent) {
			HQL = HQL + " s.rollNo= :rollNo";
		} else {
			HQL = HQL + " s.registerNo= :registerNo";
		}
		Query query = session.createQuery(HQL);
		if (rollNoPresent) {
			query.setParameter("rollNo", rollNo);
		} else {
			query.setParameter("registerNo", registerNo);
		}
		Iterator<Object> list = query.list().iterator();
		while (list.hasNext()) {
			Object object = (Object) list.next();
			if (object != null) {
				studentID = Integer.parseInt(object.toString());
			}

		}

		return studentID;

	}

	public int getStudentIdRollReg(String rollNo, String registerNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		int studentID = 0;

		String HQL = null;
		HQL = "select s.id from StudentUtilBO s where s.rollNo= :rollNo and s.registerNo= :registerNo";

		Query query = session.createQuery(HQL);
		query.setParameter("rollNo", rollNo);
		query.setParameter("registerNo", registerNo);

		Iterator<Object> list = query.list().iterator();
		while (list.hasNext()) {
			Object object = (Object) list.next();
			if (object != null) {
				studentID = Integer.parseInt(object.toString());
			}

		}

		return studentID;
	}

	public ArrayList<ExamDefinitionBO> select_ExamName_Internal(int academicYear) {
		Session session = null;
		ArrayList list;
		try {

			String hql = "select e.id, e.name"
					+ " from ExamDefinitionBO e"
					+ " where e.examTypeUtilBO.name like '%Internal Retest%' and e.academicYear = :academicYear";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
			query.setParameter("academicYear", academicYear);

			list = new ArrayList<ExamDefinitionBO>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamDefinitionBO>();
		}
		return list;
	}

	public ArrayList<ExamFooterAgreementBO> select_agreementList(
			ArrayList<Integer> classIdList) {
		Session session = null;
		ArrayList<ExamFooterAgreementBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(ExamFooterAgreementBO.class);

			crit.add(Restrictions.in("classId", classIdList));
			crit.add(Restrictions.eq("isAgreement", 1));

			list = new ArrayList(crit.list());
			session.flush();
			//session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamFooterAgreementBO>();

		}
		return list;
	}

	public ArrayList<ExamFooterAgreementBO> select_footertList(
			ArrayList<Integer> classIdList) {

		Session session = null;
		ArrayList<ExamFooterAgreementBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(ExamFooterAgreementBO.class);

			crit.add(Restrictions.in("classId", classIdList));
			crit.add(Restrictions.eq("isFooter", 1));

			list = new ArrayList(crit.list());

			session.flush();
			//session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamFooterAgreementBO>();

		}
		return list;
	}

	public ArrayList<ExamFooterAgreementBO> select_footertListByProgramTypeId(
			int programTypeID) {

		Session session = null;
		ArrayList<ExamFooterAgreementBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(ExamFooterAgreementBO.class);

			crit.add(Restrictions.eq("programTypeId", programTypeID));
			crit.add(Restrictions.eq("isFooter", 1));

			list = new ArrayList(crit.list());

			session.flush();
			//session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamFooterAgreementBO>();

		}
		return list;
	}

	public ArrayList<ExamFooterAgreementBO> select_agreementList(
			int programTypeID) {
		Session session = null;
		ArrayList<ExamFooterAgreementBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(ExamFooterAgreementBO.class);

			crit.add(Restrictions.eq("programTypeId", programTypeID));
			crit.add(Restrictions.eq("isAgreement", 1));

			list = new ArrayList(crit.list());
			session.flush();
			//session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamFooterAgreementBO>();

		}
		return list;
	}

	public ExamInternalExamTypeBO get_MarkType(String examType) {
		return null;
	}

	// To FETCH Exam Names Based On Process Type (UPDATE PROCESS)
	public ArrayList<ExamDefinitionBO> getExamNameByProcessType(
			String processType,String year) {
		Session session = null;
		ArrayList list;
		try {

			String HQL_QUERY = null;

			if (processType.equals("1") || processType.equals("4") || processType.equals("5") || processType.equals("7") || processType.equals("8")) {

				HQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  in ('Regular','Regular & Supplementary','Supplementary','Special Supplementary') and e.delIsActive = 1 and e.isActive =1";

			} else if (processType.equals("2") || processType.equals("6")) {

				HQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  in ('Regular','Regular & Supplementary') and e.delIsActive = 1 and e.isActive=1";

			} else if (processType.equals("3")) {

				HQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  in ('Supplementary','Special Supplementary','Regular & Supplementary') and e.delIsActive = 1 and e.isActive=1";
			
			} else if (processType.equals("9") || processType.equals("10")) {
				
				HQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.id = 5 and e.delIsActive = 1 and e.isActive=1";
			}
			HQL_QUERY=HQL_QUERY+" and e.academicYear="+year;
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(HQL_QUERY);
			list = new ArrayList<ExamDefinitionBO>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamDefinitionBO>();
		}
		return list;
	}

	public HashMap<Integer, String> select_Classes(int Year) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();

			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(
							"from ClassSchemewise c where c.classes.course.isActive = 1 and c.classes.isActive = 1 and c.curriculumSchemeDuration.academicYear = :academicYear order by c.classes.name")
					.setInteger("academicYear", Year);
			List<ClassSchemewise> classList = query.list();
			HashMap<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			Iterator<ClassSchemewise> itr = classList.iterator();
			ClassSchemewise classSchemewise;

			while (itr.hasNext()) {
				classSchemewise = (ClassSchemewise) itr.next();
				classMap.put(classSchemewise.getClasses().getId(),
						classSchemewise.getClasses().getName());
			}
			session.flush();
			// session.close();
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

	// To get subjectType by subjectID
	public String selectSubjectsTypeBySubjectId(int subjectId) {
		String subjectType = "";
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query query = (Query) session
				.createQuery("select s.isTheoryPractical from Subject s where s.id= :subjectId");
		query.setParameter("subjectId", subjectId);
		List list = query.list();
		session.flush();
		if (list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object row = (Object) itr.next();
				if (row != null) {
					subjectType = row.toString();
				}
			}
		}
		return subjectType;

	}

	public int getCurrentExamId() {
		String id = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "select d.id from ExamDefinitionBO d where d.isCurrent=1";
		Query query = session.createQuery(HQL);
		List list = null;
		try {
			list = query.list();
		} catch (HibernateException e) {
		}
		Iterator<Object[]> itr = list.iterator();
		while (itr.hasNext()) {
			Object row = (Object) itr.next();
			id = row.toString();
		}
		int idvalue = 0;
		if (id != null) {
			idvalue = Integer.parseInt(id);
		}
		return idvalue;
	}

	public int delete(Object obj, int id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.load(obj, id);
			session.delete(obj);
			transaction.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<ExamSubjectTO> getSubjectsByCourseSchemeExamId(
			int courseId, int schemeId, int schemeNo, Integer examId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList<ExamSubjectTO> list = null;
		try {
			String SQL_QUERY = null;

			/*SQL_QUERY = "select sub.id, sub.name || '(' || sub.code || ')'"
					+ " from SubjectUtilBO sub"
					+ " where sub.id in ("
					+ " select distinct sgs.subjectUtilBO.id"
					+ " from SubjectGroupSubjectsUtilBO sgs"
					+ " where sgs.subjectGroupUtilBO.id in ("
					+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
					+ " where css.curriculumSchemeDurationId in ("
					+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
					+ " where csd.curriculumSchemeId in ( "
					+ " select cs.id from CurriculumSchemeUtilBO cs"
					+ " where cs.courseId = :courseId"
					+ " and cs.courseSchemeId = :schemeId)"
					+ " and csd.semesterYearNo = :schemeNo and";
*/
			SQL_QUERY="select s.subject.id, s.subject.name || '(' || s.subject.code || ')' " +
					" from CurriculumSchemeDuration c join c.curriculumSchemeSubjects cs " +
					" join cs.subjectGroup.subjectGroupSubjectses s where c.semesterYearNo=:schemeNo " +
					" and c.curriculumScheme.course.id=:courseId" +
					" and c.curriculumScheme.courseScheme.id=:schemeId" +
					" and cs.subjectGroup.isActive=1" +
					" and s.isActive=1 and s.subject.isActive=1";
			
			if(suplExam(examId)){
				SQL_QUERY = SQL_QUERY + " and c.academicYear >= (select examForJoiningBatch "
					+ " from ExamDefinitionBO e where e.id = :examId)";
			}
			else{
				SQL_QUERY = SQL_QUERY + " and c.academicYear IN (select e.academicYear "
				+ " from ExamDefinitionBO e where e.id = :examId)";
				
			}
			SQL_QUERY=SQL_QUERY+" group by s.subject.id ";
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("examId", examId);
			if (query.list() != null && query.list().size() > 0) {
				list = new ArrayList<ExamSubjectTO>(query.list());
			} else {
				list = new ArrayList<ExamSubjectTO>();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;
	}

	public ArrayList<ExamSubjectTO> getSubjectsByCourseSchemeExamIdJBY(
			int courseId, int schemeId, int schemeNo, Integer examId,
			Integer jby) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList<ExamSubjectTO> list = null;
		try {
			String SQL_QUERY = null;

			if (examId != null) {

				SQL_QUERY = "select sub.id, sub.name || '(' || sub.code || ')'"
						+ " from SubjectUtilBO sub"
						+ " where sub.id in ("
						+ " select distinct sgs.subjectUtilBO.id"
						+ " from SubjectGroupSubjectsUtilBO sgs"
						+ " where sgs.subjectGroupUtilBO.id in ("
						+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
						+ " where css.curriculumSchemeDurationId in ("
						+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
						+ " where csd.curriculumSchemeId in ( "
						+ " select cs.id from CurriculumSchemeUtilBO cs"
						+ " where cs.courseId = :courseId"
						+ " and cs.courseSchemeId = :schemeId)"
						+ " and csd.semesterYearNo = :schemeNo and "
						+ " csd.academicYear IN (select e.academicYear "
						+ " from ExamDefinitionBO e where e.id = :examId)))))";
			} else {
				SQL_QUERY = "select sub.id, sub.name || '(' || sub.code || ')'"
						+ " from SubjectUtilBO sub"
						+ " where sub.id in ("
						+ " select distinct sgs.subjectUtilBO.id"
						+ " from SubjectGroupSubjectsUtilBO sgs"
						+ " where sgs.subjectGroupUtilBO.id in ("
						+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
						+ " where css.curriculumSchemeDurationId in ("
						+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
						+ " where csd.curriculumSchemeId in ( "
						+ " select cs.id from CurriculumSchemeUtilBO cs"
						+ " where cs.courseId = :courseId"
						+ " and cs.courseSchemeId = :schemeId and cs.year = :jby)"
						+ " and csd.semesterYearNo = :schemeNo))))";
			}

			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			query.setParameter("schemeNo", schemeNo);
			if (examId != null) {
				query.setParameter("examId", examId);
			} else {
				query.setParameter("jby", jby);
			}
			if (query.list() != null && query.list().size() > 0) {
				list = new ArrayList<ExamSubjectTO>(query.list());
			} else {
				list = new ArrayList<ExamSubjectTO>();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;
	}

	//raghu
	public List<ExamBlockUnblockHallTicketBO> getListOfCandidates(int examId,
			ArrayList<Integer> listClassIds, boolean useHallTicket,char type) throws Exception {
		Session session = null;
		List selectedCandidatesList = null;
		char h='H';
		if(!useHallTicket){
			h=type;
		}
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from ExamBlockUnblockHallTicketBO e where e.classId in (:classId) and e.examId="+examId+" and e.hallTktOrMarksCard =:hallTicket");
			selectedCandidatesQuery.setParameterList("classId", listClassIds);
			selectedCandidatesQuery.setCharacter("hallTicket", h);
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
	
	
	public void updateStudentsRemarks(List<ExamBlockUnBlockCandidatesTO> listCandidatesName) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		ExamBlockUnBlockCandidatesTO to;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamBlockUnBlockCandidatesTO> tcIterator = listCandidatesName.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				to = tcIterator.next();
				ExamBlockUnblockHallTicketBO bo= (ExamBlockUnblockHallTicketBO)session.get(ExamBlockUnblockHallTicketBO.class, to.getId());
				bo.setBlockReason(to.getReason());
				session.update(bo);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
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

	public boolean suplExam(
			int examId) {
		Session session = null;
		ArrayList<ExamDefinitionBO> list;
		int examTypeId = 0;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamDefinitionBO.class);
			crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.eq("id", examId));

			list = new ArrayList<ExamDefinitionBO>(crit.list());
			if (list.size() > 0){
				ExamDefinitionBO examDefinitionBO =list.get(0);
				examTypeId = examDefinitionBO.getExamTypeID();
			}
			session.flush();
			// session.close();
			
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamDefinitionBO>();

		}
		if((examTypeId == 6) || (examTypeId == 3)){
			return true;
		}
		else{
			return false;
		}

	}
	

	public List<KeyValueTO> getClassValuesByCourseIdWithYear(int courseId,
			int courseSchemeId, int schemeNo, List<KeyValueTO> list,
			Integer examId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String HQL = null;

		ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) session.get(
				ExamDefinitionBO.class, examId);	
		
		Integer examTypeId = null;
		if(examDefinitionBO!= null){
			examTypeId =  examDefinitionBO.getExamTypeID();
		}
		String supQuString = "";
		if(examTypeId == 3 || examTypeId == 6){
			 supQuString = " and Cd.academicYear >= (select e.examForJoiningBatch from ExamDefinitionBO e where e.id = :examId)";
		}
		else{
			 supQuString = " and Cd.academicYear = (select e.academicYear from ExamDefinitionBO e where e.id = :examId)";
		}
		/*	
		HQL = " select c.id, c.name from ClassUtilBO c where c.id in (select Cs.classId from ClassSchemewiseUtilBO Cs where "
				+ "Cs.curriculumSchemeDurationUtilBO.id in (select Cd.id from CurriculumSchemeDurationUtilBO Cd where Cd.curriculumSchemeId in "
				+ "(select Cu.id from CurriculumSchemeUtilBO Cu where Cu.courseId = :courseId and Cu.courseSchemeId = :courseSchemeId) and "
				+ "Cd.semesterYearNo = :schemeNo " + supQuString + ")) and c.isActive = 1)";
		*/
		
		HQL = " select c.classes.id, c.classes.name, c.curriculumSchemeDuration.academicYear from ClassSchemewise c where c.classes.id in (select Cs.classId from ClassSchemewiseUtilBO Cs where "
				+ "Cs.curriculumSchemeDurationUtilBO.id in (select Cd.id from CurriculumSchemeDurationUtilBO Cd where Cd.curriculumSchemeId in "
				+ "(select Cu.id from CurriculumSchemeUtilBO Cu where Cu.courseId = :courseId and Cu.courseSchemeId = :courseSchemeId) and "
				+ "Cd.semesterYearNo = :schemeNo " + supQuString + ")) and c.classes.isActive = 1)";
		
		Query query = session.createQuery(HQL);
		query.setParameter("courseId", courseId);
		query.setParameter("courseSchemeId", courseSchemeId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("examId", examId);
		Iterator<Object[]> itr = query.list().iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			if (obj[1] != null) {
				String year = "";
				if(obj[2]!= null ){
					year = " (" + obj[2].toString() + ")";
				}
				list.add(new KeyValueTO(Integer.parseInt(obj[0].toString()),
						obj[1].toString() + year));
			}

		}

		return list;

	}
	
	
	public List<Object> select_ActiveOnlyForCourse(Class className) {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.eq("onlyForApplication", false));

			list = crit.list();
			session.flush();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}

	public List<ExamTypeUtilBO> select_All_regular() {
		Session session = null;
		List<ExamTypeUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
            Query query=session.createQuery("from ExamTypeUtilBO etype where etype.name Not like '%Internal%'");
			list = query.list();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamTypeUtilBO>();

		}
		return list;

	}
	
	public int insert_List(ArrayList listBO,ExamBlockUnblockForm objform ) {
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		int flag = 0;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			Iterator<ExamBlockUnblockHallTicketBO> itr = listBO.iterator();
			while (itr.hasNext()) {
				ExamBlockUnblockHallTicketBO bo = (ExamBlockUnblockHallTicketBO) itr.next();
				Query query = session.createQuery("from ExamBlockUnblockHallTicketBO e where e.classId = :classId and e.examId= :examId and e.hallTktOrMarksCard=:hallTktOrMarksCard and e.studentId=:studentId");
				if( bo.getClassId()!= 0){
				query.setInteger("classId", bo.getClassId());
				}
				if( bo.getExamId()!= 0){
				query.setInteger("examId", bo.getExamId());
				}
				query.setString("hallTktOrMarksCard", bo.getHallTktOrMarksCard());
				if( bo.getStudentId()!= 0){
					query.setInteger("studentId", bo.getStudentId());
				}
				ExamBlockUnblockHallTicketBO examBo = (ExamBlockUnblockHallTicketBO)query.uniqueResult();
				if(examBo !=null){
					flag = 1;
					String reason=examBo.getBlockReason()+","+bo.getBlockReason();
					examBo.setBlockReason(reason);
					examBo.setLastModifiedDate(new Date());
					examBo.setModifiedBy(objform.getUserId());
					session.update(examBo);
				}else{
					//for (Object obj : listBO) {
						flag = 1;
						session.save(bo);
					}
				}
			
			tx.commit();
			session.flush();
			session.close();

		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
			log.error(e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		return flag;
	}
	public ArrayList<ExamSubjectTO> getSubjectsCodeNameByCourseSchemeExamId(
			String sCodeName,int courseId, int schemeId, int schemeNo, Integer examId,Integer academicYear) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList<ExamSubjectTO> list = null;
		try {
			String SQL_QUERY = null;

			/*SQL_QUERY = "select sub.id, sub.name || '(' || sub.code || ')'"
					+ " from SubjectUtilBO sub"
					+ " where sub.id in ("
					+ " select distinct sgs.subjectUtilBO.id"
					+ " from SubjectGroupSubjectsUtilBO sgs"
					+ " where sgs.subjectGroupUtilBO.id in ("
					+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
					+ " where css.curriculumSchemeDurationId in ("
					+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
					+ " where csd.curriculumSchemeId in ( "
					+ " select cs.id from CurriculumSchemeUtilBO cs"
					+ " where cs.courseId = :courseId"
					+ " and cs.courseSchemeId = :schemeId)"
					+ " and csd.semesterYearNo = :schemeNo and";
*/
			SQL_QUERY="select s.subject.id, s.subject.name, s.subject.code " +
					" from CurriculumSchemeDuration c join c.curriculumSchemeSubjects cs " +
					" join cs.subjectGroup.subjectGroupSubjectses s where c.semesterYearNo=:schemeNo " +
					" and c.curriculumScheme.course.id=:courseId" +
					" and c.curriculumScheme.courseScheme.id=:schemeId" +
					" and cs.subjectGroup.isActive=1" +
					" and s.isActive=1 and s.subject.isActive=1";
			
			if(suplExam(examId)){
				SQL_QUERY = SQL_QUERY + " and c.academicYear IN (select e.academicYear "
				+ " from ExamDefinitionBO e where e.id = :examId and e.academicYear= :academicyear)";
			}
			else{
				SQL_QUERY = SQL_QUERY + " and c.academicYear IN (select e.academicYear "
				+ " from ExamDefinitionBO e where e.id = :examId and e.academicYear= :academicyear)";
				
			}
			SQL_QUERY=SQL_QUERY+" group by s.subject.id, s.subject.name, s.subject.code ";
			
			if (sCodeName.equalsIgnoreCase("sCode")) {
				// HQL = HQL + " order by sub.code";
				SQL_QUERY = SQL_QUERY + " order by s.subject.code";

			} else {
				// HQL = HQL + " order by sub.name";
				SQL_QUERY = SQL_QUERY + " order by s.subject.name";
			}
			
			
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("examId", examId);
			query.setParameter("academicyear", academicYear);
			if (query.list() != null && query.list().size() > 0) {
				list = new ArrayList<ExamSubjectTO>(query.list());
			} else {
				list = new ArrayList<ExamSubjectTO>();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;
	}

	/**
	 * this method added by mahi
	 * @param examId
	 */
	public Map<Integer, String> getClassesForExam(int examId,String examType) throws Exception{
		Session session = null;
		int examTypeId=Integer.parseInt(examType);
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
            Query query=session.createQuery("select cls.id,cls.name,sche.curriculumSchemeDuration.curriculumScheme.year from CourseSchemeDetails cs join cs.course.classes cls join cls.classSchemewises sche where cs.examDefinition.id="+examId+" and cs.examDefinition.examType="+examTypeId+"");
			List<Object[]> list = query.list();
			if(list!=null && !list.isEmpty())
			{
				Iterator iterator=list.iterator();
				while(iterator.hasNext())
				{
					Object[] obj = (Object[])iterator.next();
				map.put((Integer)obj[0], obj[1]+"("+obj[2]+")");
				}
			}
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return map;

	}
	public ArrayList<ExamDefinitionBO> getExamNameByAcademicYearAndExamType(int academicYear,String examType) {
		Session session = null;
		ArrayList list;
		try {

			String hql = "select e.id, e.name"
					+ " from ExamDefinitionBO e"
					+ " where ( e.examTypeUtilBO.name=:examType  or e.examTypeUtilBO.name=:examType1 ) and e.academicYear = :academicYear";

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Query query = session.createQuery(hql);
			query.setParameter("academicYear", academicYear);
			
			if(examType.equalsIgnoreCase("Regular")){
				query.setParameter("examType", "Regular");
				query.setParameter("examType1", "Regular");
			}else if(examType.equalsIgnoreCase("Supplementary")){
				query.setParameter("examType", "Supplementary");
				query.setParameter("examType1", "Supplementary");
			}else if(examType.equalsIgnoreCase("Revaluation")){
				query.setParameter("examType", "Regular");
				query.setParameter("examType1", "Supplementary");
			}else if(examType.equalsIgnoreCase("Improvement")){
				query.setParameter("examType", "Supplementary");
				query.setParameter("examType1", "Supplementary");
			}else if(examType.equalsIgnoreCase("Grace")){
				query.setParameter("examType", "Regular");
				query.setParameter("examType1", "Regular");
			}
			
			list = new ArrayList<ExamDefinitionBO>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamDefinitionBO>();
		}
		return list;
	}
	public ArrayList<ExamSubjectTO> getSubjectsCodeNameByCourseSchemeIdByTeacher(
			String sCodeName,int courseId, int schemeId, int schemeNo, String teacherId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ArrayList<ExamSubjectTO> list = null;
		try {
			String SQL_QUERY = null;
			/*SQL_QUERY = "select sub.id, sub.name || '(' || sub.code || ')'"
					+ " from SubjectUtilBO sub"
					+ " where sub.id in ("
					+ " select distinct sgs.subjectUtilBO.id"
					+ " from SubjectGroupSubjectsUtilBO sgs"
					+ " where sgs.subjectGroupUtilBO.id in ("
					+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
					+ " where css.curriculumSchemeDurationId in ("
					+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
					+ " where csd.curriculumSchemeId in ( "
					+ " select cs.id from CurriculumSchemeUtilBO cs"
					+ " where cs.courseId = :courseId"
					+ " and cs.courseSchemeId = :schemeId)"
					+ " and csd.semesterYearNo = :schemeNo and";
*/
			SQL_QUERY="select s.subject.id, s.subject.name, s.subject.code " +
					" from CurriculumSchemeDuration c join c.curriculumSchemeSubjects cs " +
					" join cs.subjectGroup.subjectGroupSubjectses s where c.semesterYearNo=:schemeNo " +
					" and c.curriculumScheme.course.id=:courseId" +
					" and c.curriculumScheme.courseScheme.id=:schemeId" +
					" and cs.subjectGroup.isActive=1" +
					" and s.isActive=1 and s.subject.isActive=1" +
					" and s.subject.id IN( select distinct tcs.subject.id from TeacherClassSubject tcs where tcs.teacherId=:teacherId)";
			
			
			SQL_QUERY=SQL_QUERY+" group by s.subject.id, s.subject.name, s.subject.code ";
			
			if (sCodeName.equalsIgnoreCase("sCode")) {
				// HQL = HQL + " order by sub.code";
				SQL_QUERY = SQL_QUERY + " order by s.subject.code";

			} else {
				// HQL = HQL + " order by sub.name";
				SQL_QUERY = SQL_QUERY + " order by s.subject.name";
			}
			
			
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			query.setParameter("schemeNo", schemeNo);
			query.setString("teacherId", teacherId);
			if (query.list() != null && query.list().size() > 0) {
				list = new ArrayList<ExamSubjectTO>(query.list());
			} else {
				list = new ArrayList<ExamSubjectTO>();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list;
	}
}