package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.InterviewSelectionScheduleTO;
import com.kp.cms.to.admission.InterviewTimeSelectionTO;
import com.kp.cms.to.admission.InterviewVenuSelectionTO;

public class InterviewSelectionScheduleForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String selectionProcessDate;
	private String cutOffDate;
	private String maxNumOfSeatsOnline;
	private String maxNumOfSeatsOffline;
	private Map<Integer,String> programMap;
	private List<InterviewSelectionScheduleTO> list;
	private int id;
	private List<InterviewVenuSelectionTO> venueList;
	private List<InterviewTimeSelectionTO> timeList;
	private boolean timeFlag;
	private boolean venueFlag;
	private String venueFocus;
	private String timeFocus;
	private String flag;
	private int totalCount;
	private int timeListSize;
	private String dateChangedFlag;
	private String timeChangedFlag;
	private List<InterviewCard> interviewCard;
	private Map<Integer,String> venueMap;
	private int venueId;
	private Boolean isCardgenetated;
	
	
	
	public int getTimeListSize() {
		return timeListSize;
	}
	public void setTimeListSize(int timeListSize) {
		this.timeListSize = timeListSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getVenueFocus() {
		return venueFocus;
	}
	public void setVenueFocus(String venueFocus) {
		this.venueFocus = venueFocus;
	}
	public String getTimeFocus() {
		return timeFocus;
	}
	public void setTimeFocus(String timeFocus) {
		this.timeFocus = timeFocus;
	}
	public boolean isTimeFlag() {
		return timeFlag;
	}
	public void setTimeFlag(boolean timeFlag) {
		this.timeFlag = timeFlag;
	}
	public boolean isVenueFlag() {
		return venueFlag;
	}
	public void setVenueFlag(boolean venueFlag) {
		this.venueFlag = venueFlag;
	}
	public List<InterviewVenuSelectionTO> getVenueList() {
		return venueList;
	}
	public void setVenueList(List<InterviewVenuSelectionTO> venueList) {
		this.venueList = venueList;
	}
	public List<InterviewTimeSelectionTO> getTimeList() {
		return timeList;
	}
	public void setTimeList(List<InterviewTimeSelectionTO> timeList) {
		this.timeList = timeList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<InterviewSelectionScheduleTO> getList() {
		return list;
	}
	public void setList(List<InterviewSelectionScheduleTO> list) {
		this.list = list;
	}
	public Map<Integer, String> getProgramMap() {
		return programMap;
	}
	public void setProgramMap(Map<Integer, String> programMap) {
		this.programMap = programMap;
	}
	public String getSelectionProcessDate() {
		return selectionProcessDate;
	}
	public void setSelectionProcessDate(String selectionProcessDate) {
		this.selectionProcessDate = selectionProcessDate;
	}
	public String getCutOffDate() {
		return cutOffDate;
	}
	public void setCutOffDate(String cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	public String getMaxNumOfSeatsOnline() {
		return maxNumOfSeatsOnline;
	}
	public void setMaxNumOfSeatsOnline(String maxNumOfSeatsOnline) {
		this.maxNumOfSeatsOnline = maxNumOfSeatsOnline;
	}
	public String getMaxNumOfSeatsOffline() {
		return maxNumOfSeatsOffline;
	}
	public void setMaxNumOfSeatsOffline(String maxNumOfSeatsOffline) {
		this.maxNumOfSeatsOffline = maxNumOfSeatsOffline;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public List<InterviewCard> getInterviewCard() {
		return interviewCard;
	}
	public void setInterviewCard(List<InterviewCard> interviewCard) {
		this.interviewCard = interviewCard;
	}
	public String getDateChangedFlag() {
		return dateChangedFlag;
	}
	public void setDateChangedFlag(String dateChangedFlag) {
		this.dateChangedFlag = dateChangedFlag;
	}
	public String getTimeChangedFlag() {
		return timeChangedFlag;
	}
	public void setTimeChangedFlag(String timeChangedFlag) {
		this.timeChangedFlag = timeChangedFlag;
	}
	public Map<Integer, String> getVenueMap() {
		return venueMap;
	}
	public void setVenueMap(Map<Integer, String> venueMap) {
		this.venueMap = venueMap;
	}
	public int getVenueId() {
		return venueId;
	}
	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}
	public Boolean getIsCardgenetated() {
		return isCardgenetated;
	}
	public void setIsCardgenetated(Boolean isCardgenetated) {
		this.isCardgenetated = isCardgenetated;
	}
	
	
	
	
}
