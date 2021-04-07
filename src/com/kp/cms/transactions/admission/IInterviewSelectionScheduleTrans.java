package com.kp.cms.transactions.admission;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewSelectionScheduleForm;

public interface IInterviewSelectionScheduleTrans {

	boolean checkDuplicate(String query)throws Exception;

	boolean add(InterviewSelectionSchedule interviewSelectionSchedule)throws Exception;

	List<InterviewSelectionSchedule> getAllRecords(String academicYear)throws Exception;

	boolean delete(int id, String userId)throws Exception;

	Map<Integer, String> getprogramMap()throws Exception;

	Map<Integer, String> getVenues(int parseInt)throws Exception;

	InterviewSelectionSchedule getRecord(int id)throws Exception;

	boolean update(InterviewSelectionSchedule interviewSelectionSchedule)throws Exception;

	List<InterviewCard> getGeneratedCardDetails(int id, String selectionProcessDate)throws Exception;

	void updateInterviewCard(String selectionProcessDate,InterviewSelectionScheduleForm interviewSelectionScheduleForm ) throws ApplicationException;

	void updateInterviewSchedule(String selectionProcessDate, int id, InterviewSelectionScheduleForm interviewSelectionScheduleForm) throws ApplicationException;
	public boolean checkingIsCardgenerated(InterviewSelectionScheduleForm interviewSelectionScheduleForm )throws Exception;
	public Map<Integer,Integer> getTotalNumberOfStudentsAppliedForAdmitcard(InterviewSelectionScheduleForm interviewSelectionScheduleForm )throws Exception;
	

}
