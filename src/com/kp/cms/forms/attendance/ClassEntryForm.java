package com.kp.cms.forms.attendance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ClassEntryForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String activateId;
	private String courseId;
	private String termNo;
	private String sectionName;
	private String className;
	private List<ClassesTO> classesList;
	private String course;
	private List<CourseTO> courseList;
	private ClassesTO classTo;
	private String semesterNo;
	private String classSchemewiseId;
	// Added for change as per new UC
	private Integer courseGroupCodeId;
	private ArrayList<KeyValueTO> courseGroupCodeList;
	private String originalId;
	private String originalActivateId;
	private String originalCourseId;
	private String originalTermNo;
	private String originalSectionName;
	private String originalClassName;
	private String originalSemesterNo;
	private String originalClassSchemewiseId;
	private String originalYear;
	private Integer originalCourseGroupId;
	
	public String getClassSchemewiseId() {
		return classSchemewiseId;
	}

	public void setClassSchemewiseId(String classSchemewiseId) {
		this.classSchemewiseId = classSchemewiseId;
	}

	public String getSemesterNo() {
		return semesterNo;
	}

	public void setSemesterNo(String semesterNo) {
		this.semesterNo = semesterNo;
	}

	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId
	 *            the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * @param sectionName
	 *            the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the classesList
	 */
	public List<ClassesTO> getClassesList() {
		return classesList;
	}

	/**
	 * @param classesList
	 *            the classesList to set
	 */
	public void setClassesList(List<ClassesTO> classesList) {
		this.classesList = classesList;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the course
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * @param course
	 *            the course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	/**
	 * @return the termNo
	 */
	public String getTermNo() {
		return termNo;
	}

	/**
	 * @param termNo
	 *            the termNo to set
	 */
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	/**
	 * @return the courseList
	 */
	public List<CourseTO> getCourseList() {
		return courseList;
	}

	/**
	 * @param courseList
	 *            the courseList to set
	 */
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	/**
	 * This method is used to reset the formbean properties
	 */
	public void clear() {
		this.courseId = null;
		this.courseGroupCodeId = null;
		this.id = null;
		this.classSchemewiseId = null;
		this.sectionName = null;
		this.className = null;
		this.termNo = null;
		this.originalActivateId=null;
		this.originalClassName=null;
		this.originalClassSchemewiseId=null;
		this.originalCourseId=null;
		this.originalId=null;
		this.originalSectionName=null;
		this.originalSemesterNo=null;
		this.originalTermNo=null;
		this.setClassesList(null);
		super.clear();
	}

	/**
	 * @return the classTo
	 */
	public ClassesTO getClassTo() {
		return classTo;
	}

	/**
	 * @param classTo
	 *            the classTo to set
	 */
	public void setClassTo(ClassesTO classTo) {
		this.classTo = classTo;
	}

	/**
	 * @return the activateId
	 */
	public String getActivateId() {
		return activateId;
	}

	/**
	 * @param activateId
	 *            the activateId to set
	 */
	public void setActivateId(String activateId) {
		this.activateId = activateId;
	}

	public void setCourseGroupCodeId(Integer courseGroupCodeId) {
		try {
			if (courseGroupCodeId.intValue() > 0) {
				this.courseGroupCodeId = courseGroupCodeId;
			} else {
				this.courseGroupCodeId = null;
			}
		} catch (Exception e) {
			this.courseGroupCodeId = null;
		}
	}

	public Integer getCourseGroupCodeId() {
		return courseGroupCodeId;
	}

	public void setCourseGroupCodeList(ArrayList<KeyValueTO> courseGroupCodeList) {
		this.courseGroupCodeList = courseGroupCodeList;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getOriginalActivateId() {
		return originalActivateId;
	}

	public void setOriginalActivateId(String originalActivateId) {
		this.originalActivateId = originalActivateId;
	}

	public String getOriginalCourseId() {
		return originalCourseId;
	}

	public void setOriginalCourseId(String originalCourseId) {
		this.originalCourseId = originalCourseId;
	}

	public String getOriginalTermNo() {
		return originalTermNo;
	}

	public void setOriginalTermNo(String originalTermNo) {
		this.originalTermNo = originalTermNo;
	}

	public String getOriginalSectionName() {
		return originalSectionName;
	}

	public void setOriginalSectionName(String originalSectionName) {
		this.originalSectionName = originalSectionName;
	}

	public String getOriginalClassName() {
		return originalClassName;
	}

	public void setOriginalClassName(String originalClassName) {
		this.originalClassName = originalClassName;
	}

	public String getOriginalSemesterNo() {
		return originalSemesterNo;
	}

	public void setOriginalSemesterNo(String originalSemesterNo) {
		this.originalSemesterNo = originalSemesterNo;
	}

	public String getOriginalClassSchemewiseId() {
		return originalClassSchemewiseId;
	}

	public void setOriginalClassSchemewiseId(String originalClassSchemewiseId) {
		this.originalClassSchemewiseId = originalClassSchemewiseId;
	}

	public ArrayList<KeyValueTO> getCourseGroupCodeList() {
		return courseGroupCodeList;
	}

	public String getOriginalYear() {
		return originalYear;
	}

	public void setOriginalYear(String originalYear) {
		this.originalYear = originalYear;
	}

	public Integer getOriginalCourseGroupId() {
		return originalCourseGroupId;
	}

	public void setOriginalCourseGroupId(Integer originalCourseGroupId) {
		try {
			if (originalCourseGroupId.intValue() > 0) {
				this.originalCourseGroupId = courseGroupCodeId;
			} else {
				this.originalCourseGroupId = null;
			}
		} catch (Exception e) {
			this.originalCourseGroupId = null;
		}
	}

}
