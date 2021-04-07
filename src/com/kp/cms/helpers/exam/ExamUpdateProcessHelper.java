package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.exam.ApplicantSubjectGroupUtilBO;
import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryBO;
import com.kp.cms.bo.exam.ExamStudentDetentionDetailsBO;
import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentInternalFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSecondLangHistoryBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.bo.exam.ExamSupplStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.bo.exam.ExamUpdateCommonSubjectGroupBO;
import com.kp.cms.handlers.exam.ExamUpdateProcessHandler;
import com.kp.cms.to.exam.BatchClassTO;
import com.kp.cms.to.exam.ExamUpdateProcessMapTO;
import com.kp.cms.to.exam.ExamUpdateProcessTO;
import com.kp.cms.to.exam.StudentMarkDetailsTO;
import com.kp.cms.to.exam.SubInfoTO;
import com.kp.cms.to.exam.SupplementaryDataTO;
import com.kp.cms.transactionsimpl.exam.ExamUpdateProcessImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamUpdateProcessHelper extends ExamGenHelper {

	ExamUpdateProcessImpl impl = new ExamUpdateProcessImpl();

	// To FETCH the joining batch & classes based on the process selected
	public ArrayList<BatchClassTO> convertBOToTO(List<Object[]> list) {
		ArrayList<BatchClassTO> listOfClasses = new ArrayList<BatchClassTO>();
		BatchClassTO to = null;
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] objects = (Object[]) iterator.next();
				to = new BatchClassTO();
				if (objects[1] != null) {
					to.setClassName(objects[1].toString());
				}
				if (objects[2] != null) {
					to.setJoiningBatch(objects[2].toString());
				}
				if (objects[0] != null) {
					to.setClassId(objects[0].toString());
				}
				if (objects[3] != null) {
					to.setSemester(Integer.parseInt(objects[3].toString()));
				}
				
				listOfClasses.add(to);
			}
		}
		Collections.sort(listOfClasses);
		return listOfClasses;

	}

	// ================= PROCESS - OVERALL - REGULAR =================

	// Details of Evaluation
	public ArrayList<SubInfoTO> convertToTO_SubInfoTO(
			List<Object[]> get_type_of_evaluation) {

		// s.subjectId, s.theoryEseEnteredMaxMark, s.theoryEseMaximumMark,
		// s.practicalEseEnteredMaxMark,"
		// + " s.practicalEseMaximumMark, s.subjectUtilBO.isTheoryPractical,"
		// + " s.theoryEseMinimumMark , s.practicalEseMinimumMark"

		ArrayList<SubInfoTO> SubInfoList = new ArrayList<SubInfoTO>();
		Iterator itr = get_type_of_evaluation.iterator();
		while (itr.hasNext()) {
			SubInfoTO sTo = new SubInfoTO();
			Object[] row = (Object[]) itr.next();
			if (row[0] != null) {
				sTo.setSubjectId(Integer.parseInt(row[0].toString()));
			}
			if (row[1] != null) {
				sTo.setTheoryEseEnteredMaxMark((row[1].toString()));
			}
			if (row[2] != null) {
				sTo.setTheoryEseMaximumMark((row[2].toString()));
			}
			if (row[3] != null) {
				sTo.setPracticalEseEnteredMaxMark((row[3].toString()));
			}
			if (row[4] != null) {
				sTo.setPracticalEseMaximumMark((row[4].toString()));
			}
			if (row[5] != null) {
				sTo.setIsTheoryPractical((row[5].toString()));
			}

			if (row[6] != null) {
				sTo.setTheoryEseMinimumMark((row[6].toString()));
			}
			if (row[7] != null) {
				sTo.setPracticalEseMinimumMark((row[7].toString()));
			}
			if (row[8] != null) {
				sTo.setTypeOfEvaluation((row[8].toString()));
			}
			SubInfoList.add(sTo);
		}
		Collections.sort(SubInfoList);
		return SubInfoList;
	}

	// To determine PASS/FAIL status of a student
	public ArrayList<ExamStudentFinalMarkDetailsBO> set_details(
			Integer classId, Integer examId, Integer studentId,
			ArrayList<StudentMarkDetailsTO> stuMarkDetails,
			HashMap<Integer, ArrayList<SubInfoTO>> evaluationDetailsT,
			HashMap<Integer, ArrayList<SubInfoTO>> evaluationDetailsP) {

		ExamStudentFinalMarkDetailsBO bo = null;
		ArrayList<ExamStudentFinalMarkDetailsBO> retlist = new ArrayList<ExamStudentFinalMarkDetailsBO>();

		if (stuMarkDetails != null && stuMarkDetails.size() > 0) {
			for (StudentMarkDetailsTO studentMarkDetailsTO : stuMarkDetails) {
				bo = new ExamStudentFinalMarkDetailsBO();
				bo.setExamId(examId);
				bo.setStudentId(studentId);
				bo.setClassId(classId);
				bo.setSubjectId(studentMarkDetailsTO.getSubjectId());
				bo.setLastModifiedDate(new Date());
				if (evaluationDetailsT != null && evaluationDetailsT.size() > 0) {
					Integer subjectId = 0;
					if (studentMarkDetailsTO != null) {
						subjectId = studentMarkDetailsTO.getSubjectId();
					}

					ArrayList<SubInfoTO> sToList = evaluationDetailsT
							.get(subjectId);

					if (sToList != null) {
						Iterator<SubInfoTO> iterator = sToList.iterator();
						while (iterator.hasNext()) {
							SubInfoTO sTo = iterator.next();
							if (sTo != null) {
								bo.setSubjectTheoryMark(sTo
										.getTheoryEseMinimumMark());

								if ("t".equalsIgnoreCase(sTo
										.getIsTheoryPractical())
										|| "b".equalsIgnoreCase(sTo
												.getIsTheoryPractical())) {
									bo
											.setStudentTheoryMarks(getFinalTheoryMark(
													sTo, studentMarkDetailsTO));
									if (bo.getStudentTheoryMarks() != null
											&& bo.getStudentTheoryMarks()
													.trim().length() > 0
											&& bo.getSubjectTheoryMark() != null
											&& bo.getSubjectTheoryMark().trim()
													.length() > 0) {
										if(StringUtils.isAlpha(bo.getStudentTheoryMarks().trim())){
											bo.setPassOrFail("fail");
										}
										else
										if ((Double.parseDouble(bo
												.getStudentTheoryMarks()) >= Double
												.parseDouble(bo
														.getSubjectTheoryMark()))) {
											bo.setPassOrFail("pass");
										} else {
											bo.setPassOrFail("fail");
										}

									}

								}
							}

						}
					}
				}
				if (evaluationDetailsP != null && evaluationDetailsP.size() > 0) {

					Integer subjectId = 0;
					if (studentMarkDetailsTO != null) {
						subjectId = studentMarkDetailsTO.getSubjectId();
					}

					ArrayList<SubInfoTO> sToListP = evaluationDetailsP
							.get(subjectId);

					if (sToListP != null) {
						Iterator<SubInfoTO> iterator = sToListP.iterator();
						while (iterator.hasNext()) {
							SubInfoTO sTo = (SubInfoTO) iterator.next();
							bo.setSubjectPracticalMark(sTo
									.getPracticalEseMinimumMark());

							if ("p"
									.equalsIgnoreCase(sTo
											.getIsTheoryPractical())
									|| "b".equalsIgnoreCase(sTo
											.getIsTheoryPractical())) {
								bo
										.setStudentPracticalMarks(getFinalPracticalMark(
												sTo, studentMarkDetailsTO));

								if (bo.getStudentPracticalMarks() != null
										&& bo.getStudentPracticalMarks().trim()
												.length() > 0
										&& bo.getSubjectPracticalMark() != null
										&& bo.getSubjectPracticalMark().trim()
												.length() > 0) {
									if(StringUtils.isAlpha(bo.getStudentPracticalMarks().trim())){
										bo.setPassOrFail("fail");
									}else
									if ((Double.parseDouble(bo
											.getStudentPracticalMarks()) >= Double
											.parseDouble(bo
													.getSubjectPracticalMark()))) {
										bo.setPassOrFail("pass");
									} else {
										bo.setPassOrFail("fail");
									}

								}

							}
						}
					}
				}
				retlist.add(bo);

			}
		}
		
		return retlist;
	}

	// To get the final theory marks
	private String getFinalTheoryMark(SubInfoTO sTo,
			StudentMarkDetailsTO studentMarkDetailsTO) {
		String marks = "";

		if ("Highest".equalsIgnoreCase(studentMarkDetailsTO
				.getTheoryPreferred())) {

			marks = calculateFinalMarks(studentMarkDetailsTO.getMaxthoery(),
					sTo.getTheoryEseEnteredMaxMark(), sTo
							.getTheoryEseMaximumMark());

		} else {

			if ("Highest".equalsIgnoreCase(sTo.getTypeOfEvaluation())) {

				marks = calculateFinalMarks(
						studentMarkDetailsTO.getMaxthoery(), sTo
								.getTheoryEseEnteredMaxMark(), sTo
								.getTheoryEseMaximumMark());
			} else if ("Lowest".equalsIgnoreCase(sTo.getTypeOfEvaluation())) {
				marks = calculateFinalMarks(
						studentMarkDetailsTO.getMinthoery(), sTo
								.getTheoryEseEnteredMaxMark(), sTo
								.getTheoryEseMaximumMark());
			} else if ("Average".equalsIgnoreCase(sTo.getTypeOfEvaluation())) {
				marks = calculateFinalMarks(
						studentMarkDetailsTO.getAvgthoery(), sTo
								.getTheoryEseEnteredMaxMark(), sTo
								.getTheoryEseMaximumMark());
			}
		}
		return marks;
	}

	// To get the final practical marks
	private String getFinalPracticalMark(SubInfoTO sTo,
			StudentMarkDetailsTO studentMarkDetailsTO) {
		String marks = "";
		if ("Highest".equalsIgnoreCase(studentMarkDetailsTO
				.getPracticalPreferred())) {

			marks = calculateFinalMarks(studentMarkDetailsTO.getMaxpractical(),
					sTo.getPracticalEseEnteredMaxMark(), sTo
							.getPracticalEseMaximumMark());
		} else {

			if ("Highest".equalsIgnoreCase(sTo.getTypeOfEvaluation())) {

				marks = calculateFinalMarks(studentMarkDetailsTO
						.getMaxpractical(),
						sTo.getPracticalEseEnteredMaxMark(), sTo
								.getPracticalEseMaximumMark());
			} else if ("Lowest".equalsIgnoreCase(sTo.getTypeOfEvaluation())) {
				marks = calculateFinalMarks(studentMarkDetailsTO
						.getMinpractical(),
						sTo.getPracticalEseEnteredMaxMark(), sTo
								.getPracticalEseMaximumMark());
			} else if ("Average".equalsIgnoreCase(sTo.getTypeOfEvaluation())) {
				marks = calculateFinalMarks(studentMarkDetailsTO
						.getAvgpractical(),
						sTo.getPracticalEseEnteredMaxMark(), sTo
								.getPracticalEseMaximumMark());
			}
		}
		return marks;
	}

	// Marks calculation of a subject against max marks
	private String calculateFinalMarks(String maxobtained,
			String EnteredMaxMark, String MaximumMark) {
		
		if(StringUtils.isAlpha(maxobtained)){
			return maxobtained;
		}
		
		if (EnteredMaxMark != null && !EnteredMaxMark.equals(0)
				&& maxobtained != null && MaximumMark != null) {
			/*return Double.toString(Math
					.round((Double.parseDouble(maxobtained) / Double
							.parseDouble(EnteredMaxMark))
							* Double.parseDouble(MaximumMark)));*/
			
			Double ob = Double.parseDouble(maxobtained);
			Double ent = Double.parseDouble(EnteredMaxMark);
			Double max = Double.parseDouble(MaximumMark);
			double tot = ob*(max/ent); 
			String val = Double.toString(Math.round(tot));
			return val;
			
		} else {

			return maxobtained;
		}
	}

	// =============== PROCESS - UPDATE DETENTION ====================

	public ExamStudentDetentionDetailsBO getExamStudentDetentionDetailsBO(
			String[] value, int failedSubjects, Integer stuId,
			ArrayList<Integer> subjectsList, int schemeNo) {

		int totalSubjects = 1;
		if (subjectsList != null) {
			totalSubjects = subjectsList.size();
		}

		ExamStudentDetentionDetailsBO bo = null;
		if (value[0] != null && value[0].contains("maxBacLogNumber")) {

			if (value[1] != null && value[1].length() > 0) {
				if (failedSubjects >= Integer.parseInt(value[1])) {
					bo = new ExamStudentDetentionDetailsBO();
					bo.setStudentId(stuId);
					bo.setDetentionDate(new Date());
					bo.setSchemeNo(schemeNo);
					bo
							.setReason("Promotion criteria not satisfied Number of Subjects Failed - "
									+ failedSubjects + " / " + totalSubjects);
				}
			}
		} else {

			float percentage = 0;
			if (totalSubjects > 0) {
				percentage = ((float) failedSubjects / totalSubjects) * 100;
			}
			if (value != null) {
				if (value[1] != null && value[1].length() > 0) {
					if (percentage > Double.parseDouble(value[1])) {
						bo = new ExamStudentDetentionDetailsBO();
						bo.setStudentId(stuId);
						bo.setDetentionDate(new Date());
						bo.setSchemeNo(schemeNo);
						bo
								.setReason("Promotion criteria not satisfied Number of Subjects Failed - "
										+ failedSubjects
										+ " / "
										+ totalSubjects);
					}
				}
			}
		}

		return bo;
	}

	public ExamStudentDetentionDetailsBO getDetentionBo(Integer stuId,
			Integer schemeNo, boolean feeStatus) {

		if (!feeStatus) {
			ExamStudentDetentionDetailsBO bo = new ExamStudentDetentionDetailsBO();
			bo.setStudentId(stuId);
			bo.setDetentionDate(new Date());
			bo.setSchemeNo(schemeNo);
			bo.setReason("Promotion criteria not satisfied");
			return bo;
		}
		return null;
	}

	public ArrayList<ExamUpdateProcessTO> convertListToArrayList(
			List<Object[]> get_internalExamId_forExam) {
		ExamUpdateProcessTO to = null;
		ArrayList<ExamUpdateProcessTO> list = new ArrayList<ExamUpdateProcessTO>();
		if (get_internalExamId_forExam != null
				&& get_internalExamId_forExam.size() > 0) {
			for (Iterator iterator = get_internalExamId_forExam.iterator(); iterator
					.hasNext();) {
				to = new ExamUpdateProcessTO();
				Object[] objects = (Object[]) iterator.next();
				if (objects[0] != null) {
					to.setInternalExamId(Integer
							.parseInt(objects[0].toString()));
				}
				if (objects[1] != null) {
					to.setInternalExamTypeId(Integer.parseInt(objects[1]
							.toString()));
				}
				list.add(to);
			}
		}
		Collections.sort(list);
		return list;
	}

	public ArrayList<ExamStudentInternalFinalMarkDetailsBO> set_details_Internal(
			Integer clsId, Integer stuId,
			List<Object[]> internalMarksOfStudent, Integer internalExamTypeId,
			Integer internalExamId) {

		ExamStudentInternalFinalMarkDetailsBO bo = null;
		ArrayList<ExamStudentInternalFinalMarkDetailsBO> retlist = new ArrayList<ExamStudentInternalFinalMarkDetailsBO>();
		if (internalMarksOfStudent != null) {
			for (Iterator iterator = internalMarksOfStudent.iterator(); iterator
					.hasNext();) {
				Object[] obj = (Object[]) iterator.next();
				bo = new ExamStudentInternalFinalMarkDetailsBO();
				if (internalExamTypeId != null) {
					bo.setInternalExamTypeId(internalExamTypeId);
				}
				bo.setStudentId(stuId);
				bo.setClassId(clsId);
				bo.setExamId(internalExamId);
				if (obj[0] != null) {
					bo.setSubjectId(Integer.parseInt(obj[0].toString()));
				}
				if (obj[1] != null) {
					bo.setStudentTheoryInternalMarks(obj[1].toString());
				}
				if (obj[2] != null) {
					bo.setStudentPracticalInternalMarks(obj[2].toString());
				}
				retlist.add(bo);
			}
		}
		
		return retlist;
	}

	// ============== PROCESS - PROMOTION =================

	public ArrayList<ExamStudentSubGrpHistoryBO> convert_prev_subjGrp(
			List<Object[]> prevSubjGrpForStudent) {

		ExamStudentSubGrpHistoryBO bo = null;
		ArrayList<ExamStudentSubGrpHistoryBO> list = new ArrayList<ExamStudentSubGrpHistoryBO>();
		for (Iterator iterator = prevSubjGrpForStudent.iterator(); iterator
				.hasNext();) {
			bo = new ExamStudentSubGrpHistoryBO();
			Object[] objects = (Object[]) iterator.next();
			if (objects[0] != null) {
				bo.setStudentId((Integer) objects[0]);
			}
			if (objects[1] != null) {
				bo.setSubjectGroupId((Integer) objects[1]);
			}

			if (objects[2] != null) {
				bo.setSchemeNo((Integer) objects[2]);
			}
			list.add(bo);
		}

		return list;

	}

	public ArrayList<ApplicantSubjectGroupUtilBO> convert_next_subjGrp(
			List<Object[]> nextSubjGrpForStudent, Integer secondLanguage) {

		ApplicantSubjectGroupUtilBO bo = null;
		ArrayList<ApplicantSubjectGroupUtilBO> list = new ArrayList<ApplicantSubjectGroupUtilBO>();
		for (Iterator iterator = nextSubjGrpForStudent.iterator(); iterator
				.hasNext();) {
			boolean flag = false;
			bo = new ApplicantSubjectGroupUtilBO();
			Object[] objects = (Object[]) iterator.next();
			if (objects[0] != null) {
				bo.setAdmnApplnId((Integer) objects[0]);
			}
			if (objects[1] != null) {
				bo.setSubjectGroupId((Integer) objects[1]);
			}
			if (objects[2] != null && objects[2].toString().equals("1")) {
				// common subject group
				flag = true;

			}
			if (objects[3] != null && secondLanguage != null) {
				// match second language of student against subject group
				int secLanId = Integer.parseInt(objects[3].toString());
				if (secondLanguage == secLanId) {
					flag = true;

				}
			}
			if (flag) {
				list.add(bo);
			}
		}

		return list;
	}

	public ExamStudentSecondLangHistoryBO convertToBo(Integer stuId,
			Integer secondLanguageHistory) {
		if (secondLanguageHistory != null) {
			return new ExamStudentSecondLangHistoryBO(stuId,
					secondLanguageHistory);
		} else {
			return null;
		}

	}

	public ArrayList<Integer> getSecondLanugeData(
			HashMap<Integer, ArrayList<Integer>> secondLangForSubGrp) {
		if (secondLangForSubGrp != null && secondLangForSubGrp.size() > 1) {
			return secondLangForSubGrp.get(2);
		}
		return null;
	}

	public ArrayList<ExamUpdateCommonSubjectGroupBO> convertCommonSubjGrp(
			Integer clsId, Integer stuId, ArrayList<Integer> commonSubjectGroup) {
		ArrayList<ExamUpdateCommonSubjectGroupBO> examUpdateBOList = new ArrayList<ExamUpdateCommonSubjectGroupBO>();

		for (Integer subGrpId : commonSubjectGroup) {
			examUpdateBOList.add(new ExamUpdateCommonSubjectGroupBO(clsId,
					stuId, subGrpId));
		}

		return examUpdateBOList;
	}

	public HashMap<Integer, ExamUpdateProcessMapTO> getAttendenceListForStudent(
			List<Object[]> totalHoursHeldList,
			List<Object[]> studentAttendenceList) {
		HashMap<Integer, ExamUpdateProcessMapTO> attendenceMap = new HashMap<Integer, ExamUpdateProcessMapTO>();
		ExamUpdateProcessMapTO to = null;
		if (studentAttendenceList != null) {
			for (Iterator iterator = studentAttendenceList.iterator(); iterator
					.hasNext();) {
				Object[] row = (Object[]) iterator.next();
				to = new ExamUpdateProcessMapTO();
				if (row[1] != null) {
					to.setSumOfStudentsPresent(Integer.parseInt(row[1]
							.toString()));
				}
				if (row[2] != null) {
					to.setSumOfStudentOnLeave(Integer.parseInt(row[2]
							.toString()));
				}
				if (row[3] != null) {
					to.setSumOfStudentOnCoCurricularLeave(Integer
							.parseInt(row[3].toString()));
				}
				if (row[0] != null) {
					attendenceMap.put(Integer.parseInt(row[0].toString()), to);
				}
			}
		}
		if (totalHoursHeldList != null) {
			for (Iterator iterator = totalHoursHeldList.iterator(); iterator
					.hasNext();) {
				Object[] row = (Object[]) iterator.next();
				if (row[0] != null) {
					if (attendenceMap.containsKey(Integer.parseInt(row[0]
							.toString()))) {
						to = attendenceMap.remove(Integer.parseInt(row[0]
								.toString()));
						if (row[1] != null) {
							to.setTotalClassHourHeld(Integer.parseInt(row[1]
									.toString()));
						}
						attendenceMap.put(Integer.parseInt(row[0].toString()),
								to);
					}
				}

			}
		}
		attendenceMap = (HashMap<Integer, ExamUpdateProcessMapTO>) CommonUtil.sortMapByValue(attendenceMap);
		return attendenceMap;
	}

	// ==========================================================================
	// =========================================================================
	// ================ PROCESS - SUPPLEMENTARY DATA CREATION ==============

	// To get the chance of the student for a particular subject
	public HashMap<Integer, Integer> convertListToHashMap(
			List<Object[]> chanceOfStudent) {

		HashMap<Integer, Integer> listTO = new HashMap<Integer, Integer>();
		Iterator itr = chanceOfStudent.iterator();
		while (itr.hasNext()) {
			Object object[] = (Object[]) itr.next();
			listTO.put((Integer) object[0], (Integer) object[1]);

		}
		listTO = (HashMap<Integer, Integer>) CommonUtil.sortMapByValue(listTO);
		return listTO;
	}

	public ArrayList set_details_supplementary(Integer classId, Integer examId,
			Integer studentId, ArrayList<StudentMarkDetailsTO> stuMarkDetails,
			HashMap<Integer, Integer> chanceList, List<Object[]> list,
			boolean maxFail, Integer schemeNo, Integer subjectTotalMarks,
			Integer academicYear) {

		ExamSupplStudentFinalMarkDetailsBO bo = null;
		ExamSupplementaryImprovementApplicationBO bo1 = null;
		ExamInternalMarkSupplementaryBO supplyBo = null;
		SupplementaryDataTO to = null;
		ArrayList retlist = new ArrayList();
		boolean flag_SplSuppl = true;
		// ----

		Integer studentFinalMarks = 0;
		List<Object[]> courseScheme = new ExamUpdateProcessHandler()
				.get_course_schemeNo_forClass(classId);
		Integer courseId = 0;
		if (courseScheme != null && courseScheme.size() > 0) {
			Object[] courseId_SchemeNo = courseScheme.get(0);
			courseId = (Integer) courseId_SchemeNo[0];

		}
		BigDecimal requiredAggrePercentage = impl
				.getAggregatePassPercentage(courseId);
		// ----

		if (impl.getExamType(examId) && maxFail) {
			flag_SplSuppl = false;
		}
		int subId = 0;
		if (flag_SplSuppl) {
			HashMap<Integer, SupplementaryDataTO> supplementaryData = gethashMapFromList(list);
			if (stuMarkDetails != null && stuMarkDetails.size() > 0) {
				for (StudentMarkDetailsTO studentMarkDetailsTO : stuMarkDetails) {

					subId = studentMarkDetailsTO.getSubjectId();
					// ----
					studentFinalMarks = getStudentFinalMark(classId, subId,
							academicYear, studentId, courseId);

					// ----
					int flag2 = 0;
					int flag = 0;
					bo1 = new ExamSupplementaryImprovementApplicationBO();
					bo1.setExamId(examId);
					bo1.setStudentId(studentId);
					bo1.setSchemeNo(schemeNo);
					bo1.setSubjectId(studentMarkDetailsTO.getSubjectId());

					bo = new ExamSupplStudentFinalMarkDetailsBO();
					bo.setExamId(examId);
					bo.setStudentId(studentId);
					bo.setClassId(classId);
					// bo.setSchemeNo(schemeNo);
					bo.setSubjectId(studentMarkDetailsTO.getSubjectId());
					if (chanceList.containsKey(studentMarkDetailsTO
							.getSubjectId())) {
						Integer chanceValue = chanceList
								.get(studentMarkDetailsTO.getSubjectId());
						if (chanceValue != null) {
							chanceValue = chanceValue + 1;
							bo.setChance(chanceValue);
						}
					} else {
						bo.setChance(1);
					}
					if (!impl.getPassOrFailInternal(studentId,
							studentMarkDetailsTO.getSubjectId())) {

						supplyBo = new ExamInternalMarkSupplementaryBO();
						supplyBo.setStudentId(studentId);
						supplyBo.setSubjectId(studentMarkDetailsTO
								.getSubjectId());
						retlist.add(supplyBo);
						flag = 1;
					}
					if (!impl.getPassOrFailRegular(studentId,
							studentMarkDetailsTO.getSubjectId())) {
						if (supplementaryData.containsKey(studentMarkDetailsTO
								.getSubjectId())) {
							to = supplementaryData.get(studentMarkDetailsTO
									.getSubjectId());
							bo1.setIsSupplementary(1);
							bo1.setIsFailedTheory(to.getTheoryStatus());
							bo1.setIsFailedPractical(to.getPracticalStatus());
							if (chanceList.containsKey(studentMarkDetailsTO
									.getSubjectId())) {
								Integer chanceValue = chanceList
										.get(studentMarkDetailsTO
												.getSubjectId());
								if (chanceValue != null) {
									chanceValue = chanceValue + 1;
									bo1.setChance(chanceValue);
								}
							} else {
								bo1.setChance(1);
							}
						}
						flag2 = 1;
						retlist.add(bo1);
						flag = 1;
					}

					if (studentFinalMarks != null && subjectTotalMarks != null) {
						Double studentPercentage = Double.valueOf(
								(studentFinalMarks * 100) / subjectTotalMarks);

						if (requiredAggrePercentage != null
								&& studentPercentage != null) {
							if ((new BigDecimal(studentPercentage.toString())
									.intValue()) < (new BigDecimal(
									requiredAggrePercentage.toString())
									.intValue())) {

								if (flag2 == 0) {
									if (chanceList
											.containsKey(studentMarkDetailsTO
													.getSubjectId())) {
										Integer chanceValue = chanceList
												.get(studentMarkDetailsTO
														.getSubjectId());
										if (chanceValue != null) {
											chanceValue = chanceValue + 1;
											bo1.setChance(chanceValue);
										}
									} else {
										bo1.setChance(1);
									}
									bo1.setIsImprovement(1);
									retlist.add(bo1);

								}
							}
						}
					}

					if (flag == 1) {
						retlist.add(bo);
					}

				}

			}
		}
		
		return retlist;

	}

	public ArrayList setSuppDetails(ArrayList<StudentMarkDetailsTO> stuMarkDetails, HashMap<Integer, Integer> chanceList,
			Integer studentId,	Integer classId, Integer academicYear, Integer subjectTotalMarks, Integer examId) {

		ExamSupplementaryImprovementApplicationBO examSupplementaryImprovementApplicationBO = null;
		SupplementaryDataTO to = null;
		ArrayList retlist = new ArrayList();
		boolean flag_SplSuppl = true;
		// ----
		Integer studentFinalMarks = 0;
		List<Object[]> courseScheme = new ExamUpdateProcessHandler().get_course_schemeNo_forClass(classId);
		Integer courseId = 0, schemeNo = 0;
		if (courseScheme != null && courseScheme.size() > 0) {
			Object[] courseId_SchemeNo = courseScheme.get(0);
			courseId = (Integer) courseId_SchemeNo[0];
			schemeNo = (Integer) courseId_SchemeNo[1];

		}
		BigDecimal requiredAggrePercentage = impl.getAggregatePassPercentage(courseId);
		// ----

		
		int subId = 0;
		if (flag_SplSuppl) {
			HashMap<Integer, SupplementaryDataTO> supplementaryData = gethashMapforSuppl(stuMarkDetails);
			if (stuMarkDetails != null && stuMarkDetails.size() > 0) {
				for (StudentMarkDetailsTO studentMarkDetailsTO : stuMarkDetails) {

					subId = studentMarkDetailsTO.getSubjectId();
					studentFinalMarks = getStudentFinalMark(classId, subId,
							academicYear, studentId, courseId);

					boolean isSupSubject = false;
					examSupplementaryImprovementApplicationBO = new ExamSupplementaryImprovementApplicationBO();
					examSupplementaryImprovementApplicationBO.setExamId(examId);
					examSupplementaryImprovementApplicationBO.setStudentId(studentId);
					examSupplementaryImprovementApplicationBO.setSchemeNo(schemeNo);
					examSupplementaryImprovementApplicationBO.setSubjectId(studentMarkDetailsTO.getSubjectId());
					
					
					if (studentMarkDetailsTO.getPassRegular().equalsIgnoreCase("fail") || studentMarkDetailsTO.getPassInternal().equalsIgnoreCase("fail")) {
						if (supplementaryData.containsKey(studentMarkDetailsTO.getSubjectId())) {
							to = supplementaryData.get(studentMarkDetailsTO.getSubjectId());
							examSupplementaryImprovementApplicationBO.setIsSupplementary(1);
							examSupplementaryImprovementApplicationBO.setIsFailedTheory(to.getTheoryStatus());
							examSupplementaryImprovementApplicationBO.setIsFailedPractical(to.getPracticalStatus());
							if (chanceList.containsKey(studentMarkDetailsTO.getSubjectId())) {
								Integer chanceValue = chanceList.get(studentMarkDetailsTO.getSubjectId());
								if (chanceValue != null) {
									chanceValue = chanceValue + 1;
									examSupplementaryImprovementApplicationBO.setChance(chanceValue);
								}
							} else {
								examSupplementaryImprovementApplicationBO.setChance(1);
							}
						}
						isSupSubject = true;
						retlist.add(examSupplementaryImprovementApplicationBO);
					}

					if (studentFinalMarks != null && subjectTotalMarks != null) {
						Double studentPercentage = Double.valueOf((studentFinalMarks * 100) / subjectTotalMarks);
						if (requiredAggrePercentage != null	&& studentPercentage != null) {
							if ((new BigDecimal(studentPercentage.toString()).intValue()) < (new BigDecimal(requiredAggrePercentage.toString()).intValue())) {
								if (!isSupSubject) {
									if (chanceList.containsKey(studentMarkDetailsTO.getSubjectId())) {
										Integer chanceValue = chanceList.get(studentMarkDetailsTO.getSubjectId());
										if (chanceValue != null) {
											chanceValue = chanceValue + 1;
											examSupplementaryImprovementApplicationBO.setChance(chanceValue);
										}
									} else {
										examSupplementaryImprovementApplicationBO.setChance(1);
									}
									examSupplementaryImprovementApplicationBO.setIsImprovement(1);
									retlist.add(examSupplementaryImprovementApplicationBO);
								}
							}
						}
					}
				}

			}
		}
		Collections.sort(retlist);
		return retlist;

	}

	private Integer getStudentFinalMark(Integer classId, int subId,
			Integer academicYear, int studentId, Integer courseId) {

		boolean theoryCheck = true;
		boolean practicalCheck = true;
		boolean internalCheck = true;
		Integer studentFinalMarks = 0;
		/*Integer marks = 0;
		theoryCheck = impl.getFinalTheoryCheck(subId, academicYear, courseId);

		practicalCheck = impl.getFinalPracticalCheck(subId, academicYear,
				courseId);

		internalCheck = impl.getFinalInternalCheck(subId, academicYear,
				courseId);

		if (theoryCheck) {

			Integer theoryMarks = impl.getStudentsTheoryMarkForSubj(subId,
					studentId);
			if (theoryMarks != null) {
				marks = marks + theoryMarks;
			}
		}
		if (practicalCheck) {
			Integer practicalMarks = impl.getStudentsPracticalMarkForSubj(
					subId, studentId);
			if (practicalMarks != null) {
				marks = marks + practicalMarks;

			}
		}
		if (internalCheck) {
			marks = marks
					+ convertIntenalMarks(impl.getStudentsInternalMarkForSubj(
							subId, studentId));

		}
		studentFinalMarks = studentFinalMarks + marks;*/
		return studentFinalMarks;
	}

	//
	private HashMap<Integer, SupplementaryDataTO> gethashMapforSuppl(
			ArrayList<StudentMarkDetailsTO> list) {
		HashMap<Integer, SupplementaryDataTO> supplMap = new HashMap<Integer, SupplementaryDataTO>();
		SupplementaryDataTO to = null;
		
		if (list != null) {
			for (StudentMarkDetailsTO sto : list) {
				to = new SupplementaryDataTO();
				if (sto.getTheoryEseMin() != null && sto.getTheoryEseMin().length() > 0) {
					if(sto.getStuTheoryRegMark()!=null && StringUtils.isAlpha(sto.getStuTheoryRegMark().trim())){
						to.setTheoryStatus(0);
					}
					else{
						to.setTheoryStatus(getIntValue(sto.getStuTheoryRegMark()) < (getIntValue(sto
										.getTheoryEseMin())) ? 1 : 0);
					}
				}
				
				if (sto.getPracticalEseMin() != null && sto.getPracticalEseMin().length() > 0) {
					if(sto.getStuPracRegMark()!=null && StringUtils.isAlpha(sto.getStuPracRegMark().trim())){
						to.setPracticalStatus(0);
					}else{
						to.setPracticalStatus(getIntValue(sto.getStuPracRegMark()) < (getIntValue(sto
								.getPracticalEseMin())) ? 1 : 0);
					}
				}
				
				if(to.getTheoryStatus() == 0){
					if (sto.getTheoryIntMinMarksTotal() != null && sto.getTheoryIntMinMarksTotal().length() > 0) {
						if(getIntValue(sto.getStuTheoryIntMark()) < getIntValue(sto.getTheoryIntMinMarksTotal())){
							to.setTheoryStatus(1);
						}
					}
				}
				
				if(to.getPracticalStatus() == 0){
					if (sto.getPracticalIntMinMarksTotal() != null && sto.getPracticalIntMinMarksTotal().length() > 0) {
						if(getIntValue(sto.getStuPracIntMark()) < (getIntValue(sto.getPracticalIntMinMarksTotal()))){
							to.setPracticalStatus(1);
						}
					}
				}
				
				if (sto.getSubjectId() != null) {
					supplMap.put(Integer
							.parseInt(sto.getSubjectId().toString()), to);
				}
			}
		}
		supplMap = (HashMap<Integer, SupplementaryDataTO>) CommonUtil.sortMapByValue(supplMap);
		return supplMap;
	}

	private HashMap<Integer, SupplementaryDataTO> gethashMapFromList(
			List<Object[]> list) {
		HashMap<Integer, SupplementaryDataTO> supplMap = new HashMap<Integer, SupplementaryDataTO>();
		SupplementaryDataTO to = null;
		if (list != null) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] row = (Object[]) iterator.next();
				to = new SupplementaryDataTO();
				if (row[1] != null && row[2] != null) {
					if(StringUtils.isAlpha(row[1].toString().trim())){
						to.setTheoryStatus(0);
					}else{
						to.setTheoryStatus(getIntValue(row[1]) < (getIntValue(row[2])) ? 1 : 0);
					}
				}
				if (row[3] != null && row[4] != null) {
					if(StringUtils.isAlpha(row[3].toString().trim())){
						to.setPracticalStatus(0);
					}else{
						to.setPracticalStatus(getIntValue(row[3]) < (getIntValue(row[4])) ? 1 : 0);
					}
				}
				if (row[0] != null) {
					supplMap.put(Integer.parseInt(row[0].toString()), to);
				}
			}
		}
		supplMap = (HashMap<Integer, SupplementaryDataTO>) CommonUtil.sortMapByValue(supplMap);
		return supplMap;
	}

	private int getIntValue(Object object) {
		if (object != null) {
			BigDecimal val = new BigDecimal(object.toString());
			return val.intValue();
		}
		return 0;
	}

	public ArrayList<BatchClassTO> convertClassJoiningBatch(
			List<Object[]> classJoiningBatch) {

		ArrayList<BatchClassTO> listOfClasses = new ArrayList<BatchClassTO>();
		BatchClassTO to = null;
		if (classJoiningBatch != null && classJoiningBatch.size() > 0) {
			for (Iterator iterator = classJoiningBatch.iterator(); iterator
					.hasNext();) {
				Object[] objects = (Object[]) iterator.next();
				to = new BatchClassTO();
				if (objects[1] != null) {
					to.setClassName(objects[1].toString());
				}
				if (objects[2] != null) {
					to.setJoiningBatch(objects[2].toString());
				}
				if (objects[0] != null) {
					to.setClassId(objects[0].toString());
				}
				listOfClasses.add(to);
			}
		}
		return listOfClasses;

	}

	public Integer convertIntenalMarks(
			List<Object[]> studentsInternalMarkForSubj) {

		Integer internalMark = 0;
		Integer totalIntMarks = 0;
		if (studentsInternalMarkForSubj != null) {
			for (Iterator iterator = studentsInternalMarkForSubj.iterator(); iterator
					.hasNext();) {

				Object[] row = (Object[]) iterator.next();

				if (row[0] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[0].toString()).intValue());
				}
				if (row[1] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[1].toString()).intValue());
				}
				if (row[2] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[2].toString()).intValue());
				}
				if (row[3] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[3].toString()).intValue());
				}
				if (row[4] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[4].toString()).intValue());
				}
				if (row[5] != null) {
					internalMark = internalMark
							+ (new BigDecimal(row[5].toString()).intValue());
				}

			}
			totalIntMarks = totalIntMarks + internalMark;
		}
		return totalIntMarks;

	}

	public boolean convertRegularTheoryMarks(
			List<Object[]> studentsRegularTheoryMarksForSubj) {

		if (studentsRegularTheoryMarksForSubj != null) {
			//Iterator itr = studentsRegularTheoryMarksForSubj.iterator();
			for (Iterator iterator = studentsRegularTheoryMarksForSubj
					.iterator(); iterator.hasNext();) {

				Object[] row = (Object[]) iterator.next();

				if (row[1] != null && row[2] != null) {

					if (row[3] != null && row[4] != null) {
						if ((Integer) row[3] < (Integer) row[4]) {
							return true;
						}
					}
				}

			}

		}

		return false;
	}

	public boolean convertRegularPracticalMarks(
			List<Object[]> studentsRegularTheoryMarksForSubj) {

		if (studentsRegularTheoryMarksForSubj != null) {
			//Iterator itr = studentsRegularTheoryMarksForSubj.iterator();
			for (Iterator iterator = studentsRegularTheoryMarksForSubj
					.iterator(); iterator.hasNext();) {

				Object[] row = (Object[]) iterator.next();

				if (row[3] != null && row[4] != null) {
					if (row[3] != null && row[4] != null) {
						if ((Integer) row[3] < (Integer) row[4]) {
							return true;
						}
					}
				}

			}

		}

		return false;
	}

	public ArrayList<StudentMarkDetailsTO> convertBOToTO_mark_details(
			List<Object[]> listT, List<Object[]> listP, String theoryPreferred,
			String practicalPreferred) {
		StudentMarkDetailsTO sTo = new StudentMarkDetailsTO();
		HashMap<Integer, StudentMarkDetailsTO> map = new HashMap<Integer, StudentMarkDetailsTO>();

		if (listT != null && listT.size() > 0) {
			Iterator itr = listT.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				if (row[0] != null) {
					sTo.setSubjectId((Integer) row[0]);
				}
				if (row[1] != null) {
					sTo.setName((String) row[1]);
				}
				if (row[2] != null) {
					if(StringUtils.isAlpha(row[2].toString().trim())){
						sTo.setMaxthoery(row[2].toString());
					}else{
						sTo.setMaxthoery(new Double(row[2].toString()).toString());
					}
				}

				if (row[4] != null) {
					if(StringUtils.isAlpha(row[4].toString().trim())){
						sTo.setMinthoery(row[4].toString());
					}else{
						sTo.setMinthoery(new Double(row[4].toString()).toString());
					}
				}

				if (row[6] != null) {
					if(StringUtils.isAlpha(row[6].toString().trim())){
						sTo.setAvgthoery(row[6].toString());
					}else{
						sTo.setAvgthoery(new Double(row[6].toString()).toString());
					}
				}

				if (row[8] != null) {
					sTo.setIs_theory_practical((Character) row[8]);
				}
				map.put(sTo.getSubjectId(), sTo);
				
			}
			map = (HashMap<Integer, StudentMarkDetailsTO>) CommonUtil.sortMapByValue(map);

		}
		if (listP != null && listP.size() > 0) {

			Iterator itr = listP.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				if (row[0] != null) {
					sTo.setSubjectId((Integer) row[0]);
				}
				if (row[3] != null) {
					if(StringUtils.isAlpha(row[3].toString().trim())){
						sTo.setMaxpractical(row[3].toString());
					}else{
						sTo.setMaxpractical(new Double(row[3].toString())
								.toString());
					}
				}

				if (row[5] != null) {
					if(StringUtils.isAlpha(row[5].toString().trim())){
						sTo.setMinpractical(row[5].toString());
					}else{
						sTo.setMinpractical(new Double(row[5].toString())
								.toString());
					}
				}

				if (row[7] != null) {
					if(StringUtils.isAlpha(row[7].toString().trim())){
						sTo.setAvgpractical(row[7].toString());
					}else{
						sTo.setAvgpractical(new Double(row[7].toString())
								.toString());
					}
				}

				if (row[1] != null) {
					sTo.setName((String) row[1]);
				}
				if (row[8] != null) {
					sTo.setIs_theory_practical((Character) row[8]);
				}
				map.put(sTo.getSubjectId(), sTo);
			}
			map = (HashMap<Integer, StudentMarkDetailsTO>) CommonUtil.sortMapByValue(map);
		}

		sTo.setTheoryPreferred(theoryPreferred);
		sTo.setPracticalPreferred(practicalPreferred);
		return new ArrayList<StudentMarkDetailsTO>(map.values());

	}

	public void convertInternalSettings(List<Object[]> listRow,
			ExamStudentOverallInternalMarkDetailsBO bo) {

		Double thr = 0.0;
		Double pra = 0.0;
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[0] != null) {
				if ((Integer) row[0] == 1) {
					if (bo.getTheoryTotalAssignmentMarks() != null)
						thr = Double.parseDouble(bo
								.getTheoryTotalAssignmentMarks());

				}
			}
			if (row[1] != null) {
				if ((Integer) row[1] == 1) {
					if (bo.getTheoryTotalAttendenceMarks() != null)
						thr = thr
								+ Double.parseDouble(bo
										.getTheoryTotalAttendenceMarks());
				}
			}
			if (row[2] != null) {
				if ((Integer) row[2] == 1) {
					if (bo.getTheoryTotalSubInternalMarks() != null)
						thr = thr
								+ Double.parseDouble(bo
										.getTheoryTotalSubInternalMarks());//

				}
			}

			if (row[3] != null) {
				if ((Integer) row[3] == 1) {
					if (bo.getPracticalTotalAssignmentMarks() != null)
						pra = Double.parseDouble(bo
								.getPracticalTotalAssignmentMarks());
				}
			}
			if (row[4] != null) {
				if ((Integer) row[4] == 1) {
					if (bo.getPracticalTotalAttendenceMarks() != null)
						pra = pra
								+ Double.parseDouble(bo
										.getPracticalTotalAttendenceMarks());
				}
			}
			if (row[5] != null) {
				if ((Integer) row[5] == 1) {
					if (bo.getPracticalTotalSubInternalMarks() != null)
						pra = pra
								+ Double.parseDouble(bo
										.getPracticalTotalSubInternalMarks());
				}
			}

		}
		bo.setTheoryTotalMarks(thr.toString());
		bo.setPracticalTotalMarks(pra.toString());

	}

	public ArrayList<StudentMarkDetailsTO> getSupMarkDetailsList(List<Object[]> suppMarkDetails) throws Exception {
		ArrayList<StudentMarkDetailsTO> stuMarkListSuppl = new ArrayList<StudentMarkDetailsTO>();
		StudentMarkDetailsTO sTo = null;
		if (suppMarkDetails != null) {
			Iterator itr = suppMarkDetails.iterator();
			while (itr.hasNext()) {
				sTo = new StudentMarkDetailsTO();
				
				Object[] row = (Object[]) itr.next();

				if (row[0] != null) {
					sTo.setStudentId((Integer) row[0]);
				}
				
				if (row[1] != null) {
					sTo.setSubjectId((Integer) row[1]);
				}

				if (row[2] != null) {
					sTo.setStuTheoryRegMark(row[2].toString());
				}

				if (row[4] != null) {
					sTo.setStuTheoryIntMark(row[4].toString());
				}

				sTo.setIsFailedTheory(0);
				if (row[7] != null) {
					sTo.setTheoryEseMin(row[7].toString());
					if (sTo.getStuTheoryRegMark() != null){
						if(StringUtils.isAlpha(sTo.getStuTheoryRegMark().trim())){
							sTo.setIsFailedTheory(1);
						}
						else if (Double.parseDouble(sTo.getStuTheoryRegMark()) < Double
								.parseDouble(sTo.getTheoryEseMin())) {
							sTo.setIsFailedTheory(1);
						}
					}
					else{
						sTo.setIsFailedTheory(1);	
					}

				}
				
				if (row[8] != null) {
					sTo.setTheoryEseTheoryFinalMin(row[8].toString());
					if (sTo.getStuTheoryRegMark() != null
							&& sTo.getStuTheoryIntMark() != null){
						if(StringUtils.isAlpha(sTo.getStuTheoryRegMark().trim()) || StringUtils.isAlpha(sTo.getStuTheoryIntMark().trim()) ){
							sTo.setIsFailedTheory(1);
						}
						else if (Double.parseDouble(sTo.getStuTheoryRegMark())+ Double.parseDouble(sTo
										.getStuTheoryIntMark()) < Double.parseDouble(sTo.getTheoryEseTheoryFinalMin())) {
							sTo.setIsFailedTheory(1);
						}
					}
				 }
				
				if (row[9] != null) {

					sTo.setTheoryIntMinMarksTotal(row[9].toString());
					if (sTo.getStuTheoryIntMark() != null){
						if(StringUtils.isAlpha(sTo.getStuTheoryIntMark().trim())){
							sTo.setIsFailedTheory(1);
						}else
						if (Double.parseDouble(sTo.getStuTheoryIntMark()) < Double
								.parseDouble(sTo
										.getTheoryIntMinMarksTotal())) {
							sTo.setIsFailedTheory(1);
						}
					}
				}

				if (row[3] != null) {
					sTo.setStuPracRegMark(row[3].toString());
				}

				if (row[5] != null) {
					sTo.setStuPracIntMark(row[5].toString());
				}
				sTo.setIsFailedPractical(0);
				if (row[10] != null) {
					sTo.setPracticalEseMin(row[10].toString());
					if (sTo.getStuPracRegMark() != null){
						if(StringUtils.isAlpha(sTo.getStuPracRegMark().trim())){
							sTo.setIsFailedPractical(1);
						}
						else if (Double.parseDouble(sTo.getStuPracRegMark()) < Double
								.parseDouble(sTo.getPracticalEseMin())) {
							sTo.setIsFailedPractical(1);

						}
					/*}
					else{
						sTo.setIsFailedPractical(1);	*/
					}
				}
				if (row[11] != null) {
					sTo.setPracticalEsePracFinalMin(row[11]	.toString());
					if (sTo.getStuPracRegMark() != null
							&& sTo.getStuPracIntMark() != null){
						if(StringUtils.isAlpha(sTo.getStuPracRegMark().trim())){
							sTo.setIsFailedPractical(1);
						}
						else if (Double.parseDouble(sTo.getStuPracRegMark())
								+ Double.parseDouble(sTo.getStuPracIntMark()) < Double
								.parseDouble(sTo.getPracticalEsePracFinalMin())) {
							sTo.setIsFailedPractical(1);
						}
					}
				}
				if (row[12] != null) {
					sTo.setPracticalIntMinMarksTotal(row[12].toString());
					if (sTo.getStuPracIntMark() != null){
						if(StringUtils.isAlpha(sTo.getStuPracIntMark().trim())){
							sTo.setIsFailedPractical(1);
						}
						else if (Double.parseDouble(sTo.getStuPracIntMark()) < Double
								.parseDouble(sTo
										.getPracticalIntMinMarksTotal())) {
							sTo.setIsFailedPractical(1);

						}
					}
				}
				
				
				
				if(sTo.getIsFailedTheory() == 1 || sTo.getIsFailedPractical() == 1){
					stuMarkListSuppl.add(sTo);	
				}
				
			}
		}
		
		return stuMarkListSuppl;
	}


	public ArrayList createSupplimentaryBO(List<StudentMarkDetailsTO> stuMarkDetails, HashMap<Integer, Integer> chanceList,
			Integer studentId,	Integer classId, Integer academicYear, Integer examId, Integer schemeNo)  throws Exception{
	
		Iterator<StudentMarkDetailsTO> itr = stuMarkDetails.iterator();
		ExamSupplementaryImprovementApplicationBO examSupplementaryImprovementApplicationBO;
		
		ArrayList suppBoList = new ArrayList();
		Classes classes = new Classes();
		while (itr.hasNext()) {
			StudentMarkDetailsTO studentMarkDetailsTO = (StudentMarkDetailsTO) itr
					.next();
			examSupplementaryImprovementApplicationBO = new ExamSupplementaryImprovementApplicationBO();
			examSupplementaryImprovementApplicationBO.setExamId(examId);
			examSupplementaryImprovementApplicationBO.setStudentId(studentId);
			examSupplementaryImprovementApplicationBO.setSchemeNo(schemeNo);
			examSupplementaryImprovementApplicationBO.setSubjectId(studentMarkDetailsTO.getSubjectId());
			examSupplementaryImprovementApplicationBO.setIsFailedTheory(studentMarkDetailsTO.getIsFailedTheory());
			examSupplementaryImprovementApplicationBO.setIsFailedPractical(studentMarkDetailsTO.getIsFailedPractical());
			examSupplementaryImprovementApplicationBO.setIsSupplementary(1);
			classes.setId(classId);
			examSupplementaryImprovementApplicationBO.setClasses(classes);
			if (chanceList.containsKey(studentMarkDetailsTO.getSubjectId())) {
				Integer chanceValue = chanceList.get(studentMarkDetailsTO.getSubjectId());
				if (chanceValue != null) {
					chanceValue = chanceValue + 1;
					examSupplementaryImprovementApplicationBO.setChance(chanceValue);
				}
			} else {
				examSupplementaryImprovementApplicationBO.setChance(1);
			}
			suppBoList.add(examSupplementaryImprovementApplicationBO);
		}
		
		return suppBoList;
	}

	/**
	 * 
	 * @param classId
	 * @param academicYear
	 * @param studentId
	 * @param courseId
	 * @param subjectList
	 * @param isHighestMarks
	 * @return
	 */
	public Integer getStudentTotalMarkForImprovement(Integer classId, 
			Integer academicYear, int studentId, Integer courseId, List<Integer> subjectList, boolean isHighestMarks) throws Exception {

		Integer marks = 0;
		for (Integer subId : subjectList) {
			Integer theoryMarks = impl.getStudentsTheoryMarkForSubj(subId, studentId, isHighestMarks);
			if (theoryMarks != null) {
				marks = marks + theoryMarks;
			}
			Integer practicalMarks = impl.getStudentsPracticalMarkForSubj(subId, studentId, isHighestMarks);
			if (practicalMarks != null) {
				marks = marks + practicalMarks;

			}
			marks = marks + convertIntenalMarks(impl.getStudentsInternalMarkForSubj(subId, studentId, isHighestMarks));
		}
		return marks;
	}
}
