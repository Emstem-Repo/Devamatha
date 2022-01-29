package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExamPublishHallTicketForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.HibernateUtil;
@SuppressWarnings("unchecked")
public class ExamPublishHallTicketImpl extends ExamGenImpl {

	// On - DELETE
	public void delete(int id) throws Exception {
		Session session = null;
		try{
		String HQL_QUERY = "delete from ExamPublishHallTicketMarksCardBO e"
				+ " where e.id = :id ";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
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

	// On - EDIT
	public void update(Integer id, Integer examId, Integer classId,
			Integer agreementId, Integer footerId, String publishFor,
			Date downloadStartDate, Date downloadEndDate, int programTypeId, String examCenterCode, String examCenter,Date revaluationDate) throws Exception {
		Session session = null;
		try{
		String HQL_QUERY = "update ExamPublishHallTicketMarksCardBO e set"
				+ " e.examId = :examId, e.classId = :classId, e.agreementId = :agreementId,"
				+ " e.footerId = :footerId, e.publishFor = :publishFor, e.downloadStartDate = :downloadStartDate,"
				+ " e.downloadEndDate = :downloadEndDate, e.programTypeId= :programTypeId, e.examCenterCode = :examCenterCode,"
				+ " e.examCenter = :examCenter ,e.revaluationEndDate=:revaluationDate"
				+ " where e.id = :id ";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Query query = session.createQuery(HQL_QUERY);
		query.setParameter("id", id);
		query.setParameter("examId", examId);
		query.setParameter("classId", classId);
		query.setParameter("agreementId", agreementId);
		query.setParameter("footerId", footerId);
		query.setParameter("publishFor", publishFor);
		query.setParameter("downloadStartDate", downloadStartDate);
		query.setParameter("downloadEndDate", downloadEndDate);
		query.setParameter("programTypeId", programTypeId);
		query.setParameter("examCenterCode", examCenterCode);
		query.setParameter("examCenter", examCenter);
		query.setParameter("revaluationDate", revaluationDate);

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
		
		// session.close();

	}

	// On - EDIT to fetch the details
	
	public ExamPublishHallTicketMarksCardBO select_Unique(int id) throws Exception {

		Session session = null;
		ExamPublishHallTicketMarksCardBO list = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			/*Criteria crit = session
					.createCriteria(ExamPublishHallTicketMarksCardBO.class);
			crit.add(Restrictions.eq("id", id));
			crit.setMaxResults(1);*/
			String query= "from ExamPublishHallTicketMarksCardBO exam where exam.id ="+id;
			Query crit = session.createQuery(query);

			list = (ExamPublishHallTicketMarksCardBO)crit.uniqueResult();
			session.flush();
			// session.close();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
		if (list == null) {
			return null;
		} else
			return  list;
	}

	public String isDuplicate(int id, int examId, int classId,
			Integer agreementId, Integer footerId, Date startDate,
			Date endDate, String publishFor) throws Exception {
		Session session = null;
		String className="";
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		try {
			Criteria crit = session
					.createCriteria(ExamPublishHallTicketMarksCardBO.class);
			crit.add(Restrictions.eq("examId", examId));
			crit.add(Restrictions.eq("classId", classId));
			crit.add(Restrictions.eq("publishFor", publishFor));
			/*if (agreementId != null) {
				crit.add(Restrictions.eq("agreementId", agreementId));

			} else if (footerId != null) {
				crit.add(Restrictions.eq("footerId", footerId));

			}*/

			List<ExamPublishHallTicketMarksCardBO> list = crit.list();

			if (list.size() > 0) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					ExamPublishHallTicketMarksCardBO eBO = (ExamPublishHallTicketMarksCardBO) it
							.next();
					if (id != eBO.getId())
						if (eBO.getIsActive() == true) {
							className=eBO.getClassUtilBO().getName();
						}
				}
			}
			return className;
		}
		catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	
	/**
	 * 
	 * added by mehaboob
	 * @param examId
	 * @param val
	 * @return
	 * @throws Exception 
	 */
	//--------------------------added by mehaboob--------------------
	
	public ExamPublishExamResultsBO checkClassByPublish(int examId,int classId) throws Exception
	{
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			/*Criteria crit = session.createCriteria(ExamPublishExamResultsBO.class);
			crit.add(Restrictions.eq("exam_id", examId));
			crit.add(Restrictions.eq("class_id", val));*/
				
				Query query= session.createQuery("from ExamPublishExamResultsBO examPublish where examPublish.examDefinitionBO.id="+examId+" and  examPublish.classUtilBO.id="+classId+"");
				ExamPublishExamResultsBO examResultsBO =   (ExamPublishExamResultsBO) query.uniqueResult();
				return examResultsBO;
			} catch (Exception e) {
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
	}
	//--------------ended-----------------
	public ArrayList<ExamPublishHallTicketMarksCardBO> getClassByClassId(
			ArrayList<Integer> classIdList) throws Exception {
		Session session = null;
		ArrayList<ExamPublishHallTicketMarksCardBO> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Criteria crit = session
					.createCriteria(ExamPublishHallTicketMarksCardBO.class);
			crit.add(Restrictions.in("classId", classIdList));
			crit.add(Restrictions.eq("isActive", true));

			list = new ArrayList(crit.list());
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamPublishHallTicketMarksCardBO>();

		}
		return list;

	}
	
public ClassSchemewise getClassNameYearById(int classId,Class className) throws Exception{
		
	Session session = null;
	List<ClassUtilBO> list = null;
	int id=0;
	try {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		/*Criteria crit = session.createCriteria(className);
		crit.add(Restrictions.eq("id", classId));
		crit.setMaxResults(1);
		list = crit.list();
		if(list.size()>0){
			
			ClassUtilBO classes =list.get(0);
			 id=classes.getId();
		}*/
			Query query= session.createQuery("from ClassSchemewise c where c.classes.id="+classId);
			ClassSchemewise selectedClass =  (ClassSchemewise) query.uniqueResult();
			return selectedClass;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

public String getCurrentExamName(int examTypeID) throws Exception{
	Session session = null;
	try{
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	session = sessionFactory.openSession();

	String currentExam = null;

	String HQL_QUERY = null;
		HQL_QUERY = "select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.id="+examTypeID+ " and d.delIsActive = 1 and d.isActive=1";

	Query query = session.createQuery(HQL_QUERY);

	List list = query.list();

	if (list != null && list.size() > 0) {

		currentExam = list.get(0).toString();
	}
	return currentExam;
	}catch (Exception e) {
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
}

public List<Object> getListByExamType(ExamPublishHallTicketForm objform) throws Exception {
	List<Object> list=null;
	Session session = null;
	Query query=null;
	try {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		StringBuilder stringBuilder=new StringBuilder();
		if(objform.getExamName()!=null && !objform.getExamName().trim().isEmpty()
				&& objform.getDeanaryName()!=null && !objform.getDeanaryName().isEmpty()){
			stringBuilder.append("from ExamPublishHallTicketMarksCardBO bo where bo.examDefinitionBO.examTypeUtilBO.id="+objform.getExamType()+
					"and bo.examDefinitionBO.id= "+Integer.parseInt(objform.getExamName())+
					" and bo.classes.course.program.stream='"+objform.getDeanaryName()+"'");
			
			if(objform.getPublishFor()!=null && !objform.getPublishFor().trim().isEmpty()){
				stringBuilder.append(" and bo.publishFor='"+objform.getPublishFor()+"'");
			}
			query = session.createQuery(stringBuilder.toString());
			list = query.list();
		}else{
			stringBuilder.append("from ExamPublishHallTicketMarksCardBO bo where bo.examDefinitionBO.examTypeUtilBO.id="+objform.getExamType());
			if(objform.getExamName()!=null && !objform.getExamName().trim().isEmpty()){
				stringBuilder.append(" and bo.examDefinitionBO.id= "+Integer.parseInt(objform.getExamName()));
			}
			if(objform.getPublishFor()!=null && !objform.getPublishFor().trim().isEmpty()){
				stringBuilder.append(" and bo.publishFor='"+objform.getPublishFor()+"'");
			}
			query = session.createQuery(stringBuilder.toString());
			list = query.list();
		
		}
		session.flush();

	} catch (Exception e) {
		e.printStackTrace();
		if (session != null) {
			session.flush();
			session.close();
		}
		list=new ArrayList<Object>();
	}
	return list;
}

public ExamPublishHallTicketMarksCardBO getExamTypeAndExamName(int parseInt) throws Exception {
	Session session = null;
	ExamPublishHallTicketMarksCardBO bo=null;
	try{
	String HQL_QUERY = "from ExamPublishHallTicketMarksCardBO e"
			+ " where e.id ="+parseInt;

	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	session = sessionFactory.openSession();

	Query query = session.createQuery(HQL_QUERY);
	bo = (ExamPublishHallTicketMarksCardBO)query.uniqueResult();
	
	}catch (Exception e) {
		throw  new ApplicationException(e);
	} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
	return bo;
}

/**
 * @param examId
 * @param programTypeId
 * @param deaneryname 
 * @return
 * @throws Exception
 */
public List getCourseIdSchemeNoByProgramType(Integer examId,Integer programTypeId, String deaneryname) throws Exception{
	Session session=null;
	try{
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	 session = sessionFactory.openSession();

	String HQL = null;
	HQL = "select distinct e.courseId, e.courseSchemeId, e.schemeNo from  ExamExamCourseSchemeDetailsBO e where e.examId =:examId";
	if(programTypeId>0){
		HQL=HQL+" and e.examProgramUtilBO.programType.id=:programType";
	}
	if(deaneryname!=null && !deaneryname.isEmpty()){
		HQL=HQL+" and e.program.stream='"+deaneryname+"'";
	}
	Query query = session.createQuery(HQL);
	query.setParameter("examId", examId);
	query.setParameter("programType", programTypeId);

	return query.list();
	}
	catch (Exception e) {
		e.printStackTrace();
		throw  new ApplicationException(e);
	}
	 finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
}

	/**
	 * 
	 * getting class name by passing class id
	 * @param classId
	 * @return
	 */
	public Map<Integer, String> getClassMap(String[] classId) {
		Session session = null;
		Map<Integer,String> classMap= new HashMap<Integer, String>();
		try {
			 session = HibernateUtil.getSession();
			 
			 String id = "";
			 for(String cId: classId){
				 if(cId != null){
					 if(id.isEmpty()){
						 id = cId;
					 }else{
						 id = id+","+cId;
					 }
				 }
					 
			 }
			 Query query = session.createQuery(" from Classes c where id in ("+id+")" ); 		
			 List<Classes> list = query.list();
			 if(list!=null && !list.isEmpty()){
				 Iterator<Classes> itr = list.iterator();
				 while (itr.hasNext()) {
					 Classes classes = (Classes) itr.next();
					 classMap.put(classes.getId(), classes.getName());
				}
			 }
		}catch (Exception e) {
			session.flush();
			session.close();
		}
		return classMap;
	}

	public Map<String, String> getDeaneryMap() throws Exception{
		Session session = null;
		Map<String,String> deaneryMap= new HashMap<String, String>();
		try {
			 session = HibernateUtil.getSession();
 			 Query query = session.createQuery("select distinct(p.stream) from Program p where p.stream!=null and p.stream !=''" ); 		
			 List<Object> list = query.list();
			 if(list!=null && !list.isEmpty()){
				 Iterator<Object> itr = list.iterator();
				 while (itr.hasNext()) {
					 Object object = (Object) itr.next();
					 deaneryMap.put(object.toString(), object.toString());
				}
			 }
		}catch (Exception e) {
			session.flush();
			session.close();
		}
		return deaneryMap;
	}

	public List<Integer> getclassIds(int examId) throws Exception{
		Session session = null;
		List<Integer> list=null;
		try {
			 session = HibernateUtil.getSession();
 			 Query query = session.createQuery("select distinct e.classes.id from ExamSupplementaryImprovementApplicationBO e where (e.isAppearedTheory=1 or e.isAppearedPractical=1)" +
 			 		"and e.examId="+ examId); 		
 			 list = query.list();
		}catch (Exception e) {
			session.flush();
			session.close();
		}
		return list;
	}

	public List<KeyValueTO> getClassValuesByCourseIdNew(String examType, String examName,
			String programId, String deanaryName, List<KeyValueTO> list) throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("select classes.id, classes.name, curriculum_scheme.year from EXAM_exam_course_scheme_details" +
				" inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id" +
				" inner join course ON EXAM_exam_course_scheme_details.course_id = course.id" +
				" inner join curriculum_scheme on curriculum_scheme.course_id = course.id" +
				" inner join curriculum_scheme_duration on curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
				" and curriculum_scheme_duration.semester_year_no = EXAM_exam_course_scheme_details.scheme_no" +
				" inner join class_schemewise on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
				" inner join classes ON class_schemewise.class_id = classes.id" +
				" inner join program ON course.program_id = program.id" +
				" inner join program_type ON program.program_type_id = program_type.id" +
				" where EXAM_definition.id="+Integer.parseInt(examName)+
				" and classes.is_active=1 and program.is_active=1 and course.is_active=1 and classes.is_active=1 and program_type.is_active=1");
				if(deanaryName!=null && !deanaryName.isEmpty()){
					if(!deanaryName.equalsIgnoreCase("0")){
						stringBuilder.append(" and program.stream='"+deanaryName+"'");
					}
				}
				if(programId!=null && !programId.isEmpty() && !programId.equalsIgnoreCase("null")){
					if(!programId.equalsIgnoreCase("0")){
						stringBuilder.append(" and program_type.id="+Integer.parseInt(programId));
					}
				}
				if(!examType.equalsIgnoreCase("0")){
					if(Integer.parseInt(examType) == 3 || Integer.parseInt(examType) == 6){
						
						stringBuilder.append(" and curriculum_scheme.year >= (select exam_for_joining_batch from EXAM_definition where id="+Integer.parseInt(examName)+" )");
					}
					else{
						stringBuilder.append(" and EXAM_definition.academic_year=curriculum_scheme_duration.academic_year ");
					}
				}else{
					stringBuilder.append(" and EXAM_definition.academic_year=curriculum_scheme_duration.academic_year ");
				}
			Query query = session.createSQLQuery(stringBuilder.toString());
			Iterator<Object[]> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (obj[1] != null) {
					String year = "";
					if(obj[2]!= null ){
						year = " (" + obj[2].toString().substring(0, 4) + ")";
					}
					list.add(new KeyValueTO(Integer.parseInt(obj[0].toString()),
							obj[1].toString()+year));
				}
		
			}
			return list;
	}
	
	public List<Object[]> getStudentListByClassIds(Set<Integer> classIds) throws Exception {
			
		Session session = null;
		List<Object[]> list=null;
		List<Integer> classList=new ArrayList<Integer>();
		try {
			session = HibernateUtil.getSession();
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//Session session = sessionFactory.openSession();
			Iterator<Integer> itr = classIds.iterator();
			while (itr.hasNext()) {
				Integer classId = (Integer) itr.next();
				if(classId !=null && classId !=0)
					classList.add(classId);
			}
			if(classList !=null && !classList.isEmpty()){
			
			 Query query = session.createQuery(" select pdata.mobileNo1,pdata.mobileNo2 from Student s "+
												" inner join s.classSchemewise.classes cls"+
												" inner join s.admAppln adm"+
												" inner join adm.personalData pdata"+
												" where cls.id in(:classIdsLIst) and adm.isCancelled=0 and s.isAdmitted=1 and (s.isHide=0 or s.isHide is null)"+
												" and s.id not in(select esdrd.student.id from ExamStudentDetentionRejoinDetails esdrd"+ 
												" where (esdrd.detain=1 or esdrd.discontinued=1) and (esdrd.rejoin is null or esdrd.rejoin=0))"+
												" and pdata.mobileNo2 is not null and pdata.mobileNo2 !='' group by s.id " ); 		
			 query.setParameterList("classIdsLIst", classList);
			  list = query.list();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;

	}
	
	
	public List<Object[]> getPreviousStudentList(Set<Integer> classIds) throws Exception {
		
		Session session = null;
		List<Object[]> list=null;
		List<Integer> classList=new ArrayList<Integer>();
		try {
			Iterator<Integer> itr = classIds.iterator();
			while (itr.hasNext()) {
				Integer classId = (Integer) itr.next();
				if(classId !=null && classId !=0)
					classList.add(classId);
			}
			if(classList !=null && !classList.isEmpty()){
			session = HibernateUtil.getSession();
			 Query query = session.createQuery("select pdata.mobileNo1,pdata.mobileNo2 from StudentPreviousClassHistory spch"+
												" inner join spch.student s"+
												" inner join spch.classes cls"+
												" inner join s.admAppln adm"+
												" inner join adm.personalData pdata"+
												" where cls.id in (:classIdsLIst)"+
												" and adm.isCancelled=0 and s.isAdmitted=1 and (s.isHide=0 or s.isHide is null)"+
												" and s.id not in(select esdrd.student.id from ExamStudentDetentionRejoinDetails esdrd"+ 
												" where (esdrd.detain=1 or esdrd.discontinued=1) and (esdrd.rejoin is null or esdrd.rejoin=0))"+
												" and pdata.mobileNo2 is not null and pdata.mobileNo2 !='' group by s.id " );
			 query.setParameterList("classIdsLIst", classList);
			  list = query.list();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;

	}
	
	public String getExamType(String examtypeId) throws Exception {
		Session session = null;
		String examType="";
		try{
		String HQL_QUERY = "select e.name from ExamTypeUtilBO e"
				+ " where  e.id ="+examtypeId;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		session = sessionFactory.openSession();

		Query query = session.createQuery(HQL_QUERY);
		examType = (String)query.uniqueResult();
		
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return examType;
	}
	
	public List<Object[]> getSupplimentoryStudentListByClassIds(Set<Integer> classIds,ExamPublishHallTicketForm objform) throws Exception {
		
		Session session = null;
		List<Object[]> list=null;
		List<Integer> classList=new ArrayList<Integer>();
		try {
			session = HibernateUtil.getSession();
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//Session session = sessionFactory.openSession();
			Iterator<Integer> itr = classIds.iterator();
			while (itr.hasNext()) {
				Integer classId = (Integer) itr.next();
				if(classId !=null && classId !=0)
					classList.add(classId);
			}
			if(classList !=null && !classList.isEmpty()){
			
			 Query query = session.createQuery(" select sup.student.admAppln.personalData.mobileNo1,sup.student.admAppln.personalData.mobileNo2"+
					 							" from StudentSupplementaryImprovementApplication sup"+
					 							" where (sup.isAppearedPractical=1 or sup.isAppearedTheory=1)"+ 
					 							" and (sup.student.isHide=0 or sup.student.isHide is null) "+
					 							" and sup.student.isAdmitted=1 and sup.student.admAppln.isCancelled=0"+
					 							" and sup.student.admAppln.personalData.mobileNo1 is not null "+
					 							" and sup.student.admAppln.personalData.mobileNo2 is not null"+
					 							" and sup.classes.id in (:classIdsLIst) and sup.examDefinition.id="+objform.getExamName()+"group by sup.student.id" ); 		
			 query.setParameterList("classIdsLIst", classList);
			  list = query.list();
			}
			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return list;

	}

	public List<KeyValueTO> getClassValuesByExam(String examType,String examName, String programId, List<KeyValueTO> list) throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("select classes.id, classes.name, curriculum_scheme.year from EXAM_exam_course_scheme_details" +
				" inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id" +
				" inner join course ON EXAM_exam_course_scheme_details.course_id = course.id" +
				" inner join curriculum_scheme on curriculum_scheme.course_id = course.id" +
				" inner join curriculum_scheme_duration on curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
				" and curriculum_scheme_duration.semester_year_no = EXAM_exam_course_scheme_details.scheme_no" +
				" inner join class_schemewise on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
				" inner join classes ON class_schemewise.class_id = classes.id" +
				" inner join program ON course.program_id = program.id" +
				" inner join program_type ON program.program_type_id = program_type.id" +
				" where EXAM_definition.id="+Integer.parseInt(examName)+
				" and classes.is_active=1 and program.is_active=1 and course.is_active=1 and classes.is_active=1 and program_type.is_active=1");
				
				if(programId!=null && !programId.isEmpty() && !programId.equalsIgnoreCase("null")){
					if(!programId.equalsIgnoreCase("0")){
						stringBuilder.append(" and program_type.id="+Integer.parseInt(programId));
					}
				}
				if(!examType.equalsIgnoreCase("0")){
					if(Integer.parseInt(examType) == 3 || Integer.parseInt(examType) == 6){
						
						stringBuilder.append(" and curriculum_scheme.year >= (select exam_for_joining_batch from EXAM_definition where id="+Integer.parseInt(examName)+" )");
					}
					else{
						stringBuilder.append(" and EXAM_definition.academic_year=curriculum_scheme_duration.academic_year ");
					}
				}else{
					stringBuilder.append(" and EXAM_definition.academic_year=curriculum_scheme_duration.academic_year ");
				}
			Query query = session.createSQLQuery(stringBuilder.toString());
			Iterator<Object[]> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (obj[1] != null) {
					String year = "";
					if(obj[2]!= null ){
						year = " (" + obj[2].toString().substring(0, 4) + ")";
					}
					list.add(new KeyValueTO(Integer.parseInt(obj[0].toString()),
							obj[1].toString()+year));
				}
		
			}
			return list;
	}
}
