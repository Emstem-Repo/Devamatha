package com.kp.cms.forms.attendance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.AttendanceEntryDay;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;

import com.kp.cms.to.attendance.AttendanceEntryDayTO;
import com.kp.cms.utilities.CommonUtil;

public class AttendanceEntryDayForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	//private String date;
	private String day;
	private String id;
	private String DupId;
	private String orgId;
	private int reactivateid;
	private String orgDay;
	private String orgDate;
	private AttendanceEntryDay attendanceEntryDay;
	private List<AttendanceEntryDayTO> attendanceEntryDayList;

	public String getDupId() {
		return DupId;
	}
	public void setDupId(String dupId) {
		DupId = dupId;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<AttendanceEntryDayTO> getAttendanceEntryDayList() {
		return attendanceEntryDayList;
	}
	public void setAttendanceEntryDayList(
			List<AttendanceEntryDayTO> attendanceEntryDayList) {
		this.attendanceEntryDayList = attendanceEntryDayList;
	}
	public int getReactivateid() {
		return reactivateid;
	}
	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}
	public String getOrgDay() {
		return orgDay;
	}
	public void setOrgDay(String orgDay) {
		this.orgDay = orgDay;
	}
	public String getOrgDate() {
		return orgDate;
	}
	public void setOrgDate(String orgDate) {
		this.orgDate = orgDate;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public AttendanceEntryDay getAttendanceEntryDay() {
		return attendanceEntryDay;
	}
	public void setAttendanceEntryDay(AttendanceEntryDay attendanceEntryDay) {
		this.attendanceEntryDay = attendanceEntryDay;
	}
	/*public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}*/
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.day=null;
	
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		/*boolean invalidDate = false;
		if(this.baseactionform.date!=null && !StringUtils.isEmpty(this.date)&& !CommonUtil.isValidDate(this.date)){
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			invalidDate = true;
		}	*/
		
		

		return actionErrors;
	}
}
