package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.bo.exam.CourseSchemeUtilBO;
import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamDefinitionProgramBO;
import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.bo.exam.ExamInternalExamDetailsBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.bo.exam.IExamGenBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamDefinitionForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

@SuppressWarnings("unchecked")
public class ExamDefinationImpl extends ExamGenImpl {

	private static final Log log = LogFactory.getLog(ExamDefinationImpl.class);

	/*
	 * select active objects of Class name passed
	 */
	public List<Object> select_ExamDefn(Class className,String academicYear)throws Exception {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("delIsActive", true));
			crit.add(Restrictions.eq("academicYear", Integer.parseInt(academicYear)));
			crit.addOrder(Order.desc("year")).addOrder(Order.desc("month"));

			list = crit.list();

			session.flush();

			// session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}
	public List<Object> select_ExamDefnPrgType(Class className,String academicYear, ArrayList<ExamProgramTypeUtilBO> listPT)throws Exception {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(className);
			crit.add(Restrictions.eq("delIsActive", true));
			crit.add(Restrictions.eq("academicYear", Integer.parseInt(academicYear)));
			crit.addOrder(Order.desc("year")).addOrder(Order.desc("month"));

			list = crit.list();

			session.flush();

			// session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}
	
	
	/*public List<Object> select_ExamDefn1(ExamDefinitionForm objForm) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Object> listTO;
		
		try{
		String SQL_QUERY = " from ExamDefinitionBO e where e.delIsActive=true and e.academicYear="+objForm.getAcademicYear();

		Query query = session.createQuery(SQL_QUERY);
		List<ExamDefinitionBO> list = query.list();
		Iterator<ExamDefinitionBO> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinitionBO row = (ExamDefinitionBO) it.next();
			listTO.add(new KeyValueTO(row.getId(), row.getName()));
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return listTO;

	}*/
	

	public ArrayList<KeyValueTO> getInternalExamName() throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		try{
		String SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%int%'";

		Query query = session.createQuery(SQL_QUERY);
		List<ExamDefinitionBO> list = query.list();
		Iterator<ExamDefinitionBO> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinitionBO row = (ExamDefinitionBO) it.next();
			listTO.add(new KeyValueTO(row.getId(), row.getName()));
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return listTO;

	}

	public ArrayList<KeyValueTO> getInternalExamName(
			ArrayList<Integer> listOfProgram) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		try{
		String SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%int%' and e.programId in (:listPrgIds)";

		Query query = session.createQuery(SQL_QUERY);
		query.setParameterList("listPrgIds", listOfProgram);
		List<ExamDefinitionBO> list = query.list();
		Iterator<ExamDefinitionBO> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinitionBO row = (ExamDefinitionBO) it.next();
			int id=(row.getId()==null?0:row.getId());
			listTO.add(new KeyValueTO(id, row.getName()));
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return listTO;

	}

	public List<KeyValueTO> getInternalExamListByAcademicYear(int academicYear){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		String SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.id = 4 "
				+ "and e.academicYear = :academicYear";

		Query query = session.createQuery(SQL_QUERY);
		query.setParameter("academicYear", academicYear);
		List<ExamDefinitionBO> list = query.list();
		Iterator<ExamDefinitionBO> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinitionBO row = (ExamDefinitionBO) it.next();
			listTO.add(new KeyValueTO(row.getId(), row.getName()));
		}

		return listTO;
	}

	public boolean delete(int id, int programId, String userId, boolean fromDel, boolean isActive) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			// set ExamDefinitionProgramBO isActive = false
			String QUERY1 = " from ExamDefinitionProgramBO e where e.examDefnId = :examId";

			Query query1 = session.createQuery(QUERY1);
			query1.setParameter("examId", id);
			Iterator<ExamDefinitionProgramBO> itr1 = query1.list().iterator();
			while (itr1.hasNext()) {
				ExamDefinitionProgramBO objBO1 = (ExamDefinitionProgramBO) itr1
						.next();
				objBO1.setLastModifiedDate(new Date());

				if (fromDel) {
					objBO1.setDelIsActive(false);
					
				} if(!isActive){
					objBO1.setIsActive(false);
				}
				objBO1.setModifiedBy(userId);
				session.update(objBO1);

			}
			// set ExamExamCourseSchemeDetailsBO isActive = false
			String QUERY2 = " from ExamExamCourseSchemeDetailsBO e where e.examId = :examId and programId = :programId";
			Query query2 = session.createQuery(QUERY2);
			query2.setParameter("examId", id);
			query2.setParameter("programId", programId);
			Iterator<ExamExamCourseSchemeDetailsBO> itr2 = query2.list()
					.iterator();
			while (itr2.hasNext()) {
				ExamExamCourseSchemeDetailsBO objBO2 = (ExamExamCourseSchemeDetailsBO) itr2
						.next();
				objBO2.setLastModifiedDate(new Date());
				if (fromDel) {
					objBO2.setIsActive(false);
				}

				objBO2.setModifiedBy(userId);
				session.update(objBO2);

			}
			// if all programs of examdefn
			String QUERY3 = " from ExamDefinitionProgramBO e where e.examDefnId = :examId and e.delIsActive=1";
			Query query3 = session.createQuery(QUERY3);
			query3.setParameter("examId", id);
			List<ExamDefinitionProgramBO> list = query3.list();
			if (list.size() == 0) {

				ExamDefinitionBO objBO3 = (ExamDefinitionBO) session.get(
						ExamDefinitionBO.class, id);
				objBO3.setLastModifiedDate(new Date());
				if (fromDel) {
					objBO3.setDelIsActive(false);
				} if(!isActive) {
					objBO3.setIsActive(false);
				}

				objBO3.setModifiedBy(userId);
				session.update(objBO3);

			}

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

	public boolean updateDefPrg(int id, int programId, String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			// set ExamDefinitionProgramBO isActive = false
			String QUERY1 = " from ExamDefinitionProgramBO e where e.examDefnId = :examId"
					+ " and programId = :programId";

			Query query1 = session.createQuery(QUERY1);
			query1.setParameter("examId", id);
			query1.setParameter("programId", programId);
			Iterator<ExamDefinitionProgramBO> itr1 = query1.list().iterator();
			while (itr1.hasNext()) {
				ExamDefinitionProgramBO objBO1 = (ExamDefinitionProgramBO) itr1
						.next();
				objBO1.setLastModifiedDate(new Date());
				objBO1.setIsActive(true);
				objBO1.setModifiedBy(userId);
				session.update(objBO1);

			}
			// set ExamExamCourseSchemeDetailsBO isActive = false
			String QUERY2 = " from ExamExamCourseSchemeDetailsBO e where e.examId = :examId and programId = :programId";
			Query query2 = session.createQuery(QUERY2);
			query2.setParameter("examId", id);
			query2.setParameter("programId", programId);
			Iterator<ExamExamCourseSchemeDetailsBO> itr2 = query2.list()
					.iterator();
			while (itr2.hasNext()) {
				ExamExamCourseSchemeDetailsBO objBO2 = (ExamExamCourseSchemeDetailsBO) itr2
						.next();
				objBO2.setLastModifiedDate(new Date());
				objBO2.setIsActive(true);
				objBO2.setModifiedBy(userId);
				session.update(objBO2);

			}

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

	public void reActivate(int id, String userId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			// set exam_definition table
			ExamDefinitionBO objBO1 = (ExamDefinitionBO) session.get(
					ExamDefinitionBO.class, id);
			objBO1.setLastModifiedDate(new Date());
//			objBO1.setIsActive(true);
			objBO1.setDelIsActive(true);
			objBO1.setModifiedBy(userId);
			session.update(objBO1);
			// set exam_def_program table

			String QUERY1 = " from ExamDefinitionProgramBO e where e.examDefnId = :examId";

			Query query1 = session.createQuery(QUERY1);
			query1.setParameter("examId", id);

			Iterator<ExamDefinitionProgramBO> itr1 = query1.list().iterator();
			while (itr1.hasNext()) {
				ExamDefinitionProgramBO objBO2 = (ExamDefinitionProgramBO) itr1
						.next();
				objBO2.setLastModifiedDate(new Date());
//				objBO2.setIsActive(true);
				objBO2.setDelIsActive(true);
				objBO2.setModifiedBy(userId);
				session.update(objBO2);

			}

			// set exam_course_scheme table

			// set ExamExamCourseSchemeDetailsBO isActive = false
			String QUERY2 = " from ExamExamCourseSchemeDetailsBO e where e.examId = :examId ";
			Query query2 = session.createQuery(QUERY2);
			query2.setParameter("examId", id);
			Iterator<ExamExamCourseSchemeDetailsBO> itr2 = query2.list()
					.iterator();
			while (itr2.hasNext()) {
				ExamExamCourseSchemeDetailsBO objBO3 = (ExamExamCourseSchemeDetailsBO) itr2
						.next();
				objBO3.setLastModifiedDate(new Date());
				objBO3.setIsActive(true);
				objBO3.setModifiedBy(userId);
				session.update(objBO3);

			}

			transaction.commit();
			session.flush();
			// session.close();

		} catch (Exception e) {
			log.error(e.getMessage());
			if (transaction != null) {
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
				session.close();
			}

		}
	}

	public ArrayList<Integer> getCoursesForPrgs(ArrayList<Integer> programIDList) throws Exception {
		ArrayList<Integer> listCourseIds = new ArrayList<Integer>();
		Session session = null;
		try{
		String SQL_QUERY = "Select e.courseID from ExamCourseUtilBO e "
				+ " where e.programID in (:listPrgIds)";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Query query = session.createQuery(SQL_QUERY);
		query.setParameterList("listPrgIds", programIDList);

		Iterator it = query.iterate();
		while (it.hasNext()) {
			Object[] row = (Object[]) it.next();

			listCourseIds.add(((Integer) row[0]).intValue());
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return listCourseIds;
	}

	public ArrayList<ExamExamCourseSchemeDetailsBO> getCourseSchemeDet(
			ArrayList<Integer> listCourseIds) throws Exception {
		Session session = null;
		try{
		String SQL_QUERY = "from ExamExamCourseSchemeDetailsBO e "
				+ " where e.courseId in (:listCourseIds)";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Query query = session.createQuery(SQL_QUERY);
		query.setParameterList("listCourseIds", listCourseIds);
		return new ArrayList<ExamExamCourseSchemeDetailsBO>(query.list());
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	public ArrayList<ExamProgramTypeUtilBO> getNamesForProgramTypeIds(
			ArrayList<Integer> listProgramTypeIds) throws Exception {
		Session session = null;
		try{
		String SQL_QUERY = "from ExamProgramTypeUtilBO e "
				+ " where e.id in (:listProgramTypeIds)";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Query query = session.createQuery(SQL_QUERY);
		query.setParameterList("listProgramTypeIds", listProgramTypeIds);
		return new ArrayList<ExamProgramTypeUtilBO>(query.list());
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	public ArrayList<ExamProgramUtilBO> getNamesForProgramIds(
			ArrayList<Integer> listProgramIds) throws Exception {
		Session session = null;
		try{
		String SQL_QUERY = "from ExamProgramUtilBO e "
				+ " where e.id in (:listProgramIds)";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Query query = session.createQuery(SQL_QUERY);
		query.setParameterList("listProgramIds", listProgramIds);
		return new ArrayList<ExamProgramUtilBO>(query.list());
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	public List<ExamTypeUtilBO> select_ExamType() throws Exception {
		Session session = null;
		List<ExamTypeUtilBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria(ExamTypeUtilBO.class);

			list = crit.list();
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

	// set IS CURRENT value to false for all the exams
	public void removeIsCurrentForOtherExams() throws Exception {
		Session session = null;
		Transaction transaction = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session.createCriteria(ExamDefinitionBO.class);
		crit.add(Restrictions.eq("isCurrent", true));
		// crit.add(Restrictions.eq("isActive", true));
		// crit.add(Restrictions.eq("delIsActive", true));

		List<ExamDefinitionBO> list = crit.list();
		if (list.size() > 0) {
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamDefinitionBO> itr = list.iterator();
			int id = 0;
			while (itr.hasNext()) {
				ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) itr
						.next();
				id = examDefinitionBO.getId();
			}
			ExamDefinitionBO objBO = (ExamDefinitionBO) session.get(
					ExamDefinitionBO.class, id);

			objBO.setIsCurrent(false);

			session.update(objBO);
			transaction.commit();
		}

		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public ArrayList<Integer> isDuplicated(String examCode, int joiningBatch,
			String month, int academicYear, int examTypeId, int year)
			throws Exception {
		ArrayList<Integer> examIDList = new ArrayList<Integer>();
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session.createCriteria(ExamDefinitionBO.class);
		// crit.add(Restrictions.eq("examCode", examCode));
		crit.add(Restrictions.eq("examForJoiningBatch", joiningBatch));
		crit.add(Restrictions.eq("month", month));
		crit.add(Restrictions.eq("academicYear", academicYear));
		crit.add(Restrictions.eq("examTypeID", examTypeId));
		crit.add(Restrictions.eq("year", year));

		List<IExamGenBO> list = crit.list();

		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				ExamDefinitionBO eDBO = (ExamDefinitionBO) it.next();

				if (eDBO.getIsActive() == true) {
					// throw new DuplicateException();
					examIDList.add(eDBO.getId());
				}
			}
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return examIDList;
	}

	public void isDuplicated_CourseSchemeCombo(int courseId, int schemeNo,
			int courseSchemeId, int programId, Integer examId)
			throws DuplicateException {

		Session session = null;
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session
				.createCriteria(ExamExamCourseSchemeDetailsBO.class);

		crit.add(Restrictions.eq("examId", examId));
		crit.add(Restrictions.eq("courseId", courseId));
		crit.add(Restrictions.eq("schemeNo", schemeNo));
		crit.add(Restrictions.eq("courseSchemeId", courseSchemeId));
		crit.add(Restrictions.eq("programId", programId));

		List<ExamExamCourseSchemeDetailsBO> list = crit.list();

		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				ExamExamCourseSchemeDetailsBO eDBO = (ExamExamCourseSchemeDetailsBO) it
						.next();

				if (eDBO.getIsActive() == true) {
					throw new DuplicateException("duplicate");
				}
			}
		}
		session.flush();
		session.close();

	}

	public void isExamCodeDuplicated(String examCode) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session.createCriteria(ExamDefinitionBO.class);

		crit.add(Restrictions.eq("examCode", examCode));

		List<IExamGenBO> list = crit.list();

		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				ExamDefinitionBO eDBO = (ExamDefinitionBO) it.next();

				if (eDBO.getIsActive() == true) {
					throw new DuplicateException("examCode");
				} else if (eDBO.getIsActive() == false) {
					throw new ReActivateException(eDBO.getId());
				}
			}
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/*
	 * Insert a object into respective tables
	 */
	public int insert(ExamDefinitionBO obj) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int id = 0;
		try {
			session.save(obj);
			tx.commit();
			id = obj.getId();
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
		}
		return id;
	}

	public ArrayList<CurriculumSchemeUtilBO> select_CourseScheme(String program) throws Exception {
		ArrayList<CurriculumSchemeUtilBO> list = new ArrayList<CurriculumSchemeUtilBO>();
		Session session = null;
		try{
		String HQL_QUERY = "select c.courseId, c.courseSchemeId, c.courseSchemeUtilBO.name, "
				+ "c.examCourseUtilBO.courseName, max(c.noScheme),c.examCourseUtilBO.program.programID"
				+ " from CurriculumSchemeUtilBO c "
				+ "where c.examCourseUtilBO.program.programID in ("
				+ program
				+ ") group by c.courseId, c.courseSchemeId";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);

		Iterator<Object[]> itr = new ArrayList<Object[]>(query.list())
				.iterator();

		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			CurriculumSchemeUtilBO bo = new CurriculumSchemeUtilBO();
			ExamCourseUtilBO ecuBO = new ExamCourseUtilBO();
			ecuBO.setCourseID(Integer.parseInt(obj[0].toString()));
			ecuBO.setCourseName(obj[3].toString());
			bo.setExamCourseUtilBO(ecuBO);
			bo.setCourseSchemeId(Integer.parseInt(obj[1].toString()));
			CourseSchemeUtilBO csuBO = new CourseSchemeUtilBO();
			csuBO.setName(obj[2].toString());
			ExamProgramUtilBO epuBO = new ExamProgramUtilBO();
			epuBO.setProgramID(Integer.parseInt(obj[5].toString()));
			csuBO.setExamProgramUtilBO(epuBO);
			bo.setCourseSchemeUtilBO(csuBO);
			bo.setNoScheme(Integer.parseInt(obj[4].toString()));
			list.add(bo);
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

		return list;

	}

	public ExamDefinitionBO updatableForm(int id) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			ExamDefinitionBO objBO = (ExamDefinitionBO) session.get(
					ExamDefinitionBO.class, id);
			session.flush();
			// session.close();
			return objBO;
		} catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}

	public ArrayList<Integer> getDetails_Internal(int id) throws Exception {
		ArrayList<Integer> internalExamNameId = new ArrayList<Integer>();
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session.createCriteria(ExamInternalExamDetailsBO.class);
		crit.add(Restrictions.eq("examId", id));

		List<ExamInternalExamDetailsBO> list = crit.list();

		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				ExamInternalExamDetailsBO eDBO = (ExamInternalExamDetailsBO) it
						.next();
				if (getExamNameForId(eDBO.getInternalExamNameId()) != null) {

					internalExamNameId.add(eDBO.getInternalExamNameId());
				}
			}
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

		return internalExamNameId;

	}

	public String getDetails_InternalName(int id) throws Exception {
		String internalExamName = "";
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session.createCriteria(ExamInternalExamDetailsBO.class);
		crit.add(Restrictions.eq("examId", id));

		List<ExamInternalExamDetailsBO> list = crit.list();

		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				ExamInternalExamDetailsBO eDBO = (ExamInternalExamDetailsBO) it
						.next();

				internalExamName = internalExamName + ","
						+ getExamNameForId(eDBO.getInternalExamNameId());

			}
		}
		
		if (!internalExamName.equals("")) {
			internalExamName = internalExamName.substring(1, internalExamName
					.length());
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

		return internalExamName;

	}

	private String getExamNameForId(int id) throws Exception {
		String name = "";
		Session session = null;
		try{
		SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();
		ExamDefinitionBO objBO = (ExamDefinitionBO) session.get(
				ExamDefinitionBO.class, id);
		if (objBO.getIsActive()) {
			name = objBO.getName();
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return name;

	}

	public int getDetails_InternalType(int id) throws Exception {

		int internalExamTypeId = 0;
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session.createCriteria(ExamInternalExamTypeBO.class);
		crit.add(Restrictions.eq("examId", id));

		List<ExamInternalExamTypeBO> list = crit.list();

		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				ExamInternalExamTypeBO eDBO = (ExamInternalExamTypeBO) it
						.next();
				internalExamTypeId = eDBO.getId();
			}
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return internalExamTypeId;

	}

	public List<Object> select(Class className) throws Exception {
		Session session = null;
		List<Object> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session.createCriteria((className));
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

	public void delete_perm(int examId, int type, ArrayList<Integer> programId) throws Exception {
		String hql = null;

		try {
			if(programId.size()!=0)
			{	
				if (type == 0) {
					hql = "delete from ExamInternalExamDetailsBO  "
							+ " where examId = :examId";
				} else if (type == 1) {
					hql = "update ExamExamCourseSchemeDetailsBO set isActive=0 "
							+ " where id in (:programId)";
				} else if (type == 2) {
					hql = "update  ExamTimeTableBO tt set isActive=0 where tt.examExamCourseSchemeDetailsBO.id in (:programId)";
				}
	
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
				Session session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
	
				Query q = session.createQuery(hql);
				if (type == 0) {
					q.setParameter("examId", examId);
				}
				if (type == 1 || type == 2) {
					q.setParameterList("programId", programId);
				}
				q.executeUpdate();
				tx.commit();
			// session.close();
			}	
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			
			throw e;
		}
	}

	public void delete_examPrgr(int examId, ArrayList<Integer> programId) throws Exception {
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Criteria crit = session.createCriteria(ExamDefinitionProgramBO.class);
		crit.add(Restrictions.eq("examDefnId", examId));
		crit.add(Restrictions.in("programId", programId));

		List<ExamDefinitionProgramBO> list = crit.list();
		if (list.size() > 0) {
			Iterator<ExamDefinitionProgramBO> itr = list.iterator();
			int id = 0;
			while (itr.hasNext()) {
				ExamDefinitionProgramBO examDefinitionProgramBO = (ExamDefinitionProgramBO) itr
						.next();
				id = examDefinitionProgramBO.getId();
			}
			ExamDefinitionProgramBO objBO = (ExamDefinitionProgramBO) session
					.get(ExamDefinitionProgramBO.class, id);

			objBO.setIsActive(false);

			session.update(objBO);
			tx.commit();
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public ArrayList<ExamExamCourseSchemeDetailsBO> select_ExamCourseSchemeDetails(
			int examId) throws Exception {
		Session session = null;
		
		ArrayList<ExamExamCourseSchemeDetailsBO> list = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Criteria crit = session
				.createCriteria(ExamExamCourseSchemeDetailsBO.class);
		crit.add(Restrictions.eq("examId", examId));
		list = new ArrayList<ExamExamCourseSchemeDetailsBO>(crit.list());
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

		return list;
	}

	public ArrayList<Integer> getCourseList(int programId) throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Session session = null;
		try{
		String HQL_QUERY = "select courseID from ExamCourseUtilBO  where program.programID= :programID";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("programID", programId);
		Iterator<Object> itr = new ArrayList<Object>(query.list()).iterator();

		while (itr.hasNext()) {

			list.add(Integer.parseInt(itr.next().toString()));
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;

	}

	public List select_InternalExamType() throws Exception {
		Session session = null;
		Query query = null;
		try{
		String HQL_QUERY = "select b.id, b.name from ExamInternalExamTypeBO b where b.isActive=1";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		query = session.createQuery(HQL_QUERY);
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return query.list();

	}

	public ArrayList<Integer> getCSIds(
			ArrayList<ExamExamCourseSchemeDetailsBO> examCSDetailsList) throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Session session = null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query query = null;

		for (ExamExamCourseSchemeDetailsBO csBO : examCSDetailsList) {

			String HQL_QUERY = "select distinct cs.id"
					+ " from ExamExamCourseSchemeDetailsBO cs where cs.examId = :examId"
					+ " and cs.courseId =:courseID  and"
					+ " cs.courseSchemeId = :courseSchemeId AND cs.programId =:programId and cs.schemeNo=:schemeNo";

			query = session.createQuery(HQL_QUERY);
			query.setParameter("programId", csBO.getProgramId());
			query.setParameter("examId", csBO.getExamId());
			query.setParameter("courseID", csBO.getCourseId());
			query.setParameter("schemeNo", csBO.getSchemeNo());
			query.setParameter("courseSchemeId", csBO.getCourseSchemeId());
			Iterator<Object> itr = new ArrayList<Object>(query.list())
					.iterator();

			while (itr.hasNext()) {

				list.add(Integer.parseInt(itr.next().toString()));
			}
		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return list;
	}

	/**
	 * This method written by Balaji.S
	 * @param examCode
	 * @param examName
	 * @throws Exception
	 */
	public void isExamDuplicated(String examCode,String examName,int examId) throws Exception {
		Session session = null;
		List<ExamDefinitionBO> list=null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		String hql="from ExamDefinitionBO e where (e.examCode='"+examCode+"' or e.name='"+examName+"') and e.id<>"+examId;
		Query query=session.createQuery(hql);
		list = query.list();
		}
		catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		if (list.size() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				ExamDefinitionBO eDBO = (ExamDefinitionBO) it.next();

				if (eDBO.isDelIsActive() == true) {
					throw new DuplicateException("examCode");
				} else if (eDBO.isDelIsActive() == false) {
					throw new ReActivateException(eDBO.getId());
				}
			}
		}
	}



	public void insertData(ArrayList<ExamExamCourseSchemeDetailsBO> examCSDetailsList) throws Exception{
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		ExamExamCourseSchemeDetailsBO bo;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ExamExamCourseSchemeDetailsBO> tcIterator = examCSDetailsList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				bo = tcIterator.next();
				ExamExamCourseSchemeDetailsBO oldBo=(ExamExamCourseSchemeDetailsBO)session.createQuery("from ExamExamCourseSchemeDetailsBO bo" +
						" where bo.examId="+bo.getExamId()+" and bo.courseId="+bo.getCourseId()+"" +
						" and bo.schemeNo="+bo.getSchemeNo()+" and bo.courseSchemeId="+bo.getCourseSchemeId()
						+" and bo.programId="+bo.getProgramId()+"and bo.isActive="+bo.getIsActive()).uniqueResult();
				if(oldBo==null)
				{
					session.save(bo);
				}
				else
				if(!oldBo.getIsActive())	
				{
					bo.setIsActive(true);
					session.update(bo);
					String hql = "update ExamTimeTableBO tt set isActive=1 where tt.examExamCourseSchemeDetailsBO.id in ("+bo.getId()+")";
					Query q = session.createQuery(hql);
					q.executeUpdate();
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
	
	
	
}
