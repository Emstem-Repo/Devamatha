package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.CCGroupCourseTo;
import com.kp.cms.to.admission.CCGroupTo;

public class GroupCourseEntryForm extends BaseActionForm {
	
	private String[] programTypeIds;
	private String groupId;
	private List<CourseTO> courseList;
	private List<CCGroupTo> groupToList;
	private List<ProgramTypeTO> programTypeList;
	private List<CCGroupCourseTo> groupCourseToList;
	private int id;
	
	public String[] getProgramTypeIds() {
		return programTypeIds;
	}
	public void setProgramTypeIds(String[] programTypeIds) {
		this.programTypeIds = programTypeIds;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	
	public List<CCGroupTo> getGroupToList() {
		return groupToList;
	}
	public void setGroupToList(List<CCGroupTo> groupToList) {
		this.groupToList = groupToList;
	}
	
	
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public List<CCGroupCourseTo> getGroupCourseToList() {
		return groupCourseToList;
	}
	public void setGroupCourseToList(List<CCGroupCourseTo> groupCourseToList) {
		this.groupCourseToList = groupCourseToList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	/**
	 * 
	 */
	public void resetFields(){
		this.programTypeIds=null;
		this.groupId=null;
		this.courseList=null;
		this.id=0;
		this.groupCourseToList=null;
	}
}