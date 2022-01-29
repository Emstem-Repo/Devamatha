package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.PeersEvaluationFeedbackTO;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackQuestionTo;

public class PeersEvaluationFeedbackForm extends BaseActionForm{
	private int id;
	private int deptId;
	private String departmentName;
	private String sessionId;
	private int peerId;
	private String peerName;
	private int peerGroupId;
	private List<PeersEvaluationFeedbackTO> peerEvaluationToList;
	private int peersNo;
	private int totalPeers;
	private int totalQuestions;
	private List<EvaFacultyFeedBackQuestionTo> evaFacultyQuestionsToList;
	private String remarks;
	private PeersEvaluationFeedbackTO tempPeersEvaList;
	private boolean exist;
	private boolean submitSuccessfully;
	private List<StudentFeedbackInstructionsTO> instructionsTOsList;
	private List<EvaFacultyFeedBackQuestionTo> oldEvaFacultyQuestionsToList;
	private int assignGroupId;
	private boolean evaluationCompleted;
	private String previousEmpName;
	private boolean lastPeer;
	private Map<Integer,Integer> facultyGroupIdsMap;
	private String errorMsg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<PeersEvaluationFeedbackTO> getPeerEvaluationToList() {
		return peerEvaluationToList;
	}
	public void setPeerEvaluationToList(
			List<PeersEvaluationFeedbackTO> peerEvaluationToList) {
		this.peerEvaluationToList = peerEvaluationToList;
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
	public int getPeerGroupId() {
		return peerGroupId;
	}
	public void setPeerGroupId(int peerGroupId) {
		this.peerGroupId = peerGroupId;
	}
	public int getPeersNo() {
		return peersNo;
	}
	public void setPeersNo(int peersNo) {
		this.peersNo = peersNo;
	}
	public int getTotalPeers() {
		return totalPeers;
	}
	public void setTotalPeers(int totalPeers) {
		this.totalPeers = totalPeers;
	}
	public int getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public List<EvaFacultyFeedBackQuestionTo> getEvaFacultyQuestionsToList() {
		return evaFacultyQuestionsToList;
	}
	public void setEvaFacultyQuestionsToList(
			List<EvaFacultyFeedBackQuestionTo> evaFacultyQuestionsToList) {
		this.evaFacultyQuestionsToList = evaFacultyQuestionsToList;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isExist() {
		return exist;
	}
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public boolean isSubmitSuccessfully() {
		return submitSuccessfully;
	}
	public void setSubmitSuccessfully(boolean submitSuccessfully) {
		this.submitSuccessfully = submitSuccessfully;
	}
	public List<StudentFeedbackInstructionsTO> getInstructionsTOsList() {
		return instructionsTOsList;
	}
	public void setInstructionsTOsList(
			List<StudentFeedbackInstructionsTO> instructionsTOsList) {
		this.instructionsTOsList = instructionsTOsList;
	}
	public int getAssignGroupId() {
		return assignGroupId;
	}
	public void setAssignGroupId(int assignGroupId) {
		this.assignGroupId = assignGroupId;
	}
	
	public List<EvaFacultyFeedBackQuestionTo> getOldEvaFacultyQuestionsToList() {
		return oldEvaFacultyQuestionsToList;
	}
	public void setOldEvaFacultyQuestionsToList(
			List<EvaFacultyFeedBackQuestionTo> oldEvaFacultyQuestionsToList) {
		this.oldEvaFacultyQuestionsToList = oldEvaFacultyQuestionsToList;
	}
	public boolean isEvaluationCompleted() {
		return evaluationCompleted;
	}
	public void setEvaluationCompleted(boolean evaluationCompleted) {
		this.evaluationCompleted = evaluationCompleted;
	}
	public PeersEvaluationFeedbackTO getTempPeersEvaList() {
		return tempPeersEvaList;
	}
	public void setTempPeersEvaList(PeersEvaluationFeedbackTO tempPeersEvaList) {
		this.tempPeersEvaList = tempPeersEvaList;
	}
	public String getPreviousEmpName() {
		return previousEmpName;
	}
	public void setPreviousEmpName(String previousEmpName) {
		this.previousEmpName = previousEmpName;
	}
	public boolean isLastPeer() {
		return lastPeer;
	}
	public void setLastPeer(boolean lastPeer) {
		this.lastPeer = lastPeer;
	}
	public Map<Integer, Integer> getFacultyGroupIdsMap() {
		return facultyGroupIdsMap;
	}
	public void setFacultyGroupIdsMap(Map<Integer, Integer> facultyGroupIdsMap) {
		this.facultyGroupIdsMap = facultyGroupIdsMap;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
