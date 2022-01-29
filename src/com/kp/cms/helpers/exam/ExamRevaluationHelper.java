package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamRevaluationBO;
import com.kp.cms.bo.exam.ExamRevaluationDetailsBO;
import com.kp.cms.bo.exam.ExamRevaluationTypeBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamRevaluationDetailsTO;
import com.kp.cms.to.exam.ExamRevaluationEntryTO;
import com.kp.cms.to.exam.ExamRevaluationStudentTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamRevaluationImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamRevaluationHelper extends ExamGenHelper {

	// To FETCH SS - AS based on the rules
	// specified in SUBJECT - RULE - SETTINGS
	public ExamRevaluationEntryTO convertBOToTO_MarksDetails(
			List<Object[]> listRow, StudentUtilBO sUBO, String classCode,
			int schemeNo, int courseId) {
		ExamRevaluationEntryTO marksTO = new ExamRevaluationEntryTO();
		ArrayList<ExamRevaluationDetailsTO> listDetTO = new ArrayList<ExamRevaluationDetailsTO>();
		ExamRevaluationDetailsTO detTO;
		boolean firstRun = true;
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			if (firstRun) {
				marksTO.setId((Integer) row[0]);
				marksTO.setStudentId(sUBO.getId());
				marksTO.setStudentName(sUBO.getAdmApplnUtilBO()
						.getPersonalDataUtilBO().getName());
				marksTO.setMarksCardNo((String) row[1]);
				if (row[2] != null) {
					String date = CommonUtil.formatDate((Date) row[2],
							"dd/MM/yyyy");
					marksTO.setMarksCardDate(date);
				}
				firstRun = false;
			}
			detTO = new ExamRevaluationDetailsTO();
			detTO.setId((Integer) row[3]);

			detTO.setSubjectId((Integer) row[4]);
			detTO.setSubjectName((String) row[5]);
			ArrayList<DisplayValueTO> displayTo = new ExamRevaluationImpl()
					.get_type_of_evaluation(detTO.getSubjectId(), courseId,
							schemeNo);
			for (Iterator iterator = displayTo.iterator(); iterator.hasNext();) {
				DisplayValueTO displayValueTO = (DisplayValueTO) iterator
						.next();
				if (row[6] != null) {

					if (displayValueTO.getDisplay().equalsIgnoreCase("t")) {
						if (displayValueTO.getValue().equalsIgnoreCase(
								"highest")) {
							detTO.setTheoryMarks(row[6].toString());
							detTO.setCurrentTheoryMarks(row[6].toString());

						} else if (displayValueTO.getValue().equalsIgnoreCase(
								"lowest")) {
							detTO.setTheoryMarks(row[13].toString());
							detTO.setCurrentTheoryMarks(row[13].toString());

						} else if (displayValueTO.getValue().equalsIgnoreCase(
								"average")) {
							detTO.setTheoryMarks(row[15].toString());
							detTO.setCurrentTheoryMarks(row[15].toString());

						}
					}

				}

				if (row[7] != null) {
					if (displayValueTO.getDisplay().equalsIgnoreCase("p")) {
						if (displayValueTO.getValue().equalsIgnoreCase(
								"highest")) {
							detTO.setPracticalMarks(row[7].toString());
							detTO.setCurrentPracticalMarks(row[7].toString());

						} else if (displayValueTO.getValue().equalsIgnoreCase(
								"lowest")) {
							detTO.setPracticalMarks(row[14].toString());
							detTO.setCurrentPracticalMarks(row[14].toString());

						} else if (displayValueTO.getValue().equalsIgnoreCase(
								"average")) {
							detTO.setPracticalMarks(row[16].toString());
							detTO.setCurrentPracticalMarks(row[16].toString());

						}
					}

				}
			}
			if (row[8] != null) {
				String convertUpperCase = row[8].toString().toUpperCase();
				detTO.setIsTheoryPractical(convertUpperCase);

			}

			detTO.setExamMasterId((Integer) row[0]);
			listDetTO.add(detTO);
		}
		marksTO.setListMarksDetails(listDetTO);
		return marksTO;

	}

	// To INSERT & UPDATE SS - AS
	public ExamRevaluationBO convertTOToBO_MarksMaster(
			Integer examMarksEntryId, int examId, int studentId,
			int revaluationId, String marksCardNo, String userId,
			String marksCardDate) {
		ExamRevaluationBO eBO = new ExamRevaluationBO();
		if (examMarksEntryId != null && examMarksEntryId != 0) {
			eBO.setId(examMarksEntryId);
		} else {
			eBO.setCreatedBy(userId);
			eBO.setCreatedDate(new Date());
		}
		eBO.setExamId(examId);
		eBO.setStudentId(studentId);
		eBO.setRevaluationTypeId(revaluationId);
		eBO.setOldMarksCardNo(marksCardNo);
		if (marksCardDate != null && marksCardDate.trim().length() > 0)
			eBO.setOldMarksCardDate(CommonUtil
					.ConvertStringToDate(marksCardDate));
		eBO.setModifiedBy(userId);
		eBO.setLastModifiedDate(new Date());
		return eBO;
	}

	// To INSERT & UPDATE SS - AS
	public ArrayList<ExamRevaluationDetailsBO> convertTOToBO_MarksDetails(
			ArrayList<ExamRevaluationDetailsTO> listDetailsTO, String userId) {
		ArrayList<ExamRevaluationDetailsBO> listBO = new ArrayList<ExamRevaluationDetailsBO>();
		ExamRevaluationDetailsBO det;

		for (ExamRevaluationDetailsTO eTO : listDetailsTO) {
			det = new ExamRevaluationDetailsBO();
			det.setSubjectId(eTO.getSubjectId());

			if (!(eTO.getCurrentTheoryMarks() == null || eTO
					.getCurrentTheoryMarks().length() == 0)) {

				det.setPreviousTheoryMarks(new BigDecimal(eTO
						.getCurrentTheoryMarks()));

			}

			if (!(eTO.getCurrentPracticalMarks() == null || eTO
					.getCurrentPracticalMarks().length() == 0)) {
				det.setPreviousPracticalMarks(new BigDecimal(eTO
						.getCurrentPracticalMarks()));

			}
			det.setExamRevaluationId(eTO.getExamMasterId());
			listBO.add(det);
		}
		return listBO;

	}

	// To FETCH AS - SS based on the rules
	// specified in SUBJECT - RULE - SETTINGS
	public ArrayList<ExamRevaluationStudentTO> convertBOToTO_getStudentMarksDetails(
			List<Object[]> listRow, String subjectType, int schemeNo,
			int courseId, int subjectId) {

		ArrayList<ExamRevaluationStudentTO> listTO = new ArrayList<ExamRevaluationStudentTO>();
		Iterator itr = listRow.iterator();
		ArrayList<DisplayValueTO> displayTo = new ExamRevaluationImpl()
				.get_type_of_evaluation(subjectId, courseId, schemeNo);

		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			ExamRevaluationStudentTO eTO = new ExamRevaluationStudentTO();
			if (row[0] != null) {
				eTO.setRolNo((String) row[0]);
			}
			if (row[1] != null) {
				eTO.setRegNo((String) row[1]);
			}
			if (row[2] != null) {
				eTO.setStudentId((Integer) row[2]);
			}
			if (row[3] != null || row[4] != null) {
				eTO.setStudentName(row[3] + " " + row[4]);
			}
			if (row[5] != null) {
				eTO.setMarksEntryId((Integer) row[5]);
			}
			if (row[6] != null) {
				eTO.setDetailId((Integer) row[6]);
			}

			for (Iterator iterator = displayTo.iterator(); iterator.hasNext();) {
				DisplayValueTO displayValueTO = (DisplayValueTO) iterator
						.next();

				if (row[7] != null) {

					if (displayValueTO.getDisplay().equalsIgnoreCase("t")) {
						if (displayValueTO.getValue().equalsIgnoreCase(
								"highest")) {
							eTO.setPreviousTheoryMarks(row[7].toString());
							eTO.setCurrentTheoryMarks(row[7].toString());

						} else if (displayValueTO.getValue().equalsIgnoreCase(
								"lowest")) {
							eTO.setPreviousTheoryMarks(row[11].toString());
							eTO.setCurrentTheoryMarks(row[11].toString());

						} else if (displayValueTO.getValue().equalsIgnoreCase(
								"average")) {
							eTO.setPreviousTheoryMarks(row[13].toString());
							eTO.setCurrentTheoryMarks(row[13].toString());

						}
					}

				}

				if (row[8] != null) {
					if (displayValueTO.getDisplay().equalsIgnoreCase("p")) {
						if (displayValueTO.getValue().equalsIgnoreCase(
								"highest")) {
							eTO.setPreviousPracticalMarks(row[8].toString());
							eTO.setCurrentPracticalMarks(row[8].toString());

						} else if (displayValueTO.getValue().equalsIgnoreCase(
								"lowest")) {
							eTO.setPreviousPracticalMarks(row[12].toString());
							eTO.setCurrentPracticalMarks(row[12].toString());

						} else if (displayValueTO.getValue().equalsIgnoreCase(
								"average")) {
							eTO.setPreviousPracticalMarks(row[14].toString());
							eTO.setCurrentPracticalMarks(row[14].toString());

						}
					}

				}
			}

			eTO.setIsTheoryPractical(subjectType);
			listTO.add(eTO);
		}
		return listTO;
	}

	// To get schemeNo for a course & exam
	public HashMap<String, String> convertBOtoTO_SchemeNoBy_ExamId_CourseId(
			List select_SchemeNoBy_ExamId_CourseId) {
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator itr = select_SchemeNoBy_ExamId_CourseId.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			String name = row[1].toString() + "-" + row[2].toString();
			String id = row[0].toString() + "_" + row[1].toString();
			map.put(id, name);
		}
		return map;
	}

	// To get Re-valuation Type List
	public ArrayList<KeyValueTO> convertBOToTO_RevaluationType(
			List<ExamRevaluationTypeBO> listBO) {
		ArrayList<KeyValueTO> list = new ArrayList<KeyValueTO>();
		for (ExamRevaluationTypeBO bo : listBO) {
			list.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		return list;
	}

}
