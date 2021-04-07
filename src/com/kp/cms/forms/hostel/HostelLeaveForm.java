package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelUnitsTO;

public class HostelLeaveForm extends BaseActionForm{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private String startDate;
	private String endDate;
	private String reasons;
	private String leaveType;
	private List<HlLeave> leaveTypeList;
	private String registerNo;
	private List<HostelTO> hostelList;
	private HlApplicationForm hlApplicationFormBo;
	private String academicYear1;
	private String leaveFrom;
	private String leaveFromSession;
	private String leaveTo;
	private String leaveToSession;
	private String requestType;
	private String remarks;
	private HostelTO hostelTOList;
	private String id;
	private String studentName;
	private String hostelName;
	private String roomNo;
	private String bedNo;
	private String studentBlock;
	private String studentUnit;
	private String hostelAdmId;
	private String tempAcademicYear;
	public boolean displayStudentDetails;
	
	// for student leave application
	private String blockName;
	private String unitName;
	private HostelUnitsTO unitsTo;
	private String hlAdmId;
	private List<HostelUnitsTO> totalLeaves;
	private int hlLeaveId;
	private String noRecordFound;
	private String parentMob;
	private List<HostelLeaveApprovalTo> previousList;
	private String className;
	private String studentMobile;
	private String studentmail;
	private String isHlTransaction;
	private String rgNoFromHlTransaction;
	
	public String getStudentmail() {
		return studentmail;
	}
	public void setStudentmail(String studentmail) {
		this.studentmail = studentmail;
	}
	public String getStudentMobile() {
		return studentMobile;
	}
	public void setStudentMobile(String studentMobile) {
		this.studentMobile = studentMobile;
	}
	public String getHostelAdmId() {
		return hostelAdmId;
	}
	public void setHostelAdmId(String hostelAdmId) {
		this.hostelAdmId = hostelAdmId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getStudentBlock() {
		return studentBlock;
	}
	public void setStudentBlock(String studentBlock) {
		this.studentBlock = studentBlock;
	}
	public String getStudentUnit() {
		return studentUnit;
	}
	public void setStudentUnit(String studentUnit) {
		this.studentUnit = studentUnit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public HostelTO getHostelTOList() {
		return hostelTOList;
	}
	public void setHostelTOList(HostelTO hostelTOList) {
		this.hostelTOList = hostelTOList;
	}
	public String getAcademicYear1() {
		return academicYear1;
	}
	public void setAcademicYear1(String academicYear1) {
		this.academicYear1 = academicYear1;
	}
	public String getLeaveFrom() {
		return leaveFrom;
	}
	public void setLeaveFrom(String leaveFrom) {
		this.leaveFrom = leaveFrom;
	}
	public String getLeaveFromSession() {
		return leaveFromSession;
	}
	public void setLeaveFromSession(String leaveFromSession) {
		this.leaveFromSession = leaveFromSession;
	}
	public String getLeaveTo() {
		return leaveTo;
	}
	public void setLeaveTo(String leaveTo) {
		this.leaveTo = leaveTo;
	}
	public String getLeaveToSession() {
		return leaveToSession;
	}
	public void setLeaveToSession(String leaveToSession) {
		this.leaveToSession = leaveToSession;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the registerNo
	 */
	public String getRegisterNo() {
		return registerNo;
	}
	/**
	 * @param registerNo the registerNo to set
	 */
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	/**
	 * @return the hlApplicationFormBo
	 */
	public HlApplicationForm getHlApplicationFormBo() {
		return hlApplicationFormBo;
	}
	/**
	 * @param hlApplicationFormBo the hlApplicationFormBo to set
	 */
	public void setHlApplicationFormBo(HlApplicationForm hlApplicationFormBo) {
		this.hlApplicationFormBo = hlApplicationFormBo;
	}
	
	/**
	 * @return the hostelTOList
	 */
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public List<HlLeave> getLeaveTypeList() {
		return leaveTypeList;
	}
	public void setLeaveTypeList(List<HlLeave> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	
	public String getTempAcademicYear() {
		return tempAcademicYear;
	}
	public void setTempAcademicYear(String tempAcademicYear) {
		this.tempAcademicYear = tempAcademicYear;
	}
	
	public boolean isDisplayStudentDetails() {
		return displayStudentDetails;
	}
	public void setDisplayStudentDetails(boolean displayStudentDetails) {
		this.displayStudentDetails = displayStudentDetails;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		super.setHostelId(null);
		this.startDate = null;
		this.endDate = null;
		this.reasons = null;
		this.leaveType = null;
		this.registerNo= null;
		this.leaveFrom=null;
		this.academicYear1=null;
		this.leaveFromSession=null;
		this.leaveTo=null;
		this.leaveToSession=null;
		this.requestType=null;
		this.remarks=null;
		this.tempAcademicYear=null;
		this.displayStudentDetails=false;
		this.hostelList=null;
		this.id=null;
		
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	/**
	 * @return the hostelList
	 */
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	/**
	 * @param hostelList the hostelList to set
	 */
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public HostelUnitsTO getUnitsTo() {
		return unitsTo;
	}
	public void setUnitsTo(HostelUnitsTO unitsTo) {
		this.unitsTo = unitsTo;
	}
	public String getHlAdmId() {
		return hlAdmId;
	}
	public void setHlAdmId(String hlAdmId) {
		this.hlAdmId = hlAdmId;
	}
	public List<HostelUnitsTO> getTotalLeaves() {
		return totalLeaves;
	}
	public void setTotalLeaves(List<HostelUnitsTO> totalLeaves) {
		this.totalLeaves = totalLeaves;
	}
	public int getHlLeaveId() {
		return hlLeaveId;
	}
	public void setHlLeaveId(int hlLeaveId) {
		this.hlLeaveId = hlLeaveId;
	}
	public String getNoRecordFound() {
		return noRecordFound;
	}
	public void setNoRecordFound(String noRecordFound) {
		this.noRecordFound = noRecordFound;
	}
	public String getParentMob() {
		return parentMob;
	}
	public void setParentMob(String parentMob) {
		this.parentMob = parentMob;
	}
	public List<HostelLeaveApprovalTo> getPreviousList() {
		return previousList;
	}
	public void setPreviousList(List<HostelLeaveApprovalTo> previousList) {
		this.previousList = previousList;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getRgNoFromHlTransaction() {
		return rgNoFromHlTransaction;
	}
	public void setRgNoFromHlTransaction(String rgNoFromHlTransaction) {
		this.rgNoFromHlTransaction = rgNoFromHlTransaction;
	}
	public String getIsHlTransaction() {
		return isHlTransaction;
	}
	public void setIsHlTransaction(String isHlTransaction) {
		this.isHlTransaction = isHlTransaction;
	}
	

}