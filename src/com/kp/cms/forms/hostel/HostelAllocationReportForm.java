package com.kp.cms.forms.hostel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelAllocationReportTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelAllocationReportForm  extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromDate;
	private String toDate;
	private String allocationDate;
	private String hostelName;
	private String roomType;
	private String allocatedRoom;
	private List<HostelAllocationReportTO> allocationList;
	
	public List<HostelAllocationReportTO> getAllocationList() {
		return allocationList;
	}
	public void setAllocationList(List<HostelAllocationReportTO> allocationList) {
		this.allocationList = allocationList;
	}
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
	public String getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getAllocatedRoom() {
		return allocatedRoom;
	}
	public void setAllocatedRoom(String allocatedRoom) {
		this.allocatedRoom = allocatedRoom;
	}
	public void resetFields(ActionMapping mapping, HttpServletRequest request) {
	this.fromDate=null;
	this.toDate=null;
	}
	/*public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	*/
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		
		String formName = request.getParameter("formName");
		//String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		if(actionErrors.isEmpty()) {
			Date startDate = CommonUtil.ConvertStringToDate(this.fromDate);
			Date endDate = CommonUtil.ConvertStringToDate(this.toDate);
			Date curdate = new Date();
			if ((startDate.compareTo(curdate) == 1) ||(endDate.compareTo(curdate) == 1)) {
				actionErrors.add("error", new ActionError(CMSConstants.FEE_NO_FUTURE_DATE_));
			}
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				actionErrors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
			}
		}
		
	return actionErrors;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
