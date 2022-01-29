package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelVisitorsInfoTo;

public class HostelVisitorsInfoForm extends BaseActionForm{
	private int id;
	Map<String,String> hostelMap;
	private String visitorName;
	private String contactNo;
	private String inHours;
	private String inMins;
	private String outHours;
	private String outMins;
	List<HostelVisitorsInfoTo> hostelVisitorsInfoTosList;
	Map<String,String> hourMap;
	Map<String,String> minMap;
	private String isHlTransaction;
	private String rgNoFromHlTransaction;
	private String year;
	private String studentName;
	private String studentCourse;
	private String studentBlock;
	private String studentUnit;
	private String studentRoomNo;
	private String studentBedNo;
	
	public Map<String, String> getMinMap() {
		return minMap;
	}
	public void setMinMap(Map<String, String> minMap) {
		this.minMap = minMap;
	}
	public Map<String, String> getHourMap() {
		return hourMap;
	}
	public void setHourMap(Map<String, String> hourMap) {
		this.hourMap = hourMap;
	}
	public List<HostelVisitorsInfoTo> getHostelVisitorsInfoTosList() {
		return hostelVisitorsInfoTosList;
	}
	public void setHostelVisitorsInfoTosList(
			List<HostelVisitorsInfoTo> hostelVisitorsInfoTosList) {
		this.hostelVisitorsInfoTosList = hostelVisitorsInfoTosList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<String, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<String, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getInHours() {
		return inHours;
	}
	public void setInHours(String inHours) {
		this.inHours = inHours;
	}
	public String getInMins() {
		return inMins;
	}
	public void setInMins(String inMins) {
		this.inMins = inMins;
	}
	public String getOutHours() {
		return outHours;
	}
	public void setOutHours(String outHours) {
		this.outHours = outHours;
	}
	public String getOutMins() {
		return outMins;
	}
	public void setOutMins(String outMins) {
		this.outMins = outMins;
	}
	/**
	 * form validation
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void reset(){
		this.visitorName=null;
		this.contactNo=null;
		this.inHours=null;
		this.inMins=null;
		this.outHours=null;
		this.outMins=null;
		
	}
	public String getIsHlTransaction() {
		return isHlTransaction;
	}
	public void setIsHlTransaction(String isHlTransaction) {
		this.isHlTransaction = isHlTransaction;
	}
	public String getRgNoFromHlTransaction() {
		return rgNoFromHlTransaction;
	}
	public void setRgNoFromHlTransaction(String rgNoFromHlTransaction) {
		this.rgNoFromHlTransaction = rgNoFromHlTransaction;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentCourse() {
		return studentCourse;
	}
	public void setStudentCourse(String studentCourse) {
		this.studentCourse = studentCourse;
	}
	public String getStudentBlock() {
		return studentBlock;
	}
	public void setStudentBlock(String studentBlock) {
		this.studentBlock = studentBlock;
	}
	public String getStudentUnit() {
		return studentUnit;
	}
	public void setStudentUnit(String studentUnit) {
		this.studentUnit = studentUnit;
	}
	public String getStudentRoomNo() {
		return studentRoomNo;
	}
	public void setStudentRoomNo(String studentRoomNo) {
		this.studentRoomNo = studentRoomNo;
	}
	public String getStudentBedNo() {
		return studentBedNo;
	}
	public void setStudentBedNo(String studentBedNo) {
		this.studentBedNo = studentBedNo;
	}
	
}
