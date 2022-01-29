package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AttendanceTypeTO;

public class AttendanceTypeForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attendanceType;
	private String[] mandatory;
	private boolean defaultValue;
	private String method;
	private int id;
	private String oldName;
	private int mandatoryId[];
	private String temp;
	private Set<Integer> mandatoryIdSet;
	private AttendanceTypeTO attendanceTypeTO;
	private String selectedIndex;
	private boolean oldDefaultValue;

	private List<AttendanceTypeTO> attendanceTypeList;

	public String getAttendanceType() {
		return attendanceType;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}


	public boolean isOldDefaultValue() {
		return oldDefaultValue;
	}

	public void setOldDefaultValue(boolean oldDefaultValue) {
		this.oldDefaultValue = oldDefaultValue;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public int[] getMandatoryId() {
		return mandatoryId;
	}

	public void setMandatoryId(int[] mandatoryId) {
		this.mandatoryId = mandatoryId;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public void setAttendanceType(String attendanceType) {
		this.attendanceType = attendanceType;
	}

	public String[] getMandatory() {
		return mandatory;
	}

	public void setMandatory(String[] mandatory) {
		this.mandatory = mandatory;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<AttendanceTypeTO> getAttendanceTypeList() {
		return attendanceTypeList;
	}

	public void setAttendanceTypeList(List<AttendanceTypeTO> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}

	public Set<Integer> getMandatoryIdSet() {
		return mandatoryIdSet;
	}

	public void setMandatoryIdSet(Set<Integer> mandatoryIdSet) {
		this.mandatoryIdSet = mandatoryIdSet;
	}

	public AttendanceTypeTO getAttendanceTypeTO() {
		return attendanceTypeTO;
	}

	public void setAttendanceTypeTO(AttendanceTypeTO attendanceTypeTO) {
		this.attendanceTypeTO = attendanceTypeTO;
	}

	public String getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	/**
	 * This method is used to validate the fields
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		if(selectedIndex != null && selectedIndex.equals("-1")){
			this.mandatory = null;
		}
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	/**
	 * This method is used to reset the formbean properties
	 */
	public void clear(){
		this.attendanceType=null;
		this.mandatory=null;
		this.defaultValue = false;
	}
}
