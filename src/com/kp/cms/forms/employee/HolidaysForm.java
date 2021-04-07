package com.kp.cms.forms.employee;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.EmpAcademicHolidaysDates;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.HolidaysTO;

public class HolidaysForm extends BaseActionForm{
	
	private int id;
	private String empTypeId;
	private String startDate;
	private String endDate;
	private String holiday;
	private List<HolidaysTO> empTypeList;
	private List<HolidaysTO> holidaysTOsList;
	private String createdBy;
	private Date createdDate;
	private int oldId;
	private int datesId[];
	private Set<Integer> datesIdSet;
	private String[] datesArray;
	private HolidaysTO holidaysTO;
	private List<EmpAcademicHolidaysDates>datesToBeDeleted;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getHoliday() {
		return holiday;
	}
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
	public List<HolidaysTO> getEmpTypeList() {
		return empTypeList;
	}
	public void setEmpTypeList(List<HolidaysTO> empTypeList) {
		this.empTypeList = empTypeList;
	}
	
	public List<HolidaysTO> getHolidaysTOsList() {
		return holidaysTOsList;
	}
	public void setHolidaysTOsList(List<HolidaysTO> holidaysTOsList) {
		this.holidaysTOsList = holidaysTOsList;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getOldId() {
		return oldId;
	}
	public void setOldId(int oldId) {
		this.oldId = oldId;
	}
	public int[] getDatesId() {
		return datesId;
	}
	public void setDatesId(int[] datesId) {
		this.datesId = datesId;
	}
	public Set<Integer> getDatesIdSet() {
		return datesIdSet;
	}
	public void setDatesIdSet(Set<Integer> datesIdSet) {
		this.datesIdSet = datesIdSet;
	}
	public String[] getDatesArray() {
		return datesArray;
	}
	public void setDatesArray(String[] datesArray) {
		this.datesArray = datesArray;
	}
	public HolidaysTO getHolidaysTO() {
		return holidaysTO;
	}
	public void setHolidaysTO(HolidaysTO holidaysTO) {
		this.holidaysTO = holidaysTO;
	}
	public void resetFields() {
		this.setEmpTypeId(null);
		this.setStartDate(null);
		this.setEndDate(null);
		this.setAcademicYear(null);
		this.setHoliday(null);
		this.id=0;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<EmpAcademicHolidaysDates> getDatesToBeDeleted() {
		return datesToBeDeleted;
	}
	public void setDatesToBeDeleted(List<EmpAcademicHolidaysDates> datesToBeDeleted) {
		this.datesToBeDeleted = datesToBeDeleted;
	}
	public String getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(String empTypeId) {
		this.empTypeId = empTypeId;
	}
	
	
}
