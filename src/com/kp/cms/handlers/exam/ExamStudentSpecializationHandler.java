package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.bo.exam.ExamStudentSpecializationBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.forms.exam.ExamStudentSpecializationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.exam.ExamStudentSpecializationHelper;
import com.kp.cms.to.exam.ExamStudentSpecializationTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamStudentSpecializationImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamStudentSpecializationHandler extends
		ExamSpecializationSubjectGroupHandler {

	ExamStudentSpecializationHelper helper = new ExamStudentSpecializationHelper();
	ExamStudentSpecializationImpl impl = new ExamStudentSpecializationImpl();

	// To get the specialization list
	public ArrayList<KeyValueTO> getSpecializationList() throws Exception {
		ArrayList<ExamSpecializationBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamSpecializationBO.class));
		return helper.convertBOToTO_SpecializationList(listBO);
	}

	// On - SEARCH
	public ArrayList<ExamStudentSpecializationTO> getSpecializationStudent(
			String schemeNo, int courseId, int classId, int specId,
			Integer academicYear) throws Exception {

		String sectionName = impl.getSectionByClass(classId);
		/*ArrayList<ExamStudentSpecializationBO> listBO = new ArrayList(impl
				.select_student_specialization(schemeNo, courseId, sectionName,
						specId, academicYear));*/
		ArrayList<ExamStudentBioDataBO> listBO = new ArrayList(impl
				.select_student_specialization(schemeNo, courseId, sectionName,
						specId, academicYear));		

		return helper.convertBOToTO_SpecializationStudent(listBO);
	}

	// On - ASSIGN
	public ArrayList<ExamStudentSpecializationTO> getUnAssignedStudent(
			int academicYear, int courseId, int schemeNo, int classId,
			int schemeid) throws Exception {

		String sectionName = impl.getSectionByClass(classId);
		ArrayList<Object> listBO = new ArrayList(impl.selectUnAssignedStudents(
				academicYear, courseId, schemeNo, sectionName, schemeid));

		return helper.convertBOToTO_SpecializationNotAssignStudent(listBO);
	}

	// On - SEACH (APPLY for Update)
	public void updateStudentSpecialization(int specializationId,
			String userId, ArrayList<Integer> listOfIds) throws Exception {

		impl.update(specializationId, userId, listOfIds);
	}

	// To get the section name
	public String getSectionByClass(int classId) throws Exception {
		String sectionName = null;

		if (classId == 0) {
			return sectionName = "";
		} else {
			sectionName = ((ClassUtilBO) impl.select_Unique(classId,
					ClassUtilBO.class)).getSectionName();

			return sectionName;
		}

	}

	// On - ASSIGN (APPLY for Add)
	public void add(int specId, String userId, ArrayList<Integer> listOfIds,
			int courseId, String schemeNo, String sectionName) throws Exception {
		ArrayList<ExamStudentSpecializationBO> listBO = new ArrayList<ExamStudentSpecializationBO>();
		List<Integer> studentIdList = new ArrayList<Integer>();
		for (Integer studentId : listOfIds) {
			listBO.add(new ExamStudentSpecializationBO(courseId, studentId,
					specId, schemeNo, sectionName, userId));
			studentIdList.add(studentId);
		}
		
		//impl.insert_List(listBO);
//		Map<Integer, Integer> idMap =  impl.getIdsFromExamStudentBiodata();
		Iterator<Integer> itr = studentIdList.iterator();
		ExamStudentBioDataBO examStudentBioDataBO ;
		List<ExamStudentBioDataBO> examStudentBioDataBOList = new ArrayList<ExamStudentBioDataBO>();
		while (itr.hasNext()) {
			Integer studentId = (Integer) itr.next();
			examStudentBioDataBO = new ExamStudentBioDataBO();
//			if(idMap.containsKey(studentId)){
//				examStudentBioDataBO.setId(idMap.get(studentId));
//			}
			examStudentBioDataBO.setStudentId(studentId);
			examStudentBioDataBO.setSpecializationId(specId);
			examStudentBioDataBOList.add(examStudentBioDataBO);
		}
		
		impl.addSpecialization(examStudentBioDataBOList);

	}

	public ExamStudentSpecializationForm retainAllValues(
			ExamStudentSpecializationForm objform) throws Exception {
		CommonAjaxHandler commonHandler = new CommonAjaxHandler();

		if (CommonUtil.checkForEmpty(objform.getCourse())
				&& CommonUtil.checkForEmpty(objform.getAcademicYear())) {
			int yr = Integer.parseInt(objform.getAcademicYear().split("-")[0]);

			if (objform.getCourse() != null && yr != 0) {
				objform.setSchemeNameList(commonHandler
						.getSchemeNo_SchemeIDByCourseIdAcademicId(Integer
								.parseInt(objform.getCourse()), yr));
				objform.setScheme(objform.getScheme());
			} else {
				objform.setSchemeNameList(new HashMap<String, String>());
				objform.setScheme("");
			}
		}
		if (CommonUtil.checkForEmpty(objform.getScheme())
				&& CommonUtil.checkForEmpty(objform.getCourse())) {

			String schemeId = objform.getScheme().split("_")[0];
			String schemeNo = objform.getScheme().split("_")[1];
			int year = 0;
			if (objform.getYear() != null) {
				String year1 = objform.getYear().split("-")[0];
				year = Integer.parseInt(year1);
			}
			if (year > 0) {
				if (objform.getCourse() != null && objform.getScheme() != null) {
					objform
							.setSectionList(new ExamGenHandler()
									.getSectionByCourseIdSchemeId(objform
											.getCourse(), schemeId, Integer
											.parseInt(schemeNo), year));
				}
			}
			objform.setSection(objform.getSection());

		}
		return objform;
	}

	//Code Written By Balaji
	public ArrayList<ExamStudentSpecializationTO> getUnAssignedStudent1(int academicYear, int courseId, int schemeNo, int classId,
			int schemeid) throws Exception {

		String sectionName = impl.getSectionByClass(classId);
		ArrayList<StudentUtilBO> listBO = new ArrayList(impl.selectStudents(academicYear, courseId, schemeNo, sectionName, schemeid));

		return helper.convertBOtoTO(listBO);
	}
	
	// On - SEACH (APPLY for Update) Code Written By Balaji
	public void updateStudentSpecialization1(int specializationId,
			String userId, ArrayList<ExamStudentSpecializationTO> listOfIds) throws Exception {

		impl.update1(specializationId, userId, listOfIds);
	}
	
	// Code is written by Balaji
	// To get the specialization list accourding to course 
	public ArrayList<KeyValueTO> getSpecializationList(int courseId) throws Exception {
		ArrayList<ExamSpecializationBO> listBO = new ArrayList(impl.select_ActiveOnlyByCourse(ExamSpecializationBO.class,courseId));
		return helper.convertBOToTO_SpecializationList(listBO);
	}
}
