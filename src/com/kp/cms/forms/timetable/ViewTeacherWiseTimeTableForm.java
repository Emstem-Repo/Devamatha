package com.kp.cms.forms.timetable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.timetable.DepartentTimeTableClassTo;
import com.kp.cms.to.timetable.TimeTableClassTo;

@SuppressWarnings("serial")
public class ViewTeacherWiseTimeTableForm extends BaseActionForm{
	
	private int id;
	private List<Period> periodList;
	private Set<Integer> periodId;
	private List<TimeTableClassTo> timeTableList;
	private String flag;
	private String periodCound;
	private Map<Integer, String> departmentMap;
	private List<DepartentTimeTableClassTo> departmentTimeTableList;
	
 	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Period> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<Period> periodList) {
		this.periodList = periodList;
	}
	public Set<Integer> getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Set<Integer> periodId) {
		this.periodId = periodId;
	}
	public List<TimeTableClassTo> getTimeTableList() {
		return timeTableList;
	}
	public void setTimeTableList(List<TimeTableClassTo> timeTableList) {
		this.timeTableList = timeTableList;
	}
	public void reset() {
		this.id = 0;
		this.periodId = null;
		this.periodList = null;
		this.timeTableList = null;
		super.setYear(null);
		super.setClassSchemewiseId(null);
		this.flag = "false";
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPeriodCound() {
		return periodCound;
	}
	public void setPeriodCound(String periodCound) {
		this.periodCound = periodCound;
	}
	public Map<Integer, String> getDepartmentMap() {
		return departmentMap;
	}
	public void setDepartmentMap(Map<Integer, String> departmentMap) {
		this.departmentMap = departmentMap;
	}
	public List<DepartentTimeTableClassTo> getDepartmentTimeTableList() {
		return departmentTimeTableList;
	}
	public void setDepartmentTimeTableList(List<DepartentTimeTableClassTo> departmentTimeTableList) {
		this.departmentTimeTableList = departmentTimeTableList;
	}
}
