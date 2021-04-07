package com.kp.cms.helpers.exam;

/**
 * Dec 31, 2009 Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.kp.cms.bo.exam.ClassSchemewiseUtilBO;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.CurriculumSchemeSubjectUtilBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.utilities.CommonUtil;

public class ExamUpdateCommonSubjectGroupHelper extends ExamGenHelper {

	// return a map of key= ClassId and Value = list of Student for class
	public HashMap<Integer, ArrayList<Integer>> convertBOToMap_ClassStudent(
			ArrayList<ClassUtilBO> listStudents) {

		HashMap<Integer, ArrayList<Integer>> mapClass_ListStudent = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> listStudentId;
		Integer classId;
		for (ClassUtilBO eBO : listStudents) {
			classId = eBO.getId();
			listStudentId = new ArrayList<Integer>();
			Iterator<ClassSchemewiseUtilBO> ics = eBO.getClassSchemewiseUtilBOSet().iterator();
			while (ics.hasNext()) {
				ClassSchemewiseUtilBO classSchemewiseUtilBO = (ClassSchemewiseUtilBO) ics
						.next();
				Iterator<StudentUtilBO> iStu = classSchemewiseUtilBO
						.getStudentUtilBOSet().iterator();
				while (iStu.hasNext()) {
					StudentUtilBO studentUtilBO = (StudentUtilBO) iStu.next();

					listStudentId.add(studentUtilBO.getId());
				}
			}

			mapClass_ListStudent.put(classId, listStudentId);
		}
		mapClass_ListStudent = (HashMap<Integer, ArrayList<Integer>>) CommonUtil.sortMapByValue(mapClass_ListStudent);
		return mapClass_ListStudent;
	}

	public HashMap<Integer, ArrayList<Integer>> convertBOToMap_ClassSubjectGroup(
			ArrayList<ClassSchemewiseUtilBO> listSubjectGroups) {

		HashMap<Integer, ArrayList<Integer>> mapClass_ListSubjectGroup = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> listSubjectGroupId;
		Integer classId;
		for (ClassSchemewiseUtilBO eBO : listSubjectGroups) {
			classId = eBO.getClassId();
			listSubjectGroupId = new ArrayList<Integer>();

			Iterator<CurriculumSchemeSubjectUtilBO> ics = eBO
					.getCurriculumSchemeDurationUtilBO()
					.getCurriculumSchemeSubjectUtilBOSet().iterator();

			while (ics.hasNext()) {
				CurriculumSchemeSubjectUtilBO curriculumSchemeSubjectUtilBO = (CurriculumSchemeSubjectUtilBO) ics
						.next();
				if (curriculumSchemeSubjectUtilBO.getSubjectGroupUtilBO()
						.getIsCommonSubGrp() == 1) {
					listSubjectGroupId.add(curriculumSchemeSubjectUtilBO
							.getSubjectGroupUtilBO().getId());
				}
			}

			mapClass_ListSubjectGroup.put(classId, listSubjectGroupId);
		}
		mapClass_ListSubjectGroup = (HashMap<Integer, ArrayList<Integer>>) CommonUtil.sortMapByValue(mapClass_ListSubjectGroup);
		return mapClass_ListSubjectGroup;
	}

	public HashMap<Integer, ArrayList<Integer>> convertBOToMap_ListSplSubGroup(
			ArrayList<ClassSchemewiseUtilBO> listSubjectGroups) {

		HashMap<Integer, ArrayList<Integer>> mapClass_ListSubjectGroup = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> listSubjectGroupId;
		Integer classId;
		for (ClassSchemewiseUtilBO eBO : listSubjectGroups) {
			classId = eBO.getClassId();
			listSubjectGroupId = new ArrayList<Integer>();

			Iterator<CurriculumSchemeSubjectUtilBO> ics = eBO
					.getCurriculumSchemeDurationUtilBO()
					.getCurriculumSchemeSubjectUtilBOSet().iterator();

			while (ics.hasNext()) {
				CurriculumSchemeSubjectUtilBO curriculumSchemeSubjectUtilBO = (CurriculumSchemeSubjectUtilBO) ics
						.next();
				
					listSubjectGroupId.add(curriculumSchemeSubjectUtilBO
							.getSubjectGroupUtilBO().getId());
				
			}

			mapClass_ListSubjectGroup.put(classId, listSubjectGroupId);
		}
		mapClass_ListSubjectGroup = (HashMap<Integer, ArrayList<Integer>>) CommonUtil.sortMapByValue(mapClass_ListSubjectGroup);
		return mapClass_ListSubjectGroup;
	}

}
