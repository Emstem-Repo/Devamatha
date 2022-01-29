package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.to.exam.SubjectRuleSettingCertificateCourseTo;

public class SubjectRuleSettingsForm extends BaseActionForm{

    private static final long serialVersionUID = 1L;
    private String courseIds[];
    private String schemeType;
    List programTypeList;
    private String subjectName;
    private ExamSubjectRuleSettingsTheoryInternalTO esto;
    private ExamSubjectRuleSettingsPracticalESETO peseTo;
    private ExamSubjectRuleSettingsTheoryESETO teseTo;
    private ExamSubjectRuleSettingsPracticalInternalTO pesto;
    private String theoryExam;
    private String duptheoryExam;
    private String practicalExam;
    private String duppracticalExam;
    private String internalExam;
    private String dupinternalExam;
    private String attendance;
    private String dupattendance;
    private String minimum;
    private String maximum;
    private String valuated;
    private boolean checkTheory;
    private boolean checkpractical;
    private boolean checkAttendance;
    private boolean checkInternal;
    private List<SubjectTO> subList;
    private int id;
    private String methodType;
    private String toYear;
    private String course;
    private String subject;
    private String academicYear;
	private List<SubjectRuleSettingCertificateCourseTo> subRulCerCourList;
	private int countcheck;
	private List<Object[]> allDatas;

    public String[] getCourseIds()
    {
        return courseIds;
    }

    public void setCourseIds(String courseIds[])
    {
        this.courseIds = courseIds;
    }

    public String getSchemeType()
    {
        return schemeType;
    }

    public void setSchemeType(String schemeType)
    {
        this.schemeType = schemeType;
    }

    public List getProgramTypeList()
    {
        return programTypeList;
    }

    public void setProgramTypeList(List programTypeList)
    {
        this.programTypeList = programTypeList;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public ExamSubjectRuleSettingsTheoryInternalTO getEsto()
    {
        return esto;
    }

    public void setEsto(ExamSubjectRuleSettingsTheoryInternalTO esto)
    {
        this.esto = esto;
    }

    public ExamSubjectRuleSettingsPracticalESETO getPeseTo()
    {
        return peseTo;
    }

    public void setPeseTo(ExamSubjectRuleSettingsPracticalESETO peseTo)
    {
        this.peseTo = peseTo;
    }

    public ExamSubjectRuleSettingsTheoryESETO getTeseTo()
    {
        return teseTo;
    }

    public void setTeseTo(ExamSubjectRuleSettingsTheoryESETO teseTo)
    {
        this.teseTo = teseTo;
    }

    public ExamSubjectRuleSettingsPracticalInternalTO getPesto()
    {
        return pesto;
    }

    public void setPesto(ExamSubjectRuleSettingsPracticalInternalTO pesto)
    {
        this.pesto = pesto;
    }

    public String getTheoryExam() {
		return theoryExam;
	}

	public void setTheoryExam(String theoryExam) {
		this.theoryExam = theoryExam;
	}

	public String getDuptheoryExam() {
		return duptheoryExam;
	}

	public void setDuptheoryExam(String duptheoryExam) {
		this.duptheoryExam = duptheoryExam;
	}

	public String getPracticalExam() {
		return practicalExam;
	}

	public void setPracticalExam(String practicalExam) {
		this.practicalExam = practicalExam;
	}

	public String getDuppracticalExam() {
		return duppracticalExam;
	}

	public void setDuppracticalExam(String duppracticalExam) {
		this.duppracticalExam = duppracticalExam;
	}

	public String getInternalExam() {
		return internalExam;
	}

	public void setInternalExam(String internalExam) {
		this.internalExam = internalExam;
	}

	public String getDupinternalExam() {
		return dupinternalExam;
	}

	public void setDupinternalExam(String dupinternalExam) {
		this.dupinternalExam = dupinternalExam;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getDupattendance() {
		return dupattendance;
	}

	public void setDupattendance(String dupattendance) {
		this.dupattendance = dupattendance;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getValuated() {
		return valuated;
	}

	public void setValuated(String valuated) {
		this.valuated = valuated;
	}

	public boolean isCheckTheory() {
		return checkTheory;
	}

	public void setCheckTheory(boolean checkTheory) {
		this.checkTheory = checkTheory;
	}

	public boolean isCheckpractical() {
		return checkpractical;
	}

	public void setCheckpractical(boolean checkpractical) {
		this.checkpractical = checkpractical;
	}

	public boolean isCheckAttendance() {
		return checkAttendance;
	}

	public void setCheckAttendance(boolean checkAttendance) {
		this.checkAttendance = checkAttendance;
	}

	public boolean isCheckInternal() {
		return checkInternal;
	}

	public void setCheckInternal(boolean checkInternal) {
		this.checkInternal = checkInternal;
	}

	public List<SubjectTO> getSubList() {
		return subList;
	}

	public void setSubList(List<SubjectTO> subList) {
		this.subList = subList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 
	 */
	public void resetFields()
    {
        this.courseIds = null;
        this.schemeType = null;
        super.setAcademicYear(null);
        super.setProgramTypeId(null);
        this.subjectName = null;
        this.checkAttendance=false;
        this.checkInternal=false;
        this.checkpractical=false;
        this.checkTheory=false;
        this.methodType=null;
        this.esto=null;
        this.peseTo=null;
        this.pesto=null;
        this.esto=null;
    }
	/**
	 * 
	 */
	public void clearFields(){
		this.theoryExam=null;
		this.duptheoryExam=null;
		this.practicalExam=null;
		this.duppracticalExam=null;
		this.internalExam=null;
		this.dupinternalExam=null;
		this.attendance=null;
		this.dupattendance=null;
		this.minimum=null;
		this.maximum=null;
		this.valuated=null;
	}
	
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        String formName = request.getParameter("formName");
        ActionErrors actionErrors = super.validate(mapping, request, formName);
        return actionErrors;
    }
    
    /**
	 * 
	 */
	public void resetFields1()
    {
		super.setCourseId(null);
		this.theoryExam=null;
		this.duptheoryExam=null;
		this.practicalExam=null;
		this.duppracticalExam=null;
		this.internalExam=null;
		this.dupinternalExam=null;
		this.attendance=null;
		this.dupattendance=null;
		this.minimum=null;
		this.maximum=null;
		this.valuated=null;
        this.methodType=null;
        super.setSubjectId(null);
        this.esto=null;
        this.peseTo=null;
        this.pesto=null;
        this.esto=null;
        this.allDatas=null;
    }

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public List<SubjectRuleSettingCertificateCourseTo> getSubRulCerCourList() {
		return subRulCerCourList;
	}

	public void setSubRulCerCourList(
			List<SubjectRuleSettingCertificateCourseTo> subRulCerCourList) {
		this.subRulCerCourList = subRulCerCourList;
	}

	public int getCountcheck() {
		return countcheck;
	}

	public void setCountcheck(int countcheck) {
		this.countcheck = countcheck;
	}
	
	public void clearPage(){
		this.academicYear=null;
		this.schemeType=null;
		this.subRulCerCourList=null;
		this.allDatas=null;
	}

	public List<Object[]> getAllDatas() {
		return allDatas;
	}

	public void setAllDatas(List<Object[]> allDatas) {
		this.allDatas = allDatas;
	}

}
