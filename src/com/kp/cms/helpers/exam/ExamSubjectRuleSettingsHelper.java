package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.kp.cms.bo.exam.AttendanceTypeUtilBO;
import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamSubjecRuleSettingsMultipleEvaluatorBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAssignmentBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAttendanceBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsMultipleAnsScriptBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsSubInternalBO;
import com.kp.cms.handlers.exam.ExamSubjectRuleSettingsHandler;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAttendanceTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsEditTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubjectTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamSubjectRuleSettingsHelper extends ExamGenHelper {
	public List<KeyValueTO> convertBOToTO_ProgramType(
			ArrayList<ExamProgramTypeUtilBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamProgramTypeUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getProgramType()));
		}
		
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public HashMap<Integer, String> convertBOToTO_AttendanceType_HashMap(
			ArrayList<AttendanceTypeUtilBO> listBO) throws Exception {

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (AttendanceTypeUtilBO bo : listBO) {
			map.put(bo.getId(), bo.getName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;

	}

	public Map<Integer, String> convertBOToTO_SchemeNo_Map(
			List<CurriculumSchemeUtilBO> listBO) throws Exception {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		int maxValue = 0;

		for (CurriculumSchemeUtilBO bo : listBO) {
			if (maxValue < bo.getNoScheme()) {

				maxValue = bo.getNoScheme();
			}

		}
		for (CurriculumSchemeUtilBO bo : listBO)

		{
			for (int i = 1; i <= maxValue; i++) {
				map.put(bo.getCourseSchemeId(), Integer.toString(i));

			}

		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return sort(map);
	}

	@SuppressWarnings("unchecked")
	private Map<Integer, String> sort(Map<Integer, String> someMap) throws Exception {
		List mapKeys = new ArrayList(someMap.keySet());
		List mapValues = new ArrayList(someMap.values());

		someMap.clear();

		TreeSet sortedSet = new TreeSet(mapValues);

		Object[] sortedArray = sortedSet.toArray();

		int size = sortedArray.length;

		// a) Ascending sort
		for (int i = 0; i < size; i++) {
			Integer key = (Integer) mapKeys.get(mapValues
					.indexOf(sortedArray[i]));
			someMap.put(key, sortedArray[i].toString());
		}
		//someMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(someMap);
		return someMap;

	}

	@SuppressWarnings("unchecked")
	public List<ExamSubjectRuleSettingsSubInternalTO> convertBOToTO_SubInternal(
			ArrayList<ExamInternalExamTypeBO> listBO) throws Exception {
		ArrayList<ExamSubjectRuleSettingsSubInternalTO> listTO = new ArrayList<ExamSubjectRuleSettingsSubInternalTO>();
		Iterator it = listBO.iterator();
		Object[] row = null;
		while (it.hasNext()) {
			row = (Object[]) it.next();
			listTO.add(new ExamSubjectRuleSettingsSubInternalTO(row[0]
					.toString(), row[1].toString()));
		}
		Collections.sort(listTO);
		return listTO;
	}

	public List<ExamSubjectRuleSettingsAssignmentTO> convertBOToTO_Assignment(
			ArrayList<ExamAssignmentTypeMasterBO> listBO) throws Exception {
		ArrayList<ExamSubjectRuleSettingsAssignmentTO> listTO = new ArrayList<ExamSubjectRuleSettingsAssignmentTO>();
		for (ExamAssignmentTypeMasterBO bo : listBO) {
			listTO.add(new ExamSubjectRuleSettingsAssignmentTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO);
		return listTO;
	}

	public List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> convertBOToTO_MultipleAnswerScript(
			ArrayList<ExamMultipleAnswerScriptMasterBO> listBO) throws Exception {
		ArrayList<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO = new ArrayList<ExamSubjectRuleSettingsMultipleAnswerScriptTO>();
		for (ExamMultipleAnswerScriptMasterBO bo : listBO) {
			listTO.add(new ExamSubjectRuleSettingsMultipleAnswerScriptTO(
					bo.getId(), bo.getName()));
		}
		Collections.sort(listTO);
		return listTO;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ExamSubjectRuleSettingsSubjectTO> convertBOToTO_SubjectsList(
			List<Object[]> select_Subjects) throws Exception {
		ExamSubjectRuleSettingsSubjectTO to;
		ArrayList<ExamSubjectRuleSettingsSubjectTO> retutnList = new ArrayList<ExamSubjectRuleSettingsSubjectTO>();
		Iterator itr = select_Subjects.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamSubjectRuleSettingsSubjectTO();
			to.setSubid(Integer.parseInt(row[0].toString()));
			to.setSubjectName((String) row[1]);
			retutnList.add(to);
		}
		Collections.sort(retutnList);
		return retutnList;
	}

	public List<ExamSubjectRuleSettingsEditTO> convertBOToSubjectRuleSettingsEditTO(
			List<Object[]> listOfSubjects) throws Exception {

		ExamSubjectRuleSettingsEditTO to = null;
		int subjectRuleSettingsId = 0;
		int subjectId = 0;
		String courseName = null;
		String schemeName = null;
		String subjectName = null;
		String subjectCode = null;
		List<ExamSubjectRuleSettingsEditTO> listTO = null;
		if (listOfSubjects != null && listOfSubjects.size() > 0) {
			listTO = new ArrayList<ExamSubjectRuleSettingsEditTO>();
			Iterator<Object[]> it = listOfSubjects.iterator();
			while (it.hasNext()) {
				Object[] row = it.next();

				if (CommonUtil.checkForEmpty(row[0].toString())) {
					subjectRuleSettingsId = Integer.parseInt(row[0].toString());
				}

				if (CommonUtil.checkForEmpty(row[1].toString())) {
					courseName = row[1].toString();
				}
				if (CommonUtil.checkForEmpty(row[2].toString())
						&& CommonUtil.checkForEmpty(row[3].toString())) {
					schemeName = row[2].toString() + row[3].toString();
				}
				if (CommonUtil.checkForEmpty(row[4].toString())) {
					subjectName = row[4].toString();

				}

				if (CommonUtil.checkForEmpty(row[5].toString())) {
					subjectId = Integer.parseInt(row[5].toString());
				}
				if (CommonUtil.checkForEmpty(row[6].toString())) {
					subjectCode = row[6].toString();
				}
				to = new ExamSubjectRuleSettingsEditTO(subjectRuleSettingsId,
						courseName, schemeName, subjectName, subjectId, subjectCode);

				listTO.add(to);

			}
		} else {
			listTO = new ArrayList<ExamSubjectRuleSettingsEditTO>();
		}

		return listTO;
	}

	public List<ExamSubjectRuleSettingsSubInternalTO> convertSubInternalBOToSubInternalTO(
			List<ExamSubjectRuleSettingsSubInternalBO> listSubBO,
			String thORPractical) throws Exception {

		List<ExamSubjectRuleSettingsSubInternalTO> subTOList = new ArrayList<ExamSubjectRuleSettingsSubInternalTO>();
		ExamSubjectRuleSettingsSubInternalTO to = null;

		for (ExamSubjectRuleSettingsSubInternalBO bo : listSubBO) {
		String	selectBest="";
			if(bo.getExamSubjectRuleSettingsBO()!=null)
			   {
				   selectBest = (bo.getExamSubjectRuleSettingsBO()
							.getSelectBestOfPracticalInternal()==null?"":Integer.toString(bo.getExamSubjectRuleSettingsBO()
							.getSelectBestOfPracticalInternal()));
			   }

			String isTheoryPractical = null;
			String enteredMaxMark = (bo.getEnteredMaxMark() != null ? bo
					.getEnteredMaxMark().toString() : null);
			String maximumMark = (bo.getMaximumMark() != null ? bo
					.getMaximumMark().toString() : null);
			String minimumMark = (bo.getMinimumMark() != null ? bo
					.getMinimumMark().toString() : null);
			if (thORPractical.equalsIgnoreCase("theory")) {
				if (bo.getIsTheoryPractical().equalsIgnoreCase("t")) {
					isTheoryPractical = bo.getIsTheoryPractical();
					to = new ExamSubjectRuleSettingsSubInternalTO(Integer
							.toString(bo.getId()), enteredMaxMark, maximumMark,
							minimumMark, bo.getExamInternalExamTypeBO()
									.getName(), isTheoryPractical, selectBest,
							Integer.toString(bo.getInternalExamTypeId()));

					subTOList.add(to);
				}

			} else {
				
				   if(bo.getExamSubjectRuleSettingsBO()!=null)
				   {
					   selectBest = (bo.getExamSubjectRuleSettingsBO()
								.getSelectBestOfPracticalInternal()==null?"":Integer.toString(bo.getExamSubjectRuleSettingsBO()
								.getSelectBestOfPracticalInternal()));
				   }
				

				if (bo.getIsTheoryPractical().equalsIgnoreCase("p")) {
					isTheoryPractical = bo.getIsTheoryPractical();
					to = new ExamSubjectRuleSettingsSubInternalTO(Integer
							.toString(bo.getId()), enteredMaxMark, maximumMark,
							minimumMark, bo.getExamInternalExamTypeBO()
									.getName(), isTheoryPractical, selectBest,
							Integer.toString(bo.getInternalExamTypeId()));

					subTOList.add(to);
				}
			}

		}
		Collections.sort(subTOList);
		return subTOList;
	}

	public ExamSubjectRuleSettingsAttendanceTO convertSubInternalBOToAttendanceTO(
			List<ExamSubjectRuleSettingsAttendanceBO> listSubBO,
			String thORTheory) throws Exception {
		ExamSubjectRuleSettingsHandler handler = new ExamSubjectRuleSettingsHandler();
		HashMap<Integer, String> attendanceMap = handler
				.getAttendanceListHashMap();

		ExamSubjectRuleSettingsAttendanceTO attTO = null;
		if (listSubBO != null && listSubBO.size() > 0) {
			for (ExamSubjectRuleSettingsAttendanceBO bo : listSubBO) {

				if (thORTheory.equalsIgnoreCase("theory")) {

					boolean coCurricular = (bo.getIsCoCurricular() > 0) ? true
							: false;

					boolean leave = (bo.getIsLeave() > 0) ? true : false;

					if (CommonUtil.checkForEmpty(bo.getIsTheoryPractical())
							&& bo.getIsTheoryPractical().equalsIgnoreCase("t")) {
						String attendanceTypeId = "";
						if(bo.getAttendanceTypeId()!= null){
							attendanceTypeId = Integer.toString(bo.getAttendanceTypeId());
						}
						attTO = new ExamSubjectRuleSettingsAttendanceTO(Integer
								.toString(bo.getId()), attendanceTypeId,
								attendanceMap, coCurricular, bo
										.getIsTheoryPractical(), leave);
						attTO.setCheckedActiveDummyCoCurr(coCurricular);
						attTO.setCheckedActiveDummyLeave(leave);

					} else {
						attTO = new ExamSubjectRuleSettingsAttendanceTO();
						attTO.setAttendanceTypeList(attendanceMap);
					}

				} else {

					boolean coCurricular = (bo.getIsCoCurricular() > 0) ? true
							: false;

					boolean leave = (bo.getIsLeave() > 0) ? true : false;

					String attendanceType = "";
					if(bo.getAttendanceTypeUtilBO()!= null){
						attendanceType =  Integer.toString(bo.getAttendanceTypeUtilBO().getId());
					}
					
					if (CommonUtil.checkForEmpty(bo.getIsTheoryPractical())
							&& bo.getIsTheoryPractical().equalsIgnoreCase("p")) {

						attTO = new ExamSubjectRuleSettingsAttendanceTO(Integer
								.toString(bo.getId()), /*Integer.toString(bo
								.getAttendanceTypeUtilBO().getId())*/attendanceType,
								attendanceMap, coCurricular, bo
										.getIsTheoryPractical(), leave);
						attTO.setCheckedActiveDummyCoCurr(coCurricular);
						attTO.setCheckedActiveDummyLeave(leave);

					} else {
						attTO = new ExamSubjectRuleSettingsAttendanceTO();
						attTO.setAttendanceTypeList(attendanceMap);
					}
				}
			}

		} else if (listSubBO.size() == 0) {

			attTO = new ExamSubjectRuleSettingsAttendanceTO();
			attTO.setAttendanceTypeList(attendanceMap);

		}
		return attTO;
	}

	public List<ExamSubjectRuleSettingsAssignmentTO> convertAssignmentBOToAssignmentTO(
			List<ExamSubjectRuleSettingsAssignmentBO> listAssignmentBO,
			String thORPractical) throws Exception {

		List<ExamSubjectRuleSettingsAssignmentTO> listTO = new ArrayList<ExamSubjectRuleSettingsAssignmentTO>();
		ExamSubjectRuleSettingsAssignmentTO to = null;
		if (thORPractical.equalsIgnoreCase("theory")) {
			for (ExamSubjectRuleSettingsAssignmentBO bo : listAssignmentBO) {

				String maximumAssignMarks = (bo.getMaximumMark() != null) ? bo
						.getMaximumMark().toString() : null;
				String minimumAssignMarks = (bo.getMinimumMark() != null) ? bo
						.getMinimumMark().toString() : null;

				if (CommonUtil.checkForEmpty(bo.getIsTheoryPractical())
						&& bo.getIsTheoryPractical().equalsIgnoreCase("t")) {

					to = new ExamSubjectRuleSettingsAssignmentTO(Integer
							.toString(bo.getId()), maximumAssignMarks,
							minimumAssignMarks, bo
									.getExamAssignmentTypeMasterBO().getName(),
							bo.getIsTheoryPractical(), bo.getAssignmentTypeId());

					listTO.add(to);

				}
			}
		} else {
			for (ExamSubjectRuleSettingsAssignmentBO bo : listAssignmentBO) {

				String maximumAssignMarks = (bo.getMaximumMark() != null) ? bo
						.getMaximumMark().toString() : null;
				String minimumAssignMarks = (bo.getMinimumMark() != null) ? bo
						.getMinimumMark().toString() : null;

				if (CommonUtil.checkForEmpty(bo.getIsTheoryPractical())
						&& bo.getIsTheoryPractical().equalsIgnoreCase("p")) {

					to = new ExamSubjectRuleSettingsAssignmentTO(Integer
							.toString(bo.getId()), maximumAssignMarks,
							minimumAssignMarks, bo
									.getExamAssignmentTypeMasterBO().getName(),
							bo.getIsTheoryPractical(), bo.getAssignmentTypeId());

					listTO.add(to);

				}

			}
		}

		return listTO;
	}

	public ExamSubjectRuleSettingsTheoryInternalTO convertBOtoTo_ExamSubjectRuleSettingsTheoryInternalTO(
			ExamSubjectRuleSettingsBO bo,
			ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO) throws Exception {
		String theoryIntEntryMaxMarks = null;
		String theoryIntMaxMarksTotal = null;
		String theoryIntEntryMinMarks = null;
		String finalInternalMaximumMarks = (bo
				.getFinalTheoryInternalMaximumMark() != null ? bo
				.getFinalTheoryInternalMaximumMark().toString() : null);
		String finalInternalMinimumMarks = (bo
				.getFinalTheoryInternalMinimumMark() != null ? bo
				.getFinalTheoryInternalMinimumMark().toString() : null);
		String finalEntryMaximumMarks = (bo
				.getFinalTheoryInternalEnteredMaxMark() != null ? bo
				.getFinalTheoryInternalEnteredMaxMark().toString() : null);

		String selectTheBestOfTheory = (bo
				.getSelectBestOfTheoryInternal()!=null?Integer.toString(bo
						.getSelectBestOfTheoryInternal()):null);

		if (bo.getTheoryIntEntryMaxMarksTotal() != null) {

			theoryIntEntryMaxMarks = bo.getTheoryIntEntryMaxMarksTotal()
					.toString();
		}

		if (bo.getTheoryIntMaxMarksTotal() != null) {
			theoryIntMaxMarksTotal = bo.getTheoryIntMaxMarksTotal().toString();

		}

		if (bo.getTheoryIntMinMarksTotal() != null) {
			theoryIntEntryMinMarks = bo.getTheoryIntMinMarksTotal().toString();
		}

		boolean isCheckedSubInternal = (bo.getFinalTheoryInternalIsSubInternal() > 0) ? true
				: false;

		boolean isCheckedAttendanceChecked = (bo
				.getFinalTheoryInternalIsAttendance() > 0) ? true : false;

		boolean isCheckedAssignmentChecked = (bo
				.getFinalTheoryInternalIsAssignment() > 0) ? true: false;

		theoryIntTO.setCheckedActiveDummyAssignment(isCheckedAssignmentChecked);
		theoryIntTO.setCheckedActiveDummyAttendance(isCheckedAttendanceChecked);
		theoryIntTO.setCheckedActiveDummySubInt(isCheckedSubInternal);
		// -----------------------------------------------------------------
		theoryIntTO.setFinalInternalMaximumMarks(finalInternalMaximumMarks);

		// ----------------------------------------------------------------------
		// ---------------
		theoryIntTO.setFinalInternalMinimumMarks(finalInternalMinimumMarks);
		theoryIntTO.setFinalEntryMaximumMarks(finalEntryMaximumMarks);
		theoryIntTO.setTotalentryMaximumMarks(theoryIntEntryMaxMarks);
		theoryIntTO.setTotalMaximumMarks(theoryIntMaxMarksTotal);
		theoryIntTO.setTotalMinimummumMarks(theoryIntEntryMinMarks);
		theoryIntTO.setSelectTheBest(selectTheBestOfTheory);

		return theoryIntTO;
	}

	public ExamSubjectRuleSettingsTheoryESETO convertBOtoTo_ExamSubjectRuleSettingsTheoryESETO(
			List<ExamSubjectRuleSettingsMultipleAnsScriptBO> listBO,
			ExamSubjectRuleSettingsTheoryESETO theoryESETO,
			ExamSubjectRuleSettingsBO bo) throws Exception {

		ExamSubjectRuleSettingsMultipleAnswerScriptTO to = null;
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList = new ArrayList<ExamSubjectRuleSettingsMultipleAnswerScriptTO>();
		for (ExamSubjectRuleSettingsMultipleAnsScriptBO scriptBO : listBO) {
			if (CommonUtil.checkForEmpty(scriptBO.getIsTheoryPractical())
					&& scriptBO.getIsTheoryPractical().equalsIgnoreCase("t"))

			{
				String value = null;
				if (scriptBO.getValue() != null) {
					value = scriptBO.getValue().toString();
				}

				to = new ExamSubjectRuleSettingsMultipleAnswerScriptTO(Integer
						.toString(scriptBO.getId()), value, scriptBO
						.getExamMultipleAnswerScriptMasterBO().getName(),
						scriptBO.getIsTheoryPractical(), scriptBO
								.getMultipleAnswerScriptId());

				multipleAnswerScriptList.add(to);

			}
		}

		
		if (bo != null) {
			String maximumEntryMarksTheoryESE = (bo
					.getTheoryEseEnteredMaxMark() != null ? bo
					.getTheoryEseEnteredMaxMark().toString() : null);
			String maximumMarksTheoryESE = (bo.getTheoryEseMaximumMark() != null ? bo
					.getTheoryEseMaximumMark().toString()
					: null);
			String minimumMarksTheoryESE = (bo.getTheoryEseMinimumMark() != null ? bo
					.getTheoryEseMinimumMark().toString()
					: null);
			String maximumTheoryFinalMarksTheoryESE = (bo
					.getTheoryEseTheoryFinalMaximumMark() != null ? bo
					.getTheoryEseTheoryFinalMaximumMark().toString() : null);
			String minimumTheoryFinalMarksTheoryESE = (bo
					.getTheoryEseTheoryFinalMinimumMark() != null ? bo
					.getTheoryEseTheoryFinalMinimumMark().toString() : null);

			theoryESETO.setIsRegularTheoryESEChecked(((bo
					.getTheoryEseIsRegular() > 0) ? true : false));
			theoryESETO.setIsMultipleAnswerScriptsChecked((bo
				.getTheoryEseIsMultipleAnswerScript() > 0) ? true : false);
			theoryESETO.setIsMultipleEvaluatorChecked((bo
					.getTheoryEseIsMultipleEvaluator() > 0) ? true : false);
			
			theoryESETO
					.setMaximumEntryMarksTheoryESE(maximumEntryMarksTheoryESE);
			theoryESETO.setMaximumMarksTheoryESE(maximumMarksTheoryESE);
			theoryESETO.setMinimumMarksTheoryESE(minimumMarksTheoryESE);
			theoryESETO
					.setMaximumTheoryFinalMarksTheoryESE(maximumTheoryFinalMarksTheoryESE);
			theoryESETO
					.setMinimumTheoryFinalMarksTheoryESE(minimumTheoryFinalMarksTheoryESE);
		}
		theoryESETO.setMultipleAnswerScriptList(multipleAnswerScriptList);

		return theoryESETO;

	}

	public ExamSubjectRuleSettingsTheoryESETO convertBOtoTo_ExamSubjectRuleSettingsTheoryESEmulEvalTO(
			List<ExamSubjecRuleSettingsMultipleEvaluatorBO> mulEvalBOList,
			ExamSubjectRuleSettingsTheoryESETO theoryESETO) throws Exception {
				
		String typeOfEval = null;
		int count = 1;
		for (ExamSubjecRuleSettingsMultipleEvaluatorBO bo : mulEvalBOList) {
									
				theoryESETO.setNoOfEvaluations((bo.getNoOfEvaluations()==null?"":Integer
						.toString(bo.getNoOfEvaluations())));
				
				switch (count) {
				case 1:
					theoryESETO.setEvalId1((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					theoryESETO.setId1(bo.getId()==null?null:Integer.toString(bo.getId()));

					break;
				case 2:
					theoryESETO.setEvalId2((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					theoryESETO.setId2(bo.getId()==null?null:Integer.toString(bo.getId()));

					break;
				case 3:
					theoryESETO.setEvalId3((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					theoryESETO.setId3(bo.getId()==null?null:Integer.toString(bo.getId()));

					break;
				case 4:
					theoryESETO.setEvalId4((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					theoryESETO.setId4(bo.getId()==null?null:Integer.toString(bo.getId()));

					break;
				case 5:
					theoryESETO.setEvalId5((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					theoryESETO.setId5(bo.getId()==null?null:Integer.toString(bo.getId()));
					break;

				}
				if (CommonUtil.checkForEmpty(bo.getTypeOfEvaluation())
						&& bo.getTypeOfEvaluation().length() > 0) {
					if (bo.getTypeOfEvaluation().equalsIgnoreCase("Average"))
						typeOfEval = "1";
					else if (bo.getTypeOfEvaluation().equalsIgnoreCase(
							"Highest"))
						typeOfEval = "2";
					else if (bo.getTypeOfEvaluation().equalsIgnoreCase("Lowest"))
						typeOfEval = "3";
					else
						typeOfEval="";

				}
				
				theoryESETO.setTypeOfEvaluation(typeOfEval);
				count++;
			}
					
		return theoryESETO;

	}

	public ExamSubjectRuleSettingsPracticalInternalTO convertBOtoTo_ExamSubjectRuleSettingsPracticalInternalTO(
			ExamSubjectRuleSettingsBO bo,
			ExamSubjectRuleSettingsPracticalInternalTO practicalIntTO) throws Exception {

		String practicalInternalIntEntryMaxMarks = null;
		String practicalInternalMaxMarksTotal = null;
		String practicalInternalEntryMinMarks = null;
		String finalEntryMaximumMarks = (bo
				.getFinalPracticalInternalEnteredMaxMark() != null ? bo
				.getFinalPracticalInternalEnteredMaxMark().toString() : null);

		String finalPracticalInternalMinimum_mark = (bo
				.getFinalPracticalInternalMinimum_mark() != null ? bo
				.getFinalPracticalInternalMinimum_mark().toString() : null);
		String selectTheBestOfPractInt =(bo
				.getSelectBestOfPracticalInternal()!=null?Integer.toString(bo
						.getSelectBestOfPracticalInternal()):null) ;

		String finalPracticalInternalMaximumMark = (bo
				.getFinalPracticalInternalMaximumMark() != null ? bo
				.getFinalPracticalInternalMaximumMark().toString() : null);
		if (bo.getPracticalIntEntryMaxMarksTotal() != null) {

			practicalInternalIntEntryMaxMarks = bo
					.getPracticalIntEntryMaxMarksTotal().toString();
		}

		if (bo.getPracticalIntMaxMarksTotal() != null) {
			practicalInternalMaxMarksTotal = bo.getPracticalIntMaxMarksTotal()
					.toString();
		}

		if (bo.getPracticalIntMinMarksTotal() != null) {
			practicalInternalEntryMinMarks = bo.getPracticalIntMinMarksTotal()
					.toString();
		}
		
		
		boolean isCheckedSubInternal = (bo.getFinalPracticalInternalIsSubInternal() > 0) ? true
				: false;

		boolean isCheckedAttendanceChecked = (bo
				.getFinalPracticalInternalIsAttendance() > 0) ? true : false;

		boolean isCheckedAssignmentChecked = (bo
				.getFinalPracticalInternalIsAssignment() > 0) ? true: false;
		
		practicalIntTO.setCheckedActiveDummySubInt(isCheckedSubInternal);
		practicalIntTO.setCheckedActiveDummyAttendance(isCheckedAttendanceChecked);
		practicalIntTO.setCheckedActiveDummyAssignment(isCheckedAssignmentChecked);

		practicalIntTO.setFinalEntryMaximumMarks(finalEntryMaximumMarks);
		practicalIntTO
				.setFinalInternalMinimumMarks(finalPracticalInternalMinimum_mark);
		practicalIntTO
				.setFinalInternalMaximumMarks(finalPracticalInternalMaximumMark);
		practicalIntTO
				.setTotalentryMaximumMarks(practicalInternalIntEntryMaxMarks);
		practicalIntTO.setTotalMaximumMarks(practicalInternalMaxMarksTotal);
		practicalIntTO.setTotalMinimummumMarks(practicalInternalEntryMinMarks);
		practicalIntTO.setSelectTheBest(selectTheBestOfPractInt);
		return practicalIntTO;
	}

	public ExamSubjectRuleSettingsPracticalESETO convertBOtoTo_ExamSubjectRuleSettingsPracticalESETO(
			List<ExamSubjectRuleSettingsMultipleAnsScriptBO> listBO,
			ExamSubjectRuleSettingsPracticalESETO practicalESETO,
			ExamSubjectRuleSettingsBO bo) throws Exception {

		ExamSubjectRuleSettingsMultipleAnswerScriptTO to = null;
		List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList = new ArrayList<ExamSubjectRuleSettingsMultipleAnswerScriptTO>();
		for (ExamSubjectRuleSettingsMultipleAnsScriptBO scriptBO : listBO) {
			if (CommonUtil.checkForEmpty(scriptBO.getIsTheoryPractical())
					&& scriptBO.getIsTheoryPractical().equalsIgnoreCase("p")) {
				String value = null;
				if (scriptBO.getValue() != null) {
					value = scriptBO.getValue().toString();
				}

				to = new ExamSubjectRuleSettingsMultipleAnswerScriptTO(Integer
						.toString(scriptBO.getId()), value, scriptBO
						.getExamMultipleAnswerScriptMasterBO().getName(),
						scriptBO.getIsTheoryPractical(), scriptBO
								.getMultipleAnswerScriptId());

				multipleAnswerScriptList.add(to);

			}
		}

			
		
		String maximumEntryMarksPracticalESE = (bo
				.getPracticalEseEnteredMaxMark() != null ? bo
				.getPracticalEseEnteredMaxMark().toString() : null);

		String maximumMarksPracticalESE = (bo.getPracticalEseMaximumMark() != null ? bo
				.getPracticalEseMaximumMark().toString()
				: null);
		
		String minimumMarksPracticalESE = (bo.getPracticalEseMinimumMark() != null ? bo
				.getPracticalEseMinimumMark().toString()
				: null);

		String practicalEseTheoryFinalMaximumMark = (bo
				.getPracticalEseTheoryFinalMaximumMark() != null ? bo
				.getPracticalEseTheoryFinalMaximumMark().toString() : null);
		String minimumTheoryFinalMarksPracticalESE = (bo
				.getPracticalEseTheoryFinalMinimumMark() != null ? bo
				.getPracticalEseTheoryFinalMinimumMark().toString() : null);

		practicalESETO
				.setMaximumEntryMarksPracticalESE(maximumEntryMarksPracticalESE);
		practicalESETO.setMaximumMarksPracticalESE(maximumMarksPracticalESE);
		practicalESETO.setMinimumMarksPracticalESE(minimumMarksPracticalESE);
		practicalESETO
				.setMaximumTheoryFinalMarksPracticalESE(practicalEseTheoryFinalMaximumMark);
		practicalESETO
				.setMinimumTheoryFinalMarksPracticalESE(minimumTheoryFinalMarksPracticalESE);
		practicalESETO.setMultipleAnswerScriptList(multipleAnswerScriptList);

		practicalESETO.setIsRegularTheoryESEChecked(((bo != null && bo
				.getPracticalEseIsRegular() > 0) ? true : false));
		practicalESETO.setIsMultipleAnswerScriptsChecked((bo != null && bo
			.getPracticalEseIsMultipleAnswerScript() > 0) ? true : false);
		practicalESETO.setIsMultipleEvaluatorChecked((bo != null && bo
				.getPracticalEseIsMultipleEvaluator() > 0) ? true : false);
		return practicalESETO;
	}

	public ExamSubjectRuleSettingsPracticalESETO convertBOtoTo_ExamSubjectRuleSettingsPracticalESEmulEvalTO(
			List<ExamSubjecRuleSettingsMultipleEvaluatorBO> mulEvalBOList,
			ExamSubjectRuleSettingsPracticalESETO practicalESETO) throws Exception {

		int count = 1;
		String typeOfEval = null;
		for (ExamSubjecRuleSettingsMultipleEvaluatorBO bo : mulEvalBOList) {
			if (CommonUtil.checkForEmpty(bo.getIsTheoryPractical())
					&& bo.getIsTheoryPractical().equalsIgnoreCase("p")) {
				switch (count) {
				case 1:
					practicalESETO.setEvalId1((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					practicalESETO.setId1(bo.getId()==null?null:Integer.toString(bo.getId()));
					break;
				case 2:
					practicalESETO.setEvalId2((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					practicalESETO.setId2(bo.getId()==null?null:Integer.toString(bo.getId()));
					break;
				case 3:
					practicalESETO.setEvalId3((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					practicalESETO.setId3(bo.getId()==null?null:Integer.toString(bo.getId()));
					break;
				case 4:
					practicalESETO.setEvalId4((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					practicalESETO.setId4(bo.getId()==null?null:Integer.toString(bo.getId()));
					break;
				case 5:
					practicalESETO.setEvalId5((bo.getEvaluatorId()==null?"":Integer
							.toString(bo.getEvaluatorId())));
					practicalESETO.setId5(bo.getId()==null?null:Integer.toString(bo.getId()));
					break;

				}

				if (CommonUtil.checkForEmpty(bo.getTypeOfEvaluation())
						&& bo.getTypeOfEvaluation().length() > 0) {
					if (bo.getTypeOfEvaluation().equalsIgnoreCase("Average"))
						typeOfEval = "1";
					else if (bo.getTypeOfEvaluation().equalsIgnoreCase(
							"Highest"))
						typeOfEval = "2";
					else if (bo.getTypeOfEvaluation().equalsIgnoreCase("Lowest"))
						typeOfEval = "3";
					else
						typeOfEval="";

				}

				practicalESETO.setTypeOfEvaluation(typeOfEval);
				practicalESETO.setNoOfEvaluations((bo.getNoOfEvaluations()==null?"":Integer
						.toString(bo.getNoOfEvaluations())));
			

			}
			count++;
		}

		return practicalESETO;

	}

	public ExamSubjectRuleSettingsTO converBOTosubjectFinalTO(
			ExamSubjectRuleSettingsBO bo) throws Exception {
		ExamSubjectRuleSettingsTO subjectFinalTO = null;

		String subjectFinalMinimum = null;
		String subjectFinalMaximum = null;
		String subjectFinalValuated = null;
		boolean isTheoryExamChecked = (bo.getSubjectFinalIsTheoryExam() > 0) ? true
				: false;

		boolean isPracticalExamChecked = (bo.getSubjectFinalIsPracticalExam() > 0) ? true
				: false;

		boolean isInternalExamChecked = (bo.getSubjectFinalIsInternalExam() > 0) ? true
				: false;

		boolean isAttendanceChecked = (bo.getSubjectFinalIsAttendance() > 0) ? true
				: false;

		
		if (bo.getSubjectFinalMinimum() != null) {
			subjectFinalMinimum = bo.getSubjectFinalMinimum().toString();
		}

		if (bo.getSubjectFinalMaximum() != null) {
			subjectFinalMaximum = bo.getSubjectFinalMaximum().toString();
		}

		if (bo.getSubjectFinalValuated() != null) {
			subjectFinalValuated = bo.getSubjectFinalValuated().toString();
		}

				
		subjectFinalTO= new ExamSubjectRuleSettingsTO(subjectFinalMaximum,subjectFinalMinimum,subjectFinalValuated,isTheoryExamChecked,isPracticalExamChecked,
				isInternalExamChecked,isAttendanceChecked);
		
		
		

		return subjectFinalTO;
	}
	

}
