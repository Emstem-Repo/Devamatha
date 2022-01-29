package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ValuationScheduleForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IValuationScheduleTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ValuationScheduleTransactionImpl implements IValuationScheduleTransaction {
	/**
	 * Singleton object of NewExamMarksEntryTransactionImpl
	 */
	private static volatile ValuationScheduleTransactionImpl newExamMarksEntryTransactionImpl = null;
	private static final Log log = LogFactory.getLog(ValuationScheduleTransactionImpl.class);
	
	private ValuationScheduleTransactionImpl() {
		
	}
	/**
	 * return singleton object of NewExamMarksEntryTransactionImpl.
	 * @return
	 */
	public static ValuationScheduleTransactionImpl getInstance() {
		if (newExamMarksEntryTransactionImpl == null) {
			newExamMarksEntryTransactionImpl = new ValuationScheduleTransactionImpl();
		}
		return newExamMarksEntryTransactionImpl;
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
								" and sup.examDefinition.id="+examId+" group by sup.subject.id";
				if(displaySubType != null && displaySubType.equalsIgnoreCase("sCode")){
					query = query + " order by sup.subject.code";
				}else{
					query = query + " order by sup.subject.name";
				}
				subjects = session.createQuery(query).list();
			}else{
				String HQL = "select distinct courseId from ExamExamCourseSchemeDetailsBO e where e.examId = "+ examId;
				List<Integer> courseList = session.createQuery(HQL).list();
				if(courseList != null){
					String query = "select groupsub.subject from SubjectGroup sub " +
					" join sub.curriculumSchemeSubjects curr  " +
					" join sub.subjectGroupSubjectses groupsub" +
					" where sub.course.id in(:Courses) " +
					" and sub.isActive=1 and groupsub.isActive=1" +
					" and curr.curriculumSchemeDuration.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id= "+ examId +
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
	
	@Override
	public Map<Integer, String> getEmployeeMap() throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<Users> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(" select u from Users u left join u.employee e with e.active=1 and e.isActive=1 left join u.guest g with g.active=1 and g.isActive=1 where u.isActive=1 and ( u.isTeachingStaff=1 or g.teachingStaff=1 or e.teachingStaff=1 ) ");
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
						}else if(user.getGuest() != null && user.getGuest().getFirstName() != null && user.getGuest().getDepartment() != null && user.getGuest().getDepartment().getName() != null){
							map.put(user.getId(), user.getGuest().getFirstName()+"("+user.getGuest().getDepartment().getName()+")");
						}else if(user.getGuest() != null && user.getGuest().getFirstName() != null){
							map.put(user.getId(), user.getGuest().getFirstName());
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
	
	@Override
	public Map<Integer, String> getOtherEmployeeMap() throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<ExamValuators> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			String hql = "";
			/*//if(subjectId != null && !subjectId.isEmpty()){
				Integer deptId = (Integer)session.createQuery("select s.department.id from Subject s where s.id="+subjectId).uniqueResult();
				if(deptId != null){
					hql = "select e.evaluators from ExternalEvaluatorsDepartment e where e.isActive=1";
					hql = hql + " and e.department.id="+deptId;
				}
			}else{
				hql = "from ExamValuators e where e.isActive=1";
			}*/
			hql = "from ExamValuators e where e.isActive=1";
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
	
	public boolean saveDetails(	List<ExamValuationScheduleDetails> scheduleDetails) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			Iterator<ExamValuationScheduleDetails> itr=scheduleDetails.iterator();
			while (itr.hasNext()) {
			ExamValuationScheduleDetails examValuation = (ExamValuationScheduleDetails) itr.next();
			transaction = session.beginTransaction();
			session.save(examValuation);
			transaction.commit();
			}
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
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
			list = new ArrayList<Integer>();
		}
		return courses;
	}
	
	public ExamValuationScheduleDetails getDetailsForEdit(int id) throws Exception {
		Session session = null;
		ExamValuationScheduleDetails details = new ExamValuationScheduleDetails();
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamValuationScheduleDetails e where e.id="+id);
			details = (ExamValuationScheduleDetails)query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return details;
	}
	
	public boolean deleteDetails(int id) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			ExamValuationScheduleDetails scheduleDetails=(ExamValuationScheduleDetails)session.get(ExamValuationScheduleDetails.class,id);
			scheduleDetails.setIsActive(false);
			scheduleDetails.setLastModifiedDate(new Date());
			
			session.update(scheduleDetails);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting ExamValuationScheduleDetails in IMPL :"+e);	
			throw  new ApplicationException(e);
		} 
		finally {
			if (session != null) {
				session.flush();
				session.close();
			}
			log.info("End of deleteDetails of ExamValuationScheduleDetails TransactionImpl");
			}
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
	
	public List<ExamValuationScheduleDetails> getValuationSchedule(String currentExam,String year) {
		Session session = null;
		List<ExamValuationScheduleDetails> list = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select e from ExamValuationScheduleDetails e where e.isActive=1 and e.exam="+currentExam+" group by e.id");
			list = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamValuationScheduleDetails>();
		}
		return list;
	}
	
	
	public ExamValuationScheduleDetails getDetailsonId(int id)throws Exception
	{
		log.info("Start of getDetailsonId of ExamValuationScheduleDetails TransactionImpl");
		Session session = null;
		ExamValuationScheduleDetails scheduleDetails = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from ExamValuationScheduleDetails e where e.id= :row");
			query.setInteger("row", id);
			scheduleDetails = (ExamValuationScheduleDetails)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getDetailsonId of ExamValuationScheduleDetails TransactionImpl");
		return scheduleDetails;	
	}
	
	public boolean updateDetails(List<ExamValuationScheduleDetails> scheduleDetails) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Iterator<ExamValuationScheduleDetails> itr=scheduleDetails.iterator();
			while (itr.hasNext()) {
			ExamValuationScheduleDetails examValuation = (ExamValuationScheduleDetails) itr.next();
			transaction = session.beginTransaction();
			session.merge(examValuation);
			transaction.commit();
			}
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	
	public ExamValuationScheduleDetails checkForDuplicate(ValuationScheduleForm scheduleForm)throws Exception
	{
		Session session = null;
		ExamValuationScheduleDetails scheduleDetails = null;
		String[] selectedValuators=scheduleForm.getSelectedEmployeeId();
		String[] selectedExternal=scheduleForm.getSelectedExternalId();
		
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
	if(selectedExternal!=null && selectedExternal.length>0){
		for(int i=0;i<selectedExternal.length;i++){
			String str=  "from ExamValuationScheduleDetails e where e.isActive=1" ;
			if(scheduleForm.getExamId()!=null && !scheduleForm.getExamId().trim().isEmpty())
				str = str + " and e.exam.id=" +Integer.parseInt(scheduleForm.getExamId());
			if(scheduleForm.getSubjectId()!=null && !scheduleForm.getSubjectId().trim().isEmpty())
				str = str + " and e.subject.id=" +Integer.parseInt(scheduleForm.getSubjectId());
			if(selectedExternal[i]!= null)
				str = str + " and e.examValuators.id="+selectedExternal[i];
			if(scheduleForm.getIsReviewer() != null)
				str = str + " and e.isValuator='"+scheduleForm.getIsReviewer()+"'";
			
			Query query = session.createQuery(str);
			scheduleDetails = (ExamValuationScheduleDetails)query.uniqueResult();
			if(scheduleDetails!=null){
				return scheduleDetails;	
			   }
			}
	}if(selectedValuators!=null && selectedValuators.length>0){
		for(int j=0;j<selectedValuators.length;j++){
				String str=  "from ExamValuationScheduleDetails e where e.isActive=1" ;
				if(scheduleForm.getExamId()!=null && !scheduleForm.getExamId().trim().isEmpty())
					str = str + " and e.exam.id=" +Integer.parseInt(scheduleForm.getExamId());
				if(scheduleForm.getSubjectId()!=null && !scheduleForm.getSubjectId().trim().isEmpty())
					str = str + " and e.subject.id=" +Integer.parseInt(scheduleForm.getSubjectId());
				if(selectedValuators[j]!= null)
					str = str + " and e.users.id="+selectedValuators[j];
				if(scheduleForm.getIsReviewer() != null)
					str = str + " and e.isValuator='"+scheduleForm.getIsReviewer()+"'";
				Query query = session.createQuery(str);
				scheduleDetails = (ExamValuationScheduleDetails)query.uniqueResult();
				if(scheduleDetails!=null){
					return scheduleDetails;	
				   }
		      }
	       }
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return scheduleDetails;	
	}
	
	public List<ExamValuationScheduleDetails> getValuationScheduleList(String currentExam,String year) throws Exception {
		Session session = null;
		List<ExamValuationScheduleDetails> list = null;
		try{
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("select e from ExamValuationScheduleDetails e where e.isActive=1 and e.exam="+currentExam+" group by e.id");
			list = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamValuationScheduleDetails>();
		}
		return list;
	}
	@Override
	public Map<Integer, String> getOtherEmployeeMap(String subjectId)throws Exception {
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
	@Override
	public Map<Integer, String> getValuatorNameListBySubjectDept(String subjectId) throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<Users> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			//All Modify by manu.added guest faculty
			String hql = "select u from Users u left join u.employee e with e.active=1 and e.isActive=1 left join u.guest g with g.active=1 and g.isActive=1 where u.isActive=1 and ( u.isTeachingStaff=1 or g.teachingStaff=1 or e.teachingStaff=1 ) ";
			if(subjectId != null && !subjectId.isEmpty()){
				Integer departmentId = (Integer)session.createQuery("select s.department.id from Subject s where s.id="+subjectId).uniqueResult();
				if(departmentId != null){
					if(departmentId == 97 || departmentId == 98 || departmentId == 99 || departmentId == 100 || departmentId == 101){
						hql = hql + " and (u.employee.department.id in (97, 98, 99, 100, 101) or u.department.id in (97, 98, 99, 100, 101) or u.guest.department.id in (97, 98, 99, 100, 101))";
					}else{
						hql = hql + " and (u.employee.department.id="+departmentId+" or u.guest.department.id="+departmentId+" or u.department.id="+departmentId+")";
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
						}else if(user.getGuest() != null && user.getGuest().getFirstName() != null && user.getGuest().getDepartment() != null && user.getGuest().getDepartment().getName() != null){
							map.put(user.getId(), user.getGuest().getFirstName()+"("+user.getGuest().getDepartment().getName()+")");
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
	@Override
	public Map<Integer, String> getValuatorsAllList() throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<Users> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			String hql = "select u from Users u left join u.employee e with e.active=1 and e.isActive=1 left join u.guest g with g.active=1 and g.isActive=1 where u.isActive=1 and ( u.isTeachingStaff=1 or g.teachingStaff=1 or e.teachingStaff=1 ) ";
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
						}else if(user.getGuest() != null && user.getGuest().getFirstName() != null && user.getGuest().getDepartment() != null && user.getGuest().getDepartment().getName() != null){
							map.put(user.getId(), user.getGuest().getFirstName()+"("+user.getGuest().getDepartment().getName()+")");
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
	@Override
	public Map<Integer, String> getOtherEmployeeAllMap() throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<ExamValuators> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			String hql = "from ExamValuators e where e.isActive=1";
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

}
