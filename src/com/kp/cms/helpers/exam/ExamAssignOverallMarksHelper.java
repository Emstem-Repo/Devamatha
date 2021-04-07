package com.kp.cms.helpers.exam;

/**
 * Mar 1, 2010 Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import com.kp.cms.to.exam.ExamAssignOverallMarksTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

@SuppressWarnings("unchecked")
public class ExamAssignOverallMarksHelper extends ExamGenHelper {

	// To get students for course, scheme & subject
	public ArrayList<ExamAssignOverallMarksTO> convertBOToTO_Students_List(
			List<Object[]> select_Students, Integer examId, int courseId,
			int subjectId, String assignMentOverall, Integer assinmenttypeid,
			int subjectTypeId, int schemeNO) {
		String isTheoryPractical = "";

		if (subjectTypeId == 1) {
			isTheoryPractical = "T";
		} else if (subjectTypeId == 0) {
			isTheoryPractical = "P";
		} else {
			isTheoryPractical = "B";
		}
		ExamAssignOverallMarksTO to;
		ArrayList<ExamAssignOverallMarksTO> retutnList = new ArrayList<ExamAssignOverallMarksTO>();
		Iterator itr = select_Students.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamAssignOverallMarksTO();

			int id = 0;
			if (row[5] != null) {
				id = Integer.parseInt(row[5].toString());
			}
			to.setId(id);
			if (row[0] != null) {
				to.setRegNo(row[0].toString());
			}
			if (row[1] != null) {
				to.setRollNo(row[1].toString());
			}

			to.setStudentName(row[2].toString());
			if (row[3] != null) {

				to.setTheoryMarks(row[3].toString());
			}
			if (row[4] != null) {

				to.setPracticalMarks(row[4].toString());
			}
			int studentId = 0;
			if (row[6] != null) {
				studentId = Integer.parseInt(row[6].toString());
			}
			if(row.length > 7 && row[7]!= null){
				to.setClassId(Integer.parseInt(row[7].toString()));
			}
			to.setStudentId(studentId);
			to.setDummyStudentId(studentId);

			to.setIsTheoryPractical(isTheoryPractical);

			if (examId != null) {
				to.setExamId(examId);
			}
			to.setCourseId(courseId);
			to.setSubjectId(subjectId);
			to.setSchemeNo(schemeNO);

			to.setOverallName(assignMentOverall);
			if (assinmenttypeid != null) {
				to.setOverallId(assinmenttypeid);
			}

			retutnList.add(to);
		}
		Collections.sort(retutnList);
		return retutnList;

	}

	// To get schemeNo for a course & exam
	public Map<String, String> convertBOtoTO_SchemeNoBy_ExamId_CourseId(
			List select_SchemeNoBy_ExamId_CourseId) {
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator itr = select_SchemeNoBy_ExamId_CourseId.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			String name = row[1].toString() + "-" + row[2].toString();
			String id = row[0].toString() + "_" + row[1].toString();
			map.put(id, name);
		}
		map = (HashMap<String, String>) CommonUtil.sortMapByValue(map);
		return sort(map);
	}

	public Map<String, String> sort(HashMap<String, String> map) {
		Map<String, String> treeMap = new TreeMap<String, String>();
		List mapKeys = new ArrayList(map.keySet());
		List mapValues = new ArrayList(map.values());
		map.clear();
		TreeSet sortedSet = new TreeSet(mapValues);
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;

		for (int i = 0; i < size; i++) {
			String key = (String) mapKeys
					.get(mapValues.indexOf(sortedArray[i]));
			treeMap.put(key, sortedArray[i].toString());
		}
		map = (HashMap<String, String>) CommonUtil.sortMapByValue(map);
		return treeMap;
	}

	// To get subjects for a particular course & scheme
	public HashMap<Integer, String> convertBOToTO_Map(
			List<Object[]> select_Subjects) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Iterator itr = select_Subjects.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			map.put((Integer) row[0], (String) row[1]);
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public boolean checkComponets(List<Object[]> object) {

		for (Iterator<Object[]> iterator = object.iterator(); iterator
				.hasNext();) {
			Object[] row = iterator.next();

			if (row[0] != null) {

			}

		}

		return false;
	}

	public Map<Integer, String> convertBOTOMapList(
			List<Object[]> courseByAcademicYear) throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (courseByAcademicYear != null) {
			Iterator itr = courseByAcademicYear.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				map.put((Integer) row[0], (String) row[1]);
			}
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	// To Get Exam name list
	public ArrayList<KeyValueTO> convertBOToTO_ExamNameRRS(
			ArrayList<Object[]> listBO) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (Object[] row : listBO) {
			listTO.add(new KeyValueTO((Integer) row[0], row[1].toString()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

}
