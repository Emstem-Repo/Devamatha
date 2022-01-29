package com.kp.cms.helpers.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewBreakTime;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.InterviewProcessForm;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.handlers.admission.InterviewTypeHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewBreakTimeTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewScheduleTO;
import com.kp.cms.to.admission.InterviewTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class InterviewHelper {
	private static Log log = LogFactory.getLog(InterviewHelper.class);

	/**
	 * Converts Business Objects to Transaction object of the Interview Type.
	 * 
	 * @param programbo
	 *            - Represents the Interview Type Business objects.
	 * @return List - Interview Type Transaction object.
	 */
	public static List<InterviewProgramCourseTO> convertBOstoTos(
			List<InterviewProgramCourse> interviewbolist) {
		List<InterviewProgramCourseTO> interviewTypeList = new ArrayList<InterviewProgramCourseTO>();
		if (interviewbolist != null) {
			Iterator<InterviewProgramCourse> itr = interviewbolist.iterator();
			while (itr.hasNext()) {
				InterviewProgramCourse interviewbo = (InterviewProgramCourse) itr
						.next();
				InterviewProgramCourseTO interviewTo = new InterviewProgramCourseTO();
				interviewTo.setId(interviewbo.getId());
				interviewTo.setName(interviewbo.getName());
				interviewTypeList.add(interviewTo);
			}
		}
		log.info("exit convertBOstoTos in InterviewHelper");
		return interviewTypeList;
	}

	/**
	 * This method is used to convert TO list to BO list.
	 * 
	 * @param interviewCardTOList
	 * @return list of type InterviewCard.
	 */

	public static List<InterviewCard> converttoBO(
			List<InterviewCardTO> interviewCardTOList) {
		List<InterviewCard> interviewCardList = new ArrayList<InterviewCard>();
		if (interviewCardTOList != null) {
			Iterator<InterviewCardTO> itr = interviewCardTOList.iterator();
			while (itr.hasNext()) {
				InterviewCardTO interviewCardTO = (InterviewCardTO) itr.next();
				InterviewCard interviewCard = new InterviewCard();
				AdmAppln admAppln = new AdmAppln();
				admAppln.setIsInterviewSelected(interviewCardTO.getAdmApplnTO()
						.getIsInterviewSelected());
				interviewCard.setAdmAppln(admAppln);
				interviewCardList.add(interviewCard);
			}
		}
		log.info("exit converttoBO in InterviewHelper");
		return interviewCardList;
	}

	/**
	 * This method is used to convert BO list to TO list.
	 * 
	 * @param admApplnbolist
	 * @return list of type InterviewCardTO.
	 */

	public static List<InterviewCardTO> getApplicationTos(
			List<InterviewCard> admApplnbolist) {
		List<InterviewCardTO> admApplnTOList = new ArrayList<InterviewCardTO>();
		if (admApplnbolist != null) {
			Iterator<InterviewCard> itr = admApplnbolist.iterator();
			while (itr.hasNext()) {
				InterviewCard interviewCardbo = (InterviewCard) itr.next();
				InterviewCardTO interviewCardTo = new InterviewCardTO();
				AdmApplnTO admApplnTo = new AdmApplnTO();
				PersonalDataTO personalDataTO = new PersonalDataTO();
				CourseTO courseTO = new CourseTO();
				if (interviewCardbo.getAdmAppln() != null
						&& interviewCardbo.getAdmAppln().getApplnNo() != 0) {
					admApplnTo.setApplnNo(interviewCardbo.getAdmAppln()
							.getApplnNo());
				}
				interviewCardTo.setAdmApplnTO(admApplnTo);

				if (interviewCardbo.getAdmAppln() != null
						&& interviewCardbo.getAdmAppln().getPersonalData() != null
						&& interviewCardbo.getAdmAppln().getPersonalData()
								.getFirstName() != null) {
					personalDataTO.setFirstName(interviewCardbo.getAdmAppln()
							.getPersonalData().getFirstName());
				}
				if (interviewCardbo.getAdmAppln() != null
						&& interviewCardbo.getAdmAppln().getPersonalData() != null
						&& interviewCardbo.getAdmAppln().getPersonalData()
								.getMiddleName() != null) {
					personalDataTO.setMiddleName(interviewCardbo.getAdmAppln()
							.getPersonalData().getMiddleName());

				}
				if (interviewCardbo.getAdmAppln() != null
						&& interviewCardbo.getAdmAppln().getPersonalData() != null
						&& interviewCardbo.getAdmAppln().getPersonalData()
								.getLastName() != null) {
					personalDataTO.setLastName(interviewCardbo.getAdmAppln()
							.getPersonalData().getLastName());

				}

				admApplnTo.setPersonalData(personalDataTO);
				interviewCardTo.setAdmApplnTO(admApplnTo);

				if (interviewCardbo.getInterview() != null
						&& interviewCardbo.getInterview().getInterview() != null
						&& interviewCardbo.getInterview().getInterview()
								.getInterviewProgramCourse() != null
						&& interviewCardbo.getInterview().getInterview()
								.getInterviewProgramCourse().getName() != null) {
					interviewCardTo.setInterviewType(String
							.valueOf(interviewCardbo.getInterview()
									.getInterview().getInterviewProgramCourse()
									.getName()));
				}
				if (interviewCardbo.getAdmAppln() != null
						&& interviewCardbo.getAdmAppln().getCourseBySelectedCourseId() != null
						&& interviewCardbo.getAdmAppln().getCourseBySelectedCourseId().getName() != null) {
					courseTO.setName(interviewCardbo.getAdmAppln().getCourseBySelectedCourseId()
							.getName());
				}
				admApplnTo.setCourse(courseTO);
				interviewCardTo.setAdmApplnTO(admApplnTo);

				admApplnTOList.add(interviewCardTo);
			}
		}
		log.info("exit getApplicationTos in InterviewHelper");
		return admApplnTOList;
	}

	/**
	 * This method is used to convert form to TO.
	 * 
	 * @param interviewForm
	 * @return InterviewTO instance.
	 */

	public static InterviewTO getInterviewTO(InterviewProcessForm interviewForm) {
		InterviewProgramCourseTO interviewProgramCourseTO = new InterviewProgramCourseTO();
		InterviewTO interviewTO = new InterviewTO();
		if (interviewForm.getInterviewType() != null
				&& !interviewForm.getInterviewType().isEmpty()) {
			interviewProgramCourseTO.setId(Integer.parseInt(interviewForm
					.getInterviewType()));
			interviewTO.setInterviewProgramCourse(interviewProgramCourseTO);
		}
		if (interviewForm.getYears() != null
				&& !interviewForm.getYears().isEmpty()) {
			interviewTO.setYear(Integer.parseInt(interviewForm.getYears()));
		}
		if (interviewForm.getNoOfCandidates() != null
				&& !interviewForm.getNoOfCandidates().isEmpty()) {
			interviewTO.setNoOfCandidates(Integer.parseInt(interviewForm
					.getNoOfCandidates()));
		}
		if (interviewForm.getNoOfInterviewers() != null
				&& !interviewForm.getNoOfInterviewers().isEmpty()) {
			interviewTO.setNoOfInterviewers(Integer.parseInt(interviewForm
					.getNoOfInterviewers()));
		}

		InterviewBreakTimeTO interviewBreakTimeTO = new InterviewBreakTimeTO();
		InterviewBreakTimeTO interviewBreakTimeTO1 = new InterviewBreakTimeTO();
		Set<InterviewBreakTimeTO> interviewBreakTimeTOSet = new HashSet<InterviewBreakTimeTO>();
		if ((interviewForm.getBreakFromHours() != null && !interviewForm
				.getBreakFromHours().isEmpty())
				&& (interviewForm.getBreakFromMins() != null && !interviewForm
						.getBreakFromMins().isEmpty())) {
			if ((interviewForm.getBreakToHours() != null && !interviewForm
					.getBreakToHours().isEmpty())
					&& (interviewForm.getBreakToMins() != null && !interviewForm
							.getBreakToMins().isEmpty())) {
				interviewBreakTimeTO.setStartTime(CommonUtil.getTime(
						interviewForm.getBreakFromHours(), interviewForm
								.getBreakFromMins()));
				interviewBreakTimeTO.setEndTime(CommonUtil.getTime(
						interviewForm.getBreakToHours(), interviewForm
								.getBreakToMins()));
				interviewBreakTimeTOSet.add(interviewBreakTimeTO);
			}
		}
		if ((interviewForm.getBreakFromHoursTwo() != null && !interviewForm
				.getBreakFromHoursTwo().isEmpty())
				&& (interviewForm.getBreakFromMinsTwo() != null && !interviewForm
						.getBreakFromMinsTwo().isEmpty())) {
			if ((interviewForm.getBreakToHoursTwo() != null && !interviewForm
					.getBreakToHoursTwo().isEmpty())
					&& (interviewForm.getBreakToMinsTwo() != null && !interviewForm
							.getBreakToMinsTwo().isEmpty())) {
				interviewBreakTimeTO1.setStartTime(CommonUtil.getTime(
						interviewForm.getBreakFromHoursTwo(), interviewForm
								.getBreakFromMinsTwo()));
				interviewBreakTimeTO1.setEndTime(CommonUtil.getTime(
						interviewForm.getBreakToHoursTwo(), interviewForm
								.getBreakToMinsTwo()));
				interviewBreakTimeTOSet.add(interviewBreakTimeTO1);
			}
		}

		InterviewScheduleTO interviewScheduleTO;
		Set<InterviewScheduleTO> interviewScheduleSet = new HashSet<InterviewScheduleTO>();
		String dates[] = CommonUtil.getDates(interviewForm
				.getDatesOfInterview());
			for (int i = 0; i < dates.length; i++) {
				interviewScheduleTO = new InterviewScheduleTO();
				interviewScheduleTO.setDate(dates[i]);
				interviewScheduleTO.setVenue(interviewForm.getVenue());
				interviewScheduleTO.setStartTime(CommonUtil.getTime(
						interviewForm.getStartHours(), interviewForm
								.getStartMins()));
				interviewScheduleTO.setEndTime(CommonUtil.getTime(interviewForm
						.getEndHours(), interviewForm.getEndMins()));
				interviewScheduleTO.setTimeInterval(CommonUtil.getTime(
						interviewForm.getIntervalHours(), interviewForm
								.getIntervalMins()));
				interviewScheduleTO
						.setInterviewBreakTimes(interviewBreakTimeTOSet);
				interviewScheduleSet.add(interviewScheduleTO);
			}
		interviewTO.setInterviewSchedules(interviewScheduleSet);
		log.info("exit getInterviewTO in InterviewHelper");
		return interviewTO;
	}

	/**
	 * This method is used to get interview details.
	 * 
	 * @param interviewTO
	 * @return Interview BO instance.
	 */

	public static Interview getInterviewBO(InterviewTO interviewTO) {
		Interview interview = new Interview();
		InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
		if (interviewTO != null) {
			interviewProgramCourse.setId(interviewTO
					.getInterviewProgramCourse().getId());
			interview.setInterviewProgramCourse(interviewProgramCourse);
			interview.setYear(interviewTO.getYear());
			interview.setNoOfCandidates(interviewTO.getNoOfCandidates());
			interview.setNoOfInterviewers(interviewTO.getNoOfInterviewers());

			Set<InterviewScheduleTO> interviewScheduleTOSet = interviewTO
					.getInterviewSchedules();
			java.util.Iterator itr = interviewScheduleTOSet.iterator();
			InterviewScheduleTO interviewScheduleTO = null;
			while (itr.hasNext()) {
				interviewScheduleTO = (InterviewScheduleTO) itr.next();
				break;
			}
			Set<InterviewBreakTimeTO> interviewBreakTimeTOSet;
            if(interviewScheduleTO!=null){
			     interviewBreakTimeTOSet = interviewScheduleTO.getInterviewBreakTimes();
            }else
            	interviewBreakTimeTOSet = new HashSet<InterviewBreakTimeTO>();
			java.util.Iterator iset = interviewBreakTimeTOSet.iterator();
			InterviewBreakTimeTO interviewBreakTimeTO = null;
			InterviewBreakTime interviewBreakTime = null;
			Set<InterviewBreakTime> interviewBreakTimeSet = new HashSet<InterviewBreakTime>();
			while (iset.hasNext()) {
				interviewBreakTimeTO = (InterviewBreakTimeTO) iset.next();
				interviewBreakTime = new InterviewBreakTime();
				interviewBreakTime.setStartTime(interviewBreakTimeTO
						.getStartTime());
				interviewBreakTime
						.setEndTime(interviewBreakTimeTO.getEndTime());
				interviewBreakTimeSet.add(interviewBreakTime);
			}
			Set<InterviewSchedule> interviewSetSchedule = new HashSet<InterviewSchedule>();
			Set<InterviewScheduleTO> interviewScheduleTOSet1 = interviewTO
					.getInterviewSchedules();
			java.util.Iterator itr1 = interviewScheduleTOSet1.iterator();
			InterviewScheduleTO interviewScheduleTO1 = null;

			while (itr1.hasNext()) {
				interviewScheduleTO1 = (InterviewScheduleTO) itr1.next();
				InterviewSchedule interviewSchedule = new InterviewSchedule();
				interviewSchedule
						.setDate(CommonUtil
								.ConvertStringToSQLDate(interviewScheduleTO1
										.getDate()));
				interviewSchedule.setVenue(interviewScheduleTO1.getVenue());
				interviewSchedule.setStartTime(interviewScheduleTO1
						.getStartTime());
				interviewSchedule.setEndTime(interviewScheduleTO1.getEndTime());
				interviewSchedule.setTimeInterval(interviewScheduleTO1
						.getTimeInterval());
				interviewSchedule.setInterviewBreakTimes(interviewBreakTimeSet);
				interviewSetSchedule.add(interviewSchedule);
			}

			interview.setInterviewSchedules(interviewSetSchedule);
		}
		log.info("exit getInterviewBO in InterviewHelper");
		return interview;
	}

	/**
	 * This method is used to get program details.
	 * 
	 * @param interviewProgramCourse
	 * @param interviewProcessForm
	 * @return int value.
	 */

	public static int getProgramDetails(
			InterviewProgramCourse interviewProgramCourse,
			InterviewProcessForm interviewProcessForm) {
		int courseID = 0;
		interviewProcessForm.setProgamTypeName(interviewProgramCourse
				.getProgram().getProgramType().getName());
		interviewProcessForm.setProgamName(interviewProgramCourse.getProgram()
				.getName());
		interviewProcessForm.setCourseName(interviewProgramCourse.getCourse()
				.getCode());
		interviewProcessForm.setInterviewTypeName(interviewProgramCourse
				.getName());
		if (interviewProgramCourse.getCourse().getId() != 0) {
			courseID = interviewProgramCourse.getCourse().getId();
		}
		log.info("exit getProgramDetails in InterviewHelper");
		return courseID;
	}

	/**
	 * This method is used to get admappln details.
	 * 
	 * @return string array.
	 */

	public static String[] getAdmAppln(List candidateList) {
		String[] arrayAdmAppln = null;
		if (candidateList != null) {
			int size = candidateList.size();
			arrayAdmAppln = new String[size];
			Iterator<InterviewSelected> itr = candidateList.iterator();
			int count = 0;
			while (itr.hasNext()) {
				InterviewSelected interviewselectedbo = (InterviewSelected) itr
						.next();
				if (interviewselectedbo.getAdmAppln() != null
						|| interviewselectedbo.getAdmAppln().getId() != 0) {
					arrayAdmAppln[count] = String.valueOf(interviewselectedbo
							.getAdmAppln().getId());
					count++;
				}
			}
		}
		log.info("exit getAdmAppln in InterviewHelper");
		return arrayAdmAppln;
	}
	/**
	 * 
	 * @param candidateList
	 * @return
	 */
	public static HashMap<Integer, Integer> getInterviewTypesForCandidates(List candidateList) {
		HashMap<Integer, Integer> intPrgCourseMap = new HashMap<Integer, Integer>();
		if (candidateList != null) {
			Iterator<InterviewSelected> itr = candidateList.iterator();
			int count = 0;
			while (itr.hasNext()) {
				InterviewSelected interviewselectedbo = (InterviewSelected) itr
						.next();
				if (interviewselectedbo.getAdmAppln() != null
						|| interviewselectedbo.getAdmAppln().getId() != 0 && interviewselectedbo.getInterviewProgramCourse()!= null) {
					intPrgCourseMap.put(interviewselectedbo.getAdmAppln().getId(), interviewselectedbo.getInterviewProgramCourse().getId());
					count++;
				}
			}
		}
		log.info("exit getAdmAppln in InterviewHelper");
		return intPrgCourseMap;
	}

	/**
	 * This method is used to get interview card generation details.
	 * 
	 * @param candidateList
	 * @param interviewForm
	 * @param interview
	 * @return string array.
	 */

	public static InterviewCard[] generateInterviewCards(
			String[] candidateList, InterviewProcessForm interviewForm,
			List<Interview> interviewList /*interview*/, HashMap<Integer, Integer> intPrgCourseMap) {
		AdmAppln admAppln;
		int noOfInterviewers = Integer.parseInt(interviewForm
				.getNoOfInterviewers());
		InterviewCard interviewCard[] = new InterviewCard[candidateList.length];
		
		Set<InterviewSchedule> interviewScheduleSet = new HashSet<InterviewSchedule>();

		/*Set<InterviewSchedule> interviewScheduleSet = interview
		.getInterviewSchedules();*/
		Map<Integer, Integer> intScheduleMap = new HashMap<Integer, Integer>();
		Iterator<Interview> intItr = interviewList.iterator();
		while (intItr.hasNext()){
			Interview interview = intItr.next();
			interviewScheduleSet.addAll(interview.getInterviewSchedules());
			
		}
		Iterator<Interview> intItr1 = interviewList.iterator();
		while (intItr1.hasNext()){
			Interview interview = intItr1.next();
			Iterator<InterviewSchedule> shItr = interview.getInterviewSchedules().iterator();
			while (shItr.hasNext()) {
				InterviewSchedule interviewSchedule = (InterviewSchedule) shItr
						.next();
				intScheduleMap.put(interview.getInterviewProgramCourse().getId(), interviewSchedule.getId());
//				intScheduleMap.put(interviewSchedule.getInterview().getInterviewProgramCourse().getId(), interviewSchedule.getId());
				
				
			}
			
		}
		
		Iterator itr1 = interviewScheduleSet.iterator();
		InterviewSchedule interviewSchedule = null;
		// DateFormat sdf = new SimpleDateFormat("hh:mm");
		String[] dates = new String[interviewScheduleSet.size()];
		int date = 0;
		while (itr1.hasNext()) {
			interviewSchedule = (InterviewSchedule) itr1.next();
			dates[date] = String.valueOf(interviewSchedule.getId());
			date++;
		}
		int noOfCandidates = candidateList.length;
		InterviewCard interviewCardObj;

		int startHours = Integer.parseInt(interviewForm.getStartHours());
		int startMins = Integer.parseInt(interviewForm.getStartMins());
		int endHours = Integer.parseInt(interviewForm.getEndHours());
		int endMins = Integer.parseInt(interviewForm.getEndMins());
		int timeIntervalHours = Integer.parseInt(interviewForm
				.getIntervalHours());
		int timeIntervalMins = Integer
				.parseInt(interviewForm.getIntervalMins());
		int breakOneStartHours = Integer.parseInt(interviewForm
				.getBreakFromHours());
		int breakOneStartMins = Integer.parseInt(interviewForm
				.getBreakFromMins());
		int breakOneEndHours = Integer
				.parseInt(interviewForm.getBreakToHours());
		int breakOneEndMins = Integer.parseInt(interviewForm.getBreakToMins());
		int breakTwoStartHours = Integer.parseInt(interviewForm
				.getBreakFromHoursTwo());
		int breakTwoStartMins = Integer.parseInt(interviewForm
				.getBreakFromMinsTwo());
		int breakTwoEndHours = Integer.parseInt(interviewForm
				.getBreakToHoursTwo());
		int breakTwoEndMins = Integer.parseInt(interviewForm
				.getBreakToMinsTwo());
		int breakThreeStartHours = Integer.parseInt(interviewForm
				.getBreakFromHoursThree());
		int breakThreeStartMins = Integer.parseInt(interviewForm
				.getBreakFromMinsThree());
		int breakThreeEndHours = Integer.parseInt(interviewForm
				.getBreakToHoursThree());
		int breakThreeEndMins = Integer.parseInt(interviewForm
				.getBreakToMinsThree());
		List<String> timesList = new ArrayList<String>();

		int startTime = (startHours * 60) + startMins;
		int endTime = (endHours * 60) + endMins;
		int timeInterval = (timeIntervalHours * 60) + timeIntervalMins;
		int breakStartOne = (breakOneStartHours * 60) + breakOneStartMins;
		int breakStartTwo = (breakTwoStartHours * 60) + breakTwoStartMins;
		int breakStartThree = (breakThreeStartHours * 60) + breakThreeStartMins;
		int breakEndOne = (breakOneEndHours * 60) + breakOneEndMins;
		int breakEndTwo = (breakTwoEndHours * 60) + breakTwoEndMins;
		int breakEndThree = (breakThreeEndHours * 60) + breakThreeEndMins;

		timesList.add((startTime / 60) + ":" + (startTime % 60));

		int advanceEndTime = 0;
		boolean brkOne = false;
		boolean brkTwo = false;
		boolean brkThre = false;
		int candiCount = 0;

		while (startTime < endTime) {
			startTime = startTime + timeInterval;
			int leftTempTime = (startTime / 60);
			int rightTempTime = (startTime % 60);

			String tempStartTime = leftTempTime + ":" + rightTempTime;
			timesList.add(tempStartTime);
			if (breakStartOne < startTime && !brkOne && breakStartOne != 0) {
				// brkOne = true;
				// startTime = breakEndOne;
				timesList.remove(0);
			}

			advanceEndTime = startTime + timeInterval;
			if (breakStartOne < advanceEndTime && !brkOne && breakStartOne != 0) {
				brkOne = true;
				startTime = breakEndOne;

				timesList.remove(tempStartTime);
				timesList.add((startTime / 60) + ":" + (startTime % 60));
			}
			if (breakStartTwo < advanceEndTime && !brkTwo && breakStartTwo != 0) {
				brkTwo = true;
				startTime = breakEndTwo;

				timesList.remove(tempStartTime);
				timesList.add((startTime / 60) + ":" + (startTime % 60));
			}
			if (breakStartThree < advanceEndTime && !brkThre
					&& breakStartThree != 0) {
				brkThre = true;
				startTime = breakEndThree;

				timesList.remove(tempStartTime);
				timesList.add((startTime / 60) + ":" + (startTime % 60));
			}
			if (advanceEndTime > endTime) {
				timesList.remove(tempStartTime);
				break;
			}
		}

		int noOfCandidatesinaDay = (timesList.size() * noOfInterviewers);

		first: for (int j = 0; j < dates.length; j++) {
			Iterator<String> itr = timesList.iterator();
			String time;
			int candicateDayCount = 0;
			second: while (itr.hasNext()) {
				time = itr.next();
				for (int i = 0; i < noOfInterviewers; i++) {
					interviewCardObj = new InterviewCard();
					admAppln = new AdmAppln();
					
					
					if (candidateList.length != 0) {

							admAppln.setId(Integer
									.parseInt(candidateList[candiCount]));
						interviewCardObj.setAdmAppln(admAppln);
						interviewCardObj.setTime(time);
						int intPrgCrsId = intPrgCourseMap.get(admAppln.getId());
						InterviewSchedule interviewScheduleBo = new InterviewSchedule();
						interviewScheduleBo.setId(intScheduleMap.get(intPrgCrsId));
						//interviewScheduleBo.setId(Integer.parseInt(dates[j]));
						interviewCardObj.setInterview(interviewScheduleBo);
						interviewCardObj.setInterviewer(i + 1);
						interviewCardObj.setCreatedDate(new Date());
						interviewCardObj.setLastModifiedDate(new Date());

						interviewCard[candiCount++] = interviewCardObj;
						if (candicateDayCount++ == noOfCandidatesinaDay) {
							break second;
						}
						if (candiCount == noOfCandidates) {
							break first;
						}
					}
				}
			}
		}
		log.info("exit generateInterviewCards in InterviewHelper");
		return interviewCard;
	}

	/**
	 * This method is used to get interview card generation details for group.
	 * 
	 * @param candidateList
	 * @param interviewForm
	 * @param interview
	 * @return string array.
	 */

	public static InterviewCard[] generateInterviewCardsForGroup(
			String[] candidateList, InterviewProcessForm interviewForm,
			List<Interview> interviewList /*Interview interview*/, HashMap<Integer, Integer> intPrgCourseMap) {
		AdmAppln admAppln;
		int noOfInterviewers = Integer.parseInt(interviewForm
				.getNoOfInterviewers());
		int candidateListArray[];
		candidateListArray = convertStringtoIntArray(candidateList);
		Arrays.sort(candidateListArray);
		InterviewCard interviewCard[] = new InterviewCard[candidateListArray.length];
		
		/*Set<InterviewSchedule> interviewScheduleSet = interview
				.getInterviewSchedules();*/
		Set<InterviewSchedule> interviewScheduleSet = new HashSet<InterviewSchedule>();
			
		Map<Integer, Integer> intScheduleMap = new HashMap<Integer, Integer>();
		Iterator<Interview> intItr = interviewList.iterator();
		while (intItr.hasNext()){
			Interview interview = intItr.next();
			interviewScheduleSet.addAll(interview.getInterviewSchedules());
			
		}		
		
		Iterator<Interview> intItr1 = interviewList.iterator();
		while (intItr1.hasNext()){
			Interview interview = intItr1.next();
			Iterator<InterviewSchedule> shItr = interview.getInterviewSchedules().iterator();
			while (shItr.hasNext()) {
				InterviewSchedule interviewSchedule = (InterviewSchedule) shItr
						.next();
				intScheduleMap.put(interview.getInterviewProgramCourse().getId(), interviewSchedule.getId());
//				intScheduleMap.put(interviewSchedule.getInterview().getInterviewProgramCourse().getId(), interviewSchedule.getId());
				
				
			}
			
		}		
		
		Iterator itr1 = interviewScheduleSet.iterator();
		InterviewSchedule interviewSchedule = null;
		// DateFormat sdf = new SimpleDateFormat("hh:mm");
		String[] dates = new String[interviewScheduleSet.size()];
		int date = 0;
		while (itr1.hasNext()) {
			interviewSchedule = (InterviewSchedule) itr1.next();
			dates[date] = String.valueOf(interviewSchedule.getId());
			date++;
		}
		
		
		int noOfCandidates = candidateListArray.length;
		InterviewCard interviewCardObj;

		int startHours = Integer.parseInt(interviewForm.getStartHours());
		int startMins = Integer.parseInt(interviewForm.getStartMins());
		int endHours = Integer.parseInt(interviewForm.getEndHours());
		int endMins = Integer.parseInt(interviewForm.getEndMins());
		int timeIntervalHours = Integer.parseInt(interviewForm
				.getIntervalHours());
		int timeIntervalMins = Integer
				.parseInt(interviewForm.getIntervalMins());
		int breakOneStartHours = Integer.parseInt(interviewForm
				.getBreakFromHours());
		int breakOneStartMins = Integer.parseInt(interviewForm
				.getBreakFromMins());
		int breakOneEndHours = Integer
				.parseInt(interviewForm.getBreakToHours());
		int breakOneEndMins = Integer.parseInt(interviewForm.getBreakToMins());
		int breakTwoStartHours = Integer.parseInt(interviewForm
				.getBreakFromHoursTwo());
		int breakTwoStartMins = Integer.parseInt(interviewForm
				.getBreakFromMinsTwo());
		int breakTwoEndHours = Integer.parseInt(interviewForm
				.getBreakToHoursTwo());
		int breakTwoEndMins = Integer.parseInt(interviewForm
				.getBreakToMinsTwo());
		int breakThreeStartHours = Integer.parseInt(interviewForm
				.getBreakFromHoursThree());
		int breakThreeStartMins = Integer.parseInt(interviewForm
				.getBreakFromMinsThree());
		int breakThreeEndHours = Integer.parseInt(interviewForm
				.getBreakToHoursThree());
		int breakThreeEndMins = Integer.parseInt(interviewForm
				.getBreakToMinsThree());
		List<String> timesList = new ArrayList<String>();

		int startTime = (startHours * 60) + startMins;
		int endTime = (endHours * 60) + endMins;
		int timeInterval = (timeIntervalHours * 60) + timeIntervalMins;
		int breakStartOne = (breakOneStartHours * 60) + breakOneStartMins;
		int breakStartTwo = (breakTwoStartHours * 60) + breakTwoStartMins;
		int breakStartThree = (breakThreeStartHours * 60) + breakThreeStartMins;
		int breakEndOne = (breakOneEndHours * 60) + breakOneEndMins;
		int breakEndTwo = (breakTwoEndHours * 60) + breakTwoEndMins;
		int breakEndThree = (breakThreeEndHours * 60) + breakThreeEndMins;

		timesList.add((startTime / 60) + ":" + (startTime % 60));

		int advanceEndTime = 0;
		boolean brkOne = false;
		boolean brkTwo = false;
		boolean brkThre = false;
		int candiCount = 0;

		while (startTime < endTime) {
			startTime = startTime + timeInterval;
			int leftTempTime = (startTime / 60);
			int rightTempTime = (startTime % 60);

			String tempStartTime = leftTempTime + ":" + rightTempTime;
			timesList.add(tempStartTime);
			if (breakStartOne < startTime && !brkOne && breakStartOne != 0) {
				// brkOne = true;
				// startTime = breakEndOne;
				timesList.remove(0);
			}

			advanceEndTime = startTime + timeInterval;
			if (breakStartOne < advanceEndTime && !brkOne && breakStartOne != 0) {
				brkOne = true;
				startTime = breakEndOne;

				timesList.remove(tempStartTime);
				timesList.add((startTime / 60) + ":" + (startTime % 60));
			}
			if (breakStartTwo < advanceEndTime && !brkTwo && breakStartTwo != 0) {
				brkTwo = true;
				startTime = breakEndTwo;

				timesList.remove(tempStartTime);
				timesList.add((startTime / 60) + ":" + (startTime % 60));
			}
			if (breakStartThree < advanceEndTime && !brkThre
					&& breakStartThree != 0) {
				brkThre = true;
				startTime = breakEndThree;

				timesList.remove(tempStartTime);
				timesList.add((startTime / 60) + ":" + (startTime % 60));
			}
			if (advanceEndTime > endTime) {
				timesList.remove(tempStartTime);
				break;
			}
		}
		int tempNoOfCandidates = noOfCandidates;
		int noOfCandidatesinaDay = (timesList.size() * noOfInterviewers);
		int noOfCandidatesPerGroup = Integer.parseInt(interviewForm
				.getNoOfStudentsPerGroup());
		first: for (int j = 0; j < dates.length; j++) {
			Iterator<String> itr = timesList.iterator();
			String time;
			int candicateDayCount = 0;
			second: while (itr.hasNext()) {
				time = itr.next();
				for (int i = 0; i < noOfInterviewers; i++) {
					for (int k = 0; k < noOfCandidatesPerGroup
							&& tempNoOfCandidates > 0; k++) {
						interviewCardObj = new InterviewCard();
						admAppln = new AdmAppln();
						admAppln.setId(candidateListArray[candiCount]);
						interviewCardObj.setAdmAppln(admAppln);
						interviewCardObj.setTime(time);
						int intPrgCrsId = intPrgCourseMap.get(admAppln.getId());
						InterviewSchedule interviewScheduleBo = new InterviewSchedule();
						//interviewScheduleBo.setId(Integer.parseInt(dates[j]));
						interviewScheduleBo.setId(intScheduleMap.get(intPrgCrsId));
						interviewCardObj.setInterview(interviewScheduleBo);
						interviewCardObj.setInterviewer(i + 1);
						interviewCardObj.setCreatedDate(new Date());
						interviewCardObj.setLastModifiedDate(new Date());
						interviewCard[candiCount++] = interviewCardObj;
						tempNoOfCandidates--;
					}
					if (candicateDayCount++ == noOfCandidatesinaDay) {
						break second;
					}
					if (candiCount == noOfCandidates) {
						break first;
					}
				}
			}
		}

		return interviewCard;
	}

	/**
	 * This method is used to get student list.
	 * 
	 * @param studentList
	 * @return list of type InterviewCardTO.
	 */

	public static List getStudentsList(List studentList) {
		List<InterviewCardTO> interviewCardListTO = new ArrayList<InterviewCardTO>();
		if (studentList != null) {
			Iterator<InterviewCard> itr = studentList.iterator();
			while (itr.hasNext()) {
				InterviewCard interviewCard = (InterviewCard) itr.next();
				InterviewCardTO interviewCardTo = new InterviewCardTO();
				AdmApplnTO admApplnTO = new AdmApplnTO();
				PersonalDataTO personalTO = new PersonalDataTO();
				InterviewScheduleTO interviewScheduleTO = new InterviewScheduleTO();
				admApplnTO.setApplnNo(interviewCard.getAdmAppln().getApplnNo());
				if (interviewCard.getAdmAppln().getIsInterviewSelected() != null) {
					admApplnTO.setIsInterviewSelected(interviewCard
							.getAdmAppln().getIsInterviewSelected());
				}
				interviewCardTo.setAdmApplnTO(admApplnTO);
				if (interviewCard.getAdmAppln().getPersonalData() != null) {
					if (interviewCard.getAdmAppln().getPersonalData()
							.getFirstName() != null) {
						personalTO.setFirstName(interviewCard.getAdmAppln()
								.getPersonalData().getFirstName());
					}
				}
				if (interviewCard.getAdmAppln().getIsInterviewSelected() != null) {
					admApplnTO.setIsInterviewSelected(interviewCard
							.getAdmAppln().getIsInterviewSelected());
				}
				interviewCardTo.getAdmApplnTO().setPersonalData(personalTO);
				if (interviewCard.getInterview() != null) {
					if (interviewCard.getInterview().getDate() != null) {
						interviewScheduleTO.setDate(CommonUtil
								.getStringDate(interviewCard.getInterview()
										.getDate()));
					}
				}
				interviewCardTo.setInterview(interviewScheduleTO);
				if (interviewCard.getInterviewer() != null) {
					interviewCardTo.setInterviewer(interviewCard
							.getInterviewer());
				}
				interviewCardTo.setTime(interviewCard.getTime());
				interviewCardListTO.add(interviewCardTo);

			}
		}
		log.info("exit getStudentsList in InterviewHelper");
		return interviewCardListTO;
	}

	/**
	 * This method is used to get time list.
	 * 
	 * @param interviewProcessForm
	 * @return string array.
	 */

	public static String[] getTimeList(InterviewProcessForm interviewProcessForm) {
		long starttime = 0;
		long endtime = 0;
		long interviewtime = 0;
		long breaktimestart = 0;
		long breaktimeend = 0;
		long breaktimestarttwo = 0;
		long breaktimeendtwo = 0;
		long breaktimestartthree = 0;
		long breaktimeendthree = 0;
		String[] timeOfArray = null;

		if (CommonUtil.checkForEmpty(interviewProcessForm.getStartHours())
				&& CommonUtil
						.checkForEmpty(interviewProcessForm.getStartMins())) {
			if (CommonUtil.checkForEmpty(interviewProcessForm.getEndHours())
					&& CommonUtil.checkForEmpty(interviewProcessForm
							.getEndMins())) {
				if (CommonUtil.checkForEmpty(interviewProcessForm
						.getIntervalHours())
						&& CommonUtil.checkForEmpty(interviewProcessForm
								.getIntervalMins())) {
					starttime = CommonUtil.getMinitues(Integer
							.parseInt(interviewProcessForm.getStartHours()),
							Integer.parseInt(interviewProcessForm
									.getStartMins()));
					endtime = CommonUtil
							.getMinitues(Integer.parseInt(interviewProcessForm
									.getEndHours()),
									Integer.parseInt(interviewProcessForm
											.getEndMins()));
					interviewtime = CommonUtil.getMinitues(Integer
							.parseInt(interviewProcessForm.getIntervalHours()),
							Integer.parseInt(interviewProcessForm
									.getIntervalMins()));
					timeOfArray = CommonUtil.calculateTimeNoBreak(starttime,
							endtime, interviewtime);
				}
			}
		}
		if (CommonUtil.checkForEmpty(interviewProcessForm.getStartHours())
				&& CommonUtil
						.checkForEmpty(interviewProcessForm.getStartMins())) {
			if (CommonUtil.checkForEmpty(interviewProcessForm.getEndHours())
					&& CommonUtil.checkForEmpty(interviewProcessForm
							.getEndMins())) {
				if (CommonUtil.checkForEmpty(interviewProcessForm
						.getIntervalHours())
						&& CommonUtil.checkForEmpty(interviewProcessForm
								.getIntervalMins())) {
					if (CommonUtil.checkForEmpty(interviewProcessForm
							.getBreakFromHours())
							&& CommonUtil.checkForEmpty(interviewProcessForm
									.getBreakFromMins())) {
						if (CommonUtil.checkForEmpty(interviewProcessForm
								.getBreakToHours())
								&& CommonUtil
										.checkForEmpty(interviewProcessForm
												.getBreakToMins())) {
							starttime = CommonUtil.getMinitues(Integer
									.parseInt(interviewProcessForm
											.getStartHours()), Integer
									.parseInt(interviewProcessForm
											.getStartMins()));
							endtime = CommonUtil.getMinitues(Integer
									.parseInt(interviewProcessForm
											.getEndHours()),
									Integer.parseInt(interviewProcessForm
											.getEndMins()));
							interviewtime = CommonUtil.getMinitues(Integer
									.parseInt(interviewProcessForm
											.getIntervalHours()), Integer
									.parseInt(interviewProcessForm
											.getIntervalMins()));
							breaktimestart = CommonUtil.getMinitues(Integer
									.parseInt(interviewProcessForm
											.getBreakFromHours()), Integer
									.parseInt(interviewProcessForm
											.getBreakFromMins()));
							breaktimeend = CommonUtil.getMinitues(Integer
									.parseInt(interviewProcessForm
											.getBreakToHours()), Integer
									.parseInt(interviewProcessForm
											.getBreakToMins()));
							timeOfArray = CommonUtil.calculateTime(starttime,
									endtime, interviewtime, breaktimestart,
									breaktimeend);

						}
					}
				}
			}
		}
		if (CommonUtil.checkForEmpty(interviewProcessForm
				.getBreakFromHoursTwo())
				&& CommonUtil.checkForEmpty(interviewProcessForm
						.getBreakFromMinsTwo())) {
			if (CommonUtil.checkForEmpty(interviewProcessForm
					.getBreakToHoursTwo())
					&& CommonUtil.checkForEmpty(interviewProcessForm
							.getBreakToMinsTwo())) {
				breaktimestarttwo = CommonUtil.getMinitues(Integer
						.parseInt(interviewProcessForm.getBreakFromHoursTwo()),
						Integer.parseInt(interviewProcessForm
								.getBreakFromMinsTwo()));
				breaktimeendtwo = CommonUtil.getMinitues(Integer
						.parseInt(interviewProcessForm.getBreakToHoursTwo()),
						Integer.parseInt(interviewProcessForm
								.getBreakToMinsTwo()));
				String[] timeOfArrayTwo = CommonUtil.calculateTime(starttime, endtime,
						interviewtime, breaktimestarttwo, breaktimeendtwo);
				timeOfArray = CommonUtil.compareTwoStringArrays(timeOfArray,
						timeOfArrayTwo);
			}
		}
		if (CommonUtil.checkForEmpty(interviewProcessForm
				.getBreakFromHoursThree())
				&& CommonUtil.checkForEmpty(interviewProcessForm
						.getBreakFromMinsThree())) {
			if (CommonUtil.checkForEmpty(interviewProcessForm
					.getBreakToHoursThree())
					&& CommonUtil.checkForEmpty(interviewProcessForm
							.getBreakToMinsThree())) {
				breaktimestartthree = CommonUtil.getMinitues(
						Integer.parseInt(interviewProcessForm
								.getBreakFromHoursThree()), Integer
								.parseInt(interviewProcessForm
										.getBreakFromMinsThree()));
				breaktimeendthree = CommonUtil.getMinitues(Integer
						.parseInt(interviewProcessForm.getBreakToHoursThree()),
						Integer.parseInt(interviewProcessForm
								.getBreakToMinsThree()));
				String[] timeOfArrayThree = CommonUtil.calculateTime(starttime, endtime,
						interviewtime, breaktimestartthree, breaktimeendthree);
				timeOfArray = CommonUtil.compareTwoStringArrays(timeOfArray,
						timeOfArrayThree);
			}
		}
		log.info("exit getTimeList in InterviewHelper");

		return timeOfArray;
	}

	/**
	 * @return Mail template for final MeritList
	 * @throws Exception
	 */
	public static List<GroupTemplate> getInterviewScheduleMailTemplate(int courseId, int programId)
			throws Exception {
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		log.debug("Leaving getTemplateList ");

		return ITemplatePassword.getGroupTemplate(courseId, CMSConstants.INTERVIEW_SCHEDULE_TEMPLATE, programId);
	}

	/**
	 * Common Send mail
	 * 
	 * @param admForm
	 * @return
	 */
	public static boolean sendMail(String mailID, String sub, String message,
			Properties prop) {
		boolean sent = false;

//		String adminmail = prop .getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
		String adminmail = CMSConstants.MAIL_USERID;
		String toAddress = mailID;
		// MAIL TO CONSTRUCTION
		String subject = sub;
		String msg = message;

		MailTO mailto = new MailTO();
		mailto.setFromAddress(adminmail);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(prop
				.getProperty("knowledgepro.admission.studentmail.fromName"));
		// uses JMS
		// sent=CommonUtil.postMail(mailto);
		sent = CommonUtil.sendMail(mailto);
		return sent;
	}

	/**
	 * Send mail to student after successful submit of application
	 * 
	 * @param admForm
	 * @return
	 */
	public static boolean sendMailToStudent(
			List<InterviewCard> interviewCardList, HttpServletRequest request,
			InterviewProcessForm interviewForm) throws Exception {
		log.info("entered sendMailToStudent in InterviewHelper");
		boolean sent = false;
		int courseId = 0;
		int programId = 0;
		if(interviewForm.getProgram()!= null && !interviewForm.getProgram().trim().isEmpty()){
			programId = Integer.parseInt(interviewForm.getProgram());
		}
		if(interviewForm.getCourseId()!= null && !interviewForm.getCourseId().trim().isEmpty()){
			courseId = Integer.parseInt(interviewForm.getCourseId());
		}
		
		List<GroupTemplate> templateList = getInterviewScheduleMailTemplate(courseId, programId);
		if (templateList != null && !templateList.isEmpty()
				&& interviewCardList != null && !interviewCardList.isEmpty()) {

			String desc = templateList.get(0).getTemplateDescription();

			// send mail to applicant
			Iterator<InterviewCard> interviewCardListIterator = interviewCardList
					.iterator();

			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(
								CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {
				log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}

			while (interviewCardListIterator.hasNext()) {
				InterviewCard interviewCard = (InterviewCard) interviewCardListIterator
						.next();
				if (interviewCard.getAdmAppln() != null
						&& interviewCard.getAdmAppln().getPersonalData() != null
						&& interviewCard.getAdmAppln().getPersonalData()
								.getEmail() != null) {
					String message = desc;
					message = CommonUtil.replaceMessageText(desc, interviewCard
							.getAdmAppln(), request);
					String interviewDate = "";
					String interviewTime = "";
					String interviewVenue = "";
					String interviewType = "";
					String program = "";
					String academicyear = "";
					if (interviewCard.getInterview() != null
							&& interviewCard.getInterview().getDate() != null) {
						interviewDate = interviewCard.getInterview().getDate()
								.toString();
					}

					if (interviewCard.getTime() != null) {
						interviewTime = interviewCard.getTime();
					}
					if (interviewCard.getInterview() != null
							&& interviewCard.getInterview().getVenue() != null) {
						interviewVenue = interviewCard.getInterview()
								.getVenue();
					}
					if (interviewCard.getInterview() != null
							&& interviewCard.getInterview().getInterview() != null
							&& interviewCard.getInterview().getInterview()
									.getInterviewProgramCourse() != null
							&& interviewCard.getInterview().getInterview()
									.getInterviewProgramCourse().getName() != null) {
						interviewType = interviewCard.getInterview()
								.getInterview().getInterviewProgramCourse()
								.getName();
					}

					if (interviewCard.getAdmAppln() != null
							&& interviewCard.getAdmAppln().getCourseBySelectedCourseId() != null
							&& interviewCard.getAdmAppln().getCourseBySelectedCourseId()
									.getProgram() != null
							&& interviewCard.getAdmAppln().getCourseBySelectedCourseId()
									.getProgram().getName() != null) {
						program = interviewCard.getAdmAppln().getCourseBySelectedCourseId()
								.getProgram().getName();
					}

					if (interviewCard.getAdmAppln() != null
							&& interviewCard.getAdmAppln().getAppliedYear() != null) {
						academicyear = String.valueOf(interviewCard
								.getAdmAppln().getAppliedYear())
								+ "-"
								+ (interviewCard.getAdmAppln().getAppliedYear() + 1);
					}

					String subject = interviewType + " for " + program + " "
							+ academicyear;
					message = message
							.replace(CMSConstants.TEMPLATE_INTERVIEW_DATE,
									interviewDate);
					message = message
							.replace(CMSConstants.TEMPLATE_INTERVIEW_TIME,
									interviewTime);
					message = message.replace(
							CMSConstants.TEMPLATE_INTERVIEW_VENUE,
							interviewVenue);
					message = message
							.replace(CMSConstants.TEMPLATE_INTERVIEW_TYPE,
									interviewType);

					if (interviewForm.getInterviewTypeList()!= null && interviewForm.getInterviewTypeList().size() > 0) {
						if (interviewCard.getInterview() != null
								&& interviewCard.getInterview().getInterview() != null
								&& interviewCard.getInterview().getInterview()
										.getInterviewProgramCourse() != null
								&& interviewCard.getInterview().getInterview()
										.getInterviewProgramCourse().getId() != 0) {
							if (interviewForm.getInterviewTypeList().contains(interviewCard.getInterview().getInterview().getInterviewProgramCourse().getId())) {
								// send mail
								sendMail(interviewCard.getAdmAppln()
										.getPersonalData().getEmail(), subject,
										message, prop);
							}
						}
					}
				}
			}
		}
		return sent;
	}

	/**
	 * This method is used to get InterviewCard details.
	 * 
	 * @param interviewCard
	 * @param org
	 * @param selectedCandidates
	 * @param interviewForm
	 * @return list of type InterviewCardTO.
	 */

	public static List getInterviewCardTO(List interviewCard, Organisation org,
			String[] selectedCandidates, InterviewProcessForm interviewForm) {
		log.info("entered getInterviewCardTO in InterviewHelper");
		List<InterviewCardTO> interviewCardListTO = new ArrayList<InterviewCardTO>();
		InterviewCard iCard = null;
		if (interviewCard != null) {
			Iterator<InterviewCard> itr = interviewCard.iterator();
			while (itr.hasNext()) {
				iCard = (InterviewCard) itr.next();
				InterviewCardTO interviewCardTO = new InterviewCardTO();
				AdmApplnTO admApplnTO = new AdmApplnTO();
				PersonalDataTO personalDataTO = new PersonalDataTO();
				CourseTO courseTO = new CourseTO();
				InterviewScheduleTO interviewScheduleTO = new InterviewScheduleTO();
				if (selectedCandidates != null) {
					for (int i = 0; i < selectedCandidates.length; i++) {
						String[] appNoAndInterType = CommonUtil
								.getInterviewType(selectedCandidates[i]);
							if (appNoAndInterType[0].equalsIgnoreCase(String
									.valueOf(iCard.getAdmAppln().getApplnNo()))) {
								if (appNoAndInterType[1].equalsIgnoreCase(iCard
										.getInterview().getInterview()
										.getInterviewProgramCourse().getName())) {

									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln().getApplnNo() != 0) {
										admApplnTO.setApplnNo(iCard
												.getAdmAppln().getApplnNo());
									}
									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln()
													.getPersonalData() != null
											&& iCard.getAdmAppln()
													.getPersonalData()
													.getFirstName() != null) {
										personalDataTO.setFirstName(iCard
												.getAdmAppln()
												.getPersonalData()
												.getFirstName());
										admApplnTO
												.setPersonalData(personalDataTO);
									}
									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln()
													.getPersonalData() != null
											&& iCard.getAdmAppln()
													.getPersonalData()
													.getMiddleName() != null) {
										personalDataTO.setMiddleName(iCard
												.getAdmAppln()
												.getPersonalData()
												.getMiddleName());
										admApplnTO
												.setPersonalData(personalDataTO);
									}
									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln()
													.getPersonalData() != null
											&& iCard.getAdmAppln()
													.getPersonalData()
													.getLastName() != null) {
										personalDataTO.setLastName(iCard
												.getAdmAppln()
												.getPersonalData()
												.getLastName());
										admApplnTO
												.setPersonalData(personalDataTO);
									}

									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln().getCourseBySelectedCourseId() != null
											&& iCard.getAdmAppln().getCourseBySelectedCourseId()
													.getName() != null) {
										courseTO.setName(iCard.getAdmAppln()
												.getCourseBySelectedCourseId().getName());
										admApplnTO.setCourse(courseTO);
									}
									interviewCardTO.setAdmApplnTO(admApplnTO);
									if (iCard.getInterview() != null
											&& iCard.getInterview().getDate() != null) {
										interviewScheduleTO.setDate(CommonUtil
												.getStringDate(iCard
														.getInterview()
														.getDate()));
									}
									if (iCard.getInterview() != null
											&& iCard.getInterview()
													.getInterview() != null
											&& iCard
													.getInterview()
													.getInterview()
													.getInterviewProgramCourse() != null
											&& iCard
													.getInterview()
													.getInterview()
													.getInterviewProgramCourse()
													.getName() != null) {
										interviewCardTO
												.setInterviewType(appNoAndInterType[1]);
									}
									if (iCard.getCreatedDate() != null) {
										interviewCardTO.setIssueDate(CommonUtil
												.getStringDate(iCard
														.getCreatedDate()));
									}
									if (org.getName() != null) {
										interviewCardTO.setCollegeName(org
												.getName());
									}
									if (org.getAddressLine1() != null) {
										interviewCardTO.setCollegeAddress(org
												.getAddressLine1());
									}
									if (org.getAddressLine2() != null) {
										interviewCardTO.setCollegeAddress1(org
												.getAddressLine2());
									}
									if (org.getAddressLine3() != null) {
										interviewCardTO.setCollegeAddress2(org
												.getAddressLine3());
									}

									if (appNoAndInterType[0] != null) {
										try {
											int courseID = InterviewTypeHandler
													.getInstance()
													.getCourseID(
															Integer
																	.parseInt(appNoAndInterType[0]));
											String intCardContent = InterviewTypeHandler
													.getInstance()
													.getIntCardContent(
															courseID,
															appNoAndInterType[1]);
											interviewCardTO
													.setIntCardContent(intCardContent);
										} catch (Exception e) {
											log.info("error in getInterviewCardTO of InterviewHelper class ");

										}
									}
									interviewCardTO
											.setInterview(interviewScheduleTO);
									interviewCardTO.setTime(iCard.getTime());

									interviewCardListTO.add(interviewCardTO);

								}
							}
					}
				}
			}
		}
		log.info("exit getInterviewCardTO in InterviewHelper");
		return interviewCardListTO;
	}

	/**
	 * This method is used to get InterviewCard details.
	 * 
	 * @param interviewCard
	 * @param selectedCandidates
	 * @return list of type InterviewCardTO.
	 */

	public static List getInterviewCardTOList(List interviewCard,
			String[] selectedCandidates) {
		List<InterviewCardTO> interviewCardListTO = new ArrayList<InterviewCardTO>();
		if (interviewCard != null) {
			Iterator<InterviewCard> itr = interviewCard.iterator();
			while (itr.hasNext()) {
				InterviewCard iCard = (InterviewCard) itr.next();

				InterviewCardTO interviewCardTO = new InterviewCardTO();
				AdmApplnTO admApplnTO = new AdmApplnTO();
				PersonalDataTO personalDataTO = new PersonalDataTO();
				CourseTO courseTO = new CourseTO();
				InterviewScheduleTO interviewScheduleTO = new InterviewScheduleTO();
				if (selectedCandidates != null) {
					for (int i = 0; i < selectedCandidates.length; i++) {
						String[] appNoAndInterType = CommonUtil
								.getInterviewType(selectedCandidates[i]);

							if (appNoAndInterType[0].equalsIgnoreCase(String
									.valueOf(iCard.getAdmAppln().getApplnNo()))) {
								if (appNoAndInterType[1].equalsIgnoreCase(iCard
										.getInterview().getInterview()
										.getInterviewProgramCourse().getName())) {

									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln().getApplnNo() != 0) {
										admApplnTO.setApplnNo(iCard
												.getAdmAppln().getApplnNo());
									}
									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln()
													.getPersonalData() != null
											&& iCard.getAdmAppln()
													.getPersonalData()
													.getFirstName() != null) {
										personalDataTO.setFirstName(iCard
												.getAdmAppln()
												.getPersonalData()
												.getFirstName());
										admApplnTO
												.setPersonalData(personalDataTO);
									}
									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln()
													.getPersonalData() != null
											&& iCard.getAdmAppln()
													.getPersonalData()
													.getMiddleName() != null) {
										personalDataTO.setMiddleName(iCard
												.getAdmAppln()
												.getPersonalData()
												.getMiddleName());
										admApplnTO
												.setPersonalData(personalDataTO);
									}
									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln()
													.getPersonalData() != null
											&& iCard.getAdmAppln()
													.getPersonalData()
													.getLastName() != null) {
										personalDataTO.setLastName(iCard
												.getAdmAppln()
												.getPersonalData()
												.getLastName());
										admApplnTO
												.setPersonalData(personalDataTO);
									}
									if (iCard.getAdmAppln() != null
											&& iCard.getAdmAppln().getCourseBySelectedCourseId() != null
											&& iCard.getAdmAppln().getCourseBySelectedCourseId()
													.getName() != null) {
										courseTO.setName(iCard.getAdmAppln()
												.getCourseBySelectedCourseId().getName());
										admApplnTO.setCourse(courseTO);
									}
									interviewCardTO.setAdmApplnTO(admApplnTO);
									if (iCard.getInterview() != null
											&& iCard.getInterview().getDate() != null) {
										interviewScheduleTO.setDate(CommonUtil
												.getStringDate(iCard
														.getInterview()
														.getDate()));
									}
									if (iCard.getInterview() != null
											&& iCard.getInterview()
													.getInterview() != null
											&& iCard
													.getInterview()
													.getInterview()
													.getInterviewProgramCourse() != null
											&& iCard
													.getInterview()
													.getInterview()
													.getInterviewProgramCourse()
													.getName() != null) {
										interviewCardTO
												.setInterviewType(String
														.valueOf(iCard
																.getInterview()
																.getInterview()
																.getInterviewProgramCourse()
																.getName()));
									}

									interviewCardTO
											.setInterview(interviewScheduleTO);
									interviewCardTO.setTime(iCard.getTime());
									interviewCardTO.setInterviewer(iCard
											.getInterviewer());
									interviewCardListTO.add(interviewCardTO);
								}
							}
					}
				}
			}
		}
		log.info("exit getInterviewCardTOList in InterviewHelper");
		return interviewCardListTO;
	}

	private static int[] convertStringtoIntArray(String[] candidateList) {
		int[] candidateArray = new int[candidateList.length];
		for (int i = 0; i < candidateList.length; i++) {
			candidateArray[i] = Integer.parseInt(candidateList[i]);
		}

		return candidateArray;
	}

	/**
	 * @param interviewCardList
	 * @param interviewForm
	 * @throws Exception
	 */
	public static void sendSMSToStudent(List<InterviewCard> interviewCardList, InterviewProcessForm interviewForm) throws Exception {

		log.info("entered sendMailToStudent in InterviewHelper");
		Iterator<InterviewCard> interviewCardListIterator=interviewCardList.iterator();
			while (interviewCardListIterator.hasNext()) {
				InterviewCard interviewCard = (InterviewCard) interviewCardListIterator
						.next();
				
				if (interviewCard.getAdmAppln() != null
						&& interviewCard.getAdmAppln().getCourseBySelectedCourseId().getIsApplicationProcessSms()) {
/* Commented below condition as sms should be sent after every interview card generation for any round of interview
 * 	   				String sequence=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(interviewCard.getId(),"InterviewCard",false,"interview.interview.interviewProgramCourse.sequence");
					if(sequence!=null && sequence.equalsIgnoreCase("1")){*/
						String mobileNo="";
						if(interviewCard.getAdmAppln().getPersonalData().getMobileNo1()!=null && !interviewCard.getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
							if(interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
									|| interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
								mobileNo = "91";
							else
								mobileNo=interviewCard.getAdmAppln().getPersonalData().getMobileNo1();
						}else{
							mobileNo="91";
						}
						if(interviewCard.getAdmAppln().getPersonalData().getMobileNo2()!=null && !interviewCard.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
							mobileNo=mobileNo+interviewCard.getAdmAppln().getPersonalData().getMobileNo2();
						}
						//Application No Added By Manu	
						if(mobileNo.length()==12){
							ApplicationStatusUpdateHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_TEMPLATE_E_ADMITCARD,Integer.toString(interviewCard.getAdmAppln().getApplnNo()));
						}
					/*} */
				}
			}
	}

}
