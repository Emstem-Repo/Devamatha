package com.kp.cms.transactions.hostel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.hostel.HlStudentAttendance;
import com.kp.cms.forms.hostel.AbsentiesListForm;

public interface IAbsentiesListTransaction {
	public Map<Integer,String> getHostelMap()throws Exception;
	public List<HlAdmissionBo> getHlAdmissionBos(List<Integer> list)throws Exception;
	public List<Integer> getHlAdmissionBos(String query)throws Exception;
	Map<Integer,List<Integer>> getHlAdmissionBosByCourse(String query)throws Exception;
	Map<Integer,Map<Date,List<Integer>>> getMorningAbsentMapByCourse(String query, AbsentiesListForm absentiesListForm, String isMorning)throws Exception;
	Map<Integer,List<HostelHolidaysBo>> getHostelHolidaysByCourse(String query)throws Exception;
	Map<Integer,Map<Integer,List<HlLeave>>> getHLLeavesByCourse(AbsentiesListForm absentiesListForm)throws Exception;
	Map<Integer,HlAdmissionBo> getHlAdmissionMap(String query)throws Exception;
	public Map<Integer, List<Integer>> getPresentMapByCourseNew(AbsentiesListForm absentiesListForm) throws Exception;
	public Map<Integer, List<Integer>> getLeavesByCourseWise(AbsentiesListForm absentiesListForm) throws Exception;
	public Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> getHolidaysMap(
			AbsentiesListForm absentiesListForm) throws Exception;
	public Map<Integer, List<Integer>> getExemptionListByCourseWise(AbsentiesListForm absentiesListForm) throws Exception;
	public List<Integer> getSundayMorningExemptionStudentsList(List<Integer> admIds)throws Exception;
}
