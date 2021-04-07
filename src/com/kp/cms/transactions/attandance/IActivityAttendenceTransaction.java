package com.kp.cms.transactions.attandance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;

/**
 * An interface for Activity attendance transaction.
 */

public interface IActivityAttendenceTransaction {

	/**
	 * Get the student list by class schemewiseid.
	 * 
	 * @param classSchemeWiseId
	 *            - Represents the ClassSchemeWiseId
	 * @return - List of students.
	 * @throws ApplicationException
	 */
	public List<Student> getStudentListByClassSchemeWiseId(int classSchemeWiseId)
			throws ApplicationException;

	/**
	 * Get the period by periodId
	 * 
	 * @param periodId
	 *            - Represents the period Id
	 * @return - Period object.
	 * @throws ApplicationException
	 */
	public Period getPeriod(int periodId) throws ApplicationException;

	/**
	 * Get the period list for the start date.
	 * 
	 * @param classSchemeWiseId
	 *            - Represents the classSchemeWiseId
	 * @param startTime
	 *            - Represents the start time of the from period.
	 * @param endTime
	 *            - represents the start time of the end period
	 * @return - List of periods where starttime between starttime and end time.
	 * @throws ApplicationException
	 */
	// used to get the period list when start date == end date
	public List<Period> getPeriodListForStartDate(int classSchemeWiseId,
			String startTime, String endTime) throws ApplicationException;
	
	/**
	 * @param classSchemeWiseId
	 * @param startTime
	 * @return
	 * @throws ApplicationException
	 */
	public List<Period> getPeriodListForStartDateSelectedDateMoreThanOneDay(int classSchemeWiseId,
			String startTime) throws ApplicationException;

	/**
	 * get the period list for the end date.
	 * 
	 * @param classSchemeWiseId
	 *            - Represents the classSchemeWiseId
	 * @param startTime
	 *            - Represents the start time of the from period.
	 * @param endTime
	 *            - represents the start time of the end period
	 * @return - List of periods where endtime between 00:00:00 and end time.
	 * @throws ApplicationException
	 */
	// used to get the period list when end date > start date
	public List<Period> getPeriodListForEndDate(Integer classSchemeWiseId,
			String endTime) throws ApplicationException;

	/**
	 * Get all the period in a class
	 * @param classSchemeWiseId - represents the class schemewise id
	 * @return List of periods.
	 * @throws ApplicationException
	 */
	public List<Period> getPeriodList(Integer classSchemeWiseId)
			throws ApplicationException;

	/**
	 * returns true if program is set for register no, false otherwise
	 * 
	 * @param classSchemewiseId
	 *            -Represents the classSchemeWiseId
	 * @return
	 * @throws ApplicationException
	 */
	public Boolean checkIsRegisterNo(int classSchemewiseId)
			throws ApplicationException;


	
	/**
	 * Get the activity attendance list for modify.
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws ApplicationException
	 */
	public List<Attendance> initModifyActivityAttendence(String fromDate, String toDate) throws ApplicationException;
	
	/**
	 * Get the attendance object by attendance id.
	 * @param attendanceId
	 * @return
	 * @throws ApplicationException
	 */
	public Attendance getActivityAttendanceById(int attendanceId) throws ApplicationException;



	


	/**
	 * returns true if the isCoCurricularLeaveApproved for given date and period, false otherwise.
	 * @param activityAttendenceForm
	 * @return
	 * @throws ApplicationException
	 */
	public boolean isCoCurricularLeaveApproved(ApproveLeaveForm activityAttendenceForm,String startTime,String endTime) throws ApplicationException;

	/**
	 * updates updateCoCcurricularLeave
	 * @param approveLeaveToMap
	 * @param activityAttendanceForm
	 * @param studentLeave
	 * @param mode
	 * @throws ApplicationException
	 */
	public void updateCoCcurricularLeave(
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap,
			ApproveLeaveForm activityAttendanceForm,
			StuCocurrLeave studentLeave, String mode)throws ApplicationException;

	/**
	 * get the CoCcurricularLeave by date
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws ApplicationException
	 */
	public List<StuCocurrLeave> getCoCcurricularLeaves(Date fromDate, Date toDate)throws ApplicationException;

	/**
	 *  get the CoCcurricularLeave by  CoCcurricularLeave Id
	 * @param leaveId
	 * @return
	 * @throws ApplicationException
	 */
	public StuCocurrLeave getCoCurricularLeave(int leaveId) throws ApplicationException;

	/**
	 * deletes  CoCcurricularLeave 
	 * @param approveLeaveForm
	 * @param approveLeaveToMap
	 * @throws ApplicationException
	 */
	public void deleteCoCcurricularLeave(ApproveLeaveForm approveLeaveForm,
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap) throws ApplicationException;
	
	public Map<String,String> getStudentListByclass(ApproveLeaveForm approveLeaveForm)throws ApplicationException;
	
	public Map<String,String> getStudentRegisterNumbersListByclass(ApproveLeaveForm approveLeaveForm)throws ApplicationException;
	
	public Map<Integer,String> getClassesBySemType(ApproveLeaveForm approveLeaveForm)throws ApplicationException;

	public Integer getPeriodIdByNameAndClassId(String periodName, int parseInt,String startTime,String endTime)throws Exception;

	public List<Integer> getStartPeriodAndEndPeriodByClassId(ApproveLeaveForm activityAttendanceForm,String startTime,String endTime) throws Exception;

}
