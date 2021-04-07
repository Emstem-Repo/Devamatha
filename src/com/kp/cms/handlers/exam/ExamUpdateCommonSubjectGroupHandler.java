package com.kp.cms.handlers.exam;

/**
 * Dec 31, 2009 Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.kp.cms.bo.exam.ApplicantSubjectGroupUtilBO;
import com.kp.cms.bo.exam.ClassSchemewiseUtilBO;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.helpers.exam.ExamUpdateCommonSubjectGroupHelper;
import com.kp.cms.transactionsimpl.exam.ExamUpdateCommonSubjectGroupImpl;

public class ExamUpdateCommonSubjectGroupHandler extends ExamGenHandler {

	ExamUpdateCommonSubjectGroupHelper helper = new ExamUpdateCommonSubjectGroupHelper();
	ExamUpdateCommonSubjectGroupImpl impl = new ExamUpdateCommonSubjectGroupImpl();

	public void update(Integer academicYear, ArrayList<Integer> listClassId) {
		HashMap<Integer, ArrayList<Integer>> mapClass_ListStudent = helper
				.convertBOToMap_ClassStudent(impl.select_Students(listClassId));
		ArrayList<ClassSchemewiseUtilBO> listSubjectGroups = impl
				.select_SubjectGroups(listClassId);
		HashMap<Integer, ArrayList<Integer>> mapClass_ListCourseSubGroup = helper
				.convertBOToMap_ClassSubjectGroup(listSubjectGroups);
		HashMap<Integer, ArrayList<Integer>> mapClass_ListSplSubGroup = helper
				.convertBOToMap_ListSplSubGroup(listSubjectGroups);

		ArrayList<ApplicantSubjectGroupUtilBO> listForUpdate = new ArrayList<ApplicantSubjectGroupUtilBO>();
		ArrayList<Integer> listStudent = null;
		ArrayList<Integer> listCourseSubGroup = null;
		ArrayList<Integer> listCourseSubGroupStudent = null;

		Iterator<Integer> itr = mapClass_ListStudent.keySet().iterator();
		while (itr.hasNext()) {
			Integer classId = (Integer) itr.next();
			listStudent = mapClass_ListStudent.get(classId);
			listCourseSubGroup = mapClass_ListCourseSubGroup.get(classId);
			listCourseSubGroupStudent = mapClass_ListSplSubGroup.get(classId);

			for (Integer studentId : listStudent) {

				Integer applnId = impl.getStudentAdmnApplnId(studentId);

				impl.delPrevSubGrp(applnId);
			}

			for (Integer studentId : listStudent) {

				ExamUpdateProcessHandler u = new ExamUpdateProcessHandler();

				// To fetch the student's second language Id

				Integer applnId = impl.getStudentAdmnApplnId(studentId);
				Integer secondLanguage = u.getSecondLanguageId(studentId);
				Integer subGrpId = impl.getsubGrpBySecondLanguage(
						secondLanguage, listCourseSubGroupStudent);

				if (subGrpId != null) {

					listForUpdate.add(new ApplicantSubjectGroupUtilBO(applnId,
							subGrpId));
				}
				for (Integer courseSubGroupId : listCourseSubGroup) {

					listForUpdate.add(new ApplicantSubjectGroupUtilBO(applnId,
							courseSubGroupId));

				}

			}

		}

		impl.insert_List(listForUpdate);

	}

	public String getClassNameByIds(String[] classIds) {
		StringBuilder classNames = new StringBuilder();
		for (String id : classIds) {
			classNames.append(((ClassUtilBO) impl.select_Unique(Integer.parseInt(id),ClassUtilBO.class)).getName() + ",");
		}
		classNames.setCharAt(classNames.length()-1, ' ');
		return classNames.toString().trim();
	}
}
