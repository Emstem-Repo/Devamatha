package com.kp.cms.transactions.attandance;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.LeaveType;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;

/**
 * An interface for the approve leave transaction.
 */

public interface IApproveLeaveTransaction {

	/**
	 * Returns the list of attendance object by class schemewise id between start date and end date.
	 * @param approveLeaveForm - Represents ApproveLeaveForm object. 
	 * @return list of attendance objects.
	 * @throws ApplicationException
	 */
	public List<Attendance>  isAttendenceTaken(ApproveLeaveForm approveLeaveForm)
			throws ApplicationException;
	
	/**
	 * returns true , if the leave is already approved for entered candidates , and selection date and periods.
	 * @param approveLeaveForm - Represents the ApproveLeaveForm object.
	 * @return
	 * @throws ApplicationException
	 */
	public boolean isLeaveAlreadyApproved(ApproveLeaveForm approveLeaveForm,String startTime,String endTime) throws ApplicationException;

	/**
	 * Get the list of students for the start day with in the set of periods. 
	 * @param startDay - Represents the start day.
	 * @param periods  - Represents the set of periods.
	 * @param approveLeaveForm - Represents the ApproveLeaveForm objects
	 * @return list of AttendanceStudent objects.
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendenceList(String startDay,
			Set<Integer> periods, ApproveLeaveForm approveLeaveForm)
			throws ApplicationException;

	/**
	 * get attendance student list between from date and todate
	 * @param dayAfterStart -
	 * @param dayAfterEnd - 
	 * @param periods  - Represents the period set.
	 * @param approveLeaveForm - Represents the ApproveLeaveForm object.
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendenceListBetweenDays(
			String dayAfterStart, String dayAfterEnd, Set<Integer> periods,
			ApproveLeaveForm approveLeaveForm) throws ApplicationException;



	/**
	 * Persists the Approved leave.
	 * @param approveLeaveToMap - Represents Map object containing List of ApproveLeave Transfer object.
	 * @param approveLeaveForm  - Represents the ApproveLeaveForm object.
	 * @param studentLeave - Represents the StudentLeaveBO object.
	 * @param mode - represents the mode of operation.
	 * @throws ApplicationException
	 */
	public void updateApproveLeave(
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap,
			ApproveLeaveForm approveLeaveForm, StudentLeave studentLeave,
			String mode) throws ApplicationException;

	/**
	 * Get the List of leave types from the database.
	 * @return List of leave type objects.
	 * @throws ApplicationException
	 */
	public List<LeaveType> getLeaveType() throws ApplicationException;

	/**
	 * Get the approved leaves between from date and todate.
	 * @param fromDate - Represents the from date.
	 * @param toDate  - Represents the toDate 
	 * @return List of student leave objects.
	 * @throws Exception
	 */
	public List<StudentLeave> getApprovedLeaves(Date fromDate, Date toDate)
			throws Exception;

	/**
	 * Deletes the leave approval from the database.
	 * @param id - Represents the leave id.
	 * @param approveLeaveToMap 
	 * @param userId 
	 * @throws Exception
	 */
	public void deleteLeaveApproval(int id, Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap, String userId) throws Exception;

	/**
	 * get the leave approval based on id.
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public StudentLeave getLeaveApproval(int id) throws Exception;

	/**
	 * Get the student id by course id
	 * @param classSehemeId
	 * @param isRegNo
	 * @param num
	 * @return
	 * @throws ApplicationException
	 */
	public Integer getStudentIdByCourseAnd(int classSehemeId, boolean isRegNo,
			String num) throws ApplicationException;
	
	/**
	 * This method will list the StudentLeave for a particular day.
	 * @param date
	 * @param classSehemewiseId
	 * @return
	 * @throws Exception
	 */
	public List<StudentLeave> getApprovedLeavesForDayAndClass(Date date,Set<Integer> classSet) throws Exception;

	/**
	 * Returns true,if attendance is taken for the given register no, false otherwise.
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public boolean isCocurricularLeave(ApproveLeaveForm approveLeaveForm)
	throws ApplicationException ;
	
	/**
	 * returns true , if the leave is already approved for entered candidates , and selection date and periods.
	 * @param approveLeaveForm - Represents the ApproveLeaveForm object.
	 * @return
	 * @throws ApplicationException
	 */
	public boolean isLeaveAlreadyApproved(ApproveLeaveForm approveLeaveForm) throws ApplicationException;

}
