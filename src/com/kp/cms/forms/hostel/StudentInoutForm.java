package com.kp.cms.forms.hostel;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.StudentInoutTo;


public class StudentInoutForm extends BaseActionForm{
	private static final long serialVersionUID = 1L;
	private AdmAppln admAppln;
	private String studName;
	private String studId;
	List<StudentInoutTo> studentInoutToList;
	StudentInoutTo studentInoutTo;
	private String inTime;
	 private String outTime;
	 private String inTimeone;
	 private List<HostelTO> hostelList;
	 
	 public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getInTimeone() {
		return inTimeone;
	}
	public void setInTimeone(String inTimeone) {
		this.inTimeone = inTimeone;
	}
	public String getInTimetwo() {
		return inTimetwo;
	}
	public void setInTimetwo(String inTimetwo) {
		this.inTimetwo = inTimetwo;
	}
	public String getOutTimeone() {
		return outTimeone;
	}
	public void setOutTimeone(String outTimeone) {
		this.outTimeone = outTimeone;
	}
	public String getOutTimetwo() {
		return outTimetwo;
	}
	public void setOutTimetwo(String outTimetwo) {
		this.outTimetwo = outTimetwo;
	}
	private String inTimetwo;
	 private String outTimeone;
	 private String outTimetwo;
	
	public StudentInoutTo getStudentInoutTo() {
		return studentInoutTo;
	}
	public void setStudentInoutTo(StudentInoutTo studentInoutTo) {
		this.studentInoutTo = studentInoutTo;
	}
	public List<StudentInoutTo> getStudentInoutToList() {
		return studentInoutToList;
	}
	public void setStudentInoutToList(List<StudentInoutTo> studentInoutToList) {
		this.studentInoutToList = studentInoutToList;
	}
	public String getStudId() {
		return studId;
	}
	public void setStudId(String studId) {
		this.studId = studId;
	}
	public AdmAppln getAdmAppln() {
		return admAppln;
	}
	public void setAdmAppln(AdmAppln admAppln) {
		this.admAppln = admAppln;
	}
	public String getStudName() {
		return studName;
	}
	public void setStudName(String studName) {
		this.studName = studName;
	}
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public void clearMyFields() {
		this.studId=null;
		this.admAppln=null;
		this.studName=null;
		super.setHostelId(null);
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	/*public static long getSerialVersionUID() {
		return serialVersionUID;
	}*/

}
