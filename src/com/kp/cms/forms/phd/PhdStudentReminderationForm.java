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
import com.kp.cms.to.phd.DocumentDetailsSubmissionTO;
import com.kp.cms.to.phd.PhdStudentReminderationTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdStudentReminderationForm extends BaseActionForm{
  private int id;
  private String studentId;
  private String registerNo;
  private String studentName;
  private String courseName;
  private String courseId;
  private String documentId;
  private String guideName;
  private String coGuideName;
  private String assignDate;
  private List<PhdStudentReminderationTO> studentDetailsList;
  private List<PhdStudentReminderationTO> guideDetailList;
  private List<PhdStudentReminderationTO> coGuideDetailList;
  private List<DocumentDetailsSubmissionTO> documentList;
  private String startDate;
  private String endDate;
  private boolean print;
  private String guideFeeGenerated;
  private List<String> messageList;
  private String generatedNo;
  private String cAmountTotal;
  private String cAmountConveyance;
  private String cAmountOther;
  private String cAmountdescription;
  private String gAmountTotal;
  private String gAmountConveyance;
  private String gAmountOther;
  private String gAmountdescription;
  private String iGuideId;
  private String iCoGuideId;
  private String guideId;
  private String coGuideId;
  private String gVoucherNo;
  private String cVoucherNo;
  private String ggamount;
  private String ccamount;
  private Boolean isPaid;
  private String paidDate;
  private String otherRemarks;
  private String paidMode;
  private List<PhdStudentReminderationTO> guideFeesPatmentList;
  private String generatedDate;
  
  
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
public void clearPage(){
	this.endDate=null;
	this.startDate=null;
	this.studentDetailsList=null;
	this.guideDetailList=null;
	this.coGuideDetailList=null;
	this.guideName=null;
	this.coGuideName=null;
	this.cAmountTotal=null;
	this.cAmountConveyance=null;
	this.cAmountOther=null;
	this.cAmountdescription=null;
	this.gAmountTotal=null;
	this.gAmountConveyance=null;
	this.gAmountOther=null;
	this.gAmountdescription=null;
	this.iGuideId=null;
	this.iCoGuideId=null;
	this.guideId=null;
	this.coGuideId=null;
	this.gVoucherNo=null;
	this.cVoucherNo=null;
	this.ggamount=null;
	this.ccamount=null;
	this.guideFeesPatmentList=null;
	this.generatedDate=null;
                  }

public void clearPage1(){
	this.studentDetailsList=null;
	this.guideDetailList=null;
	this.coGuideDetailList=null;
	this.guideName=null;
	this.coGuideName=null;
	this.messageList=null;
	this.cAmountTotal=null;
	this.cAmountConveyance=null;
	this.cAmountOther=null;
	this.cAmountdescription=null;
	this.gAmountTotal=null;
	this.gAmountConveyance=null;
	this.gAmountOther=null;
	this.gAmountdescription=null;
	this.iGuideId=null;
	this.iCoGuideId=null;
	this.guideId=null;
	this.coGuideId=null;
	this.gVoucherNo=null;
	this.cVoucherNo=null;
	this.ggamount=null;
	this.ccamount=null;
	this.guideFeesPatmentList=null;
	this.generatedDate=null;
                  }
public void clearPage2(){
	this.studentDetailsList=null;
	this.guideDetailList=null;
	this.coGuideDetailList=null;
	this.guideName=null;
	this.coGuideName=null;
	this.cAmountTotal=null;
	this.cAmountConveyance=null;
	this.cAmountOther=null;
	this.cAmountdescription=null;
	this.gAmountTotal=null;
	this.gAmountConveyance=null;
	this.gAmountOther=null;
	this.gAmountdescription=null;
	this.iGuideId=null;
	this.iCoGuideId=null;
	this.guideId=null;
	this.coGuideId=null;
	this.gVoucherNo=null;
	this.cVoucherNo=null;
	this.ggamount=null;
	this.ccamount=null;
	this.guideFeesPatmentList=null;
	this.generatedDate=null;
                  }
public void clearPage3(){
	this.guideDetailList=null;
	this.coGuideDetailList=null;
	this.guideName=null;
	this.coGuideName=null;
	this.cAmountTotal=null;
	this.cAmountConveyance=null;
	this.cAmountOther=null;
	this.cAmountdescription=null;
	this.gAmountTotal=null;
	this.gAmountConveyance=null;
	this.gAmountOther=null;
	this.gAmountdescription=null;
	this.iGuideId=null;
	this.iCoGuideId=null;
	this.guideId=null;
	this.coGuideId=null;
	this.gVoucherNo=null;
	this.cVoucherNo=null;
	this.ggamount=null;
	this.ccamount=null;
	this.guideFeesPatmentList=null;
	this.generatedDate=null;
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

public String getRegisterNo() {
	return registerNo;
}

public void setRegisterNo(String registerNo) {
	this.registerNo = registerNo;
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

public String getCourseId() {
	return courseId;
}

public void setCourseId(String courseId) {
	this.courseId = courseId;
}

public String getDocumentId() {
	return documentId;
}

public void setDocumentId(String documentId) {
	this.documentId = documentId;
}

public String getAssignDate() {
	return assignDate;
}

public void setAssignDate(String assignDate) {
	this.assignDate = assignDate;
}

public List<PhdStudentReminderationTO> getStudentDetailsList() {
	return studentDetailsList;
}

public void setStudentDetailsList(
		List<PhdStudentReminderationTO> studentDetailsList) {
	this.studentDetailsList = studentDetailsList;
}

public List<DocumentDetailsSubmissionTO> getDocumentList() {
	return documentList;
}

public void setDocumentList(List<DocumentDetailsSubmissionTO> documentList) {
	this.documentList = documentList;
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

public boolean isPrint() {
	return print;
}

public void setPrint(boolean print) {
	this.print = print;
}

public String getGuideFeeGenerated() {
	return guideFeeGenerated;
}

public void setGuideFeeGenerated(String guideFeeGenerated) {
	this.guideFeeGenerated = guideFeeGenerated;
}

public List<PhdStudentReminderationTO> getGuideDetailList() {
	return guideDetailList;
}

public void setGuideDetailList(List<PhdStudentReminderationTO> guideDetailList) {
	this.guideDetailList = guideDetailList;
}

public List<PhdStudentReminderationTO> getCoGuideDetailList() {
	return coGuideDetailList;
}

public void setCoGuideDetailList(
		List<PhdStudentReminderationTO> coGuideDetailList) {
	this.coGuideDetailList = coGuideDetailList;
}

public String getGuideName() {
	return guideName;
}

public void setGuideName(String guideName) {
	this.guideName = guideName;
}

public String getCoGuideName() {
	return coGuideName;
}

public void setCoGuideName(String coGuideName) {
	this.coGuideName = coGuideName;
}
public List<String> getMessageList() {
	return messageList;
}
public void setMessageList(List<String> messageList) {
	this.messageList = messageList;
}
public String getGeneratedNo() {
	return generatedNo;
}
public void setGeneratedNo(String generatedNo) {
	this.generatedNo = generatedNo;
}
public String getcAmountTotal() {
	return cAmountTotal;
}
public void setcAmountTotal(String cAmountTotal) {
	this.cAmountTotal = cAmountTotal;
}
public String getcAmountConveyance() {
	return cAmountConveyance;
}
public void setcAmountConveyance(String cAmountConveyance) {
	this.cAmountConveyance = cAmountConveyance;
}
public String getcAmountOther() {
	return cAmountOther;
}
public void setcAmountOther(String cAmountOther) {
	this.cAmountOther = cAmountOther;
}
public String getcAmountdescription() {
	return cAmountdescription;
}
public void setcAmountdescription(String cAmountdescription) {
	this.cAmountdescription = cAmountdescription;
}
public String getgAmountTotal() {
	return gAmountTotal;
}
public void setgAmountTotal(String gAmountTotal) {
	this.gAmountTotal = gAmountTotal;
}
public String getgAmountConveyance() {
	return gAmountConveyance;
}
public void setgAmountConveyance(String gAmountConveyance) {
	this.gAmountConveyance = gAmountConveyance;
}
public String getgAmountOther() {
	return gAmountOther;
}
public void setgAmountOther(String gAmountOther) {
	this.gAmountOther = gAmountOther;
}
public String getgAmountdescription() {
	return gAmountdescription;
}
public void setgAmountdescription(String gAmountdescription) {
	this.gAmountdescription = gAmountdescription;
}
public String getiGuideId() {
	return iGuideId;
}
public void setiGuideId(String iGuideId) {
	this.iGuideId = iGuideId;
}
public String getiCoGuideId() {
	return iCoGuideId;
}
public void setiCoGuideId(String iCoGuideId) {
	this.iCoGuideId = iCoGuideId;
}
public String getGuideId() {
	return guideId;
}
public void setGuideId(String guideId) {
	this.guideId = guideId;
}
public String getCoGuideId() {
	return coGuideId;
}
public void setCoGuideId(String coGuideId) {
	this.coGuideId = coGuideId;
}
public String getgVoucherNo() {
	return gVoucherNo;
}
public void setgVoucherNo(String gVoucherNo) {
	this.gVoucherNo = gVoucherNo;
}
public String getcVoucherNo() {
	return cVoucherNo;
}
public void setcVoucherNo(String cVoucherNo) {
	this.cVoucherNo = cVoucherNo;
}
public String getGgamount() {
	return ggamount;
}
public void setGgamount(String ggamount) {
	this.ggamount = ggamount;
}
public String getCcamount() {
	return ccamount;
}
public void setCcamount(String ccamount) {
	this.ccamount = ccamount;
}
public Boolean getIsPaid() {
	return isPaid;
}
public void setIsPaid(Boolean isPaid) {
	this.isPaid = isPaid;
}
public String getPaidDate() {
	return paidDate;
}
public void setPaidDate(String paidDate) {
	this.paidDate = paidDate;
}
public String getOtherRemarks() {
	return otherRemarks;
}
public void setOtherRemarks(String otherRemarks) {
	this.otherRemarks = otherRemarks;
}
public String getPaidMode() {
	return paidMode;
}
public void setPaidMode(String paidMode) {
	this.paidMode = paidMode;
}
public List<PhdStudentReminderationTO> getGuideFeesPatmentList() {
	return guideFeesPatmentList;
}
public void setGuideFeesPatmentList(
		List<PhdStudentReminderationTO> guideFeesPatmentList) {
	this.guideFeesPatmentList = guideFeesPatmentList;
}
public String getGeneratedDate() {
	return generatedDate;
}
public void setGeneratedDate(String generatedDate) {
	this.generatedDate = generatedDate;
}

}
