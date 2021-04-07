package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.bo.exam.ExamStudentSpecializationBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.exam.ExamStudentSpecializationTO;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamStudentSpecializationImpl extends ExamGenImpl {

	// On - SEACH (APPLY for Update)
	public void update(int specializationId, String userId,
			ArrayList<Integer> listOfIds) throws Exception {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		/*String HQL_QUERY = "update ExamStudentSpecializationBO r set"
				+ " r.specializationId = :specializationId, r.modifiedBy = :modifiedBy ,"
				+ " r.lastModifiedDate = :lastModifiedDate"
				+ " where r.id in (:id)";*/
		String HQL_QUERY = "update ExamStudentBioDataBO r set"
			+ " r.specializationId = :specializationId"
			+ " where r.id in (:id)";

		
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameterList("id", listOfIds);
		query.setParameter("specializationId", specializationId);
		/*query.setParameter("modifiedBy", userId);
		query.setParameter("lastModifiedDate", new Date());*/

		query.executeUpdate();
		tx.commit();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	// On - ASSIGN
	public ArrayList<Object> selectUnAssignedStudents(int academicYear,
			int courseId, int schemeNo, String sectionName, int schemeId) throws Exception {

		ArrayList<Object> listBo = null;
		Session session = null;

		String HQL_QUERY = null;
		if (sectionName != null) {

			HQL_QUERY = "select s.id, s.admApplnUtilBO.applnNo, s.rollNo, s.registerNo,"
					+ " s.admApplnUtilBO.personalDataUtilBO.firstName, s.admApplnUtilBO.personalDataUtilBO.middleName,"
					+ " s.admApplnUtilBO.personalDataUtilBO.lastName"
					+ " from StudentUtilBO s"
					+ " where s.admApplnUtilBO.isCancelled=0 and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId = :schemeId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo and s.classSchemewiseUtilBO.classUtilBO.sectionName= :sectionName "
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear = :academicYear and s.id not in"
					+ " (select sp.studentId from ExamStudentBioDataBO sp)"; 
					//+ " (select sp.studentId from ExamStudentSpecializationBO sp)";

		} else {

			HQL_QUERY = "select s.id, s.admApplnUtilBO.applnNo, s.rollNo, s.registerNo,"
					+ " s.admApplnUtilBO.personalDataUtilBO.firstName, s.admApplnUtilBO.personalDataUtilBO.middleName,"
					+ " s.admApplnUtilBO.personalDataUtilBO.lastName"
					+ " from StudentUtilBO s"
					+ " where s.admApplnUtilBO.isCancelled=0 and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId = :schemeId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo "
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear = :academicYear and s.id not in"
					+ " (select sp.studentId from ExamStudentBioDataBO sp)";
					//+ " (select sp.studentId from ExamStudentSpecializationBO sp)";

		}

		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query = session.createQuery(HQL_QUERY);
			if (sectionName != null) {
				query.setParameter("sectionName", sectionName);
			}
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("schemeId", schemeId);
			query.setParameter("academicYear", academicYear);

			listBo = new ArrayList<Object>(query.list());
			session.flush();
			session.close();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			listBo = new ArrayList<Object>();

		}
		return listBo;
	}

	// To get students based on course,schemeNo and section(On - SEARCH)
	public ArrayList<ExamStudentBioDataBO> select_student_specialization(
			String schemeNo, Integer courseId, String sectionName,
			Integer specId, Integer academicYear) throws Exception {

	/*	String HQL_QUERY = "from ExamStudentSpecializationBO e"
				+ " where e.schemeNo = :schemeNo "
				+ " and e.courseId = :courseId and e.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear = :academicYear";*/
		
		

		ArrayList<ExamStudentBioDataBO> listBO = null;
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL_QUERY = "from ExamStudentBioDataBO e"
				+ " where e.studentUtilBO.admApplnUtilBO.isCancelled=0 and e.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo "
				+ " and e.studentUtilBO.classSchemewiseUtilBO.classUtilBO.courseId = :courseId " +
				 " and e.studentUtilBO.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear = :academicYear" +
				 " and e.specializationId IS NOT NULL";
			

			
			if (sectionName != null) {
				HQL_QUERY = HQL_QUERY + " and e.studentUtilBO.classSchemewiseUtilBO.classUtilBO.sectionName = :sectionName";

			}
			if (specId != null && specId.intValue() != 0) {
				HQL_QUERY = HQL_QUERY + " and e.specializationId= :specId ";
			}
			Query query = session.createQuery(HQL_QUERY);
			query.setInteger("schemeNo", Integer.parseInt(schemeNo));
			query.setInteger("academicYear", academicYear);
			query.setInteger("courseId", courseId);
			if (specId != null && specId.intValue() != 0) {
				query.setParameter("specId", specId);
			}
			if (sectionName != null) {
				query.setParameter("sectionName", sectionName);
			}

			listBO = new ArrayList<ExamStudentBioDataBO>(query.list());
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.flush();
				session.close();
			}
			listBO = new ArrayList<ExamStudentBioDataBO>();

		}
		return listBO;
	}

	public String getSectionByClass(int classId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		String section = null;
		String HQL_QUERY = "";
		try{
		HQL_QUERY = "select cs.sectionName " + " from ClassUtilBO cs "
				+ " where cs.id = :classId ";

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("classId", classId);

		List list = query.list();

		if (list != null && list.size() > 0) {
			section = (String) list.get(0);

		}
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

		return section;
	}
	/**
	 * 
	 * @param specializationId
	 * @param studentIds
	 * @throws Exception
	 *//*
	public void updateExamStudentBiodata(List<ExamStudentBioDataBO> examStudentBiodataList) throws Exception {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
		String HQL_QUERY = " update ExamStudentBioDataBO r set "
				+ " r.specializationId = " + specializationId 
				+ " where r.studentId in ( " + studentIds + " ) ";

		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);

		query.executeUpdate();
		tx.commit();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}
	*/
	
	
	/**
	 * 
	 * @param schemeNo
	 * @param courseId
	 * @param sectionName
	 * @param specId
	 * @param academicYear
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> getIdsFromExamStudentBiodata() throws Exception {

		Session session = null;
		Map<Integer, Integer> idMap = new HashMap<Integer, Integer>();
		try {
			String HQL_QUERY = "from ExamStudentBioDataBO e";
			ArrayList<ExamStudentBioDataBO> listBO;
			
			
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Query query = session.createQuery(HQL_QUERY);

			listBO = new ArrayList<ExamStudentBioDataBO>(query.list());
			if(listBO!= null && listBO.size() > 0){
				Iterator<ExamStudentBioDataBO> iter = listBO.iterator();
				while (iter.hasNext()) {
					ExamStudentBioDataBO examStudentBioDataBO = (ExamStudentBioDataBO) iter
							.next();
					idMap.put(examStudentBioDataBO.getStudentId(), examStudentBioDataBO.getId());	
				}
				
			}
			session.flush();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return idMap;
	}
	
	/**
	 * 
	 * @param biodata
	 * @return
	 * @throws Exception
	 */
	public boolean addSpecialization(List<ExamStudentBioDataBO> biodata) throws Exception {
		ExamStudentBioDataBO examStudentBioDataBO;
		Transaction tx=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Iterator<ExamStudentBioDataBO> itr = biodata.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			while (itr.hasNext()) {
				examStudentBioDataBO = itr.next();
				ExamStudentBioDataBO bo=(ExamStudentBioDataBO)session.createQuery("from ExamStudentBioDataBO b" +
						" where b.specializationId="+examStudentBioDataBO.getSpecializationId()+" and b.studentId="+examStudentBioDataBO.getStudentId()).uniqueResult();
				if(bo==null){
				session.saveOrUpdate(examStudentBioDataBO);
				}
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			session.flush();
			session.clear();
			session.close();
			throw new ApplicationException(e);
		}
	}
	
	
	//Code Written By Balaji
	public ArrayList<StudentUtilBO> selectStudents(int academicYear,
			int courseId, int schemeNo, String sectionName, int schemeId) throws Exception {

		ArrayList<StudentUtilBO> listBo = null;
		Session session = null;

		String HQL_QUERY = null;
		if (sectionName != null) {
			HQL_QUERY = "select s "
					+ " from StudentUtilBO s"
					+ " where s.admApplnUtilBO.isCancelled=0 and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId = :schemeId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo and s.classSchemewiseUtilBO.classUtilBO.sectionName= :sectionName "
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear = :academicYear"; 
		} else {
			HQL_QUERY = "select s "
					+ " from StudentUtilBO s"
					+ " where s.admApplnUtilBO.isCancelled=0 and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseId = :courseId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.curriculumSchemeUtilBO.courseSchemeId = :schemeId"
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.semesterYearNo = :schemeNo "
					+ " and s.classSchemewiseUtilBO.curriculumSchemeDurationUtilBO.academicYear = :academicYear";
		}
		try {
			session =HibernateUtil.getSession();
			Query query = session.createQuery(HQL_QUERY);
			if (sectionName != null) {
				query.setParameter("sectionName", sectionName);
			}
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("schemeId", schemeId);
			query.setParameter("academicYear", academicYear);

			listBo = new ArrayList<StudentUtilBO>(query.list());
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			listBo = new ArrayList<StudentUtilBO>();
		}
		return listBo;
	}
	
	
	// On - SEACH (APPLY for Update) code written by balaji
	public void update1(int specializationId, String userId,ArrayList<ExamStudentSpecializationTO> listOfIds) throws Exception {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx=null;
		try{
			session = HibernateUtil.getSession();
			Iterator<ExamStudentSpecializationTO> itr = listOfIds.iterator();
			int count = 0;
			tx = session.beginTransaction();
			tx.begin();
			ExamStudentSpecializationTO to=null;
			while (itr.hasNext()) {
				to = itr.next();
				ExamStudentBioDataBO bo=(ExamStudentBioDataBO)session.createQuery("from ExamStudentBioDataBO b" +
						" where b.specializationId="+specializationId+" and b.studentId="+to.getStudentId()).uniqueResult();
				if(bo==null ){
					ExamStudentBioDataBO bo1=(ExamStudentBioDataBO)session.get(ExamStudentBioDataBO.class,to.getId());
					bo1.setSpecializationId(specializationId);
					session.update(bo1);
				}
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}

	}

	public Collection select_ActiveOnlyByCourse(Class<ExamSpecializationBO> class1,int courseId) throws Exception {
		Session session = null;
		List<Object> list;
		try {
			session = HibernateUtil.getSession();

			Criteria crit = session.createCriteria(class1);
			crit.add(Restrictions.eq("isActive", true));
			crit.add(Restrictions.eq("courseId", courseId));

			list = crit.list();
			session.flush();

		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			list = new ArrayList<Object>();

		}
		return list;

	}
}
