package com.kp.cms.forms.studentfeedback;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.studentfeedback.EvaHiddenSubjectTeacherTo;

public class EvaHiddenSubjectTeacherForm extends BaseActionForm
{

    private int id;
    private String year;
    private String classesId;
    private Map<Integer, String> classMap;
    private List<EvaHiddenSubjectTeacherTo> hiddenTeacherList;
    private List<TeacherClassEntryTo> teacherClassSubjectList;
    private String className;
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
	public List<EvaHiddenSubjectTeacherTo> getHiddenTeacherList() {
		return hiddenTeacherList;
	}

	public void setHiddenTeacherList(
			List<EvaHiddenSubjectTeacherTo> hiddenTeacherList) {
		this.hiddenTeacherList = hiddenTeacherList;
	}

	 public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public List<TeacherClassEntryTo> getTeacherClassSubjectList() {
		return teacherClassSubjectList;
	}

	public void setTeacherClassSubjectList(
			List<TeacherClassEntryTo> teacherClassSubjectList) {
		this.teacherClassSubjectList = teacherClassSubjectList;
	}
	public void resetField(){
		id=0;
		year=null;
		classesId=null;
		hiddenTeacherList=null;
		teacherClassSubjectList=null;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
