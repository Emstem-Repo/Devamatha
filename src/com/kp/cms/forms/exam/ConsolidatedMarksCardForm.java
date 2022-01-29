package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTO;

public class ConsolidatedMarksCardForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private String[] coursesId;
	private List<ProgramTO> programList;
	private Map<Integer,String> courseMap;
	
	public String[] getCoursesId() {
		return coursesId;
	}
	public void setCoursesId(String[] coursesId) {
		this.coursesId = coursesId;
	}
	
	public List<ProgramTO> getProgramList() {
		return programList;
	}
	public void setProgramList(List<ProgramTO> programList) {
		this.programList = programList;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	/**
	 * 
	 */
	public void resetFields() {
//		super.setCourseId(null);
		super.setProgramId(null);
		this.coursesId=null;
		super.setYear(null);
		this.programList=null;
		this.courseMap=null;
	}
}
