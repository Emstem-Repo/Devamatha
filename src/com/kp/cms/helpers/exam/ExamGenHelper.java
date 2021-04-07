package com.kp.cms.helpers.exam;

/**
 * Jan 8, 2010 Created By 9Elements
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import com.kp.cms.bo.admin.ExamSecondLanguage;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamCourseGroupCodeBO;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.ExamSecondLanguageMasterBO;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.bo.exam.SubjectGroupUtilBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.to.exam.ExamClassTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamSubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

@SuppressWarnings("unchecked")
public class ExamGenHelper {

	public ArrayList<ExamCourseUtilTO> convertBOtoTO_course(ArrayList<ExamCourseUtilBO> listBO) {
		ArrayList<ExamCourseUtilTO> list = new ArrayList<ExamCourseUtilTO>();
		ExamCourseUtilTO to = null;
		for (ExamCourseUtilBO am : listBO) {
			to = new ExamCourseUtilTO(am.getCourseID(), am.getPTypeProgramCourse());
			list.add(to);
		}
		Collections.sort(list);
		return list;
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
		return treeMap;
	}

	// To get course
	public ArrayList<KeyValueTO> convertBOToTO_Course_KeyVal(
			ArrayList<ExamCourseUtilBO> listBO) {

		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamCourseUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getCourseID(), bo.getCourseName()));
			}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;

	}

	public ArrayList<KeyValueTO> convertBOToTO_Mul_Ans_Script_KeyVal(
			ArrayList<ExamMultipleAnswerScriptMasterBO> listBO) {

		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamMultipleAnswerScriptMasterBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;

	}

	// To get the scheme for a particular course
	public Map<String, String> convertBOToTO_course_SchemeId_SchemeNo(
			List<CurriculumSchemeUtilBO> listBO) {
		HashMap<String, String> map = new HashMap<String, String>();

		int maxValue = 0;
		String course_SchemeId_SchemeNo = "";
		int courseSchemeId = 0;
		for (CurriculumSchemeUtilBO bo : listBO) {
			if (maxValue < bo.getNoScheme()) {
				maxValue = bo.getNoScheme();
				courseSchemeId = bo.getCourseSchemeId();
			}
		}
		for (int i = 1; i <= maxValue; i++) {
			course_SchemeId_SchemeNo = courseSchemeId + "_" + i;
			map.put(course_SchemeId_SchemeNo, Integer.toString(i));
		}
		ExamAssignOverallMarksHelper h = new ExamAssignOverallMarksHelper();
		return h.sort(map);
	}

	// To get the scheme for a particular course
	public HashMap<Integer, String> convertBOToTO_Scheme(
			List<CurriculumSchemeUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (CurriculumSchemeUtilBO bo : listBO) {

			map.put(bo.getCourseSchemeId(), bo.getCourseSchemeUtilBO()
					.getName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	// To get the subject group
	public HashMap<Integer, String> convertBOToTO_SubGrp_Map(
			List<SubjectGroupUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (SubjectGroupUtilBO bo : listBO) {
			map.put(bo.getId(), bo.getName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	// To get subject group list
	public ArrayList<KeyValueTO> convertBOToTO_SubGrp_List(
			List<SubjectGroupUtilBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (SubjectGroupUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	// To Get Exam name list
	public ArrayList<KeyValueTO> convertBOToTO_ExamName(
			ArrayList<ExamDefinitionBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamDefinitionBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public HashMap<Integer, String> convertBOToTO_Course_HashMap(
			ArrayList<ExamCourseUtilBO> listBO) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamCourseUtilBO bo : listBO) {
			map.put(bo.getCourseID(), bo.getCourseName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;

	}

	public HashMap<Integer, String> convertBOToTO_program_map(
			ArrayList<ExamProgramUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamProgramUtilBO bo : listBO) {
			map.put(bo.getProgramID(), bo.getProgramName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;

	}

	public ArrayList<KeyValueTO> convertBOToTO_CourseGrp_List(
			List<ExamCourseGroupCodeBO> listBO) {
		ArrayList<KeyValueTO> s = new ArrayList<KeyValueTO>();
		Iterator<ExamCourseGroupCodeBO> itr = listBO.iterator();
		while (itr.hasNext()) {
			ExamCourseGroupCodeBO examCourseGroupCodeBO = (ExamCourseGroupCodeBO) itr
					.next();
		s.add(new KeyValueTO(examCourseGroupCodeBO.getId(),
					examCourseGroupCodeBO.getName()));
		}
		Collections.sort(s,new KeyValueTOComparator());
		return s;
	}

	// To get Exam Type Hash Map
	public HashMap<Integer, String> convertBOToTO_ExamType_HashMap(
			ArrayList<ExamTypeUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamTypeUtilBO bo : listBO) {
			map.put(bo.getId(), bo.getName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	// To get the SchemeNo for a particular course
	public HashMap<Integer, String> convertBOToTO_SchemeNo_usingMaxValue(
			List<CurriculumSchemeUtilBO> listBO) {

		int maxValue = 0;
		for (CurriculumSchemeUtilBO bo : listBO) {
			if (maxValue < bo.getNoScheme()) {
				maxValue = bo.getNoScheme();
			}
		}
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 1; i <= maxValue; i++) {
			map.put(Integer.valueOf(i), Integer.toString(i));
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public ArrayList<KeyValueTO> convertBOToTO_specializationMaster(
			ArrayList<ExamSpecializationBO> select_studentSpecializationCourseId) {
		ArrayList<KeyValueTO> listTo = new ArrayList<KeyValueTO>();
		for (ExamSpecializationBO bo : select_studentSpecializationCourseId) {
			listTo.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTo,new KeyValueTOComparator());
		return listTo;
	}

	public ArrayList<KeyValueTO> convertBOToTOgetSection(
			ArrayList<ClassUtilBO> listBO) {
		KeyValueTO to;
		ArrayList<KeyValueTO> retutnList = new ArrayList<KeyValueTO>();
		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new KeyValueTO(Integer.parseInt(row[0].toString()), row[1]
					.toString());
			retutnList.add(to);
		}
		Collections.sort(retutnList,new KeyValueTOComparator());
		return retutnList;
	}

	public HashMap<Integer, String> convertBOToTO_SchemeNoToClassId(
			ArrayList<CurriculumSchemeUtilBO> listBOFrom,
			ArrayList<CurriculumSchemeUtilBO> listBOTo) {
		int schemeNoFrom = 0, schemeNoTo = 0;

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (CurriculumSchemeUtilBO bo : listBOFrom) {
			schemeNoFrom = bo.getNoScheme();
		}
		for (CurriculumSchemeUtilBO bo : listBOTo) {
			schemeNoTo = bo.getNoScheme();
		}

		if (schemeNoFrom < schemeNoTo) {
			for (int i = 1; i <= schemeNoFrom; i++) {

				map.put(i, Integer.toString(i));

			}
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public ArrayList<ExamSubjectTO> convertBOToTO_Subjects(
			ArrayList<ExamSubjectTO> subjectByCourse) {
		ExamSubjectTO to;
		ArrayList<ExamSubjectTO> retutnList = new ArrayList<ExamSubjectTO>();

		Iterator itr = subjectByCourse.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[0] != null && row[1] != null) {
				to = new ExamSubjectTO(Integer.parseInt(row[0].toString()),
						row[1].toString(), null);
				retutnList.add(to);
			}
		}
		Collections.sort(retutnList);
		return retutnList;
	}

	public Map<Integer, String> convertTOToMap_Subjects(
			ArrayList<ExamSubjectTO> subjectByCourse) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamSubjectTO examSubjectTO : subjectByCourse) {
			map.put(examSubjectTO.getId(), examSubjectTO.getSubjectName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public Map<Integer, String> convertTOToMap_ExamName(
			ArrayList<KeyValueTO> examByExamType) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (KeyValueTO keyValueTO : examByExamType) {
			map.put(keyValueTO.getId(), keyValueTO.getDisplay());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public Map<Integer, String> convertTOToMapCourse(List courseByExamNameRegNo) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Iterator itr = courseByExamNameRegNo.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			map.put(Integer.parseInt(row[0].toString()), row[1].toString());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	// To get the SchemeNo for a particular course
	// both id and schemeNo= schemeNo only
	public HashMap<Integer, String> convertBOToTO_SchemeNo(
			ArrayList<CurriculumSchemeUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		int maxValue = 0;
		for (CurriculumSchemeUtilBO bo : listBO) {
			if (maxValue < bo.getNoScheme()) {
				maxValue = bo.getNoScheme();
			}

		}
		for (CurriculumSchemeUtilBO bo : listBO) {
			map.put(bo.getCourseSchemeId(), Integer.toString(bo.getNoScheme()));

		}
		// test
		for (CurriculumSchemeUtilBO bo : listBO) {

			for (int i = 1; i <= maxValue; i++) {

				map.put(bo.getCourseSchemeId(), Integer.toString(i));

			}
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	// To get the SchemeNo for a particular course
	public HashMap<Integer, String> convertBOToTO_SchemeNo(
			List<CurriculumSchemeUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (CurriculumSchemeUtilBO bo : listBO) {
			map.put(bo.getId(), Integer.toString(bo.getNoScheme()));
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public ArrayList<ExamClassTO> convertTOTO(List courseIdSchemeNo){
		ArrayList<ExamClassTO> list = new ArrayList<ExamClassTO>();
		Iterator itr = courseIdSchemeNo.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			list.add(new ExamClassTO(Integer.parseInt(row[0].toString()),
					Integer.parseInt(row[1].toString()), Integer
							.parseInt(row[2].toString())));
		}
		Collections.sort(list);
		return list;
	}

	public Map<Integer, String> convertTOClassMap(List<KeyValueTO> listValues) {
		HashMap<Integer, String> classMap = new HashMap<Integer, String>();
		Iterator<KeyValueTO> itrr = listValues.iterator();
		while (itrr.hasNext()) {
			KeyValueTO val = (KeyValueTO) itrr.next();
			classMap.put(val.getId(), val.getDisplay());
		}
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}

	public HashMap<Integer, String> convertBOToTO_Class(List<ClassUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ClassUtilBO bo : listBO) {
			map.put(bo.getId(), bo.getName());
			
		}
		map=(HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	// To get Assignment Type List
	public HashMap<Integer, String> convertBOToTO_AssignmentType(
			List<ExamAssignmentTypeMasterBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamAssignmentTypeMasterBO bo : listBO) {
			map.put(bo.getId(), bo.getName());
		}
		map=(HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public HashMap<String, String> convertBOToTO_getInternalExamType(
			List<ExamInternalExamTypeBO> listBO) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (ExamInternalExamTypeBO bo : listBO) {
			map.put(bo.getId().toString(), bo.getName());
		}
		map=(HashMap<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public HashMap<Integer, String> convertBOtoTO_ExamName_Internal(
			ArrayList<ExamDefinitionBO> select_ExamName_Internal) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Iterator itr = select_ExamName_Internal.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			map.put((Integer) row[0], row[1].toString());
		}
		map=(HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;

	}

	public HashMap<Integer, String> convertBOToTO_AgreementList(
			ArrayList<ExamFooterAgreementBO> lbo) {
		HashMap<Integer, String> listTO = new HashMap<Integer, String>();

		for (ExamFooterAgreementBO bo : lbo) {

			listTO.put(bo.getId(), bo.getName());

		}
		listTO = (HashMap<Integer, String>) CommonUtil.sortMapByValue(listTO);
		return listTO;
	}

	public HashMap<Integer, String> convertBOToTO_FooterList(
			ArrayList<ExamFooterAgreementBO> lbo) {
		HashMap<Integer, String> listTO = new HashMap<Integer, String>();
		for (ExamFooterAgreementBO bo : lbo) {
			listTO.put(bo.getId(), bo.getName());
		}
		listTO = (HashMap<Integer, String>) CommonUtil.sortMapByValue(listTO);
		return listTO;
	}

	public HashMap<Integer, String> convertBOToTO_SecondLanguage(
			List<ExamSecondLanguageMasterBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamSecondLanguageMasterBO bo : listBO) {
			map.put(bo.getId(), bo.getName());
		}
		map=(HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public int convertBoToInt(StudentUtilBO select_student) {

		return select_student.getId();
	}

	
	// To FETCH Exam Names Based On Process Type (UPDATE PROCESS)
	public HashMap<Integer, String> converBOToTO_examNameByProcessType(
			ArrayList<ExamDefinitionBO> examNameByProcessType) {
		HashMap<Integer, String> listTO = new HashMap<Integer, String>();
		for (ExamDefinitionBO bo : examNameByProcessType) {
			listTO.put(bo.getId(), bo.getName());
		}
		listTO=(HashMap<Integer, String>) CommonUtil.sortMapByValue(listTO);
		return listTO;
	}
	
	public ArrayList<ExamSubjectTO> convertBOToTO_SubjectsCodeName(
			ArrayList<ExamSubjectTO> subjectByCourse,String sCodeName) {
		ExamSubjectTO to;
		ArrayList<ExamSubjectTO> retutnList = new ArrayList<ExamSubjectTO>();

		Iterator itr = subjectByCourse.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[0] != null && row[1] != null && row[2]!= null) {
				if (sCodeName.equalsIgnoreCase("sCode")) {
				to = new ExamSubjectTO(Integer.parseInt(row[0].toString()),
						 row[2].toString() + " "
							+ row[1].toString(),null);
				retutnList.add(to);
				}else{
					to = new ExamSubjectTO(Integer.parseInt(row[0].toString()),
							 row[1].toString() + " "
								+ row[2].toString(),null);
					retutnList.add(to);
				}
			}
		}
		Collections.sort(retutnList);
		return retutnList;
	}
	
	public Map<Integer, String> convertTOToMap_SubjectsCodeName(
			ArrayList<ExamSubjectTO> subjectByCourse) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamSubjectTO examSubjectTO : subjectByCourse) {
			map.put(examSubjectTO.getId(), examSubjectTO.getSubjectName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	public Map<Integer, String> convertTOClassMapByclassIds(List<KeyValueTO> listValues,List<Integer> classIds) {
		HashMap<Integer, String> classMap = new HashMap<Integer, String>();
		Iterator<KeyValueTO> itrr = listValues.iterator();
		while (itrr.hasNext()) {
			KeyValueTO val = (KeyValueTO) itrr.next();
			if(classIds.contains(val.getId())){
				classMap.put(val.getId(), val.getDisplay());
			}
		}
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	public HashMap<Integer, String> convertBOToTO_SecondLanguageNew(
			List<ExamSecondLanguage> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamSecondLanguage bo : listBO) {
			map.put(bo.getId(), bo.getName());
		}
		map=(HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	public HashMap<Integer, String> convertBOToTO_programme_map(
			ArrayList<ProgrammeEntryBo> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ProgrammeEntryBo bo : listBO) {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String startdate = df.format(bo.getStartDate());
			String enddate = df.format(bo.getEndDate());
			map.put(bo.getDepartmentNameEntry().getId(), bo.getDepartmentNameEntry().getDepartmentName()+"("+startdate+"_"+enddate+")");
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;

	}
	public HashMap<Integer, String> convertBOToTO_programme_code(
			ArrayList<Object[]> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Iterator<Object[]> iterator=listBO.iterator();
		while(iterator.hasNext()){
			Object[] obj=iterator.next();
			map.put((Integer)obj[0],(String) obj[1]);
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;

	}	
}
