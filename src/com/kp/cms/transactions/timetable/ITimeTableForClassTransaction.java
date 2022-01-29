package com.kp.cms.transactions.timetable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.TTClasses;
import com.kp.cms.bo.admin.TTPeriodWeek;
import com.kp.cms.bo.admin.TTSubjectBatch;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.timetable.TimeTableForClassForm;

public interface ITimeTableForClassTransaction {

	boolean addTimeTableForaPeriod(List<TTSubjectBatch> boList, TimeTableForClassForm timeTableForClassForm) throws Exception;

	boolean updateFlagForTimeTable(String userIds, int ttClassId, String finalApprove) throws Exception;

	boolean checkForTtClassHistory(String classId) throws Exception;

	public List<Object[]> getTeachers(String string)throws Exception;
	
	public boolean saveAttendanceBo(List<Period> periodBo,TimeTableForClassForm timetableform, Set<Integer> selectedWeeksSet, Set<Integer> classesIdsSet, Set<Integer> teachersIdsSet, List<TTUsers> availttList, List<TTClasses> avattclassesList, List<TTSubjectBatch> avasubjbatchList, List<TTPeriodWeek> avaperiodweekList) throws Exception;

	List<Period> getperiods(Set<Integer> classesIdsSet, String periodNameForQuery) throws ApplicationException;


	List getAvailableTimetable(String timeTableDeletequery) throws ApplicationException;

	String getAttendanceType(BaseActionForm baseActionForm) throws ApplicationException;

	String getsubjectsFromTeacherClass(String userid) throws ApplicationException;
	
	List<Object[]> getSubjectByClassId(String courseId);
	
	Map<Integer, String> getClassesForAttendanceByYearAndUserId(int year, String evenOrOdd,String userId);
	
	Map<Integer, String> getClassesForAttendanceByYearAndUserIdAndSubjectId(int year, String evenOrOdd, String userId,String SubjectId);
}
