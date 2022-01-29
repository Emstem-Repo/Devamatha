package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryDetailsBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamInternalMarksSupplementaryForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.helpers.exam.ExamInternalMarkSupplementaryHelper;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IExamInternalMarkSupplementaryTransaction;
import com.kp.cms.transactionsimpl.exam.ExamInternalMarkSupplementaryImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamInternalMarkSupplementaryHandler extends ExamGenHandler {
	/**
	 * Singleton object of NewInternalMarksSupplementaryHandler
	 */
	private static volatile ExamInternalMarkSupplementaryHandler examInternalMarkSupplementaryHandler = null;
	private static final Log log = LogFactory.getLog(ExamInternalMarkSupplementaryHandler.class);
	/**
	 * return singleton object of NewInternalMarksSupplementaryHandler.
	 * @return
	 */
	public static ExamInternalMarkSupplementaryHandler getInstance() {
		if (examInternalMarkSupplementaryHandler == null) {
			examInternalMarkSupplementaryHandler = new ExamInternalMarkSupplementaryHandler();
		}
		return examInternalMarkSupplementaryHandler;
	}
	ExamInternalMarkSupplementaryHelper helper = new ExamInternalMarkSupplementaryHelper();
	ExamInternalMarkSupplementaryImpl impl = new ExamInternalMarkSupplementaryImpl();
	IExamInternalMarkSupplementaryTransaction transaction = new ExamInternalMarkSupplementaryImpl();
	Map<Integer, ExamInternalMarksSupplementaryTO> examInternalMarksSupplementaryMap=null;
	// To get the supplementary exam name list
	public ArrayList<KeyValueTO> getSupplementaryExamName(int year) throws Exception {
		ArrayList<KeyValueTO> list = helper.convertBOToTO(impl.getSupplementaryExamName(year));
		return list;
	}

	// To get subjects and marks for a student
	public List<ExamInternalMarksSupplementaryTO> getSubjectInternalSupMarks(
			int courseId, String type, String rollNo, String regNo,
			int schemeNo, int examId) throws Exception {

		boolean rollNoPresent = false;
		boolean regNoPresent = false;

		if (rollNo != null && rollNo.length() > 0) {
			rollNoPresent = true;
		}
		if (regNo != null && regNo.length() > 0) {
			regNoPresent = true;
		}
		if (rollNoPresent && regNoPresent) {
			if (!validate_Stu_rollReg(rollNo, regNo)) {
				throw new BusinessException("Inconsistent Roll and Reg No");
			}
		}

		StudentUtilBO s;
		s = impl.select_student_Only(rollNo, regNo, rollNoPresent);
		if (s != null) {
			rollNo = s.getRollNo();
			regNo = s.getRegisterNo();
		}

		 examInternalMarksSupplementaryMap =helper.convertBOToTOList(transaction.select_InternalSuppMarksToDisplay(courseId, rollNo, regNo, schemeNo, examId));
		 return helper.setBOListToTOList(transaction.select_OverAllInternalMarksToDisplay(courseId, rollNo, regNo, schemeNo, examId), examInternalMarksSupplementaryMap);		
	}

	public String getStudentId(String rollNo, String regNo) throws Exception {
		Integer l = impl.getStudentId(rollNo, regNo);
		return l.toString();
	}

	public void add(ExamInternalMarksSupplementaryForm objform, String studentId)
			throws Exception {
		List<ExamInternalMarkSupplementaryDetailsBO> examInternalMarkSupplementaryDetailsBOs =	helper.convertTOToBO(objform,studentId,examInternalMarksSupplementaryMap);
		transaction.saveExamInternalMarkDetails(examInternalMarkSupplementaryDetailsBOs);
	}

	public ExamInternalMarksSupplementaryForm retainAllValues(
			ExamInternalMarksSupplementaryForm objform) throws Exception {
//		CommonAjaxExamHandler commHandler = new CommonAjaxExamHandler();
//		objform.setListExamName(getSupplementaryExamName(Integer.parseInt(objform.getYear())));
//		if (CommonUtil.checkForEmpty(objform.getExamNameId())) {
//			Map<Integer, String> courseList = commHandler
//					.getCourseByExamName(objform.getExamNameId());
//			if (courseList != null && courseList.size() > 0)
//				objform.setCourseList(courseList);
//		}
//		if (CommonUtil.checkForEmpty(objform.getCourseId())
//				&& CommonUtil.checkForEmpty(objform.getExamNameId())) {
//			Map<String, String> schemeList = commHandler
//					.getSchemeNoByExamIdCourseId(Integer.parseInt(objform
//							.getExamNameId()), Integer.parseInt(objform
//							.getCourseId()));
//			if (schemeList != null && schemeList.size() > 0)
//				objform.setSchemeList(schemeList);
//		}
		return objform;
	}
}
