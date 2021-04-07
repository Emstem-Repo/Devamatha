package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamAssignExaminerTO;
import com.kp.cms.to.exam.InvDutyDetailsTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamAssignExaminerForm extends BaseActionForm {

	private int examNameId;
	private String date;
	private String hr;
	private String min;
	private int invigilatorId;
	private String invigilatorName;
	private String examiner;
	private String roomNo;
	private String nonEligible = "off";
	private String displayOrder;
	private String mode = "";
	private int invDutyDetailsListSize;
	private int invigilatorSize;
	private String listValues;
	private String listValuesDummy;
	private String actionType;
	private String checkValidation;
	private String exName;
	private String examType;
	private HashMap<Integer, String> examTypeList;
	private Map<Integer,String> examNameMap;
	
	public String getNonEligible() {
		return nonEligible;
	}

	public void setNonEligible(String nonEligible) {
		this.nonEligible = nonEligible;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	List<KeyValueTO> examNameList;
	List<KeyValueTO> invigilatorList;
	private List<InvDutyDetailsTO> invDutyListMain;
	private List<ExamAssignExaminerTO> listAssignExaminer;
	private List<String> listinvDutiesHeadder;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {
		this.date = "";
		this.invigilatorId = 0;
		this.examType=null;
		this.examTypeList=null;
		this.examNameMap=null;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public int getInvigilatorId() {
		return invigilatorId;
	}

	public void setInvigilatorId(int invigilatorId) {
		this.invigilatorId = invigilatorId;
	}

	public String getInvigilatorName() {
		return invigilatorName;
	}

	public void setInvigilatorName(String invigilatorName) {
		this.invigilatorName = invigilatorName;
	}

	public List<KeyValueTO> getExamNameList() {
		return examNameList;
	}

	public void setExamNameList(List<KeyValueTO> examNameList) {
		this.examNameList = examNameList;
	}

	public List<KeyValueTO> getInvigilatorList() {
		return invigilatorList;
	}

	public void setInvigilatorList(List<KeyValueTO> invigilatorList) {
		this.invigilatorList = invigilatorList;
	}

	public void setExamNameId(int examNameId) {
		this.examNameId = examNameId;
	}

	public int getExamNameId() {
		return examNameId;
	}

	public String getListValues() {
		return listValues;
	}

	public void setListValues(String listValues) {
		this.listValues = listValues;
	}

	public void setInvDutyDetailsListSize(int invDutyDetailsListSize) {
		this.invDutyDetailsListSize = invDutyDetailsListSize;
	}

	public int getInvDutyDetailsListSize() {
		return invDutyDetailsListSize;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}

	public String getExaminer() {
		return examiner;
	}

	public List<InvDutyDetailsTO> getInvDutyListMain() {
		return invDutyListMain;
	}

	public void setInvDutyListMain(List<InvDutyDetailsTO> invDutyListMain) {
		this.invDutyListMain = invDutyListMain;
	}

	// public List<InvDutyDetailsTO> getInvDutyList() {
	// return invDutyList;
	// }
	// public void setInvDutyList(List<InvDutyDetailsTO> invDutyList) {
	// this.invDutyList = invDutyList;
	// }
	public List<String> getListinvDutiesHeadder() {
		return listinvDutiesHeadder;
	}

	public void setListinvDutiesHeadder(List<String> listinvDutiesHeadder) {
		this.listinvDutiesHeadder = listinvDutiesHeadder;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getInvigilatorSize() {
		return invigilatorSize;
	}

	public void setInvigilatorSize(int invigilatorSize) {
		this.invigilatorSize = invigilatorSize;
	}

	public List<ExamAssignExaminerTO> getListAssignExaminer() {
		return listAssignExaminer;
	}

	public void setListAssignExaminer(
			List<ExamAssignExaminerTO> listAssignExaminer) {
		this.listAssignExaminer = listAssignExaminer;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActionType() {
		return actionType;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

	public void setListValuesDummy(String listValuesDummy) {
		this.listValuesDummy = listValuesDummy;
	}

	public String getListValuesDummy() {
		return listValuesDummy;
	}

	public void setCheckValidation(String checkValidation) {
		this.checkValidation = checkValidation;
	}

	public String getCheckValidation() {
		return checkValidation;
	}

	public String getExName() {
		return exName;
	}

	public void setExName(String exName) {
		this.exName = exName;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public HashMap<Integer, String> getExamTypeList() {
		return examTypeList;
	}

	public void setExamTypeList(HashMap<Integer, String> examTypeList) {
		this.examTypeList = examTypeList;
	}

	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}

	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}

}
