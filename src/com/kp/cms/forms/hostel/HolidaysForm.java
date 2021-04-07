package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EvaStudentFeedbackOpenConnectionTo;
import com.kp.cms.to.hostel.HostelHolidaysTo;

public class HolidaysForm extends BaseActionForm{
	private int id;
	Map<String,String> programMap;
	private String[] programsId;
	private String[] coursesId;
	private String holidaysFrom;
	private String holidaysTo;
	private String holidaysFromSession;
	private String holidaysToSession;
	List<HostelHolidaysTo> hostelHolidaysTo;
	private String holidaysOrVacation;
	private String description;
	private Map<Integer,String> hostelMap;
	private Map<Integer,String> blockMap;
	private Map<Integer,String> unitMap;
	
	
	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}
	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}
	public Map<Integer, String> getUnitMap() {
		return unitMap;
	}
	public void setUnitMap(Map<Integer, String> unitMap) {
		this.unitMap = unitMap;
	}
	public Map<Integer, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<Integer, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHolidaysOrVacation() {
		return holidaysOrVacation;
	}
	public void setHolidaysOrVacation(String holidaysOrVacation) {
		this.holidaysOrVacation = holidaysOrVacation;
	}
	private boolean flag;
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<HostelHolidaysTo> getHostelHolidaysTo() {
		return hostelHolidaysTo;
	}
	public void setHostelHolidaysTo(List<HostelHolidaysTo> hostelHolidaysTo) {
		this.hostelHolidaysTo = hostelHolidaysTo;
	}
	public String[] getProgramsId() {
		return programsId;
	}
	public void setProgramsId(String[] programsId) {
		this.programsId = programsId;
	}
	public String[] getCoursesId() {
		return coursesId;
	}
	public void setCoursesId(String[] coursesId) {
		this.coursesId = coursesId;
	}
	public String getHolidaysFrom() {
		return holidaysFrom;
	}
	public void setHolidaysFrom(String holidaysFrom) {
		this.holidaysFrom = holidaysFrom;
	}
	public String getHolidaysTo() {
		return holidaysTo;
	}
	public void setHolidaysTo(String holidaysTo) {
		this.holidaysTo = holidaysTo;
	}
	public String getHolidaysFromSession() {
		return holidaysFromSession;
	}
	public void setHolidaysFromSession(String holidaysFromSession) {
		this.holidaysFromSession = holidaysFromSession;
	}
	public String getHolidaysToSession() {
		return holidaysToSession;
	}
	public void setHolidaysToSession(String holidaysToSession) {
		this.holidaysToSession = holidaysToSession;
	}
	public Map<String, String> getProgramMap() {
		return programMap;
	}
	public void setProgramMap(Map<String, String> programMap) {
		this.programMap = programMap;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
			String formName = request.getParameter(CMSConstants.FORMNAME);
			ActionErrors actionErrors = super.validate(mapping, request, formName);
			return actionErrors;
		}
	public void clear() {
		this.programsId = null;
		this.holidaysFrom = null;
		this.holidaysFromSession = null;
		this.holidaysTo = null;
		this.holidaysToSession = null;
		this.coursesId=null;
		this.flag=false;
		this.holidaysOrVacation=null;
		this.description=null;
	}
}
