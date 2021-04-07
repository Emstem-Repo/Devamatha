package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.exam.ExamRevaluationApplicationBO;
import com.kp.cms.bo.exam.ExamRevaluationApplicationSubjectBO;
import com.kp.cms.bo.exam.ExamRevaluationTypeBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamRevaluationApplicationForm;
import com.kp.cms.helpers.exam.ExamRevaluationApplicationHelper;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;
import com.kp.cms.to.exam.ExamSubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamRevaluationApplicationImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamRevaluationApplicationHandler extends ExamGenHandler {

	ExamRevaluationApplicationImpl impl = new ExamRevaluationApplicationImpl();

	ExamRevaluationApplicationHelper helper = new ExamRevaluationApplicationHelper();

	// Dropdown: To get Revaluation Type
	@SuppressWarnings("unchecked")
	public ArrayList<KeyValueTO> getRevaluationTypeList() {
		ArrayList<ExamRevaluationTypeBO> lBO = new ArrayList(impl
				.select_ActiveOnly(ExamRevaluationTypeBO.class));
		return helper.convertBOToTO_RevaluationType(lBO);
	}

	// Button SEARCH: To get the Students for the particular Course and Scheme
	@SuppressWarnings("unchecked")
	public List<ExamRevaluationApplicationTO> getStudent_data(int examId,
			int courseId, int schemeNo, String courseName, String registerNo,
			String rollNo) throws Exception {
		boolean regPresent = false;
		boolean rollPresent = false;
		if (rollNo != null && rollNo.length() > 0) {
			rollPresent = true;

		}
		if (registerNo != null && registerNo.length() > 0) {
			regPresent = true;

		}
		if (regPresent && rollPresent) {
			if (!validate_Stu_rollReg(rollNo, registerNo)) {
				throw new BusinessException();
			}
		}
		return helper.convertBOToTO_student(new ArrayList(impl.select(examId,
				courseId, schemeNo)), courseName, schemeNo, registerNo, rollNo);
	}

	public ArrayList<ExamRevaluationApplicationTO> getStudent_RollRegNo(
			String rollNo, String registerNo) throws BusinessException {
		return helper.convertBOToTO_Student(impl.select_Student(rollNo,
				registerNo));
	}

	// public ArrayList<ExamSubjectTO> getSubjects(String rollNo, String regNo)
	// throws BusinessException {
	// return helper.convertBOToTO_Subjects(impl.select_SubjectsByRollNo(
	// rollNo, regNo));
	// }

	// To Add
	public void add(ExamRevaluationApplicationForm objform) throws Exception {

		BigDecimal amount = null;
		if (objform.getRevaluationAmount() != null
				&& objform.getRevaluationAmount().trim().length() > 0) {
			amount = new BigDecimal(objform.getRevaluationAmount());
		}
		ExamRevaluationApplicationBO objBO = new ExamRevaluationApplicationBO(
				getInt(objform.getExamNameId_value()), getInt(objform
						.getCourseId()), getInt(objform.getSchemeNo()),
				getInt(objform.getStudentId()), amount, new Date(),
				getInt(objform.getRevaluationType()), objform.getUserId());
		int id = 0;
		id = impl.insert_returnId(objBO);
		List<ExamSubjectTO> listSubjects = objform.getListSubject();

		for (ExamSubjectTO examSubjectTO : listSubjects) {
			if (examSubjectTO.getValue() != null) {
				impl.insert(new ExamRevaluationApplicationSubjectBO(id,
						examSubjectTO.getId()));
			}

		}

	}

	public ArrayList<ExamSubjectTO> getSubjects(Integer courseId,
			Integer examId, Integer schemeNo) throws BusinessException {
		return helper.convertBOToTO_Subjects(impl.select_SubjectsForAdd(
				courseId, examId, schemeNo));
	}

	private List<ExamSubjectTO> getSubjectsEdit(Integer courseId,
			Integer examId, Integer schemeNo, Integer revalTypeId) {
		return helper.convertBOToTO_SubjectsEdit(impl
				.select_SubjectsForCourseScheme(courseId, examId, schemeNo,
						revalTypeId));
	}

	// To Update
	public void update(ExamRevaluationApplicationForm objform,
			List<ExamSubjectTO> listCheckedSub) throws Exception {

		ExamRevaluationApplicationSubjectBO objSubBO = null;

		impl.updateRevalApp(objform.getId(), getInt(objform
				.getRevaluationType()), objform.getRevaluationAmount(), objform
				.getUserId(), CommonUtil.ConvertStringToDate(objform
				.getRevaluationDate()));

		impl.deleteRevalAppSub(objform.getId());

		for (ExamSubjectTO examSubjectTO : listCheckedSub) {
			if (examSubjectTO.getValue() != null) {
				objSubBO = new ExamRevaluationApplicationSubjectBO(objform
						.getId(), examSubjectTO.getId());
				impl.insert(objSubBO);
			}
		}

	}

	// To Delete
	public void delete(int id, String userId) throws Exception {
		impl.deleteRevalAppSub(id);
		impl.delete_IExamGenBO(id, userId, ExamRevaluationApplicationBO.class);
	}

	public ExamRevaluationApplicationForm getDetailsToAdd(
			ExamRevaluationApplicationForm objform) throws Exception {

		int examId = 0;
		if (!(objform.getExamNameId() == null
				|| objform.getExamNameId().isEmpty() || objform.getExamNameId()
				.length() == 0)) {
			examId = Integer.parseInt(objform.getExamNameId());
		} else {
			examId = Integer.parseInt(objform.getExamNameId_value());
		}

		int courseId = Integer.parseInt(objform.getCourseId());
		Integer schemeId = null;
		if (schemeId != null) {
			Integer.parseInt(objform.getSchemeId());
		}
		int schemeNo = Integer.parseInt(objform.getSchemeNo());
		String rollNo = objform.getRollNo().trim();
		String regNo = objform.getRegNo().trim();
		boolean regPresent = false;
		boolean rollPresent = false;
		if (rollNo != null && rollNo.length() > 0) {
			rollPresent = true;
		}
		if (regNo != null && regNo.length() > 0) {
			regPresent = true;
		}
		if (regPresent && rollPresent) {
			if (!validate_Stu_rollReg(rollNo, regNo)) {
				throw new BusinessException();
			}
		}
		StudentUtilBO studentBO;

		studentBO = impl.select_student_Only(rollNo, regNo, rollPresent);

		int studentId = studentBO.getId();
		rollNo = studentBO.getRollNo();
		regNo = studentBO.getRegisterNo();
		if (studentId == 0) {
			throw new BusinessException();
		}
		String studentName = studentBO.getAdmApplnUtilBO()
				.getPersonalDataUtilBO().getFirstName();
		if (studentBO.getAdmApplnUtilBO().getPersonalDataUtilBO().getLastName() != null) {

			studentName = studentName.concat(" ").concat(
					studentBO.getAdmApplnUtilBO().getPersonalDataUtilBO()
							.getLastName());
		}
		objform.setStudentName(studentName);

		String courseName = getCourseName(courseId);

		objform.setListStudentName(getStudent_data(examId, courseId, schemeNo,
				courseName, regNo, rollNo));

		objform.setListRevaluationType(getRevaluationTypeList());
		ExamRevaluationApplicationBO e;

		e = impl.getRevaluationApplicationid(examId, studentId, courseId,
				schemeNo);
		objform.setId(0);
		int id = 0;
		if (e != null) {
			id = e.getId();
		}

		if (id != 0) {

			objform.setListSubject(getSubjectsEdit(courseId, examId, schemeNo,
					id));
			helper.convertToForm(objform);

		} else {

			objform.setListSubject(getSubjects(courseId, examId, schemeNo));
			objform.setRevaluationDate(getCurrentDate());
		}

		objform.setStudentId(Integer.toString(studentId));
		objform.setExamName(getExamNameByExamId(examId));
		objform.setCourseName(courseName);
		objform.setSchemeName(Integer.toString(schemeNo));

		objform.setExamNameId_value(Integer.toString(examId));
		objform.setCourseId(objform.getCourseId());

		objform.setSchemeNo(objform.getSchemeNo());
		return objform;

	}

	private String getCurrentDate() {
		int dt = new Date().getDate();
		String date = "";
		if (dt < 10) {
			date = "0" + Integer.toString(dt);
		} else {
			date = Integer.toString(dt);
		}
		int mt = new Date().getMonth() + 1;
		String month = "";
		if (mt < 10) {
			month = "0" + Integer.toString(mt);
		} else {
			month = Integer.toString(mt);
		}
		return date + "/" + month + "/"
				+ Integer.toString(new Date().getYear() + 1900);
	}

	public ExamRevaluationApplicationForm getDetailsToEdit(
			ExamRevaluationApplicationForm objform) throws Exception {

		int courseId = Integer.parseInt(objform.getCourseId());
		// int schemeId = Integer.parseInt(objform.getSchemeId());
		int schemeNo = Integer.parseInt(objform.getSchemeNo());
		String rollNo = objform.getRollNo().trim();
		String regNo = objform.getRegNo().trim();
		String courseName = getCourseName(courseId);
		int examId = Integer.parseInt(objform.getExamNameId());
		int id = objform.getId();
		objform.setListSubject(getSubjectsEdit(courseId, examId, schemeNo, id));
		// After Change********************
		objform.setExamName(getExamNameByExamId(examId));
		objform.setCourseName(courseName);
		objform.setSchemeName(Integer.toString(schemeNo));
		boolean rollPresent = false;
		StudentUtilBO s;

		s = impl.select_student_Only(rollNo, regNo, rollPresent);

		int studentId = s.getId();
		String a = s.getAdmApplnUtilBO().getPersonalDataUtilBO().getFirstName();
		if (s.getAdmApplnUtilBO().getPersonalDataUtilBO().getLastName() != null) {
			a = a.concat(" ")
					.concat(
							s.getAdmApplnUtilBO().getPersonalDataUtilBO()
									.getLastName());
		}
		objform.setStudentName(a);
		objform.setId(id);
		objform.setStudentId(Integer.toString(studentId));

		objform.setRegNo(regNo);
		objform.setExamNameId_value(Integer.toString(examId));
		objform.setCourseId(Integer.toString(courseId));
		objform.setSchemeNo(Integer.toString(schemeNo));
		objform.setListRevaluationType(getRevaluationTypeList());
		ExamRevaluationApplicationBO e;
		e = impl.getDetails(id);
		objform.setRevaluationType(Integer.toString(e.getRevaluationTypeId()));
		objform.setRevaluationAmount(e.getAmount().toString());
		objform.setRevaluationDate(CommonUtil.formatSqlDate(e
				.getApplicationDate().toString()));
		return objform;

	}

	// private List<ExamSubjectTO> getSubjectsEdit(String rollNo, String regNo,
	// int revaluactionTypeId) {
	// return helper.convertBOToTO_SubjectsEdit(impl.select_SubjectsByRollNo(
	// rollNo, regNo, revaluactionTypeId));
	// }

	public ExamRevaluationApplicationForm getSearchDetails(
			ExamRevaluationApplicationForm objform) throws Exception {
		int examId = 0;
		if (objform.getExamNameId_value() == null
				|| objform.getExamNameId_value().isEmpty()
				|| objform.getExamNameId_value().length() == 0) {
			examId = Integer.parseInt(objform.getExamNameId());
		} else {
			examId = Integer.parseInt(objform.getExamNameId_value());
		}
		int courseId = Integer.parseInt(objform.getCourseId());
		int schemeNo = 0;
		if (!objform.getSchemeNo().isEmpty()) {
			schemeNo = Integer.parseInt(objform.getSchemeNo());
		}
		String rollNo = objform.getRollNo().trim();
		String regNo = objform.getRegNo().trim();
		String courseName = getCourseName(courseId);

		objform.setListStudentName(getStudent_data(examId, courseId, schemeNo,
				courseName, regNo, rollNo));

		objform.setExamName(getExamNameByExamId(examId));
		objform.setCourseName(courseName);
		if (schemeNo != 0) {
			objform.setSchemeName(getSchemeName(schemeNo));
		}
		objform.setRollNo(rollNo);
		objform.setRegNo(regNo);
		objform.setExamNameId_value(objform.getExamNameId());
		objform.setCourseId(objform.getCourseId());
		objform.setSchemeNo(objform.getSchemeNo());

		return objform;
	}

	private int getInt(String schemeNo) {
		try {
			return Integer.parseInt(schemeNo);
		} catch (Exception e) {
		}
		return 0;
	}

	public ExamRevaluationApplicationForm retainValues(
			ExamRevaluationApplicationForm objform) {
		// TODO Auto-generated method stub
		return null;
	}

}
