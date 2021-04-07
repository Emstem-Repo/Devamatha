package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;

/**
 * Dec 30, 2009 Created By 9Elements
 */
public class ExamRejoinForm extends BaseActionForm {
	int id ;
	private String regNumber;
	private String rollNumber ;
	private String newRegNumber;
	private String newRollNumber ;
	private String readmittedClass ;
	private String joiningBatch ;
	private String joiningDate ;
	private List<KeyValueTO> listJoinBatch;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clearPage() {
		this.regNumber = "";
		this.rollNumber = "";
		this.newRegNumber = "";
		this.newRollNumber = "";
		this.readmittedClass = "";
		this.joiningBatch = "";
		this.joiningDate = "";

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getNewRegNumber() {
		return newRegNumber;
	}

	public void setNewRegNumber(String newRegNumber) {
		this.newRegNumber = newRegNumber;
	}

	public String getNewRollNumber() {
		return newRollNumber;
	}

	public void setNewRollNumber(String newRollNumber) {
		this.newRollNumber = newRollNumber;
	}

	public String getReadmittedClass() {
		return readmittedClass;
	}

	public void setReadmittedClass(String readmittedClass) {
		this.readmittedClass = readmittedClass;
	}

	public String getJoiningBatch() {
		return joiningBatch;
	}

	public void setJoiningBatch(String joiningBatch) {
		this.joiningBatch = joiningBatch;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public List<KeyValueTO> getListJoinBatch() {
		return listJoinBatch;
	}

	public void setListJoinBatch(List<KeyValueTO> listJoinBatch) {
		this.listJoinBatch = listJoinBatch;
	}

}
