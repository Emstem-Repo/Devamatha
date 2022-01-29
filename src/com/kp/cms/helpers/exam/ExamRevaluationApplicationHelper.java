package com.kp.cms.helpers.exam;

/**
 * Jan 13, 2010
 * Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamRevaluationApplicationBO;
import com.kp.cms.bo.exam.ExamRevaluationApplicationSubjectBO;
import com.kp.cms.bo.exam.ExamRevaluationTypeBO;
import com.kp.cms.forms.exam.ExamRevaluationApplicationForm;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;
import com.kp.cms.to.exam.ExamSubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;

public class ExamRevaluationApplicationHelper extends ExamGenHelper {

	// To get Revaluation Type
	public ArrayList<KeyValueTO> convertBOToTO_RevaluationType(
			ArrayList<ExamRevaluationTypeBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamRevaluationTypeBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		return listTO;
	}

	// To get the Students for the particular Course and Scheme
	public List<ExamRevaluationApplicationTO> convertBOToTO_student(
			ArrayList<ExamRevaluationApplicationBO> listBO, String courseName,
			int schemeNo, String registerNo, String rollNo) {
		ArrayList<ExamRevaluationApplicationTO> listTO = new ArrayList<ExamRevaluationApplicationTO>();

		for (ExamRevaluationApplicationBO bo : listBO) {
			ExamRevaluationApplicationTO to = new ExamRevaluationApplicationTO();

			if ((registerNo != null && registerNo.trim().length() > 0)
					|| (rollNo != null && rollNo.trim().length() > 0)) {
				if ((bo.getStudentUtilBO().getRegisterNo() != null && bo
						.getStudentUtilBO().getRegisterNo().equalsIgnoreCase(
								registerNo))
						|| (bo.getStudentUtilBO().getRollNo() != null && bo
								.getStudentUtilBO().getRollNo()
								.equalsIgnoreCase(rollNo))) {

					to.setId(bo.getId());
					to.setCourseName(courseName);
					to.setCourseId(bo.getCourseId());
					to.setSchemeNo(bo.getSchemeNo());
					to.setRegNumber(bo.getStudentUtilBO().getRegisterNo());
					to.setRollNumber(bo.getStudentUtilBO().getRollNo());
					to.setStudentName(bo.getStudentUtilBO().getAdmApplnUtilBO()
							.getPersonalDataUtilBO().getName());

					to.setRevaluationTypeId(bo.getRevaluationTypeId());
					if (bo.getAmount() != null) {
						to.setAmount(bo.getAmount().toString());
					}
					if (bo.getApplicationDate() != null) {
						to.setDate(bo.getApplicationDate().toString());
					}

					to.setStudentId(bo.getStudentId());
					listTO.add(to);
				}

			} else {

				to.setId(bo.getId());
				to.setCourseName(courseName);
				to.setCourseId(bo.getCourseId());
				to.setSchemeNo(bo.getSchemeNo());
				to.setRegNumber(bo.getStudentUtilBO().getRegisterNo());
				to.setRollNumber(bo.getStudentUtilBO().getRollNo());
				to.setStudentName(bo.getStudentUtilBO().getAdmApplnUtilBO()
						.getPersonalDataUtilBO().getName());

				to.setRevaluationTypeId(bo.getRevaluationTypeId());
				if (bo.getAmount() != null) {
					to.setAmount(bo.getAmount().toString());
				}
				if (bo.getApplicationDate() != null) {
					to.setDate(bo.getApplicationDate().toString());
				}

				// to.setAmount(bo.getAmount().toString());
				// to.setDate(bo.getApplicationDate().toString());
				to.setStudentId(bo.getStudentId());
				listTO.add(to);
			}

		}

		return listTO;
	}

	// To get Revaluation Subjects List
	public List<ExamRevaluationApplicationTO> convertBOToTO_EditScreen(

	ExamRevaluationApplicationTO examRevaluationApplicationTO,
			ArrayList<ExamRevaluationApplicationSubjectBO> listBO,
			ArrayList<Integer> listCheckedSubject) {

		ArrayList<ExamRevaluationApplicationTO> listTO = new ArrayList<ExamRevaluationApplicationTO>();

		for (ExamRevaluationApplicationSubjectBO bo : listBO) {
			ExamRevaluationApplicationTO to = new ExamRevaluationApplicationTO(
					examRevaluationApplicationTO);
			to.setSubjectCode(bo.getSubjectUtilBO().getCode());// get from
			// listSubject
			to.setSubjectName(bo.getSubjectUtilBO().getName());// get from
			// listSubject
			int subjectId = bo.getSubjectUtilBO().getId();
			to.setSubjectId(subjectId);// get from listSubject
			if (listCheckedSubject.contains(subjectId)) {
				to.setIsSelected(true);
			} else {
				to.setIsSelected(false);
			}

		}
		return listTO;
	}

	public ExamRevaluationApplicationTO convertBOtoTO_forID(
			ExamRevaluationApplicationBO bo) {
		ExamRevaluationApplicationTO to = new ExamRevaluationApplicationTO();
		to.setId(bo.getId());
		to.setRegNumber(bo.getStudentUtilBO().getRegisterNo());
		to.setRollNumber(bo.getStudentUtilBO().getRollNo());
		to.setStudentName(bo.getStudentUtilBO().getAdmApplnUtilBO()
				.getPersonalDataUtilBO().getName());
		to.setRevaluationTypeId(bo.getRevaluationTypeId());
		to.setAmount(bo.getAmount().toString());
		to.setDate(bo.getApplicationDate().toString());
		to.setStudentId(bo.getStudentId());

		return to;
	}

	public ArrayList<Integer> convertBOtoList(
			ArrayList<ExamRevaluationApplicationSubjectBO> select_RevaluationCheckmarks) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (ExamRevaluationApplicationSubjectBO bo : select_RevaluationCheckmarks) {
			list.add(bo.getSubjectId());
		}
		return list;
	}

	public ArrayList<ExamRevaluationApplicationTO> convertBOToTO_SubjectList(
			List select_SubjectList) {
		ExamRevaluationApplicationTO to;
		ArrayList<ExamRevaluationApplicationTO> retutnList = new ArrayList<ExamRevaluationApplicationTO>();
		Iterator itr = select_SubjectList.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamRevaluationApplicationTO();

			to.setSubjectId(Integer.parseInt(row[0].toString()));
			to.setSubjectCode(row[1].toString());
			to.setSubjectName(row[2].toString());
			retutnList.add(to);

		}

		return retutnList;
	}

	public ArrayList<ExamRevaluationApplicationTO> convertBOToTO_Student(
			List select_SubjectList) {
		ExamRevaluationApplicationTO to;
		ArrayList<ExamRevaluationApplicationTO> retutnList = new ArrayList<ExamRevaluationApplicationTO>();
		Iterator itr = select_SubjectList.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamRevaluationApplicationTO();
			to.setStudentId(Integer.parseInt(row[0].toString()));
			to.setStudentName(row[1].toString());
			retutnList.add(to);
		}
		return retutnList;
	}

	public ArrayList<ExamSubjectTO> convertBOToTO_Subjects(
			List<Object[]> select_SubjectsByRollNo) {
		ExamSubjectTO to;
		ArrayList<ExamSubjectTO> retutnList = new ArrayList<ExamSubjectTO>();
		Iterator<Object[]> itr = select_SubjectsByRollNo.iterator();

		// subject.name, subject.id, subject.code

		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamSubjectTO();
			to
					.setId(row[1] == null ? 0 : (Integer.parseInt(row[1]
							.toString())));
			to.setSubjectName(row[0] == null ? "" : row[0].toString());
			to.setSubjectCode(row[2] == null ? "" : row[2].toString());
			to.setDummyValue(false);
			retutnList.add(to);

		}
		return retutnList;
	}

	// Check This - Kishore - may not be needed
	// To get Revaluation Subjects List
	public List<ExamRevaluationApplicationTO> convertBOToTO_RevaluationSubjectList(
			ArrayList<ExamRevaluationApplicationSubjectBO> listBO) {
		ArrayList<ExamRevaluationApplicationTO> listTO = new ArrayList<ExamRevaluationApplicationTO>();
		ExamRevaluationApplicationTO to = new ExamRevaluationApplicationTO();
		for (ExamRevaluationApplicationSubjectBO bo : listBO) {
			to.setSubjectCode(bo.getSubjectUtilBO().getCode());
			to.setSubjectName(bo.getSubjectUtilBO().getName());
		}
		return listTO;
	}

	public void convertToForm(ExamRevaluationApplicationForm objform) {
		// TODO Auto-generated method stub

		ArrayList<ExamRevaluationApplicationTO> listOfStudent = new ArrayList<ExamRevaluationApplicationTO>(
				objform.getListStudentName());
		if (listOfStudent.size() > 0) {
			objform.setId(listOfStudent.get(0).getId());
			objform.setStudentName(listOfStudent.get(0).getStudentName());
			objform.setCourseName(listOfStudent.get(0).getCourseName());

			objform.setSchemeNo(Integer.toString(listOfStudent.get(0)
					.getSchemeNo()));
			objform.setRegNo(listOfStudent.get(0).getRegNumber());
			objform.setRollNo(listOfStudent.get(0).getRollNumber());

			objform.setRevaluationType(Integer.toString(listOfStudent.get(0)
					.getRevaluationTypeId()));
			if (listOfStudent.get(0).getAmount() != null) {
				objform.setRevaluationAmount(listOfStudent.get(0).getAmount());
			}

			objform.setRevaluationDate(CommonUtil.formatSqlDate(listOfStudent
					.get(0).getDate()));
			objform.setStudentId(Integer.toString(listOfStudent.get(0)
					.getStudentId()));
		}

	}

	public List<ExamSubjectTO> convertBOToTO_SubjectsEdit(
			List select_SubjectsByRollNo) {
		ExamSubjectTO to;
		ArrayList<ExamSubjectTO> retutnList = new ArrayList<ExamSubjectTO>();
		Iterator itr = select_SubjectsByRollNo.iterator();

		// subject.name, subject.id, subject.code

		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamSubjectTO();
			boolean dummyValue = false;
			if (row[3] != null) {
				dummyValue = true;
			}

			to.setId(Integer.parseInt(row[0].toString()));
			to.setSubjectName(row[1].toString());
			to.setSubjectCode(row[2].toString());
			to.setDummyValue(dummyValue);
			retutnList.add(to);

		}

		return retutnList;
	}

}
