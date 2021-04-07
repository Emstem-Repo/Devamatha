package com.kp.cms.to.admin;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.struts.upload.FormFile;

public class ApplnDocTO implements Comparable<ApplnDocTO>,Comparator<ApplnDocTO>{
	
	private int id;
	private DocTypeTO docTypeTO;
	private String name;
	private String applicationNo;
	private String regnNo;
	private FormFile document;
	private FormFile editDocument;
	private byte[] currDocument;
	private String contentType;
	private int docTypeId;
	private boolean needToProduce;
	private boolean documentPresent;
	private boolean verified;
	private boolean photo;
	private boolean hardSubmitted;
	private boolean notApplicable;
	private boolean temphardSubmitted;
	private boolean tempNotApplicable;
	private String docName;
	private byte[] photoBytes;
	private String submitDate;
	private String createdBy;
	private Date createdDate;
	private String printName;
	private boolean defaultPhoto;
	private boolean needToProduceSemWiseMC;
	private String semisterNo;
	private List<ApplnDocDetailsTO> docDetailsList;
	private String semType;
	private int displayOrder;
	private boolean mngQuotaForm;
	private byte[] mngQuotaBytes;
	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isTemphardSubmitted() {
		return temphardSubmitted;
	}

	public void setTemphardSubmitted(boolean temphardSubmitted) {
		this.temphardSubmitted = temphardSubmitted;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isNeedToProduce() {
		return needToProduce;
	}

	public boolean isDocumentPresent() {
		return documentPresent;
	}

	public void setDocumentPresent(boolean documentPresent) {
		this.documentPresent = documentPresent;
	}

	public void setNeedToProduce(boolean needToProduce) {
		this.needToProduce = needToProduce;
	}

	public int getDocTypeId() {
		return docTypeId;
	}

	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DocTypeTO getDocTypeTO() {
		return docTypeTO;
	}

	public void setDocTypeTO(DocTypeTO docTypeTO) {
		this.docTypeTO = docTypeTO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getRegnNo() {
		return regnNo;
	}

	public void setRegnNo(String regnNo) {
		this.regnNo = regnNo;
	}

	public FormFile getDocument() {
		return document;
	}

	public void setDocument(FormFile document) {
		this.document = document;
	}

	public FormFile getEditDocument() {
		return editDocument;
	}

	public void setEditDocument(FormFile editDocument) {
		this.editDocument = editDocument;
	}

	public byte[] getCurrDocument() {
		return currDocument;
	}

	public void setCurrDocument(byte[] currDocument) {
		this.currDocument = currDocument;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isHardSubmitted() {
		return hardSubmitted;
	}

	public void setHardSubmitted(boolean hardSubmitted) {
		this.hardSubmitted = hardSubmitted;
	}

	public boolean isPhoto() {
		return photo;
	}

	public void setPhoto(boolean photo) {
		this.photo = photo;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public byte[] getPhotoBytes() {
		return photoBytes;
	}

	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public boolean isNotApplicable() {
		return notApplicable;
	}

	public void setNotApplicable(boolean notApplicable) {
		this.notApplicable = notApplicable;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isTempNotApplicable() {
		return tempNotApplicable;
	}

	public void setTempNotApplicable(boolean tempNotApplicable) {
		this.tempNotApplicable = tempNotApplicable;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public boolean isDefaultPhoto() {
		return defaultPhoto;
	}

	public void setDefaultPhoto(boolean defaultPhoto) {
		this.defaultPhoto = defaultPhoto;
	}

	public boolean isNeedToProduceSemWiseMC() {
		return needToProduceSemWiseMC;
	}

	public void setNeedToProduceSemWiseMC(boolean needToProduceSemWiseMC) {
		this.needToProduceSemWiseMC = needToProduceSemWiseMC;
	}

	public String getSemisterNo() {
		return this.semisterNo;
	}

	public void setSemisterNo(String semisterNo) {
		this.semisterNo = semisterNo;
	}

	public List<ApplnDocDetailsTO> getDocDetailsList() {
		return docDetailsList;
	}

	public void setDocDetailsList(List<ApplnDocDetailsTO> docDetailsList) {
		this.docDetailsList = docDetailsList;
	}

	public String getSemType() {
		return semType;
	}

	public void setSemType(String semType) {
		this.semType = semType;
	}

	@Override
	public int compareTo(ApplnDocTO to) {
		if(to != null && this != null && to.getPrintName() != null && this.printName != null){
			return this.printName.compareTo(to.getPrintName());
		}else{
			return 0;
		}
	}
//Added By manu
	@Override
	public int compare(ApplnDocTO arg0, ApplnDocTO arg1) {
		Integer a  = arg0.getDisplayOrder();
		Integer b  = arg1.getDisplayOrder();
		return a.compareTo(b);
	}
//end

	public boolean isMngQuotaForm() {
		return mngQuotaForm;
	}

	public void setMngQuotaForm(boolean mngQuotaForm) {
		this.mngQuotaForm = mngQuotaForm;
	}

	public byte[] getMngQuotaBytes() {
		return mngQuotaBytes;
	}

	public void setMngQuotaBytes(byte[] mngQuotaBytes) {
		this.mngQuotaBytes = mngQuotaBytes;
	}
	
}