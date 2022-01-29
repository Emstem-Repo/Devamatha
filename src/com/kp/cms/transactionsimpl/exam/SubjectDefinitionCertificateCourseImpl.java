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

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.bo.exam.SubjectGroupUtilBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamSubjectDefCourseForm;
import com.kp.cms.utilities.HibernateUtil;

public class SubjectDefinitionCertificateCourseImpl extends ExamGenImpl {

	private static final Log log = LogFactory.getLog(SubjectDefinitionCertificateCourseImpl.class);
	/**
	 * @param courseId
	 * @param shemeId
	 * @param schemeNo
	 * @param academicYear
	 * @param subId 
	 * @return
	 */
	public ArrayList<ExamSubDefinitionCourseWiseBO> UpdateCertificateCourse(int courseId, int shemeId,int schemeNo, int academicYear, int subId) {

		Session session = null;
		ArrayList<ExamSubDefinitionCourseWiseBO> list ;

		String HQL = " select sub.id, sub.name, sub.code, " +
		             " ( select subjectOrder from ExamSubDefinitionCourseWiseBO def where def.subjectId = sub.id and def.courseId = :courseId and" +
		             " def.schemeNo = :schemeNo and def.academicYear=:academicYear) as subjectOrder,"
		            +" ( select theoryCredit from ExamSubDefinitionCourseWiseBO def where def.subjectId = sub.id and def.courseId = :courseId and" +
	             	" def.schemeNo = :schemeNo and def.academicYear=:academicYear) as theoryCredit,"
	            	+" ( select examSubjectSectionMasterBO.name from ExamSubDefinitionCourseWiseBO def where def.subjectId = sub.id and def.courseId = :courseId and" +
	            	" def.schemeNo = :schemeNo and def.academicYear=:academicYear) as subjectSection,"
	            	+" ( select practicalCredit from ExamSubDefinitionCourseWiseBO def where def.subjectId = sub.id and def.courseId = :courseId and" +
		           " def.schemeNo = :schemeNo and def.academicYear=:academicYear) as practicalCredit"
				+ " from SubjectUtilBO sub"
				+ " where sub.id in"
				+ " (select distinct sgs.subjectUtilBO.id"
				+ " from SubjectGroupSubjectsUtilBO sgs"
				+ " where sgs.isActive=1 and sgs.subjectGroupUtilBO.id in ("
				+ " select css.subjectGroupId from CurriculumSchemeSubjectUtilBO css"
				+ " where css.curriculumSchemeDurationId in ("
				+ " select csd.id from CurriculumSchemeDurationUtilBO csd"
				+ " where csd.curriculumSchemeId in ( "
				+ " select cs.id from CurriculumSchemeUtilBO cs"
				+ " where cs.courseId = :courseId and cs.courseSchemeId = :shemeId)"
				+ " and csd.semesterYearNo = :schemeNo and csd.academicYear = :academicYear and sub.isActive=1))) and sub.isCertificateCourse=true and sub.id="+subId;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query query = session.createQuery(HQL);
		query.setParameter("courseId", courseId);
		query.setParameter("shemeId", shemeId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("academicYear", academicYear);
		list =new ArrayList<ExamSubDefinitionCourseWiseBO>(query.list());
		session.flush();
		//session.close();
		return list;
	}
	/**
	 * @param subjectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String checkIfTheoryOrPractical(int subjectId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(SubjectUtilBO.class);
		crit.add(Restrictions.eq("id", subjectId));

		List<SubjectUtilBO> list = new ArrayList<SubjectUtilBO>(crit.list());

		session.flush();
		session.close();

		return list.get(0).getIsTheoryPractical();

	}
	/**
	 * @param subjectId
	 * @param courseId
	 * @param schemeno
	 * @param academicYear
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ExamSubDefinitionCourseWiseBO> getSubjectDetailsFromSubjectId(
			int subjectId, int courseId,int schemeno,int academicYear) {
		Session session = null;
		List<ExamSubDefinitionCourseWiseBO> list = null;

		String HQL_QUERY = " select sub.id, sub.name, sub.code, e.id, e.subjectOrder, e.universitySubjectCode, e.subjectSectionId, subsec.name,"
				+ "  e.theoryHours, e.theoryCredit, e.practicalHours, e.practicalCredit,"
				+ " e.dontAddTotMarkClsDecln, e.dontShowSubType, e.dontShowMaxMarks, e.dontShowAttMarks,"
				+ " e.dontShowMinMarks, e.dontConsiderFailureTotalResult, e.showInternalFinalMarkAdded, e.showOnlyGrade"
				+ " from ExamSubDefinitionCourseWiseBO e"
				+ "  right join e.subjectUtilBO sub"
				+ " join e.examSubjectSectionMasterBO subsec"
				+ " where sub.id = :subjectId and e.courseId=:courseId and e.schemeNo=:schemeNo and e.academicYear=:academicYear";

		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeno);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<ExamSubDefinitionCourseWiseBO>(query.list());

			tx.commit();
			session.flush();
			// session.close();

			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamSubDefinitionCourseWiseBO>();
		}
		return list;
	}

	/**
	 * @param courseId
	 * @param shemeId
	 * @param schemeNo
	 * @param academicYear
	 * @return
	 */
	public Integer GetMaxSubjectOrder(int courseId, int shemeId, int schemeNo,int academicYear) {

		Session session = null;
		Integer maxOrder=null ;

		String HQL = " select max(subjectOrder) from ExamSubDefinitionCourseWiseBO def where def.courseId = :courseId " +
				" and def.schemeNo = :schemeNo and def.academicYear=:academicYear " +
				" and (def.subjectUtilBO.isCertificateCourse=0 or def.subjectUtilBO.isCertificateCourse is null)";
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Query query = session.createQuery(HQL);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("academicYear", academicYear);
		maxOrder =(Integer) query.uniqueResult();
		if(maxOrder==null){
			maxOrder=15;
		}else{
			maxOrder=maxOrder+1;
		}
		session.flush();
		session.close();
		return maxOrder;
	}
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SubjectUtilBO select_subcode_subname(int id) throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			SubjectUtilBO objBO = (SubjectUtilBO) session.get(
					SubjectUtilBO.class, id);
			session.flush();
			session.close();
			return objBO != null ? objBO : null;
		} catch (Exception e) {
			log.error(e.getMessage());
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}
	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkOptionalAndSecondLang(int id) {
		boolean check = true;

		String HQL_QUERY = " select s.id from SubjectUtilBO s "
				+ "where (s.isOptionalSubject=1 or s.isSecondLanguage=1)"
				+ " and s.id= :id";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		@SuppressWarnings("unused")
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		List<SubjectUtilBO> list = new ArrayList<SubjectUtilBO>(query.list());
		if (list.size() == 0) {
			check = false;
		}

		return check;
	}
	/**
	 * @param id
	 * @param courseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkCommonSubject(int id, int courseId) {
		boolean check = true;

		String HQL_QUERY = " select distinct s.subjectUtilBO.id"
				+ " from SubjectGroupSubjectsUtilBO s where"
				+ " s.subjectUtilBO.id=:id  and s.subjectUtilBO.isActive=1 and"
				+ " s.subjectGroupUtilBO.isCommonSubGrp=1 "
				+ " and s.subjectGroupUtilBO.isActive=1 "
				+ " and s.subjectGroupUtilBO.courseId = :courseId and s.isActive=1";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		@SuppressWarnings("unused")
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.setParameter("courseId", courseId);
		List<SubjectGroupUtilBO> list = new ArrayList<SubjectGroupUtilBO>(query
				.list());
		if (list.size() == 0) {
			check = false;
		}

		return check;
	}
	/**
	 * @param subjectId
	 * @param courseId
	 * @param schemeNo
	 * @param academicYear
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean isDuplicatedSubject(int subjectId,int courseId, int schemeNo, int academicYear) throws Exception {
		boolean check = true;

		String HQL_QUERY = "select s.id from ExamSubDefinitionCourseWiseBO s"
				+ " where s.subjectId = :subjectId and s.courseId = :courseId"
				+ " and schemeNo= :schemeNo and s.academicYear=:academicYear";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("subjectId", subjectId);
		query.setParameter("courseId", courseId);
		query.setParameter("schemeNo", schemeNo);
		query.setParameter("academicYear", academicYear);
		
		List<ExamSubDefinitionCourseWiseBO> list = new ArrayList<ExamSubDefinitionCourseWiseBO>(
				query.list());

		if (list.size() == 0) {
			check = false;
		} else {
			throw new DuplicateException();
		}

		return check;
	}
	/**
	 * @param subjectId
	 * @param courseId
	 * @param schemeNo
	 * @param academicYear
	 * @param objform 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int isPresentDetails(int subjectId, int courseId,int schemeNo, int academicYear, ExamSubjectDefCourseForm objform) {
		int isPresent = 0;
		Session session = null;
		List<ExamSubDefinitionCourseWiseBO> list = null;
		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = " select bo.id from ExamSubDefinitionCourseWiseBO bo "
					+ "where bo.subjectId= :subjectId and bo.courseId=:courseId and bo.schemeNo=:schemeNo and bo.academicYear=:academicYear";

			Query query = session.createQuery(HQL);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<ExamSubDefinitionCourseWiseBO>(query.list());
			session.flush();
			// session.close();

		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

			list = new ArrayList<ExamSubDefinitionCourseWiseBO>();

		}

		Iterator it = list.iterator();
		while (it.hasNext()) {
			Integer i = (Integer) it.next();
			isPresent = i.intValue();
			if(isPresent>0)
			objform.setId(Integer.toString(isPresent));
		}
		return isPresent;
	}
	/**
	 * @param subjectId
	 * @param courseId
	 * @param schemeNo
	 * @param academicYear
	 * @throws DuplicateException
	 */
	@SuppressWarnings("unchecked")
	public void duplicateOrderCheck(int subjectId,int courseId, int schemeNo, int academicYear) throws DuplicateException {
		Session session = null;
		List<ExamSubDefinitionCourseWiseBO> list = null;

		try {

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String HQL = " select bo.subjectId from ExamSubDefinitionCourseWiseBO bo "
					+ "where bo.courseId=:courseId and bo.subjectId != :subjectId " +
					  " and schemeNo = :schemeNo and bo.academicYear = :academicYear";

			Query query = session.createQuery(HQL);
			query.setParameter("subjectId", subjectId);
			query.setParameter("courseId", courseId);
			query.setParameter("schemeNo", schemeNo);
			query.setParameter("academicYear", academicYear);
			list = new ArrayList<ExamSubDefinitionCourseWiseBO>(query.list());
			session.flush();
			// session.close();

		} catch (Exception e) {

			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}

			list = new ArrayList<ExamSubDefinitionCourseWiseBO>();

		}

		Iterator it = list.iterator();
		while (it.hasNext()) {
			Integer i = (Integer) it.next();
			List l=session.createQuery("select subSet.subject.id from CurriculumSchemeDuration cd join cd.curriculumSchemeSubjects cs join cs.subjectGroup subGrp join subGrp.subjectGroupSubjectses subSet" +
					" where cd.academicYear="+academicYear+" and cd.semesterYearNo="+schemeNo+
					" and cd.curriculumScheme.course.id= " +courseId+
					"and subSet.isActive=1 and subGrp.isActive=1 and subSet.subject.id="+i).list();
			if(l!=null && !l.isEmpty()){
				if (checkCommonSubject(i, courseId)) {
					throw new DuplicateException();
				}
			}
		}

	}
	public List<CertificateCourse> getCertificateCourseList(ExamSubjectDefCourseForm objform) throws Exception {
		Session session=null;
		List<CertificateCourse> cerBiList;
		try{
			 session = HibernateUtil.getSession();
			 String str=" from CertificateCourse cer where cer.isActive=1";

			 if(objform.getAcademicYear()!=null && !objform.getAcademicYear().isEmpty()){
		         str=str+" and cer.year="+objform.getAcademicYear();
	           }
			 if(objform.getSchemeType()!=null && !objform.getSchemeType().isEmpty()){
				 if(objform.getSchemeType().equalsIgnoreCase("1")){
					 str=str+" and cer.semType like '%ODD%'";
				 } else if(objform.getSchemeType().equalsIgnoreCase("2")){
					 str=str+" and cer.semType like '%EVEN%'";
				 }
	           }
			 Query query = session.createQuery(str);
			 cerBiList=query.list();
			
		}catch(Exception e){
			log.error("Unable to getCertificateCourseList",e);
			 throw e;
		}
		return cerBiList;
	}
	public void getSubjectGroupsForInput(ExamSubjectDefCourseForm objform, int subjectId) throws Exception{
		Session session = null;
		List<Object[]> dataListt = null;
		try {
		session = HibernateUtil.getSession();
		String query=" select distinct adm_appln.selected_course_id,classes.term_number,subject.id,course.name,curriculum_scheme.course_scheme_id"+
					" from student inner join adm_appln ON student.adm_appln_id = adm_appln.id inner join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
					" inner join classes ON class_schemewise.class_id = classes.id inner join applicant_subject_group on applicant_subject_group.adm_appln_id = adm_appln.id " +
					" inner join subject_group ON applicant_subject_group.subject_group_id = subject_group.id inner join subject_group_subjects on subject_group_subjects.subject_group_id = subject_group.id " +
					" and subject_group_subjects.is_active=1 inner join subject ON subject_group_subjects.subject_id = subject.id inner join course on adm_appln.selected_course_id = course.id" +
					" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id " +
					" and curriculum_scheme_duration.semester_year_no=classes.term_number inner join curriculum_scheme on curriculum_scheme.course_id = course.id " +
					" and curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id "+
					" where subject.id in ("+subjectId+") and adm_appln.is_cancelled='0'" +
					" and curriculum_scheme_duration.academic_year="+objform.getAcademicYear();
					Query selectedCourseQuery=session.createSQLQuery(query);
					dataListt = selectedCourseQuery.list();
			objform.setAllDatas(dataListt);
		  } catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
    }


}
