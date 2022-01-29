package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelDailyReportTo;
import com.kp.cms.to.hostel.HostelTO;

public class HostelDailyReportForm  extends BaseActionForm{
	private String toDate;
	private String hostelName;
	private String hostelId;
	private String fromDate;
	List<HostelTO> hostelList;
	List<HostelTO> hostelTOList;
	List<HostelDailyReportTo> hostelDailyReportToList;
	
	
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
	public List<HostelDailyReportTo> getHostelDailyReportToList() {
		return hostelDailyReportToList;
	}
	public void setHostelDailyReportToList(
			List<HostelDailyReportTo> hostelDailyReportToList) {
		this.hostelDailyReportToList = hostelDailyReportToList;
	}
	public List<HostelTO> getHostelTOList() {
		return hostelTOList;
	}
	public void setHostelTOList(List<HostelTO> hostelTOList) {
		this.hostelTOList = hostelTOList;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	public void clearMyFields() {
		this.hostelList = null;
		this.toDate = null;
		this.fromDate = null;
		this.hostelTOList = null;
		this.hostelDailyReportToList = null;
		this.hostelId = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
}
