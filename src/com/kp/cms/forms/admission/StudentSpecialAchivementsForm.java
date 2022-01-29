package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.SpecialAchivementsTo;

public class StudentSpecialAchivementsForm extends BaseActionForm{
	
	private String registerNo;
	private String achivements;
	private String method;
	private List<SpecialAchivementsTo> achivementList;
	private Integer id;
	private String dupRegNo;
	private String dupAch;
	private int reactivateId;
	private String termNumber;
	private String dupTermNumber;
	
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getAchivements() {
		return achivements;
	}
	public void setAchivements(String achivements) {
		this.achivements = achivements;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void clear()
	{
		id=null;
		registerNo=null;
		achivements=null;
		termNumber=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<SpecialAchivementsTo> getAchivementList() {
		return achivementList;
	}
	public void setAchivementList(List<SpecialAchivementsTo> achivementList) {
		this.achivementList = achivementList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDupRegNo() {
		return dupRegNo;
	}
	public void setDupRegNo(String dupRegNo) {
		this.dupRegNo = dupRegNo;
	}
	public String getDupAch() {
		return dupAch;
	}
	public void setDupAch(String dupAch) {
		this.dupAch = dupAch;
	}
	public int getReactivateId() {
		return reactivateId;
	}
	public void setReactivateId(int reactivateId) {
		this.reactivateId = reactivateId;
	}
	public String getTermNumber() {
		return termNumber;
	}
	public void setTermNumber(String termNumber) {
		this.termNumber = termNumber;
	}
	public String getDupTermNumber() {
		return dupTermNumber;
	}
	public void setDupTermNumber(String dupTermNumber) {
		this.dupTermNumber = dupTermNumber;
	}

}
