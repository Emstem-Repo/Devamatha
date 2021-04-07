package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kp.cms.bo.admin.ExamSecondLanguage;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.CourseSchemeUtilBO;
import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamCourseGroupCodeBO;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamInvigilationDutyBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.ExamSecondLanguageMasterBO;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.bo.exam.SubjectGroupUtilBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.helpers.exam.ExamGenHelper;
import com.kp.cms.to.exam.ExamClassTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamSubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.transactionsimpl.exam.ExamPublishHallTicketImpl;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class ExamGenHandler {
	/**
	 * Singleton object of ExamGenHandler
	 */
	private static volatile ExamGenHandler examGenHandler = null;
	private static final Log log = LogFactory.getLog(ExamGenHandler.class);
	/**
	 * return singleton object of ExamGenHandler.
	 * @return
	 */
	public static ExamGenHandler getInstance() {
		if (examGenHandler == null) {
			examGenHandler = new ExamGenHandler();
		}
		return examGenHandler;
	}
	ExamGenHelper helper = new ExamGenHelper();

	ExamGenImpl impl = new ExamGenImpl();
	ExamPublishHallTicketImpl transaction = new ExamPublishHallTicketImpl();

	/*
	 * return a list of (ExamCourseUtilBO)
	 */
	public ArrayList<ExamCourseUtilBO> getPTypeProgramCourseList() {
		return impl.select_ActiveOnly_CourseUtil();
	}

	/*
	 * return a list of (KeyValue)
	 */
	public ArrayList<KeyValueTO> getPTypeProgramCourseList_KeyValue() {
		return helper.convertBOToTO_Course_KeyVal(impl
				.select_ActiveOnly_CourseUtil());
	}

	public Map<Integer, String> getProgramsByPType(int programTypeId) {
		return helper.convertBOToTO_program_map(impl
				.select_ActiveOnly_Program(programTypeId));

	}

	// To get the course group code list
	public ArrayList<KeyValueTO> getCourseGroupCodeList() {
		List<ExamCourseGroupCodeBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamCourseGroupCodeBO.class));
		return helper.convertBOToTO_CourseGrp_List(listBO);

	}

	/*
	 * input - programTypeid return a HashMap of (courseID and Program-Course)
	 */
	public HashMap<Integer, String> getProgramCourse(int programTypeId) {
		HashMap<Integer, String> mapCourse = new HashMap<Integer, String>();
		ArrayList<ExamCourseUtilBO> listECUBO = impl
				.select_ActiveOnly_CourseUtil();

		// newListECUBO has all ExamCourseUtilBO for a given programTypeId
		ArrayList<ExamCourseUtilBO> newListECUBO = new ArrayList<ExamCourseUtilBO>();

		for (ExamCourseUtilBO examCourseUtilBO : listECUBO) {

			if (examCourseUtilBO.getProgram().getProgramType().getId() == programTypeId) {
				newListECUBO.add(examCourseUtilBO);
			}
		}
		for (ExamCourseUtilBO examCourseUtilBO : newListECUBO) {
			mapCourse.put(examCourseUtilBO.getCourseID(), examCourseUtilBO
					.getProgramCourse());
		}
		return mapCourse;
	}

	// public ArrayList<KeyValueTO> getProgramCourse(int programTypeId) {
	// ArrayList<KeyValueTO> mapCourse = new ArrayList<KeyValueTO>();
	// ArrayList<ExamCourseUtilBO> listECUBO = impl
	// .select_ActiveOnly_CourseUtil();
	// Iterator itr = listECUBO.iterator();
	// // newListECUBO has all ExamCourseUtilBO for a given programTypeId
	// ArrayList<ExamCourseUtilBO> newListECUBO = new
	// ArrayList<ExamCourseUtilBO>();
	//
	// for (ExamCourseUtilBO examCourseUtilBO : listECUBO) {
	//
	// if (examCourseUtilBO.getProgram().getProgramType().getId() ==
	// programTypeId) {
	// newListECUBO.add(examCourseUtilBO);
	// while (itr.hasNext()) {
	// Object row[] = (Object[]) itr.next();
	//
	// mapCourse.add(new KeyValueTO(
	// examCourseUtilBO.getCourseID(), examCourseUtilBO
	// .getProgramCourse()));
	//
	//					
	// }
	// return mapCourse;
	// }
	// }
	// return mapCourse;
	//
	// }

	public Map<Integer, String> getCoursesByProgramTypes(String pids) {

		HashMap<Integer, String> mapCourse = new HashMap<Integer, String>();

		ArrayList<ExamCourseUtilBO> listECUBO = impl
				.select_ActiveOnly_CourseUtil();
		String[] ptIds = pids.split(",");

		ArrayList<Integer> listPid = new ArrayList<Integer>();
		for (int i = 0; i < ptIds.length; i++) {
			listPid.add(Integer.parseInt(ptIds[i]));

		}

		// newListECUBO has all ExamCourseUtilBO for a given programTypeId
		ArrayList<ExamCourseUtilBO> newListECUBO = new ArrayList<ExamCourseUtilBO>();

		for (ExamCourseUtilBO examCourseUtilBO : listECUBO) {

			if (listPid.contains(examCourseUtilBO.getProgram().getProgramType()
					.getId())) {
				// listPid.remove(examCourseUtilBO.getProgram().getProgramType()
				// .getId());
				newListECUBO.add(examCourseUtilBO);
			}
		}

		for (ExamCourseUtilBO examCourseUtilBO : newListECUBO) {

			mapCourse.put(examCourseUtilBO.getCourseID(), examCourseUtilBO
					.getProgramCourse());
		}
		return mapCourse;
	}

	// To get the scheme for the selected course
	public HashMap<Integer, String> getSchemeByCourse(String courseId) {
		List<CurriculumSchemeUtilBO> listBO = new ArrayList(impl
				.select_Scheme_By_Course(Integer.parseInt(courseId)));
		return helper.convertBOToTO_Scheme(listBO);

	}// To get the subject group hash map

	public HashMap<Integer, String> getSubjectGroupMap() {
		List<SubjectGroupUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(SubjectGroupUtilBO.class));
		return helper.convertBOToTO_SubGrp_Map(listBO);

	}

	// To get the subject group list
	public ArrayList<KeyValueTO> getSubjectGroupList() {
		List<SubjectGroupUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(SubjectGroupUtilBO.class));
		return helper.convertBOToTO_SubGrp_List(listBO);

	}

	// Get scheme name for the particular schemeId
	// exam Marks Entry & exam Student Marks Correction
	public String getSchemeName(int schemeId) {
		CourseSchemeUtilBO bo = (CourseSchemeUtilBO) impl.select_Unique(
				schemeId, CourseSchemeUtilBO.class);
		if (bo != null)
			return bo.getName();
		return "";
	}

	public String getExamTypeByExamTypeId(int examTypeId) {
		return ((ExamTypeUtilBO) impl.select_Unique(examTypeId,
				ExamTypeUtilBO.class)).getName();
	}

	public String getInvigilatorName(int InvId) {
		return ((ExamInvigilationDutyBO) impl.select_Unique(InvId,
				ExamInvigilationDutyBO.class)).getName();
	}

	// To get exam name list
	public ArrayList<KeyValueTO> getExamNameList() throws Exception {
		ArrayList<ExamDefinitionBO> lBO = new ArrayList(impl
				.select_ActiveOnly_ExamName());
		return helper.convertBOToTO_ExamName(lBO);
	}

	// ***************************** Course **************************

	// Get course name for the particular courseId
	public String getCourseName(int courseId) {

		Object object = impl.select_Unique(courseId, ExamCourseUtilBO.class);
		return object == null ? "" : ((ExamCourseUtilBO) object)
				.getCourseName();

	}

	// Get course name for the particular courseId
	public String getSubjectName(int subjectId) {
		return ((SubjectUtilBO) impl.select_Unique(subjectId,
				SubjectUtilBO.class)).getName();
	}

	public ArrayList<ExamCourseUtilTO> getListExamCourse(
			ArrayList<Integer> listCourses) {
		return helper.convertBOtoTO_course(impl.select_course(listCourses));
	}

	public List<ExamCourseUtilTO> getListExamCourse(int courseId) {
		ArrayList<Integer> listcourseId = new ArrayList<Integer>();
		listcourseId.add(courseId);
		return getListExamCourse(listcourseId);
	}

	// To get the course list
	public ArrayList<KeyValueTO> getCourseList() {
		ArrayList<ExamCourseUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamCourseUtilBO.class));
		return helper.convertBOToTO_Course_KeyVal(listBO);
	}

	public ArrayList<KeyValueTO> getCourseListNotAppln() {
		ArrayList<ExamCourseUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnlyForCourse(ExamCourseUtilBO.class));
		return helper.convertBOToTO_Course_KeyVal(listBO);
	}
	public List<ExamCourseUtilTO> getListExamCourseUtil() throws Exception {
		return helper.convertBOtoTO_course(impl.select_ActiveOnly_CourseUtil());
	}

	public HashMap<Integer, String> getCourseListHashMap() {
		ArrayList<ExamCourseUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamCourseUtilBO.class));
		return helper.convertBOToTO_Course_HashMap(listBO);
	}

	/*
	 * return a map of (courseID and ProgramType-Propgram-Course)
	 */
	public HashMap<Integer, String> getPTypeProgramCourseMap() {

		HashMap<Integer, String> mapCourse = new HashMap<Integer, String>();
		ArrayList<ExamCourseUtilBO> listCImpl = impl
				.select_ActiveOnly_CourseUtil();
		for (ExamCourseUtilBO examCourseUtilBO : listCImpl) {
			mapCourse.put(examCourseUtilBO.getCourseID(), examCourseUtilBO
					.getPTypeProgramCourse());
		}
		return mapCourse;
	}

	public String getExamNameList(int examId) {
		Object object = impl.select_Unique(examId, ExamDefinitionBO.class);
		return (object == null ? "" : ((ExamDefinitionBO) object).getName());
	}

	// To get the Exam Name for a particular examId
	public String getExamNameByExamId(int examId) {

		Object object = impl.select_Unique(examId, ExamDefinitionBO.class);
		return (object == null ? "" : ((ExamDefinitionBO) object).getName());

	}

	public ArrayList<KeyValueTO> getSpecializationMaster_courseId(int courseId) {
		return helper.convertBOToTO_specializationMaster(impl
				.select_studentSpecializationCourseId(courseId));
	}

	public ArrayList<KeyValueTO> getSectionByCourseIdSchemeId(String coursId,
			String schemeId, Integer schemeNo, Integer academicYear) {
		ArrayList<ClassUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly_SectionUtil(Integer.parseInt(coursId),
						Integer.parseInt(schemeId), schemeNo, academicYear));
		return helper.convertBOToTOgetSection(listBO);
	}

	public String getSpecializationName(int id) {
		return ((ExamSpecializationBO) impl.select_Unique(id,
				ExamSpecializationBO.class)).getName();
	}

	// To get the SchemeNo for a particular course
	public HashMap<Integer, String> getSchemeNoByCourseId(int courseId, int year) {
		ArrayList<CurriculumSchemeUtilBO> listBO = new ArrayList(impl
				.select_Scheme(courseId, year));

		return helper.convertBOToTO_SchemeNo(listBO);

	}

	public Map<Integer, String> select_SchemeIdFromClassId(String fromSchemeId,
			String toSchemeId) {
		ArrayList<CurriculumSchemeUtilBO> listBOFrom = new ArrayList(impl
				.select_SchemeIdFromClassId(Integer.parseInt(fromSchemeId)));
		ArrayList<CurriculumSchemeUtilBO> listBOTo = new ArrayList(impl
				.select_SchemeIdFromClassId(Integer.parseInt(toSchemeId)));

		return helper.convertBOToTO_SchemeNoToClassId(listBOFrom, listBOTo);
	}

	public Map<Integer, String> getProgramsByPTypes(ArrayList<Integer> pids) {
		return helper.convertBOToTO_program_map(impl
				.select_ActiveOnly_Program_byPTypes(pids));
	}

	public HashMap<Integer, String> getSchemeNoByCourse(String CourseId) {

		return helper.convertBOToTO_SchemeNo_usingMaxValue(impl
				.select_Scheme_By_Course(Integer.parseInt(CourseId)));

	}

	public HashMap<Integer, String> getSchemeNoByCourseId(String CourseId,
			String acdemicYear) {
		ArrayList<CurriculumSchemeUtilBO> listBO = new ArrayList(impl
				.select_Scheme(Integer.parseInt(CourseId), Integer
						.parseInt(acdemicYear)));
		return helper.convertBOToTO_Scheme(listBO);

	}

	public Map<String, String> getSchemeNo_SchemeIDByCourseIdAcademicId(
			String CourseId, String acdemicYear) {
		ArrayList<CurriculumSchemeUtilBO> listBO = new ArrayList(impl
				.select_Scheme(Integer.parseInt(CourseId), Integer
						.parseInt(acdemicYear)));
		return helper.convertBOToTO_course_SchemeId_SchemeNo(listBO);

	}

	// To get Specialization name for a particular specializationId

	// To get Exam Type Hash Map
	public HashMap<Integer, String> getExamType() {
		ArrayList<ExamTypeUtilBO> listBO = new ArrayList(impl
				.select_All(ExamTypeUtilBO.class));
		return helper.convertBOToTO_ExamType_HashMap(listBO);
	}

	public boolean validate_Stu_rollReg(String rollNo, String regNo) {
		if (impl.select_student(rollNo, regNo) == null) {
			return false;
		}
		return true;
	}

	public StudentUtilBO get_Stu_rollReg(String rollNo, String regNo) {
		return impl.select_student(rollNo, regNo);
	}

	public int get_Student_id(String rollNo, String regNo) {
		StudentUtilBO bo = impl.select_student(rollNo, regNo);
		if (bo != null) {
			return bo.getId();
		}
		return 0;
	}

	public ArrayList<Integer> getSubjectIdList(int courseId, String rollNo,
			String regNo, boolean useRoll, int schemeNo, int studentId, boolean isPreviousExam) {
		return impl
		.select_subjectId(courseId, rollNo, regNo, useRoll, schemeNo, studentId, isPreviousExam);
	}

	public String getProgramNameByProgramId(int programId) {
		return ((ExamProgramUtilBO) impl.select_Unique(programId,
				ExamProgramUtilBO.class)).getProgramName();
	}

	public String getProgramNameByProgramTypeId(int programTypeId) {

		Object object = impl.select_Unique(programTypeId,
				ExamProgramTypeUtilBO.class);
		return (object == null ? "" : ((ExamProgramTypeUtilBO) object)
				.getProgramType());

	}

	public ArrayList<KeyValueTO> getExamByExamType(String examTypeName) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		ArrayList<KeyValueTO> listTO;
		listTO = new ArrayList<KeyValueTO>();
		String SQL_QUERY = null;
		if (examTypeName.contains("Reg")) {
			SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Reg%' and e.delIsActive = 1 and e.isActive = 1 ";
		} else if (examTypeName.contains("Suppl")) {
			SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Suppl%' and e.delIsActive = 1 and e.isActive = 1 ";
		} else if (examTypeName.contains("Int")) {
			SQL_QUERY = " from ExamDefinitionBO e where e.examTypeUtilBO.name  like '%Int%' and e.delIsActive = 1 and e.isActive = 1 ";
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

	public ArrayList<ExamSubjectTO> getSubjectByCourse(int courseId,
			int schemeId, int schemeNo) {
		return helper.convertBOToTO_Subjects(impl.getSubjectByCourse(courseId,
				schemeId, schemeNo));
	}

	public ArrayList<ExamSubjectTO> getSubjectByCourse(int courseId,
			int schemeId) {
		return helper.convertBOToTO_Subjects(impl.getSubjectByCourse(courseId,
				schemeId));
	}

	public Map<Integer, String> getSubjectByCourseMap(int courseId,
			int schemeId, int schemeNo) {
		return helper.convertTOToMap_Subjects(getSubjectByCourse(courseId,
				schemeId, schemeNo));
	}

	public Map<Integer, String> getSubjectByCourseMap(int courseId, int schemeId) {
		return helper.convertTOToMap_Subjects(getSubjectByCourse(courseId,
				schemeId));
	}

	public Map<Integer, String> getExamNameByExamType(String examType) {
		return helper.convertTOToMap_ExamName(getExamByExamType(examType));
	}

	public Map<Integer, String> getCourseByExamNameRegNoRollNo(int examId,
			String regNo, String rollNo) throws BusinessException {
		HashMap<Integer, String> map = null;

		boolean oldRegPresent = false;
		boolean oldRollPresent = false;

		if (rollNo != null && rollNo.length() > 0) {
			oldRollPresent = true;

		}
		if (regNo != null && regNo.length() > 0) {
			oldRegPresent = true;

		}

		if (oldRegPresent && oldRollPresent) {
			if (!validate_Stu_rollReg(rollNo, regNo)) {
				throw new BusinessException();
			}
		}
		// Register No /roll not present
		if (!oldRegPresent && !oldRollPresent) {
			ExamUpdateExcludeWithheldHandler h = new ExamUpdateExcludeWithheldHandler();
			map = h.getCourse(Integer.toString(examId));

		} else {
			if (oldRegPresent) {
				map = (HashMap<Integer, String>) helper
						.convertTOToMapCourse(impl
								.getCourseByExamNameRegNoOnly(examId, regNo));
			} else {
				map = (HashMap<Integer, String>) helper
						.convertTOToMapCourse(impl
								.getCourseByExamNameRollNoOnly(examId, rollNo));
			}
		}

		return map;
	}

	// ExamTimeTable
	public HashMap<Integer, String> getExamNameByExamTypeId(
			ArrayList<KeyValueTO> examNames) {
		HashMap<Integer, String> examNamesList = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : examNames) {
			examNamesList.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		return examNamesList;
	}

	public Map<Integer, String> getClassCodeByExamName(Integer examId) {
		ArrayList<ExamClassTO> list = helper.convertTOTO(impl
				.getCourseIdSchemeNo(examId));
		List<KeyValueTO> listValues = new ArrayList();
		for (ExamClassTO examClassTO : list) {
			impl.getClassValuesByCourseId(examClassTO.getCourseId(),
					examClassTO.getCourseSchemeId(), examClassTO.getSchemeNo(),
					listValues, examId);

		}
		return helper.convertTOClassMap(listValues);

	}

	// To get the Class List
	public HashMap<Integer, String> getClassList() {
		List<ClassUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ClassUtilBO.class));
		return helper.convertBOToTO_Class(listBO);
	}

	// To get Assignment Type List
	public HashMap<Integer, String> getAssignmentTypeList() {
		List<ExamAssignmentTypeMasterBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamAssignmentTypeMasterBO.class));
		return helper.convertBOToTO_AssignmentType(listBO);
	}

	public String getAssignmentTypeById(int assignmentTypeId) {
		return ((ExamAssignmentTypeMasterBO) impl.select_Unique(
				assignmentTypeId, ExamAssignmentTypeMasterBO.class)).getName();

	}

	public HashMap<Integer, String> getExamName_Internal(int academicYear) {
		return helper.convertBOtoTO_ExamName_Internal(impl
				.select_ExamName_Internal(academicYear));
	}

	public HashMap<Integer, String> getClassByYear(int academicYear) {
		return impl.select_Classes(academicYear);
	}

	public Map<Integer, String> getAgreementByClassId(
			ArrayList<Integer> classListIds) {
		ArrayList<ExamFooterAgreementBO> lBO = new ArrayList<ExamFooterAgreementBO>(
				impl.select_agreementList(classListIds));
		return helper.convertBOToTO_AgreementList(lBO);
	}

	public HashMap<Integer, String> getFooterListByClassId(ArrayList classIdList) {
		ArrayList<ExamFooterAgreementBO> lBO = new ArrayList<ExamFooterAgreementBO>(
				impl.select_footertList(classIdList));
		return helper.convertBOToTO_FooterList(lBO);
	}

	public HashMap<Integer, String> getFooterListByProgramTypeId(
			int programTypeID) {
		ArrayList<ExamFooterAgreementBO> lBO = new ArrayList<ExamFooterAgreementBO>(
				impl.select_footertListByProgramTypeId(programTypeID));
		return helper.convertBOToTO_FooterList(lBO);
	}

	public Map<Integer, String> getAgreementByProgramTypeId(int programTypeID) {
		ArrayList<ExamFooterAgreementBO> lBO = new ArrayList<ExamFooterAgreementBO>(
				impl.select_agreementList(programTypeID));
		return helper.convertBOToTO_AgreementList(lBO);
	}

	public HashMap<Integer, String> getSecondLanguage() {
		ArrayList<ExamSecondLanguage> listBO = new ArrayList(impl
				.getSecLanguage());
		return helper.convertBOToTO_SecondLanguageNew(listBO);
	}

	public HashMap<String, String> getInternalExamType() {
		List<ExamInternalExamTypeBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamInternalExamTypeBO.class));
		return helper.convertBOToTO_getInternalExamType(listBO);
	}

	// To FETCH Exam Names Based On Process Type (UPDATE PROCESS)
	public HashMap<Integer, String> getExamNameByProcessType(String processType,String year) {

		return helper.converBOToTO_examNameByProcessType(impl
				.getExamNameByProcessType(processType,year));
	}

	// Bar Code Checker

	public String getDecryptRegNo(String encryptedRegNo) {

		try {
			return ExamRegDecrypt.decrypt(encryptedRegNo);
		} catch (Exception e) {
			return "";
		}
	}

	// To get subjectType by subjectID
	public String getSubjectsTypeBySubjectId(int subjectId) {
		return impl.selectSubjectsTypeBySubjectId(subjectId);
	}

	public Map<Integer, String> getSubjectsByCourseSchemeExamId(int courseId,
			int schemeId, int schemeNo, Integer examId) {
		return helper.convertTOToMap_Subjects(helper
				.convertBOToTO_Subjects(impl.getSubjectsByCourseSchemeExamId(
						courseId, schemeId, schemeNo, examId)));
	}

	public Map<Integer, String> getSubjectsByCourseSchemeExamIdJBY(
			int courseId, int schemeId, int schemeNo, Integer examId,
			Integer jby) {
		return helper.convertTOToMap_Subjects(helper
				.convertBOToTO_Subjects(impl
						.getSubjectsByCourseSchemeExamIdJBY(courseId, schemeId,
								schemeNo, examId, jby)));
	}

	public ArrayList<KeyValueTO> getCoursesByProgramTypes1(String pids) {

		ArrayList<KeyValueTO> mapCourse = new ArrayList<KeyValueTO>();

		ArrayList<ExamCourseUtilBO> listECUBO = impl
				.select_ActiveOnly_CourseUtil();
		String[] ptIds = pids.split(",");

		ArrayList<Integer> listPid = new ArrayList<Integer>();
		for (int i = 0; i < ptIds.length; i++) {
			listPid.add(Integer.parseInt(ptIds[i]));

		}

		// newListECUBO has all ExamCourseUtilBO for a given programTypeId
		ArrayList<ExamCourseUtilBO> newListECUBO = new ArrayList<ExamCourseUtilBO>();

		for (ExamCourseUtilBO examCourseUtilBO : listECUBO) {

			if (listPid.contains(examCourseUtilBO.getProgram().getProgramType()
					.getId())) {
				// listPid.remove(examCourseUtilBO.getProgram().getProgramType()
				// .getId());
				newListECUBO.add(examCourseUtilBO);
			}
		}

		for (ExamCourseUtilBO examCourseUtilBO : newListECUBO) {

			mapCourse.add(new KeyValueTO(examCourseUtilBO.getCourseID(),
					examCourseUtilBO.getProgramCourse()));
		}
		 return mapCourse;
	}
	
	// To get only Regular Exam Type Hash Map
	public HashMap<Integer, String> getRegularExamType() {
		ArrayList<ExamTypeUtilBO> listBO = new ArrayList(impl
				.select_All_regular());
		return helper.convertBOToTO_ExamType_HashMap(listBO);
	}
	
	public Map<Integer, String> getSubjectsCodeNameByCourseSchemeExamId(String sCodeName,int courseId,
			int schemeId, int schemeNo, Integer examId,Integer academicYear) {
		return helper.convertTOToMap_SubjectsCodeName(helper
				.convertBOToTO_SubjectsCodeName(impl.getSubjectsCodeNameByCourseSchemeExamId(
						sCodeName,courseId, schemeId, schemeNo, examId,academicYear),sCodeName));
	}

	/**added by mahi 
	 * @param examId
	 * @return
	 * @throws Exception 
	 */
	public Map<Integer, String> getClassesForExam(int examId,String examType) throws Exception {
		Map<Integer, String> map =  impl.getClassesForExam(examId,examType);
		return map;
	}
	
	public Map<Integer, String> getclassesMap(String examName, String examType,
			String programId, String deanaryName) throws Exception{
		List<KeyValueTO> listValues = new ArrayList();
		/*ArrayList<ExamClassTO> list = helper.convertTOTO(transaction
				.getCourseIdSchemeNoByProgramType(examName,programId,deanaryName));
		for (ExamClassTO examClassTO : list) {
			transaction.getClassValuesByCourseId(examClassTO.getCourseId(),
					examClassTO.getCourseSchemeId(), examClassTO.getSchemeNo(),
					listValues, Integer.valueOf(examName));

		}*/
		//start by giri
		listValues=transaction.getClassValuesByCourseIdNew(examType,examName,programId,deanaryName,listValues);
		//end by giri
		// if examtype is supplementary
		if(examType!=null && !examType.isEmpty() && (Integer.parseInt(examType)==6 || Integer.parseInt(examType)==3)){
			List<Integer> classIds=transaction.getclassIds(Integer.parseInt(examName));
			return helper.convertTOClassMapByclassIds(listValues,classIds);
		}
		return helper.convertTOClassMap(listValues);

	}

	public Map<Integer, String> getClassesMapByExam(String examName,String examType, String programId) throws Exception {
		List<KeyValueTO> listValues = new ArrayList();
		listValues=transaction.getClassValuesByExam(examType,examName,programId,listValues);
		if(examType!=null && !examType.isEmpty() && (Integer.parseInt(examType)==6 || Integer.parseInt(examType)==3)){
			List<Integer> classIds=transaction.getclassIds(Integer.parseInt(examName));
			return helper.convertTOClassMapByclassIds(listValues,classIds);
		}
		return helper.convertTOClassMap(listValues);

	}
	public HashMap<Integer, String> getSecondLanguageNew() {
		ArrayList<ExamSecondLanguageMasterBO> listBO = new ArrayList(impl
				.select_All(ExamSecondLanguageMasterBO.class));
		return helper.convertBOToTO_SecondLanguage(listBO);
	}
	public HashMap<Integer, String> getExamNameByAcademicYearAndExamType(int academicYear,String examType) {
		return helper.convertBOtoTO_ExamName_Internal(impl
				.getExamNameByAcademicYearAndExamType(academicYear,examType));
	}

	public Map<Integer, String> getSubjectsCodeNameByCourseSchemeIdByTeacher(String sCodeName,int courseId,
			int schemeId, int schemeNo, String teacherId) {
		return helper.convertTOToMap_SubjectsCodeName(helper
				.convertBOToTO_SubjectsCodeName(impl.getSubjectsCodeNameByCourseSchemeIdByTeacher(
						sCodeName,courseId, schemeId, schemeNo, teacherId),sCodeName));
	}
}
