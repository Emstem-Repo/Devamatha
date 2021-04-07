package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.StudentsClassGroupTo;

public class StudentClassGroupForm extends BaseActionForm {

	private String campusName;
	private String selectedClasses;
	private String[] unSelectesClasses;
	private String classValues;
	private String classGroup;
	private Map<Integer, String> classGroupMap;
	private Map<Integer, String> selectedClassMap;
	private Map<Integer, String> locationMap;
	private String method;
	private int halfLength;
	private List<StudentsClassGroupTo> classGroupToList;
	private boolean disable;
	private boolean disable2;
	private boolean flag;
	private List<StudentsClassGroupTo> groupClassesList;
	private String groupId;
	
	
	

	
	
	
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getSelectedClasses() {
		return selectedClasses;
	}
	public void setSelectedClasses(String selectedClasses) {
		this.selectedClasses = selectedClasses;
	}
	public String[] getUnSelectesClasses() {
		return unSelectesClasses;
	}
	public void setUnSelectesClasses(String[] unSelectesClasses) {
		this.unSelectesClasses = unSelectesClasses;
	}
	public String getClassValues() {
		return classValues;
	}
	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}
	
	public void reset(){
		super.setAcademicYear(null);
		super.setSchemeNo(null);
		super.setClassMap(null);
		this.disable=false;
		this.disable2=false;
		this.flag=false;
		this.campusName=null;
		this.classGroup=null;
		this.classValues=null;
		this.locationMap=null;
		this.selectedClassMap=null;
		this.classGroupMap=null;
		this.classGroupToList=null;
		this.selectedClasses=null;
		this.halfLength=0;
		this.groupClassesList=null;
		this.groupId=null;
	}
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public String getClassGroup() {
		return classGroup;
	}
	public void setClassGroup(String classGroup) {
		this.classGroup = classGroup;
	}
	public Map<Integer, String> getClassGroupMap() {
		return classGroupMap;
	}
	public void setClassGroupMap(Map<Integer, String> classGroupMap) {
		this.classGroupMap = classGroupMap;
	}
	public Map<Integer, String> getSelectedClassMap() {
		return selectedClassMap;
	}
	public void setSelectedClassMap(Map<Integer, String> selectedClassMap) {
		this.selectedClassMap = selectedClassMap;
	}
	public Map<Integer, String> getLocationMap() {
		return locationMap;
	}
	public void setLocationMap(Map<Integer, String> locationMap) {
		this.locationMap = locationMap;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getHalfLength() {
		return halfLength;
	}
	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}
	public List<StudentsClassGroupTo> getClassGroupToList() {
		return classGroupToList;
	}
	public void setClassGroupToList(List<StudentsClassGroupTo> classGroupToList) {
		this.classGroupToList = classGroupToList;
	}
	public boolean isDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	public boolean isDisable2() {
		return disable2;
	}
	public void setDisable2(boolean disable2) {
		this.disable2 = disable2;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<StudentsClassGroupTo> getGroupClassesList() {
		return groupClassesList;
	}
	public void setGroupClassesList(List<StudentsClassGroupTo> groupClassesList) {
		this.groupClassesList = groupClassesList;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
