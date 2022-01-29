package com.kp.cms.forms.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;

public class NewAttendanceSmsForm extends BaseActionForm
{
	private int id;
	private String classSchemeId;
	private String year;
	private String tempyear;
	private List<StudentTO> studentList=new ArrayList<StudentTO>();
	private int halfLength;
	private List<StudentTO> absenteesList=new ArrayList<StudentTO>();
	private String[] classes;
	private String attendancedate;
	private String attendanceDay;


	private List<Integer> teacherNotEnteredPeriodList =new ArrayList<Integer>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassSchemeId() {
		return classSchemeId;
	}
	public void setClassSchemeId(String classSchemeId) {
		this.classSchemeId = classSchemeId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getTempyear() {
		return tempyear;
	}
	public void setTempyear(String tempyear) {
		this.tempyear = tempyear;
	}
	public List<StudentTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentTO> studentList) {
		this.studentList = studentList;
	}
	public int getHalfLength() {
		return halfLength;
	}
	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}
	public List<StudentTO> getAbsenteesList() {
		return absenteesList;
	}
	public void setAbsenteesList(List<StudentTO> absenteesList) {
		this.absenteesList = absenteesList;
	}
	public String[] getClasses() {
		return classes;
	}
	public void setClasses(String[] classes) {
		this.classes = classes;
	}
	public String getAttendancedate() {
		return attendancedate;
	}
	public void setAttendancedate(String attendancedate) {
		this.attendancedate = attendancedate;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) 
	{
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		boolean invalidDate = false;
		if(this.attendancedate!=null && !StringUtils.isEmpty(this.attendancedate)&& !CommonUtil.isValidDate(this.attendancedate)){
			actionErrors.add("errors",new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			invalidDate = true;
		}
		if(this.attendancedate != null && this.attendancedate.length() != 0 && !invalidDate) {
			String formattedString=CommonUtil.ConvertStringToDateFormat(this.attendancedate, "dd/MM/yyyy","MM/dd/yyyy");
			Date date = new Date(formattedString);
			Date curdate = new Date();
			if (date.compareTo(curdate) == 1) {
				actionErrors.add("errors",new ActionMessage(CMSConstants.FEE_NO_FUTURE_DATE_));
			}
			}
		// TODO Auto-generated method stub
		return actionErrors;
	}
	
	public List<Integer> getTeacherNotEnteredPeriodList() {
		return teacherNotEnteredPeriodList;
	}
	public void setTeacherNotEnteredPeriodList(
			List<Integer> teacherNotEnteredPeriodList) {
		this.teacherNotEnteredPeriodList = teacherNotEnteredPeriodList;
	}
	
	public String getAttendanceDay() {
		return attendanceDay;
	}
	public void setAttendanceDay(String attendanceDay) {
		this.attendanceDay = attendanceDay;
	}
	
}
