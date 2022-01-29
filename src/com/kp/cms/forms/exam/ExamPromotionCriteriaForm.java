package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamPromotionCriteriaTO;

@SuppressWarnings("serial")
public class ExamPromotionCriteriaForm extends BaseActionForm {

	/**
	 * Used in Exam Module
	 */
	private int id;
	private String fromScheme;
	private String toScheme;
	private String[] scheme;
	private List<ExamCourseUtilBO> courses;
	private List<ExamPromotionCriteriaTO> mainList;
	private String maxBacklog;
	private String backLogCountPercentage;
	private String backLogNumbers;
	private String courseName;
	private String originalCourseId;
	private String originalFromScheme;
	private String originalToScheme;
	private String originalScheme;
	private HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
	private HashMap<Integer, String> schemeMap = new HashMap<Integer, String>();
	private HashMap<Integer, String> schemesMap = new HashMap<Integer, String>();
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {

		this.maxBacklog="";
		this.backLogCountPercentage="";
		this.backLogNumbers="";
		super.clear();
	}

	
	public String getOriginalCourseId() {
		return originalCourseId;
	}

	public void setOriginalCourseId(String originalCourseId) {
		this.originalCourseId = originalCourseId;
	}

	public String getOriginalFromScheme() {
		return originalFromScheme;
	}

	public void setOriginalFromScheme(String originalFromScheme) {
		this.originalFromScheme = originalFromScheme;
	}

	public String getOriginalToScheme() {
		return originalToScheme;
	}

	public void setOriginalToScheme(String originalToScheme) {
		this.originalToScheme = originalToScheme;
	}
	
	
	public String getFromScheme() {
		return fromScheme;
	}

	public void setFromScheme(String fromScheme) {
		this.fromScheme = fromScheme;
	}

	public String getToScheme() {
		return toScheme;
	}

	public void setToScheme(String toScheme) {
		this.toScheme = toScheme;
	}

	public String[] getScheme() {
		return scheme;
	}

	public void setScheme(String[] scheme) {
		this.scheme = scheme;
	}

	public List<ExamCourseUtilBO> getCourses() {
		return courses;
	}

	public void setCourses(List<ExamCourseUtilBO> courses) {
		this.courses = courses;
	}

	public String getMaxBacklog() {
		return maxBacklog;
	}

	public void setMaxBacklog(String maxBacklog) {
		this.maxBacklog = maxBacklog;
	}

	public String getBackLogCountPercentage() {
		return backLogCountPercentage;
	}

	public void setBackLogCountPercentage(String backLogCountPercentage) {
		this.backLogCountPercentage = backLogCountPercentage;
	}

	public String getBackLogNumbers() {
		return backLogNumbers;
	}

	public void setBackLogNumbers(String backLogNumbers) {
		this.backLogNumbers = backLogNumbers;
	}

	public List<ExamPromotionCriteriaTO> getMainList() {
		return mainList;
	}

	public void setMainList(List<ExamPromotionCriteriaTO> mainList) {
		this.mainList = mainList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public HashMap<Integer, String> getCourseMap() {
		return courseMap;
	}

	public void setCourseMap(HashMap<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}

	public void setSchemeMap(HashMap<Integer, String> schemeMap) {
		this.schemeMap = schemeMap;
	}

	public HashMap<Integer, String> getSchemeMap() {
		return schemeMap;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setSchemesMap(HashMap<Integer, String> schemesMap) {
		this.schemesMap = schemesMap;
	}

	public HashMap<Integer, String> getSchemesMap() {
		return schemesMap;
	}

	public void setOriginalScheme(String originalScheme) {
		this.originalScheme = originalScheme;
	}

	public String getOriginalScheme() {
		return originalScheme;
	}

}
