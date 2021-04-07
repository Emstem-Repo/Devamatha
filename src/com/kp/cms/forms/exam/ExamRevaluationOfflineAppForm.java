package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;

public class ExamRevaluationOfflineAppForm extends BaseActionForm {
	private String registerNo;
	private String examId;
	private String examType;
	Map<Integer, String> examNameMap;
	List<ExamRevaluationApplicationTO> revAppList;
	Map<String,Double> revalationFeeMap;
	private String studentName;
	private String ddNo;
	private String ddDate;
	private String amount;
	private String marksCardType;
	private String bankName;
	private String branchName;
	private int studentId;
	private int classesId;
	
	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}

	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}

	public List<ExamRevaluationApplicationTO> getRevAppList() {
		return revAppList;
	}

	public void setRevAppList(List<ExamRevaluationApplicationTO> revAppList) {
		this.revAppList = revAppList;
	}


	public Map<String, Double> getRevalationFeeMap() {
		return revalationFeeMap;
	}

	public void setRevalationFeeMap(Map<String, Double> revalationFeeMap) {
		this.revalationFeeMap = revalationFeeMap;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getDdNo() {
		return ddNo;
	}

	public void setDdNo(String ddNo) {
		this.ddNo = ddNo;
	}

	public String getDdDate() {
		return ddDate;
	}

	public void setDdDate(String ddDate) {
		this.ddDate = ddDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMarksCardType() {
		return marksCardType;
	}

	public void setMarksCardType(String marksCardType) {
		this.marksCardType = marksCardType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getClassesId() {
		return classesId;
	}

	public void setClassesId(int classesId) {
		this.classesId = classesId;
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
	
	public void resetFields() {
		this.examId=null;
		this.registerNo=null;
		this.examType="Regular";
		this.ddDate=null;
		this.ddNo=null;
		this.amount=null;
		this.bankName=null;
		this.branchName=null;
		this.studentId=0;
		this.classesId=0;
		super.setSchemeNo(null);
	}

}
