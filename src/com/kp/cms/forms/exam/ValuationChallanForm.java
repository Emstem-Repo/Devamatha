package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.ValuationChallanDetailsTO;
import com.kp.cms.to.exam.ValuationChallanTO;

public class ValuationChallanForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examType;
	private String examId;
	private String answerScriptType;
	private String displaySubType;
	private String isPreviousExam;
	private Map<Integer, String> examNameList;
	private String examName;
	private List<ExamValidationDetailsTO> validationDetails;
	private List<ExamValuationStatusTO> valuationStatus;
	private String termNumber;
	private String subjectId;
	private String printPage;
	private List<StudentTO>  students;
	private String evaluatorTypeId;
	private List<CourseTO> courseList;
	private String courseId;
	private String subjectName;
	private String startDate;
	private String endDate;
	private String otherEmpId;
	private List<ValuationChallanTO> valuatorList;
	private List<ValuationChallanTO> reviewerList;
	private List<ValuationChallanTO> minorList;
	private List<ValuationChallanTO> majorList;
	private String totalBoardMeetings;
	private String conveyanceCharge;
	private String da;
	private String ta;
	private String anyOther;
	private String otherCost;
	private boolean viewFields;
	private int totalScripts;
	private String totalScriptsAmount;
	private String employeeName;
	private String correntDate;
	private String departmentName;
	private String totalBoardMeetingCharge;
	private String totalConveyanceCharge;
	private String academicYear;
	
	//For Challan Print
	
	private String noOfBoardMeetings;
	private String boardMeeetingRate;
	private String totalBoardMeetingRate;
	private List<ValuationChallanTO> valuationScriptsList;
	private List<ValuationChallanTO> reviewerScriptsList;
	private List<ValuationChallanTO> majorScriptsList;
	private List<ValuationChallanTO> minorScriptsList;
	private String conveyanceChargeForPrint;
	private String othersCharge;
	private String grandTotal;
	private String totalInWords;
	private String challanNo;
	private String externalChallanNo;
	private String totalNoOfConveyance;
	private Map<String,Map<Integer, ValuationChallanTO>> map;
	private String currentDate;
	private Boolean rePrintDisplay;
	private Boolean printconveyence;
	private Boolean printTa;
	private Boolean printDa;
	private Boolean printOther;
	private Boolean printExternal;
	private List<ValuationChallanTO> generatedChallnList;
	private String panNo;
	private String accountNo;
	private String fingerPrintId;
	private String bankName;
	private String bankBranch;
	private String address;
	private List<ExamValuationAnswerScript> answerScripts;
	private int remunerationId;
	private String bankIfscCode;
	private String displayGuest;
	//added by chandra
	private Map<Integer,List<Integer>> examMap;
	private List<ValuationChallanDetailsTO> valuationDetailsList;
	
	public String getDisplayGuest() {
		return displayGuest;
	}
	public void setDisplayGuest(String displayGuest) {
		this.displayGuest = displayGuest;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getAnswerScriptType() {
		return answerScriptType;
	}
	public void setAnswerScriptType(String answerScriptType) {
		this.answerScriptType = answerScriptType;
	}
	
	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}
	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}
	public String getDisplaySubType() {
		return displaySubType;
	}
	public void setDisplaySubType(String displaySubType) {
		this.displaySubType = displaySubType;
	}
	public String getIsPreviousExam() {
		return isPreviousExam;
	}
	public void setIsPreviousExam(String isPreviousExam) {
		this.isPreviousExam = isPreviousExam;
	}
	
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public List<ExamValidationDetailsTO> getValidationDetails() {
		return validationDetails;
	}
	public void setValidationDetails(List<ExamValidationDetailsTO> validationDetails) {
		this.validationDetails = validationDetails;
	}
	public List<ExamValuationStatusTO> getValuationStatus() {
		return valuationStatus;
	}
	public void setValuationStatus(List<ExamValuationStatusTO> valuationStatus) {
		this.valuationStatus = valuationStatus;
	}
	public String getTermNumber() {
		return termNumber;
	}
	public void setTermNumber(String termNumber) {
		this.termNumber = termNumber;
	}
	public String getPrintPage() {
		return printPage;
	}
	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}
	public List<StudentTO> getStudents() {
		return students;
	}
	public void setStudents(List<StudentTO> students) {
		this.students = students;
	}
	public String getEvaluatorTypeId() {
		return evaluatorTypeId;
	}
	public void setEvaluatorTypeId(String evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	public String getOtherEmpId() {
		return otherEmpId;
	}
	public void setOtherEmpId(String otherEmpId) {
		this.otherEmpId = otherEmpId;
	}
	public List<ValuationChallanTO> getValuatorList() {
		return valuatorList;
	}
	public void setValuatorList(List<ValuationChallanTO> valuatorList) {
		this.valuatorList = valuatorList;
	}
	public List<ValuationChallanTO> getReviewerList() {
		return reviewerList;
	}
	public void setReviewerList(List<ValuationChallanTO> reviewerList) {
		this.reviewerList = reviewerList;
	}
	public List<ValuationChallanTO> getMinorList() {
		return minorList;
	}
	public void setMinorList(List<ValuationChallanTO> minorList) {
		this.minorList = minorList;
	}
	public List<ValuationChallanTO> getMajorList() {
		return majorList;
	}
	public void setMajorList(List<ValuationChallanTO> majorList) {
		this.majorList = majorList;
	}
	public String getTotalBoardMeetings() {
		return totalBoardMeetings;
	}
	public void setTotalBoardMeetings(String totalBoardMeetings) {
		this.totalBoardMeetings = totalBoardMeetings;
	}
	public String getConveyanceCharge() {
		return conveyanceCharge;
	}
	public void setConveyanceCharge(String conveyanceCharge) {
		this.conveyanceCharge = conveyanceCharge;
	}
	public String getDa() {
		return da;
	}
	public void setDa(String da) {
		this.da = da;
	}
	public String getTa() {
		return ta;
	}
	public void setTa(String ta) {
		this.ta = ta;
	}
	public String getAnyOther() {
		return anyOther;
	}
	public void setAnyOther(String anyOther) {
		this.anyOther = anyOther;
	}
	public String getOtherCost() {
		return otherCost;
	}
	public void setOtherCost(String otherCost) {
		this.otherCost = otherCost;
	}
	public boolean isViewFields() {
		return viewFields;
	}
	public void setViewFields(boolean viewFields) {
		this.viewFields = viewFields;
	}
	public int getTotalScripts() {
		return totalScripts;
	}
	public void setTotalScripts(int totalScripts) {
		this.totalScripts = totalScripts;
	}
	public String getTotalScriptsAmount() {
		return totalScriptsAmount;
	}
	public void setTotalScriptsAmount(String totalScriptsAmount) {
		this.totalScriptsAmount = totalScriptsAmount;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getCorrentDate() {
		return correntDate;
	}
	public void setCorrentDate(String correntDate) {
		this.correntDate = correntDate;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getTotalBoardMeetingCharge() {
		return totalBoardMeetingCharge;
	}
	public void setTotalBoardMeetingCharge(String totalBoardMeetingCharge) {
		this.totalBoardMeetingCharge = totalBoardMeetingCharge;
	}
	public String getTotalConveyanceCharge() {
		return totalConveyanceCharge;
	}
	public void setTotalConveyanceCharge(String totalConveyanceCharge) {
		this.totalConveyanceCharge = totalConveyanceCharge;
	}
	public String getNoOfBoardMeetings() {
		return noOfBoardMeetings;
	}
	public void setNoOfBoardMeetings(String noOfBoardMeetings) {
		this.noOfBoardMeetings = noOfBoardMeetings;
	}
	public String getBoardMeeetingRate() {
		return boardMeeetingRate;
	}
	public void setBoardMeeetingRate(String boardMeeetingRate) {
		this.boardMeeetingRate = boardMeeetingRate;
	}
	public String getTotalBoardMeetingRate() {
		return totalBoardMeetingRate;
	}
	public void setTotalBoardMeetingRate(String totalBoardMeetingRate) {
		this.totalBoardMeetingRate = totalBoardMeetingRate;
	}
	public List<ValuationChallanTO> getValuationScriptsList() {
		return valuationScriptsList;
	}
	public void setValuationScriptsList(
			List<ValuationChallanTO> valuationScriptsList) {
		this.valuationScriptsList = valuationScriptsList;
	}
	public List<ValuationChallanTO> getReviewerScriptsList() {
		return reviewerScriptsList;
	}
	public void setReviewerScriptsList(List<ValuationChallanTO> reviewerScriptsList) {
		this.reviewerScriptsList = reviewerScriptsList;
	}
	public List<ValuationChallanTO> getMajorScriptsList() {
		return majorScriptsList;
	}
	public void setMajorScriptsList(List<ValuationChallanTO> majorScriptsList) {
		this.majorScriptsList = majorScriptsList;
	}
	public List<ValuationChallanTO> getMinorScriptsList() {
		return minorScriptsList;
	}
	public void setMinorScriptsList(List<ValuationChallanTO> minorScriptsList) {
		this.minorScriptsList = minorScriptsList;
	}
	public String getConveyanceChargeForPrint() {
		return conveyanceChargeForPrint;
	}
	public void setConveyanceChargeForPrint(String conveyanceChargeForPrint) {
		this.conveyanceChargeForPrint = conveyanceChargeForPrint;
	}
	public String getOthersCharge() {
		return othersCharge;
	}
	public void setOthersCharge(String othersCharge) {
		this.othersCharge = othersCharge;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public String getTotalInWords() {
		return totalInWords;
	}
	public void setTotalInWords(String totalInWords) {
		this.totalInWords = totalInWords;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public String getTotalNoOfConveyance() {
		return totalNoOfConveyance;
	}
	public void setTotalNoOfConveyance(String totalNoOfConveyance) {
		this.totalNoOfConveyance = totalNoOfConveyance;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public Map<String, Map<Integer, ValuationChallanTO>> getMap() {
		return map;
	}
	public void setMap(Map<String, Map<Integer, ValuationChallanTO>> map) {
		this.map = map;
	}
	public Boolean getRePrintDisplay() {
		return rePrintDisplay;
	}
	public void setRePrintDisplay(Boolean rePrintDisplay) {
		this.rePrintDisplay = rePrintDisplay;
	}
	public Boolean getPrintconveyence() {
		return printconveyence;
	}
	public void setPrintconveyence(Boolean printconveyence) {
		this.printconveyence = printconveyence;
	}
	public Boolean getPrintTa() {
		return printTa;
	}
	public void setPrintTa(Boolean printTa) {
		this.printTa = printTa;
	}
	public Boolean getPrintDa() {
		return printDa;
	}
	public void setPrintDa(Boolean printDa) {
		this.printDa = printDa;
	}
	public Boolean getPrintOther() {
		return printOther;
	}
	public void setPrintOther(Boolean printOther) {
		this.printOther = printOther;
	}
	public List<ValuationChallanTO> getGeneratedChallnList() {
		return generatedChallnList;
	}
	public void setGeneratedChallnList(List<ValuationChallanTO> generatedChallnList) {
		this.generatedChallnList = generatedChallnList;
	}
	/**
	 * 
	 */
	public void resetFields(){
		this.examId=null;
		this.examType="Regular";
		this.answerScriptType=null;
		this.subjectId=null;
		this.displaySubType="sCode";
		this.isPreviousExam="true";
		this.validationDetails=null;
		this.termNumber=null;
		this.printPage=null;
		this.courseId = null;
		this.startDate=null;
		this.endDate=null;
		this.valuatorList=null;
		this.reviewerList=null;
		this.majorList=null;
		this.minorList=null;
		this.totalBoardMeetings=null;
		this.conveyanceCharge=null;
		this.da=null;
		this.ta=null;
		this.otherCost=null;
		this.anyOther=null;
		this.viewFields=false;
		this.rePrintDisplay=false;
		this.printconveyence=true;
		this.printDa=true;
		this.printOther=true;
		this.printTa=true;
		this.generatedChallnList=null;
		this.printExternal=false;
		this.employeeName=null;
		this.panNo=null;
		this.fingerPrintId=null;
		this.accountNo=null;
		this.bankIfscCode = null;
		this.bankBranch=null;
	}
	public void resetSomeFields() {
		this.totalBoardMeetings=null;
		this.conveyanceCharge=null;
		this.da=null;
		this.ta=null;
		this.otherCost=null;
		this.anyOther=null;
		this.viewFields=false;
		this.totalConveyanceCharge=null;
		this.totalBoardMeetingCharge=null;
		this.rePrintDisplay=false;
		this.printconveyence=true;
		this.printDa=true;
		this.printOther=true;
		this.printTa=true;
		this.totalNoOfConveyance=null;
		this.generatedChallnList=null;
		this.map=null;
		this.employeeName=null;
		this.panNo=null;
		this.fingerPrintId=null;
		this.accountNo=null;
		this.departmentName=null;
		this.bankIfscCode = null;
		this.bankBranch=null;
		this.examMap=null;
		this.valuationDetailsList=null;
	}
	public void resetFieldsEmpDetails() {
		this.employeeName=null;
		this.panNo=null;
		this.fingerPrintId=null;
		this.accountNo=null;
		this.departmentName=null;
		this.bankBranch=null;
		this.bankIfscCode=null;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getExternalChallanNo() {
		return externalChallanNo;
	}
	public void setExternalChallanNo(String externalChallanNo) {
		this.externalChallanNo = externalChallanNo;
	}
	public Boolean getPrintExternal() {
		return printExternal;
	}
	public void setPrintExternal(Boolean printExternal) {
		this.printExternal = printExternal;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getFingerPrintId() {
		return fingerPrintId;
	}
	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<ExamValuationAnswerScript> getAnswerScripts() {
		return answerScripts;
	}
	public void setAnswerScripts(List<ExamValuationAnswerScript> answerScripts) {
		this.answerScripts = answerScripts;
	}
	public int getRemunerationId() {
		return remunerationId;
	}
	public void setRemunerationId(int remunerationId) {
		this.remunerationId = remunerationId;
	}
	public String getBankIfscCode() {
		return bankIfscCode;
	}
	public void setBankIfscCode(String bankIfscCode) {
		this.bankIfscCode = bankIfscCode;
	}
	public Map<Integer, List<Integer>> getExamMap() {
		return examMap;
	}
	public void setExamMap(Map<Integer, List<Integer>> examMap) {
		this.examMap = examMap;
	}
	public List<ValuationChallanDetailsTO> getValuationDetailsList() {
		return valuationDetailsList;
	}
	public void setValuationDetailsList(
			List<ValuationChallanDetailsTO> valuationDetailsList) {
		this.valuationDetailsList = valuationDetailsList;
	}
	
}
