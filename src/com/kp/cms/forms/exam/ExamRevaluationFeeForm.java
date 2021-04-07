package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ExamRevaluationFeeTO;

public class ExamRevaluationFeeForm extends BaseActionForm{
	private int id;
    private List<ProgramTypeTO> programTypeList; 
    private List<ExamRevaluationFeeTO> revaluationFeeToList;
    private String origProgramType;
    private String origType;
    private String origAmount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void reset(){
		this.id=0;
		super.clear2();
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<ExamRevaluationFeeTO> getRevaluationFeeToList() {
		return revaluationFeeToList;
	}
	public void setRevaluationFeeToList(
			List<ExamRevaluationFeeTO> revaluationFeeToList) {
		this.revaluationFeeToList = revaluationFeeToList;
	}
	public String getOrigProgramType() {
		return origProgramType;
	}
	public void setOrigProgramType(String origProgramType) {
		this.origProgramType = origProgramType;
	}
	public String getOrigType() {
		return origType;
	}
	public void setOrigType(String origType) {
		this.origType = origType;
	}
	public String getOrigAmount() {
		return origAmount;
	}
	public void setOrigAmount(String origAmount) {
		this.origAmount = origAmount;
	}
}
