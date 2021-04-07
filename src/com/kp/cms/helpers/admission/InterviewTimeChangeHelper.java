package com.kp.cms.helpers.admission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.forms.admission.InterviewTimeChangeForm;
import com.kp.cms.handlers.admission.InterviewTimeChangeHandler;
import com.kp.cms.to.admission.InterviewTimeChangeTO;
import com.kp.cms.transactions.admission.IInterviewChangeTimeTxn;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author kalyan.c
 * Helper class for Edit Interview Schedule 
 */
public class InterviewTimeChangeHelper {
	/**
	 * @param interviewTimeChangeForm
	 * @return
	 * This method will convert from to TO,s
	 */
	public InterviewTimeChangeTO convertFormtoTo(InterviewTimeChangeForm interviewTimeChangeForm) {
		
		InterviewTimeChangeTO interviewTimeChangeTO =  new InterviewTimeChangeTO();
		
		interviewTimeChangeTO.setAppliedYear(interviewTimeChangeForm.getAppliedYear());
		interviewTimeChangeTO.setCourseId(interviewTimeChangeForm.getCourseId());
		interviewTimeChangeTO.setInterviewDate(interviewTimeChangeForm.getInterviewDate());
		interviewTimeChangeTO.setStartTimeHours(interviewTimeChangeForm.getStartingTimeHours());
		interviewTimeChangeTO.setStartTimeMins(interviewTimeChangeForm.getStartingTimeMins());
		interviewTimeChangeTO.setEndTimeHours(interviewTimeChangeForm.getEndingTimeHours());
		interviewTimeChangeTO.setEndTimeMins(interviewTimeChangeForm.getEndingTimeMins());
		if(interviewTimeChangeForm.getAppNoForm()!=null && !interviewTimeChangeForm.getAppNoForm().isEmpty()){
			interviewTimeChangeTO.setAppNoForm(Integer.parseInt(interviewTimeChangeForm.getAppNoForm()));
		}else{
			interviewTimeChangeTO.setAppNoForm(0);
		}
		if(interviewTimeChangeForm.getAppNoTo()!=null && !interviewTimeChangeForm.getAppNoTo().isEmpty()){
			interviewTimeChangeTO.setAppNoTo(Integer.parseInt(interviewTimeChangeForm.getAppNoTo()));
		}else{
			interviewTimeChangeTO.setAppNoTo(0);
		}
		return interviewTimeChangeTO;
	}
	/**
	 * @param applicantDetails
	 * @param historyMap 
	 * @return
	 * This method will covert BO's to TO's
	 */
	public List<InterviewTimeChangeTO> convertBotoTo(List applicantDetails,List<Integer> alreadyDataEnter, Map<Integer, String> historyMap) {
		List<InterviewTimeChangeTO> applicantDetailsList = new ArrayList<InterviewTimeChangeTO>();
		InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
		Set<String> s=new HashSet<String>(); 
		if (applicantDetails != null) {
			Iterator it = applicantDetails.iterator();
			while (it.hasNext()) {
				Object[] searchResults = (Object[]) it.next();
				InterviewTimeChangeTO applicantSearch = new InterviewTimeChangeTO();
				applicantSearch.setApplicationId(searchResults[0].toString());
				if(!alreadyDataEnter.contains(Integer.parseInt(applicantSearch.getApplicationId()))){
				applicantSearch.setApplicationNo(searchResults[1].toString());
				applicantSearch.setAppliedYear(searchResults[2].toString());
				if(searchResults[4] == null && searchResults[5] ==null){
					applicantSearch.setApplicantName(searchResults[3].toString());
				}else if(searchResults[4] == null){
					applicantSearch.setApplicantName(searchResults[3].toString() +" "+ searchResults[5].toString());
				}else{
					applicantSearch.setApplicantName(searchResults[3].toString() +" "+ searchResults[4].toString() +" "+ searchResults[5].toString());
				}
				if(searchResults[6]!=null){
					applicantSearch.setCourseId(searchResults[6].toString());
				}
				if(searchResults[7]!=null){
					applicantSearch.setCourseName(searchResults[7].toString());
				}
				if(searchResults[8]!=null){
					interviewProgramCourse = (InterviewProgramCourse)searchResults[8]; 
				}				
				if(interviewProgramCourse!=null){
					applicantSearch.setInterviewTypeId(interviewProgramCourse.getId());
					applicantSearch.setInterviewType(interviewProgramCourse.getName());
				}
				if(searchResults[9]!=null){
					applicantSearch.setInterviewDate(CommonUtil.ConvertStringToDateFormat(searchResults[9].toString(), "yyyy-MM-dd", "dd/MM/yyyy"));
					applicantSearch.setOldInterviewDate(CommonUtil.ConvertStringToDateFormat(searchResults[9].toString(), "yyyy-MM-dd", "dd/MM/yyyy"));
				}
				if(searchResults[10]!=null){
					applicantSearch.setTimeID(searchResults[10].toString());
				}
				if(searchResults[11]!=null){
					applicantSearch.setInterviewTime(searchResults[11].toString());
					applicantSearch.setOldInterviewTime(searchResults[11].toString());
				}
				if(searchResults[12]!=null){
					applicantSearch.setEmail(searchResults[12].toString());
				}
				if(searchResults[13]!=null){
					applicantSearch.setStartTimeHours(searchResults[13].toString());
				}
				if(searchResults[14]!=null){
					applicantSearch.setEndTimeHours(searchResults[14].toString());
				}
				if(searchResults[15]!=null){
					applicantSearch.setInterviewers(Integer.parseInt((searchResults[15].toString())));
				}
				if(searchResults[16]!=null){
					applicantSearch.setVenue(searchResults[16].toString());
				}
				if(searchResults[17]!=null){
					applicantSearch.setInterviewId(Integer.parseInt(searchResults[17].toString()));
				}
				if(searchResults[18]!=null){
					applicantSearch.setSelectionProcessId(searchResults[18].toString());
				}
				if(historyMap.containsKey(Integer.parseInt(applicantSearch.getApplicationId()))){
					applicantSearch.setTotalRounds(historyMap.get(Integer.parseInt(applicantSearch.getApplicationId())));
				}
				if(!s.contains(applicantSearch.getApplicationId())){
				applicantDetailsList.add(applicantSearch);
				s.add(applicantSearch.getApplicationId());
				}
				}
			}
		}	
		return applicantDetailsList;
	}

	/**
	 * @param interviewTimeChangeTOList
	 * @param userId
	 * @return
	 * This method will convert from TO's to BO's
	 */
	public List<InterviewCard> convertTotoBo(List<InterviewTimeChangeTO> interviewTimeChangeTOList, String userId) {
		List<InterviewCard> applicantDetailsList = new ArrayList<InterviewCard>();
		if (interviewTimeChangeTOList != null) {
			Iterator<InterviewTimeChangeTO> it = interviewTimeChangeTOList.iterator();

			while (it.hasNext()) {
				InterviewTimeChangeTO interviewTimeChangeTO = it.next();
				InterviewCard iCard = new InterviewCard();
				if(interviewTimeChangeTO!=null && interviewTimeChangeTO.getTimeID()!=null){
					iCard.setId(Integer.parseInt(interviewTimeChangeTO.getTimeID()));					
				}	
				if(interviewTimeChangeTO!=null && interviewTimeChangeTO.getInterviewTime()!=null){
					iCard.setTime(interviewTimeChangeTO.getInterviewTime());				
				}
				applicantDetailsList.add(iCard);				
			}
		}	
		return applicantDetailsList;
	}

	
//	/**
//	 * @param originalStudentList
//	 * @param updatedStudentList
//	 * This method will send mail to the candidates
//	 */
//	public static void sendMail(List<InterviewTimeChangeTO> originalStudentList, List<InterviewTimeChangeTO> updatedStudentList) {		
//
//		if (originalStudentList != null) {
//			Iterator<InterviewTimeChangeTO> it = originalStudentList.iterator();
//			if (updatedStudentList != null) {
//				Iterator<InterviewTimeChangeTO> it1 = updatedStudentList.iterator();
//
//				while (it.hasNext()) {
//					InterviewTimeChangeTO originalObject = it.next();
//					InterviewTimeChangeTO updatedObject = it1.next();
//					if(!originalObject.getInterviewTime().equalsIgnoreCase(updatedObject.getInterviewTime())){
//						InterviewTimeChangeHandler.getInstance().sendMailToStudent(updatedObject);
//					}
//				}
//			}	
//		}
//	}
	
	/**
	 * @param originalStudentList
	 * @param updatedStudentList
	 * This method will send mail to the candidates
	 */
	public static void sendMail(List<InterviewTimeChangeTO> originalStudentList, List<InterviewTimeChangeTO> updatedStudentList) {		
		if (updatedStudentList != null) {
			Iterator<InterviewTimeChangeTO> it1 = updatedStudentList.iterator();
			while (it1.hasNext()) {
				InterviewTimeChangeTO updatedObject = it1.next();
				if(!updatedObject.getInterviewTime().equals(updatedObject.getOldInterviewTime()) || !updatedObject.getInterviewDate().equals(updatedObject.getOldInterviewDate())){
					InterviewTimeChangeHandler.getInstance().sendMailToStudent(updatedObject);
				}
			}
		}
	}
	/**
	 * @param boList
	 * @param interveiwChangeTime 
	 * @return
	 * @throws Exception
	 */
	public List<InterviewTimeChangeTO> convertBoToTo(
			List<InterviewCardHistory> boList, InterviewTimeChangeForm interviewTimeChangeForm) throws Exception{
		List<InterviewTimeChangeTO> tos = new ArrayList<InterviewTimeChangeTO>();
		if(boList != null && !boList.isEmpty()){
			Iterator<InterviewCardHistory> iterator = boList.iterator();
			while (iterator.hasNext()) {
				InterviewCardHistory interviewCardHistory = (InterviewCardHistory) iterator.next();
				InterviewTimeChangeTO to = new InterviewTimeChangeTO();
				/* modified by sudhir */
				interviewTimeChangeForm.setApplicantName(interviewCardHistory.getAdmAppln().getPersonalData().getFirstName());
				/* modified by sudhir */
				to.setInterviewDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewCardHistory.getInterview().getDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
				to.setReportingTime(interviewCardHistory.getTime());
				/* code added by sudhir */
				String date = CommonUtil.formatDates(interviewCardHistory.getLastModifiedDate());
				String time = getTimeByDate(interviewCardHistory.getLastModifiedDate());
				to.seteAdmitCardGenerateOn(date+" "+time);
				interviewTimeChangeForm.setApplnNo(String.valueOf(interviewCardHistory.getAdmAppln().getApplnNo()));
				/* code added by sudhir */
				tos.add(to);
			}
		}
		return tos;
	}
	/**
	 * @param dateString
	 * @return
	 */
	public static String getTimeByDate(Date dateString) {
    	final long timestamp = dateString.getTime();

    	// with java.util.Date/Calendar api
    	final Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(timestamp);
    	// and here's how to get the String representation
    	final String timeString =
    	    new SimpleDateFormat("HH:mm:ss a").format(cal.getTime());
		 return timeString;
			}
}
