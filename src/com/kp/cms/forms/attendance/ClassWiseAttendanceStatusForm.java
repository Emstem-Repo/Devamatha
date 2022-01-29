package com.kp.cms.forms.attendance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.utilities.CommonUtil;

public class ClassWiseAttendanceStatusForm extends BaseActionForm {

	private String classes;
	private String fromDate;
	private String toDate;
	Map<Integer, String> semisterMap;
	private Map<String, String> subMap;

	

	private String oprationMode;
	private List<String> periodList;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		boolean invalidDate = false;
		if (this.fromDate != null && !StringUtils.isEmpty(this.fromDate)
				&& !CommonUtil.isValidDate(this.fromDate)) {
			actionErrors.add("errors", new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			invalidDate = true;
		}
		if (this.fromDate != null && this.fromDate.length() != 0
				&& !invalidDate) {
			String formattedString = CommonUtil.ConvertStringToDateFormat(
					this.fromDate, "dd/MM/yyyy", "MM/dd/yyyy");
			Date date = new Date(formattedString);
			Date curdate = new Date();
			if (date.compareTo(curdate) == 1) {
				actionErrors.add("errors", new ActionMessage(
						CMSConstants.FEE_NO_FUTURE_DATE_));
			}
		}
		if (this.toDate != null && !StringUtils.isEmpty(this.toDate)
				&& !CommonUtil.isValidDate(this.toDate)) {
			actionErrors.add("errors", new ActionError(
					CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			invalidDate = true;
		}
		if (this.toDate != null && this.toDate.length() != 0 && !invalidDate) {
			String formattedString = CommonUtil.ConvertStringToDateFormat(
					this.toDate, "dd/MM/yyyy", "MM/dd/yyyy");
			Date date = new Date(formattedString);
			Date curdate = new Date();
			if (date.compareTo(curdate) == 1) {
				actionErrors.add("errors", new ActionMessage(
						CMSConstants.FEE_NO_FUTURE_DATE_));
			}
		}
		return actionErrors;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
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

	public Map<Integer, String> getSemisterMap() {
		return semisterMap;
	}

	public void setSemisterMap(Map<Integer, String> semisterMap) {
		this.semisterMap = semisterMap;
	}

	public String getOprationMode() {
		return oprationMode;
	}

	public void setOprationMode(String oprationMode) {
		this.oprationMode = oprationMode;
	}

	public void resetFields() {
		super.setSemister(null);
		this.classes = null;
		this.fromDate = null;
		this.toDate = null;

	}

	public List<String> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<String> periodList) {
		this.periodList = periodList;
	}
	public Map<String, String> getSubMap() {
		return subMap;
	}

	public void setSubMap(Map<String, String> subMap) {
		this.subMap = subMap;
	}

}
