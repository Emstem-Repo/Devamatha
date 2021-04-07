package com.kp.cms.handlers.exam;

/**
 * Feb 2, 2010 Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamTimeTableBO;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamTimeTableForm;
import com.kp.cms.helpers.exam.ExamTimeTableHelper;
import com.kp.cms.to.exam.ExamSubjectTimeTableTO;
import com.kp.cms.to.exam.ExamTimeTableTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamTimeTableImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamTimeTableHandler extends ExamGenHandler {

	ExamTimeTableHelper helper = new ExamTimeTableHelper();
	ExamTimeTableImpl impl = new ExamTimeTableImpl();

	// To get the exam name for a particular exam type
	public ArrayList<KeyValueTO> getExamName(int examTypeID) {
		ArrayList<ExamDefinitionBO> lBO = new ArrayList<ExamDefinitionBO>(impl
				.select_ExamName(examTypeID));
		return helper.convertBOToTO_ExamNameList(lBO);
	}

	// To get details of first page
	public ArrayList<ExamTimeTableTO> getMainList(int examTypeId, int examId, ExamTimeTableForm objform)
			throws BusinessException, ApplicationException {
		return helper.convertBOToTO_details(impl.select_details(examTypeId,
				examId,objform));
	}

	// To get the subjects for a particular course and scheme
	//code commented by Balaji
	/*public ArrayList<ExamSubjectTimeTableTO> getSubjects(int courseId,
			int schemeId, int schemeNo, Integer examId)
			throws BusinessException, ApplicationException {

		ArrayList<ExamSubjectTimeTableTO> listOfSubjectDetails = helper
				.convertBOToTO_SubjectsList(impl.select_Subjects(courseId,
						schemeId, schemeNo,examId));
		ArrayList<ExamSubjectTimeTableTO> emptyDateSubjects = new ArrayList<ExamSubjectTimeTableTO>();
		ArrayList<ExamSubjectTimeTableTO> nonEmptyDateSubjects = new ArrayList<ExamSubjectTimeTableTO>();
		for (ExamSubjectTimeTableTO to : listOfSubjectDetails) {
			if (!(to.getDate() != null && to.getDate().trim().length() > 0)) {
				emptyDateSubjects.add(to);
			} else {
				nonEmptyDateSubjects.add(to);
			}

		}
		ArrayList<ExamSubjectTimeTableTO> emptyList = impl
				.getTimeForSubjectIds(emptyDateSubjects, examId);
		for (ExamSubjectTimeTableTO to : emptyList) {
			nonEmptyDateSubjects.add(to);
		}
		return nonEmptyDateSubjects;
	}*/

	public ExamTimeTableTO getSubjects(int id,String year,int examTypeId, String joiningBatch) throws BusinessException,ApplicationException,Exception {
		ExamTimeTableTO to = impl.getSubjects(id,joiningBatch,year);
		to.setListSubjects(getSubjects(to.getCourseId(), to.getSchemeId(), to
				.getSchemeNo(), to.getExamId(),year,examTypeId));
		return to;
	}

	// To get subjects from time table
	public int getTimeTableSubj(Integer examId) throws BusinessException, ApplicationException {
		return impl.select_TimeTableSubj(examId);
	}

	// To get subjects for a course, scheme & year
	public int getSubj_course_scheme_year(Integer courseId, Integer shemeId,
			Integer schemeNo, Integer academicYear) throws BusinessException, ApplicationException {
		return impl.select_course_scheme_year(courseId, shemeId, schemeNo,
				academicYear);
	}

	public String decide_status(Integer examId, Integer courseId,
			Integer shemeId, Integer schemeNo, Integer academicYear) throws BusinessException,ApplicationException {
		String status = "Pending";
		if (examId != null && courseId != null && shemeId != null && schemeNo != null && academicYear != null) 
		{
			int examSubCount=getTimeTableSubj(examId);
			int currSubCount=getSubj_course_scheme_year(courseId, shemeId, schemeNo, academicYear);
			if(currSubCount == 0){
				status="No Curriculum Subject";
			}else if ( examSubCount == currSubCount) 
			{
				status = "Completed";

			}
			else if (examSubCount== 0) 
			{
				status = "Pending";
			}
			else 
			{
				status = "Created";
			}
		}
		return status;
	}

	public void insert_update(ExamTimeTableTO to) throws BusinessException, ApplicationException {
		ArrayList<ExamSubjectTimeTableTO> listSubjects = to.getListSubjects();
		Date dateStarttime = null;
		Date dateEndtime = null;

		for (ExamSubjectTimeTableTO listSubjectsTO : listSubjects) {
			boolean datePresent = false;
			boolean stTimePresent = false;
			boolean endTimePresent = false;

			if (listSubjectsTO.getDate() != null
					&& listSubjectsTO.getDate().trim().length() > 0) {
				datePresent = true;

				if ((listSubjectsTO.getStartTimeHour() != null && listSubjectsTO
						.getStartTimeHour().trim().length() > 0)
						&& (listSubjectsTO.getStartTimeMin() != null && listSubjectsTO
								.getStartTimeMin().trim().length() > 0)) {
					stTimePresent = true;

				}

				if ((listSubjectsTO.getEndTimeHour() != null && listSubjectsTO
						.getEndTimeHour().trim().length() > 0)
						&& (listSubjectsTO.getEndTimeMin() != null && listSubjectsTO
								.getEndTimeMin().trim().length() > 0)) {
					endTimePresent = true;

				}
			}

/*			if (datePresent && stTimePresent && endTimePresent) {
				dateStarttime = getDate(listSubjectsTO.getDate(),
						listSubjectsTO.getStartTimeHour(), listSubjectsTO
								.getStartTimeMin());
				dateEndtime = getDate(listSubjectsTO.getDate(), listSubjectsTO
						.getEndTimeHour(), listSubjectsTO.getEndTimeMin());*/

				if (listSubjectsTO.getId() == 0) {
					// the exam id saved in the database is the exam exam course
					// scheme details id.
					/*		ExaminationSessions sessions = new ExaminationSessions();
					if(listSubjectsTO.getSessionId() != null && !listSubjectsTO.getSessionId().isEmpty()){
						sessions.setId(Integer.parseInt(listSubjectsTO.getSessionId()));
					}
				ExamTimeTableBO objBO = new ExamTimeTableBO(to.getId(),
							listSubjectsTO.getSubid(), dateStarttime,
							dateEndtime,true,sessions);*/
					
					ExamTimeTableBO objBO = new ExamTimeTableBO(to.getId(),
							listSubjectsTO.getSubid(),true);
					impl.insert(objBO);
				} else {
			/*		ExaminationSessions sessions = new ExaminationSessions();
					if(listSubjectsTO.getSessionId() != null && !listSubjectsTO.getSessionId().isEmpty()){
						sessions.setId(Integer.parseInt(listSubjectsTO.getSessionId()));
					}
					impl.updateExamTimeTable(listSubjectsTO.getId(),
							dateStarttime, dateEndtime,sessions);*/
					
					
				}
			//} 
/*		else {
				if (listSubjectsTO.getId() > 0) {
					impl.delete_timetable(listSubjectsTO.getId());
				}
			}*/
		}
	}

	public Date getDate(String date, String hr, String min) throws BusinessException {
		String dateTimeStr = date + " " + hr + ":" + min + ":00";
		String formatDate = CommonUtil.ConvertStringToDateFormat(dateTimeStr,
				"dd/M/yyyy hh:mm:ss", "M/d/yyyy h:mm:ss a");

		Date newdate = new Date(formatDate);
		return newdate;
	}

	public ArrayList<ExamSubjectTimeTableTO> getSubjects(int courseId,
			int schemeId, int schemeNo, Integer examId,String year,int examTypeId)
			throws BusinessException, ApplicationException,Exception {

//		ArrayList<ExamSubjectTimeTableTO> listOfSubjectDetails = helper
//				.convertBOToTO_SubjectsList(impl.select_Subjects(courseId,
//						schemeId, schemeNo,examId));
		
		List<Subject> subList=impl.getSubjectsByCourseYearAndSemesterNo(courseId,schemeNo,year,examId,examTypeId);
		Map<Integer,ExamTimeTableBO> timeList=impl.getExamTimeTableByExamIdAndSchemeId(examId,schemeId,schemeNo,courseId);
		
		ArrayList<ExamSubjectTimeTableTO> listOfSubjectDetails=helper.convertBOToTOSubjectsList(subList, timeList);
		
		ArrayList<ExamSubjectTimeTableTO> emptyDateSubjects = new ArrayList<ExamSubjectTimeTableTO>();
		ArrayList<ExamSubjectTimeTableTO> nonEmptyDateSubjects = new ArrayList<ExamSubjectTimeTableTO>();
		for (ExamSubjectTimeTableTO to : listOfSubjectDetails) {
			if (!(to.getDate() != null && to.getDate().trim().length() > 0)) {
				emptyDateSubjects.add(to);
			} else {
				nonEmptyDateSubjects.add(to);
			}

		}
		ArrayList<ExamSubjectTimeTableTO> emptyList = impl
				.getTimeForSubjectIds(emptyDateSubjects, examId);
		for (ExamSubjectTimeTableTO to : emptyList) {
			nonEmptyDateSubjects.add(to);
		}
		return nonEmptyDateSubjects;
	}
}
