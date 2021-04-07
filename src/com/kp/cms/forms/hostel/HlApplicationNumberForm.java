package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlApplicationFeeTO;

public class HlApplicationNumberForm extends BaseActionForm{
	private int id;
	private Map<Integer,String> hostelMap;
	private String academicYear;
	private String preFix;
	private String startNumber;
	private List<HlApplicationFeeTO> applicationNoList;
	
	
	
	/**
	 * form validation
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Map<Integer, String> getHostelMap() {
		return hostelMap;
	}



	public void setHostelMap(Map<Integer, String> hostelMap) {
		this.hostelMap = hostelMap;
	}



	public String getAcademicYear() {
		return academicYear;
	}



	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}



	public String getPreFix() {
		return preFix;
	}



	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}



	public String getStartNumber() {
		return startNumber;
	}



	public void setStartNumber(String startNumber) {
		this.startNumber = startNumber;
	}



	public List<HlApplicationFeeTO> getApplicationNoList() {
		return applicationNoList;
	}



	public void setApplicationNoList(List<HlApplicationFeeTO> applicationNoList) {
		this.applicationNoList = applicationNoList;
	}
}
