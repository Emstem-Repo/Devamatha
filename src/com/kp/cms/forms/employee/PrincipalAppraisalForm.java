package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.EmpAppraisalDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.employee.AppraisalsTO;
import com.kp.cms.to.employee.EmpAttributeTO;

public class PrincipalAppraisalForm extends BaseActionForm {
	
	private String method;
	private List<SingleFieldMasterTO> departmentList;
	private List<EmpAttributeTO> attriButeList;
	private String recommendation;
	private String comments;
	private int detailsId;
	private List<AppraisalsTO> appraisalTO;
	private AppraisalsTO appraiseTO;
	private int appraiseId;
	private Set<EmpAppraisalDetails> appraisalDetails;
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public List<EmpAttributeTO> getAttriButeList() {
		return attriButeList;
	}

	public void setAttriButeList(List<EmpAttributeTO> attriButeList) {
		this.attriButeList = attriButeList;
	}


	public List<SingleFieldMasterTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<SingleFieldMasterTO> departmentList) {
		this.departmentList = departmentList;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void clear(){
		this.setDepartmentId(null);
		this.setEmployeeId(null);
		this.recommendation = null;
		this.comments = null;
	}

	public void setDetailsId(int detailsId) {
		this.detailsId = detailsId;
	}

	public int getDetailsId() {
		return detailsId;
	}

	

	public void setAppraiseTO(AppraisalsTO appraiseTO) {
		this.appraiseTO = appraiseTO;
	}

	public AppraisalsTO getAppraiseTO() {
		return appraiseTO;
	}

	public void setAppraisalTO(List<AppraisalsTO> appraisalTO) {
		this.appraisalTO = appraisalTO;
	}

	public List<AppraisalsTO> getAppraisalTO() {
		return appraisalTO;
	}

	public void setAppraiseId(int appraiseId) {
		this.appraiseId = appraiseId;
	}

	public int getAppraiseId() {
		return appraiseId;
	}

	public void setAppraisalDetails(Set<EmpAppraisalDetails> appraisalDetails) {
		this.appraisalDetails = appraisalDetails;
	}

	public Set<EmpAppraisalDetails> getAppraisalDetails() {
		return appraisalDetails;
	}
}
