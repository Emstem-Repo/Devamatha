package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.exam.ExamMarksEntryStudentTO;
import com.kp.cms.to.exam.ExamMarksEntryTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ExamCodeComparator;

@SuppressWarnings("unchecked")
public class ExamMarksEntryHelper extends ExamGenHelper {

	public ExamMarksEntryTO convertBOToTO_MarksDetails(List<Object[]> listRow,
			StudentUtilBO sUBO, String classCode) throws Exception {
		ExamMarksEntryTO marksTO = new ExamMarksEntryTO();
		ArrayList<ExamMarksEntryDetailsTO> listDetTO = new ArrayList<ExamMarksEntryDetailsTO>();
		ExamMarksEntryDetailsTO detTO;
		boolean firstRun = true;
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			try {
				Object[] row = (Object[]) itr.next();

				if (firstRun) {
					marksTO.setId((Integer) row[0]);
					marksTO.setStudentId(sUBO.getId());
					marksTO.setStudentName(sUBO.getAdmApplnUtilBO()
							.getPersonalDataUtilBO().getName());
					marksTO.setClassCode(classCode);
					marksTO.setMarksCardNo((String) row[1]);
					marksTO.setMarksCardDate((Date) row[2]);
					firstRun = false;
				}
				detTO = new ExamMarksEntryDetailsTO();
				detTO.setId((Integer) row[3]);

				detTO.setSubjectId((Integer) row[4]);
				detTO.setSubjectName((String) row[5]);
				if (row[6] != null) {
					detTO.setTheoryMarks(row[6].toString());
				}
				if (row[7] != null) {
					detTO.setPracticalMarks(row[7].toString());
				}
				if (row[8] != null) {
					String convertUpperCase = row[8].toString().toUpperCase();
					detTO.setIsTheoryPractical(convertUpperCase);

				}

				detTO.setExamMasterId((Integer) row[0]);
				listDetTO.add(detTO);
				ExamCodeComparator comparator=new ExamCodeComparator();
				comparator.setCompare(1);
				Collections.sort(listDetTO,comparator);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		marksTO.setListMarksDetails(listDetTO);
	    return marksTO;
	}

	public ExamMarksEntryBO convertTOToBO_MarksMaster(Integer examMarksEntryId,
			int examId, int studentId, Integer evaluatorTypeId,
			String marksCardNo, String userId, String marksCardDate,
			Integer answerScriptTypeId) throws Exception {
		ExamMarksEntryBO eBO = new ExamMarksEntryBO();
		if (examMarksEntryId != null && examMarksEntryId != 0) {
			eBO.setId(examMarksEntryId);
		} else {
			eBO.setCreatedBy(userId);
			eBO.setCreatedDate(new Date());
			eBO.setModifiedBy(userId);
			eBO.setLastModifiedDate(new Date());
		}
		eBO.setExamId(examId);
		eBO.setStudentId(studentId);
		eBO.setEvaluatorTypeId(evaluatorTypeId);
		eBO.setAnswerScriptTypeId(answerScriptTypeId);
		eBO.setMarksCardNo(marksCardNo);
		if (marksCardDate != null && marksCardDate.trim().length() > 0) {
			eBO.setMarksCardDate(CommonUtil.ConvertStringToDate(marksCardDate));
		}
		eBO.setModifiedBy(userId);
		eBO.setLastModifiedDate(new Date());
		eBO.setCreatedBy(userId);
		eBO.setCreatedDate(new Date());
		return eBO;
	}

	public ArrayList<ExamMarksEntryDetailsBO> convertTOToBO_MarksDetails(
			ArrayList<ExamMarksEntryDetailsTO> listDetailsTO, String userId) throws Exception {
		ArrayList<ExamMarksEntryDetailsBO> listBO = new ArrayList<ExamMarksEntryDetailsBO>();
		ExamMarksEntryDetailsBO det;

		for (ExamMarksEntryDetailsTO eTO : listDetailsTO) {
			det = new ExamMarksEntryDetailsBO();
			det.setSubjectId(eTO.getSubjectId());

			if (!(eTO.getTheoryMarks() == null || eTO.getTheoryMarks().length() == 0))
			{
				det.setTheoryMarks(eTO.getTheoryMarks());
			}
			if (!(eTO.getPracticalMarks() == null || eTO.getPracticalMarks()
					.length() == 0)) {
				det.setPracticalMarks(eTO.getPracticalMarks());
			}
			det.setMarksEntryId(eTO.getExamMasterId());
			det.setCreatedBy(userId);
			det.setCreatedDate(new Date());
			det.setLastModifiedDate(new Date());
			det.setModifiedBy(userId);
			listBO.add(det);
		}
		
		Collections.sort(listBO);
		return listBO;

	}

	// To get all students for a single subject
	public ArrayList<ExamMarksEntryStudentTO> convertBOToTO_getStudentMarksDetails(	List<Object[]> listRow, String subjectType,List<Integer> detainedStudnets) throws Exception {
		ArrayList<ExamMarksEntryStudentTO> listTO = new ArrayList<ExamMarksEntryStudentTO>();
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			ExamMarksEntryStudentTO eTO = new ExamMarksEntryStudentTO();
			if (row[0] != null) {
				eTO.setRolNo((String) row[0]);

			}
			if (row[1] != null) {
				eTO.setRegNo((String) row[1]);

			}
			if (row[2] != null) {
				eTO.setStudentId((Integer) row[2]);
				if(detainedStudnets.contains(eTO.getStudentId()))
					continue;
			}
			if (row[3] != null || row[4] != null) {
				eTO.setStudentName(row[3] + " "
						+ (row[4] == null ? "" : row[4].toString()));

			}
			if (row[5] != null) {
				eTO.setMarksEntryId((Integer) row[5]);

			}
			if (row[6] != null) {
				eTO.setDetailId((Integer) row[6]);

			}

			if (!subjectType.equalsIgnoreCase("p")) {
				if (row[7] != null) {
					eTO.setTheoryMarks((String) row[7]);
					eTO.setIsTheoryMarksFromDb(true);

				}
				else
					eTO.setIsTheoryMarksFromDb(false);
			}
			if (!subjectType.equalsIgnoreCase("t")) {
				if (row[8] != null) {
					eTO.setPracticalMarks((String) row[8]);
					eTO.setIsPracticalMarksFromDb(true);
				}
				else
					eTO.setIsPracticalMarksFromDb(false);
			}

			eTO.setIsTheoryPractical(subjectType);
			listTO.add(eTO);
		}
		Collections.sort(listTO);
		return listTO;
	}

	public HashMap<String, String> convertBOtoTO_SchemeNoBy_ExamId_CourseId(
			List select_SchemeNoBy_ExamId_CourseId) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator itr = select_SchemeNoBy_ExamId_CourseId.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			String name = row[1].toString() + "-" + row[2].toString();
			String id = row[0].toString() + "_" + row[1].toString();
			map.put(id, name);
		}
		map = (HashMap<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public HashMap<Integer, String> convertBOToTO_Mul_Ans_Script_KeyVal(
			List<Object> listBO) {

		HashMap<Integer, String> listTO = new HashMap<Integer, String>();
		for (Iterator it = listBO.iterator(); it.hasNext();) {
			ExamMultipleAnswerScriptMasterBO bo = (ExamMultipleAnswerScriptMasterBO) it
					.next();
			listTO.put(bo.getId(), bo.getName());

		}
		listTO = (HashMap<Integer, String>) CommonUtil.sortMapByValue(listTO);
		return listTO;

	}

	public HashMap<Integer, String> convertBOToTO_Mul_EvaluatorType(
			List<Integer> listBO) throws Exception {

		HashMap<Integer, String> listTO = new HashMap<Integer, String>();
		if (listBO != null) {
			for (Integer value : listBO) {
				listTO.put(value, value.toString());

			}
		}
		listTO = (HashMap<Integer, String>) CommonUtil.sortMapByValue(listTO);
		return listTO;

	}
}
