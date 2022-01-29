package com.kp.cms.handlers.exam;

/**
 * Feb 2, 2010 Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admission.StudentEditAction;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamInternalRetestApplicationBO;
import com.kp.cms.bo.exam.ExamInternalRetestApplicationSubjectsBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.helpers.exam.ExamInternalRetestApplicationHelper;
import com.kp.cms.to.exam.ExamInternalRetestApplicationSubjectsTO;
import com.kp.cms.to.exam.ExamInternalRetestApplicationTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.transactionsimpl.exam.ExamInternalRetestApplicationImpl;

public class ExamInternalRetestApplicationHandler extends ExamGenHandler {
	private static final Log log = LogFactory.getLog(StudentEditAction.class);
	ExamInternalRetestApplicationHelper helper = new ExamInternalRetestApplicationHelper();
	ExamInternalRetestApplicationImpl impl = new ExamInternalRetestApplicationImpl();

	// On SEARCH to get the student details
	public ArrayList<ExamSupplementaryImpApplicationTO> getClassDetails(
			Integer examId, Integer classId, String rollNo, String regNo)
			throws BusinessException {

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

		return helper.converBoToTO_Student_GetDetails(impl
				.select_student_details(examId, classId, rollNo, regNo,
						rollNoPresent, regNoPresent));
	}

	// ADD - to get the subjects for a particular student
	public ExamInternalRetestApplicationTO getSubjListForStudent(
			Integer examId, Integer classId, String rollNo, String regNo)
			throws BusinessException {

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
		List<Object[]> subjectDetails = impl.get_subjectsList(examId, rollNo,
				regNo);
		List<Object[]> examDetails = impl.get_examDetails(examId, classId,
				rollNo, regNo);
		return helper.convertBOToTO_details(subjectDetails, examDetails, "");
	}

	// On EDIT - to get student details
	public ExamInternalRetestApplicationTO getToForEditedStudent(int id) throws Exception {

		List<Object[]> subjectDetails = impl.get_subjectsList(id);
		List<Object[]> examDetails = impl.get_examDetails(id);
		return helper
				.convertBOToTO_details(subjectDetails, examDetails, "edit");

	}

	// To add and update
	public void addSubjectDetails(Integer id, ExamInternalRetestApplicationTO to) throws Exception {

		ArrayList<ExamInternalRetestApplicationSubjectsTO> subjList = to
				.getSubjectList();
		Integer rid = id;
		int flag = 0;
		for (ExamInternalRetestApplicationSubjectsTO t : subjList) {
			int theory = 0;
			int practical = 0;

			if (t.getTheoryAppeared() != null
					&& t.getTheoryAppeared().equalsIgnoreCase("on")) {
				theory = 1;
			}
			if (t.getPracticalAppeared() != null
					&& t.getPracticalAppeared().equalsIgnoreCase("on")) {
				practical = 1;
			}
			if (rid == null || rid == 0) {

				ExamInternalRetestApplicationBO objBO = new ExamInternalRetestApplicationBO(to
						.getAcademicYear(), Integer.parseInt(to.getExamId()),
						to.getStudentId(), to.getClassId(), to.getChance());

				rid = impl.insert_returnId(objBO);
				flag = 1;
			} else {

				impl.updateSubjectDetails(id, Integer
						.parseInt(t.getSubjectId()), t.getFees(), theory,
						practical, to.getChance());
			}
			if (flag == 1) {
				impl.insert(new ExamInternalRetestApplicationSubjectsBO(rid,
						Integer.parseInt(t.getSubjectId()), t.getFees(),
						theory, practical));
			}

		}

	}

	// To DELETE
	public void deleteInternalRetestApplication(int id) throws Exception {
		impl.deleteIntAppl(id);

	}

	// To get class name based on examId & roll/register no
	public Map<Integer, String> getClassesByExamNameRegNoRollNo(int examId,
			String rollNo, String regNo) {

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
				try {
					throw new BusinessException("Inconsistent Roll and Reg No");
				} catch (BusinessException e) {
					log.error(
							"error in classesbyExamNameRegNoRollNo method...",
							e);
				}
			}
		}

		return helper.convertTOToMapClass(impl.getClassByExamNameRegNoOnly(
				examId, rollNo, regNo, rollNoPresent, regNoPresent));
	}

	// To get class name based on classId
	public String getClassNameById(int classId) throws Exception {
		ClassUtilBO c = ((ClassUtilBO) impl.select_Unique(classId,
				ClassUtilBO.class));
		if (c != null)
			return c.getName();
		return "";
	}

}
