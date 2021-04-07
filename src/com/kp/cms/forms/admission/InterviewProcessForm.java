package com.kp.cms.forms.admission;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.utilities.CommonUtil;

public class InterviewProcessForm extends BaseActionForm {

	private String noOfCandidates_orig = "0";
	private String programType;
	private List<ProgramTypeTO> programTypeList;
	private String program;
	private String course;
	private String interviewType;
	private ArrayList<Integer> interviewTypeList;
	private String noOfInterviewers;
	private String noOfCandidates = "0";
	private String datesOfInterview;
	private String venue;
	private String years;
	private String startHours;
	private String startMins;
	private String endHours;
	private String endMins;
	private String intervalHours;
	private String intervalMins;
	private String breakFromHours;
	private String breakFromMins;
	private String breakToHours;
	private String breakToMins;
	private String breakFromHoursTwo;
	private String breakFromMinsTwo;
	private String breakToHoursTwo;
	private String breakToMinsTwo;
	private String breakFromHoursThree;
	private String breakFromMinsThree;
	private String breakToHoursThree;
	private String breakToMinsThree;
	private String method;
	private String progamTypeName;
	private String progamName;
	private String courseName;
	private String interviewTypeName;
	private String candidateCount;

	private String applicationNo;
	private String candidateName;
	private String interviewdate;
	private String interviewtime;
	private String intervier;
	private List<InterviewCardTO> studentList;

	private String searchApplNo;
	private String srartName;
	private String birthDate;
	private List applicationList;
	private String selectedCandidates[];
	private InterviewCardTO interviewCardTO;

	private String printChallan;
	private String selectedType;
	private String intCardContent;

	private String singleGroup;
	private String noOfStudentsPerGroup;
	private String examCenter;
	private List<ExamCenterTO> examCenters;
	private String hiddenInterviewType;

	public String getPrintChallan() {
		return printChallan;
	}

	public void setPrintChallan(String printChallan) {
		this.printChallan = printChallan;
	}

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public InterviewCardTO getInterviewCardTO() {
		return interviewCardTO;
	}

	public void setInterviewCardTO(InterviewCardTO interviewCardTO) {
		this.interviewCardTO = interviewCardTO;
	}

	public String[] getSelectedCandidates() {
		return selectedCandidates;
	}

	public void setSelectedCandidates(String[] selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}

	public String getCandidateCount() {
		return candidateCount;
	}

	public void setCandidateCount(String candidateCount) {
		this.candidateCount = candidateCount;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getBreakFromHoursThree() {
		return breakFromHoursThree;
	}

	public void setBreakFromHoursThree(String breakFromHoursThree) {
		this.breakFromHoursThree = breakFromHoursThree;
	}

	public String getBreakFromMinsThree() {
		return breakFromMinsThree;
	}

	public void setBreakFromMinsThree(String breakFromMinsThree) {
		this.breakFromMinsThree = breakFromMinsThree;
	}

	public String getBreakToHoursThree() {
		return breakToHoursThree;
	}

	public void setBreakToHoursThree(String breakToHoursThree) {
		this.breakToHoursThree = breakToHoursThree;
	}

	public String getBreakToMinsThree() {
		return breakToMinsThree;
	}

	public void setBreakToMinsThree(String breakToMinsThree) {
		this.breakToMinsThree = breakToMinsThree;
	}

	public List getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List applicationList) {
		this.applicationList = applicationList;
	}

	public String getSearchApplNo() {
		return searchApplNo;
	}

	public void setSearchApplNo(String searchApplNo) {
		this.searchApplNo = searchApplNo;
	}

	public String getSrartName() {
		return srartName;
	}

	public void setSrartName(String srartName) {
		this.srartName = srartName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public List<InterviewCardTO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<InterviewCardTO> studentList) {
		this.studentList = studentList;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getInterviewdate() {
		return interviewdate;
	}

	public void setInterviewdate(String interviewdate) {
		this.interviewdate = interviewdate;
	}

	public String getInterviewtime() {
		return interviewtime;
	}

	public void setInterviewtime(String interviewtime) {
		this.interviewtime = interviewtime;
	}

	public String getIntervier() {
		return intervier;
	}

	public void setIntervier(String intervier) {
		this.intervier = intervier;
	}

	public String getProgamTypeName() {
		return progamTypeName;
	}

	public void setProgamTypeName(String progamTypeName) {
		this.progamTypeName = progamTypeName;
	}

	public String getProgamName() {
		return progamName;
	}

	public void setProgamName(String progamName) {
		this.progamName = progamName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getInterviewTypeName() {
		return interviewTypeName;
	}

	public void setInterviewTypeName(String interviewTypeName) {
		this.interviewTypeName = interviewTypeName;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getNoOfInterviewers() {
		return noOfInterviewers;
	}

	public void setNoOfInterviewers(String noOfInterviewers) {
		this.noOfInterviewers = noOfInterviewers;
	}

	public String getNoOfCandidates() {
		return noOfCandidates;
	}

	public void setNoOfCandidates(String noOfCandidates) {
		this.noOfCandidates = noOfCandidates;
	}

	public String getDatesOfInterview() {
		return datesOfInterview;
	}

	public void setDatesOfInterview(String datesOfInterview) {
		this.datesOfInterview = datesOfInterview;
	}

	public String getStartHours() {
		return startHours;
	}

	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}

	public String getStartMins() {
		return startMins;
	}

	public void setStartMins(String startMins) {
		this.startMins = startMins;
	}

	public String getEndHours() {
		return endHours;
	}

	public void setEndHours(String endHours) {
		this.endHours = endHours;
	}

	public String getEndMins() {
		return endMins;
	}

	public void setEndMins(String endMins) {
		this.endMins = endMins;
	}

	public String getIntervalHours() {
		return intervalHours;
	}

	public void setIntervalHours(String intervalHours) {
		this.intervalHours = intervalHours;
	}

	public String getIntervalMins() {
		return intervalMins;
	}

	public void setIntervalMins(String intervalMins) {
		this.intervalMins = intervalMins;
	}

	public String getBreakFromHours() {
		return breakFromHours;
	}

	public void setBreakFromHours(String breakFromHours) {
		this.breakFromHours = breakFromHours;
	}

	public String getBreakFromMins() {
		return breakFromMins;
	}

	public void setBreakFromMins(String breakFromMins) {
		this.breakFromMins = breakFromMins;
	}

	public String getBreakToHours() {
		return breakToHours;
	}

	public void setBreakToHours(String breakToHours) {
		this.breakToHours = breakToHours;
	}

	public String getBreakToMins() {
		return breakToMins;
	}

	public void setBreakToMins(String breakToMins) {
		this.breakToMins = breakToMins;
	}

	public String getBreakFromHoursTwo() {
		return breakFromHoursTwo;
	}

	public void setBreakFromHoursTwo(String breakFromHoursTwo) {
		this.breakFromHoursTwo = breakFromHoursTwo;
	}

	public String getBreakFromMinsTwo() {
		return breakFromMinsTwo;
	}

	public void setBreakFromMinsTwo(String breakFromMinsTwo) {
		this.breakFromMinsTwo = breakFromMinsTwo;
	}

	public String getBreakToHoursTwo() {
		return breakToHoursTwo;
	}

	public void setBreakToHoursTwo(String breakToHoursTwo) {
		this.breakToHoursTwo = breakToHoursTwo;
	}

	public String getBreakToMinsTwo() {
		return breakToMinsTwo;
	}

	public void setBreakToMinsTwo(String breakToMinsTwo) {
		this.breakToMinsTwo = breakToMinsTwo;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public ArrayList<Integer> getInterviewTypeList() {
		return interviewTypeList;
	}

	public void setInterviewTypeList(ArrayList<Integer> interviewTypeList) {
		this.interviewTypeList = interviewTypeList;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getIntCardContent() {
		return intCardContent;
	}

	public void setIntCardContent(String intCardContent) {
		this.intCardContent = intCardContent;
	}

	public String getNoOfStudentsPerGroup() {
		return noOfStudentsPerGroup;
	}

	public void setNoOfStudentsPerGroup(String noOfStudentsPerGroup) {
		this.noOfStudentsPerGroup = noOfStudentsPerGroup;
	}

	public void resetSelctedCandidates(ActionMapping mapping,
			HttpServletRequest request) {
		selectedCandidates = null;
	}

	public void resetInterviewPrintCard(ActionMapping mapping,
			HttpServletRequest request) {
		super.clear();
		searchApplNo = null;
		srartName = null;
		birthDate = null;
		applicationList = null;
		programType = null;
		program = null;
		course = null;
		interviewType = null;
	}

	public void resetInterviewProcess() {

		this.noOfCandidates_orig = null;
		this.programType = null;
		this.programTypeList = null;
		this.program = null;
		this.course = null;
		this.interviewType = null;
		this.interviewTypeList = null;
		this.noOfInterviewers = null;
		this.noOfCandidates = null;
		this.datesOfInterview = null;
		this.venue = null;
		this.years = null;
		this.startHours = null;
		this.startMins = null;
		this.endHours = null;
		this.endMins = null;
		this.intervalHours = null;
		this.intervalMins = null;
		this.breakFromHours = null;
		this.breakFromMins = null;
		this.breakToHours = null;
		this.breakToMins = null;
		this.breakFromHoursTwo = null;
		this.breakFromMinsTwo = null;
		this.breakToHoursTwo = null;
		this.breakToMinsTwo = null;
		this.breakFromHoursThree = null;
		this.breakFromMinsThree = null;
		this.breakToHoursThree = null;
		this.breakToMinsThree = null;

		this.progamTypeName = null;
		this.progamName = null;
		this.courseName = null;
		this.interviewTypeName = null;
		this.candidateCount = null;
		this.applicationNo = null;
		this.candidateName = null;
		this.interviewdate = null;
		this.interviewtime = null;
		this.intervier = null;
		this.studentList = null;
		this.searchApplNo = null;
		this.srartName = null;
		this.birthDate = null;
		this.applicationList = null;
		this.selectedCandidates = null;

		this.noOfStudentsPerGroup = null;
		super.setProgramTypeId(null);
		this.hiddenInterviewType=null;
	}

	public void resetCandidateCount(ActionMapping mapping,
			HttpServletRequest request) {
		candidateCount = null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		int noOfDays = 0;
		long interviewers = 0;
		if (noOfCandidates.equals("0")) {
			actionErrors.add("error", new ActionError(
					CMSConstants.NO_OF_CANDIDATES_ZERO));
		}

		if ((startHours.equalsIgnoreCase("00") && startMins
				.equalsIgnoreCase("00"))
				|| (startHours.equalsIgnoreCase("0") && startMins
						.equalsIgnoreCase("0"))) {
			if (actionErrors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_STARTTIME) != null
					&& !actionErrors.get(
							CMSConstants.KNOWLEDGEPRO_INTERVIEW_STARTTIME)
							.hasNext()) {
				actionErrors.add("error", new ActionError(
						CMSConstants.KNOWLEDGEPRO_INTERVIEW_STARTTIME));
			}
		}
		if ((endHours.equalsIgnoreCase("00") && endMins.equalsIgnoreCase("00"))
				|| (endHours.equalsIgnoreCase("0") && endMins
						.equalsIgnoreCase("0"))) {
			if (actionErrors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ENDTIME) != null
					&& !actionErrors.get(
							CMSConstants.KNOWLEDGEPRO_INTERVIEW_ENDTIME)
							.hasNext()) {
				actionErrors.add("error", new ActionError(
						CMSConstants.KNOWLEDGEPRO_INTERVIEW_ENDTIME));
			}
		}

		if ((intervalHours.equalsIgnoreCase("00") && intervalMins
				.equalsIgnoreCase("00"))
				|| (intervalHours.equalsIgnoreCase("0") && intervalMins
						.equalsIgnoreCase("0"))) {
			if (actionErrors
					.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMEINTERVAL) != null
					&& !actionErrors.get(
							CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMEINTERVAL)
							.hasNext()) {
				actionErrors.add("error", new ActionError(
						CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMEINTERVAL));
			}
		}
		if ((singleGroup != null && !singleGroup.isEmpty())
				&& (singleGroup.equalsIgnoreCase(CMSConstants.GROUP))) {
			if ((noOfStudentsPerGroup == null || noOfStudentsPerGroup.isEmpty()))
				if (actionErrors
						.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_STUDENTS_PER_GROUP) != null
						&& !actionErrors
								.get(
										CMSConstants.KNOWLEDGEPRO_INTERVIEW_STUDENTS_PER_GROUP)
								.hasNext()) {
					actionErrors
							.add(
									"error",
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_INTERVIEW_STUDENTS_PER_GROUP));
				}
		}
		if (actionErrors.isEmpty()) {

			if ((startHours != null && !startHours.isEmpty())
					&& (startMins != null && !startMins.isEmpty())
					&& (endHours != null && !endHours.isEmpty())
					&& (endMins != null && !endMins.isEmpty())) {
				if ((intervalHours != null && !intervalHours.isEmpty())
						&& (intervalMins != null && !intervalMins.isEmpty())) {
					if ((breakFromHours == null || breakFromHours.isEmpty() || breakFromHours.equals("00"))
							&& (breakFromMins == null || breakFromMins.isEmpty() || breakFromMins.equals("00"))
							&& (breakToHours == null || breakToHours.isEmpty() || breakToHours.equals("00"))
							&& (breakToMins == null || breakToMins.isEmpty() || breakToMins.equals("00"))) {
						if ((breakFromHoursTwo == null
								|| breakFromHoursTwo.isEmpty() || breakFromHoursTwo
								.equalsIgnoreCase("00"))
								&& (breakFromMinsTwo == null
										|| breakFromMinsTwo.isEmpty() || breakFromMinsTwo
										.equalsIgnoreCase("00"))
								&& (breakToHoursTwo == null
										|| breakToHoursTwo.isEmpty() || breakToHoursTwo
										.equalsIgnoreCase("00"))
								&& (breakToMinsTwo == null
										|| breakToMinsTwo.isEmpty() || breakToMinsTwo
										.equalsIgnoreCase("00"))) {
							if ((breakFromHoursThree == null
									|| breakFromHoursThree.isEmpty() || breakFromHoursThree
									.equalsIgnoreCase("00"))
									&& (breakFromMinsThree == null
											|| breakFromMinsThree.isEmpty() || breakFromMinsThree
											.equalsIgnoreCase("00"))
									&& (breakToHoursThree == null
											|| breakToHoursThree.isEmpty() || breakToHoursThree
											.equalsIgnoreCase("00"))
									&& (breakToMinsThree == null
											|| breakToMinsThree.isEmpty() || breakToMinsThree
											.equalsIgnoreCase("00"))) {
								long timeAllotted = CommonUtil
										.getDifferenceInTime(Integer
												.parseInt(startHours), Integer
												.parseInt(startMins), Integer
												.parseInt(endHours), Integer
												.parseInt(endMins));
								long interviewTime = CommonUtil.getMinitues(
										Integer.parseInt(intervalHours),
										Integer.parseInt(intervalMins));
								// long breakTime =
								// CommonUtil.getDifferenceInTime
								// (Integer.parseInt(breakFromHours),
								// Integer.parseInt
								// (breakFromMins),Integer.parseInt
								// (breakToHours)
								// ,Integer.parseInt(breakToMins));

								if (CommonUtil.checkForEmpty(noOfInterviewers)) {
									interviewers = Integer
											.parseInt(noOfInterviewers);
								}
								if (CommonUtil.checkForEmpty(datesOfInterview)) {
									String dates[] = CommonUtil
											.getDates(datesOfInterview);
									noOfDays = dates.length;
								}
								// long timeAfterBreak =
								// CommonUtil.getTimeAfterBreak(timeAllotted,
								// breakTime);
								long noOfInterviews = CommonUtil
										.noOfInterviews(timeAllotted,
												interviewTime);
								long noOfCandidatesForInt = CommonUtil
										.calculateEligibility(noOfInterviews,
												interviewers, noOfDays);
								if (CommonUtil.checkForEmpty(noOfInterviewers)) {
									long noCandidates = Integer
											.parseInt(noOfCandidates);

									if (singleGroup.equalsIgnoreCase("single")) {
										if (noOfCandidatesForInt < noCandidates) {
											if (actionErrors
													.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY) != null
													&& !actionErrors
															.get(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY)
															.hasNext()) {
												actionErrors
														.add(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY,
																new ActionError(
																		CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY));
											}
										}
									} else {
										double noOfGroups = 0.0;
										double noOfStudentsPerGp = 0.0;
										noOfStudentsPerGp = Double
												.parseDouble(noOfStudentsPerGroup);
										noOfGroups = noCandidates
												/ noOfStudentsPerGp;
										if (noOfCandidatesForInt < noOfGroups) {
											if (actionErrors
													.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY) != null
													&& !actionErrors
															.get(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY)
															.hasNext()) {
												actionErrors
														.add(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY,
																new ActionError(
																		CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY));
											}

										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(startHours), Integer
											.parseInt(startMins), Integer
											.parseInt(endHours), Integer
											.parseInt(endMins))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
								}
							}
						}
					}
				}
			}

			if ((startHours != null && !startHours.isEmpty())
					&& (startMins != null && !startMins.isEmpty())
					&& (endHours != null && !endHours.isEmpty())
					&& (endMins != null && !endMins.isEmpty())) {
				if ((intervalHours != null && !intervalHours.isEmpty())
						&& (intervalMins != null && !intervalMins.isEmpty())) {
					if ((breakFromHours != null && !breakFromHours.isEmpty())
							&& (breakFromMins != null && !breakFromMins
									.isEmpty())
							&& (breakToHours != null && !breakToHours.isEmpty())
							&& (breakToMins != null && !breakToMins.isEmpty())) {
						if ((breakFromHoursTwo == null
								|| breakFromHoursTwo.isEmpty() || breakFromHoursTwo
								.equalsIgnoreCase("00"))
								&& (breakFromMinsTwo == null
										|| breakFromMinsTwo.isEmpty() || breakFromMinsTwo
										.equalsIgnoreCase("00"))
								&& (breakToHoursTwo == null
										|| breakToHoursTwo.isEmpty() || breakToHoursTwo
										.equalsIgnoreCase("00"))
								&& (breakToMinsTwo == null
										|| breakToMinsTwo.isEmpty() || breakToMinsTwo
										.equalsIgnoreCase("00"))) {
							if ((breakFromHoursThree == null
									|| breakFromHoursThree.isEmpty() || breakFromHoursThree
									.equalsIgnoreCase("00"))
									&& (breakFromMinsThree == null
											|| breakFromMinsThree.isEmpty() || breakFromMinsThree
											.equalsIgnoreCase("00"))
									&& (breakToHoursThree == null
											|| breakToHoursThree.isEmpty() || breakToHoursThree
											.equalsIgnoreCase("00"))
									&& (breakToMinsThree == null
											|| breakToMinsThree.isEmpty() || breakToMinsThree
											.equalsIgnoreCase("00"))) {
								long timeAllotted = CommonUtil
										.getDifferenceInTime(Integer
												.parseInt(startHours), Integer
												.parseInt(startMins), Integer
												.parseInt(endHours), Integer
												.parseInt(endMins));
								long interviewTime = CommonUtil.getMinitues(
										Integer.parseInt(intervalHours),
										Integer.parseInt(intervalMins));
								long breakTime = CommonUtil
										.getDifferenceInTime(
												Integer
														.parseInt(breakFromHours),
												Integer.parseInt(breakFromMins),
												Integer.parseInt(breakToHours),
												Integer.parseInt(breakToMins));

								if (CommonUtil.checkForEmpty(noOfInterviewers)) {
									interviewers = Integer
											.parseInt(noOfInterviewers);
								}
								if (CommonUtil.checkForEmpty(datesOfInterview)) {
									String dates[] = CommonUtil
											.getDates(datesOfInterview);
									noOfDays = dates.length;
								}
								long timeAfterBreak = CommonUtil
										.getTimeAfterBreak(timeAllotted,
												breakTime);
								long noOfInterviews = CommonUtil
										.noOfInterviews(timeAfterBreak,
												interviewTime);
								long noOfCandidatesForInt = CommonUtil
										.calculateEligibility(noOfInterviews,
												interviewers, noOfDays);
								if (CommonUtil.checkForEmpty(noOfInterviewers)) {
									long noCandidates = Integer
											.parseInt(noOfCandidates);
									if (singleGroup.equalsIgnoreCase("single")) {
										if (noOfCandidatesForInt < noCandidates) {
											if (actionErrors
													.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY) != null
													&& !actionErrors
															.get(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY)
															.hasNext()) {
												actionErrors
														.add(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY,
																new ActionError(
																		CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY));
											}
										}
									} else {
										double noOfGroups = 0.0;
										double noOfStudentsPerGp = 0.0;
										noOfStudentsPerGp = Double
												.parseDouble(noOfStudentsPerGroup);
										noOfGroups = noCandidates
												/ noOfStudentsPerGp;
										if (noOfCandidatesForInt < noOfGroups) {
											if (actionErrors
													.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY) != null
													&& !actionErrors
															.get(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY)
															.hasNext()) {
												actionErrors
														.add(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY,
																new ActionError(
																		CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY));
											}

										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(startHours), Integer
											.parseInt(startMins), Integer
											.parseInt(endHours), Integer
											.parseInt(endMins))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(breakFromHours), Integer
											.parseInt(breakFromMins), Integer
											.parseInt(breakToHours), Integer
											.parseInt(breakToMins))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
								}
							}
						}
					}
				}
			}

			if ((startHours != null && !startHours.isEmpty())
					&& (startMins != null && !startMins.isEmpty())
					&& (endHours != null && !endHours.isEmpty())
					&& (endMins != null && !endMins.isEmpty())) {
				if ((intervalHours != null && !intervalHours.isEmpty())
						&& (intervalMins != null && !intervalMins.isEmpty())) {
					if ((breakFromHours != null && !breakFromHours.isEmpty())
							&& (breakFromMins != null && !breakFromMins
									.isEmpty())
							&& (breakToHours != null && !breakToHours.isEmpty())
							&& (breakToMins != null && !breakToMins.isEmpty())) {
						if ((breakFromHoursTwo != null && !breakFromHoursTwo
								.isEmpty())
								&& (breakFromMinsTwo != null && !breakFromMinsTwo
										.isEmpty())
								&& (breakToHoursTwo != null && !breakToHoursTwo
										.isEmpty())
								&& (breakToMinsTwo != null && !breakToMinsTwo
										.isEmpty())) {
							if ((breakFromHoursThree == null
									|| breakFromHoursThree.isEmpty() || breakFromHoursThree
									.equalsIgnoreCase("00"))
									&& (breakFromMinsThree == null
											|| breakFromMinsThree.isEmpty() || breakFromMinsThree
											.equalsIgnoreCase("00"))
									&& (breakToHoursThree == null
											|| breakToHoursThree.isEmpty() || breakToHoursThree
											.equalsIgnoreCase("00"))
									&& (breakToMinsThree == null
											|| breakToMinsThree.isEmpty() || breakToMinsThree
											.equalsIgnoreCase("00"))) {
								long timeAllotted = CommonUtil
										.getDifferenceInTime(Integer
												.parseInt(startHours), Integer
												.parseInt(startMins), Integer
												.parseInt(endHours), Integer
												.parseInt(endMins));
								long interviewTime = CommonUtil.getMinitues(
										Integer.parseInt(intervalHours),
										Integer.parseInt(intervalMins));
								long breakTime = CommonUtil
										.getDifferenceInTime(
												Integer
														.parseInt(breakFromHours),
												Integer.parseInt(breakFromMins),
												Integer.parseInt(breakToHours),
												Integer.parseInt(breakToMins));
								long breakTimeTwo = CommonUtil
										.getDifferenceInTime(
												Integer
														.parseInt(breakFromHoursTwo),
												Integer
														.parseInt(breakFromMinsTwo),
												Integer
														.parseInt(breakToHoursTwo),
												Integer
														.parseInt(breakToMinsTwo));

								if (CommonUtil.checkForEmpty(noOfInterviewers)) {
									interviewers = Integer
											.parseInt(noOfInterviewers);
								}
								if (CommonUtil.checkForEmpty(datesOfInterview)) {
									String dates[] = CommonUtil
											.getDates(datesOfInterview);
									noOfDays = dates.length;
								}
								long timeAfterBreak = CommonUtil
										.getTimeAfterBreak(timeAllotted,
												breakTime, breakTimeTwo);
								long noOfInterviews = CommonUtil
										.noOfInterviews(timeAfterBreak,
												interviewTime);
								long noOfCandidatesForInt = CommonUtil
										.calculateEligibility(noOfInterviews,
												interviewers, noOfDays);
								if (CommonUtil.checkForEmpty(noOfInterviewers)) {
									long noCandidates = Integer
											.parseInt(noOfCandidates);
									if (singleGroup.equalsIgnoreCase("single")) {
										if (noOfCandidatesForInt < noCandidates) {
											if (actionErrors
													.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY) != null
													&& !actionErrors
															.get(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY)
															.hasNext()) {
												actionErrors
														.add(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY,
																new ActionError(
																		CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY));
											}
										}
									} else {
										double noOfGroups = 0.0;
										double noOfStudentsPerGp = 0.0;
										noOfStudentsPerGp = Double
												.parseDouble(noOfStudentsPerGroup);
										noOfGroups = noCandidates
												/ noOfStudentsPerGp;
										if (noOfCandidatesForInt < noOfGroups) {
											if (actionErrors
													.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY) != null
													&& !actionErrors
															.get(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY)
															.hasNext()) {
												actionErrors
														.add(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY,
																new ActionError(
																		CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY));
											}

										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(startHours), Integer
											.parseInt(startMins), Integer
											.parseInt(endHours), Integer
											.parseInt(endMins))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(breakFromHours), Integer
											.parseInt(breakFromMins), Integer
											.parseInt(breakToHours), Integer
											.parseInt(breakToMins))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(breakFromHoursTwo),
											Integer.parseInt(breakFromMinsTwo),
											Integer.parseInt(breakToHoursTwo),
											Integer.parseInt(breakToMinsTwo))) {
										if (actionErrors
												.get("knowledgepro.interview.timestartend") != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
								}
							}
						}
					}
				}
			}

			if ((startHours != null && !startHours.isEmpty())
					&& (startMins != null && !startMins.isEmpty())
					&& (endHours != null && !endHours.isEmpty())
					&& (endMins != null && !endMins.isEmpty())) {
				if ((intervalHours != null && !intervalHours.isEmpty())
						&& (intervalMins != null && !intervalMins.isEmpty())) {
					if ((breakFromHours != null && !breakFromHours.isEmpty() && !breakFromHours
							.equalsIgnoreCase("00"))
							&& (breakFromMins != null
									&& !breakFromMins.isEmpty() && !breakFromMins
									.equalsIgnoreCase("00"))
							&& (breakToHours != null && !breakToHours.isEmpty() && !breakToHours
									.equalsIgnoreCase("00"))
							&& (breakToMins != null && !breakToMins.isEmpty() && !breakToMins
									.equalsIgnoreCase("00"))) {
						if ((breakFromHoursTwo != null
								&& !breakFromHoursTwo.isEmpty() && !breakFromHoursTwo
								.equalsIgnoreCase("00"))
								&& (breakFromMinsTwo != null
										&& !breakFromMinsTwo.isEmpty() && !breakFromMinsTwo
										.equalsIgnoreCase("00"))
								&& (breakToHoursTwo != null
										&& !breakToHoursTwo.isEmpty() && !breakToHoursTwo
										.equalsIgnoreCase("00"))
								&& (breakToMinsTwo != null
										&& !breakToMinsTwo.isEmpty() && !breakToMinsTwo
										.equalsIgnoreCase("00"))) {
							if ((breakFromHoursThree != null
									&& !breakFromHoursThree.isEmpty() && !breakFromHoursThree
									.equalsIgnoreCase("00"))
									&& (breakFromMinsThree != null
											&& !breakFromMinsThree.isEmpty() && !breakFromMinsThree
											.equalsIgnoreCase("00"))
									&& (breakToHoursThree != null
											&& !breakToHoursThree.isEmpty() && !breakToHoursThree
											.equalsIgnoreCase("00"))
									&& (breakToMinsThree != null
											&& !breakToMinsThree.isEmpty() && !breakToMinsThree
											.equalsIgnoreCase("00"))) {
								long timeAllotted = CommonUtil
										.getDifferenceInTime(Integer
												.parseInt(startHours), Integer
												.parseInt(startMins), Integer
												.parseInt(endHours), Integer
												.parseInt(endMins));
								long interviewTime = CommonUtil.getMinitues(
										Integer.parseInt(intervalHours),
										Integer.parseInt(intervalMins));
								long breakTime = CommonUtil
										.getDifferenceInTime(
												Integer
														.parseInt(breakFromHours),
												Integer.parseInt(breakFromMins),
												Integer.parseInt(breakToHours),
												Integer.parseInt(breakToMins));
								long breakTimeTwo = CommonUtil
										.getDifferenceInTime(
												Integer
														.parseInt(breakFromHoursTwo),
												Integer
														.parseInt(breakFromMinsTwo),
												Integer
														.parseInt(breakToHoursTwo),
												Integer
														.parseInt(breakToMinsTwo));
								long breakTimeThree = CommonUtil
										.getDifferenceInTime(
												Integer
														.parseInt(breakFromHoursThree),
												Integer
														.parseInt(breakFromMinsThree),
												Integer
														.parseInt(breakToHoursThree),
												Integer
														.parseInt(breakToMinsThree));
								if (CommonUtil.checkForEmpty(noOfInterviewers)) {
									interviewers = Integer
											.parseInt(noOfInterviewers);
								}
								if (CommonUtil.checkForEmpty(datesOfInterview)) {
									String dates[] = CommonUtil
											.getDates(datesOfInterview);
									noOfDays = dates.length;
								}
								long timeAfterBreak = CommonUtil
										.getTimeAfterBreak(timeAllotted,
												breakTime, breakTimeTwo,
												breakTimeThree);
								long noOfInterviews = CommonUtil
										.noOfInterviews(timeAfterBreak,
												interviewTime);
								long noOfCandidatesForInt = CommonUtil
										.calculateEligibility(noOfInterviews,
												interviewers, noOfDays);
								if (CommonUtil.checkForEmpty(noOfInterviewers)) {
									long noCandidates = Integer
											.parseInt(noOfCandidates);
									if (singleGroup.equalsIgnoreCase("single")) {
										if (noOfCandidatesForInt < noCandidates) {
											if (actionErrors
													.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY) != null
													&& !actionErrors
															.get(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY)
															.hasNext()) {
												actionErrors
														.add(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY,
																new ActionError(
																		CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY));
											}
										}
									} else {
										double noOfGroups = 0.0;
										double noOfStudentsPerGp = 0.0;
										noOfStudentsPerGp = Double
												.parseDouble(noOfStudentsPerGroup);
										noOfGroups = noCandidates
												/ noOfStudentsPerGp;
										if (noOfCandidatesForInt < noOfGroups) {
											if (actionErrors
													.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY) != null
													&& !actionErrors
															.get(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY)
															.hasNext()) {
												actionErrors
														.add(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY,
																new ActionError(
																		CMSConstants.KNOWLEDGEPRO_INTERVIEW_ELIGIBILITY));
											}

										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(startHours), Integer
											.parseInt(startMins), Integer
											.parseInt(endHours), Integer
											.parseInt(endMins))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(breakFromHours), Integer
											.parseInt(breakFromMins), Integer
											.parseInt(breakToHours), Integer
											.parseInt(breakToMins))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
									if (CommonUtil.compareTime(Integer
											.parseInt(breakFromHoursTwo),
											Integer.parseInt(breakFromMinsTwo),
											Integer.parseInt(breakToHoursTwo),
											Integer.parseInt(breakToMinsTwo))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
									if (CommonUtil
											.compareTime(
													Integer
															.parseInt(breakFromHoursThree),
													Integer
															.parseInt(breakFromMinsThree),
													Integer
															.parseInt(breakToHoursThree),
													Integer
															.parseInt(breakToMinsThree))) {
										if (actionErrors
												.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND) != null
												&& !actionErrors
														.get(
																CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND)
														.hasNext()) {
											actionErrors
													.add(
															"error",
															new ActionError(
																	CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMESTARTEND));
										}
									}
								}
							}
						}
					}
				}
			}

		}

		return actionErrors;
	}

	public String getSingleGroup() {
		return singleGroup;
	}

	public void setSingleGroup(String singleGroup) {
		this.singleGroup = singleGroup;
	}

	public void setNoOfCandidates_orig(String noOfCandidates_orig) {
		this.noOfCandidates_orig = noOfCandidates_orig;
	}

	public String getNoOfCandidates_orig() {
		return noOfCandidates_orig;
	}

	public String getExamCenter() {
		return examCenter;
	}

	public void setExamCenter(String examCenter) {
		this.examCenter = examCenter;
	}

	public List<ExamCenterTO> getExamCenters() {
		return examCenters;
	}

	public void setExamCenters(List<ExamCenterTO> examCenters) {
		this.examCenters = examCenters;
	}

	public String getHiddenInterviewType() {
		return hiddenInterviewType;
	}

	public void setHiddenInterviewType(String  hiddenInterviewType) {
		this.hiddenInterviewType = hiddenInterviewType;
	}

}
