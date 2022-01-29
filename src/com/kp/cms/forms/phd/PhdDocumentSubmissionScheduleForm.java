package com.kp.cms.forms.phd;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.phd.DocumentDetailsSubmissionTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdDocumentSubmissionScheduleForm extends BaseActionForm{
  private int id;
  private String studentId;
  private String registerNo;
  private String studentName;
  private String[] selectedcourseId;
  private String courseName;
  private String courseId;
  private String documentId;
  private String programTypeId;
  private String assignDate;
  private String isReminderMail;
  private String guidesFee;
  private String canSubmitOnline;
  private String submited;
  private String submittedDate;
  private String year;
  List<PhdDocumentSubmissionScheduleTO> studentDetailsList;
  private List<CourseTO> courseList;
  private List<DocumentDetailsSubmissionTO> documentList;
  private List<ProgramTypeTO> programTypeList;
  private String displayMessage;
  private String displayMsg;
  private int countcheck;
  private String startDate;
  private String endDate;
  private String guide;
  private String coGuide;
  private String status;
  private boolean print;
  private List<String> messageList;
  private String guideFeeGenerated;
  private int currentProgramType;
  
public void clearList() {
	this.id=0;
	this.selectedcourseId=null;
	super.setCourseId(null);
	super.setAcademicYear(null);
	this.programTypeId=null;
	this.studentDetailsList=null;
	this.documentList=null;
                      }
public void clearList1() {
	super.setCourseId(null);
	this.studentDetailsList=null;
	this.documentId=null;
	this.currentProgramType=0;
                      }
public void clearPage() {
	this.endDate=null;
	this.startDate=null;
	this.studentDetailsList=null;
                      }

public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getStudentId() {
	return studentId;
}


public void setStudentId(String studentId) {
	this.studentId = studentId;
}


public String[] getSelectedcourseId() {
	return selectedcourseId;
}


public void setSelectedcourseId(String[] selectedcourseId) {
	this.selectedcourseId = selectedcourseId;
}


public String getDocumentId() {
	return documentId;
}


public void setDocumentId(String documentId) {
	this.documentId = documentId;
}


public String getProgramTypeId() {
	return programTypeId;
}


public void setProgramTypeId(String programTypeId) {
	this.programTypeId = programTypeId;
}


public String getAssignDate() {
	return assignDate;
}


public void setAssignDate(String assignDate) {
	this.assignDate = assignDate;
}


public String getIsReminderMail() {
	return isReminderMail;
}


public void setIsReminderMail(String isReminderMail) {
	this.isReminderMail = isReminderMail;
}


public String getGuidesFee() {
	return guidesFee;
}


public void setGuidesFee(String guidesFee) {
	this.guidesFee = guidesFee;
}


public String getCanSubmitOnline() {
	return canSubmitOnline;
}


public void setCanSubmitOnline(String canSubmitOnline) {
	this.canSubmitOnline = canSubmitOnline;
}


public String getSubmited() {
	return submited;
}


public void setSubmited(String submited) {
	this.submited = submited;
}


public String getSubmittedDate() {
	return submittedDate;
}
public void setSubmittedDate(String submittedDate) {
	this.submittedDate = submittedDate;
}
public String getYear() {
	return year;
}


public void setYear(String year) {
	this.year = year;
}

public List<PhdDocumentSubmissionScheduleTO> getStudentDetailsList() {
	return studentDetailsList;
}


public void setStudentDetailsList(
		List<PhdDocumentSubmissionScheduleTO> studentDetailsList) {
	this.studentDetailsList = studentDetailsList;
}


public List<CourseTO> getCourseList() {
	return courseList;
}


public void setCourseList(List<CourseTO> courseList) {
	this.courseList = courseList;
}


public List<ProgramTypeTO> getProgramTypeList() {
	return programTypeList;
}


public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
	this.programTypeList = programTypeList;
}


public String getDisplayMessage() {
	return displayMessage;
}


public void setDisplayMessage(String displayMessage) {
	this.displayMessage = displayMessage;
}


public String getDisplayMsg() {
	return displayMsg;
}


public void setDisplayMsg(String displayMsg) {
	this.displayMsg = displayMsg;
}
public List<DocumentDetailsSubmissionTO> getDocumentList() {
	return documentList;
}
public void setDocumentList(List<DocumentDetailsSubmissionTO> documentList) {
	this.documentList = documentList;
}
public int getCountcheck() {
	return countcheck;
}
public void setCountcheck(int countcheck) {
	this.countcheck = countcheck;
}
public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request){
	String formName = request.getParameter("formName");
	ActionErrors actionErrors = super.validate(mapping, request, formName);
	if(actionErrors.isEmpty()) {
		if(this.startDate!=null && !this.startDate.isEmpty() && this.endDate!=null && !this.endDate.isEmpty()){
		Date startDate = CommonUtil.ConvertStringToDate(this.startDate);
		Date endDate = CommonUtil.ConvertStringToDate(this.endDate);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		if(daysBetween <= 0) {
			actionErrors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
		}
	}
		}
	return actionErrors;
}

public String getStudentName() {
	return studentName;
}
public void setStudentName(String studentName) {
	this.studentName = studentName;
}
public String getCourseName() {
	return courseName;
}
public void setCourseName(String courseName) {
	this.courseName = courseName;
}
public String getRegisterNo() {
	return registerNo;
}
public void setRegisterNo(String registerNo) {
	this.registerNo = registerNo;
}
public String getCourseId() {
	return courseId;
}
public void setCourseId(String courseId) {
	this.courseId = courseId;
}
public void clearPage1() {
	this.studentDetailsList=null;
	this.courseName=null;
	this.studentName=null;
	this.studentId=null;
	this.courseId=null;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getGuide() {
	return guide;
}
public void setGuide(String guide) {
	this.guide = guide;
}
public String getCoGuide() {
	return coGuide;
}
public void setCoGuide(String coGuide) {
	this.coGuide = coGuide;
}
public boolean isPrint() {
	return print;
}
public void setPrint(boolean print) {
	this.print = print;
}
public List<String> getMessageList() {
	return messageList;
}
public void setMessageList(List<String> messageList) {
	this.messageList = messageList;
}
public String getGuideFeeGenerated() {
	return guideFeeGenerated;
}
public void setGuideFeeGenerated(String guideFeeGenerated) {
	this.guideFeeGenerated = guideFeeGenerated;
}
public int getCurrentProgramType() {
	return currentProgramType;
}
public void setCurrentProgramType(int currentProgramType) {
	this.currentProgramType = currentProgramType;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

}
