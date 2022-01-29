package com.kp.cms.forms.reports;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.RecommendedByTO;

public class RecommendedCandidatesForm extends BaseActionForm{
	
	private String method;
	private String recommendedBy;
	private List<ProgramTO> programList;
	private List<RecommendedByTO> recommenderList;

	public String getRecommendedBy() {
		return recommendedBy;
	}
	public void setRecommendedBy(String recommendedBy) {
		this.recommendedBy = recommendedBy;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<ProgramTO> getProgramList() {
		return programList;
	}
	public void setProgramList(List<ProgramTO> programList) {
		this.programList = programList;
	}
	public List<RecommendedByTO> getRecommenderList() {
		return recommenderList;
	}
	public void setRecommenderList(List<RecommendedByTO> recommenderList) {
		this.recommenderList = recommenderList;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.clear();
		this.programList = null;
		this.recommendedBy = null;
	}
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}