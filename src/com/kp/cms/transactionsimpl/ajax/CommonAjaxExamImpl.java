package com.kp.cms.transactionsimpl.ajax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.handlers.exam.ExamAssignExaminerDutiesHandler;
import com.kp.cms.handlers.exam.ExamAssignOverallMarksHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamDefinitionHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamRejoinHandler;
import com.kp.cms.handlers.exam.ExamResultsHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksVerificationHandler;
import com.kp.cms.handlers.exam.ExamSpecializationSubjectGroupHandler;
import com.kp.cms.handlers.exam.ExamTimeTableHandler;
import com.kp.cms.handlers.exam.ExamUpdateExcludeWithheldHandler;
import com.kp.cms.helpers.exam.ExamUniversityRegisterNumberEntryHelper;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.ajax.ICommonExamAjax;
import com.kp.cms.transactionsimpl.exam.ExamUniversityRegisterNumberEntryImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 * Transaction Implementation Class for Common Ajax Exam related transactions
 * across project.
 */
@SuppressWarnings("static-access")
public class CommonAjaxExamImpl implements ICommonExamAjax {
	/**
	 * Jan 26, 2010 Created By 9Elements
	 */

	private static final Log log = LogFactory.getLog(CommonAjaxExamImpl.class);
	private static volatile CommonAjaxExamImpl commonAjaxExamImpl = null;
	private static volatile ExamGenHandler handler = null;

	public static CommonAjaxExamImpl getInstance() {
		if (commonAjaxExamImpl == null) {
			commonAjaxExamImpl = new CommonAjaxExamImpl();
			return commonAjaxExamImpl;
		}
		return commonAjaxExamImpl;
	}

	public static ExamGenHandler getHandler() {
		if (handler == null) {
			handler = new ExamGenHandler();
			return handler;
		}
		return handler;
	}

	// get course Map(program-course) based on program type
	public Map<Integer, String> getCourseByProgramType(int pid) {
		ExamGenHandler c = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> courseMap;
		try {
			courseMap = c.getProgramCourse(pid);
		} catch (Exception e) {
			courseMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return courseMap;

	}

	// get course Map(program-course) based on program type
	public Map<Integer, String> getCoursesByProgramTypes(String pids) {
		ExamGenHandler c = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> courseMap;
		try {
			courseMap = c.getCoursesByProgramTypes(pids);
		} catch (Exception e) {
			courseMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return courseMap;

	}

	/*
	 * This function is uses in Exam module like Assignment/Overall marks,
	 */
	public Map<Integer, String> getCoursesByAcademicYear(int year) {
		ExamAssignOverallMarksHandler handler = new ExamAssignOverallMarksHandler();
		Map<Integer, String> courseMap;
		try {
			courseMap = handler.getCourseByAcademicYear(year);
		} catch (Exception e) {
			courseMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return courseMap;
	}

	public Map<Integer, String> getSchemeByCourseId(int cid) {
		ExamGenHandler c = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap;
		try {
			schemeMap = c.getSchemeByCourse(Integer.toString(cid));
		} catch (Exception e) {
			schemeMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return schemeMap;
	}

	public Map<Integer, String> getProgramsByPType(int pid) {
		ExamGenHandler c = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> programMap;
		try {
			programMap = c.getProgramsByPType(pid);
		} catch (Exception e) {
			programMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return programMap;
	}

	public Map<Integer, String> getProgramsByPTypes(int pid) {
		ExamGenHandler c = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> programMap;
		try {
			programMap = c.getProgramsByPType(pid);
		} catch (Exception e) {
			programMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return programMap;
	}

	public Map<Integer, String> getClasesByExamName(String examName) {

		return getClassCodeByExamName(Integer.parseInt(examName));
		// ExamBlockUnblockHallTicketHandler c = new
		// ExamBlockUnblockHallTicketHandler();
		// Map<Integer, String> classMap;
		// try {
		// classMap = c.getClasesByExamName(examName);
		// } catch (Exception e) {
		// classMap = new HashMap<Integer, String>();
		// log.debug("Exception" + e.getMessage());
		// }
		// return classMap;
	}

	public Map<Integer, String> getClasesByJoingBatch(String joiningBatch) {
		ExamRejoinHandler rejoin = new ExamRejoinHandler();
		Map<Integer, String> classMap;
		try {
			classMap = rejoin.getClasesByJoingBatch(joiningBatch);
		} catch (Exception e) {
			classMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return classMap;
	}

	public Map<Integer, String> getSchemesByCourseId(String courseId) {
		ExamSpecializationSubjectGroupHandler handler = new ExamSpecializationSubjectGroupHandler();
		Map<Integer, String> schemeMap;
		try {
			schemeMap = handler.getSchemeByCourse(courseId);
		} catch (Exception e) {
			schemeMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return schemeMap;
	}

	public Map<Integer, String> getCourseByExamName(String examName) {
		ExamUpdateExcludeWithheldHandler handler = new ExamUpdateExcludeWithheldHandler();
		Map<Integer, String> classMap;
		try {
			classMap = handler.getCourse(examName);
		} catch (Exception e) {
			classMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return classMap;
	}

	public Map<Integer, String> getSchemeValuesBySchemeId(int fromschemeId,
			int toSchemeId) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler.select_SchemeIdFromClassId(
				Integer.toString(fromschemeId), Integer.toString(toSchemeId));

		return schemeMap;
	}

	// Exam Time Table
	public Map<Integer, String> getExamNameByExamTypeId(int examTypeId) {

		ExamTimeTableHandler h = new ExamTimeTableHandler();
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		return handler.getExamNameByExamTypeId(h.getExamName(examTypeId));
	}

	public Map<Integer, String> getProgramsByPTypes(ArrayList<Integer> pids) {
		ExamGenHandler c = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> programMap;
		try {

			programMap = c.getProgramsByPTypes(pids);
		} catch (Exception e) {
			programMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return programMap;
	}

	public Map<Integer, String> getSchemeNoByCourseId(int cid) {

		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler.getSchemeNoByCourse(Integer
				.toString(cid));
		return schemeMap;

	}

	// public Map<Integer, String> getSchemeValuesBySchemeId(int fromschemeId) {
	// ExamGenHandler handler = new ExamGenHandler();
	// Map<Integer, String> schemeMap
	// =handler.select_SchemeIdFromClassId(fromschemeId);
	// return schemeMap;
	// }

	@SuppressWarnings("unchecked")
	public HashMap<Integer, String> getProgramByAcademicYear(int academicYear) {
		ExamUniversityRegisterNumberEntryImpl c = new ExamUniversityRegisterNumberEntryImpl();
		ExamUniversityRegisterNumberEntryHelper h = new ExamUniversityRegisterNumberEntryHelper();

		List<ExamProgramUtilBO> listBO = new ArrayList<ExamProgramUtilBO>();
		try {

			listBO = new ArrayList(c
					.select_getProgramByAcademicYear(academicYear));

		} catch (Exception e) {
			new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return h.convertBOToTO_ExamUSN_entry_academic_year(listBO);
	}

	// ?ashwin
	public Map<Integer, String> getSchemeNoByCourse(int cid) {

		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler.getSchemeNoByCourse(Integer
				.toString(cid));
		return schemeMap;
	}

	public Map<Integer, String> getSubjectsByCourse(int cid, int sid,
			int schemeNo) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler.getSubjectByCourseMap(cid,
				sid, schemeNo);
		return schemeMap;
	}

	public Map<Integer, String> getSubjectsByCourse(int cid, int sid) {

		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler
				.getSubjectByCourseMap(cid, sid);
		return schemeMap;
	}

	public Map<Integer, String> getCourseByExamNameRegNoRollNo(int examId,
			String regNo, String rollNo) {
		Map<Integer, String> courseMap;
		try {
			ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
			courseMap = handler.getCourseByExamNameRegNoRollNo(examId, regNo,
					rollNo);
		} catch (Exception e) {
			courseMap = new HashMap<Integer, String>();
		}
		return courseMap;
	}

	public Map<Integer, String> getExamNameByExamType(String examType) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler
				.getExamNameByExamType(examType);
		return schemeMap;
	}

	// ajay
	public Map<Integer, String> getSchemeNoByCourseIdAcademicYear(int courseId,
			int year) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler.getSchemeNoByCourseId(Integer
				.toString(courseId), Integer.toString(year));
		return schemeMap;
	}

	public Map<String, String> getSchemeNo_SchemeIDByCourseIdAcademicId(
			int courseId, int year) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<String, String> schemeMap = handler
				.getSchemeNo_SchemeIDByCourseIdAcademicId(Integer
						.toString(courseId), Integer.toString(year));
		return schemeMap;
	}

	public Map<Integer, String> getClasesByAcademicYear(int academicYear) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler.getClassByYear(academicYear);
		return schemeMap;
	}

	public Map<Integer, String> getClassCodeByExamName(int examNameId) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = handler
				.getClassCodeByExamName(examNameId);
		return schemeMap;
	}

	public int getInternalComponentsByClasses(int examId) {

		ExamResultsHandler handler = new ExamResultsHandler();
		int id = handler.getInternalComponentsByClasses(examId);
		return id;
	}

	public Map<Integer, String> getSubjectsByCourseIdSchemeNo(int courseId,
			int schemeId, int shemeNo) {
		ExamAssignOverallMarksHandler handler = new ExamAssignOverallMarksHandler();
		Map<Integer, String> subjectsMap = handler
				.getSubjectsByCourseIdSchemeNo(courseId, schemeId, shemeNo);
		return subjectsMap;
	}

	public String getSubjectsTypeBySubjectId(int subjectId) {
		ExamAssignOverallMarksHandler handler = new ExamAssignOverallMarksHandler();
		String id = handler.getSubjectsTypeBySubjectId(subjectId);
		return id;
	}

	public Map<Integer, String> getTypeByAssignmentOverall(String type) {
		ExamAssignOverallMarksHandler handler = new ExamAssignOverallMarksHandler();
		Map<Integer, String> typeMap = handler.getTypeByAssignmentOverall(type);
		return typeMap;
	}

	public Map<String, String> getSchemeNoByExamIdCourseId(int examId,
			int courseId) {
		ExamAssignOverallMarksHandler handler = new ExamAssignOverallMarksHandler();
		Map<String, String> schemeMap = handler.getSchemeNoBy_ExamId_CourseId(
				examId, courseId);
		return schemeMap;
	}

	public Map<Integer, String> getExamNameByAcademicYear(String academicYear) {
		HashMap<Integer, String> examNames = new HashMap<Integer, String>();
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		if (academicYear != null && academicYear.length() > 0) {
			examNames = handler.getExamName_Internal(Integer
					.parseInt(academicYear));
		}

		return examNames;
	}

	// public Map<Integer, String> getRoomNoByExamName(int examNameId) {
	// Map<Integer, String> roomMap = new ExamAssignStudentsToRoomHandler()
	// .getRoomNoByExamName(examNameId);
	// return roomMap;
	// }

	public Map<Integer, String> getSectionByCourseIdSchemeId(String courseId,
			String schemeId, String schemeNo, String academicYear) {

		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		ArrayList<KeyValueTO> ktoList = null;
		try {
			ktoList = handler.getSectionByCourseIdSchemeId(courseId, schemeId,
					Integer.parseInt(schemeNo), Integer.parseInt(academicYear));

			Iterator<KeyValueTO> itr = ktoList.iterator();
			while (itr.hasNext()) {
				KeyValueTO to = (KeyValueTO) itr.next();
				schemeMap.put(to.getId(), to.getDisplay());
			}

		} catch (Exception e) {
			schemeMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return schemeMap;

	}

	public Map<Integer, String> getAgreementNameByClassId(
			ArrayList<Integer> classListIds1) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> mapAgree = handler
				.getAgreementByClassId(classListIds1);

		return mapAgree;

	}

	public Map<Integer, String> getFooterNameByClassId(
			ArrayList<Integer> classListIds) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> mapFooter = handler
				.getFooterListByClassId(classListIds);
		return mapFooter;
	}

	// ExamPublishHallTicket

	public Map<Integer, String> getAgreementNameByProgramTypeId(
			String programTypeID) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> mapAgree = handler
				.getAgreementByProgramTypeId(Integer.parseInt(programTypeID));

		return mapAgree;
	}

	// ExamPublishHallTicket
	public Map<Integer, String> getFooterNameByProgramTypeId(
			String programTypeID) {
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		Map<Integer, String> mapAgree = handler
				.getFooterListByProgramTypeId(Integer.parseInt(programTypeID));

		return mapAgree;
	}

	// Exam Definition
	public Map<Integer, String> getInternalExamByAcademicYear(
			String academicYear) {
		Map<Integer, String> mapAgree = new ExamDefinitionHandler()
				.getInternalExamListByAcademicYear(Integer
						.parseInt(academicYear));

		return mapAgree;
	}

	public String getDecryptRegNo(String regNo) {
		return new ExamSecuredMarksVerificationHandler().getDecryptRegNo(regNo);

	}

	// Exam Assign Students To Room
	public Map<Integer, String> getSubjectNameByClassIds(String classId,
			String date, String time, String examName) {
		Map<Integer, String> mapAgree = new ExamAssignStudentsToRoomHandler()
				.getSubjectNameByClassIds(classId, date, time, examName);

		return mapAgree;
	}

	// Exam Assign Students To Room
	public String getDateTimeByExamId(int examId) {
		return new ExamAssignStudentsToRoomHandler()
				.getDateTimeByExamId(examId);

	}

	public String getCurrentExam(int examtypeId) {
		return new ExamAssignStudentsToRoomHandler().getCurrentExam(examtypeId);

	}

	public int getMaxTheoryMarcks(BigDecimal marcks, int courseId, int schemeNo,
			int subjectId, String subjectType, int studentId,
			String assignmentOverall, String type) {
		return new ExamAssignOverallMarksHandler().getMaxTheoryMarks(marcks,
				courseId, schemeNo, subjectId, subjectType, studentId,
				assignmentOverall, type);

	}

	public int getMaxPracticalMarcks(int marcks, int courseId, int schemeNo,
			int subjectId, String subjectType, String assignmentOverall) {
		return 0;

	}

	public String getExamDateTimeByExamId(int examId) {
		return new ExamAssignExaminerDutiesHandler()
				.getDateTimeByExamId(examId);
	}

	@Override
	public ArrayList<KeyValueTO> getSubjectCodeName(String sCodeName, int examId) {
		// TODO Auto-generated method stub
		return new ExamSecuredMarksEntryHandler().getSubjectCodeName(sCodeName, examId);
	}

	@Override
	public Map<Integer, String> getSubjectsByCourseSchemeExamId(int courseId,
			int schemeId, int schemeNo, Integer examId) {
		ExamGenHandler handler = new ExamGenHandler();
		return handler.getSubjectsByCourseSchemeExamId(courseId, schemeId,
				schemeNo, examId);
	}
	public Map<Integer, String> getSubjectsCodeNameByCourseSchemeExamId(String sCodeName,int courseId,
			int schemeId, int schemeNo, Integer examId,Integer academicYear) {
		ExamGenHandler handler = new ExamGenHandler();
		return handler.getSubjectsCodeNameByCourseSchemeExamId(sCodeName,courseId, schemeId,
				schemeNo, examId,academicYear);
	}

	public Map<Integer, String> getSubjectsByCourseSchemeExamIdJBY(
			int courseId, int schemeId, int schemeNo, Integer examId,
			Integer jby) {
		ExamGenHandler handler = new ExamGenHandler();
		return handler.getSubjectsByCourseSchemeExamIdJBY(courseId, schemeId,
				schemeNo, examId, jby);
	}

	@Override
	public ArrayList<KeyValueTO> getCoursesByProgramTypes1(String pids) {
		ExamGenHandler c = CommonAjaxExamImpl.getHandler();
		ArrayList<KeyValueTO> courseMap;
		try {
			courseMap = c.getCoursesByProgramTypes1(pids);
		} catch (Exception e) {
			courseMap = new ArrayList<KeyValueTO>();
			log.debug("Exception" + e.getMessage());
		}
		return courseMap;
	}
	
	/**
	 * 
	 * @param courseId
	 * @param academicYear
	 * @return
	 */
	public List<CurriculumScheme> getSchemeByAcademicYear(int courseId,int academicYear)
	{
		Session session = null;
		List<CurriculumScheme> objList = null;
		try {
			session = HibernateUtil.getSession();
			String sql = " select cur from com.kp.cms.bo.exam.CurriculumSchemeUtilBO cur "+
							"  inner join cur.curriculumSchemeDurationUtilBOSet dur " +
							" where cur.courseId = " + courseId +
							" and dur.academicYear = " + academicYear;
			 
			
			Query queri = session.createQuery(sql);
			objList = queri.list();
		} catch (Exception e) {
			log.error("Error in getClassSchemeForStudent..." + e);
			
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return objList;
	}
	
	public Map<Integer, String> getProgramsByExamName(String examName) 
	{
		Map<Integer, String> programMap=new HashMap<Integer, String>();
		Session session = null;
		try 
		{
			session=HibernateUtil.getSession();
			String query="select e.examProgramUtilBO from ExamDefinitionProgramBO e  where e.examDefinitionBO.id="+Integer.parseInt(examName);
			List<ExamProgramUtilBO> programList=session.createQuery(query).list();
			if(programList.size()!=0)
			{
				for(ExamProgramUtilBO utilBO:programList)
				{
					programMap.put(utilBO.getProgramID(),utilBO.getProgramName());
				}
			}	
			
			programMap=CommonUtil.sortMapByValue(programMap);
		} catch (Exception e) {
			programMap = new HashMap<Integer, String>();
			log.debug("Exception" + e.getMessage());
		}
		return programMap;
	}
	
	public Map<Integer, String> getSubjectsByProgram(Integer programId)
	{
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select s.subject from SubjectGroupSubjects s where s.subjectGroup.course.program.id="+programId+" and s.subject.isActive=1" );
			 List<Subject> subjectList = query.list();
			Map<Integer, String> courseMap = new HashMap<Integer, String>();
			Iterator<Subject> itr = subjectList.iterator();
			Subject subject;
			while (itr.hasNext()) {
				subject = (Subject) itr.next();
					courseMap.put(subject.getId(), subject.getName());
			}
			return courseMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}

	public Integer getAcademicYear(int examId) throws Exception {
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();

			String HQL_QUERY = "select  e.academicYear from ExamDefinitionBO e"
					+ " where e.id = :examId" ;
					

			Query query = session.createQuery(HQL_QUERY);
			query.setParameter("examId", examId);

			List<Integer> list = query.list();

			int academicYear = 0;
			if (list != null && list.size() > 0) {

				Iterator<Integer> itr = list.iterator();
				while (itr.hasNext()) {
					academicYear = (Integer) itr.next();
				}
			}
			if (session != null) {
				session.flush();
				session.close();
			}
			return academicYear;
		} catch (Exception e) {
			log.error("Error in getAcademicYear" + e);
			throw new Exception();
		}

	}
	
	public List<Object> getSubjectFromRevaluationOrRetotaling(String sCodeName, int examId){
	
		Session session = null;
		List<Object>  List=null;
		try {
			session = HibernateUtil.getSession();
			String sql =" select ed.subject.id,ed.subject.code,ed.subject.name "+
							" from ExamRevaluationAppDetails ed "+
							" where ed.examRevApp.exam.id="+examId+" group by ed.subject.id"; 
			 
			if (sCodeName.equalsIgnoreCase("sCode")) {
				//  " order by sub.code";
				sql = sql + " order by ed.subject.code";

			} else {
				//  " order by sub.name";
				sql = sql + " order by ed.subject.name";
			}
			Query query = session.createQuery(sql);
			  List = query.list();
			
		} catch (Exception e) {
			log.error("Error in getClassSchemeForStudent..." + e);
			
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return List;
	}
	public Map<Integer, String> getExamNameByAcademicYearAndExamType(String academicYear, String examType) {
		HashMap<Integer, String> examNames = new HashMap<Integer, String>();
		ExamGenHandler handler = CommonAjaxExamImpl.getHandler();
		if (academicYear != null && academicYear.length() > 0) {
			examNames = handler.getExamNameByAcademicYearAndExamType(Integer
					.parseInt(academicYear),examType);
		}

		return examNames;
	}
	public Map<Integer, String> getClassNameByExamNameForSupplementary(
			int examId) {
		Session session = null;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		try{
			String SQL_QUERY = "SELECT classes.id, classes.name,left(curriculum_scheme_duration.academic_year,4) FROM  classes classes"
			+ " INNER JOIN EXAM_exam_course_scheme_details EXAM_exam_course_scheme_details"
			+ " ON classes.course_id = EXAM_exam_course_scheme_details.course_id"
			+ " AND classes.term_number = EXAM_exam_course_scheme_details.scheme_no"
			+ " INNER JOIN class_schemewise class_schemewise ON class_schemewise.class_id = classes.id"
			+ " INNER JOIN curriculum_scheme_duration curriculum_scheme_duration"
			+ " ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id"
			+ " INNER JOIN EXAM_definition EXAM_definition ON curriculum_scheme_duration.academic_year >= EXAM_definition.exam_for_joining_batch"
			+ " AND EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id where EXAM_definition.id=:examId" +
					" and classes.id in(select app.class_id from EXAM_supplementary_improvement_application app where app.exam_id=:examId group by app.class_id) " +
            " group by classes.id";
			
			Query query = session.createSQLQuery(SQL_QUERY);
			query.setParameter("examId", examId);
			List<Object[]> objList = new ArrayList<Object[]>();
			objList = (List<Object[]>) query.list();
			for (Object[] objects : objList) {
				classMap.put(Integer.valueOf(objects[0].toString()),
						objects[1].toString().concat("("+objects[2].toString()+")"));
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			log.debug("Exception" + e.getMessage());
		}
		return classMap;
	}
	public Map<String, String> getSchemeNoByExamId(int examId) {
		ExamAssignOverallMarksHandler handler = new ExamAssignOverallMarksHandler();
		Map<String, String> schemeMap = handler.getSchemeNoBy_ExamId(examId);
		return schemeMap;
	}
	
	public ArrayList<ProgrammeEntryBo> getProgramesByDepartmentId(
			String deptId) {

		Session session = null;
		ArrayList<ProgrammeEntryBo> list;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			String date = CommonUtil.getTodayDate();
			Date curDate = CommonUtil.ConvertStringToSQLDate(date);
			Query query = (Query) session
					.createQuery("select e from ProgrammeEntryBo e where sysdate() between e.startDate and e.endDate and e.id="+Integer.parseInt(deptId));

			list = new ArrayList<ProgrammeEntryBo>(query.list());
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ProgrammeEntryBo>();
		}
		return list;
	}

	@Override
	public Map<Integer, String> getCourseByTeacher(String teacherId, String year) {
		Session session = null;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		try{
			String SQL_QUERY = "select distinct course.id,course.name from teacher_class_subject" +
					" inner join class_schemewise ON teacher_class_subject.class_schemewise_id = class_schemewise.id" +
					" inner join classes ON class_schemewise.class_id = classes.id" +
					" inner join course ON classes.course_id = course.id" +
					" inner join EXAM_exam_course_scheme_details on EXAM_exam_course_scheme_details.course_id = course.id and EXAM_exam_course_scheme_details.scheme_no=classes.term_number" +
					" where teacher_class_subject.teacher_id="+teacherId+
					" and teacher_class_subject.year="+year;
			
			Query query = session.createSQLQuery(SQL_QUERY);
			List<Object[]> objList = new ArrayList<Object[]>();
			objList = (List<Object[]>) query.list();
			for (Object[] objects : objList) {
				courseMap.put(Integer.valueOf(objects[0].toString()),
						objects[1].toString());
			}
			
		}catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return courseMap;
	}

	@Override
	public Map<String, String> getSchemeNoByCourseIdByTeacher(int courseId, String teacherId, int year) {
		Session session = null;
		Map<String, String> schemeMap = new HashMap<String, String>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		try{
			String SQL_QUERY = "select course_scheme.id,classes.term_number,course_scheme.name from teacher_class_subject" +
					" inner join class_schemewise ON teacher_class_subject.class_schemewise_id = class_schemewise.id" +
					" inner join classes ON class_schemewise.class_id = classes.id" +
					" inner join course ON classes.course_id = course.id" +
					" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
					" inner join curriculum_scheme ON curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
					" inner join course_scheme ON curriculum_scheme.course_scheme_id = course_scheme.id" +
					" inner join EXAM_exam_course_scheme_details on EXAM_exam_course_scheme_details.course_id = course.id and EXAM_exam_course_scheme_details.scheme_no=classes.term_number" +
					" where teacher_class_subject.teacher_id=:teacherId" +
					" and course.id=:courseId" +
					" and teacher_class_subject.year=:year"+
					" group by classes.term_number";
			
			Query query = session.createSQLQuery(SQL_QUERY);
			query.setString("teacherId", teacherId);
			query.setInteger("courseId", courseId);
			query.setInteger("year", year);
			List<Object[]> objList = new ArrayList<Object[]>();
			objList = (List<Object[]>) query.list();
			for (Object[] objects : objList) {
				schemeMap.put((objects[0].toString())+"_"+objects[1].toString(),
						objects[1].toString()+"-"+objects[2].toString());
			}
			
		}catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return schemeMap;
	}

	@Override
	public Map<Integer, String> getSubjectsCodeNameByCourseSchemeIdByTeacher(String sCodeName,int courseId,
			int schemeId, int schemeNo,String teacherId) {
		ExamGenHandler handler = new ExamGenHandler();
		return handler.getSubjectsCodeNameByCourseSchemeIdByTeacher(sCodeName,courseId, schemeId,
				schemeNo, teacherId);
	}
	
	
}
