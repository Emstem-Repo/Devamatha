package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExamValidationDetailsForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IExamValidationDetailsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ExamValidationDetailsTxImpl implements IExamValidationDetailsTransaction{

	/**
	 * Singleton object of NewExamMarksEntryTransactionImpl
	 */
	private static volatile ExamValidationDetailsTxImpl newExamMarksEntryTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ExamValidationDetailsTxImpl.class);
	private ExamValidationDetailsTxImpl() {
		
	}
	/**
	 * return singleton object of NewExamMarksEntryTransactionImpl.
	 * @return
	 */
	public static ExamValidationDetailsTxImpl getInstance() {
		if (newExamMarksEntryTransactionImpl == null) {
			newExamMarksEntryTransactionImpl = new ExamValidationDetailsTxImpl();
		}
		return newExamMarksEntryTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#saveDetails(com.kp.cms.bo.exam.ExamValidationDetails)
	 */
	@Override
	public boolean saveDetails(ExamValidationDetails examValidationDetails) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			String query = "from ExamValidationDetails e where e.isActive=1 " ;
			if(examValidationDetails.getExam() != null && examValidationDetails.getExam().getId() != 0){
				query = query +" and e.exam.id="+examValidationDetails.getExam().getId();
			}
			if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() !=0){
				query = query + " and e.subject.id="+examValidationDetails.getSubject().getId();
			}
			if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getId() !=0){
				query = query + " and e.users.id="+examValidationDetails.getUsers().getId();
			}
			if(examValidationDetails.getExamValuators() != null){
				query = query + " and e.examValuators.id="+examValidationDetails.getExamValuators().getId();
			}
			if(examValidationDetails.getIsValuator() != null && !examValidationDetails.getIsValuator().isEmpty() && examValidationDetails.getIsValuator().equalsIgnoreCase("Valuator1")){
					query = query + " and e.isValuator='Valuator1'";
			}
			// added by chandra
			else if(examValidationDetails.getIsValuator() != null && !examValidationDetails.getIsValuator().isEmpty() && examValidationDetails.getIsValuator().equalsIgnoreCase("Valuator2")){
					query = query + " and e.isValuator='Valuator2'";
			} //
			else if(examValidationDetails.getIsValuator() != null && !examValidationDetails.getIsValuator().isEmpty() && examValidationDetails.getIsValuator().equalsIgnoreCase("Reviewer")){
				query = query + " and e.isValuator='Reviewer'";
			}else if(examValidationDetails.getIsValuator() != null && !examValidationDetails.getIsValuator().isEmpty() && examValidationDetails.getIsValuator().equalsIgnoreCase("Project Major")){
				query = query + " and e.isValuator='Project Major'";
			}else if(examValidationDetails.getIsValuator() != null && !examValidationDetails.getIsValuator().isEmpty() && examValidationDetails.getIsValuator().equalsIgnoreCase("Project Minor")){
				query = query + " and e.isValuator='Project Minor'";
			}
			ExamValidationDetails bo = (ExamValidationDetails)session.createQuery(query).uniqueResult();
			if(examValidationDetails.getExamValuators() != null && examValidationDetails.getExamValuators().getId() != 0 ){
				ExamValuators valuators = (ExamValuators)session.createQuery(" from ExamValuators v where v.id="+examValidationDetails.getExamValuators().getId()).uniqueResult();
				examValidationDetails.setExamValuators(valuators);
			}
			if(bo == null){
				session.save(examValidationDetails);
			}else {
				Set<ExamValuationAnswerScript> answerScripts = examValidationDetails.getAnswerScripts();
				Iterator<ExamValuationAnswerScript> iterator = answerScripts.iterator();
				while (iterator.hasNext()) {
					ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator.next();
					examValuationAnswerScript.setValidationDetailsId(bo);
					session.save(examValuationAnswerScript);
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getSubjectNames(java.lang.String, java.lang.String, int)
	 */
	@Override
	public ArrayList<SubjectUtilBO> getSubjectNames(String sCodeName,
			String subjectName, int examId) throws Exception {
		Session session = null;
		ArrayList<SubjectUtilBO> list;
		try {

			String courseIds = getCourseidByExamId(examId);

			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String HQL = null;

			if (examId == 0) {

				HQL = "select s.subject.id, s.subject.name, s.subject.code from SubjectGroupSubjects s  "
						+ " where s.subject.isActive = 1 "
						+ " and s.subjectGroup.isActive = 1 and s.isActive = 1 ";
			} else {
				HQL = "select s.subject.id, s.subject.name, s.subject.code from SubjectGroupSubjects s  "
						+ " where s.subjectGroup.course.id in ("
						+ courseIds
						+ ") and s.subject.isActive = 1 "
						+ " and s.subjectGroup.isActive = 1 and s.isActive = 1 ";
			}
			
			if(subjectName != null){
				if (sCodeName.equalsIgnoreCase("sCode")) {
					HQL = HQL + " and s.subject.code like '"+subjectName+"%'";
				}else{
					HQL = HQL + " and s.subject.name like '"+subjectName+"%'";
				}
			}
			HQL = HQL + " group by s.subject.id,  s.subject.name, s.subject.code ";
			if (sCodeName.equalsIgnoreCase("sCode")) {
				HQL = HQL + " order by s.subject.code";

			} else {
				HQL = HQL + " order by s.subject.name";
			}
			Query query = session.createQuery(HQL);

			list = new ArrayList<SubjectUtilBO>(query.list());

			session.flush();
			// session.close();
		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<SubjectUtilBO>();
		}
		return list;
	}
	
	/**
	 * 
	 * @param examId
	 * @return
	 */
	public String getCourseidByExamId(int examId) {
		Session session = null;
		ArrayList<Integer> list;
		StringBuffer courseIds = new StringBuffer();
		String courses = "";
		try {
			session = HibernateUtil.getSession();
			String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.examId = "
					+ examId;

			Query query = session.createQuery(HQL);
			list = new ArrayList<Integer>(query.list());

			Iterator<Integer> itr = list.iterator();
			while (itr.hasNext()) {
				Integer id = (Integer) itr.next();
				courseIds.append(id.toString() + ",");
			}
			courses = courseIds.toString();
			if (courses.endsWith(",") == true) {
				courses = StringUtils.chop(courses);
			}
			session.flush();
			session.close();

		} catch (Exception e) {

			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return courses;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getExamValidationList(java.lang.String)
	 */
	@Override
	public List<ExamValidationDetails> getExamValidationList(String currentExam,String year) throws Exception {
		Session session = null;
		List<ExamValidationDetails> list = null;
		try{
			session = HibernateUtil.getSession();
			/*String hqlQuery = "from ExamValidationDetails e where e.isActive=1 and e.exam="+currentExam;
			if(year != null && !year.isEmpty()){
				hqlQuery = hqlQuery + " and e.exam.academicYear='"+year+"'";
			}*/
			Query query = session.createQuery("select e from ExamValidationDetails e join e.answerScripts ans   where e.isActive=1 and ans.isActive=1 and e.exam="+currentExam+" group by e.id");
			list = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamValidationDetails>();
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getDetailsForEdit(int)
	 */
	@Override
	public ExamValidationDetails getDetailsForEdit(int id) throws Exception {
		Session session = null;
		ExamValidationDetails details = new ExamValidationDetails();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamValidationDetails e where e.id="+id);
			details = (ExamValidationDetails)query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			
		}
		return details;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#deleteDetails(int)
	 */
	@Override
	public boolean deleteDetails(int id) throws Exception {
		Session session = null;
		boolean delete=false;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session.createQuery("from ExamValuationAnswerScript e where e.id="+id);
			ExamValuationAnswerScript details = (ExamValuationAnswerScript)query.uniqueResult();
			if(details != null){
				details.setIsActive(false);
				session.update(details);
				delete=true;
			}
			transaction.commit();
			session.flush();
			session.close();
		}catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
			
		}
		return delete;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#updateDetails(com.kp.cms.bo.exam.ExamValidationDetails)
	 */
	@Override
	public boolean updateDetails(ExamValidationDetails examValidationDetails)
			throws Exception {
		Session session = null;
		boolean update=false;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(examValidationDetails);
			update=true;
			transaction.commit();
			session.flush();
			session.close();
		}catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
			
		}
		return update;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#getEmployeeMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getEmployeeMap() throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<Users> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("select u from Users u left join u.employee e with e.active=1 and e.isActive=1 where u.isActive=1 and u.isTeachingStaff=1");
			list= query.list();
			if(list!=null){
				Iterator<Users> iterator=list.iterator();
				while(iterator.hasNext()){
					Users user=iterator.next();
					if(user!=null){
						if(user.getEmployee() != null && user.getEmployee().getFirstName() != null && user.getEmployee().getDepartment() != null && user.getEmployee().getDepartment().getName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName()+"("+user.getEmployee().getDepartment().getName()+")");
						}else if(user.getEmployee() != null && user.getEmployee().getFirstName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName());
						}else{
							map.put(user.getId(), user.getUserName().toUpperCase());
						}
					}
				}
				map = (HashMap<Integer, String>)CommonUtil.sortMapByValue(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	
	public ArrayList<KeyValueTO> getExamByExamType(String examTypeName,String academicYear) throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		Integer year=0;
		if (academicYear != null && !academicYear.isEmpty()) {
			year = Integer.parseInt(academicYear);
		}else{
			year = (Integer)session.createQuery("select a.year from AcademicYear a where a.isCurrent=1").uniqueResult();
		}
		String SQL_QUERY = null;
		if (examTypeName.contains("Reg")) {
			SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Reg%' and e.delIsActive = 1 and e.isActive = 1 and academicYear="+year;
		} else if (examTypeName.contains("Suppl")) {
			SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Suppl%' and e.delIsActive = 1 and e.isActive = 1 and academicYear="+year;
		} else if (examTypeName.contains("Int")) {
			SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Int%' and e.delIsActive = 1 and e.isActive = 1 and academicYear="+year;
		}else if(examTypeName.contains("Both")){
			SQL_QUERY = " from ExamDefinitionBO e where (e.examTypeUtilBO.name  like '%Int%' or e.examTypeUtilBO.name  like '%Reg%') and e.delIsActive = 1 and e.isActive = 1 and academicYear="+year;
		}

		Query query = session.createQuery(SQL_QUERY);

		List<ExamDefinitionBO> list = query.list();
		Iterator<ExamDefinitionBO> it = list.iterator();
		while (it.hasNext()) {
			ExamDefinitionBO row = (ExamDefinitionBO) it.next();
			listTO.add(new KeyValueTO(row.getId(), row.getName()));
		}
		return listTO;

	}
	@Override
	public ExamValuationAnswerScript getAnswerScriptDetails(ExamValidationDetailsForm examValidationDetailsForm)throws Exception {
		ExamValuationAnswerScript boList = null;;
		Session session = null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ExamValuationAnswerScript e where e.isActive=1 and e.id="+examValidationDetailsForm.getId());
			boList= (ExamValuationAnswerScript) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return boList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#getEmployeeMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getValuatorNameList(String valuatorName,String subjectId) throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<Users> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			if(valuatorName != null && !valuatorName.isEmpty() && subjectId != null && !subjectId.isEmpty()){
				Integer departmentId = (Integer)session.createQuery("select s.department.id from Subject s where s.id="+subjectId).uniqueResult();
				
				Query query=session.createQuery("select u from Users u left join u.employee e  with e.active=1 and e.isActive=1 and e.firstName like '"+valuatorName+"%' where u.isActive=1 and u.isTeachingStaff=1 and u.userName like '"+valuatorName+"%'  and (u.employee.department.id="+departmentId+" or u.department.id="+departmentId+") ");
				list= query.list();
			}else{
				Query query=session.createQuery("select u from Users u left join u.employee e with e.active=1 and e.isActive=1 where u.isActive=1 and u.isTeachingStaff=1");
				list= query.list();
			}
			if(list!=null){
				Iterator<Users> iterator=list.iterator();
				while(iterator.hasNext()){
					Users user=iterator.next();
					if(user!=null){
						if(user.getEmployee() != null && user.getEmployee().getFirstName() != null && user.getEmployee().getDepartment() != null && user.getEmployee().getDepartment().getName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName()+"("+user.getEmployee().getDepartment().getName()+")");
						}else if(user.getEmployee() != null && user.getEmployee().getFirstName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName());
						}else{
							map.put(user.getId(), user.getUserName().toUpperCase());
						}
					}
				}
				map = (HashMap<Integer, String>)CommonUtil.sortMapByValue(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getOtherEmployeeMap()
	 */
	@Override
	public Map<Integer, String> getOtherEmployeeMap(String subjectId) throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<ExamValuators> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			String hql = "";
			if(subjectId != null && !subjectId.isEmpty()){
				Integer deptId = (Integer)session.createQuery("select s.department.id from Subject s where s.id="+subjectId).uniqueResult();
				if(deptId != null){
					if(deptId == 97 || deptId == 98 || deptId == 99 || deptId == 100 || deptId == 101){
						hql = "select e.evaluators from ExternalEvaluatorsDepartment e where e.isActive=1 and e.evaluators.isActive=1 ";
						hql = hql + " and e.department.id in(97, 98, 99, 100, 101)";
					}else{
						hql = "select e.evaluators from ExternalEvaluatorsDepartment e where e.isActive=1 and e.evaluators.isActive=1 ";
						hql = hql + " and e.department.id="+deptId;
					}
				}
			}else{
				hql = "from ExamValuators e where e.isActive=1";
			}
			
			Query query=session.createQuery(hql);
			list= query.list();
			if(list!=null){
				Iterator<ExamValuators> iterator=list.iterator();
				while(iterator.hasNext()){
					ExamValuators valuators=iterator.next();
					if(valuators.getId() != 0 && valuators.getName() !=  null){
						map.put(valuators.getId(), valuators.getName().toUpperCase());
					}
				}
				map = (HashMap<Integer, String>)CommonUtil.sortMapByValue(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getSubjectCodeName(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Map<Integer, String> getSubjectCodeName(String academicYear,
			String displaySubType, int examId, String examType) throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			List<Subject> subjects = new ArrayList<Subject>();
			if(examType != null && examType.equalsIgnoreCase("Supplementary")){
				String query = "select sup.subject from StudentSupplementaryImprovementApplication sup" +
								" where (sup.isAppearedTheory=1 or sup.isAppearedPractical=1) " +
								" and sup.classes.termNumber in (select e.schemeNo from ExamExamCourseSchemeDetailsBO e where e.examId= "+ examId +
								") and sup.examDefinition.id="+examId+" group by sup.subject.id";
				if(displaySubType != null && displaySubType.equalsIgnoreCase("sCode")){
					query = query + " order by sup.subject.code";
				}else{
					query = query + " order by sup.subject.name";
				}
				subjects = session.createQuery(query).list();
			}else{
				String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.examId = "+ examId;
				List<Integer> courseList = session.createQuery(HQL).list();
				if(courseList != null && !courseList.isEmpty()){
					String query = "select groupsub.subject from SubjectGroup sub " +
					" join sub.curriculumSchemeSubjects curr  " +
					" join sub.subjectGroupSubjectses groupsub" +
					" join curr.curriculumSchemeDuration.classSchemewises cls" +
					" where sub.course.id in(:Courses) " +
					" and sub.isActive=1 and groupsub.isActive=1" +
					" and curr.curriculumSchemeDuration.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id= "+ examId +
					") and cls.classes.termNumber in (select e.schemeNo from ExamExamCourseSchemeDetailsBO e where e.examId= "+ examId +
					") group by groupsub.subject.id";
					if(displaySubType != null && displaySubType.equalsIgnoreCase("sCode")){
						query = query + " order by groupsub.subject.code";
					}else{
						query = query + " order by groupsub.subject.name";
					}
					subjects = session.createQuery(query).setParameterList("Courses", courseList).list();
				}
			}
			if(subjects != null){
				Iterator<Subject> iterator = subjects.iterator();
				while (iterator.hasNext()) {
					Subject subject = (Subject) iterator.next();
					if(subject.getId() != 0){
						if(displaySubType != null && displaySubType.equalsIgnoreCase("sCode"))
							map.put(subject.getId(), subject.getCode()+" "+subject.getName());
						else
							map.put(subject.getId(), subject.getName()+" "+subject.getCode());
					}
				}
			}
			map = CommonUtil.sortMapByValue(map);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getValuatorNameListBySubjectDept(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getValuatorNameListBySubjectDept(
			String subjectId) throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<Users> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			String hql = "select u from Users u left join u.employee e with e.active=1 and e.isActive=1 where u.isActive=1 and u.isTeachingStaff=1 ";
			if(subjectId != null && !subjectId.isEmpty()){
				Integer departmentId = (Integer)session.createQuery("select s.department.id from Subject s where s.id="+subjectId).uniqueResult();
				if(departmentId != null){
					if(departmentId == 97 || departmentId == 98 || departmentId == 99 || departmentId == 100 || departmentId == 101){
						hql = hql + " and (u.employee.department.id in (97, 98, 99, 100, 101) or u.department.id in (97, 98, 99, 100, 101))";
					}else{
						hql = hql + " and (u.employee.department.id="+departmentId+" or u.department.id="+departmentId+")";
					}
				}
			}
			Query query=session.createQuery(hql);
			list= query.list();
			if(list!=null){
				Iterator<Users> iterator=list.iterator();
				while(iterator.hasNext()){
					Users user=iterator.next();
					if(user!=null){
						if(user.getEmployee() != null && user.getEmployee().getFirstName() != null && user.getEmployee().getDepartment() != null && user.getEmployee().getDepartment().getName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName()+"("+user.getEmployee().getDepartment().getName()+")");
						}else if(user.getEmployee() != null && user.getEmployee().getFirstName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName());
						}else if(user.getDepartment() != null && user.getDepartment().getName() != null){
							map.put(user.getId(), user.getUserName().toUpperCase()+"("+user.getDepartment().getName()+")");
						}else{
							map.put(user.getId(), user.getUserName().toUpperCase());
						}
					}
				}
				map = (HashMap<Integer, String>)CommonUtil.sortMapByValue(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getExamValidationListBySubject(java.lang.String, java.lang.String, java.lang.String)
	 code added by  mehaboob to get examValidationDetails by subjectwise*/
	@Override
	public List<ExamValuationAnswerScript> getExamValidationListBySubject(String currentExam,String subjectId) throws Exception {
		Session session = null;
		List<ExamValuationAnswerScript> list = null;
		try{
			session = HibernateUtil.getSession();
			/*String hqlQuery = "from ExamValidationDetails e where e.isActive=1 and e.exam="+currentExam;
			if(year != null && !year.isEmpty()){
				hqlQuery = hqlQuery + " and e.exam.academicYear='"+year+"'";
			}*/
			Query query = session.createQuery("select ans from ExamValuationAnswerScript ans  where ans.validationDetailsId.subject.id="+subjectId+" and ans.validationDetailsId.exam.id="+currentExam+" and ans.isActive=1 and ans.validationDetailsId.isActive=1 order by ans.issueDate");
			list = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamValuationAnswerScript>();
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getAbsentStudentIds(java.lang.String, java.lang.String)
	 */
	@Override
	public Long getAbsentStudentIds(String currentExam,String subjectId,String examType)
			throws Exception {
		Session session = null;
		Long absentStudent = null;
		try{
			session = HibernateUtil.getSession();
			String query1="select count(m.marksEntry.student.id) from MarksEntryDetails m"+
					" where m.subject.id='"+subjectId+"'  and m.marksEntry.exam.id='"+currentExam+"'" +
					" and (m.marksEntry.evaluatorType is null or m.marksEntry.evaluatorType = 1)" +
					" and (m.theoryMarks = 'AA' or m.practicalMarks = 'AA') and m.marksEntry.student.admAppln.isCancelled=0 and " +
					" (m.marksEntry.student.isHide=0 or m.marksEntry.student.isHide is null) " +
					" and m.marksEntry.student.isActive=1  and m.marksEntry.student.isAdmitted=1";
			if(!examType.equalsIgnoreCase("Supplementary")){
				query1=query1+" and m.marksEntry.student.id not in (select det.student.id from ExamStudentDetentionRejoinDetails det where " +
					" (det.detain=1 or det.discontinued=1) and (det.rejoin=0 or det.rejoin is null))";
			}
			Query query = session.createQuery(query1);
			absentStudent = (Long) query.uniqueResult();
			if(absentStudent==null){
				absentStudent=0l;
			}
		}catch (Exception e) {
			log.error("Error while getAbsentStudentIds method.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return absentStudent;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getStudentForCurrentClass(java.lang.String, java.lang.String)
	 */
	@Override
	public Long getStudentForPreviousClass(ExamValidationDetailsForm examValidationDetailsForm)
			throws Exception {
		Session session = null;
		Long previousClassStudent = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select count(s.id) from Student s" +
						" join s.studentPreviousClassesHistory classHis" +
						" join classHis.classes.classSchemewises classSchemewise join classSchemewise.curriculumSchemeDuration cd " +
						" join s.studentSubjectGroupHistory subjHist" +
						" join subjHist.subjectGroup.subjectGroupSubjectses subSet" +
						" where s.admAppln.isCancelled=0 and s.isAdmitted=1 and subSet.isActive=1" +
						" and s.id  not in(select det.student.id from ExamStudentDetentionRejoinDetails det where " +
						"(det.detain=1 or det.discontinued=1) and (det.rejoin=0 or det.rejoin is null)) and (s.isHide is null or s.isHide=0) "+
						"and classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
						" from ExamDefinitionBO e where e.id='"+examValidationDetailsForm.getExamId()+"')" +
						" and subSet.subject.id='"+examValidationDetailsForm.getSubjectId()+"' and classHis.schemeNo=subjHist.schemeNo");
			previousClassStudent = (Long) query.uniqueResult();
			if(previousClassStudent==null){
				previousClassStudent=0l;
			}
		}catch (Exception e) {
			log.error("Error getStudentForCurrentClass method" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return previousClassStudent;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getNumberOfAlreadyIssuedScript(java.lang.String, java.lang.String)
	 */
	@Override
	public Long getNumberOfAlreadyIssuedScript(String currentExam,
			String subjectId) throws Exception {
		Session session = null;
		Long alreadyIssued;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select sum(ans.numberOfAnswerScripts) from ExamValidationDetails e join e.answerScripts ans " +
					"where e.isActive=1 and e.subject.id='"+subjectId+"' and e.exam.id='"+currentExam+"' and " +
					"(e.isValuator='Valuator1' or e.isValuator='Project Major' or e.isValuator='Project Minor') and ans.isActive=1");
			alreadyIssued =  (Long) query.uniqueResult();
		}catch (Exception e) {
			log.error("Error in getNumberOfAlreadyIssuedScript method" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return alreadyIssued;
	}
	@Override
	public Long getStudentForCurrentClass(ExamValidationDetailsForm examValidationDetailsForm)
			throws Exception {
		Session session = null;
		Long currentClassStudent = null;
		try{
			session = HibernateUtil.getSession();
			Query query;
			if(examValidationDetailsForm.getExamType().equalsIgnoreCase("Supplementary")){
				query = session.createQuery("select count(supp.student) from StudentSupplementaryImprovementApplication supp " +
						"where supp.examDefinition.id='"+examValidationDetailsForm.getExamId()+"' and supp.subject.id='"+examValidationDetailsForm.getSubjectId()+"' " +
						"and (supp.isAppearedTheory=1 or supp.isAppearedPractical=1)");
			}else{
				query = session.createQuery("select count(s.id) from Student s" +
						" join s.admAppln.applicantSubjectGroups appSub" +
						" join appSub.subjectGroup.subjectGroupSubjectses subGroup" +
						" where s.admAppln.isCancelled=0 and s.isAdmitted=1  and subGroup.isActive=1" +
						" and s.id  not in(select det.student.id from ExamStudentDetentionRejoinDetails det where " +
						" (det.detain=1 or det.discontinued=1) and (det.rejoin=0 or det.rejoin is null)) and (s.isHide is null or s.isHide=0)"+
						" and s.classSchemewise.curriculumSchemeDuration.academicYear=(select e.academicYear" +
						" from ExamDefinitionBO e where e.id='"+examValidationDetailsForm.getExamId()+"') " +
						" and subGroup.subject.id='"+examValidationDetailsForm.getSubjectId()+"'");	
			}
			currentClassStudent = (Long) query.uniqueResult();
			if(currentClassStudent==null){
				currentClassStudent=0l;
			}
		}catch (Exception e) {
			log.error("Error getStudentForCurrentClass method" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return currentClassStudent;
	}
	@Override
	public boolean updateNumberOfScriptsAndValuator(
			ExamValidationDetailsForm examValidationDetailsForm)
			throws Exception {
		Session session = null;
		ExamValuationAnswerScript examValuationAnswerScript=null;
		boolean isUpdated=false;
		Transaction transaction=null;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query = session.createQuery("from ExamValuationAnswerScript e where e.isActive=1 and e.id="+examValidationDetailsForm.getId());
			examValuationAnswerScript = (ExamValuationAnswerScript) query.uniqueResult();
			if(examValuationAnswerScript!=null){
				examValuationAnswerScript.setNumberOfAnswerScripts(Integer.parseInt(examValidationDetailsForm.getAnswerScripts().getAnswerScripts()));
				ExamValidationDetails examValidationDetails=examValuationAnswerScript.getValidationDetailsId();
				if(examValidationDetails!=null){
					examValidationDetails.setIsValuator(examValidationDetailsForm.getAnswerScripts().getValuator());
				}
				examValuationAnswerScript.setValidationDetailsId(examValidationDetails);
				session.update(examValuationAnswerScript);
				transaction.commit();
				isUpdated=true;
			}
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
	}
	
	public Long getNumberOfAlreadyIssuedScriptForEvl2(String currentExam,
			String subjectId) throws Exception {
		Session session = null;
		Long alreadyIssued;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select sum(ans.numberOfAnswerScripts) from ExamValidationDetails e join e.answerScripts ans " +
					" where e.isActive=1 and e.subject.id='"+subjectId+"' and e.exam.id='"+currentExam+"' and " +
					" e.isValuator='Valuator2' and ans.isActive=1");
			alreadyIssued =  (Long) query.uniqueResult();
		}catch (Exception e) {
			log.error("Error in getNumberOfAlreadyIssuedScript method" +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return alreadyIssued;
	}
	
	
	/* (non-Javadoc) method added by chandra
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getValuatorListBySubjectDeptFromValuationScheduleDetails(java.lang.String, int)
	 */
	public Map<Integer, String> getValuatorListBySubjectDeptFromValuationScheduleDetails(
			String subjectId,int examID) throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<Users> list=null;
		Session session=null;
		String hql="";
		try {
			session=HibernateUtil.getSession();
			if(subjectId!=null && !subjectId.isEmpty()){
				
				 hql = "select ev.users from ExamValuationScheduleDetails ev  "+
				 " inner join ev.users u " +
				 " left join u.employee e with e.active=1 and e.isActive=1"+
				 " where u.isActive=1 and u.isTeachingStaff=1 and u.active=1 "+
				 " and ev.isActive=1 and ev.subject.id="+subjectId+" and ev.exam.id="+examID;
			
			}else{
				 hql = "select u from Users u left join u.employee e with e.active=1 and e.isActive=1 where u.isActive=1 and u.isTeachingStaff=1 ";
			}
			Query query=session.createQuery(hql);
			list= query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Users> iterator=list.iterator();
				while(iterator.hasNext()){
					Users user=iterator.next();
					if(user!=null){
						if(user.getEmployee() != null && user.getEmployee().getFirstName() != null && user.getEmployee().getDepartment() != null && user.getEmployee().getDepartment().getName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName()+"("+user.getEmployee().getDepartment().getName()+")");
						}else if(user.getEmployee() != null && user.getEmployee().getFirstName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName());
						}else if(user.getGuest() != null && user.getGuest().getFirstName() != null){
							map.put(user.getId(), user.getGuest().getFirstName());
						}else if(user.getDepartment() != null && user.getDepartment().getName() != null){
							map.put(user.getId(), user.getUserName().toUpperCase()+"("+user.getDepartment().getName()+")");
						}else{
							map.put(user.getId(), user.getUserName().toUpperCase());
						}
						
					}
				}
				map = (HashMap<Integer, String>)CommonUtil.sortMapByValue(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	
	
	/* (non-Javadoc) method added by chandra
	 * @see com.kp.cms.transactions.exam.IExamValidationDetailsTransaction#getOtherEmployeeMapFromValuationScheduleDetails(java.lang.String, int)
	 */
	public Map<Integer, String> getOtherEmployeeMapFromValuationScheduleDetails(String subjectId,int examID) throws Exception {
			
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<ExamValuators> list=null;
		Session session=null;
		String hql="";
		try {
			session=HibernateUtil.getSession();
			if(subjectId!=null && !subjectId.isEmpty()){
				
				 hql = "select ev.examValuators from ExamValuationScheduleDetails ev  "+
				 		"  where ev.isActive=1 and ev.subject.id="+subjectId+" and ev.exam.id="+examID;
			}else{
				hql = "from ExamValuators e where e.isActive=1";
			}
			
			Query query=session.createQuery(hql);
			list= query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<ExamValuators> iterator=list.iterator();
				while(iterator.hasNext()){
					ExamValuators valuators=iterator.next();
					if(valuators.getId() != 0 && valuators.getName() !=  null){
						map.put(valuators.getId(), valuators.getName().toUpperCase());
					}
				}
				map = (HashMap<Integer, String>)CommonUtil.sortMapByValue(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
}