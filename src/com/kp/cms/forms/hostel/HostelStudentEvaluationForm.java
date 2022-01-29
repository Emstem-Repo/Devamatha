package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelStudentEvaluationTO;
import com.kp.cms.to.hostel.HostelTO;

public class HostelStudentEvaluationForm extends BaseActionForm{
	
	private String fromDate;
	private String toDate;
	private List<HostelTO> hostelTOList;
	private HostelStudentEvaluationTO evaluationTO;
	private List<HostelStudentEvaluationTO> evaluationList;
	
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

	public List<HostelTO> getHostelTOList() {
		return hostelTOList;
	}

	public void setHostelTOList(List<HostelTO> hostelTOList) {
		this.hostelTOList = hostelTOList;
	}
    
	public HostelStudentEvaluationTO getEvaluationTO() {
		return evaluationTO;
	}

	public void setEvaluationTO(HostelStudentEvaluationTO evaluationTO) {
		this.evaluationTO = evaluationTO;
	}

	public List<HostelStudentEvaluationTO> getEvaluationList() {
		return evaluationList;
	}

	public void setEvaluationList(List<HostelStudentEvaluationTO> evaluationList) {
		this.evaluationList = evaluationList;
	}

	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields1() 
	{
		super.setHostelId(null);
		super.setAcademicYear(null);
		fromDate =null;
		toDate =null;
	}
}
