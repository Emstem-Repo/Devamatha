package com.kp.cms.transactions.timetable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.timetable.ViewTeacherWiseTimeTableForm;

public interface IViewTeacherWiseTimeTableTransactions {

	public List<Object[]> getListDataByQuery(String query)throws Exception;

	public List<Period> getPeriodLists(Set<Integer> periodId)throws Exception;

	public List<Period> getPeriodGroupList(Set<Integer> periodId)throws Exception;

	public List<Object[]> getListDataByQuery1(String query)throws Exception;

	public String getHoursCount(String userId) throws Exception;
	
	public List<Object[]> getDeptWiseTimeTable(String deptTimetableQuery) throws Exception;

	public Map<Integer, String> getDeptList(ViewTeacherWiseTimeTableForm objForm) throws Exception;
}
