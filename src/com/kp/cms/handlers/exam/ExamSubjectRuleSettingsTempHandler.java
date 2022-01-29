package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.PageBreakRecord.Break;

import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAssignmentBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsAttendanceBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsMultipleAnsScriptBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsSubInternalBO;
import com.kp.cms.forms.exam.ExamSubjectRuleSettingsForm;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAttendanceTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubjectTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.transactionsimpl.exam.ExamSubDefinitionCourseWiseImpl;
import com.kp.cms.utilities.CommonUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class ExamSubjectRuleSettingsTempHandler extends
		ExamSubjectRuleSettingsHandler {
	private static final Log log = LogFactory
			.getLog(ExamSubjectRuleSettingsTempHandler.class);

	public boolean saveTheoryInternal(
			ExamSubjectRuleSettingsAttendanceTO attTO,
			List<ExamSubjectRuleSettingsSubInternalTO> subInternalList,
			ExamSubjectRuleSettingsSubInternalTO subTO,
			List<ExamSubjectRuleSettingsAssignmentTO> assignmentList,
			ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO,
			ArrayList<Integer> listCourses, String academicYear,
			String schemeName, String userId, HttpSession session)
			throws Exception {
		boolean isInserted = false;
		BigDecimal finalEntryMaximumMarks = null;
		BigDecimal finalInternalMinimumMarks = null;
		BigDecimal finalInternalMaximumMarks = null;
		Integer count = 0;
		int assignmentChecked = (theoryIntTO.getAssignmentChecked() != null && theoryIntTO
				.getAssignmentChecked().equalsIgnoreCase("on")) ? 1 : 0;
		int subInternalChecked = (theoryIntTO.getSubInternalChecked() != null && theoryIntTO
				.getSubInternalChecked().equalsIgnoreCase("on")) ? 1 : 0;
		int attendanceChecked = (theoryIntTO.getAttendanceChecked() != null && theoryIntTO
				.getAttendanceChecked().equalsIgnoreCase("on")) ? 1 : 0;
		ExamSubjectRuleSettingsBO subjectRulesBO = new ExamSubjectRuleSettingsBO();

		if (CommonUtil.checkForEmpty(theoryIntTO.getFinalEntryMaximumMarks())
				&& theoryIntTO.getFinalEntryMaximumMarks().trim().length() > 0) {

			finalEntryMaximumMarks = new BigDecimal(theoryIntTO
					.getFinalEntryMaximumMarks());

		}
		if (CommonUtil
				.checkForEmpty(theoryIntTO.getFinalInternalMinimumMarks())
				&& theoryIntTO.getFinalInternalMinimumMarks().trim().length() > 0) {

			finalInternalMinimumMarks = new BigDecimal(theoryIntTO
					.getFinalInternalMinimumMarks());

		}
		if (CommonUtil
				.checkForEmpty(theoryIntTO.getFinalInternalMaximumMarks())
				&& theoryIntTO.getFinalInternalMaximumMarks().trim().length() > 0) {

			finalInternalMaximumMarks = new BigDecimal(theoryIntTO
					.getFinalInternalMaximumMarks());

		}

		subjectRulesBO
				.setFinalTheoryInternalEnteredMaxMark(finalEntryMaximumMarks);
		subjectRulesBO
				.setFinalTheoryInternalMinimumMark(finalInternalMinimumMarks);
		subjectRulesBO
				.setFinalTheoryInternalMaximumMark(finalInternalMaximumMarks);
		subjectRulesBO.setFinalTheoryInternalIsAssignment(assignmentChecked);
		subjectRulesBO.setFinalTheoryInternalIsAttendance(attendanceChecked);
		subjectRulesBO.setFinalTheoryInternalIsSubInternal(subInternalChecked);
		subjectRulesBO.setCreatedBy(userId);
		subjectRulesBO.setModifiedBy(userId);
		subjectRulesBO.setCreatedDate(new Date());

		if (session.getAttribute("count") != null) {

			count = (Integer) session.getAttribute("count");
		}
		if (count.intValue() == 1) {

			
			List<Integer> subjectRuleIdList = insertSubjectRuleSettingsDetails(
					academicYear, listCourses, userId, schemeName, subTO, null,
					subjectRulesBO);
			if (subjectRuleIdList.size() > 0) {
				isInserted = true;
				session.setAttribute("subjectRuleIdList", subjectRuleIdList);

				for (Integer subRuleSettingsID : subjectRuleIdList)

				{
					if (assignmentList != null || assignmentList.size() != 0) {

						addAssignmentDetails(assignmentList, subRuleSettingsID
								.intValue());
					}

					if (subInternalList != null || subInternalList.size() != 0) {

						
						addSubInternalDetails(subInternalList,
								subRuleSettingsID.intValue());
					}

					if (attTO != null) {
						attTO.setIsTheoryPractical("t");
						addAttendanceDetails(attTO, subRuleSettingsID
								.intValue());
					}

				}
			} else {
				isInserted = false;
			}

		} else {
			String mode = "update";
			
			List<Integer> subjectRuleIdList = (ArrayList<Integer>) session
					.getAttribute("subjectRuleIdList");
			if (subjectRuleIdList != null && subjectRuleIdList.size() > 0) {
				isInserted = true;
				
				List<Object> listBO = impl.select_All(
						ExamSubjectRuleSettingsBO.class, subjectRuleIdList);
				
				ArrayList<ExamSubjectRuleSettingsBO> subBOList = new ArrayList<ExamSubjectRuleSettingsBO>();
				for (Iterator it = listBO.iterator(); it.hasNext();) {
					ExamSubjectRuleSettingsBO bo =(ExamSubjectRuleSettingsBO) it
							.next();

					bo.setFinalTheoryInternalEnteredMaxMark(finalEntryMaximumMarks);
					bo.setFinalTheoryInternalMinimumMark(finalInternalMinimumMarks);
					bo.setFinalTheoryInternalMaximumMark(finalInternalMaximumMarks);
					bo.setFinalTheoryInternalIsAssignment(assignmentChecked);
					bo.setFinalTheoryInternalIsAttendance(attendanceChecked);
					bo.setFinalTheoryInternalIsSubInternal(subInternalChecked);
					bo.setCreatedBy(userId);
					bo.setModifiedBy(userId);
					bo.setCreatedDate(new Date());

					subBOList.add(bo);
				}

				impl.update_List(subBOList);
				
				List<Object> listSubInternalBO = impl.select_All(
						ExamSubjectRuleSettingsSubInternalBO.class,
						subjectRuleIdList);
				ArrayList<ExamSubjectRuleSettingsSubInternalBO> boList = new ArrayList<ExamSubjectRuleSettingsSubInternalBO>();
				for (ExamSubjectRuleSettingsSubInternalTO to : subInternalList) {
					for (Iterator it = listSubInternalBO.iterator(); it
							.hasNext();) {
						ExamSubjectRuleSettingsSubInternalBO bo = (ExamSubjectRuleSettingsSubInternalBO) it
								.next();
						bo = setToSubInternalBO(to, bo);
						boList.add(bo);
						

					}

				}

				impl.update_List(boList);

				// *------------------update Theory Assignment
				// -------------------------------------------
				List<Object> listTheoryAssignmentBO = impl.select_All(
						ExamSubjectRuleSettingsAssignmentBO.class,
						subjectRuleIdList);
				ArrayList<ExamSubjectRuleSettingsAssignmentBO> assignmentBOList = new ArrayList<ExamSubjectRuleSettingsAssignmentBO>();
				BigDecimal maximumMark = null;
				BigDecimal minimumMark = null;

				for (ExamSubjectRuleSettingsAssignmentTO to : assignmentList) {

					maximumMark = null;
					minimumMark = null;

					if (to.getMaximumAssignMarks() != null
							&& to.getMaximumAssignMarks().trim().length() > 0) {
						maximumMark = new BigDecimal(to.getMaximumAssignMarks());
					}

					if (to.getMinimumAssignMarks() != null
							&& to.getMinimumAssignMarks().trim().length() > 0) {
						minimumMark = new BigDecimal(to.getMinimumAssignMarks());
					}
					for (Iterator it = listTheoryAssignmentBO.iterator(); it
							.hasNext();) {

						ExamSubjectRuleSettingsAssignmentBO assignmentBO = (ExamSubjectRuleSettingsAssignmentBO) it
								.next();
						
						assignmentBO.setMaximumMark(maximumMark);
						assignmentBO.setMinimumMark(minimumMark);
						assignmentBO.setIsTheoryPractical("t");
						assignmentBOList.add(assignmentBO);
						
					}

				}

				impl.update_List(assignmentBOList);

				// ------------------update Theory Attendance
				// ---------------------------------------

				List<Object> listAttBO = impl.select_All(
						ExamSubjectRuleSettingsAttendanceBO.class,
						subjectRuleIdList);
				ArrayList<ExamSubjectRuleSettingsAttendanceBO> attBOList = new ArrayList<ExamSubjectRuleSettingsAttendanceBO>();
				for (Iterator it = listAttBO.iterator(); it.hasNext();) {
					ExamSubjectRuleSettingsAttendanceBO bo = (ExamSubjectRuleSettingsAttendanceBO) it
							.next();

					bo = setToTheoryAttendanceBO(bo, attTO);
					attBOList.add(bo);
				}
				impl.update_List(attBOList);

			} else {
				isInserted = false;
			}

		}
		return isInserted;
		
	}

	public List<Integer> insertSubjectRuleSettingsDetails(String academicYear,
			ArrayList<Integer> courseIds, String userId, String schemeType,
			ExamSubjectRuleSettingsSubInternalTO subTO,
			ExamSubjectRuleSettingsSubInternalTO toPractical,
			ExamSubjectRuleSettingsBO subjectRulesBO) throws Exception {

		int academic_Year = 0;
		if (CommonUtil.checkForEmpty(academicYear)) {
			academic_Year = Integer.parseInt(academicYear);
		}
		List<Integer> ids = null;

		ArrayList<ExamSubjectRuleSettingsBO> subRuleBO = new ArrayList<ExamSubjectRuleSettingsBO>();

		ArrayList<ExamSubjectRuleSettingsSubjectTO> subjectTO = null;

		Map<Integer, String> map = null;

		for (Integer courseID : courseIds) {
			Iterator it = null;

			
			map = getSchemeIDSchemeNOByCourse(Integer.toString(courseID));

			it = map.keySet().iterator();

			Integer key = (Integer) it.next();

			int value = Integer.parseInt(map.get(key));

			for (int i = 1; i <= value; i++) {

				if (i % 2 == 0) {

					try {

						subjectTO = getSubjects(courseID, key, i, academic_Year);
						for (ExamSubjectRuleSettingsSubjectTO to : subjectTO) {
							if (schemeType!=null && schemeType.equalsIgnoreCase("even")
									|| schemeType.equalsIgnoreCase("both")) {
								
								subjectRulesBO.setAcademicYear(academic_Year);
								subjectRulesBO.setSubjectId(to.getSubid());
								subjectRulesBO.setCourseId(courseID);
								subjectRulesBO.setSchemeNo(i);
								subRuleBO.add(new ExamSubjectRuleSettingsBO(
										subjectRulesBO));

							}
						}

					} catch (Exception e) {
						log.error(e.getMessage());
					}

				}

				else {

					
					try {
						subjectTO = getSubjects(courseID, key, i, academic_Year);

						
						for (ExamSubjectRuleSettingsSubjectTO to : subjectTO) {

							if (schemeType!=null && schemeType.equalsIgnoreCase("odd")
									|| schemeType.equalsIgnoreCase("both")) {
								
								subjectRulesBO.setAcademicYear(academic_Year);
								subjectRulesBO.setSubjectId(to.getSubid());
								subjectRulesBO.setCourseId(courseID);
								subjectRulesBO.setSchemeNo(i);
								subRuleBO.add(new ExamSubjectRuleSettingsBO(
										subjectRulesBO));
							}
						}

					} catch (Exception e) {

						
						log.error(e.getMessage());
					}

				}
			}

		}

		ids = impl.insert_SubjectRuleSettingsList(subRuleBO);

		return ids;

	}

	private ExamSubjectRuleSettingsSubInternalBO setToSubInternalBO(
			ExamSubjectRuleSettingsSubInternalTO to,
			ExamSubjectRuleSettingsSubInternalBO bo) {
		BigDecimal minimumMark = null;
		BigDecimal enteredMaxMark = null;
		BigDecimal maximumMark = null;
		String isTheoryPractical = null;
		if (to.getMinimumMarks() != null
				&& to.getMinimumMarks().trim().length() > 0) {
			minimumMark = new BigDecimal(to.getMinimumMarks());
		}
		if (to.getMaximumMarks() != null
				&& to.getMaximumMarks().trim().length() > 0) {
			maximumMark = new BigDecimal(to.getMaximumMarks());
		}
		if (to.getEntryMaximumMarks() != null
				&& to.getEntryMaximumMarks().trim().length() > 0) {
			enteredMaxMark = new BigDecimal(to.getEntryMaximumMarks());
		}
		bo.setMaximumMark(maximumMark);
		bo.setMinimumMark(minimumMark);
		bo.setEnteredMaxMark(enteredMaxMark);
		
		return bo;

	}

	private ExamSubjectRuleSettingsAttendanceBO setToTheoryAttendanceBO(
			ExamSubjectRuleSettingsAttendanceBO attendanceBO,
			ExamSubjectRuleSettingsAttendanceTO attTO) {
		int leaveAttendance = 0;
		int cocurricularAttendance = 0;
		int attendanceTypeId = 0;

		
		if (attTO.getLeaveAttendance() != null
				|| !attTO.getLeaveAttendance().isEmpty()
				|| attTO.getLeaveAttendance().equalsIgnoreCase("on")) {
			leaveAttendance = 1;
		}

		if (attTO.getCoCurricularAttendance() != null
				|| !attTO.getCoCurricularAttendance().isEmpty()
				|| attTO.getCoCurricularAttendance().equalsIgnoreCase("on")) {
			cocurricularAttendance = 1;
		}

		if (CommonUtil.checkForEmpty(attTO.getAttendanceType())) {
			attendanceTypeId = Integer.parseInt(attTO.getAttendanceType());
		}

		

		attendanceBO.setIsLeave(leaveAttendance);
		attendanceBO.setIsCoCurricular(cocurricularAttendance);
		attendanceBO.setAttendanceTypeId(attendanceTypeId);
		return attendanceBO;

	}

	public void editInsertedDetails(ExamSubjectRuleSettingsForm objform,
			HttpSession session) throws Exception {

		
		objform.setListCourses((ArrayList<Integer>) session
				.getAttribute("listCourses"));
		List<Integer> subjectRuleIdList = (List<Integer>) session
				.getAttribute("subjectRuleIdList");

		Integer subRuleId = (subjectRuleIdList != null
				&& subjectRuleIdList.size() != 0 ? subjectRuleIdList.get(0) : 0);
		
		/*List<Object> listSubInternalBO = impl.select_All(subRuleId,
				ExamSubjectRuleSettingsSubInternalBO.class);
		List<ExamSubjectRuleSettingsSubInternalTO> subInternalTOList = objform
				.getSubInternalList();*/

		ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO = objform
				.getTheoryIntTO();
		ExamSubjectRuleSettingsSubInternalTO subTO = objform.getSubTO();

		

		theoryIntTO = setTheoryInternal(subRuleId, theoryIntTO);

		theoryIntTO = setTheoryInternalAttendance(subRuleId, theoryIntTO);

		theoryIntTO = setTheoryInternalAssignment(subRuleId, theoryIntTO);

		// ----set to form---------------------------

		objform.setSubInternalList(theoryIntTO.getSubInternalList());

		objform.setAttTO(theoryIntTO.getListAttendanceTO());

		subTO
				.setTotalentryMaximumMarks(theoryIntTO
						.getTotalentryMaximumMarks());
		subTO.setTotalMaximumMarks(theoryIntTO.getTotalMaximumMarks());
		subTO.setTotalMinimummumMarks(theoryIntTO.getTotalMinimummumMarks());
		objform.setSubTO(subTO);

		objform.setAssignmentList(theoryIntTO.getAssignmentList());

		

	}

	public void saveTheoryESE(ExamSubjectRuleSettingsForm objform,
			HttpSession session) throws Exception {
		
		Integer count = 0;
		BigDecimal	theoryEseEnteredMaxMark=null;
		BigDecimal theoryEseMaximumMark=null;
		BigDecimal theoryEseMinimumMark =null;
		BigDecimal theoryEseTheoryFinalMaximumMark=null;
		BigDecimal theoryEseTheoryFinalMinimumMark =null;
		int theoryEseIsMultipleAnswerScript = 0;
		int theoryEseIsMultipleEvaluator = 0;
		int theoryEseIsRegular = 0;
		List<Integer> subjectRuleIdList = (List<Integer>) session
				.getAttribute("subjectRuleIdList");
		ExamSubjectRuleSettingsTheoryESETO theoryESETO = objform
				.getTheoryESETO();
		
		List<Object> listBO = impl.select_All(
				ExamSubjectRuleSettingsBO.class, subjectRuleIdList);
		ArrayList<ExamSubjectRuleSettingsBO> subBOList = new ArrayList<ExamSubjectRuleSettingsBO>();
		 if(CommonUtil.checkForEmpty(theoryESETO
					.getMaximumEntryMarksTheoryESE())||theoryESETO
					.getMaximumEntryMarksTheoryESE().trim().length()>0)
			{
				theoryEseEnteredMaxMark = new BigDecimal(theoryESETO
						.getMaximumEntryMarksTheoryESE());
				
			}
			if(CommonUtil.checkForEmpty(theoryESETO
					.getMaximumMarksTheoryESE())||theoryESETO
					.getMaximumMarksTheoryESE().trim().length()>0)
			{
				
				theoryEseMaximumMark = new BigDecimal(theoryESETO
						.getMaximumMarksTheoryESE());
				
			}
			if(CommonUtil.checkForEmpty(theoryESETO
					.getMinimumMarksTheoryESE())||theoryESETO
					.getMinimumMarksTheoryESE().trim().length()>0)
			{
				
				theoryEseMinimumMark = new BigDecimal(theoryESETO
						.getMinimumMarksTheoryESE());
				
			}
			if(CommonUtil.checkForEmpty(theoryESETO
					.getMaximumTheoryFinalMarksTheoryESE())||theoryESETO
					.getMaximumTheoryFinalMarksTheoryESE().trim().length()>0)
			{
				theoryEseTheoryFinalMaximumMark = new BigDecimal(theoryESETO
						.getMaximumTheoryFinalMarksTheoryESE());
				
				
			}
			if(CommonUtil.checkForEmpty(theoryESETO
					.getMinimumTheoryFinalMarksTheoryESE())||theoryESETO
					.getMinimumTheoryFinalMarksTheoryESE().trim().length()>0)
			{
				theoryEseTheoryFinalMinimumMark = new BigDecimal(theoryESETO
						.getMinimumTheoryFinalMarksTheoryESE());
				
				
			}
				

			if (theoryESETO.getMultipleAnswerScriptsChecked() != null
					|| !theoryESETO.getMultipleAnswerScriptsChecked().isEmpty()
					|| theoryESETO.getMultipleAnswerScriptsChecked()
							.equalsIgnoreCase("on")) {
				theoryEseIsMultipleAnswerScript = 1;
			}
			if (theoryESETO.getMultipleEvaluatorChecked() != null
					|| !theoryESETO.getMultipleEvaluatorChecked().isEmpty()
					|| theoryESETO.getMultipleEvaluatorChecked().equalsIgnoreCase(
							"on")) {
				theoryEseIsMultipleEvaluator = 1;
			}

			if (theoryESETO.getRegularTheoryESEChecked() != null
					|| !theoryESETO.getRegularTheoryESEChecked().isEmpty()
					|| theoryESETO.getRegularTheoryESEChecked().equalsIgnoreCase(
							"on")) {
				theoryEseIsRegular = 1;
			}


		if (session.getAttribute("countForTheoryESE") != null) {

			count = (Integer) session.getAttribute("countForTheoryESE");
		}
		if (count.intValue() == 1) {
			for (Integer subRuleSettingsID : subjectRuleIdList) {
				if (theoryESETO != null) {

					// Add MultipleAnswerScriptList
					if (theoryESETO.getMultipleAnswerScriptList() != null
							|| theoryESETO.getMultipleAnswerScriptList().size() != 0) {
						List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> listTO = theoryESETO
								.getMultipleAnswerScriptList();
						addMultipleAnswerScriptDetails(listTO,
								subRuleSettingsID.intValue());
					}

					
						// Add MultipleEvaluatorDetails
						addMultipleEvaluatorDetails(theoryESETO,
								subRuleSettingsID.intValue());
						
						
					

				}
				else
				{
					objform.setTheoryESETO(new ExamSubjectRuleSettingsTheoryESETO());
				}

			}
			
			
		}
		else
		{
			
			
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList=null;
			if (theoryESETO.getMultipleAnswerScriptList() != null
					|| theoryESETO.getMultipleAnswerScriptList().size() != 0) {
				 multipleAnswerScriptList = theoryESETO
						.getMultipleAnswerScriptList();
				
			}
			for(Integer subjectRuleId:subjectRuleIdList)
			{
				updateMultipleAnswerScriptDetails(multipleAnswerScriptList,subjectRuleId.intValue());
				
				
			}
			
			

			
			
			
			
			
		 	
		}
		
		//update subject rule settings for both the cases---either page submitted at the first time or second time
		
			
					for (Iterator it = listBO.iterator(); it.hasNext();) {
					ExamSubjectRuleSettingsBO bo =(ExamSubjectRuleSettingsBO) it
							.next();

					bo.setTheoryEseIsRegular(theoryEseIsRegular);
					bo.setTheoryEseIsMultipleAnswerScript(theoryEseIsMultipleAnswerScript);
					bo.setTheoryEseIsMultipleEvaluator(theoryEseIsMultipleEvaluator);
					bo.setTheoryEseEnteredMaxMark(theoryEseEnteredMaxMark);
					bo.setTheoryEseMaximumMark(theoryEseMaximumMark);
					bo.setTheoryEseMinimumMark(theoryEseMinimumMark);
					bo.setCreatedBy(objform.getUserId());
					bo.setModifiedBy(objform.getUserId());
					bo.setCreatedDate(new Date());

					subBOList.add(bo);
				}

				impl.update_List(subBOList);
				
		
	}
	
	
}
	
