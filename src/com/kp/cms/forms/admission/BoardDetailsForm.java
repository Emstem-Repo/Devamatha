package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.BoardDetailsTO;

public class BoardDetailsForm extends BaseActionForm {
	
	private String registerNo;
	private String className;
	List<BoardDetailsTO> boardList;
	private Map<Integer,Integer> yearMap;
	private String years;
	private String courseName;
	private Map<Integer,String> programMap;
	private String programName;

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<BoardDetailsTO> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<BoardDetailsTO> boardList) {
		this.boardList = boardList;
	}

	public void resetFields(){
		this.registerNo=null;
		super.setClassId(null);
		super.setYear(null);
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

	public Map<Integer, Integer> getYearMap() {
		return yearMap;
	}

	public void setYearMap(Map<Integer, Integer> yearMap) {
		this.yearMap = yearMap;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Map<Integer, String> getProgramMap() {
		return programMap;
	}

	public void setProgramMap(Map<Integer, String> programMap) {
		this.programMap = programMap;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
}
