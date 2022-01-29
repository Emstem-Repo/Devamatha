package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.chart.model.attribute.Size;

import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.InterviewSelectionScheduleForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.helpers.admission.InterviewSelectionScheduleHelper;
import com.kp.cms.to.admission.InterviewSelectionScheduleTO;
import com.kp.cms.to.admission.InterviewTimeSelectionTO;
import com.kp.cms.to.admission.InterviewVenuSelectionTO;
import com.kp.cms.to.examallotment.TeacherwiseExemptionTo;
import com.kp.cms.transactions.admission.IInterviewSelectionScheduleTrans;
import com.kp.cms.transactionsimpl.admission.InterviewSelectionScheduleTransImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;

public class InterviewSelectionScheduleHandler {
	IInterviewSelectionScheduleTrans interviewSelectionScheduleTrans=InterviewSelectionScheduleTransImpl.getInstance();
	InterviewSelectionScheduleHelper interviewSelectionScheduleHelper=InterviewSelectionScheduleHelper.getInstance();
	public static volatile InterviewSelectionScheduleHandler interviewSelectionScheduleHandler=null;
	//private constructor
	private InterviewSelectionScheduleHandler(){
		
	}
	//singleton object
	public static InterviewSelectionScheduleHandler getInstance(){
		if(interviewSelectionScheduleHandler==null){
			interviewSelectionScheduleHandler=new InterviewSelectionScheduleHandler();
			return interviewSelectionScheduleHandler;
		}
		return interviewSelectionScheduleHandler;
	}
	/**
	 * check duplicate record
	 * @param interviewSelectionScheduleForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicate(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		String query=interviewSelectionScheduleHelper.queryForDuplicateCheck(interviewSelectionScheduleForm);
		boolean flag=interviewSelectionScheduleTrans.checkDuplicate(query);
		return flag;
	}
	/**
	 * add new record
	 * @param interviewSelectionScheduleForm
	 * @return
	 * @throws Exception
	 */
	public boolean addInterviewSelectionSchedule(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		InterviewSelectionSchedule interviewSelectionSchedule=interviewSelectionScheduleHelper.convertFormToBo(interviewSelectionScheduleForm);
		boolean flag=interviewSelectionScheduleTrans.add(interviewSelectionSchedule);
		return flag;
	}
	/**
	 * get all the records
	 * @param interviewSelectionScheduleForm
	 * @throws Exception
	 */
	public void getInterviewSelectionScheduleRecords(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		List<InterviewSelectionSchedule> list=interviewSelectionScheduleTrans.getAllRecords(interviewSelectionScheduleForm.getAcademicYear());
		if(list!=null && !list.isEmpty()){
			List<InterviewSelectionScheduleTO> list1=interviewSelectionScheduleHelper.convertBosToTos(list,interviewSelectionScheduleForm);
			interviewSelectionScheduleForm.setList(list1);
		}
	}
	public boolean delete(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		boolean flag=interviewSelectionScheduleTrans.delete(interviewSelectionScheduleForm.getId(),interviewSelectionScheduleForm.getUserId());
		return flag;
	}
	public void getProgramMap(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		Map<Integer,String> map=interviewSelectionScheduleTrans.getprogramMap();
		interviewSelectionScheduleForm.setProgramMap(CommonUtil.sortMapByValue(map));
	}
	public void venues(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		//programMap
		Map<Integer,String> map=interviewSelectionScheduleTrans.getprogramMap();
		interviewSelectionScheduleForm.setProgramMap(CommonUtil.sortMapByValue(map));
		//end programMap
		//get the venues list
		Map<Integer,String> venueMap=interviewSelectionScheduleTrans.getVenues(Integer.parseInt(interviewSelectionScheduleForm.getProgramId()));
		List<InterviewVenuSelectionTO> venueList=new ArrayList<InterviewVenuSelectionTO>();
		InterviewVenuSelectionTO interviewVenuSelectionTO=new InterviewVenuSelectionTO();
		interviewVenuSelectionTO.setVenueMap(venueMap);
		interviewVenuSelectionTO.setVenueName("Venue"+(venueList.size()+1));
		venueList.add(interviewVenuSelectionTO);
		interviewSelectionScheduleForm.setVenueList(venueList);
		//code added by chandra
		interviewSelectionScheduleForm.setVenueMap(venueMap);
		//end the venues list
		//start time selection list
		if(interviewSelectionScheduleForm.getFlag()!=null && interviewSelectionScheduleForm.getFlag().equalsIgnoreCase("add")){
			interviewSelectionScheduleForm.setTimeList(null);
			Map<String,String> hoursMap=AvailableSeatsHandler.getInstance().getHoursMap();
			Map<String,String> minsMap=AvailableSeatsHandler.getInstance().getMinMap();
			List<InterviewTimeSelectionTO> timeList=new ArrayList<InterviewTimeSelectionTO>();
			InterviewTimeSelectionTO interviewTimeSelectionTO=new InterviewTimeSelectionTO();
			interviewTimeSelectionTO.setHoursMap(hoursMap);
			interviewTimeSelectionTO.setMinsMap(minsMap);
			interviewTimeSelectionTO.setTimeTemplate("From Time");
			interviewTimeSelectionTO.setTimeHenceTemplate("To Time");
			interviewTimeSelectionTO.setCandidatesTemplate("Max No.of Candidates");
			timeList.add(interviewTimeSelectionTO);
			interviewSelectionScheduleForm.setTimeList(timeList);
		}
		//end time selection list
	}
	public void addMoreVenues(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		List<InterviewVenuSelectionTO> list=interviewSelectionScheduleForm.getVenueList();
		Map<Integer,String> venueMap=interviewSelectionScheduleTrans.getVenues(Integer.parseInt(interviewSelectionScheduleForm.getProgramId()));
		InterviewVenuSelectionTO interviewVenuSelectionTO=new InterviewVenuSelectionTO();
		interviewVenuSelectionTO.setVenueMap(venueMap);
		interviewVenuSelectionTO.setVenueName("Venue"+(list.size()+1));
		list.add(interviewVenuSelectionTO);
		interviewSelectionScheduleForm.setVenueList(list);
		interviewSelectionScheduleForm.setVenueFlag(true);
		interviewSelectionScheduleForm.setVenueFocus("venue_"+(list.size()-1));
	}
	public void addMoreTimes(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		List<InterviewTimeSelectionTO> timeList=interviewSelectionScheduleForm.getTimeList();
		Map<String,String> hoursMap=AvailableSeatsHandler.getInstance().getHoursMap();
		Map<String,String> minsMap=AvailableSeatsHandler.getInstance().getMinMap();
		InterviewTimeSelectionTO interviewTimeSelectionTO=new InterviewTimeSelectionTO();
		interviewTimeSelectionTO.setHoursMap(hoursMap);
		interviewTimeSelectionTO.setMinsMap(minsMap);
		interviewTimeSelectionTO.setTimeTemplate("From Time");
		interviewTimeSelectionTO.setTimeHenceTemplate("To Time");
		interviewTimeSelectionTO.setCandidatesTemplate("Max No.of Candidates");
		timeList.add(interviewTimeSelectionTO);
		interviewSelectionScheduleForm.setTimeList(timeList);
		interviewSelectionScheduleForm.setTimeFlag(true);
		interviewSelectionScheduleForm.setVenueFocus("time_"+(timeList.size()-1));
		countTheTotalCandidate(interviewSelectionScheduleForm);
	}
	private void countTheTotalCandidate(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		int totalCount=0;
		List<InterviewTimeSelectionTO> list=interviewSelectionScheduleForm.getTimeList();
		for (InterviewTimeSelectionTO interviewTimeSelectionTO : list) {
			if(interviewTimeSelectionTO.getMaxCandidates()!=null && !interviewTimeSelectionTO.getMaxCandidates().isEmpty()){
				totalCount=totalCount+Integer.valueOf(interviewTimeSelectionTO.getMaxCandidates());
			}
		}
		interviewSelectionScheduleForm.setTotalCount(totalCount);
	}
	public void removeMoreVenues(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		List<InterviewVenuSelectionTO> list=interviewSelectionScheduleForm.getVenueList();
		if(list.size()>1){
			list.remove(list.size()-1);
			interviewSelectionScheduleForm.setVenueFocus("venue_"+(list.size()-1));
		}
		if(list.size()==1){
			interviewSelectionScheduleForm.setVenueFlag(false);
		}
	}
	public void removeMoreTimes(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		List<InterviewTimeSelectionTO> list=interviewSelectionScheduleForm.getTimeList();
		if(list.size()>1){
			int totalCount=interviewSelectionScheduleForm.getTotalCount();
			if(list.get(list.size()-1).getMaxCandidates()!=null && !list.get(list.size()-1).getMaxCandidates().isEmpty()){
				interviewSelectionScheduleForm.setTotalCount(totalCount-(Integer.parseInt(list.get(list.size()-1).getMaxCandidates())));
			}
			list.remove(list.size()-1);
			interviewSelectionScheduleForm.setTimeFocus("time_"+(list.size()-1));
		}
		if(list.size()==1){
			interviewSelectionScheduleForm.setTimeFlag(false);
		}
		countTheTotalCandidate(interviewSelectionScheduleForm);
	}
	public String validateTheLists(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		String flag=null;
		int maxNoOfCandidates=0;
		// /* code commented by chandra
		/*Iterator<InterviewVenuSelectionTO> iterator=interviewSelectionScheduleForm.getVenueList().iterator();
		while (iterator.hasNext()) {
			InterviewVenuSelectionTO interviewVenuSelectionTO = (InterviewVenuSelectionTO) iterator.next();
			if(interviewVenuSelectionTO.getVenueId()<=0){
				flag="venue";
				return flag;
			}
		}*/
		//  */code commented by chandra
		
		// /* code added by chandra
		if(interviewSelectionScheduleForm.getVenueId()<=0){
			flag="venue";
			return flag;
		}
		// */code added by chandra
		Iterator<InterviewTimeSelectionTO> iterator2=interviewSelectionScheduleForm.getTimeList().iterator();
		while (iterator2.hasNext()) {
			InterviewTimeSelectionTO interviewTimeSelectionTO = (InterviewTimeSelectionTO) iterator2.next();
			if(interviewTimeSelectionTO.getHours()==null || interviewTimeSelectionTO.getHours().isEmpty()
				|| interviewTimeSelectionTO.getMins()==null || interviewTimeSelectionTO.getMins().isEmpty()
				|| interviewTimeSelectionTO.getMaxCandidates()==null || interviewTimeSelectionTO.getMaxCandidates().isEmpty()
				|| interviewTimeSelectionTO.getHenceHours()==null || interviewTimeSelectionTO.getHenceHours().isEmpty()
				|| interviewTimeSelectionTO.getHenceMins()==null || interviewTimeSelectionTO.getHenceMins().isEmpty()){
				flag="time";
				return flag;
			}else{
				if(Integer.parseInt(interviewTimeSelectionTO.getHours())>Integer.parseInt(interviewTimeSelectionTO.getHenceHours())){
					flag="bigTime";
					//return flag;
				}else if(Integer.parseInt(interviewTimeSelectionTO.getHours())==Integer.parseInt(interviewTimeSelectionTO.getHenceHours())&& 
						Integer.parseInt(interviewTimeSelectionTO.getMins())>=Integer.parseInt(interviewTimeSelectionTO.getHenceMins())){
					flag="bigTime";
					//return flag;
				}
			}
			maxNoOfCandidates=maxNoOfCandidates+Integer.parseInt(interviewTimeSelectionTO.getMaxCandidates());
			interviewSelectionScheduleForm.setTotalCount(maxNoOfCandidates);
		}
		if(maxNoOfCandidates!=(Integer.parseInt(interviewSelectionScheduleForm.getMaxNumOfSeatsOffline())+Integer.parseInt(interviewSelectionScheduleForm.getMaxNumOfSeatsOnline()))){
			flag="candidates";
			return flag;
		}
		return flag;
	}
	public void edit(InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		InterviewSelectionSchedule interviewSelectionSchedule=interviewSelectionScheduleTrans.getRecord(interviewSelectionScheduleForm.getId());
		interviewSelectionScheduleHelper.convertBosToForm(interviewSelectionScheduleForm,interviewSelectionSchedule);
	}
	public boolean update(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws Exception{
		InterviewSelectionSchedule interviewSelectionSchedule=interviewSelectionScheduleTrans.getRecord(interviewSelectionScheduleForm.getId());
		InterviewSelectionSchedule interviewSelectionSchedule2=interviewSelectionScheduleHelper.convertFormToBoForUpdate(interviewSelectionScheduleForm,interviewSelectionSchedule);
		boolean flag=interviewSelectionScheduleTrans.update(interviewSelectionSchedule2);
		return flag;
	}
	public List<InterviewSelectionScheduleTO> getSelectionScheduleRecords(
			InterviewSelectionScheduleForm interviewSelectionScheduleForm,
			HttpServletRequest request) throws Exception{
		List<InterviewSelectionSchedule> list=interviewSelectionScheduleTrans.getAllRecords(interviewSelectionScheduleForm.getAcademicYear());
		List<InterviewSelectionScheduleTO> list1=null;
		if(list!=null && !list.isEmpty()){
			list1=interviewSelectionScheduleHelper.convertBosToTos(list,interviewSelectionScheduleForm);
		}
		return list1;
	}
	
}
	
