package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamAssignOverallMarksBO;
import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamAssignmentOverallMarksForm;
import com.kp.cms.helpers.exam.ExamAssignOverallMarksHelper;
import com.kp.cms.to.exam.ExamAssignOverallMarksTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamAssignOverallMarksImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamAssignOverallMarksHandler extends ExamGenHandler {

	ExamAssignOverallMarksHelper helper = new ExamAssignOverallMarksHelper();
	ExamAssignOverallMarksImpl impl = new ExamAssignOverallMarksImpl();

	// To get schemeNo for a course & exam
	public Map<String, String> getSchemeNoBy_ExamId_CourseId(int examId,
			int courseId) {
		return helper.convertBOtoTO_SchemeNoBy_ExamId_CourseId(impl
				.select_SchemeNoBy_ExamId_CourseId(examId, courseId));
	}

	// To get students for course, scheme & subject
	public ArrayList<ExamAssignOverallMarksTO> getStudents_List(int courseId,
			int schemeId, int subjectId, int subjectType, Integer examId,
			String assignMentOverall, Integer Assinmenttypeid, int schemeNO,
			Integer academicYear, String isPreviousExam, String examType) throws BusinessException {

		return helper.convertBOToTO_Students_List(impl
				.select_Students_course_Scheme_Subj(courseId, schemeId,
						subjectId, subjectType, schemeNO, assignMentOverall,
						examId, Assinmenttypeid, academicYear, isPreviousExam, examType), examId,
				courseId, subjectId, assignMentOverall, Assinmenttypeid,
				subjectType, schemeNO );
	}

	// On - SUBMIT(Update)
	public void add(List<ExamAssignOverallMarksTO> list, String userId,
			int subjecttypeId) throws Exception {

		ArrayList<ExamAssignOverallMarksBO> overAllAssignlist = new ArrayList<ExamAssignOverallMarksBO>();
		ArrayList<ExamStudentOverallInternalMarkDetailsBO> overallList = new ArrayList<ExamStudentOverallInternalMarkDetailsBO>();
		ArrayList<ExamStudentFinalMarkDetailsBO> finalMarkList = new ArrayList<ExamStudentFinalMarkDetailsBO>();
		for (ExamAssignOverallMarksTO to : list) {

			String theoryMarks = null;
			if (to.getTheoryMarks() != null
					&& to.getTheoryMarks().trim().length() > 0) {
				theoryMarks = to.getTheoryMarks().trim();
			}

			String practicalMarks = null;
			if (to.getPracticalMarks() != null
					&& to.getPracticalMarks().trim().length() > 0) {
				practicalMarks = to.getPracticalMarks().trim();
			}
			BigDecimal theoryMark = null;
			BigDecimal practicalMark = null;
			//if (subjecttypeId == 1 || subjecttypeId == 11) {
				if (theoryMarks != null && theoryMarks.length() > 0) {
					theoryMark = new BigDecimal(theoryMarks);
				}

			//}
			//if (subjecttypeId == 0 || subjecttypeId == 11) {
				if (practicalMarks != null && practicalMarks.length() > 0) {
					practicalMark = new BigDecimal(practicalMarks);
				}

			//}

			if (to.getId() != 0) {
				if (to.getOverallName().equals("overall")) {
					if(to.getOverallId() == 1){
						impl.update_OverallMarks(to.getId(), theoryMark, practicalMark,
								userId);
					}else
					{
						impl.updateStudentFinalMarks(to.getId(), theoryMark, practicalMark,
								userId);
					}
				}else{
					impl.updateOverallAssignmentMarks(to.getId(), theoryMark, practicalMark,
						userId);
				}
				
			} else {
				if (to.getOverallName().equals("overall")) {
					if(to.getOverallId() == 1){
						ExamStudentOverallInternalMarkDetailsBO bo = new ExamStudentOverallInternalMarkDetailsBO(to.getClassId(), to.getExamId(),
								"", null, null,	to.getPracticalMarks(), to.getDummyStudentId(),
								to.getSubjectId(), null, null, to.getTheoryMarks());
						if (theoryMark != null || practicalMark != null) {
							overallList.add(bo);
						}
					}else{
						ExamStudentFinalMarkDetailsBO bo = new ExamStudentFinalMarkDetailsBO(to.getExamId(), to.getDummyStudentId(),
								to.getClassId(), to.getSubjectId(), null,
								null, to.getTheoryMarks(),
								to.getPracticalMarks(), null);
						if (theoryMark != null || practicalMark != null) {
							finalMarkList.add(bo);
						}
					}
				}else {
					Integer examid = null;
					Integer OverallId = null;
					examid = to.getExamId();
					if (to.getOverallId() != null && to.getOverallId() > 0) {
						OverallId = to.getOverallId();
					}
					ExamAssignOverallMarksBO bo = new ExamAssignOverallMarksBO(examid, to.getCourseId(),
							to.getSubjectId(), to.getDummyStudentId(), OverallId,
							to.getOverallName(), theoryMark, practicalMark, userId);
					if (theoryMark != null || practicalMark != null) {
						overAllAssignlist.add(bo);
					}
				}
			}
		}
		if(finalMarkList.size() > 0){
			impl.insert_List(finalMarkList);
		}
		else if(overAllAssignlist.size() > 0){
			impl.insert_List(overAllAssignlist);
		}
		else if (overallList.size() > 0) {
			impl.insert_List(overallList);
			
		}

	}

	// To get subjects for a particular course & scheme
	public Map<Integer, String> getSubjectsByCourseIdSchemeNo(int courseId,
			int schemeId, int shemeNo) {
		 HashMap<Integer, String> map = helper.convertBOToTO_Map(impl.select_Subjects(courseId, schemeId,
				shemeNo));
		return map;
	}

	// To get subject type by subjectId
	public String getSubjectsTypeBySubjectId(int subjectId) {
		String value = impl.selectSubjectsTypeBySubjectId(subjectId);
		return value.toLowerCase();
	}

	// To get Type drop down
	public Map<Integer, String> getTypeByAssignmentOverall(String type) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		if (type.equalsIgnoreCase("overall")) {
			map.put(1, "Internal Overall");
			map.put(2, "Regular Overall");
		} else {
			map = getAssignmentTypeList();
		}
		return map;
	}

	public int getcurrentExamId() throws Exception {

		return impl.getcurrentExamId();
	}

	public int getMaxTheoryMarks(BigDecimal marcks, int courseId, int schemeNo,
			int subjectId, String subjectType, int studentId,
			String assignmentOverall, String type) {
		Integer accyear = null;
		BigDecimal max = new BigDecimal("0");

		accyear = impl.getAccodamicYear(studentId);
		int assignTypeId = 0;
		if (assignmentOverall.equalsIgnoreCase("Assignment")) {
			if (CommonUtil.checkForEmpty(type)) {
				assignTypeId = Integer.parseInt(type);
			}
		}

		List<Object[]> list = impl.getMaxMarcks(accyear, courseId, schemeNo,
				subjectId, assignmentOverall, assignTypeId, type);
		if (list != null && list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				if (subjectType.equalsIgnoreCase("Theory")) {
					max = getBDValue(row[0]);
				}
				if (subjectType.equalsIgnoreCase("Practical")) {
					max = getBDValue(row[1]);
				}
				if (subjectType.equalsIgnoreCase("Theory and Practical")) {
					max = getBDValue(row[0]);
					max = max.add(getBDValue(row[1]));
				}

			}
		}

		if (marcks.compareTo(max) == 1) {
			return max.intValue();
		} else {
			return 0;
		}

	}

	public int getIntValue(Object object) throws Exception {
		if (object != null) {
			int val = Integer.parseInt(object.toString());
			return val;
		}
		return 0;
	}

	public BigDecimal getBDValue(Object object) {
		if (object != null) {
			BigDecimal val = new BigDecimal(object.toString());
			return val;
		}
		return new BigDecimal("0");
	}

	public ExamAssignmentOverallMarksForm getInternalMarcks(
			ExamAssignmentOverallMarksForm objform) throws Exception {

		Integer classId = impl.getClassId(Integer.parseInt(objform.getStudentId()),objform.getIsPreviousExam(),objform.getSchemeNo());
		List<Object> list = null;
		if (classId != null) {
			list = impl.getInternalMarcks(Integer.parseInt(objform
					.getExamName()),
					Integer.parseInt(objform.getStudentId()), classId, Integer
							.parseInt(objform.getSubjectId()));
		}
		if (list != null && list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				if (objform.getSubjectType().contains("T")
						|| objform.getSubjectType().contains("B")) {
					objform.setSubInternalTheory(getBDValue(row[0]).intValue());
					objform.setAttendanceTheory(getBDValue(row[1]).intValue());
					objform.setAssignmentTheory(getIntValue(row[2]));

				}
				if (objform.getSubjectType().contains("P")
						|| objform.getSubjectType().contains("B")) {
					objform.setSubInternalPractical(getBDValue(row[3]).intValue());
					objform.setAttendancePractical(getBDValue(row[4]).intValue());
					objform.setAssignmentPractical(getBDValue(row[5]).intValue());
				}
				// if (objform.getSubjectType().contains("B")) {
				//
				// int subInternal = 0;
				// int attendance = 0;
				// int assignment = 0;
				// if (row[0] != null && row[0].toString().trim().length() > 0)
				// {
				// subInternal = getIntValue(row[0]);
				// if (row[3] != null
				// && row[3].toString().trim().length() > 0) {
				// subInternal = subInternal + getIntValue(row[3]);
				// }
				// }
				// objform.setSubInternal(subInternal);
				//
				// if (row[1] != null && row[1].toString().trim().length() > 0)
				// {
				// attendance = getIntValue(row[1]);
				// if (row[4] != null
				// && row[4].toString().trim().length() > 0) {
				// attendance = attendance + getIntValue(row[4]);
				// }
				// }
				// objform.setAttendance(attendance);
				//
				// if (row[2] != null && row[2].toString().trim().length() > 0)
				// {
				// assignment = getIntValue(row[2]);
				// if (row[5] != null
				// && row[5].toString().trim().length() > 0) {
				// assignment = assignment + getIntValue(row[5]);
				// }
				// }
				// objform.setAssignment(assignment);
				// }
			}
		}
		else
		{
			if (objform.getSubjectType().contains("T") || objform.getSubjectType().contains("B")) 
			{
				objform.setSubInternalTheory(0);
				objform.setAttendanceTheory(0);
				objform.setAssignmentTheory(0);

			}
			if (objform.getSubjectType().contains("P") || objform.getSubjectType().contains("B")) 
			{
				objform.setSubInternalPractical(0);
				objform.setAttendancePractical(0);
				objform.setAssignmentPractical(0);
			}
		}
		return objform;
	}

	public Map<Integer, String> getCourseByAcademicYear(int year)
			throws Exception {

		return helper.convertBOTOMapList(impl.getCourseByAcademicYear(year));
	}

	public boolean getInternalOverallLink(int courseId, int subjectId,
			String subjectType, int schemeNo, Integer examId) throws Exception {
		List<Object[]> list = impl.getSubjectInternalDetails(courseId,
				subjectId, subjectType, schemeNo, examId);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;

	}

	public Integer getExamAcademicYear(Integer examId) throws Exception {
		return impl.getExamAcademicYear(examId);
	}

	// To get exam name list for reg , reg& supp
	public ArrayList<KeyValueTO> getExamNameListForRRS() throws Exception {
		ArrayList<Object[]> lBO = new ArrayList(impl
				.select_ActiveOnly_ExamNameRRS());
		return helper.convertBOToTO_ExamNameRRS(lBO);
	}

	// To get schemeNo for a course & exam
	public Map<String, String> getSchemeNoBy_ExamId(int examId) {
		return helper.convertBOtoTO_SchemeNoBy_ExamId_CourseId(impl
				.select_SchemeNoBy_ExamId(examId));
	}
}
