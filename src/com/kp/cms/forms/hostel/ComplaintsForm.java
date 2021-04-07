package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.ComplaintsTO;
import com.kp.cms.to.hostel.HostelTO;

public class ComplaintsForm extends BaseActionForm{
	
	private String method;
	private String logDate;
	private String complaintType;
	private String title;
	private String description;
	private List<ComplaintsTO> complaintsList; 
	private List<HostelTO> hostelList;
	private String hostelName;
	private String requisitionNo;
	private List<ComplaintsTO> complaintsListTo;
	private int id;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the complaintsListTo
	 */
	public List<ComplaintsTO> getComplaintsListTo() {
		return complaintsListTo;
	}
	/**
	 * @param complaintsListTo the complaintsListTo to set
	 */
	public void setComplaintsListTo(List<ComplaintsTO> complaintsListTo) {
		this.complaintsListTo = complaintsListTo;
	}
	/**
	 * @return the hostelList
	 */
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	/**
	 * @param hostelList the hostelList to set
	 */
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	/**
	 * @return the requisitionNo
	 */
	public String getRequisitionNo() {
		return requisitionNo;
	}
	/**
	 * @param requisitionNo the requisitionNo to set
	 */
	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}
	/**
	 * @return the hostelName
	 */
	public String getHostelName() {
		return hostelName;
	}
	/**
	 * @param hostelName the hostelName to set
	 */
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public String getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setComplaintsList(List<ComplaintsTO> complaintsList) {
		this.complaintsList = complaintsList;
	}
	public List<ComplaintsTO> getComplaintsList() {
		return complaintsList;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.complaintType = null;
		this.description = null;
		this.title = null;
		this.hostelName=null;
		this.requisitionNo= null;
	}
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	
}