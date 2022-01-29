package com.kp.cms.forms.exam;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.BatchClassTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamUpdateProcessForm extends BaseActionForm {

	/**
	 * Used in Exam Module
	 */

	private String process;
	private String examName;
	private String academicYear;
	private String promotionCritariaCheck = "off";
	private String examFeePaidCheck = "off";
	private ArrayList<BatchClassTO> listClasses;
	private ArrayList<KeyValueTO> examList;
	private String processName;
	private String examId;
	private String listCount;

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		process = "";
		examId = "";
		promotionCritariaCheck = "off";
		examFeePaidCheck = "off";
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getPromotionCritariaCheck() {
		return promotionCritariaCheck;
	}

	public void setPromotionCritariaCheck(String promotionCritariaCheck) {
		this.promotionCritariaCheck = promotionCritariaCheck;
	}

	public String getExamFeePaidCheck() {
		return examFeePaidCheck;
	}

	public void setExamFeePaidCheck(String examFeePaidCheck) {
		this.examFeePaidCheck = examFeePaidCheck;
	}

	public void setListClasses(ArrayList<BatchClassTO> listClasses) {
		this.listClasses = listClasses;
	}

	public ArrayList<BatchClassTO> getListClasses() {
		return listClasses;
	}

	public void setExamList(ArrayList<KeyValueTO> examList) {
		this.examList = examList;
	}

	public ArrayList<KeyValueTO> getExamList() {
		return examList;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamId() {
		return examId;
	}

	public void setListCount(String listCount) {
		this.listCount = listCount;
	}

	public String getListCount() {
		return listCount;
	}

}
