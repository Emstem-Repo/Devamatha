package com.kp.cms.forms.examallotment;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExaminationSessionsTo;

public class ExaminationSessionsForm extends BaseActionForm{

	private int id;
	private String session; 
	private String orderNo;
	private String sessionDesc;
	private String timings;
	private List<ExaminationSessionsTo> list;
	
	
	public List<ExaminationSessionsTo> getList() {
		return list;
	}
	public void setList(List<ExaminationSessionsTo> list) {
		this.list = list;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSessionDesc() {
		return sessionDesc;
	}
	public void setSessionDesc(String sessionDesc) {
		this.sessionDesc = sessionDesc;
	}
	public String getTimings() {
		return timings;
	}
	public void setTimings(String timings) {
		this.timings = timings;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
