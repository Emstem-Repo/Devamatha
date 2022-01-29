package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlGroupTo;
import com.kp.cms.to.hostel.HostelAbsentiesReportTo;
import com.kp.cms.to.hostel.HostelTO;

public class HostelAbsentiesReportForm  extends BaseActionForm{
	
	private String toDate;
	private String hostelName;
	private String hostelId;
	private String fromDate;
	List<HostelTO> hostelList;
	List<HostelTO> hostelTOList;
	List<HostelAbsentiesReportTo> hostelAbsentiesReportToList;
	List<HlGroupTo> hlGroupTOList;
	private String hlGroupName;
	private String hlGroupId;
	
	
	
	
	public List<HlGroupTo> getHlGroupTOList() {
		return hlGroupTOList;
	}
	public void setHlGroupTOList(List<HlGroupTo> hlGroupTOList) {
		this.hlGroupTOList = hlGroupTOList;
	}
	public String getHlGroupName() {
		return hlGroupName;
	}
	public void setHlGroupName(String hlGroupName) {
		this.hlGroupName = hlGroupName;
	}
	public String getHlGroupId() {
		return hlGroupId;
	}
	public void setHlGroupId(String hlGroupId) {
		this.hlGroupId = hlGroupId;
	}
	public List<HostelAbsentiesReportTo> getHostelAbsentiesReportToList() {
		return hostelAbsentiesReportToList;
	}
	public void setHostelAbsentiesReportToList(
			List<HostelAbsentiesReportTo> hostelAbsentiesReportToList) {
		this.hostelAbsentiesReportToList = hostelAbsentiesReportToList;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public List<HostelTO> getHostelTOList() {
		return hostelTOList;
	}
	public void setHostelTOList(List<HostelTO> hostelTOList) {
		this.hostelTOList = hostelTOList;
	}
	public void clearMyFields() {
		this.hostelList = null;
		this.toDate = null;
		this.fromDate = null;
		this.hostelTOList = null;
		this.hostelId = null;
		this.hlGroupId = null;
		this.hlGroupName = null;
		this.hlGroupTOList = null;
		this.hostelAbsentiesReportToList = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
}
