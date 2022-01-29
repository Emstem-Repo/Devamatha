package com.kp.cms.forms.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationSubjectTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.SupplementaryAppExamTo;

public class NewSupplementaryImpApplicationForm extends BaseActionForm {
	
	private String supplementaryImprovement;
	private String examId;
	private String registerNo;
	private String rollNo;
	private String schemeNo;
	private List<KeyValueTO> examList;
	private Map<String,ExamSupplementaryImpApplicationTO> toMap;
	private String student;
	private ExamSupplementaryImpApplicationTO suppTo;
	private Map<Integer, String> courseList;
	private Map<Integer, String> schemeList;
	private String examName;
	private String schemeName;
	private String addOrEdit;
	private int studentId;
	//added by Smitha 
	private HashMap<Integer, String> examTypeList;
	private Map<Integer,String> examNameMap;
	private boolean suppImpAppAvailable;
	private List<SupplementaryAppExamTo> mainList;
	private double theoryFees;
	private double practicalFees;
	private boolean feesNotConfigured;
	private double totalFees;
	private String msg;
	private int finId;
	private int onlinePaymentId;
	private boolean printSupplementary;
	private String printData;
	private boolean displayButton;
	private boolean isExtended;
	private double fineFees;
	
	//raghu added from mounts 
	private String dateOfApplication;
	private String applicationNumber;
	private boolean regularAppAvailable;
	
	private List<ExamSupplementaryImpApplicationTO> suppToList;
	private List<ExamSupplementaryImpApplicationSubjectTO> supSubjectList;
	private String address;
	private Student studentObj;
	private String mobileNo;
	private String email;
	private String courseDep;
	private boolean isAttendanceShortage;
	private boolean add;
	private boolean continue1;
	private String description;
	private boolean supplementary;
	private String examType;
	private boolean isSubmitted;
	private String totalFeesInWords;
	private double applicationFees;
	private double cvCampFees;
	private double marksListFees;
	private double paperFees;
	private double registrationFees;
	private String prevClassId;
	private boolean isSupp;
	private double totalCvCampFees;
	private double totalPaperFees;
	private boolean challanDisplay;
	private boolean isSupplPaper;
	private double examFees;
	private boolean isFine;
	private boolean isSuperFine;
	private ExamDefinitionBO exam;
	// for revaluation
	private boolean revAppAvailable;
	private String isRevaluation;
	private String isAnswerScrptCopy;
	private String isScrutiny;
	private int revclassid;
	private String isChecked;
	
	private boolean challanButton;
	private double marksCopyFees;
	private double revaluationFees;
	private double scrutinyFees;
	private double onlineSevriceFees;
	private String applicationAmount;
	private String challanNo;
	private String revExamId;
	private String isChallengeRevaluation;
	private double challengeRevaluationFees;
	private int prevSemNo;
	private boolean appliedAlready;
	private boolean isAppliedThroughAdmin;
	private String suppExamId;
	private String suppClassId;
	
	private boolean intRedoAppAvailable;
	
	public List<ExamSupplementaryImpApplicationTO> getSuppToList() {
		return suppToList;
	}
	public void setSuppToList(List<ExamSupplementaryImpApplicationTO> suppToList) {
		this.suppToList = suppToList;
	}
	
	public String getSupplementaryImprovement() {
		return supplementaryImprovement;
	}
	public void setSupplementaryImprovement(String supplementaryImprovement) {
		this.supplementaryImprovement = supplementaryImprovement;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	
	public List<KeyValueTO> getExamList() {
		return examList;
	}
	public void setExamList(List<KeyValueTO> examList) {
		this.examList = examList;
	}
	public Map<String, ExamSupplementaryImpApplicationTO> getToMap() {
		return toMap;
	}
	public void setToMap(Map<String, ExamSupplementaryImpApplicationTO> toMap) {
		this.toMap = toMap;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public ExamSupplementaryImpApplicationTO getSuppTo() {
		return suppTo;
	}
	public void setSuppTo(ExamSupplementaryImpApplicationTO suppTo) {
		this.suppTo = suppTo;
	}
	public Map<Integer, String> getCourseList() {
		return courseList;
	}
	public void setCourseList(Map<Integer, String> courseList) {
		this.courseList = courseList;
	}
	public Map<Integer, String> getSchemeList() {
		return schemeList;
	}
	public void setSchemeList(Map<Integer, String> schemeList) {
		this.schemeList = schemeList;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getAddOrEdit() {
		return addOrEdit;
	}
	public void setAddOrEdit(String addOrEdit) {
		this.addOrEdit = addOrEdit;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public HashMap<Integer, String> getExamTypeList() {
		return examTypeList;
	}
	public void setExamTypeList(HashMap<Integer, String> examTypeList) {
		this.examTypeList = examTypeList;
	}
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
 	public boolean isSuppImpAppAvailable() {
		return suppImpAppAvailable;
	}
	public void setSuppImpAppAvailable(boolean suppImpAppAvailable) {
		this.suppImpAppAvailable = suppImpAppAvailable;
	}
	public List<SupplementaryAppExamTo> getMainList() {
		return mainList;
	}
	public void setMainList(List<SupplementaryAppExamTo> mainList) {
		this.mainList = mainList;
	}
	public double getTheoryFees() {
		return theoryFees;
	}
	public void setTheoryFees(double theoryFees) {
		this.theoryFees = theoryFees;
	}
	public double getPracticalFees() {
		return practicalFees;
	}
	public void setPracticalFees(double practicalFees) {
		this.practicalFees = practicalFees;
	}
	public boolean isFeesNotConfigured() {
		return feesNotConfigured;
	}
	public void setFeesNotConfigured(boolean feesNotConfigured) {
		this.feesNotConfigured = feesNotConfigured;
	}
	public double getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(double totalFees) {
		this.totalFees = totalFees;
	}
	public int getFinId() {
		return finId;
	}
	public void setFinId(int finId) {
		this.finId = finId;
	}
	public int getOnlinePaymentId() {
		return onlinePaymentId;
	}
	public boolean isPrintSupplementary() {
		return printSupplementary;
	}
	public void setPrintSupplementary(boolean printSupplementary) {
		this.printSupplementary = printSupplementary;
	}
	public void setOnlinePaymentId(int onlinePaymentId) {
		this.onlinePaymentId = onlinePaymentId;
	}
	public String getPrintData() {
		return printData;
	}
	public void setPrintData(String printData) {
		this.printData = printData;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public boolean isDisplayButton() {
		return displayButton;
	}
	public void setDisplayButton(boolean displayButton) {
		this.displayButton = displayButton;
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
	
	/**
	 * 
	 */
	public void resetFields(){
//		this.supplementaryImprovement=null;
		this.examId=null;
		this.registerNo=null;
		this.rollNo=null;
		super.setCourseId(null);
		this.schemeNo=null;
		this.student=null;
		this.suppTo=null;
		this.studentId=0;
		this.suppImpAppAvailable=false;
		this.mainList=null;
		this.feesNotConfigured=false;
		this.theoryFees=0;
		this.practicalFees=0;
		//this.totalFees=0;
		this.msg=null;
		this.finId=0;
		this.onlinePaymentId=0;
		this.printSupplementary=false;
		this.printData=null;
		this.displayButton=false;
		this.isExtended=false;
		super.setSmartCardNo(null);
		super.setValidThruMonth(null);
		super.setValidThruYear(null);
		this.dateOfApplication = null;
		this.applicationNumber = null;
		this.regularAppAvailable=false;
		this.supSubjectList=new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
		this.address=null;
		this.studentObj=null;
		this.courseDep=null;
		this.email="";
		this.mobileNo="";
		this.isAttendanceShortage=false;
		super.setNameOfStudent(null);
		super.setMonth(null);
		super.setYear(null);
		super.setOriginalDob(null);
		super.setCourseName(null);
		this.add=true;
		this.continue1=false;
		this.description=null;
		this.examType=null;
		this.supplementary=false;
		this.registrationFees=0;
		this.paperFees=0;
		this.cvCampFees=0;
		this.marksListFees=0;
		//this.applicationFees=0;
		this.totalCvCampFees=0;
		this.totalPaperFees=0;
		this.isSupplPaper=false;
		this.fineFees=0;
		this.examFees=0;
		this.isFine=false;
		this.isSuperFine=false;
		this.prevClassId=null;
		this.isChallengeRevaluation=null;
		this.appliedAlready = false;
		this.challanButton=false;
	}
	public boolean isExtended() {
		return isExtended;
	}
	public void setExtended(boolean isExtended) {
		this.isExtended = isExtended;
	}
	public double getFineFees() {
		return fineFees;
	}
	public void setFineFees(double fineFees) {
		this.fineFees = fineFees;
	}
	public String getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public boolean isRegularAppAvailable() {
		return regularAppAvailable;
	}
	public void setRegularAppAvailable(boolean regularAppAvailable) {
		this.regularAppAvailable = regularAppAvailable;
	}

	
	public List<ExamSupplementaryImpApplicationSubjectTO> getSupSubjectList() {
		return supSubjectList;
	}
	public void setSupSubjectList(
			List<ExamSupplementaryImpApplicationSubjectTO> supSubjectList) {
		this.supSubjectList = supSubjectList;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Student getStudentObj() {
		return studentObj;
	}
	public void setStudentObj(Student studentObj) {
		this.studentObj = studentObj;
	}
	public String getCourseDep() {
		return courseDep;
	}
	public void setCourseDep(String courseDep) {
		this.courseDep = courseDep;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean getIsAttendanceShortage() {
		return isAttendanceShortage;
	}
	public void setIsAttendanceShortage(boolean isAttendanceShortage) {
		this.isAttendanceShortage = isAttendanceShortage;
	}
	public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
	public boolean isContinue1() {
		return continue1;
	}
	public void setContinue1(boolean continue1) {
		this.continue1 = continue1;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isSupplementary() {
		return supplementary;
	}
	public void setSupplementary(boolean supplementary) {
		this.supplementary = supplementary;
	}
	
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public boolean getIsSubmitted() {
		return isSubmitted;
	}
	public void setIsSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
	public String getTotalFeesInWords() {
		return totalFeesInWords;
	}
	public void setTotalFeesInWords(String totalFeesInWords) {
		this.totalFeesInWords = totalFeesInWords;
	}
	public double getApplicationFees() {
		return applicationFees;
	}
	public void setApplicationFees(double applicationFees) {
		this.applicationFees = applicationFees;
	}
	public double getCvCampFees() {
		return cvCampFees;
	}
	public void setCvCampFees(double cvCampFees) {
		this.cvCampFees = cvCampFees;
	}
	public double getMarksListFees() {
		return marksListFees;
	}
	public void setMarksListFees(double marksListFees) {
		this.marksListFees = marksListFees;
	}
	public double getPaperFees() {
		return paperFees;
	}
	public void setPaperFees(double paperFees) {
		this.paperFees = paperFees;
	}
	public double getRegistrationFees() {
		return registrationFees;
	}
	public void setRegistrationFees(double registrationFees) {
		this.registrationFees = registrationFees;
	}
	public String getPrevClassId() {
		return prevClassId;
	}
	public void setPrevClassId(String prevClassId) {
		this.prevClassId = prevClassId;
	}
	public boolean getIsSupp() {
		return isSupp;
	}
	public void setIsSupp(boolean isSupp) {
		this.isSupp = isSupp;
	}
	public double getTotalCvCampFees() {
		return totalCvCampFees;
	}
	public void setTotalCvCampFees(double totalCvCampFees) {
		this.totalCvCampFees = totalCvCampFees;
	}
	public double getTotalPaperFees() {
		return totalPaperFees;
	}
	public void setTotalPaperFees(double totalPaperFees) {
		this.totalPaperFees = totalPaperFees;
	}
	public boolean getChallanDisplay() {
		return challanDisplay;
	}
	public void setChallanDisplay(boolean challanDisplay) {
		this.challanDisplay = challanDisplay;
	}
	public boolean getIsSupplPaper() {
		return isSupplPaper;
	}
	public void setIsSupplPaper(boolean isSupplPaper) {
		this.isSupplPaper = isSupplPaper;
	}
	public double getExamFees() {
		return examFees;
	}
	public void setExamFees(double examFees) {
		this.examFees = examFees;
	}
	public boolean getIsFine() {
		return isFine;
	}
	public void setIsFine(boolean isFine) {
		this.isFine = isFine;
	}
	public boolean getIsSuperFine() {
		return isSuperFine;
	}
	public void setIsSuperFine(boolean isSuperFine) {
		this.isSuperFine = isSuperFine;
	}
	public ExamDefinitionBO getExam() {
		return exam;
	}
	public void setExam(ExamDefinitionBO exam) {
		this.exam = exam;
	}
	public boolean isRevAppAvailable() {
		return revAppAvailable;
	}
	public void setRevAppAvailable(boolean revAppAvailable) {
		this.revAppAvailable = revAppAvailable;
	}
	public String getIsRevaluation() {
		return isRevaluation;
	}
	public void setIsRevaluation(String isRevaluation) {
		this.isRevaluation = isRevaluation;
	}
	public String getIsAnswerScrptCopy() {
		return isAnswerScrptCopy;
	}
	public void setIsAnswerScrptCopy(String isAnswerScrptCopy) {
		this.isAnswerScrptCopy = isAnswerScrptCopy;
	}
	public String getIsScrutiny() {
		return isScrutiny;
	}
	public void setIsScrutiny(String isScrutiny) {
		this.isScrutiny = isScrutiny;
	}
	public int getRevclassid() {
		return revclassid;
	}
	public void setRevclassid(int revclassid) {
		this.revclassid = revclassid;
	}
	public boolean isChallanButton() {
		return challanButton;
	}
	public void setChallanButton(boolean challanButton) {
		this.challanButton = challanButton;
	}
	public double getMarksCopyFees() {
		return marksCopyFees;
	}
	public void setMarksCopyFees(double marksCopyFees) {
		this.marksCopyFees = marksCopyFees;
	}
	public double getRevaluationFees() {
		return revaluationFees;
	}
	public void setRevaluationFees(double revaluationFees) {
		this.revaluationFees = revaluationFees;
	}
	public double getScrutinyFees() {
		return scrutinyFees;
	}
	public void setScrutinyFees(double scrutinyFees) {
		this.scrutinyFees = scrutinyFees;
	}
	public double getOnlineSevriceFees() {
		return onlineSevriceFees;
	}
	public void setOnlineSevriceFees(double onlineSevriceFees) {
		this.onlineSevriceFees = onlineSevriceFees;
	}
	public String getApplicationAmount() {
		return applicationAmount;
	}
	public void setApplicationAmount(String applicationAmount) {
		this.applicationAmount = applicationAmount;
	}
	
	public void resetFieldsForFees(){
		this.totalFeesInWords=null;
		this.totalFees=0;
		this.applicationAmount=null;
		this.challanButton=false;
		this.marksCopyFees=0;
		this.revaluationFees=0;
		this.scrutinyFees=0;
		this.displayButton=false;
		this.printSupplementary=false;
		this.isFine=false;
		this.isSuperFine=false;
		this.appliedAlready=false;
		this.isAppliedThroughAdmin=false;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public String getRevExamId() {
		return revExamId;
	}
	public void setRevExamId(String revExamId) {
		this.revExamId = revExamId;
	}
	public String getIsChallengeRevaluation() {
		return isChallengeRevaluation;
	}
	public void setIsChallengeRevaluation(String isChallengeRevaluation) {
		this.isChallengeRevaluation = isChallengeRevaluation;
	}
	public double getChallengeRevaluationFees() {
		return challengeRevaluationFees;
	}
	public void setChallengeRevaluationFees(double challengeRevaluationFees) {
		this.challengeRevaluationFees = challengeRevaluationFees;
	}
	public int getPrevSemNo() {
		return prevSemNo;
	}
	public void setPrevSemNo(int prevSemNo) {
		this.prevSemNo = prevSemNo;
	}
	public boolean getAppliedAlready() {
		return appliedAlready;
	}
	public void setAppliedAlready(boolean appliedAlready) {
		this.appliedAlready = appliedAlready;
	}
	public boolean getAppliedThroughAdmin() {
		return isAppliedThroughAdmin;
	}
	public void setAppliedThroughAdmin(boolean isAppliedThroughAdmin) {
		this.isAppliedThroughAdmin = isAppliedThroughAdmin;
	}
	public String getSuppExamId() {
		return suppExamId;
	}
	public void setSuppExamId(String suppExamId) {
		this.suppExamId = suppExamId;
	}
	public String getSuppClassId() {
		return suppClassId;
	}
	public void setSuppClassId(String suppClassId) {
		this.suppClassId = suppClassId;
	}
	public boolean getIntRedoAppAvailable() {
		return intRedoAppAvailable;
	}
	public void setIntRedoAppAvailable(boolean intRedoAppAvailable) {
		this.intRedoAppAvailable = intRedoAppAvailable;
	}
}
