package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.forms.admin.EvaStudentFeedbackOpenConnectionForm;
import com.kp.cms.forms.hostel.HolidaysForm;

public interface IHolidaysTransaction {
Map<String,String> getProgramsMap()throws Exception;
public List<Integer> getCourseIdList(int programId)throws Exception;
public boolean saveHostelHolidays(List<HostelHolidaysBo> boList)throws Exception;
public List<HostelHolidaysBo> getHostelHolidaysList()throws Exception;
public boolean deleteHolidaysDetails( HolidaysForm holidaysForm)throws Exception;
public HostelHolidaysBo getHostelHolidaysDetails(int id)throws Exception;
Map<Integer,String> getCourseMap(String id)throws Exception;
public boolean updateHostelHolidaysDetails(HostelHolidaysBo boList)throws Exception;
public List<Integer> getProgIdList()throws Exception;
Map<Integer,String> getCoursemap()throws Exception;
public List<HostelHolidaysBo> checkDuplicate(HolidaysForm holidaysForm)throws Exception;
}
