package com.kp.cms.forms.studentfeedback;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentfeedback.FacultyGradesTO;

public class FacultyGradesForm extends BaseActionForm {
	
	private String grade;
	private String scaleFrom;
	private String scaleTo;
	private int id;
	private List<FacultyGradesTO>facultyList;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void clearAll()
	{
		this.grade=null;
		this.scaleFrom=null;
		this.scaleTo=null;

		}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String orgGrade) {
		this.grade = orgGrade;
	}

	public String getScaleFrom() {
		return scaleFrom;
	}

	public void setScaleFrom(String scaleFrom) {
		this.scaleFrom = scaleFrom;
	}

	public String getScaleTo() {
		return scaleTo;
	}

	public void setScaleTo(String scaleTo) {
		this.scaleTo = scaleTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<FacultyGradesTO> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(List<FacultyGradesTO> facultyList) {
		this.facultyList = facultyList;
	}
	
}
