package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamMarksEntryBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionAllstudentsTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionSingleStudentTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionTO;
import com.kp.cms.transactionsimpl.exam.ExamStudentMarksCorrectionImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamStudentMarksCorrectionHelper extends ExamGenHelper {
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	public ExamMarksEntryBO convertTOToBO_MarksMaster(
			Integer studentsMarksCorrectionId, Integer examId,
			Integer studentId, Integer evaluatorType,
			Integer answerScriptTypeId, String marksType, String userId,
			String marksCardNo) {

		ExamMarksEntryBO eBO = new ExamMarksEntryBO();
		if (studentsMarksCorrectionId != null && studentsMarksCorrectionId != 0) {
			eBO.setId(studentsMarksCorrectionId);
		} else {
			eBO.setCreatedBy(userId);
			eBO.setCreatedDate(new Date());
		}
		eBO.setExamId(examId);
		eBO.setStudentId(studentId);
		eBO.setEvaluatorTypeId(evaluatorType);
		eBO.setAnswerScriptTypeId(answerScriptTypeId);
		eBO.setMarkType(null);
		eBO.setSequenceEvaluator(0);
		eBO.setStudentId(studentId);
		eBO.setMarksCardNo(marksCardNo);
		eBO.setModifiedBy(userId);
		eBO.setLastModifiedDate(new Date());
		return eBO;
	}

	// To get all students for a single subject
	public ArrayList<ExamStudentMarksCorrectionAllstudentsTO> convertBOToTO_getStudentMarksDetails(
			String subjectType, List<Object[]> listRow, Map<Integer, List<Integer>> markSubjectList) {
		ArrayList<ExamStudentMarksCorrectionAllstudentsTO> listTO = new ArrayList<ExamStudentMarksCorrectionAllstudentsTO>();
		ExamStudentMarksCorrectionAllstudentsTO eTO = null;
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			String setTheoryMarks = "", setPracticalMarks = "", name = "";

			eTO = new ExamStudentMarksCorrectionAllstudentsTO();
			if (row[0] != null)
				eTO.setRolNo((String) row[0]);
			if (row[1] != null)
				eTO.setRegNo((String) row[1]);
			if (row[2] != null)
				eTO.setStudentId((Integer) row[2]);
			if (row[3] != null) {
				name = (String) row[3];
			}
			if (row[4] != null) {
				name = name + " " + (String) row[4];
			}
			eTO.setStudentName(name);
			if (row[5] != null)
				eTO.setMarksEntryId((Integer) row[5]);
			if (row[6] != null)
				eTO.setDetailId((Integer) row[6]);
			if (row[7] != null)
				setTheoryMarks = (String) row[7];
			if (row[8] != null)
				setPracticalMarks = (String) row[8];
			if (setTheoryMarks != null) {
				eTO.setTheoryMarks(setTheoryMarks);
			}
			if (setPracticalMarks != null) {

				eTO.setPracticalMarks(setPracticalMarks);
			}
			if (row[9] != null) {
				if (row[9].toString().equals("true")) {
					eTO.setMistake(true);
				} else {
					eTO.setMistake(false);
				}
			}

			if (row[10] != null) {
				if (row[10].toString().equals("true")) {
					eTO.setRetest(true);
				} else {
					eTO.setRetest(false);
				}

			}
			
			if(markSubjectList!= null){
				List<Integer> subjectList = markSubjectList.get((Integer) row[5]);
				if(subjectList!= null && subjectList.contains((Integer) row[19]) ){
					eTO.setOldMarks(true);	
				}
				else{
					eTO.setOldMarks(false);
				}
			}
			else
			{
				eTO.setOldMarks(false);
			}
			

			/*if (row[11] != null)
				eTO.setComments((String) row[11]);
			if (row[12] != null) {
				if (new BigInteger(row[12].toString()).intValue() > 1) {
					eTO.setOldMarks(true);
				} else {
					eTO.setOldMarks(false);
				}
			}*/
			if (row[13] != null){
				eTO.setPreviousEvaluatorTheoryMarks(row[13].toString());
			}
			if (row[14] != null){
				eTO.setPreviousEvaluatorPracticalMarks(row[14].toString());
			}
			if (row[15] != null){
				eTO.setCreatedBy(row[15].toString());
			}
			if(row[16]!= null){
				eTO.setCreatedDate(CommonUtil.ConvertsqlStringToDate(row[16].toString()));
			}
			if(row[17]!= null){
				eTO.setModifiedBy(row[17].toString());
			}
			if(row[18]!= null){
				eTO.setLastModifiedDate(CommonUtil.ConvertsqlStringToDate(row[18].toString()));
			}

			eTO.setIsTheoryPractical(subjectType);
			listTO.add(eTO);
		}
		return listTO;
	}

	public ArrayList<ExamMarksEntryDetailsBO> convertTOToBO_MarksDetails(
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listDetailsTO,
			String userId) {

		ArrayList<ExamMarksEntryDetailsBO> listBO = new ArrayList<ExamMarksEntryDetailsBO>();
		ExamMarksEntryDetailsBO det;

		for (ExamStudentMarksCorrectionSingleStudentTO eTO : listDetailsTO) {

			det = new ExamMarksEntryDetailsBO();
			det.setSubjectId(eTO.getSubjectId());

			if (!(eTO.getTheoryMarks() == null || eTO.getTheoryMarks().length() == 0)) {

				det.setTheoryMarks(eTO.getTheoryMarks());

			}

			if (!(eTO.getPracticalMarks() == null || eTO.getPracticalMarks()
					.length() == 0)) {
				det.setPracticalMarks(eTO.getPracticalMarks());

			}

			det.setPreviousEvaluatorPracticalMarks(eTO.getPreviousEvaluatorpracticalMarks());
			det.setPreviousEvaluatorTheoryMarks(eTO.getPreviousEvaluatortheoryMarks());
			det.setCreatedBy(eTO.getCreatedBy());
			det.setCreatedDate(eTO.getCreatedDate());
			det.setModifiedBy(eTO.getModifiedBy());
			det.setLastModifiedDate(new Date());
			if(eTO.isMistake()){
				det.setIsMistake(1);
			}
			else
			{
				det.setIsMistake(0);
			}
			if(eTO.isRetest()){
				det.setIsRetest(1);
			}
			else{
				det.setIsRetest(0);
			}
			
			det.setMarksEntryId(eTO.getExamMasterId());
			det.setComments(eTO.getComments());
			det.setLastModifiedDate(new Date());
			listBO.add(det);

		}
		return listBO;

	}

	public ExamMarksEntryBO convertTOToBO_MarksMaster(Integer examMarksEntryId,
			int examId, int studentId, int evaluatorTypeId, String marksCardNo,
			String userId) {

		ExamMarksEntryBO eBO = new ExamMarksEntryBO();
		if (examMarksEntryId != null && examMarksEntryId != 0) {
			eBO.setId(examMarksEntryId);
		} else {
			eBO.setCreatedBy(userId);
			eBO.setCreatedDate(new Date());
		}
		eBO.setExamId(examId);
		eBO.setStudentId(studentId);
		eBO.setEvaluatorTypeId(evaluatorTypeId);
		eBO.setMarksCardNo(marksCardNo);
		eBO.setMarksCardDate(new Date());
		eBO.setModifiedBy(userId);
		eBO.setLastModifiedDate(new Date());
		return eBO;
	}

	public ExamStudentMarksCorrectionTO convertBOToTO_MarksDetails(
			List<Object[]> listRow, StudentUtilBO sUBO,Map<Integer, List<Integer>> subjectMarkList) {
		ExamStudentMarksCorrectionTO marksTO = new ExamStudentMarksCorrectionTO();
		ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listDetTO = new ArrayList<ExamStudentMarksCorrectionSingleStudentTO>();
		ExamStudentMarksCorrectionSingleStudentTO detTO;
		boolean firstRun = true;
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (firstRun) {
				marksTO.setId((Integer) row[0]);
				marksTO.setStudentId(sUBO.getId());
				marksTO.setStudentName(sUBO.getAdmApplnUtilBO()
						.getPersonalDataUtilBO().getName());
				marksTO.setMarksCardNo((String) row[2]);
				marksTO.setMarksCardDate((Date) row[3]);
				firstRun = false;
			}
			detTO = new ExamStudentMarksCorrectionSingleStudentTO();
			detTO.setId(Integer.parseInt(row[1].toString()));

			detTO.setSubjectId((Integer) row[4]);
			detTO.setSubjectName((String) row[5]);
			if( row[19]!= null){
				detTO.setSubjectCode((String) row[19]);
			}
			
			if (row[6] != null) {
				detTO.setTheoryMarks(row[6].toString());
			}
			if (row[7] != null) {
				detTO.setPracticalMarks(row[7].toString());
			}

			if (row[8] != null) {
				if (row[8].toString().equalsIgnoreCase("true")) {
					detTO.setRetest(true);
				} else {
					detTO.setRetest(false);
				}
			}
			if (row[9] != null) {
				if (row[9].toString().equalsIgnoreCase("true")) {
					detTO.setMistake(true);
				} else {
					detTO.setMistake(false);
				}
			}

			if (row[10] != null) {
				detTO.setComments((String) row[10]);
			}
			detTO.setExamMasterId((Integer) row[0]);
			if (row[11] != null) {
				detTO.setIsTheoryPractical(row[11].toString().toUpperCase());
			}
			/*
			if (row[12] != null) {
				if (new BigInteger(row[12].toString()).intValue() > 1) {
					detTO.setOldMarks(true);
				} else {
					detTO.setOldMarks(false);
				}
			}
			*/
			if(subjectMarkList!= null){
				List<Integer> subjectList = subjectMarkList.get((Integer) row[0]);
				if(subjectList!= null && subjectList.contains((Integer) row[4]) ){
					detTO.setOldMarks(true);	
				}
				else{
					detTO.setOldMarks(false);
				}
			}
			else
			{
				detTO.setOldMarks(false);
			}
			if (row[13] != null) {
				detTO.setPreviousEvaluatortheoryMarks(row[13].toString());
			}
			if (row[14] != null) {
				detTO.setPreviousEvaluatorpracticalMarks(row[14].toString());
			}
			if (row[15] != null) {
				detTO.setCreatedBy(row[15].toString());
			}
			if (row[16] != null) {
				detTO.setCreatedDate(CommonUtil.ConvertsqlStringToDate(row[16].toString()));
			}
			if (row[17] != null) {
				detTO.setModifiedBy(row[17].toString());
			}
			if (row[18] != null) {
				detTO.setModifiedDate(row[18].toString());
			}
			
			
			listDetTO.add(detTO);
		}
		marksTO.setListMarksDetails(listDetTO);
		return marksTO;

	}

	public HashMap<Integer, String> convert_evaluatorList_fromRuleSettings(
			List<Integer> evaluator_list) {

		HashMap<Integer, String> listTO = new HashMap<Integer, String>();
		if (evaluator_list != null) {
			for (Integer value : evaluator_list) {
				listTO.put(value, value.toString());

			}
		}
		return listTO;

	}

	public HashMap<Integer, String> convertBOToTO_getListanswerScriptType(
			List<ExamMultipleAnswerScriptMasterBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamMultipleAnswerScriptMasterBO bo : listBO) {
			map.put(bo.getId(), bo.getName());
		}
		return map;
	}

	public ArrayList<ExamStudentMarksCorrectionSingleStudentTO> convertBOToTO_OldMarks(
			ArrayList<Object[]> oldMarksList) {
		ArrayList<ExamStudentMarksCorrectionSingleStudentTO> returnList = new ArrayList<ExamStudentMarksCorrectionSingleStudentTO>();
		ExamStudentMarksCorrectionSingleStudentTO detTO;

		Iterator<Object[]> itr = oldMarksList.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			detTO = new ExamStudentMarksCorrectionSingleStudentTO();
			if (row[0] != null)
				detTO.setSubjectName(row[0].toString());
			if (row[1] != null)
				detTO.setTheoryMarks(row[1].toString());
			if (row[2] != null)
				detTO.setPracticalMarks(row[2].toString());
			if (row[3] != null)
				detTO.setComments(row[3].toString());
			if (row[4] != null)
				detTO.setCorrectedDate(CommonUtil
						.formatSqlDateTimeToString(row[4].toString()));
			returnList.add(detTO);
		}

		return returnList;
	}

	public ArrayList<ExamStudentMarksCorrectionAllstudentsTO> convertBOToTO_getStudentInternalOverall(
			String subjectType, List<Object[]> listRow, Map<Integer, List<Integer>> markSubjectList) {

		ArrayList<ExamStudentMarksCorrectionAllstudentsTO> listTO = new ArrayList<ExamStudentMarksCorrectionAllstudentsTO>();
		ExamStudentMarksCorrectionAllstudentsTO eTO = null;
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			String setTheoryMarks = "", setPracticalMarks = "", name = "";

			eTO = new ExamStudentMarksCorrectionAllstudentsTO();
			if (row[0] != null)
				eTO.setRolNo((String) row[0]);
			if (row[1] != null)
				eTO.setRegNo((String) row[1]);
			if (row[2] != null)
				eTO.setStudentId((Integer) row[2]);
			if (row[3] != null) {
				name = (String) row[3];
			}
			if (row[4] != null) {
				name = name + " " + (String) row[4];
			}
			eTO.setStudentName(name);
			if (row[5] != null)
				eTO.setMarksEntryId((Integer) row[5]);

			if (row[6] != null)
				setTheoryMarks = (String) row[6];
			if (row[7] != null)
				setPracticalMarks = (String) row[7];
			if (setTheoryMarks != null) {
				eTO.setTheoryMarks(setTheoryMarks);
			}
			if (setPracticalMarks != null) {

				eTO.setPracticalMarks(setPracticalMarks);
			}
			eTO.setMistake(false);
			eTO.setRetest(false);
			if (row[8] != null)
				eTO.setComments((String) row[8]);
			
			
			if(markSubjectList!= null){
				List<Integer> subjectList = markSubjectList.get((Integer) row[5]);
				if(subjectList!= null && subjectList.contains((Integer) row[10]) ){
					eTO.setOldMarks(true);	
				}
				else{
					eTO.setOldMarks(false);
				}
			}
			else
			{
				eTO.setOldMarks(false);
			}
			
			/*if (row[9] != null) {
				if (new BigInteger(row[9].toString()).intValue() > 1) {
					eTO.setOldMarks(true);
				} else {
					eTO.setOldMarks(false);
				}
			}*/
			
			
			
			eTO.setIsTheoryPractical(subjectType);
			listTO.add(eTO);
		}
		return listTO;

	}

	public DisplayValueTO convertInternalSettings(List<Object[]> listRow) {
		DisplayValueTO dtv = new DisplayValueTO();
		String thr = null;
		String pra = null;
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[0] != null) {
				if ((Integer) row[0] == 1) {
					thr = "imd.theory_total_assignment_mark + ";
				}
			}
			if (row[1] != null) {
				if ((Integer) row[1] == 1) {
					thr = thr + "imd.theory_total_attendance_mark + ";
				}
			}
			if (row[2] != null) {
				if ((Integer) row[2] == 1) {
					thr = thr + "imd.theory_total_sub_internal_mark ";
				} else {
					thr = thr.substring(0, thr.length() - 1);
				}
			}

			if (row[3] != null) {
				if ((Integer) row[3] == 1) {
					pra = "imd.practical_total_assignment_mark + ";
				}
			}
			if (row[4] != null) {
				if ((Integer) row[4] == 1) {
					pra = pra + " imd.practical_total_attendance_mark + ";
				}
			}
			if (row[5] != null) {
				if ((Integer) row[5] == 1) {
					pra = pra + "imd.practical_total_sub_internal_mark ";
				} else {
					pra = pra.substring(0, thr.length() - 1);
				}
			}

		}
		dtv.setDisplay(thr);
		dtv.setValue(pra);
		return dtv;
	}

	public ExamStudentMarksCorrectionTO convertBOToTO_MarksDetailsInternalOverall(
			List<Object[]> listRow, StudentUtilBO sUBO, Map<Integer, List<Integer>> subjectMarkList) {
		ExamStudentMarksCorrectionTO marksTO = new ExamStudentMarksCorrectionTO();
		ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listDetTO = new ArrayList<ExamStudentMarksCorrectionSingleStudentTO>();
		ExamStudentMarksCorrectionSingleStudentTO detTO;
		boolean firstRun = true;
		Iterator itr = listRow.iterator();

		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			if (firstRun) {
				marksTO.setId((Integer) row[0]);
				marksTO.setStudentId(sUBO.getId());
				marksTO.setStudentName(sUBO.getAdmApplnUtilBO()
						.getPersonalDataUtilBO().getName());
				marksTO.setMarksCardNo("");
				firstRun = false;
			}
			detTO = new ExamStudentMarksCorrectionSingleStudentTO();
			detTO.setId(Integer.parseInt(row[0].toString()));

			detTO.setSubjectId((Integer) row[1]);
			detTO.setSubjectName((String) row[2]);
			if (row[3] != null) {
				detTO.setTheoryMarks(row[3].toString());
			}
			if (row[4] != null) {
				detTO.setPracticalMarks(row[4].toString());
			}
			detTO.setRetest(false);
			detTO.setMistake(false);

			if (row[5] != null) {
				detTO.setComments((String) row[5]);
			}
			detTO.setExamMasterId((Integer) row[0]);
			if (row[6] != null) {
				detTO.setIsTheoryPractical(row[6].toString().toUpperCase());
			}
			/*if (row[7] != null) {
				if (new BigInteger(row[7].toString()).intValue() > 1) {
					detTO.setOldMarks(true);
				} else {
					detTO.setOldMarks(false);
				}
			}*/
			
			if (row[8] != null) {
				detTO.setSubjectCode((String) row[8]);
			}
			if(subjectMarkList!= null){
				List<Integer> subjectList = subjectMarkList.get((Integer) row[0]);
				if(subjectList!= null && subjectList.contains((Integer) row[1]) ){
					detTO.setOldMarks(true);	
				}
				else{
					detTO.setOldMarks(false);
				}
			}
			else
			{
				detTO.setOldMarks(false);
			}
			listDetTO.add(detTO);
		}
		marksTO.setListMarksDetails(listDetTO);
		return marksTO;
	}

	public void convertTOToBO_SingleStuInternalOverall(Integer id,
			Integer examId, Integer studentId,
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listDetailsTO,
			String userId, int courseId, int schemeId)
			throws DataNotFoundException {
		ExamStudentMarksCorrectionImpl impl = new ExamStudentMarksCorrectionImpl();
		

		for (ExamStudentMarksCorrectionSingleStudentTO eTO : listDetailsTO) {
			impl.chkNInsertInternalOverall(eTO.getTheoryMarks(), eTO
					.getPracticalMarks(), eTO.getId(), eTO.getSubjectId(), eTO
					.getComments());
		}
		// return listBO;
	}

	public void convertTOToBO_SingleStuRegularOverall(Integer id,
			Integer examId, Integer studentId,
			ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listDetailsTO,
			String userId, int courseId, int schemeId)
			throws DataNotFoundException {
		ExamStudentMarksCorrectionImpl impl = new ExamStudentMarksCorrectionImpl();

		// ArrayList<ExamStudentFinalMarkDetailsBO> listBO = new
		// ArrayList<ExamStudentFinalMarkDetailsBO>();
		// ExamStudentFinalMarkDetailsBO det;

		for (ExamStudentMarksCorrectionSingleStudentTO eTO : listDetailsTO) {
			impl.chkNInsertRegularOverall(eTO.getTheoryMarks(), eTO
					.getPracticalMarks(), /*id*/eTO.getId(), eTO.getSubjectId(), eTO
					.getComments());
			// det = new ExamStudentFinalMarkDetailsBO();
			// det.setSubjectId(eTO.getSubjectId());
			//
			// if (!(eTO.getTheoryMarks() == null ||
			// eTO.getTheoryMarks().length() == 0)) {
			//
			// det.setStudentTheoryMarks(eTO.getTheoryMarks());
			//
			// }
			//
			// if (!(eTO.getPracticalMarks() == null || eTO.getPracticalMarks()
			// .length() == 0)) {
			// det.setStudentPracticalMarks(eTO.getPracticalMarks());
			//
			// }
			//
			// // det.setMarksEntryId(eTO.getExamMasterId());
			// det.setComments(eTO.getComments());
			// det.setLastModifiedDate(new Date());
			//
			// if (impl.chkIfCorrectionDoneRegularOverall(det
			// .getStudentTheoryMarks(), det.getStudentPracticalMarks(), id,
			// det.getSubjectId())) {
			// listBO.add(det);
			// }

		}
		// return listBO;
	}

}
