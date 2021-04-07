package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.bo.exam.ExamSubjectSectionMasterBO;
import com.kp.cms.forms.exam.ExamSubjectDefCourseForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.SubjectDefinitionCertificateCourseHelper;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseDisplayTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.SubjectDefinitionCertificateCourseImpl;
import com.kp.cms.utilities.CommonUtil;

public class SubjectDefinitionCertificateCourseHandler extends ExamGenHandler {

	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(SubjectDefinitionCertificateCourseHandler.class);
	SubjectDefinitionCertificateCourseImpl impl = new SubjectDefinitionCertificateCourseImpl();
	SubjectDefinitionCertificateCourseHelper helper = new SubjectDefinitionCertificateCourseHelper();
	/**
	 * @param courseId
	 * @param schemeId
	 * @param schemeNo
	 * @param academicYear
	 * @param objform
	 * @param subId 
	 * @throws Exception
	 */
	public ArrayList<ExamSubDefinitionCourseWiseTO> ViewCertificateCourse(int courseId, int schemeId, int schemeNo, int academicYear,
			ExamSubjectDefCourseForm objform, int subId) throws Exception{
		ArrayList<ExamSubDefinitionCourseWiseBO> listBO = impl.UpdateCertificateCourse(courseId, schemeId, schemeNo,academicYear,subId);
		Iterator iterator = listBO.iterator();
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			
			objform.setSubId(objects[0].toString());	
			objform.setSubjectName(objects[1].toString());
			objform.setSubjectCode(objects[2].toString());
			
		String isTheoryOrPractical =checkIfTheoryOrPractical(Integer.parseInt(objects[0].toString()));

		if (isTheoryOrPractical != null && isTheoryOrPractical.length() > 0
				&& isTheoryOrPractical.equalsIgnoreCase("t")) {
			objform.setIsTheoryOrPractical("t");

		} else if (isTheoryOrPractical != null
				&& isTheoryOrPractical.length() > 0
				&& isTheoryOrPractical.equalsIgnoreCase("p")) {
			objform.setIsTheoryOrPractical("p");
		} else {
			objform.setIsTheoryOrPractical("b");
		}

		objform.setCourseId(Integer.toString(courseId));
		objform.setSchemeNo(Integer.toString(schemeNo));
		objform.setSchemeId(Integer.toString(schemeId));
		objform.setListSubjectSection(getSubjectSectioneList());
		getSubjectDetailsFromSubjectId(Integer.parseInt(objform.getSubId()), objform);
	   }
		return helper.convertBOToTO(listBO);
   }
	/**
	 * @param subjectId
	 * @return
	 */
	public String checkIfTheoryOrPractical(int subjectId) {

		return impl.checkIfTheoryOrPractical(subjectId);
	}
	// fetch details for a subject

	/**
	 * @param subjectId
	 * @param objform
	 * @return
	 */
	public List<ExamSubDefinitionCourseWiseDisplayTO> getSubjectDetailsFromSubjectId(
			int subjectId, ExamSubjectDefCourseForm objform) {
		List<ExamSubDefinitionCourseWiseBO> listBO = null;

		listBO = impl.getSubjectDetailsFromSubjectId(subjectId, Integer
				.parseInt(objform.getCourseId()),Integer.parseInt(objform.getSchemeNo()),Integer.parseInt(objform.getAcademicYear()));

		List<ExamSubDefinitionCourseWiseDisplayTO> listTO = helper.convertBOToDisplayTO(listBO);
		setToform(objform, listTO);

		return listTO;
	}
	/**
	 * @param objform
	 * @param listTO
	 */
	private void setToform(ExamSubjectDefCourseForm objform,
			List<ExamSubDefinitionCourseWiseDisplayTO> listTO) {
		objform.resetFields();
		for (ExamSubDefinitionCourseWiseDisplayTO to : listTO) {
			objform.setDupdoNotAdd(to.getDontAddTotMarkClsDecln());
			objform.setDupdoNotConsider(to.getDontConsiderFailureTotalResult());
			objform.setDupshowOnlyGrade(to.getShowOnlyGrade());
			objform.setDupshowInternalMarks(to.getShowInternalFinalMarkAdded());
			objform.setDupsubjectType(to.getDontShowSubType());
			objform.setDupmaxMarks(to.getDontShowMaxMarks());
			objform.setDupminMarks(to.getDontShowMinMarks());
			objform.setDupattendanceMarks(to.getDontShowAttMarks());
			objform.setSubjectCode(to.getSubjectCode());
			objform.setSubId(Integer.toString(to.getSubjectId()));
			objform.setSubjectSection(Integer
					.toString(to.getSubjectSectionId()));

			objform.setSubjectOrder(Integer.toString(to.getSubjectOrder()));
			objform.setUniversitySubjectCode(to.getUniversitySubjectCode());
			objform.setTheoryCredit(Integer.toString(to.getTheoryCredit()));
			objform.setTheoryHours(Integer.toString(to.getTheoryHours()));
			objform.setPractCredit(Integer.toString(to.getPracticalCredit()));
			objform.setPractHours(Integer.toString(to.getPracticalHours()));
			objform.setId(Integer.toString(to.getId()));

		}

	}
	/**
	 * @param courseId
	 * @param schemeId
	 * @param schemeNo
	 * @param academicYear
	 * @param objform
	 * @throws Exception
	 */
	public void UpdateCertificateCourse( int courseId, int schemeId, int schemeNo, int academicYear,
			                           ExamSubjectDefCourseForm objform,int subjectId) throws Exception {
		ArrayList<ExamSubDefinitionCourseWiseBO> listBO = impl.UpdateCertificateCourse(courseId, schemeId, schemeNo,academicYear,subjectId);
		Integer maxOrder = impl.GetMaxSubjectOrder(courseId, schemeId, schemeNo,	academicYear);
		Iterator iterator = listBO.iterator();
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			
			 objform.setSubId(objects[0].toString());	
			 objform.setSubjectName(objects[1].toString());
			 objform.setSubjectCode(objects[2].toString());
			 
			 addSubDefCourseDetails(objform, schemeNo,maxOrder);
			 
	    	}
	}
	/**
	 * @param objform
	 * @param schemeNo
	 * @param maxOrder
	 * @throws Exception
	 */
	public void addSubDefCourseDetails(ExamSubjectDefCourseForm objform, int schemeNo, Integer maxOrder)
	throws Exception {
    ExamSubDefinitionCourseWiseBO objBO = null;

   if (objform != null) {
	int subjectOrder = 0;
	int courseId = 0;
	int theoryHours = 0;
	int theoryCredit = 0;
	int practicalHours = 0;
	int practicalCredit = 0;
	int isActiveGradeDefn = 0;
	int isActiveAttndnc = 0;
	int academicYear = 0;
	objform = checkNull(objform);
	String userId = objform.getUserId();
	String universitySubjectCode = objform.getUniversitySubjectCode();
	int dontShowSubType = (objform.getSubjectType().equals("on") ? 1
			: 0);
	int dontShowMaxMarks = (objform.getMaxMarks().equals("on") ? 1 : 0);
	int dontShowMinMarks = (objform.getMinMarks().equals("on") ? 1 : 0);
	int dontShowAttMarks = (objform.getAttendanceMarks().equals("on") ? 1
			: 0);
	int dontAddTotMarkClsDecln = (objform.getDoNotAdd().equals("on") ? 1
			: 0);
	int dontConsiderFailureTotalResult = (objform.getDoNotConsider()
			.equals("on") ? 1 : 0);
	int showInternalFinalMarkAdded = (objform.getShowInternalMarks()
			.equals("on") ? 1 : 0);
	int showOnlyGrade = (objform.getShowOnlyGrade().equals("on") ? 1
			: 0);

	int subjectId = Integer.parseInt(objform.getSubId());

	subjectOrder = maxOrder;


	int subjectSectionId = Integer
			.parseInt(objform.getSubjectSection());

	if (objform.getPractCredit() != null
			&& objform.getPractCredit().length() != 0) {
		practicalCredit = Integer.parseInt(objform.getPractCredit());

	}
	if (objform.getTheoryHours() != null
			&& objform.getTheoryHours().length() != 0) {
		theoryHours = Integer.parseInt(objform.getTheoryHours());

	}
	if (objform.getTheoryCredit() != null
			&& objform.getTheoryCredit().length() != 0) {
		theoryCredit = Integer.parseInt(objform.getTheoryCredit());

	}
	if (objform.getPractHours() != null
			&& objform.getPractHours().length() != 0) {
		practicalHours = Integer.parseInt(objform.getPractHours());

	}

	if (objform.getCourseId() != null
			&& objform.getCourseId().length() != 0) {
		courseId = Integer.parseInt(objform.getCourseId());

	}
	if(objform.getAcademicYear()!=null && objform.getAcademicYear().length() !=0){
		academicYear = Integer.parseInt(objform.getAcademicYear());
	}
	if (impl.checkCommonSubject(subjectId, courseId))
		impl.duplicateOrderCheck(subjectId, courseId, schemeNo,academicYear);

	if (theoryHours == 0 && theoryCredit == 0) {

		objBO = new ExamSubDefinitionCourseWiseBO(subjectId,
				subjectOrder, universitySubjectCode, subjectSectionId,
				theoryHours, theoryCredit, practicalHours,
				practicalCredit, dontShowSubType, dontShowMaxMarks,
				dontShowMinMarks, dontShowAttMarks,
				dontAddTotMarkClsDecln, dontConsiderFailureTotalResult,
				showInternalFinalMarkAdded, showOnlyGrade,
				isActiveGradeDefn, isActiveAttndnc, userId, courseId, schemeNo,academicYear);

	} else if (practicalHours == 0 && practicalCredit == 0) {
		// if theory
		objBO = new ExamSubDefinitionCourseWiseBO(subjectId,
				subjectOrder, universitySubjectCode, subjectSectionId,
				theoryHours, theoryCredit, dontShowSubType,
				dontShowMaxMarks, dontShowMinMarks, dontShowAttMarks,
				dontAddTotMarkClsDecln, dontConsiderFailureTotalResult,
				showInternalFinalMarkAdded, showOnlyGrade,
				isActiveGradeDefn, isActiveAttndnc, userId, courseId, schemeNo,academicYear);
	} else {
		// if both
		objBO = new ExamSubDefinitionCourseWiseBO(subjectId,
				subjectOrder, universitySubjectCode, subjectSectionId,
				theoryHours, theoryCredit, practicalHours,
				practicalCredit, dontShowSubType, dontShowMaxMarks,
				dontShowMinMarks, dontShowAttMarks,
				dontAddTotMarkClsDecln, dontConsiderFailureTotalResult,
				showInternalFinalMarkAdded, showOnlyGrade,
				isActiveGradeDefn, isActiveAttndnc, userId, courseId, schemeNo,academicYear);

	}
	if (impl.isPresentDetails(subjectId, courseId,schemeNo,academicYear,objform) > 0) {
		int id = Integer.parseInt(objform.getId());
		objBO = new ExamSubDefinitionCourseWiseBO(id, subjectId,
				subjectOrder, universitySubjectCode, subjectSectionId,
				theoryHours, theoryCredit, practicalHours,
				practicalCredit, dontShowSubType, dontShowMaxMarks,
				dontShowMinMarks, dontShowAttMarks,
				dontAddTotMarkClsDecln, dontConsiderFailureTotalResult,
				showInternalFinalMarkAdded, showOnlyGrade,
				isActiveGradeDefn, isActiveAttndnc, userId, courseId, schemeNo,academicYear);

		impl.update(objBO);

	} else if (impl.checkOptionalAndSecondLang(subjectId)) {

		impl.insert(objBO);
	} else if (!impl.isDuplicatedSubject(subjectId,courseId, schemeNo,academicYear)) {
		impl.insert(objBO);
	}

}

}
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public ExamSubDefinitionCourseWiseTO createFormObjcet(ExamSubjectDefCourseForm form) throws Exception {

		return helper.createBOToTO(impl.select_subcode_subname(Integer.parseInt(form.getSubId())));
	}
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<KeyValueTO> getSubjectSectioneList() {
		List<ExamSubjectSectionMasterBO> listBO = new ArrayList(impl.select_ActiveOnly(ExamSubjectSectionMasterBO.class));
		return helper.convertBOToTO_SubjectSection_List(listBO);

	}
	/**
	 * @param objform
	 * @return
	 */
	private ExamSubjectDefCourseForm checkNull(ExamSubjectDefCourseForm objform) {
		if (objform.getSubjectType() == null) {
			objform.setSubjectType("0");
		}
		if (objform.getMaxMarks() == null) {
			objform.setMaxMarks("0");
		}
		if (objform.getMinMarks() == null) {
			objform.setMinMarks("0");
		}
		if (objform.getAttendanceMarks() == null) {
			objform.setAttendanceMarks("0");
		}
		if (objform.getDoNotAdd() == null) {
			objform.setDoNotAdd("0");
		}
		if (objform.getDoNotConsider() == null) {
			objform.setDoNotConsider("0");
		}
		if (objform.getShowInternalMarks() == null) {
			objform.setShowInternalMarks("0");
		}
		if (objform.getShowOnlyGrade() == null) {
			objform.setShowOnlyGrade("0");
		}

		if (objform.getTheoryHours() == null
				|| objform.getTheoryHours().length() == 0) {
			objform.setTheoryHours("0");

		}
		if (objform.getTheoryCredit() == null
				|| objform.getTheoryCredit().length() == 0) {
			objform.setTheoryCredit("0");
		}
		if (objform.getPractCredit() == null
				|| objform.getPractCredit().length() == 0) {
			objform.setPractCredit("0");
		}
		if (objform.getPractHours() == null
				|| objform.getPractHours().length() == 0) {
			objform.setPractHours("0");
		}

		return objform;
	}

	/**
	 * @param academicYear
	 * @return
	 */
	public String getacademicYear(int academicYear) {

		String academicYearName = null;
		if (academicYear != 0) {
			academicYearName = Integer.toString(academicYear).concat("-")
					+ Integer.toString(academicYear + 1);
		}
		return academicYearName;
	}

	/**
	 * @param objform
	 * @param req
	 * @return
	 */
	public ExamSubjectDefCourseForm retainAllValues(
			ExamSubjectDefCourseForm objform,HttpServletRequest req) {

		if (CommonUtil.checkForEmpty(objform.getCourse())) {
//			objform.setCourse(objform.getCourse());
			objform.setSchemeMapList(getSchemeNo_SchemeIDByCourseIdAcademicId(
					objform.getCourse(), objform.getAcademicYear()));
//			objform.setScheme(objform.getScheme());
		}
		
		if(CommonUtil.checkForEmpty(objform.getProgramId())){
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgramInOrder(Integer.parseInt(objform.getProgramId()));
			req.setAttribute("courseMap", courseMap);
		}

		return objform;
	}
	public List<ExamSubDefinitionCourseWiseTO> searchSubCerDetails(ExamSubjectDefCourseForm objform)  throws Exception{
		   List<CertificateCourse> cerBoList=impl.getCertificateCourseList(objform);
		   List<ExamSubDefinitionCourseWiseTO> cerToList=helper.convertBosToTOs(cerBoList);
			return cerToList;
		}
	public void getSubjectsForInput(int subjectId, ExamSubjectDefCourseForm objform) throws Exception{
		impl.getSubjectGroupsForInput(objform,subjectId);
		}



}
