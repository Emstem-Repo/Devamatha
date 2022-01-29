package com.kp.cms.forms.admission;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.DemandSlipInstructionTO;

public class FeeDemandSlipInstructionForm extends BaseActionForm{
	private int id;
	private String courseId;
	private String schemeNo;
	private String instruction;
	private List<CourseTO> courseList;
	private List<DemandSlipInstructionTO> deTosList;
	private String method;
	private String oldCourseId;
	private String oldSchemeNo;
	private String oldCreatedBy;
	private Date oldCreatedDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public List<DemandSlipInstructionTO> getDeTosList() {
		return deTosList;
	}
	public void setDeTosList(List<DemandSlipInstructionTO> deTosList) {
		this.deTosList = deTosList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getOldCourseId() {
		return oldCourseId;
	}
	public void setOldCourseId(String oldCourseId) {
		this.oldCourseId = oldCourseId;
	}
	public String getOldSchemeNo() {
		return oldSchemeNo;
	}
	public void setOldSchemeNo(String oldSchemeNo) {
		this.oldSchemeNo = oldSchemeNo;
	}
	public String getOldCreatedBy() {
		return oldCreatedBy;
	}
	public void setOldCreatedBy(String oldCreatedBy) {
		this.oldCreatedBy = oldCreatedBy;
	}
	public Date getOldCreatedDate() {
		return oldCreatedDate;
	}
	public void setOldCreatedDate(Date oldCreatedDate) {
		this.oldCreatedDate = oldCreatedDate;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clear(){
		id=0;
		courseId=null;
		schemeNo=null;
		instruction=null;
		oldCourseId=null;
		oldSchemeNo=null;
	}
}
