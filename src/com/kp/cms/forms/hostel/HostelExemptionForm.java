package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelExemptionTo;

public class HostelExemptionForm extends BaseActionForm{
	private String holidaysFrom;
	private String holidaysTo;
	private String holidaysFromSession;
	private String holidaysToSession;
	private Map<Integer,String> hostelMap;
	private Map<Integer,String> courseMap;
	private Map<Integer,String> classMap;
	private Map<Integer,String> spacialMap;
	private String semesterNo;
	private String registerNo;
	private String reason;
	private List<HostelExemptionTo> hlExemptionList;
	private String hostelId;
	private Map<Integer,String> blockMap;
	private Map<Integer,String> unitMap;
	private String courseId;
	private String classId;
	private String spacialId;
	private Map<Integer,Integer> alreadySavedData;
	private Map<Integer,Integer> hlExpIdMap;
	private Map<Integer,Integer> inActveHlExpIdMap;
	private boolean flag;
	private boolean isStudentExist;
	private String focus;
	
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
	public Map<Integer, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<Integer, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public String getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(String semesterNo) {
		this.semesterNo = semesterNo;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<HostelExemptionTo> getHlExemptionList() {
		return hlExemptionList;
	}
	public void setHlExemptionList(List<HostelExemptionTo> hlExemptionList) {
		this.hlExemptionList = hlExemptionList;
	}
	public void reset(){
		this.holidaysFrom=null;
		this.holidaysTo=null;
		this.holidaysFromSession=null;
		this.holidaysToSession=null;
		this.hostelMap=null;
		this.courseMap=null;
		this.hlExemptionList=null;
		this.hostelId=null;
		this.semesterNo=null;
		this.registerNo=null;
		this.reason=null;
		this.blockMap=null;
		this.unitMap=null;
		this.courseId=null;
		this.alreadySavedData=null;
		this.hlExpIdMap=null;
		this.inActveHlExpIdMap=null;
		this.flag=false;
		this.isStudentExist=false;
		this.classMap=null;
		this.spacialMap=null;
		this.classId=null;
		this.spacialId=null;
		this.focus=null;
	}
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
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
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public Map<Integer, Integer> getAlreadySavedData() {
		return alreadySavedData;
	}
	public void setAlreadySavedData(Map<Integer, Integer> alreadySavedData) {
		this.alreadySavedData = alreadySavedData;
	}
	public Map<Integer, Integer> getHlExpIdMap() {
		return hlExpIdMap;
	}
	public void setHlExpIdMap(Map<Integer, Integer> hlExpIdMap) {
		this.hlExpIdMap = hlExpIdMap;
	}
	public Map<Integer, Integer> getInActveHlExpIdMap() {
		return inActveHlExpIdMap;
	}
	public void setInActveHlExpIdMap(Map<Integer, Integer> inActveHlExpIdMap) {
		this.inActveHlExpIdMap = inActveHlExpIdMap;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public boolean isStudentExist() {
		return isStudentExist;
	}
	public void setStudentExist(boolean isStudentExist) {
		this.isStudentExist = isStudentExist;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public Map<Integer, String> getSpacialMap() {
		return spacialMap;
	}
	public void setSpacialMap(Map<Integer, String> spacialMap) {
		this.spacialMap = spacialMap;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getSpacialId() {
		return spacialId;
	}
	public void setSpacialId(String spacialId) {
		this.spacialId = spacialId;
	}
	public String getFocus() {
		return focus;
	}
	public void setFocus(String focus) {
		this.focus = focus;
	}
	
}
