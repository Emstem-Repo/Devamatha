package com.kp.cms.bo.exam;

public class GrievanceStudentBo {
	private int id;
	private String remark;
	private Boolean isHodApproved;
	private Boolean isHodRejected;
	private String grievanceNo;
	private String hodId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getIsHodApproved() {
		return isHodApproved;
	}
	public void setIsHodApproved(Boolean isHodApproved) {
		this.isHodApproved = isHodApproved;
	}
	public Boolean getIsHodRejected() {
		return isHodRejected;
	}
	public void setIsHodRejected(Boolean isHodRejected) {
		this.isHodRejected = isHodRejected;
	}
	public String getGrievanceNo() {
		return grievanceNo;
	}
	public void setGrievanceNo(String grievanceNo) {
		this.grievanceNo = grievanceNo;
	}
	public String getHodId() {
		return hodId;
	}
	public void setHodId(String hodId) {
		this.hodId = hodId;
	}

}
