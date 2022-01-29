package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.AttendanceMarkAndPercentageTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;

public class AttendanceMarksEntryForm extends BaseActionForm{
	
	private int id=0;

    private String[] selectedCourses;
    private String accYear;
    private String schemeNumber;

	private List<ExamCourseUtilTO> listExamCourseUtilTO;
    private List<AttendanceMarkAndPercentageTO> markandPercentageList;
    private List<AttendanceMarkAndPercentageTO> attendaceList;
    private Map<Integer, Integer> schemeMap;
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {
		this.listExamCourseUtilTO = null;
		this.selectedCourses = null;
		this.accYear = null;
		this.schemeNumber = null;
		this.markandPercentageList=  null;
		this.schemeMap = null;
		this.attendaceList = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(String[] selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public String getAccYear() {
		return accYear;
	}

	public void setAccYear(String accYear) {
		this.accYear = accYear;
	}

	public String getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	public List<ExamCourseUtilTO> getListExamCourseUtilTO() {
		return listExamCourseUtilTO;
	}

	public void setListExamCourseUtilTO(List<ExamCourseUtilTO> listExamCourseUtilTO) {
		this.listExamCourseUtilTO = listExamCourseUtilTO;
	}

	public List<AttendanceMarkAndPercentageTO> getMarkandPercentageList() {
		return markandPercentageList;
	}

	public void setMarkandPercentageList(
			List<AttendanceMarkAndPercentageTO> markandPercentageList) {
		this.markandPercentageList = markandPercentageList;
	}

	public Map<Integer, Integer> getSchemeMap() {
		return schemeMap;
	}

	public void setSchemeMap(Map<Integer, Integer> schemeMap) {
		this.schemeMap = schemeMap;
	}

	public List<AttendanceMarkAndPercentageTO> getAttendaceList() {
		return attendaceList;
	}

	public void setAttendaceList(List<AttendanceMarkAndPercentageTO> attendaceList) {
		this.attendaceList = attendaceList;
	}
    
	
	
	
}
