package com.kp.cms.forms.applicationform;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.applicationform.ProfessorPersonalData;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.applicationform.ProfessorBooksandArticlesTO;
import com.kp.cms.to.applicationform.ProfessorEducationDetailsTO;

import com.kp.cms.to.applicationform.ProfessorPersonalDataTO;
import com.kp.cms.to.applicationform.ProfessorPostDoctoralExperTO;
import com.kp.cms.to.applicationform.ProfessorQualificationTO;
import com.kp.cms.to.applicationform.ProfessorTeachingExperTO;

public class ApplicationRegistrationForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String departmentId;
	private Map<Integer, String> departmentMap;
	private String mobileNo;
	private String emailId;
	private String dateOfBirth;
	private String age;
	private String maritalStatus;
	private List<ReligionTO> religionList;
	private String religionId;
	private String casteId;
	private Map<Integer, String> casteMap;
	private String ugSubject;
	private String ugCollege;
	private String ugUniversity;
	private String pgSubject;
	private String pgCollege;
	private String pgUniversity;
	private Boolean isMphil;
	private String mphilTopic;
	private String mphilUniversity;
	private Boolean isPhd;
	private String phdTopic;
	private String phdUniversity;
	private Boolean isNetQualification;
	private String netDeatils;
	private Boolean isJrfQualification;
	private String jrfDeatils;

	private ProfessorPersonalDataTO personalData;

	private List<ProfessorEducationDetailsTO> educationalDetails;
	private List<ProfessorBooksandArticlesTO> booksAndArticles;

	private String bookSize;

	private String mode;

	private List<ProfessorTeachingExperTO> teachingExp;
	private List<ProfessorPostDoctoralExperTO> postDocExp;

	private String postDocSize;
	private String teachingExpSize;
	private String additionalInformation;
	private String diocese;

	private String refNo;

	private String productinfo;
	private String test;
	private String txnid;
	private String status;
	private String hash;
	private String txnRefNo;

	private String pgiStatus;
	private String txnAmt;
	private Boolean isTnxStatusSuccess;
	private String txnDate;
	private Boolean paymentSuccess;

	private String applicantRefNo;
	private String transactionRefNO;
	private String displayPage;
	private String departmentName;
	private String religionName;
	private String casteName;

	private String address;


	private String category;
	
	private FormFile editDocument;
	private String ugcApprovedInformation;
	






	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Map<Integer, String> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<Integer, String> departmentMap) {
		this.departmentMap = departmentMap;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public List<ReligionTO> getReligionList() {
		return religionList;
	}

	public void setReligionList(List<ReligionTO> religionList) {
		this.religionList = religionList;
	}

	public String getReligionId() {
		return religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getCasteId() {
		return casteId;
	}

	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}

	public Map<Integer, String> getCasteMap() {
		return casteMap;
	}

	public void setCasteMap(Map<Integer, String> casteMap) {
		this.casteMap = casteMap;
	}

	public String getUgSubject() {
		return ugSubject;
	}

	public void setUgSubject(String ugSubject) {
		this.ugSubject = ugSubject;
	}

	public String getUgCollege() {
		return ugCollege;
	}

	public void setUgCollege(String ugCollege) {
		this.ugCollege = ugCollege;
	}

	public String getUgUniversity() {
		return ugUniversity;
	}

	public void setUgUniversity(String ugUniversity) {
		this.ugUniversity = ugUniversity;
	}

	public String getPgSubject() {
		return pgSubject;
	}

	public void setPgSubject(String pgSubject) {
		this.pgSubject = pgSubject;
	}

	public String getPgCollege() {
		return pgCollege;
	}

	public void setPgCollege(String pgCollege) {
		this.pgCollege = pgCollege;
	}

	public String getPgUniversity() {
		return pgUniversity;
	}

	public void setPgUniversity(String pgUniversity) {
		this.pgUniversity = pgUniversity;
	}

	public Boolean getIsMphil() {
		return isMphil;
	}

	public void setIsMphil(Boolean isMphil) {
		this.isMphil = isMphil;
	}

	public String getMphilTopic() {
		return mphilTopic;
	}

	public void setMphilTopic(String mphilTopic) {
		this.mphilTopic = mphilTopic;
	}

	public String getMphilUniversity() {
		return mphilUniversity;
	}

	public void setMphilUniversity(String mphilUniversity) {
		this.mphilUniversity = mphilUniversity;
	}

	public Boolean getIsPhd() {
		return isPhd;
	}

	public void setIsPhd(Boolean isPhd) {
		this.isPhd = isPhd;
	}

	public String getPhdTopic() {
		return phdTopic;
	}

	public void setPhdTopic(String phdTopic) {
		this.phdTopic = phdTopic;
	}

	public String getPhdUniversity() {
		return phdUniversity;
	}

	public void setPhdUniversity(String phdUniversity) {
		this.phdUniversity = phdUniversity;
	}

	public Boolean getIsNetQualification() {
		return isNetQualification;
	}

	public void setIsNetQualification(Boolean isNetQualification) {
		this.isNetQualification = isNetQualification;
	}

	public String getNetDeatils() {
		return netDeatils;
	}

	public void setNetDeatils(String netDeatils) {
		this.netDeatils = netDeatils;
	}

	public Boolean getIsJrfQualification() {
		return isJrfQualification;
	}

	public void setIsJrfQualification(Boolean isJrfQualification) {
		this.isJrfQualification = isJrfQualification;
	}

	public String getJrfDeatils() {
		return jrfDeatils;
	}

	public void setJrfDeatils(String jrfDeatils) {
		this.jrfDeatils = jrfDeatils;
	}

	public ProfessorPersonalDataTO getPersonalData() {
		return personalData;
	}

	public void setPersonalData(ProfessorPersonalDataTO personalData) {
		this.personalData = personalData;
	}

	public List<ProfessorEducationDetailsTO> getEducationalDetails() {
		return educationalDetails;
	}

	public void setEducationalDetails(
			List<ProfessorEducationDetailsTO> educationalDetails) {
		this.educationalDetails = educationalDetails;
	}

	public List<ProfessorBooksandArticlesTO> getBooksAndArticles() {
		return booksAndArticles;
	}

	public void setBooksAndArticles(
			List<ProfessorBooksandArticlesTO> booksAndArticles) {
		this.booksAndArticles = booksAndArticles;
	}

	public String getBookSize() {
		return bookSize;
	}

	public void setBookSize(String bookSize) {
		this.bookSize = bookSize;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<ProfessorTeachingExperTO> getTeachingExp() {
		return teachingExp;
	}

	public void setTeachingExp(List<ProfessorTeachingExperTO> teachingExp) {
		this.teachingExp = teachingExp;
	}

	public List<ProfessorPostDoctoralExperTO> getPostDocExp() {
		return postDocExp;
	}

	public void setPostDocExp(List<ProfessorPostDoctoralExperTO> postDocExp) {
		this.postDocExp = postDocExp;
	}

	public String getPostDocSize() {
		return postDocSize;
	}

	public void setPostDocSize(String postDocSize) {
		this.postDocSize = postDocSize;
	}

	public String getTeachingExpSize() {
		return teachingExpSize;
	}

	public void setTeachingExpSize(String teachingExpSize) {
		this.teachingExpSize = teachingExpSize;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getDiocese() {
		return diocese;
	}

	public void setDiocese(String diocese) {
		this.diocese = diocese;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public String getPgiStatus() {
		return pgiStatus;
	}

	public void setPgiStatus(String pgiStatus) {
		this.pgiStatus = pgiStatus;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public Boolean getIsTnxStatusSuccess() {
		return isTnxStatusSuccess;
	}

	public void setIsTnxStatusSuccess(Boolean isTnxStatusSuccess) {
		this.isTnxStatusSuccess = isTnxStatusSuccess;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getPaymentSuccess() {
		return paymentSuccess;
	}

	public void setPaymentSuccess(Boolean paymentSuccess) {
		this.paymentSuccess = paymentSuccess;
	}

	public String getApplicantRefNo() {
		return applicantRefNo;
	}

	public void setApplicantRefNo(String applicantRefNo) {
		this.applicantRefNo = applicantRefNo;
	}

	public String getTransactionRefNO() {
		return transactionRefNO;
	}

	public void setTransactionRefNO(String transactionRefNO) {
		this.transactionRefNO = transactionRefNO;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}
	public String getCasteName() {
		return casteName;
	}

	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public FormFile getEditDocument() {
		return editDocument;
	}

	public void setEditDocument(FormFile editDocument) {
		this.editDocument = editDocument;
	}
	
	public String getUgcApprovedInformation() {
		return ugcApprovedInformation;
	}

	public void setUgcApprovedInformation(String ugcApprovedInformation) {
		this.ugcApprovedInformation = ugcApprovedInformation;
	}
	

}
