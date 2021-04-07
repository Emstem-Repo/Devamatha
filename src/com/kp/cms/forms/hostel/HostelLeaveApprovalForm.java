package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;

public class HostelLeaveApprovalForm extends BaseActionForm{
	private int id;
	private String fromDate;
	private String toDate;
	private String semesterNo;
	private String status;
	private int hlAdmissionId;
	private List<HostelLeaveApprovalTo> hostelLeaveApprovalTo;
	private Map<String,List<HostelLeaveApprovalTo>> leaveApprovalMap;
	private Map<String,String> hostelMap;
	private Map<Integer,String> courseMap;
	private String regNo;
	private String studentName;
	private String className;
	private List<HostelLeaveApprovalTo> approvalTosList;
	private List<String> regNoList;
	private String rejectReason;
	private boolean notSelectAtLeastOne;
	private boolean flag;
	Map<Integer,String> blockMap;
	Map<Integer,String> unitMap;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(String semesterNo) {
		this.semesterNo = semesterNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<HostelLeaveApprovalTo> getHostelLeaveApprovalTo() {
		return hostelLeaveApprovalTo;
	}
	public void setHostelLeaveApprovalTo(
			List<HostelLeaveApprovalTo> hostelLeaveApprovalTo) {
		this.hostelLeaveApprovalTo = hostelLeaveApprovalTo;
	}
	
	public Map<String, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<String, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public void reset() {
		super.clear();
		this.fromDate=null;
		this.toDate=null;
		this.hostelLeaveApprovalTo = null;
		this.semesterNo = null;
		this.status = "pending";
		this.hostelMap = null;
		this.courseMap = null;
		this.leaveApprovalMap = null;
		this.notSelectAtLeastOne = false;
		this.flag= false;
	}
	public void clearFields() {
		this.leaveApprovalMap = null;
		this.hostelLeaveApprovalTo = null;
		this.regNoList = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public int getHlAdmissionId() {
		return hlAdmissionId;
	}
	public void setHlAdmissionId(int hlAdmissionId) {
		this.hlAdmissionId = hlAdmissionId;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<HostelLeaveApprovalTo> getApprovalTosList() {
		return approvalTosList;
	}
	public void setApprovalTosList(List<HostelLeaveApprovalTo> approvalTosList) {
		this.approvalTosList = approvalTosList;
	}
	public Map<String, List<HostelLeaveApprovalTo>> getLeaveApprovalMap() {
		return leaveApprovalMap;
	}
	public void setLeaveApprovalMap(
			Map<String, List<HostelLeaveApprovalTo>> leaveApprovalMap) {
		this.leaveApprovalMap = leaveApprovalMap;
	}
	public List<String> getRegNoList() {
		return regNoList;
	}
	public void setRegNoList(List<String> regNoList) {
		this.regNoList = regNoList;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public boolean isNotSelectAtLeastOne() {
		return notSelectAtLeastOne;
	}
	public void setNotSelectAtLeastOne(boolean notSelectAtLeastOne) {
		this.notSelectAtLeastOne = notSelectAtLeastOne;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}
	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}
	public Map<Integer, String> getUnitMap() {
		return unitMap;
	}
	public void setUnitMap(Map<Integer, String> unitMap) {
		this.unitMap = unitMap;
	}
	
}
