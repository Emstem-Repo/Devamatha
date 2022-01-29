package com.kp.cms.to.admin;

import java.util.List;

import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackQuestionTo;

public class PeersEvaluationFeedbackTO {
	private int id;
	private int departmentId;
	private String departmentName;
	private int peerId;
	private String peerName;
	private int assignGrpId;
	private String assignGrpName;
	private int peersNo;
	private List<Integer> totalPeers;
	private Boolean done;
	List<EvaFacultyFeedBackQuestionTo> questionTosList;
	private String remarks;
	private int peerGroupId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public int getPeerId() {
		return peerId;
	}
	public void setPeerId(int peerId) {
		this.peerId = peerId;
	}
	public String getPeerName() {
		return peerName;
	}
	public void setPeerName(String peerName) {
		this.peerName = peerName;
	}
	public int getAssignGrpId() {
		return assignGrpId;
	}
	public void setAssignGrpId(int assignGrpId) {
		this.assignGrpId = assignGrpId;
	}
	public String getAssignGrpName() {
		return assignGrpName;
	}
	public void setAssignGrpName(String assignGrpName) {
		this.assignGrpName = assignGrpName;
	}
	public int getPeersNo() {
		return peersNo;
	}
	public void setPeersNo(int peersNo) {
		this.peersNo = peersNo;
	}
	public List<Integer> getTotalPeers() {
		return totalPeers;
	}
	public void setTotalPeers(List<Integer> totalPeers) {
		this.totalPeers = totalPeers;
	}
	public List<EvaFacultyFeedBackQuestionTo> getQuestionTosList() {
		return questionTosList;
	}
	public void setQuestionTosList(
			List<EvaFacultyFeedBackQuestionTo> questionTosList) {
		this.questionTosList = questionTosList;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getPeerGroupId() {
		return peerGroupId;
	}
	public void setPeerGroupId(int peerGroupId) {
		this.peerGroupId = peerGroupId;
	}
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
}
