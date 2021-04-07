package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamStudentEligibilityCheckBO;
import com.kp.cms.bo.exam.ExamStudentEligibilityCheckCriteriaBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.forms.exam.ExamStudentEligibilityCheckForm;
import com.kp.cms.helpers.exam.ExamStudentEligibilityCheckHelper;
import com.kp.cms.to.exam.ExamExamEligibilityTO;
import com.kp.cms.to.exam.ExamStudentEligibilityCheckTO;
import com.kp.cms.to.exam.ExamStudentEligibilityMapTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamStudentEligibilityCheckImpl;

@SuppressWarnings("unchecked")
public class ExamStudentEligibilityCheckHandler extends ExamGenHandler {

	ExamStudentEligibilityCheckHelper helper = new ExamStudentEligibilityCheckHelper();
	ExamStudentEligibilityCheckImpl impl = new ExamStudentEligibilityCheckImpl();
	ArrayList<KeyValueTO> additionalEgibilityHeadings;

	public ArrayList<ExamStudentEligibilityCheckTO> search(
			ArrayList<Integer> listClass, int examTypeId, String display) throws Exception {

		return helper.convertBOToTO_Students(impl.select_students_elig_nonElig(
				listClass, examTypeId, display));
	}

	public ArrayList<KeyValueTO> getListExamType() throws Exception {
		ArrayList<ExamTypeUtilBO> listBO = impl.getListExamType();
		return helper.convertBOToTO_ExamType_KeValueTO(listBO);
	}

	public ArrayList<ExamStudentEligibilityCheckTO> getStudentList(
			ArrayList<Integer> listClass, int examId, int examTypeId,
			char display) throws Exception {
		ExamStudentEligibilityMapTO mapTo = new ExamStudentEligibilityMapTO();
		ExamStudentEligibilityCheckTO valTo = null, detailTo = null;
		ArrayList<ExamStudentEligibilityCheckTO> listOfStudents = new ArrayList<ExamStudentEligibilityCheckTO>();
		boolean courseDisable = false, examDisable = false, attendenceDisable = false;
		for (Integer classId : listClass) {
			courseDisable = getCourseFeesChecked_forClass(classId);
			examDisable = getExamFeesChecked_forClass(classId);
			attendenceDisable = getAttendanceChecked_forClass(classId);
			ArrayList<Integer> students = get_students_for_class(classId);
			mapTo.setAdditionalElgibilityMap(getListAdditionalEligibility(
					classId, examTypeId));
			mapTo.setCourseFeeMap(getCourseFee_status(classId));
			mapTo.setAttendenceMap(getAttendanceMapForStudents(classId));
			mapTo.setStudentDetailsMap(get_eligCheck_details_student(classId));
			if (students != null && students.size() > 0) {
				for (Iterator iterator = students.iterator(); iterator
						.hasNext();) {
					Integer id = (Integer) iterator.next();
					valTo = new ExamStudentEligibilityCheckTO();
					detailTo = mapTo.getstudentDetails(id);
					valTo.setRollNo(detailTo.getRollNo());
					valTo.setRegNO(detailTo.getRegNO());
					valTo.setStudentName(detailTo.getStudentName());
					valTo.setClassCode(detailTo.getClassCode());
					valTo.setDummycourseFees(mapTo.getCourseFee(id));
					valTo.setRowId(detailTo.getRowId());
					valTo.setClassId(classId);
					valTo.setStudentId(id);
					valTo.setDummycourseFeeDisable(courseDisable);
					valTo.setDummyexamFeeDisable(examDisable);
					valTo.setDummyattendanceDisable(attendenceDisable);
					valTo.setDummyexamFees(true);
					valTo.setDummyattendance(mapTo.getAttendence(id));
					valTo.setRemarks(detailTo.getRemarks());
					ArrayList<ExamExamEligibilityTO> makeAdjustableMap = makeAdjustableMap(mapTo
							.getAdditionalFeeList(id));
					if (makeAdjustableMap != null) {
						valTo.setListOfEligibility(makeAdjustableMap);
					} else {
						valTo.setListOfEligibility(getDummyArrayList());
					}
					if (detailTo.getRowId() != null) {
						valTo.setDummyexamElgibility(detailTo
								.getDummyexamElgibility());
					} else {
						valTo
								.setDummyexamElgibility(getElgbilityTickBasedOnCount(valTo));
					}
					valTo.setCheckExam(valTo.getDummyexamElgibility());
					if (getStudentDetailsOnDisplay(valTo, display) != null) {
						listOfStudents.add(valTo);
					}
				}
			}

		}

		return listOfStudents;
	}

	private HashMap<Integer, Integer> getAttendanceMapForStudents(
			Integer classId) throws Exception {
		return impl.getAttendanceMapForStudents(classId);
	}

	private String getStudentDetailsOnDisplay(
			ExamStudentEligibilityCheckTO valTo, char display) throws Exception {
		String value = null;
		switch (display) {
		case 'E':
			if (valTo.getDummyexamElgibility()) {
				value = "1";
			}
			break;
		case 'N':
			if (!valTo.getDummyexamElgibility()) {
				value = "1";
			}
			break;
		default:
			value = "1";
			break;
		}
		return value;
	}

	private boolean getElgbilityTickBasedOnCount(
			ExamStudentEligibilityCheckTO valTo) throws Exception {
		int count = 0, elgbilityCount = 0;
		ArrayList<ExamExamEligibilityTO> listOfTo = valTo
				.getListOfEligibility();
		if (!valTo.getDummycourseFeeDisable()) {
			elgbilityCount++;
			if (valTo.getDummycourseFees())
				count++;
		}
		if (!valTo.getDummyattendanceDisable()) {
			elgbilityCount++;
			if (valTo.getDummyattendance())
				count++;
		}
		if (!valTo.getDummyexamFeeDisable()) {
			elgbilityCount++;
			if (valTo.getDummyexamFees())
				count++;
		}
		for (ExamExamEligibilityTO to : listOfTo) {
			if (!to.getDummyAdditionalDisable()) {
				elgbilityCount++;
				if (to.getDummyAdditionalFee())
					count++;
			}
		}

		if (elgbilityCount == count) {
			return true;
		}
		return false;
	}

	private ArrayList<ExamExamEligibilityTO> getDummyArrayList() throws Exception {
		int size = additionalEgibilityHeadings.size();
		ArrayList<ExamExamEligibilityTO> dummyList = new ArrayList<ExamExamEligibilityTO>();
		ExamExamEligibilityTO to = null;
		for (int i = 0; i < size; i++) {
			to = new ExamExamEligibilityTO();
			to.setId(0);
			to.setDetailId(-1);
			to.setDummyAdditionalDisable(true);
			dummyList.add(to);
		}
		return dummyList;
	}

	private ArrayList<ExamExamEligibilityTO> makeAdjustableMap(
			ArrayList<ExamExamEligibilityTO> additionalFeeListOfSingleStudent) throws Exception {

		ArrayList<Integer> listId = new ArrayList<Integer>();
		if (additionalFeeListOfSingleStudent != null) {
			for (ExamExamEligibilityTO to : additionalFeeListOfSingleStudent) {
				listId.add(to.getId());
			}
		}
		ArrayList<KeyValueTO> val = additionalEgibilityHeadings;

		ExamExamEligibilityTO dummyTo = null;
		for (Iterator iterator = val.iterator(); iterator.hasNext();) {
			KeyValueTO to = (KeyValueTO) iterator.next();
			if (additionalFeeListOfSingleStudent != null
					&& !listId.contains(to.getId())) {

				dummyTo = new ExamExamEligibilityTO();
				dummyTo.setDummyAdditionalDisable(true);
				additionalFeeListOfSingleStudent.add(dummyTo);
			}
		}
		return additionalFeeListOfSingleStudent;
	}

	// To FETCH additional eligibility list for class
	public ArrayList<KeyValueTO> getAddEligibility(List<Integer> classId,
			Integer examTypeId) throws Exception {
		additionalEgibilityHeadings = helper
				.convertBOToTO_additionalEligibility(impl
						.getAdditionalEgibility_listFor_class(classId,
								examTypeId));
		return additionalEgibilityHeadings;

	}

	public ExamStudentEligibilityCheckForm getUpdatableForm(
			ExamStudentEligibilityCheckForm objform) throws Exception {
		if (objform.getDisplayFor() != null) {
			if (objform.getDisplayFor().equalsIgnoreCase("E")) {
				objform.setDisplay("Eligible");
			}
			if (objform.getDisplayFor().equalsIgnoreCase("N")) {
				objform.setDisplay("Not Eligible");
			}
			if (objform.getDisplayFor().equalsIgnoreCase("B")) {
				objform.setDisplay("Both");
			}
		}
		return objform;
	}

	// To get course for a class
	public Integer get_course_for_class(int classId) throws Exception {

		return impl.get_course_for_class(classId);
	}

	// To get academic year for a class
	public Integer get_academicYear_for_class(int classId) throws Exception {

		return impl.get_academicYear_for_class(classId);
	}

	// To get students for a class
	public ArrayList<Integer> get_students_for_class(int classId) throws Exception {

		return impl.get_students_for_class(classId);
	}

	// To FETCH the students and fee payment status
	public HashMap<Integer, Integer> getCourseFee_status(Integer classId) throws Exception {

		Integer course = get_course_for_class(classId);
		Integer academicYear = get_academicYear_for_class(classId);
		List<Integer> students = get_students_for_class(classId);
		return helper.convertListToHashMap(impl.get_eligCheck_details(course,
				academicYear, students));
	}

	// To FETCH the students and fee payment status
	public HashMap<Integer, ExamStudentEligibilityCheckTO> get_eligCheck_details_student(
			Integer classId) throws Exception {

		Integer course = get_course_for_class(classId);
		Integer academicYear = get_academicYear_for_class(classId);
		List<Integer> students = get_students_for_class(classId);
		return helper.convertListToTO(impl.get_studentDetails_courseFees(
				course, academicYear, students));
	}

	public void updateStudentElegibility(ExamStudentEligibilityCheckForm objform) throws Exception {
		ArrayList<ExamStudentEligibilityCheckTO> listStudent = objform
				.getListStudent();
		int examId = objform.getExamId();
		int examTypeId = objform.getExamTypeId();
		Integer elgibilityEntryId = null;
		ExamStudentEligibilityCheckBO mainBO = null;
		for (ExamStudentEligibilityCheckTO objTo : listStudent) {
			elgibilityEntryId = objTo.getRowId();
			mainBO = new ExamStudentEligibilityCheckBO();
			mainBO.setId(elgibilityEntryId);
			mainBO.setExamId(examId);
			mainBO.setExamTypeId(examTypeId);
			mainBO.setClassId(objTo.getClassId());
			mainBO.setIsAttendance(getIntValue(objTo.getAttendance()));
			mainBO.setIsCourseFeesPaid(getIntValue(objTo.getCourseFees()));
			mainBO.setIsExamFeesPaid(getIntValue(objTo.getExamFees()));
			mainBO.setIsExamEligibile(getIntValue(objTo.getExamElgibility()));
			mainBO.setStudentId(objTo.getStudentId());
			mainBO.setRemarks(objTo.getRemarks());

			ExamStudentEligibilityCheckCriteriaBO detailBo = null;
			ArrayList<ExamExamEligibilityTO> additionalEligibility = objTo
					.getListOfEligibility();
			if (elgibilityEntryId == null || elgibilityEntryId < 1) {
				elgibilityEntryId = impl.insert_returnId(mainBO);
				if (additionalEligibility != null) {
					for (ExamExamEligibilityTO to : additionalEligibility) {
						if (to.getId() != null && to.getId() > 0) {
							detailBo = new ExamStudentEligibilityCheckCriteriaBO();
							detailBo.setEligibilityCriteriaId(to.getId());
							detailBo.setStuEligibilityChkId(elgibilityEntryId);
							detailBo
									.setIsEligibile(getIntValue(to.getDisplay()));
							impl.insert(detailBo);
						}
					}
				}
			} else {
				impl.update(mainBO);
				if (additionalEligibility != null) {
					ArrayList<ExamStudentEligibilityCheckCriteriaBO> bo = new ArrayList<ExamStudentEligibilityCheckCriteriaBO>();
					for (ExamExamEligibilityTO to : additionalEligibility) {
						detailBo = new ExamStudentEligibilityCheckCriteriaBO();
						detailBo.setEligibilityCriteriaId(to.getId());
						detailBo.setStuEligibilityChkId(elgibilityEntryId);
						detailBo.setIsEligibile(getIntValueForAdditional(to
								.getDisplay()));
						detailBo.setId(to.getDetailId());
						if (to.getDetailId() != null && to.getDetailId() > 0) {
							bo.add(detailBo);
						} else if (to.getDetailId() == null) {
							impl.insert(detailBo);
						}
					}
					impl.update_List(bo);
				}
			}

		}

	}

	private int getIntValueForAdditional(String display) throws Exception {

		if (display != null && display.equalsIgnoreCase("on")) {
			return 1;
		}
		return 0;
	}

	private int getIntValue(String attendance) {

		return (attendance != null) ? 1 : 0;
	}

	// To FETCH additional eligibility list for a student
	public HashMap<Integer, ArrayList<ExamExamEligibilityTO>> getListAdditionalEligibility(
			Integer classId, Integer examTypeId) throws Exception {
		ArrayList<Integer> studentId = get_students_for_class(classId);
		return helper.convertBOToTO_checkAdditionalEligibility_list(impl
				.getListAdditionalEligibility_list(classId, examTypeId,
						studentId));
	}

	// To get Exam Fees Status for a class
	public boolean getExamFeesChecked_forClass(Integer classId) throws Exception {

		return impl.getExamFeesChecked_forClass(classId);
	}

	// To get Course Fees Status for a class
	public boolean getCourseFeesChecked_forClass(Integer classId) throws Exception {

		return impl.getCourseFeesChecked_forClass(classId);
	}

	// To get Attendance Status for a class
	public boolean getAttendanceChecked_forClass(Integer classId) throws Exception {

		return impl.getAttendanceChecked_forClass(classId);
	}

	public String getClassNameById(int classId) throws Exception {
		ClassUtilBO c = ((ClassUtilBO) impl.select_Unique(classId,
				ClassUtilBO.class));
		if (c != null)
			return c.getName();
		return "";
	}

	public String getCurrentexamName() throws Exception {

		return impl.getCurrentExamName();
	}
}
