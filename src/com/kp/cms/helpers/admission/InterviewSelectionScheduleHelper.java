package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.admission.InterviewTimeSelection;
import com.kp.cms.bo.admission.InterviewVenueSelection;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.InterviewSelectionScheduleForm;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.handlers.hostel.AbsentiesListHandler;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.helpers.hostel.AbsentiesListHelper;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.to.admission.InterviewSelectionScheduleTO;
import com.kp.cms.to.admission.InterviewTimeSelectionTO;
import com.kp.cms.to.admission.InterviewVenuSelectionTO;
import com.kp.cms.transactions.admission.IInterviewSelectionScheduleTrans;
import com.kp.cms.transactionsimpl.admission.InterviewSelectionScheduleTransImpl;
import com.kp.cms.utilities.CommonUtil;

public class InterviewSelectionScheduleHelper {
	IInterviewSelectionScheduleTrans transactions=InterviewSelectionScheduleTransImpl.getInstance();
	public static volatile InterviewSelectionScheduleHelper interviewSelectionScheduleHelper=null;
	
	//private constructor
	private InterviewSelectionScheduleHelper(){
		
	}
	//singleton object
	public static  InterviewSelectionScheduleHelper getInstance(){
		if(interviewSelectionScheduleHelper==null){
			interviewSelectionScheduleHelper=new InterviewSelectionScheduleHelper();
			return interviewSelectionScheduleHelper;
		}
		return interviewSelectionScheduleHelper;
	}
	
	public String queryForDuplicateCheck(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("from InterviewSelectionSchedule i where i.isActive=1 and i.programId.id="+interviewSelectionScheduleForm.getProgramId()+
				" and '"+CommonUtil.ConvertStringToSQLDate(interviewSelectionScheduleForm.getSelectionProcessDate())+
				"' between i.selectionProcessDate and i.cutOffDate and i.academicYear='"+interviewSelectionScheduleForm.getAcademicYear()+"'"+
				" and i.examCenter.id="+interviewSelectionScheduleForm.getVenueId());
		if(interviewSelectionScheduleForm.getId()>0){
			stringBuilder.append(" and i.id!="+interviewSelectionScheduleForm.getId());
		}
		return stringBuilder.toString();
	}
	
	public InterviewSelectionSchedule convertFormToBo(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		InterviewSelectionSchedule interviewSelectionSchedule=new InterviewSelectionSchedule();
		//start interview selection schedule table
		Program program=new Program();
		program.setId(Integer.parseInt(interviewSelectionScheduleForm.getProgramId()));
		interviewSelectionSchedule.setProgramId(program);
		interviewSelectionSchedule.setAcademicYear(interviewSelectionScheduleForm.getAcademicYear());
		interviewSelectionSchedule.setSelectionProcessDate(CommonUtil.ConvertStringToSQLDate(interviewSelectionScheduleForm.getSelectionProcessDate()));
		interviewSelectionSchedule.setCutOffDate(CommonUtil.ConvertStringToSQLDate(interviewSelectionScheduleForm.getCutOffDate()));
		interviewSelectionSchedule.setMaxNumOfSeatsOffline(Integer.parseInt(interviewSelectionScheduleForm.getMaxNumOfSeatsOffline()));
		interviewSelectionSchedule.setMaxNumOfSeatsOnline(Integer.parseInt(interviewSelectionScheduleForm.getMaxNumOfSeatsOnline()));
		interviewSelectionSchedule.setCreatedBy(interviewSelectionScheduleForm.getUserId());
		interviewSelectionSchedule.setModifiedBy(interviewSelectionScheduleForm.getUserId());
		interviewSelectionSchedule.setCreatedDate(new Date());
		interviewSelectionSchedule.setLastModifiedDate(new Date());
		interviewSelectionSchedule.setIsActive(true);
		// /* code added by chandra
		ExamCenter examecnter=new ExamCenter();
		examecnter.setId(interviewSelectionScheduleForm.getVenueId());
		interviewSelectionSchedule.setExamCenter(examecnter);
		// */ code added by chandra
		//end interview selection schedule table
		//start interview time selection table
		Set<InterviewTimeSelection> interviewTimeSelectionSet=new HashSet<InterviewTimeSelection>();
		InterviewTimeSelection interviewTimeSelection=null;
		StringBuilder stringBuilder=null;
		StringBuilder stringBuilder1=null;
		Iterator<InterviewTimeSelectionTO> iterator=interviewSelectionScheduleForm.getTimeList().iterator();
		while (iterator.hasNext()) {
			InterviewTimeSelectionTO interviewTimeSelectionTO = (InterviewTimeSelectionTO) iterator.next();
			interviewTimeSelection=new InterviewTimeSelection();
			stringBuilder=new StringBuilder();
			stringBuilder1=new StringBuilder();
			stringBuilder.append(interviewTimeSelectionTO.getHours()+":"+interviewTimeSelectionTO.getMins()+":00");
			interviewTimeSelection.setTime(stringBuilder.toString());
			stringBuilder1.append(interviewTimeSelectionTO.getHenceHours()+":"+interviewTimeSelectionTO.getHenceMins()+":00");
			interviewTimeSelection.setEndTime(stringBuilder1.toString());
			interviewTimeSelection.setMaxSeats(Integer.parseInt(interviewTimeSelectionTO.getMaxCandidates()));
			interviewTimeSelection.setCreatedBy(interviewSelectionScheduleForm.getUserId());
			interviewTimeSelection.setModifiedBy(interviewSelectionScheduleForm.getUserId());
			interviewTimeSelection.setCreatedDate(new Date());
			interviewTimeSelection.setLastModifiedDate(new Date());
			interviewTimeSelection.setIsActive(true);
			interviewTimeSelection.setInterviewSelectionSchedule(interviewSelectionSchedule);
			interviewTimeSelectionSet.add(interviewTimeSelection);
		}
		interviewSelectionSchedule.setInterviewTimeSelections(interviewTimeSelectionSet);
		//end interview time selection table
		//start interview venue selection table
		
		// start /* code commented by chandra
		
		/*Set<InterviewVenueSelection> interviewVenueSelectionSet=new HashSet<InterviewVenueSelection>();
		InterviewVenueSelection interviewVenueSelection=null;
		Iterator<InterviewVenuSelectionTO> iterator2=interviewSelectionScheduleForm.getVenueList().iterator();
		while (iterator2.hasNext()) {
			InterviewVenuSelectionTO interviewVenuSelectionTO = (InterviewVenuSelectionTO) iterator2.next();
			interviewVenueSelection=new InterviewVenueSelection();
			interviewVenueSelection.setInterviewSelectionSchedule(interviewSelectionSchedule);
			ExamCenter examCenter=new ExamCenter();
			examCenter.setId(interviewVenuSelectionTO.getVenueId());
			interviewVenueSelection.setExamCenter(examCenter);
			interviewVenueSelection.setCreatedBy(interviewSelectionScheduleForm.getUserId());
			interviewVenueSelection.setModifiedBy(interviewSelectionScheduleForm.getUserId());
			interviewVenueSelection.setCreatedDate(new Date());
			interviewVenueSelection.setLastModifiedDate(new Date());
			interviewVenueSelection.setIsActive(true);
			interviewVenueSelectionSet.add(interviewVenueSelection);
		}
		interviewSelectionSchedule.setInterviewVenueSelections(interviewVenueSelectionSet);*/
		
		// end */code commented by chandra
		
		//end interview venue selection table
		return interviewSelectionSchedule;
	}
	
	public List<InterviewSelectionScheduleTO> convertBosToTos(List<InterviewSelectionSchedule> list,InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		
		Map<Integer,Integer> getTotalAppliedStudentsMap=transactions.getTotalNumberOfStudentsAppliedForAdmitcard(interviewSelectionScheduleForm);
		
		List<InterviewSelectionScheduleTO> list1=new ArrayList<InterviewSelectionScheduleTO>();
		Iterator<InterviewSelectionSchedule> iterator=list.iterator();
		InterviewSelectionScheduleTO interviewSelectionScheduleTO=null;
		while (iterator.hasNext()) {
			InterviewSelectionSchedule interviewSelectionSchedule = (InterviewSelectionSchedule) iterator.next();
			interviewSelectionScheduleTO=new InterviewSelectionScheduleTO();
			interviewSelectionScheduleTO.setId(interviewSelectionSchedule.getId());
			interviewSelectionScheduleTO.setAcademicYear(interviewSelectionSchedule.getAcademicYear());
			interviewSelectionScheduleTO.setProgramName(interviewSelectionSchedule.getProgramId().getName());
			interviewSelectionScheduleTO.setSelectionProcessDate(CommonUtil.formatDates(interviewSelectionSchedule.getSelectionProcessDate()));
			if(interviewSelectionSchedule.getExamCenter()!=null ){
				if(interviewSelectionSchedule.getExamCenter().getCenter()!=null && !interviewSelectionSchedule.getExamCenter().getCenter().isEmpty())
					interviewSelectionScheduleTO.setVenueName(interviewSelectionSchedule.getExamCenter().getCenter());
			}
			interviewSelectionScheduleTO.setCutOffDate(CommonUtil.formatDates(interviewSelectionSchedule.getCutOffDate()));
			interviewSelectionScheduleTO.setMaxNoSeatsOnline(interviewSelectionSchedule.getMaxNumOfSeatsOnline());
			interviewSelectionScheduleTO.setMaxNoSeatsOffline(interviewSelectionSchedule.getMaxNumOfSeatsOffline());
			if(getTotalAppliedStudentsMap!=null && !getTotalAppliedStudentsMap.isEmpty()){
				if(getTotalAppliedStudentsMap.containsKey(interviewSelectionSchedule.getId())){
					interviewSelectionScheduleTO.setTotalAppliedStudents(getTotalAppliedStudentsMap.get(interviewSelectionSchedule.getId()));
				}else{
					interviewSelectionScheduleTO.setTotalAppliedStudents(0);
				}
			}else{
				interviewSelectionScheduleTO.setTotalAppliedStudents(0);
			}
			list1.add(interviewSelectionScheduleTO);
		}
		return list1;
	}
	
	public void convertBosToForm(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm,
			InterviewSelectionSchedule interviewSelectionSchedule) throws Exception{
			Map<String,String> hoursMap=AvailableSeatsHandler.getInstance().getHoursMap();
			Map<String,String> minsMap=AvailableSeatsHandler.getInstance().getMinMap();
		//start
			interviewSelectionScheduleForm.setId(interviewSelectionSchedule.getId());
			interviewSelectionScheduleForm.setAcademicYear(interviewSelectionSchedule.getAcademicYear());
			//start programMap by academic year
			Map<Integer, String> programMap=transactions.getprogramMap();
			interviewSelectionScheduleForm.setProgramMap(CommonUtil.sortMapByValue(programMap));
			interviewSelectionScheduleForm.setProgramId(String.valueOf(interviewSelectionSchedule.getProgramId().getId()));
			//end programMap by academic year
			//get venue map
			Map<Integer,String> venueMap=transactions.getVenues(Integer.parseInt(interviewSelectionScheduleForm.getProgramId()));
			//end venue map
			interviewSelectionScheduleForm.setSelectionProcessDate(CommonUtil.formatDates(interviewSelectionSchedule.getSelectionProcessDate()));
				List<InterviewCard> card1=transactions.getGeneratedCardDetails(interviewSelectionSchedule.getId(),(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewSelectionSchedule.getSelectionProcessDate()), "dd-MMM-yyyy", "dd/MM/yyyy")));
				if(card1!=null && !card1.isEmpty()){
					interviewSelectionScheduleForm.setDateChangedFlag("true");
					interviewSelectionScheduleForm.setInterviewCard(card1);
				}
			interviewSelectionScheduleForm.setCutOffDate(CommonUtil.formatDates(interviewSelectionSchedule.getCutOffDate()));
			interviewSelectionScheduleForm.setMaxNumOfSeatsOffline(String.valueOf(interviewSelectionSchedule.getMaxNumOfSeatsOffline()));
			interviewSelectionScheduleForm.setMaxNumOfSeatsOnline(String.valueOf(interviewSelectionSchedule.getMaxNumOfSeatsOnline()));
			//start time selection table
			Set<InterviewTimeSelection> timeSet=interviewSelectionSchedule.getInterviewTimeSelections();
			int totalCount=0;
			List<InterviewTimeSelectionTO> timeList=new ArrayList<InterviewTimeSelectionTO>();
			InterviewTimeSelectionTO interviewTimeSelectionTO=null;
			Iterator<InterviewTimeSelection> iterator=timeSet.iterator();
			while (iterator.hasNext()) {
				InterviewTimeSelection interviewTimeSelection = (InterviewTimeSelection) iterator.next();
				if(interviewTimeSelection.getIsActive()){
					interviewTimeSelectionTO=new InterviewTimeSelectionTO();
					interviewTimeSelectionTO.setId(interviewTimeSelection.getId());
					interviewTimeSelectionTO.setTimeTemplate("From Time");
					interviewTimeSelectionTO.setTimeHenceTemplate("To Time");
					interviewTimeSelectionTO.setCandidatesTemplate("Candidate");
					interviewTimeSelectionTO.setHoursMap(hoursMap);
					interviewTimeSelectionTO.setMinsMap(minsMap);
					interviewTimeSelectionTO.setHours(interviewTimeSelection.getTime().substring(0, 2).toString());
					interviewTimeSelectionTO.setMins(interviewTimeSelection.getTime().substring(3, 5).toString());
					if(interviewTimeSelection.getId()>0){
						List<InterviewCard> card=transactions.getGeneratedCardDetails(interviewSelectionSchedule.getId(),CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewSelectionSchedule.getSelectionProcessDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
						if(card!=null && !card.isEmpty()){
							interviewSelectionScheduleForm.setDateChangedFlag("true");
							interviewSelectionScheduleForm.setInterviewCard(card);
						}
					}
					if(interviewTimeSelection.getEndTime()!=null && !interviewTimeSelection.getEndTime().isEmpty()){
						interviewTimeSelectionTO.setHenceHours(interviewTimeSelection.getEndTime().substring(0, 2).toString());
						interviewTimeSelectionTO.setHenceMins(interviewTimeSelection.getEndTime().substring(3, 5).toString());
					}
					interviewTimeSelectionTO.setMaxCandidates(String.valueOf(interviewTimeSelection.getMaxSeats()));
					totalCount=totalCount+Integer.valueOf(interviewTimeSelection.getMaxSeats());
					timeList.add(interviewTimeSelectionTO);
				}
			}
			interviewSelectionScheduleForm.setTotalCount(totalCount);
			interviewSelectionScheduleForm.setTimeList(timeList);
			if(interviewSelectionScheduleForm.getTimeList().size()>1){
				interviewSelectionScheduleForm.setTimeFlag(true);
			}
			//end time selection table
			
			// Strart /* code commented by chandra
			
			/*//start venue selection table
			Set<InterviewVenueSelection> venueSet=interviewSelectionSchedule.getInterviewVenueSelections();
			List<InterviewVenuSelectionTO> venueList=new ArrayList<InterviewVenuSelectionTO>();
			InterviewVenuSelectionTO interviewVenuSelectionTO=null;
			Iterator<InterviewVenueSelection> iterator1=venueSet.iterator();
			while (iterator1.hasNext()) {
				InterviewVenueSelection interviewVenueSelection = (InterviewVenueSelection) iterator1.next();
				if(interviewVenueSelection.getIsActive()){
					interviewVenuSelectionTO=new InterviewVenuSelectionTO();
					interviewVenuSelectionTO.setId(interviewVenueSelection.getId());
					interviewVenuSelectionTO.setVenueName("Venue"+(venueList.size()+1));
					interviewVenuSelectionTO.setVenueMap(venueMap);
					interviewVenuSelectionTO.setVenueId(interviewVenueSelection.getExamCenter().getId());
					venueList.add(interviewVenuSelectionTO);
				}
			}
			interviewSelectionScheduleForm.setVenueList(venueList);
			if(interviewSelectionScheduleForm.getVenueList().size()>1){
				interviewSelectionScheduleForm.setVenueFlag(true);
			}
			//end venue selection table
			 */			
			// End */ code commented by chandra
			
		//end
		// /* code added by chandra 	
			interviewSelectionScheduleForm.setVenueMap(venueMap);
			if(interviewSelectionSchedule.getExamCenter()!=null ){
				if(interviewSelectionSchedule.getExamCenter().getId()!=0){
					interviewSelectionScheduleForm.setVenueId(interviewSelectionSchedule.getExamCenter().getId());
				}
			}
			boolean cardgenereted=transactions.checkingIsCardgenerated(interviewSelectionScheduleForm);
			if(cardgenereted){
				interviewSelectionScheduleForm.setIsCardgenetated(true);
			}
			
		// */ code added by chandra	
			
	}
	
	public InterviewSelectionSchedule convertFormToBoForUpdate(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm,
			InterviewSelectionSchedule interviewSelectionSchedule) throws Exception{
		//set program
		Program program=new Program();
		program.setId(Integer.parseInt(interviewSelectionScheduleForm.getProgramId()));
		interviewSelectionSchedule.setProgramId(program);
		interviewSelectionSchedule.setAcademicYear(interviewSelectionScheduleForm.getAcademicYear());
		
		if(!(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewSelectionSchedule.getSelectionProcessDate()), "dd-MMM-yyyy", "dd/MM/yyyy")).equals(interviewSelectionScheduleForm.getSelectionProcessDate())){
				transactions.updateInterviewSchedule(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewSelectionSchedule.getSelectionProcessDate()), "dd-MMM-yyyy", "dd/MM/yyyy"),interviewSelectionSchedule.getId(), interviewSelectionScheduleForm);
		}
		interviewSelectionSchedule.setSelectionProcessDate(CommonUtil.ConvertStringToSQLDate(interviewSelectionScheduleForm.getSelectionProcessDate()));
		interviewSelectionSchedule.setCutOffDate(CommonUtil.ConvertStringToSQLDate(interviewSelectionScheduleForm.getCutOffDate()));
		interviewSelectionSchedule.setMaxNumOfSeatsOffline(Integer.parseInt(interviewSelectionScheduleForm.getMaxNumOfSeatsOffline()));
		interviewSelectionSchedule.setMaxNumOfSeatsOnline(Integer.parseInt(interviewSelectionScheduleForm.getMaxNumOfSeatsOnline()));
		interviewSelectionSchedule.setModifiedBy(interviewSelectionScheduleForm.getUserId());
		interviewSelectionSchedule.setLastModifiedDate(new Date());
		
		//code added by chandra
		ExamCenter examCenter=new ExamCenter();
		examCenter.setId(interviewSelectionScheduleForm.getVenueId());
		interviewSelectionSchedule.setExamCenter(examCenter);
		
		//code commented by chandra
		
		// /* code commented by chandra
		/*//start venue selection
		Map<Integer,InterviewVenueSelection> venueMap=convertVenueSetToVenueMap(interviewSelectionSchedule.getInterviewVenueSelections());
		Set<InterviewVenueSelection> venueSet= new HashSet<InterviewVenueSelection>();
		Iterator<InterviewVenuSelectionTO> iterator=interviewSelectionScheduleForm.getVenueList().iterator();
		while (iterator.hasNext()) {
			InterviewVenuSelectionTO interviewVenuSelectionTO = (InterviewVenuSelectionTO) iterator.next();
			if(venueMap.containsKey(interviewVenuSelectionTO.getId())){
				// which are alredy in venue table as active
				InterviewVenueSelection interviewVenueSelection=venueMap.get(interviewVenuSelectionTO.getId());
				ExamCenter examCenter=new ExamCenter();
				examCenter.setId(interviewVenuSelectionTO.getVenueId());
				interviewVenueSelection.setExamCenter(examCenter);
				interviewVenueSelection.setModifiedBy(interviewSelectionScheduleForm.getUserId());
				interviewVenueSelection.setLastModifiedDate(new Date());
				venueSet.add(interviewVenueSelection);
				venueMap.remove(interviewVenuSelectionTO.getId());
			}else{
				//add new venues
				InterviewVenueSelection interviewVenueSelection=new InterviewVenueSelection();
				ExamCenter examCenter=new ExamCenter();
				examCenter.setId(interviewVenuSelectionTO.getVenueId());
				interviewVenueSelection.setExamCenter(examCenter);
				interviewVenueSelection.setCreatedBy(interviewSelectionScheduleForm.getUserId());
				interviewVenueSelection.setModifiedBy(interviewSelectionScheduleForm.getUserId());
				interviewVenueSelection.setCreatedDate(new Date());
				interviewVenueSelection.setLastModifiedDate(new Date());
				interviewVenueSelection.setIsActive(true);
				interviewVenueSelection.setInterviewSelectionSchedule(interviewSelectionSchedule);
				venueSet.add(interviewVenueSelection);
			}
			
		}
		// if still venueMap have venues make this venue as isactive false
		for (Map.Entry<Integer, InterviewVenueSelection> entry : venueMap.entrySet()){
		    InterviewVenueSelection interviewVenueSelection=entry.getValue();
		    interviewVenueSelection.setModifiedBy(interviewSelectionScheduleForm.getUserId());
			interviewVenueSelection.setLastModifiedDate(new Date());
			interviewVenueSelection.setIsActive(false);
			venueSet.add(interviewVenueSelection);
		}
		interviewSelectionSchedule.setInterviewVenueSelections(venueSet);
		//end venue selection
*/		
		// */ code commented by chandra	
		
		//start time selection table
		Map<Integer,InterviewTimeSelection> timeMap=convertTimeSetToTimeMap(interviewSelectionSchedule.getInterviewTimeSelections());
		Set<InterviewTimeSelection> timeSet=new HashSet<InterviewTimeSelection>();
		Iterator<InterviewTimeSelectionTO> iterator2=interviewSelectionScheduleForm.getTimeList().iterator();
		StringBuilder stringBuilder=null;
		StringBuilder stringBuilder1=null;
		while (iterator2.hasNext()) {
			InterviewTimeSelectionTO interviewTimeSelectionTO = (InterviewTimeSelectionTO) iterator2.next();
			if(interviewTimeSelectionTO.getId()>0 && timeMap.containsKey(interviewTimeSelectionTO.getId())){
				InterviewTimeSelection interviewTimeSelection=timeMap.get(interviewTimeSelectionTO.getId());
				stringBuilder=new StringBuilder();
				stringBuilder1=new StringBuilder();
				stringBuilder.append(interviewTimeSelectionTO.getHours()+":"+interviewTimeSelectionTO.getMins()+":00");
				interviewTimeSelection.setTime(stringBuilder.toString());
				stringBuilder1.append(interviewTimeSelectionTO.getHenceHours()+":"+interviewTimeSelectionTO.getHenceMins()+":00");
				interviewTimeSelection.setEndTime(stringBuilder1.toString());
				interviewTimeSelection.setMaxSeats(Integer.parseInt(interviewTimeSelectionTO.getMaxCandidates()));
				interviewTimeSelection.setModifiedBy(interviewSelectionScheduleForm.getUserId());
				interviewTimeSelection.setLastModifiedDate(new Date());
				timeSet.add(interviewTimeSelection);
				timeMap.remove(interviewTimeSelectionTO.getId());
			}else{
				InterviewTimeSelection interviewTimeSelection=new InterviewTimeSelection();
				stringBuilder=new StringBuilder();
				stringBuilder1=new StringBuilder();
				stringBuilder.append(interviewTimeSelectionTO.getHours()+":"+interviewTimeSelectionTO.getMins()+":00");
				interviewTimeSelection.setTime(stringBuilder.toString());
				stringBuilder1.append(interviewTimeSelectionTO.getHenceHours()+":"+interviewTimeSelectionTO.getHenceMins()+":00");
				interviewTimeSelection.setEndTime(stringBuilder1.toString());
				interviewTimeSelection.setMaxSeats(Integer.parseInt(interviewTimeSelectionTO.getMaxCandidates()));
				interviewTimeSelection.setCreatedBy(interviewSelectionScheduleForm.getUserId());
				interviewTimeSelection.setModifiedBy(interviewSelectionScheduleForm.getUserId());
				interviewTimeSelection.setCreatedDate(new Date());
				interviewTimeSelection.setLastModifiedDate(new Date());
				interviewTimeSelection.setIsActive(true);
				interviewTimeSelection.setInterviewSelectionSchedule(interviewSelectionSchedule);
				timeSet.add(interviewTimeSelection);
			}
		}
		// if still timeMap have time make this time as isactive false
		for (Map.Entry<Integer, InterviewTimeSelection> entry : timeMap.entrySet()){
			InterviewTimeSelection interviewTimeSelection=entry.getValue();
			interviewTimeSelection.setModifiedBy(interviewSelectionScheduleForm.getUserId());
			interviewTimeSelection.setLastModifiedDate(new Date());
			interviewTimeSelection.setIsActive(false);
			timeSet.add(interviewTimeSelection);
		}
		interviewSelectionSchedule.setInterviewTimeSelections(timeSet);
		//end time selection table
		return interviewSelectionSchedule;
	}
	
	private Map<Integer, InterviewTimeSelection> convertTimeSetToTimeMap(
			Set<InterviewTimeSelection> interviewTimeSelections) throws Exception{
		Map<Integer, InterviewTimeSelection> map=new HashMap<Integer, InterviewTimeSelection>();
		Iterator<InterviewTimeSelection> iterator=interviewTimeSelections.iterator();
		while (iterator.hasNext()) {
			InterviewTimeSelection interviewTimeSelection = (InterviewTimeSelection) iterator.next();
			map.put(interviewTimeSelection.getId(), interviewTimeSelection);
		}
		return map;
	}
	
	private Map<Integer, InterviewVenueSelection> convertVenueSetToVenueMap(
			Set<InterviewVenueSelection> venueSet) throws Exception{
		 Map<Integer, InterviewVenueSelection> map=new HashMap<Integer, InterviewVenueSelection>();
		 Iterator<InterviewVenueSelection> iterator=venueSet.iterator();
		 while (iterator.hasNext()) {
			InterviewVenueSelection interviewVenueSelection = (InterviewVenueSelection) iterator.next();
			map.put(interviewVenueSelection.getId(), interviewVenueSelection);
		}
		return map;
	}
}
