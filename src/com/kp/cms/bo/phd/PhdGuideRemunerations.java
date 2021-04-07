package com.kp.cms.bo.phd;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;

public class PhdGuideRemunerations implements Serializable {
    
	private int id;
	private Student studentId;
	private PhdEmployee guideId;
	private PhdEmployee coGuideId;
	private Employee internalGuide;
	private Employee internalCoGuide;
	private String voucherNo;
	private Date generatedDate;
	private BigDecimal conveyanceCharges;
	private BigDecimal otherCharges;
	private String description;
	private BigDecimal totalCharges;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Set<PhdGuideRemunerationDetails> guideRemuneration=new HashSet<PhdGuideRemunerationDetails>();
	
	private Boolean isPaid;
	private Date paidDate;
	private String otherRemarks;
	private String paidMode;

	public PhdGuideRemunerations() {
	}

	public PhdGuideRemunerations(int id,PhdEmployee guideId,PhdEmployee coGuideId,Employee internalGuide,Employee internalCoGuide,String voucherNo,Student studentId,
			Date generatedDate,BigDecimal conveyanceCharges,BigDecimal otherCharges,String description,BigDecimal totalCharges,Boolean isPaid,Date paidDate,
			String otherRemarks,String paidMode,Boolean isActive,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate,Set<PhdGuideRemunerationDetails> guideRemuneration) 
	{
		
		this.id = id;
		this.guideId=guideId;
		this.coGuideId=coGuideId;
		this.internalGuide=internalGuide;
		this.internalCoGuide=internalCoGuide;
		this.voucherNo=voucherNo;
		this.generatedDate=generatedDate;
		this.conveyanceCharges=conveyanceCharges;
		this.otherCharges=otherCharges;
		this.description=description;
		this.totalCharges=totalCharges;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.guideRemuneration=guideRemuneration;
		this.isPaid=isPaid;
		this.paidDate=paidDate;
		this.otherRemarks=otherRemarks;
		this.paidMode=paidMode;
		this.studentId=studentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PhdEmployee getGuideId() {
		return guideId;
	}

	public void setGuideId(PhdEmployee guideId) {
		this.guideId = guideId;
	}

	public PhdEmployee getCoGuideId() {
		return coGuideId;
	}

	public void setCoGuideId(PhdEmployee coGuideId) {
		this.coGuideId = coGuideId;
	}

	public Employee getInternalGuide() {
		return internalGuide;
	}

	public void setInternalGuide(Employee internalGuide) {
		this.internalGuide = internalGuide;
	}

	public Employee getInternalCoGuide() {
		return internalCoGuide;
	}

	public void setInternalCoGuide(Employee internalCoGuide) {
		this.internalCoGuide = internalCoGuide;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public BigDecimal getConveyanceCharges() {
		return conveyanceCharges;
	}

	public void setConveyanceCharges(BigDecimal conveyanceCharges) {
		this.conveyanceCharges = conveyanceCharges;
	}

	public BigDecimal getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(BigDecimal otherCharges) {
		this.otherCharges = otherCharges;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Set<PhdGuideRemunerationDetails> getGuideRemuneration() {
		return guideRemuneration;
	}

	public void setGuideRemuneration(
			Set<PhdGuideRemunerationDetails> guideRemuneration) {
		this.guideRemuneration = guideRemuneration;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
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

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}

}
