package com.kp.cms.transactions.attandance;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceSlipNumber;
import com.kp.cms.bo.admin.AttendanceTimeRestrictionClasswise;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.TeacherClassEntryTo;

public interface IAttendanceEntryTransaction {
	
	public List<BatchStudent> getStudentByBatch(int batchId) throws Exception;
	
	public List<Student> getStudentByClass(Set<Integer> classSet) throws Exception;
	
	//List Of Attendence Changed by mehaboob
	
	public boolean addAttendance(List<Attendance> attendanceList,AttendanceEntryForm attendanceEntryForm) throws ConstraintViolationException,Exception;
	
	public boolean updateAttendance(Attendance attendance) throws ConstraintViolationException,Exception;

	public Attendance getAttendanceStudents(AttendanceEntryForm attendanceEntryForm,int id) throws Exception;
	
	public int getAttendanceBySingleEntries(AttendanceEntryForm attendanceEntryForm)throws Exception ;
	
	public List<Object[]> duplicateAttendanceCheck(Set<Integer> classSet,Set<Integer> periodSet,Date date,int batchId,int subjectId) throws Exception;
	
	public List<Attendance> getAttendances(Set<Integer> classSet,Date date,int AttendaceTypeId) throws Exception;
	
	public  void cancelAttendance(Set<Integer> ids) throws Exception;
	
	public List<Attendance> getAllAttendances() throws Exception;
	
	public List<StuCocurrLeave> getActivityAttendanceOnDateClassPeriod(Set<Integer> classSet,Set<Integer> periodId,Date date) throws Exception;
	
	public List<Attendance> getActivityAttendanceOnDateClassPeriod(int classId,int periodId,Date date) throws Exception;
	
	public List<Period> getPeriodsForClass(int classSchemeWise) throws Exception;
	
	public List<Attendance> getAllAttendancesForDate(Date attendanceDate) throws Exception;
	
	public List<Attendance> getClassAttendances(Set<Integer> classSet,Date date) throws Exception;
	public boolean checkIsStaff(String userId) throws Exception;

	public Map<Integer, String> getClassesByTeacher(String[] teachers,
			String year) throws Exception;
	public AttendanceSlipNumber checkDuplicateSlipNo(int year) throws Exception;
	
	public String getSubjectNameById(int parseInt) throws Exception;
	public String getPeriodNamesById(String query1) throws Exception;

	public java.util.Date checkAttendanceDateMinRange(String query)throws Exception;

	public List<Integer> getCommonSubGrpForClass(Set<Integer> classSet,int year) throws Exception;

	public Date getLastEnteredAttendanceDate() throws Exception;

	public Map<Integer, Integer> getperiodsForInputClasses(Set<Integer> classesIdsSet) throws Exception;
	
	public List<Integer> duplicateAttendanceCheckWhileUpdate(Set<Integer> classSet,Set<Integer> periodSet,Date date,int batchId,int subjectId,int attendanceId) throws Exception;

	public Map<Integer, String> getClassNamesByIds(Set<Integer> classesIdsSet) throws Exception;

	public Map<Integer, List<String>> getPeriodsForClassAndPeriods(Set<Integer> classesIdsSet, String[] periods) throws Exception;

	public List<Integer> getAllPeriodIdsByInput(AttendanceEntryForm attendanceEntryForm) throws Exception;
	
	public Map<Integer,String> getUsers() throws Exception;

	public List<Period> getperiodsByIds(String[] classIds) throws Exception;
	
	public Users getUserDetails(AttendanceEntryForm attendanceEntryForm) throws Exception;

	public List<Period> getPeriodsList(List<Integer> classes) throws Exception;

	public List<Subject> getSubjectList(List<Integer> classes, AttendanceEntryForm attendanceEntryForm) throws Exception;

	public Map<Integer, String> getBatchesBySubjectAndClassScheme1(
			String subjectId, List<Integer> classSchemewiseIds,HttpSession httpSession) throws Exception;

	public Map<Integer, String> getBatchesByActivityAndClassScheme(
			int subjectId, List<Integer> classSchemewiseIds) throws Exception;

	public List<TeacherClassSubject> getMultiTeachers(List<Integer> classes, AttendanceEntryForm attendanceEntryForm) throws Exception;

	public List<Period> getPeriodsByTeacher(String[] teachers, String year,String day, String date, String subjectId) throws Exception;

	public List<TeacherClassEntryTo> getAditionalTeachers(String[] periods,	String dayForADate, String[] teachers, String subjectId) throws Exception;

	public int getCurrentPeriodId(List<Integer> periodList) throws Exception;
	
	public List<StuCocurrLeave> stuCocurrLeaveResult(String reg,AttendanceEntryForm attendanceEntryForm, Set<Integer> classSet) throws Exception;

	public List<StudentLeave> getLeavesDetails(String reg,java.util.Date date, Set<Integer> classSet) throws Exception;

	public Map<Integer, String> getClassesByTeacher(int teacherId,String year) throws Exception;

	public List<Attendance> checkAttendanceDuplication(java.util.Date date,int teacherId,String startTime,String endTime)throws Exception;	
	public String getTeacherName(int teacherId)throws Exception;
	public Period getPeriods(int periodId)throws Exception;
	public List<Integer> duplicateAttendanceCheckForBatches(Set<Integer> classSet,Set<Integer> periodSet,Date date,int batchId) throws Exception;
	
	public List<Object[]> duplicateAttendanceCheckForCommonSubject(Set<Integer> classSet,Set<Integer> periodSet,Date date)throws Exception;

	public String getSubjectCodeBySubId(int subjectId)throws Exception;

	//public List<Period> getperiodsByPeriodIds(List<Integer> periodList) throws Exception;
	
	//raghu
	
	public List<Integer> getStudentIdsByClassSubject(Set<Integer> classSet,int subjectId) throws Exception;
	
	List<Integer> getClassAttendancesStudentsIds(Set<Integer> classSet,Set<Integer> periodSet, java.util.Date date,String batchId) throws Exception;
	
	List<Integer> getClassAttendancesStudents(Set<Integer> classSet, java.util.Date date) throws Exception;
	
	List<String> getPeriodNamesOnClass(Set<Integer> classSet) throws Exception;
	
	List<String> getPeriodNmaesOnPeriods(Set<Integer> periodSet) throws Exception;
	
	List<Student> getStudentsOnClasses(Set<Integer> classSet, int year,int subjectId,Date startDate) throws Exception;

	List<Student> getStudentsOnAttendance(int id)	throws Exception;

	public List<Student> getStudentByClassBYWeeklyAttendance(
			Set<Integer> classSet)throws Exception;

	public List getStudentByClassBYWeeklyAttendanceData(String classid,
			String studentid) throws Exception;

	public List getStudentsOnBatches(Set<Integer> batchSet,Set<Integer> classSet) throws Exception;

	public boolean saveAttendanceTimeRestrictionBo(AttendanceTimeRestrictionClasswise bo);

	public AttendanceTimeRestrictionClasswise alreadyExist(AttendanceEntryForm attendanceEntryForm) throws Exception;

	public List<AttendanceTimeRestrictionClasswise> getAttendanceTimeRestrictedList() throws Exception;

	public boolean updateAttendanceTimeRestrictionBo(AttendanceTimeRestrictionClasswise attBo);

	boolean deleteAttendanceTimeRestriction(AttendanceEntryForm attendanceEntryForm);

	public List<AttendanceTimeRestrictionClasswise> getAttendanceTimeRestriction(
			String[] classes) throws Exception;
	
	public List<Student> getStudentByClass(Set<Integer> classSet,java.util.Date attendanceDate) throws Exception;

	public String getAttendanceEntryDay(BaseActionForm baseActionForm) throws Exception;
	
	Map<Integer, String> getClassesByTeacherForADay(int userId, String date) throws Exception;

	public List<HolidayBO> checkHolidayDate() throws Exception;

	public String getclassName(int classId)throws Exception;

	public List<Period> getPeriodsForAttendanceEntryDay(String attendanceDayCheck, int parseInt)throws Exception;

	public boolean isDateBetweenCurriculumSchemeDuration(String[] classes, Date date,int year)throws Exception;

	public List<TTUsers> getCoTeachersForAteendance(String query);

}