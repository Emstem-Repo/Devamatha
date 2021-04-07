package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.HideTeachersForPeersEvaluationTo;

public class HideTeachersForPeersEvaluationForm extends BaseActionForm{
	private int id;
	private String teacherId;
	private List<DepartmentEntryTO> departmentList;
	Map<Integer,String> teachersMap;
	private List<HideTeachersForPeersEvaluationTo> hiddenTeachersList ;
	private boolean flag;
	private String deptartmentName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<DepartmentEntryTO> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<DepartmentEntryTO> departmentList) {
		this.departmentList = departmentList;
	}
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public List<HideTeachersForPeersEvaluationTo> getHiddenTeachersList() {
		return hiddenTeachersList;
	}
	public void setHiddenTeachersList(
			List<HideTeachersForPeersEvaluationTo> hiddenTeachersList) {
		this.hiddenTeachersList = hiddenTeachersList;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getDeptartmentName() {
		return deptartmentName;
	}
	public void setDeptartmentName(String deptartmentName) {
		this.deptartmentName = deptartmentName;
	}
}
