package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceSummaryReportForm;
import com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author kalyan.c
 * DAO class for Attendance Summary Report
 */
/**
 * @author kalyan.c
 *
 */
public class AttendanceSummaryReportTxnImpl implements IAttendanceSummaryReportTxnImpl{
	
	private static final Log log = LogFactory.getLog(AttendanceSummaryReportTxnImpl.class);
	
	/**
	 * 
	 */
	public static volatile AttendanceSummaryReportTxnImpl self=null;
	/**
	 * @return
	 * This method will return instance of this class
	 */
	public static AttendanceSummaryReportTxnImpl getInstance(){
		if(self==null)
			self= new AttendanceSummaryReportTxnImpl();
		return self;
	}
	/**
	 * 
	 */
	private AttendanceSummaryReportTxnImpl(){
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getStudentAttendance(java.lang.String)
	 * This method will get students based on the search criteria
	 */
	public List getStudentAttendance(String searchCriteria) throws ApplicationException {
		log.info("entered getStudentAttendance..");
		Session session = null;
		List studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			
			 studentSearchResult = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getStudentAttendance..");
		return studentSearchResult;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getClassesHeld(java.lang.String)
	 * This method will give the classes held for a particular subject
	 */
	public int getClassesHeld(String searchCriteria) throws ApplicationException {
		log.info("entered getClassesHeld..");
		Session session = null;
		int classesHeld = 0;
		List classHld = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			classHld = session.createQuery(searchCriteria).list();
			
			//------------------------------------   modify start modified to fix sum of hours held
			
			if(classHld!=null && !classHld.isEmpty() && !classHld.get(0).toString().equals("")){
				classesHeld = Integer.valueOf(classHld.get(0).toString());
			}
			//------------------------------------   modify end ------------------
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getClassesHeld..");
		return classesHeld;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getClassesAttended(java.lang.String)
	 * This method will give the classes attended for particular subject by particular student
	 */
	public int getClassesAttended(String searchCriteria) throws ApplicationException {
		log.info("entered getClassesAttended..");
		Session session = null;
		int classesAtten = 0;
		List classAttd = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			classAttd = session.createQuery(searchCriteria).list();			
			//------------------------------------   modify start modified to fix sum of hours held
//			if(classAttd!=null){
//				classesAtten = classAttd.size();
//			}
			if(classAttd!=null && !classAttd.isEmpty() && !classAttd.get(0).toString().equals("")){
				classesAtten = Integer.valueOf(classAttd.get(0).toString());
			}
			//------------------------------------   modify end ------------------
		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getClassesAttended..");
		return classesAtten;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getActivityAttended(java.lang.String)
	 * This method will give the Activity Attended for particular student
	 */
	@Override
	public int getActivityAttended(String searchCriteria)
			throws ApplicationException {
		log.info("entered getActivityAttended..");
		Session session = null;
		int activityAtten = 0;
		List activityAttd = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			activityAttd = session.createQuery(searchCriteria).list();			
			if(activityAttd!=null){
				activityAtten = activityAttd.size();
			}

		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getActivityAttended..");
		return activityAtten;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getActivityHeld(java.lang.String)
	 * This method will give the Activity Held for particular student
	 */
	@Override
	public int getActivityHeld(String searchCriteria)
			throws ApplicationException {
		log.info("entered getActivityHeld..");
		Session session = null;
		int activityHeld = 0;
		List activityHld = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			activityHld = session.createQuery(searchCriteria).list();			
			if(activityHld!=null){
				activityHeld = activityHld.size();
			}

		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getActivityHeld..");
		return activityHeld;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getSelectedActivityAttended(java.lang.String)
	 * This method will give the Selected Activity Attended for particular student
	 */
	@Override
	public int getSelectedActivityAttended(String searchCriteria)
			throws ApplicationException {
		log.info("entered getSelectedActivityAttended..");
		Session session = null;
		int selectedActAttend = 0;
		List selectedActAttd = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			selectedActAttd = session.createQuery(searchCriteria).list();			
			if(selectedActAttd!=null){
				selectedActAttend = selectedActAttd.size();
			}

		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getSelectedActivityAttended..");
		return selectedActAttend;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceSummaryReportTxnImpl#getSelectedActivityHeld(java.lang.String)
	 * This method will give the Selected Activity Held for particular student
	 */
	@Override
	public int getSelectedActivityHeld(String searchCriteria)
			throws ApplicationException {
		log.info("entered getSelectedActivityHeld..");
		Session session = null;
		int selectedActHld = 0;
		List selectedActHeld = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			selectedActHeld = session.createQuery(searchCriteria).list();			
			if(selectedActHeld!=null){
				selectedActHld = selectedActHeld.size();
			}

		} catch (Exception e) {
			log.error("error while getting the student attendance results  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("exit getSelectedActivityHeld..");
		return selectedActHld;
	}
	@Override
	public Map<Integer, Subject> getSubjectByCourseIdTermYear(int courseId,
			int year, int term) throws ApplicationException {
		List<CurriculumSchemeDuration> curriculumSchemeDurationList = null;
		CurriculumSchemeDuration curriculumSchemeDuration = null;
		Map<Integer, Subject> subjectMap = new HashMap<Integer, Subject>();
		try {
			Session session = null;
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			curriculumSchemeDurationList = session.createQuery(
					"from CurriculumSchemeDuration c where c.curriculumScheme.course.id = "
							+ courseId + " and c.academicYear = " + year
							+ "and c.semesterYearNo = " + term).list();

			if (!curriculumSchemeDurationList.isEmpty()) {
				curriculumSchemeDuration = curriculumSchemeDurationList.get(0);
			}

			if (curriculumSchemeDuration != null) {
				CurriculumSchemeSubject curriculumSchemeSubject;
				Set<CurriculumSchemeSubject> curriculumsubjectSet = curriculumSchemeDuration
						.getCurriculumSchemeSubjects();
				if (curriculumsubjectSet != null
						&& !curriculumsubjectSet.isEmpty()) {
					Iterator<CurriculumSchemeSubject> corriculumSchemaIterator = curriculumsubjectSet
							.iterator();
					while (corriculumSchemaIterator.hasNext()) {
						curriculumSchemeSubject = corriculumSchemaIterator.next();
						if (curriculumSchemeSubject.getSubjectGroup() != null
								&& curriculumSchemeSubject.getSubjectGroup()
										.getSubjectGroupSubjectses() != null
								&& !curriculumSchemeSubject.getSubjectGroup()
										.getSubjectGroupSubjectses().isEmpty()) {
							Set<SubjectGroupSubjects> subjectSet = curriculumSchemeSubject
									.getSubjectGroup()
									.getSubjectGroupSubjectses();
							if (curriculumSchemeSubject.getSubjectGroup() != null
									&& curriculumSchemeSubject
											.getSubjectGroup().getIsActive() == true) {
								if (subjectSet != null && !subjectSet.isEmpty()) {
									SubjectGroupSubjects subjectGroupSubjects;
									Iterator<SubjectGroupSubjects> subjectGroupIterator = subjectSet
											.iterator();
									while (subjectGroupIterator.hasNext()) {
										subjectGroupSubjects = subjectGroupIterator.next();
										if (subjectGroupSubjects.getIsActive() == true
												&& subjectGroupSubjects
														.getSubject() != null
												&& subjectGroupSubjects
														.getSubject()
														.getIsActive() != null
												&& subjectGroupSubjects
														.getSubject()
														.getIsActive() == true
												&& subjectGroupSubjects
														.getSubject().getId() != 0
												&& subjectGroupSubjects
														.getSubject().getName() != null) {
											subjectMap.put(subjectGroupSubjects
													.getSubject().getId(),
													subjectGroupSubjects
															.getSubject());
										}
									}
								}
							}
						}
					}
				}
			}
			session.flush();
			//session.close();

			return subjectMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, Subject>();
	}
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception
	{
		List<Integer> studentList=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			/*	String query="select s.student.id from ExamStudentDetentionRejoinDetails s " +
							"where s.id=(select max(id) from ExamStudentDetentionRejoinDetails b " +
							"where b.student.id=s.student.id) and (s.detain=1 or s.discontinued=1)";
			*/
			String query="select s.student.registerNo from ExamStudentDetentionRejoinDetails s " +
			"where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)";
			studentList=session.createQuery(query).list();
		}
		catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
		
		return studentList;
	}
	@Override
	public List getAttendanceType() throws Exception {
		Session session=null;
		List attendanceType=null;
		try{
			session=HibernateUtil.getSession();
			String quer="select atype.id from AttendanceType atype where atype.isActive=1";
			Query query=session.createQuery(quer);
			attendanceType=query.list();
		}catch(Exception exception){
			throw  new ApplicationException(exception);
		}
		return attendanceType;
	}
	public Date getStartDate(List classes,int academicYear){
		Session session=null;
		java.sql.Date startDate=null;
		List<CurriculumSchemeDuration> curriculum=null;
		try{
			session=HibernateUtil.getSession();
			
			String quer=" from CurriculumSchemeDuration cur"+
                        " where cur.curriculumScheme.id in (select DISTINCT c.id from CurriculumScheme c"+ 
                        " where c.course.id in(select DISTINCT cs.curriculumScheme.course.id from CurriculumSchemeDuration cs"+ 
                        " join cs.curriculumScheme.course.classes classes"+
                        " where classes.id in (:classes)))"+
                        " and cur.academicYear = "+academicYear;
			Query querys=session.createQuery(quer);
			querys.setParameterList("classes", classes);
			curriculum=querys.list();
			Iterator itr=curriculum.iterator();
			
		}catch(Exception exception){
			log.error("error in get start Date ");
		}
 		return startDate;
	}
	
	public Map<Integer, String> getClassByYearUserId(int year, AttendanceSummaryReportForm attendanceSummaryReportForm ) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TeacherClass t " +
					" where t.classId.curriculumSchemeDuration.academicYear="+year+" and t.isActive=1 and t.teacherId.id="+attendanceSummaryReportForm.getUserId()+" order by t.classId.classes.name");
			        
			List<TeacherClass> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			if(classList!=null && !classList.isEmpty())
			{
				Iterator<TeacherClass> itr = classList.iterator();
				TeacherClass teacherclass;
				int count = 0;
				int classArrayLen = classList.size();
				String classname[] = new String[classArrayLen];
				
				while (itr.hasNext()) {
					teacherclass = (TeacherClass) itr.next();
				classMap.put(teacherclass.getClassId().getClasses().getId(), teacherclass.getClassId().getClasses().getName());
				classname[count]=String.valueOf(teacherclass.getClassId().getClasses().getId());
				count=count+1;
				}
				attendanceSummaryReportForm.setClassesName(classname);
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	
	public Map<Integer, String> getSubjectByCourseTermYear(int courseId,
			int year, int term, AttendanceSummaryReportForm attendanceSummaryReportForm) {
		List<CurriculumSchemeDuration> curriculumSchemeDurationList = null;
		CurriculumSchemeDuration curriculumSchemeDuration = null;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			curriculumSchemeDurationList = session.createQuery(
					"from CurriculumSchemeDuration c where c.curriculumScheme.course.id = "
							+ courseId + " and c.academicYear = " + year
							+ "and c.semesterYearNo = " + term).list();
			int count = 0;
			if (!curriculumSchemeDurationList.isEmpty()) {
				curriculumSchemeDuration = curriculumSchemeDurationList.get(0);
			}

			if (curriculumSchemeDuration != null) {
				CurriculumSchemeSubject curriculumSchemeSubject;
				Set<CurriculumSchemeSubject> curriculumsubjectSet = curriculumSchemeDuration
						.getCurriculumSchemeSubjects();
				if (curriculumsubjectSet != null
						&& !curriculumsubjectSet.isEmpty()) {
					Iterator<CurriculumSchemeSubject> it1 = curriculumsubjectSet
							.iterator();
					
					while (it1.hasNext()) {
						curriculumSchemeSubject = it1.next();
						if (curriculumSchemeSubject.getSubjectGroup() != null
								&& curriculumSchemeSubject.getSubjectGroup()
										.getSubjectGroupSubjectses() != null
								&& !curriculumSchemeSubject.getSubjectGroup()
										.getSubjectGroupSubjectses().isEmpty()) {
							Set<SubjectGroupSubjects> subjectSet = curriculumSchemeSubject
									.getSubjectGroup()
									.getSubjectGroupSubjectses();
							if (curriculumSchemeSubject.getSubjectGroup() != null
									&& curriculumSchemeSubject
											.getSubjectGroup().getIsActive() == true) {
								if (subjectSet != null && !subjectSet.isEmpty()) {
									SubjectGroupSubjects subjectGroupSubjects;
									Iterator<SubjectGroupSubjects> it2 = subjectSet
											.iterator();
									
									while (it2.hasNext()) {
										subjectGroupSubjects = it2.next();
										if (subjectGroupSubjects.getIsActive() == true
												&& subjectGroupSubjects
														.getSubject() != null
												&& subjectGroupSubjects
														.getSubject()
														.getIsActive() != null
												&& subjectGroupSubjects
														.getSubject()
														.getIsActive() == true
												&& subjectGroupSubjects
														.getSubject().getId() != 0
												&& subjectGroupSubjects
														.getSubject().getName() != null 
												&&	(subjectGroupSubjects.getSubject().getIsCertificateCourse()==null ||
													 subjectGroupSubjects.getSubject().getIsCertificateCourse()==false )) {
											subjectMap
													.put(
															subjectGroupSubjects
																	.getSubject()
																	.getId(),
															subjectGroupSubjects
																	.getSubject()
																	.getName()
																	+ "("
																	+ subjectGroupSubjects
																			.getSubject()
																			.getCode()
																	+ ")");
										//	sub[count]=String.valueOf(subjectGroupSubjects.getSubject().getId());
										//	count=count+1;
										}
										
									}
									
								}
							}
						}
					}
				}
				
			}
		//	attendanceSummaryReportForm.setSubArray(sub);
			
			session.flush();
			// session.close();
			Map<Integer, String> valueMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
			return valueMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	
	
	
	
	public Map<Integer, String> getSubjectByClass(Set<Integer> classesIdsSet) {
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		List<Integer> classIDList=new ArrayList<Integer>();
		Map<Integer, String> valueMap =new HashMap<Integer, String>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			
			Iterator<Integer> iterator = classesIdsSet.iterator();
        	while (iterator.hasNext()) {
        		Integer classId = (Integer) iterator.next();
					classIDList.add(classId);
			}
			if(classIDList!=null && !classIDList.isEmpty()){
			Query query= session.createQuery("select sub.id,sub.name,sub.code from CurriculumSchemeSubject curSchsub"+
																" inner join curSchsub.curriculumSchemeDuration curSchDur "+
																" inner join curSchDur.classSchemewises   clsSchW "+
																" inner join clsSchW.classes cls "+
																" inner join curSchsub.subjectGroup subGrp"+
																" inner join subGrp.subjectGroupSubjectses subGrpSub"+
																" join subGrpSub.subject sub "+
																" where subGrp.isActive=1 and cls.isActive=1 and subGrpSub.isActive=1 and sub.isActive=1"+
																" and (sub.isCertificateCourse=0 or sub.isCertificateCourse is null)"+
																" and cls.id in(:classIDs) group by sub.id");
			query.setParameterList("classIDs", classIDList);
			List<Object[]> list = query.list();
            if(list!=null && !list.isEmpty()){
            	Iterator<Object[]> iterator1 = list.iterator();
            	while (iterator1.hasNext()) {
					Object[] objects = (Object[]) iterator1.next();
					if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
						subjectMap.put(Integer.parseInt(objects[0].toString()), (objects[1].toString()).toString()+"("+(objects[2].toString()).toString()+")");
					}
				}
            }
            valueMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
			}
			session.flush();
			session.close();
			
			return valueMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

}
