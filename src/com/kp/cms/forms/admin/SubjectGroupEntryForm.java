package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectGroupSubjectsTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.KeyValueTO;

public class SubjectGroupEntryForm extends BaseActionForm {
	
	private List<ProgramTypeTO> programTypeList;
	private String method;
	private List<SubjectGroupTO> subjectGroupList;
	private List<SubjectTO> subjectList;
	private int subjectGroupEntryId;
	private String subjectGroupName;
	private Set<SubjectGroupSubjectsTO> subjectGroupSubjectSet;
	private Map<Integer,String> subjectsMap;
	private Map<Integer,String> tempMap;
	private String[] selectedSubjects;
	private String[] oldselectedSubjects;
	private String[] groupid;
	private SubjectGroupTO subjectGroupTO;
	private int selectedSubjectGroupEntryId;
	private int subjectGroupSubjectsId;
	private String[] movedSubjectsTORight;
	private Map<Integer,String> selectedSubjectsMap;
	private String selectedIndex;
	private int selectedSubjectGroupId;
	private Map<Integer, String> mapIds;
	private String searchSubLeft;
	private Integer secondLanguageId;
	private String commonSubjectGroup;
	
	private List<KeyValueTO> listSecondLanguage ;
	private Map<Integer,Integer> groupMapId;
	private Boolean displayList;
	private String programTypeId;
	private String programId;
	
	
	public void clearAll(){
		super.clear();
		this.subjectGroupName = null;
		this.selectedSubjects = null;
		this.movedSubjectsTORight = null;
		this.selectedSubjectsMap = null;
		this.searchSubLeft = null;
		this.secondLanguageId=null;
		this.commonSubjectGroup=null;
		this.groupMapId=null;
	}
	
	public Integer getSecondLanguageId() {
		return secondLanguageId;
	}

	public void setSecondLanguageId(Integer secondLanguageId) {
		this.secondLanguageId = secondLanguageId;
	}

	public String getCommonSubjectGroup() {
		return commonSubjectGroup;
	}

	public void setCommonSubjectGroup(String commonSubjectGroup) {
		this.commonSubjectGroup = commonSubjectGroup;
	}

	public Map<Integer, String> getMapIds() {
		return mapIds;
	}

	public void setMapIds(Map<Integer, String> mapIds) {
		this.mapIds = mapIds;
	}

	public String getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String[] getSelectedSubjects() {
		return selectedSubjects;
	}

	public void setSelectedSubjects(String[] selectedSubjects) {
		this.selectedSubjects = selectedSubjects;
	}

	public Set<SubjectGroupSubjectsTO> getSubjectGroupSubjectSet() {
		return subjectGroupSubjectSet;
	}

	public void setSubjectGroupSubjectSet(
			Set<SubjectGroupSubjectsTO> subjectGroupSubjectSet) {
		this.subjectGroupSubjectSet = subjectGroupSubjectSet;
	}

	public String getSubjectGroupName() {
		return subjectGroupName;
	}

	public void setSubjectGroupName(String subjectGroupName) {
		this.subjectGroupName = subjectGroupName;
	}

	public int getSubjectGroupEntryId() {
		return subjectGroupEntryId;
	}

	public void setSubjectGroupEntryId(int subjectGroupEntryId) {
		this.subjectGroupEntryId = subjectGroupEntryId;
	}

	public List<SubjectTO> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<SubjectTO> subjectList) {
		this.subjectList = subjectList;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<Integer, String> getTempMap() {
		return tempMap;
	}

	public void setTempMap(Map<Integer, String> tempMap) {
		this.tempMap = tempMap;
	}

	public List<SubjectGroupTO> getSubjectGroupList() {
		return subjectGroupList;
	}

	public void setSubjectGroupList(List<SubjectGroupTO> subjectGroupList) {
		this.subjectGroupList = subjectGroupList;
	}
	
	
	public SubjectGroupTO getSubjectGroupTO() {
		return subjectGroupTO;
	}

	public void setSubjectGroupTO(SubjectGroupTO subjectGroupTO) {
		this.subjectGroupTO = subjectGroupTO;
	}
	
	public Map<Integer, String> getSubjectsMap() {
		return subjectsMap;
	}

	public void setSubjectsMap(Map<Integer, String> subjectsMap) {
		this.subjectsMap = subjectsMap;
	}

	public int getSelectedSubjectGroupEntryId() {
		return selectedSubjectGroupEntryId;
	}

	public void setSelectedSubjectGroupEntryId(int selectedSubjectGroupEntryId) {
		this.selectedSubjectGroupEntryId = selectedSubjectGroupEntryId;
	}

	public int getSubjectGroupSubjectsId() {
		return subjectGroupSubjectsId;
	}

	public void setSubjectGroupSubjectsId(int subjectGroupSubjectsId) {
		this.subjectGroupSubjectsId = subjectGroupSubjectsId;
	}
	
	
	public String[] getGroupid() {
		return groupid;
	}

	public void setGroupid(String[] groupid) {
		this.groupid = groupid;
	}
	

	public String[] getOldselectedSubjects() {
		return oldselectedSubjects;
	}

	public void setOldselectedSubjects(String[] oldselectedSubjects) {
		this.oldselectedSubjects = oldselectedSubjects;
	}

	public String[] getMovedSubjectsTORight() {
		return movedSubjectsTORight;
	}

	public Map<Integer, String> getSelectedSubjectsMap() {
		return selectedSubjectsMap;
	}

	public void setSelectedSubjectsMap(Map<Integer, String> selectedSubjectsMap) {
		this.selectedSubjectsMap = selectedSubjectsMap;
	}

	public void setMovedSubjectsTORight(String[] movedSubjectsTORight) {
		this.movedSubjectsTORight = movedSubjectsTORight;
	}

	public Integer getSelectedSubjectGroupId() {
		return selectedSubjectGroupId;
	}

	public void setSelectedSubjectGroupId(Integer selectedSubjectGroupId) {
		this.selectedSubjectGroupId = selectedSubjectGroupId;
	}

	public String getSearchSubLeft() {
		return searchSubLeft;
	}

	public void setSearchSubLeft(String searchSubLeft) {
		this.searchSubLeft = searchSubLeft;
	}


	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		if(selectedIndex != null && selectedIndex.equals("-1")){
			this.movedSubjectsTORight = null;
			this.selectedSubjectsMap = null;
		}
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public List<KeyValueTO> getListSecondLanguage() {
		return listSecondLanguage;
	}

	public void setListSecondLanguage(List<KeyValueTO> listSecondLanguage) {
		this.listSecondLanguage = listSecondLanguage;
	}

	public Map<Integer, Integer> getGroupMapId() {
		return groupMapId;
	}

	public void setGroupMapId(Map<Integer, Integer> groupMapId) {
		this.groupMapId = groupMapId;
	}

	public Boolean getDisplayList() {
		return displayList;
	}

	public void setDisplayList(Boolean displayList) {
		this.displayList = displayList;
	}

	public String getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
}