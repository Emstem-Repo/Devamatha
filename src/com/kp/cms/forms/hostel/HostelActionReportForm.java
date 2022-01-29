package com.kp.cms.forms.hostel;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelActionReportTO;
import com.kp.cms.to.hostel.HostelTO;

public class HostelActionReportForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private String startDate;
	private String endDate;
	private String actionId;
	private String print;
	private List<HostelTO> hostelList;
	private List<HostelActionReportTO> hostelActionReportList;
	private List<HostelActionReportTO> hostelPrintList;
	
	public List<HostelActionReportTO> getHostelPrintList() {
		return hostelPrintList;
	}
	public void setHostelPrintList(List<HostelActionReportTO> hostelPrintList) {
		this.hostelPrintList = hostelPrintList;
	}
	public List<HostelActionReportTO> getHostelActionReportList() {
		return hostelActionReportList;
	}
	public void setHostelActionReportList(
			List<HostelActionReportTO> hostelActionReportList) {
		this.hostelActionReportList = hostelActionReportList;
	}
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	
	
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}	
	
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void clear(){
		super.clear();
		this.startDate=null;
		this.endDate=null;
		this.actionId=null;
	}
}
