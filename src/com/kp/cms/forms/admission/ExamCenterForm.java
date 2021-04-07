package com.kp.cms.forms.admission;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admission.ExamCenterTO;

public class ExamCenterForm extends BaseActionForm{
	
	private int id;
	private String programId;
	private String programName;
	private String center;
	private String seatNoFrom;
	private String seatNoTo;
	private String currentSeatNo;
	private String seatNoPrefix;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private List<ProgramTO> programList;
	private List<ExamCenterTO> examCenterList;
	private int dupId;
	private String origProgId;
	private String origCenter;
	private String origCreatedBy;
	private Date origCreatedDate;
	private String isSeatNoValidationRequired;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	
	public String getSeatNoPrefix() {
		return seatNoPrefix;
	}
	public void setSeatNoPrefix(String seatNoPrefix) {
		this.seatNoPrefix = seatNoPrefix;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public List<ProgramTO> getProgramList() {
		return programList;
	}
	public void setProgramList(List<ProgramTO> programList) {
		this.programList = programList;
	}
	public List<ExamCenterTO> getExamCenterList() {
		return examCenterList;
	}
	public void setExamCenterList(List<ExamCenterTO> examCenterList) {
		this.examCenterList = examCenterList;
	}
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public String getOrigProgId() {
		return origProgId;
	}
	public void setOrigProgId(String origProgId) {
		this.origProgId = origProgId;
	}
	public String getOrigCenter() {
		return origCenter;
	}
	public void setOrigCenter(String origCenter) {
		this.origCenter = origCenter;
	}
	public String getSeatNoFrom() {
		return seatNoFrom;
	}
	public void setSeatNoFrom(String seatNoFrom) {
		this.seatNoFrom = seatNoFrom;
	}
	public String getSeatNoTo() {
		return seatNoTo;
	}
	public void setSeatNoTo(String seatNoTo) {
		this.seatNoTo = seatNoTo;
	}
	public String getCurrentSeatNo() {
		return currentSeatNo;
	}
	public void setCurrentSeatNo(String currentSeatNo) {
		this.currentSeatNo = currentSeatNo;
	}
	public String getOrigCreatedBy() {
		return origCreatedBy;
	}
	public void setOrigCreatedBy(String origCreatedBy) {
		this.origCreatedBy = origCreatedBy;
	}
	public Date getOrigCreatedDate() {
		return origCreatedDate;
	}
	public void setOrigCreatedDate(Date origCreatedDate) {
		this.origCreatedDate = origCreatedDate;
	}
	public void clear()
	{
		 programId=null;
		 center=null;
		 seatNoFrom=null;
		 seatNoTo=null;
		 currentSeatNo=null;
		 seatNoPrefix=null;
		 address1=null;
		 address2=null;
		 address3=null;
		 address4=null;
		 origProgId=null;
		 origCenter=null;
		 dupId=0;
		 isSeatNoValidationRequired="false";
	}
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request){
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public String getIsSeatNoValidationRequired() {
		return isSeatNoValidationRequired;
	}
	public void setIsSeatNoValidationRequired(String isSeatNoValidationRequired) {
		this.isSeatNoValidationRequired = isSeatNoValidationRequired;
	}

}
