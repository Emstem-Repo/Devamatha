package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.ExamSubjecRuleSettingsMultipleEvaluatorBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAssignmentBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAttendanceBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsMultipleAnsScriptBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsSubInternalBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamSubjectRuleSettingsImpl extends ExamGenImpl {
	private static final Log log = LogFactory
			.getLog(ExamSubjectRuleSettingsImpl.class);

	public List<Object> select_ActiveOnlySubinternal(Class className) throws Exception {
		log
				.info("Entering into select_ActiveOnlySubinternal() method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query = session
					.createQuery("select b.id, b.name from ExamInternalExamTypeBO b where b.isActive=1");
			list = query.list();
			session.flush();
		} catch (Exception e) {
			log.error("Exception in select_ActiveOnlySubinternal() method"
					+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();
		}
		log
				.info("Exiting select_ActiveOnlySubinternal() method of ExamSubjectRuleSettingsImpl class");
		return list;
	}

	public int insert_returnId(ExamSubjectRuleSettingsBO objBO) throws Exception {
		log
				.info("Entering into insert_returnId() method of ExamSubjectRuleSettingsImpl class");
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {
			session.save(objBO);
			id = objBO.getId();
			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error("Exception in insert_returnId() method" + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.info("Exiting insert_returnId() method of ExamSubjectRuleSettingsImpl class");
		return id;
	}

	public List<Integer> insert_SubjectRuleSettingsList(
			ArrayList<ExamSubjectRuleSettingsBO> listBO) throws Exception {
		log
				.info("Entering into insert_SubjectRuleSettingsList() method of ExamSubjectRuleSettingsImpl class");
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		List<Integer> ids = new ArrayList<Integer>();
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			for (ExamSubjectRuleSettingsBO obj : listBO) {
				if(!checkDuplicate(obj.getAcademicYear(),obj.getCourseId(),obj.getSchemeNo(),obj.getSubjectId()))
				{
					session.save(obj);
					ids.add(obj.getId());
				}
					
			}
			System.currentTimeMillis();
			tx.commit();
			session.flush();
			session.close();

		} catch (Exception e) {
			log.error("Exception in insert_SubjectRuleSettingsList() method"
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		log
				.info("Exiting insert_SubjectRuleSettingsList() method of ExamSubjectRuleSettingsImpl class");
		return ids;
	}


	public List<Object[]> select_Subjects(int courseId, int schemeId,
			int schemeNo, int academicYear) throws Exception {
		log
				.info("Entering into select_Subjects() method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String SQL_QUERY = "select sub.id, sub.name"
					+ " from SubjectUtilBO sub"
					+ " where sub.id in ( "
					+ " select distinct sgs.subjectUtilBO.id "
					+ " from SubjectGroupSubjectsUtilBO sgs"
					+ " where sgs.subjectGroupUtilBO.id in ("
					+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
					+ " where css.curriculumSchemeDurationId in ("
					+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
					+ " where csd.curriculumSchemeId in ( "
					+ " select cs.id from CurriculumSchemeUtilBO cs"
					+ " where cs.courseId = :courseId"
					+ " and cs.courseSchemeId = :schemeId)"
					+ " and csd.semesterYearNo = :schemeNo and csd.academicYear = :academicYear)))";

			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("academicYear", academicYear);
			list = query.list();
		} catch (Exception e) {
			log.error("Exception in select_Subjects() method" + e.getMessage());
			if (session != null) {
				
				session.close();
			}

		}
		log
				.info("Exiting select_Subjects() method of ExamSubjectRuleSettingsImpl class");
		return list;
	}

	public void delete_SubjectRuleSettings(int academicYear, int courseID,
			int schemeNo, boolean isActive) throws Exception {
		log
				.info("Entering into delete_SubjectRuleSettings() method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		Transaction tx = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			String hql = " update ExamSubjectRuleSettingsBO srs"
					+ " set srs.isActive = :isActive"
					+ " where srs.academicYear = :academicYear"
					+ " and srs.courseId = :courseId"
					+ " and srs.schemeNo = :schemeNo";

			Query query = session.createQuery(hql);
			query.setParameter("academicYear", academicYear);
			query.setParameter("courseId", courseID);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("isActive", isActive);

			query.executeUpdate();

			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error("Exception in delete_SubjectRuleSettings() method"
					+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			if (tx != null)
				tx.rollback();

		}
		log
				.info("Exiting delete_SubjectRuleSettings() method of ExamSubjectRuleSettingsImpl class");
	}

	public List<Object[]> selectAllDetailsForEdit(int academicYear,
			Integer courseId, Integer schemeId, List<Integer> schemeNoList) throws Exception {
		log
				.info("Entering into selectAllDetailsForEdit() method of ExamSubjectRuleSettingsImpl class");
		SessionFactory sessionFactory = null;
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String hqlQuery = "select srs.id, c.courseName , srs.schemeNo , (select cs.name from CourseSchemeUtilBO cs "
					+ " where cs.id = :schemeId) , sub.name ,sub.id as subId, sub.code"
					+ " from ExamSubjectRuleSettingsBO srs"
					+ " join srs.subjectUtilBO sub"
					+ " join srs.examCourseUtilBO c"
					+ " where c.courseID = :courseId"
					+ " and srs.schemeNo in ( :schemeNoList)"
					+ " and srs.academicYear = :academicYear"
					+ " and srs.isActive = 1 and sub.isActive=1 order by  srs.schemeNo";

			Query query = session.createQuery(hqlQuery);
			query.setParameter("academicYear", academicYear);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeId", schemeId);
			query.setParameterList("schemeNoList", schemeNoList);
			list = query.list();
		} catch (Exception e) {
			log.error("Exception in selectAllDetailsForEdit() method"
					+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		log
				.info("Exiting selectAllDetailsForEdit() method of ExamSubjectRuleSettingsImpl class");
		return list;

	}

	public List<Object> select_All(int subjectRuleId, Class className) throws Exception {
		log
				.info("Entering into select_All(int subjectRuleId, Class className) method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("subjectRuleSettingsId", subjectRuleId));
			list = crit.list();
			session.flush();
		} catch (Exception e) {

			log.error("Exception in selectAllDetailsForEdit() method"
					+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		log
				.info("Exiting select_All(int subjectRuleId, Class className) method of ExamSubjectRuleSettingsImpl class");
		return list;

	}

	public List<Object> select_All(int subjectRuleId,
			String isThoeryORPractical, Class className) throws Exception {
		log
				.info("Entering into select_All(int subjectRuleId, String isThoeryORPractical,Class className) method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("subjectRuleSettingsId", subjectRuleId));
			if (isThoeryORPractical != null
					&& isThoeryORPractical.trim().length() > 0) {
				if (isThoeryORPractical.equalsIgnoreCase("theory"))
					crit.add(Restrictions.eq("isTheoryPractical", "t"));
				else
					crit.add(Restrictions.eq("isTheoryPractical", "p"));
			}
			list = crit.list();
			session.flush();
		} catch (Exception e) {
			log
					.error("Exception in select_All(int subjectRuleId, String isThoeryORPractical,Class className) method"
							+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		log
				.info("Exiting select_All(int subjectRuleId, String isThoeryORPractical,Class className) method of ExamSubjectRuleSettingsImpl class");
		return list;

	}

	public boolean updateSubjectRuleSettings(
			ExamSubjectRuleSettingsBO subjRuleBO) throws Exception {
		log
				.info("Entering into updateSubjectRuleSettings() method of ExamSubjectRuleSettingsImpl class");
		boolean isUpdated = true;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {

			session.update(subjRuleBO);
			if (subjRuleBO.getId() == 0) {
				isUpdated = false;

			}
			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error("Exception in updateSubjectRuleSettings() method"
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.info("Exiting updateSubjectRuleSettings() method of ExamSubjectRuleSettingsImpl class");
		return isUpdated;
	}

	public int checkIfDataIsPresent(int academicYear, int schemeNo, int courseId) throws Exception {
		log
				.info("Entering into checkIfDataIsPresent() method of ExamSubjectRuleSettingsImpl class");
		List<Object> singleList = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {

			Criteria crit = session
					.createCriteria(ExamSubjectRuleSettingsBO.class);
			crit.add(Restrictions.eq("academicYear", academicYear));
			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("schemeNo", schemeNo));
			crit.add(Restrictions.eq("isActive", true));
			singleList = new ArrayList<Object>(crit.list());

			tx.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error("Exception in updateSubjectRuleSettings() method"
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.info("Exiting checkIfDataIsPresent() method of ExamSubjectRuleSettingsImpl class");
		return singleList.size();
	}

	public ExamSubjectRuleSettingsBO isDuplicated(int id, int courseId, int schemeNo,
			int academicYear) throws Exception {
		log
				.info("Entering into isDuplicated() method of ExamSubjectRuleSettingsImpl class");
		//boolean duplicate = false;
		ExamSubjectRuleSettingsBO objbo = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			Criteria crit = session.createCriteria(ExamSubjectRuleSettingsBO.class);
			crit.add(Restrictions.eq("courseId", courseId));
			crit.add(Restrictions.eq("academicYear", academicYear));
			crit.add(Restrictions.eq("schemeNo", schemeNo));
			List<ExamSubjectRuleSettingsBO> list = crit.list();
	
			if (list.size() > 0) {
				Iterator it = list.iterator();
				objbo = null;
				while (it.hasNext()) {
					objbo = (ExamSubjectRuleSettingsBO) it.next();
					/*if (objbo.getIsActive() == true) {
	
						throw new DuplicateException();
					} else {
	
						throw new ReActivateException(objbo.getId());
					}*/
				}
			}
			log	.info("Exiting isDuplicated() method of ExamSubjectRuleSettingsImpl class");
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return objbo;
	}

	public void insertORUpdateSubjectRuleSettingsList(
			ExamSubjectRuleSettingsBO updateBO, List<Object> list) throws Exception {
		log
				.info("Entering into insertORUpdateSubjectRuleSettingsList() method of ExamSubjectRuleSettingsImpl class");
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		try {

			Iterator itr = list.iterator();
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			while (itr.hasNext()) {
				ExamSubjectRuleSettingsBO bo = (ExamSubjectRuleSettingsBO) itr
						.next();

				if (bo.getId() == updateBO.getId()) {

					session.update(updateBO);
				}

			}
			tx.commit();
			session.flush();
			session.close();

		} catch (Exception e) {
			log
					.error("Exception in insertORUpdateSubjectRuleSettingsList() method"
							+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				// session.flush();
				session.close();
			}
		}
		log
				.info("Exiting insertORUpdateSubjectRuleSettingsList() method of ExamSubjectRuleSettingsImpl class");
	}

	public List<Object> select_All(Class className, List<Integer> idList) throws Exception {
		log
				.info("Entering into select_All(Class className, List<Integer> idList) method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.in("id", idList));

			list = crit.list();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log
					.error("Exception in select_All(Class className, List<Integer> idList)() method"
							+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();
		}
		log
				.info("Exiting select_All(Class className, List<Integer> idList)() method of ExamSubjectRuleSettingsImpl class");
		return list;

	}

	public ArrayList<ExamSubjectRuleSettingsBO> getList(
			List<Integer> courseIds, List<Integer> schemeNoList,
			int academicYear) throws Exception {
		log
				.info("Entering into getList() method of ExamSubjectRuleSettingsImpl class");
		ArrayList<ExamSubjectRuleSettingsBO> list = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		Criteria crit = session.createCriteria(ExamSubjectRuleSettingsBO.class);
		crit.add(Restrictions.eq("academicYear", academicYear));
		crit.add(Restrictions.in("courseId", courseIds));
		crit.add(Restrictions.in("schemeNo", schemeNoList));
		list = new ArrayList<ExamSubjectRuleSettingsBO>(crit.list());
		if (list.size() > 0) {

			return list;
		} else {
			list = new ArrayList<ExamSubjectRuleSettingsBO>();
		}
		session.flush();
		session.close();
		log
				.info("Exiting getList() method of ExamSubjectRuleSettingsImpl class");
		}catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;
	}

	public List<Integer> copyToRelatedTable(String className,
			List<Integer> newSubjectRuleIds, List<Integer> oldIdList)
			throws Exception {
		log
				.info("Entering into copyToRelatedTable() method of ExamSubjectRuleSettingsImpl class");
		List<Integer> idsList = null;
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Iterator itr1 = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		for (int i = 0; i < oldIdList.size(); i++) {

			int oldId = oldIdList.get(i);
			int newId = newSubjectRuleIds.get(i);
			Criteria crit1 = session.createCriteria(getClass(className));
			crit1.add(Restrictions.eq("subjectRuleSettingsId", oldId));

			list = new ArrayList<Object[]>(crit1.list());
			itr1 = list.iterator();
			// --------------Copy
			// ExamSubjectRuleSettingsSubInternal----------------//
			if (className.equals("ExamSubjectRuleSettingsSubInternalBO")) {
				ArrayList<ExamSubjectRuleSettingsSubInternalBO> tempList = new ArrayList<ExamSubjectRuleSettingsSubInternalBO>();

				while (itr1.hasNext()) {
					ExamSubjectRuleSettingsSubInternalBO subIntBO = (ExamSubjectRuleSettingsSubInternalBO) itr1
							.next();

					tempList.add(new ExamSubjectRuleSettingsSubInternalBO(
							newId, subIntBO.getInternalExamTypeId(), subIntBO
									.getMinimumMark(), subIntBO
									.getEnteredMaxMark(), subIntBO
									.getMaximumMark(), subIntBO
									.getIsTheoryPractical(), subIntBO
									.getIsActive()));

				}
				idsList = insert_List_BO(tempList);
			}

			// --------------Copy
			// ExamSubjectRuleSettingsAssignment----------------//
			else if (className.equals("ExamSubjectRuleSettingsAssignmentBO")) {
				ArrayList<ExamSubjectRuleSettingsAssignmentBO> tempList = new ArrayList<ExamSubjectRuleSettingsAssignmentBO>();
				while (itr1.hasNext()) {
					ExamSubjectRuleSettingsAssignmentBO assignmentBO = (ExamSubjectRuleSettingsAssignmentBO) itr1
							.next();

					tempList.add(new ExamSubjectRuleSettingsAssignmentBO(newId,
							assignmentBO.getAssignmentTypeId(), assignmentBO
									.getMinimumMark(), assignmentBO
									.getMaximumMark(), assignmentBO
									.getIsTheoryPractical(), assignmentBO
									.getIsActive()));

				}
				idsList = insert_List_BO(tempList);
			}
			// --------------Copy
			// ExamSubjectRuleSettingsAttendance----------------//

			else if (className.equals("ExamSubjectRuleSettingsAttendanceBO")) {
				ArrayList<ExamSubjectRuleSettingsAttendanceBO> tempList = new ArrayList<ExamSubjectRuleSettingsAttendanceBO>();
				while (itr1.hasNext()) {
					ExamSubjectRuleSettingsAttendanceBO attendanceBO = (ExamSubjectRuleSettingsAttendanceBO) itr1
							.next();

					tempList.add(new ExamSubjectRuleSettingsAttendanceBO(newId,
							attendanceBO.getAttendanceTypeId(), attendanceBO
									.getIsLeave(), attendanceBO
									.getIsCoCurricular(), attendanceBO
									.getIsTheoryPractical(), attendanceBO
									.getIsActive()));

				}
				idsList = insert_List_BO(tempList);
			}
			// --------------Copy
			// ExamSubjectRuleSettingsMultipleAnsScript----------------//
			else if (className
					.equals("ExamSubjectRuleSettingsMultipleAnsScriptBO")) {
				ArrayList<ExamSubjectRuleSettingsMultipleAnsScriptBO> tempList = new ArrayList<ExamSubjectRuleSettingsMultipleAnsScriptBO>();
				while (itr1.hasNext()) {
					ExamSubjectRuleSettingsMultipleAnsScriptBO multipleAnsScriptBO = (ExamSubjectRuleSettingsMultipleAnsScriptBO) itr1
							.next();

					tempList
							.add(new ExamSubjectRuleSettingsMultipleAnsScriptBO(
									newId, multipleAnsScriptBO
											.getMultipleAnswerScriptId(),
									multipleAnsScriptBO.getValue(),
									multipleAnsScriptBO.getIsTheoryPractical(),
									multipleAnsScriptBO.getIsActive()));

				}
				idsList = insert_List_BO(tempList);
			}

			// --------------Copy
			// ExamSubjecRuleSettingsMultipleEvaluator----------------//

			else if (className
					.equals("ExamSubjecRuleSettingsMultipleEvaluatorBO")) {
				ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO> tempList = new ArrayList<ExamSubjecRuleSettingsMultipleEvaluatorBO>();
				while (itr1.hasNext()) {
					ExamSubjecRuleSettingsMultipleEvaluatorBO multipleEvaluatorBO = (ExamSubjecRuleSettingsMultipleEvaluatorBO) itr1
							.next();

					tempList.add(new ExamSubjecRuleSettingsMultipleEvaluatorBO(
							newId, multipleEvaluatorBO.getEvaluatorId(),
							multipleEvaluatorBO.getNoOfEvaluations(),
							multipleEvaluatorBO.getTypeOfEvaluation(),
							multipleEvaluatorBO.getIsTheoryPractical(),
							multipleEvaluatorBO.getIsActive()));

				}
				idsList = insert_List_BO(tempList);
			}
		}
		session.flush();
		session.close();
		if (idsList != null && idsList.size() > 0) {
			log
					.info("Exiting copyToRelatedTable() method of ExamSubjectRuleSettingsImpl class");
			return idsList;
		} else {
			log
					.info("Exiting copyToRelatedTable() method of ExamSubjectRuleSettingsImpl class");
			return new ArrayList<Integer>();
		}

	}

	public List<Integer> insert_List_BO(ArrayList listBO) throws Exception {
		log
				.info("Entering into insert_List_BO() method of ExamSubjectRuleSettingsImpl class");
		List<Integer> idsList = new ArrayList<Integer>();
		Transaction tx = null;
		Session session = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		try {
			for (Object obj : listBO) {
				session = sessionFactory.openSession();
				tx = session.beginTransaction();
				if (obj instanceof ExamSubjectRuleSettingsSubInternalBO) {
					ExamSubjectRuleSettingsSubInternalBO bo = (ExamSubjectRuleSettingsSubInternalBO) obj;
					session.save(bo);
					idsList.add(bo.getId());
				} else if (obj instanceof ExamSubjectRuleSettingsAssignmentBO) {
					ExamSubjectRuleSettingsAssignmentBO bo = (ExamSubjectRuleSettingsAssignmentBO) obj;
					session.save(bo);
					idsList.add(bo.getId());
				} else if (obj instanceof ExamSubjectRuleSettingsAttendanceBO) {
					ExamSubjectRuleSettingsAttendanceBO bo = (ExamSubjectRuleSettingsAttendanceBO) obj;
					session.save(bo);
					idsList.add(bo.getId());
				} else if (obj instanceof ExamSubjectRuleSettingsMultipleAnsScriptBO) {
					ExamSubjectRuleSettingsMultipleAnsScriptBO bo = (ExamSubjectRuleSettingsMultipleAnsScriptBO) obj;
					session.save(bo);
					idsList.add(bo.getId());
				} else if (obj instanceof ExamSubjecRuleSettingsMultipleEvaluatorBO) {
					ExamSubjecRuleSettingsMultipleEvaluatorBO bo = (ExamSubjecRuleSettingsMultipleEvaluatorBO) obj;
					session.save(bo);
					idsList.add(bo.getId());
				}
				tx.commit();
				session.flush();
				session.close();
			}

		} catch (Exception e) {
			log.error("Exception in insert_List_BO() method" + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.info("Exiting insert_List_BO() method of ExamSubjectRuleSettingsImpl class");
		return idsList;
	}

	public int update(Object obj) {
		log
				.info("Entering into update(Object obj) method of ExamSubjectRuleSettingsImpl class");
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int updated = 0;
		try {
			session.update(obj);
			tx.commit();
			updated = 1;
			session.flush();
			session.close();
		} catch (Exception e) {
			log
					.error("Exception in update(Object obj) method"
							+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		log
				.info("Exiting update(Object obj) method of ExamSubjectRuleSettingsImpl class");
		return updated;
	}

	public ExamSubjectRuleSettingsAttendanceBO select_Unique_forAtt(
			int subjectRuleId, String isTheoryPractical) throws Exception {
		log
				.info("Entering into select_Unique_forAtt() method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		Object object = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamSubjectRuleSettingsAttendanceBO.class);
			crit.add(Restrictions.eq("subjectRuleSettingsId", subjectRuleId));
			crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.eq("isTheoryPractical", isTheoryPractical));
			object = crit.uniqueResult();
			// session.flush();

		} catch (Exception e) {
			log.error("Exception in select_Unique_forAtt() method"
					+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}

		log
				.info("Exiting select_Unique_forAtt() method of ExamSubjectRuleSettingsImpl class");
		return (object == null ? null
				: (ExamSubjectRuleSettingsAttendanceBO) object);
	}

	public ExamSubjectRuleSettingsBO getSubRuleSettingsOfFromAcaYear(
			Integer courseID, int schemeNo, int fromAcademicYear)throws Exception {
		log
				.info("Entering into getSubRuleSettingsOfFromAcaYear() method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		Object object = null;

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamSubjectRuleSettingsBO.class);
			crit.add(Restrictions.eq("courseId", courseID));
			crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.eq("schemeNo", schemeNo));
			crit.add(Restrictions.eq("academicYear", fromAcademicYear));
			List<Object[]> l = crit.list();
			if (l != null && l.size() > 0) {
				object = l.get(0);
			}
			// session.flush();

		} catch (Exception e) {
			log.error("Exception in getSubRuleSettingsOfFromAcaYear() method"
					+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
		}

		log
				.info("Exiting getSubRuleSettingsOfFromAcaYear() method of ExamSubjectRuleSettingsImpl class");
		return (object == null ? null : (ExamSubjectRuleSettingsBO) object);
	}

	// public HashMap<Integer, ArrayList<Integer>> getScheme_sub(
	// int courseSchemeId, int courseId, int academicYear, int noScheme) {
	// HashMap<Integer, ArrayList<Integer>> map;
	// ArrayList<Integer> l;
	// SessionFactory sessionFactory = null;
	// Session session = null;
	// try {
	// sessionFactory = HibernateUtil.getSessionFactory();
	// session = sessionFactory.openSession();
	// String sqlQuery =
	// "SELECT csd.semester_year_no AS sy, sgs.subject_id AS sid"
	// + " FROM  curriculum_scheme_subject css LEFT JOIN"
	// +
	// " curriculum_scheme_duration csd ON css.curriculum_scheme_duration_id = csd.id"
	// + " LEFT JOIN curriculum_scheme cs ON csd.curriculum_scheme_id = cs.id"
	// + " LEFT JOIN subject_group_subjects sgs"
	// + " ON css.subject_group_id = sgs.subject_group_id"
	// + " WHERE cs.course_id = :courseId"
	// + " AND cs.course_scheme_id = :courseSchemeId"
	// + " AND cs.no_scheme =:schemeNo"
	// + " AND csd.academic_year = :academicYear;";
	//
	// Query query = session.createSQLQuery(sqlQuery);
	// query.setParameter("academicYear", academicYear);
	// query.setParameter("courseId", courseId);
	// query.setParameter("courseSchemeId", courseSchemeId);
	// query.setParameter("schemeNo", noScheme);
	// Iterator<Object[]> list = query.list().iterator();
	// int sy;
	// while (list.hasNext()) {
	// Object[] row = (Object[]) list.next();
	//
	// l = new ArrayList<Integer>();
	// sy = (Integer) row[0];
	// l.add((Integer) row[1]);
	// map.put(sy, l);
	// }
	//
	// } catch (Exception e) {
	// log.error("Exception in selectAllDetailsForEdit() method"
	// + e.getMessage());
	// if (session != null) {
	// session.flush();
	// session.close();
	// }
	//
	// }
	// log
	// .info("Exiting selectAllDetailsForEdit() method of ExamSubjectRuleSettingsImpl class");
	// return map;
	//
	// }

	public ArrayList<KeyValueTO> select_SchemeByCourse(int courseId,
			int academicYear) throws Exception {
		Session session = null;
		ArrayList<KeyValueTO> list1 = new ArrayList<KeyValueTO>();
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String q = " SELECT cs.course_scheme_id, csd.semester_year_no"
					+ " FROM curriculum_scheme_duration csd LEFT JOIN"
					+ " curriculum_scheme cs  ON csd.curriculum_scheme_id = cs.id"
					+ " WHERE cs.course_id = :courseId AND csd.academic_year = :academicYear"
					+ " AND csd.start_date IS NOT NULL  order by csd.semester_year_no";
			Query query = session.createSQLQuery(q);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			Iterator<Object[]> itr = query.list().iterator();

			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				if (row[0] != null && row[1] != null) {
					list1.add(new KeyValueTO((Integer) row[0], row[1]
							.toString()));
				}

			}
			session.flush();

		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return list1;
	}

	public boolean newSubjects(Integer courseSchemeId, Integer courseId,
			int schemeNo, int academicYear) throws Exception {
		Session session = null;
		boolean flag = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String q = " select distinct sgs.subject_id  from subject_group_subjects as sgs   "
					+ " join subject_group ON sgs.subject_group_id = subject_group.id  "
					+ " join curriculum_scheme_subject as css on css.subject_group_id = subject_group.id  "
					+ " join curriculum_scheme_duration as csd on css.curriculum_scheme_duration_id = csd.id  "
					+ " join curriculum_scheme as cs on csd.curriculum_scheme_id = cs.id  "
					+ " where cs.course_id = :courseId and cs.course_scheme_id = :courseSchemeId  "
					+ " and csd.academic_year = :academicYear and csd.semester_year_no = :schemeNo  "
					+ " and sgs.is_active = 1 and sgs.subject_id not in (  "
					+ " select distinct subject_id from EXAM_subject_rule_settings srs  "
					+ " where srs.course_id = :courseId and srs.scheme_no = :schemeNo  "
					+ " and srs.academic_year = :academicYear)  ";
			Query query = session.createSQLQuery(q);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("courseSchemeId", courseSchemeId);

			List<Object[]> list = query.list();

			if (list != null && list.size() > 0) {
				flag = true;
			}
			session.flush();

		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}

		return flag;
	}

	public List<Object[]> getSubjects(Integer courseId,Integer courseSchemeId, 
			int schemeNo, int academicYear) throws Exception  {
		Session session = null;
		List<Object[]> list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String q = " select distinct sgs.subject_id, subject.name  from subject_group_subjects as sgs   "
					+ " join subject_group ON sgs.subject_group_id = subject_group.id  "
					+ " join curriculum_scheme_subject as css on css.subject_group_id = subject_group.id  "
					+ " join curriculum_scheme_duration as csd on css.curriculum_scheme_duration_id = csd.id  "
					+ " join curriculum_scheme as cs on csd.curriculum_scheme_id = cs.id  "
					+ " join subject ON sgs.subject_id = subject.id "
					+ " where cs.course_id = :courseId and cs.course_scheme_id = :courseSchemeId  "
					+ " and csd.academic_year = :academicYear and csd.semester_year_no = :schemeNo  "
					+ " and sgs.is_active = 1 and sgs.subject_id not in (  "
					+ " select distinct subject_id from EXAM_subject_rule_settings srs  "
					+ " where srs.course_id = :courseId and srs.scheme_no = :schemeNo  "
					+ " and srs.academic_year = :academicYear)  ";
			Query query = session.createSQLQuery(q);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("courseSchemeId", courseSchemeId);

			list = query.list();

		} catch (Exception e) {
			if (session != null) {
				session.close();
			}

		}

		return list;
	}
	
	private boolean checkDuplicate(int academicYear, int courseId,int schemeNo, int subjectId) 
	{
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String q = " SELECT id"
					+ " FROM exam_subject_rule_settings "
					+ " WHERE course_id = :courseId AND academic_year = :academicYear"
					+ " AND scheme_no= :schemeNo and subject_id=:subjectId";
			Query query = session.createSQLQuery(q);
			query.setParameter("courseId", courseId);
			query.setParameter("academicYear", academicYear);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("subjectId",subjectId);
			Iterator<Object[]> itr = query.list().iterator();
			if(itr.hasNext()) 
			{
				return true;
			}
			session.flush();

		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}

		}
		return false;
	}

	public boolean deleteSubjectRuleSettingSubjectWise(String id)
	{
		log.info("Entering into delete_SubjectRuleSettings() method of ExamSubjectRuleSettingsImpl class");
		Session session = null;
		Transaction tx = null;
		try 
		{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			String hql = " update ExamSubjectRuleSettingsBO srs"
			+ " set srs.isActive = :isActive"
			+ " where srs.id = :id";

			Query query = session.createQuery(hql);
			query.setParameter("id",Integer.parseInt(id));
			query.setParameter("isActive", false);
			query.executeUpdate();
			tx.commit();
			session.flush();
			return true;
		}
		catch (Exception e) 
		{
			log.error("Exception in delete_SubjectRuleSettings() method"+ e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			if (tx != null)
				tx.rollback();
		}
		log.info("Exiting delete_SubjectRuleSettings() method of ExamSubjectRuleSettingsImpl class");
		return false;
	}
}
